package br.org.cancer.modred.persistence;

import br.org.cancer.modred.redomelib.vo.ValorDnaNmdpVo;
import br.org.cancer.modred.redomelib.vo.ValorNmdpVo;

/**
 * Interface para métodos customizados de Exame.
 * @author ergomes
 *
 */
public interface IExameRepositoryCustom {

	void criarValorDnaNmdpValido(ValorNmdpVo valorNmdpVo);
	
	boolean existeValorDnaNmdpInvalido(String codigoLocusId, ValorNmdpVo valorNmdpVo);
	
	void inserirValorInvalido(String locus, String alelo);
	
	void marcarNmdpComSplitCriado(String codigo);
	
	ValorDnaNmdpVo findByLocusAndValor(String locus, String valor);
	
	ValorNmdpVo findByNmdpCodigo(String codigo);
}
