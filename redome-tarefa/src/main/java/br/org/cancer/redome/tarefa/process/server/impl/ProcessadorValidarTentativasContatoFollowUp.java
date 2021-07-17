package br.org.cancer.redome.tarefa.process.server.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.redome.tarefa.feign.client.ITentativaContatoDoadorFeign;
import br.org.cancer.redome.tarefa.model.Tarefa;
import br.org.cancer.redome.tarefa.model.domain.StatusTarefa;
import br.org.cancer.redome.tarefa.process.server.IProcessadorTarefa;
import br.org.cancer.redome.tarefa.service.IProcessoService;

/**
 * Processador que verifica a vida util do pedido de contato para fase 3.
 * Caso já tenha ocorrido o tempo ele finaliza o pediod de contato.
 * 
 * @author brunosousa
 *
 */
@Service
public class ProcessadorValidarTentativasContatoFollowUp implements IProcessadorTarefa {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProcessadorValidarTentativasContatoFollowUp.class);
	
	@Autowired
	@Lazy(true)
	private ITentativaContatoDoadorFeign tentativaContatoDoadorFeign;
		
	@Autowired
	private IProcessoService processoService;
	
	
	@SuppressWarnings("rawtypes")
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void processar(Tarefa tarefa) {
		
		if (tarefa.getTarefaPai() != null) {

			Tarefa tarefaPai = tarefa.getTarefaPai();
		
			if (StatusTarefa.ABERTA.equals(tarefaPai.getStatus())) {
				
				ResponseEntity<Boolean> podeFinalizar = tentativaContatoDoadorFeign.podeFinalizarTentativaContato(tarefaPai.getRelacaoEntidade());
				if (podeFinalizar.getBody()) {
					
					tentativaContatoDoadorFeign.finalizarTentativaContato(tarefaPai.getRelacaoEntidade());
					
				}
			}
			else if (!StatusTarefa.ATRIBUIDA.equals(tarefaPai.getStatus())) {
				processoService.fecharTarefa(tarefa);				
			}
		}
		else {
			processoService.fecharTarefa(tarefa);
		}
	}

	

}
