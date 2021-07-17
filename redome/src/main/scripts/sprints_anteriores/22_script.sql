CREATE TABLE MODRED.MATCH 
(
  MATC_ID NUMBER NOT NULL 
, MATC_STATUS NUMBER DEFAULT 0 NOT NULL 
, BUSC_ID NUMBER NOT NULL 
, CONSTRAINT PK_MATCH PRIMARY KEY 
  (
    MATC_ID 
  )
  ENABLE 
);

CREATE INDEX MODRED.FK_MATC_BUSC ON MATCH (BUSC_ID);

ALTER TABLE MODRED.MATCH
ADD CONSTRAINT FK_MATC_BUSC FOREIGN KEY
(
  BUSC_ID 
)
REFERENCES MODRED.BUSCA
(
  BUSC_ID 
)
ENABLE;

COMMENT ON TABLE MODRED.MATCH IS 'Tabela de matchs de um paciente';

COMMENT ON COLUMN MODRED.MATCH.MATC_ID IS 'Chave primária da tabela de match';

COMMENT ON COLUMN MODRED.MATCH.MATC_STATUS IS '0- ABERTO 
1 - FECHADO';

COMMENT ON COLUMN MODRED.MATCH.BUSC_ID IS 'Chave estrangeira  da tabela de busca';




ALTER TABLE MODRED.MATCH 
ADD (DOAD_NR_DMR NUMBER NOT NULL);

CREATE INDEX FK_MATC_DOAD ON MATCH (DOAD_NR_DMR);

ALTER TABLE MATCH
ADD CONSTRAINT FK_MATC_DOAD FOREIGN KEY
(
  DOAD_NR_DMR 
)
REFERENCES MODRED.DOADOR
(
  DOAD_NR_DMR 
)
ENABLE;

COMMENT ON COLUMN MATCH.DOAD_NR_DMR IS 'Chave estrangeira do doador';








CREATE TABLE MODRED.EVOLUCAO_BUSCA 
(
  EVBU_ID NUMBER NOT NULL 
, USUA_ID NUMBER NOT NULL 
, EVBU_EVENTO VARCHAR2(255) NOT NULL 
, MATC_ID NUMBER NOT NULL 
, BUSC_ID NUMBER NOT NULL 
, EVBU_DATA TIMESTAMP NOT NULL 
, CONSTRAINT PK_EVBU PRIMARY KEY 
  (
    EVBU_ID 
  )
  USING INDEX 
  (
      CREATE UNIQUE INDEX IN_PK_EVBU ON MODRED.EVOLUCAO_BUSCA (EVBU_ID ASC) 
  )
  ENABLE 
);

CREATE INDEX IN_FK_EVBU_BUSC ON MODRED.EVOLUCAO_BUSCA (BUSC_ID);

CREATE INDEX IN_FK_EVBU_MATC ON MODRED.EVOLUCAO_BUSCA (MATC_ID);

CREATE INDEX IN_FK_EVBU_USUA ON MODRED.EVOLUCAO_BUSCA (USUA_ID);

ALTER TABLE MODRED.EVOLUCAO_BUSCA
ADD CONSTRAINT FK_EVBU_BUSC FOREIGN KEY
(
  BUSC_ID 
)
REFERENCES MODRED.BUSCA
(
  BUSC_ID 
)
ENABLE;

ALTER TABLE MODRED.EVOLUCAO_BUSCA
ADD CONSTRAINT FK_EVBU_MATC FOREIGN KEY
(
  MATC_ID 
)
REFERENCES MATCH
(
  MATC_ID 
)
ENABLE;

ALTER TABLE MODRED.EVOLUCAO_BUSCA
ADD CONSTRAINT FK_EVBU_USUA FOREIGN KEY
(
  USUA_ID 
)
REFERENCES MODRED.USUARIO
(
  USUA_ID 
)
ENABLE;

COMMENT ON TABLE MODRED.EVOLUCAO_BUSCA IS 'Tabela de histórico das evoluções de um match e busca';

COMMENT ON COLUMN MODRED.EVOLUCAO_BUSCA.EVBU_ID IS 'Chave primária da tabela de evolução da busca';

COMMENT ON COLUMN MODRED.EVOLUCAO_BUSCA.USUA_ID IS 'Chave estrangeira do usuário';

COMMENT ON COLUMN MODRED.EVOLUCAO_BUSCA.EVBU_EVENTO IS 'Evento da evolução';

COMMENT ON COLUMN MODRED.EVOLUCAO_BUSCA.MATC_ID IS 'chave estrangeira do match';

COMMENT ON COLUMN MODRED.EVOLUCAO_BUSCA.BUSC_ID IS 'Chave estrangeira da busca';

COMMENT ON COLUMN MODRED.EVOLUCAO_BUSCA.EVBU_DATA IS 'Data do evento';



ALTER INDEX FK_MATC_BUSC 
RENAME TO IN_FK_MATC_BUSC;

