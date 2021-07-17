package br.org.cancer.redome.tarefa.process.server.impl;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.redome.tarefa.dto.ConfiguracaoDTO;
import br.org.cancer.redome.tarefa.feign.client.IConfiguracaoFeign;
import br.org.cancer.redome.tarefa.feign.client.IPedidoContatoSmsFeign;
import br.org.cancer.redome.tarefa.model.Tarefa;
import br.org.cancer.redome.tarefa.model.domain.StatusTarefa;
import br.org.cancer.redome.tarefa.process.server.IProcessadorTarefa;
import br.org.cancer.redome.tarefa.service.IProcessoService;

/**
 * Processador que verifica o tempo  de vida do pedido de sms. 
 * Caso tenha se cumprido o tempo fecha o pedido de contato sms e inativa o doador.
 * 
 * @author brunosousa
 *
 */
@Service
public class ProcessadorValidarPedidoContatoSmsFollowUp implements IProcessadorTarefa {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProcessadorValidarPedidoContatoSmsFollowUp.class);
	
	private static final String QUANTIDADE_MAXIMA_DIAS_CONTATO_SMS_FASE_3 = "quantidadeMaximaDiasContatoSmsFase3";
	
	@Autowired
	private IProcessoService processoService;
	
	@Autowired
	@Lazy(true)
	private IConfiguracaoFeign configuracaoFeign;
	
	@Autowired
	@Lazy(true)
	private IPedidoContatoSmsFeign pedidoContatoSmsFeign; 
		
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void processar(Tarefa tarefa) {
		
		if (tarefa.getTarefaPai() != null) {

						
			Tarefa tarefaPai = tarefa.getTarefaPai();
		
			if (StatusTarefa.ABERTA.equals(tarefaPai.getStatus())) {
	
				long dias = ChronoUnit.DAYS.between(tarefaPai.getDataCriacao(), LocalDateTime.now());
				long quantidadeMaximaDias = obterQuantidadeMaximaDiasSmsEnviado();
				
				if (dias > quantidadeMaximaDias) {
					pedidoContatoSmsFeign.finalizarPedidoContatoSms(tarefaPai.getRelacaoEntidade());
				}
			}
			else {
				processoService.fecharTarefa(tarefa);
			}
		}
		else {
			processoService.fecharTarefa(tarefa);
		}
	}

	private Long obterQuantidadeMaximaDiasSmsEnviado() {
		LOGGER.info("Obter quantidade máxima de dias para o retorno do contato de doador.");
		ResponseEntity<ConfiguracaoDTO> response = configuracaoFeign.obterConfiguracaoPorChave(QUANTIDADE_MAXIMA_DIAS_CONTATO_SMS_FASE_3);
		
		return Long.parseLong(response.getBody().getValor());
	}
}
