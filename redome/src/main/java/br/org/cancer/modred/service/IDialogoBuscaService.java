package br.org.cancer.modred.service;

import java.util.List;

import br.org.cancer.modred.model.DialogoBusca;

/**
 * Interface de negócios de diálogo de busca.
 * @author Filipe Paes
 *
 */
public interface IDialogoBuscaService {

	/**
	 * Listagem de diálogos sobre a determinada busca.
	 * @param busca - busca sobre o qual há o diálogo.
	 * @return
	 */
	List<DialogoBusca> listarDialogos(Long idBusca);
	
	/**
	 * Registra o diálogo sobre a busca.
	 * @param dialogo recebido da tela de busca.
	 */
	void salvarDialogo(DialogoBusca dialogo);
	
}
