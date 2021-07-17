package br.org.cancer.redome.tarefa.schedule;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import br.org.cancer.redome.tarefa.model.Tarefa;
import br.org.cancer.redome.tarefa.model.domain.TiposTarefa;
import br.org.cancer.redome.tarefa.process.server.IProcessadorTarefa;
import br.org.cancer.redome.tarefa.service.IProcessoService;

/**
 * Classe responsável pelo motor de processamento das tarefas.
 * 
 * @author Cintia Oliveira
 *
 */
@Component
public class ProcessServerSchedule {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProcessServerSchedule.class);
	private static final long DEZ_MINUTOS = 600000L;
	private static final long CINCO_MINUTOS = 300000L;

	@Autowired
	private IProcessoService processoService;

	@Autowired
	private ApplicationContext applicationContext;

	/**
	 * Método principal do motor de processamento.
	 */
	//@Scheduled(fixedDelay = DEZ_MINUTOS, initialDelay = CINCO_MINUTOS )
	//@Scheduled(cron = "00 54 10 * * *", zone = RedomeTarefaApplication.TIME_ZONE)
	public void task() {
		LOGGER.info("Inicio da execução do motor de tarefas.");
		this.processarTarefasAutomaticas();
		this.processarTarefasNotificacoes();
		LOGGER.info("Fim da execução do motor de tarefas.");
	}
 
	private void processarTarefasAutomaticas() {
		processarTarefas(processoService.listarTarefasAutomaticasEmAberto());
	}

	private void processarTarefasNotificacoes() {
		processarTarefas(processoService.listarTarefasNotificacoesEmAguardandoAgendamento());
	}

	private void processarTarefas(List<Tarefa> tarefas) {
		if (tarefas != null && !tarefas.isEmpty()) {
			tarefas.forEach(tarefa -> {
				Class<? extends IProcessadorTarefa> classProcessador = TiposTarefa.obterClasseProcessador(tarefa.getTipoTarefa()
						.getId());				
				if (classProcessador != null) {
					try {

						IProcessadorTarefa processador = applicationContext.getBean(classProcessador);
						processador.processar(tarefa);
						
					}
					catch (Exception e) {
						LOGGER.error("Erro ao tentar processar a tarefa {}", tarefa);
						LOGGER.error("Erro:", e);
					}
				}
				else {
					LOGGER.warn("Tipo de tarefa {} não suportado.", tarefa.getTipoTarefa());
				}
			});
		}
		else {
			LOGGER.info("Não foi encontrada nenhuma tarefa para ser processada.");
		}
	}
}
