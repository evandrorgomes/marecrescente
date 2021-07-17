SET MODE ORACLE;

--------------------------------------------------------
--  DDL for Sequence SQ_AUDI_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_AUDI_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_AVPR_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_AVPR_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_ARPR_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_ARPR_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_PRES_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_PRES_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_TIAP_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_TIAP_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_USUA_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_USUA_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_DIWO_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_DIWO_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 61 CACHE 20 ORDER;
--------------------------------------------------------
--  DDL for Sequence SQ_PEWO_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_PEWO_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_PECL_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_PECL_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_ARPW_ID
--------------------------------------------------------
   
   CREATE SEQUENCE  "SQ_ARPW_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 61 CACHE 20 ORDER;
--------------------------------------------------------
--  DDL for Sequence SQ_ARRW_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_ARRW_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_REWO_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_REWO_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_PELO_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_PELO_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_ARVL_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_ARVL_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_TRTE_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_TRTE_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;      

--------------------------------------------------------
--  DDL for Sequence SQ_RECE_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_RECE_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;      

--------------------------------------------------------
--  DDL for Sequence SQ_DECO_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_DECO_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;      
		
--------------------------------------------------------
--  DDL for Sequence SQ_COCE_ID
--------------------------------------------------------

	CREATE SEQUENCE  "SQ_COCE_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;

--------------------------------------------------------
--  DDL for Sequence SQ_FOPO_ID
--------------------------------------------------------

	CREATE SEQUENCE  "SQ_FOPO_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;

--------------------------------------------------------
--  DDL for Table AUDITORIA
--------------------------------------------------------

  CREATE TABLE "AUDITORIA" 
   (	"AUDI_ID" NUMBER, 
	"AUDI_DT_DATA" TIMESTAMP (6), 
	"AUDI_TX_USUARIO" VARCHAR2(150)
   ) ;

   COMMENT ON COLUMN "AUDITORIA"."AUDI_ID" IS 'Identificador da auditoria';
   COMMENT ON COLUMN "AUDITORIA"."AUDI_DT_DATA" IS 'Data em que a ação foi efetuada.';
   COMMENT ON COLUMN "AUDITORIA"."AUDI_TX_USUARIO" IS 'Usuário que efetuou a ação.';
   COMMENT ON TABLE "AUDITORIA"  IS 'Tabela que armazena dados de auditoria no sistema.';

--------------------------------------------------------
--  DDL for Table CONFIGURACAO
--------------------------------------------------------

  CREATE TABLE "CONFIGURACAO" 
   (	"CONF_ID" VARCHAR2(100) NOT NULL ENABLE, 
	"CONF_TX_VALOR" VARCHAR2(255),
        "CONF_TX_DESCRICAO" VARCHAR2(255)
   ) ;

   COMMENT ON COLUMN "CONFIGURACAO"."CONF_ID" IS 'Chave que será consultada o valor da configuração';
   COMMENT ON COLUMN "CONFIGURACAO"."CONF_TX_VALOR" IS 'Configuração a ser utilizada pela aplicação';
   COMMENT ON TABLE "CONFIGURACAO"  IS 'Tabela de configuração do sistema';
--------------------------------------------------------
--  DDL for Table FONTE_CELULAS
--------------------------------------------------------

  CREATE TABLE "FONTE_CELULAS" 
   (	"FOCE_ID" NUMBER(2,0) NOT NULL ENABLE, 
	"FOCE_TX_SIGLA" VARCHAR2(5), 
	"FOCE_TX_DESCRICAO" VARCHAR2(50)
   ) ;

   COMMENT ON COLUMN "FONTE_CELULAS"."FOCE_ID" IS 'Identificador da fonte de celulas.';
   COMMENT ON COLUMN "FONTE_CELULAS"."FOCE_TX_SIGLA" IS 'Sigla da fonte de celulas.';
   COMMENT ON COLUMN "FONTE_CELULAS"."FOCE_TX_DESCRICAO" IS 'Descrição da fonte de celulas.';
   COMMENT ON TABLE "FONTE_CELULAS"  IS 'Tabela responsável por armazenar os tipos de fonte de celulas utilizadas nas coletas.';
--------------------------------------------------------
--  DDL for Table TIPO_AMOSTRA
--------------------------------------------------------

  CREATE TABLE "TIPO_AMOSTRA" 
   (	"TIAM_ID" NUMBER NOT NULL ENABLE, 
	"TIAM_TX_DESCRICAO" VARCHAR2(20)
   );
