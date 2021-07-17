package br.org.cancer.modred.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.DoadorView;
import br.org.cancer.modred.model.security.Usuario;


/**
 * Evoluções do doador que são cadastradas paulatinamente pelo médico.
 * @author Filipe Paes
 *
 */
@Entity
@SequenceGenerator(name = "SQ_EVDO_ID", sequenceName = "SQ_EVDO_ID", allocationSize = 1)
@Table(name = "EVOLUCAO_DOADOR")
public class EvolucaoDoador implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8190916639685838733L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_EVDO_ID")
	@Column(name = "EVDO_ID")
	@JsonView(DoadorView.Evolucao.class)
	private Long id;
	
	@Column(name = "EVDO_DT_CRIACAO")
	@NotNull
	@JsonView(DoadorView.Evolucao.class)
	private LocalDateTime dataCriacao;
	
	@ManyToOne
	@JoinColumn(name = "DOAD_ID", nullable = true)
	@NotNull
	private Doador doador;

	@ManyToOne
	@JoinColumn(name = "USUA_ID", nullable = true)
	@NotNull
	@JsonView(DoadorView.Evolucao.class)
	private Usuario usuarioResponsavel;

	@Column(name = "EVDO_TX_DESCRICAO")
	@NotNull
	@JsonView(DoadorView.Evolucao.class)
	private String descricao;
	
	/**
	 * Construtor padrão.
	 */
	public EvolucaoDoador() {
		this.dataCriacao = LocalDateTime.now();
	}
		
	/**
	 * Construtor sobrecarregado para essa classe.
	 * 
	 * @param doador Dono da evolução.
	 * @param usuarioResponsavel usuário responsável pela inclusão da evolução. 
	 * @param descricao - texto da evolução.
	 */
	public EvolucaoDoador(Doador doador, Usuario usuarioResponsavel, String descricao) {		
		this();		
		this.doador = doador;
		this.usuarioResponsavel = usuarioResponsavel;
		this.descricao = descricao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the dataCriacao
	 */
	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	/**
	 * @param dataCriacao the dataCriacao to set
	 */
	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Doador getDoador() {
		return doador;
	}

	public void setDoador(Doador doador) {
		this.doador = doador;
	}

	public Usuario getUsuarioResponsavel() {
		return usuarioResponsavel;
	}

	public void setUsuarioResponsavel(Usuario usuarioResponsavel) {
		this.usuarioResponsavel = usuarioResponsavel;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((doador == null) ? 0 : doador.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((usuarioResponsavel == null) ? 0 : usuarioResponsavel.hashCode());
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
		EvolucaoDoador other = (EvolucaoDoador) obj;
		if (descricao == null) {
			if (other.descricao != null) {
				return false;
			}
		} 
		else if (!descricao.equals(other.descricao)) {
			return false;
		}
		if (doador == null) {
			if (other.doador != null) {
				return false;
			}
		} 
		else if (!doador.equals(other.doador)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} 
		else if (!id.equals(other.id)) {
			return false;
		}
		if (usuarioResponsavel == null) {
			if (other.usuarioResponsavel != null) {
				return false;
			}
		} 
		else if (!usuarioResponsavel.equals(other.usuarioResponsavel)) {
			return false;
		}
		return true;
	}

}
