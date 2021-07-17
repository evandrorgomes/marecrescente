package br.org.cancer.redome.tarefa.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.redome.tarefa.dto.ListaTarefaDTO;
import br.org.cancer.redome.tarefa.exception.BusinessException;
import br.org.cancer.redome.tarefa.model.Processo;
import br.org.cancer.redome.tarefa.model.Tarefa;
import br.org.cancer.redome.tarefa.model.TipoTarefa;
import br.org.cancer.redome.tarefa.model.domain.ITiposTarefa;
import br.org.cancer.redome.tarefa.model.domain.StatusProcesso;
import br.org.cancer.redome.tarefa.model.domain.StatusTarefa;
import br.org.cancer.redome.tarefa.model.domain.TipoProcesso;
import br.org.cancer.redome.tarefa.model.domain.TiposTarefa;
import br.org.cancer.redome.tarefa.persistence.IProcessoRepository;
import br.org.cancer.redome.tarefa.persistence.ITarefaRepository;
import br.org.cancer.redome.tarefa.service.IProcessoService;
import br.org.cancer.redome.tarefa.service.ITipoTarefaService;
import br.org.cancer.redome.tarefa.service.IUsuarioService;

/**
 * Implementação da interface que descreve os métodos de negócio que operam sobre o motor de processos da plataforma redome.
 * 
 * @author Thiago Moraes
 *
 */