--------------------------------------------------------
--  DDL for Table TIPO_AMOSTRA_PRESCRICAO
--------------------------------------------------------

  CREATE TABLE "TIPO_AMOSTRA_PRESCRICAO" 
   (	"TIAP_ID" NUMBER NOT NULL ENABLE, 
	"TIAP_NR_ML_AMOSTRA" NUMBER NOT NULL ENABLE, 
	"PRES_ID" NUMBER, 
	"TIAM_ID" NUMBER, 
	"TIAP_TX_OUTRO_TIPO_AMOSTRA" VARCHAR2(100)
   );
--------------------------------------------------------
--  DDL for Table PRESCRICAO
--------------------------------------------------------

  CREATE TABLE "PRESCRICAO" 
   (	"PRES_DT_COLETA_1" DATE, 
	"PRES_DT_COLETA_2" DATE, 
	"PRES_DT_RESULTADO_WORKUP_1" DATE, 
	"PRES_DT_RESULTADO_WORKUP_2" DATE, 
	"SOLI_ID" NUMBER, 
	"CETR_ID" NUMBER,
	"FOCE_ID_OPCAO_1" NUMBER(2,0), 
	"PRES_VL_QUANT_TOTAL_OPCAO_1" NUMBER(6,2), 
	"PRES_ID" NUMBER NOT NULL ENABLE, 
	"FOCE_ID_OPCAO_2" NUMBER(2,0), 
	"PRES_VL_QUANT_KG_OPCAO_1" NUMBER(6,2), 
	"PRES_VL_QUANT_TOTAL_OPCAO_2" NUMBER(6,2), 
	"PRES_VL_QUANT_KG_OPCAO_2" NUMBER(6,2), 
	"EVOL_ID" NUMBER NOT NULL ENABLE, 
	"MEDI_ID" NUMBER, 
	"PRES_DT_INFUSAO_CORDAO" DATE, 
	"PRES_DT_RECEBER_CORDAO_1" DATE, 
	"PRES_DT_RECEBER_CORDAO_2" DATE, 
	"PRES_DT_RECEBER_CORDAO_3" DATE, 
	"PRES_TX_CONTATO_RECEBER" VARCHAR2(30), 
	"PRES_IN_ARMAZENA_CORDAO" NUMBER, 
	"PRES_NR_COD_AREA_URGENTE" NUMBER(2,0), 
	"PRES_NR_TELEFONE_URGENTE" NUMBER, 
	"PRES_IN_TIPO" NUMBER, 
	"PRES_TX_CONTATO_URGENTE" VARCHAR2(30),
        "PRES_IN_FAZER_COLETA" NUMBER,
	"PRES_DT_CRIACAO" TIMESTAMP
   );
--------------------------------------------------------
--  DDL for Table ARQUIVO_PRESCRICAO
--------------------------------------------------------

  CREATE TABLE "ARQUIVO_PRESCRICAO" 
   (	"ARPR_ID" NUMBER NOT NULL ENABLE, 
	"ARPR_TX_CAMINHO" VARCHAR2(263) NOT NULL ENABLE, 
	"PRES_ID" NUMBER NOT NULL ENABLE, 
	"ARPR_IN_JUSTIFICATIVA" NUMBER(1,0) NOT NULL ENABLE, 
	"ARPR_IN_AUTORIZACAO_PACIENTE" NUMBER(1,0) NOT NULL ENABLE
   );
--------------------------------------------------------
--  DDL for Table AVALIACAO_PRESCRICAO
--------------------------------------------------------

  CREATE TABLE "AVALIACAO_PRESCRICAO" 
   (    "AVPR_ID" NUMBER NOT NULL ENABLE, 
	"FOCE_ID" NUMBER, 
	"AVPR_TX_JUSTIFICATIVA_DESCARTE" VARCHAR2(100), 
	"AVPR_TX_JUSTIFICATIVA_CANCEL" VARCHAR2(100), 
	"AVPR_DT_CRICAO" DATE NOT NULL ENABLE, 
	"USUA_ID" NUMBER, 
	"AVPR_DT_ATUALIZACAO" DATE NOT NULL ENABLE, 
	"PRES_ID" NUMBER NOT NULL ENABLE, 
	"AVPR_IN_RESULTADO" NUMBER(1,0)
   );

--------------------------------------------------------
--  DDL for Table DISTRIBUICAO_WORKUP
--------------------------------------------------------
  CREATE TABLE "DISTRIBUICAO_WORKUP" 
  (
    "DIWO_ID" NUMBER NOT NULL ENABLE, 
    "DIWO_DT_CRIACAO" TIMESTAMP NOT NULL ENABLE,
    "DIWO_DT_ATUALIZACAO" TIMESTAMP NOT NULL ENABLE,
    "DIWO_IN_TIPO" NUMBER NOT NULL ENABLE, 
    "USUA_ID_DISTRIBUIU" NUMBER, 
    "USUA_ID_RECEBEU" NUMBER, 
    "SOLI_ID" NUMBER NOT NULL ENABLE 
  );

