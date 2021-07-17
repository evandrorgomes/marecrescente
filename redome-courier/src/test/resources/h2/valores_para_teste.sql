SET MODE ORACLE;

INSERT INTO TRANSPORTADORA (TRAN_ID, TRAN_NOME, TRAN_ATIVO)
SELECT SQ_TRAN_ID.NEXTVAL, 'TRAMS tESTE', 1 FROM DUAL;

INSERT INTO ENDERECO_CONTATO (ENCO_ID, ENCO_CEP, ENCO_TX_TIPO_LOGRADOURO, ENCO_TX_NOME, ENCO_TX_COMPLEMENTO, ENCO_TX_BAIRRO,
ENCO_TX_NUMERO, ENCO_IN_EXCLUIDO, ENCO_IN_PRINCIPAL, ENCO_IN_CORRESPONDENCIA, ENCO_ID_PAIS, MUNI_ID, TRAN_ID)
SELECT SQ_ENCO_ID.NEXTVAL, '20231048', 'RUA', 'DOS INVÁLIDOS', NULL, 'CENTRO', '212', 0, 1, 1, 1, 1703, SQ_TRAN_ID.CURRVAL FROM DUAL;

INSERT INTO CONTATO_TELEFONICO (COTE_ID, COTE_TX_NOME, COTE_NR_COD_INTER, COTE_NR_COD_AREA, COTE_TX_COMPLEMENTO, COTE_IN_TIPO,
COTE_NR_NUMERO, COTE_IN_PRINCIPAL, COTE_IN_EXCLUIDO, TRAN_ID)
SELECT SQ_COTE_ID.NEXTVAL, 'CONTATO', 55, 21, NULL, 1, 21574600, 1, 0, SQ_TRAN_ID.CURRVAL FROM DUAL;

INSERT INTO EMAIL_CONTATO (EMCO_ID, EMCO_TX_EMAIL, EMCO_IN_EXCLUIDO, EMCO_IN_PRINCIPAL, TRAN_ID)
SELECT SQ_EMCO_ID.NEXTVAL, 'TESTE@TESTE.COM', 0, 1, SQ_TRAN_ID.CURRVAL FROM DUAL;

INSERT INTO TRANSPORTADORA (TRAN_ID, TRAN_NOME, TRAN_ATIVO)
SELECT SQ_TRAN_ID.NEXTVAL, 'TRAMS tESTE', 1 FROM DUAL;

INSERT INTO ENDERECO_CONTATO (ENCO_ID, ENCO_CEP, ENCO_TX_TIPO_LOGRADOURO, ENCO_TX_NOME, ENCO_TX_COMPLEMENTO, ENCO_TX_BAIRRO,
ENCO_TX_NUMERO, ENCO_IN_EXCLUIDO, ENCO_IN_PRINCIPAL, ENCO_IN_CORRESPONDENCIA, ENCO_ID_PAIS, MUNI_ID, TRAN_ID)
SELECT SQ_ENCO_ID.NEXTVAL, '20231048', 'RUA', 'DOS INVÁLIDOS', NULL, 'CENTRO', '212', 0, 1, 1, 1, 1703, SQ_TRAN_ID.CURRVAL FROM DUAL;

INSERT INTO CONTATO_TELEFONICO (COTE_ID, COTE_TX_NOME, COTE_NR_COD_INTER, COTE_NR_COD_AREA, COTE_TX_COMPLEMENTO, COTE_IN_TIPO,
COTE_NR_NUMERO, COTE_IN_PRINCIPAL, COTE_IN_EXCLUIDO, TRAN_ID)
SELECT SQ_COTE_ID.NEXTVAL, 'CONTATO', 55, 21, NULL, 1, 21574600, 1, 0, SQ_TRAN_ID.CURRVAL FROM DUAL;

INSERT INTO EMAIL_CONTATO (EMCO_ID, EMCO_TX_EMAIL, EMCO_IN_EXCLUIDO, EMCO_IN_PRINCIPAL, TRAN_ID)
SELECT SQ_EMCO_ID.NEXTVAL, 'TESTE@TESTE.COM', 0, 1, SQ_TRAN_ID.CURRVAL FROM DUAL;

INSERT INTO TRANSPORTADORA (TRAN_ID, TRAN_NOME, TRAN_ATIVO)
VALUES (10, 'NOVA TRANSPORTADORA', 1);

INSERT INTO PEDIDO_TRANSPORTE (PETR_ID, TRAN_ID, STPT_ID, PETR_HR_PREVISTA_RETIRADA, USUA_ID_RESPONSAVEL, PETR_DT_ATUALIZACAO, SOLI_ID, PELO_ID)
VALUES (1, 10, 1, sysdate, 1, sysdate, 1, 1);

INSERT INTO PEDIDO_TRANSPORTE (PETR_ID, TRAN_ID, STPT_ID, PETR_HR_PREVISTA_RETIRADA, USUA_ID_RESPONSAVEL, PETR_DT_ATUALIZACAO, SOLI_ID, PELO_ID)
VALUES (2, 10, 1, sysdate, 1, sysdate, 2, 2);

INSERT INTO PEDIDO_TRANSPORTE (PETR_ID, TRAN_ID, STPT_ID, PETR_HR_PREVISTA_RETIRADA, USUA_ID_RESPONSAVEL, PETR_DT_ATUALIZACAO, SOLI_ID, PELO_ID)
VALUES (3, 10, 1, sysdate, 1, sysdate, 2, 2);

INSERT INTO COURIER (COUR_ID, COUR_IN_ATIVO, COUR_TX_CPF, COUR_TX_NOME, COUR_TX_RG, TRAN_ID)
VALUES (1, 1, '1234567890', 'Fulano', '12345678', 10);

INSERT INTO PEDIDO_TRANSPORTE (PETR_ID, TRAN_ID, STPT_ID, PETR_HR_PREVISTA_RETIRADA, USUA_ID_RESPONSAVEL, PETR_DT_ATUALIZACAO, SOLI_ID, PELO_ID)
VALUES (4, 10, 2, sysdate, 1, sysdate, 2, 2);

INSERT INTO PEDIDO_TRANSPORTE (PETR_ID, TRAN_ID, STPT_ID, PETR_HR_PREVISTA_RETIRADA, USUA_ID_RESPONSAVEL, PETR_DT_ATUALIZACAO, SOLI_ID, PELO_ID)
VALUES (5, 10, 1, sysdate, 1, sysdate, 2, 2);

INSERT INTO PEDIDO_TRANSPORTE (PETR_ID, TRAN_ID, STPT_ID, PETR_HR_PREVISTA_RETIRADA, USUA_ID_RESPONSAVEL, PETR_DT_ATUALIZACAO, SOLI_ID, PELO_ID)
VALUES (6, 10, 3, sysdate, 1, sysdate, 2, 2);


COMMIT;