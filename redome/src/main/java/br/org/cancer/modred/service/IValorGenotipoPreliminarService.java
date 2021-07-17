package br.org.cancer.modred.service;

import java.util.List;

import br.org.cancer.modred.controller.dto.GenotipoDTO;
import br.org.cancer.modred.model.ValorGenotipoPreliminar;
import br.org.cancer.modred.model.LocusExamePreliminar;
import br.org.cancer.modred.service.custom.IService;

/**
 * Define os métodos necessários para acesso a entidade GenotipoPreliminar.
 * 
 * @author Pizão
 */
public interface IValorGenotipoPreliminarService extends IService<ValorGenotipoPreliminar, Long> {
	
	/**
	 * Salva um novo genótipo com os valores informados.
	 * Além disso, também realiza as fragmentações necessárias para 
	 * as tabelas GenotipoBusca e GenotipoExpandido.
	 * 
	 * @param valores lista de valores do genótipo.
	 */
	void salvarGenotipo(List<LocusExamePreliminar> valores);
	
	
	/**
	 * Retorna a lista de genotipo.
	 * 
	 * @param genotiposPreliminares
	 * @return lista de genotipoDto
	 */
	List<GenotipoDTO> obterGenotipoDtoPorGenotipoPreliminar(List<ValorGenotipoPreliminar> genotiposPreliminares);
	
	/**
	 * Lista os valores genótipos preliminares.
	 * 
	 * @param idBuscaPreliminar
	 * @return lista de valor genotipo preliminar
	 */
	List<ValorGenotipoPreliminar> listarPorBuscaPreliminar(Long idBuscaPreliminar);
	
}
