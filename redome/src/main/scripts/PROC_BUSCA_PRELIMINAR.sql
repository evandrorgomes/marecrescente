create or replace PROCEDURE PROC_BUSCA_PRELIMINAR(busca_id in NUMBER) iS 
doador_id NUMBER;
I NUMBER;
dataNascimento date;

BEGIN
   FOR I IN 1..10
   LOOP
     doador_id := SQ_DOAD_ID.nextval;
     dataNascimento := to_date('1970-01-01 00','yyyy-mm-dd hh24') + (round(dbms_random.value(1,10000)));
     proc_criar_doador(SQ_DOAD_NR_DMR.nextval,'12719386189','DOAD'||TO_CHAR(doador_id),'AAAAA','M',3,null,'B+',31,'RJ',1,sysdate,sysdate,dataNascimento,3,40,2,'Nome social',1,0,1,doador_id);
     commit;
     proc_criar_qual_match_pre(busca_id, doador_id, '1', false);
     commit;
   END LOOP;
   FOR I IN 1..5
   LOOP
     doador_id := SQ_DOAD_ID.nextval;
     dataNascimento := to_date('1970-01-01 00','yyyy-mm-dd hh24') + (round(dbms_random.value(1,10000)));
     proc_criar_doador(SQ_DOAD_NR_DMR.nextval,'12719386189','DOAD'||TO_CHAR(doador_id),'AAAAA','M',3,null,'B+',31,'RJ',1,sysdate,sysdate,dataNascimento,3,40,2,'Nome social',1,0,1,doador_id);
     commit;
     proc_criar_qual_match_pre(busca_id, doador_id, '2', false);
     commit;
   END LOOP;
   FOR I IN 1..2
   LOOP
     doador_id := SQ_DOAD_ID.nextval;
     dataNascimento := to_date('1970-01-01 00','yyyy-mm-dd hh24') + (round(dbms_random.value(1,10000)));
     proc_criar_doador(SQ_DOAD_NR_DMR.nextval,'12719386189','DOAD'||TO_CHAR(doador_id),'AAAAA','M',3,null,'B+',31,'RJ',1,sysdate,sysdate,dataNascimento,3,40,2,'Nome social',1,0,1,doador_id);
     commit;
     proc_criar_qual_match_pre(busca_id, doador_id, '3', false);
     commit;
   END LOOP;   
   
END PROC_BUSCA_PRELIMINAR;