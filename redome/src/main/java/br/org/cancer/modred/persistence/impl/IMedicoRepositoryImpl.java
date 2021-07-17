package br.org.cancer.modred.persistence.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import br.org.cancer.modred.controller.page.Paginacao;
import br.org.cancer.modred.model.Medico;
import br.org.cancer.modred.model.security.Usuario;
import br.org.cancer.modred.persistence.IMedicoRepositoryCustom;

/**
 * Implementação da inteface adicional que concentra e abstrai regras de
 * pesquisa. Especificamente este repositório serve como uma abstração para
 * pesquisa coleção de instâncias da entidade PreCadastroMedico.
 * 
 * @author bruno.sousa
 *
 */
public class IMedicoRepositoryImpl implements IMedicoRepositoryCustom {

	private static final Logger LOGGER = LoggerFactory.getLogger(IMedicoRepositoryImpl.class);

	@Autowired
	EntityManager entityManager;

	@Override
	public PageImpl<Medico> listarMedicos(String crm, String nome, Pageable pageable) {

		List<Medico> medicos = new ArrayList<Medico>();
		long total = 0;

		try {
			StringBuffer sql = new StringBuffer("SELECT count(M.MEDI_ID) over (), M.MEDI_ID, M.MEDI_TX_CRM, M.MEDI_TX_NOME, U.USUA_ID ")
					.append(" FROM MEDICO M, USUARIO U WHERE M.USUA_ID = U.USUA_ID ");

			if(crm != null) {
				sql.append(" AND UPPER(M.MEDI_TX_CRM) LIKE :crm ");
			}
			
			if(nome != null) {
				sql.append(" AND UPPER(M.MEDI_TX_NOME) LIKE :nome ");
			}
			
			sql.append(" ORDER BY M.MEDI_TX_NOME ");

			Query query = entityManager.createNativeQuery(sql.toString());

			if (crm != null) {
				query.setParameter("crm", "%" + crm.toUpperCase() + "%");
			}

			if (nome != null) {
				query.setParameter("nome", "%" + nome.toUpperCase() + "%");
			}
			
			if (pageable != null) {
				query.setFirstResult(new Long(pageable.getOffset()).intValue());
				query.setMaxResults(pageable.getPageSize());
			}

			@SuppressWarnings("unchecked")
			List<Object[]> list = query.getResultList();

			if (list != null) {
				for (Object[] data : list) {

					total = Long.parseLong(data[0].toString());

					Medico medico = new Medico();
					medico.setId(Long.parseLong(data[1].toString()));
					medico.setCrm(data[2].toString());
					medico.setNome(data[3].toString());
					Usuario usuario = new Usuario();
					usuario.setId(Long.parseLong(data[4].toString()));
					medico.setUsuario(usuario );
					medicos.add(medico);
				}
			}
		} 
		catch (Exception e) {
			LOGGER.error("listarMedicos >>> {}", e.getMessage());
		}

		LOGGER.debug("listarMedicos >>> {}", medicos.size());
		return new Paginacao<>(medicos, pageable, total);

	}

}
