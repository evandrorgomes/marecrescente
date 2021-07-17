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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.AvaliacaoCamaraTecnicaView;


/**
 * Caso o paciente seja cadastrado com uma cid que não seja reconhecida em portaria
 * ou caso o mesmo tenha uma idade fora do range de atendimento para a sua CID será
 * necessário gerar uma avaliação por um grupo chamado camara técnica. Os dados desta
 * avaliação são representados nesta classe.
 * @author Fiipe Paes 
 * 
 */
@Entity
@Table(name="AVALIACAO_CAMARA_TECNICA")
@SequenceGenerator(name = "SQ_AVCT_ID", sequenceName = "SQ_AVCT_ID", allocationSize = 1)
public class AvaliacaoCamaraTecnica implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_AVCT_ID")
	@Column(name="AVCT_ID")
	@JsonView({AvaliacaoCamaraTecnicaView.ListarTarefas.class,AvaliacaoCamaraTecnicaView.Detalhe.class})
	private Long id;

	@Column(name="AVCT_TX_ARQUIVO_RELATORIO")
	@JsonView({AvaliacaoCamaraTecnicaView.Detalhe.class})
	private String caminhoArquivoRelatorio;

	@Column(name="AVCT_TX_JUSTIFICATIVA")
	@JsonView({AvaliacaoCamaraTecnicaView.Detalhe.class})
	private String justificativa;

	@ManyToOne
	@JoinColumn(name="PACI_NR_RMR")
	@JsonView({AvaliacaoCamaraTecnicaView.ListarTarefas.class,AvaliacaoCamaraTecnicaView.Detalhe.class})
	@NotNull
	private Paciente paciente;

	@ManyToOne
	@JoinColumn(name="STCT_ID")
	@JsonView({AvaliacaoCamaraTecnicaView.ListarTarefas.class,AvaliacaoCamaraTecnicaView.Detalhe.class})
	@NotNull
	private StatusAvaliacaoCamaraTecnica status;
	
	@Column(name = "AVCT_DT_ATUALIZACAO")
	@NotNull
	private LocalDateTime dataAtualizacao;
	
	public AvaliacaoCamaraTecnica() {
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
	 * @return the caminhoArquivoRelatorio
	 */
	public String getCaminhoArquivoRelatorio() {
		return caminhoArquivoRelatorio;
	}

	/**
	 * @param caminhoArquivoRelatorio the caminhoArquivoRelatorio to set
	 */
	public void setCaminhoArquivoRelatorio(String caminhoArquivoRelatorio) {
		this.caminhoArquivoRelatorio = caminhoArquivoRelatorio;
	}

	/**
	 * @return the justificativa
	 */
	public String getJustificativa() {
		return justificativa;
	}

	/**
	 * @param justificativa the justificativa to set
	 */
	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}

	

	/**
	 * @return the paciente
	 */
	public Paciente getPaciente() {
		return paciente;
	}


	/**
	 * @param paciente the paciente to set
	 */
	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}


	/**
	 * @return the status
	 */
	public StatusAvaliacaoCamaraTecnica getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(StatusAvaliacaoCamaraTecnica status) {
		this.status = status;
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


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((paciente == null) ? 0 : paciente.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		AvaliacaoCamaraTecnica other = (AvaliacaoCamaraTecnica) obj;
		if (paciente == null) {
			if (other.paciente != null) {
				return false;
			}
		} 
		else if (!paciente.equals(other.paciente)) {
			return false;
		}
		if (status == null) {
			if (other.status != null) {
				return false;
			}
		} 
		else if (!status.equals(other.status)) {
			return false;
		}
		return true;
	}

	

}