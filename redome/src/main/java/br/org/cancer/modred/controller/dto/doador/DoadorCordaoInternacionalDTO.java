package br.org.cancer.modred.controller.dto.doador;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Dto para DoadorInternacional.
 * 
 * @author Filipe Paes
 *
 */

public class DoadorCordaoInternacionalDTO  implements Serializable {

	private static final long serialVersionUID = -3851323934063893734L;
	
	private Long tipoDoador;

	private Long rmrAssociado;
	
	private Long id;
	
	private LocalDate dataCadastro;
	
	private RegistroDTO registroOrigem;
	
	private String sexo;
	
	private String abo;
	
	private LocalDate dataNascimento;
	
	private LocalDateTime dataAtualizacao;
	
	private StatusDoadorDTO statusDoador;
	
	private LocalDate dataRetornoInatividade;
	
	private BigDecimal peso;
	
	private Integer idade;
	
	private String grid;

	private String idRegistro;
	
	private RegistroDTO registroPagamento;

	private Boolean cadastradoEmdis;
	
	private List<String> ressalvas;
	
	private List<LocusExameDTO> locusExames;
	
	private List<ValorGenotipoDTO> valoresGenotipo;
	
	private BigDecimal quantidadeTotalTCN;

	private BigDecimal quantidadeTotalCD34;

	private BigDecimal volume;

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
	 * @return the rmrAssociado
	 */
	public Long getRmrAssociado() {
		return rmrAssociado;
	}

	/**
	 * @param rmrAssociado the rmrAssociado to set
	 */
	public void setRmrAssociado(Long rmrAssociado) {
		this.rmrAssociado = rmrAssociado;
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
	 * @return the dataCadastro
	 */
	public LocalDate getDataCadastro() {
		return dataCadastro;
	}

	/**
	 * @param dataCadastro the dataCadastro to set
	 */
	public void setDataCadastro(LocalDate dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	/**
	 * @return the registroOrigem
	 */
	public RegistroDTO getRegistroOrigem() {
		return registroOrigem;
	}

	/**
	 * @param registroOrigem the registroOrigem to set
	 */
	public void setRegistroOrigem(RegistroDTO registroOrigem) {
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
	 * @return the statusDoador
	 */
	public StatusDoadorDTO getStatusDoador() {
		return statusDoador;
	}

	/**
	 * @param statusDoador the statusDoador to set
	 */
	public void setStatusDoador(StatusDoadorDTO statusDoador) {
		this.statusDoador = statusDoador;
	}

	/**
	 * @return the dataRetornoInatividade
	 */
	public LocalDate getDataRetornoInatividade() {
		return dataRetornoInatividade;
	}

	/**
	 * @param dataRetornoInatividade the dataRetornoInatividade to set
	 */
	public void setDataRetornoInatividade(LocalDate dataRetornoInatividade) {
		this.dataRetornoInatividade = dataRetornoInatividade;
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
	 * @return the registroPagamento
	 */
	public RegistroDTO getRegistroPagamento() {
		return registroPagamento;
	}

	/**
	 * @param registroPagamento the registroPagamento to set
	 */
	public void setRegistroPagamento(RegistroDTO registroPagamento) {
		this.registroPagamento = registroPagamento;
	}

	/**
	 * @return the cadastradoEmdis
	 */
	public Boolean getCadastradoEmdis() {
		return cadastradoEmdis;
	}

	/**
	 * @param cadastradoEmdis the cadastradoEmdis to set
	 */
	public void setCadastradoEmdis(Boolean cadastradoEmdis) {
		this.cadastradoEmdis = cadastradoEmdis;
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
	 * @return the ressalvas
	 */
	public List<String> getRessalvas() {
		return ressalvas;
	}

	/**
	 * @param ressalvas the ressalvas to set
	 */
	public void setRessalvas(List<String> ressalvas) {
		this.ressalvas = ressalvas;
	}
	
	/**
	 * @return the locusExames
	 */
	public List<LocusExameDTO> getLocusExames() {
		return locusExames;
	}

	/**
	 * @param locusExames the locusExames to set
	 */
	public void setLocusExames(List<LocusExameDTO> locusExames) {
		this.locusExames = locusExames;
	}

	/**
	 * @return the valoresGenotipo
	 */
	public List<ValorGenotipoDTO> getValoresGenotipo() {
		return valoresGenotipo;
	}

	/**
	 * @param valoresGenotipo the valoresGenotipo to set
	 */
	public void setValoresGenotipo(List<ValorGenotipoDTO> valoresGenotipo) {
		this.valoresGenotipo = valoresGenotipo;
	}
		
	
}
