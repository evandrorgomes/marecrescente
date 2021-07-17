package br.org.cancer.modred.service;

import br.org.cancer.modred.controller.dto.PedidoDto;
import br.org.cancer.modred.model.Doador;

/**
 * Interface para o serviço que trata do acesso a entidade Exame e relacionadas.
 * 
 * @author ergomes
 *
 */
public interface IExecutarProcedureMatchService {

	/**
	 * gera a procedure PROC_MATCH_PACIENTE.
	 * 
	 * @param RMR - codigo do paciente.
	 */
	void gerarMatchPaciente(Long rmr);

	/**
	 * gera a procedure PROC_MATCH_DOADOR.
	 * 
	 * @param doador DoadorInternacional ou CordaoInterncaional
	 * @param pedido Caso tenha sido solicitado o pedido de exame no momento do cadastro.
	 * @param rmr Caso o doador/cordão esteja associado a um paciente. 
	 */
	void gerarMatchDoador(Doador doador, PedidoDto pedido, Long rmr);
	
	

}
