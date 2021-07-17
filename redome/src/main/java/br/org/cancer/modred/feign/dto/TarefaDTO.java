package br.org.cancer.modred.feign.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.JoinColumn;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.PageView;
import br.org.cancer.modred.controller.view.PedidoExameView;
import br.org.cancer.modred.controller.view.TarefaView;
import br.org.cancer.modred.model.domain.StatusTarefa;
import br.org.cancer.modred.model.domain.TiposTarefa;
import br.org.cancer.modred.model.security.Perfil;
import br.org.cancer.modred.model.security.Usuario;
import br.org.cancer.modred.util.DateUtils;

/**
 * Classe base para as tarefas.
 * 
 * @author bruno.sousa
 *
 */
public class TarefaDTO implements Serializable {

	/**
	 * Identificador de versão de serialização da classe.
	 */
	private static final long serialVersionUID = 1314559986072976863L;

	/**
	 * Chave que identifica com exclusividade uma instância desta classe.
	 */
	@JsonView(value = { TarefaView.Listar.class, TarefaView.Consultar.class,
			PageView.ListarPaginacao.class})
	protected Long id;

	/**
	 * Processo para o qual esta tarefa está associada.
	 */
	@JsonView(value = { TarefaView.Listar.class, TarefaView.Consultar.class, PageView.ListarPaginacao.class })
	protected ProcessoDTO processo;

	/**
	 * Data de criação da tarefa.
	 */
	@JsonView(value = { TarefaView.Consultar.class, PageView.ListarPaginacao.class })
	protected LocalDateTime dataCriacao;

	/**
	 * Data da última atualização desta tarefa.
	 */
	@JsonView(value = { TarefaView.Consultar.class })
	protected LocalDateTime dataAtualizacao;

	/**
	 * Identificação do tipo de tarefa a ser executada no processo de negócio.
	 */
	@JsonView(value = { TarefaView.Listar.class, TarefaView.Consultar.class, 
						PageView.ListarPaginacao.class, PedidoExameView.ListarTarefas.class })
	protected TipoTarefaDTO tipoTarefa;

	/**
	 * Estado da tarefa dentro do processo de negócio.
	 */
	@JsonView(value = { TarefaView.Listar.class, TarefaView.Consultar.class, PageView.ListarPaginacao.class })
	protected Long status;

	/**
	 * Perfil do responsável por executar esta tarefa.
	 */
	@JsonView(value = { TarefaView.Listar.class, TarefaView.Consultar.class, PageView.ListarPaginacao.class })
	protected Long perfilResponsavel;

	/**
	 * Médico para o qual foi conferida esta tarefa, ou seja, trata-se do indivíduo responsável por executar a tarefa.
	 */
	@JsonView(value = { TarefaView.Listar.class, TarefaView.Consultar.class, PageView.ListarPaginacao.class})
	protected Long usuarioResponsavel;

	/**
	 * Centro de transplante para o qual foi conferida esta tarefa, ou seja, trata-se da organização responsável por executar a
	 * tarefa.
	 */
	protected Long relacaoParceiro;
	
	
	@JsonView(value = { TarefaView.Listar.class, TarefaView.Consultar.class })
	@JsonProperty(access = Access.WRITE_ONLY)
	protected Object objetoRelacaoParceiro;

	/**
	 * Descrição da tarefa.
	 */
	
	@JsonView(value = { PageView.ListarPaginacao.class })
	protected String descricao;

	/**
	 * Id da entidade relacionada a tarefa.
	 */
	@JsonView(value = { TarefaView.Consultar.class, PageView.ListarPaginacao.class })
	protected Long relacaoEntidade;

	/**
	 * Atribuito para guardar objeto buscado através de relacao entidade.
	 */
	@JsonView(value = { TarefaView.Consultar.class, TarefaView.Listar.class, PageView.ListarPaginacao.class  })
//	@JsonProperty(access = Access.)
	protected Object objetoRelacaoEntidade;

	/**
	 * Data início para execução da tarefa.
	 */
	@Column(name = "TARE_DT_INICIO")
	protected LocalDateTime dataInicio;

	/**
	 * Data fim para execução da tarefa.
	 */
	@Column(name = "TARE_DT_FIM")
	protected LocalDateTime dataFim;

	/**
	 * Hora início para execução da tarefa.
	 */
	@Column(name = "TARE_DT_HORA_INICIO")
	protected LocalDateTime horaInicio;

	/**
	 * Hora fim para execução da tarefa.
	 */
	@Column(name = "TARE_DT_HORA_FIM")
	protected LocalDateTime horaFim;

