package br.org.cancer.modred.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonView;

import org.hibernate.validator.constraints.NotEmpty;

import br.org.cancer.modred.controller.view.AvaliacaoPedidoColetaView;
import br.org.cancer.modred.controller.view.AvaliacaoResultadoWorkupView;
import br.org.cancer.modred.controller.view.BuscaView;
import br.org.cancer.modred.controller.view.PedidoWorkupView;
import br.org.cancer.modred.controller.view.TarefaView;
import br.org.cancer.modred.controller.view.WorkupView;
import br.org.cancer.modred.model.security.Usuario;

/**
 * Classe de armazenamento de resultado de workup. Nesta classe estarão representados os dados de resultados de exames de workup
 * imputados pelo centro de transplante. Os dados principais do resultado são os arquivos que serão enviados para o servidor.
 * 
 * @author Filipe Paes
 * 
 */
@Entity
@SequenceGenerator(name = "SQ_REWO_ID", sequenceName = "SQ_REWO_ID", allocationSize = 1)
@Table(name = "RESULTADO_WORKUP")
public class ResultadoWorkup implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_REWO_ID")
	@Column(name="REWO_ID")
	@JsonView(value = { WorkupView.Resultado.class, TarefaView.Listar.class, WorkupView.ResultadoParaAvaliacao.class,
			AvaliacaoPedidoColetaView.Detalhe.class, AvaliacaoResultadoWorkupView.ListarAvaliacao.class,
			PedidoWorkupView.ResultadoParaCadastroInternacional.class, PedidoWorkupView.CadastrarResultado.class })
	private Long id;

	@Column(name="PEWO_ID")
	private Long pedidoWorkup;

	@Transient
	private LocalDateTime dataAtualizacao;

	@Column(name = "REWO_DT_CRIACAO")
	@JsonView(value = { WorkupView.Resultado.class, TarefaView.Listar.class })
	@NotNull
	private LocalDateTime dataCriacao;

	@Transient
	private Boolean coletaInviavel;

	@Transient
	private String motivoInviabilidade;

	@ManyToOne
	@JoinColumn(name = "USUA_ID")
	@JsonView(value = { WorkupView.Resultado.class })
	@NotNull
	private Usuario usuario;

	@Transient
	private AvaliacaoResultadoWorkup avaliacaoResultadoWorkup;

	@NotEmpty
	@NotNull
	@OneToMany(mappedBy="resultadoWorkup", cascade=CascadeType.ALL)
	@JsonView(value={WorkupView.ResultadoParaAvaliacao.class, AvaliacaoPedidoColetaView.Detalhe.class, BuscaView.AvaliacaoWorkupDoador.class})
	private List<ArquivoResultadoWorkup> arquivosResultadoWorkup;

	public ResultadoWorkup() {}

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
	 * @return the pedidoWorkup
	 */
	public Long getPedidoWorkup() {
		return pedidoWorkup;
	}

	/**
	 * @param pedidoWorkup the pedidoWorkup to set
	 */
	public void setPedidoWorkup(Long pedidoWorkup) {
		this.pedidoWorkup = pedidoWorkup;
	}

	/**
	 * @return the dataAtualizacao
	 */
	public LocalDateTime getDataAtualizacao() {
		return dataAtualizacao;
	}

	/**
	 * @param dataAtualizacao the dataAtualizacao to set
	 */
	public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
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
	 * @return the coletaInviavel
	 */
	public Boolean getColetaInviavel() {
		return coletaInviavel;
	}

	/**
	 * @param coletaInviavel the coletaInviavel to set
	 */
	public void setColetaInviavel(Boolean coletaInviavel) {
		this.coletaInviavel = coletaInviavel;
	}

	/**
	 * @return the motivoInviabilidade
	 */
	public String getMotivoInviabilidade() {
		return motivoInviabilidade;
	}

	/**
	 * @param motivoInviabilidade the motivoInviabilidade to set
	 */
	public void setMotivoInviabilidade(String motivoInviabilidade) {
		this.motivoInviabilidade = motivoInviabilidade;
	}

	/**
	 * @return the usuario
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the arquivosResultadoWorkup
	 */
	public List<ArquivoResultadoWorkup> getArquivosResultadoWorkup() {
		return arquivosResultadoWorkup;
	}

	/**
	 * @param arquivosResultadoWorkup the arquivosResultadoWorkup to set
	 */
	public void setArquivosResultadoWorkup(List<ArquivoResultadoWorkup> arquivosResultadoWorkup) {
		this.arquivosResultadoWorkup = arquivosResultadoWorkup;
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
		result = prime * result + ( ( pedidoWorkup == null ) ? 0 : pedidoWorkup.hashCode() );
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
		ResultadoWorkup other = (ResultadoWorkup) obj;
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
		if (pedidoWorkup == null) {
			if (other.pedidoWorkup != null) {
				return false;
			}
		}
		else
			if (!pedidoWorkup.equals(other.pedidoWorkup)) {
				return false;
			}
		return true;
	}

	/**
	 * @return the avaliacaoResultadoWorkup
	 */
	public AvaliacaoResultadoWorkup getAvaliacaoResultadoWorkup() {
		return avaliacaoResultadoWorkup;
	}

	/**
	 * @param avaliacaoResultadoWorkup the avaliacaoResultadoWorkup to set
	 */
	public void setAvaliacaoResultadoWorkup(AvaliacaoResultadoWorkup avaliacaoResultadoWorkup) {
		this.avaliacaoResultadoWorkup = avaliacaoResultadoWorkup;
	}

}