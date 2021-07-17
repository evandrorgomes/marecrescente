package br.org.cancer.redome.tarefa.schedule;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.org.cancer.redome.tarefa.configuration.RedomeTarefaApplication;
import br.org.cancer.redome.tarefa.model.Tarefa;
import br.org.cancer.redome.tarefa.model.domain.TiposTarefa;
import br.org.cancer.redome.tarefa.process.server.IProcessadorTarefa;
import br.org.cancer.redome.tarefa.service.IProcessoService;

/**
 * Classe responsável pelo motor de processamento das tarefas.
 * 
 * @author ergomes
 *
 */
@Component
public class ProcessServerWmdaSchedule {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProcessServerWmdaSchedule.class);
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
	@Scheduled(cron = "00 19 18 * * *", zone = RedomeTarefaApplication.TIME_ZONE)
	public void task() {
		LOGGER.info("Inicio da execução do motor de tarefas.");
		//this.processarTarefasAutomaticas();
		//this.processarTarefasNotificacoes();
		this.processarTarefasWmda();
		LOGGER.info("Fim da execução do motor de tarefas.");
	}
 
	private void processarTarefasAutomaticas() {
		processarTarefas(processoService.listarTarefasAutomaticasEmAberto());
	}

	private void processarTarefasNotificacoes() {
		processarTarefas(processoService.listarTarefasNotificacoesEmAguardandoAgendamento());
	}

	private void processarTarefasWmda() {
		List<Tarefa> tarefas = new ArrayList<>();
//		tarefas = processoService.listarTarefasPorTipoTarefaIdEStatus(109L,1L);
//		tarefas.add(processoService.obterTarefa(79738L));

		// NOVA
		
		tarefas.add(processoService.obterTarefa(79737L));
		
		processarTarefas(tarefas);
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