	/**
	 * Define se a fatia de horário é inclusiva ou exclusiva.
	 */
	@Column(name = "TARE_IN_INCLUSIVO_EXCLUSIVO")
	protected Boolean inclusivoExclusivo;

	/**
	 * Indica se a tarefa foi agendada ou não.
	 */
	@Column(name = "TARE_IN_AGENDADO", insertable = false, updatable = false)
	protected boolean agendado;

	/**
	 * Guarda o último usuário responsável pela tarefa associada. Atributo é utilizado para tarefa ROLLBACK, onde o último
	 * responsável volta a ser responsável pela tarefa, após o tempo se esgotar.
	 */
	@Column(name = "USUA_ID_ULTIMO_RESPONSAVEL")
	protected Long ultimoUsuarioResponsavel;
	
	/**
	 * TarefaDTO pai.
	 */
	@JoinColumn(name = "TARE_ID_TAREFA_PAI")
	protected TarefaDTO tarefaPai;
	
	
	/**
	 * Usuário designado para realizar a tarefa em caso de um atendimento pré 
	 * estabelecido para alguém.
	 */
	protected Long usuarioResponsavelAgendamento;
	
	@JsonView(value = { TarefaView.Listar.class, TarefaView.Consultar.class, PageView.ListarPaginacao.class})
	protected String aging;
	
	
	/**
	 * Construtor da classe.
	 * 
	 * Toda tarefa deve ser criada associada a um processo de negócio, com a definição de "qual é a tarefa" e qual o perfil
	 * responsável por executá-la.
	 */
	public TarefaDTO() {
		super();
		this.dataCriacao = LocalDateTime.now();
		this.dataAtualizacao = LocalDateTime.now();
		this.setStatus(StatusTarefa.ABERTA.getId());
	}

	/**
	 * Construtor da classe.
	 * 
	 * Toda tarefa deve ser criada associada a um processo de negócio, com a definição de "qual é a tarefa" e qual o perfil
	 * responsável por executá-la.
	 * 
	 * @param processo - processo ao qual a tarefa deve pertencer
	 * @param tipoTarefa - tipo da tarefa
	 * @param perfilResponsavel - perfil responsável por executar a tarefa
	 */
	public TarefaDTO(ProcessoDTO processo, TiposTarefa tipoTarefa, Perfil perfilResponsavel) {
		this();
		this.tipoTarefa = tipoTarefa != null ? new TipoTarefaDTO(tipoTarefa.getId()) : null;
		this.processo = processo;
		if (perfilResponsavel != null) {
			this.perfilResponsavel = perfilResponsavel.getId();
		}
	}

	/**
	 * Construtor da classe.
	 * 
	 * Toda tarefa deve ser criada associada a um processo de negócio, com a definição de "qual é a tarefa" e qual o perfil
	 * responsável por executá-la.
	 * 
	 * @param processo - processo ao qual a tarefa deve pertencer
	 * @param tipoTarefa - tipo da tarefa
	 * @param perfilResponsavel - perfil responsável por executar a tarefa
	 * @param usuario - medico responsavel pela tarefa
	 * @param dataCriacao - data de criação da tarefa
	 */
	public TarefaDTO(ProcessoDTO processo, TiposTarefa tipoTarefa, Perfil perfilResponsavel, Usuario usuario,
			LocalDateTime dataCriacao) {
		this(processo, tipoTarefa, perfilResponsavel);
		this.usuarioResponsavel = usuario != null ?  usuario.getId() : null;
		this.dataCriacao = dataCriacao;
	}

	/**
	 * @param id
	 */
	public TarefaDTO(Long id) {
		this.id = id;
	}

	/**
	 * Método para obter a chave que identifica com exclusividade uma instância desta classe.
	 * 
	 * @return Long - id da tarefa
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Método para definir a chave que identifica com exclusividade uma instância desta classe.
	 * 
	 * @param id - id da tarefa
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Método para obter a data de criação da tarefa.
	 * 
	 * @return LocalDateTime - data de criação da tarefa
	 */
	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	/**
	 * Método para definir a data de criação da tarefa.
	 * 
	 * @param dataCriacao - data de criação da tarefa
	 */
	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	/**
	 * Método para obter a data da última atualização da tarefa.
	 * 
	 * @return LocalDateTime - data da última atualização da tarefa.
	 */
	public LocalDateTime getDataAtualizacao() {
		return dataAtualizacao;
	}

	/**
	 * Método para definir a data da última atualização da tarefa.
	 * 
	 * @param dataAtualizacao - data da última atualização da tarefa.
	 */
	public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	/**
	 * Método para obter a identificação do tipo de tarefa a ser executada no processo de negócio.
	 * 
	 * @return TipoTarefa - tipo da tarefa
	 */
	public TipoTarefaDTO getTipoTarefa() {
		return tipoTarefa;
	}

