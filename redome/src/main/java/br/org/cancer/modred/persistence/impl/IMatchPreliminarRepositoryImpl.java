package br.org.cancer.modred.persistence.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import br.org.cancer.modred.model.MatchPreliminar;
import br.org.cancer.modred.model.domain.FasesMatch;
import br.org.cancer.modred.model.domain.TiposDoador;
import br.org.cancer.modred.persistence.IMatchPreliminarRepositoryCustom;
import br.org.cancer.modred.vo.ViewMatchPreliminarQualificacaoVO;

/**
 * Classe de persistência para a classe de match preliminar.
 * 
 * @author bruno.sousa 
 */
public class IMatchPreliminarRepositoryImpl extends SimpleJpaRepository<MatchPreliminar, Long> implements IMatchPreliminarRepositoryCustom {

	private static final Logger LOGGER = LoggerFactory.getLogger(IMatchPreliminarRepositoryImpl.class);
	
	EntityManager entityManager;
	
	@Autowired
	public IMatchPreliminarRepositoryImpl(EntityManager entityManager) {
		super(MatchPreliminar.class, entityManager);
		this.entityManager = entityManager;
	}
	
	@Override
	public List<Object[]> listarMatchsPreliminares(Long idBuscaPreliminar, List<TiposDoador> tiposDoador, List<FasesMatch> fases) {
		
		StringBuilder sql = new StringBuilder("SELECT * FROM match_preliminar_dto ")				
				.append("WHERE DOAD_TX_FASE IN (:fases) ")
				.append("AND BUPR_ID = :idBuscaPreliminar ")
				.append("AND DOAD_IN_TIPO IN (:tiposDoador) ");
		
		@SuppressWarnings("unchecked")
		Query<Object[]> query = (Query<Object[]>) entityManager.createNativeQuery(sql.toString());
				
		query.setParameter("idBuscaPreliminar", idBuscaPreliminar);
		query.setParameter("fases", fases.stream().map(FasesMatch::getId).collect(Collectors.toList()) );		
		query.setParameterList("tiposDoador", tiposDoador.stream().map(TiposDoador::getId).collect(Collectors.toList()) );
		
		return query.getResultList();
		
	}

	@Override
	public List<Object[]> listarMatchsPorBuscaPreliminarIdDoador(
			Long idBuscaPreliminar, List<Long> listaIdDoador) {
		
		StringBuilder sql = new StringBuilder("SELECT * FROM match_preliminar_dto ")								
				.append("WHERE BUPR_ID = :idBuscaPreliminar ")
				.append("AND DOAD_ID IN (:idsDoador) ");
		
		@SuppressWarnings("unchecked")
		Query<Object[]> query = (Query<Object[]>) entityManager.createNativeQuery(sql.toString());
		
		query.setParameter("idBuscaPreliminar", idBuscaPreliminar);
		query.setParameterList("idsDoador", listaIdDoador);
		
		return query.getResultList();
		
	}
	
	@Override
	public List<ViewMatchPreliminarQualificacaoVO> listarViewMatchPreliminarQualificacao(Long idBuscaPreliminar) {
	
		StringBuilder sql = new StringBuilder("SELECT BUPR_ID, GEDO_ID, LOCU_ID, ") 
				.append("TROCA, POS_1_TIPO, POS_2_TIPO, POS_1_LETRA, POS_2_LETRA ") 
				.append("FROM vw_match_preliminar_qualif ")
				.append("where bupr_id = :idBuscaPreliminar");
		
		@SuppressWarnings("unchecked")
		Query<Object[]> query = (Query<Object[]>) entityManager.createNativeQuery(sql.toString());
		
		query.setParameter("idBuscaPreliminar", idBuscaPreliminar);
		
		List<Object[]> lista = query.getResultList();
		if (lista != null && !lista.isEmpty()) {
			return lista.stream().map(ViewMatchPreliminarQualificacaoVO::popularViewMatchPreliminarQualificacao).collect(Collectors.toList());
		}
		
		return new ArrayList<>();
	}
	

}
