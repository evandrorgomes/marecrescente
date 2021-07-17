package br.org.cancer.modred.controller.dto;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.AvaliacaoView;
import br.org.cancer.modred.model.Cid;

/**
 * Classe DTO que representa um item na tela de pacientes em avaliação.
 * 
 * @author Fillipe Queiroz
 *
 */
public class PacienteAvaliacaoTarefaDTO {

	@JsonView(AvaliacaoView.ListarAvaliacoesPacientes.class)
	private Long rmr;
	@JsonView(AvaliacaoView.ListarAvaliacoesPacientes.class)
	private String nome;
	@JsonView(AvaliacaoView.ListarAvaliacoesPacientes.class)
	private Integer idade;
	@JsonView(AvaliacaoView.ListarAvaliacoesPacientes.class)
	private String tempoCadastro;
	@JsonView(AvaliacaoView.ListarAvaliacoesPacientes.class)
	private Cid cid;

	public PacienteAvaliacaoTarefaDTO() {}

	/**
	 * Construtor com parametros rmr, nome e tempoCadastro.
	 * 
	 * @param rmr - rmr do paciente
	 * @param nome - nome do paciente
	 * @param tempoCadastro - tempo desde que paciente foi cadastrado
	 */
	public PacienteAvaliacaoTarefaDTO(Long rmr, String nome, String tempoCadastro) {
		this.rmr = rmr;
		this.nome = nome;
		this.tempoCadastro = tempoCadastro;
	}

	/**
	 * RMR do paciente associado a classe.
	 * 
	 * @return RMR do paciente.
	 */
	public Long getRmr() {
		return rmr;
	}

	/**
	 * Seta o rmr do paciente.
	 * 
	 * @param rmr
	 */
	public void setRmr(Long rmr) {
		this.rmr = rmr;
	}

	/**
	 * Retorna o nome do paciente associado a classe.
	 * 
	 * @return nome do paciente.
	 */
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * Seta a idade do paciente.
	 * 
	 * @return
	 */
	public Integer getIdade() {
		return idade;
	}

	/**
	 * Seta a idade do paciente.
	 * 
	 * @param idade
	 */
	public void setIdade(Integer idade) {
		this.idade = idade;
	}

	/**
	 * Retorna o aging da data de cadastro.
	 * 
	 * @return tempoCadastro
	 */
	public String getTempoCadastro() {
		return tempoCadastro;
	}

	/**
	 * Seta o aging da data de cadastro do paciente.
	 * 
	 * @param tempoCadastro
	 */
	public void setTempoCadastro(String tempoCadastro) {
		this.tempoCadastro = tempoCadastro;
	}

	/**
	 * Retorna a cid.
	 * 
	 * @return
	 */
	public Cid getCid() {
		return cid;
	}

	/**
	 * Seta uma cid.
	 * 
	 * @param cid
	 */
	public void setCid(Cid cid) {
		this.cid = cid;
	}

}
