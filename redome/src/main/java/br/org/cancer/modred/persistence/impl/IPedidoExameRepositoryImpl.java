package br.org.cancer.modred.persistence.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import br.org.cancer.modred.model.Exame;
import br.org.cancer.modred.model.PedidoExame;
import br.org.cancer.modred.model.domain.StatusSolicitacao;
import br.org.cancer.modred.persistence.IPedidoExameRepositoryCustom;
import br.org.cancer.modred.service.impl.ExameService;
import br.org.cancer.modred.vo.PedidoExameDoadorInternacionalVo;
import br.org.cancer.modred.vo.PedidoExameDoadorNacionalVo;


/**
 * Implementação de querys personalizadas de Pedido de Exame.
 * 
 * @author Bruno Sousa
 *
 */
public class IPedidoExameRepositoryImpl implements IPedidoExameRepositoryCustom {
	
	@Autowired
	private EntityManager entityManager;
		
	private ExameService<Exame> exameService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(IPedidoExameRepositoryImpl.class);


	@Override
	public List<PedidoExame> listarPedidosExameParaResolverDivergencia(Long idDoador) {
		
		StringBuffer sql = new StringBuffer("select pe.peex_id, pe.exam_id from pedido_exame pe ") 
				.append("inner join solicitacao s on (s.soli_id = pe.soli_id) ") 
				.append("inner join match m on (m.matc_id = s.matc_id) ") 
				.append("inner join doador d on (d.doad_id = m.doad_id) ")
				.append("where s.tiso_id = 2 and s.soli_in_resolver_divergencia = 1 ") 
				.append("and s.soli_nr_status = 2 and pe.stpx_id = 2 ") 
				.append("and pe.peex_dt_abertura_divergencia is not null ") 
				.append("and pe.peex_dt_conclusao_divergencia is null ") 
				.append("and d.doad_id = :idDoador ") 
				.append("order by pe.peex_dt_abertura_divergencia");
		
		@SuppressWarnings("unchecked")
		Query<Object[]> query = (Query<Object[]>) entityManager.createNativeQuery(sql.toString());

		query.setParameter("idDoador", idDoador);
		
		List<Object[]> lista = query.getResultList();
		if (lista != null) {
			return lista.stream().map(data -> { 
						final PedidoExame pedidoExame = new PedidoExame(new Long(data[0].toString()));
						final Exame exame = exameService.obterExame(new Long(data[1].toString()));
						pedidoExame.setExame(exame);
						return pedidoExame;
					}).collect(Collectors.toList());
		}
				
		return new ArrayList<>();
	}

