package br.org.cancer.modred.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Classe de persistencia de Valor P.
 * 
 * @author Filipe Paes
 * 
 */
@Entity
@Table(name = "VALOR_P")
@NamedQuery(name = "ValorP.findAll", query = "SELECT v FROM ValorP v")
public class ValorP implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ValorPPK id;

	@Column(name = "VALP_NR_VALIDO")
	private Boolean valido;

	@Column(name = "VALP_TX_VERSAO")
	private String versao;

	@Lob
	@Column(name = "VALP_TX_GRUPO")
	private String grupo;

	/**
	 * Construtor que já recebe o Locus e o Valor P.
	 * 
	 * @param locus
	 *            - String do Locus
	 * @param valor
	 *            - Valor P
	 */
	public ValorP(String locus, String valor) {
		id = new ValorPPK();
		id.setLocus(new Locus(locus));
		id.setNomeGrupo(valor);
	}
	
	/**
	 * Construtor que já recebe o Locus e o Valor G.
	 * 
	 * @param locus
	 *            - String do Locus
	 * @param nomeGrupo
	 *            - Valor P
	 * @param valoresPossiveis valores concatenados possíveis para o valor G selecionado.
	 */
	public ValorP(String locus, String nomeGrupo, String valoresPossiveis) {
		id = new ValorPPK();
		id.setLocus(new Locus(locus));
		id.setNomeGrupo(nomeGrupo);
		grupo = valoresPossiveis;
	}

	public ValorP() {
	}

	/**
	 * @return the id
	 */
	public ValorPPK getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(ValorPPK id) {
		this.id = id;
	}

	/**
	 * @return the valido
	 */
	public Boolean getValido() {
		return valido;
	}

	/**
	 * @param valido
	 *            the valido to set
	 */
	public void setValido(Boolean valido) {
		this.valido = valido;
	}

	/**
	 * @return the versao
	 */
	public String getVersao() {
		return versao;
	}

	/**
	 * @param versao
	 *            the versao to set
	 */
	public void setVersao(String versao) {
		this.versao = versao;
	}

	/**
	 * @return the grupo
	 */
	public String getGrupo() {
		return grupo;
	}

	/**
	 * @param grupo
	 *            the grupo to set
	 */
	public void setGrupo(String grupo) {
		this.grupo = grupo;
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
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((versao == null) ? 0 : versao.hashCode());
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
		ValorP other = (ValorP) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		}
		else if (!id.equals(other.id)) {
			return false;
		}
		if (versao == null) {
			if (other.versao != null) {
				return false;
			}
		}
		else if (!versao.equals(other.versao)) {
			return false;
		}
		return true;
	}

}