package br.org.cancer.modred.persistence.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import br.org.cancer.modred.controller.dto.ContatoCentroTransplantadorDTO;
import br.org.cancer.modred.model.CentroTransplante;
import br.org.cancer.modred.model.domain.EntityStatus;
import br.org.cancer.modred.model.security.Usuario;
import br.org.cancer.modred.persistence.ICentroTransplanteRepositoryCustom;
import br.org.cancer.modred.service.impl.config.Transformation;

/**
 * Implementação da inteface adicional que concentra e abstrai regras de pesquisa sobre os objetos relacionados aos centros de
 * transplante da plataforma redome. Especificamente este repositório serve como uma abstração para pesquisa coleção de instâncias
 * da entidade CentroTransplante.
 * 
 * @author Thiago Moraes
 *
 */
public class ICentroTransplanteRepositoryImpl implements ICentroTransplanteRepositoryCustom {

	private static final Logger LOGGER = LoggerFactory.getLogger(ICentroTransplanteRepositoryImpl.class);

	@Autowired
	EntityManager entityManager;

	/**
	 * Listar centro de transplantes
	 */
	@SuppressWarnings("deprecation")
	@Override
	public Page<CentroTransplante> listarCentroTransplantes(String nome, String cnpj, String cnes, Pageable pageable, 
			Long idFuncaoCentroTransplante) {

		List<CentroTransplante> centrosTransplante = new ArrayList<CentroTransplante>();
		Integer total = 0;
		try {
			StringBuffer sql = new StringBuffer(
					"SELECT count(CT.CETR_ID) over (), CT.CETR_ID, CT.CETR_TX_NOME, CT.CETR_TX_CNPJ, CT.CETR_TX_CNES FROM ");
						
			sql.append(" CENTRO_TRANSPLANTE CT");
			if(idFuncaoCentroTransplante != null){
				sql.append(" INNER JOIN FUNCAO_CENTRO_TRANSPLANTE FCT ON CT.CETR_ID = FCT.CETR_ID");
			}
			
			sql.append(" WHERE CT.CETR_NR_ENTITY_STATUS <> :status ");
			
			if (nome != null) {
				sql.append(" AND UPPER(CT.CETR_TX_NOME) LIKE :nome");
			}
			
			if (cnpj != null) {
				sql.append(" AND UPPER(CT.CETR_TX_CNPJ) LIKE :cnpj");
			}

			if (cnes != null) {
				sql.append(" AND UPPER(CT.CETR_TX_CNES) LIKE :cnes");
			}
			
			if(idFuncaoCentroTransplante != null){
				sql.append(" AND FCT.FUTR_ID = :funcao ");
			}
			
			sql.append(" ORDER BY CT.CETR_TX_NOME ASC");

			Query query = entityManager.createNativeQuery(sql.toString());
			query.setParameter("status", EntityStatus.APAGADO.getId());
			
			if(idFuncaoCentroTransplante != null){
				query.setParameter("funcao", idFuncaoCentroTransplante);
			}

			if (nome != null) {
				query.setParameter("nome", "%" + nome.toUpperCase() + "%");
			}

			if (cnpj != null) {
				query.setParameter("cnpj", "%" + cnpj.toUpperCase() + "%");
			}

			if (cnes != null) {
				query.setParameter("cnes", "%" + cnes.toUpperCase() + "%");
			}
			
			
			if (pageable != null) {
				query.setFirstResult(new Long(pageable.getOffset()).intValue());
				query.setMaxResults(pageable.getPageSize());
			}

			@SuppressWarnings("unchecked")
			List<Object[]> list = query.getResultList();

			if (list != null) {
				for (Object[] data : list) {

					total = Integer.parseInt(data[0].toString());

					CentroTransplante centroTransplante = new CentroTransplante();
					centroTransplante.setId(Long.parseLong(data[1].toString()));
					centroTransplante.setNome(data[2].toString());
					centroTransplante.setCnpj(data[3].toString());
					centroTransplante.setCnes(data[4].toString());

					centrosTransplante.add(centroTransplante);
				}
			}
		}
		catch (Exception e) {
			LOGGER.error("listarCentroTransplantes >>> {}", e.getMessage());
		}

		if (pageable == null) {
			pageable = PageRequest.of(0, total > 0 ? total : 1); 
		}
		
		LOGGER.debug("listarCentroTransplantes >>> {}", centrosTransplante.size());
		return new PageImpl<CentroTransplante>( centrosTransplante, pageable, total);
	}

	@Override
	public List<ContatoCentroTransplantadorDTO> listarCentrosTransplantePorFuncao(Long idFuncaoCT) {
		StringBuffer sql = new StringBuffer();
		sql	.append("SELECT CT.CETR_ID as id, CT.CETR_TX_NOME as nome, ")
			.append("EC.ENCO_TX_TIPO_LOGRADOURO as tipoLogradouro, EC.ENCO_TX_NOME as nomeLogradouro, ")
			.append("EC.ENCO_TX_BAIRRO as bairro, MU.MUNI_TX_NOME as municipio, MU.UF_SIGLA as uf, ")
			.append("TEL.COTE_NR_COD_INTER as codigoInternacional, TEL.COTE_NR_COD_AREA as codigoArea, TEL.COTE_NR_NUMERO as numero ")
			.append("FROM CENTRO_TRANSPLANTE CT ")
			.append("JOIN FUNCAO_CENTRO_TRANSPLANTE FCT ON(CT.CETR_ID = FCT.CETR_ID) ")
			.append("LEFT JOIN ENDERECO_CONTATO EC ON(CT.CETR_ID = EC.CETR_ID AND EC.ENCO_IN_EXCLUIDO = 0 ")
			.append("and EC.ENCO_IN_RETIRADA = 0 and EC.ENCO_IN_ENTREGA = 0 and EC.ENCO_IN_PRINCIPAL = 1) ")
			.append("LEFT JOIN MUNICIPIO MU ON(MU.MUNI_ID = EC.MUNI_ID) ")
			.append("LEFT JOIN CONTATO_TELEFONICO TEL ON(CT.CETR_ID = TEL.CETR_ID AND TEL.COTE_IN_EXCLUIDO = 0 AND TEL.COTE_IN_PRINCIPAL = 1) ")
			.append("WHERE FCT.FUTR_ID = :funcaoId ")
			.append("ORDER BY CT.CETR_TX_NOME");
		
		Query query = entityManager.createNativeQuery(sql.toString());
		query.setParameter("funcaoId", idFuncaoCT);
		
		return (List<ContatoCentroTransplantadorDTO>) Transformation.get(query, ContatoCentroTransplantadorDTO.class);
	}
	
	@Override
	public List<Usuario> listarUsuariosPorCentro(Long centroTransplanteId) {
		StringBuffer sql = new StringBuffer();
		sql	.append("SELECT USUA.USUA_ID as id ")
			.append("FROM MODRED.USUARIO USUA JOIN MODRED.USUARIO_CENTRO_TRANSPLANTE UCT ON (USUA.USUA_ID = UCT.USUA_ID) ")
			.append("WHERE UCT.CETR_ID = :centroTransplanteId");
		
		Query query = entityManager.createNativeQuery(sql.toString());
		query.setParameter("centroTransplanteId", centroTransplanteId);
		
		return (List<Usuario>) Transformation.get(query, Usuario.class);
	}

}
