package br.org.cancer.modred.model;

import java.io.Serializable;
import java.time.LocalDate;

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

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.DoadorView;
import br.org.cancer.modred.model.security.Usuario;

/**
 * Entidade que representa as ressalvas associadas a um doador.
 * 
 * @author Pizão
 */
@Entity
@SequenceGenerator(name = "SQ_REDO_ID", sequenceName = "SQ_REDO_ID", allocationSize = 1)
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@Table(name = "RESSALVA_DOADOR")
public class RessalvaDoador implements Serializable {

	private static final long serialVersionUID = -2942663499057833870L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_REDO_ID")
	@JsonView({ DoadorView.AtualizacaoFase2.class, DoadorView.ContatoPassivo.class, DoadorView.Ressalva.class })
	@Column(name = "REDO_ID")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "DOAD_ID")
	@NotNull
	private Doador doador;

	@Column(name = "REDO_TX_OBSERVACAO")
	@NotNull
	@JsonView({ DoadorView.AtualizacaoFase2.class, DoadorView.ContatoPassivo.class, DoadorView.Ressalva.class })
	private String observacao;

	@Column(name = "REDO_DT_CRIACAO")
	private LocalDate dataCriacao;

	@ManyToOne
	@JoinColumn(name = "USUA_ID_CRIACAO")
	private Usuario usuarioResponsavel;

	@Column(name = "REDO_IN_EXCLUIDO")
	@JsonView({ DoadorView.Ressalva.class })
	private Boolean excluido = false;

	public RessalvaDoador() {
		super();
	}

	/**
	 * Construtor com campos de busca.
	 * 
	 * @param doador doador associado a ressalva.
	 * @param ressalva texto descrevendo a ressalva do doador.
	 */
	public RessalvaDoador(Doador doador, String ressalva) {
		super();
		this.doador = doador;
		this.observacao = ressalva;
	}

	
	/**
	 * Construtor com campos de busca.
	 * 
	 * @param doador doador associado a ressalva.
	 * @param ressalva texto descrevendo a ressalva do doador.
	 */
	public RessalvaDoador(String observacao, Doador doador, String ressalva) {
		super();
		this.observacao = observacao;
		this.doador = doador;
		this.observacao = ressalva;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Doador getDoador() {
		return doador;
	}

	public void setDoador(Doador doador) {
		this.doador = doador;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	/**
	 * @return the dataCriacao
	 */
	public LocalDate getDataCriacao() {
		return dataCriacao;
	}

	/**
	 * @param dataCriacao the dataCriacao to set
	 */
	public void setDataCriacao(LocalDate dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	/**
	 * @return the usuarioResponsavel
	 */
	public Usuario getUsuarioResponsavel() {
		return usuarioResponsavel;
	}

	/**
	 * @param usuarioResponsavel the usuarioResponsavel to set
	 */
	public void setUsuarioResponsavel(Usuario usuarioResponsavel) {
		this.usuarioResponsavel = usuarioResponsavel;
	}

	/**
	 * @return the excluido
	 */
	public Boolean getExcluido() {
		return excluido;
	}

	/**
	 * @param excluido the excluido to set
	 */
	public void setExcluido(Boolean excluido) {
		this.excluido = excluido;
	}

}