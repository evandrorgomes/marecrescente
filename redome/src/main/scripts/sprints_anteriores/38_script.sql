UPDATE MODRED.MOTIVO_CANCELAMENTO_BUSCA SET MOCB_IN_DESC_OBRIGATORIO = 1
WHERE MOCB_ID = 3;
COMMIT;

ALTER TABLE MODRED.CANCELAMENTO_BUSCA 
RENAME COLUMN CABU_OBSERVACAO TO CABU_TX_ESPECIFIQUE;


-- RECURSO PARA EXAME DE CLASSE 2, SOMENTE DISPONÍVEL AO MÉDICO.
INSERT INTO "MODRED"."RECURSO" (RECU_ID, RECU_TX_SIGLA, RECU_TX_DESCRICAO) 
VALUES ('126', 'BAIXAR_SOLICITACAO_EXAME_CLASSE2', 'Permite baixar os arquivos para solicitação dos exames de classe 2 do paciente.');
INSERT INTO "MODRED"."PERMISSAO" (RECU_ID, PERF_ID, PERM_IN_COM_RESTRICAO) VALUES ('126', '1', '0');
COMMIT;


INSERT INTO MODRED.CATEGORIA_NOTIFICACAO (CANO_ID, CANO_TX_DESCRICAO) VALUES ('4', 'Avaliação Paciente');
COMMIT;


INSERT INTO MODRED.RECURSO (RECU_ID, RECU_TX_SIGLA, RECU_TX_DESCRICAO) VALUES 
(127, 'TRANSFERENCIA_PACIENTE_CENTRO_AVALIADOR', 'Permite ao médico responsável transferir o paciente para outro centro avaliador');
INSERT INTO MODRED.PERMISSAO (PERF_ID, RECU_ID, PERM_IN_COM_RESTRICAO) VALUES (1, 127, 0);
COMMIT;


CREATE TABLE MODRED.PEDIDO_TRANSFERENCIA_CENTRO 
(
  PETC_ID NUMBER NOT NULL 
, PETC_DT_CRIACAO TIMESTAMP NOT NULL 
, PETC_DT_ATUALIZACAO TIMESTAMP NOT NULL 
, PETC_IN_TIPO NUMBER NOT NULL 
, PETC_IN_APROVADO NUMBER DEFAULT 0 NOT NULL 
, PACI_NR_RMR NUMBER NOT NULL 
, CETR_ID_ORIGEM NUMBER NOT NULL 
, CETR_ID_DESTINO NUMBER NOT NULL 
, USUA_ID NUMBER 
, CONSTRAINT PK_PETC_ID PRIMARY KEY 
  (
    PETC_ID 
  )
  ENABLE 
);

CREATE INDEX MODRED.IN_FK_PETC_CETR_DESTINO ON MODRED.PEDIDO_TRANSFERENCIA_CENTRO (CETR_ID_DESTINO);

CREATE INDEX MODRED.IN_FK_PETC_CETR_ORIGEM ON MODRED.PEDIDO_TRANSFERENCIA_CENTRO (CETR_ID_ORIGEM);

CREATE INDEX MODRED.IN_FK_PETC_PACI ON MODRED.PEDIDO_TRANSFERENCIA_CENTRO (PACI_NR_RMR);

CREATE INDEX MODRED.IN_FK_PETC_USUA ON MODRED.PEDIDO_TRANSFERENCIA_CENTRO (USUA_ID);

ALTER TABLE MODRED.PEDIDO_TRANSFERENCIA_CENTRO
ADD CONSTRAINT FK_PETC_CETR_DESTINO FOREIGN KEY
(
  CETR_ID_DESTINO 
)
REFERENCES MODRED.CENTRO_TRANSPLANTE
(
  CETR_ID 
)
ENABLE;

ALTER TABLE MODRED.PEDIDO_TRANSFERENCIA_CENTRO
ADD CONSTRAINT FK_PETC_CETR_ORIGEM FOREIGN KEY
(
  CETR_ID_ORIGEM 
)
REFERENCES MODRED.CENTRO_TRANSPLANTE
(
  CETR_ID 
)
ENABLE;

ALTER TABLE MODRED.PEDIDO_TRANSFERENCIA_CENTRO
ADD CONSTRAINT FK_PETC_PACI FOREIGN KEY
(
  PACI_NR_RMR 
)
REFERENCES MODRED.PACIENTE
(
  PACI_NR_RMR 
)
ENABLE;

ALTER TABLE MODRED.PEDIDO_TRANSFERENCIA_CENTRO
ADD CONSTRAINT FK_PETC_USUA FOREIGN KEY
(
  USUA_ID 
)
REFERENCES MODRED.USUARIO
(
  USUA_ID 
)
ENABLE;

