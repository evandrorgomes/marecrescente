package br.org.cancer.redome.workup.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import br.org.cancer.redome.workup.model.ArquivoVoucherLogistica;
import br.org.cancer.redome.workup.model.TransporteTerrestre;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Informações sobre o detalhe da logística necessária ao doador ou material.
 * 
 * @author Pizão.
 *
 */
@NoArgsConstructor
@Getter
@Builder
public class LogisticaDoadorColetaDTO implements Serializable {
	
	private static final long serialVersionUID = -3652795976912962282L;

	private List<TransporteTerrestre> transporteTerrestre;
	
	private List<ArquivoVoucherLogistica> aereos;

	private List<ArquivoVoucherLogistica> hospedagens;
	
	private String observacao;

	private Long idPedidoColeta;

	private Long idCentroColeta;

	private LocalDate dataColeta;
	
	private boolean prosseguirComPedidoLogistica;

	private String justificativa;
	
	/**
	 * @param transporteTerrestre
	 * @param aereos
	 * @param hospedagens
	 * @param observacao
	 */
	@Builder
	public LogisticaDoadorColetaDTO(List<TransporteTerrestre> transporteTerrestre, List<ArquivoVoucherLogistica> aereos,
			List<ArquivoVoucherLogistica> hospedagens, String observacao, Long idPedidoColeta, Long idCentroColeta, 
			LocalDate dataColeta, boolean prosseguirComPedidoLogistica, String justificativa) {
		super();
		this.transporteTerrestre = transporteTerrestre;
		this.aereos = aereos;
		this.hospedagens = hospedagens;
		this.observacao = observacao;
		this.idPedidoColeta = idPedidoColeta;
		this.idCentroColeta = idCentroColeta;
		this.dataColeta = dataColeta;
		this.prosseguirComPedidoLogistica = prosseguirComPedidoLogistica;
		this.justificativa = justificativa;
	}
	
}
