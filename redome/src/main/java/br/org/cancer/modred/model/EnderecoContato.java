package br.org.cancer.modred.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.DiscriminatorFormula;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.BuscaView;
import br.org.cancer.modred.controller.view.CentroTransplanteView;
import br.org.cancer.modred.controller.view.DoadorView;
import br.org.cancer.modred.controller.view.LaboratorioView;
import br.org.cancer.modred.controller.view.MedicoView;
import br.org.cancer.modred.controller.view.PacienteView;
import br.org.cancer.modred.controller.view.TransportadoraView;
import br.org.cancer.modred.model.annotations.AssertTrueCustom;

/**
 * Classe de endereço contato.
 * 
 * @author Filipe Paes
 * 
 */
@Entity
@SequenceGenerator(name = "SQ_ENCO_ID", sequenceName = "SQ_ENCO_ID", allocationSize = 1)
@Table(name = "ENDERECO_CONTATO")
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@JsonIgnoreProperties(ignoreUnknown = true)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorFormula("case when PACI_NR_RMR is not null " +
		"	then 'PACI' " +
		"when DOAD_ID is not null " +
		"   then 'DOAD' " +
		"when HEEN_ID is not null " +
		"   then 'HEEN' " +
		"when CETR_ID is not null " +
		"   then 'CETR' " +
		"when LABO_ID is not null " +
		"   then 'LABO' " +
		"when MEDI_ID is not null " +
		"   then 'MEDI' " +
		"else 'Unknown' " +
		"end ")
public abstract class EnderecoContato implements Serializable {

	private static final long serialVersionUID = -8607861467071766963L;

	/**
	 * Chave que identifica com exclusividade uma instância desta classe.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_ENCO_ID")
	@Column(name = "ENCO_ID")
	@JsonView({ LaboratorioView.Detalhe.class, PacienteView.Detalhe.class, BuscaView.Enriquecimento.class, DoadorView.AtualizacaoFase2.class, 
			DoadorView.ContatoPassivo.class, TransportadoraView.AgendarTransporte.class, CentroTransplanteView.Detalhe.class,
			MedicoView.Detalhe.class,TransportadoraView.Detalhe.class, DoadorView.ContatoFase2.class, DoadorView.LogisticaDoador.class})
	protected Long id;
	/**
	 * Número do CEP.
	 */
	@Column(name = "ENCO_CEP")
	@Length(max = 9)
	@JsonView({ LaboratorioView.Detalhe.class, PacienteView.Detalhe.class, BuscaView.Enriquecimento.class, DoadorView.AtualizacaoFase2.class, 
			DoadorView.ContatoPassivo.class, TransportadoraView.AgendarTransporte.class, CentroTransplanteView.Detalhe.class,
			MedicoView.Detalhe.class, PacienteView.Rascunho.class,TransportadoraView.Detalhe.class, DoadorView.ContatoFase2.class,
			DoadorView.LogisticaDoador.class})
	protected String cep;
	/**
	 * País da localidade.
	 */
	@OneToOne
	@JoinColumn(name = "ENCO_ID_PAIS")
	@NotNull
	@JsonView({ LaboratorioView.Detalhe.class, PacienteView.Detalhe.class, BuscaView.Enriquecimento.class, DoadorView.AtualizacaoFase2.class, 
			DoadorView.ContatoPassivo.class, TransportadoraView.AgendarTransporte.class, CentroTransplanteView.Detalhe.class,
			MedicoView.Detalhe.class, PacienteView.Rascunho.class,TransportadoraView.Detalhe.class, DoadorView.ContatoFase2.class,
			DoadorView.LogisticaDoador.class})
	protected Pais pais;
	/**
	 * Número da localidade.
	 */
	@Column(name = "ENCO_TX_NUMERO")
	@JsonView({ LaboratorioView.Detalhe.class, PacienteView.Detalhe.class, BuscaView.Enriquecimento.class, DoadorView.AtualizacaoFase2.class, 
			DoadorView.ContatoPassivo.class, TransportadoraView.AgendarTransporte.class, CentroTransplanteView.Detalhe.class,
			MedicoView.Detalhe.class, PacienteView.Rascunho.class,TransportadoraView.Detalhe.class, DoadorView.ContatoFase2.class,
			DoadorView.LogisticaDoador.class})
	protected String numero;
	/**
	 * Bairro da localidade.
	 */
	@Column(name = "ENCO_TX_BAIRRO")
	@Length(max = 255)
	@JsonView({ LaboratorioView.Detalhe.class, PacienteView.Detalhe.class, BuscaView.Enriquecimento.class, DoadorView.AtualizacaoFase2.class, DoadorView.ContatoPassivo.class, 
			TransportadoraView.AgendarTransporte.class, CentroTransplanteView.Detalhe.class, MedicoView.Detalhe.class,
			PacienteView.Rascunho.class,TransportadoraView.Detalhe.class, DoadorView.ContatoFase2.class, 
			DoadorView.LogisticaDoador.class})
	protected String bairro;
	/**
	 * Complemento da localidade.
	 */
	@Column(name = "ENCO_TX_COMPLEMENTO")
	@Length(max = 50)
	@JsonView({ LaboratorioView.Detalhe.class, PacienteView.Detalhe.class, BuscaView.Enriquecimento.class, DoadorView.AtualizacaoFase2.class, DoadorView.ContatoPassivo.class, 
			TransportadoraView.AgendarTransporte.class, CentroTransplanteView.Detalhe.class, MedicoView.Detalhe.class,
			PacienteView.Rascunho.class,TransportadoraView.Detalhe.class, DoadorView.ContatoFase2.class})
	protected String complemento;
	/**
	 * Endereço caso seja entrangeiro.
	 */
	@Column(name = "ENCO_TX_ENDERECO_ESTRANGEIRO")
	@Length(max = 255)
	@JsonView({ LaboratorioView.Detalhe.class, PacienteView.Detalhe.class, BuscaView.Enriquecimento.class, DoadorView.AtualizacaoFase2.class, DoadorView.ContatoPassivo.class, TransportadoraView.AgendarTransporte.class,
				PacienteView.Rascunho.class,TransportadoraView.Detalhe.class, DoadorView.ContatoFase2.class,
				DoadorView.LogisticaDoador.class})
	protected String enderecoEstrangeiro;
	