ALTER INDEX FK_MATC_DOAD 
RENAME TO IN_FK_MATC_DOAD;

ALTER INDEX PK_MATCH 
RENAME TO IN_PK_MATCH;


CREATE SEQUENCE MODRED.SQ_MATC_ID INCREMENT BY 1 MAXVALUE 9999999999999999999999999999 MINVALUE 1 CACHE 20;
CREATE SEQUENCE MODRED.SQ_EVBU_ID INCREMENT BY 1 MAXVALUE 9999999999999999999999999999 MINVALUE 1 CACHE 20;


CREATE TABLE MODRED.LABORATORIO 
(
  LABO_ID NUMBER NOT NULL 
, LABO_NOME VARCHAR2(50) NOT NULL 
, CONSTRAINT PK_LABO PRIMARY KEY 
  (
    LABO_ID 
  )
  USING INDEX 
  (
      CREATE UNIQUE INDEX IN_PK_LABO ON MODRED.LABORATORIO (LABO_ID ASC) 
  )
  ENABLE 
);

COMMENT ON TABLE MODRED.LABORATORIO IS 'Tabela de laboratórios cadastrados';

COMMENT ON COLUMN MODRED.LABORATORIO.LABO_ID IS 'Chave primária da tabela de laboratório';

COMMENT ON COLUMN MODRED.LABORATORIO.LABO_NOME IS 'Nome do laboratório';



CREATE TABLE MODRED.COMENTARIO_MATCH 
(
  COMA_ID NUMBER NOT NULL 
, COMA_COMENTARIO VARCHAR2(255) NOT NULL 
, CONSTRAINT PK_COMA PRIMARY KEY 
  (
    COMA_ID 
  )
  USING INDEX 
  (
      CREATE UNIQUE INDEX IN_PK_COMA ON MODRED.COMENTARIO_MATCH (COMA_ID ASC) 
  )
  ENABLE 
);

COMMENT ON TABLE MODRED.COMENTARIO_MATCH IS 'Tabela que armazina algum comentario daquele match';

COMMENT ON COLUMN MODRED.COMENTARIO_MATCH.COMA_ID IS 'Chave primária da tabela de comentario match';

COMMENT ON COLUMN MODRED.COMENTARIO_MATCH.COMA_COMENTARIO IS 'Comentário do match';




ALTER TABLE MODRED.COMENTARIO_MATCH 
ADD (MATC_ID NUMBER NOT NULL);

CREATE INDEX IN_FK_COMA_MATC ON MODRED.COMENTARIO_MATCH (MATC_ID);

ALTER TABLE MODRED.COMENTARIO_MATCH
ADD CONSTRAINT FK_COMA_MATC FOREIGN KEY
(
  MATC_ID 
)
REFERENCES MATCH
(
  MATC_ID 
)
ENABLE;

COMMENT ON COLUMN MODRED.COMENTARIO_MATCH.MATC_ID IS 'Chave estrangeira da tabela de match';






CREATE TABLE MODRED.QUALIFICACAO_MATCH 
(
  QUMA_ID NUMBER NOT NULL 
, LOCU_ID NUMBER NOT NULL 
, QUMA_QUALIFICACAO VARCHAR2(20) NOT NULL 
, CONSTRAINT PK_QUMA PRIMARY KEY 
  (
    QUMA_ID 
  )
  USING INDEX 
  (
      CREATE UNIQUE INDEX IN_PK_QUMA ON MODRED.QUALIFICACAO_MATCH (QUMA_ID ASC) 
  )
  ENABLE 
);

COMMENT ON COLUMN MODRED.QUALIFICACAO_MATCH.QUMA_ID IS 'Chave primária da tabela de qualificaçãod e match';

COMMENT ON COLUMN MODRED.QUALIFICACAO_MATCH.LOCU_ID IS 'Chave estrangeira para tabela de locus';

COMMENT ON COLUMN MODRED.QUALIFICACAO_MATCH.QUMA_QUALIFICACAO IS 'Qualificação dividida em:
M -> MISMATCH
L ->
P ->
A ->';


ALTER TABLE MODRED.QUALIFICACAO_MATCH  
MODIFY (LOCU_ID VARCHAR2(4) );

CREATE INDEX IN_FK_QUMA_LOCU ON MODRED.QUALIFICACAO_MATCH (LOCU_ID);

ALTER TABLE MODRED.QUALIFICACAO_MATCH
ADD CONSTRAINT FK_QUMA_LOCU FOREIGN KEY
(
  LOCU_ID 
)
REFERENCES MODRED.LOCUS
(
  LOCU_ID 
)
ENABLE;

COMMENT ON TABLE MODRED.QUALIFICACAO_MATCH IS 'Tabela de qualificação de um match';


CREATE SEQUENCE MODRED.SQ_QUMA_ID INCREMENT BY 1 MAXVALUE 9999999999999999999999999999 MINVALUE 1 CACHE 20;
CREATE SEQUENCE MODRED.SQ_COMA_ID INCREMENT BY 1 MAXVALUE 9999999999999999999999999999 MINVALUE 1 CACHE 20;


