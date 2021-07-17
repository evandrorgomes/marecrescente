package br.org.cancer.modred.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import br.org.cancer.modred.controller.view.PacienteView;
import br.org.cancer.modred.controller.view.PedidoTransferenciaCentroView;
import br.org.cancer.modred.model.annotations.CustomPast;

/**
 * Classe de persistencia de Diagnosticos.
 * 
 * @author bruno.sousa
 * 
 */
@Entity
@Table(name = "DIAGNOSTICO")
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class Diagnostico implements Serializable {

    private static final long serialVersionUID = 1835335332708211001L;

    /**
     * Informa que o diagnostico pertence a esse paciente.
     */
    @Id
    @JsonView({PacienteView.Diagnostico.class })
    private Long id;

    @OneToOne
    @JoinColumn(name = "PACI_NR_RMR")
    @MapsId
    @JsonView(PacienteView.Diagnostico.class)
    @JsonProperty(access = Access.READ_ONLY)
    private Paciente paciente;
    /**
     * Data do diagnostico.
     */
    @JsonView({PacienteView.Detalhe.class, PacienteView.Diagnostico.class, PacienteView.Rascunho.class })
    @Column(name = "DIAG_DT_DIAGNOSTICO")
    @NotNull
    @CustomPast
    private LocalDate dataDiagnostico;

    /**
     * cid associada ao diagnostico.
     */
    @ManyToOne
    @JoinColumn(name = "CID_ID")
    @Valid
    @NotNull
    @JsonView({ PacienteView.Detalhe.class, PacienteView.DadosPessoais.class,
            PacienteView.IdentificacaoCompleta.class, PacienteView.IdentificacaoParcial.class,
            PedidoTransferenciaCentroView.ListarTarefas.class, PacienteView.Diagnostico.class,
            PacienteView.Rascunho.class})
    private Cid cid;

    /**
     * Método do construtor da classe.
     */
    public Diagnostico() {}

    /**
     * Retorna a primary key do diagnostico.
     * 
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Seta a primary key do diagnostico.
     * 
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retorna a primary key do diagnostico.
     * 
     * @return dataDiagnostico
     */
    public LocalDate getDataDiagnostico() {
        return dataDiagnostico;
    }

    /**
     * Seta a primary key do diagnostico.
     * 
     * @param dataDiagnostico
     */
    public void setDataDiagnostico(LocalDate dataDiagnostico) {
        this.dataDiagnostico = dataDiagnostico;
    }

    /**
     * @return cid
     */
    public Cid getCid() {
        return cid;
    }

    /**
     * @param cid
     *            cid
     */
    public void setCid(Cid cid) {
        if (cid != null && cid.getId() == null) {
            cid = null;
        }
        this.cid = cid;
    }

    /**
     * @return the paciente
     */
    public Paciente getPaciente() {
        return paciente;
    }

    /**
     * @param paciente
     *            the paciente to set
     */
    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( cid == null ) ? 0 : cid.hashCode() );
        result = prime * result + ( ( dataDiagnostico == null ) ? 0 : dataDiagnostico.hashCode() );
        result = prime * result + ( ( id == null ) ? 0 : id.hashCode() );
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!( obj instanceof Diagnostico )) {
            return false;
        }
        Diagnostico other = (Diagnostico) obj;
        if (cid == null) {
            if (other.cid != null) {
                return false;
            }
        }
        else
            if (!cid.equals(other.cid)) {
                return false;
            }
        if (dataDiagnostico == null) {
            if (other.dataDiagnostico != null) {
                return false;
            }
        }
        else
            if (!dataDiagnostico.equals(other.dataDiagnostico)) {
                return false;
            }
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
}