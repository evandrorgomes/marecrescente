package br.org.cancer.redome.courier.controller.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import br.org.cancer.redome.courier.feign.client.dto.EnderecoDTO;
import br.org.cancer.redome.courier.model.ArquivoPedidoTransporte;
import br.org.cancer.redome.courier.model.StatusPedidoTransporte;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DetalhePedidoTransporteDTO implements Serializable {

	private static final long serialVersionUID = 6078036650203362092L;
	
	private Long idPedidoTransporte;
	private String identificacaoDoador;
	private Long rmr;
	private String nomeLocalRetirada;
	private EnderecoDTO enderecoLocalRetirada;
	private String enderecoLocalRetiradaBancoCordao;
	private String nomeCentroTransplante;
	private EnderecoDTO enderecoCentroTransplante;
	private String nomeFonteCelula;
	private StatusPedidoTransporte status;
	private List<ArquivoPedidoTransporte> arquivos;
	private LocalDateTime horaPrevistaRetirada;
	private String dadosVoo;
	
	
	

}
