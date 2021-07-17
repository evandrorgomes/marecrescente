package br.org.cancer.redome.workup.dto;

import java.io.Serializable;

import br.org.cancer.redome.workup.model.ArquivoPedidoWorkup;
import br.org.cancer.redome.workup.util.ArquivoUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class ArquivoPedidoWorkupDTO implements Serializable {

	private static final long serialVersionUID = 3871361357329895414L;

	private Long id;
	
	private String caminho;
	
	private String nomeSemTimestamp;
	
	public ArquivoPedidoWorkupDTO(ArquivoPedidoWorkup arquivoPedidoWorkup) {
		this.id = arquivoPedidoWorkup.getId();
		this.caminho = arquivoPedidoWorkup.getCaminho();
		this.nomeSemTimestamp = nomeSemTimestamp() != null ? nomeSemTimestamp() : ""; 
	}

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

