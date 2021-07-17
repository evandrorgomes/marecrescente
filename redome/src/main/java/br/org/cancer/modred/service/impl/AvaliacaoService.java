package br.org.cancer.modred.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.controller.dto.AvaliacaoDTO;
import br.org.cancer.modred.controller.dto.PacienteAvaliacaoTarefaDTO;
import br.org.cancer.modred.controller.page.AvaliacaoJsonPage;
import br.org.cancer.modred.controller.page.PendenciaJsonPage;
import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.model.Avaliacao;
import br.org.cancer.modred.model.CentroTransplante;
import br.org.cancer.modred.model.LogEvolucao;
import br.org.cancer.modred.model.Medico;
import br.org.cancer.modred.model.Paciente;
import br.org.cancer.modred.model.Pendencia;
import br.org.cancer.modred.model.StatusPendencia;
import br.org.cancer.modred.model.annotations.log.CreateLog;
import br.org.cancer.modred.model.domain.Perfis;
import br.org.cancer.modred.model.domain.StatusAvaliacao;
import br.org.cancer.modred.model.domain.StatusPacientes;
import br.org.cancer.modred.model.domain.StatusPendencias;
import br.org.cancer.modred.model.domain.StatusTarefa;
import br.org.cancer.modred.model.domain.TipoLogEvolucao;
import br.org.cancer.modred.model.domain.TiposTarefa;
import br.org.cancer.modred.model.security.Perfil;
import br.org.cancer.modred.model.security.Usuario;
import br.org.cancer.modred.persistence.IAvaliacaoRepository;
import br.org.cancer.modred.persistence.IPendenciaRepository;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.service.IAvaliacaoCamaraTecnicaService;
import br.org.cancer.modred.service.IAvaliacaoService;
import br.org.cancer.modred.service.IConfiguracaoService;
import br.org.cancer.modred.service.IEvolucaoService;
import br.org.cancer.modred.service.IExamePacienteService;
import br.org.cancer.modred.service.IHistoricoStatusPacienteService;
import br.org.cancer.modred.service.IMedicoService;
import br.org.cancer.modred.service.IPacienteService;
import br.org.cancer.modred.service.IPendenciaService;
import br.org.cancer.modred.service.impl.invocation.AbstractLoggingService;
import br.org.cancer.modred.util.AppUtil;
import br.org.cancer.modred.util.CampoMensagem;
import br.org.cancer.modred.util.DateUtils;

