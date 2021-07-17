package br.org.cancer.modred.util;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Concentra os métodos facilitadores para processos envolvendo o genótipo.
 * 
 * @author Pizão.
 */
public class GenotipoUtil {

	public static final String BLANK = "-";
	public static final int UMA_CASA_GENOTIPO = 1;
	public static final int DUAS_CASAS_GENOTIPO = 2;
	public static final String GRUPO_G = "G";
	public static final String GRUPO_P = "P";
	
	/**
	 * Extrai o valor do alelo para ser utilizado como
	 * informação para a tabela de genotipo busca.
	 * 
	 * @param alelo valor completo do alelo.
	 * @return valor do alelo extraído para dois grupos.
	 */
	public static String extrairAleloParaGenotipoBusca(String alelo){
		return extrairAlelo(alelo, DUAS_CASAS_GENOTIPO);
	}
	
	/**
	 * Extrai o valor do alelo para ser utilizado como
	 * informação para a tabela de genotipo expandido.
	 * 
	 * @param alelo valor completo do alelo.
	 * @return valor do alelo extraído para dois grupos.
	 */
	public static String extrairAleloParaGenotipoExpandido(String alelo){
		return extrairAlelo(alelo, UMA_CASA_GENOTIPO);
	}
	
	/**
	 * Retorna o valor do genótipo, por quantidade de 
	 * grupos (valor separado por dois pontos).
	 * 
	 * @param alelo valor do alelo. 
	 * @param qtdGrupos quantidade de grupos de valores
	 * a serem consideradas na extração do valor do alelo.
	 * @return parte do valor do alelo contendo N casas.
	 */
	public static String extrairAlelo(String alelo, int qtdGrupos) {
		Pattern pattern = Pattern.compile(":");
		Matcher matcher = pattern.matcher(alelo);
		int matches = 0;
		boolean encontrou = false;
		do {
			encontrou = matcher.find();
			matches++;
		}
		while (matches != qtdGrupos);

		return encontrou ? alelo.substring(0, matcher.start()) : alelo;
	}
	
	/**
	 * Normaliza os alelos, caso um deles esteja como blank deve
	 * refletir o valor do outro alelo. 
	 * 
	 * @param alelo1
	 * @param alelo2
	 * @return
	 */
	public static String normalizarValoresBlank(String alelo1, String alelo2) {
		return BLANK.equals(alelo1) ? alelo2 : alelo1;
	}
	
	/**
	 * Faz o distinct na lista de acordo considerando somente o atributo informado.
	 * 
	 * @param keyExtractor referência ao atributo/getter que irá realizar a comparação.
	 * @return lista de já com distinct realizado.
	 */
	public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
		Set<Object> seen = ConcurrentHashMap.newKeySet();
		return t -> seen.add(keyExtractor.apply(t));
	}
	
	public static String obterTipoGrupo(String grupo) {
		return grupo.endsWith(GRUPO_G) ? GRUPO_G : GRUPO_P;
	}
	
}
