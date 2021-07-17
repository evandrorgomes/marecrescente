package br.org.cancer.modred.model.interfaces;

/**
 * Interface que define parcialmente a estrutura dos enums utilizados
 * no sistema. O ideia seria utilizar o próprio enum como referência,
 * direto na classe, mas se houver necessidade ou preferência por controlar
 * através de um ID que ele contenha, essa classe deve ser implementada
 * no enum desejado.
 * 
 * @param <T> Tipo do ID utilizado no enum.
 * 
 * @author Pizão
 */
public interface EnumType<T> {
	
	/**
	 * Define o ID que deve ser usado para comparação 
	 * entre os enums.
	 * 
	 * @return ID customizado do enum.
	 */
	T getId();
}
