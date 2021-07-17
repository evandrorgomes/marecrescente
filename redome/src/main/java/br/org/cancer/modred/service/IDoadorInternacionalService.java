package br.org.cancer.modred.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.org.cancer.modred.controller.dto.ExameDoadorInternacionalDto;
import br.org.cancer.modred.controller.dto.PedidoDto;
import br.org.cancer.modred.controller.dto.doador.DoadorCordaoInternacionalDTO;
import br.org.cancer.modred.controller.dto.doador.OrigemPagamentoDoadorDTO;
import br.org.cancer.modred.model.DoadorInternacional;
import br.org.cancer.modred.model.ExameDoadorInternacional;

/**
 * Interface de acesso as funcionalidades envolvendo a classe DoadorInternacional.
 * 
 * @author bruno.sousa
 */
public interface IDoadorInternacionalService extends IDoadorService {

	/**
	 * Salvar doador internacional na base do Redome. 
	 * Se o rmr for informado será feita a reservar para o paciente.
	 * Se o pedido for informado será criado a solicitação conforme o tipo de exame informado.
	 * 
	 * @param doadorCordaoInternacionalDTO doador internacional a ser salvo.
	 * @param pedidoDto deverá vir preenchido com o tipo de exame e os locus selecionados caso o tipo de exame seja TIPIFICACAO_HLA_ALTA_RESOLUCAO
	 * @return doadorInternacionalDto salvo.
	 */
	DoadorInternacional salvarDoadorInternacionalComPedidoExameSeSolicitado(DoadorCordaoInternacionalDTO doadorCordaoInternacionalDTO, PedidoDto pedido);

	/**
	 * Verifica se o doador já existe na base. Considerar como chave candidata os campos: 1. ID do doador e Registro 2. GRID, se
	 * for preenchido e já estiver em uso.
	 * 
	 * @param doador Doador a ser verificado.
	 * @return TRUE se já existir doador com as mesmas informações que devem ser únicas.
	 */
	boolean verificarDoadorJaExistente(DoadorInternacional doador);

	/**
	 * Obtém lista de doadores internaionacionais paginada.
	 * 
	 * @param idDoadorRegistro - identificação do doador.
	 * @param idRegistro - identificação do registro pagador.
	 * @param grid - identificação internacional.
	 * @return lista paginada de doadores internacionais.
	 */
	Page<DoadorInternacional> obterDoadoresInternacional(String idDoadorRegistro, Long idRegistro, String grid, Pageable page);

	/**
	 * Obtém o idRegistro do doador internacional, buscando pela pk do doador.
	 * 
	 * @param idDoador
	 * @return
	 */
	String obterIdRegistroPorIdDoador(Long idDoador);
	
	/**
	 * Obtém um DTO do doador internacional por id.
	 * @param idDoador - id do doador.
	 * @return DTO de doador internacional.
	 */
	DoadorCordaoInternacionalDTO obterDoadorPorId(Long idDoador);
	
	/**
	 * Obtem doador internacional por codigo grid.
	 * @return doador caso seja localizado ou null caso não seja localizado.
	 */
	DoadorInternacional obterDoadorPorGrid(String grid);

	/**
	 * Obtém lista de exames de doador por id de doador.
	 * @param idDoador a ser  consultado.
	 * @return listagem de DTO de exames.
	 */
	List<ExameDoadorInternacionalDto> listarExamesDeDoador(Long idDoador);
	
	
	/** 
	 * Atualiza dados pessoais de doador internacional.
	 * @param id do doador internacional.
	 * @param doador a ser atualizado.
	 */
	void atualizarDadosPessoais(Long id, DoadorInternacional doador);
	
	/**
	 * Atualiza os dados de pagamento de doador internacional.
	 * @param doador com os dados de pagamento a serem atualizados.
	 */
	void atualizarOrigemPagamento(OrigemPagamentoDoadorDTO registroDto);
	
	/**
	 * Salva um exame do doador. Este método deve ser utilizado para ratificação 
	 * de possiveis erros de exames de doador. Fora deste caso deve-se criar um 
	 * pedido de exame de doador e depois gravar o resultado.
	 * @param idDoador referência do doador.
	 * @param exame a ser persistido.
	 * @throws Exception caso haja erro ao gravar.
	 */
	void salvarExame(Long idDoador, ExameDoadorInternacional exame)throws Exception;
	
	/**
	 * Obtém um doador internacional de acordo com o a id do registro.
	 * @param id do registro do doador internacional.
	 * @return objeto do doador internacional encontrado.
	 */
	DoadorInternacional obterDoadorInternacionalPorIdRegistro(String idRegistro);

	/**
	 * Obtém o id do doador internacional caso exista um doador cadastrado com o id do registro internacional.
	 * 
	 * @param idRegistro Identificador do doador no registro internacional.
	 * @return id do doador se existir, caso contrário retorna null;
	 */
	Long obterIdentifiadorDoadorInternacionalPorIdDoRegistro(String idRegistro);

	/**
	 * Método para atualizar o doador internacional.
	 * 
	 * @param id - identificador do doador internacional
	 * @param doadorCordaoInternacionalDTO Dados para serem atualizados.
	 * @throws Exception
	 */
	void atualizar(Long id, DoadorCordaoInternacionalDTO doadorCordaoInternacionalDTO) throws Exception;
	
	/**
	 * Método para salvar o doador internacional vindo do WMDA.
	 * 
	 * @param DoadorCordaoInternacionalDTO Dados para serem atualizados.
	 * @return DoadorInternacional objeto DoadorInternacional.
	 */
	Long salvarDoadorInternacionalWmda(DoadorCordaoInternacionalDTO doadorCordaoInternacionalDTO);
	
	/**
	 * Verifica se o doador wmda já existe na base. Considerar como chave candidata os campos: 1. ID do doador e Registro 2. GRID, se
	 * for preenchido e já estiver em uso.
	 * 
	 * @param doador Doador a ser verificado.
	 * @return TRUE se já existir doador com as mesmas informações que devem ser únicas.
	 */
	boolean verificarDoadorWmdaJaExistente(DoadorInternacional doador);

}
