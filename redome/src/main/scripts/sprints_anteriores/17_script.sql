-- HISTORIA 2
CREATE TABLE MODRED.HEMO_ENTIDADE
(
  HEEN_ID NUMBER NOT NULL, 
  HEEN_TX_NOME VARCHAR2(255) NOT NULL, 
  HEEN_IN_SELECIONAVEL NUMBER NOT NULL, 
  HEEN_TX_CLASSIFICACAO varchar2(2) NOT NULL, 
  CONSTRAINT PK_HEEN PRIMARY KEY (HEEN_ID)
  USING INDEX (
    CREATE UNIQUE INDEX PK_HEEN ON MODRED.HEMO_ENTIDADE (HEEN_ID ASC) 
  ) ENABLE
);

CREATE SEQUENCE MODRED.SQ_HEEN_ID INCREMENT BY 1 MAXVALUE 9999999999999999999999999999 MINVALUE 1 CACHE 20;

COMMENT ON TABLE MODRED.HEMO_ENTIDADE IS 'Tabela de informações sobre os hemocentros e hemonúcleos disponíveis no Redome.';
COMMENT ON COLUMN MODRED.HEMO_ENTIDADE.HEEN_ID IS 'Identificador sequencial para cada entidade.';
COMMENT ON COLUMN MODRED.HEMO_ENTIDADE.HEEN_TX_NOME IS 'Nome da entidade.';
COMMENT ON COLUMN MODRED.HEMO_ENTIDADE.HEEN_TX_CLASSIFICACAO IS 'Classificação da entidade. HC-Hemocentro, HN-Hemonúcleo.';
COMMENT ON COLUMN MODRED.HEMO_ENTIDADE.HEEN_IN_SELECIONAVEL IS 'Indica se a entidade é selecionável como receptora da amostra sanguínea do paciente ou doador.';
COMMIT;

-- ASSOCIANDO HEMO_ENTIDADE AO PEDIDO DE CONTATO, QUANDO ESTE É DE FASE 3.
ALTER TABLE MODRED.PEDIDO_CONTATO
ADD HEEN_ID NUMBER;

ALTER TABLE MODRED.PEDIDO_CONTATO
ADD CONSTRAINT FK_PECO_HEEN FOREIGN KEY (HEEN_ID) REFERENCES MODRED.HEMO_ENTIDADE(HEEN_ID);

CREATE UNIQUE INDEX IN_FK_PECO_HEEN ON MODRED.PEDIDO_CONTATO (HEEN_ID ASC);
COMMIT;


--História 3

CREATE TABLE MODRED.PEDIDO_WORKUP 
(
  PEWO_ID NUMBER NOT NULL 
, PEWO_DT_COLETA DATE 
, PEWO_DT_RESULTADO_WORKUP DATE 
, PEWO_NR_INFO_CORRENTE NUMBER 
, PEWO_NR_TIPO_UTILIZADO NUMBER 
, PEWO_DT_ULTIMO_STATUS DATE 
, CONSTRAINT PK_PEWO PRIMARY KEY 
  (
    PEWO_ID 
  )
  ENABLE 
);

COMMENT ON COLUMN MODRED.PEDIDO_WORKUP.PEWO_ID IS 'Id do pedido de workup';

COMMENT ON COLUMN MODRED.PEDIDO_WORKUP.PEWO_DT_COLETA IS 'Data da coleta agendada';

COMMENT ON COLUMN MODRED.PEDIDO_WORKUP.PEWO_DT_RESULTADO_WORKUP IS 'Data de resultado dos exames de workup agendada';

COMMENT ON COLUMN MODRED.PEDIDO_WORKUP.PEWO_NR_INFO_CORRENTE IS 'Numero de informacao corrente incrementar para agrupar disponibilidade do centro de coleta';

COMMENT ON COLUMN MODRED.PEDIDO_WORKUP.PEWO_NR_TIPO_UTILIZADO IS 'Tipo de data escolhida (1 - data da prescrição ou 2 - data da disponibilidade)';

COMMENT ON COLUMN MODRED.PEDIDO_WORKUP.PEWO_DT_ULTIMO_STATUS IS 'Data da ultima atualização de status';

CREATE SEQUENCE MODRED.SQ_PEWO_ID INCREMENT BY 1 MAXVALUE 9999999999999999999999999999 MINVALUE 1 CACHE 20;


CREATE TABLE MODRED.PRESCRICAO 
(
  PEWO_ID NUMBER NOT NULL 
, PRES_DT_COLETA_1 DATE 
, PRES_DT_COLETA_2 DATE 
, PRES_DT_RESULTADO_WORKUP_1 DATE 
, PRES_DT_RESULTADO_WORKUP_2 DATE 
, DOAD_NR_DMR NUMBER 
, PRES_DT_CRICAO DATE 
, USUA_ID NUMBER 
, CONSTRAINT PK_PRESC PRIMARY KEY 
  (
    PEWO_ID 
  )
  USING INDEX 
  (
      CREATE UNIQUE INDEX IN_PK_PRES ON MODRED.PRESCRICAO (PEWO_ID ASC)
  )
  ENABLE 
) ;

CREATE INDEX MODRED.IN_FK_PRES_DOAD ON MODRED.PRESCRICAO (DOAD_NR_DMR ASC) ;

CREATE INDEX MODRED.IN_FK_PRES_USUA ON MODRED.PRESCRICAO (USUA_ID ASC);

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

COMMENT ON COLUMN MODRED.PRESCRICAO.PEWO_ID IS 'Chave estrangeira de pedido workup e chave primaria de prescrição';

COMMENT ON COLUMN MODRED.PRESCRICAO.PRES_DT_COLETA_1 IS 'Primeira opção para data de coleta';

COMMENT ON COLUMN MODRED.PRESCRICAO.PRES_DT_COLETA_2 IS 'Segunda opção para data de coleta';

COMMENT ON COLUMN MODRED.PRESCRICAO.PRES_DT_RESULTADO_WORKUP_1 IS 'Primeira opção para data de resultado de exame de workup';

COMMENT ON COLUMN MODRED.PRESCRICAO.PRES_DT_RESULTADO_WORKUP_2 IS 'Segunda opção para data de resultado de exame de workup';

COMMENT ON COLUMN MODRED.PRESCRICAO.DOAD_NR_DMR IS 'Referencia para doadodr';

COMMENT ON COLUMN MODRED.PRESCRICAO.PRES_DT_CRICAO IS 'Data de criação da prescrição';

COMMENT ON COLUMN MODRED.PRESCRICAO.USUA_ID IS 'Referencia para usuario';

ALTER TABLE MODRED.PRESCRICAO  
MODIFY (PRES_DT_COLETA_1 NOT NULL);

ALTER TABLE MODRED.PRESCRICAO  
MODIFY (PRES_DT_COLETA_2 NOT NULL);

ALTER TABLE MODRED.PRESCRICAO  
MODIFY (PRES_DT_RESULTADO_WORKUP_1 NOT NULL);

ALTER TABLE MODRED.PRESCRICAO  
MODIFY (PRES_DT_RESULTADO_WORKUP_2 NOT NULL);

ALTER TABLE MODRED.PRESCRICAO  
MODIFY (DOAD_NR_DMR NOT NULL);

