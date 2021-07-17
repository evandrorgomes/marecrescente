package br.org.cancer.modred.webservice.helper;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * Classe que representa o response do webservice.
 * 
 * @author brunosousa
 *
 */
@JacksonXmlRootElement(namespace = "http://localhost:8080/REDOMEWeb")
@JsonPropertyOrder({"dados"})
public class TSolicitarAmostraResponse extends TAbstractResponse {

	
}
