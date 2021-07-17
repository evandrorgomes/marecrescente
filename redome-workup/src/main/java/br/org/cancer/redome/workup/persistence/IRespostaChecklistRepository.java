package br.org.cancer.redome.workup.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.org.cancer.redome.workup.model.RespostaChecklist;

/**
 * Interface de dados de resposta de Checklist.
 * @author ergomes
 *
 */
@Repository
public interface IRespostaChecklistRepository extends IRepository<RespostaChecklist, Long> {


	/**
	 * Obtem uma resposta por id de item e de pedido de logistica.
	 * @param idItem item de checklist.
	 * @param idLogistica id da logistica ao qual pertence a resposta.
	 * @return resposta localizada.
	 */
	@Query("select r from RespostaChecklist r join r.item i  where i.id = :idItem and r.pedidoLogistica = :idLogistica")
	RespostaChecklist obterRespostaPor(@Param("idItem") Long idItem, @Param("idLogistica") Long idLogistica);
	
	/**
	 * Obtem respostas por id de logística.
	 * @param idLogistica - identificação do pedido de logística da  resposta.
	 * @return lista de respotas.
	 */
	List<RespostaChecklist> findByPedidoLogistica(Long idLogistica);
}