@Service
@Transactional
public class ProcessoService implements IProcessoService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProcessoService.class);

	@Autowired
	private ITarefaRepository tarefaRepository;
	
	@Autowired
	private IProcessoRepository processoRepository;
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private ITipoTarefaService tipoTarefaService;

	@Override
	public Tarefa criarTarefa(Tarefa tarefa) {

		if (tarefa != null) {
			
			if (tarefa.getTipoTarefa() == null || tarefa.getTipoTarefa().getId() == null || tarefa.getTipoTarefa().getId() <= 0) {
				throw new BusinessException("erro.mensagem.tarefa.tipo.invalido");
			}
			TiposTarefa tiposTarefa = TiposTarefa.valueOf(tarefa.getTipoTarefa().getId());
			if (tiposTarefa.getConfiguracao().isSomenteAgrupador()) {
				throw new RuntimeException("Configuração definida como somente agrupadora não utiliza o método criarTarefa.");
			}
	
			if (tarefa.getDataCriacao() == null) {
				tarefa.setDataCriacao(LocalDateTime.now());
			}
			if (tarefa.getDataAtualizacao() == null) {
				tarefa.setDataAtualizacao(LocalDateTime.now());
			}			
			if (tarefa.getStatus() == null) {
				tarefa.setStatus(StatusTarefa.ABERTA.getId());
			}
			if (tarefa.getPerfilResponsavel() == null && tiposTarefa.getConfiguracao().getPerfil() != null) {
				tarefa.setPerfilResponsavel(tiposTarefa.getConfiguracao().getPerfil().getId());
			}
			if (tarefa.getStatus().equals(StatusTarefa.ABERTA.getId()) && tarefa.getUsuarioResponsavel() != null && tiposTarefa.getConfiguracao().isCriarTarefaAtribuida()) {
				tarefa.setStatus(StatusTarefa.ATRIBUIDA.getId());
			}
			if (tarefa.getStatus().equals(StatusTarefa.ATRIBUIDA.getId()) && tarefa.getUsuarioResponsavel() == null) {
				throw new BusinessException("erro.mensagem.tarefa.atribuida.usuario.invalido");
			}
			if (tarefa.getProcesso() != null && tarefa.getProcesso().getTipo() == null && tiposTarefa.getConfiguracao().getTipoProcesso() != null) {
				tarefa.getProcesso().setTipo(tiposTarefa.getConfiguracao().getTipoProcesso().getId());
			}
			
			Processo processo = obterProcesso(tarefa.getProcesso());
			if (tiposTarefa.getConfiguracao().isIniciarProcesso() && processo == null){
				processo = criarProcesso(tarefa.getProcesso());
			}
			tarefa.setProcesso(processo);
			
			Tarefa tarefaCriada = tarefaRepository.save(tarefa);
			
			criarTarefaFollowUp(tarefaCriada);
			
			if (tarefa.getStatus().equals(StatusTarefa.ATRIBUIDA.getId())) {
				criarTarefaTimeout(tarefaCriada);				
			}

			return tarefaCriada;
		}
		
		throw new BusinessException("erro.mensagem.tarefa.nulo");
		

	}

	@Override
	public Tarefa obterTarefa(Long id) {

		if (id != null) {
			Tarefa tarefa = tarefaRepository.getOne(id);

			if (tarefa != null) {
				return tarefa;
			}
		}
		else {
			throw new BusinessException("erro.mensagem.tarefa.id.invalido");
		}
		throw new BusinessException("mensagem.nenhum.registro.encontrado", "registro");
	}

	@Override
	public List<Tarefa> listarTarefasPorTipoTarefaIdEStatus(Long tipoTarefaId, Long statusId) {
		return tarefaRepository.findByTipoTarefaIdAndStatusOrderById(tipoTarefaId, statusId);
	}
	
	@Override
	public Tarefa fecharTarefa(Tarefa tarefa) {
		return fecharTarefa(tarefa, false);
	}

	@Override
	public Tarefa fecharTarefa(Tarefa tarefa, Boolean finalizarProcesso) {

		if (tarefa != null && tarefa.getId() != null) {

			if (tarefaRepository.atualizarStatusTarefa(tarefa.getId(), StatusTarefa.FEITA.getId(), LocalDateTime.now()) == 0) {
				throw new BusinessException("erro.mensagem.tarefa.status.nao.alterado");
			}
			
			Tarefa tarefaFechada = tarefaRepository.findById(tarefa.getId()).get(); 
			
			encerrarTarefasFollowup(tarefaFechada);
			encerrarTarefaAssociada(tarefaFechada);
			
			if (finalizarProcesso) {
				terminarProcesso(tarefaFechada.getProcesso().getId());
			}
			
			return tarefaFechada;
		}
		else {
			throw new BusinessException("erro.mensagem.tarefa.id.invalido");
		}
	}

	@Override
	public List<Tarefa> listarTarefasAutomaticasEmAberto() {
		return tarefaRepository.listarTarefasAutomaticasEmAberto();
	}

	@Override
	public Tarefa removerAtribuicaoTarefa(Tarefa tarefa) {

		if (tarefa == null || tarefa.getId() == null) {
			throw new BusinessException("erro.mensagem.tarefa.id.invalido");
		}

		if (tarefaRepository.atribuirTarefaUsuario(tarefa.getId(), null
				, StatusTarefa.ABERTA.getId(), LocalDateTime.now()) == 0) {
			throw new BusinessException("erro.mensagem.tarefa.responsavel.nao.alterado");
		}
		
		Tarefa tarefaSemAtribuicao = tarefaRepository.findById(tarefa.getId()).get();
		
		cancelarTarefaAssociada(tarefaSemAtribuicao);
		
		return tarefaSemAtribuicao;

	}

	@Override
	public List<Tarefa> listarTarefasNotificacoesEmAguardandoAgendamento() {
		return tarefaRepository.listarTarefasNotificacoesEmAguardandoAgendamento(LocalDateTime.now());
	}
	
	/* (non-Javadoc)
	 * @see br.org.cancer.modred.redome.job.service.IProcessoService#atualizarTarefa(br.org.cancer.modred.redome.job.model.Tarefa)
	 */
	@Override
	public Tarefa atualizarTarefa(Tarefa tarefa){
		return tarefaRepository.save(tarefa);
	}

	@Override
	public List<Tarefa> listarTarefasFilhas(Long idTipoTarefa, Long idTarefaPai) {
		return tarefaRepository.findByTipoTarefaIdAndTarefaPaiIdOrderByDataCriacao(idTipoTarefa, idTarefaPai);
	}
	
	@Override
	public Tarefa cancelarTarefa(Tarefa tarefa) {
		return cancelarTarefa(tarefa, false);
	}
	
	@Override
	public Tarefa cancelarTarefa(Tarefa tarefa, Boolean cancelarProcesso) {

		if (tarefa != null && tarefa.getId() != null) {

			if (tarefaRepository.atualizarStatusTarefa(tarefa.getId(), StatusTarefa.CANCELADA.getId(), LocalDateTime.now()) == 0) {
				throw new BusinessException("erro.mensagem.tarefa.status.nao.alterado");
			}
			
			Tarefa tarefaCancelada = tarefaRepository.findById(tarefa.getId()).get();
			
			cancelarTarefasFollowup(tarefaCancelada);
			cancelarTarefaAssociada(tarefaCancelada);
			
			if (cancelarProcesso) {
				cancelarProcesso(tarefaCancelada.getProcesso());
			}
			
			return tarefaCancelada;
		}
		else {
			throw new BusinessException("erro.mensagem.tarefa.id.invalido");
		}
	}
	
	@Override
	public Page<Tarefa> listarTarefas(ListaTarefaDTO parametros) {
		if (CollectionUtils.isNotEmpty(parametros.getIdsTiposTarefa()) ) {
			List<Long> idTiposTarefa = parametros.getIdsTiposTarefa().stream()
					.filter(id -> !TiposTarefa.valueOf(id).getConfiguracao().isSomenteAgrupador())
					.collect(Collectors.toList());
			
			parametros.getIdsTiposTarefa().stream()
				.map(id -> TiposTarefa.valueOf(id))
				.filter(tiposTarefa -> tiposTarefa.getConfiguracao().isSomenteAgrupador())				
				.forEach(tiposTarefaAgrupador -> {
					idTiposTarefa.addAll(Stream.of(tiposTarefaAgrupador.getConfiguracao().getAgrupados()).map(tiposTarefa -> tiposTarefa.getId()).collect(Collectors.toList()));
				});
			
			parametros.setIdsTiposTarefa(idTiposTarefa);
		}
		
		return tarefaRepository.listarTarefas(parametros);
	}
	
	@Override
	public Processo obterProcessoEmAndamento(Long rmr, Long idDoador, Long tipoProcesso) {
		List<Processo> processos = processoRepository.listarProcessos(rmr, idDoador, TipoProcesso.valueOf(tipoProcesso), StatusProcesso.ANDAMENTO);
		if (CollectionUtils.isNotEmpty(processos)) {
			return processos.get(0);
		}
		return null;  
	}
	
	private Processo obterProcesso(Long id) {
		return processoRepository.findById(id).orElseThrow(() -> new BusinessException(""));
	}
	
	@Override
	public Processo terminarProcesso(Long id) {
		Processo processo = obterProcesso(id);
		Boolean tarefasEmAberto = tarefaRepository.existsByProcessoIdAndTipoTarefaAutomaticaAndStatus(id, false, StatusTarefa.ABERTA.getId());
		if (tarefasEmAberto) {
			throw new BusinessException("");
		}
		
		processo.setStatus(StatusProcesso.TERMINADO.getId());
		processoRepository.save(processo);
		
		return processo;
		
	}
	
	@Override
	public Tarefa atribuirTarefa(Tarefa tarefa, Long idUsuario) {
		
		if (tarefa == null || tarefa.getId() == null) {
			throw new BusinessException("erro.mensagem.tarefa.id.invalido");
		}
		if (idUsuario == null) {
			throw new BusinessException("erro.mensagem.tarefa.atribuir.usuario.invalido");
		}
		
		Tarefa tarefaEnconrada = tarefaRepository.findById(tarefa.getId()).get();
		
		if (StatusTarefa.ATRIBUIDA.getId().equals(tarefa.getStatus()) && isOutroUsuario(tarefa, idUsuario)) {
			throw new BusinessException("erro.mensagem.tarefa.ja.atribuida");
		}

		if (tarefaRepository.atribuirTarefaUsuario(tarefa.getId(), idUsuario
				, StatusTarefa.ATRIBUIDA.getId(), LocalDateTime.now()) == 0) {
			throw new BusinessException("erro.mensagem.tarefa.responsavel.nao.alterado");
		}
		
		Tarefa tarefaAtribuida = tarefaRepository.findById(tarefa.getId()).get();
		
		criarTarefaTimeout(tarefaAtribuida);
		
		return tarefaAtribuida;
	}
	
	private boolean isOutroUsuario(Tarefa tarefa, Long usuarioResponsavel) {
		return !usuarioResponsavel.equals(tarefa.getUsuarioResponsavel());
	}
	
	@Override
	public Tarefa atribuirTarefa(Long id, Long idUsuario) {
		return atribuirTarefa(obterTarefa(id), idUsuario);
	}
	
	@Override
	public Tarefa fecharTarefa(Long id, Boolean finalizarProcesso) {
		return fecharTarefa(obterTarefa(id), finalizarProcesso);
	}
	
	@Override
	public List<Tarefa> listarTarefasFilhas(Long idTarefaPai, Long idTipoTarefa, Long idStatus) {
		return tarefaRepository.findByTarefaPaiIdAndTipoTarefaIdAndStatus(idTarefaPai, idTipoTarefa, idStatus); 
	}
	
	@Override
	public Tarefa atualizarTarefa(Long id, Tarefa tarefa) {
		Tarefa tarefaRecuperada = obterTarefa(id);
		tarefaRecuperada.setDataAtualizacao(tarefa.getDataAtualizacao());
		tarefaRecuperada.setRelacaoParceiro(tarefa.getRelacaoParceiro());
		tarefaRecuperada.setDescricao(tarefa.getDescricao());
		tarefaRecuperada.setRelacaoEntidade(tarefa.getRelacaoEntidade());
		tarefaRecuperada.setPerfilResponsavel(tarefa.getPerfilResponsavel());
		tarefaRecuperada.setUsuarioResponsavel(tarefa.getUsuarioResponsavel());
		tarefaRecuperada.setDataInicio(tarefa.getDataInicio());
		tarefaRecuperada.setUltimoUsuarioResponsavel(tarefa.getUltimoUsuarioResponsavel());
		
		return atualizarTarefa(tarefa);
	}
	
	@Override
	public Tarefa cancelarTarefa(Long id, Boolean cancelarProcesso) {
		return cancelarTarefa(obterTarefa(id), cancelarProcesso);
	}
	
	@Override
	public Tarefa removerAtribuicaoTarefa(Long id) {
		return removerAtribuicaoTarefa(obterTarefa(id));
	}
	
	@Override
	public Processo criarProcesso(Processo processo) {

		if (processo != null) {

			if (processo.getDataCriacao() == null) {
				processo.setDataCriacao(LocalDateTime.now());
			}
			if (processo.getDataAtualizacao() == null) {
				processo.setDataAtualizacao(LocalDateTime.now());
			}
			
			if (processo.getRmr() == null && processo.getIdDoador() == null) {
				throw new BusinessException("erro.mensagem.processo.sem.dono");
			}
			
			if (processo.getRmr() != null && processo.getIdDoador() != null) {
				throw new BusinessException("erro.mensagem.processo.muitos.dono");
			}

			if (processo.getTipo() == null) {
				throw new BusinessException("erro.mensagem.processo.tipo.invalido");
			}

			if (processo.getStatus() == null) {
				processo.setStatus(StatusProcesso.ANDAMENTO.getId());
			}

			processoRepository.save(processo);
			return processo;
		}
		else {
			throw new BusinessException("erro.mensagem.processo.nulo");
		}
	}
	
	@Override
	public Processo cancelarProcesso(Processo processo) {

		if (processo != null && processo.getId() != null) {
									
			Boolean tarefasEmAberto = tarefaRepository.existsByProcessoIdAndTipoTarefaAutomaticaAndStatus(processo.getId(), false, StatusTarefa.ABERTA.getId());
			if (tarefasEmAberto) {
				throw new BusinessException("");
			}
			Processo processoParaCancelar = obterProcesso(processo.getId());			
			processoParaCancelar.setStatus(StatusProcesso.CANCELADO.getId());
			processoParaCancelar.setDataAtualizacao(LocalDateTime.now());
			processoRepository.save(processoParaCancelar);
			
			return processo;
			

			/*
			 * if (processoRepository.atualizarStatusProcesso(processo.getId(),
			 * StatusProcesso.CANCELADO.getId(), LocalDateTime .now()) == 0) { throw new
			 * BusinessException("erro.mensagem.processo.status.nao.alterado"); }
			 */
			//return obterProcesso(processo.getId());
		}
		else {
			throw new BusinessException("erro.mensagem.processo.id.invalido");
		}

	}
	
	@Override
	public Processo cancelarProcesso(Long id) {
		return cancelarProcesso(obterProcesso(id));
	}
	
	@Override
	public Tarefa atribuirTarefaUsuarioLogado(Long id) {
		return atribuirTarefa(obterTarefa(id), usuarioService.obterIdUsuarioLogado());
	}
	
	private Processo obterProcesso(Processo processo) {
		if (processo == null) {
			throw new BusinessException("erro.mensagem.processo.nulo");
		}
		else {
			if ((processo.getId() == null || processo.getId().longValue() <= 0) && processo.getTipo() == null 
					&& processo.getRmr() == null && processo.getIdDoador() == null) {
				throw new BusinessException("erro.mensagem.processo.nulo");
			}
			if (processo.getId() == null && processo.getTipo() == null 
					&& (processo.getRmr() != null || processo.getIdDoador() != null)) {
				throw new BusinessException("erro.mensagem.processo.nulo");
			}
			if (processo.getId() == null && processo.getTipo() != null 
					&& (processo.getRmr() == null && processo.getIdDoador() == null)) {
				throw new BusinessException("erro.mensagem.processo.nulo");
			}
						
			if (processo.getId() != null) {
				return obterProcesso(processo.getId());					
			}
			if (processo.getTipo() != null) {
				if (processo.getRmr() != null) {
					return obterProcessoEmAndamento(processo.getRmr(), null, processo.getTipo());
				}
				if (processo.getIdDoador() != null) {
					return obterProcessoEmAndamento(null, processo.getIdDoador(), processo.getTipo());
				}
			}
		}
		
		throw new BusinessException("erro.mensagem.processo.nao.encontrado");
	}
	
	private void criarTarefaFollowUp(Tarefa tarefaPai) {
		TiposTarefa tipoTarefaPai = TiposTarefa.valueOf(tarefaPai.getTipoTarefa().getId());

		if (CollectionUtils.isNotEmpty(tipoTarefaPai.getConfiguracao().getFollowUp())) {			
			tipoTarefaPai.getConfiguracao().getFollowUp().stream().forEach(tipoTarefaFollowUp -> {
				TipoTarefa tipoTarefaFollowUpRecuperado = tipoTarefaService.obterTipoTarefa(tipoTarefaFollowUp.getId());
				LocalDateTime dataParaExecutar = LocalDateTime.now().plusSeconds(tipoTarefaFollowUpRecuperado.getTempoExecucao());
				
				Tarefa tarefaCriada = criarTarefa(Tarefa.builder()
						.perfilResponsavel(tipoTarefaFollowUp.getConfiguracao().getPerfil() != null ? tipoTarefaFollowUp.getConfiguracao().getPerfil().getId(): null)
						.tipoTarefa(tipoTarefaFollowUpRecuperado)
						.dataInicio(dataParaExecutar)
						.processo(Processo.builder().id(tarefaPai.getProcesso().getId()).build())
						.status(StatusTarefa.AGUARDANDO.getId())
						.tarefaPai(tarefaPai)
						.build());

				LOGGER.info("Criou tarefa de follow up com id \"{}\" para a tarefa de id\"{}\"", tarefaCriada.getId(), tarefaPai
						.getId());
			});
		}
	}
	
	/**
	 * Cria a tarefa automática, se a tarefa original indicar que necessita.
	 * 
	 * @param tarefa tarefa que origina a tarefa automática, se a configuração indicar que há necessidade.
	 */
	private void criarTarefaTimeout(Tarefa tarefaPai) {
		TiposTarefa tipoTarefaPai = TiposTarefa.valueOf(tarefaPai.getTipoTarefa().getId());

		ITiposTarefa tiposTarefaAssociada = tipoTarefaPai.getConfiguracao().getTimeout();
		
		if (tiposTarefaAssociada == null) {
			return;
		}
		
		Long ultimoUsuarioResponsavel = null;
		
		List<Tarefa> tarefasRollback = listarTarefasFilhas(tarefaPai.getId(), tiposTarefaAssociada.getId(), StatusTarefa.ABERTA.getId()); 
		if (CollectionUtils.isNotEmpty(tarefasRollback)) {			
			tarefasRollback.forEach(tarefaRollBack -> {
				LOGGER.info("Fechando tarefa de rollback {0}", tarefaRollBack.getId());
				cancelarTarefa(tarefaRollBack, false);				
			});
		}
		
		if (TiposTarefa.ROLLBACK_ATRIBUICAO.equals(tiposTarefaAssociada)) {	
			ultimoUsuarioResponsavel = tarefaPai.getUsuarioResponsavel() == null ? null : tarefaPai.getUsuarioResponsavel();
		}
				
		Tarefa tarefaCriada = criarTarefa(Tarefa.builder()
				.perfilResponsavel(tiposTarefaAssociada.getConfiguracao().getPerfil() != null ? tiposTarefaAssociada.getConfiguracao().getPerfil().getId() : null)
				.tipoTarefa(TipoTarefa.builder().id(tiposTarefaAssociada.getId()).build())				
				.processo(Processo.builder().id(tarefaPai.getProcesso().getId()).build())
				.status(StatusTarefa.ABERTA.getId())
				.tarefaPai(tarefaPai)
				.ultimoUsuarioResponsavel(ultimoUsuarioResponsavel)
				.build());

		LOGGER.info("Criou tarefa de associada com id \"{}\" para a tarefa de id\"{}\"", tarefaCriada.getId(), tarefaPai
				.getId());
				
	}
		
	private void encerrarTarefasFollowup(Tarefa tarefaPai) {
		encerrarCancelarTarefasFollowup(tarefaPai, false);
	}
	
	private void cancelarTarefasFollowup(Tarefa tarefaPai) {
		encerrarCancelarTarefasFollowup(tarefaPai, true);
	}
	
	private void encerrarCancelarTarefasFollowup(Tarefa tarefaPai, Boolean executarCancelamento) {
		TiposTarefa tipoTarefaPai = TiposTarefa.valueOf(tarefaPai.getTipoTarefa().getId());
		
		if (CollectionUtils.isNotEmpty(tipoTarefaPai.getConfiguracao().getFollowUp())) {
			tipoTarefaPai.getConfiguracao().getFollowUp().forEach(tipoTarefaFollowUp -> {
				List<Tarefa> tarefasFollowUp = listarTarefasFilhas(tarefaPai.getId(), tipoTarefaFollowUp.getId(), StatusTarefa.AGUARDANDO.getId());
				if (CollectionUtils.isNotEmpty(tarefasFollowUp)) {
					tarefasFollowUp.forEach(tarefaFollowUp -> {
						if (executarCancelamento) {
							cancelarTarefa(tarefaFollowUp, false);
						}
						else {
						   fecharTarefa(tarefaFollowUp, false);
						}						
					});
				}
			});
		}
	}
	
	private void encerrarTarefaAssociada(Tarefa tarefaPai) {
		encerrarCancelarTarefaAssociada(tarefaPai, false);
	}
	
	private void cancelarTarefaAssociada(Tarefa tarefaPai) {
		encerrarCancelarTarefaAssociada(tarefaPai, true);
	}
	
	private void encerrarCancelarTarefaAssociada(Tarefa tarefaPai, Boolean executarCancelamento) {
		TiposTarefa tipoTarefaPai = TiposTarefa.valueOf(tarefaPai.getTipoTarefa().getId());
		
		ITiposTarefa tiposTarefaAssociada = tipoTarefaPai.getConfiguracao().getTimeout();
		
		if (tiposTarefaAssociada == null) {
			return;
		}
				
		List<Tarefa> tarefasTimeout = listarTarefasFilhas(tarefaPai.getId(), tiposTarefaAssociada.getId(), StatusTarefa.ABERTA.getId());
		if (CollectionUtils.isNotEmpty(tarefasTimeout)) {
			tarefasTimeout.forEach(tarefaTimeout -> {
				if (executarCancelamento) {
					cancelarTarefa(tarefaTimeout, false);
				}
				else { 
					fecharTarefa(tarefaTimeout, false);
				}
			});
		}
	}
	
	
	
	

}