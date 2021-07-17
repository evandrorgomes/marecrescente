INSERT INTO MODRED.TIPO_TAREFA (TITA_ID, TITA_TX_DESCRICAO, TITA_IN_AUTOMATICA, TITA_NR_TEMPO_EXECUCAO, TARE_IN_SOMENTE_AGRUPADOR) VALUES (78, 'AUTORIZACAO_PACIENTE', 0, 3600, 0);
INSERT INTO MODRED.RECURSO (RECU_ID, RECU_TX_SIGLA, RECU_TX_DESCRICAO) VALUES (106, 'AUTORIZACAO_PACIENTE', 'Permite a um médico do centro de transplante fazer o upload da autorização do paciente.');
INSERT INTO MODRED.PERMISSAO (RECU_ID, PERF_ID, PERM_IN_COM_RESTRICAO) VALUES (106, 15, 0);
COMMIT;

ALTER TABLE MODRED.ARQUIVO_PRESCRICAO 
ADD (ARPR_IN_AUTORIZACAO_PACIENTE NUMBER(1) );

COMMENT ON COLUMN MODRED.ARQUIVO_PRESCRICAO.ARPR_IN_AUTORIZACAO_PACIENTE IS 'Informa se o arquivo é de autorização o paciente.';

UPDATE MODRED.ARQUIVO_PRESCRICAO SET ARPR_IN_AUTORIZACAO_PACIENTE = 0;
COMMIT;

ALTER TABLE MODRED.ARQUIVO_PRESCRICAO  
MODIFY (ARPR_IN_AUTORIZACAO_PACIENTE NOT NULL);

CREATE TABLE MODRED.TIPO_CHECKLIST 
(
  TIPC_ID NUMBER NOT NULL CREATE TABLE MODRED.CONSTANTE_RELATORIO 
(
  CORE_ID_CODIGO VARCHAR2(255) NOT NULL 
, CORE_TX_VALOR VARCHAR2(255) NOT NULL 
, CONSTRAINT PK_CORE PRIMARY KEY 
  (
    CORE_ID_CODIGO 
  )
  ENABLE 
);

COMMENT ON TABLE MODRED.CONSTANTE_RELATORIO IS 'Tabela que contém as constantes que serão utilizadas nos relatórios.';
COMMENT ON COLUMN MODRED.CONSTANTE_RELATORIO.CORE_ID_CODIGO IS 'Identificador único da tabela';
COMMENT ON COLUMN MODRED.CONSTANTE_RELATORIO.CORE_TX_VALOR IS 'Valor da constante.';

INSERT INTO MODRED.CONSTANTE_RELATORIO (CORE_ID_CODIGO, CORE_TX_VALOR) VALUES ('PRESIDENTE_ANVISA', 'Nome do presidente da ANVISA');
COMMIT;


CREATE TABLE MODRED.CONSTANTE_RELATORIO_AUD 
(
  AUDI_ID NUMBER NOT NULL
, CORE_ID_CODIGO VARCHAR2(255) NOT NULL 
, AUDI_TX_TIPO NUMBER NOT NULL
, CORE_TX_VALOR VARCHAR2(255) NOT NULL 
, CONSTRAINT PK_CORA PRIMARY KEY 
  (
    AUDI_ID,
    CORE_ID_CODIGO 
  )
  ENABLE 
);

GRANT SELECT ON "MODRED"."CONSTANTE_RELATORIO" TO RMODRED_READ;
GRANT UPDATE ON "MODRED"."CONSTANTE_RELATORIO" TO RMODRED_WRITE;

GRANT SELECT ON "MODRED"."CONSTANTE_RELATORIO_AUD" TO RMODRED_READ;
GRANT INSERT, UPDATE ON "MODRED"."CONSTANTE_RELATORIO_AUD" TO RMODRED_WRITE;

CREATE OR REPLACE SYNONYM "MODRED_APLICACAO"."CONSTANTE_RELATORIO" FOR "MODRED"."CONSTANTE_RELATORIO";
CREATE OR REPLACE SYNONYM "MODRED_APLICACAO"."CONSTANTE_RELATORIO_AUD" FOR "MODRED"."CONSTANTE_RELATORIO_AUD";


, TIPC_TX_NM_TIPO VARCHAR2(80 BYTE) 
, CONSTRAINT PK_TIPC_ID PRIMARY KEY 
  (
    TIPC_ID 
  )
  USING INDEX 
  (
      CREATE UNIQUE INDEX IN_PK_TIPC_ID ON TIPO_CHECKLIST (TIPC_ID ASC) 
  )
  ENABLE 
);

COMMENT ON COLUMN MODRED.TIPO_CHECKLIST.TIPC_ID IS 'Id sequencial do tipo de checklist ';

COMMENT ON COLUMN MODRED.TIPO_CHECKLIST.TIPC_TX_NM_TIPO IS 'Nome do tipo de checklist ';


CREATE TABLE MODRED.ITENS_CHECKLIST 
(
  ITEC_ID NUMBER NOT NULL 
, ITEC_TX_NM_ITEM VARCHAR2(60 BYTE) NOT NULL 
, TIPC_ID NUMBER NOT NULL 
, CONSTRAINT PK_ITEC_ID PRIMARY KEY 
  (
    ITEC_ID 
  )
  USING INDEX 
  (
      CREATE UNIQUE INDEX IN_PK_ITEC_ID ON ITENS_CHECKLIST (ITEC_ID ASC) 
  )
  ENABLE 
) 
;
CREATE INDEX MODRED.IN_FK_ITEC_TIPC ON ITENS_CHECKLIST (TIPC_ID ASC);

ALTER TABLE MODRED.ITENS_CHECKLIST
ADD CONSTRAINT FK_ITEC_TIPC FOREIGN KEY
(
  TIPC_ID 
)
REFERENCES MODRED.TIPO_CHECKLIST
(
  TIPC_ID 
)
ENABLE;

COMMENT ON COLUMN MODRED.ITENS_CHECKLIST.ITEC_ID IS 'Id sequencial do item de checklist';

