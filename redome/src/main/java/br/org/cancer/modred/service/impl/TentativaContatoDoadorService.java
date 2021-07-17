package br.org.cancer.modred.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.controller.view.TarefaView;
import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.model.Configuracao;
import br.org.cancer.modred.model.ContatoTelefonicoDoador;
import br.org.cancer.modred.model.Doador;
import br.org.cancer.modred.model.DoadorNacional;
import br.org.cancer.modred.model.Paciente;
import br.org.cancer.modred.model.PedidoContato;
import br.org.cancer.modred.model.Solicitacao;
import br.org.cancer.modred.model.TentativaContatoDoador;
import br.org.cancer.modred.model.TipoSolicitacao;
import br.org.cancer.modred.model.Turno;
import br.org.cancer.modred.model.domain.Periodos;
import br.org.cancer.modred.model.domain.StatusTarefa;
import br.org.cancer.modred.model.domain.TiposSolicitacao;
import br.org.cancer.modred.model.domain.TiposTarefa;
import br.org.cancer.modred.model.security.Usuario;
import br.org.cancer.modred.persistence.IConfiguracaoRepository;
import br.org.cancer.modred.persistence.ITentativaContatoDoadorRepository;
import br.org.cancer.modred.persistence.ITurnoRepository;
import br.org.cancer.modred.service.IContatoTelefonicoDoadorService;
import br.org.cancer.modred.service.IPedidoContatoService;
import br.org.cancer.modred.service.IPedidoExameService;
import br.org.cancer.modred.service.ITentativaContatoDoadorService;
import br.org.cancer.modred.service.IUsuarioService;

/**
 * Classe de implementação das funcionalidades envolvendo a entidade TentativaContatoDoadorService.
 * 
 * @author Pizão
 *
 */
@Service
@Transactional
public class TentativaContatoDoadorService implements ITentativaContatoDoadorService {
	
	private static final Logger LOG = LoggerFactory.getLogger(TentativaContatoDoadorService.class);

	private static final String DEVE_ADICIONAR_USUARIO_AGENDAMENTO = "S";

	@Autowired
	private ITentativaContatoDoadorRepository repository;

	private IUsuarioService usuarioService;

	private IContatoTelefonicoDoadorService contatoTelefonicoDoadorService;

	private IPedidoExameService pedidoExameService;
	
	private IPedidoContatoService pedidoContatoService;

	@Autowired
	private IConfiguracaoRepository configuracaoRepository;
	
	@Autowired
	private ITurnoRepository turnoRepository;


	@Override
	public void finalizarTentativaContatoECriarNovaTentativa(Long idTentativaContatoDoador, Long idContatoTelefone, LocalDate dataAgendamento, LocalTime horaInicio, LocalTime horaFim, String atribuirUsuario) {
		TentativaContatoDoador tentativaBase = repository.findById(idTentativaContatoDoador).get();
				
		TarefaDTO tarefaFinalizada = finalizarTentaticaContato(tentativaBase);
		
		criarTentativaParaProximoPeriodo(tarefaFinalizada, tentativaBase.getPedidoContato(), idContatoTelefone, dataAgendamento, horaInicio, horaFim, atribuirUsuario);
				
	}
	
	@Override
	public TarefaDTO finalizarTentaticaContato(TentativaContatoDoador tentativaContatoDoador) {
		tentativaContatoDoador.setFinalizada(true);
		tentativaContatoDoador.setUsuario(usuarioService.obterUsuarioLogado());
		
		repository.save(tentativaContatoDoador);
		
		return finalizarTarefa(tentativaContatoDoador);
	}

	@Override
	public void finalizarTentaticaContatoPassivo(TentativaContatoDoador tentativaContatoDoador) {
		tentativaContatoDoador.setFinalizada(true);
		tentativaContatoDoador.setUsuario(usuarioService.obterUsuarioLogado());
		
		repository.save(tentativaContatoDoador);
	}
	
