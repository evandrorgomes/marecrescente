package br.org.cancer.redome.tarefa.process.server.impl;

import static java.time.temporal.ChronoUnit.DAYS;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import br.org.cancer.redome.tarefa.exception.BusinessException;
import br.org.cancer.redome.tarefa.feign.client.IPedidoExameFeign;
import br.org.cancer.redome.tarefa.model.Tarefa;
import br.org.cancer.redome.tarefa.model.domain.StatusTarefa;
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
public class ProcessadorChecklistDoadorExame  implements IProcessadorTarefa  {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProcessadorChecklistDoadorExame.class);
	
	@Autowired
	private IProcessoService processoService;
	
	@Autowired
	protected MessageSource messageSource;
	
	@Autowired
	@Lazy(true)
	private IPedidoExameFeign pedidoExameFeign;
	
	@Override
	public void processar(Tarefa tarefaTimeout) {
		LOGGER.info("Verificando se a tarefa de pedido de exame tem mais de 30 dias");
		Long tempoExecutando = DAYS.between(tarefaTimeout.getDataCriacao(), LocalDateTime.now());
		Long tempoExecucao = tarefaTimeout.getTipoTarefa().getTempoExecucao() / 60 / 60 / 24;
		if (tempoExecutando >= tempoExecucao) {
			Tarefa tarefaPai = processoService.obterTarefa(tarefaTimeout.getRelacaoEntidade());
			if (tarefaPai == null) {
				throw new BusinessException("erro.mensagem.tarefa.associada.nao.encontrada", tarefaTimeout.getId().toString());
			}
			if (tarefaPai.getStatus().equals(StatusTarefa.ABERTA)) {		
				pedidoExameFeign.criarCheckListExameSemResultadoMais30Dias(tarefaPai.getId());
				processoService.fecharTarefa(tarefaTimeout);
			}
			else if (tarefaPai.getStatus().equals(StatusTarefa.FEITA )) {
				processoService.fecharTarefa(tarefaTimeout);
			}
			else if (tarefaPai.getStatus().equals(StatusTarefa.CANCELADA )) {
				processoService.cancelarTarefa(tarefaTimeout);
			}
		}
	}
}
