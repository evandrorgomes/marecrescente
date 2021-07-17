package br.org.cancer.redome.workup.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.org.cancer.redome.workup.model.annotation.AssertTrueCustom;
import br.org.cancer.redome.workup.model.domain.FontesCelulas;
import br.org.cancer.redome.workup.model.domain.TiposResultadoWorkup;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe de armazenamento de resultado de workup. Nesta classe estarão representados os dados de resultados de exames de workup
 * imputados pelo centro de transplante. Os dados principais do resultado são os arquivos que serão enviados para o servidor.
 * 
 * @author Filipe Paes
 * 
 */
@Entity
@SequenceGenerator(name = "SQ_REWO_ID", sequenceName = "SQ_REWO_ID", allocationSize = 1)
@Table(name = "RESULTADO_WORKUP")
@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ResultadoWorkup implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_REWO_ID")
	@Column(name="REWO_ID")
	private Long id;

	@ManyToOne
	@JoinColumn(name="PEWO_ID")
	@NotNull
	private PedidoWorkup pedidoWorkup;

	@Column(name = "REWO_DT_CRIACAO")
	@NotNull
	@Default
	private LocalDateTime dataCriacao = LocalDateTime.now();

	@Column(name = "USUA_ID")
	@NotNull
	private Long usuario;

	@Column(name = "REWO_IN_TIPO")
	@NotNull
	private Long tipo;
	
	@Column(name = "REWO_IN_DOADOR_INDISPONIVEL")
	@Default        
	private Boolean doadorIndisponivel = false;

	@Column(name = "REWO_IN_COLETA_INVIAVEL")
	@Default        
	private Boolean coletaInviavel = false;
	
	@Column(name = "REWO_TX_MOTIVO_COLETA_INVIAVEL")
	private String motivoInviabilidade;

	@OneToMany(mappedBy="resultadoWorkup", cascade=CascadeType.ALL)
	private List<ArquivoResultadoWorkup> arquivosResultadoWorkup;
	
	@ManyToOne
	@JoinColumn(name="FOCE_ID")
	private FonteCelula fonteCelula;
	
	@Column(name = "REWO_DT_COLETA")	
	private LocalDate dataColeta;
	
	@Column(name = "REWO_DT_GCSF")
	private LocalDate dataGCSF;
	
	@Column(name = "REWO_IN_ADEGUADO_AFERESE")
	private Boolean adeguadoAferese;
	
	@Column(name = "REWO_TX_ACESSO_VENOSO_CENTRAL")
	private String acessoVenosoCentral;
	
	@Column(name = "REWO_IN_SANGUE_ANTOLOGO_COLETADO")
	private Boolean sangueAntologoColetado;
	
	@Column(name = "REWO_TX_MOTIVO_SA_NAO_COLETADO")
	private String motivoSangueAntologoNaoColetado;
	
	@AssertTrueCustom(message = "{javax.validation.constraints.NotNull.message}", field = "coletaInviavel")
	@JsonIgnore
	public boolean isColetaInviavelPeloRegistroInernacionalObrigatorio() {
		return (tipo != null && tipo.equals(TiposResultadoWorkup.INTERNACIONAL.getId()) && coletaInviavel == null ? false : true);
	}
	
	@AssertTrueCustom(message = "{javax.validation.constraints.NotNull.message}", field = "doadorIndisponivel")
	@JsonIgnore
	public boolean isDoadorIndisponivelPeloRegistroInernacionalObrigatorio() {
		return (tipo != null && tipo.equals(TiposResultadoWorkup.INTERNACIONAL.getId()) && coletaInviavel != null && coletaInviavel  && doadorIndisponivel == null ? false : true);
	}
	
	@AssertTrueCustom(message = "{javax.validation.constraints.NotNull.message}", field = "fonteCelula")
	@JsonIgnore
	public boolean isFonteCelulaObrigatorioParaTipoNacional() {
		return (tipo != null && tipo.equals(TiposResultadoWorkup.NACIONAL.getId()) && coletaInviavel != null && !coletaInviavel && fonteCelula == null ? false : true);
	}
	
	@AssertTrueCustom(message = "{javax.validation.constraints.NotNull.message}", field = "dataColeta")
	@JsonIgnore
	public boolean isDataColetaObrigatorioParaTipoNacional() {
		return (tipo != null && tipo.equals(TiposResultadoWorkup.NACIONAL.getId()) && dataColeta == null ? false : true);
	}
	
	@AssertTrueCustom(message = "{javax.validation.constraints.NotNull.message}", field = "dataGCSF")
	@JsonIgnore
	public boolean isDataGCSFObrigatorioParaFonteSanguePerifierico() {
		return (tipo != null && tipo.equals(TiposResultadoWorkup.NACIONAL.getId()) &&
				fonteCelula != null && fonteCelula.getId().equals(FontesCelulas.SANGUE_PERIFERICO.getFonteCelulaId()) && dataGCSF == null  ? false : true);
	}
	
	@AssertTrueCustom(message = "{javax.validation.constraints.NotNull.message}", field = "adeguadoAferese")
	@JsonIgnore
	public boolean isAdeguadoAfereseObrigatorioParaFonteSanguePerifierico() {
		return (tipo != null && tipo.equals(TiposResultadoWorkup.NACIONAL.getId()) &&
				fonteCelula != null && fonteCelula.getId().equals(FontesCelulas.SANGUE_PERIFERICO.getFonteCelulaId()) && adeguadoAferese == null  ? false : true);
	}
	
	@AssertTrueCustom(message = "{javax.validation.constraints.NotNull.message}", field = "acessoVenosoCentral")
	@JsonIgnore
	public boolean isAcessoVenosoCentralObrigatorioParaFonteSanguePerifiericoENaoAdeguadoAferese() {
		return (tipo != null && tipo.equals(TiposResultadoWorkup.NACIONAL.getId()) &&
				fonteCelula != null && fonteCelula.getId().equals(FontesCelulas.SANGUE_PERIFERICO.getFonteCelulaId()) && adeguadoAferese != null && !adeguadoAferese && acessoVenosoCentral == null  ? false : true);
	}
	
	@AssertTrueCustom(message = "{javax.validation.constraints.NotNull.message}", field = "sangueAntologoColetado")
	@JsonIgnore
	public boolean isSangueAntologoColetadoObrigatorioParaFonteMedulaOssea() {
		return (tipo != null && tipo.equals(TiposResultadoWorkup.NACIONAL.getId()) &&
				fonteCelula != null && fonteCelula.getId().equals(FontesCelulas.MEDULA_OSSEA.getFonteCelulaId()) && sangueAntologoColetado == null  ? false : true);
	}
	
	@AssertTrueCustom(message = "{javax.validation.constraints.NotNull.message}", field = "motivoSangueAntologoNaoColetado")
	@JsonIgnore
	public boolean isMotivoSangueAntologoNaoColetadoObrigatorioParaFonteMedulaOsseaESangueAntologoNaoColetado() {
		return (tipo != null && tipo.equals(TiposResultadoWorkup.NACIONAL.getId()) &&
				fonteCelula != null && fonteCelula.getId().equals(FontesCelulas.MEDULA_OSSEA.getFonteCelulaId()) && sangueAntologoColetado != null && !sangueAntologoColetado && motivoSangueAntologoNaoColetado == null  ? false : true);
	}

}