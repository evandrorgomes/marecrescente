INSERT INTO MODRED.TIPO_TAREFA (TITA_ID, TITA_TX_DESCRICAO, TITA_IN_AUTOMATICA, TITA_NR_TEMPO_EXECUCAO, TARE_IN_SOMENTE_AGRUPADOR)
VALUES (103, 'ENVIAR_PEDIDO_FASE2_PARA_EMDIS', 1, NULL, 0);

COMMIT;

--RETIRADA DA COLUNA DE PEDIDO DE EXAME E DE COLETA PARA A INCLUSÃO CORRETA QUE É PARA PAGAMENTO
ALTER TABLE ITEM_INVOICE 
DROP CONSTRAINT FK_ITIN_PECL;

ALTER TABLE ITEM_INVOICE 
DROP CONSTRAINT FK_ITIN_PEEX;

ALTER TABLE ITEM_INVOICE 
DROP COLUMN PEEX_ID;

ALTER TABLE ITEM_INVOICE 
DROP COLUMN PECL_ID;

ALTER TABLE ITEM_INVOICE 
ADD (PAGA_ID NUMBER );

CREATE INDEX IN_FK_ITIN_PAGA ON ITEM_INVOICE (PAGA_ID ASC);

ALTER TABLE ITEM_INVOICE
ADD CONSTRAINT FK_ITIN_PAGA FOREIGN KEY
(
  PAGA_ID 
)
REFERENCES PAGAMENTO
(
  PAGA_ID 
)
ENABLE;

COMMENT ON COLUMN ITEM_INVOICE.PAGA_ID IS 'Identificação do pagamento junto ao redome';



ALTER TABLE ITEM_INVOICE 
DROP CONSTRAINT FK_ITIN_TIPI;

ALTER TABLE ITEM_INVOICE 
DROP COLUMN TIPI_ID;

--########################################################################################

ALTER TABLE MODRED.BUSCA_CHECKLIST MODIFY (BUCH_IN_VISTO DEFAULT 0 );

--########################################################################################


INSERT INTO MODRED.TIPO_EXAME_LOCUS (LOCU_ID, TIEX_ID) 
VALUES ('DPB1', 1);
INSERT INTO MODRED.TIPO_EXAME_LOCUS (LOCU_ID, TIEX_ID) 
VALUES ('DPB1', 5);
INSERT INTO MODRED.TIPO_EXAME_LOCUS (LOCU_ID, TIEX_ID) 
VALUES ('DPB1', 6);
COMMIT;


ALTER TABLE MODRED.REGISTRO 
ADD (REGI_TX_SIGLA VARCHAR2(2) );

COMMENT ON COLUMN MODRED.REGISTRO.REGI_TX_SIGLA IS 'Silga do registro.';

UPDATE MODRED.REGISTRO SET REGI_TX_SIGLA = 'BR'
WHERE REGI_ID = 1;
UPDATE MODRED.REGISTRO SET REGI_TX_SIGLA = 'US'
WHERE REGI_ID = 2;
COMMIT;

--###############################################################################################################

ALTER TABLE PEDIDO_COLETA ADD (PECL_IN_COLETA_INTERNACIONAL NUMBER(1) DEFAULT 0 );

COMMENT ON COLUMN PEDIDO_COLETA.PECL_IN_COLETA_INTERNACIONAL IS '0- Para coleta de amostra nacional  1-  Para coleta de amostra internacional.';

--###############################################################################################################

ALTER TABLE MODRED.DOADOR  
MODIFY (DOAD_TX_ID_REGISTRO VARCHAR2(30 BYTE) );

ALTER TABLE MODRED.DOADOR_AUD  
MODIFY (DOAD_TX_ID_REGISTRO VARCHAR2(30 BYTE) );

--######################### MODRED.BUSCA #####################################
ALTER TABLE MODRED.BUSCA ADD (BUSC_DT_ANALISE TIMESTAMP );
COMMENT ON COLUMN MODRED.BUSCA.BUSC_DT_ANALISE IS 'Data da análise da busca.';

ALTER TABLE MODRED.BUSCA_AUD ADD (BUSC_DT_ANALISE TIMESTAMP );
COMMENT ON COLUMN MODRED.BUSCA.BUSC_DT_ANALISE IS 'Data da análise da busca.';
--#############################################################################


Insert into USUARIO (USUA_ID,USUA_TX_NOME,USUA_TX_USERNAME,USUA_TX_PASSWORD,USUA_IN_ATIVO,TRAN_ID,LABO_ID,USUA_TX_EMAIL) values ('102','REDOME FILA','REDOME-FILA','$2a$11$KMAfznbkZhx9mvESGy3.GewPWbSGQLVTnj5O0m7cIo5NrucDtcXT.','1',null,null,'redome.fila@mail.com');
Insert into PERFIL (PERF_ID,PERF_TX_DESCRICAO,SIST_ID) values ('202','REDOME_FILA','1');
Insert into USUARIO_PERFIL (USUA_ID,PERF_ID) values ('102','202');

Insert into PERMISSAO (RECU_ID,PERF_ID,PERM_IN_COM_RESTRICAO) values ('91','202','0');
commit;



ALTER TABLE TAREFA 
ADD (USUA_ID_AGENDAMENTO NUMBER );

COMMENT ON COLUMN TAREFA.USUA_ID_AGENDAMENTO IS 'Usuario responsavel pelo atendimento em caso de agendamento';


INSERT INTO "MODRED"."TIPO_TAREFA" (TITA_ID, TITA_TX_DESCRICAO, TITA_IN_AUTOMATICA, TITA_NR_TEMPO_EXECUCAO, TARE_IN_SOMENTE_AGRUPADOR) VALUES ('104', 'DESATRIBUIR_USUARIO_AGENDAMENTO_FOLLOW_UP', '1', '86400', '0');
commit;




INSERT INTO "MODRED"."USUARIO" (USUA_ID, USUA_TX_NOME, USUA_TX_USERNAME, USUA_TX_PASSWORD, USUA_IN_ATIVO, USUA_TX_EMAIL) VALUES (1981, 'CENTRO_COLETA', 'CENTRO_COLETA', '$2a$11$KMAfznbkZhx9mvESGy3.GewPWbSGQLVTnj5O0m7cIo5NrucDtcXT.', '1', 'TESTE@UOL.COM');
COMMIT;

INSERT INTO "MODRED"."PERFIL" (PERF_ID, PERF_TX_DESCRICAO, SIST_ID) VALUES (29, 'CENTRO_COLETA', '1');
COMMIT;

INSERT INTO usuario_perfil(PERF_ID, USUA_ID)VALUES(29, 1981);
COMMIT;

INSERT INTO PERMISSAO (recu_id, perf_id) values (46,29);
commit;


