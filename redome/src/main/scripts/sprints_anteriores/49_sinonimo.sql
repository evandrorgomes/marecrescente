CREATE OR REPLACE SYNONYM MODRED_APLICACAO.MATCH_DTO FOR MODRED.MATCH_DTO;

DROP SYNONYM MODRED_APLICACAO.SQ_GEPR_ID;
DROP SYNONYM MODRED_APLICACAO.GENOTIPO_PRELIMINAR;
DROP SYNONYM MODRED_APLICACAO.GENOTIPO_BUSCA_PRELIMINAR;
DROP SYNONYM MODRED_APLICACAO.SQ_GEBP_ID;
DROP SYNONYM MODRED_APLICACAO.GENOTIPO_EXPANDIDO_PRELIMINAR;
DROP SYNONYM MODRED_APLICACAO.SQ_GEEP_ID;

CREATE OR REPLACE SYNONYM MODRED_APLICACAO.VALOR_GENOTIPO_PRELIMINAR FOR MODRED.VALOR_GENOTIPO_PRELIMINAR;
CREATE OR REPLACE SYNONYM MODRED_APLICACAO.VALOR_GENOTIPO_BUSCA_PRELIMINA FOR MODRED.VALOR_GENOTIPO_BUSCA_PRELIMINA;
CREATE OR REPLACE SYNONYM MODRED_APLICACAO.VALOR_GENOTIPO_EXPAND_PRELIMIN FOR MODRED.VALOR_GENOTIPO_EXPAND_PRELIMIN;

CREATE OR REPLACE SYNONYM MODRED_APLICACAO.SQ_VABP_ID FOR MODRED.SQ_VABP_ID;
CREATE OR REPLACE SYNONYM MODRED_APLICACAO.SQ_VAEP_ID FOR MODRED.SQ_VAEP_ID;

CREATE OR REPLACE SYNONYM MODRED_APLICACAO.MATCH_PRELIMINAR_DTO FOR MODRED.MATCH_PRELIMINAR_DTO;

CREATE OR REPLACE SYNONYM MODRED_APLICACAO.vw_match_preliminar FOR MODRED.vw_match_preliminar;
CREATE OR REPLACE SYNONYM MODRED_APLICACAO.vw_match_preliminar_qualif FOR MODRED.vw_match_preliminar_qualif;
CREATE OR REPLACE SYNONYM MODRED_APLICACAO.MUNICIPIO FOR MODRED.MUNICIPIO;
CREATE OR REPLACE SYNONYM MODRED_APLICACAO.ESTADO_CIVIL FOR MODRED.ESTADO_CIVIL;

CREATE OR REPLACE SYNONYM MODRED_APLICACAO.PROC_MATCH_PACIENTE FOR MODRED.PROC_MATCH_PACIENTE;
CREATE OR REPLACE SYNONYM MODRED_APLICACAO.PROC_MATCH_PRELIMINAR FOR MODRED.PROC_MATCH_PRELIMINAR;

CREATE OR REPLACE SYNONYM MODRED_APLICACAO.TIPO_PERMISSIVIDADE FOR MODRED.TIPO_PERMISSIVIDADE;
CREATE OR REPLACE SYNONYM MODRED_APLICACAO.GRUPO_PERMISSIVIDADE FOR MODRED.GRUPO_PERMISSIVIDADE;
CREATE OR REPLACE SYNONYM MODRED_APLICACAO.DPB1_GRUPO_PERMISSIVIDADE FOR MODRED.DPB1_GRUPO_PERMISSIVIDADE;
CREATE OR REPLACE SYNONYM MODRED_APLICACAO.DPB1_GRUPO_PERMIS_PACIE_DOADOR FOR MODRED.DPB1_GRUPO_PERMIS_PACIE_DOADOR;
CREATE OR REPLACE SYNONYM MODRED_APLICACAO.SQ_DPGP_ID FOR MODRED.SQ_DPGP_ID;
CREATE OR REPLACE SYNONYM MODRED_APLICACAO.SQ_DGPD_ID FOR MODRED.SQ_DGPD_ID;


