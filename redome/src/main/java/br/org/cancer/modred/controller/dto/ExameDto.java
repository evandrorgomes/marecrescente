package br.org.cancer.modred.controller.dto;

import java.time.format.DateTimeFormatter;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.ExameView;
import br.org.cancer.modred.model.Exame;
import br.org.cancer.modred.model.ExameDoadorNacional;
import br.org.cancer.modred.model.ExamePaciente;

/**
 * Classe Vo para dados da tela de Evolução.
 * 
 * @author Filipe Paes
 *
 */

public class ExameDto {

	/**
	 * Lista de evoluções.
	 */
	@JsonView(ExameView.ListaExame.class)
	private List<Exame> exames;
	/**
	 * Última evolução.
	 */
	@JsonView({ExameView.ListaExame.class,ExameView.ConferirExame.class})
	private Exame exameSelecionado;
	
	@JsonView(ExameView.ListaExame.class)
	private String usernameMedicoResponsavel;
	
	/**
	 * Rmr do paciente atrelado ao exame.
	 */
	@JsonView(ExameView.ConferirExame.class)
	private Long rmrPaciente;
	
	@JsonView(ExameView.ListaExame.class)
	private boolean pacienteEmObito;
	
	@JsonView(ExameView.ListaExame.class)
	private boolean buscaEmMatch;
	
	@JsonView(ExameView.ListaExame.class)
	private boolean temPedidoExameCT = false;
	
	@JsonView(ExameView.ListaExame.class)
	private Long idTipoExame;
	
	@JsonView(ExameView.ListaExame.class)
	private Long idBusca;
	
	/**
	 * Retorna a lista de exames.
	 * 
	 * @return
	 */
	public List<Exame> getExames() {
		return exames;
	}

	/**
	 * Seta a lista de exames.
	 * 
	 * @param exames
	 */
	public void setExames(List<Exame> exames) {
		this.exames = exames;
	}

	/**
	 * Retorna o exame selecionado.
	 * 
	 * @return
	 */
	public Exame getExameSelecionado() {
		return exameSelecionado;
	}

	/**
	 * Seta o exame selecionado.
	 * 
	 * @param exameSelecionado
	 */
	public void setExameSelecionado(Exame exameSelecionado) {
		this.exameSelecionado = exameSelecionado;
	}

	
	/**
	 * @return the usernameMedicoResponsavel
	 */
	public String getUsernameMedicoResponsavel() {
		return usernameMedicoResponsavel;
	}

	
	/**
	 * @param usernameMedicoResponsavel the usernameMedicoResponsavel to set
	 */
	public void setUsernameMedicoResponsavel(String usernameMedicoResponsavel) {
		this.usernameMedicoResponsavel = usernameMedicoResponsavel;
	}

	/**
	 * @return the rmrPaciente
	 */
	public Long getRmrPaciente() {
		return rmrPaciente;
	}

	/**
	 * @param rmrPaciente the rmrPaciente to set
	 */
	public void setRmrPaciente(Long rmrPaciente) {
		this.rmrPaciente = rmrPaciente;
	}
	
	/**Método auxiliar para devolver a data como string.
	 * No front a data está sendo exibida com um dia a menos.
	 * @return data formatada
	 */
	@JsonView({ExameView.ConferirExame.class})
	@JsonProperty(access = Access.READ_ONLY)
	public String getDataColetaAmostraFormatada() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
		if (exameSelecionado instanceof ExamePaciente) {
			return ((ExamePaciente)exameSelecionado).getDataColetaAmostra().format(formatter);
		}
		else {
			return null;
		}
		
	}
		
	/**Método auxiliar para devolver a data como string.
	 * No front a data está sendo exibida com um dia a menos.
	 * @return data formatada
	 */
	@JsonView({ExameView.ConferirExame.class})
	@JsonProperty(access = Access.READ_ONLY)
	public String getDataExameFormatada() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
		if (exameSelecionado instanceof ExamePaciente) {
			return ((ExamePaciente)exameSelecionado).getDataExame().format(formatter);
		}
		else if (exameSelecionado instanceof ExameDoadorNacional) {
			return ((ExameDoadorNacional)exameSelecionado).getDataExame().format(formatter);
		}
		else {
			return null;
		}
	}
	
	/**
	 * Retorna true se paciente está em óbito.
	 * 
	 * @return
	 */
	public boolean isPacienteEmObito() {
		return pacienteEmObito;
	}

	/**
	 * Seta se o paciente está em óbito.
	 * 
	 * @param pacienteEmObito
	 */
	public void setPacienteEmObito(boolean pacienteEmObito) {
		this.pacienteEmObito = pacienteEmObito;
	}

	/**
	 * Indica se a busca já está com match selecionado.
	 * Caso esteja, paciente não poderá receber novos exames.
	 * 
	 * @return TRUE, se a busca está com match em andamento.
	 */
	public boolean isBuscaEmMatch() {
		return buscaEmMatch;
	}

	public void setBuscaEmMatch(boolean buscaEmMatch) {
		this.buscaEmMatch = buscaEmMatch;
	}

	
	/**
	 * @return the temPedidoExameCT
	 */
	public boolean isTemPedidoExameCT() {
		return temPedidoExameCT;
	}

	
	/**
	 * @param temPedidoExameCT the temPedidoExameCT to set
	 */
	public void setTemPedidoExameCT(boolean temPedidoExameCT) {
		this.temPedidoExameCT = temPedidoExameCT;
	}

	
	/**
	 * @return the idBusca
	 */
	public Long getIdBusca() {
		return idBusca;
	}

	
	/**
	 * @param idBusca the idBusca to set
	 */
	public void setIdBusca(Long idBusca) {
		this.idBusca = idBusca;
	}

	/**
	 * @return the idTipoExame
	 */
	public Long getIdTipoExame() {
		return idTipoExame;
	}

	/**
	 * @param idTipoExame the idTipoExame to set
	 */
	public void setIdTipoExame(Long idTipoExame) {
		this.idTipoExame = idTipoExame;
	}

	
	
}
