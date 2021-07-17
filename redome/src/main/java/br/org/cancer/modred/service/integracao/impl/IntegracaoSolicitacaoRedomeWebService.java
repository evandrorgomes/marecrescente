package br.org.cancer.modred.service.integracao.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.controller.dto.doador.DoadorNacionalDTO;
import br.org.cancer.modred.controller.dto.doador.MensagemErroIntegracao;
import br.org.cancer.modred.controller.dto.doador.PedidoExameDTO;
import br.org.cancer.modred.controller.dto.doador.SolicitacaoRedomewebDTO;
import br.org.cancer.modred.exception.ValidationException;
import br.org.cancer.modred.model.DoadorNacional;
import br.org.cancer.modred.model.ExameDoadorNacional;
import br.org.cancer.modred.model.Laboratorio;
import br.org.cancer.modred.model.PedidoExame;
import br.org.cancer.modred.model.SolicitacaoRedomeweb;
import br.org.cancer.modred.model.StatusPedidoExame;
import br.org.cancer.modred.model.domain.StatusPedidosExame;
import br.org.cancer.modred.model.domain.StatusSolicitacao;
import br.org.cancer.modred.model.domain.TiposSolicitacaoRedomeweb;
import br.org.cancer.modred.model.domain.TiposTarefa;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.persistence.ISolicitacaoRedomewebRepository;
import br.org.cancer.modred.persistence.ISolicitacaoRedomewebRepositoryCustom;
import br.org.cancer.modred.service.IDoadorService;
import br.org.cancer.modred.service.IExameDoadorService;
import br.org.cancer.modred.service.IGenotipoDoadorService;
import br.org.cancer.modred.service.ILaboratorioService;
import br.org.cancer.modred.service.IPedidoExameService;
import br.org.cancer.modred.service.ISolicitacaoRedomewebService;
import br.org.cancer.modred.service.ISolicitacaoService;
import br.org.cancer.modred.service.impl.custom.AbstractService;
import br.org.cancer.modred.service.integracao.IIntegracaoSolicitacaoRedomeWebService;

/**
 * Classe de implementação das funcionalidades envolvendo a entidade PedidoExame.
 * 
 * @author Pizão
 * 
 */
@Service
@Transactional
public class IntegracaoSolicitacaoRedomeWebService extends AbstractService<SolicitacaoRedomeweb, Long> implements IIntegracaoSolicitacaoRedomeWebService {

	private static final Logger LOGGER = LoggerFactory.getLogger(IntegracaoSolicitacaoRedomeWebService.class);
	
	@Autowired
	private ISolicitacaoRedomewebService solicitacaoRedomewebService;
	
	@Autowired
	private ILaboratorioService laboratorioService;
	
	@Autowired
	private IGenotipoDoadorService<ExameDoadorNacional> genotipoDoadorService;
	
	@Autowired
	private IDoadorService doadorService;
	
	@Autowired
	private IExameDoadorService<ExameDoadorNacional> exameDoadorNacionalService;
	
	@Autowired
	private ISolicitacaoService solicitacaoService;
	
	@Autowired
	private IPedidoExameService pedidoExameService;
	
	@Autowired
	private ISolicitacaoRedomewebRepositoryCustom solicitacaoRedomewebRepositoryCustom;
	
	@Autowired
	private ISolicitacaoRedomewebRepository solicitacaoRedomewebRepository;
	
	@Override
	public IRepository<SolicitacaoRedomeweb, Long> getRepository() {
		return solicitacaoRedomewebRepository;
	}
	
