package br.org.cancer.modred.persistence.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import br.org.cancer.modred.controller.page.Paginacao;
import br.org.cancer.modred.model.DoadorInternacional;
import br.org.cancer.modred.model.Registro;
import br.org.cancer.modred.model.StatusDoador;
import br.org.cancer.modred.persistence.IDoadorInternacionalRepositoryCustom;

/**
 * Implementaçao de métodos customizados de doadores internacionais.
 * @author Filipe Paes
 *
 */
public class IDoadorInternacionalRepositoryImpl implements IDoadorInternacionalRepositoryCustom {

	private static final Logger LOGGER = LoggerFactory.getLogger(IDoadorInternacionalRepositoryImpl.class);

	@Autowired
	EntityManager entityManager;
	
	@Override
	public Paginacao<DoadorInternacional> obterDoadoresInternacionaisPaginado(Pageable page, String idDoadorRegistro, Long registro,
			String grid) {
		List<DoadorInternacional> doadores = new ArrayList<DoadorInternacional>();
		long total = 0;

		StringBuffer sql = new StringBuffer("SELECT ")
		.append(" COUNT(d.doad_id) OVER(), ")
		.append(" d.doad_id, ")
		.append(" r.regi_tx_nome, ")
		.append(" d.doad_tx_grid, ")
		.append(" d.doad_tx_id_registro, ")
		.append(" s.stdo_id, ")
		.append(" s.stdo_tx_descricao ")
		.append(" FROM ")
		.append(" doador d ")
		.append(" INNER JOIN registro r ON d.regi_id_origem = r.regi_id ")
		.append(" INNER JOIN status_doador s ON d.stdo_id = s.stdo_id ")
		.append(" where d.doad_in_tipo in (1, 3) ");
					
		
		if(idDoadorRegistro != null){
			sql.append(" AND UPPER(d.doad_tx_id_registro) like :id");
		}
		if(registro != null){
			sql.append(" AND r.regi_id = :idregistro");
		}
		if(grid != null){
			sql.append(" AND UPPER(d.doad_tx_grid) = UPPER(:grid) ");
		}
		sql.append(" ORDER BY d.doad_dt_cadastro DESC ");
		
		Query query = entityManager.createNativeQuery(sql.toString());

		if (page != null) {
			query.setFirstResult(new Long(page.getOffset()).intValue());
			query.setMaxResults(page.getPageSize());
		}

		if(idDoadorRegistro != null){
			query.setParameter("id",  "%" +  idDoadorRegistro.toUpperCase() + "%");
		}
		if(registro != null){
			query.setParameter("idregistro", registro);
		}
		if(grid != null){
			query.setParameter("grid", grid);
		}
		
		
		@SuppressWarnings("unchecked")
		List<Object[]> list = query.getResultList();

		if (list != null) {
			for (Object[] data : list) {
				total = Long.parseLong(data[0].toString());

				DoadorInternacional doadorI = new DoadorInternacional();
				doadorI.setId(Long.parseLong(data[1].toString()));
				Registro registroOrigem = new Registro();
				registroOrigem.setNome(data[2].toString());
				doadorI.setRegistroOrigem(registroOrigem );
				if (data[3] != null) {
					doadorI.setGrid(data[3].toString());
				}
				doadorI.setIdRegistro(data[4].toString());
				
				StatusDoador status = new StatusDoador();
				status.setId(Long.parseLong(data[5].toString()));
				status.setDescricao(data[6].toString());
				doadorI.setStatusDoador(status);
				
				doadores.add(doadorI);
			}
		}

		LOGGER.debug("listarDoadores >>> {}", doadores.size());
		return new Paginacao<DoadorInternacional>(doadores, page, total);
	}

}
