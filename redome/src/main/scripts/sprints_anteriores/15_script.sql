--TAREFA
ALTER TABLE MODRED.TAREFA 
ADD(TARE_DT_INICIO DATE NULL
, TARE_DT_FIM DATE NULL
, TARE_DT_HORA_INICIO TIMESTAMP NULL 
, TARE_DT_HORA_FIM TIMESTAMP NULL
, TARE_NR_INCLUSIVO_EXCLUSIVO NUMBER  NULL ); 


COMMENT ON COLUMN MODRED.TAREFA.TARE_DT_INICIO IS 'Data do início de execução de tarefa';

COMMENT ON COLUMN MODRED.TAREFA.TARE_DT_FIM IS 'Data fim para execução da tarefa';

COMMENT ON COLUMN MODRED.TAREFA.TARE_DT_HORA_INICIO IS 'Hora do início da tarefa';

COMMENT ON COLUMN MODRED.TAREFA.TARE_DT_HORA_FIM IS 'Hora fim da execução da tarefa';

COMMENT ON COLUMN MODRED.TAREFA.TARE_NR_INCLUSIVO_EXCLUSIVO IS 'Define se o tempo é para incluir ou excluir determinado período. 0 é para incluir e 1 para excluir';

--DOADOR
CREATE TABLE MODRED.STATUS_DOADOR 
(
  STDO_ID NUMBER NOT NULL, 
  STDO_TX_DESCRICAO VARCHAR2(40) NOT NULL, 
  STDO_IN_TEMPO_OBRIGATORIO NUMBER DEFAULT 0 NOT NULL, 
  CONSTRAINT PK_STDO PRIMARY KEY 
  (
    STDO_ID 
  )
  USING INDEX 
  (
      CREATE UNIQUE INDEX PK_STDO ON MODRED.STATUS_DOADOR (STDO_ID ASC) 
  )
  ENABLE 
);

COMMENT ON TABLE MODRED.STATUS_DOADOR IS 'Status do Doador';
COMMENT ON COLUMN MODRED.STATUS_DOADOR.STDO_ID IS 'Identificação do status doador';
COMMENT ON COLUMN MODRED.STATUS_DOADOR.STDO_TX_DESCRICAO IS 'descricão do status doador';
COMMENT ON COLUMN MODRED.STATUS_DOADOR.STDO_IN_TEMPO_OBRIGATORIO IS 'Coluna que define se o campo tempo é obrigatório.';

INSERT INTO MODRED.STATUS_DOADOR VALUES (1, 'Ativo', 0);
INSERT INTO MODRED.STATUS_DOADOR VALUES (2, 'Ativo com ressalva', 0);
INSERT INTO MODRED.STATUS_DOADOR VALUES (3, 'Inativo temporário', 1);
INSERT INTO MODRED.STATUS_DOADOR VALUES (4, 'Inativo permanente', 0);

CREATE TABLE MODRED.MOTIVO_STATUS_DOADOR 
(
  MOSD_ID NUMBER NOT NULL, 
  MOSD_TX_DESCRICAO VARCHAR2(40) NOT NULL, 
  STDO_ID NUMBER NOT NULL, 
  CONSTRAINT PK_MOSD PRIMARY KEY 
  (
    MOSD_ID 
  )
  USING INDEX 
  (
      CREATE UNIQUE INDEX PK_MOSD ON MODRED.MOTIVO_STATUS_DOADOR (MOSD_ID ASC) 
  )
  ENABLE 
);

CREATE INDEX IN_FK_MOSD_STDO ON MODRED.MOTIVO_STATUS_DOADOR (STDO_ID ASC);

ALTER TABLE MODRED.MOTIVO_STATUS_DOADOR
ADD CONSTRAINT FK_MOSD_STDO FOREIGN KEY
(
  STDO_ID 
)
REFERENCES MODRED.STATUS_DOADOR
(
  STDO_ID 
)
ENABLE;

COMMENT ON TABLE MODRED.MOTIVO_STATUS_DOADOR IS 'Motivo Status Doador';
COMMENT ON COLUMN MODRED.MOTIVO_STATUS_DOADOR.MOSD_ID IS 'Identificação do motivo status doador';
COMMENT ON COLUMN MODRED.MOTIVO_STATUS_DOADOR.STDO_ID IS 'Id do status doador';

INSERT INTO MODRED.MOTIVO_STATUS_DOADOR VALUES (1, 'Óbito', 4);
INSERT INTO MODRED.MOTIVO_STATUS_DOADOR VALUES (2, 'Presidiário', 3);
INSERT INTO MODRED.MOTIVO_STATUS_DOADOR VALUES (3, 'Gravidez', 3);

CREATE TABLE MODRED.MOTIVO_STATUS_DOADOR_RECURSO 
(
  MOSD_ID NUMBER NOT NULL, 
  RECU_ID NUMBER NOT NULL, 
  CONSTRAINT PK_MOSR PRIMARY KEY 
  (
    MOSD_ID 
  , RECU_ID 
  )
  USING INDEX 
  (
      CREATE UNIQUE INDEX PK_MOSR ON MODRED.MOTIVO_STATUS_DOADOR_RECURSO (MOSD_ID ASC, RECU_ID ASC) 
  )
  ENABLE 
);

CREATE INDEX IN_FK_MOSR_MOSD ON MODRED.MOTIVO_STATUS_DOADOR_RECURSO (MOSD_ID ASC);
CREATE INDEX IN_FK_MOSR_RECU ON MODRED.MOTIVO_STATUS_DOADOR_RECURSO (RECU_ID ASC);

ALTER TABLE MODRED.MOTIVO_STATUS_DOADOR_RECURSO
ADD CONSTRAINT FK_MOSR_MOSD FOREIGN KEY
(
  MOSD_ID 
)
REFERENCES MODRED.MOTIVO_STATUS_DOADOR
(
  MOSD_ID 
)
ENABLE;

ALTER TABLE MODRED.MOTIVO_STATUS_DOADOR_RECURSO
ADD CONSTRAINT FK_MOSR_RECU FOREIGN KEY
(
  RECU_ID 
)
REFERENCES MODRED.RECURSO
(
  RECU_ID 
)
ENABLE;

COMMENT ON TABLE MODRED.MOTIVO_STATUS_DOADOR_RECURSO IS 'Relação entre motivo status doador e recurso';
COMMENT ON COLUMN MODRED.MOTIVO_STATUS_DOADOR_RECURSO.MOSD_ID IS 'Id do Motivo Status Doador';
COMMENT ON COLUMN MODRED.MOTIVO_STATUS_DOADOR_RECURSO.RECU_ID IS 'Id do Recurso';


