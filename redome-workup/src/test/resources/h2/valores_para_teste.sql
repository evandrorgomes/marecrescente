insert into PRESCRICAO (PRES_ID, EVOL_ID, FOCE_ID_OPCAO_1, FOCE_ID_OPCAO_2, MEDI_ID, PRES_DT_COLETA_1, PRES_DT_COLETA_2, PRES_DT_INFUSAO_CORDAO,    PRES_DT_RECEBER_CORDAO_1, PRES_DT_RECEBER_CORDAO_2, PRES_DT_RECEBER_CORDAO_3, PRES_DT_RESULTADO_WORKUP_1, PRES_DT_RESULTADO_WORKUP_2,     PRES_IN_ARMAZENA_CORDAO, PRES_IN_FAZER_COLETA, PRES_IN_TIPO, PRES_NR_COD_AREA_URGENTE, PRES_NR_TELEFONE_URGENTE, PRES_TX_CONTATO_RECEBER,     PRES_TX_CONTATO_URGENTE, PRES_VL_QUANT_KG_OPCAO_1, PRES_VL_QUANT_KG_OPCAO_2, PRES_VL_QUANT_TOTAL_OPCAO_1, PRES_VL_QUANT_TOTAL_OPCAO_2, SOLI_ID, CETR_ID)
values (1, 1, 1, null, 1, sysdate, sysdate, null, null, null, null, sysdate, sysdate, null, 0, 0, null, null, null, null, null, null, null, null, 1, 1);
commit;

insert into AVALIACAO_PRESCRICAO (AVPR_ID, AVPR_DT_ATUALIZACAO, AVPR_DT_CRICAO, AVPR_IN_RESULTADO, AVPR_TX_JUSTIFICATIVA_CANCEL,     AVPR_TX_JUSTIFICATIVA_DESCARTE, FOCE_ID, PRES_ID, USUA_ID)
values (1, sysdate, sysdate, null, null, null, null, 1, null);

commit;


insert into PRESCRICAO (PRES_ID, EVOL_ID, FOCE_ID_OPCAO_1, FOCE_ID_OPCAO_2, MEDI_ID, PRES_DT_COLETA_1, PRES_DT_COLETA_2, PRES_DT_INFUSAO_CORDAO,    PRES_DT_RECEBER_CORDAO_1, PRES_DT_RECEBER_CORDAO_2, PRES_DT_RECEBER_CORDAO_3, PRES_DT_RESULTADO_WORKUP_1, PRES_DT_RESULTADO_WORKUP_2,     PRES_IN_ARMAZENA_CORDAO, PRES_IN_FAZER_COLETA, PRES_IN_TIPO, PRES_NR_COD_AREA_URGENTE, PRES_NR_TELEFONE_URGENTE, PRES_TX_CONTATO_RECEBER,     PRES_TX_CONTATO_URGENTE, PRES_VL_QUANT_KG_OPCAO_1, PRES_VL_QUANT_KG_OPCAO_2, PRES_VL_QUANT_TOTAL_OPCAO_1, PRES_VL_QUANT_TOTAL_OPCAO_2, SOLI_ID, CETR_ID)
values (2, 1, 1, 2, 1, sysdate, sysdate, null, null, null, null, sysdate, sysdate, null, 0, 0, null, null, null, null, null, null, null, null, 1, 1);
commit;

insert into AVALIACAO_PRESCRICAO (AVPR_ID, AVPR_DT_ATUALIZACAO, AVPR_DT_CRICAO, AVPR_IN_RESULTADO, AVPR_TX_JUSTIFICATIVA_CANCEL,     AVPR_TX_JUSTIFICATIVA_DESCARTE, FOCE_ID, PRES_ID, USUA_ID)
values (2, sysdate, sysdate, null, null, null, null, 2, null);

commit;


insert into AVALIACAO_PRESCRICAO (AVPR_ID, AVPR_DT_ATUALIZACAO, AVPR_DT_CRICAO, AVPR_IN_RESULTADO, AVPR_TX_JUSTIFICATIVA_CANCEL,     AVPR_TX_JUSTIFICATIVA_DESCARTE, FOCE_ID, PRES_ID, USUA_ID)
values (3, sysdate, sysdate, null, null, null, null, 2, null);

commit;


insert into AVALIACAO_PRESCRICAO (AVPR_ID, AVPR_DT_ATUALIZACAO, AVPR_DT_CRICAO, AVPR_IN_RESULTADO, AVPR_TX_JUSTIFICATIVA_CANCEL,     AVPR_TX_JUSTIFICATIVA_DESCARTE, FOCE_ID, PRES_ID, USUA_ID)
values (5, sysdate, sysdate, 1, null, null, null, 2, null);

commit;



insert into PRESCRICAO (PRES_ID, EVOL_ID, FOCE_ID_OPCAO_1, FOCE_ID_OPCAO_2, MEDI_ID, PRES_DT_COLETA_1, PRES_DT_COLETA_2, PRES_DT_INFUSAO_CORDAO,    PRES_DT_RECEBER_CORDAO_1, PRES_DT_RECEBER_CORDAO_2, PRES_DT_RECEBER_CORDAO_3, PRES_DT_RESULTADO_WORKUP_1, PRES_DT_RESULTADO_WORKUP_2,     PRES_IN_ARMAZENA_CORDAO, PRES_IN_FAZER_COLETA, PRES_IN_TIPO, PRES_NR_COD_AREA_URGENTE, PRES_NR_TELEFONE_URGENTE, PRES_TX_CONTATO_RECEBER,     PRES_TX_CONTATO_URGENTE, PRES_VL_QUANT_KG_OPCAO_1, PRES_VL_QUANT_KG_OPCAO_2, PRES_VL_QUANT_TOTAL_OPCAO_1, PRES_VL_QUANT_TOTAL_OPCAO_2, SOLI_ID, CETR_ID)
values (3, 1, 1, null, 1, sysdate, sysdate, null, null, null, null, sysdate, sysdate, null, 0, 0, null, null, null, null, null, null, null, null, 1, 1);
commit;

insert into AVALIACAO_PRESCRICAO (AVPR_ID, AVPR_DT_ATUALIZACAO, AVPR_DT_CRICAO, AVPR_IN_RESULTADO, AVPR_TX_JUSTIFICATIVA_CANCEL,     AVPR_TX_JUSTIFICATIVA_DESCARTE, FOCE_ID, PRES_ID, USUA_ID)
values (6, sysdate, sysdate, null, null, null, null, 3, null);

commit;


insert into PRESCRICAO (PRES_ID, EVOL_ID, FOCE_ID_OPCAO_1, FOCE_ID_OPCAO_2, MEDI_ID, PRES_DT_COLETA_1, PRES_DT_COLETA_2, PRES_DT_INFUSAO_CORDAO,    PRES_DT_RECEBER_CORDAO_1, PRES_DT_RECEBER_CORDAO_2, PRES_DT_RECEBER_CORDAO_3, PRES_DT_RESULTADO_WORKUP_1, PRES_DT_RESULTADO_WORKUP_2,     PRES_IN_ARMAZENA_CORDAO, PRES_IN_FAZER_COLETA, PRES_IN_TIPO, PRES_NR_COD_AREA_URGENTE, PRES_NR_TELEFONE_URGENTE, PRES_TX_CONTATO_RECEBER,     PRES_TX_CONTATO_URGENTE, PRES_VL_QUANT_KG_OPCAO_1, PRES_VL_QUANT_KG_OPCAO_2, PRES_VL_QUANT_TOTAL_OPCAO_1, PRES_VL_QUANT_TOTAL_OPCAO_2, SOLI_ID, CETR_ID)
values (4, 1, 1, 2, 1, sysdate, sysdate, null, null, null, null, sysdate, sysdate, null, 0, 0, null, null, null, null, null, null, null, null, 1, 1);
commit;

insert into AVALIACAO_PRESCRICAO (AVPR_ID, AVPR_DT_ATUALIZACAO, AVPR_DT_CRICAO, AVPR_IN_RESULTADO, AVPR_TX_JUSTIFICATIVA_CANCEL,     AVPR_TX_JUSTIFICATIVA_DESCARTE, FOCE_ID, PRES_ID, USUA_ID)
values (7, sysdate, sysdate, null, null, null, null, 4, null);

