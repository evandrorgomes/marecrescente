package br.org.cancer.redome.tarefa.process.server.impl;

import static java.time.temporal.ChronoUnit.DAYS;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.redome.tarefa.exception.BusinessException;
import br.org.cancer.redome.tarefa.feign.client.IExameFeign;
import br.org.cancer.redome.tarefa.model.Tarefa;
import br.org.cancer.redome.tarefa.model.domain.StatusTarefa;
import br.org.cancer.redome.tarefa.process.server.IProcessadorTarefa;
import br.org.cancer.redome.tarefa.service.IProcessoService;

/**
 * Processador para notificação de cadastro de resultado Exame Internacional.
 * 
 * @author bruno.sousa
 *
 */
@Service
@Transactional
public class ProcessadorNotificacaoCadastrarResultadoExameCtInternacional15 implements IProcessadorTarefa {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProcessadorNotificacaoCadastrarResultadoExameCtInternacional15.class);
	
	@Autowired
	private IProcessoService processoService;
	
	@Autowired
	protected MessageSource messageSource;
	
	
	@Autowired
	@Lazy(true)
	private IExameFeign exameFeign;

	@Override
	public void processar(Tarefa tarefa) {
		Long tempoExecutando = DAYS.between(tarefa.getDataCriacao(), LocalDateTime.now());
		Long tempoExecucao = tarefa.getTipoTarefa().getTempoExecucao() / 60 / 60 / 24;
		if (tempoExecutando >= tempoExecucao) {
			Tarefa tarefaAVerificar = processoService.obterTarefa(tarefa.getTarefaPai().getId());
			if (tarefaAVerificar == null) {
				throw new BusinessException("erro.mensagem.tarefa.associada.nao.encontrada", tarefa.getId().toString());
			}
			if (tarefaAVerificar.getStatus().equals(StatusTarefa.ABERTA)) {
				LOGGER.info(tarefaAVerificar.getId().toString());
				exameFeign.notificacarCadastroResultadoExameCtInternacional15(tarefaAVerificar.getId());			
			}
			else if (tarefaAVerificar.getStatus().equals(StatusTarefa.FEITA )) {
				processoService.fecharTarefa(tarefa);
			}
			else if (tarefaAVerificar.getStatus().equals(StatusTarefa.CANCELADA )) {
				processoService.cancelarTarefa(tarefa);
				
			}
			
		}
	}
	
	
}
