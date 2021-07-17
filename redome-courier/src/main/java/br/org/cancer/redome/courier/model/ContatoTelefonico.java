package br.org.cancer.redome.courier.model;

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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.DiscriminatorFormula;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@DiscriminatorFormula("case when COUR_ID is not null " +
		"   then 'COUR' " +		
		"when TRAN_ID is not null " +
		"   then 'TRAN' " +
		"else 'Unknown' " +
		"end ")
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class ContatoTelefonico implements Serializable {

	private static final long serialVersionUID = -2817302903502150386L;

	/**
	 * Chave que identifica com exclusividade uma instância desta classe.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_COTE_ID")
	@Column(name = "COTE_ID")
	protected Long id;
	/**
	 * Marcação que indica que o número telefônico é o principal.
	 */
	@Column(name = "COTE_IN_PRINCIPAL")
	@NotNull
	protected Boolean principal;
	/**
	 * Tipo do telefone [residencial, celular].
	 */
	@Column(name = "COTE_IN_TIPO")
	@NotNull
	protected Integer tipo;
	/**
	 * Código de área.
	 */
	@Column(name = "COTE_NR_COD_AREA")
	@NotNull
	protected Integer codArea;
	/**
	 * Código internacional.
	 */
	@Column(name = "COTE_NR_COD_INTER")
	protected Integer codInter;
	/**
	 * Número do telefone.
	 */
	@Column(name = "COTE_NR_NUMERO")
	@NotNull
	protected Long numero;
	/**
	 * Complemento descritivo.
	 */
	@Column(name = "COTE_TX_COMPLEMENTO")
	@Length(max = 20)
	protected String complemento;
	/**
	 * Nome do contato.
	 */
	@Column(name = "COTE_TX_NOME")
	@Length(max = 100)
	protected String nome;

	@Column(name = "COTE_IN_EXCLUIDO")
	protected boolean excluido;

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