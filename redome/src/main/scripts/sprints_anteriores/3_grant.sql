GRANT SELECT ON MODRED.CID TO RMODRED_READ;
GRANT SELECT ON MODRED.ESTAGIO_DOENCA TO RMODRED_READ;
GRANT SELECT ON MODRED.MOTIVO TO RMODRED_READ;
GRANT SELECT ON MODRED.DIAGNOSTICO TO RMODRED_READ;
GRANT INSERT, UPDATE ON MODRED.DIAGNOSTICO TO RMODRED_WRITE;
GRANT SELECT ON MODRED.CONDICAO_PACIENTE TO RMODRED_READ;
GRANT SELECT ON MODRED.EVOLUCAO TO RMODRED_READ;
GRANT INSERT, UPDATE ON MODRED.EVOLUCAO TO RMODRED_WRITE;
GRANT SELECT ON MODRED.SQ_EVOL_ID TO RMODRED_WRITE;	