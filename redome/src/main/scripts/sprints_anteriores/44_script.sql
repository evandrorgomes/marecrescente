--FILIPE PAES/INICIO (ESTORIA 2696)
CREATE TABLE MODRED.DESTINO_COLETA 
(
  DECO_ID NUMBER NOT NULL 
, DECO_TX_DESCRICAO VARCHAR2(60 BYTE) NOT NULL 
, CONSTRAINT PK_DECO PRIMARY KEY 
  (
    DECO_ID 
  )
  USING INDEX 
  (
      CREATE UNIQUE INDEX IN_PK_DECO ON DESTINO_COLETA (DECO_ID ASC) 
  )ENABLE 
) ;

COMMENT ON TABLE DESTINO_COLETA IS 'DESTINOS DADOS A COLETA RETIRADA DO DOADOR';
COMMENT ON COLUMN DESTINO_COLETA.DECO_ID IS 'ID DO DESTINO DE COLETA';
COMMENT ON COLUMN DESTINO_COLETA.DECO_TX_DESCRICAO IS 'NOME DO DESTINO DADO A COLETA';

-- CRIAÇÃO DA TABELA DE SISTEMA
CREATE TABLE MODRED.SISTEMA(
    SIST_ID NUMBER NOT NULL,
    SIST_TX_NOME VARCHAR2(100) NOT NULL,
    CONSTRAINT PK_SIST PRIMARY KEY (SIST_ID) USING INDEX (
      CREATE UNIQUE INDEX IN_PK_SIST ON MODRED.SISTEMA (SIST_ID ASC)
    )
  ENABLE 
);

COMMENT ON TABLE MODRED.SISTEMA IS 'Tabela que armazena quais são os sistemas (parceiros) envolvidos com o ModRed.';
COMMENT ON COLUMN MODRED.SISTEMA.SIST_ID IS 'Chave primária autoincremental e sequencial da tabela.';
COMMENT ON COLUMN MODRED.SISTEMA.SIST_TX_NOME IS 'Nome do sistema.';

-- CARGA DOS DADOS DE SISTEMA
INSERT INTO MODRED.SISTEMA(SIST_ID, SIST_TX_NOME) VALUES(1, 'Redome');
INSERT INTO MODRED.SISTEMA(SIST_ID, SIST_TX_NOME) VALUES(2, 'Centro Avaliador');
INSERT INTO MODRED.SISTEMA(SIST_ID, SIST_TX_NOME) VALUES(3, 'Call Center');
INSERT INTO MODRED.SISTEMA(SIST_ID, SIST_TX_NOME) VALUES(4, 'Centro Transplantador');
INSERT INTO MODRED.SISTEMA(SIST_ID, SIST_TX_NOME) VALUES(5, 'Transportadora');
INSERT INTO MODRED.SISTEMA(SIST_ID, SIST_TX_NOME) VALUES(6, 'Laboratório');
COMMIT;

-- ASSOCIAÇÃO COM PERFIL
ALTER TABLE MODRED.PERFIL
ADD SIST_ID NUMBER;

COMMENT ON COLUMN MODRED.PERFIL.SIST_ID IS 'Define com qual sistema o perfil está associado.';

ALTER TABLE MODRED.PERFIL
ADD CONSTRAINT FK_PERF_SIST FOREIGN KEY(SIST_ID)
REFERENCES MODRED.SISTEMA(SIST_ID)
ENABLE;

CREATE INDEX IN_FK_PERF_SIST ON MODRED.PERFIL (SIST_ID ASC);

-- ATUALIZANDO A TABELA DE PERFIL DE ACORDO COM SEUS SISTEMAS.
UPDATE MODRED.PERFIL SET SIST_ID = 1 WHERE PERF_ID = 1;
UPDATE MODRED.PERFIL SET SIST_ID = 2 WHERE PERF_ID = 2;
UPDATE MODRED.PERFIL SET SIST_ID = 1 WHERE PERF_ID = 3;
UPDATE MODRED.PERFIL SET SIST_ID = 1 WHERE PERF_ID = 5;
UPDATE MODRED.PERFIL SET SIST_ID = 3 WHERE PERF_ID = 6;
UPDATE MODRED.PERFIL SET SIST_ID = 3 WHERE PERF_ID = 7;
UPDATE MODRED.PERFIL SET SIST_ID = 3 WHERE PERF_ID = 8;
UPDATE MODRED.PERFIL SET SIST_ID = 1 WHERE PERF_ID = 9;
UPDATE MODRED.PERFIL SET SIST_ID = 3 WHERE PERF_ID = 10;
UPDATE MODRED.PERFIL SET SIST_ID = 1 WHERE PERF_ID = 11;
UPDATE MODRED.PERFIL SET SIST_ID = 5 WHERE PERF_ID = 12;
UPDATE MODRED.PERFIL SET SIST_ID = 1 WHERE PERF_ID = 13;
UPDATE MODRED.PERFIL SET SIST_ID = 4 WHERE PERF_ID = 14;
UPDATE MODRED.PERFIL SET SIST_ID = 4 WHERE PERF_ID = 15;
UPDATE MODRED.PERFIL SET SIST_ID = 1 WHERE PERF_ID = 16;
UPDATE MODRED.PERFIL SET SIST_ID = 5 WHERE PERF_ID = 17;
UPDATE MODRED.PERFIL SET SIST_ID = 6 WHERE PERF_ID = 18;
UPDATE MODRED.PERFIL SET SIST_ID = 1 WHERE PERF_ID = 19;
UPDATE MODRED.PERFIL SET SIST_ID = 1 WHERE PERF_ID = 20;
UPDATE MODRED.PERFIL SET SIST_ID = 1 WHERE PERF_ID = 21;
UPDATE MODRED.PERFIL SET SIST_ID = 1 WHERE PERF_ID = 22;
UPDATE MODRED.PERFIL SET SIST_ID = 1 WHERE PERF_ID = 23;
UPDATE MODRED.PERFIL SET SIST_ID = 1 WHERE PERF_ID = 24;
UPDATE MODRED.PERFIL SET SIST_ID = 1 WHERE PERF_ID = 25;
COMMIT;

-- TORNANDO A COLUNA NOT NULL
ALTER TABLE MODRED.PERFIL
MODIFY SIST_ID NOT NULL;

-- REMOVENDO COLUNA CRIADA E NUNCA UTILIZADA.
ALTER TABLE MODRED.PERFIL
DROP COLUMN PERF_NR_ENTITY_STATUS;