COMMENT ON COLUMN MODRED.ITENS_CHECKLIST.ITEC_TX_NM_ITEM IS 'Texto do item de checklist';

COMMENT ON COLUMN MODRED.ITENS_CHECKLIST.TIPC_ID IS 'Id do tipo de checklist ';


CREATE SEQUENCE MODRED.SQ_ITEC_ID INCREMENT BY 1 MAXVALUE 9999999999999999999999999999 MINVALUE 1 CACHE 20;


CREATE TABLE MODRED.RESPOSTA_CHECKLIST 
(
  RESC_ID NUMBER NOT NULL 
, RESC_IN_RESPOSTA NUMBER NOT NULL 
, ITEC_ID NUMBER NOT NULL 
, PELO_ID NUMBER NOT NULL 
, CONSTRAINT PK_RESC_ID PRIMARY KEY 
  (
    RESC_ID 
  )
  USING INDEX 
  (
      CREATE UNIQUE INDEX IN_PK_RESC_ID ON RESPOSTA_CHECKLIST (RESC_ID ASC) 
  )
  ENABLE 
);

CREATE INDEX MODRED.IN_FK_RESC_PELO ON RESPOSTA_CHECKLIST (RESC_IN_RESPOSTA ASC);

ALTER TABLE MODRED.RESPOSTA_CHECKLIST
ADD CONSTRAINT FK_RESC_PELO FOREIGN KEY
(
  PELO_ID 
)
REFERENCES MODRED.PEDIDO_LOGISTICA
(
  PELO_ID 
)
ENABLE;

COMMENT ON COLUMN RESPOSTA_CHECKLIST.RESC_ID IS 'Id sequencial de resposta de checklist';

COMMENT ON COLUMN RESPOSTA_CHECKLIST.RESC_IN_RESPOSTA IS 'Resposta como true ou false ao item de checklist';

COMMENT ON COLUMN RESPOSTA_CHECKLIST.ITEC_ID IS 'Item de checklist ao qual está sendo respondido';

COMMENT ON COLUMN RESPOSTA_CHECKLIST.PELO_ID IS 'Referencia de logistica ';


ALTER TABLE MODRED.STATUS_PEDIDO_LOGISTICA  
MODIFY (STPL_TX_DESCRICAO VARCHAR2(60 BYTE) );


INSERT INTO "MODRED"."STATUS_PEDIDO_LOGISTICA" (STPL_ID, STPL_TX_DESCRICAO) VALUES ('3', 'AGUARDANDO AUTORIZAÇÃO PACIENTE');
INSERT INTO "MODRED"."TIPO_TAREFA" (TITA_ID, TITA_TX_DESCRICAO, TITA_IN_AUTOMATICA, TARE_IN_SOMENTE_AGRUPADOR) VALUES ('79', 'RETIRADA_MATERIAL_INTERNACIONAL', '0', '0');
INSERT INTO "MODRED"."TIPO_TAREFA" (TITA_ID, TITA_TX_DESCRICAO, TITA_IN_AUTOMATICA, TARE_IN_SOMENTE_AGRUPADOR) VALUES ('80', 'ENTREGA DE MATERIAL_INTERNACIONAL', '0', '0');
COMMIT;


INSERT INTO "MODRED"."TIPO_CHECKLIST" (TIPC_ID, TIPC_TX_NM_TIPO) VALUES ('1', 'LOGÍSTICA DE MEDULA INTERNACIONAL');
INSERT INTO "MODRED"."TIPO_CHECKLIST" (TIPC_ID, TIPC_TX_NM_TIPO) VALUES ('2', 'LOGÍSTICA DE CORDÃO INTERNACIONAL');
COMMIT;


-- BUSCA PRELIMINAR
CREATE TABLE MODRED.BUSCA_PRELIMINAR
(
    BUPR_ID NUMBER NOT NULL,
    BUPR_TX_NOME_PACIENTE VARCHAR2(255) NOT NULL,
    BUPR_DT_NASCIMENTO DATE NOT NULL,
    BUPR_TX_ABO VARCHAR2(3) NOT NULL,
    BUPR_VL_PESO NUMBER(4,1) NOT NULL,
    CONSTRAINT PK_GENO PRIMARY KEY (
        BUPR_ID
    ) ENABLE
);

COMMENT ON TABLE MODRED.BUSCA_PRELIMINAR IS 'Tabela de busca preliminar realizada quando um médico deseja fazer uma consulta prévia no Redome, antes do cadastro definitivo.';
COMMENT ON COLUMN MODRED.BUSCA_PRELIMINAR.BUPR_ID IS 'Sequencial identificador da tabela busca preliminar.';
COMMENT ON COLUMN MODRED.BUSCA_PRELIMINAR.BUPR_TX_NOME_PACIENTE IS 'Nome do paciente.';
COMMENT ON COLUMN MODRED.BUSCA_PRELIMINAR.BUPR_DT_NASCIMENTO IS 'Data de nascimento do paciente.';
COMMENT ON COLUMN MODRED.BUSCA_PRELIMINAR.BUPR_TX_ABO IS 'ABO do paciente.';
COMMENT ON COLUMN MODRED.BUSCA_PRELIMINAR.BUPR_VL_PESO IS 'Peso do paciente.';

-- SEQUENCE
CREATE SEQUENCE MODRED.SQ_BUPR_ID INCREMENT BY 1 MAXVALUE 9999999999999999999999999999 MINVALUE 1 CACHE 20;

COMMIT;


-- GENOTIPO_PRELIMINAR
CREATE TABLE MODRED.GENOTIPO_PRELIMINAR
(
    GEPR_ID NUMBER NOT NULL,
    GEPR_TX_CODIGO_LOCUS VARCHAR2(4 BYTE) NOT NULL,
    GEPR_TX_ALELO VARCHAR2(20 BYTE) NOT NULL,
    GEPR_NR_POSICAO_ALELO NUMBER(1) NOT NULL,
    BUPR_ID NUMBER NOT NULL,
    CONSTRAINT PK_GEPR PRIMARY KEY (
        GEPR_ID
    )
    ENABLE
);