ALTER TABLE MODRED.PRESCRICAO  
MODIFY (PRES_DT_CRICAO NOT NULL);

ALTER TABLE MODRED.PRESCRICAO  
MODIFY (USUA_ID NOT NULL);

COMMENT ON TABLE MODRED.PRESCRICAO IS 'Armazenamento de prescrição médica para workup e coleta';


-- HISTORIA 2 - ASSOCIAÇÃO ENTRE HEMOENTIDADE E ENDERECO_CONTATO
TRUNCATE TABLE MODRED.HEMO_ENTIDADE;

ALTER TABLE MODRED.HEMO_ENTIDADE
ADD ENCO_ID NUMBER NOT NULL;

COMMENT ON COLUMN MODRED.HEMO_ENTIDADE.ENCO_ID IS 'ID de identificação do endereço associado a uma hemoentidade.';

ALTER TABLE MODRED.HEMO_ENTIDADE
ADD CONSTRAINT FK_HEEN_ENCO FOREIGN KEY (ENCO_ID) REFERENCES MODRED.ENDERECO_CONTATO(ENCO_ID);

CREATE UNIQUE INDEX IN_FK_HEEN_ENCO ON MODRED.HEMO_ENTIDADE (ENCO_ID ASC);
COMMIT;


ALTER TABLE MODRED.ENDERECO_CONTATO
ADD HEEN_ID NUMBER;

COMMENT ON COLUMN MODRED.ENDERECO_CONTATO.HEEN_ID IS 'ID de identificação da hemoentidade associada a este endereço.';

ALTER TABLE MODRED.ENDERECO_CONTATO
ADD CONSTRAINT FK_ENCO_HEEN FOREIGN KEY (HEEN_ID) REFERENCES MODRED.HEMO_ENTIDADE(HEEN_ID);

CREATE UNIQUE INDEX IN_FK_ENCO_HEEN ON MODRED.ENDERECO_CONTATO (HEEN_ID ASC);
COMMIT;


ALTER TABLE MODRED.ENDERECO_CONTATO_AUD
ADD HEEN_ID NUMBER;
COMMENT ON COLUMN MODRED.ENDERECO_CONTATO_AUD.HEEN_ID IS 'ID de identificação da hemoentidade associada a este endereço.';
COMMIT;


--PERMISSÃO PARA VISUALIZAR A LISTAGEM DE TRABALHO DE WORKUP
INSERT INTO "MODRED"."PERFIL" (PERF_ID, PERF_TX_DESCRICAO, PERF_NR_ENTITY_STATUS) VALUES ('9', 'ANALISTA_WORKUP', '1');
INSERT INTO "MODRED"."USUARIO" (USUA_ID, USUA_TX_NOME, USUA_TX_USERNAME, USUA_TX_PASSWORD, USUA_IN_ATIVO, USUA_NR_ENTITY_STATUS) VALUES ('9', 'ANALISTA WORKUP', 'ANALISTA_WORKUP', '$2a$11$KMAfznbkZhx9mvESGy3.GewPWbSGQLVTnj5O0m7cIo5NrucDtcXT.', '1', '1');
INSERT INTO "MODRED"."RECURSO" (RECU_ID, RECU_TX_SIGLA, RECU_TX_DESCRICAO) VALUES ('38', 'VISUALIZAR_LISTA_WORKUP', 'Permite visualizar a listagem de trabalho para agendamento de workup');
INSERT INTO "MODRED"."PERMISSAO" (RECU_ID, PERF_ID, PERM_IN_COM_RESTRICAO) VALUES ('38', '9', '0');



--Criação de tabelas iniciais da sprint história 4 -Queiroz 21/12/2017 10:44
CREATE TABLE MODRED.DISPONIBILIDADE 
(
  DISP_ID NUMBER NOT NULL 
, DISP_TX_DESCRICAO VARCHAR2(255) NOT NULL 
, DISP_DT_RESULTADO_WORKUP TIMESTAMP 
, DISP_DT_COLETA TIMESTAMP 
, PEWO_ID NUMBER NOT NULL 
, DISP_DT_CRIACAO TIMESTAMP NOT NULL 
, DISP_DT_ACEITE TIMESTAMP 
, CONSTRAINT PK_DISP PRIMARY KEY 
  (
    DISP_ID 
  )
  USING INDEX 
  (
      CREATE UNIQUE INDEX PK_DISP ON MODRED.DISPONIBILIDADE (DISP_ID ASC) 
  )
  ENABLE 
);

CREATE INDEX IN_FK_DISP_PEWO ON MODRED.DISPONIBILIDADE (PEWO_ID);

ALTER TABLE MODRED.DISPONIBILIDADE
ADD CONSTRAINT FK_DISP_PEWO FOREIGN KEY
(
  PEWO_ID 
)
REFERENCES MODRED.PEDIDO_WORKUP
(
  PEWO_ID 
)
ENABLE;

COMMENT ON TABLE MODRED.DISPONIBILIDADE IS 'Tabela com as disponibilidades sugeridas para workup.';

COMMENT ON COLUMN MODRED.DISPONIBILIDADE.DISP_ID IS 'Chave primária de disponibilidade';

COMMENT ON COLUMN MODRED.DISPONIBILIDADE.DISP_TX_DESCRICAO IS 'Descrição com o resumo das datas disponibilizadas.';

COMMENT ON COLUMN MODRED.DISPONIBILIDADE.DISP_DT_RESULTADO_WORKUP IS 'Nova sugestão de data de resultado dos exames de workup';

COMMENT ON COLUMN MODRED.DISPONIBILIDADE.DISP_DT_COLETA IS 'Nova sugestão de Data da coleta.';

COMMENT ON COLUMN MODRED.DISPONIBILIDADE.PEWO_ID IS 'Chave estrangeira da tabela PEDIDO_WORKUP.';

COMMENT ON COLUMN MODRED.DISPONIBILIDADE.DISP_DT_CRIACAO IS 'Data de criação da disponibilidade';

COMMENT ON COLUMN MODRED.DISPONIBILIDADE.DISP_DT_ACEITE IS 'Data que o centro de transplante sugeriu novas datas.';


CREATE TABLE MODRED.STATUS_PEDIDO_WORKUP 
(
  STPW_ID NUMBER NOT NULL 
, STPW_DESCRICAO VARCHAR2(30) NOT NULL 
, CONSTRAINT PK_STPW PRIMARY KEY 
  (
    STPW_ID 
  )
  USING INDEX 
  (
      CREATE UNIQUE INDEX PK_STPW ON MODRED.STATUS_PEDIDO_WORKUP (STPW_ID ASC) 
  )
  ENABLE 
);

COMMENT ON TABLE MODRED.STATUS_PEDIDO_WORKUP IS 'Tabela de status de pedido de workup.';

COMMENT ON COLUMN MODRED.STATUS_PEDIDO_WORKUP.STPW_ID IS 'Chave primária ta tabela de status do pedido de workup.';

COMMENT ON COLUMN MODRED.STATUS_PEDIDO_WORKUP.STPW_DESCRICAO IS 'Descrição do status';

