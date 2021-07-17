package br.org.cancer.redome.tarefa.process.server.impl;

import static java.time.temporal.ChronoUnit.SECONDS;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import br.org.cancer.redome.tarefa.feign.client.IMatchFeign;
import br.org.cancer.redome.tarefa.model.Tarefa;
import br.org.cancer.redome.tarefa.process.server.IProcessadorTarefa;
import br.org.cancer.redome.tarefa.service.IProcessoService;

/**
 * Processador para gerar checklist de busca caso o resultado de 
 * exame tenha passado de 30 dias.
 * @author Filipe Paes
 *
 */
@Service
@Transactional
public class ProcessadorChecklistExameDivergenteFollowup  implements IProcessadorTarefa  {
	
	@Autowired
	private IProcessoService processoService;
	
	@Autowired
	protected MessageSource messageSource;
	
	@Autowired
	@Lazy(true)
	private IMatchFeign matchFeign;
	
	@Override
	public void processar(Tarefa tarefa) {
		Tarefa tarefaDoBanco = processoService.obterTarefa(tarefa.getId()); 
		Long tempoExecutando = SECONDS.between(tarefa.getDataCriacao(), LocalDateTime.now());
		if (tempoExecutando >= tarefaDoBanco.getTipoTarefa().getTempoExecucao()) {
			matchFeign.criarCheckListsPorDivergenciasMatch(tarefaDoBanco.getId());
			processoService.fecharTarefa(tarefa);
		}
	}
}
