INSERT INTO MODRED.ENDERECO_CONTATO (enco_id, enco_id_pais, enco_tx_tipo_logradouro, enco_tx_nome, enco_nr_numero, enco_tx_bairro, enco_tx_municipio, enco_tx_sigla_uf, enco_cep, cetr_id) VALUES (MODRED.SQ_ENCO_ID.nextval,'31','Rua','Santa Marcelina','177','Itaquera','São Paulo','SP','08270070',13);
INSERT INTO MODRED.AUDITORIA(AUDI_ID,AUDI_DT_DATA,AUDI_TX_USUARIO) values(MODRED.SQ_AUDI_ID.NEXTVAL,sysdate,'SISTEMA');
INSERT INTO MODRED.ENDERECO_CONTATO_AUD (audi_id, enco_id, audi_tx_tipo, enco_tx_bairro,
    enco_cep, enco_tx_complemento, enco_tx_endereco_estrangeiro, enco_tx_municipio, enco_tx_nome, enco_nr_numero, enco_tx_tipo_logradouro, enco_tx_sigla_uf, enco_id_pais, enco_in_excluido,
    paci_nr_rmr, enco_in_principal, enco_in_correspondencia, doad_nr_dmr, heen_id, cetr_id ) SELECT (SELECT MAX(AUDI_ID) FROM MODRED.AUDITORIA),
    enco_id, '0', enco_tx_bairro, enco_cep,enco_tx_complemento, enco_tx_endereco_estrangeiro, enco_tx_municipio, enco_tx_nome, enco_nr_numero, enco_tx_tipo_logradouro, enco_tx_sigla_uf, enco_id_pais, enco_in_excluido,
    paci_nr_rmr, enco_in_principal, enco_in_correspondencia, doad_nr_dmr, heen_id, cetr_id FROM MODRED.ENDERECO_CONTATO where enco_id = (select max(ec.enco_id) from MODRED.ENDERECO_CONTATO ec);

INSERT INTO MODRED.ENDERECO_CONTATO (enco_id, enco_id_pais, enco_tx_tipo_logradouro, enco_tx_nome, enco_nr_numero, enco_tx_bairro, enco_tx_municipio, enco_tx_sigla_uf, enco_cep, cetr_id) VALUES (MODRED.SQ_ENCO_ID.nextval,'31','Rua','Dr.Gabriel Porto','1270','Barão Geraldo','Campinas','SP','13083210',9);
INSERT INTO MODRED.AUDITORIA(AUDI_ID,AUDI_DT_DATA,AUDI_TX_USUARIO) values(MODRED.SQ_AUDI_ID.NEXTVAL,sysdate,'SISTEMA');
INSERT INTO MODRED.ENDERECO_CONTATO_AUD (audi_id, enco_id, audi_tx_tipo, enco_tx_bairro,
    enco_cep, enco_tx_complemento, enco_tx_endereco_estrangeiro, enco_tx_municipio, enco_tx_nome, enco_nr_numero, enco_tx_tipo_logradouro, enco_tx_sigla_uf, enco_id_pais, enco_in_excluido,
    paci_nr_rmr, enco_in_principal, enco_in_correspondencia, doad_nr_dmr, heen_id, cetr_id ) SELECT (SELECT MAX(AUDI_ID) FROM MODRED.AUDITORIA),
    enco_id, '0', enco_tx_bairro, enco_cep,enco_tx_complemento, enco_tx_endereco_estrangeiro, enco_tx_municipio, enco_tx_nome, enco_nr_numero, enco_tx_tipo_logradouro, enco_tx_sigla_uf, enco_id_pais, enco_in_excluido,
    paci_nr_rmr, enco_in_principal, enco_in_correspondencia, doad_nr_dmr, heen_id, cetr_id FROM MODRED.ENDERECO_CONTATO where enco_id = (select max(ec.enco_id) from MODRED.ENDERECO_CONTATO ec);

INSERT INTO MODRED.ENDERECO_CONTATO (enco_id, enco_id_pais, enco_tx_tipo_logradouro, enco_tx_nome, enco_nr_numero, enco_tx_bairro, enco_tx_municipio, enco_tx_sigla_uf, enco_cep, cetr_id) VALUES (MODRED.SQ_ENCO_ID.nextval,'31','Rua','Antenor Duarte Vilela','1331','Paulo Prata','Barretos','SP','14787400',21);
INSERT INTO MODRED.AUDITORIA(AUDI_ID,AUDI_DT_DATA,AUDI_TX_USUARIO) values(MODRED.SQ_AUDI_ID.NEXTVAL,sysdate,'SISTEMA');
INSERT INTO MODRED.ENDERECO_CONTATO_AUD (audi_id, enco_id, audi_tx_tipo, enco_tx_bairro,
    enco_cep, enco_tx_complemento, enco_tx_endereco_estrangeiro, enco_tx_municipio, enco_tx_nome, enco_nr_numero, enco_tx_tipo_logradouro, enco_tx_sigla_uf, enco_id_pais, enco_in_excluido,
    paci_nr_rmr, enco_in_principal, enco_in_correspondencia, doad_nr_dmr, heen_id, cetr_id ) SELECT (SELECT MAX(AUDI_ID) FROM MODRED.AUDITORIA),
    enco_id, '0', enco_tx_bairro, enco_cep,enco_tx_complemento, enco_tx_endereco_estrangeiro, enco_tx_municipio, enco_tx_nome, enco_nr_numero, enco_tx_tipo_logradouro, enco_tx_sigla_uf, enco_id_pais, enco_in_excluido,
    paci_nr_rmr, enco_in_principal, enco_in_correspondencia, doad_nr_dmr, heen_id, cetr_id FROM MODRED.ENDERECO_CONTATO where enco_id = (select max(ec.enco_id) from MODRED.ENDERECO_CONTATO ec);

INSERT INTO MODRED.ENDERECO_CONTATO (enco_id, enco_id_pais, enco_tx_tipo_logradouro, enco_tx_nome, enco_nr_numero, enco_tx_bairro, enco_tx_municipio, enco_tx_sigla_uf, enco_cep, cetr_id) VALUES (MODRED.SQ_ENCO_ID.nextval,'31','Rua','Pedro de Toledo','572','Vila Clementino','São Paulo','SP','04023062',11);
INSERT INTO MODRED.AUDITORIA(AUDI_ID,AUDI_DT_DATA,AUDI_TX_USUARIO) values(MODRED.SQ_AUDI_ID.NEXTVAL,sysdate,'SISTEMA');
INSERT INTO MODRED.ENDERECO_CONTATO_AUD (audi_id, enco_id, audi_tx_tipo, enco_tx_bairro,
    enco_cep, enco_tx_complemento, enco_tx_endereco_estrangeiro, enco_tx_municipio, enco_tx_nome, enco_nr_numero, enco_tx_tipo_logradouro, enco_tx_sigla_uf, enco_id_pais, enco_in_excluido,
    paci_nr_rmr, enco_in_principal, enco_in_correspondencia, doad_nr_dmr, heen_id, cetr_id ) SELECT (SELECT MAX(AUDI_ID) FROM MODRED.AUDITORIA),
    enco_id, '0', enco_tx_bairro, enco_cep,enco_tx_complemento, enco_tx_endereco_estrangeiro, enco_tx_municipio, enco_tx_nome, enco_nr_numero, enco_tx_tipo_logradouro, enco_tx_sigla_uf, enco_id_pais, enco_in_excluido,
    paci_nr_rmr, enco_in_principal, enco_in_correspondencia, doad_nr_dmr, heen_id, cetr_id FROM MODRED.ENDERECO_CONTATO where enco_id = (select max(ec.enco_id) from MODRED.ENDERECO_CONTATO ec);

