package br.org.cancer.modred.model.security;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.UsuarioView;

/**
 * Classe de define quem são os sistemas que agrupam perfis no ModRed.
 * O sistema identifica o "parceiro" a que o perfil se refere, facilitando
 * o agrupamento a configuração de acesso no cadastro de usuários.
 * 
 * @author Pizão
 * 
 */
@Entity
@Table(name = "SISTEMA")
public class Sistema implements Serializable {
	private static final long serialVersionUID = -3966334312855681983L;

	@Id
	@Column(name = "SIST_ID")
	@JsonView(UsuarioView.Consultar.class)
	private Long id;
	
	@Column(name = "SIST_TX_NOME")
	@JsonView(UsuarioView.Consultar.class)
	private String nome;
	
	// Indica se o perfil está disponível para ser utilizado pela equipe do Redome.
	@Column(name = "SIST_IN_DISPONIVEL_REDOME")
	@JsonView(UsuarioView.Consultar.class)
	private Boolean disponivelRedome;
	

	public Sistema() {}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	/**
	 * Indica se o perfil está disponível para cadastro.
	 * @return TRUE se pode ser cadastrado pelo Redome.
	 */
	public Boolean getDisponivelRedome() {
		return disponivelRedome;
	}

	public void setDisponivelRedome(Boolean disponivelRedome) {
		this.disponivelRedome = disponivelRedome;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Sistema other = (Sistema) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		}
		else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}
	
}