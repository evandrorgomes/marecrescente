package br.org.cancer.modred.service.impl;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Arrays;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.helper.JsonViewPage;
import br.org.cancer.modred.model.ArquivoResultadoWorkup;
import br.org.cancer.modred.model.AvaliacaoPedidoColeta;
import br.org.cancer.modred.model.AvaliacaoResultadoWorkup;
import br.org.cancer.modred.model.AvaliacaoWorkupDoador;
import br.org.cancer.modred.model.CentroTransplante;
import br.org.cancer.modred.model.Doador;
import br.org.cancer.modred.model.IDoador;
import br.org.cancer.modred.model.Paciente;
import br.org.cancer.modred.model.PedidoWorkup;
import br.org.cancer.modred.model.Solicitacao;
import br.org.cancer.modred.model.annotations.log.CreateLog;
import br.org.cancer.modred.model.domain.StatusTarefa;
import br.org.cancer.modred.model.domain.TipoLogEvolucao;
import br.org.cancer.modred.model.domain.TiposDoador;
import br.org.cancer.modred.model.domain.TiposTarefa;
import br.org.cancer.modred.persistence.IArquivoResultadoWorkupRepository;
import br.org.cancer.modred.persistence.IAvaliacaoResultadoWorkupRepository;
import br.org.cancer.modred.persistence.IAvaliacaoWorkupDoadorRepository;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.service.IAvaliacaoResultadoWorkupService;
import br.org.cancer.modred.service.ICentroTransplanteService;
import br.org.cancer.modred.service.IPedidoColetaService;
import br.org.cancer.modred.service.IPedidoWorkupService;
import br.org.cancer.modred.service.ISolicitacaoService;
import br.org.cancer.modred.service.IStorageService;
import br.org.cancer.modred.service.impl.invocation.AbstractLoggingService;

/**
 * Classe de implementação dos métodos de negócio para avaliação de resultado workup.
 * 
 * @author Fillipe Queiroz
 *
 */
@Service
@Transactional
public class AvaliacaoResultadoWorkupService extends AbstractLoggingService<AvaliacaoResultadoWorkup, Long> implements IAvaliacaoResultadoWorkupService {

	@Autowired
	private IAvaliacaoResultadoWorkupRepository avaliacaoResultadoWorkupRepository;
	
	@Autowired
	private IAvaliacaoWorkupDoadorRepository avaliacaoWorkupDoadorRepository;
	
	private IPedidoColetaService pedidoColetaService;
		
