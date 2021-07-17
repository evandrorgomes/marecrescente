package br.org.cancer.modred.vo;

import java.io.Serializable;

/**
 * @author bruno.sousa
 *
 */
public class CepCorreioVO implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String cep;
    
    private String uf;
    
    private String localidade;
    
    private String bairro;
    
    private String tipoLogradouro;
    
    private String logradouro;
    
    private String codigoIbge;
    
    public CepCorreioVO() {
        super();
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
     * @return the uf
     */
    public String getUf() {
        return uf;
    }

    
    /**
     * @param uf the uf to set
     */
    public void setUf(String uf) {
        this.uf = uf;
    }

    /**
     * @return the localidade
     */
    public String getLocalidade() {
        return localidade;
    }

    
    /**
     * @param localidade the localidade to set
     */
    public void setLocalidade(String localidade) {
        this.localidade = localidade;
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
     * @return the logradouro
     */
    public String getLogradouro() {
        return logradouro;
    }

    
    /**
     * @param logradouro the logradouro to set
     */
    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

	/**
	 * @return the codigoIbge
	 */
	public String getCodigoIbge() {
		return codigoIbge;
	}

	/**
	 * @param codigoIbge the codigoIbge to set
	 */
	public void setCodigoIbge(String codigoIbge) {
		this.codigoIbge = codigoIbge;
	}
    
    
    
}
