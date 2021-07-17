package br.org.cancer.redome.workup.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Tipos de amostras à serem solicitadas para um laboratório.
 * 
 * @author Filipe Paes
 *
 */
@Entity
@Table(name = "TIPO_AMOSTRA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoAmostra {

	@Id
	@Column(name = "TIAM_ID")
	private Long id;

	@Column(name = "TIAM_TX_DESCRICAO")
	private String descricao;
	

}
