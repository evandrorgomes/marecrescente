package br.org.cancer.modred.model;

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
	 * Variável estática representando o tamanho máximo do arquivo de laudo.
	 */
	public static final String TAMANHO_MAXIMO_LAUDO = "tamanhoArquivoLaudoEmByte";

	/**
	 * Variável estática representando a extensão do arquivo de laudo.
	 */
	public static final String EXTENSAO_ARQUIVO_LAUDO = "extensaoArquivoLaudo";

	/**
	 * Variável estática representando a quantidade máxima de arquivos de laudo.
	 */
	public static final String QUANTIDADE_ARQUIVO_LAUDO = "qtdArquivosLaudo";
	
	
	/**
	 * Variável estática representando a quantidade máxima de arquivos de relatorio internacional.
	 */
	public static final String QUANTIDADE_ARQUIVO_RELATORIO_INTERNACIONAL = "quantidadeArquivosRelatorioInternacional";

	/**
	 * Tempo em dias - Se a atualizacao do doador foi feita antes desse periodo não poderá ser criado outro pedido de
	 * enriquecimento Fase2.
	 */
	public static final String TEMPO_MAXIMO_ENRIQUECIMENTO_EM_DIAS_FASE2 = "tempoMaximoEnriquecimentoEmDiasFase2";

	/**
	 * Tempo em dias - Se a atualizacao do doador foi feita antes desse periodo não poderá ser criado outro pedido de
	 * enriquecimento Fase3.
	 */
	public static final String TEMPO_MAXIMO_ENRIQUECIMENTO_EM_DIAS_FASE3 = "tempoMaximoEnriquecimentoEmDiasFase3";
	
	/**
	 * Quantidade máxima de tentativas que podem ser feitas para contato telefonico de fase 2.
	 */
	public static final String QUANTIDADE_MAXIMA_TENTATIVAS_CONTATO_FASE_2 = "quantidadeMaximaTentativasContatoFase2";

	/**
	 * Quantidade máxima de dias para contato telefonico de fase 2.
	 */
	public static final String QUANTIDADE_MAXIMA_DIAS_CONTATO_FASE_2 = "quantidadeMaximaDiasContatoFase2";

	/**
	 * Quantidade máxima de tentativas que podem ser feitas para contato telefonico de fase 3.
	 */
	public static final String QUANTIDADE_MAXIMA_TENTATIVAS_CONTATO_FASE_3 = "quantidadeMaximaTentativasContatoFase3";

	/**
	 * Quantidade máxima de dias para contato telefonico de fase 3.
	 */
	public static final String QUANTIDADE_MAXIMA_DIAS_CONTATO_FASE_3 = "quantidadeMaximaDiasContatoFase2";

	/**
	 * Quantidade mínima de tentativas que podem ser feitas para contato telefonico de fase 2.
	 */
	public static final String QUANTIDADE_MINIMA_TENTATIVAS_CONTATO_FASE_2 = "quantidadeMinimaTentativasContatoFase2";

	/**
	 * Quantidade mínima de tentativas que podem ser feitas para contato telefonico de fase 3.
	 */
	public static final String QUANTIDADE_MINIMA_TENTATIVAS_CONTATO_FASE_3 = "quantidadeMinimaTentativasContatoFase3";
	
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
	 * Quantidade máxima de arquivos de evolução de paciente.
	 */
	public static final String QUANTIDADE_MAXIMA_ARQUIVOS_EVOLUCAO_PACIENTE = "quantidadeMaximaArquivosEvolucao";

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
	 * Variável estática representando o tempo mínimo para inclusão de uma evolução por conta da prescrição.
	 */
	public static final String TEMPO_MINIMO_INCLUSAO_EVOLUCAO_EM_SEGUNDOS = "tempoMinimoInclusaoEvolucaoEmSegundos";

	/**
	 * Variável para extensão do arquivo de pedido de transporte.
	 */
	public static final String EXTENSAO_ARQUIVO_PEDIDO_TRANSPORTE = "extensaoArquivoPedidoTransporte";
	

	/**
	 * Variável para extensão do arquivo de evolucao de paciente.
	 */
	public static final String EXTENSAO_ARQUIVO_EVOLUCAO_PACIENTE = "extensaoArquivoEvolucao";

	
	/**
	 * Variável para tamanho do arquivo de pedido de transporte.
	 */
	public static final String TAMANHO_ARQUIVO_PEDIDO_TRANSPORTE = "tamanhoArquivoPedidoTransporteEmByte";
	
	/**
	 * Variável estática representando o tamanho máximo do arquivo de autorizacao do paciente.
	 */
	public static final String TAMANHO_MAXIMO_AUTORIZACAO_PACIENTE = "tamanhoArquivoAutorizacaoPacienteEmByte";
	
	
	/**
	 * Variável estática representando o tamanho máximo de relatório internacional.
	 */
	public static final String TAMANHO_MAXIMO_RELATORIO_INTERNACIONAL = "tamanhoArquivoRelatorioInternacionalEmByte";
	
	
	/**
	 * Variável estática representando o tamanho máximo do arquivo de evolução do paciente.
	 */
	public static final String TAMANHO_MAXIMO_EVOLUCAO_PACIENTE = "tamanhoArquivoEvolucao";
	
	/**
	 * Variável estática representando a extensão do arquivo da autorizacao do paciente.
	 */
	public static final String EXTENSAO_ARQUIVO_AUTORIZACAO_PACIENTE = "extensaoArquivoAutorizacaoPaciente";
	
	
	/**
	 * Variável estática representando a extensão do arquivo da relatorio internacional.
	 */
	public static final String EXTENSAO_ARQUIVO_RELATORIO_INTERNACIONAL = "extensaoArquivoRelatorioInternacional";
	
	
	/**
	 * Variável estática representando idade máxima para que o paciente seja transplantado.
	 */
	public static final String IDADE_MAXIMA_PACIENTE_TRANSPLANTE = "idadeMaximaPacienteTransplante";
	
	/**
	 * Tempo limita para considerar uma evoluçao atualizada.
	 */
	public static final String TEMPO_MAXIMO_CONSIDERAR_EVOLUCAO_ATUALIZADA = "tempoMaximoParaConsiderarEvolucaoAtualizada";
	
	/**
	 * Texto utilizado como assunto no envio de email para os laboratórios com exame divergente.
	 */
	public static final String ASSUNTO_EMAIL_EXAME_DIVERGENTE = "assuntoEmailExameDivergente";
	
	/**
	 * Utilizado para comparação da soma dos pesos dos locus do genótipo para determinar em qual fase o doador estará. 
	 */
	public static final String SOMATORIO_TOTAL_PARA_IDENTIFICAR_FASE2 = "somatorioTotalParaIdentificarFase2";
	
	/**
	 * Tempo limita para considerar o retorno da comunicação do doador na fase3.
	 */
	public static final String TEMPO_MAXIMO_CONSIDERAR_RETORNO_CONTATO_DOADOR_FASE3 = "tempoMaximoParaConsiderarRetornoContatoDoadorFase3";

	/**
	 * Definição da idade máxima para doador. 
	 */
	public static final String IDADE_MAXIMA_DOADOR = "idadeMaximaDoador";
	
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
