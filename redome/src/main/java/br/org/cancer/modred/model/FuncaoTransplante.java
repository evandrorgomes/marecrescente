package br.org.cancer.modred.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.CentroTransplanteView;

/**
 * Classe de persistencia de FUNCAO_TRANSPLANTE.
 * 
 * @author bruno.sousa
 *
 */
@Entity
@Table(name = "FUNCAO_TRANSPLANTE")
public class FuncaoTransplante implements Serializable {
	private static final long serialVersionUID = -244033354277632412L;

	/**
	 * Papel de avaliador.
	 */
	public static final Long PAPEL_AVALIADOR = 1L;

	/**
	 * Papel de centro de coleta.
	 */
	public static final Long PAPEL_COLETOR = 2L;

	/**
	 * Papel de transplantador.
	 */
	public static final Long PAPEL_TRANSPLANTADOR = 3L; 
	/**
	 * Chave que identifica com exclusividade uma instância desta classe.
	 */
	@Id
	@Column(name = "FUTR_ID")
	@JsonView(CentroTransplanteView.Detalhe.class)
	private Long id;

	/**
	 * Descrição do papel.
	 */
	@Column(name = "FUTR_DESCRICAO")
	@JsonView(CentroTransplanteView.Detalhe.class)
	private String descricao;

	/**
	 * Chave primária do papel.
	 * 
	 * @return
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Chave primária do papel.
	 * 
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Descrição do papel.
	 * 
	 * @return
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * Descrição do papel.
	 * 
	 * @param descricao
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
		if (!( obj instanceof FuncaoTransplante )) {
			return false;
		}
		FuncaoTransplante other = (FuncaoTransplante) obj;
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
		return true;
	}

}