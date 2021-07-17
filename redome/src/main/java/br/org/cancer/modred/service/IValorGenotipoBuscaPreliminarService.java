package br.org.cancer.modred.service;

import br.org.cancer.modred.model.ValorGenotipoBuscaPreliminar;
import br.org.cancer.modred.model.ValorGenotipoPreliminar;
import br.org.cancer.modred.service.custom.IService;

/**
 * Define os métodos necessários para acesso a entidade GenotipoPreliminar.
 * 
 * @author Pizão
 */
public interface IValorGenotipoBuscaPreliminarService extends IService<ValorGenotipoBuscaPreliminar, Long> {
	
	/**
	 * Salva a lista de valores válidos para o genótipo preliminar informado.
	 * Neste processo, são realizadas as fragmentações necessárias para 
	 * as tabelas GenotipoBusca.
	 * 
	 * @param valores lista de valores do genótipo.
	 */
	void salvarGenotipoBusca(ValorGenotipoPreliminar genotipoPreliminar);
	
}
