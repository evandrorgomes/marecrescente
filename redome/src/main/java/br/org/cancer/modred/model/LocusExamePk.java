package br.org.cancer.modred.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.ExameDoadorView;
import br.org.cancer.modred.controller.view.ExameView;
import br.org.cancer.modred.controller.view.GenotipoView;
import br.org.cancer.modred.controller.view.PacienteView;

/**
 * Classe Locus.
 * @author Dev Team
 *
 */
@Embeddable
public class LocusExamePk implements Serializable {
    private static final long serialVersionUID = 1962789948377607701L;

    @ManyToOne
    @JoinColumn(name = "LOCU_ID")
    @NotNull
    @Valid
    @JsonView({ExameView.ListaExame.class,ExameView.ConferirExame.class, PacienteView.Rascunho.class, ExameDoadorView.ExameListaCombo.class, 
    			GenotipoView.ListaExame.class, GenotipoView.Divergente.class})
    private Locus locus;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "EXAM_ID")
    @NotNull
    private Exame exame;

    public LocusExamePk() {}
    
    /**
     * Construtor customizado para que seja possível montar um chave PK a partir
     * das informações fornecidas.
     * 
     * @param locus codigo do lócus.
     * @param exame ID do exame.
     */
    public LocusExamePk(String locus, Exame exame){
    	this();
    	this.locus = new Locus(locus);
    	this.exame = exame;
    }
    
    public Locus getLocus() {
        return locus;
    }

    public void setLocus(Locus locus) {
        this.locus = locus;
    }

    
    public Exame getExame() {
        return exame;
    }
    
    public void setExame(Exame exame) {
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
        LocusExamePk other = (LocusExamePk) obj;
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