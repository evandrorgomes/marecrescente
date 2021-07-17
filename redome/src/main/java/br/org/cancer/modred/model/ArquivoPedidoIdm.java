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


/**
 * Classe de arquivos de pedido de IDM. 
 * 
 * @author bruno.sousa
 * 
 */
@Entity
@SequenceGenerator(name = "SQ_ARPI_ID", sequenceName = "SQ_ARPI_ID", allocationSize = 1)
@Table(name="ARQUIVO_PEDIDO_IDM")
public class ArquivoPedidoIdm implements Serializable {
	
	private static final long serialVersionUID = 3425164053142335193L;

	@Id
	@Column(name="ARPI_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_ARPI_ID")
	private Long id;

	@Column(name="ARPI_TX_CAMINHO")
	private String caminho;

	@ManyToOne
	@JoinColumn(name="PEID_ID")
	private PedidoIdm pedidoIdm;

	public ArquivoPedidoIdm() {
	}

	/**
	 * Obtém a id do arquivo de pedido de IDM no banco.
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
	 * @return the pedidoIdm
	 */
	public PedidoIdm getPedidoIdm() {
		return pedidoIdm;
	}

	
	/**
	 * @param pedidoIdm the pedidoIdm to set
	 */
	public void setPedidoIdm(PedidoIdm pedidoIdm) {
		this.pedidoIdm = pedidoIdm;
	}

	/**
	 * Método para obter somente o nome do arquivo de exame.
	 * 
	 * @return nome nome
	 */
	public String obterNomeArquivo() {
		String[] partes = this.getCaminho().split("/");
		if (partes.length > 1) {
			return partes[1];
		}
		else {
			return null;
		}
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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( caminho == null ) ? 0 : caminho.hashCode() );
		result = prime * result + ( ( id == null ) ? 0 : id.hashCode() );
		result = prime * result + ( ( pedidoIdm == null ) ? 0 : pedidoIdm.hashCode() );
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
		if (!( obj instanceof ArquivoPedidoIdm )) {
			return false;
		}
		ArquivoPedidoIdm other = (ArquivoPedidoIdm) obj;
		if (caminho == null) {
			if (other.caminho != null) {
				return false;
			}
		}
		else
			if (!caminho.equals(other.caminho)) {
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
		if (pedidoIdm == null) {
			if (other.pedidoIdm != null) {
				return false;
			}
		}
		else
			if (!pedidoIdm.equals(other.pedidoIdm)) {
				return false;
			}
		return true;
	}
	

}