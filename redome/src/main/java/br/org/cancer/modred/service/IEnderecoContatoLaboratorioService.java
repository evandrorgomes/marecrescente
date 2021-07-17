package br.org.cancer.modred.service;

import java.util.List;

import br.org.cancer.modred.model.EnderecoContatoLaboratorio;
import br.org.cancer.modred.service.custom.IService;

/**
 * Interface para tratar parte de negocios de Endereco de Contato Doador.
 * 
 * @author Pizão
 *
 */
public interface IEnderecoContatoLaboratorioService extends IService<EnderecoContatoLaboratorio, Long> {

	/**
	 * Lista os endereços para o laboratorio informado.
	 * 
	 * @param idLaboratorio identificação de laboratorio.
	 * @return lista de endereços do laboratorio.
	 */
	List<EnderecoContatoLaboratorio> listarEnderecos(Long idLaboratorio);
}
