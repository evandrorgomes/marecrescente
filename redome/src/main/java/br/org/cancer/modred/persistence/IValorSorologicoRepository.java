package br.org.cancer.modred.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.ValorSorologico;
import br.org.cancer.modred.model.ValorSorologicoPk;

/**
 * Repositório para acesso ao banco da entidade ValorSorologico.
 * 
 * @author Pizão.
 *
 */
@Repository
public interface IValorSorologicoRepository extends JpaRepository<ValorSorologico, ValorSorologicoPk> {
	
	/**
	 * Retorna o valor sorologico a partir do locus e antigeno.
	 * 
	 * @param codigoLocus código do locus a ser pesquisado.
	 * @param antigeno valor do antigeno a ser utilizado no filtro.
	 * @return o valor sorologico referente aos parâmetros passados.
	 */
	@Query("select distinct v "
		+  "from ValorSorologico v where v.id.locus.codigo = :codigoLocus and v.id.antigeno = :antigeno")
	ValorSorologico obterValorSorologico(@Param("codigoLocus") String codigoLocus, @Param("antigeno") String antigeno);
	
}