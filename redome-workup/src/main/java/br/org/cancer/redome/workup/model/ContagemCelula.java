package br.org.cancer.redome.workup.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.org.cancer.redome.workup.dto.ContagemCelulaDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe de persistencia de um cadastro de contagem celula.
 * 
 * @author ruan.agra
 *
 */
@Entity
@Table(name="CONTAGEM_CELULA")
@SequenceGenerator(name = "SQ_COCE_ID", sequenceName = "SQ_COCE_ID", allocationSize = 1)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder(toBuilder = true)

public class ContagemCelula implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = -934021192628539653L;
	/**
	 * Identificador da contagem celua.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_COCE_ID")
	@Column(name = "COCE_ID")
	private Long id;
	/**
	 * Identificador do pedido de workup.
	 */
	@Column(name = "PEWO_ID")
	private Long idPedidoWorkup;

	/**
	 * Fonte de Celula: 0-Medula, 1-periferico e 2-linfocito.
	 */
	@Column(name = "COCE_IN_FONTE_CELULA")
	private String fonteCelula;
	
	/**
	 * ABO: 0-maior, 1-menor e 2-incompativel.
	 */
	@Column(name = "COCE_IN_ABO")
	private String abo;
	
	/**
	 * Flag Manipulação do produto.
	 */
	@Column(name = "COCE_IN_MANIPULACAO_PRODUTO")
	private Boolean manipulacaoProduto;
	
	/**
	 * Qual Manipulação do produto.
	 */
	@Column(name = "COCE_TX_MANIPULACAO")
	private String manipulacao;
	
	/**
	 * Data da Coleta de Medula.
	 */
	@Column(name = "COCE_DT_COLETA")
	private LocalDateTime dataColeta;
	
	/**
	 *Volume da bolsa ao Coletar Medula.
	 */
	@Column(name = "COCE_VL_VOLUME_BOLSA")
	private Long volumeBolsa;
	
	/**
	 *Volume de Anticoagulante na Bolsa ao Coletar Medula.
	 */
	@Column(name = "COCE_VL_ANTICOAGULANTE")
	private Long volumeAnticoagulante;
	
	/**
	 *Quantidade de TCN na Bolsa ao Coletar Medula.
	 */
	@Column(name = "COCE_NR_TCN")
	private Long quantidadeTCN;
	
	/**
	 *Quantidade de TCN por kg na Bolsa ao Coletar Medula.
	 */
	@Column(name = "COCE_NR_TCN_KG")
	private Long quantidadeTCNKG;
	
	/**
	 * Data da 1ª aferise sangue periferico ou Linfocito.
	 */
	@Column(name = "COCE_DT_AFERISE_0")
	private LocalDateTime dataAferese_0;
	
	/**
	 *Volume da bolsa da 1ª aferise. 
	 */
	@Column(name = "COCE_VL_VOLUME_BOLSA_0")
	private Long volumeBolsa_0;
	
	/**
	 *Numero de Celulas da 1ª aferise. 
	 */
	@Column(name = "COCE_NR_CELULA_0")
	private Long Celula_0;
	
	/**
	 *Numero de Celulas por Kg do paciente da 1ª aferise. 
	 */
	@Column(name = "COCE_NR_CELULA_KG_0")
	private Long CelulaKg_0;
	
	/**
	 * Data da 2ª aferise sangue periferico ou Linfocito.
	 */
	@Column(name = "COCE_DT_AFERISE_1")
	private LocalDateTime dataAferese_1;
	
	/**
	 *Volume da bolsa da 2ª aferise. 
	 */
	@Column(name = "COCE_VL_VOLUME_BOLSA_1")
	private Long volumeBolsa_1;
	
	/**
	 *Numero de Celulas da 2ª aferise. 
	 */
	@Column(name = "COCE_NR_CELULA_1")
	private Long Celula_1;
	
	/**
	 *Numero de Celulas por Kg do paciente da 2ª aferise. 
	 */
	@Column(name = "COCE_NR_CELULA_KG_1")
	private Long CelulaKg_1;
	
	/**
	 * Data da 3ª aferise sangue periferico ou Linfocito.
	 */
	@Column(name = "COCE_DT_AFERISE_2")
	private LocalDateTime dataAferese_2;
	
	/**
	 *Volume da bolsa da 3ª aferise. 
	 */
	@Column(name = "COCE_VL_VOLUME_BOLSA_2")
	private Long volumeBolsa_2;
	
	/**
	 *Numero de Celulas da 3ª aferise. 
	 */
	@Column(name = "COCE_NR_CELULA_2")
	private Long Celula_2;
	
	/**
	 *Numero de Celulas por Kg do paciente da 3ª aferise. 
	 */
	@Column(name = "COCE_NR_CELULA_KG_2")
	private Long CelulaKg_2;
	
	/**
	 * Data do Envio da coleta.
	 */
	@Column(name = "COCE_DT_ENVIO")
	private LocalDateTime diaEnvio;
	
	/**
	 * Anticoagulante Utilizado.
	 */
	@Column(name = "COCE_TX_ANTICOAGULANTE")
	private String anticoagulante;
	
	/**
	 * Flag intercorrência durante ou após a coleta de CTH.
	 */
	@Column(name = "COCE_IN_INTERCORRENCIA")
	private Boolean houveIntercorrencia;
	
	/**
	 * Quais intercorrências durante ou após a coleta de CTH
	 */
	@Column(name = "COCE_TX_INTERCORRENCIA")
	private String intercorrencia;
	
	
	public static ContagemCelula parseDTO(ContagemCelulaDTO contagemCelulaDTO) {
		
		ContagemCelula contagemCelula = ContagemCelula.builder()
				.idPedidoWorkup(contagemCelulaDTO.getIdPedidoWorkup())
				.fonteCelula(contagemCelulaDTO.getFonteCelula())
				.abo(contagemCelulaDTO.getAbo())
				.manipulacaoProduto(contagemCelulaDTO.getManipulacaoProduto())
				.manipulacao(contagemCelulaDTO.getManipulacao())
				.dataColeta(contagemCelulaDTO.getDataColeta())
				.volumeBolsa(contagemCelulaDTO.getVolumeBolsa())
				.volumeAnticoagulante(contagemCelulaDTO.getVolumeAnticoagulante())
				.quantidadeTCN(contagemCelulaDTO.getQuantidadeTCN())
				.quantidadeTCNKG(contagemCelulaDTO.getQuantidadeTCNKG())
				.dataAferese_0(contagemCelulaDTO.getDataAferese_0())
				.volumeBolsa_0(contagemCelulaDTO.getVolumeBolsa_0())
				.Celula_0(contagemCelulaDTO.getCelula_0())
				.CelulaKg_0(contagemCelulaDTO.getCelulaKg_0())
				.dataAferese_1(contagemCelulaDTO.getDataAferese_1())
				.volumeBolsa_1(contagemCelulaDTO.getVolumeBolsa_1())
				.Celula_1(contagemCelulaDTO.getCelula_1())
				.CelulaKg_1(contagemCelulaDTO.getCelulaKg_1())
				.dataAferese_2(contagemCelulaDTO.getDataAferese_2())
				.volumeBolsa_2(contagemCelulaDTO.getVolumeBolsa_2())
				.Celula_2(contagemCelulaDTO.getCelula_2())
				.CelulaKg_2(contagemCelulaDTO.getCelulaKg_2())
				.diaEnvio(contagemCelulaDTO.getDiaEnvio())
				.anticoagulante(contagemCelulaDTO.getAnticoagulante())
				.houveIntercorrencia(contagemCelulaDTO.getHouveIntercorrencia())
				.intercorrencia(contagemCelulaDTO.getIntercorrencia())
				.build();
		
		return contagemCelula;
	}
	
