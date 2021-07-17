package br.org.cancer.redome.tarefa.process.server.impl;

import static java.time.temporal.ChronoUnit.DAYS;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import br.org.cancer.redome.tarefa.exception.BusinessException;
import br.org.cancer.redome.tarefa.feign.client.IExameFeign;
import br.org.cancer.redome.tarefa.model.Tarefa;
import br.org.cancer.redome.tarefa.model.domain.StatusTarefa;
import br.org.cancer.redome.tarefa.model.domain.TiposTarefa;
import br.org.cancer.redome.tarefa.process.server.IProcessadorTarefa;
import br.org.cancer.redome.tarefa.service.IProcessoService;

/**
 * Processador para notificação de cadastro de resultado IDM Internacional.
 * 
 * @author bruno.sousa
 *
 */
@Service
@Transactional
public class ProcessadorNotificacaoCadastrarResultadoIDMInternacional7 implements IProcessadorTarefa {
	
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
		if (tempoExecutando >= tempoExecucao) {
			Tarefa tarefaAVerificar = processoService.obterTarefa(tarefa.getTarefaPai().getId());
			if (tarefaAVerificar == null) {
				throw new BusinessException("erro.mensagem.tarefa.associada.nao.encontrada", tarefa.getId().toString());
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
					exameFeign.notificacarCadastroResultadoExameIdmInternacional7(tarefaAVerificar.getId());
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
