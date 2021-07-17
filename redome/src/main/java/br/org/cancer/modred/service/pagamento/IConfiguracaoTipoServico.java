package br.org.cancer.modred.service.pagamento;

/**
 * @author bruno.sousa
 *
 * Define a interface para os eventos que envolvem requisições ao pagamento de serviços.
 */
public interface IConfiguracaoTipoServico {


	/**
	 * Entidade relacionada ao tipo de serviço.
	 * 
	 * @return classe da entidade relacionada.
	 */
	Class<?> getEntidade();
	
	/**
	 * Relaciona uma entidade para ser utilizada na hora de buscar os objetos relacionados.
	 * 
	 * @param entidade que está relacionada as tarefas desse tipo
	 * @return IConfiguracaoPagamento configurada
	 */
	IConfiguracaoTipoServico relacionadoCom(Class<?> entidade);

	/**
	 * Classe responsável por obter um pagamento.
	 * 
	 * @return interface
	 */
	IObterPagamento obterPagamento();
	
	
	/**
	 * Classe responsável por criar o pagamento.
	 * 
	 * @return interface
	 */
	ICriarPagamento criarPagamento();

	/**
	 * Classe responsável por cancelar pagamento.
	 * @return interface
	 */
	ICancelarPagamento cancelarPagamento();

	/**
	 * Classe responsável por atualizar pagamento.
	 * @return interface
	 */
	IAtualizarPagamento atualizarPagamento();

}
