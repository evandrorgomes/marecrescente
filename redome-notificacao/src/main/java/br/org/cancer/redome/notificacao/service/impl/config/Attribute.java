package br.org.cancer.redome.notificacao.service.impl.config;

/**
 * Representa uma informação que precisa ser exibido (Projection) 
 * ou definir uma ordenação (OrderBy) para as funcionalidades da classe
 * AbstractService.
 * 
 * @author Pizão
 *
 */
public abstract class Attribute {

	protected String attributeName;
	
	public Attribute(String attributeName) {
		this.attributeName = attributeName;
	}
	
	/**
	 * Representa o nome do atributo dentro do 
	 * modelo de entidades do back-end.
	 * 
	 * @return o nome do atributo, conforme definido na classe.
	 */
	public String getAttributeName() {
		return attributeName;
	}
	
}
