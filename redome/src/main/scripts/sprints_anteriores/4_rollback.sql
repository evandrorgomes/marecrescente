DROP TABLE MODRED.metodologia_exame;
DROP TABLE MODRED.metodologia;
DROP TABLE MODRED.arquivo_exame;
DROP TABLE MODRED.LOCUS_EXAME;
DROP TABLE MODRED.exame;
DROP TABLE MODRED.configuracao;
DROP TABLE MODRED.LOCUS CASCADE CONSTRAINTS;

DROP SYNONYM MODRED_APLICACAO.metodologia_exame;
DROP SYNONYM MODRED_APLICACAO.LOCUS_EXAME;
DROP SYNONYM MODRED_APLICACAO.LOCUS;
DROP SYNONYM MODRED_APLICACAO.configuracao;


DROP SYNONYM MODRED_APLICACAO.EXAME;
DROP SYNONYM MODRED_APLICACAO.SQ_EXAM_ID;
DROP SEQUENCE MODRED.SQ_EXAM_ID;

DROP SYNONYM MODRED_APLICACAO.ARQUIVO_EXAME;
DROP SYNONYM MODRED_APLICACAO.SQ_AREX_ID;
DROP SEQUENCE MODRED.SQ_AREX_ID;

DROP SYNONYM MODRED_APLICACAO.VALOR_DNA;
DROP SYNONYM MODRED_APLICACAO.VALOR_NMDP;
DROP SYNONYM MODRED_APLICACAO.VALOR_DNA_NMDP;

DROP TABLE MODRED.VALOR_DNA;
DROP TABLE MODRED.VALOR_NMDP;
DROP TABLE MODRED.VALOR_DNA_NMDP;

DROP TABLE MODRED.CENTRO_AVALIADOR CASCADE CONSTRAINTS;