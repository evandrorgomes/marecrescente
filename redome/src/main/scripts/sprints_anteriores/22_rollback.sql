DROP SYNONYM MODRED_APLICACAO.SQ_MATC_ID;
DROP SYNONYM MODRED_APLICACAO.MATCH;

DROP SYNONYM MODRED_APLICACAO.SQ_EVBU_ID;
DROP SYNONYM MODRED_APLICACAO.EVOLUCAO_BUSCA;

DROP SYNONYM MODRED_APLICACAO.SQ_COMA_ID;
DROP SYNONYM MODRED_APLICACAO.COMENTARIO_MATCH;

DROP SYNONYM MODRED_APLICACAO.SQ_QUMA_ID;
DROP SYNONYM MODRED_APLICACAO.QUALIFICACAO_MATCH;

ALTER TABLE MODRED.EXAME 
DROP COLUMN EXAME_IN_CONFIRMATORIO;


ALTER TABLE MODRED.SOLICITACAO 
DROP CONSTRAINT FK_SOLI_MATC;

ALTER TABLE MODRED.SOLICITACAO 
DROP COLUMN MATC_ID;

drop SEQUENCE MODRED.SQ_MATC_ID;
drop SEQUENCE MODRED.SQ_EVBU_ID;
drop SEQUENCE MODRED.SQ_COMA_ID;
drop SEQUENCE MODRED.SQ_QUMA_ID;

ALTER TABLE MODRED.DOADOR 
DROP CONSTRAINT FK_DOAD_LABO;

ALTER TABLE MODRED.DOADOR 
DROP COLUMN LABO_ID;

drop table "MODRED"."EVOLUCAO_BUSCA" cascade constraints PURGE;
drop table "MODRED"."LABORATORIO" cascade constraints PURGE;
drop table "MODRED"."COMENTARIO_MATCH" cascade constraints PURGE;
drop table "MODRED"."QUALIFICACAO_MATCH" cascade constraints PURGE;
drop table "MODRED"."MATCH" cascade constraints PURGE;


ALTER TABLE MODRED.BUSCA 
DROP COLUMN PACI_IN_ACEITA_MISMATCH;

COMMIT;

DELETE FROM MODRED.PERMISSAO
WHERE RECU_ID = 56
AND PERF_ID = 9;

DELETE FROM MODRED.PERMISSAO
WHERE RECU_ID = 57
AND PERF_ID = 9;

DELETE FROM MODRED.RECURSO 
WHERE RECU_ID = 56;

DELETE FROM MODRED.RECURSO 
WHERE RECU_ID = 57;

COMMIT;





ALTER TABLE MODRED.VALOR_GENOTIPO DROP CONSTRAINT FK_VAGE_GENO;
ALTER TABLE MODRED.VALOR_GENOTIPO DROP CONSTRAINT FK_VAGB_GENO;
ALTER TABLE MODRED.VALOR_GENOTIPO DROP CONSTRAINT FK_VGEE_GENO;


DROP TABLE MODRED.GENOTIPO;

DROP SEQUENCE MODRED.SQ_VAGE_ID;

ALTER TABLE MODRED.VALOR_GENOTIPO
RENAME TO GENOTIPO;

ALTER TABLE MODRED.GENOTIPO 
DROP COLUMN VAGE_NR_TIPO;

ALTER TABLE MODRED.GENOTIPO RENAME COLUMN VAGE_TX_ALELO TO GENO_TX_ALELO;

ALTER TABLE MODRED.GENOTIPO RENAME COLUMN VAGE_NR_POSICAO_ALELO TO GENO_NR_POSICAO_ALELO;

ALTER TABLE MODRED.GENOTIPO 
RENAME CONSTRAINT PK_VAGE TO PK_GENO;

ALTER TABLE MODRED.GENOTIPO 
RENAME CONSTRAINT FK_VAGE_LOEX TO FK_GENO_LOEX;

ALTER INDEX IN_PK_VAGE
RENAME TO PK_GENO ;

DROP INDEX IN_FK_VAGE_GENO;


DROP SEQUENCE MODRED.SQ_VAGB_ID;
DROP TABLE MODRED.VALOR_GENOTIPO_BUSCA;

DROP SEQUENCE MODRED.SQ_VGEE_ID;
DROP TABLE MODRED.VALOR_GENOTIPO_EXPANDIDO;


DROP SYNONYM "MODRED_APLICACAO"."SQ_VAGE_ID";
DROP SYNONYM "MODRED_APLICACAO"."VALOR_GENOTIPO";

DROP SYNONYM "MODRED_APLICACAO"."SQ_GENO_ID" ;
DROP SYNONYM "MODRED_APLICACAO"."GENOTIPO";

DROP SYNONYM "MODRED_APLICACAO"."SQ_VAGB_ID";
DROP SYNONYM "MODRED_APLICACAO"."VALOR_GENOTIPO_BUSCA";

DROP SYNONYM "MODRED_APLICACAO"."SQ_VGEE_ID";
DROP SYNONYM "MODRED_APLICACAO"."VALOR_GENOTIPO_EXPANDIDO";


ALTER TABLE MODRED.DOADOR DROP CONSTRAINT FK_DOAD_GENO;
ALTER TABLE MODRED.DOADOR 
DROP COLUMN GENO_ID;


ALTER TABLE MODRED.PACIENTE DROP CONSTRAINT FK_PACI_GENO;
ALTER TABLE MODRED.PACIENTE 
DROP COLUMN GENO_ID;

DROP INDEX IN_FK_PACI_GENO;

DELETE FROM MODRED.PERMISSAO
WHERE RECU_ID = 60
AND PERF_ID = 5;

DELETE FROM MODRED.RECURSO
WHERE RECU_ID = 60;

COMMIT;