	/**
	 * Método para definir a identificação do tipo de tarefa a ser executada no processo de negócio.
	 * 
	 * @param tipo - tipo da tarefa
	 */
	public void setTipoTarefa(TipoTarefaDTO tipoTarefa) {
		this.tipoTarefa = tipoTarefa;
	}

	/**
	 * Método para obter o estado da tarefa dentro do processo de negócio.
	 * 
	 * @return StatusTarefa - o estado da tarefa dentro do processo de negócio.
	 */
	public Long getStatus() {
		return status;
	}

	/**
	 * Método para definir o estado da tarefa dentro do processo de negócio.
	 * 
	 * @param status - o estado da tarefa dentro do processo de negócio.
	 */
	public void setStatus(Long status) {
		this.status = status;
	}

	/**
	 * Método para obter o perfil do responsável por executar esta tarefa.
	 * 
	 * @return Perfil - o perfil do responsável por executar esta tarefa.
	 */
	public Long getPerfilResponsavel() {
		return perfilResponsavel;
	}

	/**
	 * Método para definir o perfil do responsável por executar esta tarefa.
	 * 
	 * @param perfilResponsavel - o perfil do responsável por executar esta tarefa.
	 */
	public void setPerfilResponsavel(Long perfilResponsavel) {
		this.perfilResponsavel = perfilResponsavel;
	}

	/**
	 * Método para obter o usuário para o qual esta tarefa foi conferida, ou seja, o indivíduo responsável por executar esta
	 * tarefa.
	 * 
	 * @return Medico - o médico responsável por executar esta tarefa.
	 */
	public Long getUsuarioResponsavel() {
		return usuarioResponsavel;
	}

	/**
	 * Método para definir o médico para o qual esta tarefa foi conferida, ou seja, o indivíduo responsável por executar esta
	 * tarefa.
	 * 
	 * @param usuarioResponsavel - o médico responsável por executar esta tarefa.
	 */
	public void setUsuarioResponsavel(Long usuarioResponsavel) {
		this.usuarioResponsavel = usuarioResponsavel;
	}

	/**
	 * Método para obter o parceiro para o qual esta tarefa foi conferida, ou seja, a organização responsável por
	 * executar esta tarefa.
	 * 
	 * @return Object - o parceiro responsável por executar esta tarefa.
	 */
	public Object getObjetoRelacaoParceiro() {
		return objetoRelacaoParceiro;
	}

	/**
	 * Método para definir o parceiro para o qual esta tarefa foi conferida, ou seja, a organização responsável por
	 * executar esta tarefa. 
	 * 
	 * @param objetoRelacaoParceiro
	 */
	public void setObjetoRelacaoParceiro(Object objetoRelacaoParceiro) {		
		this.objetoRelacaoParceiro = objetoRelacaoParceiro;		
	}

	/**
	 * Método para obter o processo para o qual esta tarefa está associada.
	 * 
	 * @return Processo - o processo para o qual esta tarefa está associada.
	 */
	public ProcessoDTO getProcesso() {
		return processo;
	}

	/**
	 * Método para definir o processo para o qual esta tarefa está associada.
	 * 
	 * @return processo - o processo para o qual esta tarefa está associada.
	 */
	public void setProcesso(ProcessoDTO processo) {
		this.processo = processo;
	}

	/**
	 * Retorna a descricao da tarefa.
	 * 
	 * @return a descricao
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * Seta a descricao da tarefa.
	 * 
	 * @param descricao the descricao to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * @return the relacaoEntidade
	 */
	public Long getRelacaoEntidade() {
		return relacaoEntidade;
	}

	/**
	 * @param relacaoEntidade the relacaoEntidade to set
	 */
	public void setRelacaoEntidade(Long relacaoEntidade) {
		this.relacaoEntidade = relacaoEntidade;
	}

	/**
	 * @return the objetoRelacaoEntidade
	 */
	public Object getObjetoRelacaoEntidade() {
		return objetoRelacaoEntidade;
	}

	/**
	 * @param objetoRelacaoEntidade the objetoRelacaoEntidade to set
	 */
	public void setObjetoRelacaoEntidade(Object objetoRelacaoEntidade) {
		this.objetoRelacaoEntidade = objetoRelacaoEntidade;
	}

	/**
	 * @return the dataInicio
	 */
	public LocalDateTime getDataInicio() {
		return dataInicio;
	}

	/**
	 * @param dataInicio the dataInicio to set
	 */
	public void setDataInicio(LocalDateTime dataInicio) {
		this.dataInicio = dataInicio;
	}

