package br.org.cancer.redome.notificacao.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;

import br.org.cancer.redome.notificacao.dto.NotificacaoDTO;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;

/**
 * Classe que define os atributos contidos na entidade
 * que representa uma notificação associado a um determinado
 * paciente enviado para um determinado usuário.
 * 
 * @author ergomes
 * 
 */
@Entity
@SequenceGenerator(name = "SQ_NOTI_ID", sequenceName = "SQ_NOTI_ID", allocationSize = 1)
@Table(name = "NOTIFICACAO")
@Data
@Builder
public class Notificacao implements Serializable {
	private static final long serialVersionUID = -6115279067378891892L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_NOTI_ID")
	@Column(name = "NOTI_ID")
	private Long id;

	@Column(name = "NOTI_TX_DESCRICAO")
	private String descricao;

	@OneToOne
	@JoinColumn(name = "CANO_ID")
	private CategoriaNotificacao categoria;

	@Column(name = "PACI_NR_RMR")
	private Long rmr;

	@Column(name = "NOTI_IN_LIDO")
	@Default
	private Boolean lido = false;
	
	/**
	 * Data de criação da notificação.
	 */
	@Column(name = "NOTI_DT_CRIACAO")
	@NotNull
	@CreationTimestamp
	private LocalDateTime dataCriacao;

	/**
	 * Data de leitura da notificação.
	 */
	@Column(name = "NOTI_DT_LIDO")
	protected LocalDateTime dataLeitura;	
	
	/**
	 * Parceiro para o usuário tem relação.
	 */
	@Column(name = "NOTI_ID_PARCEIRO")
	private Long parceiro;

	/**
	 * Usuário para o qual foi criada a notificação.
	 */
	@Transient
	@NotNull
	private Long usuario;
	
	public Notificacao() {
		this.dataCriacao = LocalDateTime.now();
	}

	/**
	 * @param id
	 * @param descricao
	 * @param categoria
	 * @param paciente
	 * @param lido
	 * @param dataCriacao
	 * @param dataLeitura
	 * @param parceiro
	 * @param usuario
	 */
	public Notificacao(Long id, String descricao, CategoriaNotificacao categoria, Long rmr, Boolean lido,
			LocalDateTime dataCriacao, LocalDateTime dataLeitura, Long parceiro, Long usuario) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.categoria = categoria;
		this.rmr = rmr;
		this.lido = lido;
		this.dataCriacao = dataCriacao;
		this.dataLeitura = dataLeitura;
		this.parceiro = parceiro;
		this.usuario = usuario;
	}
	
	public static Notificacao parse(NotificacaoDTO notificacaoDto) {
		
		Notificacao notificacaoRetorno = new Notificacao();
		
		notificacaoRetorno.setId(notificacaoDto.getId());
		notificacaoRetorno.setDescricao(notificacaoDto.getDescricao());
		notificacaoRetorno.setCategoria(new CategoriaNotificacao(notificacaoDto.getCategoriaId()));
		notificacaoRetorno.setRmr(notificacaoDto.getRmr());
		notificacaoRetorno.setLido(notificacaoDto.getLido());
		notificacaoRetorno.setDataCriacao(notificacaoDto.getDataCriacao());
		notificacaoRetorno.setDataLeitura(notificacaoDto.getDataLeitura());
		notificacaoRetorno.setParceiro(notificacaoDto.getParceiro());
		//notificacaoRetorno.setUsuario(notificacaoDto.getUsuarioId());
		
		return notificacaoRetorno;
	}

}