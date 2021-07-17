-- HISTÓRIA DE ENRIQUECIMENTO
INSERT INTO MODRED.RECURSO (RECU_ID, RECU_TX_SIGLA, RECU_TX_DESCRICAO) VALUES (26, 'INATIVAR_DOADOR', 'Permite inativar o doador para futuras buscas de forma permanente ou temporária.');
INSERT INTO MODRED.PERMISSAO (RECU_ID, PERF_ID, PERM_IN_COM_RESTRICAO) VALUES (26, 6, 0);
INSERT INTO MODRED.PERMISSAO (RECU_ID, PERF_ID, PERM_IN_COM_RESTRICAO) VALUES (26, 7, 0);
INSERT INTO MODRED.PERMISSAO (RECU_ID, PERF_ID, PERM_IN_COM_RESTRICAO) VALUES (26, 8, 0);

COMMENT ON COLUMN MODRED.DOADOR.DOAD_NR_TEMPO_INATIVO IS 'Tempo (em dias) em que o paciente ficará inativo.';

--RECURSO DE ADIÇÃO DE ENDERECO (06/12/2017) Queiroz
INSERT INTO MODRED.RECURSO (RECU_ID,RECU_TX_SIGLA,RECU_TX_DESCRICAO)
VALUES (27,'ADICIONAR_ENDERECO_DOADOR','Permite que adicionar um novo endereco para o doador.');

INSERT INTO MODRED.PERMISSAO (RECU_ID, PERF_ID, PERM_IN_COM_RESTRICAO) VALUES(27,6,0);

INSERT INTO "MODRED"."RECURSO" (RECU_ID, RECU_TX_SIGLA, RECU_TX_DESCRICAO) VALUES ('28', 'EXCLUIR_TELEFONE_CONTATO', 'Permite excluir um telefone do doador');
INSERT INTO "MODRED"."RECURSO" (RECU_ID, RECU_TX_SIGLA, RECU_TX_DESCRICAO) VALUES ('29', 'EXCLUIR_ENDERECO_CONTATO', 'Permite excluir um endereço do doador');
INSERT INTO "MODRED"."RECURSO" (RECU_ID, RECU_TX_SIGLA, RECU_TX_DESCRICAO) VALUES ('30', 'EXCLUIR_EMAIL_CONTATO', 'Permite excluir um email do doador');
commit;

INSERT INTO "MODRED"."PERMISSAO" (RECU_ID, PERF_ID, PERM_IN_COM_RESTRICAO) VALUES ('28', '7', '0');
INSERT INTO "MODRED"."PERMISSAO" (RECU_ID, PERF_ID, PERM_IN_COM_RESTRICAO) VALUES ('28', '8', '0');
INSERT INTO "MODRED"."PERMISSAO" (RECU_ID, PERF_ID, PERM_IN_COM_RESTRICAO) VALUES ('29', '7', '0');
INSERT INTO "MODRED"."PERMISSAO" (RECU_ID, PERF_ID, PERM_IN_COM_RESTRICAO) VALUES ('29', '8', '0');
INSERT INTO "MODRED"."PERMISSAO" (RECU_ID, PERF_ID, PERM_IN_COM_RESTRICAO) VALUES ('30', '7', '0');
INSERT INTO "MODRED"."PERMISSAO" (RECU_ID, PERF_ID, PERM_IN_COM_RESTRICAO) VALUES ('30', '8', '0');
commit;


INSERT INTO MODRED.RECURSO (RECU_ID, RECU_TX_SIGLA, RECU_TX_DESCRICAO) 
VALUES (31, 'ADICIONAR_CONTATO_TELEFONICO_DOADOR', 'Permite adicionar um novo telefone para o doador.');

INSERT INTO MODRED.PERMISSAO (RECU_ID, PERF_ID, PERM_IN_COM_RESTRICAO) VALUES(31, 6, 0);
COMMIT;

ALTER TABLE MODRED.ENDERECO_CONTATO 
ADD (ENCO_IN_PRINCIPAL NUMBER(1) DEFAULT 0,
     ENCO_IN_CORRESPONDENCIA NUMBER(1) DEFAULT 0);

COMMENT ON COLUMN MODRED.ENDERECO_CONTATO.ENCO_IN_PRINCIPAL IS 'Flag lógico que identifica se o endereço é o principal de contato com o doador.';
COMMENT ON COLUMN MODRED.ENDERECO_CONTATO.ENCO_IN_CORRESPONDENCIA IS 'Flag lógico que identifica se o endereço é de correspondência.';

ALTER TABLE MODRED.ENDERECO_CONTATO_AUD 
ADD (ENCO_IN_PRINCIPAL NUMBER(1) DEFAULT 0,
     ENCO_IN_CORRESPONDENCIA NUMBER(1) DEFAULT 0);

ALTER TABLE MODRED.ENDERECO_CONTATO_AUD 
ADD (DOAD_NR_DMR NUMBER );

INSERT INTO "MODRED"."RECURSO" (RECU_ID, RECU_TX_SIGLA, RECU_TX_DESCRICAO) VALUES ('32', 'ATUALIZAR_IDENTIFICACAO_DOADOR', 'Permite atualizar os dados de identificação do doador.');
INSERT INTO "MODRED"."PERMISSAO" (RECU_ID, PERF_ID, PERM_IN_COM_RESTRICAO) VALUES ('32', '7', '0');
INSERT INTO "MODRED"."PERMISSAO" (RECU_ID, PERF_ID, PERM_IN_COM_RESTRICAO) VALUES ('32', '8', '0');
COMMIT;


