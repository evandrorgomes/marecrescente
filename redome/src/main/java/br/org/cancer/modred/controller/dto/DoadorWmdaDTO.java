package br.org.cancer.modred.controller.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import br.org.cancer.modred.controller.dto.doador.LocusExameWmdaDTO;

/**
 * Classe DTO que representa a pesquisa das informações do doador compativel no WMDA.
 * 
 * @author ergomes
 *
 */
public class DoadorWmdaDTO implements Serializable {

	private static final long serialVersionUID = 7594228725683381949L;

	private Long id;
	private String grid;
	private String donPool;
	private String jsonDoador;
	
	/* ###### DOADOR ###### */
	private String identificacao;
	private Long tipoDoador;
	private Long registroOrigem;
	private String sexo;
	private String abo;
	private LocalDate dataNascimento;
	private BigDecimal peso;
	private BigDecimal quantidadeTotalTCN;
	private BigDecimal quantidadeTotalCD34;
	private BigDecimal volume;
	private Integer idade;
	private List<LocusExameWmdaDTO> locusExame;
	
	private MatchWmdaDTO matchWmdaDto;

	/**
	 * 
	 */
	public DoadorWmdaDTO() {}

	/**
	 * @param id
	 * @param grid
	 * @param donPool
	 * @param jsonDoador
	 * @param identificacao
	 * @param tipoDoador
	 * @param registroOrigem
	 * @param sexo
	 * @param abo
	 * @param dataNascimento
	 * @param peso
	 * @param quantidadeTotalTCN
	 * @param quantidadeTotalCD34
	 * @param volume
	 * @param idade
	 * @param locusExame
	 * @param matchWmdaDto
	 */
	public DoadorWmdaDTO(Long id, String grid, String donPool, String jsonDoador, String identificacao, Long tipoDoador,
			Long registroOrigem, String sexo, String abo, LocalDate dataNascimento, BigDecimal peso,
			BigDecimal quantidadeTotalTCN, BigDecimal quantidadeTotalCD34, BigDecimal volume, Integer idade,
			List<LocusExameWmdaDTO> locusExame, MatchWmdaDTO matchWmdaDto) {
		super();
		this.id = id;
		this.grid = grid;
		this.donPool = donPool;
		this.jsonDoador = jsonDoador;
		this.identificacao = identificacao;
		this.tipoDoador = tipoDoador;
		this.registroOrigem = registroOrigem;
		this.sexo = sexo;
		this.abo = abo;
		this.dataNascimento = dataNascimento;
		this.peso = peso;
		this.quantidadeTotalTCN = quantidadeTotalTCN;
		this.quantidadeTotalCD34 = quantidadeTotalCD34;
		this.volume = volume;
		this.idade = idade;
		this.locusExame = locusExame;
		this.matchWmdaDto = matchWmdaDto;
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
	 * @return the tipoDoador
	 */
	public Long getTipoDoador() {
		return tipoDoador;
	}

	/**
	 * @param tipoDoador the tipoDoador to set
	 */
	public void setTipoDoador(Long tipoDoador) {
		this.tipoDoador = tipoDoador;
	}

	/**
	 * @return the registroOrigem
	 */
	public Long getRegistroOrigem() {
		return registroOrigem;
	}

	/**
	 * @param registroOrigem the registroOrigem to set
	 */
	public void setRegistroOrigem(Long registroOrigem) {
		this.registroOrigem = registroOrigem;
	}

	/**
	 * @return the sexo
	 */
	public String getSexo() {
		return sexo;
	}

	/**
	 * @param sexo the sexo to set
	 */
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	/**
	 * @return the abo
	 */
	public String getAbo() {
		return abo;
	}

	/**
	 * @param abo the abo to set
	 */
	public void setAbo(String abo) {
		this.abo = abo;
	}

	/**
	 * @return the dataNascimento
	 */
	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	/**
	 * @param dataNascimento the dataNascimento to set
	 */
	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	/**
	 * @return the peso
	 */
	public BigDecimal getPeso() {
		return peso;
	}

	/**
	 * @param peso the peso to set
	 */
	public void setPeso(BigDecimal peso) {
		this.peso = peso;
	}

	/**
	 * @return the quantidadeTotalTCN
	 */
	public BigDecimal getQuantidadeTotalTCN() {
		return quantidadeTotalTCN;
	}

	/**
	 * @param quantidadeTotalTCN the quantidadeTotalTCN to set
	 */
	public void setQuantidadeTotalTCN(BigDecimal quantidadeTotalTCN) {
		this.quantidadeTotalTCN = quantidadeTotalTCN;
	}

	/**
	 * @return the quantidadeTotalCD34
	 */
	public BigDecimal getQuantidadeTotalCD34() {
		return quantidadeTotalCD34;
	}

	/**
	 * @param quantidadeTotalCD34 the quantidadeTotalCD34 to set
	 */
	public void setQuantidadeTotalCD34(BigDecimal quantidadeTotalCD34) {
		this.quantidadeTotalCD34 = quantidadeTotalCD34;
	}

	/**
	 * @return the volume
	 */
	public BigDecimal getVolume() {
		return volume;
	}

	/**
	 * @param volume the volume to set
	 */
	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}

	/**
	 * @return the idade
	 */
	public Integer getIdade() {
		return idade;
	}

	/**
	 * @param idade the idade to set
	 */
	public void setIdade(Integer idade) {
		this.idade = idade;
	}

	/**
	 * @return the locusExame
	 */
	public List<LocusExameWmdaDTO> getLocusExame() {
		return locusExame;
	}

	/**
	 * @param locusExame the locusExame to set
	 */
	public void setLocusExame(List<LocusExameWmdaDTO> locusExame) {
		this.locusExame = locusExame;
	}

	/**
	 * @return the matchWmdaDto
	 */
	public MatchWmdaDTO getMatchWmdaDto() {
		return matchWmdaDto;
	}

	/**
	 * @param matchWmdaDto the matchWmdaDto to set
	 */
	public void setMatchWmdaDto(MatchWmdaDTO matchWmdaDto) {
		this.matchWmdaDto = matchWmdaDto;
	}
	
}