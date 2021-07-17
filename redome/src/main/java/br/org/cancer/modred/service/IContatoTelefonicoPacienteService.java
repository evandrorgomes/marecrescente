package br.org.cancer.modred.service;

import java.util.List;

import br.org.cancer.modred.model.ContatoTelefonicoPaciente;
import br.org.cancer.modred.model.Paciente;
import br.org.cancer.modred.util.CampoMensagem;

/**
 * Classe para utilização de serviços envolvidos com a 
 * entidade ContatoTelefonico.
 * 
 * @author Pizão.
 *
 */
public interface IContatoTelefonicoPacienteService {
	
	/**
	 * Atualiza os contatos telefônicos.
	 * 
	 * @param paciente paciente que sofrerá a atualização.
	 * @param telefones lista de telefones atual.
	 * 
	 * @return lista de contatos atualizados.
	 */
	List<ContatoTelefonicoPaciente> atualizar(Paciente paciente, List<ContatoTelefonicoPaciente> telefones);
	
	/**
	 * Realiza a validação da lista informada, se está dentro dos parâmetros de preenchimento.
	 * 
	 * @param telefones lista de telefones a serem validados.
	 * 
	 * @return mensagens de erro, caso ocorram.
	 */
	List<CampoMensagem> validar(List<ContatoTelefonicoPaciente> telefones);
	
}