-- RECURSO PARA VISUALIZAR USUÁRIOS DESTINADO AO PERFIL ADMIN.
INSERT INTO "MODRED"."RECURSO" (RECU_ID, RECU_TX_SIGLA, RECU_TX_DESCRICAO) VALUES ('139', 'LISTAR_USUARIOS', 'Permite listar os usuários cadastrados no sistema.');
INSERT INTO "MODRED"."PERMISSAO" (RECU_ID, PERF_ID, PERM_IN_COM_RESTRICAO) VALUES ('139', '19', '0');
==== BASE ====
COMMIT;

-- INSERE RECURSO ALTERAR USUÁRIO
INSERT INTO "MODRED"."RECURSO" (RECU_ID, RECU_TX_SIGLA, RECU_TX_DESCRICAO) VALUES ('140', 'ALTERAR_USUARIO', 'Permite altera um usuário no sistema.');
INSERT INTO "MODRED"."PERMISSAO" (RECU_ID, PERF_ID, PERM_IN_COM_RESTRICAO) VALUES ('140', '19', '0');
COMMIT;

ALTER TABLE MODRED.LABORATORIO 
ADD (LABO_NR_ID_REDOMEWEB NUMBER );

COMMENT ON COLUMN MODRED.LABORATORIO.LABO_NR_ID_REDOMEWEB IS 'Identificador do laboratório na base do REDOMEWeb (integração).';

INSERT INTO MODRED.LABORATORIO (LABO_ID, LABO_TX_NOME, LABO_IN_FAZ_CT, LABO_NR_QUANT_EXAME_CT, LABO_TX_NOME_CONTATO, LABO_NR_ID_REDOMEWEB)
VALUES (4, 'LABORATORIO REDOMENET WEB', 1, 200, 'Gestor Redome', 152);
COMMIT;

ALTER TABLE MODRED.PEDIDO_EXAME 
ADD (PEEX_ID_SOLICITACAO_REDOMEWEB NUMBER );

COMMENT ON COLUMN MODRED.PEDIDO_EXAME.PEEX_ID_SOLICITACAO_REDOMEWEB IS 'Referência à solicitação criada no REDOMEWeb.';

ALTER TABLE MODRED.PEDIDO_EXAME_AUD 
ADD (PEEX_ID_SOLICITACAO_REDOMEWEB NUMBER );

ALTER TABLE MODRED.HEMO_ENTIDADE 
ADD (HEEN_ID_HEMOCENTRO_REDOMEWEB NUMBER );

COMMENT ON COLUMN MODRED.HEMO_ENTIDADE.HEEN_ID_HEMOCENTRO_REDOMEWEB IS 'Referência para o hemocentro do redomeweb.';

INSERT INTO MODRED.HEMO_ENTIDADE (HEEN_ID, HEEN_TX_NOME, HEEN_IN_SELECIONAVEL, HEEN_TX_CLASSIFICACAO, HEEN_ID_HEMOCENTRO_REDOMEWEB)
VALUES (3, 'HEMOCENTRO REDOMENET WEB', 1, 'HC', 151);
COMMIT;

ALTER TABLE MODRED.DOADOR 
ADD (HEEN_ID NUMBER );

CREATE INDEX MODRED.IN_FK_DOAD_HEEN ON DOADOR (HEEN_ID);

ALTER TABLE MODRED.DOADOR
ADD CONSTRAINT FK_DOAD_HEEN FOREIGN KEY
(
  HEEN_ID 
)
REFERENCES MODRED.HEMO_ENTIDADE
(
  HEEN_ID 
)
ENABLE;

COMMENT ON COLUMN MODRED.DOADOR.HEEN_ID IS 'Referencia para o hemocentro/hemonucleo que cadastratrou o doador.';

INSERT INTO MODRED.TIPO_EXAME (TIEX_ID, TIEX_TX_DESCRICAO)
VALUES (2, 'Locus C, Alta Resolução Classe II');
INSERT INTO MODRED.TIPO_EXAME_LOCUS (LOCU_ID, TIEX_ID)
VALUES ('C', 2);
INSERT INTO MODRED.TIPO_EXAME_LOCUS (LOCU_ID, TIEX_ID)
VALUES ('DRB1', 2);
INSERT INTO MODRED.TIPO_EXAME_LOCUS (LOCU_ID, TIEX_ID)
VALUES ('DQB1', 2);
COMMIT;

ALTER TABLE STATUS_PEDIDO_EXAME  
MODIFY (STPX_TX_DESCRICAO VARCHAR2(40 BYTE) );

ALTER TABLE MODRED.STATUS_PEDIDO_EXAME 
ADD (STPX_ID_STATUS_REDOMEWEB NUMBER );

COMMENT ON COLUMN MODRED.STATUS_PEDIDO_EXAME.STPX_ID_STATUS_REDOMEWEB IS 'Identificador referente ao status do redomeweb.';

UPDATE MODRED.STATUS_PEDIDO_EXAME SET STPX_ID_STATUS_REDOMEWEB = 2
WHERE STPX_ID = 3;
UPDATE MODRED.STATUS_PEDIDO_EXAME SET STPX_ID_STATUS_REDOMEWEB = 1
WHERE STPX_ID = 2;
UPDATE MODRED.STATUS_PEDIDO_EXAME SET STPX_ID_STATUS_REDOMEWEB = 0,
STPX_TX_DESCRICAO = 'NÃO ATENDIDA - SEM HLA/IDM'
WHERE STPX_ID = 4;

INSERT INTO STATUS_PEDIDO_EXAME (STPX_ID, STPX_TX_DESCRICAO, STPX_ID_STATUS_REDOMEWEB)
VALUES (5, 'NÃO ATENDIDA - COM HLA/IDM', 3);
INSERT INTO STATUS_PEDIDO_EXAME (STPX_ID, STPX_TX_DESCRICAO, STPX_ID_STATUS_REDOMEWEB)
VALUES (6, 'ENCAMINHADA', 5);
INSERT INTO STATUS_PEDIDO_EXAME (STPX_ID, STPX_TX_DESCRICAO, STPX_ID_STATUS_REDOMEWEB)
VALUES (7, 'ENCAMINHADA RECUSADA', 6);
INSERT INTO STATUS_PEDIDO_EXAME (STPX_ID, STPX_TX_DESCRICAO, STPX_ID_STATUS_REDOMEWEB)
VALUES (8, 'ENCAMINHADA ATENDIDA', 7);

