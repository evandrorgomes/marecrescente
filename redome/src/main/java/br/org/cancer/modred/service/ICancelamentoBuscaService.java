package br.org.cancer.modred.service;

import br.org.cancer.modred.model.CancelamentoBusca;

/**
 * Interface para métodos de negocio de Cancelamento da Busca.
 * 
 * @author bruno.sousa
 */
public interface ICancelamentoBuscaService {

	/**
	 * Método para obter ultimo cancelamentobusca pelo id da busca.
	 * @param id - da busca .
	 * @return CancelamentoBusca localizada por id.
	 */
	CancelamentoBusca obterUltimoCancelamentoBuscaPeloIdDaBusca(Long idBusca);

	
}
