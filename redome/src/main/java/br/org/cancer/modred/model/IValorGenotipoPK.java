/**
 * 
 */
package br.org.cancer.modred.model;

import java.io.Serializable;

/**
 * @author Pizão
 * 
 * Define uma interface para chave primária compartilhada
 * entre paciente e doador.
 */
public interface IValorGenotipoPK extends Serializable {

	/**
	 * Locus do alelo informado.
	 * @return
	 */
	Locus getLocus();
	void setLocus(Locus locus);

	/**
	 * Exame que gerou este genótipo.
	 * @return
	 */
	Exame getExame();
	void setExame(Exame exame);

	/**
	 * Retorna a posição do alelo conforme apresentado no
	 * HLA.
	 * @return
	 */
	Integer getPosicaoAlelo();
	void setPosicaoAlelo(Integer posicao);
	
}
