package br.org.cancer.modred.controller.dto.doador;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * Classe de persistencia de doador para a tabela doador.
 * 
 * @author ergomes
 * 
 */
@JsonTypeName("doadorNacionalDTO")
public class DoadorNacionalDTO extends DoadorDTO implements Serializable {

	private static final long serialVersionUID = -1876211173339279212L;

	private Long dmr;
	private String cpf;
	private String nome;
	private String nomeSocial;
	private String nomeMae;
	private RacaDTO raca;
	private EtniaDTO etnia;
	private PaisDTO pais;
	private Long naturalidade;
	private String rg;
	private String orgaoExpedidor;
	private Float altura;
	private String nomePai;
	private List<ContatoTelefonicoDoadorDTO> contatosTelefonicos;
	private List<EnderecoContatoDoadorDTO> enderecosContato;
	private List<EmailContatoDoadorDTO> emailsContato;
	private LaboratorioDTO laboratorio;
	private List<ExameDoadorNacionalDTO> exames;
	private HemoEntidadeDTO hemoEntidade;
	private Long idDoador;
	
	private String numeroHemocentro;
	private String fumante;
	private Long estadoCivil;
	private Long campanha;
	private LocalDate dataColeta;
	
	/**
	 * 
	 */
	public DoadorNacionalDTO() {}

	
	/**
	 * @param idDoador
	 */
	public DoadorNacionalDTO(Long idDoador) {
		super();
		this.idDoador = idDoador;
	}


	/**
	 * @return the dmr
	 */
	public Long getDmr() {
		return dmr;
	}

	/**
	 * @param dmr the dmr to set
	 */
	public void setDmr(Long dmr) {
		this.dmr = dmr;
	}

	/**
	 * @return the cpf
	 */
	public String getCpf() {
		return cpf;
	}

	/**
	 * @param cpf the cpf to set
	 */
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return the nomeSocial
	 */
	public String getNomeSocial() {
		return nomeSocial;
	}

	/**
	 * @param nomeSocial the nomeSocial to set
	 */
	public void setNomeSocial(String nomeSocial) {
		this.nomeSocial = nomeSocial;
	}

	/**
	 * @return the nomeMae
	 */
	public String getNomeMae() {
		return nomeMae;
	}

	/**
	 * @param nomeMae the nomeMae to set
	 */
	public void setNomeMae(String nomeMae) {
		this.nomeMae = nomeMae;
	}

	/**
	 * @return the raca
	 */
	public RacaDTO getRaca() {
		return raca;
	}

	/**
	 * @param raca the raca to set
	 */
	public void setRaca(RacaDTO raca) {
		this.raca = raca;
	}

	/**
	 * @return the etnia
	 */
	public EtniaDTO getEtnia() {
		return etnia;
	}

	/**
	 * @param etnia the etnia to set
	 */
	public void setEtnia(EtniaDTO etnia) {
		this.etnia = etnia;
	}

	/**
	 * @return the pais
	 */
	public PaisDTO getPais() {
		return pais;
	}

	/**
	 * @param pais the pais to set
	 */
	public void setPais(PaisDTO pais) {
		this.pais = pais;
	}

	/**
	 * @return the naturalidade
	 */
	public Long getNaturalidade() {
		return naturalidade;
	}

	/**
	 * @param naturalidade the naturalidade to set
	 */
	public void setNaturalidade(Long naturalidade) {
		this.naturalidade = naturalidade;
	}

	/**
	 * @return the rg
	 */
	public String getRg() {
		return rg;
	}

	/**
	 * @param rg the rg to set
	 */
	public void setRg(String rg) {
		this.rg = rg;
	}

	/**
	 * @return the orgaoExpedidor
	 */
	public String getOrgaoExpedidor() {
		return orgaoExpedidor;
	}

	/**
	 * @param orgaoExpedidor the orgaoExpedidor to set
	 */
	public void setOrgaoExpedidor(String orgaoExpedidor) {
		this.orgaoExpedidor = orgaoExpedidor;
	}

	/**
	 * @return the altura
	 */
	public Float getAltura() {
		return altura;
	}

	/**
	 * @param altura the altura to set
	 */
	public void setAltura(Float altura) {
		this.altura = altura;
	}

	/**
	 * @return the nomePai
	 */
	public String getNomePai() {
		return nomePai;
	}