commit;



insert into PRESCRICAO (PRES_ID, EVOL_ID, FOCE_ID_OPCAO_1, FOCE_ID_OPCAO_2, MEDI_ID, PRES_DT_COLETA_1, PRES_DT_COLETA_2, PRES_DT_INFUSAO_CORDAO,    PRES_DT_RECEBER_CORDAO_1, PRES_DT_RECEBER_CORDAO_2, PRES_DT_RECEBER_CORDAO_3, PRES_DT_RESULTADO_WORKUP_1, PRES_DT_RESULTADO_WORKUP_2,     PRES_IN_ARMAZENA_CORDAO, PRES_IN_FAZER_COLETA, PRES_IN_TIPO, PRES_NR_COD_AREA_URGENTE, PRES_NR_TELEFONE_URGENTE, PRES_TX_CONTATO_RECEBER,     PRES_TX_CONTATO_URGENTE, PRES_VL_QUANT_KG_OPCAO_1, PRES_VL_QUANT_KG_OPCAO_2, PRES_VL_QUANT_TOTAL_OPCAO_1, PRES_VL_QUANT_TOTAL_OPCAO_2, SOLI_ID, CETR_ID)
values (5, 1, 1, 2, 1, sysdate, sysdate - 10, null, null, null, null, sysdate, sysdate, null, 0, 0, null, null, null, null, null, null, null, null, 1, 1);
commit;

insert into AVALIACAO_PRESCRICAO (AVPR_ID, AVPR_DT_ATUALIZACAO, AVPR_DT_CRICAO, AVPR_IN_RESULTADO, AVPR_TX_JUSTIFICATIVA_CANCEL,     AVPR_TX_JUSTIFICATIVA_DESCARTE, FOCE_ID, PRES_ID, USUA_ID)
values (8, sysdate, sysdate, null, null, null, null, 5, null);

commit;


insert into AVALIACAO_PRESCRICAO (AVPR_ID, AVPR_DT_ATUALIZACAO, AVPR_DT_CRICAO, AVPR_IN_RESULTADO, AVPR_TX_JUSTIFICATIVA_CANCEL,     AVPR_TX_JUSTIFICATIVA_DESCARTE, FOCE_ID, PRES_ID, USUA_ID)
values (9, sysdate, sysdate, null, null, null, null, 5, null);

commit;

insert into AVALIACAO_PRESCRICAO (AVPR_ID, AVPR_DT_ATUALIZACAO, AVPR_DT_CRICAO, AVPR_IN_RESULTADO, AVPR_TX_JUSTIFICATIVA_CANCEL,     AVPR_TX_JUSTIFICATIVA_DESCARTE, FOCE_ID, PRES_ID, USUA_ID)
values (10, sysdate, sysdate, null, null, null, null, 5, null);

commit;



insert into PRESCRICAO (PRES_ID, EVOL_ID, FOCE_ID_OPCAO_1, FOCE_ID_OPCAO_2, MEDI_ID, PRES_DT_COLETA_1, PRES_DT_COLETA_2, PRES_DT_INFUSAO_CORDAO,    PRES_DT_RECEBER_CORDAO_1, PRES_DT_RECEBER_CORDAO_2, PRES_DT_RECEBER_CORDAO_3, PRES_DT_RESULTADO_WORKUP_1, PRES_DT_RESULTADO_WORKUP_2,     PRES_IN_ARMAZENA_CORDAO, PRES_IN_FAZER_COLETA, PRES_IN_TIPO, PRES_NR_COD_AREA_URGENTE, PRES_NR_TELEFONE_URGENTE, PRES_TX_CONTATO_RECEBER,     PRES_TX_CONTATO_URGENTE, PRES_VL_QUANT_KG_OPCAO_1, PRES_VL_QUANT_KG_OPCAO_2, PRES_VL_QUANT_TOTAL_OPCAO_1, PRES_VL_QUANT_TOTAL_OPCAO_2, SOLI_ID, PRES_DT_CRIACAO, CETR_ID)
values (6, 1, 1, 2, 1, sysdate, sysdate - 10, null, null, null, null, sysdate, sysdate, null, 0, 0, null, null, null, null, null, null, null, null, 3, sysdate, 1);
commit;

insert into AVALIACAO_PRESCRICAO (AVPR_ID, AVPR_DT_ATUALIZACAO, AVPR_DT_CRICAO, AVPR_IN_RESULTADO, AVPR_TX_JUSTIFICATIVA_CANCEL,     AVPR_TX_JUSTIFICATIVA_DESCARTE, FOCE_ID, PRES_ID, USUA_ID)
values (11, sysdate, sysdate, 1, null, null, null, 6, 13);

commit;

insert into DISTRIBUICAO_WORKUP (
    DIWO_ID,
    DIWO_DT_ATUALIZACAO,
    DIWO_DT_CRIACAO,
    DIWO_IN_TIPO,
    SOLI_ID,
    USUA_ID_DISTRIBUIU,
    USUA_ID_RECEBEU)
values (
    1,
    sysdate,
    sysdate,
    0,
    3,
    null,
    null);
;



insert into PRESCRICAO (PRES_ID, EVOL_ID, FOCE_ID_OPCAO_1, FOCE_ID_OPCAO_2, MEDI_ID, PRES_DT_COLETA_1, PRES_DT_COLETA_2, PRES_DT_INFUSAO_CORDAO,    PRES_DT_RECEBER_CORDAO_1, PRES_DT_RECEBER_CORDAO_2, PRES_DT_RECEBER_CORDAO_3, PRES_DT_RESULTADO_WORKUP_1, PRES_DT_RESULTADO_WORKUP_2,     PRES_IN_ARMAZENA_CORDAO, PRES_IN_FAZER_COLETA, PRES_IN_TIPO, PRES_NR_COD_AREA_URGENTE, PRES_NR_TELEFONE_URGENTE, PRES_TX_CONTATO_RECEBER,     PRES_TX_CONTATO_URGENTE, PRES_VL_QUANT_KG_OPCAO_1, PRES_VL_QUANT_KG_OPCAO_2, PRES_VL_QUANT_TOTAL_OPCAO_1, PRES_VL_QUANT_TOTAL_OPCAO_2, SOLI_ID, PRES_DT_CRIACAO, CETR_ID)
values (7, 1, 1, 2, 1, sysdate, sysdate - 10, null, null, null, null, sysdate, sysdate, null, 0, 0, null, null, null, null, null, null, null, null, 4, sysdate, 1);
commit;

insert into AVALIACAO_PRESCRICAO (AVPR_ID, AVPR_DT_ATUALIZACAO, AVPR_DT_CRICAO, AVPR_IN_RESULTADO, AVPR_TX_JUSTIFICATIVA_CANCEL, AVPR_TX_JUSTIFICATIVA_DESCARTE, FOCE_ID, PRES_ID, USUA_ID)
values (12, sysdate, sysdate, 1, null, null, null, 7, 13);


insert into DISTRIBUICAO_WORKUP (
    DIWO_ID,
    DIWO_DT_ATUALIZACAO,
    DIWO_DT_CRIACAO,
    DIWO_IN_TIPO,
    SOLI_ID,
    USUA_ID_DISTRIBUIU,
    USUA_ID_RECEBEU)
values (
    2,
    sysdate,
    sysdate,
    0,
    4,
    9,
    9);
;

insert into PRESCRICAO (PRES_ID, EVOL_ID, FOCE_ID_OPCAO_1, FOCE_ID_OPCAO_2, MEDI_ID, PRES_DT_COLETA_1, PRES_DT_COLETA_2, PRES_DT_INFUSAO_CORDAO,    PRES_DT_RECEBER_CORDAO_1, PRES_DT_RECEBER_CORDAO_2, PRES_DT_RECEBER_CORDAO_3, PRES_DT_RESULTADO_WORKUP_1, PRES_DT_RESULTADO_WORKUP_2,     PRES_IN_ARMAZENA_CORDAO, PRES_IN_FAZER_COLETA, PRES_IN_TIPO, PRES_NR_COD_AREA_URGENTE, PRES_NR_TELEFONE_URGENTE, PRES_TX_CONTATO_RECEBER,     PRES_TX_CONTATO_URGENTE, PRES_VL_QUANT_KG_OPCAO_1, PRES_VL_QUANT_KG_OPCAO_2, PRES_VL_QUANT_TOTAL_OPCAO_1, PRES_VL_QUANT_TOTAL_OPCAO_2, SOLI_ID, PRES_DT_CRIACAO, CETR_ID)
values (8, 1, 1, 2, 1, sysdate, sysdate - 10, null, null, null, null, sysdate, sysdate, null, 0, 0, null, null, null, null, null, null, null, null, 5, sysdate, 1);
commit;

