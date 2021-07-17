package br.org.cancer.modred.persistence.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import br.org.cancer.modred.controller.dto.BuscaPaginacaoDTO;
import br.org.cancer.modred.controller.dto.HistoricoBuscaDTO;
import br.org.cancer.modred.controller.page.BuscaJsonPage;
import br.org.cancer.modred.persistence.IBuscaRepositoryCustom;
import br.org.cancer.modred.service.impl.config.Transformation;
import br.org.cancer.modred.util.DateUtils;
import br.org.cancer.modred.util.PaginacaoUtil;

/**
 * Classe de implementação de consultas especificas de Busca.
 * 
 * @author Filipe Paes
 *
 */
public class IBuscaRepositoryImpl implements IBuscaRepositoryCustom {

	@Autowired
	EntityManager entityManager;

	@Override	
	public Page<BuscaPaginacaoDTO> listarBuscas(String loginAnalistaBusca, Long idTipoBuscaCheckList, 
			Long rmr, String nome, List<Long> status, Pageable pageable) {
		List<BuscaPaginacaoDTO> result = new ArrayList<BuscaPaginacaoDTO>();
		
		StringBuffer sql = 
				new StringBuffer("SELECT count(*) over() as totalRegistros, ")
						.append(" P.PACI_NR_RMR as rmr, ")
						.append(" P.PACI_TX_NOME as nome, ")						
						.append(" get_score(P.PACI_NR_RMR) as score, ")	
						.append(" CAVAL.CETR_TX_NOME as nomeCentroAvaliador, ")
						.append(" MIN(BCHECK.prazoExpirar) as prazoExpirar, ")
						.append(" B.BUSC_DT_ANALISE as dataUltimaAnalise, ")
						.append(" MAX(e.evol_dt_criacao) as dataUltimaEvolucao ")
					.append("FROM PACIENTE P JOIN BUSCA B ON(P.PACI_NR_RMR = B.PACI_NR_RMR) ")
					.append("JOIN DIAGNOSTICO D ON(P.PACI_NR_RMR = D.PACI_NR_RMR) ")
					.append("JOIN CID C ON(D.CID_ID = C.CID_ID) ")
					.append("JOIN CID_IDIOMA CI ON(C.CID_ID = CI.CID_ID) ")
					.append("LEFT JOIN CENTRO_TRANSPLANTE CE ON(B.CETR_ID_CENTRO_TRANSPLANTE = CE.CETR_ID) ")
					.append("JOIN AVALIACAO A ON(P.PACI_NR_RMR = A.PACI_NR_RMR) ")
					.append("LEFT JOIN (")
					.append(" 	SELECT BUSC_ID, TIBC_ID, TRUNC(BUCH_DT_CRIACAO + BUCH_NR_AGE) as prazoExpirar ")
					.append(" 	FROM BUSCA_CHECKLIST ")
					.append(" 	WHERE BUCH_DT_VISTO IS NULL ")
					.append(") BCHECK ON(B.BUSC_ID = BCHECK.BUSC_ID) ")
					.append("JOIN USUARIO_CENTRO_TRANSPLANTE UCT ON(P.CETR_ID_AVALIADOR = UCT.CETR_ID) ")
					.append("JOIN USUARIO USUA_BUS ON(USUA_BUS.USUA_ID = UCT.USUA_ID) ")
					.append("JOIN CENTRO_TRANSPLANTE CAVAL ON(UCT.CETR_ID = CAVAL.CETR_ID) ") 
					.append("LEFT JOIN EVOLUCAO E ON ( P.PACI_NR_RMR = E.PACI_NR_RMR ) ")
					.append("WHERE CI.IDIO_ID = 1 ")
					.append("AND B.USUA_ID IS NULL ")
					.append("AND B.STBU_ID IN(:status_busca) ")
					.append("AND USUA_BUS.USUA_TX_USERNAME = :loginAnalistaBusca ");
					
		if(idTipoBuscaCheckList != null){
			sql.append("AND BCHECK.TIBC_ID = :idTipoBuscaCheckList ");
		}
		if(rmr != null){
			sql.append("AND P.PACI_NR_RMR = :rmr ");
		}
		if(nome != null){
			sql.append("AND UPPER(P.PACI_TX_NOME) LIKE UPPER(:nome)");
		}
					
		sql	.append(" GROUP BY P.PACI_NR_RMR, P.PACI_TX_NOME, ")
			.append(" get_score(P.PACI_NR_RMR), CAVAL.CETR_TX_NOME, B.BUSC_DT_ANALISE ");
		
		sql.append("ORDER BY MIN(BCHECK.prazoExpirar) "); 


		@SuppressWarnings("unchecked")
		Query<Object[]> query = (Query<Object[]>) entityManager.createNativeQuery(sql.toString());
		
		query.setParameterList("status_busca", status);
		query.setParameter("loginAnalistaBusca", loginAnalistaBusca);
		
		if(idTipoBuscaCheckList != null){
			query.setParameter("idTipoBuscaCheckList", idTipoBuscaCheckList);
		}
		if(rmr != null){
			query.setParameter("rmr", rmr);
		}
		if(nome != null){
			query.setParameter("nome", "%" + nome + "%");
		}
		
		long total = 0;
		result = Transformation.get(query, BuscaPaginacaoDTO.class);
		if(CollectionUtils.isNotEmpty(result)){
			total = result.get(0).getTotalRegistros();
		}
		
		result.forEach(buscaDTO -> {
			buscaDTO.setPrazoExpirarDiasUteis(DateUtils.calcularExpiracaoPrazo(buscaDTO.getPrazoExpirar()));
		});
		
		
		List<BuscaPaginacaoDTO> listaPaginada = 
				PaginacaoUtil.retornarListaPaginada(result, pageable.getPageNumber(), pageable.getPageSize());

		return new BuscaJsonPage<>(listaPaginada, pageable, total);
	}
	
	/**
	 * Lista o histórico das recusas do CT para a busca informada.
	 * 
	 * @param rmr RMR do paciente associado a busca e, por consequencia, ao histórico da busca.
	 * @return lista de históricos de busca.
	 */
	@SuppressWarnings("unchecked")
	public List<HistoricoBuscaDTO> listarHistoricoBusca(@Param("rmr") Long rmr){
		StringBuffer sql = new StringBuffer("select hist.justificativa, hist.dataAtualizacao, usua.nome as nomeUsuario ")
					.append("from HistoricoBusca hist join hist.busca busc join busc.paciente p join hist.usuario usua ") 
					.append("where p.rmr = :rmr");
		Query<Object[]> query = (Query<Object[]>) entityManager.createQuery(sql.toString());
		query.setParameter("rmr", rmr);
		return (List<HistoricoBuscaDTO>) Transformation.get(query, HistoricoBuscaDTO.class);
	}

}
