--Script para armazenar a avaliação de workup do doador
GRANT SELECT ON MODRED.SQ_AVWD_ID TO RMODRED_WRITE;
GRANT SELECT ON "MODRED"."AVALIACAO_WORKUP_DOADOR" TO RMODRED_READ;
GRANT INSERT, UPDATE ON "MODRED"."AVALIACAO_WORKUP_DOADOR" TO RMODRED_WRITE;
--FIM