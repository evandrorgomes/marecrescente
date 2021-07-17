package br.org.cancer.modred.webservice.helper;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * Classe helper para obter o retorno do webservice do redomeweb.
 * 
 * @author brunosousa
 *
 */
@JacksonXmlRootElement(namespace = "http://localhost:8081/REDOMEWeb/schemas/retorno", localName = "retorno")
@JsonPropertyOrder({"sucessos", "erros"})
public class TRetorno {

	@JacksonXmlElementWrapper(useWrapping = false, namespace="http://localhost:8081/REDOMEWeb/schemas/retorno")
	protected List<TSucesso> sucessos;
	
	@JacksonXmlElementWrapper(useWrapping = false, namespace="http://localhost:8081/REDOMEWeb/schemas/retorno")
    protected List<TErro> erros;
     
    /**
     * Lista de mensagens de sucesso.
     * 
     * @return Lista de mensagens de sucessos.
     */
    public List<TSucesso> getSucessos() {
        if (sucessos == null) {
            sucessos = new ArrayList<TSucesso>();
        }
        return this.sucessos;
    }    
    
    /**
     * Lista de mensagens de erro.
     * 
     * @return lista de mensagens de erro.
     */
    public List<TErro> getErros() {
        if (erros == null) {
            erros = new ArrayList<TErro>();
        }
        return this.erros;
    }
    
    
}
