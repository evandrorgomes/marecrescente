package br.org.cancer.modred.persistence.impl;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.query.Query;
import org.hibernate.transform.ResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.persistence.IDashboardContatoRepository;
import br.org.cancer.modred.vo.dashboard.ContatoVo;
import br.org.cancer.modred.vo.dashboard.DetalheContatoVo;

/**
 * Implementação de consulta para o dashboard de contato.
 * 
 * @author brunosousa
 *
 */
@Repository
public class IDashboardContatoRepositoryImpl implements IDashboardContatoRepository {
	
	@Autowired
    private EntityManager entityManager;
	
	@Override
	public ContatoVo obterTotaisContato(LocalDate dataInicio, LocalDate dataFinal) {
		
		StringBuilder sql = new StringBuilder("select fase, tipopedido, sum(totalpedido), sum(totalfechado) from ( ")
				.append("select fase, 'E' as tipopedido, dataCriacao, sum(totalpedido) as totalPedido, sum(totalfechado) as totalFechado from ( ")
				.append("select peen_in_tipo_enriquecimento as fase, peen_dt_criacao as dataCriacao,  count(*) as TotalPedido, null as TotalFechado from pedido_enriquecimento ")
				.append("where peen_in_cancelado = 0 ")
				.append("group by peen_in_tipo_enriquecimento, peen_dt_criacao ")
				.append("union all ")
				.append("select peen_in_tipo_enriquecimento as fase, peen_dt_criacao, null, count(*) as TotalFechado from pedido_enriquecimento ")
				.append("where peen_in_cancelado = 0 ")
				.append("and peen_in_aberto = 0 ")
				.append("group by peen_in_tipo_enriquecimento, peen_dt_criacao) ")
				.append("group by fase, 'E', datacriacao ")
				.append("union all ")
				.append("select fase, 'C', dataCriacao, sum(totalpedido), sum(totalfechado) from ( ")
				.append("select peco_in_tipo_contato as fase, peco_dt_criacao as datacriacao, count(*) as totalpedido, null as totalFechado from pedido_contato ")
				.append("where peco_in_aberto = 1 ")
				.append("or (peco_in_aberto = 0 ")
				.append("and peco_in_contactado is not null) ")
				.append("group by peco_in_tipo_contato, peco_dt_criacao ")
				.append("union all ")
				.append("select peco_in_tipo_contato, peco_dt_criacao, null, count(*) as totalFechado from pedido_contato ")
				.append("where peco_in_aberto = 0 ")
				.append("and peco_in_contactado is not null ")
				.append("group by peco_in_tipo_contato, peco_dt_criacao) ")
				.append("group by fase, 'C', dataCriacao) ")
				.append("where dataCriacao >= :dataInicial and dataCriacao <= :dataFinal ")
				.append("group by fase, tipopedido ")
				.append("order by 1 ");

		@SuppressWarnings("unchecked")
		Query<Object[]> query = (Query<Object[]>) entityManager.createNativeQuery(sql.toString());
		
		query.setParameter("dataInicial", dataInicio.atStartOfDay() );
		query.setParameter("dataFinal", dataFinal.atTime(23, 59, 59));
		
		List<Object[]> lista = query.getResultList();
		
		ContatoVo retorno = new ContatoVo();
		if (lista != null) {
			lista.stream().forEach(data -> {
				final int fase = Integer.parseInt(data[0].toString());
				final String tipoPedido = data[1].toString();
				if (fase == 1) {
					if ("E".equals(tipoPedido)) {
						if(data[2] != null) {
							retorno.setTotalEnriquecimentoFase2(Integer.parseInt(data[2].toString()));
						}
						if(data[3] != null) {
							retorno.setTotalFechadoEnriquecimentoFase2(Integer.parseInt(data[3].toString()));
						}
					}
					else {
						if(data[2] != null) {
							retorno.setTotalContatoFase2(Integer.parseInt(data[2].toString()));
						}
						if(data[3] != null) {
							retorno.setTotalFechadoContatoFase2(Integer.parseInt(data[3].toString()));
						}
					}
				}
				else {
					if ("E".equals(tipoPedido)) {
						if(data[2] != null) {
							retorno.setTotalEnriquecimentoFase3(Integer.parseInt(data[2].toString()));
						}
						if(data[3] != null) {
							retorno.setTotalFechadoEnriquecimentoFase3(Integer.parseInt(data[3].toString()));
						}
					}
					else {
						if(data[2] != null) {
							retorno.setTotalContatoFase3(Integer.parseInt(data[2].toString()));
						}
						if(data[3] != null) {
							retorno.setTotalFechadoContatoFase3(Integer.parseInt(data[3].toString()));
						}
					}
				}
				
			});
		}
				
		return retorno;
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public List<DetalheContatoVo> listaDetalhesContatoPorPeriodo(LocalDate dataInicio, LocalDate dataFinal) {
		StringBuilder sql = new StringBuilder("select fase, tipopedido, dmr, nome, dataCriacao, numeroTentativas, ultimaTentativa, status   from (")
				.append("select pe.peen_in_tipo_enriquecimento as fase, 'E' as tipopedido, d.doad_nr_dmr as dmr, d.doad_tx_nome as nome, pe.peen_dt_criacao as dataCriacao, ") 
				.append("		null as numeroTentativas, null as ultimaTentativa,  pe.peen_in_aberto as status from pedido_enriquecimento pe, solicitacao s, match m, doador d ")
				.append("		where pe.soli_id = s.soli_id ")
				.append("		and s.matc_id = m.matc_id ")
				.append("		and m.doad_id = d.doad_id ")
				.append("		and peen_in_cancelado = 0 ")
				.append("		union all ")
				.append("		select pc.peco_in_tipo_contato as fase, 'C' as tipopedido, d.doad_nr_dmr as dmr, d.doad_tx_nome as nome, ") 
				.append("		pc.peco_dt_criacao as datacriacao, nvl(tc.numeroTentativas, 0), tc.ultimaTentativa, ")
				.append("		pc.peco_in_aberto as status from pedido_contato pc, doador d, ")
				.append("		(select peco_id, count(*) as numeroTentativas, max(tecd_dt_criacao) ultimaTentativa from tentativa_contato_doador ")
				.append("		  where tecd_in_finalizada = 1 ")
				.append("		  group by peco_id) tc ")
				.append("		where pc.doad_id = d.doad_id ")
				.append("		and tc.peco_id (+)= pc.peco_id ")
				.append("		and (peco_in_aberto = 1 ")
				.append("		or (peco_in_aberto = 0 ")
				.append("		    and peco_in_contactado is not null))) ")
				.append("where dataCriacao >= :dataInicial and dataCriacao <= :dataFinal ")
				.append("order by 5 desc");

		@SuppressWarnings({ "rawtypes" })
		Query query = (Query<Object[]>) entityManager.createNativeQuery(sql.toString());
		
		query.setParameter("dataInicial", dataInicio.atStartOfDay() );
		query.setParameter("dataFinal", dataFinal.atTime(23, 59, 59));
		
		return query.setResultTransformer(new ResultTransformer() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public DetalheContatoVo transformTuple(Object[] data, String[] aliases) {
				DetalheContatoVo detalhe = new DetalheContatoVo();
				detalhe.setFase(Integer.parseInt(data[0].toString()));
				detalhe.setTipoPedido(data[1].toString());
				detalhe.setDmr(Long.parseLong(data[2].toString()));
				if (data[3] != null) {
					detalhe.setNome(data[3].toString());
				}
				detalhe.setDataCriacao( ( (Timestamp) data[4] ).toLocalDateTime());
				if (data[5] != null) {
					detalhe.setNumeroTentativas(Integer.parseInt(data[5].toString()));
				}
				if (data[6] != null) {
					detalhe.setUltimaTentativa( ( (Timestamp) data[6] ).toLocalDateTime());
				}
				detalhe.setAberto(true);
				if ("0".equals(data[7].toString())) {
					detalhe.setAberto(false);
				}
				
				return detalhe;
			}
			
			@SuppressWarnings("rawtypes")
			@Override
			public List<DetalheContatoVo> transformList(List collection) {
				return collection;
			}
		}).getResultList();
		
	}

}
