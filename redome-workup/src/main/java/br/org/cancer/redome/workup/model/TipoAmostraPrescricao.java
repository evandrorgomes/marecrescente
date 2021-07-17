package br.org.cancer.redome.workup.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe associativa de tipo amostra e prescrição.
 * @author Filipe Paes
 *
 */
@Entity
@Table(name = "TIPO_AMOSTRA_PRESCRICAO")
@SequenceGenerator(name = "SQ_TIAP_ID", sequenceName = "SQ_TIAP_ID", allocationSize = 1)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TipoAmostraPrescricao implements Serializable {
	
	private static final long serialVersionUID = 2233122325862850172L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_TIAP_ID")
	@Column(name = "TIAP_ID")
	private Long id;
	
	@Column(name="TIAP_NR_ML_AMOSTRA")
	private Integer ml;
	
	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "TIAM_ID")
	private TipoAmostra tipoAmostra;
	
	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "PRES_ID")
	private PrescricaoMedula prescricao;
	
	@Column(name="TIAP_TX_OUTRO_TIPO_AMOSTRA")
	private String descricaoOutrosExames;
	
	
}
