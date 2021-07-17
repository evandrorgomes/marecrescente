package br.org.cancer.redome.courier.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import br.org.cancer.redome.courier.controller.dto.RetornoInclusaoDTO;
import br.org.cancer.redome.courier.controller.dto.TransportadoraListaDTO;
import br.org.cancer.redome.courier.model.ContatoTelefonicoTransportadora;
import br.org.cancer.redome.courier.model.EmailContatoTransportadora;
import br.org.cancer.redome.courier.model.Transportadora;
import br.org.cancer.redome.courier.service.custom.IService;

/**
 * Métodos de negócio de transportadora.
 * @author Filipe Paes
 *
 */
public interface ITransportadoraService extends IService<Transportadora, Long> {

	
	/**
	 * Obtém uma lista paginada de transportadora.
	 * 
	 * @param pageRequest referencia de paginação.
	 * @param nome nome da transportadora para filtro.
	 * @return lista da transportadora paginada.
	 */
	Page<Transportadora> listarTransportadoras(PageRequest pageRequest, String nome); 
	
	/**
	 * Exclusão lógica de transportadora.
	 * @param idTransportadora id da transportadora a ser excluida.
	 */
	void inativarTransportadora(Long idTransportadora);
	
	
	/**
	 * Insere uma nova transportadora na base de dados.
	 * @param transportadora item a ser inserido.
	 */
	void criarTransportadora(Transportadora transportadora);
		
	/**
	 * Método para adicionar endereço a transportadora.
	 * 
	 * @param id - id da transportadora
	 * @param emailTransportadora - Email a ser adicionado 
	 * @return dto com o endereço salvo
	 */
	RetornoInclusaoDTO adicionarEmail(Long id, EmailContatoTransportadora emailTransportadora);

	/**
	 * Método para adicionar telefone a transportadora.
	 * 
	 * @param id - id da transportadora
	 * @param contato - Telefone de contato a ser adicionado
	 * @return dto com o telefone salvo
	 */
	RetornoInclusaoDTO adicionarContatoTelefonico(Long id, ContatoTelefonicoTransportadora contato);
	
	
	/**
	 * Método para listar id e nome das transportadoras ativas.
	 * 
	 * @return Lista de transportadoras ativas.
	 */
	List<TransportadoraListaDTO> listarIdENomeDeTransportadoresAtivas();

	/**
	 * Método para obter transportadoras ativas.
	 * 
	 * @param id - identificacao transportadora.
	 * @return Transportadora - objeto Transportadora. 
	 */
	Transportadora obterTransportadoraPorId(Long id);
}
