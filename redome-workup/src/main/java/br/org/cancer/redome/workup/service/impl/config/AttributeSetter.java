package br.org.cancer.redome.workup.service.impl.config;

/**
 * 
 * @author Pizão
 * 
 * Classe que representa a relação atributo/valor utilizados como filtros
 * nas instruções de SELECT (projeção e filtro) e update (valores a serem atualizados e filtros), por exemplo.
 * 
 * 
 * @param <T> tipo do valor a ser setado.
 */
public abstract class AttributeSetter<T> {
	
	private String attributeName;
	private T value;
	
	/**
	 * Construtor sobrecarregado para facilitar a instanciação, 
	 * além de explicitar a necessidade de preenchimento de ambos 
	 * os valores.
	 * 
	 * @param attributeName
	 * @param value
	 */
	public AttributeSetter(String attributeName, T value) {
		this.attributeName = attributeName;
		this.value = value;
	}
	
	public String getAttributeName() {
		return attributeName;
	}
	
	/**
	 * Retorna o nome do atributo formatado para o parâmetro.
	 * Basicamente, remove caracteres inválidos (ponto, nesse caso) 
	 * para o valor possa ser usado como nome de parâmetro.
	 * 
	 * @return parâmetro formatado.
	 */
	public String getParameterName() {
		return attributeName.replaceAll("[.]", "_");
	}
	
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}
	
	public T getValue() {
		return value;
	}
	
	public void setValue(T value) {
		this.value = value;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((attributeName == null) ? 0 : attributeName.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj){
			return true;
		}
		if (obj == null){
			return false;
		}
		if (getClass() != obj.getClass()){
			return false;
		}
		AttributeSetter<T> other = (AttributeSetter<T>) obj;
		if (attributeName == null) {
			if (other.attributeName != null){
				return false;
			}
		}
		else if (!attributeName.equals(other.attributeName)){
			return false;
		}
		return true;
	}

}