ALTER TABLE MODRED.SOLICITACAO 
ADD (MATC_ID NUMBER );

ALTER TABLE MODRED.SOLICITACAO
ADD CONSTRAINT FK_SOLI_MATC FOREIGN KEY
(
  MATC_ID 
)
REFERENCES MODRED.MATCH
(
  MATC_ID 
)
ENABLE;

COMMENT ON COLUMN MODRED.SOLICITACAO.MATC_ID IS 'Chave estrangeira para o match';


ALTER TABLE MODRED.QUALIFICACAO_MATCH 
ADD (QUMA_POSICAO NUMBER NOT NULL);

COMMENT ON COLUMN MODRED.QUALIFICACAO_MATCH.QUMA_POSICAO IS '1 - Alelo 1 
2 - Alelo 2';


ALTER TABLE MODRED.DOADOR 
ADD (LABO_ID NUMBER );

CREATE INDEX IN_FK_DOAD_LABO ON MODRED.DOADOR (LABO_ID);

ALTER TABLE MODRED.DOADOR
ADD CONSTRAINT FK_DOAD_LABO FOREIGN KEY
(
  LABO_ID 
)
REFERENCES MODRED.LABORATORIO
(
  LABO_ID 
)
ENABLE;

COMMENT ON COLUMN MODRED.DOADOR.LABO_ID IS 'Laboratorio do doador';




ALTER TABLE MODRED.EXAME 
ADD (EXAME_NR_CONFIRMATORIO NUMBER DEFAULT 0 NOT NULL);

COMMENT ON COLUMN MODRED.EXAME.EXAME_NR_CONFIRMATORIO IS '0 - tipo de exame nao confirmatorio

1- tipo de exame confirmatorio';


ALTER TABLE MODRED.EXAME RENAME COLUMN EXAME_NR_CONFIRMATORIO TO EXAME_IN_CONFIRMATORIO;



ALTER TABLE MODRED.BUSCA 
ADD (PACI_IN_ACEITA_MISMATCH NUMBER(1) );

COMMENT ON COLUMN MODRED.BUSCA.PACI_IN_ACEITA_MISMATCH IS 'Define se o médico aceita pelo menos 1 mismatch ex. 5x6';


COMMIT;


INSERT INTO MODRED.RECURSO (RECU_ID, RECU_TX_SIGLA, RECU_TX_DESCRICAO) VALUES (57, 'CANCELAR_AGENDAMENTO_PEDIDO_COLETA_CENTRO_COLETA', 'Permite ao analista de workup notificar o centro de coleta do cancelamento da coleta.');
INSERT INTO MODRED.PERMISSAO (RECU_ID, PERF_ID, PERM_IN_COM_RESTRICAO) VALUES (57, 9, 0);

COMMIT;



--alterações e criação de tabelas da parte de genótipo - Filipe Paes

ALTER TABLE MODRED.GENOTIPO 
RENAME TO VALOR_GENOTIPO;
--TODO - ALTERAR DEPOIS DO REFACTORY PARA NOT NULL
--ALTER TABLE MODRED.VALOR_GENOTIPO 
--ADD (VAGE_NR_TIPO NUMBER NOT NULL);
ALTER TABLE MODRED.VALOR_GENOTIPO 
ADD (VAGE_NR_TIPO NUMBER);

ALTER TABLE MODRED.VALOR_GENOTIPO RENAME COLUMN GENO_TX_ALELO TO VAGE_TX_ALELO;

ALTER TABLE MODRED.VALOR_GENOTIPO RENAME COLUMN GENO_NR_POSICAO_ALELO TO VAGE_NR_POSICAO_ALELO;

ALTER TABLE MODRED.VALOR_GENOTIPO 
RENAME CONSTRAINT PK_GENO TO PK_VAGE;

ALTER TABLE MODRED.VALOR_GENOTIPO 
RENAME CONSTRAINT FK_GENO_LOEX TO FK_VAGE_LOEX;

ALTER INDEX PK_GENO 
RENAME TO IN_PK_VAGE;

COMMENT ON COLUMN MODRED.VALOR_GENOTIPO.VAGE_NR_TIPO IS '0- para sorologia, 1- para antígeno, 2- NMDP, 3- Grupo G, 4-  Grupo P e 5 Alelo';


-- SEQUENCIA PARA VALOR GENOTIPO
CREATE SEQUENCE  "MODRED"."SQ_VAGE_ID"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;





CREATE TABLE MODRED.GENOTIPO 
(
  GENO_ID NUMBER NOT NULL 
, GENO_DT_ALTERACAO TIMESTAMP(6) 
, CONSTRAINT PK_GENO PRIMARY KEY 
  (
    GENO_ID 
  )
  USING INDEX 
  (
      CREATE UNIQUE INDEX IN_PK_GENO ON GENOTIPO (GENO_ID ASC) 
  )
  ENABLE 
);

