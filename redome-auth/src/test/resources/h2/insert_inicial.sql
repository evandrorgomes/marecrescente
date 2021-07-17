SET MODE ORACLE;
Insert into SISTEMA (SIST_ID,SIST_TX_NOME,SIST_IN_DISPONIVEL_REDOME) values ('1','Redome','1');

Insert into PERFIL (PERF_ID,PERF_TX_DESCRICAO,SIST_ID) values ('19','ADMIN','1');
Insert into PERFIL (PERF_ID,PERF_TX_DESCRICAO,SIST_ID) values ('17','TRANSPORTADORA','5');

insert into BANCO_SANGUE_CORDAO (BASC_ID, BASC_NR_ID_BRASILCORD, BASC_TX_CONTATO, BASC_TX_ENDERECO, BASC_TX_NOME, BASC_TX_SIGLA)
values ('1', '1', 'Flávio', 'PRAÇA CRUZ VERMELHA', 'INCA', 'INCA');
commit;


INSERT INTO USUARIO (USUA_ID,USUA_TX_NOME,USUA_TX_USERNAME,USUA_TX_PASSWORD,USUA_IN_ATIVO,TRAN_ID,LABO_ID,USUA_TX_EMAIL) VALUES ('19', 'ADMIN', 'ADMIN', '$2a$11$KMAfznbkZhx9mvESGy3.GewPWbSGQLVTnj5O0m7cIo5NrucDtcXT.', '1', '', '', 'admin@mail.com');
INSERT INTO USUARIO (USUA_ID,USUA_TX_NOME,USUA_TX_USERNAME,USUA_TX_PASSWORD,USUA_IN_ATIVO,TRAN_ID,LABO_ID,USUA_TX_EMAIL) VALUES ('51', 'COURIER', 'COURIER', '$2a$11$KMAfznbkZhx9mvESGy3.GewPWbSGQLVTnj5O0m7cIo5NrucDtcXT.', '1', '1', '', 'courier@mail.com');

Insert into USUARIO_PERFIL (USUA_ID,PERF_ID) values ('19','19');
Insert into USUARIO_PERFIL (USUA_ID,PERF_ID) values ('51','17');

Insert into PERMISSAO (RECU_ID,PERF_ID,PERM_IN_COM_RESTRICAO) values ('198','19','0');

commit;


