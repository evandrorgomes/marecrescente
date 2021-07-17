package br.org.cancer.redome.workup.dto;

import java.io.Serializable;
import java.time.LocalDate;

import br.org.cancer.redome.workup.model.ResultadoWorkup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe de DTO de resultado de workup. 
 * 
 * @author ergomes
 * 
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Data
public class ResultadoWorkupNacionalDTO implements Serializable {

	private static final long serialVersionUID = 5991252479966697681L;
	
	private Boolean coletaInviavel;
	
	private String motivoInviabilidade;
	
	private Long idPedidoWorkup;
	
	private Long idFonteCelula;
	
	private LocalDate dataColeta;
	
	private LocalDate dataGCSF;
	
	private Boolean adeguadoAferese;
	
	private String acessoVenosoCentral;
	
	private Boolean sangueAntologoColetado;
	
	private String motivoSangueAntologoNaoColetado;

	/**
	 * @param coletaInviavel
	 * @param motivoInviabilidade
	 * @param idPedidoWorkup
	 * @param pedidosAdicionaisWorkup
	 */
	public ResultadoWorkupNacionalDTO(ResultadoWorkup resultadoWorkup) {

		this.coletaInviavel = resultadoWorkup.getColetaInviavel();
		this.motivoInviabilidade = resultadoWorkup.getMotivoInviabilidade();
		this.idPedidoWorkup = resultadoWorkup.getPedidoWorkup().getId();
	}

}