insert into DISTRIBUICAO_WORKUP (
    DIWO_ID,
    DIWO_DT_ATUALIZACAO,
    DIWO_DT_CRIACAO,
    DIWO_IN_TIPO,
    SOLI_ID,
    USUA_ID_DISTRIBUIU,
    USUA_ID_RECEBEU)
values (
    3,
    sysdate,
    sysdate,
    0,
    5,
    NULL,
    NULL);
    
insert into DISTRIBUICAO_WORKUP (
    DIWO_ID,
    DIWO_DT_ATUALIZACAO,
    DIWO_DT_CRIACAO,
    DIWO_IN_TIPO,
    SOLI_ID,
    USUA_ID_DISTRIBUIU,
    USUA_ID_RECEBEU)
values (
    4,
    sysdate,
    sysdate,
    1,
    5,
    NULL,
    NULL);


insert into DISTRIBUICAO_WORKUP (
    DIWO_ID,
    DIWO_DT_ATUALIZACAO,
    DIWO_DT_CRIACAO,
    DIWO_IN_TIPO,
    SOLI_ID,
    USUA_ID_DISTRIBUIU,
    USUA_ID_RECEBEU)
values (
    5,
    sysdate,
    sysdate,
    0,
    5,
    NULL,
    NULL);
    
insert into DISTRIBUICAO_WORKUP (
    DIWO_ID,
    DIWO_DT_ATUALIZACAO,
    DIWO_DT_CRIACAO,
    DIWO_IN_TIPO,
    SOLI_ID,
    USUA_ID_DISTRIBUIU,
    USUA_ID_RECEBEU)
values (
    6,
    sysdate,
    sysdate,
    1,
    5,
    NULL,
    NULL);
    
insert into DISTRIBUICAO_WORKUP (
    DIWO_ID,
    DIWO_DT_ATUALIZACAO,
    DIWO_DT_CRIACAO,
    DIWO_IN_TIPO,
    SOLI_ID,
    USUA_ID_DISTRIBUIU,
    USUA_ID_RECEBEU)
values (
    7,
    sysdate,
    sysdate,
    0,
    5,
    NULL,
    NULL);    
    
insert into DISTRIBUICAO_WORKUP (
    DIWO_ID,
    DIWO_DT_ATUALIZACAO,
    DIWO_DT_CRIACAO,
    DIWO_IN_TIPO,
    SOLI_ID,
    USUA_ID_DISTRIBUIU,
    USUA_ID_RECEBEU)
values (
    8,
    sysdate,
    sysdate,
    0,
    5,
    9,
    9);    
    
insert into pedido_workup (pewo_id, pewo_in_tipo, pewo_in_status, SOLI_ID, PEWO_DT_CRIACAO)
values (1, 0, 1, 4, sysdate);

insert into pedido_workup (pewo_id, pewo_in_tipo, pewo_in_status, SOLI_ID, PEWO_DT_CRIACAO)
values (2, 0, 1, 5, sysdate);
     
insert into pedido_workup (pewo_id, pewo_in_tipo, pewo_in_status, SOLI_ID, PEWO_DT_CRIACAO, PEWO_DT_COLETA)
values (3, 0, 1, 5, sysdate, sysdate);

insert into pedido_workup (pewo_id, pewo_in_tipo, pewo_in_status, SOLI_ID, PEWO_DT_CRIACAO, PEWO_DT_EXAME, PEWO_DT_RESULTADO, PEWO_DT_INTERNACAO, PEWO_DT_COLETA)
values (4, 0, 1, 5, sysdate, sysdate, sysdate, sysdate, sysdate);

insert into pedido_workup (pewo_id, pewo_in_tipo, pewo_in_status, SOLI_ID, PEWO_DT_CRIACAO, PEWO_DT_COLETA)
values (5, 0, 1, 5, sysdate, sysdate+5);
    
insert into pedido_workup (pewo_id, pewo_in_tipo, pewo_in_status, SOLI_ID, PEWO_DT_CRIACAO)
values (6, 1, 1, 5, sysdate);
    
insert into pedido_workup (pewo_id, pewo_in_tipo, pewo_in_status, SOLI_ID, PEWO_DT_CRIACAO, PEWO_DT_EXAME, PEWO_DT_RESULTADO, PEWO_DT_COLETA)
values (7, 1, 1, 5, sysdate, sysdate, sysdate, sysdate);

insert into pedido_workup (pewo_id, pewo_in_tipo, pewo_in_status, SOLI_ID, PEWO_DT_CRIACAO)
values (8, 1, 1, 5, sysdate);    

insert into pedido_workup (pewo_id, pewo_in_tipo, pewo_in_status, SOLI_ID, PEWO_DT_CRIACAO)
values (9, 0, 1, 5, sysdate);

insert into pedido_workup (pewo_id, pewo_in_tipo, pewo_in_status, SOLI_ID, PEWO_DT_CRIACAO)
values (10, 0, 1, 5, sysdate);

insert into pedido_workup (pewo_id, pewo_in_tipo, pewo_in_status, SOLI_ID, PEWO_DT_CRIACAO)
values (11, 0, 2, 5, sysdate);

insert into pedido_workup (pewo_id, pewo_in_tipo, pewo_in_status, SOLI_ID, PEWO_DT_CRIACAO)
values (12, 0, 1, 5, sysdate);
    
insert into resultado_workup (rewo_id, rewo_dt_criacao, usua_id, pewo_id, rewo_in_tipo)
values (1, sysdate, 20, 12, 1);

insert into pedido_workup (pewo_id, pewo_in_tipo, pewo_in_status, SOLI_ID, PEWO_DT_CRIACAO)
values (13, 0, 1, 5, sysdate);    
    
insert into pedido_workup (pewo_id, pewo_in_tipo, pewo_in_status, SOLI_ID, PEWO_DT_CRIACAO)
values (14, 0, 1, 5, sysdate);    

insert into pedido_workup (pewo_id, pewo_in_tipo, pewo_in_status, SOLI_ID, PEWO_DT_CRIACAO)
values (15, 0, 1, 5, sysdate);    

insert into pedido_workup (pewo_id, pewo_in_tipo, pewo_in_status, SOLI_ID, PEWO_DT_CRIACAO)
values (16, 0, 1, 5, sysdate);

insert into pedido_logistica (pelo_id, pelo_in_tipo, stpl_id, pewo_id, pelo_dt_criacao, pelo_dt_atualizacao)
values (1, 1, 1, 7, sysdate, sysdate);  

insert into pedido_logistica (pelo_id, pelo_in_tipo, stpl_id, pewo_id, pelo_dt_criacao, pelo_dt_atualizacao)
values (2, 1, 2, 7, sysdate, sysdate);  

insert into pedido_logistica (pelo_id, pelo_in_tipo, stpl_id, pewo_id, pelo_dt_criacao, pelo_dt_atualizacao)
values (3, 1, 1, 7, sysdate, sysdate);

insert into pedido_logistica (pelo_id, pelo_in_tipo, stpl_id, pewo_id, pelo_dt_criacao, pelo_dt_atualizacao)
values (4, 1, 1, 7, sysdate, sysdate);

insert into pedido_logistica (pelo_id, pelo_in_tipo, stpl_id, pewo_id, pelo_dt_criacao, pelo_dt_atualizacao)
values (5, 1, 1, 7, sysdate, sysdate);

