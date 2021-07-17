
--adicionando a permissão para o médico transplantador
INSERT INTO "MODRED"."PERMISSAO" (RECU_ID, PERF_ID, PERM_IN_COM_RESTRICAO ) VALUES ('53', '15', 0);
INSERT INTO "MODRED"."PERMISSAO" (RECU_ID, PERF_ID, PERM_IN_COM_RESTRICAO) VALUES ('50', '15', 0);
INSERT INTO "MODRED"."PERMISSAO" (RECU_ID, PERF_ID, PERM_IN_COM_RESTRICAO) VALUES ('52', '15', 0);
--retirando a permissão do médico comum
DELETE FROM MODRED.PERMISSAO WHERE RECU_ID = 50 AND PERF_ID = 1;

-- TORNANDO OS CAMPOS RESULTADO WORKUP COMO NÃO OBRIGATÓRIOS PARA OS CASOS DE CORDÃO INTERNACIONAL.
ALTER TABLE PRESCRICAO  
MODIFY (PRES_DT_RESULTADO_WORKUP_1 NULL);

ALTER TABLE PRESCRICAO  
MODIFY (PRES_DT_RESULTADO_WORKUP_2 NULL);

-- TORNANDO A FLAG SE DESEJA SER O CENTRO DE COLETA NÃO OBRIGATORIA
ALTER TABLE MODRED.DISPONIBILIDADE  
MODIFY (DISP_IN_CT_COLETA NULL);


INSERT INTO MODRED.TIPO_TAREFA (TITA_ID, TITA_TX_DESCRICAO, TITA_IN_AUTOMATICA, TITA_NR_TEMPO_EXECUCAO, TARE_IN_SOMENTE_AGRUPADOR)
VALUES(71, 'CADASTRAR_RESULTADO_WORKUP_INTERNACIONAL', 0, NULL, 0);

INSERT INTO MODRED.PERFIL (PERF_ID, PERF_TX_DESCRICAO, PERF_NR_ENTITY_STATUS)
VALUES (20, 'ANALISTA_WORKUP_INTERNACIONAL', 1);

INSERT INTO MODRED.RECURSO (RECU_ID, RECU_TX_SIGLA, RECU_TX_DESCRICAO)
VALUES (111,'CADASTRAR_RESULTADO_WORKUP_INTERNACIONAL','Permite ao analista workup internacional');
   
INSERT INTO MODRED.USUARIO (USUA_ID, USUA_TX_NOME, USUA_TX_USERNAME, USUA_TX_PASSWORD, USUA_IN_ATIVO, USUA_NR_ENTITY_STATUS, TRAN_ID, LABO_ID)
VALUES (20, 'ANALISTA_WORKUP_INTERNACIONAL', 'ANALISTA_WORKUP_INTERNACIONAL', '$2a$11$KMAfznbkZhx9mvESGy3.GewPWbSGQLVTnj5O0m7cIo5NrucDtcXT.', 1,1,NULL,NULL);


INSERT INTO MODRED.USUARIO_PERFIL (USUA_ID, PERF_ID) VALUES (20,20);

INSERT INTO MODRED.PERMISSAO (RECU_ID, PERF_ID, PERM_IN_COM_RESTRICAO) VALUES (111, 20, 0);

insert into MODRED.permissao (recu_id, perf_id, perm_in_com_restricao) values(49, 20, 0);
insert into MODRED.PERMISSAO ( RECU_ID, PERF_ID, PERM_IN_COM_RESTRICAO) VALUES (49, 9, 0);
insert into modred.permissao(recu_id, perf_id, perm_in_com_restricao) values (46,20,9);

--Incluindo tipo de tarefa de pedido de coleta internacional 
INSERT INTO "MODRED"."TIPO_TAREFA" (TITA_ID, TITA_TX_DESCRICAO, TITA_IN_AUTOMATICA, TARE_IN_SOMENTE_AGRUPADOR) VALUES ('72', 'PEDIDO_COLETA_INTERNACIONAL', '0', '0');
COMMIT;

insert into MODRED.permissao (recu_id, perf_id, perm_in_com_restricao) values(49, 20, 0);
COMMIT;

-- CRIANDO TIPO DE TAREFA PEDIDO WORKUP INTERNACIONAL
INSERT INTO "MODRED"."TIPO_TAREFA" (TITA_ID, TITA_TX_DESCRICAO, TITA_IN_AUTOMATICA, TARE_IN_SOMENTE_AGRUPADOR) VALUES ('73', 'PEDIDO_WORKUP_INTERNACIONAL', '0', '0');
COMMIT;

-- CRIANDO O RECURSO PARA ACESSO AO AGENDAMENTO DE WORKUP INTERNACIONAL
INSERT INTO "MODRED"."RECURSO" (RECU_ID, RECU_TX_SIGLA, RECU_TX_DESCRICAO) 
VALUES ('101', 'TRATAR_PEDIDO_WORKUP_INTERNACIONAL', 'Permite ao analista workup internacional agendar um pedido de workup internacional.');
COMMIT;

