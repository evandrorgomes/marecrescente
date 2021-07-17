-- Adicionando propriedade Ativo na tabela de USUARIO
ALTER TABLE MODRED.USUARIO 
ADD (USUA_IN_ATIVO NUMBER(1) DEFAULT 1 NOT NULL);

COMMENT ON COLUMN MODRED.USUARIO.USUA_IN_ATIVO IS 'Indica se o usuário está ativo no sistema';

-- Criação de tabelas para Controle de Acesso da aplicação

CREATE TABLE MODRED.RECURSO 
(
  RECU_ID NUMBER NOT NULL,
RECU_TX_SIGLA VARCHAR2(50) NOT NULL,
RECU_TX_DESCRICAO VARCHAR2(100) NOT NULL
);

ALTER TABLE MODRED.RECURSO
ADD CONSTRAINT PK_RECU PRIMARY KEY 
(
  RECU_ID 
)
ENABLE;

COMMENT ON TABLE MODRED.RECURSO IS 'Recursos da aplicação.';
COMMENT ON COLUMN MODRED.RECURSO.RECU_ID IS 'Identificador do recurso.';
COMMENT ON COLUMN MODRED.RECURSO.RECU_TX_SIGLA IS 'Sigla do recurso.';
COMMENT ON COLUMN MODRED.RECURSO.RECU_TX_DESCRICAO IS 'Descrição do recurso.';


CREATE TABLE MODRED.PERMISSAO 
(
  RECU_ID NUMBER NOT NULL,
  PERF_ID NUMBER NOT NULL,
  PERM_IN_COM_RESTRICAO NUMBER(1) DEFAULT 0 NOT NULL
);

COMMENT ON TABLE MODRED.PERMISSAO IS 'Permissões aos recursos dos perfis.';
COMMENT ON COLUMN MODRED.PERMISSAO.RECU_ID IS 'Identificador do recurso.';
COMMENT ON COLUMN MODRED.PERMISSAO.PERF_ID IS 'Identificador do perfil.';
COMMENT ON COLUMN MODRED.PERMISSAO.PERM_IN_COM_RESTRICAO IS 'Indica se possui ou não alguma restrição que devem ser verificadas no momento da sua concessão.';

ALTER TABLE MODRED.PERMISSAO
ADD CONSTRAINT PK_PERM PRIMARY KEY 
(
  RECU_ID,
  PERF_ID
)
ENABLE;

ALTER TABLE MODRED.PERMISSAO
ADD CONSTRAINT FK_PERM_RECU FOREIGN KEY
(
  RECU_ID 
)
REFERENCES MODRED.RECURSO
(
  RECU_ID 
)
ENABLE;

ALTER TABLE MODRED.PERMISSAO
ADD CONSTRAINT FK_PERM_PERF FOREIGN KEY
(
  PERF_ID 
)
REFERENCES MODRED.PERFIL
(
  PERF_ID 
)
ENABLE;

INSERT INTO MODRED.RECURSO VALUES (1, 'CADASTRAR_PACIENTE', 'Permite cadastrar um novo paciente no sistema.');
INSERT INTO MODRED.RECURSO VALUES (2, 'CONSULTAR_PACIENTE', 'Permite consultar pacientes.');
INSERT INTO MODRED.RECURSO VALUES (3, 'VISUALIZAR_FICHA_PACIENTE', 'Permite visualizar os dados da ficha do paciente.');
INSERT INTO MODRED.RECURSO VALUES (4, 'CONSULTAR_EXAMES_PACIENTE', 'Permite consultar os exames dos pacientes.');
INSERT INTO MODRED.RECURSO VALUES (5, 'CONSULTAR_EVOLUCOES_PACIENTE', 'Permite consultar as evoluções dos pacientes.');
INSERT INTO MODRED.RECURSO VALUES (6, 'CADASTRAR_EXAMES_PACIENTE', 'Permite cadastrar novos exames para os pacientes.');
INSERT INTO MODRED.RECURSO VALUES (7, 'CADASTRAR_EVOLUCOES_PACIENTE', 'Permite cadastrar novas evoluções para os pacientes.');

INSERT INTO MODRED.PERMISSAO VALUES (1,1,0);
INSERT INTO MODRED.PERMISSAO VALUES (2,1,0);
INSERT INTO MODRED.PERMISSAO VALUES (3,1,1);
INSERT INTO MODRED.PERMISSAO VALUES (4,1,1);
INSERT INTO MODRED.PERMISSAO VALUES (5,1,1);
INSERT INTO MODRED.PERMISSAO VALUES (6,1,1);
INSERT INTO MODRED.PERMISSAO VALUES (7,1,1);
COMMIT;

