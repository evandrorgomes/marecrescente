package br.org.cancer.modred.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Classe DTO para dados do genotipo comparado de doadores com match de um paciente.
 * 
 * @author fillipe.queiroz
 *
 */
public class GenotipoDoadorComparadoDTO {

	private Long id;
	private String sexo;
	private LocalDate dataNascimento;
	private Integer idade;
	private BigDecimal peso;
	private String abo;
	private List<GenotipoDTO> genotipoDoador;
	private String classificacao;
	private String mismatch;
	private String fase;
	private Long dmr;
	private String idRegistro;
	private String registroOrigem;	
	private BigDecimal quantidadeTCNPorKilo;
	private BigDecimal quantidadeCD34PorKilo;
	private Long tipoDoador;
	private String idBscup;
	private LocalDateTime dataAtualizacao;
	private String descricaoTipoPermissividade;
	private String identificadorDoador;

	/**
	 * @return the genotipoDoador
	 */
	public List<GenotipoDTO> getGenotipoDoador() {
		return genotipoDoador;
	}

	/**
	 * @param genotipoDoador the genotipoDoador to set
	 */
	public void setGenotipoDoador(List<GenotipoDTO> genotipoDoador) {
		this.genotipoDoador = genotipoDoador;
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
	 * @return the classificacao
	 */
	public String getClassificacao() {
		return classificacao;
	}

	/**
	 * @param classificacao the classificacao to set
	 */
	public void setClassificacao(String classificacao) {
		this.classificacao = classificacao;
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
	 * @return the idRegistro
	 */
	public String getIdRegistro() {
		return idRegistro;
	}

	
	/**
	 * @param idRegistro the idRegistro to set
	 */
	public void setIdRegistro(String idRegistro) {
		this.idRegistro = idRegistro;
	}

	
	/**
	 * @return the registroOrigem
	 */
	public String getRegistroOrigem() {
		return registroOrigem;
	}

	
	/**
	 * @param registroOrigem the registroOrigem to set
	 */
	public void setRegistroOrigem(String registroOrigem) {
		this.registroOrigem = registroOrigem;
	}

	
	/**
	 * @return the quantidadeTCNPorKilo
	 */
	public BigDecimal getQuantidadeTCNPorKilo() {
		return quantidadeTCNPorKilo;
	}

	
	/**
	 * @param quantidadeTCNPorKilo the quantidadeTCNPorKilo to set
	 */
	public void setQuantidadeTCNPorKilo(BigDecimal quantidadeTCNPorKilo) {
		this.quantidadeTCNPorKilo = quantidadeTCNPorKilo;
	}

	
	/**
	 * @return the quantidadeCD34PorKilo
	 */
	public BigDecimal getQuantidadeCD34PorKilo() {
		return quantidadeCD34PorKilo;
	}

	
	/**
	 * @param quantidadeCD34PorKilo the quantidadeCD34PorKilo to set
	 */
	public void setQuantidadeCD34PorKilo(BigDecimal quantidadeCD34PorKilo) {
		this.quantidadeCD34PorKilo = quantidadeCD34PorKilo;
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
	 * @return the idBscup
	 */
	public String getIdBscup() {
		return idBscup;
	}

	
	/**
	 * @param idBscup the idBscup to set
	 */
	public void setIdBscup(String idBscup) {
		this.idBscup = idBscup;
	}

	
	/**
	 * @return the dmr
	 */
	public Long getDmr() {
		return dmr;
	}

	
	/**
	 * @param dmr the dmr to set
	 */
	public void setDmr(Long dmr) {
		this.dmr = dmr;
	}

	/**
	 * @return the mismatch
	 */
	public String getMismatch() {
		return mismatch;
	}

	/**
	 * @param mismatch the mismatch to set
	 */
	public void setMismatch(String mismatch) {
		this.mismatch = mismatch;
	}

	/**
	 * @return the fase
	 */
	public String getFase() {
		return fase;
	}

	/**
	 * @param fase the fase to set
	 */
	public void setFase(String fase) {
		this.fase = fase;
	}

	/**
	 * @return the dataAtualizacao
	 */
	public LocalDateTime getDataAtualizacao() {
		return dataAtualizacao;
	}

	/**
	 * @param dataAtualizacao the dataAtualizacao to set
	 */
	public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	/**
	 * @return the descricaoTipoPermissividade
	 */
	public String getDescricaoTipoPermissividade() {
		return descricaoTipoPermissividade;
	}

	/**
	 * @param descricaoTipoPermissividade the descricaoTipoPermissividade to set
	 */
	public void setDescricaoTipoPermissividade(String descricaoTipoPermissividade) {
		this.descricaoTipoPermissividade = descricaoTipoPermissividade;
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

	public String getIdentificadorDoador() {
		return identificadorDoador;
	}

	public void setIdentificadorDoador(String identificadorDoador) {
		this.identificadorDoador = identificadorDoador;
	}
	
	
}