	@Override
	public List<PedidoExameDoadorNacionalVo> listarPedidosDeExameNacional(Long idDoador, Long idPaciente, Long idBusca,
			List<Integer> filtrarSolicitacaoPelosStatus, List<Long> filtrarSolicitacaoPelosTipos, List<Long> filtrarTarefaPelosTipos, Pageable pageable) {
		List<PedidoExameDoadorNacionalVo> pedidos = new ArrayList<PedidoExameDoadorNacionalVo>();

		StringBuilder sql = new StringBuilder(
				"SELECT TA.TARE_ID, TA.TITA_ID, MT.BUSC_ID, BU.PACI_NR_RMR, SO.SOLI_ID, PA.PACI_TX_NOME, MT.DOAD_ID, ")
			    .append(" PE.PEEX_ID, DO.DOAD_IN_TIPO, DO.DOAD_NR_DMR, DO.DOAD_TX_NOME, ")
			    .append(" (CASE WHEN (LA.LABO_TX_NOME IS NOT NULL) THEN LA.LABO_TX_NOME ELSE LA2.LABO_TX_NOME END) LABO_TX_NOME, ") 
			    .append(" PE.PEEX_DT_CRIACAO, TE.TIEX_TX_DESCRICAO, ")
				.append(" (CASE WHEN (EN.PEEN_IN_ABERTO = 1) THEN 'PEDIDO DE ENRIQUECIMENTO ABERTO' ")
			    .append(" WHEN (PC.PECO_IN_ABERTO = 1) THEN 'PEDIDO DE CONTATO ABERTO' ")
			    .append(" WHEN (SM.PECS_IN_ABERTO = 1 ) THEN 'AGUARDANDO RETORNO SMS' ")
			    .append(" WHEN (AN.AMNE_IN_PROSSEGUIR = 1) THEN 'PEDIDO DE ANALISE MÉDICA ABERTO' ")
			    .append(" ELSE SP.STPX_TX_DESCRICAO END) STATUS, ")
			    .append(" EN.PEEN_ID, EN.PEEN_DT_CRIACAO, PC.PECO_ID, PC.PECO_DT_CRIACAO, SO.SOLI_NR_STATUS, SO.SOLI_DT_CRIACAO, ")
			    .append(" SM.PECS_ID, SM.PECS_DT_CRIACAO, count(*) over() ")

			    .append(" FROM MATCH MT ")
			    .append(" INNER JOIN BUSCA BU ON (MT.BUSC_ID = BU.BUSC_ID) ")
			    .append(" INNER JOIN DOADOR DO ON (MT.DOAD_ID = DO.DOAD_ID) ")
			    .append(" INNER JOIN PACIENTE PA ON (BU.PACI_NR_RMR = PA.PACI_NR_RMR) ")
			    .append(" INNER JOIN SOLICITACAO SO ON (MT.MATC_ID = SO.MATC_ID AND SO.TISO_ID IN (:tiposSolicitacao)) ")
			    .append(" INNER JOIN TIPO_EXAME TE ON (SO.TIEX_ID = TE.TIEX_ID) ")
			    .append(" LEFT OUTER JOIN PEDIDO_EXAME PE ON (SO.SOLI_ID = PE.SOLI_ID) ")
			    .append(" LEFT OUTER JOIN STATUS_PEDIDO_EXAME SP ON (PE.STPX_ID = SP.STPX_ID) ")
			    .append(" LEFT OUTER JOIN LABORATORIO LA ON (PE.LABO_ID = LA.LABO_ID) ")
			    .append(" LEFT OUTER JOIN LABORATORIO LA2 ON (SO.LABO_ID = LA2.LABO_ID) ")
			    .append(" LEFT OUTER JOIN PEDIDO_ENRIQUECIMENTO EN ON (SO.SOLI_ID = EN.SOLI_ID AND EN.PEEN_IN_ABERTO = 1) ")
			    .append(" LEFT OUTER JOIN PEDIDO_CONTATO PC ON (SO.SOLI_ID = PC.SOLI_ID AND PC.PECO_IN_ABERTO = 1 AND PC.PECO_IN_PASSIVO = 0) ")
			    .append(" LEFT OUTER JOIN PEDIDO_CONTATO PC2 ON (SO.SOLI_ID = PC2.SOLI_ID AND PC2.PECO_IN_ABERTO = 0 AND PC2.PECO_IN_PASSIVO = 0) ")
			    .append(" LEFT OUTER JOIN PEDIDO_CONTATO_SMS SM ON (PC2.PECO_ID = SM.PECO_ID AND SM.PECS_IN_ABERTO = 1 ) ")
			    .append(" LEFT OUTER JOIN ANALISE_MEDICA AN ON (PC.PECO_ID = AN.PECO_ID AND AN.AMNE_IN_PROSSEGUIR = 1) ")
			    .append(" LEFT OUTER JOIN TAREFA TA ON (TA.TARE_ID = (SELECT TF.TARE_ID FROM TAREFA TF ")
			    .append(" WHERE TF.TARE_NR_RELACAO_ENTIDADE = PE.PEEX_ID AND TF.TITA_ID IN (:tiposTarefa))) ");

		sql.append(" WHERE SO.SOLI_NR_STATUS IN (:statusSolicitacao) ");
		
		if(idDoador != null) {
			sql.append(" AND DO.DOAD_ID = :idDoador ");
		}

		if(idPaciente != null) {
			sql.append(" AND PA.PACI_ID = :idPaciente ");
		}
		
		if(idBusca != null) {
			sql.append(" AND BU.BUSC_ID = :idBusca ");
		}
		
		sql.append(" ORDER BY DO.DOAD_NR_DMR DESC, SO.SOLI_DT_CRIACAO DESC");

		@SuppressWarnings("unchecked")
		Query<Object[]> query = (Query<Object[]>) entityManager.createNativeQuery(sql.toString());

		query.setParameterList("statusSolicitacao", filtrarSolicitacaoPelosStatus);
		
		query.setParameterList("tiposSolicitacao", filtrarSolicitacaoPelosTipos);

		query.setParameterList("tiposTarefa", filtrarTarefaPelosTipos);
		
		if(idDoador != null) {
			query.setParameter("idDoador", idDoador);
		}
		if (idPaciente != null) {
			query.setParameter("idPaciente", idPaciente);
		}
		if (idBusca != null) {
			query.setParameter("idBusca", idBusca);
		}

		List<Object[]> list = query.getResultList();

		if (list != null) {
			for (Object[] data : list) {
				pedidos.add(popularConsultaPedidoExameNacionalVo(data));
			}
		}

		return pedidos;
	}
	