	/**
	 * @param nomePai the nomePai to set
	 */
	public void setNomePai(String nomePai) {
		this.nomePai = nomePai;
	}

	/**
	 * @return the contatosTelefonicos
	 */
	public List<ContatoTelefonicoDoadorDTO> getContatosTelefonicos() {
		return contatosTelefonicos;
	}

	/**
	 * @param contatosTelefonicos the contatosTelefonicos to set
	 */
	public void setContatosTelefonicos(List<ContatoTelefonicoDoadorDTO> contatosTelefonicos) {
		this.contatosTelefonicos = contatosTelefonicos;
	}

	/**
	 * @return the enderecosContato
	 */
	public List<EnderecoContatoDoadorDTO> getEnderecosContato() {
		return enderecosContato;
	}

	/**
	 * @param enderecosContato the enderecosContato to set
	 */
	public void setEnderecosContato(List<EnderecoContatoDoadorDTO> enderecosContato) {
		this.enderecosContato = enderecosContato;
	}

	/**
	 * @return the emailsContato
	 */
	public List<EmailContatoDoadorDTO> getEmailsContato() {
		return emailsContato;
	}

	/**
	 * @param emailsContato the emailsContato to set
	 */
	public void setEmailsContato(List<EmailContatoDoadorDTO> emailsContato) {
		this.emailsContato = emailsContato;
	}

	/**
	 * @return the laboratorio
	 */
	public LaboratorioDTO getLaboratorio() {
		return laboratorio;
	}

	/**
	 * @param laboratorio the laboratorio to set
	 */
	public void setLaboratorio(LaboratorioDTO laboratorio) {
		this.laboratorio = laboratorio;
	}

	/**
	 * @return the exames
	 */
	public List<ExameDoadorNacionalDTO> getExames() {
		return exames;
	}

	/**
	 * @param exames the exames to set
	 */
	public void setExames(List<ExameDoadorNacionalDTO> exames) {
		this.exames = exames;
	}

	/**
	 * @return the hemoEntidade
	 */
	public HemoEntidadeDTO getHemoEntidade() {
		return hemoEntidade;
	}

	/**
	 * @param hemoEntidade the hemoEntidade to set
	 */
	public void setHemoEntidade(HemoEntidadeDTO hemoEntidade) {
		this.hemoEntidade = hemoEntidade;
	}
	
	/**
	 * @return the idDoador
	 */
	public Long getIdDoador() {
		return idDoador;
	}

	/**
	 * @param idDoador the idDoador to set
	 */
	public void setIdDoador(Long idDoador) {
		this.idDoador = idDoador;
	}
	
	/**
	 * @return the numeroHemocentro
	 */
	public String getNumeroHemocentro() {
		return numeroHemocentro;
	}


	/**
	 * @param numeroHemocentro the numeroHemocentro to set
	 */
	public void setNumeroHemocentro(String numeroHemocentro) {
		this.numeroHemocentro = numeroHemocentro;
	}


	/**
	 * @return the fumante
	 */
	public String getFumante() {
		return fumante;
	}


	/**
	 * @param fumante the fumante to set
	 */
	public void setFumante(String fumante) {
		this.fumante = fumante;
	}


	/**
	 * @return the estadoCivil
	 */
	public Long getEstadoCivil() {
		return estadoCivil;
	}


	/**
	 * @param estadoCivil the estadoCivil to set
	 */
	public void setEstadoCivil(Long estadoCivil) {
		this.estadoCivil = estadoCivil;
	}


	/**
	 * @return the campanha
	 */
	public Long getCampanha() {
		return campanha;
	}


	/**
	 * @param campanha the campanha to set
	 */
	public void setCampanha(Long campanha) {
		this.campanha = campanha;
	}


	/**
	 * @return the dataColeta
	 */
	public LocalDate getDataColeta() {
		return dataColeta;
	}


	/**
	 * @param dataColeta the dataColeta to set
	 */
	public void setDataColeta(LocalDate dataColeta) {
		this.dataColeta = dataColeta;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((dmr == null) ? 0 : dmr.hashCode());
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
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		DoadorNacionalDTO other = (DoadorNacionalDTO) obj;
		if (dmr == null) {
			if (other.dmr != null) {
				return false;
			}
		} 
		else if (!dmr.equals(other.dmr)) {
			return false;
		}
		return true;
	}


}