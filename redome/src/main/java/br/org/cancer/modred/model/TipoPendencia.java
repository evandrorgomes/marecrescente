package br.org.cancer.modred.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.AvaliacaoView;

/**
 * Classe que representa o tipo de pendencia.
 * 
 * @author Fillipe Queiroz
 */
@Entity
@Table(name = "TIPO_PENDENCIA")
public class TipoPendencia implements Serializable {

	private static final long serialVersionUID = 1926285985170121365L;

	@Id
	@Column(name = "TIPE_ID")
	@JsonView({ AvaliacaoView.ListarPendencias.class })
	private Long id;

	@Column(name = "TIPE_TX_DESCRICAO")
	@JsonView({ AvaliacaoView.ListarPendencias.class })
	private String descricao;

	public TipoPendencia() {
		super();
	}

	public TipoPendencia(Long id) {
		this();
		this.id = id;
	}

	public TipoPendencia(Long id, String descricao) {
		this(id);
		this.descricao = descricao;
	}

	/**
	 * Retorna a chave primaria do tipo da pendencia.
	 * 
	 * @return id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Seta a chave primaria do tipo da pendencia.
	 * 
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Retorna a descricao do tipo de pendencia.
	 * 
	 * @return descricao
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * Seta a descricao do tipo de pendencia.
	 * 
	 * @param descricao
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( descricao == null ) ? 0 : descricao.hashCode() );
		result = prime * result + (int) ( id ^ ( id >>> 32 ) );
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
		if (getClass() != obj.getClass()) {
			return false;
		}
		TipoPendencia other = (TipoPendencia) obj;
		if (descricao == null) {
			if (other.descricao != null) {
				return false;
			}
		}
		else
			if (!descricao.equals(other.descricao)) {
				return false;
			}
		if (id != other.id) {
			return false;
		}
		return true;
	}

}