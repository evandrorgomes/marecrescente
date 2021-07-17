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
 * Implementação do processamenta da tarefa automática do tipo ROLLBACK_ATRIBUICAO.
 * Consiste em resgatar o último usuário responsável, exatamente anterior ao atual,
 * pela tarefa e torná-lo novamente o responsável.
 * 
 * @author Pizão
 *
 */
@Service
public class ProcessadorRollBackAtribuicao implements IProcessadorTarefa {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProcessadorRollBackAtribuicao.class);

	@Autowired
	private IProcessoService processoService;

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void processar(Tarefa tarefaRollback) {
		LOGGER.info("Inicio processamento da tarefa rollback {}", tarefaRollback);

		Tarefa tarefaEstornada = processoService.obterTarefa(tarefaRollback.getRelacaoEntidade());
		if (tarefaEstornada == null) {
			throw new BusinessException("erro.mensagem.tarefa.associada.nao.encontrada", tarefaRollback.getId().toString());
		}

		Long tempoExecutando = SECONDS.between(tarefaRollback.getDataCriacao(), LocalDateTime.now());
		boolean ocorreuTimeOut = tempoExecutando >= tarefaRollback.getTipoTarefa().getTempoExecucao();
		
		if (ocorreuTimeOut) {
			tarefaEstornada.setStatus(StatusTarefa.ABERTA.getId());
			tarefaEstornada.setUsuarioResponsavel(tarefaRollback.getUltimoUsuarioResponsavel());
			processoService.atualizarTarefa(tarefaEstornada);
			processoService.fecharTarefa(tarefaRollback);
		}

		LOGGER.info("Término processamento da tarefa rollback {}", tarefaRollback);
	}

}
