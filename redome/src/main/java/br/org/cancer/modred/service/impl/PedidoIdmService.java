package br.org.cancer.modred.service.impl;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.model.CordaoInternacional;
import br.org.cancer.modred.model.Doador;
import br.org.cancer.modred.model.DoadorInternacional;
import br.org.cancer.modred.model.DoadorNacional;
import br.org.cancer.modred.model.Paciente;
import br.org.cancer.modred.model.Pagamento;
import br.org.cancer.modred.model.PedidoIdm;
import br.org.cancer.modred.model.Solicitacao;
import br.org.cancer.modred.model.StatusPedidoIdm;
import br.org.cancer.modred.model.annotations.log.CreateLog;
import br.org.cancer.modred.model.domain.StatusPedidosIdm;
import br.org.cancer.modred.model.domain.StatusTarefa;
import br.org.cancer.modred.model.domain.TipoLogEvolucao;
import br.org.cancer.modred.model.domain.TiposDoador;
import br.org.cancer.modred.model.domain.TiposServico;
import br.org.cancer.modred.model.domain.TiposTarefa;
import br.org.cancer.modred.persistence.IPedidoIdmRepository;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.service.IArquivoPedidoIdmService;
import br.org.cancer.modred.service.IPacienteService;
import br.org.cancer.modred.service.IPedidoIdmService;
import br.org.cancer.modred.service.ISolicitacaoService;
import br.org.cancer.modred.service.IStorageService;
import br.org.cancer.modred.service.IUsuarioService;
import br.org.cancer.modred.service.impl.config.Equals;
import br.org.cancer.modred.service.impl.invocation.AbstractLoggingService;

/**
 * Classe de implementação das funcionalidades envolvendo a entidade PedidoIdm.
 * 
 * @author bruno.sousa
 * 
 */
@Service
@Transactional
public class PedidoIdmService extends AbstractLoggingService<PedidoIdm, Long> implements IPedidoIdmService { 

	private static final Logger LOGGER = LoggerFactory.getLogger(PedidoIdmService.class);

	@Autowired
	private IPedidoIdmRepository pedidoIdmRepository;

	@Autowired
	private IUsuarioService usuarioService;

	@Autowired
	private IArquivoPedidoIdmService arquivoPedidoIdmService;

	@Autowired
	private IPacienteService pacienteService;

	@Autowired
	private ISolicitacaoService solicitacaoService;

	@SuppressWarnings("rawtypes")
	@Autowired
	private IStorageService storageService;

	@Override
	public IRepository<PedidoIdm, Long> getRepository() {
		return pedidoIdmRepository;
	}

	@Override
	public Paciente obterPaciente(PedidoIdm pedidoIdm) {
		pedidoIdm = findById(pedidoIdm.getId());
		return pacienteService.obterPacientePorSolicitacao(pedidoIdm.getSolicitacao().getId());
	}

	@Override
	public String[] obterParametros(PedidoIdm pedidoIdm) {
		String[] retorno = { null };
		pedidoIdm = findById(pedidoIdm.getId());
		if (pedidoIdm.getSolicitacao().getMatch() != null && pedidoIdm.getSolicitacao().getMatch().getDoador() != null) {
			Doador doador = pedidoIdm.getSolicitacao().getMatch().getDoador();
			if (doador instanceof DoadorNacional) {
				retorno[0] = ( (DoadorNacional) doador ).getDmr().toString();
			}
			else if (doador instanceof DoadorInternacional) {
				retorno[0] = ((DoadorInternacional)doador).getIdRegistro();
			}
			else if (doador instanceof CordaoInternacional) {
				retorno[0] = ((CordaoInternacional)doador).getIdRegistro();
			}
		}
		return retorno;
	}

	/**
	 * {@inheritDoc}
	 */
	@CreateLog(TipoLogEvolucao.SOLICITADO_PEDIDO_EXAME_IDM_PARA_DOADOR_INTERNACIONAL)
	@Override
	public void criarPedidoIdmInternacional(Solicitacao solicitacao) {
		PedidoIdm pedidoIdm = new PedidoIdm();
		pedidoIdm.setDataCriacao(LocalDateTime.now());
		pedidoIdm.setSolicitacao(solicitacao);
		pedidoIdm.setStatusPedidoIdm(new StatusPedidoIdm(StatusPedidosIdm.AGUARDANDO_RESULTADO.getId()));
		save(pedidoIdm);

		criarTarefaCadastrarResultadoIdmInternacional(pedidoIdm);
		if (TiposDoador.INTERNACIONAL.equals(solicitacao.getMatch().getDoador().getTipoDoador())) {
			criarPagamentosPedidoIdmInternacional(pedidoIdm);
		}
	}

