DROP SYNONYM MODRED_APLICACAO.CATEGORIA_NOTIFICACAO;
DROP SYNONYM MODRED_APLICACAO.NOTIFICACAO;
DROP SYNONYM MODRED_APLICACAO.MOTIVO_DESCARTE;
DROP SYNONYM MODRED_APLICACAO.SQ_NOTI_ID;

ALTER TABLE EXAME 
DROP COLUMN MODE_ID;

ALTER TABLE EXAME 
DROP COLUMN EXAM_NR_STATUS;

DROP TABLE NOTIFICACAO;
DROP TABLE CATEGORIA_NOTIFICACAO;
DROP TABLE MOTIVO_DESCARTE;
DROP SEQUENCE SQ_NOTI_ID;


ALTER TABLE CID_ESTAGIO_DOENCA 
DROP COLUMN CIED_NR_CURABILIDADE;

ALTER TABLE CID_ESTAGIO_DOENCA 
DROP COLUMN CIED_NR_Q_CONSTANTE;

ALTER TABLE CID_ESTAGIO_DOENCA 
DROP COLUMN CIED_NR_URGENCIA ;


ALTER TABLE CID 
DROP COLUMN CID_NR_CURABILIDADE;

ALTER TABLE CID 
DROP COLUMN CID_NR_Q_CONSTANTE;

ALTER TABLE CID 
DROP COLUMN CID_NR_URGENCIA;


DELETE FROM "MODRED"."USUARIO" WHERE USUA_ID = 3;
DELETE FROM  "MODRED"."PERMISSAO" WHERE RECU_ID = 11 AND PERF_ID = 3 AND PERM_IN_COM_RESTRICAO = 0;
DELETE FROM "MODRED"."RECURSO" WHERE RECU_ID = 11;
DELETE FROM "MODRED"."PERFIL" WHERE PERF_ID = 3;
commit;
DROP SYNONYM MODRED_APLICACAO.LOCUS_EXAME_AUD;
DROP SYNONYM MODRED_APLICACAO.EXAME_AUD;
DROP SYNONYM MODRED_APLICACAO.METODOLOGIA_EXAME_AUD;

DROP TABLE MODRED.EXAME_AUD;
DROP TABLE MODRED.LOCUS_EXAME_AUD;
DROP TABLE MODRED.METODOLOGIA_EXAME_AUD;

---JOB---
ALTER TABLE MODRED.VALOR_NMDP 
DROP COLUMN VAMN_DT_ULTIMA_ATUALIZACAO_ARQ;

ALTER TABLE MODRED.VALOR_NMDP 
DROP COLUMN VAMN_NR_QUANTIDADE;

REVOKE GRANT INSERT ON MODRED.VALOR_NMDP FROM RMODRED_WRITE;

REVOKE GRANT INSERT, UPDATE, DELETE ON MODRED.VALOR_DNA_NMDP FROM RMODRED_WRITE;

DROP TABLE MODRED.TEMP_VALOR_NMDP;
DROP TABLE MODRED.SPLIT_VALOR_DNA;
DROP TABLE MODRED.SPLIT_NMDP;
