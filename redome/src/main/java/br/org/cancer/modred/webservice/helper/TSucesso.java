package br.org.cancer.modred.webservice.helper;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * Classe helper que representa uma mensagem de sucesso retornado pelo webservice.
 * 
 * @author brunosousa
 *
 */
@JsonPropertyOrder({"id", "mensagem"})
public class TSucesso {
	
	@JacksonXmlProperty(isAttribute=false, namespace="http://localhost:8081/REDOMEWeb/schemas/retorno")
	protected Long id;

	@JacksonXmlProperty(isAttribute=false, namespace="http://localhost:8081/REDOMEWeb/schemas/retorno")
    protected TMensagem mensagem;
	
	public TSucesso() {
	}
	
	public TSucesso(String id) {
		this.id = Long.valueOf(id);
	}
	
	public TSucesso(String id, TMensagem mensagem) {
		this.id = Long.valueOf(id);
		this.mensagem = mensagem;
	}
	
	public TSucesso(Long id) {
		this.id = id;
	}
	
	public TSucesso(Long id, TMensagem mensagem) {
		this.id = id;
		this.mensagem = mensagem;
	}
    
    
    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setId(Long value) {
        this.id = value;
    }
    
    

    /**
     * Gets the value of the mensagem property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public TMensagem getMensagem() {
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
    public void setMensagem(TMensagem value) {
        this.mensagem = value;
    }


}