COMMENT ON TABLE GENOTIPO IS 'Tabela de pai de genótpo ';

COMMENT ON COLUMN GENOTIPO.GENO_ID IS 'Chave primária da tabela de genótipo';

COMMENT ON COLUMN GENOTIPO.GENO_DT_ALTERACAO IS 'Data de geração do genótipo';

--SEQUENCIA DE GENOTIPO
CREATE SEQUENCE  "MODRED"."SQ_GENO_ID"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;



--TODO - ALTERAR DEPOIS DO REFACTORY PARA NOT NULL
--ALTER TABLE MODRED.VALOR_GENOTIPO 
--ADD (GENO_ID NUMBER NOT NULL);
ALTER TABLE MODRED.VALOR_GENOTIPO 
ADD (GENO_ID NUMBER);

CREATE INDEX MODRED.IN_FK_VAGE_GENO ON VALOR_GENOTIPO (GENO_ID ASC);

ALTER TABLE MODRED.VALOR_GENOTIPO
ADD CONSTRAINT FK_VAGE_GENO FOREIGN KEY
(
  GENO_ID 
)
REFERENCES GENOTIPO
(
  GENO_ID 
)
ENABLE;

COMMENT ON COLUMN MODRED.VALOR_GENOTIPO.GENO_ID IS 'Referência para tabela pai de genótipo';



CREATE TABLE MODRED.VALOR_GENOTIPO_BUSCA 
(
  VAGB_ID NUMBER NOT NULL 
, LOCU_ID VARCHAR2(10 BYTE) NOT NULL 
, VAGB_NR_POSICAO NUMBER NOT NULL 
, VAGB_NR_TIPO NUMBER NOT NULL 
, GENO_ID NUMBER NOT NULL 
, VAGB_TX_VALOR VARCHAR2(5) NOT NULL 
, CONSTRAINT PK_VAGB PRIMARY KEY 
  (
    VAGB_ID 
  )
  USING INDEX 
  (
      CREATE UNIQUE INDEX VALOR_GENOTIPO_BUSCA_PK ON VALOR_GENOTIPO_BUSCA (VAGB_ID ASC) 
  )
  ENABLE 
);

CREATE INDEX MODRED.IN_FK_VAGB_GENO ON VALOR_GENOTIPO_BUSCA (GENO_ID ASC);

CREATE INDEX MODRED.IN_FK_VAGB_LOCU ON VALOR_GENOTIPO_BUSCA (LOCU_ID ASC);

ALTER TABLE MODRED.VALOR_GENOTIPO_BUSCA
ADD CONSTRAINT FK_VAGB_GENO FOREIGN KEY
(
  GENO_ID 
)
REFERENCES GENOTIPO
(
  GENO_ID 
)
ENABLE;

ALTER TABLE MODRED.VALOR_GENOTIPO_BUSCA
ADD CONSTRAINT FK_VAGB_LOCU FOREIGN KEY
(
  LOCU_ID 
)
REFERENCES LOCUS
(
  LOCU_ID 
)
ENABLE;

COMMENT ON TABLE MODRED.VALOR_GENOTIPO_BUSCA IS 'Tabela de valores de genótipo com a coluna de valores reduzida ';

COMMENT ON COLUMN MODRED.VALOR_GENOTIPO_BUSCA.VAGB_ID IS 'Chave primária de valor genótipo busca';

COMMENT ON COLUMN MODRED.VALOR_GENOTIPO_BUSCA.LOCU_ID IS 'Chave para tabela de lous ';

COMMENT ON COLUMN MODRED.VALOR_GENOTIPO_BUSCA.VAGB_NR_POSICAO IS 'Numero da posição do locus ';

COMMENT ON COLUMN MODRED.VALOR_GENOTIPO_BUSCA.VAGB_NR_TIPO IS '0- para sorologia, 1- para antígeno, 2- NMDP, 3- Grupo G, 4-  Grupo P e 5 Alelo';

COMMENT ON COLUMN MODRED.VALOR_GENOTIPO_BUSCA.GENO_ID IS 'Referência de genótipo ';

COMMENT ON COLUMN MODRED.VALOR_GENOTIPO_BUSCA.VAGB_TX_VALOR IS 'Valor de resultado de HLA';


--SEQUENCE DE VALOR GENOTIPO DE BUSCA 
CREATE SEQUENCE  "MODRED"."SQ_VAGB_ID"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 61 CACHE 20 NOORDER  NOCYCLE ;



CREATE TABLE MODRED.VALOR_GENOTIPO_EXPANDIDO 
(
  VGEE_ID NUMBER NOT NULL 
, VGEE_NR_POSICAO NUMBER NOT NULL 
, VGEE_TX_VALOR NUMBER NOT NULL 
, LOCU_ID VARCHAR2(10 BYTE) NOT NULL 
, GENO_ID NUMBER NOT NULL 
, CONSTRAINT PK_VGEE PRIMARY KEY 
  (
    VGEE_ID 
  )
  USING INDEX 
  (
      CREATE UNIQUE INDEX MODRED.IN_PK_VGEE ON VALOR_GENOTIPO_EXPANDIDO (VGEE_ID ASC)
  )
  ENABLE 
) ;

