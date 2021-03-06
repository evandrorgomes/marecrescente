-- HISTÓRIA DE ENRIQUECIMENTO
DELETE FROM MODRED.RECURSO WHERE RECU_ID = 26;
DELETE FROM MODRED.PERMISSAO WHERE RECU_ID = 26 AND PERF_ID IN (6, 7, 8);

--REMOVENDO RECURSO DE ADIÇÃO DE ENDERECO (06/12/2017) Queiroz
delete from MODRED.PERMISSAO where RECU_ID = 27 and perf_id = 6;
delete from MODRED.RECURSO where RECU_ID = 27;

delete from "MODRED"."PERMISSAO"  where recu_id = 32 and perf_id = 6;

DROP TABLE MODRED.RESSALVA_DOADOR CASCADE CONSTRAINTS;
DROP SEQUENCE MODRED.SQ_REDO_ID;

delete from MODRED.PERMISSAO where RECU_ID = 27 and perf_id = 6;

DELETE from MODRED.PERMISSAO where RECU_ID = 35 and perf_id IN (7, 8);
DELETE from MODRED.RECURSO where RECU_ID = 35;

/* formulário */
DROP SYNONYM MODRED_APLICACAO.SQ_FORM_ID;
DROP SYNONYM MODRED_APLICACAO.SQ_FODO_ID;
DROP SYNONYM MODRED_APLICACAO.SQ_REFD_ID;

DROP SYNONYM MODRED_APLICACAO.RESPOSTA_FORMULARIO_DOADOR;
DROP SYNONYM MODRED_APLICACAO.FORMULARIO_DOADOR;
DROP SYNONYM MODRED_APLICACAO.FORMULARIO;
DROP SYNONYM MODRED_APLICACAO.TIPO_FORMULARIO;

DROP SEQUENCE MODRED.SQ_FORM_ID;
DROP SEQUENCE MODRED.SQ_FODO_ID;
DROP SEQUENCE MODRED.SQ_REFD_ID;

DROP TABLE MODRED.RESPOSTA_FORMULARIO_DOADOR;
DROP TABLE MODRED.FORMULARIO_DOADOR;
DROP TABLE MODRED.FORMULARIO;
DROP TABLE MODRED.TIPO_FORMULARIO;
/* fim formulário */DELETE from MODRED.RECURSO where RECU_ID = 35;

DELETE FROM "MODRED"."MOTIVO_STATUS_DOADOR_RECURSO" WHERE MOSD_ID = 1 AND RECU_ID = 26;
DELETE FROM "MODRED"."MOTIVO_STATUS_DOADOR_RECURSO" WHERE MOSD_ID = 2 AND RECU_ID = 26;
DELETE FROM "MODRED"."MOTIVO_STATUS_DOADOR_RECURSO" WHERE MOSD_ID = 3 AND RECU_ID = 26;
COMMIT;

-- RENOMEANDO COLUNA DE TEMPO DE INATIVIDADE PARA DATA DE RETORNO
ALTER TABLE MODRED.DOADOR ADD (DOAD_NR_TEMPO_INATIVO NUMBER);
COMMENT ON COLUMN MODRED.DOADOR.DOAD_NR_TEMPO_INATIVO IS 'Tempo em que o paciente ficará inativo';
UPDATE MODRED.DOADOR SET DOAD_NR_TEMPO_INATIVO = ROUND(DOAD_DT_RETORNO_INATIVIDADE - SYSDATE, 0);
ALTER TABLE MODRED.DOADOR DROP COLUMN DOAD_DT_RETORNO_INATIVIDADE;

ALTER TABLE MODRED.DOADOR_AUD ADD (DOAD_NR_TEMPO_INATIVO NUMBER);
COMMENT ON COLUMN MODRED.DOADOR_AUD.DOAD_NR_TEMPO_INATIVO IS 'Tempo em que o paciente ficará inativo';
UPDATE MODRED.DOADOR_AUD SET DOAD_NR_TEMPO_INATIVO = ROUND(DOAD_DT_RETORNO_INATIVIDADE - SYSDATE, 0);
ALTER TABLE MODRED.DOADOR_AUD DROP COLUMN DOAD_DT_RETORNO_INATIVIDADE;
COMMIT;

delete from MODRED.MOTIVO_STATUS_DOADOR_RECURSO where mosd_id = 3 and recu_id = 26;

UPDATE MODRED.RECURSO SET RECU_TX_SIGLA = 'INATIVAR_DOADOR', 
RECU_TX_DESCRICAO = 'Permite inativar o doador para futuras buscas de forma permanente ou temporária.' 
WHERE RECU_ID = 26;