	private void criarTentativaParaProximoPeriodo(TarefaDTO tarefaFinalizada, PedidoContato pedidoContato, Long idContatoTelefone,
			LocalDate dataAgendamento, LocalTime horaInicio, LocalTime horaFim, String atribuirUsuario) {
		
		if (podeCriarNovaTentativa(pedidoContato)) {
			TentativaContatoDoador novaTentativaContato = criarTentativaContato(pedidoContato, idContatoTelefone, dataAgendamento, horaInicio, horaFim );
			salvarTentativa(novaTentativaContato);
		
			criarTarefaAgendadaPorPeriodo(tarefaFinalizada, novaTentativaContato, atribuirUsuario);
			
		}
		else {
			determinarProximaEtapa(pedidoContato);
		}
	}
	
	private void determinarProximaEtapa(PedidoContato pedidoContato) {
		pedidoContatoService.finalizarPedidoContatoAutomaticamente(pedidoContato.getId());
		
		final TipoSolicitacao tipoSolicitacao = pedidoContato.getSolicitacao().getTipoSolicitacao();
		if (TiposSolicitacao.FASE_2.getId().equals(tipoSolicitacao.getId())) {
			pedidoExameService.criarPedidoDoadorNacional(pedidoContato.getSolicitacao());
		}
		else {
			agendarSMS(pedidoContato);
		}
	}

	private Boolean podeCriarNovaTentativa(PedidoContato pedidoContato) {
		
		List<TentativaContatoDoador> tentativasDeContatoPorDoador = repository
				.findByPedidoContatoIdOrderByIdDesc(pedidoContato.getId());
		
		Integer qtTentativasRealizadas = tentativasDeContatoPorDoador.size();
		List<Configuracao> configuracoes = obterConfiguracaoPorFase(pedidoContato);
		LocalDateTime dataCriacao = pedidoContato.getDataCriacao();
		long dias = ChronoUnit.DAYS.between(dataCriacao, LocalDateTime.now());

		Integer quantidadeMaximaTentativas = obterQuantidadeMaximaTentativas(configuracoes);
		Integer quantidadeMinimaTentativas = obterQuantidadeMinimaTentativas(configuracoes);
		Integer quantidadeMaximaDias = obterQuantidadeMaximaDias(configuracoes);

		if (quantidadeMaximaDias == 0) {
			return qtTentativasRealizadas < quantidadeMaximaTentativas;
		}
		else if (quantidadeMaximaTentativas == 0 || quantidadeMinimaTentativas == 0) {
			return dias < quantidadeMaximaDias;
		}
		else {
			return (qtTentativasRealizadas < quantidadeMaximaTentativas && dias <= quantidadeMaximaDias) ||
					(dias > quantidadeMaximaDias && qtTentativasRealizadas < quantidadeMinimaTentativas);
		}
		
	}
	
	private Integer obterQuantidadeMaximaDias(List<Configuracao> configuracoes) {
		LOG.info("Obter quantidade máxima de dias para contato de doador.");
		OptionalInt quantidade = configuracoes.stream()
			.filter(configuracao -> Configuracao.QUANTIDADE_MAXIMA_DIAS_CONTATO_FASE_2.equals(configuracao.getChave()) ||
					Configuracao.QUANTIDADE_MAXIMA_DIAS_CONTATO_FASE_3.equals(configuracao.getChave()))
			.mapToInt(configuracao -> Integer.parseInt(configuracao.getValor()))
			.findFirst();
		if (quantidade.isPresent()) {
			return quantidade.getAsInt();
		}
					
		throw new BusinessException("erro.interno");
	}
	
