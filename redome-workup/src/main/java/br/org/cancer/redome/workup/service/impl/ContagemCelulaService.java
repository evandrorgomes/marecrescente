package br.org.cancer.redome.workup.service.impl;

import java.util.Arrays;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.redome.workup.dto.ContagemCelulaDTO;
import br.org.cancer.redome.workup.dto.ListaTarefaDTO;
import br.org.cancer.redome.workup.dto.ProcessoDTO;
import br.org.cancer.redome.workup.dto.SolicitacaoWorkupDTO;
import br.org.cancer.redome.workup.dto.TarefaDTO;
import br.org.cancer.redome.workup.dto.TipoTarefaDTO;
import br.org.cancer.redome.workup.exception.BusinessException;
import br.org.cancer.redome.workup.feign.client.ISolicitacaoFeign;
import br.org.cancer.redome.workup.helper.ITarefaHelper;
import br.org.cancer.redome.workup.model.ContagemCelula;
import br.org.cancer.redome.workup.model.PedidoWorkup;
import br.org.cancer.redome.workup.model.domain.FontesCelulas;
import br.org.cancer.redome.workup.model.domain.Perfis;
import br.org.cancer.redome.workup.model.domain.StatusTarefa;
import br.org.cancer.redome.workup.model.domain.TipoProcesso;
import br.org.cancer.redome.workup.model.domain.TiposTarefa;
import br.org.cancer.redome.workup.persistence.IContagemCelulaRepository;
import br.org.cancer.redome.workup.persistence.IRepository;
import br.org.cancer.redome.workup.service.IContagemCelulaService;
import br.org.cancer.redome.workup.service.IPedidoWorkupService;
import br.org.cancer.redome.workup.service.IUsuarioService;
import br.org.cancer.redome.workup.service.impl.custom.AbstractService;


