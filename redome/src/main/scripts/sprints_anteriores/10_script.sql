--Cria um registro de avaliacao para os pacientes antigos que sao nao aparentados
insert into modred.avaliacao (AVAL_ID,PACI_NR_RMR,AVAL_DT_CRIACAO,AVAL_IN_RESULTADO,MEDI_ID_AVALIADOR,AVAL_TX_OBSERVACAO,AVAL_DT_RESULTADO,CETR_ID,AVAL_IN_STATUS)
select SQ_AVAL_ID.nextval, t1.* 
from (select rmr, dtCriacao, resultado, medicoAvaliador,obs,dtResultado,centroAvaliador,status from (
    select
    paciente.paci_nr_rmr as rmr,
    current_date as dtCriacao,
    null as resultado,
    null as medicoAvaliador,
    null as obs,
    null as dtResultado,
    (CASE WHEN paciente.cetr_id_avaliador is null THEN 2 ELSE paciente.cetr_id_avaliador END) as centroAvaliador,
    1 as status
    from modred.paciente where paci_nr_rmr not in (select paci_nr_rmr from avaliacao) and paci_in_motivo_cadastro = 1)) t1;


commit;

/* 11/09 - Alterando restrição da permissão para 1 (TRUE)  */
UPDATE MODRED.PERMISSAO SET PERM_IN_COM_RESTRICAO = 1 WHERE RECU_ID = 9;

--Recurso para o médico visualizar a avaliação do paciente
INSERT INTO MODRED.RECURSO VALUES (11, 'VISUALIZAR_AVALIACAO', 'Permite o médico acessar tela de avaliação do paciente.');
INSERT INTO MODRED.PERMISSAO VALUES (11, 1, 0);

-- Recurso para o médico que possui permissão para atualizar a avaliação do paciente (avaliador associado a avaliação que vai sofrer a alteração).
INSERT INTO MODRED.RECURSO VALUES (12, 'ATUALIZAR_AVALIACAO_PACIENTE', 'Permite ao médico que possui permissão para atualizar a avaliação do paciente.');
INSERT INTO MODRED.PERMISSAO VALUES (12, 2, 1);

COMMIT;

--Coloca o recurso VISUALIZAR_AVALIACAO com permissão apenas para visualizar as avaliações dos pacientes que o medico atende
update permissao set perm_in_com_restricao = 1 where recu_id = 11;

-- Retirando associação de RECURSOS incorretos
DELETE FROM MODRED.PERMISSAO WHERE RECU_ID = 5 AND PERF_ID = 1;
DELETE FROM MODRED.PERMISSAO WHERE RECU_ID = 4 AND PERF_ID = 1;

COMMIT;

-- Atualizando permissões dos perfis

UPDATE MODRED.RECURSO SET RECU_TX_DESCRICAO='Permite visualizar a avaliação do paciente.' WHERE RECU_ID = 11;

DELETE FROM MODRED.PERMISSAO WHERE RECU_ID=12;
DELETE FROM MODRED.RECURSO WHERE RECU_ID=12;

INSERT INTO MODRED.PERMISSAO VALUES(5, 1, 1);
INSERT INTO MODRED.PERMISSAO VALUES(4, 1, 1);

commit;