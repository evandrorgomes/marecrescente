package br.org.cancer.modred.webservice.helper;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * Classe helper que representa o body do soap:envelope.
 * 
 * @author brunosousa
 *
 */
@JacksonXmlRootElement(namespace = "http://www.w3.org/2003/05/soap-envelope")
@JsonPropertyOrder({"faultcode", "faultstring"})
public class TFault {
	
	@JacksonXmlProperty(isAttribute=false)
	private String faultcode;
	
	@JacksonXmlProperty(isAttribute=false)
	private String faultstring;

	/**
	 * @return the faultcode
	 */
	public String getFaultcode() {
		return faultcode;
	}

	/**
	 * @param faultcode the faultcode to set
	 */
	public void setFaultcode(String faultcode) {
		this.faultcode = faultcode;
	}

	/**
	 * @return the faultstring
	 */
	public String getFaultstring() {
		return faultstring;
	}

	/**
	 * @param faultstring the faultstring to set
	 */
	public void setFaultstring(String faultstring) {
		this.faultstring = faultstring;
	}
	

}
