CREATE TABLE PROCESSO 
(
  PROC_ID NUMBER NOT NULL 
, PROC_DT_CRIACAO TIMESTAMP(6) NOT NULL 
, PROC_DT_ATUALIZACAO TIMESTAMP(6) NOT NULL 
, PROC_NR_TIPO NUMBER NOT NULL 
, PACI_NR_RMR NUMBER
, DOAD_ID NUMBER  
, PROC_NR_STATUS NUMBER NOT NULL 
);
CREATE TABLE TIPO_TAREFA 
(
  TITA_ID NUMBER NOT NULL 
, TITA_TX_DESCRICAO VARCHAR2(60 BYTE) NOT NULL 
, TITA_IN_AUTOMATICA NUMBER NOT NULL 
, TITA_NR_TEMPO_EXECUCAO NUMBER 
, TARE_IN_SOMENTE_AGRUPADOR NUMBER NOT NULL
);

CREATE TABLE TAREFA 
(
  TARE_ID NUMBER NOT NULL 
, PROC_ID NUMBER NOT NULL 
, TARE_DT_CRIACAO TIMESTAMP(6) NOT NULL 
, TARE_DT_ATUALIZACAO TIMESTAMP(6) NOT NULL 
, TITA_ID NUMBER NOT NULL 
, TARE_NR_STATUS NUMBER NOT NULL 
, PERF_ID_RESPONSAVEL NUMBER 
, CETR_ID_RESPONSAVEL NUMBER 
, TARE_TX_DESCRICAO VARCHAR2(255 BYTE) 
, USUA_ID_RESPONSAVEL NUMBER 
, TARE_NR_RELACAO_ENTIDADE NUMBER 
, TARE_DT_INICIO TIMESTAMP(6) 
, TARE_DT_FIM TIMESTAMP(6) 
, TARE_DT_HORA_INICIO TIMESTAMP(6) 
, TARE_DT_HORA_FIM TIMESTAMP(6) 
, TARE_IN_INCLUSIVO_EXCLUSIVO NUMBER 
, TARE_IN_AGENDADO NUMBER  
, USUA_ID_ULTIMO_RESPONSAVEL NUMBER 
, TARE_NR_PARCEIRO NUMBER 
, TARE_ID_TAREFA_PAI NUMBER 
, USUA_ID_AGENDAMENTO NUMBER
);

CREATE SEQUENCE  "SQ_PROC_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
CREATE SEQUENCE  "SQ_TARE_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;


