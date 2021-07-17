package br.org.cancer.modred.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.AvaliacaoPrescricaoView;

/**
 * Tipos de amostras à serem solicitadas para um laboratório.
 * 
 * @author Filipe Paes
 *
 */
@Entity
@Table(name = "TIPO_AMOSTRA")
public class TipoAmostra {

	@Id
	@Column(name = "TIAM_ID")
	@JsonView({AvaliacaoPrescricaoView.Detalhe.class})
	private Long id;

	@Column(name = "TIAM_TX_DESCRICAO")
	@JsonView({AvaliacaoPrescricaoView.Detalhe.class})
	private String descricao;
	
	public TipoAmostra() {
		// TODO Auto-generated constructor stub
	}

	public TipoAmostra(Long id, String descricao) {
		super();
		this.id = id;
		this.descricao = descricao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		TipoAmostra other = (TipoAmostra) obj;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
