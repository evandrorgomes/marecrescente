package br.org.cancer.modred.controller.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRootName;

import br.org.cancer.modred.util.DateUtils;

/**
 * DTO para exibição da dialogo da pendencia.
 * 
 * @author Cintia Oliveira
 *
 */
@JsonRootName("respostaPendencia")
public class RespostaPendenciaDTO {

	private String resposta;
	private String usuario;
	@JsonIgnore
	private LocalDateTime data;
	private Long exame;
	private Long evolucao;

	/**
	 * @return the resposta
	 */
	public String getResposta() {
		return resposta;
	}

	/**
	 * @param resposta the resposta to set
	 */
	public void setResposta(String resposta) {
		this.resposta = resposta;
	}

	/**
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the data
	 */
	public LocalDateTime getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(LocalDateTime data) {
		this.data = data;
	}

	/**
	 * @return the exame
	 */
	public Long getExame() {
		return exame;
	}

	/**
	 * @param exame the exame to set
	 */
	public void setExame(Long exame) {
		this.exame = exame;
	}

	/**
	 * @return the evolucao
	 */
	public Long getEvolucao() {
		return evolucao;
	}

	/**
	 * @param evolucao the evolucao to set
	 */
	public void setEvolucao(Long evolucao) {
		this.evolucao = evolucao;
	}

	public String getDataFormatadaDialogo() {
		return DateUtils.getDataFormatadaComHoraEMinutos(data);

	}

}