CREATE INDEX MODRED.IN_FK_GENO_ID ON VALOR_GENOTIPO_EXPANDIDO (GENO_ID ASC);

CREATE INDEX MODRED.IN_FK_LOCU_ID ON VALOR_GENOTIPO_EXPANDIDO (LOCU_ID ASC);

ALTER TABLE MODRED.VALOR_GENOTIPO_EXPANDIDO
ADD CONSTRAINT FK_VGEE_GENO FOREIGN KEY
(
  GENO_ID 
)
REFERENCES GENOTIPO
(
  GENO_ID 
)
ENABLE;


ALTER TABLE MODRED.VALOR_GENOTIPO_EXPANDIDO
ADD CONSTRAINT FK_VGEE_LOCU FOREIGN KEY
(
  LOCU_ID 
)
REFERENCES LOCUS
(
  LOCU_ID 
)
ENABLE;

COMMENT ON TABLE MODRED.VALOR_GENOTIPO_EXPANDIDO IS 'Tabela de valores exapandidos de genetótipos ';

COMMENT ON COLUMN MODRED.VALOR_GENOTIPO_EXPANDIDO.VGEE_ID IS 'Chave primária de valor de genótipo expandido';

COMMENT ON COLUMN MODRED.VALOR_GENOTIPO_EXPANDIDO.VGEE_NR_POSICAO IS 'Posicao do valor do genótipo que pode ser 1 ou 2';

COMMENT ON COLUMN MODRED.VALOR_GENOTIPO_EXPANDIDO.VGEE_TX_VALOR IS 'Valor de genótipo expandido';

COMMENT ON COLUMN MODRED.VALOR_GENOTIPO_EXPANDIDO.LOCU_ID IS 'Referência de locus ';


-- SEQUENCIA DE GENOTIPO EXPANDIDO 
CREATE SEQUENCE  "MODRED"."SQ_VGEE_ID"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;




ALTER TABLE MODRED.DOADOR 
ADD (GENO_ID NUMBER );
CREATE INDEX IN_FK_DOAD_GENO ON DOADOR (GENO_ID ASC);
ALTER TABLE DOADOR
ADD CONSTRAINT FK_DOAD_GENO FOREIGN KEY
(
  GENO_ID 
)
REFERENCES GENOTIPO
(
  GENO_ID 
)
ENABLE;

COMMENT ON COLUMN MODRED.DOADOR.GENO_ID IS 'Referência de genótipo ';

ALTER TABLE PACIENTE 
ADD (GENO_ID NUMBER );

CREATE INDEX IN_FK_PACI_GENO ON PACIENTE (GENO_ID ASC);

ALTER TABLE PACIENTE
ADD CONSTRAINT FK_PACI_GENO FOREIGN KEY
(
  GENO_ID 
)
REFERENCES GENOTIPO
(
  GENO_ID 
)
ENABLE;

COMMENT ON COLUMN PACIENTE.GENO_ID IS 'Referência para a tabela de genótipo';



INSERT INTO "MODRED"."RECURSO" (RECU_ID, RECU_TX_SIGLA, RECU_TX_DESCRICAO) VALUES ('58', 'AVALIAR_DOADOR_MATCH ', 'Permite o analista de busca analizar o Match entre doador e paciente');
INSERT INTO "MODRED"."PERMISSAO" (RECU_ID, PERF_ID) VALUES ('58', '5');

commit;



INSERT INTO "MODRED"."TIPO_TAREFA" (TITA_ID, TITA_TX_DESCRICAO, TITA_IN_AUTOMATICA) VALUES ('48', 'DISPONIBILIZAR_DOADOR', '0');
INSERT INTO "MODRED"."TIPO_SOLICITACAO" (TISO_ID, TISO_TX_DESCRICAO) VALUES ('4', 'DISPONIBILIZACAO');
COMMIT;



UPDATE "MODRED"."TIPO_TAREFA" SET TITA_IN_AUTOMATICA = '1', TITA_NR_TEMPO_EXECUCAO = '432000', TITA_TX_DESCRICAO='NOTIFICACAO_DISPONIBILIZAR_DOADOR' WHERE TITA_ID = 48;
COMMIT;

INSERT INTO "MODRED"."LABORATORIO" (LABO_ID, LABO_NOME) VALUES ('1', 'INCA');
UPDATE MODRED.DOADOR SET LABO_ID = 1;
COMMIT;



ALTER TABLE MODRED.SOLICITACAO  
MODIFY (DOAD_NR_DMR NULL);

ALTER TABLE MODRED.SOLICITACAO  
MODIFY (PACI_NR_RMR NULL);

