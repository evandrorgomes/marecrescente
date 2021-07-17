package br.org.cancer.modred.model.domain;

/**
 * Enum que representa os IDs da tabela de MotivoCancelamentoColeta.
 * São os motivos informados pelo usuário ou pelo sistema, ao ocorrer alguma 
 * ação no pedido de coleta ou em entidades relacionadas.
 * 
 * @author Pizão
 *
 */
public enum MotivosCancelamentoColeta {
	CT_NAO_RESPONDE,
	SEM_DATAS_DISPONIVEIS,
	CT_RECUSOU_COLETA,
	BUSCA_FOI_CANCELADA,
	PRESCRICAO_CANCELADA,
	BUSCA_ENCERRADA;
}
