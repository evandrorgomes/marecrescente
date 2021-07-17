CREATE OR REPLACE VIEW "MODRED"."VW_MATCH" AS 
  select /*+ FIRST_ROWS */gedo_id, gepa_id, sum(qtd) qtdab, sum(mysmatches) mysmatches
from(
    (select 0 qtd, gedo_id, gepa_id, 2 - max(matches) mysmatches from
        (select gedo_id, gepa_id, count(vgep_nr_posicao) matches, tipo from
            (select distinct gedo_id, gepa_id,  vgep_nr_posicao, tipo from    
                (Select doador.gedo_id, paciente.gepa_id, paciente.vgep_nr_posicao, 
                        CASE paciente.vgep_nr_posicao WHEN doador.vged_nr_posicao THEN 1 ELSE 2 END tipo
                 from valor_genotipo_expand_doador doador, valor_genotipo_expand_paciente paciente
                 where
                 -- paciente.gepa_id = 3845 AND
                  paciente.locu_id = 'DQB1' AND
                  doador.locu_id = paciente.locu_id AND
                  doador.vged_tx_valor = paciente.vgep_tx_valor ))
          group by gedo_id, gepa_id, tipo)
       group by 0, gedo_id, gepa_id)
    /* precisa ser union all senão se tiver mysmatches nos 3 vai contar apenas um pois o union puro faz distinct */
    union all
    (select 0 qtd, gedo_id,  gepa_id, 2 - max(matches) mysmatches from
        (select gedo_id, gepa_id, count(vgep_nr_posicao) matches, tipo from
            (select distinct gedo_id, gepa_id,  vgep_nr_posicao, tipo from    
                (Select doador.gedo_id, paciente.gepa_id, paciente.vgep_nr_posicao, 
                        CASE paciente.vgep_nr_posicao WHEN doador.vged_nr_posicao THEN 1 ELSE 2 END tipo
                 from valor_genotipo_expand_doador doador, valor_genotipo_expand_paciente paciente
                 where
                 --paciente.gepa_id = 3845 AND
                  paciente.locu_id = 'C' AND
                  doador.locu_id = paciente.locu_id AND
                  doador.vged_tx_valor = paciente.vgep_tx_valor ))
          group by gedo_id, gepa_id, tipo)
       group by 0, gedo_id, gepa_id)
    union all
    (select 0 qtd, gedo_id, gepa_id, 2 - max(matches) mysmatches from
        (select gedo_id, gepa_id, count(vgep_nr_posicao) matches, tipo from
            (select distinct gedo_id, gepa_id, vgep_nr_posicao, tipo from    
                (Select doador.gedo_id, paciente.gepa_id, paciente.vgep_nr_posicao, 
                        CASE paciente.vgep_nr_posicao WHEN doador.vged_nr_posicao THEN 1 ELSE 2 END tipo
                 from valor_genotipo_expand_doador doador, valor_genotipo_expand_paciente paciente
                 where
                 -- paciente.gepa_id = 3845 AND
                  paciente.locu_id = 'DRB1' AND
                  doador.locu_id = paciente.locu_id AND
                  doador.vged_tx_valor = paciente.vgep_tx_valor ))
          group by gedo_id, gepa_id, tipo)
       group by 0, gedo_id, gepa_id) 
    union all
    (select 1 qtd, gedo_id, gepa_id, 2 - max(matches) mysmatches from
        (select gedo_id, gepa_id, count(vgep_nr_posicao) matches, tipo from
            (select distinct gedo_id, gepa_id,  vgep_nr_posicao, tipo from    
                (Select doador.gedo_id, paciente.gepa_id, paciente.vgep_nr_posicao, CASE paciente.vgep_nr_posicao WHEN doador.vged_nr_posicao THEN 1 ELSE 2 END tipo
                 from valor_genotipo_expand_doador doador, valor_genotipo_expand_paciente paciente
                 where
                --paciente.gepa_id = 3845 AND
                  paciente.locu_id = 'A' AND
                  doador.locu_id = paciente.locu_id AND
                  doador.vged_tx_valor = paciente.vgep_tx_valor))
          group by gedo_id, gepa_id, tipo)
       group by 1, gedo_id, gepa_id)
    union all
    (select 1 qtd, gedo_id, gepa_id, 2 - max(matches) mysmatches from
        (select gedo_id, gepa_id, count(vgep_nr_posicao) matches, tipo from
            (select distinct gedo_id, gepa_id,  vgep_nr_posicao, tipo from    
                (Select doador.gedo_id, paciente.gepa_id, paciente.vgep_nr_posicao, CASE paciente.vgep_nr_posicao WHEN doador.vged_nr_posicao THEN 1 ELSE 2 END tipo
                 from valor_genotipo_expand_doador doador, valor_genotipo_expand_paciente paciente
                 where
                  --paciente.gepa_id = 3845 AND
                  paciente.locu_id = 'B' AND
                  doador.locu_id = paciente.locu_id AND
                  doador.vged_tx_valor = paciente.vgep_tx_valor))
          group by gedo_id, gepa_id, tipo)
       group by 1, gedo_id, gepa_id)
)
group by gedo_id, gepa_id 
/* todos, doador e paciente devem ter A e B por isto verificamos se a quantidade de locus testados é igual a 2
   pode acontecer uma homozigose mas neste caso ao montar o genótipo extendido devemos replicar os valores do alelo que está preenchido */
having sum( mysmatches ) <= 1 and sum(qtd)=2
;
