package br.org.cancer.modred.controller.dto;

import java.time.LocalDateTime;

import br.org.cancer.modred.model.domain.StatusTarefa;

/**
 * Classe DTO que representa um item na tela de pacientes em avaliação.
 * 
 * @author Pizão
 *
 */
public class PacienteTarefaDTO {

	private Long rmr;
	private String nome;
	private Integer totalTarefas;
	private LocalDateTime dataCriacaoAvaliacao;
	private LocalDateTime dataTarefa;
	private String agingTarefa;
	private String agingAvaliacao;
	private LocalDateTime dataCadastro;
	private String agingCadastro;
	private String descricao;
	private StatusTarefa statusTarefa;
	private Long idTarefa;
	
	private String medicoResponsavelPaciente;

	public PacienteTarefaDTO() {}


	/**
	 * Cria uma nova instância dos atributos informados.
	 * 
	 * @param rmr RMR do paciente a ser preenchido.
	 * @param nome Nome do paciente.
	 * @param dataCriacaoAvaliacao data de criação da avaliação.
	 */
	public PacienteTarefaDTO(Long rmr, String nome, LocalDateTime dataCriacaoAvaliacao) {
		this.rmr = rmr;
		this.nome = nome;
		this.dataCriacaoAvaliacao = dataCriacaoAvaliacao;
	}
	
	/**
	 * Cria uma nova instância a partir da informada (clone).
	 * 
	 * @param dto referência para o novo objeto.
	 */
	public PacienteTarefaDTO(PacienteTarefaDTO dto) {
		this.rmr = dto.rmr;
		this.nome = dto.nome;
		this.totalTarefas = dto.totalTarefas;
		this.dataCriacaoAvaliacao = dto.dataCriacaoAvaliacao;
		this.dataTarefa = dto.dataTarefa;
		this.agingTarefa = dto.agingTarefa;
		this.agingAvaliacao = dto.agingAvaliacao;
		this.dataCadastro = dto.dataCadastro;
		this.agingCadastro = dto.agingCadastro;
		this.descricao = dto.descricao;
	}

	/**
	 * RMR do paciente associado a classe.
	 * 
	 * @return RMR do paciente.
	 */
	public Long getRmr() {
		return rmr;
	}

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
	 * A data da pendência mais antiga, em andamento, associado ao paciente.
	 * 
	 * @return representa o aging da pendência, conforme esperado.
	 */
	public String getAgingTarefa() {
		return agingTarefa;
	}

	public void setAgingTarefa(String agingTarefa) {
		this.agingTarefa = agingTarefa;
	}

	/**
	 * Retorna o aging da avaliação formatado para exibição da tela.
	 * 
	 * @return representa o aging da avaliação, conforme esperado.
	 */
	public String getAgingAvaliacao() {
		return agingAvaliacao;
	}

	public void setAgingAvaliacao(String agingAvaliacao) {
		this.agingAvaliacao = agingAvaliacao;
	}

	/**
	 * Retorna o total de pendências associado ao paciente.
	 * 
	 * @return total de pendências em andamento.
	 */
	public Integer getTotalTarefas() {
		return totalTarefas;
	}

	public void setTotalTarefas(Integer totalTarefas) {
		this.totalTarefas = totalTarefas;
	}

	/**
	 * Retorna a data de criação da avaliação associada ao paciente.
	 * 
	 * @return data de criação da avaliação.
	 */
	public LocalDateTime getDataCriacaoAvaliacao() {
		return dataCriacaoAvaliacao;
	}

	public void setDataCriacaoAvaliacao(LocalDateTime dataCriacaoAvaliacao) {
		this.dataCriacaoAvaliacao = dataCriacaoAvaliacao;
	}

	/**
	 * Data da criação do cadastro do paciente.
	 * 
	 * @return data do cadastro.
	 */
	public LocalDateTime getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(LocalDateTime dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	/**
	 * Retorna o aging da data do cadastro no formatado para exibição da tela.
	 * 
	 * @return representa o aging do cadastro, no formato, conforme esperado.
	 */
	public String getAgingCadastro() {
		return agingCadastro;
	}

	public void setAgingCadastro(String agingCadastro) {
		this.agingCadastro = agingCadastro;
	}

	/**
	 * Retorna a descrição da tarefa.
	 * 
	 * @return conteúdo da descrição da tarefa.
	 */
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * Retorna a data da tarefa mais antiga para este paciente.
	 * 
	 * @return data da tarefa mais antiga.
	 */
	public LocalDateTime getDataTarefa() {
		return dataTarefa;
	}

	public void setDataTarefa(LocalDateTime dataTarefa) {
		this.dataTarefa = dataTarefa;
	}

	/**
	 * Retorna o status da tarefa.
	 * 
	 * @return status tarefa.
	 */
	public StatusTarefa getStatusTarefa() {
		return statusTarefa;
	}

	public void setStatusTarefa(StatusTarefa statusTarefa) {
		this.statusTarefa = statusTarefa;
	}
	
	/**
	 * Define a ordenação dos status tarefa, de acordo com a 
	 * exibição de tarefas, exigida na tela.
	 * 
	 * @return a posição de ordenação, de acordo com o status.
	 */
	public Integer getOrdenacaoStatusTarefa(){
		if(statusTarefa == null){
			return 0;
		}
		else if(StatusTarefa.ATRIBUIDA.getId().equals(statusTarefa.getId())){
			return 1;
		}
		return -1;
	}
	
	@Override
	public String toString(){
		return "Paciente: " + rmr + " - " + nome + " / " + "(" + descricao + ")";
	}


	
	/**
	 * Recupera o Id da tarefa para ser tratada após o médico ser notificado.
	 * 
	 * @return the idTarefa
	 */
	public Long getIdTarefa() {
		return idTarefa;
	}


	
	/**
	 * Seta o Id da tarefa para ser tratada após o médico ser notificado.
	 * 
	 * @param idTarefa the idTarefa to set
	 */
	public void setIdTarefa(Long idTarefa) {
		this.idTarefa = idTarefa;
	}


	/**
	 * @return the medicoResponsavelPaciente
	 */
	public String getMedicoResponsavelPaciente() {
		return medicoResponsavelPaciente;
	}


	/**
	 * @param medicoResponsavelPaciente the medicoResponsavelPaciente to set
	 */
	public void setMedicoResponsavelPaciente(String medicoResponsavelPaciente) {
		this.medicoResponsavelPaciente = medicoResponsavelPaciente;
	}
	
	

}
