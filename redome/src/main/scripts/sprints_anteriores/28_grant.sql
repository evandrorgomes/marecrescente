GRANT SELECT ON MODRED.SQ_RELA_ID TO RMODRED_WRITE;
GRANT INSERT, UPDATE ON MODRED.RELATORIO TO RMODRED_WRITE;
GRANT SELECT ON MODRED.RELATORIO TO RMODRED_READ;

GRANT SELECT ON MODRED.SQ_HIBU_ID TO RMODRED_WRITE;
GRANT INSERT ON MODRED.HISTORICO_BUSCA TO RMODRED_WRITE;
GRANT SELECT ON MODRED.HISTORICO_BUSCA TO RMODRED_READ;

--Estoria 2890
GRANT SELECT ON MODRED.TIPO_EXAME TO RMODRED_READ;
GRANT SELECT ON MODRED.TIPO_EXAME_LOCUS TO RMODRED_READ;
GRANT SELECT ON MODRED.STATUS_PEDIDO_EXAME TO RMODRED_READ;
GRANT SELECT ON MODRED.INSTRUCAO_COLETA TO RMODRED_READ;
GRANT INSERT, UPDATE ON MODRED.INSTRUCAO_COLETA TO RMODRED_WRITE;
GRANT SELECT ON MODRED.SQ_INCO_ID TO RMODRED_READ;
--Estotia 2890

GRANT SELECT ON MODRED.SQ_DOAD_ID TO RMODRED_READ;
GRANT SELECT ON MODRED.REGISTRO TO RMODRED_READ;


-- GRANT DA TABELA DE RESERVA_DOADOR_INTERNACIONAL
GRANT SELECT ON MODRED.SQ_REDI_ID TO RMODRED_READ;
GRANT INSERT, UPDATE ON MODRED.RESERVA_DOADOR_INTERNACIONAL TO RMODRED_WRITE;
GRANT SELECT ON MODRED.RESERVA_DOADOR_INTERNACIONAL TO RMODRED_READ;