insert into arquivo_voucher_logistica (arvl_id, ARVL_TX_COMENTARIO, ARVL_TX_CAMINHO, ARVL_NR_TIPO, PELO_ID)
values (sq_arvl_id.nextval, '', 'passagem.pdf', 2, 5);

insert into arquivo_voucher_logistica (arvl_id, ARVL_TX_COMENTARIO, ARVL_TX_CAMINHO, ARVL_NR_TIPO, PELO_ID)
values (sq_arvl_id.nextval, '', 'hospedagem.pdf', 1, 5);

insert into transporte_terrestre (TRTE_ID, TRTE_DT_DATA, TRTE_TX_ORIGEM, TRTE_TX_DESTINO, TRTE_TX_OBJETO_TRANSPORTE, TRTE_DT_CRIACAO, PELO_ID)
values (sq_TRTE_ID.nextval, sysdate, 'origem', 'destino', 'objeto', sysdate, 5);

insert into pedido_logistica (pelo_id, pelo_in_tipo, stpl_id, pewo_id, pelo_dt_criacao, pelo_dt_atualizacao)
values (6, 1, 1, 7, sysdate, sysdate);



insert into PRESCRICAO (PRES_ID, EVOL_ID, FOCE_ID_OPCAO_1, FOCE_ID_OPCAO_2, MEDI_ID, PRES_DT_COLETA_1, PRES_DT_COLETA_2, PRES_DT_INFUSAO_CORDAO,    PRES_DT_RECEBER_CORDAO_1, PRES_DT_RECEBER_CORDAO_2, PRES_DT_RECEBER_CORDAO_3, PRES_DT_RESULTADO_WORKUP_1, PRES_DT_RESULTADO_WORKUP_2,     PRES_IN_ARMAZENA_CORDAO, PRES_IN_FAZER_COLETA, PRES_IN_TIPO, PRES_NR_COD_AREA_URGENTE, PRES_NR_TELEFONE_URGENTE, PRES_TX_CONTATO_RECEBER,     PRES_TX_CONTATO_URGENTE, PRES_VL_QUANT_KG_OPCAO_1, PRES_VL_QUANT_KG_OPCAO_2, PRES_VL_QUANT_TOTAL_OPCAO_1, PRES_VL_QUANT_TOTAL_OPCAO_2, SOLI_ID, CETR_ID)
values (9, 1, 1, null, 1, sysdate, sysdate, null, null, null, null, sysdate, sysdate, null, 0, 0, null, null, null, null, null, null, null, null, 6, 1);
commit;

INSERT INTO arquivo_prescricao (ARPR_ID, ARPR_TX_CAMINHO, PRES_ID,  ARPR_IN_JUSTIFICATIVA, ARPR_IN_AUTORIZACAO_PACIENTE)
SELECT SQ_ARPR_ID.NEXTVAL, 'prescricao.pdf', 9, 0, 1 from dual;

insert into pedido_workup (pewo_id, pewo_in_tipo, pewo_in_status, SOLI_ID, PEWO_DT_CRIACAO)
values (17, 1, 1, 6, sysdate);

insert into resultado_workup (rewo_id, rewo_dt_criacao, usua_id, pewo_id, rewo_in_tipo, REWO_IN_COLETA_INVIAVEL, REWO_IN_DOADOR_INDISPONIVEL, REWO_TX_MOTIVO_COLETA_INVIAVEL)
values (2, sysdate, 20, 17, 1, 0, 0, 'teste');


insert into PRESCRICAO (PRES_ID, EVOL_ID, FOCE_ID_OPCAO_1, FOCE_ID_OPCAO_2, MEDI_ID, PRES_DT_COLETA_1, PRES_DT_COLETA_2, PRES_DT_INFUSAO_CORDAO,    PRES_DT_RECEBER_CORDAO_1, PRES_DT_RECEBER_CORDAO_2, PRES_DT_RECEBER_CORDAO_3, PRES_DT_RESULTADO_WORKUP_1, PRES_DT_RESULTADO_WORKUP_2,     PRES_IN_ARMAZENA_CORDAO, PRES_IN_FAZER_COLETA, PRES_IN_TIPO, PRES_NR_COD_AREA_URGENTE, PRES_NR_TELEFONE_URGENTE, PRES_TX_CONTATO_RECEBER,     PRES_TX_CONTATO_URGENTE, PRES_VL_QUANT_KG_OPCAO_1, PRES_VL_QUANT_KG_OPCAO_2, PRES_VL_QUANT_TOTAL_OPCAO_1, PRES_VL_QUANT_TOTAL_OPCAO_2, SOLI_ID, CETR_ID)
values (10, 1, 1, null, 1, sysdate, sysdate, null, null, null, null, sysdate, sysdate, null, 0, 0, null, null, null, null, null, null, null, null, 7, 1);
commit;

insert into pedido_workup (pewo_id, pewo_in_tipo, pewo_in_status, SOLI_ID, PEWO_DT_CRIACAO)
values (18, 1, 1, 7, sysdate);

insert into resultado_workup (rewo_id, rewo_dt_criacao, usua_id, pewo_id, rewo_in_tipo, REWO_IN_COLETA_INVIAVEL, REWO_IN_DOADOR_INDISPONIVEL, REWO_TX_MOTIVO_COLETA_INVIAVEL)
values (3, sysdate, 20, 18, 1, 0, 0, 'teste');


insert into PRESCRICAO (PRES_ID, EVOL_ID, FOCE_ID_OPCAO_1, FOCE_ID_OPCAO_2, MEDI_ID, PRES_DT_COLETA_1, PRES_DT_COLETA_2, PRES_DT_INFUSAO_CORDAO,    PRES_DT_RECEBER_CORDAO_1, PRES_DT_RECEBER_CORDAO_2, PRES_DT_RECEBER_CORDAO_3, PRES_DT_RESULTADO_WORKUP_1, PRES_DT_RESULTADO_WORKUP_2,     PRES_IN_ARMAZENA_CORDAO, PRES_IN_FAZER_COLETA, PRES_IN_TIPO, PRES_NR_COD_AREA_URGENTE, PRES_NR_TELEFONE_URGENTE, PRES_TX_CONTATO_RECEBER,     PRES_TX_CONTATO_URGENTE, PRES_VL_QUANT_KG_OPCAO_1, PRES_VL_QUANT_KG_OPCAO_2, PRES_VL_QUANT_TOTAL_OPCAO_1, PRES_VL_QUANT_TOTAL_OPCAO_2, SOLI_ID, CETR_ID)
values (11, 1, 1, null, 1, sysdate, sysdate, null, null, null, null, sysdate, sysdate, null, 0, 0, null, null, null, null, null, null, null, null, 8, 1);
commit;

insert into pedido_workup (pewo_id, pewo_in_tipo, pewo_in_status, SOLI_ID, PEWO_DT_CRIACAO)
values (19, 1, 1, 8, sysdate);

insert into resultado_workup (rewo_id, rewo_dt_criacao, usua_id, pewo_id, rewo_in_tipo, REWO_IN_COLETA_INVIAVEL, REWO_IN_DOADOR_INDISPONIVEL, REWO_TX_MOTIVO_COLETA_INVIAVEL, FOCE_ID)
values (4, sysdate, 20, 19, 1, 0, 0, 'teste', 1);

insert into avaliacao_resultado_workup (avrw_id, avrw_dt_criacao, avrw_dt_atualizacao, usua_id_responsavel, avrw_in_prosseguir, avrw_tx_justificativa, rewo_id)
select sq_avrw_id.NEXTVAL, SYSDATE, SYSDATE, 20, 1, 'teste', 4 from dual;

