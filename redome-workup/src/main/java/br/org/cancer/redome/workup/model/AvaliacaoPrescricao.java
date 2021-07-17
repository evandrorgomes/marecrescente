package br.org.cancer.redome.workup.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.org.cancer.redome.workup.observable.EntityObservable;
import br.org.cancer.redome.workup.observable.impl.AvaliacaoPrescricaoReprovadaObserver;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Classe que representa a entidade de avaliacao de prescrição.
 * 
 * @author Fillipe.queiroz
 *
 */
@Entity
@Table(name = "AVALIACAO_PRESCRICAO")
@SequenceGenerator(name = "SQ_AVPR_ID", sequenceName = "SQ_AVPR_ID", allocationSize = 1)
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class AvaliacaoPrescricao extends EntityObservable implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "AVPR_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_AVPR_ID")
	private long id;

	@Column(name = "AVPR_DT_ATUALIZACAO")
	private LocalDateTime dataAtualizacao;

	@Column(name = "AVPR_DT_CRICAO")
	private LocalDateTime dataCriacao;

	@Column(name = "AVPR_TX_JUSTIFICATIVA_CANCEL")
	private String justificativaCancelamento;

	@Column(name = "AVPR_TX_JUSTIFICATIVA_DESCARTE")
	private String justificativaDescarteFonteCelula;

	@ManyToOne
	@JoinColumn(name = "FOCE_ID")
	private FonteCelula fonteCelula;

	@Column(name = "AVPR_IN_RESULTADO")
	private Boolean aprovado;

	@OneToOne
	@JoinColumn(name = "PRES_ID")	
	private Prescricao prescricao;

	@Builder
	public AvaliacaoPrescricao() {
		super();
		addObserver(AvaliacaoPrescricaoReprovadaObserver.class);
	}


}