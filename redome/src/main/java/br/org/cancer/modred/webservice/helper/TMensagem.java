package br.org.cancer.modred.webservice.helper;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * Classe helper que representa a mensagem tanto de erro como de sucesso.
 * 
 * @author brunosousa
 *
 */
@JsonPropertyOrder({"mensagem", "codigo", "linha", "coluna"})
public class TMensagem {

    @JacksonXmlProperty(isAttribute=false, namespace="http://localhost:8081/REDOMEWeb/schemas/retorno")
    protected String mensagem;
    
    @JacksonXmlProperty(isAttribute=true, namespace="http://localhost:8081/REDOMEWeb/schemas/retorno")
    protected long codigo;
    
    @JacksonXmlProperty(isAttribute=true, namespace="http://localhost:8081/REDOMEWeb/schemas/retorno")
    protected long linha;
    
    @JacksonXmlProperty(isAttribute=true, namespace="http://localhost:8081/REDOMEWeb/schemas/retorno")
    protected long coluna;
    
    public TMensagem() {
	}
    
    /**
     * Construtor recebendo a mensagem.
     * 
     * @param mensagem - texto da mensagem.
     */
    public TMensagem(String mensagem) {
    	this.codigo = 2L;
    	this.linha = 0L;
    	this.coluna = 0L;
    	this.mensagem = mensagem;
	}
    
    /**
     * Construtor recebendo todos as propriedades.
     * 
     * @param codigo - Código do erro
     * @param linha - Linha onde ocorreu o erro
     * @param coluna - coluna onde ocorreu o erro
     * @param mensagem - texto da mensagem
     */
    public TMensagem(Long codigo, Long linha, Long coluna, String mensagem) {
    	this.codigo = codigo;
    	this.linha = linha;
    	this.coluna = coluna;
    	this.mensagem = mensagem;    	
	}
    
    
    
    /**
     * Gets the value of the mensagem property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMensagem() {
        return mensagem;
    }

    /**
     * Sets the value of the mensagem property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMensagem(String value) {
        this.mensagem = value;
    }

    /**
     * Gets the value of the codigo property.
     * 
     */
    public long getCodigo() {
        return codigo;
    }

    /**
     * Sets the value of the codigo property.
     * 
     */
    public void setCodigo(long value) {
        this.codigo = value;
    }

    /**
     * Gets the value of the linha property.
     * 
     */
    public long getLinha() {
        return linha;
    }

    /**
     * Sets the value of the linha property.
     * 
     */
    public void setLinha(long value) {
        this.linha = value;
    }

    /**
     * Gets the value of the coluna property.
     * 
     */
    public long getColuna() {
        return coluna;
    }

    /**
     * Sets the value of the coluna property.
     * 
     */
    public void setColuna(long value) {
        this.coluna = value;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TMensagem [mensagem=" + mensagem + ", codigo=" + codigo + ", linha=" + linha + ", coluna=" + coluna
				+ "]";
	}
    
    

}
