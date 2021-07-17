package br.org.cancer.modred.service;

import br.org.cancer.modred.model.ValorGenotipoExpandidoDoador;
import br.org.cancer.modred.service.custom.IService;

/**
 * Interface para disponibilizar funcionalidades para a classe de serviço da entidade de ValorGenótipoExpandido.
 * 
 * @author Fillipe Queiroz
 *
 */
public interface IValorGenotipoExpandidoDoadorService extends IService<ValorGenotipoExpandidoDoador, Long>{


	/**
	 * Deleta o genotipo expandido por um id de genotipo.
	 * 
	 * @param idGenotipo identificador do genotipo.
	 */
	void deletarValoresPorGenotipo(Long idGenotipo);
}
