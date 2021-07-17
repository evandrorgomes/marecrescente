package br.org.cancer.modred.persistence.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import br.org.cancer.modred.model.DoadorNacional;
import br.org.cancer.modred.persistence.IDoadorNacionalRepositoryCustom;
import br.org.cancer.modred.util.DateUtils;
import br.org.cancer.modred.vo.ConsultaDoadorNacionalVo;

/**
 * @author fillipe.queiroz Classe de persistência para controlar a paginação da busca do doador.
 */
public class IDoadorNacionalRepositoryImpl implements IDoadorNacionalRepositoryCustom {

	private static final Logger LOGGER = LoggerFactory.getLogger(IDoadorNacionalRepositoryImpl.class);

	@Autowired
	EntityManager entityManager;

	@Override
	public List<ConsultaDoadorNacionalVo> listarDoadoresNacionaisVo(Pageable pageable, Long dmr, String nome, String cpf) {
		List<ConsultaDoadorNacionalVo> doadores = new ArrayList<ConsultaDoadorNacionalVo>();

		StringBuffer sql = new StringBuffer(
				"SELECT count(DOAD.DOAD_ID) over (), DOAD.DOAD_ID, DOAD.DOAD_NR_DMR, DOAD.DOAD_TX_NOME, DOAD.DOAD_DT_RETORNO_INATIVIDADE, ")
						.append(" MS.MOSD_ID, MS.MOSD_TX_DESCRICAO, ST.STDO_ID, ST.STDO_TX_DESCRICAO ")
						.append(" FROM DOADOR DOAD")
						.append(" INNER JOIN STATUS_DOADOR ST ON DOAD.STDO_ID = ST.STDO_ID")
						.append(" LEFT JOIN MOTIVO_STATUS_DOADOR MS ON DOAD.MOSD_ID = MS.MOSD_ID")
						.append(" WHERE DOAD.DOAD_IN_TIPO IN (0,3) ");

		if(dmr != null) {
			sql.append(" AND DOAD.DOAD_NR_DMR = :dmr");
		}

		if(nome != null) {
			sql.append(" AND UPPER(DOAD.DOAD_TX_NOME) like :nome");
		}

		if(cpf != null) {
			sql.append(" AND DOAD.DOAD_TX_CPF = :cpf");
		}

		sql.append(" ORDER BY DOAD.DOAD_TX_NOME");

		Query query = entityManager.createNativeQuery(sql.toString());

		if (pageable != null) {
			query.setFirstResult(new Long(pageable.getOffset()).intValue());
			query.setMaxResults(pageable.getPageSize());
		}

		if (dmr != null) {
			query.setParameter("dmr", dmr);
		}
		if (nome != null) {
			query.setParameter("nome", "%" + nome.toUpperCase() + "%");
		}
		if (cpf != null) {
			query.setParameter("cpf", cpf.replace(".", "").replace("-", ""));
		}

		@SuppressWarnings("unchecked")
		List<Object[]> list = query.getResultList();

		if (list != null) {
			for (Object[] data : list) {
				
				ConsultaDoadorNacionalVo doador = new ConsultaDoadorNacionalVo();
				doador.setIdDoador(Long.parseLong(data[1].toString()));
				doador.setDmr(Long.parseLong(data[2].toString()));

				if (data[3] != null) {
					doador.setNomeDoador(data[3].toString());
				}
				
				if (data[4] != null) {
					doador.setDataRetornoInatividade(DateUtils.converterParaLocalDate(( (java.util.Date) data[4] )));
				}
				
				if(data[5] != null) {
					doador.setIdMotivoDoador(Long.parseLong(data[5].toString()));
					doador.setDescricaoMotivoDoador(data[6].toString());
				}
				
				doador.setIdStatusDoador(Long.parseLong(data[7].toString()));
				doador.setDescricaoStatusDoador(data[8].toString());

				doadores.add(doador);
			}
		}

		LOGGER.debug("listarDoadores >>> {}", doadores.size());
		return doadores;
	}

	private static String montarQueryPeloMapaParametros(Map<String, Object> mapaParametros) {
		if (mapaParametros.size() == 0) {
			return "";
		}
		String query = " WHERE ";
		int count = 0;
		for (Map.Entry<String, Object> entry : mapaParametros.entrySet()) {
			if (entry.getValue() != null) {
				String[] coluna = entry.getKey().split(",");
				if (count > 0) {
					query += "AND ";
				}

				query += coluna[0] + coluna[1] + " ";
				count++;
			}
		}
		return query;

	}
	
	@Override
	public List<DoadorNacional> listarDoadoresNacionais(Pageable pageable, Long dmr, String nome) {
		List<DoadorNacional> doadores = new ArrayList<DoadorNacional>();

		StringBuffer sql = new StringBuffer(
				"SELECT count(DOAD.DOAD_ID) over (), DOAD.DOAD_ID, DOAD.DOAD_NR_DMR, DOAD.DOAD_TX_NOME ")
						.append(" FROM DOADOR DOAD");

		Map<String, Object> mapaParametros = new HashMap<String, Object>();
		mapaParametros.put("DOAD.DOAD_NR_DMR,=:dmr", dmr);
		mapaParametros.put("UPPER(DOAD.DOAD_TX_NOME), like :nome", nome);

		sql.append(montarQueryPeloMapaParametros(mapaParametros));

		sql.append(" AND DOAD.DOAD_IN_TIPO IN (0,2)");
		sql.append(" ORDER BY DOAD.DOAD_TX_NOME");

		Query query = entityManager.createNativeQuery(sql.toString());

		if (pageable != null) {
			query.setFirstResult(new Long(pageable.getOffset()).intValue());
			query.setMaxResults(pageable.getPageSize());
		}
		if (dmr != null) {
			query.setParameter("dmr", dmr);
		}
		if (nome != null) {
			query.setParameter("nome", "%" + nome.toUpperCase() + "%");
		}

		@SuppressWarnings("unchecked")
		List<Object[]> list = query.getResultList();

		if (list != null) {
			for (Object[] data : list) {
				
				DoadorNacional doador = new DoadorNacional();
				doador.setId(Long.parseLong(data[1].toString()));
				doador.setDmr(Long.parseLong(data[2].toString()));

				if (data[3] != null) {
					doador.setNome(data[3].toString());
				}

				doadores.add(doador);
			}
		}

		LOGGER.debug("listarDoadores >>> {}", doadores.size());
		return doadores;
	}

}
