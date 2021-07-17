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
public class ProcessadorTimeout implements IProcessadorTarefa {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProcessadorTimeout.class);

	@Autowired
	private IProcessoService processoService;

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void processar(Tarefa tarefa) {
		LOGGER.info("Inicio processamento da tarefa {}", tarefa);

		Tarefa tarefaAVerificar = processoService.obterTarefa(tarefa.getRelacaoEntidade());
		if (tarefaAVerificar == null) {
			throw new BusinessException("erro.mensagem.tarefa.associada.nao.encontrada", tarefa.getId().toString());
		}

		Long tempoExecutando = SECONDS.between(tarefa.getDataCriacao(), LocalDateTime.now());
		if (tarefaAVerificar.getTipoTarefa().getTempoExecucao() != null && tempoExecutando >= tarefaAVerificar.getTipoTarefa().getTempoExecucao()) {
			if (tarefaAVerificar.getStatus().equals(StatusTarefa.ATRIBUIDA)) {
				processoService.removerAtribuicaoTarefa(tarefaAVerificar);
			}
			processoService.fecharTarefa(tarefa);
		}

		LOGGER.info("Término processamento da tarefa {}", tarefa);
	}

}
