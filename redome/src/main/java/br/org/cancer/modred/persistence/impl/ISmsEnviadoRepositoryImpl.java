package br.org.cancer.modred.persistence.impl;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import br.org.cancer.modred.gateway.sms.StatusSms;
import br.org.cancer.modred.persistence.ISmsEnviadoRepositoryCustom;
import br.org.cancer.modred.util.AppUtil;
import br.org.cancer.modred.vo.SmsVo;

/**
 * Implementação de querys personalizadas de SmsEnviados.
 * @author brunosousa
 *
 */
public class ISmsEnviadoRepositoryImpl implements ISmsEnviadoRepositoryCustom {
	
	@Autowired
	private EntityManager entityManager;
	
	@Override
	public Page<SmsVo> listarSmsEniviados(Long dmr, LocalDate dataInicial, LocalDate dataFinal, StatusSms status,
			PageRequest paginacao) {

		StringBuilder sql = new StringBuilder("SELECT SE.SMEN_DT_CRIACAO, SE.SMEN_IN_IDENTICACA_GATEWAY_SMS, ")
				.append("CT.COTE_NR_COD_AREA, CT.COTE_NR_NUMERO, D.DOAD_NR_DMR, count(*) over() ")
				.append("FROM SMS_ENVIADO SE ") 
				.append("INNER JOIN CONTATO_TELEFONICO CT ON (CT.COTE_ID = SE.COTE_ID) ") 
				.append("INNER JOIN PEDIDO_CONTATO_SMS PCS ON (PCS.PECS_ID = SE.PECS_ID) ") 
				.append("INNER JOIN PEDIDO_CONTATO PC ON (PC.PECO_ID = PCS.PECO_ID) ") 
				.append("INNER JOIN DOADOR D ON (PC.DOAD_ID = D.DOAD_ID) ");
		
		String separador = "where ";
		if (dataInicial != null) {
			sql.append(separador + "SE.SMEN_DT_CRIACAO >= :DATAINICIAL ");
			separador = "and ";
		}
		if (dataFinal != null) {
			sql.append(separador + "SE.SMEN_DT_CRIACAO <= :DATAFINAL ");
			separador = "and ";
		}
		if (dmr != null) {
			sql.append(separador + "D.DOAD_NR_DMR = :DMR ");
			separador = "and ";
		}
		if (status != null) {
			sql.append(separador + "SE.SMEN_IN_IDENTICACA_GATEWAY_SMS = :STATUS ");
		}
		
		sql.append("ORDER BY SE.SMEN_DT_CRIACAO DESC ");
		
		@SuppressWarnings("unchecked")
		Query<Object[]> query = (Query<Object[]>) entityManager.createNativeQuery(sql.toString());

		if (paginacao != null) {
			query.setFirstResult(new Long(paginacao.getOffset()).intValue());
			query.setMaxResults(paginacao.getPageSize());
		}
		
		if (dataInicial != null) {
			query.setParameter("DATAINICIAL", dataInicial.atStartOfDay());
		}
		if (dataFinal != null) {
			query.setParameter("DATAFINAL", dataFinal.atTime(23, 59, 59));
		}
		if (dmr != null) {
			query.setParameter("DMR", dmr);
		}
		if (status != null) {
			query.setParameter("STATUS", status.getId());
		}
		
		List<SmsVo> retorno = new ArrayList<>();
		
		List<Object[]> list = query.getResultList();

		int count = 0;
		if (list != null) {
			for (Object[] data : list) {
				
				SmsVo sms = new SmsVo();
				if (data[0] != null) {
					sms.setData(( (Timestamp) data[0] ).toLocalDateTime());
				}
				if (data[1] != null) {
					sms.setStatus(StatusSms.get(Integer.parseInt(data[1].toString())));
				}
				if (data[2] != null && data[3] != null) {
					sms.setTelefone(String.format("(%s) %s", data[2].toString(), AppUtil.formatarTelefoneCelular(new Long(data[3].toString()))) );
				}
				if (data[4] != null) {
					sms.setDmr(Long.parseLong(data[4].toString()));
				}
				
				if (data[5] != null) {
					count = Integer.valueOf(data[5].toString());
				}
				
				retorno.add(sms);
			}
		}
		
		return new PageImpl<>(retorno, paginacao, count);
	}

}
