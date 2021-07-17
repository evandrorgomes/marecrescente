package br.org.cancer.redome.workup.model.domain;

/**
 * Enum para tipos eventos passíveis de gerar log de evolução.
 * 
 * @author Pizão
 *
 */
public enum TipoLogEvolucao {
	
	INDEFINIDO,
    PRESCRICAO_CRIADA_PARA_MEDULA,
    PRESCRICAO_CRIADA_PARA_CORDAO,
    PRESCRICAO_APROVADA_PARA_MEDULA,
    PRESCRICAO_REPROVADA_PARA_MEDULA,
    PRESCRICAO_APROVADA_PARA_CORDAO,
    PRESCRICAO_REPROVADA_PARA_CORDAO,
    LOGISTICA_DOADOR_WORKUP_CONFIRMADA_PARA_DOADOR,
    LOGISTICA_DOADOR_COLETA_CONFIRMADA_PARA_DOADOR,
    AVALIACAO_RESULTADO_PEDIDO_WORKUP_APROVADO_PARA_DOADOR,
    AVALIACAO_RESULTADO_PEDIDO_WORKUP_NAO_APROVADO_PARA_DOADOR,
    ANALISE_MEDICA_DOADOR_COLETA_PARA_DOADOR,
    AVALIACAO_PEDIDO_COLETA,
    RESULTADO_PEDIDO_WORKUP_INVIAVEL_APROVADO_PARA_DOADOR,
    RESULTADO_PEDIDO_WORKUP_INVIAVEL_NAO_APROVADO_PARA_DOADOR,
    INCLUIR_EXAME_ADICIONAL_WORKUP_PARA_DOADOR,
    INCLUIDO_EXAME_ADICIONAL_WORKUP_PARA_DOADOR,
    AGENDAMENTO_COLETA_PARA_DOADOR,
    RECEBIMENTO_COLETA_PARA_DOADOR;
    
}