INSERT INTO "MODRED"."RECURSO" (RECU_ID, RECU_TX_SIGLA, RECU_TX_DESCRICAO) 
VALUES (34, 'ADICIONAR_EMAIL_DOADOR', 'Permite adicionar um novo e-mail para o doador.');

INSERT INTO "MODRED"."PERMISSAO" (RECU_ID, PERF_ID, PERM_IN_COM_RESTRICAO) 
VALUES (34, 6, 0);

INSERT INTO "MODRED"."RECURSO" (RECU_ID, RECU_TX_SIGLA, RECU_TX_DESCRICAO) VALUES ('33', 'ATUALIZAR_DADOS_PESSOAIS_DOADOR', 'Permite atualizar os dados pessoais do doador.');
INSERT INTO "MODRED"."PERMISSAO" (RECU_ID, PERF_ID, PERM_IN_COM_RESTRICAO) VALUES ('33', '7', '0');
INSERT INTO "MODRED"."PERMISSAO" (RECU_ID, PERF_ID, PERM_IN_COM_RESTRICAO) VALUES ('33', '8', '0');
COMMIT;

ALTER TABLE MODRED.EMAIL_CONTATO 
ADD (EMCO_IN_PRINCIPAL NUMBER(1) DEFAULT 0 NOT NULL );
COMMENT ON COLUMN MODRED.EMAIL_CONTATO.EMCO_IN_PRINCIPAL IS 'Flag que identifica se o email é o principal para contato.';

ALTER TABLE MODRED.EMAIL_CONTATO_AUD 
ADD (EMCO_IN_PRINCIPAL NUMBER(1) );

INSERT INTO "MODRED"."PERMISSAO" (RECU_ID, PERF_ID, PERM_IN_COM_RESTRICAO) VALUES ('32', '6', '0');
COMMIT;

-- Tabela de RESSALVAS
CREATE TABLE MODRED.RESSALVA_DOADOR 
(
  REDO_ID NUMBER NOT NULL, 
  REDO_TX_OBSERVACAO VARCHAR2(255) NOT NULL, 
  DOAD_NR_DMR NUMBER NOT NULL, 
  CONSTRAINT PK_REDO PRIMARY KEY (REDO_ID)
  USING INDEX (
    CREATE UNIQUE INDEX PK_REDO ON MODRED.RESSALVA_DOADOR (REDO_ID ASC) 
  ) ENABLE,
  CONSTRAINT FK_REDO_DOAD FOREIGN KEY (DOAD_NR_DMR)
  REFERENCES MODRED.DOADOR(DOAD_NR_DMR)
);

CREATE UNIQUE INDEX IN_FK_REDO_DOAD ON MODRED.RESSALVA_DOADOR (DOAD_NR_DMR ASC);

CREATE SEQUENCE MODRED.SQ_REDO_ID INCREMENT BY 1 MAXVALUE 9999999999999999999999999999 MINVALUE 1 CACHE 20;

COMMENT ON TABLE MODRED.RESSALVA_DOADOR IS 'Ressalvas, associadas ao doador, que devem ser consideradas durante o processo de busca ou match.';
COMMENT ON COLUMN MODRED.RESSALVA_DOADOR.REDO_ID IS 'Identificador sequencial para a ressalva do doador.';
COMMENT ON COLUMN MODRED.RESSALVA_DOADOR.REDO_TX_OBSERVACAO IS 'Texto explicativo sobre o motivo da ressalva do doador.';
COMMENT ON COLUMN MODRED.RESSALVA_DOADOR.DOAD_NR_DMR IS 'Identificador do doador associado a ressalva.';
COMMIT;

INSERT INTO MODRED.PERMISSAO (RECU_ID, PERF_ID, PERM_IN_COM_RESTRICAO) VALUES (8, 6, 0);
COMMIT;

--Queiroz adicionando permissão para o analista de contato adicionar telefone
insert into modred.permissao (recu_id, perf_id, perm_in_com_restricao) values (31, 7, 0);
insert into modred.permissao (recu_id, perf_id, perm_in_com_restricao) values (8, 7, 0);
commit;

INSERT INTO "MODRED"."CONFIGURACAO" (CONF_ID, CONF_TX_VALOR) VALUES ('quantidadeMaximaDiasContatoFase2', '8');
INSERT INTO "MODRED"."CONFIGURACAO" (CONF_ID, CONF_TX_VALOR) VALUES ('quantidadeMaximaDiasContatoFase3', '6');
commit;

INSERT INTO "MODRED"."CONFIGURACAO" (CONF_ID, CONF_TX_VALOR) VALUES ('quantidadeTentativasTelefonemaFase3', '6');
INSERT INTO "MODRED"."CONFIGURACAO" (CONF_ID, CONF_TX_VALOR) VALUES ('quantidadeDiasContatoFase3', '8');
commit;

INSERT INTO "MODRED"."CONFIGURACAO" (CONF_ID, CONF_TX_VALOR) VALUES ('quantidadeMaximaTentativasContatoFase2', '6');
INSERT INTO "MODRED"."CONFIGURACAO" (CONF_ID, CONF_TX_VALOR) VALUES ('quantidadeMaximaTentativasContatoFase3', '6');

INSERT INTO "MODRED"."CONFIGURACAO" (CONF_ID, CONF_TX_VALOR) VALUES ('quantidadeMinimaTentativasContatoFase2', '2');
INSERT INTO "MODRED"."CONFIGURACAO" (CONF_ID, CONF_TX_VALOR) VALUES ('quantidadeMinimaTentativasContatoFase3', '2');
commit;

INSERT INTO "MODRED"."MOTIVO_STATUS_DOADOR" (MOSD_ID, MOSD_TX_DESCRICAO, STDO_ID) VALUES ('4', 'Não contactado', '4');
commit;

