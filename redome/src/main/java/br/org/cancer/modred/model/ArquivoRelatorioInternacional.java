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

import br.org.cancer.modred.controller.view.BuscaView;
import br.org.cancer.modred.service.impl.custom.EntityObservable;
import br.org.cancer.modred.service.impl.observers.UploadBuscaInternacionalObserver;

/**
 * Classe de armazenamento de arquivos de relatório internacional ligados a
 * busca.
 * 
 * @author Filipe Paes
 *
 */
@Entity
@SequenceGenerator(name = "SQ_ARRI_ID", sequenceName = "SQ_ARRI_ID", allocationSize = 1)
@Table(name = "ARQUIVO_RELAT_INTERNACIONAL")
public class ArquivoRelatorioInternacional extends EntityObservable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3752307362469201624L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_ARRI_ID")
	@Column(name = "ARRI_ID")
	@JsonView(BuscaView.ListarArquivosRelatorioBuscaInternacional.class)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "BUSC_ID")
	private Busca busca;

	@Column(name = "ARRI_TX_DESCRICAO_ARQUIVO")
	@JsonView(BuscaView.ListarArquivosRelatorioBuscaInternacional.class)
	private String descricao;
	
	@Column(name = "ARRI_TX_CAMINHO_ARQUIVO")
	@JsonView(BuscaView.ListarArquivosRelatorioBuscaInternacional.class)
	private String caminho;
	
	
	/**
	 * Construtor para definição dos observers envolvendo
	 * esta entidade.
	 */
	public ArquivoRelatorioInternacional() {
		super();
		addObserver(UploadBuscaInternacionalObserver.class);
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the busca
	 */
	public Busca getBusca() {
		return busca;
	}

	/**
	 * @param busca
	 *            the busca to set
	 */
	public void setBusca(Busca busca) {
		this.busca = busca;
	}

	/**
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * @param descricao
	 *            the descricao to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * @return the caminho
	 */
	public String getCaminho() {
		return caminho;
	}

	/**
	 * @param caminho
	 *            the caminho to set
	 */
	public void setCaminho(String caminho) {
		this.caminho = caminho;
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
		result = prime * result + ((busca == null) ? 0 : busca.hashCode());
		result = prime * result + ((caminho == null) ? 0 : caminho.hashCode());
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
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
		ArquivoRelatorioInternacional other = (ArquivoRelatorioInternacional) obj;
		if (busca == null) {
			if (other.busca != null) {
				return false;
			}
		} 
		else if (!busca.equals(other.busca)) {
			return false;
		}
		if (caminho == null) {
			if (other.caminho != null) {
				return false;
			}
		} 
		else if (!caminho.equals(other.caminho)) {
			return false;
		}
		if (descricao == null) {
			if (other.descricao != null) {
				return false;
			}
		} 
		else if (!descricao.equals(other.descricao)) {
			return false;
		}
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
