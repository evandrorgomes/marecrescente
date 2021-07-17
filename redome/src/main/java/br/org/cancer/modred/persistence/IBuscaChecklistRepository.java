package br.org.cancer.modred.persistence;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.BuscaChecklist;



/**
 * Interface de persistencia de BuscaCheckList.
 * 
 * @author Filipe Paes
 */
@Repository
public interface IBuscaChecklistRepository extends IRepository<BuscaChecklist, Long>{

	/**
	 * Lista todas os itens de checklist de busca associados a busca .
	 * 
	 * @param idBusca ID da busca.
	 * @return lista de busca checklist.
	 */
	@Query("select clist "
			+ "from BuscaChecklist clist join clist.busca bus left join clist.match mat join clist.tipoBuscaChecklist tpBusc "
		+ "where bus.id = :idBusca and clist.dataHoraVisto is null order by mat.id desc ")
	List<BuscaChecklist> listarBuscaChecklist(@Param("idBusca") Long idBusca);
	
	/**
	 * Lista todos os itens de checklist associados a uma busca e a um doador.
	 * @param idBusca ID da busca
	 * @param idMatch ID do match.
	 * @return lista de busca checklist.
	 */
	@Query("select clist "
		+ "from BuscaChecklist clist join clist.busca bus join clist.match mat join clist.tipoBuscaChecklist tpBusc "
		+ "where bus.id = :idBusca and mat.id = :idMatch and clist.dataHoraVisto is null")
	List<BuscaChecklist> listarBuscaChecklist(@Param("idBusca") Long idBusca, @Param("idMatch") Long idMatch);
	
	
	@Query("select b from BuscaChecklist b "
	+ " join b.busca bu join bu.paciente p join b.tipoBuscaChecklist t "
	+ " where p.centroAvaliador.id in "
	+ " ( "
	+ "		select c.id from "
	+ "		Usuario u join u.centrosTransplantesParaTarefas c "
	+ "		where  u.username = :username "
	+ "	) "
	+ " and (t.id = :idTipoChecklist or :idTipoChecklist is null) "
	+ " and b.visto is null "
	+ " order by b.dataCriacao ")
	Page<BuscaChecklist> listarBuscaChecklistPorAnalistaETipoChecklist(Pageable page, @Param("username") String username, @Param("idTipoChecklist") Long idTipoChecklist);
	
	
	/**
	 * Lista todos os itens de checklist associados a uma busca e a um doador.
	 * 
	 * @param idBusca ID da busca
	 * @param idMatch ID do match.
	 * @return lista de busca checklist.
	 */
	@Query(nativeQuery =true, value= "SELECT COUNT(BC.BUCH_ID) " + 
			"FROM BUSCA_CHECKLIST BC INNER JOIN BUSCA B ON (B.BUSC_ID = BC.BUSC_ID) " + 
			"INNER JOIN MATCH M ON (M.BUSC_ID = B.BUSC_ID AND M.MATC_ID = BC.MATC_ID) " + 
			"INNER JOIN DOADOR D ON (D.DOAD_ID = M.DOAD_ID) " + 
			"INNER JOIN SOLICITACAO S ON (S.MATC_ID = M.MATC_ID) " + 
			"WHERE M.MATC_IN_STATUS = 0 " + 
			"AND D.DOAD_TX_FASE = :fase " + 
			"AND B.BUSC_ID = :idBusca " + 
			"AND D.DOAD_IN_TIPO IN (:tiposDoador) " + 
			"AND S.SOLI_NR_STATUS = 2 "	+ 
			"AND (BC.BUCH_IN_VISTO = 0  OR BC.BUCH_IN_VISTO IS NULL) " + 
			"AND BC.BUCH_DT_VISTO IS NULL ")
	Long totalBuscaChecklistHistoricoMatchPorSituacao(@Param("idBusca") Long idBusca, @Param("fase") String fase, @Param("tiposDoador") List<Long> tiposDoador);
	
	
}
