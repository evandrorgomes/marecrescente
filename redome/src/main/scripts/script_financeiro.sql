CREATE TABLE MODRED.STATUS_INVOICE 
(
  STIN_ID NUMBER NOT NULL 
, STIN_TX_DESCRICAO VARCHAR2(40 BYTE) NOT NULL 
, CONSTRAINT PK_STATUS_INVOICE PRIMARY KEY (STIN_ID)
  USING INDEX 
  (
      CREATE UNIQUE INDEX PK_STATUS_INVOICE ON STATUS_INVOICE (STIN_ID ASC) 
  )ENABLE 
);

COMMENT ON COLUMN MODRED.STATUS_INVOICE.STIN_ID IS 'Identificador sequencial da tabela.';
COMMENT ON COLUMN MODRED.STATUS_INVOICE.STIN_TX_DESCRICAO IS 'Descrição do status do invoice. (0 pago, 1 aberto e 2 cancelada)';

--###############################################################################################################

CREATE TABLE MODRED.TIPO_PEDIDO_ITEM_INVOICE 
(
  TIPI_ID NUMBER NOT NULL 
, TIPI_TX_DESCRICAO VARCHAR2(20 BYTE) NOT NULL 
, CONSTRAINT PK_TIPO_PEDIDO_ITEM_INVOICE PRIMARY KEY ( TIPI_ID )
  USING INDEX 
  (
      CREATE UNIQUE INDEX TIPO_PEDIDO_ITEM_INVOICE_PK ON TIPO_PEDIDO_ITEM_INVOICE (TIPI_ID ASC) 
  )
  ENABLE 
);

COMMENT ON COLUMN MODRED.TIPO_PEDIDO_ITEM_INVOICE.TIPI_ID IS 'Identificador do tipo de pedido de item invoice.';
COMMENT ON COLUMN MODRED.TIPO_PEDIDO_ITEM_INVOICE.TIPI_TX_DESCRICAO IS 'Descrição do tipo de pedido de item invoice. (0 amostra/ 1 exame/ 2 segunda fase)';

--###############################################################################################################

CREATE TABLE MODRED.INVOICE 
(
  INVO_ID NUMBER NOT NULL 
, INVO_NR_INVOICE NUMBER NOT NULL 
, INVO_DT_VENCIMENTO DATE 
, INVO_DT_CRIACAO TIMESTAMP(6) 
, INVO_DT_FATURAMENTO DATE 
, INVO_NR_LOTE NUMBER 
, STIN_ID NUMBER NOT NULL 
, CONSTRAINT PK_INVOICE PRIMARY KEY ( INVO_ID )
  USING INDEX 
  (
      CREATE UNIQUE INDEX PK_INVOICE ON INVOICE (INVO_ID ASC) 
  )
  ENABLE 
);

ALTER TABLE INVOICE
ADD CONSTRAINT FK_INVO_STIN FOREIGN KEY ( STIN_ID )
REFERENCES STATUS_INVOICE ( STIN_ID )
ENABLE;

COMMENT ON COLUMN MODRED.INVOICE.INVO_ID IS 'Identificador sequencial da tabela.';
COMMENT ON COLUMN MODRED.INVOICE.INVO_NR_INVOICE IS 'Número identificador do invoice.';
COMMENT ON COLUMN MODRED.INVOICE.INVO_DT_VENCIMENTO IS 'Data de vencimento do invoice.';
COMMENT ON COLUMN MODRED.INVOICE.INVO_DT_CRIACAO IS 'Data de criação do invoice.';
COMMENT ON COLUMN MODRED.INVOICE.INVO_DT_FATURAMENTO IS 'Data de faturamento do invoice.';
COMMENT ON COLUMN MODRED.INVOICE.INVO_NR_LOTE IS 'Número do lote do invoice.';
COMMENT ON COLUMN MODRED.INVOICE.STIN_ID IS 'Referência para a tabela de status.';

CREATE SEQUENCE MODRED.SQ_INVO_ID INCREMENT BY 1 MAXVALUE 9999999999999999999999999999 MINVALUE 1 CACHE 20;

