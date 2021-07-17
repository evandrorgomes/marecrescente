package br.org.cancer.modred.service;

import java.util.List;

import br.org.cancer.modred.model.EmailContatoDoador;

/**
 * Interface de negocios para Email de Contato Doador.
 * @author Filipe Paes
 *
 */
public interface IEmailContatoDoadorService {
	
	/**
	 * Validar o preenchimento do e-mail e salva no banco.
	 * Se o preenchimento estiver incorreto, lança exceção de validação.
	 * 
	 * @param email e-mail a ser salvo.
	 * @return TRUE salvo com sucesso, FALSE contrário.
	 */
	boolean salvar(EmailContatoDoador email);
	
	/**
	 * Validação da classe EmailContatoDoador.
	 * 
	 * @param email objeto a ser validado.
	 */
	void validar(EmailContatoDoador email);
	
	/**
	 * Lista os e-mails associados ao doador.
	 * 
	 * @param idDoador ID do doador.
	 * @return lista de e-mails.
	 */
	List<EmailContatoDoador> listarEmails(Long idDoador);
}
