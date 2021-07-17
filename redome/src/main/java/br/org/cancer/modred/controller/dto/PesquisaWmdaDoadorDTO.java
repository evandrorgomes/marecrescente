package br.org.cancer.modred.controller.dto;

import java.io.Serializable;

import br.org.cancer.modred.model.PesquisaWmdaDoador;

/**
 * Classe DTO que representa a pesquisa das informações do doador compativel no WMDA.
 * 
 * @author ergomes
 *
 */
public class PesquisaWmdaDoadorDTO implements Serializable {

	private static final long serialVersionUID = 9190442631913708120L;

	private Long id;
	private String identificacao;
	private String grid;
	private String donPool;
	private String jsonDoador;
	private String logPesquisaDoador;
	private Long pesquisaWmdaId;
	private Long buscaId;
	
	private DoadorWmdaDTO doadorWmdaDto;
	
	/**
	 * 
	 */
	public PesquisaWmdaDoadorDTO() {}
	
	/**
	 * @param id
	 * @param identificacao
	 * @param grid
	 * @param donPool
	 * @param jsonDoador
	 * @param logDoador
	 * @param pesquisaWmdaId
	 * @param buscaId
	 * @param doadorWmdaDto
	 */
	public PesquisaWmdaDoadorDTO(Long id, String identificacao, String grid, String donPool, String jsonDoador,
			String logPesquisaDoador, Long pesquisaWmdaId, Long buscaId, DoadorWmdaDTO doadorWmdaDto) {
		this.id = id;
		this.identificacao = identificacao;
		this.grid = grid;
		this.donPool = donPool;
		this.jsonDoador = jsonDoador;
		this.logPesquisaDoador = logPesquisaDoador;
		this.pesquisaWmdaId = pesquisaWmdaId;
		this.buscaId = buscaId;
		this.doadorWmdaDto = doadorWmdaDto;
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
	 * @return the identificacao
	 */
	public String getIdentificacao() {
		return identificacao;
	}
	/**
	 * @param identificacao the identificacao to set
	 */
	public void setIdentificacao(String identificacao) {
		this.identificacao = identificacao;
	}
	/**
	 * @return the grid
	 */
	public String getGrid() {
		return grid;
	}
	/**
	 * @param grid the grid to set
	 */
	public void setGrid(String grid) {
		this.grid = grid;
	}
	/**
	 * @return the donPool
	 */
	public String getDonPool() {
		return donPool;
	}
	/**
	 * @param donPool the donPool to set
	 */
	public void setDonPool(String donPool) {
		this.donPool = donPool;
	}
	/**
	 * @return the jsonDoador
	 */
	public String getJsonDoador() {
		return jsonDoador;
	}
	/**
	 * @param jsonDoador the jsonDoador to set
	 */
	public void setJsonDoador(String jsonDoador) {
		this.jsonDoador = jsonDoador;
	}
	/**
	 * @return the pesquisaWmdaId
	 */
	public Long getPesquisaWmdaId() {
		return pesquisaWmdaId;
	}
	/**
	 * @param pesquisaWmdaId the pesquisaWmdaId to set
	 */
	public void setPesquisaWmdaId(Long pesquisaWmdaId) {
		this.pesquisaWmdaId = pesquisaWmdaId;
	}
	/**
	 * @return the doadorWmdaDto
	 */
	public DoadorWmdaDTO getDoadorWmdaDto() {
		return doadorWmdaDto;
	}
	/**
	 * @param doadorWmdaDto the doadorWmdaDto to set
	 */
	public void setDoadorWmdaDto(DoadorWmdaDTO doadorWmdaDto) {
		this.doadorWmdaDto = doadorWmdaDto;
	}

	/**
	 * @return the logPesquisaDoador
	 */
	public String getLogPesquisaDoador() {
		return logPesquisaDoador;
	}

	/**
	 * @param logPesquisaDoador the logPesquisaDoador to set
	 */
	public void setLogPesquisaDoador(String logPesquisaDoador) {
		this.logPesquisaDoador = logPesquisaDoador;
	}

	/**
	 * @return the buscaId
	 */
	public Long getBuscaId() {
		return buscaId;
	}
	/**
	 * @param buscaId the buscaId to set
	 */
	public void setBuscaId(Long buscaId) {
		this.buscaId = buscaId;
	}
	
	public static PesquisaWmdaDoadorDTO parse(PesquisaWmdaDoador pesquisaWmdaDoador) {
		PesquisaWmdaDoadorDTO pesDoadorRetornoDto = new PesquisaWmdaDoadorDTO();
		pesDoadorRetornoDto.setPesquisaWmdaId(pesquisaWmdaDoador.getPesquisaWmda());
		pesDoadorRetornoDto.setIdentificacao(pesquisaWmdaDoador.getIdentificacao());
		pesDoadorRetornoDto.setGrid(pesquisaWmdaDoador.getGrid());
		pesDoadorRetornoDto.setDonPool(pesquisaWmdaDoador.getDonPool());
		pesDoadorRetornoDto.setJsonDoador(pesquisaWmdaDoador.getJsonDoador());
		pesDoadorRetornoDto.setLogPesquisaDoador(pesquisaWmdaDoador.getLogPesquisaDoador());
		return pesDoadorRetornoDto;
	}

}