--------------------------------------------------------
--  DDL for Table DISTRIBUICAO_WORKUP_AUD
--------------------------------------------------------
  CREATE TABLE "DISTRIBUICAO_WORKUP_AUD" 
  (
    "DIWO_ID" NUMBER NOT NULL ENABLE, 
    "AUDI_ID" NUMBER NOT NULL ENABLE,
    "AUDI_TX_TIPO" NUMBER NOT NULL ENABLE,
    "DIWO_DT_CRIACAO" TIMESTAMP NOT NULL ENABLE,
    "DIWO_DT_ATUALIZACAO" TIMESTAMP NOT NULL ENABLE,
    "DIWO_IN_TIPO" NUMBER NOT NULL ENABLE, 
    "USUA_ID_DISTRIBUIU" NUMBER, 
    "USUA_ID_RECEBEU" NUMBER, 
    "SOLI_ID" NUMBER NOT NULL ENABLE 
  );

--------------------------------------------------------
--  DDL for Table PEDIDO_WORKUP
--------------------------------------------------------

  CREATE TABLE "PEDIDO_WORKUP" 
   ("PEWO_ID" NUMBER,
    "PEWO_DT_CRIACAO" TIMESTAMP (6),
    "PEWO_IN_TIPO" NUMBER,
    "PEWO_IN_STATUS" NUMBER,
    "SOLI_ID" NUMBER, 
	"CETR_ID_TRANSP" NUMBER,
	"CETR_ID_COLETA" NUMBER,
	"PEWO_DT_CRIACAO_PLANO" TIMESTAMP (6),	
	"PEWO_DT_EXAME" DATE,
	"PEWO_DT_RESULTADO" DATE,
	"PEWO_DT_INTERNACAO" DATE,    
	"PEWO_DT_COLETA" DATE,
	"PEWO_DT_INFUSAO" DATE,
	"PEWO_DT_CONDICIONAMENTO" DATE,
	"PEWO_IN_CRIOPRESERVACAO" BOOLEAN,
	"PEWO_TX_OBSERVACAO" TEXT,
	"PEWO_DT_EXAME_MEDICO_1" TIMESTAMP (6),
	"PEWO_DT_EXAME_MEDICO_2" TIMESTAMP (6),
	"PEWO_DT_REPETICAO_BTHCG" TIMESTAMP (6),
	"PEWO_TX_SETOR_ANDAR" VARCHAR2(30),
	"PEWO_TX_PROCURAR_POR" VARCHAR2(50),
	"PEWO_IN_JEJUM" NUMBER,
	"PEWO_HR_JEJUM" NUMBER,
	"PEWO_TX_INFORMACOES_ADICIONAIS" TEXT,
	"PEWO_TX_OBS_APROVA_PLANO" TEXT
   ) ;

--------------------------------------------------------
--  DDL for Table PEDIDO_COLETA
--------------------------------------------------------

  CREATE TABLE "PEDIDO_COLETA" 
   ("PECL_ID" NUMBER, 
	"PECL_DT_CRIACAO" TIMESTAMP (6), 
	"PECL_DT_DISPONIBILIDADE_DOADOR" DATE, 
	"PECL_DT_LIBERACAO_DOADOR" DATE, 
	"PECL_IN_LOGISTICA_DOADOR" NUMBER(1,0), 
	"PECL_IN_LOGISTICA_MATERIAL" NUMBER(1,0), 
	"PECL_DT_ULTIMO_STATUS" TIMESTAMP (6), 
	"STPC_ID" NUMBER, 
	"CETR_ID_COLETA" NUMBER, 
	"USUA_ID" NUMBER, 
	"SOLI_ID" NUMBER, 
	"PEWO_ID" NUMBER,
	"PECL_DT_COLETA" TIMESTAMP (6),  
	"MOCC_TX_CODIGO" VARCHAR2(50),
	"PECL_IN_PRODUDO" NUMBER,
	"PECL_IN_COLETA_INTERNACIONAL" NUMBER,
    "PECL_DT_INICIO_GCSF" TIMESTAMP (6),
	"PECL_DT_INTERNACAO" TIMESTAMP (6),
    "PECL_TX_GCSF_SETOR_ANDAR" VARCHAR2(30),
    "PECL_TX_GCSF_PROCURAR_POR" VARCHAR2(50),
    "PECL_TX_INTERNACAO_SETOR_ANDAR" VARCHAR2(30),
    "PECL_TX_INTERNACAO_PROCURAR_POR" VARCHAR2(50),
    "PECL_IN_JEJUM" NUMBER(1,0),
    "PECL_NR_HR_JEJUM" NUMBER,
    "PECL_IN_JEJUM_TOTAL" NUMBER(1,0),
    "PECL_TX_INFORMACOES_ADICIONAIS" CLOB
   ) ;
   
