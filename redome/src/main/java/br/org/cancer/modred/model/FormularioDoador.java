package br.org.cancer.modred.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.TarefaView;
import br.org.cancer.modred.model.security.Usuario;

/**
 * Classe que representa o FormularioDoador. 
 * 
 * @author bruno.sousa
 *
 */
@Entity
@Table(name = "FORMULARIO_DOADOR")
@SequenceGenerator(name = "SQ_FODO_ID", sequenceName = "SQ_FODO_ID", allocationSize = 1)
public class FormularioDoador implements Serializable {

	private static final long serialVersionUID = 8090566759771193498L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_FODO_ID")
    @Column(name = "FODO_ID")
    @JsonView(value = { TarefaView.Listar.class})
    private Long id;
	
	@ManyToOne
	@JoinColumn(name = "FORM_ID")
	@NotNull
    private Formulario formulario;
	
	@ManyToOne
	@JoinColumn(name = "PECO_ID")
	@NotNull
    private PedidoContato pedidoContato;
	
	@ManyToOne
	@JoinColumn(name = "DOAD_ID")
	@NotNull
	private DoadorNacional doadorNacional;

	@Column(name = "PEWO_ID")
	@NotNull
	private Long pedidoWorkup;
	
	@Column(name = "FODO_DT_RESPOSTA")
	private LocalDateTime dataResposta;
	
	@Lob
	@Column(name = "FODO_TX_RESPOSTAS")
	private String respostas;
	
	@ManyToOne
	@JoinColumn(name = "USUA_ID")
	@NotNull	
	private Usuario usuario;
		
	public FormularioDoador() {
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
	 * Fomulário associado.
	 * 
	 * @return the formulario
	 */
	public Formulario getFormulario() {
		return formulario;
	}

	
	/**
	 * Fomulário associado.
	 * 
	 * @param formulario the formulario to set
	 */
	public void setFormulario(Formulario formulario) {
		this.formulario = formulario;
	}

	
	/**
	 * Pedido de Contato associado.
	 * 
	 * @return the pedidoContato
	 */
	public PedidoContato getPedidoContato() {
		return pedidoContato;
	}

	
	/**
	 * Pedido de Contato associado.
	 * 
	 * @param pedidoContato the pedidoContato to set
	 */
	public void setPedidoContato(PedidoContato pedidoContato) {
		this.pedidoContato = pedidoContato;
	}

	
	/**
	 * Doador Associado.
	 * 
	 * @return the doador
	 */
	public DoadorNacional getDoadorNacional() {
		return doadorNacional;
	}

	
	/**
	 * Doador Associado.
	 * 
	 * @param doador the doador to set
	 */
	public void setDoadorNacional(DoadorNacional doadorNacional) {
		this.doadorNacional = doadorNacional;
	}

	
	/**
	 * Data da resposta.
	 * 
	 * @return the dataResposta
	 */
	public LocalDateTime getDataResposta() {
		return dataResposta;
	}

	
	/**
	 * Data da resposta.
	 * 
	 * @param dataResposta the dataResposta to set
	 */
	public void setDataResposta(LocalDateTime dataResposta) {
		this.dataResposta = dataResposta;
	}

	
	/**
	 * Json contendo as perguntas com as opções (quando existirem) e a resposta.
	 * 
	 * @return the respostas
	 */
	public String getRespostas() {
		return respostas;
	}

	
	/**
	 * Json contendo as perguntas com as opções (quando existirem) e a resposta.
	 * 
	 * @param respostas the respostas to set
	 */
	public void setRespostas(String respostas) {
		this.respostas = respostas;
	}

	/**
	 * @return the usuario
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the pedidoWorkup
	 */
	public Long getPedidoWorkup() {
		return pedidoWorkup;
	}


	/**
	 * @param pedidoWorkup the pedidoWorkup to set
	 */
	public void setPedidoWorkup(Long pedidoWorkup) {
		this.pedidoWorkup = pedidoWorkup;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataResposta == null) ? 0 : dataResposta.hashCode());
		result = prime * result + ((doadorNacional == null) ? 0 : doadorNacional.hashCode());
		result = prime * result + ((formulario == null) ? 0 : formulario.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((pedidoContato == null) ? 0 : pedidoContato.hashCode());
		result = prime * result + ((pedidoWorkup == null) ? 0 : pedidoWorkup.hashCode());
		result = prime * result + ((respostas == null) ? 0 : respostas.hashCode());
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FormularioDoador other = (FormularioDoador) obj;
		if (dataResposta == null) {
			if (other.dataResposta != null)
				return false;
		} else if (!dataResposta.equals(other.dataResposta))
			return false;
		if (doadorNacional == null) {
			if (other.doadorNacional != null)
				return false;
		} else if (!doadorNacional.equals(other.doadorNacional))
			return false;
		if (formulario == null) {
			if (other.formulario != null)
				return false;
		} else if (!formulario.equals(other.formulario))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (pedidoContato == null) {
			if (other.pedidoContato != null)
				return false;
		} else if (!pedidoContato.equals(other.pedidoContato))
			return false;
		if (pedidoWorkup == null) {
			if (other.pedidoWorkup != null)
				return false;
		} else if (!pedidoWorkup.equals(other.pedidoWorkup))
			return false;
		if (respostas == null) {
			if (other.respostas != null)
				return false;
		} else if (!respostas.equals(other.respostas))
			return false;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		return true;
	}


//	/* (non-Javadoc)
//	 * @see java.lang.Object#hashCode()
//	 */
//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + ( ( dataResposta == null ) ? 0 : dataResposta.hashCode() );
//		result = prime * result + ( ( doadorNacional == null ) ? 0 : doadorNacional.hashCode() );
//		result = prime * result + ( ( formulario == null ) ? 0 : formulario.hashCode() );
//		result = prime * result + ( ( id == null ) ? 0 : id.hashCode() );
//		result = prime * result + ( ( pedidoContato == null ) ? 0 : pedidoContato.hashCode() );
//		result = prime * result + ( ( respostas == null ) ? 0 : respostas.hashCode() );
//		return result;
//	}
//
//
//	/* (non-Javadoc)
//	 * @see java.lang.Object#equals(java.lang.Object)
//	 */
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj) {
//			return true;
//		}
//		if (obj == null) {
//			return false;
//		}
//		if (!( obj instanceof FormularioDoador )) {
//			return false;
//		}
//		FormularioDoador other = (FormularioDoador) obj;
//		if (dataResposta == null) {
//			if (other.dataResposta != null) {
//				return false;
//			}
//		}
//		else
//			if (!dataResposta.equals(other.dataResposta)) {
//				return false;
//			}
//		if (doadorNacional == null) {
//			if (other.doadorNacional != null) {
//				return false;
//			}
//		}
//		else
//			if (!doadorNacional.equals(other.doadorNacional)) {
//				return false;
//			}
//		if (formulario == null) {
//			if (other.formulario != null) {
//				return false;
//			}
//		}
//		else
//			if (!formulario.equals(other.formulario)) {
//				return false;
//			}
//		if (id == null) {
//			if (other.id != null) {
//				return false;
//			}
//		}
//		else
//			if (!id.equals(other.id)) {
//				return false;
//			}
//		if (pedidoContato == null) {
//			if (other.pedidoContato != null) {
//				return false;
//			}
//		}
//		else
//			if (!pedidoContato.equals(other.pedidoContato)) {
//				return false;
//			}
//		if (respostas == null) {
//			if (other.respostas != null) {
//				return false;
//			}
//		}
//		else
//			if (!respostas.equals(other.respostas)) {
//				return false;
//			}
//		return true;
//	}

	
	
}
