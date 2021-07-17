create or replace procedure proc_criar_qualificacoes_match (rmr in NUMBER, idDoador in NUMBER, fase in VARCHAR2, com_mismatch in BOOLEAN)


IS
matchId NUMBER;


begin
    select m.matc_id into matchId from MODRED.paciente paci inner join MODRED.busca b on paci.paci_nr_rmr = b.paci_nr_rmr inner join match m on m.busc_id = b.busc_id where paci.PACI_NR_RMR = rmr and m.doad_id = idDoador;


    if (fase = '1') then
        INSERT INTO MODRED.QUALIFICACAO_MATCH (QUMA_ID, MATC_ID, LOCU_ID, QUMA_TX_QUALIFICACAO, QUMA_NR_POSICAO, DOAD_ID, QUMA_TX_GENOTIPO, QUMA_NR_TIPO) VALUES (modred.SQ_QUMA_ID.NEXTVAL, matchId, 'A', 'A', 1, idDoador, '01:01', 5); 
        if(com_mismatch) then
            INSERT INTO MODRED.QUALIFICACAO_MATCH (QUMA_ID, MATC_ID, LOCU_ID, QUMA_TX_QUALIFICACAO, QUMA_NR_POSICAO, DOAD_ID, QUMA_TX_GENOTIPO, QUMA_NR_TIPO) VALUES (modred.SQ_QUMA_ID.NEXTVAL, matchId, 'A', 'M', 2, idDoador, '01:01:01', 5); 
        else
            INSERT INTO MODRED.QUALIFICACAO_MATCH (QUMA_ID, MATC_ID, LOCU_ID, QUMA_TX_QUALIFICACAO, QUMA_NR_POSICAO, DOAD_ID, QUMA_TX_GENOTIPO, QUMA_NR_TIPO) VALUES (modred.SQ_QUMA_ID.NEXTVAL, matchId, 'A', 'P', 2, idDoador, '01:01:01:01', 5); 
        end if;

        INSERT INTO MODRED.QUALIFICACAO_MATCH (QUMA_ID, MATC_ID, LOCU_ID, QUMA_TX_QUALIFICACAO, QUMA_NR_POSICAO, DOAD_ID, QUMA_TX_GENOTIPO, QUMA_NR_TIPO) VALUES (modred.SQ_QUMA_ID.NEXTVAL, matchId, 'B', 'L', 1, idDoador, '38:NOVO', 5); 
        INSERT INTO MODRED.QUALIFICACAO_MATCH (QUMA_ID, MATC_ID, LOCU_ID, QUMA_TX_QUALIFICACAO, QUMA_NR_POSICAO, DOAD_ID, QUMA_TX_GENOTIPO, QUMA_NR_TIPO) VALUES (modred.SQ_QUMA_ID.NEXTVAL, matchId, 'B', 'P', 2, idDoador, '18:NOVO', 5); 
        INSERT INTO MODRED.QUALIFICACAO_MATCH (QUMA_ID, MATC_ID, LOCU_ID, QUMA_TX_QUALIFICACAO, QUMA_NR_POSICAO, DOAD_ID, QUMA_TX_GENOTIPO, QUMA_NR_TIPO) VALUES (modred.SQ_QUMA_ID.NEXTVAL, matchId, 'DRB1', 'A', 1, idDoador, '01:01', 5); 
        INSERT INTO MODRED.QUALIFICACAO_MATCH (QUMA_ID, MATC_ID, LOCU_ID, QUMA_TX_QUALIFICACAO, QUMA_NR_POSICAO, DOAD_ID, QUMA_TX_GENOTIPO, QUMA_NR_TIPO) VALUES (modred.SQ_QUMA_ID.NEXTVAL, matchId, 'DRB1', 'L', 2, idDoador, '01:01:01', 5); 
        update match set MATC_TX_SITUACAO = 'F1' where matc_id = matchId;
    elsif (fase = '2' OR fase = '3' or fase='D') then
        INSERT INTO MODRED.QUALIFICACAO_MATCH (QUMA_ID, MATC_ID, LOCU_ID, QUMA_TX_QUALIFICACAO, QUMA_NR_POSICAO, DOAD_ID, QUMA_TX_GENOTIPO, QUMA_NR_TIPO) VALUES (modred.SQ_QUMA_ID.NEXTVAL, matchId, 'A', 'A', 1, idDoador, '01:01:01:02N', 5); 
        if(com_mismatch) then
            INSERT INTO MODRED.QUALIFICACAO_MATCH (QUMA_ID, MATC_ID, LOCU_ID, QUMA_TX_QUALIFICACAO, QUMA_NR_POSICAO, DOAD_ID, QUMA_TX_GENOTIPO, QUMA_NR_TIPO) VALUES (modred.SQ_QUMA_ID.NEXTVAL, matchId, 'A', 'M', 2, idDoador, '01:01:01:03', 5); 
        else
            INSERT INTO MODRED.QUALIFICACAO_MATCH (QUMA_ID, MATC_ID, LOCU_ID, QUMA_TX_QUALIFICACAO, QUMA_NR_POSICAO, DOAD_ID, QUMA_TX_GENOTIPO, QUMA_NR_TIPO) VALUES (modred.SQ_QUMA_ID.NEXTVAL, matchId, 'A', 'P', 2, idDoador, '01:01:01:04', 5); 
        end if;

        INSERT INTO MODRED.QUALIFICACAO_MATCH (QUMA_ID, MATC_ID, LOCU_ID, QUMA_TX_QUALIFICACAO, QUMA_NR_POSICAO, DOAD_ID, QUMA_TX_GENOTIPO, QUMA_NR_TIPO) VALUES (modred.SQ_QUMA_ID.NEXTVAL, matchId, 'B', 'L', 1, idDoador, '35:NOVO', 5); 
        INSERT INTO MODRED.QUALIFICACAO_MATCH (QUMA_ID, MATC_ID, LOCU_ID, QUMA_TX_QUALIFICACAO, QUMA_NR_POSICAO, DOAD_ID, QUMA_TX_GENOTIPO, QUMA_NR_TIPO) VALUES (modred.SQ_QUMA_ID.NEXTVAL, matchId, 'B', 'P', 2, idDoador, '46:NOVO', 5); 
        INSERT INTO MODRED.QUALIFICACAO_MATCH (QUMA_ID, MATC_ID, LOCU_ID, QUMA_TX_QUALIFICACAO, QUMA_NR_POSICAO, DOAD_ID, QUMA_TX_GENOTIPO, QUMA_NR_TIPO) VALUES (modred.SQ_QUMA_ID.NEXTVAL, matchId, 'DRB1', 'A', 1, idDoador, '01:01:01G', 3); 
        INSERT INTO MODRED.QUALIFICACAO_MATCH (QUMA_ID, MATC_ID, LOCU_ID, QUMA_TX_QUALIFICACAO, QUMA_NR_POSICAO, DOAD_ID, QUMA_TX_GENOTIPO, QUMA_NR_TIPO) VALUES (modred.SQ_QUMA_ID.NEXTVAL, matchId, 'DRB1', 'L', 2, idDoador, '01:01:02', 5); 
        INSERT INTO MODRED.QUALIFICACAO_MATCH (QUMA_ID, MATC_ID, LOCU_ID, QUMA_TX_QUALIFICACAO, QUMA_NR_POSICAO, DOAD_ID, QUMA_TX_GENOTIPO, QUMA_NR_TIPO) VALUES (modred.SQ_QUMA_ID.NEXTVAL, matchId, 'C', 'A', 1, idDoador, '01:02', 5); 
        INSERT INTO MODRED.QUALIFICACAO_MATCH (QUMA_ID, MATC_ID, LOCU_ID, QUMA_TX_QUALIFICACAO, QUMA_NR_POSICAO, DOAD_ID, QUMA_TX_GENOTIPO, QUMA_NR_TIPO) VALUES (modred.SQ_QUMA_ID.NEXTVAL, matchId, 'C', 'L', 2, idDoador, '01:02:01', 5); 
        INSERT INTO MODRED.QUALIFICACAO_MATCH (QUMA_ID, MATC_ID, LOCU_ID, QUMA_TX_QUALIFICACAO, QUMA_NR_POSICAO, DOAD_ID, QUMA_TX_GENOTIPO, QUMA_NR_TIPO) VALUES (modred.SQ_QUMA_ID.NEXTVAL, matchId, 'DRB3', 'A', 1, idDoador, '01:02', 5); 
        INSERT INTO MODRED.QUALIFICACAO_MATCH (QUMA_ID, MATC_ID, LOCU_ID, QUMA_TX_QUALIFICACAO, QUMA_NR_POSICAO, DOAD_ID, QUMA_TX_GENOTIPO, QUMA_NR_TIPO) VALUES (modred.SQ_QUMA_ID.NEXTVAL, matchId, 'DRB3', 'L', 2, idDoador, '01:01:02:02', 5); 
        if(fase = '2') then
            update match set MATC_TX_SITUACAO = 'F2' where matc_id = matchId;
        else 
            if(fase = '3') then
                update match set MATC_TX_SITUACAO = 'F3' where matc_id = matchId;
            else 
                update match set MATC_TX_SITUACAO = 'D' where matc_id = matchId;
            end if;
        end if;
    end if;
    commit;
end;