insert into MODRED.tipo_tarefa (tita_id, tita_tx_descricao, tita_in_automatica, tita_nr_tempo_execucao)
values (46, 'CANCELAR_AGENDAMENTO_PEDIDO_COLETA_DOADOR', 0, null);

insert into MODRED.tipo_tarefa (tita_id, tita_tx_descricao, tita_in_automatica, tita_nr_tempo_execucao)
values (47, 'CANCELAR_AGENDAMENTO_PEDIDO_COLETA_CENTRO_COLETA', 0, null);
COMMIT;


INSERT INTO MODRED.RECURSO (RECU_ID, RECU_TX_SIGLA, RECU_TX_DESCRICAO) VALUES (59, 'CANCELAR_AGENDAMENTO_PEDIDO_COLETA_DOADOR', 'Permite ao analista de workup notificar o doador do cancelamento da coleta.');
INSERT INTO MODRED.PERMISSAO (RECU_ID, PERF_ID, PERM_IN_COM_RESTRICAO) VALUES (59, 9, 0);
COMMIT;


insert into modred.match (MATC_ID, BUSC_ID, DOAD_NR_DMR,  MATC_STATUS) 
select modred.SQ_MATC_ID.NEXTVAL,  bu.busc_id, soli.doad_nr_dmr,  soli.SOLI_NR_STATUS 
from modred.busca bu inner join modred.solicitacao soli on bu.paci_nr_rmr = soli.paci_nr_rmr where soli.matc_id is null;

insert into modred.laboratorio (labo_id, labo_nome) values (1,'Laboratório Biomedico');
update modred.doador set labo_id = 1;
commit;

INSERT INTO MODRED.RECURSO (RECU_ID, RECU_TX_SIGLA, RECU_TX_DESCRICAO) VALUES (60, 'ANALISE_MATCH', 'Permite ao analista de busca analisar os matchs de um paciente.');
INSERT INTO MODRED.PERMISSAO (RECU_ID, PERF_ID, PERM_IN_COM_RESTRICAO) VALUES (60, 5, 0);
COMMIT;


-- RENOMEAR COLUNAS INCORRETAS (QUALIFICACAO_MATCH)
ALTER TABLE MODRED.QUALIFICACAO_MATCH RENAME COLUMN QUMA_POSICAO TO QUMA_NR_POSICAO;
ALTER TABLE MODRED.QUALIFICACAO_MATCH RENAME COLUMN QUMA_QUALIFICACAO TO QUMA_TX_QUALIFICACAO;
commit;


-- INCLUSAO DE PACIENTE NO VALOR_GENOTIPO_EXPANDIDO
ALTER TABLE MODRED.VALOR_GENOTIPO_EXPANDIDO 
ADD (PACI_NR_RMR NUMBER);

ALTER TABLE MODRED.VALOR_GENOTIPO_EXPANDIDO
ADD CONSTRAINT FK_VGEE_PACI FOREIGN KEY
(
  PACI_NR_RMR 
)
REFERENCES MODRED.PACIENTE
(
  PACI_NR_RMR 
)
ENABLE;

CREATE INDEX MODRED.IN_FK_VGEE_PACI ON MODRED.VALOR_GENOTIPO_EXPANDIDO (PACI_NR_RMR);


-- INCLUSAO DE DOADOR NO VALOR_GENOTIPO_EXPANDIDO
ALTER TABLE MODRED.VALOR_GENOTIPO_EXPANDIDO 
ADD (DOAD_NR_DMR NUMBER);

ALTER TABLE MODRED.VALOR_GENOTIPO_EXPANDIDO
ADD CONSTRAINT FK_VGEE_DOAD FOREIGN KEY
(
  DOAD_NR_DMR 
)
REFERENCES MODRED.DOADOR
(
  DOAD_NR_DMR 
)
ENABLE;

CREATE INDEX MODRED.IN_FK_VGEE_DOAD ON MODRED.VALOR_GENOTIPO_EXPANDIDO (DOAD_NR_DMR);

COMMIT;

-- PREENCHENDO A COLUNA MATCH_ID DENTRO DE SOLICITAÇÃO.
UPDATE MODRED.SOLICITACAO S SET S.MATC_ID = ( 
    SELECT DISTINCT M.MATC_ID
    FROM MODRED.SOLICITACAO S_2 JOIN MODRED.MATCH M  ON (S_2.DOAD_NR_DMR = M.DOAD_NR_DMR)
    JOIN MODRED.BUSCA B ON (B.BUSC_ID = M.BUSC_ID AND B.PACI_NR_RMR = S_2.PACI_NR_RMR)
    WHERE   S_2.SOLI_ID = S.SOLI_ID)
WHERE S.DOAD_NR_DMR IS NOT NULL AND S.PACI_NR_RMR IS NOT NULL;
    




ALTER TABLE MODRED.DOADOR  
MODIFY (LABO_ID NOT NULL);

ALTER TABLE MODRED.DOADOR_AUD 
ADD (LABO_ID NUMBER );

