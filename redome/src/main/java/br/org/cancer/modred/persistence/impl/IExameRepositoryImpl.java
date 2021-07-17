package br.org.cancer.modred.persistence.impl;

import java.sql.Clob;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.persistence.IExameRepositoryCustom;
import br.org.cancer.modred.redomelib.vo.ValorDnaNmdpVo;
import br.org.cancer.modred.redomelib.vo.ValorNmdpVo;

/**
 * Classe de implementação de consultas especificas de Exame.
 * 
 * @author ergomes
 *
 */
@Repository
@Transactional
public class IExameRepositoryImpl implements IExameRepositoryCustom {

	@Autowired
	EntityManager entityManager;

	@Override
	@SuppressWarnings("rawtypes")
	public ValorNmdpVo findByNmdpCodigo(String codigo) {
		StringBuilder sql = new StringBuilder("select vanm_id_codigo, vanm_tx_subtipo, vanm_in_agrupado, vanm_nr_quantidade, vanm_in_split_criado ")
				.append("from valor_nmdp where vanm_id_codigo = :codigo");
		
		Query query = (Query) entityManager.createNativeQuery(sql.toString());
		query.setParameter("codigo", codigo);

		
		Object[] object = (Object[]) query.getSingleResult();
		if (object != null) {
			Clob clob = (Clob)object[1];
			String subtipos;
			try {
				subtipos = clob.getSubString(1, (int) clob.length());
			} catch (SQLException e) {
				subtipos = "";
			} 
			return new ValorNmdpVo(object[0].toString(), subtipos, "1".equals(object[2].toString()), new Long(object[3].toString()), "1".equals(object[4].toString())); 
		}
				
		return null;	
	}
	
	
	@Override
	@SuppressWarnings("rawtypes")
	public void criarValorDnaNmdpValido(ValorNmdpVo valorNmdpVo) {
		
		List<String> valores = Arrays.asList(valorNmdpVo.getSubtipo().split("/"));
		String alelos = StringUtils.join(valores.stream()
				.map(org.springframework.util.StringUtils::quote)
				.toArray(), ',');
		
		StringBuilder sql = new StringBuilder("insert into valor_dna_nmdp_valido (locu_id, dnnm_tx_valor, vadn_tx_nome_grupo, dnnm_in_ativo) ") 
				.append("select locu_id, spvd_tx_grupo_alelico || ':' || codigo, 'NMDP', 1 from ( ");
		if (valorNmdpVo.getAgrupado()) {
			sql.append("select distinct x.codigo, x.locu_id, z.spvd_tx_grupo_alelico from (")
				.append("select ") 
				.append("    /*+ index(s, IN_GRUPO_CODIGO) */ ") 
				.append("    '" + valorNmdpVo.getCodigo() + "' as Codigo, ") 
				.append("    locu_id, ")  
				.append("    count(*) ")  
				.append("from ") 
				.append("    split_valor_dna s ") 
				.append("where ") 
				.append("    spvd_in_compara_nmdp = 1 ") 
				.append("    and ")
				.append("(spvd_tx_grupo_alelico || ':' || spvd_tx_valor_alelico) in (" + alelos + ")")
				.append("group by '" + valorNmdpVo.getCodigo() + "', locu_id ")
				.append("HAVING ") 
				.append("    count(*) = " + valorNmdpVo.getQuantidadeSubTipo() + " ")
				.append(") x, ")
				.append("(select locu_id, spvd_tx_grupo_alelico from split_valor_dna ") 
				.append("	    where  ")
				.append("	    spvd_in_compara_nmdp = 1 ") 
				.append("	    and ")
				.append("	    (spvd_tx_grupo_alelico || ':' || spvd_tx_valor_alelico) in (" + alelos + ") ") 
				.append("	) z ")
				.append("	where x.locu_id = z.locu_id ")
				.append("	) ");
		}
		else {
			sql.append("select ") 
				.append("    /*+ index(s, IN_VALOR) */ ") 
				.append("    '" + valorNmdpVo.getCodigo() + "'  as Codigo, ") 
				.append("    locu_id, ") 
				.append("    spvd_tx_grupo_alelico, ") 
				.append("    count(*) ")  
				.append("from ") 
				.append("    split_valor_dna s ") 
				.append("where ") 
				.append("    spvd_in_compara_nmdp = 1 ") 
				.append("    and ")
				.append("    spvd_tx_valor_alelico in (" + alelos + ") ") 
				.append("group by '" + valorNmdpVo.getCodigo() + "', locu_id, spvd_tx_grupo_alelico ")
				.append("HAVING ") 
				.append("    count(*) = " + valorNmdpVo.getQuantidadeSubTipo() + " " )
				.append(")");
		}

		Query query = (Query) entityManager.createNativeQuery(sql.toString());
			
		query.executeUpdate();
	}

