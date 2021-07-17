package br.org.cancer.modred.service;

import java.util.List;

import br.org.cancer.modred.controller.dto.RetornoInclusaoDTO;
import br.org.cancer.modred.model.ContatoTelefonicoDoador;

/**
 * Classe para utilização de serviços envolvidos com a entidade
 * ContatoTelefonicoDoador.
 * 
 * @author Pizão.
 *
 */
public interface IContatoTelefonicoDoadorService {

	/**
	 * Lista todos os contatos telefônicos para o doador informado.
	 * 
	 * @param idDoador identificador do doador.
	 * @return lista de telefones associados.
	 */
	List<ContatoTelefonicoDoador> listarTelefones(Long idDoador);

	/**
	 * Lista todos os contatos telefônicos para o doador informado.
	 * 
	 * @param idDoador
	 *            identificador do doador.
	 * @return um telefone celular (de preferência ao marcado como principal).
	 */
	ContatoTelefonicoDoador obterTelefoneCelularParaSMS(Long idDoador);
	
	/**
	 * Validar o preenchimento do contato e salva no banco.
	 * Se o preenchimento estiver incorreto, lança exceção de validação.
	 * 
	 * @param contato contato a ser salvo.
	 * @return RetornoInclusaoDTO salvo com sucesso e id do objeto salvo, FALSE contrário.
	 */
	RetornoInclusaoDTO salvar(ContatoTelefonicoDoador contato);
	
	/**
	 * Validação da classe ContatoTelefonicoDoador.
	 * 
	 * @param contatoDoador objeto a ser validado.
	 */
	void validar(ContatoTelefonicoDoador contatoDoador);

	/**
	 * Obtem o ContatoTelefonicoDoador pelo id.
	 * 	
	 * @param id ContatoTelefonicoDoador.
	 */
	ContatoTelefonicoDoador obterContatoTelefonicoDoador(Long id);
}