	@Override
	@Transactional
	public List<MensagemErroIntegracao> atualizarPedidoExameIntegracaoDoadorNacional(List<SolicitacaoRedomewebDTO> solicitacoes) {
		
		List<MensagemErroIntegracao> listSolicitacaoRetorno = new ArrayList<>();
		solicitacoes.stream().forEach(solicitacaoDto ->{
			try {
				SolicitacaoRedomewebDTO solRedomeWebDto = solicitacaoRedomewebRepositoryCustom.listarSolicitacaoRedomewebDTOPorId(solicitacaoDto.getIdSolicitacaoRedomeweb());
				
				PedidoExame pedidoExame = pedidoExameService.obterPedidoExame(solicitacaoDto.getPedidoExame().getId());
				
				StatusPedidoExame status = new StatusPedidoExame(); 
				status.setId(solicitacaoDto.getPedidoExame().getIdStatusPedidoExame());
				pedidoExame.setStatusPedidoExame(status);

				solicitacaoDto.getPedidoExame().getExame().setDoador(new DoadorNacionalDTO(solRedomeWebDto.getPedidoExame().getSolicitacao().getIdDoador()));
				solicitacaoDto.getPedidoExame().getSolicitacao().setRmr(solRedomeWebDto.getPedidoExame().getSolicitacao().getRmr());
				
				if(solRedomeWebDto != null) {
					 
					// SOLICITAÇÃO EXAME
					if(TiposSolicitacaoRedomeweb.EXAME.getId() == solicitacaoDto.getTipoSolicitacaoRedomeweb()) {
						
						pedidoExame.setSolicitacao(solicitacaoService.obterPorId(solRedomeWebDto.getPedidoExame().getSolicitacao().getIdSolicitacao()));
						
						// SOLICITAÇÃO CANCELADA (2)
						if(StatusPedidosExame.CANCELADO.getId() == solicitacaoDto.getPedidoExame().getIdStatusPedidoExame()) {
							pedidoExame.getSolicitacao().setStatus(StatusSolicitacao.CANCELADA.getId());
						}
						
						// SOLICITACAO ATENDIDA (1)
						if(StatusPedidosExame.RESULTADO_CADASTRADO.getId() == solicitacaoDto.getPedidoExame().getIdStatusPedidoExame()) {
							criarSolicitacaoAtendidaFase2(solicitacaoDto, pedidoExame);
						}
	
						// SOLICITACAO NAO ATENDIDA SEM HLA (0)
						if(StatusPedidosExame.NAO_ATENDIDA_SEM_HLA_IDM.getId() == solicitacaoDto.getPedidoExame().getIdStatusPedidoExame() &&
								solicitacaoDto.getPedidoExame().getIdLaboratorio() != null) {
							criarSolicitacaoNaoAtendidaFase2(solicitacaoDto, pedidoExame);
						}
						
						SolicitacaoRedomeweb solRedomeWeb = solicitacaoRedomewebService.findById(solRedomeWebDto.getId());
						solRedomeWeb.setStatusPedidoExame(status.getId());
						solicitacaoRedomewebService.save(solRedomeWeb);
						
					}
					else {
						
						// SOLICITAÇÃO AMOSTRA
						if(StatusPedidosExame.RESULTADO_CADASTRADO.getId() == solicitacaoDto.getPedidoExame().getIdStatusPedidoExame()) {
							
							SolicitacaoRedomewebDTO dto = new SolicitacaoRedomewebDTO();
							dto.setTipoSolicitacaoRedomeweb(TiposSolicitacaoRedomeweb.EXAME.getId());
							dto.setIdSolicitacaoRedomeweb(solRedomeWebDto.getPedidoExame().getSolicitacao().getIdSolicitacao());
							dto.setPedidoExame(new PedidoExameDTO(solRedomeWebDto.getPedidoExame().getId(), StatusPedidosExame.NAO_ATENDIDA_SEM_HLA_IDM.getId()));
							criarSolicitacaoRedomeweb(dto);
													
							SolicitacaoRedomeweb solRedomeWeb = solicitacaoRedomewebService.findById(solRedomeWebDto.getId());
							solRedomeWeb.setStatusPedidoExame(solicitacaoDto.getPedidoExame().getIdStatusPedidoExame());
							solRedomeWeb.getPedidoExame().getStatusPedidoExame().setId(StatusPedidosExame.NAO_ATENDIDA_SEM_HLA_IDM.getId());
							solicitacaoRedomewebService.save(solRedomeWeb);
						}
					}
					

				}
				else {
					// SOLICITACAO NAO ATENDIDA SEM HLA (0) 
					if(StatusPedidosExame.NAO_ATENDIDA_SEM_HLA_IDM.getId() == solicitacaoDto.getPedidoExame().getIdStatusPedidoExame()) {
						criarSolicitacaoRedomeweb(solicitacaoDto);
					}
				}					
				
			}
			catch (Exception erro) {

				MensagemErroIntegracao mensagem = new MensagemErroIntegracao();
				
				if(erro instanceof ValidationException) {
					ValidationException validation = (ValidationException) erro;
					String errosValidations = validation.getErros().stream().map(e -> "Campo " + e.getCampo() + " - " + e.getMensagem()).collect(Collectors.joining(","));
					mensagem.setDescricaoErro(errosValidations);
				}
				else if(erro instanceof NullPointerException) {
					mensagem.setDescricaoErro("NullPointerException - Ocorreu um erro ao salvar objeto no banco de dados.");
				}
				else {
					mensagem.setDescricaoErro(erro.getCause().getCause().getMessage());
				}
				mensagem.setIdSolicitacao(solicitacaoDto.getId());

				listSolicitacaoRetorno.add(mensagem);
			}
		});
		
		return listSolicitacaoRetorno;
	}

