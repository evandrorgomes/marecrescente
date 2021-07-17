INSERT INTO MODRED.PERFIL (PERF_ID, PERF_TX_DESCRICAO, SIST_ID)
VALUES (28, 'ANALISTA_CONTATO', 1);

INSERT INTO MODRED.RECURSO (RECU_ID, RECU_TX_SIGLA, RECU_TX_DESCRICAO)
VALUES (214, 'VISUALIZAR_DASHBOARD', 'Permite visualizar a home com a dashboard');

INSERT INTO MODRED.PERMISSAO (PERF_ID, RECU_ID, PERM_IN_COM_RESTRICAO)
VALUES (28, 214, 0);

INSERT INTO USUARIO (USUA_ID, USUA_TX_NOME, USUA_TX_USERNAME, USUA_TX_PASSWORD, USUA_IN_ATIVO, TRAN_ID, LABO_ID, USUA_TX_EMAIL)
SELECT SQ_USUA_ID.NEXTVAL, 'Analista de contato', 'ANALISTA_CONTATO', '$2a$11$KMAfznbkZhx9mvESGy3.GewPWbSGQLVTnj5O0m7cIo5NrucDtcXT.', 1, null, null, 'teste@gmail.com' from dual;

INSERT INTO MODRED.USUARIO_PERFIL (USUA_ID, PERF_ID)
SELECT SQ_USUA_ID.CURRVAL, 28 FROM DUAL;

COMMIT;

ALTER TABLE MODRED.PEDIDO_ENRIQUECIMENTO 
ADD (PEEN_IN_CANCELADO NUMBER DEFAULT 0 NOT NULL);

COMMENT ON COLUMN MODRED.PEDIDO_ENRIQUECIMENTO.PEEN_IN_CANCELADO IS 'Flag que indica se o pedido de enriquecimento está cancelado.';

UPDATE MODRED.PEDIDO_ENRIQUECIMENTO SET PEEN_IN_CANCELADO = 1
WHERE PEEN_ID IN (
SELECT PE.PEEN_ID FROM MODRED.PEDIDO_ENRIQUECIMENTO PE, MODRED.SOLICITACAO S WHERE
PE.SOLI_ID = S.SOLI_ID
AND S.SOLI_NR_STATUS = 3);
COMMIT;

--######################################## INSERT DE UMA NOVA CONFIGURAÇÃO ####################################################
insert into configuracao values ('tempoMaximoParaConsiderarRetornoContatoDoadorFase3', 60, 'Tempo limita para considerar o retorno da comunicação do doador na fase3.');
commit;

--criando usuario unico para REDOME_JOB
INSERT INTO "MODRED"."USUARIO" (USUA_ID, USUA_TX_NOME, USUA_TX_USERNAME, USUA_TX_PASSWORD, USUA_IN_ATIVO, USUA_TX_EMAIL) VALUES ('2321', 'REDOME JOB', 'REDOME_JOB', '$2a$11$KMAfznbkZhx9mvESGy3.GewPWbSGQLVTnj5O0m7cIo5NrucDtcXT.', '1', 'TESTE@UOL.COM');
COMMIT;
--adicionando o perfil de CONTATO_FASE_3 para usuario REDOME_JOB
INSERT INTO USUARIO_PERFIL (USUA_ID, PERF_ID)VALUES(2321,8);
COMMIT;
--no
insert into motivo_status_doador(MOSD_ID, MOSD_TX_DESCRICAO, stdo_id) VALUES (11, 'Afastado por idade', 4);
COMMIT;

--adicionando o perfil de CONTATO_PASSIVO para usuario REDOME_JOB
INSERT INTO USUARIO_PERFIL (USUA_ID, PERF_ID)VALUES(2321,10);
COMMIT;


INSERT INTO "MODRED"."CONFIGURACAO" (CONF_ID, CONF_TX_VALOR, CONF_TX_DESCRICAO) VALUES ('tempoMaximoSemEvolucaoEmDias', '90', 'Utilizado para afastamento de pacientes sem evolução durante um determinado período estipulado pelo REDOME');
commit;

--############### ALTERANDO PERFIL - CONTATO PASSIVO #######################
update permissao set  perf_id = 5 where recu_id = 48 and perf_id = 10;
commit;
--##########################################################################
