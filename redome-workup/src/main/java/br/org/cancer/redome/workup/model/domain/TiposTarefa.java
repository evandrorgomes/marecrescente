package br.org.cancer.redome.workup.model.domain;

import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum TiposTarefa {
	
	CADASTRAR_PRESCRICAO(106L),
	CADASTRAR_PRESCRICAO_MEDULA(52L),
	CADASTRAR_PRESCRICAO_CORDAO(105L),
	AVALIAR_PRESCRICAO(49L),
	AUTORIZACAO_PACIENTE(78L),
	DISTRIBUIR_WORKUP_NACIONAL(110L),
	DISTRIBUIR_WORKUP_INTERNACIONAL(111L),
	CADASTRAR_FORMULARIO_DOADOR(112L),
	DEFINIR_CENTRO_COLETA(114L),
	INFORMAR_PLANO_WORKUP_NACIONAL(115L),
	INFORMAR_PLANO_WORKUP_INTERNACIONAL(118L),
	WORKUP(113L),
	WORKUP_CENTRO_COLETA(116L),
	CONFIRMAR_PLANO_WORKUP(117L),
	INFORMAR_DETALHE_WORKUP_NACIONAL(119L),
	INFORMAR_RESULTADO_WORKUP_INTERNACIONAL(220L),
	AVALIAR_RESULTADO_WORKUP_NACIONAL(33L),
	AVALIAR_RESULTADO_WORKUP_INTERNACIONAL(222L),
	WORKUP_CENTRO_TRANSPLANTE(221L),
	INFORMAR_LOGISTICA_DOADOR_WORKUP(36L),
	INFORMAR_RESULTADO_WORKUP_NACIONAL(35L),
	INFORMAR_AUTORIZACAO_PACIENTE(223L),
	AGENDAR_COLETA_INTERNACIONAL(72L),
	AGENDAR_COLETA_NACIONAL(224L),	
	AVALIAR_PEDIDO_COLETA(37L),
	
	INFORMAR_EXAME_ADICIONAL_WORKUP_NACIONAL(225L),
	INFORMAR_EXAME_ADICIONAL_WORKUP_INTERNACIONAL(226L),
	
	INFORMAR_LOGISTICA_MATERIAL_COLETA_INTERNACIONAL(84L),
	INFORMAR_LOGISTICA_MATERIAL_COLETA_NACIONAL(227L),
	
	INFORMAR_LOGISTICA_DOADOR_COLETA(228L),
	
	INFORMAR_RECEBIMENTO_COLETA(50L),
	ANALISAR_PEDIDO_TRANSPORTE(55L),

	INFORMAR_RESULTADO_DOADOR_COLETA(229L),
	INFORMAR_DOCUMENTACAO_MATERIAL_AEREO(42L),
	LOGISTICA(59L),
	INFORMAR_FORMULARIO_POSCOLETA(230L),
	CADASTRAR_COLETA_CONTAGEM_CELULA(231L);

	@Getter
	private Long id;
	
	
	public static TiposTarefa valueOf(Long id) {
		if (id == null) {
			return null;
		}
		return Stream.of(TiposTarefa.values()).filter(tipo -> tipo.getId().equals(id)).findFirst().orElse(null);
	}
	

}