package br.org.cancer.modred.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import br.org.cancer.modred.controller.dto.genotipo.ComposicaoAlelo;
import br.org.cancer.modred.controller.dto.genotipo.ValorAlelo;
import br.org.cancer.modred.model.annotations.EnumValues;
import br.org.cancer.modred.model.domain.TiposDoador;
import br.org.cancer.modred.util.EnumUtil;

/**
 * Classe que representa a entidade Genotipo no modelo do Redome. Esta classe consiste na consolidação de todos os valores
 * alélicos de maior resolução para um determinado exame/paciente.
 * 
 * Os critérios que definem a resolução são (por ordem, quanto ao fim da lista, maior resolução): 1. NMDP. Ex.: 01:ABCD 2. Grupo
 * G: 01:01:01G 3. Grupo P: 01:01P 4. Alelo: 01:01
 * 
 * Lembrando que valores sorológicos e antígeno (01, 02...) não são inseridos no sistema.
 * 
 * @author Pizão
 *
 */
@Entity
@Table(name = "VALOR_GENOTIPO_DOADOR")
public class ValorGenotipoDoador implements IValorGenotipo {

	private static final long serialVersionUID = -479417337692249849L;

	@EmbeddedId
	@Valid
	@NotNull
	private ValorGenotipoDoadorPK id;

	@EnumValues(ComposicaoAlelo.class)
	@Column(name = "VAGD_NR_TIPO")
	private Integer tipo;

	@Column(name = "VAGD_TX_ALELO")
	@NotNull
	private String alelo;

	@Column(name = "VAGD_NR_POSICAO_ALELO", insertable = false, updatable = false)
	private Integer posicao;

	@ManyToOne
	@JoinColumn(name = "GEDO_ID")
	private GenotipoDoador genotipo;

	@NotNull
	@Column(name = "VAGD_IN_TIPO_DOADOR")
	private Long tipoDoador;

	@Column(name = "VAGD_IN_DIVERGENTE")
	private Boolean divergente;
	
	
	public ValorGenotipoDoador() {}

	/**
	 * Construtor customizado para que seja possível criar um instância do genótipo somente com as informações necessárias.
	 * 
	 * @param codigoLocus código do locus.
	 * @param valorAlelo valor do alelo.
	 * @param posicao posição do alelo dentro do locus exame.
	 * @param idDoador - Identificador do Doador.
	 * @param tipoDoador - Tipo do doador. 
	 */
	public ValorGenotipoDoador(String codigoLocus, String valorAlelo, Integer posicao, Long idDoador, Long tipoDoador) {
		ValorGenotipoDoadorPK pk = new ValorGenotipoDoadorPK();
		pk.setLocus(new Locus(codigoLocus));
		pk.setPosicaoAlelo(posicao);

		this.setId(pk);
		this.alelo = valorAlelo;
		this.posicao = posicao;
		this.tipoDoador = tipoDoador;

		Doador doador = null;
		if (TiposDoador.NACIONAL.getId().equals(this.tipoDoador)) {
			doador = new DoadorNacional(idDoador);
		}
		else if (TiposDoador.INTERNACIONAL.getId().equals(this.tipoDoador)) {
			doador = new DoadorInternacional(idDoador);
		}
		else if (TiposDoador.CORDAO_NACIONAL.getId().equals(this.tipoDoador)) {
			doador = new CordaoNacional(idDoador);
		}
		else if (TiposDoador.CORDAO_INTERNACIONAL.getId().equals(this.tipoDoador)) {
			doador = new CordaoInternacional(idDoador);
		}
		
		this.genotipo = new GenotipoDoador();
		this.genotipo.setDoador(doador);
		
	}

	/**
	 * Construtor customizado para que seja possível criar um instância do genótipo somente com as informações necessárias.
	 * 
	 * @param codigoLocus código do locus.
	 * @param valorAlelo valor do alelo.
	 * @param posicao posição do alelo dentro do locus exame.
	 * @param exame - exame do alelo
	 */
	public ValorGenotipoDoador(String codigoLocus, String valorAlelo, Integer posicao, Exame exame) {
		ValorGenotipoDoadorPK pk = new ValorGenotipoDoadorPK();
		pk.setLocus(new Locus(codigoLocus));
		pk.setPosicaoAlelo(posicao);
		pk.setExame(exame);
		this.setId(pk);
		this.alelo = valorAlelo;
	}

