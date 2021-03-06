
CREATE TABLE MODRED.COURIER 
(
  COUR_ID NUMBER NOT NULL 
, COUR_TX_NOME VARCHAR2(40) NOT NULL 
, COUR_TX_CPF VARCHAR2(11) NOT NULL 
, COUR_TX_RG VARCHAR2(13) NOT NULL 
, CONSTRAINT PK_COUR PRIMARY KEY 
  (
    COUR_ID 
  )
  USING INDEX 
  (
      CREATE UNIQUE INDEX IN_PK_COUR ON MODRED.COURIER (COUR_ID ASC) 
  )
  ENABLE 
);

COMMENT ON TABLE MODRED.COURIER IS 'Tabela de courier';
COMMENT ON COLUMN MODRED.COURIER.COUR_ID IS 'Chave primária de Courier';
COMMENT ON COLUMN MODRED.COURIER.COUR_TX_NOME IS 'Nome do Courier';
COMMENT ON COLUMN MODRED.COURIER.COUR_TX_CPF IS 'CPF do Courier';
COMMENT ON COLUMN MODRED.COURIER.COUR_TX_RG IS 'RG do Courier';



ALTER TABLE MODRED.COURIER 
ADD (TRAN_ID NUMBER NOT NULL);

CREATE INDEX IN_FK_COUR_TRAN ON MODRED.COURIER (TRAN_ID);

ALTER TABLE MODRED.COURIER
ADD CONSTRAINT FK_COUR_TRAN FOREIGN KEY
(
  TRAN_ID 
)
REFERENCES MODRED.TRANSPORTADORA
(
  TRAN_ID 
)
ENABLE;

COMMENT ON COLUMN MODRED.COURIER.TRAN_ID IS 'Chave estrangeira para transportadora.
';


CREATE SEQUENCE "MODRED"."SQ_COUR_ID" MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER NOCYCLE;

-- CRIANDO TAREFA DE PEDIDO DE LOGÍSTICA PARA CORDÃO NACIONAL
INSERT INTO "MODRED"."TIPO_TAREFA" (TITA_ID, TITA_TX_DESCRICAO, TITA_IN_AUTOMATICA, TARE_IN_SOMENTE_AGRUPADOR) VALUES ('75', 'PEDIDO_LOGISTICA_CORDAO_NACIONAL', '0', '0');
COMMIT;

INSERT INTO "MODRED"."TIPO_TAREFA" (TITA_ID, TITA_TX_DESCRICAO, TITA_IN_AUTOMATICA, TARE_IN_SOMENTE_AGRUPADOR) VALUES ('76', 'RETIRADA_CORDAO_NACIONAL', '0', '0');
COMMIT;

INSERT INTO "MODRED"."TIPO_TAREFA" (TITA_ID, TITA_TX_DESCRICAO, TITA_IN_AUTOMATICA, TARE_IN_SOMENTE_AGRUPADOR) VALUES ('77', 'ENTREGA_LOGISTICA_CORDAO_NACIONAL', '0', '0');
COMMIT;


ALTER TABLE MODRED.PEDIDO_TRANSPORTE RENAME COLUMN PETR_DADOS_COURIER TO COUR_ID;
UPDATE MODRED.PEDIDO_TRANSPORTE  SET COUR_ID = NULL;
ALTER TABLE MODRED.PEDIDO_TRANSPORTE MODIFY (COUR_ID NUMBER );

CREATE INDEX IN_FK_PETR_COUR ON MODRED.PEDIDO_TRANSPORTE (COUR_ID);

ALTER TABLE MODRED.PEDIDO_TRANSPORTE
ADD CONSTRAINT FK_PETR_COUR FOREIGN KEY
(
  COUR_ID 
)
REFERENCES MODRED.COURIER
(
  COUR_ID 
)
ENABLE;

COMMENT ON COLUMN MODRED.PEDIDO_TRANSPORTE.COUR_ID IS 'Courier inserido pela transportadora.';



ALTER TABLE MODRED.CONTATO_TELEFONICO ADD (COUR_ID NUMBER );

