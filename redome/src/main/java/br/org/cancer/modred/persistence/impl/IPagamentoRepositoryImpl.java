package br.org.cancer.modred.persistence.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import br.org.cancer.modred.model.Busca;
import br.org.cancer.modred.model.Match;
import br.org.cancer.modred.model.Pagamento;
import br.org.cancer.modred.model.Registro;
import br.org.cancer.modred.model.StatusPagamento;
import br.org.cancer.modred.model.TipoServico;
import br.org.cancer.modred.model.domain.StatusPagamentos;
import br.org.cancer.modred.persistence.IPagamentoRepositoryCustom;

/**
 * Implementação da inteface adicional que concentra e abstrai regras de pesquisa sobre os objetos do motor de processos da
 * plataforma redome. Especificamente este repositório serve como uma abstração para pesquisa coleção de instâncias da entidade
 * TarefaDTO.
 * 
 * @author Thiago Moraes
 *
 */
public class IPagamentoRepositoryImpl extends SimpleJpaRepository<Pagamento, Long> implements IPagamentoRepositoryCustom {

	private static final Logger LOGGER = LoggerFactory.getLogger(IPagamentoRepositoryImpl.class);

	@Autowired
	private EntityManager entityManager;

	@Autowired
	public IPagamentoRepositoryImpl(EntityManager em) {
		super(Pagamento.class, em);
	}
	
	@Override
	public Page<Pagamento> listarPagamentos(Long idMatch, Long idRegistro, Long idTipoServico,
			List<StatusPagamentos> statusPagamentos, Long relacaoEntidadeId, Pageable pageable) {
		List<Pagamento> pagamentos = new ArrayList<Pagamento>();
		StringBuffer sql = new StringBuffer(
				" SELECT P.PAGA_ID, P.PAGA_DT_CRIACAO, P.PAGA_DT_ATUALIZACAO, P.PAGA_IN_COBRACA, P.PAGA_ID_OBEJTORELACIONADO, ")  
				.append("P.TISE_ID, TS.TISE_TX_SIGLA, ")
				.append("P.STPA_ID, SP.STPA_TX_DESCRICAO, ")
				.append("P.REGI_ID, R.REGI_TX_NOME, ")
				.append("P.MATC_ID, M.BUSC_ID, M.DOAD_ID, COUNT(*) OVER() ") 
				.append("FROM PAGAMENTO P INNER JOIN TIPO_SERVICO TS ON (P.TISE_ID = TS.TISE_ID) ")
				.append("INNER JOIN STATUS_PAGAMENTO SP ON(P.STPA_ID = SP.STPA_ID) ")
				.append("INNER JOIN REGISTRO R ON (P.REGI_ID = R.REGI_ID) ")
				.append("INNER JOIN MATCH M ON (P.MATC_ID = M.MATC_ID) ");

		String condicao = "where ";
		if (idTipoServico != null) {
			sql.append(condicao + "P.TISE_ID = :tipoServico ");
		}
		if (idMatch != null) {
			sql.append(" AND P.MATC_ID = :match");
		}
		if (idRegistro != null) {
			sql.append(" AND P.REGI_ID = :registro");
		}		
		if (statusPagamentos != null && !statusPagamentos.isEmpty()) {
			sql.append(" AND P.STPA_ID in (:statusPagamentos)");
		}
		if (relacaoEntidadeId != null) {
			sql.append(" AND P.PAGA_ID_OBEJTORELACIONADO = :relacaoEntidadeId");
		}

		sql.append(" ORDER BY PAGA_ID ASC");
		
		@SuppressWarnings("unchecked")
		Query<Object[]> query = (Query<Object[]>) entityManager.createNativeQuery(sql.toString());

		if (pageable != null) {
			query.setFirstResult(new Long(pageable.getOffset()).intValue());
			query.setMaxResults(pageable.getPageSize());
		}

		if (idTipoServico != null) {
			query.setParameter("tipoServico", idTipoServico);
		}
		if (idMatch != null) {
			query.setParameter("match", idMatch);
		}
		if (idRegistro != null) {
			query.setParameter("registro", idRegistro);
		}		
		if (statusPagamentos != null && !statusPagamentos.isEmpty()) {
			List<Long> idsStatusPagamento = statusPagamentos.stream().map(status -> status.getId()).collect(Collectors.toList());
			query.setParameterList("statusPagamentos", idsStatusPagamento);
		}
		if (relacaoEntidadeId != null) {
			query.setParameter("relacaoEntidadeId", relacaoEntidadeId);
		}

		List<Object[]> list = query.getResultList();
		
		int count = 0;
		if (list != null) {
			for (Object[] data : list) {

				Busca busca = new Busca(Long.parseLong(data[12].toString()));
				
				Match match = new Match(Long.parseLong(data[11].toString()));
				match.setBusca(busca);
				
				Registro registro = new Registro(Long.parseLong(data[9].toString()));
				registro.setNome(data[10].toString());
				
				StatusPagamento statusPagamento = new StatusPagamento(StatusPagamentos.valueOf(Long.parseLong(data[7].toString())).getId());
				statusPagamento.setDescricao(data[8].toString());
				
				TipoServico tipoServico = new TipoServico(Long.parseLong(data[5].toString()));
				tipoServico.setDescricao(data[6].toString());
				
				Pagamento pagamento = new Pagamento();
				pagamento.setId(Long.parseLong(data[0].toString()));
				pagamento.setDataCriacao(( (Timestamp) data[1] ).toLocalDateTime());
				pagamento.setDataAtualizacao(( (Timestamp) data[2] ).toLocalDateTime());
				pagamento.setCobranca(false);
				if (data[3] != null && data[3].toString().equals("1")) {
					pagamento.setCobranca(true);	
				}				
				if (data[4] != null) {
					pagamento.setIdObjetoRelacionado(Long.parseLong(data[4].toString()));
				}

				pagamentos.add(pagamento);
			}
		}
		
		LOGGER.debug("listarPagamentos >>> {}", pagamentos.size());
		return new PageImpl<Pagamento>(pagamentos, pageable, count);
	}
	
}
