/**
 * 
 */
package br.org.cancer.modred.model;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import org.springframework.context.i18n.LocaleContextHolder;

import br.org.cancer.modred.model.domain.Idiomas;

/**
 * Interface responsável pela padronização dos dominios com internacionalização.
 * 
 * @author Cintia Oliveira
 *
 * @param <T> entidade responsável por armazenar os valores internacionalizados do dominio.
 */
public interface IDominioInternacionalizado <T extends Serializable> extends Serializable {

	/**
	 * Retorna mapa com os valores do dominio internacionalizado. A chave é o id do Idioma.
	 * 
	 * @return mapa com os dados internacionalizados.
	 */
	Map<Integer, T> getInternacionalizacao();

	/**
	 * Retorna o valor internacionalizado da propriedade cujo nome foi passado como parâmetro.
	 * 
	 * @param nomePropriedade cujo valor será retornado.
	 * @return valor internacionalizado
	 */
	default String getValorPropriedadeInternacionalizada(String nomePropriedade) {
		String valor = null;
		Idiomas idioma = Idiomas.valueOf(LocaleContextHolder.getLocale().getLanguage().toUpperCase());

		if (getInternacionalizacao() != null && !getInternacionalizacao().isEmpty()) {
			T internacional = getInternacionalizacao().get(idioma.getId());

			if (internacional != null) {
				try {
					Method metodo = internacional.getClass().getMethod("get" + nomePropriedade, null);
					valor = (String) metodo.invoke(internacional, null);
					/*Field propriedade = internacional.getClass().getDeclaredField(nomePropriedade);
					valor = (String) propriedade.get(internacional);*/
				}
				catch (NoSuchMethodException | SecurityException | IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
					valor = null;
				}
			}
		}

		return valor;
	}
}