--------------------------------------------------------
--  DDL for Table ARQUIVO_PEDIDO_WORKUP
--------------------------------------------------------   
   
	CREATE TABLE ARQUIVO_PEDIDO_WORKUP 
	(
	  ARPW_ID NUMBER NOT NULL 
	, ARPW_TX_CAMINHO VARCHAR2(255) NOT NULL 
	, PEWO_ID NUMBER NOT NULL 
	);   
	
--------------------------------------------------------
--  DDL for Table RESULTADO_WORKUP
--------------------------------------------------------

  CREATE TABLE "RESULTADO_WORKUP" 
   (	"REWO_ID" NUMBER, 
	"REWO_DT_CRIACAO" TIMESTAMP (6), 
	"USUA_ID" NUMBER, 
	"PEWO_ID" NUMBER,
	"REWO_IN_TIPO" NUMBER,
	"REWO_IN_COLETA_INVIAVEL" NUMBER,
	"REWO_IN_DOADOR_INDISPONIVEL" NUMBER,
	"REWO_TX_MOTIVO_COLETA_INVIAVEL" VARCHAR2(500),
	"FOCE_ID" NUMBER,
	"REWO_DT_COLETA" DATE,
	"REWO_DT_GCSF" DATE,
	"REWO_IN_ADEGUADO_AFERESE" NUMBER,
	"REWO_TX_ACESSO_VENOSO_CENTRAL" VARCHAR2(500),
	"REWO_IN_SANGUE_ANTOLOGO_COLETADO" NUMBER,
	"REWO_TX_MOTIVO_SA_NAO_COLETADO" VARCHAR2(500)
   ) ;	

--------------------------------------------------------
--  DDL for Table ARQUIVO_RESULTADO_WORKUP
--------------------------------------------------------

  CREATE TABLE "ARQUIVO_RESULTADO_WORKUP" 
   (	"ARRW_ID" NUMBER, 
	"ARRW_TX_CAMINHO" VARCHAR2(263), 
	"ARRW_TX_DESCRICAO" VARCHAR2(50),  
	"REWO_ID" NUMBER,
	"PEAW_ID" NUMBER
   ) ;
   
   
--------------------------------------------------------
--  DDL for Table PEDIDO_LOGISTICA
--------------------------------------------------------

  CREATE TABLE "PEDIDO_LOGISTICA" 
   (	"PELO_ID" NUMBER, 
	"PECL_ID" NUMBER,	   
	"PELO_DT_CRIACAO" TIMESTAMP (6), 
	"PELO_TX_OBSERVACAO" VARCHAR2(500), 
	"PELO_DT_ATUALIZACAO" TIMESTAMP (6), 
	"PELO_DT_CHEGADA" DATE,
	"PELO_DT_EMBARQUE" Date,
	"USUA_ID_RESPONSAVEL" NUMBER, 
	"PELO_TX_HAWB" VARCHAR2(200), 
	"PELO_TX_ID_DOADOR_LOCAL" VARCHAR2(200), 
	"PELO_TX_NOME_COURIER" VARCHAR2(250), 
	"PELO_TX_PASSAPORTE_COURIER" VARCHAR2(250), 
	"PELO_TX_LOCAL_RETIRADA" VARCHAR2(500), 
	"PEWO_ID" NUMBER, 
	"PELO_IN_TIPO" NUMBER, 
	"STPL_ID" NUMBER,
	"PELO_TX_JUSTIFICA_NAO_PROSSEGUIMENTO" TEXT,
	"TRAN_ID" NUMBER,
	"PELO_HR_PREVISTA_RETIRADA" TIMESTAMP (6),
	"PELO_IN_AEREO" NUMBER
   );
   
--------------------------------------------------------
--  DDL for Table PEDIDO_LOGISTICA_AUD
--------------------------------------------------------

  CREATE TABLE "PEDIDO_LOGISTICA_AUD" 
   (	"PELO_ID" NUMBER, 
	"AUDI_ID" NUMBER(19,0), 
	"AUDI_TX_TIPO" NUMBER(3,0), 
	"PELO_DT_CRIACAO" TIMESTAMP (6), 
	"PELO_TX_OBSERVACAO" VARCHAR2(500), 
	"PELO_DT_ATUALIZACAO" TIMESTAMP (6), 
	"PELO_DT_CHEGADA" DATE,
	"PELO_DT_EMBARQUE" Date,
	"USUA_ID_RESPONSAVEL" NUMBER, 
	"PELO_TX_HAWB" VARCHAR2(200), 
	"PELO_TX_ID_DOADOR_LOCAL" VARCHAR2(200), 
	"PELO_TX_NOME_COURIER" VARCHAR2(250), 
	"PELO_TX_PASSAPORTE_COURIER" VARCHAR2(250), 
	"PELO_TX_LOCAL_RETIRADA" VARCHAR2(500), 
	"PEWO_ID" NUMBER, 
	"PELO_IN_TIPO" NUMBER, 
	"STPL_ID" NUMBER,
	"PELO_TX_JUSTIFICA_NAO_PROSSEGUIMENTO" TEXT,
	"TRAN_ID" NUMBER,
	"PELO_HR_PREVISTA_RETIRADA" TIMESTAMP (6),
	"PELO_IN_AEREO" NUMBER
   ) ;   
   
