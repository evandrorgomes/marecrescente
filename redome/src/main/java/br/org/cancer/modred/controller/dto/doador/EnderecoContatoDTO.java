package br.org.cancer.modred.controller.dto.doador;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Classe de endereço contato.
 * 
 * @author ergomes
 * 
 */
@JsonTypeInfo(
		  use = JsonTypeInfo.Id.NAME,
		  defaultImpl = Void.class
)
@JsonSubTypes({ 
@Type(value = EnderecoContatoDTO.class, name = "enderecoContatoDTO"),
@Type(value = EnderecoContatoDoadorDTO.class, name = "enderecoContatoDoadorDTO"),
@Type(value = EnderecoContatoHemoEntidadeDTO.class, name = "enderecoContatoHemoEntidadeDTO"),
@Type(value = EnderecoContatoLaboratorioDTO.class, name = "enderecoContatoLaboratorioDTO"),
})
public class EnderecoContatoDTO implements Serializable {

	private static final long serialVersionUID = -5636020089147242301L;
	
	protected Long id;
	protected String cep;
	protected PaisDTO pais;
	protected String numero;
	protected String bairro;
	protected String complemento;
	protected String enderecoEstrangeiro;
	protected String municipio;
	protected String nomeLogradouro;
	protected String uf;
	protected String tipoLogradouro;
	protected boolean excluido;
	protected boolean principal;
	protected boolean correspondencia;

	public EnderecoContatoDTO() {}
	
	/**
	 * Construtor da classe EnderecoContatoDTO.
	 * 
	 * @param enderecoContato endereço do contato.
	 */
	public EnderecoContatoDTO(EnderecoContatoDTO enderecoContato) { 
		this.id = enderecoContato.getId();
		this.cep = enderecoContato.getCep();
		this.pais = enderecoContato.getPais();
		this.numero = enderecoContato.getNumero();
		this.bairro = enderecoContato.getBairro();
		this.complemento = enderecoContato.getComplemento();
		this.enderecoEstrangeiro = enderecoContato.getEnderecoEstrangeiro();
		this.municipio = enderecoContato.getMunicipio();
		this.nomeLogradouro = enderecoContato.getNomeLogradouro();
		this.uf = enderecoContato.getUf();
		this.tipoLogradouro = enderecoContato.getTipoLogradouro();
		this.excluido = enderecoContato.isExcluido();
		this.principal = enderecoContato.isPrincipal();
		this.correspondencia = enderecoContato.isCorrespondencia();
	}
	

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
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
	 * @param cep
	 *            the cep to set
	 */
	public void setCep(String cep) {
		this.cep = cep;
	}

	/**
	 * @return the pais
	 */
	public PaisDTO getPais() {
		return pais;
	}

	/**
	 * @param pais
	 *            the pais to set
	 */
	public void setPais(PaisDTO pais) {
		this.pais = pais;
	}

	/**
	 * @return the numero
	 */
	public String getNumero() {
		return numero;
	}

	/**
	 * @param numero
	 *            the numero to set
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
	 * @param bairro
	 *            the bairro to set
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
	 * @param complemento
	 *            the complemento to set
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
	 * @param enderecoEstrangeiro
	 *            the enderecoEstrangeiro to set
	 */
	public void setEnderecoEstrangeiro(String enderecoEstrangeiro) {
		this.enderecoEstrangeiro = enderecoEstrangeiro;
	}

	/**
	 * @return the municipio
	 */
	public String getMunicipio() {
		return municipio;
	}

	/**
	 * @param municipio
	 *            the municipio to set
	 */
	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	/**
	 * @return the nomeLogradouro
	 */
	public String getNomeLogradouro() {
		return nomeLogradouro;
	}

	/**
	 * @param nomeLogradouro
	 *            the nomeLogradouro to set
	 */
	public void setNomeLogradouro(String nomeLogradouro) {
		this.nomeLogradouro = nomeLogradouro;
	}

	/**
	 * @return the uf
	 */
	public String getUf() {
		return uf;
	}

	/**
	 * @param uf
	 *            the uf to set
	 */
	public void setUf(String uf) {
		this.uf = uf;
	}

	/**
	 * @return the tipoLogradouro
	 */
	public String getTipoLogradouro() {
		return tipoLogradouro;
	}

	/**
	 * @param tipoLogradouro
	 *            the tipoLogradouro to set
	 */
	public void setTipoLogradouro(String tipoLogradouro) {
		this.tipoLogradouro = tipoLogradouro;
	}

	/**
	 * @return the excluido
	 */
	public boolean isExcluido() {
		return excluido;
	}

