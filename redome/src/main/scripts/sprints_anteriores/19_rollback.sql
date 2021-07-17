--Script para armazenar o resultado de workup CINTIA 16/01
drop table "MODRED"."RESULTADO_WORKUP" cascade constraints PURGE;
drop table "MODRED"."RESULTADO_WORKUP_AUD" cascade constraints PURGE;
drop SEQUENCE MODRED.SQ_REWO_ID;
DROP SYNONYM MODRED_APLICACAO.SQ_REWO_ID;
DROP SYNONYM MODRED_APLICACAO.RESULTADO_WORKUP;
DROP SYNONYM MODRED_APLICACAO.RESULTADO_WORKUP_AUD;

drop table "MODRED"."ARQUIVO_RESULTADO_WORKUP" cascade constraints PURGE;
drop table "MODRED"."ARQUIVO_RESULTADO_WORKUP_AUD" cascade constraints PURGE;
drop SEQUENCE MODRED.SQ_ARRW_ID;
DROP SYNONYM MODRED_APLICACAO.SQ_ARRW_ID;
DROP SYNONYM MODRED_APLICACAO.ARQUIVO_RESULTADO_WORKUP;
DROP SYNONYM MODRED_APLICACAO.ARQUIVO_RESULTADO_WORKUP_AUD;
--FIM

--Script para a funcionalidade de cadastrar resultado de workup Cintia 16/01
drop table "MODRED"."USUARIO_CENTRO_TRANSPLANTE" cascade constraints PURGE;
DROP SYNONYM MODRED_APLICACAO.USUARIO_CENTRO_TRANSPLANTE;

DELETE FROM MODRED.PERMISSAO WHERE RECU_ID=46;
DELETE FROM MODRED.RECURSO WHERE RECU_ID=46;
DELETE FROM MODRED.USUARIO_PERFIL WHERE USUA_ID=11 AND PERF_ID=11;
DELETE FROM MODRED.PERFIL WHERE PERF_ID=11;
DELETE FROM MODRED.USUARIO WHERE USUA_ID=11;


DELETE FROM MODRED.TIPO_TAREFA WHERE TITA_ID=32;
DELETE FROM MODRED.TIPO_TAREFA WHERE TITA_ID=33;

DELETE FROM MODRED.CONFIGURACAO WHERE CONF_ID='extensaoArquivoResultadoWorkup';
DELETE FROM MODRED.CONFIGURACAO WHERE CONF_ID='tamanhoArquivoResultadoWorkupEmByte';
DELETE FROM MODRED.CONFIGURACAO WHERE CONF_ID='quantidadeMaximaArquivosResultadoWorkup';
--FIM

--Script para armazenar os pedidos de logistica do pedido de workup Cintia 17/01
drop table "MODRED"."TIPO_PEDIDO_LOGISTICA" cascade constraints PURGE;
drop table "MODRED"."STATUS_PEDIDO_LOGISTICA" cascade constraints PURGE;
drop table "MODRED"."TRANSPORTE_TERRESTRE" cascade constraints PURGE;
drop table "MODRED"."ARQUIVO_VOUCHER_LOGISTICA" cascade constraints PURGE;
drop table "MODRED"."PEDIDO_LOGISTICA" cascade constraints PURGE;
drop SEQUENCE MODRED.SQ_PELO_ID;
drop SEQUENCE MODRED.SQ_TRTE_ID;
DROP SYNONYM MODRED_APLICACAO.SQ_PELO_ID;
DROP SYNONYM MODRED_APLICACAO.SQ_TRTE_ID;
DROP SYNONYM MODRED_APLICACAO.TIPO_PEDIDO_LOGISTICA;
DROP SYNONYM MODRED_APLICACAO.STATUS_PEDIDO_LOGISTICA;
DROP SYNONYM MODRED_APLICACAO.PEDIDO_LOGISTICA;
DROP SYNONYM MODRED_APLICACAO.ARQUIVO_VOUCHER_LOGISTICA;
DROP SYNONYM MODRED_APLICACAO.TRANSPORTE_TERRESTRE;