	/**
	 * Descrição do logradouro.
	 */
	@Column(name = "ENCO_TX_NOME")
	@Length(max = 255)
	@JsonView({ LaboratorioView.Detalhe.class, PacienteView.Detalhe.class, BuscaView.Enriquecimento.class, DoadorView.AtualizacaoFase2.class, DoadorView.ContatoPassivo.class, 
				TransportadoraView.AgendarTransporte.class, CentroTransplanteView.Detalhe.class, MedicoView.Detalhe.class,
				PacienteView.Rascunho.class,TransportadoraView.Detalhe.class, DoadorView.ContatoFase2.class, DoadorView.LogisticaDoador.class})
	protected String nomeLogradouro;
	
	/**
	 * Municipio da localidade.
	 */
	
	
	@ManyToOne
	@JoinColumn(name = "MUNI_ID")
	@JsonView({ LaboratorioView.Detalhe.class, PacienteView.Detalhe.class, BuscaView.Enriquecimento.class, DoadorView.AtualizacaoFase2.class,
				DoadorView.ContatoPassivo.class, TransportadoraView.AgendarTransporte.class, LaboratorioView.ListarCT.class, CentroTransplanteView.Detalhe.class, 
				LaboratorioView.Listar.class, MedicoView.Detalhe.class, PacienteView.Rascunho.class,TransportadoraView.Detalhe.class, 
				DoadorView.ContatoFase2.class, DoadorView.LogisticaDoador.class})
	protected Municipio municipio;

	/**
	 * Tipo do logradouro.
	 */
	@Column(name = "ENCO_TX_TIPO_LOGRADOURO")
	@Length(max = 100)
	@JsonView({ LaboratorioView.Detalhe.class, PacienteView.Detalhe.class, BuscaView.Enriquecimento.class, DoadorView.AtualizacaoFase2.class, DoadorView.ContatoPassivo.class, 
			TransportadoraView.AgendarTransporte.class, CentroTransplanteView.Detalhe.class, MedicoView.Detalhe.class,
			PacienteView.Rascunho.class,TransportadoraView.Detalhe.class, DoadorView.ContatoFase2.class,
			DoadorView.LogisticaDoador.class})
	protected String tipoLogradouro;

	@Column(name = "ENCO_IN_EXCLUIDO")
	@JsonView({ LaboratorioView.Detalhe.class, PacienteView.Detalhe.class, BuscaView.Enriquecimento.class, DoadorView.AtualizacaoFase2.class, DoadorView.ContatoPassivo.class, 
		TransportadoraView.AgendarTransporte.class, CentroTransplanteView.Detalhe.class, MedicoView.Detalhe.class,
		PacienteView.Rascunho.class, DoadorView.ContatoFase2.class}) 
	protected boolean excluido;

	@Column(name = "ENCO_IN_PRINCIPAL")
	@JsonView({ LaboratorioView.Detalhe.class, PacienteView.Detalhe.class, BuscaView.Enriquecimento.class, DoadorView.AtualizacaoFase2.class, DoadorView.ContatoPassivo.class, 
			TransportadoraView.AgendarTransporte.class, CentroTransplanteView.Detalhe.class, MedicoView.Detalhe.class,
			PacienteView.Rascunho.class, DoadorView.ContatoFase2.class, DoadorView.LogisticaDoador.class})
	protected boolean principal;