	private PedidoExameDoadorNacionalVo popularConsultaPedidoExameNacionalVo(Object[] data) {
		PedidoExameDoadorNacionalVo voConsulta = new PedidoExameDoadorNacionalVo();  
		
		if (data[0] != null) {
			voConsulta.setIdTarefa(Long.parseLong(data[0].toString()));
		}
		if (data[1] != null) {
			voConsulta.setTipoTarefa(Long.parseLong(data[1].toString()));
		}
		if (data[2] != null) {
			voConsulta.setIdBusca(Long.parseLong(data[2].toString()));
		}
		if (data[3] != null) {
			voConsulta.setRmr(Long.parseLong(data[3].toString()));
		}
		if (data[4] != null) {
			voConsulta.setIdSolicitacao(Long.parseLong(data[4].toString()));
		}
		if (data[5] != null) {
			voConsulta.setNomePaciente(data[5].toString());
		}
		if (data[6] != null) {
			voConsulta.setIdDoador(Long.parseLong(data[6].toString()));
		}
		if (data[7] != null) {
			voConsulta.setIdPedidoExame(Long.parseLong(data[7].toString()));
		}
		if (data[15] != null) {
			voConsulta.setIdPedidoEnriquecimento(Long.parseLong(data[15].toString()));
		}
		if (data[17] != null) {
			voConsulta.setIdPedidoContato(Long.parseLong(data[17].toString()));
		}
		if (data[8] != null) {
			voConsulta.setTipoDoador(Long.parseLong(data[8].toString()));
		}
		if (data[9] != null) {
			voConsulta.setDmr(Long.parseLong(data[9].toString()));
		}
		if (data[10] != null) {
			voConsulta.setNomeDoador(data[10].toString());
		}
		if (data[11] != null) {
			voConsulta.setNomeLaboratorio(data[11].toString());
		}
		
		if (data[13] != null) {
			voConsulta.setTipoExameDescricao(data[13].toString());
		}
		if(data[19] != null && Integer.parseInt(data[19].toString()) != StatusSolicitacao.ABERTA.getId()) {
			voConsulta.setStatusDescricao(StatusSolicitacao.getDescricaoPorId(Integer.parseInt(data[19].toString())));
		}
		else {
			if(data[14] != null) {
				voConsulta.setStatusDescricao(data[14].toString());
			}
		}
		
		if(data[19] != null && Integer.parseInt(data[19].toString()) != StatusSolicitacao.ABERTA.getId()) {
			voConsulta.setDataCriacao(((Timestamp) data[20]).toLocalDateTime());
		}
		else {
			if(data[12] != null) {
				voConsulta.setDataCriacao(((Timestamp) data[12]).toLocalDateTime());
			}
			else if(data[16] != null) {
				voConsulta.setDataCriacao(((Timestamp) data[16]).toLocalDateTime());
			}
			else if(data[18] != null) {
				voConsulta.setDataCriacao(((Timestamp) data[18]).toLocalDateTime());
			}
			else if(data[22] != null) {
				voConsulta.setDataCriacao(((Timestamp) data[22]).toLocalDateTime());
			}
		}
		if (data[21] != null) {
			voConsulta.setIdPedidoContatoSms(Long.parseLong(data[21].toString()));
		}
		
		return voConsulta;
	}

	
	@Override
	public List<PedidoExameDoadorInternacionalVo> listarPedidosDeExameInternacional(Long idBusca,
			List<Integer> filtrarSolicitacaoPelosStatus, List<Long> filtrarSolicitacaoPelosTipos, List<Long> filtrarTarefaPelosTipos, 
			Pageable pageable) {
		
		
		List<PedidoExameDoadorInternacionalVo> pedidos = new ArrayList<PedidoExameDoadorInternacionalVo>();

		StringBuilder sql = new StringBuilder(
				"SELECT pe.*,COUNT(*) OVER() FROM ")
				.append("    ( ")
				.append("        SELECT bu.busc_id, so.soli_id, so.soli_nr_status, ")
				.append("            do.doad_id,do.doad_in_emdis,do.doad_tx_id_registro, ")
				.append("            do.doad_in_tipo,pe.peex_id AS pedido_id, re.regi_tx_nome, ")
				.append("            ta.tare_id, ta.proc_id, ta.tita_id, so.tiso_id, ")
				.append("            (SELECT LISTAGG(lp.locu_id, ' E ')  ")
				.append("            WITHIN GROUP(ORDER BY lp.locu_id) AS locu_id ")
				.append("            FROM locus_pedido_exame lp WHERE LP.peex_id = pe.peex_id ")
				.append("            ) AS locu_id, PE.peex_dt_criacao   AS data_criacao, ")
				.append("            do.doad_tx_grid,te.tiex_id,te.tiex_tx_descricao ")
				.append("        FROM solicitacao so, match mt, ")
				.append("            doador do, busca bu,pedido_exame pe, ")
				.append("            tarefa ta,registro re,tipo_exame te ")
				.append("        WHERE so.matc_id = mt.matc_id ")
				.append("            AND mt.doad_id = do.doad_id ")
				.append("            AND mt.busc_id = bu.busc_id ")
				.append("            AND pe.soli_id = so.soli_id ")
				.append("            AND re.regi_id = do.regi_id_origem ")
				.append("            AND ta.tare_nr_relacao_entidade = pe.peex_id ")
				.append("            AND pe.tiex_id = te.tiex_id ")
				.append("            AND so.tiso_id IN (:tipossolicitacao) ")
				.append("            AND ta.tita_id IN (:tipostarefa) ")
				.append("            AND so.soli_nr_status IN (:statussolicitacao) ")
				.append("            AND bu.busc_id = :idbusca ")
				.append("        UNION ALL ")
				.append("        SELECT ")
				.append("            bu.busc_id,so.soli_id,so.soli_nr_status, ")
				.append("            do.doad_id,do.doad_in_emdis, do.doad_tx_id_registro,do.doad_in_tipo, ")
				.append("            pi.peid_id,re.regi_tx_nome,ta.tare_id,ta.proc_id, ")
				.append("            ta.tita_id,so.tiso_id,NULL AS locu_id,pi.peid_dt_criacao data_criacao, ")
				.append("            do.doad_tx_grid,NULL,NULL ")
				.append("        FROM ")
				.append("            solicitacao so,match mt, ")
				.append("            doador do,busca bu, ")
				.append("            pedido_idm pi,tarefa ta,registro re ")
				.append("        WHERE ")
				.append("            so.matc_id = mt.matc_id ")
				.append("            AND mt.doad_id = do.doad_id ")
				.append("            AND mt.busc_id = bu.busc_id ")
				.append("            AND pi.soli_id = so.soli_id ")
				.append("            AND re.regi_id = do.regi_id_origem ")
				.append("            AND ta.tare_nr_relacao_entidade = pi.peid_id ")
				.append("            AND so.tiso_id IN (:tipossolicitacao) ")
				.append("            AND ta.tita_id IN (:tipostarefa) ")
				.append("            AND so.soli_nr_status IN (:statussolicitacao) ")
				.append("            AND bu.busc_id = :idbusca ")
				.append("    ) pe ")
				.append("ORDER BY ")
				.append("    doad_tx_id_registro DESC, ")
				.append("    data_criacao DESC ");				
		
		@SuppressWarnings("unchecked")
		Query<Object[]> query = (Query<Object[]>) entityManager.createNativeQuery(sql.toString());
		
		query.setParameterList("tipossolicitacao", filtrarSolicitacaoPelosTipos);
	
		query.setParameterList("tipostarefa", filtrarTarefaPelosTipos);
		
		query.setParameterList("statussolicitacao", filtrarSolicitacaoPelosStatus);
	
		query.setParameter("idbusca", idBusca);
	
		List<Object[]> list = query.getResultList();
	
		if (list != null) {
			for (Object[] data : list) {
				PedidoExameDoadorInternacionalVo voData = popularConsultaPedidoExameInternacionalVo(data);
				pedidos.add(voData);
			}
		}
	
		return pedidos;
	}