CREATE INDEX IN_FK_COTE_COUR ON MODRED.CONTATO_TELEFONICO (COTE_TX_NOME);

ALTER TABLE MODRED.CONTATO_TELEFONICO
ADD CONSTRAINT FK_COTE_COUR FOREIGN KEY
(
  COUR_ID 
)
REFERENCES MODRED.COURIER
(
  COUR_ID 
)
ENABLE;

ALTER TABLE MODRED.CONTATO_TELEFONICO_AUD ADD (COUR_ID NUMBER );

INSERT INTO MODRED.COURIER(COUR_ID, COUR_TX_NOME, COUR_TX_RG, COUR_TX_CPF, TRAN_ID)
VALUES ( MODRED.SQ_COUR_ID.NEXTVAL, 'SEVERINO', '15456454', '20564841561', 1);

INSERT INTO "MODRED"."CONTATO_TELEFONICO" (COTE_ID, COTE_TX_NOME, COTE_NR_COD_INTER, COTE_NR_COD_AREA, COTE_IN_TIPO, COTE_NR_NUMERO, COTE_IN_PRINCIPAL, COTE_IN_EXCLUIDO, COUR_ID) 
VALUES (MODRED.SQ_COTE_ID.NEXTVAL, 'teste', '55', '21', '1', '21574600', '1', '0', '1');



update modred.configuracao set conf_tx_valor = 5 where conf_id = 'quantidadeMaximaArquivosPedidoTransporte';
VALUES (MODRED.SQ_COTE_ID.NEXTVAL, 'teste', '55', '21', '1', '21574600', '1', '0', '1');



-- INCLUINDO ENDEREÇO PARA BANCO DE SANGUE
ALTER TABLE MODRED.BANCO_SANGUE_CORDAO
ADD BASC_TX_ENDERECO VARCHAR2(1000);
-- PREENCHENDO COM UM TEXTO PROVISÓRIO
UPDATE MODRED.BANCO_SANGUE_CORDAO
SET BASC_TX_ENDERECO = 'RUA BSCUP, 1 - CENTRO/RJ';
-- E TORNANDO-O OBRIGATÓRIO
ALTER TABLE MODRED.BANCO_SANGUE_CORDAO
MODIFY BASC_TX_ENDERECO NOT NULL;

CREATE SEQUENCE "MODRED"."SQ_COUR_ID" MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER NOCYCLE;


-- ADICIONANDO CAMPO DE TELEFONE PARA BANCO DE SANGUE
ALTER TABLE BANCO_SANGUE_CORDAO
ADD BASC_TX_CONTATO VARCHAR2(1000);

-- ATUALIZANDO COM QUALQUER INFORMAÇÃO, SÓ PARA TORNAR CAMPO OBRIGATÓRIO
UPDATE BANCO_SANGUE_CORDAO
SET BASC_TX_CONTATO = '+55 (21) 5555-4444';
COMMIT;

-- TORNANDO O CAMPO OBRIGATÓRIO
ALTER TABLE BANCO_SANGUE_CORDAO
MODIFY BASC_TX_CONTATO VARCHAR2(1000) NOT NULL;

INSERT INTO "MODRED"."PERFIL" (PERF_ID, PERF_TX_DESCRICAO, PERF_NR_ENTITY_STATUS) VALUES ('21', 'WEBSERVICE', '1');
INSERT INTO "MODRED"."USUARIO" (USUA_ID, USUA_TX_NOME, USUA_TX_USERNAME, USUA_TX_PASSWORD, USUA_IN_ATIVO, USUA_NR_ENTITY_STATUS) VALUES ('21', 'WEBSERVICE_BRASILCORD', 'WEBSERVICE_BRASILCORD', '$2a$11$KMAfznbkZhx9mvESGy3.GewPWbSGQLVTnj5O0m7cIo5NrucDtcXT.', '1', '1');
commit;

INSERT INTO "MODRED"."RECURSO" (RECU_ID, RECU_TX_SIGLA, RECU_TX_DESCRICAO) VALUES ('104', 'CADASTRAR_CORDAO_NACIONAL', 'Permite o cadastro de cordão nacional');
INSERT INTO "MODRED"."PERMISSAO" (RECU_ID, PERF_ID, PERM_IN_COM_RESTRICAO) VALUES ('104', '21', '0');
commit;


