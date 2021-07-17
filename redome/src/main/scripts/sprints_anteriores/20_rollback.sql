--Scripts para agendamento de pedido de coleta Cintia 30/01

--Alteracao na tabela PEDIDO_WORKUP
ALTER TABLE MODRED.PEDIDO_WORKUP 
DROP COLUMN PEWO_DT_LIBERACAO_DOADOR;

ALTER TABLE MODRED.PEDIDO_WORKUP RENAME COLUMN  PEWO_DT_COLETA TO  PEWO_DT_FINAL_COLETA;
COMMENT ON COLUMN MODRED.PEDIDO_WORKUP.PEWO_DT_FINAL_COLETA IS 'Data da coleta, o material deve estar disponível nesta data.';

ALTER TABLE MODRED.PEDIDO_WORKUP RENAME COLUMN PEWO_DT_DISPONIBILIDADE_DOADOR TO PEWO_DT_INICIO_COLETA;

COMMENT ON COLUMN MODRED.PEDIDO_WORKUP.PEWO_DT_INICIO_COLETA IS 'Data prevista em que o doador deve estar no centro de coleta.';

ALTER TABLE MODRED.PEDIDO_WORKUP_AUD 
DROP COLUMN PEWO_DT_LIBERACAO_DOADOR;

ALTER TABLE MODRED.PEDIDO_WORKUP_AUD RENAME COLUMN PEWO_DT_COLETA TO PEWO_DT_FINAL_COLETA;

ALTER TABLE MODRED.PEDIDO_WORKUP_AUD RENAME COLUMN PEWO_DT_DISPONIBILIDADE_DOADOR TO PEWO_DT_INICIO_COLETA;

--Alteracao na tabela DISPONIBILIDADE
ALTER TABLE MODRED.DISPONIBILIDADE 
DROP COLUMN DISP_DT_LIBERACAO_DOADOR;

ALTER TABLE MODRED.DISPONIBILIDADE RENAME COLUMN DISP_DT_COLETA TO DISP_DT_FINAL_COLETA;
COMMENT ON COLUMN MODRED.DISPONIBILIDADE.DISP_DT_FINAL_COLETA IS 'Data da coleta, quando o material deve estar disponível.';

ALTER TABLE MODRED.DISPONIBILIDADE RENAME COLUMN DISP_DT_DISPONIBILIDADE_DOADOR TO DISP_DT_INICIO_COLETA;

COMMENT ON COLUMN MODRED.DISPONIBILIDADE.DISP_DT_INICIO_COLETA IS 'Data prevista em que o doador deve estar no centro de coleta.';

ALTER TABLE MODRED.DISPONIBILIDADE_AUD 
DROP COLUMN DISP_DT_LIBERACAO_DOADOR;

ALTER TABLE MODRED.DISPONIBILIDADE_AUD RENAME COLUMN DISP_DT_COLETA TO DISP_DT_FINAL_COLETA;

ALTER TABLE MODRED.DISPONIBILIDADE_AUD RENAME COLUMN DISP_DT_DISPONIBILIDADE_DOADOR TO DISP_DT_INICIO_COLETA;

--Alteracao na tabela PEDIDO_COLETA
ALTER TABLE MODRED.PEDIDO_COLETA 
DROP CONSTRAINT FK_PECL_PEWO;

DROP INDEX IN_FK_PECL_PEWO;

ALTER TABLE MODRED.PEDIDO_COLETA 
DROP COLUMN PEWO_ID;

ALTER TABLE MODRED.PEDIDO_COLETA 
ADD (SOLI_ID NUMBER NOT NULL);

ALTER TABLE MODRED.PEDIDO_COLETA RENAME COLUMN PECL_DT_DISPONIBILIDADE_DOADOR TO PECL_DT_INICIO_COLETA;

ALTER TABLE MODRED.PEDIDO_COLETA RENAME COLUMN PECL_DT_LIBERACAO_DOADOR TO PECL_DT_FINAL_COLETA;


ALTER TABLE MODRED.PEDIDO_COLETA
ADD CONSTRAINT FK_PECL_SOLI FOREIGN KEY
(
  SOLI_ID 
)
REFERENCES MODRED.SOLICITACAO
(
  SOLI_ID 
)
ENABLE;

COMMENT ON COLUMN MODRED.PEDIDO_COLETA.PECL_DT_INICIO_COLETA IS 'Data em que o doador deverá estar disponível no centro de coleta.';

COMMENT ON COLUMN MODRED.PEDIDO_COLETA.PECL_DT_FINAL_COLETA IS 'Data em que o doador será liberado pelo centro de coleta.';

COMMENT ON COLUMN MODRED.PEDIDO_COLETA.SOLI_ID IS 'Identificador da solicitacao.';

--Alteracao na tabela PEDIDO_LOGISTICA
ALTER TABLE MODRED.PEDIDO_LOGISTICA 
DROP COLUMN PECL_ID;

ALTER TABLE MODRED.PEDIDO_LOGISTICA  
MODIFY (PEWO_ID NOT NULL);

DROP INDEX IN_FK_PELO_PECL;

ALTER TABLE MODRED.PEDIDO_LOGISTICA 
DROP CONSTRAINT FK_PELO_PECL;

ALTER TABLE MODRED.PEDIDO_LOGISTICA_AUD 
DROP COLUMN PECL_ID;

--- Alteração do tipo de prescrição
INSERT INTO MODRED.TIPO_SOLICITACAO (tiso_id, tiso_tx_descricao) VALUES (4,'COLETA');
UPDATE "MODRED"."TIPO_SOLICITACAO" SET TISO_TX_DESCRICAO='WORKUP' WHERE TISO_ID=3;

