package br.org.cancer.modred.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.org.cancer.modred.helper.IConfiguracaoProcessServer;
import br.org.cancer.modred.model.domain.TiposTarefa;

/**
 * Classe que representa o tipo de serviço.
 * 
 * @author bruno.sousa
 */
@Entity
@Table(name = "TIPO_SERVICO")
public class TipoServico implements Serializable {

	@Id
	@Column(name = "TISE_ID")
	private Long id;

	@Column(name = "TISE_TX_DESCRICAO")
	private String descricao;


	/**
	 * Construtor padrão.
	 */
	public TipoServico() {}

	/**
	 * Construtor passsando o id.
	 * 
	 * @param id
	 */
	public TipoServico(Long id) {
		super();
		this.id = id;
	}

	/**
	 * Retorna a chave primaria do tipo de serviço.
	 * 
	 * @return id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Seta a chave primaria do tipo de serviço.
	 * 
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Retorna a descricao do tipo de serviço.
	 * 
	 * @return descricao
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * Seta a descricao do tipo de serviço.
	 * 
	 * @param descricao
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}


	/**
	 * @return configuração para execução e tratamento desse tipo de serviços.
	 */
	public IConfiguracaoProcessServer getConfiguracao() {
		TiposTarefa tipo = TiposTarefa.valueOf(this.getId());

		if (tipo != null) {
			return tipo.getConfiguracao();
		}

		return null;
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
		if (!( obj instanceof TipoServico )) {
			return false;
		}
		TipoServico other = (TipoServico) obj;
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