INSERT INTO "MODRED"."RECURSO" (RECU_ID, RECU_TX_SIGLA, RECU_TX_DESCRICAO) VALUES ('36', 'FINALIZAR_TENTATIVA_CONTATO', 'Permite que o analista de fase 2 e 3 salve os dados de tentativa de contato.');
commit;

INSERT INTO "MODRED"."PERMISSAO" (RECU_ID, PERF_ID, PERM_IN_COM_RESTRICAO) VALUES ('36', '7', '0');
INSERT INTO "MODRED"."PERMISSAO" (RECU_ID, PERF_ID, PERM_IN_COM_RESTRICAO) VALUES ('36', '8', '0');
commit;

INSERT INTO MODRED.RECURSO (RECU_ID, RECU_TX_SIGLA, RECU_TX_DESCRICAO) 
VALUES (35, 'ADICIONAR_RESSALVA_DOADOR', 'Permite adicionar uma nova ressalva para o doador.');

INSERT INTO MODRED.PERMISSAO (RECU_ID, PERF_ID, PERM_IN_COM_RESTRICAO)
VALUES(35, 7, 0);

INSERT INTO MODRED.PERMISSAO (RECU_ID, PERF_ID, PERM_IN_COM_RESTRICAO)
VALUES(35, 8, 0);

COMMIT;

INSERT INTO "MODRED"."MOTIVO_STATUS_DOADOR_RECURSO" (MOSD_ID, RECU_ID) VALUES ('1', '25');
INSERT INTO "MODRED"."MOTIVO_STATUS_DOADOR_RECURSO" (MOSD_ID, RECU_ID) VALUES ('2', '25');
INSERT INTO "MODRED"."MOTIVO_STATUS_DOADOR_RECURSO" (MOSD_ID, RECU_ID) VALUES ('3', '34');
INSERT INTO "MODRED"."MOTIVO_STATUS_DOADOR_RECURSO" (MOSD_ID, RECU_ID) VALUES ('4', '34');
commit;

INSERT INTO MODRED.PERMISSAO (recu_id, perf_id, perm_in_com_restricao) values (34, 7, 0);
ALTER TABLE MODRED.EMAIL_CONTATO MODIFY (EMCO_IN_EXCLUIDO DEFAULT 0 NOT NULL);
commit;

INSERT INTO "MODRED"."MOTIVO_STATUS_DOADOR" (MOSD_ID, MOSD_TX_DESCRICAO, STDO_ID) VALUES ('5', 'Viagem', '3');
INSERT INTO "MODRED"."MOTIVO_STATUS_DOADOR" (MOSD_ID, MOSD_TX_DESCRICAO, STDO_ID) VALUES ('6', 'Desistência', '4');
commit;

/* formulário */
CREATE TABLE MODRED.TIPO_FORMULARIO 
(
  TIFO_ID NUMBER NOT NULL 
, TIFO_TX_DESCRICAO VARCHAR2(30) 
, TIFO_IN_ORIGEM_PACIENTE VARCHAR2(1) 
, CONSTRAINT PK_TIFO PRIMARY KEY 
  (
    TIFO_ID 
  )
  USING INDEX 
  (
      CREATE UNIQUE INDEX IN_PK_TIFO ON MODRED.TIPO_FORMULARIO (TIFO_ID ASC) 
  )
  ENABLE 
);

COMMENT ON TABLE MODRED.TIPO_FORMULARIO IS 'Tipo de formulário';
COMMENT ON COLUMN MODRED.TIPO_FORMULARIO.TIFO_ID IS 'Chave primária que identifica com exclusividade um tipo de formulário.';
COMMENT ON COLUMN MODRED.TIPO_FORMULARIO.TIFO_TX_DESCRICAO IS 'Descrição do tipo de formulário.';
COMMENT ON COLUMN MODRED.TIPO_FORMULARIO.TIFO_IN_ORIGEM_PACIENTE IS 'Flag que identifica se o formulário será utilizado com paciente nacional ou internacional. Sendo I para internacional e N para nacional.';


CREATE TABLE MODRED.FORMULARIO 
(
  FORM_ID NUMBER NOT NULL 
, TIFO_ID NUMBER NOT NULL 
, FORM_IN_ATIVO NUMBER(1) NOT NULL 
, FORM_TX_FORMATO CLOB NOT NULL 
, CONSTRAINT PK_FORM PRIMARY KEY 
  (
    FORM_ID 
  )
  USING INDEX 
  (
      CREATE UNIQUE INDEX IN_PK_FORM ON MODRED.FORMULARIO (FORM_ID ASC) 
  )
  ENABLE 
);

CREATE INDEX IN_FK_FORM_TIFO ON MODRED.FORMULARIO (TIFO_ID);

ALTER TABLE MODRED.FORMULARIO
ADD CONSTRAINT FK_FORM_TIFO FOREIGN KEY
(
  TIFO_ID 
)
REFERENCES MODRED.TIPO_FORMULARIO
(
  TIFO_ID 
)
ENABLE;

COMMENT ON TABLE MODRED.FORMULARIO IS 'Formulário';
COMMENT ON COLUMN MODRED.FORMULARIO.FORM_ID IS 'Chave primária que identifica com exclusividade um formulário.';
COMMENT ON COLUMN MODRED.FORMULARIO.TIFO_ID IS 'Chave estrangeira que representa o tipo do formulário.';
COMMENT ON COLUMN MODRED.FORMULARIO.FORM_IN_ATIVO IS 'Informa se o formulário está ativo ou inativo.';
COMMENT ON COLUMN MODRED.FORMULARIO.FORM_TX_FORMATO IS 'Armazena o formulário em formato json.';