INSERT INTO MODRED.ENDERECO_CONTATO (enco_id, enco_id_pais, enco_tx_tipo_logradouro, enco_tx_nome, enco_nr_numero, enco_tx_bairro, enco_tx_municipio, enco_tx_sigla_uf, enco_cep, cetr_id) VALUES (MODRED.SQ_ENCO_ID.nextval,'31','Rua','Professor Antonio Prudente','211',' Liberdade','São Paulo','SP','01509900',16);
INSERT INTO MODRED.AUDITORIA(AUDI_ID,AUDI_DT_DATA,AUDI_TX_USUARIO) values(MODRED.SQ_AUDI_ID.NEXTVAL,sysdate,'SISTEMA');
INSERT INTO MODRED.ENDERECO_CONTATO_AUD (audi_id, enco_id, audi_tx_tipo, enco_tx_bairro,
    enco_cep, enco_tx_complemento, enco_tx_endereco_estrangeiro, enco_tx_municipio, enco_tx_nome, enco_nr_numero, enco_tx_tipo_logradouro, enco_tx_sigla_uf, enco_id_pais, enco_in_excluido,
    paci_nr_rmr, enco_in_principal, enco_in_correspondencia, doad_nr_dmr, heen_id, cetr_id ) SELECT (SELECT MAX(AUDI_ID) FROM MODRED.AUDITORIA),
    enco_id, '0', enco_tx_bairro, enco_cep,enco_tx_complemento, enco_tx_endereco_estrangeiro, enco_tx_municipio, enco_tx_nome, enco_nr_numero, enco_tx_tipo_logradouro, enco_tx_sigla_uf, enco_id_pais, enco_in_excluido,
    paci_nr_rmr, enco_in_principal, enco_in_correspondencia, doad_nr_dmr, heen_id, cetr_id FROM MODRED.ENDERECO_CONTATO where enco_id = (select max(ec.enco_id) from MODRED.ENDERECO_CONTATO ec);

INSERT INTO MODRED.ENDERECO_CONTATO (enco_id, enco_id_pais, enco_tx_tipo_logradouro, enco_tx_nome, enco_nr_numero, enco_tx_bairro, enco_tx_municipio, enco_tx_sigla_uf, enco_cep, cetr_id) VALUES (MODRED.SQ_ENCO_ID.nextval,'31','Rua','João Julião','331','Paraíso','São Paulo','SP','01323903',20);
INSERT INTO MODRED.AUDITORIA(AUDI_ID,AUDI_DT_DATA,AUDI_TX_USUARIO) values(MODRED.SQ_AUDI_ID.NEXTVAL,sysdate,'SISTEMA');
INSERT INTO MODRED.ENDERECO_CONTATO_AUD (audi_id, enco_id, audi_tx_tipo, enco_tx_bairro,
    enco_cep, enco_tx_complemento, enco_tx_endereco_estrangeiro, enco_tx_municipio, enco_tx_nome, enco_nr_numero, enco_tx_tipo_logradouro, enco_tx_sigla_uf, enco_id_pais, enco_in_excluido,
    paci_nr_rmr, enco_in_principal, enco_in_correspondencia, doad_nr_dmr, heen_id, cetr_id ) SELECT (SELECT MAX(AUDI_ID) FROM MODRED.AUDITORIA),
    enco_id, '0', enco_tx_bairro, enco_cep,enco_tx_complemento, enco_tx_endereco_estrangeiro, enco_tx_municipio, enco_tx_nome, enco_nr_numero, enco_tx_tipo_logradouro, enco_tx_sigla_uf, enco_id_pais, enco_in_excluido,
    paci_nr_rmr, enco_in_principal, enco_in_correspondencia, doad_nr_dmr, heen_id, cetr_id FROM MODRED.ENDERECO_CONTATO where enco_id = (select max(ec.enco_id) from MODRED.ENDERECO_CONTATO ec);

INSERT INTO MODRED.ENDERECO_CONTATO (enco_id, enco_id_pais, enco_tx_tipo_logradouro, enco_tx_nome, enco_nr_numero, enco_tx_bairro, enco_tx_municipio, enco_tx_sigla_uf, enco_cep, cetr_id) VALUES (MODRED.SQ_ENCO_ID.nextval,'31','Rua','Dona Silvéria','150','Vila Assis','Jaú','SP','17210200',4);
INSERT INTO MODRED.AUDITORIA(AUDI_ID,AUDI_DT_DATA,AUDI_TX_USUARIO) values(MODRED.SQ_AUDI_ID.NEXTVAL,sysdate,'SISTEMA');
INSERT INTO MODRED.ENDERECO_CONTATO_AUD (audi_id, enco_id, audi_tx_tipo, enco_tx_bairro,
    enco_cep, enco_tx_complemento, enco_tx_endereco_estrangeiro, enco_tx_municipio, enco_tx_nome, enco_nr_numero, enco_tx_tipo_logradouro, enco_tx_sigla_uf, enco_id_pais, enco_in_excluido,
    paci_nr_rmr, enco_in_principal, enco_in_correspondencia, doad_nr_dmr, heen_id, cetr_id ) SELECT (SELECT MAX(AUDI_ID) FROM MODRED.AUDITORIA),
    enco_id, '0', enco_tx_bairro, enco_cep,enco_tx_complemento, enco_tx_endereco_estrangeiro, enco_tx_municipio, enco_tx_nome, enco_nr_numero, enco_tx_tipo_logradouro, enco_tx_sigla_uf, enco_id_pais, enco_in_excluido,
    paci_nr_rmr, enco_in_principal, enco_in_correspondencia, doad_nr_dmr, heen_id, cetr_id FROM MODRED.ENDERECO_CONTATO where enco_id = (select max(ec.enco_id) from MODRED.ENDERECO_CONTATO ec);

INSERT INTO MODRED.ENDERECO_CONTATO (enco_id, enco_id_pais, enco_tx_tipo_logradouro, enco_tx_nome, enco_nr_numero, enco_tx_bairro, enco_tx_municipio, enco_tx_sigla_uf, enco_cep, cetr_id) VALUES (MODRED.SQ_ENCO_ID.nextval,'31','Avenida','Bandeirantes','3900','Monte Alegre','Ribeirão Preto','SP','14048900',14);
INSERT INTO MODRED.AUDITORIA(AUDI_ID,AUDI_DT_DATA,AUDI_TX_USUARIO) values(MODRED.SQ_AUDI_ID.NEXTVAL,sysdate,'SISTEMA');
INSERT INTO MODRED.ENDERECO_CONTATO_AUD (audi_id, enco_id, audi_tx_tipo, enco_tx_bairro,
    enco_cep, enco_tx_complemento, enco_tx_endereco_estrangeiro, enco_tx_municipio, enco_tx_nome, enco_nr_numero, enco_tx_tipo_logradouro, enco_tx_sigla_uf, enco_id_pais, enco_in_excluido,
    paci_nr_rmr, enco_in_principal, enco_in_correspondencia, doad_nr_dmr, heen_id, cetr_id ) SELECT (SELECT MAX(AUDI_ID) FROM MODRED.AUDITORIA),
    enco_id, '0', enco_tx_bairro, enco_cep,enco_tx_complemento, enco_tx_endereco_estrangeiro, enco_tx_municipio, enco_tx_nome, enco_nr_numero, enco_tx_tipo_logradouro, enco_tx_sigla_uf, enco_id_pais, enco_in_excluido,
    paci_nr_rmr, enco_in_principal, enco_in_correspondencia, doad_nr_dmr, heen_id, cetr_id FROM MODRED.ENDERECO_CONTATO where enco_id = (select max(ec.enco_id) from MODRED.ENDERECO_CONTATO ec);

