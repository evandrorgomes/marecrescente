package br.org.cancer.modred.service;

import java.util.List;

import br.org.cancer.modred.controller.dto.GenotipoDTO;
import br.org.cancer.modred.model.GenotipoPaciente;
import br.org.cancer.modred.model.Paciente;
import br.org.cancer.modred.model.ValorGenotipoPaciente;

/**
 * Interface para disponibilizar funcionalidades para a classe de serviço da entidade de ValorGenotipoPaciente.
 * 
 * @author Pizão
 *
 */

public interface IValorGenotipoPacienteService extends IValorGenotipoService<ValorGenotipoPaciente> {

	/**
	 * Gera os valores de genótipo para o paciente informado, a partir dos exames já conferidos do mesmo.
	 * 
	 * @param rmr identificador do paciente.
	 * @return lista dos valores genótipos já aprovados nos exames.
	 */
	List<ValorGenotipoPaciente> gerarGenotipoPaciente(Long rmr);
	
	/**
	 * Obtém o genotípo do paciente a partir do RMR.
	 * 
	 * @param rmr RMR do paciente.
	 * @return lista de DTOs que representam o genótipo do paciente.
	 */
	List<GenotipoDTO> obterGenotipoPacienteDto(Long rmr);
	
	/**
	 * Exclui valores de genótipo por id de genótipo.
	 * @param genotipoId - id do genótipo.
	 */
	void excluirValoresGenotiposPaciente(Long genotipoId);
	
	/**
	 * Salva a lista de valores genótipo para o paciente informado.
	 * 
	 * @param valoresGenotipos valores a serem salvos.
	 * @param genotipo agrupador associado.
	 * @param paciente paciente que possui os valores informados.
	 * @return lista de valores salvos.
	 */
	List<ValorGenotipoPaciente> salvar(
			List<ValorGenotipoPaciente> valoresGenotipos, GenotipoPaciente genotipo, Paciente paciente);
}
