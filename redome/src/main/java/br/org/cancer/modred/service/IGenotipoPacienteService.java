package br.org.cancer.modred.service;

import java.io.File;
import java.util.List;

import org.springframework.data.repository.query.Param;

import br.org.cancer.modred.controller.dto.GenotipoComparadoDTO;
import br.org.cancer.modred.model.GenotipoPaciente;
import br.org.cancer.modred.model.IGenotipo;
import br.org.cancer.modred.model.Paciente;

/**
 * Interface para disponibilizar funcionalidades para a classe de serviço 
 * da entidade de Genótipo do Paciente.
 * 
 * @author Fillipe Queiroz
 *
 */
public interface IGenotipoPacienteService {

	/**
	 * Método para salvar genótipos.
	 * 
	 * @param paciente - paciente a ser gerado o genotipo
	 * @return List<ValorGenotipo>
	 */
	GenotipoPaciente gerarGenotipo(Paciente paciente, Boolean executarProcedureMatch);

	/**
	 * Obtém o genótipo pelo id.
	 * 
	 * @param idGenotipo - identificador do genotipo
	 * @return genotipo recuperado
	 */
	IGenotipo obterGenotipo(Long idGenotipo);
	
	/**
	 * Listar os genotipos comparados do paciente e doadores.
	 * 
	 * @param rmr - identificador do paciente
	 * @param listaIdsDoador - identificadores do doador
	 * @return DTO com todos os genotipos
	 */
	GenotipoComparadoDTO listarGenotiposComparados(Long rmr, List<Long> listaIdsDoador);

	/**
	 * Obtém genotipo pelo paciente.
	 * 
	 * @param rmr - identificador do paciente
	 * @return genotipo atual
	 */
	GenotipoPaciente obterGenotipoPorPaciente(Long rmr);
	
	/**
	 * Deleta fisicamente o genótipo associado ao paciente.
	 * 
	 * @param rmr identificação do paciente.
	 */
	void deletarGenotipoPorPaciente(Long rmr);

	/**
	 * Listar os genotipos comparados da busca preliminar e doadores.
	 * 
	 * @param idBuscaPreliminar - identificador do paciente da busca preliminar
	 * @param listaIdsDoador - identificadores do doador
	 * @return DTO com todos os genotipos
	 */
	GenotipoComparadoDTO listarGenotiposComparadosBuscaPreliminar(Long idBuscaPreliminar, List<Long> ids);
	
	/**
	 * Valida se o paciente possui os locus C, DRB1 e DQB1 (considerados de classe C).
	 * 
	 * @param rmr identificador do paciente.
	 * @return TRUE, caso possua todos os locus.
	 */
	Boolean verificarSePossuiExamesClasseC(@Param("rmr") Long rmr);
	
	void gerarGenotipoPacienteImportacao(Paciente paciente);
	
	File impressaoGenotiposComparados(Long rmr, List<Long> listaIdsDoador);
}