	private void criarSolicitacaoNaoAtendidaFase2(SolicitacaoRedomewebDTO solicitacaoDto, PedidoExame pedidoExame) {
		Laboratorio laboratorio = laboratorioService.findById(solicitacaoDto.getPedidoExame().getIdLaboratorio());
		pedidoExame.getSolicitacao().setLaboratorio(laboratorio);
		pedidoExame.setLaboratorio(laboratorio);
	}

	private void criarSolicitacaoAtendidaFase2(SolicitacaoRedomewebDTO solicitacaoDto, PedidoExame pedidoExame) {
		
		pedidoExame.getSolicitacao().setStatus(StatusSolicitacao.CONCLUIDA.getId());
		ExameDoadorNacional exame = exameDoadorNacionalService.criarExameDoadorNacional(solicitacaoDto.getPedidoExame().getExame());
		
		pedidoExame.setExame(exame);
		pedidoExame.setDataColetaAmostra(solicitacaoDto.getPedidoExame().getDataColetaAmostra());
		
		DoadorNacional doadorNacional = new DoadorNacional();
		doadorNacional = (DoadorNacional) doadorService.obterDoador(exame.getDoador().getDmr());
		genotipoDoadorService.gerarGenotipo(doadorNacional);
		
		TiposTarefa.CADASTRAR_RESULTADO_EXAME.getConfiguracao()
		.fecharTarefa()	
		.comRmr(solicitacaoDto.getPedidoExame().getSolicitacao().getRmr())
		.comObjetoRelacionado(pedidoExame.getId())
		.apply();
	}

	private void criarSolicitacaoRedomeweb(SolicitacaoRedomewebDTO solicitacaoRedomeweb) {
		PedidoExame pedidoExame = pedidoExameService.findById(solicitacaoRedomeweb.getPedidoExame().getId());
		
		SolicitacaoRedomeweb solRedWeb = new SolicitacaoRedomeweb();
		solRedWeb.setPedidoExame(pedidoExame);
		solRedWeb.setTipoSolicitacaoRedomeweb(TiposSolicitacaoRedomeweb.valueOf(solicitacaoRedomeweb.getTipoSolicitacaoRedomeweb()));
		solRedWeb.setIdSolicitacaoRedomeweb(solicitacaoRedomeweb.getIdSolicitacaoRedomeweb());
		solRedWeb.setDataCriacao(LocalDateTime.now());
		solRedWeb.setStatusPedidoExame(solicitacaoRedomeweb.getPedidoExame().getIdStatusPedidoExame());
		solicitacaoRedomewebService.save(solRedWeb);
	}

}
