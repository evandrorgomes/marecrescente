package br.org.cancer.redome.tarefa.process.server.impl;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import br.org.cancer.redome.tarefa.dto.PesquisaWmdaDTO;
import br.org.cancer.redome.tarefa.dto.PesquisaWmdaDoadorDTO;
import br.org.cancer.redome.tarefa.dto.ResultadoPesquisaWmdaDTO;
import br.org.cancer.redome.tarefa.exception.BusinessException;
import br.org.cancer.redome.tarefa.feign.client.IBuscaFeign;
import br.org.cancer.redome.tarefa.feign.client.IPesquisaWmdaDoadorFeign;
import br.org.cancer.redome.tarefa.feign.client.IPesquisaWmdaFeign;
import br.org.cancer.redome.tarefa.model.Tarefa;
import br.org.cancer.redome.tarefa.model.domain.StatusPesquisaWmda;
import br.org.cancer.redome.tarefa.model.domain.StatusTarefa;
import br.org.cancer.redome.tarefa.process.server.IProcessadorTarefa;
import br.org.cancer.redome.tarefa.service.IProcessoService;
import br.org.cancer.redome.tarefa.service.IWmdaService;

/**
 * Processo para enviar pacientes para o WMDA.
 * @author Filipe Paes
 *
 */
@Service
@Transactional
public class ProcessadorDePesquisaResultadoDoadorParaWmda  implements IProcessadorTarefa {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProcessadorDePesquisaResultadoDoadorParaWmda.class);
	
	private static final String TIPO_BUSCA_DOADOR_MEDULA = "DR";
	private static final String TIPO_BUSCA_DOADOR_CORDAO = "CB";
	private static final LocalDateTime START = LocalDateTime.now();
	private static final String DONPOOL = "8766";
	public static int idxImport;
	public static int idxSearch;
	public static int userLimit;
	public static int qtdValorBlank;
	
	@Autowired
	private IWmdaService wmdaService;
	
	@Autowired
	private IProcessoService processoService;
	
	@Autowired
	@Lazy(true)
	private IBuscaFeign buscaFeign;
	
	@Autowired
	@Lazy(true)
	private IPesquisaWmdaFeign pesquisaWmdaFeign;

	@Autowired
	@Lazy(true)
	private IPesquisaWmdaDoadorFeign pesquisaWmdaDoadorFeign;
	
	@Override
	public void processar(Tarefa tarefa) {
		
		if (tarefa == null) {
			throw new BusinessException("erro.mensagem.tarefa.associada.nao.encontrada");
		}
		if (tarefa.getStatus().equals(StatusTarefa.ABERTA.getId())) {		

			ResultadoPesquisaWmdaDTO objSearchResultsDto = null;
			
			idxImport = 0; idxSearch = 0; userLimit = 100; qtdValorBlank = 0;

			LOGGER.info("Inicio da execução da pesquisa wmda para atualização das informações do doador e match validos.");
			PesquisaWmdaDTO pesquisaWmdaDto = pesquisaWmdaFeign.obterPesquisaWmdaPorBuscaIdEStatusAbertoEProcessadoErro(tarefa.getRelacaoEntidade());

			List<String> listaDoadorPesquisado = this.pesquisaWmdaDoadorFeign.obterListaDeIdentificacaoDoadorWmdaExistente(pesquisaWmdaDto.getId());
			if(listaDoadorPesquisado != null && !listaDoadorPesquisado.isEmpty()) {
				idxImport = pesquisaWmdaDto.getQtdMatchWmdaImportado();
			}
			
			try {	

				objSearchResultsDto = 
						wmdaService.buscarSearchResultsDoadores(pesquisaWmdaDto.getWmdaId(), pesquisaWmdaDto.getSearchResultId());
				
				if(pesquisaWmdaDto.getStatus().equals(StatusPesquisaWmda.ABERTO.getId()) || 
						pesquisaWmdaDto.getStatus().equals(StatusPesquisaWmda.PROCESSADO_COM_ERRO.getId())) {
					//pesquisaWmdaDto.setJsonPesquisaWmda(objSearchResultsDto.getJsonWmda());
					this.atualizarRotinaPesquisaWmda(pesquisaWmdaDto, StatusPesquisaWmda.ABERTO.getId());
				}
//				else {
//					wmdaService.converterMatchsDoadoresWmdaParaResultadoPesquisa(pesquisaWmdaDto.getJsonPesquisaWmda()); 
//				}
	
				if(objSearchResultsDto.getDonors() != null) {
					
					idxSearch = objSearchResultsDto.getDonors().size();
					userLimit = userLimit > idxSearch ? idxSearch : userLimit; 
											
					objSearchResultsDto.getDonors()
						.stream()
						.filter(p1 -> !listaDoadorPesquisado.contains(p1.getId()))
						.filter(p2 -> idxImport < userLimit)
						.forEach(donor -> {
	
						PesquisaWmdaDoadorDTO novaPesquisaWmdaDoadorDto = PesquisaWmdaDoadorDTO.parse(donor);
						if(novaPesquisaWmdaDoadorDto != null) {
							novaPesquisaWmdaDoadorDto = novaPesquisaWmdaDoadorDto.toBuilder()
									.pesquisaWmdaId(pesquisaWmdaDto.getId())
									.buscaId(pesquisaWmdaDto.getBuscaId())
									.donPool(DONPOOL)
									.build();
		
							LOGGER.info(novaPesquisaWmdaDoadorDto.getIdentificacao() + " Inicio da criação da pesquisa wmda do doador!");
							PesquisaWmdaDoadorDTO pesquisaWmdaDoadorDto = this.pesquisaWmdaDoadorFeign.manterRotinaPesquisaWmdaDoador(novaPesquisaWmdaDoadorDto);
							if(pesquisaWmdaDoadorDto.getLogPesquisaDoador() == null) {
		
								LOGGER.info("Atualização do match wmda!");
								this.pesquisaWmdaFeign.manterRotinaMatchWmda(pesquisaWmdaDoadorDto.getDoadorWmdaDto().getMatchWmdaDto());
								
								idxImport++;
						
							}else {
								LOGGER.info("Problema na Pesquisa WMDA Doador" + pesquisaWmdaDoadorDto.getLogPesquisaDoador() 
										+ "Identificador: " + pesquisaWmdaDoadorDto.getIdentificacao()
										+ "Grid: " + pesquisaWmdaDoadorDto.getGrid());
							}
						}
						else {
							//LOGGER.info("Faltando informações na pesquisa wmda!");
							qtdValorBlank++;
						}
					});
					pesquisaWmdaDto.setLogPesquisaWmda(null);
					this.atualizarRotinaPesquisaWmda(pesquisaWmdaDto, StatusPesquisaWmda.PROCESSADO.getId());
					
					if(pesquisaWmdaDto.getTipo().equals(TIPO_BUSCA_DOADOR_MEDULA)) {
						
						if((idxSearch < userLimit && idxImport != idxSearch) || (idxSearch > userLimit && idxImport != userLimit)) {
							LOGGER.error("Erro na execução do schedule - limite de importação não realizado ");
							pesquisaWmdaDto.setLogPesquisaWmda("Límite de importação não realizado: " + idxSearch);
							this.atualizarRotinaPesquisaWmda(pesquisaWmdaDto, StatusPesquisaWmda.PROCESSADO_COM_ERRO.getId());
						}else {
							LOGGER.info("Criação da pesquisa wmda para cordão!");
							this.criarPesquisaTipoCordaoWmda(pesquisaWmdaDto);
						}
					}else {
						LOGGER.info("Fechar tarefa!");
						this.processoService.fecharTarefa(tarefa);
					}
				}	
			}
		    catch (Exception e) {
				LOGGER.error("Erro na execução do schedule ", e.getMessage());
				pesquisaWmdaDto.setLogPesquisaWmda(e.getMessage());
				this.atualizarRotinaPesquisaWmda(pesquisaWmdaDto, StatusPesquisaWmda.PROCESSADO_COM_ERRO.getId());
			}

			LOGGER.info("Fim da execução - Tempo total de execução: {} minutos", ChronoUnit.MINUTES.between(START, LocalDateTime.now()));
		}
//		else if (tarefa.getStatus().equals(StatusTarefa.FEITA.getId())) {
//			processoService.fecharTarefa(tarefa);
//		}
//		else if (tarefa.getStatus().equals(StatusTarefa.CANCELADA.getId())) {
//			processoService.cancelarTarefa(tarefa);
//		}
	}

	/**
	 * @param pesquisaWmdaDto
	 * @param objSearchResultsDto
	 */
	private void atualizarRotinaPesquisaWmda(PesquisaWmdaDTO pesquisaWmdaDto, Integer statusId) {
		PesquisaWmdaDTO novaPesquisaWmdaDto = pesquisaWmdaDto.toBuilder()
			.qtdMatchWmda(idxSearch)
			.qtdMatchWmdaImportado(idxImport)
			.qtdValorBlank(qtdValorBlank)
			.dtResultado(LocalDateTime.now())
			.status(statusId)
			.build();

		LOGGER.info("Atualização da pesquisa wmda!");
		this.pesquisaWmdaFeign.atualizarRotinaPesquisaWmda(novaPesquisaWmdaDto.getId(), novaPesquisaWmdaDto);
	}
	
	/**
	 * @param tarefa
	 * @param pacienteWmda
	 */
	public PesquisaWmdaDTO criarPesquisaTipoCordaoWmda(PesquisaWmdaDTO pesquisaWmdaDto) {
		
		PesquisaWmdaDTO novaPesquisaWmdaDto = PesquisaWmdaDTO.builder()
			.buscaId(pesquisaWmdaDto.getBuscaId())
			.wmdaId(pesquisaWmdaDto.getWmdaId())
 			.tipo(TIPO_BUSCA_DOADOR_CORDAO)
 			.status(StatusPesquisaWmda.ABERTO.getId())
 			.build();
		
		ResultadoPesquisaWmdaDTO respSearchesDto = wmdaService.buscarSearchIdPorWmdaId(novaPesquisaWmdaDto.getWmdaId(), novaPesquisaWmdaDto.getTipo());
		novaPesquisaWmdaDto.setSearchId(respSearchesDto.getSearchId());

		ResultadoPesquisaWmdaDTO respSearchResultsDto = wmdaService.buscarSearchResultsIdPorWmdaId(novaPesquisaWmdaDto.getWmdaId());
		if(respSearchResultsDto.getRequests() != null && !respSearchResultsDto.getRequests().isEmpty()) {
			novaPesquisaWmdaDto.setSearchResultId(respSearchResultsDto.getRequests().get(0).getSearchResultsId());
		}

		return pesquisaWmdaFeign.criarPesquisaWmda(novaPesquisaWmdaDto);
	}

}