	@Column(name = "ENCO_IN_CORRESPONDENCIA")
	@JsonView({ LaboratorioView.Detalhe.class, PacienteView.Detalhe.class, BuscaView.Enriquecimento.class, DoadorView.AtualizacaoFase2.class, DoadorView.ContatoPassivo.class, 
			TransportadoraView.AgendarTransporte.class, CentroTransplanteView.Detalhe.class, MedicoView.Detalhe.class, PacienteView.Rascunho.class, DoadorView.ContatoFase2.class  })
	protected boolean correspondencia;

	public EnderecoContato() {}

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
	 * @return the cep
	 */
	public String getCep() {
		return cep;
	}

	/**
	 * @param cep the cep to set
	 */
	public void setCep(String cep) {
		this.cep = cep;
	}

	/**
	 * @return the pais
	 */
	public Pais getPais() {
		return pais;
	}

	/**
	 * @param pais the pais to set
	 */
	public void setPais(Pais pais) {
		this.pais = pais;
	}

	/**
	 * @return the numero
	 */
	public String getNumero() {
		return numero;
	}

	/**
	 * @param numero the numero to set
	 */
	public void setNumero(String numero) {
		this.numero = numero;
	}

	/**
	 * @return the bairro
	 */
	public String getBairro() {
		return bairro;
	}

	/**
	 * @param bairro the bairro to set
	 */
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	/**
	 * @return the complemento
	 */
	public String getComplemento() {
		return complemento;
	}

	/**
	 * @param complemento the complemento to set
	 */
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	/**
	 * @return the enderecoEstrangeiro
	 */
	public String getEnderecoEstrangeiro() {
		return enderecoEstrangeiro;
	}

	/**
	 * @param enderecoEstrangeiro the enderecoEstrangeiro to set
	 */
	public void setEnderecoEstrangeiro(String enderecoEstrangeiro) {
		this.enderecoEstrangeiro = enderecoEstrangeiro;
	}

	/**
	 * @return the municipio
	 */
	public Municipio getMunicipio() {
		return municipio;
	}

