--------------------------------------------------------
--  Arquivo criado - Sexta-feira-Abril-05-2019   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Index PK_CANO
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_CANO" ON "CATEGORIA_NOTIFICACAO" ("CANO_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_NOTI
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_NOTI" ON "NOTIFICACAO" ("NOTI_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_USUA
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_USUA" ON "USUARIO" ("USUA_ID");
--------------------------------------------------------
--  DDL for Index PACIENTE_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "PACIENTE_PK" ON "PACIENTE" ("PACI_NR_RMR") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_NOTI_CANO
--------------------------------------------------------

  CREATE INDEX "IN_FK_NOTI_CANO" ON "NOTIFICACAO" ("CANO_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_NOTI_PACI
--------------------------------------------------------

  CREATE INDEX "IN_FK_NOTI_PACI" ON "NOTIFICACAO" ("PACI_NR_RMR") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_NOTI_USUA
--------------------------------------------------------

  CREATE INDEX "IN_FK_NOTI_USUA" ON "NOTIFICACAO" ("USUA_ID") 
  ;

--------------------------------------------------------
--  Constraints for Table CATEGORIA_NOTIFICACAO
--------------------------------------------------------

  ALTER TABLE "CATEGORIA_NOTIFICACAO" MODIFY ("CANO_ID" NOT NULL ENABLE);
  ALTER TABLE "CATEGORIA_NOTIFICACAO" MODIFY ("CANO_TX_DESCRICAO" NOT NULL ENABLE);
  ALTER TABLE "CATEGORIA_NOTIFICACAO" ADD CONSTRAINT "PK_CANO" PRIMARY KEY ("CANO_ID");
--------------------------------------------------------
--  Constraints for Table NOTIFICACAO
--------------------------------------------------------

  ALTER TABLE "NOTIFICACAO" MODIFY ("NOTI_ID" NOT NULL ENABLE);
  ALTER TABLE "NOTIFICACAO" MODIFY ("PACI_NR_RMR" NOT NULL ENABLE);
  ALTER TABLE "NOTIFICACAO" MODIFY ("NOTI_TX_DESCRICAO" NOT NULL ENABLE);
  ALTER TABLE "NOTIFICACAO" MODIFY ("CANO_ID" NOT NULL ENABLE);
  ALTER TABLE "NOTIFICACAO" MODIFY ("NOTI_IN_LIDO" NOT NULL ENABLE);
  ALTER TABLE "NOTIFICACAO" ADD CONSTRAINT "PK_NOTI" PRIMARY KEY ("NOTI_ID");
--------------------------------------------------------
--  Ref Constraints for Table NOTIFICACAO
--------------------------------------------------------

  ALTER TABLE "NOTIFICACAO" ADD CONSTRAINT "FK_NOTI_CANO" FOREIGN KEY ("CANO_ID")
	  REFERENCES "CATEGORIA_NOTIFICACAO" ("CANO_ID") ;
--------------------------------------------------------
--  Constraints for Table USUARIO
--------------------------------------------------------

  ALTER TABLE "USUARIO" MODIFY ("USUA_ID" NOT NULL ENABLE);
  ALTER TABLE "USUARIO" MODIFY ("USUA_TX_USERNAME" NOT NULL ENABLE);
  ALTER TABLE "USUARIO" ADD CONSTRAINT "PK_USUA" PRIMARY KEY ("USUA_ID");
--------------------------------------------------------
--  Constraints for Table PACIENTE
--------------------------------------------------------

  ALTER TABLE "PACIENTE" MODIFY ("PACI_NR_RMR" NOT NULL ENABLE);
  ALTER TABLE "PACIENTE" MODIFY ("USUA_ID" NOT NULL ENABLE);
  ALTER TABLE "PACIENTE" ADD CONSTRAINT "PACIENTE_PK" PRIMARY KEY ("PACI_NR_RMR");