@Service
@Transactional
public class ContagemCelulaService extends AbstractService<ContagemCelula, Long> implements IContagemCelulaService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PedidoLogisticaMaterialColetaNacionalService.class);
	
	@Override
	public IRepository<ContagemCelula, Long> getRepository() {
		// TODO Auto-generated method stub
		return contagemCelulaRepository;
	}
	@Autowired
	private IContagemCelulaRepository contagemCelulaRepository;
	
	@Autowired
	private IPedidoWorkupService pedidoWorkupService;
	
	@Autowired
	private ISolicitacaoFeign solicitacaoFeign;
	
	@Autowired
	@Lazy(true)
	private ITarefaHelper tarefaHelper;
	
	@Autowired
	private IUsuarioService usuarioService;
	
	
	@Override
	public ContagemCelulaDTO obterContagemCelulaDto(Long idPedidoWorkup) {
		if (idPedidoWorkup == null) {
			throw new BusinessException("erro.id.nulo");
		}
		return ContagemCelulaDTO.parseToDTO( contagemCelulaRepository.findByIdPedidoWorkup(idPedidoWorkup));
	}
	
	
	public ContagemCelula obterContagemCelula(Long idPedidoWorkup) {
		if (idPedidoWorkup == null) {
			throw new BusinessException("erro.id.nulo");
		}
		return contagemCelulaRepository.findByIdPedidoWorkup(idPedidoWorkup);
	}
	
	
	@Override
	public void salvarContagemCelula(Long idPedidoWorkup, ContagemCelulaDTO contagemCelulaDTO) {
		ContagemCelula contagemCelula = null;
		validarContagemCelula(contagemCelulaDTO);
		
		contagemCelula = this.obterContagemCelula(idPedidoWorkup);
		
		if(contagemCelula== null) {
			contagemCelula = ContagemCelula.parseDTO(contagemCelulaDTO);
			contagemCelula.setIdPedidoWorkup(idPedidoWorkup);
		}else {
			long _idContagemCelula = contagemCelula.getId();
			contagemCelulaDTO.setIdPedidoWorkup(idPedidoWorkup);
			contagemCelula = ContagemCelula.parseObjeto(contagemCelula,contagemCelulaDTO);
			contagemCelula.setId(_idContagemCelula);
		}
		
		
		
		contagemCelulaRepository.save(contagemCelula);
		
	}
	
	@Override
	public void finalizarContagemCelula(Long idPedidoWorkup, ContagemCelulaDTO contagemCelulaDTO) throws JsonProcessingException {
		
		salvarContagemCelula( idPedidoWorkup, contagemCelulaDTO);
		
		PedidoWorkup pedidoWorkup = pedidoWorkupService.obterPedidoWorkup(idPedidoWorkup);
		
		SolicitacaoWorkupDTO solicitacao = solicitacaoFeign.obterSolicitacaoWorkup(pedidoWorkup.getSolicitacao());
		
		solicitacaoFeign.atualizarSolicitacaoContagemCelula(pedidoWorkup.getSolicitacao());
		
		fecharTarefaContagemCelula(idPedidoWorkup,solicitacao);
		
		this.criarFormularioPosColeta(idPedidoWorkup,solicitacao);
		//solicitacao = solicitacaoFeign.atualizarFaseWorkup(pedidoWorkup.getSolicitacao(), FasesWorkup.AGUARDANDO_INFORMACAO_POSCOLETA.getId());
		
		
	}
	
	private void criarFormularioPosColeta(Long idPedidoWorkup, SolicitacaoWorkupDTO solicitacao) {
		Long rmr = solicitacao.getMatch().getBusca().getPaciente().getRmr();

		ProcessoDTO processo = new ProcessoDTO(TipoProcesso.BUSCA);
		processo.setRmr(rmr);

		TarefaDTO tarefa = TarefaDTO.builder()
				.processo(processo) 
				.tipoTarefa(new TipoTarefaDTO(TiposTarefa.INFORMAR_FORMULARIO_POSCOLETA.getId()))
				.perfilResponsavel(Perfis.MEDICO_CENTRO_COLETA.getId())
				.status(StatusTarefa.ABERTA.getId())
				.relacaoEntidade(idPedidoWorkup)
				.relacaoParceiro(solicitacao.getIdCentroColeta())
				.build(); 

		tarefaHelper.criarTarefa(tarefa);
		
	}


	private void fecharTarefaContagemCelula(Long idPedidoWorkup, SolicitacaoWorkupDTO solicitacao) throws JsonProcessingException {
		Long rmr = solicitacao.getMatch().getBusca().getPaciente().getRmr();
		
		ListaTarefaDTO filtroDTO = ListaTarefaDTO.builder()
				.idsTiposTarefa(Arrays.asList(TiposTarefa.CADASTRAR_COLETA_CONTAGEM_CELULA.getId()))
				.statusTarefa(Arrays.asList(StatusTarefa.ATRIBUIDA.getId()))
				.idUsuarioLogado(usuarioService.obterIdUsuarioLogado())
				.rmr(rmr)
				.relacaoEntidadeId(idPedidoWorkup)
				.tipoProcesso(TipoProcesso.BUSCA.getId())
				.build();
		
		tarefaHelper.encerrarTarefa(filtroDTO, false);
		
	}
	

	
	private void validarContagemCelula(ContagemCelulaDTO contagem) {
		if(contagem.getFonteCelula()==null) {
			throw new BusinessException("erro.validacao.registro.invalido", "Fonte Celula sem marcação");
		}else {
			if(contagem.getFonteCelula().equals(FontesCelulas.MEDULA_OSSEA.getSigla())) {
				if(contagem.getDataColeta()==null) {
					throw new BusinessException("erro.validacao.registro.invalido", "Coleta sem Data");
				}
				if(contagem.getVolumeBolsa()==null) {
					throw new BusinessException("erro.validacao.registro.invalido", "Volume da bolsa coletada");
				}
				if(contagem.getVolumeAnticoagulante()==null) {
					throw new BusinessException("erro.validacao.registro.invalido", "Volume de Anticoagulante na Bolsa ao Coletar Medula");
				}
				if(contagem.getQuantidadeTCN()==null) {
					throw new BusinessException("erro.validacao.registro.invalido", "Quantidade de TCN na Bolsa ao Coletar Medula.");
				}
				if(contagem.getQuantidadeTCNKG()==null) {
					throw new BusinessException("erro.validacao.registro.invalido", "Quantidade de TCN por kg na Bolsa ao Coletar Medula.");
				}
			}else {
				if(contagem.getDataAferese_0()==null ) {
					throw new BusinessException("erro.validacao.registro.invalido", "Data da 1ª aferise sangue periferico ou Linfocito.");

				}else {
					if(contagem.getVolumeBolsa_0()==null) {
						throw new BusinessException("erro.validacao.registro.invalido", "Quantidade de TCN por kg na 1ª Bolsa ao sangue periferico ou Linfocito");
					}
					if(contagem.getCelula_0()==null) {
						throw new BusinessException("erro.validacao.registro.invalido", "Número de Celulas da 1ª aferise.");
					}
					if(contagem.getCelulaKg_0()==null) {
						throw new BusinessException("erro.validacao.registro.invalido", "Número de Celulas por Kg do paciente da 1ª aferise.");
					}
					if( contagem.getVolumeBolsa_1()!=null||contagem.getCelula_1()!=null||contagem.getCelulaKg_1()!=null ) {
						throw new BusinessException("erro.validacao.registro.invalido", "Data da 2ª aferise sangue periferico ou Linfocito.");
					}else if(contagem.getDataAferese_1()!=null){
						if(contagem.getVolumeBolsa_1()==null) {
							throw new BusinessException("erro.validacao.registro.invalido", "Quantidade de TCN por kg na 2ª Bolsa ao sangue periferico ou Linfocito");
						}
						if(contagem.getCelula_1()==null) {
							throw new BusinessException("erro.validacao.registro.invalido", "Número de Celulas da 2ª aferise.");
						}
						if(contagem.getCelulaKg_1()==null) {
							throw new BusinessException("erro.validacao.registro.invalido", "Número de Celulas por Kg do paciente da 2ª aferise.");
						}
						if( contagem.getVolumeBolsa_2()!=null||contagem.getCelula_2()!=null||contagem.getCelulaKg_2()!=null ) {
							throw new BusinessException("erro.validacao.registro.invalido", "Data da 3ª aferise sangue periferico ou Linfocito.");
						}else if(contagem.getDataAferese_2()!=null){
							if(contagem.getVolumeBolsa_2()==null) {
								throw new BusinessException("erro.validacao.registro.invalido", "Quantidade de TCN por kg na 3ª Bolsa ao sangue periferico ou Linfocito");
							}
							if(contagem.getCelula_2()==null) {
								throw new BusinessException("erro.validacao.registro.invalido", "Número de Celulas da 3ª aferise.");
							}
							if(contagem.getCelulaKg_2()==null) {
								throw new BusinessException("erro.validacao.registro.invalido", "Número de Celulas por Kg do paciente da 3ª aferise.");
							}
						}
					}
				}
			}
		}
		if(contagem.getAbo()==null) {
			throw new BusinessException("erro.validacao.registro.invalido", "ABO sem marcação");
		}
		if(contagem.getManipulacaoProduto()==null) {
			throw new BusinessException("erro.validacao.registro.invalido", "Manipulação do produto sem marcação");
		}else {
			if(contagem.getManipulacaoProduto()) {
				if(contagem.getManipulacao()==null) {
					throw new BusinessException("erro.validacao.registro.invalido", "Qual Manipulação do produto");
				}
			}
		}
		if(contagem.getDiaEnvio()==null) {
			throw new BusinessException("erro.validacao.registro.invalido", "Data do Envio da coleta.");
		}
		if(contagem.getAnticoagulante()==null) {
			throw new BusinessException("erro.validacao.registro.invalido", "Anticoagulante Utilizado.");
		}
		
		if(contagem.getHouveIntercorrencia()==null) {
			throw new BusinessException("erro.validacao.registro.invalido", "intercorrência durante ou após a coleta de CTH sem marcação");
		}else {
			if(contagem.getManipulacaoProduto()) {
				if(contagem.getIntercorrencia()==null) {
					throw new BusinessException("erro.validacao.registro.invalido", "Quais intercorrências durante ou após a coleta de CTH");
				}
			}
		}
	}


	@Override
	public ContagemCelula criarPedidoContagemCelula(Long idPedidoWorkup) {
		LOGGER.info("Criar pedido de logistica material coleta");
				
		ContagemCelula contagemCelula = ContagemCelula.builder()
			.idPedidoWorkup(idPedidoWorkup)
			.build();

		return save(contagemCelula);
		
	}
	
	
	
}
