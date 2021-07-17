
interface TipoTarefa {
    id: number;
    parceiro: string;
}

const TiposTarefa = {
    NOTIFICACAO: {
        id: 1,
    } as TipoTarefa,
    TIMEOUT_CONFERENCIA_EXAME: {
        id: 7,
        //parceiro: c
    } as TipoTarefa,
    LIBERAR_ACOMPANHAR_BUSCA: {
        id: 9,
        //parceiro: c
    } as TipoTarefa,
    TIMEOUT: {
        id: 14,
        //parceiro: c
    } as TipoTarefa,
    AVALIAR_PACIENTE: {
        id: 5,
        //parceiro: c
    } as TipoTarefa,
    AVALIAR_EXAME_HLA: {
        id: 6,
        //parceiro: c
    } as TipoTarefa,
    RECEBER_PACIENTE: {
        id: 8,
        //parceiro: c
    } as TipoTarefa,
    ENRIQUECIMENTO_FASE_2: {
        id: 10,
        //parceiro: c
    } as TipoTarefa,
    ENRIQUECIMENTO_FASE_3: {
        id: 11,
        //parceiro: c
    } as TipoTarefa,
    CONTACTAR_FASE_2: {
        id: 12,
        //parceiro: c
    } as TipoTarefa,
    CONTACTAR_FASE_3: {
        id: 13,
        //parceiro: c
    } as TipoTarefa,
    SMS: {
        id: 15,
        //parceiro: c
    } as TipoTarefa,
    CONTATO_HEMOCENTRO: {
        id: 20,
        //parceiro: c
    } as TipoTarefa,
    RESPONDER_PENDENCIA: {
        id: 27,
        //parceiro: c
    } as TipoTarefa,
    NOTIFICACAO_EXAME_CONFERIDO: {
        id: 28,
        //parceiro: c
    } as TipoTarefa,
    PEDIDO_WORKUP: {
        id: 30,
        //parceiro: c
    } as TipoTarefa,
    PEDIDO_COLETA_HEMOCENTRO: {
        id: 29,
        //parceiro: c
    } as TipoTarefa,
    SUGERIR_DATA_WORKUP: {
        id: 32,
        //parceiro: c
    } as TipoTarefa,
    SUGERIR_DATA_COLETA: {
        id: 74,
    } as TipoTarefa,
    INFORMAR_LOGISTICA_DOADOR_WORKUP: {
        id: 36,
        //parceiro: c
    } as TipoTarefa,
    AVALIAR_RESULTADO_WORKUP_NACIONAL: {
        id: 33,
        //parceiro: c
    } as TipoTarefa,
    PEDIDO_COLETA: {
        id: 40,
        //parceiro: c
    } as TipoTarefa,
    AVALIAR_PEDIDO_COLETA: {
        id: 37,
        //parceiro: c
    } as TipoTarefa,
    ANALISE_MEDICA_DOADOR_COLETA: {
        id: 38,
        //parceiro: c
    } as TipoTarefa,
    PEDIDO_LOGISTICA_DOADOR_COLETA: {
        id: 41,
        //parceiro: c
    } as TipoTarefa,
    CANCELAR_AGENDAMENTO_PEDIDO_COLETA_DOADOR: {
        id: 46,
        //parceiro: c
    } as TipoTarefa,
    CANCELAR_AGENDAMENTO_PEDIDO_COLETA_CENTRO_COLETA: {
        id: 47,
        //parceiro: c
    } as TipoTarefa,
    AVALIAR_PRESCRICAO: {
        id: 49,
        //parceiro: c
    } as TipoTarefa,
    INFORMAR_RECEBIMENTO_COLETA: {
        id: 50,
        //parceiro: c
    } as TipoTarefa,
    CADASTRAR_PRESCRICAO: {
        id: 52,
        parceiro: "CentroTransplante"
    } as TipoTarefa,
    ENCONTRAR_CENTRO_TRANSPLANTE: {
        id: 53,
        //parceiro: c
    } as TipoTarefa,
    PEDIDO_TRANSPORTE: {
        id: 55,
        //parceiro: c
    } as TipoTarefa,
    PEDIDO_LOGISTICA_MATERIAL_COLETA: {
        id: 42,
        //parceiro: c
    } as TipoTarefa,
    PEDIDO_TRANSPORTE_RETIRADA: {
        id: 56,
        //parceiro: c
    } as TipoTarefa,
    PEDIDO_TRANSPORTE_ENTREGA: {
        id: 57,
        //parceiro: c
    } as TipoTarefa,
    PEDIDO_TRANSPORTE_RETIRADA_ENTREGA: {
        id: 58,
        parceiro: "Transportadora"
    } as TipoTarefa,
    RECEBER_COLETA_PARA_EXAME: {
        id: 60,
        parceiro: "Laboratorio"
    } as TipoTarefa,
    CADASTRAR_RESULTADO_CT: {
        id: 61,
        parceiro: "Laboratorio"
    } as TipoTarefa,
    CADASTRAR_RESULTADO_EXAME: {
        id: 62,
        parceiro: "Laboratorio"
    } as TipoTarefa,
    CADASTRAR_RESULTADO_EXAME_INTERNACIONAL: {
        id: 63
    } as TipoTarefa,
    CADASTRAR_RESULTADO_EXAME_CT_INTERNACIONAL: {
        id: 65,
    } as TipoTarefa,
    CADASTRAR_RESULTADO_EXAME_IDM_INTERNACIONAL: {
        id: 68
    } as TipoTarefa,
    CADASTRAR_RESULTADO_WORKUP_INTERNACIONAL: {
        id: 71
    } as TipoTarefa,
    AGENDAR_COLETA_INTERNACIONAL: {
        id: 72,
        //parceiro: c
    } as TipoTarefa,
    PEDIDO_WORKUP_INTERNACIONAL: {
        id: 73
    } as TipoTarefa,
    PEDIDO_LOGISTICA_CORDAO_NACIONAL: {
        id: 75
    } as TipoTarefa,
    AUTORIZACAO_PACIENTE: {
        id: 78
    } as TipoTarefa,
    RETIRADA_MATERIAL_INTERNACIONAL: {
        id: 81
    } as TipoTarefa,
    ENTREGA_MATERIAL_INTERNACIONAL: {
        id: 82
    } as TipoTarefa,
    INFORMAR_LOGISTICA_MATERIAL_COLETA_INTERNACIONAL: {
        id: 84,
    } as TipoTarefa,
    CADASTRAR_FORMULARIO_DOADOR: {
        id: 112,
    } as TipoTarefa,
    DEFINIR_CENTRO_COLETA: {
        id: 114,
    } as TipoTarefa,
    INFORMAR_PLANO_WORKUP_NACIONAL: {
        id: 115,
    } as TipoTarefa,
    INFORMAR_PLANO_WORKUP_INTERNACIONAL: {
        id: 118,
    } as TipoTarefa,
    CONFIRMAR_PLANO_WORKUP: {
        id: 117,
    } as TipoTarefa,
    INFORMAR_RESULTADO_WORKUP_INTERNACIONAL: {
        id: 220
    } as TipoTarefa,
    INFORMAR_RESULTADO_WORKUP_NACIONAL: {
        id: 35,
    }as TipoTarefa,
    AVALIAR_RESULTADO_WORKUP_INTERNACIONAL: {
        id: 222,
    } as TipoTarefa,
    INFORMAR_AUTORIZACAO_PACIENTE: {
        id: 223,
    } as TipoTarefa,
    AGENDAR_COLETA_NACIONAL: {
        id: 224,
    } as TipoTarefa,
    INFORMAR_EXAME_ADICIONAL_WORKUP_NACIONAL: {
        id: 225,
    } as TipoTarefa,
    INFORMAR_EXAME_ADICIONAL_WORKUP_INTERNACIONAL: {
        id: 226,
    } as TipoTarefa,
    INFORMAR_LOGISTICA_MATERIAL_COLETA_NACIONAL: {
      id: 227,
    } as TipoTarefa,
    INFORMAR_LOGISTICA_DOADOR_COLETA: {
      id: 228,
    } as TipoTarefa
};
type TiposTarefa = (typeof TiposTarefa)[keyof typeof TiposTarefa];
export { TiposTarefa };
