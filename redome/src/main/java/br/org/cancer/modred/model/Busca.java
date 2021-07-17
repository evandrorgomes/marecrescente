package br.org.cancer.modred.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.AvaliacaoPrescricaoView;
import br.org.cancer.modred.controller.view.AvaliacaoResultadoWorkupView;
import br.org.cancer.modred.controller.view.BuscaView;
import br.org.cancer.modred.controller.view.LaboratorioView;
import br.org.cancer.modred.controller.view.PacienteView;
import br.org.cancer.modred.controller.view.PedidoColetaView;
import br.org.cancer.modred.controller.view.PedidoWorkupView;
import br.org.cancer.modred.controller.view.PrescricaoView;
import br.org.cancer.modred.controller.view.SolicitacaoView;
import br.org.cancer.modred.controller.view.TransportadoraView;
import br.org.cancer.modred.model.security.Usuario;
import br.org.cancer.modred.service.impl.custom.EntityObservable;

/**
 * Classe de persistencia de Busca.
 * 
 * @author Filipe Paes
 */
@Entity
@SequenceGenerator(name = "SQ_BUSC_ID", sequenceName = "SQ_BUSC_ID", allocationSize = 1)
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@Table(name = "BUSCA")
public class Busca extends EntityObservable implements Serializable {

	private static final long serialVersionUID = 6778140134277292133L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_BUSC_ID")
	@Column(name = "BUSC_ID")
	@JsonView({BuscaView.Busca.class,BuscaView.DialogoBusca.class, PacienteView.Consulta.class, SolicitacaoView.detalhe.class })
	private Long id;

	@ManyToOne
	@JoinColumn(name = "PACI_NR_RMR")
	@JsonView({	BuscaView.Busca.class, TransportadoraView.Listar.class, LaboratorioView.ListarReceberExame.class,
				TransportadoraView.AgendarTransporte.class, PrescricaoView.AutorizacaoPacienteListar.class,
				AvaliacaoResultadoWorkupView.ListarAvaliacao.class, AvaliacaoPrescricaoView.Detalhe.class, SolicitacaoView.detalhe.class,
				SolicitacaoView.listar.class})
	private Paciente paciente;

	@ManyToOne
	@JoinColumn(name = "STBU_ID")
	@JsonView({ BuscaView.Busca.class, PacienteView.Detalhe.class })
	private StatusBusca status;

	@ManyToOne
	@JoinColumn(name = "USUA_ID")
	@JsonView(BuscaView.Busca.class)
	private Usuario usuario;
	
	@ManyToOne
	@JoinColumn(name = "CETR_ID_CENTRO_TRANSPLANTE")
	@JsonView({ BuscaView.Busca.class, PacienteView.Consulta.class, TransportadoraView.Listar.class,
			TransportadoraView.AgendarTransporte.class, PedidoColetaView.SugerirDataColeta.class,
			PedidoWorkupView.SugerirDataWorkup.class, PedidoWorkupView.AgendamentoWorkup.class, SolicitacaoView.detalhe.class})
	private CentroTransplante centroTransplante;
	
	@NotAudited
	@OneToMany(mappedBy="busca")
	private List<Match> matchs;
	
	@Column(name = "PACI_IN_ACEITA_MISMATCH")
	private Integer aceitaMismatch;
	
	/**
	 * Data e hora da data ultima analise tecnica.
	 */
	@Column(name = "BUSC_DT_ANALISE")
	@JsonView({BuscaView.Busca.class, PacienteView.Rascunho.class })
	private LocalDateTime dataUltimaAnalise;
	
	public Busca() {
		super();
	}
	
	public Busca(Long id) {
		this.id = id;;
	}

	/**
	 * Construtor com campos de busca.
	 * 
	 * @param paciente - paciente do processo de busca.
	 * @param status - status da busca.
	 * @param usuario - analista responsavel pela busca.
	 * @param aceitaMismatch - campo do paciente se aceita ou não mismatch
	 */
	public Busca(Paciente paciente, StatusBusca status, Usuario usuario, Integer aceitaMismatch) {
		this();
		this.paciente = paciente;
		this.status = status;
		this.usuario = usuario;
		this.aceitaMismatch = aceitaMismatch;
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
	 * @return the paciente
	 */
	public Paciente getPaciente() {
		return paciente;
	}

	/**
	 * @param paciente the paciente to set
	 */
	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	/**
	 * @return the status
	 */
	public StatusBusca getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(StatusBusca status) {
		this.status = status;
	}

	/**
	 * @return the usuario responsável pela busca.
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the aceitaMismatch
	 */
	public Integer getAceitaMismatch() {
		return aceitaMismatch;
	}

	/**
	 * @param aceitaMismatch the aceitaMismatch to set
	 */
	public void setAceitaMismatch(Integer aceitaMismatch) {
		this.aceitaMismatch = aceitaMismatch;
	}

	/**
	 * @return the matchs
	 */
	public List<Match> getMatchs() {
		return matchs;
	}

	/**
	 * @param matchs the matchs to set
	 */
	public void setMatchs(List<Match> matchs) {
		this.matchs = matchs;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((paciente == null) ? 0 : paciente.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj){
			return true;
		}
		if (obj == null){
			return false;
		}
		if (getClass() != obj.getClass()){
			return false;
		}
		Busca other = (Busca) obj;
		if (paciente == null) {
			if (other.paciente != null){
				return false;
			}
		}
		else if (!paciente.equals(other.paciente)){
			return false;
		}
		if (status == null) {
			if (other.status != null){
				return false;
			}
		}
		else if (!status.equals(other.status)){
			return false;
		}
		return true;
	}

	/**
	 * Centro de transplante associado ao paciente.
	 * Durante o cadastro do paciente um CT é informado como sugestão, mas
	 * a escolha definita só ocorre quando o doador é disponibilizado para
	 * prescrição com este paciente (este passo ainda pode ser alterado
	 * e ocorrer no momento do retorno do teste confirmatório).
	 * 
	 * @return centro de transplante confirmado para o paciente.
	 */
	public CentroTransplante getCentroTransplante() {
		return centroTransplante;
	}

	public void setCentroTransplante(CentroTransplante centroTransplante) {
		this.centroTransplante = centroTransplante;
	}

	/**
	 * @return the dataUltimaAnalise
	 */
	public LocalDateTime getDataUltimaAnalise() {
		return dataUltimaAnalise;
	}

	/**
	 * @param dataUltimaAnalise the dataUltimaAnalise to set
	 */
	public void setDataUltimaAnalise(LocalDateTime dataUltimaAnalise) {
		this.dataUltimaAnalise = dataUltimaAnalise;
	}
	
}