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
import br.org.cancer.modred.model.EnderecoContatoLaboratorio;
import br.org.cancer.modred.model.Laboratorio;
import br.org.cancer.modred.model.Municipio;
import br.org.cancer.modred.model.Uf;
import br.org.cancer.modred.persistence.ILaboratorioRepositoryCustom;

/**
 * Implementação da inteface adicional que concentra e abstrai regras de
 * pesquisa. Especificamente este repositório serve como uma abstração para
 * pesquisa coleção de instâncias da entidade PreCadastroMedico.
 * 
 * @author bruno.sousa
 *
 */
public class ILaboratorioRepositoryImpl implements ILaboratorioRepositoryCustom {

	private static final Logger LOGGER = LoggerFactory.getLogger(ILaboratorioRepositoryImpl.class);

	@Autowired
	EntityManager entityManager;


	@Override
	public PageImpl<Laboratorio> listarLaboratorios(String nome, String uf, Pageable pageable) {
		
		List<Laboratorio> laboratorios = new ArrayList<>();
		long total = 0;

		try {
			StringBuffer sql = new StringBuffer("SELECT count(L.LABO_ID) over(), L.LABO_ID, L.LABO_TX_NOME, E.ENCO_ID, E.MUNI_ID, M.UF_SIGLA ")
					.append("FROM LABORATORIO L, ENDERECO_CONTATO E, MUNICIPIO M ")
					.append("WHERE L.LABO_ID = E.LABO_ID AND L.LABO_IN_FAZ_CT = 1 AND E.MUNI_ID = M.MUNI_ID ");
			
			if(nome != null) {
				sql.append(" AND UPPER(L.LABO_TX_NOME) LIKE :nome ");
			}
			
			if(uf != null) {
				sql.append(" AND UPPER(M.UF_SIGLA) LIKE :uf ");
			}
			
			sql.append(" order by L.LABO_TX_NOME ASC ");

			Query query = entityManager.createNativeQuery(sql.toString());

			if (nome != null) {
				query.setParameter("nome", "%" + nome.toUpperCase() + "%");
			}

			if (uf != null) {
				query.setParameter("uf", "%" + uf.toUpperCase() + "%");
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

					Laboratorio laboratorio = new Laboratorio();
					laboratorio.setId(Long.parseLong(data[1].toString()));
					laboratorio.setNome(data[2].toString());
					EnderecoContatoLaboratorio endereco = new EnderecoContatoLaboratorio();
					endereco.setId(Long.parseLong(data[3].toString()));
					final Municipio municipio = new Municipio(new Long(data[4].toString()));
					municipio.setUf(new Uf(data[5].toString(), null));					
					endereco.setMunicipio(municipio);
					laboratorio.setEndereco(endereco);
					laboratorios.add(laboratorio);
				}
			}
		} 
		catch (Exception e) {
			LOGGER.error("ListarLaboratoriosFazExameCT >>> {}", e.getMessage());
		}

		LOGGER.debug("ListarLaboratoriosFazExameCT >>> {}", laboratorios.size());
		return new Paginacao<>(laboratorios, pageable, total);
	}

	
}