--------------------------------------------------------
--  DDL for Table PACIENTE
--------------------------------------------------------

  CREATE TABLE "PACIENTE" 
   (	"PACI_NR_RMR" NUMBER, 
	"PACI_TX_CPF" VARCHAR2(11), 
	"PACI_TX_CNS" VARCHAR2(15), 
	"PACI_TX_NOME" VARCHAR2(255), 
	"PACI_TX_NOME_MAE" VARCHAR2(255), 
	"PACI_IN_SEXO" VARCHAR2(1), 
	"RACA_ID" NUMBER, 
	"ETNI_ID" NUMBER, 
	"PACI_TX_ABO" VARCHAR2(3), 
	"PAIS_ID" NUMBER, 
	"UF_SIGLA" VARCHAR2(2), 
	"PACI_TX_EMAIL" VARCHAR2(100), 
	"USUA_ID" NUMBER, 
	"PACI_DT_CADASTRO" TIMESTAMP (6), 
	"PACI_DT_NASCIMENTO" DATE, 
	"RESP_ID" NUMBER, 
	"CETR_ID_AVALIADOR" NUMBER, 
	"MEDI_ID_RESPONSAVEL" NUMBER, 
	"PACI_IN_ACEITA_MISMATCH" NUMBER(1,0), 
	"PACI_TX_NOME_FONETIZADO" VARCHAR2(255), 
	"STPA_ID" NUMBER, 
	"PACI_NR_TEMPO_TRANSPLANTE" NUMBER,
	"PACI_NR_WMDA" NUMBER,
	"PACI_DT_ENVIO_EMDIS" DATE,
	"PACI_NR_WMDA_ID" NUMBER
	--"PACI_NR_SCORE" number
   ) ;

   COMMENT ON COLUMN "PACIENTE"."PACI_NR_RMR" IS 'Identificador do Paciente';
   COMMENT ON COLUMN "PACIENTE"."PACI_TX_CPF" IS 'Cpf do paciente';
   COMMENT ON COLUMN "PACIENTE"."PACI_TX_CNS" IS 'Cartão nacional de saúde do paciente';
   COMMENT ON COLUMN "PACIENTE"."PACI_TX_NOME" IS 'Nome do paciente';
   COMMENT ON COLUMN "PACIENTE"."PACI_TX_NOME_MAE" IS 'Nome da Mãe do Paciente';
   COMMENT ON COLUMN "PACIENTE"."PACI_IN_SEXO" IS 'Sexo do paciente (M - masculinho, F- Feminino)';
   COMMENT ON COLUMN "PACIENTE"."RACA_ID" IS 'Referencia para tabela raça ';
   COMMENT ON COLUMN "PACIENTE"."ETNI_ID" IS 'Referencia para a tabela etnia ';
   COMMENT ON COLUMN "PACIENTE"."PACI_TX_ABO" IS 'Tipo sanguíneo (A+,B+,A-,B-,AB+,AB-,O+,O-)';
   COMMENT ON COLUMN "PACIENTE"."PAIS_ID" IS 'Id do país da nacionalidade';
   COMMENT ON COLUMN "PACIENTE"."UF_SIGLA" IS 'Id do estado da naturadlidade';
   COMMENT ON COLUMN "PACIENTE"."PACI_TX_EMAIL" IS 'Email do paciente';
   COMMENT ON COLUMN "PACIENTE"."USUA_ID" IS 'Referencia do usuario que cadastrou';
   COMMENT ON COLUMN "PACIENTE"."PACI_DT_CADASTRO" IS 'Data do cadastro ';
   COMMENT ON COLUMN "PACIENTE"."PACI_DT_NASCIMENTO" IS 'Data de nascimento do Paciente';
   COMMENT ON COLUMN "PACIENTE"."RESP_ID" IS 'Referência para a tabela de Responsável.';
   COMMENT ON COLUMN "PACIENTE"."CETR_ID_AVALIADOR" IS 'Referência para centro avaliador na tabela CENTRO_TRANSPLANTE.';
   COMMENT ON COLUMN "PACIENTE"."MEDI_ID_RESPONSAVEL" IS 'Chave estrangeira relacionada ao médico responsável';
   COMMENT ON COLUMN "PACIENTE"."PACI_IN_ACEITA_MISMATCH" IS 'Define se o médico aceita pelo menos 1 mismatch ex. 5x6';
   COMMENT ON COLUMN "PACIENTE"."PACI_TX_NOME_FONETIZADO" IS 'Nome do paciente fonetizado';
   COMMENT ON COLUMN "PACIENTE"."STPA_ID" IS 'Status do paciente';
   COMMENT ON COLUMN "PACIENTE"."PACI_NR_TEMPO_TRANSPLANTE" IS 'Tempo em dias para transplante do paciente.';
   COMMENT ON COLUMN "PACIENTE"."PACI_NR_WMDA" IS 'Id do wmda quando o paciente é liberado.';
   COMMENT ON COLUMN "PACIENTE"."PACI_DT_ENVIO_EMDIS" IS 'Data de envio dos dados do paciente para EMIDIS.';   
   COMMENT ON TABLE "PACIENTE"  IS 'Pacientes';
--------------------------------------------------------
--  DDL for Table PACIENTE_AUD
--------------------------------------------------------

  CREATE TABLE "PACIENTE_AUD" 
   (	"PACI_NR_RMR" NUMBER, 
	"AUDI_ID" NUMBER, 
	"AUDI_TX_TIPO" NUMBER(3,0), 
	"PACI_TX_ABO" VARCHAR2(3), 
	"PACI_IN_ACEITA_MISMATCH" NUMBER(1,0), 
	"PACI_TX_CNS" VARCHAR2(255), 
	"PACI_TX_CPF" VARCHAR2(255), 
	"PACI_DT_CADASTRO" TIMESTAMP (6), 
	"PACI_DT_NASCIMENTO" DATE, 
	"PACI_TX_EMAIL" VARCHAR2(255), 
	"PACI_IN_MOTIVO_CADASTRO" NUMBER(1,0), 
	"PACI_TX_NOME" VARCHAR2(255), 
	"PACI_TX_NOME_FONETIZADO" VARCHAR2(255), 
	"PACI_TX_NOME_MAE" VARCHAR2(255), 
	"PACI_IN_SEXO" VARCHAR2(1), 
	"CETR_ID_AVALIADOR" NUMBER, 
	"CETR_ID_TRANSPLANTADOR" NUMBER, 
	"ETNI_ID" NUMBER, 
	"MEDI_ID_RESPONSAVEL" NUMBER, 
	"UF_SIGLA" VARCHAR2(2), 
	"PAIS_ID" NUMBER, 
	"RACA_ID" NUMBER, 
	"RESP_ID" NUMBER, 
	"USUA_ID" NUMBER, 
	"GENO_ID" NUMBER, 
	"STPA_ID" NUMBER, 
	"SPAC_ID" NUMBER, 
	"PACI_NR_TEMPO_TRANSPLANTE" NUMBER,
	"PACI_NR_WMDA" NUMBER,
	"PACI_DT_ENVIO_EMDIS" DATE
   ) ;

   COMMENT ON TABLE "PACIENTE_AUD"  IS 'Tabela de auditoria da tabela PACIENTE.';

