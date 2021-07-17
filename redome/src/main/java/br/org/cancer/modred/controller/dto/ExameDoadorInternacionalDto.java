package br.org.cancer.modred.controller.dto;

import java.time.LocalDateTime;
import java.util.List;

import br.org.cancer.modred.model.LocusExame;

/**
 * Dto para colocar exames de HLA e de IDM juntos.
 * 
 * @author Filipe Paes
 *
 */
public class ExameDoadorInternacionalDto {

	private Integer idGenerico;
	private Long id;
	private Integer statusExame = 0;
	private List<LocusExame> locusExames;
	private String caminhoArquivo;
	private Long tipoExame;
	private LocalDateTime dataCriacao;

	/**
	 * @return the idGenerico
	 */
	public Integer getIdGenerico() {
		return idGenerico;
	}

	/**
	 * @param idGenerico the idGenerico to set
	 */
	public void setIdGenerico(Integer idGenerico) {
		this.idGenerico = idGenerico;
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
	 * @return the statusExame
	 */
	public Integer getStatusExame() {
		return statusExame;
	}

	/**
	 * @param statusExame
	 *            the statusExame to set
	 */
	public void setStatusExame(Integer statusExame) {
		this.statusExame = statusExame;
	}

	/**
	 * @return the locusExames
	 */
	public List<LocusExame> getLocusExames() {
		return locusExames;
	}

	/**
	 * @param locusExames
	 *            the locusExames to set
	 */
	public void setLocusExames(List<LocusExame> locusExames) {
		this.locusExames = locusExames;
	}

	/**
	 * @return the caminhoArquivo
	 */
	public String getCaminhoArquivo() {
		return caminhoArquivo;
	}

	/**
	 * @param caminhoArquivo
	 *            the caminhoArquivo to set
	 */
	public void setCaminhoArquivo(String caminhoArquivo) {
		this.caminhoArquivo = caminhoArquivo;
	}

	/**
	 * @return the tipoExame
	 */
	public Long getTipoExame() {
		return tipoExame;
	}

	/**
	 * @param tipoExame the tipoExame to set
	 */
	public void setTipoExame(Long tipoExame) {
		this.tipoExame = tipoExame;
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

	
}
