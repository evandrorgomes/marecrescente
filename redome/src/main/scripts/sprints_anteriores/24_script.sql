-- SPRINT 24
--Queiroz Inicio
INSERT INTO MODRED.RECURSO VALUES (73, 'AVALIAR_PRESCRICAO', 'Permite ao medico avaliar uma nova prescrição criada.');
INSERT INTO MODRED.PERMISSAO VALUES (73, 13, 0);


CREATE TABLE MODRED.AVALIACAO_PRESCRICAO 
(
  AVPR_ID NUMBER NOT NULL 
, FOCE_ID NUMBER 
, AVPR_TX_JUSTIFICATIVA_DESCARTE VARCHAR2(100) 
, AVPR_TX_JUSTIFICATIVA_CANCEL VARCHAR2(100) 
, AVPR_DT_CRICAO DATE NOT NULL 
, USUA_ID NUMBER 
, AVPR_DT_ATUALIZACAO DATE NOT NULL 
, CONSTRAINT PK_AVPR PRIMARY KEY 
  (
    AVPR_ID 
  )
  USING INDEX 
  (
      CREATE UNIQUE INDEX IN_PK_AVPR ON MODRED.AVALIACAO_PRESCRICAO (AVPR_ID ASC) 
  )
  ENABLE 
);

CREATE INDEX IN_FK_AVPR_FOCE ON MODRED.AVALIACAO_PRESCRICAO (AVPR_TX_JUSTIFICATIVA_DESCARTE);

CREATE INDEX IN_FK_AVPR_USUA ON MODRED.AVALIACAO_PRESCRICAO (FOCE_ID);

ALTER TABLE MODRED.AVALIACAO_PRESCRICAO
ADD CONSTRAINT FK_AVPR_FOCE FOREIGN KEY
(
  FOCE_ID 
)
REFERENCES MODRED.FONTE_CELULAS
(
  FOCE_ID 
)
ENABLE;

ALTER TABLE MODRED.AVALIACAO_PRESCRICAO
ADD CONSTRAINT FK_AVPR_USUA FOREIGN KEY
(
  USUA_ID 
)
REFERENCES MODRED.USUARIO
(
  USUA_ID 
)
ENABLE;

COMMENT ON TABLE MODRED.AVALIACAO_PRESCRICAO IS 'Tabela para avaliar uma prescrição.';

COMMENT ON COLUMN MODRED.AVALIACAO_PRESCRICAO.AVPR_ID IS 'Chave primária da avaliação da prescrição.';

COMMENT ON COLUMN MODRED.AVALIACAO_PRESCRICAO.FOCE_ID IS 'Fonte celula descartada.';

COMMENT ON COLUMN MODRED.AVALIACAO_PRESCRICAO.AVPR_TX_JUSTIFICATIVA_DESCARTE IS 'Justificativa  do descarte de uma fonte';

COMMENT ON COLUMN MODRED.AVALIACAO_PRESCRICAO.AVPR_TX_JUSTIFICATIVA_CANCEL IS 'Justificativa do cancelamento da prescrição';

COMMENT ON COLUMN MODRED.AVALIACAO_PRESCRICAO.AVPR_DT_CRICAO IS 'Data de criação da avaliação';

COMMENT ON COLUMN MODRED.AVALIACAO_PRESCRICAO.USUA_ID IS 'Usuario responsável pela avaliação da prescrição.';

COMMENT ON COLUMN MODRED.AVALIACAO_PRESCRICAO.AVPR_DT_ATUALIZACAO IS 'Data de atualização da avaliação';

CREATE SEQUENCE  MODRED.SQ_AVPR_ID  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;

INSERT INTO "MODRED"."TIPO_TAREFA" (TITA_ID, TITA_TX_DESCRICAO, TITA_IN_AUTOMATICA) VALUES ('49', 'AVALIACAO_PRESCRICAO', '0');





DROP INDEX MODRED.IN_FK_AVPR_FOCE;

