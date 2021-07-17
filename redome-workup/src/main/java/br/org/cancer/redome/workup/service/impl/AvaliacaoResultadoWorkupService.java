package br.org.cancer.redome.workup.service.impl;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.org.cancer.redome.workup.dto.AvaliacaoResultadoWorkupDTO;
import br.org.cancer.redome.workup.dto.CriarLogEvolucaoDTO;
import br.org.cancer.redome.workup.dto.DoadorDTO;
import br.org.cancer.redome.workup.dto.ListaTarefaDTO;
import br.org.cancer.redome.workup.dto.ProcessoDTO;
import br.org.cancer.redome.workup.dto.ResultadoWorkupNacionalDTO;
import br.org.cancer.redome.workup.dto.SolicitacaoWorkupDTO;
import br.org.cancer.redome.workup.dto.TarefaDTO;
import br.org.cancer.redome.workup.dto.TipoTarefaDTO;
import br.org.cancer.redome.workup.exception.BusinessException;
import br.org.cancer.redome.workup.feign.client.ILogEvolucaoFeign;
import br.org.cancer.redome.workup.feign.client.INotificacaoFeign;
import br.org.cancer.redome.workup.feign.client.ISolicitacaoFeign;
import br.org.cancer.redome.workup.helper.ITarefaHelper;
import br.org.cancer.redome.workup.model.AvaliacaoResultadoWorkup;
import br.org.cancer.redome.workup.model.PedidoColeta;
import br.org.cancer.redome.workup.model.Prescricao;
import br.org.cancer.redome.workup.model.ResultadoWorkup;
import br.org.cancer.redome.workup.model.domain.FasesWorkup;
import br.org.cancer.redome.workup.model.domain.Perfis;
import br.org.cancer.redome.workup.model.domain.StatusTarefa;
import br.org.cancer.redome.workup.model.domain.TipoLogEvolucao;
import br.org.cancer.redome.workup.model.domain.TipoProcesso;
import br.org.cancer.redome.workup.model.domain.TiposTarefa;
import br.org.cancer.redome.workup.persistence.IAvaliacaoResultadoWorkupRepository;
import br.org.cancer.redome.workup.persistence.IRepository;
import br.org.cancer.redome.workup.service.IAvaliacaoResultadoWorkupService;
import br.org.cancer.redome.workup.service.IPedidoColetaService;
import br.org.cancer.redome.workup.service.IPedidoLogisticaMaterialColetaInternacionalService;
import br.org.cancer.redome.workup.service.IPedidoWorkupService;
import br.org.cancer.redome.workup.service.IPrescricaoService;
import br.org.cancer.redome.workup.service.IResultadoWorkupService;
import br.org.cancer.redome.workup.service.IUsuarioService;
import br.org.cancer.redome.workup.service.impl.custom.AbstractService;

/**
 * Classe de acesso as funcionalidades que envolvem a entidade Prescrição.
 * 
 * @author bruno.sousa
 *
 */