INSERT INTO MODRED.ENDERECO_CONTATO (enco_id, enco_id_pais, enco_tx_tipo_logradouro, enco_tx_nome, enco_nr_numero, enco_tx_bairro, enco_tx_municipio, enco_tx_sigla_uf, enco_cep, cetr_id) VALUES (MODRED.SQ_ENCO_ID.nextval,'31','Alameda','Álvaro Celso','175','Santa Efigênia','Belo Horizonte','MG','30130100',8);
INSERT INTO MODRED.AUDITORIA(AUDI_ID,AUDI_DT_DATA,AUDI_TX_USUARIO) values(MODRED.SQ_AUDI_ID.NEXTVAL,sysdate,'SISTEMA');
INSERT INTO MODRED.ENDERECO_CONTATO_AUD (audi_id, enco_id, audi_tx_tipo, enco_tx_bairro,
    enco_cep, enco_tx_complemento, enco_tx_endereco_estrangeiro, enco_tx_municipio, enco_tx_nome, enco_nr_numero, enco_tx_tipo_logradouro, enco_tx_sigla_uf, enco_id_pais, enco_in_excluido,
    paci_nr_rmr, enco_in_principal, enco_in_correspondencia, doad_nr_dmr, heen_id, cetr_id ) SELECT (SELECT MAX(AUDI_ID) FROM MODRED.AUDITORIA),
    enco_id, '0', enco_tx_bairro, enco_cep,enco_tx_complemento, enco_tx_endereco_estrangeiro, enco_tx_municipio, enco_tx_nome, enco_nr_numero, enco_tx_tipo_logradouro, enco_tx_sigla_uf, enco_id_pais, enco_in_excluido,
    paci_nr_rmr, enco_in_principal, enco_in_correspondencia, doad_nr_dmr, heen_id, cetr_id FROM MODRED.ENDERECO_CONTATO where enco_id = (select max(ec.enco_id) from MODRED.ENDERECO_CONTATO ec);

INSERT INTO MODRED.ENDERECO_CONTATO (enco_id, enco_id_pais, enco_tx_tipo_logradouro, enco_tx_nome, enco_nr_numero, enco_tx_bairro, enco_tx_municipio, enco_tx_sigla_uf, enco_cep, cetr_id) VALUES (MODRED.SQ_ENCO_ID.nextval,'31','Avenida','Ramiro Barcelos','2350','Largo Eduardo','Porto Alegre','RS','90035903',2);
INSERT INTO MODRED.AUDITORIA(AUDI_ID,AUDI_DT_DATA,AUDI_TX_USUARIO) values(MODRED.SQ_AUDI_ID.NEXTVAL,sysdate,'SISTEMA');
INSERT INTO MODRED.ENDERECO_CONTATO_AUD (audi_id, enco_id, audi_tx_tipo, enco_tx_bairro,
    enco_cep, enco_tx_complemento, enco_tx_endereco_estrangeiro, enco_tx_municipio, enco_tx_nome, enco_nr_numero, enco_tx_tipo_logradouro, enco_tx_sigla_uf, enco_id_pais, enco_in_excluido,
    paci_nr_rmr, enco_in_principal, enco_in_correspondencia, doad_nr_dmr, heen_id, cetr_id ) SELECT (SELECT MAX(AUDI_ID) FROM MODRED.AUDITORIA),
    enco_id, '0', enco_tx_bairro, enco_cep,enco_tx_complemento, enco_tx_endereco_estrangeiro, enco_tx_municipio, enco_tx_nome, enco_nr_numero, enco_tx_tipo_logradouro, enco_tx_sigla_uf, enco_id_pais, enco_in_excluido,
    paci_nr_rmr, enco_in_principal, enco_in_correspondencia, doad_nr_dmr, heen_id, cetr_id FROM MODRED.ENDERECO_CONTATO where enco_id = (select max(ec.enco_id) from MODRED.ENDERECO_CONTATO ec);

INSERT INTO MODRED.ENDERECO_CONTATO (enco_id, enco_id_pais, enco_tx_tipo_logradouro, enco_tx_nome, enco_nr_numero, enco_tx_bairro, enco_tx_municipio, enco_tx_sigla_uf, enco_cep, cetr_id) VALUES (MODRED.SQ_ENCO_ID.nextval,'31','Rua','General Carneiro','181','Centro','Curitiba','PR','80060900',1);
INSERT INTO MODRED.AUDITORIA(AUDI_ID,AUDI_DT_DATA,AUDI_TX_USUARIO) values(MODRED.SQ_AUDI_ID.NEXTVAL,sysdate,'SISTEMA');
INSERT INTO MODRED.ENDERECO_CONTATO_AUD (audi_id, enco_id, audi_tx_tipo, enco_tx_bairro,
    enco_cep, enco_tx_complemento, enco_tx_endereco_estrangeiro, enco_tx_municipio, enco_tx_nome, enco_nr_numero, enco_tx_tipo_logradouro, enco_tx_sigla_uf, enco_id_pais, enco_in_excluido,
    paci_nr_rmr, enco_in_principal, enco_in_correspondencia, doad_nr_dmr, heen_id, cetr_id ) SELECT (SELECT MAX(AUDI_ID) FROM MODRED.AUDITORIA),
    enco_id, '0', enco_tx_bairro, enco_cep,enco_tx_complemento, enco_tx_endereco_estrangeiro, enco_tx_municipio, enco_tx_nome, enco_nr_numero, enco_tx_tipo_logradouro, enco_tx_sigla_uf, enco_id_pais, enco_in_excluido,
    paci_nr_rmr, enco_in_principal, enco_in_correspondencia, doad_nr_dmr, heen_id, cetr_id FROM MODRED.ENDERECO_CONTATO where enco_id = (select max(ec.enco_id) from MODRED.ENDERECO_CONTATO ec);

INSERT INTO MODRED.ENDERECO_CONTATO (enco_id, enco_id_pais, enco_tx_tipo_logradouro, enco_tx_nome, enco_nr_numero, enco_tx_bairro, enco_tx_municipio, enco_tx_sigla_uf, enco_cep, cetr_id) VALUES (MODRED.SQ_ENCO_ID.nextval,'31','Avenida','Albert Einstein','627','Morumbi','São Paulo','SP','56520000',7);
INSERT INTO MODRED.AUDITORIA(AUDI_ID,AUDI_DT_DATA,AUDI_TX_USUARIO) values(MODRED.SQ_AUDI_ID.NEXTVAL,sysdate,'SISTEMA');
INSERT INTO MODRED.ENDERECO_CONTATO_AUD (audi_id, enco_id, audi_tx_tipo, enco_tx_bairro,
    enco_cep, enco_tx_complemento, enco_tx_endereco_estrangeiro, enco_tx_municipio, enco_tx_nome, enco_nr_numero, enco_tx_tipo_logradouro, enco_tx_sigla_uf, enco_id_pais, enco_in_excluido,
    paci_nr_rmr, enco_in_principal, enco_in_correspondencia, doad_nr_dmr, heen_id, cetr_id ) SELECT (SELECT MAX(AUDI_ID) FROM MODRED.AUDITORIA),
    enco_id, '0', enco_tx_bairro, enco_cep,enco_tx_complemento, enco_tx_endereco_estrangeiro, enco_tx_municipio, enco_tx_nome, enco_nr_numero, enco_tx_tipo_logradouro, enco_tx_sigla_uf, enco_id_pais, enco_in_excluido,
    paci_nr_rmr, enco_in_principal, enco_in_correspondencia, doad_nr_dmr, heen_id, cetr_id FROM MODRED.ENDERECO_CONTATO where enco_id = (select max(ec.enco_id) from MODRED.ENDERECO_CONTATO ec);

