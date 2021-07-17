package br.org.cancer.modred.service;

import java.util.List;

import br.org.cancer.modred.controller.dto.genotipo.Nmdp;
import br.org.cancer.modred.controller.dto.genotipo.ValorAlelo;

/**
 * Interface para acesso a entidade ValorNmdp.
 * 
 * @author Pizão
 *
 */
public interface IDnaService {

	/**
	 * Lista os valores compatíveis a partir de código NMDP.
	 * 
	 * @param nmdp código NMDP a ser pesquisado.
	 * @return lista de valores compatíveis.
	 */
	List<String> listarValoresCompativeisNmdp(Nmdp nmdp);
	
	/**
	 * Lista os valores compatíveis a partir de código NMDP.
	 * 
	 * @param antigeno valor do antígeno que deverá ser utilizado na composição do valor, se o mesmo não for agrupado.
	 * @param codigoNmdp código NMDP a ser pesquisado.
	 * @return lista de valores compatíveis.
	 */
	List<String> listarValoresCompativeisNmdp(String antigeno, String codigoNmdp);
	
	/**
	 * Lista os valores compatíveis com o código G informado.
	 * 
	 * @param codigoG utilizado como filtro na pesquisa.
	 * @return lista de valores possíveis para o código informado.
	 */
	List<String> listarValoresCompativeisGrupoG(String codigoG, String nomeGrupo);
	
	/**
	 * Lista os valores compatíveis com o código P informado.
	 * 
	 * @param codigoP utilizado como filtro na pesquisa.
	 * @return lista de valores possíveis para o código informado.
	 */
	List<String> listarValoresCompativeisGrupoP(String codigoP, String nomeGrupo);
	
	/**
	 * Lista os valores compatíveis com a código locus e antigeno informados.
	 * 
	 * @param codigoLocus utilizado como filtro na pesquisa.
	 * @param antigeno utilizado como filtro na pesquisa.
	 * @return lista de valores possíveis para o código informado.
	 */
	List<String> listarValoresCompativeisSorologico(String codigoLocus, String antigeno);
	
	/**
	 * Obtém o alelo tipado de acordo com o valor recebido. Além disto, caso
	 * seja um valor agrupador (NMDP, Grupo ou G), os valores válidos são
	 * listados e associado ao objeto para que possa ser utilizado para
	 * comparações de validade, por exemplo.
	 * 
	 * @param codigoLocus
	 *            código do locus.
	 * @param valorAlelico
	 *            valor alélico.
	 * @return valor do alelo tipado com seus valores válidos.
	 */
	ValorAlelo obterAleloTipado(String codigoLocus, String valorAlelico);
	
	/**
	 * Cria um map como forma de cache para os valores
	 * de HLA agrupados, como NMDP, consultados muitas vezes durante
	 * a geração do genótipo.
	 */
	void criarCacheTemporarioSeNecessario();
	
	/**
	 * Limpa o cache dos valores HLA agrupados
	 * já consultados.
	 * 
	 */
	void clearCacheTemporario();
}
