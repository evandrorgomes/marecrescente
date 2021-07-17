package br.org.cancer.modred.service;

import br.org.cancer.modred.model.ValorGenotipoBuscaDoador;
import br.org.cancer.modred.service.custom.IService;

/**
 * Interface para disponibilizar funcionalidades para a classe de serviço da entidade de ValorGenótipoBusca.
 * 
 * @author Fillipe Queiroz
 *
 */
public interface IValorGenotipoBuscaDoadorService extends IService<ValorGenotipoBuscaDoador, Long>{

	/**
	 * Deleta os registros de valores para busca associado ao paciente informado.
	 * 
	 * @param genotipoId ID do genótipo.
	 */
	void deletarValoresPorGenotipo(Long genotipoId);
	
}
