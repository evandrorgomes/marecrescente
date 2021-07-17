package br.org.cancer.modred.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;

import br.org.cancer.modred.controller.dto.RetornoInclusaoDTO;
import br.org.cancer.modred.controller.page.Paginacao;
import br.org.cancer.modred.model.ContatoTelefonicoDoador;
import br.org.cancer.modred.model.DoadorNacional;
import br.org.cancer.modred.model.EmailContatoDoador;
import br.org.cancer.modred.model.EnderecoContatoDoador;
import br.org.cancer.modred.model.PedidoLogistica;
import br.org.cancer.modred.util.CampoMensagem;
import br.org.cancer.modred.vo.ConsultaDoadorNacionalVo;

/**
 * Interface de acesso as funcionalidades envolvendo a classe Doador.
 * 
 * @author Pizão
 */
public interface IDoadorNacionalService extends IDoadorService {

	/**
	 * Obtém o doador associado ao ID da solicitação informado.
	 * 
	 * @param pedidoContatoId ID do pedido de contato a ser pesquisado.
	 * @return o doador relacionado a solicitação.
	 */
	DoadorNacional obterDoadorPorPedidoContato(Long pedidoContatoId);

	/**
	 * Obtém o doador associado ao DMR informado.
	 * 
	 * @param dmr dmr do doador a ser pesquisado.
	 * @return o doador nacional.
	 */
	public DoadorNacional obterDoadorNacionalPorDmr(Long dmr);
	
	/**
	 * Obtém de acorodo com tentativa de contato.
	 * 
	 * @param tentativaContatoId ID da tentativa de contato a ser pesquisado.
	 * @return o doador relacionado a solicitação.
	 */
	DoadorNacional obterDoadorPorTentativaDeContato(Long tentativaContatoId);

	/**
	 * Adiciona um novo endereço para o doador.
	 * 
	 * @param id
	 * 
	 * @param enderecoContato
	 */
	RetornoInclusaoDTO adicionarEnderecoContato(Long id, EnderecoContatoDoador enderecoContato);

	/**
	 * Adiciona um contato telefônico para o doador.
	 * 
	 * @param id Identificador do doador.
	 * @param contatoTelefonico novo contato do doador.
	 * @return RetornoInclusaoDTO - dto com o id do objeto incluido e mensagem
	 */
	RetornoInclusaoDTO adicionarContatoTelefonico(Long id, ContatoTelefonicoDoador contatoTelefonico);

	/**
	 * Verifica se doador já possui contato principal.
	 * 
	 * @param id identificador do doador.
	 * @return TRUE se já possui contato principal.
	 */
	boolean verificarDoadorTemContatoPrincipal(Long id);

	/**
	 * Adiciona um novo e-mail para o doador.
	 * 
	 * @param id identificação do doador.
	 * 
	 * @param email novo e-mail a ser cadastrado.
	 * @return retorno da inclusão contendo o ID e a mensagem.
	 */
	RetornoInclusaoDTO adicionarEmail(Long id, EmailContatoDoador email);

	/**
	 * Validação da classe Doador.
	 * 
	 * @param doador objeto a ser validado.
	 * @return lista de mensagens de erro.
	 */
	List<CampoMensagem> validar(DoadorNacional doador);

	/**
	 * Método para salvar a identificação do doador.
	 * 
	 * @param id - identificação do doador.
	 * @param doador - doador a ser gravado.
	 */
	void atualizarIdentificacao(Long id, DoadorNacional doador);

	/**
	 * Método para salvar dados pessoais do doador.
	 * 
	 * @param id - identificação do doador.
	 * @param doador - doador a ser gravado.
	 */
	void atualizarDadosPessoais(Long id, DoadorNacional doador);

	/**
	 * Obtém a naturalidade (somente a sigla) do doador.
	 * 
	 * @param id identificação do doador.
	 * @return sigla da UF.
	 */
	String obterNaturalidadeDoador(Long id);

	/**
	 * 
	 * Busca a lista de doadores por um dos parâmentros.
	 * 
	 * @param paginacao - paginacao da listagem
	 * @param id - identificador do doador
	 * @param nome - nome do doador
	 * @param cpf - cpf do doador
	 * @return lista com doadores
	 */
	Paginacao<ConsultaDoadorNacionalVo> listarDoadoresNacionaisVo(PageRequest paginacao, Long id, String nome, String cpf);

	/**
	 * Obtem o doador ativo a partir do pedido de logística.
	 * 
	 * @param pedidoLogistica pedido de logistica a ser utilizado na pesquisa.
	 * @return doador ativo associado ao pedido informado.
	 */
	DoadorNacional obterDoadorPorPedidoLogistica(PedidoLogistica pedidoLogistica);

	/**
	 * Retorna apenas o dmr a partir do id do doador.
	 * 
	 * @param idDoador
	 * @return
	 */
	Long obterDmrPorIdDoador(Long idDoador);

	/**
	 * Criar informações do doador provenientes da integração com o redome web.
	 * 
	 * @param doadores lista do redome web 
	 */
	void criarDoadorNacional(DoadorNacional doadorNacional);
	
	ConsultaDoadorNacionalVo obterDadosPedidoContatoPorDoador(ConsultaDoadorNacionalVo doador);
	
	Paginacao<DoadorNacional> listarDoadoresNacionais(PageRequest pageable, Long dmr, String nome);
	
}
