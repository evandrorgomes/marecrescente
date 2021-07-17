-- PROCESSO --
-- TABELA
CREATE TABLE "MODRED"."PROCESSO" 
(
	"PROC_ID"  NUMBER NOT NULL,
	"PROC_DT_CRIACAO" TIMESTAMP NOT NULL,
	"PROC_DT_ATUALIZACAO" TIMESTAMP NOT NULL,
	"PROC_NR_TIPO" NUMBER NOT NULL,
	"PACI_NR_RMR" NUMBER NULL,
	"PROC_NR_STATUS" NUMBER NOT NULL,
	CONSTRAINT "PK_PROC" PRIMARY KEY (PROC_ID) 
	USING INDEX 
	  (
	      CREATE UNIQUE INDEX PK_PROC ON MODRED.PROCESSO (PROC_ID ASC)
	  )ENABLE
);

ALTER TABLE "MODRED"."PROCESSO" ADD CONSTRAINT FK_PROC_PACI FOREIGN KEY (PACI_NR_RMR) REFERENCES "MODRED"."PACIENTE" (PACI_NR_RMR) ENABLE;
CREATE INDEX IN_FK_PROC_PACI ON MODRED.PROCESSO (PACI_NR_RMR ASC);

COMMENT ON TABLE "MODRED"."PROCESSO" IS 'Tabela de processos do redome';
COMMENT ON COLUMN "MODRED"."PROCESSO"."PROC_ID" IS 'Chave primária que identifica com exclusividade um processo';
COMMENT ON COLUMN "MODRED"."PROCESSO"."PROC_DT_CRIACAO" IS 'Data de criação do processo';
COMMENT ON COLUMN "MODRED"."PROCESSO"."PROC_DT_ATUALIZACAO" IS 'Data da última atualização deste processo.';
COMMENT ON COLUMN "MODRED"."PROCESSO"."PROC_NR_TIPO" IS 'Identificação do tipo de processo.';
COMMENT ON COLUMN "MODRED"."PROCESSO"."PACI_NR_RMR" IS 'Paciente para o qual este processo de busca está associado.';
COMMENT ON COLUMN "MODRED"."PROCESSO"."PROC_NR_STATUS" IS 'Estado atual do processo.';


-- SEQUENCE
CREATE SEQUENCE "MODRED"."SQ_PROC_ID" MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER NOCYCLE;


-- TAREFA --
-- TABELA
CREATE TABLE "MODRED"."TAREFA" 
(
	"TARE_ID"  NUMBER NOT NULL,
	"PROC_ID"  NUMBER NOT NULL,
	"TARE_DT_CRIACAO" TIMESTAMP NOT NULL,
	"TARE_DT_ATUALIZACAO" TIMESTAMP NOT NULL,
	"TARE_NR_TIPO" NUMBER NOT NULL,	
	"TARE_NR_STATUS" NUMBER NOT NULL,	
	"PERF_ID_RESPONSAVEL" NUMBER NOT NULL,	
	"MEDI_ID_RESPONSAVEL" NUMBER NULL,
	"CETR_ID_RESPONSAVEL" NUMBER NULL,		
	CONSTRAINT "PK_TARE" PRIMARY KEY (TARE_ID)
	USING INDEX 
	  (
	      CREATE UNIQUE INDEX PK_TARE ON MODRED.TAREFA (TARE_ID ASC)
	  )ENABLE
);

ALTER TABLE "MODRED"."TAREFA" ADD CONSTRAINT FK_TARE_PROC FOREIGN KEY (PROC_ID) REFERENCES "MODRED"."PROCESSO" (PROC_ID) ENABLE;
ALTER TABLE "MODRED"."TAREFA" ADD CONSTRAINT FK_TARE_MEDI_ID_RESPONSAVEL FOREIGN KEY (MEDI_ID_RESPONSAVEL) REFERENCES "MODRED"."MEDICO" (MEDI_ID) ENABLE;
ALTER TABLE "MODRED"."TAREFA" ADD CONSTRAINT FK_TARE_CETR_ID_RESPONSAVEL FOREIGN KEY (CETR_ID_RESPONSAVEL) REFERENCES "MODRED"."CENTRO_TRANSPLANTE" (CETR_ID) ENABLE;

CREATE INDEX IN_FK_TARE_PROC ON MODRED.TAREFA (PROC_ID ASC);
CREATE INDEX IN_FK_TARE_MEDI_ID_RESPONSAVEL ON MODRED.TAREFA (MEDI_ID_RESPONSAVEL ASC);
CREATE INDEX IN_FK_CETR_ID_RESPONSAVEL ON MODRED.TAREFA (CETR_ID_RESPONSAVEL ASC);

