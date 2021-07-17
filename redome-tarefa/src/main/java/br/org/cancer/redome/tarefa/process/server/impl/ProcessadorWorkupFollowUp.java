package br.org.cancer.redome.tarefa.process.server.impl;

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
 * processador de notificações para alteração do status de {@link StatusTarefa.AGUARDANDO} para {@link StatusTarefa.ATRIBUIDA}.
 * 
 * @author Fillipe Moreira.
 *
 */
@Service
public class ProcessadorWorkupFollowUp implements IProcessadorTarefa {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProcessadorWorkupFollowUp.class);

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

		if (StatusTarefa.FEITA.equals(tarefaAVerificar.getStatus())) {
			tarefa.setStatus(StatusTarefa.FEITA.getId());
			LOGGER.info("Tarefa associada já foi feita, atualizando a tarefa {}", tarefa);
		}
		else {
			if (tarefaAVerificar.getPerfilResponsavel() == null) {
				throw new BusinessException("erro.mensagem.tarefa.associada.notificacao.sem.responsavel", tarefa.getId()
						.toString());
			}

			tarefa.setStatus(StatusTarefa.ATRIBUIDA.getId());
			LOGGER.info("Atualizando o status da tarefa para atribuida {}", tarefa);
		}

		processoService.atualizarTarefa(tarefa);

		LOGGER.info("Término processamento da tarefa {}", tarefa);
	}

}
