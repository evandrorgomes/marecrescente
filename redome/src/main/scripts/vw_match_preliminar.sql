CREATE OR REPLACE VIEW "MODRED"."VW_MATCH_PRELIMINAR" AS 
  select /*+ FIRST_ROWS */gedo_id, bupr_id, sum(qtd) qtdab, sum(mismatches) mismatches
from(
    (select 0 qtd, gedo_id, bupr_id, 2 - max(matches) mismatches from
        (select gedo_id, bupr_id, count(vaep_nr_posicao) matches, tipo from
            (select distinct gedo_id, bupr_id,  vaep_nr_posicao, tipo from    
                (Select doador.gedo_id, preliminar.bupr_id, preliminar.vaep_nr_posicao, 
                        CASE preliminar.vaep_nr_posicao WHEN doador.vged_nr_posicao THEN 1 ELSE 2 END tipo
                 from valor_genotipo_expand_doador doador, valor_genotipo_expand_prelimin preliminar
                 where
                 -- preliminar.bupr_id = 3845 AND
                  preliminar.locu_id = 'DQB1' AND
                  doador.locu_id = preliminar.locu_id AND
                  doador.vged_tx_valor = preliminar.vaep_nr_valor ))
          group by gedo_id, bupr_id, tipo)
       group by 0, gedo_id, bupr_id)
    /* precisa ser union all senão se tiver mismatches nos 3 vai contar apenas um pois o union puro faz distinct */
    union all
    (select 0 qtd, gedo_id,  bupr_id, 2 - max(matches) mismatches from
        (select gedo_id, bupr_id, count(vaep_nr_posicao) matches, tipo from
            (select distinct gedo_id, bupr_id,  vaep_nr_posicao, tipo from    
                (Select doador.gedo_id, preliminar.bupr_id, preliminar.vaep_nr_posicao, 
                        CASE preliminar.vaep_nr_posicao WHEN doador.vged_nr_posicao THEN 1 ELSE 2 END tipo
                 from valor_genotipo_expand_doador doador, valor_genotipo_expand_prelimin preliminar
                 where
                 --preliminar.bupr_id = 3845 AND
                  preliminar.locu_id = 'C' AND
                  doador.locu_id = preliminar.locu_id AND
                  doador.vged_tx_valor = preliminar.vaep_nr_valor ))
          group by gedo_id, bupr_id, tipo)
       group by 0, gedo_id, bupr_id)
    union all
    (select 0 qtd, gedo_id, bupr_id, 2 - max(matches) mismatches from
        (select gedo_id, bupr_id, count(vaep_nr_posicao) matches, tipo from
            (select distinct gedo_id, bupr_id, vaep_nr_posicao, tipo from    
                (Select doador.gedo_id, preliminar.bupr_id, preliminar.vaep_nr_posicao, 
                        CASE preliminar.vaep_nr_posicao WHEN doador.vged_nr_posicao THEN 1 ELSE 2 END tipo
                 from valor_genotipo_expand_doador doador, valor_genotipo_expand_prelimin preliminar
                 where
                 -- preliminar.bupr_id = 3845 AND
                  preliminar.locu_id = 'DRB1' AND
                  doador.locu_id = preliminar.locu_id AND
                  doador.vged_tx_valor = preliminar.vaep_nr_valor ))
          group by gedo_id, bupr_id, tipo)
       group by 0, gedo_id, bupr_id) 
    union all
    (select 1 qtd, gedo_id, bupr_id, 2 - max(matches) mismatches from
        (select gedo_id, bupr_id, count(vaep_nr_posicao) matches, tipo from
            (select distinct gedo_id, bupr_id,  vaep_nr_posicao, tipo from    
                (Select doador.gedo_id, preliminar.bupr_id, preliminar.vaep_nr_posicao, CASE preliminar.vaep_nr_posicao WHEN doador.vged_nr_posicao THEN 1 ELSE 2 END tipo
                 from valor_genotipo_expand_doador doador, valor_genotipo_expand_prelimin preliminar
                 where
                --preliminar.bupr_id = 3845 AND
                  preliminar.locu_id = 'A' AND
                  doador.locu_id = preliminar.locu_id AND
                  doador.vged_tx_valor = preliminar.vaep_nr_valor))
          group by gedo_id, bupr_id, tipo)
       group by 1, gedo_id, bupr_id)
    union all
    (select 1 qtd, gedo_id, bupr_id, 2 - max(matches) mismatches from
        (select gedo_id, bupr_id, count(vaep_nr_posicao) matches, tipo from
            (select distinct gedo_id, bupr_id,  vaep_nr_posicao, tipo from    
                (Select doador.gedo_id, preliminar.bupr_id, preliminar.vaep_nr_posicao, CASE preliminar.vaep_nr_posicao WHEN doador.vged_nr_posicao THEN 1 ELSE 2 END tipo
                 from valor_genotipo_expand_doador doador, valor_genotipo_expand_prelimin preliminar
                 where
                  --preliminar.bupr_id = 3845 AND
                  preliminar.locu_id = 'B' AND
                  doador.locu_id = preliminar.locu_id AND
                  doador.vged_tx_valor = preliminar.vaep_nr_valor))
          group by gedo_id, bupr_id, tipo)
       group by 1, gedo_id, bupr_id)
)
group by gedo_id, bupr_id 
/* todos, doador e paciente devem ter A e B por isto verificamos se a quantidade de locus testados é igual a 2
   pode acontecer uma homozigose mas neste caso ao montar o genótipo extendido devemos replicar os valores do alelo que está preenchido */
having sum( mismatches ) <= 1 and sum(qtd)=2
;