UPDATE MODRED.STATUS_PEDIDO_EXAME SET STPX_ID_STATUS_REDOMEWEB = 10
WHERE STPX_ID = 0;
INSERT INTO STATUS_PEDIDO_EXAME (STPX_ID, STPX_TX_DESCRICAO, STPX_ID_STATUS_REDOMEWEB)
VALUES (9, 'AGUARDANDO ACEITE', 11);
INSERT INTO STATUS_PEDIDO_EXAME (STPX_ID, STPX_TX_DESCRICAO, STPX_ID_STATUS_REDOMEWEB)
VALUES (10, 'ATENDIDA PARCIALMENTE', 13);
INSERT INTO STATUS_PEDIDO_EXAME (STPX_ID, STPX_TX_DESCRICAO, STPX_ID_STATUS_REDOMEWEB)
VALUES (11, 'ENCAMINHADA ATENDIDA PARCIALMENTE', 14);
INSERT INTO STATUS_PEDIDO_EXAME (STPX_ID, STPX_TX_DESCRICAO, STPX_ID_STATUS_REDOMEWEB)
VALUES (12, 'ENCAMINHADA DEVOLVIDA', 15);
INSERT INTO STATUS_PEDIDO_EXAME (STPX_ID, STPX_TX_DESCRICAO, STPX_ID_STATUS_REDOMEWEB)
VALUES (13, 'AGUARDANDO REDOME', 16);
COMMIT;


-- INSERINDO RECURSO PARA CADASTRAR USUÁRIO
INSERT INTO "MODRED"."RECURSO" (RECU_ID, RECU_TX_SIGLA, RECU_TX_DESCRICAO) VALUES ('141', 'CADASTRAR_USUARIO', 'Permite cadastrar um novo usuário no sistema.');

-- PERMISSÃO PARA SOMENTE PERFIL ADMIN
INSERT INTO "MODRED"."PERMISSAO" (RECU_ID, PERF_ID, PERM_IN_COM_RESTRICAO) VALUES ('141', '19', '0');
COMMIT;

-- REMOVENDO TABELAS TEMPORÁRIAS CRIADAS POR UM COMPORTAMENTO INESPERADO DO HIBERNATE
-- COM ESTRAGÉGIA JOINED ENTRE TABELAS. INFORMAMOS UMA NOVA CONFIGURAÇÃO PARA O HIBERNATE
-- E REMOVEMOS AS TABELAS. 
DROP TABLE "MODRED"."HT_USUARIO";
DROP TABLE "MODRED"."HT_USUARIO_BANCO_SANGUE_CORDAO";

-- INSERINDO RECURSO PARA INATIVAR USUARIO
INSERT INTO "MODRED"."RECURSO" (RECU_ID, RECU_TX_SIGLA, RECU_TX_DESCRICAO) VALUES ('142', 'INATIVAR_USUARIO', 'Permite inativar um usuário do sistema, bloqueando seu acesso por tempo indeterminado.');

-- PERMISSÃO PARA SOMENTE PERFIL ADMIN
INSERT INTO "MODRED"."PERMISSAO" (RECU_ID, PERF_ID, PERM_IN_COM_RESTRICAO) VALUES ('142', '19', '0');
COMMIT;

-- REMOVER COLUNA DE STATUS NÃO UTILIZADA NO USUARIO.
ALTER TABLE MODRED.USUARIO
DROP COLUMN USUA_NR_ENTITY_STATUS CASCADE CONSTRAINTS;

-- ADICIONA O FLAG INDICANDO SE PODE OU NÃO SER CADASTRADO PELO REDOME.
ALTER TABLE MODRED.SISTEMA
ADD SIST_IN_DISPONIVEL_REDOME NUMBER;
COMMENT ON COLUMN MODRED.SISTEMA.SIST_IN_DISPONIVEL_REDOME IS 'Indica quando o Redome pode ou não criar um novo usuário utilizando com perfil associado a este sistema. Flag definido para separar o que é de obrigação de terceiros (parceiro) ou do Redome.';

UPDATE MODRED.SISTEMA
SET SIST_IN_DISPONIVEL_REDOME = 1;

UPDATE MODRED.SISTEMA
SET SIST_IN_DISPONIVEL_REDOME = 0
WHERE SIST_ID IN (2, 4, 5, 6);



INSERT INTO "MODRED"."MOTIVO_STATUS_DOADOR_RECURSO" (MOSD_ID, RECU_ID) VALUES ('9', '91');
INSERT INTO "MODRED"."MOTIVO_STATUS_DOADOR_RECURSO" (MOSD_ID, RECU_ID) VALUES ('10', '91');
commit;

-- LEVANDO A FASE DO MATCH PARA DENTRO DO DOADOR
ALTER TABLE MODRED.DOADOR
ADD DOAD_TX_FASE VARCHAR2(2);
COMMENT ON COLUMN MODRED.DOADOR.DOAD_TX_FASE IS 'Indica, de acordo com os exames recebidos e aprovados, qual a fase encontra-se o doador. F1: Match inicial com paciente; F2: A, B, C, DR e DQ de média pra cima (Alelos e grupos NMDP, G e P); F3: Exame CT realizado.';

-- ATUALIZANDO A COLUNA COM AS INFORMAÇOES CONTIDAS NO MATCH
MERGE INTO MODRED.DOADOR DOAD 
    USING 
    (SELECT DOAD_ID, MAX(MATC_TX_SITUACAO) AS MATC_TX_SITUACAO, MAX(MATC_ID) AS MATC_ID 
    FROM MODRED.MATCH 
    GROUP BY DOAD_ID) MAT
ON( DOAD.DOAD_ID = MAT.DOAD_ID )
WHEN MATCHED THEN UPDATE SET DOAD.DOAD_TX_FASE = MAT.MATC_TX_SITUACAO;
COMMIT;

-- REMOVENDO ANTIGA REFERÊNCIA A FASE QUE ESTAVA NO MATCH
ALTER TABLE MODRED.MATCH
DROP COLUMN MATC_TX_SITUACAO CASCADE CONSTRAINTS;









--TABELAS DE BUSCA CHECKLIST

CREATE TABLE MODRED.TIPO_BUSCA_CHECKLIST 
(
  TIBC_ID NUMBER NOT NULL 
, TIBC_TX_DESCRICAO VARCHAR2(100) 
, TIBC_NR_AGE_DIAS NUMBER 
, CONSTRAINT TIBC_ID PRIMARY KEY 
  (
    TIBC_ID 
  )
  USING INDEX 
  (
      CREATE UNIQUE INDEX IN_PK_TIBC ON TIPO_BUSCA_CHECKLIST (TIBC_ID ASC) 
  )
  ENABLE 
);

COMMENT ON TABLE MODRED.TIPO_BUSCA_CHECKLIST IS 'Tabela de tipo de busca checklist';

COMMENT ON COLUMN MODRED.TIPO_BUSCA_CHECKLIST.TIBC_ID IS 'Identificação de tipo de busca checklist';

COMMENT ON COLUMN MODRED.TIPO_BUSCA_CHECKLIST.TIBC_TX_DESCRICAO IS 'Descrição de tipo de busca de chcklist';

COMMENT ON COLUMN MODRED.TIPO_BUSCA_CHECKLIST.TIBC_NR_AGE_DIAS IS 'Agging em dias';