ALTER TABLE MODRED.GENOTIPO_PRELIMINAR
ADD CONSTRAINT FK_GEPR_BUPR FOREIGN KEY (BUPR_ID)
REFERENCES MODRED.BUSCA_PRELIMINAR (BUPR_ID);

CREATE INDEX IN_FK_GEPR_BUPR ON MODRED.GENOTIPO_PRELIMINAR (BUPR_ID ASC);

COMMENT ON TABLE MODRED.GENOTIPO_PRELIMINAR IS 'Tabela que registra os valores do genótipo do paciente consultado.';
COMMENT ON COLUMN MODRED.GENOTIPO_PRELIMINAR.GEPR_ID IS 'Sequence identificador da tabela.';
COMMENT ON COLUMN MODRED.GENOTIPO_PRELIMINAR.GEPR_TX_CODIGO_LOCUS IS 'Código do locus.';
COMMENT ON COLUMN MODRED.GENOTIPO_PRELIMINAR.GEPR_TX_ALELO IS 'Valor do alélico.';
COMMENT ON COLUMN MODRED.GENOTIPO_PRELIMINAR.GEPR_NR_POSICAO_ALELO IS 'Posição do valor alélico indicado no momento do cadastro.';
COMMENT ON COLUMN MODRED.GENOTIPO_PRELIMINAR.BUPR_ID IS 'Identificador (FK) da tabela de busca preliminar. Indica quem é a quem está associado os valores inseridos.';

-- SEQUENCE
CREATE SEQUENCE MODRED.SQ_GEPR_ID INCREMENT BY 1 MAXVALUE 9999999999999999999999999999 MINVALUE 1 CACHE 20;

COMMIT;


-- GENOTIPO_BUSCA_PRELIMINAR
CREATE TABLE MODRED.GENOTIPO_BUSCA_PRELIMINAR
(
    GEBP_ID NUMBER NOT NULL,
    GEBP_TX_CODIGO_LOCUS VARCHAR2(4 BYTE) NOT NULL,
    GEBP_TX_ALELO VARCHAR2(20 BYTE) NOT NULL,
    GEBP_NR_POSICAO_ALELO NUMBER(1) NOT NULL,
    GEBP_IN_COMPOSICAO_VALOR NUMBER(1) NOT NULL,
    BUPR_ID NUMBER NOT NULL,
    CONSTRAINT PK_GEBP PRIMARY KEY (
        GEBP_ID
    )
    ENABLE
);

ALTER TABLE MODRED.GENOTIPO_BUSCA_PRELIMINAR
ADD CONSTRAINT FK_GEBP_BUPR FOREIGN KEY (BUPR_ID)
REFERENCES MODRED.BUSCA_PRELIMINAR (BUPR_ID);

CREATE INDEX IN_FK_GEBP_BUPR ON MODRED.GENOTIPO_BUSCA_PRELIMINAR (BUPR_ID ASC);

COMMENT ON TABLE MODRED.GENOTIPO_BUSCA_PRELIMINAR IS 'Tabela que registra os valores do genótipo reduzidos e expandidos para os valores válidos, em caso de agrupamentos como P, G e NNMDP.';
COMMENT ON COLUMN MODRED.GENOTIPO_BUSCA_PRELIMINAR.GEBP_ID IS 'Sequence identificador da tabela.';
COMMENT ON COLUMN MODRED.GENOTIPO_BUSCA_PRELIMINAR.GEBP_TX_CODIGO_LOCUS IS 'Código do locus.';
COMMENT ON COLUMN MODRED.GENOTIPO_BUSCA_PRELIMINAR.GEBP_TX_ALELO IS 'Valor do alelo correspondente.';
COMMENT ON COLUMN MODRED.GENOTIPO_BUSCA_PRELIMINAR.GEBP_NR_POSICAO_ALELO IS 'Posição do valor alélico indicado no momento do cadastro.';
COMMENT ON COLUMN MODRED.GENOTIPO_BUSCA_PRELIMINAR.GEBP_IN_COMPOSICAO_VALOR IS 'Composição do valor alélico. As possibilidades são: SOROLOGICO(0), ANTIGENO(1), NMDP(2), GRUPO_G(3), GRUPO_P(4), ALELO(5).';
COMMENT ON COLUMN MODRED.GENOTIPO_BUSCA_PRELIMINAR.BUPR_ID IS 'Identificador (FK) da tabela de busca preliminar. Indica qual o paciente está associado os valores inseridos.';

-- SEQUENCE
CREATE SEQUENCE MODRED.SQ_GEBP_ID INCREMENT BY 1 MAXVALUE 9999999999999999999999999999 MINVALUE 1 CACHE 20;

COMMIT;


-- GENOTIPO_EXPANDIDO_PRELIMINAR
CREATE TABLE MODRED.GENOTIPO_EXPANDIDO_PRELIMINAR
(
    GEEP_ID NUMBER NOT NULL,
    GEEP_TX_CODIGO_LOCUS VARCHAR2(4 BYTE) NOT NULL,
    GEEP_TX_ALELO VARCHAR2(20 BYTE) NOT NULL,
    GEEP_NR_POSICAO_ALELO NUMBER(1) NOT NULL,
    BUPR_ID NUMBER NOT NULL,
    CONSTRAINT PK_GEEP PRIMARY KEY (
        GEEP_ID
    )
    ENABLE
);


ALTER TABLE MODRED.GENOTIPO_EXPANDIDO_PRELIMINAR
ADD CONSTRAINT FK_GEEP_BUPR FOREIGN KEY (BUPR_ID)
REFERENCES MODRED.BUSCA_PRELIMINAR (BUPR_ID);

CREATE INDEX IN_FK_GEEP_BUPR ON MODRED.GENOTIPO_EXPANDIDO_PRELIMINAR (BUPR_ID ASC);

