  CREATE OR REPLACE FORCE EDITIONABLE VIEW "MODRED"."MATCH_PRELIMINAR_DTO" AS 
  SELECT 
M.MAPR_ID,              --00
M.DOAD_ID,              --01 
D.DOAD_TX_ID_REGISTRO,  --02
r.regi_tx_nome,         --03          
D.DOAD_NR_DMR,          --04 
D.DOAD_TX_NOME,         --05  
d.doad_in_sexo,         --06
d.doad_dt_nascimento,   --07
d.doad_dt_atualizacao,  --08
d.doad_vl_peso,         --09 
d.doad_tx_abo,          --10
MAPR_TX_MISMATCH,         --11
0 as outrosProcessos,     --12
'false' as possuiRessalva, --13
'false' as possuiComentario, --14
MAPR_TX_GRADE, --15
'false' as temPrescricao, --16
0 as proc_id,    --17
null as peex_id,  --18
null as SOLI_ID, --19
null as rmrPedidoExame, --20
d.doad_vl_quant_total_tcn,  --21
d.doad_vl_quant_total_cd34, --22
d.doad_in_tipo,   --23
bsc.basc_tx_sigla || d.DOAD_TX_ID_BANCO_SANGUE_CORDAO as idBscup, --24
1 as MAPR_IN_STATUS,  --25
D.DOAD_TX_FASE,    --26
null as PACI_NR_RMR,      --27
B.BUPR_ID,  --28
'false' as possuiGenotipoDivergente, --29
b.bupr_vl_peso, -- 30
'MATCHPRELIMINAR' as TIPOMATCH, --31
0 as MATC_IN_DISPONIBILIZADO,  -- 32
null as TISO_ID   --33
FROM MATCH_PRELIMINAR M, 
BUSCA_PRELIMINAR B,
DOADOR D,
banco_sangue_cordao bsc,
registro r
WHERE 
M.BUPR_ID = B.BUPR_ID
AND M.DOAD_ID = D.DOAD_ID
and bsc.basc_id (+)= d.basc_id
and r.regi_id = d.regi_id_origem
ORDER BY DECODE(D.DOAD_IN_TIPO, 0, 0, 1, 1, 2, 0, 3, 1),
D.DOAD_IN_SEXO DESC,
D.DOAD_DT_NASCIMENTO DESC,
D.DOAD_VL_PESO DESC
;
