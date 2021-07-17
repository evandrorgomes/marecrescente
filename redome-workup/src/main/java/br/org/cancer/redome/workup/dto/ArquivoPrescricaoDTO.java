package br.org.cancer.redome.workup.dto;

import java.io.Serializable;

import br.org.cancer.redome.workup.model.ArquivoPrescricao;
import br.org.cancer.redome.workup.util.ArquivoUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class ArquivoPrescricaoDTO implements Serializable {

	private static final long serialVersionUID = 3871361357329895414L;

	private Long id;
	
	private String caminho;
	
	private Boolean justificativa;
	
	private Boolean autorizacaoPaciente;
	
	private String nomeSemTimestamp;
	
	public ArquivoPrescricaoDTO(ArquivoPrescricao arquivoPrescricao) {
		this.id = arquivoPrescricao.getId();
		this.caminho = arquivoPrescricao.getCaminho();
		this.justificativa = arquivoPrescricao.getJustificativa();
		this.autorizacaoPaciente = arquivoPrescricao.getAutorizacaoPaciente();
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

