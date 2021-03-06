ALTER TABLE MODRED.TENTATIVA_CONTATO_DOADOR 
DROP COLUMN COTE_ID CASCADE CONSTRAINTS;

ALTER TABLE MODRED.TENTATIVA_CONTATO_DOADOR 
DROP COLUMN TECD_DT_HORA_RETORNO CASCADE CONSTRAINTS;

ALTER TABLE MODRED.TENTATIVA_CONTATO_DOADOR  
MODIFY (TECD_DT_CRIACAO not NULL);

ALTER TABLE MODRED.TENTATIVA_CONTATO_DOADOR  
MODIFY (USUA_ID not NULL);

ALTER TABLE MODRED.TENTATIVA_CONTATO_DOADOR  
MODIFY (RETC_ID not NULL);

--Ajustes nos scripts da sprint passada Cintia 03/01
--renomeando coluna
ALTER TABLE MODRED.PRESCRICAO RENAME COLUMN PRES_DT_CRIACAO TO PRES_DT_CRICAO;

ALTER TABLE MODRED.PRESCRICAO  
MODIFY (PRES_DT_CRICAO DATE );

--removendo coluna de endereco
ALTER TABLE MODRED.HEMO_ENTIDADE
ADD ENCO_ID NUMBER NOT NULL;

COMMENT ON COLUMN MODRED.HEMO_ENTIDADE.ENCO_ID IS 'ID de identificação do endereço associado a uma hemoentidade.';

ALTER TABLE MODRED.HEMO_ENTIDADE
ADD CONSTRAINT FK_HEEN_ENCO FOREIGN KEY (ENCO_ID) REFERENCES MODRED.ENDERECO_CONTATO(ENCO_ID);

CREATE UNIQUE INDEX IN_FK_HEEN_ENCO ON MODRED.HEMO_ENTIDADE (ENCO_ID ASC);
COMMIT;

--alterando o tipo das colunas de data
ALTER TABLE MODRED.DISPONIBILIDADE  
MODIFY (DISP_DT_RESULTADO_WORKUP TIMESTAMP );

ALTER TABLE MODRED.DISPONIBILIDADE  
MODIFY (DISP_DT_COLETA TIMESTAMP );

-- criando foreign keys
DROP INDEX IN_FK_FUCT_CETR;
DROP INDEX IN_FK_FUCT_FUTR;

--renomeando tabela INFO_PREVIA para DISPONIBILIDADE_CENTRO_COLETA
drop table "MODRED"."DISPONIBILIDADE_CENTRO_COLETA" cascade constraints PURGE;
drop SEQUENCE MODRED.SQ_DICC_ID;

CREATE TABLE MODRED.INFO_PREVIA 
(
  INPR_ID NUMBER NOT NULL 
, PEWO_ID NUMBER NOT NULL 
, INPR_NR_INFO_CORRENTE NUMBER NOT NULL 
, INPR_TX_DESCRICAO VARCHAR2(80) NOT NULL 
, CONSTRAINT PK_INPR PRIMARY KEY 
  (
    INPR_ID 
  )
  USING INDEX 
  (
      CREATE UNIQUE INDEX IN_PK_INPR ON MODRED.INFO_PREVIA (INPR_ID ASC) 
  )
  ENABLE 
);

CREATE INDEX IN_FK_INPR_PEWO ON MODRED.INFO_PREVIA (PEWO_ID);

ALTER TABLE MODRED.INFO_PREVIA
ADD CONSTRAINT FK_INPR_PEWO FOREIGN KEY
(
  PEWO_ID 
)
REFERENCES MODRED.PEDIDO_WORKUP
(
  PEWO_ID 
)
ENABLE;

COMMENT ON TABLE MODRED.INFO_PREVIA IS 'Tabela que vai armazenar as informações de possíveis datas disponíveis de um centro de transplante.';

COMMENT ON COLUMN MODRED.INFO_PREVIA.INPR_ID IS 'Chave primária da tabela de info previa.';

COMMENT ON COLUMN MODRED.INFO_PREVIA.PEWO_ID IS 'Chave estrangeira da tabela de pedido_workup';

