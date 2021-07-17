package br.org.cancer.modred.model;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Classe de persistencia de ContatoTelefonicoPaciente para a tabela CONTATO_TELEFONICO.
 * 
 * @author Fillipe Queiroz
 */
@Entity
@DiscriminatorValue(value = "PACI")
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContatoTelefonicoPaciente extends ContatoTelefonico implements Serializable {

	private static final long serialVersionUID = 3379883641227797135L;

	@ManyToOne
	@JoinColumn(name = "PACI_NR_RMR")
	@JsonIgnore
	private Paciente paciente;

	public ContatoTelefonicoPaciente() {
		super();
	}

	/**
	 * @return the paciente
	 */
	public Paciente getPaciente() {
		return paciente;
	}

	/**
	 * @param paciente the paciente to set
	 */
	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	/**
	 * Metodo dsl de adição de tipo.
	 * 
	 * @param tipo
	 * @return ContatoTelefonicoPaciente this
	 */
	public ContatoTelefonicoPaciente addTipo(Integer tipo) {
		super.tipo = tipo;
		return this;
	}

	/**
	 * Metodo dsl de adição de código de área.
	 * 
	 * @param codArea
	 * @return ContatoTelefonicoPaciente this
	 */
	public ContatoTelefonicoPaciente addCodigoDeArea(Integer codArea) {
		this.codArea = codArea;
		return this;
	}

	/**
	 * Metodo dsl de adição de código de internacional.
	 * 
	 * @param codInter
	 * @return ContatoTelefonicoPaciente this
	 */
	public ContatoTelefonicoPaciente addCodigoInternacional(Integer codInter) {
		this.codInter = codInter;
		return this;
	}

	/**
	 * Metodo dsl de adição de número.
	 * 
	 * @param numero
	 * @return ContatoTelefonicoPaciente this
	 */
	public ContatoTelefonicoPaciente addNumero(Long numero) {
		this.numero = numero;
		return this;
	}

	/**
	 * Metodo dsl de adição de complemento.
	 * 
	 * @param complemento
	 * @return ContatoTelefonicoPaciente this
	 */
	public ContatoTelefonicoPaciente addComplemento(String complemento) {
		this.complemento = complemento;
		return this;
	}

	/**
	 * Metodo dsl de adição do nome do contato.
	 * 
	 * @param nome
	 * @return ContatoTelefonicoPaciente this
	 */
	public ContatoTelefonicoPaciente addNome(String nome) {
		this.nome = nome;
		return this;
	}

	/**
	 * Metodo dsl de adição de Id.
	 * 
	 * @param id
	 * @return ContatoTelefonicoPaciente this
	 */
	public ContatoTelefonicoPaciente addId(Long id) {
		this.id = id;
		return this;
	}

	/**
	 * Metodo dsl de adição de se é ou não principal.
	 * 
	 * @param principal
	 * @return ContatoTelefonicoPaciente this
	 */
	public ContatoTelefonicoPaciente isPrincipal(Boolean principal) {
		this.principal = principal;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ( ( paciente == null ) ? 0 : paciente.hashCode() );
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
		if (!super.equals(obj)) {
			return false;
		}
		if (!( obj instanceof ContatoTelefonicoPaciente )) {
			return false;
		}
		ContatoTelefonicoPaciente other = (ContatoTelefonicoPaciente) obj;
		if (paciente == null) {
			if (other.paciente != null) {
				return false;
			}
		}
		else
			if (!paciente.equals(other.paciente)) {
				return false;
			}
		return true;
	}

}