CREATE TABLE MODRED.FORMULARIO_DOADOR 
(
  FODO_ID NUMBER NOT NULL 
, FORM_ID NUMBER NOT NULL 
, PECO_ID NUMBER NOT NULL 
, DOAD_NR_DMR NUMBER NOT NULL 
, FODO_DT_RESPOSTA TIMESTAMP NOT NULL 
, FODO_TX_RESPOSTAS CLOB NOT NULL 
, CONSTRAINT PK_FODO PRIMARY KEY 
  (
    FODO_ID 
  )
  USING INDEX 
  (
      CREATE UNIQUE INDEX IN_PK_FODO ON MODRED.FORMULARIO_DOADOR (FODO_ID ASC) 
  )
  ENABLE 
);

CREATE INDEX IN_FK_FODO_DOAD ON MODRED.FORMULARIO_DOADOR (DOAD_NR_DMR);
CREATE INDEX IN_FK_FODO_FORM ON MODRED.FORMULARIO_DOADOR (FORM_ID);
CREATE INDEX IN_FK_FODO_PECO ON MODRED.FORMULARIO_DOADOR (PECO_ID);

ALTER TABLE MODRED.FORMULARIO_DOADOR
ADD CONSTRAINT FK_FODO_DOAD FOREIGN KEY
(
  DOAD_NR_DMR 
)
REFERENCES MODRED.DOADOR
(
  DOAD_NR_DMR 
)
ENABLE;

ALTER TABLE MODRED.FORMULARIO_DOADOR
ADD CONSTRAINT FK_FODO_FORM FOREIGN KEY
(
  FORM_ID 
)
REFERENCES MODRED.FORMULARIO
(
  FORM_ID 
)
ENABLE;

ALTER TABLE MODRED.FORMULARIO_DOADOR
ADD CONSTRAINT FK_FODO_PECO FOREIGN KEY
(
  PECO_ID 
)
REFERENCES MODRED.PEDIDO_CONTATO
(
  PECO_ID 
)
ENABLE;

COMMENT ON TABLE MODRED.FORMULARIO_DOADOR IS 'Armazena as respostas do formulário.';
COMMENT ON COLUMN MODRED.FORMULARIO_DOADOR.FODO_ID IS 'Chave primária que identifica com exclusividade um formulário_doador.';
COMMENT ON COLUMN MODRED.FORMULARIO_DOADOR.FORM_ID IS 'Chave estrangeira para o formulário.';
COMMENT ON COLUMN MODRED.FORMULARIO_DOADOR.PECO_ID IS 'Chave estrangeira para o pedido de contato.';
COMMENT ON COLUMN MODRED.FORMULARIO_DOADOR.DOAD_NR_DMR IS 'Chave estrangeira para o doador.';
COMMENT ON COLUMN MODRED.FORMULARIO_DOADOR.FODO_DT_RESPOSTA IS 'Data em que foi respondido o formulário.';
COMMENT ON COLUMN MODRED.FORMULARIO_DOADOR.FODO_TX_RESPOSTAS IS 'Formulário respondido.';

CREATE TABLE MODRED.RESPOSTA_FORMULARIO_DOADOR 
(
  REFD_ID NUMBER NOT NULL 
, FODO_ID NUMBER NOT NULL 
, REFD_TX_TOKEN VARCHAR2(50) NOT NULL 
, REFD_TX_RESPOSTA VARCHAR2(4000) 
, CONSTRAINT PK_REFD PRIMARY KEY 
  (
    REFD_ID 
  )
  USING INDEX 
  (
      CREATE UNIQUE INDEX IN_PK_REFD ON MODRED.RESPOSTA_FORMULARIO_DOADOR (REFD_ID ASC) 
  )
  ENABLE 
);

CREATE INDEX IN_FK_REFD_FODO ON MODRED.RESPOSTA_FORMULARIO_DOADOR (FODO_ID);

ALTER TABLE MODRED.RESPOSTA_FORMULARIO_DOADOR
ADD CONSTRAINT FK_REFD_FODO FOREIGN KEY
(
  FODO_ID 
)
REFERENCES MODRED.FORMULARIO_DOADOR
(
  FODO_ID 
)
ENABLE;

COMMENT ON TABLE MODRED.RESPOSTA_FORMULARIO_DOADOR IS 'Resposta do formulário associado ao token';
COMMENT ON COLUMN MODRED.RESPOSTA_FORMULARIO_DOADOR.REFD_ID IS 'Chave primária que identifica com exclusividade uma resposta_formulário_doador.';
COMMENT ON COLUMN MODRED.RESPOSTA_FORMULARIO_DOADOR.FODO_ID IS 'Chave estrangeira para o formulario_doador.';
COMMENT ON COLUMN MODRED.RESPOSTA_FORMULARIO_DOADOR.REFD_TX_TOKEN IS 'Token da pergunta.';
COMMENT ON COLUMN MODRED.RESPOSTA_FORMULARIO_DOADOR.REFD_TX_RESPOSTA IS 'Valor respondido.';

CREATE SEQUENCE MODRED.SQ_FORM_ID INCREMENT BY 1 START WITH 1 MAXVALUE 99999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999 MINVALUE 1;
CREATE SEQUENCE MODRED.SQ_FODO_ID INCREMENT BY 1 START WITH 1 MAXVALUE 99999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999 MINVALUE 1;
CREATE SEQUENCE MODRED.SQ_REFD_ID INCREMENT BY 1 START WITH 1 MAXVALUE 99999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999 MINVALUE 1;

/* fim formulário */


