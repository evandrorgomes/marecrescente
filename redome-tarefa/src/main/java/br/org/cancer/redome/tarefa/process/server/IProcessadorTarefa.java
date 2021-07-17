package br.org.cancer.redome.tarefa.process.server;

import br.org.cancer.redome.tarefa.model.Tarefa;

/**
 * Interface responsável pelo processamento das tarefas.
 * 
 * @author Cintia Oliveira
 *
 */
public interface IProcessadorTarefa {

	/**
	 * Método responsável por processar uma tarefa.
	 * 
	 * @param tarefa
	 */
	void processar(Tarefa tarefa);
}
