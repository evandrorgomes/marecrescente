GRANT SELECT ON MODRED.PERFIL_LOG_EVOLUCAO TO RMODRED_READ;
GRANT INSERT, UPDATE ON MODRED.PERFIL_LOG_EVOLUCAO TO RMODRED_WRITE;

GRANT SELECT ON MODRED.STATUS_PACIENTE TO RMODRED_READ;
GRANT INSERT, UPDATE ON MODRED.STATUS_PACIENTE TO RMODRED_WRITE;


GRANT SELECT ON MODRED.HISTORICO_STATUS_PACIENTE TO RMODRED_READ;
GRANT INSERT, UPDATE ON MODRED.HISTORICO_STATUS_PACIENTE TO RMODRED_WRITE;

GRANT SELECT ON MODRED.SQ_HISP_ID TO RMODRED_WRITE;


GRANT SELECT ON MODRED.AVALIACAO_NOVA_BUSCA TO RMODRED_READ;
GRANT INSERT, UPDATE ON MODRED.AVALIACAO_NOVA_BUSCA TO RMODRED_WRITE;
GRANT SELECT ON MODRED.SQ_AVNB_ID TO RMODRED_WRITE;