--------------------------------------------------------
--  DDL for Table LOCUS
--------------------------------------------------------

  CREATE TABLE "LOCUS" 
   (	"LOCU_ID" VARCHAR2(4), 
	"LOCU_NR_ORDEM" NUMBER(2,0), 
	"LOCU_NR_PESO_FASE2" NUMBER
   ) ;

   COMMENT ON COLUMN "LOCUS"."LOCU_ID" IS 'Chave Primária do locus exame que é um código';
   COMMENT ON COLUMN "LOCUS"."LOCU_NR_ORDEM" IS 'Ordem do locus para ser apresentada na tela';
   COMMENT ON COLUMN "LOCUS"."LOCU_NR_PESO_FASE2" IS 'Define o peso de cada locus para definir se o doador está em Fase 1 ou 2, a partir do HLA. A relação fase/somatório é: Fase 1: Somatório menor que 1; Fase 2: maior que 1.';
   COMMENT ON TABLE "LOCUS"  IS 'Tabela de domínio de locus';
--------------------------------------------------------
--  DDL for Table LOCUS_EXAME
--------------------------------------------------------

  CREATE TABLE "LOCUS_EXAME" 
   (	"LOEX_TX_PRIMEIRO_ALELO" VARCHAR2(20), 
	"LOEX_TX_SEGUNDO_ALELO" VARCHAR2(20), 
	"LOCU_ID" VARCHAR2(4), 
	"EXAM_ID" NUMBER, 
	"LOEX_IN_PRIMEIRO_DIVERGENTE" NUMBER, 
	"LOEX_IN_SEGUNDO_DIVERGENTE" NUMBER, 
	"LOEX_IN_INATIVO" NUMBER, 
	"USUA_ID" NUMBER, 
	"LOEX_IN_SELECIONADO" NUMBER, 
	"LOEX_DT_MARCACAO_DIVERGENTE" TIMESTAMP (6), 
	"LOEX_IN_MOTIVO_DIVERGENCIA" NUMBER
   ) ;

   COMMENT ON COLUMN "LOCUS_EXAME"."LOEX_TX_PRIMEIRO_ALELO" IS 'Codigo do primeiro alelo';
   COMMENT ON COLUMN "LOCUS_EXAME"."LOEX_TX_SEGUNDO_ALELO" IS 'Codigo do segundo alelo';
   COMMENT ON COLUMN "LOCUS_EXAME"."LOCU_ID" IS 'Chave estrangeira para locus';
   COMMENT ON COLUMN "LOCUS_EXAME"."EXAM_ID" IS 'Chave estrangeira para exame.';
   COMMENT ON COLUMN "LOCUS_EXAME"."LOEX_IN_PRIMEIRO_DIVERGENTE" IS 'Identifica o primeiro alelo como divergente ou não';
   COMMENT ON COLUMN "LOCUS_EXAME"."LOEX_IN_SEGUNDO_DIVERGENTE" IS 'Identifica o segundo alelo como divergente ou não';
   COMMENT ON COLUMN "LOCUS_EXAME"."LOEX_IN_INATIVO" IS 'Informa se o locus exame est� ativo ou inativo.';
   COMMENT ON COLUMN "LOCUS_EXAME"."USUA_ID" IS 'Identifica��o do usu�rio que inativou.';
   COMMENT ON COLUMN "LOCUS_EXAME"."LOEX_IN_SELECIONADO" IS 'Informa que esse locus foi escolhido para ser usado no gen�tipo quando o motivo da  divergencia for Empate.';
   COMMENT ON COLUMN "LOCUS_EXAME"."LOEX_DT_MARCACAO_DIVERGENTE" IS 'Informa a data que foi resolvido a divergencia deste locus.';
   COMMENT ON COLUMN "LOCUS_EXAME"."LOEX_IN_MOTIVO_DIVERGENCIA" IS 'Informa o motivo da divergencia se foi 0 - por divegencia ou 1 - por empate.';
   COMMENT ON TABLE "LOCUS_EXAME"  IS 'Tabela de locus de um determinado exame do paciente';
