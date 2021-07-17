package br.org.cancer.modred.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.AvaliacaoPrescricaoView;
import br.org.cancer.modred.controller.view.PedidoColetaView;
import br.org.cancer.modred.controller.view.PedidoWorkupView;
import br.org.cancer.modred.controller.view.TarefaLogisticaView;
import br.org.cancer.modred.controller.view.TransportadoraView;

/**
 * Classe que representa os tipos de fonte de celula existentes.
 * 
 * @author Fillipe Queiroz
 *
 */
@Entity
@Table(name = "FONTE_CELULAS")
public class FonteCelula implements Serializable {

	private static final long serialVersionUID = -3749821347178713784L;

	@Id
	@Column(name = "FOCE_ID")
	@JsonView(value = { AvaliacaoPrescricaoView.Detalhe.class, PedidoWorkupView.DetalheWorkup.class, 
			PedidoColetaView.DetalheColeta.class })
	private Long id;

	@Column(name = "FOCE_TX_SIGLA")
	@JsonView(value = {PedidoWorkupView.DetalheWorkup.class, PedidoColetaView.DetalheColeta.class, 
			AvaliacaoPrescricaoView.Detalhe.class })
	private String sigla;

	@Column(name = "FOCE_TX_DESCRICAO")
	@JsonView(value = {PedidoWorkupView.DetalheWorkup.class, PedidoColetaView.DetalheColeta.class, 
			AvaliacaoPrescricaoView.Detalhe.class, TarefaLogisticaView.Listar.class,
			TransportadoraView.AgendarTransporte.class})
	private String descricao;
	
	public FonteCelula() {
		super();
	}

	public FonteCelula(Long id) {
		super();
		this.id = id;
	}

	/**
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
	 * @return the sigla
	 */
	public String getSigla() {
		return sigla;
	}

	/**
	 * @param sigla the sigla to set
	 */
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	/**
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * @param descricao the descricao to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
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
		result = prime * result + ( ( descricao == null ) ? 0 : descricao.hashCode() );
		result = prime * result + ( ( id == null ) ? 0 : id.hashCode() );
		result = prime * result + ( ( sigla == null ) ? 0 : sigla.hashCode() );
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
		FonteCelula other = (FonteCelula) obj;
		if (descricao == null) {
			if (other.descricao != null) {
				return false;
			}
		}
		else
			if (!descricao.equals(other.descricao)) {
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
		if (sigla == null) {
			if (other.sigla != null) {
				return false;
			}
		}
		else
			if (!sigla.equals(other.sigla)) {
				return false;
			}
		return true;
	}

}