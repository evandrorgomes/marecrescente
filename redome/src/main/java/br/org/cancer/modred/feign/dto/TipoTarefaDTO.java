package br.org.cancer.modred.feign.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.PageView;
import br.org.cancer.modred.controller.view.PedidoExameView;
import br.org.cancer.modred.controller.view.TarefaLogisticaView;
import br.org.cancer.modred.controller.view.TarefaView;
import br.org.cancer.modred.controller.view.TransportadoraView;
import br.org.cancer.modred.helper.IConfiguracaoProcessServer;
import br.org.cancer.modred.model.domain.TiposTarefa;

/**
 * Classe que representa o tipo de tarefa.
 * 
 * @author bruno.sousa
 */
public class TipoTarefaDTO implements Serializable {

	private static final long serialVersionUID = 4557443107551121549L;

	@JsonView(value = { TarefaView.Listar.class, TarefaLogisticaView.Listar.class, 
						TransportadoraView.Listar.class, PedidoExameView.ListarTarefas.class,
						TransportadoraView.AgendarTransporte.class})
	private Long id;

	private String descricao;

	private Boolean automatica;

	private Long tempoExecucao;
	
	private Boolean somenteAgrupador;
	

	/**
	 * Construtor padrão.
	 */
	public TipoTarefaDTO() {}

	/**
	 * Construtor passsando o id.
	 * 
	 * @param id
	 */
	public TipoTarefaDTO(Long id) {
		super();
		this.id = id;
	}

	/**
	 * Retorna a chave primaria do tipo da tarefa.
	 * 
	 * @return id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Seta a chave primaria do tipo da tarefa.
	 * 
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Retorna a descricao do tipo de tarefa.
	 * 
	 * @return descricao
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * Seta a descricao do tipo de tarefa.
	 * 
	 * @param descricao
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * Retorna se o tipo de tarefa é automatica.
	 * 
	 * @return the automatica
	 */
	public Boolean getAutomatica() {
		return automatica;
	}

	/**
	 * Seta se o tipo de tarefa será automatica.
	 * 
	 * @param automatica the automatica to set
	 */
	public void setAutomatica(Boolean automatica) {
		this.automatica = automatica;
	}

	/**
	 * Retorna o tempo da execução do tipo de tarefa automatica.
	 * 
	 * @return the tempoExecucao
	 */
	public Long getTempoExecucao() {
		return tempoExecucao;
	}

	/**
	 * Seta o tempo da execução do tipo de tarefa automatica.
	 * 
	 * @param tempoExecucao the tempoExecucao to set
	 */
	public void setTempoExecucao(Long tempoExecucao) {
		this.tempoExecucao = tempoExecucao;
	}

	/**
	 * @return configuração para execução e tratamento desse tipo de tarefa.
	 */
	public IConfiguracaoProcessServer getConfiguracao() {
		TiposTarefa tipo = TiposTarefa.valueOf(id);

		if (tipo != null) {
			if(id != null){
				return tipo.getConfiguracao();	
			}
			return tipo.getConfiguracao();
		}
		

		return null;
	}
	
	@JsonView(PageView.ListarPaginacao.class)
	public String getClassEntidadeRelacionamento() {
		return getConfiguracao().getEntidade().getSimpleName();
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
		result = prime * result + ( ( automatica == null ) ? 0 : automatica.hashCode() );
		result = prime * result + ( ( descricao == null ) ? 0 : descricao.hashCode() );
		result = prime * result + ( ( id == null ) ? 0 : id.hashCode() );
		result = prime * result + ( ( tempoExecucao == null ) ? 0 : tempoExecucao.hashCode() );
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
		if (!( obj instanceof TipoTarefaDTO )) {
			return false;
		}
		TipoTarefaDTO other = (TipoTarefaDTO) obj;
		if (automatica == null) {
			if (other.automatica != null) {
				return false;
			}
		}
		else
			if (!automatica.equals(other.automatica)) {
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
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		}
		else
			if (!id.equals(other.id)) {
				return false;
			}
		if (tempoExecucao == null) {
			if (other.tempoExecucao != null) {
				return false;
			}
		}
		else
			if (!tempoExecucao.equals(other.tempoExecucao)) {
				return false;
			}
		return true;
	}

	/**
	 * Indica se o tipo de tarefa é somente um agrupador de outros tipos.
	 * Na prática, esse tipo é gera tarefas, apenas exibe a lista de 
	 * um grupo de tipos pré definidos.
	 * 
	 * @return TRUE se é usado somente para agrupador outros tipos.
	 */
	public Boolean getSomenteAgrupador() {
		return somenteAgrupador;
	}

	public void setSomenteAgrupador(Boolean agrupador) {
		this.somenteAgrupador = agrupador;
	}

}