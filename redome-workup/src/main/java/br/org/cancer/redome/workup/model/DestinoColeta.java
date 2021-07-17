package br.org.cancer.redome.workup.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Domínio dos destino dado para a coleta do doador.
 * @author ergomes
 */
@Entity
@Table(name = "DESTINO_COLETA")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class DestinoColeta implements Serializable {

	private static final long serialVersionUID = 9073683145022356523L;
	
	public static final Long INFUSAO = 1L;
	public static final Long CONGELAMENTO = 2L;
	public static final Long DESCARTE = 3L;
	
	@Id
	@Column(name = "DECO_ID")
	private Long id;

	@Column(name = "DECO_TX_DESCRICAO")
	private String descricao;
}