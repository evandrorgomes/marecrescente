package br.org.cancer.modred.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.TarefaView;

/**
 * Classe que representa a RespostaFormularioDoador.
 * 
 * @author bruno.sousa
 *
 */
@Entity
@Table(name = "RESPOSTA_FORMULARIO_DOADOR")
@SequenceGenerator(name = "SQ_REFD_ID", sequenceName = "SQ_REFD_ID", allocationSize = 1)
public class RespostaFormularioDoador implements Serializable {

	private static final long serialVersionUID = -2305782108349831683L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_REFD_ID")
    @Column(name = "REFD_ID")
    @JsonView(value = { TarefaView.Listar.class})
    private Long id;
	
	@ManyToOne
	@JoinColumn(name = "FODO_ID")
	@NotNull
    private FormularioDoador formularioDoador;
	
	@Column(name = "REFD_TX_TOKEN")
	private String token;
	
	@Column(name = "REFD_TX_RESPOSTA")
	private String resposta;
		
	/**
	 * Construtor padrão.
	 */
	public RespostaFormularioDoador() {
	}
	
	
	/**
	 * Construtor sobrecarregado.
	 * 
	 * @param id - Identificador da entidade.
	 * @param token - Nome do Token.
	 * @param resposta - valor da resposta
	 */
	public RespostaFormularioDoador(Long id, String token, String resposta) {
		super();
		this.id = id;
		this.token = token;
		this.resposta = resposta;
	}

	/**
	 * Identificador da entidade.
	 * 
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	
	/**
	 * Identificador da entidade.
	 * 
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	
	/**
	 * Referência para o FormularioDoador.
	 * 
	 * @return the formularioDoador
	 */
	public FormularioDoador getFormularioDoador() {
		return formularioDoador;
	}

	
	/**
	 * Referência para o FormularioDoador.
	 * 
	 * @param formularioDoador the formularioDoador to set
	 */
	public void setFormularioDoador(FormularioDoador formularioDoador) {
		this.formularioDoador = formularioDoador;
	}

	
	/**
	 * Nome do Token. 
	 * 
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	
	/**
	 * Nome do Token.
	 * 
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	
	/**
	 * Valor da Resposta.
	 * 
	 * @return the resposta
	 */
	public String getResposta() {
		return resposta;
	}

	
	/**
	 * Valor da Resposta.
	 * 
	 * @param resposta the resposta to set
	 */
	public void setResposta(String resposta) {
		this.resposta = resposta;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( formularioDoador == null ) ? 0 : formularioDoador.hashCode() );
		result = prime * result + ( ( id == null ) ? 0 : id.hashCode() );
		result = prime * result + ( ( resposta == null ) ? 0 : resposta.hashCode() );
		result = prime * result + ( ( token == null ) ? 0 : token.hashCode() );
		return result;
	}


	/* (non-Javadoc)
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
		if (!( obj instanceof RespostaFormularioDoador )) {
			return false;
		}
		RespostaFormularioDoador other = (RespostaFormularioDoador) obj;
		if (formularioDoador == null) {
			if (other.formularioDoador != null) {
				return false;
			}
		}
		else
			if (!formularioDoador.equals(other.formularioDoador)) {
				return false;
			}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		}
		else
			if (!id.equals(other.id)) {
				return false;
			}
		if (resposta == null) {
			if (other.resposta != null) {
				return false;
			}
		}
		else
			if (!resposta.equals(other.resposta)) {
				return false;
			}
		if (token == null) {
			if (other.token != null) {
				return false;
			}
		}
		else
			if (!token.equals(other.token)) {
				return false;
			}
		return true;
	}
	
	
	

}