	private void criarPagamentosPedidoIdmInternacional(PedidoIdm pedidoIdm) {
		Pagamento pagamento = TiposServico.PEDIDO_IDM
				.getConfiguracao().obterPagamento()
				.comMatch(pedidoIdm.getSolicitacao().getMatch().getId())
				.comObjetoRelacionado(pedidoIdm.getId())
				.comRegistro(pedidoIdm.getSolicitacao().getMatch().getDoador().getRegistroOrigem().getId())
				.apply();
		if (pagamento != null) {
			throw new BusinessException("erro.mensagem.pagamento.ja.existe");
		}
		TiposServico.PEDIDO_IDM.getConfiguracao().criarPagamento()
				.comMatch(pedidoIdm.getSolicitacao().getMatch().getId())
				.comObjetoRelacionado(pedidoIdm.getId())
				.comRegistro(pedidoIdm.getSolicitacao().getMatch().getDoador().getRegistroOrigem().getId())
				.apply();

		TiposServico.AMOSTRA_IDM.getConfiguracao().criarPagamento()
				.comMatch(pedidoIdm.getSolicitacao().getMatch().getId())
				.comObjetoRelacionado(pedidoIdm.getId())
				.comRegistro(pedidoIdm.getSolicitacao().getMatch().getDoador().getRegistroOrigem().getId())
				.apply();
	}

	private void criarTarefaCadastrarResultadoIdmInternacional(PedidoIdm pedidoIdm) {
		final Long rmr = pedidoIdm.getSolicitacao().getMatch().getBusca().getPaciente().getRmr();
		
		TiposTarefa.CADASTRAR_RESULTADO_EXAME_IDM_INTERNACIONAL.getConfiguracao()
				.criarTarefa()
				.comRmr(rmr)
				.comObjetoRelacionado(pedidoIdm.getId())
				.apply();
	}

