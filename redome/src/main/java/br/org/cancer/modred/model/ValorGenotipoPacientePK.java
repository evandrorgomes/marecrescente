package br.org.cancer.modred.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Classe para identificação do ID composto da classe Genotipo. Consiste na
 * junção de 3 propriedades da classe: Locus, Exame e Posição do Alelo. Esta
 * classe é uma imagem da classe LocusExame, porém, como são guardados os
 * valores por alelo e não por locus, como na classe citada, foi necessário ter
 * mais um atributo definido a que posição o valor pertence.
 * 
 * @author Pizão
 *
 */
@Embeddable
public class ValorGenotipoPacientePK implements IValorGenotipoPK {
	private static final long serialVersionUID = -4689016238212559231L;

	@ManyToOne
	@JoinColumn(name = "LOCU_ID")
	@NotNull
	@Valid
	private Locus locus;

	@ManyToOne
	@JoinColumn(name = "EXAM_ID")
	@NotNull
	private Exame exame;

	@Column(name = "VAGP_NR_POSICAO_ALELO")
	@NotNull
	private Integer posicaoAlelo;

	/**
	 * Locus associado a classe genótipo.
	 * 
	 * @return ID do locus.
	 */
	public Locus getLocus() {
		return locus;
	}

	/**
	 * Seta o Locus.
	 * 
	 * @param locus
	 */
	public void setLocus(Locus locus) {
		this.locus = locus;
	}

	/**
	 * Exame associado a classe genótipo.
	 * 
	 * @return ID do exame.
	 */
	public Exame getExame() {
		return exame;
	}

	/**
	 * Seta o exame.
	 * 
	 * @param exame
	 */
	public void setExame(Exame exame) {
		this.exame = exame;
	}

	/**
	 * Posição do alelo dentro do locus (1 - Primeiro Alelo, 2 - Segundo Alelo).
	 * 
	 * @return posição do alelo.
	 */
	public Integer getPosicaoAlelo() {
		return posicaoAlelo;
	}

	/**
	 * Seta a posição do alelo.
	 * 
	 * @param posicaoAlelo
	 */
	public void setPosicaoAlelo(Integer posicaoAlelo) {
		this.posicaoAlelo = posicaoAlelo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((exame == null) ? 0 : exame.hashCode());
		result = prime * result + ((locus == null) ? 0 : locus.hashCode());
		result = prime * result + ((posicaoAlelo == null) ? 0 : posicaoAlelo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj){
			return true;
		}
		if (obj == null){
			return false;
		}
		if (getClass() != obj.getClass()){
			return false;
		}
		ValorGenotipoPacientePK other = (ValorGenotipoPacientePK) obj;
		if (exame == null) {
			if (other.exame != null){
				return false;
			}
		}
		else if (!exame.equals(other.exame)){
			return false;
		}
		if (locus == null) {
			if (other.locus != null){
				return false;
			}
		}
		else if (!locus.equals(other.locus)){
			return false;
		}
		if (posicaoAlelo == null) {
			if (other.posicaoAlelo != null){
				return false;
			}
		}
		else if (!posicaoAlelo.equals(other.posicaoAlelo)){
			return false;
		}
		return true;
	}

}