-- atualizando a senha do usuário médico
UPDATE "MODRED"."USUARIO" SET USUA_TX_PASSWORD = '$2a$11$KMAfznbkZhx9mvESGy3.GewPWbSGQLVTnj5O0m7cIo5NrucDtcXT.' WHERE USUA_ID = 1;

/*		Avaliação		*/
CREATE TABLE MODRED.AVALIACAO 
(
  AVAL_ID NUMBER NOT NULL 
, PACI_NR_RMR NUMBER NOT NULL 
, AVAL_DT_CRIACAO TIMESTAMP NOT NULL 
, AVAL_IN_RESULTADO NUMBER(1) 
, MEDI_ID_AVALIADOR NUMBER  
, AVAL_TX_OBSERVACAO VARCHAR2(200 BYTE) 
, AVAL_DT_RESULTADO TIMESTAMP 
, CETR_ID NUMBER NOT NULL 
, AVAL_IN_STATUS NUMBER(1) NOT NULL 
, CONSTRAINT PK_AVAL PRIMARY KEY 
  (
    AVAL_ID 
  )
  USING INDEX 
  (
      CREATE UNIQUE INDEX PK_AVAL ON MODRED.AVALIACAO (AVAL_ID ASC) 
      
  )
  ENABLE 
);

CREATE INDEX IN_FK_AVAL_CETR ON MODRED.AVALIACAO (CETR_ID ASC) ;

CREATE INDEX IN_FK_AVAL_PACI ON MODRED.AVALIACAO (PACI_NR_RMR ASC) ;

ALTER TABLE MODRED.AVALIACAO
ADD CONSTRAINT FK_AVAL_CETR FOREIGN KEY
(
  CETR_ID 
)
REFERENCES MODRED.CENTRO_TRANSPLANTE
(
  CETR_ID 
)
ENABLE;

ALTER TABLE MODRED.AVALIACAO
ADD CONSTRAINT FK_AVAL_PACI FOREIGN KEY
(
  PACI_NR_RMR 
)
REFERENCES MODRED.PACIENTE
(
  PACI_NR_RMR 
)
ENABLE;

COMMENT ON TABLE MODRED.AVALIACAO IS 'Tabela de avaliação do paciente para liberação para busca';

COMMENT ON COLUMN MODRED.AVALIACAO.AVAL_ID IS 'Chave primária da tabela de avaliação';

COMMENT ON COLUMN MODRED.AVALIACAO.PACI_NR_RMR IS 'Chave estrangeira da tabela de paciente';

COMMENT ON COLUMN MODRED.AVALIACAO.AVAL_DT_CRIACAO IS 'Data de criação da avaliação';

COMMENT ON COLUMN MODRED.AVALIACAO.AVAL_IN_RESULTADO IS 'Restultado da avaliação 0 - reprovado 1 -  aprovado';

COMMENT ON COLUMN MODRED.AVALIACAO.MEDI_ID_AVALIADOR IS 'Chave estrangeira da tabelade médico';

COMMENT ON COLUMN MODRED.AVALIACAO.AVAL_TX_OBSERVACAO IS 'Observação ao encerrar uma avaliação';

COMMENT ON COLUMN MODRED.AVALIACAO.AVAL_DT_RESULTADO IS 'Data do resultado da avaliação';

COMMENT ON COLUMN MODRED.AVALIACAO.CETR_ID IS 'Chave estrangeira para tabela de centro avaliador';

COMMENT ON COLUMN MODRED.AVALIACAO.AVAL_IN_STATUS IS 'Status da avaliação 0 - Finalizado 1 - Aberto';

/*		Status Pendencia		*/

CREATE TABLE MODRED.STATUS_PENDENCIA 
(
  STPE_ID NUMBER(2) NOT NULL 
, STPE_TX_DESCRICAO VARCHAR2(20) NOT NULL 
, CONSTRAINT PK_STPE PRIMARY KEY 
  (
    STPE_ID 
  )
  USING INDEX 
  (
      CREATE UNIQUE INDEX PK_STPE ON STATUS_PENDENCIA (STPE_ID ASC) 
  )
  ENABLE 
);

