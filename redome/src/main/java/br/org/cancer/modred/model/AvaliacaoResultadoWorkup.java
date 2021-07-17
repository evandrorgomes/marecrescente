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
import br.org.cancer.modred.controller.view.TarefaView;
import br.org.cancer.modred.controller.view.WorkupView;
import br.org.cancer.modred.model.security.Usuario;
import br.org.cancer.modred.service.impl.custom.EntityObservable;
import br.org.cancer.modred.service.impl.observers.AvaliacaoResultadoWorkupAprovadoObserver;
import br.org.cancer.modred.service.impl.observers.AvaliacaoResultadoWorkupRecusadoObserver;

/**
 * Classe de persistencia de avaliação de resultado de workup.
 * 
 * @author Fillipe Queiroz
 *
 */
@Entity
@Table(name = "AVALIACAO_RESULTADO_WORKUP")
@SequenceGenerator(name = "SQ_AVRW_ID", sequenceName = "SQ_AVRW_ID", allocationSize = 1)
public class AvaliacaoResultadoWorkup extends EntityObservable implements Serializable {

	private static final long serialVersionUID = 5170101253604507919L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_AVRW_ID")
	@Column(name = "AVRW_ID")
	@JsonView({ TarefaView.Listar.class, WorkupView.ResultadoParaAvaliacao.class,
		AvaliacaoResultadoWorkupView.ListarAvaliacao.class})
	private Long id;

	@Column(name = "AVRW_DT_CRIACAO")
	@NotNull
	private LocalDateTime dataCriacao;

	@Column(name = "AVRW_DT_ATUALIZACAO")
	private LocalDateTime dataAtualizacao;

	@ManyToOne
	@JoinColumn(name = "USUA_ID_RESPONSAVEL")
	private Usuario usuarioResponsavel;

	@Column(name = "AVRW_IN_PROSSEGUIR")
	private Boolean solicitaColeta;

	@Column(name = "AVRW_TX_JUSTIFICATIVA")
	@JsonView({ BuscaView.AvaliacaoWorkupDoador.class})
	private String justificativa;

	@OneToOne
	@JoinColumn(name = "REWO_ID")
	@JsonView({ TarefaView.Listar.class, WorkupView.ResultadoParaAvaliacao.class,
				AvaliacaoResultadoWorkupView.ListarAvaliacao.class})
	private ResultadoWorkup resultadoWorkup;

//	@OneToMany(mappedBy = "avaliacaoResultadoWorkup", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	private List<PedidoAdicionalWorkup> pedidosAdicionais;

	public AvaliacaoResultadoWorkup() {
		super();
		addObserver(AvaliacaoResultadoWorkupAprovadoObserver.class);
		addObserver(AvaliacaoResultadoWorkupRecusadoObserver.class);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public LocalDateTime getDataAtualizacao() {
		return dataAtualizacao;
	}

	public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	public Usuario getUsuarioResponsavel() {
		return usuarioResponsavel;
	}

	public void setUsuarioResponsavel(Usuario usuarioResponsavel) {
		this.usuarioResponsavel = usuarioResponsavel;
	}

	public Boolean getSolicitaColeta() {
		return solicitaColeta;
	}

	public void setSolicitaColeta(Boolean solicitaColeta) {
		this.solicitaColeta = solicitaColeta;
	}

	public String getJustificativa() {
		return justificativa;
	}

	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}

	public ResultadoWorkup getResultadoWorkup() {
		return resultadoWorkup;
	}

	public void setResultadoWorkup(ResultadoWorkup resultadoWorkup) {
		this.resultadoWorkup = resultadoWorkup;
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
		result = prime * result + ( ( dataAtualizacao == null ) ? 0 : dataAtualizacao.hashCode() );
		result = prime * result + ( ( dataCriacao == null ) ? 0 : dataCriacao.hashCode() );
		result = prime * result + ( ( id == null ) ? 0 : id.hashCode() );
		result = prime * result + ( ( justificativa == null ) ? 0 : justificativa.hashCode() );
		result = prime * result + ( ( resultadoWorkup == null ) ? 0 : resultadoWorkup.hashCode() );
		result = prime * result + ( ( solicitaColeta == null ) ? 0 : solicitaColeta.hashCode() );
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
		AvaliacaoResultadoWorkup other = (AvaliacaoResultadoWorkup) obj;
		if (dataAtualizacao == null) {
			if (other.dataAtualizacao != null) {
				return false;
			}
		}
		else
			if (!dataAtualizacao.equals(other.dataAtualizacao)) {
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
		if (justificativa == null) {
			if (other.justificativa != null) {
				return false;
			}
		}
		else
			if (!justificativa.equals(other.justificativa)) {
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
		if (solicitaColeta == null) {
			if (other.solicitaColeta != null) {
				return false;
			}
		}
		else
			if (!solicitaColeta.equals(other.solicitaColeta)) {
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

//	/**
//	 * @return the pedidosAdicionais
//	 */
//	public List<PedidoAdicionalWorkup> getPedidosAdicionais() {
//		return pedidosAdicionais;
//	}
//
//	/**
//	 * @param pedidosAdicionais the pedidosAdicionais to set
//	 */
//	public void setPedidosAdicionais(List<PedidoAdicionalWorkup> pedidosAdicionais) {
//		this.pedidosAdicionais = pedidosAdicionais;
//	}

}