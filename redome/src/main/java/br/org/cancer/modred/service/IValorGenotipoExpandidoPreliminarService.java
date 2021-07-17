package br.org.cancer.modred.service;

import java.util.List;

import br.org.cancer.modred.model.ValorGenotipoBuscaPreliminar;
import br.org.cancer.modred.model.ValorGenotipoExpandidoPreliminar;
import br.org.cancer.modred.service.custom.IService;

/**
 * Define os métodos necessários para acesso a entidade GenotipoExpandidoPreliminar.
 * 
 * @author Pizão
 */
public interface IValorGenotipoExpandidoPreliminarService extends IService<ValorGenotipoExpandidoPreliminar, Long> {
	
	/**
	 * Lista e salva todos os valores compatíveis com o genótipo busca informado.
	 * Valor genótipo é expandido a partir do valor alelo informado no genotipo busca 
	 * visando realizar processos de comparações de forma mais simplificados, para isso, 
	 * os valores são fragmentados e salvos na GenotipoExpandido.
	 * 
	 * @param valores lista de valores do genótipo expandido.
	 */
	void salvarGenotipoExpandido(List<ValorGenotipoBuscaPreliminar> valores);
	
}
