package br.org.cancer.redome.courier.service.impl.config;

/**
 * Representa o atributo projetado no resultado de uma pesquisa.
 * 
 * @author Pizão.
 *
 */
public class Projection extends Attribute {
	
	/**
	 * Construtor sobrecarregado para facilitar a instanciação, além de
	 * explicitar a necessidade da informação.
	 * 
	 * @param attributeName nome do atributo a ser trazidos no resultado da busca.
	 */
	public Projection(String attributeName) {
		super(attributeName);
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
		Projection other = (Projection) obj;
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