	/**
	 * @return the dataFim
	 */
	public LocalDateTime getDataFim() {
		return dataFim;
	}

	/**
	 * @param dataFim the dataFim to set
	 */
	public void setDataFim(LocalDateTime dataFim) {
		this.dataFim = dataFim;
	}

	/**
	 * @return the horaInicio
	 */
	public LocalDateTime getHoraInicio() {
		return horaInicio;
	}

	/**
	 * @param horaInicio the horaInicio to set
	 */
	public void setHoraInicio(LocalDateTime horaInicio) {
		this.horaInicio = horaInicio;
	}

	/**
	 * @return the horaFim
	 */
	public LocalDateTime getHoraFim() {
		return horaFim;
	}

	/**
	 * @param horaFim the horaFim to set
	 */
	public void setHoraFim(LocalDateTime horaFim) {
		this.horaFim = horaFim;
	}

	/**
	 * @return the inclusivoExclusivo
	 */
	public Boolean getInclusivoExclusivo() {
		return inclusivoExclusivo;
	}

	/**
	 * @param inclusivoExclusivo the inclusivoExclusivo to set
	 */
	public void setInclusivoExclusivo(Boolean inclusivoExclusivo) {
		this.inclusivoExclusivo = inclusivoExclusivo;
	}

	/**
	 * @return the agendado
	 */
	public boolean isAgendado() {
		return agendado;
	}

