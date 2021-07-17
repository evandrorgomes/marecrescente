package br.org.cancer.modred.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.org.cancer.modred.controller.dto.PesquisaWmdaDoadorDTO;

/**
 * Classe que representa a pesquisa das informações do doador compativel no WMDA.
 * 
 * @author ergomes
 *
 */
@Entity
@Table(name = "PESQUISA_WMDA_DOADOR")
@SequenceGenerator(name = "SQ_PEWD_ID", sequenceName = "SQ_PEWD_ID", allocationSize = 1)
public class PesquisaWmdaDoador implements Serializable {

	private static final long serialVersionUID = -581548474965551546L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_PEWD_ID")
	@Column(name = "PEWD_ID")
	private Long id;

	@Column(name = "PEWM_ID")
	private Long pesquisaWmda;
	
	@Column(name = "PEWD_TX_ID")
	private String identificacao;

	@Column(name = "PEWD_TX_GRID")	
	private String grid;

	@Column(name = "PEWD_NR_DON_POOL")	
	private String donPool;

	@Column(name = "PEWD_TX_JSON")	
	private String jsonDoador;

	@Column(name = "PEWD_TX_LOG")	
	private String logPesquisaDoador;
	
	public PesquisaWmdaDoador() {
		super();
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
	 * @return the pesquisaWmda
	 */
	public Long getPesquisaWmda() {
		return pesquisaWmda;
	}

	/**
	 * @param pesquisaWmda the pesquisaWmda to set
	 */
	public void setPesquisaWmda(Long pesquisaWmda) {
		this.pesquisaWmda = pesquisaWmda;
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

	public static PesquisaWmdaDoador parse(PesquisaWmdaDoadorDTO pesquisaWmdaDoadorDto) {
		PesquisaWmdaDoador pesDoadorRetorno = new PesquisaWmdaDoador();
		pesDoadorRetorno.setPesquisaWmda(pesquisaWmdaDoadorDto.getPesquisaWmdaId());
		pesDoadorRetorno.setIdentificacao(pesquisaWmdaDoadorDto.getIdentificacao());
		pesDoadorRetorno.setGrid(pesquisaWmdaDoadorDto.getGrid());
		pesDoadorRetorno.setDonPool(pesquisaWmdaDoadorDto.getDoadorWmdaDto().getDonPool());
		pesDoadorRetorno.setJsonDoador(pesquisaWmdaDoadorDto.getJsonDoador());
		pesDoadorRetorno.setLogPesquisaDoador(pesquisaWmdaDoadorDto.getLogPesquisaDoador());
		return pesDoadorRetorno;
	}
}