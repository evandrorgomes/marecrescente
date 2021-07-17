package br.org.cancer.modred.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.ExameView;
import br.org.cancer.modred.controller.view.GenotipoView;
import br.org.cancer.modred.controller.view.PacienteView;

/**
 * Classe que representa a metodologia usada no exame.
 * 
 * @author Rafael Pizão
 *
 */
@Entity
@Table(name = "METODOLOGIA")
public class Metodologia implements Serializable {

    private static final long serialVersionUID = 4764791472444249514L;

    @Id
    @Column(name = "METO_ID")
    @JsonView({ExameView.ConferirExame.class, PacienteView.Rascunho.class, GenotipoView.ListaExame.class})
    private Long id;

    @Column(name = "METO_TX_SIGLA")
    @JsonView({ExameView.ListaExame.class,ExameView.ConferirExame.class, GenotipoView.ListaExame.class, GenotipoView.Divergente.class})
    private String sigla;

    @Column(name = "METO_TX_DESCRICAO")
    @JsonView({ExameView.ListaExame.class,ExameView.ConferirExame.class, GenotipoView.ListaExame.class, GenotipoView.Divergente.class})
    private String descricao;
    
    @Column(name = "METO_NR_PESO_GENOTIPO")
    private Integer pesoGenotipo;

    /**
     * Construtor padrão.
     */
    public Metodologia() {}

    /**
     * Construtor sobrecarregado.
     * 
     * @param sigla
     * @param descricao
     */
    public Metodologia(String sigla, String descricao) {
        this.sigla = sigla;
        this.descricao = descricao;
    }

    /**
     * Construtor sobrecarregado.
     * 
     * @param id
     * @param sigla
     * @param descricao
     */
    public Metodologia(Long id, String sigla, String descricao) {
        this(sigla, descricao);
        this.id = id;
    }

    /**
     * 
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the sigla
     */
    public String getSigla() {
        return sigla;
    }

    /**
     * @param sigla
     *            the sigla to set
     */
    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    /**
     * @return the descricao
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * @param descricao
     *            the descricao to set
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }
    
	/**
	 * @return
	 */
	public Integer getPesoGenotipo() {
		return pesoGenotipo;
	}

	/**
	 * @param pesoGenotipo the pesoGenotipo to set
	 */
	public void setPesoGenotipo(Integer pesoGenotipo) {
		this.pesoGenotipo = pesoGenotipo;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((pesoGenotipo == null) ? 0 : pesoGenotipo.hashCode());
		result = prime * result + ((sigla == null) ? 0 : sigla.hashCode());
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
		Metodologia other = (Metodologia) obj;
		if (descricao == null) {
			if (other.descricao != null) {
				return false;
			}
		} 
		else if (!descricao.equals(other.descricao)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} 
		else if (!id.equals(other.id)) {
			return false;
		}
		if (pesoGenotipo == null) {
			if (other.pesoGenotipo != null) {
				return false;
			}
		} 
		else if (!pesoGenotipo.equals(other.pesoGenotipo)) {
			return false;
		}
		if (sigla == null) {
			if (other.sigla != null) {
				return false;
			}
		} 
		else if (!sigla.equals(other.sigla)) {
			return false;
		}
		return true;
	}

	
	
}