INSERT INTO MODRED.ENDERECO_CONTATO (enco_id, enco_id_pais, enco_tx_tipo_logradouro, enco_tx_nome, enco_nr_numero, enco_tx_bairro, enco_tx_municipio, enco_tx_sigla_uf, enco_cep, cetr_id) VALUES (MODRED.SQ_ENCO_ID.nextval,'31','Praça','da Cruz Vermelha','23','Centro','Rio de Janeiro','RJ','20230130',3);
INSERT INTO MODRED.AUDITORIA(AUDI_ID,AUDI_DT_DATA,AUDI_TX_USUARIO) values(MODRED.SQ_AUDI_ID.NEXTVAL,sysdate,'SISTEMA');
INSERT INTO MODRED.ENDERECO_CONTATO_AUD (audi_id, enco_id, audi_tx_tipo, enco_tx_bairro,
    enco_cep, enco_tx_complemento, enco_tx_endereco_estrangeiro, enco_tx_municipio, enco_tx_nome, enco_nr_numero, enco_tx_tipo_logradouro, enco_tx_sigla_uf, enco_id_pais, enco_in_excluido,
    paci_nr_rmr, enco_in_principal, enco_in_correspondencia, doad_nr_dmr, heen_id, cetr_id ) SELECT (SELECT MAX(AUDI_ID) FROM MODRED.AUDITORIA),
    enco_id, '0', enco_tx_bairro, enco_cep,enco_tx_complemento, enco_tx_endereco_estrangeiro, enco_tx_municipio, enco_tx_nome, enco_nr_numero, enco_tx_tipo_logradouro, enco_tx_sigla_uf, enco_id_pais, enco_in_excluido,
    paci_nr_rmr, enco_in_principal, enco_in_correspondencia, doad_nr_dmr, heen_id, cetr_id FROM MODRED.ENDERECO_CONTATO where enco_id = (select max(ec.enco_id) from MODRED.ENDERECO_CONTATO ec);

INSERT INTO MODRED.ENDERECO_CONTATO (enco_id, enco_id_pais, enco_tx_tipo_logradouro, enco_tx_nome, enco_nr_numero, enco_tx_bairro, enco_tx_municipio, enco_tx_sigla_uf, enco_cep, cetr_id) VALUES (MODRED.SQ_ENCO_ID.nextval,'31','Avenida','Afonso Pena','754','Tirol','Natal','RN','59020100',17);
--enderecos
INSERT INTO MODRED.AUDITORIA(AUDI_ID,AUDI_DT_DATA,AUDI_TX_USUARIO) values(MODRED.SQ_AUDI_ID.NEXTVAL,sysdate,'SISTEMA');
INSERT INTO MODRED.ENDERECO_CONTATO_AUD (audi_id, enco_id, audi_tx_tipo, enco_tx_bairro,
    enco_cep, enco_tx_complemento, enco_tx_endereco_estrangeiro, enco_tx_municipio, enco_tx_nome, enco_nr_numero, enco_tx_tipo_logradouro, enco_tx_sigla_uf, enco_id_pais, enco_in_excluido,
    paci_nr_rmr, enco_in_principal, enco_in_correspondencia, doad_nr_dmr, heen_id, cetr_id ) SELECT (SELECT MAX(AUDI_ID) FROM MODRED.AUDITORIA),
    enco_id, '0', enco_tx_bairro, enco_cep,enco_tx_complemento, enco_tx_endereco_estrangeiro, enco_tx_municipio, enco_tx_nome, enco_nr_numero, enco_tx_tipo_logradouro, enco_tx_sigla_uf, enco_id_pais, enco_in_excluido,
    paci_nr_rmr, enco_in_principal, enco_in_correspondencia, doad_nr_dmr, heen_id, cetr_id FROM MODRED.ENDERECO_CONTATO where enco_id = (select max(ec.enco_id) from MODRED.ENDERECO_CONTATO ec);

INSERT INTO MODRED.ENDERECO_CONTATO (enco_id, enco_id_pais, enco_tx_tipo_logradouro, enco_tx_nome, enco_nr_numero, enco_tx_bairro, enco_tx_municipio, enco_tx_sigla_uf, enco_cep, cetr_id) VALUES (MODRED.SQ_ENCO_ID.nextval,'31','Avenida','Agamenon Magalhães','4760','Paissandu','Recife','PE','52010902',5);
INSERT INTO MODRED.AUDITORIA(AUDI_ID,AUDI_DT_DATA,AUDI_TX_USUARIO) values(MODRED.SQ_AUDI_ID.NEXTVAL,sysdate,'SISTEMA');
INSERT INTO MODRED.ENDERECO_CONTATO_AUD (audi_id, enco_id, audi_tx_tipo, enco_tx_bairro,
    enco_cep, enco_tx_complemento, enco_tx_endereco_estrangeiro, enco_tx_municipio, enco_tx_nome, enco_nr_numero, enco_tx_tipo_logradouro, enco_tx_sigla_uf, enco_id_pais, enco_in_excluido,
    paci_nr_rmr, enco_in_principal, enco_in_correspondencia, doad_nr_dmr, heen_id, cetr_id ) SELECT (SELECT MAX(AUDI_ID) FROM MODRED.AUDITORIA),
    enco_id, '0', enco_tx_bairro, enco_cep,enco_tx_complemento, enco_tx_endereco_estrangeiro, enco_tx_municipio, enco_tx_nome, enco_nr_numero, enco_tx_tipo_logradouro, enco_tx_sigla_uf, enco_id_pais, enco_in_excluido,
    paci_nr_rmr, enco_in_principal, enco_in_correspondencia, doad_nr_dmr, heen_id, cetr_id FROM MODRED.ENDERECO_CONTATO where enco_id = (select max(ec.enco_id) from MODRED.ENDERECO_CONTATO ec);

