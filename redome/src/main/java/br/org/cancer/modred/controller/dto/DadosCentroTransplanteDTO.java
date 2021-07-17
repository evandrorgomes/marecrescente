package br.org.cancer.modred.controller.dto;

import java.util.List;

import br.org.cancer.modred.model.ContatoTelefonico;

/**
 * Classe referente a nova disponibilidade de centro de coleta.
 * 
 * @author Fillipe Queiroz
 *
 */
public class DadosCentroTransplanteDTO {

	private Long rmr;
	private String nomePaciente;
	private String nomeMedico;
	private String nomeCentroTransplante;
	private List<ContatoTelefonico> telefonesCT;
	private List<ContatoTelefonico> telefonesMedico;

	/**
	 * Construtor default.
	 */
	public DadosCentroTransplanteDTO() {
		super();
	}

	/**
	 * Construtor chamado pelo hql.
	 * @param rmr - identificador do paciente
	 * @param nomePaciente - nome do paciente
	 * @param nomeMedico - nome do medico
	 * @param nomeCentroTransplante - nome do centro de transplante
	 */
	public DadosCentroTransplanteDTO(Long rmr, String nomePaciente, String nomeMedico, String nomeCentroTransplante) {
		super();
		this.rmr = rmr;
		this.nomePaciente = nomePaciente;
		this.nomeMedico = nomeMedico;
		this.nomeCentroTransplante = nomeCentroTransplante;
	}

	/**
	 * Identificador do paciente.
	 * 
	 * @return
	 */
	public Long getRmr() {
		return rmr;
	}

	/**
	 * Identificador do paciente.
	 * 
	 * @param rmr
	 */
	public void setRmr(Long rmr) {
		this.rmr = rmr;
	}

	/**
	 * Nome do paciente.
	 * 
	 * @return
	 */
	public String getNomePaciente() {
		return nomePaciente;
	}

	/**
	 * Nome do paciente.
	 * 
	 * @param nomePaciente
	 */
	public void setNomePaciente(String nomePaciente) {
		this.nomePaciente = nomePaciente;
	}

	/**
	 * Nome do medico.
	 * 
	 * @return
	 */
	public String getNomeMedico() {
		return nomeMedico;
	}

	/**
	 * Nome do medico.
	 * 
	 * @param nomeMedico
	 */
	public void setNomeMedico(String nomeMedico) {
		this.nomeMedico = nomeMedico;
	}

	/**
	 * Nome do CT.
	 * 
	 * @return
	 */
	public String getNomeCentroTransplante() {
		return nomeCentroTransplante;
	}

	/**
	 * Nome do CT.
	 * 
	 * @param nomeCentroTransplante
	 */
	public void setNomeCentroTransplante(String nomeCentroTransplante) {
		this.nomeCentroTransplante = nomeCentroTransplante;
	}

	/**
	 * telefones do centro de transplante.
	 * 
	 * @return
	 */
	public List<ContatoTelefonico> getTelefonesCT() {
		return telefonesCT;
	}

	/**
	 * telefones do centro de transplante.
	 * 
	 * @param telefonesCT
	 */
	public void setTelefonesCT(List<ContatoTelefonico> telefonesCT) {
		this.telefonesCT = telefonesCT;
	}

	/**
	 * telefones do medico.
	 * 
	 * @return
	 */
	public List<ContatoTelefonico> getTelefonesMedico() {
		return telefonesMedico;
	}

	/**
	 * telefones do medico.
	 * 
	 * @param telefonesMedico
	 */
	public void setTelefonesMedico(List<ContatoTelefonico> telefonesMedico) {
		this.telefonesMedico = telefonesMedico;
	}

}