COMMENT ON TABLE MODRED.STATUS_PENDENCIA IS 'Tabela de domínio dos status das pendências de uma avaliação';

COMMENT ON COLUMN MODRED.STATUS_PENDENCIA.STPE_ID IS 'Chave primária do status pendência';

COMMENT ON COLUMN MODRED.STATUS_PENDENCIA.STPE_TX_DESCRICAO IS 'Descrição do status de uma pendência de avaliação';


/*		Tipo Pendencia		*/

CREATE TABLE MODRED.TIPO_PENDENCIA 
(
  TIPE_ID NUMBER(2) NOT NULL 
, TIPE_TX_DESCRICAO VARCHAR2(50) NOT NULL 
, CONSTRAINT PK_TIPE PRIMARY KEY 
  (
    TIPE_ID 
  )
  USING INDEX 
  (
      CREATE UNIQUE INDEX PK_TIPE ON MODRED.TIPO_PENDENCIA (TIPE_ID ASC) 
  )
  ENABLE 
);

COMMENT ON TABLE MODRED.TIPO_PENDENCIA IS 'Tabela referente aos tipos de pendências possíveis';

COMMENT ON COLUMN MODRED.TIPO_PENDENCIA.TIPE_ID IS 'Chave primária do tipo de pendencia';

COMMENT ON COLUMN MODRED.TIPO_PENDENCIA.TIPE_TX_DESCRICAO IS 'Descrição do tipo de pendência';


/*		Resposta Pendencia		*/
CREATE TABLE MODRED.RESPOSTA_PENDENCIA 
(
  REPE_ID NUMBER NOT NULL 
, USUA_ID NUMBER NOT NULL 
, REPE_DT_DATA TIMESTAMP NOT NULL 
, REPE_TX_RESPOSTA VARCHAR2(200) 
, EXAM_ID NUMBER 
, EVOL_ID NUMBER 
, CONSTRAINT PK_REPE PRIMARY KEY 
  (
    REPE_ID 
  )
  USING INDEX 
  (
      CREATE UNIQUE INDEX PK_REPE ON MODRED.RESPOSTA_PENDENCIA (REPE_ID ASC) 
  )
  ENABLE 
);

CREATE INDEX IN_FK_REPE_EVOL ON MODRED.RESPOSTA_PENDENCIA (EVOL_ID);

CREATE INDEX IN_FK_REPE_EXAM ON MODRED.RESPOSTA_PENDENCIA (EXAM_ID);

CREATE INDEX IN_FK_REPE_USUA ON MODRED.RESPOSTA_PENDENCIA (USUA_ID);

ALTER TABLE MODRED.RESPOSTA_PENDENCIA
ADD CONSTRAINT FK_REPE_EVOL FOREIGN KEY
(
  EVOL_ID 
)
REFERENCES MODRED.EVOLUCAO
(
  EVOL_ID 
)
ENABLE;

ALTER TABLE MODRED.RESPOSTA_PENDENCIA
ADD CONSTRAINT FK_REPE_EXAM FOREIGN KEY
(
  EXAM_ID 
)
REFERENCES MODRED.EXAME
(
  EXAM_ID 
)
ENABLE;

ALTER TABLE MODRED.RESPOSTA_PENDENCIA
ADD CONSTRAINT FK_REPE_USUA FOREIGN KEY
(
  USUA_ID 
)
REFERENCES MODRED.USUARIO
(
  USUA_ID 
)
ENABLE;

COMMENT ON TABLE MODRED.RESPOSTA_PENDENCIA IS 'Mensagens trocadas entre o médico responsável e o médico avaliador sobre as pendências';

COMMENT ON COLUMN MODRED.RESPOSTA_PENDENCIA.REPE_ID IS 'Chave primária da tabela de resposta';

COMMENT ON COLUMN MODRED.RESPOSTA_PENDENCIA.USUA_ID IS 'Chave estrangeira para tabela de usuário, informa quem gravou a resposta';

COMMENT ON COLUMN MODRED.RESPOSTA_PENDENCIA.REPE_DT_DATA IS 'Data da resposta';

COMMENT ON COLUMN MODRED.RESPOSTA_PENDENCIA.REPE_TX_RESPOSTA IS 'Descrição da resposta enviado pelo médico ou avaliador';

COMMENT ON COLUMN MODRED.RESPOSTA_PENDENCIA.EXAM_ID IS 'Chave estrangeira da tabela EXAME';

