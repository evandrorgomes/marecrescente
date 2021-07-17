package br.org.cancer.modred.service;

import java.util.List;

import br.org.cancer.modred.controller.dto.ExameDoadorInternacionalDto;
import br.org.cancer.modred.controller.dto.PedidoDto;
import br.org.cancer.modred.controller.dto.doador.DoadorCordaoInternacionalDTO;
import br.org.cancer.modred.controller.dto.doador.OrigemPagamentoDoadorDTO;
import br.org.cancer.modred.model.CordaoInternacional;
import br.org.cancer.modred.model.ExameCordaoInternacional;

/**
 * Interface de acesso as funcionalidades envolvendo a classe CordaoInternacional.
 * 
 * @author bruno.sousa
 */
public interface ICordaoInternacionalService extends IDoadorService {

	/**
	 * Obtém o idRegistro do cordao internacional, buscando pela pk do doador.
	 * 
	 * @param idDoador
	 * @return
	 */
	String obterIdRegistroPorIdDoador(Long idDoador);

	/**
	 * Salvar cordão internacional na base do Redome. 
	 * Se o rmr for informado será feita a reservar para o paciente.
	 * Se o pedido for informado será criado a solicitação conforme o tipo de exame informado.
	 * 
	 * @param doadorCordaoInternacionalDTO cordao internacional a ser salvo.
	 * @param pedido caso seja solicitado junto com o cadastro o pedido de exame
	 * 
	 * @return doador internacional salvo.
	 */
	CordaoInternacional salvarCordaoInternacionalComPedidoExameSeSolicitado(DoadorCordaoInternacionalDTO doadorCordaoInternacionalDTO, PedidoDto pedido);
	
	/**
	 * Atualiza a Origem de Pagamento.
	 * 
	 * @param registroDto - Origem do registro.
	 */
	void atualizarOrigemPagamento(OrigemPagamentoDoadorDTO registroDto);

	/**
	 * Obtém a lista do cordao internacional, buscando pelo id do doador.
	 * 
	 * @param idDoador
	 * @return
	 */
	List<ExameDoadorInternacionalDto> listarExamesCordaoInternacional(Long idDoador);

	/**
	 * Salva um exame cordao internacional. Este método deve ser utilizado para ratificação 
	 * de possiveis erros de exames de doador. Fora deste caso deve-se criar um 
	 * pedido de exame de doador e depois gravar o resultado.
	 * @param idDoador referência do doador.
	 * @param exame a ser persistido.
	 * @throws Exception caso haja erro ao gravar.
	 */
	void salvarExameCordaoInternacional(Long idDoador, ExameCordaoInternacional exame) throws Exception;
	
	
	void atualizarDadosPessoais(Long id, CordaoInternacional cordao);
	
	void atualizar(Long id, DoadorCordaoInternacionalDTO doadorCordaoInternacionalDTO) throws Exception;
	
	void salvarExame(Long idDoador, ExameCordaoInternacional exame) throws Exception;
	
	
	/**
	 * Obtém o id do doador internacional caso exista um doador cadastrado com o id do registro internacional.
	 * 
	 * @param idRegistro Identificador do doador no registro internacional.
	 * @return id do doador se existir, caso contrário retorna null;
	 */
	Long obterIdentifiadorCordaoInternacionalPorIdDoRegistro(String idRegistro);

	/**
	 * Salva um doador cordao internacional. 
	 * 
	 * @param DoadorCordaoInternacionalDTO referência do doador.
	 * @return Long idDoador - Id do novo doador. 
	 */
	Long salvarDoadorCordaoInternacionalWmda(DoadorCordaoInternacionalDTO doadorCordaoInternacionalDTO);
	
	/**
	 * Obtém o doador internacional caso exista um doador cadastrado com o id do registro internacional.
	 * 
	 * @param idRegistro Identificador do doador no registro internacional.
	 * @return id do doador se existir, caso contrário retorna null;
	 */
	CordaoInternacional obterCordaoInternacionalPorIdRegistro(String idRegistro);
}
