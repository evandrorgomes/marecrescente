package br.org.cancer.redome.auth.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Data;


/**
 * Classe de associação entre CentroTransplantador e Usuario.
 * @author Filipe Paes
 *
 */
@Entity
@Table(name="CENTRO_TRANSPLANTE_USUARIO")
@SequenceGenerator(name = "SQ_CETU_ID", sequenceName = "SQ_CETU_ID", allocationSize = 1)
@Data
public class CentroTransplanteUsuario implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_CETU_ID")
	@Column(name="CETU_ID")
	private Long id;
	
	@Column(name="CETU_IN_RESPONSAVEL")
	private Boolean responsavel;
	
	
	@ManyToOne
	@JoinColumn(name="CETR_ID")
	@JsonProperty(access=Access.WRITE_ONLY)
	private CentroTransplante centroTransplante;
	
	
	@ManyToOne
	@JoinColumn(name="USUA_ID")
	private Usuario usuario;


	
}