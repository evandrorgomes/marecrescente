package br.org.cancer.modred.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.AvaliacaoResultadoWorkupView;
import br.org.cancer.modred.controller.view.BuscaView;
import br.org.cancer.modred.model.security.Usuario;

/**
 * Classe bean para persistencia de avaliação de workup do doador.
 * 
 * @author Fillipe Queiroz
 */
@Entity
@Table(name = "AVALIACAO_WORKUP_DOADOR")
@SequenceGenerator(name = "SQ_AVWD_ID", sequenceName = "SQ_AVWD_ID", allocationSize = 1)
public class AvaliacaoWorkupDoador implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_AVWD_ID")
	@JsonView(value = { BuscaView.AvaliacaoWorkupDoador.class })
	@Column(name = "AVWD_ID")
	private Long id;

	@OneToOne
	@JoinColumn(name = "REWO_ID")
	@NotNull
	@JsonView(value = { BuscaView.AvaliacaoWorkupDoador.class,  AvaliacaoResultadoWorkupView.ListarAvaliacao.class})
	private ResultadoWorkup resultadoWorkup;

	@Column(name = "AVWD_DT_CRIACAO")
	@NotNull
	private LocalDateTime dataCriacao;

	@Column(name = "AVWD_DT_CONCLUSAO")
	private LocalDateTime dataConclusao;

	@ManyToOne
	@JoinColumn(name = "USUA_ID_RESPONSAVEL")
	private Usuario usuarioResponsavel;

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
	 * @return the resultadoWorkup
	 */
	public ResultadoWorkup getResultadoWorkup() {
		return resultadoWorkup;
	}

	/**
	 * @param resultadoWorkup the resultadoWorkup to set
	 */
	public void setResultadoWorkup(ResultadoWorkup resultadoWorkup) {
		this.resultadoWorkup = resultadoWorkup;
	}

	/**
	 * @return the dataCriacao
	 */
	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	/**
	 * @param dataCriacao the dataCriacao to set
	 */
	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	/**
	 * @return the dataConclusao
	 */
	public LocalDateTime getDataConclusao() {
		return dataConclusao;
	}

	/**
	 * @param dataConclusao the dataConclusao to set
	 */
	public void setDataConclusao(LocalDateTime dataConclusao) {
		this.dataConclusao = dataConclusao;
	}

	/**
	 * @return the usuarioResponsavel
	 */
	public Usuario getUsuarioResponsavel() {
		return usuarioResponsavel;
	}

	/**
	 * @param usuarioResponsavel the usuarioResponsavel to set
	 */
	public void setUsuarioResponsavel(Usuario usuarioResponsavel) {
		this.usuarioResponsavel = usuarioResponsavel;
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
		result = prime * result + ( ( dataConclusao == null ) ? 0 : dataConclusao.hashCode() );
		result = prime * result + ( ( dataCriacao == null ) ? 0 : dataCriacao.hashCode() );
		result = prime * result + ( ( id == null ) ? 0 : id.hashCode() );
		result = prime * result + ( ( resultadoWorkup == null ) ? 0 : resultadoWorkup.hashCode() );
		result = prime * result + ( ( usuarioResponsavel == null ) ? 0 : usuarioResponsavel.hashCode() );
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
		AvaliacaoWorkupDoador other = (AvaliacaoWorkupDoador) obj;
		if (dataConclusao == null) {
			if (other.dataConclusao != null) {
				return false;
			}
		}
		else
			if (!dataConclusao.equals(other.dataConclusao)) {
				return false;
			}
		if (dataCriacao == null) {
			if (other.dataCriacao != null) {
				return false;
			}
		}
		else
			if (!dataCriacao.equals(other.dataCriacao)) {
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
		if (resultadoWorkup == null) {
			if (other.resultadoWorkup != null) {
				return false;
			}
		}
		else
			if (!resultadoWorkup.equals(other.resultadoWorkup)) {
				return false;
			}
		if (usuarioResponsavel == null) {
			if (other.usuarioResponsavel != null) {
				return false;
			}
		}
		else
			if (!usuarioResponsavel.equals(other.usuarioResponsavel)) {
				return false;
			}
		return true;
	}

}