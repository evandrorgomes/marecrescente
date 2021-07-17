package br.org.cancer.redome.courier.feign.client.dto;

import java.io.Serializable;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
public class LogisticaMaterialTransporteDTO implements Serializable {
	
	private static final long serialVersionUID = 7866719172254943987L;
		
	private Long rmr;
	private String identificacaoDoador;
	private String nomeLocalRetirada;
	private EnderecoDTO enderecoRetirada;
	private String enderecoRetiradaBancoCordao;
	private String nomeCentroTransplante;
	private EnderecoDTO enderecoEntrega;
	private String nomeFonteCelula;

}
