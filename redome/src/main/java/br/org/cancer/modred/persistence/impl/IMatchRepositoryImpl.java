package br.org.cancer.modred.persistence.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import br.org.cancer.modred.model.domain.FasesMatch;
import br.org.cancer.modred.model.domain.TiposDoador;
import br.org.cancer.modred.persistence.IMatchRepositoryCustom;

/**
 * Classe de persistência para a classe de match.
 * 
 * @author bruno.sousa 
 */
public class IMatchRepositoryImpl implements IMatchRepositoryCustom {

	private static final Logger LOGGER = LoggerFactory.getLogger(IMatchRepositoryImpl.class);
	
	@Autowired
	EntityManager entityManager;
	
	@Override
	public List<Object[]> listarMatchPorPacienteSituacaoTipoDoadorStatus(Long rmr, List<FasesMatch> situacoes, 
			List<TiposDoador> tiposDoador, boolean status, boolean disponibilizado) {
		
		StringBuilder sql = new StringBuilder("SELECT * FROM match_dto ")
				.append("WHERE MATC_IN_STATUS = :status ")
				.append("AND DOAD_TX_FASE IN (:situacoes) ")
				.append("AND PACI_NR_RMR = :rmr ")
				.append("AND DOAD_IN_TIPO IN (:tiposDoador) ")
				.append("AND MATC_IN_DISPONIBILIZADO = :disponibilizado ");
		
		
		@SuppressWarnings("unchecked")
		Query<Object[]> query = (Query<Object[]>) entityManager.createNativeQuery(sql.toString());
		
		query.setParameter("status", status ? 1 : 0);
		
		query.setParameter("rmr", rmr);
		query.setParameterList("situacoes", situacoes.stream().map(FasesMatch::getId).collect(Collectors.toList()) );		
		query.setParameterList("tiposDoador", tiposDoador.stream().map(TiposDoador::getId).collect(Collectors.toList()) );
		query.setParameter("disponibilizado", disponibilizado);
		
		return query.getResultList();
	}
	
	@Override
	public Object[] obterMatchPorId(Long id) {
		
		StringBuilder sql = new StringBuilder("SELECT * FROM match_dto ")
				.append("WHERE MATC_ID = :id");
		
		@SuppressWarnings("unchecked")
		Query<Object[]> query = (Query<Object[]>) entityManager.createNativeQuery(sql.toString());
				
		query.setParameter("id", id);
		
		return query.getSingleResult();
	}
	
	
	@Override
	public List<Object[]> listarMatchsPorPacienteStatusIdDoador(Long rmr, boolean status, List<Long> listaIdDoador) {
		
		StringBuilder sql = new StringBuilder("SELECT * FROM match_dto ")
				.append("WHERE MATC_IN_STATUS = :status ")
				.append("AND PACI_NR_RMR = :rmr ")
				.append("AND DOAD_ID IN (:idsDoador) ORDER BY DOAD_NR_DMR ASC ");
		
		@SuppressWarnings("unchecked")
		Query<Object[]> query = (Query<Object[]>) entityManager.createNativeQuery(sql.toString());
		
		query.setParameter("status", status ? 1 : 0);
		
		query.setParameter("rmr", rmr);
		query.setParameterList("idsDoador", listaIdDoador);
		
		return  query.getResultList();		
	}
	
	@Override
	public List<Object[]> listarMatchInativosPorPacienteSituacaoTipoDoadorComSolicitacao(Long rmr, String situacao,
			List<TiposDoador> tiposDoador) {
		
		StringBuilder sql = new StringBuilder("SELECT m.* FROM match_dto m, ")
				.append("SOLICITACAO S  ")
				.append("WHERE S.MATC_ID = M.MATC_ID ")
				.append("AND S.SOLI_NR_STATUS in (1, 2, 3) ")
				.append("AND m.MATC_IN_STATUS = 0 ")
				.append("AND m.DOAD_TX_FASE IN (:situacoes) ")
				.append("AND m.PACI_NR_RMR = :rmr ")
				.append("AND m.DOAD_IN_TIPO IN (:tiposDoador) ");
		
		@SuppressWarnings("unchecked")
		Query<Object[]> query = (Query<Object[]>) entityManager.createNativeQuery(sql.toString());
		
		query.setParameter("rmr", rmr);
		query.setParameter("situacoes", situacao);		
		query.setParameterList("tiposDoador", tiposDoador.stream().map(TiposDoador::getId).collect(Collectors.toList()) );
		
		return query.getResultList();
	}

}
