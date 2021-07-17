DELETE FROM MODRED.TIPO_TRANSPLANTE;
INSERT INTO MODRED.TIPO_TRANSPLANTE (TITR_ID, TITR_TX_DESCRICAO) VALUES (1, 'Nenhum');
INSERT INTO MODRED.TIPO_TRANSPLANTE (TITR_ID, TITR_TX_DESCRICAO) VALUES (2, 'Aparentado HLA idêntico');
INSERT INTO MODRED.TIPO_TRANSPLANTE (TITR_ID, TITR_TX_DESCRICAO) VALUES (3, 'Aparentado haploidêntico');
INSERT INTO MODRED.TIPO_TRANSPLANTE (TITR_ID, TITR_TX_DESCRICAO) VALUES (4, 'Não aparentado');
COMMIT;

--Atualização
UPDATE MODRED.TIPO_TRANSPLANTE SET TITR_TX_DESCRICAO = 'Nenhum' WHERE TITR_ID = 1;
UPDATE MODRED.TIPO_TRANSPLANTE SET TITR_TX_DESCRICAO = 'Aparentado HLA idêntico' WHERE TITR_ID = 2;
UPDATE MODRED.TIPO_TRANSPLANTE SET TITR_TX_DESCRICAO = 'Aparentado haploidêntico' WHERE TITR_ID = 3;
UPDATE MODRED.TIPO_TRANSPLANTE SET TITR_TX_DESCRICAO = 'Não aparentado' WHERE TITR_ID = 4;
COMMIT;