CREATE TABLE MODRED.DOADOR
   (DOAD_NR_DMR NUMBER NOT NULL, 
	DOAD_TX_CPF VARCHAR2(11 BYTE),  
	DOAD_TX_NOME VARCHAR2(255 BYTE) NOT NULL, 
	DOAD_TX_NOME_MAE VARCHAR2(255 BYTE) NOT NULL, 
	DOAD_IN_SEXO VARCHAR2(1 BYTE) NOT NULL, 
	RACA_ID NUMBER, 
	ETNI_ID NUMBER, 
	DOAD_TX_ABO VARCHAR2(3 BYTE), 
	PAIS_ID NUMBER, 
	UF_SIGLA VARCHAR2(2 BYTE), 
	USUA_ID NUMBER, 
	DOAD_DT_CADASTRO TIMESTAMP (6), 
    DOAD_DT_ATUALIZACAO TIMESTAMP (6), 
	DOAD_DT_NASCIMENTO DATE, 
    STDO_ID NUMBER NOT NULL,
    DOAD_NR_TEMPO_INATIVO NUMBER,
    MOSD_ID NUMBER,
    CONSTRAINT PK_DOAD PRIMARY KEY 
    (
        DOAD_NR_DMR 
    )
    USING INDEX 
    (
        CREATE UNIQUE INDEX PK_DOAD ON MODRED.DOADOR (DOAD_NR_DMR ASC)
    )
    ENABLE);   


   COMMENT ON COLUMN "MODRED"."DOADOR"."DOAD_NR_DMR" IS 'Identificador do Doador';
   COMMENT ON COLUMN "MODRED"."DOADOR"."DOAD_TX_CPF" IS 'Cpf do dador';
   COMMENT ON COLUMN "MODRED"."DOADOR"."DOAD_TX_NOME" IS 'Nome do doador';
   COMMENT ON COLUMN "MODRED"."DOADOR"."DOAD_TX_NOME_MAE" IS 'Nome da Mãe do doador';
   COMMENT ON COLUMN "MODRED"."DOADOR"."DOAD_IN_SEXO" IS 'Sexo do doador (M - masculinho, F- Feminino)';
   COMMENT ON COLUMN "MODRED"."DOADOR"."RACA_ID" IS 'Referencia para tabela raça ';
   COMMENT ON COLUMN "MODRED"."DOADOR"."ETNI_ID" IS 'Referencia para a tabela etnia ';
   COMMENT ON COLUMN "MODRED"."DOADOR"."DOAD_TX_ABO" IS 'Tipo sanguíneo (A+,B+,A-,B-,AB+,AB-,O+,O-)';
   COMMENT ON COLUMN "MODRED"."DOADOR"."PAIS_ID" IS 'Id do país da nacionalidade';
   COMMENT ON COLUMN "MODRED"."DOADOR"."UF_SIGLA" IS 'Id do estado da naturadlidade';
   COMMENT ON COLUMN "MODRED"."DOADOR"."USUA_ID" IS 'Referencia do usuario que cadastrou';
   COMMENT ON COLUMN "MODRED"."DOADOR"."DOAD_DT_CADASTRO" IS 'Data do cadastro ';
   COMMENT ON COLUMN "MODRED"."DOADOR"."DOAD_DT_ATUALIZACAO" IS 'Data da ultima atualização ';
   COMMENT ON COLUMN "MODRED"."DOADOR"."DOAD_DT_NASCIMENTO" IS 'Data de nascimento do doador';
   COMMENT ON COLUMN "MODRED"."DOADOR"."STDO_ID" IS 'Id do status do doador';
   COMMENT ON COLUMN "MODRED"."DOADOR"."DOAD_NR_TEMPO_INATIVO" IS 'Tempo em que o paciente ficará inativo';
   COMMENT ON COLUMN "MODRED"."DOADOR"."MOSD_ID" IS 'ID do motivo do status doador';
   COMMENT ON TABLE "MODRED"."DOADOR"  IS 'Doadores';
   
   CREATE SEQUENCE MODRED.SQ_DOAD_NR_DMR INCREMENT BY 1 MAXVALUE 9999999999999999999999999999 MINVALUE 1 CACHE 20;

  CREATE INDEX "MODRED"."IN_FK_DOAD_UF" ON "MODRED"."DOADOR" ("UF_SIGLA");

ALTER TABLE MODRED.DOADOR
ADD CONSTRAINT FK_DOAD_UF FOREIGN KEY
(
  UF_SIGLA 
)
REFERENCES MODRED.UF
(
  UF_SIGLA 
)
ENABLE;  
  
  CREATE INDEX "MODRED"."IN_FK_DOAD_USUA" ON "MODRED"."DOADOR" ("USUA_ID");

ALTER TABLE MODRED.DOADOR
ADD CONSTRAINT FK_DOAD_USUA FOREIGN KEY
(
  USUA_ID 
)
REFERENCES MODRED.USUARIO
(
  USUA_ID 
)
ENABLE;  

  CREATE INDEX "MODRED"."IN_FK_DOAD_ETNI" ON "MODRED"."DOADOR" ("ETNI_ID");

ALTER TABLE MODRED.DOADOR
ADD CONSTRAINT FK_DOAD_ETNI FOREIGN KEY
(
  ETNI_ID 
)
REFERENCES MODRED.ETNIA
(
  ETNI_ID 
)
ENABLE;  
  
  CREATE INDEX "MODRED"."IN_FK_DOAD_PAIS" ON "MODRED"."DOADOR" ("PAIS_ID");
ALTER TABLE MODRED.DOADOR
ADD CONSTRAINT FK_DOAD_PAIS FOREIGN KEY
(
  PAIS_ID 
)
REFERENCES MODRED.PAIS
(
  PAIS_ID 
)
ENABLE;

  CREATE INDEX "MODRED"."IN_FK_DOAD_RACA" ON "MODRED"."DOADOR" ("RACA_ID");
ALTER TABLE MODRED.DOADOR
ADD CONSTRAINT FK_DOAD_RACA FOREIGN KEY
(
  RACA_ID 
)
REFERENCES MODRED.RACA
(
  RACA_ID 
)
ENABLE;  
  CREATE INDEX "MODRED"."IN_FK_DOAD_STDO" ON "MODRED"."DOADOR" ("STDO_ID");
ALTER TABLE MODRED.DOADOR
ADD CONSTRAINT FK_DOAD_STDO FOREIGN KEY
(
  STDO_ID 
)
REFERENCES MODRED.STATUS_DOADOR
(
  STDO_ID 
)
ENABLE;  
  CREATE INDEX "MODRED"."IN_FK_DOAD_MOSD" ON "MODRED"."DOADOR" ("MOSD_ID");
ALTER TABLE MODRED.DOADOR
ADD CONSTRAINT FK_DOAD_MOSD FOREIGN KEY
(
  MOSD_ID 
)
REFERENCES MODRED.MOTIVO_STATUS_DOADOR
(
  MOSD_ID 
)
ENABLE;  


CREATE TABLE MODRED.DOADOR_AUD
   (DOAD_NR_DMR NUMBER NOT NULL,
    AUDI_ID NUMBER NOT NULL,
    AUDI_TX_TIPO NUMBER(3,0), 
	DOAD_TX_CPF VARCHAR2(11 BYTE),  
	DOAD_TX_NOME VARCHAR2(255 BYTE), 
	DOAD_TX_NOME_MAE VARCHAR2(255 BYTE), 
	DOAD_IN_SEXO VARCHAR2(1 BYTE), 
	RACA_ID NUMBER, 
	ETNI_ID NUMBER, 
	DOAD_TX_ABO VARCHAR2(3 BYTE), 
	PAIS_ID NUMBER, 
	UF_SIGLA VARCHAR2(2 BYTE), 
	USUA_ID NUMBER, 
	DOAD_DT_CADASTRO TIMESTAMP (6), 
    DOAD_DT_ATUALIZACAO TIMESTAMP (6), 
	DOAD_DT_NASCIMENTO DATE, 
    STDO_ID NUMBER,
    DOAD_NR_TEMPO_INATIVO NUMBER,
    MOSD_ID NUMBER,
    CONSTRAINT PK_DOAU PRIMARY KEY 
    (
        DOAD_NR_DMR,
        AUDI_ID
    )
    USING INDEX 
    (
        CREATE UNIQUE INDEX PK_DOAU ON MODRED.DOADOR_AUD (DOAD_NR_DMR ASC, AUDI_ID ASC )
    )
    ENABLE);
  