	/**
	 * @param municipio the municipio to set
	 */
	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}

	/**
	 * @return the nomeLogradouro
	 */
	public String getNomeLogradouro() {
		return nomeLogradouro;
	}

	/**
	 * @param nomeLogradouro the nomeLogradouro to set
	 */
	public void setNomeLogradouro(String nomeLogradouro) {
		this.nomeLogradouro = nomeLogradouro;
	}

	/**
	 * @return the tipoLogradouro
	 */
	public String getTipoLogradouro() {
		return tipoLogradouro;
	}

	/**
	 * @param tipoLogradouro the tipoLogradouro to set
	 */
	public void setTipoLogradouro(String tipoLogradouro) {
		this.tipoLogradouro = tipoLogradouro;
	}

	/**
	 * @return the excluido
	 */
	public boolean getExcluido() {
		return excluido;
	}

	/**
	 * @param excluido the excluido to set
	 */
	public void setExcluido(boolean excluido) {
		this.excluido = excluido;
	}

	@AssertTrueCustom(message = "{javax.validation.constraints.NotNull.message}", field = "cep")
	@JsonIgnore
	public boolean isPermitidoCepNulo() {
		return permiteNuloPaisBrasil(this.cep);
	}

	@AssertTrueCustom(	message = "{javax.validation.constraints.NotNull.message}",
						field = "tipoLogradouro")
	@JsonIgnore
	public boolean isPermitidoTipoLogradouroNulo() {
		return permiteNuloPaisBrasil(this.tipoLogradouro);
	}

	@AssertTrueCustom(	message = "{javax.validation.constraints.NotNull.message}",
						field = "nomeLogradouro")
	@JsonIgnore
	public boolean isPermitidoNomeLogradouroNulo() {
		return permiteNuloPaisBrasil(this.nomeLogradouro);
	}

	@AssertTrueCustom(message = "{javax.validation.constraints.NotNull.message}", field = "bairro")
	@JsonIgnore
	public boolean isPermitidoBairroNulo() {
		return permiteNuloPaisBrasil(this.bairro);
	}

	@AssertTrueCustom(	message = "{javax.validation.constraints.NotNull.message}",
						field = "municipio")
	@JsonIgnore
	public boolean isPermitidoMunicipioNulo() {
		return permiteNuloPaisBrasil(this.municipio);
	}

	/**
	 * método.
	 * 
	 * @return true false
	 */
	@AssertTrueCustom(	message = "{javax.validation.constraints.NotNull.message}",
						field = "enderecoEstrangeiro")
	@JsonIgnore
	public boolean isPermitidoEnderecoEstrangeiroNulo() {
		if (( pais == null || !pais.isBrasil() ) && ( StringUtils.isEmpty(
				this.enderecoEstrangeiro) )) {
			return false;
		}
		return true;
	}

	@AssertTrueCustom(message = "{javax.validation.constraints.NotNull.message}", field = "numero")
	@JsonIgnore
	public boolean isPermitidoNumeroNulo() {
		return permiteNuloPaisBrasil(this.numero);
	}

	private boolean permiteNuloPaisBrasil(Object propriedade) {
		if (this.pais == null || !this.pais.isBrasil()) {
			return true;
		}
		else
			if (propriedade == null || ( propriedade != null && propriedade instanceof String
					&& StringUtils.isEmpty((String) propriedade) )) {
				return false;
			}
		return true;
	}

	/**
	 * @return the principal
	 */
	public boolean isPrincipal() {
		return principal;
	}

	/**
	 * @param principal the principal to set
	 */
	public void setPrincipal(boolean principal) {
		this.principal = principal;
	}

	/**
	 * @return the correspondencia
	 */
	public boolean isCorrespondencia() {
		return correspondencia;
	}

	/**
	 * @param correspondencia the correspondencia to set
	 */
	public void setCorrespondencia(boolean correspondencia) {
		this.correspondencia = correspondencia;
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
		result = prime * result + ( ( bairro == null ) ? 0 : bairro.hashCode() );
		result = prime * result + ( ( cep == null ) ? 0 : cep.hashCode() );
		result = prime * result + ( ( complemento == null ) ? 0 : complemento.hashCode() );
		result = prime * result + ( correspondencia ? 1231 : 1237 );
		result = prime * result + ( ( enderecoEstrangeiro == null ) ? 0 : enderecoEstrangeiro.hashCode() );
		result = prime * result + ( excluido ? 1231 : 1237 );
		result = prime * result + ( ( id == null ) ? 0 : id.hashCode() );
		result = prime * result + ( ( municipio == null ) ? 0 : municipio.hashCode() );
		result = prime * result + ( ( nomeLogradouro == null ) ? 0 : nomeLogradouro.hashCode() );
		result = prime * result + ( ( numero == null ) ? 0 : numero.hashCode() );
		result = prime * result + ( ( pais == null ) ? 0 : pais.hashCode() );
		result = prime * result + ( principal ? 1231 : 1237 );
		result = prime * result + ( ( tipoLogradouro == null ) ? 0 : tipoLogradouro.hashCode() );		
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
		if (!( obj instanceof EnderecoContato )) {
			return false;
		}
		EnderecoContato other = (EnderecoContato) obj;
		if (bairro == null) {
			if (other.bairro != null) {
				return false;
			}
		}
		else
			if (!bairro.equals(other.bairro)) {
				return false;
			}
		if (cep == null) {
			if (other.cep != null) {
				return false;
			}
		}
		else
			if (!cep.equals(other.cep)) {
				return false;
			}
		if (complemento == null) {
			if (other.complemento != null) {
				return false;
			}
		}
		else
			if (!complemento.equals(other.complemento)) {
				return false;
			}
		if (correspondencia != other.correspondencia) {
			return false;
		}
		if (enderecoEstrangeiro == null) {
			if (other.enderecoEstrangeiro != null) {
				return false;
			}
		}
		else
			if (!enderecoEstrangeiro.equals(other.enderecoEstrangeiro)) {
				return false;
			}
		if (excluido != other.excluido) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		}
		else
			if (!id.equals(other.id)) {
				return false;
			}
		if (municipio == null) {
			if (other.municipio != null) {
				return false;
			}
		}
		else
			if (!municipio.equals(other.municipio)) {
				return false;
			}
		if (nomeLogradouro == null) {
			if (other.nomeLogradouro != null) {
				return false;
			}
		}
		else
			if (!nomeLogradouro.equals(other.nomeLogradouro)) {
				return false;
			}
		if (numero == null) {
			if (other.numero != null) {
				return false;
			}
		}
		else
			if (!numero.equals(other.numero)) {
				return false;
			}
		if (pais == null) {
			if (other.pais != null) {
				return false;
			}
		}
		else
			if (!pais.equals(other.pais)) {
				return false;
			}
		if (principal != other.principal) {
			return false;
		}
		if (tipoLogradouro == null) {
			if (other.tipoLogradouro != null) {
				return false;
			}
		}
		else
			if (!tipoLogradouro.equals(other.tipoLogradouro)) {
				return false;
			}
		return true;
	}

	@Override
	public String toString() {
		return tipoLogradouro + " " + nomeLogradouro + ", " + municipio;
	}

}