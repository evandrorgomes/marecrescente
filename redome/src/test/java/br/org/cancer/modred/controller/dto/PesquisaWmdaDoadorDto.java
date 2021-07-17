package br.org.cancer.modred.controller.dto;

/**
 * Classe DTO que representa a pesquisa das informações do doador compativel no WMDA.
 * 
 * @author ergomes
 *
 */
public class PesquisaWmdaDoadorDto {

	private Long id;
	private Long pesquisaWmdaId;
	private String doadorId;
	private String grid;
	private String donPool;
	private String jsonDoador;
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to comO
	 */
	public PesquisaWmdaDoadorDto comOId(Long id) {
		this.id = id;
		return this;
	}
	/**
	 * @return the pesquisaWmdaId
	 */
	public Long getPesquisaWmdaId() {
		return pesquisaWmdaId;
	}
	/**
	 * @param pesquisaWmdaId the pesquisaWmdaId to comO
	 */
	public PesquisaWmdaDoadorDto comOPesquisaWmdaId(Long pesquisaWmdaId) {
		this.pesquisaWmdaId = pesquisaWmdaId;
		return this;
	}
	/**
	 * @return the doadorId
	 */
	public String getDoadorId() {
		return doadorId;
	}
	/**
	 * @param doadorId the doadorId to comO
	 */
	public PesquisaWmdaDoadorDto comODoadorId(String doadorId) {
		this.doadorId = doadorId;
		return this;
	}
	/**
	 * @return the grid
	 */
	public String getGrid() {
		return grid;
	}
	/**
	 * @param grid the grid to comO
	 */
	public PesquisaWmdaDoadorDto comOGrid(String grid) {
		this.grid = grid;
		return this;
	}
	/**
	 * @return the donPool
	 */
	public String getDonPool() {
		return donPool;
	}
	/**
	 * @param donPool the donPool to comO
	 */
	public PesquisaWmdaDoadorDto comODonPool(String donPool) {
		this.donPool = donPool;
		return this;
	}
	/**
	 * @return the jsonDoador
	 */
	public String getJsonDoador() {
		return jsonDoador;
	}
	/**
	 * @param jsonDoador the jsonDoador to comO
	 */
	public PesquisaWmdaDoadorDto comOJsonDoador(String jsonDoador) {
		this.jsonDoador = jsonDoador;
		return this;
	}

}