--------------------------------------------------------
--  DDL for Table LOCUS_EXAME_AUD
--------------------------------------------------------

  CREATE TABLE "LOCUS_EXAME_AUD" 
   (	"EXAM_ID" NUMBER, 
	"LOCU_ID" VARCHAR2(255), 
	"AUDI_ID" NUMBER, 
	"AUDI_TX_TIPO" NUMBER, 
	"LOEX_TX_PRIMEIRO_ALELO" VARCHAR2(255), 
	"LOEX_TX_SEGUNDO_ALELO" VARCHAR2(255), 
	"LOEX_IN_PRIMEIRO_DIVERGENTE" NUMBER, 
	"LOEX_IN_SEGUNDO_DIVERGENTE" NUMBER, 
	"LOEX_IN_INATIVO" NUMBER, 
	"USUA_ID" NUMBER, 
	"LOEX_IN_SELECIONADO" NUMBER, 
	"LOEX_DT_MARCACAO_DIVERGENTE" TIMESTAMP (6), 
	"LOEX_IN_MOTIVO_DIVERGENCIA" NUMBER
   ) ;

   COMMENT ON TABLE "LOCUS_EXAME_AUD"  IS 'Tabela de auditoria da tabela LOCUS_EXAME';
--------------------------------------------------------
--  DDL for Table LOCUS_EXAME_PRELIMINAR
--------------------------------------------------------

  CREATE TABLE "LOCUS_EXAME_PRELIMINAR" 
   (	"LOEP_ID" NUMBER, 
	"LOEP_TX_CODIGO_LOCUS" VARCHAR2(4), 
	"LOEP_TX_PRIMEIRO_ALELO" VARCHAR2(20), 
	"LOEP_TX_SEGUNDO_ALELO" VARCHAR2(20), 
	"BUPR_ID" NUMBER
   ) ;

   COMMENT ON COLUMN "LOCUS_EXAME_PRELIMINAR"."LOEP_ID" IS 'Sequence identificador da tabela.';
   COMMENT ON COLUMN "LOCUS_EXAME_PRELIMINAR"."LOEP_TX_CODIGO_LOCUS" IS 'Código do locus.';
   COMMENT ON COLUMN "LOCUS_EXAME_PRELIMINAR"."LOEP_TX_PRIMEIRO_ALELO" IS 'Valor do alelo na primeira posição.';
   COMMENT ON COLUMN "LOCUS_EXAME_PRELIMINAR"."LOEP_TX_SEGUNDO_ALELO" IS 'Valor do alelo na segunda posição.';
   COMMENT ON COLUMN "LOCUS_EXAME_PRELIMINAR"."BUPR_ID" IS 'Identificador (FK) da tabela de busca preliminar. Indica quem é a quem está associado os valores inseridos.';
   COMMENT ON TABLE "LOCUS_EXAME_PRELIMINAR"  IS 'Tabela que registra os valores, lócus por lócus, do genótipo do paciente preliminar.';
--------------------------------------------------------
--  DDL for Table LOCUS_PEDIDO_EXAME
--------------------------------------------------------

  CREATE TABLE "LOCUS_PEDIDO_EXAME" 
   (	"LOCU_ID" VARCHAR2(4), 
	"PEEX_ID" NUMBER
   ) ;

   COMMENT ON COLUMN "LOCUS_PEDIDO_EXAME"."LOCU_ID" IS 'Código do lócus selecionado no pedido.';
   COMMENT ON COLUMN "LOCUS_PEDIDO_EXAME"."PEEX_ID" IS 'ID do pedido de exame onde os lócus foram solicitados.';
   COMMENT ON TABLE "LOCUS_PEDIDO_EXAME"  IS 'Tabela onde ficam os lócus solicitados no pedido de exame.';
--------------------------------------------------------
--  DDL for Table LOCUS_PEDIDO_EXAME_AUD
--------------------------------------------------------

  CREATE TABLE "LOCUS_PEDIDO_EXAME_AUD" 
   (	"AUDI_ID" NUMBER(19,0), 
	"PEEX_ID" NUMBER(19,0), 
	"LOCU_ID" VARCHAR2(255 CHAR), 
	"AUDI_TX_TIPO" NUMBER(3,0)
   ) ;

   COMMENT ON COLUMN "LOCUS_PEDIDO_EXAME_AUD"."AUDI_ID" IS 'IDENTIFICAÇÃO DE AUDITORIA';
   COMMENT ON COLUMN "LOCUS_PEDIDO_EXAME_AUD"."PEEX_ID" IS 'IDENTIFICAÇÃO DO PEDIDO DE EXAME';
   COMMENT ON COLUMN "LOCUS_PEDIDO_EXAME_AUD"."LOCU_ID" IS 'IDENTIFICAÇÃO DO LOCUS';
   COMMENT ON COLUMN "LOCUS_PEDIDO_EXAME_AUD"."AUDI_TX_TIPO" IS 'TIPO DE AUDITORIA';
   COMMENT ON TABLE "LOCUS_PEDIDO_EXAME_AUD"  IS 'AUDITORIA DE LOCUS DE PEDIDO EXAME';

