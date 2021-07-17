package br.org.cancer.modred.controller.dto;

import java.util.List;

import br.org.cancer.modred.model.ContatoTelefonicoPaciente;
import br.org.cancer.modred.model.EnderecoContatoPaciente;
import br.org.cancer.modred.model.Paciente;

/**
 * Classe representante do DTO de contato do paciente.
 * Esta classe traz somente o que é possível editar.
 * 
 * @author Pizão.
 *
 */
public class ContatoPacienteDTO {

	private Long rmr;
	private List<EnderecoContatoPaciente> enderecosContato;
	private List<ContatoTelefonicoPaciente> contatosTelefonicos;
	private String email;
	
	public ContatoPacienteDTO(){}
	
	/**
	 * Construtor sobrecarregado para que seja possível montá-lo a partir dos parâmetros informados.
	 * 
	 * @param rmr do paciente a ser associado ao dto.
	 * @param email do paciente a ser associado ao dto.
	 * @param enderecos endereços de contato do paciente a ser associado ao dto.
	 * @param telefones telefones de contato do paciente a ser associado ao dto.
	 */
	public ContatoPacienteDTO(Long rmr, String email, List<EnderecoContatoPaciente> enderecos, List<ContatoTelefonicoPaciente> telefones){
		this.rmr = rmr;
		this.email = email;
		this.enderecosContato = enderecos;
		this.contatosTelefonicos = telefones;
	}
	
	/**
	 * Construtor sobrecarregado para que seja possível montá-lo a partir dos parâmetros informados.
	 * 
	 * @param paciente a ser utilizado como base para criação do dto.
	 */
	public ContatoPacienteDTO(Paciente paciente){
		this(paciente.getRmr(), paciente.getEmail(), paciente.getEnderecosContato(), paciente.getContatosTelefonicos());
	}
	
	public Long getRmr() {
		return rmr;
	}
	public void setRmr(Long rmr) {
		this.rmr = rmr;
	}
	public List<EnderecoContatoPaciente> getEnderecosContato() {
		return enderecosContato;
	}
	public void setEnderecosContato(List<EnderecoContatoPaciente> enderecosContato) {
		this.enderecosContato = enderecosContato;
	}
	public List<ContatoTelefonicoPaciente> getContatosTelefonicos() {
		return contatosTelefonicos;
	}
	public void setContatosTelefonicos(List<ContatoTelefonicoPaciente> contatosTelefonicos) {
		this.contatosTelefonicos = contatosTelefonicos;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	

}