CREATE TABLE MODRED.MOTIVO_CANCELAMENTO_WORKUP 
(
  MOCW_ID NUMBER NOT NULL 
, MOCW_DESCRICAO VARCHAR2(60) NOT NULL 
, CONSTRAINT PK_MOCW PRIMARY KEY 
  (
    MOCW_ID 
  )
  USING INDEX 
  (
      CREATE UNIQUE INDEX PK_MOCW ON MODRED.MOTIVO_CANCELAMENTO_WORKUP (MOCW_ID ASC) 
  )
  ENABLE 
);

COMMENT ON TABLE MODRED.MOTIVO_CANCELAMENTO_WORKUP IS 'Tabela de motivos de cancelamento de um workup';

COMMENT ON COLUMN MODRED.MOTIVO_CANCELAMENTO_WORKUP.MOCW_ID IS 'Chave primária da tabela de movo de cancelamento.';

COMMENT ON COLUMN MODRED.MOTIVO_CANCELAMENTO_WORKUP.MOCW_DESCRICAO IS 'Descrição do motivo de cancelamento de um pedido de workup.';


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

ALTER TABLE MODRED.PEDIDO_WORKUP 
ADD (STPW_ID NUMBER NOT NULL);

ALTER TABLE MODRED.PEDIDO_WORKUP 
ADD (MOCW_ID NUMBER );

CREATE INDEX IN_FK_PEWO_MOCW ON MODRED.PEDIDO_WORKUP (MOCW_ID);

CREATE INDEX IN_FK_PEWO_STPW ON MODRED.PEDIDO_WORKUP (STPW_ID);

ALTER TABLE MODRED.PEDIDO_WORKUP
ADD CONSTRAINT FK_PEWO_MOCW FOREIGN KEY
(
  MOCW_ID 
)
REFERENCES MODRED.MOTIVO_CANCELAMENTO_WORKUP
(
  MOCW_ID 
)
ENABLE;

ALTER TABLE MODRED.PEDIDO_WORKUP
ADD CONSTRAINT FK_PEWO_STPW FOREIGN KEY
(
  STPW_ID 
)
REFERENCES MODRED.STATUS_PEDIDO_WORKUP
(
  STPW_ID 
)
ENABLE;

COMMENT ON COLUMN MODRED.PEDIDO_WORKUP.STPW_ID IS 'Chave estrangeira do status de pedido de workup.';

COMMENT ON COLUMN MODRED.PEDIDO_WORKUP.MOCW_ID IS 'Motivo de cancelamento do pedido de workup';
--Fim scripts -Queiroz 21/12/2017 10:44

--Criação de sequences para disponibilidade e info_previa Queiroz 21/12/2017 12:02 
CREATE SEQUENCE MODRED.SQ_DISP_ID INCREMENT BY 1 START WITH 1 MAXVALUE 9999999999999999999999999999 MINVALUE 1;
CREATE SEQUENCE MODRED.SQ_INPR_ID INCREMENT BY 1 START WITH 1 MAXVALUE 9999999999999999999999999999 MINVALUE 1;
COMMIT;
--Fim scripts -Queiroz 21/12/2017 12:02

-- HISTORIA 2 - CRIAÇÃO DO USUÁRIO DE CONTATO FASE 3.
INSERT INTO MODRED.USUARIO (USUA_ID, USUA_TX_NOME, USUA_TX_USERNAME, USUA_TX_PASSWORD, USUA_IN_ATIVO, USUA_NR_ENTITY_STATUS) 
VALUES (8, 'ANALISTA_CONTATO_FASE3', 'CONTATO_FASE3', '$2a$11$KMAfznbkZhx9mvESGy3.GewPWbSGQLVTnj5O0m7cIo5NrucDtcXT.', '1', '1');
INSERT INTO MODRED.USUARIO_PERFIL (USUA_ID, PERF_ID) VALUES (8, 8);
COMMIT;

-- INSERINDO AS PERMISSÕES PARA O ANALISTA DE CONTATO FASE 3.
INSERT INTO MODRED.PERMISSAO (RECU_ID, PERF_ID, PERM_IN_COM_RESTRICAO) VALUES (31, 8, 0);
INSERT INTO MODRED.PERMISSAO (RECU_ID, PERF_ID, PERM_IN_COM_RESTRICAO) VALUES (23, 8, 0);
INSERT INTO MODRED.PERMISSAO (RECU_ID, PERF_ID, PERM_IN_COM_RESTRICAO) VALUES (34, 8, 0);
INSERT INTO MODRED.PERMISSAO (RECU_ID, PERF_ID, PERM_IN_COM_RESTRICAO) VALUES (27, 8, 0);
INSERT INTO MODRED.PERMISSAO (RECU_ID, PERF_ID, PERM_IN_COM_RESTRICAO) VALUES (8, 8, 0);
--Fim scripts -Queiroz 21/12/2017 12:02



--Funções do centro de transplante Queiroz 21/12/2017 10:19

CREATE TABLE MODRED.FUNCAO_TRANSPLANTE 
(
  FUTR_ID NUMBER NOT NULL 
, FUTR_DESCRICAO VARCHAR2(50) NOT NULL 
, CONSTRAINT PK_FUTR PRIMARY KEY 
  (
    FUTR_ID 
  )
  USING INDEX 
  (
      CREATE UNIQUE INDEX PK_FUTR ON MODRED.FUNCAO_TRANSPLANTE (FUTR_ID ASC) 
  )
  ENABLE 
);

COMMENT ON TABLE MODRED.FUNCAO_TRANSPLANTE IS 'Tabela de funções de um centro de transplante';

COMMENT ON COLUMN MODRED.FUNCAO_TRANSPLANTE.FUTR_ID IS 'Identificador da tabela.';

COMMENT ON COLUMN MODRED.FUNCAO_TRANSPLANTE.FUTR_DESCRICAO IS 'Descrição da função de um centro de transplante.';

insert into modred.FUNCAO_TRANSPLANTE(FUTR_ID, FUTR_DESCRICAO) VALUES(1,'AVALIADOR');
insert into modred.FUNCAO_TRANSPLANTE(FUTR_ID, FUTR_DESCRICAO) VALUES(2,'COLETADOR');
insert into modred.FUNCAO_TRANSPLANTE(FUTR_ID, FUTR_DESCRICAO) VALUES(3,'TRANSPLANTADOR');
COMMIT;

CREATE TABLE MODRED.FUNCAO_CENTRO_TRANSPLANTE 
(
  CETR_ID NUMBER NOT NULL 
, FUTR_ID NUMBER NOT NULL 
, CONSTRAINT PK_FUCT PRIMARY KEY 
  (
    CETR_ID 
  , FUTR_ID 
  )
  USING INDEX 
  (
      CREATE UNIQUE INDEX PK_FUCT ON MODRED.FUNCAO_CENTRO_TRANSPLANTE (CETR_ID ASC, FUTR_ID ASC) 
  )
  ENABLE 
);

COMMENT ON TABLE MODRED.FUNCAO_CENTRO_TRANSPLANTE IS 'Tabela de funções de um centro de transplante.';

COMMENT ON COLUMN MODRED.FUNCAO_CENTRO_TRANSPLANTE.CETR_ID IS 'Identificador da tabela de centro de transplante.';

COMMENT ON COLUMN MODRED.FUNCAO_CENTRO_TRANSPLANTE.FUTR_ID IS 'Identificador da tabela de funcao de um centro de transplante.';


