package br.org.cancer.modred.controller.dto;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.GenotipoView;

/**
 * Classe VO para dados da tela de Genótipo.
 * 
 * @author fillipe.queiroz
 *
 */
public class GenotipoDTO {

	@JsonView(GenotipoView.Divergente.class)
	private String locus;
	
	@JsonView(GenotipoView.Divergente.class)
	private String primeiroAlelo;
	
	@JsonView(GenotipoView.Divergente.class)
	private String examePrimeiroAlelo;
	
	@JsonView(GenotipoView.Divergente.class)
	private String segundoAlelo;
	
	@JsonView(GenotipoView.Divergente.class)
	private String exameSegundoAlelo;
	
	@JsonView(GenotipoView.Divergente.class)
	private Integer ordem;
	
	@JsonView(GenotipoView.Divergente.class)
	private Integer tipoPrimeiroAlelo;
	
	@JsonView(GenotipoView.Divergente.class)
	private Integer tipoSegundoAlelo;
	
	@JsonView(GenotipoView.Divergente.class)
	private String qualificacaoAlelo;
	
	@JsonView(GenotipoView.Divergente.class)
	private Boolean divergentePrimeiroAlelo;
	
	@JsonView(GenotipoView.Divergente.class)
	private Boolean divergenteSegundoAlelo;

	@JsonView(GenotipoView.Divergente.class)
	private String probabilidade;
	
	public GenotipoDTO() {
	}
	
	public GenotipoDTO(String locus) {
		this.locus = locus;
	}
	
	public GenotipoDTO(String locus, Integer ordem) {
		this(locus);
		this.ordem = ordem;
	}

	
	/**
	 * Retorna o locus.
	 * 
	 * @return locus
	 */
	public String getLocus() {
		return locus;
	}

	/**
	 * Seta o locus.
	 * 
	 * @param locus -
	 */
	public void setLocus(String locus) {
		this.locus = locus;
	}

	/**
	 * Retorna o primeiro alelo.
	 * 
	 * @return primeiroAlelo -
	 */
	public String getPrimeiroAlelo() {
		return primeiroAlelo;
	}

	/**
	 * Seta o primeiro alelo.
	 * 
	 * @param primeiroAlelo
	 */
	public void setPrimeiroAlelo(String primeiroAlelo) {
		this.primeiroAlelo = primeiroAlelo;
	}

	/**
	 * Segundo alelo.
	 * 
	 * @return segundoAlelo -
	 */
	public String getSegundoAlelo() {
		return segundoAlelo;
	}

	/**
	 * Segundo alelo.
	 * 
	 * @param segundoAlelo
	 */
	public void setSegundoAlelo(String segundoAlelo) {
		this.segundoAlelo = segundoAlelo;
	}

	/**
	 * Retorna o exame do primeiro alelo concatenado.
	 * 
	 * @return examePrimeiroAlelo -
	 */
	public String getExamePrimeiroAlelo() {
		return examePrimeiroAlelo;
	}

	/**
	 * Seta o exame do primeiro alelo concatenado.
	 * 
	 * @param examePrimeiroAlelo
	 */
	public void setExamePrimeiroAlelo(String examePrimeiroAlelo) {
		this.examePrimeiroAlelo = examePrimeiroAlelo;
	}

	/**
	 * Retorna o exame do segundo alelo concatenado.
	 * 
	 * @return exameSegundoAlelo -
	 */
	public String getExameSegundoAlelo() {
		return exameSegundoAlelo;
	}

