package br.org.cancer.modred.vo;

import java.io.Serializable;

/**
 * Classe criada para alteração de senha do usuário.
 * O WebSphere está dando erro com multipart form-data
 * SRVE8020E: Servlet does not accept multipart requests
 * 
 * @author brunosousa
 *
 */
public class AlterarSenhaVo implements Serializable {
	
	private static final long serialVersionUID = 8369610281746313351L;
	
	private String senhaAtual;
	private String novaSenha;
	/**
	 * @return the senhaAtual
	 */
	public String getSenhaAtual() {
		return senhaAtual;
	}
	/**
	 * @param senhaAtual the senhaAtual to set
	 */
	public void setSenhaAtual(String senhaAtual) {
		this.senhaAtual = senhaAtual;
	}
	/**
	 * @return the novaSenha
	 */
	public String getNovaSenha() {
		return novaSenha;
	}
	/**
	 * @param novaSenha the novaSenha to set
	 */
	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}
	
	

}
