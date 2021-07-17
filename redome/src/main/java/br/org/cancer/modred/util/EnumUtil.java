package br.org.cancer.modred.util;

import br.org.cancer.modred.model.interfaces.EnumType;

/**
 * 
 * @author Pizão
 * 
 * Classe que facilita operações envolvendo os enums do sistema.
 *
 */
public class EnumUtil {

	/**
	 * Verifica se determinado valor existe como ID de uma determinada classe de Enum.
	 * 
	 * @param enumClass classe de enum.
	 * @param value valor do ID.
	 * @return TRUE, caso exista.
	 */
	public static boolean contains(Class<? extends EnumType<?>> enumClass, Object value) {
		if (value == null) {
			return true;
		}

		boolean valid = false;

		for (EnumType<?> enumConst : enumClass.getEnumConstants()) {
			final Object keyValue = enumConst.getId();
			if (keyValue.equals(value)) {
				valid = true;
				break;
			}
		}

		return valid;
	}
	
	/**
	 * Retorna o enum correspondente ao valor informado. 
	 * @param <T>
	 * 
	 * @param enumClass classe do enum.
	 * @param value valor correspondente ao ID.
	 * @return enum correspondente.
	 */
	public static EnumType<?> valueOf(Class<? extends EnumType<?>> enumClass, Object value) {
		EnumType<?>[] values = enumClass.getEnumConstants();
		for (int i = 0; i < values.length; i++) {
			if (values[i].getId().equals(value)) {
				return values[i];
			}
		}
		throw new IllegalArgumentException("ID " + value + " não existe para o enum " + enumClass.getCanonicalName());
	}
	
}