	private Integer obterQuantidadeMaximaTentativas(List<Configuracao> configuracoes) {
		LOG.info("Obter quantidade máxima de tentativas para contato de doador.");
		OptionalInt quantidade = configuracoes.stream()
			.filter(configuracao -> Configuracao.QUANTIDADE_MAXIMA_TENTATIVAS_CONTATO_FASE_2.equals(configuracao.getChave()) ||
					Configuracao.QUANTIDADE_MAXIMA_TENTATIVAS_CONTATO_FASE_3.equals(configuracao.getChave()))
			.mapToInt(configuracao -> Integer.parseInt(configuracao.getValor()))
			.findFirst();
		if (quantidade.isPresent()) {
			return quantidade.getAsInt();
		}
			
		throw new BusinessException("erro.interno");
	}
	
	private Integer obterQuantidadeMinimaTentativas(List<Configuracao> configuracoes) {
		LOG.info("Obter quantidade minima de tentativas para contato de doador.");
		OptionalInt quantidade = configuracoes.stream()
			.filter(configuracao -> Configuracao.QUANTIDADE_MINIMA_TENTATIVAS_CONTATO_FASE_2.equals(configuracao.getChave()) ||
					Configuracao.QUANTIDADE_MINIMA_TENTATIVAS_CONTATO_FASE_3.equals(configuracao.getChave()))
			.mapToInt(configuracao -> Integer.parseInt(configuracao.getValor()))
			.findFirst();
		if (quantidade.isPresent()) {
			return quantidade.getAsInt();
		}
			
		throw new BusinessException("erro.interno");
	}
	

	private TarefaDTO finalizarTarefa(TentativaContatoDoador tentativa) {
		TiposTarefa tipoTarefa = obterTipoTarefaPorSolicitacao(tentativa.getPedidoContato().getSolicitacao());
		
		return tipoTarefa.getConfiguracao().fecharTarefa()
			.comRmr(tentativa.getPedidoContato().getSolicitacao().getMatch().getBusca().getPaciente().getRmr())
			.comStatus(Arrays.asList(StatusTarefa.ATRIBUIDA))
			.comUsuario(usuarioService.obterUsuarioLogado())
			.comObjetoRelacionado(tentativa.getId())
			.semAgendamento(true)
			.apply();		
		
	}

	private List<Configuracao> obterConfiguracaoPorFase(PedidoContato pedido) {
		String quantidadeMaximaTentativasChave = "";
		String quantidadeMinimaTentativasChave = "";
		String quantidadeDiasChave = "";

		if (pedido.getSolicitacao()
				.getTipoSolicitacao()
				.getId()
				.equals(TiposSolicitacao.FASE_2.getId())) {
			quantidadeMaximaTentativasChave = Configuracao.QUANTIDADE_MAXIMA_TENTATIVAS_CONTATO_FASE_2;
			quantidadeMinimaTentativasChave = Configuracao.QUANTIDADE_MINIMA_TENTATIVAS_CONTATO_FASE_2;
			quantidadeDiasChave = Configuracao.QUANTIDADE_MAXIMA_DIAS_CONTATO_FASE_2;
		}
		else {
			quantidadeMaximaTentativasChave = Configuracao.QUANTIDADE_MAXIMA_TENTATIVAS_CONTATO_FASE_3;
			quantidadeMinimaTentativasChave = Configuracao.QUANTIDADE_MINIMA_TENTATIVAS_CONTATO_FASE_3;
			quantidadeDiasChave = Configuracao.QUANTIDADE_MAXIMA_DIAS_CONTATO_FASE_3;
		}

		return configuracaoRepository.findByChaveIn(new String[] { quantidadeMaximaTentativasChave,
				quantidadeMinimaTentativasChave,
				quantidadeDiasChave });		
	}
	