--###############################################################################################################

CREATE TABLE ITEM_INVOICE 
(
  ITIN_ID NUMBER NOT NULL 
, ITIN_NR_RMR NUMBER NOT NULL 
, ITIN_IN_INDEVIDO NUMBER DEFAULT 0 
, ITIN_VL_ITEM_INVOICE NUMBER(6, 0) NOT NULL 
, TIPI_ID NUMBER NOT NULL 
, INVO_ID NUMBER NOT NULL 
, PEEX_ID NUMBER 
, PECL_ID NUMBER 
, CONSTRAINT PK_ITEM_INVOICE PRIMARY KEY ( ITIN_ID )
  USING INDEX 
  (
      CREATE UNIQUE INDEX PK_ITEM_INVOICE ON ITEM_INVOICE (ITIN_ID ASC) 
  )
  ENABLE 
); 

ALTER TABLE ITEM_INVOICE
ADD CONSTRAINT FK_ITIN_INVO FOREIGN KEY( INVO_ID )
REFERENCES INVOICE( INVO_ID )
ENABLE;

ALTER TABLE ITEM_INVOICE
ADD CONSTRAINT FK_ITIN_PECL FOREIGN KEY(  PECL_ID )
REFERENCES PEDIDO_COLETA(  PECL_ID )
ENABLE;

ALTER TABLE ITEM_INVOICE
ADD CONSTRAINT FK_ITIN_PEEX FOREIGN KEY(  PEEX_ID )
REFERENCES PEDIDO_EXAME(  PEEX_ID )
ENABLE;

ALTER TABLE ITEM_INVOICE
ADD CONSTRAINT FK_ITIN_TIPI FOREIGN KEY(  TIPI_ID )
REFERENCES TIPO_PEDIDO_ITEM_INVOICE(  TIPI_ID )
ENABLE;

COMMENT ON COLUMN ITEM_INVOICE.ITIN_ID IS 'Identificador sequencial da tabela.';
COMMENT ON COLUMN ITEM_INVOICE.ITIN_NR_RMR IS 'Número do RMR do paciente da invoice.	';
COMMENT ON COLUMN ITEM_INVOICE.ITIN_IN_INDEVIDO IS '0 - Para item invoice valido 1-  Para item invoice indevido.';
COMMENT ON COLUMN ITEM_INVOICE.ITIN_VL_ITEM_INVOICE IS 'Valor do item de invoice.';
COMMENT ON COLUMN ITEM_INVOICE.TIPI_ID IS 'Referência para a tabela de tipo de item de pedido invoice.';
COMMENT ON COLUMN ITEM_INVOICE.INVO_ID IS 'Referência para a tabela de invoice.';
COMMENT ON COLUMN ITEM_INVOICE.PEEX_ID IS 'Referência para a tabela de pedido de exame.';
COMMENT ON COLUMN ITEM_INVOICE.PECL_ID IS 'Referência para a tabela de pedido de coleta.';

CREATE SEQUENCE MODRED.SQ_ITIN INCREMENT BY 1 MAXVALUE 9999999999999999999999999999 MINVALUE 1 CACHE 20;

--###############################################################################################################

--##############################################################################################################################

INSERT INTO MODRED.STATUS_INVOICE VALUES (0, 'pago');
INSERT INTO MODRED.STATUS_INVOICE VALUES (1, 'aberto');
INSERT INTO MODRED.STATUS_INVOICE VALUES (2, 'cancelada');
COMMIT;

INSERT INTO MODRED.TIPO_PEDIDO_ITEM_INVOICE VALUES (0, 'Amostra');
INSERT INTO MODRED.TIPO_PEDIDO_ITEM_INVOICE VALUES (1, 'Exame IDM');
INSERT INTO MODRED.TIPO_PEDIDO_ITEM_INVOICE VALUES (2, 'Exame Segunda Fase');
INSERT INTO MODRED.TIPO_PEDIDO_ITEM_INVOICE VALUES (3, 'Coleta de Medula');
COMMIT;

--#################################################################################################################################