package br.org.cancer.redome.courier.controller.dto;

import java.io.Serializable;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Dto para confirmar uma logistica de transporte realizado por um usuário de uma transportadora.
 * 
 * @author Fillipe Queiroz
 *
 */
@Builder
@Getter
@NoArgsConstructor(access =AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ConfirmacaoTransporteDTO implements Serializable {

	private static final long serialVersionUID = 4449354356416831382L;
	
	private Long idCourier;
	private String dadosVoo;
	private boolean voo;


}