--------------------------------------------------------
--  DDL for Table ARQUIVO_VOUCHER_LOGISTICA
--------------------------------------------------------

  CREATE TABLE "ARQUIVO_VOUCHER_LOGISTICA" 
   (	"ARVL_ID" NUMBER, 
	"ARVL_TX_COMENTARIO" VARCHAR2(100), 
	"ARVL_TX_CAMINHO" VARCHAR2(263), 
	"ARVL_NR_TIPO" NUMBER(1,0), 
	"PELO_ID" NUMBER
   ) ;
   
--------------------------------------------------------
--  DDL for Table ARQUIVO_VOUCHER_LOGISTICA_AUD
--------------------------------------------------------

  CREATE TABLE "ARQUIVO_VOUCHER_LOGISTICA_AUD" 
   (	"ARVL_ID" NUMBER(19,0), 
	"AUDI_ID" NUMBER(19,0), 
	"AUDI_TX_TIPO" NUMBER(3,0), 
	"ARVL_TX_CAMINHO" VARCHAR2(255 CHAR), 
	"ARVL_TX_COMENTARIO" VARCHAR2(255 CHAR), 
	"ARVL_NR_TIPO" NUMBER(10,0), 
	"PELO_ID" NUMBER(19,0)
   ) ;      
   
--------------------------------------------------------
--  DDL for Table TRANSPORTE_TERRESTRE
--------------------------------------------------------

  CREATE TABLE "TRANSPORTE_TERRESTRE" 
   (	"TRTE_ID" NUMBER, 
	"TRTE_DT_DATA" TIMESTAMP (6), 
	"TRTE_TX_ORIGEM" VARCHAR2(200), 
	"TRTE_TX_DESTINO" VARCHAR2(200), 
	"TRTE_TX_OBJETO_TRANSPORTE" VARCHAR2(200), 
	"TRTE_DT_CRIACAO" TIMESTAMP (6),  
	"PELO_ID" NUMBER
   ) ;
   
--------------------------------------------------------
--  DDL for Table TRANSPORTE_TERRESTRE_AUD
--------------------------------------------------------

  CREATE TABLE "TRANSPORTE_TERRESTRE_AUD" 
   (	"TRTE_ID" NUMBER(19,0), 
	"AUDI_ID" NUMBER(19,0), 
	"AUDI_TX_TIPO" NUMBER(3,0), 
	"TRTE_DT_CRIACAO" TIMESTAMP (6), 
	"TRTE_DT_DATA" TIMESTAMP (6), 
	"TRTE_TX_DESTINO" VARCHAR2(255 CHAR), 
	"TRTE_TX_OBJETO_TRANSPORTE" VARCHAR2(255 CHAR), 
	"TRTE_TX_ORIGEM" VARCHAR2(255 CHAR), 
	"PELO_ID" NUMBER(19,0)
   ) ;   
      
--------------------------------------------------------
--  DDL for Table STATUS_PEDIDO_LOGISTICA
--------------------------------------------------------

  CREATE TABLE "STATUS_PEDIDO_LOGISTICA" 
   (	"STPL_ID" NUMBER(2,0), 
	"STPL_TX_DESCRICAO" VARCHAR2(60)
   ) ;

   	
--------------------------------------------------------
--  DDL for Sequence SQ_AVRW_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_AVRW_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Table AVALIACAO_RESULTADO_WORKUP
--------------------------------------------------------

  CREATE TABLE "AVALIACAO_RESULTADO_WORKUP" 
   (	"AVRW_ID" NUMBER, 
	"AVRW_DT_CRIACAO" TIMESTAMP (6), 
	"AVRW_DT_ATUALIZACAO" TIMESTAMP (6), 
	"USUA_ID_RESPONSAVEL" NUMBER, 
	"AVRW_IN_PROSSEGUIR" NUMBER, 
	"AVRW_TX_JUSTIFICATIVA" VARCHAR2(500), 
	"REWO_ID" NUMBER
   );
   
