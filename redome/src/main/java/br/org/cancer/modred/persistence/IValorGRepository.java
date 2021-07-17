package br.org.cancer.modred.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.ValorG;
import br.org.cancer.modred.model.ValorGPK;

/**
 * Classe de repositorio para Valor G.
 * @author Filipe Paes
 *
 */
@Repository
public interface IValorGRepository  extends IRepository<ValorG, ValorGPK>{
	/**
	 * Método para listar todos os valores P de acordo com atributo de válido ou não.
	 * @param ativo - boleano de ativo ou não.
	 * @return List<ValorG> resultado da pesquisa de ativos.
	 */
	List<ValorG> findByValido(Boolean valido);
	
	/**
	 * Retorna o valor G referente ao lócus e nome do grupo.
	 * 
	 * @param codigoLocus código do locus a ser pesquisado.
	 * @param nomeGrupo nome do grupo a ser utilizado no filtro.
	 * @return o valor G referente aos parâmetros passados.
	 */
	@Query("select distinct v "
		+  "from ValorG v where v.id.locus.codigo = :codigoLocus and v.id.nomeGrupo = :nomeGrupo and valido = true")
	ValorG obterValorG(@Param("codigoLocus") String codigoLocus, @Param("nomeGrupo") String nomeGrupo);
}
