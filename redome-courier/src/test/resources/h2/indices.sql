--------------------------------------------------------
--  Arquivo criado - Sexta-feira-Abril-05-2019   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Index PK_AUDI
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_AUDI" ON "AUDITORIA" ("AUDI_ID") 
  ;
--------------------------------------------------------
--  Constraints for Table AUDITORIA
--------------------------------------------------------

  ALTER TABLE "AUDITORIA" MODIFY ("AUDI_ID" NOT NULL ENABLE);
  ALTER TABLE "AUDITORIA" ADD CONSTRAINT "PK_AUDI" PRIMARY KEY ("AUDI_ID");

--------------------------------------------------------
--  DDL for Index PK_CONF
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_CONF" ON "CONFIGURACAO" ("CONF_ID")
  ;
--------------------------------------------------------
--  Constraints for Table CONFIGURACAO
--------------------------------------------------------

  ALTER TABLE "CONFIGURACAO" ADD CONSTRAINT "PK_CONF" PRIMARY KEY ("CONF_ID");
--------------------------------------------------------
--  DDL for Index IN_PK_TRAN
--------------------------------------------------------

  CREATE UNIQUE INDEX "IN_PK_TRAN" ON "TRANSPORTADORA" ("TRAN_ID") 
  ;
--------------------------------------------------------
--  Constraints for Table TRANSPORTADORA
--------------------------------------------------------

  ALTER TABLE "TRANSPORTADORA" MODIFY ("TRAN_ID" NOT NULL ENABLE);
  ALTER TABLE "TRANSPORTADORA" MODIFY ("TRAN_NOME" NOT NULL ENABLE);
  ALTER TABLE "TRANSPORTADORA" MODIFY ("TRAN_ATIVO" NOT NULL ENABLE);
  ALTER TABLE "TRANSPORTADORA" ADD CONSTRAINT "PK_TRAN_ID" PRIMARY KEY ("TRAN_ID");
