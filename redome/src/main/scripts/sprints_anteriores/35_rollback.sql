DELETE FROM "MODRED"."USUARIO_PERFIL" WHERE  USUA_ID = 53 AND  PERF_ID = 22;
DELETE FROM "MODRED"."USUARIO" WHERE  USUA_ID = 53;
DELETE FROM "MODRED"."PERFIL" WHERE PERF_ID = 22;
COMMIT;


DELETE FROM "MODRED"."TIPO_PEDIDO_LOGISTICA" WHERE  = 4;
DELETE FROM "MODRED"."TIPO_PEDIDO_LOGISTICA" WHERE  = 5;
commit;

ALTER TABLE PEDIDO_LOGISTICA_AUD 
drop column TIPL_IN_ORIGEM;

delete from  "MODRED"."PERMISSAO" where RECU_ID = 44 and PERF_ID=21;
commit;

DELETE FROM "MODRED"."PERFIL" WHERE PERF_ID = 23;
COMMIT;

DELETE FROM "MODRED"."RECURSO" WHERE RECU_ID = 114;
COMMIT;

DELETE FROM "MODRED"."PERMISSAO" WHERE RECU_ID = 114 AND  PERF_ID = 23;
COMMIT;


DELETE FROM "MODRED"."USUARIO" WHERE USUA_ID = 54;
COMMIT;


DELETE FROM "MODRED"."USUARIO_PERFIL" WHERE USUA_ID = 54 AND PERF_ID = 23;
COMMIT;

