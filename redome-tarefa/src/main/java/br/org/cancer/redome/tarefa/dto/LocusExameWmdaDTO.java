package br.org.cancer.redome.tarefa.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe dto para transporte de valores de HLA.
 * @author  ergomes ###
 *
 */
@Data @NoArgsConstructor @Builder @AllArgsConstructor
public class LocusExameWmdaDTO implements Serializable {

	private static final long serialVersionUID = -6346589775437106431L;

	private String codigo;
	private String primeiroAlelo;
	private String segundoAlelo;
}
