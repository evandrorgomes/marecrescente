package br.org.cancer.modred.util.sort;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;

/**
 * @author Pizão
 *
 * Lista de funcionalidades para facilitar a manipulação de coleções no sistema.
 */
public class ColecoesUtil {

	private ColecoesUtil() {}

	/**
	 * Retorna a lista ordenada de acordo com os parâmetros de ordenação informados.
	 * 
	 * @param lista lista de itens a serem ordenados.
	 * @param criterios critérios de ordenação informados.
	 * @param <T> indica o tipo a ser considerado para os objetos a serem ordenados.
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> void ordenar(List<T> lista, CriterioOrdenacao<T> ...criterios) {
		if(ArrayUtils.isNotEmpty(criterios)){
			for (CriterioOrdenacao<T> criterio : criterios) {
				lista.sort( aplicarCriterio(criterio) );
			}
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static <T> Comparator<T> aplicarCriterio(CriterioOrdenacao<T> clausula) {
		Function<T, ? extends Comparable> getterAtributo = clausula.getGetterAtributo();
		
		if (clausula.isDecrescente()) {
			return (item1, item2) -> getterAtributo.apply(item2).compareTo(getterAtributo.apply(item1));
		}
		return (item1, item2) -> getterAtributo.apply(item1).compareTo(getterAtributo.apply(item2));
	}
	
	/**
	 * Parentes.
	 * 
	 * @param <C> - Class
	 * @param childs - Childs
	 * @param setParent - SetParent
	 * @param parent - parent
	 */
	public static <C> void setParent(List<? extends C> childs, Function<Object, Void> setParent, Object parent) { 
		if(CollectionUtils.isNotEmpty(childs)) {
			childs.forEach(child -> {
				setParent.apply(parent);
			});
		}
	}

}

