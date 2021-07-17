DROP SYNONYM MODRED_APLICACAO.RASCUNHO;
DROP SYNONYM MODRED_APLICACAO.SQ_RASC_ID;

DROP TABLE RASCUNHO;
DROP SEQUENCE SQ_RASC_ID;

/**** Configuracao Draft ******************/
DELETE FROM MODRED.CONFIGURACAO WHERE CONF_ID = 'tempoSalvarRascunhoEmSegundos';
COMMIT;

/******* Auditoria do exame *****************/
ALTER TABLE MODRED.EXAME 
DROP CONSTRAINT FK_EXAM_USUA;

DROP INDEX MODRED.IN_FK_EXAM_USUA;

ALTER TABLE MODRED.EXAME 
DROP COLUMN USUA_ID;

/******* Auditoria Evolucao *****************/
ALTER TABLE MODRED.EVOLUCAO 
DROP CONSTRAINT FK_EVOL_USUA;

DROP INDEX MODRED.IN_FK_EVOL_USUA;

ALTER TABLE MODRED.EVOLUCAO 
DROP COLUMN USUA_ID;

-------------Auditoria----------------
drop table "MODRED"."ENDERECO_CONTATO_AUD" cascade constraints PURGE;
drop table "MODRED"."CONTATO_TELEFONICO_AUD" cascade constraints PURGE;
drop table "MODRED"."DIAGNOSTICO_AUD" cascade constraints PURGE;
drop table "MODRED"."PACIENTE_AUD" cascade constraints PURGE;
drop table "MODRED"."RESPONSAVEL_AUD" cascade constraints PURGE;
drop table "MODRED"."AUDITORIA" cascade constraints PURGE;
COMMIT;