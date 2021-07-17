package br.org.cancer.redome.tarefa.process.server.impl;

import static java.time.temporal.ChronoUnit.SECONDS;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.redome.tarefa.exception.BusinessException;
import br.org.cancer.redome.tarefa.model.Tarefa;
import br.org.cancer.redome.tarefa.model.domain.StatusTarefa;
import br.org.cancer.redome.tarefa.process.server.IProcessadorTarefa;
import br.org.cancer.redome.tarefa.service.IProcessoService;

/**
 * Exemplo de implementação de um processador de tarefas.
 * 
 * @author Cintia Oliveira
 *
 */
@Service
public class ProcessadorTimeoutConferenciaExame implements IProcessadorTarefa {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProcessadorTimeoutConferenciaExame.class);

	@Autowired
	private IProcessoService processoService;

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void processar(Tarefa tarefa) {
		LOGGER.info("Inicio processamento da tarefa {}", tarefa);

		Long tempoExecutando = SECONDS.between(tarefa.getDataCriacao(), LocalDateTime.now());
		if (tempoExecutando >= tarefa.getTipoTarefa().getTempoExecucao()) {
			Tarefa tarefaAVerificar = processoService.obterTarefa(tarefa.getTarefaPai().getId());
			if (tarefaAVerificar == null) {
				throw new BusinessException("erro.mensagem.tarefa.associada.nao.encontrada", tarefa.getId().toString());
			}
			if (tarefaAVerificar.getStatus().equals(StatusTarefa.ATRIBUIDA)) {
				processoService.removerAtribuicaoTarefa(tarefaAVerificar);
			}
			processoService.fecharTarefa(tarefa);
		}

		LOGGER.info("Término processamento da tarefa {}", tarefa);
	}

}
