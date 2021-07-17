--------------------------------------------------------
--  Arquivo criado - Sexta-feira-Abril-05-2019   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Index CETU_ID
--------------------------------------------------------

  CREATE UNIQUE INDEX "CETU_ID" ON "CENTRO_TRANSPLANTE_USUARIO" ("CETU_ID") 
  ;
--------------------------------------------------------
--  DDL for Index FK_PRCM_USUA
--------------------------------------------------------

  CREATE INDEX "FK_PRCM_USUA" ON "PRE_CADASTRO_MEDICO" ("USUA_ID") 
  ;
--------------------------------------------------------
--  DDL for Index HIBU_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "HIBU_PK" ON "HISTORICO_BUSCA" ("HIBU_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_BACO_TX_DNE
--------------------------------------------------------

  CREATE INDEX "IN_BACO_TX_DNE" ON "BAIRRO_CORREIO" ("BACO_TX_DNE") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_AREV_EVOL
--------------------------------------------------------

  CREATE INDEX "IN_FK_AREV_EVOL" ON "ARQUIVO_EVOLUCAO" ("EVOL_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_AREX_EXAM
--------------------------------------------------------

  CREATE INDEX "IN_FK_AREX_EXAM" ON "ARQUIVO_EXAME" ("EXAM_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_ARPI_PEID
--------------------------------------------------------

  CREATE INDEX "IN_FK_ARPI_PEID" ON "ARQUIVO_PEDIDO_IDM" ("PEID_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_ARPR_PRES
--------------------------------------------------------

  CREATE INDEX "IN_FK_ARPR_PRES" ON "ARQUIVO_PRESCRICAO" ("PRES_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_ARPT_PETR
--------------------------------------------------------

  CREATE INDEX "IN_FK_ARPT_PETR" ON "ARQUIVO_PEDIDO_TRANSPORTE" ("PETR_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_ARRI_BUSC
--------------------------------------------------------

  CREATE INDEX "IN_FK_ARRI_BUSC" ON "ARQUIVO_RELAT_INTERNACIONAL" ("BUSC_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_ARRW_REWO
--------------------------------------------------------

  CREATE INDEX "IN_FK_ARRW_REWO" ON "ARQUIVO_RESULTADO_WORKUP" ("REWO_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_ARVL_PELO
--------------------------------------------------------

  CREATE INDEX "IN_FK_ARVL_PELO" ON "ARQUIVO_VOUCHER_LOGISTICA" ("PELO_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_AVAL_CETR
--------------------------------------------------------

  CREATE INDEX "IN_FK_AVAL_CETR" ON "AVALIACAO" ("CETR_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_AVAL_PACI
--------------------------------------------------------

  CREATE INDEX "IN_FK_AVAL_PACI" ON "AVALIACAO" ("PACI_NR_RMR") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_AVAU_AUDI
--------------------------------------------------------

  CREATE INDEX "IN_FK_AVAU_AUDI" ON "AVALIACAO_AUD" ("AVAL_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_AVCT_PACI
--------------------------------------------------------

  CREATE INDEX "IN_FK_AVCT_PACI" ON "AVALIACAO_CAMARA_TECNICA" ("PACI_NR_RMR") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_AVCT_STCT
--------------------------------------------------------

  CREATE INDEX "IN_FK_AVCT_STCT" ON "AVALIACAO_CAMARA_TECNICA" ("STCT_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_AVLA_AUDI
--------------------------------------------------------

  CREATE INDEX "IN_FK_AVLA_AUDI" ON "ARQUIVO_VOUCHER_LOGISTICA_AUD" ("AUDI_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_AVPC_USUA
--------------------------------------------------------

  CREATE INDEX "IN_FK_AVPC_USUA" ON "AVALIACAO_PEDIDO_COLETA" ("USUA_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_AVPR_FOCE
--------------------------------------------------------

  CREATE INDEX "IN_FK_AVPR_FOCE" ON "AVALIACAO_PRESCRICAO" ("FOCE_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_AVPR_PRES
--------------------------------------------------------

  CREATE INDEX "IN_FK_AVPR_PRES" ON "AVALIACAO_PRESCRICAO" ("PRES_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_AVPR_USUA
--------------------------------------------------------

  CREATE INDEX "IN_FK_AVPR_USUA" ON "AVALIACAO_PRESCRICAO" ("USUA_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_AVRW_REWO
--------------------------------------------------------

  CREATE INDEX "IN_FK_AVRW_REWO" ON "AVALIACAO_RESULTADO_WORKUP" ("REWO_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_AVRW_USUA
--------------------------------------------------------

  CREATE INDEX "IN_FK_AVRW_USUA" ON "AVALIACAO_RESULTADO_WORKUP" ("USUA_ID_RESPONSAVEL") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_AVWD_REWO
--------------------------------------------------------

  CREATE INDEX "IN_FK_AVWD_REWO" ON "AVALIACAO_WORKUP_DOADOR" ("REWO_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_AVWD_USUA
--------------------------------------------------------

  CREATE INDEX "IN_FK_AVWD_USUA" ON "AVALIACAO_WORKUP_DOADOR" ("USUA_ID_RESPONSAVEL") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_BACO_LOCC
--------------------------------------------------------

  CREATE INDEX "IN_FK_BACO_LOCC" ON "BAIRRO_CORREIO" ("LOCC_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_BUCH_BUSC
--------------------------------------------------------

  CREATE INDEX "IN_FK_BUCH_BUSC" ON "BUSCA_CHECKLIST" ("BUSC_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_BUCH_TIBH
--------------------------------------------------------

  CREATE INDEX "IN_FK_BUCH_TIBH" ON "BUSCA_CHECKLIST" ("TIBC_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_BUCH_USUA
--------------------------------------------------------

  CREATE INDEX "IN_FK_BUCH_USUA" ON "BUSCA_CHECKLIST" ("USUA_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_BUSC
--------------------------------------------------------

  CREATE INDEX "IN_FK_BUSC" ON "CANCELAMENTO_BUSCA" ("BUSC_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_BUSC_CETR_TRANSPLANTE
--------------------------------------------------------

  CREATE INDEX "IN_FK_BUSC_CETR_TRANSPLANTE" ON "BUSCA" ("CETR_ID_CENTRO_TRANSPLANTE") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_BUSC_PACI
--------------------------------------------------------

  CREATE INDEX "IN_FK_BUSC_PACI" ON "BUSCA" ("PACI_NR_RMR") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_BUSC_STBU
--------------------------------------------------------

  CREATE INDEX "IN_FK_BUSC_STBU" ON "BUSCA" ("STBU_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_BUSC_USU
--------------------------------------------------------

  CREATE INDEX "IN_FK_BUSC_USU" ON "BUSCA" ("USUA_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_CACH_TIPC
--------------------------------------------------------

  CREATE INDEX "IN_FK_CACH_TIPC" ON "CATEGORIA_CHECKLIST" ("CACH_TX_NM_CATEGORIA") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_CETR_LABO
--------------------------------------------------------

  CREATE INDEX "IN_FK_CETR_LABO" ON "CENTRO_TRANSPLANTE" ("LABO_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_CIID_CID
--------------------------------------------------------

  CREATE INDEX "IN_FK_CIID_CID" ON "CID_IDIOMA" ("CID_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_CIID_IDIO
--------------------------------------------------------

  CREATE INDEX "IN_FK_CIID_IDIO" ON "CID_IDIOMA" ("IDIO_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_COMA_MATC
--------------------------------------------------------

  CREATE INDEX "IN_FK_COMA_MATC" ON "COMENTARIO_MATCH" ("MATC_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_COTA_AUDI
--------------------------------------------------------

  CREATE INDEX "IN_FK_COTA_AUDI" ON "CONTATO_TELEFONICO_AUD" ("AUDI_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_COTE_CETR
--------------------------------------------------------

  CREATE INDEX "IN_FK_COTE_CETR" ON "CONTATO_TELEFONICO" ("CETR_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_COTE_COUR
--------------------------------------------------------

  CREATE INDEX "IN_FK_COTE_COUR" ON "CONTATO_TELEFONICO" ("COTE_TX_NOME") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_COTE_DOAD
--------------------------------------------------------

  CREATE INDEX "IN_FK_COTE_DOAD" ON "CONTATO_TELEFONICO" ("DOAD_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_COTE_MEDI
--------------------------------------------------------

  CREATE INDEX "IN_FK_COTE_MEDI" ON "CONTATO_TELEFONICO" ("MEDI_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_COTE_PACI
--------------------------------------------------------

  CREATE INDEX "IN_FK_COTE_PACI" ON "CONTATO_TELEFONICO" ("PACI_NR_RMR") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_DIAU_AUDI
--------------------------------------------------------

  CREATE INDEX "IN_FK_DIAU_AUDI" ON "DIAGNOSTICO_AUD" ("AUDI_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_DIBU_BUSC
--------------------------------------------------------

  CREATE INDEX "IN_FK_DIBU_BUSC" ON "DIALOGO_BUSCA" ("BUSC_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_DIBU_USUA
--------------------------------------------------------

  CREATE INDEX "IN_FK_DIBU_USUA" ON "DIALOGO_BUSCA" ("USUA_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_DICC_CETR
--------------------------------------------------------

  CREATE INDEX "IN_FK_DICC_CETR" ON "DISPONIBILIDADE_CENTRO_COLETA" ("CETR_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_DICC_PEWO
--------------------------------------------------------

  CREATE INDEX "IN_FK_DICC_PEWO" ON "DISPONIBILIDADE_CENTRO_COLETA" ("PEWO_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_DISA_AUDI
--------------------------------------------------------

  CREATE INDEX "IN_FK_DISA_AUDI" ON "DISPONIBILIDADE_AUD" ("AUDI_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_DISP_PECL
--------------------------------------------------------

  CREATE INDEX "IN_FK_DISP_PECL" ON "DISPONIBILIDADE" ("PECL_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_DNNM_LOCU
--------------------------------------------------------

  CREATE INDEX "IN_FK_DNNM_LOCU" ON "VALOR_DNA_NMDP_VALIDO" ("LOCU_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_DNNM_LOCU1
--------------------------------------------------------

  CREATE INDEX "IN_FK_DNNM_LOCU1" ON "COPIA_VALOR_DNA_NMDP" ("LOCU_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_DOAD_BASC
--------------------------------------------------------

  CREATE INDEX "IN_FK_DOAD_BASC" ON "DOADOR" ("BASC_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_DOAD_ETNI
--------------------------------------------------------

  CREATE INDEX "IN_FK_DOAD_ETNI" ON "DOADOR" ("ETNI_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_DOAD_HEEN
--------------------------------------------------------

  CREATE INDEX "IN_FK_DOAD_HEEN" ON "DOADOR" ("HEEN_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_DOAD_LABO
--------------------------------------------------------

  CREATE INDEX "IN_FK_DOAD_LABO" ON "DOADOR" ("LABO_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_DOAD_MOSD
--------------------------------------------------------

  CREATE INDEX "IN_FK_DOAD_MOSD" ON "DOADOR" ("MOSD_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_DOAD_PAIS
--------------------------------------------------------

  CREATE INDEX "IN_FK_DOAD_PAIS" ON "DOADOR" ("PAIS_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_DOAD_RACA
--------------------------------------------------------

  CREATE INDEX "IN_FK_DOAD_RACA" ON "DOADOR" ("RACA_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_DOAD_REGI_ORIGEM
--------------------------------------------------------

  CREATE INDEX "IN_FK_DOAD_REGI_ORIGEM" ON "DOADOR" ("REGI_ID_ORIGEM") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_DOAD_REGI_PAGAMENTO
--------------------------------------------------------

  CREATE INDEX "IN_FK_DOAD_REGI_PAGAMENTO" ON "DOADOR" ("REGI_ID_PAGAMENTO") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_DOAD_STDO
--------------------------------------------------------

  CREATE INDEX "IN_FK_DOAD_STDO" ON "DOADOR" ("STDO_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_DOAD_UF
--------------------------------------------------------

  CREATE INDEX "IN_FK_DOAD_UF" ON "DOADOR" ("UF_SIGLA") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_DOAD_USUA
--------------------------------------------------------

  CREATE INDEX "IN_FK_DOAD_USUA" ON "DOADOR" ("USUA_ID") 
  ;
  --------------------------------------------------------
--  DDL for Index IN_FK_DOAD_ESCI
--------------------------------------------------------

  CREATE INDEX "IN_FK_DOAD_ESCI" ON "DOADOR" ("ESCI_ID") 
  ;  
  
--------------------------------------------------------
--  DDL for Index IN_FK_DOAU_AUD
--------------------------------------------------------

  CREATE INDEX "IN_FK_DOAU_AUD" ON "DOADOR_AUD" ("AUDI_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_EMCA_AUDI
--------------------------------------------------------

  CREATE INDEX "IN_FK_EMCA_AUDI" ON "EMAIL_CONTATO_AUD" ("AUDI_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_EMCO_DOAD
--------------------------------------------------------

  CREATE INDEX "IN_FK_EMCO_DOAD" ON "EMAIL_CONTATO" ("DOAD_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_EMCO_LABO
--------------------------------------------------------

  CREATE INDEX "IN_FK_EMCO_LABO" ON "EMAIL_CONTATO" ("LABO_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_EMCO_CETR
--------------------------------------------------------  
  
  CREATE INDEX "IN_FK_EMCO_CETR" ON "EMAIL_CONTATO" ("CETR_ID")
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_ENCA_AUDI
--------------------------------------------------------

  CREATE INDEX "IN_FK_ENCA_AUDI" ON "ENDERECO_CONTATO_AUD" ("AUDI_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_ENCO_CETR
--------------------------------------------------------

  CREATE INDEX "IN_FK_ENCO_CETR" ON "ENDERECO_CONTATO" ("CETR_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_ENCO_DOAD
--------------------------------------------------------

  CREATE INDEX "IN_FK_ENCO_DOAD" ON "ENDERECO_CONTATO" ("DOAD_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_ENCO_HEEN
--------------------------------------------------------

  CREATE UNIQUE INDEX "IN_FK_ENCO_HEEN" ON "ENDERECO_CONTATO" ("HEEN_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_ENCO_PACI
--------------------------------------------------------

  CREATE INDEX "IN_FK_ENCO_PACI" ON "ENDERECO_CONTATO" ("PACI_NR_RMR") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_EVOL_COPA
--------------------------------------------------------

  CREATE INDEX "IN_FK_EVOL_COPA" ON "EVOLUCAO" ("COPA_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_EVOL_ESDO
--------------------------------------------------------

  CREATE INDEX "IN_FK_EVOL_ESDO" ON "EVOLUCAO" ("ESDO_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_EVOL_MOTI
--------------------------------------------------------

  CREATE INDEX "IN_FK_EVOL_MOTI" ON "EVOLUCAO" ("MOTI_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_EVOL_PACI
--------------------------------------------------------

  CREATE INDEX "IN_FK_EVOL_PACI" ON "EVOLUCAO" ("PACI_NR_RMR") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_EVOL_USUA
--------------------------------------------------------

  CREATE INDEX "IN_FK_EVOL_USUA" ON "EVOLUCAO" ("USUA_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_EXAM_LABO
--------------------------------------------------------

  CREATE INDEX "IN_FK_EXAM_LABO" ON "EXAME" ("LABO_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_EXAM_MODE
--------------------------------------------------------

  CREATE INDEX "IN_FK_EXAM_MODE" ON "EXAME" ("MODE_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_EXAM_PACI
--------------------------------------------------------

  CREATE INDEX "IN_FK_EXAM_PACI" ON "EXAME" ("PACI_NR_RMR") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_EXAM_USUA
--------------------------------------------------------

  CREATE INDEX "IN_FK_EXAM_USUA" ON "EXAME" ("USUA_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_EXAU_AUDI
--------------------------------------------------------

  CREATE INDEX "IN_FK_EXAU_AUDI" ON "EXAME_AUD" ("AUDI_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_FODO_DOAD
--------------------------------------------------------

  CREATE INDEX "IN_FK_FODO_DOAD" ON "FORMULARIO_DOADOR" ("DOAD_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_FODO_FORM
--------------------------------------------------------

  CREATE INDEX "IN_FK_FODO_FORM" ON "FORMULARIO_DOADOR" ("FORM_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_FODO_PECO
--------------------------------------------------------

  CREATE INDEX "IN_FK_FODO_PECO" ON "FORMULARIO_DOADOR" ("PECO_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_FORM_TIFO
--------------------------------------------------------

  CREATE INDEX "IN_FK_FORM_TIFO" ON "FORMULARIO" ("TIFO_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_FUCT_CETR
--------------------------------------------------------

  CREATE INDEX "IN_FK_FUCT_CETR" ON "FUNCAO_CENTRO_TRANSPLANTE" ("CETR_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_FUCT_FUTR
--------------------------------------------------------

  CREATE INDEX "IN_FK_FUCT_FUTR" ON "FUNCAO_CENTRO_TRANSPLANTE" ("FUTR_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_GEBP_BUPR
--------------------------------------------------------

  CREATE INDEX "IN_FK_GEBP_BUPR" ON "GENOTIPO_BUSCA_PRELIMINAR" ("BUPR_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_GEDO_DOAD
--------------------------------------------------------

  CREATE INDEX "IN_FK_GEDO_DOAD" ON "GENOTIPO_DOADOR" ("DOAD_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_GEEP_BUPR
--------------------------------------------------------

  CREATE INDEX "IN_FK_GEEP_BUPR" ON "GENOTIPO_EXPANDIDO_PRELIMINAR" ("BUPR_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_GENO_LOEX
--------------------------------------------------------

  CREATE INDEX "IN_FK_GENO_LOEX" ON "LOCUS_EXAME" ("EXAM_ID", "LOCU_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_GEPA_PACI
--------------------------------------------------------

  CREATE INDEX "IN_FK_GEPA_PACI" ON "GENOTIPO_PACIENTE" ("PACI_NR_RMR") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_GEPR_BUPR
--------------------------------------------------------

  CREATE INDEX "IN_FK_GEPR_BUPR" ON "GENOTIPO_PRELIMINAR" ("BUPR_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_HEEN_HEEN
--------------------------------------------------------

  CREATE INDEX "IN_FK_HEEN_HEEN" ON "HEMO_ENTIDADE" ("HEEN_ID_HEMOCENTRO") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_HISP_PACI
--------------------------------------------------------

  CREATE INDEX "IN_FK_HISP_PACI" ON "HISTORICO_STATUS_PACIENTE" ("PACI_NR_RMR") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_HISP_STPA
--------------------------------------------------------

  CREATE INDEX "IN_FK_HISP_STPA" ON "HISTORICO_STATUS_PACIENTE" ("STPA_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_INCO_LABO
--------------------------------------------------------

  CREATE INDEX "IN_FK_INCO_LABO" ON "INSTRUCAO_COLETA" ("LABO_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_INPR_PEWO
--------------------------------------------------------

  CREATE INDEX "IN_FK_INPR_PEWO" ON "INFO_PREVIA" ("PEWO_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_ITEC_TIPC
--------------------------------------------------------

  CREATE INDEX "IN_FK_ITEC_TIPC" ON "ITENS_CHECKLIST" ("CACH_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_LOCC_UNFC
--------------------------------------------------------

  CREATE INDEX "IN_FK_LOCC_UNFC" ON "LOCALIDADE_CORREIO" ("UNFC_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_LOEA_AUDI
--------------------------------------------------------

  CREATE INDEX "IN_FK_LOEA_AUDI" ON "LOCUS_EXAME_AUD" ("AUDI_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_LOEP_BUPR
--------------------------------------------------------

  CREATE INDEX "IN_FK_LOEP_BUPR" ON "LOCUS_EXAME_PRELIMINAR" ("BUPR_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_LOEV_PACI
--------------------------------------------------------

  CREATE INDEX "IN_FK_LOEV_PACI" ON "LOG_EVOLUCAO" ("PACI_NR_RMR") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_LOEV_USUA
--------------------------------------------------------

  CREATE INDEX "IN_FK_LOEV_USUA" ON "LOG_EVOLUCAO" ("USUA_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_LOEX_EXAM
--------------------------------------------------------

  CREATE INDEX "IN_FK_LOEX_EXAM" ON "LOCUS_EXAME" ("EXAM_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_LOEX_LOCU
--------------------------------------------------------

  CREATE INDEX "IN_FK_LOEX_LOCU" ON "LOCUS_EXAME" ("LOCU_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_LOEX_USUA
--------------------------------------------------------

  CREATE INDEX "IN_FK_LOEX_USUA" ON "LOCUS_EXAME" ("USUA_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_LOGC_BACO_FINAL
--------------------------------------------------------

  CREATE INDEX "IN_FK_LOGC_BACO_FINAL" ON "LOGRADOURO_CORREIO" ("BACO_ID_FINAL") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_LOGC_BACO_INICIAL
--------------------------------------------------------

  CREATE INDEX "IN_FK_LOGC_BACO_INICIAL" ON "LOGRADOURO_CORREIO" ("BACO_ID_INICIAL") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_LOGC_TILO
--------------------------------------------------------

  CREATE INDEX "IN_FK_LOGC_TILO" ON "LOGRADOURO_CORREIO" ("TILC_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_LOPE_LOCU
--------------------------------------------------------

  CREATE INDEX "IN_FK_LOPE_LOCU" ON "LOCUS_PEDIDO_EXAME" ("LOCU_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_LOPE_PEEX
--------------------------------------------------------

  CREATE INDEX "IN_FK_LOPE_PEEX" ON "LOCUS_PEDIDO_EXAME" ("PEEX_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_MAPR_BUPR
--------------------------------------------------------

  CREATE INDEX "IN_FK_MAPR_BUPR" ON "MATCH_PRELIMINAR" ("BUPR_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_MAPR_DOAD
--------------------------------------------------------

  CREATE INDEX "IN_FK_MAPR_DOAD" ON "MATCH_PRELIMINAR" ("DOAD_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_MATC_BUSC
--------------------------------------------------------

  CREATE INDEX "IN_FK_MATC_BUSC" ON "MATCH" ("BUSC_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_MATC_DOAD
--------------------------------------------------------

  CREATE INDEX "IN_FK_MATC_DOAD" ON "MATCH" ("DOAD_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_MEDI_CETR
--------------------------------------------------------

  CREATE INDEX "IN_FK_MEDI_CETR" ON "MEDICO" ("CETR_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_MEDI_USUA
--------------------------------------------------------

  CREATE INDEX "IN_FK_MEDI_USUA" ON "MEDICO" ("USUA_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_MEEA_AUDI
--------------------------------------------------------

  CREATE INDEX "IN_FK_MEEA_AUDI" ON "METODOLOGIA_EXAME_AUD" ("AUDI_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_MEEX_EXAM
--------------------------------------------------------

  CREATE INDEX "IN_FK_MEEX_EXAM" ON "METODOLOGIA_EXAME" ("EXAM_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_MEEX_METO
--------------------------------------------------------

  CREATE INDEX "IN_FK_MEEX_METO" ON "METODOLOGIA_EXAME" ("METO_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_MOCB_CABU
--------------------------------------------------------

  CREATE INDEX "IN_FK_MOCB_CABU" ON "CANCELAMENTO_BUSCA" ("MOCB_ID") 
  ;

--------------------------------------------------------
--  DDL for Index IN_FK_MOSD_STDO
--------------------------------------------------------

  CREATE INDEX "IN_FK_MOSD_STDO" ON "MOTIVO_STATUS_DOADOR" ("STDO_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_MOSR_MOSD
--------------------------------------------------------

  CREATE INDEX "IN_FK_MOSR_MOSD" ON "MOTIVO_STATUS_DOADOR_RECURSO" ("MOSD_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_MOSR_RECU
--------------------------------------------------------

  CREATE INDEX "IN_FK_MOSR_RECU" ON "MOTIVO_STATUS_DOADOR_RECURSO" ("RECU_ID") 
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
--  DDL for Index IN_FK_PAAD_AUDI
--------------------------------------------------------

  CREATE INDEX "IN_FK_PAAD_AUDI" ON "PAGAMENTO_AUD" ("AUDI_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_PAAU_AUDI
--------------------------------------------------------

  CREATE INDEX "IN_FK_PAAU_AUDI" ON "PACIENTE_AUD" ("AUDI_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_PACI_CETR_AVALIADOR
--------------------------------------------------------

  CREATE INDEX "IN_FK_PACI_CETR_AVALIADOR" ON "PACIENTE" ("CETR_ID_AVALIADOR") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_PACI_ETNI
--------------------------------------------------------

  CREATE INDEX "IN_FK_PACI_ETNI" ON "PACIENTE" ("ETNI_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_PACI_MEDI
--------------------------------------------------------

  CREATE INDEX "IN_FK_PACI_MEDI" ON "PACIENTE" ("MEDI_ID_RESPONSAVEL") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_PACI_PAIS
--------------------------------------------------------

  CREATE INDEX "IN_FK_PACI_PAIS" ON "PACIENTE" ("PAIS_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_PACI_RACA
--------------------------------------------------------

  CREATE INDEX "IN_FK_PACI_RACA" ON "PACIENTE" ("RACA_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_PACI_RESP
--------------------------------------------------------

  CREATE INDEX "IN_FK_PACI_RESP" ON "PACIENTE" ("RESP_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_PACI_SPAC
--------------------------------------------------------

  CREATE INDEX "IN_FK_PACI_SPAC" ON "PACIENTE" ("PACI_TX_CPF") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_PACI_UF
--------------------------------------------------------

  CREATE INDEX "IN_FK_PACI_UF" ON "PACIENTE" ("UF_SIGLA") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_PACI_USUA
--------------------------------------------------------

  CREATE INDEX "IN_FK_PACI_USUA" ON "PACIENTE" ("USUA_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_PAGA_MATC
--------------------------------------------------------

  CREATE INDEX "IN_FK_PAGA_MATC" ON "PAGAMENTO" ("MATC_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_PAGA_REGI
--------------------------------------------------------

  CREATE INDEX "IN_FK_PAGA_REGI" ON "PAGAMENTO" ("REGI_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_PAGA_STPA
--------------------------------------------------------

  CREATE INDEX "IN_FK_PAGA_STPA" ON "PAGAMENTO" ("STPA_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_PAGA_TISE
--------------------------------------------------------

  CREATE INDEX "IN_FK_PAGA_TISE" ON "PAGAMENTO" ("TISE_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_PAMI_LOCU
--------------------------------------------------------

  CREATE INDEX "IN_FK_PAMI_LOCU" ON "PACIENTE_MISMATCH" ("LOCU_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_PAMI_PACI
--------------------------------------------------------

  CREATE INDEX "IN_FK_PAMI_PACI" ON "PACIENTE_MISMATCH" ("PACI_NR_RMR") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_PEAU_AUDI
--------------------------------------------------------

  CREATE INDEX "IN_FK_PEAU_AUDI" ON "PENDENCIA_AUD" ("PEND_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_PEAW_AVRW
--------------------------------------------------------

  CREATE INDEX "IN_FK_PEAW_AVRW" ON "PEDIDO_ADICIONAL_WORKUP" ("AVRW_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_PECL_CETR
--------------------------------------------------------

  CREATE INDEX "IN_FK_PECL_CETR" ON "PEDIDO_COLETA" ("CETR_ID_COLETA") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_PECL_PEWO
--------------------------------------------------------

  CREATE INDEX "IN_FK_PECL_PEWO" ON "PEDIDO_COLETA" ("PEWO_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_PECL_STPC
--------------------------------------------------------

  CREATE INDEX "IN_FK_PECL_STPC" ON "PEDIDO_COLETA" ("STPC_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_PECL_USUA
--------------------------------------------------------

  CREATE INDEX "IN_FK_PECL_USUA" ON "PEDIDO_COLETA" ("USUA_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_PECO_HEEN
--------------------------------------------------------

  CREATE UNIQUE INDEX "IN_FK_PECO_HEEN" ON "PEDIDO_CONTATO" ("HEEN_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_PECO_SOLI
--------------------------------------------------------

  CREATE INDEX "IN_FK_PECO_SOLI" ON "PEDIDO_CONTATO" ("SOLI_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_PEEN_SOLI
--------------------------------------------------------

  CREATE INDEX "IN_FK_PEEN_SOLI" ON "PEDIDO_ENRIQUECIMENTO" ("SOLI_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_PEEN_USUA
--------------------------------------------------------

  CREATE INDEX "IN_FK_PEEN_USUA" ON "PEDIDO_ENRIQUECIMENTO" ("USUA_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_PEEX_EXAM
--------------------------------------------------------

  CREATE INDEX "IN_FK_PEEX_EXAM" ON "PEDIDO_EXAME" ("EXAM_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_PEEX_EXAM_CORDAO_INTER
--------------------------------------------------------

  CREATE INDEX "IN_FK_PEEX_EXAM_CORDAO_INTER" ON "PEDIDO_EXAME" ("EXAM_ID_CORDAO_INTERNACIONAL") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_PEEX_EXAM_DOADOR_INTER
--------------------------------------------------------

  CREATE INDEX "IN_FK_PEEX_EXAM_DOADOR_INTER" ON "PEDIDO_EXAME" ("EXAM_ID_DOADOR_INTERNACIONAL") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_PEEX_LABO
--------------------------------------------------------

  CREATE INDEX "IN_FK_PEEX_LABO" ON "PEDIDO_EXAME" ("LABO_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_PEEX_SOLI
--------------------------------------------------------

  CREATE INDEX "IN_FK_PEEX_SOLI" ON "PEDIDO_EXAME" ("SOLI_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_PEEX_STPX
--------------------------------------------------------

  CREATE INDEX "IN_FK_PEEX_STPX" ON "PEDIDO_EXAME" ("STPX_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_PEEX_TIEX
--------------------------------------------------------

  CREATE INDEX "IN_FK_PEEX_TIEX" ON "PEDIDO_EXAME" ("TIEX_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_PEID_SOLI
--------------------------------------------------------

  CREATE INDEX "IN_FK_PEID_SOLI" ON "PEDIDO_IDM" ("SOLI_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_PEID_STPI
--------------------------------------------------------

  CREATE INDEX "IN_FK_PEID_STPI" ON "PEDIDO_IDM" ("STPI_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_PELA_AUDI
--------------------------------------------------------

  CREATE INDEX "IN_FK_PELA_AUDI" ON "PEDIDO_LOGISTICA_AUD" ("AUDI_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_PELE_LOEV
--------------------------------------------------------

  CREATE INDEX "IN_FK_PELE_LOEV" ON "PERFIL_LOG_EVOLUCAO" ("LOEV_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_PELE_PERF
--------------------------------------------------------

  CREATE INDEX "IN_FK_PELE_PERF" ON "PERFIL_LOG_EVOLUCAO" ("PERF_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_PELO_PECL
--------------------------------------------------------

  CREATE INDEX "IN_FK_PELO_PECL" ON "PEDIDO_LOGISTICA" ("PECL_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_PELO_PETR
--------------------------------------------------------

  CREATE INDEX "IN_FK_PELO_PETR" ON "PEDIDO_LOGISTICA" ("PELO_DT_CRIACAO") 
  ;
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
--  DDL for Index IN_FK_PEND_AVAL
--------------------------------------------------------

  CREATE INDEX "IN_FK_PEND_AVAL" ON "PENDENCIA" ("AVAL_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_PEND_STPE
--------------------------------------------------------

  CREATE INDEX "IN_FK_PEND_STPE" ON "PENDENCIA" ("STPE_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_PEND_TIPE
--------------------------------------------------------

  CREATE INDEX "IN_FK_PEND_TIPE" ON "PENDENCIA" ("TIPE_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_PERF_SIST
--------------------------------------------------------

  CREATE INDEX "IN_FK_PERF_SIST" ON "PERFIL" ("SIST_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_PETC_CETR_DESTINO
--------------------------------------------------------

  CREATE INDEX "IN_FK_PETC_CETR_DESTINO" ON "PEDIDO_TRANSFERENCIA_CENTRO" ("CETR_ID_DESTINO") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_PETC_CETR_ORIGEM
--------------------------------------------------------

  CREATE INDEX "IN_FK_PETC_CETR_ORIGEM" ON "PEDIDO_TRANSFERENCIA_CENTRO" ("CETR_ID_ORIGEM") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_PETC_PACI
--------------------------------------------------------

  CREATE INDEX "IN_FK_PETC_PACI" ON "PEDIDO_TRANSFERENCIA_CENTRO" ("PACI_NR_RMR") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_PETC_USUA
--------------------------------------------------------

  CREATE INDEX "IN_FK_PETC_USUA" ON "PEDIDO_TRANSFERENCIA_CENTRO" ("USUA_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_PETR_COUR
--------------------------------------------------------

  CREATE INDEX "IN_FK_PETR_COUR" ON "PEDIDO_TRANSPORTE" ("COUR_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_PEWA_AUDI
--------------------------------------------------------

  CREATE INDEX "IN_FK_PEWA_AUDI" ON "PEDIDO_WORKUP_AUD" ("AUDI_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_PEWO_CETR
--------------------------------------------------------

  CREATE INDEX "IN_FK_PEWO_CETR1" ON "PEDIDO_WORKUP" ("CETR_ID_TRANSP") 
  ;
  CREATE INDEX "IN_FK_PEWO_CETR" ON "PEDIDO_WORKUP" ("CETR_ID_COLETA") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_PEWO_DISP
--------------------------------------------------------

  CREATE INDEX "IN_FK_PEWO_DISP" ON "DISPONIBILIDADE" ("PEWO_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_PRES_AVPR
--------------------------------------------------------

  CREATE INDEX "IN_FK_PRES_AVPR" ON "PRESCRICAO" ("AVPR_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_PRES_EVOL
--------------------------------------------------------

  CREATE INDEX "IN_FK_PRES_EVOL" ON "PRESCRICAO" ("EVOL_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_PRES_FOCE_OPCAO_1
--------------------------------------------------------

  CREATE INDEX "IN_FK_PRES_FOCE_OPCAO_1" ON "PRESCRICAO" ("FOCE_ID_OPCAO_1") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_PRES_FOCE_OPCAO_2
--------------------------------------------------------

  CREATE INDEX "IN_FK_PRES_FOCE_OPCAO_2" ON "PRESCRICAO" ("FOCE_ID_OPCAO_2") 
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
--  DDL for Index IN_FK_QUMA_DOAD
--------------------------------------------------------

  CREATE INDEX "IN_FK_QUMA_DOAD" ON "QUALIFICACAO_MATCH" ("DOAD_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_QUMA_LOCU
--------------------------------------------------------

  CREATE INDEX "IN_FK_QUMA_LOCU" ON "QUALIFICACAO_MATCH" ("LOCU_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_QUMA_MATC
--------------------------------------------------------

  CREATE INDEX "IN_FK_QUMA_MATC" ON "QUALIFICACAO_MATCH" ("MATC_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_QUMP_DOAD
--------------------------------------------------------

  CREATE INDEX "IN_FK_QUMP_DOAD" ON "QUALIFICACAO_MATCH_PRELIMINAR" ("DOAD_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_QUMP_LOCU
--------------------------------------------------------

  CREATE INDEX "IN_FK_QUMP_LOCU" ON "QUALIFICACAO_MATCH_PRELIMINAR" ("LOCU_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_QUMP_MATC
--------------------------------------------------------

  CREATE INDEX "IN_FK_QUMP_MATC" ON "QUALIFICACAO_MATCH_PRELIMINAR" ("MAPR_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_RASC_USUA
--------------------------------------------------------

  CREATE INDEX "IN_FK_RASC_USUA" ON "RASCUNHO" ("USUA_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_REAU_AUDI
--------------------------------------------------------

  CREATE INDEX "IN_FK_REAU_AUDI" ON "RESPONSAVEL_AUD" ("AUDI_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_RECO_DECO
--------------------------------------------------------

  CREATE INDEX "IN_FK_RECO_DECO" ON "RECEBIMENTO_COLETA" ("DECO_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_RECO_FOCE
--------------------------------------------------------

  CREATE INDEX "IN_FK_RECO_FOCE" ON "RECEBIMENTO_COLETA" ("FOCE_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_RECO_PECL
--------------------------------------------------------

  CREATE INDEX "IN_FK_RECO_PECL" ON "RECEBIMENTO_COLETA" ("PECL_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_REDO_DOAD
--------------------------------------------------------

  CREATE INDEX "IN_FK_REDO_DOAD" ON "RESSALVA_DOADOR" ("DOAD_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_REDO_USUA
--------------------------------------------------------

  CREATE INDEX "IN_FK_REDO_USUA" ON "RESSALVA_DOADOR" ("USUA_ID_CRIACAO") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_REFD_FODO
--------------------------------------------------------

  CREATE INDEX "IN_FK_REFD_FODO" ON "RESPOSTA_FORMULARIO_DOADOR" ("FODO_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_REGI_PAIS
--------------------------------------------------------

  CREATE INDEX "IN_FK_REGI_PAIS" ON "REGISTRO" ("PAIS_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_REPA_ARRW
--------------------------------------------------------

  CREATE INDEX "IN_FK_REPA_ARRW" ON "RESPOSTA_PEDIDO_ADICIONAL" ("ARRW_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_REPA_PEAW
--------------------------------------------------------

  CREATE INDEX "IN_FK_REPA_PEAW" ON "RESPOSTA_PEDIDO_ADICIONAL" ("PEAW_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_REPE_EVOL
--------------------------------------------------------

  CREATE INDEX "IN_FK_REPE_EVOL" ON "RESPOSTA_PENDENCIA" ("EVOL_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_REPE_EXAM
--------------------------------------------------------

  CREATE INDEX "IN_FK_REPE_EXAM" ON "RESPOSTA_PENDENCIA" ("EXAM_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_REPE_USUA
--------------------------------------------------------

  CREATE INDEX "IN_FK_REPE_USUA" ON "RESPOSTA_PENDENCIA" ("USUA_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_RESC_PELO
--------------------------------------------------------

  CREATE INDEX "IN_FK_RESC_PELO" ON "RESPOSTA_CHECKLIST" ("RESC_IN_RESPOSTA") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_REWO_PEWO
--------------------------------------------------------

  CREATE INDEX "IN_FK_REWO_PEWO" ON "RESULTADO_WORKUP" ("PEWO_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_REWO_USUA
--------------------------------------------------------

  CREATE INDEX "IN_FK_REWO_USUA" ON "RESULTADO_WORKUP" ("USUA_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_SOLI_LABO
--------------------------------------------------------

  CREATE INDEX "IN_FK_SOLI_LABO" ON "SOLICITACAO" ("LABO_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_SOLI_PEWO
--------------------------------------------------------

  CREATE INDEX "IN_FK_SOLI_PEWO" ON "PEDIDO_WORKUP" ("SOLI_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_SOLI_TIEX
--------------------------------------------------------

  CREATE INDEX "IN_FK_SOLI_TIEX" ON "SOLICITACAO" ("TIEX_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_SOLI_TISO
--------------------------------------------------------

  CREATE INDEX "IN_FK_SOLI_TISO" ON "SOLICITACAO" ("TISO_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_SOLI_USUA_RESPONSAVEL
--------------------------------------------------------

  CREATE INDEX "IN_FK_SOLI_USUA_RESPONSAVEL" ON "SOLICITACAO" ("USUA_ID_RESPONSAVEL")
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_SOLI_CETR_TRANSPLANTE
--------------------------------------------------------

  CREATE INDEX "IN_FK_SOLI_CETR_TRANSPLANTE" ON "SOLICITACAO" ("CETR_ID_TRANSPLANTE")
  ;  
--------------------------------------------------------
--  DDL for Index IN_FK_SOLI_CETR_COLETA
--------------------------------------------------------

  CREATE INDEX "IN_FK_SOLI_CETR_COLETA" ON "SOLICITACAO" ("CETR_ID_COLETA")
  ;  
--------------------------------------------------------
--  DDL for Index IN_FK_SORE_PEEX
--------------------------------------------------------

  CREATE INDEX "IN_FK_SORE_PEEX" ON "SOLICITACAO_REDOMEWEB" ("PEEX_ID") 
  ;

--------------------------------------------------------
--  DDL for Index IN_FK_TECD_COTE
--------------------------------------------------------

  CREATE INDEX "IN_FK_TECD_COTE" ON "TENTATIVA_CONTATO_DOADOR" ("COTE_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_TECD_PECO
--------------------------------------------------------

  CREATE INDEX "IN_FK_TECD_PECO" ON "TENTATIVA_CONTATO_DOADOR" ("PECO_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_TECD_RETC
--------------------------------------------------------

  CREATE INDEX "IN_FK_TECD_RETC" ON "TENTATIVA_CONTATO_DOADOR" ("RETC_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_TECD_USUA
--------------------------------------------------------

  CREATE INDEX "IN_FK_TECD_USUA" ON "TENTATIVA_CONTATO_DOADOR" ("USUA_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_TRTA_AUDI
--------------------------------------------------------

  CREATE INDEX "IN_FK_TRTA_AUDI" ON "TRANSPORTE_TERRESTRE_AUD" ("AUDI_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_TRTE_PELO
--------------------------------------------------------

  CREATE INDEX "IN_FK_TRTE_PELO" ON "TRANSPORTE_TERRESTRE" ("PELO_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_UNFC_PACO
--------------------------------------------------------

  CREATE INDEX "IN_FK_UNFC_PACO" ON "UNIDADE_FEDERATIVA_CORREIO" ("PACO_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_USPE_PERF
--------------------------------------------------------

  CREATE INDEX "IN_FK_USPE_PERF" ON "USUARIO_PERFIL" ("PERF_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_USPE_USUA
--------------------------------------------------------

  CREATE INDEX "IN_FK_USPE_USUA" ON "USUARIO_PERFIL" ("USUA_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_USUA_LABO
--------------------------------------------------------

  CREATE INDEX "IN_FK_USUA_LABO" ON "USUARIO" ("LABO_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_VADN_LOCU
--------------------------------------------------------

  CREATE INDEX "IN_FK_VADN_LOCU" ON "VALOR_DNA" ("LOCU_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_VAGD_GEDO
--------------------------------------------------------

  CREATE INDEX "IN_FK_VAGD_GEDO" ON "VALOR_GENOTIPO_DOADOR" ("GEDO_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_VAGP_GEPA
--------------------------------------------------------

  CREATE INDEX "IN_FK_VAGP_GEPA" ON "VALOR_GENOTIPO_PACIENTE" ("GEPA_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_VALP_LOCU
--------------------------------------------------------

  CREATE UNIQUE INDEX "IN_FK_VALP_LOCU" ON "VALOR_P" ("LOCU_ID", "VALP_TX_NOME_GRUPO") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_VASO_LOCU
--------------------------------------------------------

  CREATE INDEX "IN_FK_VASO_LOCU" ON "VALOR_SOROLOGICO" ("LOCU_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_VGBD_GEDO
--------------------------------------------------------

  CREATE INDEX "IN_FK_VGBD_GEDO" ON "VALOR_GENOTIPO_BUSCA_DOADOR" ("GEDO_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_VGBD_LOCU
--------------------------------------------------------

  CREATE INDEX "IN_FK_VGBD_LOCU" ON "VALOR_GENOTIPO_BUSCA_DOADOR" ("LOCU_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_VGBP_GEPA
--------------------------------------------------------

  CREATE INDEX "IN_FK_VGBP_GEPA" ON "VALOR_GENOTIPO_BUSCA_PACIENTE" ("GEPA_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_VGBP_LOCU
--------------------------------------------------------

  CREATE INDEX "IN_FK_VGBP_LOCU" ON "VALOR_GENOTIPO_BUSCA_PACIENTE" ("LOCU_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_VGED_DOAD
--------------------------------------------------------

  CREATE INDEX "IN_FK_VGED_DOAD" ON "VALOR_GENOTIPO_EXPAND_DOADOR" ("DOAD_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_VGED_GEDO
--------------------------------------------------------

  CREATE INDEX "IN_FK_VGED_GEDO" ON "VALOR_GENOTIPO_EXPAND_DOADOR" ("GEDO_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_VGED_LOCU
--------------------------------------------------------

  CREATE INDEX "IN_FK_VGED_LOCU" ON "VALOR_GENOTIPO_EXPAND_DOADOR" ("LOCU_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_VGEP_GEPA
--------------------------------------------------------

  CREATE INDEX "IN_FK_VGEP_GEPA" ON "VALOR_GENOTIPO_EXPAND_PACIENTE" ("GEPA_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_VGEP_LOCU
--------------------------------------------------------

  CREATE INDEX "IN_FK_VGEP_LOCU" ON "VALOR_GENOTIPO_EXPAND_PACIENTE" ("LOCU_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_VGEP_PACI
--------------------------------------------------------

  CREATE INDEX "IN_FK_VGEP_PACI" ON "VALOR_GENOTIPO_EXPAND_PACIENTE" ("PACI_NR_RMR") 
  ;
--------------------------------------------------------
--  DDL for Index IN_FUCT_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "IN_FUCT_PK" ON "FUNCAO_CENTRO_TRANSPLANTE" ("CETR_ID", "FUTR_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_GRU_ALELICO_VALOR_ALELICO
--------------------------------------------------------

  CREATE INDEX "IN_GRU_ALELICO_VALOR_ALELICO" ON "SPLIT_NMDP" ("SPNM_TX_GRUPO_ALELICO", "SPNM_TX_VALOR_ALELICO") 
  ;
--------------------------------------------------------
--  DDL for Index IN_LOCU_ID_DNNM_TX_VALOR
--------------------------------------------------------

  CREATE INDEX "IN_LOCU_ID_DNNM_TX_VALOR" ON "VALOR_DNA_NMDP_VALIDO" ("DNNM_TX_VALOR") 
  ;
--------------------------------------------------------
--  DDL for Index IN_LOCU_ID_DNNM_TX_VALOR1
--------------------------------------------------------

  CREATE INDEX "IN_LOCU_ID_DNNM_TX_VALOR1" ON "COPIA_VALOR_DNA_NMDP" ("DNNM_TX_VALOR") 
  ;
--------------------------------------------------------
--  DDL for Index IN_PK_ARPT_ID
--------------------------------------------------------

  CREATE UNIQUE INDEX "IN_PK_ARPT_ID" ON "ARQUIVO_PEDIDO_TRANSPORTE" ("ARPT_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_PK_AVPR
--------------------------------------------------------

  CREATE UNIQUE INDEX "IN_PK_AVPR" ON "AVALIACAO_PRESCRICAO" ("AVPR_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_PK_BUAU
--------------------------------------------------------

  CREATE UNIQUE INDEX "IN_PK_BUAU" ON "BUSCA_AUD" ("BUSC_ID", "AUDI_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_PK_BUCH_MATC
--------------------------------------------------------

  CREATE INDEX "IN_PK_BUCH_MATC" ON "BUSCA_CHECKLIST" ("MATC_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_PK_DECO
--------------------------------------------------------

  CREATE UNIQUE INDEX "IN_PK_DECO" ON "DESTINO_COLETA" ("DECO_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_PK_DIBU_ID
--------------------------------------------------------

  CREATE UNIQUE INDEX "IN_PK_DIBU_ID" ON "DIALOGO_BUSCA" ("DIBU_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_PK_EMCO
--------------------------------------------------------

  CREATE UNIQUE INDEX "IN_PK_EMCO" ON "EMAIL_CONTATO" ("EMCO_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_PK_ENCO
--------------------------------------------------------

  CREATE UNIQUE INDEX "IN_PK_ENCO" ON "ENDERECO_CONTATO" ("ENCO_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_PK_EXAA
--------------------------------------------------------

  CREATE UNIQUE INDEX "IN_PK_EXAA" ON "EXAME_AUD" ("EXAM_ID", "AUDI_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_PK_FODO
--------------------------------------------------------

  CREATE UNIQUE INDEX "IN_PK_FODO" ON "FORMULARIO_DOADOR" ("FODO_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_PK_FORM
--------------------------------------------------------

  CREATE UNIQUE INDEX "IN_PK_FORM" ON "FORMULARIO" ("FORM_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_PK_FUTR
--------------------------------------------------------

  CREATE UNIQUE INDEX "IN_PK_FUTR" ON "FUNCAO_TRANSPLANTE" ("FUTR_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_PK_GEDO
--------------------------------------------------------

  CREATE UNIQUE INDEX "IN_PK_GEDO" ON "GENOTIPO_DOADOR" ("GEDO_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_PK_GEPA
--------------------------------------------------------

  CREATE UNIQUE INDEX "IN_PK_GEPA" ON "GENOTIPO_PACIENTE" ("GEPA_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_PK_LABO
--------------------------------------------------------

  CREATE UNIQUE INDEX "IN_PK_LABO" ON "LABORATORIO" ("LABO_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_PK_MOCB
--------------------------------------------------------

  CREATE UNIQUE INDEX "IN_PK_MOCB" ON "MOTIVO_CANCELAMENTO_BUSCA" ("MOCB_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_PK_MOSD
--------------------------------------------------------

  CREATE UNIQUE INDEX "IN_PK_MOSD" ON "MOTIVO_STATUS_DOADOR" ("MOSD_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_PK_PEEN_ID
--------------------------------------------------------

  CREATE UNIQUE INDEX "IN_PK_PEEN_ID" ON "PEDIDO_ENRIQUECIMENTO" ("PEEN_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_PK_PETR
--------------------------------------------------------

  CREATE UNIQUE INDEX "IN_PK_PETR" ON "PEDIDO_TRANSPORTE" ("PETR_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_PK_RECO
--------------------------------------------------------

  CREATE UNIQUE INDEX "IN_PK_RECO" ON "RECEBIMENTO_COLETA" ("RECE_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_PK_STBU
--------------------------------------------------------

  CREATE UNIQUE INDEX "IN_PK_STBU" ON "STATUS_BUSCA" ("STBU_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_PK_STDO
--------------------------------------------------------

  CREATE UNIQUE INDEX "IN_PK_STDO" ON "STATUS_DOADOR" ("STDO_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_PK_TIFO
--------------------------------------------------------

  CREATE UNIQUE INDEX "IN_PK_TIFO" ON "TIPO_FORMULARIO" ("TIFO_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_PK_TISO
--------------------------------------------------------

  CREATE UNIQUE INDEX "IN_PK_TISO" ON "TIPO_SOLICITACAO" ("TISO_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_PK_VERSAO_ARQUIVO_BAIXADO
--------------------------------------------------------

  CREATE UNIQUE INDEX "IN_PK_VERSAO_ARQUIVO_BAIXADO" ON "VERSAO_ARQUIVO_BAIXADO" ("VEAB_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_PK_VGBP
--------------------------------------------------------

  CREATE UNIQUE INDEX "IN_PK_VGBP" ON "VALOR_GENOTIPO_BUSCA_PACIENTE" ("VGBP_ID") 
  ;
--------------------------------------------------------
--  DDL for Index IN_SPNM_TX_VALOR_ALELICO
--------------------------------------------------------

  CREATE INDEX "IN_SPNM_TX_VALOR_ALELICO" ON "SPLIT_NMDP" ("SPNM_TX_VALOR_ALELICO") 
  ;
--------------------------------------------------------
--  DDL for Index IN_TESD_VALOR
--------------------------------------------------------

  CREATE INDEX "IN_TESD_VALOR" ON "TEMP_SPLIT_VALOR_DNA" ("TESD_IN_COMPARA_NMDP", "TESD_TX_VALOR_ALELICO", "LOCU_ID", "TESD_TX_GRUPO_ALELICO") 
  ;
--------------------------------------------------------
--  DDL for Index IN_VADN_TX_NOME_GRUPO
--------------------------------------------------------

  CREATE INDEX "IN_VADN_TX_NOME_GRUPO" ON "VALOR_DNA_NMDP_VALIDO" ("VADN_TX_NOME_GRUPO") 
  ;
--------------------------------------------------------
--  DDL for Index IN_VALOR
--------------------------------------------------------

  CREATE INDEX "IN_VALOR" ON "SPLIT_VALOR_DNA" ("SPVD_IN_COMPARA_NMDP", "SPVD_TX_VALOR_ALELICO", "LOCU_ID", "SPVD_TX_GRUPO_ALELICO") 
  ;
--------------------------------------------------------
--  DDL for Index LOCUS_PEDIDO_EXAME_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "LOCUS_PEDIDO_EXAME_PK" ON "LOCUS_PEDIDO_EXAME" ("LOCU_ID", "PEEX_ID") 
  ;
--------------------------------------------------------
--  DDL for Index MOTIVO_DESCARTE_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "MOTIVO_DESCARTE_PK" ON "MOTIVO_DESCARTE" ("MODE_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PACIENTE_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "PACIENTE_PK" ON "PACIENTE" ("PACI_NR_RMR") 
  ;
--------------------------------------------------------
--  DDL for Index PK_AREV_ID
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_AREV_ID" ON "ARQUIVO_EVOLUCAO" ("AREV_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_AREX
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_AREX" ON "ARQUIVO_EXAME" ("AREX_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_ARPI
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_ARPI" ON "ARQUIVO_PEDIDO_IDM" ("ARPI_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_ARPR
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_ARPR" ON "ARQUIVO_PRESCRICAO" ("ARPR_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_ARRI
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_ARRI" ON "ARQUIVO_RELAT_INTERNACIONAL" ("ARRI_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_ARRW
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_ARRW" ON "ARQUIVO_RESULTADO_WORKUP" ("ARRW_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_ARVL
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_ARVL" ON "ARQUIVO_VOUCHER_LOGISTICA" ("ARVL_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_ASRP
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_ASRP" ON "ASSOCIA_RESPOSTA_PENDENCIA" ("PEND_ID", "REPE_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_AUDI
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_AUDI" ON "AUDITORIA" ("AUDI_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_AVAL
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_AVAL" ON "AVALIACAO" ("AVAL_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_AVAU
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_AVAU" ON "AVALIACAO_AUD" ("AVAL_ID", "AUDI_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_AVCT
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_AVCT" ON "AVALIACAO_CAMARA_TECNICA" ("AVCT_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_AVLA
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_AVLA" ON "ARQUIVO_VOUCHER_LOGISTICA_AUD" ("ARVL_ID", "AUDI_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_AVNB
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_AVNB" ON "AVALIACAO_NOVA_BUSCA" ("AVNB_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_AVPC
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_AVPC" ON "AVALIACAO_PEDIDO_COLETA" ("AVPC_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_AVRW
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_AVRW" ON "AVALIACAO_RESULTADO_WORKUP" ("AVRW_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_AVWD
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_AVWD" ON "AVALIACAO_WORKUP_DOADOR" ("AVWD_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_BACO
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_BACO" ON "BAIRRO_CORREIO" ("BACO_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_BASC
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_BASC" ON "BANCO_SANGUE_CORDAO" ("BASC_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_BUCH_ID
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_BUCH_ID" ON "BUSCA_CHECKLIST" ("BUCH_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_CACH_ID
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_CACH_ID" ON "CATEGORIA_CHECKLIST" ("CACH_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_CANO
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_CANO" ON "CATEGORIA_NOTIFICACAO" ("CANO_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_CETR
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_CETR" ON "CENTRO_TRANSPLANTE" ("CETR_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_CID
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_CID" ON "CID" ("CID_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_CIED
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_CIED" ON "CID_ESTAGIO_DOENCA" ("CID_ID", "ESDO_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_CIID
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_CIID" ON "CID_IDIOMA" ("CID_ID", "IDIO_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_CLAB
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_CLAB" ON "CLASSIFICACAO_ABO" ("CLAB_TX_ABO", "CLAB_TX_ABO_RELACAO") 
  ;
--------------------------------------------------------
--  DDL for Index PK_COJB
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_COJB" ON "CONTROLE_JOB_BRASILCORD" ("COJB_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_CONF
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_CONF" ON "CONFIGURACAO" ("CONF_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_COPA
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_COPA" ON "CONDICAO_PACIENTE" ("COPA_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_CORA
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_CORA" ON "CONSTANTE_RELATORIO_AUD" ("AUDI_ID", "CORE_ID_CODIGO") 
  ;
--------------------------------------------------------
--  DDL for Index PK_CORE
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_CORE" ON "CONSTANTE_RELATORIO" ("CORE_ID_CODIGO") 
  ;
--------------------------------------------------------
--  DDL for Index PK_COTA
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_COTA" ON "CONTATO_TELEFONICO_AUD" ("COTE_ID", "AUDI_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_COTE
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_COTE" ON "CONTATO_TELEFONICO" ("COTE_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_DIAG
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_DIAG" ON "DIAGNOSTICO" ("PACI_NR_RMR") 
  ;
--------------------------------------------------------
--  DDL for Index PK_DIAU
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_DIAU" ON "DIAGNOSTICO_AUD" ("PACI_NR_RMR", "AUDI_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_DICC
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_DICC" ON "DISPONIBILIDADE_CENTRO_COLETA" ("DICC_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_DISA
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_DISA" ON "DISPONIBILIDADE_AUD" ("DISP_ID", "AUDI_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_DOAD
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_DOAD" ON "DOADOR" ("DOAD_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_DOAU1
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_DOAU1" ON "DOADOR_AUD" ("AUDI_ID", "DOAD_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_DOIR
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_DOIR" ON "DOADOR_IN_REDOME" ("DOIR_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_ENCA
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_ENCA" ON "ENDERECO_CONTATO_AUD" ("ENCO_ID", "AUDI_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_ESDO
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_ESDO" ON "ESTAGIO_DOENCA" ("ESDO_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_ESIR
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_ESIR" ON "ESTABELECIMENTO_IN_REDOME" ("ESIR_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_ETNI
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_ETNI" ON "ETNIA" ("ETNI_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_EVOL
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_EVOL" ON "EVOLUCAO" ("EVOL_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_EXAM
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_EXAM" ON "EXAME" ("EXAM_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_FOCE
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_FOCE" ON "FONTE_CELULAS" ("FOCE_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_GEBP
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_GEBP" ON "GENOTIPO_BUSCA_PRELIMINAR" ("GEBP_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_GEEP
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_GEEP" ON "GENOTIPO_EXPANDIDO_PRELIMINAR" ("GEEP_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_GENO
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_GENO" ON "BUSCA_PRELIMINAR" ("BUPR_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_GEPR
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_GEPR" ON "GENOTIPO_PRELIMINAR" ("GEPR_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_HEEN
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_HEEN" ON "HEMO_ENTIDADE" ("HEEN_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_HISP_ID
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_HISP_ID" ON "HISTORICO_STATUS_PACIENTE" ("HISP_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_IDIO
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_IDIO" ON "IDIOMA" ("IDIO_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_INCO
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_INCO" ON "INSTRUCAO_COLETA" ("INCO_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_ITEC_ID
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_ITEC_ID" ON "ITENS_CHECKLIST" ("ITEC_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_LOCO
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_LOCO" ON "LOCALIDADE_CORREIO" ("LOCC_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_LOCU
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_LOCU" ON "LOCUS" ("LOCU_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_LOEA
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_LOEA" ON "LOCUS_EXAME_AUD" ("EXAM_ID", "LOCU_ID", "AUDI_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_LOEP
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_LOEP" ON "LOCUS_EXAME_PRELIMINAR" ("LOEP_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_LOEX
--------------------------------------------------------

  CREATE INDEX "PK_LOEX" ON "LOCUS_EXAME" ("LOCU_ID", "EXAM_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_LOGC
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_LOGC" ON "LOGRADOURO_CORREIO" ("LOGC_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_MAPR
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_MAPR" ON "MATCH_PRELIMINAR" ("MAPR_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_MECR
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_MECR" ON "MEDICO_CT_REFERENCIA" ("MEDI_ID", "CETR_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_MEDI
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_MEDI" ON "MEDICO" ("MEDI_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_MEEX
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_MEEX" ON "METODOLOGIA_EXAME" ("EXAM_ID", "METO_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_METO
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_METO" ON "METODOLOGIA" ("METO_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_MOCC
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_MOCC" ON "MOTIVO_CANCELAMENTO_COLETA" ("MOCC_TX_CODIGO") 
  ;
--------------------------------------------------------
--  DDL for Index PK_MOTI
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_MOTI" ON "MOTIVO" ("MOTI_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_NOTI
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_NOTI" ON "NOTIFICACAO" ("NOTI_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_OBDO
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_OBDO" ON "RESSALVA_DOADOR" ("REDO_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_PAAD
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_PAAD" ON "PAGAMENTO_AUD" ("AUDI_ID", "PAGA_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_PAAU
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_PAAU" ON "PACIENTE_AUD" ("PACI_NR_RMR", "AUDI_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_PACO
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_PACO" ON "PAIS_CORREIO" ("PACO_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_PAGA
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_PAGA" ON "PAGAMENTO" ("PAGA_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_PAIR
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_PAIR" ON "PACIENTE_IN_REREME" ("PACI_NR_RMR", "PAIR_DT_INCLUSAO") 
  ;
--------------------------------------------------------
--  DDL for Index PK_PAIS
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_PAIS" ON "PAIS" ("PAIS_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_PAMI
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_PAMI" ON "PACIENTE_MISMATCH" ("PACI_NR_RMR", "LOCU_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_PCCR
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_PCCR" ON "PRE_CAD_MEDICO_CT_REFERENCIA" ("PRCM_ID", "CETR_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_PCEM
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_PCEM" ON "PRE_CADASTRO_MEDICO_EMAIL" ("PCEM_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_PCME
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_PCME" ON "PRE_CADASTRO_MEDICO_ENDERECO" ("PCME_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_PCMT
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_PCMT" ON "PRE_CADASTRO_MEDICO_TELEFONE" ("PCMT_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_PEAU
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_PEAU" ON "PENDENCIA_AUD" ("PEND_ID", "AUDI_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_PEAW
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_PEAW" ON "PEDIDO_ADICIONAL_WORKUP" ("PEAW_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_PECL
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_PECL" ON "PEDIDO_COLETA" ("PECL_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_PECO
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_PECO" ON "PEDIDO_CONTATO" ("PECO_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_PEEX
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_PEEX" ON "PEDIDO_EXAME" ("PEEX_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_PEIA
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_PEIA" ON "PEDIDO_IDM_AUD" ("PEID_ID", "AUDI_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_PEID
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_PEID" ON "PEDIDO_IDM" ("PEID_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_PELA
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_PELA" ON "PEDIDO_LOGISTICA_AUD" ("PELO_ID", "AUDI_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_PELE
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_PELE" ON "PERFIL_LOG_EVOLUCAO" ("PERF_ID", "LOEV_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_PELO
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_PELO" ON "PEDIDO_LOGISTICA" ("PELO_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_PEND
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_PEND" ON "PENDENCIA" ("PEND_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_PERF
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_PERF" ON "PERFIL" ("PERF_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_PERM
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_PERM" ON "PERMISSAO" ("RECU_ID", "PERF_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_PETC_ID
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_PETC_ID" ON "PEDIDO_TRANSFERENCIA_CENTRO" ("PETC_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_PEWA
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_PEWA" ON "PEDIDO_WORKUP_AUD" ("PEWO_ID", "AUDI_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_PEWO
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_PEWO" ON "PEDIDO_WORKUP" ("PEWO_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_PRCM
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_PRCM" ON "PRE_CADASTRO_MEDICO" ("PRCM_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_PRES
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_PRES" ON "PRESCRICAO" ("PRES_ID") 
  ;

--------------------------------------------------------
--  DDL for Index PK_QUMP
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_QUMP" ON "QUALIFICACAO_MATCH_PRELIMINAR" ("QUMP_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_RACA
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_RACA" ON "RACA" ("RACA_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_RASC
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_RASC" ON "RASCUNHO" ("RASC_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_REAU
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_REAU" ON "RESPONSAVEL_AUD" ("RESP_ID", "AUDI_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_RECU
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_RECU" ON "RECURSO" ("RECU_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_REGI
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_REGI" ON "REGISTRO" ("REGI_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_REPA
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_REPA" ON "RESPOSTA_PEDIDO_ADICIONAL" ("REPA_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_REPE
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_REPE" ON "RESPOSTA_PENDENCIA" ("REPE_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_RESC_ID
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_RESC_ID" ON "RESPOSTA_CHECKLIST" ("RESC_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_RESP
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_RESP" ON "RESPONSAVEL" ("RESP_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_RETC
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_RETC" ON "RESPOSTA_TENTATIVA_CONTATO" ("RETC_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_REWO
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_REWO" ON "RESULTADO_WORKUP" ("REWO_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_SIST
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_SIST" ON "SISTEMA" ("SIST_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_SORE
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_SORE" ON "SOLICITACAO_REDOMEWEB" ("SORE_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_SPAC
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_SPAC" ON "STATUS_PACIENTE" ("STPA_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_SPNM
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_SPNM" ON "SPLIT_NMDP" ("TEVN_ID_CODIGO", "SPNM_TX_GRUPO_ALELICO", "SPNM_TX_VALOR_ALELICO") 
  ;
--------------------------------------------------------
--  DDL for Index PK_SPVD
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_SPVD" ON "SPLIT_VALOR_DNA" ("LOCU_ID", "SPVD_TX_GRUPO_ALELICO", "SPVD_TX_VALOR_ALELICO") 
  ;
--------------------------------------------------------
--  DDL for Index PK_STCT_ID
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_STCT_ID" ON "STATUS_AVALIACAO_CAMARA_TEC" ("STCT_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_STPA
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_STPA" ON "STATUS_PAGAMENTO" ("STPA_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_STPC
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_STPC" ON "STATUS_PEDIDO_COLETA" ("STPC_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_STPE
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_STPE" ON "STATUS_PENDENCIA" ("STPE_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_STPI
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_STPI" ON "STATUS_PEDIDO_IDM" ("STPI_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_STPL
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_STPL" ON "STATUS_PEDIDO_LOGISTICA" ("STPL_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_STPX
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_STPX" ON "STATUS_PEDIDO_EXAME" ("STPX_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_TECD
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_TECD" ON "TENTATIVA_CONTATO_DOADOR" ("TECD_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_TESD
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_TESD" ON "TEMP_SPLIT_VALOR_DNA" ("LOCU_ID", "TESD_TX_GRUPO_ALELICO", "TESD_TX_VALOR_ALELICO") 
  ;
--------------------------------------------------------
--  DDL for Index PK_TEVD
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_TEVD" ON "TEMP_VALOR_DNA" ("LOCU_ID", "TEVD_TX_VALOR") 
  ;
--------------------------------------------------------
--  DDL for Index PK_TEVG
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_TEVG" ON "TEMP_VALOR_G" ("LOCU_ID", "TEVG_TX_NOME_GRUPO") ;
--------------------------------------------------------
--  DDL for Index PK_TEVN
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_TEVN" ON "TEMP_VALOR_NMDP" ("TEVN_ID_CODIGO") 
  ;
--------------------------------------------------------
--  DDL for Index PK_TEVP
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_TEVP" ON "TEMP_VALOR_P" ("LOCU_ID", "TEVP_TX_NOME_GRUPO") ;
--------------------------------------------------------
--  DDL for Index PK_TIEL
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_TIEL" ON "TIPO_EXAME_LOCUS" ("LOCU_ID", "TIEX_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_TIEX
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_TIEX" ON "TIPO_EXAME" ("TIEX_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_TILC
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_TILC" ON "TIPO_LOGRADOURO_CORREIO" ("TILC_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_TIPC_ID
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_TIPC_ID" ON "TIPO_CHECKLIST" ("TIPC_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_TIPE
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_TIPE" ON "TIPO_PENDENCIA" ("TIPE_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_TIPL
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_TIPL" ON "TIPO_PEDIDO_LOGISTICA" ("TIPL_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_TISE
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_TISE" ON "TIPO_SERVICO" ("TISE_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_TITR
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_TITR" ON "TIPO_TRANSPLANTE" ("TITR_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_TRTA
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_TRTA" ON "TRANSPORTE_TERRESTRE_AUD" ("TRTE_ID", "AUDI_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_TRTE
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_TRTE" ON "TRANSPORTE_TERRESTRE" ("TRTE_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_UBSC_ID
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_UBSC_ID" ON "USUARIO_BANCO_SANGUE_CORDAO" ("BASC_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_UF
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_UF" ON "UF" ("UF_SIGLA") 
  ;
--------------------------------------------------------
--  DDL for Index PK_UNFC
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_UNFC" ON "UNIDADE_FEDERATIVA_CORREIO" ("UNFC_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_USCT
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_USCT" ON "USUARIO_CENTRO_TRANSPLANTE" ("USUA_ID", "CETR_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_USPE
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_USPE" ON "USUARIO_PERFIL" ("USUA_ID", "PERF_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_USUA
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_USUA" ON "USUARIO" ("USUA_ID") 
  ;
--------------------------------------------------------
--  DDL for Index PK_VANM
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_VANM" ON "VALOR_NMDP" ("VANM_ID_CODIGO") 
  ;
--------------------------------------------------------
--  DDL for Index PK_VANM1
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_VANM1" ON "NOVO_VALOR_NMDP" ("NOVN_ID_CODIGO") 
  ;
--------------------------------------------------------
--  DDL for Index PK_VASO
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_VASO" ON "VALOR_SOROLOGICO" ("LOCU_ID", "VASO_TX_ANTIGENO") 
  ;
--------------------------------------------------------
--  DDL for Index REDI_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "REDI_PK" ON "RESERVA_DOADOR_INTERNACIONAL" ("REDI_ID") 
  ;
--------------------------------------------------------
--  DDL for Index RELATORIO_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "RELATORIO_PK" ON "RELATORIO" ("RELA_ID") 
  ;
--------------------------------------------------------
--  DDL for Index SPLIT_NMDP_INDEX1
--------------------------------------------------------

  CREATE INDEX "SPLIT_NMDP_INDEX1" ON "SPLIT_NMDP" ("SPNM_TX_GRUPO_ALELICO", "SPNM_TX_VALOR_ALELICO") 
  ;
--------------------------------------------------------
--  DDL for Index SPLIT_NMDP_INDEX2
--------------------------------------------------------

  CREATE INDEX "SPLIT_NMDP_INDEX2" ON "SPLIT_NMDP" ("SPNM_TX_GRUPO_ALELICO") 
  ;
--------------------------------------------------------
--  DDL for Index TIBC_ID
--------------------------------------------------------

  CREATE UNIQUE INDEX "TIBC_ID" ON "TIPO_BUSCA_CHECKLIST" ("TIBC_ID") 
  ;
--------------------------------------------------------
--  DDL for Index UK_DOAD_NR_DMR
--------------------------------------------------------

  CREATE UNIQUE INDEX "UK_DOAD_NR_DMR" ON "DOADOR" ("DOAD_NR_DMR") 
  ;
--------------------------------------------------------
--  DDL for Index UK_RELA
--------------------------------------------------------

  CREATE UNIQUE INDEX "UK_RELA" ON "RELATORIO" ("RELA_TX_CODIGO") 
  ;
--------------------------------------------------------
--  DDL for Index UK_SORE
--------------------------------------------------------

  CREATE UNIQUE INDEX "UK_SORE" ON "SOLICITACAO_REDOMEWEB" ("SORE_ID_SOLICITACAO_REDOMEWEB", "SORE_NR_TIPO") 
  ;
--------------------------------------------------------
--  DDL for Index VALOR_NMDP_INDEX3
--------------------------------------------------------

  CREATE INDEX "VALOR_NMDP_INDEX3" ON "VALOR_NMDP" ("VANM_ID_CODIGO", "VANM_IN_AGRUPADO") 
  ;
--------------------------------------------------------
--  DDL for Index PK_SPVG
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_SPVG" ON "SPLIT_VALOR_G" ("LOCU_ID", "SPVG_TX_VALOR", "SPVG_TX_NOME_GRUPO")
  ;
--------------------------------------------------------
--  DDL for Index PK_SPVP
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_SPVP" ON "SPLIT_VALOR_P" ("LOCU_ID", "SPVP_TX_VALOR", "SPVP_TX_NOME_GRUPO")
  ;
--------------------------------------------------------
--  DDL for Index PK_PEED
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_PEED" ON "PEDIDO_ENVIO_EMDIS" ("PEEE_ID")
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_PEED_SOLI
--------------------------------------------------------

  CREATE INDEX "IN_FK_PEED_SOLI" ON "PEDIDO_ENVIO_EMDIS" ("SOLI_ID")
  ;
--------------------------------------------------------
--  DDL for Index IN_FK_SOLI_FAWO
--------------------------------------------------------

  CREATE INDEX IN_FK_SOLI_FAWO ON SOLICITACAO (FAWO_ID ASC)
  ;

--------------------------------------------------------
--  DDL for Index IN_FK_SOLI_MOCS
--------------------------------------------------------

  CREATE INDEX IN_FK_SOLI_MOCS ON SOLICITACAO (MOCS_ID ASC)
  ;  
--------------------------------------------------------
--  Constraints for Table RECURSO
--------------------------------------------------------

  ALTER TABLE "RECURSO" MODIFY ("RECU_ID" NOT NULL ENABLE);
  ALTER TABLE "RECURSO" MODIFY ("RECU_TX_SIGLA" NOT NULL ENABLE);
  ALTER TABLE "RECURSO" MODIFY ("RECU_TX_DESCRICAO" NOT NULL ENABLE);
  ALTER TABLE "RECURSO" ADD CONSTRAINT "PK_RECU" PRIMARY KEY ("RECU_ID");
--------------------------------------------------------
--  Constraints for Table TEMP_VALOR_G
--------------------------------------------------------

  ALTER TABLE "TEMP_VALOR_G" MODIFY ("LOCU_ID" NOT NULL ENABLE);
  ALTER TABLE "TEMP_VALOR_G" MODIFY ("TEVG_TX_NOME_GRUPO" NOT NULL ENABLE);
  ALTER TABLE "TEMP_VALOR_G" MODIFY ("TEVG_NR_VALIDO" NOT NULL ENABLE);
  ALTER TABLE "TEMP_VALOR_G" ADD CONSTRAINT "PK_TEVG" PRIMARY KEY ("LOCU_ID", "TEVG_TX_NOME_GRUPO");
--------------------------------------------------------
--  Constraints for Table STATUS_PAGAMENTO
--------------------------------------------------------

  ALTER TABLE "STATUS_PAGAMENTO" MODIFY ("STPA_ID" NOT NULL ENABLE);
  ALTER TABLE "STATUS_PAGAMENTO" MODIFY ("STPA_TX_DESCRICAO" NOT NULL ENABLE);
  ALTER TABLE "STATUS_PAGAMENTO" ADD CONSTRAINT "PK_STPA" PRIMARY KEY ("STPA_ID");
--------------------------------------------------------
--  Constraints for Table TIPO_EXAME_LOCUS
--------------------------------------------------------

  ALTER TABLE "TIPO_EXAME_LOCUS" MODIFY ("LOCU_ID" NOT NULL ENABLE);
  ALTER TABLE "TIPO_EXAME_LOCUS" MODIFY ("TIEX_ID" NOT NULL ENABLE);
  ALTER TABLE "TIPO_EXAME_LOCUS" ADD CONSTRAINT "PK_TIEL" PRIMARY KEY ("LOCU_ID", "TIEX_ID");
--------------------------------------------------------
--  Constraints for Table PERMISSAO
--------------------------------------------------------

  ALTER TABLE "PERMISSAO" MODIFY ("RECU_ID" NOT NULL ENABLE);
  ALTER TABLE "PERMISSAO" MODIFY ("PERF_ID" NOT NULL ENABLE);
  ALTER TABLE "PERMISSAO" MODIFY ("PERM_IN_COM_RESTRICAO" NOT NULL ENABLE);
  ALTER TABLE "PERMISSAO" ADD CONSTRAINT "PK_PERM" PRIMARY KEY ("RECU_ID", "PERF_ID");
--------------------------------------------------------
--  Constraints for Table RACA
--------------------------------------------------------

  ALTER TABLE "RACA" MODIFY ("RACA_ID" NOT NULL ENABLE);
  ALTER TABLE "RACA" MODIFY ("RACA_TX_NOME" NOT NULL ENABLE);
  ALTER TABLE "RACA" ADD CONSTRAINT "PK_RACA" PRIMARY KEY ("RACA_ID");
--------------------------------------------------------
--  Constraints for Table AVALIACAO_PEDIDO_COLETA
--------------------------------------------------------

  ALTER TABLE "AVALIACAO_PEDIDO_COLETA" MODIFY ("AVPC_ID" NOT NULL ENABLE);
  ALTER TABLE "AVALIACAO_PEDIDO_COLETA" MODIFY ("AVPC_DT_CRIACAO" NOT NULL ENABLE);
  ALTER TABLE "AVALIACAO_PEDIDO_COLETA" ADD CONSTRAINT "PK_AVPC" PRIMARY KEY ("AVPC_ID");
--------------------------------------------------------
--  Constraints for Table VALOR_GENOTIPO_EXPAND_PACIENTE
--------------------------------------------------------

  ALTER TABLE "VALOR_GENOTIPO_EXPAND_PACIENTE" MODIFY ("VGEP_ID" NOT NULL ENABLE);
  ALTER TABLE "VALOR_GENOTIPO_EXPAND_PACIENTE" MODIFY ("VGEP_NR_POSICAO" NOT NULL ENABLE);
  ALTER TABLE "VALOR_GENOTIPO_EXPAND_PACIENTE" MODIFY ("VGEP_TX_VALOR" NOT NULL ENABLE);
  ALTER TABLE "VALOR_GENOTIPO_EXPAND_PACIENTE" MODIFY ("LOCU_ID" NOT NULL ENABLE);
  ALTER TABLE "VALOR_GENOTIPO_EXPAND_PACIENTE" MODIFY ("GEPA_ID" NOT NULL ENABLE);
  ALTER TABLE "VALOR_GENOTIPO_EXPAND_PACIENTE" MODIFY ("PACI_NR_RMR" NOT NULL ENABLE);
  ALTER TABLE "VALOR_GENOTIPO_EXPAND_PACIENTE" ADD CONSTRAINT "PK_VGEP" PRIMARY KEY ("VGEP_ID");
--------------------------------------------------------
--  Constraints for Table VALOR_GENOTIPO_PACIENTE
--------------------------------------------------------

  ALTER TABLE "VALOR_GENOTIPO_PACIENTE" MODIFY ("EXAM_ID" NOT NULL ENABLE);
  ALTER TABLE "VALOR_GENOTIPO_PACIENTE" MODIFY ("LOCU_ID" NOT NULL ENABLE);
  ALTER TABLE "VALOR_GENOTIPO_PACIENTE" MODIFY ("VAGP_TX_ALELO" NOT NULL ENABLE);
  ALTER TABLE "VALOR_GENOTIPO_PACIENTE" MODIFY ("VAGP_NR_POSICAO_ALELO" NOT NULL ENABLE);
  ALTER TABLE "VALOR_GENOTIPO_PACIENTE" MODIFY ("VAGP_NR_TIPO" NOT NULL ENABLE);
  ALTER TABLE "VALOR_GENOTIPO_PACIENTE" MODIFY ("GEPA_ID" NOT NULL ENABLE);
  ALTER TABLE "VALOR_GENOTIPO_PACIENTE" ADD CONSTRAINT "PK_VAGP" PRIMARY KEY ("EXAM_ID", "LOCU_ID", "VAGP_NR_POSICAO_ALELO");
--------------------------------------------------------
--  Constraints for Table REGISTRO
--------------------------------------------------------

  ALTER TABLE "REGISTRO" MODIFY ("REGI_ID" NOT NULL ENABLE);
  ALTER TABLE "REGISTRO" MODIFY ("PAIS_ID" NOT NULL ENABLE);
  ALTER TABLE "REGISTRO" ADD CONSTRAINT "PK_REGI" PRIMARY KEY ("REGI_ID");
--------------------------------------------------------
--  Constraints for Table RESPONSAVEL_AUD
--------------------------------------------------------

  ALTER TABLE "RESPONSAVEL_AUD" MODIFY ("RESP_ID" NOT NULL ENABLE);
  ALTER TABLE "RESPONSAVEL_AUD" MODIFY ("AUDI_ID" NOT NULL ENABLE);
  ALTER TABLE "RESPONSAVEL_AUD" ADD CONSTRAINT "PK_REAU" PRIMARY KEY ("RESP_ID", "AUDI_ID");
--------------------------------------------------------
--  Constraints for Table USUARIO_BANCO_SANGUE_CORDAO
--------------------------------------------------------

  ALTER TABLE "USUARIO_BANCO_SANGUE_CORDAO" MODIFY ("USUA_ID" NOT NULL ENABLE);
  ALTER TABLE "USUARIO_BANCO_SANGUE_CORDAO" MODIFY ("BASC_ID" NOT NULL ENABLE);
  ALTER TABLE "USUARIO_BANCO_SANGUE_CORDAO" ADD CONSTRAINT "PK_UBSC_ID" PRIMARY KEY ("BASC_ID");
--------------------------------------------------------
--  Constraints for Table LOCUS_EXAME
--------------------------------------------------------

  ALTER TABLE "LOCUS_EXAME" MODIFY ("LOEX_TX_PRIMEIRO_ALELO" NOT NULL ENABLE);
  ALTER TABLE "LOCUS_EXAME" MODIFY ("LOEX_TX_SEGUNDO_ALELO" NOT NULL ENABLE);
  ALTER TABLE "LOCUS_EXAME" MODIFY ("LOCU_ID" NOT NULL ENABLE);
  ALTER TABLE "LOCUS_EXAME" MODIFY ("EXAM_ID" NOT NULL ENABLE);
  ALTER TABLE "LOCUS_EXAME" MODIFY ("LOEX_IN_INATIVO" NOT NULL ENABLE);
  ALTER TABLE "LOCUS_EXAME" ADD CONSTRAINT "PK_LOEX" PRIMARY KEY ("LOCU_ID", "EXAM_ID");

--------------------------------------------------------
--  Constraints for Table PRE_CADASTRO_MEDICO_EMAIL
--------------------------------------------------------

  ALTER TABLE "PRE_CADASTRO_MEDICO_EMAIL" MODIFY ("PCEM_ID" NOT NULL ENABLE);
  ALTER TABLE "PRE_CADASTRO_MEDICO_EMAIL" MODIFY ("PCEM_IN_PRINCIPAL" NOT NULL ENABLE);
  ALTER TABLE "PRE_CADASTRO_MEDICO_EMAIL" MODIFY ("PRCM_ID" NOT NULL ENABLE);
  ALTER TABLE "PRE_CADASTRO_MEDICO_EMAIL" ADD CONSTRAINT "PK_PCEM" PRIMARY KEY ("PCEM_ID");
--------------------------------------------------------
--  Constraints for Table TEMP_VALOR_DNA
--------------------------------------------------------

  ALTER TABLE "TEMP_VALOR_DNA" ADD CONSTRAINT "PK_TEVD" PRIMARY KEY ("LOCU_ID", "TEVD_TX_VALOR");
--------------------------------------------------------
--  Constraints for Table LOCUS_EXAME_AUD
--------------------------------------------------------

  ALTER TABLE "LOCUS_EXAME_AUD" MODIFY ("EXAM_ID" NOT NULL ENABLE);
  ALTER TABLE "LOCUS_EXAME_AUD" MODIFY ("LOCU_ID" NOT NULL ENABLE);
  ALTER TABLE "LOCUS_EXAME_AUD" MODIFY ("AUDI_ID" NOT NULL ENABLE);
  ALTER TABLE "LOCUS_EXAME_AUD" ADD CONSTRAINT "PK_LOEA" PRIMARY KEY ("EXAM_ID", "LOCU_ID", "AUDI_ID");
--------------------------------------------------------
--  Constraints for Table MATCH_PRELIMINAR
--------------------------------------------------------

  ALTER TABLE "MATCH_PRELIMINAR" MODIFY ("MAPR_ID" NOT NULL ENABLE);
  ALTER TABLE "MATCH_PRELIMINAR" MODIFY ("DOAD_ID" NOT NULL ENABLE);
  ALTER TABLE "MATCH_PRELIMINAR" MODIFY ("BUPR_ID" NOT NULL ENABLE);
  ALTER TABLE "MATCH_PRELIMINAR" MODIFY ("MAPR_IN_FASE" NOT NULL ENABLE);
  ALTER TABLE "MATCH_PRELIMINAR" ADD CONSTRAINT "PK_MAPR" PRIMARY KEY ("MAPR_ID");
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
--  Constraints for Table BUSCA_PRELIMINAR
--------------------------------------------------------

  ALTER TABLE "BUSCA_PRELIMINAR" MODIFY ("BUPR_ID" NOT NULL ENABLE);
  ALTER TABLE "BUSCA_PRELIMINAR" MODIFY ("BUPR_TX_NOME_PACIENTE" NOT NULL ENABLE);
  ALTER TABLE "BUSCA_PRELIMINAR" MODIFY ("BUPR_DT_NASCIMENTO" NOT NULL ENABLE);
  ALTER TABLE "BUSCA_PRELIMINAR" MODIFY ("BUPR_TX_ABO" NOT NULL ENABLE);
  ALTER TABLE "BUSCA_PRELIMINAR" MODIFY ("BUPR_VL_PESO" NOT NULL ENABLE);
  ALTER TABLE "BUSCA_PRELIMINAR" MODIFY ("BUPR_DT_CADASTRO" NOT NULL ENABLE);
  ALTER TABLE "BUSCA_PRELIMINAR" MODIFY ("USUA_ID" NOT NULL ENABLE);
  ALTER TABLE "BUSCA_PRELIMINAR" ADD CONSTRAINT "PK_GENO" PRIMARY KEY ("BUPR_ID");
--------------------------------------------------------
--  Constraints for Table PEDIDO_ADICIONAL_WORKUP
--------------------------------------------------------

  ALTER TABLE "PEDIDO_ADICIONAL_WORKUP" MODIFY ("PEAW_ID" NOT NULL ENABLE);
  ALTER TABLE "PEDIDO_ADICIONAL_WORKUP" MODIFY ("PEAW_TX_DESCRICAO" NOT NULL ENABLE);
  ALTER TABLE "PEDIDO_ADICIONAL_WORKUP" MODIFY ("PEAW_DT_CRIACAO" NOT NULL ENABLE);
  ALTER TABLE "PEDIDO_ADICIONAL_WORKUP" MODIFY ("AVRW_ID" NOT NULL ENABLE);
  ALTER TABLE "PEDIDO_ADICIONAL_WORKUP" ADD CONSTRAINT "PK_PEAW" PRIMARY KEY ("PEAW_ID");
--------------------------------------------------------
--  Constraints for Table ETNIA
--------------------------------------------------------

  ALTER TABLE "ETNIA" MODIFY ("ETNI_ID" NOT NULL ENABLE);
  ALTER TABLE "ETNIA" MODIFY ("ETNI_TX_NOME" NOT NULL ENABLE);
  ALTER TABLE "ETNIA" ADD CONSTRAINT "PK_ETNI" PRIMARY KEY ("ETNI_ID");

--------------------------------------------------------
--  Constraints for Table PEDIDO_IDM_AUD
--------------------------------------------------------

  ALTER TABLE "PEDIDO_IDM_AUD" MODIFY ("PEID_ID" NOT NULL ENABLE);
  ALTER TABLE "PEDIDO_IDM_AUD" MODIFY ("AUDI_ID" NOT NULL ENABLE);
  ALTER TABLE "PEDIDO_IDM_AUD" MODIFY ("AUDI_TX_TIPO" NOT NULL ENABLE);
  ALTER TABLE "PEDIDO_IDM_AUD" ADD CONSTRAINT "PK_PEIA" PRIMARY KEY ("PEID_ID", "AUDI_ID");
--------------------------------------------------------
--  Constraints for Table RESPOSTA_TENTATIVA_CONTATO
--------------------------------------------------------

  ALTER TABLE "RESPOSTA_TENTATIVA_CONTATO" MODIFY ("RETC_ID" NOT NULL ENABLE);
  ALTER TABLE "RESPOSTA_TENTATIVA_CONTATO" MODIFY ("RETC_TX_DESCRICAO" NOT NULL ENABLE);
  ALTER TABLE "RESPOSTA_TENTATIVA_CONTATO" ADD CONSTRAINT "PK_RETC" PRIMARY KEY ("RETC_ID");
--------------------------------------------------------
--  Constraints for Table STATUS_AVALIACAO_CAMARA_TEC
--------------------------------------------------------

  ALTER TABLE "STATUS_AVALIACAO_CAMARA_TEC" MODIFY ("STCT_ID" NOT NULL ENABLE);
  ALTER TABLE "STATUS_AVALIACAO_CAMARA_TEC" MODIFY ("STCT_TX_DESCRICAO" NOT NULL ENABLE);
  ALTER TABLE "STATUS_AVALIACAO_CAMARA_TEC" ADD CONSTRAINT "PK_STCT_ID" PRIMARY KEY ("STCT_ID");

--------------------------------------------------------
--  Constraints for Table TEMP_SPLIT_VALOR_DNA
--------------------------------------------------------

  ALTER TABLE "TEMP_SPLIT_VALOR_DNA" MODIFY ("LOCU_ID" NOT NULL ENABLE);
  ALTER TABLE "TEMP_SPLIT_VALOR_DNA" MODIFY ("TESD_TX_GRUPO_ALELICO" NOT NULL ENABLE);
  ALTER TABLE "TEMP_SPLIT_VALOR_DNA" MODIFY ("TESD_TX_VALOR_ALELICO" NOT NULL ENABLE);
  ALTER TABLE "TEMP_SPLIT_VALOR_DNA" ADD CONSTRAINT "PK_TESD" PRIMARY KEY ("LOCU_ID", "TESD_TX_GRUPO_ALELICO", "TESD_TX_VALOR_ALELICO");
--------------------------------------------------------
--  Constraints for Table METODOLOGIA
--------------------------------------------------------

  ALTER TABLE "METODOLOGIA" MODIFY ("METO_ID" NOT NULL ENABLE);
  ALTER TABLE "METODOLOGIA" MODIFY ("METO_TX_SIGLA" NOT NULL ENABLE);
  ALTER TABLE "METODOLOGIA" MODIFY ("METO_TX_DESCRICAO" NOT NULL ENABLE);
  ALTER TABLE "METODOLOGIA" ADD CONSTRAINT "PK_METO" PRIMARY KEY ("METO_ID");
--------------------------------------------------------
--  Constraints for Table PENDENCIA_AUD
--------------------------------------------------------

  ALTER TABLE "PENDENCIA_AUD" MODIFY ("PEND_ID" NOT NULL ENABLE);
  ALTER TABLE "PENDENCIA_AUD" MODIFY ("AUDI_ID" NOT NULL ENABLE);
  ALTER TABLE "PENDENCIA_AUD" ADD CONSTRAINT "PK_PEAU" PRIMARY KEY ("PEND_ID", "AUDI_ID");
--------------------------------------------------------
--  Constraints for Table TIPO_LOGRADOURO_CORREIO
--------------------------------------------------------

  ALTER TABLE "TIPO_LOGRADOURO_CORREIO" MODIFY ("TILC_ID" NOT NULL ENABLE);
  ALTER TABLE "TIPO_LOGRADOURO_CORREIO" MODIFY ("TILC_TX_NOME" NOT NULL ENABLE);
  ALTER TABLE "TIPO_LOGRADOURO_CORREIO" MODIFY ("TILC_TX_NOME_ABREVIADO" NOT NULL ENABLE);
  ALTER TABLE "TIPO_LOGRADOURO_CORREIO" ADD CONSTRAINT "PK_TILC" PRIMARY KEY ("TILC_ID");
--------------------------------------------------------
--  Constraints for Table AVALIACAO_NOVA_BUSCA
--------------------------------------------------------

  ALTER TABLE "AVALIACAO_NOVA_BUSCA" MODIFY ("AVNB_ID" NOT NULL ENABLE);
  ALTER TABLE "AVALIACAO_NOVA_BUSCA" MODIFY ("PACI_NR_RMR" NOT NULL ENABLE);
  ALTER TABLE "AVALIACAO_NOVA_BUSCA" MODIFY ("AVNB_TX_STATUS" NOT NULL ENABLE);
  ALTER TABLE "AVALIACAO_NOVA_BUSCA" MODIFY ("AVNB_DT_CRIACAO" NOT NULL ENABLE);
  ALTER TABLE "AVALIACAO_NOVA_BUSCA" ADD CONSTRAINT "PK_AVNB" PRIMARY KEY ("AVNB_ID");
--------------------------------------------------------
--  Constraints for Table INSTRUCAO_COLETA
--------------------------------------------------------

  ALTER TABLE "INSTRUCAO_COLETA" MODIFY ("INCO_ID" NOT NULL ENABLE);
  ALTER TABLE "INSTRUCAO_COLETA" MODIFY ("INCO_TX_DESCRICAO" NOT NULL ENABLE);
  ALTER TABLE "INSTRUCAO_COLETA" MODIFY ("LABO_ID" NOT NULL ENABLE);
  ALTER TABLE "INSTRUCAO_COLETA" ADD CONSTRAINT "PK_INCO" PRIMARY KEY ("INCO_ID");
--------------------------------------------------------
--  Constraints for Table STATUS_PENDENCIA
--------------------------------------------------------

  ALTER TABLE "STATUS_PENDENCIA" MODIFY ("STPE_ID" NOT NULL ENABLE);
  ALTER TABLE "STATUS_PENDENCIA" MODIFY ("STPE_TX_DESCRICAO" NOT NULL ENABLE);
  ALTER TABLE "STATUS_PENDENCIA" ADD CONSTRAINT "PK_STPE" PRIMARY KEY ("STPE_ID");
--------------------------------------------------------
--  Constraints for Table AVALIACAO_AUD
--------------------------------------------------------

  ALTER TABLE "AVALIACAO_AUD" MODIFY ("AVAL_ID" NOT NULL ENABLE);
  ALTER TABLE "AVALIACAO_AUD" MODIFY ("AUDI_ID" NOT NULL ENABLE);
  ALTER TABLE "AVALIACAO_AUD" ADD CONSTRAINT "PK_AVAU" PRIMARY KEY ("AVAL_ID", "AUDI_ID");
--------------------------------------------------------
--  Constraints for Table TIPO_PENDENCIA
--------------------------------------------------------

  ALTER TABLE "TIPO_PENDENCIA" MODIFY ("TIPE_ID" NOT NULL ENABLE);
  ALTER TABLE "TIPO_PENDENCIA" MODIFY ("TIPE_TX_DESCRICAO" NOT NULL ENABLE);
  ALTER TABLE "TIPO_PENDENCIA" ADD CONSTRAINT "PK_TIPE" PRIMARY KEY ("TIPE_ID");
--------------------------------------------------------
--  Constraints for Table RESSALVA_DOADOR_AUD
--------------------------------------------------------

  ALTER TABLE "RESSALVA_DOADOR_AUD" MODIFY ("REDO_ID" NOT NULL ENABLE);
  ALTER TABLE "RESSALVA_DOADOR_AUD" MODIFY ("AUDI_ID" NOT NULL ENABLE);
  ALTER TABLE "RESSALVA_DOADOR_AUD" ADD CONSTRAINT "PK_REDO" PRIMARY KEY ("REDO_ID", "AUDI_ID");
--------------------------------------------------------
--  Constraints for Table SOLICITACAO_REDOMEWEB
--------------------------------------------------------

  ALTER TABLE "SOLICITACAO_REDOMEWEB" MODIFY ("SORE_ID" NOT NULL ENABLE);
  ALTER TABLE "SOLICITACAO_REDOMEWEB" MODIFY ("SORE_NR_TIPO" NOT NULL ENABLE);
  ALTER TABLE "SOLICITACAO_REDOMEWEB" MODIFY ("SORE_ID_SOLICITACAO_REDOMEWEB" NOT NULL ENABLE);
  ALTER TABLE "SOLICITACAO_REDOMEWEB" MODIFY ("SORE_DT_CRIACAO" NOT NULL ENABLE);
  ALTER TABLE "SOLICITACAO_REDOMEWEB" MODIFY ("PEEX_ID" NOT NULL ENABLE);
  ALTER TABLE "SOLICITACAO_REDOMEWEB" ADD CONSTRAINT "PK_SORE" PRIMARY KEY ("SORE_ID");
  ALTER TABLE "SOLICITACAO_REDOMEWEB" ADD CONSTRAINT "UK_SORE" UNIQUE ("SORE_ID_SOLICITACAO_REDOMEWEB", "SORE_NR_TIPO");
--------------------------------------------------------
--  Constraints for Table VALOR_GENOTIPO_DOADOR
--------------------------------------------------------

  ALTER TABLE "VALOR_GENOTIPO_DOADOR" MODIFY ("EXAM_ID" NOT NULL ENABLE);
  ALTER TABLE "VALOR_GENOTIPO_DOADOR" MODIFY ("LOCU_ID" NOT NULL ENABLE);
  ALTER TABLE "VALOR_GENOTIPO_DOADOR" MODIFY ("VAGD_TX_ALELO" NOT NULL ENABLE);
  ALTER TABLE "VALOR_GENOTIPO_DOADOR" MODIFY ("VAGD_NR_POSICAO_ALELO" NOT NULL ENABLE);
  ALTER TABLE "VALOR_GENOTIPO_DOADOR" MODIFY ("VAGD_NR_TIPO" NOT NULL ENABLE);
  ALTER TABLE "VALOR_GENOTIPO_DOADOR" MODIFY ("GEDO_ID" NOT NULL ENABLE);
  ALTER TABLE "VALOR_GENOTIPO_DOADOR" MODIFY ("VAGD_IN_TIPO_DOADOR" NOT NULL ENABLE);
  ALTER TABLE "VALOR_GENOTIPO_DOADOR" ADD CONSTRAINT "PK_VAGD" PRIMARY KEY ("EXAM_ID", "LOCU_ID", "VAGD_NR_POSICAO_ALELO");
--------------------------------------------------------
--  Constraints for Table PACIENTE_IN_REREME
--------------------------------------------------------

  ALTER TABLE "PACIENTE_IN_REREME" MODIFY ("PACI_NR_RMR" NOT NULL ENABLE);
  ALTER TABLE "PACIENTE_IN_REREME" MODIFY ("PAIR_DT_INCLUSAO" NOT NULL ENABLE);
  ALTER TABLE "PACIENTE_IN_REREME" MODIFY ("PAIR_IN_OPERACAO" NOT NULL ENABLE);
  ALTER TABLE "PACIENTE_IN_REREME" MODIFY ("PAIR_IN_STATUS_OPERACAO" NOT NULL ENABLE);
  ALTER TABLE "PACIENTE_IN_REREME" MODIFY ("PAIR_TX_REGISTRO_PACIENTE" NOT NULL ENABLE);
  ALTER TABLE "PACIENTE_IN_REREME" ADD CONSTRAINT "PK_PAIR" PRIMARY KEY ("PACI_NR_RMR", "PAIR_DT_INCLUSAO");
--------------------------------------------------------
--  Constraints for Table PRE_CADASTRO_MEDICO
--------------------------------------------------------

  ALTER TABLE "PRE_CADASTRO_MEDICO" MODIFY ("PRCM_ID" NOT NULL ENABLE);
  ALTER TABLE "PRE_CADASTRO_MEDICO" MODIFY ("PRCM_TX_CRM" NOT NULL ENABLE);
  ALTER TABLE "PRE_CADASTRO_MEDICO" MODIFY ("PRCM_TX_NOME" NOT NULL ENABLE);
  ALTER TABLE "PRE_CADASTRO_MEDICO" MODIFY ("PRCM_TX_LOGIN" NOT NULL ENABLE);
  ALTER TABLE "PRE_CADASTRO_MEDICO" MODIFY ("PRCM_TX_ARQUIVO_CRM" NOT NULL ENABLE);
  ALTER TABLE "PRE_CADASTRO_MEDICO" MODIFY ("PCME_ID" NOT NULL ENABLE);
  ALTER TABLE "PRE_CADASTRO_MEDICO" MODIFY ("PRCM_TX_STATUS" NOT NULL ENABLE);
  ALTER TABLE "PRE_CADASTRO_MEDICO" MODIFY ("PRCM_DT_SOLICITACAO" NOT NULL ENABLE);
  ALTER TABLE "PRE_CADASTRO_MEDICO" MODIFY ("PRCM_DT_ATUALIZACAO" NOT NULL ENABLE);
  ALTER TABLE "PRE_CADASTRO_MEDICO" ADD CONSTRAINT "PK_PRCM" PRIMARY KEY ("PRCM_ID");
--------------------------------------------------------
--  Constraints for Table TEMP_VALOR_P
--------------------------------------------------------

  ALTER TABLE "TEMP_VALOR_P" MODIFY ("LOCU_ID" NOT NULL ENABLE);
  ALTER TABLE "TEMP_VALOR_P" MODIFY ("TEVP_TX_GRUPO" NOT NULL ENABLE);
  ALTER TABLE "TEMP_VALOR_P" MODIFY ("TEVP_TX_NOME_GRUPO" NOT NULL ENABLE);
  ALTER TABLE "TEMP_VALOR_P" MODIFY ("TEVP_NR_VALIDO" NOT NULL ENABLE);
  ALTER TABLE "TEMP_VALOR_P" ADD CONSTRAINT "PK_TEVP" PRIMARY KEY ("LOCU_ID", "TEVP_TX_NOME_GRUPO");
--------------------------------------------------------
--  Constraints for Table QUALIFICACAO_MATCH_PRELIMINAR
--------------------------------------------------------

  ALTER TABLE "QUALIFICACAO_MATCH_PRELIMINAR" MODIFY ("QUMP_ID" NOT NULL ENABLE);
  ALTER TABLE "QUALIFICACAO_MATCH_PRELIMINAR" MODIFY ("LOCU_ID" NOT NULL ENABLE);
  ALTER TABLE "QUALIFICACAO_MATCH_PRELIMINAR" MODIFY ("QUMP_TX_QUALIFICACAO" NOT NULL ENABLE);
  ALTER TABLE "QUALIFICACAO_MATCH_PRELIMINAR" MODIFY ("QUMP_NR_POSICAO" NOT NULL ENABLE);
  ALTER TABLE "QUALIFICACAO_MATCH_PRELIMINAR" ADD CONSTRAINT "PK_QUMP" PRIMARY KEY ("QUMP_ID");
--------------------------------------------------------
--  Constraints for Table TIPO_BUSCA_CHECKLIST
--------------------------------------------------------

  ALTER TABLE "TIPO_BUSCA_CHECKLIST" MODIFY ("TIBC_ID" NOT NULL ENABLE);
  ALTER TABLE "TIPO_BUSCA_CHECKLIST" ADD CONSTRAINT "TIBC_ID" PRIMARY KEY ("TIBC_ID");
  
--------------------------------------------------------
--  Constraints for Table ESTADO_CIVIL
--------------------------------------------------------  
ALTER TABLE "ESTADO_CIVIL" ADD CONSTRAINT "PK_ESCI" PRIMARY KEY ("ESCI_ID");  

--------------------------------------------------------
--  Constraints for Table CID
--------------------------------------------------------

  ALTER TABLE "CID" MODIFY ("CID_ID" NOT NULL ENABLE);
  ALTER TABLE "CID" MODIFY ("CID_TX_CODIGO" NOT NULL ENABLE);
  ALTER TABLE "CID" MODIFY ("CID_IN_TRANSPLANTE" NOT NULL ENABLE);
  ALTER TABLE "CID" ADD CONSTRAINT "PK_CID" PRIMARY KEY ("CID_ID");
--------------------------------------------------------
--  Constraints for Table HISTORICO_STATUS_PACIENTE
--------------------------------------------------------

  ALTER TABLE "HISTORICO_STATUS_PACIENTE" MODIFY ("HISP_ID" NOT NULL ENABLE);
  ALTER TABLE "HISTORICO_STATUS_PACIENTE" MODIFY ("STPA_ID" NOT NULL ENABLE);
  ALTER TABLE "HISTORICO_STATUS_PACIENTE" MODIFY ("HISP_DT_ALTERACAO" NOT NULL ENABLE);
  ALTER TABLE "HISTORICO_STATUS_PACIENTE" MODIFY ("PACI_NR_RMR" NOT NULL ENABLE);
  ALTER TABLE "HISTORICO_STATUS_PACIENTE" ADD CONSTRAINT "PK_HISP_ID" PRIMARY KEY ("HISP_ID");
--------------------------------------------------------
--  Constraints for Table USUARIO
--------------------------------------------------------

  ALTER TABLE "USUARIO" MODIFY ("USUA_ID" NOT NULL ENABLE);
  ALTER TABLE "USUARIO" MODIFY ("USUA_TX_NOME" NOT NULL ENABLE);
  ALTER TABLE "USUARIO" MODIFY ("USUA_TX_USERNAME" NOT NULL ENABLE);
  ALTER TABLE "USUARIO" MODIFY ("USUA_TX_PASSWORD" NOT NULL ENABLE);
  ALTER TABLE "USUARIO" MODIFY ("USUA_IN_ATIVO" NOT NULL ENABLE);
  ALTER TABLE "USUARIO" MODIFY ("USUA_TX_EMAIL" NOT NULL ENABLE);
  ALTER TABLE "USUARIO" ADD CONSTRAINT "PK_USUA" PRIMARY KEY ("USUA_ID");
--------------------------------------------------------
--  Constraints for Table DISPONIBILIDADE
--------------------------------------------------------

  ALTER TABLE "DISPONIBILIDADE" MODIFY ("DISP_ID" NOT NULL ENABLE);
  ALTER TABLE "DISPONIBILIDADE" MODIFY ("DISP_TX_DESCRICAO" NOT NULL ENABLE);
  ALTER TABLE "DISPONIBILIDADE" MODIFY ("DISP_DT_CRIACAO" NOT NULL ENABLE);
  ALTER TABLE "DISPONIBILIDADE" ADD CONSTRAINT "PK_DISP" PRIMARY KEY ("DISP_ID");
--------------------------------------------------------
--  Constraints for Table STATUS_PEDIDO_COLETA
--------------------------------------------------------

  ALTER TABLE "STATUS_PEDIDO_COLETA" MODIFY ("STPC_ID" NOT NULL ENABLE);
  ALTER TABLE "STATUS_PEDIDO_COLETA" MODIFY ("STPC_TX_DESCRICAO" NOT NULL ENABLE);
  ALTER TABLE "STATUS_PEDIDO_COLETA" ADD CONSTRAINT "PK_STPC" PRIMARY KEY ("STPC_ID");
--------------------------------------------------------
--  Constraints for Table MATCH
--------------------------------------------------------

  ALTER TABLE "MATCH" MODIFY ("MATC_ID" NOT NULL ENABLE);
  ALTER TABLE "MATCH" MODIFY ("MATC_IN_STATUS" NOT NULL ENABLE);
  ALTER TABLE "MATCH" MODIFY ("BUSC_ID" NOT NULL ENABLE);
  ALTER TABLE "MATCH" MODIFY ("DOAD_ID" NOT NULL ENABLE);
  ALTER TABLE "MATCH" ADD CONSTRAINT "PK_MATCH" PRIMARY KEY ("MATC_ID");
--------------------------------------------------------
--  Constraints for Table TEMP_VALOR_DNA_NMDP
--------------------------------------------------------

  ALTER TABLE "TEMP_VALOR_DNA_NMDP" MODIFY ("LOCU_ID" NOT NULL ENABLE);
  ALTER TABLE "TEMP_VALOR_DNA_NMDP" MODIFY ("DNNM_TX_VALOR" NOT NULL ENABLE);
  ALTER TABLE "TEMP_VALOR_DNA_NMDP" MODIFY ("DNNM_TX_NOME_GRUPO" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table EXAME
--------------------------------------------------------

  ALTER TABLE "EXAME" MODIFY ("EXAM_ID" NOT NULL ENABLE);
  ALTER TABLE "EXAME" MODIFY ("EXAM_DT_CRIACAO" NOT NULL ENABLE);
  ALTER TABLE "EXAME" MODIFY ("USUA_ID" NOT NULL ENABLE);
  ALTER TABLE "EXAME" MODIFY ("EXAM_NR_STATUS" NOT NULL ENABLE);
  ALTER TABLE "EXAME" MODIFY ("EXAME_IN_CONFIRMATORIO" NOT NULL ENABLE);
  ALTER TABLE "EXAME" MODIFY ("EXAME_NR_CONFIRMATORIO" NOT NULL ENABLE);
  ALTER TABLE "EXAME" ADD CONSTRAINT "PK_EXAM" PRIMARY KEY ("EXAM_ID");
--------------------------------------------------------
--  Constraints for Table PEDIDO_LOGISTICA
--------------------------------------------------------

  ALTER TABLE "PEDIDO_LOGISTICA" MODIFY ("PELO_ID" NOT NULL ENABLE);
  ALTER TABLE "PEDIDO_LOGISTICA" MODIFY ("PELO_DT_CRIACAO" NOT NULL ENABLE);
  ALTER TABLE "PEDIDO_LOGISTICA" MODIFY ("PELO_IN_TIPO" NOT NULL ENABLE);
  ALTER TABLE "PEDIDO_LOGISTICA" MODIFY ("STPL_ID" NOT NULL ENABLE);
  ALTER TABLE "PEDIDO_LOGISTICA" MODIFY ("TIPL_IN_ORIGEM" NOT NULL ENABLE);
  ALTER TABLE "PEDIDO_LOGISTICA" ADD CONSTRAINT "PK_PELO" PRIMARY KEY ("PELO_ID");
--------------------------------------------------------
--  Constraints for Table GENOTIPO_BUSCA_PRELIMINAR
--------------------------------------------------------

  ALTER TABLE "GENOTIPO_BUSCA_PRELIMINAR" MODIFY ("GEBP_ID" NOT NULL ENABLE);
  ALTER TABLE "GENOTIPO_BUSCA_PRELIMINAR" MODIFY ("GEBP_TX_CODIGO_LOCUS" NOT NULL ENABLE);
  ALTER TABLE "GENOTIPO_BUSCA_PRELIMINAR" MODIFY ("GEBP_TX_ALELO" NOT NULL ENABLE);
  ALTER TABLE "GENOTIPO_BUSCA_PRELIMINAR" MODIFY ("GEBP_NR_POSICAO_ALELO" NOT NULL ENABLE);
  ALTER TABLE "GENOTIPO_BUSCA_PRELIMINAR" MODIFY ("GEBP_IN_COMPOSICAO_VALOR" NOT NULL ENABLE);
  ALTER TABLE "GENOTIPO_BUSCA_PRELIMINAR" MODIFY ("BUPR_ID" NOT NULL ENABLE);
  ALTER TABLE "GENOTIPO_BUSCA_PRELIMINAR" ADD CONSTRAINT "PK_GEBP" PRIMARY KEY ("GEBP_ID");
--------------------------------------------------------
--  Constraints for Table SOLICITACAO
--------------------------------------------------------

  ALTER TABLE "SOLICITACAO" MODIFY ("SOLI_ID" NOT NULL ENABLE);
  ALTER TABLE "SOLICITACAO" MODIFY ("TISO_ID" NOT NULL ENABLE);
  ALTER TABLE "SOLICITACAO" MODIFY ("SOLI_DT_CRIACAO" NOT NULL ENABLE);
  ALTER TABLE "SOLICITACAO" MODIFY ("SOLI_NR_STATUS" NOT NULL ENABLE);
  ALTER TABLE "SOLICITACAO" ADD CONSTRAINT "PK_SOLI" PRIMARY KEY ("SOLI_ID");
--------------------------------------------------------
--  Constraints for Table METODOLOGIA_EXAME_AUD
--------------------------------------------------------

  ALTER TABLE "METODOLOGIA_EXAME_AUD" MODIFY ("AUDI_ID" NOT NULL ENABLE);
  ALTER TABLE "METODOLOGIA_EXAME_AUD" MODIFY ("EXAM_ID" NOT NULL ENABLE);
  ALTER TABLE "METODOLOGIA_EXAME_AUD" MODIFY ("METO_ID" NOT NULL ENABLE);
  ALTER TABLE "METODOLOGIA_EXAME_AUD" ADD CONSTRAINT "PK_MEEA" PRIMARY KEY ("AUDI_ID", "EXAM_ID", "METO_ID");
--------------------------------------------------------
--  Constraints for Table PAIS_CORREIO
--------------------------------------------------------

  ALTER TABLE "PAIS_CORREIO" MODIFY ("PACO_ID" NOT NULL ENABLE);
  ALTER TABLE "PAIS_CORREIO" MODIFY ("PACO_TX_NOME" NOT NULL ENABLE);
  ALTER TABLE "PAIS_CORREIO" ADD CONSTRAINT "PK_PACO" PRIMARY KEY ("PACO_ID");
--------------------------------------------------------
--  Constraints for Table DOADOR
--------------------------------------------------------

  ALTER TABLE "DOADOR" MODIFY ("DOAD_ID" NOT NULL ENABLE);
  ALTER TABLE "DOADOR" MODIFY ("DOAD_IN_SEXO" NOT NULL ENABLE);
  ALTER TABLE "DOADOR" MODIFY ("DOAD_TX_ABO" NOT NULL ENABLE);
  ALTER TABLE "DOADOR" MODIFY ("DOAD_DT_ATUALIZACAO" NOT NULL ENABLE);
  ALTER TABLE "DOADOR" MODIFY ("STDO_ID" NOT NULL ENABLE);
  ALTER TABLE "DOADOR" MODIFY ("REGI_ID_ORIGEM" NOT NULL ENABLE);
  ALTER TABLE "DOADOR" MODIFY ("DOAD_IN_TIPO" NOT NULL ENABLE);  
  ALTER TABLE "DOADOR" ADD CONSTRAINT "UK_DOAD_NR_DMR" UNIQUE ("DOAD_NR_DMR");
  ALTER TABLE "DOADOR" ADD CONSTRAINT "PK_DOAD" PRIMARY KEY ("DOAD_ID");
  
--------------------------------------------------------
--  Constraints for Table TURNO
--------------------------------------------------------

  ALTER TABLE "TURNO" MODIFY ("TURN_ID" NOT NULL ENABLE);
  ALTER TABLE "TURNO" MODIFY ("TURN_TX_DESCRICAO" NOT NULL ENABLE);
  ALTER TABLE "TURNO" MODIFY ("TURN_DT_HR_INICIO" NOT NULL ENABLE);
  ALTER TABLE "TURNO" MODIFY ("TURN_DT_HR_FIM" NOT NULL ENABLE);
  ALTER TABLE "TURNO" MODIFY ("TURN_IN_INCLUSIVO_EXCLUSIVO" NOT NULL ENABLE);
  ALTER TABLE "TURNO" ADD CONSTRAINT "PK_TURNO" PRIMARY KEY ("TURN_ID");
--------------------------------------------------------
--  Constraints for Table STATUS_PEDIDO_WORKUP
--------------------------------------------------------

  ALTER TABLE "STATUS_PEDIDO_WORKUP" MODIFY ("STPW_ID" NOT NULL ENABLE);
  ALTER TABLE "STATUS_PEDIDO_WORKUP" MODIFY ("STPW_DESCRICAO" NOT NULL ENABLE);
  ALTER TABLE "STATUS_PEDIDO_WORKUP" ADD CONSTRAINT "PK_STPW" PRIMARY KEY ("STPW_ID");
--------------------------------------------------------
--  Constraints for Table LOCALIDADE_CORREIO
--------------------------------------------------------

  ALTER TABLE "LOCALIDADE_CORREIO" MODIFY ("LOCC_ID" NOT NULL ENABLE);
  ALTER TABLE "LOCALIDADE_CORREIO" MODIFY ("LOCC_TX_NOME" NOT NULL ENABLE);
  ALTER TABLE "LOCALIDADE_CORREIO" MODIFY ("LOCC_IN_TIPO" NOT NULL ENABLE);
  ALTER TABLE "LOCALIDADE_CORREIO" MODIFY ("LOCC_TX_DNE" NOT NULL ENABLE);
  ALTER TABLE "LOCALIDADE_CORREIO" ADD CONSTRAINT "PK_LOCO" PRIMARY KEY ("LOCC_ID");
  ALTER TABLE "LOCALIDADE_CORREIO" ADD CONSTRAINT "CK_TP_LOCC" CHECK (LOCC_IN_TIPO in ('D', 'M', 'P', 'R'));
--------------------------------------------------------
--  Constraints for Table RESPOSTA_CHECKLIST
--------------------------------------------------------

  ALTER TABLE "RESPOSTA_CHECKLIST" MODIFY ("RESC_ID" NOT NULL ENABLE);
  ALTER TABLE "RESPOSTA_CHECKLIST" MODIFY ("RESC_IN_RESPOSTA" NOT NULL ENABLE);
  ALTER TABLE "RESPOSTA_CHECKLIST" MODIFY ("ITEC_ID" NOT NULL ENABLE);
  ALTER TABLE "RESPOSTA_CHECKLIST" MODIFY ("PELO_ID" NOT NULL ENABLE);
  ALTER TABLE "RESPOSTA_CHECKLIST" ADD CONSTRAINT "PK_RESC_ID" PRIMARY KEY ("RESC_ID");
--------------------------------------------------------
--  Constraints for Table USUARIO_PERFIL
--------------------------------------------------------

  ALTER TABLE "USUARIO_PERFIL" MODIFY ("USUA_ID" NOT NULL ENABLE);
  ALTER TABLE "USUARIO_PERFIL" MODIFY ("PERF_ID" NOT NULL ENABLE);
  ALTER TABLE "USUARIO_PERFIL" ADD CONSTRAINT "PK_USPE" PRIMARY KEY ("USUA_ID", "PERF_ID");
--------------------------------------------------------
--  Constraints for Table ARQUIVO_RESULTADO_WORKUP
--------------------------------------------------------

  ALTER TABLE "ARQUIVO_RESULTADO_WORKUP" MODIFY ("ARRW_ID" NOT NULL ENABLE);
  ALTER TABLE "ARQUIVO_RESULTADO_WORKUP" MODIFY ("ARRW_TX_CAMINHO" NOT NULL ENABLE);
  ALTER TABLE "ARQUIVO_RESULTADO_WORKUP" MODIFY ("REWO_ID" NOT NULL ENABLE);
  ALTER TABLE "ARQUIVO_RESULTADO_WORKUP" ADD CONSTRAINT "PK_ARRW" PRIMARY KEY ("ARRW_ID");
--------------------------------------------------------
--  Constraints for Table RASCUNHO
--------------------------------------------------------

  ALTER TABLE "RASCUNHO" MODIFY ("RASC_ID" NOT NULL ENABLE);
  ALTER TABLE "RASCUNHO" MODIFY ("USUA_ID" NOT NULL ENABLE);
  ALTER TABLE "RASCUNHO" MODIFY ("RASC_TX_JSON" NOT NULL ENABLE);
  ALTER TABLE "RASCUNHO" ADD CONSTRAINT "PK_RASC" PRIMARY KEY ("RASC_ID");
--------------------------------------------------------
--  Constraints for Table DESTINO_COLETA
--------------------------------------------------------

  ALTER TABLE "DESTINO_COLETA" MODIFY ("DECO_ID" NOT NULL ENABLE);
  ALTER TABLE "DESTINO_COLETA" MODIFY ("DECO_TX_DESCRICAO" NOT NULL ENABLE);
  ALTER TABLE "DESTINO_COLETA" ADD CONSTRAINT "PK_DECO" PRIMARY KEY ("DECO_ID");
--------------------------------------------------------
--  Constraints for Table DISPONIBILIDADE_AUD
--------------------------------------------------------

  ALTER TABLE "DISPONIBILIDADE_AUD" MODIFY ("DISP_ID" NOT NULL ENABLE);
  ALTER TABLE "DISPONIBILIDADE_AUD" MODIFY ("AUDI_ID" NOT NULL ENABLE);
  ALTER TABLE "DISPONIBILIDADE_AUD" ADD CONSTRAINT "PK_DISA" PRIMARY KEY ("DISP_ID", "AUDI_ID");
--------------------------------------------------------
--  Constraints for Table SPLIT_VALOR_DNA
--------------------------------------------------------

  ALTER TABLE "SPLIT_VALOR_DNA" MODIFY ("LOCU_ID" NOT NULL ENABLE);
  ALTER TABLE "SPLIT_VALOR_DNA" MODIFY ("SPVD_TX_GRUPO_ALELICO" NOT NULL ENABLE);
  ALTER TABLE "SPLIT_VALOR_DNA" MODIFY ("SPVD_TX_VALOR_ALELICO" NOT NULL ENABLE);
  ALTER TABLE "SPLIT_VALOR_DNA" MODIFY ("SPVD_IN_COMPARA_NMDP" NOT NULL ENABLE);
  ALTER TABLE "SPLIT_VALOR_DNA" ADD CONSTRAINT "PK_SPVD" PRIMARY KEY ("LOCU_ID", "SPVD_TX_GRUPO_ALELICO", "SPVD_TX_VALOR_ALELICO");
--------------------------------------------------------
--  Constraints for Table VALOR_P
--------------------------------------------------------

  ALTER TABLE "VALOR_P" MODIFY ("LOCU_ID" NOT NULL ENABLE);
  ALTER TABLE "VALOR_P" MODIFY ("VALP_TX_NOME_GRUPO" NOT NULL ENABLE);
  ALTER TABLE "VALOR_P" MODIFY ("VALP_NR_VALIDO" NOT NULL ENABLE);
  ALTER TABLE "VALOR_P" ADD CONSTRAINT "PK_VALP" PRIMARY KEY ("LOCU_ID", "VALP_TX_NOME_GRUPO");
--------------------------------------------------------
--  Constraints for Table SPLIT_NMDP
--------------------------------------------------------

  ALTER TABLE "SPLIT_NMDP" MODIFY ("TEVN_ID_CODIGO" NOT NULL ENABLE);
  ALTER TABLE "SPLIT_NMDP" MODIFY ("SPNM_TX_GRUPO_ALELICO" NOT NULL ENABLE);
  ALTER TABLE "SPLIT_NMDP" MODIFY ("SPNM_TX_VALOR_ALELICO" NOT NULL ENABLE);
  ALTER TABLE "SPLIT_NMDP" ADD CONSTRAINT "PK_SPNM" PRIMARY KEY ("TEVN_ID_CODIGO", "SPNM_TX_GRUPO_ALELICO", "SPNM_TX_VALOR_ALELICO");
--------------------------------------------------------
--  Constraints for Table PEDIDO_IDM
--------------------------------------------------------

  ALTER TABLE "PEDIDO_IDM" MODIFY ("PEID_ID" NOT NULL ENABLE);
  ALTER TABLE "PEDIDO_IDM" MODIFY ("PEID_DT_CRIACAO" NOT NULL ENABLE);
  ALTER TABLE "PEDIDO_IDM" MODIFY ("SOLI_ID" NOT NULL ENABLE);
  ALTER TABLE "PEDIDO_IDM" MODIFY ("STPI_ID" NOT NULL ENABLE);
  ALTER TABLE "PEDIDO_IDM" ADD CONSTRAINT "PK_PEID" PRIMARY KEY ("PEID_ID");
--------------------------------------------------------
--  Constraints for Table SISTEMA
--------------------------------------------------------

  ALTER TABLE "SISTEMA" MODIFY ("SIST_ID" NOT NULL ENABLE);
  ALTER TABLE "SISTEMA" MODIFY ("SIST_TX_NOME" NOT NULL ENABLE);
  ALTER TABLE "SISTEMA" ADD CONSTRAINT "PK_SIST" PRIMARY KEY ("SIST_ID");
--------------------------------------------------------
--  Constraints for Table RESSALVA_DOADOR
--------------------------------------------------------

  ALTER TABLE "RESSALVA_DOADOR" MODIFY ("REDO_ID" NOT NULL ENABLE);
  ALTER TABLE "RESSALVA_DOADOR" MODIFY ("REDO_TX_OBSERVACAO" NOT NULL ENABLE);
  ALTER TABLE "RESSALVA_DOADOR" MODIFY ("DOAD_ID" NOT NULL ENABLE);
  ALTER TABLE "RESSALVA_DOADOR" MODIFY ("REDO_DT_CRIACAO" NOT NULL ENABLE);
  ALTER TABLE "RESSALVA_DOADOR" MODIFY ("USUA_ID_CRIACAO" NOT NULL ENABLE);
  ALTER TABLE "RESSALVA_DOADOR" MODIFY ("REDO_IN_EXCLUIDO" NOT NULL ENABLE);
  ALTER TABLE "RESSALVA_DOADOR" ADD CONSTRAINT "PK_OBDO" PRIMARY KEY ("REDO_ID");
--------------------------------------------------------
--  Constraints for Table PEDIDO_ENRIQUECIMENTO
--------------------------------------------------------

  ALTER TABLE "PEDIDO_ENRIQUECIMENTO" MODIFY ("PEEN_ID" NOT NULL ENABLE);
  ALTER TABLE "PEDIDO_ENRIQUECIMENTO" MODIFY ("SOLI_ID" NOT NULL ENABLE);
  ALTER TABLE "PEDIDO_ENRIQUECIMENTO" MODIFY ("PEEN_IN_ABERTO" NOT NULL ENABLE);
  ALTER TABLE "PEDIDO_ENRIQUECIMENTO" MODIFY ("PEEN_IN_TIPO_ENRIQUECIMENTO" NOT NULL ENABLE);
  ALTER TABLE "PEDIDO_ENRIQUECIMENTO" ADD CONSTRAINT "PK_PEEN_ID" PRIMARY KEY ("PEEN_ID");
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
--  Constraints for Table ESTAGIO_DOENCA
--------------------------------------------------------

  ALTER TABLE "ESTAGIO_DOENCA" MODIFY ("ESDO_ID" NOT NULL ENABLE);
  ALTER TABLE "ESTAGIO_DOENCA" MODIFY ("ESDO_TX_DESCRICAO" NOT NULL ENABLE);
  ALTER TABLE "ESTAGIO_DOENCA" ADD CONSTRAINT "PK_ESDO" PRIMARY KEY ("ESDO_ID");
--------------------------------------------------------
--  Constraints for Table RESPOSTA_PEDIDO_ADICIONAL
--------------------------------------------------------

  ALTER TABLE "RESPOSTA_PEDIDO_ADICIONAL" MODIFY ("REPA_ID" NOT NULL ENABLE);
  ALTER TABLE "RESPOSTA_PEDIDO_ADICIONAL" MODIFY ("USUA_ID" NOT NULL ENABLE);
  ALTER TABLE "RESPOSTA_PEDIDO_ADICIONAL" MODIFY ("REPA_DT_DATA" NOT NULL ENABLE);
  ALTER TABLE "RESPOSTA_PEDIDO_ADICIONAL" MODIFY ("REPA_TX_RESPOSTA" NOT NULL ENABLE);
  ALTER TABLE "RESPOSTA_PEDIDO_ADICIONAL" MODIFY ("PEAW_ID" NOT NULL ENABLE);
  ALTER TABLE "RESPOSTA_PEDIDO_ADICIONAL" ADD CONSTRAINT "PK_REPA" PRIMARY KEY ("REPA_ID");
--------------------------------------------------------
--  Constraints for Table CONTROLE_JOB_BRASILCORD
--------------------------------------------------------

  ALTER TABLE "CONTROLE_JOB_BRASILCORD" MODIFY ("COJB_ID" NOT NULL ENABLE);
  ALTER TABLE "CONTROLE_JOB_BRASILCORD" MODIFY ("COJB_DT_SINCRONIZACAO" NOT NULL ENABLE);
  ALTER TABLE "CONTROLE_JOB_BRASILCORD" MODIFY ("COJB_IN_SUCESSO" NOT NULL ENABLE);
  ALTER TABLE "CONTROLE_JOB_BRASILCORD" ADD CONSTRAINT "PK_COJB" PRIMARY KEY ("COJB_ID");
--------------------------------------------------------
--  Constraints for Table ENDERECO_CONTATO_AUD
--------------------------------------------------------

  ALTER TABLE "ENDERECO_CONTATO_AUD" MODIFY ("ENCO_ID" NOT NULL ENABLE);
  ALTER TABLE "ENDERECO_CONTATO_AUD" MODIFY ("AUDI_ID" NOT NULL ENABLE);
  ALTER TABLE "ENDERECO_CONTATO_AUD" ADD CONSTRAINT "PK_ENCA" PRIMARY KEY ("ENCO_ID", "AUDI_ID");
--------------------------------------------------------
--  Constraints for Table STATUS_PEDIDO_EXAME
--------------------------------------------------------

  ALTER TABLE "STATUS_PEDIDO_EXAME" MODIFY ("STPX_ID" NOT NULL ENABLE);
  ALTER TABLE "STATUS_PEDIDO_EXAME" MODIFY ("STPX_TX_DESCRICAO" NOT NULL ENABLE);
  ALTER TABLE "STATUS_PEDIDO_EXAME" ADD CONSTRAINT "PK_STPX" PRIMARY KEY ("STPX_ID");
--------------------------------------------------------
--  Constraints for Table ARQUIVO_VOUCHER_LOGISTICA
--------------------------------------------------------

  ALTER TABLE "ARQUIVO_VOUCHER_LOGISTICA" MODIFY ("ARVL_ID" NOT NULL ENABLE);
  ALTER TABLE "ARQUIVO_VOUCHER_LOGISTICA" MODIFY ("ARVL_TX_CAMINHO" NOT NULL ENABLE);
  ALTER TABLE "ARQUIVO_VOUCHER_LOGISTICA" MODIFY ("ARVL_IN_EXCLUIDO" NOT NULL ENABLE);
  ALTER TABLE "ARQUIVO_VOUCHER_LOGISTICA" MODIFY ("ARVL_NR_TIPO" NOT NULL ENABLE);
  ALTER TABLE "ARQUIVO_VOUCHER_LOGISTICA" MODIFY ("PELO_ID" NOT NULL ENABLE);
  ALTER TABLE "ARQUIVO_VOUCHER_LOGISTICA" ADD CONSTRAINT "PK_ARVL" PRIMARY KEY ("ARVL_ID");
--------------------------------------------------------
--  Constraints for Table TIPO_PEDIDO_LOGISTICA
--------------------------------------------------------

  ALTER TABLE "TIPO_PEDIDO_LOGISTICA" MODIFY ("TIPL_ID" NOT NULL ENABLE);
  ALTER TABLE "TIPO_PEDIDO_LOGISTICA" MODIFY ("TIPL_TX_DESCRICAO" NOT NULL ENABLE);
  ALTER TABLE "TIPO_PEDIDO_LOGISTICA" ADD CONSTRAINT "PK_TIPL" PRIMARY KEY ("TIPL_ID");
--------------------------------------------------------
--  Constraints for Table DOADOR_AUD
--------------------------------------------------------

  ALTER TABLE "DOADOR_AUD" MODIFY ("AUDI_ID" NOT NULL ENABLE);
  ALTER TABLE "DOADOR_AUD" MODIFY ("DOAD_ID" NOT NULL ENABLE);
  ALTER TABLE "DOADOR_AUD" ADD CONSTRAINT "PK_DOAU" PRIMARY KEY ("AUDI_ID", "DOAD_ID");
--------------------------------------------------------
--  Constraints for Table TENTATIVA_CONTATO_DOADOR
--------------------------------------------------------

  ALTER TABLE "TENTATIVA_CONTATO_DOADOR" MODIFY ("TECD_ID" NOT NULL ENABLE);
  ALTER TABLE "TENTATIVA_CONTATO_DOADOR" MODIFY ("TECD_DT_CRIACAO" NOT NULL ENABLE);
  ALTER TABLE "TENTATIVA_CONTATO_DOADOR" MODIFY ("PECO_ID" NOT NULL ENABLE);
  ALTER TABLE "TENTATIVA_CONTATO_DOADOR" ADD CONSTRAINT "PK_TECD" PRIMARY KEY ("TECD_ID");
--------------------------------------------------------
--  Constraints for Table PAGAMENTO
--------------------------------------------------------

  ALTER TABLE "PAGAMENTO" MODIFY ("PAGA_ID" NOT NULL ENABLE);
  ALTER TABLE "PAGAMENTO" MODIFY ("PAGA_DT_CRIACAO" NOT NULL ENABLE);
  ALTER TABLE "PAGAMENTO" MODIFY ("PAGA_DT_ATUALIZACAO" NOT NULL ENABLE);
  ALTER TABLE "PAGAMENTO" MODIFY ("PAGA_IN_COBRACA" NOT NULL ENABLE);
  ALTER TABLE "PAGAMENTO" MODIFY ("TISE_ID" NOT NULL ENABLE);
  ALTER TABLE "PAGAMENTO" MODIFY ("STPA_ID" NOT NULL ENABLE);
  ALTER TABLE "PAGAMENTO" MODIFY ("REGI_ID" NOT NULL ENABLE);
  ALTER TABLE "PAGAMENTO" MODIFY ("MATC_ID" NOT NULL ENABLE);
  ALTER TABLE "PAGAMENTO" ADD CONSTRAINT "PK_PAGA" PRIMARY KEY ("PAGA_ID");
--------------------------------------------------------
--  Constraints for Table ARQUIVO_PEDIDO_IDM
--------------------------------------------------------

  ALTER TABLE "ARQUIVO_PEDIDO_IDM" MODIFY ("ARPI_ID" NOT NULL ENABLE);
  ALTER TABLE "ARQUIVO_PEDIDO_IDM" MODIFY ("ARPI_TX_CAMINHO" NOT NULL ENABLE);
  ALTER TABLE "ARQUIVO_PEDIDO_IDM" ADD CONSTRAINT "PK_ARPI" PRIMARY KEY ("ARPI_ID");
--------------------------------------------------------
--  Constraints for Table AVALIACAO_PRESCRICAO
--------------------------------------------------------

  ALTER TABLE "AVALIACAO_PRESCRICAO" MODIFY ("AVPR_ID" NOT NULL ENABLE);
  ALTER TABLE "AVALIACAO_PRESCRICAO" MODIFY ("AVPR_DT_CRICAO" NOT NULL ENABLE);
  ALTER TABLE "AVALIACAO_PRESCRICAO" MODIFY ("AVPR_DT_ATUALIZACAO" NOT NULL ENABLE);
  ALTER TABLE "AVALIACAO_PRESCRICAO" MODIFY ("PRES_ID" NOT NULL ENABLE);
  ALTER TABLE "AVALIACAO_PRESCRICAO" ADD CONSTRAINT "PK_AVPR" PRIMARY KEY ("AVPR_ID");
--------------------------------------------------------
--  Constraints for Table RESERVA_DOADOR_INTERNACIONAL
--------------------------------------------------------

  ALTER TABLE "RESERVA_DOADOR_INTERNACIONAL" MODIFY ("REDI_ID" NOT NULL ENABLE);
  ALTER TABLE "RESERVA_DOADOR_INTERNACIONAL" MODIFY ("REDI_DT_ATUALIZACAO" NOT NULL ENABLE);
  ALTER TABLE "RESERVA_DOADOR_INTERNACIONAL" MODIFY ("DOAD_ID" NOT NULL ENABLE);
  ALTER TABLE "RESERVA_DOADOR_INTERNACIONAL" MODIFY ("BUSC_ID" NOT NULL ENABLE);
  ALTER TABLE "RESERVA_DOADOR_INTERNACIONAL" MODIFY ("REDI_TX_STATUS" NOT NULL ENABLE);
  ALTER TABLE "RESERVA_DOADOR_INTERNACIONAL" ADD CONSTRAINT "REDI_PK" PRIMARY KEY ("REDI_ID");
--------------------------------------------------------
--  Constraints for Table STATUS_BUSCA
--------------------------------------------------------

  ALTER TABLE "STATUS_BUSCA" MODIFY ("STBU_ID" NOT NULL ENABLE);
  ALTER TABLE "STATUS_BUSCA" MODIFY ("STBU_TX_DESCRICAO" NOT NULL ENABLE);
  ALTER TABLE "STATUS_BUSCA" MODIFY ("STBU_IN_BUSCA_ATIVA" NOT NULL ENABLE);
  ALTER TABLE "STATUS_BUSCA" ADD CONSTRAINT "PK_STBU" PRIMARY KEY ("STBU_ID");
--------------------------------------------------------
--  Constraints for Table DIALOGO_BUSCA
--------------------------------------------------------

  ALTER TABLE "DIALOGO_BUSCA" MODIFY ("DIBU_ID" NOT NULL ENABLE);
  ALTER TABLE "DIALOGO_BUSCA" MODIFY ("BUSC_ID" NOT NULL ENABLE);
  ALTER TABLE "DIALOGO_BUSCA" MODIFY ("USUA_ID" NOT NULL ENABLE);
  ALTER TABLE "DIALOGO_BUSCA" MODIFY ("DIBU_TX_MENSAGEM" NOT NULL ENABLE);
  ALTER TABLE "DIALOGO_BUSCA" MODIFY ("DIBU_DT_MOMENTO_MENSAGEM" NOT NULL ENABLE);
  ALTER TABLE "DIALOGO_BUSCA" ADD CONSTRAINT "PK_DIBU_ID" PRIMARY KEY ("DIBU_ID");
--------------------------------------------------------
--  Constraints for Table PRE_CADASTRO_MEDICO_ENDERECO
--------------------------------------------------------

  ALTER TABLE "PRE_CADASTRO_MEDICO_ENDERECO" MODIFY ("PCME_ID" NOT NULL ENABLE);
  ALTER TABLE "PRE_CADASTRO_MEDICO_ENDERECO" MODIFY ("PCME_ID_PAIS" NOT NULL ENABLE);
  ALTER TABLE "PRE_CADASTRO_MEDICO_ENDERECO" MODIFY ("PCME_TX_CEP" NOT NULL ENABLE);
  ALTER TABLE "PRE_CADASTRO_MEDICO_ENDERECO" MODIFY ("PCME_TX_TIPO_LOGRADOURO" NOT NULL ENABLE);
  ALTER TABLE "PRE_CADASTRO_MEDICO_ENDERECO" MODIFY ("PCME_TX_NOME_LOGRADOURO" NOT NULL ENABLE);
  ALTER TABLE "PRE_CADASTRO_MEDICO_ENDERECO" MODIFY ("PCME_TX_BAIRRO" NOT NULL ENABLE);
  ALTER TABLE "PRE_CADASTRO_MEDICO_ENDERECO" MODIFY ("PCME_NR_NUMERO" NOT NULL ENABLE);
  ALTER TABLE "PRE_CADASTRO_MEDICO_ENDERECO" ADD CONSTRAINT "PK_PCME" PRIMARY KEY ("PCME_ID");
--------------------------------------------------------
--  Constraints for Table STATUS_PACIENTE
--------------------------------------------------------

  ALTER TABLE "STATUS_PACIENTE" MODIFY ("STPA_ID" NOT NULL ENABLE);
  ALTER TABLE "STATUS_PACIENTE" MODIFY ("STPA_TX_DESCRICAO" NOT NULL ENABLE);
  ALTER TABLE "STATUS_PACIENTE" ADD CONSTRAINT "PK_SPAC" PRIMARY KEY ("STPA_ID");
--------------------------------------------------------
--  Constraints for Table CATEGORIA_CHECKLIST
--------------------------------------------------------

  ALTER TABLE "CATEGORIA_CHECKLIST" MODIFY ("CACH_ID" NOT NULL ENABLE);
  ALTER TABLE "CATEGORIA_CHECKLIST" MODIFY ("CACH_TX_NM_CATEGORIA" NOT NULL ENABLE);
  ALTER TABLE "CATEGORIA_CHECKLIST" MODIFY ("TIPC_ID" NOT NULL ENABLE);
  ALTER TABLE "CATEGORIA_CHECKLIST" ADD CONSTRAINT "PK_CACH_ID" PRIMARY KEY ("CACH_ID");
--------------------------------------------------------
--  Constraints for Table PRESCRICAO
--------------------------------------------------------

  ALTER TABLE "PRESCRICAO" MODIFY ("PRES_DT_COLETA_1" NOT NULL ENABLE);
  ALTER TABLE "PRESCRICAO" MODIFY ("PRES_DT_COLETA_2" NOT NULL ENABLE);
  ALTER TABLE "PRESCRICAO" MODIFY ("FOCE_ID_OPCAO_1" NOT NULL ENABLE);
  ALTER TABLE "PRESCRICAO" MODIFY ("PRES_VL_QUANT_TOTAL_OPCAO_1" NOT NULL ENABLE);
  ALTER TABLE "PRESCRICAO" MODIFY ("PRES_ID" NOT NULL ENABLE);
  ALTER TABLE "PRESCRICAO" MODIFY ("PRES_VL_QUANT_KG_OPCAO_1" NOT NULL ENABLE);
  ALTER TABLE "PRESCRICAO" MODIFY ("EVOL_ID" NOT NULL ENABLE);
  ALTER TABLE "PRESCRICAO" ADD CONSTRAINT "PK_PRES" PRIMARY KEY ("PRES_ID");
--------------------------------------------------------
--  Constraints for Table DIAGNOSTICO
--------------------------------------------------------

  ALTER TABLE "DIAGNOSTICO" MODIFY ("PACI_NR_RMR" NOT NULL ENABLE);
  ALTER TABLE "DIAGNOSTICO" MODIFY ("DIAG_DT_DIAGNOSTICO" NOT NULL ENABLE);
  ALTER TABLE "DIAGNOSTICO" MODIFY ("CID_ID" NOT NULL ENABLE);
  ALTER TABLE "DIAGNOSTICO" ADD CONSTRAINT "PK_DIAG" PRIMARY KEY ("PACI_NR_RMR");
--------------------------------------------------------
--  Constraints for Table FORMULARIO_DOADOR
--------------------------------------------------------

  ALTER TABLE "FORMULARIO_DOADOR" MODIFY ("FODO_ID" NOT NULL ENABLE);
  ALTER TABLE "FORMULARIO_DOADOR" MODIFY ("FORM_ID" NOT NULL ENABLE);
  ALTER TABLE "FORMULARIO_DOADOR" MODIFY ("PECO_ID" NOT NULL ENABLE);
  ALTER TABLE "FORMULARIO_DOADOR" MODIFY ("DOAD_ID" NOT NULL ENABLE);
  ALTER TABLE "FORMULARIO_DOADOR" MODIFY ("FODO_DT_RESPOSTA" NOT NULL ENABLE);
  ALTER TABLE "FORMULARIO_DOADOR" MODIFY ("FODO_TX_RESPOSTAS" NOT NULL ENABLE);
  ALTER TABLE "FORMULARIO_DOADOR" ADD CONSTRAINT "PK_FODO" PRIMARY KEY ("FODO_ID");
--------------------------------------------------------
--  Constraints for Table VALOR_DNA
--------------------------------------------------------

  ALTER TABLE "VALOR_DNA" MODIFY ("LOCU_ID" NOT NULL ENABLE);
  ALTER TABLE "VALOR_DNA" MODIFY ("VADN_TX_VALOR" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table CONSTANTE_RELATORIO
--------------------------------------------------------

  ALTER TABLE "CONSTANTE_RELATORIO" MODIFY ("CORE_ID_CODIGO" NOT NULL ENABLE);
  ALTER TABLE "CONSTANTE_RELATORIO" MODIFY ("CORE_TX_VALOR" NOT NULL ENABLE);
  ALTER TABLE "CONSTANTE_RELATORIO" ADD CONSTRAINT "PK_CORE" PRIMARY KEY ("CORE_ID_CODIGO");
--------------------------------------------------------
--  Constraints for Table GENOTIPO_PACIENTE
--------------------------------------------------------

  ALTER TABLE "GENOTIPO_PACIENTE" MODIFY ("GEPA_ID" NOT NULL ENABLE);
  ALTER TABLE "GENOTIPO_PACIENTE" MODIFY ("GEPA_IN_EXCLUIDO" NOT NULL ENABLE);
  ALTER TABLE "GENOTIPO_PACIENTE" MODIFY ("PACI_NR_RMR" NOT NULL ENABLE);
  ALTER TABLE "GENOTIPO_PACIENTE" ADD CONSTRAINT "PK_GEPA" PRIMARY KEY ("GEPA_ID");
--------------------------------------------------------
--  Constraints for Table STATUS_PEDIDO_IDM
--------------------------------------------------------

  ALTER TABLE "STATUS_PEDIDO_IDM" MODIFY ("STPI_ID" NOT NULL ENABLE);
  ALTER TABLE "STATUS_PEDIDO_IDM" MODIFY ("STPI_TX_DESCRICAO" NOT NULL ENABLE);
  ALTER TABLE "STATUS_PEDIDO_IDM" ADD CONSTRAINT "PK_STPI" PRIMARY KEY ("STPI_ID");
--------------------------------------------------------
--  Constraints for Table ESTABELECIMENTO_IN_REDOME
--------------------------------------------------------

  ALTER TABLE "ESTABELECIMENTO_IN_REDOME" MODIFY ("ESIR_ID" NOT NULL ENABLE);
  ALTER TABLE "ESTABELECIMENTO_IN_REDOME" MODIFY ("ID_ESTABELECIMENTO" NOT NULL ENABLE);
  ALTER TABLE "ESTABELECIMENTO_IN_REDOME" MODIFY ("ESIR_NR_TIPO_ENTIDADE" NOT NULL ENABLE);
  ALTER TABLE "ESTABELECIMENTO_IN_REDOME" MODIFY ("ESIR_DT_INCLUSAO" NOT NULL ENABLE);
  ALTER TABLE "ESTABELECIMENTO_IN_REDOME" MODIFY ("ESIR_IN_STATUS_OPERACAO" NOT NULL ENABLE);
  ALTER TABLE "ESTABELECIMENTO_IN_REDOME" MODIFY ("ESIR_TX_REGISTRO" NOT NULL ENABLE);
  ALTER TABLE "ESTABELECIMENTO_IN_REDOME" ADD CONSTRAINT "PK_ESIR" PRIMARY KEY ("ESIR_ID");
--------------------------------------------------------
--  Constraints for Table VALOR_GENOTIPO_BUSCA_PACIENTE
--------------------------------------------------------

  ALTER TABLE "VALOR_GENOTIPO_BUSCA_PACIENTE" MODIFY ("VGBP_ID" NOT NULL ENABLE);
  ALTER TABLE "VALOR_GENOTIPO_BUSCA_PACIENTE" MODIFY ("LOCU_ID" NOT NULL ENABLE);
  ALTER TABLE "VALOR_GENOTIPO_BUSCA_PACIENTE" MODIFY ("VGBP_NR_POSICAO" NOT NULL ENABLE);
  ALTER TABLE "VALOR_GENOTIPO_BUSCA_PACIENTE" MODIFY ("VGBP_NR_TIPO" NOT NULL ENABLE);
  ALTER TABLE "VALOR_GENOTIPO_BUSCA_PACIENTE" MODIFY ("GEPA_ID" NOT NULL ENABLE);
  ALTER TABLE "VALOR_GENOTIPO_BUSCA_PACIENTE" MODIFY ("VGBP_TX_VALOR" NOT NULL ENABLE);
  ALTER TABLE "VALOR_GENOTIPO_BUSCA_PACIENTE" ADD CONSTRAINT "PK_VGBP" PRIMARY KEY ("VGBP_ID");
--------------------------------------------------------
--  Constraints for Table FUNCAO_TRANSPLANTE
--------------------------------------------------------

  ALTER TABLE "FUNCAO_TRANSPLANTE" MODIFY ("FUTR_ID" NOT NULL ENABLE);
  ALTER TABLE "FUNCAO_TRANSPLANTE" MODIFY ("FUTR_DESCRICAO" NOT NULL ENABLE);
  ALTER TABLE "FUNCAO_TRANSPLANTE" ADD CONSTRAINT "PK_FUTR" PRIMARY KEY ("FUTR_ID");
--------------------------------------------------------
--  Constraints for Table PEDIDO_COLETA
--------------------------------------------------------

  ALTER TABLE "PEDIDO_COLETA" MODIFY ("PECL_ID" NOT NULL ENABLE);
  ALTER TABLE "PEDIDO_COLETA" MODIFY ("PECL_DT_CRIACAO" NOT NULL ENABLE);
  ALTER TABLE "PEDIDO_COLETA" MODIFY ("STPC_ID" NOT NULL ENABLE);
  ALTER TABLE "PEDIDO_COLETA" MODIFY ("SOLI_ID" NOT NULL ENABLE);
  ALTER TABLE "PEDIDO_COLETA" ADD CONSTRAINT "PK_PECL" PRIMARY KEY ("PECL_ID");
--------------------------------------------------------
--  Constraints for Table PRE_CAD_MEDICO_CT_REFERENCIA
--------------------------------------------------------

  ALTER TABLE "PRE_CAD_MEDICO_CT_REFERENCIA" MODIFY ("PRCM_ID" NOT NULL ENABLE);
  ALTER TABLE "PRE_CAD_MEDICO_CT_REFERENCIA" MODIFY ("CETR_ID" NOT NULL ENABLE);
  ALTER TABLE "PRE_CAD_MEDICO_CT_REFERENCIA" ADD CONSTRAINT "PK_PCCR" PRIMARY KEY ("PRCM_ID", "CETR_ID");
--------------------------------------------------------
--  Constraints for Table RELATORIO
--------------------------------------------------------

  ALTER TABLE "RELATORIO" MODIFY ("RELA_ID" NOT NULL ENABLE);
  ALTER TABLE "RELATORIO" MODIFY ("RELA_TX_CODIGO" NOT NULL ENABLE);
  ALTER TABLE "RELATORIO" MODIFY ("RELA_IN_TIPO" NOT NULL ENABLE);
  ALTER TABLE "RELATORIO" MODIFY ("RELA_TX_HTML" NOT NULL ENABLE);
  ALTER TABLE "RELATORIO" ADD CONSTRAINT "PK_RELA" PRIMARY KEY ("RELA_ID");
  ALTER TABLE "RELATORIO" ADD CONSTRAINT "UK_RELA" UNIQUE ("RELA_TX_CODIGO");
--------------------------------------------------------
--  Constraints for Table TIPO_TRANSPLANTE
--------------------------------------------------------

  ALTER TABLE "TIPO_TRANSPLANTE" MODIFY ("TITR_ID" NOT NULL ENABLE);
  ALTER TABLE "TIPO_TRANSPLANTE" MODIFY ("TITR_TX_DESCRICAO" NOT NULL ENABLE);
  ALTER TABLE "TIPO_TRANSPLANTE" ADD CONSTRAINT "PK_TITR" PRIMARY KEY ("TITR_ID");
--------------------------------------------------------
--  Constraints for Table UF
--------------------------------------------------------

  ALTER TABLE "UF" MODIFY ("UF_SIGLA" NOT NULL ENABLE);
  ALTER TABLE "UF" ADD CONSTRAINT "PK_UF" PRIMARY KEY ("UF_SIGLA");

  --------------------------------------------------------
--  Constraints for Table CONSTANTE_RELATORIO_AUD
--------------------------------------------------------

  ALTER TABLE "CONSTANTE_RELATORIO_AUD" MODIFY ("AUDI_ID" NOT NULL ENABLE);
  ALTER TABLE "CONSTANTE_RELATORIO_AUD" MODIFY ("CORE_ID_CODIGO" NOT NULL ENABLE);
  ALTER TABLE "CONSTANTE_RELATORIO_AUD" MODIFY ("AUDI_TX_TIPO" NOT NULL ENABLE);
  ALTER TABLE "CONSTANTE_RELATORIO_AUD" MODIFY ("CORE_TX_VALOR" NOT NULL ENABLE);
  ALTER TABLE "CONSTANTE_RELATORIO_AUD" ADD CONSTRAINT "PK_CORA" PRIMARY KEY ("AUDI_ID", "CORE_ID_CODIGO");
--------------------------------------------------------
--  Constraints for Table GENOTIPO_PRELIMINAR
--------------------------------------------------------

  ALTER TABLE "GENOTIPO_PRELIMINAR" MODIFY ("GEPR_ID" NOT NULL ENABLE);
  ALTER TABLE "GENOTIPO_PRELIMINAR" MODIFY ("GEPR_TX_CODIGO_LOCUS" NOT NULL ENABLE);
  ALTER TABLE "GENOTIPO_PRELIMINAR" MODIFY ("GEPR_TX_ALELO" NOT NULL ENABLE);
  ALTER TABLE "GENOTIPO_PRELIMINAR" MODIFY ("GEPR_NR_POSICAO_ALELO" NOT NULL ENABLE);
  ALTER TABLE "GENOTIPO_PRELIMINAR" MODIFY ("BUPR_ID" NOT NULL ENABLE);
  ALTER TABLE "GENOTIPO_PRELIMINAR" MODIFY ("GEPR_IN_COMPOSICAO_VALOR" NOT NULL ENABLE);
  ALTER TABLE "GENOTIPO_PRELIMINAR" ADD CONSTRAINT "PK_GEPR" PRIMARY KEY ("GEPR_ID");
--------------------------------------------------------
--  Constraints for Table MOTIVO_DESCARTE
--------------------------------------------------------

  ALTER TABLE "MOTIVO_DESCARTE" MODIFY ("MODE_ID" NOT NULL ENABLE);
  ALTER TABLE "MOTIVO_DESCARTE" MODIFY ("MODE_TX_DESCRICAO" NOT NULL ENABLE);
  ALTER TABLE "MOTIVO_DESCARTE" ADD CONSTRAINT "PK_MODE" PRIMARY KEY ("MODE_ID");
--------------------------------------------------------
--  Constraints for Table PERFIL
--------------------------------------------------------

  ALTER TABLE "PERFIL" MODIFY ("PERF_ID" NOT NULL ENABLE);
  ALTER TABLE "PERFIL" MODIFY ("PERF_TX_DESCRICAO" NOT NULL ENABLE);
  ALTER TABLE "PERFIL" MODIFY ("SIST_ID" NOT NULL ENABLE);
  ALTER TABLE "PERFIL" ADD CONSTRAINT "PK_PERF" PRIMARY KEY ("PERF_ID");
--------------------------------------------------------
--  Constraints for Table STATUS_DOADOR
--------------------------------------------------------

  ALTER TABLE "STATUS_DOADOR" MODIFY ("STDO_ID" NOT NULL ENABLE);
  ALTER TABLE "STATUS_DOADOR" MODIFY ("STDO_TX_DESCRICAO" NOT NULL ENABLE);
  ALTER TABLE "STATUS_DOADOR" MODIFY ("STDO_IN_TEMPO_OBRIGATORIO" NOT NULL ENABLE);
  ALTER TABLE "STATUS_DOADOR" ADD CONSTRAINT "PK_STDO" PRIMARY KEY ("STDO_ID");
--------------------------------------------------------
--  Constraints for Table TIPO_SOLICITACAO
--------------------------------------------------------

  ALTER TABLE "TIPO_SOLICITACAO" MODIFY ("TISO_ID" NOT NULL ENABLE);
  ALTER TABLE "TIPO_SOLICITACAO" MODIFY ("TISO_TX_DESCRICAO" NOT NULL ENABLE);
  ALTER TABLE "TIPO_SOLICITACAO" ADD CONSTRAINT "PK_TISO" PRIMARY KEY ("TISO_ID");
--------------------------------------------------------
--  Constraints for Table LOCUS_PEDIDO_EXAME
--------------------------------------------------------

  ALTER TABLE "LOCUS_PEDIDO_EXAME" MODIFY ("LOCU_ID" NOT NULL ENABLE);
  ALTER TABLE "LOCUS_PEDIDO_EXAME" MODIFY ("PEEX_ID" NOT NULL ENABLE);
  ALTER TABLE "LOCUS_PEDIDO_EXAME" ADD CONSTRAINT "LOCUS_PEDIDO_EXAME_PK" PRIMARY KEY ("LOCU_ID", "PEEX_ID");
--------------------------------------------------------
--  Constraints for Table ARQUIVO_PEDIDO_TRANSPORTE
--------------------------------------------------------

  ALTER TABLE "ARQUIVO_PEDIDO_TRANSPORTE" MODIFY ("ARPT_ID" NOT NULL ENABLE);
  ALTER TABLE "ARQUIVO_PEDIDO_TRANSPORTE" MODIFY ("ARPT_TX_CAMINHO" NOT NULL ENABLE);
  ALTER TABLE "ARQUIVO_PEDIDO_TRANSPORTE" MODIFY ("PETR_ID" NOT NULL ENABLE);
  ALTER TABLE "ARQUIVO_PEDIDO_TRANSPORTE" ADD CONSTRAINT "PK_ARPT_ID" PRIMARY KEY ("ARPT_ID");
--------------------------------------------------------
--  Constraints for Table ITENS_CHECKLIST
--------------------------------------------------------

  ALTER TABLE "ITENS_CHECKLIST" MODIFY ("ITEC_ID" NOT NULL ENABLE);
  ALTER TABLE "ITENS_CHECKLIST" MODIFY ("ITEC_TX_NM_ITEM" NOT NULL ENABLE);
  ALTER TABLE "ITENS_CHECKLIST" MODIFY ("CACH_ID" NOT NULL ENABLE);
  ALTER TABLE "ITENS_CHECKLIST" ADD CONSTRAINT "PK_ITEC_ID" PRIMARY KEY ("ITEC_ID");
--------------------------------------------------------
--  Constraints for Table MOTIVO
--------------------------------------------------------

  ALTER TABLE "MOTIVO" MODIFY ("MOTI_ID" NOT NULL ENABLE);
  ALTER TABLE "MOTIVO" MODIFY ("MOTI_TX_DESCRICAO" NOT NULL ENABLE);
  ALTER TABLE "MOTIVO" ADD CONSTRAINT "PK_MOTI" PRIMARY KEY ("MOTI_ID");
--------------------------------------------------------
--  Constraints for Table PEDIDO_EXAME
--------------------------------------------------------

  ALTER TABLE "PEDIDO_EXAME" MODIFY ("PEEX_ID" NOT NULL ENABLE);
  ALTER TABLE "PEDIDO_EXAME" MODIFY ("PEEX_DT_CRIACAO" NOT NULL ENABLE);
  ALTER TABLE "PEDIDO_EXAME" MODIFY ("SOLI_ID" NOT NULL ENABLE);
  ALTER TABLE "PEDIDO_EXAME" MODIFY ("STPX_ID" NOT NULL ENABLE);
  ALTER TABLE "PEDIDO_EXAME" MODIFY ("PEEX_IN_OWNER" NOT NULL ENABLE);
  ALTER TABLE "PEDIDO_EXAME" ADD CONSTRAINT "PK_PEEX" PRIMARY KEY ("PEEX_ID");
--------------------------------------------------------
--  Constraints for Table FUNCAO_CENTRO_TRANSPLANTE
--------------------------------------------------------

  ALTER TABLE "FUNCAO_CENTRO_TRANSPLANTE" MODIFY ("CETR_ID" NOT NULL ENABLE);
  ALTER TABLE "FUNCAO_CENTRO_TRANSPLANTE" MODIFY ("FUTR_ID" NOT NULL ENABLE);
  ALTER TABLE "FUNCAO_CENTRO_TRANSPLANTE" ADD CONSTRAINT "FUCT_PK" PRIMARY KEY ("CETR_ID", "FUTR_ID");
--------------------------------------------------------
--  Constraints for Table PEDIDO_WORKUP
--------------------------------------------------------

  ALTER TABLE "PEDIDO_WORKUP" MODIFY ("PEWO_ID" NOT NULL ENABLE);
  ALTER TABLE "PEDIDO_WORKUP" MODIFY ("PEWO_IN_STATUS" NOT NULL ENABLE);
  ALTER TABLE "PEDIDO_WORKUP" MODIFY ("SOLI_ID" NOT NULL ENABLE);
  ALTER TABLE "PEDIDO_WORKUP" MODIFY ("PEWO_DT_CRIACAO" NOT NULL ENABLE);
  ALTER TABLE "PEDIDO_WORKUP" ADD CONSTRAINT "PK_PEWO" PRIMARY KEY ("PEWO_ID");
--------------------------------------------------------
--  Constraints for Table HISTORICO_BUSCA
--------------------------------------------------------

  ALTER TABLE "HISTORICO_BUSCA" MODIFY ("HIBU_ID" NOT NULL ENABLE);
  ALTER TABLE "HISTORICO_BUSCA" MODIFY ("HIBU_DT_ATUALIZACAO" NOT NULL ENABLE);
  ALTER TABLE "HISTORICO_BUSCA" MODIFY ("USUA_ID" NOT NULL ENABLE);
  ALTER TABLE "HISTORICO_BUSCA" MODIFY ("CETR_ID" NOT NULL ENABLE);
  ALTER TABLE "HISTORICO_BUSCA" MODIFY ("BUSC_ID" NOT NULL ENABLE);
  ALTER TABLE "HISTORICO_BUSCA" MODIFY ("HIBU_TX_JUSTIFICATIVA" NOT NULL ENABLE);
  ALTER TABLE "HISTORICO_BUSCA" ADD CONSTRAINT "HIBU_PK" PRIMARY KEY ("HIBU_ID");
--------------------------------------------------------
--  Constraints for Table LABORATORIO
--------------------------------------------------------

  ALTER TABLE "LABORATORIO" MODIFY ("LABO_ID" NOT NULL ENABLE);
  ALTER TABLE "LABORATORIO" MODIFY ("LABO_TX_NOME" NOT NULL ENABLE);
  ALTER TABLE "LABORATORIO" MODIFY ("LABO_IN_FAZ_CT" NOT NULL ENABLE);
  ALTER TABLE "LABORATORIO" MODIFY ("LABO_NR_QUANT_EXAME_CT" NOT NULL ENABLE);
  ALTER TABLE "LABORATORIO" ADD CONSTRAINT "PK_LABO" PRIMARY KEY ("LABO_ID");
--------------------------------------------------------
--  Constraints for Table PENDENCIA
--------------------------------------------------------

  ALTER TABLE "PENDENCIA" MODIFY ("PEND_ID" NOT NULL ENABLE);
  ALTER TABLE "PENDENCIA" MODIFY ("PEND_TX_DESCRICAO" NOT NULL ENABLE);
  ALTER TABLE "PENDENCIA" MODIFY ("AVAL_ID" NOT NULL ENABLE);
  ALTER TABLE "PENDENCIA" MODIFY ("STPE_ID" NOT NULL ENABLE);
  ALTER TABLE "PENDENCIA" MODIFY ("TIPE_ID" NOT NULL ENABLE);
  ALTER TABLE "PENDENCIA" MODIFY ("PEND_DT_CRIACAO" NOT NULL ENABLE);
  ALTER TABLE "PENDENCIA" ADD CONSTRAINT "PK_PEND" PRIMARY KEY ("PEND_ID");
--------------------------------------------------------
--  Constraints for Table AVALIACAO_RESULTADO_WORKUP
--------------------------------------------------------

  ALTER TABLE "AVALIACAO_RESULTADO_WORKUP" MODIFY ("AVRW_ID" NOT NULL ENABLE);
  ALTER TABLE "AVALIACAO_RESULTADO_WORKUP" MODIFY ("AVRW_DT_CRIACAO" NOT NULL ENABLE);
  ALTER TABLE "AVALIACAO_RESULTADO_WORKUP" MODIFY ("REWO_ID" NOT NULL ENABLE);
  ALTER TABLE "AVALIACAO_RESULTADO_WORKUP" ADD CONSTRAINT "PK_AVRW" PRIMARY KEY ("AVRW_ID");
--------------------------------------------------------
--  Constraints for Table ARQUIVO_EXAME
--------------------------------------------------------

  ALTER TABLE "ARQUIVO_EXAME" MODIFY ("AREX_TX_CAMINHO_ARQUIVO" NOT NULL ENABLE);
  ALTER TABLE "ARQUIVO_EXAME" MODIFY ("EXAM_ID" NOT NULL ENABLE);
  ALTER TABLE "ARQUIVO_EXAME" MODIFY ("AREX_ID" NOT NULL ENABLE);
  ALTER TABLE "ARQUIVO_EXAME" ADD CONSTRAINT "PK_AREX" PRIMARY KEY ("AREX_ID");
--------------------------------------------------------
--  Constraints for Table LOGRADOURO_CORREIO
--------------------------------------------------------

  ALTER TABLE "LOGRADOURO_CORREIO" MODIFY ("LOGC_ID" NOT NULL ENABLE);
  ALTER TABLE "LOGRADOURO_CORREIO" MODIFY ("LOGC_TX_NOME" NOT NULL ENABLE);
  ALTER TABLE "LOGRADOURO_CORREIO" MODIFY ("LOGC_TX_CEP" NOT NULL ENABLE);
  ALTER TABLE "LOGRADOURO_CORREIO" MODIFY ("BACO_ID_INICIAL" NOT NULL ENABLE);
  ALTER TABLE "LOGRADOURO_CORREIO" MODIFY ("LOGC_TX_CHAVE_DNE" NOT NULL ENABLE);
  ALTER TABLE "LOGRADOURO_CORREIO" ADD CONSTRAINT "PK_LOGC" PRIMARY KEY ("LOGC_ID");
--------------------------------------------------------
--  Constraints for Table PACIENTE
--------------------------------------------------------

  ALTER TABLE "PACIENTE" MODIFY ("PACI_NR_RMR" NOT NULL ENABLE);
  ALTER TABLE "PACIENTE" MODIFY ("PACI_TX_NOME" NOT NULL ENABLE);
  ALTER TABLE "PACIENTE" MODIFY ("PACI_IN_SEXO" NOT NULL ENABLE);
  ALTER TABLE "PACIENTE" MODIFY ("RACA_ID" NOT NULL ENABLE);
  ALTER TABLE "PACIENTE" MODIFY ("PACI_TX_ABO" NOT NULL ENABLE);
  ALTER TABLE "PACIENTE" MODIFY ("PAIS_ID" NOT NULL ENABLE);
  ALTER TABLE "PACIENTE" MODIFY ("USUA_ID" NOT NULL ENABLE);
  ALTER TABLE "PACIENTE" MODIFY ("PACI_DT_CADASTRO" NOT NULL ENABLE);
  ALTER TABLE "PACIENTE" MODIFY ("PACI_DT_NASCIMENTO" NOT NULL ENABLE);
  ALTER TABLE "PACIENTE" MODIFY ("CETR_ID_AVALIADOR" NOT NULL ENABLE);
  ALTER TABLE "PACIENTE" MODIFY ("MEDI_ID_RESPONSAVEL" NOT NULL ENABLE);
  ALTER TABLE "PACIENTE" MODIFY ("PACI_TX_NOME_FONETIZADO" NOT NULL ENABLE);
  ALTER TABLE "PACIENTE" ADD CONSTRAINT "PACIENTE_PK" PRIMARY KEY ("PACI_NR_RMR");
--------------------------------------------------------
--  Constraints for Table DISPONIBILIDADE_CENTRO_COLETA
--------------------------------------------------------

  ALTER TABLE "DISPONIBILIDADE_CENTRO_COLETA" MODIFY ("DICC_ID" NOT NULL ENABLE);
  ALTER TABLE "DISPONIBILIDADE_CENTRO_COLETA" MODIFY ("PEWO_ID" NOT NULL ENABLE);
  ALTER TABLE "DISPONIBILIDADE_CENTRO_COLETA" MODIFY ("DICC_NR_INFO_CORRENTE" NOT NULL ENABLE);
  ALTER TABLE "DISPONIBILIDADE_CENTRO_COLETA" MODIFY ("DICC_TX_DESCRICAO" NOT NULL ENABLE);
  ALTER TABLE "DISPONIBILIDADE_CENTRO_COLETA" MODIFY ("CETR_ID" NOT NULL ENABLE);
  ALTER TABLE "DISPONIBILIDADE_CENTRO_COLETA" MODIFY ("DICC_IN_EXCLUSAO" NOT NULL ENABLE);
  ALTER TABLE "DISPONIBILIDADE_CENTRO_COLETA" ADD CONSTRAINT "PK_DICC" PRIMARY KEY ("DICC_ID");
--------------------------------------------------------
--  Constraints for Table DOADOR_IN_REDOME
--------------------------------------------------------

  ALTER TABLE "DOADOR_IN_REDOME" MODIFY ("DOIR_NR_DMR" NOT NULL ENABLE);
  ALTER TABLE "DOADOR_IN_REDOME" MODIFY ("DOIR_ID" NOT NULL ENABLE);
  ALTER TABLE "DOADOR_IN_REDOME" ADD CONSTRAINT "PK_DOIR" PRIMARY KEY ("DOIR_ID");
--------------------------------------------------------
--  Constraints for Table RESPOSTA_PENDENCIA
--------------------------------------------------------

  ALTER TABLE "RESPOSTA_PENDENCIA" MODIFY ("REPE_ID" NOT NULL ENABLE);
  ALTER TABLE "RESPOSTA_PENDENCIA" MODIFY ("USUA_ID" NOT NULL ENABLE);
  ALTER TABLE "RESPOSTA_PENDENCIA" MODIFY ("REPE_DT_DATA" NOT NULL ENABLE);
  ALTER TABLE "RESPOSTA_PENDENCIA" ADD CONSTRAINT "PK_REPE" PRIMARY KEY ("REPE_ID");
--------------------------------------------------------
--  Constraints for Table TRANSPORTE_TERRESTRE
--------------------------------------------------------

  ALTER TABLE "TRANSPORTE_TERRESTRE" MODIFY ("TRTE_ID" NOT NULL ENABLE);
  ALTER TABLE "TRANSPORTE_TERRESTRE" MODIFY ("TRTE_DT_DATA" NOT NULL ENABLE);
  ALTER TABLE "TRANSPORTE_TERRESTRE" MODIFY ("TRTE_TX_ORIGEM" NOT NULL ENABLE);
  ALTER TABLE "TRANSPORTE_TERRESTRE" MODIFY ("TRTE_TX_DESTINO" NOT NULL ENABLE);
  ALTER TABLE "TRANSPORTE_TERRESTRE" MODIFY ("TRTE_TX_OBJETO_TRANSPORTE" NOT NULL ENABLE);
  ALTER TABLE "TRANSPORTE_TERRESTRE" MODIFY ("TRTE_IN_EXCLUIDO" NOT NULL ENABLE);
  ALTER TABLE "TRANSPORTE_TERRESTRE" MODIFY ("PELO_ID" NOT NULL ENABLE);
  ALTER TABLE "TRANSPORTE_TERRESTRE" ADD CONSTRAINT "PK_TRTE" PRIMARY KEY ("TRTE_ID");
--------------------------------------------------------
--  Constraints for Table ARQUIVO_PRESCRICAO
--------------------------------------------------------

  ALTER TABLE "ARQUIVO_PRESCRICAO" MODIFY ("ARPR_ID" NOT NULL ENABLE);
  ALTER TABLE "ARQUIVO_PRESCRICAO" MODIFY ("ARPR_TX_CAMINHO" NOT NULL ENABLE);
  ALTER TABLE "ARQUIVO_PRESCRICAO" MODIFY ("PRES_ID" NOT NULL ENABLE);
  ALTER TABLE "ARQUIVO_PRESCRICAO" MODIFY ("ARPR_IN_JUSTIFICATIVA" NOT NULL ENABLE);
  ALTER TABLE "ARQUIVO_PRESCRICAO" MODIFY ("ARPR_IN_AUTORIZACAO_PACIENTE" NOT NULL ENABLE);
  ALTER TABLE "ARQUIVO_PRESCRICAO" ADD CONSTRAINT "PK_ARPR" PRIMARY KEY ("ARPR_ID");
--------------------------------------------------------
--  Constraints for Table BUSCA_AUD
--------------------------------------------------------

  ALTER TABLE "BUSCA_AUD" MODIFY ("BUSC_ID" NOT NULL ENABLE);
  ALTER TABLE "BUSCA_AUD" MODIFY ("AUDI_ID" NOT NULL ENABLE);
  ALTER TABLE "BUSCA_AUD" ADD CONSTRAINT "PK_BUAU" PRIMARY KEY ("BUSC_ID", "AUDI_ID");
--------------------------------------------------------
--  Constraints for Table PAIS
--------------------------------------------------------

  ALTER TABLE "PAIS" MODIFY ("PAIS_ID" NOT NULL ENABLE);
  ALTER TABLE "PAIS" MODIFY ("PAIS_TX_NOME" NOT NULL ENABLE);
  ALTER TABLE "PAIS" ADD CONSTRAINT "PK_PAIS" PRIMARY KEY ("PAIS_ID");
--------------------------------------------------------
--  Constraints for Table RECEBIMENTO_COLETA
--------------------------------------------------------

  ALTER TABLE "RECEBIMENTO_COLETA" MODIFY ("RECE_ID" NOT NULL ENABLE);
  ALTER TABLE "RECEBIMENTO_COLETA" MODIFY ("PECL_ID" NOT NULL ENABLE);
  ALTER TABLE "RECEBIMENTO_COLETA" MODIFY ("FOCE_ID" NOT NULL ENABLE);
  ALTER TABLE "RECEBIMENTO_COLETA" MODIFY ("RECI_DT_RECEBIMENTO" NOT NULL ENABLE);
  ALTER TABLE "RECEBIMENTO_COLETA" ADD CONSTRAINT "PK_RECI_ID" PRIMARY KEY ("RECE_ID");
--------------------------------------------------------
--  Constraints for Table ASSOCIA_RESPOSTA_PENDENCIA
--------------------------------------------------------

  ALTER TABLE "ASSOCIA_RESPOSTA_PENDENCIA" MODIFY ("PEND_ID" NOT NULL ENABLE);
  ALTER TABLE "ASSOCIA_RESPOSTA_PENDENCIA" MODIFY ("REPE_ID" NOT NULL ENABLE);
  ALTER TABLE "ASSOCIA_RESPOSTA_PENDENCIA" ADD CONSTRAINT "PK_ASRP" PRIMARY KEY ("PEND_ID", "REPE_ID");
--------------------------------------------------------
--  Constraints for Table VERSAO_ARQUIVO_BAIXADO
--------------------------------------------------------

  ALTER TABLE "VERSAO_ARQUIVO_BAIXADO" MODIFY ("VEAB_ID" NOT NULL ENABLE);
  ALTER TABLE "VERSAO_ARQUIVO_BAIXADO" MODIFY ("VEAB_TX_NOME_ARQUIVO" NOT NULL ENABLE);
  ALTER TABLE "VERSAO_ARQUIVO_BAIXADO" MODIFY ("VEAB_TX_VERSAO" NOT NULL ENABLE);
  ALTER TABLE "VERSAO_ARQUIVO_BAIXADO" ADD CONSTRAINT "PK_VERSAO_ARQUIVO_BAIXADO" PRIMARY KEY ("VEAB_ID");
--------------------------------------------------------
--  Constraints for Table TEMP_VALOR_NMDP
--------------------------------------------------------

  ALTER TABLE "TEMP_VALOR_NMDP" MODIFY ("TEVN_ID_CODIGO" NOT NULL ENABLE);
  ALTER TABLE "TEMP_VALOR_NMDP" MODIFY ("TEVN_TX_SUBTIPO" NOT NULL ENABLE);
  ALTER TABLE "TEMP_VALOR_NMDP" MODIFY ("TEVN_IN_AGRUPADO" NOT NULL ENABLE);
  ALTER TABLE "TEMP_VALOR_NMDP" ADD CONSTRAINT "PK_TEVN" PRIMARY KEY ("TEVN_ID_CODIGO");
--------------------------------------------------------
--  Constraints for Table GENOTIPO_DOADOR
--------------------------------------------------------

  ALTER TABLE "GENOTIPO_DOADOR" MODIFY ("GEDO_ID" NOT NULL ENABLE);
  ALTER TABLE "GENOTIPO_DOADOR" MODIFY ("GEDO_IN_EXCLUIDO" NOT NULL ENABLE);
  ALTER TABLE "GENOTIPO_DOADOR" MODIFY ("GEDO_IN_TIPO_DOADOR" NOT NULL ENABLE);
  ALTER TABLE "GENOTIPO_DOADOR" ADD CONSTRAINT "PK_GEDO" PRIMARY KEY ("GEDO_ID");
  ALTER TABLE "GENOTIPO_DOADOR" ADD CONSTRAINT "UK_GEDO_DOAD" UNIQUE ("DOAD_ID");
--------------------------------------------------------
--  Constraints for Table CLASSIFICACAO_ABO
--------------------------------------------------------

  ALTER TABLE "CLASSIFICACAO_ABO" MODIFY ("CLAB_TX_ABO" NOT NULL ENABLE);
  ALTER TABLE "CLASSIFICACAO_ABO" MODIFY ("CLAB_VL_PRIORIDADE" NOT NULL ENABLE);
  ALTER TABLE "CLASSIFICACAO_ABO" MODIFY ("CLAB_TX_ABO_RELACAO" NOT NULL ENABLE);
  ALTER TABLE "CLASSIFICACAO_ABO" ADD CONSTRAINT "PK_CLAB" PRIMARY KEY ("CLAB_TX_ABO", "CLAB_TX_ABO_RELACAO");
--------------------------------------------------------
--  Constraints for Table LOCUS
--------------------------------------------------------

  ALTER TABLE "LOCUS" MODIFY ("LOCU_ID" NOT NULL ENABLE);
  ALTER TABLE "LOCUS" MODIFY ("LOCU_NR_ORDEM" NOT NULL ENABLE);
  ALTER TABLE "LOCUS" ADD CONSTRAINT "PK_LOCU" PRIMARY KEY ("LOCU_ID");
--------------------------------------------------------
--  Constraints for Table EMAIL_CONTATO
--------------------------------------------------------

  ALTER TABLE "EMAIL_CONTATO" MODIFY ("EMCO_ID" NOT NULL ENABLE);
  ALTER TABLE "EMAIL_CONTATO" MODIFY ("EMCO_TX_EMAIL" NOT NULL ENABLE);
  ALTER TABLE "EMAIL_CONTATO" MODIFY ("EMCO_IN_EXCLUIDO" NOT NULL ENABLE);
  ALTER TABLE "EMAIL_CONTATO" MODIFY ("EMCO_IN_PRINCIPAL" NOT NULL ENABLE);
  ALTER TABLE "EMAIL_CONTATO" ADD CONSTRAINT "PK_EMCO" PRIMARY KEY ("EMCO_ID");
--------------------------------------------------------
--  Constraints for Table CATEGORIA_NOTIFICACAO
--------------------------------------------------------

  ALTER TABLE "CATEGORIA_NOTIFICACAO" MODIFY ("CANO_ID" NOT NULL ENABLE);
  ALTER TABLE "CATEGORIA_NOTIFICACAO" MODIFY ("CANO_TX_DESCRICAO" NOT NULL ENABLE);
  ALTER TABLE "CATEGORIA_NOTIFICACAO" ADD CONSTRAINT "PK_CANO" PRIMARY KEY ("CANO_ID");
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
--  Constraints for Table RESULTADO_WORKUP
--------------------------------------------------------

  ALTER TABLE "RESULTADO_WORKUP" MODIFY ("REWO_ID" NOT NULL ENABLE);
  ALTER TABLE "RESULTADO_WORKUP" MODIFY ("REWO_DT_CRIACAO" NOT NULL ENABLE);
  ALTER TABLE "RESULTADO_WORKUP" MODIFY ("PEWO_ID" NOT NULL ENABLE);
  ALTER TABLE "RESULTADO_WORKUP" ADD CONSTRAINT "PK_REWO" PRIMARY KEY ("REWO_ID");
--------------------------------------------------------
--  Constraints for Table MEDICO_CT_REFERENCIA
--------------------------------------------------------

  ALTER TABLE "MEDICO_CT_REFERENCIA" MODIFY ("MEDI_ID" NOT NULL ENABLE);
  ALTER TABLE "MEDICO_CT_REFERENCIA" MODIFY ("CETR_ID" NOT NULL ENABLE);
  ALTER TABLE "MEDICO_CT_REFERENCIA" ADD CONSTRAINT "PK_MECR" PRIMARY KEY ("MEDI_ID", "CETR_ID");
--------------------------------------------------------
--  Constraints for Table PEDIDO_WORKUP_AUD
--------------------------------------------------------

  ALTER TABLE "PEDIDO_WORKUP_AUD" MODIFY ("PEWO_ID" NOT NULL ENABLE);
  ALTER TABLE "PEDIDO_WORKUP_AUD" MODIFY ("AUDI_ID" NOT NULL ENABLE);
  ALTER TABLE "PEDIDO_WORKUP_AUD" ADD CONSTRAINT "PK_PEWA" PRIMARY KEY ("PEWO_ID", "AUDI_ID");
--------------------------------------------------------
--  Constraints for Table TIPO_SERVICO
--------------------------------------------------------

  ALTER TABLE "TIPO_SERVICO" MODIFY ("TISE_ID" NOT NULL ENABLE);
  ALTER TABLE "TIPO_SERVICO" MODIFY ("TISE_TX_DESCRICAO" NOT NULL ENABLE);
  ALTER TABLE "TIPO_SERVICO" ADD CONSTRAINT "PK_TISE" PRIMARY KEY ("TISE_ID");
--------------------------------------------------------
--  Constraints for Table CONDICAO_PACIENTE
--------------------------------------------------------

  ALTER TABLE "CONDICAO_PACIENTE" MODIFY ("COPA_ID" NOT NULL ENABLE);
  ALTER TABLE "CONDICAO_PACIENTE" MODIFY ("COPA_TX_DESCRICAO" NOT NULL ENABLE);
  ALTER TABLE "CONDICAO_PACIENTE" ADD CONSTRAINT "PK_COPA" PRIMARY KEY ("COPA_ID");

--------------------------------------------------------
--  Constraints for Table ARQUIVO_RELAT_INTERNACIONAL
--------------------------------------------------------

  ALTER TABLE "ARQUIVO_RELAT_INTERNACIONAL" MODIFY ("ARRI_ID" NOT NULL ENABLE);
  ALTER TABLE "ARQUIVO_RELAT_INTERNACIONAL" MODIFY ("BUSC_ID" NOT NULL ENABLE);
  ALTER TABLE "ARQUIVO_RELAT_INTERNACIONAL" ADD CONSTRAINT "PK_ARRI" PRIMARY KEY ("ARRI_ID");
--------------------------------------------------------
--  Constraints for Table EXAME_AUD
--------------------------------------------------------

  ALTER TABLE "EXAME_AUD" MODIFY ("EXAM_ID" NOT NULL ENABLE);
  ALTER TABLE "EXAME_AUD" MODIFY ("AUDI_ID" NOT NULL ENABLE);
  ALTER TABLE "EXAME_AUD" ADD CONSTRAINT "PK_EXAA" PRIMARY KEY ("EXAM_ID", "AUDI_ID");
--------------------------------------------------------
--  Constraints for Table PEDIDO_LOGISTICA_AUD
--------------------------------------------------------

  ALTER TABLE "PEDIDO_LOGISTICA_AUD" MODIFY ("PELO_ID" NOT NULL ENABLE);
  ALTER TABLE "PEDIDO_LOGISTICA_AUD" MODIFY ("AUDI_ID" NOT NULL ENABLE);
  ALTER TABLE "PEDIDO_LOGISTICA_AUD" ADD CONSTRAINT "PK_PELA" PRIMARY KEY ("PELO_ID", "AUDI_ID");
--------------------------------------------------------
--  Constraints for Table DIAGNOSTICO_AUD
--------------------------------------------------------

  ALTER TABLE "DIAGNOSTICO_AUD" MODIFY ("PACI_NR_RMR" NOT NULL ENABLE);
  ALTER TABLE "DIAGNOSTICO_AUD" MODIFY ("AUDI_ID" NOT NULL ENABLE);
  ALTER TABLE "DIAGNOSTICO_AUD" ADD CONSTRAINT "PK_DIAU" PRIMARY KEY ("PACI_NR_RMR", "AUDI_ID");
--------------------------------------------------------
--  Constraints for Table FORMULARIO
--------------------------------------------------------

  ALTER TABLE "FORMULARIO" MODIFY ("FORM_ID" NOT NULL ENABLE);
  ALTER TABLE "FORMULARIO" MODIFY ("TIFO_ID" NOT NULL ENABLE);
  ALTER TABLE "FORMULARIO" MODIFY ("FORM_IN_ATIVO" NOT NULL ENABLE);
  ALTER TABLE "FORMULARIO" MODIFY ("FORM_TX_FORMATO" NOT NULL ENABLE);
  ALTER TABLE "FORMULARIO" ADD CONSTRAINT "PK_FORM" PRIMARY KEY ("FORM_ID");
--------------------------------------------------------
--  Constraints for Table MOTIVO_STATUS_DOADOR
--------------------------------------------------------

  ALTER TABLE "MOTIVO_STATUS_DOADOR" MODIFY ("MOSD_ID" NOT NULL ENABLE);
  ALTER TABLE "MOTIVO_STATUS_DOADOR" MODIFY ("MOSD_TX_DESCRICAO" NOT NULL ENABLE);
  ALTER TABLE "MOTIVO_STATUS_DOADOR" MODIFY ("STDO_ID" NOT NULL ENABLE);
  ALTER TABLE "MOTIVO_STATUS_DOADOR" ADD CONSTRAINT "PK_MOSD" PRIMARY KEY ("MOSD_ID");
--------------------------------------------------------
--  Constraints for Table UNIDADE_FEDERATIVA_CORREIO
--------------------------------------------------------

  ALTER TABLE "UNIDADE_FEDERATIVA_CORREIO" MODIFY ("UNFC_ID" NOT NULL ENABLE);
  ALTER TABLE "UNIDADE_FEDERATIVA_CORREIO" MODIFY ("UNFC_TX_SIGLA" NOT NULL ENABLE);
  ALTER TABLE "UNIDADE_FEDERATIVA_CORREIO" MODIFY ("UNFC_TX_NOME" NOT NULL ENABLE);
  ALTER TABLE "UNIDADE_FEDERATIVA_CORREIO" MODIFY ("PACO_ID" NOT NULL ENABLE);
  ALTER TABLE "UNIDADE_FEDERATIVA_CORREIO" MODIFY ("UNFC_TX_DNE" NOT NULL ENABLE);
  ALTER TABLE "UNIDADE_FEDERATIVA_CORREIO" ADD CONSTRAINT "PK_UNFC" PRIMARY KEY ("UNFC_ID");
--------------------------------------------------------
--  Constraints for Table MOTIVO_CANCELAMENTO_COLETA
--------------------------------------------------------

  ALTER TABLE "MOTIVO_CANCELAMENTO_COLETA" MODIFY ("MOCC_TX_CODIGO" NOT NULL ENABLE);
  ALTER TABLE "MOTIVO_CANCELAMENTO_COLETA" MODIFY ("MOCC_DESCRICAO" NOT NULL ENABLE);
  ALTER TABLE "MOTIVO_CANCELAMENTO_COLETA" ADD CONSTRAINT "PK_MOCC" PRIMARY KEY ("MOCC_TX_CODIGO");
--------------------------------------------------------
--  Constraints for Table AVALIACAO
--------------------------------------------------------

  ALTER TABLE "AVALIACAO" MODIFY ("AVAL_ID" NOT NULL ENABLE);
  ALTER TABLE "AVALIACAO" MODIFY ("PACI_NR_RMR" NOT NULL ENABLE);
  ALTER TABLE "AVALIACAO" MODIFY ("AVAL_DT_CRIACAO" NOT NULL ENABLE);
  ALTER TABLE "AVALIACAO" MODIFY ("CETR_ID" NOT NULL ENABLE);
  ALTER TABLE "AVALIACAO" MODIFY ("AVAL_IN_STATUS" NOT NULL ENABLE);
  ALTER TABLE "AVALIACAO" ADD CONSTRAINT "PK_AVAL" PRIMARY KEY ("AVAL_ID");
--------------------------------------------------------
--  Constraints for Table CONTATO_TELEFONICO_AUD
--------------------------------------------------------

  ALTER TABLE "CONTATO_TELEFONICO_AUD" MODIFY ("COTE_ID" NOT NULL ENABLE);
  ALTER TABLE "CONTATO_TELEFONICO_AUD" MODIFY ("AUDI_ID" NOT NULL ENABLE);
  ALTER TABLE "CONTATO_TELEFONICO_AUD" ADD CONSTRAINT "PK_COTA" PRIMARY KEY ("COTE_ID", "AUDI_ID");
--------------------------------------------------------
--  Constraints for Table METODOLOGIA_EXAME
--------------------------------------------------------

  ALTER TABLE "METODOLOGIA_EXAME" MODIFY ("EXAM_ID" NOT NULL ENABLE);
  ALTER TABLE "METODOLOGIA_EXAME" MODIFY ("METO_ID" NOT NULL ENABLE);
  ALTER TABLE "METODOLOGIA_EXAME" ADD CONSTRAINT "PK_MEEX" PRIMARY KEY ("EXAM_ID", "METO_ID");
--------------------------------------------------------
--  Constraints for Table MOTIVO_CANCELAMENTO_BUSCA
--------------------------------------------------------

  ALTER TABLE "MOTIVO_CANCELAMENTO_BUSCA" MODIFY ("MOCB_ID" NOT NULL ENABLE);
  ALTER TABLE "MOTIVO_CANCELAMENTO_BUSCA" MODIFY ("MOCB_TX_DESCRICAO" NOT NULL ENABLE);
  ALTER TABLE "MOTIVO_CANCELAMENTO_BUSCA" MODIFY ("MOCB_IN_DESC_OBRIGATORIO" NOT NULL ENABLE);
  ALTER TABLE "MOTIVO_CANCELAMENTO_BUSCA" ADD CONSTRAINT "PK_MOCB" PRIMARY KEY ("MOCB_ID");
--------------------------------------------------------
--  Constraints for Table TIPO_CHECKLIST
--------------------------------------------------------

  ALTER TABLE "TIPO_CHECKLIST" MODIFY ("TIPC_ID" NOT NULL ENABLE);
  ALTER TABLE "TIPO_CHECKLIST" ADD CONSTRAINT "PK_TIPC_ID" PRIMARY KEY ("TIPC_ID");
--------------------------------------------------------
--  Constraints for Table HEMO_ENTIDADE
--------------------------------------------------------

  ALTER TABLE "HEMO_ENTIDADE" MODIFY ("HEEN_ID" NOT NULL ENABLE);
  ALTER TABLE "HEMO_ENTIDADE" MODIFY ("HEEN_TX_NOME" NOT NULL ENABLE);
  ALTER TABLE "HEMO_ENTIDADE" MODIFY ("HEEN_IN_SELECIONAVEL" NOT NULL ENABLE);
  ALTER TABLE "HEMO_ENTIDADE" MODIFY ("HEEN_TX_CLASSIFICACAO" NOT NULL ENABLE);
  ALTER TABLE "HEMO_ENTIDADE" ADD CONSTRAINT "PK_HEEN" PRIMARY KEY ("HEEN_ID");
--------------------------------------------------------
--  Constraints for Table STATUS_PEDIDO_LOGISTICA
--------------------------------------------------------

  ALTER TABLE "STATUS_PEDIDO_LOGISTICA" MODIFY ("STPL_ID" NOT NULL ENABLE);
  ALTER TABLE "STATUS_PEDIDO_LOGISTICA" MODIFY ("STPL_TX_DESCRICAO" NOT NULL ENABLE);
  ALTER TABLE "STATUS_PEDIDO_LOGISTICA" ADD CONSTRAINT "PK_STPL" PRIMARY KEY ("STPL_ID");
--------------------------------------------------------
--  Constraints for Table USUARIO_CENTRO_TRANSPLANTE
--------------------------------------------------------

  ALTER TABLE "USUARIO_CENTRO_TRANSPLANTE" MODIFY ("USUA_ID" NOT NULL ENABLE);
  ALTER TABLE "USUARIO_CENTRO_TRANSPLANTE" MODIFY ("CETR_ID" NOT NULL ENABLE);
  ALTER TABLE "USUARIO_CENTRO_TRANSPLANTE" ADD CONSTRAINT "PK_USCT" PRIMARY KEY ("USUA_ID", "CETR_ID");
--------------------------------------------------------
--  Constraints for Table ARQUIVO_VOUCHER_LOGISTICA_AUD
--------------------------------------------------------

  ALTER TABLE "ARQUIVO_VOUCHER_LOGISTICA_AUD" MODIFY ("ARVL_ID" NOT NULL ENABLE);
  ALTER TABLE "ARQUIVO_VOUCHER_LOGISTICA_AUD" MODIFY ("AUDI_ID" NOT NULL ENABLE);
  ALTER TABLE "ARQUIVO_VOUCHER_LOGISTICA_AUD" ADD CONSTRAINT "PK_AVLA" PRIMARY KEY ("ARVL_ID", "AUDI_ID");
--------------------------------------------------------
--  Constraints for Table TIPO_FORMULARIO
--------------------------------------------------------

  ALTER TABLE "TIPO_FORMULARIO" MODIFY ("TIFO_ID" NOT NULL ENABLE);
  ALTER TABLE "TIPO_FORMULARIO" MODIFY ("TIFO_IN_NACIONAL" NOT NULL ENABLE);
  ALTER TABLE "TIPO_FORMULARIO" MODIFY ("TIFO_IN_INTERNACIONAL" NOT NULL ENABLE);
  ALTER TABLE "TIPO_FORMULARIO" ADD CONSTRAINT "PK_TIFO" PRIMARY KEY ("TIFO_ID");
--------------------------------------------------------
--  Constraints for Table VALOR_DNA_NMDP_VALIDO
--------------------------------------------------------

  ALTER TABLE "VALOR_DNA_NMDP_VALIDO" MODIFY ("LOCU_ID" NOT NULL ENABLE);
  ALTER TABLE "VALOR_DNA_NMDP_VALIDO" MODIFY ("DNNM_TX_VALOR" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table GENOTIPO_EXPANDIDO_PRELIMINAR
--------------------------------------------------------

  ALTER TABLE "GENOTIPO_EXPANDIDO_PRELIMINAR" MODIFY ("GEEP_ID" NOT NULL ENABLE);
  ALTER TABLE "GENOTIPO_EXPANDIDO_PRELIMINAR" MODIFY ("GEEP_TX_CODIGO_LOCUS" NOT NULL ENABLE);
  ALTER TABLE "GENOTIPO_EXPANDIDO_PRELIMINAR" MODIFY ("GEEP_NR_ALELO" NOT NULL ENABLE);
  ALTER TABLE "GENOTIPO_EXPANDIDO_PRELIMINAR" MODIFY ("GEEP_NR_POSICAO_ALELO" NOT NULL ENABLE);
  ALTER TABLE "GENOTIPO_EXPANDIDO_PRELIMINAR" MODIFY ("BUPR_ID" NOT NULL ENABLE);
  ALTER TABLE "GENOTIPO_EXPANDIDO_PRELIMINAR" ADD CONSTRAINT "PK_GEEP" PRIMARY KEY ("GEEP_ID");
--------------------------------------------------------
--  Constraints for Table STATUS_PEDIDO_TRANSPORTE
--------------------------------------------------------

  ALTER TABLE "STATUS_PEDIDO_TRANSPORTE" MODIFY ("STPT_ID" NOT NULL ENABLE);
  ALTER TABLE "STATUS_PEDIDO_TRANSPORTE" ADD CONSTRAINT "PK_STPT" PRIMARY KEY ("STPT_ID");
--------------------------------------------------------
--  Constraints for Table MEDICO
--------------------------------------------------------

  ALTER TABLE "MEDICO" MODIFY ("MEDI_ID" NOT NULL ENABLE);
  ALTER TABLE "MEDICO" MODIFY ("MEDI_TX_CRM" NOT NULL ENABLE);
  ALTER TABLE "MEDICO" MODIFY ("MEDI_TX_NOME" NOT NULL ENABLE);
  ALTER TABLE "MEDICO" MODIFY ("MEDI_TX_ARQUIVO_CRM" NOT NULL ENABLE);
  ALTER TABLE "MEDICO" ADD CONSTRAINT "PK_MEDI" PRIMARY KEY ("MEDI_ID");
--------------------------------------------------------
--  Constraints for Table VALOR_NMDP
--------------------------------------------------------

  ALTER TABLE "VALOR_NMDP" MODIFY ("VANM_ID_CODIGO" NOT NULL ENABLE);
  ALTER TABLE "VALOR_NMDP" MODIFY ("VANM_TX_SUBTIPO" NOT NULL ENABLE);
  ALTER TABLE "VALOR_NMDP" MODIFY ("VANM_IN_AGRUPADO" NOT NULL ENABLE);
  ALTER TABLE "VALOR_NMDP" ADD CONSTRAINT "PK_VANM" PRIMARY KEY ("VANM_ID_CODIGO");
--------------------------------------------------------
--  Constraints for Table IDIOMA
--------------------------------------------------------

  ALTER TABLE "IDIOMA" MODIFY ("IDIO_ID" NOT NULL ENABLE);
  ALTER TABLE "IDIOMA" MODIFY ("IDIO_TX_SIGLA" NOT NULL ENABLE);
  ALTER TABLE "IDIOMA" ADD CONSTRAINT "PK_IDIO" PRIMARY KEY ("IDIO_ID");
--------------------------------------------------------
--  Constraints for Table MOTIVO_CANCELAMENTO_WORKUP
--------------------------------------------------------

  ALTER TABLE "MOTIVO_CANCELAMENTO_WORKUP" MODIFY ("MOCW_ID" NOT NULL ENABLE);
  ALTER TABLE "MOTIVO_CANCELAMENTO_WORKUP" MODIFY ("MOCW_DESCRICAO" NOT NULL ENABLE);
  ALTER TABLE "MOTIVO_CANCELAMENTO_WORKUP" MODIFY ("MOCW_IN_SELECIONAVEL" NOT NULL ENABLE);
  ALTER TABLE "MOTIVO_CANCELAMENTO_WORKUP" ADD CONSTRAINT "PK_MOCW" PRIMARY KEY ("MOCW_ID");
--------------------------------------------------------
--  Constraints for Table PEDIDO_CONTATO
--------------------------------------------------------

  ALTER TABLE "PEDIDO_CONTATO" MODIFY ("PECO_ID" NOT NULL ENABLE);
  ALTER TABLE "PEDIDO_CONTATO" MODIFY ("PECO_DT_CRIACAO" NOT NULL ENABLE);
  ALTER TABLE "PEDIDO_CONTATO" MODIFY ("PECO_IN_ABERTO" NOT NULL ENABLE);
  ALTER TABLE "PEDIDO_CONTATO" MODIFY ("SOLI_ID" NOT NULL ENABLE);
  ALTER TABLE "PEDIDO_CONTATO" ADD CONSTRAINT "PK_PECO" PRIMARY KEY ("PECO_ID");
--------------------------------------------------------
--  Constraints for Table VALOR_SOROLOGICO
--------------------------------------------------------

  ALTER TABLE "VALOR_SOROLOGICO" MODIFY ("LOCU_ID" NOT NULL ENABLE);
  ALTER TABLE "VALOR_SOROLOGICO" MODIFY ("VASO_TX_ANTIGENO" NOT NULL ENABLE);
  ALTER TABLE "VALOR_SOROLOGICO" MODIFY ("VASO_TX_VALORES" NOT NULL ENABLE);
  ALTER TABLE "VALOR_SOROLOGICO" ADD CONSTRAINT "PK_VASO" PRIMARY KEY ("LOCU_ID", "VASO_TX_ANTIGENO");
--------------------------------------------------------
--  Constraints for Table EMAIL_CONTATO_AUD
--------------------------------------------------------

  ALTER TABLE "EMAIL_CONTATO_AUD" MODIFY ("EMCO_ID" NOT NULL ENABLE);
  ALTER TABLE "EMAIL_CONTATO_AUD" MODIFY ("AUDI_ID" NOT NULL ENABLE);
  ALTER TABLE "EMAIL_CONTATO_AUD" MODIFY ("EMCO_TX_EMAIL" NOT NULL ENABLE);
  ALTER TABLE "EMAIL_CONTATO_AUD" ADD CONSTRAINT "PK_EMCA" PRIMARY KEY ("EMCO_ID", "AUDI_ID");
--------------------------------------------------------
--  Constraints for Table INFO_PREVIA
--------------------------------------------------------

  ALTER TABLE "INFO_PREVIA" MODIFY ("INPR_ID" NOT NULL ENABLE);
  ALTER TABLE "INFO_PREVIA" MODIFY ("PEWO_ID" NOT NULL ENABLE);
  ALTER TABLE "INFO_PREVIA" MODIFY ("INPR_NR_INFO_CORRENTE" NOT NULL ENABLE);
  ALTER TABLE "INFO_PREVIA" MODIFY ("INPR_TX_DESCRICAO" NOT NULL ENABLE);
  ALTER TABLE "INFO_PREVIA" ADD CONSTRAINT "PK_INPR" PRIMARY KEY ("INPR_ID");
--------------------------------------------------------
--  Constraints for Table LOCUS_PEDIDO_EXAME_AUD
--------------------------------------------------------

  ALTER TABLE "LOCUS_PEDIDO_EXAME_AUD" MODIFY ("AUDI_ID" NOT NULL ENABLE);
  ALTER TABLE "LOCUS_PEDIDO_EXAME_AUD" MODIFY ("PEEX_ID" NOT NULL ENABLE);
  ALTER TABLE "LOCUS_PEDIDO_EXAME_AUD" MODIFY ("LOCU_ID" NOT NULL ENABLE);
  ALTER TABLE "LOCUS_PEDIDO_EXAME_AUD" ADD CONSTRAINT "PK_LPEA" PRIMARY KEY ("AUDI_ID", "PEEX_ID", "LOCU_ID");
--------------------------------------------------------
--  Constraints for Table TIPO_EXAME
--------------------------------------------------------

  ALTER TABLE "TIPO_EXAME" MODIFY ("TIEX_ID" NOT NULL ENABLE);
  ALTER TABLE "TIPO_EXAME" ADD CONSTRAINT "PK_TIEX" PRIMARY KEY ("TIEX_ID");
--------------------------------------------------------
--  Constraints for Table PACIENTE_MISMATCH
--------------------------------------------------------

  ALTER TABLE "PACIENTE_MISMATCH" MODIFY ("PACI_NR_RMR" NOT NULL ENABLE);
  ALTER TABLE "PACIENTE_MISMATCH" MODIFY ("LOCU_ID" NOT NULL ENABLE);
  ALTER TABLE "PACIENTE_MISMATCH" ADD CONSTRAINT "PK_PAMI" PRIMARY KEY ("PACI_NR_RMR", "LOCU_ID");
--------------------------------------------------------
--  Constraints for Table AVALIACAO_CAMARA_TECNICA
--------------------------------------------------------

  ALTER TABLE "AVALIACAO_CAMARA_TECNICA" MODIFY ("AVCT_ID" NOT NULL ENABLE);
  ALTER TABLE "AVALIACAO_CAMARA_TECNICA" MODIFY ("PACI_NR_RMR" NOT NULL ENABLE);
  ALTER TABLE "AVALIACAO_CAMARA_TECNICA" MODIFY ("STCT_ID" NOT NULL ENABLE);
  ALTER TABLE "AVALIACAO_CAMARA_TECNICA" ADD CONSTRAINT "PK_AVCT" PRIMARY KEY ("AVCT_ID");
--------------------------------------------------------
--  Constraints for Table CANCELAMENTO_BUSCA
--------------------------------------------------------

  ALTER TABLE "CANCELAMENTO_BUSCA" MODIFY ("CABU_ID" NOT NULL ENABLE);
  ALTER TABLE "CANCELAMENTO_BUSCA" MODIFY ("BUSC_ID" NOT NULL ENABLE);
  ALTER TABLE "CANCELAMENTO_BUSCA" MODIFY ("MOCB_ID" NOT NULL ENABLE);
  ALTER TABLE "CANCELAMENTO_BUSCA" MODIFY ("USUA_ID" NOT NULL ENABLE);
  ALTER TABLE "CANCELAMENTO_BUSCA" MODIFY ("CABU_DT_CRIACAO" NOT NULL ENABLE);
  ALTER TABLE "CANCELAMENTO_BUSCA" ADD CONSTRAINT "PK_CABU" PRIMARY KEY ("CABU_ID");
--------------------------------------------------------
--  Constraints for Table CID_IDIOMA
--------------------------------------------------------

  ALTER TABLE "CID_IDIOMA" MODIFY ("CID_ID" NOT NULL ENABLE);
  ALTER TABLE "CID_IDIOMA" MODIFY ("IDIO_ID" NOT NULL ENABLE);
  ALTER TABLE "CID_IDIOMA" MODIFY ("CIID_TX_DESCRICAO" NOT NULL ENABLE);
  ALTER TABLE "CID_IDIOMA" ADD CONSTRAINT "PK_CIID" PRIMARY KEY ("CID_ID", "IDIO_ID");
--------------------------------------------------------
--  Constraints for Table PEDIDO_EXAME_AUD
--------------------------------------------------------

  ALTER TABLE "PEDIDO_EXAME_AUD" MODIFY ("PEEX_ID" NOT NULL ENABLE);
  ALTER TABLE "PEDIDO_EXAME_AUD" MODIFY ("AUDI_ID" NOT NULL ENABLE);
  ALTER TABLE "PEDIDO_EXAME_AUD" ADD CONSTRAINT "PK_PEEA" PRIMARY KEY ("PEEX_ID", "AUDI_ID");
--------------------------------------------------------
--  Constraints for Table ARQUIVO_EVOLUCAO
--------------------------------------------------------

  ALTER TABLE "ARQUIVO_EVOLUCAO" MODIFY ("AREV_ID" NOT NULL ENABLE);
  ALTER TABLE "ARQUIVO_EVOLUCAO" MODIFY ("EVOL_ID" NOT NULL ENABLE);
  ALTER TABLE "ARQUIVO_EVOLUCAO" MODIFY ("AREV_TX_CAMINHO_ARQUIVO" NOT NULL ENABLE);
  ALTER TABLE "ARQUIVO_EVOLUCAO" ADD CONSTRAINT "PK_AREV_ID" PRIMARY KEY ("AREV_ID");
--------------------------------------------------------
--  Constraints for Table CONFIGURACAO
--------------------------------------------------------

  ALTER TABLE "CONFIGURACAO" MODIFY ("CONF_ID" NOT NULL ENABLE);
  ALTER TABLE "CONFIGURACAO" MODIFY ("CONF_TX_VALOR" NOT NULL ENABLE);
  ALTER TABLE "CONFIGURACAO" ADD CONSTRAINT "PK_CONF" PRIMARY KEY ("CONF_ID");
--------------------------------------------------------
--  Constraints for Table PERFIL_LOG_EVOLUCAO
--------------------------------------------------------

  ALTER TABLE "PERFIL_LOG_EVOLUCAO" MODIFY ("PERF_ID" NOT NULL ENABLE);
  ALTER TABLE "PERFIL_LOG_EVOLUCAO" MODIFY ("LOEV_ID" NOT NULL ENABLE);
  ALTER TABLE "PERFIL_LOG_EVOLUCAO" ADD CONSTRAINT "PK_PELE" PRIMARY KEY ("PERF_ID", "LOEV_ID");
--------------------------------------------------------
--  Constraints for Table RESPONSAVEL
--------------------------------------------------------

  ALTER TABLE "RESPONSAVEL" MODIFY ("RESP_ID" NOT NULL ENABLE);
  ALTER TABLE "RESPONSAVEL" MODIFY ("RESP_TX_CPF" NOT NULL ENABLE);
  ALTER TABLE "RESPONSAVEL" MODIFY ("RESP_TX_NOME" NOT NULL ENABLE);
  ALTER TABLE "RESPONSAVEL" MODIFY ("RESP_TX_PARENTESCO" NOT NULL ENABLE);
  ALTER TABLE "RESPONSAVEL" ADD CONSTRAINT "PK_RESP" PRIMARY KEY ("RESP_ID");
--------------------------------------------------------
--  Constraints for Table VALOR_GENOTIPO_EXPAND_DOADOR
--------------------------------------------------------

  ALTER TABLE "VALOR_GENOTIPO_EXPAND_DOADOR" MODIFY ("VGED_ID" NOT NULL ENABLE);
  ALTER TABLE "VALOR_GENOTIPO_EXPAND_DOADOR" MODIFY ("VGED_NR_POSICAO" NOT NULL ENABLE);
  ALTER TABLE "VALOR_GENOTIPO_EXPAND_DOADOR" MODIFY ("VGED_TX_VALOR" NOT NULL ENABLE);
  ALTER TABLE "VALOR_GENOTIPO_EXPAND_DOADOR" MODIFY ("LOCU_ID" NOT NULL ENABLE);
  ALTER TABLE "VALOR_GENOTIPO_EXPAND_DOADOR" MODIFY ("GEDO_ID" NOT NULL ENABLE);
  ALTER TABLE "VALOR_GENOTIPO_EXPAND_DOADOR" MODIFY ("VGED_IN_TIPO_DOADOR" NOT NULL ENABLE);
  ALTER TABLE "VALOR_GENOTIPO_EXPAND_DOADOR" ADD CONSTRAINT "PK_VGED" PRIMARY KEY ("VGED_ID");
--------------------------------------------------------
--  Constraints for Table AVALIACAO_WORKUP_DOADOR
--------------------------------------------------------

  ALTER TABLE "AVALIACAO_WORKUP_DOADOR" MODIFY ("AVWD_ID" NOT NULL ENABLE);
  ALTER TABLE "AVALIACAO_WORKUP_DOADOR" MODIFY ("REWO_ID" NOT NULL ENABLE);
  ALTER TABLE "AVALIACAO_WORKUP_DOADOR" MODIFY ("AVWD_DT_CRIACAO" NOT NULL ENABLE);
  ALTER TABLE "AVALIACAO_WORKUP_DOADOR" ADD CONSTRAINT "PK_AVWD" PRIMARY KEY ("AVWD_ID");
--------------------------------------------------------
--  Constraints for Table LOCUS_EXAME_PRELIMINAR
--------------------------------------------------------

  ALTER TABLE "LOCUS_EXAME_PRELIMINAR" MODIFY ("LOEP_ID" NOT NULL ENABLE);
  ALTER TABLE "LOCUS_EXAME_PRELIMINAR" MODIFY ("LOEP_TX_CODIGO_LOCUS" NOT NULL ENABLE);
  ALTER TABLE "LOCUS_EXAME_PRELIMINAR" MODIFY ("LOEP_TX_PRIMEIRO_ALELO" NOT NULL ENABLE);
  ALTER TABLE "LOCUS_EXAME_PRELIMINAR" MODIFY ("LOEP_TX_SEGUNDO_ALELO" NOT NULL ENABLE);
  ALTER TABLE "LOCUS_EXAME_PRELIMINAR" MODIFY ("BUPR_ID" NOT NULL ENABLE);
  ALTER TABLE "LOCUS_EXAME_PRELIMINAR" ADD CONSTRAINT "PK_LOEP" PRIMARY KEY ("LOEP_ID");
--------------------------------------------------------
--  Constraints for Table PRE_CADASTRO_MEDICO_TELEFONE
--------------------------------------------------------

  ALTER TABLE "PRE_CADASTRO_MEDICO_TELEFONE" MODIFY ("PCMT_ID" NOT NULL ENABLE);
  ALTER TABLE "PRE_CADASTRO_MEDICO_TELEFONE" MODIFY ("PCMT_NR_COD_AREA" NOT NULL ENABLE);
  ALTER TABLE "PRE_CADASTRO_MEDICO_TELEFONE" MODIFY ("PCMT_IN_TIPO" NOT NULL ENABLE);
  ALTER TABLE "PRE_CADASTRO_MEDICO_TELEFONE" MODIFY ("PCMT_NR_NUMERO" NOT NULL ENABLE);
  ALTER TABLE "PRE_CADASTRO_MEDICO_TELEFONE" MODIFY ("PCMT_IN_PRINCIPAL" NOT NULL ENABLE);
  ALTER TABLE "PRE_CADASTRO_MEDICO_TELEFONE" MODIFY ("PRCM_ID" NOT NULL ENABLE);
  ALTER TABLE "PRE_CADASTRO_MEDICO_TELEFONE" ADD CONSTRAINT "PK_PCMT" PRIMARY KEY ("PCMT_ID");
--------------------------------------------------------
--  Constraints for Table VALOR_GENOTIPO_BUSCA_DOADOR
--------------------------------------------------------

  ALTER TABLE "VALOR_GENOTIPO_BUSCA_DOADOR" MODIFY ("VGBD_ID" NOT NULL ENABLE);
  ALTER TABLE "VALOR_GENOTIPO_BUSCA_DOADOR" MODIFY ("LOCU_ID" NOT NULL ENABLE);
  ALTER TABLE "VALOR_GENOTIPO_BUSCA_DOADOR" MODIFY ("VGBD_NR_POSICAO" NOT NULL ENABLE);
  ALTER TABLE "VALOR_GENOTIPO_BUSCA_DOADOR" MODIFY ("VGBD_NR_TIPO" NOT NULL ENABLE);
  ALTER TABLE "VALOR_GENOTIPO_BUSCA_DOADOR" MODIFY ("GEDO_ID" NOT NULL ENABLE);
  ALTER TABLE "VALOR_GENOTIPO_BUSCA_DOADOR" MODIFY ("VGBD_TX_VALOR" NOT NULL ENABLE);
  ALTER TABLE "VALOR_GENOTIPO_BUSCA_DOADOR" MODIFY ("VGBD_IN_TIPO_DOADOR" NOT NULL ENABLE);
  ALTER TABLE "VALOR_GENOTIPO_BUSCA_DOADOR" ADD CONSTRAINT "PK_VGBD" PRIMARY KEY ("VGBD_ID");
--------------------------------------------------------
--  Constraints for Table PACIENTE_AUD
--------------------------------------------------------

  ALTER TABLE "PACIENTE_AUD" MODIFY ("PACI_NR_RMR" NOT NULL ENABLE);
  ALTER TABLE "PACIENTE_AUD" MODIFY ("AUDI_ID" NOT NULL ENABLE);
  ALTER TABLE "PACIENTE_AUD" ADD CONSTRAINT "PK_PAAU" PRIMARY KEY ("PACI_NR_RMR", "AUDI_ID");
--------------------------------------------------------
--  Constraints for Table CENTRO_TRANSPLANTE
--------------------------------------------------------

  ALTER TABLE "CENTRO_TRANSPLANTE" MODIFY ("CETR_ID" NOT NULL ENABLE);
  ALTER TABLE "CENTRO_TRANSPLANTE" MODIFY ("CETR_TX_NOME" NOT NULL ENABLE);
  ALTER TABLE "CENTRO_TRANSPLANTE" MODIFY ("CETR_TX_CNPJ" NOT NULL ENABLE);
  ALTER TABLE "CENTRO_TRANSPLANTE" MODIFY ("CETR_TX_CNES" NOT NULL ENABLE);
  ALTER TABLE "CENTRO_TRANSPLANTE" MODIFY ("CETR_NR_ENTITY_STATUS" NOT NULL ENABLE);
  ALTER TABLE "CENTRO_TRANSPLANTE" ADD CONSTRAINT "PK_CETR" PRIMARY KEY ("CETR_ID");
--------------------------------------------------------
--  Constraints for Table PAGAMENTO_AUD
--------------------------------------------------------

  ALTER TABLE "PAGAMENTO_AUD" MODIFY ("AUDI_ID" NOT NULL ENABLE);
  ALTER TABLE "PAGAMENTO_AUD" MODIFY ("PAGA_ID" NOT NULL ENABLE);
  ALTER TABLE "PAGAMENTO_AUD" MODIFY ("PAGA_DT_CRIACAO" NOT NULL ENABLE);
  ALTER TABLE "PAGAMENTO_AUD" MODIFY ("PAGA_DT_ATUALIZACAO" NOT NULL ENABLE);
  ALTER TABLE "PAGAMENTO_AUD" MODIFY ("PAGA_IN_COBRACA" NOT NULL ENABLE);
  ALTER TABLE "PAGAMENTO_AUD" MODIFY ("TISE_ID" NOT NULL ENABLE);
  ALTER TABLE "PAGAMENTO_AUD" MODIFY ("STPA_ID" NOT NULL ENABLE);
  ALTER TABLE "PAGAMENTO_AUD" MODIFY ("REGI_ID" NOT NULL ENABLE);
  ALTER TABLE "PAGAMENTO_AUD" ADD CONSTRAINT "PK_PAAD" PRIMARY KEY ("AUDI_ID", "PAGA_ID");
--------------------------------------------------------
--  Constraints for Table LOG_EVOLUCAO
--------------------------------------------------------

  ALTER TABLE "LOG_EVOLUCAO" MODIFY ("LOEV_ID" NOT NULL ENABLE);
  ALTER TABLE "LOG_EVOLUCAO" MODIFY ("USUA_ID" NOT NULL ENABLE);
  ALTER TABLE "LOG_EVOLUCAO" MODIFY ("LOEV_IN_TIPO_EVENTO" NOT NULL ENABLE);
  ALTER TABLE "LOG_EVOLUCAO" MODIFY ("PACI_NR_RMR" NOT NULL ENABLE);
  ALTER TABLE "LOG_EVOLUCAO" MODIFY ("LOEV_DT_DATA" NOT NULL ENABLE);
  ALTER TABLE "LOG_EVOLUCAO" ADD CONSTRAINT "PK_LOEV" PRIMARY KEY ("LOEV_ID");
--------------------------------------------------------
--  Constraints for Table AUDITORIA
--------------------------------------------------------

  ALTER TABLE "AUDITORIA" MODIFY ("AUDI_ID" NOT NULL ENABLE);
  ALTER TABLE "AUDITORIA" ADD CONSTRAINT "PK_AUDI" PRIMARY KEY ("AUDI_ID");
--------------------------------------------------------
--  Constraints for Table MOTIVO_STATUS_DOADOR_RECURSO
--------------------------------------------------------

  ALTER TABLE "MOTIVO_STATUS_DOADOR_RECURSO" MODIFY ("MOSD_ID" NOT NULL ENABLE);
  ALTER TABLE "MOTIVO_STATUS_DOADOR_RECURSO" MODIFY ("RECU_ID" NOT NULL ENABLE);
  ALTER TABLE "MOTIVO_STATUS_DOADOR_RECURSO" ADD CONSTRAINT "PK_MOSR" PRIMARY KEY ("MOSD_ID", "RECU_ID");
--------------------------------------------------------
--  Constraints for Table QUALIFICACAO_MATCH
--------------------------------------------------------

  ALTER TABLE "QUALIFICACAO_MATCH" MODIFY ("QUMA_ID" NOT NULL ENABLE);
  ALTER TABLE "QUALIFICACAO_MATCH" MODIFY ("LOCU_ID" NOT NULL ENABLE);
  ALTER TABLE "QUALIFICACAO_MATCH" MODIFY ("QUMA_TX_QUALIFICACAO" NOT NULL ENABLE);
  ALTER TABLE "QUALIFICACAO_MATCH" MODIFY ("QUMA_NR_POSICAO" NOT NULL ENABLE);
  ALTER TABLE "QUALIFICACAO_MATCH" ADD CONSTRAINT "PK_QUMA" PRIMARY KEY ("QUMA_ID");
--------------------------------------------------------
--  Constraints for Table CENTRO_TRANSPLANTE_USUARIO
--------------------------------------------------------

  ALTER TABLE "CENTRO_TRANSPLANTE_USUARIO" MODIFY ("USUA_ID" NOT NULL ENABLE);
  ALTER TABLE "CENTRO_TRANSPLANTE_USUARIO" MODIFY ("CETR_ID" NOT NULL ENABLE);
  ALTER TABLE "CENTRO_TRANSPLANTE_USUARIO" MODIFY ("CETU_IN_RESPONSAVEL" NOT NULL ENABLE);
  ALTER TABLE "CENTRO_TRANSPLANTE_USUARIO" MODIFY ("CETU_ID" NOT NULL ENABLE);
  ALTER TABLE "CENTRO_TRANSPLANTE_USUARIO" ADD CONSTRAINT "CETU_ID" PRIMARY KEY ("CETU_ID");
--------------------------------------------------------
--  Constraints for Table NOVO_VALOR_NMDP
--------------------------------------------------------

  ALTER TABLE "NOVO_VALOR_NMDP" MODIFY ("NOVN_ID_CODIGO" NOT NULL ENABLE);
  ALTER TABLE "NOVO_VALOR_NMDP" MODIFY ("NOVN_TX_SUBTIPO" NOT NULL ENABLE);
  ALTER TABLE "NOVO_VALOR_NMDP" MODIFY ("NOVN_IN_AGRUPADO" NOT NULL ENABLE);
  ALTER TABLE "NOVO_VALOR_NMDP" ADD CONSTRAINT "PK_VANM1" PRIMARY KEY ("NOVN_ID_CODIGO");
--------------------------------------------------------
--  Constraints for Table PEDIDO_TRANSFERENCIA_CENTRO
--------------------------------------------------------

  ALTER TABLE "PEDIDO_TRANSFERENCIA_CENTRO" MODIFY ("PETC_ID" NOT NULL ENABLE);
  ALTER TABLE "PEDIDO_TRANSFERENCIA_CENTRO" MODIFY ("PETC_DT_CRIACAO" NOT NULL ENABLE);
  ALTER TABLE "PEDIDO_TRANSFERENCIA_CENTRO" MODIFY ("PETC_DT_ATUALIZACAO" NOT NULL ENABLE);
  ALTER TABLE "PEDIDO_TRANSFERENCIA_CENTRO" MODIFY ("PETC_IN_TIPO" NOT NULL ENABLE);
  ALTER TABLE "PEDIDO_TRANSFERENCIA_CENTRO" MODIFY ("PACI_NR_RMR" NOT NULL ENABLE);
  ALTER TABLE "PEDIDO_TRANSFERENCIA_CENTRO" MODIFY ("CETR_ID_ORIGEM" NOT NULL ENABLE);
  ALTER TABLE "PEDIDO_TRANSFERENCIA_CENTRO" MODIFY ("CETR_ID_DESTINO" NOT NULL ENABLE);
  ALTER TABLE "PEDIDO_TRANSFERENCIA_CENTRO" ADD CONSTRAINT "PK_PETC_ID" PRIMARY KEY ("PETC_ID");
--------------------------------------------------------
--  Constraints for Table EVOLUCAO
--------------------------------------------------------

  ALTER TABLE "EVOLUCAO" MODIFY ("EVOL_ID" NOT NULL ENABLE);
  ALTER TABLE "EVOLUCAO" MODIFY ("EVOL_DT_CRIACAO" NOT NULL ENABLE);
  ALTER TABLE "EVOLUCAO" MODIFY ("MOTI_ID" NOT NULL ENABLE);
  ALTER TABLE "EVOLUCAO" MODIFY ("EVOL_VL_PESO" NOT NULL ENABLE);
  ALTER TABLE "EVOLUCAO" MODIFY ("EVOL_VL_ALTURA" NOT NULL ENABLE);
  ALTER TABLE "EVOLUCAO" MODIFY ("EVOL_TX_TRATAMENTO_ANTERIOR" NOT NULL ENABLE);
  ALTER TABLE "EVOLUCAO" MODIFY ("EVOL_TX_TRATAMENTO_ATUAL" NOT NULL ENABLE);
  ALTER TABLE "EVOLUCAO" MODIFY ("EVOL_TX_CONDICAO_ATUAL" NOT NULL ENABLE);
  ALTER TABLE "EVOLUCAO" MODIFY ("COPA_ID" NOT NULL ENABLE);
  ALTER TABLE "EVOLUCAO" MODIFY ("PACI_NR_RMR" NOT NULL ENABLE);
  ALTER TABLE "EVOLUCAO" MODIFY ("USUA_ID" NOT NULL ENABLE);
  ALTER TABLE "EVOLUCAO" MODIFY ("EVOL_IN_EXAME_ANTICORPO" NOT NULL ENABLE);
  ALTER TABLE "EVOLUCAO" ADD CONSTRAINT "PK_EVOL" PRIMARY KEY ("EVOL_ID");
--------------------------------------------------------
--  Constraints for Table FONTE_CELULAS
--------------------------------------------------------

  ALTER TABLE "FONTE_CELULAS" MODIFY ("FOCE_ID" NOT NULL ENABLE);
  ALTER TABLE "FONTE_CELULAS" MODIFY ("FOCE_TX_SIGLA" NOT NULL ENABLE);
  ALTER TABLE "FONTE_CELULAS" MODIFY ("FOCE_TX_DESCRICAO" NOT NULL ENABLE);
  ALTER TABLE "FONTE_CELULAS" ADD CONSTRAINT "PK_FOCE" PRIMARY KEY ("FOCE_ID");
--------------------------------------------------------
--  Constraints for Table RESPOSTA_FORMULARIO_DOADOR
--------------------------------------------------------

  ALTER TABLE "RESPOSTA_FORMULARIO_DOADOR" MODIFY ("REFD_ID" NOT NULL ENABLE);
  ALTER TABLE "RESPOSTA_FORMULARIO_DOADOR" MODIFY ("FODO_ID" NOT NULL ENABLE);
  ALTER TABLE "RESPOSTA_FORMULARIO_DOADOR" MODIFY ("REFD_TX_TOKEN" NOT NULL ENABLE);
  ALTER TABLE "RESPOSTA_FORMULARIO_DOADOR" ADD CONSTRAINT "PK_REFD" PRIMARY KEY ("REFD_ID");
--------------------------------------------------------
--  Constraints for Table VALOR_G
--------------------------------------------------------

  ALTER TABLE "VALOR_G" MODIFY ("LOCU_ID" NOT NULL ENABLE);
  ALTER TABLE "VALOR_G" MODIFY ("VALG_TX_NOME_GRUPO" NOT NULL ENABLE);
  ALTER TABLE "VALOR_G" MODIFY ("VALG_NR_VALIDO" NOT NULL ENABLE);
  ALTER TABLE "VALOR_G" ADD CONSTRAINT "PK_VALG" PRIMARY KEY ("LOCU_ID", "VALG_TX_NOME_GRUPO");
--------------------------------------------------------
--  Constraints for Table BAIRRO_CORREIO
--------------------------------------------------------

  ALTER TABLE "BAIRRO_CORREIO" MODIFY ("BACO_ID" NOT NULL ENABLE);
  ALTER TABLE "BAIRRO_CORREIO" MODIFY ("BACO_TX_NOME" NOT NULL ENABLE);
  ALTER TABLE "BAIRRO_CORREIO" MODIFY ("LOCC_ID" NOT NULL ENABLE);
  ALTER TABLE "BAIRRO_CORREIO" MODIFY ("BACO_TX_DNE" NOT NULL ENABLE);
  ALTER TABLE "BAIRRO_CORREIO" ADD CONSTRAINT "PK_BACO" PRIMARY KEY ("BACO_ID");
--------------------------------------------------------
--  Constraints for Table BUSCA_CHECKLIST
--------------------------------------------------------

  ALTER TABLE "BUSCA_CHECKLIST" MODIFY ("BUCH_ID" NOT NULL ENABLE);
  ALTER TABLE "BUSCA_CHECKLIST" MODIFY ("BUCH_DT_CRIACAO" NOT NULL ENABLE);
  ALTER TABLE "BUSCA_CHECKLIST" MODIFY ("TIBC_ID" NOT NULL ENABLE);
  ALTER TABLE "BUSCA_CHECKLIST" MODIFY ("BUSC_ID" NOT NULL ENABLE);
  ALTER TABLE "BUSCA_CHECKLIST" ADD CONSTRAINT "PK_BUCH_ID" PRIMARY KEY ("BUCH_ID");
--------------------------------------------------------
--  Constraints for Table ENDERECO_CONTATO
--------------------------------------------------------

  ALTER TABLE "ENDERECO_CONTATO" MODIFY ("ENCO_ID" NOT NULL ENABLE);
  ALTER TABLE "ENDERECO_CONTATO" MODIFY ("ENCO_ID_PAIS" NOT NULL ENABLE);
  ALTER TABLE "ENDERECO_CONTATO" MODIFY ("ENCO_IN_EXCLUIDO" NOT NULL ENABLE);
  ALTER TABLE "ENDERECO_CONTATO" ADD CONSTRAINT "PK_ENCO" PRIMARY KEY ("ENCO_ID");
--------------------------------------------------------
--  Constraints for Table CID_ESTAGIO_DOENCA
--------------------------------------------------------

  ALTER TABLE "CID_ESTAGIO_DOENCA" MODIFY ("CID_ID" NOT NULL ENABLE);
  ALTER TABLE "CID_ESTAGIO_DOENCA" MODIFY ("ESDO_ID" NOT NULL ENABLE);
  ALTER TABLE "CID_ESTAGIO_DOENCA" ADD CONSTRAINT "PK_CIED" PRIMARY KEY ("CID_ID", "ESDO_ID");
--------------------------------------------------------
--  Constraints for Table BANCO_SANGUE_CORDAO
--------------------------------------------------------

  ALTER TABLE "BANCO_SANGUE_CORDAO" MODIFY ("BASC_ID" NOT NULL ENABLE);
  ALTER TABLE "BANCO_SANGUE_CORDAO" MODIFY ("BASC_TX_SIGLA" NOT NULL ENABLE);
  ALTER TABLE "BANCO_SANGUE_CORDAO" MODIFY ("BASC_TX_NOME" NOT NULL ENABLE);
  ALTER TABLE "BANCO_SANGUE_CORDAO" MODIFY ("BASC_TX_ENDERECO" NOT NULL ENABLE);
  ALTER TABLE "BANCO_SANGUE_CORDAO" MODIFY ("BASC_TX_CONTATO" NOT NULL ENABLE);
  ALTER TABLE "BANCO_SANGUE_CORDAO" ADD CONSTRAINT "PK_BASC" PRIMARY KEY ("BASC_ID");
--------------------------------------------------------
--  Constraints for Table BUSCA
--------------------------------------------------------

  ALTER TABLE "BUSCA" MODIFY ("BUSC_ID" NOT NULL ENABLE);
  ALTER TABLE "BUSCA" MODIFY ("PACI_NR_RMR" NOT NULL ENABLE);
  ALTER TABLE "BUSCA" MODIFY ("STBU_ID" NOT NULL ENABLE);
  ALTER TABLE "BUSCA" ADD CONSTRAINT "PK_BUSC" PRIMARY KEY ("BUSC_ID");
--------------------------------------------------------
--  Constraints for Table TRANSPORTE_TERRESTRE_AUD
--------------------------------------------------------

  ALTER TABLE "TRANSPORTE_TERRESTRE_AUD" MODIFY ("TRTE_ID" NOT NULL ENABLE);
  ALTER TABLE "TRANSPORTE_TERRESTRE_AUD" MODIFY ("AUDI_ID" NOT NULL ENABLE);
  ALTER TABLE "TRANSPORTE_TERRESTRE_AUD" ADD CONSTRAINT "PK_TRTA" PRIMARY KEY ("TRTE_ID", "AUDI_ID");
--------------------------------------------------------
--  Constraints for Table COMENTARIO_MATCH
--------------------------------------------------------

  ALTER TABLE "COMENTARIO_MATCH" MODIFY ("COMA_ID" NOT NULL ENABLE);
  ALTER TABLE "COMENTARIO_MATCH" MODIFY ("COMA_TX_COMENTARIO" NOT NULL ENABLE);
  ALTER TABLE "COMENTARIO_MATCH" MODIFY ("MATC_ID" NOT NULL ENABLE);
  ALTER TABLE "COMENTARIO_MATCH" ADD CONSTRAINT "PK_COMA" PRIMARY KEY ("COMA_ID");
--------------------------------------------------------
--  Constraints for Table MUNICIPIO
--------------------------------------------------------  
  ALTER TABLE "MUNICIPIO" ADD CONSTRAINT "PK_MUNI" PRIMARY KEY ("MUNI_ID");
  
--------------------------------------------------------
--  Ref Constraints for Table ARQUIVO_EVOLUCAO
--------------------------------------------------------

  ALTER TABLE "ARQUIVO_EVOLUCAO" ADD CONSTRAINT "FK_AREV_EVOL" FOREIGN KEY ("EVOL_ID")
	  REFERENCES "EVOLUCAO" ("EVOL_ID");
--------------------------------------------------------
--  Ref Constraints for Table ARQUIVO_EXAME
--------------------------------------------------------

  ALTER TABLE "ARQUIVO_EXAME" ADD CONSTRAINT "FK_AREX_EXAM" FOREIGN KEY ("EXAM_ID")
	  REFERENCES "EXAME" ("EXAM_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table ARQUIVO_PEDIDO_IDM
--------------------------------------------------------

  ALTER TABLE "ARQUIVO_PEDIDO_IDM" ADD CONSTRAINT "FK_ARPI_PEID" FOREIGN KEY ("PEID_ID")
	  REFERENCES "PEDIDO_IDM" ("PEID_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table ARQUIVO_PEDIDO_TRANSPORTE
--------------------------------------------------------

  ALTER TABLE "ARQUIVO_PEDIDO_TRANSPORTE" ADD CONSTRAINT "FK_ARPT_PETR" FOREIGN KEY ("PETR_ID")
	  REFERENCES "PEDIDO_TRANSPORTE" ("PETR_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table ARQUIVO_PRESCRICAO
--------------------------------------------------------

  ALTER TABLE "ARQUIVO_PRESCRICAO" ADD CONSTRAINT "FK_ARPR_PRES" FOREIGN KEY ("PRES_ID")
	  REFERENCES "PRESCRICAO" ("PRES_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table ARQUIVO_RELAT_INTERNACIONAL
--------------------------------------------------------

  ALTER TABLE "ARQUIVO_RELAT_INTERNACIONAL" ADD CONSTRAINT "FK_ARRI_BUSC" FOREIGN KEY ("BUSC_ID")
	  REFERENCES "BUSCA" ("BUSC_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table ARQUIVO_RESULTADO_WORKUP
--------------------------------------------------------

  ALTER TABLE "ARQUIVO_RESULTADO_WORKUP" ADD CONSTRAINT "FK_ARRW_REWO" FOREIGN KEY ("REWO_ID")
	  REFERENCES "RESULTADO_WORKUP" ("REWO_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table ARQUIVO_VOUCHER_LOGISTICA
--------------------------------------------------------

  ALTER TABLE "ARQUIVO_VOUCHER_LOGISTICA" ADD CONSTRAINT "FK_ARVL_PELO" FOREIGN KEY ("PELO_ID")
	  REFERENCES "PEDIDO_LOGISTICA" ("PELO_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table ARQUIVO_VOUCHER_LOGISTICA_AUD
--------------------------------------------------------

  ALTER TABLE "ARQUIVO_VOUCHER_LOGISTICA_AUD" ADD CONSTRAINT "FK_AVLA_AUDI" FOREIGN KEY ("AUDI_ID")
	  REFERENCES "AUDITORIA" ("AUDI_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table AVALIACAO
--------------------------------------------------------

  ALTER TABLE "AVALIACAO" ADD CONSTRAINT "FK_AVAL_CETR" FOREIGN KEY ("CETR_ID")
	  REFERENCES "CENTRO_TRANSPLANTE" ("CETR_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table AVALIACAO_AUD
--------------------------------------------------------

  ALTER TABLE "AVALIACAO_AUD" ADD CONSTRAINT "FK_AVAU_AUDI" FOREIGN KEY ("AUDI_ID")
	  REFERENCES "AUDITORIA" ("AUDI_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table AVALIACAO_CAMARA_TECNICA
--------------------------------------------------------

  ALTER TABLE "AVALIACAO_CAMARA_TECNICA" ADD CONSTRAINT "FK_AVCT_STCT" FOREIGN KEY ("STCT_ID")
	  REFERENCES "STATUS_AVALIACAO_CAMARA_TEC" ("STCT_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table AVALIACAO_NOVA_BUSCA
--------------------------------------------------------

  ALTER TABLE "AVALIACAO_NOVA_BUSCA" ADD CONSTRAINT "FK_AVNB_USUA" FOREIGN KEY ("USUA_ID_AVALIADOR")
	  REFERENCES "USUARIO" ("USUA_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table AVALIACAO_PEDIDO_COLETA
--------------------------------------------------------

  ALTER TABLE "AVALIACAO_PEDIDO_COLETA" ADD CONSTRAINT "FK_AVPC_USUA" FOREIGN KEY ("USUA_ID")
	  REFERENCES "USUARIO" ("USUA_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table AVALIACAO_PRESCRICAO
--------------------------------------------------------

  ALTER TABLE "AVALIACAO_PRESCRICAO" ADD CONSTRAINT "FK_AVPR_FOCE" FOREIGN KEY ("FOCE_ID")
	  REFERENCES "FONTE_CELULAS" ("FOCE_ID") ;
  ALTER TABLE "AVALIACAO_PRESCRICAO" ADD CONSTRAINT "FK_AVPR_USUA" FOREIGN KEY ("USUA_ID")
	  REFERENCES "USUARIO" ("USUA_ID") ;
  ALTER TABLE "AVALIACAO_PRESCRICAO" ADD CONSTRAINT "FK_AVPR_PRES" FOREIGN KEY ("PRES_ID")
	  REFERENCES "PRESCRICAO" ("PRES_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table AVALIACAO_RESULTADO_WORKUP
--------------------------------------------------------

  ALTER TABLE "AVALIACAO_RESULTADO_WORKUP" ADD CONSTRAINT "FK_AVRW_REWO" FOREIGN KEY ("REWO_ID")
	  REFERENCES "RESULTADO_WORKUP" ("REWO_ID") ;
  ALTER TABLE "AVALIACAO_RESULTADO_WORKUP" ADD CONSTRAINT "FK_AVRW_USUA" FOREIGN KEY ("USUA_ID_RESPONSAVEL")
	  REFERENCES "USUARIO" ("USUA_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table AVALIACAO_WORKUP_DOADOR
--------------------------------------------------------

  ALTER TABLE "AVALIACAO_WORKUP_DOADOR" ADD CONSTRAINT "FK_AVWD_USUA" FOREIGN KEY ("USUA_ID_RESPONSAVEL")
	  REFERENCES "USUARIO" ("USUA_ID") ;
  ALTER TABLE "AVALIACAO_WORKUP_DOADOR" ADD CONSTRAINT "FK_AVWD_REWO" FOREIGN KEY ("REWO_ID")
	  REFERENCES "RESULTADO_WORKUP" ("REWO_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table BAIRRO_CORREIO
--------------------------------------------------------

  ALTER TABLE "BAIRRO_CORREIO" ADD CONSTRAINT "FK_BACO_LOCC" FOREIGN KEY ("LOCC_ID")
	  REFERENCES "LOCALIDADE_CORREIO" ("LOCC_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table BUSCA
--------------------------------------------------------

  ALTER TABLE "BUSCA" ADD CONSTRAINT "FK_BUSC_CETR_TRANSPLANTE" FOREIGN KEY ("CETR_ID_CENTRO_TRANSPLANTE")
	  REFERENCES "CENTRO_TRANSPLANTE" ("CETR_ID") ;
  ALTER TABLE "BUSCA" ADD CONSTRAINT "FK_BUSC_STBU" FOREIGN KEY ("STBU_ID")
	  REFERENCES "STATUS_BUSCA" ("STBU_ID") ;
  ALTER TABLE "BUSCA" ADD CONSTRAINT "FK_BUSC_USUA" FOREIGN KEY ("USUA_ID")
	  REFERENCES "USUARIO" ("USUA_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table BUSCA_AUD
--------------------------------------------------------

  ALTER TABLE "BUSCA_AUD" ADD CONSTRAINT "FK_BUAU_AUDI" FOREIGN KEY ("AUDI_ID")
	  REFERENCES "AUDITORIA" ("AUDI_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table BUSCA_CHECKLIST
--------------------------------------------------------

  ALTER TABLE "BUSCA_CHECKLIST" ADD CONSTRAINT "FK_BUCH_BUSC" FOREIGN KEY ("BUSC_ID")
	  REFERENCES "BUSCA" ("BUSC_ID") ;
  ALTER TABLE "BUSCA_CHECKLIST" ADD CONSTRAINT "FK_BUCH_USUA" FOREIGN KEY ("USUA_ID")
	  REFERENCES "USUARIO" ("USUA_ID") ;
  ALTER TABLE "BUSCA_CHECKLIST" ADD CONSTRAINT "FK_BUCH_TIBH" FOREIGN KEY ("TIBC_ID")
	  REFERENCES "TIPO_BUSCA_CHECKLIST" ("TIBC_ID") ;
  ALTER TABLE "BUSCA_CHECKLIST" ADD CONSTRAINT "FK_BUCH_MATC" FOREIGN KEY ("MATC_ID")
	  REFERENCES "MATCH" ("MATC_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table CANCELAMENTO_BUSCA
--------------------------------------------------------

  ALTER TABLE "CANCELAMENTO_BUSCA" ADD CONSTRAINT "FK_BUSC_CABU" FOREIGN KEY ("BUSC_ID")
	  REFERENCES "BUSCA" ("BUSC_ID") ;
  ALTER TABLE "CANCELAMENTO_BUSCA" ADD CONSTRAINT "FK_MOCB_CABU" FOREIGN KEY ("MOCB_ID")
	  REFERENCES "MOTIVO_CANCELAMENTO_BUSCA" ("MOCB_ID") ;
  ALTER TABLE "CANCELAMENTO_BUSCA" ADD CONSTRAINT "FK_USUA_CABU" FOREIGN KEY ("USUA_ID")
	  REFERENCES "USUARIO" ("USUA_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table CATEGORIA_CHECKLIST
--------------------------------------------------------

  ALTER TABLE "CATEGORIA_CHECKLIST" ADD CONSTRAINT "FK_CACH_TICH" FOREIGN KEY ("TIPC_ID")
	  REFERENCES "TIPO_CHECKLIST" ("TIPC_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table CENTRO_TRANSPLANTE
--------------------------------------------------------

  ALTER TABLE "CENTRO_TRANSPLANTE" ADD CONSTRAINT "FK_CETR_LABO" FOREIGN KEY ("LABO_ID")
	  REFERENCES "LABORATORIO" ("LABO_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table CENTRO_TRANSPLANTE_USUARIO
--------------------------------------------------------

  ALTER TABLE "CENTRO_TRANSPLANTE_USUARIO" ADD CONSTRAINT "FK_CETU_CETR" FOREIGN KEY ("CETR_ID")
	  REFERENCES "CENTRO_TRANSPLANTE" ("CETR_ID") ;
  ALTER TABLE "CENTRO_TRANSPLANTE_USUARIO" ADD CONSTRAINT "FK_CETU_USU" FOREIGN KEY ("USUA_ID")
	  REFERENCES "USUARIO" ("USUA_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table CID_ESTAGIO_DOENCA
--------------------------------------------------------

  ALTER TABLE "CID_ESTAGIO_DOENCA" ADD CONSTRAINT "FK_CIED_CID" FOREIGN KEY ("CID_ID")
	  REFERENCES "CID" ("CID_ID") ;
  ALTER TABLE "CID_ESTAGIO_DOENCA" ADD CONSTRAINT "FK_CIED_ESDO" FOREIGN KEY ("ESDO_ID")
	  REFERENCES "ESTAGIO_DOENCA" ("ESDO_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table CID_IDIOMA
--------------------------------------------------------

  ALTER TABLE "CID_IDIOMA" ADD CONSTRAINT "FK_CIID_CID" FOREIGN KEY ("CID_ID")
	  REFERENCES "CID" ("CID_ID") ;
  ALTER TABLE "CID_IDIOMA" ADD CONSTRAINT "FK_CIID_IDIO" FOREIGN KEY ("IDIO_ID")
	  REFERENCES "IDIOMA" ("IDIO_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table COMENTARIO_MATCH
--------------------------------------------------------

  ALTER TABLE "COMENTARIO_MATCH" ADD CONSTRAINT "FK_COMA_MATC" FOREIGN KEY ("MATC_ID")
	  REFERENCES "MATCH" ("MATC_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table CONTATO_TELEFONICO
--------------------------------------------------------

  ALTER TABLE "CONTATO_TELEFONICO" ADD CONSTRAINT "FK_COTE_DOAD" FOREIGN KEY ("DOAD_ID")
	  REFERENCES "DOADOR" ("DOAD_ID") ;
  ALTER TABLE "CONTATO_TELEFONICO" ADD CONSTRAINT "FK_COTE_CETR" FOREIGN KEY ("CETR_ID")
	  REFERENCES "CENTRO_TRANSPLANTE" ("CETR_ID") ;
  ALTER TABLE "CONTATO_TELEFONICO" ADD CONSTRAINT "FK_COTE_MEDI" FOREIGN KEY ("MEDI_ID")
	  REFERENCES "MEDICO" ("MEDI_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table CONTATO_TELEFONICO_AUD
--------------------------------------------------------

  ALTER TABLE "CONTATO_TELEFONICO_AUD" ADD CONSTRAINT "FK_COTA_AUDI" FOREIGN KEY ("AUDI_ID")
	  REFERENCES "AUDITORIA" ("AUDI_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table DIAGNOSTICO
--------------------------------------------------------

  ALTER TABLE "DIAGNOSTICO" ADD CONSTRAINT "FK_DIAG_CID" FOREIGN KEY ("CID_ID")
	  REFERENCES "CID" ("CID_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table DIAGNOSTICO_AUD
--------------------------------------------------------

  ALTER TABLE "DIAGNOSTICO_AUD" ADD CONSTRAINT "FK_DIAU_AUDI" FOREIGN KEY ("AUDI_ID")
	  REFERENCES "AUDITORIA" ("AUDI_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table DIALOGO_BUSCA
--------------------------------------------------------

  ALTER TABLE "DIALOGO_BUSCA" ADD CONSTRAINT "FK_DIBU_BUSC" FOREIGN KEY ("BUSC_ID")
	  REFERENCES "BUSCA" ("BUSC_ID") ;
  ALTER TABLE "DIALOGO_BUSCA" ADD CONSTRAINT "FK_DIBU_USUA" FOREIGN KEY ("USUA_ID")
	  REFERENCES "USUARIO" ("USUA_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table DISPONIBILIDADE
--------------------------------------------------------

  ALTER TABLE "DISPONIBILIDADE" ADD CONSTRAINT "FK_DISP_PECL" FOREIGN KEY ("PECL_ID")
	  REFERENCES "PEDIDO_COLETA" ("PECL_ID") ;
  ALTER TABLE "DISPONIBILIDADE" ADD CONSTRAINT "FK_PEWO_DISP" FOREIGN KEY ("PEWO_ID")
	  REFERENCES "PEDIDO_WORKUP" ("PEWO_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table DISPONIBILIDADE_AUD
--------------------------------------------------------

  ALTER TABLE "DISPONIBILIDADE_AUD" ADD CONSTRAINT "FK_DISA_AUDI" FOREIGN KEY ("AUDI_ID")
	  REFERENCES "AUDITORIA" ("AUDI_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table DISPONIBILIDADE_CENTRO_COLETA
--------------------------------------------------------

  ALTER TABLE "DISPONIBILIDADE_CENTRO_COLETA" ADD CONSTRAINT "FK_DICC_CETR" FOREIGN KEY ("CETR_ID")
	  REFERENCES "CENTRO_TRANSPLANTE" ("CETR_ID") ;
  ALTER TABLE "DISPONIBILIDADE_CENTRO_COLETA" ADD CONSTRAINT "FK_DICC_PEWO" FOREIGN KEY ("PEWO_ID")
	  REFERENCES "PEDIDO_WORKUP" ("PEWO_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table DOADOR
--------------------------------------------------------

  ALTER TABLE "DOADOR" ADD CONSTRAINT "FK_DOAD_BASC" FOREIGN KEY ("BASC_ID")
	  REFERENCES "BANCO_SANGUE_CORDAO" ("BASC_ID") ;
  ALTER TABLE "DOADOR" ADD CONSTRAINT "FK_DOAD_UF" FOREIGN KEY ("UF_SIGLA")
	  REFERENCES "UF" ("UF_SIGLA") ;
  ALTER TABLE "DOADOR" ADD CONSTRAINT "FK_DOAD_USUA" FOREIGN KEY ("USUA_ID")
	  REFERENCES "USUARIO" ("USUA_ID") ;
  ALTER TABLE "DOADOR" ADD CONSTRAINT "FK_DOAD_ETNI" FOREIGN KEY ("ETNI_ID")
	  REFERENCES "ETNIA" ("ETNI_ID") ;
  ALTER TABLE "DOADOR" ADD CONSTRAINT "FK_DOAD_PAIS" FOREIGN KEY ("PAIS_ID")
	  REFERENCES "PAIS" ("PAIS_ID") ;
  ALTER TABLE "DOADOR" ADD CONSTRAINT "FK_DOAD_RACA" FOREIGN KEY ("RACA_ID")
	  REFERENCES "RACA" ("RACA_ID") ;
  ALTER TABLE "DOADOR" ADD CONSTRAINT "FK_DOAD_STDO" FOREIGN KEY ("STDO_ID")
	  REFERENCES "STATUS_DOADOR" ("STDO_ID") ;
  ALTER TABLE "DOADOR" ADD CONSTRAINT "FK_DOAD_MOSD" FOREIGN KEY ("MOSD_ID")
	  REFERENCES "MOTIVO_STATUS_DOADOR" ("MOSD_ID") ;
  ALTER TABLE "DOADOR" ADD CONSTRAINT "FK_DOAD_REGI_PAGAMENTO" FOREIGN KEY ("REGI_ID_PAGAMENTO")
	  REFERENCES "REGISTRO" ("REGI_ID") ;
  ALTER TABLE "DOADOR" ADD CONSTRAINT "FK_DOAD_REGI_ORIGEM" FOREIGN KEY ("REGI_ID_ORIGEM")
	  REFERENCES "REGISTRO" ("REGI_ID") ;
  ALTER TABLE "DOADOR" ADD CONSTRAINT "FK_DOAD_LABO" FOREIGN KEY ("LABO_ID")
	  REFERENCES "LABORATORIO" ("LABO_ID") ;
  ALTER TABLE "DOADOR" ADD CONSTRAINT "FK_DOAD_HEEN" FOREIGN KEY ("HEEN_ID")
	  REFERENCES "HEMO_ENTIDADE" ("HEEN_ID") ;
  ALTER TABLE "DOADOR" ADD CONSTRAINT "FK_DOAD_ESCI" FOREIGN KEY ("ESCI_ID") 
  	  REFERENCES "ESTADO_CIVIL" ("ESCI_ID");
  ALTER TABLE "DOADOR" ADD CONSTRAINT "FK_DOAD_MUNI" FOREIGN KEY ("MUNI_ID")
      REFERENCES "MUNICIPIO" ("MUNI_ID");
--------------------------------------------------------
--  Ref Constraints for Table DOADOR_AUD
--------------------------------------------------------

  ALTER TABLE "DOADOR_AUD" ADD CONSTRAINT "FK_DOAU_AUDI" FOREIGN KEY ("AUDI_ID")
	  REFERENCES "AUDITORIA" ("AUDI_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table EMAIL_CONTATO
--------------------------------------------------------

  ALTER TABLE "EMAIL_CONTATO" ADD CONSTRAINT "FK_EMCO_DOAD" FOREIGN KEY ("DOAD_ID")
	  REFERENCES "DOADOR" ("DOAD_ID") ;
  ALTER TABLE "EMAIL_CONTATO" ADD CONSTRAINT "FK_EMCO_MEDI" FOREIGN KEY ("MEDI_ID")
	  REFERENCES "MEDICO" ("MEDI_ID") ;
  ALTER TABLE "EMAIL_CONTATO" ADD CONSTRAINT "FK_EMCO_LABO" FOREIGN KEY ("LABO_ID")
	  REFERENCES "LABORATORIO" ("LABO_ID") ;
  ALTER TABLE "EMAIL_CONTATO" ADD CONSTRAINT "FK_EMCO_CETR" FOREIGN KEY ("CETR_ID")
      REFERENCES "CENTRO_TRANSPLANTE" ("CETR_ID");	  
--------------------------------------------------------
--  Ref Constraints for Table EMAIL_CONTATO_AUD
--------------------------------------------------------

  ALTER TABLE "EMAIL_CONTATO_AUD" ADD CONSTRAINT "FK_EMCA_AUDI" FOREIGN KEY ("AUDI_ID")
	  REFERENCES "AUDITORIA" ("AUDI_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table ENDERECO_CONTATO
--------------------------------------------------------

  ALTER TABLE "ENDERECO_CONTATO" ADD CONSTRAINT "FK_ENCO_DOAD" FOREIGN KEY ("DOAD_ID")
	  REFERENCES "DOADOR" ("DOAD_ID") ;
  ALTER TABLE "ENDERECO_CONTATO" ADD CONSTRAINT "FK_ENCO_HEEN" FOREIGN KEY ("HEEN_ID")
	  REFERENCES "HEMO_ENTIDADE" ("HEEN_ID") ;
  ALTER TABLE "ENDERECO_CONTATO" ADD CONSTRAINT "FK_ENCO_CETR" FOREIGN KEY ("CETR_ID")
	  REFERENCES "CENTRO_TRANSPLANTE" ("CETR_ID") ;
  ALTER TABLE "ENDERECO_CONTATO" ADD CONSTRAINT "FK_ENCO_PAIS" FOREIGN KEY ("ENCO_ID_PAIS")
	  REFERENCES "PAIS" ("PAIS_ID") ;
  ALTER TABLE "ENDERECO_CONTATO" ADD CONSTRAINT "FK_ENCO_MEDI" FOREIGN KEY ("MEDI_ID")
	  REFERENCES "MEDICO" ("MEDI_ID") ;
  ALTER TABLE "ENDERECO_CONTATO" ADD CONSTRAINT "FK_ENCO_MUNI" FOREIGN KEY ("MUNI_ID")
      REFERENCES "MUNICIPIO" ("MUNI_ID");	  
--------------------------------------------------------
--  Ref Constraints for Table ENDERECO_CONTATO_AUD
--------------------------------------------------------

  ALTER TABLE "ENDERECO_CONTATO_AUD" ADD CONSTRAINT "FK_ENCA_AUDI" FOREIGN KEY ("AUDI_ID")
	  REFERENCES "AUDITORIA" ("AUDI_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table EVOLUCAO
--------------------------------------------------------

  ALTER TABLE "EVOLUCAO" ADD CONSTRAINT "FK_EVOL_USUA" FOREIGN KEY ("USUA_ID")
	  REFERENCES "USUARIO" ("USUA_ID") ;
  ALTER TABLE "EVOLUCAO" ADD CONSTRAINT "FK_EVOL_ESDO" FOREIGN KEY ("ESDO_ID")
	  REFERENCES "ESTAGIO_DOENCA" ("ESDO_ID") ;
  ALTER TABLE "EVOLUCAO" ADD CONSTRAINT "FK_EVOL_COPA" FOREIGN KEY ("COPA_ID")
	  REFERENCES "CONDICAO_PACIENTE" ("COPA_ID") ;
  ALTER TABLE "EVOLUCAO" ADD CONSTRAINT "FK_EVOL_MOTI" FOREIGN KEY ("MOTI_ID")
	  REFERENCES "MOTIVO" ("MOTI_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table EXAME
--------------------------------------------------------

  ALTER TABLE "EXAME" ADD CONSTRAINT "FK_EXAM_LABO" FOREIGN KEY ("LABO_ID")
	  REFERENCES "LABORATORIO" ("LABO_ID") ;
  ALTER TABLE "EXAME" ADD CONSTRAINT "FK_EXAM_USUA" FOREIGN KEY ("USUA_ID")
	  REFERENCES "USUARIO" ("USUA_ID") ;
  ALTER TABLE "EXAME" ADD CONSTRAINT "FK_EXAM_MODE" FOREIGN KEY ("MODE_ID")
	  REFERENCES "MOTIVO_DESCARTE" ("MODE_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table EXAME_AUD
--------------------------------------------------------

  ALTER TABLE "EXAME_AUD" ADD CONSTRAINT "FK_EXAA_AUDI" FOREIGN KEY ("AUDI_ID")
	  REFERENCES "AUDITORIA" ("AUDI_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table FORMULARIO
--------------------------------------------------------

  ALTER TABLE "FORMULARIO" ADD CONSTRAINT "FK_FORM_TIFO" FOREIGN KEY ("TIFO_ID")
	  REFERENCES "TIPO_FORMULARIO" ("TIFO_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table FORMULARIO_DOADOR
--------------------------------------------------------

  ALTER TABLE "FORMULARIO_DOADOR" ADD CONSTRAINT "FK_FODO_DOAD" FOREIGN KEY ("DOAD_ID")
	  REFERENCES "DOADOR" ("DOAD_ID") ;
  ALTER TABLE "FORMULARIO_DOADOR" ADD CONSTRAINT "FK_FODO_FORM" FOREIGN KEY ("FORM_ID")
	  REFERENCES "FORMULARIO" ("FORM_ID") ;
  ALTER TABLE "FORMULARIO_DOADOR" ADD CONSTRAINT "FK_FODO_PECO" FOREIGN KEY ("PECO_ID")
	  REFERENCES "PEDIDO_CONTATO" ("PECO_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table FUNCAO_CENTRO_TRANSPLANTE
--------------------------------------------------------

  ALTER TABLE "FUNCAO_CENTRO_TRANSPLANTE" ADD CONSTRAINT "FK_FUCT_CETR" FOREIGN KEY ("CETR_ID")
	  REFERENCES "CENTRO_TRANSPLANTE" ("CETR_ID") ;
  ALTER TABLE "FUNCAO_CENTRO_TRANSPLANTE" ADD CONSTRAINT "FK_FUCT_FUTR" FOREIGN KEY ("FUTR_ID")
	  REFERENCES "FUNCAO_TRANSPLANTE" ("FUTR_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table GENOTIPO_BUSCA_PRELIMINAR
--------------------------------------------------------

  ALTER TABLE "GENOTIPO_BUSCA_PRELIMINAR" ADD CONSTRAINT "FK_GEBP_BUPR" FOREIGN KEY ("BUPR_ID")
	  REFERENCES "BUSCA_PRELIMINAR" ("BUPR_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table GENOTIPO_DOADOR
--------------------------------------------------------

  ALTER TABLE "GENOTIPO_DOADOR" ADD CONSTRAINT "FK_GEDO_DOAD" FOREIGN KEY ("DOAD_ID")
	  REFERENCES "DOADOR" ("DOAD_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table GENOTIPO_EXPANDIDO_PRELIMINAR
--------------------------------------------------------

  ALTER TABLE "GENOTIPO_EXPANDIDO_PRELIMINAR" ADD CONSTRAINT "FK_GEEP_BUPR" FOREIGN KEY ("BUPR_ID")
	  REFERENCES "BUSCA_PRELIMINAR" ("BUPR_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table GENOTIPO_PRELIMINAR
--------------------------------------------------------

  ALTER TABLE "GENOTIPO_PRELIMINAR" ADD CONSTRAINT "FK_GEPR_BUPR" FOREIGN KEY ("BUPR_ID")
	  REFERENCES "BUSCA_PRELIMINAR" ("BUPR_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table HEMO_ENTIDADE
--------------------------------------------------------

  ALTER TABLE "HEMO_ENTIDADE" ADD CONSTRAINT "FK_HEEN_HEEN" FOREIGN KEY ("HEEN_ID_HEMOCENTRO")
	  REFERENCES "HEMO_ENTIDADE" ("HEEN_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table HISTORICO_BUSCA
--------------------------------------------------------

  ALTER TABLE "HISTORICO_BUSCA" ADD CONSTRAINT "FK_HIBU_USUA" FOREIGN KEY ("USUA_ID")
	  REFERENCES "USUARIO" ("USUA_ID") ;
  ALTER TABLE "HISTORICO_BUSCA" ADD CONSTRAINT "FK_HIBU_CETR" FOREIGN KEY ("CETR_ID")
	  REFERENCES "CENTRO_TRANSPLANTE" ("CETR_ID") ;
  ALTER TABLE "HISTORICO_BUSCA" ADD CONSTRAINT "FK_HIBU_BUSC" FOREIGN KEY ("BUSC_ID")
	  REFERENCES "BUSCA" ("BUSC_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table HISTORICO_STATUS_PACIENTE
--------------------------------------------------------

  ALTER TABLE "HISTORICO_STATUS_PACIENTE" ADD CONSTRAINT "FK_HISP_STPA" FOREIGN KEY ("STPA_ID")
	  REFERENCES "STATUS_PACIENTE" ("STPA_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table INFO_PREVIA
--------------------------------------------------------

  ALTER TABLE "INFO_PREVIA" ADD CONSTRAINT "FK_INPR_PEWO" FOREIGN KEY ("PEWO_ID")
	  REFERENCES "PEDIDO_WORKUP" ("PEWO_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table INSTRUCAO_COLETA
--------------------------------------------------------

  ALTER TABLE "INSTRUCAO_COLETA" ADD CONSTRAINT "FK_INCO_LABO" FOREIGN KEY ("LABO_ID")
	  REFERENCES "LABORATORIO" ("LABO_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table ITENS_CHECKLIST
--------------------------------------------------------

  ALTER TABLE "ITENS_CHECKLIST" ADD CONSTRAINT "FK_ITEC_TIPC" FOREIGN KEY ("CACH_ID")
	  REFERENCES "CATEGORIA_CHECKLIST" ("CACH_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table LOCALIDADE_CORREIO
--------------------------------------------------------

  ALTER TABLE "LOCALIDADE_CORREIO" ADD CONSTRAINT "FK_LOCC_UNFC" FOREIGN KEY ("UNFC_ID")
	  REFERENCES "UNIDADE_FEDERATIVA_CORREIO" ("UNFC_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table LOCUS_EXAME
--------------------------------------------------------

  ALTER TABLE "LOCUS_EXAME" ADD CONSTRAINT "FK_LOEX_EXAM" FOREIGN KEY ("EXAM_ID")
	  REFERENCES "EXAME" ("EXAM_ID") ;
  ALTER TABLE "LOCUS_EXAME" ADD CONSTRAINT "FK_LOEX_LOCU" FOREIGN KEY ("LOCU_ID")
	  REFERENCES "LOCUS" ("LOCU_ID") ;
  ALTER TABLE "LOCUS_EXAME" ADD CONSTRAINT "FK_LOEX_USUA" FOREIGN KEY ("USUA_ID")
	  REFERENCES "USUARIO" ("USUA_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table LOCUS_EXAME_AUD
--------------------------------------------------------

  ALTER TABLE "LOCUS_EXAME_AUD" ADD CONSTRAINT "FK_LOEA_AUDI" FOREIGN KEY ("AUDI_ID")
	  REFERENCES "AUDITORIA" ("AUDI_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table LOCUS_EXAME_PRELIMINAR
--------------------------------------------------------

  ALTER TABLE "LOCUS_EXAME_PRELIMINAR" ADD CONSTRAINT "FK_LOEP_BUPR" FOREIGN KEY ("BUPR_ID")
	  REFERENCES "BUSCA_PRELIMINAR" ("BUPR_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table LOCUS_PEDIDO_EXAME
--------------------------------------------------------

  ALTER TABLE "LOCUS_PEDIDO_EXAME" ADD CONSTRAINT "FK_LOPE_LOCU" FOREIGN KEY ("LOCU_ID")
	  REFERENCES "LOCUS" ("LOCU_ID") ;
  ALTER TABLE "LOCUS_PEDIDO_EXAME" ADD CONSTRAINT "FK_LOPE_PEEX" FOREIGN KEY ("PEEX_ID")
	  REFERENCES "PEDIDO_EXAME" ("PEEX_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table LOCUS_PEDIDO_EXAME_AUD
--------------------------------------------------------

  ALTER TABLE "LOCUS_PEDIDO_EXAME_AUD" ADD CONSTRAINT "FK_LPEA_AUDI" FOREIGN KEY ("AUDI_ID")
	  REFERENCES "AUDITORIA" ("AUDI_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table LOG_EVOLUCAO
--------------------------------------------------------

  ALTER TABLE "LOG_EVOLUCAO" ADD CONSTRAINT "FK_LOEV_USUA" FOREIGN KEY ("USUA_ID")
	  REFERENCES "USUARIO" ("USUA_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table LOGRADOURO_CORREIO
--------------------------------------------------------

  ALTER TABLE "LOGRADOURO_CORREIO" ADD CONSTRAINT "FK_LOGC_BACO_INICIAL" FOREIGN KEY ("BACO_ID_FINAL")
	  REFERENCES "BAIRRO_CORREIO" ("BACO_ID") ;
  ALTER TABLE "LOGRADOURO_CORREIO" ADD CONSTRAINT "FK_LOGC_BACO_FINAL" FOREIGN KEY ("BACO_ID_INICIAL")
	  REFERENCES "BAIRRO_CORREIO" ("BACO_ID") ;
  ALTER TABLE "LOGRADOURO_CORREIO" ADD CONSTRAINT "FK_LOGC_TILO" FOREIGN KEY ("TILC_ID")
	  REFERENCES "TIPO_LOGRADOURO_CORREIO" ("TILC_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table MATCH
--------------------------------------------------------

  ALTER TABLE "MATCH" ADD CONSTRAINT "FK_MATC_DOAD" FOREIGN KEY ("DOAD_ID")
	  REFERENCES "DOADOR" ("DOAD_ID") ;
  ALTER TABLE "MATCH" ADD CONSTRAINT "FK_MATC_BUSC" FOREIGN KEY ("BUSC_ID")
	  REFERENCES "BUSCA" ("BUSC_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table MATCH_PRELIMINAR
--------------------------------------------------------

  ALTER TABLE "MATCH_PRELIMINAR" ADD CONSTRAINT "FK_MAPR_DOAD" FOREIGN KEY ("DOAD_ID")
	  REFERENCES "DOADOR" ("DOAD_ID") ;
  ALTER TABLE "MATCH_PRELIMINAR" ADD CONSTRAINT "FK_MAPR_BUPR" FOREIGN KEY ("BUPR_ID")
	  REFERENCES "BUSCA_PRELIMINAR" ("BUPR_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table MEDICO
--------------------------------------------------------

  ALTER TABLE "MEDICO" ADD CONSTRAINT "FK_MEDI_USUA" FOREIGN KEY ("USUA_ID")
	  REFERENCES "USUARIO" ("USUA_ID") ;
  ALTER TABLE "MEDICO" ADD CONSTRAINT "FK_MEDI_CETR" FOREIGN KEY ("CETR_ID")
	  REFERENCES "CENTRO_TRANSPLANTE" ("CETR_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table MEDICO_CT_REFERENCIA
--------------------------------------------------------

  ALTER TABLE "MEDICO_CT_REFERENCIA" ADD CONSTRAINT "FK_MECR_MEDI" FOREIGN KEY ("MEDI_ID")
	  REFERENCES "MEDICO" ("MEDI_ID") ;
  ALTER TABLE "MEDICO_CT_REFERENCIA" ADD CONSTRAINT "FK_MECR_CETR" FOREIGN KEY ("CETR_ID")
	  REFERENCES "CENTRO_TRANSPLANTE" ("CETR_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table METODOLOGIA_EXAME
--------------------------------------------------------

  ALTER TABLE "METODOLOGIA_EXAME" ADD CONSTRAINT "FK_MEEX_EXAM" FOREIGN KEY ("EXAM_ID")
	  REFERENCES "EXAME" ("EXAM_ID") ;
  ALTER TABLE "METODOLOGIA_EXAME" ADD CONSTRAINT "FK_MEEX_METO" FOREIGN KEY ("METO_ID")
	  REFERENCES "METODOLOGIA" ("METO_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table METODOLOGIA_EXAME_AUD
--------------------------------------------------------

  ALTER TABLE "METODOLOGIA_EXAME_AUD" ADD CONSTRAINT "FK_MEEA_AUDI" FOREIGN KEY ("AUDI_ID")
	  REFERENCES "AUDITORIA" ("AUDI_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table MOTIVO_STATUS_DOADOR
--------------------------------------------------------

  ALTER TABLE "MOTIVO_STATUS_DOADOR" ADD CONSTRAINT "FK_MOSD_STDO" FOREIGN KEY ("STDO_ID")
	  REFERENCES "STATUS_DOADOR" ("STDO_ID") ;	  
--------------------------------------------------------
--  Ref Constraints for Table MOTIVO_STATUS_DOADOR_RECURSO
--------------------------------------------------------

  ALTER TABLE "MOTIVO_STATUS_DOADOR_RECURSO" ADD CONSTRAINT "FK_MOSR_MOSD" FOREIGN KEY ("MOSD_ID")
	  REFERENCES "MOTIVO_STATUS_DOADOR" ("MOSD_ID") ;
  ALTER TABLE "MOTIVO_STATUS_DOADOR_RECURSO" ADD CONSTRAINT "FK_MOSR_RECU" FOREIGN KEY ("RECU_ID")
	  REFERENCES "RECURSO" ("RECU_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table MUNICIPIO
--------------------------------------------------------

	ALTER TABLE "MUNICIPIO" ADD CONSTRAINT "FK_MUNI_UF" FOREIGN KEY ("UF_SIGLA") REFERENCES "UF" ("UF_SIGLA");	  
--------------------------------------------------------
--  Ref Constraints for Table NOTIFICACAO
--------------------------------------------------------

  ALTER TABLE "NOTIFICACAO" ADD CONSTRAINT "FK_NOTI_CANO" FOREIGN KEY ("CANO_ID")
	  REFERENCES "CATEGORIA_NOTIFICACAO" ("CANO_ID") ;
  ALTER TABLE "NOTIFICACAO" ADD CONSTRAINT "FK_NOTI_USUA" FOREIGN KEY ("USUA_ID")
	  REFERENCES "USUARIO" ("USUA_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table PACIENTE_AUD
--------------------------------------------------------

  ALTER TABLE "PACIENTE_AUD" ADD CONSTRAINT "FK_PAAU_AUDI" FOREIGN KEY ("AUDI_ID")
	  REFERENCES "AUDITORIA" ("AUDI_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table PACIENTE_MISMATCH
--------------------------------------------------------

  ALTER TABLE "PACIENTE_MISMATCH" ADD CONSTRAINT "FK_PAMI_LOCU" FOREIGN KEY ("LOCU_ID")
	  REFERENCES "LOCUS" ("LOCU_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table PAGAMENTO
--------------------------------------------------------

  ALTER TABLE "PAGAMENTO" ADD CONSTRAINT "FK_PAGA_MATC" FOREIGN KEY ("MATC_ID")
	  REFERENCES "MATCH" ("MATC_ID") ;
  ALTER TABLE "PAGAMENTO" ADD CONSTRAINT "FK_PAGA_REGI" FOREIGN KEY ("REGI_ID")
	  REFERENCES "REGISTRO" ("REGI_ID") ;
  ALTER TABLE "PAGAMENTO" ADD CONSTRAINT "FK_PAGA_STPA" FOREIGN KEY ("STPA_ID")
	  REFERENCES "STATUS_PAGAMENTO" ("STPA_ID") ;
  ALTER TABLE "PAGAMENTO" ADD CONSTRAINT "FK_PAGA_TISE" FOREIGN KEY ("TISE_ID")
	  REFERENCES "TIPO_SERVICO" ("TISE_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table PAGAMENTO_AUD
--------------------------------------------------------

  ALTER TABLE "PAGAMENTO_AUD" ADD CONSTRAINT "FK_PAAD_AUDI" FOREIGN KEY ("AUDI_ID")
	  REFERENCES "AUDITORIA" ("AUDI_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table PEDIDO_ADICIONAL_WORKUP
--------------------------------------------------------

  ALTER TABLE "PEDIDO_ADICIONAL_WORKUP" ADD CONSTRAINT "FK_PEAW_AVRW" FOREIGN KEY ("AVRW_ID")
	  REFERENCES "AVALIACAO_RESULTADO_WORKUP" ("AVRW_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table PEDIDO_COLETA
--------------------------------------------------------

  ALTER TABLE "PEDIDO_COLETA" ADD CONSTRAINT "FK_PECO_MOCC" FOREIGN KEY ("MOCC_TX_CODIGO")
	  REFERENCES "MOTIVO_CANCELAMENTO_COLETA" ("MOCC_TX_CODIGO") ;
  ALTER TABLE "PEDIDO_COLETA" ADD CONSTRAINT "FK_PECL_STPC" FOREIGN KEY ("STPC_ID")
	  REFERENCES "STATUS_PEDIDO_COLETA" ("STPC_ID") ;
  ALTER TABLE "PEDIDO_COLETA" ADD CONSTRAINT "FK_PECL_CETR" FOREIGN KEY ("CETR_ID_COLETA")
	  REFERENCES "CENTRO_TRANSPLANTE" ("CETR_ID") ;
  ALTER TABLE "PEDIDO_COLETA" ADD CONSTRAINT "FK_PECL_USUA" FOREIGN KEY ("USUA_ID")
	  REFERENCES "USUARIO" ("USUA_ID") ;
  ALTER TABLE "PEDIDO_COLETA" ADD CONSTRAINT "FK_PECL_PEWO" FOREIGN KEY ("PEWO_ID")
	  REFERENCES "PEDIDO_WORKUP" ("PEWO_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table PEDIDO_CONTATO
--------------------------------------------------------

  ALTER TABLE "PEDIDO_CONTATO" ADD CONSTRAINT "FK_PECO_SOLI" FOREIGN KEY ("SOLI_ID")
	  REFERENCES "SOLICITACAO" ("SOLI_ID") ;
  ALTER TABLE "PEDIDO_CONTATO" ADD CONSTRAINT "FK_PECO_HEEN" FOREIGN KEY ("HEEN_ID")
	  REFERENCES "HEMO_ENTIDADE" ("HEEN_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table PEDIDO_ENRIQUECIMENTO
--------------------------------------------------------

  ALTER TABLE "PEDIDO_ENRIQUECIMENTO" ADD CONSTRAINT "FK_PEEN_SOLI" FOREIGN KEY ("SOLI_ID")
	  REFERENCES "SOLICITACAO" ("SOLI_ID") ;
  ALTER TABLE "PEDIDO_ENRIQUECIMENTO" ADD CONSTRAINT "FK_PEEN_USUA" FOREIGN KEY ("USUA_ID")
	  REFERENCES "USUARIO" ("USUA_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table PEDIDO_EXAME
--------------------------------------------------------

  ALTER TABLE "PEDIDO_EXAME" ADD CONSTRAINT "FK_PEEX_SOLI" FOREIGN KEY ("SOLI_ID")
	  REFERENCES "SOLICITACAO" ("SOLI_ID") ;
  ALTER TABLE "PEDIDO_EXAME" ADD CONSTRAINT "FK_PEEX_EXAM_CORDAO_INTER" FOREIGN KEY ("EXAM_ID_CORDAO_INTERNACIONAL")
	  REFERENCES "EXAME" ("EXAM_ID") ;
  ALTER TABLE "PEDIDO_EXAME" ADD CONSTRAINT "FK_PEEX_LABO" FOREIGN KEY ("LABO_ID")
	  REFERENCES "LABORATORIO" ("LABO_ID") ;
  ALTER TABLE "PEDIDO_EXAME" ADD CONSTRAINT "FK_PEEX_STPX" FOREIGN KEY ("STPX_ID")
	  REFERENCES "STATUS_PEDIDO_EXAME" ("STPX_ID") ;
  ALTER TABLE "PEDIDO_EXAME" ADD CONSTRAINT "FK_PEEX_EXAM" FOREIGN KEY ("EXAM_ID")
	  REFERENCES "EXAME" ("EXAM_ID") ;
  ALTER TABLE "PEDIDO_EXAME" ADD CONSTRAINT "FK_PEEX_TIEX" FOREIGN KEY ("TIEX_ID")
	  REFERENCES "TIPO_EXAME" ("TIEX_ID") ;
  ALTER TABLE "PEDIDO_EXAME" ADD CONSTRAINT "FK_PEEX_EXAM_DOADOR_INTER" FOREIGN KEY ("EXAM_ID_DOADOR_INTERNACIONAL")
	  REFERENCES "EXAME" ("EXAM_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table PEDIDO_EXAME_AUD
--------------------------------------------------------

  ALTER TABLE "PEDIDO_EXAME_AUD" ADD CONSTRAINT "FK_PEEA_AUDI" FOREIGN KEY ("AUDI_ID")
	  REFERENCES "AUDITORIA" ("AUDI_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table PEDIDO_IDM
--------------------------------------------------------

  ALTER TABLE "PEDIDO_IDM" ADD CONSTRAINT "FK_PEID_SOLI" FOREIGN KEY ("SOLI_ID")
	  REFERENCES "SOLICITACAO" ("SOLI_ID") ;
  ALTER TABLE "PEDIDO_IDM" ADD CONSTRAINT "FK_PEID_STPI" FOREIGN KEY ("STPI_ID")
	  REFERENCES "STATUS_PEDIDO_IDM" ("STPI_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table PEDIDO_IDM_AUD
--------------------------------------------------------

  ALTER TABLE "PEDIDO_IDM_AUD" ADD CONSTRAINT "FK_PEIA_AUDI" FOREIGN KEY ("AUDI_ID")
	  REFERENCES "AUDITORIA" ("AUDI_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table PEDIDO_LOGISTICA
--------------------------------------------------------

  ALTER TABLE "PEDIDO_LOGISTICA" ADD CONSTRAINT "FK_PELO_PECL" FOREIGN KEY ("PECL_ID")
	  REFERENCES "PEDIDO_COLETA" ("PECL_ID") ;
  ALTER TABLE "PEDIDO_LOGISTICA" ADD CONSTRAINT "FK_PELO_PEWO" FOREIGN KEY ("PEWO_ID")
	  REFERENCES "PEDIDO_WORKUP" ("PEWO_ID") ;
  ALTER TABLE "PEDIDO_LOGISTICA" ADD CONSTRAINT "FK_PELO_USUA" FOREIGN KEY ("USUA_ID_RESPONSAVEL")
	  REFERENCES "USUARIO" ("USUA_ID") ;
  ALTER TABLE "PEDIDO_LOGISTICA" ADD CONSTRAINT "FK_PELO_STPL" FOREIGN KEY ("STPL_ID")
	  REFERENCES "STATUS_PEDIDO_LOGISTICA" ("STPL_ID") ;  
  ALTER TABLE "PEDIDO_LOGISTICA" ADD CONSTRAINT "FK_PELO_PETR" FOREIGN KEY ("PETR_ID")
	  REFERENCES "PEDIDO_TRANSPORTE" ("PETR_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table PEDIDO_LOGISTICA_AUD
--------------------------------------------------------

  ALTER TABLE "PEDIDO_LOGISTICA_AUD" ADD CONSTRAINT "FK_PELA_AUDI" FOREIGN KEY ("AUDI_ID")
	  REFERENCES "AUDITORIA" ("AUDI_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table PEDIDO_TRANSFERENCIA_CENTRO
--------------------------------------------------------

  ALTER TABLE "PEDIDO_TRANSFERENCIA_CENTRO" ADD CONSTRAINT "FK_PETC_CETR_DESTINO" FOREIGN KEY ("CETR_ID_DESTINO")
	  REFERENCES "CENTRO_TRANSPLANTE" ("CETR_ID") ;
  ALTER TABLE "PEDIDO_TRANSFERENCIA_CENTRO" ADD CONSTRAINT "FK_PETC_CETR_ORIGEM" FOREIGN KEY ("CETR_ID_ORIGEM")
	  REFERENCES "CENTRO_TRANSPLANTE" ("CETR_ID") ;
  ALTER TABLE "PEDIDO_TRANSFERENCIA_CENTRO" ADD CONSTRAINT "FK_PETC_USUA" FOREIGN KEY ("USUA_ID")
	  REFERENCES "USUARIO" ("USUA_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table PEDIDO_TRANSPORTE
--------------------------------------------------------

  ALTER TABLE "PEDIDO_TRANSPORTE" ADD CONSTRAINT "FK_PETR_USUA" FOREIGN KEY ("USUA_ID_RESPONSAVEL")
	  REFERENCES "USUARIO" ("USUA_ID") ;
--  ALTER TABLE "PEDIDO_TRANSPORTE" ADD CONSTRAINT "FK_PETR_TRAN" FOREIGN KEY ("TRAN_ID")
--	  REFERENCES "TRANSPORTADORA" ("TRAN_ID") ;
  ALTER TABLE "PEDIDO_TRANSPORTE" ADD CONSTRAINT "FK_PETR_STPT" FOREIGN KEY ("STPT_ID")
	  REFERENCES "STATUS_PEDIDO_TRANSPORTE" ("STPT_ID") ;
--  ALTER TABLE "PEDIDO_TRANSPORTE" ADD CONSTRAINT "FK_PETR_COUR" FOREIGN KEY ("COUR_ID")
--	  REFERENCES "COURIER" ("COUR_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table PEDIDO_WORKUP
--------------------------------------------------------

  ALTER TABLE "PEDIDO_WORKUP" ADD CONSTRAINT "FK_PEWO_CETR1" FOREIGN KEY ("CETR_ID_TRANSP")
	  REFERENCES "CENTRO_TRANSPLANTE" ("CETR_ID") ;
  ALTER TABLE "PEDIDO_WORKUP" ADD CONSTRAINT "FK_PEWO_CETR" FOREIGN KEY ("CETR_ID_COLETA")
	  REFERENCES "CENTRO_TRANSPLANTE" ("CETR_ID") ;
  ALTER TABLE "PEDIDO_WORKUP" ADD CONSTRAINT "FK_SOLI_PEWO" FOREIGN KEY ("SOLI_ID")
	  REFERENCES "SOLICITACAO" ("SOLI_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table PEDIDO_WORKUP_AUD
--------------------------------------------------------

  ALTER TABLE "PEDIDO_WORKUP_AUD" ADD CONSTRAINT "FK_PEWA_AUDI" FOREIGN KEY ("AUDI_ID")
	  REFERENCES "AUDITORIA" ("AUDI_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table PENDENCIA
--------------------------------------------------------

  ALTER TABLE "PENDENCIA" ADD CONSTRAINT "FK_PEND_AVAL" FOREIGN KEY ("AVAL_ID")
	  REFERENCES "AVALIACAO" ("AVAL_ID") ;
  ALTER TABLE "PENDENCIA" ADD CONSTRAINT "FK_PEND_STPE" FOREIGN KEY ("STPE_ID")
	  REFERENCES "STATUS_PENDENCIA" ("STPE_ID") ;
  ALTER TABLE "PENDENCIA" ADD CONSTRAINT "FK_PEND_TIPE" FOREIGN KEY ("TIPE_ID")
	  REFERENCES "TIPO_PENDENCIA" ("TIPE_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table PENDENCIA_AUD
--------------------------------------------------------

  ALTER TABLE "PENDENCIA_AUD" ADD CONSTRAINT "FK_PEAU_AUDI" FOREIGN KEY ("AUDI_ID")
	  REFERENCES "AUDITORIA" ("AUDI_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table PERFIL
--------------------------------------------------------

  ALTER TABLE "PERFIL" ADD CONSTRAINT "FK_PERF_SIST" FOREIGN KEY ("SIST_ID")
	  REFERENCES "SISTEMA" ("SIST_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table PERFIL_LOG_EVOLUCAO
--------------------------------------------------------

  ALTER TABLE "PERFIL_LOG_EVOLUCAO" ADD CONSTRAINT "FK_PELE_LOEV" FOREIGN KEY ("LOEV_ID")
	  REFERENCES "LOG_EVOLUCAO" ("LOEV_ID") ;
  ALTER TABLE "PERFIL_LOG_EVOLUCAO" ADD CONSTRAINT "FK_PELE_PERF" FOREIGN KEY ("PERF_ID")
	  REFERENCES "PERFIL" ("PERF_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table PERMISSAO
--------------------------------------------------------

  ALTER TABLE "PERMISSAO" ADD CONSTRAINT "FK_PERM_RECU" FOREIGN KEY ("RECU_ID")
	  REFERENCES "RECURSO" ("RECU_ID") ;
  ALTER TABLE "PERMISSAO" ADD CONSTRAINT "FK_PERM_PERF" FOREIGN KEY ("PERF_ID")
	  REFERENCES "PERFIL" ("PERF_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table PRE_CADASTRO_MEDICO
--------------------------------------------------------

  ALTER TABLE "PRE_CADASTRO_MEDICO" ADD CONSTRAINT "FK_PRCM_USUA" FOREIGN KEY ("USUA_ID")
	  REFERENCES "USUARIO" ("USUA_ID") ;
  ALTER TABLE "PRE_CADASTRO_MEDICO" ADD CONSTRAINT "FK_PRCM_PCME" FOREIGN KEY ("PCME_ID")
	  REFERENCES "PRE_CADASTRO_MEDICO_ENDERECO" ("PCME_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table PRE_CADASTRO_MEDICO_EMAIL
--------------------------------------------------------

  ALTER TABLE "PRE_CADASTRO_MEDICO_EMAIL" ADD CONSTRAINT "FK_PRCM_PCEM" FOREIGN KEY ("PRCM_ID")
	  REFERENCES "PRE_CADASTRO_MEDICO" ("PRCM_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table PRE_CADASTRO_MEDICO_ENDERECOL
--------------------------------------------------------	  

  ALTER TABLE "PRE_CADASTRO_MEDICO_ENDERECO" ADD CONSTRAINT "FK_PCME_MUNI" FOREIGN KEY ("MUNI_ID")
      REFERENCES "MUNICIPIO" ("MUNI_ID");	  
--------------------------------------------------------
--  Ref Constraints for Table PRE_CADASTRO_MEDICO_TELEFONE
--------------------------------------------------------

  ALTER TABLE "PRE_CADASTRO_MEDICO_TELEFONE" ADD CONSTRAINT "FK_PRCM_PCMT" FOREIGN KEY ("PRCM_ID")
	  REFERENCES "PRE_CADASTRO_MEDICO" ("PRCM_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table PRE_CAD_MEDICO_CT_REFERENCIA
--------------------------------------------------------

  ALTER TABLE "PRE_CAD_MEDICO_CT_REFERENCIA" ADD CONSTRAINT "FK_PCCR_PRCM" FOREIGN KEY ("PRCM_ID")
	  REFERENCES "PRE_CADASTRO_MEDICO" ("PRCM_ID") ;
  ALTER TABLE "PRE_CAD_MEDICO_CT_REFERENCIA" ADD CONSTRAINT "FK_PCCR_CETR" FOREIGN KEY ("CETR_ID")
	  REFERENCES "CENTRO_TRANSPLANTE" ("CETR_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table PRESCRICAO
--------------------------------------------------------

  ALTER TABLE "PRESCRICAO" ADD CONSTRAINT "FK_PRES_SOLI" FOREIGN KEY ("SOLI_ID")
	  REFERENCES "SOLICITACAO" ("SOLI_ID") ;
  ALTER TABLE "PRESCRICAO" ADD CONSTRAINT "FK_PRES_MEDI" FOREIGN KEY ("MEDI_ID")
	  REFERENCES "MEDICO" ("MEDI_ID") ;
  ALTER TABLE "PRESCRICAO" ADD CONSTRAINT "FK_PRES_FOCE_OP_1" FOREIGN KEY ("FOCE_ID_OPCAO_1")
	  REFERENCES "FONTE_CELULAS" ("FOCE_ID") ;
  ALTER TABLE "PRESCRICAO" ADD CONSTRAINT "FK_PRES_FOCE_OP_2" FOREIGN KEY ("FOCE_ID_OPCAO_2")
	  REFERENCES "FONTE_CELULAS" ("FOCE_ID") ;
  ALTER TABLE "PRESCRICAO" ADD CONSTRAINT "FK_PRES_EVOL" FOREIGN KEY ("EVOL_ID")
	  REFERENCES "EVOLUCAO" ("EVOL_ID") ;
  ALTER TABLE "PRESCRICAO" ADD CONSTRAINT "FK_PRES_AVPR" FOREIGN KEY ("AVPR_ID")
	  REFERENCES "AVALIACAO_PRESCRICAO" ("AVPR_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table QUALIFICACAO_MATCH
--------------------------------------------------------

  ALTER TABLE "QUALIFICACAO_MATCH" ADD CONSTRAINT "FL_QUMA_MATC" FOREIGN KEY ("MATC_ID")
	  REFERENCES "MATCH" ("MATC_ID") ;
  ALTER TABLE "QUALIFICACAO_MATCH" ADD CONSTRAINT "FK_QUMA_DOAD" FOREIGN KEY ("DOAD_ID")
	  REFERENCES "DOADOR" ("DOAD_ID") ;
  ALTER TABLE "QUALIFICACAO_MATCH" ADD CONSTRAINT "FK_QUMA_LOCU" FOREIGN KEY ("LOCU_ID")
	  REFERENCES "LOCUS" ("LOCU_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table QUALIFICACAO_MATCH_PRELIMINAR
--------------------------------------------------------

  ALTER TABLE "QUALIFICACAO_MATCH_PRELIMINAR" ADD CONSTRAINT "FK_QUMP_DOAD" FOREIGN KEY ("DOAD_ID")
	  REFERENCES "DOADOR" ("DOAD_ID") ;
  ALTER TABLE "QUALIFICACAO_MATCH_PRELIMINAR" ADD CONSTRAINT "FK_QUMP_LOCU" FOREIGN KEY ("LOCU_ID")
	  REFERENCES "LOCUS" ("LOCU_ID") ;
  ALTER TABLE "QUALIFICACAO_MATCH_PRELIMINAR" ADD CONSTRAINT "FK_QUMP_MAPR" FOREIGN KEY ("MAPR_ID")
	  REFERENCES "MATCH_PRELIMINAR" ("MAPR_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table RASCUNHO
--------------------------------------------------------

  ALTER TABLE "RASCUNHO" ADD CONSTRAINT "FK_RASC_USUA" FOREIGN KEY ("USUA_ID")
	  REFERENCES "USUARIO" ("USUA_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table RECEBIMENTO_COLETA
--------------------------------------------------------

  ALTER TABLE "RECEBIMENTO_COLETA" ADD CONSTRAINT "FK_RECI_DECO" FOREIGN KEY ("DECO_ID")
	  REFERENCES "DESTINO_COLETA" ("DECO_ID") ;
  ALTER TABLE "RECEBIMENTO_COLETA" ADD CONSTRAINT "FK_RECI_FOCE" FOREIGN KEY ("FOCE_ID")
	  REFERENCES "FONTE_CELULAS" ("FOCE_ID") ;
  ALTER TABLE "RECEBIMENTO_COLETA" ADD CONSTRAINT "FK_RECI_PECL" FOREIGN KEY ("PECL_ID")
	  REFERENCES "PEDIDO_COLETA" ("PECL_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table REGISTRO
--------------------------------------------------------

  ALTER TABLE "REGISTRO" ADD CONSTRAINT "FK_REGI_PAIS" FOREIGN KEY ("PAIS_ID")
	  REFERENCES "PAIS" ("PAIS_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table RESERVA_DOADOR_INTERNACIONAL
--------------------------------------------------------

  ALTER TABLE "RESERVA_DOADOR_INTERNACIONAL" ADD CONSTRAINT "FK_REDI_DOAD" FOREIGN KEY ("DOAD_ID")
	  REFERENCES "DOADOR" ("DOAD_ID") ;
  ALTER TABLE "RESERVA_DOADOR_INTERNACIONAL" ADD CONSTRAINT "FK_REDI_BUSC" FOREIGN KEY ("BUSC_ID")
	  REFERENCES "BUSCA" ("BUSC_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table RESPONSAVEL_AUD
--------------------------------------------------------

  ALTER TABLE "RESPONSAVEL_AUD" ADD CONSTRAINT "FK_REAU_AUDI" FOREIGN KEY ("AUDI_ID")
	  REFERENCES "AUDITORIA" ("AUDI_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table RESPOSTA_CHECKLIST
--------------------------------------------------------

  ALTER TABLE "RESPOSTA_CHECKLIST" ADD CONSTRAINT "FK_RESC_PELO" FOREIGN KEY ("PELO_ID")
	  REFERENCES "PEDIDO_LOGISTICA" ("PELO_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table RESPOSTA_FORMULARIO_DOADOR
--------------------------------------------------------

  ALTER TABLE "RESPOSTA_FORMULARIO_DOADOR" ADD CONSTRAINT "FK_REFD_FODO" FOREIGN KEY ("FODO_ID")
	  REFERENCES "FORMULARIO_DOADOR" ("FODO_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table RESPOSTA_PEDIDO_ADICIONAL
--------------------------------------------------------

  ALTER TABLE "RESPOSTA_PEDIDO_ADICIONAL" ADD CONSTRAINT "FK_REPA_ARRW" FOREIGN KEY ("ARRW_ID")
	  REFERENCES "ARQUIVO_RESULTADO_WORKUP" ("ARRW_ID") ;
  ALTER TABLE "RESPOSTA_PEDIDO_ADICIONAL" ADD CONSTRAINT "FK_REPA_PEAW" FOREIGN KEY ("PEAW_ID")
	  REFERENCES "PEDIDO_ADICIONAL_WORKUP" ("PEAW_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table RESPOSTA_PENDENCIA
--------------------------------------------------------

  ALTER TABLE "RESPOSTA_PENDENCIA" ADD CONSTRAINT "FK_REPE_EVOL" FOREIGN KEY ("EVOL_ID")
	  REFERENCES "EVOLUCAO" ("EVOL_ID") ;
  ALTER TABLE "RESPOSTA_PENDENCIA" ADD CONSTRAINT "FK_REPE_EXAM" FOREIGN KEY ("EXAM_ID")
	  REFERENCES "EXAME" ("EXAM_ID") ;
  ALTER TABLE "RESPOSTA_PENDENCIA" ADD CONSTRAINT "FK_REPE_USUA" FOREIGN KEY ("USUA_ID")
	  REFERENCES "USUARIO" ("USUA_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table RESSALVA_DOADOR
--------------------------------------------------------

  ALTER TABLE "RESSALVA_DOADOR" ADD CONSTRAINT "FK_REDO_USUA" FOREIGN KEY ("USUA_ID_CRIACAO")
	  REFERENCES "USUARIO" ("USUA_ID") ;
  ALTER TABLE "RESSALVA_DOADOR" ADD CONSTRAINT "FK_REDO_DOAD" FOREIGN KEY ("DOAD_ID")
	  REFERENCES "DOADOR" ("DOAD_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table RESSALVA_DOADOR_AUD
--------------------------------------------------------

  ALTER TABLE "RESSALVA_DOADOR_AUD" ADD CONSTRAINT "FK_REDO_AUDI_ID" FOREIGN KEY ("AUDI_ID")
	  REFERENCES "AUDITORIA" ("AUDI_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table RESULTADO_WORKUP
--------------------------------------------------------

  ALTER TABLE "RESULTADO_WORKUP" ADD CONSTRAINT "FK_REWO_PEWO" FOREIGN KEY ("PEWO_ID")
	  REFERENCES "PEDIDO_WORKUP" ("PEWO_ID") ;
  ALTER TABLE "RESULTADO_WORKUP" ADD CONSTRAINT "FK_REWO_USUA" FOREIGN KEY ("USUA_ID")
	  REFERENCES "USUARIO" ("USUA_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table SOLICITACAO
--------------------------------------------------------

  ALTER TABLE "SOLICITACAO" ADD CONSTRAINT "FK_SOLI_BUSC" FOREIGN KEY ("BUSC_ID")
	  REFERENCES "BUSCA" ("BUSC_ID") ;
  ALTER TABLE "SOLICITACAO" ADD CONSTRAINT "FK_SOLI_MATC" FOREIGN KEY ("MATC_ID")
	  REFERENCES "MATCH" ("MATC_ID") ;
  ALTER TABLE "SOLICITACAO" ADD CONSTRAINT "FK_SOLI_TISO" FOREIGN KEY ("TISO_ID")
	  REFERENCES "TIPO_SOLICITACAO" ("TISO_ID") ;
  ALTER TABLE "SOLICITACAO" ADD CONSTRAINT "FK_SOLI_TIEX" FOREIGN KEY ("TIEX_ID")
	  REFERENCES "TIPO_EXAME" ("TIEX_ID") ;
  ALTER TABLE "SOLICITACAO" ADD CONSTRAINT "FK_SOLI_LABO" FOREIGN KEY ("LABO_ID")
	  REFERENCES "LABORATORIO" ("LABO_ID") ;
  ALTER TABLE "SOLICITACAO" ADD CONSTRAINT "FK_SOLI_USUA_RESPONSAVEL" FOREIGN KEY ("USUA_ID_RESPONSAVEL")
      REFERENCES "USUARIO" ("USUA_ID");
  ALTER TABLE "SOLICITACAO" ADD CONSTRAINT "FK_SOLI_MOCS" FOREIGN KEY ("MOCS_ID") 
      REFERENCES "MOTIVO_CANCELAMENTO_SOLICITACAO" ("MOCS_ID");
  ALTER TABLE "SOLICITACAO" ADD CONSTRAINT "FK_SOLI_CETR_TRANSPLANTE" FOREIGN KEY ("CETR_ID_TRANSPLANTE")
      REFERENCES "CENTRO_TRANSPLANTE" ("CETR_ID"); 
  ALTER TABLE "SOLICITACAO" ADD CONSTRAINT "FK_SOLI_CETR_COLETA" FOREIGN KEY ("CETR_ID_COLETA")
      REFERENCES "CENTRO_TRANSPLANTE" ("CETR_ID");
--------------------------------------------------------
--  Ref Constraints for Table SOLICITACAO_REDOMEWEB
--------------------------------------------------------

  ALTER TABLE "SOLICITACAO_REDOMEWEB" ADD CONSTRAINT "FK_SORE_PEEX" FOREIGN KEY ("PEEX_ID")
	  REFERENCES "PEDIDO_EXAME" ("PEEX_ID") ;

--------------------------------------------------------
--  Ref Constraints for Table TENTATIVA_CONTATO_DOADOR
--------------------------------------------------------

  ALTER TABLE "TENTATIVA_CONTATO_DOADOR" ADD CONSTRAINT "FK_TECD_COTE" FOREIGN KEY ("COTE_ID")
	  REFERENCES "CONTATO_TELEFONICO" ("COTE_ID") ;
  ALTER TABLE "TENTATIVA_CONTATO_DOADOR" ADD CONSTRAINT "FK_TECD_RETC" FOREIGN KEY ("RETC_ID")
	  REFERENCES "RESPOSTA_TENTATIVA_CONTATO" ("RETC_ID") ;
  ALTER TABLE "TENTATIVA_CONTATO_DOADOR" ADD CONSTRAINT "FK_TECD_PECO" FOREIGN KEY ("PECO_ID")
	  REFERENCES "PEDIDO_CONTATO" ("PECO_ID") ;
  ALTER TABLE "TENTATIVA_CONTATO_DOADOR" ADD CONSTRAINT "FK_TECD_USUA" FOREIGN KEY ("USUA_ID")
	  REFERENCES "USUARIO" ("USUA_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table TIPO_EXAME_LOCUS
--------------------------------------------------------

  ALTER TABLE "TIPO_EXAME_LOCUS" ADD CONSTRAINT "FK_TIEL_LOCU" FOREIGN KEY ("LOCU_ID")
	  REFERENCES "LOCUS" ("LOCU_ID") ;
  ALTER TABLE "TIPO_EXAME_LOCUS" ADD CONSTRAINT "FK_TIEL_TIEX" FOREIGN KEY ("TIEX_ID")
	  REFERENCES "TIPO_EXAME" ("TIEX_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table TRANSPORTE_TERRESTRE
--------------------------------------------------------

  ALTER TABLE "TRANSPORTE_TERRESTRE" ADD CONSTRAINT "FK_TRTE_PELO" FOREIGN KEY ("PELO_ID")
	  REFERENCES "PEDIDO_LOGISTICA" ("PELO_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table TRANSPORTE_TERRESTRE_AUD
--------------------------------------------------------

  ALTER TABLE "TRANSPORTE_TERRESTRE_AUD" ADD CONSTRAINT "FK_TRTA_AUDI" FOREIGN KEY ("AUDI_ID")
	  REFERENCES "AUDITORIA" ("AUDI_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table UNIDADE_FEDERATIVA_CORREIO
--------------------------------------------------------

  ALTER TABLE "UNIDADE_FEDERATIVA_CORREIO" ADD CONSTRAINT "FK_UNFC_PACO" FOREIGN KEY ("PACO_ID")
	  REFERENCES "PAIS_CORREIO" ("PACO_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table USUARIO
--------------------------------------------------------

  ALTER TABLE "USUARIO" ADD CONSTRAINT "FK_USUA_LABO" FOREIGN KEY ("LABO_ID")
	  REFERENCES "LABORATORIO" ("LABO_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table USUARIO_BANCO_SANGUE_CORDAO
--------------------------------------------------------

  ALTER TABLE "USUARIO_BANCO_SANGUE_CORDAO" ADD CONSTRAINT "FK_USUA_UBSC" FOREIGN KEY ("USUA_ID")
	  REFERENCES "USUARIO" ("USUA_ID") ;
  ALTER TABLE "USUARIO_BANCO_SANGUE_CORDAO" ADD CONSTRAINT "FK_BASC_UBSC" FOREIGN KEY ("BASC_ID")
	  REFERENCES "BANCO_SANGUE_CORDAO" ("BASC_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table USUARIO_PERFIL
--------------------------------------------------------

  ALTER TABLE "USUARIO_PERFIL" ADD CONSTRAINT "FK_USPE_USUA" FOREIGN KEY ("USUA_ID")
	  REFERENCES "USUARIO" ("USUA_ID") ;
  ALTER TABLE "USUARIO_PERFIL" ADD CONSTRAINT "FK_USPE_PERF" FOREIGN KEY ("PERF_ID")
	  REFERENCES "PERFIL" ("PERF_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table VALOR_DNA
--------------------------------------------------------

  ALTER TABLE "VALOR_DNA" ADD CONSTRAINT "FK_VADN_LOCU" FOREIGN KEY ("LOCU_ID")
	  REFERENCES "LOCUS" ("LOCU_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table VALOR_DNA_NMDP_VALIDO
--------------------------------------------------------

  ALTER TABLE "VALOR_DNA_NMDP_VALIDO" ADD CONSTRAINT "FK_DNNM_LOCU" FOREIGN KEY ("LOCU_ID")
	  REFERENCES "LOCUS" ("LOCU_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table VALOR_G
--------------------------------------------------------

  ALTER TABLE "VALOR_G" ADD CONSTRAINT "FK_VALG_LOCU" FOREIGN KEY ("LOCU_ID")
	  REFERENCES "LOCUS" ("LOCU_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table VALOR_GENOTIPO_BUSCA_DOADOR
--------------------------------------------------------

  ALTER TABLE "VALOR_GENOTIPO_BUSCA_DOADOR" ADD CONSTRAINT "FK_VGBD_LOCU" FOREIGN KEY ("LOCU_ID")
	  REFERENCES "LOCUS" ("LOCU_ID") ;
  ALTER TABLE "VALOR_GENOTIPO_BUSCA_DOADOR" ADD CONSTRAINT "FK_VGBD_GEDO" FOREIGN KEY ("GEDO_ID")
	  REFERENCES "GENOTIPO_DOADOR" ("GEDO_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table VALOR_GENOTIPO_BUSCA_PACIENTE
--------------------------------------------------------

  ALTER TABLE "VALOR_GENOTIPO_BUSCA_PACIENTE" ADD CONSTRAINT "FK_VGBP_GEPA" FOREIGN KEY ("GEPA_ID")
	  REFERENCES "GENOTIPO_PACIENTE" ("GEPA_ID") ;
  ALTER TABLE "VALOR_GENOTIPO_BUSCA_PACIENTE" ADD CONSTRAINT "FK_VGBP_LOCU" FOREIGN KEY ("LOCU_ID")
	  REFERENCES "LOCUS" ("LOCU_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table VALOR_GENOTIPO_DOADOR
--------------------------------------------------------

  ALTER TABLE "VALOR_GENOTIPO_DOADOR" ADD CONSTRAINT "FK_VAGD_GEDO" FOREIGN KEY ("GEDO_ID")
	  REFERENCES "GENOTIPO_DOADOR" ("GEDO_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table VALOR_GENOTIPO_EXPAND_DOADOR
--------------------------------------------------------

  ALTER TABLE "VALOR_GENOTIPO_EXPAND_DOADOR" ADD CONSTRAINT "FK_VGED_DOAD" FOREIGN KEY ("DOAD_ID")
	  REFERENCES "DOADOR" ("DOAD_ID") ;
  ALTER TABLE "VALOR_GENOTIPO_EXPAND_DOADOR" ADD CONSTRAINT "FK_VGED_GEDO" FOREIGN KEY ("GEDO_ID")
	  REFERENCES "GENOTIPO_DOADOR" ("GEDO_ID") ;
  ALTER TABLE "VALOR_GENOTIPO_EXPAND_DOADOR" ADD CONSTRAINT "FK_VGED_LOCU" FOREIGN KEY ("LOCU_ID")
	  REFERENCES "LOCUS" ("LOCU_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table VALOR_GENOTIPO_EXPAND_PACIENTE
--------------------------------------------------------

  ALTER TABLE "VALOR_GENOTIPO_EXPAND_PACIENTE" ADD CONSTRAINT "FK_VGEP_GEPA" FOREIGN KEY ("GEPA_ID")
	  REFERENCES "GENOTIPO_PACIENTE" ("GEPA_ID") ;
  ALTER TABLE "VALOR_GENOTIPO_EXPAND_PACIENTE" ADD CONSTRAINT "FK_VGEP_LOCU" FOREIGN KEY ("LOCU_ID")
	  REFERENCES "LOCUS" ("LOCU_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table VALOR_GENOTIPO_PACIENTE
--------------------------------------------------------

  ALTER TABLE "VALOR_GENOTIPO_PACIENTE" ADD CONSTRAINT "FK_VAGP_GEPA" FOREIGN KEY ("GEPA_ID")
	  REFERENCES "GENOTIPO_PACIENTE" ("GEPA_ID") ;
  ALTER TABLE "VALOR_GENOTIPO_PACIENTE" ADD CONSTRAINT "FK_VAGE_LOEX" FOREIGN KEY ("LOCU_ID", "EXAM_ID")
	  REFERENCES "LOCUS_EXAME" ("LOCU_ID", "EXAM_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table VALOR_P
--------------------------------------------------------

  ALTER TABLE "VALOR_P" ADD CONSTRAINT "FK_VALP_LOCU" FOREIGN KEY ("LOCU_ID")
	  REFERENCES "LOCUS" ("LOCU_ID") ;
--------------------------------------------------------
--  Ref Constraints for Table VALOR_SOROLOGICO
--------------------------------------------------------

  ALTER TABLE "VALOR_SOROLOGICO" ADD CONSTRAINT "FK_VASO_LOCU" FOREIGN KEY ("LOCU_ID")
	  REFERENCES "LOCUS" ("LOCU_ID") ;
--------------------------------------------------------
--  Constraints for Table SPLIT_VALOR_G
--------------------------------------------------------

  ALTER TABLE "SPLIT_VALOR_G" MODIFY ("LOCU_ID" NOT NULL ENABLE);
  ALTER TABLE "SPLIT_VALOR_G" MODIFY ("SPVG_TX_VALOR" NOT NULL ENABLE);
  ALTER TABLE "SPLIT_VALOR_G" MODIFY ("SPVG_TX_NOME_GRUPO" NOT NULL ENABLE);
  ALTER TABLE "SPLIT_VALOR_G" MODIFY ("SPVG_NR_VALIDO" NOT NULL ENABLE);
  ALTER TABLE "SPLIT_VALOR_G" ADD CONSTRAINT "PK_SPVG" PRIMARY KEY ("LOCU_ID", "SPVG_TX_VALOR", "SPVG_TX_NOME_GRUPO");
--------------------------------------------------------
--  Constraints for Table SPLIT_VALOR_P
--------------------------------------------------------

  ALTER TABLE "SPLIT_VALOR_P" MODIFY ("LOCU_ID" NOT NULL ENABLE);
  ALTER TABLE "SPLIT_VALOR_P" MODIFY ("SPVP_TX_VALOR" NOT NULL ENABLE);
  ALTER TABLE "SPLIT_VALOR_P" MODIFY ("SPVP_TX_NOME_GRUPO" NOT NULL ENABLE);
  ALTER TABLE "SPLIT_VALOR_P" MODIFY ("SPVP_NR_VALIDO" NOT NULL ENABLE);
  ALTER TABLE "SPLIT_VALOR_P" ADD CONSTRAINT "PK_SPVP" PRIMARY KEY ("LOCU_ID", "SPVP_TX_VALOR", "SPVP_TX_NOME_GRUPO");  
--------------------------------------------------------
--  Constraints for Table PEDIDO_ENVIO_EMDIS
--------------------------------------------------------

  ALTER TABLE "PEDIDO_ENVIO_EMDIS" MODIFY ("PEEE_ID" NOT NULL ENABLE);
  ALTER TABLE "PEDIDO_ENVIO_EMDIS" MODIFY ("PEEE_DT_CRIACAO" NOT NULL ENABLE);
  ALTER TABLE "PEDIDO_ENVIO_EMDIS" ADD CONSTRAINT "PK_PEED" PRIMARY KEY ("PEEE_ID");  
  
  
