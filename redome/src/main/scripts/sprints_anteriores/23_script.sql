ALTER TABLE MODRED.QUALIFICACAO_MATCH 
ADD (DOAD_NR_DMR NUMBER );

ALTER TABLE MODRED.QUALIFICACAO_MATCH 
ADD (MATC_TX_GENOTIPO VARCHAR2(40) );

CREATE INDEX IN_FK_QUMA_DOAD ON MODRED.QUALIFICACAO_MATCH (QUMA_TX_QUALIFICACAO);

ALTER TABLE MODRED.QUALIFICACAO_MATCH
ADD CONSTRAINT FK_QUMA_DOAD FOREIGN KEY
(
  DOAD_NR_DMR 
)
REFERENCES MODRED.DOADOR
(
  DOAD_NR_DMR 
)
ENABLE;

COMMENT ON COLUMN MODRED.QUALIFICACAO_MATCH.DOAD_NR_DMR IS 'Chave estrangeira para tabela de doador';

COMMENT ON COLUMN MODRED.QUALIFICACAO_MATCH.MATC_TX_GENOTIPO IS 'Genótipo comparado entre o paciente e o doador.';

ALTER TABLE QUALIFICACAO_MATCH RENAME COLUMN MATC_TX_GENOTIPO TO QUMA_TX_GENOTIPO;

INSERT INTO "MODRED"."CONFIGURACAO" (CONF_ID, CONF_TX_VALOR) VALUES ('quantidadeFavoritosAvaliacaoMatch', '10');


ALTER TABLE MODRED.GENOTIPO 
ADD (GENO_IN_EXCLUIDO NUMBER(1) DEFAULT 0 NOT NULL);

COMMENT ON COLUMN MODRED.GENOTIPO.GENO_IN_EXCLUIDO IS 'Se registro foi excluido';

ALTER TABLE MODRED.BUSCA_AUD 
ADD (PACI_IN_ACEITA_MISMATCH NUMBER(1) );


ALTER TABLE MODRED.VALOR_GENOTIPO_BUSCA  
MODIFY (VAGB_TX_VALOR VARCHAR2(10 BYTE) );


-- ESTORIA 3 - INICIO

-- RECURSOS INCLUÍDOS
-- Recurso para acesso ao Histórico de Busca
INSERT INTO MODRED.RECURSO (RECU_ID, RECU_TX_SIGLA, RECU_TX_DESCRICAO) 
VALUES (62, 'VISUALIZAR_HISTORICO_BUSCA', 'Permite visualizar o histórico de busca (evoluções da busca) de um paciente');

CREATE INDEX IN_FK_QUMA_DOAD ON MODRED.QUALIFICACAO_MATCH (QUMA_TX_QUALIFICACAO);
INSERT INTO MODRED.PERMISSAO (RECU_ID, PERF_ID, PERM_IN_COM_RESTRICAO) VALUES (62, 5, 0);
-- RECURSOS

-- TORNANDO A COLUNA MATC_ID DENTRO DA EVOLUCAO_BUSCA PARA NULA
ALTER TABLE MODRED.EVOLUCAO_BUSCA
MODIFY (MATC_ID NULL);

COMMIT;

-- INCLUÍNDO A RELAÇÃO ENTRE PEDIDO_EXAME E SOLICITACAO E REMOVENDO A RELAÇÃO DIRETA COM DOADOR.
ALTER TABLE MODRED.PEDIDO_EXAME
ADD SOLI_ID NUMBER;

UPDATE MODRED.PEDIDO_EXAME P SET P.SOLI_ID = (
SELECT DISTINCT SOL.SOLI_ID
FROM MODRED.PEDIDO_EXAME PE JOIN MODRED.DOADOR DOAD ON(PE.DOAD_NR_DMR = DOAD.DOAD_NR_DMR)
JOIN MODRED.SOLICITACAO SOL ON(SOL.DOAD_NR_DMR = DOAD.DOAD_NR_DMR)
WHERE DOAD.DOAD_NR_DMR = P.DOAD_NR_DMR);

DELETE FROM MODRED.PEDIDO_EXAME
WHERE SOLI_ID IS NULL;

ALTER TABLE MODRED.PEDIDO_EXAME
MODIFY SOLI_ID NUMBER NOT NULL;

ALTER TABLE MODRED.PEDIDO_EXAME
ADD CONSTRAINT FK_PEEX_SOLI FOREIGN KEY(SOLI_ID)
REFERENCES MODRED.SOLICITACAO(SOLI_ID)
ENABLE;

CREATE INDEX IN_FK_PEEX_SOLI ON MODRED.PEDIDO_EXAME (SOLI_ID ASC);