DELETE FROM "MODRED"."MOTIVO_STATUS_DOADOR_RECURSO" WHERE MOSD_ID = 3 AND RECU_ID = 34;
DELETE FROM "MODRED"."MOTIVO_STATUS_DOADOR_RECURSO" WHERE MOSD_ID = 4 AND RECU_ID = 34;

INSERT INTO "MODRED"."MOTIVO_STATUS_DOADOR_RECURSO" (MOSD_ID, RECU_ID) VALUES ('3', '36');
INSERT INTO "MODRED"."MOTIVO_STATUS_DOADOR_RECURSO" (MOSD_ID, RECU_ID) VALUES ('5', '36');
INSERT INTO "MODRED"."MOTIVO_STATUS_DOADOR_RECURSO" (MOSD_ID, RECU_ID) VALUES ('6', '36');
COMMIT;



CREATE TABLE MODRED.TURNO 
(
  TURN_ID NUMBER NOT NULL 
, TURN_TX_DESCRICAO VARCHAR2(30 BYTE) NOT NULL 
, TURN_DT_HR_INICIO TIMESTAMP(6) NOT NULL 
, TURN_DT_HR_FIM TIMESTAMP(6) NOT NULL 
, CONSTRAINT PK_TURNO PRIMARY KEY 
  (
    TURN_ID 
  )
  USING INDEX 
  (
      CREATE UNIQUE INDEX IN_PK_TURNO ON TURNO (TURN_ID ASC) 
  )
  ENABLE 
);

COMMENT ON COLUMN MODRED.TURNO.TURN_ID IS 'Identificacao da tabela de turno';

COMMENT ON COLUMN MODRED.TURNO.TURN_TX_DESCRICAO IS 'Descrição do turno';

COMMENT ON COLUMN MODRED.TURNO.TURN_DT_HR_INICIO IS 'Hora inicial do turno';

COMMENT ON COLUMN MODRED.TURNO.TURN_DT_HR_FIM IS 'Hora final do turno';



INSERT INTO "MODRED"."TURNO" (TURN_ID, TURN_TX_DESCRICAO, TURN_DT_HR_INICIO, TURN_DT_HR_FIM) VALUES ('1', 'MANHÃ', TO_TIMESTAMP('2017-12-12 08:00:00.721000000', 'YYYY-MM-DD HH24:MI:SS.FF'), TO_TIMESTAMP('2017-12-12 11:59:59.886000000', 'YYYY-MM-DD HH24:MI:SS.FF'));
INSERT INTO "MODRED"."TURNO" (TURN_ID, TURN_TX_DESCRICAO, TURN_DT_HR_INICIO, TURN_DT_HR_FIM) VALUES ('2', 'TARDE', TO_TIMESTAMP('2017-12-12 12:00:00.821000000', 'YYYY-MM-DD HH24:MI:SS.FF'), TO_TIMESTAMP('2017-12-12 17:59:59.589000000', 'YYYY-MM-DD HH24:MI:SS.FF'));
INSERT INTO "MODRED"."TURNO" (TURN_ID, TURN_TX_DESCRICAO, TURN_DT_HR_INICIO, TURN_DT_HR_FIM) VALUES ('3', 'NOITE', TO_TIMESTAMP('2017-12-12 18:00:00.597000000', 'YYYY-MM-DD HH24:MI:SS.FF'), TO_TIMESTAMP('2017-12-12 22:00:00.133000000', 'YYYY-MM-DD HH24:MI:SS.FF'));
COMMIT;


COMMIT;

/* ALTERAÇÃO TIPO FORMULARIO */
ALTER TABLE MODRED.TIPO_FORMULARIO 
DROP COLUMN TIFO_IN_ORIGEM_PACIENTE;

ALTER TABLE MODRED.TIPO_FORMULARIO 
ADD (TIFO_IN_NACIONAL NUMBER(1) NOT NULL,
     TIFO_IN_INTERNACIONAL NUMBER(1) NOT NULL);

COMMENT ON COLUMN MODRED.TIPO_FORMULARIO.TIFO_IN_NACIONAL IS 'Flag que informa se o formulario é nacional.';
COMMENT ON COLUMN MODRED.TIPO_FORMULARIO.TIFO_IN_INTERNACIONAL IS 'Flag que informa se o formulario é internacional.';
 
INSERT INTO MODRED.TIPO_FORMULARIO VALUES (1, 'Fase 2', 1, 1);
INSERT INTO MODRED.TIPO_FORMULARIO VALUES (2, 'Fase 3', 1, 1);
COMMIT;
/* FIM - ALTERAÇÃO TIPO FORMULARIO */



CREATE TABLE MODRED.RESSALVA_DOADOR_AUD 
(
  REDO_ID NUMBER(19, 0) NOT NULL 
, AUDI_ID NUMBER(19, 0) NOT NULL 
, AUDI_TX_TIPO NUMBER(3, 0) 
, REDO_TX_OBSERVACAO VARCHAR2(255 CHAR) 
, DOAD_NR_DMR NUMBER(19, 0) 
, CONSTRAINT PK_REDA PRIMARY KEY 
  (
    REDO_ID 
  , AUDI_ID 
  )
  USING INDEX 
  (
      CREATE UNIQUE INDEX PK_REDA ON RESSALVA_DOADOR_AUD (REDO_ID ASC, AUDI_ID ASC) 
      NOPARALLEL 
  )
  ENABLE 
);

ALTER TABLE MODRED.RESSALVA_DOADOR_AUD
ADD CONSTRAINT FK_REDA_AUDI_ID FOREIGN KEY
(
  AUDI_ID 
)
REFERENCES AUDITORIA
(
  AUDI_ID 
)
ENABLE;