COMMENT ON TABLE MODRED.GENOTIPO_EXPANDIDO_PRELIMINAR IS 'Tabela que registra os valores do genótipo expandidos, ou seja, extraídos os valores de grupamento G, P, NMDP e separados em grupo alélico e valor alélico.';
COMMENT ON COLUMN MODRED.GENOTIPO_EXPANDIDO_PRELIMINAR.GEEP_ID IS 'Sequence identificador da tabela.';
COMMENT ON COLUMN MODRED.GENOTIPO_EXPANDIDO_PRELIMINAR.GEEP_TX_CODIGO_LOCUS IS 'Código do locus.';
COMMENT ON COLUMN MODRED.GENOTIPO_EXPANDIDO_PRELIMINAR.GEEP_TX_ALELO IS 'Valor do alelo correspondente.';
COMMENT ON COLUMN MODRED.GENOTIPO_EXPANDIDO_PRELIMINAR.GEEP_NR_POSICAO_ALELO IS 'Posição do valor alélico indicado no momento do cadastro.';
COMMENT ON COLUMN MODRED.GENOTIPO_EXPANDIDO_PRELIMINAR.BUPR_ID IS 'Identificador (FK) da tabela de busca preliminar. Indica qual o paciente está associado os valores inseridos.';

-- SEQUENCE
CREATE SEQUENCE MODRED.SQ_GEEP_ID INCREMENT BY 1 MAXVALUE 9999999999999999999999999999 MINVALUE 1 CACHE 20;

COMMIT;



-- MATCH_PRELIMINAR
CREATE TABLE MODRED.MATCH_PRELIMINAR
(
    MAPR_ID NUMBER NOT NULL,
    DOAD_ID NUMBER NOT NULL,
    BUPR_ID NUMBER NOT NULL,
    MAPR_IN_FASE VARCHAR2(2) NOT NULL,
    CONSTRAINT PK_MAPR PRIMARY KEY (
        MAPR_ID
    )
    ENABLE
);


ALTER TABLE MODRED.MATCH_PRELIMINAR
ADD CONSTRAINT FK_MAPR_DOAD FOREIGN KEY (DOAD_ID)
REFERENCES MODRED.DOADOR (DOAD_ID);

CREATE INDEX IN_FK_MAPR_DOAD ON MODRED.MATCH_PRELIMINAR (DOAD_ID ASC);

ALTER TABLE MODRED.MATCH_PRELIMINAR
ADD CONSTRAINT FK_MAPR_BUPR FOREIGN KEY (BUPR_ID)
REFERENCES MODRED.BUSCA_PRELIMINAR (BUPR_ID);

CREATE INDEX IN_FK_MAPR_BUPR ON MODRED.MATCH_PRELIMINAR (BUPR_ID ASC);

COMMENT ON TABLE MODRED.MATCH_PRELIMINAR IS 'Tabela que matchs ocorridos após a inclusão da busca preliminar.';
COMMENT ON COLUMN MODRED.MATCH_PRELIMINAR.MAPR_ID IS 'Sequence identificador da tabela.';
COMMENT ON COLUMN MODRED.MATCH_PRELIMINAR.DOAD_ID IS 'Doador (para o cadastro preliminar, somente os nacionais) que ocorreu match.';
COMMENT ON COLUMN MODRED.MATCH_PRELIMINAR.BUPR_ID IS 'Identificador (FK) da tabela de busca preliminar. Indica qual o paciente está associado os valores inseridos.';
COMMENT ON COLUMN MODRED.MATCH_PRELIMINAR.MAPR_IN_FASE IS 'Em que fase encontra-se o match.';

-- SEQUENCE
CREATE SEQUENCE MODRED.SQ_MAPR_ID INCREMENT BY 1 MAXVALUE 9999999999999999999999999999 MINVALUE 1 CACHE 20;

COMMIT;



-- QUALIFICACAO_MATCH_PRELIMINAR
CREATE TABLE MODRED.QUALIFICACAO_MATCH_PRELIMINAR
(
    QUMP_ID NUMBER NOT NULL,
    QUMP_TX_CODIGO_LOCUS VARCHAR2(4 BYTE) NOT NULL,
    QUMP_TX_GENOTIPO VARCHAR2(20) NOT NULL,
    QUMP_NR_POSICAO_ALELO NUMBER(1) NOT NULL,
    QUMP_IN_QUALIFICACAO VARCHAR2(1) NOT NULL,
    QUMP_IN_TIPO_ALELO NUMBER NOT NULL,
    MAPR_ID NUMBER NOT NULL,
    CONSTRAINT PK_QUMP PRIMARY KEY (
        QUMP_ID
    )
    ENABLE
);

ALTER TABLE MODRED.QUALIFICACAO_MATCH_PRELIMINAR
ADD CONSTRAINT FK_QUMP_MAPR FOREIGN KEY (MAPR_ID)
REFERENCES MODRED.MATCH_PRELIMINAR (MAPR_ID);

CREATE INDEX IN_FK_QUMP_MAPR ON MODRED.QUALIFICACAO_MATCH_PRELIMINAR (MAPR_ID ASC);

COMMENT ON TABLE MODRED.QUALIFICACAO_MATCH_PRELIMINAR IS 'Tabela que registra os valores do genótipo expandidos, ou seja, extraídos os valores de grupamento G, P, NMDP e separados em grupo alélico e valor alélico.';
COMMENT ON COLUMN MODRED.QUALIFICACAO_MATCH_PRELIMINAR.QUMP_ID IS 'Sequence identificador da tabela.';
COMMENT ON COLUMN MODRED.QUALIFICACAO_MATCH_PRELIMINAR.QUMP_TX_CODIGO_LOCUS IS 'Código do locus.';
COMMENT ON COLUMN MODRED.QUALIFICACAO_MATCH_PRELIMINAR.QUMP_TX_GENOTIPO IS 'Valor do alelo correspondente.';
COMMENT ON COLUMN MODRED.QUALIFICACAO_MATCH_PRELIMINAR.QUMP_NR_POSICAO_ALELO IS 'Posição do valor alélico indicado no momento do cadastro.';
COMMENT ON COLUMN MODRED.QUALIFICACAO_MATCH_PRELIMINAR.QUMP_IN_QUALIFICACAO IS 'Valor do alelo correspondente.';
COMMENT ON COLUMN MODRED.QUALIFICACAO_MATCH_PRELIMINAR.QUMP_IN_TIPO_ALELO IS 'Valor do alelo correspondente.';
COMMENT ON COLUMN MODRED.QUALIFICACAO_MATCH_PRELIMINAR.MAPR_ID IS 'Identificador (FK) da tabela de match preliminar. Indica qual o doador/paciente associados a estes valores.';