ALTER TABLE MODRED.DOADOR_AUD 
ADD (GENO_ID NUMBER );


ALTER TABLE MODRED.PACIENTE_AUD 
ADD (GENO_ID NUMBER );

COMMIT;



ALTER TABLE MODRED.MATCH 
ADD (MATC_TX_SITUACAO VARCHAR2(2) );

COMMENT ON COLUMN MODRED.MATCH.MATC_TX_SITUACAO IS 'F1 - Fase 1 
	F2 - Fase 2 
	F3 - Fase 3 
	EX - Exame extendido 
	TC - Teste confirmatorio 
	D - Disponibilizado';
	
COMMIT;


-- ACERTOS NOS PADRÕES DOS NOMES DAS COLUNAS DE MATCH, COMENTARIO_MATCH E EVOLUCAO_BUSCA
ALTER TABLE MODRED.MATCH RENAME COLUMN MATC_STATUS TO MATC_IN_STATUS;
ALTER TABLE MODRED.COMENTARIO_MATCH RENAME COLUMN COMA_COMENTARIO TO COMA_TX_COMENTARIO;
ALTER TABLE MODRED.EVOLUCAO_BUSCA RENAME COLUMN EVBU_DATA TO EVBU_DT_DATA;
ALTER TABLE MODRED.EVOLUCAO_BUSCA RENAME COLUMN EVBU_EVENTO TO EVBU_TX_EVENTO;


INSERT INTO MODRED.RECURSO (RECU_ID, RECU_TX_SIGLA, RECU_TX_DESCRICAO) VALUES (61, 'ADICIONAR_COMENTARIO_MATCH', 'Permite ao usuário incluir um novo comentário para um match.');
INSERT INTO MODRED.PERMISSAO (RECU_ID, PERF_ID, PERM_IN_COM_RESTRICAO) VALUES (61, 5, 0);
COMMIT;
ALTER TABLE MODRED.EVOLUCAO_BUSCA RENAME COLUMN EVBU_EVENTO TO EVBU_TX_EVENTO;



ALTER TABLE MODRED.QUALIFICACAO_MATCH 
ADD (MATC_ID NUMBER );

CREATE INDEX IN_FK_QUMA_MATC ON MODRED.QUALIFICACAO_MATCH (MATC_ID);

ALTER TABLE MODRED.QUALIFICACAO_MATCH
ADD CONSTRAINT FL_QUMA_MATC FOREIGN KEY
(
  MATC_ID 
)
REFERENCES MATCH
(
  MATC_ID 
)
ENABLE;

COMMENT ON COLUMN MODRED.QUALIFICACAO_MATCH.MATC_ID IS 'Chave estrangeira para tabela de match';
ALTER TABLE MODRED.QUALIFICACAO_MATCH RENAME COLUMN QUMA_PK TO QUMA_ID;
commit;






create or replace procedure proc_criar_qualificacoes_match (rmr in NUMBER, dmr in NUMBER, fase in VARCHAR2, com_mismatch in BOOLEAN)


IS
matchId NUMBER;

