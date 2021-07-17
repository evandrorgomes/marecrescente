package br.org.cancer.modred.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.AnaliseMedicaView;
import br.org.cancer.modred.controller.view.DoadorView;
import br.org.cancer.modred.controller.view.PedidoExameView;
import br.org.cancer.modred.controller.view.TentativaContatoDoadorView;
import br.org.cancer.modred.model.security.Usuario;

/**
 * Representa a tentativa de contato com o doador.
 * 
 * @author Pizão
 * 
 */
@Entity
@SequenceGenerator(name = "SQ_TECD_ID", sequenceName = "SQ_TECD_ID", allocationSize = 1)
@Table(name = "TENTATIVA_CONTATO_DOADOR")
public class TentativaContatoDoador implements Serializable {

	private static final long serialVersionUID = 2546987101247125528L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_TECD_ID")
	@Column(name = "TECD_ID")
	@JsonView(value = { TentativaContatoDoadorView.Consultar.class, DoadorView.ContatoPassivo.class, 
			DoadorView.ContatoFase2.class, AnaliseMedicaView.Detalhe.class })
	private Long id;

	@Column(name = "TECD_DT_CRIACAO")
	@NotNull
	private LocalDateTime dataCriacao;

	@ManyToOne
	@JoinColumn(name = "USUA_ID")
	private Usuario usuario;

	@ManyToOne
	@JoinColumn(name = "PECO_ID")
	@NotNull
	@JsonView({TentativaContatoDoadorView.Consultar.class, PedidoExameView.ListarTarefas.class, 
		DoadorView.ContatoFase2.class, AnaliseMedicaView.Detalhe.class})
	private PedidoContato pedidoContato;

	@Column(name = "TECD_IN_FINALIZADA")
	@NotNull
	private Boolean finalizada;

	@Column(name = "TECD_HR_INICIO_AGENDAMENTO")
	private LocalDateTime horaInicioAgendamento;

	@Column(name = "TECD_HR_FIM_AGENDAMENTO")
	private LocalDateTime horaFimAgendamento;

	@Column(name = "TECD_DT_AGENDAMENTO")
	private LocalDate dataAgendamento;

	@ManyToOne
	@JoinColumn(name = "COTE_ID")
	@JsonView({TentativaContatoDoadorView.Consultar.class, PedidoExameView.ListarTarefas.class, 
		DoadorView.ContatoFase2.class, AnaliseMedicaView.Detalhe.class})
	private ContatoTelefonicoDoador contatoTelefonicoDoador;
	
	public TentativaContatoDoador() {
		this.dataCriacao = LocalDateTime.now();
		this.finalizada = false;
	}

	public TentativaContatoDoador(PedidoContato pedidoContato) {
		this();
		this.pedidoContato = pedidoContato;
	}

	/**
	 * Construtor passando o ID.
	 * 
	 * @param id
	 */
	public TentativaContatoDoador(Long id) {
		super();
		this.id = id;
	}

	/**
	 * Identificador da entidade.
	 * 
	 * @return ID da entidade.
	 */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Data de realização da tentativa de contato.
	 * 
	 * @return data de criação da tentativa.
	 */
	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	/**
	 * Retorna o usuário que realizou a tentativa.
	 * 
	 * @return usuário de criação.
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}


	public PedidoContato getPedidoContato() {
		return pedidoContato;
	}

	/**
	 * Seta o pedido de contato para uma determinada tentativa de contato.
	 * 
	 * @param pedidoContato pedido de contato associado.
	 */
	public void setPedidoContato(PedidoContato pedidoContato) {
		this.pedidoContato = pedidoContato;
	}

	/**
	 * @return the horaInicioAgendamento
	 */
	public LocalDateTime getHoraInicioAgendamento() {
		return horaInicioAgendamento;
	}

	/**
	 * @param horaInicioAgendamento the horaInicioAgendamento to set
	 */
	public void setHoraInicioAgendamento(LocalDateTime horaInicioAgendamento) {
		this.horaInicioAgendamento = horaInicioAgendamento;
	}

	/**
	 * @return the horaFimAgendamento
	 */
	public LocalDateTime getHoraFimAgendamento() {
		return horaFimAgendamento;
	}

	/**
	 * @param horaFimAgendamento the horaFimAgendamento to set
	 */
	public void setHoraFimAgendamento(LocalDateTime horaFimAgendamento) {
		this.horaFimAgendamento = horaFimAgendamento;
	}

	/**
	 * @return the dataAgendamento
	 */
	public LocalDate getDataAgendamento() {
		return dataAgendamento;
	}
	
	/**
	 * @return the finalizada
	 */
	public Boolean getFinalizada() {
		return finalizada;
	}

	/**
	 * @param finalizada the finalizada to set
	 */
	public void setFinalizada(Boolean finalizada) {
		this.finalizada = finalizada;
	}
	
	/**
	 * @param dataAgendamento the dataAgendamento to set
	 */
	public void setDataAgendamento(LocalDate dataAgendamento) {
		this.dataAgendamento = dataAgendamento;
	}

	/**
	 * @return the contatoTelefonicoDoador
	 */
	public ContatoTelefonicoDoador getContatoTelefonicoDoador() {
		return contatoTelefonicoDoador;
	}

	/**
	 * @param contatoTelefonicoDoador the contatoTelefonicoDoador to set
	 */
	public void setContatoTelefonicoDoador(ContatoTelefonicoDoador contatoTelefonicoDoador) {
		this.contatoTelefonicoDoador = contatoTelefonicoDoador;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( id == null ) ? 0 : id.hashCode() );
		result = prime * result + ( ( pedidoContato == null ) ? 0 : pedidoContato.hashCode() );
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
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
		TentativaContatoDoador other = (TentativaContatoDoador) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		}
		else
			if (!id.equals(other.id)) {
				return false;
			}
		if (pedidoContato == null) {
			if (other.pedidoContato != null) {
				return false;
			}
		}
		else
			if (!pedidoContato.equals(other.pedidoContato)) {
				return false;
			}
		return true;
	}
	
	/**
	 * @return the finalizada
	 */
	public Boolean getNaoFinalizada() {
		if (finalizada != null) {
			return !finalizada;
		}
		return false;
	}

}