COMMENT ON TABLE "MODRED"."TAREFA" IS 'Tabela de tarefas do motor de processos do redome';
COMMENT ON COLUMN "MODRED"."TAREFA"."TARE_ID" IS 'Chave primária que identifica com exclusividade uma tarefa';
COMMENT ON COLUMN "MODRED"."TAREFA"."PROC_ID" IS 'Processo para o qual esta tarefa está associada.';
COMMENT ON COLUMN "MODRED"."TAREFA"."TARE_DT_CRIACAO" IS 'Data de criação da tarefa.';
COMMENT ON COLUMN "MODRED"."TAREFA"."TARE_DT_ATUALIZACAO" IS 'Data da última atualização desta tarefa.';
COMMENT ON COLUMN "MODRED"."TAREFA"."TARE_NR_TIPO" IS 'Identificação do tipo de tarefa a ser executada no processo de negócio.';
COMMENT ON COLUMN "MODRED"."TAREFA"."TARE_NR_STATUS" IS 'Estado da tarefa dentro do processo de negócio.';
COMMENT ON COLUMN "MODRED"."TAREFA"."PERF_ID_RESPONSAVEL" IS 'Perfil do responsável por executar esta tarefa.';
COMMENT ON COLUMN "MODRED"."TAREFA"."MEDI_ID_RESPONSAVEL" IS 'Médico para o qual foi conferida esta tarefa, ou seja, trata-se do indivíduo responsável por executar a tarefa.';
COMMENT ON COLUMN "MODRED"."TAREFA"."CETR_ID_RESPONSAVEL" IS 'Centro de transplante para o qual foi conferida esta tarefa, ou seja, trata-se da organização responsável por executar a tarefa.';

-- SEQUENCE
CREATE SEQUENCE "MODRED"."SQ_TARE_ID" MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER NOCYCLE;

-- Internacionalização da CID
CREATE TABLE MODRED.IDIOMA 
(
  IDIO_ID NUMBER(1) NOT NULL 
, IDIO_TX_SIGLA VARCHAR2(2) NOT NULL 
, CONSTRAINT PK_IDIO PRIMARY KEY 
  (
    IDIO_ID 
  )
  ENABLE 
);

COMMENT ON TABLE MODRED.IDIOMA IS 'Tabela com os idiomas suportados pelo sistema.';
COMMENT ON COLUMN MODRED.IDIOMA.IDIO_ID IS 'Identificador do idioma';
COMMENT ON COLUMN MODRED.IDIOMA.IDIO_TX_SIGLA IS 'Sigla do idioma';

INSERT INTO MODRED.IDIOMA VALUES (1, 'pt');
INSERT INTO MODRED.IDIOMA VALUES (2, 'en');
INSERT INTO MODRED.IDIOMA VALUES (3, 'es');

CREATE TABLE MODRED.CID_IDIOMA 
(
  CID_ID NUMBER NOT NULL 
, IDIO_ID NUMBER NOT NULL 
, CIID_TX_DESCRICAO VARCHAR2(300) NOT NULL
, CONSTRAINT PK_CIID PRIMARY KEY 
  (
    CID_ID 
  , IDIO_ID 
  )
  ENABLE 
);

CREATE INDEX IN_FK_CIID_CID ON MODRED.CID_IDIOMA (CID_ID ASC);

CREATE INDEX IN_FK_CIID_IDIO ON MODRED.CID_IDIOMA (IDIO_ID ASC);

ALTER TABLE MODRED.CID_IDIOMA
ADD CONSTRAINT FK_CIID_CID FOREIGN KEY
(
  CID_ID 
)
REFERENCES MODRED.CID
(
  CID_ID 
)
ENABLE;

ALTER TABLE MODRED.CID_IDIOMA
ADD CONSTRAINT FK_CIID_IDIO FOREIGN KEY
(
  IDIO_ID 
)
REFERENCES MODRED.IDIOMA
(
  IDIO_ID 
)
ENABLE;

COMMENT ON TABLE MODRED.CID_IDIOMA IS 'Tabela que armazena os valores internacionalizados para cada CID.';
COMMENT ON COLUMN MODRED.CID_IDIOMA.CID_ID IS 'Identificador da CID.';
COMMENT ON COLUMN MODRED.CID_IDIOMA.IDIO_ID IS 'Identificador do idioma.';
COMMENT ON COLUMN MODRED.CID_IDIOMA.CIID_TX_DESCRICAO IS 'Descrição da CID no idioma especificado.';