	private TarefaDTO criarTarefaAgendadaPorPeriodo(TarefaDTO ultimaTarefa, TentativaContatoDoador tentativaDeContatoPorDoador, String atribuirUsuario) {
		LOG.info("Criando tarefa agendada por periodo (turno) para contato de doador.");
		
		TiposTarefa tipoTarefa = obterTipoTarefaPorSolicitacao(tentativaDeContatoPorDoador.getPedidoContato().getSolicitacao());
		Usuario usuarioAgendamento = deveAtribuirUsuarioParaAgendamento(atribuirUsuario)?this.usuarioService.obterUsuarioLogado():null; 
		if (tentativaDeContatoPorDoador.getDataAgendamento() == null && tentativaDeContatoPorDoador.getHoraInicioAgendamento() == null) {
			Turno turnoLigacao = turnoRepository.buscarTurnoPorHora(tentativaDeContatoPorDoador.getDataCriacao());
			Turno turnoAtual = obterTurnoAtual(ultimaTarefa.getHoraInicio(), ultimaTarefa.getHoraFim(), ultimaTarefa
					.getInclusivoExclusivo(), ultimaTarefa.getDataInicio());
	
			Turno turnoParacontato = obterTurnoParaContato(turnoAtual, turnoLigacao);		
	
			if (turnoParacontato != null) {
				
				if (turnoParacontato.getInclusivoExclusivo() == null) {
					return tipoTarefa.getConfiguracao().criarTarefa()
							.comObjetoRelacionado(tentativaDeContatoPorDoador.getId())
							.comProcessoId(ultimaTarefa.getProcesso().getId())
							.comOUsuarioParaAgendamento(usuarioAgendamento)
							.apply();
				}
				else if (!turnoParacontato.getInclusivoExclusivo()) {
					return tipoTarefa.getConfiguracao().criarTarefa()
							.comObjetoRelacionado(tentativaDeContatoPorDoador.getId())
							.comProcessoId(ultimaTarefa.getProcesso().getId())
							.comHoraInicio(turnoParacontato.getHoraInicio())
							.comHoraFim(turnoParacontato.getHoraFim())
							.comOUsuarioParaAgendamento(usuarioAgendamento)
							.inclusive()
							.apply();
				}
				
				return tipoTarefa.getConfiguracao().criarTarefa()
						.comObjetoRelacionado(tentativaDeContatoPorDoador.getId())
						.comProcessoId(ultimaTarefa.getProcesso().getId())
						.comHoraInicio(turnoParacontato.getHoraInicio())
						.comHoraFim(turnoParacontato.getHoraFim())
						.comOUsuarioParaAgendamento(usuarioAgendamento)
						.exclusive()
						.apply();
			}
			
		}
		
		return tipoTarefa.getConfiguracao().criarTarefa()
				.comObjetoRelacionado(tentativaDeContatoPorDoador.getId())
				.comProcessoId(ultimaTarefa.getProcesso().getId())
				.comDataInicio(tentativaDeContatoPorDoador.getDataAgendamento() != null ? tentativaDeContatoPorDoador.getDataAgendamento().atTime(0, 0) : null)
				.comHoraInicio(tentativaDeContatoPorDoador.getHoraInicioAgendamento())
				.comHoraFim(tentativaDeContatoPorDoador.getHoraFimAgendamento())
				.comOUsuarioParaAgendamento(usuarioAgendamento)
				.agendada()
				.apply();

	}

	private boolean deveAtribuirUsuarioParaAgendamento(String atribuirUsuario) {
		return atribuirUsuario.equals(DEVE_ADICIONAR_USUARIO_AGENDAMENTO);
	}

	private Turno obterTurnoAtual(LocalDateTime horaInicio, LocalDateTime horaFim, Boolean inclusivoExclusivo,
			LocalDateTime dataInicio) {
		Turno turno = null;
		if (horaInicio != null && horaFim != null && dataInicio == null) {
			turno = turnoRepository.findByHoraInicioAndHoraFimAndInclusivoExclusivo(horaInicio, horaFim, inclusivoExclusivo);
		}
		else {
			turno = new Turno(Periodos.QUALQUER.getId());
		}

		return turno;

	}

