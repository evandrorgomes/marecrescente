package br.org.cancer.modred.controller.dto.doador;

import java.io.Serializable;

/**
 * Classe que representa a relação entre locus e exame.
 * 
 * @author evandro.gomes
 *
 */
public class LocusExamePkDTO implements Serializable {

	private static final long serialVersionUID = 8443003648092357956L;

	private LocusDTO locus;
    private ExameDTO exame;

        
    /**
	 * 
	 */
	public LocusExamePkDTO() {}

	/**
	 * @param new Locus
	 * @param exame
	 */
	public LocusExamePkDTO(String Locus, ExameDTO exame) {
		this.locus = new LocusDTO(Locus);
		this.exame = exame;
	}

	public LocusDTO getLocus() {
        return locus;
    }
    
    public void setLocus(LocusDTO locus) {
        this.locus = locus;
    }
    
    public ExameDTO getExame() {
        return exame;
    }
    
    public void setExame(ExameDTO exame) {
        this.exame = exame;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( exame == null ) ? 0 : exame.hashCode() );
        result = prime * result + ( ( locus == null ) ? 0 : locus.hashCode() );
        return result;
    }


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
        LocusExamePkDTO other = (LocusExamePkDTO) obj;
        if (exame == null) {
            if (other.exame != null) {
                return false;
            }
        }
        else
            if (!exame.equals(other.exame)) {
                return false;
            }
        if (locus == null) {
            if (other.locus != null) {
                return false;
            }
        }
        else
            if (!locus.equals(other.locus)) {
                return false;
            }
        return true;
    }
    
}