insert into PRESCRICAO (PRES_ID, EVOL_ID, FOCE_ID_OPCAO_1, FOCE_ID_OPCAO_2, MEDI_ID, PRES_DT_COLETA_1, PRES_DT_COLETA_2, PRES_DT_INFUSAO_CORDAO,    PRES_DT_RECEBER_CORDAO_1, PRES_DT_RECEBER_CORDAO_2, PRES_DT_RECEBER_CORDAO_3, PRES_DT_RESULTADO_WORKUP_1, PRES_DT_RESULTADO_WORKUP_2,     PRES_IN_ARMAZENA_CORDAO, PRES_IN_FAZER_COLETA, PRES_IN_TIPO, PRES_NR_COD_AREA_URGENTE, PRES_NR_TELEFONE_URGENTE, PRES_TX_CONTATO_RECEBER,     PRES_TX_CONTATO_URGENTE, PRES_VL_QUANT_KG_OPCAO_1, PRES_VL_QUANT_KG_OPCAO_2, PRES_VL_QUANT_TOTAL_OPCAO_1, PRES_VL_QUANT_TOTAL_OPCAO_2, SOLI_ID, CETR_ID)
values (12, 1, 1, null, 1, sysdate, sysdate, null, null, null, null, sysdate, sysdate, null, 0, 0, null, null, null, null, null, null, null, null, 9, 1);
commit;

insert into pedido_workup (pewo_id, pewo_in_tipo, pewo_in_status, SOLI_ID, PEWO_DT_CRIACAO)
values (20, 1, 1, 9, sysdate);

insert into resultado_workup (rewo_id, rewo_dt_criacao, usua_id, pewo_id, rewo_in_tipo, REWO_IN_COLETA_INVIAVEL, REWO_IN_DOADOR_INDISPONIVEL, REWO_TX_MOTIVO_COLETA_INVIAVEL)
values (5, sysdate, 20, 20, 1, 1, 0, 'teste');


insert into PRESCRICAO (PRES_ID, EVOL_ID, FOCE_ID_OPCAO_1, FOCE_ID_OPCAO_2, MEDI_ID, PRES_DT_COLETA_1, PRES_DT_COLETA_2, PRES_DT_INFUSAO_CORDAO,    PRES_DT_RECEBER_CORDAO_1, PRES_DT_RECEBER_CORDAO_2, PRES_DT_RECEBER_CORDAO_3, PRES_DT_RESULTADO_WORKUP_1, PRES_DT_RESULTADO_WORKUP_2,     PRES_IN_ARMAZENA_CORDAO, PRES_IN_FAZER_COLETA, PRES_IN_TIPO, PRES_NR_COD_AREA_URGENTE, PRES_NR_TELEFONE_URGENTE, PRES_TX_CONTATO_RECEBER,     PRES_TX_CONTATO_URGENTE, PRES_VL_QUANT_KG_OPCAO_1, PRES_VL_QUANT_KG_OPCAO_2, PRES_VL_QUANT_TOTAL_OPCAO_1, PRES_VL_QUANT_TOTAL_OPCAO_2, SOLI_ID, CETR_ID)
values (13, 1, 1, null, 1, sysdate, sysdate, null, null, null, null, sysdate, sysdate, null, 0, 0, null, null, null, null, null, null, null, null, 10, 1);
commit;

INSERT INTO arquivo_prescricao (ARPR_ID, ARPR_TX_CAMINHO, PRES_ID,  ARPR_IN_JUSTIFICATIVA, ARPR_IN_AUTORIZACAO_PACIENTE)
SELECT SQ_ARPR_ID.NEXTVAL, 'prescricao.pdf', 13, 0, 1 from dual;

insert into pedido_workup (pewo_id, pewo_in_tipo, pewo_in_status, SOLI_ID, PEWO_DT_CRIACAO)
values (21, 1, 1, 10, sysdate);

insert into resultado_workup (rewo_id, rewo_dt_criacao, usua_id, pewo_id, rewo_in_tipo, REWO_IN_COLETA_INVIAVEL, REWO_IN_DOADOR_INDISPONIVEL, REWO_TX_MOTIVO_COLETA_INVIAVEL)
values (6, sysdate, 20, 21, 1, 1, 0, 'teste');


insert into PRESCRICAO (PRES_ID, EVOL_ID, FOCE_ID_OPCAO_1, FOCE_ID_OPCAO_2, MEDI_ID, PRES_DT_COLETA_1, PRES_DT_COLETA_2, PRES_DT_INFUSAO_CORDAO,    PRES_DT_RECEBER_CORDAO_1, PRES_DT_RECEBER_CORDAO_2, PRES_DT_RECEBER_CORDAO_3, PRES_DT_RESULTADO_WORKUP_1, PRES_DT_RESULTADO_WORKUP_2,     PRES_IN_ARMAZENA_CORDAO, PRES_IN_FAZER_COLETA, PRES_IN_TIPO, PRES_NR_COD_AREA_URGENTE, PRES_NR_TELEFONE_URGENTE, PRES_TX_CONTATO_RECEBER,     PRES_TX_CONTATO_URGENTE, PRES_VL_QUANT_KG_OPCAO_1, PRES_VL_QUANT_KG_OPCAO_2, PRES_VL_QUANT_TOTAL_OPCAO_1, PRES_VL_QUANT_TOTAL_OPCAO_2, SOLI_ID, CETR_ID)
values (14, 1, 1, null, 1, sysdate, sysdate, null, null, null, null, sysdate, sysdate, null, 0, 0, null, null, null, null, null, null, null, null, 11, 1);
commit;

insert into pedido_workup (pewo_id, pewo_in_tipo, pewo_in_status, SOLI_ID, PEWO_DT_CRIACAO)
values (22, 1, 2, 11, sysdate);

insert into resultado_workup (rewo_id, rewo_dt_criacao, usua_id, pewo_id, rewo_in_tipo, REWO_IN_COLETA_INVIAVEL, REWO_IN_DOADOR_INDISPONIVEL, REWO_TX_MOTIVO_COLETA_INVIAVEL)
values (7, sysdate, 20, 22, 1, 0, 0, 'teste');



insert into PRESCRICAO (PRES_ID, EVOL_ID, FOCE_ID_OPCAO_1, FOCE_ID_OPCAO_2, MEDI_ID, PRES_DT_COLETA_1, PRES_DT_COLETA_2, PRES_DT_INFUSAO_CORDAO, PRES_DT_RECEBER_CORDAO_1, PRES_DT_RECEBER_CORDAO_2, PRES_DT_RECEBER_CORDAO_3, PRES_DT_RESULTADO_WORKUP_1, PRES_DT_RESULTADO_WORKUP_2,     PRES_IN_ARMAZENA_CORDAO, PRES_IN_FAZER_COLETA, PRES_IN_TIPO, PRES_NR_COD_AREA_URGENTE, PRES_NR_TELEFONE_URGENTE, PRES_TX_CONTATO_RECEBER,     PRES_TX_CONTATO_URGENTE, PRES_VL_QUANT_KG_OPCAO_1, PRES_VL_QUANT_KG_OPCAO_2, PRES_VL_QUANT_TOTAL_OPCAO_1, PRES_VL_QUANT_TOTAL_OPCAO_2, SOLI_ID, CETR_ID)
values (15, 1, 1, null, 1, sysdate, sysdate, null, null, null, null, sysdate, sysdate, null, 0, 0, null, null, null, null, null, null, null, null, 12, 1);
commit;

insert into pedido_workup (pewo_id, pewo_in_tipo, pewo_in_status, SOLI_ID, PEWO_DT_CRIACAO)
values (23, 1, 1, 12, sysdate);

insert into resultado_workup (rewo_id, rewo_dt_criacao, usua_id, pewo_id, rewo_in_tipo, REWO_IN_COLETA_INVIAVEL, REWO_IN_DOADOR_INDISPONIVEL)
values (8, sysdate, 20, 23, 1, 0, 0);


insert into pedido_workup (pewo_id, pewo_in_tipo, pewo_in_status, SOLI_ID, PEWO_DT_CRIACAO)
values (24, 1, 2, 12, sysdate);

insert into resultado_workup (rewo_id, rewo_dt_criacao, usua_id, pewo_id, rewo_in_tipo, REWO_IN_COLETA_INVIAVEL, REWO_IN_DOADOR_INDISPONIVEL)
values (9, sysdate, 20, 24, 1, 0, 0);

insert into pedido_workup (pewo_id, pewo_in_tipo, pewo_in_status, SOLI_ID, PEWO_DT_CRIACAO)
values (25, 0, 1, 5, sysdate);

--######################################################################################################

