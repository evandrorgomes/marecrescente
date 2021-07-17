package br.org.cancer.modred.notificacao;

import java.util.List;

import br.org.cancer.modred.feign.dto.NotificacaoDTO;
import br.org.cancer.modred.model.domain.Perfis;

/**
 * Classe responsável por criar uma notificação.
 * 
 * @author brunosousa
 *
 */
public interface ICriarNotificacao {
	
	/**
	 * Executa a criação da notificação.
	 */
	List<NotificacaoDTO> apply();
	
	/**
	 * Parceiro associado na notificacao possa fazer parte.
	 * 
	 * @param objetoParceiro
	 * @return
	 */
	ICriarNotificacao comParceiro(Long objetoParceiro);
	
	/**
	 * Usuario a ser notificado.
	 * 
	 * @param usuario
	 * @return
	 */
	ICriarNotificacao paraUsuario(Long usuario);
	
	/**
	 * Perfil a ser notificado.
	 * 
	 * @param perfil
	 * @return
	 */
	ICriarNotificacao paraPerfil(Perfis perfil);
	
	/**
	 * Seta a descrição da tarefa.
	 * 
	 * @param descricao
	 * @return
	 */
	ICriarNotificacao comDescricao(String descricao);
	
	/**
	 * Passa a identificação do paciente.
	 * 
	 * @param rmr ID do paciente.
	 * @return a referência do this da própria classe.
	 */
	ICriarNotificacao comPaciente(Long rmr);

}
