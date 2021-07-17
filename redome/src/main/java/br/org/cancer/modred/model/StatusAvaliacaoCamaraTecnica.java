package br.org.cancer.modred.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.AvaliacaoCamaraTecnicaView;

/**
 * Classe de status para avaliação de camara técnica.
 * @author Filipe Paes
 */
@Entity
@Table(name = "STATUS_AVALIACAO_CAMARA_TEC")
public class StatusAvaliacaoCamaraTecnica implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "STCT_ID")
	@JsonView({AvaliacaoCamaraTecnicaView.ListarTarefas.class,AvaliacaoCamaraTecnicaView.Detalhe.class})
	private Long id;

	@Column(name = "STCT_TX_DESCRICAO")
	@JsonView({AvaliacaoCamaraTecnicaView.ListarTarefas.class,AvaliacaoCamaraTecnicaView.Detalhe.class})
	private String descricao;
	
	public static final Long AGUARDANDO_AVALIACAO = 1L;
	public static final Long APROVADO = 2L;
	public static final Long REPROVADO = 3L;
	public static final Long AGUARDANDO_COORD_REDOME = 4L;
	public static final Long AGUARDANDO_CAMARA_TECNICA = 5L;


	public StatusAvaliacaoCamaraTecnica() {
	}

	public StatusAvaliacaoCamaraTecnica(Long idStatus) {
		this.id = idStatus;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
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
		StatusAvaliacaoCamaraTecnica other = (StatusAvaliacaoCamaraTecnica) obj;
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