INSERT INTO "MODRED"."USUARIO_CENTRO_TRANSPLANTE" (USUA_ID, CETR_ID) VALUES ('1981', '90');
commit;

insert into permissao(perf_id, recu_id, perm_in_com_restricao)values(29,49,0);
commit;

INSERT INTO PERMISSAO(perf_id,RECU_ID)VALUES(12,10);
COMMIT;



INSERT INTO "MODRED"."CONFIGURACAO" (CONF_ID, CONF_TX_VALOR) VALUES ('extensaoArquivoAlteracaoLogistica', 'application/pdf');
INSERT INTO "MODRED"."CONFIGURACAO" (CONF_ID, CONF_TX_VALOR) VALUES ('tamanhoArquivoAlteracaoLogisticaEmByte', '5242880');
INSERT INTO "MODRED"."CONFIGURACAO" (CONF_ID, CONF_TX_VALOR) VALUES ('quantidadeMaximaArquivosAlteracaoLogistica', '1');
commit;

ALTER TABLE ARQUIVO_PEDIDO_TRANSPORTE 
ADD (ARPT_TX_DESCRICAO_ALTERACAO VARCHAR2(4000) );


/* 04/02/2020 - rODAR NA HOMOLOGAÇÃO */

update log_evolucao set LOEV_IN_TIPO_EVENTO = 'PRESCRICAO_APROVADA_PARA_MEDULA'
where LOEV_IN_TIPO_EVENTO = 'PEDIDO_WORKUP_CRIADO_PARA_DOADOR' and
LOEV_TX_PARAMETROS in (select to_char(DOAD_NR_DMR) from doador where DOAD_IN_TIPO = 0 );

update log_evolucao set LOEV_IN_TIPO_EVENTO = 'PRESCRICAO_APROVADA_PARA_MEDULA'
where LOEV_IN_TIPO_EVENTO = 'PEDIDO_WORKUP_CRIADO_PARA_DOADOR' and
LOEV_TX_PARAMETROS in (select to_char(DOAD_TX_ID_REGISTRO) from doador where DOAD_IN_TIPO = 1 );

update log_evolucao set LOEV_IN_TIPO_EVENTO = 'PRESCRICAO_APROVADA_PARA_CORDAO'
where LOEV_IN_TIPO_EVENTO = 'PEDIDO_WORKUP_CRIADO_PARA_DOADOR' and
LOEV_TX_PARAMETROS in (select DOAD_TX_ID_BANCO_SANGUE_CORDAO from doador where DOAD_IN_TIPO = 2 );

update log_evolucao set LOEV_IN_TIPO_EVENTO = 'PRESCRICAO_APROVADA_PARA_CORDAO'
where LOEV_IN_TIPO_EVENTO = 'PEDIDO_WORKUP_CRIADO_PARA_DOADOR' and
LOEV_TX_PARAMETROS in (select DOAD_TX_ID_REGISTRO from doador where DOAD_IN_TIPO = 3 );


UPDATE MODRED.TIPO_TAREFA SET TITA_TX_DESCRICAO = 'CADASTRAR_PRESCRICAO_MEDULA'
WHERE TITA_ID = 52;

INSERT INTO MODRED.TIPO_TAREFA (TITA_ID,TITA_TX_DESCRICAO,TITA_IN_AUTOMATICA,TITA_NR_TEMPO_EXECUCAO,TARE_IN_SOMENTE_AGRUPADOR) 
VALUES (105,'CADASTRAR_PRESCRICAO_CORDAO',0,NULL,0);

UPDATE MODRED.TIPO_SOLICITACAO SET TISO_TX_DESCRICAO = 'WORKUP_CORDAO'
WHERE TISO_ID = 4;


create or replace FUNCTION FUNC_TEM_PRESCRICAO(pmatc_id integer) RETURN varchar2
IS
total integer;
BEGIN
    SELECT
        COUNT(*) into total
    FROM
        prescricao    p,
        solicitacao   s
    WHERE s.matc_id = pmatc_id
          AND (s.tiso_id = 3 or s.tiso_id = 4)
          AND s.soli_nr_status = 1
          AND p.soli_id = s.soli_id;   
  IF total > 0 THEN
     RETURN 'true';
  ELSE
     RETURN 'false';
  END IF;
END;


/* 11/02/2020 - rodar na homologação */

INSERT INTO MODRED.STATUS_BUSCA (STBU_ID, STBU_TX_DESCRICAO, STBU_IN_BUSCA_ATIVA)
VALUES (6, 'ENCERRADA', 0);

INSERT INTO MODRED.MOTIVO_CANCELAMENTO_COLETA (MOCC_TX_CODIGO,MOCC_DESCRICAO) 
VALUES ('BUSCA_ENCERRADA', 'Ocorre quando a busca do paciente é encerrada e o pedido de coleta, se houver, for realizado.');

INSERT INTO MODRED.STATUS_PEDIDO_LOGISTICA (STPL_ID,STPL_TX_DESCRICAO) 
VALUES (4, 'CANCELADO'); 

INSERT INTO MODRED.STATUS_PEDIDO_TRANSPORTE (STPT_ID, STPT_TX_DESCRICAO)
VALUES (7, 'CANCELADO');

COMMIT;


CREATE TABLE MODRED.TIPO_AMOSTRA 
(
  TIAM_ID NUMBER NOT NULL 
, TIAM_TX_DESCRICAO VARCHAR2(20 BYTE) 
, CONSTRAINT PK_TIPO_AMOSTRA PRIMARY KEY 
  (
    TIAM_ID 
  )
  USING INDEX 
  (
      CREATE UNIQUE INDEX IN_PK_TIAM ON TIPO_AMOSTRA (TIAM_ID ASC) 
  )
  ENABLE 
);

COMMENT ON TABLE MODRED.TIPO_AMOSTRA IS 'Tabela de tipos de amostras à serem solicitadas ao longo do sistema';

COMMENT ON COLUMN MODRED.TIPO_AMOSTRA.TIAM_ID IS 'id da tabela de tipo de amostra';

COMMENT ON COLUMN MODRED.TIPO_AMOSTRA.TIAM_TX_DESCRICAO IS 'Descrição da tabela de tipo de amostra ';






CREATE TABLE MODRED.TIPO_AMOSTRA_PRESCRICAO 
(
  TIAP_ID NUMBER NOT NULL 
, TIAP_NR_ML_AMOSTRA NUMBER NOT NULL 
, PRES_ID NUMBER 
, TIAM_ID NUMBER 
, TIAP_TX_OUTRO_TIPO_AMOSTRA VARCHAR2(100) 
, CONSTRAINT PK_TIAP PRIMARY KEY 
  (
    TIAP_ID 
  )
  USING INDEX 
  (
      CREATE UNIQUE INDEX IN_PK_TIAP ON TIPO_AMOSTRA_PRESCRICAO (TIAP_ID ASC) 
  )
  ENABLE 
);

