package br.org.cancer.redome.tarefa.process.server.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import br.org.cancer.redome.tarefa.feign.client.IPedidoContatoSmsFeign;
import br.org.cancer.redome.tarefa.model.Tarefa;
import br.org.cancer.redome.tarefa.model.domain.StatusTarefa;
import br.org.cancer.redome.tarefa.process.server.IProcessadorTarefa;
import br.org.cancer.redome.tarefa.service.IProcessoService;

/**
 * Precessador para verificar o status do sms enviado.
 * 
 * @author brunosousa
 *
 */
@Service
public class ProcessadorVerificarStatusSmsEnviado implements IProcessadorTarefa {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProcessadorVerificarStatusSmsEnviado.class);
	
	@Autowired
	private IProcessoService processoService;
	
	@Autowired
	@Lazy(true)
	private IPedidoContatoSmsFeign pedidoContatoSmsFeign;
	
	@Override
	public void processar(Tarefa tarefa) {
		
		if (tarefa.getTarefaPai() != null) {

			Tarefa tarefaPai = tarefa.getTarefaPai();
		
			if (StatusTarefa.ABERTA.equals(tarefaPai.getStatus())) {
				
				pedidoContatoSmsFeign.atualizaStatusSmsEnviado(tarefaPai.getRelacaoEntidade());
				
			}
			else {
				processoService.fecharTarefa(tarefa);				
			}
		}
		else {
			processoService.fecharTarefa(tarefa);
		}
	}

	
}
