create or replace procedure        proc_criar_doador (dmr in NUMBER,cpf in VARCHAR2,
nome	in	VARCHAR2,
nomeMae	in VARCHAR2,
sexo	in	VARCHAR2,
racaId		in		NUMBER,
etniaId		in		NUMBER,
abo		in	VARCHAR2,
paisId		in		NUMBER,
ufSigla		in	VARCHAR2,
usuarioId		in		NUMBER,
dtCadastro	in TIMESTAMP,
dtAtualizacao	in TIMESTAMP,
dtNascimento in	DATE,
statusDoadorId		in		NUMBER,
tempoInativo in	NUMBER,
motivoStatusId		in		NUMBER,
nomeSocial	in VARCHAR2,
laboId		in		NUMBER,
doadTipo in NUMBER,
regiId in NUMBER,
doadId in NUMBER)

is

 AUDI_ID NUMBER(10,0);
 PESO NUMBER(4,1);

begin

    SELECT MODRED.SQ_AUDI_ID.NEXTVAL INTO AUDI_ID FROM dual;
    PESO := round(dbms_random.value(60.0,149.9), 1);

    insert into MODRED.doador(DOAD_NR_DMR,DOAD_TX_CPF,DOAD_TX_NOME,DOAD_TX_NOME_MAE,DOAD_IN_SEXO,
        RACA_ID,ETNI_ID,DOAD_TX_ABO,PAIS_ID,UF_SIGLA,USUA_ID,DOAD_DT_CADASTRO, DOAD_VL_PESO,
        DOAD_DT_ATUALIZACAO,DOAD_DT_NASCIMENTO,STDO_ID,MOSD_ID, DOAD_TX_NOME_SOCIAL, LABO_ID, DOAD_IN_TIPO, REGI_ID_ORIGEM, DOAD_ID)
    values (
        dmr,cpf,nome,nomeMae,sexo,racaId, etniaId,abo,paisId,ufSigla,usuarioId,dtCadastro,PESO,dtAtualizacao,
        dtNascimento,statusDoadorId,motivoStatusId,nomeSocial, laboId, doadTipo, regiId, doadId
    );

    insert into modred.auditoria(AUDI_ID,AUDI_DT_DATA,AUDI_TX_USUARIO) values(AUDI_ID,sysdate,'SISTEMA');

    insert into MODRED.doador_aud(DOAD_NR_DMR,AUDI_ID,AUDI_TX_TIPO,DOAD_TX_CPF,DOAD_TX_NOME,DOAD_TX_NOME_MAE,DOAD_IN_SEXO,
    RACA_ID,ETNI_ID,DOAD_TX_ABO,PAIS_ID,UF_SIGLA,USUA_ID,DOAD_DT_CADASTRO,DOAD_VL_PESO,
    DOAD_DT_ATUALIZACAO,DOAD_DT_NASCIMENTO,STDO_ID,MOSD_ID, DOAD_TX_NOME_SOCIAL, LABO_ID, DOAD_IN_TIPO, REGI_ID_ORIGEM, DOAD_ID )
    values (
    dmr,AUDI_ID,0,
    cpf,nome,nomeMae,sexo,racaId, etniaId,abo,paisId,ufSigla,usuarioId,dtCadastro,PESO,dtAtualizacao,
    dtNascimento,statusDoadorId,motivoStatusId,nomeSocial, laboId, doadTipo, regiId, doadId
    );


end;