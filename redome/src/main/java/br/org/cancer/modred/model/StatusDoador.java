package br.org.cancer.modred.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.BuscaView;
import br.org.cancer.modred.controller.view.DoadorView;
import br.org.cancer.modred.controller.view.TarefaView;
import br.org.cancer.modred.controller.view.WorkupView;

/**
 * Classe de persistencia para Status Doador.
 * 
 * @author Fillipe Queiroz
 */
@Entity
@Table(name = "STATUS_DOADOR")
public class StatusDoador implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final Long ATIVO = 1L;
	public static final Long ATIVO_RESSALVA = 2L;
	public static final Long INATIVO_TEMPORARIO = 3L;
	public static final Long INATIVO_PERMANENTE = 4L;

	@Id
	@Column(name = "STDO_ID")
	@JsonView({DoadorView.IdentificacaoCompleta.class, DoadorView.IdentificacaoParcial.class, TarefaView.Consultar.class, 
		BuscaView.Enriquecimento.class, WorkupView.Resultado.class, DoadorView.ContatoPassivo.class})
	private Long id;

	@Column(name = "STDO_TX_DESCRICAO")
	@JsonView({DoadorView.IdentificacaoCompleta.class, DoadorView.IdentificacaoParcial.class, WorkupView.Resultado.class})
	private String descricao;

	@Column(name = "STDO_IN_TEMPO_OBRIGATORIO")
	@JsonView({DoadorView.ContatoPassivo.class})
	private Boolean tempoObrigatorio;

	@OneToMany(mappedBy = "statusDoador", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@Fetch(FetchMode.SUBSELECT)
	private List<MotivoStatusDoador> motivosStatus;

	public StatusDoador() {}

	public StatusDoador(Long idStatus) {
		this.id = idStatus;
	}

	/**
	 * Construtor do statusDoador.
	 * 
	 * @param id parametro long.
	 * @param descricao parametro string.
	 */
	public StatusDoador(Long id, String descricao) {
		super();
		this.id = id;
		this.descricao = descricao;
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
	 * Descricao do status.
	 * 
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * Descricao do status.
	 * 
	 * @param descricao the descricao to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * Diz se a coluna de tempo em um determinado status é obrigatória.
	 * 
	 * @return
	 */
	public Boolean getTempoObrigatorio() {
		return tempoObrigatorio;
	}

	/**
	 * Diz se a coluna de tempo em um determinado status é obrigatória.
	 * 
	 * @param tempoObrigatorio
	 */
	public void setTempoObrigatorio(Boolean tempoObrigatorio) {
		this.tempoObrigatorio = tempoObrigatorio;
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
		result = prime * result + ( ( motivosStatus == null ) ? 0 : motivosStatus.hashCode() );
		result = prime * result + ( ( tempoObrigatorio == null ) ? 0 : tempoObrigatorio.hashCode() );
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
		StatusDoador other = (StatusDoador) obj;
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
		if (motivosStatus == null) {
			if (other.motivosStatus != null) {
				return false;
			}
		}
		else
			if (!motivosStatus.equals(other.motivosStatus)) {
				return false;
			}
		if (tempoObrigatorio == null) {
			if (other.tempoObrigatorio != null) {
				return false;
			}
		}
		else
			if (!tempoObrigatorio.equals(other.tempoObrigatorio)) {
				return false;
			}
		return true;
	}
	
	public static boolean isAtivo(Long id){
		return ATIVO.equals(id) || ATIVO_RESSALVA.equals(id);
	}

}