INSERT INTO MODRED.CID_IDIOMA SELECT CID_ID, 1, CID_TX_DESCRICAO FROM MODRED.CID;
COMMIT;

ALTER TABLE MODRED.CID 
DROP COLUMN CID_TX_DESCRICAO;


--Inclusão da coluna de descrição na TAREFA
ALTER TABLE MODRED.TAREFA 
ADD (TARE_TX_DESCRICAO VARCHAR2(255) );

COMMENT ON COLUMN MODRED.TAREFA.TARE_TX_DESCRICAO IS 'Descrição de uma tarefa';
COMMIT;

-- Inclusão de recurso para visualização de notificações (tarefas) no sistema.
INSERT INTO MODRED.RECURSO (RECU_ID, RECU_TX_SIGLA, RECU_TX_DESCRICAO) VALUES (13, 'VISUALIZAR_NOTIFICACOES', 'Permite visualizar as notificações (tarefas) atribuídas ao médico no sistema.');
INSERT INTO MODRED.PERMISSAO (RECU_ID, PERF_ID, PERM_IN_COM_RESTRICAO) VALUES (13, 1, 0);
COMMIT;

-- Inclusão de recurso para visualização de pendências (tarefas) dos pacientes pelo perfil MEDICO.
INSERT INTO MODRED.RECURSO (RECU_ID, RECU_TX_SIGLA, RECU_TX_DESCRICAO) VALUES (14, 'VISUALIZAR_PENDENCIAS_AVALIACAO', 'Permite visualizar as notificações (tarefas) relacionadas ao paciente.');
INSERT INTO MODRED.PERMISSAO (RECU_ID, PERF_ID, PERM_IN_COM_RESTRICAO) VALUES (14, 1, 0);
COMMIT;


--Inclusão da coluna de status da entidade na tabela PERFIL, necessário para deleção lógica.
ALTER TABLE MODRED.PERFIL ADD (PERF_NR_ENTITY_STATUS NUMBER default 1 NOT NULL );
COMMENT ON COLUMN MODRED.PERFIL.PERF_NR_ENTITY_STATUS IS 'Estado lógico da entidade do sitema';


--Inclusão da coluna de status da entidade na tabela USUARIO, necessário para deleção lógica.
ALTER TABLE MODRED.USUARIO ADD (USUA_NR_ENTITY_STATUS NUMBER default 1 NOT NULL );
COMMENT ON COLUMN MODRED.USUARIO.USUA_NR_ENTITY_STATUS IS 'Estado lógico da entidade do sitema';

--Inclusão da coluna de status da entidade na tabela CENTRO_TRANSPLANTE, necessário para deleção lógica.
ALTER TABLE MODRED.CENTRO_TRANSPLANTE ADD (CETR_NR_ENTITY_STATUS NUMBER default 1 NOT NULL );
COMMENT ON COLUMN MODRED.CENTRO_TRANSPLANTE.CETR_NR_ENTITY_STATUS IS 'Estado lógico da entidade do sitema';


ALTER SEQUENCE MODRED.SQ_PERF_ID INCREMENT BY 200;
SELECT MODRED.SQ_PERF_ID.NEXTVAL FROM dual;
ALTER SEQUENCE MODRED.SQ_PERF_ID INCREMENT BY 1;

ALTER SEQUENCE MODRED.SQ_USUA_ID INCREMENT BY 200;
SELECT MODRED.SQ_USUA_ID.NEXTVAL FROM dual;
ALTER SEQUENCE MODRED.SQ_USUA_ID INCREMENT BY 1;

ALTER SEQUENCE MODRED.SQ_USUA_ID INCREMENT BY 200;
SELECT MODRED.SQ_USUA_ID.NEXTVAL FROM dual;
ALTER SEQUENCE MODRED.SQ_USUA_ID INCREMENT BY 1;

-- nova permissão
INSERT INTO "MODRED"."PERMISSAO" (RECU_ID, PERF_ID, PERM_IN_COM_RESTRICAO) VALUES ('14', '2', '0');
COMMIT;

--Insere um processo do tipo 15 para todos os pacientes que não possuem
insert into MODRED.processo (proc_id, proc_dt_criacao, proc_dt_atualizacao, proc_nr_tipo, paci_nr_rmr, proc_nr_status)
select SQ_AVAL_ID.nextval,sysdate, sysdate,15, t1.*,1
from(
    select paci_nr_rmr from MODRED.paciente where paci_nr_rmr not in (select paci_nr_rmr from MODRED.processo where PROC_NR_TIPO = 15)
) t1;

