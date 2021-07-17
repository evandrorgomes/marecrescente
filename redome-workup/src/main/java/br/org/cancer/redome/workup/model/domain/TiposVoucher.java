package br.org.cancer.redome.workup.model.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum que indica o tipo do voucher.
 * 1 - Hospedagem
 * 2 - Transporte Aéreo
 * 
 * @author Pizão.
 *
 */
@AllArgsConstructor
@Getter
public enum TiposVoucher {
	
    HOSPEDAGEM(1L),
    TRANSPORTE_AEREO(2L);

    private Long codigo;


}
