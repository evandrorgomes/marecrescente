SET MODE ORACLE;

--------------------------------------------------------
--  DDL for Sequence SQ_AUDI_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_AUDI_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Table AUDITORIA
--------------------------------------------------------

  CREATE TABLE "AUDITORIA" 
   (	"AUDI_ID" NUMBER, 
	"AUDI_DT_DATA" TIMESTAMP (6), 
	"AUDI_TX_USUARIO" VARCHAR2(150)
   ) ;
--------------------------------------------------------
--  DDL for Table CONFIGURACAO
--------------------------------------------------------

  CREATE TABLE "CONFIGURACAO" 
   (	"CONF_ID" VARCHAR2(100) NOT NULL ENABLE, 
	"CONF_TX_VALOR" VARCHAR2(255),
        "CONF_TX_DESCRICAO" VARCHAR2(255)
   ) ;
   
--------------------------------------------------------
--  DDL for Sequence SQ_TRAN_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_TRAN_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;

--------------------------------------------------------
--  DDL for Table TRANSPORTADORA
--------------------------------------------------------

  CREATE TABLE "TRANSPORTADORA" 
   (	"TRAN_ID" NUMBER, 
	"TRAN_NOME" VARCHAR2(55), 
	"TRAN_ATIVO" NUMBER DEFAULT 1
   ) ;

--------------------------------------------------------
--  DDL for Sequence SQ_COUR_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_COUR_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Table COURIER
--------------------------------------------------------

  CREATE TABLE "COURIER" 
   (	"COUR_ID" NUMBER, 
	"COUR_TX_NOME" VARCHAR2(40), 
	"COUR_TX_CPF" VARCHAR2(11), 
	"COUR_TX_RG" VARCHAR2(13), 
	"TRAN_ID" NUMBER, 
	"COUR_IN_ATIVO" NUMBER DEFAULT 1
   ) ;

--------------------------------------------------------
--  DDL for Table PAIS
--------------------------------------------------------

  CREATE TABLE "PAIS" 
   (	"PAIS_ID" NUMBER, 
	"PAIS_TX_NOME" VARCHAR2(100),
	"PAIS_TX_CD_NACIONAL_REDOMEWEB" VARCHAR2(3)
   ) ;
--------------------------------------------------------
--  DDL for Table UF
--------------------------------------------------------

  CREATE TABLE "UF" 
   (	"UF_SIGLA" VARCHAR2(2), 
	"UF_TX_NOME" VARCHAR2(80)
   ) ;
--------------------------------------------------------
--  DDL for Table MUNICIPIO
--------------------------------------------------------

CREATE TABLE "MUNICIPIO"
(
  "MUNI_ID" NUMBER NOT NULL,
  "UF_SIGLA" VARCHAR2(2) NOT NULL,
  "MUNI_TX_NOME" VARCHAR2(72) NOT NULL,
  "MUNI_TX_CODIGO_IBGE" VARCHAR2(7),
  "MUNI_TX_CODIGO_DNE" VARCHAR2(8)
);   
   

--------------------------------------------------------
--  DDL for Sequence SQ_ENCO_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_ENCO_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Table ENDERECO_CONTATO
--------------------------------------------------------

  CREATE TABLE "ENDERECO_CONTATO" 
   (	"ENCO_ID" NUMBER, 
	"ENCO_ID_PAIS" NUMBER, 
	"ENCO_CEP" VARCHAR2(9), 
	"ENCO_TX_TIPO_LOGRADOURO" VARCHAR2(100), 
	"ENCO_TX_NOME" VARCHAR2(255), 
	"ENCO_TX_COMPLEMENTO" VARCHAR2(50), 
	"ENCO_TX_BAIRRO" VARCHAR2(255),   
	"ENCO_TX_ENDERECO_ESTRANGEIRO" VARCHAR2(255), 
	"ENCO_TX_NUMERO" VARCHAR2(10), 
	"ENCO_IN_EXCLUIDO" NUMBER DEFAULT 0, 
	"ENCO_IN_PRINCIPAL" NUMBER(1,0) DEFAULT 0, 
	"ENCO_IN_CORRESPONDENCIA" NUMBER(1,0) DEFAULT 0, 
	"ENCO_FL_EXCLUIDO" NUMBER, 
	"TRAN_ID" NUMBER,
	"MUNI_ID" NUMBER	
   ) ;   
--------------------------------------------------------
--  DDL for Table ENDERECO_CONTATO_AUD
--------------------------------------------------------

  CREATE TABLE "ENDERECO_CONTATO_AUD" 
   (	"ENCO_ID" NUMBER, 
	"AUDI_ID" NUMBER, 
	"AUDI_TX_TIPO" NUMBER(3,0), 
	"ENCO_TX_BAIRRO" VARCHAR2(255), 
	"ENCO_CEP" VARCHAR2(255), 
	"ENCO_TX_COMPLEMENTO" VARCHAR2(255), 
	"ENCO_TX_ENDERECO_ESTRANGEIRO" VARCHAR2(255), 
	"ENCO_TX_NOME" VARCHAR2(255), 
	"ENCO_TX_NUMERO" VARCHAR2(10), 
	"ENCO_TX_TIPO_LOGRADOURO" VARCHAR2(255), 
	"ENCO_ID_PAIS" NUMBER, 
	"ENCO_IN_EXCLUIDO" NUMBER, 
	"ENCO_IN_PRINCIPAL" NUMBER(1,0) DEFAULT 0, 
	"ENCO_IN_CORRESPONDENCIA" NUMBER(1,0) DEFAULT 0, 
	"ENCO_FL_EXCLUIDO" NUMBER, 
	"TRAN_ID" NUMBER,
	"MUNI_ID" NUMBER
   ) ;   