--Insere um processo do tipo 10 para todos os pacientes que não possuem
insert into MODRED.processo (proc_id, proc_dt_criacao, proc_dt_atualizacao, proc_nr_tipo, paci_nr_rmr, proc_nr_status)
select SQ_AVAL_ID.nextval,sysdate, sysdate,10, t1.*,1
from(
    select paci_nr_rmr from MODRED.paciente where paci_nr_rmr not in (select paci_nr_rmr from MODRED.processo where PROC_NR_TIPO = 10)
) t1;


--Cria tarefas para o medico responsavel do paciente para cada pendência em ABERTA
--o tipo da tarefa eh 27 (RESPONDER PENDENCIA)
insert into MODRED.tarefa (tare_id,proc_id,tare_dt_criacao,tare_dt_atualizacao, tare_nr_tipo,tare_nr_status,perf_id_responsavel,medi_id_responsavel)
select SQ_TARE_ID.nextval,(select proc_id from MODRED.processo where paci_nr_rmr = t1.paci_nr_rmr and PROC_NR_TIPO = 10), 
t1.pend_dt_criacao,t1.pend_dt_criacao,27,1,(select PERF_ID from perfil where perf_tx_descricao = 'MÉDICO'),t1.medi_id_responsavel

from(
    select aval.paci_nr_rmr, pend.pend_dt_criacao,p.medi_id_responsavel from MODRED.avaliacao aval inner join MODRED.pendencia pend on aval.aval_id = pend.aval_id
    inner join MODRED.paciente p on p.paci_nr_rmr = aval.paci_nr_rmr
    where pend.stpe_id = 1 and pend.pend_dt_criacao not in ( select t.tare_dt_criacao from MODRED.tarefa t WHERE t.tare_nr_tipo = 27 and t.medi_id_responsavel = p.medi_id_responsavel)
) t1;


--Cria tarefas para o avaliador responsavel do paciente para cada pendência em status RESPONDIDA
--o tipo da tarefa eh 27 (RESPONDER PENDENCIA)
insert into MODRED.tarefa (tare_id,proc_id,tare_dt_criacao,tare_dt_atualizacao, tare_nr_tipo,tare_nr_status,perf_id_responsavel,medi_id_responsavel)
select SQ_TARE_ID.nextval,(select proc_id from MODRED.processo where paci_nr_rmr = t1.paci_nr_rmr and PROC_NR_TIPO = 10), 
t1.pend_dt_criacao,t1.pend_dt_criacao,27,1,(select PERF_ID from MODRED.perfil where perf_tx_descricao = 'MÉDICO_AVALIADOR'),t1.medi_id_avaliador

from(
    select aval.paci_nr_rmr, pend.pend_dt_criacao,p.medi_id_responsavel,aval.medi_id_avaliador from MODRED.avaliacao aval inner join MODRED.pendencia pend on aval.aval_id = pend.aval_id
    inner join MODRED.paciente p on p.paci_nr_rmr = aval.paci_nr_rmr
    where pend.stpe_id = 2 and pend.pend_dt_criacao not in ( select t.tare_dt_criacao from MODRED.tarefa t WHERE t.tare_nr_tipo = 27 and t.medi_id_responsavel = aval.medi_id_avaliador)
) t1;


--Remoção da tabela de NOTIFICACAO E CATEGORIA_NOTIFICACAO (Mudança para utilização de PROCESSO/TAREFA)
DROP TABLE MODRED.NOTIFICACAO CASCADE CONSTRAINTS;
DROP TABLE MODRED.CATEGORIA_NOTIFICACAO CASCADE CONSTRAINTS;
COMMIT;

update modred.tarefa set TARE_NR_STATUS = 2 where TARE_NR_STATUS = 1;
COMMIT;

---JOB---
DROP TABLE MODRED.VALOR_DNA;

ALTER TABLE MODRED.SPLIT_VALOR_DNA 
ADD (SPVD_IN_COMPARA_NMDP NUMBER DEFAULT 0 NOT NULL);

ALTER TABLE MODRED.SPLIT_VALOR_DNA 
ADD (SPVD_TX_VERSAO VARCHAR2(6) );

COMMENT ON COLUMN MODRED.SPLIT_VALOR_DNA.SPVD_IN_COMPARA_NMDP IS 'Flag que identifica se o valor será usuado para comparação com nmdp';
COMMENT ON COLUMN MODRED.SPLIT_VALOR_DNA.SPVD_TX_VERSAO IS 'Versão do arquivo em que foi gerado o split';