insert into PRESCRICAO (PRES_ID, EVOL_ID, FOCE_ID_OPCAO_1, FOCE_ID_OPCAO_2, MEDI_ID, PRES_DT_COLETA_1, PRES_DT_COLETA_2, PRES_DT_INFUSAO_CORDAO, PRES_DT_RECEBER_CORDAO_1, PRES_DT_RECEBER_CORDAO_2, PRES_DT_RECEBER_CORDAO_3, PRES_DT_RESULTADO_WORKUP_1, PRES_DT_RESULTADO_WORKUP_2,     PRES_IN_ARMAZENA_CORDAO, PRES_IN_FAZER_COLETA, PRES_IN_TIPO, PRES_NR_COD_AREA_URGENTE, PRES_NR_TELEFONE_URGENTE, PRES_TX_CONTATO_RECEBER,     PRES_TX_CONTATO_URGENTE, PRES_VL_QUANT_KG_OPCAO_1, PRES_VL_QUANT_KG_OPCAO_2, PRES_VL_QUANT_TOTAL_OPCAO_1, PRES_VL_QUANT_TOTAL_OPCAO_2, SOLI_ID, CETR_ID)
values (20, 1, 1, null, 1, sysdate, sysdate, null, null, null, null, sysdate, sysdate, null, 0, 0, null, null, null, null, null, null, null, null, 15, 1);
commit;

INSERT INTO arquivo_prescricao (ARPR_ID, ARPR_TX_CAMINHO, PRES_ID,  ARPR_IN_JUSTIFICATIVA, ARPR_IN_AUTORIZACAO_PACIENTE)
SELECT SQ_ARPR_ID.NEXTVAL, 'prescricao.pdf', 20, 0, 1 from dual;

insert into pedido_workup (pewo_id, pewo_in_tipo, pewo_in_status, SOLI_ID, PEWO_DT_CRIACAO)
values (30, 1, 1, 15, sysdate);

insert into resultado_workup (rewo_id, rewo_dt_criacao, usua_id, pewo_id, rewo_in_tipo, REWO_IN_COLETA_INVIAVEL, REWO_IN_DOADOR_INDISPONIVEL)
values (10, sysdate, 20, 30, 0, 0, 0);


insert into PRESCRICAO (PRES_ID, EVOL_ID, FOCE_ID_OPCAO_1, FOCE_ID_OPCAO_2, MEDI_ID, PRES_DT_COLETA_1, PRES_DT_COLETA_2, PRES_DT_INFUSAO_CORDAO, PRES_DT_RECEBER_CORDAO_1, PRES_DT_RECEBER_CORDAO_2, PRES_DT_RECEBER_CORDAO_3, PRES_DT_RESULTADO_WORKUP_1, PRES_DT_RESULTADO_WORKUP_2,     PRES_IN_ARMAZENA_CORDAO, PRES_IN_FAZER_COLETA, PRES_IN_TIPO, PRES_NR_COD_AREA_URGENTE, PRES_NR_TELEFONE_URGENTE, PRES_TX_CONTATO_RECEBER,     PRES_TX_CONTATO_URGENTE, PRES_VL_QUANT_KG_OPCAO_1, PRES_VL_QUANT_KG_OPCAO_2, PRES_VL_QUANT_TOTAL_OPCAO_1, PRES_VL_QUANT_TOTAL_OPCAO_2, SOLI_ID, CETR_ID)
values (21, 1, 1, null, 1, sysdate, sysdate, null, null, null, null, sysdate, sysdate, null, 0, 0, null, null, null, null, null, null, null, null, 16, 1);
commit;

insert into pedido_workup (pewo_id, pewo_in_tipo, pewo_in_status, SOLI_ID, PEWO_DT_CRIACAO)
values (31, 1, 1, 16, sysdate);

insert into resultado_workup (rewo_id, rewo_dt_criacao, usua_id, pewo_id, rewo_in_tipo, REWO_IN_COLETA_INVIAVEL, REWO_IN_DOADOR_INDISPONIVEL)
values (11, sysdate, 20, 31, 0, 0, 0);


insert into PRESCRICAO (PRES_ID, EVOL_ID, FOCE_ID_OPCAO_1, FOCE_ID_OPCAO_2, MEDI_ID, PRES_DT_COLETA_1, PRES_DT_COLETA_2, PRES_DT_INFUSAO_CORDAO, PRES_DT_RECEBER_CORDAO_1, PRES_DT_RECEBER_CORDAO_2, PRES_DT_RECEBER_CORDAO_3, PRES_DT_RESULTADO_WORKUP_1, PRES_DT_RESULTADO_WORKUP_2,     PRES_IN_ARMAZENA_CORDAO, PRES_IN_FAZER_COLETA, PRES_IN_TIPO, PRES_NR_COD_AREA_URGENTE, PRES_NR_TELEFONE_URGENTE, PRES_TX_CONTATO_RECEBER,     PRES_TX_CONTATO_URGENTE, PRES_VL_QUANT_KG_OPCAO_1, PRES_VL_QUANT_KG_OPCAO_2, PRES_VL_QUANT_TOTAL_OPCAO_1, PRES_VL_QUANT_TOTAL_OPCAO_2, SOLI_ID, CETR_ID)
values (22, 1, 1, null, 1, sysdate, sysdate, null, null, null, null, sysdate, sysdate, null, 0, 0, null, null, null, null, null, null, null, null, 17, 1);
commit;

insert into pedido_workup (pewo_id, pewo_in_tipo, pewo_in_status, SOLI_ID, PEWO_DT_CRIACAO)
values (32, 1, 1, 17, sysdate);

insert into resultado_workup (rewo_id, rewo_dt_criacao, usua_id, pewo_id, rewo_in_tipo, REWO_IN_COLETA_INVIAVEL, REWO_IN_DOADOR_INDISPONIVEL)
values (12, sysdate, 20, 32, 0, 0, 0);


insert into PRESCRICAO (PRES_ID, EVOL_ID, FOCE_ID_OPCAO_1, FOCE_ID_OPCAO_2, MEDI_ID, PRES_DT_COLETA_1, PRES_DT_COLETA_2, PRES_DT_INFUSAO_CORDAO, PRES_DT_RECEBER_CORDAO_1, PRES_DT_RECEBER_CORDAO_2, PRES_DT_RECEBER_CORDAO_3, PRES_DT_RESULTADO_WORKUP_1, PRES_DT_RESULTADO_WORKUP_2,  PRES_IN_ARMAZENA_CORDAO, PRES_IN_FAZER_COLETA, PRES_IN_TIPO, PRES_NR_COD_AREA_URGENTE, PRES_NR_TELEFONE_URGENTE, PRES_TX_CONTATO_RECEBER, PRES_TX_CONTATO_URGENTE, PRES_VL_QUANT_KG_OPCAO_1, PRES_VL_QUANT_KG_OPCAO_2, PRES_VL_QUANT_TOTAL_OPCAO_1, PRES_VL_QUANT_TOTAL_OPCAO_2, SOLI_ID, CETR_ID)
values (23, 1, 1, null, 1, sysdate, sysdate, null, null, null, null, sysdate, sysdate, null, 0, 0, null, null, null, null, null, null, null, null, 18, 1);
commit;


insert into PRESCRICAO (PRES_ID, EVOL_ID, FOCE_ID_OPCAO_1, FOCE_ID_OPCAO_2, MEDI_ID, PRES_DT_COLETA_1, PRES_DT_COLETA_2, PRES_DT_INFUSAO_CORDAO, PRES_DT_RECEBER_CORDAO_1, PRES_DT_RECEBER_CORDAO_2, PRES_DT_RECEBER_CORDAO_3, PRES_DT_RESULTADO_WORKUP_1, PRES_DT_RESULTADO_WORKUP_2,  PRES_IN_ARMAZENA_CORDAO, PRES_IN_FAZER_COLETA, PRES_IN_TIPO, PRES_NR_COD_AREA_URGENTE, PRES_NR_TELEFONE_URGENTE, PRES_TX_CONTATO_RECEBER, PRES_TX_CONTATO_URGENTE, PRES_VL_QUANT_KG_OPCAO_1, PRES_VL_QUANT_KG_OPCAO_2, PRES_VL_QUANT_TOTAL_OPCAO_1, PRES_VL_QUANT_TOTAL_OPCAO_2, SOLI_ID, CETR_ID)
values (24, 1, 1, null, 1, sysdate, sysdate, null, null, null, null, sysdate, sysdate, null, 0, 0, null, null, null, null, null, null, null, null, 19, 1);
commit;