COMMENT ON COLUMN MODRED.RESPOSTA_PENDENCIA.EVOL_ID IS 'Chave estrangeira da tabela de evolução';

/*		Associa Resposta Pendencia		*/
CREATE TABLE MODRED.ASSOCIA_RESPOSTA_PENDENCIA 
(
  PEND_ID NUMBER NOT NULL 
, REPE_ID NUMBER NOT NULL 
, CONSTRAINT PK_ASRP PRIMARY KEY 
  (
    PEND_ID 
  , REPE_ID 
  )
  USING INDEX 
  (
      CREATE UNIQUE INDEX PK_ASRP ON MODRED.ASSOCIA_RESPOSTA_PENDENCIA (PEND_ID ASC, REPE_ID ASC) 
  )
  ENABLE 
);

COMMENT ON TABLE MODRED.ASSOCIA_RESPOSTA_PENDENCIA IS 'Tabela de relacionamento entre pendencias e suas respostas.';

COMMENT ON COLUMN MODRED.ASSOCIA_RESPOSTA_PENDENCIA.PEND_ID IS 'Pk e Fk da tabela de pendencia';

COMMENT ON COLUMN MODRED.ASSOCIA_RESPOSTA_PENDENCIA.REPE_ID IS 'Pk e Fk da tabela de resposta';


/*		Pendencia		*/
CREATE TABLE MODRED.PENDENCIA 
(
  PEND_ID NUMBER NOT NULL 
, PEND_TX_DESCRICAO VARCHAR2(20) NOT NULL 
, AVAL_ID NUMBER NOT NULL 
, STPE_ID NUMBER NOT NULL 
, TIPE_ID NUMBER NOT NULL 
, PEND_DT_CRIACAO TIMESTAMP NOT NULL 
, CONSTRAINT PK_PEND PRIMARY KEY 
  (
    PEND_ID 
  )
  USING INDEX 
  (
      CREATE UNIQUE INDEX PK_PEND ON MODRED.PENDENCIA (PEND_ID ASC) 
  )
  ENABLE 
);

CREATE INDEX IN_FK_PEND_AVAL ON MODRED.PENDENCIA (AVAL_ID);

CREATE INDEX IN_FK_PEND_STPE ON MODRED.PENDENCIA (STPE_ID);

CREATE INDEX IN_FK_PEND_TIPE ON MODRED.PENDENCIA (TIPE_ID);

ALTER TABLE MODRED.PENDENCIA
ADD CONSTRAINT FK_PEND_AVAL FOREIGN KEY
(
  AVAL_ID 
)
REFERENCES MODRED.AVALIACAO
(
  AVAL_ID 
)
ENABLE;

ALTER TABLE MODRED.PENDENCIA
ADD CONSTRAINT FK_PEND_STPE FOREIGN KEY
(
  STPE_ID 
)
REFERENCES MODRED.STATUS_PENDENCIA
(
  STPE_ID 
)
ENABLE;

ALTER TABLE MODRED.PENDENCIA
ADD CONSTRAINT FK_PEND_TIPE FOREIGN KEY
(
  TIPE_ID 
)
REFERENCES MODRED.TIPO_PENDENCIA
(
  TIPE_ID 
)
ENABLE;

COMMENT ON TABLE MODRED.PENDENCIA IS 'Tabela de pendências da avaliação de um paciente.';

COMMENT ON COLUMN MODRED.PENDENCIA.PEND_ID IS 'Chave primária da tabela pendencia';

COMMENT ON COLUMN MODRED.PENDENCIA.PEND_TX_DESCRICAO IS 'Descrição da pendencia criada pelo avaliador';

COMMENT ON COLUMN MODRED.PENDENCIA.AVAL_ID IS 'Chave estrangeira para tabela de avaliação';

COMMENT ON COLUMN MODRED.PENDENCIA.STPE_ID IS 'Chave estrangeira para o status da pendência';

COMMENT ON COLUMN MODRED.PENDENCIA.TIPE_ID IS 'Chave estrangeira para o tipo de pendencia';

COMMENT ON COLUMN MODRED.PENDENCIA.PEND_DT_CRIACAO IS 'Data da criação da pendencia';


/*		Medico		*/
ALTER TABLE MODRED.MEDICO 
ADD (CETR_ID NUMBER );

CREATE INDEX IN_FK_MEDI_CETR ON MODRED.MEDICO (CETR_ID);