INSERT INTO MODRED.ENDERECO_CONTATO (enco_id, enco_id_pais, enco_tx_tipo_logradouro, enco_tx_nome, enco_nr_numero, enco_tx_bairro, enco_tx_municipio, enco_tx_sigla_uf, enco_cep, cetr_id) VALUES (MODRED.SQ_ENCO_ID.nextval,'31','Avenida','Francisco Sales','1111','Santa Efigênia','Belo Horizonte','MG','30150225',34);
INSERT INTO MODRED.AUDITORIA(AUDI_ID,AUDI_DT_DATA,AUDI_TX_USUARIO) values(MODRED.SQ_AUDI_ID.NEXTVAL,sysdate,'SISTEMA');
INSERT INTO MODRED.ENDERECO_CONTATO_AUD (audi_id, enco_id, audi_tx_tipo, enco_tx_bairro,
    enco_cep, enco_tx_complemento, enco_tx_endereco_estrangeiro, enco_tx_municipio, enco_tx_nome, enco_nr_numero, enco_tx_tipo_logradouro, enco_tx_sigla_uf, enco_id_pais, enco_in_excluido,
    paci_nr_rmr, enco_in_principal, enco_in_correspondencia, doad_nr_dmr, heen_id, cetr_id ) SELECT (SELECT MAX(AUDI_ID) FROM MODRED.AUDITORIA),
    enco_id, '0', enco_tx_bairro, enco_cep,enco_tx_complemento, enco_tx_endereco_estrangeiro, enco_tx_municipio, enco_tx_nome, enco_nr_numero, enco_tx_tipo_logradouro, enco_tx_sigla_uf, enco_id_pais, enco_in_excluido,
    paci_nr_rmr, enco_in_principal, enco_in_correspondencia, doad_nr_dmr, heen_id, cetr_id FROM MODRED.ENDERECO_CONTATO where enco_id = (select max(ec.enco_id) from MODRED.ENDERECO_CONTATO ec);

INSERT INTO MODRED.ENDERECO_CONTATO (enco_id, enco_id_pais, enco_tx_tipo_logradouro, enco_tx_nome, enco_nr_numero, enco_tx_bairro, enco_tx_municipio, enco_tx_sigla_uf, enco_cep, cetr_id) VALUES (MODRED.SQ_ENCO_ID.nextval,'31','Rua','Carlos Chagas','480','Barão Geraldo','Campinas','SP','13083978',6);
INSERT INTO MODRED.AUDITORIA(AUDI_ID,AUDI_DT_DATA,AUDI_TX_USUARIO) values(MODRED.SQ_AUDI_ID.NEXTVAL,sysdate,'SISTEMA');
INSERT INTO MODRED.ENDERECO_CONTATO_AUD (audi_id, enco_id, audi_tx_tipo, enco_tx_bairro,
    enco_cep, enco_tx_complemento, enco_tx_endereco_estrangeiro, enco_tx_municipio, enco_tx_nome, enco_nr_numero, enco_tx_tipo_logradouro, enco_tx_sigla_uf, enco_id_pais, enco_in_excluido,
    paci_nr_rmr, enco_in_principal, enco_in_correspondencia, doad_nr_dmr, heen_id, cetr_id ) SELECT (SELECT MAX(AUDI_ID) FROM MODRED.AUDITORIA),
    enco_id, '0', enco_tx_bairro, enco_cep,enco_tx_complemento, enco_tx_endereco_estrangeiro, enco_tx_municipio, enco_tx_nome, enco_nr_numero, enco_tx_tipo_logradouro, enco_tx_sigla_uf, enco_id_pais, enco_in_excluido,
    paci_nr_rmr, enco_in_principal, enco_in_correspondencia, doad_nr_dmr, heen_id, cetr_id FROM MODRED.ENDERECO_CONTATO where enco_id = (select max(ec.enco_id) from MODRED.ENDERECO_CONTATO ec);

commit;

--telefones

INSERT INTO MODRED.CONTATO_TELEFONICO (cote_id, cote_in_tipo, cote_nr_cod_inter, cote_nr_cod_area, cote_nr_numero, cote_tx_nome, cetr_id ) VALUES ( MODRED.SQ_COTE_ID.NEXTVAL, 1, '55','41','33601700','Dra. Carmem Bonfim',1);
INSERT INTO MODRED.AUDITORIA(AUDI_ID,AUDI_DT_DATA,AUDI_TX_USUARIO) values(MODRED.SQ_AUDI_ID.NEXTVAL,sysdate,'SISTEMA');
INSERT INTO MODRED.CONTATO_TELEFONICO_AUD ( cote_id, audi_id, audi_tx_tipo, cote_nr_cod_area, cote_nr_cod_inter, cote_tx_complemento, cote_tx_nome, cote_nr_numero, cote_in_principal, cote_in_tipo, paci_nr_rmr, cote_in_excluido, doad_nr_dmr, medi_id, cetr_id) 
SELECT cote_id, (SELECT MAX(AUDI_ID) FROM MODRED.AUDITORIA), '0', cote_nr_cod_area, cote_nr_cod_inter, cote_tx_complemento, cote_tx_nome, cote_nr_numero,cote_in_principal, cote_in_tipo, paci_nr_rmr, cote_in_excluido, doad_nr_dmr, medi_id, cetr_id
FROM MODRED.CONTATO_TELEFONICO where cote_id = (select max(ec.cote_id) from MODRED.CONTATO_TELEFONICO ec);

INSERT INTO MODRED.CONTATO_TELEFONICO (cote_id, cote_in_tipo, cote_nr_cod_inter, cote_nr_cod_area, cote_nr_numero, cote_tx_nome, cetr_id ) VALUES ( MODRED.SQ_COTE_ID.NEXTVAL, 1, '55','52','33598001','Dra. Liane Esteves e/ou Dr. Lauro Gregianin',2);
INSERT INTO MODRED.AUDITORIA(AUDI_ID,AUDI_DT_DATA,AUDI_TX_USUARIO) values(MODRED.SQ_AUDI_ID.NEXTVAL,sysdate,'SISTEMA');
INSERT INTO MODRED.CONTATO_TELEFONICO_AUD ( cote_id, audi_id, audi_tx_tipo, cote_nr_cod_area, cote_nr_cod_inter, cote_tx_complemento, cote_tx_nome, cote_nr_numero, cote_in_principal, cote_in_tipo, paci_nr_rmr, cote_in_excluido, doad_nr_dmr, medi_id, cetr_id) 
SELECT cote_id, (SELECT MAX(AUDI_ID) FROM MODRED.AUDITORIA), '0', cote_nr_cod_area, cote_nr_cod_inter, cote_tx_complemento, cote_tx_nome, cote_nr_numero,cote_in_principal, cote_in_tipo, paci_nr_rmr, cote_in_excluido, doad_nr_dmr, medi_id, cetr_id
FROM MODRED.CONTATO_TELEFONICO where cote_id = (select max(ec.cote_id) from MODRED.CONTATO_TELEFONICO ec);

INSERT INTO MODRED.CONTATO_TELEFONICO (cote_id, cote_in_tipo, cote_nr_cod_inter, cote_nr_cod_area, cote_nr_numero, cote_tx_nome, cetr_id ) VALUES ( MODRED.SQ_COTE_ID.NEXTVAL, 1, '55','21','32071818','Drª Simone Maradei',3);
INSERT INTO MODRED.AUDITORIA(AUDI_ID,AUDI_DT_DATA,AUDI_TX_USUARIO) values(MODRED.SQ_AUDI_ID.NEXTVAL,sysdate,'SISTEMA');
INSERT INTO MODRED.CONTATO_TELEFONICO_AUD ( cote_id, audi_id, audi_tx_tipo, cote_nr_cod_area, cote_nr_cod_inter, cote_tx_complemento, cote_tx_nome, cote_nr_numero, cote_in_principal, cote_in_tipo, paci_nr_rmr, cote_in_excluido, doad_nr_dmr, medi_id, cetr_id) 
SELECT cote_id, (SELECT MAX(AUDI_ID) FROM MODRED.AUDITORIA), '0', cote_nr_cod_area, cote_nr_cod_inter, cote_tx_complemento, cote_tx_nome, cote_nr_numero,cote_in_principal, cote_in_tipo, paci_nr_rmr, cote_in_excluido, doad_nr_dmr, medi_id, cetr_id
FROM MODRED.CONTATO_TELEFONICO where cote_id = (select max(ec.cote_id) from MODRED.CONTATO_TELEFONICO ec);