create global temporary table MODRED.TEMP_SPLIT_VALOR_DNA
(
  LOCU_ID VARCHAR2(4 BYTE) NOT NULL 
, TESD_TX_GRUPO_ALELICO VARCHAR2(3 BYTE) NOT NULL 
, TESD_TX_VALOR_ALELICO VARCHAR2(20 BYTE) NOT NULL 
, TESD_IN_COMPARA_NMDP NUMBER DEFAULT 0
, TESD_TX_VERSAO VARCHAR2(10 BYTE)
, CONSTRAINT PK_TESD PRIMARY KEY 
  (
    LOCU_ID,
    TESD_TX_GRUPO_ALELICO,
    TESD_TX_VALOR_ALELICO
  )
  ENABLE 
) on commit preserve rows;

COMMENT ON TABLE MODRED.TEMP_SPLIT_VALOR_DNA IS 'TABELA TEMPORÁRIA DE SPLIT DOS VALORES DNA';
COMMENT ON COLUMN MODRED.TEMP_SPLIT_VALOR_DNA.LOCU_ID IS 'Chave estrangeira para locus';
COMMENT ON COLUMN MODRED.TEMP_SPLIT_VALOR_DNA.TESD_TX_GRUPO_ALELICO IS 'Valor do grupo alelico';
COMMENT ON COLUMN MODRED.TEMP_SPLIT_VALOR_DNA.TESD_TX_VALOR_ALELICO IS 'Valor alelico';
COMMENT ON COLUMN MODRED.TEMP_SPLIT_VALOR_DNA.TESD_IN_COMPARA_NMDP IS 'Flag que identifica se o valor será usuado para comparação com nmdp';
COMMENT ON COLUMN MODRED.TEMP_SPLIT_VALOR_DNA.TESD_TX_VERSAO IS 'Versão do arquivo em que foi gerado o split';


  CREATE global temporary TABLE MODRED.TEMP_VALOR_DNA 
   (LOCU_ID VARCHAR2(4 BYTE), 
	TEVD_TX_VALOR VARCHAR2(20 BYTE),
    TEVD_TX_VERSAO VARCHAR2(10 BYTE),
   CONSTRAINT PK_TEVD PRIMARY KEY 
  (
    LOCU_ID,
    TEVD_TX_VALOR
  )
  ENABLE 
) on commit preserve rows;

   COMMENT ON COLUMN MODRED.TEMP_VALOR_DNA.LOCU_ID IS 'CHAVE ESTRANGEIRA RELACIONADA AO LOCUS';
   COMMENT ON COLUMN MODRED.TEMP_VALOR_DNA.TEVD_TX_VALOR IS 'VALOR DNA';
   COMMENT ON COLUMN MODRED.TEMP_VALOR_DNA.TEVD_TX_VERSAO is 'Versão do arquivo ';
   COMMENT ON TABLE MODRED.TEMP_VALOR_DNA  IS 'TABELA TEMPORÁRIA DE VALORES DNA';


  CREATE TABLE MODRED.TEMP_VALOR_DNA_NMDP
   (LOCU_ID VARCHAR2(4 BYTE) NOT NULL, 
	DNNM_TX_VALOR VARCHAR2(20 BYTE) NOT NULL
   );

 COMMENT ON COLUMN MODRED.VALOR_DNA_NMDP.LOCU_ID IS 'CHAVE ESTRANGEIRA RELACIONADA AO LOCUS';
 COMMENT ON COLUMN MODRED.VALOR_DNA_NMDP.DNNM_TX_VALOR IS 'VALORES VALIDOS SOROLOGIA E NMDP';
 COMMENT ON TABLE MODRED.VALOR_DNA_NMDP  IS 'TABELA DE RESULTADO DOS VALORES VALIDOS TEMPORÁRIOS DE DNA E NMDP';
 

alter table MODRED.VALOR_DNA_NMDP RENAME TO VALOR_DNA_NMDP_VALIDO;


CREATE VIEW MODRED.VALOR_DNA_NMDP
AS SELECT 
    LOCU_ID,
    DNNM_TX_VALOR
FROM 
    VALOR_DNA_NMDP_VALIDO
WITH READ ONLY;

COMMENT ON TABLE VALOR_DNA_NMDP IS 'VIEW DE RESULTADO DOS VALORES VALIDOS DE DNA E NMDP';