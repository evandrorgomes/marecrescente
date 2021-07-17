package br.org.cancer.redome.courier.model.security;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

import br.org.cancer.redome.courier.configuraion.AuditoriaListener;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Customização da classe que representa a tabela onde todas as revisões das
 * entidades são armazenadas.
 * 
 * @author Cintia Oliveira
 *
 */
@RevisionEntity(AuditoriaListener.class)
@Entity
@Table(name = "AUDITORIA")
@Data
@NoArgsConstructor
public class Auditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_AUDI_ID")
    @RevisionNumber
    @SequenceGenerator(name = "SQ_AUDI_ID", sequenceName = "SQ_AUDI_ID", allocationSize = 1)
    @Column(name = "AUDI_ID")
    private Long id;

    // não permite usar a nova api de data
    @RevisionTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "AUDI_DT_DATA")
    @NotNull
    private Date data;

    @Column(name = "AUDI_TX_USUARIO", length = 20)
    @NotNull
    @Setter
    private String usuario;




}
