package br.org.cancer.modred.webservice.helper;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * Classe helper que representa o envelope do soap.
 * 
 * @author brunosousa
 *
 */
@JacksonXmlRootElement(namespace = "http://localhost:8081/REDOMEWeb/schemas/retorno", localName = "retorno")
@JsonPropertyOrder({"Body"})
public class TEnvelope {
	
	@JacksonXmlProperty(isAttribute=false, namespace="http://localhost:8081/REDOMEWeb/schemas/retorno", localName="Body")
	private TBody body;
	
	/**
	 * @return the body
	 */
	public TBody getBody() {
		return body;
	}

	/**
	 * @param body the body to set
	 */
	public void setBody(TBody body) {
		this.body = body;
	}
	
	

}
