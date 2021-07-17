GRANT SELECT ON MODRED.SQ_GEDO_ID TO RMODRED_WRITE;
GRANT SELECT ON MODRED.SQ_VGBD_ID TO RMODRED_WRITE;
GRANT SELECT ON MODRED.SQ_VGED_ID TO RMODRED_WRITE;
GRANT SELECT ON MODRED.SQ_GEPA_ID TO RMODRED_WRITE;

GRANT SELECT ON MODRED.GENOTIPO_PACIENTE TO RMODRED_READ;
GRANT INSERT, UPDATE ON MODRED.GENOTIPO_PACIENTE TO RMODRED_WRITE;
GRANT SELECT ON MODRED.VALOR_GENOTIPO_PACIENTE TO RMODRED_READ;
GRANT INSERT, UPDATE ON MODRED.VALOR_GENOTIPO_PACIENTE TO RMODRED_WRITE;
GRANT SELECT ON MODRED.VALOR_GENOTIPO_EXPAND_PACIENTE TO RMODRED_READ;
GRANT INSERT, UPDATE ON MODRED.VALOR_GENOTIPO_EXPAND_PACIENTE TO RMODRED_WRITE;
GRANT SELECT ON MODRED.VALOR_GENOTIPO_BUSCA_PACIENTE TO RMODRED_READ;
GRANT INSERT, UPDATE ON MODRED.VALOR_GENOTIPO_BUSCA_PACIENTE TO RMODRED_WRITE;

GRANT SELECT ON MODRED.GENOTIPO_DOADOR TO RMODRED_READ;
GRANT INSERT, UPDATE ON MODRED.GENOTIPO_DOADOR TO RMODRED_WRITE;

GRANT SELECT ON MODRED.VALOR_GENOTIPO_DOADOR TO RMODRED_READ;
GRANT INSERT, UPDATE ON MODRED.VALOR_GENOTIPO_DOADOR TO RMODRED_WRITE;

GRANT SELECT ON MODRED.VALOR_GENOTIPO_EXPAND_DOADOR TO RMODRED_READ;
GRANT INSERT, UPDATE ON MODRED.VALOR_GENOTIPO_EXPAND_DOADOR TO RMODRED_WRITE;

GRANT SELECT ON MODRED.VALOR_GENOTIPO_BUSCA_DOADOR TO RMODRED_READ;
GRANT INSERT, UPDATE ON MODRED.VALOR_GENOTIPO_BUSCA_DOADOR TO RMODRED_WRITE;

GRANT execute ON MODRED.proc_criar_match_fake TO RMODRED_READ;
commit;

-- GRANTS ENVOLVENDO A TABELA LOCUS_PEDIDO_EXAME
GRANT SELECT ON MODRED.SQ_LOPE_ID TO RMODRED_WRITE;
GRANT INSERT, UPDATE, DELETE ON MODRED.LOCUS_PEDIDO_EXAME TO RMODRED_WRITE;
GRANT SELECT ON MODRED.LOCUS_PEDIDO_EXAME TO RMODRED_READ;

GRANT DELETE ON MODRED.VALOR_GENOTIPO_DOADOR TO RMODRED_WRITE;
GRANT DELETE ON MODRED.GENOTIPO_DOADOR TO RMODRED_WRITE;
GRANT DELETE ON MODRED.VALOR_GENOTIPO_BUSCA_DOADOR TO RMODRED_WRITE;
GRANT DELETE ON MODRED.VALOR_GENOTIPO_EXPAND_DOADOR TO RMODRED_WRITE;
commit;


GRANT SELECT ON MODRED.PEDIDO_EXAME_AUD TO RMODRED_READ;
GRANT INSERT, UPDATE ON MODRED.PEDIDO_EXAME_AUD TO RMODRED_WRITE;

GRANT SELECT ON MODRED.LOCUS_PEDIDO_EXAME_AUD TO RMODRED_READ;
GRANT INSERT, UPDATE ON MODRED.LOCUS_PEDIDO_EXAME_AUD TO RMODRED_WRITE;