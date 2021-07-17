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

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.TransportadoraView;


/**
 * Classe de arquivos de pedido de transporte. Estes arquivos são os que acompanham o currier
 * nas viajens ao qual eles transportam as medulas.
 * @author Filipe Paes
 * 
 */
@Entity
@SequenceGenerator(name = "SQ_ARPT_ID", sequenceName = "SQ_ARPT_ID", allocationSize = 1)
@Table(name="ARQUIVO_PEDIDO_TRANSPORTE")
public class ArquivoPedidoTransporte implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ARPT_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_ARPT_ID")
	@JsonView(value = { TransportadoraView.AgendarTransporte.class })
	private Long id;

	@Column(name="ARPT_TX_CAMINHO")
	@JsonView(value = { TransportadoraView.AgendarTransporte.class })
	private String caminho;

	@ManyToOne
	@JoinColumn(name="PETR_ID")
	private PedidoTransporte pedidoTransporte;
	
	@Column(name="ARPT_TX_DESCRICAO_ALTERACAO")
	@JsonView(value = { TransportadoraView.AgendarTransporte.class })
	private String descricaoAlteracao;

	
	public ArquivoPedidoTransporte() {
	}

	/**
	 * @param pedidoTransporte
	 */
	public ArquivoPedidoTransporte(PedidoTransporte pedidoTransporte) {
		this.pedidoTransporte = pedidoTransporte;
	}

	/**
	 * Obtém a id do arquivo de pedido de transporte no banco.
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Obtém o caminho do arquivo no storage.
	 * @return the caminho
	 */
	public String getCaminho() {
		return caminho;
	}

	/**
	 * @param caminho the caminho to set
	 */
	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}

	/**
	 * Obtém a referencia do pedido de transporte.
	 * @return the pedidoTransporte
	 */
	public PedidoTransporte getPedidoTransporte() {
		return pedidoTransporte;
	}

	/**
	 * @param pedidoTransporte the pedidoTransporte to set
	 */
	public void setPedidoTransporte(PedidoTransporte pedidoTransporte) {
		this.pedidoTransporte = pedidoTransporte;
	}
	

	
	
	/**
	 * @return the descricaoAlteracao
	 */
	public String getDescricaoAlteracao() {
		return descricaoAlteracao;
	}

	/**
	 * @param descricaoAlteracao the descricaoAlteracao to set
	 */
	public void setDescricaoAlteracao(String descricaoAlteracao) {
		this.descricaoAlteracao = descricaoAlteracao;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		if (getClass() != obj.getClass()) {
			return false;
		}
		ArquivoPedidoTransporte other = (ArquivoPedidoTransporte) obj;
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

	/**
	 * Método para obter somente o nome do arquivo de exame.
	 * 
	 * @return nome nome
	 */
	public String obterNomeArquivo() {
		String[] partes = this.getCaminho().split("/");
		if (partes.length > 1) {
			return partes[partes.length - 1];
		}
		return null;
	}

	/**
	 * Método para obter somente o diretorio de exame.
	 * 
	 * @return dir dir
	 */
	public String obterDiretorioArquivo() {
		String[] result = this.getCaminho().split("/");
		if (result.length > 1) {
			return result[0];
		}
		else {
			return null;
		}
	}
	
	

}