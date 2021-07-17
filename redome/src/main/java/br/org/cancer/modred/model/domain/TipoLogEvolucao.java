package br.org.cancer.modred.model.domain;

/**
 * Enum para tipos eventos passíveis de gerar log de evolução.
 * 
 * @author Pizão
 *
 */
public enum TipoLogEvolucao {
	INDEFINIDO(null),
	
	PACIENTE_CADASTRADO("paciente.cadastrado.mensagem.log.evolucao"),

	OCORREU_MATCH("criacao.match.mensagem.log.evolucao"),
	
	AVALIACAO_APROVADA_PARA_PACIENTE("avaliacao.aprovada.paciente.mensagem.log.evolucao"),
	AVALIACAO_REPROVADA_PARA_PACIENTE("avaliacao.reprovada.paciente.mensagem.log.evolucao"),

	NOVO_EXAME_CADASTRADO_PARA_PACIENTE("novo.exame.cadastrado.paciente.mensagem.log.evolucao"),
	EXAME_ACEITO_PARA_PACIENTE("exame.aceito.paciente.mensagem.log.evolucao"),
	EXAME_DESCARTADO_PARA_PACIENTE("exame.descartado.paciente.mensagem.log.evolucao"),
	
	NOVA_EVOLUCAO_PARA_PACIENTE("nova.evolucao.cadastrada.paciente.mensagem.log.evolucao"),
	
	GENOTIPO_ATUALIZADO_PARA_PACIENTE("genotipo.atualizado.paciente.mensagem.log.evolucao"),
	
    PEDIDO_ENRIQUECIMENTO_REALIZADO_PARA_DOADOR("criacao.pedidoenriquecimento.mensagem.log.evolucao"),
    
    PEDIDO_CONTATO_FASE_2_REALIZADO_PARA_DOADOR("criacao.contato.fase2.mensagem.log.evolucao"),
    PEDIDO_CONTATO_FASE_2_CANCELADO_PARA_DOADOR("cancelamento.contato.fase2.mensagem.log.evolucao"),
    
    PEDIDO_EXAME_FASE_2_REALIZADO_PARA_DOADOR("criacao.exame.fase2.mensagem.log.evolucao"),
    PEDIDO_EXAME_FASE_2_CANCELADO_PARA_DOADOR("cancelamento.exame.fase2.mensagem.log.evolucao"),
    
    PEDIDO_CONTATO_FASE_3_REALIZADO_PARA_DOADOR("criacao.contato.fase3.mensagem.log.evolucao"),
	PEDIDO_CONTATO_FASE_3_CANCELADO_PARA_DOADOR("cancelamento.contato.fase3.mensagem.log.evolucao"),
    
	PEDIDO_EXAME_FASE_3_REALIZADO_PARA_DOADOR("criacao.exame.fase3.mensagem.log.evolucao"),
    PEDIDO_EXAME_FASE_3_CANCELADO_PARA_DOADOR("cancelamento.exame.fase3.mensagem.log.evolucao"),
    
    DOADOR_DISPONIBILIZADO("disponibilizacao.doador.mensagem.log.evolucao"),
    PRESCRICAO_CRIADA_PARA_MEDULA("criacao.prescricao.mensagem.log.evolucao.medula"),
    PRESCRICAO_CRIADA_PARA_CORDAO("criacao.prescricao.mensagem.log.evolucao.cordao"),
    PRESCRICAO_APROVADA_PARA_MEDULA("avaliacao.prescricao.aprovada.mensagem.log.evolucao.medula"),
    PRESCRICAO_REPROVADA_PARA_MEDULA("avaliacao.prescricao.reprovada.mensagem.log.evolucao.medula"),
    PRESCRICAO_APROVADA_PARA_CORDAO("avaliacao.prescricao.aprovada.mensagem.log.evolucao.cordao"),
    PRESCRICAO_REPROVADA_PARA_CORDAO("avaliacao.prescricao.reprovada.mensagem.log.evolucao.cordao"),
    
    PEDIDO_WORKUP_CRIADO_PARA_DOADOR("criacao.pedidoworkup.mensagem.log.evolucao.doador"),
    
    PEDIDO_WORKUP_CANCELADO_PARA_DOADOR("cancelamento.pedidoworkup.mensagem.log.evolucao"),
    PEDIDO_WORKUP_AGENDADO_PARA_DOADOR("agendamento.pedidoworkup.mensagem.log.evolucao"),
    
    LOGISTICA_DOADOR_WORKUP_CONFIRMADA_PARA_DOADOR("logistica.doador.pedidoworkup.confirmada.mensagem.log.evolucao"),
    