insert into pedido_workup (pewo_id, pewo_in_tipo, pewo_in_status, SOLI_ID, PEWO_DT_CRIACAO)
values (33, 1, 1, 19, sysdate);

insert into resultado_workup (rewo_id, rewo_dt_criacao, usua_id, pewo_id, rewo_in_tipo, REWO_IN_COLETA_INVIAVEL, REWO_IN_DOADOR_INDISPONIVEL)
values (13, sysdate, 20, 33, 0, 0, 0);

insert into avaliacao_resultado_workup (avrw_id, avrw_dt_criacao, avrw_dt_atualizacao, usua_id_responsavel, avrw_in_prosseguir, avrw_tx_justificativa, rewo_id)
select sq_avrw_id.NEXTVAL, SYSDATE, SYSDATE, 20, 1, null, 13 from dual;


insert into pedido_adicional_workup (PEAW_ID, PEAW_TX_DESCRICAO, PEAW_DT_CRIACAO, PEWO_ID)
values (1, 'Texto Adicional', sysdate, 1);

insert into arquivo_pedido_adicional_workup (APAW_ID, APAW_TX_CAMINHO, APAW_TX_DESCRICAO, PEAW_ID)
values (1, 'PEDIDO_WORKUP/26/PEDIDO_EXAME_ADICIONAL/1595812027099_Exame Adicional 1.txt','Texto da descricao', 1);

insert into arquivo_pedido_adicional_workup (APAW_ID, APAW_TX_CAMINHO, APAW_TX_DESCRICAO, PEAW_ID)
values (2, 'PEDIDO_WORKUP/26/PEDIDO_EXAME_ADICIONAL/1595812027099_Exame Adicional 2.txt','Texto da descricao', 1);

insert into arquivo_pedido_adicional_workup (APAW_ID, APAW_TX_CAMINHO, APAW_TX_DESCRICAO, PEAW_ID)
values (3, 'PEDIDO_WORKUP/26/PEDIDO_EXAME_ADICIONAL/1595812027099_Exame Adicional 3.txt','Texto da descricao', 1);


insert into pedido_workup (pewo_id, pewo_in_tipo, pewo_in_status, SOLI_ID, PEWO_DT_CRIACAO)
values (34, 1, 1, 20, sysdate);

insert into resultado_workup (rewo_id, rewo_dt_criacao, usua_id, pewo_id, rewo_in_tipo, REWO_IN_COLETA_INVIAVEL, REWO_IN_DOADOR_INDISPONIVEL)
values (14, sysdate, 20, 34, 0, 1, 0);

select sq_avrw_id.NEXTVAL from dual;

insert into avaliacao_resultado_workup (avrw_id, avrw_dt_criacao, avrw_dt_atualizacao, usua_id_responsavel, avrw_in_prosseguir, avrw_tx_justificativa, rewo_id)
select 3, SYSDATE, SYSDATE, 20, 1, null, 14 from dual;


insert into pedido_workup (pewo_id, pewo_in_tipo, pewo_in_status, SOLI_ID, PEWO_DT_CRIACAO)
values (35, 1, 1, 21, sysdate);

insert into resultado_workup (rewo_id, rewo_dt_criacao, usua_id, pewo_id, rewo_in_tipo, REWO_IN_COLETA_INVIAVEL, REWO_IN_DOADOR_INDISPONIVEL)
values (15, sysdate, 21, 34, 0, 0, 0);

select sq_avrw_id.NEXTVAL from dual;

insert into avaliacao_resultado_workup (avrw_id, avrw_dt_criacao, avrw_dt_atualizacao, usua_id_responsavel, avrw_in_prosseguir, avrw_tx_justificativa, rewo_id)
select 4, SYSDATE, SYSDATE, 20, 0, null, 15 from dual;

select sq_avrw_id.NEXTVAL from dual;

insert into avaliacao_resultado_workup (avrw_id, avrw_dt_criacao, avrw_dt_atualizacao, usua_id_responsavel, avrw_in_prosseguir, avrw_tx_justificativa, rewo_id)
select 5, SYSDATE, SYSDATE, 20, 1, null, 15 from dual;

insert into pedido_workup (pewo_id, pewo_in_tipo, pewo_in_status, SOLI_ID, PEWO_DT_CRIACAO)
values (36, 1, 1, 22, sysdate);

insert into resultado_workup (rewo_id, rewo_dt_criacao, usua_id, pewo_id, rewo_in_tipo, REWO_IN_COLETA_INVIAVEL, REWO_IN_DOADOR_INDISPONIVEL)
values (16, sysdate, 21, 36, 0, 1, 0);

select sq_avrw_id.NEXTVAL from dual;

insert into avaliacao_resultado_workup (avrw_id, avrw_dt_criacao, avrw_dt_atualizacao, usua_id_responsavel, avrw_in_prosseguir, avrw_tx_justificativa, rewo_id)
select 6, SYSDATE, SYSDATE, 20, 1, null, 16 from dual;

insert into avaliacao_pedido_coleta (AVPC_ID, USUA_ID, AVPC_IN_PEDIDO_PROSSEGUE, AVPC_TX_JUSTIFICATIVA, AVPC_DT_CRIACAO, AVRW_ID)
select sq_AVPC_ID.nextval, 13, 1, null, sysdate, 6 from dual;


insert into pedido_workup (pewo_id, pewo_in_tipo, pewo_in_status, SOLI_ID, PEWO_DT_CRIACAO)
values (37, 1, 1, 22, sysdate);

insert into resultado_workup (rewo_id, rewo_dt_criacao, usua_id, pewo_id, rewo_in_tipo, REWO_IN_COLETA_INVIAVEL, REWO_IN_DOADOR_INDISPONIVEL)
values (17, sysdate, 21, 37, 0, 1, 0);

--id == 7
insert into avaliacao_resultado_workup (avrw_id, avrw_dt_criacao, avrw_dt_atualizacao, usua_id_responsavel, avrw_in_prosseguir, avrw_tx_justificativa, rewo_id)
select sq_avrw_id.NEXTVAL, SYSDATE, SYSDATE, 20, 1, null, 17 from dual;

Insert into PEDIDO_COLETA (PECL_ID,PECL_DT_CRIACAO,PECL_DT_DISPONIBILIDADE_DOADOR,PECL_DT_LIBERACAO_DOADOR,PECL_IN_LOGISTICA_DOADOR,PECL_IN_LOGISTICA_MATERIAL,PECL_DT_ULTIMO_STATUS,STPC_ID,CETR_ID_COLETA,USUA_ID,PEWO_ID,SOLI_ID,PECL_DT_COLETA,MOCC_TX_CODIGO,PECL_IN_COLETA_INTERNACIONAL,PECL_IN_PRODUDO,PECL_DT_INICIO_GCSF,PECL_DT_INTERNACAO,PECL_TX_GCSF_SETOR_ANDAR,PECL_TX_GCSF_PROCURAR_POR,PECL_TX_INTERNACAO_SETOR_ANDAR,PECL_TX_INTERNACAO_PROCURAR_POR,PECL_NR_HR_JEJUM,PECL_TX_INFORMACOES_ADICIONAIS,PECL_IN_JEJUM,PECL_IN_JEJUM_TOTAL) 
values (1,SYSDATE,null,null,null,null,null,2,null,null,null,5,SYSDATE,null,0,1,SYSDATE,null,'Setor 1','José',null,null,44,'teste adicionais',1,1);