CREATE INDEX MODRED.IN_FK_DOAU_AUD ON MODRED.DOADOR_AUD ("AUDI_ID");
ALTER TABLE MODRED.DOADOR_AUD
ADD CONSTRAINT FK_DOAU_AUDI FOREIGN KEY
(
  AUDI_ID 
)
REFERENCES MODRED.AUDITORIA
(
  AUDI_ID 
)
ENABLE;


ALTER TABLE MODRED.CONTATO_TELEFONICO
ADD (COTE_FL_EXCLUIDO NUMBER );

COMMENT ON COLUMN MODRED.CONTATO_TELEFONICO.COTE_FL_EXCLUIDO IS 'Flag lógico para exclusão';

UPDATE MODRED.CONTATO_TELEFONICO SET COTE_FL_EXCLUIDO = 0;
COMMIT;

ALTER TABLE MODRED.CONTATO_TELEFONICO_AUD
ADD (COTE_FL_EXCLUIDO NUMBER );

UPDATE MODRED.CONTATO_TELEFONICO_AUD SET COTE_FL_EXCLUIDO = 0;
COMMIT;

ALTER TABLE MODRED.CONTATO_TELEFONICO 
ADD (DOAD_NR_DMR NUMBER );

ALTER TABLE MODRED.CONTATO_TELEFONICO  
MODIFY (PACI_NR_RMR NULL);

CREATE INDEX IN_FK_COTE_DOAD ON MODRED.CONTATO_TELEFONICO (DOAD_NR_DMR ASC);

ALTER TABLE MODRED.CONTATO_TELEFONICO
ADD CONSTRAINT FK_COTE_DOAD FOREIGN KEY
(
  DOAD_NR_DMR 
)
REFERENCES DOADOR
(
  DOAD_NR_DMR 
)
ENABLE;

COMMENT ON COLUMN MODRED.CONTATO_TELEFONICO.DOAD_NR_DMR IS 'Referencia do doador que possui os telefones';

ALTER TABLE MODRED.CONTATO_TELEFONICO_AUD
ADD (DOAD_NR_DMR NUMBER );


ALTER TABLE MODRED.ENDERECO_CONTATO
ADD (ENCO_FL_EXCLUIDO NUMBER );

COMMENT ON COLUMN MODRED.ENDERECO_CONTATO.ENCO_FL_EXCLUIDO IS 'Flag lógico para exclusão';

UPDATE MODRED.ENDERECO_CONTATO SET ENCO_FL_EXCLUIDO = 0;
COMMIT;

ALTER TABLE MODRED.ENDERECO_CONTATO_AUD
ADD (ENCO_FL_EXCLUIDO NUMBER );

UPDATE MODRED.ENDERECO_CONTATO_AUD SET ENCO_FL_EXCLUIDO = 0;
COMMIT;

ALTER TABLE MODRED.ENDERECO_CONTATO
ADD (PACI_NR_RMR NUMBER );

UPDATE MODRED.ENDERECO_CONTATO E SET E.PACI_NR_RMR = (SELECT PA.PACI_NR_RMR FROM MODRED.PACIENTE PA WHERE PA.ENCO_ID = E.ENCO_ID);
COMMIT;

CREATE INDEX IN_FK_ENCO_PACI ON MODRED.ENDERECO_CONTATO (PACI_NR_RMR ASC);

ALTER TABLE MODRED.ENDERECO_CONTATO
ADD CONSTRAINT FK_ENCO_PACI FOREIGN KEY
(
  PACI_NR_RMR 
)
REFERENCES MODRED.PACIENTE
(
  PACI_NR_RMR 
)
ENABLE;

ALTER TABLE MODRED.ENDERECO_CONTATO_AUD 
ADD (PACI_NR_RMR NUMBER );

UPDATE MODRED.ENDERECO_CONTATO_AUD E SET E.PACI_NR_RMR = (SELECT PA.PACI_NR_RMR FROM MODRED.ENDERECO_CONTATO PA WHERE PA.ENCO_ID = E.ENCO_ID);
COMMIT;

ALTER TABLE MODRED.PACIENTE 
DROP CONSTRAINT FK_PACI_ENCO;

ALTER TABLE MODRED.PACIENTE 
DROP COLUMN ENCO_ID;

ALTER TABLE MODRED.PACIENTE_AUD 
DROP COLUMN ENCO_ID;

ALTER TABLE MODRED.ENDERECO_CONTATO
ADD (DOAD_NR_DMR NUMBER );

CREATE INDEX IN_FK_ENCO_DOAD ON MODRED.ENDERECO_CONTATO (DOAD_NR_DMR ASC);

ALTER TABLE MODRED.ENDERECO_CONTATO
ADD CONSTRAINT FK_ENCO_DOAD FOREIGN KEY
(
  DOAD_NR_DMR 
)
REFERENCES MODRED.DOADOR
(
  DOAD_NR_DMR 
)
ENABLE;

CREATE TABLE MODRED.EMAIL_CONTATO 
(
  EMCO_ID NUMBER NOT NULL, 
  EMCO_TX_EMAIL VARCHAR2(100) NOT NULL, 
  DOAD_NR_DMR NUMBER, 
  EMCO_FL_EXCLUIDO NUMBER,
  CONSTRAINT PK_EMCO PRIMARY KEY 
  (
    EMCO_ID 
  )
  USING INDEX 
  (
      CREATE UNIQUE INDEX PK_EMCO ON MODRED.EMAIL_CONTATO (EMCO_ID ASC) 
  )
  ENABLE 
);

CREATE INDEX IN_FK_EMCO_DOAD ON MODRED.EMAIL_CONTATO (DOAD_NR_DMR ASC);

ALTER TABLE MODRED.EMAIL_CONTATO
ADD CONSTRAINT FK_EMCO_DOAD FOREIGN KEY
(
  DOAD_NR_DMR 
)
REFERENCES MODRED.DOADOR
(
  DOAD_NR_DMR 
)
ENABLE;

COMMENT ON TABLE MODRED.EMAIL_CONTATO IS 'Endereços de email para contato';
COMMENT ON COLUMN MODRED.EMAIL_CONTATO.EMCO_ID IS 'Referência do email contato';
COMMENT ON COLUMN MODRED.EMAIL_CONTATO.EMCO_TX_EMAIL IS 'E-mail';
COMMENT ON COLUMN MODRED.EMAIL_CONTATO.DOAD_NR_DMR IS 'Id do doador';

CREATE SEQUENCE MODRED.SQ_EMCO_ID INCREMENT BY 1 MAXVALUE 9999999999999999999999999999 MINVALUE 1 CACHE 20;

CREATE TABLE MODRED.EMAIL_CONTATO_AUD 
(
  EMCO_ID NUMBER NOT NULL, 
  AUDI_ID NUMBER NOT NULL,
  AUDI_TX_TIPO NUMBER(3,0),   
  EMCO_TX_EMAIL VARCHAR2(100) NOT NULL, 
  DOAD_NR_DMR NUMBER,
  EMCO_FL_EXCLUIDO NUMBER,
  CONSTRAINT PK_EMCA PRIMARY KEY 
  (
    EMCO_ID,
    AUDI_ID
  )
  USING INDEX 
  (
      CREATE UNIQUE INDEX PK_EMCA ON MODRED.EMAIL_CONTATO_AUD (EMCO_ID ASC, AUDI_ID ASC) 
  )
  ENABLE 
);