insert into modred.FUNCAO_CENTRO_TRANSPLANTE (CETR_ID,FUTR_ID) values(2,1);
insert into modred.FUNCAO_CENTRO_TRANSPLANTE (CETR_ID,FUTR_ID) values(2,2);
insert into modred.FUNCAO_CENTRO_TRANSPLANTE (CETR_ID,FUTR_ID) values(2,3); 
insert into modred.FUNCAO_CENTRO_TRANSPLANTE (CETR_ID,FUTR_ID) values(3,3); 
insert into modred.FUNCAO_CENTRO_TRANSPLANTE (CETR_ID,FUTR_ID) values(4,3); 
insert into modred.FUNCAO_CENTRO_TRANSPLANTE (CETR_ID,FUTR_ID) values(5,3); 
insert into modred.FUNCAO_CENTRO_TRANSPLANTE (CETR_ID,FUTR_ID) values(6,3); 
insert into modred.FUNCAO_CENTRO_TRANSPLANTE (CETR_ID,FUTR_ID) values(7,3); 
insert into modred.FUNCAO_CENTRO_TRANSPLANTE (CETR_ID,FUTR_ID) values(8,3); 
insert into modred.FUNCAO_CENTRO_TRANSPLANTE (CETR_ID,FUTR_ID) values(9,3); 
insert into modred.FUNCAO_CENTRO_TRANSPLANTE (CETR_ID,FUTR_ID) values(10,3); 
insert into modred.FUNCAO_CENTRO_TRANSPLANTE (CETR_ID,FUTR_ID) values(11,3); 
insert into modred.FUNCAO_CENTRO_TRANSPLANTE (CETR_ID,FUTR_ID) values(12,3); 
insert into modred.FUNCAO_CENTRO_TRANSPLANTE (CETR_ID,FUTR_ID) values(13,3); 
insert into modred.FUNCAO_CENTRO_TRANSPLANTE (CETR_ID,FUTR_ID) values(14,3); 
insert into modred.FUNCAO_CENTRO_TRANSPLANTE (CETR_ID,FUTR_ID) values(15,3); 
insert into modred.FUNCAO_CENTRO_TRANSPLANTE (CETR_ID,FUTR_ID) values(16,3); 
insert into modred.FUNCAO_CENTRO_TRANSPLANTE (CETR_ID,FUTR_ID) values(17,3); 
insert into modred.FUNCAO_CENTRO_TRANSPLANTE (CETR_ID,FUTR_ID) values(18,3); 
insert into modred.FUNCAO_CENTRO_TRANSPLANTE (CETR_ID,FUTR_ID) values(19,3); 
insert into modred.FUNCAO_CENTRO_TRANSPLANTE (CETR_ID,FUTR_ID) values(20,3); 
insert into modred.FUNCAO_CENTRO_TRANSPLANTE (CETR_ID,FUTR_ID) values(21,3); 
insert into modred.FUNCAO_CENTRO_TRANSPLANTE (CETR_ID,FUTR_ID) values(22,3); 
insert into modred.FUNCAO_CENTRO_TRANSPLANTE (CETR_ID,FUTR_ID) values(23,3); 
insert into modred.FUNCAO_CENTRO_TRANSPLANTE (CETR_ID,FUTR_ID) values(24,3); 
insert into modred.FUNCAO_CENTRO_TRANSPLANTE (CETR_ID,FUTR_ID) values(25,3); 
insert into modred.FUNCAO_CENTRO_TRANSPLANTE (CETR_ID,FUTR_ID) values(26,3); 
insert into modred.FUNCAO_CENTRO_TRANSPLANTE (CETR_ID,FUTR_ID) values(27,3); 
insert into modred.FUNCAO_CENTRO_TRANSPLANTE (CETR_ID,FUTR_ID) values(28,3); 
insert into modred.FUNCAO_CENTRO_TRANSPLANTE (CETR_ID,FUTR_ID) values(29,3); 
insert into modred.FUNCAO_CENTRO_TRANSPLANTE (CETR_ID,FUTR_ID) values(30,3); 
insert into modred.FUNCAO_CENTRO_TRANSPLANTE (CETR_ID,FUTR_ID) values(31,3); 
insert into modred.FUNCAO_CENTRO_TRANSPLANTE (CETR_ID,FUTR_ID) values(32,3); 
insert into modred.FUNCAO_CENTRO_TRANSPLANTE (CETR_ID,FUTR_ID) values(33,3); 
insert into modred.FUNCAO_CENTRO_TRANSPLANTE (CETR_ID,FUTR_ID) values(34,3); 
insert into modred.FUNCAO_CENTRO_TRANSPLANTE (CETR_ID,FUTR_ID) values(35,3);
COMMIT;

/*ALTERAÇÃO DE PEDIDO DE WORKUP - FILIPE PAES*/

ALTER TABLE MODRED.PEDIDO_WORKUP 
ADD (SOLI_ID NUMBER NOT NULL);

CREATE INDEX IN_FK_PEWO_SOLI ON MODRED.PEDIDO_WORKUP (SOLI_ID);

ALTER TABLE MODRED.PEDIDO_WORKUP
ADD CONSTRAINT FK_PEWO_SOLI FOREIGN KEY
(
  SOLI_ID 
)
REFERENCES MODRED.SOLICITACAO
(
  SOLI_ID 
)
ENABLE;

COMMENT ON COLUMN MODRED.PEDIDO_WORKUP.SOLI_ID IS 'Referência para solicitação';

INSERT INTO "MODRED"."TIPO_SOLICITACAO" (TISO_ID, TISO_TX_DESCRICAO) VALUES ('3', 'WORKUP');
commit;

INSERT INTO "MODRED"."STATUS_PEDIDO_WORKUP" (STPW_ID, STPW_DESCRICAO) VALUES ('1', 'INICIAL');
INSERT INTO "MODRED"."STATUS_PEDIDO_WORKUP" (STPW_ID, STPW_DESCRICAO) VALUES ('2', 'EM ANÁLISE');
INSERT INTO "MODRED"."STATUS_PEDIDO_WORKUP" (STPW_ID, STPW_DESCRICAO) VALUES ('3', 'AGUARDANDO CT');
INSERT INTO "MODRED"."STATUS_PEDIDO_WORKUP" (STPW_ID, STPW_DESCRICAO) VALUES ('4', 'CANCELADO');
INSERT INTO "MODRED"."STATUS_PEDIDO_WORKUP" (STPW_ID, STPW_DESCRICAO) VALUES ('5', 'REALIZADO');
commit;

insert into MODRED.usuario_perfil (usua_id,perf_id) values (9,9);
COMMIT;


--Script de tipo de tarefa para o pedido de coleta do hemocentro Queiroz 26/12/2017
INSERT INTO MODRED.TIPO_TAREFA (TITA_ID, TITA_TX_DESCRICAO, TITA_IN_AUTOMATICA, TITA_NR_TEMPO_EXECUCAO) VALUES (29, 'PEDIDO_COLETA_HEMOCENTRO', 0, NULL);
COMMIT;
--Fim Script de tipo de tarefa para o pedido de coleta do hemocentro Queiroz 26/12/2017