CREATE TABLE MODRED.BUSCA_CHECKLIST 
(
  BUCH_ID NUMBER NOT NULL 
, BUCH_DT_CRIACAO TIMESTAMP(6) NOT NULL 
, BUCH_DT_VISTO TIMESTAMP(6) 
, BUCH_IN_VISTO NUMBER  
, TIBC_ID NUMBER NOT NULL 
, USUA_ID NUMBER 
, BUSC_ID NUMBER NOT NULL 
, BUCH_AGE NUMBER 
, CONSTRAINT PK_BUCH_ID PRIMARY KEY 
  (
    BUCH_ID 
  )
  USING INDEX 
  (
      CREATE UNIQUE INDEX IN_PK_BUCH_ID ON BUSCA_CHECKLIST (BUCH_ID ASC) 
  )
  ENABLE 
);

CREATE INDEX IN_FK_BUCH_BUSC ON BUSCA_CHECKLIST (BUSC_ID ASC);

CREATE INDEX IN_FK_BUCH_TIBH ON BUSCA_CHECKLIST (TIBC_ID ASC);

CREATE INDEX IN_FK_BUCH_USUA ON BUSCA_CHECKLIST (USUA_ID ASC);

ALTER TABLE BUSCA_CHECKLIST
ADD CONSTRAINT FK_BUCH_BUSC FOREIGN KEY
(
  BUSC_ID 
)
REFERENCES BUSCA
(
  BUSC_ID 
)
ENABLE;

ALTER TABLE BUSCA_CHECKLIST
ADD CONSTRAINT FK_BUCH_USUA FOREIGN KEY
(
  USUA_ID 
)
REFERENCES USUARIO
(
  USUA_ID 
)
ENABLE;

ALTER TABLE BUSCA_CHECKLIST
ADD CONSTRAINT FK_BUCH_TIBH FOREIGN KEY
(
  TIBC_ID 
)
REFERENCES TIPO_BUSCA_CHECKLIST
(
  TIBC_ID 
)
ENABLE;

COMMENT ON TABLE MODRED.BUSCA_CHECKLIST IS 'Tabela de checklist para busca.';

COMMENT ON COLUMN MODRED.BUSCA_CHECKLIST.BUSC_ID IS 'Identificação da busca do checklist';

COMMENT ON COLUMN MODRED.BUSCA_CHECKLIST.BUCH_ID IS 'Chave primaria de identificacao de busca checklist';

COMMENT ON COLUMN MODRED.BUSCA_CHECKLIST.BUCH_DT_CRIACAO IS 'Data e hora da atualização de checklist';

COMMENT ON COLUMN MODRED.BUSCA_CHECKLIST.BUCH_IN_VISTO IS 'Indica se o checklis foi ou não visualizado';

COMMENT ON COLUMN MODRED.BUSCA_CHECKLIST.TIBC_ID IS 'Tipo de checklist
';

COMMENT ON COLUMN MODRED.BUSCA_CHECKLIST.USUA_ID IS 'Usuário que deu o visto no checklist';

COMMENT ON COLUMN MODRED.BUSCA_CHECKLIST.BUSC_ID IS 'Identificação da busca do checklist';

COMMENT ON COLUMN BUSCA_CHECKLIST.BUCH_DT_VISTO IS 'Flag para marcar se o item foi visto pelo analista';

COMMENT ON COLUMN BUSCA_CHECKLIST.BUCH_AGE IS 'Quantidade de dias em que foi realizada a tarefa de busca';


CREATE SEQUENCE SQ_BUCH_ID INCREMENT BY 1 MAXVALUE 9999999999999999999999999999 MINVALUE 1 CACHE 20;


UPDATE RECURSO SET recu_tx_sigla = 'SOLICITAR_FASE_2_NACIONAL',recu_tx_descricao = 'Permite ao analista de busca solicitar fase 2 para um doador nacional.'
WHERE recu_tx_sigla = 'PROGREDIR_FASE_2';
UPDATE RECURSO SET recu_tx_sigla = 'SOLICITAR_FASE_3_NACIONAL',recu_tx_descricao = 'Permite ao analista de busca solicitar fase 3 para um doador nacional.'
WHERE recu_tx_sigla = 'PROGREDIR_FASE_3';
UPDATE RECURSO SET recu_tx_sigla = 'DISPONIBILIZAR_DOADOR'
WHERE recu_tx_sigla = 'PROGREDIR_DISPONIBILIZAR';
COMMIT;


CREATE TABLE MODRED.SOLICITACAO_REDOMEWEB 
(
  SORE_ID NUMBER NOT NULL 
, SORE_NR_TIPO NUMBER NOT NULL 
, SORE_ID_SOLICITACAO_REDOMEWEB NUMBER NOT NULL 
, SORE_DT_CRIACAO TIMESTAMP NOT NULL 
, PEEX_ID NUMBER NOT NULL 
, CONSTRAINT PK_SORE PRIMARY KEY 
  (
    SORE_ID 
  )
  ENABLE 
);

CREATE INDEX MODRED.IN_FK_SORE_PEEX ON MODRED.SOLICITACAO_REDOMEWEB (PEEX_ID);

ALTER TABLE MODRED.SOLICITACAO_REDOMEWEB
ADD CONSTRAINT UK_SORE UNIQUE 
(
  SORE_ID_SOLICITACAO_REDOMEWEB 
, SORE_NR_TIPO 
)
USING INDEX 
(
    CREATE UNIQUE INDEX MODRED.IN_UK_SORE ON MODRED.SOLICITACAO_REDOMEWEB (SORE_ID_SOLICITACAO_REDOMEWEB ASC, SORE_NR_TIPO ASC) 
)
 ENABLE;

ALTER TABLE MODRED.SOLICITACAO_REDOMEWEB
ADD CONSTRAINT FK_SORE_PEEX FOREIGN KEY
(
  PEEX_ID 
)
REFERENCES MODRED.PEDIDO_EXAME
(
  PEEX_ID 
)
ENABLE;

COMMENT ON TABLE MODRED.SOLICITACAO_REDOMEWEB IS 'Tabela de relacionamento entro os pedidos de exame com as solicitações do redomeweb.';
COMMENT ON COLUMN MODRED.SOLICITACAO_REDOMEWEB.SORE_ID IS 'Identificador sequencial da tabela.';
COMMENT ON COLUMN MODRED.SOLICITACAO_REDOMEWEB.SORE_NR_TIPO IS 'Tipo da solicitação do redomeweb podendo ser (Exame ou amostra de sangue).';
COMMENT ON COLUMN MODRED.SOLICITACAO_REDOMEWEB.SORE_ID_SOLICITACAO_REDOMEWEB IS 'Identificador da solicitação do redomeweb.';
COMMENT ON COLUMN MODRED.SOLICITACAO_REDOMEWEB.SORE_DT_CRIACAO IS 'Data de criação.';
COMMENT ON COLUMN MODRED.SOLICITACAO_REDOMEWEB.PEEX_ID IS 'Referência para a tabela de  pedido de exame.';


