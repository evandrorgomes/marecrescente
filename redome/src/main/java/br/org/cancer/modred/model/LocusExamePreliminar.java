package br.org.cancer.modred.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Class que representa uma parte do HLA informado para uma
 * consulta preliminar e esta parte corresponde ao primeiro e segundo valor
 * de um determinado lócus.
 * 
 * @author Rafael Pizão
 *
 */
@Entity
@Table(name = "LOCUS_EXAME_PRELIMINAR")
@SequenceGenerator(name = "SQ_LOEP_ID", sequenceName = "SQ_LOEP_ID", allocationSize = 1)
public class LocusExamePreliminar implements Serializable {
	private static final long serialVersionUID = 8164317294854406372L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_LOEP_ID")
	@Column(name = "LOEP_ID")
    private Long id;
    
	@ManyToOne
    @JoinColumn(name = "LOCU_ID")
    @NotNull
    @Valid
    private Locus locus;
	
	@NotNull
    @Column(name = "LOEP_TX_PRIMEIRO_ALELO")
    private String primeiroAlelo;

	@NotNull
    @Column(name = "LOEP_TX_SEGUNDO_ALELO")
    private String segundoAlelo;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "BUPR_ID")
	private BuscaPreliminar buscaPreliminar;
	

	public BuscaPreliminar getBuscaPreliminar() {
		return buscaPreliminar;
	}

	public void setBuscaPreliminar(BuscaPreliminar buscaPreliminar) {
		this.buscaPreliminar = buscaPreliminar;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Locus getLocus() {
		return locus;
	}

	public void setLocus(Locus locus) {
		this.locus = locus;
	}

	public String getPrimeiroAlelo() {
		return primeiroAlelo;
	}

	public void setPrimeiroAlelo(String primeiroAlelo) {
		this.primeiroAlelo = primeiroAlelo;
	}

	public String getSegundoAlelo() {
		return segundoAlelo;
	}

	public void setSegundoAlelo(String segundoAlelo) {
		this.segundoAlelo = segundoAlelo;
	}
    

}
