package br.org.cancer.redome.tarefa.persistence.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import br.org.cancer.redome.tarefa.dto.ListaTarefaDTO;
import br.org.cancer.redome.tarefa.model.Processo;
import br.org.cancer.redome.tarefa.model.Tarefa;
import br.org.cancer.redome.tarefa.model.TipoTarefa;
import br.org.cancer.redome.tarefa.model.domain.StatusTarefa;
import br.org.cancer.redome.tarefa.persistence.ITarefaRepositoryCustom;
import br.org.cancer.redome.tarefa.util.DateUtils;

/**
 * Implementação da inteface adicional que concentra e abstrai regras de pesquisa sobre os objetos do motor de processos da
 * plataforma redome. Especificamente este repositório serve como uma abstração para pesquisa coleção de instâncias da entidade
 * Tarefa.
 * 
 * @author Thiago Moraes
 *
 */
public class ITarefaRepositoryImpl extends SimpleJpaRepository<Tarefa, Long> implements ITarefaRepositoryCustom {

	private static final Logger LOGGER = LoggerFactory.getLogger(ITarefaRepositoryImpl.class);

	@Autowired
	EntityManager entityManager;

	@Autowired
	public ITarefaRepositoryImpl(EntityManager em) {
		super(Tarefa.class, em);
	}

	/*
	 * @Modifying(clearAutomatically = true)
	 * 
	 * @Override public int atualizarStatusTarefa(Long tarefaId, StatusTarefa
	 * status, LocalDateTime now) {
	 * 
	 * int result = 0; try { StringBuffer sql = new
	 * StringBuffer("UPDATE TAREFA SET TARE_NR_STATUS=:status, TARE_DT_ATUALIZACAO=:now "
	 * ) .append("WHERE TARE_ID = :tarefaId AND TARE_NR_STATUS <> :fstatus");
	 * 
	 * Query<?> query = (Query<?>) entityManager.createNativeQuery(sql.toString());
	 * query.setParameter("status", status.getId()); query.setParameter("now", now);
	 * query.setParameter("tarefaId", tarefaId); query.setParameter("fstatus",
	 * StatusTarefa.FEITA.getId());
	 * 
	 * result = query.executeUpdate(); } catch (Exception e) {
	 * LOGGER.error("atualizarStatusTarefa >>> {}", e.getMessage()); }
	 * 
	 * LOGGER.debug("atualizarStatusTarefa >>> {}, updated={}", status, result);
	 * 
	 * return result; }
	 */
	