DROP INDEX MODRED.IN_FK_AVPR_USUA;

ALTER TABLE MODRED.AVALIACAO_PRESCRICAO 
ADD (PRES_ID NUMBER NOT NULL);

CREATE INDEX IN_FK_AVPR_FOCE ON MODRED.AVALIACAO_PRESCRICAO (FOCE_ID ASC);

CREATE INDEX IN_FK_AVPR_USUA ON MODRED.AVALIACAO_PRESCRICAO (USUA_ID ASC);

CREATE INDEX IN_FK_AVPR_PRES ON MODRED.AVALIACAO_PRESCRICAO (PRES_ID);

ALTER TABLE MODRED.AVALIACAO_PRESCRICAO
ADD CONSTRAINT FK_AVPR_PRES FOREIGN KEY
(
  PRES_ID 
)
REFERENCES MODRED.PRESCRICAO
(
  PRES_ID 
)
ENABLE;

COMMENT ON COLUMN MODRED.AVALIACAO_PRESCRICAO.PRES_ID IS 'Chave estrangeira para tabela PRESCRICAO.';





ALTER TABLE MODRED.AVALIACAO_PRESCRICAO 
ADD (AVPR_IN_RESULTADO NUMBER(1) );

COMMENT ON COLUMN MODRED.AVALIACAO_PRESCRICAO.AVPR_IN_RESULTADO IS '0 - Reprovado e 1 - para aprovado';


commit;
--Queiroz Fim


-- início Estória 1
ALTER TABLE MODRED.PRESCRICAO RENAME COLUMN FOCE_ID TO FOCE_ID_OPCAO_1;

ALTER TABLE MODRED.PRESCRICAO 
RENAME COLUMN PRES_VL_QUANTIDADE_TCN_CD34 TO PRES_VL_QUANT_TOTAL_OPCAO_1;


MERGE INTO MODRED.PRESCRICAO PRESC
USING (
    SELECT PR.PRES_ID, EV.EVOL_VL_PESO FROM MODRED.PRESCRICAO PR, MODRED.SOLICITACAO S, MODRED.MATCH M, MODRED.BUSCA B, MODRED.PACIENTE P, 
        (SELECT ROWNUM, E.* FROM (
            SELECT  E.EVOL_ID, E.PACI_NR_RMR, E.EVOL_VL_PESO 
            FROM MODRED.EVOLUCAO E JOIN 
                (SELECT PACI_NR_RMR RMR, MAX(EVOL_DT_CRIACAO) MAX_DATA FROM MODRED.EVOLUCAO GROUP BY PACI_NR_RMR) 
                    MEVOL ON(MEVOL.RMR = E.PACI_NR_RMR AND MEVOL.MAX_DATA = E.EVOL_DT_CRIACAO)
        ) E) EV
    WHERE PR.SOLI_ID = S.SOLI_ID
    AND S.MATC_ID = M.MATC_ID
    AND M.BUSC_ID = B.BUSC_ID
    AND B.PACI_NR_RMR = P.PACI_NR_RMR
    and P.PACI_NR_RMR = EV.PACI_NR_RMR
    ) PRES
ON (PRESC.PRES_ID = PRES.PRES_ID)
WHEN MATCHED THEN UPDATE
SET PRESC.PRES_VL_QUANT_TOTAL_OPCAO_1 = PRES.EVOL_VL_PESO * 10;

ALTER TABLE MODRED.PRESCRICAO  
MODIFY (PRES_VL_QUANT_TOTAL_OPCAO_1 NOT NULL);


ALTER TABLE MODRED.PRESCRICAO 
ADD (FOCE_ID_OPCAO_2 NUMBER(2) );

ALTER TABLE MODRED.PRESCRICAO 
ADD (PRES_VL_QUANT_KG_OPCAO_1 NUMBER(6,2) );


UPDATE MODRED.PRESCRICAO
SET PRES_VL_QUANT_KG_OPCAO_1 = 10;
COMMIT;