--FILIPE PAES
INSERT INTO "MODRED"."TIPO_TAREFA" (TITA_ID, TITA_TX_DESCRICAO, TITA_IN_AUTOMATICA) VALUES ('30', 'PEDIDO_WORKUP', '0');
commit;

--Script de tipo de tarefa para follow up de workup Queiroz 26/12/2017
INSERT INTO MODRED.TIPO_TAREFA (TITA_ID, TITA_TX_DESCRICAO, TITA_IN_AUTOMATICA, TITA_NR_TEMPO_EXECUCAO) VALUES (31, 'NOTIFICACAO_WORKUP', 1, 432000);
COMMIT;
--Fim Script de tipo de tarefa para follow up de workup Queiroz 26/12/2017


-- PIZÃO: INCLUSÃO DO QUESTIONÁRIO DE FASE 3.
DELETE FROM MODRED.FORMULARIO WHERE FORM_ID = 2;

INSERT INTO MODRED.FORMULARIO VALUES (2, 2, 1, '
[
    { "id": "MotivoCadastramento", "descricao": "O que motivou o cadastramento no REDOME?", "tipo": "CHECKBUTTON",
      "opcoes": [ { "descricao": "Já sou doador de orgãos e tecidos", "valor": "DOADOR" },
                  { "descricao": "Doença na família", "valor": "DOENCA" }, 
                  { "descricao": "Curiosidade/Necessidade", "valor": "CURIOSIDADE" }, 
                  { "descricao": "Solidariedade", "valor": "SOLIDARIEDADE" }, 
                  { "descricao": "Outros", "valor": "OUTROS" } ]
    },
    { "id": "ConhecimentoPrograma", "descricao": "Como tomou conhecimento do programa de doação de medula óssea?", "tipo": "CHECKBUTTON",
      "opcoes": [ { "descricao": "Mídia", "valor": "MIDIA" },
                  { "descricao": "Campanha", "valor": "CAMPANHA" }, 
                  { "descricao": "Amigos e familiares", "valor": "AMIGOS" }, 
                  { "descricao": "Outros", "valor": "OUTROS" } ]
    },
    { "id": "SentimentoDoador", "descricao": "Qual é seu sentimento com a possibilidade de se tornar um doador?", "tipo": "CHECKBUTTON",
      "opcoes": [ { "descricao": "Felicidade", "valor": "FELICIDADE" },
                  { "descricao": "Medo", "valor": "MEDO" }, 
                  { "descricao": "Vontade de ajudar o próximo", "valor": "VONTADE" }]
    },
    { "id": "Confidencialidade", "descricao": "Está ciente da confidencialidade no processo de doação de CTH?", "tipo": "RADIOBUTTON",
      "opcoes": [ { "descricao": "Sim", "valor": "S" }, { "descricao": "Não", "valor": "N" } ]
    },
    { "id": "Anonimato", "descricao": "Qual é a sua opinião sobre o anonimato entre você e a pessoa para quem possivelmente faria a doação de MO?", "tipo": "RADIOBUTTON",
      "opcoes": [ { "descricao": "Concordo", "valor": "CONCORDO" },
                  { "descricao": "Não Concordo", "valor": "NAOCONCORDO" }, 
                  { "descricao": "Indiferente", "valor": "INDIFERENTE" } ]
    },
    { "id": "MomentoCadastro", "descricao": "Seu cadastro no REDOME foi realizado em qual momento?", "tipo": "CHECKBUTTON",
      "opcoes": [ { "descricao": "Durante uma campanha direcionada para um paciente", "valor": "CAMPANHAPACIENTE" },
                  { "descricao": "Doação de sangue", "valor": "DOACAOSANGUE" }, 
                  { "descricao": "Voluntariamente no Hemocantro solicitando o cadastro para o REDOME", "valor": "VOLUNTARIAMENTE" }, 
                  { "descricao": "Campanha do Hemocentro", "valor": "CAMPANHAHEMOCENTRO" }, 
                  { "descricao": "NA", "valor": "NA" } ]
    },
    { "id": "Duvidas", "descricao": "Você tem alguma dúvida sobre o processo de doação de Medula Óssea?", "tipo": "RADIOBUTTON",
      "opcoes": [ { "descricao": "Sim", "valor": "S" }, { "descricao": "Não", "valor": "N" } ]
    },
    { "id": "PreferenciaColeta", "descricao": "O doador informou preferência em realizar a coleta por:", "tipo": "RADIOBUTTON",
      "opcoes": [ { "descricao": "Afêrese", "valor": "AFERESE" }, { "descricao": "Punção", "valor": "PUNCAO" } ]
    },
    { "id": "Prosseguir", "descricao": "Deseja seguir com o processo de doação de CTH?", "tipo": "RADIOBUTTON",
      "opcoes": [ { "descricao": "Sim", "valor": "S" }, { "descricao": "Não", "valor": "N" } ]
    },
    { "id": "Religiao", "descricao": "Possui alguma religião?", "tipo": "RADIOBUTTON",
      "valorDefault" : "<1.Religiao>", 
      "opcoes": [ { "descricao": "Sim", "valor": "S" }, { "descricao": "Não", "valor": "N" } ],
      "dependentes": [ { "idPergunta": "QualReligiao", "valor": "S" }, { "idPergunta": "RestricaoReligiao", "valor": "S" } ]
    },
    { "id": "QualReligiao", "descricao": "Qual religião?", "tipo": "TEXT",
      "valorDefault" : "<1.QualReligiao>", 
      "possuiDependencia": "true",
      "tamanho": 100
    },'
);

UPDATE MODRED.FORMULARIO SET FORM_TX_FORMATO = FORM_TX_FORMATO || '
    { "id": "RestricaoReligiao", "descricao": "Essa religião restringe futuras doações?", "tipo": "RADIOBUTTON",
      "valorDefault" : "<1.RestricaoReligiao>", 
      "possuiDependencia": "true",
      "opcoes": [ { "descricao": "Sim", "valor": "S" }, { "descricao": "Não", "valor": "N" } ]
    },
    { "id": "AtividadeFisica", "descricao": "Pratica alguma atividade física?", "tipo": "RADIOBUTTON",
      "opcoes": [ { "descricao": "Sim", "valor": "S" }, { "descricao": "Não", "valor": "N" } ],
      "dependentes": [ { "idPergunta": "QualAtividadeFisica", "valor": "S" } ]
    },
    { "id": "QualAtividadeFisica", "descricao": "Qual?", "tipo": "TEXT",
      "possuiDependencia": "true",
      "tamanho": 100
    },
    { "id": "DoadorSangue", "descricao": "Doador de sangue?", "tipo": "RADIOBUTTON",
      "valorDefault" : "<1.DoadorSangue>", 
      "opcoes": [ { "descricao": "Sim", "valor": "S" }, { "descricao": "Não", "valor": "N" } ],
      "dependentes": [ { "idPergunta": "UltimaDoacao", "valor": "S" } ]
    },
    { "id": "UltimaDoacao", "descricao": "Última doação de sangue?", "tipo": "RADIOBUTTON",
      "valorDefault" : "<1.UltimaDoacao>", 
      "possuiDependencia": "true",
      "opcoes": [ { "descricao": "Seis meses a um ano", "valor": "A" }, { "descricao": "Mais de um ano", "valor": "B" } ]
    },
    { "id": "Cancer", "descricao": "Já teve algum tipo de câncer?", "tipo": "RADIOBUTTON",
      "valorDefault" : "<1.Cancer>", 
      "opcoes": [ { "descricao": "Sim", "valor": "S" }, { "descricao": "Não", "valor": "N" } ],
      "dependentes": [ { "idPergunta": "QuaisCancer", "valor": "S" } ]
    },
    { "id": "QuaisCancer", "descricao": "Informe quais, indicando o ano de ocorrência e o tratamento para cada um:", "tipo": "TEXTAREA",
      "valorDefault" : "<1.QuaisCancer>", 
      "possuiDependencia": "true",
      "tamanho": 4000
    },
    { "id": "Hepatite", "descricao": "Já teve algum tipo de hepatite?", "tipo": "RADIOBUTTON",
      "valorDefault" : "<1.Hepatite>", 
      "opcoes": [ { "descricao": "Sim", "valor": "S" }, { "descricao": "Não", "valor": "N" } ],
      "dependentes": [ { "idPergunta": "QuaisHepatite", "valor": "S" } ]
    },
    { "id": "QuaisHepatite", "descricao": "Informe quais tipos, indicando o ano de ocorrência de cada um:", "tipo": "TEXTAREA",
      "valorDefault" : "<1.QuaisHepatite>", 
      "possuiDependencia": "true",
      "tamanho": 4000
    },
    { "id": "Infeccao", "descricao": "Já teve algum tipo de infecção?", "tipo": "RADIOBUTTON",
      "valorDefault" : "<1.Infeccao>", 
      "opcoes": [ { "descricao": "Sim", "valor": "S" }, { "descricao": "Não", "valor": "N" } ],
      "dependentes": [ { "idPergunta": "QuaisInfeccao", "valor": "S" } ]
    },
    { "id": "QuaisInfeccao", "descricao": "Informe quais, indicando o ano de ocorrência de cada uma:", "tipo": "TEXTAREA",
      "valorDefault" : "<1.QuaisInfeccao>", 
      "possuiDependencia": "true",
      "tamanho": 4000
    },
    { "id": "DST", "descricao": "Já teve alguma DST (Doença Sexualmente Transmissível)? (HIV, HTLV, Sífilis, Herpes ou outras)", "tipo": "RADIOBUTTON",
      "valorDefault" : "<1.DST>", 
      "opcoes": [ { "descricao": "Sim", "valor": "S" }, { "descricao": "Não", "valor": "N" } ],
      "dependentes": [ { "idPergunta": "QuaisDST", "valor": "S" } ]
    },
    { "id": "QuaisDST", "descricao": "Quais?", "tipo": "CHECKBUTTON",
      "valorDefault" : "<1.QuaisDST>", 
      "opcoes": [ { "descricao": "HIV", "valor": "HIV" }, { "descricao": "HTLV", "valor": "HTLV" }, { "descricao": "Sífilis", "valor": "SIFILIS" }, { "descricao": "Herpes", "valor": "HERPES" }, { "descricao": "Outra(s)", "valor": "OUTRAS" } ],
      "dependentes": [ { "idPergunta": "OutrasDST", "valor": "OUTRAS" } ]
    },'
WHERE FORM_ID = 2;

UPDATE MODRED.FORMULARIO SET FORM_TX_FORMATO = FORM_TX_FORMATO || '
    { "id": "OutrasDST", "descricao": "Informe quais outras, indicando o ano e o tratamento de cada uma:", "tipo": "TEXTAREA",
      "valorDefault" : "<1.OutrasDST>", 
      "possuiDependencia": "true",
      "tamanho": 4000
    },
    { "id": "Alergia", "descricao": "Já teve alguma alergia?", "tipo": "RADIOBUTTON",
      "valorDefault" : "<1.Alergia>", 
      "opcoes": [ { "descricao": "Sim", "valor": "S" }, { "descricao": "Não", "valor": "N" } ],
      "dependentes": [ { "idPergunta": "QuaisAlergia", "valor": "S" } ]
    },
    { "id": "QuaisAlergia", "descricao": "Informe quais alergias:", "tipo": "TEXTAREA",
      "valorDefault" : "<1.QuaisAlergia>", 
      "possuiDependencia": "true",
      "tamanho": 4000
    },
    { "id": "Gestacao", "descricao": "Já teve gestações?", "tipo": "RADIOBUTTON",
      "valorDefault" : "<1.Gestacao>", 
      "opcoes": [ { "descricao": "Sim", "valor": "S" }, { "descricao": "Não", "valor": "N" } ],
      "dependentes": [ { "idPergunta": "TipoGestacao", "valor": "S" } ]
    },
    { "id": "TipoGestacao", "descricao": "Gestações de que tipos?", "tipo": "CHECKBUTTON",
      "valorDefault" : "<1.TipoGestacao>", 
      "possuiDependencia": "true",
      "opcoes": [ { "descricao": "Parto Normal", "valor": "NORMAL" }, { "descricao": "Parto Cesária", "valor": "CESARIA" } ],
      "dependentes": [ { "idPergunta": "GestacaoNormal", "valor": "NORMAL" }, { "idPergunta": "GestacaoCesaria", "valor": "CESARIA" } ]
    },
    { "id": "GestacaoNormal", "descricao": "Informe os anos e quantidade de partos normais:", "tipo": "TEXTAREA",
      "valorDefault" : "<1.GestacaoNormal>", 
      "possuiDependencia": "true",
      "tamanho": 4000
    },
    { "id": "GestacaoCesaria", "descricao": "Informe os anos e quantidade de partos Cesária:", "tipo": "TEXTAREA",
      "valorDefault" : "<1.GestacaoCesaria>", 
      "possuiDependencia": "true",
      "tamanho": 4000
    },
    { "id": "Aborto", "descricao": "Já teve algum aborto?", "tipo": "RADIOBUTTON",
      "valorDefault" : "<1.Aborto>", 
      "opcoes": [ { "descricao": "Sim", "valor": "S" }, { "descricao": "Não", "valor": "N" } ],
      "dependentes": [ { "idPergunta": "QuaisAborto", "valor": "S" } ]
    },
    { "id": "QuaisAborto", "descricao": "Informe os anos e a causa dos abortos:", "tipo": "TEXTAREA",
      "valorDefault" : "<1.QuaisAborto>", 
      "possuiDependencia": "true",
      "tamanho": 4000
    },
    { "id": "Cirurgia", "descricao": "Já realizou alguma cirurgia?", "tipo": "RADIOBUTTON",
      "valorDefault" : "<1.Cirurgia>", 
      "opcoes": [ { "descricao": "Sim", "valor": "S" }, { "descricao": "Não", "valor": "N" } ],
      "dependentes": [ { "idPergunta": "QuaisCirurgia", "valor": "S" } ]
    },
    { "id": "QuaisCirurgia", "descricao": "Informe quais cirurgias e o respectivo ano:", "tipo": "TEXTAREA",
      "valorDefault" : "<1.QuaisCirurgia>", 
      "possuiDependencia": "true",
      "tamanho": 4000
    },
    { "id": "Tatuagem", "descricao": "Já fez alguma tatuagem ou maquiagem definitiva?", "tipo": "RADIOBUTTON",
      "valorDefault" : "<1.Tatuagem>", 
      "opcoes": [ { "descricao": "Sim", "valor": "S" }, { "descricao": "Não", "valor": "N" } ],
      "dependentes": [ { "idPergunta": "UltimaTatuagem", "valor": "S" } ]
    },
    { "id": "UltimaTatuagem", "descricao": "Em que ano foi a última?", "tipo": "TEXT",
      "valorDefault" : "<1.UltimaTatuagem>", 
      "possuiDependencia": "true",
      "tamanho": 10
    },
    { "id": "Transfusao", "descricao": "Já fez alguma transfusão de sangue, plaquetas, plasma ou outros?", "tipo": "RADIOBUTTON",
      "valorDefault" : "<1.Transfusao>", 
      "opcoes": [ { "descricao": "Sim", "valor": "S" }, { "descricao": "Não", "valor": "N" } ],
      "dependentes": [ { "idPergunta": "QuaisTransfusao", "valor": "S" } ]
    },'
WHERE FORM_ID = 2;

UPDATE MODRED.FORMULARIO SET FORM_TX_FORMATO = FORM_TX_FORMATO || '
    { "id": "QuaisTransfusao", "descricao": "Informe quais transfusões e o respectivo ano e motivo:", "tipo": "TEXTAREA",
      "valorDefault" : "<1.QuaisTransfusao>", 
      "possuiDependencia": "true",
      "tamanho": 4000
    },
    { "id": "Medicamento", "descricao": "Faz uso de algum medicamento?", "tipo": "RADIOBUTTON",
      "valorDefault" : "<1.Medicamento>", 
      "opcoes": [ { "descricao": "Sim", "valor": "S" }, { "descricao": "Não", "valor": "N" } ],
      "dependentes": [ { "idPergunta": "QuaisMedicamento", "valor": "S" } ]
    },
    { "id": "QuaisMedicamento", "descricao": "Informe quais medicamentos faz uso:", "tipo": "TEXTAREA",
      "valorDefault" : "<1.QuaisMedicamento>", 
      "possuiDependencia": "true",
      "tamanho": 4000
    },
    { "id": "Tratamento", "descricao": "Já fez algum tratamento de saúde (colonoscopia/rinoscopia/endoscopia)?", "tipo": "RADIOBUTTON",
      "valorDefault" : "<1.Tratamento>", 
      "opcoes": [ { "descricao": "Sim", "valor": "S" }, { "descricao": "Não", "valor": "N" } ],
      "dependentes": [ { "idPergunta": "QuaisTratamento", "valor": "S" } ]
    },
    { "id": "QuaisTratamento", "descricao": "Informe quais tratamentos e o respectivo ano:", "tipo": "TEXTAREA",
      "valorDefault" : "<1.QuaisTratamento>", 
      "possuiDependencia": "true",
      "tamanho": 4000
    },
    { "id": "Vacinas", "descricao": "Tomou vacinas recentemente?", "tipo": "RADIOBUTTON",
      "opcoes": [ { "descricao": "Sim", "valor": "S" }, { "descricao": "Não", "valor": "N" } ],
      "dependentes": [ { "idPergunta": "QuaisVacinas", "valor": "S" } ]
    },
    { "id": "QuaisVacinas", "descricao": "Informe quais vacinas e a respectiva data:", "tipo": "TEXTAREA",
      "possuiDependencia": "true",
      "tamanho": 4000
    },
    { "id": "Viagens", "descricao": "Viajou recentemente para áreas endêmicas (malária/febre amarela)?", "tipo": "RADIOBUTTON",
      "opcoes": [ { "descricao": "Sim", "valor": "S" }, { "descricao": "Não", "valor": "N" } ],
      "dependentes": [ { "idPergunta": "QuaisViagens", "valor": "S" } ]
    },
    { "id": "QuaisViagens", "descricao": "Informe quais destinos o respectivo ano das viagens:", "tipo": "TEXTAREA",
      "possuiDependencia": "true",
      "tamanho": 4000
    },
    { "id": "DisponibilidadeViagem", "descricao": "Tem disponibilidade para viajar no ato da coleta de Medula Óssea caso não possa ser em seu estado?", "tipo": "RADIOBUTTON",
      "valorDefault" : "<1.DisponibilidadeViagem>", 
      "opcoes": [ { "descricao": "SIM", "valor": "S" }, { "descricao": "Não", "valor": "N" } ],
      "dependentes": [ { "idPergunta": "JustificativaViagem", "valor": "OUTROS" } ]
    },
    { "id": "JustificativaViagem", "descricao": "Qual o motivo da impossibilidade?", "tipo": "TEXTAREA",
      "valorDefault" : "<1.JustificativaViagem>", 
      "possuiDependencia": "true",
      "tamanho": 4000
    },'
WHERE FORM_ID = 2;

UPDATE MODRED.FORMULARIO SET FORM_TX_FORMATO = FORM_TX_FORMATO || '
    { "id": "FormaContato", "descricao": "Qual a melhor forma de contato?", "tipo": "CHECKBUTTON",
      "valorDefault" : "<1.FormaContato>", 
      "opcoes": [ { "descricao": "SMS", "valor": "SMS" }, { "descricao": "Telegrama", "valor": "TELEGRAMA" }, { "descricao": "E-Mail", "valor": "EMAIL" }, { "descricao": "Telefone", "valor": "TELEFONE" }, { "descricao": "Indiferente", "valor": "Indiferente" }, { "descricao": "Outros", "valor": "OUTROS" } ],
      "dependentes": [ { "idPergunta": "OutrosContato", "valor": "OUTROS" } ]
    },
    { "id": "OutrosContato", "descricao": "Quais outro tipo de contato:", "tipo": "TEXTAREA",
      "valorDefault" : "<1.OutrosContato>", 
      "possuiDependencia": "true",
      "tamanho": 4000
    },
    { "id": "PeriodoContato", "descricao": "Qual a melhor período para contato?", "tipo": "CHECKBUTTON",
      "valorDefault" : "<1.PeriodoContato>", 
      "opcoes": [ { "descricao": "Manhã", "valor": "M" }, { "descricao": "Tarde", "valor": "T" }, { "descricao": "Noite", "valor": "N" } ]
    }
  ]'