CREATE INDEX MODRED.IN_FK_EMCA_AUDI ON MODRED.EMAIL_CONTATO_AUD ("AUDI_ID");
ALTER TABLE MODRED.EMAIL_CONTATO_AUD
ADD CONSTRAINT FK_EMCA_AUDI FOREIGN KEY
(
  AUDI_ID 
)
REFERENCES MODRED.AUDITORIA
(
  AUDI_ID 
)
ENABLE;

--SOLICITACAO

CREATE TABLE MODRED.TIPO_SOLICITACAO 
(
  TISO_ID NUMBER NOT NULL 
, TISO_TX_DESCRICAO VARCHAR2(20) NOT NULL 
, CONSTRAINT PK_TISO PRIMARY KEY 
  (
    TISO_ID 
  )
  USING INDEX 
  (
      CREATE UNIQUE INDEX PK_TISO ON MODRED.TIPO_SOLICITACAO (TISO_ID ASC) 
  )
  ENABLE 
);

COMMENT ON TABLE MODRED.TIPO_SOLICITACAO IS 'Tipos de solicitação';
COMMENT ON COLUMN MODRED.TIPO_SOLICITACAO.TISO_ID IS 'Referência do tipo de solicitação';
COMMENT ON COLUMN MODRED.TIPO_SOLICITACAO.TISO_TX_DESCRICAO IS 'Descrição tipo de solicitação';


CREATE TABLE MODRED.SOLICITACAO 
(
  SOLI_ID NUMBER NOT NULL, 
  DOAD_NR_DMR NUMBER NOT NULL, 
  TISO_ID NUMBER NOT NULL, 
  CONSTRAINT PK_SOLI PRIMARY KEY 
  (
    SOLI_ID 
  )
  USING INDEX 
  (
      CREATE UNIQUE INDEX PK_SOLI ON MODRED.SOLICITACAO (SOLI_ID ASC) 
  )
  ENABLE 
);

CREATE INDEX IN_FK_SOLI_DOAD ON MODRED.SOLICITACAO (DOAD_NR_DMR ASC);
CREATE INDEX IN_FK_SOLI_TISO ON MODRED.SOLICITACAO (TISO_ID ASC);

ALTER TABLE MODRED.SOLICITACAO
ADD CONSTRAINT FK_SOLI_DOAD FOREIGN KEY
(
  DOAD_NR_DMR 
)
REFERENCES MODRED.DOADOR
(
  DOAD_NR_DMR 
)
ENABLE;

ALTER TABLE MODRED.SOLICITACAO
ADD CONSTRAINT FK_SOLI_TISO FOREIGN KEY
(
  TISO_ID 
)
REFERENCES MODRED.TIPO_SOLICITACAO
(
  TISO_ID 
)
ENABLE;

COMMENT ON TABLE MODRED.SOLICITACAO IS 'Solicitação';
COMMENT ON COLUMN MODRED.SOLICITACAO.SOLI_ID IS 'Referência da solicitação';
COMMENT ON COLUMN MODRED.SOLICITACAO.DOAD_NR_DMR IS 'Id do doador';
COMMENT ON COLUMN MODRED.SOLICITACAO.TISO_ID IS 'Id do tipo de solicitação';

CREATE SEQUENCE MODRED.SQ_SOLI_ID INCREMENT BY 1 MAXVALUE 9999999999999999999999999999 MINVALUE 1 CACHE 20;

CREATE TABLE MODRED.PEDIDO_ENRIQUECIMENTO 
(
  PEEN_ID NUMBER NOT NULL 
, SOLI_ID NUMBER NOT NULL 
, PEEN_DT_ENRIQUECIMENTO TIMESTAMP 
, USUA_ID NUMBER 
, PEEN_DT_CRIACAO TIMESTAMP 
, PEEN_FL_FECHADO NUMBER(1) 
, CONSTRAINT PK_PEEN_ID PRIMARY KEY 
  (
    PEEN_ID 
  )
  USING INDEX 
  (
      CREATE UNIQUE INDEX PK_PEEN_ID ON MODRED.PEDIDO_ENRIQUECIMENTO (PEEN_ID ASC) 
  )
  ENABLE 
);

CREATE INDEX IN_FK_PEEN_SOLI ON MODRED.PEDIDO_ENRIQUECIMENTO (SOLI_ID ASC);
CREATE INDEX IN_FK_PEEN_USUA ON MODRED.PEDIDO_ENRIQUECIMENTO (USUA_ID ASC);

ALTER TABLE MODRED.PEDIDO_ENRIQUECIMENTO
ADD CONSTRAINT FK_PEEN_SOLI FOREIGN KEY
(
  SOLI_ID 
)
REFERENCES MODRED.SOLICITACAO
(
  SOLI_ID 
)
ENABLE;

ALTER TABLE MODRED.PEDIDO_ENRIQUECIMENTO
ADD CONSTRAINT FK_PEEN_USUA FOREIGN KEY
(
  USUA_ID 
)
REFERENCES MODRED.USUARIO
(
  USUA_ID 
)
ENABLE;

COMMENT ON TABLE MODRED.PEDIDO_ENRIQUECIMENTO IS 'Pedido de enriquecimento';
COMMENT ON COLUMN MODRED.PEDIDO_ENRIQUECIMENTO.PEEN_ID IS 'Referência do pedido de enriquecimento';
COMMENT ON COLUMN MODRED.PEDIDO_ENRIQUECIMENTO.SOLI_ID IS 'Id da solicitação';
COMMENT ON COLUMN MODRED.PEDIDO_ENRIQUECIMENTO.PEEN_DT_ENRIQUECIMENTO IS 'Data em que foi realizado o enriquecimento';
COMMENT ON COLUMN MODRED.PEDIDO_ENRIQUECIMENTO.USUA_ID IS 'Identificação do usuário que fez o enriquecimento';
COMMENT ON COLUMN MODRED.PEDIDO_ENRIQUECIMENTO.PEEN_DT_CRIACAO IS 'Data de criação';
COMMENT ON COLUMN MODRED.PEDIDO_ENRIQUECIMENTO.PEEN_FL_FECHADO IS 'Flag que indica se o enriquecimento está aberto ou fechado';

CREATE SEQUENCE MODRED.SQ_PEEN_ID INCREMENT BY 1 MAXVALUE 9999999999999999999999999999 MINVALUE 1 CACHE 20;

--Criação dos perfis 
insert into MODRED.perfil (perf_id, perf_tx_descricao,perf_nr_entity_status)
values (6, 'ENRIQUECEDOR', 1);

insert into MODRED.perfil (perf_id, perf_tx_descricao,perf_nr_entity_status)
values (7, 'ANALISTA_CONTATO_FASE2', 1);

insert into MODRED.perfil (perf_id, perf_tx_descricao,perf_nr_entity_status)
values (8, 'ANALISTA_CONTATO_FASE3', 1);

INSERT INTO MODRED.RECURSO (RECU_ID, RECU_TX_SIGLA, RECU_TX_DESCRICAO)
VALUES(23, 'CONTACTAR_FASE_2','Permite que se possa contactar o doador na fase 2.');

