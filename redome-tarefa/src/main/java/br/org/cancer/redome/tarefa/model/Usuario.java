package br.org.cancer.redome.tarefa.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Classe para representar as características e comportamentos do usuário.
 * 
 * @author bruno.sousa
 *
 */
@Entity
@Table(name = "USUARIO")
@Data
@AllArgsConstructor
@Builder
public class Usuario implements Serializable {

	private static final long serialVersionUID = 3939069671930132719L;
	
	/**
	 * Chave que identifica com exclusividade uma instância desta classe.
	 */
	@Id
	@Column(name = "USUA_ID")
	private Long id;
	
	/**
	 * O username é o elemento usado para logar no sistema.
	 */
	@Column(name = "USUA_TX_USERNAME")
	private String username;

	/**
	 * Método construtor da classe Usuario.
	 * 
	 * @return Usuario - instância da classe Usuario.
	 */
	public Usuario() {
		super();
	}



}