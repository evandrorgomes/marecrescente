package br.org.cancer.redome.workup.model.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum CategoriasNotificacao {
	
	PRESCRICAO_NACIONAL(3L, Perfis.ANALISTA_WORKUP),
	PRESCRICAO_INTERNACIONAL(14L, Perfis.ANALISTA_WORKUP_INTERNACIONAL),
	AVALIACAO_RESULTADO_WORKUP(12L, Perfis.MEDICO_TRANSPLANTADOR),
	PRESCRICAO_INTERNACIONAL_CANCELADA(14L, Perfis.MEDICO_TRANSPLANTADOR),
	AVALIACAO_PEDIDO_COLETA(6L, null),
	AGENDAMENTO_COLETA(11l, Perfis.MEDICO_TRANSPLANTADOR),
	RECEBIMENTO_COLETA(8L, Perfis.MEDICO_TRANSPLANTADOR);
	
	@Getter
	private Long id;
	
	@Getter
	private Perfis perfil;

}