INSERT INTO MODRED.RECURSO (RECU_ID, RECU_TX_SIGLA, RECU_TX_DESCRICAO)
VALUES(24, 'CONTACTAR_FASE_3','Permite que se possa contactar o doador na fase 3.');

INSERT INTO MODRED.RECURSO (RECU_ID, RECU_TX_SIGLA, RECU_TX_DESCRICAO)
VALUES(25, 'ENRIQUECER_DOADOR','Permite enriquecer os dados do doador.');

insert into MODRED.permissao (recu_id, perf_id, perm_in_com_restricao) values(25, 6, 0);
insert into MODRED.permissao (recu_id, perf_id, perm_in_com_restricao) values(23, 7, 0);
insert into MODRED.permissao (recu_id, perf_id, perm_in_com_restricao) values(24, 8, 0);

insert into MODRED.usuario (usua_id, usua_tx_nome, usua_tx_username,usua_tx_password,usua_in_ativo,usua_nr_entity_status)
values(6,'ENRIQUECEDOR','ENRIQUECEDOR','$2a$11$KMAfznbkZhx9mvESGy3.GewPWbSGQLVTnj5O0m7cIo5NrucDtcXT.',1,1);

INSERT INTO MODRED.usuario_perfil (USUA_ID,PERF_ID)
VALUES (6,6);
COMMIT;

--ADICIONANDO CAMPO NOME SOCIAL EM DOADOR
ALTER TABLE MODRED.DOADOR 
ADD (DOAD_TX_NOME_SOCIAL VARCHAR2(255) );

COMMENT ON COLUMN MODRED.DOADOR.DOAD_TX_NOME_SOCIAL IS 'Nome Social do doador';

ALTER TABLE MODRED.DOADOR_AUD 
ADD (DOAD_TX_NOME_SOCIAL VARCHAR2(255) );
commit;

--PROCEDURE PARA CRIAR DOADOR
create or replace procedure MODRED.proc_criar_doador (dmr in NUMBER,cpf in VARCHAR2,
nome	in	VARCHAR2,
nomeMae	in VARCHAR2,
sexo	in	VARCHAR2,
racaId		in		NUMBER,
etniaId		in		NUMBER,
abo		in	VARCHAR2,
paisId		in		NUMBER,
ufSigla		in	VARCHAR2,
usuarioId		in		NUMBER,
dtCadastro	in TIMESTAMP,
dtAtualizacao	in TIMESTAMP,
dtNascimento in	DATE,
statusDoadorId		in		NUMBER,
tempoInativo in	NUMBER,
motivoStatusId		in		NUMBER,
nomeSocial	in VARCHAR2)

is

 AUDI_ID NUMBER(10,0);

begin

    SELECT MODRED.SQ_AUDI_ID.NEXTVAL INTO AUDI_ID FROM dual;

    insert into MODRED.doador(DOAD_NR_DMR,DOAD_TX_CPF,DOAD_TX_NOME,DOAD_TX_NOME_MAE,DOAD_IN_SEXO,
        RACA_ID,ETNI_ID,DOAD_TX_ABO,PAIS_ID,UF_SIGLA,USUA_ID,DOAD_DT_CADASTRO,
        DOAD_DT_ATUALIZACAO,DOAD_DT_NASCIMENTO,STDO_ID,DOAD_NR_TEMPO_INATIVO,MOSD_ID, DOAD_TX_NOME_SOCIAL)
    values (
        dmr,cpf,nome,nomeMae,sexo,racaId, etniaId,abo,paisId,ufSigla,usuarioId,dtCadastro,dtAtualizacao,
        dtNascimento,statusDoadorId,tempoInativo,motivoStatusId,nomeSocial
    );

    insert into modred.auditoria(AUDI_ID,AUDI_DT_DATA,AUDI_TX_USUARIO) values(AUDI_ID,sysdate,'SISTEMA');

    insert into MODRED.doador_aud(DOAD_NR_DMR,AUDI_ID,AUDI_TX_TIPO,DOAD_TX_CPF,DOAD_TX_NOME,DOAD_TX_NOME_MAE,DOAD_IN_SEXO,
    RACA_ID,ETNI_ID,DOAD_TX_ABO,PAIS_ID,UF_SIGLA,USUA_ID,DOAD_DT_CADASTRO,
    DOAD_DT_ATUALIZACAO,DOAD_DT_NASCIMENTO,STDO_ID,DOAD_NR_TEMPO_INATIVO,MOSD_ID, DOAD_TX_NOME_SOCIAL)
    values (
    dmr,AUDI_ID,0,
    cpf,nome,nomeMae,sexo,racaId, etniaId,abo,paisId,ufSigla,usuarioId,dtCadastro,dtAtualizacao,
    dtNascimento,statusDoadorId,tempoInativo,motivoStatusId,nomeSocial
    );


end;

execute modred.proc_criar_doador (SQ_DOAD_NR_DMR.nextval,'27743668878','Joaquim Barbosa','Maria Josefina',	'M',	1,		null,	'AB+',	31,		'RJ',		1,			sysdate,	sysdate,		'24-02-1986',		1,			null,			null,			'Nome social');
execute modred.proc_criar_doador (SQ_DOAD_NR_DMR.nextval,'82671018100','Raissa Araujo Cunha','Carla Araujo Cunha',	'F',	2,		null,	'A+',	31,		'SP',		1,			sysdate,	sysdate,		'30-08-1956',		2,			30,			null,			'Nome social');
execute modred.proc_criar_doador (SQ_DOAD_NR_DMR.nextval,'12719386189','Kauã Pinto Costa','Fernanda Pinto Costa',	'M',	3,		null,	'B+',	31,		'RJ',		1,			sysdate,	sysdate,		'17-11-1938',		3,			40,			2,			'Nome social');
execute modred.proc_criar_doador (SQ_DOAD_NR_DMR.nextval,'12077765305','Leila Fernandes Dias','Joaquina Fernandes Dias',	'F',	1,		null,	'O+',	31,		'RJ',		1,			sysdate,	sysdate,		'16-08-1978',		1,			null,			null,			'Nome social');
execute modred.proc_criar_doador (SQ_DOAD_NR_DMR.nextval,'88157910196','Leonardo Azevedo Araujo','Carina Azevedo Araujo',	'M',	1,		null,	'A+',	31,		'SP',		1,			sysdate,	sysdate,		'05-05-1982',		1,			null,			null,			'Nome social');
execute modred.proc_criar_doador (SQ_DOAD_NR_DMR.nextval,'58685341310','Eduardo Rodrigues Lima','Joana Rodrigues Lima',	'M',	1,		null,	'B-',	31,		'SP',		1,			sysdate,	sysdate,		'17-02-1956',		2,			20,			null,			'Nome social');
execute modred.proc_criar_doador (SQ_DOAD_NR_DMR.nextval,'68348289585','Marcos Souza Carvalho','Carla Souza Carvalho',	'M',	3,		null,	'AB-',	31,		'RJ',		1,			sysdate,	sysdate,		'18-10-1962',		3,			null,			2,			'Nome social');
execute modred.proc_criar_doador (SQ_DOAD_NR_DMR.nextval,'21535266988','Felipe Oliveira Gomes','Barbara Oliveira Gomes',	'M',	3,		null,	'B+',	31,		'RJ',		1,			sysdate,	sysdate,		'20-10-1941',		1,			null,			null,			'Nome social');
execute modred.proc_criar_doador (SQ_DOAD_NR_DMR.nextval,'27741258878','Diego Barros Castro','Maria Barros Castro',	'M',	1,		null,	'A+',	31,		'RJ',		1,			sysdate,	sysdate,		'05-08-1965',		2,			50,			null,			'Nome social');
execute modred.proc_criar_doador (SQ_DOAD_NR_DMR.nextval,'10495006874','Guilherme Almeida Santos','Eliana Almeida Santos',	'M',	1,		null,	'AB+',	31,		'RJ',		1,			sysdate,	sysdate,		'18-09-1948',		2,			180,			null,			'Nome social');
execute modred.proc_criar_doador (SQ_DOAD_NR_DMR.nextval,'95642120735','Murilo Barbosa Oliveira','Patricia Barbosa Oliveira',	'M',	1,		null,	'O+',	31,		'RJ',		1,			sysdate,	sysdate,		'05-08-1945',		1,			null,			null,			'Nome social');
execute modred.proc_criar_doador (SQ_DOAD_NR_DMR.nextval,'19779058036','Bruno Rocha Almeida','Angelica Rocha Almeida',	'M',	1,		null,	'AB+',	31,		'RJ',		1,			sysdate,	sysdate,		'24-02-1986',		2,			70,			null,			'Nome social');
execute modred.proc_criar_doador (SQ_DOAD_NR_DMR.nextval,'90809295490','Vitoria Alves Costa','Carina Alves Costa',	'F',	1,		null,	'B+',	31,		'RJ',		1,			sysdate,	sysdate,		'04-08-1989',		3,			null,			2,			'Nome social');
execute modred.proc_criar_doador (SQ_DOAD_NR_DMR.nextval,'22659512000','Nicole Fernandes Araujo','Katrina Fernandes Araujo',	'F',	3,		null,	'A+',	31,		'RJ',		1,			sysdate,	sysdate,		'03-01-1951',		1,			null,			null,			'Nome social');