WHERE FORM_ID = 2;

COMMIT;

-- ATUALIZANDO TEMPLATE DO FORMULÁRIO DE FASE 2
DELETE FROM MODRED.RESPOSTA_FORMULARIO_DOADOR WHERE FODO_ID IN (SELECT FODO_ID FROM MODRED.FORMULARIO_DOADOR WHERE FORM_ID = 1);
DELETE FROM MODRED.FORMULARIO_DOADOR WHERE FORM_ID = 1;
DELETE FROM MODRED.FORMULARIO WHERE FORM_ID = 1;

INSERT INTO MODRED.FORMULARIO VALUES (1, 1, 1, '
[
    { "id": "LembraREDOME", "descricao": "Você lembra de ter se cadastrado como doador do REDOME?", "tipo": "RADIOBUTTON",
      "opcoes": [ { "descricao": "Sim", "valor": "S" }, { "descricao": "Não", "valor": "N" } ]
    },
    { "id": "Doncas", "descricao": "Você apresenta ou apresentou alguma destas doenças?", "tipo": "CHECKBUTTON",
      "opcoes": [ { "descricao": "Câncer", "valor": "CANCER" },
                  { "descricao": "Hepatite", "valor": "HEPATITE" }, 
                  { "descricao": "Tuberculose", "valor": "TUBERCULOSE" }, 
                  { "descricao": "HIV", "valor": "HIV" } ],
      "dependentes": [ { "idPergunta": "QuaisCancer", "valor": "CANCER" },
                       { "idPergunta": "QuaisHepatite", "valor": "HEPATITE" },
	        { "idPergunta": "QuaisTuberculose", "valor": "Tuberculose" } ]
    },
    { "id": "QuaisCancer", "descricao": "Informe quais tipos de câncer, indicando o ano de ocorrência e o tratamento:", "tipo": "TEXTAREA",
      "possuiDependencia": "true",
      "tamanho": 4000
    },
    { "id": "QuaisHepatite", "descricao": "Informe quais tipos de hepatite, indicando o ano de ocorrência e o tratamento:", "tipo": "TEXTAREA",
      "possuiDependencia": "true",
      "tamanho": 4000
    },
    { "id": "QuaisTuberculose", "descricao": "Informe o ano de ocorrência e o tratamento feito para tuberculose:", "tipo": "TEXTAREA",
      "possuiDependencia": "true",
      "tamanho": 4000
    },
    { "id": "Sexo", "descricao": "Sexo do doador?", "tipo": "RADIOBUTTON",
      "opcoes": [ { "descricao": "Feminino", "valor": "F" }, { "descricao": "Masculino", "valor": "M" } ],
      "dependentes": [ { "idPergunta": "Gravida", "valor": "F" },
                       { "idPergunta": "UltimoParto", "valor": "F" } ]
    },
    { "id": "Gravida", "descricao": "Você está grávida ou amamentando?", "tipo": "RADIOBUTTON",
      "possuiDependencia": "true",
      "opcoes": [ { "descricao": "Sim", "valor": "S" }, { "descricao": "Não", "valor": "N" } ]
    },
    { "id": "UltimoParto", "descricao": "Você já teve algum parto?", "tipo": "RADIOBUTTON",
      "possuiDependencia": "true",
      "opcoes": [ { "descricao": "Sim", "valor": "S" }, { "descricao": "Não", "valor": "N" } ],
      "dependentes": [ { "idPergunta": "DataUltimoParto", "valor": "S" } ]
    },
    { "id": "DataUltimoParto", "descricao": "Data do parto da última gestação:", "tipo": "TEXT",
      "possuiDependencia": "true",
      "tamanho": 10
    },
	
    { "id": "Medicamento", "descricao": "Você usa alguma medicação regularmente?", "tipo": "RADIOBUTTON",
      "opcoes": [ { "descricao": "Sim", "valor": "S" }, { "descricao": "Não", "valor": "N" } ],
      "dependentes": [ { "idPergunta": "QuaisMedicamento", "valor": "S" } ]
    },
');

UPDATE MODRED.FORMULARIO SET FORM_TX_FORMATO = FORM_TX_FORMATO || '{ "id": "QuaisMedicamento", "descricao": "Informe quais medicamentos faz uso:", "tipo": "TEXTAREA",
      "possuiDependencia": "true",
      "tamanho": 4000
    },
    { "id": "FormaContato", "descricao": "Qual a melhor forma de contato?", "tipo": "CHECKBUTTON",
      "opcoes": [ { "descricao": "SMS", "valor": "SMS" }, { "descricao": "Telegrama", "valor": "TELEGRAMA" }, { "descricao": "E-Mail", "valor": "EMAIL" }, { "descricao": "Telefone", "valor": "TELEFONE" }, { "descricao": "Indiferente", "valor": "Indiferente" }, { "descricao": "Outros", "valor": "OUTROS" } ],
      "dependentes": [ { "idPergunta": "OutrosContato", "valor": "OUTROS" } ]
    },
    { "id": "OutrosContato", "descricao": "Quais outro tipo de contato:", "tipo": "TEXTAREA",
      "possuiDependencia": "true",
      "tamanho": 4000
    },
    { "id": "PeriodoContato", "descricao": "Qual a melhor período para contato?", "tipo": "CHECKBUTTON",
      "opcoes": [ { "descricao": "Manhã", "valor": "M" }, { "descricao": "Tarde", "valor": "T" }, { "descricao": "Noite", "valor": "N" } ]
    },
    { "id": "DisponibilidadeViagem", "descricao": "Tem disponibilidade para viajar no ato da coleta de Medula Óssea caso não possa ser em seu estado?", "tipo": "RADIOBUTTON",
      "opcoes": [ { "descricao": "SIM", "valor": "S" }, { "descricao": "Não", "valor": "N" } ],
      "dependentes": [ { "idPergunta": "JustificativaViagem", "valor": "OUTROS" } ]
    },
    { "id": "JustificativaViagem", "descricao": "Qual o motivo da impossibilidade?", "tipo": "TEXTAREA",
      "possuiDependencia": "true",
      "tamanho": 4000
    }
  ]'
