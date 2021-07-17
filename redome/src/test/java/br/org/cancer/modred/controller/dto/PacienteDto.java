package br.org.cancer.modred.controller.dto;

import java.time.LocalDate;

import br.org.cancer.modred.model.Responsavel;

public class PacienteDto {

	private String nome;
	private String nomeMae;
	private String cpf;
	private String cns;
	private Responsavel responsavel;
	private LocalDate dataNascimento;
	private Long rmr;
	private Long wmdaId;
	
	
	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}
	/**
	 * @param nome the nome to set
	 */
	public PacienteDto comONome(String nome) {
		this.nome = nome;
		return this;
	}
	/**
	 * @return the nomeMae
	 */
	public String getNomeMae() {
		return nomeMae;
	}
	/**
	 * @param nomeMae the nomeMae to set
	 */
	public PacienteDto comONomeMae(String nomeMae) {
		this.nomeMae = nomeMae;
		return this;
	}
	/**
	 * @return the cpf
	 */
	public String getCpf() {
		return cpf;
	}
	/**
	 * @param cpf the cpf to set
	 */
	public PacienteDto comOCpf(String cpf) {
		this.cpf = cpf;
		return this;
	}
	/**
	 * @return the cns
	 */
	public String getCns() {
		return cns;
	}
	/**
	 * @param cns the cns to set
	 */
	public PacienteDto comOCns(String cns) {
		this.cns = cns;
		return this;
	}
	/**
	 * @return the responsavel
	 */
	public Responsavel getResponsavel() {
		return responsavel;
	}
	/**
	 * @param responsavel the responsavel to set
	 */
	public PacienteDto comOResponsavel(Responsavel responsavel) {
		this.responsavel = responsavel;
		return this;
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
	public PacienteDto comADataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
		return this;
		
	}
	/**
	 * @return the rmr
	 */
	public Long getRmr() {
		return rmr;
	}
	
	/**
	 * @param rmr the rmr to set
	 */
	public PacienteDto comORmr(Long rmr) {
		this.rmr = rmr;
		return this;
	}

	/**
	 * @return the wmdaId
	 */
	public Long getWmdaId() {
		return wmdaId;
	}
	
	/**
	 * @param wmdaId the wmdaId to set
	 */
	public PacienteDto comOWmdaId(Long wmdaId) {
		this.wmdaId = wmdaId;
		return this;
	}
	
	
}
