package br.org.cancer.modred.model.domain;

import br.org.cancer.modred.exception.BusinessException;

/**
 * Enum para os códigos dos relatorios.
 * 
 * @author Fillipe Queiroz
 *
 */
public enum Relatorios {

	PEDIDO_EXAME_DQB1_DRB1("PEDIDO_EXAME_DQB1_DRB1"),
	PEDIDO_EXAME_C("PEDIDO_EXAME_C"),
	PEDIDO_EXAME_ALTA_RESOLUCAO("PEDIDO_EXAME_ALTA_RESOLUCAO"),
	PEDIDO_EXAME_CT("PEDIDO_EXAME_CT"),
	CARTA_MO("CARTA_MO"),
	CARTA_TRANSPORTE("CARTA_TRANSPORTE"),
	EMAIL_ENVIO_SENHA("EMAIL_ENVIO_SENHA"),
	LEMBRAR_SENHA("LEMBRAR_SENHA"),
	INSTRUCAO_COLETA_SANGUE_SWAB_ORAL_CT("INSTRUCAO_COLETA_SANGUE_SWAB_ORAL_CT"),
	INSTRUCAO_COLETA_SOMENTE_SWAB_ORAL_CT("INSTRUCAO_COLETA_SOMENTE_SWAB_ORAL_CT"),
	INSTRUCAO_COLETA_SANGUE_CT("INSTRUCAO_COLETA_SANGUE_CT"),
	EMAIL_DIVERGENCIA_LABORATORIO("EMAIL_DIVERGENCIA_LABORATORIO");

	String codigo;

	Relatorios(String codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * Retorna o enum de acordo com o código informado.
	 * 
	 * @param codigo código a ser pesquisado na lista de enums.
	 * @return o enum relacionado ao código.
	 */
	public static Relatorios get(String codigo) {
		for (Relatorios status : values()) {
			if (status.equals(codigo)) {
				return status;
			}
		}
		throw new BusinessException("erro.interno");
	}

}