COMMENT ON COLUMN MODRED.INFO_PREVIA.INPR_NR_INFO_CORRENTE IS 'Número referente ao grupo de informações prévias de um pedido de workup.';

COMMENT ON COLUMN MODRED.INFO_PREVIA.INPR_TX_DESCRICAO IS 'Descrição da info previa';
CREATE SEQUENCE MODRED.SQ_INPR_ID INCREMENT BY 1 START WITH 1 MAXVALUE 9999999999999999999999999999 MINVALUE 1;

GRANT SELECT ON "MODRED"."INFO_PREVIA" TO RMODRED_READ;
GRANT INSERT, UPDATE ON "MODRED"."INFO_PREVIA" TO RMODRED_WRITE;
GRANT SELECT ON MODRED.SQ_INPR_ID TO RMODRED_WRITE;

CREATE OR REPLACE SYNONYM MODRED_APLICACAO.SQ_INPR_ID FOR MODRED.SQ_INPR_ID;
CREATE OR REPLACE SYNONYM MODRED_APLICACAO.INFO_PREVIA FOR MODRED.INFO_PREVIA;
COMMIT;
--FIM

-- RECURSO LISTA PENDENCIAS DE WORKUP
DELETE FROM MODRED.PERMISSAO WHERE RECU_ID = 39 AND PERF_ID = 1;
DELETE FROM MODRED.RECURSO WHERE RECU_ID = 39;
COMMIT;
-- FIM / RECURSO

--Alteração no modelo de dado para possibilitar a criação de mais de um pedido de workup para uma mesma prescrição Cintia 04/01
--deletando possiveis registros
DELETE FROM MODRED.PRESCRICAO;
DELETE FROM MODRED.PEDIDO_WORKUP;
DELETE FROM MODRED.SOLICITACAO;

--Alteração na tabela de PRESCRICAO
ALTER TABLE MODRED.PRESCRICAO 
ADD (PEWO_ID NUMBER NOT NULL);

ALTER TABLE MODRED.PRESCRICAO 
ADD (DOAD_NR_DMR NUMBER NOT NULL);

ALTER TABLE MODRED.PRESCRICAO 
ADD (PRES_DT_CRIACAO TIMESTAMP NOT NULL);

ALTER TABLE MODRED.PRESCRICAO 
ADD (USUA_ID NUMBER NOT NULL);

ALTER TABLE MODRED.PRESCRICAO
ADD CONSTRAINT FK_PRESC_DOAD FOREIGN KEY
(
  DOAD_NR_DMR 
)
REFERENCES MODRED.DOADOR
(
  DOAD_NR_DMR 
)
ENABLE;

ALTER TABLE MODRED.PRESCRICAO
ADD CONSTRAINT FK_PRESC_PEWO FOREIGN KEY
(
  PEWO_ID 
)
REFERENCES MODRED.PEDIDO_WORKUP
(
  PEWO_ID 
)
ENABLE;

ALTER TABLE MODRED.PRESCRICAO
ADD CONSTRAINT FK_PRESC_USUA FOREIGN KEY
(
  USUA_ID 
)
REFERENCES MODRED.USUARIO
(
  USUA_ID 
)
ENABLE;


CREATE INDEX MODRED.IN_FK_PRES_DOAD ON MODRED.PRESCRICAO (DOAD_NR_DMR ASC) ;
CREATE INDEX MODRED.IN_FK_PRES_USUA ON MODRED.PRESCRICAO (USUA_ID ASC);
CREATE INDEX MODRED.IN_FK_PRES_PEWO ON MODRED.PRESCRICAO (PEWO_ID ASC);

ALTER TABLE MODRED.PRESCRICAO 
DROP CONSTRAINT PK_PRES;

ALTER TABLE MODRED.PRESCRICAO 
DROP CONSTRAINT FK_PRES_SOLI;

ALTER TABLE MODRED.PRESCRICAO 
DROP COLUMN SOLI_ID;

ALTER TABLE MODRED.PRESCRICAO
ADD CONSTRAINT PK_PRESC PRIMARY KEY 
(
  PEWO_ID 
)
ENABLE;

CREATE UNIQUE INDEX IN_PK_PRES ON MODRED.PRESCRICAO (PEWO_ID ASC)

