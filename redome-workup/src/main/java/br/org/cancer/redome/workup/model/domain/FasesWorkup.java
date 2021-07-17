package br.org.cancer.redome.workup.model.domain;

import java.util.stream.Stream;

import br.org.cancer.redome.workup.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum FasesWorkup {
	
	AGUARDANDO_AVALIACAO_PRESCRICAO(1L, "fase.workup.aguardando.avaliacao.prescricao"),
	AGUARDANDO_DISTRIBUICAO_WORKUP(2L, "fase.workup.aguardando.distribuicao.workup"),
	AGUARDANDO_FORMULARIO_DOADOR(3L, "fase.workup.aguardando.formulario.doador"),
	AGUARDANDO_DEFINIR_CENTRO(4L, "fase.workup.aguardando.definir.centro.coleta"),
	AGUARDANDO_PLANO_WORKUP(5L, "fase.workup.aguardando.plano.workup"),
	AGUARDANDO_ACEITE_PLANO(6L, "fase.workup.aguardando.aprovacao.plano"),
	AGUARDANDO_AGENDAMENTO_COLETA(7L, "fase.workup.aguardando.agendamento.coleta"),
	AGUARDANDO_AVALIACAO_RESULTADO_WORKUP(8L, "fase.workup.aguardando.avaliacao.resultado.workup"),
	AGUARDANDO_RESULTADO_WORKUP(9L, "fase.workup.aguardando.resultado.workup"),
	AGUARDANDO_LOGISTICA_DOADOR_WORKUP(10L, "fase.workup.aguardando.logistica.doador.workup"),
	AGUARDANDO_AUTORIZACAO_PACIENTE(11L, "fase.workup.aguardando.autorizacao.paciente"),
	AGUARDANDO_AVALIACAO_PEDIDO_COLETA(12L, "fase.workup.aguardando.avaliacao.pedido.coleta"),
	AGUARDANDO_EXAME_ADICIONAL_DOADOR(13L, "fase.workup.aguardando.exame.adicional.doador"),
	AGUARDANDO_DEFINICAO_LOGISTICA(14L, "fase.workup.aguardando.logistica.coleta.doador"),
	AGUARDANDO_LOGISTICA_COLETA_INTERNACIONAL(15L, "fase.workup.aguardando.logistica.material.workup"),
	AGUARDANDO_RECEBIMENTO_COLETA(16L, "fase.workup.aguardando.logistica.recebimento.coleta"),
	AGUARDANDO_ANALISE_PEDIDO_TRANSPORTE(17L, "fase.workup.aguardando.analise.pedido.transporte"),
    AGUARDANDO_DOCUMENTACAO_PEDIDO_TRANSPORTE_AEREO(18L, "fase.workup.aguardando.documentacao.pedido.transporte.aereo"),
	AGUARDANDO_RETIRADA_MATERIAL(19L, "fase.workup.aguardando.retirada.material"),
	AGUARDANDO_RESULTADO_DOADOR_COLETA(20L, "fase.workup.aguardando.resultado.doador.coleta"),
	AGUARDANDO_INFORMACAO_POSCOLETA(21L, "fase.workup.aguardando.informacao.poscoleta");
	
	private Long id;
	private String messageId;
	
	public static FasesWorkup valueOf(Long id) {
		return Stream.of(FasesWorkup.values())
				.filter(fase -> fase.getId().equals(id)).findFirst()
				.orElseThrow(() -> new BusinessException(""));
	}

}
