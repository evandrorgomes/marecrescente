-- Criação da tabela de Medico e do relacionamento com o Paciente
CREATE TABLE MODRED.MEDICO 
(
  MEDI_ID NUMBER NOT NULL 
, MEDI_TX_CRM VARCHAR2(20) NOT NULL 
, MEDI_TX_NOME VARCHAR2(255) NOT NULL 
, USUA_ID NUMBER
, CONSTRAINT PK_MEDI PRIMARY KEY (
    MEDI_ID
) ENABLE
, CONSTRAINT FK_MEDI_USUA FOREIGN KEY
(
  USUA_ID 
)
REFERENCES MODRED.USUARIO
(
  USUA_ID 
) ENABLE);

COMMENT ON TABLE MODRED.MEDICO IS 'Tabela de médicos';
COMMENT ON COLUMN MODRED.MEDICO.MEDI_ID IS 'Chave primária do médico';
COMMENT ON COLUMN MODRED.MEDICO.MEDI_TX_CRM IS 'Número do CRM do médico';
COMMENT ON COLUMN MODRED.MEDICO.MEDI_TX_NOME IS 'Nome do médico';
COMMENT ON COLUMN MODRED.MEDICO.USUA_ID IS 'Chave estrangeira relacionada ao usuário';

CREATE INDEX IN_FK_MEDI_USUA ON MODRED.MEDICO (USUA_ID ASC);

CREATE SEQUENCE MODRED.SQ_MEDI_ID INCREMENT BY 1 START WITH 1 MAXVALUE 9999999999999999999999999999 MINVALUE 1;

INSERT INTO MODRED.MEDICO VALUES (SQ_MEDI_ID.NEXTVAL, '10000', 'MEDICO GENERICO', 1);
COMMIT;


ALTER TABLE MODRED.PACIENTE 
ADD (MEDI_ID_RESPONSAVEL NUMBER );

COMMENT ON COLUMN MODRED.PACIENTE.MEDI_ID_RESPONSAVEL IS 'Chave estrangeira relacionada ao médico responsável';

ALTER TABLE MODRED.PACIENTE
ADD CONSTRAINT FK_PACI_MEDI FOREIGN KEY
(
  MEDI_ID_RESPONSAVEL 
)
REFERENCES MODRED.MEDICO
(
  MEDI_ID 
)
ENABLE;

CREATE INDEX IN_FK_PACI_MEDI ON MODRED.PACIENTE (MEDI_ID_RESPONSAVEL ASC);

UPDATE MODRED.PACIENTE SET MEDI_ID_RESPONSAVEL = 1;
COMMIT;

ALTER TABLE MODRED.PACIENTE  
MODIFY (MEDI_ID_RESPONSAVEL NOT NULL);

-- Criação de indice para melhorar a performance da consulta na tabela VALOR_DNA_NMDP
CREATE INDEX IN_VALOR_DNA_NMDP ON VALOR_DNA_NMDP (LOCU_ID ASC, DNNM_TX_VALOR ASC);

-- Alteração do nome da FK de Responsável em Paciente
ALTER INDEX IN_PACI_RESP 
RENAME TO IN_FK_PACI_RESP;

COMMENT ON COLUMN PACIENTE.RESP_ID IS 'Referência para a tabela de Responsável.';

--Alteração de Sequence de paciente para começar por 3 milhões.
ALTER SEQUENCE MODRED.SQ_PACI_NR_RMR INCREMENT BY 3000000;
SELECT MODRED.SQ_PACI_NR_RMR.NEXTVAL FROM dual;
ALTER SEQUENCE MODRED.SQ_PACI_NR_RMR INCREMENT BY 1;

-- INCLUSÃO DO PDF NA CONFIGURAÇÃO DO UPLOAD
UPDATE "MODRED"."CONFIGURACAO" 
SET CONF_TX_VALOR = 'image/png,image/jpeg,image/bmp,image/tiff,image/jpg,application/pdf'
WHERE CONF_ID = 'extensaoArquivoLaudo';

ALTER TABLE MODRED.EVOLUCAO  
MODIFY (ESDO_ID NULL);

-- Atualização do nome da UF AC
update MODRED.UF set UF_TX_NOME = 'ACRE' where UF_SIGLA='AC';