CREATE INDEX IN_FK_TIAP_PRES ON TIPO_AMOSTRA_PRESCRICAO (PRES_ID);

CREATE INDEX IN_FK_TIAP_TIAM ON TIPO_AMOSTRA_PRESCRICAO (TIAM_ID);

ALTER TABLE MODRED.TIPO_AMOSTRA_PRESCRICAO
ADD CONSTRAINT FK_TIAP_PRES FOREIGN KEY
(
  PRES_ID 
)
REFERENCES PRESCRICAO
(
  PRES_ID 
)
ENABLE;

ALTER TABLE MODRED.TIPO_AMOSTRA_PRESCRICAO
ADD CONSTRAINT FK_TIAP_TIAM FOREIGN KEY
(
  TIAM_ID 
)
REFERENCES TIPO_AMOSTRA
(
  TIAM_ID 
)
ENABLE;

COMMENT ON TABLE MODRED.TIPO_AMOSTRA_PRESCRICAO IS 'Tabela associativa entre tipo de amostra e prescrição';

COMMENT ON COLUMN MODRED.TIPO_AMOSTRA_PRESCRICAO.TIAP_ID IS 'Id da tabela de tipo amostra prescricao';

COMMENT ON COLUMN MODRED.TIPO_AMOSTRA_PRESCRICAO.TIAP_NR_ML_AMOSTRA IS 'Quantidade de MLs solicitados para a amostra ';

COMMENT ON COLUMN MODRED.TIPO_AMOSTRA_PRESCRICAO.PRES_ID IS 'Id da prescricação ';

COMMENT ON COLUMN MODRED.TIPO_AMOSTRA_PRESCRICAO.TIAM_ID IS 'Id do tipo de amostra';

COMMENT ON COLUMN MODRED.TIPO_AMOSTRA_PRESCRICAO.TIAP_TX_OUTRO_TIPO_AMOSTRA IS 'Texto descritivo caso seja outro tipo de amostra';


CREATE SEQUENCE MODRED.SQ_TIAP_ID INCREMENT BY 1 MAXVALUE 9999999999999999999999999999 MINVALUE 1 CACHE 20;



INSERT INTO "MODRED"."TIPO_AMOSTRA" (TIAM_ID, TIAM_TX_DESCRICAO) VALUES ('1', 'EDTA');
INSERT INTO "MODRED"."TIPO_AMOSTRA" (TIAM_ID, TIAM_TX_DESCRICAO) VALUES ('2', 'ACD');
INSERT INTO "MODRED"."TIPO_AMOSTRA" (TIAM_ID, TIAM_TX_DESCRICAO) VALUES ('3', 'Heparina');
INSERT INTO "MODRED"."TIPO_AMOSTRA" (TIAM_ID, TIAM_TX_DESCRICAO) VALUES ('4', 'Sem Anticoagolante');
INSERT INTO "MODRED"."TIPO_AMOSTRA" (TIAM_ID, TIAM_TX_DESCRICAO) VALUES ('5', 'Anti-HLA');
INSERT INTO "MODRED"."TIPO_AMOSTRA" (TIAM_ID, TIAM_TX_DESCRICAO) VALUES ('6', 'Outros');


ALTER TABLE MODRED.DISPONIBILIDADE 
DROP COLUMN DISP_DT_LIMITE_WORKUP;

ALTER TABLE MODRED.DISPONIBILIDADE 
DROP COLUMN DISP_DT_COLETA;

ALTER TABLE MODRED.DISPONIBILIDADE 
DROP COLUMN DISP_IN_CT_COLETA;

ALTER TABLE MODRED.DISPONIBILIDADE 
DROP COLUMN DISP_DT_DISPONIBILIDADE_DOADOR;

ALTER TABLE MODRED.DISPONIBILIDADE 
DROP COLUMN DISP_DT_LIBERACAO_DOADOR;


ALTER TABLE MODRED.DISPONIBILIDADE_AUD 
DROP COLUMN DISP_DT_LIMITE_WORKUP;

ALTER TABLE MODRED.DISPONIBILIDADE_AUD 
DROP COLUMN DISP_DT_COLETA;

ALTER TABLE MODRED.DISPONIBILIDADE_AUD
DROP COLUMN DISP_IN_CT_COLETA;

ALTER TABLE MODRED.DISPONIBILIDADE_AUD 
DROP COLUMN DISP_DT_DISPONIBILIDADE_DOADOR;

ALTER TABLE MODRED.DISPONIBILIDADE_AUD 
DROP COLUMN DISP_DT_LIBERACAO_DOADOR;


ALTER TABLE DISPONIBILIDADE_CENTRO_COLETA 
DROP CONSTRAINT FK_DICC_CETR;

ALTER TABLE DISPONIBILIDADE_CENTRO_COLETA 
DROP CONSTRAINT FK_DICC_PEWO;

ALTER TABLE DISPONIBILIDADE_CENTRO_COLETA 
DROP CONSTRAINT PK_DICC;

DROP INDEX IN_FK_DICC_CETR;

DROP INDEX IN_FK_DICC_PEWO;

DROP TABLE DISPONIBILIDADE_CENTRO_COLETA;
DROP SEQUENCE MODRED.SQ_DICC_ID;

INSERT INTO MODRED.CATEGORIA_NOTIFICACAO (CANO_ID, CANO_TX_DESCRICAO) 
VALUES (11, 'Pedido Coleta');

INSERT INTO MODRED.CATEGORIA_NOTIFICACAO (CANO_ID, CANO_TX_DESCRICAO) 
VALUES (12, 'Avaliacao Pedido Workup');

INSERT INTO MODRED.CATEGORIA_NOTIFICACAO (CANO_ID, CANO_TX_DESCRICAO) 
VALUES (13, 'Match Disponibilizado');
COMMIT;

UPDATE "MODRED"."TIPO_TRANSPLANTE" SET TITR_TX_DESCRICAO = 'Alogênico - Aparentado' WHERE TITR_ID = 2;
UPDATE "MODRED"."TIPO_TRANSPLANTE" SET TITR_TX_DESCRICAO = 'Alogênico - Haploidêntico ' WHERE  TITR_ID = 3;
UPDATE "MODRED"."TIPO_TRANSPLANTE" SET TITR_TX_DESCRICAO = 'Alogênico - SCUP' WHERE  TITR_ID = 4;
INSERT INTO "MODRED"."TIPO_TRANSPLANTE" (TITR_ID, TITR_TX_DESCRICAO) VALUES ('5', 'Alogênico - Não Aparentado');
INSERT INTO "MODRED"."TIPO_TRANSPLANTE" (TITR_ID, TITR_TX_DESCRICAO) VALUES ('6', 'Autólogo');