DELETE FROM MODRED.PERMISSAO WHERE RECU_ID=47;
DELETE FROM MODRED.RECURSO WHERE RECU_ID=47;
DELETE FROM MODRED.USUARIO_PERFIL WHERE USUA_ID=12 AND PERF_ID=12;
DELETE FROM MODRED.PERFIL WHERE PERF_ID=12;
DELETE FROM MODRED.USUARIO WHERE USUA_ID=12;

DELETE FROM MODRED.TIPO_TAREFA WHERE TITA_ID=36;

DELETE FROM MODRED.CONFIGURACAO WHERE CONF_ID='extensaoArquivoPedidoLogistica';
DELETE FROM MODRED.CONFIGURACAO WHERE CONF_ID='tamanhoArquivoPedidoLogisticaEmByte';

--FIM

--ajuste na funcionalidade de pedido de workup
ALTER TABLE MODRED.PEDIDO_WORKUP 
DROP COLUMN PEWO_DT_INICIO_COLETA;

ALTER TABLE MODRED.PEDIDO_WORKUP 
DROP COLUMN PEWO_DT_INICIO_WORKUP;

ALTER TABLE MODRED.PEDIDO_WORKUP 
DROP COLUMN PEWO_DT_FINAL_WORKUP;

ALTER TABLE MODRED.PEDIDO_WORKUP RENAME COLUMN  PEWO_DT_FINAL_COLETA TO PEWO_DT_COLETA;

ALTER TABLE MODRED.PEDIDO_WORKUP RENAME COLUMN PEWO_DT_LIMITE_WORKUP TO PEWO_DT_RESULTADO_WORKUP;

ALTER TABLE MODRED.PEDIDO_WORKUP_AUD 
DROP COLUMN PEWO_DT_INICIO_COLETA;

ALTER TABLE MODRED.PEDIDO_WORKUP_AUD 
DROP COLUMN PEWO_DT_INICIO_WORKUP;

ALTER TABLE MODRED.PEDIDO_WORKUP_AUD 
DROP COLUMN PEWO_DT_FINAL_WORKUP;

ALTER TABLE MODRED.PEDIDO_WORKUP_AUD RENAME COLUMN  PEWO_DT_FINAL_COLETA TO PEWO_DT_COLETA;

ALTER TABLE MODRED.PEDIDO_WORKUP_AUD RENAME COLUMN PEWO_DT_LIMITE_WORKUP TO PEWO_DT_RESULTADO_WORKUP;

ALTER TABLE DISPONIBILIDADE 
DROP COLUMN DISP_DT_INICIO_COLETA;

ALTER TABLE DISPONIBILIDADE
DROP COLUMN DISP_DT_INICIO_WORKUP;

ALTER TABLE DISPONIBILIDADE 
DROP COLUMN DISP_DT_FINAL_WORKUP;

ALTER TABLE MODRED.DISPONIBILIDADE RENAME COLUMN  DISP_DT_FINAL_COLETA TO DISP_DT_COLETA;

ALTER TABLE MODRED.DISPONIBILIDADE RENAME COLUMN  DISP_DT_LIMITE_WORKUP TO DISP_DT_RESULTADO_WORKUP;

ALTER TABLE DISPONIBILIDADE_AUD 
DROP COLUMN DISP_DT_INICIO_COLETA;

ALTER TABLE DISPONIBILIDADE_AUD 
DROP COLUMN DISP_DT_INICIO_WORKUP;

ALTER TABLE DISPONIBILIDADE_AUD 
DROP COLUMN DISP_DT_FINAL_WORKUP;

--FIM

--Adicionando propriedade na tabela PEDIDO_WORKUP
ALTER TABLE MODRED.PEDIDO_WORKUP 
DROP COLUMN PEWO_IN_NECESSITA_LOGISTICA;

ALTER TABLE MODRED.PEDIDO_WORKUP_AUD 
DROP COLUMN PEWO_IN_NECESSITA_LOGISTICA NUMBER(1);
--fim