COMMENT ON TABLE MODRED.RESSALVA_DOADOR_AUD IS 'Tabela de auditoria da table Ressalva Doador.';
COMMENT ON COLUMN MODRED.RESSALVA_DOADOR_AUD.REDO_ID IS 'Id da ressalva';
COMMENT ON COLUMN MODRED.RESSALVA_DOADOR_AUD.AUDI_ID IS 'Id da auditoria';
COMMENT ON COLUMN MODRED.RESSALVA_DOADOR_AUD.AUDI_TX_TIPO IS 'Tipo da auditoria';
COMMENT ON COLUMN MODRED.RESSALVA_DOADOR_AUD.REDO_TX_OBSERVACAO IS 'Texto da observação';
COMMENT ON COLUMN MODRED.RESSALVA_DOADOR_AUD.DOAD_NR_DMR IS 'Id do doador';
COMMIT;


INSERT INTO "MODRED"."CONFIGURACAO" (CONF_ID, CONF_TX_VALOR) VALUES ('quantidadeMinimaTentativasFase3', '2');
INSERT INTO "MODRED"."CONFIGURACAO" (CONF_ID, CONF_TX_VALOR) VALUES ('quantidadeMinimaTentativasFase2', '2');
commit;

-- Associando recurso ao motivo status do doador
INSERT INTO "MODRED"."MOTIVO_STATUS_DOADOR_RECURSO" (MOSD_ID, RECU_ID) VALUES ('1', '26');
INSERT INTO "MODRED"."MOTIVO_STATUS_DOADOR_RECURSO" (MOSD_ID, RECU_ID) VALUES ('2', '26');
INSERT INTO "MODRED"."MOTIVO_STATUS_DOADOR_RECURSO" (MOSD_ID, RECU_ID) VALUES ('3', '26');
COMMIT;

-- RENOMEANDO A COLUNA DE TEMPO DE INATIVO DO DOADOR E MANTENDO OS DADOS JÁ INSERIDOS.
ALTER TABLE MODRED.DOADOR ADD (DOAD_DT_RETORNO_INATIVIDADE DATE);
COMMENT ON COLUMN MODRED.DOADOR.DOAD_DT_RETORNO_INATIVIDADE IS 'Enquanto inativo temporário, é a data de retorno do doador do período de inatividade.';
UPDATE MODRED.DOADOR SET DOAD_DT_RETORNO_INATIVIDADE = SYSDATE + DOAD_NR_TEMPO_INATIVO;
ALTER TABLE MODRED.DOADOR DROP COLUMN DOAD_NR_TEMPO_INATIVO;

ALTER TABLE MODRED.DOADOR_AUD ADD (DOAD_DT_RETORNO_INATIVIDADE DATE);
COMMENT ON COLUMN MODRED.DOADOR_AUD.DOAD_DT_RETORNO_INATIVIDADE IS 'Enquanto inativo temporário, é a data de retorno do doador do período de inatividade.';
UPDATE MODRED.DOADOR_AUD SET DOAD_DT_RETORNO_INATIVIDADE = SYSDATE + DOAD_NR_TEMPO_INATIVO;
ALTER TABLE MODRED.DOADOR_AUD DROP COLUMN DOAD_NR_TEMPO_INATIVO;
COMMIT;

