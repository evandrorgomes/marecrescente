package br.org.cancer.modred.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DiscriminatorFormula;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.BuscaView;
import br.org.cancer.modred.controller.view.CentroTransplanteView;
import br.org.cancer.modred.controller.view.CourierView;
import br.org.cancer.modred.controller.view.DoadorView;
import br.org.cancer.modred.controller.view.FormularioView;
import br.org.cancer.modred.controller.view.MedicoView;
import br.org.cancer.modred.controller.view.PacienteView;
import br.org.cancer.modred.controller.view.RegistroContatoView;
import br.org.cancer.modred.controller.view.TentativaContatoDoadorView;
import br.org.cancer.modred.controller.view.TransportadoraView;
import br.org.cancer.modred.model.annotations.EnumValues;
import br.org.cancer.modred.model.domain.TipoTelefone;

/**
 * Classe de persistencia de ContatoTelefonico para a tabela CONTATO_TELEFONICO.
 * 
 * @author Filipe Paes
 */
@Entity
@SequenceGenerator(name = "SQ_COTE_ID", sequenceName = "SQ_COTE_ID", allocationSize = 1)
@Table(name = "CONTATO_TELEFONICO")
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@JsonIgnoreProperties(ignoreUnknown = true)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorFormula("case when PACI_NR_RMR is not null " +
		"	then 'PACI' " +
		"when DOAD_ID is not null " +
		"   then 'DOAD' " +
		"when MEDI_ID is not null " +
		"   then 'MEDI' " +
		"when CETR_ID is not null " +
		"   then 'CETR' " +
		"else 'Unknown' " +
		"end ")
public abstract class ContatoTelefonico implements Serializable {

	private static final long serialVersionUID = -2817302903502150386L;

	/**
	 * Chave que identifica com exclusividade uma instância desta classe.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_COTE_ID")
	@Column(name = "COTE_ID")
	@JsonView({ PacienteView.Detalhe.class, BuscaView.Enriquecimento.class, DoadorView.AtualizacaoFase2.class,
			DoadorView.ContatoPassivo.class,
			DoadorView.ContatoFase2.class, TentativaContatoDoadorView.Consultar.class,
			TransportadoraView.AgendarTransporte.class, CentroTransplanteView.Detalhe.class,
			MedicoView.Detalhe.class,TransportadoraView.Detalhe.class, CourierView.Detalhe.class, 
			FormularioView.DetalheDoador.class, DoadorView.LogisticaDoador.class})
	protected Long id;
	/**
	 * Marcação que indica que o número telefônico é o principal.
	 */
	@Column(name = "COTE_IN_PRINCIPAL")
	@NotNull
	@JsonView({ PacienteView.Detalhe.class, BuscaView.Enriquecimento.class, DoadorView.AtualizacaoFase2.class,
			DoadorView.ContatoPassivo.class,
			DoadorView.ContatoFase2.class, TentativaContatoDoadorView.Consultar.class,
			TransportadoraView.AgendarTransporte.class, CentroTransplanteView.Detalhe.class,
			MedicoView.Detalhe.class, PacienteView.Rascunho.class,TransportadoraView.Detalhe.class, CourierView.Detalhe.class,
			FormularioView.DetalheDoador.class, DoadorView.LogisticaDoador.class})
	protected Boolean principal;
	/**
	 * Tipo do telefone [residencial, celular].
	 */
	@Column(name = "COTE_IN_TIPO")
	@NotNull
	@EnumValues(TipoTelefone.class)
	@JsonView({ PacienteView.Detalhe.class, BuscaView.Enriquecimento.class, DoadorView.AtualizacaoFase2.class,
			DoadorView.ContatoPassivo.class,
			DoadorView.ContatoFase2.class, TentativaContatoDoadorView.Consultar.class,
			TransportadoraView.AgendarTransporte.class, CentroTransplanteView.Detalhe.class,
			MedicoView.Detalhe.class, PacienteView.Rascunho.class,TransportadoraView.Detalhe.class, CourierView.Detalhe.class})
	protected Integer tipo;
	/**
	 * Código de área.
	 */
	@Column(name = "COTE_NR_COD_AREA")
	@NotNull
	@JsonView({ PacienteView.Detalhe.class, BuscaView.Enriquecimento.class, DoadorView.AtualizacaoFase2.class,
			DoadorView.ContatoPassivo.class,
			DoadorView.ContatoFase2.class, TentativaContatoDoadorView.Consultar.class,
			TransportadoraView.AgendarTransporte.class, CentroTransplanteView.Detalhe.class,
			MedicoView.Detalhe.class, PacienteView.Rascunho.class,TransportadoraView.Detalhe.class, CourierView.Detalhe.class, 
			RegistroContatoView.Consulta.class, FormularioView.DetalheDoador.class, DoadorView.LogisticaDoador.class})
	protected Integer codArea;
	/**
	 * Código internacional.
	 */
	@Column(name = "COTE_NR_COD_INTER")
	@JsonView({ PacienteView.Detalhe.class, BuscaView.Enriquecimento.class, DoadorView.AtualizacaoFase2.class,
			DoadorView.ContatoPassivo.class,
			DoadorView.ContatoFase2.class, TentativaContatoDoadorView.Consultar.class,
			TransportadoraView.AgendarTransporte.class, CentroTransplanteView.Detalhe.class,
			MedicoView.Detalhe.class, PacienteView.Rascunho.class,TransportadoraView.Detalhe.class, 
			CourierView.Detalhe.class, RegistroContatoView.Consulta.class, DoadorView.LogisticaDoador.class})
	protected Integer codInter;
	/**
	 * Número do telefone.
	 */
	@Column(name = "COTE_NR_NUMERO")
	@NotNull
	@JsonView({ PacienteView.Detalhe.class, BuscaView.Enriquecimento.class, DoadorView.AtualizacaoFase2.class,
			DoadorView.ContatoPassivo.class, CentroTransplanteView.Detalhe.class,
			DoadorView.ContatoFase2.class, TentativaContatoDoadorView.Consultar.class,
			TransportadoraView.AgendarTransporte.class, CentroTransplanteView.Detalhe.class,
			MedicoView.Detalhe.class, PacienteView.Rascunho.class,TransportadoraView.Detalhe.class, CourierView.Detalhe.class, 
			RegistroContatoView.Consulta.class, FormularioView.DetalheDoador.class, DoadorView.LogisticaDoador.class})
	protected Long numero;
	/**
	 * Complemento descritivo.
	 */
	@Column(name = "COTE_TX_COMPLEMENTO")
	@Length(max = 20)
	@JsonView({ PacienteView.Detalhe.class, BuscaView.Enriquecimento.class, DoadorView.AtualizacaoFase2.class,
			DoadorView.ContatoPassivo.class,
			DoadorView.ContatoFase2.class, TentativaContatoDoadorView.Consultar.class,
			TransportadoraView.AgendarTransporte.class, CentroTransplanteView.Detalhe.class,
			MedicoView.Detalhe.class, PacienteView.Rascunho.class,TransportadoraView.Detalhe.class, 
			CourierView.Detalhe.class, RegistroContatoView.Consulta.class, DoadorView.LogisticaDoador.class})
	protected String complemento;
	/**
	 * Nome do contato.
	 */
	@Column(name = "COTE_TX_NOME")
	@Length(max = 100)
	@JsonView({ PacienteView.Detalhe.class, BuscaView.Enriquecimento.class, DoadorView.AtualizacaoFase2.class,
			DoadorView.ContatoPassivo.class,
			DoadorView.ContatoFase2.class, TentativaContatoDoadorView.Consultar.class,
			TransportadoraView.AgendarTransporte.class, CentroTransplanteView.Detalhe.class,
			MedicoView.Detalhe.class, PacienteView.Rascunho.class,TransportadoraView.Detalhe.class, 
			CourierView.Detalhe.class, RegistroContatoView.Consulta.class, FormularioView.DetalheDoador.class,
			DoadorView.LogisticaDoador.class})
	protected String nome;

