package br.org.cancer.redome.notificacao.service.impl.config;

/**
 * Cria a condição para utilizar na comparação da montagem da query, dinamicamente,
 * no HQL.
 * 
 * @author Pizão
 */
public enum FilterComparator {

	EQUALS("="),
	NOT_EQUALS("<>"),
	IS_NULL("IS NULL"), 
	IS_NOTNULL("IS NOT NULL"),
	LESS_THAN("<"),
	GREATER_THAN(">"),
	LESS_THAN_OR_EQUALS("<="),
	GREATER_THAN_OR_EQUALS(">=");

	private String operation;

	FilterComparator(String operation) {
		this.operation = operation;
	}

	public String getOperation() {
		return this.operation;
	}
}
