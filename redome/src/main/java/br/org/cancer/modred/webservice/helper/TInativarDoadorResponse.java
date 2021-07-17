package br.org.cancer.modred.webservice.helper;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * Tipo de retorno de inativação de doador do REDOMEWEB.
 * @author Filipe Paes
 *
 */
@JacksonXmlRootElement(namespace = "http://localhost:8080/REDOMEWeb")
@JsonPropertyOrder({"dados"})
public class TInativarDoadorResponse  extends TAbstractResponse{

}