CREATE SEQUENCE MODRED.SQ_SORE_ID INCREMENT BY 1 MAXVALUE 9999999999999999999999999999 MINVALUE 1 CACHE 20 ORDER;

ALTER TABLE MODRED.PEDIDO_EXAME 
DROP COLUMN PEEX_ID_SOLICITACAO_REDOMEWEB;

UPDATE MODRED.RECURSO SET recu_tx_sigla = 'SOLICITAR_FASE3_PACIENTE'
WHERE recu_tx_sigla = 'PEDIDO_EXAME_FASE3_PACIENTE';
COMMIT;

DELETE FROM TAREFA WHERE TITA_ID = (SELECT TITA_ID FROM TIPO_TAREFA WHERE tita_tx_descricao = 'PEDIDO_COLETA_HEMOCENTRO');
DELETE FROM TIPO_TAREFA WHERE tita_tx_descricao = 'PEDIDO_COLETA_HEMOCENTRO';
COMMIT;



-- INCLUINDO A COLUNA QUE DEFINE O PESO DE UM LOCUS NA DEFINIÇÃO DA RESOLUÇÃO DE UM HLA.
ALTER TABLE MODRED.LOCUS
ADD LOCU_NR_PESO_FASE2 NUMBER;
COMMENT ON COLUMN MODRED.LOCUS.LOCU_NR_PESO_FASE2 IS 'Define o peso de cada locus para definir se o doador está em Fase 1 ou 2, a partir do HLA. A relação fase/somatório é: Fase 1: Somatório menor que 1; Fase 2: maior que 1.';

-- DISTRIBUINDO PESOS PARA COMPOR 1 E DEFINIR OU NÃO O DOADOR COMO FASE 2.
UPDATE MODRED.LOCUS
SET LOCU_NR_PESO_FASE2 = 0;

UPDATE MODRED.LOCUS
SET LOCU_NR_PESO_FASE2 = 0.2
WHERE LOCU_ID IN ('A', 'B', 'C', 'DRB1', 'DQB1');
COMMIT;

-- CORRIGINDO O PROBLEMA DE INCLUSÃO DE NOVA COLUNA NA TABELA DE AUDITORIA
ALTER TABLE MODRED.DOADOR_AUD
ADD DOAD_TX_FASE VARCHAR2(2);


INSERT INTO "MODRED"."TIPO_BUSCA_CHECKLIST" (TIBC_ID, TIBC_TX_DESCRICAO, TIBC_NR_AGE_DIAS) VALUES ('1', 'NOVA BUSCA', '2');
INSERT INTO "MODRED"."TIPO_BUSCA_CHECKLIST" (TIBC_ID, TIBC_TX_DESCRICAO, TIBC_NR_AGE_DIAS) VALUES ('2', 'ALTEROU GENOTIPO', '3');
INSERT INTO "MODRED"."TIPO_BUSCA_CHECKLIST" (TIBC_ID, TIBC_TX_DESCRICAO, TIBC_NR_AGE_DIAS) VALUES ('3', 'RESULTADO EXAME SEGUNDA FASE INTERNACIONAL', '3');
INSERT INTO "MODRED"."TIPO_BUSCA_CHECKLIST" (TIBC_ID, TIBC_TX_DESCRICAO, TIBC_NR_AGE_DIAS) VALUES ('4', 'RESULTADO EXAME CT INTERNACIONAL', '3');
commit;


UPDATE RECURSO SET recu_tx_sigla = 'CANCELAR_FASE_2_INTERNACIONAL'
WHERE recu_tx_sigla = 'CANCELAR_PEDIDO_EXAME';
COMMIT;

INSERT INTO MODRED.MOTIVO_CANCELAMENTO_COLETA (MOCC_TX_CODIGO, MOCC_DESCRICAO) VALUES ('PRESCRICAO_CANCELADA', 'Ocorre quando a prescrição é cancelada na avaliação da mesma.');
COMMIT;



-- RENOMEANDO COLUNA COM O PADRÃO
ALTER TABLE MODRED.BUSCA_CHECKLIST
RENAME COLUMN BUCH_AGE TO BUCH_NR_AGE;

-- ATUALIZANDO PRAZO DE REFERÊNCIA NA TABELA DE BUSCA CHECKLIST, DE ACORDO COM O TIPO.
UPDATE MODRED.BUSCA_CHECKLIST BCHECK
SET BCHECK.BUCH_AGE = (SELECT TIBC_NR_AGE_DIAS FROM MODRED.TIPO_BUSCA_CHECKLIST WHERE TIBC_ID = BCHECK.TIBC_ID);
COMMIT;


INSERT INTO "MODRED"."PERMISSAO" (RECU_ID, PERF_ID, PERM_IN_COM_RESTRICAO) VALUES ('86', '18', '0');
INSERT INTO "MODRED"."TIPO_BUSCA_CHECKLIST" (TIBC_ID, TIBC_TX_DESCRICAO, TIBC_NR_AGE_DIAS) VALUES ('5', 'RESULTADO EXAME CT PACIENTE', '3');
COMMIT;

-- REMOVE TODOS OS RELACIONAMENTOS ENTRE ANALISTAS E CENTROS AVALIADORES.
DELETE FROM MODRED.USUARIO_CENTRO_TRANSPLANTE;
COMMIT;
-- IMPORTANTE!!! DEVEM SER ASSOCIADOS AO ANALISTA DE BUSCA OS CENTROS AVALIADORES DOS PACIENTES, PARA QUE APAREÇAM NA TELA PACIENTES EM BUSCA.

-- INCLUSÃO DE NOVO USUÁRIO ANALISTA DE BUSCA PARA TESTAR A FUNCIONALIDADE
INSERT INTO "MODRED"."USUARIO" (USUA_ID, USUA_TX_NOME, USUA_TX_USERNAME, USUA_TX_PASSWORD, USUA_IN_ATIVO, USUA_TX_EMAIL) 
VALUES ('99', 'ANALISTA BUSCA 2', 'ANALISTA_BUSCA2', '$2a$11$KMAfznbkZhx9mvESGy3.GewPWbSGQLVTnj5O0m7cIo5NrucDtcXT.', '1', 'analista_busca@mail.com');

INSERT INTO "MODRED"."USUARIO_PERFIL" (USUA_ID, PERF_ID) VALUES ('99', '5');

INSERT INTO MODRED.USUARIO_CENTRO_TRANSPLANTE
VALUES(99, 37);
commit;