--------------------------------------------------------
--  DDL for Index IN_FK_COUR_TRAN
--------------------------------------------------------

  CREATE INDEX "IN_FK_COUR_TRAN" ON "COURIER" ("TRAN_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_COUR
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_COUR" ON "COURIER" ("COUR_ID") 
  ;
--------------------------------------------------------
--  Constraints for Table COURIER
--------------------------------------------------------

  ALTER TABLE "COURIER" MODIFY ("COUR_ID" NOT NULL ENABLE);
  ALTER TABLE "COURIER" MODIFY ("COUR_TX_NOME" NOT NULL ENABLE);
  ALTER TABLE "COURIER" MODIFY ("COUR_TX_CPF" NOT NULL ENABLE);
  ALTER TABLE "COURIER" MODIFY ("COUR_TX_RG" NOT NULL ENABLE);
  ALTER TABLE "COURIER" MODIFY ("TRAN_ID" NOT NULL ENABLE);
  ALTER TABLE "COURIER" ADD CONSTRAINT "PK_COUR" PRIMARY KEY ("COUR_ID");
--------------------------------------------------------
--  Ref Constraints for Table COURIER
--------------------------------------------------------

  ALTER TABLE "COURIER" ADD CONSTRAINT "FK_COUR_TRAN" FOREIGN KEY ("TRAN_ID")
	  REFERENCES "TRANSPORTADORA" ("TRAN_ID") ;
--------------------------------------------------------
--  DDL for Index PK_PAIS
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_PAIS" ON "PAIS" ("PAIS_ID") 
  ;
--------------------------------------------------------
--  Constraints for Table PAIS
--------------------------------------------------------

  ALTER TABLE "PAIS" MODIFY ("PAIS_ID" NOT NULL ENABLE);
  ALTER TABLE "PAIS" MODIFY ("PAIS_TX_NOME" NOT NULL ENABLE);
  ALTER TABLE "PAIS" ADD CONSTRAINT "PK_PAIS" PRIMARY KEY ("PAIS_ID");  
--------------------------------------------------------
--  DDL for Index PK_UF
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_UF" ON "UF" ("UF_SIGLA") 
  ;
--------------------------------------------------------
--  Constraints for Table UF
--------------------------------------------------------

  ALTER TABLE "UF" MODIFY ("UF_SIGLA" NOT NULL ENABLE);
  ALTER TABLE "UF" ADD CONSTRAINT "PK_UF" PRIMARY KEY ("UF_SIGLA");
--------------------------------------------------------
--  Constraints for Table MUNICIPIO
--------------------------------------------------------  
  ALTER TABLE "MUNICIPIO" ADD CONSTRAINT "PK_MUNI" PRIMARY KEY ("MUNI_ID");
--------------------------------------------------------
--  Ref Constraints for Table MUNICIPIO
--------------------------------------------------------

	ALTER TABLE "MUNICIPIO" ADD CONSTRAINT "FK_MUNI_UF" FOREIGN KEY ("UF_SIGLA") REFERENCES "UF" ("UF_SIGLA");
--------------------------------------------------------
--  DDL for Index IN_FK_ENCO_TRAN
--------------------------------------------------------

  CREATE INDEX "IN_FK_ENCO_TRAN" ON "ENDERECO_CONTATO" ("TRAN_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_PK_ENCO
--------------------------------------------------------

  CREATE UNIQUE INDEX "IN_PK_ENCO" ON "ENDERECO_CONTATO" ("ENCO_ID") 
  ;
--------------------------------------------------------
--  Constraints for Table ENDERECO_CONTATO
--------------------------------------------------------

  ALTER TABLE "ENDERECO_CONTATO" MODIFY ("ENCO_ID" NOT NULL ENABLE);
  ALTER TABLE "ENDERECO_CONTATO" MODIFY ("ENCO_ID_PAIS" NOT NULL ENABLE);
  ALTER TABLE "ENDERECO_CONTATO" MODIFY ("ENCO_IN_EXCLUIDO" NOT NULL ENABLE);
  ALTER TABLE "ENDERECO_CONTATO" ADD CONSTRAINT "PK_ENCO" PRIMARY KEY ("ENCO_ID");
--------------------------------------------------------
--  Ref Constraints for Table ENDERECO_CONTATO
--------------------------------------------------------

  ALTER TABLE "ENDERECO_CONTATO" ADD CONSTRAINT "FK_ENCO_PAIS" FOREIGN KEY ("ENCO_ID_PAIS")
	  REFERENCES "PAIS" ("PAIS_ID") ;  
  ALTER TABLE "ENDERECO_CONTATO" ADD CONSTRAINT "FK_ENCO_TRAN" FOREIGN KEY ("TRAN_ID")
	  REFERENCES "TRANSPORTADORA" ("TRAN_ID") ;
  ALTER TABLE "ENDERECO_CONTATO" ADD CONSTRAINT "FK_ENCO_MUNI" FOREIGN KEY ("MUNI_ID")
      REFERENCES "MUNICIPIO" ("MUNI_ID");
--------------------------------------------------------
--  DDL for Index IN_FK_ENCA_AUDI
--------------------------------------------------------

  CREATE INDEX "IN_FK_ENCA_AUDI" ON "ENDERECO_CONTATO_AUD" ("AUDI_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_ENCA
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_ENCA" ON "ENDERECO_CONTATO_AUD" ("ENCO_ID", "AUDI_ID") 
  ;
--------------------------------------------------------
--  Constraints for Table ENDERECO_CONTATO_AUD
--------------------------------------------------------

  ALTER TABLE "ENDERECO_CONTATO_AUD" MODIFY ("ENCO_ID" NOT NULL ENABLE);
  ALTER TABLE "ENDERECO_CONTATO_AUD" MODIFY ("AUDI_ID" NOT NULL ENABLE);
  ALTER TABLE "ENDERECO_CONTATO_AUD" ADD CONSTRAINT "PK_ENCA" PRIMARY KEY ("ENCO_ID", "AUDI_ID");
--------------------------------------------------------
--  Ref Constraints for Table ENDERECO_CONTATO_AUD
--------------------------------------------------------

  ALTER TABLE "ENDERECO_CONTATO_AUD" ADD CONSTRAINT "FK_ENCA_AUDI" FOREIGN KEY ("AUDI_ID")
	  REFERENCES "AUDITORIA" ("AUDI_ID") ;
--------------------------------------------------------
--  DDL for Index IN_FK_EMCO_COUR
--------------------------------------------------------

  CREATE INDEX "IN_FK_EMCO_COUR" ON "EMAIL_CONTATO" ("COUR_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_EMCO_TRAN
--------------------------------------------------------

  CREATE INDEX "IN_FK_EMCO_TRAN" ON "EMAIL_CONTATO" ("TRAN_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_PK_EMCO
--------------------------------------------------------

  CREATE UNIQUE INDEX "IN_PK_EMCO" ON "EMAIL_CONTATO" ("EMCO_ID") 
  ;
--------------------------------------------------------
--  Constraints for Table EMAIL_CONTATO
--------------------------------------------------------

  ALTER TABLE "EMAIL_CONTATO" MODIFY ("EMCO_ID" NOT NULL ENABLE);
  ALTER TABLE "EMAIL_CONTATO" MODIFY ("EMCO_TX_EMAIL" NOT NULL ENABLE);
  ALTER TABLE "EMAIL_CONTATO" MODIFY ("EMCO_IN_EXCLUIDO" NOT NULL ENABLE);
  ALTER TABLE "EMAIL_CONTATO" MODIFY ("EMCO_IN_PRINCIPAL" NOT NULL ENABLE);
  ALTER TABLE "EMAIL_CONTATO" ADD CONSTRAINT "PK_EMCO" PRIMARY KEY ("EMCO_ID");
--------------------------------------------------------
--  Ref Constraints for Table EMAIL_CONTATO
--------------------------------------------------------

  ALTER TABLE "EMAIL_CONTATO" ADD CONSTRAINT "FK_EMCO_TRAN" FOREIGN KEY ("TRAN_ID")
	  REFERENCES "TRANSPORTADORA" ("TRAN_ID") ;
  ALTER TABLE "EMAIL_CONTATO" ADD CONSTRAINT "FK_EMCO_COUR" FOREIGN KEY ("COUR_ID")
	  REFERENCES "COURIER" ("COUR_ID") ;
--------------------------------------------------------
--  DDL for Index IN_FK_EMCA_AUDI
--------------------------------------------------------

  CREATE INDEX "IN_FK_EMCA_AUDI" ON "EMAIL_CONTATO_AUD" ("AUDI_ID") 
  ;
--------------------------------------------------------
--  Constraints for Table EMAIL_CONTATO_AUD
--------------------------------------------------------

  ALTER TABLE "EMAIL_CONTATO_AUD" MODIFY ("EMCO_ID" NOT NULL ENABLE);
  ALTER TABLE "EMAIL_CONTATO_AUD" MODIFY ("AUDI_ID" NOT NULL ENABLE);
  ALTER TABLE "EMAIL_CONTATO_AUD" MODIFY ("EMCO_TX_EMAIL" NOT NULL ENABLE);
  ALTER TABLE "EMAIL_CONTATO_AUD" ADD CONSTRAINT "PK_EMCA" PRIMARY KEY ("EMCO_ID", "AUDI_ID");
--------------------------------------------------------
--  Ref Constraints for Table EMAIL_CONTATO_AUD
--------------------------------------------------------

  ALTER TABLE "EMAIL_CONTATO_AUD" ADD CONSTRAINT "FK_EMCA_AUDI" FOREIGN KEY ("AUDI_ID")
	  REFERENCES "AUDITORIA" ("AUDI_ID") ;
--------------------------------------------------------
--  DDL for Index IN_FK_COTE_COUR
--------------------------------------------------------

  CREATE INDEX "IN_FK_COTE_COUR" ON "CONTATO_TELEFONICO" ("COUR_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_COTE_TRAN
--------------------------------------------------------

  CREATE INDEX "IN_FK_COTE_TRAN" ON "CONTATO_TELEFONICO" ("TRAN_ID") 
  ;        
--------------------------------------------------------
--  DDL for Index PK_COTE
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_COTE" ON "CONTATO_TELEFONICO" ("COTE_ID") 
  ;
--------------------------------------------------------
--  Constraints for Table CONTATO_TELEFONICO
--------------------------------------------------------

  ALTER TABLE "CONTATO_TELEFONICO" MODIFY ("COTE_ID" NOT NULL ENABLE);
  ALTER TABLE "CONTATO_TELEFONICO" MODIFY ("COTE_NR_COD_AREA" NOT NULL ENABLE);
  ALTER TABLE "CONTATO_TELEFONICO" MODIFY ("COTE_IN_TIPO" NOT NULL ENABLE);
  ALTER TABLE "CONTATO_TELEFONICO" MODIFY ("COTE_NR_NUMERO" NOT NULL ENABLE);
  ALTER TABLE "CONTATO_TELEFONICO" MODIFY ("COTE_IN_PRINCIPAL" NOT NULL ENABLE);
  ALTER TABLE "CONTATO_TELEFONICO" MODIFY ("COTE_IN_EXCLUIDO" NOT NULL ENABLE);
  ALTER TABLE "CONTATO_TELEFONICO" ADD CONSTRAINT "PK_COTE" PRIMARY KEY ("COTE_ID");
--------------------------------------------------------
--  Ref Constraints for Table CONTATO_TELEFONICO
--------------------------------------------------------

  ALTER TABLE "CONTATO_TELEFONICO" ADD CONSTRAINT "FK_COTE_COUR" FOREIGN KEY ("COUR_ID")
	  REFERENCES "COURIER" ("COUR_ID") ;
  ALTER TABLE "CONTATO_TELEFONICO" ADD CONSTRAINT "FK_COTE_TRAN" FOREIGN KEY ("TRAN_ID")
	  REFERENCES "TRANSPORTADORA" ("TRAN_ID") ;
--------------------------------------------------------
--  DDL for Index IN_FK_COTA_AUDI
--------------------------------------------------------

  CREATE INDEX "IN_FK_COTA_AUDI" ON "CONTATO_TELEFONICO_AUD" ("AUDI_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_COTA
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_COTA" ON "CONTATO_TELEFONICO_AUD" ("COTE_ID", "AUDI_ID") 
  ;
--------------------------------------------------------
--  Constraints for Table CONTATO_TELEFONICO_AUD
--------------------------------------------------------

  ALTER TABLE "CONTATO_TELEFONICO_AUD" MODIFY ("COTE_ID" NOT NULL ENABLE);
  ALTER TABLE "CONTATO_TELEFONICO_AUD" MODIFY ("AUDI_ID" NOT NULL ENABLE);
  ALTER TABLE "CONTATO_TELEFONICO_AUD" ADD CONSTRAINT "PK_COTA" PRIMARY KEY ("COTE_ID", "AUDI_ID");  
--------------------------------------------------------
--  Ref Constraints for Table CONTATO_TELEFONICO_AUD
--------------------------------------------------------

  ALTER TABLE "CONTATO_TELEFONICO_AUD" ADD CONSTRAINT "FK_COTA_AUDI" FOREIGN KEY ("AUDI_ID")
	  REFERENCES "AUDITORIA" ("AUDI_ID") ;  
    
--------------------------------------------------------
--  Constraints for Table STATUS_PEDIDO_TRANSPORTE
--------------------------------------------------------

  ALTER TABLE "STATUS_PEDIDO_TRANSPORTE" MODIFY ("STPT_ID" NOT NULL ENABLE);
  ALTER TABLE "STATUS_PEDIDO_TRANSPORTE" ADD CONSTRAINT "PK_STPT" PRIMARY KEY ("STPT_ID");
  
--------------------------------------------------------
--  DDL for Index IN_FK_PETR_COUR
--------------------------------------------------------

  CREATE INDEX "IN_FK_PETR_COUR" ON "PEDIDO_TRANSPORTE" ("COUR_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_PK_PETR
--------------------------------------------------------

  CREATE UNIQUE INDEX "IN_PK_PETR" ON "PEDIDO_TRANSPORTE" ("PETR_ID") 
  ;
--------------------------------------------------------
--  Constraints for Table PEDIDO_TRANSPORTE
--------------------------------------------------------

  ALTER TABLE "PEDIDO_TRANSPORTE" MODIFY ("PETR_ID" NOT NULL ENABLE);
  ALTER TABLE "PEDIDO_TRANSPORTE" MODIFY ("PETR_DT_ATUALIZACAO" NOT NULL ENABLE);
  ALTER TABLE "PEDIDO_TRANSPORTE" MODIFY ("PETR_HR_PREVISTA_RETIRADA" NOT NULL ENABLE);
  ALTER TABLE "PEDIDO_TRANSPORTE" MODIFY ("TRAN_ID" NOT NULL ENABLE);
  ALTER TABLE "PEDIDO_TRANSPORTE" MODIFY ("STPT_ID" NOT NULL ENABLE);
  ALTER TABLE "PEDIDO_TRANSPORTE" MODIFY ("PETR_IN_STATUS" NOT NULL ENABLE);
  ALTER TABLE "PEDIDO_TRANSPORTE" ADD CONSTRAINT "PK_PETR_ID" PRIMARY KEY ("PETR_ID");	  
	    
--------------------------------------------------------
--  Ref Constraints for Table PEDIDO_TRANSPORTE
--------------------------------------------------------

  ALTER TABLE "PEDIDO_TRANSPORTE" ADD CONSTRAINT "FK_PETR_TRAN" FOREIGN KEY ("TRAN_ID")
	  REFERENCES "TRANSPORTADORA" ("TRAN_ID") ;
  ALTER TABLE "PEDIDO_TRANSPORTE" ADD CONSTRAINT "FK_PETR_STPT" FOREIGN KEY ("STPT_ID")
	  REFERENCES "STATUS_PEDIDO_TRANSPORTE" ("STPT_ID") ;
  ALTER TABLE "PEDIDO_TRANSPORTE" ADD CONSTRAINT "FK_PETR_COUR" FOREIGN KEY ("COUR_ID")
	  REFERENCES "COURIER" ("COUR_ID") ;
    
--------------------------------------------------------
--  DDL for Index IN_FK_ARPT_PETR
--------------------------------------------------------

  CREATE INDEX "IN_FK_ARPT_PETR" ON "ARQUIVO_PEDIDO_TRANSPORTE" ("PETR_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_PK_ARPT_ID
--------------------------------------------------------

  CREATE UNIQUE INDEX "IN_PK_ARPT_ID" ON "ARQUIVO_PEDIDO_TRANSPORTE" ("ARPT_ID") 
  ;
--------------------------------------------------------
--  Constraints for Table ARQUIVO_PEDIDO_TRANSPORTE
--------------------------------------------------------

  ALTER TABLE "ARQUIVO_PEDIDO_TRANSPORTE" MODIFY ("ARPT_ID" NOT NULL ENABLE);
  ALTER TABLE "ARQUIVO_PEDIDO_TRANSPORTE" MODIFY ("ARPT_TX_CAMINHO" NOT NULL ENABLE);
  ALTER TABLE "ARQUIVO_PEDIDO_TRANSPORTE" MODIFY ("PETR_ID" NOT NULL ENABLE);
  ALTER TABLE "ARQUIVO_PEDIDO_TRANSPORTE" ADD CONSTRAINT "PK_ARPT_ID" PRIMARY KEY ("ARPT_ID");
--------------------------------------------------------
--  Ref Constraints for Table ARQUIVO_PEDIDO_TRANSPORTE
--------------------------------------------------------

  ALTER TABLE "ARQUIVO_PEDIDO_TRANSPORTE" ADD CONSTRAINT "FK_ARPT_PETR" FOREIGN KEY ("PETR_ID")
	  REFERENCES "PEDIDO_TRANSPORTE" ("PETR_ID") ;    



  