	/**
	 * Seta o exame do segundo alelo concatenado.
	 * 
	 * @param exameSegundoAlelo
	 */
	public void setExameSegundoAlelo(String exameSegundoAlelo) {
		this.exameSegundoAlelo = exameSegundoAlelo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((examePrimeiroAlelo == null) ? 0 : examePrimeiroAlelo.hashCode());
		result = prime * result + ((exameSegundoAlelo == null) ? 0 : exameSegundoAlelo.hashCode());
		result = prime * result + ((locus == null) ? 0 : locus.hashCode());
		result = prime * result + ((primeiroAlelo == null) ? 0 : primeiroAlelo.hashCode());
		result = prime * result + ((segundoAlelo == null) ? 0 : segundoAlelo.hashCode());
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
		
		GenotipoDTO other = (GenotipoDTO) obj;
		
		if (examePrimeiroAlelo == null) {
			if (other.examePrimeiroAlelo != null){
				return false;
			}
		}
		else if (!examePrimeiroAlelo.equals(other.examePrimeiroAlelo)){
			return false;
		}
		if (exameSegundoAlelo == null) {
			if (other.exameSegundoAlelo != null){
				return false;
			}
		}
		else if (!exameSegundoAlelo.equals(other.exameSegundoAlelo)){
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
		if (primeiroAlelo == null) {
			if (other.primeiroAlelo != null){
				return false;
			}
		}
		else if (!primeiroAlelo.equals(other.primeiroAlelo)){
			return false;
		}
		if (segundoAlelo == null) {
			if (other.segundoAlelo != null){
				return false;
			}
		}
		else if (!segundoAlelo.equals(other.segundoAlelo)){
			return false;
		}
		return true;
	}

	
	/**
	 * @return the ordem
	 */
	public Integer getOrdem() {
		return ordem;
	}

	
	/**
	 * @param ordem the ordem to set
	 */
	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
	}

	
	/**
	 * @return the tipoPrimeiroAlelo
	 */
	public Integer getTipoPrimeiroAlelo() {
		return tipoPrimeiroAlelo;
	}

	
	/**
	 * @param tipoPrimeiroAlelo the tipoPrimeiroAlelo to set
	 */
	public void setTipoPrimeiroAlelo(Integer tipoPrimeiroAlelo) {
		this.tipoPrimeiroAlelo = tipoPrimeiroAlelo;
	}

	
	/**
	 * @return the tipoSegundoAlelo
	 */
	public Integer getTipoSegundoAlelo() {
		return tipoSegundoAlelo;
	}
	
	/**
	 * @param tipoSegundoAlelo the tipoSegundoAlelo to set
	 */
	public void setTipoSegundoAlelo(Integer tipoSegundoAlelo) {
		this.tipoSegundoAlelo = tipoSegundoAlelo;
	}

	/**
	 * @return the divergentePrimeiroAlelo
	 */
	public Boolean getDivergentePrimeiroAlelo() {
		return divergentePrimeiroAlelo;
	}

	/**
	 * @param divergentePrimeiroAlelo the divergentePrimeiroAlelo to set
	 */
	public void setDivergentePrimeiroAlelo(Boolean divergentePrimeiroAlelo) {
		this.divergentePrimeiroAlelo = divergentePrimeiroAlelo;
	}

	/**
	 * @return the divergenteSegundoAlelo
	 */
	public Boolean getDivergenteSegundoAlelo() {
		return divergenteSegundoAlelo;
	}

	/**
	 * @param divergenteSegundoAlelo the divergenteSegundoAlelo to set
	 */
	public void setDivergenteSegundoAlelo(Boolean divergenteSegundoAlelo) {
		this.divergenteSegundoAlelo = divergenteSegundoAlelo;
	}

	/**
	 * @return the qualificacaoAlelo
	 */
	public String getQualificacaoAlelo() {
		return qualificacaoAlelo;
	}

	/**
	 * @param qualificacaoAlelo the qualificacaoAlelo to set
	 */
	public void setQualificacaoAlelo(String qualificacaoAlelo) {
		this.qualificacaoAlelo = qualificacaoAlelo;
	}

	/**
	 * @return the probabilidade
	 */
	public String getProbabilidade() {
		return probabilidade;
	}

	/**
	 * @param probabilidade the probabilidade to set
	 */
	public void setProbabilidade(String probabilidade) {
		this.probabilidade = probabilidade;
	}
	
	
}