-- DAR PERMISSÃO AO ANALISTA WORKUP INTERNACIONAL PARA AGENDAR PEDIDO WORKUP INTERNACIONAL
INSERT INTO "MODRED"."PERMISSAO" (RECU_ID, PERF_ID, PERM_IN_COM_RESTRICAO) VALUES ('101', '20', '0');
COMMIT;

-- ATUALIZA O TIPO DE TAREFA (PEDIDO DE WORKUP NACIONAL OU INTERNACIONAL) DE ACORDO COM O TIPO DOADOR.
-- CORRIGINDO POSSÍVEIS PROBLEMAS CAUSADOS ENQUANTO A IMPLEMENTAÇÃO DO INTERNACIONAL NÃO É FINALIZADA.
-- TAREFA EM ABERTO
UPDATE MODRED.TAREFA 
SET TITA_ID = 73
WHERE TARE_ID IN (
    SELECT TAR.TARE_ID
    FROM MODRED.TAREFA TAR JOIN MODRED.PEDIDO_WORKUP PW ON(TAR.TARE_NR_RELACAO_ENTIDADE = PW.PEWO_ID)
    JOIN MODRED.SOLICITACAO SOL ON(PW.SOLI_ID = SOL.SOLI_ID)
    JOIN MODRED.MATCH MAT ON(SOL.MATC_ID = MAT.MATC_ID)
    JOIN MODRED.DOADOR DOAD ON(MAT.DOAD_ID = DOAD.DOAD_ID)
    WHERE TAR.TITA_ID = 30
    AND DOAD.DOAD_IN_TIPO = 1
);
COMMIT;

-- ATUALIZANDO AS JÁ ATRIBUÍDAS
UPDATE MODRED.TAREFA 
SET USUA_ID_RESPONSAVEL = 20
WHERE TITA_ID = 73 AND USUA_ID_RESPONSAVEL = 9;

-- DANDO PERMISSÃO PARA VISUALIZAR HEADER COMPLETO DO DOADOR
INSERT INTO "MODRED"."PERMISSAO" (RECU_ID, PERF_ID, PERM_IN_COM_RESTRICAO) VALUES ('44', '20', '0');
COMMIT;

INSERT INTO MODRED.RECURSO (RECU_ID, RECU_TX_SIGLA, RECU_TX_DESCRICAO) VALUES (102, 'TRATAR_PEDIDO_COLETA_INTERNACIONAL', 'Permiteao analista workup internacional agendar um pedido de coleta internacional.');
INSERT INTO MODRED.PERMISSAO (RECU_ID, PERF_ID, PERM_IN_COM_RESTRICAO) VALUES (102, 20, 0);
COMMIT;

ALTER TABLE MODRED.DISPONIBILIDADE_AUD
ADD (PECL_ID NUMBER );

ALTER TABLE MODRED.DISPONIBILIDADE 
ADD (PECL_ID NUMBER );

ALTER TABLE MODRED.DISPONIBILIDADE  
MODIFY (PEWO_ID NULL);

CREATE INDEX MODRED.IN_FK_DISP_PECL ON MODRED.DISPONIBILIDADE (PECL_ID);

ALTER TABLE MODRED.DISPONIBILIDADE
ADD CONSTRAINT FK_DISP_PECL FOREIGN KEY
(
  PECL_ID 
)
REFERENCES MODRED.PEDIDO_COLETA
(
  PECL_ID 
)
ENABLE;

COMMENT ON COLUMN MODRED.DISPONIBILIDADE.PECL_ID IS 'Chave estrangeira da tabela PEDIDO_COLETA.';

INSERT INTO MODRED.TIPO_TAREFA (TITA_ID, TITA_TX_DESCRICAO, TITA_IN_AUTOMATICA, TITA_NR_TEMPO_EXECUCAO, TARE_IN_SOMENTE_AGRUPADOR) 
VALUES (74, 'SUGERIR_DATA_WORKUP_INTERNACIONAL', 0, 3600, 0);
COMMIT;


DELETE FROM MODRED.PERMISSAO WHERE RECU_ID = 46 AND PERF_ID = 20;
COMMIT;


-- INSERINDO STATUS DO PEDIDO DE COLETA PARA QUANDO HOUVER NEGOCIAÇÃO COM CT E AS DATAS ESTIVEREM EM ANÁLISE.
INSERT INTO "MODRED"."STATUS_PEDIDO_COLETA" (STPC_ID, STPC_TX_DESCRICAO) VALUES ('6', 'Em Análise');
COMMIT;

-- RECURSO PARA CANCELAMENTO DE PEDIDO DE COLETA PELO CENTRO TRANSPLANTADOR
INSERT INTO "MODRED"."RECURSO" (RECU_ID, RECU_TX_SIGLA, RECU_TX_DESCRICAO) 
VALUES ('103', 'CANCELAR_PEDIDO_COLETA_CT', 'Permite ao médico transplantador cancelar um pedido de coleta.');
COMMIT;

-- PERMISSÃO PARA MÉDICO TRANSPLANTADOR CANCELAR O PEDIDO DE COLETA
INSERT INTO "MODRED"."PERMISSAO" (RECU_ID, PERF_ID, PERM_IN_COM_RESTRICAO) VALUES ('103', '15', '0');
COMMIT;

