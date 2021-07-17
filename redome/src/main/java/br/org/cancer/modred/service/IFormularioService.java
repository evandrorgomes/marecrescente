package br.org.cancer.modred.service;

import br.org.cancer.modred.controller.dto.ResultadoWorkupNacionalDTO;
import br.org.cancer.modred.model.Formulario;
import br.org.cancer.modred.model.domain.TiposFormulario;

/**
 * Interface para metodos de negocio de Formulário.
 * 
 * @author bruno.sousa
 *
 */
public interface IFormularioService {

	/**
	 * Método para obter o formulário por idDoador e tipo e que esteja ativo. 
	 * 
	 * @param id
	 * @param tipoFormulario
	 * @return
	 */
	Formulario obterFormulario(Long id, TiposFormulario tipoFormulario);
	
	/**
	 * Método para gravar o formulário preenchido parcialmente ou total com ou sem validação com pedido de workup.
	 * 
	 * @param Long id - identificador pedido de workup
	 * @param formulario - formulario a ser persistido.
	 */
	void salvarFormularioComPedidoWorkup(Long id, Formulario formulario);

	/**
	 * Método para gravar o formulário preenchido parcialmente ou total com ou sem validação com pedido de contato.
	 * 
	 * @param Long id - identificador pedido de contato
	 * @param formulario - formulario a ser persistido.
	 */
	void salvarFormularioComPedidoContato(Long id, Formulario formulario);

	/**
	 * Método para finalizar o formulário preenchido com validação com pedido de workup.
	 * 
	 * @param Long idPedidoWorkup - identificador pedido de workup
	 * @param formulario - formulario a ser persistido.
	 * 
	 * @return Formulario - retorna o formulario corrente.
	 */
	Formulario finalizarFormularioComPedidoWorkup(Long idPedidoWorkup, Formulario formulario);

	/**
	 * Método para finalizar o formulário preenchido com validação e resultado de workup nacional.
	 * 
	 * @param Long idPedidoWorkup - identificador pedido de workup.
	 * @param ResultadoWorkupNacionalDTO - DTO de resultado de workup nacional.
	 * @param formulario - formulario a ser persistido.
	 * 
	 * @return Formulario - retorna o formulario corrente.
	 */
	Formulario finalizarFormularioResultadoWorkup(Long idPedidoWorkup,
			ResultadoWorkupNacionalDTO resultadoWorkupNacionalDTO, Formulario formulario);
	
	/**
	 * Método para finalizar o formulário de pós coleta preenchido com validação. 
	 * 
	 * @param Long idPedidoWorkup - identificador pedido de workup.	 * 
	 * @param formulario - formulario a ser persistido.
	 * 
	 * @return Formulario - retorna o formulario corrente.
	 */	
	
	Formulario finalizarFormularioPosColeta(Long idPedidoWorkup, Formulario formulario);
	
}