ALTER TABLE MODRED.MEDICO
ADD CONSTRAINT FK_MEDI_CETR FOREIGN KEY
(
  CETR_ID 
)
REFERENCES MODRED.CENTRO_TRANSPLANTE
(
  CETR_ID 
)
ENABLE;

COMMENT ON COLUMN MODRED.MEDICO.CETR_ID IS 'Chave estrangeira do centro_transplante';


/*		Sequences		*/
CREATE SEQUENCE MODRED.SQ_AVAL_ID INCREMENT BY 1 START WITH 1 MAXVALUE 9999999999999999999999999999 MINVALUE 1 CACHE 20;
CREATE SEQUENCE MODRED.SQ_PEND_ID INCREMENT BY 1 START WITH 1 MAXVALUE 9999999999999999999999999999 MINVALUE 1 CACHE 20;
CREATE SEQUENCE MODRED.SQ_REPE_ID INCREMENT BY 1 START WITH 1 MAXVALUE 9999999999999999999999999999 MINVALUE 1 CACHE 20;

/* Permissão da identificação para o medico responsável do paciente */
INSERT INTO MODRED.RECURSO (RECU_ID, RECU_TX_SIGLA, RECU_TX_DESCRICAO) VALUES ('8', 'VISUALIZAR_IDENTIFICACAO_COMPLETA', 'Permite visualizar a identificação completa do paciente');
INSERT INTO MODRED.PERMISSAO (RECU_ID, PERF_ID, PERM_IN_COM_RESTRICAO) VALUES ('8', '1', '1');
COMMIT;

/*POPULAR STATUS DE PENDENCIA*/

INSERT INTO "MODRED"."STATUS_PENDENCIA" (STPE_ID, STPE_TX_DESCRICAO) VALUES ('1', 'ABERTA');
INSERT INTO "MODRED"."STATUS_PENDENCIA" (STPE_ID, STPE_TX_DESCRICAO) VALUES ('2', 'RESPONDIDA');
INSERT INTO "MODRED"."STATUS_PENDENCIA" (STPE_ID, STPE_TX_DESCRICAO) VALUES ('3', 'CANCELADA');
INSERT INTO "MODRED"."STATUS_PENDENCIA" (STPE_ID, STPE_TX_DESCRICAO) VALUES ('4', 'FECHADA');
COMMIT;

/*TIPO DE PENDENCIA*/

INSERT INTO "MODRED"."TIPO_PENDENCIA" (TIPE_ID, TIPE_TX_DESCRICAO) VALUES ('1', 'EXAME');
INSERT INTO "MODRED"."TIPO_PENDENCIA" (TIPE_ID, TIPE_TX_DESCRICAO) VALUES ('2', 'EVOLUÇÃO');
INSERT INTO "MODRED"."TIPO_PENDENCIA" (TIPE_ID, TIPE_TX_DESCRICAO) VALUES ('3', 'QUESTIONAMENTO');
COMMIT;

-- PENDENCIA
ALTER TABLE MODRED.PENDENCIA  
MODIFY (PEND_TX_DESCRICAO VARCHAR2(200 BYTE) );

/*Cria o usuário Médico Avaliador */
INSERT INTO MODRED.USUARIO VALUES (2, 'MEDICO AVALIADOR', 'AVALIADOR', '$2a$11$KMAfznbkZhx9mvESGy3.GewPWbSGQLVTnj5O0m7cIo5NrucDtcXT.', 1);

/* Cria o Médico Avaliador associador ao usuário */
INSERT INTO MODRED.MEDICO VALUES (2, '45668', 'MEDICO AVALIADOR', 2, 2);

/* cria o perfil Médico avaliador */
INSERT into MODRED.PERFIL VALUES (2, 'MÉDICO_AVALIADOR');

/* Criação dos recursos  */
INSERT INTO MODRED.RECURSO VALUES (9, 'AVALIAR_PACIENTE', 'Permite avaliar um paciente no sistema.');
INSERT INTO MODRED.RECURSO values (10, 'VISUALIZAR_IDENTIFICACAO_PARCIAL', 'Permite visualizar a identificação parcial do paciente');

/* Atribui o perfil médico avaliador para o usuário 2 */
INSERT INTO MODRED.USUARIO_PERFIL values ( 2, 2);

/* Acerta as permições para o perfil médico avaliador */
INSERT INTO MODRED.PERMISSAO VALUES (9, 2, 0);
INSERT INTO MODRED.PERMISSAO VALUES (10, 2, 1);
INSERT INTO MODRED.PERMISSAO VALUES (4, 2, 1);
INSERT INTO MODRED.PERMISSAO VALUES (5, 2, 1);