	@Column(name = "COTE_IN_EXCLUIDO")
	@JsonView({ BuscaView.Enriquecimento.class, DoadorView.ContatoFase2.class, DoadorView.AtualizacaoFase2.class,
			DoadorView.ContatoPassivo.class, TentativaContatoDoadorView.Consultar.class,
			TransportadoraView.AgendarTransporte.class, CentroTransplanteView.Detalhe.class})
	protected boolean excluido;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the principal
	 */
	public Boolean getPrincipal() {
		return principal;
	}

	/**
	 * @param principal the principal to set
	 */
	public void setPrincipal(Boolean principal) {
		this.principal = principal;
	}

	/**
	 * @return the tipo
	 */
	public Integer getTipo() {
		return tipo;
	}

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return the codArea
	 */
	public Integer getCodArea() {
		return codArea;
	}

	/**
	 * @param codArea the codArea to set
	 */
	public void setCodArea(Integer codArea) {
		this.codArea = codArea;
	}

	/**
	 * @return the codInter
	 */
	public Integer getCodInter() {
		return codInter;
	}

	/**
	 * @param codInter the codInter to set
	 */
	public void setCodInter(Integer codInter) {
		this.codInter = codInter;
	}

	/**
	 * @return the numero
	 */
	public Long getNumero() {
		return numero;
	}

	/**
	 * @param numero the numero to set
	 */
	public void setNumero(Long numero) {
		this.numero = numero;
	}

	/**
	 * @return the complemento
	 */
	public String getComplemento() {
		return complemento;
	}

	/**
	 * @param complemento the complemento to set
	 */
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
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
		result = prime * result + ( ( codArea == null ) ? 0 : codArea.hashCode() );
		result = prime * result + ( ( codInter == null ) ? 0 : codInter.hashCode() );
		result = prime * result + ( ( numero == null ) ? 0 : numero.hashCode() );
		return result;
	}
	
	/**
	 * @return the excluido
	 */
	public boolean isExcluido() {
		return excluido;
	}
	
	/**
	 * @return the excluido
	 */
	public boolean naoEstaExcluido() {
		return !excluido;
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
		ContatoTelefonico other = (ContatoTelefonico) obj;
		if (codArea == null) {
			if (other.codArea != null) {
				return false;
			}
		}
		else
			if (!codArea.equals(other.codArea)) {
				return false;
			}
		if (codInter == null) {
			if (other.codInter != null) {
				return false;
			}
		}
		else
			if (!codInter.equals(other.codInter)) {
				return false;
			}
		if (numero == null) {
			if (other.numero != null) {
				return false;
			}
		}
		else
			if (!numero.equals(other.numero)) {
				return false;
			}
		return true;
	}

	/**
	 * Retorna o contato formatado de forma linear, unindo código internacional, ddd e número.
	 * 
	 * @return contato formatado.
	 */
	public String retornarFormatadoParaSms() {
		return String.valueOf(codInter) + String.valueOf(codArea) + String.valueOf(numero);
	}
	

	@Override
	public String toString(){
		return "+" + codInter + " (" + codArea + ") " + numero;
	}

}