	private Turno obterTurnoParaContato(Turno turnoAtual, Turno turnoLigacao) {
		Turno turno = null;
		if (turnoAtual.getId().equals(Periodos.QUALQUER.getId())) {
			if (turnoLigacao.getId().equals(Periodos.MANHA.getId())) {
				turno = turnoRepository.findById(Periodos.NAO_MANHA.getId()).get();
			}
			else
				if (turnoLigacao.getId().equals(Periodos.TARDE.getId())) {
					turno = turnoRepository.findById(Periodos.NAO_TARDE.getId()).get();
				}
				else
					if (turnoLigacao.getId().equals(Periodos.NOITE.getId())) {
						turno = turnoRepository.findById(Periodos.NAO_NOITE.getId()).get();
					}
		}
		else
			if (turnoAtual.getId().equals(Periodos.MANHA.getId()) ||
					turnoAtual.getId().equals(Periodos.TARDE.getId()) ||
					turnoAtual.getId().equals(Periodos.NOITE.getId())) {
				turno = new Turno(Periodos.QUALQUER.getId());
			}
			else
				if (turnoAtual.getId().equals(Periodos.NAO_MANHA.getId())) {
					if (turnoLigacao.getId().equals(Periodos.TARDE.getId())) {
						turno = turnoRepository.findById(Periodos.NOITE.getId()).get();
					}
					else
						if (turnoLigacao.getId().equals(Periodos.NOITE.getId())) {
							turno = turnoRepository.findById(Periodos.TARDE.getId()).get();
						}
				}
				else
					if (turnoAtual.getId().equals(Periodos.NAO_TARDE.getId())) {
						if (turnoLigacao.getId().equals(Periodos.MANHA.getId())) {
							turno = turnoRepository.findById(Periodos.NOITE.getId()).get();
						}
						else
							if (turnoLigacao.getId().equals(Periodos.NOITE.getId())) {
								turno = turnoRepository.findById(Periodos.MANHA.getId()).get();
							}
					}
					else
						if (turnoAtual.getId().equals(Periodos.NAO_NOITE.getId())) {
							if (turnoLigacao.getId().equals(Periodos.MANHA.getId())) {
								turno = turnoRepository.findById(Periodos.TARDE.getId()).get();
							}
							else
								if (turnoLigacao.getId().equals(Periodos.TARDE.getId())) {
									turno = turnoRepository.findById(Periodos.MANHA.getId()).get();
								}
						}

		return turno;
	}

	private TiposTarefa obterTipoTarefaPorSolicitacao(Solicitacao solicitacao) {
		if (solicitacao.getTipoSolicitacao().getId().equals(TiposSolicitacao.FASE_2.getId())) {
			return TiposTarefa.CONTACTAR_FASE_2;
			
		}

		return TiposTarefa.CONTACTAR_FASE_3;
	}

	/**
	 * Cria a tarefa Contato Fase 2, associado ao processo de Busca, aberta para essa interação.
	 * 
	 * @param pedido pedido de contato associado ao processo.
	 * @param paciente paciente associado a solicitação.
	 * @param doador doador associado a solicitação.
	 */
	private void agendarSMS(PedidoContato pedido) {
		final Doador doador = pedido.getSolicitacao().getMatch().getDoador();
		final Paciente paciente = pedido.getSolicitacao().getMatch().getBusca().getPaciente();
		ContatoTelefonicoDoador celularSms = contatoTelefonicoDoadorService.obterTelefoneCelularParaSMS(doador.getId());

		if (celularSms != null) {
			TiposTarefa.SMS.getConfiguracao().criarTarefa()
			   .comRmr(paciente.getRmr())
			   .comDescricao(celularSms.retornarFormatadoParaSms())
			   .comObjetoRelacionado(pedido.getId())
			   .apply();
		}
	}

