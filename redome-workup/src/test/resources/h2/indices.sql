--------------------------------------------------------
--  Arquivo criado - Sexta-feira-Abril-05-2019   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Index PK_AUDI
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_AUDI" ON "AUDITORIA" ("AUDI_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_ARPR
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_ARPR" ON "ARQUIVO_PRESCRICAO" ("ARPR_ID")
  ;

--------------------------------------------------------
--  DDL for Index PK_APAW
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_APAW" ON "ARQUIVO_PEDIDO_ADICIONAL_WORKUP" ("APAW_ID");
		
--------------------------------------------------------
--  DDL for Index PK_AVPR
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_AVPR" ON "AVALIACAO_PRESCRICAO" ("AVPR_ID")
  ;
--------------------------------------------------------
--  DDL for Index PK_CONF
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_CONF" ON "CONFIGURACAO" ("CONF_ID")
  ;
--------------------------------------------------------
--  DDL for Index PK_FOCE
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_FOCE" ON "FONTE_CELULAS" ("FOCE_ID")
  ;
--------------------------------------------------------
--  DDL for Index IN_PK_TIAM
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_TIAM" ON "TIPO_AMOSTRA" ("TIAM_ID")
  ;
--------------------------------------------------------
--  DDL for Index PK_TIAP
--------------------------------------------------------

  CREATE UNIQUE INDEX "IN_PK_TIAP" ON "TIPO_AMOSTRA_PRESCRICAO" ("TIAP_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_PRES
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_PRES" ON "PRESCRICAO" ("PRES_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_DIWO_ID
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_DIWO_ID" ON "DISTRIBUICAO_WORKUP" ("DIWO_ID" ASC)
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_TIAP_TIAM
--------------------------------------------------------

  CREATE INDEX "IN_FK_TIAP_TIAM" ON "TIPO_AMOSTRA_PRESCRICAO" ("TIAM_ID") 
  ;

--------------------------------------------------------
--  DDL for Index FK_TIAP_PRES
--------------------------------------------------------

  CREATE INDEX "FK_TIAP_PRES" ON "TIPO_AMOSTRA_PRESCRICAO" ("PRES_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_PRES_FOCE_OP_1
--------------------------------------------------------

  CREATE INDEX "IN_FK_PRES_FOCE_OP_1" ON "PRESCRICAO" ("FOCE_ID_OPCAO_1")
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_PRES_FOCE_OP_2
--------------------------------------------------------

  CREATE INDEX "IN_FK_PRES_FOCE_OP_2" ON "PRESCRICAO" ("FOCE_ID_OPCAO_2")
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_PRES_EVOL
--------------------------------------------------------

  CREATE INDEX "IN_FK_PRES_EVOL" ON "PRESCRICAO" ("EVOL_ID")
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_PRES_MEDI
--------------------------------------------------------

  CREATE INDEX "IN_FK_PRES_MEDI" ON "PRESCRICAO" ("MEDI_ID")
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_PRES_SOLI
--------------------------------------------------------

  CREATE INDEX "IN_FK_PRES_SOLI" ON "PRESCRICAO" ("SOLI_ID")
  ;

--------------------------------------------------------
--  DDL for Index IN_FK_ARPR_PRES
--------------------------------------------------------

  CREATE INDEX "IN_FK_ARPR_PRES" ON "ARQUIVO_PRESCRICAO" ("PRES_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_AVPR_PRES
--------------------------------------------------------

  CREATE INDEX "IN_FK_AVPR_PRES" ON "AVALIACAO_PRESCRICAO" ("PRES_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_AVPR_FOCE
--------------------------------------------------------

  CREATE INDEX "IN_FK_AVPR_FOCE" ON "AVALIACAO_PRESCRICAO" ("FOCE_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_AVPR_USUA
--------------------------------------------------------

  CREATE INDEX "IN_FK_AVPR_USUA" ON "AVALIACAO_PRESCRICAO" ("USUA_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_DIWO_SOLI
--------------------------------------------------------

  CREATE INDEX "IN_FK_DIWO_SOLI" ON "DISTRIBUICAO_WORKUP" ("SOLI_ID" ASC)
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_DIWO_USUA_DISTRIBUIU
--------------------------------------------------------

  CREATE INDEX "IN_FK_DIWO_USUA_DISTRIBUIU" ON "DISTRIBUICAO_WORKUP" ("USUA_ID_DISTRIBUIU" ASC)
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_DIWO_USUA_RECEBEU
--------------------------------------------------------

  CREATE INDEX "IN_FK_DIWO_USUA_RECEBEU" ON "DISTRIBUICAO_WORKUP" ("USUA_ID_RECEBEU" ASC)
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_PEWO_CETR_TRANSP
--------------------------------------------------------

  CREATE INDEX "IN_FK_PEWO_CETR_TRANSP" ON "PEDIDO_WORKUP" ("CETR_ID_TRANSP") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_SOLI_PEWO
--------------------------------------------------------

  CREATE INDEX "IN_FK_SOLI_PEWO" ON "PEDIDO_WORKUP" ("SOLI_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_PEWO
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_PEWO" ON "PEDIDO_WORKUP" ("PEWO_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_PECL_CETR
--------------------------------------------------------

  CREATE INDEX "IN_FK_PECL_CETR" ON "PEDIDO_COLETA" ("CETR_ID_COLETA") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_PECL_USUA
--------------------------------------------------------

  CREATE INDEX "IN_FK_PECL_USUA" ON "PEDIDO_COLETA" ("USUA_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_PECL
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_PECL" ON "PEDIDO_COLETA" ("PECL_ID") 
  ;

--------------------------------------------------------
--  DDL for Index IN_FK_ARPW_PEWO
--------------------------------------------------------  
  
  CREATE INDEX IN_FK_ARPW_PEWO ON ARQUIVO_PEDIDO_WORKUP (PEWO_ID ASC)
  ;
--------------------------------------------------------
--  Constraints for Table AUDITORIA
--------------------------------------------------------

  ALTER TABLE "AUDITORIA" MODIFY ("AUDI_ID" NOT NULL ENABLE);
  ALTER TABLE "AUDITORIA" ADD CONSTRAINT "PK_AUDI" PRIMARY KEY ("AUDI_ID");
--------------------------------------------------------
--  Constraints for Table CONFIGURACAO
--------------------------------------------------------

  ALTER TABLE "CONFIGURACAO" ADD CONSTRAINT "PK_CONF" PRIMARY KEY ("CONF_ID");
--------------------------------------------------------
--  Constraints for Table FONTE_CELULAS
--------------------------------------------------------

  ALTER TABLE "FONTE_CELULAS" ADD CONSTRAINT "PK_FOCE" PRIMARY KEY ("FOCE_ID");
--------------------------------------------------------
--  Constraints for Table TIPO_AMOSTRA
--------------------------------------------------------

  ALTER TABLE "TIPO_AMOSTRA" ADD CONSTRAINT "PK_TIAM_ID" PRIMARY KEY ("TIAM_ID");
--------------------------------------------------------
--  Constraints for Table PRESCRICAO
--------------------------------------------------------

  ALTER TABLE "PRESCRICAO" ADD CONSTRAINT "PK_PRES" PRIMARY KEY ("PRES_ID");
--------------------------------------------------------
--  Constraints for Table DISTRIBUICAO_WORKUP
--------------------------------------------------------

  ALTER TABLE "DISTRIBUICAO_WORKUP" ADD CONSTRAINT "PK_DIWO" PRIMARY KEY ("DIWO_ID");
--------------------------------------------------------
--  Constraints for Table DISTRIBUICAO_WORKUP_AUD
--------------------------------------------------------

  ALTER TABLE "DISTRIBUICAO_WORKUP_AUD" ADD CONSTRAINT "PK_DWAU" PRIMARY KEY ("DIWO_ID", "AUDI_ID");
--------------------------------------------------------
--  Ref Constraints for Table PRESCRICAO
--------------------------------------------------------

  ALTER TABLE "PRESCRICAO" ADD CONSTRAINT "FK_PRES_FOCE_OP_1" FOREIGN KEY ("FOCE_ID_OPCAO_1")
	  REFERENCES "FONTE_CELULAS" ("FOCE_ID");
  ALTER TABLE "PRESCRICAO" ADD CONSTRAINT "FK_PRES_FOCE_OP_2" FOREIGN KEY ("FOCE_ID_OPCAO_2")
	  REFERENCES "FONTE_CELULAS" ("FOCE_ID");

--------------------------------------------------------
--  Constraints for Table TIPO_AMOSTRA_PRESCRICAO
--------------------------------------------------------

  ALTER TABLE "TIPO_AMOSTRA_PRESCRICAO" ADD CONSTRAINT "PK_TIAP" PRIMARY KEY ("TIAP_ID");
--------------------------------------------------------
--  Ref Constraints for Table TIPO_AMOSTRA_PRESCRICAO
--------------------------------------------------------

  ALTER TABLE "TIPO_AMOSTRA_PRESCRICAO" ADD CONSTRAINT "FK_TIAP_TIAM" FOREIGN KEY ("TIAM_ID")
	  REFERENCES "TIPO_AMOSTRA" ("TIAM_ID");

  ALTER TABLE "TIPO_AMOSTRA_PRESCRICAO" ADD CONSTRAINT "FK_TIAP_PRES" FOREIGN KEY ("PRES_ID")
	  REFERENCES "PRESCRICAO" ("PRES_ID");

--------------------------------------------------------
--  Constraints for Table ARQUIVO_PRESCRICAO
--------------------------------------------------------

  ALTER TABLE "ARQUIVO_PRESCRICAO" ADD CONSTRAINT "PK_ARPR" PRIMARY KEY ("ARPR_ID");
--------------------------------------------------------
--  Ref Constraints for Table ARQUIVO_PRESCRICAO
--------------------------------------------------------

  ALTER TABLE "ARQUIVO_PRESCRICAO" ADD CONSTRAINT "FK_ARPR_PRES" FOREIGN KEY ("PRES_ID")
	  REFERENCES "PRESCRICAO" ("PRES_ID");
--------------------------------------------------------
--  Constraints for Table AVALIACAO_PRESCRICAO
--------------------------------------------------------

  ALTER TABLE "AVALIACAO_PRESCRICAO" ADD CONSTRAINT "PK_AVPR" PRIMARY KEY ("AVPR_ID");
--------------------------------------------------------
--  Ref Constraints for Table AVALIACAO_PRESCRICAO
--------------------------------------------------------

  ALTER TABLE "AVALIACAO_PRESCRICAO" ADD CONSTRAINT "FK_AVPR_PRES" FOREIGN KEY ("PRES_ID")
	  REFERENCES "PRESCRICAO" ("PRES_ID");
  ALTER TABLE "AVALIACAO_PRESCRICAO" ADD CONSTRAINT "FK_AVPR_FOCE" FOREIGN KEY ("FOCE_ID")
	  REFERENCES "FONTE_CELULAS" ("FOCE_ID"); 
  
--------------------------------------------------------
--  Ref Constraints for Table DISTRIBUICAO_WORKUP_AUD
--------------------------------------------------------

  ALTER TABLE "DISTRIBUICAO_WORKUP_AUD" ADD CONSTRAINT "FK_DWAU_AUDI" FOREIGN KEY ("AUDI_ID")
	  REFERENCES "AUDITORIA" ("AUDI_ID") ;

--------------------------------------------------------
--  Constraints for Table PEDIDO_WORKUP
--------------------------------------------------------

  ALTER TABLE "PEDIDO_WORKUP" MODIFY ("PEWO_ID" NOT NULL ENABLE);
  ALTER TABLE "PEDIDO_WORKUP" MODIFY ("SOLI_ID" NOT NULL ENABLE);
  ALTER TABLE "PEDIDO_WORKUP" MODIFY ("PEWO_DT_CRIACAO" NOT NULL ENABLE);
  ALTER TABLE "PEDIDO_WORKUP" MODIFY ("PEWO_IN_TIPO" NOT NULL ENABLE);
  ALTER TABLE "PEDIDO_WORKUP" MODIFY ("PEWO_IN_STATUS" NOT NULL ENABLE);
  ALTER TABLE "PEDIDO_WORKUP" ADD CONSTRAINT "PK_PEWO" PRIMARY KEY ("PEWO_ID");
  

--------------------------------------------------------
--  Constraints for Table PEDIDO_COLETA
--------------------------------------------------------

  ALTER TABLE "PEDIDO_COLETA" MODIFY ("PECL_ID" NOT NULL ENABLE);
  ALTER TABLE "PEDIDO_COLETA" MODIFY ("PECL_DT_CRIACAO" NOT NULL ENABLE);
  ALTER TABLE "PEDIDO_COLETA" MODIFY ("SOLI_ID" NOT NULL ENABLE);
  ALTER TABLE "PEDIDO_COLETA" ADD CONSTRAINT "PK_PECL" PRIMARY KEY ("PECL_ID");

    
--------------------------------------------------------
--  Constraints for Table ARQUIVO_PEDIDO_WORKUP
--------------------------------------------------------

  ALTER TABLE "ARQUIVO_PEDIDO_WORKUP" ADD CONSTRAINT "PK_ARPW" PRIMARY KEY ("ARPW_ID");


--------------------------------------------------------
--  Ref Constraints for Table ARQUIVO_PEDIDO_WORKUP
--------------------------------------------------------

  ALTER TABLE "ARQUIVO_PEDIDO_WORKUP" ADD CONSTRAINT FK_ARPW_PEWO FOREIGN KEY ("PEWO_ID")
	REFERENCES "PEDIDO_WORKUP" ("PEWO_ID");
	
	
	
--------------------------------------------------------
--  DDL for Index IN_FK_REWO_PEWO
--------------------------------------------------------

  CREATE INDEX "IN_FK_REWO_PEWO" ON "RESULTADO_WORKUP" ("PEWO_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_REWO_FOCE
--------------------------------------------------------

  CREATE INDEX "IN_FK_REWO_FOCE" ON "RESULTADO_WORKUP" ("FOCE_ID") 
  ;  
--------------------------------------------------------
--  DDL for Index PK_REWO
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_REWO" ON "RESULTADO_WORKUP" ("REWO_ID") 
  ;
--------------------------------------------------------
--  Constraints for Table RESULTADO_WORKUP
--------------------------------------------------------

  ALTER TABLE "RESULTADO_WORKUP" MODIFY ("REWO_ID" NOT NULL ENABLE);
  ALTER TABLE "RESULTADO_WORKUP" MODIFY ("REWO_DT_CRIACAO" NOT NULL ENABLE);
  ALTER TABLE "RESULTADO_WORKUP" MODIFY ("PEWO_ID" NOT NULL ENABLE);  
  ALTER TABLE "RESULTADO_WORKUP" ADD CONSTRAINT "PK_REWO" PRIMARY KEY ("REWO_ID");  
--------------------------------------------------------
--  Ref Constraints for Table RESULTADO_WORKUP
--------------------------------------------------------

  ALTER TABLE "RESULTADO_WORKUP" ADD CONSTRAINT "FK_REWO_PEWO" FOREIGN KEY ("PEWO_ID")
	  REFERENCES "PEDIDO_WORKUP" ("PEWO_ID") ;
  ALTER TABLE "RESULTADO_WORKUP" ADD CONSTRAINT "FK_REWO_FOCE" FOREIGN KEY ("FOCE_ID")
	  REFERENCES "FONTE_CELULAS" ("FOCE_ID") ;	  

--------------------------------------------------------
--  DDL for Index IN_FK_ARRW_REWO
--------------------------------------------------------

  CREATE INDEX "IN_FK_ARRW_REWO" ON "ARQUIVO_RESULTADO_WORKUP" ("REWO_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_ARRW_PEAW
--------------------------------------------------------

  CREATE INDEX "IN_FK_ARRW_PEAW" ON "PEDIDO_ADICIONAL_WORKUP" ("PEWO_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_ARRW
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_ARRW" ON "ARQUIVO_RESULTADO_WORKUP" ("ARRW_ID") 
  ;
--------------------------------------------------------
--  Constraints for Table ARQUIVO_RESULTADO_WORKUP
--------------------------------------------------------

  ALTER TABLE "ARQUIVO_RESULTADO_WORKUP" MODIFY ("ARRW_ID" NOT NULL ENABLE);
  ALTER TABLE "ARQUIVO_RESULTADO_WORKUP" MODIFY ("ARRW_TX_CAMINHO" NOT NULL ENABLE);
  ALTER TABLE "ARQUIVO_RESULTADO_WORKUP" MODIFY ("REWO_ID" NOT NULL ENABLE);
  ALTER TABLE "ARQUIVO_RESULTADO_WORKUP" ADD CONSTRAINT "PK_ARRW" PRIMARY KEY ("ARRW_ID");
  
--------------------------------------------------------
--  Ref Constraints for Table ARQUIVO_RESULTADO_WORKUP
--------------------------------------------------------

  ALTER TABLE "ARQUIVO_RESULTADO_WORKUP" ADD CONSTRAINT "FK_ARRW_REWO" FOREIGN KEY ("REWO_ID")
	  REFERENCES "RESULTADO_WORKUP" ("REWO_ID") ;  
  

--------------------------------------------------------
--  DDL for Index PK_STPL
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_STPL" ON "STATUS_PEDIDO_LOGISTICA" ("STPL_ID") 
  ;
--------------------------------------------------------
--  Constraints for Table STATUS_PEDIDO_LOGISTICA
--------------------------------------------------------

  ALTER TABLE "STATUS_PEDIDO_LOGISTICA" MODIFY ("STPL_ID" NOT NULL ENABLE);
  ALTER TABLE "STATUS_PEDIDO_LOGISTICA" MODIFY ("STPL_TX_DESCRICAO" NOT NULL ENABLE);
  ALTER TABLE "STATUS_PEDIDO_LOGISTICA" ADD CONSTRAINT "PK_STPL" PRIMARY KEY ("STPL_ID");  
  
  
--------------------------------------------------------
--  DDL for Index IN_FK_PELO_PEWO
--------------------------------------------------------

  CREATE INDEX "IN_FK_PELO_PEWO" ON "PEDIDO_LOGISTICA" ("PEWO_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_PELO_STPL
--------------------------------------------------------

  CREATE INDEX "IN_FK_PELO_STPL" ON "PEDIDO_LOGISTICA" ("STPL_ID") 
  ;  
  
--------------------------------------------------------
--  DDL for Index IN_FK_PELO_USUA
--------------------------------------------------------

  CREATE INDEX "IN_FK_PELO_USUA" ON "PEDIDO_LOGISTICA" ("USUA_ID_RESPONSAVEL") 
  ;
--------------------------------------------------------
--  DDL for Index PK_PELO
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_PELO" ON "PEDIDO_LOGISTICA" ("PELO_ID") 
  ;
--------------------------------------------------------
--  Constraints for Table PEDIDO_LOGISTICA
--------------------------------------------------------

  ALTER TABLE "PEDIDO_LOGISTICA" MODIFY ("PELO_ID" NOT NULL ENABLE);
  ALTER TABLE "PEDIDO_LOGISTICA" MODIFY ("PELO_DT_CRIACAO" NOT NULL ENABLE);
  ALTER TABLE "PEDIDO_LOGISTICA" MODIFY ("PELO_IN_TIPO" NOT NULL ENABLE);
  ALTER TABLE "PEDIDO_LOGISTICA" MODIFY ("STPL_ID" NOT NULL ENABLE);
  ALTER TABLE "PEDIDO_LOGISTICA" ADD CONSTRAINT "PK_PELO" PRIMARY KEY ("PELO_ID");
  
--------------------------------------------------------
--  Ref Constraints for Table PEDIDO_LOGISTICA
--------------------------------------------------------

  ALTER TABLE "PEDIDO_LOGISTICA" ADD CONSTRAINT "FK_PELO_PEWO" FOREIGN KEY ("PEWO_ID")
	  REFERENCES "PEDIDO_WORKUP" ("PEWO_ID") ;  
  ALTER TABLE "PEDIDO_LOGISTICA" ADD CONSTRAINT "FK_PELO_STPL" FOREIGN KEY ("STPL_ID")
	  REFERENCES "STATUS_PEDIDO_LOGISTICA" ("STPL_ID") ;

--------------------------------------------------------
--  DDL for Index IN_FK_TRTE_PELO
--------------------------------------------------------

  CREATE INDEX "IN_FK_TRTE_PELO" ON "TRANSPORTE_TERRESTRE" ("PELO_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_TRTE
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_TRTE" ON "TRANSPORTE_TERRESTRE" ("TRTE_ID") 
  ;
--------------------------------------------------------
--  Constraints for Table TRANSPORTE_TERRESTRE
--------------------------------------------------------

  ALTER TABLE "TRANSPORTE_TERRESTRE" MODIFY ("TRTE_ID" NOT NULL ENABLE);
  ALTER TABLE "TRANSPORTE_TERRESTRE" MODIFY ("TRTE_DT_DATA" NOT NULL ENABLE);
  ALTER TABLE "TRANSPORTE_TERRESTRE" MODIFY ("TRTE_TX_ORIGEM" NOT NULL ENABLE);
  ALTER TABLE "TRANSPORTE_TERRESTRE" MODIFY ("TRTE_TX_DESTINO" NOT NULL ENABLE);
  ALTER TABLE "TRANSPORTE_TERRESTRE" MODIFY ("TRTE_TX_OBJETO_TRANSPORTE" NOT NULL ENABLE);
  ALTER TABLE "TRANSPORTE_TERRESTRE" MODIFY ("PELO_ID" NOT NULL ENABLE);
  ALTER TABLE "TRANSPORTE_TERRESTRE" ADD CONSTRAINT "PK_TRTE" PRIMARY KEY ("TRTE_ID");  
  
--------------------------------------------------------
--  Ref Constraints for Table TRANSPORTE_TERRESTRE
--------------------------------------------------------

  ALTER TABLE "TRANSPORTE_TERRESTRE" ADD CONSTRAINT "FK_TRTE_PELO" FOREIGN KEY ("PELO_ID")
	  REFERENCES "PEDIDO_LOGISTICA" ("PELO_ID") ;
	  
--------------------------------------------------------
--  DDL for Index IN_FK_ARVL_PELO
--------------------------------------------------------

  CREATE INDEX "IN_FK_ARVL_PELO" ON "ARQUIVO_VOUCHER_LOGISTICA" ("PELO_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_ARVL
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_ARVL" ON "ARQUIVO_VOUCHER_LOGISTICA" ("ARVL_ID") 
  ;
--------------------------------------------------------
--  Constraints for Table ARQUIVO_VOUCHER_LOGISTICA
--------------------------------------------------------

  ALTER TABLE "ARQUIVO_VOUCHER_LOGISTICA" MODIFY ("ARVL_ID" NOT NULL ENABLE);
  ALTER TABLE "ARQUIVO_VOUCHER_LOGISTICA" MODIFY ("ARVL_TX_CAMINHO" NOT NULL ENABLE);
  ALTER TABLE "ARQUIVO_VOUCHER_LOGISTICA" MODIFY ("ARVL_NR_TIPO" NOT NULL ENABLE);
  ALTER TABLE "ARQUIVO_VOUCHER_LOGISTICA" MODIFY ("PELO_ID" NOT NULL ENABLE);
  ALTER TABLE "ARQUIVO_VOUCHER_LOGISTICA" ADD CONSTRAINT "PK_ARVL" PRIMARY KEY ("ARVL_ID");
  
--------------------------------------------------------
--  Ref Constraints for Table ARQUIVO_VOUCHER_LOGISTICA
--------------------------------------------------------

  ALTER TABLE "ARQUIVO_VOUCHER_LOGISTICA" ADD CONSTRAINT "FK_ARVL_PELO" FOREIGN KEY ("PELO_ID")
	  REFERENCES "PEDIDO_LOGISTICA" ("PELO_ID") ;
	  

--------------------------------------------------------
--  DDL for Index IN_FK_PELA_AUDI
--------------------------------------------------------

  CREATE INDEX "IN_FK_PELA_AUDI" ON "PEDIDO_LOGISTICA_AUD" ("AUDI_ID") 
  ;
  
--------------------------------------------------------
--  DDL for Index PK_PELA
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_PELA" ON "PEDIDO_LOGISTICA_AUD" ("PELO_ID", "AUDI_ID") 
  ;
--------------------------------------------------------
--  Constraints for Table PEDIDO_LOGISTICA_AUD
--------------------------------------------------------

  ALTER TABLE "PEDIDO_LOGISTICA_AUD" MODIFY ("PELO_ID" NOT NULL ENABLE);
  ALTER TABLE "PEDIDO_LOGISTICA_AUD" MODIFY ("AUDI_ID" NOT NULL ENABLE);
  ALTER TABLE "PEDIDO_LOGISTICA_AUD" ADD CONSTRAINT "PK_PELA" PRIMARY KEY ("PELO_ID", "AUDI_ID");
--------------------------------------------------------
--  Ref Constraints for Table PEDIDO_LOGISTICA_AUD
--------------------------------------------------------

  ALTER TABLE "PEDIDO_LOGISTICA_AUD" ADD CONSTRAINT "FK_PELA_AUDI" FOREIGN KEY ("AUDI_ID")
	  REFERENCES "AUDITORIA" ("AUDI_ID") ;
	  
--------------------------------------------------------
--  DDL for Index IN_FK_TRTA_AUDI
--------------------------------------------------------

  CREATE INDEX "IN_FK_TRTA_AUDI" ON "TRANSPORTE_TERRESTRE_AUD" ("AUDI_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_TRTA
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_TRTA" ON "TRANSPORTE_TERRESTRE_AUD" ("TRTE_ID", "AUDI_ID") 
  ;
--------------------------------------------------------
--  Constraints for Table TRANSPORTE_TERRESTRE_AUD
--------------------------------------------------------

  ALTER TABLE "TRANSPORTE_TERRESTRE_AUD" MODIFY ("TRTE_ID" NOT NULL ENABLE);
  ALTER TABLE "TRANSPORTE_TERRESTRE_AUD" MODIFY ("AUDI_ID" NOT NULL ENABLE);
  ALTER TABLE "TRANSPORTE_TERRESTRE_AUD" ADD CONSTRAINT "PK_TRTA" PRIMARY KEY ("TRTE_ID", "AUDI_ID");
--------------------------------------------------------
--  Ref Constraints for Table TRANSPORTE_TERRESTRE_AUD
--------------------------------------------------------

  ALTER TABLE "TRANSPORTE_TERRESTRE_AUD" ADD CONSTRAINT "FK_TRTA_AUDI" FOREIGN KEY ("AUDI_ID")
	  REFERENCES "AUDITORIA" ("AUDI_ID") ;
	  
--------------------------------------------------------
--  DDL for Index IN_FK_AVLA_AUDI
--------------------------------------------------------

  CREATE INDEX "IN_FK_AVLA_AUDI" ON "ARQUIVO_VOUCHER_LOGISTICA_AUD" ("AUDI_ID") 
  ;
  
--------------------------------------------------------
--  DDL for Index PK_AVLA
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_AVLA" ON "ARQUIVO_VOUCHER_LOGISTICA_AUD" ("ARVL_ID", "AUDI_ID") 
  ;
--------------------------------------------------------
--  Constraints for Table ARQUIVO_VOUCHER_LOGISTICA_AUD
--------------------------------------------------------

  ALTER TABLE "ARQUIVO_VOUCHER_LOGISTICA_AUD" MODIFY ("ARVL_ID" NOT NULL ENABLE);
  ALTER TABLE "ARQUIVO_VOUCHER_LOGISTICA_AUD" MODIFY ("AUDI_ID" NOT NULL ENABLE);
  ALTER TABLE "ARQUIVO_VOUCHER_LOGISTICA_AUD" ADD CONSTRAINT "PK_AVLA" PRIMARY KEY ("ARVL_ID", "AUDI_ID");
--------------------------------------------------------
--  Ref Constraints for Table ARQUIVO_VOUCHER_LOGISTICA_AUD
--------------------------------------------------------

  ALTER TABLE "ARQUIVO_VOUCHER_LOGISTICA_AUD" ADD CONSTRAINT "FK_AVLA_AUDI" FOREIGN KEY ("AUDI_ID")
	  REFERENCES "AUDITORIA" ("AUDI_ID") ;
    

--------------------------------------------------------
--  DDL for Index IN_FK_AVRW_REWO
--------------------------------------------------------

  CREATE INDEX "IN_FK_AVRW_REWO" ON "AVALIACAO_RESULTADO_WORKUP" ("REWO_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_AVRW
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_AVRW" ON "AVALIACAO_RESULTADO_WORKUP" ("AVRW_ID") 
  ;
--------------------------------------------------------
--  Constraints for Table AVALIACAO_RESULTADO_WORKUP
--------------------------------------------------------

  ALTER TABLE "AVALIACAO_RESULTADO_WORKUP" MODIFY ("AVRW_ID" NOT NULL ENABLE);
  ALTER TABLE "AVALIACAO_RESULTADO_WORKUP" MODIFY ("AVRW_DT_CRIACAO" NOT NULL ENABLE);
  ALTER TABLE "AVALIACAO_RESULTADO_WORKUP" MODIFY ("REWO_ID" NOT NULL ENABLE);
  ALTER TABLE "AVALIACAO_RESULTADO_WORKUP" ADD CONSTRAINT "PK_AVRW" PRIMARY KEY ("AVRW_ID");  
--------------------------------------------------------
--  Ref Constraints for Table AVALIACAO_RESULTADO_WORKUP
--------------------------------------------------------

  ALTER TABLE "AVALIACAO_RESULTADO_WORKUP" ADD CONSTRAINT "FK_AVRW_REWO" FOREIGN KEY ("REWO_ID")
	  REFERENCES "RESULTADO_WORKUP" ("REWO_ID") ;

--------------------------------------------------------
--  DDL for Index IN_FK_PEAW_REWO
--------------------------------------------------------

  CREATE INDEX "IN_FK_PEAW_REWO" ON "RESULTADO_WORKUP" ("REWO_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_PEAW
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_PEAW" ON "PEDIDO_ADICIONAL_WORKUP" ("PEAW_ID") 
  ;          
--------------------------------------------------------
--  Constraints for Table PEDIDO_ADICIONAL_WORKUP
--------------------------------------------------------

  ALTER TABLE "PEDIDO_ADICIONAL_WORKUP" MODIFY ("PEAW_ID" NOT NULL ENABLE);
  ALTER TABLE "PEDIDO_ADICIONAL_WORKUP" MODIFY ("PEAW_TX_DESCRICAO" NOT NULL ENABLE);
  ALTER TABLE "PEDIDO_ADICIONAL_WORKUP" MODIFY ("PEAW_DT_CRIACAO" NOT NULL ENABLE);
  ALTER TABLE "PEDIDO_ADICIONAL_WORKUP" ADD CONSTRAINT "PK_PEAW" PRIMARY KEY ("PEAW_ID");
--------------------------------------------------------
--  Ref Constraints for Table PEDIDO_ADICIONAL_WORKUP
--------------------------------------------------------
  
--------------------------------------------------------
--  DDL for Index IN_FK_AVPC_AVRW
--------------------------------------------------------

  CREATE INDEX "IN_FK_AVPC_AVRW" ON "AVALIACAO_PEDIDO_COLETA" ("AVRW_ID") 
  ;  
--------------------------------------------------------
--  DDL for Index PK_AVPC
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_AVPC" ON "AVALIACAO_PEDIDO_COLETA" ("AVPC_ID") 
  ;
--------------------------------------------------------
--  Constraints for Table AVALIACAO_PEDIDO_COLETA
--------------------------------------------------------

  ALTER TABLE "AVALIACAO_PEDIDO_COLETA" MODIFY ("AVPC_ID" NOT NULL ENABLE);
  ALTER TABLE "AVALIACAO_PEDIDO_COLETA" MODIFY ("AVPC_DT_CRIACAO" NOT NULL ENABLE);
  ALTER TABLE "AVALIACAO_PEDIDO_COLETA" MODIFY ("AVRW_ID" NOT NULL ENABLE);
  ALTER TABLE "AVALIACAO_PEDIDO_COLETA" ADD CONSTRAINT "PK_AVPC" PRIMARY KEY ("AVPC_ID");  
--------------------------------------------------------
--  Ref Constraints for Table AVALIACAO_PEDIDO_COLETA
--------------------------------------------------------

  ALTER TABLE "AVALIACAO_PEDIDO_COLETA" ADD CONSTRAINT "FK_AVPC_ARRW" FOREIGN KEY ("AVRW_ID")
	  REFERENCES "AVALIACAO_RESULTADO_WORKUP" ("AVRW_ID") ;
  
  