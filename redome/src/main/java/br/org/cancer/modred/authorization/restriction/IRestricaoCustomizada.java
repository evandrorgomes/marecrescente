package br.org.cancer.modred.authorization.restriction;

import java.io.Serializable;

import br.org.cancer.modred.model.security.CustomUser;

/**
 * 
 * @author Pizão.
 *
 * Interface para as implementações de restrição de acesso customizadas.
 *
 * @param <T> Classe de dominio que deverá ser utilizada para validação.
 */
public interface IRestricaoCustomizada <T extends Serializable> {

	/**
	 * Realiza a verificação customizada para o recurso informado no parâmetro.
	 * 
	 * @param usuario Usuário logado na aplicação
	 * @param recurso Recurso no qual a restrição está associada (a flag PERM_IN_COM_RESTRICAO deve estar marcada como TRUE no
	 * banco)
	 * @param objetoParametro Parametro enviado do controlador para a validação
	 * @return
	 */
	boolean hasPermissao(CustomUser usuario, String recurso, T objetoParametro);

	/**
	 * Realiza o cast do objeto recebido para o objeto esperado no generics da classe.
	 * 
	 * @param entity o objeto e que deve ser vasculhado e retornado o objeto esperado que deve estar contido nele.
	 * @return o objeto esperado para classe de validação.
	 */
	T castTo(Object entity);
}
