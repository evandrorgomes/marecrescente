package br.org.cancer.redome.tarefa.persistence.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import br.org.cancer.redome.tarefa.model.Processo;
import br.org.cancer.redome.tarefa.model.domain.ITipoProcesso;
import br.org.cancer.redome.tarefa.model.domain.StatusProcesso;
import br.org.cancer.redome.tarefa.persistence.IProcessoRepositoryCustom;

/**
 * Implementação da inteface adicional que concentra e abstrai regras de pesquisa sobre os 
 * objetos do motor de processos da plataforma redome. Especificamente este repositório serve como uma abstração 
 * para pesquisa coleção de instâncias da entidade Processo.
 * 
 * @author Thiago Moraes
 *
 */
public class IProcessoRepositoryImpl implements IProcessoRepositoryCustom {

	private static final Logger LOGGER = LoggerFactory.getLogger(IProcessoRepositoryImpl.class);

	@Autowired
	EntityManager entityManager;

	@Override
	public List<Processo> listarProcessos(Long rmr, Long idDoador, ITipoProcesso tipo, StatusProcesso status) {

		List<Processo> processos = new ArrayList<Processo>();
		long total = 0;
		
		try {
			StringBuffer sql = new StringBuffer(
					"SELECT count(PROC_ID) over (), PROC_ID, PROC_DT_CRIACAO, PROC_DT_ATUALIZACAO, PROC_NR_TIPO, PACI_NR_RMR, PROC_NR_STATUS, DOAD_ID FROM")
			             .append(" PROCESSO WHERE PROC_ID>0 ");

			if (tipo != null) {
				sql.append(" AND PROC_NR_TIPO=:tipo");
			}
			if (status != null) {
				sql.append(" AND PROC_NR_STATUS=:status");
			}
			if (rmr != null) {
				sql.append(" AND PACI_NR_RMR=:rmr");
			}
			
			if (idDoador != null) {
				sql.append(" AND DOAD_ID=:idDoador");
			}

			sql.append(" ORDER BY PROC_ID DESC");

			Query query = entityManager.createNativeQuery(sql.toString());
			
			if (tipo != null) {
				query.setParameter("tipo", tipo.getId());
			}
			if (status != null) {
				query.setParameter("status", status.getId());
			}
			if (rmr != null ) {
				query.setParameter("rmr", rmr);
			}
			if (idDoador != null ) {
				query.setParameter("idDoador", idDoador);
			}

			@SuppressWarnings("unchecked")
			List<Object[]> list = query.getResultList();

			if (list != null) {
				for (Object[] data : list) {
				    total = Long.parseLong(data[0].toString());
				    
				    Processo processo = Processo.builder().tipo(Long.parseLong(data[4].toString()))
				    		.id(Long.parseLong(data[1].toString()))
				    		.dataCriacao(( (Timestamp) data[2] ).toLocalDateTime())
				    		.dataAtualizacao(( (Timestamp) data[3] ).toLocalDateTime())
				    		.status(Long.parseLong(data[6].toString()))
				    		.rmr(data[5] != null ? Long.parseLong(data[5].toString()) : null)
				    		.idDoador(data[7] != null ? Long.parseLong(data[7].toString()) : null)
				    		.build();

				    processos.add(processo);
				}
			}
		}
		catch (Exception e) {
			
			LOGGER.error("listarProcessos >>> {}", e.getMessage());
		}

		LOGGER.debug("listarProcessos >>> {}", processos.size());
		return processos;

	}

	/*
	 * @Override public int atualizarStatusProcesso(Long processoId, StatusProcesso
	 * status, LocalDateTime now) {
	 * 
	 * int result = 0; try { StringBuffer sql = new StringBuffer(
	 * "UPDATE PROCESSO SET PROC_NR_STATUS= :status, PROC_DT_ATUALIZACAO= :now WHERE "
	 * ) .append("PROC_ID = :processoId AND PROC_NR_STATUS <> :fstatus");
	 * 
	 * Query query = entityManager.createNativeQuery(sql.toString());
	 * query.setParameter("status", status.getId()); query.setParameter("now", now);
	 * query.setParameter("processoId", processoId); query.setParameter("fstatus",
	 * StatusProcesso.TERMINADO.getId());
	 * 
	 * result = query.executeUpdate(); } catch (Exception e) {
	 * LOGGER.error("atualizarStatusProcesso >>> {}", e.getMessage()); }
	 * 
	 * LOGGER.debug("atualizarStatusProcesso >>> {}, updated={}", status, result);
	 * 
	 * return result;
	 * 
	 * }
	 */

}
