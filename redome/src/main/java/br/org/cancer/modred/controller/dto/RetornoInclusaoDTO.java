package br.org.cancer.modred.controller.dto;

/**
 * Classe de DTO retorno com id do objeto inserido e mensagem de retorno.
 * 
 * @author Fillipe Queiroz
 */
public class RetornoInclusaoDTO {

	private Long idObjeto;
	private String mensagem;
	private String stringRetorno;
	private Long status;

	/**
	 * Construtor default.
	 */
	public RetornoInclusaoDTO() {}

	/**
	 * Construtor recebendo o id do objeto inserido e a mensagem.
	 * 
	 * @param idObjeto - identificador do objeto
	 * @param mensagem - mensagem de retorno
	 */
	public RetornoInclusaoDTO(Long idObjeto, String mensagem) {
		super();
		this.idObjeto = idObjeto;
		this.mensagem = mensagem;
		this.status = StatusRetornoInclusaoDTO.SUCESSO.getId();
	}
	/**
	 * Construtor recebendo o id do objeto, a mensagem e a string de retorno.
	 * 
	 * @param idObjeto - id do objeto salvo.
	 * @param mensagem - mensagem de retorno.
	 * @param stringRetorno - objeto de retorno, caso haja.
	 */

	public RetornoInclusaoDTO(Long idObjeto, String mensagem, String stringRetorno) {
		super();
		this.idObjeto = idObjeto;
		this.mensagem = mensagem;
		this.stringRetorno = stringRetorno;
		this.status = StatusRetornoInclusaoDTO.SUCESSO.getId();
	}
	
	/**
	 * Construtor recebendo todas as propriedades.
	 * 
	 * @param idObjeto - id do objeto salvo.
	 * @param mensagem - mensagem de retorno.
	 * @param stringRetorno - objeto de retorno, caso haja.
	 * @param status - Status de retorno.
	 */

	public RetornoInclusaoDTO(Long idObjeto, String mensagem, String stringRetorno, StatusRetornoInclusaoDTO status) {
		super();
		this.idObjeto = idObjeto;
		this.mensagem = mensagem;
		this.stringRetorno = stringRetorno;
		this.status = status.getId();
	}

	/**
	 * Identificador do objeto inserido.
	 * 
	 * @return
	 */
	public Long getIdObjeto() {
		return idObjeto;
	}

	/**
	 * Identificador do objeto inserido.
	 * 
	 * @param idObjeto
	 */
	public void setIdObjeto(Long idObjeto) {
		this.idObjeto = idObjeto;
	}

	/**
	 * Mensagem de retorno de inclusao.
	 * 
	 * @return
	 */
	public String getMensagem() {
		return mensagem;
	}

	/**
	 * Mensagem de retorno de inclusao.
	 * 
	 * @param mensagem
	 */
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	/**
	 * @return the stringRetorno
	 */
	public String getStringRetorno() {
		return stringRetorno;
	}

	/**
	 * @param stringRetorno the stringRetorno to set
	 */
	public void setStringRetorno(String stringRetorno) {
		this.stringRetorno = stringRetorno;
	}

	/**
	 * @return the status
	 */
	public StatusRetornoInclusaoDTO getStatus() {
		if (status != null) {
			return StatusRetornoInclusaoDTO.valueOf(status);
		}
		return null;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(StatusRetornoInclusaoDTO status) {
		if (status != null) {
			this.status = status.getId();
		}
		else {
			this.status = null;
		}
	}
	

	
}
