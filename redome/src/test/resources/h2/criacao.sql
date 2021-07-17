SET MODE ORACLE;
--------------------------------------------------------
--  Arquivo criado - Sexta-feira-Abril-05-2019   
--------------------------------------------------------
/*DROP SEQUENCE "SQ_AREV_ID";
DROP SEQUENCE "SQ_AREX_ID";
DROP SEQUENCE "SQ_ARPI_ID";
DROP SEQUENCE "SQ_ARPR_ID";
DROP SEQUENCE "SQ_ARPT_ID";
DROP SEQUENCE "SQ_ARRI_ID";
DROP SEQUENCE "SQ_ARRW_ID";
DROP SEQUENCE "SQ_ARVL_ID";
DROP SEQUENCE "SQ_AUDI_ID";
DROP SEQUENCE "SQ_AVAL_ID";
DROP SEQUENCE "SQ_AVCT_ID";
DROP SEQUENCE "SQ_AVNB_ID";
DROP SEQUENCE "SQ_AVPC_ID";
DROP SEQUENCE "SQ_AVPR_ID";
DROP SEQUENCE "SQ_AVRW_ID";
DROP SEQUENCE "SQ_AVWD_ID";
DROP SEQUENCE "SQ_BASC_ID";
DROP SEQUENCE "SQ_BUCH_ID";
DROP SEQUENCE "SQ_BUPR_ID";
DROP SEQUENCE "SQ_BUSC_ID";
DROP SEQUENCE "SQ_CABU_ID";
DROP SEQUENCE "SQ_CETR_ID";
DROP SEQUENCE "SQ_CETU_ID";
DROP SEQUENCE "SQ_COJB_ID";
DROP SEQUENCE "SQ_COMA_ID";
DROP SEQUENCE "SQ_COTE_ID";
DROP SEQUENCE "SQ_DIBU_ID";
DROP SEQUENCE "SQ_DICC_ID";
DROP SEQUENCE "SQ_DISP_ID";
DROP SEQUENCE "SQ_DOAD_ID";
DROP SEQUENCE "SQ_DOAD_NR_DMR";
DROP SEQUENCE "SQ_DOIR_ID";
DROP SEQUENCE "SQ_EMCO_ID";
DROP SEQUENCE "SQ_ENCO_ID";
DROP SEQUENCE "SQ_ESIR_ID";
DROP SEQUENCE "SQ_EVOL_ID";
DROP SEQUENCE "SQ_EXAM_ID";
DROP SEQUENCE "SQ_FODO_ID";
DROP SEQUENCE "SQ_FORM_ID";
DROP SEQUENCE "SQ_GEBP_ID";
DROP SEQUENCE "SQ_GEBR_ID";
DROP SEQUENCE "SQ_GEDO_ID";
DROP SEQUENCE "SQ_GEEP_ID";
DROP SEQUENCE "SQ_GENO_ID";
DROP SEQUENCE "SQ_GEPA_ID";
DROP SEQUENCE "SQ_GEPR_ID";
DROP SEQUENCE "SQ_HEEN_ID";
DROP SEQUENCE "SQ_HIBU_ID";
DROP SEQUENCE "SQ_HISP_ID";
DROP SEQUENCE "SQ_INCO_ID";
DROP SEQUENCE "SQ_INPR_ID";
DROP SEQUENCE "SQ_ITEC_ID";
DROP SEQUENCE "SQ_LABO_ID";
DROP SEQUENCE "SQ_LOEP_ID";
DROP SEQUENCE "SQ_LOEV_ID";
DROP SEQUENCE "SQ_LOPE_ID";
DROP SEQUENCE "SQ_MAPR_ID";
DROP SEQUENCE "SQ_MATC_ID";
DROP SEQUENCE "SQ_MEDI_ID";
DROP SEQUENCE "SQ_METO_ID";
DROP SEQUENCE "SQ_NOTI_ID";
DROP SEQUENCE "SQ_PACI_NR_RMR";
DROP SEQUENCE "SQ_PAGA_ID";
DROP SEQUENCE "SQ_PCEM_ID";
DROP SEQUENCE "SQ_PCME_ID";
DROP SEQUENCE "SQ_PCMT_ID";
DROP SEQUENCE "SQ_PEAW_ID";
DROP SEQUENCE "SQ_PECL_ID";
DROP SEQUENCE "SQ_PECO_ID";
DROP SEQUENCE "SQ_PEEN_ID";
DROP SEQUENCE "SQ_PEEX_ID";
DROP SEQUENCE "SQ_PEID_ID";
DROP SEQUENCE "SQ_PELO_ID";
DROP SEQUENCE "SQ_PEND_ID";
DROP SEQUENCE "SQ_PERF_ID";
DROP SEQUENCE "SQ_PETC_ID";
DROP SEQUENCE "SQ_PETR_ID";
DROP SEQUENCE "SQ_PEWO_ID";
DROP SEQUENCE "SQ_PRCM_ID";
DROP SEQUENCE "SQ_PRES_ID";
DROP SEQUENCE "SQ_PROC_ID";
DROP SEQUENCE "SQ_QUMA_ID";
DROP SEQUENCE "SQ_QUMP_ID";
DROP SEQUENCE "SQ_RASC_ID";
DROP SEQUENCE "SQ_RECO_ID";
DROP SEQUENCE "SQ_REDI_ID";
DROP SEQUENCE "SQ_REDO_ID";
DROP SEQUENCE "SQ_REFD_ID";
DROP SEQUENCE "SQ_RELA_ID";
DROP SEQUENCE "SQ_REPA_ID";
DROP SEQUENCE "SQ_REPE_ID";
DROP SEQUENCE "SQ_RESC_ID";
DROP SEQUENCE "SQ_RESP_ID";
DROP SEQUENCE "SQ_RETC_ID";
DROP SEQUENCE "SQ_REWO_ID";
DROP SEQUENCE "SQ_SOLI_ID";
DROP SEQUENCE "SQ_SORE_ID";
DROP SEQUENCE "SQ_TARE_ID";
DROP SEQUENCE "SQ_TECD_ID";
DROP SEQUENCE "SQ_TRTE_ID";
DROP SEQUENCE "SQ_USUA_ID";
DROP SEQUENCE "SQ_VAGB_ID";
DROP SEQUENCE "SQ_VAGE_ID";
DROP SEQUENCE "SQ_VEAB_ID";
DROP SEQUENCE "SQ_VGBD_ID";
DROP SEQUENCE "SQ_VGED_ID";
DROP SEQUENCE "SQ_VGEE_ID";
DROP TABLE "ARQUIVO_EVOLUCAO";
DROP TABLE "ARQUIVO_EXAME";
DROP TABLE "ARQUIVO_PEDIDO_IDM";
DROP TABLE "ARQUIVO_PEDIDO_TRANSPORTE";
DROP TABLE "ARQUIVO_PRESCRICAO";
DROP TABLE "ARQUIVO_RELAT_INTERNACIONAL";
DROP TABLE "ARQUIVO_RESULTADO_WORKUP";
DROP TABLE "ARQUIVO_RESULTADO_WORKUP_AUD";
DROP TABLE "ARQUIVO_VOUCHER_LOGISTICA";
DROP TABLE "ARQUIVO_VOUCHER_LOGISTICA_AUD";
DROP TABLE "ASSOCIA_RESPOSTA_PENDENCIA";
DROP TABLE "AUDITORIA";
DROP TABLE "AVALIACAO";
DROP TABLE "AVALIACAO_AUD";
DROP TABLE "AVALIACAO_CAMARA_TECNICA";
DROP TABLE "AVALIACAO_NOVA_BUSCA";
DROP TABLE "AVALIACAO_PEDIDO_COLETA";
DROP TABLE "AVALIACAO_PRESCRICAO";
DROP TABLE "AVALIACAO_RESULTADO_WORKUP";
DROP TABLE "AVALIACAO_WORKUP_DOADOR";
DROP TABLE "BAIRRO_CORREIO";
DROP TABLE "BANCO_SANGUE_CORDAO";
DROP TABLE "BUSCA";
DROP TABLE "BUSCA_AUD";
DROP TABLE "BUSCA_CHECKLIST";
DROP TABLE "BUSCA_PRELIMINAR";
DROP TABLE "CANCELAMENTO_BUSCA";
DROP TABLE "CATEGORIA_CHECKLIST";
DROP TABLE "CATEGORIA_NOTIFICACAO";
DROP TABLE "CENTRO_TRANSPLANTE";
DROP TABLE "CENTRO_TRANSPLANTE_USUARIO";
DROP TABLE "CID";
DROP TABLE "CID_ESTAGIO_DOENCA";
DROP TABLE "CID_IDIOMA";
DROP TABLE "CLASSIFICACAO_ABO";
DROP TABLE "COMENTARIO_MATCH";
DROP TABLE "CONDICAO_PACIENTE";
DROP TABLE "CONFIGURACAO";
DROP TABLE "CONSTANTE_RELATORIO";
DROP TABLE "CONSTANTE_RELATORIO_AUD";
DROP TABLE "CONTATO_TELEFONICO";
DROP TABLE "CONTATO_TELEFONICO_AUD";
DROP TABLE "CONTROLE_JOB_BRASILCORD";
DROP TABLE "COPIA_VALOR_DNA_NMDP";
DROP TABLE "dados_sem_dna_nmdp-12_35_09";
DROP TABLE "DESTINO_COLETA";
DROP TABLE "DIAGNOSTICO";
DROP TABLE "DIAGNOSTICO_AUD";
DROP TABLE "DIALOGO_BUSCA";
DROP TABLE "DISPONIBILIDADE";
DROP TABLE "DISPONIBILIDADE_AUD";
DROP TABLE "DISPONIBILIDADE_CENTRO_COLETA";
DROP TABLE "DOADOR";
DROP TABLE "DOADOR_AUD";
DROP TABLE "DOADOR_IN_REDOME";
DROP TABLE "EMAIL_CONTATO";
DROP TABLE "EMAIL_CONTATO_AUD";
DROP TABLE "ENDERECO_CONTATO";
DROP TABLE "ENDERECO_CONTATO_AUD";
DROP TABLE "ESTABELECIMENTO_IN_REDOME";
DROP TABLE "ESTAGIO_DOENCA";
DROP TABLE "ETNIA";
DROP TABLE "ET$0111A1440001";
DROP TABLE "EVOLUCAO";
DROP TABLE "EXAME";
DROP TABLE "EXAME_AUD";
DROP TABLE "FONTE_CELULAS";
DROP TABLE "FORMULARIO";
DROP TABLE "FORMULARIO_DOADOR";
DROP TABLE "FUNCAO_CENTRO_TRANSPLANTE";
DROP TABLE "FUNCAO_TRANSPLANTE";
DROP TABLE "GENOTIPO_BUSCA_PRELIMINAR";
DROP TABLE "GENOTIPO_DOADOR";
DROP TABLE "GENOTIPO_EXPANDIDO_PRELIMINAR";
DROP TABLE "GENOTIPO_PACIENTE";
DROP TABLE "GENOTIPO_PRELIMINAR";
DROP TABLE "HEMO_ENTIDADE";
DROP TABLE "HISTORICO_BUSCA";
DROP TABLE "HISTORICO_STATUS_PACIENTE";
DROP TABLE "IDIOMA";
DROP TABLE "INFO_PREVIA";
DROP TABLE "INSTRUCAO_COLETA";
DROP TABLE "ITENS_CHECKLIST";
DROP TABLE "LABORATORIO";
DROP TABLE "LOCALIDADE_CORREIO";
DROP TABLE "LOCUS";
DROP TABLE "LOCUS_EXAME";
DROP TABLE "LOCUS_EXAME_AUD";
DROP TABLE "LOCUS_EXAME_PRELIMINAR";
DROP TABLE "LOCUS_PEDIDO_EXAME";
DROP TABLE "LOCUS_PEDIDO_EXAME_AUD";
DROP TABLE "LOG_EVOLUCAO";
DROP TABLE "LOGRADOURO_CORREIO";
DROP TABLE "MATCH";
DROP TABLE "MATCH_PRELIMINAR";
DROP TABLE "MEDICO";
DROP TABLE "MEDICO_CT_REFERENCIA";
DROP TABLE "METODOLOGIA";
DROP TABLE "METODOLOGIA_EXAME";
DROP TABLE "METODOLOGIA_EXAME_AUD";
DROP TABLE "MOTIVO";
DROP TABLE "MOTIVO_CANCELAMENTO_BUSCA";
DROP TABLE "MOTIVO_CANCELAMENTO_COLETA";
DROP TABLE "MOTIVO_CANCELAMENTO_WORKUP";
DROP TABLE "MOTIVO_DESCARTE";
DROP TABLE "MOTIVO_STATUS_DOADOR";
DROP TABLE "MOTIVO_STATUS_DOADOR_RECURSO";
DROP TABLE "NOTIFICACAO";
DROP TABLE "NOVO_VALOR_NMDP";
DROP TABLE "PACIENTE";
DROP TABLE "PACIENTE_AUD";
DROP TABLE "PACIENTE_IN_REREME";
DROP TABLE "PACIENTE_MISMATCH";
DROP TABLE "PAGAMENTO";
DROP TABLE "PAGAMENTO_AUD";
DROP TABLE "PAIS";
DROP TABLE "PAIS_CORREIO";
DROP TABLE "PEDIDO_ADICIONAL_WORKUP";
DROP TABLE "PEDIDO_COLETA";
DROP TABLE "PEDIDO_CONTATO";
DROP TABLE "PEDIDO_ENRIQUECIMENTO";
DROP TABLE "PEDIDO_EXAME";
DROP TABLE "PEDIDO_EXAME_AUD";
DROP TABLE "PEDIDO_IDM";
DROP TABLE "PEDIDO_IDM_AUD";
DROP TABLE "PEDIDO_LOGISTICA";
DROP TABLE "PEDIDO_LOGISTICA_AUD";
DROP TABLE "PEDIDO_TRANSFERENCIA_CENTRO";
DROP TABLE "PEDIDO_TRANSPORTE";
DROP TABLE "PEDIDO_WORKUP";
DROP TABLE "PEDIDO_WORKUP_AUD";
DROP TABLE "PENDENCIA";
DROP TABLE "PENDENCIA_AUD";
DROP TABLE "PERFIL";
DROP TABLE "PERFIL_LOG_EVOLUCAO";
DROP TABLE "PERMISSAO";
DROP TABLE "PRE_CADASTRO_MEDICO";
DROP TABLE "PRE_CADASTRO_MEDICO_EMAIL";
DROP TABLE "PRE_CADASTRO_MEDICO_ENDERECO";
DROP TABLE "PRE_CADASTRO_MEDICO_TELEFONE";
DROP TABLE "PRE_CAD_MEDICO_CT_REFERENCIA";
DROP TABLE "PRESCRICAO";

DROP TABLE "QUALIFICACAO_MATCH";
DROP TABLE "QUALIFICACAO_MATCH_PRELIMINAR";
DROP TABLE "RACA";
DROP TABLE "RASCUNHO";
DROP TABLE "RECEBIMENTO_COLETA";
DROP TABLE "RECURSO";
DROP TABLE "REGISTRO";
DROP TABLE "RELATORIO";
DROP TABLE "RESERVA_DOADOR_INTERNACIONAL";
DROP TABLE "RESPONSAVEL";
DROP TABLE "RESPONSAVEL_AUD";
DROP TABLE "RESPOSTA_CHECKLIST";
DROP TABLE "RESPOSTA_FORMULARIO_DOADOR";
DROP TABLE "RESPOSTA_PEDIDO_ADICIONAL";
DROP TABLE "RESPOSTA_PENDENCIA";
DROP TABLE "RESPOSTA_TENTATIVA_CONTATO";
DROP TABLE "RESSALVA_DOADOR";
DROP TABLE "RESSALVA_DOADOR_AUD";
DROP TABLE "RESULTADO_WORKUP";
DROP TABLE "RESULTADO_WORKUP_AUD";
DROP TABLE "SISTEMA";
DROP TABLE "SOLICITACAO";
DROP TABLE "SOLICITACAO_REDOMEWEB";
DROP TABLE "SPLIT_NMDP";
DROP TABLE "SPLIT_VALOR_DNA";
DROP TABLE "STATUS_AVALIACAO_CAMARA_TEC";
DROP TABLE "STATUS_BUSCA";
DROP TABLE "STATUS_DOADOR";
DROP TABLE "STATUS_PACIENTE";
DROP TABLE "STATUS_PAGAMENTO";
DROP TABLE "STATUS_PEDIDO_COLETA";
DROP TABLE "STATUS_PEDIDO_EXAME";
DROP TABLE "STATUS_PEDIDO_IDM";
DROP TABLE "STATUS_PEDIDO_LOGISTICA";
DROP TABLE "STATUS_PEDIDO_TRANSPORTE";
DROP TABLE "STATUS_PEDIDO_WORKUP";
DROP TABLE "STATUS_PENDENCIA";
DROP TABLE "TEMP_SPLIT_VALOR_DNA";
DROP TABLE "TEMP_VALOR_DNA";
DROP TABLE "TEMP_VALOR_DNA_NMDP";
DROP TABLE "TEMP_VALOR_G";
DROP TABLE "TEMP_VALOR_NMDP";
DROP TABLE "TEMP_VALOR_P";
DROP TABLE "TENTATIVA_CONTATO_DOADOR";
DROP TABLE "TIPO_BUSCA_CHECKLIST";
DROP TABLE "TIPO_CHECKLIST";
DROP TABLE "TIPO_EXAME";
DROP TABLE "TIPO_EXAME_LOCUS";
DROP TABLE "TIPO_FORMULARIO";
DROP TABLE "TIPO_LOGRADOURO_CORREIO";
DROP TABLE "TIPO_PEDIDO_LOGISTICA";
DROP TABLE "TIPO_PENDENCIA";
DROP TABLE "TIPO_SERVICO";
DROP TABLE "TIPO_SOLICITACAO";
DROP TABLE "TIPO_TRANSPLANTE";
DROP TABLE "TRANSPORTE_TERRESTRE";
DROP TABLE "TRANSPORTE_TERRESTRE_AUD";
DROP TABLE "TURNO";
DROP TABLE "UF";
DROP TABLE "UNIDADE_FEDERATIVA_CORREIO";
DROP TABLE "USUARIO";
DROP TABLE "USUARIO_BANCO_SANGUE_CORDAO";
DROP TABLE "USUARIO_CENTRO_TRANSPLANTE";
DROP TABLE "USUARIO_PERFIL";
DROP TABLE "VALOR_DNA";
DROP TABLE "VALOR_DNA_NMDP_VALIDO";
DROP TABLE "VALOR_G";
DROP TABLE "VALOR_GENOTIPO_BUSCA_DOADOR";
DROP TABLE "VALOR_GENOTIPO_BUSCA_PACIENTE";
DROP TABLE "VALOR_GENOTIPO_DOADOR";
DROP TABLE "VALOR_GENOTIPO_EXPAND_DOADOR";
DROP TABLE "VALOR_GENOTIPO_EXPAND_PACIENTE";
DROP TABLE "VALOR_GENOTIPO_PACIENTE";
DROP TABLE "VALOR_NMDP";
DROP TABLE "VALOR_P";
DROP TABLE "VALOR_SOROLOGICO";
DROP TABLE "VERSAO_ARQUIVO_BAIXADO";
DROP VIEW "VALOR_DNA_NMDP"; */
--------------------------------------------------------
--  DDL for Sequence SQ_AREV_ID
--------------------------------------------------------

   CREATE SEQUENCE SQ_AREV_ID  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_AREX_ID
--------------------------------------------------------

   CREATE SEQUENCE  SQ_AREX_ID  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_ARPI_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_ARPI_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_ARPR_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_ARPR_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_ARPT_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_ARPT_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_ARRI_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_ARRI_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_ARRW_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_ARRW_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_ARVL_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_ARVL_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_AUDI_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_AUDI_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_AVAL_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_AVAL_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_AVCT_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_AVCT_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_AVNB_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_AVNB_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_AVPC_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_AVPC_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_AVPR_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_AVPR_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_AVRW_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_AVRW_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_AVWD_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_AVWD_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_BASC_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_BASC_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_BUCH_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_BUCH_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_BUPR_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_BUPR_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_BUSC_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_BUSC_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_CABU_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_CABU_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_CETR_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_CETR_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_CETU_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_CETU_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_COJB_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_COJB_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_COMA_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_COMA_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_COTE_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_COTE_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_DIBU_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_DIBU_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_DICC_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_DICC_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_DISP_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_DISP_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_DOAD_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_DOAD_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_DOAD_NR_DMR
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_DOAD_NR_DMR"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_DOIR_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_DOIR_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_EMCO_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_EMCO_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_ENCO_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_ENCO_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_ESIR_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_ESIR_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_EVOL_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_EVOL_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_EXAM_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_EXAM_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_FODO_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_FODO_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_FORM_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_FORM_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_GEBP_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_GEBP_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_GEBR_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_GEBR_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_GEDO_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_GEDO_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_GEEP_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_GEEP_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_GENO_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_GENO_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_GEPA_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_GEPA_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_GEPR_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_GEPR_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_HEEN_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_HEEN_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_HIBU_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_HIBU_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_HISP_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_HISP_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_INCO_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_INCO_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_INPR_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_INPR_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_ITEC_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_ITEC_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_LABO_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_LABO_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_LOEP_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_LOEP_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_LOEV_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_LOEV_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_LOPE_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_LOPE_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_MAPR_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_MAPR_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_MATC_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_MATC_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_MEDI_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_MEDI_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_METO_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_METO_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_NOTI_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_NOTI_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_PACI_NR_RMR
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_PACI_NR_RMR"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 3000000 CACHE 20 ORDER  NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_PAGA_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_PAGA_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_PCEM_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_PCEM_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_PCME_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_PCME_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_PCMT_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_PCMT_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_PEAW_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_PEAW_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_PECL_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_PECL_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_PECO_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_PECO_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_PEEN_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_PEEN_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_PEEX_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_PEEX_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_PEID_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_PEID_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_PELO_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_PELO_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_PEND_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_PEND_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_PERF_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_PERF_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_PETC_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_PETC_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_PETR_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_PETR_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_PEWO_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_PEWO_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_PRCM_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_PRCM_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_PRES_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_PRES_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_PROC_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_PROC_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_QUMA_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_QUMA_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_QUMP_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_QUMP_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_RASC_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_RASC_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_RECO_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_RECO_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_REDI_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_REDI_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_REDO_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_REDO_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_REFD_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_REFD_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_RELA_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_RELA_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_REPA_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_REPA_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_REPE_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_REPE_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER  NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_RESC_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_RESC_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_RESP_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_RESP_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_RETC_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_RETC_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_REWO_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_REWO_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_SOLI_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_SOLI_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_SORE_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_SORE_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_TARE_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_TARE_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_TECD_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_TECD_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_TRTE_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_TRTE_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_USUA_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_USUA_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_VAGB_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_VAGB_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_VAGE_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_VAGE_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_VEAB_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_VEAB_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_VGBD_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_VGBD_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_VGED_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_VGED_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_VGEE_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_VGEE_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_PEEE_ID
--------------------------------------------------------

   CREATE SEQUENCE  "SQ_PEEE_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER  NOCYCLE;
--------------------------------------------------------
--  DDL for Table ARQUIVO_EVOLUCAO
--------------------------------------------------------

CREATE SEQUENCE  "SQ_PEWM_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER  NOCYCLE;
--------------------------------------------------------
--  DDL for Sequence SQ_PEWM_ID
--------------------------------------------------------

CREATE SEQUENCE  "SQ_PEWD_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER  NOCYCLE;

--------------------------------------------------------
--  DDL for Sequence SQ_COCE_ID
--------------------------------------------------------

	CREATE SEQUENCE  "SQ_COCE_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;

--------------------------------------------------------
--  DDL for Sequence SQ_FOPO_ID
--------------------------------------------------------

	CREATE SEQUENCE  "SQ_FOPO_ID"  MINVALUE 1 MAXVALUE 99999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE;

--------------------------------------------------------
--  DDL for Sequence SQ_PEWD_ID
--------------------------------------------------------


  CREATE TABLE "ARQUIVO_EVOLUCAO" 
   (	"AREV_ID" NUMBER, 
	"EVOL_ID" NUMBER, 
	"AREV_TX_CAMINHO_ARQUIVO" VARCHAR2(250)
   ) ;

   COMMENT ON COLUMN "ARQUIVO_EVOLUCAO"."AREV_ID" IS 'Chave primária da tabela';
   COMMENT ON COLUMN "ARQUIVO_EVOLUCAO"."EVOL_ID" IS 'Referência de evolução';
   COMMENT ON COLUMN "ARQUIVO_EVOLUCAO"."AREV_TX_CAMINHO_ARQUIVO" IS 'Caminho do arquivo de evolução';
   COMMENT ON TABLE "ARQUIVO_EVOLUCAO"  IS 'Tabela de relacionamento ente evolução e seus arquivos ';
--------------------------------------------------------
--  DDL for Table ARQUIVO_EXAME
--------------------------------------------------------

  CREATE TABLE "ARQUIVO_EXAME" 
   (	"AREX_TX_CAMINHO_ARQUIVO" VARCHAR2(263), 
	"EXAM_ID" NUMBER, 
	"AREX_ID" NUMBER
   ) ;

   COMMENT ON COLUMN "ARQUIVO_EXAME"."AREX_TX_CAMINHO_ARQUIVO" IS 'caminho dos arquivos do laudo';
   COMMENT ON COLUMN "ARQUIVO_EXAME"."EXAM_ID" IS 'Chave estrangeira da tabela de exame';
   COMMENT ON COLUMN "ARQUIVO_EXAME"."AREX_ID" IS 'Chave primária da tabela';
   COMMENT ON TABLE "ARQUIVO_EXAME"  IS 'Tabela que guarda o caminho dos arquivos de laudo do exame de um paciente';
--------------------------------------------------------
--  DDL for Table ARQUIVO_PEDIDO_IDM
--------------------------------------------------------

  CREATE TABLE "ARQUIVO_PEDIDO_IDM" 
   (	"ARPI_ID" NUMBER, 
	"ARPI_TX_CAMINHO" VARCHAR2(255), 
	"PEID_ID" NUMBER
   ) ;

   COMMENT ON COLUMN "ARQUIVO_PEDIDO_IDM"."ARPI_ID" IS 'Identificador sequencial da tabela.';
   COMMENT ON COLUMN "ARQUIVO_PEDIDO_IDM"."ARPI_TX_CAMINHO" IS 'Caminho doa rquivo no storage.';
   COMMENT ON COLUMN "ARQUIVO_PEDIDO_IDM"."PEID_ID" IS 'Referência para o pedido de idm.';
   COMMENT ON TABLE "ARQUIVO_PEDIDO_IDM"  IS 'Arquivo do laudo do pedido de idm.';
--------------------------------------------------------
--  DDL for Table ARQUIVO_PEDIDO_TRANSPORTE
--------------------------------------------------------

  CREATE TABLE "ARQUIVO_PEDIDO_TRANSPORTE" 
   (	"ARPT_ID" NUMBER, 
	"ARPT_TX_CAMINHO" VARCHAR2(255), 
	"PETR_ID" NUMBER
   ) ;

   COMMENT ON COLUMN "ARQUIVO_PEDIDO_TRANSPORTE"."ARPT_ID" IS 'Identificação do arquivo de pedido de transporte';
   COMMENT ON COLUMN "ARQUIVO_PEDIDO_TRANSPORTE"."ARPT_TX_CAMINHO" IS 'Caminho do arquivo no storage ';
   COMMENT ON COLUMN "ARQUIVO_PEDIDO_TRANSPORTE"."PETR_ID" IS 'Referencia para o pedido de transporte';
   COMMENT ON TABLE "ARQUIVO_PEDIDO_TRANSPORTE"  IS 'Armazenamento de referência de arquivos de pedido de transporte. No momento em que as passagens são emitidas pela C.N.T é necessário que seja gerado um arquivo para que fique com o Courier. Este arquivo será armazenado nesta tabela.';
--------------------------------------------------------
--  DDL for Table ARQUIVO_PRESCRICAO
--------------------------------------------------------

  CREATE TABLE "ARQUIVO_PRESCRICAO" 
   (	"ARPR_ID" NUMBER, 
	"ARPR_TX_CAMINHO" VARCHAR2(263), 
	"PRES_ID" NUMBER, 
	"ARPR_IN_JUSTIFICATIVA" NUMBER(1,0), 
	"ARPR_IN_AUTORIZACAO_PACIENTE" NUMBER(1,0)
   ) ;

   COMMENT ON COLUMN "ARQUIVO_PRESCRICAO"."ARPR_ID" IS 'Identificador do arquivo de prescricao.';
   COMMENT ON COLUMN "ARQUIVO_PRESCRICAO"."ARPR_TX_CAMINHO" IS 'Caminho do arquivo no storage.';
   COMMENT ON COLUMN "ARQUIVO_PRESCRICAO"."PRES_ID" IS 'Identificador da prescrição.';
   COMMENT ON COLUMN "ARQUIVO_PRESCRICAO"."ARPR_IN_JUSTIFICATIVA" IS 'Informa se o arquivo é de justificativa.';
   COMMENT ON COLUMN "ARQUIVO_PRESCRICAO"."ARPR_IN_AUTORIZACAO_PACIENTE" IS 'Informa se o arquivo é de autorização o paciente.';
--------------------------------------------------------
--  DDL for Table ARQUIVO_RELAT_INTERNACIONAL
--------------------------------------------------------

  CREATE TABLE "ARQUIVO_RELAT_INTERNACIONAL" 
   (	"ARRI_ID" NUMBER, 
	"BUSC_ID" NUMBER, 
	"ARRI_TX_DESCRICAO_ARQUIVO" VARCHAR2(255), 
	"ARRI_TX_CAMINHO_ARQUIVO" VARCHAR2(255)
   ) ;

   COMMENT ON COLUMN "ARQUIVO_RELAT_INTERNACIONAL"."ARRI_ID" IS 'Identifica��o do arquivo de relat�rio internacional';
   COMMENT ON COLUMN "ARQUIVO_RELAT_INTERNACIONAL"."BUSC_ID" IS 'Identifica��o da busca';
   COMMENT ON COLUMN "ARQUIVO_RELAT_INTERNACIONAL"."ARRI_TX_DESCRICAO_ARQUIVO" IS 'Descri��o do arquivo';
   COMMENT ON COLUMN "ARQUIVO_RELAT_INTERNACIONAL"."ARRI_TX_CAMINHO_ARQUIVO" IS 'Caminho do arquivo';
   COMMENT ON TABLE "ARQUIVO_RELAT_INTERNACIONAL"  IS 'Tabela para conter caminho dos arquivos de relat�rio de busca internacional para o analista de busca';
--------------------------------------------------------
--  DDL for Table ARQUIVO_RESULTADO_WORKUP
--------------------------------------------------------

  CREATE TABLE "ARQUIVO_RESULTADO_WORKUP" 
   (	"ARRW_ID" NUMBER, 
	"ARRW_TX_CAMINHO" VARCHAR2(263), 
	"ARRW_TX_DESCRICAO" VARCHAR2(50),  
	"REWO_ID" NUMBER
   ) ;

   COMMENT ON COLUMN "ARQUIVO_RESULTADO_WORKUP"."ARRW_ID" IS 'Identificador do arquivo de resultado de workup.';
   COMMENT ON COLUMN "ARQUIVO_RESULTADO_WORKUP"."ARRW_TX_CAMINHO" IS 'Caminho do arquivo no storage.';
   COMMENT ON COLUMN "ARQUIVO_RESULTADO_WORKUP"."ARRW_TX_DESCRICAO" IS 'Descrição sobre o arquivo.';
   COMMENT ON COLUMN "ARQUIVO_RESULTADO_WORKUP"."REWO_ID" IS 'Identificador do resultado de workup.';
   COMMENT ON TABLE "ARQUIVO_RESULTADO_WORKUP"  IS 'Tabela responsável por armazenar os arquivos do resultado de workup.';

--------------------------------------------------------
--  DDL for Table ARQUIVO_VOUCHER_LOGISTICA
--------------------------------------------------------

  CREATE TABLE "ARQUIVO_VOUCHER_LOGISTICA" 
   (	"ARVL_ID" NUMBER, 
	"ARVL_TX_COMENTARIO" VARCHAR2(100), 
	"ARVL_TX_CAMINHO" VARCHAR2(263), 
	"ARVL_IN_EXCLUIDO" NUMBER(1,0) DEFAULT 0, 
	"ARVL_NR_TIPO" NUMBER(1,0), 
	"PELO_ID" NUMBER
   ) ;

   COMMENT ON COLUMN "ARQUIVO_VOUCHER_LOGISTICA"."ARVL_ID" IS 'Identificador do arquivo de voucher.';
   COMMENT ON COLUMN "ARQUIVO_VOUCHER_LOGISTICA"."ARVL_TX_COMENTARIO" IS 'Comentário sobre o voucher.';
   COMMENT ON COLUMN "ARQUIVO_VOUCHER_LOGISTICA"."ARVL_TX_CAMINHO" IS 'Caminho do arquivo no storage.';
   COMMENT ON COLUMN "ARQUIVO_VOUCHER_LOGISTICA"."ARVL_IN_EXCLUIDO" IS 'Indica se o arquivo foi excluido.';
   COMMENT ON COLUMN "ARQUIVO_VOUCHER_LOGISTICA"."ARVL_NR_TIPO" IS 'Indica o tipo do voucher: 1-hospedagem, 2-transporte aereo.';
   COMMENT ON COLUMN "ARQUIVO_VOUCHER_LOGISTICA"."PELO_ID" IS 'Identificador do pedido de logistica.';
   COMMENT ON TABLE "ARQUIVO_VOUCHER_LOGISTICA"  IS 'Tabela responsável por armazenar os dados sobre os vouchers associados a um pedido de logistica.';
--------------------------------------------------------
--  DDL for Table ARQUIVO_VOUCHER_LOGISTICA_AUD
--------------------------------------------------------

  CREATE TABLE "ARQUIVO_VOUCHER_LOGISTICA_AUD" 
   (	"ARVL_ID" NUMBER(19,0), 
	"AUDI_ID" NUMBER(19,0), 
	"AUDI_TX_TIPO" NUMBER(3,0), 
	"ARVL_TX_CAMINHO" VARCHAR2(255 CHAR), 
	"ARVL_TX_COMENTARIO" VARCHAR2(255 CHAR), 
	"ARVL_IN_EXCLUIDO" NUMBER(1,0), 
	"ARVL_NR_TIPO" NUMBER(10,0), 
	"PELO_ID" NUMBER(19,0)
   ) ;

   COMMENT ON TABLE "ARQUIVO_VOUCHER_LOGISTICA_AUD"  IS 'Tabela de auditoria da tabela ARQUIVO_VOUCHER_LOGISTICA.';
--------------------------------------------------------
--  DDL for Table ASSOCIA_RESPOSTA_PENDENCIA
--------------------------------------------------------

  CREATE TABLE "ASSOCIA_RESPOSTA_PENDENCIA" 
   (	"PEND_ID" NUMBER, 
	"REPE_ID" NUMBER
   ) ;

   COMMENT ON COLUMN "ASSOCIA_RESPOSTA_PENDENCIA"."PEND_ID" IS 'Pk e Fk da tabela de pendencia';
   COMMENT ON COLUMN "ASSOCIA_RESPOSTA_PENDENCIA"."REPE_ID" IS 'Pk e Fk da tabela de resposta';
   COMMENT ON TABLE "ASSOCIA_RESPOSTA_PENDENCIA"  IS 'Tabela de relacionamento entre pendencias e suas respostas.';
--------------------------------------------------------
--  DDL for Table AUDITORIA
--------------------------------------------------------

  CREATE TABLE "AUDITORIA" 
   (	"AUDI_ID" NUMBER, 
	"AUDI_DT_DATA" TIMESTAMP (6), 
	"AUDI_TX_USUARIO" VARCHAR2(150)
   ) ;

   COMMENT ON COLUMN "AUDITORIA"."AUDI_ID" IS 'Identificador da auditoria';
   COMMENT ON COLUMN "AUDITORIA"."AUDI_DT_DATA" IS 'Data em que a ação foi efetuada.';
   COMMENT ON COLUMN "AUDITORIA"."AUDI_TX_USUARIO" IS 'Usuário que efetuou a ação.';
   COMMENT ON TABLE "AUDITORIA"  IS 'Tabela que armazena dados de auditoria no sistema.';
--------------------------------------------------------
--  DDL for Table AVALIACAO
--------------------------------------------------------

  CREATE TABLE "AVALIACAO" 
   (	"AVAL_ID" NUMBER, 
	"PACI_NR_RMR" NUMBER, 
	"AVAL_DT_CRIACAO" TIMESTAMP (6), 
	"AVAL_IN_RESULTADO" NUMBER(1,0), 
	"MEDI_ID_AVALIADOR" NUMBER, 
	"AVAL_TX_OBSERVACAO" VARCHAR2(200), 
	"AVAL_DT_RESULTADO" TIMESTAMP (6), 
	"CETR_ID" NUMBER, 
	"AVAL_IN_STATUS" NUMBER(1,0), 
	"AVAL_TX_MOTIVO_CANCELAMENTO" VARCHAR2(30)
   ) ;

   COMMENT ON COLUMN "AVALIACAO"."AVAL_ID" IS 'Chave primária da tabela de avaliação';
   COMMENT ON COLUMN "AVALIACAO"."PACI_NR_RMR" IS 'Chave estrangeira da tabela de paciente';
   COMMENT ON COLUMN "AVALIACAO"."AVAL_DT_CRIACAO" IS 'Data de criação da avaliação';
   COMMENT ON COLUMN "AVALIACAO"."AVAL_IN_RESULTADO" IS 'Restultado da avaliação 0 - reprovado 1 -  aprovado';
   COMMENT ON COLUMN "AVALIACAO"."MEDI_ID_AVALIADOR" IS 'Chave estrangeira da tabelade médico';
   COMMENT ON COLUMN "AVALIACAO"."AVAL_TX_OBSERVACAO" IS 'Observação ao encerrar uma avaliação';
   COMMENT ON COLUMN "AVALIACAO"."AVAL_DT_RESULTADO" IS 'Data do resultado da avaliação';
   COMMENT ON COLUMN "AVALIACAO"."CETR_ID" IS 'Chave estrangeira para tabela de centro avaliador';
   COMMENT ON COLUMN "AVALIACAO"."AVAL_IN_STATUS" IS 'Status da avaliação 0 - Finalizado 1 - Aberto 2 - Cancelada por busca';
   COMMENT ON COLUMN "AVALIACAO"."AVAL_TX_MOTIVO_CANCELAMENTO" IS 'Descrição com o motivo do cancelamento da avaliação.';
   COMMENT ON TABLE "AVALIACAO"  IS 'Tabela de avaliação do paciente para liberação para busca';
--------------------------------------------------------
--  DDL for Table AVALIACAO_AUD
--------------------------------------------------------

  CREATE TABLE "AVALIACAO_AUD" 
   (	"AVAL_ID" NUMBER, 
	"AUDI_ID" NUMBER, 
	"AUDI_TX_TIPO" NUMBER(3,0), 
	"AVAL_IN_STATUS" NUMBER(1,0), 
	"AVAL_IN_RESULTADO" NUMBER(1,0), 
	"AVAL_DT_CRIACAO" TIMESTAMP (6), 
	"AVAL_DT_RESULTADO" TIMESTAMP (6), 
	"AVAL_TX_OBSERVACAO" VARCHAR2(255 CHAR), 
	"CETR_ID" NUMBER, 
	"MEDI_ID_AVALIADOR" NUMBER, 
	"PACI_NR_RMR" NUMBER, 
	"AVAL_TX_MOTIVO_CANCELAMENTO" VARCHAR2(30)
   ) ;

   COMMENT ON TABLE "AVALIACAO_AUD"  IS 'Tabela de auditoria de avaliacao';
--------------------------------------------------------
--  DDL for Table AVALIACAO_CAMARA_TECNICA
--------------------------------------------------------

  CREATE TABLE "AVALIACAO_CAMARA_TECNICA" 
   (	"AVCT_ID" NUMBER, 
	"AVCT_TX_JUSTIFICATIVA" VARCHAR2(255), 
	"PACI_NR_RMR" NUMBER, 
	"STCT_ID" NUMBER, 
	"AVCT_TX_ARQUIVO_RELATORIO" VARCHAR2(255), 
	"AVCT_DT_ATUALIZACAO" TIMESTAMP (6)
   ) ;

   COMMENT ON COLUMN "AVALIACAO_CAMARA_TECNICA"."AVCT_ID" IS 'Chave primária de avaliação de câmera técnica';
   COMMENT ON COLUMN "AVALIACAO_CAMARA_TECNICA"."AVCT_TX_JUSTIFICATIVA" IS 'Texto de justificativa para o resultado da avaliação';
   COMMENT ON COLUMN "AVALIACAO_CAMARA_TECNICA"."PACI_NR_RMR" IS 'Identificação do paciente que será avaliado';
   COMMENT ON COLUMN "AVALIACAO_CAMARA_TECNICA"."STCT_ID" IS 'Status da avaliação';
   COMMENT ON COLUMN "AVALIACAO_CAMARA_TECNICA"."AVCT_TX_ARQUIVO_RELATORIO" IS 'Campo para descrição do caminho do arquivo a ser persistido
';
   COMMENT ON COLUMN "AVALIACAO_CAMARA_TECNICA"."AVCT_DT_ATUALIZACAO" IS 'Campo de data atulização da avaliação';
   COMMENT ON TABLE "AVALIACAO_CAMARA_TECNICA"  IS 'Tabela de armazenamento de avaliações de um grupo do REDOME chamado câmera técnica. Este grupo avalia pacientes com diagnósticos com CID''s fora do contemplamento da portaria ou com idade fora do range de atendimento.';
--------------------------------------------------------
--  DDL for Table AVALIACAO_NOVA_BUSCA
--------------------------------------------------------

  CREATE TABLE "AVALIACAO_NOVA_BUSCA" 
   (	"AVNB_ID" NUMBER, 
	"PACI_NR_RMR" NUMBER, 
	"AVNB_TX_STATUS" VARCHAR2(30), 
	"USUA_ID_AVALIADOR" NUMBER, 
	"AVNB_DT_AVALIADO" TIMESTAMP (6), 
	"AVNB_TX_JUSTIFICATIVA" VARCHAR2(200), 
	"AVNB_DT_CRIACAO" TIMESTAMP (6)
   ) ;

   COMMENT ON COLUMN "AVALIACAO_NOVA_BUSCA"."AVNB_ID" IS 'Identificador auto sequencial da tabela.';
   COMMENT ON COLUMN "AVALIACAO_NOVA_BUSCA"."PACI_NR_RMR" IS 'Identificador do paciente que deverá ser avaliado pela nova busca.';
   COMMENT ON COLUMN "AVALIACAO_NOVA_BUSCA"."AVNB_TX_STATUS" IS 'Status da nova busca (AGUARDANDO_AVALIACAO, APROVADA, REPROVADA).';
   COMMENT ON COLUMN "AVALIACAO_NOVA_BUSCA"."USUA_ID_AVALIADOR" IS 'Após ocorrida a avaliação, guarda quem foi o usuário que realizou.';
   COMMENT ON COLUMN "AVALIACAO_NOVA_BUSCA"."AVNB_DT_AVALIADO" IS 'Após ocorrida a avaliação, guarda a data que foi realizada.';
   COMMENT ON COLUMN "AVALIACAO_NOVA_BUSCA"."AVNB_TX_JUSTIFICATIVA" IS 'Após ocorrida a reprovação, guarda a justificativa.';
   COMMENT ON TABLE "AVALIACAO_NOVA_BUSCA"  IS 'Armazena informações relativas a avaliação para nova busca do paciente.';
--------------------------------------------------------
--  DDL for Table AVALIACAO_PEDIDO_COLETA
--------------------------------------------------------

  CREATE TABLE "AVALIACAO_PEDIDO_COLETA" 
   (	"AVPC_ID" NUMBER, 
	"AVPC_DT_CRIACAO" TIMESTAMP (6), 
	"USUA_ID" NUMBER, 
	"AVPC_IN_PEDIDO_PROSSEGUE" NUMBER(1,0), 
	"AVPC_TX_JUSTIFICATIVA" VARCHAR2(500) 
   ) ;

   COMMENT ON COLUMN "AVALIACAO_PEDIDO_COLETA"."AVPC_ID" IS 'Identificador da avaliação do pedido de coleta.';
   COMMENT ON COLUMN "AVALIACAO_PEDIDO_COLETA"."AVPC_DT_CRIACAO" IS 'Data de criação da avaliação.';
   COMMENT ON COLUMN "AVALIACAO_PEDIDO_COLETA"."USUA_ID" IS 'Usuário que efetuou a avaliação.';
   COMMENT ON COLUMN "AVALIACAO_PEDIDO_COLETA"."AVPC_IN_PEDIDO_PROSSEGUE" IS 'Indica se o pedido de coleta deve prosseguir.';
   COMMENT ON COLUMN "AVALIACAO_PEDIDO_COLETA"."AVPC_TX_JUSTIFICATIVA" IS 'Justificativa do avaliador.';
   COMMENT ON TABLE "AVALIACAO_PEDIDO_COLETA"  IS 'Tabela responsável por armazenar avaliações aos pedidos de coleta.';
--------------------------------------------------------
--  DDL for Table AVALIACAO_PRESCRICAO
--------------------------------------------------------

  CREATE TABLE "AVALIACAO_PRESCRICAO" 
   (	"AVPR_ID" NUMBER, 
	"FOCE_ID" NUMBER, 
	"AVPR_TX_JUSTIFICATIVA_DESCARTE" VARCHAR2(100), 
	"AVPR_TX_JUSTIFICATIVA_CANCEL" VARCHAR2(100), 
	"AVPR_DT_CRICAO" DATE, 
	"USUA_ID" NUMBER, 
	"AVPR_DT_ATUALIZACAO" DATE, 
	"PRES_ID" NUMBER, 
	"AVPR_IN_RESULTADO" NUMBER(1,0)
   ) ;

   COMMENT ON COLUMN "AVALIACAO_PRESCRICAO"."AVPR_ID" IS 'Chave primária da avaliação da prescrição.';
   COMMENT ON COLUMN "AVALIACAO_PRESCRICAO"."FOCE_ID" IS 'Fonte celula descartada.';
   COMMENT ON COLUMN "AVALIACAO_PRESCRICAO"."AVPR_TX_JUSTIFICATIVA_DESCARTE" IS 'Justificativa  do descarte de uma fonte';
   COMMENT ON COLUMN "AVALIACAO_PRESCRICAO"."AVPR_TX_JUSTIFICATIVA_CANCEL" IS 'Justificativa do cancelamento da prescrição';
   COMMENT ON COLUMN "AVALIACAO_PRESCRICAO"."AVPR_DT_CRICAO" IS 'Data de criação da avaliação';
   COMMENT ON COLUMN "AVALIACAO_PRESCRICAO"."USUA_ID" IS 'Usuario responsável pela avaliação da prescrição.';
   COMMENT ON COLUMN "AVALIACAO_PRESCRICAO"."AVPR_DT_ATUALIZACAO" IS 'Data de atualização da avaliação';
   COMMENT ON COLUMN "AVALIACAO_PRESCRICAO"."PRES_ID" IS 'Chave estrangeira para tabela PRESCRICAO.';
   COMMENT ON COLUMN "AVALIACAO_PRESCRICAO"."AVPR_IN_RESULTADO" IS '0 - Reprovado e 1 - para aprovado';
   COMMENT ON TABLE "AVALIACAO_PRESCRICAO"  IS 'Tabela para avaliar uma prescrição.';
--------------------------------------------------------
--  DDL for Table AVALIACAO_RESULTADO_WORKUP
--------------------------------------------------------

  CREATE TABLE "AVALIACAO_RESULTADO_WORKUP" 
   (	"AVRW_ID" NUMBER, 
	"AVRW_DT_CRIACAO" TIMESTAMP (6), 
	"AVRW_DT_ATUALIZACAO" TIMESTAMP (6), 
	"USUA_ID_RESPONSAVEL" NUMBER, 
	"AVRW_IN_PROSSEGUIR" NUMBER(1,0), 
	"AVRW_TX_JUSTIFICATIVA" VARCHAR2(500), 
	"REWO_ID" NUMBER
   ) ;

   COMMENT ON COLUMN "AVALIACAO_RESULTADO_WORKUP"."AVRW_ID" IS 'Identificador da avaliação do resultado.';
   COMMENT ON COLUMN "AVALIACAO_RESULTADO_WORKUP"."AVRW_DT_CRIACAO" IS 'Data de criação da avaliação.';
   COMMENT ON COLUMN "AVALIACAO_RESULTADO_WORKUP"."AVRW_DT_ATUALIZACAO" IS 'Data de atualização.';
   COMMENT ON COLUMN "AVALIACAO_RESULTADO_WORKUP"."USUA_ID_RESPONSAVEL" IS 'Usuário responsável pela avaliação.';
   COMMENT ON COLUMN "AVALIACAO_RESULTADO_WORKUP"."AVRW_IN_PROSSEGUIR" IS 'Indica se a coleta foi solicitada baseada no resultado do workup.';
   COMMENT ON COLUMN "AVALIACAO_RESULTADO_WORKUP"."AVRW_TX_JUSTIFICATIVA" IS 'Justificativa para solicitação ou descarte de coleta.';
   COMMENT ON COLUMN "AVALIACAO_RESULTADO_WORKUP"."REWO_ID" IS 'Identificador do pedido de workup.';
   COMMENT ON TABLE "AVALIACAO_RESULTADO_WORKUP"  IS 'Tabela responsável por armazenar a avaliação de um resultado de workup.';
--------------------------------------------------------
--  DDL for Table AVALIACAO_WORKUP_DOADOR
--------------------------------------------------------

  CREATE TABLE "AVALIACAO_WORKUP_DOADOR" 
   (	"AVWD_ID" NUMBER, 
	"REWO_ID" NUMBER, 
	"AVWD_DT_CRIACAO" TIMESTAMP (6), 
	"AVWD_DT_CONCLUSAO" TIMESTAMP (6), 
	"USUA_ID_RESPONSAVEL" NUMBER, 
	"AVWD_NR_RESULTADO" NUMBER(1,0)
   ) ;

   COMMENT ON COLUMN "AVALIACAO_WORKUP_DOADOR"."AVWD_ID" IS 'Identificador da avaliação de workup do doador.';
   COMMENT ON COLUMN "AVALIACAO_WORKUP_DOADOR"."REWO_ID" IS 'Identificador do resultado de workup do doador.';
   COMMENT ON COLUMN "AVALIACAO_WORKUP_DOADOR"."AVWD_DT_CRIACAO" IS 'Data de criação da avaliação.';
   COMMENT ON COLUMN "AVALIACAO_WORKUP_DOADOR"."AVWD_DT_CONCLUSAO" IS 'Data de conclusao.';
   COMMENT ON COLUMN "AVALIACAO_WORKUP_DOADOR"."USUA_ID_RESPONSAVEL" IS 'Usuário responsável que efetuou a avaliação.';
   COMMENT ON COLUMN "AVALIACAO_WORKUP_DOADOR"."AVWD_NR_RESULTADO" IS 'Indica o resultado da avaliação. 0-Nada, 1-Inativou doador, 2-Alterou ressalvas.';
   COMMENT ON TABLE "AVALIACAO_WORKUP_DOADOR"  IS 'Tabela responsável por armazenar as avaliações de workup dos doadores.';
--------------------------------------------------------
--  DDL for Table BAIRRO_CORREIO
--------------------------------------------------------

  CREATE TABLE "BAIRRO_CORREIO" 
   (	"BACO_ID" NUMBER, 
	"BACO_TX_NOME" VARCHAR2(72), 
	"LOCC_ID" NUMBER, 
	"BACO_TX_DNE" VARCHAR2(8)
   ) ;

   COMMENT ON COLUMN "BAIRRO_CORREIO"."BACO_ID" IS 'Identificador do registro';
   COMMENT ON COLUMN "BAIRRO_CORREIO"."BACO_TX_NOME" IS 'Nome Oficial do Bairro';
   COMMENT ON COLUMN "BAIRRO_CORREIO"."LOCC_ID" IS 'ID da Tabela da Localidade';
   COMMENT ON COLUMN "BAIRRO_CORREIO"."BACO_TX_DNE" IS 'Chave do Bairro no DNE';
   COMMENT ON TABLE "BAIRRO_CORREIO"  IS 'Bairro por Localidade';
--------------------------------------------------------
--  DDL for Table BANCO_SANGUE_CORDAO
--------------------------------------------------------

  CREATE TABLE "BANCO_SANGUE_CORDAO" 
   (	"BASC_ID" NUMBER, 
	"BASC_TX_SIGLA" VARCHAR2(4), 
	"BASC_TX_NOME" VARCHAR2(60), 
	"BASC_TX_ENDERECO" VARCHAR2(1000), 
	"BASC_TX_CONTATO" VARCHAR2(1000), 
	"BASC_NR_ID_BRASILCORD" NUMBER
   ) ;

   COMMENT ON COLUMN "BANCO_SANGUE_CORDAO"."BASC_ID" IS 'Identificador único sequencial da tabela.';
   COMMENT ON COLUMN "BANCO_SANGUE_CORDAO"."BASC_TX_SIGLA" IS 'Sigla do banco de cordão. Será usuado para compor a identificação do cordão.';
   COMMENT ON COLUMN "BANCO_SANGUE_CORDAO"."BASC_TX_NOME" IS 'Nome do bacno de cordão.';
   COMMENT ON COLUMN "BANCO_SANGUE_CORDAO"."BASC_NR_ID_BRASILCORD" IS 'Id do banco no sistema brasilcord';
   COMMENT ON TABLE "BANCO_SANGUE_CORDAO"  IS 'Referente aos Bancos de Sangue de Cordão Umbilical.';
--------------------------------------------------------
--  DDL for Table BUSCA
--------------------------------------------------------

  CREATE TABLE "BUSCA" 
   (	"BUSC_ID" NUMBER, 
	"PACI_NR_RMR" NUMBER, 
	"USUA_ID" NUMBER, 
	"STBU_ID" NUMBER, 
	"PACI_IN_ACEITA_MISMATCH" NUMBER(1,0), 
	"CETR_ID_CENTRO_TRANSPLANTE" NUMBER,
	"BUSC_DT_ANALISE" TIMESTAMP(6) 	
   ) ;

   COMMENT ON COLUMN "BUSCA"."BUSC_ID" IS 'IDENTIFICAÇÃO DA BUSCA';
   COMMENT ON COLUMN "BUSCA"."PACI_NR_RMR" IS 'PACIENTE DO PROCESSO DE BUSCA';
   COMMENT ON COLUMN "BUSCA"."USUA_ID" IS 'ANALISTA RESPONSÁVEL PELA BUSCA';
   COMMENT ON COLUMN "BUSCA"."STBU_ID" IS 'STATUS DA BUSCA ';
   COMMENT ON COLUMN "BUSCA"."PACI_IN_ACEITA_MISMATCH" IS 'Define se o médico aceita pelo menos 1 mismatch ex. 5x6';
   COMMENT ON COLUMN "BUSCA"."CETR_ID_CENTRO_TRANSPLANTE" IS 'Centro de transplante confirmado para o paciente (FK).';
--------------------------------------------------------
--  DDL for Table BUSCA_AUD
--------------------------------------------------------

  CREATE TABLE "BUSCA_AUD" 
   (	"BUSC_ID" NUMBER, 
	"AUDI_ID" NUMBER, 
	"AUDI_TX_TIPO" NUMBER, 
	"PACI_NR_RMR" NUMBER, 
	"STBU_ID" NUMBER, 
	"USUA_ID" NUMBER, 
	"CETR_ID_CENTRO_COLETA" NUMBER, 
	"PACI_IN_ACEITA_MISMATCH" NUMBER(1,0), 
	"CETR_ID_CENTRO_TRANSPLANTE" NUMBER
   ) ;

   COMMENT ON COLUMN "BUSCA_AUD"."BUSC_ID" IS 'IDENTIFICAÇÃO DA AUTITORIA DE BUSCA';
   COMMENT ON COLUMN "BUSCA_AUD"."AUDI_ID" IS 'REFERÊNCIA PARA A TABELA DE AUDITORIA';
   COMMENT ON COLUMN "BUSCA_AUD"."AUDI_TX_TIPO" IS 'TIPO DE AUTITORIA';
   COMMENT ON COLUMN "BUSCA_AUD"."PACI_NR_RMR" IS 'REFERÊNCIA AO PACIENTE';
   COMMENT ON COLUMN "BUSCA_AUD"."STBU_ID" IS 'STATUS DA BUSCA';
   COMMENT ON COLUMN "BUSCA_AUD"."USUA_ID" IS 'REFERÊNCIA DO USUARIO RESPONSAVÉL PELA BUSCA';
   COMMENT ON COLUMN "BUSCA_AUD"."CETR_ID_CENTRO_COLETA" IS 'Chave estrangeira do centro de transplante.';
--------------------------------------------------------
--  DDL for Table BUSCA_CHECKLIST
--------------------------------------------------------

  CREATE TABLE "BUSCA_CHECKLIST" 
   (	"BUCH_ID" NUMBER, 
	"BUCH_DT_CRIACAO" TIMESTAMP (6), 
	"BUCH_DT_VISTO" TIMESTAMP (6), 
	"BUCH_IN_VISTO" NUMBER, 
	"TIBC_ID" NUMBER, 
	"USUA_ID" NUMBER, 
	"BUSC_ID" NUMBER, 
	"BUCH_NR_AGE" NUMBER, 
	"MATC_ID" NUMBER
   ) ;

   COMMENT ON COLUMN "BUSCA_CHECKLIST"."BUCH_ID" IS 'Chave primaria de identificacao de busca checklist';
   COMMENT ON COLUMN "BUSCA_CHECKLIST"."BUCH_DT_CRIACAO" IS 'Data e hora da atualização de checklist';
   COMMENT ON COLUMN "BUSCA_CHECKLIST"."BUCH_DT_VISTO" IS 'Flag para marcar se o item foi visto pelo analista';
   COMMENT ON COLUMN "BUSCA_CHECKLIST"."BUCH_IN_VISTO" IS 'Indica se o checklis foi ou não visualizado';
   COMMENT ON COLUMN "BUSCA_CHECKLIST"."TIBC_ID" IS 'Tipo de checklist
';
   COMMENT ON COLUMN "BUSCA_CHECKLIST"."USUA_ID" IS 'Usuário que deu o visto no checklist';
   COMMENT ON COLUMN "BUSCA_CHECKLIST"."BUSC_ID" IS 'Identificação da busca do checklist';
   COMMENT ON COLUMN "BUSCA_CHECKLIST"."BUCH_NR_AGE" IS 'Quantidade de dias em que foi realizada a tarefa de busca';
   COMMENT ON COLUMN "BUSCA_CHECKLIST"."MATC_ID" IS 'Referencia de match ';
   COMMENT ON TABLE "BUSCA_CHECKLIST"  IS 'Tabela de checklist para busca.';
--------------------------------------------------------
--  DDL for Table BUSCA_PRELIMINAR
--------------------------------------------------------

  CREATE TABLE "BUSCA_PRELIMINAR" 
   (	"BUPR_ID" NUMBER, 
	"BUPR_TX_NOME_PACIENTE" VARCHAR2(255), 
	"BUPR_DT_NASCIMENTO" DATE, 
	"BUPR_TX_ABO" VARCHAR2(3), 
	"BUPR_VL_PESO" NUMBER(4,1), 
	"BUPR_DT_CADASTRO" TIMESTAMP (6), 
	"USUA_ID" NUMBER
   ) ;

   COMMENT ON COLUMN "BUSCA_PRELIMINAR"."BUPR_ID" IS 'Sequencial identificador da tabela busca preliminar.';
   COMMENT ON COLUMN "BUSCA_PRELIMINAR"."BUPR_TX_NOME_PACIENTE" IS 'Nome do paciente.';
   COMMENT ON COLUMN "BUSCA_PRELIMINAR"."BUPR_DT_NASCIMENTO" IS 'Data de nascimento do paciente.';
   COMMENT ON COLUMN "BUSCA_PRELIMINAR"."BUPR_TX_ABO" IS 'ABO do paciente.';
   COMMENT ON COLUMN "BUSCA_PRELIMINAR"."BUPR_VL_PESO" IS 'Peso do paciente.';
   COMMENT ON COLUMN "BUSCA_PRELIMINAR"."BUPR_DT_CADASTRO" IS 'Data em que foi realizada a busca (cadastro do registro).';
   COMMENT ON COLUMN "BUSCA_PRELIMINAR"."USUA_ID" IS 'Usuário que realizou a busca (cadastro do registro).';
   COMMENT ON TABLE "BUSCA_PRELIMINAR"  IS 'Tabela de busca preliminar realizada quando um médico deseja fazer uma consulta prévia no Redome, antes do cadastro definitivo.';
--------------------------------------------------------
--  DDL for Table CANCELAMENTO_BUSCA
--------------------------------------------------------

  CREATE TABLE "CANCELAMENTO_BUSCA" 
   (	"CABU_ID" NUMBER, 
	"CABU_DT_EVENTO" TIMESTAMP (6), 
	"CABU_TX_ESPECIFIQUE" VARCHAR2(255), 
	"BUSC_ID" NUMBER, 
	"MOCB_ID" NUMBER, 
	"USUA_ID" NUMBER, 
	"CABU_DT_CRIACAO" TIMESTAMP (6)
   ) ;

   COMMENT ON COLUMN "CANCELAMENTO_BUSCA"."CABU_ID" IS 'Chave primária da tabela de cancelamento da busca do paciente';
   COMMENT ON COLUMN "CANCELAMENTO_BUSCA"."CABU_DT_EVENTO" IS 'Data do evento referente ao cancelamento';
   COMMENT ON COLUMN "CANCELAMENTO_BUSCA"."CABU_TX_ESPECIFIQUE" IS 'Campo que armazena uma justificativa ao cancelamento';
   COMMENT ON COLUMN "CANCELAMENTO_BUSCA"."BUSC_ID" IS 'Chave estrangeira da tabela de busca.';
   COMMENT ON COLUMN "CANCELAMENTO_BUSCA"."MOCB_ID" IS 'Chave estrangeira da tabela de motivo';
   COMMENT ON COLUMN "CANCELAMENTO_BUSCA"."USUA_ID" IS 'Chave estrangeira do usuario responsavel pelo cancelamento da busca';
   COMMENT ON COLUMN "CANCELAMENTO_BUSCA"."CABU_DT_CRIACAO" IS 'Data de cancelamento da busca.';
   COMMENT ON TABLE "CANCELAMENTO_BUSCA"  IS 'Tabela de cancelamento de busca de um paciente';
--------------------------------------------------------
--  DDL for Table CATEGORIA_CHECKLIST
--------------------------------------------------------

  CREATE TABLE "CATEGORIA_CHECKLIST" 
   (	"CACH_ID" NUMBER, 
	"CACH_TX_NM_CATEGORIA" VARCHAR2(80), 
	"TIPC_ID" NUMBER
   ) ;
--------------------------------------------------------
--  DDL for Table CATEGORIA_NOTIFICACAO
--------------------------------------------------------

  CREATE TABLE "CATEGORIA_NOTIFICACAO" 
   (	"CANO_ID" NUMBER(1,0), 
	"CANO_TX_DESCRICAO" VARCHAR2(100)
   ) ;

   COMMENT ON COLUMN "CATEGORIA_NOTIFICACAO"."CANO_ID" IS 'Chave primária para categoria de notificação';
   COMMENT ON COLUMN "CATEGORIA_NOTIFICACAO"."CANO_TX_DESCRICAO" IS 'Descrição da categoria de notificação';
   COMMENT ON TABLE "CATEGORIA_NOTIFICACAO"  IS 'Tabela que armazena as categorias de notificações do sistema';
--------------------------------------------------------
--  DDL for Table CENTRO_TRANSPLANTE
--------------------------------------------------------

  CREATE TABLE "CENTRO_TRANSPLANTE" 
   (	"CETR_ID" NUMBER, 
	"CETR_TX_NOME" VARCHAR2(100), 
	"CETR_TX_CNPJ" VARCHAR2(14), 
	"CETR_TX_CNES" VARCHAR2(10), 
	"CETR_NR_ENTITY_STATUS" NUMBER DEFAULT 1, 
	"LABO_ID" NUMBER
   ) ;

   COMMENT ON COLUMN "CENTRO_TRANSPLANTE"."CETR_ID" IS 'Chave primária da tabela.';
   COMMENT ON COLUMN "CENTRO_TRANSPLANTE"."CETR_TX_NOME" IS 'Nome do centro de transplante.';
   COMMENT ON COLUMN "CENTRO_TRANSPLANTE"."CETR_TX_CNPJ" IS 'CNPJ do centro de transplante.';
   COMMENT ON COLUMN "CENTRO_TRANSPLANTE"."CETR_TX_CNES" IS 'CNES ao qual está inserido o centro de transplante. (Cadastro Nacional de Estabelecimentos de Saúde).';
   COMMENT ON COLUMN "CENTRO_TRANSPLANTE"."CETR_NR_ENTITY_STATUS" IS 'Estado lógico da entidade do sitema';
   COMMENT ON COLUMN "CENTRO_TRANSPLANTE"."LABO_ID" IS 'Identificador do laboratório de preferência deste centro.';
   COMMENT ON TABLE "CENTRO_TRANSPLANTE"  IS 'Tabela de Centros de Transplantes/Avaliadores';
--------------------------------------------------------
--  DDL for Table CENTRO_TRANSPLANTE_USUARIO
--------------------------------------------------------

  CREATE TABLE "CENTRO_TRANSPLANTE_USUARIO" 
   (	"USUA_ID" NUMBER, 
	"CETR_ID" NUMBER, 
	"CETU_IN_RESPONSAVEL" NUMBER DEFAULT 0, 
	"CETU_ID" NUMBER
   ) ;

   COMMENT ON COLUMN "CENTRO_TRANSPLANTE_USUARIO"."USUA_ID" IS 'Identificação do usuário ';
   COMMENT ON COLUMN "CENTRO_TRANSPLANTE_USUARIO"."CETR_ID" IS 'Identificação do centro de transplante ';
   COMMENT ON COLUMN "CENTRO_TRANSPLANTE_USUARIO"."CETU_IN_RESPONSAVEL" IS 'Se o usuario é responsável por um centro ou não';
   COMMENT ON COLUMN "CENTRO_TRANSPLANTE_USUARIO"."CETU_ID" IS 'Chave primária para centro transplante usuario';
   COMMENT ON TABLE "CENTRO_TRANSPLANTE_USUARIO"  IS 'Tabela de relacionamento entre usuário e centro de transplante para fins de organização de tarefas dos usuários do REDOME.';

--------------------------------------------------------
--  DDL for Table ESTADO_CIVIL
--------------------------------------------------------
CREATE TABLE ESTADO_CIVIL(
    ESCI_ID NUMBER NOT NULL ENABLE, 
    ESCI_TX_NOME VARCHAR2(50) NOT NULL
);
	
COMMENT ON COLUMN "ESTADO_CIVIL"."ESCI_ID" IS 'Identificador do estado civil';
COMMENT ON COLUMN "ESTADO_CIVIL"."ESCI_TX_NOME" IS 'Nome do estado civil';
COMMENT ON TABLE "ESTADO_CIVIL"  IS 'Estado Civil';   
--------------------------------------------------------
--  DDL for Table CID
--------------------------------------------------------

  CREATE TABLE "CID" 
   (	"CID_ID" NUMBER, 
	"CID_TX_CODIGO" VARCHAR2(40), 
	"CID_IN_TRANSPLANTE" NUMBER(1,0) DEFAULT 0, 
	"CID_NR_CURABILIDADE" NUMBER, 
	"CID_NR_Q_CONSTANTE" NUMBER, 
	"CID_NR_URGENCIA" NUMBER, 
	"CID_NR_IDADE_MINIMA" NUMBER, 
	"CID_NR_IDADE_MAXIMA" NUMBER,
	"CID_TX_SIGLA_CRIR" VARCHAR2(10)
   ) ;

   COMMENT ON COLUMN "CID"."CID_ID" IS 'Chave primária do CID';
   COMMENT ON COLUMN "CID"."CID_TX_CODIGO" IS 'Código do CID';
   COMMENT ON COLUMN "CID"."CID_IN_TRANSPLANTE" IS 'Indica se o CID é contemplado para transplante';
   COMMENT ON COLUMN "CID"."CID_NR_CURABILIDADE" IS 'Valor de curabilidade para ser usado no cálculo de score';
   COMMENT ON COLUMN "CID"."CID_NR_Q_CONSTANTE" IS 'Valor q para ser usado no cálculo de score';
   COMMENT ON COLUMN "CID"."CID_NR_URGENCIA" IS 'Valor de urgencia para ser usado no calculo de score';
   COMMENT ON COLUMN "CID"."CID_NR_IDADE_MINIMA" IS 'Valor da idade minima para atendimento desta CID';
   COMMENT ON COLUMN "CID"."CID_NR_IDADE_MAXIMA" IS 'Valor da idade maxima para atendimento desta CID';
   COMMENT ON COLUMN "CID"."CID_TX_SIGLA_CRIR" IS 'Sigla de identificação da CID no EMDIS.';
   COMMENT ON TABLE "CID"  IS 'Tabela de CIDS - Código Internacional de Doenças';
--------------------------------------------------------
--  DDL for Table CID_ESTAGIO_DOENCA
--------------------------------------------------------

  CREATE TABLE "CID_ESTAGIO_DOENCA" 
   (	"CID_ID" NUMBER, 
	"ESDO_ID" NUMBER, 
	"CIED_NR_CURABILIDADE" NUMBER, 
	"CIED_NR_Q_CONSTANTE" NUMBER, 
	"CIED_NR_URGENCIA" NUMBER
   ) ;

   COMMENT ON COLUMN "CID_ESTAGIO_DOENCA"."CID_ID" IS 'Identificador único de CID.';
   COMMENT ON COLUMN "CID_ESTAGIO_DOENCA"."ESDO_ID" IS 'Identificador único de estágio da doença.';
   COMMENT ON COLUMN "CID_ESTAGIO_DOENCA"."CIED_NR_CURABILIDADE" IS 'Valor de curabilidade para ser usado no cálculo de score';
   COMMENT ON COLUMN "CID_ESTAGIO_DOENCA"."CIED_NR_Q_CONSTANTE" IS 'Valor q para ser usado no cálculo de score';
   COMMENT ON COLUMN "CID_ESTAGIO_DOENCA"."CIED_NR_URGENCIA" IS 'Valor de urgencia para ser usado no calculo de score';
   COMMENT ON TABLE "CID_ESTAGIO_DOENCA"  IS 'Tabela de relacionamento entre CIDs e Estágios de doenças.';
--------------------------------------------------------
--  DDL for Table CID_IDIOMA
--------------------------------------------------------

  CREATE TABLE "CID_IDIOMA" 
   (	"CID_ID" NUMBER, 
	"IDIO_ID" NUMBER, 
	"CIID_TX_DESCRICAO" VARCHAR2(300)
   ) ;

   COMMENT ON COLUMN "CID_IDIOMA"."CID_ID" IS 'Identificador da CID.';
   COMMENT ON COLUMN "CID_IDIOMA"."IDIO_ID" IS 'Identificador do idioma.';
   COMMENT ON COLUMN "CID_IDIOMA"."CIID_TX_DESCRICAO" IS 'Descrição da CID no idioma especificado.';
   COMMENT ON TABLE "CID_IDIOMA"  IS 'Tabela que armazena os valores internacionalizados para cada CID.';
--------------------------------------------------------
--  DDL for Table CLASSIFICACAO_ABO
--------------------------------------------------------

  CREATE TABLE "CLASSIFICACAO_ABO" 
   (	"CLAB_TX_ABO" VARCHAR2(20), 
	"CLAB_VL_PRIORIDADE" NUMBER, 
	"CLAB_TX_ABO_RELACAO" VARCHAR2(20)
   ) ;

   COMMENT ON COLUMN "CLASSIFICACAO_ABO"."CLAB_TX_ABO" IS 'Chave primária da tabela de classificação abo.';
   COMMENT ON COLUMN "CLASSIFICACAO_ABO"."CLAB_VL_PRIORIDADE" IS 'Ordenação do ABO';
   COMMENT ON COLUMN "CLASSIFICACAO_ABO"."CLAB_TX_ABO_RELACAO" IS 'Relação do abo atual com outro ABO.';
   COMMENT ON TABLE "CLASSIFICACAO_ABO"  IS 'Tabela para priorizar ordenação de ABO.';
--------------------------------------------------------
--  DDL for Table COMENTARIO_MATCH
--------------------------------------------------------

  CREATE TABLE "COMENTARIO_MATCH" 
   (	"COMA_ID" NUMBER, 
	"COMA_TX_COMENTARIO" VARCHAR2(255), 
	"MATC_ID" NUMBER
   ) ;

   COMMENT ON COLUMN "COMENTARIO_MATCH"."COMA_ID" IS 'Chave primária da tabela de comentario match';
   COMMENT ON COLUMN "COMENTARIO_MATCH"."COMA_TX_COMENTARIO" IS 'Comentário do match';
   COMMENT ON COLUMN "COMENTARIO_MATCH"."MATC_ID" IS 'Chave estrangeira da tabela de match';
   COMMENT ON TABLE "COMENTARIO_MATCH"  IS 'Tabela que armazina algum comentario daquele match';
--------------------------------------------------------
--  DDL for Table CONDICAO_PACIENTE
--------------------------------------------------------

  CREATE TABLE "CONDICAO_PACIENTE" 
   (	"COPA_ID" NUMBER, 
	"COPA_TX_DESCRICAO" VARCHAR2(40)
   ) ;

   COMMENT ON COLUMN "CONDICAO_PACIENTE"."COPA_ID" IS 'Chave primária da condição do paciente';
   COMMENT ON COLUMN "CONDICAO_PACIENTE"."COPA_TX_DESCRICAO" IS 'Descrição da condição do paciente';
   COMMENT ON TABLE "CONDICAO_PACIENTE"  IS 'Tabela que define as possíveis condições de um paciente';
--------------------------------------------------------
--  DDL for Table CONFIGURACAO
--------------------------------------------------------

  CREATE TABLE "CONFIGURACAO" 
   (	"CONF_ID" VARCHAR2(100), 
	"CONF_TX_VALOR" VARCHAR2(255)
   ) ;

   COMMENT ON COLUMN "CONFIGURACAO"."CONF_ID" IS 'Chave que será consultada o valor da configuração';
   COMMENT ON COLUMN "CONFIGURACAO"."CONF_TX_VALOR" IS 'Configuração a ser utilizada pela aplicação';
   COMMENT ON TABLE "CONFIGURACAO"  IS 'Tabela de configuração do sistema';
--------------------------------------------------------
--  DDL for Table CONSTANTE_RELATORIO
--------------------------------------------------------

  CREATE TABLE "CONSTANTE_RELATORIO" 
   (	"CORE_ID_CODIGO" VARCHAR2(255), 
	"CORE_TX_VALOR" VARCHAR2(255)
   ) ;

   COMMENT ON COLUMN "CONSTANTE_RELATORIO"."CORE_ID_CODIGO" IS 'Identificador único da tabela';
   COMMENT ON COLUMN "CONSTANTE_RELATORIO"."CORE_TX_VALOR" IS 'Valor da constante.';
   COMMENT ON TABLE "CONSTANTE_RELATORIO"  IS 'Tabela que contém as constantes que serão utilizadas nos relatórios.';
--------------------------------------------------------
--  DDL for Table CONSTANTE_RELATORIO_AUD
--------------------------------------------------------

  CREATE TABLE "CONSTANTE_RELATORIO_AUD" 
   (	"AUDI_ID" NUMBER, 
	"CORE_ID_CODIGO" VARCHAR2(255), 
	"AUDI_TX_TIPO" NUMBER, 
	"CORE_TX_VALOR" VARCHAR2(255)
   ) ;
--------------------------------------------------------
--  DDL for Table CONTATO_TELEFONICO
--------------------------------------------------------

  CREATE TABLE "CONTATO_TELEFONICO" 
   (	"COTE_ID" NUMBER, 
	"COTE_TX_NOME" VARCHAR2(100), 
	"COTE_NR_COD_INTER" NUMBER(5,0), 
	"COTE_NR_COD_AREA" NUMBER(5,0), 
	"COTE_TX_COMPLEMENTO" VARCHAR2(20), 
	"PACI_NR_RMR" NUMBER, 
	"COTE_IN_TIPO" NUMBER(1,0), 
	"COTE_NR_NUMERO" NUMBER(10,0), 
	"COTE_IN_PRINCIPAL" NUMBER(1,0) DEFAULT 0, 
	"COTE_IN_EXCLUIDO" NUMBER DEFAULT 0, 
	"DOAD_ID" NUMBER, 
	"MEDI_ID" NUMBER, 
	"CETR_ID" NUMBER, 
	"COTE_FL_EXCLUIDO" NUMBER
   ) ;

   COMMENT ON COLUMN "CONTATO_TELEFONICO"."COTE_ID" IS 'Identificador do Contato Telefônico';
   COMMENT ON COLUMN "CONTATO_TELEFONICO"."COTE_TX_NOME" IS 'Nome do Contato';
   COMMENT ON COLUMN "CONTATO_TELEFONICO"."COTE_NR_COD_INTER" IS 'Código Internacional';
   COMMENT ON COLUMN "CONTATO_TELEFONICO"."COTE_NR_COD_AREA" IS 'Código de área';
   COMMENT ON COLUMN "CONTATO_TELEFONICO"."COTE_TX_COMPLEMENTO" IS 'Complemento do Telefone';
   COMMENT ON COLUMN "CONTATO_TELEFONICO"."PACI_NR_RMR" IS 'Referencia do paciente que possui os telefones';
   COMMENT ON COLUMN "CONTATO_TELEFONICO"."COTE_IN_TIPO" IS 'Tipo do contato (1 - fixo, 2- celular)';
   COMMENT ON COLUMN "CONTATO_TELEFONICO"."COTE_NR_NUMERO" IS 'Número do Telefone';
   COMMENT ON COLUMN "CONTATO_TELEFONICO"."COTE_IN_PRINCIPAL" IS 'Flag para definir se o telefone será o principal ou não (1- principal, 0-não principal)';
   COMMENT ON COLUMN "CONTATO_TELEFONICO"."COTE_IN_EXCLUIDO" IS 'Flag lógico para exclusão';
   COMMENT ON COLUMN "CONTATO_TELEFONICO"."DOAD_ID" IS 'Referencia do doador que possui os telefones';
   COMMENT ON COLUMN "CONTATO_TELEFONICO"."MEDI_ID" IS 'Contato relacionado ao médico.';
   COMMENT ON COLUMN "CONTATO_TELEFONICO"."CETR_ID" IS 'Referencia do centro de transplante que possui o telefone.';
   COMMENT ON COLUMN "CONTATO_TELEFONICO"."COTE_FL_EXCLUIDO" IS 'Flag lógico para exclusão';   
   COMMENT ON TABLE "CONTATO_TELEFONICO"  IS 'Contatos Telefonicos dos Pacientes';
--------------------------------------------------------
--  DDL for Table CONTATO_TELEFONICO_AUD
--------------------------------------------------------

  CREATE TABLE "CONTATO_TELEFONICO_AUD" 
   (	"COTE_ID" NUMBER, 
	"AUDI_ID" NUMBER, 
	"AUDI_TX_TIPO" NUMBER(3,0), 
	"COTE_NR_COD_AREA" NUMBER(10,0), 
	"COTE_NR_COD_INTER" NUMBER(10,0), 
	"COTE_TX_COMPLEMENTO" VARCHAR2(255), 
	"COTE_TX_NOME" VARCHAR2(255), 
	"COTE_NR_NUMERO" NUMBER, 
	"COTE_IN_PRINCIPAL" NUMBER(1,0), 
	"COTE_IN_TIPO" NUMBER(1,0), 
	"PACI_NR_RMR" NUMBER, 
	"COTE_IN_EXCLUIDO" NUMBER, 
	"DOAD_ID" NUMBER, 
	"MEDI_ID" NUMBER, 
	"CETR_ID" NUMBER, 
	"COTE_FL_EXCLUIDO" NUMBER, 
   ) ;

   COMMENT ON TABLE "CONTATO_TELEFONICO_AUD"  IS 'Tabela de auditoria da tabela CONTATO_TELEFONICO.';
--------------------------------------------------------
--  DDL for Table CONTROLE_JOB_BRASILCORD
--------------------------------------------------------

  CREATE TABLE "CONTROLE_JOB_BRASILCORD" 
   (	"COJB_ID" NUMBER, 
	"COJB_DT_SINCRONIZACAO" TIMESTAMP (6), 
	"COJB_IN_SUCESSO" CHAR(1)
   ) ;

   COMMENT ON COLUMN "CONTROLE_JOB_BRASILCORD"."COJB_ID" IS 'Id do controle de acessos ao serviço de cordão do BSCUP';
   COMMENT ON COLUMN "CONTROLE_JOB_BRASILCORD"."COJB_DT_SINCRONIZACAO" IS 'Data da última sincronização';
   COMMENT ON COLUMN "CONTROLE_JOB_BRASILCORD"."COJB_IN_SUCESSO" IS 'Se houve sucessou falha na última sincronização';
   COMMENT ON TABLE "CONTROLE_JOB_BRASILCORD"  IS 'Tabela para registro de sincronização ao serviço de liberação de cordão do BrasilCord';
--------------------------------------------------------
--  DDL for Table COPIA_VALOR_DNA_NMDP
--------------------------------------------------------

  CREATE TABLE "COPIA_VALOR_DNA_NMDP" 
   (	"LOCU_ID" VARCHAR2(4), 
	"DNNM_TX_VALOR" VARCHAR2(20)
   ) ;

   COMMENT ON COLUMN "COPIA_VALOR_DNA_NMDP"."LOCU_ID" IS 'CHAVE ESTRANGEIRA RELACIONADA AO LOCUS';
   COMMENT ON COLUMN "COPIA_VALOR_DNA_NMDP"."DNNM_TX_VALOR" IS 'VALORES VALIDOS SOROLOGIA E NMDP';
   COMMENT ON TABLE "COPIA_VALOR_DNA_NMDP"  IS 'TABELA DE RESULTADO DOS VALORES VALIDOS DE DNA E NMDP';

--------------------------------------------------------
--  DDL for Table dados_sem_dna_nmdp-12_35_09
--------------------------------------------------------

  CREATE TABLE "dados_sem_dna_nmdp-12_35_09" 
   (	"PROCESS_ORDER" NUMBER, 
	"DUPLICATE" NUMBER, 
	"DUMP_FILEID" NUMBER, 
	"DUMP_POSITION" NUMBER, 
	"DUMP_LENGTH" NUMBER, 
	"DUMP_ORIG_LENGTH" NUMBER, 
	"DUMP_ALLOCATION" NUMBER, 
	"COMPLETED_ROWS" NUMBER, 
	"ERROR_COUNT" NUMBER, 
	"ELAPSED_TIME" NUMBER, 
	"OBJECT_TYPE_PATH" VARCHAR2(200), 
	"OBJECT_PATH_SEQNO" NUMBER, 
	"OBJECT_TYPE" VARCHAR2(30), 
	"IN_PROGRESS" CHAR(1), 
	"OBJECT_NAME" VARCHAR2(500), 
	"OBJECT_LONG_NAME" VARCHAR2(4000), 
	"OBJECT_SCHEMA" VARCHAR2(30), 
	"ORIGINAL_OBJECT_SCHEMA" VARCHAR2(30), 
	"ORIGINAL_OBJECT_NAME" VARCHAR2(4000), 
	"PARTITION_NAME" VARCHAR2(30), 
	"SUBPARTITION_NAME" VARCHAR2(30), 
	"DATAOBJ_NUM" NUMBER, 
	"FLAGS" NUMBER, 
	"PROPERTY" NUMBER, 
	"TRIGFLAG" NUMBER, 
	"CREATION_LEVEL" NUMBER, 
	"COMPLETION_TIME" DATE, 
	"OBJECT_TABLESPACE" VARCHAR2(30), 
	"SIZE_ESTIMATE" NUMBER, 
	"OBJECT_ROW" NUMBER, 
	"PROCESSING_STATE" CHAR(1), 
	"PROCESSING_STATUS" CHAR(1), 
	"BASE_PROCESS_ORDER" NUMBER, 
	"BASE_OBJECT_TYPE" VARCHAR2(30), 
	"BASE_OBJECT_NAME" VARCHAR2(30), 
	"BASE_OBJECT_SCHEMA" VARCHAR2(30), 
	"ANCESTOR_PROCESS_ORDER" NUMBER, 
	"DOMAIN_PROCESS_ORDER" NUMBER, 
	"PARALLELIZATION" NUMBER, 
	"UNLOAD_METHOD" NUMBER, 
	"LOAD_METHOD" NUMBER, 
	"GRANULES" NUMBER, 
	"SCN" NUMBER, 
	"GRANTOR" VARCHAR2(30), 
	"XML_CLOB" CLOB, 
	"PARENT_PROCESS_ORDER" NUMBER, 
	"NAME" VARCHAR2(30), 
	"VALUE_T" VARCHAR2(4000), 
	"VALUE_N" NUMBER, 
	"IS_DEFAULT" NUMBER, 
	"FILE_TYPE" NUMBER, 
	"USER_DIRECTORY" VARCHAR2(4000), 
	"USER_FILE_NAME" VARCHAR2(4000), 
	"FILE_NAME" VARCHAR2(4000), 
	"EXTEND_SIZE" NUMBER, 
	"FILE_MAX_SIZE" NUMBER, 
	"PROCESS_NAME" VARCHAR2(30), 
	"LAST_UPDATE" DATE, 
	"WORK_ITEM" VARCHAR2(30), 
	"OBJECT_NUMBER" NUMBER, 
	"COMPLETED_BYTES" NUMBER, 
	"TOTAL_BYTES" NUMBER, 
	"METADATA_IO" NUMBER, 
	"DATA_IO" NUMBER, 
	"CUMULATIVE_TIME" NUMBER, 
	"PACKET_NUMBER" NUMBER, 
	"INSTANCE_ID" NUMBER, 
	"OLD_VALUE" VARCHAR2(4000), 
	"SEED" NUMBER, 
	"LAST_FILE" NUMBER, 
	"USER_NAME" VARCHAR2(30), 
	"OPERATION" VARCHAR2(30), 
	"JOB_MODE" VARCHAR2(30), 
	"QUEUE_TABNUM" NUMBER, 
	"CONTROL_QUEUE" VARCHAR2(30), 
	"STATUS_QUEUE" VARCHAR2(30), 
	"REMOTE_LINK" VARCHAR2(4000), 
	"VERSION" NUMBER, 
	"JOB_VERSION" VARCHAR2(30), 
	"DB_VERSION" VARCHAR2(30), 
	"TIMEZONE" VARCHAR2(64), 
	"STATE" VARCHAR2(30), 
	"PHASE" NUMBER, 
	"GUID" RAW(16), 
	"START_TIME" DATE, 
	"BLOCK_SIZE" NUMBER, 
	"METADATA_BUFFER_SIZE" NUMBER, 
	"DATA_BUFFER_SIZE" NUMBER, 
	"DEGREE" NUMBER, 
	"PLATFORM" VARCHAR2(101), 
	"ABORT_STEP" NUMBER, 
	"INSTANCE" VARCHAR2(60), 
	"CLUSTER_OK" NUMBER, 
	"SERVICE_NAME" VARCHAR2(100), 
	"OBJECT_INT_OID" VARCHAR2(32)
   ) ;

   COMMENT ON TABLE "dados_sem_dna_nmdp-12_35_09"  IS 'Data Pump Master Table EXPORT                         TABLE                         ';
--------------------------------------------------------
--  DDL for Table DESTINO_COLETA
--------------------------------------------------------

  CREATE TABLE "DESTINO_COLETA" 
   (	"DECO_ID" NUMBER, 
	"DECO_TX_DESCRICAO" VARCHAR2(60)
   ) ;

   COMMENT ON COLUMN "DESTINO_COLETA"."DECO_ID" IS 'ID DO DESTINO DE COLETA';
   COMMENT ON COLUMN "DESTINO_COLETA"."DECO_TX_DESCRICAO" IS 'NOME DO DESTINO DADO A COLETA';
   COMMENT ON TABLE "DESTINO_COLETA"  IS 'DESTINOS DADOS A COLETA RETIRADA DO DOADOR';
--------------------------------------------------------
--  DDL for Table DIAGNOSTICO
--------------------------------------------------------

  CREATE TABLE "DIAGNOSTICO" 
   (	"PACI_NR_RMR" NUMBER, 
	"DIAG_DT_DIAGNOSTICO" DATE, 
	"CID_ID" NUMBER
   ) ;

   COMMENT ON COLUMN "DIAGNOSTICO"."PACI_NR_RMR" IS 'Chave primária de diagnóstico';
   COMMENT ON COLUMN "DIAGNOSTICO"."DIAG_DT_DIAGNOSTICO" IS 'Data do diagnóstico';
   COMMENT ON COLUMN "DIAGNOSTICO"."CID_ID" IS 'Chave estrangeira para CID';
   COMMENT ON TABLE "DIAGNOSTICO"  IS 'Tabela de diagnostico de cada paciente';
--------------------------------------------------------
--  DDL for Table DIAGNOSTICO_AUD
--------------------------------------------------------

  CREATE TABLE "DIAGNOSTICO_AUD" 
   (	"PACI_NR_RMR" NUMBER, 
	"AUDI_ID" NUMBER, 
	"AUDI_TX_TIPO" NUMBER(3,0), 
	"DIAG_DT_DIAGNOSTICO" DATE, 
	"CID_ID" NUMBER
   ) ;

   COMMENT ON TABLE "DIAGNOSTICO_AUD"  IS 'Tabela de auditoria da tabela DIAGNOSTICO.';
--------------------------------------------------------
--  DDL for Table DIALOGO_BUSCA
--------------------------------------------------------

  CREATE TABLE "DIALOGO_BUSCA" 
   (	"DIBU_ID" NUMBER, 
	"BUSC_ID" NUMBER, 
	"USUA_ID" NUMBER, 
	"DIBU_TX_MENSAGEM" VARCHAR2(255), 
	"DIBU_DT_MOMENTO_MENSAGEM" TIMESTAMP (6)
   ) ;

   COMMENT ON COLUMN "DIALOGO_BUSCA"."DIBU_ID" IS 'IDENTIFICAÇÃO DO DIALOGO DE BUSCA';
   COMMENT ON COLUMN "DIALOGO_BUSCA"."BUSC_ID" IS 'REFERENCIA DE BUSCA';
   COMMENT ON COLUMN "DIALOGO_BUSCA"."USUA_ID" IS 'USUARIO QUE ESTÁ ENVIANDO A MENSAGEM';
   COMMENT ON COLUMN "DIALOGO_BUSCA"."DIBU_TX_MENSAGEM" IS 'MENSAGEM ENVIADA';
   COMMENT ON COLUMN "DIALOGO_BUSCA"."DIBU_DT_MOMENTO_MENSAGEM" IS 'MOMENTO DA MENSAGEM';
   COMMENT ON TABLE "DIALOGO_BUSCA"  IS 'ARMAZENAMENTO DA CONVERSA ENTRE O MÉDICO DO PACIENTE E O ANALISTA DE BUSCA SOBRE A BUSCA EM QUESTÃO';
--------------------------------------------------------
--  DDL for Table DISPONIBILIDADE
--------------------------------------------------------

  CREATE TABLE "DISPONIBILIDADE" 
   (	"DISP_ID" NUMBER, 
	"DISP_TX_DESCRICAO" VARCHAR2(255), 
	"DISP_DT_LIMITE_WORKUP" DATE, 
	"DISP_DT_COLETA" DATE, 
	"PEWO_ID" NUMBER, 
	"DISP_DT_CRIACAO" TIMESTAMP (6), 
	"DISP_DT_ACEITE" TIMESTAMP (6), 
	"DISP_IN_CT_COLETA" NUMBER(1,0) DEFAULT 0, 
	"DISP_DT_DISPONIBILIDADE_DOADOR" DATE, 
	"DISP_DT_LIBERACAO_DOADOR" DATE, 
	"PECL_ID" NUMBER
   ) ;

   COMMENT ON COLUMN "DISPONIBILIDADE"."DISP_ID" IS 'Chave primária de disponibilidade';
   COMMENT ON COLUMN "DISPONIBILIDADE"."DISP_TX_DESCRICAO" IS 'Descrição com o resumo das datas disponibilizadas.';
   COMMENT ON COLUMN "DISPONIBILIDADE"."DISP_DT_LIMITE_WORKUP" IS 'Data limite para entregado resultado de workup';
   COMMENT ON COLUMN "DISPONIBILIDADE"."DISP_DT_COLETA" IS 'Data da coleta, quando o material deve estar disponível.';
   COMMENT ON COLUMN "DISPONIBILIDADE"."PEWO_ID" IS 'Chave estrangeira da tabela PEDIDO_WORKUP.';
   COMMENT ON COLUMN "DISPONIBILIDADE"."DISP_DT_CRIACAO" IS 'Data de criação da disponibilidade';
   COMMENT ON COLUMN "DISPONIBILIDADE"."DISP_DT_ACEITE" IS 'Data que o centro de transplante sugeriu novas datas.';
   COMMENT ON COLUMN "DISPONIBILIDADE"."DISP_IN_CT_COLETA" IS 'Indica se o Centro de transplante deseja fazer a coleta. 0-Não, 1-Sim.';
   COMMENT ON COLUMN "DISPONIBILIDADE"."DISP_DT_DISPONIBILIDADE_DOADOR" IS 'Data prevista em que o doador deve estar no centro de coleta.';
   COMMENT ON COLUMN "DISPONIBILIDADE"."DISP_DT_LIBERACAO_DOADOR" IS 'Data prevista em que o doador será liberado pelo centro de coleta.';
   COMMENT ON COLUMN "DISPONIBILIDADE"."PECL_ID" IS 'Chave estrangeira da tabela PEDIDO_COLETA.';
   COMMENT ON TABLE "DISPONIBILIDADE"  IS 'Tabela com as disponibilidades sugeridas para workup.';
--------------------------------------------------------
--  DDL for Table DISPONIBILIDADE_AUD
--------------------------------------------------------

  CREATE TABLE "DISPONIBILIDADE_AUD" 
   (	"DISP_ID" NUMBER(19,0), 
	"AUDI_ID" NUMBER(19,0), 
	"AUDI_TX_TIPO" NUMBER(3,0), 
	"DISP_IN_CT_COLETA" NUMBER(1,0), 
	"DISP_DT_ACEITE" TIMESTAMP (6), 
	"DISP_DT_COLETA" DATE, 
	"DISP_DT_CRIACAO" TIMESTAMP (6), 
	"DISP_DT_LIMITE_WORKUP" DATE, 
	"DISP_TX_DESCRICAO" VARCHAR2(255 CHAR), 
	"PEWO_ID" NUMBER(19,0), 
	"DISP_DT_DISPONIBILIDADE_DOADOR" DATE, 
	"DISP_DT_LIBERACAO_DOADOR" DATE, 
	"PECL_ID" NUMBER
   ) ;

   COMMENT ON TABLE "DISPONIBILIDADE_AUD"  IS 'Tabela de auditoria da tabela DISPONIBILIDADE.';
--------------------------------------------------------
--  DDL for Table DISPONIBILIDADE_CENTRO_COLETA
--------------------------------------------------------

  CREATE TABLE "DISPONIBILIDADE_CENTRO_COLETA" 
   (	"DICC_ID" NUMBER, 
	"PEWO_ID" NUMBER, 
	"DICC_NR_INFO_CORRENTE" NUMBER, 
	"DICC_TX_DESCRICAO" VARCHAR2(255), 
	"CETR_ID" NUMBER, 
	"DICC_IN_EXCLUSAO" NUMBER DEFAULT 0
   ) ;

   COMMENT ON COLUMN "DISPONIBILIDADE_CENTRO_COLETA"."DICC_ID" IS 'Chave primária da tabela de info previa.';
   COMMENT ON COLUMN "DISPONIBILIDADE_CENTRO_COLETA"."PEWO_ID" IS 'Chave estrangeira da tabela de pedido_workup';
   COMMENT ON COLUMN "DISPONIBILIDADE_CENTRO_COLETA"."DICC_NR_INFO_CORRENTE" IS 'Número referente ao grupo de informações prévias de um pedido de workup.';
   COMMENT ON COLUMN "DISPONIBILIDADE_CENTRO_COLETA"."DICC_TX_DESCRICAO" IS 'Descrição da info previa';
   COMMENT ON COLUMN "DISPONIBILIDADE_CENTRO_COLETA"."CETR_ID" IS 'Centro de coleta a qual pertence essa disponibilidade.';
   COMMENT ON COLUMN "DISPONIBILIDADE_CENTRO_COLETA"."DICC_IN_EXCLUSAO" IS 'Flag lógico para exclusão';
   COMMENT ON TABLE "DISPONIBILIDADE_CENTRO_COLETA"  IS 'Tabela que vai armazenar as informações de possíveis datas disponíveis de um centro de transplante.';
--------------------------------------------------------
--  DDL for Table DOADOR
--------------------------------------------------------

  CREATE TABLE "DOADOR" 
   (	"DOAD_NR_DMR" NUMBER, 
	"DOAD_TX_CPF" VARCHAR2(11), 
	"DOAD_TX_NOME" VARCHAR2(255), 
	"DOAD_TX_NOME_MAE" VARCHAR2(255), 
	"DOAD_IN_SEXO" VARCHAR2(1), 
	"RACA_ID" NUMBER, 
	"ETNI_ID" NUMBER, 
	"DOAD_TX_ABO" VARCHAR2(3), 
	"PAIS_ID" NUMBER, 
	"UF_SIGLA" VARCHAR2(2), 
	"DOAD_DT_CADASTRO" TIMESTAMP (6), 
	"DOAD_DT_ATUALIZACAO" TIMESTAMP (6), 
	"DOAD_DT_NASCIMENTO" DATE, 
	"STDO_ID" NUMBER, 
	"MOSD_ID" NUMBER, 
	"DOAD_TX_NOME_SOCIAL" VARCHAR2(255), 
	"DOAD_TX_RG" VARCHAR2(15), 
	"DOAD_TX_ORGAO_EXPEDIDOR" VARCHAR2(255), 
	"DOAD_DT_RETORNO_INATIVIDADE" DATE, 
	"DOAD_VL_ALTURA" NUMBER(3,2), 
	"DOAD_VL_PESO" NUMBER(4,1), 
	"DOAD_TX_NOME_PAI" VARCHAR2(255), 
	"LABO_ID" NUMBER, 
	"DOAD_ID" NUMBER, 
	"DOAD_NR_IDADE" NUMBER, 
	"REGI_ID_ORIGEM" NUMBER, 
	"REGI_ID_PAGAMENTO" NUMBER, 
	"DOAD_IN_TIPO" NUMBER, 
	"DOAD_TX_ID_REGISTRO" VARCHAR2(20), 
	"USUA_ID" NUMBER, 
	"DOAD_IN_EMDIS" NUMBER, 
	"DOAD_VL_QUANT_TOTAL_CD34" NUMBER(6,2), 
	"DOAD_VL_QUANT_TOTAL_TCN" NUMBER(6,2), 
	"BASC_ID" NUMBER, 
	"DOAD_TX_ID_BANCO_SANGUE_CORDAO" VARCHAR2(20), 
	"DOAD_VL_VOLUME" NUMBER(6,2), 
	"DOAD_VL_TOT_LINFOCITOS" NUMBER(6,0), 
	"DOAD_VL_TOT_MONOCITOS" NUMBER(6,0), 
	"DOAD_VL_TOT_GRANULOCITOS" NUMBER(6,0), 
	"DOAD_VL_PER_HEMATOCRITOS" NUMBER(6,0), 
	"DOAD_VL_TOT_ANTES" NUMBER(6,0), 
	"DOAD_VL_TOT_DEPOIS" NUMBER(6,0), 
	"DOAD_VL_TOT_REAL" NUMBER(6,0), 
	"DOAD_VL_QUANT_TOTAL_TCN_ANTES" NUMBER(6,0), 
	"DOAD_VL_TOT_PER_HEMATOCRITOS" NUMBER(6,0), 
	"HEEN_ID" NUMBER, 
	"DOAD_TX_FASE" VARCHAR2(2), 
	"DOAD_TX_GRID" VARCHAR2(19),
	"DOAD_TX_NUMERO_HEMOCENTRO" VARCHAR2(15),
	"DOAD_IN_FUMANTE" VARCHAR2(1),
	"ESCI_ID" NUMBER,
    "MUNI_ID" NUMBER,
    "MUNI_ID_NATURALIDADE" NUMBER,
    "DOAD_NR_ID_CAMPANHA_REDOMEWEB" NUMBER, 
	"DOAD_DT_COLETA" DATE 
   ) ;

   COMMENT ON COLUMN "DOADOR"."DOAD_NR_DMR" IS 'Identificador do Doador';
   COMMENT ON COLUMN "DOADOR"."DOAD_TX_CPF" IS 'Cpf do dador';
   COMMENT ON COLUMN "DOADOR"."DOAD_TX_NOME" IS 'Nome do doador';
   COMMENT ON COLUMN "DOADOR"."DOAD_TX_NOME_MAE" IS 'Nome da Mãe do doador';
   COMMENT ON COLUMN "DOADOR"."DOAD_IN_SEXO" IS 'Sexo do doador (M - masculinho, F- Feminino)';
   COMMENT ON COLUMN "DOADOR"."RACA_ID" IS 'Referencia para tabela raça ';
   COMMENT ON COLUMN "DOADOR"."ETNI_ID" IS 'Referencia para a tabela etnia ';
   COMMENT ON COLUMN "DOADOR"."DOAD_TX_ABO" IS 'Tipo sanguíneo (A+,B+,A-,B-,AB+,AB-,O+,O-)';
   COMMENT ON COLUMN "DOADOR"."PAIS_ID" IS 'Id do país da nacionalidade';
   COMMENT ON COLUMN "DOADOR"."UF_SIGLA" IS 'Id do estado da naturadlidade';
   COMMENT ON COLUMN "DOADOR"."DOAD_DT_CADASTRO" IS 'Data do cadastro ';
   COMMENT ON COLUMN "DOADOR"."DOAD_DT_ATUALIZACAO" IS 'Data da ultima atualização ';
   COMMENT ON COLUMN "DOADOR"."DOAD_DT_NASCIMENTO" IS 'Data de nascimento do doador';
   COMMENT ON COLUMN "DOADOR"."STDO_ID" IS 'Id do status do doador';
   COMMENT ON COLUMN "DOADOR"."MOSD_ID" IS 'ID do motivo do status doador';
   COMMENT ON COLUMN "DOADOR"."DOAD_TX_NOME_SOCIAL" IS 'Nome Social do doador';
   COMMENT ON COLUMN "DOADOR"."DOAD_TX_RG" IS 'Número do documento de identidade (RG)';
   COMMENT ON COLUMN "DOADOR"."DOAD_TX_ORGAO_EXPEDIDOR" IS 'Orgão Expedidor do RG';
   COMMENT ON COLUMN "DOADOR"."DOAD_DT_RETORNO_INATIVIDADE" IS 'Enquanto inativo temporário, é a data de retorno do doador do período de inatividade.';
   COMMENT ON COLUMN "DOADOR"."DOAD_VL_ALTURA" IS 'Altura atual do doador';
   COMMENT ON COLUMN "DOADOR"."DOAD_VL_PESO" IS 'Peso atual do doador';
   COMMENT ON COLUMN "DOADOR"."DOAD_TX_NOME_PAI" IS 'Nome do pai do doador';
   COMMENT ON COLUMN "DOADOR"."LABO_ID" IS 'Laboratorio do doador';
   COMMENT ON COLUMN "DOADOR"."DOAD_ID" IS 'Identificador do doador';
   COMMENT ON COLUMN "DOADOR"."DOAD_NR_IDADE" IS 'Idade do doador';
   COMMENT ON COLUMN "DOADOR"."REGI_ID_ORIGEM" IS 'Registro Origem do doador.';
   COMMENT ON COLUMN "DOADOR"."REGI_ID_PAGAMENTO" IS 'Registro para pagamento dos doadores internacionais.';
   COMMENT ON COLUMN "DOADOR"."DOAD_IN_TIPO" IS 'Identifica o tipo de doador. 0 - Doador Nacional 1 - Doador Internacional 2 - Cordão Nacional 3 Cordão Internacional.';
   COMMENT ON COLUMN "DOADOR"."DOAD_TX_ID_REGISTRO" IS 'Identificador do doador internacional no registro de origem.';
   COMMENT ON COLUMN "DOADOR"."USUA_ID" IS 'Referencia para o usuário que cadastratrou o doador.';
   COMMENT ON COLUMN "DOADOR"."DOAD_IN_EMDIS" IS 'Indica se o cadastro do doador internacional veio do EDMIS ou não.';
   COMMENT ON COLUMN "DOADOR"."DOAD_VL_QUANT_TOTAL_CD34" IS 'Quantidade total de  CD34';
   COMMENT ON COLUMN "DOADOR"."DOAD_VL_QUANT_TOTAL_TCN" IS 'Quantidade total de TCN';
   COMMENT ON COLUMN "DOADOR"."BASC_ID" IS 'Referência para a tabela banco de sangue de cordão.';
   COMMENT ON COLUMN "DOADOR"."DOAD_TX_ID_BANCO_SANGUE_CORDAO" IS 'Identificador do cordão nacional no banco de sangue de cordão.';
   COMMENT ON COLUMN "DOADOR"."DOAD_VL_VOLUME" IS 'Quantidade em ml de sangue da bolsa';
   COMMENT ON COLUMN "DOADOR"."DOAD_VL_TOT_LINFOCITOS" IS 'Usado pelos doadores do tipo cordão para o total de linfócitos ';
   COMMENT ON COLUMN "DOADOR"."DOAD_VL_TOT_MONOCITOS" IS 'Usado pelos doadores do tipo cordão para o total de 
 monocitos';
   COMMENT ON COLUMN "DOADOR"."DOAD_VL_TOT_GRANULOCITOS" IS 'Usado pelos doadores do tipo cordão para o total de granulocitos';
   COMMENT ON COLUMN "DOADOR"."DOAD_VL_PER_HEMATOCRITOS" IS 'Usado pelos doadores do tipo cordão para o total de hematocritos';
   COMMENT ON COLUMN "DOADOR"."DOAD_VL_TOT_ANTES" IS 'Usado pelos doadores do tipo cordão para o total de valor total antes';
   COMMENT ON COLUMN "DOADOR"."DOAD_VL_TOT_DEPOIS" IS 'Usado pelos doadores do tipo cordão para o total de valor total depois';
   COMMENT ON COLUMN "DOADOR"."DOAD_VL_TOT_REAL" IS 'Usado pelos doadores do tipo cordão para o total de valor total real
';
   COMMENT ON COLUMN "DOADOR"."DOAD_VL_QUANT_TOTAL_TCN_ANTES" IS 'Usado pelos doadores do tipo cordão para o total de celulas nucleadas antes
';
   COMMENT ON COLUMN "DOADOR"."DOAD_VL_TOT_PER_HEMATOCRITOS" IS 'Usado pelos doadores do tipo cordão para o total de hematocritos';
   COMMENT ON COLUMN "DOADOR"."HEEN_ID" IS 'Referencia para o hemocentro/hemonucleo que cadastratrou o doador.';
   COMMENT ON COLUMN "DOADOR"."DOAD_TX_FASE" IS 'Indica, de acordo com os exames recebidos e aprovados, qual a fase encontra-se o doador. F1: Match inicial com paciente; F2: A, B, C, DR e DQ de média pra cima (Alelos e grupos NMDP, G e P); F3: Exame CT realizado.';
   COMMENT ON COLUMN "DOADOR"."DOAD_TX_GRID" IS 'Identificador únido do doador no registro internacional.';
   COMMENT ON COLUMN "DOADOR"."DOAD_TX_NUMERO_HEMOCENTRO" IS 'Número do doador no hemocentro.';
   COMMENT ON COLUMN "DOADOR"."DOAD_IN_FUMANTE" IS 'Indica se o doador é fumante ou não.';
   COMMENT ON COLUMN "DOADOR"."ESCI_ID" IS 'Referencia para tabela estado civil.';
   COMMENT ON COLUMN "DOADOR"."MUNI_ID" IS 'Id do MuncÃ­pio de naturalidade';
   COMMENT ON TABLE "DOADOR"  IS 'Doadores';
--------------------------------------------------------
--  DDL for Table DOADOR_AUD
--------------------------------------------------------

  CREATE TABLE "DOADOR_AUD" 
   (	"DOAD_NR_DMR" NUMBER, 
	"AUDI_ID" NUMBER, 
	"AUDI_TX_TIPO" NUMBER(3,0), 
	"DOAD_TX_CPF" VARCHAR2(11), 
	"DOAD_TX_NOME" VARCHAR2(255), 
	"DOAD_TX_NOME_MAE" VARCHAR2(255), 
	"DOAD_IN_SEXO" VARCHAR2(1), 
	"RACA_ID" NUMBER, 
	"ETNI_ID" NUMBER, 
	"DOAD_TX_ABO" VARCHAR2(3), 
	"PAIS_ID" NUMBER, 
	"UF_SIGLA" VARCHAR2(2), 
	"DOAD_DT_CADASTRO" TIMESTAMP (6), 
	"DOAD_DT_ATUALIZACAO" TIMESTAMP (6), 
	"DOAD_DT_NASCIMENTO" DATE, 
	"STDO_ID" NUMBER, 
	"MOSD_ID" NUMBER, 
	"DOAD_TX_NOME_SOCIAL" VARCHAR2(255), 
	"DOAD_TX_RG" VARCHAR2(15), 
	"DOAD_TX_ORGAO_EXPEDIDOR" VARCHAR2(255), 
	"DOAD_DT_RETORNO_INATIVIDADE" DATE, 
	"DOAD_VL_ALTURA" NUMBER(3,2), 
	"DOAD_VL_PESO" NUMBER(4,1), 
	"DOAD_TX_NOME_PAI" VARCHAR2(255), 
	"LABO_ID" NUMBER, 
	"DOAD_ID" VARCHAR2(20), 
	"REGI_ID_PAGAMENTO" NUMBER, 
	"DOAD_IN_TIPO" NUMBER, 
	"DOAD_TX_ID_REGISTRO" VARCHAR2(20), 
	"DOAD_IN_EMDIS" NUMBER, 
	"DOAD_NR_IDADE" NUMBER, 
	"REGI_ID_ORIGEM" NUMBER, 
	"USUA_ID" NUMBER, 
	"DOAD_VL_QUANT_TOTAL_CD34" NUMBER(6,0), 
	"DOAD_VL_QUANT_TOTAL_TCN" NUMBER(6,0), 
	"BASC_ID" NUMBER, 
	"DOAD_TX_ID_BANCO_SANGUE_CORDAO" VARCHAR2(20), 
	"DOAD_VL_VOLUME" NUMBER(6,0), 
	"DOAD_TX_FASE" VARCHAR2(2), 
	"HEEN_ID" NUMBER, 
	"DOAD_TX_GRID" VARCHAR2(12),
	"DOAD_TX_NUMERO_HEMOCENTRO" VARCHAR2(15),
	"DOAD_IN_FUMANTE" VARCHAR2(1),
	"ESCI_ID" NUMBER,
	"MUNI_ID" NUMBER
   ) ;

   COMMENT ON COLUMN "DOADOR_AUD"."DOAD_DT_RETORNO_INATIVIDADE" IS 'Enquanto inativo temporário, é a data de retorno do doador do período de inatividade.';
   COMMENT ON COLUMN "DOADOR_AUD"."DOAD_TX_GRID" IS 'Identificador únido do doador no registro internacional.';
--------------------------------------------------------
--  DDL for Table DOADOR_IN_REDOME
--------------------------------------------------------

  CREATE TABLE "DOADOR_IN_REDOME" 
   (	"DOIR_ID" NUMBER, 
	"DOIR_DT_INCLUSAO" TIMESTAMP (6), 
	"DOIR_IN_OPERACAO" VARCHAR2(1), 
	"DOIR_IN_STATUS_OPERACAO" VARCHAR2(1), 
	"DOIR_TX_DESCRICAO_ERRO" VARCHAR2(255), 
	"DOIR_TX_REGISTRO_DOADOR" CLOB, 
	"DOIR_DT_PROCESSAMENTO" TIMESTAMP (6), 
	"DOIR_TX_TIPO_ALTERACAO" VARCHAR2(20), 
	"DOIR_NR_ID_ALTERACAO" NUMBER, 
	"DOIR_NR_DMR" NUMBER
   ) ;

   COMMENT ON COLUMN "DOADOR_IN_REDOME"."DOIR_ID" IS 'ID da integra��o redome.';
   COMMENT ON COLUMN "DOADOR_IN_REDOME"."DOIR_DT_INCLUSAO" IS 'Data da inclusão do doador';
   COMMENT ON COLUMN "DOADOR_IN_REDOME"."DOIR_IN_OPERACAO" IS 'Tipo de operação I-Inclusão A-Alteração';
   COMMENT ON COLUMN "DOADOR_IN_REDOME"."DOIR_IN_STATUS_OPERACAO" IS 'Status da operação. indica o que já foi processado ao registro P- A processar, T- Transferido';
   COMMENT ON COLUMN "DOADOR_IN_REDOME"."DOIR_TX_DESCRICAO_ERRO" IS 'Texto livre para erros';
   COMMENT ON COLUMN "DOADOR_IN_REDOME"."DOIR_TX_REGISTRO_DOADOR" IS 'Json com a estrutura do doador a ser importado';
   COMMENT ON COLUMN "DOADOR_IN_REDOME"."DOIR_DT_PROCESSAMENTO" IS 'Data de processamento do registro';
   COMMENT ON COLUMN "DOADOR_IN_REDOME"."DOIR_TX_TIPO_ALTERACAO" IS 'Tipo de alteração definido no enum tipo alteracao';
   COMMENT ON COLUMN "DOADOR_IN_REDOME"."DOIR_NR_ID_ALTERACAO" IS 'Coluna utilizada para auxliar no processo de importação.';
   COMMENT ON COLUMN "DOADOR_IN_REDOME"."DOIR_NR_DMR" IS 'dmr de identifica��o do doador.';
   COMMENT ON TABLE "DOADOR_IN_REDOME"  IS 'Guarda os dados migrados do redome para o modred em formato JSON ';
--------------------------------------------------------
--  DDL for Table EMAIL_CONTATO
--------------------------------------------------------

  CREATE TABLE "EMAIL_CONTATO" 
   (	"EMCO_ID" NUMBER, 
	"EMCO_TX_EMAIL" VARCHAR2(100), 
	"DOAD_ID" NUMBER, 
	"EMCO_IN_EXCLUIDO" NUMBER DEFAULT 0, 
	"EMCO_IN_PRINCIPAL" NUMBER(1,0) DEFAULT 0, 
	"MEDI_ID" NUMBER, 
	"LABO_ID" NUMBER, 
	"CETR_ID" NUMBER
   ) ;

   COMMENT ON COLUMN "EMAIL_CONTATO"."EMCO_ID" IS 'Referência do email contato';
   COMMENT ON COLUMN "EMAIL_CONTATO"."EMCO_TX_EMAIL" IS 'E-mail';
   COMMENT ON COLUMN "EMAIL_CONTATO"."DOAD_ID" IS 'Id do doador';
   COMMENT ON COLUMN "EMAIL_CONTATO"."EMCO_IN_PRINCIPAL" IS 'Flag que identifica se o email é o principal para contato.';
   COMMENT ON COLUMN "EMAIL_CONTATO"."MEDI_ID" IS 'E-mail relacionado ao médico.';
   COMMENT ON COLUMN "EMAIL_CONTATO"."LABO_ID" IS 'Identificador do Laboratorio.';
   COMMENT ON COLUMN "EMAIL_CONTATO"."CETR_ID" IS 'Referência paraa tabela centro de transplante.';
   COMMENT ON TABLE "EMAIL_CONTATO"  IS 'Endereços de email para contato';
--------------------------------------------------------
--  DDL for Table EMAIL_CONTATO_AUD
--------------------------------------------------------

  CREATE TABLE "EMAIL_CONTATO_AUD" 
   (	"EMCO_ID" NUMBER, 
	"AUDI_ID" NUMBER, 
	"AUDI_TX_TIPO" NUMBER(3,0), 
	"EMCO_TX_EMAIL" VARCHAR2(100), 
	"DOAD_ID" NUMBER, 
	"EMCO_IN_EXCLUIDO" NUMBER, 
	"EMCO_IN_PRINCIPAL" NUMBER(1,0), 
	"MEDI_ID" NUMBER, 
	"LABO_ID" NUMBER, 
	"CETR_ID" NUMBER
   ) ;
--------------------------------------------------------
--  DDL for Table ENDERECO_CONTATO
--------------------------------------------------------

  CREATE TABLE "ENDERECO_CONTATO" 
   (	"ENCO_ID" NUMBER, 
	"ENCO_ID_PAIS" NUMBER, 
	"ENCO_CEP" VARCHAR2(9), 
	"ENCO_TX_TIPO_LOGRADOURO" VARCHAR2(100), 
	"ENCO_TX_NOME" VARCHAR2(255), 
	"ENCO_TX_COMPLEMENTO" VARCHAR2(50), 
	"ENCO_TX_BAIRRO" VARCHAR2(255),   
	"ENCO_TX_ENDERECO_ESTRANGEIRO" VARCHAR2(255), 
	"ENCO_TX_NUMERO" VARCHAR2(10), 
	"ENCO_IN_EXCLUIDO" NUMBER DEFAULT 0, 
	"PACI_NR_RMR" NUMBER, 
	"DOAD_ID" NUMBER, 
	"ENCO_IN_PRINCIPAL" NUMBER(1,0) DEFAULT 0, 
	"ENCO_IN_CORRESPONDENCIA" NUMBER(1,0) DEFAULT 0, 
	"HEEN_ID" NUMBER, 
	"CETR_ID" NUMBER, 
	"ENCO_FL_EXCLUIDO" NUMBER, 
	"LABO_ID" NUMBER, 
	"ENCO_IN_RETIRADA" NUMBER DEFAULT 0, 
	"ENCO_IN_ENTREGA" NUMBER DEFAULT 0,
	"ENCO_IN_WORKUP" NUMBER DEFAULT 0, 
	"ENCO_IN_GCSF" NUMBER DEFAULT 0, 
	"ENCO_IN_INTERNACAO" NUMBER DEFAULT 0, 
	"MEDI_ID" NUMBER, 
	"MUNI_ID" NUMBER	
   ) ;

   COMMENT ON COLUMN "ENDERECO_CONTATO"."ENCO_ID" IS 'Identificador do Contato';
   COMMENT ON COLUMN "ENDERECO_CONTATO"."ENCO_ID_PAIS" IS 'Campo de referencia de pais';
   COMMENT ON COLUMN "ENDERECO_CONTATO"."ENCO_CEP" IS 'CEP do contato';
   COMMENT ON COLUMN "ENDERECO_CONTATO"."ENCO_TX_TIPO_LOGRADOURO" IS 'Tipo do Logradouro';
   COMMENT ON COLUMN "ENDERECO_CONTATO"."ENCO_TX_NOME" IS 'Nome do Logradouro';
   COMMENT ON COLUMN "ENDERECO_CONTATO"."ENCO_TX_COMPLEMENTO" IS 'Complemento do logradouro';
   COMMENT ON COLUMN "ENDERECO_CONTATO"."ENCO_TX_BAIRRO" IS 'Bairro do logradouro';
   COMMENT ON COLUMN "ENDERECO_CONTATO"."ENCO_TX_ENDERECO_ESTRANGEIRO" IS 'Endereço completo quando for estrangeiro';
   COMMENT ON COLUMN "ENDERECO_CONTATO"."ENCO_TX_NUMERO" IS 'Número do logradouro';
   COMMENT ON COLUMN "ENDERECO_CONTATO"."ENCO_IN_EXCLUIDO" IS 'Flag lógico para exclusão';
   COMMENT ON COLUMN "ENDERECO_CONTATO"."ENCO_IN_PRINCIPAL" IS 'Flag lógico que identifica se o endereço é o principal de contato com o doador.';
   COMMENT ON COLUMN "ENDERECO_CONTATO"."ENCO_IN_CORRESPONDENCIA" IS 'Flag lógico que identifica se o endereço é de correspondência.';
   COMMENT ON COLUMN "ENDERECO_CONTATO"."HEEN_ID" IS 'ID de identificação da hemoentidade associada a este endereço.';
   COMMENT ON COLUMN "ENDERECO_CONTATO"."ENCO_FL_EXCLUIDO" IS 'Flag lógico para exclusão';
   COMMENT ON COLUMN "ENDERECO_CONTATO"."LABO_ID" IS 'ID de identificação do laboratório associado a este endereço.';
   COMMENT ON COLUMN "ENDERECO_CONTATO"."ENCO_IN_RETIRADA" IS 'Flag lógico que identifica se o endereço é de retirada para um determinado centro de transplante.';
   COMMENT ON COLUMN "ENDERECO_CONTATO"."ENCO_IN_ENTREGA" IS 'Flag lógico que identifica se o endereço é de entrega para um determinado centro de transplante.';
   COMMENT ON COLUMN "ENDERECO_CONTATO"."ENCO_IN_WORKUP" IS 'Flag lógico que identifica se o endereço é de workup para um determinado centro de transplante.';
   COMMENT ON COLUMN "ENDERECO_CONTATO"."ENCO_IN_GCSF" IS 'Flag lógico que identifica se o endereço é G-CSF para um determinado centro de transplante.';
   COMMENT ON COLUMN "ENDERECO_CONTATO"."ENCO_IN_INTERNACAO" IS 'Flag lógico que identifica se o endereço é de internação para um determinado centro de transplante.';
   COMMENT ON COLUMN "ENDERECO_CONTATO"."MEDI_ID" IS 'Endereço relacionado ao médico.';
   COMMENT ON COLUMN "ENDERECO_CONTATO"."MUNI_ID" IS 'Identificador do Município ';
   COMMENT ON TABLE "ENDERECO_CONTATO"  IS 'Dados de endereço do Paciente';
--------------------------------------------------------
--  DDL for Table ENDERECO_CONTATO_AUD
--------------------------------------------------------

  CREATE TABLE "ENDERECO_CONTATO_AUD" 
   (	"ENCO_ID" NUMBER, 
	"AUDI_ID" NUMBER, 
	"AUDI_TX_TIPO" NUMBER(3,0), 
	"ENCO_TX_BAIRRO" VARCHAR2(255), 
	"ENCO_CEP" VARCHAR2(255), 
	"ENCO_TX_COMPLEMENTO" VARCHAR2(255), 
	"ENCO_TX_ENDERECO_ESTRANGEIRO" VARCHAR2(255), 
	"ENCO_TX_NOME" VARCHAR2(255), 
	"ENCO_TX_NUMERO" VARCHAR2(10), 
	"ENCO_TX_TIPO_LOGRADOURO" VARCHAR2(255), 
	"ENCO_ID_PAIS" NUMBER, 
	"ENCO_IN_EXCLUIDO" NUMBER, 
	"PACI_NR_RMR" NUMBER, 
	"ENCO_IN_PRINCIPAL" NUMBER(1,0) DEFAULT 0, 
	"ENCO_IN_CORRESPONDENCIA" NUMBER(1,0) DEFAULT 0, 
	"DOAD_ID" NUMBER, 
	"HEEN_ID" NUMBER, 
	"CETR_ID" NUMBER, 
	"ENCO_FL_EXCLUIDO" NUMBER, 
	"LABO_ID" NUMBER, 
	"ENCO_IN_RETIRADA" NUMBER DEFAULT 0, 
	"ENCO_IN_ENTREGA" NUMBER DEFAULT 0, 
	"ENCO_IN_WORKUP" NUMBER DEFAULT 0, 
	"ENCO_IN_GCSF" NUMBER DEFAULT 0, 
	"ENCO_IN_INTERNACAO" NUMBER DEFAULT 0, 
	"MEDI_ID" NUMBER, 
	"MUNI_ID" NUMBER
   ) ;

   COMMENT ON COLUMN "ENDERECO_CONTATO_AUD"."HEEN_ID" IS 'ID de identificação da hemoentidade associada a este endereço.';
   COMMENT ON TABLE "ENDERECO_CONTATO_AUD"  IS 'Tabela de auditoria da tabela ENDERECO_CONTATO.';
--------------------------------------------------------
--  DDL for Table ESTABELECIMENTO_IN_REDOME
--------------------------------------------------------

  CREATE TABLE "ESTABELECIMENTO_IN_REDOME" 
   (	"ESIR_ID" NUMBER, 
	"ID_ESTABELECIMENTO" NUMBER, 
	"ESIR_NR_TIPO_ENTIDADE" NUMBER, 
	"ESIR_DT_INCLUSAO" TIMESTAMP (6), 
	"ESIR_IN_STATUS_OPERACAO" VARCHAR2(1), 
	"ESIR_TX_DESCRICAO_ERRO" VARCHAR2(255), 
	"ESIR_TX_REGISTRO" CLOB, 
	"ESIR_DT_PROCESSAMENTO" TIMESTAMP (6)
   ) ;

   COMMENT ON COLUMN "ESTABELECIMENTO_IN_REDOME"."ESIR_ID" IS 'Identificador único do tabela.';
   COMMENT ON COLUMN "ESTABELECIMENTO_IN_REDOME"."ID_ESTABELECIMENTO" IS 'Identificador do estabelecimento no REDOMEWEB a ser processado.';
   COMMENT ON COLUMN "ESTABELECIMENTO_IN_REDOME"."ESIR_NR_TIPO_ENTIDADE" IS 'Tipo de entidade a ser processada 0 - HemoEntidade ou 1 - Laboratorio';
   COMMENT ON COLUMN "ESTABELECIMENTO_IN_REDOME"."ESIR_DT_INCLUSAO" IS 'Data da inclusão do doador';
   COMMENT ON COLUMN "ESTABELECIMENTO_IN_REDOME"."ESIR_IN_STATUS_OPERACAO" IS 'Status da operação. indica o que já foi processado ao registro P- A processar, T- Transferido';
   COMMENT ON COLUMN "ESTABELECIMENTO_IN_REDOME"."ESIR_TX_DESCRICAO_ERRO" IS 'Texto livre para erros';
   COMMENT ON COLUMN "ESTABELECIMENTO_IN_REDOME"."ESIR_TX_REGISTRO" IS 'Json com a estrutura das entidades (HemoEntidade ou Laboratorio) a ser importado';
   COMMENT ON COLUMN "ESTABELECIMENTO_IN_REDOME"."ESIR_DT_PROCESSAMENTO" IS 'Data de processamento do registro';
   COMMENT ON TABLE "ESTABELECIMENTO_IN_REDOME"  IS 'Guarda os dados migrados do redome para o modred em formato JSON dos estabelecimentos (HemoEntidade, Laboratorio)';
--------------------------------------------------------
--  DDL for Table ESTAGIO_DOENCA
--------------------------------------------------------

  CREATE TABLE "ESTAGIO_DOENCA" 
   (	"ESDO_ID" NUMBER, 
	"ESDO_TX_DESCRICAO" VARCHAR2(40)
   ) ;

   COMMENT ON COLUMN "ESTAGIO_DOENCA"."ESDO_ID" IS 'Chave primária de estágio';
   COMMENT ON COLUMN "ESTAGIO_DOENCA"."ESDO_TX_DESCRICAO" IS 'Descrição do estágio';
   COMMENT ON TABLE "ESTAGIO_DOENCA"  IS 'Tabela de estágio da doença do CID';
--------------------------------------------------------
--  DDL for Table ETNIA
--------------------------------------------------------

  CREATE TABLE "ETNIA" 
   (	"ETNI_ID" NUMBER, 
	"ETNI_TX_NOME" VARCHAR2(100),
	"ETNI_TX_CD_ETNIA_REDOMEWEB" VARCHAR2(4) 
   ) ;

   COMMENT ON COLUMN "ETNIA"."ETNI_ID" IS 'Identificador da etnia';
   COMMENT ON COLUMN "ETNIA"."ETNI_TX_NOME" IS 'Nome da etnia ';
   COMMENT ON COLUMN "ETNIA"."ETNI_TX_CD_ETNIA_REDOMEWEB" IS 'Identificador da etnia na base do REDOMEWeb (integração).';
   COMMENT ON TABLE "ETNIA"  IS 'Etnias indígenas';
--------------------------------------------------------
--  DDL for Table EVOLUCAO
--------------------------------------------------------

  CREATE TABLE "EVOLUCAO" 
   (	"EVOL_ID" NUMBER, 
	"EVOL_DT_CRIACAO" TIMESTAMP (6), 
	"MOTI_ID" NUMBER, 
	"EVOL_VL_PESO" NUMBER(4,1), 
	"EVOL_VL_ALTURA" NUMBER(3,2), 
	"EVOL_IN_CMV" NUMBER(1,0), 
	"EVOL_TX_TRATAMENTO_ANTERIOR" VARCHAR2(4000), 
	"EVOL_TX_TRATAMENTO_ATUAL" VARCHAR2(4000), 
	"EVOL_TX_CONDICAO_ATUAL" VARCHAR2(4000), 
	"COPA_ID" NUMBER, 
	"ESDO_ID" NUMBER, 
	"PACI_NR_RMR" NUMBER, 
	"USUA_ID" NUMBER, 
	"EVOL_IN_EXAME_ANTICORPO" NUMBER, 
	"EVOL_DT_EXAME_ANTICORPO" TIMESTAMP (6), 
	"EVOL_TX_EXAME_ANTICORPO" VARCHAR2(255),
	"EVOL_DT_ULTIMO_TRANSPLANTE" DATE 
   ) ;

   COMMENT ON COLUMN "EVOLUCAO"."EVOL_ID" IS 'Chave primária de evolução';
   COMMENT ON COLUMN "EVOLUCAO"."EVOL_DT_CRIACAO" IS 'Data de criação de evolução';
   COMMENT ON COLUMN "EVOLUCAO"."MOTI_ID" IS 'Chave estrangeira de motivo';
   COMMENT ON COLUMN "EVOLUCAO"."EVOL_VL_PESO" IS 'Peso atual do paciente';
   COMMENT ON COLUMN "EVOLUCAO"."EVOL_VL_ALTURA" IS 'Altura atual do paciente';
   COMMENT ON COLUMN "EVOLUCAO"."EVOL_IN_CMV" IS 'Resultado do exame cmv';
   COMMENT ON COLUMN "EVOLUCAO"."EVOL_TX_TRATAMENTO_ANTERIOR" IS 'Descrição do tratamento anterior do paciente';
   COMMENT ON COLUMN "EVOLUCAO"."EVOL_TX_TRATAMENTO_ATUAL" IS 'Descrição do tratamento atual do paciente';
   COMMENT ON COLUMN "EVOLUCAO"."EVOL_TX_CONDICAO_ATUAL" IS 'Condição atual do paciente';
   COMMENT ON COLUMN "EVOLUCAO"."COPA_ID" IS 'Chave estrageira para tabela de condição do paciente';
   COMMENT ON COLUMN "EVOLUCAO"."ESDO_ID" IS 'Chave estrangeira para o estágio da doença';
   COMMENT ON COLUMN "EVOLUCAO"."PACI_NR_RMR" IS 'Chave estrangeira para o paciente';
   COMMENT ON COLUMN "EVOLUCAO"."USUA_ID" IS 'Chave estrangeira para o usuário';
   COMMENT ON COLUMN "EVOLUCAO"."EVOL_IN_EXAME_ANTICORPO" IS 'Se o paciente realizou ou não o exame de anticorpo (0 para não e 1 para sim)';
   COMMENT ON COLUMN "EVOLUCAO"."EVOL_DT_EXAME_ANTICORPO" IS 'Data de realização do exame do anticorpo';
   COMMENT ON COLUMN "EVOLUCAO"."EVOL_TX_EXAME_ANTICORPO" IS 'Texto livre para resultado de exame de anticorpo';
   COMMENT ON TABLE "EVOLUCAO"  IS 'Tabela de evolução da doença do paciente';
--------------------------------------------------------
--  DDL for Table EXAME
--------------------------------------------------------

  CREATE TABLE "EXAME" 
   (	"EXAM_ID" NUMBER, 
	"EXAM_DT_EXAME" DATE, 
	"EXAM_DT_CRIACAO" TIMESTAMP (6), 
	"PACI_NR_RMR" NUMBER, 
	"USUA_ID" NUMBER, 
	"EXAM_NR_STATUS" NUMBER DEFAULT 0, 
	"MODE_ID" NUMBER, 
	"EXAME_IN_CONFIRMATORIO" NUMBER DEFAULT 0, 
	"EXAME_NR_CONFIRMATORIO" NUMBER DEFAULT 0, 
	"LABO_ID" NUMBER, 
	"EXAM_DT_COLETA_AMOSTRA" DATE, 
	"EXAM_IN_LABORATORIO_PARTICULAR" NUMBER, 
	"DOAD_ID" NUMBER, 
	"EXAM_IN_EDITADO_AVALIADOR" NUMBER, 
	"EXAM_IN_TIPO_AMOSTRA" NUMBER
   ) ;

   COMMENT ON COLUMN "EXAME"."EXAM_ID" IS 'Chave primária de exame';
   COMMENT ON COLUMN "EXAME"."EXAM_DT_EXAME" IS 'Data do exame';
   COMMENT ON COLUMN "EXAME"."EXAM_DT_CRIACAO" IS 'Data de criação do registro';
   COMMENT ON COLUMN "EXAME"."PACI_NR_RMR" IS 'Chave estrangeira relacionada ao paciente';
   COMMENT ON COLUMN "EXAME"."USUA_ID" IS 'Referencia do usuario que cadastrou';
   COMMENT ON COLUMN "EXAME"."MODE_ID" IS 'Referencia para motivo de descarte';
   COMMENT ON COLUMN "EXAME"."EXAME_IN_CONFIRMATORIO" IS '0 - tipo de exame nao confirmatorio

1- tipo de exame confirmatorio';
   COMMENT ON COLUMN "EXAME"."EXAME_NR_CONFIRMATORIO" IS '0 - tipo de exame nao confirmatorio

1- tipo de exame confirmatorio';
   COMMENT ON COLUMN "EXAME"."LABO_ID" IS 'Referencia para a tabela de laboratorios.';
   COMMENT ON COLUMN "EXAME"."EXAM_DT_COLETA_AMOSTRA" IS 'Data da coleta da amostra.';
   COMMENT ON COLUMN "EXAME"."EXAM_IN_LABORATORIO_PARTICULAR" IS '0 - não é particular. A coluna LABO_ID deverá estar preenchido; 1 - quando é particular;';
   COMMENT ON COLUMN "EXAME"."EXAM_IN_EDITADO_AVALIADOR" IS '0 - Para exame não editado e 1-  para editado';
   COMMENT ON COLUMN "EXAME"."EXAM_IN_TIPO_AMOSTRA" IS '0 - amostra sangue; 1 - amostra swab oral; 2- amostra sangue + swab oral';
   COMMENT ON TABLE "EXAME"  IS 'Tabela de exames de um paciente';
--------------------------------------------------------
--  DDL for Table EXAME_AUD
--------------------------------------------------------

  CREATE TABLE "EXAME_AUD" 
   (	"EXAM_ID" NUMBER, 
	"AUDI_ID" NUMBER, 
	"AUDI_TX_TIPO" NUMBER, 
	"EXAM_DT_CRIACAO" TIMESTAMP (6), 
	"EXAM_DT_EXAME" DATE, 
	"EXAM_NR_STATUS" NUMBER, 
	"MODE_ID" NUMBER, 
	"PACI_NR_RMR" NUMBER, 
	"USUA_ID" NUMBER, 
	"LABO_ID" NUMBER, 
	"EXAM_DT_COLETA_AMOSTRA" DATE, 
	"EXAM_IN_LABORATORIO_PARTICULAR" NUMBER, 
	"DOAD_ID" NUMBER, 
	"EXAM_IN_EDITADO_AVALIADOR" NUMBER, 
	"EXAM_IN_TIPO_AMOSTRA" NUMBER
   ) ;

   COMMENT ON COLUMN "EXAME_AUD"."EXAM_IN_TIPO_AMOSTRA" IS '0 - amostra sangue; 1 - amostra swab oral; 2- amostra sangue + swab oral';
   COMMENT ON TABLE "EXAME_AUD"  IS 'Tabela de auditoria da tabela EXAME';
--------------------------------------------------------
--  DDL for Table FONTE_CELULAS
--------------------------------------------------------

  CREATE TABLE "FONTE_CELULAS" 
   (	"FOCE_ID" NUMBER(2,0), 
	"FOCE_TX_SIGLA" VARCHAR2(5), 
	"FOCE_TX_DESCRICAO" VARCHAR2(50)
   ) ;

   COMMENT ON COLUMN "FONTE_CELULAS"."FOCE_ID" IS 'Identificador da fonte de celulas.';
   COMMENT ON COLUMN "FONTE_CELULAS"."FOCE_TX_SIGLA" IS 'Sigla da fonte de celulas.';
   COMMENT ON COLUMN "FONTE_CELULAS"."FOCE_TX_DESCRICAO" IS 'Descrição da fonte de celulas.';
   COMMENT ON TABLE "FONTE_CELULAS"  IS 'Tabela responsável por armazenar os tipos de fonte de celulas utilizadas nas coletas.';
--------------------------------------------------------
--  DDL for Table FORMULARIO
--------------------------------------------------------

  CREATE TABLE "FORMULARIO" 
   (	"FORM_ID" NUMBER, 
	"TIFO_ID" NUMBER, 
	"FORM_IN_ATIVO" NUMBER(1,0), 
	"FORM_TX_FORMATO" CLOB
   ) ;

   COMMENT ON COLUMN "FORMULARIO"."FORM_ID" IS 'Chave primária que identifica com exclusividade um formulário.';
   COMMENT ON COLUMN "FORMULARIO"."TIFO_ID" IS 'Chave estrangeira que representa o tipo do formulário.';
   COMMENT ON COLUMN "FORMULARIO"."FORM_IN_ATIVO" IS 'Informa se o formulário está ativo ou inativo.';
   COMMENT ON COLUMN "FORMULARIO"."FORM_TX_FORMATO" IS 'Armazena o formulário em formato json.';
   COMMENT ON TABLE "FORMULARIO"  IS 'Formulário';
--------------------------------------------------------
--  DDL for Table FORMULARIO_DOADOR
--------------------------------------------------------

  CREATE TABLE "FORMULARIO_DOADOR" 
   (	"FODO_ID" NUMBER, 
	"FORM_ID" NUMBER, 
	"PECO_ID" NUMBER,
	"PEWO_ID" NUMBER,
	"DOAD_ID" NUMBER, 
	"FODO_DT_RESPOSTA" TIMESTAMP (6), 
	"FODO_TX_RESPOSTAS" CLOB
   ) ;

   COMMENT ON COLUMN "FORMULARIO_DOADOR"."FODO_ID" IS 'Chave primária que identifica com exclusividade um formulário_doador.';
   COMMENT ON COLUMN "FORMULARIO_DOADOR"."FORM_ID" IS 'Chave estrangeira para o formulário.';
   COMMENT ON COLUMN "FORMULARIO_DOADOR"."PECO_ID" IS 'Chave estrangeira para o pedido de contato.';
   COMMENT ON COLUMN "FORMULARIO_DOADOR"."PEWO_ID" IS 'Chave estrangeira para o pedido de workup.';
   COMMENT ON COLUMN "FORMULARIO_DOADOR"."DOAD_ID" IS 'Chave estrangeira para o doador.';
   COMMENT ON COLUMN "FORMULARIO_DOADOR"."FODO_DT_RESPOSTA" IS 'Data em que foi respondido o formulário.';
   COMMENT ON COLUMN "FORMULARIO_DOADOR"."FODO_TX_RESPOSTAS" IS 'Formulário respondido.';
   COMMENT ON TABLE "FORMULARIO_DOADOR"  IS 'Armazena as respostas do formulário.';
--------------------------------------------------------
--  DDL for Table FUNCAO_CENTRO_TRANSPLANTE
--------------------------------------------------------

  CREATE TABLE "FUNCAO_CENTRO_TRANSPLANTE" 
   (	"CETR_ID" NUMBER, 
	"FUTR_ID" NUMBER
   ) ;

   COMMENT ON COLUMN "FUNCAO_CENTRO_TRANSPLANTE"."CETR_ID" IS 'Identificador da tabela de centro de transplante.';
   COMMENT ON COLUMN "FUNCAO_CENTRO_TRANSPLANTE"."FUTR_ID" IS 'Identificador da tabela de funcao de um centro de transplante.';
   COMMENT ON TABLE "FUNCAO_CENTRO_TRANSPLANTE"  IS 'Tabela de funções de um centro de transplante.';
--------------------------------------------------------
--  DDL for Table FUNCAO_TRANSPLANTE
--------------------------------------------------------

  CREATE TABLE "FUNCAO_TRANSPLANTE" 
   (	"FUTR_ID" NUMBER, 
	"FUTR_DESCRICAO" VARCHAR2(50)
   ) ;

   COMMENT ON COLUMN "FUNCAO_TRANSPLANTE"."FUTR_ID" IS 'Identificador da tabela.';
   COMMENT ON COLUMN "FUNCAO_TRANSPLANTE"."FUTR_DESCRICAO" IS 'Descrição da função de um centro de transplante.';
   COMMENT ON TABLE "FUNCAO_TRANSPLANTE"  IS 'Tabela de funções de um centro de transplante';
--------------------------------------------------------
--  DDL for Table GENOTIPO_BUSCA_PRELIMINAR
--------------------------------------------------------

  CREATE TABLE "GENOTIPO_BUSCA_PRELIMINAR" 
   (	"GEBP_ID" NUMBER, 
	"GEBP_TX_CODIGO_LOCUS" VARCHAR2(4), 
	"GEBP_TX_ALELO" VARCHAR2(20), 
	"GEBP_NR_POSICAO_ALELO" NUMBER(1,0), 
	"GEBP_IN_COMPOSICAO_VALOR" NUMBER(1,0), 
	"BUPR_ID" NUMBER
   ) ;

   COMMENT ON COLUMN "GENOTIPO_BUSCA_PRELIMINAR"."GEBP_ID" IS 'Sequence identificador da tabela.';
   COMMENT ON COLUMN "GENOTIPO_BUSCA_PRELIMINAR"."GEBP_TX_CODIGO_LOCUS" IS 'Código do locus.';
   COMMENT ON COLUMN "GENOTIPO_BUSCA_PRELIMINAR"."GEBP_TX_ALELO" IS 'Valor do alelo correspondente.';
   COMMENT ON COLUMN "GENOTIPO_BUSCA_PRELIMINAR"."GEBP_NR_POSICAO_ALELO" IS 'Posição do valor alélico indicado no momento do cadastro.';
   COMMENT ON COLUMN "GENOTIPO_BUSCA_PRELIMINAR"."GEBP_IN_COMPOSICAO_VALOR" IS 'Composição do valor alélico. As possibilidades são: SOROLOGICO(0), ANTIGENO(1), NMDP(2), GRUPO_G(3), GRUPO_P(4), ALELO(5).';
   COMMENT ON COLUMN "GENOTIPO_BUSCA_PRELIMINAR"."BUPR_ID" IS 'Identificador (FK) da tabela de busca preliminar. Indica qual o paciente está associado os valores inseridos.';
   COMMENT ON TABLE "GENOTIPO_BUSCA_PRELIMINAR"  IS 'Tabela que registra os valores do genótipo reduzidos e expandidos para os valores válidos, em caso de agrupamentos como P, G e NNMDP.';
--------------------------------------------------------
--  DDL for Table GENOTIPO_DOADOR
--------------------------------------------------------

  CREATE TABLE "GENOTIPO_DOADOR" 
   (	"GEDO_ID" NUMBER, 
	"GEDO_DT_ALTERACAO" TIMESTAMP (6), 
	"GEDO_IN_EXCLUIDO" NUMBER(1,0) DEFAULT 0, 
	"DOAD_ID" NUMBER, 
	"GEDO_IN_TIPO_DOADOR" NUMBER(1,0)
   ) ;

   COMMENT ON COLUMN "GENOTIPO_DOADOR"."GEDO_ID" IS 'Identificador do genotipo de doador';
   COMMENT ON COLUMN "GENOTIPO_DOADOR"."GEDO_DT_ALTERACAO" IS 'Data de alteração do genotipo';
   COMMENT ON COLUMN "GENOTIPO_DOADOR"."GEDO_IN_EXCLUIDO" IS 'Se genotipo foi excluido ou não';
   COMMENT ON COLUMN "GENOTIPO_DOADOR"."DOAD_ID" IS 'Chave estrangeira de doador';
   COMMENT ON COLUMN "GENOTIPO_DOADOR"."GEDO_IN_TIPO_DOADOR" IS 'Identifica o tipo de doador. 0 - Doador Nacional 1 - Doador Internacional 2 - Cordão Nacional 3 Cordão Internacional.';
   COMMENT ON TABLE "GENOTIPO_DOADOR"  IS 'Tabela de genotipo de doadores';
--------------------------------------------------------
--  DDL for Table GENOTIPO_EXPANDIDO_PRELIMINAR
--------------------------------------------------------

  CREATE TABLE "GENOTIPO_EXPANDIDO_PRELIMINAR" 
   (	"GEEP_ID" NUMBER, 
	"GEEP_TX_CODIGO_LOCUS" VARCHAR2(4), 
	"GEEP_NR_ALELO" NUMBER, 
	"GEEP_NR_POSICAO_ALELO" NUMBER(1,0), 
	"BUPR_ID" NUMBER
   ) ;

   COMMENT ON COLUMN "GENOTIPO_EXPANDIDO_PRELIMINAR"."GEEP_ID" IS 'Sequence identificador da tabela.';
   COMMENT ON COLUMN "GENOTIPO_EXPANDIDO_PRELIMINAR"."GEEP_TX_CODIGO_LOCUS" IS 'Código do locus.';
   COMMENT ON COLUMN "GENOTIPO_EXPANDIDO_PRELIMINAR"."GEEP_NR_ALELO" IS 'Valor do alelo correspondente.';
   COMMENT ON COLUMN "GENOTIPO_EXPANDIDO_PRELIMINAR"."GEEP_NR_POSICAO_ALELO" IS 'Posição do valor alélico indicado no momento do cadastro.';
   COMMENT ON COLUMN "GENOTIPO_EXPANDIDO_PRELIMINAR"."BUPR_ID" IS 'Identificador (FK) da tabela de busca preliminar. Indica qual o paciente está associado os valores inseridos.';
   COMMENT ON TABLE "GENOTIPO_EXPANDIDO_PRELIMINAR"  IS 'Tabela que registra os valores do genótipo expandidos, ou seja, extraídos os valores de grupamento G, P, NMDP e separados em grupo alélico e valor alélico.';
--------------------------------------------------------
--  DDL for Table GENOTIPO_PACIENTE
--------------------------------------------------------

  CREATE TABLE "GENOTIPO_PACIENTE" 
   (	"GEPA_ID" NUMBER, 
	"GEPA_DT_ALTERACAO" TIMESTAMP (6), 
	"GEPA_IN_EXCLUIDO" NUMBER(1,0) DEFAULT 0, 
	"PACI_NR_RMR" NUMBER
   ) ;

   COMMENT ON COLUMN "GENOTIPO_PACIENTE"."GEPA_ID" IS 'Chave primária da tabela de genótipo';
   COMMENT ON COLUMN "GENOTIPO_PACIENTE"."GEPA_DT_ALTERACAO" IS 'Data de geração do genótipo';
   COMMENT ON COLUMN "GENOTIPO_PACIENTE"."GEPA_IN_EXCLUIDO" IS 'Se registro foi excluido';
   COMMENT ON COLUMN "GENOTIPO_PACIENTE"."PACI_NR_RMR" IS 'Identificador do paciente';
   COMMENT ON TABLE "GENOTIPO_PACIENTE"  IS 'Tabela de pai de genótpo ';
--------------------------------------------------------
--  DDL for Table GENOTIPO_PRELIMINAR
--------------------------------------------------------

  CREATE TABLE "GENOTIPO_PRELIMINAR" 
   (	"GEPR_ID" NUMBER, 
	"GEPR_TX_CODIGO_LOCUS" VARCHAR2(4), 
	"GEPR_TX_ALELO" VARCHAR2(20), 
	"GEPR_NR_POSICAO_ALELO" NUMBER(1,0), 
	"BUPR_ID" NUMBER, 
	"GEPR_IN_COMPOSICAO_VALOR" NUMBER
   ) ;

   COMMENT ON COLUMN "GENOTIPO_PRELIMINAR"."GEPR_ID" IS 'Sequence identificador da tabela.';
   COMMENT ON COLUMN "GENOTIPO_PRELIMINAR"."GEPR_TX_CODIGO_LOCUS" IS 'Código do locus.';
   COMMENT ON COLUMN "GENOTIPO_PRELIMINAR"."GEPR_TX_ALELO" IS 'Valor do alélico.';
   COMMENT ON COLUMN "GENOTIPO_PRELIMINAR"."GEPR_NR_POSICAO_ALELO" IS 'Posição do valor alélico indicado no momento do cadastro.';
   COMMENT ON COLUMN "GENOTIPO_PRELIMINAR"."BUPR_ID" IS 'Identificador (FK) da tabela de busca preliminar. Indica quem é a quem está associado os valores inseridos.';
   COMMENT ON COLUMN "GENOTIPO_PRELIMINAR"."GEPR_IN_COMPOSICAO_VALOR" IS 'Coluna que define a composição do valor, se Alelo, NMDP, Grupo P, Grupo G, Sorologico ou Antígeno.';
   COMMENT ON TABLE "GENOTIPO_PRELIMINAR"  IS 'Tabela que registra os valores do genótipo do paciente consultado.';
--------------------------------------------------------
--  DDL for Table HEMO_ENTIDADE
--------------------------------------------------------

  CREATE TABLE "HEMO_ENTIDADE" 
   (	"HEEN_ID" NUMBER, 
	"HEEN_TX_NOME" VARCHAR2(255), 
	"HEEN_IN_SELECIONAVEL" NUMBER, 
	"HEEN_TX_CLASSIFICACAO" VARCHAR2(2), 
	"HEEN_ID_HEMOCENTRO_REDOMEWEB" NUMBER, 
	"HEEN_ID_HEMOCENTRO" NUMBER
   ) ;

   COMMENT ON COLUMN "HEMO_ENTIDADE"."HEEN_ID" IS 'Identificador sequencial para cada entidade.';
   COMMENT ON COLUMN "HEMO_ENTIDADE"."HEEN_TX_NOME" IS 'Nome da entidade.';
   COMMENT ON COLUMN "HEMO_ENTIDADE"."HEEN_IN_SELECIONAVEL" IS 'Indica se a entidade é selecionável como receptora da amostra sanguínea do paciente ou doador.';
   COMMENT ON COLUMN "HEMO_ENTIDADE"."HEEN_TX_CLASSIFICACAO" IS 'Classificação da entidade. HC-Hemocentro, HN-Hemonúcleo.';
   COMMENT ON COLUMN "HEMO_ENTIDADE"."HEEN_ID_HEMOCENTRO_REDOMEWEB" IS 'Referência para o hemocentro do redomeweb.';
   COMMENT ON COLUMN "HEMO_ENTIDADE"."HEEN_ID_HEMOCENTRO" IS 'Referência circular para o obter o hemocentro (pai) do hemonúcleo.';
   COMMENT ON TABLE "HEMO_ENTIDADE"  IS 'Tabela de informações sobre os hemocentros e hemonúcleos disponíveis no Redome.';
--------------------------------------------------------
--  DDL for Table HISTORICO_BUSCA
--------------------------------------------------------

  CREATE TABLE "HISTORICO_BUSCA" 
   (	"HIBU_ID" NUMBER, 
	"HIBU_DT_ATUALIZACAO" TIMESTAMP (6), 
	"USUA_ID" NUMBER, 
	"CETR_ID" NUMBER, 
	"BUSC_ID" NUMBER, 
	"HIBU_TX_JUSTIFICATIVA" VARCHAR2(200)
   ) ;

   COMMENT ON COLUMN "HISTORICO_BUSCA"."HIBU_ID" IS 'Identificador da tabela';
   COMMENT ON COLUMN "HISTORICO_BUSCA"."HIBU_DT_ATUALIZACAO" IS 'Data que ocorreu a atualização na busca.';
   COMMENT ON COLUMN "HISTORICO_BUSCA"."USUA_ID" IS 'Usuário que realizou a atualização na busca.';
   COMMENT ON COLUMN "HISTORICO_BUSCA"."CETR_ID" IS 'Centro transplantador que estava selecionado como responsável pela busca.';
   COMMENT ON COLUMN "HISTORICO_BUSCA"."BUSC_ID" IS 'Identificador da busca que sofreu a atualização.';
   COMMENT ON COLUMN "HISTORICO_BUSCA"."HIBU_TX_JUSTIFICATIVA" IS 'Texto com a justificativa da ocorrência de atualização.';
   COMMENT ON TABLE "HISTORICO_BUSCA"  IS 'Tabela com o histórico da busca (quando o CT recusa realizar o procedimento).';
--------------------------------------------------------
--  DDL for Table HISTORICO_STATUS_PACIENTE
--------------------------------------------------------

  CREATE TABLE "HISTORICO_STATUS_PACIENTE" 
   (	"HISP_ID" NUMBER, 
	"STPA_ID" NUMBER, 
	"HISP_DT_ALTERACAO" TIMESTAMP (6), 
	"PACI_NR_RMR" NUMBER
   ) ;

   COMMENT ON COLUMN "HISTORICO_STATUS_PACIENTE"."HISP_ID" IS 'Identificação de histórico de status de paciente';
   COMMENT ON COLUMN "HISTORICO_STATUS_PACIENTE"."STPA_ID" IS 'Referência de status de paciente';
   COMMENT ON COLUMN "HISTORICO_STATUS_PACIENTE"."HISP_DT_ALTERACAO" IS 'Data de alteração do status';
   COMMENT ON COLUMN "HISTORICO_STATUS_PACIENTE"."PACI_NR_RMR" IS 'Referencia de paciente';
   COMMENT ON TABLE "HISTORICO_STATUS_PACIENTE"  IS 'Armazena data de alteração de status de paciente ';
--------------------------------------------------------
--  DDL for Table IDIOMA
--------------------------------------------------------

  CREATE TABLE "IDIOMA" 
   (	"IDIO_ID" NUMBER(1,0), 
	"IDIO_TX_SIGLA" VARCHAR2(2)
   ) ;

   COMMENT ON COLUMN "IDIOMA"."IDIO_ID" IS 'Identificador do idioma';
   COMMENT ON COLUMN "IDIOMA"."IDIO_TX_SIGLA" IS 'Sigla do idioma';
   COMMENT ON TABLE "IDIOMA"  IS 'Tabela com os idiomas suportados pelo sistema.';
--------------------------------------------------------
--  DDL for Table INFO_PREVIA
--------------------------------------------------------

  CREATE TABLE "INFO_PREVIA" 
   (	"INPR_ID" NUMBER, 
	"PEWO_ID" NUMBER, 
	"INPR_NR_INFO_CORRENTE" NUMBER, 
	"INPR_TX_DESCRICAO" VARCHAR2(80)
   ) ;

   COMMENT ON COLUMN "INFO_PREVIA"."INPR_ID" IS 'Chave primária da tabela de info previa.';
   COMMENT ON COLUMN "INFO_PREVIA"."PEWO_ID" IS 'Chave estrangeira da tabela de pedido_workup';
   COMMENT ON COLUMN "INFO_PREVIA"."INPR_NR_INFO_CORRENTE" IS 'Número referente ao grupo de informações prévias de um pedido de workup.';
   COMMENT ON COLUMN "INFO_PREVIA"."INPR_TX_DESCRICAO" IS 'Descrição da info previa';
   COMMENT ON TABLE "INFO_PREVIA"  IS 'Tabela que vai armazenar as informações de possíveis datas disponíveis de um centro de transplante.';
--------------------------------------------------------
--  DDL for Table INSTRUCAO_COLETA
--------------------------------------------------------

  CREATE TABLE "INSTRUCAO_COLETA" 
   (	"INCO_ID" NUMBER, 
	"INCO_TX_DESCRICAO" VARCHAR2(2000), 
	"LABO_ID" NUMBER, 
	"INCO_IN_TIPO_INSTRUCAO" VARCHAR2(5)
   ) ;

   COMMENT ON COLUMN "INSTRUCAO_COLETA"."INCO_ID" IS 'Chave primária.';
   COMMENT ON COLUMN "INSTRUCAO_COLETA"."INCO_TX_DESCRICAO" IS 'Descrição da instrução.';
   COMMENT ON COLUMN "INSTRUCAO_COLETA"."LABO_ID" IS 'Identificador para a tabela de laboratório.';
   COMMENT ON COLUMN "INSTRUCAO_COLETA"."INCO_IN_TIPO_INSTRUCAO" IS 'Flag que indica para qual finalidade a instrução deve ser aplicada. EXAME - Para pedido de exame e SWAB - para pedido de exame com swab.';
   COMMENT ON TABLE "INSTRUCAO_COLETA"  IS 'Tabela referente ais instruções de coleta.';
--------------------------------------------------------
--  DDL for Table ITENS_CHECKLIST
--------------------------------------------------------

  CREATE TABLE "ITENS_CHECKLIST" 
   (	"ITEC_ID" NUMBER, 
	"ITEC_TX_NM_ITEM" VARCHAR2(60), 
	"CACH_ID" NUMBER
   ) ;

   COMMENT ON COLUMN "ITENS_CHECKLIST"."ITEC_ID" IS 'Id sequencial do item de checklist';
   COMMENT ON COLUMN "ITENS_CHECKLIST"."ITEC_TX_NM_ITEM" IS 'Texto do item de checklist';
   COMMENT ON COLUMN "ITENS_CHECKLIST"."CACH_ID" IS 'Id da categoria de checklist';
--------------------------------------------------------
--  DDL for Table LABORATORIO
--------------------------------------------------------

  CREATE TABLE "LABORATORIO" 
   (	"LABO_ID" NUMBER, 
	"LABO_TX_NOME" VARCHAR2(255), 
	"LABO_IN_FAZ_CT" NUMBER, 
	"LABO_NR_QUANT_EXAME_CT" NUMBER, 
	"LABO_TX_NOME_CONTATO" VARCHAR2(100), 
	"LABO_NR_ID_REDOMEWEB" NUMBER
   ) ;

   COMMENT ON COLUMN "LABORATORIO"."LABO_ID" IS 'Chave primária da tabela de laboratório';
   COMMENT ON COLUMN "LABORATORIO"."LABO_TX_NOME" IS 'Nome do laboratório';
   COMMENT ON COLUMN "LABORATORIO"."LABO_IN_FAZ_CT" IS 'Informa se o laboratório faz exame de ct.';
   COMMENT ON COLUMN "LABORATORIO"."LABO_TX_NOME_CONTATO" IS 'Nome da pessoa de contato.';
   COMMENT ON COLUMN "LABORATORIO"."LABO_NR_ID_REDOMEWEB" IS 'Identificador do laboratório na base do REDOMEWeb (integração).';
   COMMENT ON TABLE "LABORATORIO"  IS 'Tabela de laboratórios cadastrados';
--------------------------------------------------------
--  DDL for Table LOCALIDADE_CORREIO
--------------------------------------------------------

  CREATE TABLE "LOCALIDADE_CORREIO" 
   (	"LOCC_ID" NUMBER, 
	"LOCC_TX_NOME" VARCHAR2(72), 
	"LOCC_TX_CEP" VARCHAR2(8), 
	"LOCC_NR_CODIGO_IBGE" VARCHAR2(7), 
	"LOCC_IN_TIPO" VARCHAR2(1), 
	"LOCC_ID_SUBORDINACAO" NUMBER, 
	"UNFC_ID" NUMBER, 
	"LOCC_TX_DNE" VARCHAR2(8)
   ) ;

   COMMENT ON COLUMN "LOCALIDADE_CORREIO"."LOCC_ID" IS 'Identificador do Registro';
   COMMENT ON COLUMN "LOCALIDADE_CORREIO"."LOCC_TX_NOME" IS 'Nome Oficial da Localidade';
   COMMENT ON COLUMN "LOCALIDADE_CORREIO"."LOCC_TX_CEP" IS 'CEP da Localidade';
   COMMENT ON COLUMN "LOCALIDADE_CORREIO"."LOCC_NR_CODIGO_IBGE" IS 'Código IBGE da Localidade';
   COMMENT ON COLUMN "LOCALIDADE_CORREIO"."LOCC_IN_TIPO" IS 'Tipo da Localidade (D->Distrito, M->Município, P->Povoado, R->Região Administrativa)';
   COMMENT ON COLUMN "LOCALIDADE_CORREIO"."LOCC_ID_SUBORDINACAO" IS 'Chave que permite localizar os dados do município ao qual o distrito/povoado pertence.';
   COMMENT ON COLUMN "LOCALIDADE_CORREIO"."UNFC_ID" IS 'ID da tabela Unidade Federativa';
   COMMENT ON COLUMN "LOCALIDADE_CORREIO"."LOCC_TX_DNE" IS 'Chave da Localidade no DNE';
   COMMENT ON TABLE "LOCALIDADE_CORREIO"  IS 'Tabela de Localidades(Municípios)';
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
--------------------------------------------------------
--  DDL for Table LOG_EVOLUCAO
--------------------------------------------------------

  CREATE TABLE "LOG_EVOLUCAO" 
   (	"LOEV_ID" NUMBER, 
	"USUA_ID" NUMBER, 
	"LOEV_IN_TIPO_EVENTO" VARCHAR2(255), 
	"PACI_NR_RMR" NUMBER, 
	"LOEV_DT_DATA" TIMESTAMP (6), 
	"LOEV_TX_PARAMETROS" VARCHAR2(255)
   ) ;

   COMMENT ON COLUMN "LOG_EVOLUCAO"."LOEV_ID" IS 'Chave primária da tabela de log de evoluções.';
   COMMENT ON COLUMN "LOG_EVOLUCAO"."USUA_ID" IS 'Indica o usuário que realizou a inclusão de novo log.';
   COMMENT ON COLUMN "LOG_EVOLUCAO"."LOEV_IN_TIPO_EVENTO" IS 'Tipo de evento ocorrido no log. Representa um enum no sistema, indicando a mensagem a ser exibida.';
   COMMENT ON COLUMN "LOG_EVOLUCAO"."LOEV_DT_DATA" IS 'Data que ocorreu o evento (inclusão)';
   COMMENT ON TABLE "LOG_EVOLUCAO"  IS 'Tabela de contendo o log de evoluções do paciente (podendo ser extendida para outras evoluções, como doador, por exemplo).';
--------------------------------------------------------
--  DDL for Table LOGRADOURO_CORREIO
--------------------------------------------------------

  CREATE TABLE "LOGRADOURO_CORREIO" 
   (	"LOGC_ID" NUMBER, 
	"LOGC_TX_NOME" VARCHAR2(72), 
	"LOGC_TX_CEP" VARCHAR2(8), 
	"TILC_ID" NUMBER, 
	"BACO_ID_INICIAL" NUMBER, 
	"BACO_ID_FINAL" NUMBER, 
	"LOGC_TX_CHAVE_DNE" VARCHAR2(8)
   ) ;

   COMMENT ON COLUMN "LOGRADOURO_CORREIO"."LOGC_ID" IS 'Número sequencial';
   COMMENT ON COLUMN "LOGRADOURO_CORREIO"."LOGC_TX_NOME" IS 'Nome Oficial do LOGRADOURO_CORREIO';
   COMMENT ON COLUMN "LOGRADOURO_CORREIO"."LOGC_TX_CEP" IS 'CEP do LOGRADOURO_CORREIO';
   COMMENT ON COLUMN "LOGRADOURO_CORREIO"."TILC_ID" IS 'ID do Tipo LOGRADOURO_CORREIO';
   COMMENT ON COLUMN "LOGRADOURO_CORREIO"."BACO_ID_INICIAL" IS 'ID do BAIRRO_CORREIO Inicial';
   COMMENT ON COLUMN "LOGRADOURO_CORREIO"."BACO_ID_FINAL" IS 'ID do BAIRRO_CORREIO final';
   COMMENT ON COLUMN "LOGRADOURO_CORREIO"."LOGC_TX_CHAVE_DNE" IS 'Chave do logradouro no DNE';
   COMMENT ON TABLE "LOGRADOURO_CORREIO"  IS 'Tabela de LOGRADOURO_CORREIOs';
--------------------------------------------------------
--  DDL for Table MATCH
--------------------------------------------------------



CREATE TABLE MATCH 
(
  MATC_ID NUMBER NOT NULL 
, MATC_IN_STATUS NUMBER DEFAULT 0 NOT NULL 
, BUSC_ID NUMBER NOT NULL 
, DOAD_ID NUMBER NOT NULL 
, MATC_TX_GRADE VARCHAR2(5 BYTE) 
, MATC_TX_MISMATCH VARCHAR2(20 BYTE) 
, MATC_TX_GRADE_ANTES VARCHAR2(5 BYTE) 
, MATC_TX_MISMATCH_ANTES VARCHAR2(20 BYTE) 
, MATC_DT_MATCH DATE 
, MATC_DT_ALTERACAO DATE 
, TIPM_ID_DPB1 NUMBER 
, MATC_IN_DISPONIBILIZADO NUMBER DEFAULT 0 
, MATC_NR_ORDENACAO_WMDA NUMBER
, MATC_TX_PROBABILIDADE0 VARCHAR2(5 BYTE)
, MATC_TX_PROBABILIDADE1 VARCHAR2(5 BYTE)
, MATC_TX_PROBABILIDADE2 VARCHAR2(5 BYTE)
);

COMMENT ON TABLE MATCH IS 'Tabela de matchs de um paciente';

COMMENT ON COLUMN MATCH.MATC_ID IS 'Chave primária da tabela de match';

COMMENT ON COLUMN MATCH.MATC_IN_STATUS IS '0- ABERTO 
1 - FECHADO';

COMMENT ON COLUMN MATCH.BUSC_ID IS 'Chave estrangeira  da tabela de busca';

COMMENT ON COLUMN MATCH.DOAD_ID IS 'Chave estrangeira do doador';

COMMENT ON COLUMN MATCH.MATC_TX_GRADE IS 'Match grade ex: "05/06", "06/06"';

COMMENT ON COLUMN MATCH.MATC_TX_MISMATCH IS 'Informa os locus onde estão os mismatchs';

COMMENT ON COLUMN MATCH.MATC_TX_GRADE_ANTES IS 'Match grade anterior ex: "05/06", "06/06"';

COMMENT ON COLUMN MATCH.MATC_TX_MISMATCH_ANTES IS 'Locus onde estavam os mismatchs anteriormente';

COMMENT ON COLUMN MATCH.MATC_DT_MATCH IS 'Data em que o primeiro match foi executado';

COMMENT ON COLUMN MATCH.MATC_DT_ALTERACAO IS 'Data em que o match foi atualizado';

COMMENT ON COLUMN MATCH.MATC_IN_DISPONIBILIZADO IS 'Informa que o doador foi disponibilizado para o centro de transplante.';

--------------------------------------------------------
--  DDL for Table MATCH_PRELIMINAR
--------------------------------------------------------

  CREATE TABLE "MATCH_PRELIMINAR" 
   (	"MAPR_ID" NUMBER, 
	"DOAD_ID" NUMBER, 
	"BUPR_ID" NUMBER, 
	"MAPR_IN_FASE" VARCHAR2(2)
   ) ;

   COMMENT ON COLUMN "MATCH_PRELIMINAR"."MAPR_ID" IS 'Sequence identificador da tabela.';
   COMMENT ON COLUMN "MATCH_PRELIMINAR"."DOAD_ID" IS 'Doador (para o cadastro preliminar, somente os nacionais) que ocorreu match.';
   COMMENT ON COLUMN "MATCH_PRELIMINAR"."BUPR_ID" IS 'Identificador (FK) da tabela de busca preliminar. Indica qual o paciente está associado os valores inseridos.';
   COMMENT ON COLUMN "MATCH_PRELIMINAR"."MAPR_IN_FASE" IS 'Em que fase encontra-se o match.';
   COMMENT ON TABLE "MATCH_PRELIMINAR"  IS 'Tabela que matchs ocorridos após a inclusão da busca preliminar.';
--------------------------------------------------------
--  DDL for Table MEDICO
--------------------------------------------------------

  CREATE TABLE "MEDICO" 
   (	"MEDI_ID" NUMBER, 
	"MEDI_TX_CRM" VARCHAR2(20), 
	"MEDI_TX_NOME" VARCHAR2(255), 
	"USUA_ID" NUMBER, 
	"CETR_ID" NUMBER, 
	"MEDI_TX_ARQUIVO_CRM" VARCHAR2(255)
   ) ;

   COMMENT ON COLUMN "MEDICO"."MEDI_ID" IS 'Chave primária do médico';
   COMMENT ON COLUMN "MEDICO"."MEDI_TX_CRM" IS 'Número do CRM do médico';
   COMMENT ON COLUMN "MEDICO"."MEDI_TX_NOME" IS 'Nome do médico';
   COMMENT ON COLUMN "MEDICO"."USUA_ID" IS 'Chave estrangeira relacionada ao usuário';
   COMMENT ON COLUMN "MEDICO"."CETR_ID" IS 'Chave estrangeira do centro_transplante';
   COMMENT ON COLUMN "MEDICO"."MEDI_TX_ARQUIVO_CRM" IS 'URL que aponta para o arquivo CRM, que o médico informou no pré cadastro, para fins de validação do acesso.';
   COMMENT ON TABLE "MEDICO"  IS 'Tabela de médicos';
--------------------------------------------------------
--  DDL for Table MEDICO_CT_REFERENCIA
--------------------------------------------------------

  CREATE TABLE "MEDICO_CT_REFERENCIA" 
   (	"MEDI_ID" NUMBER, 
	"CETR_ID" NUMBER
   ) ;

   COMMENT ON COLUMN "MEDICO_CT_REFERENCIA"."MEDI_ID" IS 'Identificação da tabela médico.';
   COMMENT ON COLUMN "MEDICO_CT_REFERENCIA"."CETR_ID" IS 'Identificação da tabela centro transplante.';
   COMMENT ON TABLE "MEDICO_CT_REFERENCIA"  IS 'Tabela de relacionamento entre o médico e os centros de referência que ele atua (centros avaliadores).';
--------------------------------------------------------
--  DDL for Table METODOLOGIA
--------------------------------------------------------

  CREATE TABLE "METODOLOGIA" 
   (	"METO_ID" NUMBER, 
	"METO_TX_SIGLA" VARCHAR2(5), 
	"METO_TX_DESCRICAO" VARCHAR2(40), 
	"METO_NR_PESO_GENOTIPO" NUMBER
   ) ;

   COMMENT ON COLUMN "METODOLOGIA"."METO_ID" IS 'Chave primária de metodologia';
   COMMENT ON COLUMN "METODOLOGIA"."METO_TX_SIGLA" IS 'Sigla da metodologia';
   COMMENT ON COLUMN "METODOLOGIA"."METO_TX_DESCRICAO" IS 'Descrição da metodologia';
   COMMENT ON COLUMN "METODOLOGIA"."METO_NR_PESO_GENOTIPO" IS 'Valor para pesar no momento da escolha em caso de empate em genótipo
';
   COMMENT ON TABLE "METODOLOGIA"  IS 'Tabela de domínio de metodologias';
--------------------------------------------------------
--  DDL for Table METODOLOGIA_EXAME
--------------------------------------------------------

  CREATE TABLE "METODOLOGIA_EXAME" 
   (	"EXAM_ID" NUMBER, 
	"METO_ID" NUMBER
   ) ;

   COMMENT ON COLUMN "METODOLOGIA_EXAME"."EXAM_ID" IS 'Chave estrangeira para exame';
   COMMENT ON COLUMN "METODOLOGIA_EXAME"."METO_ID" IS 'Chave estrangeira pra metodologia';
   COMMENT ON TABLE "METODOLOGIA_EXAME"  IS 'Tabela de relacionamento entre metodologia e exame';
--------------------------------------------------------
--  DDL for Table METODOLOGIA_EXAME_AUD
--------------------------------------------------------

  CREATE TABLE "METODOLOGIA_EXAME_AUD" 
   (	"AUDI_ID" NUMBER, 
	"EXAM_ID" NUMBER, 
	"METO_ID" NUMBER, 
	"AUDI_TX_TIPO" NUMBER
   ) ;

   COMMENT ON TABLE "METODOLOGIA_EXAME_AUD"  IS 'Tabela de auditoria da tabela METODOLOGIA_EXAME';
--------------------------------------------------------
--  DDL for Table MOTIVO
--------------------------------------------------------

  CREATE TABLE "MOTIVO" 
   (	"MOTI_ID" NUMBER, 
	"MOTI_TX_DESCRICAO" VARCHAR2(40)
   ) ;

   COMMENT ON COLUMN "MOTIVO"."MOTI_ID" IS 'Chave primária da tabela de motivo';
   COMMENT ON COLUMN "MOTIVO"."MOTI_TX_DESCRICAO" IS 'Descrição do motivo';
   COMMENT ON TABLE "MOTIVO"  IS 'Tabela de domínio de motivos da evolução da doença';
--------------------------------------------------------
--  DDL for Table MOTIVO_CANCELAMENTO_BUSCA
--------------------------------------------------------

  CREATE TABLE "MOTIVO_CANCELAMENTO_BUSCA" 
   (	"MOCB_ID" NUMBER, 
	"MOCB_TX_DESCRICAO" VARCHAR2(80), 
	"MOCB_IN_DESC_OBRIGATORIO" NUMBER(1,0) DEFAULT 0
   ) ;

   COMMENT ON COLUMN "MOTIVO_CANCELAMENTO_BUSCA"."MOCB_ID" IS 'Chave primária de motivo de cancelamento de uma busca';
   COMMENT ON COLUMN "MOTIVO_CANCELAMENTO_BUSCA"."MOCB_TX_DESCRICAO" IS 'Descricao do motivo de cancelamento';
   COMMENT ON COLUMN "MOTIVO_CANCELAMENTO_BUSCA"."MOCB_IN_DESC_OBRIGATORIO" IS 'Coluna que define se o campo descricao é obrigatório.';
   COMMENT ON TABLE "MOTIVO_CANCELAMENTO_BUSCA"  IS 'Tabela de motivos de cancelamento de busca';
--------------------------------------------------------
--  DDL for Table MOTIVO_CANCELAMENTO_COLETA
--------------------------------------------------------

  CREATE TABLE "MOTIVO_CANCELAMENTO_COLETA" 
   (	"MOCC_TX_CODIGO" VARCHAR2(50), 
	"MOCC_DESCRICAO" VARCHAR2(200)
   ) ;

   COMMENT ON COLUMN "MOTIVO_CANCELAMENTO_COLETA"."MOCC_TX_CODIGO" IS 'Chave primária da tabela. Existe um enum representando no back-end.';
   COMMENT ON COLUMN "MOTIVO_CANCELAMENTO_COLETA"."MOCC_DESCRICAO" IS 'Descrição do motivo de cancelamento de um pedido de coleta.';
   COMMENT ON TABLE "MOTIVO_CANCELAMENTO_COLETA"  IS 'Tabela de motivos para o cancelamento de um pedido de coleta.';
--------------------------------------------------------
--  DDL for Table MOTIVO_CANCELAMENTO_WORKUP
--------------------------------------------------------

  CREATE TABLE "MOTIVO_CANCELAMENTO_WORKUP" 
   (	"MOCW_ID" NUMBER, 
	"MOCW_DESCRICAO" VARCHAR2(60), 
	"MOCW_IN_SELECIONAVEL" NUMBER(1,0) DEFAULT 1
   ) ;

   COMMENT ON COLUMN "MOTIVO_CANCELAMENTO_WORKUP"."MOCW_ID" IS 'Chave primária da tabela de movo de cancelamento.';
   COMMENT ON COLUMN "MOTIVO_CANCELAMENTO_WORKUP"."MOCW_DESCRICAO" IS 'Descrição do motivo de cancelamento de um pedido de workup.';
   COMMENT ON COLUMN "MOTIVO_CANCELAMENTO_WORKUP"."MOCW_IN_SELECIONAVEL" IS 'Indica se o registro poderá ser selecionado na aplicação.';
   COMMENT ON TABLE "MOTIVO_CANCELAMENTO_WORKUP"  IS 'Tabela de motivos de cancelamento de um workup';
--------------------------------------------------------
--  DDL for Table MOTIVO_DESCARTE
--------------------------------------------------------

  CREATE TABLE "MOTIVO_DESCARTE" 
   (	"MODE_ID" NUMBER, 
	"MODE_TX_DESCRICAO" VARCHAR2(100)
   ) ;

   COMMENT ON COLUMN "MOTIVO_DESCARTE"."MODE_ID" IS 'Identificação do motivo de descarte';
   COMMENT ON COLUMN "MOTIVO_DESCARTE"."MODE_TX_DESCRICAO" IS 'Descrição do motivo de descarte';
   COMMENT ON TABLE "MOTIVO_DESCARTE"  IS 'Tabela que armazena os motivos de descarte dos exames';
--------------------------------------------------------
--  DDL for Table MOTIVO_STATUS_DOADOR
--------------------------------------------------------

CREATE TABLE "MOTIVO_STATUS_DOADOR" 
(
  "MOSD_ID" NUMBER NOT NULL 
, "MOSD_TX_DESCRICAO" VARCHAR2(40 BYTE) NOT NULL 
, "STDO_ID" NUMBER NOT NULL 
, "MOSD_IN_ACAO_DOADOR_INATIVO" NUMBER
);

   COMMENT ON COLUMN "MOTIVO_STATUS_DOADOR"."MOSD_ID" IS 'Identificação do motivo status doador';
   COMMENT ON COLUMN "MOTIVO_STATUS_DOADOR"."STDO_ID" IS 'Id do status doador';
   COMMENT ON TABLE "MOTIVO_STATUS_DOADOR"  IS 'Motivo Status Doador';
--------------------------------------------------------
--  DDL for Table MOTIVO_STATUS_DOADOR_RECURSO
--------------------------------------------------------

  CREATE TABLE "MOTIVO_STATUS_DOADOR_RECURSO" 
   (	"MOSD_ID" NUMBER, 
	"RECU_ID" NUMBER
   ) ;

   COMMENT ON COLUMN "MOTIVO_STATUS_DOADOR_RECURSO"."MOSD_ID" IS 'Id do Motivo Status Doador';
   COMMENT ON COLUMN "MOTIVO_STATUS_DOADOR_RECURSO"."RECU_ID" IS 'Id do Recurso';
   COMMENT ON TABLE "MOTIVO_STATUS_DOADOR_RECURSO"  IS 'Relação entre motivo status doador e recurso';
--------------------------------------------------------
--  DDL for Table NOTIFICACAO
--------------------------------------------------------

  CREATE TABLE "NOTIFICACAO" 
   (	"NOTI_ID" NUMBER, 
	"PACI_NR_RMR" NUMBER, 
	"NOTI_TX_DESCRICAO" VARCHAR2(255), 
	"CANO_ID" NUMBER, 
	"NOTI_IN_LIDO" NUMBER(1,0) DEFAULT 0, 
	"NOTI_DT_CRIACAO" TIMESTAMP (6), 
	"USUA_ID" NUMBER, 
	"NOTI_DT_LIDO" TIMESTAMP (6), 
	"NOTI_ID_PARCEIRO" NUMBER
   ) ;

   COMMENT ON COLUMN "NOTIFICACAO"."NOTI_ID" IS 'Chave primária de notificicação';
   COMMENT ON COLUMN "NOTIFICACAO"."PACI_NR_RMR" IS 'RMR do paciente';
   COMMENT ON COLUMN "NOTIFICACAO"."NOTI_TX_DESCRICAO" IS 'Descrição da notificação';
   COMMENT ON COLUMN "NOTIFICACAO"."CANO_ID" IS 'Referência para a tabela de categoria da notificação';
   COMMENT ON COLUMN "NOTIFICACAO"."NOTI_IN_LIDO" IS 'Indica se a notificação já foi lida (1-TRUE) ou não (0-FALSE)';
   COMMENT ON COLUMN "NOTIFICACAO"."NOTI_DT_CRIACAO" IS 'Data de criação da notificação.';
   COMMENT ON COLUMN "NOTIFICACAO"."USUA_ID" IS 'Referencia para a tabela de usuário';
   COMMENT ON COLUMN "NOTIFICACAO"."NOTI_DT_LIDO" IS 'Data em que foi feita a leitura da notificação.';
   COMMENT ON COLUMN "NOTIFICACAO"."NOTI_ID_PARCEIRO" IS 'Referência ao parceiro relacionado com o usuário caso exista.';
   COMMENT ON TABLE "NOTIFICACAO"  IS 'Tabela que armazena as notificações do sistema';
--------------------------------------------------------
--  DDL for Table NOVO_VALOR_NMDP
--------------------------------------------------------

  CREATE TABLE "NOVO_VALOR_NMDP" 
   (	"NOVN_ID_CODIGO" VARCHAR2(10), 
	"NOVN_TX_SUBTIPO" VARCHAR2(4000), 
	"NOVN_IN_AGRUPADO" NUMBER(1,0) DEFAULT 0, 
	"NOVN_DT_ULTIMA_ATUALIZACAO_ARQ" DATE, 
	"NOVN_NR_QUANTIDADE" NUMBER DEFAULT 0
   ) ;

   COMMENT ON COLUMN "NOVO_VALOR_NMDP"."NOVN_ID_CODIGO" IS 'CÓDIGO NMDP';
   COMMENT ON COLUMN "NOVO_VALOR_NMDP"."NOVN_TX_SUBTIPO" IS 'VALORES ALELICOS';
   COMMENT ON COLUMN "NOVO_VALOR_NMDP"."NOVN_IN_AGRUPADO" IS 'DEFINE SE O VALOR ALELIXO ESTA AGRUPADO EX: 13:05/13:06';
   COMMENT ON COLUMN "NOVO_VALOR_NMDP"."NOVN_DT_ULTIMA_ATUALIZACAO_ARQ" IS 'Ultima data de atualização do arquivo';
   COMMENT ON COLUMN "NOVO_VALOR_NMDP"."NOVN_NR_QUANTIDADE" IS 'Quantidade de subtipos';
   COMMENT ON TABLE "NOVO_VALOR_NMDP"  IS 'TABELA DE VALORES NMDP';
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
	"PACI_TX_WMDA_ID" VARCHAR2(10 BYTE)
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
--  DDL for Table PACIENTE_IN_REREME
--------------------------------------------------------

  CREATE TABLE "PACIENTE_IN_REREME" 
   (	"PACI_NR_RMR" NUMBER, 
	"PAIR_DT_INCLUSAO" TIMESTAMP (6), 
	"PAIR_IN_OPERACAO" VARCHAR2(1), 
	"PAIR_IN_STATUS_OPERACAO" VARCHAR2(1), 
	"PAIR_TX_DESCRICAO_ERRO" VARCHAR2(255), 
	"PAIR_TX_REGISTRO_PACIENTE" CLOB, 
	"PAIR_DT_PROCESSAMENTO" TIMESTAMP (6), 
	"PAIR_TX_TIPO_ALTERACAO" VARCHAR2(20), 
	"PAIR_NR_ID_ALTERACAO" NUMBER
   ) ;

   COMMENT ON COLUMN "PACIENTE_IN_REREME"."PACI_NR_RMR" IS 'RMR do paciente, referente ao registro processado.';
   COMMENT ON COLUMN "PACIENTE_IN_REREME"."PAIR_DT_INCLUSAO" IS 'Data da inclusão do registro na tabela.';
   COMMENT ON COLUMN "PACIENTE_IN_REREME"."PAIR_IN_OPERACAO" IS 'Operação a ser realizada no destino (Rereme) I - Inclusão, A - Alteração';
   COMMENT ON COLUMN "PACIENTE_IN_REREME"."PAIR_IN_STATUS_OPERACAO" IS 'Status da operação indica o que já foi realizado com o registro. P - A processar, T - Transferido, E - Erro';
   COMMENT ON COLUMN "PACIENTE_IN_REREME"."PAIR_TX_DESCRICAO_ERRO" IS 'Texto livre sobre o erro durante o processamento, caso ocorra.';
   COMMENT ON COLUMN "PACIENTE_IN_REREME"."PAIR_TX_REGISTRO_PACIENTE" IS 'Estrutura JSON com o dados do paciente lidos do Redome.';
   COMMENT ON COLUMN "PACIENTE_IN_REREME"."PAIR_DT_PROCESSAMENTO" IS 'Data de processamento do registro pela Rereme.';
   COMMENT ON COLUMN "PACIENTE_IN_REREME"."PAIR_TX_TIPO_ALTERACAO" IS 'Tipo de Alteração (NOVO_EXAME, NOVA_EVOLUCAO)';
   COMMENT ON COLUMN "PACIENTE_IN_REREME"."PAIR_NR_ID_ALTERACAO" IS 'ID do Exame ou da evolução';
--------------------------------------------------------
--  DDL for Table PACIENTE_MISMATCH
--------------------------------------------------------

  CREATE TABLE "PACIENTE_MISMATCH" 
   (	"PACI_NR_RMR" NUMBER, 
	"LOCU_ID" VARCHAR2(4)
   ) ;

   COMMENT ON COLUMN "PACIENTE_MISMATCH"."PACI_NR_RMR" IS 'Número do RMR do paciente que irá ter relacionamento com o locus
';
   COMMENT ON COLUMN "PACIENTE_MISMATCH"."LOCU_ID" IS 'Código do lócus para relacionamento
';
   COMMENT ON TABLE "PACIENTE_MISMATCH"  IS 'Tabela para armazenamento do relacionamento entre paciente e locus no sentido de informar quais locus podem ser feitos mismatch para o paciente em questão.';
--------------------------------------------------------
--  DDL for Table PAGAMENTO
--------------------------------------------------------

  CREATE TABLE "PAGAMENTO" 
   (	"PAGA_ID" NUMBER, 
	"PAGA_DT_CRIACAO" TIMESTAMP (6), 
	"PAGA_DT_ATUALIZACAO" TIMESTAMP (6), 
	"PAGA_IN_COBRACA" NUMBER(1,0), 
	"PAGA_ID_OBEJTORELACIONADO" NUMBER, 
	"TISE_ID" NUMBER, 
	"STPA_ID" NUMBER, 
	"REGI_ID" NUMBER, 
	"MATC_ID" NUMBER
   ) ;

   COMMENT ON COLUMN "PAGAMENTO"."PAGA_ID" IS 'Identificador sequencial da tabela.';
   COMMENT ON COLUMN "PAGAMENTO"."PAGA_DT_CRIACAO" IS 'Data de criação.';
   COMMENT ON COLUMN "PAGAMENTO"."PAGA_DT_ATUALIZACAO" IS 'Data de atualização.';
   COMMENT ON COLUMN "PAGAMENTO"."PAGA_IN_COBRACA" IS 'Informa se o pagamento é de cobrança.';
   COMMENT ON COLUMN "PAGAMENTO"."PAGA_ID_OBEJTORELACIONADO" IS 'Id do obejto que originou o serviço.';
   COMMENT ON COLUMN "PAGAMENTO"."TISE_ID" IS 'Referência para o tipo de serviço.';
   COMMENT ON COLUMN "PAGAMENTO"."STPA_ID" IS 'Referência para a tabekla de status de pagamento.';
   COMMENT ON COLUMN "PAGAMENTO"."REGI_ID" IS 'Referência para a tabela de registros.';
   COMMENT ON COLUMN "PAGAMENTO"."MATC_ID" IS 'Referência para a tabela de match.';
   COMMENT ON TABLE "PAGAMENTO"  IS 'Tabela que registra os pagamentos podenso ser cobrança.';
--------------------------------------------------------
--  DDL for Table PAGAMENTO_AUD
--------------------------------------------------------

  CREATE TABLE "PAGAMENTO_AUD" 
   (	"AUDI_ID" NUMBER, 
	"PAGA_ID" NUMBER, 
	"AUDI_TX_TIPO" NUMBER, 
	"PAGA_DT_CRIACAO" DATE, 
	"PAGA_DT_ATUALIZACAO" DATE, 
	"PAGA_IN_COBRACA" NUMBER(1,0), 
	"PAGA_ID_OBEJTORELACIONADO" NUMBER, 
	"TISE_ID" NUMBER, 
	"STPA_ID" NUMBER, 
	"REGI_ID" NUMBER, 
	"MATC_ID" NUMBER
   ) ;
--------------------------------------------------------
--  DDL for Table PAIS
--------------------------------------------------------

  CREATE TABLE "PAIS" 
   (	"PAIS_ID" NUMBER, 
	"PAIS_TX_NOME" VARCHAR2(100),
	"PAIS_TX_CD_NACIONAL_REDOMEWEB" VARCHAR2(3)
   ) ;

   COMMENT ON COLUMN "PAIS"."PAIS_ID" IS 'Identificador do País';
   COMMENT ON COLUMN "PAIS"."PAIS_TX_NOME" IS 'Nome do País ';
   COMMENT ON COLUMN "PAIS"."PAIS_TX_CD_NACIONAL_REDOMEWEB" IS 'Identificador da Nacionalidade na base do REDOMEWeb (integração).';
   COMMENT ON TABLE "PAIS"  IS 'Paises';
--------------------------------------------------------
--  DDL for Table PAIS_CORREIO
--------------------------------------------------------

  CREATE TABLE "PAIS_CORREIO" 
   (	"PACO_ID" VARCHAR2(2), 
	"PACO_TX_NOME" VARCHAR2(72)
   ) ;

   COMMENT ON COLUMN "PAIS_CORREIO"."PACO_ID" IS 'Sigla do País';
   COMMENT ON COLUMN "PAIS_CORREIO"."PACO_TX_NOME" IS 'Nome do País';
   COMMENT ON TABLE "PAIS_CORREIO"  IS 'Tabela de País';
--------------------------------------------------------
--  DDL for Table PEDIDO_ADICIONAL_WORKUP
--------------------------------------------------------

  CREATE TABLE "PEDIDO_ADICIONAL_WORKUP" 
   (	"PEAW_ID" NUMBER, 
	"PEAW_TX_DESCRICAO" VARCHAR2(200), 
	"PEAW_DT_CRIACAO" TIMESTAMP (6), 
	"AVRW_ID" NUMBER
   ) ;

   COMMENT ON COLUMN "PEDIDO_ADICIONAL_WORKUP"."PEAW_ID" IS 'Identificador do pedido adicional de workup.';
   COMMENT ON COLUMN "PEDIDO_ADICIONAL_WORKUP"."PEAW_TX_DESCRICAO" IS 'Descrição do pedido adicional.';
   COMMENT ON COLUMN "PEDIDO_ADICIONAL_WORKUP"."PEAW_DT_CRIACAO" IS 'Data da criação do pedido adicional.';
   COMMENT ON COLUMN "PEDIDO_ADICIONAL_WORKUP"."AVRW_ID" IS 'Identificador da avaliação de resultado de workup.';
   COMMENT ON TABLE "PEDIDO_ADICIONAL_WORKUP"  IS 'Tabela de pedidos adicionais da avaliação do resultado de workup.';
--------------------------------------------------------
--  DDL for Table PEDIDO_COLETA
--------------------------------------------------------

  CREATE TABLE "PEDIDO_COLETA" 
   (	"PECL_ID" NUMBER, 
	"PECL_DT_CRIACAO" TIMESTAMP (6), 
	"PECL_DT_DISPONIBILIDADE_DOADOR" DATE, 
	"PECL_DT_LIBERACAO_DOADOR" DATE, 
	"PECL_IN_LOGISTICA_DOADOR" NUMBER(1,0), 
	"PECL_IN_LOGISTICA_MATERIAL" NUMBER(1,0), 
	"PECL_DT_ULTIMO_STATUS" TIMESTAMP (6), 
	"STPC_ID" NUMBER(2,0), 
	"CETR_ID_COLETA" NUMBER, 
	"USUA_ID" NUMBER, 
	"PEWO_ID" NUMBER, 
	"SOLI_ID" NUMBER, 
	"PECL_DT_COLETA" DATE, 
	"MOCC_TX_CODIGO" VARCHAR2(50),
	"PECL_IN_PRODUDO" NUMBER,
    "PECL_DT_INICIO_GCSF" TIMESTAMP (6),
	"PECL_DT_INTERNACAO" TIMESTAMP (6),
    "PECL_TX_GCSF_SETOR_ANDAR" VARCHAR2(30),
    "PECL_TX_GCSF_PROCURAR_POR" VARCHAR2(50),
    "PECL_TX_INTERNACAO_SETOR_ANDAR" VARCHAR2(30),
    "PECL_TX_INTERNACAO_PROCURAR_POR" VARCHAR2(50),
    "PECL_IN_JEJUM" NUMBER(1,0),
    "PECL_NR_HR_JEJUM" NUMBER,
    "PECL_IN_JEJUM_TOTAL" NUMBER(1,0),
    "PECL_TX_INFORMACOES_ADICIONAIS" CLOB
	
   ) ;

   COMMENT ON COLUMN "PEDIDO_COLETA"."PECL_ID" IS 'Identificador do pedido de coleta.';
   COMMENT ON COLUMN "PEDIDO_COLETA"."PECL_DT_CRIACAO" IS 'Data de criação do pedido de coleta.';
   COMMENT ON COLUMN "PEDIDO_COLETA"."PECL_DT_DISPONIBILIDADE_DOADOR" IS 'Data em que o doador deverá estar disponível no centro de coleta.';
   COMMENT ON COLUMN "PEDIDO_COLETA"."PECL_DT_LIBERACAO_DOADOR" IS 'Data em que o doador será liberado pelo centro de coleta.';
   COMMENT ON COLUMN "PEDIDO_COLETA"."PECL_IN_LOGISTICA_DOADOR" IS 'Indica se necessita de logistica para o doador.';
   COMMENT ON COLUMN "PEDIDO_COLETA"."PECL_IN_LOGISTICA_MATERIAL" IS 'Indica se necessita de logistica para o material.';
   COMMENT ON COLUMN "PEDIDO_COLETA"."PECL_DT_ULTIMO_STATUS" IS 'Data da ultima atualização de status.';
   COMMENT ON COLUMN "PEDIDO_COLETA"."STPC_ID" IS 'Identificador para status de pedido coleta.';
   COMMENT ON COLUMN "PEDIDO_COLETA"."CETR_ID_COLETA" IS 'Centro de transplante responsável pela coleta.';
   COMMENT ON COLUMN "PEDIDO_COLETA"."USUA_ID" IS 'Usuário responsável pelo pedido de coleta.';
   COMMENT ON COLUMN "PEDIDO_COLETA"."PEWO_ID" IS 'Identificador do pedido de workup.';
   COMMENT ON COLUMN "PEDIDO_COLETA"."PECL_DT_COLETA" IS 'Data da coleta replicada do pedido de workup ou da prescrição, Depende se o doador é medula ou cordão.';
   COMMENT ON TABLE "PEDIDO_COLETA"  IS 'Tabela para armazenar os pedidos de coleta.';
   
   COMMENT ON COLUMN "PEDIDO_COLETA"."SOLI_ID" IS 'Identificador da soliciotação.';
   COMMENT ON COLUMN "PEDIDO_COLETA"."MOCC_TX_CODIGO" IS 'Motivo do cancelamento do pedido de coleta.';
   COMMENT ON COLUMN "PEDIDO_COLETA"."PECL_IN_PRODUDO" IS 'Informar o tipo de produto 0-MO | 1-PBSC | DLI';
   COMMENT ON COLUMN "PEDIDO_COLETA"."PECL_DT_INICIO_GCSF" IS 'Data inicío do G-CSF do doador.';
   COMMENT ON COLUMN "PEDIDO_COLETA"."PECL_DT_INTERNACAO" IS 'Data da internação do doador.';
   COMMENT ON COLUMN "PEDIDO_COLETA"."PECL_TX_GCSF_SETOR_ANDAR" IS 'Nome setor e andar do G-CSF informado no agendamento do pedido de coleta.';
   COMMENT ON COLUMN "PEDIDO_COLETA"."PECL_TX_GCSF_PROCURAR_POR" IS 'Nome procurar por do G-CSF informado no agendamento do pedido de coleta.';
   COMMENT ON COLUMN "PEDIDO_COLETA"."PECL_TX_INTERNACAO_SETOR_ANDAR" IS 'Nome setor e andar da internação informado no agendamento do pedido de coleta.';
   COMMENT ON COLUMN "PEDIDO_COLETA"."PECL_TX_INTERNACAO_PROCURAR_POR" IS 'Nome procurar por da internação informado no agendamento do pedido de coleta.';
   COMMENT ON COLUMN "PEDIDO_COLETA"."PECL_IN_JEJUM" IS 'Flag doador em jejum informado no agendamento de coleta.';
   COMMENT ON COLUMN "PEDIDO_COLETA"."PECL_NR_HR_JEJUM" IS 'Jejum de quantas horas informado no agendamento de coleta.';
   COMMENT ON COLUMN "PEDIDO_COLETA"."PECL_IN_JEJUM_TOTAL" IS 'Doador em jejum total - sim 1 ou não 0  no agendamento de coleta.';
   COMMENT ON COLUMN "PEDIDO_COLETA"."PECL_TX_INFORMACOES_ADICIONAIS" IS 'Campo livre de observaçao que sera informado no agendamento de coleta.';

   
--------------------------------------------------------
--  DDL for Table PEDIDO_CONTATO
--------------------------------------------------------

  CREATE TABLE "PEDIDO_CONTATO" 
   (	"PECO_ID" NUMBER, 
	"PECO_DT_CRIACAO" TIMESTAMP (6), 
	"PECO_IN_ABERTO" NUMBER DEFAULT 1, 
	"SOLI_ID" NUMBER, 
	"HEEN_ID" NUMBER,
	"PECO_IN_FINALIZACAO_AUTOMATICA" NUMBER,
	"PECO_DT_FINALIZACAO" TIMESTAMP(6),
	"PECO_IN_CONTACTADO" NUMBER,
	"PECO_IN_CONTACTADO_TERCEIRO" NUMBER,
	"PECO_NR_ACAO" NUMBER,
	"MOSD_ID" NUMBER,
	"PECO_NR_TEMPO_INATIVACAO_TEMP" NUMBER,
	"USUA_ID" NUMBER,
	"DOAD_ID" NUMBER,
	"PECO_IN_PASSIVO" NUMBER,
	"PECO_IN_TIPO_CONTATO" NUMBER
   ) ;


   COMMENT ON COLUMN "PEDIDO_CONTATO"."PECO_ID" IS 'Identificador sequencial da tabela.';
   COMMENT ON COLUMN "PEDIDO_CONTATO"."PECO_DT_CRIACAO" IS 'Data realização do pedido.';
   COMMENT ON COLUMN "PEDIDO_CONTATO"."PECO_IN_ABERTO" IS 'Indica se o pedido está em 1-aberto ou 0-fechado.';
--------------------------------------------------------
--  DDL for Table PEDIDO_ENRIQUECIMENTO
--------------------------------------------------------

  CREATE TABLE "PEDIDO_ENRIQUECIMENTO" 
   (	"PEEN_ID" NUMBER, 
	"SOLI_ID" NUMBER, 
	"PEEN_DT_ENRIQUECIMENTO" TIMESTAMP (6), 
	"USUA_ID" NUMBER, 
	"PEEN_DT_CRIACAO" TIMESTAMP (6), 
	"PEEN_IN_ABERTO" NUMBER(1,0) DEFAULT 1, 
	"PEEN_IN_TIPO_ENRIQUECIMENTO" NUMBER(1,0)
   ) ;

   COMMENT ON COLUMN "PEDIDO_ENRIQUECIMENTO"."PEEN_ID" IS 'Referência do pedido de enriquecimento';
   COMMENT ON COLUMN "PEDIDO_ENRIQUECIMENTO"."SOLI_ID" IS 'Id da solicitação';
   COMMENT ON COLUMN "PEDIDO_ENRIQUECIMENTO"."PEEN_DT_ENRIQUECIMENTO" IS 'Data em que foi realizado o enriquecimento';
   COMMENT ON COLUMN "PEDIDO_ENRIQUECIMENTO"."USUA_ID" IS 'Identificação do usuário que fez o enriquecimento';
   COMMENT ON COLUMN "PEDIDO_ENRIQUECIMENTO"."PEEN_DT_CRIACAO" IS 'Data de criação';
   COMMENT ON COLUMN "PEDIDO_ENRIQUECIMENTO"."PEEN_IN_ABERTO" IS 'Flag que indica se o enriquecimento está 1-aberto ou 0-fechado';
   COMMENT ON COLUMN "PEDIDO_ENRIQUECIMENTO"."PEEN_IN_TIPO_ENRIQUECIMENTO" IS 'Tipo de Enriquecimento';
   COMMENT ON TABLE "PEDIDO_ENRIQUECIMENTO"  IS 'Pedido de enriquecimento';
--------------------------------------------------------
--  DDL for Table PEDIDO_EXAME
--------------------------------------------------------


  CREATE TABLE "PEDIDO_EXAME" 
   (	"PEEX_ID" NUMBER, 
	"PEEX_DT_CRIACAO" TIMESTAMP (6), 
	"SOLI_ID" NUMBER, 
	"LABO_ID" NUMBER, 
	"STPX_ID" NUMBER, 
	"PEEX_DT_COLETA_AMOSTRA" DATE, 
	"PEEX_DT_RECEBIMENTO_AMOSTRA" DATE, 
	"PEEX_IN_OWNER" VARCHAR2(100), 
	"EXAM_ID" NUMBER, 
	"TIEX_ID" NUMBER, 
	"EXAM_ID_DOADOR_INTERNACIONAL" NUMBER, 
	"PEEX_TX_JUSTIFICATIVA" VARCHAR2(255), 
	"PEEX_DT_CANCELAMENTO" DATE, 
	"EXAM_ID_CORDAO_INTERNACIONAL" NUMBER, 
	"PEEX_DT_ABERTURA_DIVERGENCIA" TIMESTAMP (6), 
	"PEEX_DT_CONCLUSAO_DIVERGENCIA" TIMESTAMP (6)
   ) ;

   COMMENT ON COLUMN "PEDIDO_EXAME"."PEEX_ID" IS 'Identificador sequencial da tabela.';
   COMMENT ON COLUMN "PEDIDO_EXAME"."PEEX_DT_CRIACAO" IS 'Data realização do pedido.';
   COMMENT ON COLUMN "PEDIDO_EXAME"."LABO_ID" IS 'Referência para a tabela de  laboratório.';
   COMMENT ON COLUMN "PEDIDO_EXAME"."STPX_ID" IS 'Referencia para atabela de status.';
   COMMENT ON COLUMN "PEDIDO_EXAME"."PEEX_DT_COLETA_AMOSTRA" IS 'Data da coleta da amostra feita pelo médico.';
   COMMENT ON COLUMN "PEDIDO_EXAME"."PEEX_DT_RECEBIMENTO_AMOSTRA" IS 'Data de recebimento da amostra pelo laboratório.';
   COMMENT ON COLUMN "PEDIDO_EXAME"."PEEX_IN_OWNER" IS 'Indica quem é o dono do pedido de exame, se é o paciente ou o doador.';
   COMMENT ON COLUMN "PEDIDO_EXAME"."EXAM_ID" IS 'Referência para o exame de resultado do pedido';
   COMMENT ON COLUMN "PEDIDO_EXAME"."TIEX_ID" IS 'Referência para o tipo de exame do pedido';
   COMMENT ON COLUMN "PEDIDO_EXAME"."EXAM_ID_DOADOR_INTERNACIONAL" IS 'Referência para o exame do doador internacional de resultado do pedido';
   COMMENT ON COLUMN "PEDIDO_EXAME"."PEEX_TX_JUSTIFICATIVA" IS 'Campo livre onde é informado a justificativa para o cancelamento do pedido.';
   COMMENT ON COLUMN "PEDIDO_EXAME"."PEEX_DT_CANCELAMENTO" IS 'Data de cancelamento do pedido.';
   COMMENT ON COLUMN "PEDIDO_EXAME"."EXAM_ID_CORDAO_INTERNACIONAL" IS 'Referência para o exame do cordão internacional de resultado do pedido';
   COMMENT ON COLUMN "PEDIDO_EXAME"."PEEX_DT_ABERTURA_DIVERGENCIA" IS 'Data de controle de pedido para resolver divergencia.';
   COMMENT ON COLUMN "PEDIDO_EXAME"."PEEX_DT_CONCLUSAO_DIVERGENCIA" IS 'Data de controle de pedido para resolver divergencia.';
--------------------------------------------------------
--  DDL for Table PEDIDO_EXAME_AUD
--------------------------------------------------------

  CREATE TABLE "PEDIDO_EXAME_AUD" 
   (	"PEEX_ID" NUMBER(19,0), 
	"AUDI_ID" NUMBER(19,0), 
	"AUDI_TX_TIPO" NUMBER(3,0), 
	"PEEX_DT_COLETA_AMOSTRA" DATE, 
	"PEEX_DT_CRIACAO" TIMESTAMP (6), 
	"PEEX_DT_RECEBIMENTO_AMOSTRA" DATE, 
	"PEEX_TX_JUSTIFICATIVA" VARCHAR2(255 CHAR), 
	"PEEX_IN_OWNER" VARCHAR2(255 CHAR), 
	"EXAM_ID" NUMBER(19,0), 
	"EXAM_ID_DOADOR_INTERNACIONAL" NUMBER(19,0), 
	"LABO_ID" NUMBER(19,0), 
	"SOLI_ID" NUMBER(19,0), 
	"STPX_ID" NUMBER(19,0), 
	"TIEX_ID" NUMBER(19,0), 
	"PEEX_DT_CANCELAMENTO" DATE, 
	"EXAM_ID_CORDAO_INTERNACIONAL" NUMBER, 
	"PEEX_ID_SOLICITACAO_REDOMEWEB" NUMBER, 
	"PEEX_DT_ABERTURA_DIVERGENCIA" TIMESTAMP (6), 
	"PEEX_DT_CONCLUSAO_DIVERGENCIA" TIMESTAMP (6)
   ) ;

   COMMENT ON COLUMN "PEDIDO_EXAME_AUD"."PEEX_ID" IS 'ID DO PEDIDO DE EXAME';
   COMMENT ON COLUMN "PEDIDO_EXAME_AUD"."AUDI_ID" IS 'ID DA AUDITORIA';
   COMMENT ON COLUMN "PEDIDO_EXAME_AUD"."AUDI_TX_TIPO" IS 'TIPO DE OPERAÇÃO';
   COMMENT ON COLUMN "PEDIDO_EXAME_AUD"."PEEX_DT_COLETA_AMOSTRA" IS 'AUDITORIA DA DATA COLETA';
   COMMENT ON COLUMN "PEDIDO_EXAME_AUD"."PEEX_DT_CRIACAO" IS 'AUDITORIA DA DATA DE CRIAÇÃO';
   COMMENT ON COLUMN "PEDIDO_EXAME_AUD"."PEEX_DT_RECEBIMENTO_AMOSTRA" IS 'AUDITORIA DA DATA DE RECEBIMENTO';
   COMMENT ON COLUMN "PEDIDO_EXAME_AUD"."PEEX_TX_JUSTIFICATIVA" IS 'AUDITORIA DA JUSTIFICATIVA';
   COMMENT ON COLUMN "PEDIDO_EXAME_AUD"."PEEX_IN_OWNER" IS 'AUDITORIA DO DONO PEDIDO (DOADOR OU PACIENTE)
';
   COMMENT ON COLUMN "PEDIDO_EXAME_AUD"."EXAM_ID" IS 'AUDITORIA DO EXAME';
   COMMENT ON COLUMN "PEDIDO_EXAME_AUD"."EXAM_ID_DOADOR_INTERNACIONAL" IS 'AUDITORIA DO EXAME INTERNACIONAL';
   COMMENT ON COLUMN "PEDIDO_EXAME_AUD"."LABO_ID" IS 'AUDITORIA DO LABORATORIO';
   COMMENT ON COLUMN "PEDIDO_EXAME_AUD"."SOLI_ID" IS 'AUDITORIA DA SOLICITACAO';
   COMMENT ON COLUMN "PEDIDO_EXAME_AUD"."STPX_ID" IS 'AUDITORIA DO STATUS DO PEDIDO';
   COMMENT ON COLUMN "PEDIDO_EXAME_AUD"."TIEX_ID" IS 'AUDITORIA TIPO DE EXAME';
--------------------------------------------------------
--  DDL for Table PEDIDO_IDM
--------------------------------------------------------

  CREATE TABLE "PEDIDO_IDM" 
   (	"PEID_ID" NUMBER, 
	"PEID_DT_CRIACAO" TIMESTAMP (6), 
	"SOLI_ID" NUMBER, 
	"STPI_ID" NUMBER, 
	"PEID_DT_CANCELAMENTO" DATE
   ) ;

   COMMENT ON COLUMN "PEDIDO_IDM"."PEID_ID" IS 'Identificador sequencial da tabela.';
   COMMENT ON COLUMN "PEDIDO_IDM"."PEID_DT_CRIACAO" IS 'Data da criação do pedido.';
   COMMENT ON COLUMN "PEDIDO_IDM"."SOLI_ID" IS 'Referência para a tabela de solicitação.';
   COMMENT ON COLUMN "PEDIDO_IDM"."STPI_ID" IS 'Referência para a tabela de status.';
   COMMENT ON COLUMN "PEDIDO_IDM"."PEID_DT_CANCELAMENTO" IS 'Data de cancelamento do pedido, caso este tendha sido cancelada';
   COMMENT ON TABLE "PEDIDO_IDM"  IS 'Pedido de IDM de um Doador.';
--------------------------------------------------------
--  DDL for Table PEDIDO_IDM_AUD
--------------------------------------------------------

  CREATE TABLE "PEDIDO_IDM_AUD" 
   (	"PEID_ID" NUMBER, 
	"AUDI_ID" NUMBER, 
	"AUDI_TX_TIPO" NUMBER, 
	"PEID_DT_CRIACAO" TIMESTAMP (6), 
	"SOLI_ID" NUMBER, 
	"STPI_ID" NUMBER, 
	"PEID_DT_CANCELAMENTO" DATE
   ) ;
--------------------------------------------------------
--  DDL for Table PEDIDO_LOGISTICA
--------------------------------------------------------

  CREATE TABLE "PEDIDO_LOGISTICA" 
   (	"PELO_ID" NUMBER, 
	"PELO_DT_CRIACAO" TIMESTAMP (6), 
	"PELO_TX_OBSERVACAO" VARCHAR2(500), 
	"PELO_DT_ATUALIZACAO" TIMESTAMP (6), 
	"USUA_ID_RESPONSAVEL" NUMBER, 
	"PEWO_ID" NUMBER, 
	"PELO_IN_TIPO" NUMBER(2,0), 
	"STPL_ID" NUMBER(2,0), 
	"PECL_ID" NUMBER, 
	"PETR_ID" NUMBER, 
	"PELO_TX_RETIRADA_PAIS" VARCHAR2(80), 
	"PELO_TX_RETIRADA_ESTADO" VARCHAR2(80), 
	"PELO_TX_RETIRADA_CIDADE" VARCHAR2(80), 
	"PELO_TX_RETIRADA_RUA" VARCHAR2(200), 
	"PELO_TX_TELEFONE" VARCHAR2(15), 
	"PELO_TX_ID_DOADOR_LOCAL" VARCHAR2(20), 
	"PELO_TX_HAWB" VARCHAR2(20), 
	"TIPL_IN_ORIGEM" VARCHAR2(13), 
	"PELO_TX_NOME_COURIER" VARCHAR2(255), 
	"PELO_TX_PASSAPORTE_COURIER" VARCHAR2(255)
   ) ;

   COMMENT ON COLUMN "PEDIDO_LOGISTICA"."PELO_DT_CRIACAO" IS 'Data da criação do pedido.';
   COMMENT ON COLUMN "PEDIDO_LOGISTICA"."PELO_TX_OBSERVACAO" IS 'Texto com observações sobre o pedido d elogistica.';
   COMMENT ON COLUMN "PEDIDO_LOGISTICA"."PELO_DT_ATUALIZACAO" IS 'Data de atualização.';
   COMMENT ON COLUMN "PEDIDO_LOGISTICA"."USUA_ID_RESPONSAVEL" IS 'Usuário responsável pelo pedido.';
   COMMENT ON COLUMN "PEDIDO_LOGISTICA"."PEWO_ID" IS 'Identificador do pedido de workup que originou esse pedido.';
   COMMENT ON COLUMN "PEDIDO_LOGISTICA"."PELO_IN_TIPO" IS 'Identificador do tipo de pedido de logistica.';
   COMMENT ON COLUMN "PEDIDO_LOGISTICA"."STPL_ID" IS 'Identificador do Status do pedido.';
   COMMENT ON COLUMN "PEDIDO_LOGISTICA"."PECL_ID" IS 'Identificador do pedido de coleta que originou esse pedido.';
   COMMENT ON COLUMN "PEDIDO_LOGISTICA"."PETR_ID" IS 'Identificador da tabela de pedido de transporte';
   COMMENT ON COLUMN "PEDIDO_LOGISTICA"."PELO_TX_RETIRADA_PAIS" IS 'Campo de pais de retirada usado para medula internacional';
   COMMENT ON COLUMN "PEDIDO_LOGISTICA"."PELO_TX_RETIRADA_ESTADO" IS 'Campo de estado de retirado usado para medula internacional
';
   COMMENT ON COLUMN "PEDIDO_LOGISTICA"."PELO_TX_RETIRADA_CIDADE" IS 'Campo de cidade de retirado usado para medula internacional
';
   COMMENT ON COLUMN "PEDIDO_LOGISTICA"."PELO_TX_RETIRADA_RUA" IS 'Campo de rua  de retirado usado para medula internacional
';
   COMMENT ON COLUMN "PEDIDO_LOGISTICA"."PELO_TX_TELEFONE" IS 'Campo de telefone de retirado usado para medula internacional
';
   COMMENT ON COLUMN "PEDIDO_LOGISTICA"."PELO_TX_ID_DOADOR_LOCAL" IS 'Identificação do doador no país de origem da medula';
   COMMENT ON COLUMN "PEDIDO_LOGISTICA"."PELO_TX_HAWB" IS 'Localizador';
   COMMENT ON COLUMN "PEDIDO_LOGISTICA"."TIPL_IN_ORIGEM" IS 'Define a origem do pedido de logística, se nacional (NACIONAL) ou internacional (INTERNACIONAL).';
   COMMENT ON COLUMN "PEDIDO_LOGISTICA"."PELO_TX_NOME_COURIER" IS 'Nome do courier responsável pela retirada do material para implante (somente internacional).';
   COMMENT ON COLUMN "PEDIDO_LOGISTICA"."PELO_TX_PASSAPORTE_COURIER" IS 'Número do passaporte do courier responsável pela retirada do material para implante (somente internacional).';
   COMMENT ON TABLE "PEDIDO_LOGISTICA"  IS 'Tabela responsável por armazenar os pedidos de logistica.';
--------------------------------------------------------
--  DDL for Table PEDIDO_LOGISTICA_AUD
--------------------------------------------------------

  CREATE TABLE "PEDIDO_LOGISTICA_AUD" 
   (	"PELO_ID" NUMBER, 
	"AUDI_ID" NUMBER(19,0), 
	"AUDI_TX_TIPO" NUMBER(3,0), 
	"PELO_DT_CRIACAO" TIMESTAMP (6), 
	"PELO_TX_OBSERVACAO" VARCHAR2(500), 
	"PELO_DT_ATUALIZACAO" TIMESTAMP (6), 
	"USUA_ID_RESPONSAVEL" NUMBER, 
	"PEWO_ID" NUMBER, 
	"TIPL_ID" NUMBER(2,0), 
	"STPL_ID" NUMBER(2,0), 
	"PECL_ID" NUMBER, 
	"PETR_ID" NUMBER, 
	"PELO_TX_RETIRADA_PAIS" VARCHAR2(80), 
	"PELO_TX_RETIRADA_ESTADO" VARCHAR2(80), 
	"PELO_TX_RETIRADA_CIDADE" VARCHAR2(80), 
	"PELO_TX_RETIRADA_RUA" VARCHAR2(200), 
	"PELO_TX_TELEFONE" VARCHAR2(15), 
	"PELO_TX_ID_DOADOR_LOCAL" VARCHAR2(20), 
	"PELO_TX_HAWB" VARCHAR2(20), 
	"TIPL_IN_ORIGEM" VARCHAR2(13), 
	"PELO_TX_NOME_COURIER" VARCHAR2(255), 
	"PELO_TX_PASSAPORTE_COURIER" VARCHAR2(255)
   ) ;

   COMMENT ON TABLE "PEDIDO_LOGISTICA_AUD"  IS 'Tabela de auditoria da tabela PEDIDO_LOGISTICA.';
--------------------------------------------------------
--  DDL for Table PEDIDO_TRANSFERENCIA_CENTRO
--------------------------------------------------------

  CREATE TABLE "PEDIDO_TRANSFERENCIA_CENTRO" 
   (	"PETC_ID" NUMBER, 
	"PETC_DT_CRIACAO" TIMESTAMP (6), 
	"PETC_DT_ATUALIZACAO" TIMESTAMP (6), 
	"PETC_IN_TIPO" NUMBER, 
	"PETC_IN_APROVADO" NUMBER DEFAULT NULL, 
	"PACI_NR_RMR" NUMBER, 
	"CETR_ID_ORIGEM" NUMBER, 
	"CETR_ID_DESTINO" NUMBER, 
	"USUA_ID" NUMBER, 
	"PETC_TX_JUSTIFICATIVA" VARCHAR2(255)
   ) ;

   COMMENT ON COLUMN "PEDIDO_TRANSFERENCIA_CENTRO"."PETC_ID" IS 'Identificador único da tabela.';
   COMMENT ON COLUMN "PEDIDO_TRANSFERENCIA_CENTRO"."PETC_DT_CRIACAO" IS 'Data de criação do registro.';
   COMMENT ON COLUMN "PEDIDO_TRANSFERENCIA_CENTRO"."PETC_DT_ATUALIZACAO" IS 'Data de atualização do registro.';
   COMMENT ON COLUMN "PEDIDO_TRANSFERENCIA_CENTRO"."PETC_IN_TIPO" IS 'Tipo de transferência.';
   COMMENT ON COLUMN "PEDIDO_TRANSFERENCIA_CENTRO"."PETC_IN_APROVADO" IS 'Flag que indica se a transferência foi aprovado.';
   COMMENT ON COLUMN "PEDIDO_TRANSFERENCIA_CENTRO"."PACI_NR_RMR" IS 'Chave de referência a tabela de paciente.';
   COMMENT ON COLUMN "PEDIDO_TRANSFERENCIA_CENTRO"."CETR_ID_ORIGEM" IS 'Chave de referência a tabela de centro transplantador. Identifica a origem da transferência.';
   COMMENT ON COLUMN "PEDIDO_TRANSFERENCIA_CENTRO"."CETR_ID_DESTINO" IS 'Chave de referência a tabela de centro transplantador. Identifica o destino da transferência.';
   COMMENT ON COLUMN "PEDIDO_TRANSFERENCIA_CENTRO"."USUA_ID" IS 'Chave de referência a tabela de usuário. Identifica o usuário que aprovou a transferência.';
   COMMENT ON COLUMN "PEDIDO_TRANSFERENCIA_CENTRO"."PETC_TX_JUSTIFICATIVA" IS 'Justificativa do motiva da recusa do pedido de transferencia.';
   COMMENT ON TABLE "PEDIDO_TRANSFERENCIA_CENTRO"  IS 'Está tabela armazena os pedidos de transferência entre Centro Avaliador e Centro Transplante.';
--------------------------------------------------------
--  DDL for Table PEDIDO_TRANSPORTE
--------------------------------------------------------

  CREATE TABLE "PEDIDO_TRANSPORTE" 
   (	"PETR_ID" NUMBER, 
	"PETR_DT_ATUALIZACAO" TIMESTAMP (6), 
	"USUA_ID_RESPONSAVEL" NUMBER, 
	"PETR_HR_PREVISTA_RETIRADA" TIMESTAMP (6), 
	"TRAN_ID" NUMBER, 
	"STPT_ID" NUMBER DEFAULT 1, 
	"PETR_DADOS_VOO" VARCHAR2(255), 
	"COUR_ID" NUMBER, 
	"PETR_IN_STATUS" NUMBER DEFAULT 0, 
	"PETR_DT_RETIRADA" TIMESTAMP (6), 
	"PETR_DT_ENTREGA" TIMESTAMP (6)
   ) ;

   COMMENT ON COLUMN "PEDIDO_TRANSPORTE"."PETR_ID" IS 'Identifica��o da tabela.';
   COMMENT ON COLUMN "PEDIDO_TRANSPORTE"."PETR_DT_ATUALIZACAO" IS 'Data de �ltima atualiza��o da tabela.';
   COMMENT ON COLUMN "PEDIDO_TRANSPORTE"."USUA_ID_RESPONSAVEL" IS 'Usu�rio respons�vel por realizar (finalizar) o pedido.';
   COMMENT ON COLUMN "PEDIDO_TRANSPORTE"."PETR_HR_PREVISTA_RETIRADA" IS 'Hora prevista para a retirada do material da coleta.';
   COMMENT ON COLUMN "PEDIDO_TRANSPORTE"."TRAN_ID" IS 'Transportadora respons�vel por enviar o courier para retirada do material.';
   COMMENT ON COLUMN "PEDIDO_TRANSPORTE"."STPT_ID" IS 'Identificado da tabela de status do pedido de transporte';
   COMMENT ON COLUMN "PEDIDO_TRANSPORTE"."PETR_DADOS_VOO" IS 'Dados do voo inseridos pela transportadora.';
   COMMENT ON COLUMN "PEDIDO_TRANSPORTE"."COUR_ID" IS 'Courier inserido pela transportadora.';
   COMMENT ON COLUMN "PEDIDO_TRANSPORTE"."PETR_IN_STATUS" IS '0 - ANALISE, 1 - AGUARDANDO DOCUMENTACAO, 2 - AGUARDANDO CONFIRMACAO, 3 - AGUARDANDO RETIRADA, 4 - AGUARDANDO ENTREGA';
   COMMENT ON COLUMN "PEDIDO_TRANSPORTE"."PETR_DT_RETIRADA" IS 'Data da retirada do material da coleta.';
   COMMENT ON COLUMN "PEDIDO_TRANSPORTE"."PETR_DT_ENTREGA" IS 'Data da entregado material da coleta.';
   COMMENT ON TABLE "PEDIDO_TRANSPORTE"  IS 'Entidade que representa a transportadora que ir� realizar o transporte do material coletado (empresa terceirizada de courier).';
--------------------------------------------------------
--  DDL for Table PEDIDO_WORKUP
--------------------------------------------------------

  CREATE TABLE "PEDIDO_WORKUP" 
   ("PEWO_ID" NUMBER,
    "PEWO_DT_CRIACAO" TIMESTAMP (6), 
	"PEWO_DT_COLETA" DATE, 
	"PEWO_IN_STATUS" NUMBER, 
	"SOLI_ID" NUMBER,
	"CETR_ID_TRANSP" NUMBER, 
	"CETR_ID_COLETA" NUMBER 
   ) ;

--------------------------------------------------------
--  DDL for Table PEDIDO_WORKUP_AUD
--------------------------------------------------------

  CREATE TABLE "PEDIDO_WORKUP_AUD" 
   ("PEWO_ID" NUMBER(19,0), 
	"AUDI_ID" NUMBER(19,0), 
	"AUDI_TX_TIPO" NUMBER(3,0),
	"PEWO_DT_CRIACAO" TIMESTAMP (6), 
	"PEWO_DT_COLETA" DATE, 
	"PEWO_IN_STATUS" NUMBER(19,0), 	
	"CETR_ID" NUMBER(19,0),  
	"SOLI_ID" NUMBER(19,0)
   ) ;

   COMMENT ON TABLE "PEDIDO_WORKUP_AUD"  IS 'Tabela de auditoria da tabela PEDIDO_WORKUP.';
--------------------------------------------------------
--  DDL for Table PENDENCIA
--------------------------------------------------------

  CREATE TABLE "PENDENCIA" 
   (	"PEND_ID" NUMBER, 
	"PEND_TX_DESCRICAO" VARCHAR2(200), 
	"AVAL_ID" NUMBER, 
	"STPE_ID" NUMBER, 
	"TIPE_ID" NUMBER, 
	"PEND_DT_CRIACAO" TIMESTAMP (6)
   ) ;

   COMMENT ON COLUMN "PENDENCIA"."PEND_ID" IS 'Chave primária da tabela pendencia';
   COMMENT ON COLUMN "PENDENCIA"."PEND_TX_DESCRICAO" IS 'Descrição da pendencia criada pelo avaliador';
   COMMENT ON COLUMN "PENDENCIA"."AVAL_ID" IS 'Chave estrangeira para tabela de avaliação';
   COMMENT ON COLUMN "PENDENCIA"."STPE_ID" IS 'Chave estrangeira para o status da pendência';
   COMMENT ON COLUMN "PENDENCIA"."TIPE_ID" IS 'Chave estrangeira para o tipo de pendencia';
   COMMENT ON COLUMN "PENDENCIA"."PEND_DT_CRIACAO" IS 'Data da criação da pendencia';
   COMMENT ON TABLE "PENDENCIA"  IS 'Tabela de pendências da avaliação de um paciente.';
--------------------------------------------------------
--  DDL for Table PENDENCIA_AUD
--------------------------------------------------------

  CREATE TABLE "PENDENCIA_AUD" 
   (	"PEND_ID" NUMBER, 
	"AUDI_ID" NUMBER, 
	"AUDI_TX_TIPO" NUMBER(3,0), 
	"PEND_DT_CRIACAO" TIMESTAMP (6), 
	"PEND_TX_DESCRICAO" VARCHAR2(255 CHAR), 
	"AVAL_ID" NUMBER, 
	"STPE_ID" NUMBER, 
	"TIPE_ID" NUMBER
   ) ;

   COMMENT ON TABLE "PENDENCIA_AUD"  IS 'Tabela de auditoria de pendencia';
--------------------------------------------------------
--  DDL for Table PERFIL
--------------------------------------------------------

  CREATE TABLE "PERFIL" 
   (	"PERF_ID" NUMBER, 
	"PERF_TX_DESCRICAO" VARCHAR2(50), 
	"SIST_ID" NUMBER
   ) ;

   COMMENT ON COLUMN "PERFIL"."PERF_ID" IS 'Identificador do perfil';
   COMMENT ON COLUMN "PERFIL"."PERF_TX_DESCRICAO" IS 'Descrição do perfil';
   COMMENT ON COLUMN "PERFIL"."SIST_ID" IS 'Define com qual sistema o perfil está associado.';
   COMMENT ON TABLE "PERFIL"  IS 'Quadro de perfis do sistema';
--------------------------------------------------------
--  DDL for Table PERFIL_LOG_EVOLUCAO
--------------------------------------------------------

  CREATE TABLE "PERFIL_LOG_EVOLUCAO" 
   (	"PERF_ID" NUMBER, 
	"LOEV_ID" NUMBER
   ) ;

   COMMENT ON COLUMN "PERFIL_LOG_EVOLUCAO"."PERF_ID" IS 'Referencia de perfil
';
   COMMENT ON COLUMN "PERFIL_LOG_EVOLUCAO"."LOEV_ID" IS 'Referencia de historico';
   COMMENT ON TABLE "PERFIL_LOG_EVOLUCAO"  IS 'Armazena a referencia de log e os perfis que não podem ter acesso a uma determinada referencia de log.';
--------------------------------------------------------
--  DDL for Table PERMISSAO
--------------------------------------------------------

  CREATE TABLE "PERMISSAO" 
   (	"RECU_ID" NUMBER, 
	"PERF_ID" NUMBER, 
	"PERM_IN_COM_RESTRICAO" NUMBER(1,0) DEFAULT 0
   ) ;

   COMMENT ON COLUMN "PERMISSAO"."RECU_ID" IS 'Identificador do recurso.';
   COMMENT ON COLUMN "PERMISSAO"."PERF_ID" IS 'Identificador do perfil.';
   COMMENT ON COLUMN "PERMISSAO"."PERM_IN_COM_RESTRICAO" IS 'Indica se possui ou não alguma restrição que devem ser verificadas no momento da sua concessão.';
   COMMENT ON TABLE "PERMISSAO"  IS 'Permissões aos recursos dos perfis.';
--------------------------------------------------------
--  DDL for Table PRE_CADASTRO_MEDICO
--------------------------------------------------------

  CREATE TABLE "PRE_CADASTRO_MEDICO" 
   (	"PRCM_ID" NUMBER, 
	"PRCM_TX_CRM" VARCHAR2(13), 
	"PRCM_TX_NOME" VARCHAR2(255), 
	"PRCM_TX_LOGIN" VARCHAR2(50), 
	"PRCM_TX_ARQUIVO_CRM" VARCHAR2(255), 
	"PCME_ID" NUMBER, 
	"PRCM_TX_STATUS" VARCHAR2(20), 
	"PRCM_DT_SOLICITACAO" TIMESTAMP (6), 
	"PRCM_DT_ATUALIZACAO" TIMESTAMP (6), 
	"PRCM_TX_JUSTIFICATIVA" VARCHAR2(4000), 
	"USUA_ID" NUMBER
   ) ;

   COMMENT ON COLUMN "PRE_CADASTRO_MEDICO"."PRCM_ID" IS 'Identificação da tabela.';
   COMMENT ON COLUMN "PRE_CADASTRO_MEDICO"."PRCM_TX_CRM" IS 'CRM do médico pré cadastrado.';
   COMMENT ON COLUMN "PRE_CADASTRO_MEDICO"."PRCM_TX_NOME" IS 'Nome do médico pré cadastrado.';
   COMMENT ON COLUMN "PRE_CADASTRO_MEDICO"."PRCM_TX_LOGIN" IS 'Login único sugerido pelo médico no pré cadastro.';
   COMMENT ON COLUMN "PRE_CADASTRO_MEDICO"."PRCM_TX_ARQUIVO_CRM" IS 'URL que aponta para o arquivo CRM que o médico informou para validação do cadastro.';
   COMMENT ON COLUMN "PRE_CADASTRO_MEDICO"."PCME_ID" IS 'ID do endereço informado pelo médico.';
   COMMENT ON COLUMN "PRE_CADASTRO_MEDICO"."PRCM_DT_ATUALIZACAO" IS 'Data de atualização do registro.';
   COMMENT ON COLUMN "PRE_CADASTRO_MEDICO"."PRCM_TX_JUSTIFICATIVA" IS 'Justificativa pelo qual o pré cadastro foi reprovado.';
   COMMENT ON COLUMN "PRE_CADASTRO_MEDICO"."USUA_ID" IS 'ID do usuário responsável pela aprovação ou reprovação do pré cadastro.';
   COMMENT ON TABLE "PRE_CADASTRO_MEDICO"  IS 'Tabela que guarda o pré cadastro de médico, onde o mesmo se cadastra e solicita acesso ao Redome e pode ser ou não aprovado.';
--------------------------------------------------------
--  DDL for Table PRE_CADASTRO_MEDICO_EMAIL
--------------------------------------------------------

  CREATE TABLE "PRE_CADASTRO_MEDICO_EMAIL" 
   (	"PCEM_ID" NUMBER, 
	"PCEM_TX_EMAIL" VARCHAR2(100), 
	"PCEM_IN_PRINCIPAL" NUMBER(1,0), 
	"PRCM_ID" NUMBER
   ) ;

   COMMENT ON COLUMN "PRE_CADASTRO_MEDICO_EMAIL"."PCEM_ID" IS 'Identificação da tabela.';
   COMMENT ON COLUMN "PRE_CADASTRO_MEDICO_EMAIL"."PCEM_TX_EMAIL" IS 'E-mail de contato do médico.';
   COMMENT ON COLUMN "PRE_CADASTRO_MEDICO_EMAIL"."PCEM_IN_PRINCIPAL" IS 'Indica se o contato é o principal ou não (somente um contato pode ser marcado).';
   COMMENT ON COLUMN "PRE_CADASTRO_MEDICO_EMAIL"."PRCM_ID" IS 'ID do pré cadastro do médico.';
   COMMENT ON TABLE "PRE_CADASTRO_MEDICO_EMAIL"  IS 'Tabela que guarda os e-mails para contato com o médico inseridos no pré cadastro.';
--------------------------------------------------------
--  DDL for Table PRE_CADASTRO_MEDICO_ENDERECO
--------------------------------------------------------

  CREATE TABLE "PRE_CADASTRO_MEDICO_ENDERECO" 
   (	"PCME_ID" NUMBER, 
	"PCME_ID_PAIS" NUMBER, 
	"PCME_TX_CEP" VARCHAR2(9), 
	"PCME_TX_TIPO_LOGRADOURO" VARCHAR2(100), 
	"PCME_TX_NOME_LOGRADOURO" VARCHAR2(200), 
	"PCME_TX_COMPLEMENTO" VARCHAR2(255), 
	"PCME_TX_BAIRRO" VARCHAR2(255), 
	"PCME_NR_NUMERO" VARCHAR2(10),
	"MUNI_ID" NUMBER
   ) ;

   COMMENT ON COLUMN "PRE_CADASTRO_MEDICO_ENDERECO"."PCME_ID" IS 'Identificação da tabela.';
   COMMENT ON COLUMN "PRE_CADASTRO_MEDICO_ENDERECO"."PCME_ID_PAIS" IS 'País em que reside o médico.';
   COMMENT ON COLUMN "PRE_CADASTRO_MEDICO_ENDERECO"."PCME_TX_CEP" IS 'CEP da residência do médico.';
   COMMENT ON COLUMN "PRE_CADASTRO_MEDICO_ENDERECO"."PCME_TX_TIPO_LOGRADOURO" IS 'Tipo do logradouro do médico.';
   COMMENT ON COLUMN "PRE_CADASTRO_MEDICO_ENDERECO"."PCME_TX_COMPLEMENTO" IS 'Tipo do logradouro do médico.';
   COMMENT ON COLUMN "PRE_CADASTRO_MEDICO_ENDERECO"."PCME_TX_BAIRRO" IS 'Tipo do logradouro do médico.';
   COMMENT ON COLUMN "PRE_CADASTRO_MEDICO_ENDERECO"."PCME_NR_NUMERO" IS 'Tipo do logradouro do médico.';
   COMMENT ON COLUMN "PRE_CADASTRO_MEDICO_ENDERECO"."MUNI_ID" IS 'Identificador do Muncípio';
   COMMENT ON TABLE "PRE_CADASTRO_MEDICO_ENDERECO"  IS 'Tabela que guarda o endereço defino para oo pré cadastro de médico, onde o mesmo se cadastra e solicita acesso ao Redome e pode ser ou não aprovado.';
--------------------------------------------------------
--  DDL for Table PRE_CADASTRO_MEDICO_TELEFONE
--------------------------------------------------------

  CREATE TABLE "PRE_CADASTRO_MEDICO_TELEFONE" 
   (	"PCMT_ID" NUMBER, 
	"PCMT_TX_NOME" VARCHAR2(100), 
	"PCMT_NR_COD_AREA" NUMBER(5,0), 
	"PCMT_TX_COMPLEMENTO" VARCHAR2(20), 
	"PCMT_IN_TIPO" NUMBER, 
	"PCMT_NR_NUMERO" NUMBER, 
	"PCMT_IN_PRINCIPAL" NUMBER(1,0), 
	"PRCM_ID" NUMBER
   ) ;

   COMMENT ON COLUMN "PRE_CADASTRO_MEDICO_TELEFONE"."PCMT_ID" IS 'Identificação da tabela.';
   COMMENT ON COLUMN "PRE_CADASTRO_MEDICO_TELEFONE"."PCMT_TX_NOME" IS 'Nome do contato do médico pré cadastrado.';
   COMMENT ON COLUMN "PRE_CADASTRO_MEDICO_TELEFONE"."PCMT_NR_COD_AREA" IS 'Código de área do número informado';
   COMMENT ON COLUMN "PRE_CADASTRO_MEDICO_TELEFONE"."PCMT_TX_COMPLEMENTO" IS 'Texto livre utilizado para alguma informação adicional ao contato.';
   COMMENT ON COLUMN "PRE_CADASTRO_MEDICO_TELEFONE"."PCMT_IN_TIPO" IS 'Tipo do contato, se é Fixo ou Celular.';
   COMMENT ON COLUMN "PRE_CADASTRO_MEDICO_TELEFONE"."PCMT_NR_NUMERO" IS 'Número do telefonte de contato informado.';
   COMMENT ON COLUMN "PRE_CADASTRO_MEDICO_TELEFONE"."PCMT_IN_PRINCIPAL" IS 'Indica se o contato é o principal ou não (somente um contato pode ser marcado).';
   COMMENT ON COLUMN "PRE_CADASTRO_MEDICO_TELEFONE"."PRCM_ID" IS 'ID do pré cadastro do médico.';
   COMMENT ON TABLE "PRE_CADASTRO_MEDICO_TELEFONE"  IS 'Tabela que guarda os telefones de contato do médico inseridos no pré cadastro.';
--------------------------------------------------------
--  DDL for Table PRE_CAD_MEDICO_CT_REFERENCIA
--------------------------------------------------------

  CREATE TABLE "PRE_CAD_MEDICO_CT_REFERENCIA" 
   (	"PRCM_ID" NUMBER, 
	"CETR_ID" NUMBER
   ) ;

   COMMENT ON COLUMN "PRE_CAD_MEDICO_CT_REFERENCIA"."PRCM_ID" IS 'Identificação da tabela pré cadastro médico.';
   COMMENT ON COLUMN "PRE_CAD_MEDICO_CT_REFERENCIA"."CETR_ID" IS 'Identificação da tabela centro transplante.';
   COMMENT ON TABLE "PRE_CAD_MEDICO_CT_REFERENCIA"  IS 'Tabela de relacionamento entre o pré cadastro do médico e os centros de referência que ele atua (centros avaliadores).';
--------------------------------------------------------
--  DDL for Table PRESCRICAO
--------------------------------------------------------

  CREATE TABLE "PRESCRICAO" 
   (	"PRES_DT_COLETA_1" DATE, 
	"PRES_DT_COLETA_2" DATE, 
	"PRES_DT_RESULTADO_WORKUP_1" DATE, 
	"PRES_DT_RESULTADO_WORKUP_2" DATE, 
	"SOLI_ID" NUMBER, 
	"FOCE_ID_OPCAO_1" NUMBER(2,0), 
	"PRES_VL_QUANT_TOTAL_OPCAO_1" NUMBER(6,2), 
	"PRES_ID" NUMBER, 
	"FOCE_ID_OPCAO_2" NUMBER(2,0), 
	"PRES_VL_QUANT_KG_OPCAO_1" NUMBER(6,2), 
	"PRES_VL_QUANT_TOTAL_OPCAO_2" NUMBER(6,2), 
	"PRES_VL_QUANT_KG_OPCAO_2" NUMBER(6,2), 
	"EVOL_ID" NUMBER, 
	"AVPR_ID" NUMBER, 
	"MEDI_ID" NUMBER
   ) ;

   COMMENT ON COLUMN "PRESCRICAO"."PRES_DT_COLETA_1" IS 'Primeira opção para data de coleta';
   COMMENT ON COLUMN "PRESCRICAO"."PRES_DT_COLETA_2" IS 'Segunda opção para data de coleta';
   COMMENT ON COLUMN "PRESCRICAO"."PRES_DT_RESULTADO_WORKUP_1" IS 'Primeira opção para data de resultado de exame de workup';
   COMMENT ON COLUMN "PRESCRICAO"."PRES_DT_RESULTADO_WORKUP_2" IS 'Segunda opção para data de resultado de exame de workup';
   COMMENT ON COLUMN "PRESCRICAO"."SOLI_ID" IS 'Identificador da solicitação.';
   COMMENT ON COLUMN "PRESCRICAO"."FOCE_ID_OPCAO_1" IS 'Identificador do tipo de fonte de células para a opção 1.';
   COMMENT ON COLUMN "PRESCRICAO"."PRES_VL_QUANT_TOTAL_OPCAO_1" IS 'Quantidade total de TCN ou CD34 para a opção 1.';
   COMMENT ON COLUMN "PRESCRICAO"."PRES_ID" IS 'Identificador da prescrição.';
   COMMENT ON COLUMN "PRESCRICAO"."FOCE_ID_OPCAO_2" IS 'Identificador do tipo de fonte de células para a opção 2.';
   COMMENT ON COLUMN "PRESCRICAO"."PRES_VL_QUANT_KG_OPCAO_1" IS 'Quantidade por kg de TCN ou CD34 para a opção 1.';
   COMMENT ON COLUMN "PRESCRICAO"."PRES_VL_QUANT_TOTAL_OPCAO_2" IS 'Quantidade total de TCN ou CD34 para a opção 2.';
   COMMENT ON COLUMN "PRESCRICAO"."PRES_VL_QUANT_KG_OPCAO_2" IS 'Quantidade por kg de TCN ou CD34 para a opção 1.';
   COMMENT ON COLUMN "PRESCRICAO"."EVOL_ID" IS 'Identificador da Evolução.';
   COMMENT ON COLUMN "PRESCRICAO"."AVPR_ID" IS 'Identificador para Avaliação Prescrição';
   COMMENT ON COLUMN "PRESCRICAO"."MEDI_ID" IS 'Identificador para o Médico Responsável';
   COMMENT ON TABLE "PRESCRICAO"  IS 'Armazenamento de prescrição médica para workup e coleta';
--------------------------------------------------------
--  DDL for Table QUALIFICACAO_MATCH
--------------------------------------------------------

  CREATE TABLE "QUALIFICACAO_MATCH" 
   (	"QUMA_ID" NUMBER, 
	"LOCU_ID" VARCHAR2(4), 
	"QUMA_TX_QUALIFICACAO" VARCHAR2(20), 
	"QUMA_NR_POSICAO" NUMBER, 
	"MATC_ID" NUMBER, 
	"DOAD_ID" NUMBER, 
	"QUMA_TX_GENOTIPO" VARCHAR2(40), 
	"QUMA_NR_TIPO" NUMBER,
	"QUMA_TX_PROBABILIDADE" VARCHAR2(5)
   ) ;
		
   COMMENT ON COLUMN "QUALIFICACAO_MATCH"."QUMA_ID" IS 'Chave primária da tabela de qualificaçãod e match';
   COMMENT ON COLUMN "QUALIFICACAO_MATCH"."LOCU_ID" IS 'Chave estrangeira para tabela de locus';
   COMMENT ON COLUMN "QUALIFICACAO_MATCH"."QUMA_TX_QUALIFICACAO" IS 'Qualificação dividida em: M -> MISMATCH L -> P -> A ->';
   COMMENT ON COLUMN "QUALIFICACAO_MATCH"."QUMA_NR_POSICAO" IS '1 - Alelo 1 2 - Alelo 2';
   COMMENT ON COLUMN "QUALIFICACAO_MATCH"."MATC_ID" IS 'Chave estrangeira para tabela de match';
   COMMENT ON COLUMN "QUALIFICACAO_MATCH"."DOAD_ID" IS 'Chave estrangeira para tabela de doador';
   COMMENT ON COLUMN "QUALIFICACAO_MATCH"."QUMA_TX_GENOTIPO" IS 'Genótipo comparado entre o paciente e o doador.';
   COMMENT ON COLUMN "QUALIFICACAO_MATCH"."QUMA_NR_TIPO" IS '0- para sorologia, 1- para antígeno, 2- NMDP, 3- Grupo G, 4-  Grupo P e 5 Alelo';
   COMMENT ON COLUMN "QUALIFICACAO_MATCH"."QUMA_TX_PROBABILIDADE" IS 'probabilidade de compatibilidade.';
   COMMENT ON TABLE "QUALIFICACAO_MATCH"  IS 'Tabela de qualificação de um match';
--------------------------------------------------------
--  DDL for Table QUALIFICACAO_MATCH_PRELIMINAR
--------------------------------------------------------

  CREATE TABLE "QUALIFICACAO_MATCH_PRELIMINAR" 
   (	"QUMP_ID" NUMBER, 
	"LOCU_ID" VARCHAR2(4), 
	"QUMP_TX_QUALIFICACAO" VARCHAR2(20), 
	"QUMP_NR_POSICAO" NUMBER, 
	"MAPR_ID" NUMBER, 
	"DOAD_ID" NUMBER, 
	"QUMP_TX_GENOTIPO" VARCHAR2(40), 
	"QUMP_NR_TIPO" NUMBER
   ) ;

   COMMENT ON COLUMN "QUALIFICACAO_MATCH_PRELIMINAR"."QUMP_ID" IS 'Chave primária da tabela de qualificação e match';
   COMMENT ON COLUMN "QUALIFICACAO_MATCH_PRELIMINAR"."LOCU_ID" IS 'Chave estrangeira para tabela de locus';
   COMMENT ON COLUMN "QUALIFICACAO_MATCH_PRELIMINAR"."QUMP_TX_QUALIFICACAO" IS 'Qualificação dividida em: M -> MISMATCH L -> P -> A ->';
   COMMENT ON COLUMN "QUALIFICACAO_MATCH_PRELIMINAR"."QUMP_NR_POSICAO" IS '1 - Alelo 1 2 - Alelo 2';
   COMMENT ON COLUMN "QUALIFICACAO_MATCH_PRELIMINAR"."MAPR_ID" IS 'Chave estrangeira para tabela de match preliminar';
   COMMENT ON COLUMN "QUALIFICACAO_MATCH_PRELIMINAR"."DOAD_ID" IS 'Chave estrangeira para tabela de doador';
   COMMENT ON COLUMN "QUALIFICACAO_MATCH_PRELIMINAR"."QUMP_TX_GENOTIPO" IS 'Genótipo comparado entre o paciente e o doador.';
   COMMENT ON COLUMN "QUALIFICACAO_MATCH_PRELIMINAR"."QUMP_NR_TIPO" IS '0- para sorologia, 1- para antígeno, 2- NMDP, 3- Grupo G, 4-  Grupo P e 5 Alelo';
--------------------------------------------------------
--  DDL for Table RACA
--------------------------------------------------------

  CREATE TABLE "RACA" 
   (	"RACA_ID" NUMBER, 
	"RACA_TX_NOME" VARCHAR2(50),
	"RACA_NR_ID_REDOMEWEB" NUMBER
   ) ;

   COMMENT ON COLUMN "RACA"."RACA_ID" IS 'Identifiador da raca';
   COMMENT ON COLUMN "RACA"."RACA_TX_NOME" IS 'Nome da raca';
   COMMENT ON COLUMN "RACA"."RACA_NR_ID_REDOMEWEB" IS 'Identificador da raça na base do REDOMEWeb (integração).';
   COMMENT ON TABLE "RACA"  IS 'Raças';
--------------------------------------------------------
--  DDL for Table RASCUNHO
--------------------------------------------------------

  CREATE TABLE "RASCUNHO" 
   (	"RASC_ID" NUMBER, 
	"USUA_ID" NUMBER, 
	"RASC_TX_JSON" BLOB
   ) ;

   COMMENT ON COLUMN "RASCUNHO"."RASC_ID" IS 'Identificação do rascunho ';
   COMMENT ON COLUMN "RASCUNHO"."USUA_ID" IS 'Referência do usuário que é dono do rascunho';
   COMMENT ON COLUMN "RASCUNHO"."RASC_TX_JSON" IS 'Objeto em formato JSON do paciente ';
--------------------------------------------------------
--  DDL for Table RECEBIMENTO_COLETA
--------------------------------------------------------

  CREATE TABLE "RECEBIMENTO_COLETA" 
   (	"RECE_ID" NUMBER, 
	"PECL_ID" NUMBER, 
	"FOCE_ID" NUMBER, 
	"DECO_ID" NUMBER, 
	"RECI_DT_INFUSAO" DATE, 
	"RECI_DT_DESCARTE" DATE, 
	"RECI_DT_PREVISTA_INFUSAO" DATE, 
	"RECI_NR_TOTAL_TCN" NUMBER, 
	"RECI_NR_TOTAL_CD34" NUMBER, 
	"RECI_TX_JUST_CONGELAMENTO" VARCHAR2(500), 
	"RECI_TX_JUST_DESCARTE" VARCHAR2(500), 
	"RECI_IN_RECEBEU" NUMBER, 
	"RECI_DT_RECEBIMENTO" TIMESTAMP (6)
   ) ;

   COMMENT ON COLUMN "RECEBIMENTO_COLETA"."RECE_ID" IS 'ID DE RECEBIMENTO DE COLETA';
   COMMENT ON COLUMN "RECEBIMENTO_COLETA"."PECL_ID" IS 'REFERENCIA DE PEDIDO DE COLETA';
   COMMENT ON COLUMN "RECEBIMENTO_COLETA"."FOCE_ID" IS 'REFERENICA PARA A FONTE DA AMOSTRA';
   COMMENT ON COLUMN "RECEBIMENTO_COLETA"."DECO_ID" IS 'REFERENCIA PARA O TIPO DE DESTINO QUE FOI DADO A AMOSTRA';
   COMMENT ON COLUMN "RECEBIMENTO_COLETA"."RECI_DT_INFUSAO" IS 'DATA DA INSUSÃO CASO A COLETA TENHA SIDO INFUDIDA ';
   COMMENT ON COLUMN "RECEBIMENTO_COLETA"."RECI_DT_DESCARTE" IS 'DATA DO DESCARTE CASO A COLETA TENHA SIDO DESCARTADA';
   COMMENT ON COLUMN "RECEBIMENTO_COLETA"."RECI_DT_PREVISTA_INFUSAO" IS 'DATA PREVISTA PARA INFUSÃO CASO A COLETA TENHA SIDO CONGELADA ';
   COMMENT ON COLUMN "RECEBIMENTO_COLETA"."RECI_NR_TOTAL_TCN" IS 'TOTAL DE CELULAS NUCLEADAS CASO A FONTE SEJA TCN';
   COMMENT ON COLUMN "RECEBIMENTO_COLETA"."RECI_NR_TOTAL_CD34" IS 'TOTAL DE CD34 CASO A FONTE SEJA CD34';
   COMMENT ON COLUMN "RECEBIMENTO_COLETA"."RECI_TX_JUST_CONGELAMENTO" IS 'JUSTIFICATIVA PARA CASO A AMOSTRA TENHA SIDO CONGELADA';
   COMMENT ON COLUMN "RECEBIMENTO_COLETA"."RECI_TX_JUST_DESCARTE" IS 'JUSTIFICATIVA DE DESCARTE CASO A AMOSTRA TENHA SIDO DESCARTADA';
   COMMENT ON COLUMN "RECEBIMENTO_COLETA"."RECI_IN_RECEBEU" IS '1 SE RECEBEU A COLETA E 0 SE NÃO RECEBEU';
   COMMENT ON COLUMN "RECEBIMENTO_COLETA"."RECI_DT_RECEBIMENTO" IS 'DATA DO RECEBIMENTO DA COLETA NO CENTRO DE TRANSPLANTE';
   COMMENT ON TABLE "RECEBIMENTO_COLETA"  IS 'REGISTRO DE RECEBIMENTO E DESTINO DADO PARA A COLETA FEITA DO DOADOR.';
--------------------------------------------------------
--  DDL for Table RECURSO
--------------------------------------------------------

  CREATE TABLE "RECURSO" 
   (	"RECU_ID" NUMBER, 
	"RECU_TX_SIGLA" VARCHAR2(50), 
	"RECU_TX_DESCRICAO" VARCHAR2(200)
   ) ;

   COMMENT ON COLUMN "RECURSO"."RECU_ID" IS 'Identificador do recurso.';
   COMMENT ON COLUMN "RECURSO"."RECU_TX_SIGLA" IS 'Sigla do recurso.';
   COMMENT ON COLUMN "RECURSO"."RECU_TX_DESCRICAO" IS 'Descrição do recurso.';
   COMMENT ON TABLE "RECURSO"  IS 'Recursos da aplicação.';
--------------------------------------------------------
--  DDL for Table REGISTRO
--------------------------------------------------------

  CREATE TABLE "REGISTRO" 
   (	"REGI_ID" NUMBER, 
	"REGI_TX_NOME" VARCHAR2(20),
	"REGI_TX_SIGLA" VARCHAR2(2),
	"PAIS_ID" NUMBER,
	"REGI_NR_DON_POOL" NUMBER
   ) ;

   COMMENT ON COLUMN "REGISTRO"."REGI_ID" IS 'Identificador único da tabela.';
   COMMENT ON COLUMN "REGISTRO"."REGI_TX_NOME" IS 'Nome do registro.';
   COMMENT ON COLUMN "REGISTRO"."REGI_TX_SIGLA" IS 'Silga do registro.';
   COMMENT ON COLUMN "REGISTRO"."PAIS_ID" IS 'Identificador do país do registro.';
   COMMENT ON COLUMN "REGISTRO"."REGI_NR_DON_POOL"  IS 'Identificador internacional do registro utilizado no EMDIS e WMDA';
   
--------------------------------------------------------
--  DDL for Table RELATORIO
--------------------------------------------------------

  CREATE TABLE "RELATORIO" 
   (	"RELA_ID" NUMBER, 
	"RELA_TX_CODIGO" VARCHAR2(40), 
	"RELA_IN_TIPO" NUMBER, 
	"RELA_TX_HTML" CLOB
   ) ;

   COMMENT ON COLUMN "RELATORIO"."RELA_ID" IS 'Identificador do relatorio';
   COMMENT ON COLUMN "RELATORIO"."RELA_IN_TIPO" IS '1 - pdf 2 - Email';
   COMMENT ON COLUMN "RELATORIO"."RELA_TX_HTML" IS 'arquivo no formato html';
   COMMENT ON TABLE "RELATORIO"  IS 'Tabela para guardar os relatórios do sistema.';
--------------------------------------------------------
--  DDL for Table RESERVA_DOADOR_INTERNACIONAL
--------------------------------------------------------

  CREATE TABLE "RESERVA_DOADOR_INTERNACIONAL" 
   (	"REDI_ID" NUMBER, 
	"REDI_DT_ATUALIZACAO" TIMESTAMP (6), 
	"DOAD_ID" NUMBER, 
	"BUSC_ID" NUMBER, 
	"REDI_TX_STATUS" VARCHAR2(50)
   ) ;

   COMMENT ON COLUMN "RESERVA_DOADOR_INTERNACIONAL"."REDI_ID" IS 'Identificador sequencial da tabela';
   COMMENT ON COLUMN "RESERVA_DOADOR_INTERNACIONAL"."REDI_DT_ATUALIZACAO" IS 'Data que ocorreu a última atualização no registro.';
   COMMENT ON COLUMN "RESERVA_DOADOR_INTERNACIONAL"."DOAD_ID" IS 'Doador internacional que foi reservado para o paciente.';
   COMMENT ON COLUMN "RESERVA_DOADOR_INTERNACIONAL"."BUSC_ID" IS 'Busca do paciente que recebeu a reserva do doador.';
   COMMENT ON COLUMN "RESERVA_DOADOR_INTERNACIONAL"."REDI_TX_STATUS" IS 'Status da reserva entre doador e paciente. Pode ser ATIVO ou INATIVO.';
   COMMENT ON TABLE "RESERVA_DOADOR_INTERNACIONAL"  IS 'Tabela que guarda a reserva do doador internacional para um paciente.';
--------------------------------------------------------
--  DDL for Table RESPONSAVEL
--------------------------------------------------------

  CREATE TABLE "RESPONSAVEL" 
   (	"RESP_ID" NUMBER, 
	"RESP_TX_CPF" VARCHAR2(11), 
	"RESP_TX_NOME" VARCHAR2(255), 
	"RESP_TX_PARENTESCO" VARCHAR2(20)
   ) ;

   COMMENT ON COLUMN "RESPONSAVEL"."RESP_ID" IS 'Identificador do responsável';
   COMMENT ON COLUMN "RESPONSAVEL"."RESP_TX_CPF" IS 'CPF do Responsável';
   COMMENT ON COLUMN "RESPONSAVEL"."RESP_TX_NOME" IS 'Nome do Responsável';
   COMMENT ON COLUMN "RESPONSAVEL"."RESP_TX_PARENTESCO" IS 'Parentesco com o paciente';
   COMMENT ON TABLE "RESPONSAVEL"  IS 'Responsável do paciente';
--------------------------------------------------------
--  DDL for Table RESPONSAVEL_AUD
--------------------------------------------------------

  CREATE TABLE "RESPONSAVEL_AUD" 
   (	"RESP_ID" NUMBER, 
	"AUDI_ID" NUMBER, 
	"AUDI_TX_TIPO" NUMBER(3,0), 
	"RESP_TX_CPF" VARCHAR2(255), 
	"RESP_TX_NOME" VARCHAR2(255), 
	"RESP_TX_PARENTESCO" VARCHAR2(255)
   ) ;

   COMMENT ON TABLE "RESPONSAVEL_AUD"  IS 'Tabela de auditoria da tabela RESPONSAVEL.';
--------------------------------------------------------
--  DDL for Table RESPOSTA_CHECKLIST
--------------------------------------------------------

  CREATE TABLE "RESPOSTA_CHECKLIST" 
   (	"RESC_ID" NUMBER, 
	"RESC_IN_RESPOSTA" NUMBER, 
	"ITEC_ID" NUMBER, 
	"PELO_ID" NUMBER
   ) ;

   COMMENT ON COLUMN "RESPOSTA_CHECKLIST"."RESC_ID" IS 'Id sequencial de resposta de checklist';
   COMMENT ON COLUMN "RESPOSTA_CHECKLIST"."RESC_IN_RESPOSTA" IS 'Resposta como true ou false ao item de checklist';
   COMMENT ON COLUMN "RESPOSTA_CHECKLIST"."ITEC_ID" IS 'Item de checklist ao qual está sendo respondido';
   COMMENT ON COLUMN "RESPOSTA_CHECKLIST"."PELO_ID" IS 'Referencia de logistica ';
--------------------------------------------------------
--  DDL for Table RESPOSTA_FORMULARIO_DOADOR
--------------------------------------------------------

  CREATE TABLE "RESPOSTA_FORMULARIO_DOADOR" 
   (	"REFD_ID" NUMBER, 
	"FODO_ID" NUMBER, 
	"REFD_TX_TOKEN" VARCHAR2(50), 
	"REFD_TX_RESPOSTA" VARCHAR2(4000)
   ) ;

   COMMENT ON COLUMN "RESPOSTA_FORMULARIO_DOADOR"."REFD_ID" IS 'Chave primária que identifica com exclusividade uma resposta_formulário_doador.';
   COMMENT ON COLUMN "RESPOSTA_FORMULARIO_DOADOR"."FODO_ID" IS 'Chave estrangeira para o formulario_doador.';
   COMMENT ON COLUMN "RESPOSTA_FORMULARIO_DOADOR"."REFD_TX_TOKEN" IS 'Token da pergunta.';
   COMMENT ON COLUMN "RESPOSTA_FORMULARIO_DOADOR"."REFD_TX_RESPOSTA" IS 'Valor respondido.';
   COMMENT ON TABLE "RESPOSTA_FORMULARIO_DOADOR"  IS 'Resposta do formulário associado ao token';
--------------------------------------------------------
--  DDL for Table RESPOSTA_PEDIDO_ADICIONAL
--------------------------------------------------------

  CREATE TABLE "RESPOSTA_PEDIDO_ADICIONAL" 
   (	"REPA_ID" NUMBER, 
	"USUA_ID" NUMBER, 
	"REPA_DT_DATA" TIMESTAMP (6), 
	"REPA_TX_RESPOSTA" VARCHAR2(200), 
	"ARRW_ID" NUMBER, 
	"PEAW_ID" NUMBER
   ) ;

   COMMENT ON COLUMN "RESPOSTA_PEDIDO_ADICIONAL"."REPA_ID" IS 'Identificador da resposta a um pedido adicional.';
   COMMENT ON COLUMN "RESPOSTA_PEDIDO_ADICIONAL"."USUA_ID" IS 'Identificador do usuário que respondeu.';
   COMMENT ON COLUMN "RESPOSTA_PEDIDO_ADICIONAL"."REPA_DT_DATA" IS 'Data da resposta.';
   COMMENT ON COLUMN "RESPOSTA_PEDIDO_ADICIONAL"."REPA_TX_RESPOSTA" IS 'Texto da resposta.';
   COMMENT ON COLUMN "RESPOSTA_PEDIDO_ADICIONAL"."ARRW_ID" IS 'Identificador do arquivo de resultado de workup associado a essa resposta.';
   COMMENT ON COLUMN "RESPOSTA_PEDIDO_ADICIONAL"."PEAW_ID" IS 'Identificador do pedido adicional de workup.';
   COMMENT ON TABLE "RESPOSTA_PEDIDO_ADICIONAL"  IS 'Mensagens trocadas sobre um pedido adicional de workup.';
--------------------------------------------------------
--  DDL for Table RESPOSTA_PENDENCIA
--------------------------------------------------------

  CREATE TABLE "RESPOSTA_PENDENCIA" 
   (	"REPE_ID" NUMBER, 
	"USUA_ID" NUMBER, 
	"REPE_DT_DATA" TIMESTAMP (6), 
	"REPE_TX_RESPOSTA" VARCHAR2(200), 
	"EXAM_ID" NUMBER, 
	"EVOL_ID" NUMBER
   ) ;

   COMMENT ON COLUMN "RESPOSTA_PENDENCIA"."REPE_ID" IS 'Chave primária da tabela de resposta';
   COMMENT ON COLUMN "RESPOSTA_PENDENCIA"."USUA_ID" IS 'Chave estrangeira para tabela de usuário, informa quem gravou a resposta';
   COMMENT ON COLUMN "RESPOSTA_PENDENCIA"."REPE_DT_DATA" IS 'Data da resposta';
   COMMENT ON COLUMN "RESPOSTA_PENDENCIA"."REPE_TX_RESPOSTA" IS 'Descrição da resposta enviado pelo médico ou avaliador';
   COMMENT ON COLUMN "RESPOSTA_PENDENCIA"."EXAM_ID" IS 'Chave estrangeira da tabela EXAME';
   COMMENT ON COLUMN "RESPOSTA_PENDENCIA"."EVOL_ID" IS 'Chave estrangeira da tabela de evolução';
   COMMENT ON TABLE "RESPOSTA_PENDENCIA"  IS 'Mensagens trocadas entre o médico responsável e o médico avaliador sobre as pendências';
--------------------------------------------------------
--  DDL for Table RESPOSTA_TENTATIVA_CONTATO
--------------------------------------------------------

  CREATE TABLE "RESPOSTA_TENTATIVA_CONTATO" 
   (	"RETC_ID" NUMBER, 
	"RETC_TX_DESCRICAO" VARCHAR2(100)
   ) ;

   COMMENT ON COLUMN "RESPOSTA_TENTATIVA_CONTATO"."RETC_ID" IS 'Identificador sequencial da tabela.';
   COMMENT ON COLUMN "RESPOSTA_TENTATIVA_CONTATO"."RETC_TX_DESCRICAO" IS 'Descrição do resultado da tentativa de contato realizada.';
   COMMENT ON TABLE "RESPOSTA_TENTATIVA_CONTATO"  IS 'Resposta para a tentativa de contato com o doador.';
--------------------------------------------------------
--  DDL for Table RESSALVA_DOADOR
--------------------------------------------------------

  CREATE TABLE "RESSALVA_DOADOR" 
   (	"REDO_ID" NUMBER, 
	"REDO_TX_OBSERVACAO" VARCHAR2(255), 
	"DOAD_ID" NUMBER, 
	"REDO_DT_CRIACAO" DATE, 
	"USUA_ID_CRIACAO" NUMBER, 
	"REDO_IN_EXCLUIDO" NUMBER(1,0) DEFAULT 0
   ) ;

   COMMENT ON COLUMN "RESSALVA_DOADOR"."REDO_ID" IS 'Identificador sequencial para a ressalva do doador.';
   COMMENT ON COLUMN "RESSALVA_DOADOR"."REDO_TX_OBSERVACAO" IS 'Texto explicativo sobre o motivo da ressalva do doador.';
   COMMENT ON COLUMN "RESSALVA_DOADOR"."DOAD_ID" IS 'Identificador do doador associado a ressalva.';
   COMMENT ON COLUMN "RESSALVA_DOADOR"."REDO_DT_CRIACAO" IS 'Data em que a ressalva foi criada.';
   COMMENT ON COLUMN "RESSALVA_DOADOR"."USUA_ID_CRIACAO" IS 'Usuário responsável pela adição da ressalva.';
   COMMENT ON COLUMN "RESSALVA_DOADOR"."REDO_IN_EXCLUIDO" IS 'Indicador que a ressalva foi excluída. 1-Sim, 0-Não.';
   COMMENT ON TABLE "RESSALVA_DOADOR"  IS 'Ressalvas, associadas ao doador, que devem ser consideradas durante o processo de busca ou match.';
--------------------------------------------------------
--  DDL for Table RESSALVA_DOADOR_AUD
--------------------------------------------------------

  CREATE TABLE "RESSALVA_DOADOR_AUD" 
   (	"REDO_ID" NUMBER(19,0), 
	"AUDI_ID" NUMBER(19,0), 
	"AUDI_TX_TIPO" NUMBER(3,0), 
	"REDO_TX_OBSERVACAO" VARCHAR2(255 CHAR), 
	"DOAD_ID" NUMBER(19,0), 
	"REDO_DT_CRIACAO" DATE, 
	"USUA_ID_CRIACAO" NUMBER, 
	"REDO_IN_EXCLUIDO" NUMBER(1,0)
   ) ;

   COMMENT ON COLUMN "RESSALVA_DOADOR_AUD"."REDO_ID" IS 'Id da ressalva';
   COMMENT ON COLUMN "RESSALVA_DOADOR_AUD"."AUDI_ID" IS 'Id da auditoria';
   COMMENT ON COLUMN "RESSALVA_DOADOR_AUD"."AUDI_TX_TIPO" IS 'Tipo da auditoria';
   COMMENT ON COLUMN "RESSALVA_DOADOR_AUD"."REDO_TX_OBSERVACAO" IS 'Texto da observação';
   COMMENT ON COLUMN "RESSALVA_DOADOR_AUD"."DOAD_ID" IS 'Id do doador';
   COMMENT ON TABLE "RESSALVA_DOADOR_AUD"  IS 'Tabela de auditoria da table Ressalva Doador.';
--------------------------------------------------------
--  DDL for Table RESULTADO_WORKUP
--------------------------------------------------------

  CREATE TABLE "RESULTADO_WORKUP" 
   (	"REWO_ID" NUMBER, 
	"REWO_DT_CRIACAO" TIMESTAMP (6), 
	"USUA_ID" NUMBER, 
	"PEWO_ID" NUMBER
   ) ;

   COMMENT ON COLUMN "RESULTADO_WORKUP"."REWO_ID" IS 'Identificador do resultado de workup.';
   COMMENT ON COLUMN "RESULTADO_WORKUP"."REWO_DT_CRIACAO" IS 'Data de criação do resultado.';
   COMMENT ON COLUMN "RESULTADO_WORKUP"."USUA_ID" IS 'Usuário que efetuou a última atualização do resultado.';
   COMMENT ON COLUMN "RESULTADO_WORKUP"."PEWO_ID" IS 'Identificador do pedido de workup.';
   COMMENT ON TABLE "RESULTADO_WORKUP"  IS 'Tabela responsável por armazenar o resultado dos pedidos de workup.';
--------------------------------------------------------
--  DDL for Table SISTEMA
--------------------------------------------------------

  CREATE TABLE "SISTEMA" 
   (	"SIST_ID" NUMBER, 
	"SIST_TX_NOME" VARCHAR2(100), 
	"SIST_IN_DISPONIVEL_REDOME" NUMBER
   ) ;

   COMMENT ON COLUMN "SISTEMA"."SIST_ID" IS 'Chave primária autoincremental e sequencial da tabela.';
   COMMENT ON COLUMN "SISTEMA"."SIST_TX_NOME" IS 'Nome do sistema.';
   COMMENT ON COLUMN "SISTEMA"."SIST_IN_DISPONIVEL_REDOME" IS 'Indica quando o Redome pode ou não criar um novo usuário utilizando com perfil associado a este sistema. Flag definido para separar o que é de obrigação de terceiros (parceiro) ou do Redome.';
   COMMENT ON TABLE "SISTEMA"  IS 'Tabela que armazena quais são os sistemas (parceiros) envolvidos com o ModRed.';
--------------------------------------------------------
--  DDL for Table SOLICITACAO
--------------------------------------------------------

  CREATE TABLE "SOLICITACAO" 
   (	"SOLI_ID" NUMBER, 
	"TISO_ID" NUMBER, 
	"PACI_NR_RMR" NUMBER, 
	"USUA_ID" NUMBER, 
	"SOLI_DT_CRIACAO" TIMESTAMP (6), 
	"SOLI_NR_STATUS" NUMBER, 
	"MATC_ID" NUMBER, 
	"BUSC_ID" NUMBER, 
	"TIEX_ID" NUMBER, 
	"LABO_ID" NUMBER, 
	"SOLI_IN_RESOLVER_DIVERGENCIA" NUMBER,
    "USUA_ID_RESPONSAVEL" NUMBER,
    "FAWO_ID" NUMBER,
    "MOCS_ID" NUMBER,
    "CETR_ID_TRANSPLANTE" NUMBER,
    "CETR_ID_COLETA" NUMBER,
    "SOLI_IN_POSCOLETA" NUMBER, 
	"SOLI_IN_CONTAGEM_CELULA" NUMBER
   ) ;

   COMMENT ON COLUMN "SOLICITACAO"."SOLI_ID" IS 'Referência da solicitação';
   COMMENT ON COLUMN "SOLICITACAO"."TISO_ID" IS 'Id do tipo de solicitação';
   COMMENT ON COLUMN "SOLICITACAO"."PACI_NR_RMR" IS 'Id do paciente';
   COMMENT ON COLUMN "SOLICITACAO"."USUA_ID" IS 'Usuário que fez a solicitação';
   COMMENT ON COLUMN "SOLICITACAO"."SOLI_DT_CRIACAO" IS 'Data de criação da solicitação';
   COMMENT ON COLUMN "SOLICITACAO"."SOLI_NR_STATUS" IS 'Status da solicitação: 1-aberta, 2-concluída e 3-cancelada.';
   COMMENT ON COLUMN "SOLICITACAO"."MATC_ID" IS 'Chave estrangeira para o match';
   COMMENT ON COLUMN "SOLICITACAO"."TIEX_ID" IS 'Chave estrangeira que guarda o tipo de exame solicitado para doador nacional.';
   COMMENT ON COLUMN "SOLICITACAO"."LABO_ID" IS 'Chave estrangeira que guarda o laboratório para doador nacional.';
   COMMENT ON COLUMN "SOLICITACAO"."SOLI_IN_RESOLVER_DIVERGENCIA" IS 'Flag que indica se a solicita��o foi criada para resolver divergencia no genotipo.';
   COMMENT ON COLUMN "SOLICITACAO"."USUA_ID_RESPONSAVEL" IS 'Usuario resonsavel pela solicitaçao de workup.';
   COMMENT ON COLUMN "SOLICITACAO"."SOLI_IN_POSCOLETA" IS 'Flag que indica se a solicitação tem formulário de pós coleta';
   COMMENT ON COLUMN "SOLICITACAO"."SOLI_IN_CONTAGEM_CELULA" IS 'Flag que indica se a solicitação tem formulário de Contagem Celula';
   COMMENT ON TABLE "SOLICITACAO"  IS 'Solicitação';
--------------------------------------------------------
--  DDL for Table SOLICITACAO_REDOMEWEB
--------------------------------------------------------

  CREATE TABLE "SOLICITACAO_REDOMEWEB" 
   (	"SORE_ID" NUMBER, 
	"SORE_NR_TIPO" NUMBER, 
	"SORE_ID_SOLICITACAO_REDOMEWEB" NUMBER, 
	"SORE_DT_CRIACAO" TIMESTAMP (6), 
	"PEEX_ID" NUMBER
   ) ;

   COMMENT ON COLUMN "SOLICITACAO_REDOMEWEB"."SORE_ID" IS 'Identificador sequencial da tabela.';
   COMMENT ON COLUMN "SOLICITACAO_REDOMEWEB"."SORE_NR_TIPO" IS 'Tipo da solicitação do redomeweb podendo ser (Exame ou amostra de sangue).';
   COMMENT ON COLUMN "SOLICITACAO_REDOMEWEB"."SORE_ID_SOLICITACAO_REDOMEWEB" IS 'Identificador da solicitação do redomeweb.';
   COMMENT ON COLUMN "SOLICITACAO_REDOMEWEB"."SORE_DT_CRIACAO" IS 'Data de criação.';
   COMMENT ON COLUMN "SOLICITACAO_REDOMEWEB"."PEEX_ID" IS 'Referência para a tabela de  pedido de exame.';
   COMMENT ON TABLE "SOLICITACAO_REDOMEWEB"  IS 'Tabela de relacionamento entro os pedidos de exame com as solicitações do redomeweb.';
--------------------------------------------------------
--  DDL for Table SPLIT_NMDP
--------------------------------------------------------

  CREATE TABLE "SPLIT_NMDP" 
   (	"TEVN_ID_CODIGO" VARCHAR2(10), 
	"SPNM_TX_GRUPO_ALELICO" VARCHAR2(3), 
	"SPNM_TX_VALOR_ALELICO" VARCHAR2(4), 
	"SPNM_DT_VERSAO" DATE
   ) ;

   COMMENT ON COLUMN "SPLIT_NMDP"."TEVN_ID_CODIGO" IS 'CÓDIGO NMDP';
   COMMENT ON COLUMN "SPLIT_NMDP"."SPNM_TX_GRUPO_ALELICO" IS 'Valor do grupo alelico';
   COMMENT ON COLUMN "SPLIT_NMDP"."SPNM_TX_VALOR_ALELICO" IS 'Valor alelico';
   COMMENT ON COLUMN "SPLIT_NMDP"."SPNM_DT_VERSAO" IS 'Data do arquivo';
   COMMENT ON TABLE "SPLIT_NMDP"  IS 'TABELA TEMPORÁRIA DE VALORES NMDP SEPARADOS POR GRUPO E VALOR ALELO';
--------------------------------------------------------
--  DDL for Table SPLIT_VALOR_DNA
--------------------------------------------------------

  CREATE TABLE "SPLIT_VALOR_DNA" 
   (	"LOCU_ID" VARCHAR2(4), 
	"SPVD_TX_GRUPO_ALELICO" VARCHAR2(3), 
	"SPVD_TX_VALOR_ALELICO" VARCHAR2(20), 
	"SPVD_IN_COMPARA_NMDP" NUMBER DEFAULT 0, 
	"SPVD_TX_VERSAO" VARCHAR2(6), 
	"SPVD_IN_ATIVO" NUMBER
   ) ;

   COMMENT ON COLUMN "SPLIT_VALOR_DNA"."LOCU_ID" IS 'Chave estrangeira para locus';
   COMMENT ON COLUMN "SPLIT_VALOR_DNA"."SPVD_TX_GRUPO_ALELICO" IS 'Valor do grupo alelico';
   COMMENT ON COLUMN "SPLIT_VALOR_DNA"."SPVD_TX_VALOR_ALELICO" IS 'Valor alelico';
   COMMENT ON COLUMN "SPLIT_VALOR_DNA"."SPVD_IN_COMPARA_NMDP" IS 'Flag que identifica se o valor será usuado para comparação com nmdp';
   COMMENT ON COLUMN "SPLIT_VALOR_DNA"."SPVD_TX_VERSAO" IS 'Versão do arquivo em que foi gerado o split';
   COMMENT ON COLUMN "SPLIT_VALOR_DNA"."SPVD_IN_ATIVO" IS 'Status do valor';
   COMMENT ON TABLE "SPLIT_VALOR_DNA"  IS 'TABELA DE VALORES DNA SEPARADOS POR GRUPO E PRIMEIRO ALELO';
--------------------------------------------------------
--  DDL for Table STATUS_AVALIACAO_CAMARA_TEC
--------------------------------------------------------

  CREATE TABLE "STATUS_AVALIACAO_CAMARA_TEC" 
   (	"STCT_ID" NUMBER, 
	"STCT_TX_DESCRICAO" VARCHAR2(80)
   ) ;

   COMMENT ON COLUMN "STATUS_AVALIACAO_CAMARA_TEC"."STCT_ID" IS 'Chave primária do status da avaliação de camera técnica';
   COMMENT ON COLUMN "STATUS_AVALIACAO_CAMARA_TEC"."STCT_TX_DESCRICAO" IS 'Texto dexcritivo do status da avaliação da câmera técnica';
   COMMENT ON TABLE "STATUS_AVALIACAO_CAMARA_TEC"  IS 'Tabela com status de avaliações de câmera técnica ';
--------------------------------------------------------
--  DDL for Table STATUS_BUSCA
--------------------------------------------------------

  CREATE TABLE "STATUS_BUSCA" 
   (	"STBU_ID" NUMBER, 
	"STBU_TX_DESCRICAO" VARCHAR2(60), 
	"STBU_IN_BUSCA_ATIVA" NUMBER(1,0) DEFAULT 0
   ) ;

   COMMENT ON COLUMN "STATUS_BUSCA"."STBU_ID" IS 'IDENTIFICAÇÃO DO STATUS DA BUSCA';
   COMMENT ON COLUMN "STATUS_BUSCA"."STBU_TX_DESCRICAO" IS 'DESCRIÇÃO DO STATUS DE BUSCA';
   COMMENT ON COLUMN "STATUS_BUSCA"."STBU_IN_BUSCA_ATIVA" IS 'AGRUPADOR DOS STATUS DA BUSCA, 0 - INATIVO E 1 - ATIVO';
--------------------------------------------------------
--  DDL for Table STATUS_DOADOR
--------------------------------------------------------

  CREATE TABLE "STATUS_DOADOR" 
   (	"STDO_ID" NUMBER, 
	"STDO_TX_DESCRICAO" VARCHAR2(40), 
	"STDO_IN_TEMPO_OBRIGATORIO" NUMBER DEFAULT 0
   ) ;

   COMMENT ON COLUMN "STATUS_DOADOR"."STDO_ID" IS 'Identificação do status doador';
   COMMENT ON COLUMN "STATUS_DOADOR"."STDO_TX_DESCRICAO" IS 'descricão do status doador';
   COMMENT ON COLUMN "STATUS_DOADOR"."STDO_IN_TEMPO_OBRIGATORIO" IS 'Coluna que define se o campo tempo é obrigatório.';
   COMMENT ON TABLE "STATUS_DOADOR"  IS 'Status do Doador';
--------------------------------------------------------
--  DDL for Table STATUS_PACIENTE
--------------------------------------------------------

  CREATE TABLE "STATUS_PACIENTE" 
   (	"STPA_ID" NUMBER, 
	"STPA_TX_DESCRICAO" VARCHAR2(60), 
	"STPA_NR_ORDEM" NUMBER
   ) ;

   COMMENT ON COLUMN "STATUS_PACIENTE"."STPA_ID" IS 'Id do status de paciente
';
   COMMENT ON COLUMN "STATUS_PACIENTE"."STPA_TX_DESCRICAO" IS 'Descrição do status ';
   COMMENT ON COLUMN "STATUS_PACIENTE"."STPA_NR_ORDEM" IS 'A fim de desempatar possiveis staus que aconteçam simultaneamente este campos deve ser informado para que o que tiver em uma ordem superior será listado primeiro
';
   COMMENT ON TABLE "STATUS_PACIENTE"  IS 'Domínio de status de paciente';
--------------------------------------------------------
--  DDL for Table STATUS_PAGAMENTO
--------------------------------------------------------

  CREATE TABLE "STATUS_PAGAMENTO" 
   (	"STPA_ID" NUMBER, 
	"STPA_TX_DESCRICAO" VARCHAR2(20)
   ) ;

   COMMENT ON COLUMN "STATUS_PAGAMENTO"."STPA_ID" IS 'Identificador sequencial da tabela.';
   COMMENT ON COLUMN "STATUS_PAGAMENTO"."STPA_TX_DESCRICAO" IS 'Descrição do status de pagamento.';
--------------------------------------------------------
--  DDL for Table STATUS_PEDIDO_COLETA
--------------------------------------------------------

  CREATE TABLE "STATUS_PEDIDO_COLETA" 
   (	"STPC_ID" NUMBER(2,0), 
	"STPC_TX_DESCRICAO" VARCHAR2(20)
   ) ;

   COMMENT ON COLUMN "STATUS_PEDIDO_COLETA"."STPC_ID" IS 'Identificador do status de pedido de coleta.';
   COMMENT ON COLUMN "STATUS_PEDIDO_COLETA"."STPC_TX_DESCRICAO" IS 'Descrição do status de pedido de coleta.';
   COMMENT ON TABLE "STATUS_PEDIDO_COLETA"  IS 'Tabela responsável por armazenar os status de pedidos de coleta.';
--------------------------------------------------------
--  DDL for Table STATUS_PEDIDO_EXAME
--------------------------------------------------------

  CREATE TABLE "STATUS_PEDIDO_EXAME" 
   (	"STPX_ID" NUMBER, 
	"STPX_TX_DESCRICAO" VARCHAR2(40), 
	"STPX_ID_STATUS_REDOMEWEB" NUMBER
   ) ;

   COMMENT ON COLUMN "STATUS_PEDIDO_EXAME"."STPX_ID" IS 'Identificador único da tabela.';
   COMMENT ON COLUMN "STATUS_PEDIDO_EXAME"."STPX_TX_DESCRICAO" IS 'Descrição do status.';
   COMMENT ON COLUMN "STATUS_PEDIDO_EXAME"."STPX_ID_STATUS_REDOMEWEB" IS 'Identificador referente ao status do redomeweb.';
   COMMENT ON TABLE "STATUS_PEDIDO_EXAME"  IS 'Tabela de status utilizada pelo pedido de exame.';
--------------------------------------------------------
--  DDL for Table STATUS_PEDIDO_IDM
--------------------------------------------------------

  CREATE TABLE "STATUS_PEDIDO_IDM" 
   (	"STPI_ID" NUMBER, 
	"STPI_TX_DESCRICAO" VARCHAR2(30)
   ) ;

   COMMENT ON COLUMN "STATUS_PEDIDO_IDM"."STPI_ID" IS 'Identificador sequencial da tabela.';
   COMMENT ON COLUMN "STATUS_PEDIDO_IDM"."STPI_TX_DESCRICAO" IS 'Descrição do status.';
   COMMENT ON TABLE "STATUS_PEDIDO_IDM"  IS 'Status do Pedido de IDM.';
--------------------------------------------------------
--  DDL for Table STATUS_PEDIDO_LOGISTICA
--------------------------------------------------------

  CREATE TABLE "STATUS_PEDIDO_LOGISTICA" 
   (	"STPL_ID" NUMBER(2,0), 
	"STPL_TX_DESCRICAO" VARCHAR2(60)
   ) ;

   COMMENT ON COLUMN "STATUS_PEDIDO_LOGISTICA"."STPL_ID" IS 'Identificador do status de pedido de logistica.';
   COMMENT ON COLUMN "STATUS_PEDIDO_LOGISTICA"."STPL_TX_DESCRICAO" IS 'Descrição do status de pedido de logistica.';
   COMMENT ON TABLE "STATUS_PEDIDO_LOGISTICA"  IS 'Tabela responsável por armazenar os status de pedidos de logistica.';
--------------------------------------------------------
--  DDL for Table STATUS_PEDIDO_TRANSPORTE
--------------------------------------------------------

  CREATE TABLE "STATUS_PEDIDO_TRANSPORTE" 
   (	"STPT_ID" NUMBER, 
	"STPT_TX_DESCRICAO" VARCHAR2(30)
   ) ;

   COMMENT ON COLUMN "STATUS_PEDIDO_TRANSPORTE"."STPT_ID" IS 'Chave primária da tabela';
   COMMENT ON COLUMN "STATUS_PEDIDO_TRANSPORTE"."STPT_TX_DESCRICAO" IS 'Descrição do status';
--------------------------------------------------------
--  DDL for Table STATUS_PEDIDO_WORKUP
--------------------------------------------------------

  CREATE TABLE "STATUS_PEDIDO_WORKUP" 
   (	"STPW_ID" NUMBER, 
	"STPW_DESCRICAO" VARCHAR2(30)
   ) ;

   COMMENT ON COLUMN "STATUS_PEDIDO_WORKUP"."STPW_ID" IS 'Chave primária ta tabela de status do pedido de workup.';
   COMMENT ON COLUMN "STATUS_PEDIDO_WORKUP"."STPW_DESCRICAO" IS 'Descrição do status';
   COMMENT ON TABLE "STATUS_PEDIDO_WORKUP"  IS 'Tabela de status de pedido de workup.';
--------------------------------------------------------
--  DDL for Table STATUS_PENDENCIA
--------------------------------------------------------

  CREATE TABLE "STATUS_PENDENCIA" 
   (	"STPE_ID" NUMBER(2,0), 
	"STPE_TX_DESCRICAO" VARCHAR2(20)
   ) ;

   COMMENT ON COLUMN "STATUS_PENDENCIA"."STPE_ID" IS 'Chave primária do status pendência';
   COMMENT ON COLUMN "STATUS_PENDENCIA"."STPE_TX_DESCRICAO" IS 'Descrição do status de uma pendência de avaliação';
   COMMENT ON TABLE "STATUS_PENDENCIA"  IS 'Tabela de domínio dos status das pendências de uma avaliação';
--------------------------------------------------------
--  DDL for Table TEMP_SPLIT_VALOR_DNA
--------------------------------------------------------

  CREATE TABLE "TEMP_SPLIT_VALOR_DNA" 
   (	"LOCU_ID" VARCHAR2(4), 
	"TESD_TX_GRUPO_ALELICO" VARCHAR2(3), 
	"TESD_TX_VALOR_ALELICO" VARCHAR2(20), 
	"TESD_IN_COMPARA_NMDP" NUMBER DEFAULT 0, 
	"TESD_TX_VERSAO" VARCHAR2(6)
   ) ;

   COMMENT ON COLUMN "TEMP_SPLIT_VALOR_DNA"."LOCU_ID" IS 'Chave estrangeira para locus';
   COMMENT ON COLUMN "TEMP_SPLIT_VALOR_DNA"."TESD_TX_GRUPO_ALELICO" IS 'Valor do grupo alelico';
   COMMENT ON COLUMN "TEMP_SPLIT_VALOR_DNA"."TESD_TX_VALOR_ALELICO" IS 'Valor alelico';
   COMMENT ON COLUMN "TEMP_SPLIT_VALOR_DNA"."TESD_IN_COMPARA_NMDP" IS 'Flag que identifica se o valor será usuado para comparação com nmdp';
   COMMENT ON COLUMN "TEMP_SPLIT_VALOR_DNA"."TESD_TX_VERSAO" IS 'Versão do arquivo em que foi gerado o split';
   COMMENT ON TABLE "TEMP_SPLIT_VALOR_DNA"  IS 'TABELA TEMPORÁRIA DE SPLIT DOS VALORES DNA';
--------------------------------------------------------
--  DDL for Table TEMP_VALOR_DNA
--------------------------------------------------------

  CREATE TABLE "TEMP_VALOR_DNA" 
   (	"LOCU_ID" VARCHAR2(4) not null, 
	"TEVD_TX_VALOR" VARCHAR2(20) not null, 
	"TEVD_TX_VERSAO" VARCHAR2(5)
   ) ;

   COMMENT ON COLUMN "TEMP_VALOR_DNA"."LOCU_ID" IS 'CHAVE ESTRANGEIRA RELACIONADA AO LOCUS';
   COMMENT ON COLUMN "TEMP_VALOR_DNA"."TEVD_TX_VALOR" IS 'VALOR DNA';
   COMMENT ON COLUMN "TEMP_VALOR_DNA"."TEVD_TX_VERSAO" IS 'Versão do arquivo ';
   COMMENT ON TABLE "TEMP_VALOR_DNA"  IS 'TABELA TEMPORÁRIA DE VALORES DNA';
--------------------------------------------------------
--  DDL for Table TEMP_VALOR_DNA_NMDP
--------------------------------------------------------

  CREATE TABLE "TEMP_VALOR_DNA_NMDP" 
   (	"LOCU_ID" VARCHAR2(4), 
	"DNNM_TX_VALOR" VARCHAR2(20), 
	"DNNM_TX_NOME_GRUPO" VARCHAR2(6)
   ) ;
--------------------------------------------------------
--  DDL for Table TEMP_VALOR_G
--------------------------------------------------------

  CREATE TABLE "TEMP_VALOR_G" 
   (	"LOCU_ID" VARCHAR2(4), 
	"TEVG_TX_GRUPO" CLOB, 
	"TEVG_TX_NOME_GRUPO" VARCHAR2(20), 
	"TEVG_NR_VALIDO" NUMBER, 
	"TEVG_TX_VERSAO" VARCHAR2(10)
   );

   COMMENT ON COLUMN "TEMP_VALOR_G"."LOCU_ID" IS 'NOME DO LOCUS E REFERENCIA PARA A TABELA DE LOCUS';
   COMMENT ON COLUMN "TEMP_VALOR_G"."TEVG_TX_GRUPO" IS 'ALELOS VINCULADOS AO GRUPO';
   COMMENT ON COLUMN "TEMP_VALOR_G"."TEVG_TX_NOME_GRUPO" IS 'NOME DO GRUPO';
   COMMENT ON COLUMN "TEMP_VALOR_G"."TEVG_NR_VALIDO" IS 'INDICA SE O VALOR É OU VÁLIDO (0 PARA INVÁLIDO E 1 PARA VÁLIDO)';
   COMMENT ON COLUMN "TEMP_VALOR_G"."TEVG_TX_VERSAO" IS 'VERSÃO DO VALOR G BAIXADO
';
--------------------------------------------------------
--  DDL for Table TEMP_VALOR_NMDP
--------------------------------------------------------

  CREATE TABLE "TEMP_VALOR_NMDP" 
   (	"TEVN_ID_CODIGO" VARCHAR2(10), 
	"TEVN_TX_SUBTIPO" CLOB, 
	"TEVN_IN_AGRUPADO" NUMBER(1,0) DEFAULT 0, 
	"TEVN_DT_ULTIMA_ATUALIZACAO_ARQ" DATE, 
	"TEVN_NR_QUANTIDADE" NUMBER DEFAULT 0
   ) ;

   COMMENT ON COLUMN "TEMP_VALOR_NMDP"."TEVN_ID_CODIGO" IS 'CÓDIGO NMDP';
   COMMENT ON COLUMN "TEMP_VALOR_NMDP"."TEVN_TX_SUBTIPO" IS 'VALORES ALELICOS';
   COMMENT ON COLUMN "TEMP_VALOR_NMDP"."TEVN_IN_AGRUPADO" IS 'DEFINE SE O VALOR ALELIXO ESTA AGRUPADO EX: 13:05/13:06';
   COMMENT ON COLUMN "TEMP_VALOR_NMDP"."TEVN_DT_ULTIMA_ATUALIZACAO_ARQ" IS 'Ultima data de atualização do arquivo';
   COMMENT ON COLUMN "TEMP_VALOR_NMDP"."TEVN_NR_QUANTIDADE" IS 'Quantidade de subtipos';
   COMMENT ON TABLE "TEMP_VALOR_NMDP"  IS 'TABELA TEMPORÁRIA DE VALORES NMDP PARA ATUALIZAÇÃO';
--------------------------------------------------------
--  DDL for Table TEMP_VALOR_P
--------------------------------------------------------

  CREATE TABLE "TEMP_VALOR_P" 
   (	"LOCU_ID" VARCHAR2(4), 
	"TEVP_TX_GRUPO" CLOB, 
	"TEVP_TX_NOME_GRUPO" VARCHAR2(20), 
	"TEVP_NR_VALIDO" NUMBER, 
	"TEVP_TX_VERSAO" VARCHAR2(10)
   );

   COMMENT ON COLUMN "TEMP_VALOR_P"."LOCU_ID" IS 'NOME DO LOCUS E REFERENCIA PARA A TABELA DE LOCUS';
   COMMENT ON COLUMN "TEMP_VALOR_P"."TEVP_TX_GRUPO" IS 'ALELOS VINCULADOS AO GRUPO';
   COMMENT ON COLUMN "TEMP_VALOR_P"."TEVP_TX_NOME_GRUPO" IS 'NOME DO GRUPO';
   COMMENT ON COLUMN "TEMP_VALOR_P"."TEVP_NR_VALIDO" IS 'INDICA SE O VALOR É OU VÁLIDO (0 PARA INVÁLIDO E 1 PARA VÁLIDO)';
   COMMENT ON COLUMN "TEMP_VALOR_P"."TEVP_TX_VERSAO" IS 'VERSÃO DO VALOR P BAIXADO
';
--------------------------------------------------------
--  DDL for Table TENTATIVA_CONTATO_DOADOR
--------------------------------------------------------

  CREATE TABLE "TENTATIVA_CONTATO_DOADOR" 
   (	"TECD_ID" NUMBER, 
	"TECD_DT_CRIACAO" TIMESTAMP (6), 
	"USUA_ID" NUMBER, 
	"RETC_ID" NUMBER, 
	"PECO_ID" NUMBER, 
	"COTE_ID" NUMBER, 
	"TECD_DT_REALIZACAO_CONTATO" TIMESTAMP (6), 
	"TECD_DT_AGENDAMENTO" DATE, 
	"TECD_HR_INICIO_AGENDAMENTO" TIMESTAMP (6), 
	"TECD_HR_FIM_AGENDAMENTO" TIMESTAMP (6)
   ) ;

   COMMENT ON COLUMN "TENTATIVA_CONTATO_DOADOR"."TECD_ID" IS 'Identificador sequencial da tabela.';
   COMMENT ON COLUMN "TENTATIVA_CONTATO_DOADOR"."TECD_DT_CRIACAO" IS 'Data realização da tentativa de contato.';
   COMMENT ON COLUMN "TENTATIVA_CONTATO_DOADOR"."USUA_ID" IS 'Usuário responsável pela tentativa de contato.';
   COMMENT ON COLUMN "TENTATIVA_CONTATO_DOADOR"."RETC_ID" IS 'Identificador da resposta da tentativa de contato.';
   COMMENT ON COLUMN "TENTATIVA_CONTATO_DOADOR"."PECO_ID" IS 'Identificador do pedido de contato.';
   COMMENT ON COLUMN "TENTATIVA_CONTATO_DOADOR"."COTE_ID" IS 'Referencia ao contato telefonico doador para agendamento ';
   COMMENT ON COLUMN "TENTATIVA_CONTATO_DOADOR"."TECD_DT_REALIZACAO_CONTATO" IS 'Data de realização do contato';
   COMMENT ON COLUMN "TENTATIVA_CONTATO_DOADOR"."TECD_DT_AGENDAMENTO" IS 'Data do agendamento';
   COMMENT ON COLUMN "TENTATIVA_CONTATO_DOADOR"."TECD_HR_INICIO_AGENDAMENTO" IS 'Hora de início do período do agendamento';
   COMMENT ON COLUMN "TENTATIVA_CONTATO_DOADOR"."TECD_HR_FIM_AGENDAMENTO" IS 'Hora final do período do agendamento';
   COMMENT ON TABLE "TENTATIVA_CONTATO_DOADOR"  IS 'Tentativa de contato com o doador.';

--------------------------------------------------------
--  DDL for Table TIPO_BUSCA_CHECKLIST
--------------------------------------------------------

  CREATE TABLE "TIPO_BUSCA_CHECKLIST" 
   (	"TIBC_ID" NUMBER, 
	"TIBC_TX_DESCRICAO" VARCHAR2(100), 
	"TIBC_NR_AGE_DIAS" NUMBER
   ) ;

   COMMENT ON COLUMN "TIPO_BUSCA_CHECKLIST"."TIBC_ID" IS 'Identificação de tipo de busca checklist';
   COMMENT ON COLUMN "TIPO_BUSCA_CHECKLIST"."TIBC_TX_DESCRICAO" IS 'Descrição de tipo de busca de chcklist';
   COMMENT ON COLUMN "TIPO_BUSCA_CHECKLIST"."TIBC_NR_AGE_DIAS" IS 'Agging em dias';
   COMMENT ON TABLE "TIPO_BUSCA_CHECKLIST"  IS 'Tabela de tipo de busca checklist';
--------------------------------------------------------
--  DDL for Table TIPO_CHECKLIST
--------------------------------------------------------

  CREATE TABLE "TIPO_CHECKLIST" 
   (	"TIPC_ID" NUMBER, 
	"TIPC_TX_NM_TIPO" VARCHAR2(80)
   ) ;

   COMMENT ON COLUMN "TIPO_CHECKLIST"."TIPC_ID" IS 'Id sequencial do tipo de checklist ';
   COMMENT ON COLUMN "TIPO_CHECKLIST"."TIPC_TX_NM_TIPO" IS 'Nome do tipo de checklist ';
--------------------------------------------------------
--  DDL for Table TIPO_EXAME
--------------------------------------------------------

  CREATE TABLE "TIPO_EXAME" 
   (	"TIEX_ID" NUMBER, 
	"TIEX_TX_DESCRICAO" VARCHAR2(50)
   ) ;

   COMMENT ON COLUMN "TIPO_EXAME"."TIEX_ID" IS 'Identificador único do tipo de exame.';
   COMMENT ON COLUMN "TIPO_EXAME"."TIEX_TX_DESCRICAO" IS 'Descrição do tipo de exame';
   COMMENT ON TABLE "TIPO_EXAME"  IS 'Tipo de Exame';
--------------------------------------------------------
--  DDL for Table TIPO_EXAME_LOCUS
--------------------------------------------------------

  CREATE TABLE "TIPO_EXAME_LOCUS" 
   (	"LOCU_ID" VARCHAR2(4), 
	"TIEX_ID" NUMBER
   ) ;

   COMMENT ON COLUMN "TIPO_EXAME_LOCUS"."LOCU_ID" IS 'Identificador referente ao locus.';
   COMMENT ON COLUMN "TIPO_EXAME_LOCUS"."TIEX_ID" IS 'Identificador referente ao tipo dde exame.';
   COMMENT ON TABLE "TIPO_EXAME_LOCUS"  IS 'Tabela associativa entre tipo de exame e locus.';
--------------------------------------------------------
--  DDL for Table TIPO_FORMULARIO
--------------------------------------------------------

  CREATE TABLE "TIPO_FORMULARIO" 
   (	"TIFO_ID" NUMBER, 
	"TIFO_TX_DESCRICAO" VARCHAR2(30), 
	"TIFO_IN_NACIONAL" NUMBER(1,0), 
	"TIFO_IN_INTERNACIONAL" NUMBER(1,0)
   ) ;

   COMMENT ON COLUMN "TIPO_FORMULARIO"."TIFO_ID" IS 'Chave primária que identifica com exclusividade um tipo de formulário.';
   COMMENT ON COLUMN "TIPO_FORMULARIO"."TIFO_TX_DESCRICAO" IS 'Descrição do tipo de formulário.';
   COMMENT ON COLUMN "TIPO_FORMULARIO"."TIFO_IN_NACIONAL" IS 'Flag que informa se o formulario é nacional.';
   COMMENT ON COLUMN "TIPO_FORMULARIO"."TIFO_IN_INTERNACIONAL" IS 'Flag que informa se o formulario é internacional.';
   COMMENT ON TABLE "TIPO_FORMULARIO"  IS 'Tipo de formulário';
--------------------------------------------------------
--  DDL for Table TIPO_LOGRADOURO_CORREIO
--------------------------------------------------------

  CREATE TABLE "TIPO_LOGRADOURO_CORREIO" 
   (	"TILC_ID" NUMBER, 
	"TILC_TX_NOME" VARCHAR2(72), 
	"TILC_TX_NOME_ABREVIADO" VARCHAR2(15)
   ) ;

   COMMENT ON COLUMN "TIPO_LOGRADOURO_CORREIO"."TILC_ID" IS 'Chave do Tipo de Logradouro no DNE';
   COMMENT ON COLUMN "TIPO_LOGRADOURO_CORREIO"."TILC_TX_NOME" IS 'Nome Oficial do Tipo de Logradouro';
   COMMENT ON COLUMN "TIPO_LOGRADOURO_CORREIO"."TILC_TX_NOME_ABREVIADO" IS 'Abreviatura do Tipo de logradouro';
   COMMENT ON TABLE "TIPO_LOGRADOURO_CORREIO"  IS 'Tipos de Logradouros';
--------------------------------------------------------
--  DDL for Table TIPO_PEDIDO_LOGISTICA
--------------------------------------------------------

  CREATE TABLE "TIPO_PEDIDO_LOGISTICA" 
   (	"TIPL_ID" NUMBER(2,0), 
	"TIPL_TX_DESCRICAO" VARCHAR2(20)
   ) ;

   COMMENT ON COLUMN "TIPO_PEDIDO_LOGISTICA"."TIPL_ID" IS 'Identificador do tipo de pedido de logistica.';
   COMMENT ON COLUMN "TIPO_PEDIDO_LOGISTICA"."TIPL_TX_DESCRICAO" IS 'Descrição do tipo de pedido de logistica.';
   COMMENT ON TABLE "TIPO_PEDIDO_LOGISTICA"  IS 'Tabela responsável por armazenar os tipos de pedidos de logistica.';
--------------------------------------------------------
--  DDL for Table TIPO_PENDENCIA
--------------------------------------------------------

  CREATE TABLE "TIPO_PENDENCIA" 
   (	"TIPE_ID" NUMBER(2,0), 
	"TIPE_TX_DESCRICAO" VARCHAR2(50)
   ) ;

   COMMENT ON COLUMN "TIPO_PENDENCIA"."TIPE_ID" IS 'Chave primária do tipo de pendencia';
   COMMENT ON COLUMN "TIPO_PENDENCIA"."TIPE_TX_DESCRICAO" IS 'Descrição do tipo de pendência';
   COMMENT ON TABLE "TIPO_PENDENCIA"  IS 'Tabela referente aos tipos de pendências possíveis';
--------------------------------------------------------
--  DDL for Table TIPO_SERVICO
--------------------------------------------------------

  CREATE TABLE "TIPO_SERVICO" 
   (	"TISE_ID" NUMBER, 
	"TISE_TX_SIGLA" VARCHAR2(20), 
	"TISE_TX_DESCRICAO" VARCHAR2(255)
   ) ;

   COMMENT ON COLUMN "TIPO_SERVICO"."TISE_ID" IS 'Identificador sequencial da tabela.';
   COMMENT ON COLUMN "TIPO_SERVICO"."TISE_TX_SIGLA" IS 'Sigla do tipo de serviço.';
   COMMENT ON COLUMN "TIPO_SERVICO"."TISE_TX_DESCRICAO" IS 'Descrição do tipo de serviço.';
   COMMENT ON TABLE "TIPO_SERVICO"  IS 'Tipo de Serviço utilizado para cobrança ou pagamernto de invoices.';
--------------------------------------------------------
--  DDL for Table TIPO_SOLICITACAO
--------------------------------------------------------

  CREATE TABLE "TIPO_SOLICITACAO" 
   (	"TISO_ID" NUMBER, 
	"TISO_TX_DESCRICAO" VARCHAR2(50)
   ) ;

   COMMENT ON COLUMN "TIPO_SOLICITACAO"."TISO_ID" IS 'Referência do tipo de solicitação';
   COMMENT ON COLUMN "TIPO_SOLICITACAO"."TISO_TX_DESCRICAO" IS 'Descrição tipo de solicitação';
   COMMENT ON TABLE "TIPO_SOLICITACAO"  IS 'Tipos de solicitação';

--------------------------------------------------------
--  DDL for Table TIPO_TRANSPLANTE
--------------------------------------------------------

  CREATE TABLE "TIPO_TRANSPLANTE" 
   (	"TITR_ID" NUMBER, 
	"TITR_TX_DESCRICAO" VARCHAR2(30)
   ) ;

   COMMENT ON COLUMN "TIPO_TRANSPLANTE"."TITR_ID" IS 'Chave primária do tipo de transplante';
   COMMENT ON COLUMN "TIPO_TRANSPLANTE"."TITR_TX_DESCRICAO" IS 'Descrição do tipo de transplante';

--------------------------------------------------------
--  DDL for Table TRANSPORTE_TERRESTRE
--------------------------------------------------------

  CREATE TABLE "TRANSPORTE_TERRESTRE" 
   (	"TRTE_ID" NUMBER, 
	"TRTE_DT_DATA" TIMESTAMP (6), 
	"TRTE_TX_ORIGEM" VARCHAR2(200), 
	"TRTE_TX_DESTINO" VARCHAR2(200), 
	"TRTE_TX_OBJETO_TRANSPORTE" VARCHAR2(200), 
	"TRTE_DT_CRIACAO" TIMESTAMP (6), 
	"TRTE_IN_EXCLUIDO" NUMBER(1,0) DEFAULT 0, 
	"PELO_ID" NUMBER
   ) ;

   COMMENT ON COLUMN "TRANSPORTE_TERRESTRE"."TRTE_DT_DATA" IS 'Data/hora em que o transporte deverá ocorrer.';
   COMMENT ON COLUMN "TRANSPORTE_TERRESTRE"."TRTE_TX_ORIGEM" IS 'Origem do objeto a ser transportado.';
   COMMENT ON COLUMN "TRANSPORTE_TERRESTRE"."TRTE_TX_DESTINO" IS 'Destino do objeto a ser transportado.';
   COMMENT ON COLUMN "TRANSPORTE_TERRESTRE"."TRTE_TX_OBJETO_TRANSPORTE" IS 'Informações sobre o objeto ou pessoa que deverá ser transportada.';
   COMMENT ON COLUMN "TRANSPORTE_TERRESTRE"."TRTE_DT_CRIACAO" IS 'Data de criação.';
   COMMENT ON COLUMN "TRANSPORTE_TERRESTRE"."TRTE_IN_EXCLUIDO" IS 'Indica se foi excluido.';
   COMMENT ON COLUMN "TRANSPORTE_TERRESTRE"."PELO_ID" IS 'Identificador de pedido de coleta.';
   COMMENT ON TABLE "TRANSPORTE_TERRESTRE"  IS 'Tabela responsável por armazenar as solicitações de transporte terreste efetuadas em um pedido de logistica.';
--------------------------------------------------------
--  DDL for Table TRANSPORTE_TERRESTRE_AUD
--------------------------------------------------------

  CREATE TABLE "TRANSPORTE_TERRESTRE_AUD" 
   (	"TRTE_ID" NUMBER(19,0), 
	"AUDI_ID" NUMBER(19,0), 
	"AUDI_TX_TIPO" NUMBER(3,0), 
	"TRTE_DT_CRIACAO" TIMESTAMP (6), 
	"TRTE_DT_DATA" TIMESTAMP (6), 
	"TRTE_TX_DESTINO" VARCHAR2(255 CHAR), 
	"TRTE_IN_EXCLUIDO" NUMBER(1,0), 
	"TRTE_TX_OBJETO_TRANSPORTE" VARCHAR2(255 CHAR), 
	"TRTE_TX_ORIGEM" VARCHAR2(255 CHAR), 
	"PELO_ID" NUMBER(19,0)
   ) ;

   COMMENT ON TABLE "TRANSPORTE_TERRESTRE_AUD"  IS 'Tabela de auditoria da tabela TRANSPORTE_TERRESTRE.';
--------------------------------------------------------
--  DDL for Table TURNO
--------------------------------------------------------

  CREATE TABLE "TURNO" 
   (	"TURN_ID" NUMBER, 
	"TURN_TX_DESCRICAO" VARCHAR2(30), 
	"TURN_DT_HR_INICIO" TIMESTAMP (6), 
	"TURN_DT_HR_FIM" TIMESTAMP (6), 
	"TURN_IN_INCLUSIVO_EXCLUSIVO" NUMBER(1,0) DEFAULT 0
   ) ;

   COMMENT ON COLUMN "TURNO"."TURN_ID" IS 'Identificacao da tabela de turno';
   COMMENT ON COLUMN "TURNO"."TURN_TX_DESCRICAO" IS 'Descrição do turno';
   COMMENT ON COLUMN "TURNO"."TURN_DT_HR_INICIO" IS 'Hora inicial do turno';
   COMMENT ON COLUMN "TURNO"."TURN_DT_HR_FIM" IS 'Hora final do turno';
   COMMENT ON COLUMN "TURNO"."TURN_IN_INCLUSIVO_EXCLUSIVO" IS 'Define se o tempo é para incluir ou excluir determinado período.';
--------------------------------------------------------
--  DDL for Table UF
--------------------------------------------------------

  CREATE TABLE "UF" 
   (	"UF_SIGLA" VARCHAR2(2), 
	"UF_TX_NOME" VARCHAR2(80)
   ) ;

   COMMENT ON COLUMN "UF"."UF_SIGLA" IS 'Identificador de UF';
   COMMENT ON COLUMN "UF"."UF_TX_NOME" IS 'Nome da UF';
   COMMENT ON TABLE "UF"  IS 'Unidades Federativas do Brasil';
--------------------------------------------------------
--  DDL for Table UNIDADE_FEDERATIVA_CORREIO
--------------------------------------------------------

  CREATE TABLE "UNIDADE_FEDERATIVA_CORREIO" 
   (	"UNFC_ID" NUMBER, 
	"UNFC_TX_SIGLA" VARCHAR2(2), 
	"UNFC_TX_NOME" VARCHAR2(72), 
	"PACO_ID" VARCHAR2(2), 
	"UNFC_TX_DNE" VARCHAR2(2)
   ) ;

   COMMENT ON COLUMN "UNIDADE_FEDERATIVA_CORREIO"."UNFC_ID" IS 'Identificador da registro';
   COMMENT ON COLUMN "UNIDADE_FEDERATIVA_CORREIO"."UNFC_TX_SIGLA" IS 'Sigla da Unidade da Federação';
   COMMENT ON COLUMN "UNIDADE_FEDERATIVA_CORREIO"."UNFC_TX_NOME" IS 'Nome da Unidade da Federação';
   COMMENT ON COLUMN "UNIDADE_FEDERATIVA_CORREIO"."PACO_ID" IS 'id da tabela país';
   COMMENT ON COLUMN "UNIDADE_FEDERATIVA_CORREIO"."UNFC_TX_DNE" IS 'Chave da UF no DNE';
   COMMENT ON TABLE "UNIDADE_FEDERATIVA_CORREIO"  IS 'Unidade Federativa';
--------------------------------------------------------
--  DDL for Table USUARIO
--------------------------------------------------------

  CREATE TABLE "USUARIO" 
   (	"USUA_ID" NUMBER, 
	"USUA_TX_NOME" VARCHAR2(150), 
	"USUA_TX_USERNAME" VARCHAR2(50), 
	"USUA_TX_PASSWORD" VARCHAR2(255), 
	"USUA_IN_ATIVO" NUMBER(1,0) DEFAULT 1, 
	"TRAN_ID" NUMBER, 
	"LABO_ID" NUMBER, 
	"USUA_TX_EMAIL" VARCHAR2(100)
   ) ;

   COMMENT ON COLUMN "USUARIO"."USUA_ID" IS 'Identificador do usuário';
   COMMENT ON COLUMN "USUARIO"."USUA_TX_NOME" IS 'Nome do usuário';
   COMMENT ON COLUMN "USUARIO"."USUA_TX_USERNAME" IS 'Username do usuário';
   COMMENT ON COLUMN "USUARIO"."USUA_TX_PASSWORD" IS 'Password do usuário criptografada';
   COMMENT ON COLUMN "USUARIO"."USUA_IN_ATIVO" IS 'Indica se o usuário está ativo no sistema';
   COMMENT ON COLUMN "USUARIO"."TRAN_ID" IS 'Transportadora a qual o usuário está associado.';
   COMMENT ON COLUMN "USUARIO"."LABO_ID" IS 'Referencia para o laboratório ao qual o usuário está vinculado para o caso dele ser do perfil laboratório.';
   COMMENT ON COLUMN "USUARIO"."USUA_TX_EMAIL" IS 'E-mail, informado pelo usuário, para contato.';
   COMMENT ON TABLE "USUARIO"  IS 'Usuários do sistema';
--------------------------------------------------------
--  DDL for Table USUARIO_BANCO_SANGUE_CORDAO
--------------------------------------------------------

  CREATE TABLE "USUARIO_BANCO_SANGUE_CORDAO" 
   (	"USUA_ID" NUMBER, 
	"BASC_ID" NUMBER
   ) ;

   COMMENT ON COLUMN "USUARIO_BANCO_SANGUE_CORDAO"."USUA_ID" IS 'Além de chave estrangeira, é a chave primária compartilhada com a tabela de usuários.';
   COMMENT ON COLUMN "USUARIO_BANCO_SANGUE_CORDAO"."BASC_ID" IS 'Chave estrangeira da tabela Banco de Sangue de Cordão.';
   COMMENT ON TABLE "USUARIO_BANCO_SANGUE_CORDAO"  IS 'Tabela é uma especialização, em cima da tabela de usuário, criada para o BrasilCord.';
--------------------------------------------------------
--  DDL for Table USUARIO_CENTRO_TRANSPLANTE
--------------------------------------------------------

  CREATE TABLE "USUARIO_CENTRO_TRANSPLANTE" 
   (	"USUA_ID" NUMBER, 
	"CETR_ID" NUMBER
   ) ;

   COMMENT ON COLUMN "USUARIO_CENTRO_TRANSPLANTE"."USUA_ID" IS 'Identificador do usuário';
   COMMENT ON COLUMN "USUARIO_CENTRO_TRANSPLANTE"."CETR_ID" IS 'Identificador centro de transplante.';
   COMMENT ON TABLE "USUARIO_CENTRO_TRANSPLANTE"  IS 'Tabela responsável por associar usuários a centro de transplantes.';
--------------------------------------------------------
--  DDL for Table USUARIO_PERFIL
--------------------------------------------------------

  CREATE TABLE "USUARIO_PERFIL" 
   (	"USUA_ID" NUMBER, 
	"PERF_ID" NUMBER
   ) ;

   COMMENT ON COLUMN "USUARIO_PERFIL"."USUA_ID" IS 'Identificador do usuário';
   COMMENT ON COLUMN "USUARIO_PERFIL"."PERF_ID" IS 'Identificador do Perfil';
   COMMENT ON TABLE "USUARIO_PERFIL"  IS 'Perfis dos usuários';
--------------------------------------------------------
--  DDL for Table VALOR_DNA
--------------------------------------------------------

  CREATE TABLE "VALOR_DNA" 
   (	"LOCU_ID" VARCHAR2(4), 
	"VADN_TX_VALOR" VARCHAR2(20), 
	"VADN_TX_VALOR_G" VARCHAR2(20), 
	"VADN_TX_VALOR_P" VARCHAR2(20)
   ) ;

   COMMENT ON COLUMN "VALOR_DNA"."LOCU_ID" IS 'CHAVE ESTRANGEIRA RELACIONADA AO LOCUS';
   COMMENT ON COLUMN "VALOR_DNA"."VADN_TX_VALOR" IS 'VALOR DNA';
   COMMENT ON COLUMN "VALOR_DNA"."VADN_TX_VALOR_G" IS 'VALOR DE AGRUPAMENTO G';
   COMMENT ON COLUMN "VALOR_DNA"."VADN_TX_VALOR_P" IS 'VALOR DE AGRUPAMENTO P';
   COMMENT ON TABLE "VALOR_DNA"  IS 'TABELA DE VALORES DNA';
--------------------------------------------------------
--  DDL for Table VALOR_DNA_NMDP_VALIDO
--------------------------------------------------------

  CREATE TABLE "VALOR_DNA_NMDP_VALIDO" 
   (	"LOCU_ID" VARCHAR2(4), 
	"DNNM_TX_VALOR" VARCHAR2(20), 
	"VADN_TX_NOME_GRUPO" VARCHAR2(6), 
	"DNNM_IN_ATIVO" NUMBER
   ) ;

   COMMENT ON COLUMN "VALOR_DNA_NMDP_VALIDO"."LOCU_ID" IS 'CHAVE ESTRANGEIRA RELACIONADA AO LOCUS';
   COMMENT ON COLUMN "VALOR_DNA_NMDP_VALIDO"."DNNM_TX_VALOR" IS 'VALORES VALIDOS SOROLOGIA E NMDP';
   COMMENT ON COLUMN "VALOR_DNA_NMDP_VALIDO"."VADN_TX_NOME_GRUPO" IS 'NOME DO GRUPO ALÉLICO';
   COMMENT ON COLUMN "VALOR_DNA_NMDP_VALIDO"."DNNM_IN_ATIVO" IS 'Identifica se ovalor está ativo, ou seja se o valor é valido.';
   COMMENT ON TABLE "VALOR_DNA_NMDP_VALIDO"  IS 'TABELA DE RESULTADO DOS VALORES VALIDOS TEMPORÁRIOS DE DNA E NMDP';
--------------------------------------------------------
--  DDL for Table VALOR_G
--------------------------------------------------------

  CREATE TABLE "VALOR_G" 
   (	"LOCU_ID" VARCHAR2(4), 
	"VALG_TX_GRUPO" CLOB, 
	"VALG_TX_NOME_GRUPO" VARCHAR2(20), 
	"VALG_NR_VALIDO" NUMBER, 
	"VALG_TX_VERSAO" VARCHAR2(10)
   ) ;

   COMMENT ON COLUMN "VALOR_G"."LOCU_ID" IS 'NOME DO LOCUS E REFERENCIA PARA A TABELA DE LOCUS';
   COMMENT ON COLUMN "VALOR_G"."VALG_TX_GRUPO" IS 'ALELOS VINCULADOS AO GRUPO';
   COMMENT ON COLUMN "VALOR_G"."VALG_TX_NOME_GRUPO" IS 'NOME DO GRUPO';
   COMMENT ON COLUMN "VALOR_G"."VALG_NR_VALIDO" IS 'INDICA SE O VALOR É OU VÁLIDO (0 PARA INVÁLIDO E 1 PARA VÁLIDO)';
   COMMENT ON COLUMN "VALOR_G"."VALG_TX_VERSAO" IS 'VERSÃO DO VALOR G BAIXADO
';
--------------------------------------------------------
--  DDL for Table VALOR_GENOTIPO_BUSCA_DOADOR
--------------------------------------------------------

  CREATE TABLE "VALOR_GENOTIPO_BUSCA_DOADOR" 
   (	"VGBD_ID" NUMBER, 
	"LOCU_ID" VARCHAR2(10), 
	"VGBD_NR_POSICAO" NUMBER, 
	"VGBD_NR_TIPO" NUMBER, 
	"GEDO_ID" NUMBER, 
	"VGBD_TX_VALOR" VARCHAR2(10), 
	"VGBD_IN_TIPO_DOADOR" NUMBER(1,0)
   ) ;

   COMMENT ON COLUMN "VALOR_GENOTIPO_BUSCA_DOADOR"."VGBD_ID" IS 'Chave primária de valor genótipo busca';
   COMMENT ON COLUMN "VALOR_GENOTIPO_BUSCA_DOADOR"."LOCU_ID" IS 'Chave para tabela de lous ';
   COMMENT ON COLUMN "VALOR_GENOTIPO_BUSCA_DOADOR"."VGBD_NR_POSICAO" IS 'Numero da posição do locus ';
   COMMENT ON COLUMN "VALOR_GENOTIPO_BUSCA_DOADOR"."VGBD_NR_TIPO" IS '0- para sorologia, 1- para antígeno, 2- NMDP, 3- Grupo G, 4-  Grupo P e 5 Alelo';
   COMMENT ON COLUMN "VALOR_GENOTIPO_BUSCA_DOADOR"."GEDO_ID" IS 'Referência de genótipo ';
   COMMENT ON COLUMN "VALOR_GENOTIPO_BUSCA_DOADOR"."VGBD_TX_VALOR" IS 'Valor de resultado de HLA';
   COMMENT ON COLUMN "VALOR_GENOTIPO_BUSCA_DOADOR"."VGBD_IN_TIPO_DOADOR" IS 'Identifica o tipo de doador. 0 - Doador Nacional 1 - Doador Internacional 2 - Cordão Nacional 3 Cordão Internacional.';
   COMMENT ON TABLE "VALOR_GENOTIPO_BUSCA_DOADOR"  IS 'Tabela de valores de genótipo com a coluna de valores reduzida ';
--------------------------------------------------------
--  DDL for Table VALOR_GENOTIPO_BUSCA_PACIENTE
--------------------------------------------------------

  CREATE TABLE "VALOR_GENOTIPO_BUSCA_PACIENTE" 
   (	"VGBP_ID" NUMBER, 
	"LOCU_ID" VARCHAR2(10), 
	"VGBP_NR_POSICAO" NUMBER, 
	"VGBP_NR_TIPO" NUMBER, 
	"GEPA_ID" NUMBER, 
	"VGBP_TX_VALOR" VARCHAR2(10)
   ) ;

   COMMENT ON COLUMN "VALOR_GENOTIPO_BUSCA_PACIENTE"."VGBP_ID" IS 'Chave primária de valor genótipo busca';
   COMMENT ON COLUMN "VALOR_GENOTIPO_BUSCA_PACIENTE"."LOCU_ID" IS 'Chave para tabela de lous ';
   COMMENT ON COLUMN "VALOR_GENOTIPO_BUSCA_PACIENTE"."VGBP_NR_POSICAO" IS 'Numero da posição do locus ';
   COMMENT ON COLUMN "VALOR_GENOTIPO_BUSCA_PACIENTE"."VGBP_NR_TIPO" IS '0- para sorologia, 1- para antígeno, 2- NMDP, 3- Grupo G, 4-  Grupo P e 5 Alelo';
   COMMENT ON COLUMN "VALOR_GENOTIPO_BUSCA_PACIENTE"."GEPA_ID" IS 'Referência de genótipo ';
   COMMENT ON COLUMN "VALOR_GENOTIPO_BUSCA_PACIENTE"."VGBP_TX_VALOR" IS 'Valor de resultado de HLA';
   COMMENT ON TABLE "VALOR_GENOTIPO_BUSCA_PACIENTE"  IS 'Tabela de valores de genótipo com a coluna de valores reduzida ';
--------------------------------------------------------
--  DDL for Table VALOR_GENOTIPO_DOADOR
--------------------------------------------------------

  CREATE TABLE "VALOR_GENOTIPO_DOADOR" 
   (	"EXAM_ID" NUMBER, 
	"LOCU_ID" VARCHAR2(4), 
	"VAGD_TX_ALELO" VARCHAR2(20), 
	"VAGD_NR_POSICAO_ALELO" NUMBER(1,0), 
	"VAGD_NR_TIPO" NUMBER, 
	"GEDO_ID" NUMBER, 
	"VAGD_IN_TIPO_DOADOR" NUMBER(1,0), 
	"VAGD_IN_DIVERGENTE" NUMBER
   ) ;

   COMMENT ON COLUMN "VALOR_GENOTIPO_DOADOR"."EXAM_ID" IS 'Identificador para a tabela de exame.';
   COMMENT ON COLUMN "VALOR_GENOTIPO_DOADOR"."LOCU_ID" IS 'Identificador para a tabela de locus.';
   COMMENT ON COLUMN "VALOR_GENOTIPO_DOADOR"."VAGD_TX_ALELO" IS 'Valor do alelo com maior resolução.';
   COMMENT ON COLUMN "VALOR_GENOTIPO_DOADOR"."VAGD_NR_POSICAO_ALELO" IS 'Posição do valor alelo dentro do locus/exame, 
se é referente ao primeiro ou segundo alelo.';
   COMMENT ON COLUMN "VALOR_GENOTIPO_DOADOR"."VAGD_NR_TIPO" IS '0- para sorologia, 1- para antígeno, 2- NMDP, 3- Grupo G, 4-  Grupo P e 5 Alelo';
   COMMENT ON COLUMN "VALOR_GENOTIPO_DOADOR"."GEDO_ID" IS 'Referência para tabela pai de genótipo';
   COMMENT ON COLUMN "VALOR_GENOTIPO_DOADOR"."VAGD_IN_TIPO_DOADOR" IS 'Identifica o tipo de doador. 0 - Doador Nacional 1 - Doador Internacional 2 - Cordão Nacional 3 Cordão Internacional.';
   COMMENT ON COLUMN "VALOR_GENOTIPO_DOADOR"."VAGD_IN_DIVERGENTE" IS 'Identifica o valor como sendo divergente ou não';
   COMMENT ON TABLE "VALOR_GENOTIPO_DOADOR"  IS 'Tabela de genótipos por doador. Armazena os locus exame, 
com os valores alélicos de maior resolução, associados ao exames já conferidos (status aceito) para o doador.';
--------------------------------------------------------
--  DDL for Table VALOR_GENOTIPO_EXPAND_DOADOR
--------------------------------------------------------

  CREATE TABLE "VALOR_GENOTIPO_EXPAND_DOADOR" 
   (	"VGED_ID" NUMBER, 
	"VGED_NR_POSICAO" NUMBER, 
	"VGED_TX_VALOR" NUMBER, 
	"LOCU_ID" VARCHAR2(10), 
	"GEDO_ID" NUMBER, 
	"DOAD_ID" NUMBER, 
	"VGED_IN_TIPO_DOADOR" NUMBER
   ) ;

   COMMENT ON COLUMN "VALOR_GENOTIPO_EXPAND_DOADOR"."VGED_ID" IS 'Identificador do genotipo expandido de doador';
   COMMENT ON COLUMN "VALOR_GENOTIPO_EXPAND_DOADOR"."VGED_NR_POSICAO" IS 'posição do alelo';
   COMMENT ON COLUMN "VALOR_GENOTIPO_EXPAND_DOADOR"."VGED_TX_VALOR" IS 'valor do alelo';
   COMMENT ON COLUMN "VALOR_GENOTIPO_EXPAND_DOADOR"."LOCU_ID" IS 'locus';
   COMMENT ON COLUMN "VALOR_GENOTIPO_EXPAND_DOADOR"."GEDO_ID" IS 'id do genotipo do doador';
   COMMENT ON COLUMN "VALOR_GENOTIPO_EXPAND_DOADOR"."DOAD_ID" IS 'id do doador';
   COMMENT ON COLUMN "VALOR_GENOTIPO_EXPAND_DOADOR"."VGED_IN_TIPO_DOADOR" IS 'Identifica o tipo de doador. 0 - Doador Nacional 1 - Doador Internacional 2 - Cordão Nacional 3 Cordão Internacional.';
   COMMENT ON TABLE "VALOR_GENOTIPO_EXPAND_DOADOR"  IS 'Tabela de genotipo expandido do doador, guarda a primeira parte do alelo.';
--------------------------------------------------------
--  DDL for Table VALOR_GENOTIPO_EXPAND_PACIENTE
--------------------------------------------------------

  CREATE TABLE "VALOR_GENOTIPO_EXPAND_PACIENTE" 
   (	"VGEP_ID" NUMBER, 
	"VGEP_NR_POSICAO" NUMBER, 
	"VGEP_TX_VALOR" NUMBER, 
	"LOCU_ID" VARCHAR2(10), 
	"GEPA_ID" NUMBER, 
	"PACI_NR_RMR" NUMBER
   ) ;

   COMMENT ON COLUMN "VALOR_GENOTIPO_EXPAND_PACIENTE"."VGEP_ID" IS 'Chave primária de valor de genótipo expandido';
   COMMENT ON COLUMN "VALOR_GENOTIPO_EXPAND_PACIENTE"."VGEP_NR_POSICAO" IS 'Posicao do valor do genótipo que pode ser 1 ou 2';
   COMMENT ON COLUMN "VALOR_GENOTIPO_EXPAND_PACIENTE"."VGEP_TX_VALOR" IS 'Valor de genótipo expandido';
   COMMENT ON COLUMN "VALOR_GENOTIPO_EXPAND_PACIENTE"."LOCU_ID" IS 'Referência de locus ';
   COMMENT ON COLUMN "VALOR_GENOTIPO_EXPAND_PACIENTE"."GEPA_ID" IS 'Identificador do genotipo de paciente';
   COMMENT ON COLUMN "VALOR_GENOTIPO_EXPAND_PACIENTE"."PACI_NR_RMR" IS 'referencia ao paciente';
   COMMENT ON TABLE "VALOR_GENOTIPO_EXPAND_PACIENTE"  IS 'Tabela de valores exapandidos de genetótipos ';
--------------------------------------------------------
--  DDL for Table VALOR_GENOTIPO_PACIENTE
--------------------------------------------------------

  CREATE TABLE "VALOR_GENOTIPO_PACIENTE" 
   (	"EXAM_ID" NUMBER, 
	"LOCU_ID" VARCHAR2(4), 
	"VAGP_TX_ALELO" VARCHAR2(20), 
	"VAGP_NR_POSICAO_ALELO" NUMBER(1,0), 
	"VAGP_NR_TIPO" NUMBER, 
	"GEPA_ID" NUMBER, 
	"VAGP_IN_DIVERGENTE" NUMBER
   ) ;

   COMMENT ON COLUMN "VALOR_GENOTIPO_PACIENTE"."EXAM_ID" IS 'Identificador para a tabela de exame.';
   COMMENT ON COLUMN "VALOR_GENOTIPO_PACIENTE"."LOCU_ID" IS 'Identificador para a tabela de locus.';
   COMMENT ON COLUMN "VALOR_GENOTIPO_PACIENTE"."VAGP_TX_ALELO" IS 'Valor do alelo com maior resolução.';
   COMMENT ON COLUMN "VALOR_GENOTIPO_PACIENTE"."VAGP_NR_POSICAO_ALELO" IS 'Posição do valor alelo dentro do locus/exame, 
se é referente ao primeiro ou segundo alelo.';
   COMMENT ON COLUMN "VALOR_GENOTIPO_PACIENTE"."VAGP_NR_TIPO" IS '0- para sorologia, 1- para antígeno, 2- NMDP, 3- Grupo G, 4-  Grupo P e 5 Alelo';
   COMMENT ON COLUMN "VALOR_GENOTIPO_PACIENTE"."GEPA_ID" IS 'Referência para tabela pai de genótipo';
   COMMENT ON COLUMN "VALOR_GENOTIPO_PACIENTE"."VAGP_IN_DIVERGENTE" IS 'Identifica o valor como sendo divergente ou não';
   COMMENT ON TABLE "VALOR_GENOTIPO_PACIENTE"  IS 'Tabela de genótipos por paciente. Armazena os locus exame, 
com os valores alélicos de maior resolução, associados ao exames já conferidos (status aceito) para o paciente.';
--------------------------------------------------------
--  DDL for Table VALOR_NMDP
--------------------------------------------------------

  CREATE TABLE "VALOR_NMDP" 
   (	"VANM_ID_CODIGO" VARCHAR2(10), 
	"VANM_TX_SUBTIPO" CLOB, 
	"VANM_IN_AGRUPADO" NUMBER(1,0) DEFAULT 0, 
	"VANM_DT_ULTIMA_ATUALIZACAO_ARQ" DATE, 
	"VANM_NR_QUANTIDADE" NUMBER DEFAULT 0, 
	"VANM_IN_SPLIT_CRIADO" NUMBER
   ) ;

   COMMENT ON COLUMN "VALOR_NMDP"."VANM_ID_CODIGO" IS 'CÓDIGO NMDP';
   COMMENT ON COLUMN "VALOR_NMDP"."VANM_TX_SUBTIPO" IS 'VALORES ALELICOS';
   COMMENT ON COLUMN "VALOR_NMDP"."VANM_IN_AGRUPADO" IS 'DEFINE SE O VALOR ALELIXO ESTA AGRUPADO EX: 13:05/13:06';
   COMMENT ON COLUMN "VALOR_NMDP"."VANM_DT_ULTIMA_ATUALIZACAO_ARQ" IS 'Ultima data de atualização do arquivo';
   COMMENT ON COLUMN "VALOR_NMDP"."VANM_NR_QUANTIDADE" IS 'Quantidade de subtipos';
   COMMENT ON COLUMN "VALOR_NMDP"."VANM_IN_SPLIT_CRIADO" IS 'Define se o valor nmdp já convertido em valores validos de dna.';
   COMMENT ON TABLE "VALOR_NMDP"  IS 'TABELA DE VALORES NMDP';
--------------------------------------------------------
--  DDL for Table VALOR_P
--------------------------------------------------------

  CREATE TABLE "VALOR_P" 
   (	"LOCU_ID" VARCHAR2(4), 
	"VALP_TX_GRUPO" CLOB, 
	"VALP_TX_NOME_GRUPO" VARCHAR2(20), 
	"VALP_NR_VALIDO" NUMBER, 
	"VALP_TX_VERSAO" VARCHAR2(10)
   ) ;

   COMMENT ON COLUMN "VALOR_P"."LOCU_ID" IS 'NOME DO LOCUS E REFERENCIA PARA A TABELA DE LOCUS';
   COMMENT ON COLUMN "VALOR_P"."VALP_TX_GRUPO" IS 'ALELOS VINCULADOS AO GRUPO';
   COMMENT ON COLUMN "VALOR_P"."VALP_TX_NOME_GRUPO" IS 'NOME DO GRUPO';
   COMMENT ON COLUMN "VALOR_P"."VALP_NR_VALIDO" IS 'INDICA SE O VALOR É OU VÁLIDO (0 PARA INVÁLIDO E 1 PARA VÁLIDO)';
   COMMENT ON COLUMN "VALOR_P"."VALP_TX_VERSAO" IS 'VERSÃO DO VALOR P BAIXADO
';
--------------------------------------------------------
--  DDL for Table VALOR_SOROLOGICO
--------------------------------------------------------

  CREATE TABLE "VALOR_SOROLOGICO" 
   (	"LOCU_ID" VARCHAR2(4), 
	"VASO_TX_ANTIGENO" VARCHAR2(10), 
	"VASO_TX_VALORES" VARCHAR2(100)
   ) ;

   COMMENT ON COLUMN "VALOR_SOROLOGICO"."LOCU_ID" IS 'Código do locus (Chave estrangeira para a tabela de Locus).';
   COMMENT ON COLUMN "VALOR_SOROLOGICO"."VASO_TX_ANTIGENO" IS 'Código do antígeno e identificador da tabela.';
   COMMENT ON COLUMN "VALOR_SOROLOGICO"."VASO_TX_VALORES" IS 'Valores válidos relacionados ao antígeno.';
   COMMENT ON TABLE "VALOR_SOROLOGICO"  IS 'Tabela de valores por antígeno.';
--------------------------------------------------------
--  DDL for Table VERSAO_ARQUIVO_BAIXADO
--------------------------------------------------------

  CREATE TABLE "VERSAO_ARQUIVO_BAIXADO" 
   (	"VEAB_ID" NUMBER, 
	"VEAB_TX_NOME_ARQUIVO" VARCHAR2(80), 
	"VEAB_TX_VERSAO" VARCHAR2(15)
   ) ;

   COMMENT ON COLUMN "VERSAO_ARQUIVO_BAIXADO"."VEAB_ID" IS 'IDENTIFICADOR DA VERSÃO DO ARQUIVO BAIXADO';
   COMMENT ON COLUMN "VERSAO_ARQUIVO_BAIXADO"."VEAB_TX_NOME_ARQUIVO" IS 'NOME DO ARQUIVO BAIXADO';
   COMMENT ON COLUMN "VERSAO_ARQUIVO_BAIXADO"."VEAB_TX_VERSAO" IS 'VERSÃO DO ARQUIVO BAIXADO
';
--------------------------------------------------------
--  DDL for View VALOR_DNA_NMDP
--------------------------------------------------------

  CREATE VIEW "VALOR_DNA_NMDP" ("LOCU_ID", "DNNM_TX_VALOR", "DNNM_IN_ATIVO") AS 
  SELECT LOCU_ID, DNNM_TX_VALOR, DNNM_IN_ATIVO FROM VALOR_DNA_NMDP_VALIDO;

   COMMENT ON TABLE "VALOR_DNA_NMDP"  IS 'VIEW DE RESULTADO DOS VALORES VALIDOS DE DNA E NMDP'
;
--------------------------------------------------------
--  DDL for Table MUNICIPIO
--------------------------------------------------------

CREATE TABLE "MUNICIPIO"
(
  "MUNI_ID" NUMBER NOT NULL,
  "UF_SIGLA" VARCHAR2(2) NOT NULL,
  "MUNI_TX_NOME" VARCHAR2(72) NOT NULL,
  "MUNI_TX_CODIGO_IBGE" VARCHAR2(7),
  "MUNI_TX_CODIGO_DNE" VARCHAR2(8)
);

  COMMENT ON COLUMN "MUNICIPIO"."MUNI_ID" IS 'Identificador de Município';
  COMMENT ON COLUMN "MUNICIPIO"."UF_SIGLA" IS 'Id de UF';
  COMMENT ON COLUMN "MUNICIPIO"."MUNI_TX_NOME" IS 'Nome do Município';
  COMMENT ON COLUMN "MUNICIPIO"."MUNI_TX_CODIGO_IBGE" IS 'Código do IBGE do Municiípio';
  COMMENT ON COLUMN "MUNICIPIO"."MUNI_TX_CODIGO_DNE" IS 'Código DNE do Município';
  COMMENT ON TABLE "MUNICIPIO"  IS 'Município';
  
--------------------------------------------------------
--  DDL for Table SPLIT_VALOR_G
--------------------------------------------------------  

  CREATE TABLE "SPLIT_VALOR_G" 
   (	"LOCU_ID" VARCHAR2(4 BYTE), 
	"SPVG_TX_VALOR" VARCHAR2(20 BYTE), 
	"SPVG_TX_NOME_GRUPO" VARCHAR2(20 BYTE), 
	"SPVG_NR_VALIDO" NUMBER, 
	"SPVG_TX_VERSAO" VARCHAR2(10 BYTE)
   );

   COMMENT ON COLUMN "SPLIT_VALOR_G"."LOCU_ID" IS 'NOME DO LOCUS E REFERENCIA PARA A TABELA DE LOCUS';
   COMMENT ON COLUMN "SPLIT_VALOR_G"."SPVG_TX_VALOR" IS 'ALELOS VINCULADOS AO GRUPO';
   COMMENT ON COLUMN "SPLIT_VALOR_G"."SPVG_TX_NOME_GRUPO" IS 'NOME DO GRUPO';
   COMMENT ON COLUMN "SPLIT_VALOR_G"."SPVG_NR_VALIDO" IS 'INDICA SE O VALOR É OU VÁLIDO (0 PARA INVÁLIDO E 1 PARA VÁLIDO)';
   COMMENT ON COLUMN "SPLIT_VALOR_G"."SPVG_TX_VERSAO" IS 'VERSÃO DO VALOR G BAIXADO';

--------------------------------------------------------
--  DDL for Table SPLIT_VALOR_P
--------------------------------------------------------

  CREATE TABLE "SPLIT_VALOR_P" 
   (	"LOCU_ID" VARCHAR2(4 BYTE), 
	"SPVP_TX_VALOR" VARCHAR2(20 BYTE), 
	"SPVP_TX_NOME_GRUPO" VARCHAR2(20 BYTE), 
	"SPVP_NR_VALIDO" NUMBER, 
	"SPVP_TX_VERSAO" VARCHAR2(10 BYTE)
   );

   COMMENT ON COLUMN "SPLIT_VALOR_P"."LOCU_ID" IS 'NOME DO LOCUS E REFERENCIA PARA A TABELA DE LOCUS';
   COMMENT ON COLUMN "SPLIT_VALOR_P"."SPVP_TX_VALOR" IS 'ALELOS VINCULADOS AO GRUPO';
   COMMENT ON COLUMN "SPLIT_VALOR_P"."SPVP_TX_NOME_GRUPO" IS 'NOME DO GRUPO';
   COMMENT ON COLUMN "SPLIT_VALOR_P"."SPVP_NR_VALIDO" IS 'INDICA SE O VALOR É OU VÁLIDO (0 PARA INVÁLIDO E 1 PARA VÁLIDO)';
   COMMENT ON COLUMN "SPLIT_VALOR_P"."SPVP_TX_VERSAO" IS 'VERSÃO DO VALOR G BAIXADO';
   
--------------------------------------------------------
--  DDL for Table PEDIDO_ENVIO_EMDIS
--------------------------------------------------------

  CREATE TABLE "PEDIDO_ENVIO_EMDIS" 
   (	"PEEE_ID" NUMBER, 
	"PEEE_DT_CRIACAO" TIMESTAMP (6), 
	"PEEE_DT_ENVIO_EMDIS" TIMESTAMP (6), 
	"PEEE_TX_NM_ARQUIVO_ENVIO" VARCHAR2(250 BYTE), 
	"PEEE_DT_RETORNO_EMDIS" TIMESTAMP (6), 
	"PEEE_TX_NM_ARQUIVO_RETORNO" VARCHAR2(20 BYTE), 
	"SOLI_ID" NUMBER
   );

   COMMENT ON COLUMN "PEDIDO_ENVIO_EMDIS"."PEEE_ID" IS 'Identificação do pedido de envio para emdis';
   COMMENT ON COLUMN "PEDIDO_ENVIO_EMDIS"."PEEE_DT_CRIACAO" IS 'Data da criação do pedido emdis';
   COMMENT ON COLUMN "PEDIDO_ENVIO_EMDIS"."PEEE_DT_ENVIO_EMDIS" IS 'Data em que o pedido foi efetivamente enviado para o emdis';
   COMMENT ON COLUMN "PEDIDO_ENVIO_EMDIS"."PEEE_TX_NM_ARQUIVO_ENVIO" IS 'Nome do arquivo de envio do emdis';
   COMMENT ON COLUMN "PEDIDO_ENVIO_EMDIS"."PEEE_DT_RETORNO_EMDIS" IS 'Data de retorno emdis';
   COMMENT ON COLUMN "PEDIDO_ENVIO_EMDIS"."PEEE_TX_NM_ARQUIVO_RETORNO" IS 'Nome do arquivo de retorno do emdis';
   COMMENT ON COLUMN "PEDIDO_ENVIO_EMDIS"."SOLI_ID" IS 'id da solicitação de pedido emdis';
   COMMENT ON TABLE "PEDIDO_ENVIO_EMDIS"  IS 'Tabela de controle depdidos realizados ao emdis';   

   
   
CREATE TABLE "TIPO_PERMISSIVIDADE" 
(
  "TIPM_ID" NUMBER NOT NULL 
, "TIPM_TX_DESCRICAO" VARCHAR2(30 BYTE) NOT NULL
);
COMMENT ON TABLE TIPO_PERMISSIVIDADE IS 'Tabela de tipos de permissividade.';

COMMENT ON COLUMN TIPO_PERMISSIVIDADE.TIPM_ID IS 'Identificador único da tabela.';

--------------------------------------------------------
--  DDL for Table PESQUISA_WMDA
--------------------------------------------------------

CREATE TABLE "PESQUISA_WMDA" 
(	"PEWM_ID" NUMBER NOT NULL, 
	"BUSC_ID" NUMBER NOT NULL, 
	"PEWM_NR_SEARCH" NUMBER NOT NULL, 
	"PEWM_NR_SEARCH_RESULT" NUMBER, 
	"PEWM_TX_SEARCH_TYPE" VARCHAR2(2 BYTE) NOT NULL, 
	"PEWM_TX_ALGORITHM" VARCHAR2(1 BYTE), 
	"PEWM_DT_ENVIO" DATE NOT NULL, 
	"PEWM_DT_RESULTADO" DATE,
	"PEWM_TX_WMDA_ID" VARCHAR2(10 BYTE),
	"PEWM_NR_STATUS" VARCHAR2(5 BYTE)
);

COMMENT ON COLUMN "PESQUISA_WMDA"."PEWM_ID" IS 'Identificador da tabela';
COMMENT ON COLUMN "PESQUISA_WMDA"."BUSC_ID" IS 'Identificador da busca';
COMMENT ON COLUMN "PESQUISA_WMDA"."PEWM_NR_SEARCH" IS 'Identificador da busca no WMDA retorno do post /pastients/{wmdaId}/searches';
COMMENT ON COLUMN "PESQUISA_WMDA"."PEWM_NR_SEARCH_RESULT" IS 'Identificador da pesquisa - retorno do get /patients/{wmdaId}/searches';
COMMENT ON COLUMN "PESQUISA_WMDA"."PEWM_TX_SEARCH_TYPE" IS 'Tipo de busca DR para doador de medula e CB para cordão';
COMMENT ON COLUMN "PESQUISA_WMDA"."PEWM_TX_ALGORITHM" IS 'Tipo de algoritimo utilizado H para haplotipo e F para alélico';
COMMENT ON COLUMN "PESQUISA_WMDA"."PEWM_DT_ENVIO" IS 'Data e hora do post';
COMMENT ON COLUMN "PESQUISA_WMDA"."PEWM_DT_RESULTADO" IS 'Data e hora do get dos resultados';
COMMENT ON COLUMN "PESQUISA_WMDA"."PEWM_TX_WMDA_ID" IS 'Identificação do paciente no registro do wmda.';
COMMENT ON COLUMN "PESQUISA_WMDA"."PEWM_NR_STATUS" IS 'Status da pesquisa: 1-aberta, 2-concluída e 3-cancelada.';

--------------------------------------------------------
--  DDL for Table PESQUISA_WMDA_DOADOR
--------------------------------------------------------

CREATE TABLE "PESQUISA_WMDA_DOADOR" 
(	"PEWD_ID" NUMBER NOT NULL, 
	"PEWM_ID" NUMBER NOT NULL, 
	"PEWD_TX_ID" VARCHAR2(20 BYTE) NOT NULL, 
	"PEWD_TX_GRID" VARCHAR2(19 BYTE), 
	"PEWD_NR_DON_POOL" VARCHAR2(4 BYTE) NOT NULL, 
	"PEWD_TX_JSON" CLOB NOT NULL,
	"PEWD_TX_LOG" CLOB
	
);
		
COMMENT ON COLUMN "PESQUISA_WMDA_DOADOR"."PEWD_ID" IS 'Identificador da tabela';
COMMENT ON COLUMN "PESQUISA_WMDA_DOADOR"."PEWM_ID" IS 'Identificado/chave estrangeira para PESQUISA_WMDA';
COMMENT ON COLUMN "PESQUISA_WMDA_DOADOR"."PEWD_TX_ID" IS 'Id do doador - retorno do serviço';
COMMENT ON COLUMN "PESQUISA_WMDA_DOADOR"."PEWD_TX_GRID" IS 'GRID do doador - retorno do serviço';
COMMENT ON COLUMN "PESQUISA_WMDA_DOADOR"."PEWD_NR_DON_POOL" IS 'Idenficação do registro do doador - retorno do serviço';
COMMENT ON COLUMN "PESQUISA_WMDA_DOADOR"."PEWD_TX_JSON" IS 'JSON com dados detalhados do doador - retorno do serviço';

--------------------------------------------------------
--  DDL for Table MOTIVO_CANCELAMENTO_SOLICITACAO
--------------------------------------------------------

CREATE TABLE MOTIVO_CANCELAMENTO_SOLICITACAO 
(
  MOCS_ID NUMBER NOT NULL 
, MOCS_TX_DESCRICAO VARCHAR2(50) NOT NULL 
);

COMMENT ON TABLE MOTIVO_CANCELAMENTO_SOLICITACAO IS 'Motivos de cancelamento das solicitaçoes.';
COMMENT ON COLUMN MOTIVO_CANCELAMENTO_SOLICITACAO.MOCS_ID IS 'Identificador unico do registro.';
COMMENT ON COLUMN MOTIVO_CANCELAMENTO_SOLICITACAO.MOCS_TX_DESCRICAO IS 'Descriçao do motivo do cancelamento.';

   --------------------------------------------------------
   --  DDL for Table CONTAGEM_CELULA
   --------------------------------------------------------
   
  CREATE TABLE "CONTAGEM_CELULA" 
   ("COCE_ID" NUMBER NOT NULL , 
	"PEWO_ID" NUMBER NOT NULL , 
	"COCE_IN_FONTE_CELULA" VARCHAR2(20 BYTE), 
	"COCE_IN_ABO" VARCHAR2(20 BYTE), 
	"COCE_IN_MANIPULACAO_PRODUTO" NUMBER, 
	"COCE_TX_MANIPULACAO" VARCHAR2(20 BYTE), 
	"COCE_DT_COLETA" DATE, 
	"COCE_VL_VOLUME_BOLSA" NUMBER, 
	"COCE_VL_ANTICOAGULANTE" NUMBER, 
	"COCE_NR_TCN" NUMBER, 
	"COCE_NR_TCN_KG" NUMBER, 
	"COCE_DT_AFERISE_0" DATE, 
	"COCE_VL_VOLUME_BOLSA_0" NUMBER, 
	"COCE_NR_CELULA_0" NUMBER, 
	"COCE_NR_CELULA_KG_0" NUMBER, 
	"COCE_DT_AFERISE_1" DATE, 
	"COCE_VL_VOLUME_BOLSA_1" NUMBER, 
	"COCE_NR_CELULA_1" NUMBER, 
	"COCE_NR_CELULA_KG_1" NUMBER, 
	"COCE_DT_AFERISE_2" DATE, 
	"COCE_VL_VOLUME_BOLSA_2" NUMBER, 
	"COCE_NR_CELULA_2" NUMBER, 
	"COCE_NR_CELULA_KG_2" NUMBER, 
	"COCE_DT_ENVIO" DATE, 
	"COCE_TX_ANTICOAGULANTE" VARCHAR2(20 BYTE), 
	"COCE_IN_INTERCORRENCIA" NUMBER, 
	"COCE_TX_INTERCORRENCIA" VARCHAR2(20 BYTE) 
	
 	);

   COMMENT ON COLUMN "CONTAGEM_CELULA"."COCE_ID" IS 'Identificador da contagem celula.';
   COMMENT ON COLUMN "CONTAGEM_CELULA"."PEWO_ID" IS 'Identificador do pedido de workup.';
   COMMENT ON COLUMN "CONTAGEM_CELULA"."COCE_IN_FONTE_CELULA" IS 'Fonte de Celula';
   COMMENT ON COLUMN "CONTAGEM_CELULA"."COCE_IN_ABO" IS 'ABO';
   COMMENT ON COLUMN "CONTAGEM_CELULA"."COCE_IN_MANIPULACAO_PRODUTO" IS 'Manipulação do produto: 0-FALSE, 1-TRUE.';
   COMMENT ON COLUMN "CONTAGEM_CELULA"."COCE_TX_MANIPULACAO" IS 'Qual Manipulação do produto.';
   COMMENT ON COLUMN "CONTAGEM_CELULA"."COCE_DT_COLETA" IS 'Data da Coleta de Medula.';
   COMMENT ON COLUMN "CONTAGEM_CELULA"."COCE_VL_VOLUME_BOLSA" IS 'Volume da bolsa ao Coletar Medula.';
   COMMENT ON COLUMN "CONTAGEM_CELULA"."COCE_VL_ANTICOAGULANTE" IS 'Volume de Anticoagulante na Bolsa.';
   COMMENT ON COLUMN "CONTAGEM_CELULA"."COCE_NR_TCN" IS 'Quantidade de TCN na Bolsa.';
   COMMENT ON COLUMN "CONTAGEM_CELULA"."COCE_NR_TCN_KG" IS 'Quantidade de TCN por kg na Bolsa.';
   COMMENT ON COLUMN "CONTAGEM_CELULA"."COCE_DT_AFERISE_0" IS 'Data da 1ª aferise sangue periferico ou Linfocito.';
   COMMENT ON COLUMN "CONTAGEM_CELULA"."COCE_VL_VOLUME_BOLSA_0" IS 'Volume da bolsa da 1ª aferise.';
   COMMENT ON COLUMN "CONTAGEM_CELULA"."COCE_NR_CELULA_0" IS 'Numero de Celulas da 1ª aferise.';
   COMMENT ON COLUMN "CONTAGEM_CELULA"."COCE_NR_CELULA_KG_0" IS 'Numero de Celulas por Kg do paciente da 1ª aferise.';
   COMMENT ON COLUMN "CONTAGEM_CELULA"."COCE_DT_AFERISE_1" IS 'Data da 2ª aferise sangue periferico ou Linfocito.';
   COMMENT ON COLUMN "CONTAGEM_CELULA"."COCE_VL_VOLUME_BOLSA_1" IS 'Volume da bolsa da 2ª aferise.';
   COMMENT ON COLUMN "CONTAGEM_CELULA"."COCE_NR_CELULA_1" IS 'Numero de Celulas da 2ª aferise.';
   COMMENT ON COLUMN "CONTAGEM_CELULA"."COCE_NR_CELULA_KG_1" IS 'Numero de Celulas por Kg do paciente da 2ª aferise.';
   COMMENT ON COLUMN "CONTAGEM_CELULA"."COCE_DT_AFERISE_2" IS 'Data da 3ª aferise sangue periferico ou Linfocito.';
   COMMENT ON COLUMN "CONTAGEM_CELULA"."COCE_VL_VOLUME_BOLSA_2" IS 'Volume da bolsa da 3ª aferise.';
   COMMENT ON COLUMN "CONTAGEM_CELULA"."COCE_NR_CELULA_2" IS 'Numero de Celulas da 3ª aferise.';
   COMMENT ON COLUMN "CONTAGEM_CELULA"."COCE_NR_CELULA_KG_2" IS 'Numero de Celulas por Kg do paciente da 3ª aferise.';
   COMMENT ON COLUMN "CONTAGEM_CELULA"."COCE_DT_ENVIO" IS 'Data do Envio da coleta.';
   COMMENT ON COLUMN "CONTAGEM_CELULA"."COCE_TX_ANTICOAGULANTE" IS 'Anticoagulante Utilizado.';
   COMMENT ON COLUMN "CONTAGEM_CELULA"."COCE_IN_INTERCORRENCIA" IS 'Houve alguma intercorrência durante ou após a coleta de CTH?: 0-FALSE, 1-TRUE.';
   COMMENT ON COLUMN "CONTAGEM_CELULA"."COCE_TX_INTERCORRENCIA" IS 'Quais intercorrências durante ou após a coleta de CTH.';
   
   --------------------------------------------------------
   --  DDL for Table FORMULARIO_POSCOLETA
   --------------------------------------------------------
   
  CREATE TABLE FORMULARIO_POSCOLETA
(FOPO_ID NUMBER NOT NULL , 
 PEWO_ID NUMBER, 
 USUA_ID NUMBER,
 FOPO_DT_CRIACAO TIMESTAMP (6) NOT NULL , 
 FOPO_DT_ATUALIZACAO TIMESTAMP (6)
);

 
 COMMENT ON COLUMN FORMULARIO_POSCOLETA.FOPO_ID IS 'Chave primária que identifica com exclusividade um formulário pós coleta.'; 
 COMMENT ON COLUMN FORMULARIO_POSCOLETA.PEWO_ID IS 'Chave estrangeira para pedido de workup';
 COMMENT ON COLUMN FORMULARIO_POSCOLETA.USUA_ID IS 'Id do usuário que preencheu o formulário pós coleta';
 COMMENT ON COLUMN FORMULARIO_POSCOLETA.FOPO_DT_CRIACAO IS 'Data de criação do fromulário pós coleta.';
 COMMENT ON COLUMN FORMULARIO_POSCOLETA.FOPO_DT_ATUALIZACAO IS 'Data de atualização do fromulário pós coleta.';
 