	private AvaliacaoPedidoColetaService avaliacaoPedidoColetaService; 
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AvaliacaoResultadoWorkupService.class);

	@Autowired
	private IArquivoResultadoWorkupRepository arquivoResultadoWorkupRepository;
	
	@SuppressWarnings("rawtypes")
	@Autowired
	private IStorageService storageService;
	
	private ISolicitacaoService solicitacaoService;
	
	private ICentroTransplanteService centroTransplanteService;
	
	private IPedidoWorkupService pedidoWorkupService;
	

	public AvaliacaoResultadoWorkupService() {
		super();
	}
	
	
	@Override
	public IRepository<AvaliacaoResultadoWorkup, Long> getRepository() {
		return avaliacaoResultadoWorkupRepository;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@CreateLog(TipoLogEvolucao.AVALIACAO_RESULTADO_PEDIDO_WORKUP_APROVADO_PARA_DOADOR)
	@Override
	public AvaliacaoResultadoWorkup efetuarPedidoColeta(Long idAvaliacaoResultadoWorkup, String justificativa) {
		AvaliacaoResultadoWorkup avaliacaoResultadoWorkup = obterAvaliacaoResultadoWorkupPorId(idAvaliacaoResultadoWorkup);

		if (avaliacaoResultadoWorkup == null) {
			throw new BusinessException("avaliacao.resultado.workup.nao.encontrada");
		}
		
		PedidoWorkup pedidoWorkup = pedidoWorkupService.obterPedidoWorkup(avaliacaoResultadoWorkup.getResultadoWorkup().getPedidoWorkup());
		
		Long rmr = pedidoWorkup.getSolicitacao().getMatch().getBusca().getPaciente().getRmr();

		fecharTarefaAvaliacao(rmr, idAvaliacaoResultadoWorkup);
		
		if (!avaliacaoResultadoWorkup.getResultadoWorkup().getColetaInviavel()) {
			pedidoColetaService.criarPedidoColetaMedula(pedidoWorkup);		
			avaliacaoResultadoWorkup.setSolicitaColeta(true);
		}
		
		if(avaliacaoResultadoWorkup.getResultadoWorkup().getColetaInviavel()){
			Doador doador = pedidoWorkup.getSolicitacao().getMatch().getDoador();
			if(TiposDoador.NACIONAL.getId().equals(doador.getTipoDoador().getId())){
				criarAvaliacaoPedidoColeta(rmr, avaliacaoResultadoWorkup, pedidoWorkup.getSolicitacao());				
			}
			avaliacaoResultadoWorkup.setSolicitaColeta(false);
			avaliacaoResultadoWorkup.setJustificativa(justificativa);
		}
		
		avaliacaoResultadoWorkup.setDataAtualizacao(LocalDateTime.now());
		return avaliacaoResultadoWorkupRepository.save(avaliacaoResultadoWorkup);
	}
	
	private void criarAvaliacaoPedidoColeta(Long rmr, AvaliacaoResultadoWorkup avaliacaoResultadoWorkup, Solicitacao solicitacao) {
		AvaliacaoPedidoColeta avaliacaoPedidoColeta = new AvaliacaoPedidoColeta();
		avaliacaoPedidoColeta.setDataCriacao(LocalDateTime.now());
		avaliacaoPedidoColeta.setSolicitacao(solicitacao);
		avaliacaoPedidoColetaService.save(avaliacaoPedidoColeta);
		criarTarefaMedicoRedomeParaAvaliarPedidoColeta(rmr, avaliacaoPedidoColeta.getId());
	}

	private void fecharTarefaAvaliacao(Long rmr, Long idAvaliacaoResultadoWorkup) {
		TiposTarefa.AVALIAR_RESULTADO_WORKUP_NACIONAL.getConfiguracao()
		.fecharTarefa()
		.comObjetoRelacionado(idAvaliacaoResultadoWorkup)
		.comRmr(rmr)
		.comStatus(Arrays.asList(StatusTarefa.ATRIBUIDA))
		.apply();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.org.cancer.modred.service.IAvaliacaoResultadoWorkupService#obterAvaliacaoResultadoWorkupPorId(java.lang.Long)
	 */
	@Override
	public AvaliacaoResultadoWorkup obterAvaliacaoResultadoWorkupPorId(Long idAvaliacao) {
		return avaliacaoResultadoWorkupRepository.findById(idAvaliacao).get();
	}

	@Override
	public void salvar(AvaliacaoResultadoWorkup avaliacao){
		this.avaliacaoResultadoWorkupRepository.save(avaliacao);
	}

	@CreateLog(TipoLogEvolucao.AVALIACAO_RESULTADO_PEDIDO_WORKUP_NAO_APROVADO_PARA_DOADOR)
	@Override
	public void negarAvaliacaoResultadoWorkup(Long idAvaliacaoResultadoWorkup, String justificativa) {
		AvaliacaoResultadoWorkup avaliacaoResultadoWorkup = obterAvaliacaoResultadoWorkupPorId(idAvaliacaoResultadoWorkup);
		
		if (avaliacaoResultadoWorkup == null) {
			throw new BusinessException("avaliacao.resultado.workup.nao.encontrada");
		}

		PedidoWorkup pedidoWorkup = pedidoWorkupService.obterPedidoWorkup(avaliacaoResultadoWorkup.getResultadoWorkup().getPedidoWorkup());
		TiposDoador tipoDoador = pedidoWorkup.getSolicitacao().getMatch().getDoador().getTipoDoador();
		
		Paciente paciente = pedidoWorkup.getSolicitacao().getMatch().getBusca().getPaciente();
		Long rmr = paciente.getRmr();
		
		fecharTarefaAvaliacao(rmr, idAvaliacaoResultadoWorkup);
		
		AvaliacaoWorkupDoador avaliacaoWorkupDoador = new AvaliacaoWorkupDoador();
		avaliacaoWorkupDoador.setDataCriacao(LocalDateTime.now());
		avaliacaoWorkupDoador.setResultadoWorkup(avaliacaoResultadoWorkup.getResultadoWorkup());
		avaliacaoWorkupDoadorRepository.save(avaliacaoWorkupDoador);
				
		if(TiposDoador.NACIONAL.getId().equals(tipoDoador.getId())){
			criarTarefaMedicoRedomeParaAvaliarDoador(rmr, avaliacaoWorkupDoador.getId());			
		}

		avaliacaoResultadoWorkup.setDataAtualizacao(LocalDateTime.now());
		avaliacaoResultadoWorkup.setJustificativa(justificativa);
		avaliacaoResultadoWorkup.setSolicitaColeta(false);
		save(avaliacaoResultadoWorkup);
		
		solicitacaoService.cancelarSolicitacao(pedidoWorkup.getSolicitacao().getId(), null, null, null, null);
	}
	
	private void criarTarefaMedicoRedomeParaAvaliarDoador(Long rmr, Long avaliacaoResultadoWorkupId) {
		TiposTarefa.ANALISE_MEDICA_DOADOR_COLETA.getConfiguracao()
		.criarTarefa()
		.comRmr(rmr)
		.comObjetoRelacionado(avaliacaoResultadoWorkupId)
		.apply();
	}

	private void criarTarefaMedicoRedomeParaAvaliarPedidoColeta(Long rmr, Long avaliacaoResultadoWorkupId) {
		TiposTarefa.AVALIAR_PEDIDO_COLETA.getConfiguracao()
		.criarTarefa()
		.comRmr(rmr)
		.comObjetoRelacionado(avaliacaoResultadoWorkupId)
		.apply();
	}
	
	@Override
	public File obterArquivoLaudo(Long idArquivo){
		LOGGER.info("BAIXANDO ARQUIVO DE LAUDO");
		ArquivoResultadoWorkup arquivoResultadoWorkup = arquivoResultadoWorkupRepository.findById(idArquivo).get();
		String[] nomeEDiretorio = storageService.obterNomeEDiretorioPorCaminho(arquivoResultadoWorkup.getCaminho());
		return storageService.download(nomeEDiretorio[0], nomeEDiretorio[1]);
	}

	@Override
	public JsonViewPage<TarefaDTO> listarTarefasPorCentroTransplante(Long idCentroTransplante, PageRequest pageRequest) {

		final CentroTransplante centroTransplante = centroTransplanteService.obterCentroTransplante(idCentroTransplante);
		//final Usuario usuarioLogado = usuarioService.obterUsuarioLogado(); 
				
		return TiposTarefa.AVALIAR_RESULTADO_WORKUP_NACIONAL.getConfiguracao()
					.listarTarefa()
					.comPaginacao(pageRequest)
					.comStatus(Arrays.asList(StatusTarefa.ABERTA, StatusTarefa.ATRIBUIDA))
					.comParceiros(Arrays.asList(centroTransplante.getId()))
					//.comUsuario(usuarioLogado)
					.apply();
	}


	/**
	 * @param pedidoColetaService the pedidoColetaService to set
	 */
	@Autowired
	public void setPedidoColetaService(IPedidoColetaService pedidoColetaService) {
		this.pedidoColetaService = pedidoColetaService;
	}


	/**
	 * @param avaliacaoPedidoColetaService the avaliacaoPedidoColetaService to set
	 */
	@Autowired
	public void setAvaliacaoPedidoColetaService(AvaliacaoPedidoColetaService avaliacaoPedidoColetaService) {
		this.avaliacaoPedidoColetaService = avaliacaoPedidoColetaService;
	}


	/**
	 * @param solicitacaoService the solicitacaoService to set
	 */
	@Autowired
	public void setSolicitacaoService(ISolicitacaoService solicitacaoService) {
		this.solicitacaoService = solicitacaoService;
	}


	/**
	 * @param centroTransplanteService the centroTransplanteService to set
	 */
	@Autowired
	public void setCentroTransplanteService(ICentroTransplanteService centroTransplanteService) {
		this.centroTransplanteService = centroTransplanteService;
	}


	@Override
	public Paciente obterPaciente(AvaliacaoResultadoWorkup avaliacaoResultadoWorkup) {
		PedidoWorkup pedidoWorkup = pedidoWorkupService.obterPedidoWorkup(avaliacaoResultadoWorkup.getResultadoWorkup().getPedidoWorkup());
		return pedidoWorkup.getSolicitacao().getMatch().getBusca().getPaciente();
	}


	@SuppressWarnings("rawtypes")
	@Override
	public String[] obterParametros(AvaliacaoResultadoWorkup avaliacaoResultadoWorkup) {
		PedidoWorkup pedidoWorkup = pedidoWorkupService.obterPedidoWorkup(avaliacaoResultadoWorkup.getResultadoWorkup().getPedidoWorkup());
		IDoador doador = (IDoador) pedidoWorkup.getSolicitacao().getMatch().getDoador();
		return StringUtils.split(doador.getIdentificacao().toString());
	}

	@Autowired
	public void setPedidoWorkupService(IPedidoWorkupService pedidoWorkupService) {
		this.pedidoWorkupService = pedidoWorkupService;
	}
	

}
