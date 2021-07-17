CREATE OR REPLACE FORCE EDITIONABLE VIEW "MODRED"."MATCH_DTO" AS 
  SELECT M.MATC_ID, M.DOAD_ID, D.DOAD_TX_ID_REGISTRO, r.regi_tx_nome, D.DOAD_NR_DMR, D.DOAD_TX_NOME, d.doad_in_sexo,
d.doad_dt_nascimento, d.doad_dt_atualizacao, d.doad_vl_peso, d.doad_tx_abo, MATC_TX_MISMATCH, OP.outrosProcessos,
case when rd.totalRessalvas >= 1 then 'true' else 'false' end as possuiRessalva,
case when CM.totalComentario >= 1 then 'true' else 'false' end as possuiComentario,
m.MATC_TX_GRADE,
case when presc.totalPrescricao >= 1 then 'true' else 'false' end as temPrescricao,
p.proc_id,
pex.peex_id, SOLI.SOLI_ID, DECODE(pex.paci_nr_rmr, null, soli.paci_nr_rmr, pex.paci_nr_rmr)  as rmrPedidoExame,
d.doad_vl_quant_total_tcn, d.doad_vl_quant_total_cd34,
d.doad_in_tipo, 
bsc.basc_tx_sigla || d.DOAD_TX_ID_BANCO_SANGUE_CORDAO as idBscup,
M.MATC_IN_STATUS, D.DOAD_TX_FASE, B.PACI_NR_RMR, B.BUSC_ID,
case when g.totalGenotipoDivergente >= 1 then 'true' else 'false' end as possuiGenotipoDivergente,
null as bupr_vl_peso,
'MATCH' as TIPOMATCH,
M.MATC_IN_DISPONIBILIZADO,
SOLI.TISO_ID
FROM MATCH M, 
BUSCA B,
DOADOR D,
(SELECT M1.MATC_ID, M1.DOAD_ID, COUNT(M1.MATC_ID) outrosProcessos
              FROM MATCH M1 
              INNER JOIN BUSCA B1 ON (M1.BUSC_ID = B1.BUSC_ID)
              INNER JOIN STATUS_BUSCA SB ON (B1.stbu_id = SB.STBU_ID)              
              WHERE sb.stbu_in_busca_ativa = 1
              GROUP BY M1.MATC_ID, M1.DOAD_ID
             ) OP,
(SELECT R.DOAD_ID, COUNT(R.REDO_ID) totalRessalvas FROM RESSALVA_DOADOR R GROUP BY R.DOAD_ID) RD,
(select c.matc_id, count(c.coma_id) totalComentario from COMENTARIO_MATCH c group by c.matc_id) CM,
(select s.matc_id, count(p.pres_id) totalPrescricao from prescricao p inner join solicitacao s on (p.soli_id = s.soli_id)
 where s.tiso_id = 3 and s.soli_nr_status = 1 group by s.matc_id) presc,
Processo p,
(select pe.PEEX_ID, M1.DOAD_ID, B1.PACI_NR_RMR
        from Pedido_Exame pe 
        INNER join Solicitacao s ON (PE.SOLI_ID = S.SOLI_ID) 
        INNER join match m1 ON (S.MATC_ID = M1.MATC_ID)
        INNER JOIN BUSCA B1 ON (M1.BUSC_ID = B1.BUSC_ID)
        where (pe.STPX_ID = 0 or pe.STPX_ID = 4)
		and s.SOLI_NR_STATUS = 1 and m1.MATC_IN_STATUS = 1) PEX,
(select S.SOLI_ID, M1.DOAD_ID, B1.PACI_NR_RMR, S.TISO_ID
        from Solicitacao s  
        INNER join match m1 ON (S.MATC_ID = M1.MATC_ID)
        INNER JOIN BUSCA B1 ON (M1.BUSC_ID = B1.BUSC_ID)
        where s.SOLI_NR_STATUS = 1 and m1.MATC_IN_STATUS = 1) SOLI,       
banco_sangue_cordao bsc,
REGISTRO R,
(select gd.doad_id, count(*) totalGenotipoDivergente from valor_genotipo_doador vgd inner join genotipo_doador gd on (vgd.gedo_id = gd.gedo_id) where vgd.VAGD_IN_DIVERGENTE = 1 group by gd.doad_id) g
WHERE 
M.BUSC_ID = B.BUSC_ID
AND M.DOAD_ID = D.DOAD_ID
AND OP.MATC_ID (+)!= M.MATC_ID
AND OP.DOAD_ID (+)= M.DOAD_ID
AND RD.DOAD_ID (+)=M.DOAD_ID
AND CM.MATC_ID (+)=M.MATC_ID
AND presc.MATC_ID (+) = M.MATC_ID
AND P.PACI_NR_RMR = b.paci_nr_rmr
AND p.proc_nr_tipo = 5
AND p.proc_nr_status = 1
AND pex.doad_id (+) = M.DOAD_ID
AND SOLI.doad_id (+) = M.DOAD_ID
and bsc.basc_id (+)= d.basc_id
AND R.REGI_ID (+)=D.REGI_ID_ORIGEM
and g.doad_id (+)= m.doad_id
ORDER BY DECODE(D.DOAD_IN_TIPO, 0, 0, 1, 1, 2, 0, 3, 1),
D.DOAD_IN_SEXO DESC,
D.DOAD_DT_NASCIMENTO DESC,
D.DOAD_VL_PESO DESC
;
