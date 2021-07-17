package br.org.cancer.modred.feign.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.EncontrarCentroTransplanteView;
import br.org.cancer.modred.controller.view.PageView;
import br.org.cancer.modred.controller.view.ProcessoView;
import br.org.cancer.modred.controller.view.TarefaLogisticaView;
import br.org.cancer.modred.controller.view.TarefaView;
import br.org.cancer.modred.model.Doador;
import br.org.cancer.modred.model.Paciente;
import br.org.cancer.modred.model.annotations.EnumValues;
import br.org.cancer.modred.model.domain.StatusProcesso;
import br.org.cancer.modred.model.domain.TipoProcesso;

/**
 * 
 * Esta classe é uma abstração para representar processos dentro da plataforma redome . Tal processo é compreendido por tarefas
 * executadas de maneira sequencial ou em paralelo, e que representam ações lógicas e claras para gerar seus resultados de forma
 * independente.
 * 
 * Os processos possuem começo e fim determinados.
 * 
 * @author Thiago Moraes
 *
 */

public class ProcessoDTO implements Serializable {

	/**
	 * Identificador de versão de serialização da classe.
	 */
	private static final long serialVersionUID = -583157896227858302L;

	/**
	 * Chave que identifica com exclusividade uma instância desta classe.
	 */
	@JsonView(value = { ProcessoView.Listar.class, ProcessoView.Consultar.class, TarefaView.Listar.class,
			TarefaView.Consultar.class , TarefaLogisticaView.Listar.class,PageView.ListarPaginacao.class})
	private Long id;

	/**
	 * Data de criação do processo.
	 */
	@JsonView(value = { ProcessoView.Consultar.class })
	private LocalDateTime dataCriacao;

	/**
	 * Data da última atualização deste processo.
	 */
	@JsonView(value = { ProcessoView.Consultar.class })
	private LocalDateTime dataAtualizacao;

	/**
	 * Identificação do tipo de processo.
	 */
	@EnumValues(TipoProcesso.class)
	@JsonView(value = { ProcessoView.Listar.class, ProcessoView.Consultar.class })
	private Long tipo;

	
	private Long rmr;
	
	private String nomePaciente;
	
	/**
	 * Paciente para o qual este processo de busca está associado.
	 */
	@JsonView(value = { ProcessoView.Listar.class, ProcessoView.Consultar.class, TarefaView.Listar.class, EncontrarCentroTransplanteView.ListarBuscas.class, PageView.ListarPaginacao.class })
	private Paciente paciente;

	private Long idDoador;
	
	private String nomeDoador;
	
	/**
	 * Doador para o qual este processo de busca está associado.
	 */
	@JsonView(value = { ProcessoView.Listar.class, ProcessoView.Consultar.class, TarefaView.Listar.class, EncontrarCentroTransplanteView.ListarBuscas.class, PageView.ListarPaginacao.class })
	private Doador doador;
	
	/**
	 * Estado atual do processo.
	 */
	@JsonView(value = { ProcessoView.Listar.class, ProcessoView.Consultar.class })
	private Long status;

	/**
	 * Construtor da classe.
	 * 
	 * Todo processo deve ter seu o seu tipo identificado. Para determinados processos (ex. processo de busca de doador), é
	 * mandatório a definição do paciente associado.
	 */
	public ProcessoDTO() {
		super();
		this.dataCriacao = LocalDateTime.now();
		this.dataAtualizacao = LocalDateTime.now();
		this.setStatus(StatusProcesso.ANDAMENTO.getId());
	}

	/**
	 * Construtor da classe.
	 * 
	 * Todo processo deve ter seu o seu tipo identificado. Para determinados processos (ex. processo de busca de doador), é
	 * mandatório a definição do paciente associado.
	 */
	public ProcessoDTO(TipoProcesso tipo) {
		this();
		this.setTipo(tipo.getId());
	}
	
	public ProcessoDTO(Long id, TipoProcesso tipo) {
		this(tipo);
		this.id = id;
	}
	

	/**
	 * Método para obter a chave que identifica com exclusividade uma instância desta classe.
	 * 
	 * @return Long - id do processo
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Método para definir a chave que identifica com exclusividade uma instância desta classe.
	 * 
	 * @param id - chave do processo
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Método para obter a data de criação do processo.
	 * 
	 * @return LocalDateTime - data de criação do processo
	 */
	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	/**
	 * Método para definir a data de criação do processo.
	 * 
	 * @param dataCriacao - data de criação do processo
	 */
	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	/**
	 * Método para obter a data da última atualização do processo.
	 * 
	 * @return LocalDateTime - data da última atualização do processo
	 */
	public LocalDateTime getDataAtualizacao() {
		return dataAtualizacao;
	}

	/**
	 * Método para definir a data da última atualização do processo.
	 * 
	 * @param dataAtualizacao - data da última atualização do processo
	 */
	public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	/**
	 * Método para obter o estado atual do processo de busca de doadoar compativel.
	 * 
	 * @return StatusProcesso - o estado atual do processo.
	 */
	public Long getStatus() {
		return status;
	}
	
	public void setStatus(Long status) {
		this.status = status;
	}

	/**
	 * Método para obter o paciente para o qual este processo de busca está relacionado.
	 * 
	 * @return Paciente - o paciente para o qual este processo de busca está relacionado.
	 */
	public Paciente getPaciente() {
		return paciente;
	}

	/**
	 * Método para definir o paciente para o qual este processo de busca está relacionado.
	 * 
	 * @param Paciente - o paciente para o qual este processo de busca está relacionado.
	 */
	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
		this.rmr = this.paciente != null && this.paciente.getRmr() != null ? this.paciente.getRmr() : null;		
	}

	/**
	 * Método para obter a identificação do tipo de processo.
	 * 
	 * @return TipoProcesso - a identificação do tipo de processo.
	 */
	public Long getTipo() {
		return tipo;
	}
	
	public void setTipo(Long tipo) {
		this.tipo = tipo;
	}
	
	/**
	 * @return the doador
	 */
	public Doador getDoador() {
		return doador;
	}

	/**
	 * @param doador the doador to set
	 */
	public void setDoador(Doador doador) {
		this.doador = doador;
		this.idDoador = this.doador != null ? this.doador.getId() : null;
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
	public void setRmr(Long rmr) {
		this.rmr = rmr;
		this.paciente = this.rmr != null ? new Paciente(rmr) : null;
	}

	/**
	 * @return the idDoador
	 */
	public Long getIdDoador() {
		return idDoador;
	}

	/**
	 * @param idDoador the idDoador to set
	 */
	public void setIdDoador(Long idDoador) {
		this.idDoador = idDoador;
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
	 * @return the nomeDoador
	 */
	public String getNomeDoador() {
		return nomeDoador;
	}

	/**
	 * @param nomeDoador the nomeDoador to set
	 */
	public void setNomeDoador(String nomeDoador) {
		this.nomeDoador = nomeDoador;
	}

	/**
	 * Método para mostrar a representação textual das instâncias desta classe.
	 */
	@Override
	public String toString() {
		StringBuffer output = new StringBuffer("processo id");
		output.append("=[").append(id).append("], tipo=[").append(tipo).append("], paciente=[").append(( paciente != null )
				? paciente.getRmr() : null).append("], status=[").append(status).append("], data criação=[").append(dataCriacao)
				.append("]").append("], ultima atualizacao=[").append(dataAtualizacao).append("]");
		return output.toString();
	}

}