INSERT INTO MODRED.CONTATO_TELEFONICO (cote_id, cote_in_tipo, cote_nr_cod_inter, cote_nr_cod_area, cote_nr_numero, cote_tx_nome, cetr_id ) VALUES ( MODRED.SQ_COTE_ID.NEXTVAL, 1, '55','14','36021289','Dr. Vergílio Antonio Rensi Colturato',4);
INSERT INTO MODRED.AUDITORIA(AUDI_ID,AUDI_DT_DATA,AUDI_TX_USUARIO) values(MODRED.SQ_AUDI_ID.NEXTVAL,sysdate,'SISTEMA');
INSERT INTO MODRED.CONTATO_TELEFONICO_AUD ( cote_id, audi_id, audi_tx_tipo, cote_nr_cod_area, cote_nr_cod_inter, cote_tx_complemento, cote_tx_nome, cote_nr_numero, cote_in_principal, cote_in_tipo, paci_nr_rmr, cote_in_excluido, doad_nr_dmr, medi_id, cetr_id) 
SELECT cote_id, (SELECT MAX(AUDI_ID) FROM MODRED.AUDITORIA), '0', cote_nr_cod_area, cote_nr_cod_inter, cote_tx_complemento, cote_tx_nome, cote_nr_numero,cote_in_principal, cote_in_tipo, paci_nr_rmr, cote_in_excluido, doad_nr_dmr, medi_id, cetr_id
FROM MODRED.CONTATO_TELEFONICO where cote_id = (select max(ec.cote_id) from MODRED.CONTATO_TELEFONICO ec);

INSERT INTO MODRED.CONTATO_TELEFONICO (cote_id, cote_in_tipo, cote_nr_cod_inter, cote_nr_cod_area, cote_nr_numero, cote_tx_nome, cetr_id ) VALUES ( MODRED.SQ_COTE_ID.NEXTVAL, 1, '55','81','34161930','Dr. Luis Fábio Botelho e Drª. Mariana Cahú',5);
INSERT INTO MODRED.AUDITORIA(AUDI_ID,AUDI_DT_DATA,AUDI_TX_USUARIO) values(MODRED.SQ_AUDI_ID.NEXTVAL,sysdate,'SISTEMA');
INSERT INTO MODRED.CONTATO_TELEFONICO_AUD ( cote_id, audi_id, audi_tx_tipo, cote_nr_cod_area, cote_nr_cod_inter, cote_tx_complemento, cote_tx_nome, cote_nr_numero, cote_in_principal, cote_in_tipo, paci_nr_rmr, cote_in_excluido, doad_nr_dmr, medi_id, cetr_id) 
SELECT cote_id, (SELECT MAX(AUDI_ID) FROM MODRED.AUDITORIA), '0', cote_nr_cod_area, cote_nr_cod_inter, cote_tx_complemento, cote_tx_nome, cote_nr_numero,cote_in_principal, cote_in_tipo, paci_nr_rmr, cote_in_excluido, doad_nr_dmr, medi_id, cetr_id
FROM MODRED.CONTATO_TELEFONICO where cote_id = (select max(ec.cote_id) from MODRED.CONTATO_TELEFONICO ec);

INSERT INTO MODRED.CONTATO_TELEFONICO (cote_id, cote_in_tipo, cote_nr_cod_inter, cote_nr_cod_area, cote_nr_numero, cote_tx_nome, cetr_id ) VALUES ( MODRED.SQ_COTE_ID.NEXTVAL, 1, '55','19','35218740','DR. FRANCISCO ARANHA OU DR. AFONSO',6);
INSERT INTO MODRED.AUDITORIA(AUDI_ID,AUDI_DT_DATA,AUDI_TX_USUARIO) values(MODRED.SQ_AUDI_ID.NEXTVAL,sysdate,'SISTEMA');
INSERT INTO MODRED.CONTATO_TELEFONICO_AUD ( cote_id, audi_id, audi_tx_tipo, cote_nr_cod_area, cote_nr_cod_inter, cote_tx_complemento, cote_tx_nome, cote_nr_numero, cote_in_principal, cote_in_tipo, paci_nr_rmr, cote_in_excluido, doad_nr_dmr, medi_id, cetr_id) 
SELECT cote_id, (SELECT MAX(AUDI_ID) FROM MODRED.AUDITORIA), '0', cote_nr_cod_area, cote_nr_cod_inter, cote_tx_complemento, cote_tx_nome, cote_nr_numero,cote_in_principal, cote_in_tipo, paci_nr_rmr, cote_in_excluido, doad_nr_dmr, medi_id, cetr_id
FROM MODRED.CONTATO_TELEFONICO where cote_id = (select max(ec.cote_id) from MODRED.CONTATO_TELEFONICO ec);

INSERT INTO MODRED.CONTATO_TELEFONICO (cote_id, cote_in_tipo, cote_nr_cod_inter, cote_nr_cod_area, cote_nr_numero, cote_tx_nome, cetr_id ) VALUES ( MODRED.SQ_COTE_ID.NEXTVAL, 1, '55','11','21511477','Dra Andrea Kondo / Dra. Aline Guilherme',7);
INSERT INTO MODRED.AUDITORIA(AUDI_ID,AUDI_DT_DATA,AUDI_TX_USUARIO) values(MODRED.SQ_AUDI_ID.NEXTVAL,sysdate,'SISTEMA');
INSERT INTO MODRED.CONTATO_TELEFONICO_AUD ( cote_id, audi_id, audi_tx_tipo, cote_nr_cod_area, cote_nr_cod_inter, cote_tx_complemento, cote_tx_nome, cote_nr_numero, cote_in_principal, cote_in_tipo, paci_nr_rmr, cote_in_excluido, doad_nr_dmr, medi_id, cetr_id) 
SELECT cote_id, (SELECT MAX(AUDI_ID) FROM MODRED.AUDITORIA), '0', cote_nr_cod_area, cote_nr_cod_inter, cote_tx_complemento, cote_tx_nome, cote_nr_numero,cote_in_principal, cote_in_tipo, paci_nr_rmr, cote_in_excluido, doad_nr_dmr, medi_id, cetr_id
FROM MODRED.CONTATO_TELEFONICO where cote_id = (select max(ec.cote_id) from MODRED.CONTATO_TELEFONICO ec);