CREATE TABLE MODRED.TIPO_TRANSPLANTE_EVOLUCAO 
(
  TITR_ID NUMBER NOT NULL 
, EVOL_ID NUMBER NOT NULL 
, CONSTRAINT PK_TITE PRIMARY KEY 
  (
    TITR_ID 
  , EVOL_ID 
  )
  USING INDEX 
  (
      CREATE UNIQUE INDEX IN_PK_TITE ON TIPO_TRANSPLANTE_EVOLUCAO (TITR_ID ASC, EVOL_ID ASC) 
  )
  ENABLE 
);

CREATE INDEX IN_FK_TITE_EVOL ON TIPO_TRANSPLANTE_EVOLUCAO (EVOL_ID ASC);

CREATE INDEX IN_FK_TITE_TITR ON TIPO_TRANSPLANTE_EVOLUCAO (TITR_ID ASC);

ALTER TABLE TIPO_TRANSPLANTE_EVOLUCAO
ADD CONSTRAINT FK_TITE_EVOL FOREIGN KEY
(
  EVOL_ID 
)
REFERENCES EVOLUCAO
(
  EVOL_ID 
)
ENABLE;

ALTER TABLE TIPO_TRANSPLANTE_EVOLUCAO
ADD CONSTRAINT FK_TITE_TITR FOREIGN KEY
(
  TITR_ID 
)
REFERENCES TIPO_TRANSPLANTE
(
  TITR_ID 
)
ENABLE;

COMMENT ON TABLE MODRED.TIPO_TRANSPLANTE_EVOLUCAO IS 'Tabela associativa de tipo de transplnate e evolução';

COMMENT ON COLUMN MODRED.TIPO_TRANSPLANTE_EVOLUCAO.TITR_ID IS 'Referencia para tipo transplante ';

COMMENT ON COLUMN MODRED.TIPO_TRANSPLANTE_EVOLUCAO.EVOL_ID IS 'Referencia para evolucao';

INSERT INTO MODRED.TIPO_TAREFA VALUES (107, 'VERIFICAR_GERACAO_MATCH_DOADOR', 0, NULL,0);  
INSERT INTO MODRED.TIPO_TAREFA VALUES (108, 'VERIFICAR_GERACAO_MATCH_PACIENTE', 0, NULL,0);  
commit;

ALTER TABLE EVOLUCAO 
ADD (EVOL_DT_ULTIMO_TRANSPLANTE DATE );

COMMENT ON COLUMN EVOLUCAO.EVOL_DT_ULTIMO_TRANSPLANTE IS 'Data do ultimo transplante caso exista
';
--#######################################################################################################
ALTER TABLE MODRED.PROCESSO ADD (DOAD_ID NUMBER );
COMMENT ON COLUMN MODRED.PROCESSO.DOAD_ID IS 'Doador para o qual este processo de busca está associado.';
--#######################################################################################################

--#######################################################################################################
-- CRIAÇÃO E ALTERAÇÃO - WMDA
--#######################################################################################################

CREATE SEQUENCE  "MODRED"."SQ_PEWM_ID"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOKEEP  NOSCALE  GLOBAL ;
CREATE SEQUENCE  "MODRED"."SQ_PEWD_ID"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOKEEP  NOSCALE  GLOBAL ;
CREATE SEQUENCE  "MODRED"."SQ_REAS_ID"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOKEEP  NOSCALE  GLOBAL ;

  CREATE TABLE "MODRED"."PESQUISA_WMDA" 
   ("PEWM_ID" NUMBER NOT NULL ENABLE, 
"BUSC_ID" NUMBER NOT NULL ENABLE, 
"PEWM_NR_SEARCH_ID" NUMBER NOT NULL ENABLE, 
"PEWM_NR_SEARCH_RESULT_ID" NUMBER, 
"PEWM_TX_SEARCH_TYPE" VARCHAR2(2 BYTE) NOT NULL ENABLE, 
"PEWM_TX_ALGORITHM" VARCHAR2(1 BYTE), 
"PEWM_DT_ENVIO" DATE NOT NULL ENABLE, 
"PEWM_DT_RESULTADO" DATE, 
CONSTRAINT "PK_PEWM" PRIMARY KEY ("PEWM_ID")
  USING INDEX (CREATE UNIQUE INDEX "MODRED"."PESQUISA_WMDA_PK" ON "MODRED"."PESQUISA_WMDA" ("PEWM_ID") 
  TABLESPACE "MODRED" )  ENABLE, 
CONSTRAINT "FK_PEWM_BUSC" FOREIGN KEY ("BUSC_ID")
 REFERENCES "MODRED"."BUSCA" ("BUSC_ID") ENABLE
   ) 
   TABLESPACE "MODRED" ;

   COMMENT ON COLUMN "MODRED"."PESQUISA_WMDA"."PEWM_ID" IS 'Identificador da tabela';
   COMMENT ON COLUMN "MODRED"."PESQUISA_WMDA"."BUSC_ID" IS 'Identificador da busca';
   COMMENT ON COLUMN "MODRED"."PESQUISA_WMDA"."PEWM_NR_SEARCH_ID" IS 'Identificador da busca no WMDA retorno do post /pastients/{wmdaId}/searches';
   COMMENT ON COLUMN "MODRED"."PESQUISA_WMDA"."PEWM_NR_SEARCH_RESULT_ID" IS 'Identificador da pesquisa - retorno do get /patients/{wmdaId}/searches';
   COMMENT ON COLUMN "MODRED"."PESQUISA_WMDA"."PEWM_TX_SEARCH_TYPE" IS 'Tipo de busca DR para doador de medula e CB para cordão';
   COMMENT ON COLUMN "MODRED"."PESQUISA_WMDA"."PEWM_TX_ALGORITHM" IS 'Tipo de algoritimo utilizado H para haplotipo e F para alélico';
   COMMENT ON COLUMN "MODRED"."PESQUISA_WMDA"."PEWM_DT_ENVIO" IS 'Data e hora do post';
   COMMENT ON COLUMN "MODRED"."PESQUISA_WMDA"."PEWM_DT_RESULTADO" IS 'Data e hora do get dos resultados';
   

