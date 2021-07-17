package br.org.cancer.redome.workup.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * Classe que representa uma configuração.
 * 
 * @author Rafael Pizão
 *
 */
@Entity
public class Configuracao implements Serializable {

	private static final long serialVersionUID = 3641665513483748002L;
	
	/**
	 *Extensão de arquivo válida para arquivo de resultado de workup. 
	 */
	public static final String EXTENSAO_ARQUIVO_RESULTADO_WORKUP = "extensaoArquivoResultadoWorkup";
	
	/**
	 * Tamanho do arquivo de resultado de workup.
	 */
	public static final String TAMANHO_ARQUIVO_RESULTADO_WORKUP = "tamanhoArquivoResultadoWorkupEmByte";
	
	/**
	 * Quantidade máxima de arquivos de resultado de workup.
	 */
	public static final String QUANTIDADE_MAXIMA_ARQUIVOS_RESULTADO_WORKUP = "quantidadeMaximaArquivosResultadoWorkup";
	
	/**
	 * Variável estática representando o tamanho máximo do arquivo de voucher do pedido de logistica.
	 */
	public static final String TAMANHO_MAXIMO_VOUCHER = "tamanhoArquivoPedidoLogisticaEmByte";
	
	/**
	 * Variável estática representando a extensão do arquivo de voucher do pedido de logistica.
	 */
	public static final String EXTENSAO_ARQUIVO_VOUCHER = "extensaoArquivoPedidoLogistica";
	
	/**
	 * Variável estática representando a quantidade máxima de arquivos de prescricao.
	 */
	public static final String QUANTIDADE_ARQUIVO_PRESCRICAO = "quantidadeMaximaArquivosPrescricao";
	
	/**
	 * Variável estática representando o tamanho máximo do arquivo de prescrição.
	 */
	public static final String TAMANHO_MAXIMO_PRESCRICAO = "tamanhoArquivoPrescricaoEmByte";
	
	/**
	 * Variável estática representando a extensão do arquivo da prescrição.
	 */
	public static final String EXTENSAO_ARQUIVO_PRESCRICAO = "extensaoArquivoPrescricao";
	
	
	/**
	 * Variável estática representando o tamanho máximo do arquivo de autorizacao do paciente.
	 */
	public static final String TAMANHO_MAXIMO_AUTORIZACAO_PACIENTE = "tamanhoArquivoAutorizacaoPacienteEmByte";
		
	/**
	 * Variável estática representando a extensão do arquivo da autorizacao do paciente.
	 */
	public static final String EXTENSAO_ARQUIVO_AUTORIZACAO_PACIENTE = "extensaoArquivoAutorizacaoPaciente";
	
	/**
	 * Variável estática representando o tamanho máximo do arquivo do pedido de workup.
	 */
	public static final String TAMANHO_MAXIMO_PEDIDO_WORKUP = "tamanhoArquivoPedidoWorkupEmByte";
	
	/**
	 * Variável estática representando a extensão do arquivo do pedido de workup.
	 */
	public static final String EXTENSAO_ARQUIVO_PEDIDO_WORKUP = "extensaoArquivoPedidoWorkup";

	
	
	/**
	 * Variável estática representando o tamanho máximo do arquivo de laudo.
	 */
	public static final String TAMANHO_MAXIMO_ARQUIVO_LAUDO = "tamanhoArquivoLaudoEmByte";
	
	/**
	 * Variável estática representando a extensão do arquivo de laudo.
	 */
	public static final String EXTENSAO_ARQUIVO_LAUDO = "extensaoArquivoLaudo";

	/**
	 * Variável estática representando a quantidade máxima de arquivos de laudo.
	 */
	public static final String QUANTIDADE_ARQUIVO_LAUDO = "qtdArquivosLaudo";

	
	@Id
	@Column(name = "CONF_ID")
	private String chave;

	@NotNull
	@Column(name = "CONF_TX_VALOR")
	private String valor;

	/**
	 * Construtor padrão.
	 */
	public Configuracao() {}

	/**
	 * @return the chave
	 */
	public String getChave() {
		return chave;
	}

	/**
	 * @param chave the chave to set
	 */
	public void setChave(String chave) {
		this.chave = chave;
	}

	/**
	 * @return the valor
	 */
	public String getValor() {
		return valor;
	}

	/**
	 * @param valor the valor to set
	 */
	public void setValor(String valor) {
		this.valor = valor;
	}

	/**
	 * Implementação do método hashCode para a classe Configuração.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( chave == null ) ? 0 : chave.hashCode() );
		return result;
	}

	/**
	 * Implementação do método equals para a classe Configuração.
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
		Configuracao other = (Configuracao) obj;
		if (chave == null) {
			if (other.chave != null) {
				return false;
			}
		}
		else
			if (!chave.equals(other.chave)) {
				return false;
			}
		return true;
	}
}