	/**
	 * Cria a tentativa de contato .
	 * 
	 * @param pedidoContato - pedido de contato para criar a tentativa.
	 * @return TentativaContatoDoador - uma tentativa de contato.
	 */
	@Override
	public TentativaContatoDoador criarTentativaContato(PedidoContato pedidoContato, Long idContatoTelefone, LocalDate dataAgendamento, LocalTime horaInicio, LocalTime horaFim) {
		
		if (horaInicio != null && horaFim != null && !horaInicio.isBefore(horaFim)) {
			throw new BusinessException("tentativacontatodoador.error.datainicial_maior_que_datafinal");
		}
		TentativaContatoDoador tentativaContato = new TentativaContatoDoador(pedidoContato);
		
		if (idContatoTelefone != null) {
			final DoadorNacional doador = (DoadorNacional) pedidoContato.getSolicitacao().getMatch().getDoador();
			Optional<ContatoTelefonicoDoador> optionalContatoTelefone = doador.getContatosTelefonicos().stream()
				.filter(telefone -> telefone.getId().equals(idContatoTelefone))
				.findAny();
			if (optionalContatoTelefone.isPresent()) {
				tentativaContato.setContatoTelefonicoDoador(optionalContatoTelefone.get());
			}
			else {
				throw new BusinessException("tentativacontatodoador.error.agendamento.telefone.nao.encontrado");
			}
		}
		
		tentativaContato.setDataAgendamento(dataAgendamento);
		tentativaContato.setHoraInicioAgendamento(dataAgendamento != null && horaInicio != null ? horaInicio.atDate(dataAgendamento) : null);
		tentativaContato.setHoraFimAgendamento(dataAgendamento != null && horaFim != null ? horaFim.atDate(dataAgendamento) : null);
		repository.save(tentativaContato);
		
		return tentativaContato;
		
	}

	@Override
	public TentativaContatoDoador obterTentativaPorId(Long tentativaContatoId) {
		return repository.findById(tentativaContatoId).orElseThrow(() ->  new BusinessException("mensagem.nenhuma.registro.encontrado"));
	}

	@Override
	public List<TentativaContatoDoador> obterPorPedido(Long idPedido) {
		return repository.findByPedidoContatoId(idPedido);
	}

	@Override
	public void salvarTentativa(TentativaContatoDoador tentativa) {
		repository.save(tentativa);
	}

