package br.org.cancer.modred.persistence.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import br.org.cancer.modred.controller.dto.RespostaPendenciaDTO;
import br.org.cancer.modred.controller.page.PendenciaJsonPage;
import br.org.cancer.modred.model.Pendencia;
import br.org.cancer.modred.model.StatusPendencia;
import br.org.cancer.modred.model.TipoPendencia;
import br.org.cancer.modred.model.domain.StatusPendencias;
import br.org.cancer.modred.persistence.IPendenciaRepositoryCustom;

/**
 * @author bruno.sousa Classe de persistência para controlar a paginação da busca de pendências.
 */
public class IPendenciaRepositoryImpl implements IPendenciaRepositoryCustom {

	private static final Logger LOGGER = LoggerFactory.getLogger(IPendenciaRepositoryImpl.class);

	@Autowired
	private EntityManager entityManager;

	@Override
	@SuppressWarnings("unchecked")
	public PendenciaJsonPage<Pendencia> findAllCustom(Pageable pageable, Long avaliacaoId, StatusPendencias... status) {
		LOGGER.info("inicio da busca de pendencias no repositorio");

		StringBuilder sqlCount = new StringBuilder("SELECT COUNT(1) FROM PENDENCIA P WHERE AVAL_ID = :avaliacaoId ");

		StringBuilder sqlList = new StringBuilder("SELECT P.PEND_ID, P.PEND_DT_CRIACAO, P.PEND_TX_DESCRICAO, ")
				.append("P.STPE_ID, SP.STPE_TX_DESCRICAO, ")
				.append("P.TIPE_ID,	TP.TIPE_TX_DESCRICAO ")
				.append("FROM PENDENCIA P ,")
				.append("STATUS_PENDENCIA SP ,")
				.append("TIPO_PENDENCIA TP ")
				.append("WHERE P.AVAL_ID = :avaliacaoId ")
				.append("AND SP.STPE_ID = P.STPE_ID ")
				.append("AND TP.TIPE_ID = P.TIPE_ID ");

		List<Long> statuses = new ArrayList<>();
		if (status != null) {
			sqlCount.append(" AND STPE_ID IN (:status) ");
			sqlList.append(" AND P.STPE_ID IN (:status) ");

			sqlList.append(" ORDER BY DECODE ( P.STPE_ID");

			int ordem = 1;
			for (StatusPendencias stPendencia : status) {
				statuses.add(stPendencia.getId());
				sqlList.append(", ").append(stPendencia.getId()).append(",").append(ordem);
				ordem++;
			}

			sqlList.append(")");
		}

		Query countQuery = entityManager.createNativeQuery(sqlCount.toString());
		countQuery.setParameter("avaliacaoId", avaliacaoId);

		Query listQuery = entityManager.createNativeQuery(sqlList.toString())
				.setParameter("avaliacaoId", avaliacaoId)
				.setFirstResult(new Long(pageable.getOffset()).intValue())
				.setMaxResults(pageable.getPageSize());

		if (status != null) {
			countQuery.setParameter("status", statuses);
			listQuery.setParameter("status", statuses);
		}

		Long quantidadeRegistros = ( (BigDecimal) countQuery.getSingleResult() ).longValue();

		LOGGER.info("inicio do select paginado");
		List<Object[]> list = listQuery.getResultList();
		LOGGER.info("fim do select paginado");

		List<Pendencia> lista = new ArrayList<>();
		if (list != null) {
			for (Object[] data : list) {

				Pendencia pendencia = new Pendencia(( (BigDecimal) data[0] ).longValue(),
						( (Timestamp) data[1] ).toLocalDateTime(),
						(String) data[2],
						new StatusPendencia(( (BigDecimal) data[3] ).longValue(), (String) data[4]),
						new TipoPendencia(( (BigDecimal) data[5] ).longValue(), (String) data[6]));

				lista.add(pendencia);
			}
		}

		LOGGER.info("fim da busca de pendencia no repositorio");

		return new PendenciaJsonPage<Pendencia>(lista, pageable, quantidadeRegistros);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RespostaPendenciaDTO> listarRespostas(Long pendenciaId) {
		List<RespostaPendenciaDTO> respostas = null;

		StringBuilder sqlList = new StringBuilder(
				"SELECT R.REPE_TX_RESPOSTA, U.USUA_TX_USERNAME, R.REPE_DT_DATA, R.EXAM_ID, R.EVOL_ID ")
						.append("FROM RESPOSTA_PENDENCIA R, ASSOCIA_RESPOSTA_PENDENCIA ARP, PENDENCIA P, USUARIO U ")
						.append("WHERE R.REPE_ID = ARP.REPE_ID ")
						.append("AND ARP.PEND_ID = P.PEND_ID ")
						.append("AND U.USUA_ID = R.USUA_ID ")
						.append("AND P.PEND_ID = :pendenciaId ")
						.append("ORDER BY R.REPE_DT_DATA ASC ");

		List<Object[]> list = entityManager.createNativeQuery(sqlList.toString())
				.setParameter("pendenciaId", pendenciaId).getResultList();

		if (list != null) {
			respostas = new ArrayList<>();

			for (Object[] data : list) {

				RespostaPendenciaDTO resposta = new RespostaPendenciaDTO();

				if (data[0] != null) {
					resposta.setResposta((String) data[0]);
				}

				resposta.setUsuario((String) data[1]);
				resposta.setData(( (Timestamp) data[2] ).toLocalDateTime());

				if (data[3] != null) {
					resposta.setExame(( (BigDecimal) data[3] ).longValue());
				}

				if (data[4] != null) {
					resposta.setEvolucao(( (BigDecimal) data[4] ).longValue());
				}

				respostas.add(resposta);
			}
		}

		return respostas;
	}

}
