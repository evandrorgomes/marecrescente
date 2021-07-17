package br.org.cancer.modred.service;

import br.org.cancer.modred.model.AvaliacaoWorkupDoador;

/**
 * Interface para acesso as funcionalidades envolvendo a avaliação de Doador.
 * 
 * @author Fillipe Queiroz.
 */
public interface IAvaliacaoWorkupDoadorService {

	/**
	 * Finaliza a avaliação de doador.
	 * 
	 * @param idAvaliacao
	 */
	void finalizarAvaliacao(Long idAvaliacao);
	
	/**
	 * Salva avaliacao de workup de doador.
	 * @param avaliacao a ser persistida.
	 */
	void salvarAvaliacaoWorkup(AvaliacaoWorkupDoador avaliacao);

}