--------------------------------------------------------
--  DDL for Sequence SQ_PEAW_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_PEAW_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;

--------------------------------------------------------
--  DDL for Table PEDIDO_ADICIONAL_WORKUP
--------------------------------------------------------

  CREATE TABLE "PEDIDO_ADICIONAL_WORKUP" 
   (	"PEAW_ID" NUMBER, 
	"PEAW_TX_DESCRICAO" VARCHAR2(200), 
	"PEAW_DT_CRIACAO" TIMESTAMP (6), 
	"PEWO_ID" NUMBER
   ) ;   
   
--------------------------------------------------------
--  DDL for Table ARQUIVO_PEDIDO_ADICIONAL_WORKUP
--------------------------------------------------------   
   
	CREATE TABLE "ARQUIVO_PEDIDO_ADICIONAL_WORKUP"
	(
	  "APAW_ID" NUMBER NOT NULL
	, "APAW_TX_CAMINHO" VARCHAR2(255) NOT NULL
	, "APAW_TX_DESCRICAO" VARCHAR2(255)
	, "PEAW_ID" NUMBER
	);   		
		
--------------------------------------------------------
--  DDL for Table AVALIACAO_PEDIDO_COLETA
--------------------------------------------------------

  CREATE TABLE "AVALIACAO_PEDIDO_COLETA" 
   ("AVPC_ID" NUMBER, 
	"AVPC_DT_CRIACAO" TIMESTAMP (6), 
	"USUA_ID" NUMBER, 
	"AVPC_IN_PEDIDO_PROSSEGUE" NUMBER(1,0), 
	"AVPC_TX_JUSTIFICATIVA" VARCHAR2(500), 
	"AVRW_ID" NUMBER
   ) ;
   
--------------------------------------------------------
--  DDL for Sequence SQ_AVPC_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_AVPC_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
   

--------------------------------------------------------
--  DDL for Table RECEBIMENTO_COLETA
--------------------------------------------------------

  CREATE TABLE "RECEBIMENTO_COLETA" 
   (	"RECE_ID" NUMBER, 
	"PECL_ID" NUMBER, 
	"FOCE_ID" NUMBER, 
	"DECO_ID" NUMBER, 
	"RECE_DT_INFUSAO" DATE, 
	"RECE_DT_DESCARTE" DATE, 
	"RECE_DT_PREVISTA_INFUSAO" DATE, 
	"RECE_NR_TOTAL_TCN" NUMBER, 
	"RECE_NR_TOTAL_CD34" NUMBER, 
	"RECE_TX_JUST_CONGELAMENTO" VARCHAR2(500), 
	"RECE_TX_JUST_DESCARTE" VARCHAR2(500), 
	"RECE_IN_RECEBEU" NUMBER, 
	"RECE_DT_RECEBIMENTO" DATE,
	"RECE_NR_NUMERO_BOLSAS" NUMBER,
	"RECE_NR_VOLUME_PRODUTO" NUMBER,
	"RECE_IN_COLETA_DEACORDO_PRESCRICAO" NUMBER,
	"RECE_TX_MOTIVO_COLETA_INCORRETA" CLOB,
	"RECE_IN_INDENTIFICACAO_DOADOR_CONFERE" NUMBER,
	"RECE_TX_MOTIVO_IDENTIFICACAO_DOADOR_INCORRETO" CLOB,
	"RECE_IN_ACONDICIONADO_CORRETO" NUMBER,
	"RECE_TX_MOTIVO_ACONDICIONADO_INCORRETO" CLOB,
	"RECE_IN_EVENTO_ADVERSO" NUMBER,
	"RECE_TX_DESCREVA_EVENTO_ADVERSO" CLOB,
	"RECE_TX_COMENTARIO_ADICIONAL" CLOB
	
   ) ;
		
--------------------------------------------------------
--  DDL for Table DESTINO_COLETA
--------------------------------------------------------

  CREATE TABLE "DESTINO_COLETA" 
   (	"DECO_ID" NUMBER, 
	"DECO_TX_DESCRICAO" VARCHAR2(60)
   ) ;

