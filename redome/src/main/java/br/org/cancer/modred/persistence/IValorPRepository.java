package br.org.cancer.modred.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.ValorP;
import br.org.cancer.modred.model.ValorPPK;

/**
 * Classe de repositorio para Valor P.
 * @author Filipe Paes
 *
 */
@Repository
public interface IValorPRepository  extends IRepository<ValorP, ValorPPK> {
	/**
	 * Método para listar todos os valores P de acordo com atributo de válido ou não.
	 * @param ativo - boleano de ativo ou não.
	 * @return List<ValorP> resultado da pesquisa de ativos.
	 */
	List<ValorP> findByValido(Boolean valido);
	
	/**
	 * Retorna o valor G referente ao lócus e nome do grupo.
	 * 
	 * @param codigoLocus código do locus a ser pesquisado.
	 * @param nomeGrupo nome do grupo a ser utilizado no filtro.
	 * @return o valor G referente aos parâmetros passados.
	 */
	@Query("select distinct v "
		+  "from ValorP v where v.id.locus.codigo = :codigoLocus and v.id.nomeGrupo = :nomeGrupo and valido = true")
	ValorP obterValorP(@Param("codigoLocus") String codigoLocus, @Param("nomeGrupo") String nomeGrupo);
}