--Auditoria para pedido de logistica
drop table "MODRED"."PEDIDO_LOGISTICA" cascade constraints PURGE;
DROP SYNONYM MODRED_APLICACAO.PEDIDO_LOGISTICA;
drop table "MODRED"."ARQUIVO_VOUCHER_LOGISTICA" cascade constraints PURGE;
DROP SYNONYM MODRED_APLICACAO.ARQUIVO_VOUCHER_LOGISTICA;
drop table "MODRED"."TRANSPORTE_TERRESTRE" cascade constraints PURGE;
DROP SYNONYM MODRED_APLICACAO.TRANSPORTE_TERRESTRE;
--FIM

-- Script para avaliação de resultado de workup Cintia 23/01
DROP TABLE MODRED.AVALIACAO_RESULTADO_WORKUP cascade constraints PURGE;
DROP SEQUENCE MODRED.SQ_AVRW_ID;
DROP TABLE MODRED.PEDIDO_ADICIONAL_WORKUP cascade constraints PURGE;
DROP SEQUENCE MODRED.SQ_PEAW_ID;
DROP TABLE MODRED.RESPOSTA_PEDIDO_ADICIONAL cascade constraints PURGE; 
DROP SEQUENCE MODRED.SQ_REPA_ID;
DROP TABLE MODRED.PEDIDO_COLETA cascade constraints PURGE; 
DROP TABLE MODRED.STATUS_PEDIDO_COLETA cascade constraints PURGE; 

--sequence
DROP SEQUENCE MODRED.SQ_PECL_ID;

--Demais scripts
DELETE FROM MODRED.TIPO_SOLICITACAO WHERE tiso_id=4;
DELETE FROM MODRED.STATUS_PEDIDO_COLETA WHERE STPC_ID = 1;

DELETE FROM MODRED.USUARIO_PERFIL WHERE USUA_ID='13';
DELETE FROM "MODRED"."PERFIL" WHERE PERF_ID='13';
DELETE FROM "MODRED"."USUARIO" WHERE USUA_ID='13';


DELETE FROM MODRED.TIPO_TAREFA WHERE TITA_ID = 37;
DELETE FROM MODRED.TIPO_TAREFA WHERE TITA_ID = 38;
DELETE FROM MODRED.TIPO_TAREFA WHERE TITA_ID = 39;
DELETE FROM MODRED.TIPO_TAREFA WHERE TITA_ID = 40;
COMMIT;

--Sinonimos
DROP SYNONYM "MODRED_APLICACAO"."AVALIACAO_RESULTADO_WORKUP";
DROP SYNONYM "MODRED_APLICACAO"."PEDIDO_ADICIONAL_WORKUP";
DROP SYNONYM "MODRED_APLICACAO"."RESPOSTA_PEDIDO_ADICIONAL";
DROP SYNONYM "MODRED_APLICACAO"."PEDIDO_COLETA";
DROP SYNONYM "MODRED_APLICACAO"."STATUS_PEDIDO_COLETA";

DROP SYNONYM "MODRED_APLICACAO"."SQ_AVRW_ID";
DROP SYNONYM "MODRED_APLICACAO"."SQ_PEAW_ID";
DROP SYNONYM "MODRED_APLICACAO"."SQ_REPA_ID";
DROP SYNONYM "MODRED_APLICACAO"."SQ_PECL_ID";
--FIM




--Voltando alteração da tabela de pedido workup 
ALTER TABLE MODRED.PEDIDO_WORKUP RENAME COLUMN PEWO_IN_LOGISTICA_DOADOR TO PEWO_IN_NECESSITA_LOGISTICA;
ALTER TABLE MODRED.PEDIDO_WORKUP_AUD RENAME COLUMN PEWO_IN_LOGISTICA_DOADOR TO PEWO_IN_NECESSITA_LOGISTICA ;
--Excluindo recursos e permissoes
DELETE FROM "MODRED"."RECURSO" WHERE RECU_ID = 49;
DELETE FROM "MODRED"."PERMISSAO" WHERE RECU_ID = 49 AND PERF_ID = 11;
COMMIT;