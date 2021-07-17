package br.org.cancer.modred.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.DoadorView;
import br.org.cancer.modred.controller.view.PacienteView;

/**
 * Classe de persistencia de etnia para a tabela ETNIA.
 * 
 * @author Filipe Paes
 * 
 */
@Entity
public class Etnia implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * Chave que identifica com exclusividade uma instância desta classe.
	 */
	@Id
	@Column(name = "ETNI_ID")
	@JsonView({PacienteView.DadosPessoais.class, DoadorView.AtualizacaoFase2.class, DoadorView.ContatoPassivo.class,
		PacienteView.Rascunho.class, DoadorView.ContatoFase2.class})
	private Long id;
	/**
	 * Nome descritivo da etnia.
	 */
	@Column(name = "ETNI_TX_NOME")
	@JsonView({PacienteView.IdentificacaoCompleta.class, PacienteView.IdentificacaoParcial.class,
		DoadorView.AtualizacaoFase2.class, DoadorView.ContatoPassivo.class, DoadorView.ContatoFase2.class})
	private String nome;

	/**
	 * Construtor básico.
	 */
	public Etnia() {
	}

	/**
	 * Construtor com todos os campos da classe.
	 */
	public Etnia(Long id, String nome) {
		this.id = id;
		this.nome = nome;
	}

	/**
	 * Método que obtém a id da etnia.
	 * 
	 * @return Long
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Método que atribui a id da etnia.
	 * 
	 * @param Long
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Método que obtém o nome da etnia.
	 * 
	 * @return String
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * Método que atribui nome da etnia.
	 * 
	 * @param String
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}	

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
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
		Etnia other = (Etnia) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} 
		else
			if (!id.equals(other.id)) {
				return false;
			}
		return true;
	}
}