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
import org.hibernate.validator.constraints.Email;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.BuscaView;
import br.org.cancer.modred.controller.view.CentroTransplanteView;
import br.org.cancer.modred.controller.view.CourierView;
import br.org.cancer.modred.controller.view.DoadorView;
import br.org.cancer.modred.controller.view.GenotipoView;
import br.org.cancer.modred.controller.view.LaboratorioView;
import br.org.cancer.modred.controller.view.MedicoView;
import br.org.cancer.modred.controller.view.TransportadoraView;

/**
 * Classe de persistencia de EmailContato para a tabela email_contato.
 * 
 * @author Fillipe Queiroz
 */
@Entity
@SequenceGenerator(name = "SQ_EMCO_ID", sequenceName = "SQ_EMCO_ID", allocationSize = 1)
@Table(name = "EMAIL_CONTATO")
@JsonIgnoreProperties(ignoreUnknown = true)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorFormula(
	    "case when DOAD_ID is not null " +
	    "then 'DOAD' " +
	    "when MEDI_ID is not null " +
	    "then 'MEDI' " +
	    "when LABO_ID is not null " +
	    "then 'LABO' " +	    
	    "when CETR_ID is not null " +
	    "then 'CETR' " +
	    "else 'Unknown' " +
	    "end "
)
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public abstract class EmailContato implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_EMCO_ID")
	@Column(name = "EMCO_ID")
	@JsonView({LaboratorioView.Detalhe.class, BuscaView.Enriquecimento.class, DoadorView.AtualizacaoFase2.class, DoadorView.ContatoPassivo.class, 
				MedicoView.Detalhe.class,TransportadoraView.Detalhe.class,CourierView.Detalhe.class, CentroTransplanteView.Detalhe.class,
				DoadorView.ContatoFase2.class})
	protected Long id;

	@Column(name = "EMCO_TX_EMAIL")
	@Email
	@NotNull
	@JsonView({LaboratorioView.Detalhe.class, BuscaView.Enriquecimento.class, DoadorView.AtualizacaoFase2.class, DoadorView.ContatoPassivo.class,
				MedicoView.Detalhe.class, GenotipoView.Divergente.class,TransportadoraView.Detalhe.class,CourierView.Detalhe.class,
				CentroTransplanteView.Detalhe.class, DoadorView.ContatoFase2.class, DoadorView.LogisticaDoador.class})
	protected String email;

	@Column(name = "EMCO_IN_EXCLUIDO")
	@JsonView({LaboratorioView.Detalhe.class, BuscaView.Enriquecimento.class, DoadorView.AtualizacaoFase2.class, DoadorView.ContatoPassivo.class,
		MedicoView.Detalhe.class,TransportadoraView.Detalhe.class,CourierView.Detalhe.class, CentroTransplanteView.Detalhe.class,
		DoadorView.ContatoFase2.class})
	protected boolean excluido;
	
	@Column(name = "EMCO_IN_PRINCIPAL")
	@JsonView({LaboratorioView.Detalhe.class, BuscaView.Enriquecimento.class, DoadorView.AtualizacaoFase2.class, DoadorView.ContatoPassivo.class,
				MedicoView.Detalhe.class,TransportadoraView.Detalhe.class,CourierView.Detalhe.class, CentroTransplanteView.Detalhe.class,
				DoadorView.ContatoFase2.class, DoadorView.LogisticaDoador.class})
	protected Boolean principal;

	public EmailContato() {}

	/**
	 * Chave primaria do registro.
	 * 
	 * @return
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Chave primaria do registro.
	 * 
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Email do contato.
	 * 
	 * @return
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Email do contato.
	 * 
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Flag que diz se registro foi excluido.
	 * 
	 * @return
	 */
	public boolean getExcluido() {
		return excluido;
	}

	/**
	 * Flag que diz se registro foi excluido.
	 * 
	 * @param excluido
	 */
	public void setExcluido(boolean excluido) {
		this.excluido = excluido;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( email == null ) ? 0 : email.hashCode() );
		result = prime * result + ( excluido ? 1231 : 1237 );
		result = prime * result + ( ( id == null ) ? 0 : id.hashCode() );
		result = prime * result + ( ( principal == null ) ? 0 : principal.hashCode() );
		return result;
	}

	/* (non-Javadoc)
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
		EmailContato other = (EmailContato) obj;
		if (email == null) {
			if (other.email != null) {
				return false;
			}
		}
		else
			if (!email.equals(other.email)) {
				return false;
			}
		if (excluido != other.excluido) {
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
		if (principal == null) {
			if (other.principal != null) {
				return false;
			}
		}
		else
			if (!principal.equals(other.principal)) {
				return false;
			}
		return true;
	}

}