ALTER TABLE MODRED.PRESCRICAO  
MODIFY (PRES_VL_QUANT_KG_OPCAO_1 NOT NULL);

ALTER TABLE MODRED.PRESCRICAO 
ADD (PRES_VL_QUANT_TOTAL_OPCAO_2 NUMBER(6,2) );

ALTER TABLE MODRED.PRESCRICAO 
ADD (PRES_VL_QUANT_KG_OPCAO_2 NUMBER(6,2) );

ALTER INDEX MODRED.IN_FK_PRES_FOCE 
RENAME TO IN_FK_PRES_FOCE_OPCAO_1;

CREATE INDEX MODRED.IN_FK_PRES_FOCE_OPCAO_2 ON MODRED.PRESCRICAO (FOCE_ID_OPCAO_2);

ALTER TABLE MODRED.PRESCRICAO
ADD CONSTRAINT FK_PRES_FOCE_OP_2 FOREIGN KEY
(
  FOCE_ID_OPCAO_2 
)
REFERENCES MODRED.FONTE_CELULAS
(
  FOCE_ID 
)
ENABLE;

ALTER TABLE MODRED.PRESCRICAO 
RENAME CONSTRAINT FK_PRES_FOCE TO FK_PRES_FOCE_OP_1;

COMMENT ON COLUMN MODRED.PRESCRICAO.FOCE_ID_OPCAO_1 IS 'Identificador do tipo de fonte de células para a opção 1.';

COMMENT ON COLUMN MODRED.PRESCRICAO.PRES_VL_QUANT_TOTAL_OPCAO_1 IS 'Quantidade total de TCN ou CD34 para a opção 1.';

COMMENT ON COLUMN MODRED.PRESCRICAO.FOCE_ID_OPCAO_2 IS 'Identificador do tipo de fonte de células para a opção 2.';

COMMENT ON COLUMN MODRED.PRESCRICAO.PRES_VL_QUANT_KG_OPCAO_1 IS 'Quantidade por kg de TCN ou CD34 para a opção 1.';

COMMENT ON COLUMN MODRED.PRESCRICAO.PRES_VL_QUANT_TOTAL_OPCAO_2 IS 'Quantidade total de TCN ou CD34 para a opção 2.';

COMMENT ON COLUMN MODRED.PRESCRICAO.PRES_VL_QUANT_KG_OPCAO_2 IS 'Quantidade por kg de TCN ou CD34 para a opção 1.';

ALTER TABLE MODRED.ARQUIVO_PRESCRICAO 
ADD (ARPR_IN_JUSTIFICATIVA NUMBER(1));

COMMENT ON COLUMN MODRED.ARQUIVO_PRESCRICAO.ARPR_IN_JUSTIFICATIVA IS 'Informa se o arquivo é de justificativa.';

UPDATE MODRED.ARQUIVO_PRESCRICAO SET ARPR_IN_JUSTIFICATIVA = 0;
COMMIT;

ALTER TABLE MODRED.ARQUIVO_PRESCRICAO 
MODIFY (ARPR_IN_JUSTIFICATIVA NOT NULL);

INSERT INTO MODRED.CONFIGURACAO (CONF_ID, CONF_TX_VALOR) VALUES ('tempoMinimoInclusaoEvolucaoEmSegundos', '86400');

INSERT INTO "MODRED"."TIPO_TAREFA" (TITA_ID, TITA_TX_DESCRICAO, TITA_IN_AUTOMATICA, TITA_NR_TEMPO_EXECUCAO) VALUES ('51', 'NOTIFICACAO_MEDICO_DESCARTE_PRESCRICAO', '1', '3600');
COMMIT;

ALTER TABLE MODRED.PRESCRICAO 
ADD (EVOL_ID NUMBER );

CREATE INDEX IN_FK_PRES_EVOL ON MODRED.PRESCRICAO (EVOL_ID);