-- TABELA PARA GUARDAR OS MOTIVOS DE CANCELAMENTO DE PEDIDO DE COLETA
CREATE TABLE MODRED.MOTIVO_CANCELAMENTO_COLETA(
  MOCC_TX_CODIGO VARCHAR2(50) NOT NULL,
  MOCC_DESCRICAO VARCHAR2(200) NOT NULL,
  CONSTRAINT PK_MOCC PRIMARY KEY (MOCC_TX_CODIGO)
  USING INDEX (CREATE UNIQUE INDEX PK_MOCC ON MODRED.MOTIVO_CANCELAMENTO_COLETA (MOCC_TX_CODIGO ASC))
  ENABLE 
);
COMMENT ON TABLE MODRED.MOTIVO_CANCELAMENTO_COLETA IS 'Tabela de motivos para o cancelamento de um pedido de coleta.';
COMMENT ON COLUMN MODRED.MOTIVO_CANCELAMENTO_COLETA.MOCC_TX_CODIGO IS 'Chave primária da tabela. Existe um enum representando no back-end.';
COMMENT ON COLUMN MODRED.MOTIVO_CANCELAMENTO_COLETA.MOCC_DESCRICAO IS 'Descrição do motivo de cancelamento de um pedido de coleta.';

-- COLUNA PARA ASSOCIAÇÃO COM MOTIVO E PEDIDO COLETA
ALTER TABLE MODRED.PEDIDO_COLETA
ADD MOCC_TX_CODIGO VARCHAR2(50) NULL;

-- CONSTRAINT PARA REFERENCIA ENTRE MOTIVO CANCELAMENTO E PEDIDO DE COLETA.
ALTER TABLE MODRED.PEDIDO_COLETA
ADD CONSTRAINT FK_PECO_MOCC FOREIGN KEY(MOCC_TX_CODIGO)
REFERENCES MODRED.MOTIVO_CANCELAMENTO_COLETA(MOCC_TX_CODIGO)
ENABLE;


-- INSERINDO OS PRIMEIROS MOTIVOS DE CANCELAMENTO DE COLETA
INSERT INTO "MODRED"."MOTIVO_CANCELAMENTO_COLETA" (MOCC_TX_CODIGO, MOCC_DESCRICAO) VALUES ('CT_NAO_RESPONDE', 'Ocorre quando o analista tenta contato sem sucesso com o centro de transplante.');
INSERT INTO "MODRED"."MOTIVO_CANCELAMENTO_COLETA" (MOCC_TX_CODIGO, MOCC_DESCRICAO) VALUES ('SEM_DATAS_DISPONIVEIS', 'Ocorre quando o centro de transplante informa não possuir datas disponíveis dentro da range necessário para o procedimento.');
INSERT INTO "MODRED"."MOTIVO_CANCELAMENTO_COLETA" (MOCC_TX_CODIGO, MOCC_DESCRICAO) VALUES ('CT_RECUSOU_COLETA', 'Ocorre quando o centro de transplante solicita o cancelamento do pedido, indicando não estar mais apto a realizar o procedimento.');
INSERT INTO "MODRED"."MOTIVO_CANCELAMENTO_COLETA" (MOCC_TX_CODIGO, MOCC_DESCRICAO) VALUES ('BUSCA_FOI_CANCELADA', 'Ocorre quando a busca do paciente é cancelada e o pedido de coleta, se houver, for cancelado.');
COMMIT;

INSERT INTO MODRED.STATUS_PEDIDO_COLETA (STPC_ID, STPC_TX_DESCRICAO) VALUES (5, 'Aguardando CT');
COMMIT;

ALTER TABLE PRESCRICAO 
ADD (MEDI_ID NUMBER );

CREATE INDEX IN_FK_PRES_MEDI ON PRESCRICAO (MEDI_ID);

ALTER TABLE PRESCRICAO
ADD CONSTRAINT FK_PRES_MEDI FOREIGN KEY
(
  MEDI_ID 
)
REFERENCES MEDICO
(
  MEDI_ID 
)
ENABLE;

COMMENT ON COLUMN PRESCRICAO.MEDI_ID IS 'Identificador para o Médico Responsável';

INSERT INTO MODRED.MEDICO (MEDI_ID, MEDI_TX_CRM, MEDI_TX_NOME, USUA_ID, CETR_ID) VALUES (15, '15', 'MEDICO_TRANSPLANTADOR', 15, 3);

MERGE INTO MODRED.PRESCRICAO P
USING (SELECT PR.PRES_ID, M.MEDI_ID FROM MODRED.PRESCRICAO PR, MODRED.SOLICITACAO S, MODRED.MEDICO M 
        WHERE PR.SOLI_ID = S.SOLI_ID AND S.USUA_ID = M.USUA_ID) X
ON (P.PRES_ID = X.PRES_ID)
WHEN MATCHED THEN UPDATE SET P.MEDI_ID = X.MEDI_ID;
COMMIT;
