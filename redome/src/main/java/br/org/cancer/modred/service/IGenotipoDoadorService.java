package br.org.cancer.modred.service;

import br.org.cancer.modred.controller.dto.GenotipoDivergenteDTO;
import br.org.cancer.modred.controller.dto.ResultadoDivergenciaDTO;
import br.org.cancer.modred.model.Doador;
import br.org.cancer.modred.model.Exame;
import br.org.cancer.modred.model.GenotipoDoador;

/**
 * Interface para disponibilizar funcionalidades para a classe de serviço da entidade de Genótipo.
 * 
 * @author Fillipe Queiroz
 * 
 * @param <T>
 *
 */
public interface IGenotipoDoadorService<T extends Exame> {

	/**
	 * Obtém o genótipo pelo id.
	 * 
	 * @param idGenotipo - identificador do genotipo
	 * @return genotipo recuperado
	 */
	GenotipoDoador obterGenotipo(Long idGenotipo);

	/**
	 * @param idDoadorInternacional
	 */
	GenotipoDoador obterGenotipoPorDoador(Long idDoadorInternacional);

	/**
	 * Gera um novo genótipo para o doador, além de realizar as 
	 * atualizações nas tabelas auxiliares para o match, tais como:
	 * ValorGenotipoDoador, ValorGenotipoBuscaDoador e ValorGenotipoExpandidoDoador.
	 * Também executa a procedure de match para esse doador.
	 * 
	 * @param doador doador a ter o novo genótipo gerado.
	 * @return o genótipo após criado.
	 */
	GenotipoDoador gerarGenotipo(Doador doador);
	
	
	/**
	 * Gera um novo genótipo para o doador, além de realizar as 
	 * atualizações nas tabelas auxiliares para o match, tais como:
	 * ValorGenotipoDoador, ValorGenotipoBuscaDoador e ValorGenotipoExpandidoDoador.
	 * Tendo a posiibilidade de não executar a procedure de match.
	 * 
	 * @param doador doador a ter o novo genótipo gerado.
	 * @param executarProcedureMatch flag que indica se executará a rotina de match para esse doador
	 * @return genótipo do doador
	 */
	GenotipoDoador gerarGenotipo(Doador doador, Boolean executarProcedureMatch);
	
	
	
	/**
	 * Atualiza a fase do doador de acordo com o genotipo do mesmo.
	 * 
	 * @param idDooador identificador do doador a ter a fase atualizada.
	 * 
	 */
	void atualizarFaseDoador(Long idDoador);
		
	/**
	 * Resolve a discrepância do genotipo do doador.
	 * 
	 * @param idDoador Identificador do doador
	 * @param resultadoDivergenciaDTO Resultado da Divergencia
	 */
	void resolverDivergencia(Long idDoador, ResultadoDivergenciaDTO resultadoDivergenciaDTO);

	/**
	 * Obtém o genótipo divergente do doador.
	 * 
	 * @param idDoador Identificador do doador.
	 * @return GenotipoDivergenteDTO 
	 */
	GenotipoDivergenteDTO obterGenotipoDoadorDivergenteDto(Long idDoador);
	
	void gerarGenotipoDoadorImportacao(Doador doador);
}