--------------------------------------------------------
--  DDL for Table TIPO_PEDIDO_LOGISTICA
--------------------------------------------------------

  CREATE TABLE "TIPO_PEDIDO_LOGISTICA" 
   (	"TIPL_ID" NUMBER(2,0), 
	"TIPL_TX_DESCRICAO" VARCHAR2(30)
   ) ;

   COMMENT ON COLUMN "TIPO_PEDIDO_LOGISTICA"."TIPL_ID" IS 'Identificador do tipo de pedido de logistica.';
   COMMENT ON COLUMN "TIPO_PEDIDO_LOGISTICA"."TIPL_TX_DESCRICAO" IS 'Descrição do tipo de pedido de logistica.';
   COMMENT ON TABLE "TIPO_PEDIDO_LOGISTICA"  IS 'Tabela responsável por armazenar os tipos de pedidos de logistica.';
		
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

      COMMENT ON COLUMN "USUARIO"."USUA_ID" IS 'Identificador do usuário';
      COMMENT ON COLUMN "USUARIO"."USUA_TX_NOME" IS 'Nome do usuário';
      COMMENT ON COLUMN "USUARIO"."USUA_TX_USERNAME" IS 'Username do usuário';
      COMMENT ON COLUMN "USUARIO"."USUA_TX_PASSWORD" IS 'Password do usuário criptografada';
      COMMENT ON COLUMN "USUARIO"."USUA_IN_ATIVO" IS 'Indica se o usuário está ativo no sistema';
      COMMENT ON COLUMN "USUARIO"."TRAN_ID" IS 'Transportadora a qual o usuário está associado.';
      COMMENT ON COLUMN "USUARIO"."LABO_ID" IS 'Referencia para o laboratório ao qual o usuário está vinculado para o caso dele ser do perfil laboratório.';
      COMMENT ON COLUMN "USUARIO"."USUA_TX_EMAIL" IS 'E-mail, informado pelo usuário, para contato.';
      COMMENT ON TABLE "USUARIO"  IS 'Usuários do sistema';
   	
   	
   --------------------------------------------------------
   --  DDL for Table CONTAGEM_CELULA
   --------------------------------------------------------
   
  CREATE TABLE "CONTAGEM_CELULA" 
   ("COCE_ID" NUMBER NOT NULL , 
	"PEWO_ID" NUMBER NOT NULL , 
	"COCE_IN_FONTE_CELULA" VARCHAR2(20 BYTE), 
	"COCE_IN_ABO" VARCHAR2(20 BYTE), 
	"COCE_IN_MANIPULACAO_PRODUTO" NUMBER, 
	"COCE_TX_MANIPULACAO" VARCHAR2(20 BYTE), 
	"COCE_DT_COLETA" DATE, 
	"COCE_VL_VOLUME_BOLSA" NUMBER, 
	"COCE_VL_ANTICOAGULANTE" NUMBER, 
	"COCE_NR_TCN" NUMBER, 
	"COCE_NR_TCN_KG" NUMBER, 
	"COCE_DT_AFERISE_0" DATE, 
	"COCE_VL_VOLUME_BOLSA_0" NUMBER, 
	"COCE_NR_CELULA_0" NUMBER, 
	"COCE_NR_CELULA_KG_0" NUMBER, 
	"COCE_DT_AFERISE_1" DATE, 
	"COCE_VL_VOLUME_BOLSA_1" NUMBER, 
	"COCE_NR_CELULA_1" NUMBER, 
	"COCE_NR_CELULA_KG_1" NUMBER, 
	"COCE_DT_AFERISE_2" DATE, 
	"COCE_VL_VOLUME_BOLSA_2" NUMBER, 
	"COCE_NR_CELULA_2" NUMBER, 
	"COCE_NR_CELULA_KG_2" NUMBER, 
	"COCE_DT_ENVIO" DATE, 
	"COCE_TX_ANTICOAGULANTE" VARCHAR2(20 BYTE), 
	"COCE_IN_INTERCORRENCIA" NUMBER, 
	"COCE_TX_INTERCORRENCIA" VARCHAR2(20 BYTE) 
	
 	);

   COMMENT ON COLUMN "CONTAGEM_CELULA"."COCE_ID" IS 'Identificador da contagem celula.';
   COMMENT ON COLUMN "CONTAGEM_CELULA"."PEWO_ID" IS 'Identificador do pedido de workup.';
   COMMENT ON COLUMN "CONTAGEM_CELULA"."COCE_IN_FONTE_CELULA" IS 'Fonte de Celula';
   COMMENT ON COLUMN "CONTAGEM_CELULA"."COCE_IN_ABO" IS 'ABO';
   COMMENT ON COLUMN "CONTAGEM_CELULA"."COCE_IN_MANIPULACAO_PRODUTO" IS 'Manipulação do produto: 0-FALSE, 1-TRUE.';
   COMMENT ON COLUMN "CONTAGEM_CELULA"."COCE_TX_MANIPULACAO" IS 'Qual Manipulação do produto.';
   COMMENT ON COLUMN "CONTAGEM_CELULA"."COCE_DT_COLETA" IS 'Data da Coleta de Medula.';
   COMMENT ON COLUMN "CONTAGEM_CELULA"."COCE_VL_VOLUME_BOLSA" IS 'Volume da bolsa ao Coletar Medula.';
   COMMENT ON COLUMN "CONTAGEM_CELULA"."COCE_VL_ANTICOAGULANTE" IS 'Volume de Anticoagulante na Bolsa.';
   COMMENT ON COLUMN "CONTAGEM_CELULA"."COCE_NR_TCN" IS 'Quantidade de TCN na Bolsa.';
   COMMENT ON COLUMN "CONTAGEM_CELULA"."COCE_NR_TCN_KG" IS 'Quantidade de TCN por kg na Bolsa.';
   COMMENT ON COLUMN "CONTAGEM_CELULA"."COCE_DT_AFERISE_0" IS 'Data da 1ª aferise sangue periferico ou Linfocito.';
   COMMENT ON COLUMN "CONTAGEM_CELULA"."COCE_VL_VOLUME_BOLSA_0" IS 'Volume da bolsa da 1ª aferise.';
   COMMENT ON COLUMN "CONTAGEM_CELULA"."COCE_NR_CELULA_0" IS 'Numero de Celulas da 1ª aferise.';
   COMMENT ON COLUMN "CONTAGEM_CELULA"."COCE_NR_CELULA_KG_0" IS 'Numero de Celulas por Kg do paciente da 1ª aferise.';
   COMMENT ON COLUMN "CONTAGEM_CELULA"."COCE_DT_AFERISE_1" IS 'Data da 2ª aferise sangue periferico ou Linfocito.';
   COMMENT ON COLUMN "CONTAGEM_CELULA"."COCE_VL_VOLUME_BOLSA_1" IS 'Volume da bolsa da 2ª aferise.';
   COMMENT ON COLUMN "CONTAGEM_CELULA"."COCE_NR_CELULA_1" IS 'Numero de Celulas da 2ª aferise.';
   COMMENT ON COLUMN "CONTAGEM_CELULA"."COCE_NR_CELULA_KG_1" IS 'Numero de Celulas por Kg do paciente da 2ª aferise.';
   COMMENT ON COLUMN "CONTAGEM_CELULA"."COCE_DT_AFERISE_2" IS 'Data da 3ª aferise sangue periferico ou Linfocito.';
   COMMENT ON COLUMN "CONTAGEM_CELULA"."COCE_VL_VOLUME_BOLSA_2" IS 'Volume da bolsa da 3ª aferise.';
   COMMENT ON COLUMN "CONTAGEM_CELULA"."COCE_NR_CELULA_2" IS 'Numero de Celulas da 3ª aferise.';
   COMMENT ON COLUMN "CONTAGEM_CELULA"."COCE_NR_CELULA_KG_2" IS 'Numero de Celulas por Kg do paciente da 3ª aferise.';
   COMMENT ON COLUMN "CONTAGEM_CELULA"."COCE_DT_ENVIO" IS 'Data do Envio da coleta.';
   COMMENT ON COLUMN "CONTAGEM_CELULA"."COCE_TX_ANTICOAGULANTE" IS 'Anticoagulante Utilizado.';
   COMMENT ON COLUMN "CONTAGEM_CELULA"."COCE_IN_INTERCORRENCIA" IS 'Houve alguma intercorrência durante ou após a coleta de CTH?: 0-FALSE, 1-TRUE.';
   COMMENT ON COLUMN "CONTAGEM_CELULA"."COCE_TX_INTERCORRENCIA" IS 'Quais intercorrências durante ou após a coleta de CTH.';
   
   --------------------------------------------------------
   --  DDL for Table FORMULARIO_POSCOLETA
   --------------------------------------------------------
   
  CREATE TABLE FORMULARIO_POSCOLETA
(FOPO_ID NUMBER NOT NULL , 
 PEWO_ID NUMBER, 
 USUA_ID NUMBER,
 FOPO_DT_CRIACAO TIMESTAMP (6) NOT NULL , 
 FOPO_DT_ATUALIZACAO TIMESTAMP (6)
);

 
 COMMENT ON COLUMN FORMULARIO_POSCOLETA.FOPO_ID IS 'Chave primária que identifica com exclusividade um formulário pós coleta.'; 
 COMMENT ON COLUMN FORMULARIO_POSCOLETA.PEWO_ID IS 'Chave estrangeira para pedido de workup';
 COMMENT ON COLUMN FORMULARIO_POSCOLETA.USUA_ID IS 'Id do usuário que preencheu o formulário pós coleta';
 COMMENT ON COLUMN FORMULARIO_POSCOLETA.FOPO_DT_CRIACAO IS 'Data de criação do fromulário pós coleta.';
 COMMENT ON COLUMN FORMULARIO_POSCOLETA.FOPO_DT_ATUALIZACAO IS 'Data de atualização do fromulário pós coleta.';
 
