package br.org.cancer.redome.tarefa.process.server.impl;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.cancer.redome.tarefa.exception.BusinessException;
import br.org.cancer.redome.tarefa.model.Tarefa;
import br.org.cancer.redome.tarefa.model.domain.StatusTarefa;
import br.org.cancer.redome.tarefa.process.server.IProcessadorTarefa;
import br.org.cancer.redome.tarefa.service.IProcessoService;

/**
 * Remove a atribuição de uma tarefa para um usuario como agendamento de contato. 
 * @author Filipe Paes
 *
 */
@Service
@Transactional
public class ProcessadorDesatribuirUsuarioAgendamentoContato implements IProcessadorTarefa{

	@Autowired
	private IProcessoService processoService;

	
	@Override
	public void processar(Tarefa tarefa) {
		Tarefa tarefaAVerificar = processoService.obterTarefa(tarefa.getTarefaPai().getId());
		if (tarefaAVerificar == null) {
			throw new BusinessException("erro.mensagem.tarefa.associada.nao.encontrada", tarefa.getId().toString());
		}
		if (tarefaAVerificar.getDataInicio().isAfter(LocalDateTime.now())) {
			if (tarefaAVerificar.getStatus().equals(StatusTarefa.ABERTA)) {								
				tarefaAVerificar.setUsuarioResponsavelAgendamento(null);
				processoService.atualizarTarefa(tarefa);
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
