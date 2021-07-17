package br.org.cancer.modred.persistence.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;

import br.org.cancer.modred.controller.dto.SolicitacaoDTO;
import br.org.cancer.modred.controller.dto.doador.PedidoExameDTO;
import br.org.cancer.modred.controller.dto.doador.SolicitacaoRedomewebDTO;
import br.org.cancer.modred.persistence.ISolicitacaoRedomewebRepositoryCustom;

/**
 * Classe de implementação de consultas especificas de Busca.
 * 
 * @author ergomes
 *
 */
public class ISolicitacaoRedomewebRepositoryImpl implements ISolicitacaoRedomewebRepositoryCustom {

	@Autowired
	EntityManager entityManager;

	@Override
	@SuppressWarnings("unchecked")
	public SolicitacaoRedomewebDTO listarSolicitacaoRedomewebDTOPorId(@Param("idSolicitacaoRedomeweb") Long idSolicitacaoRedomeweb) {
		
		StringBuffer sql = new StringBuffer("SELECT sr.sore_id, pe.peex_id, pe.labo_id, so.soli_id, ma.matc_id, ma.doad_id, bu.busc_id, bu.paci_nr_rmr, pe.stpx_id  ")
				.append("FROM solicitacao_redomeweb sr, pedido_exame pe, solicitacao so, match ma, busca bu ") 
				.append("WHERE sr.peex_id = pe.peex_id AND pe.soli_id = so.soli_id AND so.matc_id = ma.matc_id AND ma.busc_id = bu.busc_id AND sr.sore_id_solicitacao_redomeweb = :idSolicitacaoRedomeweb");
		
		Query query = entityManager.createNativeQuery(sql.toString())
				.setParameter("idSolicitacaoRedomeweb", idSolicitacaoRedomeweb);
		
		List<Object[]> list = query.getResultList();
		
		if(!list.isEmpty()) {
			for(Object[] obj : list){
				SolicitacaoRedomewebDTO solRedDto = new SolicitacaoRedomewebDTO();
				solRedDto.setId(Long.valueOf(obj[0].toString()));
				
				PedidoExameDTO pedDto = new PedidoExameDTO();
				pedDto.setId(Long.parseLong(obj[1].toString()));
				pedDto.setIdLaboratorio(Long.parseLong(obj[2].toString()));
				
				SolicitacaoDTO solicitacao = new SolicitacaoDTO();
				solicitacao.setIdSolicitacao(Long.parseLong(obj[3].toString()));
				solicitacao.setIdMatch(Long.parseLong(obj[4].toString()));
				solicitacao.setIdDoador(Long.parseLong(obj[5].toString()));
				solicitacao.setIdBusca(Long.parseLong(obj[6].toString()));
				solicitacao.setRmr(Long.parseLong(obj[7].toString()));
				pedDto.setSolicitacao(solicitacao);

				pedDto.setIdStatusPedidoExame(Long.parseLong(obj[8].toString()));
				
				solRedDto.setPedidoExame(pedDto);
				
				return solRedDto;
			}
		}
		return null;
	}
	
}