INSERT INTO MODRED.CONTATO_TELEFONICO (cote_id, cote_in_tipo, cote_nr_cod_inter, cote_nr_cod_area, cote_nr_numero, cote_tx_nome, cetr_id ) VALUES ( MODRED.SQ_COTE_ID.NEXTVAL, 1, '55',' 31','34099198','Dr. Antonio Vaz de Macedo',8);
INSERT INTO MODRED.AUDITORIA(AUDI_ID,AUDI_DT_DATA,AUDI_TX_USUARIO) values(MODRED.SQ_AUDI_ID.NEXTVAL,sysdate,'SISTEMA');
INSERT INTO MODRED.CONTATO_TELEFONICO_AUD ( cote_id, audi_id, audi_tx_tipo, cote_nr_cod_area, cote_nr_cod_inter, cote_tx_complemento, cote_tx_nome, cote_nr_numero, cote_in_principal, cote_in_tipo, paci_nr_rmr, cote_in_excluido, doad_nr_dmr, medi_id, cetr_id) 
SELECT cote_id, (SELECT MAX(AUDI_ID) FROM MODRED.AUDITORIA), '0', cote_nr_cod_area, cote_nr_cod_inter, cote_tx_complemento, cote_tx_nome, cote_nr_numero,cote_in_principal, cote_in_tipo, paci_nr_rmr, cote_in_excluido, doad_nr_dmr, medi_id, cetr_id
FROM MODRED.CONTATO_TELEFONICO where cote_id = (select max(ec.cote_id) from MODRED.CONTATO_TELEFONICO ec);

INSERT INTO MODRED.CONTATO_TELEFONICO (cote_id, cote_in_tipo, cote_nr_cod_inter, cote_nr_cod_area, cote_nr_numero, cote_tx_nome, cetr_id ) VALUES ( MODRED.SQ_COTE_ID.NEXTVAL, 1, '55','19','37875110','Dra. KÁTIA APARECIDA DE BRITO EID',9);
INSERT INTO MODRED.AUDITORIA(AUDI_ID,AUDI_DT_DATA,AUDI_TX_USUARIO) values(MODRED.SQ_AUDI_ID.NEXTVAL,sysdate,'SISTEMA');
INSERT INTO MODRED.CONTATO_TELEFONICO_AUD ( cote_id, audi_id, audi_tx_tipo, cote_nr_cod_area, cote_nr_cod_inter, cote_tx_complemento, cote_tx_nome, cote_nr_numero, cote_in_principal, cote_in_tipo, paci_nr_rmr, cote_in_excluido, doad_nr_dmr, medi_id, cetr_id) 
SELECT cote_id, (SELECT MAX(AUDI_ID) FROM MODRED.AUDITORIA), '0', cote_nr_cod_area, cote_nr_cod_inter, cote_tx_complemento, cote_tx_nome, cote_nr_numero,cote_in_principal, cote_in_tipo, paci_nr_rmr, cote_in_excluido, doad_nr_dmr, medi_id, cetr_id
FROM MODRED.CONTATO_TELEFONICO where cote_id = (select max(ec.cote_id) from MODRED.CONTATO_TELEFONICO ec);

INSERT INTO MODRED.CONTATO_TELEFONICO (cote_id, cote_in_tipo, cote_nr_cod_inter, cote_nr_cod_area, cote_nr_numero, cote_tx_nome, cetr_id ) VALUES ( MODRED.SQ_COTE_ID.NEXTVAL, 1, '55','11','50808478','Dr. Victor Gottardello Zechin',11);
INSERT INTO MODRED.AUDITORIA(AUDI_ID,AUDI_DT_DATA,AUDI_TX_USUARIO) values(MODRED.SQ_AUDI_ID.NEXTVAL,sysdate,'SISTEMA');
INSERT INTO MODRED.CONTATO_TELEFONICO_AUD ( cote_id, audi_id, audi_tx_tipo, cote_nr_cod_area, cote_nr_cod_inter, cote_tx_complemento, cote_tx_nome, cote_nr_numero, cote_in_principal, cote_in_tipo, paci_nr_rmr, cote_in_excluido, doad_nr_dmr, medi_id, cetr_id) 
SELECT cote_id, (SELECT MAX(AUDI_ID) FROM MODRED.AUDITORIA), '0', cote_nr_cod_area, cote_nr_cod_inter, cote_tx_complemento, cote_tx_nome, cote_nr_numero,cote_in_principal, cote_in_tipo, paci_nr_rmr, cote_in_excluido, doad_nr_dmr, medi_id, cetr_id
FROM MODRED.CONTATO_TELEFONICO where cote_id = (select max(ec.cote_id) from MODRED.CONTATO_TELEFONICO ec);

INSERT INTO MODRED.CONTATO_TELEFONICO (cote_id, cote_in_tipo, cote_nr_cod_inter, cote_nr_cod_area, cote_nr_numero, cote_tx_nome, cetr_id ) VALUES ( MODRED.SQ_COTE_ID.NEXTVAL, 1, '55','11','20706453','Dra. Ana Marcela',13);
INSERT INTO MODRED.AUDITORIA(AUDI_ID,AUDI_DT_DATA,AUDI_TX_USUARIO) values(MODRED.SQ_AUDI_ID.NEXTVAL,sysdate,'SISTEMA');
INSERT INTO MODRED.CONTATO_TELEFONICO_AUD ( cote_id, audi_id, audi_tx_tipo, cote_nr_cod_area, cote_nr_cod_inter, cote_tx_complemento, cote_tx_nome, cote_nr_numero, cote_in_principal, cote_in_tipo, paci_nr_rmr, cote_in_excluido, doad_nr_dmr, medi_id, cetr_id) 
SELECT cote_id, (SELECT MAX(AUDI_ID) FROM MODRED.AUDITORIA), '0', cote_nr_cod_area, cote_nr_cod_inter, cote_tx_complemento, cote_tx_nome, cote_nr_numero,cote_in_principal, cote_in_tipo, paci_nr_rmr, cote_in_excluido, doad_nr_dmr, medi_id, cetr_id
FROM MODRED.CONTATO_TELEFONICO where cote_id = (select max(ec.cote_id) from MODRED.CONTATO_TELEFONICO ec);

INSERT INTO MODRED.CONTATO_TELEFONICO (cote_id, cote_in_tipo, cote_nr_cod_inter, cote_nr_cod_area, cote_nr_numero, cote_tx_nome, cetr_id ) VALUES ( MODRED.SQ_COTE_ID.NEXTVAL, 1, '55','16','36022705','Dr. Fabiano Pieroni',14);
INSERT INTO MODRED.AUDITORIA(AUDI_ID,AUDI_DT_DATA,AUDI_TX_USUARIO) values(MODRED.SQ_AUDI_ID.NEXTVAL,sysdate,'SISTEMA');
INSERT INTO MODRED.CONTATO_TELEFONICO_AUD ( cote_id, audi_id, audi_tx_tipo, cote_nr_cod_area, cote_nr_cod_inter, cote_tx_complemento, cote_tx_nome, cote_nr_numero, cote_in_principal, cote_in_tipo, paci_nr_rmr, cote_in_excluido, doad_nr_dmr, medi_id, cetr_id) 
SELECT cote_id, (SELECT MAX(AUDI_ID) FROM MODRED.AUDITORIA), '0', cote_nr_cod_area, cote_nr_cod_inter, cote_tx_complemento, cote_tx_nome, cote_nr_numero,cote_in_principal, cote_in_tipo, paci_nr_rmr, cote_in_excluido, doad_nr_dmr, medi_id, cetr_id
FROM MODRED.CONTATO_TELEFONICO where cote_id = (select max(ec.cote_id) from MODRED.CONTATO_TELEFONICO ec);