INSERT INTO "MODRED"."TIPO_BUSCA_CHECKLIST" (TIBC_ID, TIBC_TX_DESCRICAO, TIBC_NR_AGE_DIAS) VALUES ('6', 'Diálogo do Médico', '2');
commit;


CREATE TABLE MODRED.ESTABELECIMENTO_IN_REDOME 
(
  ESIR_ID NUMBER NOT NULL
, ID_ESTABELECIMENTO NUMBER NOT NULL 
, ESIR_NR_TIPO_ENTIDADE NUMBER NOT NULL 
, ESIR_DT_INCLUSAO TIMESTAMP(6) NOT NULL
, ESIR_IN_STATUS_OPERACAO VARCHAR2(1 BYTE) NOT NULL 
, ESIR_TX_DESCRICAO_ERRO VARCHAR2(255 BYTE) 
, ESIR_TX_REGISTRO CLOB NOT NULL
, ESIR_DT_PROCESSAMENTO TIMESTAMP(6) 
, CONSTRAINT PK_ESIR PRIMARY KEY 
  (
    ESIR_ID 
  )
  USING INDEX 
  (
      CREATE UNIQUE INDEX MODRED.IN_PK_ESIR ON MODRED.ESTABELECIMENTO_IN_REDOME (ESIR_ID ASC)  
  )
  ENABLE 
);

COMMENT ON TABLE MODRED.ESTABELECIMENTO_IN_REDOME IS 'Guarda os dados migrados do redome para o modred em formato JSON dos estabelecimentos (HemoEntidade, Laboratorio)';

COMMENT ON COLUMN MODRED.ESTABELECIMENTO_IN_REDOME.ESIR_ID IS 'Identificador único do tabela.';

COMMENT ON COLUMN MODRED.ESTABELECIMENTO_IN_REDOME.ID_ESTABELECIMENTO IS 'Identificador do estabelecimento no REDOMEWEB a ser processado.';

COMMENT ON COLUMN MODRED.ESTABELECIMENTO_IN_REDOME.ESIR_NR_TIPO_ENTIDADE IS 'Tipo de entidade a ser processada 0 - HemoEntidade ou 1 - Laboratorio';

COMMENT ON COLUMN MODRED.ESTABELECIMENTO_IN_REDOME.ESIR_DT_INCLUSAO IS 'Data da inclusão do doador';

COMMENT ON COLUMN MODRED.ESTABELECIMENTO_IN_REDOME.ESIR_IN_STATUS_OPERACAO IS 'Status da operação. indica o que já foi processado ao registro P- A processar, T- Transferido';

COMMENT ON COLUMN MODRED.ESTABELECIMENTO_IN_REDOME.ESIR_TX_DESCRICAO_ERRO IS 'Texto livre para erros';

COMMENT ON COLUMN MODRED.ESTABELECIMENTO_IN_REDOME.ESIR_TX_REGISTRO IS 'Json com a estrutura das entidades (HemoEntidade ou Laboratorio) a ser importado';

COMMENT ON COLUMN MODRED.ESTABELECIMENTO_IN_REDOME.ESIR_DT_PROCESSAMENTO IS 'Data de processamento do registro';


CREATE SEQUENCE MODRED.SQ_ESIR_ID INCREMENT BY 1 MAXVALUE 9999999999999999999999999999 MINVALUE 1 CACHE 20 ORDER;


ALTER TABLE MODRED.HEMO_ENTIDADE 
ADD (HEEN_ID_HEMOCENTRO NUMBER );

CREATE INDEX MODRED.IN_FK_HEEN_HEEN ON MODRED.HEMO_ENTIDADE (HEEN_ID_HEMOCENTRO);

ALTER TABLE MODRED.HEMO_ENTIDADE
ADD CONSTRAINT FK_HEEN_HEEN FOREIGN KEY
(
  HEEN_ID_HEMOCENTRO 
)
REFERENCES MODRED.HEMO_ENTIDADE
(
  HEEN_ID 
)
ENABLE;

COMMENT ON COLUMN MODRED.HEMO_ENTIDADE.HEEN_ID_HEMOCENTRO IS 'Referência circular para o obter o hemocentro (pai) do hemonúcleo.';

CREATE SEQUENCE MODRED.SQ_LABO_ID INCREMENT BY 1 MAXVALUE 9999999999999999999999999999 MINVALUE 1 CACHE 20 ORDER;


ALTER TABLE MODRED.LABORATORIO  
MODIFY (LABO_TX_NOME VARCHAR2(255 BYTE) );

ALTER TABLE MODRED.LABORATORIO  
MODIFY (LABO_TX_NOME_CONTATO VARCHAR2(100 BYTE) );



INSERT INTO "MODRED"."TIPO_TAREFA" (TITA_ID, TITA_TX_DESCRICAO, TITA_IN_AUTOMATICA, TITA_NR_TEMPO_EXECUCAO, TARE_IN_SOMENTE_AGRUPADOR) VALUES ('88', 'CHECKLIST_BUSCA_EXAME_FOLLOWUP_30D', '1', '2592000', '0');
commit;



INSERT INTO "MODRED"."TIPO_BUSCA_CHECKLIST" (TIBC_ID, TIBC_TX_DESCRICAO, TIBC_NR_AGE_DIAS) VALUES ('7', 'EXAME SEM RESULTADO A MAIS DE 30 DIAS', '30');
commit;



ALTER TABLE BUSCA_CHECKLIST 
ADD (MATC_ID NUMBER );

CREATE INDEX IN_PK_BUCH_MATC ON BUSCA_CHECKLIST (MATC_ID ASC);

ALTER TABLE BUSCA_CHECKLIST
ADD CONSTRAINT FK_BUCH_MATC FOREIGN KEY
(
  MATC_ID 
)
REFERENCES MATCH
(
  MATC_ID 
)
ENABLE;

COMMENT ON COLUMN BUSCA_CHECKLIST.MATC_ID IS 'Referencia de match ';



INSERT INTO "MODRED"."TIPO_TAREFA" (TITA_ID, TITA_TX_DESCRICAO, TITA_IN_AUTOMATICA, TITA_NR_TEMPO_EXECUCAO, TARE_IN_SOMENTE_AGRUPADOR) VALUES ('89', 'CHECKLIST_MATCH_EXAME_FOLLOUP_30D', '1', '2592000', '0');
COMMIT;


-- ATUALIZANDO OS TEXTOS DOS TIPOS DE BUSCA CHECKLIST. 
UPDATE MODRED.TIPO_BUSCA_CHECKLIST
SET TIBC_TX_DESCRICAO = 'Exame CT Internacional'
WHERE TIBC_ID = 4;

UPDATE MODRED.TIPO_BUSCA_CHECKLIST
SET TIBC_TX_DESCRICAO = 'Exame F2 Internacional'
WHERE TIBC_ID = 3;

