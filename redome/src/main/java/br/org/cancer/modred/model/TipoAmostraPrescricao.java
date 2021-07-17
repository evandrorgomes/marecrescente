package br.org.cancer.modred.model;

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

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.AvaliacaoPrescricaoView;
import br.org.cancer.modred.controller.view.PedidoTransferenciaCentroView;

/**
 * Classe associativa de tipo amostra e prescrição.
 * @author Filipe Paes
 *
 */
@Entity
@Table(name = "TIPO_AMOSTRA_PRESCRICAO")
@SequenceGenerator(name = "SQ_TIAP_ID", sequenceName = "SQ_TIAP_ID", allocationSize = 1)
public class TipoAmostraPrescricao {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_TIAP_ID")
	@Column(name = "TIAP_ID")
	@JsonView({PedidoTransferenciaCentroView.ListarTarefas.class, PedidoTransferenciaCentroView.Detalhe.class,AvaliacaoPrescricaoView.Detalhe.class})
	private Long id;
	
	@Column(name="TIAP_NR_ML_AMOSTRA")
	@JsonView({AvaliacaoPrescricaoView.Detalhe.class})
	private Integer ml;
	
	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "TIAM_ID")
	@JsonView({AvaliacaoPrescricaoView.Detalhe.class})
	private TipoAmostra tipoAmostra;
	
	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "PRES_ID")
	private Prescricao prescricao;
	
	@Column(name="TIAP_TX_OUTRO_TIPO_AMOSTRA")
	@JsonView({AvaliacaoPrescricaoView.Detalhe.class})
	private String descricaoOutrosExames;
	
	
	

	public TipoAmostraPrescricao(Long id, Integer ml, TipoAmostra tipoAmostra, Prescricao prescricao,
			String descricaoOutrosExames) {
		super();
		this.id = id;
		this.ml = ml;
		this.tipoAmostra = tipoAmostra;
		this.prescricao = prescricao;
		this.descricaoOutrosExames = descricaoOutrosExames;
	}
	
	public TipoAmostraPrescricao() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getMl() {
		return ml;
	}

	public void setMl(Integer ml) {
		this.ml = ml;
	}

	public TipoAmostra getTipoAmostra() {
		return tipoAmostra;
	}

	public void setTipoAmostra(TipoAmostra tipoAmostra) {
		this.tipoAmostra = tipoAmostra;
	}

	public Prescricao getPrescricao() {
		return prescricao;
	}

	public void setPrescricao(Prescricao prescricao) {
		this.prescricao = prescricao;
	}

	public String getDescricaoOutrosExames() {
		return descricaoOutrosExames;
	}

	public void setDescricaoOutrosExames(String descricaoOutrosExames) {
		this.descricaoOutrosExames = descricaoOutrosExames;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((ml == null) ? 0 : ml.hashCode());
		result = prime * result + ((prescricao == null) ? 0 : prescricao.hashCode());
		result = prime * result + ((tipoAmostra == null) ? 0 : tipoAmostra.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TipoAmostraPrescricao other = (TipoAmostraPrescricao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (ml == null) {
			if (other.ml != null)
				return false;
		} else if (!ml.equals(other.ml))
			return false;
		if (prescricao == null) {
			if (other.prescricao != null)
				return false;
		} else if (!prescricao.equals(other.prescricao))
			return false;
		if (tipoAmostra == null) {
			if (other.tipoAmostra != null)
				return false;
		} else if (!tipoAmostra.equals(other.tipoAmostra))
			return false;
		return true;
	}
	
	
	
	
	
	
	
}
