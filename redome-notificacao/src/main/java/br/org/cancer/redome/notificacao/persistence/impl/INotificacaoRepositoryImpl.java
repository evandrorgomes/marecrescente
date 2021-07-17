
package br.org.cancer.redome.notificacao.persistence.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import br.org.cancer.redome.notificacao.controller.page.Paginacao;
import br.org.cancer.redome.notificacao.dto.ListaNotificacaoDTO;
import br.org.cancer.redome.notificacao.model.CategoriaNotificacao;
import br.org.cancer.redome.notificacao.model.Notificacao;
import br.org.cancer.redome.notificacao.persistence.INotificacaoRepositoryCustom;

/**
 * Implementação da inteface adicional que concentra e abstrai regras de pesquisa sobre os objetos do motor de processos da
 * plataforma redome. Especificamente este repositório serve como uma abstração para pesquisa coleção de instâncias da entidade
 * Tarefa.
 * 
 * @author brunosousa
 *
 */
@Transactional
public class INotificacaoRepositoryImpl extends SimpleJpaRepository<Notificacao, Long> implements INotificacaoRepositoryCustom {

	private static final Logger LOGGER = LoggerFactory.getLogger(INotificacaoRepositoryImpl.class);
	private final Long RETORNO_ZERO = 0L;
	
	@Autowired
	private EntityManager entityManager;
	
	@Autowired
	public INotificacaoRepositoryImpl(EntityManager em) {
		super(Notificacao.class, em);
	}

	@Override
	public Page<Notificacao> listarNotificacoes(ListaNotificacaoDTO parametros) {
		
		List<Notificacao> notificacoes = new ArrayList<Notificacao>();

		StringBuffer sql = montarSQL(parametros.getIdNotificacao(), parametros.getIdCategoriaNotificacao(), 
			parametros.getParceiros(), parametros.getLido(), parametros.getRmr(), parametros.getSomentePacientesDoUsuario(), false);

		Query<Object[]> query = montarQuery(parametros.getIdNotificacao(), parametros.getIdCategoriaNotificacao(), parametros.getParceiros(), 
				parametros.getLido(), parametros.getRmr(), parametros.getIdUsuarioLogado(), parametros.getSomentePacientesDoUsuario(), 
				parametros.getPageable(), sql);
			
		List<Object[]> list = query.getResultList();

		int count = 0;
		if (list != null) {
			for (Object[] data : list) {

				CategoriaNotificacao categoria = new CategoriaNotificacao();
				categoria.setId(Long.parseLong(data[6].toString()));
				categoria.setDescricao(data[7].toString());
				
				Notificacao notificacao = new Notificacao();
				notificacao.setCategoria(categoria);

				notificacao.setId(Long.parseLong(data[0].toString()));
				
				if (data[1] != null) {
					notificacao.setDescricao(data[1].toString());
				}
				
				if (data[2] != null) {
					if ("1".equals(data[2].toString())) {
						notificacao.setLido(true);
					}
					else {
						notificacao.setLido(false);
					}					
				}
				
				notificacao.setDataCriacao(( (Timestamp) data[3] ).toLocalDateTime());

				if (data[4] != null) {
					notificacao.setDataLeitura(( (Timestamp) data[4] ).toLocalDateTime());
				}
				
				if (data[5] != null) {
					notificacao.setParceiro(Long.parseLong(data[5].toString()));
				}

				if (data[8] != null) {
					notificacao.setRmr(Long.parseLong(data[8].toString()));
				}

				if (data[9] != null) {
					notificacao.setUsuario(Long.parseLong(data[9].toString()));
				}

				if (data[10] != null) {
					count = Integer.valueOf(data[10].toString());
				}

				notificacoes.add(notificacao);
			}
		}

		LOGGER.debug("listarNotificacoes >>> {}", notificacoes.size());
		return new Paginacao<>(notificacoes, parametros.getPageable(), count);

	}
	
	@Override
	public Long contarNotificacoes(ListaNotificacaoDTO parametros) {
		
		StringBuffer sql = montarSQL(parametros.getIdNotificacao(), parametros.getIdCategoriaNotificacao(), 
				parametros.getParceiros(), parametros.getLido(), parametros.getRmr(), null, true);

		Query<Object[]> query = montarQuery(parametros.getIdNotificacao(), parametros.getIdCategoriaNotificacao(), 
				parametros.getParceiros(), parametros.getLido(), parametros.getRmr(), parametros.getIdUsuarioLogado(),
				null, null, sql);
		
		Object result = query.getSingleResult();
		
		if (result != null ) {
			return Long.parseLong(result.toString());
		}
		
		return RETORNO_ZERO;
	}