UPDATE MODRED.TIPO_BUSCA_CHECKLIST
SET TIBC_TX_DESCRICAO = 'Exame CT do Paciente'
WHERE TIBC_ID = 5;

UPDATE MODRED.TIPO_BUSCA_CHECKLIST
SET TIBC_TX_DESCRICAO = 'Exame Sem Resultado - 30 dias'
WHERE TIBC_ID = 7;

UPDATE MODRED.TIPO_BUSCA_CHECKLIST
SET TIBC_TX_DESCRICAO = 'Genótipo Atualizado'
WHERE TIBC_ID = 2;

UPDATE MODRED.TIPO_BUSCA_CHECKLIST
SET TIBC_TX_DESCRICAO = 'Nova Busca'
WHERE TIBC_ID = 1;

UPDATE MODRED.TIPO_BUSCA_CHECKLIST
SET TIBC_TX_DESCRICAO = 'Diálogo do Médico'
WHERE TIBC_ID = 6;
COMMIT;


INSERT INTO MODRED.TIPO_EXAME (TIEX_ID, TIEX_TX_DESCRICAO) VALUES (3, 'Locus C');
INSERT INTO MODRED.TIPO_EXAME_LOCUS (LOCU_ID, TIEX_ID) VALUES ('C', 3);
INSERT INTO MODRED.TIPO_EXAME (TIEX_ID, TIEX_TX_DESCRICAO) VALUES (4, 'Alta Resolução Classe II');
INSERT INTO MODRED.TIPO_EXAME_LOCUS (LOCU_ID, TIEX_ID) VALUES ('DRB1', 4);
INSERT INTO MODRED.TIPO_EXAME_LOCUS (LOCU_ID, TIEX_ID) VALUES ('DQB1', 4);
COMMIT;

    
ALTER TABLE MODRED.SOLICITACAO 
ADD (TIEX_ID NUMBER );

CREATE INDEX MODRED.IN_FK_SOLI_TIEX ON MODRED.SOLICITACAO (TIEX_ID);

ALTER TABLE MODRED.SOLICITACAO
ADD CONSTRAINT FK_SOLI_TIEX FOREIGN KEY
(
  TIEX_ID 
)
REFERENCES MODRED.TIPO_EXAME
(
  TIEX_ID 
)
ENABLE;

COMMENT ON COLUMN MODRED.SOLICITACAO.TIEX_ID IS 'Chave estrangeira que guarda o tipo de exame solicitado para doador nacional.';

ALTER TABLE MODRED.SOLICITACAO 
ADD (LABO_ID NUMBER );

CREATE INDEX MODRED.IN_FK_SOLI_LABO ON MODRED.SOLICITACAO (LABO_ID);

ALTER TABLE MODRED.SOLICITACAO
ADD CONSTRAINT FK_SOLI_LABO FOREIGN KEY
(
  LABO_ID 
)
REFERENCES MODRED.LABORATORIO
(
  LABO_ID 
)
ENABLE;

COMMENT ON COLUMN MODRED.SOLICITACAO.LABO_ID IS 'Chave estrangeira que guarda o laboratório para doador nacional.';

-- ASSOCIADO ANALISTA DE BUSCA A TODOS OS CENTRO AVALIADORES DISPONÍVEIS NO MOMENTO.
INSERT INTO MODRED.USUARIO_CENTRO_TRANSPLANTE
VALUES(5, 2);
INSERT INTO MODRED.USUARIO_CENTRO_TRANSPLANTE
VALUES(5, 37);
INSERT INTO MODRED.USUARIO_CENTRO_TRANSPLANTE
VALUES(5, 39);
INSERT INTO MODRED.USUARIO_CENTRO_TRANSPLANTE
VALUES(5, 40);
COMMIT;

-- RECURSO PARA DAR VISTO NO CHECKLIST DE BUSCA
INSERT INTO "MODRED"."RECURSO" (RECU_ID, RECU_TX_SIGLA, RECU_TX_DESCRICAO) VALUES ('191', 'VISTAR_CHECKLIST', 'Possibilita dar visto nos itens de checklist de busca.');
INSERT INTO "MODRED"."PERMISSAO" (RECU_ID, PERF_ID, PERM_IN_COM_RESTRICAO) VALUES ('191', '5', '0');
COMMIT;



INSERT INTO MODRED.RECURSO (RECU_ID, RECU_TX_SIGLA, RECU_TX_DESCRICAO) VALUES 
(192, 'EDITAR_FASE2_INTERNACIONAL', 'Permite ao analista de busca editar o pedido de exame de fase 2 internacional.');
INSERT INTO MODRED.PERMISSAO (RECU_ID, PERF_ID, PERM_IN_COM_RESTRICAO) VALUES
(192, 5, 0);
COMMIT;

INSERT INTO MODRED.RECURSO (RECU_ID, RECU_TX_SIGLA, RECU_TX_DESCRICAO) VALUES 
(193, 'EXIBIR_HISTORICO_BUSCA', 'Permite ao analista de busca visualizar os matchs inativos da busca.');
INSERT INTO MODRED.PERMISSAO (RECU_ID, PERF_ID, PERM_IN_COM_RESTRICAO) VALUES
(193, 5, 0);
INSERT INTO MODRED.PERMISSAO (RECU_ID, PERF_ID, PERM_IN_COM_RESTRICAO) VALUES
(193, 1, 0);
COMMIT;

-- ATUALIZANDO O TEXTO DO TIPO DE BUSCA CHECKLIST.
UPDATE MODRED.TIPO_BUSCA_CHECKLIST  SET  TIBC_TX_DESCRICAO = 'Resultado de Exame'
WHERE TIBC_ID = 3;
COMMIT;

UPDATE MODRED.BUSCA_CHECKLIST SET TIBC_ID = 3 WHERE TIBC_ID IN (4,5);
COMMIT;

--REMOVENDO OS TIPOS DE EXAME 
DELETE FROM MODRED.TIPO_BUSCA_CHECKLIST WHERE TIBC_ID IN (4,5);
COMMIT;


COMMIT;

-- INCLUINDO UMA TAREFA AGRUPADORA DE PEDIDOS DE EXAME PARA DOADOR NACIONAL.
INSERT INTO "MODRED"."TIPO_TAREFA" (TITA_ID, TITA_TX_DESCRICAO, TITA_IN_AUTOMATICA, TARE_IN_SOMENTE_AGRUPADOR) 
VALUES ('90', 'RESULTADO_EXAME_NACIONAL', '0', '1');
COMMIT;

-- ADICIONANDO CAMPO NA TABELA DE AUDITORIA
ALTER TABLE MODRED.DOADOR_AUD
ADD (HEEN_ID NUMBER);

