package br.org.cancer.redome.tarefa.process.server.impl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import br.org.cancer.redome.tarefa.dto.PacienteWmdaDTO;
import br.org.cancer.redome.tarefa.dto.PesquisaWmdaDTO;
import br.org.cancer.redome.tarefa.dto.ResultadoPesquisaWmdaDTO;
import br.org.cancer.redome.tarefa.exception.BusinessException;
import br.org.cancer.redome.tarefa.feign.client.IPacienteFeign;
import br.org.cancer.redome.tarefa.feign.client.IPesquisaWmdaFeign;
import br.org.cancer.redome.tarefa.model.Tarefa;
import br.org.cancer.redome.tarefa.model.TipoTarefa;
import br.org.cancer.redome.tarefa.model.domain.StatusPesquisaWmda;
import br.org.cancer.redome.tarefa.model.domain.StatusTarefa;
import br.org.cancer.redome.tarefa.model.domain.TiposTarefa;
import br.org.cancer.redome.tarefa.process.server.IProcessadorTarefa;
import br.org.cancer.redome.tarefa.service.IProcessoService;
import br.org.cancer.redome.tarefa.service.IWmdaService;

/**
 * Processo para enviar pacientes para o WMDA.
 * @author ergomes
 *
 */
@Service
@Transactional
public class ProcessadorDeEnvioDadosPacienteParaWmda  implements IProcessadorTarefa {
		
	private static final Logger LOGGER = LoggerFactory.getLogger(ProcessadorDeEnvioDadosPacienteParaWmda.class);

	private static final String TIPO_BUSCA_DOADOR_MEDULA = "DR";
	private static final LocalDateTime START = LocalDateTime.now();
	
	@Autowired
	private IWmdaService wmdaService;
	
	@Autowired
	private IProcessoService processoService;
	
	@Autowired
	@Lazy(true)
	private IPacienteFeign pacienteFeign;
	
	@Autowired
	@Lazy(true)
	private IPesquisaWmdaFeign pesquisaWmdaFeign;
	
	@Override
	public void processar(Tarefa tarefa) {
		if (tarefa == null) {
			throw new BusinessException("erro.mensagem.tarefa.associada.nao.encontrada");
		}
		if (tarefa.getStatus().equals(StatusTarefa.ABERTA.getId())) {		

			try {	
				
				LOGGER.info("Inicio da execução da inclusão das informações do paciente no wmda.");
				
//				ResultadoPesquisaWmdaDTO resPacienteWmdaDto = this.manterPacienteWmda(tarefa.getProcesso().getRmr());
				
				PacienteWmdaDTO pacienteWmdaDto = pacienteFeign.obterPacienteDtoWmdaPorRmr(tarefa.getProcesso().getRmr());
				if(pacienteWmdaDto.getAbo().equals("-")){
					pacienteWmdaDto.setAbo("A");
				}

							
				PesquisaWmdaDTO pesquisaWmdaDto = PesquisaWmdaDTO.builder()
					.buscaId(tarefa.getRelacaoEntidade())
					.wmdaId(String.valueOf(pacienteWmdaDto.getWmdaId()))
					//.wmdaId(resPacienteWmdaDto.getWmdaId())
					.build();
				this.criarPesquisaMatchTipoMedulaWmda(pesquisaWmdaDto);
				
				this.processoService.fecharTarefa(tarefa);
				this.criarTarefaResultadoMatchTipoMedulaWmdaFollowUp(tarefa);
				
			}
			catch (Exception e) {
				LOGGER.error("Erro na execução do schedule ", e);
			}
	
			LOGGER.info("Fim da execução - Tempo total de execução: {} minutos", ChronoUnit.MINUTES.between(START, LocalDateTime.now()));
		}
		else if (tarefa.getStatus().equals(StatusTarefa.FEITA.getId())) {
			processoService.fecharTarefa(tarefa);
		}
		else if (tarefa.getStatus().equals(StatusTarefa.CANCELADA.getId())) {
			processoService.cancelarTarefa(tarefa);
		}
	}

	/**
	 * @param tarefa
	 */
	public void criarTarefaResultadoMatchTipoMedulaWmdaFollowUp(Tarefa tarefa) {
		tarefa = tarefa.toBuilder()
			.tipoTarefa(TipoTarefa.builder().id(TiposTarefa.RESULTADO_PESQUISA_DOADOR_WMDA_FOLLOWUP.getId()).build())
			.status(StatusTarefa.ABERTA.getId())
			.build();
		processoService.criarTarefa(tarefa);
	}

	/**
	 * @param tarefa
	 * @return
	 * @throws IOException 
	 */
	public ResultadoPesquisaWmdaDTO manterPacienteWmda(Long rmr) throws IOException {
		
		ResultadoPesquisaWmdaDTO respPacienteDto = null;
		
		PacienteWmdaDTO pacienteWmdaDto = pacienteFeign.obterPacienteDtoWmdaPorRmr(rmr);
		if(pacienteWmdaDto.getAbo().equals("-")){
			pacienteWmdaDto.setAbo("A");
		}
		if(pacienteWmdaDto.getWmdaId() != null) {
			respPacienteDto = wmdaService.atualizarPacienteWmda(pacienteWmdaDto);
		}else {
			respPacienteDto = wmdaService.criarPacienteWmda(pacienteWmdaDto);
			pacienteFeign.atualizarWmdaIdPorRmrDoPaciente(pacienteWmdaDto.getRmr(), respPacienteDto.getWmdaId());
		}
		return respPacienteDto;
	}

	/**
	 * @param tarefa
	 * @param pacienteWmda
	 */
	public PesquisaWmdaDTO criarPesquisaMatchTipoMedulaWmda(PesquisaWmdaDTO pesquisaWmdaDto) {
		
		pesquisaWmdaDto = pesquisaWmdaDto.toBuilder()
			.tipo(TIPO_BUSCA_DOADOR_MEDULA)
			.status(StatusPesquisaWmda.ABERTO.getId())
			.build();
		
		ResultadoPesquisaWmdaDTO respSearchesDto = wmdaService.buscarSearchIdPorWmdaId(pesquisaWmdaDto.getWmdaId(), pesquisaWmdaDto.getTipo());
		pesquisaWmdaDto.setSearchId(respSearchesDto.getSearchId());

		ResultadoPesquisaWmdaDTO respSearchResultsDto = wmdaService.buscarSearchResultsIdPorWmdaId(pesquisaWmdaDto.getWmdaId());
		pesquisaWmdaDto.setSearchResultId(respSearchResultsDto.getRequests().get(0).getSearchResultsId());

		return pesquisaWmdaFeign.criarPesquisaWmda(pesquisaWmdaDto);
	}

}
