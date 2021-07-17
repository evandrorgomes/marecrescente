package br.org.cancer.modred.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Classe utilitária para métodos manipularadores de listas.
 * @author Filipe Paes
 *
 */
public class ListUtil {

	/**
	 * Consulta uma e retorna valores distintos de acordo com um determinado parâmetro de pesquisa.
	 * @param <T>
	 * @param keyExtractor
	 * @return
	 */
    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor){
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
}
