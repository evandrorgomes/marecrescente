package br.org.cancer.modred.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import br.org.cancer.modred.model.Locus;
import br.org.cancer.modred.redomelib.vo.ValorNmdpVo;

/**
 * Classe DTO para dados do genotipo entre paciente e doadores com match.
 * 
 * @author fillipe.queiroz
 *
 */
public class GenotipoComparadoDTO {

	private Long rmr;
	private String nomePaciente;
	private LocalDate dataNascimento;
	private String sexo;
	private BigDecimal peso;
	private String abo; 
	private String nomeMedicoResponsavel;
	private String nomeCentroAvaliador;
	private Integer idade;
	private List<ValorNmdpVo> valoresNmdp;
	
	private List<Locus> listaLocus;
	private List<GenotipoDTO> genotipoPaciente;
	private List<GenotipoDoadorComparadoDTO> genotiposDoadores;

	/**
	 * @return the rmr
	 */
	public Long getRmr() {
		return rmr;
	}

	/**
	 * @param rmr the rmr to set
	 */
	public void setRmr(Long rmr) {
		this.rmr = rmr;
	}

	/**
	 * @return the genotipoPaciente
	 */
	public List<GenotipoDTO> getGenotipoPaciente() {
		return genotipoPaciente;
	}

	/**
	 * @param genotipoPaciente the genotipoPaciente to set
	 */
	public void setGenotipoPaciente(List<GenotipoDTO> genotipoPaciente) {
		this.genotipoPaciente = genotipoPaciente;
	}

	/**
	 * @return the genotiposDoadores
	 */
	public List<GenotipoDoadorComparadoDTO> getGenotiposDoadores() {
		return genotiposDoadores;
	}

	/**
	 * @param genotiposDoadores the genotiposDoadores to set
	 */
	public void setGenotiposDoadores(List<GenotipoDoadorComparadoDTO> genotiposDoadores) {
		this.genotiposDoadores = genotiposDoadores;
	}

	
	/**
	 * @return the listaLocus
	 */
	public List<Locus> getListaLocus() {
		return listaLocus;
	}

	
	/**
	 * @param listaLocus the listaLocus to set
	 */
	public void setListaLocus(List<Locus> listaLocus) {
		this.listaLocus = listaLocus;
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
	 * @return the nomePaciente
	 */
	public String getNomePaciente() {
		return nomePaciente;
	}

	
	/**
	 * @param nomePaciente the nomePaciente to set
	 */
	public void setNomePaciente(String nomePaciente) {
		this.nomePaciente = nomePaciente;
	}

	/**
	 * @return the nomeMedicoResponsavel
	 */
	public String getNomeMedicoResponsavel() {
		return nomeMedicoResponsavel;
	}

	/**
	 * @param nomeMedicoResponsavel the nomeMedicoResponsavel to set
	 */
	public void setNomeMedicoResponsavel(String nomeMedicoResponsavel) {
		this.nomeMedicoResponsavel = nomeMedicoResponsavel;
	}

	/**
	 * @return the nomeCentroAvaliador
	 */
	public String getNomeCentroAvaliador() {
		return nomeCentroAvaliador;
	}

	/**
	 * @param nomeCentroAvaliador the nomeCentroAvaliador to set
	 */
	public void setNomeCentroAvaliador(String nomeCentroAvaliador) {
		this.nomeCentroAvaliador = nomeCentroAvaliador;
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
	 * @return the valoresNmdp
	 */
	public List<ValorNmdpVo> getValoresNmdp() {
		return valoresNmdp;
	}

	/**
	 * @param valoresNmdp the valoresNmdp to set
	 */
	public void setValoresNmdp(List<ValorNmdpVo> valoresNmdp) {
		this.valoresNmdp = valoresNmdp;
	}
	
}
