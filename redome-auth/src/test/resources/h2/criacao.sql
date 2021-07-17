SET MODE ORACLE;
--------------------------------------------------------
--  DDL for Table BANCO_SANGUE_CORDAO
--------------------------------------------------------

  CREATE TABLE "BANCO_SANGUE_CORDAO" 
   (	"BASC_ID" NUMBER, 
	"BASC_TX_SIGLA" VARCHAR2(4), 
	"BASC_TX_NOME" VARCHAR2(60), 
	"BASC_TX_ENDERECO" VARCHAR2(1000), 
	"BASC_TX_CONTATO" VARCHAR2(1000), 
	"BASC_NR_ID_BRASILCORD" NUMBER
   ) ;
--------------------------------------------------------
--  DDL for Sequence SQ_BASC_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_BASC_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;   
--------------------------------------------------------
--  DDL for Table CENTRO_TRANSPLANTE
--------------------------------------------------------

  CREATE TABLE "CENTRO_TRANSPLANTE" 
   (	"CETR_ID" NUMBER, 
	"CETR_TX_NOME" VARCHAR2(100), 
	"CETR_TX_CNPJ" VARCHAR2(14), 
	"CETR_TX_CNES" VARCHAR2(10), 
	"CETR_NR_ENTITY_STATUS" NUMBER DEFAULT 1, 
	"LABO_ID" NUMBER
   ) ;
--------------------------------------------------------
--  DDL for Sequence SQ_CETR_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_CETR_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Table CENTRO_TRANSPLANTE_USUARIO
--------------------------------------------------------

  CREATE TABLE "CENTRO_TRANSPLANTE_USUARIO" 
   (	"USUA_ID" NUMBER, 
	"CETR_ID" NUMBER, 
	"CETU_IN_RESPONSAVEL" NUMBER DEFAULT 0, 
	"CETU_ID" NUMBER
   ) ;
--------------------------------------------------------
--  DDL for Sequence SQ_CETU_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_CETU_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Table FUNCAO_TRANSPLANTE
--------------------------------------------------------

  CREATE TABLE "FUNCAO_TRANSPLANTE" 
   (	"FUTR_ID" NUMBER, 
	"FUTR_DESCRICAO" VARCHAR2(50)
   ) ;
--------------------------------------------------------
--  DDL for Table LABORATORIO
--------------------------------------------------------

  CREATE TABLE "LABORATORIO" 
   (	"LABO_ID" NUMBER, 
	"LABO_TX_NOME" VARCHAR2(255), 
	"LABO_IN_FAZ_CT" NUMBER, 
	"LABO_NR_QUANT_EXAME_CT" NUMBER, 
	"LABO_TX_NOME_CONTATO" VARCHAR2(100), 
	"LABO_NR_ID_REDOMEWEB" NUMBER
   ) ;
--------------------------------------------------------
--  DDL for Table PERFIL
--------------------------------------------------------

  CREATE TABLE "PERFIL" 
   (	"PERF_ID" NUMBER, 
	"PERF_TX_DESCRICAO" VARCHAR2(50), 
	"SIST_ID" NUMBER
   ) ;
--------------------------------------------------------
--  DDL for Sequence SQ_PERF_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_PERF_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Table PERMISSAO
--------------------------------------------------------

  CREATE TABLE "PERMISSAO" 
   (	"RECU_ID" NUMBER, 
	"PERF_ID" NUMBER, 
	"PERM_IN_COM_RESTRICAO" NUMBER(1,0) DEFAULT 0
   ) ;
--------------------------------------------------------
--  DDL for Table RECURSO
--------------------------------------------------------

  CREATE TABLE "RECURSO" 
   (	"RECU_ID" NUMBER, 
	"RECU_TX_SIGLA" VARCHAR2(50), 
	"RECU_TX_DESCRICAO" VARCHAR2(200)
   ) ;
--------------------------------------------------------
--  DDL for Table SISTEMA
--------------------------------------------------------

  CREATE TABLE "SISTEMA" 
   (	"SIST_ID" NUMBER, 
	"SIST_TX_NOME" VARCHAR2(100), 
	"SIST_IN_DISPONIVEL_REDOME" NUMBER
   ) ;
--------------------------------------------------------
--  DDL for Table USUARIO
--------------------------------------------------------

  CREATE TABLE "USUARIO" 
   (	"USUA_ID" NUMBER, 
	"USUA_TX_NOME" VARCHAR2(150), 
	"USUA_TX_USERNAME" VARCHAR2(50), 
	"USUA_TX_PASSWORD" VARCHAR2(255), 
	"USUA_IN_ATIVO" NUMBER(1,0) DEFAULT 1, 
	"TRAN_ID" NUMBER, 
	"LABO_ID" NUMBER, 
	"USUA_TX_EMAIL" VARCHAR2(100)
   ) ;
--------------------------------------------------------
--  DDL for Sequence SQ_USUA_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_USUA_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;   
--------------------------------------------------------
--  DDL for Table USUARIO_PERFIL
--------------------------------------------------------

  CREATE TABLE "USUARIO_PERFIL" 
   (	"USUA_ID" NUMBER, 
	"PERF_ID" NUMBER
   ) ;   
--------------------------------------------------------
--  DDL for Table USUARIO_BANCO_SANGUE_CORDAO
--------------------------------------------------------

  CREATE TABLE "USUARIO_BANCO_SANGUE_CORDAO" 
   (	"USUA_ID" NUMBER, 
	"BASC_ID" NUMBER
   ) ;
--------------------------------------------------------
--  DDL for Table FUNCAO_CENTRO_TRANSPLANTE
--------------------------------------------------------

  CREATE TABLE "FUNCAO_CENTRO_TRANSPLANTE" 
   (	"CETR_ID" NUMBER, 
	"FUTR_ID" NUMBER
   ) ;   
   