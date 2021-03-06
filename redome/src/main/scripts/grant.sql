GRANT SELECT ON MODRED.TIPO_AMOSTRA TO RMODRED_READ;
GRANT SELECT ON MODRED.TIPO_AMOSTRA_PRESCRICAO TO RMODRED_READ;
GRANT INSERT, UPDATE ON MODRED.TIPO_AMOSTRA_PRESCRICAO TO RMODRED_WRITE;
GRANT SELECT ON MODRED.SQ_TIAP_ID TO RMODRED_READ;

GRANT SELECT ON MODRED.TIPO_TRANSPLANTE_EVOLUCAO TO RMODRED_READ;
GRANT INSERT, UPDATE ON MODRED.TIPO_TRANSPLANTE_EVOLUCAO TO RMODRED_WRITE;
GRANT SELECT ON MODRED.TIPO_TRANSPLANTE_EVOLUCAO TO RMODRED_READ;

--#######################################################################################################
GRANT SELECT ON MODRED.PESQUISA_WMDA TO RMODRED_READ;
GRANT INSERT, UPDATE ON MODRED.PESQUISA_WMDA TO RMODRED_WRITE;
GRANT SELECT ON MODRED.SQ_PEWM_ID TO RMODRED_READ;
GRANT INSERT, UPDATE ON MODRED.SQ_PEWM_ID TO RMODRED_WRITE;

GRANT SELECT ON MODRED.PESQUISA_WMDA_DOADOR TO RMODRED_READ;
GRANT INSERT, UPDATE ON MODRED.PESQUISA_WMDA_DOADOR TO RMODRED_WRITE;
GRANT SELECT ON MODRED.SQ_PEWD_ID TO RMODRED_READ;
GRANT INSERT, UPDATE ON MODRED.SQ_PEWD_ID TO RMODRED_WRITE;

GRANT SELECT ON MODRED.REGISTRO_ASSOCIADO TO RMODRED_READ;
GRANT INSERT, UPDATE ON MODRED.REGISTRO_ASSOCIADO TO RMODRED_WRITE;
GRANT SELECT ON MODRED.SQ_REAS_ID TO RMODRED_READ;
GRANT INSERT, UPDATE ON MODRED.SQ_REAS_ID TO RMODRED_WRITE;

--#######################################################################################################
--################ SPRINT 63

GRANT SELECT ON MODRED.FASE_WORKUP TO RMODRED_READ;
GRANT SELECT ON MODRED.MOTIVO_CANCELAMENTO_WORKUP TO RMODRED_READ;