ALTER TABLE MODRED.PRESCRICAO
ADD CONSTRAINT FK_PRES_EVOL FOREIGN KEY
(
  EVOL_ID 
)
REFERENCES MODRED.EVOLUCAO
(
  EVOL_ID 
)
ENABLE;

COMMENT ON COLUMN MODRED.PRESCRICAO.EVOL_ID IS 'Identificador da Evolução.';


MERGE INTO MODRED.PRESCRICAO PRESC
USING (
    SELECT PR.PRES_ID, EV.EVOL_ID FROM MODRED.PRESCRICAO PR, MODRED.SOLICITACAO S, MODRED.MATCH M, MODRED.BUSCA B, MODRED.PACIENTE P, 
        (SELECT ROWNUM, E.* FROM (
            SELECT  E.EVOL_ID, E.PACI_NR_RMR, E.EVOL_VL_PESO 
            FROM MODRED.EVOLUCAO E JOIN 
                (SELECT PACI_NR_RMR RMR, MAX(EVOL_DT_CRIACAO) MAX_DATA FROM MODRED.EVOLUCAO GROUP BY PACI_NR_RMR) 
                    MEVOL ON(MEVOL.RMR = E.PACI_NR_RMR AND MEVOL.MAX_DATA = E.EVOL_DT_CRIACAO)
        ) E) EV
    WHERE PR.SOLI_ID = S.SOLI_ID
    AND S.MATC_ID = M.MATC_ID
    AND M.BUSC_ID = B.BUSC_ID
    AND B.PACI_NR_RMR = P.PACI_NR_RMR
    and P.PACI_NR_RMR = EV.PACI_NR_RMR
    ) PRES
ON (PRESC.PRES_ID = PRES.PRES_ID)
WHEN MATCHED THEN UPDATE
SET PRESC.EVOL_ID = PRES.EVOL_ID;

COMMIT;

ALTER TABLE MODRED.PRESCRICAO  
MODIFY (EVOL_ID NOT NULL);

ALTER TABLE MODRED.PEDIDO_WORKUP 
ADD (FOCE_ID NUMBER );

CREATE INDEX MODRED.IN_FK_PEWO_FOCE ON MODRED.PEDIDO_WORKUP (FOCE_ID);

ALTER TABLE MODRED.PEDIDO_WORKUP
ADD CONSTRAINT FK_PEWO_FOCE FOREIGN KEY
(
  FOCE_ID 
)
REFERENCES MODRED.FONTE_CELULAS
(
  FOCE_ID 
)
ENABLE;

COMMENT ON COLUMN MODRED.PEDIDO_WORKUP.FOCE_ID IS 'Identificador da fonte de celula.';

ALTER TABLE MODRED.PEDIDO_WORKUP_AUD 
ADD (FOCE_ID NUMBER );

ALTER TABLE MODRED.PRESCRICAO 
ADD (AVPR_ID NUMBER );

CREATE INDEX MODRED.IN_FK_PRES_AVPR ON MODRED.PRESCRICAO (AVPR_ID);

ALTER TABLE MODRED.PRESCRICAO
ADD CONSTRAINT FK_PRES_AVPR FOREIGN KEY
(
  AVPR_ID 
)
REFERENCES MODRED.AVALIACAO_PRESCRICAO
(
  AVPR_ID 
)
ENABLE;

COMMENT ON COLUMN MODRED.PRESCRICAO.AVPR_ID IS 'Identificador para Avaliação Prescrição';

-- Final Estória 1
-- ESTÓRIA 2698 - CRONOLOGIA DO HISTORICO DO PACIENTE

DROP TABLE MODRED.EVOLUCAO_BUSCA;
DROP SEQUENCE MODRED.SQ_EVBU_ID;