ALTER TABLE MODRED.PEDIDO_EXAME
DROP COLUMN DOAD_NR_DMR;
-- ESTORIA 3 - FINAL

-- ESTORIA 5 - INICIO
ALTER TABLE MODRED.PRESCRICAO 
ADD (PRES_VL_QUANTIDADE_TCN_CD34 NUMBER(6, 2),
     PRES_ID NUMBER);

COMMENT ON COLUMN MODRED.PRESCRICAO.PRES_VL_QUANTIDADE_TCN_CD34 IS 'Quantidade de TCN ou CD34 para o cálculo (quantidade / peso do paciente).';
COMMENT ON COLUMN MODRED.PRESCRICAO.PRES_ID IS 'Identificador da prescrição.';

ALTER TABLE MODRED.PRESCRICAO  
MODIFY (SOLI_ID NULL);

CREATE SEQUENCE  MODRED.SQ_PRES_ID  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;

UPDATE MODRED.PRESCRICAO SET PRES_ID = SQ_PRES_ID.NEXTVAL;
COMMIT;

ALTER TABLE MODRED.PRESCRICAO 
DROP CONSTRAINT PK_PRES;

ALTER TABLE MODRED.PRESCRICAO  
MODIFY (PRES_ID NOT NULL);

CREATE INDEX MODRED.IN_FK_PRES_SOLI ON MODRED.PRESCRICAO (SOLI_ID);

ALTER TABLE MODRED.PRESCRICAO
ADD CONSTRAINT PK_PRES PRIMARY KEY 
(
  PRES_ID 
)
USING INDEX 
(
    CREATE UNIQUE INDEX MODRED.PK_PRES ON PRESCRICAO (PRES_ID ASC) 
)
ENABLE;

CREATE TABLE MODRED.ARQUIVO_PRESCRICAO 
(
  ARPR_ID NUMBER NOT NULL 
, ARPR_TX_CAMINHO VARCHAR2(263) NOT NULL 
, PRES_ID NUMBER NOT NULL 
, CONSTRAINT PK_ARPR PRIMARY KEY 
  (
    ARPR_ID 
  )
  USING INDEX 
  (
      CREATE UNIQUE INDEX PK_ARPR ON ARQUIVO_PRESCRICAO (ARPR_ID ASC) 
  )
  ENABLE 
);

CREATE INDEX MODRED.IN_FK_ARPR_PRES ON MODRED.ARQUIVO_PRESCRICAO (PRES_ID);

ALTER TABLE MODRED.ARQUIVO_PRESCRICAO
ADD CONSTRAINT FK_ARPR_PRES FOREIGN KEY
(
  PRES_ID 
)
REFERENCES MODRED.PRESCRICAO
(
  PRES_ID 
)
ENABLE;

COMMENT ON COLUMN MODRED.ARQUIVO_PRESCRICAO.ARPR_ID IS 'Identificador do arquivo de prescricao.';
COMMENT ON COLUMN MODRED.ARQUIVO_PRESCRICAO.ARPR_TX_CAMINHO IS 'Caminho do arquivo no storage.';
COMMENT ON COLUMN MODRED.ARQUIVO_PRESCRICAO.PRES_ID IS 'Identificador da prescrição.';

CREATE SEQUENCE  MODRED.SQ_ARPR_ID  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;


INSERT INTO MODRED.CONFIGURACAO (CONF_ID, CONF_TX_VALOR) VALUES ('quantidadeMaximaArquivosPrescricao', '3');
INSERT INTO MODRED.CONFIGURACAO (CONF_ID, CONF_TX_VALOR) VALUES ('tamanhoArquivoPrescricaoEmByte', '5242880');
INSERT INTO MODRED.CONFIGURACAO (CONF_ID, CONF_TX_VALOR) VALUES ('extensaoArquivoPrescricao', 'application/pdf');
COMMIT;