	/**
	 * Método para mostrar a representação textual das instâncias desta classe.
	 */
	@Override
	public String toString() {
		StringBuffer output = new StringBuffer("tarefa id");
		output.append("=[").append(id).append("], tipo tarefa=[").append(tipoTarefa).append("], processo=[")
				.append(( processo != null ) ? processo.getId() : null).append("], status=[").append(status)
				.append("], perfil responsavel=[").append(( perfilResponsavel != null ) ? perfilResponsavel : null)
				.append("], usuario responsavel=[").append(( usuarioResponsavel != null ) ? usuarioResponsavel : null)
				.append("], parceiro responsavel=[").append(( relacaoParceiro != null )
						? relacaoParceiro : null).append("], relação entidade=")
				.append("], data criação=[").append(dataCriacao)
				.append("], ultima atualizacao=[").append(dataAtualizacao).append("]");
		return output.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( relacaoParceiro == null ) ? 0 : relacaoParceiro.hashCode() );
		result = prime * result + ( ( dataAtualizacao == null ) ? 0 : dataAtualizacao.hashCode() );
		result = prime * result + ( ( dataCriacao == null ) ? 0 : dataCriacao.hashCode() );
		result = prime * result + ( ( dataFim == null ) ? 0 : dataFim.hashCode() );
		result = prime * result + ( ( dataInicio == null ) ? 0 : dataInicio.hashCode() );
		result = prime * result + ( ( descricao == null ) ? 0 : descricao.hashCode() );
		result = prime * result + ( ( horaFim == null ) ? 0 : horaFim.hashCode() );
		result = prime * result + ( ( horaInicio == null ) ? 0 : horaInicio.hashCode() );
		result = prime * result + ( ( id == null ) ? 0 : id.hashCode() );
		result = prime * result + ( ( inclusivoExclusivo == null ) ? 0 : inclusivoExclusivo.hashCode() );
		result = prime * result + ( ( perfilResponsavel == null ) ? 0 : perfilResponsavel.hashCode() );
		result = prime * result + ( ( status == null ) ? 0 : status.hashCode() );
		result = prime * result + ( ( tipoTarefa == null ) ? 0 : tipoTarefa.hashCode() );
		result = prime * result + ( ( usuarioResponsavel == null ) ? 0 : usuarioResponsavel.hashCode() );
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!( obj instanceof TarefaDTO )) {
			return false;
		}
		TarefaDTO other = (TarefaDTO) obj;
		if (relacaoParceiro == null) {
			if (other.relacaoParceiro != null) {
				return false;
			}
		}
		else
			if (!relacaoParceiro.equals(other.relacaoParceiro)) {
				return false;
			}
		if (dataAtualizacao == null) {
			if (other.dataAtualizacao != null) {
				return false;
			}
		}
		else
			if (!dataAtualizacao.equals(other.dataAtualizacao)) {
				return false;
			}
		if (dataCriacao == null) {
			if (other.dataCriacao != null) {
				return false;
			}
		}
		else
			if (!dataCriacao.equals(other.dataCriacao)) {
				return false;
			}
		if (dataFim == null) {
			if (other.dataFim != null) {
				return false;
			}
		}
		else
			if (!dataFim.equals(other.dataFim)) {
				return false;
			}
		if (dataInicio == null) {
			if (other.dataInicio != null) {
				return false;
			}
		}
		else
			if (!dataInicio.equals(other.dataInicio)) {
				return false;
			}
		if (descricao == null) {
			if (other.descricao != null) {
				return false;
			}
		}
		else
			if (!descricao.equals(other.descricao)) {
				return false;
			}
		if (horaFim == null) {
			if (other.horaFim != null) {
				return false;
			}
		}
		else
			if (!horaFim.equals(other.horaFim)) {
				return false;
			}
		if (horaInicio == null) {
			if (other.horaInicio != null) {
				return false;
			}
		}
		else
			if (!horaInicio.equals(other.horaInicio)) {
				return false;
			}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		}
		else
			if (!id.equals(other.id)) {
				return false;
			}
		if (inclusivoExclusivo == null) {
			if (other.inclusivoExclusivo != null) {
				return false;
			}
		}
		else
			if (!inclusivoExclusivo.equals(other.inclusivoExclusivo)) {
				return false;
			}
		if (perfilResponsavel == null) {
			if (other.perfilResponsavel != null) {
				return false;
			}
		}
		else
			if (!perfilResponsavel.equals(other.perfilResponsavel)) {
				return false;
			}
		if (status == null) {
			if (other.status != null) {
				return false;
			}
		}
		else
			if (!status.equals(other.status)) {
				return false;
			}
		if (tipoTarefa == null) {
			if (other.tipoTarefa != null) {
				return false;
			}
		}
		else
			if (!tipoTarefa.equals(other.tipoTarefa)) {
				return false;
			}
		if (usuarioResponsavel == null) {
			if (other.usuarioResponsavel != null) {
				return false;
			}
		}
		else
			if (!usuarioResponsavel.equals(other.usuarioResponsavel)) {
				return false;
			}
		return true;
	}

	public Long getUltimoUsuarioResponsavel() {
		return ultimoUsuarioResponsavel;
	}

	public void setUltimoUsuarioResponsavel(Long ultimoUsuarioResponsavel) {
		this.ultimoUsuarioResponsavel = ultimoUsuarioResponsavel;
	}

	/**
	 * @return the aging
	 */
	public String getAging() {
		if(this.dataCriacao == null){
			return "";
		}
		return DateUtils.obterAging(this.dataCriacao);
	}

	
	/**
	 * @param aging the aging to set
	 */
	public void setAging(String aging) {
		this.aging = aging;
	}

	
	/**
	 * @return the relacaoParceiro
	 */
	public Long getRelacaoParceiro() {
		return relacaoParceiro;
	}

	
	/**
	 * @param relacaoParceiro the relacaoParceiro to set
	 */
	public void setRelacaoParceiro(Long relacaoParceiro) {
		this.relacaoParceiro = relacaoParceiro;
	}
	
	/**
	 * Indica se a tarefa ainda está em andamento ou não.
	 * 
	 * @return TRUE se ainda está sendo realizada, FALSE caso já tenha finalizado.
	 */
	public boolean emAndamento(){
		return StatusTarefa.ABERTA.getId().equals(getStatusTarefa().getId()) || StatusTarefa.ATRIBUIDA.getId().equals(getStatusTarefa().getId());
	}

	
	/**
	 * @return the tarefaPai
	 */
	public TarefaDTO getTarefaPai() {
		return tarefaPai;
	}

	
	/**
	 * @param tarefaPai the tarefaPai to set
	 */
	public void setTarefaPai(TarefaDTO tarefaPai) {
		this.tarefaPai = tarefaPai;
	}

	/**
	 * @param agendado the agendado to set
	 */
	public void setAgendado(boolean agendado) {
		this.agendado = agendado;
	}

	/**
	 * @return the usuarioResponsavelAgendamento
	 */
	public Long getUsuarioResponsavelAgendamento() {
		return usuarioResponsavelAgendamento;
	}

	/**
	 * @param usuarioResponsavelAgendamento the usuarioResponsavelAgendamento to set
	 */
	public void setUsuarioResponsavelAgendamento(Long usuarioResponsavelAgendamento) {
		this.usuarioResponsavelAgendamento = usuarioResponsavelAgendamento;
	}
	
	@JsonIgnore
	public TiposTarefa getTipo() {
		if (tipoTarefa == null) {
			return null;
		}
		return TiposTarefa.valueOf(tipoTarefa.getId());
	}
	
	public StatusTarefa getStatusTarefa() {
		if (status == null) {
			return null;
		}
		return StatusTarefa.valueOf(status);
	}


	
}