	@Override
	public boolean existeValorDnaNmdpInvalido(String codigoLocusId, ValorNmdpVo valorNmdpVo) {
		
		List<String> valores = Arrays.asList(valorNmdpVo.getSubtipo().split("/"));
		String alelos = StringUtils.join(valores.stream()
				.map(org.springframework.util.StringUtils::quote)
				.toArray(), ',');
		
		StringBuilder sql = new StringBuilder("select ") 
		.append("	'" + valorNmdpVo.getCodigo() + "' as Codigo, ") 
		.append(	"locu_id, ")  
		.append("	count(*) ")  
		.append("from ") 
		.append("    split_valor_dna s ") 
		.append("where ") 
		.append("    spvd_in_compara_nmdp = 1 ") 
		.append("    and locu_id = '" + codigoLocusId + "' ")
		.append("(spvd_tx_grupo_alelico || ':' || spvd_tx_valor_alelico) in (" + alelos + ")")
		.append("group by '" + valorNmdpVo.getCodigo() + "', locu_id ")
		.append("HAVING ") 
		.append("    count(*) <> " + valorNmdpVo.getQuantidadeSubTipo() + " ")
		.append(") ");

	//	Query query = entityManager.createNativeQuery(sql.toString());
		Optional<Object> opt = Optional.ofNullable(entityManager.createNativeQuery(sql.toString()));
		return opt.isPresent();
	}

	
	
	@Override
	@SuppressWarnings("rawtypes")
	public void inserirValorInvalido(String locus, String alelo) {
		StringBuilder sql = new StringBuilder("insert into valor_dna_nmdp_valido (locu_id, dnnm_tx_valor, vadn_tx_nome_grupo, dnnm_in_ativo) ")
				.append("values (:locus, :valor, 'NMDP', 0) ");
		
		Query query = (Query) entityManager.createNativeQuery(sql.toString());

		query.setParameter("locus", locus);
		query.setParameter("valor", alelo);
					
		query.executeUpdate();
	}
	
	@Override
	@SuppressWarnings("rawtypes")
	public void marcarNmdpComSplitCriado(String codigo) {
		StringBuilder sql = new StringBuilder("update valor_nmdp  set  vanm_in_split_criado = 1 ") 
				.append("where vanm_id_codigo = :codigo");
		
		Query query = (Query) entityManager.createNativeQuery(sql.toString());
		query.setParameter("codigo", codigo);
					
		query.executeUpdate();
	}

	@Override
	@SuppressWarnings("rawtypes")
	public ValorDnaNmdpVo findByLocusAndValor(String locus, String valor) {
		
		StringBuilder sql = new StringBuilder("select LOCU_ID, DNNM_TX_VALOR, DNNM_IN_ATIVO from VALOR_DNA_NMDP ")
				.append("where locu_id = :locus and trim(dnnm_tx_valor) = trim(:valor) ");
		
		Query query = (Query) entityManager.createNativeQuery(sql.toString());
		query.setParameter("locus", locus);
		query.setParameter("valor", valor);
		
		try {
			Object[] object = (Object[]) query.getSingleResult();
			if (object != null) {
				return new ValorDnaNmdpVo(object[0].toString(), object[1].toString(), "1".equals(object[2].toString())); 
			}
		}
		catch (NoResultException nre) {
			return null;
		}
				
		return null;
	}

}
