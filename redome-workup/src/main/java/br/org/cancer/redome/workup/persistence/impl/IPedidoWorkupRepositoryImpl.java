package br.org.cancer.redome.workup.persistence.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import br.org.cancer.redome.workup.dto.ConsultaTarefasWorkupDTO;
import br.org.cancer.redome.workup.dto.FiltroListaTarefaWorkupDTO;
import br.org.cancer.redome.workup.model.PedidoWorkup;
import br.org.cancer.redome.workup.persistence.IPedidoWorkupRepositoryCustom;

/**
 * Classe de persistência para a classe de match preliminar.
 * 
 * @author bruno.sousa 
 */
public class IPedidoWorkupRepositoryImpl extends SimpleJpaRepository<PedidoWorkup, Long> implements IPedidoWorkupRepositoryCustom {

	private static final Logger LOGGER = LoggerFactory.getLogger(IPedidoWorkupRepositoryImpl.class);
	
	EntityManager entityManager;
	
	@Autowired
	public IPedidoWorkupRepositoryImpl(EntityManager entityManager) {
		super(PedidoWorkup.class, entityManager);
		this.entityManager = entityManager;
	}
	

	@Override
	public List<ConsultaTarefasWorkupDTO> listarTarefasWorkupView(FiltroListaTarefaWorkupDTO filtro) {
	
		StringBuilder sql = new StringBuilder("SELECT PACI_NR_RMR, DOAD_IDENTIFICACAO, REGI_TX_NOME, FOCE_TX_DESCRICAO, PRES_DT_RESULTADO_WORKUP_1, PRES_DT_COLETA_1, PEWO_DT_RESULTADO, PEWO_DT_COLETA, ")
				.append("TITA_TX_DESCRICAO_EXIBIR, CETR_TX_NOME, MEDI_TX_NOME, PRES_DT_CRIACAO, TARE_ID, TITA_ID, TARE_NR_STATUS, PRES_ID, PEWO_ID, CETR_ID_TRANSPLANTE, ")
				.append("PACI_TX_NOME, PACI_DT_NASCIMENTO, PRES_IN_TIPO, REWO_ID, PECL_ID, PELO_ID, PEAW_ID, DOAD_ID, USUA_TX_NOME " )
				.append("FROM vw_tarefa_workup ")
				.append("where perf_id in (:idsPerfis) and tare_nr_status in (:idsStatus) ");
				
				if(filtro.getIdUsuario() != null) {
					sql.append("and usua_id=:idUsuario ");
				}else if(filtro.getIdCentroTransplante() != null) {
					sql.append("and cetr_id_transplante=:idCentroSelecionado ");
				}else if(filtro.getIdCentroColeta() != null) {
					sql.append("and cetr_id_coleta=:idCentroSelecionado ");
				}
		
		@SuppressWarnings("unchecked")
		Query<Object[]> query = (Query<Object[]>) entityManager.createNativeQuery(sql.toString());

		query.setParameter("idsPerfis", filtro.getPerfilResponsavel());
		query.setParameter("idsStatus", filtro.getStatusTarefa());
		if(filtro.getIdUsuario() != null) {
			query.setParameter("idUsuario", filtro.getIdUsuario());
		}else if(filtro.getIdCentroTransplante() != null) {
			query.setParameter("idCentroSelecionado", filtro.getIdCentroTransplante());
		}else if(filtro.getIdCentroColeta() != null) {
			query.setParameter("idCentroSelecionado", filtro.getIdCentroColeta());
		}
		
		List<Object[]> lista = query.getResultList();
		if (lista != null && !lista.isEmpty()) {
			return lista.stream().map(ConsultaTarefasWorkupDTO::popularViewTarefaWorkup).collect(Collectors.toList());
		}
		
		return new ArrayList<>();
	}

	@Override
	public List<ConsultaTarefasWorkupDTO> listarSolicitacoesWorkupView(FiltroListaTarefaWorkupDTO filtro) {
	
		StringBuilder sql = new StringBuilder("SELECT PACI_NR_RMR, DOAD_ID, REGI_TX_NOME, FOCE_TX_DESCRICAO, PRES_DT_RESULTADO_WORKUP_1, PRES_DT_COLETA_1, PEWO_DT_RESULTADO, ")
				.append("PEWO_DT_COLETA, FAWO_TX_DESCRICAO, CETR_TX_NOME, MEDI_TX_NOME, PRES_DT_CRIACAO ")
				.append("FROM vw_solicitacao_workup ")
				.append("WHERE ");
				
				if(filtro.getIdUsuarioResponsavel() != null) {
					sql.append("usua_id_responsavel=:idUsuarioResponsavel ");
				}else if(filtro.getIdCentroTransplante() != null) {
					sql.append("cetr_id_transplante=:idCentroSelecionado ");
				}else if(filtro.getIdCentroColeta() != null) {
					sql.append("cetr_id_coleta=:idCentroSelecionado ");
				}
				
				sql.append("and soli_id not in (select soli_id from vw_tarefa_workup where perf_id in (:idsPerfis) and tare_nr_status in (:idsStatus) "); 
				
				if(filtro.getIdUsuario() != null) {
					sql.append("and usua_id=:idUsuario ) ");
				}else if(filtro.getIdCentroTransplante() != null) {
						sql.append("and cetr_id_transplante=:idCentroSelecionado ) ");
				}else if(filtro.getIdCentroColeta() != null) {
					sql.append("and cetr_id_coleta=:idCentroSelecionado ) ");
				}
				

		@SuppressWarnings("unchecked")
		Query<Object[]> query = (Query<Object[]>) entityManager.createNativeQuery(sql.toString());

		query.setParameter("idsPerfis", filtro.getPerfilResponsavel());
		query.setParameter("idsStatus", filtro.getStatusTarefa());
		
		if(filtro.getIdUsuarioResponsavel() != null && filtro.getIdUsuario() != null) {
			query.setParameter("idUsuarioResponsavel", filtro.getIdUsuarioResponsavel());
			query.setParameter("idUsuario", filtro.getIdUsuario());
		}else if(filtro.getIdCentroTransplante() != null) {
			query.setParameter("idCentroSelecionado", filtro.getIdCentroTransplante());
		}else if(filtro.getIdCentroColeta() != null) {
			query.setParameter("idCentroSelecionado", filtro.getIdCentroColeta());
		}
		
		List<Object[]> lista = query.getResultList();
		if (lista != null && !lista.isEmpty()) {
			return lista.stream().map(ConsultaTarefasWorkupDTO::popularViewSolicitacaofaWorkup).collect(Collectors.toList());
		}
		
		return new ArrayList<>();
	}

}