	@Override
	public Page<Tarefa> listarTarefas(ListaTarefaDTO parametros) {

		List<Tarefa> tarefas = new ArrayList<Tarefa>();

		StringBuffer sql = new StringBuffer(
				" SELECT T.TARE_ID, T.PROC_ID, P.PROC_NR_TIPO, T.TARE_DT_CRIACAO, T.TARE_DT_ATUALIZACAO, ")
						.append(" T.TITA_ID, T.TARE_NR_STATUS, T.PERF_ID_RESPONSAVEL, T.USUA_ID_RESPONSAVEL,  ")
						.append(" T.TARE_NR_PARCEIRO, T.TARE_TX_DESCRICAO, P.PACI_NR_RMR, T.TARE_NR_RELACAO_ENTIDADE, ")
						.append(" T.TARE_DT_INICIO, T.TARE_DT_HORA_INICIO, T.TARE_DT_HORA_FIM, ")
						.append(" T.TARE_IN_INCLUSIVO_EXCLUSIVO, ")
						.append(" U.USUA_TX_NOME, PA.PACI_TX_NOME, T.TARE_ID_TAREFA_PAI, P.DOAD_ID, DO.DOAD_TX_NOME, count(*) over()")
						.append(" FROM TAREFA T")
						.append(" INNER JOIN PROCESSO P ON T.PROC_ID = P.PROC_ID")
						.append(" LEFT JOIN USUARIO U ON U.USUA_ID = T.USUA_ID_RESPONSAVEL")
						.append(" LEFT JOIN PACIENTE PA ON PA.PACI_NR_RMR = P.PACI_NR_RMR")
						.append(" LEFT JOIN DOADOR DO ON DO.DOAD_ID = P.DOAD_ID");
		String condicao = " where";
		if(!parametros.getTarefaSemAgendamento()) {
			sql.append(condicao);
			condicao = " and";
			sql.append(" ((T.TARE_IN_INCLUSIVO_EXCLUSIVO IS NULL AND T.TARE_IN_AGENDADO = 0) ");
			sql.append(" OR ((T.TARE_IN_INCLUSIVO_EXCLUSIVO  = 0 AND T.TARE_IN_AGENDADO = 0 AND to_char(:datahoralocal, 'hh24:mi:ss') BETWEEN to_char(TARE_DT_HORA_INICIO, 'hh24:mi:ss') AND to_char(TARE_DT_HORA_FIM, 'hh24:mi:ss')) ");
			sql.append(" OR (T.TARE_IN_INCLUSIVO_EXCLUSIVO  = 1 AND T.TARE_IN_AGENDADO = 0 AND to_char(:datahoralocal, 'hh24:mi:ss') NOT BETWEEN to_char(TARE_DT_HORA_INICIO, 'hh24:mi:ss') AND to_char(TARE_DT_HORA_FIM, 'hh24:mi:ss')) ");
			sql.append(" OR (T.TARE_IN_AGENDADO = 1  AND :datahoralocal > TARE_DT_INICIO AND to_char(:datahoralocal, 'hh24:mi:ss') BETWEEN to_char(TARE_DT_HORA_INICIO, 'hh24:mi:ss') AND to_char(TARE_DT_HORA_FIM, 'hh24:mi:ss'))  ");
			sql.append(" OR (T.TARE_IN_AGENDADO = 1 AND :datahoralocal > TARE_DT_INICIO AND to_char(TARE_DT_HORA_FIM, 'hh24:mi:ss') IS NULL AND to_char(:datahoralocal, 'hh24:mi:ss') > to_char(TARE_DT_HORA_INICIO, 'hh24:mi:ss')) ");			
			sql.append(" OR (T.USUA_ID_AGENDAMENTO IS NULL OR T.USUA_ID_AGENDAMENTO = :usuarioAgendamento)))");
			
		}
		
		if (parametros.getInclusivoExclusivo() != null) {
			sql.append(condicao);
			condicao = " and";
			sql.append(" T.TARE_IN_INCLUSIVO_EXCLUSIVO = :inclusivoExclusivo ");
		}
		if (CollectionUtils.isNotEmpty(parametros.getIdsTiposTarefa())) {
			sql.append(condicao);
			condicao = " and";
			sql.append(" T.TITA_ID IN (:tiposTarefa)");
		}
		if (parametros.getProcessoId() != null) {
			sql.append(condicao);
			condicao = " and";
			sql.append(" T.PROC_ID=:processoId");
		}
		if (parametros.getTipoProcesso() != null) {
			sql.append(condicao);
			condicao = " and";
			sql.append(" P.PROC_NR_TIPO=:tipoProcesso");
		}
		if (parametros.getRmr() != null) {
			sql.append(condicao);
			condicao = " and";
			sql.append(" P.PACI_NR_RMR=:rmr");
		}
		if (parametros.getIdDoador() != null) {
			sql.append(condicao);
			condicao = " and";
			sql.append(" P.DOAD_ID=:idDoador");
		}
		
		if (CollectionUtils.isNotEmpty(parametros.getStatusTarefa())) {
			final Boolean existeStatusAtribuida = parametros.getStatusTarefa().stream().anyMatch(
					status -> StatusTarefa.ATRIBUIDA.getId().equals(status));
			final boolean somenteStatusAtribuido = 
					existeStatusAtribuida && parametros.getStatusTarefa().size() == 1;
			final boolean outrosStatusAlemDoAtribuido = 
					existeStatusAtribuida && parametros.getStatusTarefa().size() > 1;
			
			if(somenteStatusAtribuido && !parametros.getAtribuidoQualquerUsuario()){
				sql.append(condicao);
				condicao = " and";
				sql	.append(" T.TARE_NR_STATUS = :statusTarefaAtribuida AND T.USUA_ID_RESPONSAVEL = :usuarioLogadoId");
			}
			else if(outrosStatusAlemDoAtribuido && !parametros.getAtribuidoQualquerUsuario()){
				sql.append(condicao);
				condicao = " and";
				sql	.append(" (")
					.append(" T.TARE_NR_STATUS in (:demaisStatusTarefa)")
					.append(" OR (T.TARE_NR_STATUS = :statusTarefaAtribuida AND T.USUA_ID_RESPONSAVEL = :usuarioLogadoId)")
				.append(")");
			}
			else {
				sql.append(condicao);
				condicao = " and";
				sql.append(" T.TARE_NR_STATUS in (:statusTarefa)");
			}
			
		}
		if (parametros.getStatusProcesso() != null) {
			sql.append(condicao);
			condicao = " and";
			sql.append(" P.PROC_NR_STATUS=:statusProcesso");
		}
		if (CollectionUtils.isNotEmpty(parametros.getPerfilResponsavel())) {
			sql.append(condicao);
			condicao = " and";
			sql.append(" T.PERF_ID_RESPONSAVEL in (:perfisIds)");
		}
		if (parametros.getIdUsuarioResponsavel() != null ) {
			sql.append(condicao);
			condicao = " and";
			sql.append(" T.USUA_ID_RESPONSAVEL = :usuarioId ");
		}
		if (CollectionUtils.isNotEmpty(parametros.getParceiros())) {
			sql.append(condicao);
			condicao = " and";
			sql.append(" T.TARE_NR_PARCEIRO in (:relacaoParceiro)");
		}
		if (parametros.getRelacaoEntidadeId() != null) {
			sql.append(condicao);
			condicao = " and";
			sql.append(" T.TARE_NR_RELACAO_ENTIDADE=:relacaoEntidadeId");
		}
		
		if (parametros.getTarefaPaiId() != null) {
			sql.append(condicao);
			condicao = " and";
			sql.append(" T.TARE_ID_TAREFA_PAI=:tarefaPaiId");
		}

		sql.append(" ORDER BY TARE_IN_AGENDADO DESC, TARE_ID ASC");

		@SuppressWarnings("unchecked")
		Query<Object[]> query = (Query<Object[]>) entityManager.createNativeQuery(sql.toString());

		if (parametros.getPageable() != null) {
			query.setFirstResult(new Long(parametros.getPageable().getOffset()).intValue());
			query.setMaxResults(parametros.getPageable().getPageSize());
		}
		
		if(!parametros.getTarefaSemAgendamento()) {
			query.setParameter("datahoralocal", LocalDateTime.now());
			query.setParameter("usuarioAgendamento", parametros.getIdUsuarioLogado());
		}

		if (CollectionUtils.isNotEmpty(parametros.getIdsTiposTarefa())) {
			query.setParameterList("tiposTarefa", parametros.getIdsTiposTarefa());
		}
		if (parametros.getProcessoId() != null) {
			query.setParameter("processoId", parametros.getProcessoId());
		}
		if (parametros.getTipoProcesso() != null) {
			query.setParameter("tipoProcesso", parametros.getTipoProcesso());			
		}
		if (parametros.getRmr() != null) {
			query.setParameter("rmr", parametros.getRmr());
		}
		if (parametros.getIdDoador() != null) {
			query.setParameter("idDoador", parametros.getIdDoador());
		}
		if (CollectionUtils.isNotEmpty(parametros.getStatusTarefa())) {
			final Boolean existeStatusAtribuida = parametros.getStatusTarefa().stream().anyMatch(
					status -> StatusTarefa.ATRIBUIDA.getId().equals(status));
			final boolean somenteStatusAtribuido = 
					existeStatusAtribuida && parametros.getStatusTarefa().size() == 1;
			final boolean outrosStatusAlemDoAtribuido = 
					existeStatusAtribuida && parametros.getStatusTarefa().size() > 1;
			
			if(somenteStatusAtribuido && !parametros.getAtribuidoQualquerUsuario()){
				query.setParameter("statusTarefaAtribuida", StatusTarefa.ATRIBUIDA.getId());
				query.setParameter("usuarioLogadoId", parametros.getIdUsuarioLogado());
			}
			else if(outrosStatusAlemDoAtribuido && !parametros.getAtribuidoQualquerUsuario()){
				List<Long> statusExcetoAtribuida = 
						parametros.getStatusTarefa().stream().filter(
								status -> !StatusTarefa.ATRIBUIDA.getId().equals(status))
						. collect(Collectors.toList());
				
				//List<Long> idsDemaisStatusTarefa = statusExcetoAtribuida.stream().map(status -> status.getId()).collect(Collectors.toList());
				query.setParameterList("demaisStatusTarefa", statusExcetoAtribuida);
				query.setParameter("statusTarefaAtribuida", StatusTarefa.ATRIBUIDA.getId());
				query.setParameter("usuarioLogadoId", parametros.getIdUsuarioLogado());
			}
			else {
				//List<Long> idsStatusTarefa = parametros.getStatusTarefa().stream().map(status -> status.getId()).collect(Collectors.toList());
				query.setParameterList("statusTarefa", parametros.getStatusTarefa());
			}
		}
		if (parametros.getStatusProcesso() != null) {
			query.setParameter("statusProcesso", parametros.getStatusProcesso());
		}
		if (CollectionUtils.isNotEmpty(parametros.getPerfilResponsavel())) {			
			query.setParameterList("perfisIds", parametros.getPerfilResponsavel());
		}
		if (parametros.getIdUsuarioResponsavel() != null) {
			query.setParameter("usuarioId", parametros.getIdUsuarioResponsavel());
		}
		if (CollectionUtils.isNotEmpty(parametros.getParceiros())) {
			query.setParameterList("relacaoParceiro", parametros.getParceiros());
		}
		if (parametros.getInclusivoExclusivo() != null) {
			query.setParameter("inclusivoExclusivo", parametros.getInclusivoExclusivo() == true ? 1 : 0);
		}
		if (parametros.getRelacaoEntidadeId() != null) {
			query.setParameter("relacaoEntidadeId", parametros.getRelacaoEntidadeId());
		}
		
		if (parametros.getTarefaPaiId() != null) {
			query.setParameter("tarefaPaiId", parametros.getTarefaPaiId());
		}
				
		List<Object[]> list = query.getResultList();

		int count = 0;
		if (list != null) {
			for (Object[] data : list) {

				Processo processo = Processo.builder()
						.tipo(Long.parseLong(data[2].toString()))
						.id(Long.parseLong(data[1].toString()))
						.build();
				

				if (data[11] != null) {
					processo.setRmr(Long.parseLong(data[11].toString()));
				}

				if (data[18] != null) {
					processo.setNomePaciente(data[18].toString());
				}
				
				if (data[20] != null) {
					processo.setIdDoador(Long.parseLong(data[20].toString()));
				}
				if (data[21] != null) {
					processo.setNomeDoador(data[21].toString());
				}
				
				//Tarefa tarefa = new Tarefa();
				//tarefa.setProcesso(processo);
				//tarefa.setId(Long.parseLong(data[0].toString()));

				
				Tarefa tarefa = Tarefa.builder()
						.processo(processo)
						.id(Long.parseLong(data[0].toString()))
						.tipoTarefa(TipoTarefa.builder().id(Long.parseLong(data[5].toString())).build())
						.perfilResponsavel(data[7] != null ? Long.parseLong(data[7].toString()) : null)
						.dataCriacao(( (Timestamp) data[3] ).toLocalDateTime())
						.dataAtualizacao( ( (Timestamp) data[4] ).toLocalDateTime())
						.status(Long.parseLong(data[6].toString()))
						.usuarioResponsavel(data[8] != null ? Long.parseLong(data[8].toString()) : null)
						.relacaoParceiro(data[9] != null ? Long.parseLong(data[9].toString()) : null)
						.descricao(data[10] != null ? data[10].toString() : null)
						.relacaoEntidade(data[12] != null ? Long.parseLong(data[12].toString()) : null)
						.dataInicio(data[13] != null ? ( (Timestamp) data[13] ).toLocalDateTime() : null )
						.horaInicio(data[14] != null ? ( (Timestamp) data[14] ).toLocalDateTime() : null)
						.horaFim(data[15] != null ? ( (Timestamp) data[15] ).toLocalDateTime() : null)
						.inclusivoExclusivo(data[16] != null ? data[16].toString().equals("1") : null)
						.tarefaPai( data[19] != null ? Tarefa.builder().id(Long.parseLong(data[19].toString())).build() : null)
						.aging(obterAging(( (Timestamp) data[3] ).toLocalDateTime()))
						.build();
						
				if (data[21] != null) {
					count = Integer.valueOf(data[21].toString());
				}

				tarefas.add(tarefa);
			}
		}
		
		Pageable pageable = parametros.getPageable();
		if (pageable == null) {
			pageable = PageRequest.of(0, count > 0 ? count : 1); 
		}
		
		LOGGER.debug("listarTarefas >>> {}", tarefas.size());
		return new PageImpl<>(tarefas, pageable, count);

	}
	
	
	/**
	 * @return the aging
	 */
	private String obterAging(LocalDateTime data) {
		if(data == null){
			return "";
		}
		return DateUtils.obterAging(data);
	}
	

}