-- SEQUENCE
CREATE SEQUENCE MODRED.SQ_QUMP_ID INCREMENT BY 1 MAXVALUE 9999999999999999999999999999 MINVALUE 1 CACHE 20;

INSERT INTO MODRED.CATEGORIA_NOTIFICACAO (CANO_ID, CANO_TX_DESCRICAO) VALUES (3, 'Prescricao');
COMMIT;


INSERT INTO MODRED.CONFIGURACAO (CONF_ID, CONF_TX_VALOR) VALUES ('tamanhoArquivoAutorizacaoPacienteEmByte', '5242880');
INSERT INTO MODRED.CONFIGURACAO (CONF_ID, CONF_TX_VALOR) VALUES ('extensaoArquivoAutorizacaoPaciente', 'application/pdf');
COMMIT;

CREATE TABLE MODRED.CONSTANTE_RELATORIO 
(
  CORE_ID_CODIGO VARCHAR2(255) NOT NULL 
, CORE_TX_VALOR VARCHAR2(255) NOT NULL 
, CONSTRAINT PK_CORE PRIMARY KEY 
  (
    CORE_ID_CODIGO 
  )
  ENABLE 
);

COMMENT ON TABLE MODRED.CONSTANTE_RELATORIO IS 'Tabela que contém as constantes que serão utilizadas nos relatórios.';
COMMENT ON COLUMN MODRED.CONSTANTE_RELATORIO.CORE_ID_CODIGO IS 'Identificador único da tabela';
COMMENT ON COLUMN MODRED.CONSTANTE_RELATORIO.CORE_TX_VALOR IS 'Valor da constante.';

INSERT INTO MODRED.CONSTANTE_RELATORIO (CORE_ID_CODIGO, CORE_TX_VALOR) VALUES ('PRESIDENTE_ANVISA', 'Nome do presidente da ANVISA');
COMMIT;


CREATE TABLE MODRED.CONSTANTE_RELATORIO_AUD 
(
  AUDI_ID NUMBER NOT NULL
, CORE_ID_CODIGO VARCHAR2(255) NOT NULL 
, AUDI_TX_TIPO NUMBER NOT NULL
, CORE_TX_VALOR VARCHAR2(255) NOT NULL 
, CONSTRAINT PK_CORA PRIMARY KEY 
  (
    AUDI_ID,
    CORE_ID_CODIGO 
  )
  ENABLE 
);


-- RECURSO PARA CADASTRO DE BUSCA PRELIMINAR
INSERT INTO MODRED.RECURSO (RECU_ID, RECU_TX_SIGLA, RECU_TX_DESCRICAO)
VALUES ('107', 'CADASTRAR_BUSCA_PRELIMINAR', 'Permite a um médico realizar uma busca prévia (preliminar) para o paciente na base de doadores nacionais do Redome.');

-- PERMISSÃO PARA O MEDICO REALIZAR A BUSCA PRELIMINAR
INSERT INTO MODRED.PERMISSAO (RECU_ID, PERF_ID, PERM_IN_COM_RESTRICAO) VALUES ('107', '1', '0');
COMMIT;


-- LOCUS_EXAME_PRELIMINAR
CREATE TABLE MODRED.LOCUS_EXAME_PRELIMINAR
(
    LOEP_ID NUMBER NOT NULL,
    LOEP_TX_CODIGO_LOCUS VARCHAR2(4 BYTE) NOT NULL,
    LOEP_TX_PRIMEIRO_ALELO VARCHAR2(20 BYTE) NOT NULL,
    LOEP_TX_SEGUNDO_ALELO VARCHAR2(20 BYTE) NOT NULL,
    BUPR_ID NUMBER NOT NULL,
    CONSTRAINT PK_LOEP PRIMARY KEY (
        LOEP_ID
    )
    ENABLE
);

ALTER TABLE MODRED.LOCUS_EXAME_PRELIMINAR
ADD CONSTRAINT FK_LOEP_BUPR FOREIGN KEY (BUPR_ID)
REFERENCES MODRED.BUSCA_PRELIMINAR (BUPR_ID);

CREATE INDEX IN_FK_LOEP_BUPR ON MODRED.LOCUS_EXAME_PRELIMINAR (BUPR_ID ASC);

COMMENT ON TABLE MODRED.LOCUS_EXAME_PRELIMINAR IS 'Tabela que registra os valores, lócus por lócus, do genótipo do paciente preliminar.';
COMMENT ON COLUMN MODRED.LOCUS_EXAME_PRELIMINAR.LOEP_ID IS 'Sequence identificador da tabela.';
COMMENT ON COLUMN MODRED.LOCUS_EXAME_PRELIMINAR.LOEP_TX_CODIGO_LOCUS IS 'Código do locus.';
COMMENT ON COLUMN MODRED.LOCUS_EXAME_PRELIMINAR.LOEP_TX_PRIMEIRO_ALELO IS 'Valor do alelo na primeira posição.';
COMMENT ON COLUMN MODRED.LOCUS_EXAME_PRELIMINAR.LOEP_TX_SEGUNDO_ALELO IS 'Valor do alelo na segunda posição.';
COMMENT ON COLUMN MODRED.LOCUS_EXAME_PRELIMINAR.BUPR_ID IS 'Identificador (FK) da tabela de busca preliminar. Indica quem é a quem está associado os valores inseridos.';

-- SEQUENCE
CREATE SEQUENCE MODRED.SQ_LOEP_ID INCREMENT BY 1 MAXVALUE 9999999999999999999999999999 MINVALUE 1 CACHE 20;

COMMIT;


-- ADICIONA A COLUNA DE DOADOR
ALTER TABLE MODRED.EXAME
ADD DOAD_ID NUMBER;