/**
 * Classe para de negócios para Avaliação.
 * 
 * @author bruno.sousa
 *
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class AvaliacaoService extends AbstractLoggingService<Avaliacao, Long> implements IAvaliacaoService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AvaliacaoService.class);
	
	@Autowired
	private IPendenciaRepository repository;
	
	@Autowired
	private IAvaliacaoRepository avaliacaoRepository;
	
	private IEvolucaoService evolucaoService;
	
	private IMedicoService medicoService;
	
	private IPendenciaService pendenciaService;

	private IPacienteService pacienteService;
	
	private IExamePacienteService exameService;
	
	private IAvaliacaoCamaraTecnicaService avaliacaoCamaraTecnicaService;
	
	private IConfiguracaoService configuracaoService;
	
	private IHistoricoStatusPacienteService historicoStatusPacienteService;
	
	private final Long tipoTarefaAvaliarPaciente = 5L;

	
	@Override
	public IRepository<Avaliacao, Long> getRepository() {
		return avaliacaoRepository;
	}

	@Override
	public PendenciaJsonPage<Pendencia> listarPendencias(Long avaliacaoId, PageRequest pageRequest) {
		if (avaliacaoId == null) {
			throw new BusinessException("avaliacao.nao.informada.para.listar.pendencias");
		}

		StatusPendencias[] ordenacao = null;

		if (verificarUsuarioLogadoResponsavelPelaAvaliacao(avaliacaoId)) {
			ordenacao = new StatusPendencias[] { StatusPendencias.RESPONDIDA, StatusPendencias.ABERTA,
					StatusPendencias.FECHADA, StatusPendencias.CANCELADA };
		}
		else {
			ordenacao = new StatusPendencias[] { StatusPendencias.ABERTA, StatusPendencias.RESPONDIDA,
					StatusPendencias.FECHADA, StatusPendencias.CANCELADA };
		}

		return repository.findAllCustom(pageRequest, avaliacaoId, ordenacao);
	}

	@Override
	public Avaliacao obterUltimaAvaliacaoPorPaciente(Long rmr) {
		Avaliacao avaliacao = avaliacaoRepository.findLastByPacienteRmrOrderByDataCriacao(
				rmr);

		if (avaliacao == null) {
			throw new BusinessException("avaliacao.erro.obter.ultimo.registro", rmr.toString());
		}

		return avaliacao;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AvaliacaoDTO obterAvaliacaoAtual(Long rmr) {
		AvaliacaoDTO avaliacaoAtual = new AvaliacaoDTO();
		Avaliacao avaliacao = obterUltimaAvaliacaoPorPaciente(rmr);

		if (usuarioService.usuarioLogadoPossuiPerfil(Perfis.MEDICO_AVALIADOR)) {
			avaliacaoAtual.setUltimaEvolucao(evolucaoService.obterUltimaEvolucaoDoPaciente(rmr));
		}

		avaliacaoAtual.setAvaliacaoAtual(avaliacao);

		return avaliacaoAtual;
	}
	

	/**
	 * {@inheritDoc}
	 */
	public Avaliacao obterAvaliacao(Long idAvaliacao) {
		if (idAvaliacao == null) {
			throw new BusinessException("erro.avaliacao.nao.encontrada");
		}
 		Avaliacao avaliacao = avaliacaoRepository.findById(idAvaliacao).orElse(null);
		if (avaliacao == null) {
			throw new BusinessException("erro.avaliacao.nao.encontrada");
		}
		return avaliacao;
	}
	
	private void aprovarPaciente(Long idAvaliacao, Avaliacao avaliacao) {
		LOGGER.info("Inicio da aprovação do paciente pelo avaliador - id da avaliacao: " + idAvaliacao);
		
		try {
			Avaliacao avaliacaoRecuperada = obterAvaliacao(idAvaliacao);		
			pacienteService.verificarPacienteEmObito(avaliacaoRecuperada.getPaciente().getRmr(), true);
			if (verificarNecessidadeCriarAvaliacaoCameraTecnica(avaliacaoRecuperada.getPaciente())) {
				avaliacaoCamaraTecnicaService.criarAvaliacaoInicial(avaliacaoRecuperada.getPaciente());
				historicoStatusPacienteService.adicionarStatusPaciente(StatusPacientes.AGUARDANDO_APROVACAO_CAMARA_TECNICA, avaliacaoRecuperada.getPaciente());				
			}
			else{
				exameService.criarProcessoConferenciaExames(avaliacaoRecuperada.getPaciente());
			}
			fecharTarefaAvaliacaoPaciente(avaliacaoRecuperada.getId(), avaliacaoRecuperada.getPaciente().getRmr());
			avaliacaoRecuperada.setAprovado(true);
			avaliacaoRecuperada.setDataResultado(LocalDateTime.now());
			avaliacaoRecuperada.setStatus(StatusAvaliacao.FECHADA.getId());
			avaliacaoRecuperada.setObservacao(avaliacao.getObservacao());
			avaliacaoRecuperada.getPaciente().setTempoParaTransplante(avaliacao.getPaciente().getTempoParaTransplante());
			
			tratarPendenciasPorAvaliacao(avaliacaoRecuperada);
			save(avaliacaoRecuperada);
			//buscaService.iniciarProcessoDeBusca(avaliacaoRecuperada.getPaciente().getRmr());			
		}
		finally {
			LOGGER.info("Término da aprovação do paciente pelo avaliador - id da avaliacao: " + idAvaliacao);
		}
	}
	
	private boolean verificarNecessidadeCriarAvaliacaoCameraTecnica(Paciente paciente) {
		long anos = ChronoUnit.YEARS.between(paciente.getDataNascimento(), LocalDate.now());
		if( !paciente.getDiagnostico().getCid().getTransplante()
		|| (paciente.getDiagnostico().getCid().getIdadeMinima() !=null && anos < paciente.getDiagnostico().getCid().getIdadeMinima()) 
		|| (paciente.getDiagnostico().getCid().getIdadeMaxima() !=null && anos > paciente.getDiagnostico().getCid().getIdadeMaxima()) ){
			return true;
		}
		return false;
	}
	
	private void fecharTarefaAvaliacaoPaciente(Long idAvaliacao, Long rmr) {
		TiposTarefa.AVALIAR_PACIENTE.getConfiguracao()
			.fecharTarefa()
			.comStatus(Arrays.asList(StatusTarefa.ABERTA, StatusTarefa.ATRIBUIDA))
			.comObjetoRelacionado(idAvaliacao)
			.finalizarProcesso()
			.comRmr(rmr)
			.apply();
	}
	
	private void reprovarPaciente(Long idAvaliacao, Avaliacao avaliacao) {
		LOGGER.info("Inicio da reprovação do paciente pelo avaliador - id da avaliacao: " + idAvaliacao);
		try {
			Avaliacao avaliacaoRecuperada = obterAvaliacao(idAvaliacao);		
			pacienteService.verificarPacienteEmObito(avaliacaoRecuperada.getPaciente().getRmr(), true);
			historicoStatusPacienteService.adicionarStatusPaciente(StatusPacientes.REPROVADO, avaliacaoRecuperada.getPaciente());
			fecharTarefaAvaliacaoPaciente(avaliacaoRecuperada.getId(), avaliacaoRecuperada.getPaciente().getRmr());
			avaliacaoRecuperada.setAprovado(false);
			avaliacaoRecuperada.setDataResultado(LocalDateTime.now());
			avaliacaoRecuperada.setStatus(StatusAvaliacao.FECHADA.getId());
			avaliacaoRecuperada.setObservacao(avaliacao.getObservacao());
			tratarPendenciasPorAvaliacao(avaliacaoRecuperada);
			save(avaliacaoRecuperada);
		}
		finally {
			LOGGER.info("Término da reprovação do paciente pelo avaliador - id da avaliacao: " + idAvaliacao);
		}
	}
	
	@Override
	protected List<CampoMensagem> validateEntity(Avaliacao avaliacao) {
		List<CampoMensagem> campos = super.validateEntity(avaliacao);
		
		if (avaliacao.getAprovado() != null && !avaliacao.getAprovado()) {
			if (avaliacao.getObservacao() == null || "".equals(avaliacao.getObservacao()) ) {
				campos.add(new CampoMensagem("observacao", AppUtil.getMensagem(messageSource, "erro.observacao.obrigatorio")));
			}
		}
		else if (avaliacao.getAprovado() != null && avaliacao.getAprovado()) {
			if (avaliacao.getPaciente() != null && avaliacao.getPaciente().getTempoParaTransplante() == null) {
				campos.add(new CampoMensagem("tempoParaTransplante", AppUtil.getMensagem(messageSource, "erro.tempoParaTransplante.obrigatorio")));
			}
		}
		
		return campos;
	}
	 
	/**
	 * {@inheritDoc}
	 */
	@CreateLog
	@Override
	public void alterarAprovacaoPaciente(Long idAvaliacao, Avaliacao avaliacao) {
		if (avaliacao.getAprovado() != null && avaliacao.getAprovado()) {
			aprovarPaciente(idAvaliacao, avaliacao);			
		}
		else if (avaliacao.getAprovado() != null && !avaliacao.getAprovado()) {
			reprovarPaciente(idAvaliacao, avaliacao);
		}
		else {
			new BusinessException("erro.interno");
		}
		
	}

	private void tratarPendenciasPorAvaliacao(Avaliacao avaliacao) {
		if (Optional.ofNullable(avaliacao.getPendencias()).isPresent()) {
			avaliacao.getPendencias().forEach(p -> {
				if (p.getStatusPendencia().getId().equals(StatusPendencias.ABERTA.getId())) {
					p.setStatusPendencia(new StatusPendencia(StatusPendencias.CANCELADA.getId()));
				}
				else {
					if (p.getStatusPendencia().getId().equals(StatusPendencias.RESPONDIDA
							.getId())) {
						p.setStatusPendencia(new StatusPendencia(StatusPendencias.FECHADA.getId()));
					}
				}
			});
		}
	}

	@Override
	public boolean verificarUsuarioLogadoResponsavelPelaAvaliacao(Long avaliacaoId) {
		Long usuarioLogadoId = usuarioService.obterUsuarioLogadoId();
		return avaliacaoRepository.verificarSeUsuarioResponsavelAvaliacao(avaliacaoId, usuarioLogadoId);
	}

	/**
	 * Método para criar o processo de avaliação para busca e a tarefa para o centro avaliador do paciente, essa tarefa é iniciada
	 * como aberta para futuramente algum avaliador se tornar responsável por ela.
	 * 
	 * @param processoAvaliacao - processo da tarefa
	 * @param paciente - paciente
	 */
	private void criarProcessoAvaliacaoParaBusca(Avaliacao avaliacao, Paciente paciente) {
		TiposTarefa	.AVALIAR_PACIENTE.getConfiguracao()
					.criarTarefa()
					.comRmr(paciente.getRmr())
					.comObjetoRelacionado(avaliacao.getId())
					.comParceiro(paciente.getCentroAvaliador().getId())
					.apply();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.org.cancer.modred.service.IPacienteService#listarAvaliacoesDeUmCentroTransplantador(org.springframework.data.domain.
	 * PageRequest)
	 */
	@Override
	public AvaliacaoJsonPage<PacienteAvaliacaoTarefaDTO> listarAvaliacoesDeUmCentroTransplantador(Long idCentroTransplante, PageRequest paginacao) {
		Usuario usuario = usuarioService.obterUsuarioLogado();
		List<Perfil> perfisUsuarioLogado = usuario.getPerfis();
		final boolean temPerfilAvaliador = perfisUsuarioLogado.stream().anyMatch(perfil -> isPerfilAvaliador(perfil));
		if (!temPerfilAvaliador) {
			throw new BusinessException("erro.mensagem.usuario.nao.avaliador");
		}
		Medico medicoAvaliador = medicoService.obterMedicoPorUsuario(usuario.getId());
		if (medicoAvaliador == null) {
			throw new BusinessException("erro.mensagem.usuario.sem.medico");
		}
		if (usuario.getCentroTransplanteUsuarios() == null || usuario.getCentroTransplanteUsuarios().size() == 0) {
			throw new BusinessException("erro.mensagem.medico.sem.centro.transplantador");
		}
		else {
 			final boolean usuarioPertenceAoCentro = usuario.getCentroTransplanteUsuarios().stream().anyMatch(centro -> idCentroTransplante.equals(centro.getCentroTransplante().getId()));
 			if (!usuarioPertenceAoCentro) {
 				throw new BusinessException("erro.mensagem.medico.nao.pertence.centro.transplantador");
 			}
		}
		
		Page<TarefaDTO> tarefas = TiposTarefa.AVALIAR_PACIENTE.getConfiguracao().listarTarefa()
				.comPaginacao(paginacao)
				.comStatus(Arrays.asList(StatusTarefa.ABERTA))
				.comParceiros(Arrays.asList(idCentroTransplante))
				.apply().getValue();
				
		List<PacienteAvaliacaoTarefaDTO> pacientesASeremAvaliados = tarefas.getContent()
				.stream()
				.map(tarefa ->
		{
					Paciente paciente = pacienteService.findById(tarefa.getProcesso().getPaciente().getRmr());
					PacienteAvaliacaoTarefaDTO pacienteAvaliacaoTarefaDTO = new PacienteAvaliacaoTarefaDTO();
					pacienteAvaliacaoTarefaDTO.setRmr(paciente.getRmr());
					pacienteAvaliacaoTarefaDTO.setNome(paciente.getNome());
					Long idade = ChronoUnit.YEARS.between(paciente.getDataNascimento(), LocalDate
							.now());
					pacienteAvaliacaoTarefaDTO.setIdade(idade.intValue());
					pacienteAvaliacaoTarefaDTO.setTempoCadastro(DateUtils.obterAging(paciente.getDataCadastro()));

					pacienteAvaliacaoTarefaDTO.setCid(paciente.getDiagnostico().getCid());
					return pacienteAvaliacaoTarefaDTO;

				}).collect(Collectors.toList());

		if (CollectionUtils.isNotEmpty(pacientesASeremAvaliados)) {
			return new AvaliacaoJsonPage<PacienteAvaliacaoTarefaDTO>(pacientesASeremAvaliados, paginacao, tarefas
					.getTotalElements());
		}

		return null;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.org.cancer.modred.service.IPacienteService#listarPacientesDoAvaliadorLogado(org.springframework.data.domain.PageRequest)
	 */
	@Override
	public AvaliacaoJsonPage<PacienteAvaliacaoTarefaDTO> listarPacientesDoAvaliadorLogado(Long idCentroTransplante, PageRequest paginacao) {
		final Usuario usuarioAvaliador = usuarioService.obterUsuarioLogado();
		final List<Perfil> perfisUsuarioLogado = usuarioAvaliador.getPerfis();
		final boolean temPerfilAvaliador = perfisUsuarioLogado.stream().anyMatch(perfil -> isPerfilAvaliador(perfil));
		if (!temPerfilAvaliador) {
			throw new BusinessException("erro.mensagem.usuario.nao.avaliador");
		}
		if (usuarioAvaliador.getCentroTransplanteUsuarios() == null || usuarioAvaliador.getCentroTransplanteUsuarios().size() == 0) {
			throw new BusinessException("erro.mensagem.medico.sem.centro.transplantador");
		}
		else {
 			final boolean usuarioPertenceAoCentro = usuarioAvaliador.getCentroTransplanteUsuarios().stream()
 					.anyMatch(centro -> idCentroTransplante.equals(centro.getCentroTransplante().getId()));
 			if (!usuarioPertenceAoCentro) {
 				throw new BusinessException("erro.mensagem.medico.nao.pertence.centro.transplantador");
 			}
		}
		
		return listarTarefasPacientesDoAvaliadorLogado(usuarioAvaliador, idCentroTransplante, paginacao);
	}

	/**
	 * Lista as tarefas de avaliação para um determinado avaliador.
	 * 
	 * @param usuarioAvaliador
	 * @param paginacao
	 * @return
	 */
	private AvaliacaoJsonPage<PacienteAvaliacaoTarefaDTO> listarTarefasPacientesDoAvaliadorLogado(Usuario usuarioAvaliador, 
			Long idCentroTransplante, PageRequest paginacao) {
		
		Page<TarefaDTO> tarefas = TiposTarefa.AVALIAR_PACIENTE.getConfiguracao().listarTarefa()
				.comPaginacao(paginacao)
				.comStatus(Arrays.asList(StatusTarefa.ATRIBUIDA))
				.comParceiros(Arrays.asList(idCentroTransplante))
				.comUsuario(usuarioAvaliador)
				.apply().getValue();

		List<PacienteAvaliacaoTarefaDTO> pacientesASeremAvaliados = tarefas.getContent()
				.stream()
				.map(tarefa -> {
					Paciente paciente = pacienteService.findById(tarefa.getProcesso().getPaciente().getRmr());
					PacienteAvaliacaoTarefaDTO pacienteAvaliacaoTarefaDTO = new PacienteAvaliacaoTarefaDTO();
					pacienteAvaliacaoTarefaDTO.setRmr(paciente.getRmr());
					pacienteAvaliacaoTarefaDTO.setNome(paciente.getNome());
					Long idade = ChronoUnit.YEARS.between(paciente.getDataNascimento(), LocalDate.now());
					pacienteAvaliacaoTarefaDTO.setIdade(idade.intValue());
					pacienteAvaliacaoTarefaDTO.setTempoCadastro(DateUtils.obterAging(paciente.getDataCadastro()));
					pacienteAvaliacaoTarefaDTO.setCid(paciente.getDiagnostico().getCid());
					return pacienteAvaliacaoTarefaDTO;

				}).collect(Collectors.toList());

		if (CollectionUtils.isNotEmpty(pacientesASeremAvaliados)) {
			return new AvaliacaoJsonPage<PacienteAvaliacaoTarefaDTO>(pacientesASeremAvaliados, paginacao, tarefas
					.getTotalElements());
		}

		return null;
	}

	/**
	 * Método para informar se o avaliador está logado.
	 * 
	 * @param perfil
	 * @return
	 */
	private boolean isPerfilAvaliador(Perfil perfil) {
		return perfil.getId() == Perfis.MEDICO_AVALIADOR.getId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.org.cancer.modred.service.IAvaliacaoService#atribuirAvaliacaoAoAvaliador(java.lang.Long)
	 */
	@Override
	public void atribuirAvaliacaoAoAvaliador(Long avaliacaoId) {
		Avaliacao avaliacaoAtual = avaliacaoRepository.findById(avaliacaoId).get();
		
		final Usuario usuarioAvaliador = usuarioService.obterUsuarioLogado();
		final List<Perfil> perfisUsuarioLogado = usuarioAvaliador.getPerfis();
		final boolean temPerfilAvaliador = perfisUsuarioLogado.stream().anyMatch(perfil -> isPerfilAvaliador(perfil));
		if (!temPerfilAvaliador) {
			throw new BusinessException("erro.mensagem.usuario.nao.avaliador");
		}
		if (usuarioAvaliador.getCentroTransplanteUsuarios() == null || usuarioAvaliador.getCentroTransplanteUsuarios().size() == 0) {
			throw new BusinessException("erro.mensagem.medico.sem.centro.transplantador");
		}
		else {
 			final boolean usuarioPertenceAoCentro = usuarioAvaliador.getCentroTransplanteUsuarios().stream()
 					.anyMatch(centro -> avaliacaoAtual.getCentroAvaliador().getId().equals(centro.getCentroTransplante().getId()));
 			if (!usuarioPertenceAoCentro) {
 				throw new BusinessException("erro.mensagem.medico.nao.pertence.centro.transplantador");
 			}
		}
		
		Medico medico = medicoService.obterMedicoPorUsuario(usuarioService.obterUsuarioLogado()
				.getId());
		if (avaliacaoAtual.getPaciente().getMedicoResponsavel().equals(medico)) {
			throw new BusinessException("erro.mensagem.avaliacao.mesmo.medico.paciente");
		}
		
		TarefaDTO tarefa = TiposTarefa.AVALIAR_PACIENTE.getConfiguracao().obterTarefa()
			.comRmr(avaliacaoAtual.getPaciente().getRmr())
			.comStatus(Arrays.asList(StatusTarefa.ABERTA))
			.comObjetoRelacionado(avaliacaoAtual.getId())
			.comParceiros(Arrays.asList(avaliacaoAtual.getCentroAvaliador().getId()))
			.apply();
		
		if (tarefa == null) {
			
		}
		
		tarefa = TiposTarefa.AVALIAR_PACIENTE.getConfiguracao().atribuirTarefa()
				.comTarefa(tarefa)
				.comUsuarioLogado()
				.apply();
							
		avaliacaoAtual.setMedicoResponsavel(medico);
		avaliacaoRepository.save(avaliacaoAtual);

	}

	/**
	 * Método para cancelar a avaliação, suas tarefas e pendências.
	 * 
	 * @param avaliacao - avaliacao a ser cancelada
	 * @param rmr - identificador do paciente
	 */
	@Override
	public void cancelarAvaliacaoEProcessoDeAvaliacao(Avaliacao avaliacao, Long rmr) {

		cancelarAvaliacao(avaliacao);
		pendenciaService.cancelarPendencias(avaliacao.getPendencias());

	}

	private void cancelarAvaliacao(Avaliacao avaliacao) {
		avaliacao.setStatus(StatusAvaliacao.CANCELADA.getId());
		avaliacao.setMotivoCancelamento(AppUtil.getMensagem(messageSource, "avaliacao.motivo.cancelamento"));

		avaliacaoRepository.save(avaliacao);
		cancelarTarefaDeAvaliacao(avaliacao);
	}

	private void cancelarTarefaDeAvaliacao(Avaliacao avaliacao) {
		TarefaDTO tarefa  = TiposTarefa.AVALIAR_PACIENTE.getConfiguracao().obterTarefa()
			.comObjetoRelacionado(avaliacao.getId())
			.comStatus(Arrays.asList(StatusTarefa.ABERTA, StatusTarefa.ATRIBUIDA))
			.paraOutroUsuario(true)
			.comRmr(avaliacao.getPaciente().getRmr())
			.apply();
		if (tarefa != null) {
			TiposTarefa.AVALIAR_PACIENTE.getConfiguracao().cancelarTarefa()
				.comTarefa(tarefa.getId())
				.cancelarProcesso()
				.apply();
		}
		
	}

	@Override
	public void criarAvaliacaoInicial(Paciente paciente) {
		Avaliacao avaliacao = avaliacaoRepository.save(montarAvaliacaoInicial(paciente));
		criarProcessoAvaliacaoParaBusca(avaliacao, paciente);
		LOGGER.info("Avaliação criada para o paciente {}", paciente.getRmr());

	}

	private Avaliacao montarAvaliacaoInicial(Paciente paciente) {
		Avaliacao avaliacao = new Avaliacao();
		avaliacao.setStatus(StatusAvaliacao.ABERTA.getId());
		avaliacao.setCentroAvaliador(paciente.getCentroAvaliador());
		avaliacao.setPaciente(paciente);
		avaliacao.setDataCriacao(LocalDateTime.now());

		paciente.setAvaliacoes(new ArrayList<>());
		paciente.getAvaliacoes().add(avaliacao);

		return avaliacao;
	}

	@Override
	public Paciente obterPaciente(Avaliacao avaliacao) {
		return avaliacao.getPaciente();
	}

	@Override
	public String[] obterParametros(Avaliacao avaliacao) {
		return avaliacao.getPaciente().getRmr().toString().split(";");
	}
	
	@Override
	public LogEvolucao criarLog(Avaliacao avaliacao, TipoLogEvolucao tipoLog, Perfis[] perfisExcluidos){
		LogEvolucao log = super.criarLog(avaliacao, tipoLog, perfisExcluidos);
		if(Boolean.TRUE.equals(avaliacao.getAprovado())){
			log.setTipoEvento(TipoLogEvolucao.AVALIACAO_APROVADA_PARA_PACIENTE);
		}
		else {
			log.setTipoEvento(TipoLogEvolucao.AVALIACAO_REPROVADA_PARA_PACIENTE);
		}
		return log;
	}
	
	@Override
	public boolean verificarSeAvaliacaoEmAndamento(Long rmr) {
		Avaliacao ultimaAvaliacao = obterUltimaAvaliacaoPorPaciente(rmr);
		if (Optional.ofNullable(ultimaAvaliacao).isPresent()) {
			return ultimaAvaliacao.emAndamento();
		}
		LOGGER.error("Não foi encontrado avaliação para o paciente " + rmr + " possível inconsistência de base.");
		throw new BusinessException("erro.interno");
	}
	
	@Override
	public boolean verificarSeAvaliacaoAprovada(Long rmr) {
		Avaliacao ultimaAvaliacao = obterUltimaAvaliacaoPorPaciente(rmr);
		if (Optional.ofNullable(ultimaAvaliacao).isPresent()) {
			return ultimaAvaliacao.aprovada();
		}
		LOGGER.error("Não foi encontrado avaliação para o paciente " + rmr + " possível inconsistência de base.");
		throw new BusinessException("erro.interno");
	}
	
	@Override
	public void alterarCentroAvaliador(Long idAvaliador, CentroTransplante novoCentroAvaliador){
		Avaliacao avaliacao = findById(idAvaliador);
		final Paciente paciente = avaliacao.getPaciente();
		
		TiposTarefa.AVALIAR_PACIENTE.getConfiguracao()
			.cancelarTarefa()
			.comRmr(paciente.getRmr())
			.comStatus(Arrays.asList(StatusTarefa.ABERTA, StatusTarefa.ATRIBUIDA))
			.comObjetoRelacionado(avaliacao.getId())
			.apply();
		
		avaliacao.setMedicoResponsavel(null);
		avaliacao.setCentroAvaliador(novoCentroAvaliador);
		save(avaliacao);
		
		TiposTarefa.AVALIAR_PACIENTE.getConfiguracao()
			.criarTarefa()
			.comRmr(paciente.getRmr())
			.comObjetoRelacionado(avaliacao.getId())
			.comParceiro(novoCentroAvaliador.getId())
			.apply();
		
		pacienteService.transferirCentroAvaliador(paciente.getRmr(), novoCentroAvaliador);
	}

	/**
	 * @param evolucaoService the evolucaoService to set
	 */
	@Autowired
	public void setEvolucaoService(IEvolucaoService evolucaoService) {
		this.evolucaoService = evolucaoService;
	}

	/**
	 * @param medicoService the medicoService to set
	 */
	@Autowired
	public void setMedicoService(IMedicoService medicoService) {
		this.medicoService = medicoService;
	}

	/**
	 * @param pendenciaService the pendenciaService to set
	 */
	@Autowired
	public void setPendenciaService(IPendenciaService pendenciaService) {
		this.pendenciaService = pendenciaService;
	}

	/**
	 * @param pacienteService the pacienteService to set
	 */
	@Autowired
	public void setPacienteService(IPacienteService pacienteService) {
		this.pacienteService = pacienteService;
	}

	/**
	 * @param exameService the exameService to set
	 */
	@Autowired
	public void setExameService(IExamePacienteService exameService) {
		this.exameService = exameService;
	}

	/**
	 * @param avaliacaoCamaraTecnicaService the avaliacaoCamaraTecnicaService to set
	 */
	@Autowired
	public void setAvaliacaoCamaraTecnicaService(IAvaliacaoCamaraTecnicaService avaliacaoCamaraTecnicaService) {
		this.avaliacaoCamaraTecnicaService = avaliacaoCamaraTecnicaService;
	}

	/**
	 * @param configuracaoService the configuracaoService to set
	 */
	@Autowired
	public void setConfiguracaoService(IConfiguracaoService configuracaoService) {
		this.configuracaoService = configuracaoService;
	}

	/**
	 * @param historicoStatusPacienteService the historicoStatusPacienteService to set
	 */
	@Autowired
	public void setHistoricoStatusPacienteService(IHistoricoStatusPacienteService historicoStatusPacienteService) {
		this.historicoStatusPacienteService = historicoStatusPacienteService;
	}
	

}
