package br.org.cancer.modred.service;

import br.org.cancer.modred.model.ValorGenotipoExpandidoPaciente;
import br.org.cancer.modred.service.custom.IService;

/**
 * Interface para disponibilizar funcionalidades para a classe de serviço da entidade de ValorGenótipoExpandido.
 * 
 * @author Fillipe Queiroz
 *
 */
public interface IValorGenotipoExpandidoService extends IService<ValorGenotipoExpandidoPaciente, Long>{

	/**
	 * Deleta os registros de valores para busca associado ao paciente informado.
	 * 
	 * @param rmr identificador do paciente.
	 */
	void deletarValoresPorPaciente(Long rmr);

	/**
	 * Deleta o genotipo expandido por um id de genotipo.
	 * 
	 * @param idGenotipo identificador do genotipo.
	 */
	void deletarValoresPorGenotipo(Long idGenotipo);
}