-- ATUALIZANDO A COLUNA ÚNICA COM OS DADOS DE CADA DOADOR.
UPDATE MODRED.EXAME EX1
SET EX1.DOAD_ID = ( 
    SELECT  CASE WHEN DOAD_ID_NACIONAL IS NOT NULL THEN DOAD_ID_NACIONAL
            WHEN DOAD_ID_INTERNACIONAL IS NOT NULL THEN DOAD_ID_INTERNACIONAL
            WHEN DOAD_ID_CORDAO_INTERNACIONAL IS NOT NULL THEN DOAD_ID_CORDAO_INTERNACIONAL
            WHEN DOAD_ID_CORDAO_NACIONAL IS NOT NULL THEN DOAD_ID_CORDAO_NACIONAL END
    FROM EXAME EX2
    WHERE EX1.EXAM_ID = EX2.EXAM_ID
)
WHERE PACI_NR_RMR IS NULL;
COMMIT;

-- DELETANDO AS COLUNAS NÃO MAIS UTILIZADAS
ALTER TABLE MODRED.EXAME
DROP COLUMN DOAD_ID_NACIONAL;

ALTER TABLE MODRED.EXAME
DROP COLUMN DOAD_ID_INTERNACIONAL;

ALTER TABLE MODRED.EXAME
DROP COLUMN DOAD_ID_CORDAO_INTERNACIONAL;

ALTER TABLE MODRED.EXAME
DROP COLUMN DOAD_ID_CORDAO_NACIONAL;



/*TABELA DE CATEGORIA DE CHECKLIST*/
CREATE TABLE MODRED.CATEGORIA_CHECKLIST 
(
  CACH_ID NUMBER NOT NULL 
, CACH_TX_NM_CATEGORIA VARCHAR2(80 BYTE) NOT NULL 
, TIPC_ID NUMBER NOT NULL 
, CONSTRAINT PK_CACH_ID PRIMARY KEY 
  (
    CACH_ID 
  )
  USING INDEX 
  (
      CREATE UNIQUE INDEX IN_PK_CACH_ID ON CATEGORIA_CHECKLIST (CACH_ID ASC) 
  )
  ENABLE 
);

CREATE INDEX IN_FK_CACH_TIPC ON CATEGORIA_CHECKLIST (CACH_TX_NM_CATEGORIA ASC);

ALTER TABLE MODRED.CATEGORIA_CHECKLIST
ADD CONSTRAINT FK_CACH_TICH FOREIGN KEY
(
  TIPC_ID 
)
REFERENCES TIPO_CHECKLIST
(
  TIPC_ID 
)
ENABLE;

--ALTERACAO EM ITEM CHECKLIST PARA REFERENCIA DE CATEGORIA 
ALTER TABLE MODRED.ITENS_CHECKLIST 
DROP CONSTRAINT FK_ITEC_TIPC;

ALTER TABLE MODRED.ITENS_CHECKLIST RENAME COLUMN TIPC_ID TO CACH_ID;

ALTER TABLE MODRED.ITENS_CHECKLIST
ADD CONSTRAINT FK_ITEC_TIPC FOREIGN KEY
(
  CACH_ID 
)
REFERENCES MODRED.CATEGORIA_CHECKLIST
(
  CACH_ID 
)
ENABLE;

COMMENT ON COLUMN ITENS_CHECKLIST.CACH_ID IS 'Id da categoria de checklist';


--dados para poppular categorias e itens de checklist para testes
INSERT INTO "MODRED"."CATEGORIA_CHECKLIST" (CACH_ID, CACH_TX_NM_CATEGORIA, TIPC_ID) VALUES ('1', 'Anvisa', '1');
INSERT INTO "MODRED"."CATEGORIA_CHECKLIST" (CACH_ID, CACH_TX_NM_CATEGORIA, TIPC_ID) VALUES ('2', 'Dados Courier', '1');
INSERT INTO "MODRED"."ITENS_CHECKLIST" (ITEC_ID, ITEC_TX_NM_ITEM, CACH_ID) VALUES ('1', 'Arquivo 1', '1');
INSERT INTO "MODRED"."ITENS_CHECKLIST" (ITEC_ID, ITEC_TX_NM_ITEM, CACH_ID) VALUES ('2', 'Arquivo 2', '1');
INSERT INTO "MODRED"."ITENS_CHECKLIST" (ITEC_ID, ITEC_TX_NM_ITEM, CACH_ID) VALUES ('3', 'Arquivo 3', '2');
INSERT INTO "MODRED"."ITENS_CHECKLIST" (ITEC_ID, ITEC_TX_NM_ITEM, CACH_ID) VALUES ('4', 'Arquivo 4', '2');
commit;

--SEQUENCE DA RESPOSTA DE CHECKLIST QUE ESQUECI DE CRIAR =/
CREATE SEQUENCE MODRED.SQ_RESC_ID INCREMENT BY 1 MAXVALUE 9999999999999999999999999999 MINVALUE 1 CACHE 20;




--criação de tarefa de retirada de material internacional
INSERT INTO "MODRED"."TIPO_TAREFA" (TITA_ID, TITA_TX_DESCRICAO, TITA_IN_AUTOMATICA, TARE_IN_SOMENTE_AGRUPADOR) VALUES ('81', 'RETIRADA_MATERIAL_INTERNACIONAL', '0', '0');
INSERT INTO "MODRED"."RECURSO" (RECU_ID, RECU_TX_SIGLA, RECU_TX_DESCRICAO) VALUES ('109', 'RECEBER_AMOSTRA_INTERNACIONAL', 'Permite o recebimento de uma amostra internacional');
INSERT INTO "MODRED"."PERMISSAO" (RECU_ID, PERF_ID, PERM_IN_COM_RESTRICAO) VALUES ('109', '12', '0');


INSERT INTO "MODRED"."TIPO_TAREFA" (TITA_ID, TITA_TX_DESCRICAO, TITA_IN_AUTOMATICA, TARE_IN_SOMENTE_AGRUPADOR) VALUES ('82', 'ENTREGA_MATERIAL_INTERNACIONAL', '0', '0');
commit;