ALTER TABLE MODRED.BANCO_SANGUE_CORDAO 
ADD (BASC_NR_ID_BRASILCORD NUMBER );

ALTER TABLE MODRED.DOADOR 
ADD (DOAD_VL_TOT_LINFOCITOS NUMBER(6) );

ALTER TABLE MODRED.DOADOR 
ADD (DOAD_VL_TOT_MONOCITOS NUMBER(6) );

ALTER TABLE MODRED.DOADOR 
ADD (DOAD_VL_TOT_GRANULOCITOS NUMBER(6) );

ALTER TABLE MODRED.DOADOR 
ADD (DOAD_VL_PER_HEMATOCRITOS NUMBER(6) );

ALTER TABLE MODRED.DOADOR 
ADD (DOAD_VL_TOT_ANTES NUMBER(6) );

ALTER TABLE MODRED.DOADOR 
ADD (DOAD_VL_TOT_DEPOIS NUMBER(6) );

ALTER TABLE MODRED.DOADOR 
ADD (DOAD_VL_TOT_REAL NUMBER(6) );

ALTER TABLE MODRED.DOADOR 
ADD (DOAD_VL_QUANT_TOTAL_TCN_ANTES NUMBER(6));



ALTER TABLE MODRED.DOADOR_AUD 
ADD (DOAD_VL_TOT_LINFOCITOS NUMBER(6) );

ALTER TABLE MODRED.DOADOR_AUD 
ADD (DOAD_VL_TOT_MONOCITOS NUMBER(6) );

ALTER TABLE MODRED.DOADOR_AUD
ADD (DOAD_VL_TOT_GRANULOCITOS NUMBER(6) );

ALTER TABLE MODRED.DOADOR_AUD
ADD (DOAD_VL_PER_HEMATOCRITOS NUMBER(6) );

ALTER TABLE MODRED.DOADOR_AUD
ADD (DOAD_VL_TOT_ANTES NUMBER(6) );

ALTER TABLE MODRED.DOADOR_AUD
ADD (DOAD_VL_TOT_DEPOIS NUMBER(6) );

ALTER TABLE MODRED.DOADOR_AUD
ADD (DOAD_VL_TOT_REAL NUMBER(6) );

ALTER TABLE MODRED.DOADOR_AUD
ADD (DOAD_VL_QUANT_TOTAL_TCN_ANTES NUMBER(6));



UPDATE "MODRED"."BANCO_SANGUE_CORDAO" SET BASC_NR_ID_BRASILCORD = '1' WHERE BASC_ID = 1;



ALTER TABLE MODRED.DOADOR 
ADD (DOAD_VL_TOT_PER_HEMATOCRITOS NUMBER(6) );


ALTER TABLE MODRED.DOADOR_AUD 
ADD (DOAD_VL_TOT_PER_HEMATOCRITOS NUMBER(6) );





COMMENT ON COLUMN MODRED.BANCO_SANGUE_CORDAO.BASC_NR_ID_BRASILCORD IS 'Id do banco no sistema brasilcord';

COMMENT ON COLUMN MODRED.DOADOR.DOAD_VL_TOT_LINFOCITOS IS 'Usado pelos doadores do tipo cordão para o total de linfócitos ';

COMMENT ON COLUMN MODRED.DOADOR.DOAD_VL_TOT_MONOCITOS IS 'Usado pelos doadores do tipo cordão para o total de 
 monocitos';

COMMENT ON COLUMN MODRED.DOADOR.DOAD_VL_TOT_GRANULOCITOS IS 'Usado pelos doadores do tipo cordão para o total de granulocitos';

COMMENT ON COLUMN DOADOR.DOAD_VL_PER_HEMATOCRITOS IS 'Usado pelos doadores do tipo cordão para o total de hematocritos';

COMMENT ON COLUMN MODRED.DOADOR.DOAD_VL_TOT_ANTES IS 'Usado pelos doadores do tipo cordão para o total de valor total antes';