INSERT INTO MODRED.FORMULARIO VALUES (1, 1, 1, 
'[
    { "id": "DoadorSangue", "descricao": "Doador de sangue?", "tipo": "RADIOBUTTON",
      "opcoes": [ { "descricao": "Sim", "valor": "S" }, { "descricao": "Não", "valor": "N" } ],
      "dependentes": [ { "idPergunta": "UltimaDoacao", "valor": "S" } ]
    },
    { "id": "UltimaDoacao", "descricao": "Última doação de sangue?", "tipo": "RADIOBUTTON",
      "possuiDependencia": "true",
      "opcoes": [ { "descricao": "Seis meses a um ano", "valor": "A" }, { "descricao": "Mais de um ano", "valor": "B" } ]
    },
    
    { "id": "Cancer", "descricao": "Já teve algum tipo de câncer?", "tipo": "RADIOBUTTON",
      "opcoes": [ { "descricao": "Sim", "valor": "S" }, { "descricao": "Não", "valor": "N" } ],
      "dependentes": [ { "idPergunta": "QuaisCancer", "valor": "S" } ]
    },
    { "id": "QuaisCancer", "descricao": "Informe quais, indicando o ano de ocorrência e o tratamento para cada um:", "tipo": "TEXTAREA",
      "possuiDependencia": "true",
      "tamanho": "4000"
    },
    { "id": "Hepatite", "descricao": "Já teve algum tipo de hepatite?", "tipo": "RADIOBUTTON",
      "opcoes": [ { "descricao": "Sim", "valor": "S" }, { "descricao": "Não", "valor": "N" } ],
      "dependentes": [ { "idPergunta": "QuaisHepatite", "valor": "S" } ]
    },
    { "id": "QuaisHepatite", "descricao": "Informe quais tipos, indicando o ano de ocorrência de cada um:", "tipo": "TEXTAREA",
      "possuiDependencia": "true",
      "tamanho": "4000"
    },
    { "id": "Infeccao", "descricao": "Já teve algum tipo de infecção?", "tipo": "RADIOBUTTON",
      "opcoes": [ { "descricao": "Sim", "valor": "S" }, { "descricao": "Não", "valor": "N" } ],
      "dependentes": [ { "idPergunta": "QuaisInfeccao", "valor": "S" } ]
    },
    { "id": "QuaisInfeccao", "descricao": "Informe quais, indicando o ano de ocorrência de cada uma:", "tipo": "TEXTAREA",
      "possuiDependencia": "true",
      "tamanho": "4000"
    },
    { "id": "DST", "descricao": "Já teve alguma DST (Doença Sexualmente Transmissível)? (HIV, HTLV, Sífilis, Herpes ou outras)", "tipo": "RADIOBUTTON",
      "opcoes": [ { "descricao": "Sim", "valor": "S" }, { "descricao": "Não", "valor": "N" } ],
      "dependentes": [ { "idPergunta": "QuaisDST", "valor": "S" } ]
    },
    { "id": "QuaisDST", "descricao": "Quais?", "tipo": "CHECKBUTTON",
      "opcoes": [ { "descricao": "HIV", "valor": "HIV" }, { "descricao": "HTLV", "valor": "HTLV" }, { "descricao": "Sífilis", "valor": "SIFILIS" }, { "descricao": "Herpes", "valor": "HERPES" }, { "descricao": "Outra(s)", "valor": "OUTRAS" } ],
      "dependentes": [ { "idPergunta": "OutrasDST", "valor": "OUTRAS" } ]
    },
    { "id": "OutrasDST", "descricao": "Informe quais outras, indicando o ano e o tratamento de cada uma:", "tipo": "TEXTAREA",
      "possuiDependencia": "true",
      "tamanho": "4000"
    },
    { "id": "Alergia", "descricao": "Já teve alguma alergia?", "tipo": "RADIOBUTTON",
      "opcoes": [ { "descricao": "Sim", "valor": "S" }, { "descricao": "Não", "valor": "N" } ],
      "dependentes": [ { "idPergunta": "QuaisAlergia", "valor": "S" } ]
    },
    { "id": "QuaisAlergia", "descricao": "Informe quais alergias:", "tipo": "TEXTAREA",
      "possuiDependencia": "true",
      "tamanho": "4000"
    },
    { "id": "Gestacao", "descricao": "Já teve gestações?", "tipo": "RADIOBUTTON",
      "opcoes": [ { "descricao": "Sim", "valor": "S" }, { "descricao": "Não", "valor": "N" } ],
      "dependentes": [ { "idPergunta": "TipoGestacao", "valor": "S" } ]
    },
    { "id": "TipoGestacao", "descricao": "Gestações de que tipos?", "tipo": "CHECKBUTTON",
      "possuiDependencia": "true",
      "opcoes": [ { "descricao": "Parto Normal", "valor": "NORMAL" }, { "descricao": "Parto Cesária", "valor": "CESARIA" } ],
      "dependentes": [ { "idPergunta": "GestacaoNormal", "valor": "NORMAL" }, { "idPergunta": "GestacaoCesaria", "valor": "CESARIA" } ]
    },');
    
UPDATE MODRED.FORMULARIO SET FORM_TX_FORMATO = FORM_TX_FORMATO || '{ "id": "GestacaoNormal", "descricao": "Informe os anos e quantidade de partos normais:", "tipo": "TEXTAREA",
      "possuiDependencia": "true",
      "tamanho": "4000"
    },
    { "id": "GestacaoCesaria", "descricao": "Informe os anos e quantidade de partos Cesária:", "tipo": "TEXTAREA",
      "possuiDependencia": "true",
      "tamanho": "4000"
    },
    { "id": "Aborto", "descricao": "Já teve algum aborto?", "tipo": "RADIOBUTTON",
      "opcoes": [ { "descricao": "Sim", "valor": "S" }, { "descricao": "Não", "valor": "N" } ],
      "dependentes": [ { "idPergunta": "QuaisAborto", "valor": "S" } ]
    },
    { "id": "QuaisAborto", "descricao": "Informe os anos e a causa dos abortos:", "tipo": "TEXTAREA",
      "possuiDependencia": "true",
      "tamanho": "4000"
    },
    { "id": "Cirurgia", "descricao": "Já realizou alguma cirurgia?", "tipo": "RADIOBUTTON",
      "opcoes": [ { "descricao": "Sim", "valor": "S" }, { "descricao": "Não", "valor": "N" } ],
      "dependentes": [ { "idPergunta": "QuaisCirurgia", "valor": "S" } ]
    },
    { "id": "QuaisCirurgia", "descricao": "Informe quais cirurgias e o respectivo ano:", "tipo": "TEXTAREA",
      "possuiDependencia": "true",
      "tamanho": "4000"
    },
    { "id": "Tatuagem", "descricao": "Já fez alguma tatuagem ou maquiagem definitiva?", "tipo": "RADIOBUTTON",
      "opcoes": [ { "descricao": "Sim", "valor": "S" }, { "descricao": "Não", "valor": "N" } ],
      "dependentes": [ { "idPergunta": "UltimaTatuagem", "valor": "S" } ]
    },
    { "id": "UltimaTatuagem", "descricao": "Em que ano foi a última?", "tipo": "TEXT",
      "possuiDependencia": "true",
      "tamanho": "10"
    },
    { "id": "Transfusao", "descricao": "Já fez alguma transfusão de sangue, plaquetas, plasma ou outros?", "tipo": "RADIOBUTTON",
      "opcoes": [ { "descricao": "Sim", "valor": "S" }, { "descricao": "Não", "valor": "N" } ],
      "dependentes": [ { "idPergunta": "QuaisTransfusao", "valor": "S" } ]
    },
    { "id": "QuaisTransfusao", "descricao": "Informe quais transfusões e o respectivo ano e motivo:", "tipo": "TEXTAREA",
      "possuiDependencia": "true",
      "tamanho": "4000"
    },
    { "id": "Medicamento", "descricao": "Faz uso de algum medicamento?", "tipo": "RADIOBUTTON",
      "opcoes": [ { "descricao": "Sim", "valor": "S" }, { "descricao": "Não", "valor": "N" } ],
      "dependentes": [ { "idPergunta": "QuaisMedicamento", "valor": "S" } ]
    },
    { "id": "QuaisMedicamento", "descricao": "Informe quais medicamentos faz uso:", "tipo": "TEXTAREA",
      "possuiDependencia": "true",
      "tamanho": "4000"
    },
    { "id": "Tratamento", "descricao": "Já fez algum tratamento de saúde (colonoscopia/rinoscopia/endoscopia)?", "tipo": "RADIOBUTTON",
      "opcoes": [ { "descricao": "Sim", "valor": "S" }, { "descricao": "Não", "valor": "N" } ],
      "dependentes": [ { "idPergunta": "QuaisTratamento", "valor": "S" } ]
    },
    { "id": "QuaisTratamento", "descricao": "Informe quais tratamentos e o respectivo ano:", "tipo": "TEXTAREA",
      "possuiDependencia": "true",
      "tamanho": "4000"
    },
    { "id": "Religiao", "descricao": "Possui alguma religião?", "tipo": "RADIOBUTTON",
      "opcoes": [ { "descricao": "Sim", "valor": "S" }, { "descricao": "Não", "valor": "N" } ],
      "dependentes": [ { "idPergunta": "QualReligiao", "valor": "S" }, { "idPergunta": "RestricaoReligiao", "valor": "S" } ]
    },
    { "id": "QualReligiao", "descricao": "Qual religião?", "tipo": "TEXT",
      "possuiDependencia": "true",
      "tamanho": "100"
    },
    { "id": "RestricaoReligiao", "descricao": "Essa religião restringe futuras doações?", "tipo": "RADIOBUTTON",
      "possuiDependencia": "true",
      "opcoes": [ { "descricao": "Sim", "valor": "S" }, { "descricao": "Não", "valor": "N" } ]
    },'