CREATE TABLE "MODRED"."PESQUISA_WMDA_DOADOR" 
   (	"PEWD_ID" NUMBER NOT NULL ENABLE, 
"PEWM_ID" NUMBER NOT NULL ENABLE, 
"PEWD_TX_ID" VARCHAR2(20 BYTE) NOT NULL ENABLE, 
"PEWD_TX_GRID" VARCHAR2(19 BYTE), 
"PEWD_NR_DON_POOL" VARCHAR2(4 BYTE) NOT NULL ENABLE, 
"PEWD_TX_JSON" CLOB NOT NULL ENABLE, 
CONSTRAINT "PK_PEWD" PRIMARY KEY ("PEWD_ID")
  USING INDEX (CREATE UNIQUE INDEX "MODRED"."PESQUISA_WMDA_DOADOR_PK" ON "MODRED"."PESQUISA_WMDA_DOADOR" ("PEWD_ID") 
  TABLESPACE "MODRED" )  ENABLE, 
CONSTRAINT "FK_PEWD_PEWM" FOREIGN KEY ("PEWM_ID")
 REFERENCES "MODRED"."PESQUISA_WMDA" ("PEWM_ID") ENABLE
   ) 
  TABLESPACE "MODRED";

   COMMENT ON COLUMN "MODRED"."PESQUISA_WMDA_DOADOR"."PEWD_ID" IS 'Identificador da tabela';
   COMMENT ON COLUMN "MODRED"."PESQUISA_WMDA_DOADOR"."PEWM_ID" IS 'Identificado/chave estrangeira para PESQUISA_WMDA';
   COMMENT ON COLUMN "MODRED"."PESQUISA_WMDA_DOADOR"."PEWD_TX_ID" IS 'Id do doador - retorno do serviço';
   COMMENT ON COLUMN "MODRED"."PESQUISA_WMDA_DOADOR"."PEWD_TX_GRID" IS 'GRID do doador - retorno do serviço';
   COMMENT ON COLUMN "MODRED"."PESQUISA_WMDA_DOADOR"."PEWD_NR_DON_POOL" IS 'Idenficação do registro do doador - retorno do serviço';
   COMMENT ON COLUMN "MODRED"."PESQUISA_WMDA_DOADOR"."PEWD_TX_JSON" IS 'JSON com dados detalhados do doador - retorno do serviço';  


  CREATE TABLE "MODRED"."REGISTRO_ASSOCIADO" 
   (	"REAS_ID" NUMBER NOT NULL ENABLE, 
"REGI_ID" NUMBER NOT NULL ENABLE, 
"REAS_NR_DON_POOL" NUMBER NOT NULL ENABLE, 
"REAS_IN_PRINCIPAL" NUMBER(*,0) DEFAULT 0 NOT NULL ENABLE, 
"REAS_TX_WMDA_SHORTCODE" VARCHAR2(20 BYTE), 
CONSTRAINT "PK_REAS" PRIMARY KEY ("REAS_ID")
  USING INDEX (CREATE UNIQUE INDEX "MODRED"."REGISTRO_ASSOCIADO_PK" ON "MODRED"."REGISTRO_ASSOCIADO" ("REAS_ID") 
    TABLESPACE "MODRED" )  ENABLE, 
CONSTRAINT "FK_REAS_REGI" FOREIGN KEY ("REGI_ID")
 REFERENCES "MODRED"."REGISTRO" ("REGI_ID") ENABLE
   ) 
  TABLESPACE "MODRED" ;

   COMMENT ON COLUMN "MODRED"."REGISTRO_ASSOCIADO"."REAS_ID" IS 'Identificador da tabela';
   COMMENT ON COLUMN "MODRED"."REGISTRO_ASSOCIADO"."REGI_ID" IS 'Chave estrangeira para tabela REGISTRO';
   COMMENT ON COLUMN "MODRED"."REGISTRO_ASSOCIADO"."REAS_NR_DON_POOL" IS 'Númerodo donor pool associado';
   COMMENT ON COLUMN "MODRED"."REGISTRO_ASSOCIADO"."REAS_IN_PRINCIPAL" IS 'Indica se é o principal';


alter table paciente add(paci_nr_wmda_id number);
COMMENT ON COLUMN "MODRED"."PACIENTE"."PACI_NR_WMDA_ID" IS 'Identificador do paciente no WMDA - retorno do post patients';

ALTER TABLE PACIENTE_AUD ADD (PACI_NR_WMDA_ID NUMBER );
COMMENT ON COLUMN "MODRED"."PACIENTE_AUD"."PACI_NR_WMDA_ID" IS 'Identificador do paciente no WMDA - retorno do post patients';

alter table registro add(regi_nr_don_pool number);
COMMENT ON COLUMN "MODRED"."REGISTRO"."REGI_NR_DON_POOL" IS 'Identificador internacional do registro utilizado no EMDIS e WMDA'

Insert into RECURSO (RECU_ID,RECU_TX_SIGLA,RECU_TX_DESCRICAO) values (216,'CADASTRAR_PESQUISA_WMDA','Cria uma nova pesquisa wmda');
Insert into RECURSO (RECU_ID,RECU_TX_SIGLA,RECU_TX_DESCRICAO) values (217,'CONSULTAR_PESQUISA_WMDA','Consulta as informações da pesquisa wmda');
commit;

Insert into PERMISSAO (RECU_ID,PERF_ID,PERM_IN_COM_RESTRICAO) values (216, 5, 0);
Insert into PERMISSAO (RECU_ID,PERF_ID,PERM_IN_COM_RESTRICAO) values (217, 5, 0);
commit;

--#######################################################################################################

ALTER TABLE MODRED.SOLICITACAO 
ADD (USUA_ID_RESPONSAVEL NUMBER );

CREATE INDEX MODRED.IN_FK_SOLI_USUA_RESPONSAVEL ON MODRED.SOLICITACAO (USUA_ID_RESPONSAVEL ASC);

ALTER TABLE MODRED.SOLICITACAO
ADD CONSTRAINT FK_SOLI_USUA_RESPONSAVEL FOREIGN KEY
(
  USUA_ID_RESPONSAVEL 
)
REFERENCES MODRED.USUARIO
(
  USUA_ID 
)
ENABLE;

COMMENT ON COLUMN MODRED.SOLICITACAO.USUA_ID_RESPONSAVEL IS 'Usuario resonsavel pela solicitaçao de workup.';

--###########################################################################################################

######## SPRINT 63 #########################

CREATE TABLE MODRED.FASE_WORKUP 
(
  FAWO_ID NUMBER NOT NULL 
, FAWO_TX_SIGLA VARCHAR2(50) NOT NULL 
);

COMMENT ON TABLE MODRED.FASE_WORKUP IS 'Contem as fases do workup.';
COMMENT ON COLUMN MODRED.FASE_WORKUP.FAWO_ID IS 'Identificador unico do registro.';
COMMENT ON COLUMN MODRED.FASE_WORKUP.FAWO_TX_SIGLA IS 'Descriçao da fase.';


ALTER TABLE MODRED.FASE_WORKUP
ADD CONSTRAINT PK_FAWO PRIMARY KEY 
(
  FAWO_ID 
)
USING INDEX 
(
    CREATE UNIQUE INDEX PK_FAWO ON FASE_WORKUP (FAWO_ID ASC) 
)
 ENABLE;
 