COMMENT ON TABLE MODRED.PEDIDO_TRANSFERENCIA_CENTRO IS 'Está tabela armazena os pedidos de transferência entre Centro Avaliador e Centro Transplante.';
COMMENT ON COLUMN MODRED.PEDIDO_TRANSFERENCIA_CENTRO.PETC_ID IS 'Identificador único da tabela.';
COMMENT ON COLUMN MODRED.PEDIDO_TRANSFERENCIA_CENTRO.PETC_DT_CRIACAO IS 'Data de criação do registro.';
COMMENT ON COLUMN MODRED.PEDIDO_TRANSFERENCIA_CENTRO.PETC_DT_ATUALIZACAO IS 'Data de atualização do registro.';
COMMENT ON COLUMN MODRED.PEDIDO_TRANSFERENCIA_CENTRO.PETC_IN_TIPO IS 'Tipo de transferência.';
COMMENT ON COLUMN MODRED.PEDIDO_TRANSFERENCIA_CENTRO.PETC_IN_APROVADO IS 'Flag que indica se a transferência foi aprovado.';
COMMENT ON COLUMN MODRED.PEDIDO_TRANSFERENCIA_CENTRO.PACI_NR_RMR IS 'Chave de referência a tabela de paciente.';
COMMENT ON COLUMN MODRED.PEDIDO_TRANSFERENCIA_CENTRO.CETR_ID_ORIGEM IS 'Chave de referência a tabela de centro transplantador. Identifica a origem da transferência.';
COMMENT ON COLUMN MODRED.PEDIDO_TRANSFERENCIA_CENTRO.CETR_ID_DESTINO IS 'Chave de referência a tabela de centro transplantador. Identifica o destino da transferência.';
COMMENT ON COLUMN MODRED.PEDIDO_TRANSFERENCIA_CENTRO.USUA_ID IS 'Chave de referência a tabela de usuário. Identifica o usuário que aprovou a transferência.';

CREATE SEQUENCE MODRED.SQ_PETC_ID INCREMENT BY 1 MAXVALUE 9999999999999999999999999999 MINVALUE 1 CACHE 20 ORDER;


INSERT INTO MODRED.TIPO_TAREFA (TITA_ID, TITA_TX_DESCRICAO, TITA_IN_AUTOMATICA, TITA_NR_TEMPO_EXECUCAO, TARE_IN_SOMENTE_AGRUPADOR)
VALUES (85, 'PEDIDO_TRANSFERENCIA_CENTRO', 0, 3600, 0);
COMMIT;

ALTER TABLE EXAME 
ADD (EXAM_IN_EDITADO_AVALIADOR NUMBER );

COMMENT ON COLUMN EXAME.EXAM_IN_EDITADO_AVALIADOR IS '0 - Para exame não editado e 1-  para editado';

ALTER TABLE EXAME_AUD 
ADD (EXAM_IN_EDITADO_AVALIADOR NUMBER );


ALTER TABLE MODRED.PEDIDO_TRANSFERENCIA_CENTRO  
MODIFY (PETC_IN_APROVADO DEFAULT NULL NULL);

INSERT INTO MODRED.RECURSO (RECU_ID, RECU_TX_SIGLA, RECU_TX_DESCRICAO) VALUES 
(128, 'CONSULTAR_TRANSFERENCIA_PACIENTE_CENTRO_AVALIADOR', 'Permite ao médico do centro avaliador consultar as transferências para o centro especifico.');
INSERT INTO MODRED.PERMISSAO (PERF_ID, RECU_ID, PERM_IN_COM_RESTRICAO) VALUES 
(2, 128, 0);
COMMIT;

-- Notificação para o médico do paciente, ao ser aprovado.
INSERT INTO "MODRED"."TIPO_TAREFA" (TITA_ID, TITA_TX_DESCRICAO, TITA_IN_AUTOMATICA, TARE_IN_SOMENTE_AGRUPADOR) 
VALUES ('86', 'NOTIFICACAO_APROVACAO_TRANSFERENCIA_CENTRO', '0', '0');

INSERT INTO "MODRED"."TIPO_TAREFA" (TITA_ID, TITA_TX_DESCRICAO, TITA_IN_AUTOMATICA, TARE_IN_SOMENTE_AGRUPADOR) 
VALUES ('87', 'NOTIFICACAO_REAPROVACAO_TRANSFERENCIA_CENTRO', '0', '0');
COMMIT;

-- TORNANDO A COLUNA APROVADO NULLABLE (MOMENTO QUANDO AINDA NÃO HOUVE AVALIAÇÃO).
ALTER TABLE MODRED.PEDIDO_TRANSFERENCIA_CENTRO
MODIFY PETC_IN_APROVADO NULL;


