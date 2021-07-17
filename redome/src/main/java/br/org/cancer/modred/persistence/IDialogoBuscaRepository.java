package br.org.cancer.modred.persistence;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.DialogoBusca;

/**
 * Interface de persistencia de Recebimento de Coleta.
 * @author Filipe Paes
 */
@Repository
public interface IDialogoBuscaRepository  extends IRepository<DialogoBusca, Long> {
	
	/**
	 * Lista dialogos relacionados a determinada busca.
	 * @param idBusca - identificação da busca do diálogo.
	 * @return listagem de diálogos.
	 */
	List<DialogoBusca> findByBuscaIdOrderByDataHoraMensagemAsc(Long idBusca);

}
