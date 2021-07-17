package br.org.cancer.modred.model.security;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.TarefaLogisticaView;
import br.org.cancer.modred.controller.view.TarefaView;
import br.org.cancer.modred.controller.view.UsuarioView;
import net.sf.ehcache.pool.sizeof.annotations.IgnoreSizeOf;

/**
 * Classe que representa os perfis do usuário.
 * 
 * @author bruno.sousa
 *
 */
@Entity
@Table(name = "PERFIL")
@SequenceGenerator(name = "SQ_PERF_ID", sequenceName = "SQ_PERF_ID", allocationSize = 1)
@NamedQuery(name = "Perfil.findAll", query = "SELECT p FROM Perfil p")
public class Perfil implements Serializable {

	private static final long serialVersionUID = 2720309670982841027L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_PERF_ID")
	@Column(name = "PERF_ID")
	@JsonView(value = { TarefaView.Listar.class, TarefaLogisticaView.Listar.class, UsuarioView.Consultar.class })
	private Long id;

	@JsonView(UsuarioView.Consultar.class)
	@Column(name = "PERF_TX_DESCRICAO")
	private String descricao;

	@ManyToMany(mappedBy = "perfis", fetch = FetchType.LAZY)
	@JsonIgnore
	@IgnoreSizeOf
	private List<Usuario> usuarios;

	@ManyToMany(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinTable(	name = "PERMISSAO",
				joinColumns =
	{ @JoinColumn(name = "PERF_ID") },
				inverseJoinColumns =
	{ @JoinColumn(name = "RECU_ID") })
	@JsonIgnore
	@IgnoreSizeOf
	private List<Recurso> recursos;

	/**
	 * Quantidade de usuários que possue este perfil.
	 */
	private transient int qtUsuarios;
	
	// Indica a qual sistema (parceiro) o perfil está associado.
	@ManyToOne
	@JoinColumn(name = "SIST_ID")
	@NotNull
	@JsonView(UsuarioView.Consultar.class)
	private Sistema sistema;
	
	

	/**
	 * Método construtor da classe Perfil.
	 * 
	 * @return Perfil - instância da classe Perfil.
	 */
	public Perfil() {
		super();
	}

	/**
	 * Construtor sobrecarregado para instanciar um perfil com ID pré definido.
	 * 
	 * @param id ID do perfil.
	 */
	public Perfil(Long id) {
		this();
		this.id = id;
	}

	
	public Perfil(String descricao) {
		super();
		this.descricao = descricao;
	}

	/**
	 * Método para obter a chave que identifica com exclusividade cada instância desta classe.
	 * 
	 * @return Long - a chave de identificação da instância da classe.
	 */
	public Long getId() {
		return this.id;
	}

	/**
	 * Método para definir a chave que identifica com exclusividade a instância desta classe.
	 * 
	 * @param id - a chave de identificação da instância da classe.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Método para obter a descrição do perfil.
	 * 
	 * @return String - a descrição do perfil.
	 */
	public String getDescricao() {
		return this.descricao;
	}

	/**
	 * Método para definir a descrição do perfil.
	 * 
	 * @param descrição - a descrição do perfil.
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * Método para obter a lista de usuários associados ao perfil.
	 * 
	 * @return List<Usuario> - lista com os usuários.
	 */
	public List<Usuario> getUsuarios() {
		return this.usuarios;
	}

	/**
	 * Método para definir os usuários associados.
	 * 
	 * @param List<Usuario> - Lista de usuários.
	 */
	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	/**
	 * @return the recursos
	 */
	public List<Recurso> getRecursos() {
		return recursos;
	}

	/**
	 * @param recursos the recursos to set
	 */
	public void setRecursos(List<Recurso> recursos) {
		this.recursos = recursos;
	}

	/**
	 * Método para obter o total de usuários ativos que possue este perfil.
	 * 
	 * @return int - total de usuários ativos que possue este perfil.
	 */
	public int getQtUsuarios() {
		return qtUsuarios;
	}

	/**
	 * Método para definir o total de usuários ativos que possue este perfil.
	 * 
	 * @param qtUsuarios - total de usuários ativos que possue este perfil.
	 */
	public void setQtUsuarios(int qtUsuarios) {
		this.qtUsuarios = qtUsuarios;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( id == null ) ? 0 : id.hashCode() );
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Perfil other = (Perfil) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		}
		else
			if (!id.equals(other.id)) {
				return false;
			}
		return true;
	}

	/**
	 * Identifica quem é o sistema associado ao perfil.
	 * 
	 * @return entidade de sistema que está associado.
	 */
	public Sistema getSistema() {
		return sistema;
	}

	public void setSistema(Sistema sistema) {
		this.sistema = sistema;
	}
	
	@Override
	public String toString(){
		return "(" + sistema.getNome() + ") - " + descricao;
	}

}