INSERT INTO MODRED.RECURSO (RECU_ID, RECU_TX_SIGLA, RECU_TX_DESCRICAO) VALUES 
(129, 'DETALHE_TRANSFERENCIA_PACIENTE_CENTRO_AVALIADOR', 'Permite ao médico do centro avaliador visualizar os detalhes da transferência de um paciente.');
INSERT INTO MODRED.PERMISSAO (PERF_ID, RECU_ID, PERM_IN_COM_RESTRICAO) VALUES
(2, 129, 0);
COMMIT;

INSERT INTO MODRED.RECURSO (RECU_ID, RECU_TX_SIGLA, RECU_TX_DESCRICAO) VALUES
(130, 'RECUSAR_TRANSFERENCIA_PACIENTE_CENTRO_AVALIADOR', 'Permite ao médico do centro avaliador recusar a transferência de um paciente.');
INSERT INTO MODRED.PERMISSAO (PERF_ID, RECU_ID, PERM_IN_COM_RESTRICAO) VALUES
(2, 130, 0);
COMMIT;

ALTER TABLE MODRED.PEDIDO_TRANSFERENCIA_CENTRO 
ADD (PETC_TX_JUSTIFICATIVA VARCHAR2(255) );

COMMENT ON COLUMN MODRED.PEDIDO_TRANSFERENCIA_CENTRO.PETC_TX_JUSTIFICATIVA IS 'Justificativa do motiva da recusa do pedido de transferencia.';

INSERT INTO MODRED.CATEGORIA_NOTIFICACAO (CANO_ID, CANO_TX_DESCRICAO) VALUES 
('5', 'Transferência Paciente');

DELETE FROM MODRED.TIPO_TAREFA
WHERE TITA_ID = 87;
COMMIT;

INSERT INTO MODRED.RECURSO (RECU_ID, RECU_TX_SIGLA, RECU_TX_DESCRICAO) VALUES
(131, 'ACEITAR_TRANSFERENCIA_PACIENTE_CENTRO_AVALIADOR', 'Permite ao médico do centro avaliador aceitar a transferência de um paciente.');
INSERT INTO MODRED.PERMISSAO (PERF_ID, RECU_ID, PERM_IN_COM_RESTRICAO) VALUES
(2, 131, 0);
COMMIT;

DELETE FROM TIPO_TAREFA
WHERE TITA_ID = 86;
COMMIT;
ALTER TABLE EXAME_AUD 
ADD (EXAM_IN_EDITADO_AVALIADOR NUMBER );





ALTER TABLE CENTRO_TRANSPLANTE_USUARIO 
ADD (CETU_IN_RESPONSAVEL NUMBER DEFAULT 0 NOT NULL);

COMMENT ON COLUMN CENTRO_TRANSPLANTE_USUARIO.CETU_IN_RESPONSAVEL IS 'Se o usuario é responsável por um centro ou não';



ALTER TABLE MODRED.CENTRO_TRANSPLANTE_USUARIO 
DROP CONSTRAINT PK_CETU;

ALTER TABLE MODRED.CENTRO_TRANSPLANTE_USUARIO 
ADD (CETU_ID NUMBER );

COMMENT ON COLUMN MODRED.CENTRO_TRANSPLANTE_USUARIO.CETU_ID IS 'Chave primária para centro transplante usuario';


ALTER TABLE MODRED.CENTRO_TRANSPLANTE_USUARIO  
MODIFY (CETU_ID NOT NULL);

ALTER TABLE MODRED.CENTRO_TRANSPLANTE_USUARIO
ADD CONSTRAINT CETU_ID PRIMARY KEY 
(
  CETU_ID 
)
USING INDEX 
(
    CREATE UNIQUE INDEX IN_CETU_ID ON CENTRO_TRANSPLANTE_USUARIO (CETU_ID ASC) 
)
 ENABLE;
 

 CREATE SEQUENCE  "MODRED"."SQ_CETU_ID" INCREMENT BY 1 MAXVALUE 9999999999999999999999999999 MINVALUE 1 CACHE 20 ORDER;

-- TORNANDO STATUS NOT NULL, SE AINDA NÃO FOR.
ALTER TABLE MODRED.AVALIACAO
MODIFY AVAL_IN_STATUS NOT NULL;

-- TORNANDO AS AVALIAÇÕES NÃO REALIZADAS COM RESULTADO NULO.
UPDATE MODRED.AVALIACAO
SET AVAL_IN_RESULTADO = NULL
WHERE AVAL_IN_STATUS = 1 AND AVAL_IN_RESULTADO = 0;