drop procedure modred.proc_criar_doador;
commit;

-- TABELA DE RESPOSTAS TENTATIVA CONTATO
CREATE TABLE MODRED.RESPOSTA_TENTATIVA_CONTATO (
    RTCO_ID NUMBER NOT NULL, 
	RTCO_TX_DESCRICAO VARCHAR(100) NOT NULL,
    CONSTRAINT PK_RTCO PRIMARY KEY (RTCO_ID) 
        USING INDEX(CREATE UNIQUE INDEX PK_RTCO ON MODRED.RESPOSTA_TENTATIVA_CONTATO (RTCO_ID ASC)) ENABLE 
);
COMMENT ON COLUMN MODRED.RESPOSTA_TENTATIVA_CONTATO.RTCO_ID IS 'Identificador sequencial da tabela.';
COMMENT ON COLUMN MODRED.RESPOSTA_TENTATIVA_CONTATO.RTCO_TX_DESCRICAO IS 'Descrição do resultado da tentativa de contato realizada.';

CREATE SEQUENCE MODRED.SQ_RTCO_ID INCREMENT BY 1 MAXVALUE 9999999999999999999999999999 MINVALUE 1 CACHE 20;


-- TABELA DE PEDIDO_CONTATO
CREATE TABLE MODRED.PEDIDO_CONTATO (
    PECO_ID NUMBER NOT NULL, 
	PECO_DT_CRIACAO TIMESTAMP NOT NULL,
    PECO_ABERTO NUMBER NOT NULL,
    SOLI_ID NUMBER NOT NULL,
    CONSTRAINT PK_PECO PRIMARY KEY (PECO_ID) 
        USING INDEX(CREATE UNIQUE INDEX PK_PECO ON MODRED.PEDIDO_CONTATO (PECO_ID ASC)) ENABLE,
    CONSTRAINT FK_PECO_SOLI FOREIGN KEY ( SOLI_ID )
        REFERENCES MODRED.SOLICITACAO ( SOLI_ID ) ENABLE
);
COMMENT ON COLUMN MODRED.PEDIDO_CONTATO.PECO_ID IS 'Identificador sequencial da tabela.';
COMMENT ON COLUMN MODRED.PEDIDO_CONTATO.PECO_DT_CRIACAO IS 'Data realização do pedido.';
COMMENT ON COLUMN MODRED.PEDIDO_CONTATO.PECO_ABERTO IS 'Indica se o pedido está em 1-aberto ou 0-fechado.';

CREATE SEQUENCE MODRED.SQ_PECO_ID INCREMENT BY 1 MAXVALUE 9999999999999999999999999999 MINVALUE 1 CACHE 20;
CREATE INDEX IN_FK_PECO_SOLI ON MODRED.PEDIDO_CONTATO (SOLI_ID ASC);


-- TABELA DE RESPOSTAS TENTATIVA CONTATO
CREATE TABLE MODRED.TENTATIVA_CONTATO_DOADOR (
    TCDO_ID NUMBER NOT NULL, 
	TCDO_DT_CRIACAO TIMESTAMP NOT NULL,
    USUA_ID NUMBER NOT NULL,
    RTCO_ID NUMBER NOT NULL,
    PECO_ID NUMBER NOT NULL,
    CONSTRAINT PK_TCDO PRIMARY KEY (TCDO_ID) 
        USING INDEX(CREATE UNIQUE INDEX PK_TCDO ON MODRED.TENTATIVA_CONTATO_DOADOR (TCDO_ID ASC)) ENABLE ,
    CONSTRAINT FK_TCDO_RTCO FOREIGN KEY ( RTCO_ID )
        REFERENCES MODRED.RESPOSTA_TENTATIVA_CONTATO ( RTCO_ID ) ENABLE,
    CONSTRAINT FK_TCDO_PECO FOREIGN KEY ( PECO_ID )
        REFERENCES MODRED.PEDIDO_CONTATO ( PECO_ID ) ENABLE
);

COMMENT ON COLUMN MODRED.TENTATIVA_CONTATO_DOADOR.TCDO_ID IS 'Identificador sequencial da tabela.';
COMMENT ON COLUMN MODRED.TENTATIVA_CONTATO_DOADOR.TCDO_DT_CRIACAO IS 'Data realização da tentativa de contato.';
COMMENT ON COLUMN MODRED.TENTATIVA_CONTATO_DOADOR.USUA_ID IS 'Usuário responsável pela tentativa de contato.';
COMMENT ON COLUMN MODRED.TENTATIVA_CONTATO_DOADOR.RTCO_ID IS 'Identificador da resposta da tentativa de contato.';
COMMENT ON COLUMN MODRED.TENTATIVA_CONTATO_DOADOR.PECO_ID IS 'Identificador do pedido de contato.';

CREATE SEQUENCE MODRED.SQ_TCDO_ID INCREMENT BY 1 MAXVALUE 9999999999999999999999999999 MINVALUE 1 CACHE 20;
CREATE INDEX IN_FK_TCDO_RTCO ON MODRED.TENTATIVA_CONTATO_DOADOR (RTCO_ID ASC);
CREATE INDEX IN_FK_TCDO_PECO ON MODRED.TENTATIVA_CONTATO_DOADOR (PECO_ID ASC);

CREATE SEQUENCE MODRED.SQ_RTCO_ID INCREMENT BY 1 MAXVALUE 9999999999999999999999999999 MINVALUE 1 CACHE 20;
COMMIT;


