package br.org.cancer.modred.service.pagamento.impl;

import br.org.cancer.modred.service.pagamento.IAtualizarPagamento;
import br.org.cancer.modred.service.pagamento.ICancelarPagamento;
import br.org.cancer.modred.service.pagamento.ICriarPagamento;
import br.org.cancer.modred.service.pagamento.IObterPagamento;

/**
 * Classe responsável por configurar a utilização dos tipos de serviço na aplicação.
 * 
 * @author bruno.sousa
 *
 */
public class ConfiguracaoTipoServico extends AbstractConfiguracaoTipoServico{


	private IObterPagamento obterPagamento;
	private ICriarPagamento criarPagamento;
	private ICancelarPagamento cancelarPagamento;
	private IAtualizarPagamento atualizarPagamento;
	
	/**
	 * Construtor preparado para receber o ID do tipo de tarefa, obrigatoriamente.
	 * 
	 * @param idTipoTarefa ID do tipo de tarefa.
	 */
	private ConfiguracaoTipoServico(Long idTipoServico) {
		super(idTipoServico);
	}
	
	/**
	 * Cria uma nova instância para o tipo de Serviço informado.
	 * 
	 * @param idTipoServico ID do tipo de Serciço.
	 * @return nova instancia de ConfiguracaoTipoServico
	 */
	public static ConfiguracaoTipoServico newInstance(Long idTipoServico) {
		return new ConfiguracaoTipoServico(idTipoServico);
	}
	
	@Override
	public IObterPagamento obterPagamento() {
		if(obterPagamento != null){
			return obterPagamento;
		}
		
		return new ObterPagamento(getIdTipoServico());
	}

	@Override
	public ICriarPagamento criarPagamento() {
		if(criarPagamento != null){
			return criarPagamento;
		}
		return new CriarPagamento(getIdTipoServico());
	}
	
	/**
	 * UTILIZAR APENAS NOS TESTES UNITARIOS.
	 * @param obterPagamento the obterPagamento to set
	 */
	public void setObterPagamento(IObterPagamento obterPagamento) {
		this.obterPagamento = obterPagamento;
	}

	/**
	 * UTILIZAR APENAS NOS TESTES UNITARIOS.
	 * @param criarPagamento the criarPagamento to set
	 */
	public void setCriarPagamento(ICriarPagamento criarPagamento) {
		this.criarPagamento = criarPagamento;
	}
	
	/**
	 * UTILIZAR APENAS NOS TESTES UNITARIOS.
	 * @param cancelarPagamento
	 */
	public void setCancelarPagamento(ICancelarPagamento cancelarPagamento){
		this.cancelarPagamento = cancelarPagamento;
	}

	@Override
	public ICancelarPagamento cancelarPagamento() {
		if(cancelarPagamento != null){
			return this.cancelarPagamento;
		}
		return new CancelarPagamento(getIdTipoServico());
	}

	@Override
	public IAtualizarPagamento atualizarPagamento() {
		if(atualizarPagamento != null){
			return this.atualizarPagamento;
		}
		return new AtualizarPagamento(getIdTipoServico());
	}
	

}