    RESULTADO_PEDIDO_WORKUP_CADASTRADO_PARA_DOADOR("cadastro.resultado.pedidoworkup.mensagem.log.evolucao"),
    
    AVALIACAO_RESULTADO_PEDIDO_WORKUP_APROVADO_PARA_DOADOR("avaliacao.resultado.pedidoworkup.aprovado.mensagem.log.evolucao"),
    AVALIACAO_RESULTADO_PEDIDO_WORKUP_NAO_APROVADO_PARA_DOADOR("avaliacao.resultado.pedidoworkup.nao.aprovado.mensagem.log.evolucao"),
    
    INCLUIR_EXAME_ADICIONAL_WORKUP_PARA_DOADOR("pedido.adicional.pedidoworkup.incluir.exame.mensagem.log.evolucao"),
    INCLUIDO_EXAME_ADICIONAL_WORKUP_PARA_DOADOR("pedido.adicional.pedidoworkup.incluido.exame.mensagem.log.evolucao"),
    
    RESULTADO_PEDIDO_WORKUP_INVIAVEL_APROVADO_PARA_DOADOR("resultado.pedidoworkup.inviavel.aprovado.mensagem.log.evolucao"),
    RESULTADO_PEDIDO_WORKUP_INVIAVEL_NAO_APROVADO_PARA_DOADOR("resultado.pedidoworkup.inviavel.nao.aprovado.mensagem.log.evolucao"),    
    AVALIACAO_PEDIDO_COLETA("avaliacao.resultado.pedidoworkup.inviavel.aprovacao.prosseguir.mensagem.log.evolucao"),

    AGENDAMENTO_COLETA_PARA_DOADOR("agendamento.pedidocoleta.mensagem.log.evolucao"),
    RECEBIMENTO_COLETA_PARA_DOADOR("recebimento.pedidocoleta.mensagem.log.evolucao"),
        
    LOGISTICA_DOADOR_COLETA_CONFIRMADA_PARA_DOADOR("logistica.doador.pedidocoleta.confirmada.mensagem.log.evolucao"),
    LOGISTICA_MATERIAL_COLETA_CONFIRMADA_PARA_DOADOR("logistica.material.pedidocoleta.confirmada.mensagem.log.evolucao"),
    
    PEDIDO_TRANSPORTE_AGENDADO_PARA_DOADOR("agendamento.pedidotrasporte.mensagem.log.evolucao"),
    PEDIDO_TRANSPORTE_MATERIAL_RETIRADO_PARA_DOADOR("pedidotrasporte.material.retirado.mensagem.log.evolucao"),
    PEDIDO_TRANSPORTE_MATERIAL_ENTREGUE_PARA_DOADOR("pedidotrasporte.material.entregue.mensagem.log.evolucao"),
    
    RECEBIMENTO_COLETA_MATERIAL_RECEBIDO_PARA_DOADOR("recebimento.material.recebido.mensagem.log.evolucao"),
    RECEBIMENTO_COLETA_MATERIAL_RECEBIDO_DESCARTADO_PARA_DOADOR("recebimento.material.recebido.descartado.mensagem.log.evolucao"),
    RECEBIMENTO_COLETA_MATERIAL_RECEBIDO_CONGELADO_PARA_DOADOR("recebimento.material.recebido.congelado.mensagem.log.evolucao"),
    RECEBIMENTO_COLETA_MATERIAL_RECEBIDO_INFUNDIDO_PARA_DOADOR("recebimento.material.recebido.infundido.mensagem.log.evolucao"),
    
