﻿ALTER TABLE MODRED.EVOLUCAO  
MODIFY (ESDO_ID NULL);

update MODRED.EVOLUCAO set ESDO_ID=null;

DELETE FROM MODRED.ESTAGIO_DOENCA;

INSERT INTO MODRED.ESTAGIO_DOENCA (ESDO_ID, ESDO_TX_DESCRICAO) VALUES (1, '1ª Remissão' );
INSERT INTO MODRED.ESTAGIO_DOENCA (ESDO_ID, ESDO_TX_DESCRICAO) VALUES (2, '2ª Remissão' );
INSERT INTO MODRED.ESTAGIO_DOENCA (ESDO_ID, ESDO_TX_DESCRICAO) VALUES (3, '3ª Remissão' );
INSERT INTO MODRED.ESTAGIO_DOENCA (ESDO_ID, ESDO_TX_DESCRICAO) VALUES (4, 'Falha de indução' );
INSERT INTO MODRED.ESTAGIO_DOENCA (ESDO_ID, ESDO_TX_DESCRICAO) VALUES (5, 'Grave' );
INSERT INTO MODRED.ESTAGIO_DOENCA (ESDO_ID, ESDO_TX_DESCRICAO) VALUES (6, 'Fase acelerada' );
INSERT INTO MODRED.ESTAGIO_DOENCA (ESDO_ID, ESDO_TX_DESCRICAO) VALUES (7, 'Fase crônica' );
INSERT INTO MODRED.ESTAGIO_DOENCA (ESDO_ID, ESDO_TX_DESCRICAO) VALUES (8, '1ª Remissão Ph neg' );
INSERT INTO MODRED.ESTAGIO_DOENCA (ESDO_ID, ESDO_TX_DESCRICAO) VALUES (9, '1ª Remissão Ph pos' );
INSERT INTO MODRED.ESTAGIO_DOENCA (ESDO_ID, ESDO_TX_DESCRICAO) VALUES (10, 'Hipocelular' );
INSERT INTO MODRED.ESTAGIO_DOENCA (ESDO_ID, ESDO_TX_DESCRICAO) VALUES (11, 'Em transformação' );
INSERT INTO MODRED.ESTAGIO_DOENCA (ESDO_ID, ESDO_TX_DESCRICAO) VALUES (12, 'LMMC' );
INSERT INTO MODRED.ESTAGIO_DOENCA (ESDO_ID, ESDO_TX_DESCRICAO) VALUES (13, 'Outras síndromes mielodisplássicas' );
INSERT INTO MODRED.ESTAGIO_DOENCA (ESDO_ID, ESDO_TX_DESCRICAO) VALUES (14, 'Mielofibrose' );
INSERT INTO MODRED.ESTAGIO_DOENCA (ESDO_ID, ESDO_TX_DESCRICAO) VALUES (15, 'Outras síndromes mieloproliferativas' );
INSERT INTO MODRED.ESTAGIO_DOENCA (ESDO_ID, ESDO_TX_DESCRICAO) VALUES (16, 'Estável' );
INSERT INTO MODRED.ESTAGIO_DOENCA (ESDO_ID, ESDO_TX_DESCRICAO) VALUES (17, 'Recidiva' );
COMMIT;

update MODRED.EVOLUCAO set ESDO_ID=1;

ALTER TABLE MODRED.EVOLUCAO  
MODIFY (ESDO_ID NOT NULL);

COMMIT;