Insert into PEDIDO_COLETA (PECL_ID,PECL_DT_CRIACAO,PECL_DT_DISPONIBILIDADE_DOADOR,PECL_DT_LIBERACAO_DOADOR,PECL_IN_LOGISTICA_DOADOR,PECL_IN_LOGISTICA_MATERIAL,PECL_DT_ULTIMO_STATUS,STPC_ID,CETR_ID_COLETA,USUA_ID,PEWO_ID,SOLI_ID,PECL_DT_COLETA,MOCC_TX_CODIGO,PECL_IN_COLETA_INTERNACIONAL,PECL_IN_PRODUDO,PECL_DT_INICIO_GCSF,PECL_DT_INTERNACAO,PECL_TX_GCSF_SETOR_ANDAR,PECL_TX_GCSF_PROCURAR_POR,PECL_TX_INTERNACAO_SETOR_ANDAR,PECL_TX_INTERNACAO_PROCURAR_POR,PECL_NR_HR_JEJUM,PECL_TX_INFORMACOES_ADICIONAIS,PECL_IN_JEJUM,PECL_IN_JEJUM_TOTAL) 
values (2,SYSDATE,null,null,null,null,null,2,null,null,null,8,SYSDATE,null,0,1,SYSDATE,null,'Setor 1','José',null,null,44,'teste adicionais',1,1);

insert into pedido_logistica (pelo_id, pelo_in_tipo, stpl_id, pewo_id, pelo_dt_criacao, pelo_dt_atualizacao, PECL_ID)
values (30, 2, 1, 7, sysdate, sysdate,2);  

insert into PRESCRICAO (PRES_ID, EVOL_ID, FOCE_ID_OPCAO_1, FOCE_ID_OPCAO_2, MEDI_ID, PRES_DT_COLETA_1, PRES_DT_COLETA_2, PRES_DT_INFUSAO_CORDAO,    PRES_DT_RECEBER_CORDAO_1, PRES_DT_RECEBER_CORDAO_2, PRES_DT_RECEBER_CORDAO_3, PRES_DT_RESULTADO_WORKUP_1, PRES_DT_RESULTADO_WORKUP_2,     PRES_IN_ARMAZENA_CORDAO, PRES_IN_FAZER_COLETA, PRES_IN_TIPO, PRES_NR_COD_AREA_URGENTE, PRES_NR_TELEFONE_URGENTE, PRES_TX_CONTATO_RECEBER,     PRES_TX_CONTATO_URGENTE, PRES_VL_QUANT_KG_OPCAO_1, PRES_VL_QUANT_KG_OPCAO_2, PRES_VL_QUANT_TOTAL_OPCAO_1, PRES_VL_QUANT_TOTAL_OPCAO_2, SOLI_ID, PRES_DT_CRIACAO, CETR_ID)
values (25, 1, 1, 2, 1, sysdate, sysdate - 10, null, null, null, null, sysdate, sysdate, null, 0, 0, null, null, null, null, null, null, null, null, 23, sysdate, 1);
commit;

insert into AVALIACAO_PRESCRICAO (AVPR_ID, AVPR_DT_ATUALIZACAO, AVPR_DT_CRICAO, AVPR_IN_RESULTADO, AVPR_TX_JUSTIFICATIVA_CANCEL, AVPR_TX_JUSTIFICATIVA_DESCARTE, FOCE_ID, PRES_ID, USUA_ID)
values (13, sysdate, sysdate, 1, null, null, 2, 25, 13);

insert into pedido_workup (pewo_id, pewo_in_tipo, pewo_in_status, SOLI_ID, PEWO_DT_CRIACAO)
values (38, 0, 1, 23, sysdate);

insert into PRESCRICAO (PRES_ID, EVOL_ID, FOCE_ID_OPCAO_1, FOCE_ID_OPCAO_2, MEDI_ID, PRES_DT_COLETA_1, PRES_DT_COLETA_2, PRES_DT_INFUSAO_CORDAO,    PRES_DT_RECEBER_CORDAO_1, PRES_DT_RECEBER_CORDAO_2, PRES_DT_RECEBER_CORDAO_3, PRES_DT_RESULTADO_WORKUP_1, PRES_DT_RESULTADO_WORKUP_2,     PRES_IN_ARMAZENA_CORDAO, PRES_IN_FAZER_COLETA, PRES_IN_TIPO, PRES_NR_COD_AREA_URGENTE, PRES_NR_TELEFONE_URGENTE, PRES_TX_CONTATO_RECEBER,     PRES_TX_CONTATO_URGENTE, PRES_VL_QUANT_KG_OPCAO_1, PRES_VL_QUANT_KG_OPCAO_2, PRES_VL_QUANT_TOTAL_OPCAO_1, PRES_VL_QUANT_TOTAL_OPCAO_2, SOLI_ID, PRES_DT_CRIACAO, CETR_ID)
values (26, 1, 2, null, 1, sysdate, sysdate - 10, null, null, null, null, sysdate, sysdate, null, 0, 0, null, null, null, null, null, null, null, null, 24, sysdate, 1);
commit;

insert into AVALIACAO_PRESCRICAO (AVPR_ID, AVPR_DT_ATUALIZACAO, AVPR_DT_CRICAO, AVPR_IN_RESULTADO, AVPR_TX_JUSTIFICATIVA_CANCEL, AVPR_TX_JUSTIFICATIVA_DESCARTE, FOCE_ID, PRES_ID, USUA_ID)
values (14, sysdate, sysdate, 1, null, null, null, 26, 13);

insert into pedido_workup (pewo_id, pewo_in_tipo, pewo_in_status, SOLI_ID, PEWO_DT_CRIACAO)
values (39, 0, 1, 24, sysdate);


insert into pedido_logistica (pelo_id, pelo_in_tipo, stpl_id, pelo_dt_criacao, pelo_dt_atualizacao, PECL_ID)
values (31, 3, 1, sysdate, sysdate, 2);  

insert into pedido_logistica (pelo_id, pelo_in_tipo, stpl_id, pelo_dt_criacao, pelo_dt_atualizacao, PECL_ID)
values (32, 2, 1, sysdate, sysdate, 2);  

insert into pedido_logistica (pelo_id, pelo_in_tipo, stpl_id, pelo_dt_criacao, pelo_dt_atualizacao, PECL_ID)
values (33, 4, 1, sysdate, sysdate, 2);  


insert into pedido_logistica (pelo_id, pelo_in_tipo, stpl_id, pelo_dt_criacao, pelo_dt_atualizacao, PECL_ID, PELO_IN_AEREO)
values (34, 3, 1, sysdate, sysdate, 2, 0);

insert into pedido_logistica (pelo_id, pelo_in_tipo, stpl_id, pelo_dt_criacao, pelo_dt_atualizacao, PECL_ID, PELO_IN_AEREO)
values (35, 3, 2, sysdate, sysdate, 2, 0);

insert into pedido_logistica (pelo_id, pelo_in_tipo, stpl_id, pelo_dt_criacao, pelo_dt_atualizacao, PECL_ID, PELO_IN_AEREO)
values (36, 3, 1, sysdate, sysdate, 2, 1);


--###############################################################################################################################

Insert into PEDIDO_COLETA (PECL_ID,PECL_DT_CRIACAO,PECL_DT_DISPONIBILIDADE_DOADOR,PECL_DT_LIBERACAO_DOADOR,PECL_IN_LOGISTICA_DOADOR,PECL_IN_LOGISTICA_MATERIAL,PECL_DT_ULTIMO_STATUS,STPC_ID,CETR_ID_COLETA,USUA_ID,PEWO_ID,SOLI_ID,PECL_DT_COLETA,MOCC_TX_CODIGO,PECL_IN_COLETA_INTERNACIONAL,PECL_IN_PRODUDO,PECL_DT_INICIO_GCSF,PECL_DT_INTERNACAO,PECL_TX_GCSF_SETOR_ANDAR,PECL_TX_GCSF_PROCURAR_POR,PECL_TX_INTERNACAO_SETOR_ANDAR,PECL_TX_INTERNACAO_PROCURAR_POR,PECL_NR_HR_JEJUM,PECL_TX_INFORMACOES_ADICIONAIS,PECL_IN_JEJUM,PECL_IN_JEJUM_TOTAL) 
values (40,SYSDATE,null,null,null,null,null,2,null,null,null,45,SYSDATE,null,0,1,SYSDATE,null,'Setor 1','José',null,null,44,'teste adicionais',1,1);


insert into pedido_workup (pewo_id, pewo_in_tipo, pewo_in_status, SOLI_ID, PEWO_DT_CRIACAO)
values (42, 1, 1, 45, sysdate);




