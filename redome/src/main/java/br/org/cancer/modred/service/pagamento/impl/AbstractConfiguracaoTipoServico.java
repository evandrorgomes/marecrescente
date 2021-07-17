/**
 * 
 */
package br.org.cancer.modred.service.pagamento.impl;

import br.org.cancer.modred.service.pagamento.IConfiguracaoTipoServico;

/**
 * Interface que define a estrutura da configuração necessária para a
 * realização de requisições genéricas de tipos de serviços.
 * 
 * @author bruno.sousa
 * 
 */
public abstract class AbstractConfiguracaoTipoServico implements IConfiguracaoTipoServico{

	private Long idTipoServico;
	private Class<?> entidade;
		
	/**
	 * Coonstrutor para padronizar as extensões de configuração de tarefa, forçando
	 * que seja passado um ID do tipo tarefa para cada instância.
	 * 
	 * @param idTipoTarefa ID do tipo de tarefa associado.
	 */
	protected AbstractConfiguracaoTipoServico(Long idTipoServico) {
		super();		
		this.idTipoServico = idTipoServico;
	}
	
	public Class<?> getEntidade() {
		return entidade;
	}
	
	/*public void setEntidade(Class<?> entidade) {
		this.entidade = entidade;
	}*/

	/**
	 * @param entidade que está relacionada as tarefas desse tipo
	 * @return IConfiguracaoProcessServer configurada
	 */
	public IConfiguracaoTipoServico relacionadoCom(Class<?> entidade) {
		this.entidade = entidade;
		return this;
	}


	/**
	 * Retorna o ID do tipo de serviço.
	 * 
	 * @return the idTipoServico ID do tipo de serviço.
	 */
	public Long getIdTipoServico() {
		return idTipoServico;
	}
	


}
