package br.org.cancer.modred.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.ExameDoadorView;
import br.org.cancer.modred.controller.view.ExameView;
import br.org.cancer.modred.controller.view.GenotipoView;
import br.org.cancer.modred.controller.view.PacienteView;
import br.org.cancer.modred.model.annotations.EnumValues;
import br.org.cancer.modred.model.domain.MotivoDivergenciaLocusExame;
import br.org.cancer.modred.model.security.Usuario;

/**
 * Classe que representa a relação entre locus e exame.
 * 
 * @author Rafael Pizão
 *
 */
@Entity
@Table(name = "LOCUS_EXAME")
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class LocusExame implements Serializable {

	private static final long serialVersionUID = 1514967206431334523L;

	@EmbeddedId
	@Valid
	@NotNull
	@JsonView({ ExameView.ListaExame.class, ExameView.ConferirExame.class, PacienteView.Rascunho.class,
			ExameDoadorView.ExameListaCombo.class, GenotipoView.ListaExame.class, GenotipoView.Divergente.class })
	private LocusExamePk id;

	@Column(name = "LOEX_TX_PRIMEIRO_ALELO")
	@NotNull
	@JsonView({ ExameView.ListaExame.class, ExameView.ConferirExame.class, PacienteView.Rascunho.class,
			ExameDoadorView.ExameListaCombo.class, GenotipoView.ListaExame.class, GenotipoView.Divergente.class })
	private String primeiroAlelo;

	@Column(name = "LOEX_IN_PRIMEIRO_DIVERGENTE")
	@JsonView({ ExameView.ListaExame.class, GenotipoView.ListaExame.class, GenotipoView.Divergente.class })
	private Boolean primeiroAleloDivergente;

	@Column(name = "LOEX_TX_SEGUNDO_ALELO")
	@NotNull
	@JsonView({ ExameView.ListaExame.class, ExameView.ConferirExame.class, PacienteView.Rascunho.class,
			ExameDoadorView.ExameListaCombo.class, GenotipoView.ListaExame.class, GenotipoView.Divergente.class })
	private String segundoAlelo;

	@Column(name = "LOEX_IN_SEGUNDO_DIVERGENTE")
	@JsonView({ ExameView.ListaExame.class, GenotipoView.ListaExame.class, GenotipoView.Divergente.class })
	private Boolean segundoAleloDivergente;
	
	@Column(name = "LOEX_IN_INATIVO")
	@JsonView({ ExameView.ListaExame.class, GenotipoView.ListaExame.class, GenotipoView.Divergente.class })
	private Boolean inativo = false;
	
	@Transient
	@JsonView({GenotipoView.ListaExame.class, GenotipoView.Divergente.class})
	private String primeiroAleloComposicao;
	
	@Transient
	@JsonView({GenotipoView.ListaExame.class, GenotipoView.Divergente.class})
	private String segundoAleloComposicao;
	
	@Column(name = "LOEX_DT_MARCACAO_DIVERGENTE")
	private LocalDateTime dataMarcacao;
	
	@ManyToOne
	@JoinColumn(name = "USUA_ID")
	@NotFound(action=NotFoundAction.IGNORE)
	private Usuario usuarioResponsavel;
	
	@Column(name = "LOEX_IN_MOTIVO_DIVERGENCIA")
	@EnumValues(MotivoDivergenciaLocusExame.class)	
	private Integer motivoDivergencia;
	
	@Column(name = "LOEX_IN_SELECIONADO")
	private Boolean selecionado;
	
	/**
	 * Utilizado na inativação do locusExame para gerar o log da busca.
	 */
	@Transient
	private Long idBusca;

	/**
	 * Construtor sobrecarregado. Utilizado para popular o ID, composto por
	 * exame e lócus, somente com o que é necessário para identificá-los.
	 * 
	 * @param exame
	 *            exame associado ao locus exame.
	 * @param locus
	 *            código do lócus.
	 * @param primeiroAlelo
	 *            valor do primeiro alelo.
	 * @param segundoAlelo
	 *            valor do segundo alelo.
	 */
	public LocusExame(Exame exame, String locus, String primeiroAlelo, String segundoAlelo) {
		this.id = new LocusExamePk(locus, exame);
		this.primeiroAlelo = primeiroAlelo;
		this.segundoAlelo = segundoAlelo;
	}

	/**
	 * Construtor sobrecarregado.
	 * 
	 * @param primeiroAlelo
	 * @param segundoAlelo
	 */
	public LocusExame(String primeiroAlelo, String segundoAlelo) {
		this.primeiroAlelo = primeiroAlelo;
		this.segundoAlelo = segundoAlelo;
	}

	/**
	 * Construtor padrão.
	 */
	public LocusExame() {
	}

	/**
	 * @return the primeiroAlelo
	 */
	public String getPrimeiroAlelo() {
		return primeiroAlelo;
	}

	/**
	 * @param primeiroAlelo
	 *            the primeiroAlelo to set
	 */
	public void setPrimeiroAlelo(String primeiroAlelo) {
		this.primeiroAlelo = primeiroAlelo;
	}

	/**
	 * @return the segundoAlelo
	 */
	public String getSegundoAlelo() {
		return segundoAlelo;
	}

	/**
	 * @param segundoAlelo
	 *            the segundoAlelo to set
	 */
	public void setSegundoAlelo(String segundoAlelo) {
		this.segundoAlelo = segundoAlelo;
	}

	/**
	 * @return the id
	 */
	public LocusExamePk getId() {
		return id;
	}

	/**
	 * Setter do id.
	 * 
	 * @param id
	 */
	public void setId(LocusExamePk id) {
		this.id = id;
	}

	/**
	 * @return the primeiroAleloDivergente
	 */
	public Boolean getPrimeiroAleloDivergente() {
		return primeiroAleloDivergente;
	}

	/**
	 * @param primeiroAleloDivergente
	 *            the primeiroAleloDivergente to set
	 */
	public void setPrimeiroAleloDivergente(Boolean primeiroAleloDivergente) {
		this.primeiroAleloDivergente = primeiroAleloDivergente;
	}

	/**
	 * @return the segundoAleloDivergente
	 */
	public Boolean getSegundoAleloDivergente() {
		return segundoAleloDivergente;
	}

	/**
	 * @param segundoAleloDivergente
	 *            the segundoAleloDivergente to set
	 */
	public void setSegundoAleloDivergente(Boolean segundoAleloDivergente) {
		this.segundoAleloDivergente = segundoAleloDivergente;
	}
	

	/**
	 * @return the inativo
	 */
	public Boolean getInativo() {
		return inativo;
	}

	/**
	 * @param inativo the inativo to set
	 */
	public void setInativo(Boolean inativo) {
		this.inativo = inativo;
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
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((primeiroAlelo == null) ? 0 : primeiroAlelo.hashCode());
		result = prime * result + ((primeiroAleloDivergente == null) ? 0 : primeiroAleloDivergente.hashCode());
		result = prime * result + ((segundoAlelo == null) ? 0 : segundoAlelo.hashCode());
		result = prime * result + ((segundoAleloDivergente == null) ? 0 : segundoAleloDivergente.hashCode());
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
		if (getClass() != obj.getClass()) {
			return false;
		}
		LocusExame other = (LocusExame) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} 
		else if (!id.equals(other.id)) {
			return false;
		}
		if (primeiroAlelo == null) {
			if (other.primeiroAlelo != null) {
				return false;
			}
		} 
		else if (!primeiroAlelo.equals(other.primeiroAlelo)) {
			return false;
		}
		if (primeiroAleloDivergente == null) {
			if (other.primeiroAleloDivergente != null) {
				return false;
			}
		}
		else if (!primeiroAleloDivergente.equals(other.primeiroAleloDivergente)) {
			return false;
		}
		if (segundoAlelo == null) {
			if (other.segundoAlelo != null) {
				return false;
			}
		}
		else if (!segundoAlelo.equals(other.segundoAlelo)) {
			return false;
		}
		if (segundoAleloDivergente == null) {
			if (other.segundoAleloDivergente != null) {
				return false;
			}
		}
		else if (!segundoAleloDivergente.equals(other.segundoAleloDivergente)) {
			return false;
		}
		return true;
	}

	/**
	 * Metodo que verifica se o objeto LocusExame eh uma instancia com todos
	 * atributos nulos.
	 * 
	 * @return null se os atributos são nulos.
	 */
	public boolean isVazio() {
		return this.primeiroAlelo == null && this.segundoAlelo == null;
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
	 * @return the primeiroAleloComposicao
	 */
	public String getPrimeiroAleloComposicao() {
		return primeiroAleloComposicao;
	}

	/**
	 * @param primeiroAleloComposicao the primeiroAleloComposicao to set
	 */
	public void setPrimeiroAleloComposicao(String primeiroAleloComposicao) {
		this.primeiroAleloComposicao = primeiroAleloComposicao;
	}

	/**
	 * @return the segundoAleloComposicao
	 */
	public String getSegundoAleloComposicao() {
		return segundoAleloComposicao;
	}

	/**
	 * @param segundoAleloComposicao the segundoAleloComposicao to set
	 */
	public void setSegundoAleloComposicao(String segundoAleloComposicao) {
		this.segundoAleloComposicao = segundoAleloComposicao;
	}

	/**
	 * @return the dataMarcacao
	 */
	public LocalDateTime getDataMarcacao() {
		return dataMarcacao;
	}

	/**
	 * @param dataMarcacao the dataMarcacao to set
	 */
	public void setDataMarcacao(LocalDateTime dataMarcacao) {
		this.dataMarcacao = dataMarcacao;
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
	 * @return the motivoDivergencia
	 */
	public Integer getMotivoDivergencia() {
		return motivoDivergencia;
	}

	/**
	 * @param motivoDivergencia the motivoDivergencia to set
	 */
	public void setMotivoDivergencia(Integer motivoDivergencia) {
		this.motivoDivergencia = motivoDivergencia;
	}

	/**
	 * @return the selecionado
	 */
	public Boolean getSelecionado() {
		return selecionado;
	}

	/**
	 * @param selecionado the selecionado to set
	 */
	public void setSelecionado(Boolean selecionado) {
		this.selecionado = selecionado;
	}

	
	
}
