package br.org.cancer.modred.webservice.helper;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * Classe helper que representa um erro retornado pelo webservice.
 * 
 * @author brunosousa
 *
 */
@JsonPropertyOrder({"id", "mensagens"})
public class TErro {

	@JacksonXmlProperty(isAttribute=false)
	protected Long id;	

	@JacksonXmlElementWrapper(useWrapping = false, namespace="http://localhost:8081/REDOMEWeb/schemas/retorno")
	protected List<TMensagem> mensagens;

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
     * Gets the value of the erros property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the erros property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getErros().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TErro }
     * 
     * @return Lista de mensagens
     */
    public List<TMensagem> getMensagens() {
        if (mensagens == null) {
            mensagens = new ArrayList<TMensagem>();
        }
        return this.mensagens;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String[] retorno = {"TErro [id=" + id + ", mensagens="}; 
		mensagens.forEach(mensagem -> {
			retorno[0] += mensagem.toString();
		});		
		retorno[0] += "]";
		return retorno[0];
	}    
    

}