INSERT INTO MODRED.RECURSO (RECU_ID, RECU_TX_SIGLA, RECU_TX_DESCRICAO) VALUES (63, 'VISUALIZAR_FAVORITO_MATCH', 'Permite favoritar um doador para a comparação do genótipo com o paciente.');
INSERT INTO MODRED.RECURSO (RECU_ID, RECU_TX_SIGLA, RECU_TX_DESCRICAO) VALUES (64, 'VISUALIZAR_RESSALVA_MATCH', 'Permite visualizar as ressalvas de um doador com match de um paciente.');
INSERT INTO MODRED.RECURSO (RECU_ID, RECU_TX_SIGLA, RECU_TX_DESCRICAO) VALUES (65, 'VISUALIZAR_COMENTARIO_MATCH', 'Permite visualizar os comentários do match de um doador com um paciente.');
INSERT INTO MODRED.RECURSO (RECU_ID, RECU_TX_SIGLA, RECU_TX_DESCRICAO) VALUES (66, 'PROGREDIR_FASE_2', 'Permite ao analista de busca solicitar fase 2 para um doador.');
INSERT INTO MODRED.RECURSO (RECU_ID, RECU_TX_SIGLA, RECU_TX_DESCRICAO) VALUES (67, 'PROGREDIR_FASE_3', 'Permite ao analista de busca solicitar fase 3 para um doador.');
INSERT INTO MODRED.RECURSO (RECU_ID, RECU_TX_SIGLA, RECU_TX_DESCRICAO) VALUES (68, 'PROGREDIR_DISPONIBILIZAR', 'Permite ao analista de busca disponibilizar o doador para o médico fazer a prescrição.');
INSERT INTO MODRED.RECURSO (RECU_ID, RECU_TX_SIGLA, RECU_TX_DESCRICAO) VALUES (69, 'CANCELAR_FASE_2', 'Permite ao analista de busca cancelar uma solicitar fase 2 para um doador.');
INSERT INTO MODRED.RECURSO (RECU_ID, RECU_TX_SIGLA, RECU_TX_DESCRICAO) VALUES (70, 'CANCELAR_FASE_3', 'Permite ao analista de busca cancelar uma solicitar fase 3 para um doador.');
INSERT INTO MODRED.RECURSO (RECU_ID, RECU_TX_SIGLA, RECU_TX_DESCRICAO) VALUES (71, 'CADASTRAR_PRESCRICAO', 'Permite ao médico responsável pelo paciente cadastrar uma prescrição.');
INSERT INTO MODRED.RECURSO (RECU_ID, RECU_TX_SIGLA, RECU_TX_DESCRICAO) VALUES (72, 'VISUALIZAR_OUTROS_PROCESSOS', 'Permite ao analista de busca visualizar outros processos de busca para um doador.');

DELETE FROM MODRED.PERMISSAO WHERE RECU_ID = 58 AND PERF_ID = 5;
UPDATE MODRED.RECURSO SET RECU_TX_SIGLA = 'VAGO - UTILIZAR ESSE CÓDIGO' WHERE RECU_ID = 58;

INSERT INTO MODRED.PERMISSAO (RECU_ID, PERF_ID, PERM_IN_COM_RESTRICAO) VALUES (63, 5, 0);
INSERT INTO MODRED.PERMISSAO (RECU_ID, PERF_ID, PERM_IN_COM_RESTRICAO) VALUES (64, 5, 0);
INSERT INTO MODRED.PERMISSAO (RECU_ID, PERF_ID, PERM_IN_COM_RESTRICAO) VALUES (65, 5, 0);
INSERT INTO MODRED.PERMISSAO (RECU_ID, PERF_ID, PERM_IN_COM_RESTRICAO) VALUES (66, 5, 0);
INSERT INTO MODRED.PERMISSAO (RECU_ID, PERF_ID, PERM_IN_COM_RESTRICAO) VALUES (67, 5, 0);
INSERT INTO MODRED.PERMISSAO (RECU_ID, PERF_ID, PERM_IN_COM_RESTRICAO) VALUES (68, 5, 0);
INSERT INTO MODRED.PERMISSAO (RECU_ID, PERF_ID, PERM_IN_COM_RESTRICAO) VALUES (69, 5, 0);
INSERT INTO MODRED.PERMISSAO (RECU_ID, PERF_ID, PERM_IN_COM_RESTRICAO) VALUES (70, 5, 0);
INSERT INTO MODRED.PERMISSAO (RECU_ID, PERF_ID, PERM_IN_COM_RESTRICAO) VALUES (72, 5, 0);

INSERT INTO MODRED.PERMISSAO (RECU_ID, PERF_ID, PERM_IN_COM_RESTRICAO) VALUES (60, 1, 0);
INSERT INTO MODRED.PERMISSAO (RECU_ID, PERF_ID, PERM_IN_COM_RESTRICAO) VALUES (71, 1, 0);
COMMIT;

--FIM ESTORIA 5

-- CONCEDENDO PERMISSÃO PARA O MÉDICO PARA COMPARAR GENOTIPO
INSERT INTO MODRED.PERMISSAO (RECU_ID, PERF_ID, PERM_IN_COM_RESTRICAO) VALUES (63, 1, 0);
-- CONCEDENDO PERMISSÃO PARA MÉDICO VISUALIZAR HISTÓRICO DA BUSCA
INSERT INTO MODRED.PERMISSAO (RECU_ID, PERF_ID, PERM_IN_COM_RESTRICAO) VALUES (62, 1, 0);
COMMIT;