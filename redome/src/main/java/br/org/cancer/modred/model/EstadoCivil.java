package br.org.cancer.modred.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.BuscaView;
import br.org.cancer.modred.controller.view.DoadorView;

/**
 * Classe de persistência de estado civil para tabela ESTADO CIVIL.
 * @author Elizabete Poly
 *
 */

@Entity
@Table (name = "ESTADO_CIVIL")
public class EstadoCivil implements Serializable {
	
	private static final long serialVersionUID = -3831433658332543061L;

	/**
	 * Chave que identifica com exclusividade uma instância desta classe.
	 */
	@Id
	@Column(name = "ESCI_ID")
    @JsonView({  BuscaView.Enriquecimento.class, DoadorView.AtualizacaoFase2.class, DoadorView.ContatoPassivo.class, DoadorView.ContatoFase2.class})
	private Long id;

	/**
	 * Nome descritivo do estado Civil.
	 */	
	@Column(name = "ESCI_TX_NOME")
	@JsonView({ BuscaView.Enriquecimento.class, DoadorView.AtualizacaoFase2.class, DoadorView.ContatoPassivo.class, DoadorView.ContatoFase2.class })
	private String nome;
	
	/**
	 * Construtor padrão.
	 */	
	public EstadoCivil() {}

	/**
	 * @param id
	 */
	public EstadoCivil(Long id) {
		this.id = id;
	}

	/**
	 * Construtor com todos os campos da classe.
	 */
	public EstadoCivil(Long id, String nome) {		
		this.id = id;
		this.nome = nome;
	}

	/**
	 * Método que obtem o id do estado civil.
	 * 
	 * @return Long
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Método que atribui o id do estado civil.
	 * 
	 * @param Long
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Método que obtem o nome do estado civil.
	 * 
	 * @return String
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * Método que atribui nome do estado civil.
	 * 
	 * @param String
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/* 
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());		
		return result;
	}

	/* 
	 *  {@inheritDoc}
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
		
		EstadoCivil other = (EstadoCivil) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}	
		} 
		else if (!id.equals(other.id)) {
			return false;
		}	
		if (nome == null) {
			if (other.nome != null) {
				return false;
			}	
		} 
		else if (!nome.equals(other.nome)) {
			return false;
		}	
		return true;
	}
	
	
	
}