-- TORNANDO COLUNA DE VALOR EXPANDIDO NUMERICA
ALTER TABLE MODRED.GENOTIPO_EXPANDIDO_PRELIMINAR
MODIFY GEEP_TX_ALELO NUMBER;

ALTER TABLE MODRED.GENOTIPO_EXPANDIDO_PRELIMINAR
RENAME COLUMN GEEP_TX_ALELO TO GEEP_NR_ALELO;

-- CRIANDO COLUNA QUE IDENTIFICA O TIPO DA COLUNA
DELETE FROM MODRED.GENOTIPO_PRELIMINAR;

ALTER TABLE MODRED.GENOTIPO_PRELIMINAR
ADD GEPR_IN_COMPOSICAO_VALOR NUMBER NOT NULL;

COMMENT ON COLUMN MODRED.GENOTIPO_PRELIMINAR.GEPR_IN_COMPOSICAO_VALOR IS 
'Coluna que define a composição do valor, se Alelo, NMDP, Grupo P, Grupo G, Sorologico ou Antígeno.';

INSERT INTO MODRED.RECURSO (RECU_ID, RECU_TX_SIGLA, RECU_TX_DESCRICAO) VALUES
  (108, 'VISUALIZAR_MATCH_PRELIMINAR', 'Permite a um médico visualizar um match prévio (busca preliminar) para o paciente na base de doadores nacionais do Redome.');
INSERT INTO MODRED.PERMISSAO (PERF_ID, RECU_ID, PERM_IN_COM_RESTRICAO) VALUES (1, 108, 0);
INSERT INTO "MODRED"."TIPO_TAREFA" (TITA_ID, TITA_TX_DESCRICAO, TITA_IN_AUTOMATICA, TARE_IN_SOMENTE_AGRUPADOR) VALUES ('83', 'MATERIAL_INTERNACIONAL_RETIRADA_ENTREGA', '0', '0');
COMMIT;

-- REFAZENDO A TABELA DE QUALIFICACAO MATCH PRELIMINAR
DROP TABLE MODRED.QUALIFICACAO_MATCH_PRELIMINAR;

CREATE TABLE MODRED.QUALIFICACAO_MATCH_PRELIMINAR
  (QUMP_ID NUMBER NOT NULL,
    LOCU_ID VARCHAR2(4) NOT NULL,
    QUMP_TX_QUALIFICACAO VARCHAR2(20) NOT NULL,
    QUMP_NR_POSICAO NUMBER NOT NULL,
    MAPR_ID NUMBER,
    DOAD_ID NUMBER,
    QUMP_TX_GENOTIPO VARCHAR2(40),
    QUMP_NR_TIPO NUMBER
  );

ALTER TABLE MODRED.QUALIFICACAO_MATCH_PRELIMINAR ADD CONSTRAINT PK_QUMP PRIMARY KEY (QUMP_ID)
   USING INDEX (CREATE UNIQUE INDEX MODRED.IN_PK_QUMP ON MODRED.QUALIFICACAO_MATCH_PRELIMINAR (QUMP_ID));
   
ALTER TABLE MODRED.QUALIFICACAO_MATCH_PRELIMINAR ADD CONSTRAINT FK_QUMP_LOCU FOREIGN KEY (LOCU_ID)
   REFERENCES MODRED.LOCUS (LOCU_ID);
ALTER TABLE MODRED.QUALIFICACAO_MATCH_PRELIMINAR ADD CONSTRAINT FK_QUMP_MAPR FOREIGN KEY (MAPR_ID)
   REFERENCES MODRED.MATCH_PRELIMINAR (MAPR_ID);
ALTER TABLE MODRED.QUALIFICACAO_MATCH_PRELIMINAR ADD CONSTRAINT FK_QUMP_DOAD FOREIGN KEY (DOAD_ID)
   REFERENCES MODRED.DOADOR (DOAD_ID);

  COMMENT ON COLUMN MODRED.QUALIFICACAO_MATCH_PRELIMINAR.QUMP_ID IS 'Chave primária da tabela de qualificação e match';
  COMMENT ON COLUMN MODRED.QUALIFICACAO_MATCH_PRELIMINAR.LOCU_ID IS 'Chave estrangeira para tabela de locus';
  COMMENT ON COLUMN MODRED.QUALIFICACAO_MATCH_PRELIMINAR.QUMP_TX_QUALIFICACAO IS 'Qualificação dividida em:
M -> MISMATCH
L -> MISMATCH ALELICO
P -> POTENCIAL
A -> MATCH_ALELICO';
  COMMENT ON COLUMN MODRED.QUALIFICACAO_MATCH_PRELIMINAR.QUMP_NR_POSICAO IS '1 - Alelo 1, 2 - Alelo 2';
  COMMENT ON COLUMN MODRED.QUALIFICACAO_MATCH_PRELIMINAR.MAPR_ID IS 'Chave estrangeira para tabela de match preliminar';
  COMMENT ON COLUMN MODRED.QUALIFICACAO_MATCH_PRELIMINAR.DOAD_ID IS 'Chave estrangeira para tabela de doador';
  COMMENT ON COLUMN MODRED.QUALIFICACAO_MATCH_PRELIMINAR.QUMP_TX_GENOTIPO IS 'Genótipo comparado entre o paciente e o doador.';
  COMMENT ON COLUMN MODRED.QUALIFICACAO_MATCH_PRELIMINAR.QUMP_NR_TIPO IS '0- para sorologia, 1- para antígeno, 2- NMDP, 3- Grupo G, 4-  Grupo P e 5 Alelo';
  COMMENT ON TABLE MODRED.QUALIFICACAO_MATCH_PRELIMINAR  IS 'Tabela de qualificação de um match';

 CREATE INDEX MODRED.IN_FK_QUMP_DOAD ON MODRED.QUALIFICACAO_MATCH_PRELIMINAR (DOAD_ID);
 CREATE INDEX MODRED.IN_FK_QUMP_LOCU ON MODRED.QUALIFICACAO_MATCH_PRELIMINAR (LOCU_ID);
 CREATE INDEX MODRED.IN_FK_QUMP_MATC ON MODRED.QUALIFICACAO_MATCH_PRELIMINAR (MAPR_ID);