begin
    select m.matc_id into matchId from MODRED.paciente paci inner join MODRED.busca b on paci.paci_nr_rmr = b.paci_nr_rmr inner join match m on m.busc_id = b.busc_id 
    where paci.PACI_NR_RMR = rmr and m.doad_nr_dmr = dmr;


    if (fase = '1') then
        INSERT INTO MODRED.QUALIFICACAO_MATCH (QUMA_ID, MATC_ID, LOCU_ID, QUMA_TX_QUALIFICACAO, QUMA_NR_POSICAO) VALUES (modred.SQ_QUMA_ID.NEXTVAL, matchId, 'A', 'A', 1); 
        if(com_mismatch) then
            INSERT INTO MODRED.QUALIFICACAO_MATCH (QUMA_ID, MATC_ID, LOCU_ID, QUMA_TX_QUALIFICACAO, QUMA_NR_POSICAO) VALUES (modred.SQ_QUMA_ID.NEXTVAL, matchId, 'A', 'M', 2); 
        else
            INSERT INTO MODRED.QUALIFICACAO_MATCH (QUMA_ID, MATC_ID, LOCU_ID, QUMA_TX_QUALIFICACAO, QUMA_NR_POSICAO) VALUES (modred.SQ_QUMA_ID.NEXTVAL, matchId, 'A', 'P', 2); 
        end if;

        INSERT INTO MODRED.QUALIFICACAO_MATCH (QUMA_ID, MATC_ID, LOCU_ID, QUMA_TX_QUALIFICACAO, QUMA_NR_POSICAO) VALUES (modred.SQ_QUMA_ID.NEXTVAL, matchId, 'B', 'L', 1); 
        INSERT INTO MODRED.QUALIFICACAO_MATCH (QUMA_ID, MATC_ID, LOCU_ID, QUMA_TX_QUALIFICACAO, QUMA_NR_POSICAO) VALUES (modred.SQ_QUMA_ID.NEXTVAL, matchId, 'B', 'P', 2); 
        INSERT INTO MODRED.QUALIFICACAO_MATCH (QUMA_ID, MATC_ID, LOCU_ID, QUMA_TX_QUALIFICACAO, QUMA_NR_POSICAO) VALUES (modred.SQ_QUMA_ID.NEXTVAL, matchId, 'DRB1', 'A', 1); 
        INSERT INTO MODRED.QUALIFICACAO_MATCH (QUMA_ID, MATC_ID, LOCU_ID, QUMA_TX_QUALIFICACAO, QUMA_NR_POSICAO) VALUES (modred.SQ_QUMA_ID.NEXTVAL, matchId, 'DRB1', 'L', 2); 
        update match set MATC_TX_SITUACAO = 'F1' where matc_id = matchId;
    elsif (fase = '2' OR fase = '3') then
        INSERT INTO MODRED.QUALIFICACAO_MATCH (QUMA_ID, MATC_ID, LOCU_ID, QUMA_TX_QUALIFICACAO, QUMA_NR_POSICAO) VALUES (modred.SQ_QUMA_ID.NEXTVAL, matchId, 'A', 'A', 1); 
        if(com_mismatch) then
            INSERT INTO MODRED.QUALIFICACAO_MATCH (QUMA_ID, MATC_ID, LOCU_ID, QUMA_TX_QUALIFICACAO, QUMA_NR_POSICAO) VALUES (modred.SQ_QUMA_ID.NEXTVAL, matchId, 'A', 'M', 2); 
        else
            INSERT INTO MODRED.QUALIFICACAO_MATCH (QUMA_ID, MATC_ID, LOCU_ID, QUMA_TX_QUALIFICACAO, QUMA_NR_POSICAO) VALUES (modred.SQ_QUMA_ID.NEXTVAL, matchId, 'A', 'P', 2); 
        end if;

        INSERT INTO MODRED.QUALIFICACAO_MATCH (QUMA_ID, MATC_ID, LOCU_ID, QUMA_TX_QUALIFICACAO, QUMA_NR_POSICAO) VALUES (modred.SQ_QUMA_ID.NEXTVAL, matchId, 'B', 'L', 1); 
        INSERT INTO MODRED.QUALIFICACAO_MATCH (QUMA_ID, MATC_ID, LOCU_ID, QUMA_TX_QUALIFICACAO, QUMA_NR_POSICAO) VALUES (modred.SQ_QUMA_ID.NEXTVAL, matchId, 'B', 'P', 2); 
        INSERT INTO MODRED.QUALIFICACAO_MATCH (QUMA_ID, MATC_ID, LOCU_ID, QUMA_TX_QUALIFICACAO, QUMA_NR_POSICAO) VALUES (modred.SQ_QUMA_ID.NEXTVAL, matchId, 'DRB1', 'A', 1); 
        INSERT INTO MODRED.QUALIFICACAO_MATCH (QUMA_ID, MATC_ID, LOCU_ID, QUMA_TX_QUALIFICACAO, QUMA_NR_POSICAO) VALUES (modred.SQ_QUMA_ID.NEXTVAL, matchId, 'DRB1', 'L', 2); 
        INSERT INTO MODRED.QUALIFICACAO_MATCH (QUMA_ID, MATC_ID, LOCU_ID, QUMA_TX_QUALIFICACAO, QUMA_NR_POSICAO) VALUES (modred.SQ_QUMA_ID.NEXTVAL, matchId, 'C', 'A', 1); 
        INSERT INTO MODRED.QUALIFICACAO_MATCH (QUMA_ID, MATC_ID, LOCU_ID, QUMA_TX_QUALIFICACAO, QUMA_NR_POSICAO) VALUES (modred.SQ_QUMA_ID.NEXTVAL, matchId, 'C', 'L', 2); 
        INSERT INTO MODRED.QUALIFICACAO_MATCH (QUMA_ID, MATC_ID, LOCU_ID, QUMA_TX_QUALIFICACAO, QUMA_NR_POSICAO) VALUES (modred.SQ_QUMA_ID.NEXTVAL, matchId, 'DRB3', 'A', 1); 
        INSERT INTO MODRED.QUALIFICACAO_MATCH (QUMA_ID, MATC_ID, LOCU_ID, QUMA_TX_QUALIFICACAO, QUMA_NR_POSICAO) VALUES (modred.SQ_QUMA_ID.NEXTVAL, matchId, 'DRB3', 'L', 2); 
        if(fase = '2') then
            update match set MATC_TX_SITUACAO = 'F2' where matc_id = matchId;
        else
            update match set MATC_TX_SITUACAO = 'F3' where matc_id = matchId;
        end if;
    end if;


end;


INSERT INTO "MODRED"."TIPO_SOLICITACAO" (TISO_ID, TISO_TX_DESCRICAO) VALUES ('4', 'DISPONIBILIZAR');
COMMIT;