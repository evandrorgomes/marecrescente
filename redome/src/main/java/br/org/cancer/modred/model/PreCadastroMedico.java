package br.org.cancer.modred.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.PreCadastroMedicoView;
import br.org.cancer.modred.model.annotations.AssertTrueCustom;
import br.org.cancer.modred.model.domain.StatusPreCadastro;
import br.org.cancer.modred.model.security.Usuario;

/**
 * Classe que representa a entidade de pré cadastro de médico no Redome.
 * 
 * @author Rafael Pizão
 *
 */
@Entity
@SequenceGenerator(name = "SQ_PRCM_ID", sequenceName = "SQ_PRCM_ID", allocationSize = 1)
@Table(name = "PRE_CADASTRO_MEDICO")
public class PreCadastroMedico implements Serializable {
	private static final long serialVersionUID = -5695460978946471921L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_PRCM_ID")
	@Column(name = "PRCM_ID")
	@JsonView({PreCadastroMedicoView.Listar.class, PreCadastroMedicoView.Detalhe.class})
	private Long id;

	@NotNull
	@Column(name = "PRCM_TX_CRM")
	@JsonView(PreCadastroMedicoView.Detalhe.class)
	private String crm;

	@NotNull
	@Column(name = "PRCM_TX_NOME")
	@JsonView({PreCadastroMedicoView.Listar.class, PreCadastroMedicoView.Detalhe.class})
	private String nome;
	
	@NotNull
	@Column(name = "PRCM_TX_LOGIN")
	@JsonView(PreCadastroMedicoView.Detalhe.class)
	private String login;
	
	@NotNull
	@Column(name = "PRCM_TX_ARQUIVO_CRM")
	@JsonView(PreCadastroMedicoView.Detalhe.class)
	private String arquivoCrm;
	
	@NotNull
	@Column(name = "PRCM_TX_STATUS")
	@Enumerated(EnumType.STRING)
	private StatusPreCadastro status;
	
