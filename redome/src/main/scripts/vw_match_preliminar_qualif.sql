CREATE OR REPLACE FORCE EDITIONABLE VIEW "MODRED"."VW_MATCH_PRELIMINAR_QUALIF" ("BUPR_ID", "GEDO_ID", "LOCU_ID", "TROCA", "POS_1_TIPO", "POS_2_TIPO", "POS_1_LETRA", "POS_2_LETRA") AS 
  SELECT
    bupr_id,
    gedo_id,
    locu_id,
    CASE
        WHEN MAX(nvl(pos_1_igual, 0)) + MAX(nvl(pos_2_igual, 0)) = 0   THEN 1
        ELSE 0
    END troca,
    MAX(pos_1_tipo) pos_1_tipo,
    MAX(pos_2_tipo) pos_2_tipo,
    decode(MAX(pos_1_letra),'0','M','1','L','2','P','3','A','') pos_1_letra,
    decode(MAX(pos_2_letra),'0','M','1','L','2','P','3','A','') pos_2_letra
FROM
    (
        SELECT
            bupr_id,
            gedo_id,
            locu_id,   
            DECODE(posicao, 1, posicao_igual, NULL) pos_1_igual,
            DECODE(posicao, 2, posicao_igual, NULL) pos_2_igual,
            DECODE(posicao, 1, tipo, NULL) pos_1_tipo,
            DECODE(posicao, 2, tipo, NULL) pos_2_tipo,
            DECODE(posicao, 1, letra, NULL) pos_1_letra,
            DECODE(posicao, 2, letra, NULL) pos_2_letra
        FROM
            (
                SELECT
                    preliminar.bupr_id,
                    doador.gedo_id,
                    doador.locu_id,
                    doador.vgbd_nr_tipo          tipo,
                    preliminar.vabp_nr_posicao   posicao,
                    CASE preliminar.vabp_nr_posicao
                        WHEN doador.vgbd_nr_posicao   THEN 1
                        ELSE 0
                    END posicao_igual,
                    CASE
                        WHEN ( doador.vgbd_nr_tipo < 3
                               OR preliminar.vabp_nr_tipo < 3 )
                             AND ( doador.vgbd_tx_valor LIKE preliminar.vabp_tx_valor || '%'
                                   OR preliminar.vabp_tx_valor LIKE doador.vgbd_tx_valor || '%' ) THEN '2'
                        WHEN ( doador.vgbd_nr_tipo >= 3
                               OR preliminar.vabp_nr_tipo >= 3 )
                             AND ( doador.vgbd_tx_valor LIKE preliminar.vabp_tx_valor || '%'
                                   OR preliminar.vabp_tx_valor LIKE doador.vgbd_tx_valor || '%' ) THEN '3'
                        WHEN ( doador.vgbd_nr_tipo < 3
                               OR preliminar.vabp_nr_tipo < 3 )
                             AND ( doador.vgbd_tx_valor NOT LIKE preliminar.vabp_tx_valor || '%'
                                   AND preliminar.vabp_tx_valor NOT LIKE doador.vgbd_tx_valor || '%' ) THEN '0'
                        WHEN ( doador.vgbd_nr_tipo >= 3
                               OR preliminar.vabp_nr_tipo >= 3 )
                             AND ( doador.vgbd_tx_valor NOT LIKE preliminar.vabp_tx_valor || '%'
                                   AND preliminar.vabp_tx_valor NOT LIKE doador.vgbd_tx_valor || '%' ) THEN '1'
                        ELSE NULL
                    END letra
                FROM
                    valor_genotipo_busca_doador doador,
                    valor_genotipo_busca_prelimina preliminar,
                    vw_match_preliminar matchpreliminar
                WHERE
                    doador.gedo_id = matchpreliminar.gedo_id
                    AND preliminar.bupr_id = matchpreliminar.bupr_id
                    AND doador.locu_id = preliminar.locu_id
            )
    )
GROUP BY
    bupr_id,
    gedo_id,
    locu_id
ORDER BY
    1,
    2,
    3
;
