package br.org.cancer.modred.controller.dto.doador;

import java.io.Serializable;

/**
 * Classe que representa a relação entre locus e exame.
 * 
 * @author ergomes
 *
 */
public class LocusExameDTO implements Serializable {


	private static final long serialVersionUID = -4135000412789231774L;

	private LocusExamePkDTO id;
	private Long ordem;
	private Long obrigatorio;
	private String tipo;
    private String primeiroAlelo;
    private String segundoAlelo;
    private String codigo;

    /**
	 * 
	 */
	public LocusExameDTO() {}

	/**
     * Construtor sobrecarregado.
     * @param primeiroAlelo
     * @param segundoAlelo
     */
    public LocusExameDTO(String codigo, String primeiroAlelo, String segundoAlelo) {
        this.primeiroAlelo = primeiroAlelo;
        this.segundoAlelo = segundoAlelo;
        this.codigo = codigo;
    }
	
	
	/**
     * Construtor sobrecarregado.
     * @param primeiroAlelo
     * @param segundoAlelo
     */
    public LocusExameDTO(String primeiroAlelo, String segundoAlelo) {
        this.primeiroAlelo = primeiroAlelo;
        this.segundoAlelo = segundoAlelo;
    }
    
 	/**
     * Construtor padrão.
     * @param segundoAlelo 
     * @param primeiroAlelo 
     * @param codigo 
     * @param exame 
     */
    public LocusExameDTO(ExameDTO exame, String locus, String primeiroAlelo, String segundoAlelo) {
		this.id = new LocusExamePkDTO(locus, exame);
		this.primeiroAlelo = primeiroAlelo;
		this.segundoAlelo = segundoAlelo;
    }

	/**
     * @return the primeiroAlelo.
     */
    public String getPrimeiroAlelo() {
        return primeiroAlelo;
    }

    /**
     * @param primeiroAlelo
     *            the primeiroAlelo to set
     */
    public void setPrimeiroAlelo(String primeiroAlelo) {
        this.primeiroAlelo = primeiroAlelo;
    }

    /**
     * @return the segundoAlelo
     */
    public String getSegundoAlelo() {
        return segundoAlelo;
    }

    /**
     * @param segundoAlelo
     *            the segundoAlelo to set
     */
    public void setSegundoAlelo(String segundoAlelo) {
        this.segundoAlelo = segundoAlelo;
    }

    /**
     * @return the id
     */
    public LocusExamePkDTO getId() {
        return id;
    }

    /**
     * Setter do id.
     * @param id
     */
    public void setId(LocusExamePkDTO id) {
        this.id = id;
    }

	/**
	 * @return the ordem
	 */
	public Long getOrdem() {
		return ordem;
	}

	/**
	 * @param ordem the ordem to set
	 */
	public void setOrdem(Long ordem) {
		this.ordem = ordem;
	}

	/**
	 * @return the obrigatorio
	 */
	public Long getObrigatorio() {
		return obrigatorio;
	}

	/**
	 * @param obrigatorio the obrigatorio to set
	 */
	public void setObrigatorio(Long obrigatorio) {
		this.obrigatorio = obrigatorio;
	}

	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	/**
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/* (non-Javadoc)
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
		LocusExameDTO other = (LocusExameDTO) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} 
		else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}
	
}
