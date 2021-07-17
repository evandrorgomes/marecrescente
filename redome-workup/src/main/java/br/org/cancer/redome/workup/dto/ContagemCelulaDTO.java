package br.org.cancer.redome.workup.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import br.org.cancer.redome.workup.model.ContagemCelula;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ContagemCelulaDTO implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 7855253934245111308L;
	
	private Long id;
	private Long idPedidoWorkup;
	private String fonteCelula;
	private String abo;
	private Boolean manipulacaoProduto;
	private String manipulacao;
	private LocalDateTime dataColeta;
	private Long volumeBolsa;
	private Long volumeAnticoagulante;
	private Long quantidadeTCN;
	private Long quantidadeTCNKG;
	
	private LocalDateTime dataAferese_0;
	private Long celula_0;
	private Long celulaKg_0;
	private Long volumeBolsa_0;
	
	private LocalDateTime dataAferese_1;
	private Long volumeBolsa_1;
	private Long celula_1;
	private Long celulaKg_1;
	
	private LocalDateTime dataAferese_2;
	private Long volumeBolsa_2;
	private Long celula_2;
	private Long celulaKg_2;
	
	private LocalDateTime diaEnvio;
	private String anticoagulante;
	private Boolean houveIntercorrencia;
	private String intercorrencia;
	

	public static ContagemCelulaDTO parseToDTO(ContagemCelula contagemCelula) {
		
		
		ContagemCelulaDTO contagemCelulaDTO = ContagemCelulaDTO.builder()
				.idPedidoWorkup(contagemCelula.getIdPedidoWorkup())
				.fonteCelula(contagemCelula.getFonteCelula())
				.abo(contagemCelula.getAbo())
				.manipulacaoProduto(contagemCelula.getManipulacaoProduto())
				.manipulacao(contagemCelula.getManipulacao())
				.dataColeta(contagemCelula.getDataColeta())
				.volumeBolsa(contagemCelula.getVolumeBolsa())
				.volumeAnticoagulante(contagemCelula.getVolumeAnticoagulante())
				.quantidadeTCN(contagemCelula.getQuantidadeTCN())
				.quantidadeTCNKG(contagemCelula.getQuantidadeTCNKG())
				.dataAferese_0(contagemCelula.getDataAferese_0())
				.volumeBolsa_0(contagemCelula.getVolumeBolsa_0())
				.celula_0(contagemCelula.getCelula_0())
				.celulaKg_0(contagemCelula.getCelulaKg_0())
				.dataAferese_1(contagemCelula.getDataAferese_1())
				.volumeBolsa_1(contagemCelula.getVolumeBolsa_1())
				.celula_1(contagemCelula.getCelula_1())
				.celulaKg_1(contagemCelula.getCelulaKg_1())
				.dataAferese_2(contagemCelula.getDataAferese_2())
				.volumeBolsa_2(contagemCelula.getVolumeBolsa_2())
				.celula_2(contagemCelula.getCelula_2())
				.celulaKg_2(contagemCelula.getCelulaKg_2())
				.diaEnvio(contagemCelula.getDiaEnvio())
				.anticoagulante(contagemCelula.getAnticoagulante())
				.houveIntercorrencia(contagemCelula.getHouveIntercorrencia())
				.intercorrencia(contagemCelula.getIntercorrencia())
				.build();
		
		return contagemCelulaDTO;
	}
	
	
}