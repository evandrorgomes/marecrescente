create or replace procedure proc_criar_match_fake (rmr in NUMBER, idDoador in NUMBER)

IS
idMatch NUMBER;
qtdLocus NUMBER;
tipoSolicitacao NUMBER;
idBusca NUMBER;
i   NUMBER;
idSolicitacao NUMBER;
idPedidoExame NUMBER;


begin
i:=0;
--DBMS_OUTPUT.PUT_LINE(rmr); 
--DBMS_OUTPUT.PUT_LINE(idDoador); 

    BEGIN
        select distinct ma.matc_id into idMatch from MODRED.match ma 
            inner join MODRED.busca b on ma.BUSC_ID = ma.BUSC_ID 
            inner join MODRED.status_busca sb on sb.stbu_id = b.stbu_id 
            where 
            ma.doad_id = idDoador and b.paci_nr_rmr = rmr 
            and sb.stbu_in_busca_ativa = 1
            and ma.matc_in_status = 1;

    EXCEPTION
      WHEN NO_DATA_FOUND THEN
        idBusca := NULL;
    END;

    --Encontra o id da busca
    select b.busc_id into idBusca from MODRED.busca b 
            inner join MODRED.status_busca sb on sb.stbu_id = b.stbu_id             
            where b.paci_nr_rmr = rmr
            and sb.stbu_in_busca_ativa = 1;

    --DBMS_OUTPUT.PUT_LINE(fase); 
    --DBMS_OUTPUT.PUT_LINE(idBusca); 

    --criar um match se não existir,
   if(idMatch IS null) then
    idMatch:=SQ_MATC_ID.nextval;
        --DBMS_OUTPUT.PUT_LINE('Inserir busca'); 
        INSERT INTO MODRED.MATCH(MATC_ID, MATC_IN_STATUS, BUSC_ID, DOAD_ID) 
        values (idMatch, 1, idBusca, idDoador);
    end if;

   --Cria a qualificação match a partir do genotipo do doador
    delete from MODRED.qualificacao_match where doad_id = idDoador;
  FOR rec IN (select vg.* from MODRED.VALOR_GENOTIPO_DOADOR vg WHERE vg.GEDO_ID IN (SELECT GEDO_ID FROM MODRED.GENOTIPO_DOADOR WHERE DOAD_ID = idDoador))
       LOOP
          i := i + 1;

          --0- para sorologia, 1- para antígeno, 2- NMDP, 3- Grupo G, 4-  Grupo P e 5 Alelo
          insert into MODRED.qualificacao_match (QUMA_ID, LOCU_ID, QUMA_TX_QUALIFICACAO, QUMA_NR_POSICAO, MATC_ID, DOAD_ID, QUMA_TX_GENOTIPO, QUMA_NR_TIPO) 
          values (SQ_QUMA_ID.nextval,rec.locu_id,'P' , REC.VAGD_NR_POSICAO_ALELO, idMatch, idDoador, REC.VAGD_TX_ALELO, REC.VAGD_NR_TIPO);
          --DBMS_OUTPUT.put_line ('Record ' || i || ' is emp ' || rec.locu_id);
       END LOOP;

end;