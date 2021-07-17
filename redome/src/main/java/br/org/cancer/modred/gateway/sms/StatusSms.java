
package br.org.cancer.modred.gateway.sms;

import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.model.interfaces.EnumType;

/**
 * Status de envio e retorno do sms.
 * 
 * @author brunosousa
 *
 */
public enum StatusSms implements EnumType<Integer> {
	
	AGUARDANDO_ENVIO(0),
	ENTREGUE(1),
	LEITURA(2),
	DEVOLVIDO(3);
	
	private Integer id;
	
	StatusSms(Integer id) {
		this.id = id;
	}

	@Override
	public Integer getId() {
		return id;
	}
	
	/**
	 * Retorna o enum de acordo com o código informado.
	 * 
	 * @param id Id a ser pesquisado na lista de enums.
	 * @return o enum relacionado ao código.
	 */
	public static StatusSms get(Integer id) {
		for (StatusSms status : values()) {
			if (status.getId().equals(id)) {
				return status;
			}
		}
		throw new BusinessException("erro.interno");
	}

}