COMMENT ON COLUMN MODRED.DOADOR.DOAD_VL_TOT_DEPOIS IS 'Usado pelos doadores do tipo cordão para o total de valor total depois';

COMMENT ON COLUMN MODRED.DOADOR.DOAD_VL_TOT_REAL IS 'Usado pelos doadores do tipo cordão para o total de valor total real
';
COMMENT ON COLUMN MODRED.DOADOR.DOAD_VL_QUANT_TOTAL_TCN_ANTES IS 'Usado pelos doadores do tipo cordão para o total de celulas nucleadas antes
';
COMMENT ON COLUMN DOADOR.DOAD_VL_TOT_PER_HEMATOCRITOS IS 'Usado pelos doadores do tipo cordão para o total de hematocritos';

CREATE TABLE MODRED.CONTROLE_JOB_BRASILCORD 
(
  COJB_ID NUMBER NOT NULL 
, COJB_DT_SINCRONIZACAO TIMESTAMP(6) NOT NULL 
, COJB_IN_SUCESSO CHAR(1 BYTE) NOT NULL 
, CONSTRAINT PK_COJB PRIMARY KEY 
  (
    COJB_ID 
  )
  USING INDEX 
  (
      CREATE UNIQUE INDEX IN_PK_COJB ON CONTROLE_JOB_BRASILCORD (COJB_ID ASC)
  )
  ENABLE 
);

COMMENT ON TABLE MODRED.CONTROLE_JOB_BRASILCORD IS 'Tabela para registro de sincronização ao serviço de liberação de cordão do BrasilCord';

COMMENT ON COLUMN MODRED.CONTROLE_JOB_BRASILCORD.COJB_ID IS 'Id do controle de acessos ao serviço de cordão do BSCUP';

COMMENT ON COLUMN MODRED.CONTROLE_JOB_BRASILCORD.COJB_DT_SINCRONIZACAO IS 'Data da última sincronização';

COMMENT ON COLUMN MODRED.CONTROLE_JOB_BRASILCORD.COJB_IN_SUCESSO IS 'Se houve sucessou falha na última sincronização';

CREATE SEQUENCE MODRED.SQ_COJB_ID INCREMENT BY 1 MAXVALUE 9999999999999999999999999999 MINVALUE 1 CACHE 20;




-- INCLUINDO RECURSO PARA AGENDAR TRANSPORTE MATERIAL
-- AUMENTANDO TAMANHO DO CAMPO COMENTÁRIO.
ALTER TABLE MODRED.RECURSO
MODIFY RECU_TX_DESCRICAO VARCHAR(200);

--ADICIONAR NOVO RECURSO PARA AGENDAMENTO MATERIAL POR PARTE DA TRANSPORTADORA 
INSERT INTO MODRED.RECURSO (RECU_ID, RECU_TX_SIGLA, RECU_TX_DESCRICAO)
VALUES(105, 'AGENDAR_TRANSPORTE_MATERIAL', 'Permite a um representante por parte da transportadora lidar com todo o processo de agendamento de transporte de material direcionado para ela.');

-- DANDO PERMISSÃO AO PERFIL COURIER
INSERT INTO PERMISSAO(RECU_ID, PERF_ID, PERM_IN_COM_RESTRICAO)
VALUES(105, 17, 0);
COMMIT;

-- TORNANDO DESTINO DA COLETA CAMPO NULLABLE.
-- DESTINO DA AMOSTRA NÃO PODE SER NOT NULL, POIS A ENTIDADE É CRIADA NA ENTREGA DA AMOSTRA PELO COURIER.
ALTER TABLE MODRED.RECEBIMENTO_COLETA
MODIFY DECO_ID NUMBER NULL;
INSERT INTO "MODRED"."USUARIO_PERFIL" (USUA_ID, PERF_ID) VALUES ('21', '21');


-- TORNANDO PEDIDO DE TRANSPORTE OBRIGATÓRIO PARA O ARQUIVO.
ALTER TABLE MODRED.ARQUIVO_PEDIDO_TRANSPORTE
MODIFY PETR_ID NUMBER NOT NULL;