--ALTERACOES EM TAREFA

ALTER TABLE MODRED.TAREFA RENAME COLUMN TARE_NR_INCLUSIVO_EXCLUSIVO TO TARE_IN_INCLUSIVO_EXCLUSIVO;

ALTER TABLE MODRED.TAREFA  
MODIFY (TARE_DT_INICIO TIMESTAMP );

ALTER TABLE MODRED.TAREFA  
MODIFY (TARE_DT_FIM TIMESTAMP );

ALTER TABLE MODRED.TAREFA 
ADD (TARE_IN_AGENDADO NUMBER AS ( CASE WHEN TARE_DT_INICIO IS NOT NULL OR TAREFA.TARE_DT_INICIO IS NOT NULL THEN 1
ELSE 0
END ) VIRTUAL );


--Cria a configuração do tempo maximo que o doador pode ter sido atualizado para 
--que possa criar um novo pedido de enriquecimento
insert into modred.configuracao (conf_id, conf_tx_valor) values('tempoMaximoEnriquecimentoEmDias','60');

--Criando tipos de solicitacao
insert into modred.tipo_solicitacao (tiso_id, tiso_tx_descricao) values (1, 'FASE 2');
insert into modred.tipo_solicitacao (tiso_id, tiso_tx_descricao) values (2, 'FASE 3');

--Criação de tipos de tarefas
insert into modred.tipo_tarefa (tita_id, TITA_TX_DESCRICAO, tita_in_automatica, tita_nr_tempo_execucao)
values (10,'ENRIQUECIMENTO_FASE_2',0,null);

insert into modred.tipo_tarefa (tita_id, TITA_TX_DESCRICAO, tita_in_automatica, tita_nr_tempo_execucao)
values (11,'ENRIQUECIMENTO_FASE_3',0,null);

insert into modred.tipo_tarefa (tita_id, TITA_TX_DESCRICAO, tita_in_automatica, tita_nr_tempo_execucao)
values (12,'CONTACTAR_FASE_2',0,null);

insert into modred.tipo_tarefa (tita_id, TITA_TX_DESCRICAO, tita_in_automatica, tita_nr_tempo_execucao)
values (13,'CONTACTAR_FASE_3',0,null);

insert into modred.tipo_tarefa (tita_id, TITA_TX_DESCRICAO, tita_in_automatica, tita_nr_tempo_execucao)
values (14,'TIMEOUT',1,null);

-- INCLUSÃO DO PACIENTE NA RELAÇÃO COM DOADOR (PROVISORIAMENTE, REPRESENTADO PELA ENTIDADE SOLICITACAO)
ALTER TABLE MODRED.SOLICITACAO
ADD PACI_NR_RMR NUMBER NOT NULL;

-- INCLUSÃO DO TIPO DE TAREFA - SMS
INSERT INTO MODRED.TIPO_TAREFA (TITA_ID, TITA_TX_DESCRICAO, TITA_IN_AUTOMATICA) VALUES ('15', 'SMS', '0');

-- INCLUSÃO DO TIPO DE TAREFA - CONTATO HEMOCENTRO
INSERT INTO MODRED.TIPO_TAREFA (TITA_ID, TITA_TX_DESCRICAO, TITA_IN_AUTOMATICA) VALUES (20, 'CONTATO_HEMOCENTRO', '0');

-- PEDIDO_EXAME
CREATE TABLE MODRED.PEDIDO_EXAME (
    PEEX_ID NUMBER NOT NULL, 
	PEEX_DT_CRIACAO TIMESTAMP NOT NULL,
    DOAD_NR_DMR NUMBER NOT NULL,
    PEEX_NR_STATUS NUMBER NOT NULL,
    CONSTRAINT PK_PEEX PRIMARY KEY (PEEX_ID) 
        USING INDEX(CREATE UNIQUE INDEX PK_PEEX ON MODRED.PEDIDO_EXAME (PEEX_ID ASC)) ENABLE,
    CONSTRAINT FK_PEEX_DOAD FOREIGN KEY ( DOAD_NR_DMR )
        REFERENCES MODRED.DOADOR ( DOAD_NR_DMR ) ENABLE
);
COMMENT ON COLUMN MODRED.PEDIDO_EXAME.PEEX_ID IS 'Identificador sequencial da tabela.';
COMMENT ON COLUMN MODRED.PEDIDO_EXAME.PEEX_DT_CRIACAO IS 'Data realização do pedido.';
COMMENT ON COLUMN MODRED.PEDIDO_EXAME.DOAD_NR_DMR IS 'Referência para o doador relacionado ao pedido.';
COMMENT ON COLUMN MODRED.PEDIDO_EXAME.PEEX_NR_STATUS IS 'Indica o status do pedido de exame (0-SOLICITADO, 1-REALIZADO,	2-CANCELADO).';

CREATE SEQUENCE MODRED.SQ_PEEX_ID INCREMENT BY 1 MAXVALUE 9999999999999999999999999999 MINVALUE 1 CACHE 20;

--RENOMEANDO COLUNA
ALTER TABLE MODRED.CONTATO_TELEFONICO RENAME COLUMN COTE_FL_EXCLUIDO TO COTE_IN_EXCLUIDO;
ALTER TABLE MODRED.CONTATO_TELEFONICO  
MODIFY (COTE_IN_EXCLUIDO DEFAULT 0 NOT NULL);

ALTER TABLE MODRED.EMAIL_CONTATO RENAME COLUMN EMCO_FL_EXCLUIDO TO EMCO_IN_EXCLUIDO;
ALTER TABLE MODRED.EMAIL_CONTATO_AUD RENAME COLUMN EMCO_FL_EXCLUIDO TO EMCO_IN_EXCLUIDO;

ALTER TABLE MODRED.PEDIDO_ENRIQUECIMENTO RENAME COLUMN PEEN_FL_FECHADO TO PEEN_IN_ABERTO;
COMMENT ON COLUMN MODRED.PEDIDO_ENRIQUECIMENTO.PEEN_IN_ABERTO IS 'Flag que indica se o enriquecimento está 1-aberto ou 0-fechado';
ALTER TABLE MODRED.PEDIDO_ENRIQUECIMENTO  
MODIFY (PEEN_IN_ABERTO DEFAULT 1 NOT NULL);

ALTER TABLE MODRED.PEDIDO_CONTATO RENAME COLUMN PECO_ABERTO TO PECO_IN_ABERTO;
ALTER TABLE MODRED.PEDIDO_CONTATO  
MODIFY (PECO_IN_ABERTO DEFAULT 1);

ALTER TABLE MODRED.CONTATO_TELEFONICO_AUD RENAME COLUMN COTE_FL_EXCLUIDO TO COTE_IN_EXCLUIDO;

ALTER TABLE MODRED.ENDERECO_CONTATO RENAME COLUMN ENCO_FL_EXCLUIDO TO ENCO_IN_EXCLUIDO;
ALTER TABLE MODRED.ENDERECO_CONTATO_AUD RENAME COLUMN ENCO_FL_EXCLUIDO TO ENCO_IN_EXCLUIDO;
ALTER TABLE MODRED.ENDERECO_CONTATO
MODIFY (ENCO_IN_EXCLUIDO DEFAULT 0 NOT NULL );