	private PedidoExameDoadorInternacionalVo popularConsultaPedidoExameInternacionalVo(Object[] data) {
		
		PedidoExameDoadorInternacionalVo voConsulta = new PedidoExameDoadorInternacionalVo();  
		int coluna = 0;
		
		voConsulta.setIdBusca(Optional.ofNullable(Long.parseLong(data[coluna++].toString())).orElse(null));
		voConsulta.setIdSolicitacao(Optional.ofNullable(Long.parseLong(data[coluna++].toString())).orElse(null));
		voConsulta.setIdStatusSolicitacao(Optional.ofNullable(Long.parseLong(data[coluna++].toString())).orElse(null));
		voConsulta.setIdDoador(Optional.ofNullable(Long.parseLong(data[coluna++].toString())).orElse(null));
		voConsulta.setVeioDoEmdis(Optional.ofNullable(Long.parseLong(data[coluna++].toString())).orElse(null));
		voConsulta.setIdOrigem(Optional.ofNullable(data[coluna++].toString()).orElse(null));
		voConsulta.setTipoDoador(Optional.ofNullable(Long.parseLong(data[coluna++].toString())).orElse(null));
		voConsulta.setIdPedidoExame(Optional.ofNullable(Long.parseLong(data[coluna++].toString())).orElse(null));
		voConsulta.setOrigem(Optional.ofNullable(data[coluna++].toString()).orElse(null));
		voConsulta.setIdTarefa(Optional.ofNullable(Long.parseLong(data[coluna++].toString())).orElse(null));
		voConsulta.setIdProcesso(Optional.ofNullable(Long.parseLong(data[coluna++].toString())).orElse(null));
		voConsulta.setTipoTarefa(Optional.ofNullable(Long.parseLong(data[coluna++].toString())).orElse(null));
		voConsulta.setIdTipoSolicitacao(Optional.ofNullable(Long.parseLong(data[coluna++].toString())).orElse(null));
		voConsulta.setExame(Optional.ofNullable(data[coluna++].toString()).orElse(null));
		voConsulta.setDataCriacao(Optional.ofNullable(((Timestamp) data[coluna++]).toLocalDateTime()).orElse(null));
		voConsulta.setGrid(Optional.ofNullable(data[coluna++].toString()).orElse(null));
		voConsulta.setIdExame(Optional.ofNullable(Long.parseLong(data[coluna++].toString())).orElse(null));
		voConsulta.setNomeTipoExame(Optional.ofNullable(data[coluna++].toString()).orElse(null));
		return voConsulta;
	}

