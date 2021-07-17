package br.org.cancer.modred.model;

import java.io.Serializable;
import java.time.LocalDateTime;

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
import javax.validation.constraints.NotNull;

import br.org.cancer.modred.model.security.Usuario;

/**
 * Representa o histórico da busca gerado, no momento, quando o CT recusa 
 * ser o responsável por determinada busca (paciente).
 * 
 * @author Pizão
 */
@Entity
@SequenceGenerator(name = "SQ_HIBU_ID", sequenceName = "SQ_HIBU_ID", allocationSize = 1)
@Table(name = "HISTORICO_BUSCA")
public class HistoricoBusca implements Serializable {
	private static final long serialVersionUID = -3540531502653784576L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_HIBU_ID")
	@Column(name = "HIBU_ID")
	private Long id;
	
	@NotNull
	@Column(name = "HIBU_DT_ATUALIZACAO")
	private LocalDateTime dataAtualizacao;

	@Column(name = "HIBU_TX_JUSTIFICATIVA")
	@NotNull
	private String justificativa;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USUA_ID")
	private Usuario usuario;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CETR_ID")
	private CentroTransplante centroTransplanteAnterior;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BUSC_ID")
	private Busca busca;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Data de atualização da busca.
	 * @return data e hora da atualização.
	 */
	public LocalDateTime getDataAtualizacao() {
		return dataAtualizacao;
	}

	/**
	 * Setar a data de atualização que ocorreu o evento.
	 * 
	 * @param dataAtualizacao data e hora da atualização.
	 */
	public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	/**
	 * Justificativa para a atualização.
	 * @return texto com a justificativa.
	 */
	public String getJustificativa() {
		return justificativa;
	}

	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}

	/**
	 * Usuário que realizou o evento.
	 * @return usuario.
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	/**
	 * Centro transplantador que estava selecionado para realização o transplante e 
	 * foi recusado.
	 * @return centro de transplante que estava selecionado.
	 */
	public CentroTransplante getCentroTransplanteAnterior() {
		return centroTransplanteAnterior;
	}

	public void setCentroTransplanteAnterior(CentroTransplante centroTransplanteAnterior) {
		this.centroTransplanteAnterior = centroTransplanteAnterior;
	}

	/**
	 * Busca que sofreu a atualização.
	 * 
	 * @return referência para busca.
	 */
	public Busca getBusca() {
		return busca;
	}

	public void setBusca(Busca busca) {
		this.busca = busca;
	}

}