-- INCLUINDO UMA TAREFA AGRUPADORA DE CONTATO DOADOR NACIONAL.
INSERT INTO "MODRED"."TIPO_TAREFA" (TITA_ID, TITA_TX_DESCRICAO, TITA_IN_AUTOMATICA, TARE_IN_SOMENTE_AGRUPADOR) 
VALUES ('91', 'CONTATO_DOADOR', '0', '1');
COMMIT;

-- INCLUINDO UMA TAREFA AGRUPADORA DE ENRIQUECIMENTO DOADOR NACIONAL.
INSERT INTO "MODRED"."TIPO_TAREFA" (TITA_ID, TITA_TX_DESCRICAO, TITA_IN_AUTOMATICA, TARE_IN_SOMENTE_AGRUPADOR) 
VALUES ('92', 'ENRIQUECER_DOADOR', '0', '1');
COMMIT;

-- FORÇANDO AS SOLICITAÇÃO DE FASE 2 RECEBENDO OS LABORATÓRIOS INFORMADOS PARA O DOADOR.
MERGE INTO MODRED.SOLICITACAO SOL1
USING ( SELECT S.SOLI_ID, M.MATC_ID, D.LABO_ID
        FROM MODRED.SOLICITACAO S JOIN MODRED.MATCH M ON(M.MATC_ID = S.MATC_ID) 
        JOIN MODRED.DOADOR D ON (M.DOAD_ID = D.DOAD_ID)
        WHERE TISO_ID = 1) SOL2 ON (SOL1.SOLI_ID = SOL2.SOLI_ID)
WHEN MATCHED THEN UPDATE SET SOL1.LABO_ID = SOL2.LABO_ID;
COMMIT;

-- RECRIANDO A CONSTRAINT DA PK DA TABELA DE USUARIO_BANCO_SANGUE_CORDAO
ALTER TABLE MODRED.USUARIO_BANCO_SANGUE_CORDAO
DROP CONSTRAINT PK_UBSC_ID;
ALTER TABLE MODRED.USUARIO_BANCO_SANGUE_CORDAO
ADD CONSTRAINT PK_UBSC PRIMARY KEY (USUA_ID, BASC_ID) ENABLE;


set define off;
INSERT INTO "MODRED"."RELATORIO" (RELA_ID, RELA_TX_CODIGO, RELA_IN_TIPO, RELA_TX_HTML) VALUES (SQ_RELA_ID.nextval, 'EMAIL_ENVIO_SENHA', '1', '<p>&amp;LOGO_REDOME&amp;</p>

<p>&nbsp;</p>

<p>&amp;NOME_USUARIO&amp;</p>

<p>&amp;LOGIN_USUARIO&amp;</p>

<p>&amp;SENHA_USUARIO&amp;</p>
');

INSERT INTO "MODRED"."RELATORIO" (RELA_ID, RELA_TX_CODIGO, RELA_IN_TIPO, RELA_TX_HTML) VALUES (SQ_RELA_ID.nextval, 'LEMBRAR_SENHA', '1', '<p>&amp;LOGO_REDOME&amp;</p>

<p>&nbsp;</p>

<p>&amp;NOME_USUARIO&amp;</p>

<p>&amp;LOGIN_USUARIO&amp;</p>

<p>&amp;SENHA_USUARIO&amp;</p>
');
commit;
set define on;


-- ATUALIZANDO TAMBÉM AS SOLICITAÇÕES DE FASE 3 (LABORATÓRIOS).
MERGE INTO MODRED.SOLICITACAO SOL1
USING ( SELECT S.SOLI_ID, M.MATC_ID, D.LABO_ID
        FROM MODRED.SOLICITACAO S JOIN MODRED.MATCH M ON(M.MATC_ID = S.MATC_ID) 
        JOIN MODRED.DOADOR D ON (M.DOAD_ID = D.DOAD_ID)
        WHERE TISO_ID IN (1, 2)) SOL2 ON (SOL1.SOLI_ID = SOL2.SOLI_ID)
WHEN MATCHED THEN UPDATE SET SOL1.LABO_ID = SOL2.LABO_ID;
COMMIT;

-- ATUALIZANDO TIPO DE EXAME PARA AS SOLICITAÇÕES DE FASE 2 E 3, NACIONAL.
UPDATE MODRED.SOLICITACAO
SET TIEX_ID = 2
WHERE TISO_ID = 1;

UPDATE MODRED.SOLICITACAO
SET TIEX_ID = 1
WHERE TISO_ID = 2;
COMMIT;

-- TIPO DE TAREFA AGRUPADORA DE RESULTADOS DE EXAME INTERNACIONAL (FASE 2 E CT)
INSERT INTO "MODRED"."TIPO_TAREFA" (TITA_ID, TITA_TX_DESCRICAO, TITA_IN_AUTOMATICA, TARE_IN_SOMENTE_AGRUPADOR) 
VALUES ('93', 'RESULTADO_EXAME_INTERNACIONAL', '0', '1');
COMMIT;

UPDATE MODRED.RECURSO SET RECU_TX_SIGLA = 'EXIBIR_HISTORICO_MATCH'
WHERE RECU_ID = 193;
COMMIT;

-- ATUALIZANDO O TEXTO DO TIPO DE BUSCA CHECKLIST.
UPDATE MODRED.TIPO_BUSCA_CHECKLIST SET TIBC_TX_DESCRICAO = 'Resultado de Exame Segunda Fase'
WHERE TIBC_ID = 3;
COMMIT;

-- INCLUINDO UM TIPO DE BUSCA CHECKLIST PARA CT
INSERT INTO MODRED.TIPO_BUSCA_CHECKLIST (TIBC_ID, TIBC_TX_DESCRICAO, TIBC_NR_AGE_DIAS) VALUES ('4', 'Resultado de Exame CT', '3');
COMMIT;



INSERT INTO "MODRED"."PERMISSAO" (RECU_ID, PERF_ID, PERM_IN_COM_RESTRICAO) VALUES ('22', '1', '0');
commit;


ALTER TABLE modred.doador ADD (doad_tx_grid VARCHAR2(12));
COMMENT ON COLUMN modred.doador.doad_tx_grid IS 'Identificador únido do doador no registro internacional.';
UPDATE modred.doador SET doad_tx_grid = doad_nr_grid;
ALTER TABLE modred.doador DROP COLUMN doad_nr_grid;

ALTER TABLE MODRED.DOADOR_AUD DROP COLUMN DOAD_NR_GRID;
ALTER TABLE MODRED.DOADOR_AUD ADD (DOAD_TX_GRID VARCHAR2(12) );
COMMENT ON COLUMN MODRED.DOADOR_AUD.DOAD_TX_GRID IS 'Identificador únido do doador no registro internacional.';

COMMIT;