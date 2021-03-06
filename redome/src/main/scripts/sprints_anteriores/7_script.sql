ALTER TABLE MODRED.PACIENTE 
ADD (PACI_TX_NOME_FONETIZADO VARCHAR2(255) );

COMMENT ON COLUMN MODRED.PACIENTE.PACI_TX_NOME_FONETIZADO IS 'Nome do paciente fonetizado';

--Rodar o teste " PacienteUpdateNomeFonetizadoTest " antes de colocar o campo como nulo 

ALTER TABLE MODRED.PACIENTE  
MODIFY (PACI_TX_NOME_FONETIZADO NOT NULL);


insert into motivo (MOTI_ID,MOTI_TX_DESCRICAO) VALUES (2,'ACOMPANHAMENTO TRIMESTRAL');
insert into motivo (MOTI_ID,MOTI_TX_DESCRICAO) VALUES (3,'MUDANÇA DE TRATAMENTO');
insert into motivo (MOTI_ID,MOTI_TX_DESCRICAO) VALUES (4,'ALTERAÇÃO DO ESTADO DO PACIENTE');
commit;