create or replace procedure        proc_deletar_paciente_teste 

IS

        rmr number := 0;
        idBusca number := 0;
        idMatch number := 0;
        idProcessoAvaliacaoBusca number := 0;
        idProcessoBusca number := 0;
        idProcessoConfExame number := 0;
        idExame number := 0;
        idGenotipoPaci number := 0;
        
        idDoador number := 0;
        idExameDoador number := 0;
        idGenotipoDoador number := 0;
        
        
        idCordao number := 0;
        idExameCordao number := 0;
        idGenotipoCordao number := 0;
        idMatchCordao number := 0;
        
    begin
    
        begin
        
           select paci_nr_rmr into rmr from paciente where paci_tx_nome = 'João dos Testes';
           dbms_output.put_line('rmr!');
           dbms_output.put_line(rmr);
       EXCEPTION
        WHEN NO_DATA_FOUND THEN
        dbms_output.put_line('nao encontrou rmr!');
            NULL;
        end;
        
        begin
           select busc_id into idBusca from BUSCA where paci_nr_rmr = rmr;
           dbms_output.put_line('idBusca!');
           dbms_output.put_line(idBusca);
        EXCEPTION
        WHEN NO_DATA_FOUND THEN
        dbms_output.put_line('nao encontrou algumaidBusca!');
            NULL;
        end;
        
         begin
           select doad_id into idDoador from doador where doad_tx_id_registro = 'IOSDJJ9218jddjasa82' and doad_nr_grid = '99999993828';
           dbms_output.put_line('idDoador!');
           dbms_output.put_line(idDoador);
        EXCEPTION
        WHEN NO_DATA_FOUND THEN
        dbms_output.put_line('nao encontrou idDoador!');
            NULL;
        end;
        
        
       begin
           select matc_id into idMatch from match where busc_id = idBusca and doad_id = idDoador;
           dbms_output.put_line('idMatch!');
           dbms_output.put_line(idMatch);
       EXCEPTION
        WHEN NO_DATA_FOUND THEN
            dbms_output.put_line('nao encontrou idMatch!');
            NULL;
        end;
        
        begin
           select proc_id into idProcessoAvaliacaoBusca from PROCESSO where paci_nr_rmr = rmr and proc_nr_tipo = 10;
           dbms_output.put_line('idProcessoAvaliacaoBusca!');
           dbms_output.put_line(idProcessoAvaliacaoBusca);
        EXCEPTION
        WHEN NO_DATA_FOUND THEN
        dbms_output.put_line('nao encontrou idProcessoAvaliacaoBusca!');
            NULL;
        end;
        
       begin
        select proc_id into idProcessoBusca from PROCESSO where paci_nr_rmr = rmr and proc_nr_tipo = 5;
       EXCEPTION
        WHEN NO_DATA_FOUND THEN
            dbms_output.put_line('nao encontrou idProcessoBusca!');
            NULL;
        end;
        
        begin
           select proc_id into idProcessoConfExame from PROCESSO where paci_nr_rmr = rmr and proc_nr_tipo = 15;
           dbms_output.put_line('idProcessoConfExame!');
           dbms_output.put_line(idProcessoConfExame);
        EXCEPTION
        WHEN NO_DATA_FOUND THEN
        dbms_output.put_line('nao encontrou idProcessoConfExame!');
            NULL;
        end;
       
       begin
           SELECT exam_id INTO idExame from EXAME where paci_nr_rmr = rmr;  
           dbms_output.put_line('exameId!');
           dbms_output.put_line(idExame);
        EXCEPTION
        WHEN NO_DATA_FOUND THEN
        dbms_output.put_line('nao encontrou idExame!');
            NULL;
        end;
       
       
      
        
        
         begin
           SELECT exam_id INTO idExameDoador from EXAME where doad_id_internacional = idDoador;
           dbms_output.put_line('idExameDoador!');
           dbms_output.put_line(idExameDoador);
        EXCEPTION
        WHEN NO_DATA_FOUND THEN
        dbms_output.put_line('nao encontrou idExameDoador!');
            NULL;
        end;
        
        begin
            select gedo_id into idGenotipoDoador from GENOTIPO_DOADOR where doad_id = idDoador;
        EXCEPTION
        WHEN NO_DATA_FOUND THEN
        dbms_output.put_line('nao encontrou idGenotipoDoador!');
            NULL;
        end;
        
        begin
            select gepa_id into idGenotipoPaci from GENOTIPO_PACIENTE where paci_nr_rmr = rmr;
            dbms_output.put_line('idGenotipoPaci!');
           dbms_output.put_line(idGenotipoPaci);
        EXCEPTION
        WHEN NO_DATA_FOUND THEN
        dbms_output.put_line('nao encontrou idGenotipoPaci!');
            NULL;
        end;
        
        begin
           select doad_id into idCordao from doador where doad_tx_id_registro = 'aaaaabbbbccccjasa82' and doad_nr_grid = '999113321828' ;
           dbms_output.put_line('idCordao!');
           dbms_output.put_line(idCordao);
        EXCEPTION
        WHEN NO_DATA_FOUND THEN
        dbms_output.put_line('nao encontrou idCordao!');
            NULL;
        end;
        
        begin
           SELECT exam_id INTO idExameCordao from EXAME where doad_id_cordao_internacional = idCordao;
           dbms_output.put_line('idExameCordao!');
           dbms_output.put_line(idExameCordao);
        EXCEPTION
        WHEN NO_DATA_FOUND THEN
        dbms_output.put_line('nao encontrou idExameCordao!');
            NULL;
        end;
        
        begin
            select gedo_id into idGenotipoCordao from GENOTIPO_DOADOR where doad_id = idCordao;
        EXCEPTION
        WHEN NO_DATA_FOUND THEN
        dbms_output.put_line('nao encontrou idGenotipoCordao!');
            NULL;
        end;
        
        begin
           select matc_id into idMatchCordao from match where busc_id = idBusca and doad_id = idCordao;
           dbms_output.put_line('idMatchCordao!');
           dbms_output.put_line(idMatchCordao);
       EXCEPTION
        WHEN NO_DATA_FOUND THEN
            dbms_output.put_line('nao encontrou idMatchCordao!');
            NULL;
        end;
        
    delete from ENDERECO_CONTATO where paci_nr_rmr = rmr;
    delete from CONTATO_TELEFONICO where paci_nr_rmr = rmr;
    delete from AVALIACAO where paci_nr_rmr = rmr;
    
    delete from RESERVA_DOADOR_INTERNACIONAL where busc_id = idBusca;
    delete from qualificacao_match where matc_id = idMatch;
    delete from qualificacao_match where matc_id = idMatchCordao;
    
    delete from MATCH where busc_id = idBusca;
    
    delete from BUSCA where paci_nr_rmr = rmr;  
    delete from LOG_EVOLUCAO where paci_nr_rmr = rmr;  
    
    delete from tarefa where proc_id = idProcessoAvaliacaoBusca;
    delete from tarefa where proc_id = idProcessoBusca;
    delete from tarefa where proc_id = idProcessoConfExame;
    
    delete from PROCESSO where paci_nr_rmr = rmr;      
    delete from DIAGNOSTICO where paci_nr_rmr = rmr;  
    delete from EVOLUCAO where paci_nr_rmr = rmr;  
    
      delete from VALOR_GENOTIPO_EXPAND_PACIENTE where gepa_id = idGenotipoPaci;
    delete from VALOR_GENOTIPO_BUSCA_PACIENTE where gepa_id = idGenotipoPaci;
    delete from VALOR_GENOTIPO_PACIENTE where gepa_id = idGenotipoPaci;
    delete from GENOTIPO_PACIENTE where gepa_id = idGenotipoPaci;
    
    
    delete from LOCUS_EXAME where exam_id = idExame;  
    delete from METODOLOGIA_EXAME where exam_id = idExame;  
    delete from ARQUIVO_EXAME where exam_id = idExame;  
    delete from EXAME where paci_nr_rmr = rmr;  
    
  
    
    delete from NOTIFICACAO where paci_nr_rmr = rmr;  
    delete from paciente where paci_nr_rmr = rmr;
    dbms_output.put_line('deletou paciente!');
    
    delete from LOCUS_EXAME where exam_id = idExameDoador;  
    delete from exame where doad_id_internacional = idDoador;
    delete from VALOR_GENOTIPO_EXPAND_DOADOR where gedo_id = idGenotipoDoador;
    delete from VALOR_GENOTIPO_BUSCA_DOADOR where gedo_id = idGenotipoDoador;
    delete from VALOR_GENOTIPO_DOADOR where gedo_id = idGenotipoDoador;
    delete from GENOTIPO_DOADOR where gedo_id = idGenotipoDoador;
    
    
    delete from doador where doad_id = idDoador;
    dbms_output.put_line('deletou doador!');
    
    
    
    delete from LOCUS_EXAME where exam_id = idExameCordao;  
    delete from exame where doad_id_cordao_internacional = idCordao;
    delete from VALOR_GENOTIPO_EXPAND_DOADOR where gedo_id = idGenotipoCordao;
    delete from VALOR_GENOTIPO_BUSCA_DOADOR where gedo_id = idGenotipoCordao;
    delete from VALOR_GENOTIPO_DOADOR where gedo_id = idGenotipoCordao;
    delete from GENOTIPO_DOADOR where gedo_id = idGenotipoCordao;
    
    
    delete from doador where doad_id = idCordao;
    dbms_output.put_line('deletou cordao!');
    
    commit;
    dbms_output.put_line('comitou!');
 end;