INSERT INTO MODRED.FASE_WORKUP (FAWO_ID, FAWO_TX_SIGLA) 
VALUES (1, 'AGUARDANDO_AVALIACAO_PRESCRICAO');
INSERT INTO MODRED.FASE_WORKUP (FAWO_ID, FAWO_TX_SIGLA) 
VALUES (2, 'AGUARDANDO_DISTRIBUICAO_WORKUP');
INSERT INTO MODRED.FASE_WORKUP (FAWO_ID, FAWO_TX_SIGLA) 
VALUES (3, 'AGUARDANDO_FORMULARIO_DOADOR');
INSERT INTO MODRED.FASE_WORKUP (FAWO_ID, FAWO_TX_SIGLA) 
VALUES (4, 'AGUARDANDO_PLANO_WORKUP');
INSERT INTO MODRED.FASE_WORKUP (FAWO_ID, FAWO_TX_SIGLA) 
VALUES (5, 'AGUARDANDO_PEDIDO_COLETA');
COMMIT; 
 

CREATE TABLE MODRED.MOTIVO_CANCELAMENTO_SOLICITACAO 
(
  MOCS_ID NUMBER NOT NULL 
, MOCS_TX_DESCRICAO VARCHAR2(50) NOT NULL 
);

COMMENT ON TABLE MODRED.MOTIVO_CANCELAMENTO_SOLICITACAO IS 'Motivos de cancelamento das solicitaçoes.';
COMMENT ON COLUMN MODRED.MOTIVO_CANCELAMENTO_SOLICITACAO.MOCS_ID IS 'Identificador unico do registro.';
COMMENT ON COLUMN MODRED.MOTIVO_CANCELAMENTO_SOLICITACAO.MOCS_TX_DESCRICAO IS 'Descriçao do motivo do cancelamento.';


ALTER TABLE MODRED.MOTIVO_CANCELAMENTO_SOLICITACAO
ADD CONSTRAINT PK_MOCS PRIMARY KEY 
(
  MOCS_ID 
)
USING INDEX 
(
    CREATE UNIQUE INDEX PK_MOCS ON MOTIVO_CANCELAMENTO_SOLICITACAO (MOCS_ID ASC) 
)
 ENABLE;


INSERT INTO MODRED.MOTIVO_CANCELAMENTO_SOLICITACAO (MOCS_ID, MOCS_TX_DESCRICAO) 
VALUES (1,	'BUSCA ENCERRADA');
INSERT INTO MODRED.MOTIVO_CANCELAMENTO_SOLICITACAO (MOCS_ID, MOCS_TX_DESCRICAO) 
VALUES (2,	'BUSCA CANCELADA');
INSERT INTO MODRED.MOTIVO_CANCELAMENTO_SOLICITACAO (MOCS_ID, MOCS_TX_DESCRICAO) 
VALUES (3,	'PRESCRICAO CANCELADA');
COMMIT;


ALTER TABLE MODRED.SOLICITACAO 
ADD (FAWO_ID NUMBER );

ALTER TABLE MODRED.SOLICITACAO 
ADD (MOCS_ID NUMBER );

CREATE INDEX IN_FK_SOLI_FAWO ON MODRED.SOLICITACAO (FAWO_ID ASC);

CREATE INDEX IN_FK_SOLI_MOCS ON MODRED.SOLICITACAO (MOCS_ID ASC);

ALTER TABLE MODRED.SOLICITACAO
ADD CONSTRAINT FK_SOLI_FAWO FOREIGN KEY
(
  FAWO_ID 
)
REFERENCES MODRED.FASE_WORKUP
(
  FAWO_ID 
)
ENABLE;

ALTER TABLE MODRED.SOLICITACAO
ADD CONSTRAINT FK_SOLI_MOCS FOREIGN KEY
(
  MOCS_ID 
)
REFERENCES MODRED.MOTIVO_CANCELAMENTO_SOLICITACAO
(
  MOCS_ID 
)
ENABLE;

COMMENT ON COLUMN MODRED.SOLICITACAO.FAWO_ID IS 'Chave estrangeira para a fase_workup.';

COMMENT ON COLUMN MODRED.SOLICITACAO.MOCS_ID IS 'Chave estrangeira para o motivo_cancelamento_solicitacao.';


ALTER TABLE MODRED.FORMULARIO_DOADOR ADD (PEWO_ID NUMBER );
ALTER TABLE MODRED.FORMULARIO_DOADOR MODIFY (PECO_ID NULL);
ALTER TABLE MODRED.FORMULARIO_DOADOR ADD CONSTRAINT FK_FODO_PEWO FOREIGN KEY ( PEWO_ID )
REFERENCES MODRED.PEDIDO_WORKUP ( PEWO_ID ) ENABLE;
COMMENT ON COLUMN MODRED.FORMULARIO_DOADOR.PEWO_ID IS 'Chave estrangeira para o pedido de workup.';

INSERT INTO "MODRED"."RECURSO" (RECU_ID, RECU_TX_SIGLA, RECU_TX_DESCRICAO) VALUES (221, 'CONSULTAR_FORMULARIO', 'Permite consultar formularios.')
INSERT INTO "MODRED"."RECURSO" (RECU_ID, RECU_TX_SIGLA, RECU_TX_DESCRICAO) VALUES (222, 'CADASTRAR_FORMULARIO', 'Permite cadastrar um novo formulário.')

INSERT INTO "MODRED"."PERMISSAO" (RECU_ID, PERF_ID, PERM_IN_COM_RESTRICAO) VALUES (221, 9, 0)
INSERT INTO "MODRED"."PERMISSAO" (RECU_ID, PERF_ID, PERM_IN_COM_RESTRICAO) VALUES (222, 9, 0)

--###########################################################################################################

INSERT INTO "MODRED"."TIPO_TAREFA" VALUES (114,'DEFINIR_CENTRO_COLETA',0,0,0);
INSERT INTO "MODRED"."TIPO_TAREFA" VALUES (115,'INFORMAR_PLANO_WORKUP',0,0,0);
COMMIT;

--###########################################################################################################

ALTER TABLE MODRED.ENDERECO_CONTATO ADD (ENCO_IN_WORKUP NUMBER DEFAULT 0 );
COMMENT ON COLUMN MODRED.ENDERECO_CONTATO.ENCO_IN_WORKUP IS 'Flag lógico que identifica se o endereço é de workup para um determinado centro de transplante.';

ALTER TABLE MODRED.ENDERECO_CONTATO_AUD ADD (ENCO_IN_WORKUP NUMBER DEFAULT 0 );
COMMENT ON COLUMN MODRED.ENDERECO_CONTATO_AUD.ENCO_IN_WORKUP IS 'Flag lógico que identifica se o endereço é de workup para um determinado centro de transplante.';

--###########################################################################################################

ALTER TABLE MODRED.ARQUIVO_RESULTADO_WORKUP MODIFY (REWO_ID NULL);
ALTER TABLE MODRED.ARQUIVO_RESULTADO_WORKUP MODIFY CONSTRAINT FK_ARRW_REWO DISABLE;

