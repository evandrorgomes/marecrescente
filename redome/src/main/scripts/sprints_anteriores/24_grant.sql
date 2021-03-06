--EST?RIA 5

GRANT SELECT ON MODRED.SQ_PRES_ID TO RMODRED_WRITE;
GRANT SELECT ON MODRED.SQ_ARPR_ID TO RMODRED_WRITE;

GRANT SELECT ON MODRED.ARQUIVO_PRESCRICAO TO RMODRED_READ;
GRANT INSERT, UPDATE ON MODRED.ARQUIVO_PRESCRICAO TO RMODRED_WRITE;

GRANT INSERT, UPDATE ON MODRED.ARQUIVO_PRESCRICAO TO RMODRED_WRITE;

GRANT SELECT ON MODRED.SQ_AVPR_ID TO RMODRED_WRITE;

GRANT SELECT ON MODRED.AVALIACAO_PRESCRICAO TO RMODRED_READ;
GRANT INSERT, UPDATE ON MODRED.AVALIACAO_PRESCRICAO TO RMODRED_WRITE;

-- GRANTS 2698
GRANT SELECT ON MODRED.LOG_EVOLUCAO TO RMODRED_READ;
GRANT INSERT, UPDATE ON MODRED.LOG_EVOLUCAO TO RMODRED_WRITE;
GRANT SELECT ON MODRED.SQ_LOEV_ID TO RMODRED_WRITE;
-- GRANTS

-- RELACIONAMENTO PACIENTE X GENOTIPO
GRANT INSERT, UPDATE, DELETE ON "MODRED"."VALOR_GENOTIPO_BUSCA" TO RMODRED_WRITE;
GRANT INSERT, UPDATE, DELETE ON "MODRED"."VALOR_GENOTIPO_EXPANDIDO" TO RMODRED_WRITE;
GRANT INSERT, UPDATE, DELETE ON "MODRED"."GENOTIPO" TO RMODRED_WRITE;