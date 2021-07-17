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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.AnaliseMedicaView;
import br.org.cancer.modred.model.security.Usuario;

/**
 * Analise médica realizada a pedido dos colaboradores de contato com Doador que caso aprovada
 * será pedido o pedido de exame do doador e caso reprovado será inativado o doador.
 * @author Filipe Paes
 *
 */
@Entity
@SequenceGenerator(name = "SQ_ANME_ID", sequenceName = "SQ_ANME_ID", allocationSize = 1)
@Table(name = "ANALISE_MEDICA")
public class AnaliseMedica implements Serializable {

	private static final long serialVersionUID = -4698527561651432684L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_ANME_ID")
	@Column(name = "ANME_ID")
	@JsonView({AnaliseMedicaView.ListarTarefas.class, AnaliseMedicaView.Detalhe.class})
	private Long id;

	@ManyToOne
	@JoinColumn(name = "PECO_ID", nullable = true)
	@JsonView({AnaliseMedicaView.ListarTarefas.class, AnaliseMedicaView.Detalhe.class})
	private PedidoContato pedidoContato;

	@ManyToOne
	@JoinColumn(name = "USUA_ID", nullable = true)
	private Usuario usuario;

	@Column(name = "AMNE_IN_PROSSEGUIR")
	private Boolean prosseguir;

	@ManyToOne
	@JoinColumn(name = "MOSD_ID")
	private MotivoStatusDoador motivoStatusDoador;

	@Column(name = "ANME_DT_CRIACAO")
	@JsonView({AnaliseMedicaView.ListarTarefas.class, AnaliseMedicaView.Detalhe.class})
	private LocalDateTime dataCriacao;

	@Column(name = "ANME_DT_REALIZACAO_ANALISE")
	@JsonView({AnaliseMedicaView.ListarTarefas.class, AnaliseMedicaView.Detalhe.class})
	private LocalDateTime dataAnalise;
	
	@Column(name = "ANME_NR_TEMPO_INATIVACAO_TEMP")	
	private Long tempoInativacaoTemporaria;

	
	public AnaliseMedica() {
		this.dataCriacao = LocalDateTime.now();
	}
	
	public AnaliseMedica(PedidoContato pedidoContato) {
		this();
		this.pedidoContato = pedidoContato;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PedidoContato getPedidoContato() {
		return pedidoContato;
	}

	public void setPedidoContato(PedidoContato pedidoContato) {
		this.pedidoContato = pedidoContato;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Boolean getProsseguir() {
		return prosseguir;
	}

	public void setProsseguir(Boolean prosseguir) {
		this.prosseguir = prosseguir;
	}

	public MotivoStatusDoador getMotivoStatusDoador() {
		return motivoStatusDoador;
	}

	public void setMotivoStatusDoador(MotivoStatusDoador motivoStatusDoador) {
		this.motivoStatusDoador = motivoStatusDoador;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public LocalDateTime getDataAnalise() {
		return dataAnalise;
	}

	public void setDataAnalise(LocalDateTime dataAnalise) {
		this.dataAnalise = dataAnalise;
	}
	
	/**
	 * @return the tempoInativacaoTemporaria
	 */
	public Long getTempoInativacaoTemporaria() {
		return tempoInativacaoTemporaria;
	}

	/**
	 * @param tempoInativacaoTemporaria the tempoInativacaoTemporaria to set
	 */
	public void setTempoInativacaoTemporaria(Long tempoInativacaoTemporaria) {
		this.tempoInativacaoTemporaria = tempoInativacaoTemporaria;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataCriacao == null) ? 0 : dataCriacao.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((pedidoContato == null) ? 0 : pedidoContato.hashCode());
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
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
		AnaliseMedica other = (AnaliseMedica) obj;
		if (dataCriacao == null) {
			if (other.dataCriacao != null) {
				return false;
			}
		} 
		else if (!dataCriacao.equals(other.dataCriacao)) {
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
		if (pedidoContato == null) {
			if (other.pedidoContato != null) {
				return false;
			}
		}
		else if (!pedidoContato.equals(other.pedidoContato)) {
			return false;
		}
		if (usuario == null) {
			if (other.usuario != null) {
				return false;
			}
		}
		else if (!usuario.equals(other.usuario)) {
			return false;
		}
		return true;
	}

}