	@NotNull
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "PCME_ID")
	@Fetch(FetchMode.JOIN)
	@JsonView(PreCadastroMedicoView.Detalhe.class)
	private PreCadastroMedicoEndereco endereco;

	@OneToMany(mappedBy = "preCadastroMedico", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonView(PreCadastroMedicoView.Detalhe.class)
    private List<PreCadastroMedicoTelefone> telefones;
	
	@OneToMany(mappedBy = "preCadastroMedico", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonView(PreCadastroMedicoView.Detalhe.class)
    private List<PreCadastroMedicoEmail> emails;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "PRE_CAD_MEDICO_CT_REFERENCIA", 
	joinColumns = @JoinColumn(name = "PRCM_ID", insertable = true), 
	inverseJoinColumns = @JoinColumn(name = "CETR_ID", insertable = true))
	@JsonView(PreCadastroMedicoView.Detalhe.class)
	private List<CentroTransplante> centrosReferencia;
	
	@NotNull
	@Column(name = "PRCM_DT_SOLICITACAO")
	@JsonView({PreCadastroMedicoView.Listar.class, PreCadastroMedicoView.Detalhe.class})
	private LocalDateTime dataSolicitacao;
	
	@NotNull
	@Column(name = "PRCM_DT_ATUALIZACAO")
	private LocalDateTime dataAtualizacao;
		
	/**
	 * Usuário responsável pela aprovação ou reprovação.
	 */
	@ManyToOne
	@JoinColumn(name = "USUA_ID")
	private Usuario usuarioResponsavel;	
	
	@Column(name = "PRCM_TX_JUSTIFICATIVA")
	private String justificativa;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCrm() {
		return crm;
	}

	public void setCrm(String crm) {
		this.crm = crm;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getArquivoCrm() {
		return arquivoCrm;
	}

	public void setArquivoCrm(String arquivoCrm) {
		this.arquivoCrm = arquivoCrm;
	}

	public PreCadastroMedicoEndereco getEndereco() {
		return endereco;
	}

	public void setEndereco(PreCadastroMedicoEndereco endereco) {
		this.endereco = endereco;
	}

	public StatusPreCadastro getStatus() {
		return status;
	}

	public void setStatus(StatusPreCadastro status) {
		this.status = status;
	}

	public List<PreCadastroMedicoTelefone> getTelefones() {
		return telefones;
	}

	/**
	 * @param telefones - Telefones
	 */
	public void setTelefones(List<PreCadastroMedicoTelefone> telefones) {
		if(CollectionUtils.isNotEmpty(telefones)){
			telefones.forEach(email -> {
				email.setPreCadastroMedico(this);
			});
		}
		
		this.telefones = telefones;
	}

	public List<PreCadastroMedicoEmail> getEmails() {
		return emails;
	}
	
	/**
	 * Retorna o e-mail marcado como principal.
	 * 
	 * @return entidade com e-mail principal do pré cadastro.
	 */
	public PreCadastroMedicoEmail getEmailPrincipal(){
		return getEmails().stream().filter(email -> {
			return Boolean.TRUE.equals(email.getPrincipal());
		}).findFirst().get();
	}

	/**
	 * @param emails - emails
	 */
	public void setEmails(List<PreCadastroMedicoEmail> emails) {
		if(CollectionUtils.isNotEmpty(emails)){
			emails.forEach(email -> {
				email.setPreCadastroMedico(this);
			});
		}
		
		this.emails = emails;
	}

	public List<CentroTransplante> getCentrosReferencia() {
		return centrosReferencia;
	}

	public void setCentrosReferencia(List<CentroTransplante> centrosReferencia) {
		this.centrosReferencia = centrosReferencia;
	}

	public LocalDateTime getDataSolicitacao() {
		return dataSolicitacao;
	}

	public void setDataSolicitacao(LocalDateTime dataSolicitacao) {
		this.dataSolicitacao = dataSolicitacao;
	}

	/**
	 * @return the dataAtualizacao
	 */
	public LocalDateTime getDataAtualizacao() {
		return dataAtualizacao;
	}

	/**
	 * @param dataAtualizacao the dataAtualizacao to set
	 */
	public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	/**
	 * @return the usuarioResponsavel
	 */
	public Usuario getUsuarioResponsavel() {
		return usuarioResponsavel;
	}

	/**
	 * @param usuarioResponsavel the usuarioResponsavel to set
	 */
	public void setUsuarioResponsavel(Usuario usuarioResponsavel) {
		this.usuarioResponsavel = usuarioResponsavel;
	}

	/**
	 * @return the justificativa
	 */
	public String getJustificativa() {
		return justificativa;
	}

	/**
	 * @param justificativa the justificativa to set
	 */
	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}
	
	/**
	 * Método Bean Validation para verificar obrigatoriedade da justificativa quando o status for reprovado.
	 * 
	 * @return boolean true se a justificativa estiver preeenchida e o status for REPROVADO. Caso contrário, deve retornar FALSE.
	 */
	@AssertTrueCustom(message = "{javax.validation.constraints.NotNull.message}", field = "justificativa")
	@JsonIgnore
	public boolean isJustificativaObrigatorio() {
		if (getStatus().equals(StatusPreCadastro.REPROVADO)) {
			return StringUtils.isNotEmpty(this.justificativa);
		}
		return true;
	}
	
	/**
	 * Método Bean Validation para verificar obrigatoriedade do usuarioResponsavel quando o status for aprovado ou reprovado.
	 * 
	 * @return boolean true se o usuario responsavel estiver preeenchido e o status for APROVADO ou REPROVADO. 
	 * 				    Caso contrário, deve retornar FALSE.
	 */
	@AssertTrueCustom(message = "{javax.validation.constraints.NotNull.message}", field = "usuarioResponsavel")
	@JsonIgnore
	public boolean isUsuarioResponsavelObrigatorio() {
		if (getStatus().equals(StatusPreCadastro.APROVADO) || getStatus().equals(StatusPreCadastro.REPROVADO)) {
			return Optional.ofNullable(this.usuarioResponsavel).isPresent();
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((crm == null) ? 0 : crm.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		return result;
	}

	/* (non-Javadoc)
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
		PreCadastroMedico other = (PreCadastroMedico) obj;
		if (crm == null) {
			if (other.crm != null) {
				return false;
			}
		} 
		else if (!crm.equals(other.crm)) {
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
		if (login == null) {
			if (other.login != null) {
				return false;
			}
		} 
		else if (!login.equals(other.login)) {
			return false;
		}
		return true;
	}
	

}
