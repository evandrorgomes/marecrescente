package br.org.cancer.redome.courier.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import br.org.cancer.redome.courier.controller.dto.RetornoInclusaoDTO;
import br.org.cancer.redome.courier.model.ContatoTelefonicoCourier;
import br.org.cancer.redome.courier.model.Courier;
import br.org.cancer.redome.courier.model.EmailContatoCourier;
import br.org.cancer.redome.courier.service.custom.IService;

/**
 * Interface para métodos de negocio de Courier.
 * 
 * @author Fillipe Queiroz
 */
public interface ICourierService extends IService<Courier, Long> {

	/**
	 * Lista todos os Courier's associados a transportadora logada.
	 * @return List<Courier> lista de courier's
	 */
	List<Courier> listarCourierPorTransportadoraLogada();
	
	/**
	 * Realiza busca de corier's ativos por transportadora 
	 * do usuario logado e de acordo com os parâmetros passados.
	 * 
	 * @param pageRequest pagina em questão.
	 * @param nome do courier (opcional).
	 * @param cpf do courier (opcional).
	 * @param id do courier (opcional).
	 * @return lista paginada de resultados.
	 */
	Page<Courier> listarCouriesAtivosPorTransportadoraEPor(PageRequest pageRequest,String nome,String cpf, Long id);
	
	/**
	 * Inseri na base um novo registro de courier.
	 * @param courier item a ser inserido.
	 */
	void inserirCourier(Courier courier);
	
	/**
	 * Atualiza um registro existente de courier.
	 * 
	 * @param courier item a ser atualizado.
	 * @param id identificação do courier.
	 */
	void atualizarCourier(Courier courier, Long id);
	
	
	/**
	 * Inativa um courier.
	 * @param id identificação do item a ser inativado.
	 */
	void inativarCourier(Long id);
	
	/**
	 * Adiciona um contato telefonico a um currier.
	 * @param id - identificação do courier.
	 * @param contato - objeto de contato.
	 * @return dto contendo mensagem e id do courier inserido.
	 */
	RetornoInclusaoDTO adicionarContatoTelefonico(Long id, ContatoTelefonicoCourier contato);
	
	/**
	 * Método para adicionar endereço ao courier.
	 * 
	 * @param id - id da transportadora
	 * @param emailCourier - Email a ser adicionado 
	 * @return dto com o endereço salvo
	 */
	RetornoInclusaoDTO adicionarEmail(Long id, EmailContatoCourier emailCourier);

	/**
	 * Obtém o courier.
	 * 
	 * @param id Identificador do courier
	 * @return Courier se existir ou BusinessException se não existir.
	 */
	Courier obterCourierPorId(Long id);

}
