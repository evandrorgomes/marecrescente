package br.org.cancer.modred.model;

import java.io.Serializable;
import java.time.LocalDateTime;

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

import br.org.cancer.modred.exception.BusinessException;

/**
 * Representa o pedido de contato para determinada solicitação.
 * 
 * @author Pizão
 * 
 */
@Entity
@SequenceGenerator(name = "SQ_DONC_ID", sequenceName = "SQ_DONC_ID", allocationSize = 1)
@Table(name = "DOADOR_NAO_CONTACTADO")
public class DoadorNaoContactado implements Serializable {

	private static final long serialVersionUID = 7774495959241636184L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_DONC_ID")
	@Column(name = "DONC_ID")	
	private Long id;

	@Column(name = "DONC_DT_CRIACAO")
	@NotNull
	private LocalDateTime dataCriacao;

	@Column(name = "DONC_IN_ABERTO")
	@NotNull
	private boolean aberto;
	
	@Column(name = "DONC_DT_FINALIZACAO")
	private LocalDateTime dataFinalizacao;
	
	@ManyToOne
	@JoinColumn(name = "DOAD_ID")
	@NotNull
	private Doador doador;

	@ManyToOne
	@JoinColumn(name = "PECO_ID")
	@NotNull
	private PedidoContato pedidoContato;
		

	/**
	 * Contrutor padrão.
	 */
	public DoadorNaoContactado() {
		dataCriacao = LocalDateTime.now();
		aberto = true;
	}

	/**
	 * Construtor recebendo o pedido de contato.
	 * 
	 * @param pedidoContato - Pedido de contato que originou oesse objeto. Pparametro obrigatório.
	 */
	public DoadorNaoContactado(PedidoContato pedidoContato) {
		this();
		if (pedidoContato == null) {
			throw new BusinessException("erro.interno");
		}
		this.pedidoContato = pedidoContato;
		this.doador = pedidoContato.getDoador();
	}

	/**
	 * Identificador da entidade.
	 * 
	 * @return ID da entidade.
	 */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Data de realização da tentativa de contato.
	 * 
	 * @return data de criação da tentativa.
	 */
	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public boolean isAberto() {
		return aberto;
	}

	public void setAberto(boolean aberto) {
		this.aberto = aberto;
	}

	/**
	 * @return the pedidoContato
	 */
	public PedidoContato getPedidoContato() {
		return pedidoContato;
	}

	/**
	 * @param pedidoContato the pedidoContato to set
	 */
	public void setPedidoContato(PedidoContato pedidoContato) {
		this.pedidoContato = pedidoContato;
	}
	
	/**
	 * @return the dataFinalizacao
	 */
	public LocalDateTime getDataFinalizacao() {
		return dataFinalizacao;
	}

	/**
	 * @param dataFinalizacao the dataFinalizacao to set
	 */
	public void setDataFinalizacao(LocalDateTime dataFinalizacao) {
		this.dataFinalizacao = dataFinalizacao;
	}

	/**
	 * @return the doador
	 */
	public Doador getDoador() {
		return doador;
	}

	/**
	 * @param doador the doador to set
	 */
	public void setDoador(Doador doador) {
		this.doador = doador;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ( aberto ? 1231 : 1237 );
		result = prime * result + ( ( dataCriacao == null ) ? 0 : dataCriacao.hashCode() );
		result = prime * result + ( ( id == null ) ? 0 : id.hashCode() );
		
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
		if (!( obj instanceof DoadorNaoContactado )) {
			return false;
		}
		DoadorNaoContactado other = (DoadorNaoContactado) obj;
		if (aberto != other.aberto) {
			return false;
		}
		if (dataCriacao == null) {
			if (other.dataCriacao != null) {
				return false;
			}
		}
		else
			if (!dataCriacao.equals(other.dataCriacao)) {
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
		if (dataFinalizacao == null) {
			if (other.dataFinalizacao != null) {
				return false;
			}
		}
		else
			if (!dataFinalizacao.equals(other.dataFinalizacao)) {
				return false;
			}
		
		if (doador == null) {
			if (other.doador != null) {
				return false;
			}
		}
		else
			if (!doador.equals(other.doador)) {
				return false;
			}
		if (pedidoContato == null) {
			if (other.pedidoContato != null) {
				return false;
			}
		}
		else
			if (!pedidoContato.equals(other.pedidoContato)) {
				return false;
			}
		return true;
	}

}