WHERE FORM_ID = 1;

COMMIT;

ALTER TABLE MODRED.PEDIDO_WORKUP 
ADD (PEWO_DT_CRIACAO TIMESTAMP );

COMMENT ON COLUMN MODRED.PEDIDO_WORKUP.PEWO_DT_CRIACAO IS 'Data da criação do pedido de workup';

--Alteração na tabela de turno
ALTER TABLE MODRED.TURNO 
ADD (TURN_IN_INCLUSIVO_EXCLUSIVO NUMBER(1) DEFAULT 0 NOT NULL);

COMMENT ON COLUMN MODRED.TURNO.TURN_IN_INCLUSIVO_EXCLUSIVO IS 'Define se o tempo é para incluir ou excluir determinado período.';

UPDATE MODRED.TURNO SET TURN_IN_INCLUSIVO_EXCLUSIVO = 1;

INSERT INTO MODRED.TURNO (TURN_ID, TURN_TX_DESCRICAO, TURN_DT_HR_INICIO, TURN_DT_HR_FIM, TURN_IN_INCLUSIVO_EXCLUSIVO)
VALUES (4, 'NAO_MANHÃ', '12/12/17 08:00:00,721000000', '12/12/17 11:59:59,886000000', 0);
INSERT INTO MODRED.TURNO (TURN_ID, TURN_TX_DESCRICAO, TURN_DT_HR_INICIO, TURN_DT_HR_FIM, TURN_IN_INCLUSIVO_EXCLUSIVO)
VALUES (5, 'NAO_TARDE', '12/12/17 12:00:00,821000000', '12/12/17 17:59:59,589000000', 0);
INSERT INTO MODRED.TURNO (TURN_ID, TURN_TX_DESCRICAO, TURN_DT_HR_INICIO, TURN_DT_HR_FIM, TURN_IN_INCLUSIVO_EXCLUSIVO)
VALUES (6, 'NAO_NOITE', '12/12/17 18:00:00,597000000', '12/12/17 22:00:00,133000000', 0);
COMMIT;

--Fim da alteração na tabela de turno


delete from modred.permissao where recu_id = 23 and perf_id = 8;
commit;