	/**
	 * @param exameService the exameService to set
	 */
	@Autowired
	public void setExameService(ExameService<Exame> exameService) {
		this.exameService = exameService;
	}
	
	@Override
	public String obterIdRegistroDoadorInternacionalPorPedidoExameId(Long idPedidoExame) {
		StringBuffer sql = new StringBuffer("SELECT D.DOAD_TX_ID_REGISTRO FROM PEDIDO_EXAME P ")
				.append("INNER JOIN SOLICITACAO S ON (P.SOLI_ID = S.SOLI_ID) ")
				.append("INNER JOIN MATCH M ON (S.MATC_ID = M.MATC_ID) ")
				.append("INNER JOIN DOADOR D ON (M.DOAD_ID = D.DOAD_ID) ")
				.append("WHERE P.PEEX_ID = :id");
		
		@SuppressWarnings("unchecked")
		Query<Object[]> query = (Query<Object[]>) entityManager.createNativeQuery(sql.toString());
		query.setParameter("id", idPedidoExame);
		Object resultado = query.getSingleResult();
	
		if (resultado == null) {
			LOGGER.error("Não foi encontrado o doador com o pedido de exame " + idPedidoExame);
			return null;
		}
		
		return resultado.toString();
	}

	@Override
	public String obterIdRegistroDoadorInternacionalPorPedidoIdmId(Long idPedidoIdm) {
		StringBuffer sql = new StringBuffer("SELECT D.DOAD_TX_ID_REGISTRO FROM PEDIDO_IDM P ")
				.append("INNER JOIN SOLICITACAO S ON (P.SOLI_ID = S.SOLI_ID) ")
				.append("INNER JOIN MATCH M ON (S.MATC_ID = M.MATC_ID) ")
				.append("INNER JOIN DOADOR D ON (M.DOAD_ID = D.DOAD_ID) ")
				.append("WHERE P.PEID_ID = :id");
		
		
		@SuppressWarnings("unchecked")
		Query<Object[]> query = (Query<Object[]>) entityManager.createNativeQuery(sql.toString());
	
		query.setParameter("id", idPedidoIdm);
		Object resultado = query.getSingleResult();
	
		if (resultado == null) {
			LOGGER.error("Não foi encontrado o doador com o pedido de IDM " + idPedidoIdm);
			return null;
		}
		
		return resultado.toString();
	}

	

}
