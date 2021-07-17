package br.org.cancer.redome.workup.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.org.cancer.redome.workup.util.ArquivoUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe de arquivos da prescricao. Dentro desta estarão as referencias para o storage.
 * 
 * @author bruno.sousa.
 */
@Entity
@SequenceGenerator(name = "SQ_ARPW_ID", sequenceName = "SQ_ARPW_ID", allocationSize = 1)
@Table(name = "ARQUIVO_PEDIDO_WORKUP")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArquivoPedidoWorkup implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Variável estática representando o tamanho máximo que o nome do arquivo pode conter.
	 */
	public static final int TAMANHO_MAXIMO_NOME_ARQUIVO = 257;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_ARPW_ID")
	@Column(name = "ARPW_ID")
	private Long id;

	@Column(name = "ARPW_TX_CAMINHO")
	private String caminho;

	@ManyToOne
	@JoinColumn(name = "PEWO_ID")
	private PedidoWorkup pedidoWorkup;

	/**
	 * Método para obter somente o nome do arquivo de exame.
	 * 
	 * @return nome nome
	 */
	public String nomeSemTimestamp() {
		return ArquivoUtil.obterNomeArquivoSemTimestampPorCaminhoArquivo(this.getCaminho());
	}

	/**
	 * Retorna o nome do arquivo com timestamp.
	 * 
	 * @return
	 */
	public String nomeComTimestamp() {
		return ArquivoUtil.obterNomeArquivoComTimestampPorCaminhoArquivo(this.getCaminho());
	}

}