/**
 * 
 */
package br.org.cancer.modred.model;

import java.io.Serializable;

/**
 * @author Pizão
 * 
 * Interface que define os getters e setters necessários para
 * um valor genótipo, tanto de doador quanto de paciente.
 * Esta interface foi criada para que as funcionalidades envolvendo
 * ambos os valores possam ser compartilhadas sem duplicidade de código.
 * Lembrando que, a interface só foi necessária pois ambos os valores não
 * possuem a mesma origem (tabela do banco de dados).
 * 
 */
public interface IValorGenotipo extends Serializable {
	
	/**
	 * Retorna o ID da entidade.
	 * @return
	 */
	IValorGenotipoPK getId();
	void setId(IValorGenotipoPK id);
	
	/**
	 * Retorna o tipo do valor.
	 * @see {@link ComposicaoAlelo.class}
	 * @return
	 */
	Integer getTipo();
	void setTipo(Integer tipo);
	
	/**
	 * Valor do alelo.
	 * @return
	 */
	String getAlelo();
	void setAlelo(String alelo);
	
	/**
	 * Posição do alelo conforme foi apresentado no HLA.
	 * @return
	 */
	Integer getPosicao();
	void setPosicao(Integer alelo);
	
	/**
	 * Locus de referência para o valor.
	 * @return
	 */
	Locus getLocus();
	void setLocus(Locus locus);
	
	/**
	 * Genótipo agregador dos valores alélicos.
	 * Pode ser do paciente ou doador.
	 * @return
	 */
	IGenotipo getGenotipo();
	void setGenotipo(IGenotipo genotipo);
	
	/**
	 * Flag para valores divergentes em relação a exames anteriores.
	 * @return
	 */
	Boolean getDivergente();
	void setDivergente(Boolean divergente);
	
	
}