    CENTRO_TRANSPLANTE_CONFIRMADO_PARA_PACIENTE("centro.transplante.confirmado.mensagem.log.evolucao"),
	CENTRO_TRANSPLANTE_INDEFINIDO_PARA_PACIENTE("centro.transplante.indefinido.mensagem.log.evolucao"),
	PEDIDO_EXAME_FASE_3_REALIZADO_PARA_PACIENTE("criacao.exame.fase3.paciente.mensagem.log.evolucao"),
    PEDIDO_EXAME_FASE_3_CANCELADO_PARA_PACIENTE("cancelamento.exame.fase3.paciente.mensagem.log.evolucao"),
    RESULTADO_EXAME_IDM_PARA_DOADOR("exame.idm.doador.mensagem.log.evolucao"),
	CENTRO_TRANSPLANTE_REDEFINIDO_PARA_PACIENTE("centro.transplante.redefinido.mensagem.log.evolucao"),
	SOLICITADO_PEDIDO_EXAME_CT_PARA_DOADOR_INTERNACIONAL("solicitado_pedido_exame_ct_doador.log.evolucao"),
	SOLICITADO_PEDIDO_EXAME_IDM_PARA_DOADOR_INTERNACIONAL("solicitado_pedido_exame_idm_doador.log.evolucao"),
	SOLICITADO_PEDIDO_EXAME_CT_PARA_CORDAO_INTERNACIONAL("solicitado_pedido_exame_ct_cordao.log.evolucao"),
	PEDIDO_EXAME_CT_CANCELADO_PARA_CORDAO_INTERNACIONAL("cancelamento.pedido.exame.ct.cordao.mensagem.log.evolucao"),
	PEDIDO_EXAME_CT_CANCELADO_PARA_DOADOR_INTERNACIONAL("cancelamento.pedido.exame.ct.doador.mensagem.log.evolucao"),
	PEDIDO_EXAME_IDM_CANCELADO_PARA_DOADOR_INTERNACIONAL("cancelamento.pedido.exame.idm.mensagem.log.evolucao"),
	RESULTADO_EXAME_CT_PARA_DOADOR("exame.ct.doador.mensagem.log.evolucao"),
	RESULTADO_EXAME_PARA_DOADOR("exame.doador.mensagem.log.evolucao"),
	RESULTADO_EXAME_CT_PARA_CORDAO("exame.ct.cordao.mensagem.log.evolucao"),
	APROVADO_PEDIDO_TRANSFERENCIA_CENTRO_AVALIADOR("aprovacao.pedido.transferencia.centroavaliador.log.evolucao"),
	REPROVADO_PEDIDO_TRANSFERENCIA_CENTRO_AVALIADOR("reprovar.pedido.transferencia.centroavaliador.log.evolucao"),
	DIAGNOSTICO_PACIENTE_ALTERADO("diagnostico.paciente.alterado.log.evolucao"),
	NOVA_BUSCA_SOLICITADA_AVALIACAO_REDOME("nova.busca.solicitada.avaliacao.redome.log.evolucao"),

	SOLICITADO_PEDIDO_TRANSFERENCIA_CENTRO_AVALIADOR("log.pedido.transferencia.centroavaliador.solicitado"),
	AVALIACAO_CAMARA_TECNICA_APROVADA("avaliacao.camara.tecnica.aprovacao.realizada"),
	AVALIACAO_CAMARA_TECNICA_CRIADA("avaliacao.camara.tecnica.criada"),
	AVALIACAO_CAMARA_TECNICA_REPROVADA("avaliacao.camara.tecnica.reprovacao.realizada"),
	BUSCA_CHECK_LIST_CONFIRMADA("busca.checklist.vistado"),
	BUSCA_CHECK_LIST_VISTADO("busca.checklist.log.vistado"),
	DIVERGENCIA_LOCUS_EXAME_SOLUCIONADA("divergencia.locus.exame.resolvida"),
	ENVIO_EMAIL_LABORATORIO_EXAME_DIVERGENTE("enviado.email.laboratorio.divergencia"),
	ANALISE_BUSCA_CHECK_LIST_VISTADO("analise.busca.checklist.vistado");
	
	/* SOLICITACAO DE EXAME POR FASE  */
//	SOLICITADO_EXAME_FASE2_PARA_DOADOR_NACIONAL("criacao.solicitacao.fase2.mensagem.log.evolucao"),
//	SOLICITADO_EXAME_FASE3_PARA_DOADOR_NACIONAL("criacao.solicitacao.fase3.mensagem.log.evolucao"),
	
	/* PEDIDO DE CONTATO PARA DOADOR */
//    CONTATO_FINALIZADO_PROSSEGUIR("contato.finalizado.prosseguir.mensagem.log.evolucao"),
//    CONTATO_FINALIZADO_NAO_PROSSEGUIR("contato.finalizado.nao.prosseguir.mensagem.log.evolucao");
	
	private String mensagem;

    /**
     * Define um construtor para o enum indicando a
     * mensagem que será exibida.
     * 
     * @param mensagem token para a mensagem dentro 
     * do arquivo de mensagem internacionalizadas.
     */
    TipoLogEvolucao(String mensagem) {
        this.mensagem = mensagem;
    }

    /**
     * Mensagem que deverá ser exibida quando
     * este tipo de evento for exibido no histórico.
     * 
     * @return mensagem internacionalizada, devidamente parametrizada.
     */
    public String getMensagem() {
        return mensagem;
    }
}