--Alteracao na tabela PRESCRICAO
ALTER TABLE MODRED.PRESCRICAO 
DROP CONSTRAINT FK_PRES_FOCE;

DROP INDEX IN_FK_PRES_FOCE;

ALTER TABLE MODRED.PRESCRICAO 
DROP COLUMN FOCE_ID;

--Criação de tabela para armazenar os tipo de fontes de celulas utilizados na coleta
drop table "MODRED"."FONTE_CELULAS" cascade constraints PURGE;

--Adicionando status a tabela de STATUS_PEDIDO_COLETA
DELETE FROM MODRED.STATUS_PEDIDO_COLETA WHERE STPC_ID > 1;

--Criando/alterando tipos de tarefa
DELETE FROM MODRED.TIPO_TAREFA WHERE TITA_ID > 40;
UPDATE MODRED.TIPO_TAREFA SET TITA_TX_DESCRICAO = 'PEDIDO_LOGISTICA_COLETA' WHERE TITA_ID=40;
COMMIT;

ALTER TABLE MODRED.TIPO_TAREFA  
MODIFY (TITA_TX_DESCRICAO VARCHAR2(30 BYTE) );

--Criando recurso
DELETE FROM MODRED.PERMISSAO WHERE RECU_ID=51;
DELETE FROM MODRED.RECURSO WHERE RECU_ID=51;
commit;

--FIM

-- TAREFA DE ROLLBACK
ALTER TABLE MODRED.TAREFA
DROP COLUMN USUA_ID_ULTIMO_RESPONSAVEL;
COMMIT;
-- FIM

-- TIPO DE TAREFA DE ROLLBACK
DELETE FROM MODRED.TIPO_TAREFA WHERE TITA_ID = 37;
COMMIT;
-- FIM


--ALTERAÇÃO DA FUNÇÃO GET_SCORE (RODAR SEPARADO)
create or replace FUNCTION get_score( paciente_id IN NUMBER )
  RETURN NUMBER
IS
  estagio_doenca_id NUMBER; 
  pontos_paciente_q_const NUMBER;
  dt_nascimento DATE;
  dt_cadastro DATE;
  idade NUMBER;
  diff_dias_cadastro NUMBER;
  cid_id_paci NUMBER;
  curabilidade NUMBER;
  q_constante NUMBER;
  urgencia NUMBER;

BEGIN

    select esdo_id into estagio_doenca_id from (select esdo_id  from evolucao where paci_nr_rmr = paciente_id order by evol_dt_criacao desc) where ROWNUM = 1  ;
    select cid_id into cid_id_paci from (select cid_id from diagnostico where paci_nr_rmr = paciente_id) where rownum = 1;
    -- quando estagio id for nula, devo buscar os parametros na propria cid
    IF estagio_doenca_id is null THEN                
        select CID_NR_CURABILIDADE,CID_NR_Q_CONSTANTE,CID_NR_URGENCIA INTO curabilidade,q_constante,urgencia from cid WHERE CID_ID = cid_id_paci;       
    ELSE        
        select CIED_NR_CURABILIDADE,CIED_NR_Q_CONSTANTE,CIED_NR_URGENCIA INTO curabilidade,q_constante,urgencia from cid_estagio_doenca WHERE ESDO_ID = estagio_doenca_id AND CID_ID = cid_id_paci;               
    END IF;

    select paci_dt_nascimento, paci_dt_cadastro into dt_nascimento ,dt_cadastro from paciente where paci_nr_rmr = paciente_id;

    --Recupera a idade do paciente
    SELECT trunc((months_between(sysdate, dt_nascimento))/12) AS idade into idade FROM DUAL;

    pontos_paciente_q_const:=0;  
    --Calcula pontos do paciente se for menor de 13 anos
    IF idade < 13 THEN
        pontos_paciente_q_const := 20;

    END IF;

    -- Calcula pontos do paciente com a seguinte regra:
    -- Adiciona 0.33 pontos a cada dia após o cadastro do paciente
    SELECT (TRUNC (TO_DATE(SYSDATE,'dd/mm/yyyy')) - TO_DATE (dt_cadastro, 'dd/mm/yyyy')) INTO diff_dias_cadastro FROM DUAL;
    pontos_paciente_q_const := pontos_paciente_q_const + diff_dias_cadastro*0.33;
    DBMS_OUTPUT.PUT_LINE (pontos_paciente_q_const + curabilidade + q_constante + urgencia);
    return pontos_paciente_q_const + curabilidade + q_constante + urgencia;

END;
-- FIM DA ALTERAÇÃO DA FUNÇÃO GET_SCORE


--Nova coluna na tabela de paciente
ALTER TABLE MODRED.PACIENTE 
DROP COLUMN PACI_NR_SCORE NUMBER;
--Fim da nova coluna na tabela de paciente

--Script para armazenar a avaliação ao pedido de coleta CINTIA 06/02
drop table "MODRED"."AVALIACAO_PEDIDO_COLETA" cascade constraints PURGE;
drop SEQUENCE MODRED.SQ_AVPC_ID;
DROP SYNONYM MODRED_APLICACAO.SQ_AVPC_ID;
DROP SYNONYM MODRED_APLICACAO.AVALIACAO_PEDIDO_COLETA;

DELETE FROM MODRED.PERMISSAO WHERE RECU_ID=52;
DELETE FROM MODRED.RECURSO WHERE RECU_ID=52;
commit;
--FIM

--criação de tipo de tarefas de notificação
DELETE FROM MODRED.TIPO_TAREFA WHERE TITA_ID = 44 OR TITA_ID = 45;
COMMIT;

ALTER TABLE MODRED.TIPO_TAREFA  
MODIFY (TITA_TX_DESCRICAO VARCHAR2(40 BYTE) );
--FIM
