package br.org.cancer.modred.webservice.helper;

import org.codehaus.jackson.annotate.JsonPropertyOrder;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
/**
 * Classe que representa o response do webservice.
 * 
 * @author elizabetepoly
 *
 */
@JacksonXmlRootElement(namespace = "http://localhost:8080/REDOMEWeb")
@JsonPropertyOrder({"dados"})
public class TAlteraDoadorRedomeResponse extends TAbstractResponse { 
	/**
	 * Classe que representa o response do webservice.
	 * 
	 * 	@author elizabetepoly
	 */
	


}
