package br.org.cancer.modred.redomelib.persistence;

import br.org.cancer.modred.redomelib.vo.ValorDnaNmdpVo;
import br.org.cancer.modred.redomelib.vo.ValorNmdpVo;

/**
 * Interface de negocios de validação de hla.
 * 
 * @author brunosousa
 *
 */
public interface IHlaRepository {
	
	ValorDnaNmdpVo findByLocusAndValor(String locus, String valor);

	ValorNmdpVo findByNmdpCodigo(String codigo);

	void criarValorDnaNmdpValido(ValorNmdpVo valorNmdpVo);

	void inserirValorInvalido(String locus, String alelo);

	void marcarNmdpComSplitCriado(String codigo); 

}
