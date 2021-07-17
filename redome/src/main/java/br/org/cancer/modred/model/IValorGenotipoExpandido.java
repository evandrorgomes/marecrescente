/**
 * 
 */
package br.org.cancer.modred.model;

import java.io.Serializable;

/**
 * Define a assinatura das classes que sofrem redução no valor
 * genótipo recebido, tais como GenotipoBusca e Expandido, para 
 * paciente e doador.
 * 
 * @author Pizão
 */
public interface IValorGenotipoExpandido extends Serializable {

	/**
	 * Retorna o ID da entidade.
	 * @return
	 */
	Long getId();
	void setId(Long id);
	
	/**
	 * Retorna a posição do alelo conforme apresentado no
	 * HLA.
	 * @return
	 */
	Integer getPosicao();
	void setPosicao(Integer alelo);
	
	/**
	 * Valor do alelo particionado para busca.
	 * @return
	 */
	Long getValor();
	void setValor(Long valor);
	
	/**
	 * Locus do alelo informado.
	 * @return
	 */
	Locus getLocus();
	void setLocus(Locus locus);
	
	/**
	 * Genótipo de paciente ou doador associado aos valores.
	 * @return
	 */
	IGenotipo getGenotipo();
	void setGenotipo(IGenotipo genotipo);
	
	/**
	 * Define quem é o proprietário deste valor, se
	 * paciente ou doador.
	 * @return
	 */
	IProprietarioHla getProprietario();
	void setProprietario(IProprietarioHla proprietario);

	
}