	/**
	 * @param excluido
	 *            the excluido to set
	 */
	public void setExcluido(boolean excluido) {
		this.excluido = excluido;
	}

	public boolean isPermitidoCepNulo() {
		return permiteNuloPaisBrasil(this.cep);
	}

	public boolean isPermitidoUfNula() {
		return permiteNuloPaisBrasil(this.uf);
	}

	public boolean isPermitidoTipoLogradouroNulo() {
		return permiteNuloPaisBrasil(this.tipoLogradouro);
	}

	public boolean isPermitidoNomeLogradouroNulo() {
		return permiteNuloPaisBrasil(this.nomeLogradouro);
	}

	public boolean isPermitidoBairroNulo() {
		return permiteNuloPaisBrasil(this.bairro);
	}

	public boolean isPermitidoMunicipioNulo() {
		return permiteNuloPaisBrasil(this.municipio);
	}

	/**
	 * método.
	 * 
	 * @return true false
	 */
	public boolean isPermitidoEnderecoEstrangeiroNulo() {
		if ((pais == null || !pais.isBrasil()) && (StringUtils.isEmpty(this.enderecoEstrangeiro))) {
			return false;
		}
		return true;
	}

	public boolean isPermitidoNumeroNulo() {
		return permiteNuloPaisBrasil(this.numero);
	}

	private boolean permiteNuloPaisBrasil(Object propriedade) {
		if (this.pais == null || !this.pais.isBrasil()) {
			return true;
		} 
		else if (propriedade == null || (propriedade != null && propriedade instanceof String
				&& StringUtils.isEmpty((String) propriedade))) {
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
	 * @param principal
	 *            the principal to set
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
	 * @param correspondencia
	 *            the correspondencia to set
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
		result = prime * result + ((bairro == null) ? 0 : bairro.hashCode());
		result = prime * result + ((cep == null) ? 0 : cep.hashCode());
		result = prime * result + ((complemento == null) ? 0 : complemento.hashCode());
		result = prime * result + (correspondencia ? 1231 : 1237);
		result = prime * result + ((enderecoEstrangeiro == null) ? 0 : enderecoEstrangeiro.hashCode());
		result = prime * result + (excluido ? 1231 : 1237);
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((municipio == null) ? 0 : municipio.hashCode());
		result = prime * result + ((nomeLogradouro == null) ? 0 : nomeLogradouro.hashCode());
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
		result = prime * result + ((pais == null) ? 0 : pais.hashCode());
		result = prime * result + (principal ? 1231 : 1237);
		result = prime * result + ((tipoLogradouro == null) ? 0 : tipoLogradouro.hashCode());
		result = prime * result + ((uf == null) ? 0 : uf.hashCode());
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
		if (!(obj instanceof EnderecoContatoDTO)) {
			return false;
		}
		EnderecoContatoDTO other = (EnderecoContatoDTO) obj;
		if (bairro == null) {
			if (other.bairro != null) {
				return false;
			}
		} 
		else if (!bairro.equals(other.bairro)) {
			return false;
		}
		if (cep == null) {
			if (other.cep != null) {
				return false;
			}
		} 
		else if (!cep.equals(other.cep)) {
			return false;
		}
		if (complemento == null) {
			if (other.complemento != null) {
				return false;
			}
		} 
		else if (!complemento.equals(other.complemento)) {
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
		else if (!enderecoEstrangeiro.equals(other.enderecoEstrangeiro)) {
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
		else if (!id.equals(other.id)) {
			return false;
		}
		if (municipio == null) {
			if (other.municipio != null) {
				return false;
			}
		} 
		else if (!municipio.equals(other.municipio)) {
			return false;
		}
		if (nomeLogradouro == null) {
			if (other.nomeLogradouro != null) {
				return false;
			}
		} 
		else if (!nomeLogradouro.equals(other.nomeLogradouro)) {
			return false;
		}
		if (numero == null) {
			if (other.numero != null) {
				return false;
			}
		} 
		else if (!numero.equals(other.numero)) {
			return false;
		}
		if (pais == null) {
			if (other.pais != null) {
				return false;
			}
		} 
		else if (!pais.equals(other.pais)) {
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
		else if (!tipoLogradouro.equals(other.tipoLogradouro)) {
			return false;
		}
		if (uf == null) {
			if (other.uf != null) {
				return false;
			}
		} 
		else if (!uf.equals(other.uf)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return tipoLogradouro + " " + nomeLogradouro + ", " + municipio + ", " + uf;
	}

}