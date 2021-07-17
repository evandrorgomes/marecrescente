GRANT SELECT ON MODRED.SISTEMA TO RMODRED_READ;

GRANT SELECT ON "MODRED"."TIPO_BUSCA_CHECKLIST" TO RMODRED_READ;
GRANT INSERT, UPDATE ON "MODRED"."TIPO_BUSCA_CHECKLIST" TO RMODRED_WRITE;

GRANT SELECT ON "MODRED"."BUSCA_CHECKLIST" TO RMODRED_READ;
GRANT INSERT, UPDATE ON "MODRED"."BUSCA_CHECKLIST" TO RMODRED_WRITE;

GRANT SELECT ON MODRED.SQ_BUCH_ID TO RMODRED_WRITE;

GRANT SELECT ON MODRED.SOLICITACAO_REDOMEWEB TO RMODRED_READ;
GRANT INSERT ON MODRED.SOLICITACAO_REDOMEWEB TO RMODRED_WRITE;

GRANT SELECT ON MODRED.SQ_SORE_ID TO RMODRED_WRITE;


GRANT SELECT ON MODRED.ESTABELECIMENTO_IN_REDOME TO RMODRED_READ;
GRANT INSERT, UPDATE ON MODRED.ESTABELECIMENTO_IN_REDOME TO RMODRED_WRITE;

GRANT SELECT ON MODRED.SQ_ESIR_ID TO RMODRED_WRITE;

GRANT SELECT ON MODRED.SQ_LABO_ID TO RMODRED_WRITE;

GRANT INSERT, UPDATE ON MODRED.HEMO_ENTIDADE TO RMODRED_WRITE;
GRANT INSERT, UPDATE ON MODRED.LABORATORIO TO RMODRED_WRITE;