INSERT INTO MODRED.CONTATO_TELEFONICO (cote_id, cote_in_tipo, cote_nr_cod_inter, cote_nr_cod_area, cote_nr_numero, cote_tx_nome, cetr_id ) VALUES ( MODRED.SQ_COTE_ID.NEXTVAL, 1, '55','11','21895000','Dr. Garles Miller Matias Vieira',16);
INSERT INTO MODRED.AUDITORIA(AUDI_ID,AUDI_DT_DATA,AUDI_TX_USUARIO) values(MODRED.SQ_AUDI_ID.NEXTVAL,sysdate,'SISTEMA');
INSERT INTO MODRED.CONTATO_TELEFONICO_AUD ( cote_id, audi_id, audi_tx_tipo, cote_nr_cod_area, cote_nr_cod_inter, cote_tx_complemento, cote_tx_nome, cote_nr_numero, cote_in_principal, cote_in_tipo, paci_nr_rmr, cote_in_excluido, doad_nr_dmr, medi_id, cetr_id) 
SELECT cote_id, (SELECT MAX(AUDI_ID) FROM MODRED.AUDITORIA), '0', cote_nr_cod_area, cote_nr_cod_inter, cote_tx_complemento, cote_tx_nome, cote_nr_numero,cote_in_principal, cote_in_tipo, paci_nr_rmr, cote_in_excluido, doad_nr_dmr, medi_id, cetr_id
FROM MODRED.CONTATO_TELEFONICO where cote_id = (select max(ec.cote_id) from MODRED.CONTATO_TELEFONICO ec);

INSERT INTO MODRED.CONTATO_TELEFONICO (cote_id, cote_in_tipo, cote_nr_cod_inter, cote_nr_cod_area, cote_nr_numero, cote_tx_nome, cetr_id ) VALUES ( MODRED.SQ_COTE_ID.NEXTVAL, 1, '55','84','40091185','Dr. Rodolfo',17);
INSERT INTO MODRED.AUDITORIA(AUDI_ID,AUDI_DT_DATA,AUDI_TX_USUARIO) values(MODRED.SQ_AUDI_ID.NEXTVAL,sysdate,'SISTEMA');
INSERT INTO MODRED.CONTATO_TELEFONICO_AUD ( cote_id, audi_id, audi_tx_tipo, cote_nr_cod_area, cote_nr_cod_inter, cote_tx_complemento, cote_tx_nome, cote_nr_numero, cote_in_principal, cote_in_tipo, paci_nr_rmr, cote_in_excluido, doad_nr_dmr, medi_id, cetr_id) 
SELECT cote_id, (SELECT MAX(AUDI_ID) FROM MODRED.AUDITORIA), '0', cote_nr_cod_area, cote_nr_cod_inter, cote_tx_complemento, cote_tx_nome, cote_nr_numero,cote_in_principal, cote_in_tipo, paci_nr_rmr, cote_in_excluido, doad_nr_dmr, medi_id, cetr_id
FROM MODRED.CONTATO_TELEFONICO where cote_id = (select max(ec.cote_id) from MODRED.CONTATO_TELEFONICO ec);

INSERT INTO MODRED.CONTATO_TELEFONICO (cote_id, cote_in_tipo, cote_nr_cod_inter, cote_nr_cod_area, cote_nr_numero, cote_tx_nome, cetr_id ) VALUES ( MODRED.SQ_COTE_ID.NEXTVAL, 1, '55','11','32146467','Philip Bachour',20);
INSERT INTO MODRED.AUDITORIA(AUDI_ID,AUDI_DT_DATA,AUDI_TX_USUARIO) values(MODRED.SQ_AUDI_ID.NEXTVAL,sysdate,'SISTEMA');
INSERT INTO MODRED.CONTATO_TELEFONICO_AUD ( cote_id, audi_id, audi_tx_tipo, cote_nr_cod_area, cote_nr_cod_inter, cote_tx_complemento, cote_tx_nome, cote_nr_numero, cote_in_principal, cote_in_tipo, paci_nr_rmr, cote_in_excluido, doad_nr_dmr, medi_id, cetr_id) 
SELECT cote_id, (SELECT MAX(AUDI_ID) FROM MODRED.AUDITORIA), '0', cote_nr_cod_area, cote_nr_cod_inter, cote_tx_complemento, cote_tx_nome, cote_nr_numero,cote_in_principal, cote_in_tipo, paci_nr_rmr, cote_in_excluido, doad_nr_dmr, medi_id, cetr_id
FROM MODRED.CONTATO_TELEFONICO where cote_id = (select max(ec.cote_id) from MODRED.CONTATO_TELEFONICO ec);

INSERT INTO MODRED.CONTATO_TELEFONICO (cote_id, cote_in_tipo, cote_nr_cod_inter, cote_nr_cod_area, cote_nr_numero, cote_tx_nome, cetr_id ) VALUES ( MODRED.SQ_COTE_ID.NEXTVAL, 1, '55','17','33216600','Dr. Eduardo José de Alencar Paton',21);
INSERT INTO MODRED.AUDITORIA(AUDI_ID,AUDI_DT_DATA,AUDI_TX_USUARIO) values(MODRED.SQ_AUDI_ID.NEXTVAL,sysdate,'SISTEMA');
INSERT INTO MODRED.CONTATO_TELEFONICO_AUD ( cote_id, audi_id, audi_tx_tipo, cote_nr_cod_area, cote_nr_cod_inter, cote_tx_complemento, cote_tx_nome, cote_nr_numero, cote_in_principal, cote_in_tipo, paci_nr_rmr, cote_in_excluido, doad_nr_dmr, medi_id, cetr_id) 
SELECT cote_id, (SELECT MAX(AUDI_ID) FROM MODRED.AUDITORIA), '0', cote_nr_cod_area, cote_nr_cod_inter, cote_tx_complemento, cote_tx_nome, cote_nr_numero,cote_in_principal, cote_in_tipo, paci_nr_rmr, cote_in_excluido, doad_nr_dmr, medi_id, cetr_id
FROM MODRED.CONTATO_TELEFONICO where cote_id = (select max(ec.cote_id) from MODRED.CONTATO_TELEFONICO ec);

INSERT INTO MODRED.CONTATO_TELEFONICO (cote_id, cote_in_tipo, cote_nr_cod_inter, cote_nr_cod_area, cote_nr_numero, cote_tx_nome, cetr_id ) VALUES ( MODRED.SQ_COTE_ID.NEXTVAL, 1, '55','31','32388904','Wellington Azevedo',34);
INSERT INTO MODRED.AUDITORIA(AUDI_ID,AUDI_DT_DATA,AUDI_TX_USUARIO) values(MODRED.SQ_AUDI_ID.NEXTVAL,sysdate,'SISTEMA');
INSERT INTO MODRED.CONTATO_TELEFONICO_AUD ( cote_id, audi_id, audi_tx_tipo, cote_nr_cod_area, cote_nr_cod_inter, cote_tx_complemento, cote_tx_nome, cote_nr_numero, cote_in_principal, cote_in_tipo, paci_nr_rmr, cote_in_excluido, doad_nr_dmr, medi_id, cetr_id) 
SELECT cote_id, (SELECT MAX(AUDI_ID) FROM MODRED.AUDITORIA), '0', cote_nr_cod_area, cote_nr_cod_inter, cote_tx_complemento, cote_tx_nome, cote_nr_numero,cote_in_principal, cote_in_tipo, paci_nr_rmr, cote_in_excluido, doad_nr_dmr, medi_id, cetr_id
FROM MODRED.CONTATO_TELEFONICO where cote_id = (select max(ec.cote_id) from MODRED.CONTATO_TELEFONICO ec);
commit;