package br.org.cancer.modred.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.org.cancer.modred.model.RegistroContato;
import br.org.cancer.modred.service.custom.IService;

/**
 * Interface de métodos de negócio para registro de contato.
 * @author Filipe Paes
 *
 */
public interface IRegistroContatoService  extends IService<RegistroContato, Long> {

	/**
	 * Lista registros de contato por pedido.
	 * @param page pagina a ser exibida.
	 * @param idPedidoContato identificação do pedido.
	 * @return listagem de registros de contato.
	 */
	Page<RegistroContato> listarPor(Pageable page,  Long idPedidoContato);
	
	/**
	 * Inclui na base um novo registro de contato.
	 * @param registro objeto a ser persistido.
	 */
	void inserir(RegistroContato registro);
}
