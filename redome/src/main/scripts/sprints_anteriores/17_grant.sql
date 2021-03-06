GRANT SELECT ON "MODRED"."PEDIDO_WORKUP" TO RMODRED_READ;
GRANT INSERT, UPDATE ON "MODRED"."PEDIDO_WORKUP" TO RMODRED_WRITE;
GRANT SELECT ON "MODRED"."PRESCRICAO" TO RMODRED_READ;
GRANT INSERT, UPDATE ON "MODRED"."PRESCRICAO" TO RMODRED_WRITE;
GRANT SELECT ON "MODRED"."SQ_PEWO_ID" TO RMODRED_WRITE;
-- HISTORIA 2
GRANT SELECT ON MODRED.HEMO_ENTIDADE TO RMODRED_READ;
GRANT SELECT ON MODRED.SQ_HEEN_ID TO RMODRED_WRITE;
COMMIT;
--Inicio scripts -Queiroz 21/12/2017 12:00
GRANT SELECT ON "MODRED"."DISPONIBILIDADE" TO RMODRED_READ;
GRANT INSERT, UPDATE ON "MODRED"."DISPONIBILIDADE" TO RMODRED_WRITE;
GRANT SELECT ON "MODRED"."INFO_PREVIA" TO RMODRED_READ;
GRANT INSERT, UPDATE ON "MODRED"."INFO_PREVIA" TO RMODRED_WRITE;
GRANT SELECT ON MODRED.SQ_INPR_ID TO RMODRED_WRITE;
GRANT SELECT ON MODRED.SQ_DISP_ID TO RMODRED_WRITE;
GRANT SELECT ON "MODRED"."STATUS_PEDIDO_WORKUP" TO RMODRED_READ;
GRANT SELECT ON "MODRED"."MOTIVO_CANCELAMENTO_WORKUP" TO RMODRED_READ;
COMMIT;
--Fim scripts -Queiroz 21/12/2017 12:00

--Alterando tabela de centro_transplante para ser um centro generico com diversos papeis Queiroz 21/12/2017 14:30
GRANT SELECT ON "MODRED"."FUNCAO_TRANSPLANTE" TO RMODRED_READ;
GRANT SELECT ON "MODRED"."FUNCAO_CENTRO_TRANSPLANTE" TO RMODRED_READ;
--Fim scripts -Queiroz 21/12/2017 14:30