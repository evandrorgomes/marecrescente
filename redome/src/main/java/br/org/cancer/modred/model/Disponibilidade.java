package br.org.cancer.modred.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.DisponibilidadeView;
import br.org.cancer.modred.controller.view.PedidoColetaView;
import br.org.cancer.modred.controller.view.PedidoWorkupView;
import br.org.cancer.modred.service.impl.custom.EntityObservable;
import br.org.cancer.modred.service.impl.observers.ConfirmacaoDataRealizacaoColetaObserver;
import br.org.cancer.modred.service.impl.observers.ConfirmacaoDataRealizacaoWorkupObserver;

/**
 * Disponibilidade é a sugestão de agendamento ocorrida entre o analista de workup e o centro de transplante
 * visando fechar uma data para realização do workup ou da coleta.
 * 
 * @author Filipe Paes
 */
@Entity
@Table(name="DISPONIBILIDADE")
@SequenceGenerator(name = "SQ_DISP_ID", sequenceName = "SQ_DISP_ID", allocationSize = 1)
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class Disponibilidade extends EntityObservable  implements Serializable {

	private static final long serialVersionUID = 3819619782895028066L;

	@Id
	@Column(name = "DISP_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_DISP_ID")
	@JsonView(DisponibilidadeView.VisualizacaoCentroTransplante.class)
	private Long id;
		
	@Column(name = "DISP_DT_CRIACAO")
	@NotNull
	private LocalDateTime dataCriacao;

	@Column(name = "DISP_DT_ACEITE")
	@JsonView({PedidoWorkupView.DetalheWorkup.class, PedidoColetaView.DetalheColeta.class})
	private LocalDateTime dataAceite;

	@Column(name = "DISP_TX_DESCRICAO")
	@JsonView(value = {PedidoWorkupView.DetalheWorkup.class, DisponibilidadeView.VisualizacaoCentroTransplante.class, 
			PedidoColetaView.DetalheColeta.class})
	@NotNull
	private String descricao;

	@ManyToOne
	@JoinColumn(name = "PEWO_ID")
	@JsonView(value = {DisponibilidadeView.VisualizacaoCentroTransplante.class})
	private PedidoWorkup pedidoWorkup;
	
	@ManyToOne
	@JoinColumn(name = "PECL_ID")
	@JsonView(value = {DisponibilidadeView.VisualizacaoCentroTransplante.class})
	private PedidoColeta pedidoColeta;
	
	public Disponibilidade() {
		super();
		this.dataCriacao = LocalDateTime.now();
		addObserver(ConfirmacaoDataRealizacaoWorkupObserver.class);
		addObserver(ConfirmacaoDataRealizacaoColetaObserver.class);
	}
	
	/**
	 * Construtor sobrecarregado para facilitar a carga da lista vinda do back-end.
	 * 
	 * @param id da disponibilidade.
	 * @param descricao descrição da disponibilidade.
	 * @param dataCriacao data de criação 
	 */
	public Disponibilidade(Long id, String descricao, LocalDateTime dataCriacao) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.dataCriacao = dataCriacao;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the dataAceite
	 */
	public LocalDateTime getDataAceite() {
		return dataAceite;
	}

	/**
	 * @param dataAceite the dataAceite to set
	 */
	public void setDataAceite(LocalDateTime dataAceite) {
		this.dataAceite = dataAceite;
	}

	/**
	 * @return the dataCriacao
	 */
	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	/**
	 * @param dataCriacao the dataCriacao to set
	 */
	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	/**
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * @param descricao the descricao to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * @return the pedidoWorkup
	 */
	public PedidoWorkup getPedidoWorkup() {
		return pedidoWorkup;
	}

	/**
	 * @param pedidoWorkup the pedidoWorkup to set
	 */
	public void setPedidoWorkup(PedidoWorkup pedidoWorkup) {
		this.pedidoWorkup = pedidoWorkup;
	}

	@PrePersist
	private void gravarComDataCriacao(){
		this.dataCriacao = LocalDateTime.now();
	}
	
	/**
	 * @return the pedidoColeta
	 */
	public PedidoColeta getPedidoColeta() {
		return pedidoColeta;
	}

	
	/**
	 * @param pedidoColeta the pedidoColeta to set
	 */
	public void setPedidoColeta(PedidoColeta pedidoColeta) {
		this.pedidoColeta = pedidoColeta;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataCriacao == null) ? 0 : dataCriacao.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Disponibilidade other = (Disponibilidade) obj;
		if (dataCriacao == null) {
			if (other.dataCriacao != null)
				return false;
		} else if (!dataCriacao.equals(other.dataCriacao))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	

}