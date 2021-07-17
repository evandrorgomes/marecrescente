package br.org.cancer.redome.workup.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.org.cancer.redome.workup.model.annotation.AssertTrueCustom;
import br.org.cancer.redome.workup.model.domain.TiposResultadoWorkup;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe de persistencia de avaliação de resultado de workup.
 * 
 * @author Fillipe Queiroz
 *
 */
@Entity
@Table(name = "AVALIACAO_RESULTADO_WORKUP")
@SequenceGenerator(name = "SQ_AVRW_ID", sequenceName = "SQ_AVRW_ID", allocationSize = 1)
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor()
@Builder
public class AvaliacaoResultadoWorkup implements Serializable {

	private static final long serialVersionUID = 5170101253604507919L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_AVRW_ID")
	@Column(name = "AVRW_ID")
	private Long id;

	@Column(name = "AVRW_DT_CRIACAO")
	@NotNull
	@Default
	private LocalDateTime dataCriacao = LocalDateTime.now();

	@Column(name = "AVRW_DT_ATUALIZACAO")
	@Default
	private LocalDateTime dataAtualizacao = LocalDateTime.now();;

	@Column(name = "USUA_ID_RESPONSAVEL")
	private Long usuarioResponsavel;

	@Column(name = "AVRW_IN_PROSSEGUIR")
	@Default
	private Boolean prosseguir = false;

	@Column(name = "AVRW_TX_JUSTIFICATIVA")
	private String justificativa;

	@OneToOne
	@JoinColumn(name = "REWO_ID")
	private ResultadoWorkup resultadoWorkup;
	


	@AssertTrueCustom(message = "{javax.validation.constraints.NotNull.message}", field = "justificativa")
	@JsonIgnore
	public boolean isColetaInviavelPeloRegistroInernacionalObrigatorio() {
		return (resultadoWorkup != null && resultadoWorkup.getTipo().equals(TiposResultadoWorkup.INTERNACIONAL.getId()) && 
				resultadoWorkup.getColetaInviavel() && StringUtils.isEmpty(justificativa) ? false : true);
	}
	
	
	
	

}