	/**
	 * @param idNotificacao
	 * @param idCategoriaNotificacao
	 * @param parceiros
	 * @param lidas
	 * @param rmr
	 * @param idUsuario
	 * @param somentePacientesDoUsuario
	 * @param pageable
	 * @param sql
	 * @return
	 */
	private Query<Object[]> montarQuery(Long idNotificacao, Long idCategoriaNotificacao, List<Long> parceiros,
			Boolean lidas, Long rmr, Long idUsuario, Boolean somentePacientesDoUsuario, Pageable pageable, StringBuffer sql) {
		@SuppressWarnings("unchecked")
		Query<Object[]> query = (Query<Object[]>) entityManager.createNativeQuery(sql.toString());

		if (pageable != null) {
			query.setFirstResult(new Long(pageable.getOffset()).intValue());
			query.setMaxResults(pageable.getPageSize());
		}
		
		query.setParameter("usuarioId", idUsuario);
		
		if (idNotificacao != null) {
			query.setParameter("notificacaoId", idNotificacao);			
		}
		
		if (idCategoriaNotificacao != null) {
			query.setParameter("categoriaNotificacaoId", idCategoriaNotificacao);			
		}
		
		if (rmr != null) {
			query.setParameter("rmr", rmr);
		}
		if (parceiros != null && !parceiros.isEmpty()) {
			query.setParameterList("parceiro", parceiros);
		}
				
		if (lidas != null) {
			query.setParameter("lida", lidas == true ? 1 : 0);			
		}
		if (somentePacientesDoUsuario != null) {
			query.setParameter("usuarioResponsavelId", idUsuario);
		}
		
		return query;
	}

	/**
	 * @param idNotificacao
	 * @param idCategoriaNotificacao
	 * @param parceiros
	 * @param lidas
	 * @param rmr
	 * @param somentePacientesDoUsuario
	 * @return
	 */
	private StringBuffer montarSQL(Long idNotificacao, Long idCategoriaNotificacao, List<Long> parceiros, Boolean lidas,
			Long rmr, Boolean somentePacientesDoUsuario, Boolean sqlCount) {
		
		StringBuffer sql = new StringBuffer("SELECT ");
		if (sqlCount) {
			sql.append("COUNT(N.NOTI_ID) ");
		}
		else {
			sql.append("N.NOTI_ID, N.NOTI_TX_DESCRICAO, N.NOTI_IN_LIDO, ")
				.append("N.NOTI_DT_CRIACAO, N.NOTI_DT_LIDO, N.NOTI_ID_PARCEIRO, ")
				.append("N.CANO_ID, C.CANO_TX_DESCRICAO, ")
				.append("N.PACI_NR_RMR, N.USUA_ID, count(*) over() ");
		}
		sql.append("FROM NOTIFICACAO N ")
			.append("INNER JOIN CATEGORIA_NOTIFICACAO C ON C.CANO_ID = N.CANO_ID ")
			.append("INNER JOIN PACIENTE P ON P.PACI_NR_RMR = N.PACI_NR_RMR ")
			.append("INNER JOIN USUARIO U ON U.USUA_ID = N.USUA_ID ") 
			.append("WHERE N.USUA_ID = :usuarioId ");
		
		if (idNotificacao != null) {
			sql.append(" AND N.NOTI_ID = :notificacaoId");
		}
		
		if (idCategoriaNotificacao != null) {
			sql.append(" AND N.CANO_ID = :categoriaNotificacaoId");
		}

		if (rmr != null) {
			sql.append(" AND P.PACI_NR_RMR=:rmr");
		}		
		if (parceiros != null && !parceiros.isEmpty()) {
			sql.append(" AND N.NOTI_ID_PARCEIRO in (:parceiro)");
		}
		
		if (lidas != null) {
			sql.append(" AND N.NOTI_IN_LIDO = (:lida)");
		}
		
		if (somentePacientesDoUsuario != null) {
			sql.append(" AND P.USUA_ID = (:usuarioResponsavelId)");
		}

		if (!sqlCount) {
			sql.append(" ORDER BY NOTI_IN_LIDO DESC, NOTI_ID ASC");
		}
		return sql;
	}


}
