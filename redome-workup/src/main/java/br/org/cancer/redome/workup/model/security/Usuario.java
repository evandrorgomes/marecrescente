package br.org.cancer.redome.workup.model.security;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe para representar as características e comportamentos do usuário.
 * 
 * @author ergomes
 *
 */
@Entity
@Table(name = "USUARIO")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@SequenceGenerator(name = "SQ_USUA_ID", sequenceName = "SQ_USUA_ID", allocationSize = 1)
public class Usuario implements Serializable {

	private static final long serialVersionUID = 3939069671930132719L;
	
	/**
	 * Chave que identifica com exclusividade uma instância desta classe.
	 */
	@Id
	@Column(name = "USUA_ID")
	private Long id;
	
	@Column(name = "USUA_TX_NOME")
	private String nome;
	
	/**
	 * O username é o elemento usado para logar no sistema.
	 */
	@Column(name = "USUA_TX_USERNAME")
	private String username;

	@Column(name = "TRAN_ID")
	private Long idTransportadora;

	@Transient
	private List<String> authorities;

	
}