	@Override
	public PedidoIdm findBySolicitacaoId(Long idSolicitacao) {
		return pedidoIdmRepository.findBySolicitacaoId(idSolicitacao);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.org.cancer.modred.service.IPedidoIdmService#cancelarPedido(br.org.cancer.modred.model.Solicitacao)
	 */
	@Override
	@CreateLog(TipoLogEvolucao.PEDIDO_EXAME_IDM_CANCELADO_PARA_DOADOR_INTERNACIONAL)
	public void cancelarPedido(Solicitacao solicitacao, LocalDate dataCancelamento) {

		Solicitacao solicitacaoLoc = solicitacaoService.findById(solicitacao.getId());
		if (TiposDoador.INTERNACIONAL.getId().equals(solicitacaoLoc.getMatch().getDoador().getTipoDoador().getId()) || 
				TiposDoador.CORDAO_INTERNACIONAL.getId().equals(solicitacaoLoc.getMatch().getDoador().getTipoDoador().getId())) {
			PedidoIdm pedidoIdm = findOne(new Equals<Long>("solicitacao.id", solicitacaoLoc.getId()),
					new Equals<Long>("solicitacao.tipoSolicitacao.id", solicitacaoLoc.getTipoSolicitacao().getId()));

			if (pedidoIdm != null && StatusPedidosIdm.AGUARDANDO_RESULTADO.getId().equals(pedidoIdm.getStatusPedidoIdm()
					.getId())) {
				cancelarPedido(pedidoIdm, dataCancelamento);
			}
		}

	}

	/**
	 * Fecha a tarefa aberta para o perfil do analista de busca . Só é possível cancelar a tarefa se ela ainda não estiver sido
	 * finalizada.
	 * 
	 * @param pedidoExame - pedido de exame envolvido no procedimento.
	 * @return TRUE se foi possível cancelar a tarefa.
	 */
	private boolean cancelarTarefaAnalistaBuscaSePossivel(PedidoIdm pedidoIdm) {
		final Paciente paciente = pedidoIdm.getSolicitacao().getMatch().getBusca().getPaciente();

		TarefaDTO tarefaCadastrarResultadoExameInternacional = TiposTarefa.CADASTRAR_RESULTADO_EXAME_IDM_INTERNACIONAL
				.getConfiguracao()
				.obterTarefa()
				.comRmr(paciente.getRmr())
				.comStatus(Arrays.asList(StatusTarefa.ABERTA, StatusTarefa.ATRIBUIDA))
				.comObjetoRelacionado(pedidoIdm.getId())
				.comUsuario(usuarioService.obterUsuarioLogado())
				.apply();

		if (tarefaCadastrarResultadoExameInternacional != null && tarefaCadastrarResultadoExameInternacional.emAndamento()) {
			tarefaCadastrarResultadoExameInternacional.getTipo().getConfiguracao().cancelarTarefa()
					.comTarefa(tarefaCadastrarResultadoExameInternacional.getId())
					.apply();
			return true;
		}
		return false;
	}

	@CreateLog(TipoLogEvolucao.RESULTADO_EXAME_IDM_PARA_DOADOR)
	@Override
	public void salvarResultadoPedidoIdmDoadorInternacional(Long idPedidoIdm, List<MultipartFile> listaArquivosLaudo) {
		PedidoIdm pedidoIdm = findById(idPedidoIdm);
		pedidoIdm.setStatusPedidoIdm(new StatusPedidoIdm(StatusPedidosIdm.RESULTADO_CADASTRADO.getId()));
		pedidoIdm = save(pedidoIdm);

		fecharTarefaParaCadastrarResultadoIDM(pedidoIdm);

		try {
			arquivoPedidoIdmService.salvarLaudo(listaArquivosLaudo, pedidoIdm);
		}
		catch (IOException e) {
			LOGGER.error("Ocorreu um erro ao subir o laudo de resultado do IDM.");
			throw new BusinessException(e.getMessage());
		}
	}

	private void fecharTarefaParaCadastrarResultadoIDM(PedidoIdm pedidoIdm) {
		final Long rmr = pedidoIdm.getSolicitacao().getMatch().getBusca().getPaciente().getRmr();

		TiposTarefa.CADASTRAR_RESULTADO_EXAME_IDM_INTERNACIONAL.getConfiguracao()
				.fecharTarefa()
				.comRmr(rmr)
				.comStatus(Arrays.asList(StatusTarefa.ATRIBUIDA))
				.comObjetoRelacionado(pedidoIdm.getId())
				.apply();
	}

	@Override
	public TarefaDTO obterTarefaPorPedidoEmAberto(PedidoIdm pedidoIdm) {
		final Long rmr = pedidoIdm.getSolicitacao().getMatch().getBusca().getPaciente().getRmr();

		return TiposTarefa.CADASTRAR_RESULTADO_EXAME_IDM_INTERNACIONAL.getConfiguracao()
				.obterTarefa()
				.comRmr(rmr)
				.comStatus(Arrays.asList(StatusTarefa.ABERTA))
				.comObjetoRelacionado(pedidoIdm.getId())
				.apply();
	}

	private void cancelarPedido(PedidoIdm pedidoIdm, LocalDate dataCancelamento) {
		pedidoIdm.setStatusPedidoIdm(new StatusPedidoIdm(StatusPedidosIdm.CANCELADO.getId()));
		pedidoIdm.setDataCancelamento(dataCancelamento);
		pedidoIdmRepository.save(pedidoIdm);
		cancelarTarefaAnalistaBuscaSePossivel(pedidoIdm);

		TiposServico.AMOSTRA_IDM.getConfiguracao()
				.cancelarPagamento()
				.comMatch(pedidoIdm.getSolicitacao().getMatch().getId())
				.comObjetoRelacionado(pedidoIdm.getId())
				.comRegistro(pedidoIdm.getSolicitacao().getMatch().getDoador().getRegistroOrigem().getId())
				.apply();
	}

	@Override
	public List<PedidoIdm> listarPedidosPorDoador(Long idDoador) {
		return find(new Equals<Long>("solicitacao.match.doador.id", idDoador));
	}

	@Override
	public File downloadArquivoIdm(Long idPedido) {
		PedidoIdm pedidoIdm = this.findById(idPedido);
		if(pedidoIdm.getArquivosResultado().isEmpty()){
			throw new BusinessException("erro.arquivo.nao.localizado");
		}
		return storageService.download(pedidoIdm.getArquivosResultado().get(0).getCaminho());
	}

}