	@Override
	public void cancelarTentativasDeContato(PedidoContato pedido) {
		List<TentativaContatoDoador> tentativas = repository.findByPedidoContatoId(pedido.getId());
		TiposTarefa tipoTarefa = obterTipoTarefaPorSolicitacao(pedido.getSolicitacao());
		
		final Long rmr = pedido.getSolicitacao().getMatch().getBusca().getPaciente().getRmr();
		
		tentativas.stream()
			.filter(tentativa -> !tentativa.getFinalizada())
			.forEach(tentativa -> {
				tentativa.setFinalizada(true);
				repository.save(tentativa);
				
				tipoTarefa.getConfiguracao().cancelarTarefa()
				    .comRmr(rmr)
					.comObjetoRelacionado(tentativa.getId())
					.comStatus(Arrays.asList(StatusTarefa.ABERTA, StatusTarefa.ATRIBUIDA))
					.apply();
			});		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public MappingJacksonValue atribuirTarefa(Long idTentativaContato, Long idTipoTarefa) {
		
		TiposTarefa tipoTarefa = TiposTarefa.valueOf(idTipoTarefa);
		if (tipoTarefa == null) {
			throw new BusinessException("erro.mensagem.tarefa.tipo.invalido");			
		}
		TentativaContatoDoador tentativaContato = obterTentativaPorId(idTentativaContato);
		if (tentativaContato == null) {
			throw new BusinessException("erro.pedido.nao.encontrado");
		}
		Paciente paciente = tentativaContato.getPedidoContato().getSolicitacao().getMatch().getBusca().getPaciente();
		
		TarefaDTO tarefa = tipoTarefa.getConfiguracao().obterTarefa()				
				.comRmr(paciente.getRmr())				
				.comObjetoRelacionado(tentativaContato.getId())
				.comStatus(Arrays.asList(StatusTarefa.ABERTA, StatusTarefa.ATRIBUIDA))
				.apply();
		if (tarefa.getStatusTarefa().getId().equals(StatusTarefa.ATRIBUIDA.getId())) {
			tipoTarefa.getConfiguracao().removerAtribuicaoTarefa()
				.comTarefa(tarefa.getId())
				.apply();
		}
		
		tarefa = tipoTarefa.getConfiguracao().atribuirTarefa()
				.comUsuarioLogado()
				.comTarefa(tarefa)
				.apply();
		
		MappingJacksonValue retorno = new MappingJacksonValue(tarefa);

		if (tipoTarefa.getJsonView() != null) {
			retorno.setSerializationView(tipoTarefa.getJsonView());
		}
		else {
			retorno.setSerializationView(TarefaView.Consultar.class);
		}
		
		return retorno; 
	}
	

	
	
	@Override
	public TentativaContatoDoador obterUltimaTentativaContatoPorPedidoContatoId(Long idPedidoContato) {
		List<TentativaContatoDoador> tentativasDeContatoPorDoador = repository
				.findByPedidoContatoIdOrderByIdDesc(idPedidoContato);
		
		if (tentativasDeContatoPorDoador == null || tentativasDeContatoPorDoador.isEmpty()) {
			throw new BusinessException("erro.interno");
		}
		
		return tentativasDeContatoPorDoador.get(0);
	}
	
	@Override
	public TarefaDTO obterTarefaAssociadaATentativaContatoEStatusTarefa(TentativaContatoDoador tentativaContato, List<StatusTarefa> listStatusTarefa,
			Boolean atribuidoQualquerUsuario, Boolean tarefaSemAgendamento) {
		Long rmr = null;
		if(tentativaContato.getPedidoContato().getSolicitacao() != null) {
			rmr = tentativaContato.getPedidoContato().getSolicitacao().getMatch().getBusca().getPaciente().getRmr();
		}
		else {
			return null;
		}
		
		TiposTarefa tipoTarefa = obterTipoTarefaPorSolicitacao(tentativaContato.getPedidoContato().getSolicitacao());
		
		return tipoTarefa.getConfiguracao().obterTarefa()
					.comRmr(rmr)
					.comObjetoRelacionado(tentativaContato.getId())
					.comStatus(listStatusTarefa)
					.paraOutroUsuario(atribuidoQualquerUsuario)
					.semAgendamento(tarefaSemAgendamento)
					.apply();
	}

	/**
	 * @param pedidoExameService the pedidoExameService to set
	 */
	@Autowired
	public void setPedidoExameService(IPedidoExameService pedidoExameService) {
		this.pedidoExameService = pedidoExameService;
	}

	/**
	 * @param usuarioService the usuarioService to set
	 */
	@Autowired
	public void setUsuarioService(IUsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	/**
	 * @param contatoTelefonicoDoadorService the contatoTelefonicoDoadorService to set
	 */
	@Autowired
	public void setContatoTelefonicoDoadorService(IContatoTelefonicoDoadorService contatoTelefonicoDoadorService) {
		this.contatoTelefonicoDoadorService = contatoTelefonicoDoadorService;
	}

	/**
	 * @param pedidoContatoService the pedidoContatoService to set
	 */
	@Autowired
	public void setPedidoContatoService(IPedidoContatoService pedidoContatoService) {
		this.pedidoContatoService = pedidoContatoService;
	}
	
	@Override
	public Boolean podeFinalizarTentativaContato(Long id) {
		return !podeCriarNovaTentativa(obterTentativaPorId(id).getPedidoContato());
	}
	
	@Override
	public void finalizarTentaticaContato(Long id) {
		determinarProximaEtapa(obterTentativaPorId(id).getPedidoContato());
	}
	

}