--------------------------------------------------------
--  DDL for Sequence SQ_EMCO_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_EMCO_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Table EMAIL_CONTATO
--------------------------------------------------------

  CREATE TABLE "EMAIL_CONTATO" 
   (	"EMCO_ID" NUMBER, 
	"EMCO_TX_EMAIL" VARCHAR2(100), 
	"EMCO_IN_EXCLUIDO" NUMBER DEFAULT 0, 
	"EMCO_IN_PRINCIPAL" NUMBER(1,0) DEFAULT 0, 
	"TRAN_ID" NUMBER, 
	"COUR_ID" NUMBER
   ) ;
--------------------------------------------------------
--  DDL for Table EMAIL_CONTATO_AUD
--------------------------------------------------------

  CREATE TABLE "EMAIL_CONTATO_AUD" 
   (	"EMCO_ID" NUMBER, 
	"AUDI_ID" NUMBER, 
	"AUDI_TX_TIPO" NUMBER(3,0), 
	"EMCO_TX_EMAIL" VARCHAR2(100), 
	"EMCO_IN_EXCLUIDO" NUMBER, 
	"EMCO_IN_PRINCIPAL" NUMBER(1,0), 
	"TRAN_ID" NUMBER, 
	"COUR_ID" NUMBER
   ) ;
   
--------------------------------------------------------
--  DDL for Sequence SQ_COTE_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_COTE_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Table CONTATO_TELEFONICO
--------------------------------------------------------

  CREATE TABLE "CONTATO_TELEFONICO" 
   (	"COTE_ID" NUMBER, 
	"COTE_TX_NOME" VARCHAR2(100), 
	"COTE_NR_COD_INTER" NUMBER(5,0), 
	"COTE_NR_COD_AREA" NUMBER(5,0), 
	"COTE_TX_COMPLEMENTO" VARCHAR2(20), 
	"COTE_IN_TIPO" NUMBER(1,0), 
	"COTE_NR_NUMERO" NUMBER(10,0), 
	"COTE_IN_PRINCIPAL" NUMBER(1,0) DEFAULT 0, 
	"COTE_IN_EXCLUIDO" NUMBER DEFAULT 0,
	"COTE_FL_EXCLUIDO" NUMBER, 
	"COUR_ID" NUMBER, 
	"TRAN_ID" NUMBER
   ) ;   
--------------------------------------------------------
--  DDL for Table CONTATO_TELEFONICO_AUD
--------------------------------------------------------

  CREATE TABLE "CONTATO_TELEFONICO_AUD" 
   (	"COTE_ID" NUMBER, 
	"AUDI_ID" NUMBER, 
	"AUDI_TX_TIPO" NUMBER(3,0), 
	"COTE_NR_COD_AREA" NUMBER(10,0), 
	"COTE_NR_COD_INTER" NUMBER(10,0), 
	"COTE_TX_COMPLEMENTO" VARCHAR2(255), 
	"COTE_TX_NOME" VARCHAR2(255), 
	"COTE_NR_NUMERO" NUMBER, 
	"COTE_IN_PRINCIPAL" NUMBER(1,0), 
	"COTE_IN_TIPO" NUMBER(1,0), 
	"COTE_IN_EXCLUIDO" NUMBER, 
	"COTE_FL_EXCLUIDO" NUMBER, 
	"COUR_ID" NUMBER, 
	"TRAN_ID" NUMBER
   ) ;
   

--------------------------------------------------------
--  DDL for Sequence SQ_PETR_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_PETR_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;   
   
--------------------------------------------------------
--  DDL for Table PEDIDO_TRANSPORTE
--------------------------------------------------------

  CREATE TABLE "PEDIDO_TRANSPORTE" 
   (	"PETR_ID" NUMBER, 
	"PETR_DT_ATUALIZACAO" TIMESTAMP (6), 
	"USUA_ID_RESPONSAVEL" NUMBER, 
	"PETR_HR_PREVISTA_RETIRADA" TIMESTAMP (6), 
	"TRAN_ID" NUMBER, 
	"STPT_ID" NUMBER DEFAULT 1, 
	"PETR_DADOS_VOO" VARCHAR2(255), 
	"COUR_ID" NUMBER, 
	"PETR_IN_STATUS" NUMBER DEFAULT 0, 
	"PETR_DT_RETIRADA" TIMESTAMP (6), 
	"PETR_DT_ENTREGA" TIMESTAMP (6),
	"SOLI_ID" NUMBER,
	"PELO_ID" NUMBER,
	"FOCE_ID" NUMBER
   ) ;
   
--------------------------------------------------------
--  DDL for Sequence SQ_ARPT_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_ARPT_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Table ARQUIVO_PEDIDO_TRANSPORTE
--------------------------------------------------------

  CREATE TABLE "ARQUIVO_PEDIDO_TRANSPORTE" 
   (	"ARPT_ID" NUMBER, 
	"ARPT_TX_CAMINHO" VARCHAR2(255), 
	"PETR_ID" NUMBER
   ) ;   
   
--------------------------------------------------------
--  DDL for Table STATUS_PEDIDO_TRANSPORTE
--------------------------------------------------------

  CREATE TABLE "STATUS_PEDIDO_TRANSPORTE" 
   (	"STPT_ID" NUMBER, 
	"STPT_TX_DESCRICAO" VARCHAR2(30)
   ) ;   
      
   
   