COMMIT;


---------------Auditoria-------------------------
CREATE TABLE MODRED.AVALIACAO_AUD 
(
  AVAL_ID NUMBER NOT NULL 
, AUDI_ID NUMBER NOT NULL 
, AUDI_TX_TIPO NUMBER(3) 
, AVAL_IN_STATUS NUMBER(1) 
, AVAL_IN_RESULTADO NUMBER(1) 
, AVAL_DT_CRIACAO TIMESTAMP(6) 
, AVAL_DT_RESULTADO TIMESTAMP(6) 
, AVAL_TX_OBSERVACAO VARCHAR2(255 CHAR) 
, CETR_ID NUMBER
, MEDI_ID_AVALIADOR NUMBER
, PACI_NR_RMR NUMBER
, CONSTRAINT SYS_C008637 PRIMARY KEY 
  (
    AVAL_ID 
  , AUDI_ID 
  )
  USING INDEX 
  (
      CREATE UNIQUE INDEX SYS_C008637 ON MODRED.AVALIACAO_AUD (AVAL_ID ASC, AUDI_ID ASC) 
      
  )
  ENABLE 
);

ALTER TABLE MODRED.AVALIACAO_AUD
ADD CONSTRAINT FKG33H63X8Q8MRRVWMGOI6H4BY3 FOREIGN KEY
(
  AUDI_ID 
)
REFERENCES MODRED.AUDITORIA
(
  AUDI_ID 
)
ENABLE;

COMMENT ON TABLE MODRED.AVALIACAO_AUD IS 'Tabela de auditoria de avaliacao';


CREATE TABLE MODRED.PENDENCIA_AUD 
(
  PEND_ID NUMBER NOT NULL 
, AUDI_ID NUMBER NOT NULL 
, AUDI_TX_TIPO NUMBER(3) 
, PEND_DT_CRIACAO TIMESTAMP(6) 
, PEND_TX_DESCRICAO VARCHAR2(255 CHAR) 
, AVAL_ID NUMBER
, STPE_ID NUMBER
, TIPE_ID NUMBER 
, CONSTRAINT SYS_C008640 PRIMARY KEY 
  (
    PEND_ID 
  , AUDI_ID 
  )
  USING INDEX 
  (
      CREATE UNIQUE INDEX SYS_C008640 ON MODRED.PENDENCIA_AUD (PEND_ID ASC, AUDI_ID ASC) 
  )
  ENABLE 
);

ALTER TABLE MODRED.PENDENCIA_AUD
ADD CONSTRAINT FKS05HQLINFY3OXNW3L2LBF16KH FOREIGN KEY
(
  AUDI_ID 
)
REFERENCES MODRED.AUDITORIA
(
  AUDI_ID 
)
ENABLE;

COMMENT ON TABLE MODRED.PENDENCIA_AUD IS 'Tabela de auditoria de pendencia';

/* Correção dos nomes de constraints e indices */
ALTER INDEX SYS_C008637 
RENAME TO PK_AVAU;

CREATE INDEX IN_FK_AVAU_AUDI ON MODRED.AVALIACAO_AUD (AVAL_ID);

ALTER TABLE MODRED.AVALIACAO_AUD 
RENAME CONSTRAINT SYS_C008637 TO PK_AVAU;

ALTER TABLE MODRED.AVALIACAO_AUD 
RENAME CONSTRAINT FKG33H63X8Q8MRRVWMGOI6H4BY3 TO FK_AVAU_AUDI;


ALTER INDEX SYS_C008640 
RENAME TO PK_PEAU;

CREATE INDEX IN_FK_PEAU_AUDI ON MODRED.PENDENCIA_AUD (PEND_ID);

ALTER TABLE MODRED.PENDENCIA_AUD 
RENAME CONSTRAINT SYS_C008640 TO PK_PEAU;

ALTER TABLE MODRED.PENDENCIA_AUD 
RENAME CONSTRAINT FKS05HQLINFY3OXNW3L2LBF16KH TO FK_PEAU_AUDI;

-- Sequence da Tabela de Auditoria
CREATE SEQUENCE MODRED.SQ_AUDI_ID INCREMENT BY 1 START WITH 1 MAXVALUE 9999999999999999999999999999 MINVALUE 1 CACHE 20;