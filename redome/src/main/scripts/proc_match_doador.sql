--------------------------------------------------------
--  DDL for Procedure PROC_MATCH_DOADOR
--------------------------------------------------------
set define off;

  CREATE OR REPLACE EDITIONABLE PROCEDURE "MODRED"."PROC_MATCH_DOADOR" (pNR_DMR NUMBER)
IS

vgepa_id NUMBER;
vgedo_id NUMBER;
vtotmatch NUMBER;
vposgen NUMBER;
vmismatch VARCHAR2(20);
vtotlocus NUMBER;
vvalor VARCHAR2(20);
vvalor1 VARCHAR2(20);
vvalor2 VARCHAR2(20);
vmatch VARCHAR2(5);
vmatc_id NUMBER;
vdoad_id NUMBER;
vbusc_id NUMBER;
vpaci_nr_rmr NUMBER;

TYPE genotipo_record IS RECORD (LOCU_ID VARCHAR2(10), NR_TIPO NUMBER, NR_POSICAO NUMBER, TX_QUALIFICACAO VARCHAR2(20), TX_GENOTIPO VARCHAR2(40));
TYPE genotipo_table IS TABLE OF genotipo_record INDEX BY BINARY_INTEGER;
genotipo_doador genotipo_table;

CURSOR c_match (pgedo_id NUMBER) 
IS 
SELECT * FROM vw_match_qualificacao WHERE gedo_id=pgedo_id order by gepa_id; 
r_match c_match%ROWTYPE;

BEGIN
     DBMS_OUTPUT.PUT_LINE('INÍCIO: '||TO_CHAR(SYSDATE,'HH24:MI:SS')); 
     
     SELECT doad_id INTO vdoad_id FROM doador WHERE doad_nr_dmr = pnr_dmr;
     
     SELECT gedo_id INTO vgedo_id FROM genotipo_doador WHERE doad_id = vdoad_id;
     
     OPEN c_match(vgedo_id);
     DBMS_OUTPUT.PUT_LINE('ABRIU QUERY: '||TO_CHAR(SYSDATE,'HH24:MI:SS')); 
     LOOP
        FETCH c_match INTO r_match;
        EXIT WHEN c_match%NOTFOUND;

        -- mudou paciente
        IF vgepa_id IS NULL or vgepa_id<>r_match.gepa_id THEN 
        
           IF vgepa_id IS NOT NULL THEN
              vmatch := LPAD(TO_CHAR(vtotmatch),2,'0')||'/'||LPAD(TO_CHAR(vtotlocus),2,'0');

              IF vmatch IN ('05/06','06/06','07/08','08/08','09/10','10/10') THEN         
                 SELECT sq_matc_id.NEXTVAL INTO vmatc_id FROM dual;
                 SELECT paci_nr_rmr INTO vpaci_nr_rmr FROM genotipo_paciente WHERE gepa_id=vgepa_id;
                 SELECT busc_id INTO vbusc_id FROM busca WHERE paci_nr_rmr=vpaci_nr_rmr and stbu_id=2;
                 INSERT INTO match(matc_id,matc_in_status,busc_id,doad_id,matc_tx_grade,matc_tx_mismatch,matc_dt_match) VALUES(vmatc_id,1,vbusc_id,vdoad_id,vmatch,vmismatch,sysdate); 
    
                 FOR I IN 1..vposgen LOOP
                    INSERT INTO qualificacao_match(quma_id,locu_id,quma_tx_qualificacao,quma_nr_posicao,matc_id,doad_id,quma_tx_genotipo,quma_nr_tipo)
                    VALUES (
                         sq_quma_id.NEXTVAL,
                         genotipo_doador(I).locu_id,
                         genotipo_doador(I).tx_qualificacao,
                         genotipo_doador(I).nr_posicao,
                         vmatc_id,
                         vdoad_id,
                         genotipo_doador(I).tx_genotipo,
                         genotipo_doador(I).nr_tipo);
                 END LOOP;
                 genotipo_doador.delete();           
              END IF;
           END IF;
           vtotmatch := 0;
           vposgen   := 0;
           vmismatch := '';
           vgedo_id := r_match.gedo_id;
        END IF;

        SELECT COUNT(DISTINCT doador.locu_id)*2 INTO vtotlocus 
        FROM valor_genotipo_expand_doador doador, valor_genotipo_expand_paciente paciente WHERE 
        doador.gedo_id   = r_match.gedo_id AND
        paciente.gepa_id = r_match.gepa_id AND
        doador.locu_id   = paciente.locu_id; 
               
        vvalor1 := NULL;
        vvalor2 := NULL;

        SELECT vagd_tx_alelo INTO vValor1 
          FROM valor_genotipo_doador WHERE 
          gedo_id = r_match.gedo_id AND 
          locu_id=r_match.locu_id AND 
          vagd_nr_posicao_alelo=1;
          
        SELECT vagd_tx_alelo INTO vValor2 
          FROM valor_genotipo_doador WHERE 
          gedo_id = r_match.gedo_id AND 
          locu_id=r_match.locu_id AND 
          vagd_nr_posicao_alelo=2;
    
        -- 8º Se o match foi cruzado ordena os valores do doador conforme o match
        IF r_match.troca=1 THEN
           vValor  := vValor1;
           vValor1 := vValor2;
           vValor2 := vValor;
        END IF;
                   
        IF r_match.pos_1_letra in ('M','L') OR r_match.pos_2_letra in ('M','L') THEN 
           IF vmismatch<>'' THEN
              vmismatch := vmismatch || ', ';
           END IF;
           vmismatch := vmismatch || r_match.locu_id;
        END IF;
      
        IF r_match.pos_1_tipo IS NOT NULL THEN   
           IF r_match.pos_1_letra IN ('P','A') THEN
              vtotmatch := vtotmatch + 1;
           END IF;
           vposgen := vposgen + 1;
           genotipo_doador(vposgen).LOCU_ID         := r_match.locu_id;
           genotipo_doador(vposgen).TX_QUALIFICACAO := r_match.pos_1_letra;
           genotipo_doador(vposgen).NR_POSICAO      := 1;
           genotipo_doador(vposgen).TX_GENOTIPO     := vvalor1;
           genotipo_doador(vposgen).NR_TIPO         := r_match.pos_1_tipo;                    
        END IF;
         
        IF r_match.pos_2_tipo IS NOT NULL THEN
           IF r_match.pos_2_letra IN ('P','A') THEN
              vtotmatch := vtotmatch + 1;
           END IF;
           vposgen := vposgen + 1;
           genotipo_doador(vposgen).LOCU_ID         := r_match.locu_id;
           genotipo_doador(vposgen).TX_QUALIFICACAO := r_match.pos_2_letra;
           genotipo_doador(vposgen).NR_POSICAO      := 2;
           genotipo_doador(vposgen).TX_GENOTIPO     := vvalor2;
           genotipo_doador(vposgen).NR_TIPO         := r_match.pos_2_tipo;                    
        END IF; 
       
     END LOOP;
     COMMIT;         
     DBMS_OUTPUT.PUT_LINE('FIM: '||TO_CHAR(SYSDATE,'HH24:MI:SS')); 
 EXCEPTION
    WHEN OTHERS THEN
       DBMS_OUTPUT.PUT_LINE('DOADOR GENÓTIPO: '||TO_CHAR(r_match.gedo_id)); 
       DBMS_OUTPUT.PUT_LINE('FIM: '||TO_CHAR(SYSDATE,'HH24:MI:SS')); 
END PROC_MATCH_DOADOR;

/