@Service
@Transactional
public class AvaliacaoResultadoWorkupService extends AbstractService<AvaliacaoResultadoWorkup, Long> implements
		IAvaliacaoResultadoWorkupService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AvaliacaoResultadoWorkupService.class);

	@Autowired
	private IAvaliacaoResultadoWorkupRepository avaliacaoResultadoWorkupRepository;
	
	@Autowired
	private IResultadoWorkupService resultadoWorkupService;
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	@Lazy(true)
	private ISolicitacaoFeign solicitacaoFeign;
	
	@Autowired
	@Lazy(true)
	private ITarefaHelper tarefaHelper;
	
	@Autowired
	@Lazy(true)
	private INotificacaoFeign notificacaoFeign;
	
	@Autowired
	@Lazy(true)
	private ILogEvolucaoFeign logEvolucaoFeign;
	
	@Autowired
	private IPrescricaoService prescricaoService;
	
	@Autowired
	private IPedidoColetaService pedidoColetaService;
	
	@Autowired
	private IPedidoWorkupService pedidoWorkupService;
	
	@Autowired
	private IPedidoLogisticaMaterialColetaInternacionalService pedidoLogisticaService;
	
	@Override
	public IRepository<AvaliacaoResultadoWorkup, Long> getRepository() {
		return avaliacaoResultadoWorkupRepository;
	}
	
	private AvaliacaoResultadoWorkup obterAvaliacaoResultadoWorkupPeloResultadoWorkupId(Long idResultadoWorkup) {		
		return avaliacaoResultadoWorkupRepository.findByResultadoWorkupId(idResultadoWorkup).orElse(null);
	}
	
	
	@Override
	public String prosseguirResultadoWorkupInternacional(Long idResultadoWorkup, String justificativa) throws Exception {
		ResultadoWorkup resultadoWorkup = resultadoWorkupService.obterResultadoWorkupInternacional(idResultadoWorkup);
		validarAvaliacaoResultadoWorkupExistente(resultadoWorkup.getId());
		
		AvaliacaoResultadoWorkup avaliacao = AvaliacaoResultadoWorkup.builder()
				.resultadoWorkup(resultadoWorkup)
				.prosseguir(true)
				.usuarioResponsavel(usuarioService.obterIdUsuarioLogado())
				.justificativa(justificativa)
				.build();
		
		save(avaliacao);
		
		pedidoWorkupService.finalizarWorkupInternacional(resultadoWorkup.getPedidoWorkup().getId());
		
		FasesWorkup faseWorkup = obterFaseWorkupPelaAutorizacaoDoPaciente(resultadoWorkup.getPedidoWorkup().getSolicitacao()); 
		
		SolicitacaoWorkupDTO solicitacao = solicitacaoFeign.atualizarFaseWorkup(resultadoWorkup.getPedidoWorkup().getSolicitacao(), 
				faseWorkup.getId() );
		fecharTarefaAvaliarResultadoWorkupInternacional(resultadoWorkup.getId(), solicitacao);
		criarLogEvolucao(TipoLogEvolucao.AVALIACAO_RESULTADO_PEDIDO_WORKUP_APROVADO_PARA_DOADOR, solicitacao.getMatch().getBusca().getPaciente().getRmr(), solicitacao.getMatch().getDoador());

		if (faseWorkup.equals(FasesWorkup.AGUARDANDO_LOGISTICA_COLETA_INTERNACIONAL)) {
			PedidoColeta pedidoColeta = pedidoColetaService.criarPedidoColeta(solicitacao);
			pedidoLogisticaService.criarPedidoLogisticaMaterialColetaInternacional(pedidoColeta.getId());
			return "avaliacao.resulado.workup.confirmada.pedido.coleta";
		}
		else {
			cancelarTarefaAutorizacaoPaciente(solicitacao);
			criarTarefaInformarAutorizacaoPaciente(solicitacao);
			return "avaliacao.resulado.workup.confirmada.autorizacao.paciente";
		}
		
	}
		
	private void criarTarefaInformarAutorizacaoPaciente(SolicitacaoWorkupDTO solicitacao) {
		Long rmr = solicitacao.getMatch().getBusca().getPaciente().getRmr();
		final Prescricao prescricao = prescricaoService.obterPrescricaoPorSolicitacao(solicitacao.getId());		
		
		ProcessoDTO processo = new ProcessoDTO(TipoProcesso.BUSCA);
		processo.setRmr(rmr);
		
		TarefaDTO tarefa = TarefaDTO.builder()
				.processo(processo)
				.tipoTarefa(new TipoTarefaDTO(TiposTarefa.INFORMAR_AUTORIZACAO_PACIENTE.getId()))
				.perfilResponsavel(Perfis.MEDICO_TRANSPLANTADOR.getId())
				.relacaoEntidade(prescricao.getId())
				.relacaoParceiro(prescricao.getCentroTransplante())
				.status(StatusTarefa.ABERTA.getId())
				.build();
		
		tarefaHelper.criarTarefa(tarefa);		
	}

	private void cancelarTarefaAutorizacaoPaciente(SolicitacaoWorkupDTO solicitacao) {
		final Long rmr = solicitacao.getMatch().getBusca().getPaciente().getRmr();
		final Prescricao prescricao = prescricaoService.obterPrescricaoPorSolicitacao(solicitacao.getId());
		
		ListaTarefaDTO filtroDTO = ListaTarefaDTO.builder()
				.idsTiposTarefa(Arrays.asList(TiposTarefa.AUTORIZACAO_PACIENTE.getId()))
				.statusTarefa(Arrays.asList(StatusTarefa.ABERTA.getId()))				
				.parceiros(Arrays.asList(prescricao.getCentroTransplante()))
				.rmr(rmr)
				.relacaoEntidadeId(prescricao.getId())
				.tipoProcesso(TipoProcesso.BUSCA.getId())
				.build();
		try {
			List<TarefaDTO> tarefas = tarefaHelper.listarTarefas(filtroDTO); 
			if (CollectionUtils.isNotEmpty(tarefas)) {
				tarefaHelper.cancelarTarefa(tarefas.get(0).getId(), false);	
			}			
		} catch (JsonProcessingException e) {
			LOGGER.error("cancelarTarefaAutorizacaoPaciente", e);
			new BusinessException("erro.interno");
		}
		
	}

	private FasesWorkup obterFaseWorkupPelaAutorizacaoDoPaciente(Long idSolicitacao) {
		Prescricao prescricao = prescricaoService.obterPrescricaoPorSolicitacao(idSolicitacao);
		boolean possuiAutorizacaoPaciente = false;
		if (CollectionUtils.isNotEmpty(prescricao.getArquivosPrescricao())) {
			possuiAutorizacaoPaciente = prescricao.getArquivosPrescricao().stream().anyMatch(arquivo -> arquivo.getAutorizacaoPaciente());
		}
		if (possuiAutorizacaoPaciente) {
			return FasesWorkup.AGUARDANDO_LOGISTICA_COLETA_INTERNACIONAL;
		}
		
		return FasesWorkup.AGUARDANDO_AUTORIZACAO_PACIENTE;
				
	}	
	
	private void criarLogEvolucao(TipoLogEvolucao tipoLogEvolucao,Long rmr, DoadorDTO doador) {
		
		String[] parametros = {doador.getIdentificacao()};
		
		logEvolucaoFeign.criarLogEvolucao(CriarLogEvolucaoDTO.builder()
				.tipo(tipoLogEvolucao.name())
				.rmr(rmr)
				.parametros(parametros )
				.build());
				
	}
	
	private void fecharTarefaAvaliarResultadoWorkupInternacional(Long idResultadoWorkup, SolicitacaoWorkupDTO solicitacao) {
		Long rmr = solicitacao.getMatch().getBusca().getPaciente().getRmr();
		
		ListaTarefaDTO filtroDTO = ListaTarefaDTO.builder()
				.idsTiposTarefa(Arrays.asList(TiposTarefa.AVALIAR_RESULTADO_WORKUP_INTERNACIONAL.getId()))
				.statusTarefa(Arrays.asList(StatusTarefa.ATRIBUIDA.getId()))
				.idUsuarioLogado(usuarioService.obterIdUsuarioLogado())
				.rmr(rmr)
				.relacaoEntidadeId(idResultadoWorkup)
				.tipoProcesso(TipoProcesso.BUSCA.getId())
				.build();
		
		try {
			tarefaHelper.encerrarTarefa(filtroDTO, false);
		} catch (JsonProcessingException e) {
			LOGGER.error("fecharTarefaAvaliarResultadoWorkup", e);
			throw new BusinessException("erro.interno");
		}
		
	}
	
	@Override
	public void naoProsseguirResultadoWorkupInternacional(Long idResultadoWorkup, String justificativa) {

		ResultadoWorkup resultadoWorkup = resultadoWorkupService.obterResultadoWorkupInternacional(idResultadoWorkup);		
		validarAvaliacaoResultadoWorkupExistente(resultadoWorkup.getId());
		
		if (StringUtils.isEmpty(justificativa)) {
			throw new BusinessException("erro.avaliacao.resultado.workup.nao.pressegur.sem.justificativa");
		}
		
		AvaliacaoResultadoWorkup avaliacao = AvaliacaoResultadoWorkup.builder()
				.resultadoWorkup(resultadoWorkup)
				.usuarioResponsavel(usuarioService.obterIdUsuarioLogado())
				.justificativa(justificativa)
				.build();
		
		save(avaliacao);
		
		pedidoWorkupService.cancelarWorkupInternacional(resultadoWorkup.getPedidoWorkup().getId());
		SolicitacaoWorkupDTO solicitacao = solicitacaoFeign.obterSolicitacaoWorkup(resultadoWorkup.getPedidoWorkup().getSolicitacao());
		
		fecharTarefaAvaliarResultadoWorkupInternacional(resultadoWorkup.getId(), solicitacao);
		cancelarTarefaAutorizacaoPaciente(solicitacao);
		criarLogEvolucao(TipoLogEvolucao.AVALIACAO_RESULTADO_PEDIDO_WORKUP_NAO_APROVADO_PARA_DOADOR, solicitacao.getMatch().getBusca().getPaciente().getRmr(), solicitacao.getMatch().getDoador());
		
	}

	@Override
	public AvaliacaoResultadoWorkup obterAvaliacaoResultadoWorkupPorId(Long idAvaliacao) {
		if (idAvaliacao == null) {
			throw new BusinessException("erro.id.nulo");
		}

		return avaliacaoResultadoWorkupRepository.findById(idAvaliacao).orElseThrow(() -> new BusinessException("erro.nao.existe", "Avaliação do resultado de workup") );
	}

	private void validarAvaliacaoResultadoWorkupExistente(Long idResultadoWorkup) {
		AvaliacaoResultadoWorkup avaliacaoResultadoWorkupEncontrada = obterAvaliacaoResultadoWorkupPeloResultadoWorkupId(idResultadoWorkup);
		if (avaliacaoResultadoWorkupEncontrada != null) {
			throw new BusinessException("erro.avaliacao.resultado.workup.ja.cadastrado");
		}
	}
	
	
	@Override
	public String prosseguirResultadoWorkupNacional(Long idResultadoWorkup) throws Exception {
		ResultadoWorkup resultadoWorkup = resultadoWorkupService.obterResultadoWorkupNacional(idResultadoWorkup);
		validarAvaliacaoResultadoWorkupExistente(resultadoWorkup.getId());
		
		AvaliacaoResultadoWorkup avaliacao = AvaliacaoResultadoWorkup.builder()
				.resultadoWorkup(resultadoWorkup)
				.prosseguir(true)
				.usuarioResponsavel(usuarioService.obterIdUsuarioLogado())
				.build();

		save(avaliacao);
		
		pedidoWorkupService.finalizarWorkupNacional(resultadoWorkup.getPedidoWorkup().getId());
		
		SolicitacaoWorkupDTO solicitacao = solicitacaoFeign.atualizarFaseWorkup(resultadoWorkup.getPedidoWorkup().getSolicitacao(), 
				FasesWorkup.AGUARDANDO_AGENDAMENTO_COLETA.getId() );
		
		fecharTarefaAvaliarResultadoWorkupNacional(resultadoWorkup.getId(), solicitacao);
		criarLogEvolucao(TipoLogEvolucao.AVALIACAO_RESULTADO_PEDIDO_WORKUP_APROVADO_PARA_DOADOR, solicitacao.getMatch().getBusca().getPaciente().getRmr(), solicitacao.getMatch().getDoador());

		pedidoColetaService.criarPedidoColeta(solicitacao);
		return "avaliacao.resulado.workup.confirmada.pedido.coleta";
	}
	
	@Override
	public void naoProsseguirResultadoWorkupNacional(Long idResultadoWorkup, String justificativa) {

		ResultadoWorkup resultadoWorkup = resultadoWorkupService.obterResultadoWorkupNacional(idResultadoWorkup);		
		validarAvaliacaoResultadoWorkupExistente(resultadoWorkup.getId());
		
		if (StringUtils.isEmpty(justificativa)) {
			throw new BusinessException("erro.avaliacao.resultado.workup.nao.pressegur.sem.justificativa");
		}
		
		AvaliacaoResultadoWorkup avaliacao = AvaliacaoResultadoWorkup.builder()
				.resultadoWorkup(resultadoWorkup)
				.usuarioResponsavel(usuarioService.obterIdUsuarioLogado())
				.justificativa(justificativa)
				.build();
		
		save(avaliacao);
		
		pedidoWorkupService.cancelarWorkupNacional(resultadoWorkup.getPedidoWorkup().getId());
		SolicitacaoWorkupDTO solicitacao = solicitacaoFeign.obterSolicitacaoWorkup(resultadoWorkup.getPedidoWorkup().getSolicitacao());
		
		fecharTarefaAvaliarResultadoWorkupNacional(resultadoWorkup.getId(), solicitacao);
		criarLogEvolucao(TipoLogEvolucao.AVALIACAO_RESULTADO_PEDIDO_WORKUP_NAO_APROVADO_PARA_DOADOR, solicitacao.getMatch().getBusca().getPaciente().getRmr(), solicitacao.getMatch().getDoador());
		criarTarefaAvaliarPedidoColeta(avaliacao.getId(), solicitacao);
	}
	
	private void criarTarefaAvaliarPedidoColeta(Long idAvaliacaoResultadoWorkup, SolicitacaoWorkupDTO solicitacao) {
		Long rmr = solicitacao.getMatch().getBusca().getPaciente().getRmr();
		
		ProcessoDTO processo = new ProcessoDTO(TipoProcesso.BUSCA);
		processo.setRmr(rmr);
		
		TarefaDTO tarefa = TarefaDTO.builder()
				.processo(processo)
				.tipoTarefa(new TipoTarefaDTO(TiposTarefa.AVALIAR_PEDIDO_COLETA.getId()))
				.perfilResponsavel(Perfis.MEDICO_REDOME.getId())
				.relacaoEntidade(idAvaliacaoResultadoWorkup)
				.status(StatusTarefa.ABERTA.getId())
				.build();
		
		tarefaHelper.criarTarefa(tarefa);		
		
	}
	
	private void fecharTarefaAvaliarResultadoWorkupNacional(Long idResultadoWorkup, SolicitacaoWorkupDTO solicitacao) {
		Long rmr = solicitacao.getMatch().getBusca().getPaciente().getRmr();
		
		ListaTarefaDTO filtroDTO = ListaTarefaDTO.builder()
				.idsTiposTarefa(Arrays.asList(TiposTarefa.AVALIAR_RESULTADO_WORKUP_NACIONAL.getId()))
				.statusTarefa(Arrays.asList(StatusTarefa.ATRIBUIDA.getId()))
				.idUsuarioLogado(usuarioService.obterIdUsuarioLogado())
				.rmr(rmr)
				.relacaoEntidadeId(idResultadoWorkup)
				.tipoProcesso(TipoProcesso.BUSCA.getId())
				.build();
		
		try {
			tarefaHelper.encerrarTarefa(filtroDTO, false);
		} catch (JsonProcessingException e) {
			LOGGER.error("fecharTarefaAvaliarResultadoWorkup", e);
			throw new BusinessException("erro.interno");
		}
	}

	@Override
	public String prosseguirColetaInviavelResultadoWorkupNacional(Long idResultadoWorkup, String justificativa) throws Exception {
		ResultadoWorkup resultadoWorkup = resultadoWorkupService.obterResultadoWorkupNacional(idResultadoWorkup);
		validarAvaliacaoResultadoWorkupExistente(resultadoWorkup.getId());
		
		AvaliacaoResultadoWorkup avaliacao = AvaliacaoResultadoWorkup.builder()
				.resultadoWorkup(resultadoWorkup)
				.prosseguir(true)
				.usuarioResponsavel(usuarioService.obterIdUsuarioLogado())
				.justificativa(justificativa)
				.build();

		save(avaliacao);
		
		SolicitacaoWorkupDTO solicitacao = solicitacaoFeign.atualizarFaseWorkup(resultadoWorkup.getPedidoWorkup().getSolicitacao(), 
				FasesWorkup.AGUARDANDO_AVALIACAO_PEDIDO_COLETA.getId() );
		
		fecharTarefaAvaliarResultadoWorkupNacional(resultadoWorkup.getId(), solicitacao);
		criarLogEvolucao(TipoLogEvolucao.AVALIACAO_PEDIDO_COLETA, solicitacao.getMatch().getBusca().getPaciente().getRmr(), solicitacao.getMatch().getDoador());

		criarTarefaAvaliarPedidoColeta(avaliacao.getId(), solicitacao);
		return "avaliacao.resulado.workup.confirmada.aprovacao.medico.redome";
	}

	@Override
	public AvaliacaoResultadoWorkup obterAvaliacaoResultadoWorkupPorSolicitacao(Long idSolicitacao) {
		return avaliacaoResultadoWorkupRepository.findBySolicitacaoId(idSolicitacao).orElse(null);
	}
	
	@Override
	public AvaliacaoResultadoWorkupDTO obterAvaliacaoResultadoWorkupNacionalPorId(Long idAvaliacaoResultadoWorkup) {
		final AvaliacaoResultadoWorkup avaliacao  = obterAvaliacaoResultadoWorkupPorId(idAvaliacaoResultadoWorkup);
		final Prescricao prescricao = prescricaoService.obterPrescricaoPorSolicitacao(avaliacao.getResultadoWorkup().getPedidoWorkup().getSolicitacao());
		return AvaliacaoResultadoWorkupDTO.builder()
				    .idPrescricao(prescricao.getId())
					.avaliacaoProsseguir(avaliacao.getProsseguir())
					.justificativaAvaliacaoResultadoWorkup(avaliacao.getJustificativa())
					.resultadoWorkupDTO(new ResultadoWorkupNacionalDTO(avaliacao.getResultadoWorkup()))
					.build();
	}

}