-- REAJUSTANDO NOME DA TABELA E COLUNAS SEGUNDO PADRÃO
DROP TABLE MODRED.TENTATIVA_CONTATO_DOADOR CASCADE CONSTRAINTS;
DROP SEQUENCE MODRED.SQ_TCDO_ID;
DROP TABLE MODRED.RESPOSTA_TENTATIVA_CONTATO CASCADE CONSTRAINTS;
DROP SEQUENCE MODRED.SQ_RTCO_ID;

-- RECRIANDO - TABELA DE RESPOSTAS TENTATIVA CONTATO
CREATE TABLE MODRED.RESPOSTA_TENTATIVA_CONTATO (
    RETC_ID NUMBER NOT NULL, 
	RETC_TX_DESCRICAO VARCHAR(100) NOT NULL,
    CONSTRAINT PK_RETC PRIMARY KEY (RETC_ID) 
        USING INDEX(CREATE UNIQUE INDEX PK_RETC ON MODRED.RESPOSTA_TENTATIVA_CONTATO (RETC_ID ASC)) ENABLE 
);
COMMENT ON TABLE MODRED.RESPOSTA_TENTATIVA_CONTATO IS 'Resposta para a tentativa de contato com o doador.';
COMMENT ON COLUMN MODRED.RESPOSTA_TENTATIVA_CONTATO.RETC_ID IS 'Identificador sequencial da tabela.';
COMMENT ON COLUMN MODRED.RESPOSTA_TENTATIVA_CONTATO.RETC_TX_DESCRICAO IS 'Descrição do resultado da tentativa de contato realizada.';

CREATE SEQUENCE MODRED.SQ_RETC_ID INCREMENT BY 1 MAXVALUE 9999999999999999999999999999 MINVALUE 1 CACHE 20;

-- RECRIANDO - TABELA DE RESPOSTAS TENTATIVA CONTATO
CREATE TABLE MODRED.TENTATIVA_CONTATO_DOADOR (
    TECD_ID NUMBER NOT NULL, 
	TECD_DT_CRIACAO TIMESTAMP NOT NULL,
    USUA_ID NUMBER NOT NULL,
    RETC_ID NUMBER NOT NULL,
    PECO_ID NUMBER NOT NULL,
    CONSTRAINT PK_TECD PRIMARY KEY (TECD_ID) 
        USING INDEX(CREATE UNIQUE INDEX PK_TECD ON MODRED.TENTATIVA_CONTATO_DOADOR (TECD_ID ASC)) ENABLE ,
    CONSTRAINT FK_TECD_RETC FOREIGN KEY ( RETC_ID )
        REFERENCES MODRED.RESPOSTA_TENTATIVA_CONTATO ( RETC_ID ) ENABLE,
    CONSTRAINT FK_TECD_PECO FOREIGN KEY ( PECO_ID )
        REFERENCES MODRED.PEDIDO_CONTATO ( PECO_ID ) ENABLE
);

COMMENT ON TABLE MODRED.TENTATIVA_CONTATO_DOADOR IS 'Tentativa de contato com o doador.';
COMMENT ON COLUMN MODRED.TENTATIVA_CONTATO_DOADOR.TECD_ID IS 'Identificador sequencial da tabela.';
COMMENT ON COLUMN MODRED.TENTATIVA_CONTATO_DOADOR.TECD_DT_CRIACAO IS 'Data realização da tentativa de contato.';
COMMENT ON COLUMN MODRED.TENTATIVA_CONTATO_DOADOR.USUA_ID IS 'Usuário responsável pela tentativa de contato.';
COMMENT ON COLUMN MODRED.TENTATIVA_CONTATO_DOADOR.RETC_ID IS 'Identificador da resposta da tentativa de contato.';
COMMENT ON COLUMN MODRED.TENTATIVA_CONTATO_DOADOR.PECO_ID IS 'Identificador do pedido de contato.';

CREATE SEQUENCE MODRED.SQ_TECD_ID INCREMENT BY 1 MAXVALUE 9999999999999999999999999999 MINVALUE 1 CACHE 20;
CREATE INDEX IN_FK_TECD_RETC ON MODRED.TENTATIVA_CONTATO_DOADOR (RETC_ID ASC);
CREATE INDEX IN_FK_TECD_PECO ON MODRED.TENTATIVA_CONTATO_DOADOR (PECO_ID ASC);

ALTER TABLE MODRED.TENTATIVA_CONTATO_DOADOR
ADD CONSTRAINT FK_TECD_USUA FOREIGN KEY (USUA_ID) REFERENCES MODRED.USUARIO(USUA_ID)
ENABLE;

COMMIT;

CREATE INDEX IN_FK_TECD_USUA ON MODRED.TENTATIVA_CONTATO_DOADOR (USUA_ID ASC);
COMMIT;

INSERT INTO MODRED.USUARIO (USUA_ID, USUA_TX_NOME, USUA_TX_USERNAME, USUA_TX_PASSWORD, USUA_IN_ATIVO, USUA_NR_ENTITY_STATUS) VALUES ('7', 'ANALISTA_CONTATO_FASE2', 'CONTATO_FASE2', '$2a$11$KMAfznbkZhx9mvESGy3.GewPWbSGQLVTnj5O0m7cIo5NrucDtcXT.', '1', '1');
INSERT INTO MODRED.USUARIO_PERFIL (USUA_ID, PERF_ID) VALUES ('7', '7');

ALTER TABLE MODRED.PEDIDO_ENRIQUECIMENTO 
ADD (PEEN_IN_TIPO_ENRIQUECIMENTO NUMBER NOT NULL);

COMMENT ON COLUMN MODRED.PEDIDO_ENRIQUECIMENTO.PEEN_IN_TIPO_ENRIQUECIMENTO IS 'Tipo de Enriquecimento';

-- INSERT DOS RESPOSTAS POSSÍVEIS AS TENTATIVAS DE CONTATO
INSERT INTO "MODRED"."RESPOSTA_TENTATIVA_CONTATO" (RETC_ID, RETC_TX_DESCRICAO) VALUES ('1', 'Contato Realizado (Diretamente com doador)');
INSERT INTO "MODRED"."RESPOSTA_TENTATIVA_CONTATO" (RETC_ID, RETC_TX_DESCRICAO) VALUES ('2', 'Contato Realizado (Doador ausente)');
INSERT INTO "MODRED"."RESPOSTA_TENTATIVA_CONTATO" (RETC_ID, RETC_TX_DESCRICAO) VALUES ('3', 'Não Houve Contato');
INSERT INTO "MODRED"."RESPOSTA_TENTATIVA_CONTATO" (RETC_ID, RETC_TX_DESCRICAO) VALUES ('4', 'Telefones Descartados');
INSERT INTO "MODRED"."RESPOSTA_TENTATIVA_CONTATO" (RETC_ID, RETC_TX_DESCRICAO) VALUES ('5', 'Inativado');

ALTER TABLE MODRED.DOADOR 
ADD (DOAD_TX_RG VARCHAR2(9),
     DOAD_TX_ORGAO_EXPEDIDOR VARCHAR2(255) );

COMMENT ON COLUMN MODRED.DOADOR.DOAD_TX_RG IS 'Número do documento de identidade (RG)';
COMMENT ON COLUMN MODRED.DOADOR.DOAD_TX_ORGAO_EXPEDIDOR IS 'Orgão Expedidor do RG';

ALTER TABLE MODRED.DOADOR_AUD 
ADD (DOAD_TX_RG VARCHAR2(9),
     DOAD_TX_ORGAO_EXPEDIDOR VARCHAR2(255) );