INSERT INTO "MODRED"."PERMISSAO" (RECU_ID, PERF_ID, PERM_IN_COM_RESTRICAO) VALUES (221, 32, 0)
INSERT INTO "MODRED"."PERMISSAO" (RECU_ID, PERF_ID, PERM_IN_COM_RESTRICAO) VALUES (222, 32, 0)

--###########################################################################################################

UPDATE "MODRED"."RECURSO" SET RECU_TX_SIGLA = 'FINALIZAR_FORMULARIO_RESULTADO_WORKUP', RECU_TX_DESCRICAO = 'Permite finalizar formulário resultado de workup.' WHERE RECU_ID = 227;
UPDATE "MODRED"."TIPO_TAREFA" SET TITA_TX_DESCRICAO = 'AVALIAR_RESULTADO_WORKUP_NACIONAL' WHERE TITA_ID = 33;
INSERT INTO "MODRED"."RECURSO" (RECU_ID, RECU_TX_SIGLA, RECU_TX_DESCRICAO) VALUES (SQ_RECU_ID.NEXTVAL, 'FINALIZAR_FORMULARIO_PEDIDO_WORKUP', 'Permite finalizar formulário pedido de workup.')

ALTER TABLE MODRED.RESULTADO_WORKUP ADD (REWO_IN_COLETA_INVIAVEL NUMBER );
COMMENT ON COLUMN MODRED.RESULTADO_WORKUP.REWO_IN_COLETA_INVIAVEL IS 'Coleta inviavel pelo registro inernacional.';

INSERT INTO "MODRED"."TIPO_TAREFA" (TITA_ID, TITA_TX_DESCRICAO, TITA_IN_AUTOMATICA, TARE_IN_SOMENTE_AGRUPADOR) VALUES (222, 'AVALIAR_RESULTADO_WORKUP_INTERNACIONAL', 0, 0);

--###########################################################################################################
INSERT INTO "MODRED"."PERMISSAO" (RECU_ID, PERF_ID, PERM_IN_COM_RESTRICAO) VALUES (221, 15, 0);

--###########################################################################################################

--######################################### Sprint 68 #######################################################

ALTER TABLE MODRED.ENDERECO_CONTATO ADD (ENCO_IN_GCSF NUMBER DEFAULT 0 );
ALTER TABLE MODRED.ENDERECO_CONTATO ADD (ENCO_IN_INTERNACAO NUMBER DEFAULT 0 );
COMMENT ON COLUMN MODRED.ENDERECO_CONTATO.ENCO_IN_GCSF IS 'Flag lógico que identifica se o endereço é de G-CSF para um determinado centro de transplante.';
COMMENT ON COLUMN MODRED.ENDERECO_CONTATO.ENCO_IN_INTERNACAO IS 'Flag lógico que identifica se o endereço é de internação para um determinado centro de transplante.';

ALTER TABLE MODRED.ENDERECO_CONTATO_AUD ADD (ENCO_IN_GCSF NUMBER DEFAULT 0 );
ALTER TABLE MODRED.ENDERECO_CONTATO_AUD ADD (ENCO_IN_INTERNACAO NUMBER DEFAULT 0 );
COMMENT ON COLUMN MODRED.ENDERECO_CONTATO_AUD.ENCO_IN_GCSF IS 'Flag lógico que identifica se o endereço é de G-CSF para um determinado centro de transplante.';
COMMENT ON COLUMN MODRED.ENDERECO_CONTATO_AUD.ENCO_IN_INTERNACAO IS 'Flag lógico que identifica se o endereço é de internação para um determinado centro de transplante.';

ALTER TABLE SOLICITACAO 
ADD (CETR_ID_TRANSPLANTE NUMBER );

ALTER TABLE SOLICITACAO 
ADD (CETR_ID_COLETA NUMBER );

CREATE INDEX IN_FK_SOLI_CETR_COLETA ON SOLICITACAO (CETR_ID_COLETA ASC);

CREATE INDEX IN_FK_SOLI_CETR_TRANSPLANTE ON SOLICITACAO (CETR_ID_TRANSPLANTE ASC);

ALTER TABLE SOLICITACAO
ADD CONSTRAINT FK_SOLI_CETR_COLETA FOREIGN KEY
(
  CETR_ID_COLETA 
)
REFERENCES CENTRO_TRANSPLANTE
(
  CETR_ID 
)
ENABLE;

ALTER TABLE SOLICITACAO
ADD CONSTRAINT FK_SOLI_CETR_TRANSPLANTE FOREIGN KEY
(
  CETR_ID_TRANSPLANTE 
)
REFERENCES CENTRO_TRANSPLANTE
(
  CETR_ID 
)
ENABLE;

--###########################################################################################################

ALTER TABLE MODRED.MATCH ADD (MATC_NR_ORDENACAO_WMDA NUMBER(2,0) );
COMMENT ON COLUMN MODRED.MATCH.MATC_NR_ORDENACAO_WMDA IS 'Ordem do doador wmda para ser apresentada na tela.';

ALTER TABLE MODRED.MATCH ADD (MACH_TX_PROBABILIDADE0 VARCHAR2(5) );
ALTER TABLE MODRED.MATCH ADD (MACH_TX_PROBABILIDADE1 VARCHAR2(5) );
ALTER TABLE MODRED.MATCH ADD (MACH_TX_PROBABILIDADE2 VARCHAR2(5) );
COMMENT ON COLUMN MODRED.MATCH.MACH_TX_PROBABILIDADE0 IS '% probabilidade de 0 mismatch';
COMMENT ON COLUMN MODRED.MATCH.MACH_TX_PROBABILIDADE1 IS '% probabilidade de 1 mismatch';
COMMENT ON COLUMN MODRED.MATCH.MACH_TX_PROBABILIDADE2 IS '% probabilidade de 2 mismatch';

