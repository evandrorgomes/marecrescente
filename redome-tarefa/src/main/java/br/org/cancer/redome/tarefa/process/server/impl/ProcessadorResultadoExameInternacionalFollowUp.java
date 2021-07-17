package br.org.cancer.redome.tarefa.process.server.impl;

import static java.time.temporal.ChronoUnit.DAYS;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.redome.tarefa.exception.BusinessException;
import br.org.cancer.redome.tarefa.feign.client.IExameFeign;
import br.org.cancer.redome.tarefa.model.Tarefa;
import br.org.cancer.redome.tarefa.model.domain.StatusTarefa;
import br.org.cancer.redome.tarefa.model.domain.TiposTarefa;
import br.org.cancer.redome.tarefa.process.server.IProcessadorTarefa;
import br.org.cancer.redome.tarefa.service.IProcessoService;

/**
 * Processador para notificação de cadastro de resultado Exame Internacional.
 * 
 * @author bruno.sousa
 *
 */
@Service
@Transactional
public class ProcessadorResultadoExameInternacionalFollowUp implements IProcessadorTarefa {
	
	@Autowired
	private IProcessoService processoService;
	
	@Autowired
	protected MessageSource messageSource;
	
	@Autowired
	@Lazy(true)
	private IExameFeign exameFeign;
	

	@Override
	public void processar(Tarefa tarefa) {
		Long tempoExecutando = DAYS.between(tarefa.getDataCriacao(), LocalDateTime.now());
		Long tempoExecucao = tarefa.getTipoTarefa().getTempoExecucao() / 60 / 60 / 24;
		if (tempoExecutando != 0 && tempoExecutando >= tempoExecucao) {
			Tarefa tarefaAVerificar = processoService.obterTarefa(tarefa.getRelacaoEntidade());
			if (tarefaAVerificar == null && tarefa.getTarefaPai() == null) {
				throw new BusinessException("erro.mensagem.tarefa.associada.nao.encontrada", tarefa.getId().toString());
			}
			if (tarefaAVerificar == null && tarefa.getTarefaPai() != null) {
				tarefaAVerificar = tarefa.getTarefaPai();
			}
			if (tarefaAVerificar.getStatus().equals(StatusTarefa.ABERTA)) {				
				List<Tarefa> tarefasNotificacao = processoService.listarTarefasFilhas(TiposTarefa.NOTIFICACAO.getId(), tarefa.getId());
				if (tarefasNotificacao == null ) {
					tarefasNotificacao = new ArrayList<Tarefa>();
				}
				Long quantidade = tempoExecutando / tempoExecucao;
				if (tarefasNotificacao.size() < quantidade) {
					tarefasNotificacao.stream()
						.filter(notificacao -> StatusTarefa.ABERTA.getId().equals(notificacao.getStatus()) )
						.forEach(notificacao -> {
							processoService.cancelarTarefa(notificacao);
						});
					exameFeign.notificarUsuariosSobreResultadoDeExameDeDoadorInternacional(tarefaAVerificar.getId());					
				}
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
