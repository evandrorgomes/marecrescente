DELETE FROM MODRED.PERMISSAO WHERE RECU_ID = 50 AND PERF_ID = 15;
DELETE FROM MODRED.PERMISSAO WHERE RECU_ID = 52 AND PERF_ID = 15;
DELETE FROM MODRED.PERMISSAO WHERE RECU_ID = 53 AND PERF_ID = 15;

INSERT INTO "MODRED"."PERMISSAO" (RECU_ID, PERF_ID) VALUES ('50', '1');

DELETE FROM "MODRED"."TIPO_TAREFA" where TITA_ID = 72;