CREATE TABLE MODRED.LOG_EVOLUCAO
(
  LOEV_ID NUMBER NOT NULL,
  USUA_ID NUMBER NOT NULL,
  LOEV_IN_TIPO_EVENTO VARCHAR2(255) NOT NULL,
  PACI_NR_RMR NUMBER NOT NULL,
  LOEV_NR_ID_OBJETO NUMBER NOT NULL,
  LOEV_DT_DATA TIMESTAMP NOT NULL,
  CONSTRAINT PK_LOEV PRIMARY KEY (LOEV_ID)
  USING INDEX (
    CREATE UNIQUE INDEX IN_PK_LOEV ON MODRED.LOG_EVOLUCAO (LOEV_ID ASC)
  )
  ENABLE
);

CREATE INDEX IN_FK_LOEV_PACI ON MODRED.LOG_EVOLUCAO (PACI_NR_RMR);
CREATE INDEX IN_FK_LOEV_USUA ON MODRED.LOG_EVOLUCAO (USUA_ID);

ALTER TABLE MODRED.LOG_EVOLUCAO
ADD CONSTRAINT FK_LOEV_PACI FOREIGN KEY(PACI_NR_RMR)
REFERENCES MODRED.PACIENTE (PACI_NR_RMR) ENABLE;

ALTER TABLE MODRED.LOG_EVOLUCAO
ADD CONSTRAINT FK_LOEV_USUA FOREIGN KEY(USUA_ID)
REFERENCES MODRED.USUARIO (USUA_ID) ENABLE;

COMMENT ON TABLE MODRED.LOG_EVOLUCAO IS 'Tabela de contendo o log de evoluções do paciente (podendo ser extendida para outras evoluções, como doador, por exemplo).';
COMMENT ON COLUMN MODRED.LOG_EVOLUCAO.LOEV_ID IS 'Chave primária da tabela de log de evoluções.';
COMMENT ON COLUMN MODRED.LOG_EVOLUCAO.USUA_ID IS 'Indica o usuário que realizou a inclusão de novo log.';
COMMENT ON COLUMN MODRED.LOG_EVOLUCAO.LOEV_IN_TIPO_EVENTO IS 'Tipo de evento ocorrido no log. Representa um enum no sistema, indicando a mensagem a ser exibida.';
COMMENT ON COLUMN MODRED.LOG_EVOLUCAO.LOEV_DT_DATA IS 'Data que ocorreu o evento (inclusão)';
COMMENT ON COLUMN MODRED.LOG_EVOLUCAO.LOEV_NR_ID_OBJETO IS 'ID do objeto genérico associado ao log. Pode ser qualquer informação a ser exibida na mensagem do log, desde um RMR até uma informação mais específica e necessária para compor a mensagem.';

CREATE SEQUENCE MODRED.SQ_LOEV_ID INCREMENT BY 1 MAXVALUE 9999999999999999999999999999 MINVALUE 1 CACHE 20;
COMMIT;

UPDATE MODRED.RECURSO SET RECU_TX_SIGLA = 'VISUALIZAR_LOG_EVOLUCAO' WHERE RECU_ID = 62;
COMMIT;

-- ESTORIA 2698

-- CORRIGINDO MAPEAMENTO ENTRE GENOTIPO E PACIENTE
-- RELACIONAMENTO, PELO MODELO CONCEITUAL, CONTINUA ONE-TO-ONE
-- MAS PASSA A TER PACIENTE NO GENOTIPO.
ALTER TABLE MODRED.GENOTIPO
ADD PACI_NR_RMR NUMBER;

MERGE INTO MODRED.GENOTIPO GEN
USING (
    SELECT GENO_ID, PACI_NR_RMR
    FROM MODRED.PACIENTE
    WHERE GENO_ID IS NOT NULL
    ) PAC
ON (GEN.GENO_ID = PAC.GENO_ID)
WHEN MATCHED THEN UPDATE
SET GEN.PACI_NR_RMR = PAC.PACI_NR_RMR;
COMMIT;

ALTER TABLE MODRED.GENOTIPO
MODIFY PACI_NR_RMR NOT NULL;

ALTER TABLE MODRED.PACIENTE
DROP COLUMN GENO_ID;
-- ESTORIA 2698
