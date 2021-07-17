package br.org.cancer.modred.persistence.impl;

import java.sql.Timestamp;
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
import br.org.cancer.modred.model.PreCadastroMedico;
import br.org.cancer.modred.model.domain.StatusPreCadastro;
import br.org.cancer.modred.persistence.IPreCadastroMedicoRepositoryCustom;

/**
 * Implementação da inteface adicional que concentra e abstrai regras de
 * pesquisa. Especificamente este repositório serve como uma abstração para
 * pesquisa coleção de instâncias da entidade PreCadastroMedico.
 * 
 * @author bruno.sousa
 *
 */
public class IPreCadastroMedicoRepositoryImpl implements IPreCadastroMedicoRepositoryCustom {

	private static final Logger LOGGER = LoggerFactory.getLogger(IPreCadastroMedicoRepositoryImpl.class);

	@Autowired
	EntityManager entityManager;

	@Override
	public PageImpl<PreCadastroMedico> findByStatus(StatusPreCadastro status, Pageable pageable) {

		List<PreCadastroMedico> preCadastros = new ArrayList<PreCadastroMedico>();
		long total = 0;

		try {
			StringBuffer sql = new StringBuffer("SELECT count(P.PRCM_ID) over (), P.PRCM_ID, P.PRCM_TX_NOME,")
					.append(" PRCM_DT_SOLICITACAO FROM PRE_CADASTRO_MEDICO P WHERE P.PRCM_TX_STATUS = :status ");

			sql.append(" ORDER BY PRCM_DT_SOLICITACAO DESC");

			Query query = entityManager.createNativeQuery(sql.toString());
			query.setParameter("status", status.toString());

			if (pageable != null) {
				query.setFirstResult(new Long(pageable.getOffset()).intValue());
				query.setMaxResults(pageable.getPageSize());
			}

			@SuppressWarnings("unchecked")
			List<Object[]> list = query.getResultList();

			if (list != null) {
				for (Object[] data : list) {

					total = Long.parseLong(data[0].toString());

					PreCadastroMedico preCadastro = new PreCadastroMedico();
					preCadastro.setId(Long.parseLong(data[1].toString()));
					preCadastro.setNome(data[2].toString());
					preCadastro.setDataSolicitacao(( (Timestamp) data[3] ).toLocalDateTime());
					
					preCadastros.add(preCadastro);
				}
			}
		} 
		catch (Exception e) {
			LOGGER.error("listarPreCadastrosMedicos >>> {}", e.getMessage());
		}

		LOGGER.debug("listarPreCadastrosMedicos >>> {}", preCadastros.size());
		return new Paginacao<>(preCadastros, pageable, total);

	}

}
