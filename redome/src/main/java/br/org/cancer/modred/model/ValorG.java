package br.org.cancer.modred.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Classe de persistencia para Valor G.
 * 
 * @author Filipe Paes
 */
@Entity
@Table(name = "VALOR_G")
@NamedQuery(name = "ValorG.findAll", query = "SELECT v FROM ValorG v")
public class ValorG implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ValorGPK id;

	@Column(name = "VALG_NR_VALIDO")
	private Boolean valido;

	@Column(name = "VALG_TX_VERSAO")
	private String versao;

	@Lob
	@Column(name = "VALG_TX_GRUPO")
	private String grupo;
	

	public ValorG() {
	}

	/**
	 * Construtor que já recebe o Locus e o Valor G.
	 * 
	 * @param locus
	 *            - String do Locus
	 * @param valor
	 *            - Valor P
	 */
	public ValorG(String locus, String valor) {
		id = new ValorGPK();
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
	public ValorG(String locus, String nomeGrupo, String valoresPossiveis) {
		id = new ValorGPK();
		id.setLocus(new Locus(locus));
		id.setNomeGrupo(nomeGrupo);
		grupo = valoresPossiveis;
	}

	/**
	 * @return the id
	 */
	public ValorGPK getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(ValorGPK id) {
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
		ValorG other = (ValorG) obj;
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