public static ContagemCelula parseObjeto(ContagemCelula contagemCelula, ContagemCelulaDTO contagemCelulaDTO) {
		
		contagemCelula = new ContagemCelula().toBuilder()
				.idPedidoWorkup(contagemCelulaDTO.getIdPedidoWorkup())
				.fonteCelula(contagemCelulaDTO.getFonteCelula())
				.abo(contagemCelulaDTO.getAbo())
				.manipulacaoProduto(contagemCelulaDTO.getManipulacaoProduto())
				.manipulacao(contagemCelulaDTO.getManipulacao())
				.dataColeta(contagemCelulaDTO.getDataColeta())
				.volumeBolsa(contagemCelulaDTO.getVolumeBolsa())
				.volumeAnticoagulante(contagemCelulaDTO.getVolumeAnticoagulante())
				.quantidadeTCN(contagemCelulaDTO.getQuantidadeTCN())
				.quantidadeTCNKG(contagemCelulaDTO.getQuantidadeTCNKG())
				.dataAferese_0(contagemCelulaDTO.getDataAferese_0())
				.volumeBolsa_0(contagemCelulaDTO.getVolumeBolsa_0())
				.Celula_0(contagemCelulaDTO.getCelula_0())
				.CelulaKg_0(contagemCelulaDTO.getCelulaKg_0())
				.dataAferese_1(contagemCelulaDTO.getDataAferese_1())
				.volumeBolsa_1(contagemCelulaDTO.getVolumeBolsa_1())
				.Celula_1(contagemCelulaDTO.getCelula_1())
				.CelulaKg_1(contagemCelulaDTO.getCelulaKg_1())
				.dataAferese_2(contagemCelulaDTO.getDataAferese_2())
				.volumeBolsa_2(contagemCelulaDTO.getVolumeBolsa_2())
				.Celula_2(contagemCelulaDTO.getCelula_2())
				.CelulaKg_2(contagemCelulaDTO.getCelulaKg_2())
				.diaEnvio(contagemCelulaDTO.getDiaEnvio())
				.anticoagulante(contagemCelulaDTO.getAnticoagulante())
				.houveIntercorrencia(contagemCelulaDTO.getHouveIntercorrencia())
				.intercorrencia(contagemCelulaDTO.getIntercorrencia())
				.build();
		
		return contagemCelula;
	}
	
}