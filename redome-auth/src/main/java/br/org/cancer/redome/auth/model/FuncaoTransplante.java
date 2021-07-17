package br.org.cancer.redome.auth.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe de persistencia de FUNCAO_TRANSPLANTE.
 * 
 * @author bruno.sousa
 *
 */
@Entity
@Table(name = "FUNCAO_TRANSPLANTE")
@Data
@AllArgsConstructor @NoArgsConstructor
public class FuncaoTransplante implements Serializable {
	private static final long serialVersionUID = -244033354277632412L;

	/**
	 * Papel de avaliador.
	 */
	public static final Long PAPEL_AVALIADOR = 1L;

	/**
	 * Papel de centro de coleta.
	 */
	public static final Long PAPEL_COLETOR = 2L;

	/**
	 * Papel de transplantador.
	 */
	public static final Long PAPEL_TRANSPLANTADOR = 3L; 
	/**
	 * Chave que identifica com exclusividade uma instância desta classe.
	 */
	@Id
	@Column(name = "FUTR_ID")
	private Long id;

	/**
	 * Descrição do papel.
	 */
	@Column(name = "FUTR_DESCRICAO")
	private String descricao;

}