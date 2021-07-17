package br.org.cancer.modred.model;

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


/**
 * Registro de pedidos de envio de dados de paciente para emdis. Nesta haverá
 * todos os registros de criação, envio e recebimento dos dados do emdis.
 * @author Filipe Paes
 *
 */
@Entity
@SequenceGenerator(name = "SQ_PEEE_ID", sequenceName = "SQ_PEEE_ID", allocationSize = 1)
@Table(name = "PEDIDO_ENVIO_EMDIS")
public class PedidoEnvioEmdis {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_PEEE_ID")
	@Column(name = "PEEE_ID")
	private Long id;
	
	/**
	 * Data de criação do pedido.
	 */
	@Column(name = "PEEE_DT_CRIACAO")
	private LocalDateTime dataCriacao;
	
	/**
	 * Data de envio retornado pela fila emdis.
	 */
	@Column(name = "PEEE_DT_ENVIO_EMDIS")
	private LocalDateTime dataEnvioEmdis;
	
	/**
	 * Nome do arquivo de envio retornado pelo emdis.
	 */
	@Column(name = "PEEE_TX_NM_ARQUIVO_ENVIO")
	private String nomeArquivoEnvioEmdis;
	
	/**
	 * Data de retorno do emdis.
	 */
	@Column(name = "PEEE_DT_RETORNO_EMDIS")
	private LocalDateTime dataRetornoEmdis;
	
	
	/**
	 * Nome do arquivo de retorno emdis.
	 */
	@Column(name = "PEEE_TX_NM_ARQUIVO_RETORNO")
	private String nomeArquivoRetornoEmdis;
	
	/**
	 * Referencia de solicitação do tipo ENVIO_PACIENTE_EMDIS.
	 */
	@ManyToOne
	@JoinColumn(name = "SOLI_ID")
	private Solicitacao solicitacao;

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
	 * @return the dataEnvioEmdis
	 */
	public LocalDateTime getDataEnvioEmdis() {
		return dataEnvioEmdis;
	}

	/**
	 * @param dataEnvioEmdis the dataEnvioEmdis to set
	 */
	public void setDataEnvioEmdis(LocalDateTime dataEnvioEmdis) {
		this.dataEnvioEmdis = dataEnvioEmdis;
	}

	/**
	 * @return the nomeArquivoEnvioEmdis
	 */
	public String getNomeArquivoEnvioEmdis() {
		return nomeArquivoEnvioEmdis;
	}

	/**
	 * @param nomeArquivoEnvioEmdis the nomeArquivoEnvioEmdis to set
	 */
	public void setNomeArquivoEnvioEmdis(String nomeArquivoEnvioEmdis) {
		this.nomeArquivoEnvioEmdis = nomeArquivoEnvioEmdis;
	}

	/**
	 * @return the dataRetornoEmdis
	 */
	public LocalDateTime getDataRetornoEmdis() {
		return dataRetornoEmdis;
	}

	/**
	 * @param dataRetornoEmdis the dataRetornoEmdis to set
	 */
	public void setDataRetornoEmdis(LocalDateTime dataRetornoEmdis) {
		this.dataRetornoEmdis = dataRetornoEmdis;
	}

	/**
	 * @return the nomeArquivoRetornoEmdis
	 */
	public String getNomeArquivoRetornoEmdis() {
		return nomeArquivoRetornoEmdis;
	}

	/**
	 * @param nomeArquivoRetornoEmdis the nomeArquivoRetornoEmdis to set
	 */
	public void setNomeArquivoRetornoEmdis(String nomeArquivoRetornoEmdis) {
		this.nomeArquivoRetornoEmdis = nomeArquivoRetornoEmdis;
	}

	/**
	 * @return the solicitacao
	 */
	public Solicitacao getSolicitacao() {
		return solicitacao;
	}

	/**
	 * @param solicitacao the solicitacao to set
	 */
	public void setSolicitacao(Solicitacao solicitacao) {
		this.solicitacao = solicitacao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataCriacao == null) ? 0 : dataCriacao.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((solicitacao == null) ? 0 : solicitacao.hashCode());
		return result;
	}

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
		PedidoEnvioEmdis other = (PedidoEnvioEmdis) obj;
		if (dataCriacao == null) {
			if (other.dataCriacao != null) {
				return false;
			}
		} 
		else if (!dataCriacao.equals(other.dataCriacao)) {
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
		if (solicitacao == null) {
			if (other.solicitacao != null) {
				return false;
			}
		} 
		else if (!solicitacao.equals(other.solicitacao)) {
			return false;
		}
		return true;
	}
	 
}