	/**
	 * Construtor customizado para que seja possível criar um instância do genótipo através de um alelo. Alelo é o objeto
	 * transitado no algoritmo que monta o genótipo para um determinado paciente.
	 * 
	 * @param alelo valor do alelo que será utilizado para instanciação.
	 */
	public ValorGenotipoDoador(ValorAlelo alelo) {
		ValorGenotipoDoadorPK pk = new ValorGenotipoDoadorPK();
		pk.setLocus(new Locus(alelo.getCodigoLocus()));
		pk.setExame(alelo.getExame());
		pk.setPosicaoAlelo(alelo.getPosicao());
		this.setId(pk);
		this.divergente = alelo.getDivergente();
		this.alelo = alelo.getValor();
	}

	/**
	 * ID da entidade Genotipo.
	 * 
	 * @return ID da entidade.
	 */
	@Override
	public ValorGenotipoDoadorPK getId() {
		return id;
	}

	/**
	 * Seta o id do genotipo.
	 * 
	 * @param id
	 */
	@Override
	public void setId(IValorGenotipoPK id) {
		this.id = (ValorGenotipoDoadorPK) id;
	}

	/**
	 * Valor do alelo de maior resolução para a combinação de exame/locus.
	 * 
	 * @return valor do alelo.
	 */
	@Override
	public String getAlelo() {
		return alelo;
	}

	/**
	 * Seta o alelo.
	 * 
	 * @param alelo
	 */
	@Override
	public void setAlelo(String alelo) {
		this.alelo = alelo;
	}

	/**
	 * @return o tipo de valor.
	 */
	@Override
	public Integer getTipo() {
		return tipo;
	}

	/**
	 * @param seta o tipo do valor.
	 */
	@Override
	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return the genotipo
	 */
	@Override
	public IGenotipo getGenotipo() {
		return genotipo;
	}

	/**
	 * @param genotipo the genotipo to set
	 */
	@Override
	public void setGenotipo(IGenotipo genotipo) {
		this.genotipo = (GenotipoDoador) genotipo;
	}

	/**
	 * @return the divergente
	 */
	@Override
	public Boolean getDivergente() {
		return divergente;
	}

	/**
	 * @param divergente the divergente to set
	 */
	@Override
	public void setDivergente(Boolean divergente) {
		this.divergente = divergente;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( id == null ) ? 0 : id.hashCode() );
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ValorGenotipoDoador other = (ValorGenotipoDoador) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		}
		else
			if (!id.equals(other.id)) {
				return false;
			}
		return true;
	}

	@Override
	public String toString() {
		return id.getLocus().getCodigo() + "*" + alelo + "(" + id.getPosicaoAlelo() + ")";
	}

	/**
	 * @return the posicao
	 */
	@Override
	public Integer getPosicao() {
		return posicao;
	}

	/**
	 * @param posicao the posicao to set
	 */
	@Override
	public void setPosicao(Integer posicao) {
		this.posicao = posicao;
	}

	/**
	 * @return the locus
	 */
	@Override
	public Locus getLocus() {
		return id.getLocus();
	}

	/**
	 * @param locus the locus to set
	 */
	@Override
	public void setLocus(Locus locus) {
		this.id.setLocus(locus);
	}

	/**
	 * @return the tipoDoador
	 */
	public TiposDoador getTipoDoador() {
		if (tipoDoador != null) {
			return (TiposDoador) EnumUtil.valueOf(TiposDoador.class, tipoDoador);
		}
		return null;
	}

	/**
	 * @param tipoDoador the tipoDoador to set
	 */
	public void setTipoDoador(TiposDoador tipoDoador) {
		if (tipoDoador != null) {
			this.tipoDoador = tipoDoador.getId();
		}

	}

}