--Alteração na tabela de SOLICITACAO
DROP INDEX IN_FK_SOLI_USUA;

ALTER TABLE MODRED.SOLICITACAO 
DROP CONSTRAINT FK_SOLI_USUA;

ALTER TABLE MODRED.SOLICITACAO 
DROP COLUMN USUA_ID;

ALTER TABLE MODRED.SOLICITACAO 
DROP COLUMN SOLI_DT_CRIACAO;

ALTER TABLE MODRED.SOLICITACAO 
DROP COLUMN SOLI_NR_STATUS;

ALTER TABLE MODRED.SOLICITACAO 
MODIFY (SOLI_DT_CRIACAO NULL);

--Alteração na tabela de PEDIDO_WORKUP
ALTER TABLE MODRED.PEDIDO_WORKUP 
MODIFY (PEWO_DT_CRIACAO TIMESTAMP NULL);

DROP INDEX IN_FK_PEWO_CETR;

ALTER TABLE MODRED.PEDIDO_WORKUP 
DROP CONSTRAINT FK_PEWO_CETR;

ALTER TABLE MODRED.PEDIDO_WORKUP 
DROP COLUMN CETR_ID;


--Alteração na tabela de DISPONIBILIDADE_CENTRO_COLETA

DROP INDEX IN_FK_DICC_CETR;

ALTER TABLE MODRED.DISPONIBILIDADE_CENTRO_COLETA 
DROP CONSTRAINT FK_DICC_CETR;

ALTER TABLE MODRED.DISPONIBILIDADE_CENTRO_COLETA 
DROP COLUMN CETR_ID;

ALTER TABLE MODRED.DISPONIBILIDADE_CENTRO_COLETA 
ADD (CETR_ID NUMBER NOT NULL);

CREATE INDEX IN_FK_DICC_CETR ON MODRED.DISPONIBILIDADE_CENTRO_COLETA (CETR_ID ASC);

--FIM

--Adicionando coluna na tabela DISPONIBILIDADE Cintia 05/01
ALTER TABLE MODRED.DISPONIBILIDADE 
ADD (DISP_IN_CT_COLETA NUMBER(1) DEFAULT 0 NOT NULL);

COMMENT ON COLUMN MODRED.DISPONIBILIDADE.DISP_IN_CT_COLETA IS 'Indica se o Centro de transplante deseja fazer a coleta. 0-Não, 1-Sim.';
-- Fim

--Adicionando auditoria nas tabelas DISPONIBILIDADE e PEDIDO_WORKUP Cintia 05/01
drop table "MODRED"."DISPONIBILIDADE_AUD" cascade constraints PURGE;
drop table "MODRED"."PEDIDO_WORKUP_AUD" cascade constraints PURGE;

DROP SYNONYM MODRED_APLICACAO.DISPONIBILIDADE_AUD;
DROP SYNONYM MODRED_APLICACAO.PEDIDO_WORKUP_AUD;

--FIM



ALTER TABLE MODRED.TENTATIVA_CONTATO_DOADOR 
DROP COLUMN TECD_DT_AGENDAMENTO;

ALTER TABLE MODRED.TENTATIVA_CONTATO_DOADOR 
DROP COLUMN TECD_HR_INICIO_AGENDAMENTO;

ALTER TABLE MODRED.TENTATIVA_CONTATO_DOADOR 
DROP COLUMN TECD_HR_FIM_AGENDAMENTO;

ALTER TABLE MODRED.TENTATIVA_CONTATO_DOADOR RENAME 
DROP COLUMN TECD_DT_REALIZACAO_CONTATO;

-- RECURSO VISUALIZAR DISPONIBILIDADE PARA WORKUP
DELETE FROM MODRED.PERMISSAO WHERE RECU_ID = 43 AND PERF_ID = 1;
DELETE FROM MODRED.RECURSO WHERE RECU_ID = 43;
COMMIT;


-- REMOÇÃO DO RESPONSAVEL PELO PEDIDO DE WORKUP
ALTER TABLE MODRED.PEDIDO_WORKUP
DROP COLUMN USUA_ID CASCADE CONSTRAINTS;
