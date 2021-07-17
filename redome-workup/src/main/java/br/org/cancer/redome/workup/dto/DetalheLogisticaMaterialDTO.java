package br.org.cancer.redome.workup.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Classe de DTO para dados especificos da tela de pedido de transporte.
 * 
 * @author Filipe Paes
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Getter
@Setter
public class DetalheLogisticaMaterialDTO {

	private Long idPedidoLogistica;
	private Long rmr;
	private Long idDoador;
	private Long identificacao;
	private String nomeDoador;
	private String dataColeta;
	private Long idTipoDoador;
	private String nomeFonteCelula;
	private String nomeCentroTransplante;
	
	private String nomeLocalRetirada;
	private String enderecoRetiradaBancoCordao;
	
	private EnderecoDTO enderecoLocalRetirada;
	private EnderecoDTO enderecoLocalEntrega;
	
	private List<String> contatosLocalRetirada;

	private Boolean tipoAereo;
	private String justificativa;
	private Boolean prosseguirComPedidoLogistica;
	
	private Long transportadora;
	private LocalDateTime horaPrevistaRetirada;

	//####################################### 
	
//	private List<String> contatosCentroTransplante;
//
//	private String dadosVoo;
//	
//	private List<String> contatosLocalRetirada;
//	
//	private String retiradaLocal;
//	private String retiradaPais;
//	private String retiradaEstado;
//	private String retiradaCidade;
//	private String retiradaRua;
//	private String retiradaTelefone;
//	private String retiradaIdDoador;
//	private String retiradaHawb;
//	
//	private String nomeCourier;
//	private String passaporteCourier;
//	
//	private LocalDate dataEmbarque;
//	private LocalDate dataChegada;
	
//	private String enderecoCentroTransplante;

	
	private DetalheLogisticaMaterialAereoDTO materialAereo;
	
}
