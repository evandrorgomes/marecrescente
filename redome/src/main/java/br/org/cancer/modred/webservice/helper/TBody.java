package br.org.cancer.modred.webservice.helper;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import br.org.cancer.modred.exception.BusinessException;

/**
 * Classe helper que representa o body do soap:envelope.
 * 
 * @author brunosousa
 *
 */
@JsonPropertyOrder({"solicitarExameResponse"
	, "cancelarExameResponse"
	, "solicitarAmostraResponse"
	, "cancelarAmostraResponse"
	, "alteraDoadorRedomeResponse"
	, "inativarDoadorResponse"
	, "alterarStatusDeDoadorResponse" })
public class TBody {
	
	@JacksonXmlProperty(isAttribute=false, namespace="http://localhost:8080/REDOMEWeb")
	private TSolicitarExameResponse solicitarExameResponse;
	
	@JacksonXmlProperty(isAttribute=false, namespace="http://localhost:8080/REDOMEWeb")
	private TCancelarExameResponse cancelarExameResponse;
	
	@JacksonXmlProperty(isAttribute=false, namespace="http://localhost:8080/REDOMEWeb")
	private TSolicitarAmostraResponse solicitarAmostraResponse;
	
	@JacksonXmlProperty(isAttribute=false, namespace="http://localhost:8080/REDOMEWeb")
	private TCancelarAmostraResponse cancelarAmostraResponse;
	
	@JacksonXmlProperty(isAttribute=false, namespace="http://localhost:8080/REDOMEWeb")
	private TAlteraDoadorRedomeResponse alteraDoadorRedomeResponse;
	
	@JacksonXmlProperty(isAttribute=false, namespace="http://localhost:8080/REDOMEWeb")
	private TInativarDoadorResponse inativarDoadorResponse;
	
	
	@JacksonXmlProperty(isAttribute=false, namespace="http://localhost:8080/REDOMEWeb")
	private TAlterarStatusDeDoadorResponse alterarStatusDeDoadorResponse;
	
	@JacksonXmlProperty(isAttribute=false, namespace="http://localhost:8080/REDOMEWeb", localName="Fault")
	private TFault fault;
	
	/**
	 * Retorno genérico de {@link TAbstractResponse}.
	 * @param responseType tipo a ser retornado
	 * @return response
	 */
	public TAbstractResponse getResponse(TAbstractResponse responseType) {
		if(responseType instanceof TSolicitarExameResponse) {
			return this.solicitarExameResponse;
		}
		else if(responseType instanceof TCancelarExameResponse) {
			return this.cancelarExameResponse;
		}
		else if(responseType instanceof TSolicitarAmostraResponse) {
			return this.solicitarAmostraResponse;
		}
		else if(responseType instanceof TCancelarAmostraResponse) {
			return this.cancelarAmostraResponse;
		}
		else if(responseType instanceof TAlteraDoadorRedomeResponse) {
			return this.alteraDoadorRedomeResponse;
		}
		else if(responseType instanceof TInativarDoadorResponse) {
			return this.inativarDoadorResponse;
		}
		else if(responseType instanceof TAlterarStatusDeDoadorResponse) {
			return this.alterarStatusDeDoadorResponse;
		}
		else {
			throw new BusinessException("Response type inválido");
		}
	}
	

	/**
	 * @return the solicitarExameResponse
	 */
	public TSolicitarExameResponse getSolicitarExameResponse() {
		return solicitarExameResponse;
	}

	/**
	 * @param solicitarExameResponse the solicitarExameResponse to set
	 */
	public void setSolicitarExameResponse(TSolicitarExameResponse solicitarExameResponse) {
		this.solicitarExameResponse = solicitarExameResponse;
	}

	/**
	 * @return the cancelarExameResponse
	 */
	public TCancelarExameResponse getCancelarExameResponse() {
		return cancelarExameResponse;
	}

	/**
	 * @param cancelarExameResponse the cancelarExameResponse to set
	 */
	public void setCancelarExameResponse(TCancelarExameResponse cancelarExameResponse) {
		this.cancelarExameResponse = cancelarExameResponse;
	}

	/**
	 * @return the solicitarAmostraResponse
	 */
	public TSolicitarAmostraResponse getSolicitarAmostraResponse() {
		return solicitarAmostraResponse;
	}

	/**
	 * @param solicitarAmostraResponse the solicitarAmostraResponse to set
	 */
	public void setSolicitarAmostraResponse(TSolicitarAmostraResponse solicitarAmostraResponse) {
		this.solicitarAmostraResponse = solicitarAmostraResponse;
	}

	/**
	 * @return the cancelarAmostraResponse
	 */
	public TCancelarAmostraResponse getCancelarAmostraResponse() {
		return cancelarAmostraResponse;
	}

	/**
	 * @param cancelarAmostraResponse the cancelarAmostraResponse to set
	 */
	public void setCancelarAmostraResponse(TCancelarAmostraResponse cancelarAmostraResponse) {
		this.cancelarAmostraResponse = cancelarAmostraResponse;
	}

	/**
	 * @return the alteraDoadorRedomeResponse
	 */
	public TAlteraDoadorRedomeResponse getAlteraDoadorRedomeResponse() {
		return alteraDoadorRedomeResponse;
	}

	/**
	 * @param alteraDoadorRedomeResponse the alteraDoadorRedomeResponse to set
	 */
	public void setAlteraDoadorRedomeResponse(TAlteraDoadorRedomeResponse alteraDoadorRedomeResponse) {
		this.alteraDoadorRedomeResponse = alteraDoadorRedomeResponse;
	}

	/**
	 * @return the inativarDoadorResponse
	 */
	public TInativarDoadorResponse getInativarDoadorResponse() {
		return inativarDoadorResponse;
	}

	/**
	 * @param inativarDoadorResponse the inativarDoadorResponse to set
	 */
	public void setInativarDoadorResponse(TInativarDoadorResponse inativarDoadorResponse) {
		this.inativarDoadorResponse = inativarDoadorResponse;
	}

	/**
	 * @return the alterarStatusDeDoadorResponse
	 */
	public TAlterarStatusDeDoadorResponse getAlterarStatusDeDoadorResponse() {
		return alterarStatusDeDoadorResponse;
	}


	/**
	 * @param alterarStatusDeDoadorResponse the alterarStatusDeDoadorResponse to set
	 */
	public void setAlterarStatusDeDoadorResponse(TAlterarStatusDeDoadorResponse alterarStatusDeDoadorResponse) {
		this.alterarStatusDeDoadorResponse = alterarStatusDeDoadorResponse;
	}


	/**
	 * @return the fault
	 */
	public TFault getFault() {
		return fault;
	}

	/**
	 * @param fault the fault to set
	 */
	public void setFault(TFault fault) {
		this.fault = fault;
	}
	

}
