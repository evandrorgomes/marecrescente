  CREATE OR REPLACE VIEW "MODRED"."VW_MATCH_QUALIFICACAO"  AS 
  select gepa_id, gedo_id, locu_id,
                                case 
                                when max(nvl(pos_1_igual,0))+max(nvl(pos_2_igual,0))=0 then 1
                                else 0
                                end troca,
                                max(pos_1_tipo)  pos_1_tipo,
                                max(pos_2_tipo)  pos_2_tipo,
                                decode(MAX(pos_1_letra),'0','M','1','L','2','P','3','A','') pos_1_letra,
                                decode(MAX(pos_2_letra),'0','M','1','L','2','P','3','A','') pos_2_letra
                                from (
                                select gepa_id, gedo_id, locu_id
                                , decode(posicao, 1, posicao_igual, null) pos_1_igual
                                , decode(posicao, 2, posicao_igual, null) pos_2_igual
                                , decode(posicao, 1, tipo, null) pos_1_tipo
                                , decode(posicao, 2, tipo, null) pos_2_tipo
                                , decode(posicao, 1, letra, null) pos_1_letra
                                , decode(posicao, 2, letra, null) pos_2_letra
                                from (
                                select paciente.gepa_id, doador.gedo_id, doador.locu_id, doador.vgbd_nr_tipo tipo, paciente.vgbp_nr_posicao posicao,
                                case 
                                  paciente.vgbp_nr_posicao 
                                  when doador.vgbd_nr_posicao then 1 
                                  else 0 
                                end posicao_igual,
                                case 
                                      when (doador.vgbd_nr_tipo<3 or paciente.vgbp_nr_tipo<3) and 
                                           ( doador.vgbd_TX_VALOR like paciente.vgbp_TX_VALOR||'%' or
                                             paciente.vgbp_TX_VALOR like doador.vgbd_TX_VALOR||'%'
                                            ) then '2'
                                      when (doador.vgbd_nr_tipo>=3 or paciente.vgbp_nr_tipo>=3) and 
                                           ( doador.vgbd_TX_VALOR like paciente.vgbp_TX_VALOR||'%' or
                                             paciente.vgbp_TX_VALOR like doador.vgbd_TX_VALOR||'%'
                                            ) then '3'      
                                      when (doador.vgbd_nr_tipo<3 or paciente.vgbp_nr_tipo<3) and 
                                           ( doador.vgbd_TX_VALOR not like paciente.vgbp_TX_VALOR||'%' and
                                             paciente.vgbp_TX_VALOR not like doador.vgbd_TX_VALOR||'%'
                                            ) then '0'
                                      when (doador.vgbd_nr_tipo>=3 or paciente.vgbp_nr_tipo>=3) and 
                                           ( doador.vgbd_TX_VALOR not like paciente.vgbp_TX_VALOR||'%' and
                                             paciente.vgbp_TX_VALOR not like doador.vgbd_TX_VALOR||'%'
                                            ) then '1'                                                
                                      else null
                                    end letra
                                from 
                                valor_genotipo_busca_doador doador, valor_genotipo_busca_paciente paciente, vw_match match
                                where 
                                doador.gedo_id = match.gedo_id and
                                paciente.gepa_id = match.gepa_id and
                                doador.locu_id=paciente.locu_id))          
                                group by gepa_id, gedo_id, locu_id  
                                order by 1,2
;
