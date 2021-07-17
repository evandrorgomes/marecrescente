SET MODE ORACLE;
--------------------------------------------------------
--  DDL for Sequence SQ_NOTI_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_NOTI_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_USUA_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_USUA_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Table CATEGORIA_NOTIFICACAO
--------------------------------------------------------

  CREATE TABLE "CATEGORIA_NOTIFICACAO" 
   (	"CANO_ID" NUMBER(1,0), 
	"CANO_TX_DESCRICAO" VARCHAR2(100)
   ) ;

   COMMENT ON COLUMN "CATEGORIA_NOTIFICACAO"."CANO_ID" IS 'Chave primária para categoria de notificação';
   COMMENT ON COLUMN "CATEGORIA_NOTIFICACAO"."CANO_TX_DESCRICAO" IS 'Descrição da categoria de notificação';
   COMMENT ON TABLE "CATEGORIA_NOTIFICACAO"  IS 'Tabela que armazena as categorias de notificações do sistema';
--------------------------------------------------------
--  DDL for Table NOTIFICACAO
--------------------------------------------------------

  CREATE TABLE "NOTIFICACAO" 
   (	"NOTI_ID" NUMBER, 
	"PACI_NR_RMR" NUMBER, 
	"NOTI_TX_DESCRICAO" VARCHAR2(255), 
	"CANO_ID" NUMBER, 
	"NOTI_IN_LIDO" NUMBER(1,0) DEFAULT 0, 
	"NOTI_DT_CRIACAO" TIMESTAMP (6), 
	"USUA_ID" NUMBER, 
	"NOTI_DT_LIDO" TIMESTAMP (6), 
	"NOTI_ID_PARCEIRO" NUMBER
   ) ;

   COMMENT ON COLUMN "NOTIFICACAO"."NOTI_ID" IS 'Chave primária de notificicação';
   COMMENT ON COLUMN "NOTIFICACAO"."PACI_NR_RMR" IS 'RMR do paciente';
   COMMENT ON COLUMN "NOTIFICACAO"."NOTI_TX_DESCRICAO" IS 'Descrição da notificação';
   COMMENT ON COLUMN "NOTIFICACAO"."CANO_ID" IS 'Referência para a tabela de categoria da notificação';
   COMMENT ON COLUMN "NOTIFICACAO"."NOTI_IN_LIDO" IS 'Indica se a notificação já foi lida (1-TRUE) ou não (0-FALSE)';
   COMMENT ON COLUMN "NOTIFICACAO"."NOTI_DT_CRIACAO" IS 'Data de criação da notificação.';
   COMMENT ON COLUMN "NOTIFICACAO"."USUA_ID" IS 'Referencia para a tabela de usuário';
   COMMENT ON COLUMN "NOTIFICACAO"."NOTI_DT_LIDO" IS 'Data em que foi feita a leitura da notificação.';
   COMMENT ON COLUMN "NOTIFICACAO"."NOTI_ID_PARCEIRO" IS 'Referência ao parceiro relacionado com o usuário caso exista.';
   COMMENT ON TABLE "NOTIFICACAO"  IS 'Tabela que armazena as notificações do sistema';

--------------------------------------------------------
--  DDL for Table USUARIO
--------------------------------------------------------

  CREATE TABLE "USUARIO" 
   (	"USUA_ID" NUMBER, 
	"USUA_TX_USERNAME" VARCHAR2(50)
   ) ;

   COMMENT ON COLUMN "USUARIO"."USUA_ID" IS 'Identificador do usuário';
   COMMENT ON COLUMN "USUARIO"."USUA_TX_USERNAME" IS 'Username do usuário';
   COMMENT ON TABLE "USUARIO"  IS 'Usuários do sistema';

  CREATE TABLE "PACIENTE" 
   (	"PACI_NR_RMR" NUMBER, 
	"USUA_ID" NUMBER
   ) ;

   COMMENT ON COLUMN "PACIENTE"."PACI_NR_RMR" IS 'Identificador do Paciente';
   COMMENT ON COLUMN "PACIENTE"."USUA_ID" IS 'Referencia do usuario que cadastrou';
   COMMENT ON TABLE "PACIENTE"  IS 'Pacientes';