ALTER TABLE MODRED.QUALIFICACAO_MATCH MODIFY (QUMA_NR_POSICAO NULL);
ALTER TABLE MODRED.QUALIFICACAO_MATCH ADD (QUMA_TX_PROBABILIDADE_A VARCHAR2(5) );
ALTER TABLE MODRED.QUALIFICACAO_MATCH ADD (QUMA_TX_PROBABILIDADE_B VARCHAR2(5) );
ALTER TABLE MODRED.QUALIFICACAO_MATCH ADD (QUMA_TX_PROBABILIDADE_C VARCHAR2(5) );
ALTER TABLE MODRED.QUALIFICACAO_MATCH ADD (QUMA_TX_PROBABILIDADE_DR VARCHAR2(5) );
ALTER TABLE MODRED.QUALIFICACAO_MATCH ADD (QUMA_TX_PROBABILIDADE_DQ VARCHAR2(5) );
COMMENT ON COLUMN MODRED.QUALIFICACAO_MATCH.QUMA_TX_PROBABILIDADE_A IS 'probabilidade de compatibilidade em A*';
COMMENT ON COLUMN MODRED.QUALIFICACAO_MATCH.QUMA_TX_PROBABILIDADE_B IS 'probabilidade de compatibilidade em B*';
COMMENT ON COLUMN MODRED.QUALIFICACAO_MATCH.QUMA_TX_PROBABILIDADE_C IS 'probabilidade de compatibilidade em C*';
COMMENT ON COLUMN MODRED.QUALIFICACAO_MATCH.QUMA_TX_PROBABILIDADE_DR IS 'probabilidade de compatibilidade em DRB1*';
COMMENT ON COLUMN MODRED.QUALIFICACAO_MATCH.QUMA_TX_PROBABILIDADE_DQ IS 'probabilidade de compatibilidade em DQB1*';

ALTER TABLE MODRED.REGISTRO MODIFY (REGI_TX_NOME VARCHAR2(200 BYTE) );
ALTER TABLE MODRED.REGISTRO MODIFY (REGI_TX_SIGLA VARCHAR2(6 BYTE) );


ALTER TABLE MODRED.PESQUISA_WMDA ADD (PEWM_NR_WMDA_ID VARCHAR2(10) );
ALTER TABLE MODRED.PESQUISA_WMDA ADD (PEWM_NR_STATUS NUMBER );
ALTER TABLE MODRED.PESQUISA_WMDA RENAME COLUMN PEWM_NR_SEARCH_ID TO PEWM_NR_SEARCH;
ALTER TABLE MODRED.PESQUISA_WMDA RENAME COLUMN PEWM_NR_SEARCH_RESULT_ID TO PEWM_NR_SEARCH_RESULT;
ALTER TABLE MODRED.PESQUISA_WMDA MODIFY (PEWM_TX_SEARCH_TYPE VARCHAR2(5 BYTE) );
ALTER TABLE MODRED.PESQUISA_WMDA MODIFY (PEWM_TX_ALGORITHM);

COMMENT ON COLUMN MODRED.PESQUISA_WMDA.PEWM_NR_SEARCH IS 'Identificação da pesquisa no wmda.';
COMMENT ON COLUMN MODRED.PESQUISA_WMDA.PEWM_NR_SEARCH_RESULT IS 'Identificação do resultado da pesquisa no wmda.';
COMMENT ON COLUMN MODRED.PESQUISA_WMDA.PEWM_TX_SEARCH_TYPE IS 'Tipo de pesquisa no wmda. DR (medula) e CB (Cordao).';
COMMENT ON COLUMN MODRED.PESQUISA_WMDA.PEWM_NR_WMDA_ID IS 'Identificação do paciente no registro do wmda.';
COMMENT ON COLUMN MODRED.PESQUISA_WMDA.PEWM_NR_STATUS IS 'Status da pesquisa: 1-aberta, 2-concluída e 3-cancelada.';

--###########################################################################################################

ALTER TABLE MODRED.PESQUISA_WMDA_DOADOR ADD (PEWD_TX_LOG CLOB );
ALTER TABLE MODRED.PACIENTE RENAME COLUMN PACI_NR_WMDA_ID TO PACI_TX_WMDA_ID;
ALTER TABLE MODRED.PACIENTE MODIFY (PACI_TX_WMDA_ID VARCHAR2(10) );

ALTER TABLE MODRED.PACIENTE_AUD RENAME COLUMN PACI_NR_WMDA_ID TO PACI_TX_WMDA_ID;
ALTER TABLE MODRED.PACIENTE_AUD MODIFY (PACI_TX_WMDA_ID VARCHAR2(10) );

ALTER TABLE MODRED.PESQUISA_WMDA RENAME COLUMN PEWM_NR_WMDA_ID TO PEWM_TX_WMDA_ID;

--###########################################################################################################

GRANT SELECT ON MODRED.TIPO_TRANSPLANTE_EVOLUCAO TO RMODRED_READ;
GRANT INSERT, UPDATE, DELETE ON MODRED.TIPO_TRANSPLANTE_EVOLUCAO TO RMODRED_WRITE;

--###########################################################################################################

ALTER TABLE MODRED.PESQUISA_WMDA_DOADOR MODIFY (PEWD_TX_ID NULL);
ALTER TABLE MODRED.PESQUISA_WMDA_DOADOR MODIFY (PEWD_NR_DON_POOL NULL);
ALTER TABLE MODRED.PESQUISA_WMDA_DOADOR MODIFY (PEWD_TX_JSON NULL);

--###########################################################################################################

ALTER TABLE MODRED.PESQUISA_WMDA ADD (PEWM_NR_MATCH_WMDA NUMBER );
ALTER TABLE MODRED.PESQUISA_WMDA ADD (PEWM_NR_MATCH_IMPORTADO NUMBER );
ALTER TABLE MODRED.PESQUISA_WMDA ADD (PEWM_TX_LOG CLOB );

COMMENT ON COLUMN MODRED.PESQUISA_WMDA.PEWM_NR_MATCH_WMDA IS 'Quantidade de match executadas no wmda.';
COMMENT ON COLUMN MODRED.PESQUISA_WMDA.PEWM_NR_MATCH_IMPORTADO IS 'Quantidade de match importado do wmda.';
COMMENT ON COLUMN MODRED.PESQUISA_WMDA.PEWM_TX_LOG IS 'Log de erro na importação do wmda.';

--###########################################################################################################

ALTER TABLE MATCH MODIFY (MATC_NR_ORDENACAO_WMDA NUMBER(*, 0) );

ALTER TABLE MODRED.PESQUISA_WMDA ADD (PEWM_TX_JSON CLOB );
COMMENT ON COLUMN MODRED.PESQUISA_WMDA.PEWM_TX_JSON IS 'Json com todas as informações importadas do wmda paciente.';

--###########################################################################################################

CREATE OR REPLACE VIEW "MODRED"."VW_TEMP_VALOR_DNA_NMDP" AS 
SELECT LOCU_ID, DNNM_TX_VALOR, DNNM_TX_NOME_GRUPO, 1 AS DNNM_IN_ATIVO FROM TEMP_VALOR_DNA_NMDP WITH READ ONLY;
COMMENT ON TABLE "MODRED"."VW_TEMP_VALOR_DNA_NMDP" IS 'VIEW DE RESULTADO DOS VALORES TEMPORARIOS DE DNA E NMDP';

ALTER TABLE MODRED.PESQUISA_WMDA ADD (PEWM_NR_VALOR_BLANK NUMBER );
COMMENT ON COLUMN MODRED.PESQUISA_WMDA.PEWM_NR_VALOR_BLANK IS 'Campos da pessquisa sem valores.';
