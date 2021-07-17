package br.org.cancer.redome.workup.persistence;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.org.cancer.redome.workup.dto.ConsultaTarefasWorkupDTO;
import br.org.cancer.redome.workup.dto.FiltroListaTarefaWorkupDTO;



/**
 * Interface para de métodos de banco para match preliminar customizadas.
 * 
 * @author bruno.sousa
 *
 */
@Repository
public interface IPedidoWorkupRepositoryCustom {


	/**
	 * Lista a view utilizada na lista de tarefa de workup. 
	 * 
	 * @param FiltroListaTarefaDTO - objeto dto filtro da tarefa.
	 * @return lista com os ddados da view.
	 */
	List<ConsultaTarefasWorkupDTO> listarTarefasWorkupView(FiltroListaTarefaWorkupDTO filtro);
	
	/**
	 * Lista a view utilizada na lista de tarefa de workup para solicitação. 
	 * 
	 * @param FiltroListaTarefaDTO - objeto dto filtro de solicitação.
	 * @return lista com os ddados da view.
	 */
	List<ConsultaTarefasWorkupDTO> listarSolicitacoesWorkupView(FiltroListaTarefaWorkupDTO filtro);
		
}
