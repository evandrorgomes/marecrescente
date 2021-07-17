package br.org.cancer.redome.feign.client.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.org.cancer.redome.feign.client.domain.TiposSolicitacao;
import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Classe de dto para Solicitacao.
 * 
 * @author bruno.sousa
 */
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
public class SolicitacaoDTO implements Serializable {

	private static final long serialVersionUID = -98575266789449732L;

	@Getter
	private Long id;
	@Getter
	private TipoSolicitacaoDTO tipoSolicitacao;

	private UsuarioDTO usuario;
	
	@Getter
	private Long faseWorkup;
	
	@Default
	@Getter
	private LocalDateTime dataCriacao = LocalDateTime.now();
	
	@Default
	@Getter
	private Integer status = 0;
	@Getter
	private MatchDTO match;
	
	@Getter
	private CentroTransplanteDTO centroColeta;

	@Getter
	private CentroTransplanteDTO centroTransplante;
	
	public Boolean solicitacaoWorkupNacional() {
		return TiposSolicitacao.WORKUP_DOADOR_NACIONAL_PACIENTE_NACIONAL.getId().equals(tipoSolicitacao.getId()) || 
				TiposSolicitacao.CORDAO_NACIONAL_PACIENTE_NACIONAL.getId().equals(tipoSolicitacao.getId());
	}
	
	public Boolean solicitacaoWorkupInternacional() {
		return TiposSolicitacao.WORKUP_DOADOR_INTERNACIONAL.getId().equals(tipoSolicitacao.getId()) || 
				TiposSolicitacao.CORDAO_INTERNACIONAL.getId().equals(tipoSolicitacao.getId());
	}
	
	public Boolean solicitacaoWorkupMedula() {
		return TiposSolicitacao.WORKUP_DOADOR_NACIONAL_PACIENTE_NACIONAL.getId().equals(tipoSolicitacao.getId()) || 
				TiposSolicitacao.WORKUP_DOADOR_INTERNACIONAL.getId().equals(tipoSolicitacao.getId());
	}
	
	public Boolean solicitacaoWorkupCordao() {
		return TiposSolicitacao.CORDAO_NACIONAL_PACIENTE_NACIONAL.getId().equals(tipoSolicitacao.getId()) || 
				TiposSolicitacao.CORDAO_INTERNACIONAL.getId().equals(tipoSolicitacao.getId());
	}

	
}