WHERE FORM_ID = 1;
    
UPDATE MODRED.FORMULARIO SET FORM_TX_FORMATO = FORM_TX_FORMATO || '{ "id": "FormaContato", "descricao": "Qual a melhor forma de contato?", "tipo": "CHECKBUTTON",
      "opcoes": [ { "descricao": "SMS", "valor": "SMS" }, { "descricao": "Telegrama", "valor": "TELEGRAMA" }, { "descricao": "E-Mail", "valor": "EMAIL" }, { "descricao": "Telefone", "valor": "TELEFONE" }, { "descricao": "Indiferente", "valor": "Indiferente" }, { "descricao": "Outros", "valor": "OUTROS" } ],
      "dependentes": [ { "idPergunta": "OutrosContato", "valor": "OUTROS" } ]
    },
    { "id": "OutrosContato", "descricao": "Quais outro tipo de contato:", "tipo": "TEXTAREA",
      "possuiDependencia": "true",
      "tamanho": "4000"
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
      "tamanho": "4000"
    }
  ]'
WHERE FORM_ID = 1;
commit;


-- Alteração na tabela de DOADOR
ALTER TABLE MODRED.DOADOR 
ADD (DOAD_VL_ALTURA NUMBER(3,2) );

ALTER TABLE MODRED.DOADOR 
ADD (DOAD_VL_PESO NUMBER(4,1) );

ALTER TABLE MODRED.DOADOR 
ADD (DOAD_TX_NOME_PAI VARCHAR2(255) );

COMMENT ON COLUMN MODRED.DOADOR.DOAD_VL_ALTURA IS 'Altura atual do doador';

COMMENT ON COLUMN MODRED.DOADOR.DOAD_VL_PESO IS 'Peso atual do doador';

COMMENT ON COLUMN MODRED.DOADOR.DOAD_TX_NOME_PAI IS 'Nome do pai do doador';

ALTER TABLE MODRED.DOADOR_AUD 
ADD (DOAD_VL_ALTURA NUMBER(3,2) );

ALTER TABLE MODRED.DOADOR_AUD 
ADD (DOAD_VL_PESO NUMBER(4,1) );

ALTER TABLE MODRED.DOADOR_AUD 
ADD (DOAD_TX_NOME_PAI VARCHAR2(255) );
COMMIT;

DROP INDEX IN_FK_REDO_DOAD;
COMMIT;


INSERT INTO MODRED.PERMISSAO (RECU_ID, PERF_ID, PERM_IN_COM_RESTRICAO) VALUES(27,7,0);
COMMIT;
-- Adicionando valor de execução para os tipos de tarefas de enriquecimento e contato
UPDATE MODRED.TIPO_TAREFA SET TITA_NR_TEMPO_EXECUCAO=3600 WHERE TITA_ID IN (10, 11, 12, 13);



INSERT INTO MODRED.RECURSO (RECU_ID, RECU_TX_SIGLA, RECU_TX_DESCRICAO) VALUES (37, 'INATIVAR_DOADOR_FASE2', 'Permite inativar o doador para futuras buscas de forma permanente ou temporária para a fase2.');
insert into MODRED.MOTIVO_STATUS_DOADOR_RECURSO (MOSD_ID,RECU_ID) VALUES (5, 37);
insert into MODRED.MOTIVO_STATUS_DOADOR_RECURSO (MOSD_ID,RECU_ID) VALUES (3, 37);
insert into MODRED.MOTIVO_STATUS_DOADOR_RECURSO (MOSD_ID,RECU_ID) VALUES (6, 37);
COMMIT;

UPDATE MODRED.RECURSO SET RECU_TX_SIGLA = 'INATIVAR_DOADOR_ENRIQUECIMENTO', 
RECU_TX_DESCRICAO = 'Permite que o atendente inative o doador durante o processo de enriquecimento do cadastro.' 
WHERE RECU_ID = 26;
COMMIT;