INSERT INTO RECURSO (RECU_ID, RECU_TX_SIGLA, RECU_TX_DESCRICAO) VALUES 
(110, 'ANALISE_MATCH_PRELIMINAR', 'Permite a um médico comparar o genótio do paciente da busca preliminar com os doadores do Redome.');
INSERT INTO PERMISSAO (PERF_ID, RECU_ID, PERM_IN_COM_RESTRICAO) VALUES
(1, 110, 0);
COMMIT;

ALTER TABLE MODRED.PEDIDO_LOGISTICA 
ADD (PELO_TX_RETIRADA_PAIS VARCHAR2(80) );

ALTER TABLE MODRED.PEDIDO_LOGISTICA 
ADD (PELO_TX_RETIRADA_ESTADO VARCHAR2(80) );

ALTER TABLE MODRED.PEDIDO_LOGISTICA 
ADD (PELO_TX_RETIRADA_CIDADE VARCHAR2(80) );

ALTER TABLE MODRED.PEDIDO_LOGISTICA 
ADD (PELO_TX_RETIRADA_RUA VARCHAR2(200) );

ALTER TABLE MODRED.PEDIDO_LOGISTICA 
ADD (PELO_TX_TELEFONE VARCHAR2(15) );

COMMENT ON COLUMN MODRED.PEDIDO_LOGISTICA.PELO_TX_RETIRADA_PAIS IS 'Campo de pais de retirada usado para medula internacional';

COMMENT ON COLUMN MODRED.PEDIDO_LOGISTICA.PELO_TX_RETIRADA_ESTADO IS 'Campo de estado de retirado usado para medula internacional
';

COMMENT ON COLUMN MODRED.PEDIDO_LOGISTICA.PELO_TX_RETIRADA_CIDADE IS 'Campo de cidade de retirado usado para medula internacional
';

COMMENT ON COLUMN MODRED.PEDIDO_LOGISTICA.PELO_TX_RETIRADA_RUA IS 'Campo de rua  de retirado usado para medula internacional
';

COMMENT ON COLUMN MODRED.PEDIDO_LOGISTICA.PELO_TX_TELEFONE IS 'Campo de telefone de retirado usado para medula internacional
';


ALTER TABLE MODRED.PEDIDO_LOGISTICA 
ADD (PELO_TX_ID_DOADOR_LOCAL VARCHAR2(20) );

ALTER TABLE MODRED.PEDIDO_LOGISTICA 
ADD (PELO_TX_HAWB VARCHAR2(20) );

COMMENT ON COLUMN MODRED.PEDIDO_LOGISTICA.PELO_TX_ID_DOADOR_LOCAL IS 'Identificação do doador no país de origem da medula';

COMMENT ON COLUMN MODRED.PEDIDO_LOGISTICA.PELO_TX_HAWB IS 'Localizador';

ALTER TABLE MODRED.PEDIDO_LOGISTICA_AUD 
ADD (PELO_TX_RETIRADA_PAIS VARCHAR2(80) );

ALTER TABLE MODRED.PEDIDO_LOGISTICA_AUD 
ADD (PELO_TX_RETIRADA_ESTADO VARCHAR2(80) );

ALTER TABLE MODRED.PEDIDO_LOGISTICA_AUD 
ADD (PELO_TX_RETIRADA_CIDADE VARCHAR2(80) );

ALTER TABLE MODRED.PEDIDO_LOGISTICA_AUD 
ADD (PELO_TX_RETIRADA_RUA VARCHAR2(200) );

ALTER TABLE MODRED.PEDIDO_LOGISTICA_AUD 
ADD (PELO_TX_TELEFONE VARCHAR2(15) );

ALTER TABLE MODRED.PEDIDO_LOGISTICA_AUD 
ADD (PELO_TX_ID_DOADOR_LOCAL VARCHAR2(20) );

ALTER TABLE MODRED.PEDIDO_LOGISTICA_AUD 
ADD (PELO_TX_HAWB VARCHAR2(20) );


-- AUDITORIA
-- ADICIONA A COLUNA DE DOADOR
ALTER TABLE MODRED.EXAME_AUD
ADD DOAD_ID NUMBER;

-- ATUALIZANDO A COLUNA ÚNICA COM OS DADOS DE CADA DOADOR.
UPDATE MODRED.EXAME_AUD EX1
SET EX1.DOAD_ID = ( 
    SELECT  CASE WHEN DOAD_ID_NACIONAL IS NOT NULL THEN DOAD_ID_NACIONAL
            WHEN DOAD_ID_INTERNACIONAL IS NOT NULL THEN DOAD_ID_INTERNACIONAL
            WHEN DOAD_ID_CORDAO_INTERNACIONAL IS NOT NULL THEN DOAD_ID_CORDAO_INTERNACIONAL
            WHEN DOAD_ID_CORDAO_NACIONAL IS NOT NULL THEN DOAD_ID_CORDAO_NACIONAL END
    FROM EXAME_AUD EX2
    WHERE EX1.EXAM_ID = EX2.EXAM_ID
)
WHERE PACI_NR_RMR IS NULL;
COMMIT;

-- DELETANDO AS COLUNAS NÃO MAIS UTILIZADAS
ALTER TABLE MODRED.EXAME_AUD
DROP COLUMN DOAD_ID_NACIONAL;

ALTER TABLE MODRED.EXAME_AUD
DROP COLUMN DOAD_ID_INTERNACIONAL;

ALTER TABLE MODRED.EXAME_AUD
DROP COLUMN DOAD_ID_CORDAO_INTERNACIONAL;

ALTER TABLE MODRED.EXAME_AUD
DROP COLUMN DOAD_ID_CORDAO_NACIONAL;

ALTER TABLE MODRED.PEDIDO_TRANSPORTE  
MODIFY (PETR_HR_PREVISTA_RETIRADA NULL);

ALTER TABLE MODRED.PEDIDO_TRANSPORTE  
MODIFY (TRAN_ID NULL);