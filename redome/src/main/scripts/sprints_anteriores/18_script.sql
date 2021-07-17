--Filipe Paes - alteração na tabela de tentativa de contato telefonico 03/01/18.
ALTER TABLE MODRED.TENTATIVA_CONTATO_DOADOR 
ADD (COTE_ID NUMBER );

CREATE INDEX IN_FK_TECD_COTE ON MODRED.TENTATIVA_CONTATO_DOADOR (COTE_ID ASC);

ALTER TABLE MODRED.TENTATIVA_CONTATO_DOADOR
ADD CONSTRAINT FK_TECD_COTE FOREIGN KEY
(
  COTE_ID 
)
REFERENCES MODRED.CONTATO_TELEFONICO
(
  COTE_ID 
)
ENABLE;

COMMENT ON COLUMN MODRED.TENTATIVA_CONTATO_DOADOR.COTE_ID IS 'Referencia ao contato telefonico doador para agendamento ';


ALTER TABLE MODRED.TENTATIVA_CONTATO_DOADOR  
MODIFY (TECD_DT_CRIACAO NULL);

ALTER TABLE MODRED.TENTATIVA_CONTATO_DOADOR  
MODIFY (USUA_ID NULL);

ALTER TABLE MODRED.TENTATIVA_CONTATO_DOADOR  
MODIFY (RETC_ID NULL);


ALTER TABLE MODRED.TENTATIVA_CONTATO_DOADOR 
ADD (TECD_DT_HORA_RETORNO TIMESTAMP );

COMMENT ON COLUMN MODRED.TENTATIVA_CONTATO_DOADOR.TECD_DT_HORA_RETORNO IS 'Data e hora do retorno de agendamento';

-- Fillipe QUeiroz inserindo motivos de cancelamento de workup 03/01/2018
INSERT INTO MODRED.MOTIVO_CANCELAMENTO_WORKUP (MOCW_ID,MOCW_DESCRICAO) VALUES (1,'CT NÃO RESPONDE DISPONIBILIDADE');
INSERT INTO MODRED.MOTIVO_CANCELAMENTO_WORKUP (MOCW_ID,MOCW_DESCRICAO) VALUES (2,'PRESCRIÇÃO EXPIRADA');
INSERT INTO MODRED.MOTIVO_CANCELAMENTO_WORKUP (MOCW_ID,MOCW_DESCRICAO) VALUES (3,'SEM DATAS DISPONÍVEIS');
-- FIM
 
--Ajustes nos scripts da sprint passada Cintia 03/01

--renomeando coluna
ALTER TABLE MODRED.PRESCRICAO RENAME COLUMN PRES_DT_CRICAO TO PRES_DT_CRIACAO;

ALTER TABLE MODRED.PRESCRICAO  
MODIFY (PRES_DT_CRIACAO TIMESTAMP );

--removendo coluna de endereco
ALTER TABLE MODRED.HEMO_ENTIDADE 
DROP CONSTRAINT FK_HEEN_ENCO;

ALTER TABLE MODRED.HEMO_ENTIDADE 
DROP COLUMN ENCO_ID;

--alterando o tipo das colunas de data
ALTER TABLE MODRED.DISPONIBILIDADE  
MODIFY (DISP_DT_RESULTADO_WORKUP DATE );

ALTER TABLE MODRED.DISPONIBILIDADE  
MODIFY (DISP_DT_COLETA DATE );

-- criando foreign keys
CREATE INDEX IN_FK_FUCT_CETR ON MODRED.FUNCAO_CENTRO_TRANSPLANTE (CETR_ID);
CREATE INDEX IN_FK_FUCT_FUTR ON MODRED.FUNCAO_CENTRO_TRANSPLANTE (FUTR_ID);

ALTER TABLE MODRED.FUNCAO_CENTRO_TRANSPLANTE
ADD CONSTRAINT FK_FUCT_CETR FOREIGN KEY
(
  CETR_ID 
)
REFERENCES MODRED.CENTRO_TRANSPLANTE
(
  CETR_ID 
)
ENABLE;

ALTER TABLE MODRED.FUNCAO_CENTRO_TRANSPLANTE
ADD CONSTRAINT FK_FUCT_FUTR FOREIGN KEY
(
  FUTR_ID 
)
REFERENCES MODRED.FUNCAO_TRANSPLANTE
(
  FUTR_ID 
)
ENABLE;

--renomeando tabela INFO_PREVIA para DISPONIBILIDADE_CENTRO_COLETA
drop table "MODRED"."INFO_PREVIA" cascade constraints PURGE;
drop SEQUENCE MODRED.SQ_INPR_ID;

CREATE TABLE MODRED.DISPONIBILIDADE_CENTRO_COLETA 
(
  DICC_ID NUMBER NOT NULL 
, PEWO_ID NUMBER NOT NULL 
, DICC_NR_INFO_CORRENTE NUMBER NOT NULL 
, DICC_TX_DESCRICAO VARCHAR2(80) NOT NULL 
, CONSTRAINT PK_DICC PRIMARY KEY 
  (
    DICC_ID 
  )
  USING INDEX 
  (
      CREATE UNIQUE INDEX PK_DICC ON MODRED.DISPONIBILIDADE_CENTRO_COLETA (DICC_ID ASC) 
  )
  ENABLE 
);

CREATE INDEX IN_FK_DICC_PEWO ON MODRED.DISPONIBILIDADE_CENTRO_COLETA (PEWO_ID);

ALTER TABLE MODRED.DISPONIBILIDADE_CENTRO_COLETA
ADD CONSTRAINT FK_DICC_PEWO FOREIGN KEY
(
  PEWO_ID 
)
REFERENCES MODRED.PEDIDO_WORKUP
(
  PEWO_ID 
)
ENABLE;

COMMENT ON TABLE MODRED.DISPONIBILIDADE_CENTRO_COLETA IS 'Tabela que vai armazenar as informações de possíveis datas disponíveis de um centro de transplante.';

COMMENT ON COLUMN MODRED.DISPONIBILIDADE_CENTRO_COLETA.DICC_ID IS 'Chave primária da tabela de info previa.';

COMMENT ON COLUMN MODRED.DISPONIBILIDADE_CENTRO_COLETA.PEWO_ID IS 'Chave estrangeira da tabela de pedido_workup';

COMMENT ON COLUMN MODRED.DISPONIBILIDADE_CENTRO_COLETA.DICC_NR_INFO_CORRENTE IS 'Número referente ao grupo de informações prévias de um pedido de workup.';

COMMENT ON COLUMN MODRED.DISPONIBILIDADE_CENTRO_COLETA.DICC_TX_DESCRICAO IS 'Descrição da info previa';

CREATE SEQUENCE MODRED.SQ_DICC_ID INCREMENT BY 1 START WITH 1 MAXVALUE 9999999999999999999999999999 MINVALUE 1;

--alteração no ajuste de fase 3
DELETE FROM MODRED.FORMULARIO WHERE FORM_ID = 2;

INSERT INTO MODRED.FORMULARIO VALUES (2, 2, 1, '
[
    { "id": "MotivoCadastramento", "descricao": "O que motivou o cadastramento no REDOME?", "tipo": "CHECKBUTTON",
      "opcoes": [ { "descricao": "Já sou doador de orgãos e tecidos", "valor": "DOADOR" },
                  { "descricao": "Doença na família", "valor": "DOENCA" }, 
                  { "descricao": "Curiosidade/Necessidade", "valor": "CURIOSIDADE" }, 
                  { "descricao": "Solidariedade", "valor": "SOLIDARIEDADE" }, 
                  { "descricao": "Outros", "valor": "OUTROS" } ]
    },
    { "id": "ConhecimentoPrograma", "descricao": "Como tomou conhecimento do programa de doação de medula óssea?", "tipo": "CHECKBUTTON",
      "opcoes": [ { "descricao": "Mídia", "valor": "MIDIA" },
                  { "descricao": "Campanha", "valor": "CAMPANHA" }, 
                  { "descricao": "Amigos e familiares", "valor": "AMIGOS" }, 
                  { "descricao": "Outros", "valor": "OUTROS" } ]
    },
    { "id": "SentimentoDoador", "descricao": "Qual é seu sentimento com a possibilidade de se tornar um doador?", "tipo": "CHECKBUTTON",
      "opcoes": [ { "descricao": "Felicidade", "valor": "FELICIDADE" },
                  { "descricao": "Medo", "valor": "MEDO" }, 
                  { "descricao": "Vontade de ajudar o próximo", "valor": "VONTADE" }]
    },
    { "id": "Confidencialidade", "descricao": "Está ciente da confidencialidade no processo de doação de CTH?", "tipo": "RADIOBUTTON",
      "opcoes": [ { "descricao": "Sim", "valor": "S" }, { "descricao": "Não", "valor": "N" } ]
    },
    { "id": "Anonimato", "descricao": "Qual é a sua opinião sobre o anonimato entre você e a pessoa para quem possivelmente faria a doação de MO?", "tipo": "RADIOBUTTON",
      "opcoes": [ { "descricao": "Concordo", "valor": "CONCORDO" },
                  { "descricao": "Não Concordo", "valor": "NAOCONCORDO" }, 
                  { "descricao": "Indiferente", "valor": "INDIFERENTE" } ]
    },
    { "id": "MomentoCadastro", "descricao": "Seu cadastro no REDOME foi realizado em qual momento?", "tipo": "CHECKBUTTON",
      "opcoes": [ { "descricao": "Durante uma campanha direcionada para um paciente", "valor": "CAMPANHAPACIENTE" },
                  { "descricao": "Doação de sangue", "valor": "DOACAOSANGUE" }, 
                  { "descricao": "Voluntariamente no Hemocantro solicitando o cadastro para o REDOME", "valor": "VOLUNTARIAMENTE" }, 
                  { "descricao": "Campanha do Hemocentro", "valor": "CAMPANHAHEMOCENTRO" }, 
                  { "descricao": "NA", "valor": "NA" } ]
    },
    { "id": "Duvidas", "descricao": "Você tem alguma dúvida sobre o processo de doação de Medula Óssea?", "tipo": "RADIOBUTTON",
      "opcoes": [ { "descricao": "Sim", "valor": "S" }, { "descricao": "Não", "valor": "N" } ]
    },
    { "id": "PreferenciaColeta", "descricao": "O doador informou preferência em realizar a coleta por:", "tipo": "RADIOBUTTON",
      "opcoes": [ { "descricao": "Afêrese", "valor": "AFERESE" }, { "descricao": "Punção", "valor": "PUNCAO" } ]
    },
    { "id": "Prosseguir", "descricao": "Deseja seguir com o processo de doação de CTH?", "tipo": "RADIOBUTTON",
      "opcoes": [ { "descricao": "Sim", "valor": "S" }, { "descricao": "Não", "valor": "N" } ]
    },
    { "id": "Religiao", "descricao": "Possui alguma religião?", "tipo": "RADIOBUTTON",
      "valorDefault" : "<1.Religiao>", 
      "opcoes": [ { "descricao": "Sim", "valor": "S" }, { "descricao": "Não", "valor": "N" } ],
      "dependentes": [ { "idPergunta": "QualReligiao", "valor": "S" }, { "idPergunta": "RestricaoReligiao", "valor": "S" } ]
    },
    { "id": "QualReligiao", "descricao": "Qual religião?", "tipo": "TEXT",
      "valorDefault" : "<1.QualReligiao>", 
      "possuiDependencia": "true",
      "tamanho": 100
    },'
);

UPDATE MODRED.FORMULARIO SET FORM_TX_FORMATO = FORM_TX_FORMATO || '
    { "id": "RestricaoReligiao", "descricao": "Essa religião restringe futuras doações?", "tipo": "RADIOBUTTON",
      "valorDefault" : "<1.RestricaoReligiao>", 
      "possuiDependencia": "true",
      "opcoes": [ { "descricao": "Sim", "valor": "S" }, { "descricao": "Não", "valor": "N" } ]
    },
    { "id": "AtividadeFisica", "descricao": "Pratica alguma atividade física?", "tipo": "RADIOBUTTON",
      "opcoes": [ { "descricao": "Sim", "valor": "S" }, { "descricao": "Não", "valor": "N" } ],
      "dependentes": [ { "idPergunta": "QualAtividadeFisica", "valor": "S" } ]
    },
    { "id": "QualAtividadeFisica", "descricao": "Qual?", "tipo": "TEXT",
      "possuiDependencia": "true",
      "tamanho": 100
    },
    { "id": "DoadorSangue", "descricao": "Doador de sangue?", "tipo": "RADIOBUTTON",
      "valorDefault" : "<1.DoadorSangue>", 
      "opcoes": [ { "descricao": "Sim", "valor": "S" }, { "descricao": "Não", "valor": "N" } ],
      "dependentes": [ { "idPergunta": "UltimaDoacao", "valor": "S" } ]
    },
    { "id": "UltimaDoacao", "descricao": "Última doação de sangue?", "tipo": "RADIOBUTTON",
      "valorDefault" : "<1.UltimaDoacao>", 
      "possuiDependencia": "true",
      "opcoes": [ { "descricao": "Seis meses a um ano", "valor": "A" }, { "descricao": "Mais de um ano", "valor": "B" } ]
    },
    { "id": "Cancer", "descricao": "Já teve algum tipo de câncer?", "tipo": "RADIOBUTTON",
      "valorDefault" : "<1.Cancer>", 
      "opcoes": [ { "descricao": "Sim", "valor": "S" }, { "descricao": "Não", "valor": "N" } ],
      "dependentes": [ { "idPergunta": "QuaisCancer", "valor": "S" } ]
    },
    { "id": "QuaisCancer", "descricao": "Informe quais, indicando o ano de ocorrência e o tratamento para cada um:", "tipo": "TEXTAREA",
      "valorDefault" : "<1.QuaisCancer>", 
      "possuiDependencia": "true",
      "tamanho": 4000
    },
    { "id": "Hepatite", "descricao": "Já teve algum tipo de hepatite?", "tipo": "RADIOBUTTON",
      "valorDefault" : "<1.Hepatite>", 
      "opcoes": [ { "descricao": "Sim", "valor": "S" }, { "descricao": "Não", "valor": "N" } ],
      "dependentes": [ { "idPergunta": "QuaisHepatite", "valor": "S" } ]
    },
    { "id": "QuaisHepatite", "descricao": "Informe quais tipos, indicando o ano de ocorrência de cada um:", "tipo": "TEXTAREA",
      "valorDefault" : "<1.QuaisHepatite>", 
      "possuiDependencia": "true",
      "tamanho": 4000
    },
    { "id": "Infeccao", "descricao": "Já teve algum tipo de infecção?", "tipo": "RADIOBUTTON",
      "valorDefault" : "<1.Infeccao>", 
      "opcoes": [ { "descricao": "Sim", "valor": "S" }, { "descricao": "Não", "valor": "N" } ],
      "dependentes": [ { "idPergunta": "QuaisInfeccao", "valor": "S" } ]
    },
    { "id": "QuaisInfeccao", "descricao": "Informe quais, indicando o ano de ocorrência de cada uma:", "tipo": "TEXTAREA",
      "valorDefault" : "<1.QuaisInfeccao>", 
      "possuiDependencia": "true",
      "tamanho": 4000
    },
    { "id": "DST", "descricao": "Já teve alguma DST (Doença Sexualmente Transmissível)? (HIV, HTLV, Sífilis, Herpes ou outras)", "tipo": "RADIOBUTTON",
      "valorDefault" : "<1.DST>", 
      "opcoes": [ { "descricao": "Sim", "valor": "S" }, { "descricao": "Não", "valor": "N" } ],
      "dependentes": [ { "idPergunta": "QuaisDST", "valor": "S" } ]
    },
    { "id": "QuaisDST", "descricao": "Quais?", "tipo": "CHECKBUTTON",
      "valorDefault" : "<1.QuaisDST>", 
      "opcoes": [ { "descricao": "HIV", "valor": "HIV" }, { "descricao": "HTLV", "valor": "HTLV" }, { "descricao": "Sífilis", "valor": "SIFILIS" }, { "descricao": "Herpes", "valor": "HERPES" }, { "descricao": "Outra(s)", "valor": "OUTRAS" } ],
      "possuiDependencia": "true",
	  "dependentes": [ { "idPergunta": "OutrasDST", "valor": "OUTRAS" } ]
    },'
WHERE FORM_ID = 2;

UPDATE MODRED.FORMULARIO SET FORM_TX_FORMATO = FORM_TX_FORMATO || '
    { "id": "OutrasDST", "descricao": "Informe quais outras, indicando o ano e o tratamento de cada uma:", "tipo": "TEXTAREA",
      "valorDefault" : "<1.OutrasDST>", 
      "possuiDependencia": "true",
      "tamanho": 4000
    },
    { "id": "Alergia", "descricao": "Já teve alguma alergia?", "tipo": "RADIOBUTTON",
      "valorDefault" : "<1.Alergia>", 
      "opcoes": [ { "descricao": "Sim", "valor": "S" }, { "descricao": "Não", "valor": "N" } ],
      "dependentes": [ { "idPergunta": "QuaisAlergia", "valor": "S" } ]
    },
    { "id": "QuaisAlergia", "descricao": "Informe quais alergias:", "tipo": "TEXTAREA",
      "valorDefault" : "<1.QuaisAlergia>", 
      "possuiDependencia": "true",
      "tamanho": 4000
    },
    { "id": "Gestacao", "descricao": "Já teve gestações?", "tipo": "RADIOBUTTON",
      "valorDefault" : "<1.Gestacao>", 
      "opcoes": [ { "descricao": "Sim", "valor": "S" }, { "descricao": "Não", "valor": "N" } ],
      "dependentes": [ { "idPergunta": "TipoGestacao", "valor": "S" } ]
    },
    { "id": "TipoGestacao", "descricao": "Gestações de que tipos?", "tipo": "CHECKBUTTON",
      "valorDefault" : "<1.TipoGestacao>", 
      "possuiDependencia": "true",
      "opcoes": [ { "descricao": "Parto Normal", "valor": "NORMAL" }, { "descricao": "Parto Cesária", "valor": "CESARIA" } ],
      "dependentes": [ { "idPergunta": "GestacaoNormal", "valor": "NORMAL" }, { "idPergunta": "GestacaoCesaria", "valor": "CESARIA" } ]
    },
    { "id": "GestacaoNormal", "descricao": "Informe os anos e quantidade de partos normais:", "tipo": "TEXTAREA",
      "valorDefault" : "<1.GestacaoNormal>", 
      "possuiDependencia": "true",
      "tamanho": 4000
    },
    { "id": "GestacaoCesaria", "descricao": "Informe os anos e quantidade de partos Cesária:", "tipo": "TEXTAREA",
      "valorDefault" : "<1.GestacaoCesaria>", 
      "possuiDependencia": "true",
      "tamanho": 4000
    },
    { "id": "Aborto", "descricao": "Já teve algum aborto?", "tipo": "RADIOBUTTON",
      "valorDefault" : "<1.Aborto>", 
      "opcoes": [ { "descricao": "Sim", "valor": "S" }, { "descricao": "Não", "valor": "N" } ],
      "dependentes": [ { "idPergunta": "QuaisAborto", "valor": "S" } ]
    },
    { "id": "QuaisAborto", "descricao": "Informe os anos e a causa dos abortos:", "tipo": "TEXTAREA",
      "valorDefault" : "<1.QuaisAborto>", 
      "possuiDependencia": "true",
      "tamanho": 4000
    },
    { "id": "Cirurgia", "descricao": "Já realizou alguma cirurgia?", "tipo": "RADIOBUTTON",
      "valorDefault" : "<1.Cirurgia>", 
      "opcoes": [ { "descricao": "Sim", "valor": "S" }, { "descricao": "Não", "valor": "N" } ],
      "dependentes": [ { "idPergunta": "QuaisCirurgia", "valor": "S" } ]
    },
    { "id": "QuaisCirurgia", "descricao": "Informe quais cirurgias e o respectivo ano:", "tipo": "TEXTAREA",
      "valorDefault" : "<1.QuaisCirurgia>", 
      "possuiDependencia": "true",
      "tamanho": 4000
    },
    { "id": "Tatuagem", "descricao": "Já fez alguma tatuagem ou maquiagem definitiva?", "tipo": "RADIOBUTTON",
      "valorDefault" : "<1.Tatuagem>", 
      "opcoes": [ { "descricao": "Sim", "valor": "S" }, { "descricao": "Não", "valor": "N" } ],
      "dependentes": [ { "idPergunta": "UltimaTatuagem", "valor": "S" } ]
    },
    { "id": "UltimaTatuagem", "descricao": "Em que ano foi a última?", "tipo": "TEXT",
      "valorDefault" : "<1.UltimaTatuagem>", 
      "possuiDependencia": "true",
      "tamanho": 10
    },
    { "id": "Transfusao", "descricao": "Já fez alguma transfusão de sangue, plaquetas, plasma ou outros?", "tipo": "RADIOBUTTON",
      "valorDefault" : "<1.Transfusao>", 
      "opcoes": [ { "descricao": "Sim", "valor": "S" }, { "descricao": "Não", "valor": "N" } ],
      "dependentes": [ { "idPergunta": "QuaisTransfusao", "valor": "S" } ]
    },'
WHERE FORM_ID = 2;

UPDATE MODRED.FORMULARIO SET FORM_TX_FORMATO = FORM_TX_FORMATO || '
    { "id": "QuaisTransfusao", "descricao": "Informe quais transfusões e o respectivo ano e motivo:", "tipo": "TEXTAREA",
      "valorDefault" : "<1.QuaisTransfusao>", 
      "possuiDependencia": "true",
      "tamanho": 4000
    },
    { "id": "Medicamento", "descricao": "Faz uso de algum medicamento?", "tipo": "RADIOBUTTON",
      "valorDefault" : "<1.Medicamento>", 
      "opcoes": [ { "descricao": "Sim", "valor": "S" }, { "descricao": "Não", "valor": "N" } ],
      "dependentes": [ { "idPergunta": "QuaisMedicamento", "valor": "S" } ]
    },
    { "id": "QuaisMedicamento", "descricao": "Informe quais medicamentos faz uso:", "tipo": "TEXTAREA",
      "valorDefault" : "<1.QuaisMedicamento>", 
      "possuiDependencia": "true",
      "tamanho": 4000
    },
    { "id": "Tratamento", "descricao": "Já fez algum tratamento de saúde (colonoscopia/rinoscopia/endoscopia)?", "tipo": "RADIOBUTTON",
      "valorDefault" : "<1.Tratamento>", 
      "opcoes": [ { "descricao": "Sim", "valor": "S" }, { "descricao": "Não", "valor": "N" } ],
      "dependentes": [ { "idPergunta": "QuaisTratamento", "valor": "S" } ]
    },
    { "id": "QuaisTratamento", "descricao": "Informe quais tratamentos e o respectivo ano:", "tipo": "TEXTAREA",
      "valorDefault" : "<1.QuaisTratamento>", 
      "possuiDependencia": "true",
      "tamanho": 4000
    },
    { "id": "Vacinas", "descricao": "Tomou vacinas recentemente?", "tipo": "RADIOBUTTON",
      "opcoes": [ { "descricao": "Sim", "valor": "S" }, { "descricao": "Não", "valor": "N" } ],
      "dependentes": [ { "idPergunta": "QuaisVacinas", "valor": "S" } ]
    },
    { "id": "QuaisVacinas", "descricao": "Informe quais vacinas e a respectiva data:", "tipo": "TEXTAREA",
      "possuiDependencia": "true",
      "tamanho": 4000
    },
    { "id": "Viagens", "descricao": "Viajou recentemente para áreas endêmicas (malária/febre amarela)?", "tipo": "RADIOBUTTON",
      "opcoes": [ { "descricao": "Sim", "valor": "S" }, { "descricao": "Não", "valor": "N" } ],
      "dependentes": [ { "idPergunta": "QuaisViagens", "valor": "S" } ]
    },
    { "id": "QuaisViagens", "descricao": "Informe quais destinos o respectivo ano das viagens:", "tipo": "TEXTAREA",
      "possuiDependencia": "true",
      "tamanho": 4000
    },
    { "id": "DisponibilidadeViagem", "descricao": "Tem disponibilidade para viajar no ato da coleta de Medula Óssea caso não possa ser em seu estado?", "tipo": "RADIOBUTTON",
      "valorDefault" : "<1.DisponibilidadeViagem>", 
      "opcoes": [ { "descricao": "Sim", "valor": "S" }, { "descricao": "Não", "valor": "N" } ],
      "dependentes": [ { "idPergunta": "JustificativaViagem", "valor": "OUTROS" } ]
    },
    { "id": "JustificativaViagem", "descricao": "Qual o motivo da impossibilidade?", "tipo": "TEXTAREA",
      "valorDefault" : "<1.JustificativaViagem>", 
      "possuiDependencia": "true",
      "tamanho": 4000
    },'
WHERE FORM_ID = 2;

UPDATE MODRED.FORMULARIO SET FORM_TX_FORMATO = FORM_TX_FORMATO || '
    { "id": "FormaContato", "descricao": "Qual a melhor forma de contato?", "tipo": "CHECKBUTTON",
      "valorDefault" : "<1.FormaContato>", 
      "opcoes": [ { "descricao": "SMS", "valor": "SMS" }, { "descricao": "Telegrama", "valor": "TELEGRAMA" }, { "descricao": "E-Mail", "valor": "EMAIL" }, { "descricao": "Telefone", "valor": "TELEFONE" }, { "descricao": "Indiferente", "valor": "Indiferente" }, { "descricao": "Outros", "valor": "OUTROS" } ],
      "dependentes": [ { "idPergunta": "OutrosContato", "valor": "OUTROS" } ]
    },
    { "id": "OutrosContato", "descricao": "Quais outro tipo de contato:", "tipo": "TEXTAREA",
      "valorDefault" : "<1.OutrosContato>", 
      "possuiDependencia": "true",
      "tamanho": 4000
    },
    { "id": "PeriodoContato", "descricao": "Qual a melhor período para contato?", "tipo": "CHECKBUTTON",
      "valorDefault" : "<1.PeriodoContato>", 
      "opcoes": [ { "descricao": "Manhã", "valor": "M" }, { "descricao": "Tarde", "valor": "T" }, { "descricao": "Noite", "valor": "N" } ]
    }
  ]'
WHERE FORM_ID = 2;

COMMIT;
-- FIM

-- RECURSO LISTA PENDENCIAS DE WORKUP
INSERT INTO MODRED.RECURSO (RECU_ID, RECU_TX_SIGLA, RECU_TX_DESCRICAO) 
VALUES (39, 'VISUALIZAR_PENDENCIA_WORKUP', 'Permite visualizar a listagem de trabalho com pendências de workup para Centro Transplante.');

INSERT INTO MODRED.PERMISSAO (RECU_ID, PERF_ID, PERM_IN_COM_RESTRICAO) VALUES (39, 1, 1);
COMMIT;
-- FIM / RECURSO


update MODRED.TENTATIVA_CONTATO_DOADOR set TECD_DT_CRIACAO = sysdate;
commit;

ALTER TABLE MODRED.TENTATIVA_CONTATO_DOADOR 
ADD (TECD_DT_AGENDAMENTO TIMESTAMP );

ALTER TABLE MODRED.TENTATIVA_CONTATO_DOADOR 
ADD (TECD_HR_INCIO_AGENDAMENTO TIMESTAMP );

ALTER TABLE MODRED.TENTATIVA_CONTATO_DOADOR 
ADD (TECD_HR_FIM_AGENDAMENTO TIMESTAMP );

ALTER TABLE MODRED.TENTATIVA_CONTATO_DOADOR RENAME COLUMN TECD_DT_HORA_RETORNO TO TECD_DT_REALIZACAO_CONTATO;

ALTER TABLE MODRED.TENTATIVA_CONTATO_DOADOR  
MODIFY (TECD_DT_CRIACAO NOT NULL);

COMMENT ON COLUMN MODRED.TENTATIVA_CONTATO_DOADOR.TECD_DT_REALIZACAO_CONTATO IS 'Data de realização do contato';

COMMENT ON COLUMN MODRED.TENTATIVA_CONTATO_DOADOR.TECD_DT_AGENDAMENTO IS 'Data do agendamento';

COMMENT ON COLUMN MODRED.TENTATIVA_CONTATO_DOADOR.TECD_HR_INCIO_AGENDAMENTO IS 'Hora de início do período do agendamento';

COMMENT ON COLUMN MODRED.TENTATIVA_CONTATO_DOADOR.TECD_HR_FIM_AGENDAMENTO IS 'Hora final do período do agendamento';

--Alteração no modelo de dado para possibilitar a criação de mais de um pedido de workup para uma mesma prescrição Cintia 04/01
--deletando possiveis registros
DELETE FROM MODRED.PRESCRICAO;
DELETE FROM MODRED.PEDIDO_WORKUP;
DELETE FROM MODRED.SOLICITACAO;

--Alteração na tabela de PRESCRICAO
ALTER TABLE MODRED.PRESCRICAO 
DROP CONSTRAINT FK_PRESC_DOAD;

ALTER TABLE MODRED.PRESCRICAO 
DROP CONSTRAINT FK_PRESC_PEWO;

ALTER TABLE MODRED.PRESCRICAO 
DROP CONSTRAINT FK_PRESC_USUA;

ALTER TABLE MODRED.PRESCRICAO 
DROP CONSTRAINT PK_PRESC;

ALTER TABLE MODRED.PRESCRICAO 
DROP COLUMN PEWO_ID;

ALTER TABLE MODRED.PRESCRICAO 
DROP COLUMN DOAD_NR_DMR;

ALTER TABLE MODRED.PRESCRICAO 
DROP COLUMN PRES_DT_CRIACAO;

ALTER TABLE MODRED.PRESCRICAO 
DROP COLUMN USUA_ID;

DROP INDEX IN_FK_PRES_DOAD;

DROP INDEX IN_FK_PRES_USUA;

ALTER TABLE MODRED.PRESCRICAO 
ADD (SOLI_ID NUMBER NOT NULL);

ALTER TABLE MODRED.PRESCRICAO
ADD CONSTRAINT PK_PRES PRIMARY KEY 
(
  SOLI_ID 
)
ENABLE;

ALTER TABLE MODRED.PRESCRICAO
ADD CONSTRAINT FK_PRES_SOLI FOREIGN KEY
(
  SOLI_ID 
)
REFERENCES MODRED.SOLICITACAO
(
  SOLI_ID 
)
ENABLE;

--Alteração na tabela de SOLICITACAO
ALTER TABLE MODRED.SOLICITACAO 
ADD (USUA_ID NUMBER );

ALTER TABLE MODRED.SOLICITACAO 
ADD (SOLI_DT_CRIACAO TIMESTAMP );

ALTER TABLE MODRED.SOLICITACAO 
ADD (SOLI_NR_STATUS NUMBER );

COMMENT ON COLUMN MODRED.SOLICITACAO.PACI_NR_RMR IS 'Id do paciente';

COMMENT ON COLUMN MODRED.SOLICITACAO.USUA_ID IS 'Usuário que fez a solicitação';

COMMENT ON COLUMN MODRED.SOLICITACAO.SOLI_DT_CRIACAO IS 'Data de criação da solicitação';

COMMENT ON COLUMN MODRED.SOLICITACAO.SOLI_NR_STATUS IS 'Status da solicitação: 1-aberta, 2-concluída e 3-cancelada.';

UPDATE MODRED.SOLICITACAO SET SOLI_NR_STATUS=1;
UPDATE MODRED.SOLICITACAO SET SOLI_DT_CRIACAO=SYSDATE;

ALTER TABLE MODRED.SOLICITACAO 
MODIFY (SOLI_DT_CRIACAO NOT NULL);

ALTER TABLE MODRED.SOLICITACAO 
MODIFY (SOLI_NR_STATUS NUMBER NOT NULL);

--Alteração na tabela de PEDIDO_WORKUP
ALTER TABLE MODRED.PEDIDO_WORKUP 
MODIFY (PEWO_DT_CRIACAO TIMESTAMP NOT NULL);

ALTER TABLE MODRED.PEDIDO_WORKUP 
ADD (CETR_ID NUMBER );

ALTER TABLE MODRED.PEDIDO_WORKUP  
MODIFY (PEWO_DT_ULTIMO_STATUS TIMESTAMP );

CREATE INDEX IN_FK_PEWO_CETR ON MODRED.PEDIDO_WORKUP (CETR_ID ASC);

ALTER TABLE MODRED.PEDIDO_WORKUP
ADD CONSTRAINT FK_PEWO_CETR FOREIGN KEY
(
  CETR_ID 
)
REFERENCES MODRED.CENTRO_TRANSPLANTE
(
  CETR_ID 
)
ENABLE;

COMMENT ON COLUMN MODRED.PEDIDO_WORKUP.PEWO_DT_CRIACAO IS 'Data de criação do pedido de workup.';

COMMENT ON COLUMN MODRED.PEDIDO_WORKUP.CETR_ID IS 'Centro de transplante responsável pelo workup do doador.';

--Alteração na tabela de DISPONIBILIDADE_CENTRO_COLETA
ALTER TABLE MODRED.DISPONIBILIDADE_CENTRO_COLETA 
ADD (CETR_ID NUMBER NOT NULL);

CREATE INDEX IN_FK_DICC_CETR ON MODRED.DISPONIBILIDADE_CENTRO_COLETA (CETR_ID ASC);

ALTER TABLE MODRED.DISPONIBILIDADE_CENTRO_COLETA
ADD CONSTRAINT FK_DICC_CETR FOREIGN KEY
(
  CETR_ID 
)
REFERENCES MODRED.CENTRO_TRANSPLANTE
(
  CETR_ID 
)
ENABLE;

COMMENT ON COLUMN MODRED.DISPONIBILIDADE_CENTRO_COLETA.CETR_ID IS 'Centro de coleta a qual pertence essa disponibilidade.';

--FIM

--Queiroz Criando tipo de tarefa para o médico avaliar e sugerir uma data para o analista de workup
insert into modred.tipo_tarefa (tita_id, TITA_TX_DESCRICAO, tita_in_automatica,tita_nr_tempo_execucao) values(32,'SUGERIR_DATA_WORKUP',0,null);
--Fim
--Queiroz criando recurso para cancelamento de pedido workup
insert into modred.recurso (recu_id,recu_tx_sigla,recu_tx_descricao) values (40,'CANCELAR_PEDIDO_WORKUP','Permite o analista de workup cancelar um pedido de workup');
--FIM

--Adicionando coluna na tabela DISPONIBILIDADE Cintia 05/01
ALTER TABLE MODRED.DISPONIBILIDADE 
ADD (DISP_IN_CT_COLETA NUMBER(1) DEFAULT 0 NOT NULL);

COMMENT ON COLUMN MODRED.DISPONIBILIDADE.DISP_IN_CT_COLETA IS 'Indica se o Centro de transplante deseja fazer a coleta. 0-Não, 1-Sim.';
-- Fim

--Adicionando auditoria nas tabelas DISPONIBILIDADE e PEDIDO_WORKUP Cintia 05/01
CREATE TABLE MODRED.DISPONIBILIDADE_AUD 
(
  DISP_ID NUMBER(19, 0) NOT NULL 
, AUDI_ID NUMBER(19, 0) NOT NULL 
, AUDI_TX_TIPO NUMBER(3, 0) 
, DISP_IN_CT_COLETA NUMBER(1, 0) 
, DISP_DT_ACEITE TIMESTAMP(6) 
, DISP_DT_COLETA DATE 
, DISP_DT_CRIACAO TIMESTAMP(6) 
, DISP_DT_RESULTADO_WORKUP DATE 
, DISP_TX_DESCRICAO VARCHAR2(255 CHAR) 
, PEWO_ID NUMBER(19, 0) 
, CONSTRAINT PK_DISA PRIMARY KEY 
  (
    DISP_ID 
  , AUDI_ID 
  )
  USING INDEX 
  (
      CREATE UNIQUE INDEX PK_DISA ON MODRED.DISPONIBILIDADE_AUD (DISP_ID ASC, AUDI_ID ASC)
  )
  ENABLE); 


ALTER TABLE MODRED.DISPONIBILIDADE_AUD
ADD CONSTRAINT FK_DISA_AUDI FOREIGN KEY
(
  AUDI_ID 
)
REFERENCES MODRED.AUDITORIA
(
  AUDI_ID 
)
ENABLE;

CREATE INDEX IN_FK_DISA_AUDI ON MODRED.DISPONIBILIDADE_AUD (AUDI_ID);

COMMENT ON TABLE MODRED.DISPONIBILIDADE_AUD IS 'Tabela de auditoria da tabela DISPONIBILIDADE.';

  
  CREATE TABLE MODRED.PEDIDO_WORKUP_AUD 
(
  PEWO_ID NUMBER(19, 0) NOT NULL 
, AUDI_ID NUMBER(19, 0) NOT NULL 
, AUDI_TX_TIPO NUMBER(3, 0) 
, PEWO_DT_COLETA DATE 
, PEWO_DT_CRIACAO TIMESTAMP(6) 
, PEWO_DT_RESULTADO_WORKUP DATE 
, PEWO_DT_ULTIMO_STATUS TIMESTAMP(6) 
, PEWO_NR_INFO_CORRENTE NUMBER(10, 0) 
, PEWO_NR_TIPO_UTILIZADO NUMBER(10, 0) 
, CETR_ID NUMBER(19, 0) 
, MOCW_ID NUMBER(19, 0) 
, SOLI_ID NUMBER(19, 0) 
, STPW_ID NUMBER(19, 0) 
, CONSTRAINT PK_PEWA PRIMARY KEY 
  (
    PEWO_ID 
  , AUDI_ID 
  )
  USING INDEX 
  (
      CREATE UNIQUE INDEX PK_PEWA ON MODRED.PEDIDO_WORKUP_AUD (PEWO_ID ASC, AUDI_ID ASC)
  )
  ENABLE );

ALTER TABLE MODRED.PEDIDO_WORKUP_AUD
ADD CONSTRAINT FK_PEWA_AUDI FOREIGN KEY
(
  AUDI_ID 
)
REFERENCES MODRED.AUDITORIA
(
  AUDI_ID 
)
ENABLE;

CREATE INDEX IN_FK_PEWA_AUDI ON MODRED.PEDIDO_WORKUP_AUD (AUDI_ID);

COMMENT ON TABLE MODRED.PEDIDO_WORKUP_AUD IS 'Tabela de auditoria da tabela PEDIDO_WORKUP.';

--FIM

--Queiroz adicionando status de pedido de workup agendado.
INSERT INTO "MODRED"."STATUS_PEDIDO_WORKUP" (STPW_ID, STPW_DESCRICAO) VALUES ('6', 'AGENDADO');
update modred.recurso set recu_tx_sigla = 'TRATAR_PEDIDO_WORKUP' where recu_id = 38;
--fim

--filipe paes
--permissão para inativação de doador para analista de fase 3

INSERT INTO MODRED.RECURSO (RECU_ID, RECU_TX_SIGLA, RECU_TX_DESCRICAO) VALUES (41, 'INATIVAR_DOADOR_FASE3', 'Permite inativar o doador para futuras buscas de forma permanente ou temporária para a fase3.');
COMMIT;

insert into MODRED.MOTIVO_STATUS_DOADOR_RECURSO (MOSD_ID,RECU_ID) VALUES (5, 41);
insert into MODRED.MOTIVO_STATUS_DOADOR_RECURSO (MOSD_ID,RECU_ID) VALUES (3, 41);
insert into MODRED.MOTIVO_STATUS_DOADOR_RECURSO (MOSD_ID,RECU_ID) VALUES (6, 41);
COMMIT;

INSERT INTO "MODRED"."PERMISSAO" (RECU_ID, PERF_ID) VALUES ('41', '8');
COMMIT;

--Inserindo hemocentros com endereço na base Cintia 05/01
Insert into MODRED.HEMO_ENTIDADE (HEEN_ID,HEEN_TX_NOME,HEEN_IN_SELECIONAVEL,HEEN_TX_CLASSIFICACAO) select SQ_HEEN_ID.NEXTVAL, 'HEMORIO','1','HC' from dual;
Insert into MODRED.ENDERECO_CONTATO (ENCO_ID,ENCO_ID_PAIS,ENCO_CEP,ENCO_TX_TIPO_LOGRADOURO,ENCO_TX_NOME,ENCO_TX_COMPLEMENTO,ENCO_TX_BAIRRO,ENCO_TX_MUNICIPIO,ENCO_TX_SIGLA_UF,ENCO_TX_ENDERECO_ESTRANGEIRO,ENCO_NR_NUMERO,ENCO_IN_EXCLUIDO,PACI_NR_RMR,DOAD_NR_DMR,ENCO_IN_PRINCIPAL,ENCO_IN_CORRESPONDENCIA, HEEN_ID) 
select SQ_ENCO_ID.NEXTVAL ,'31','20211030','RUA','FREI CANECA',null,'CENTRO','RIO DE JANEIRO','RJ',null,'8','0',null,null,'0','0', (SELECT max(H.HEEN_ID) FROM MODRED.HEMO_ENTIDADE H) FROM DUAL;

Insert into MODRED.HEMO_ENTIDADE (HEEN_ID,HEEN_TX_NOME,HEEN_IN_SELECIONAVEL,HEEN_TX_CLASSIFICACAO) select SQ_HEEN_ID.NEXTVAL,'HEMOMINAS','1','HC' from dual;
Insert into MODRED.ENDERECO_CONTATO (ENCO_ID,ENCO_ID_PAIS,ENCO_CEP,ENCO_TX_TIPO_LOGRADOURO,ENCO_TX_NOME,ENCO_TX_COMPLEMENTO,ENCO_TX_BAIRRO,ENCO_TX_MUNICIPIO,ENCO_TX_SIGLA_UF,ENCO_TX_ENDERECO_ESTRANGEIRO,ENCO_NR_NUMERO,ENCO_IN_EXCLUIDO,PACI_NR_RMR,DOAD_NR_DMR,ENCO_IN_PRINCIPAL,ENCO_IN_CORRESPONDENCIA,HEEN_ID) 
select SQ_ENCO_ID.NEXTVAL ,'31','20010020','RUA','BARÃO DE CATAGUASES',null,'SANTA HELENA','JUIZ DE FORA','MG',null,'100','0',null,null,'1','0',(SELECT max(H.HEEN_ID) FROM MODRED.HEMO_ENTIDADE H) FROM DUAL;
commit;
--FIMCOMMIT;

--acerto tabela de tentativa de contato
ALTER TABLE MODRED.TENTATIVA_CONTATO_DOADOR RENAME COLUMN TECD_HR_INCIO_AGENDAMENTO TO TECD_HR_INICIO_AGENDAMENTO;

ALTER TABLE MODRED.TENTATIVA_CONTATO_DOADOR  
MODIFY (TECD_DT_AGENDAMENTO DATE );

-- MOTIVO DE CANCELAMENTO UTILIZADO QUANDO PRESCRIÇÃO É CANCELADA.
INSERT INTO MODRED.MOTIVO_CANCELAMENTO_WORKUP (MOCW_ID, MOCW_DESCRICAO) VALUES (4, 'PRESCRIÇÃO CANCELADA');
COMMIT;

-- Recurso para Cancelamento de Pedido de Workup por parte do CT.
INSERT INTO MODRED.RECURSO (RECU_ID, RECU_TX_SIGLA, RECU_TX_DESCRICAO) VALUES (42, 'CANCELAR_PEDIDO_WORKUP_CT', 'Permite cancelar o pedido de workup e a solicitação. Este recurso é específico para o CT.');
COMMIT;
INSERT INTO MODRED.PERMISSAO (RECU_ID, PERF_ID, PERM_IN_COM_RESTRICAO) VALUES (42, 1, 0);
COMMIT;

--Queiroz -- adicionando permissao de visualização de cabeçalho do doador ao analista_workup
insert into modred.permissao (PERF_ID,RECU_ID, PERM_IN_COM_RESTRICAO) values (9,8,0);
--fim

-- Adicionando contatos para Médico e Centro de Transplante Cintia 09/01

-- Tabela CONTATO_TELEFONICO
ALTER TABLE MODRED.CONTATO_TELEFONICO 
ADD (MEDI_ID NUMBER );

ALTER TABLE MODRED.CONTATO_TELEFONICO 
ADD (CETR_ID NUMBER );

CREATE INDEX IN_FK_COTE_CETR ON MODRED.CONTATO_TELEFONICO (CETR_ID ASC);

CREATE INDEX IN_FK_COTE_MEDI ON MODRED.CONTATO_TELEFONICO (MEDI_ID ASC);

CREATE INDEX IN_FK_COTE_PACI ON MODRED.CONTATO_TELEFONICO (PACI_NR_RMR ASC);

ALTER TABLE MODRED.CONTATO_TELEFONICO
ADD CONSTRAINT FK_COTE_CETR FOREIGN KEY
(
  CETR_ID 
)
REFERENCES MODRED.CENTRO_TRANSPLANTE
(
  CETR_ID 
)
ENABLE;

ALTER TABLE MODRED.CONTATO_TELEFONICO
ADD CONSTRAINT FK_COTE_MEDI FOREIGN KEY
(
  MEDI_ID 
)
REFERENCES MODRED.MEDICO
(
  MEDI_ID 
)
ENABLE;

COMMENT ON COLUMN MODRED.CONTATO_TELEFONICO.MEDI_ID IS 'Referencia do médico que possui o telefone.';

COMMENT ON COLUMN MODRED.CONTATO_TELEFONICO.CETR_ID IS 'Referencia do centro de transplante que possui o telefone.';

ALTER TABLE MODRED.CONTATO_TELEFONICO_AUD
ADD (MEDI_ID NUMBER );

ALTER TABLE MODRED.CONTATO_TELEFONICO_AUD
ADD (CETR_ID NUMBER );

-- Tabela ENDERECO_CONTATO
ALTER TABLE MODRED.ENDERECO_CONTATO 
ADD (CETR_ID NUMBER );

CREATE INDEX IN_FK_ENCO_CETR ON MODRED.ENDERECO_CONTATO (CETR_ID ASC);

ALTER TABLE MODRED.ENDERECO_CONTATO
ADD CONSTRAINT FK_ENCO_CETR FOREIGN KEY
(
  CETR_ID 
)
REFERENCES MODRED.CENTRO_TRANSPLANTE
(
  CETR_ID 
)
ENABLE;

ALTER TABLE MODRED.ENDERECO_CONTATO_AUD
ADD (CETR_ID NUMBER );

--FIM
-- Recurso para visualizar disponibilidade Pedido de Workup.
INSERT INTO MODRED.RECURSO (RECU_ID, RECU_TX_SIGLA, RECU_TX_DESCRICAO) VALUES (43, 'VISUALIZAR_DISPONIBILIDADE', 'Permite visualizar a disponibilidade informada pelo analista workup para o médico do CT.');
COMMIT;
INSERT INTO MODRED.PERMISSAO (RECU_ID, PERF_ID, PERM_IN_COM_RESTRICAO) VALUES (43, 1, 0);
COMMIT;



ALTER TABLE MODRED.DISPONIBILIDADE_CENTRO_COLETA 
ADD (DICC_IN_EXCLUSAO NUMBER DEFAULT 0 NOT NULL);

COMMENT ON COLUMN MODRED.DISPONIBILIDADE_CENTRO_COLETA.DICC_IN_EXCLUSAO IS 'Flag lógico para exclusão';
-- INCLUSÃO DO RESPONSAVEL PELO PEDIDO DE WORKUP
ALTER TABLE MODRED.PEDIDO_WORKUP
ADD (USUA_ID NUMBER);

CREATE INDEX IN_FK_PEWO_USUA ON MODRED.PEDIDO_WORKUP (USUA_ID ASC);

ALTER TABLE MODRED.PEDIDO_WORKUP
ADD CONSTRAINT FK_PEWO_USUA FOREIGN KEY(USUA_ID)
REFERENCES MODRED.USUARIO(USUA_ID)
ENABLE;
COMMIT;

-- Atualizando para retirar restrição do recurso
UPDATE MODRED.PERMISSAO SET PERM_IN_COM_RESTRICAO = 0
WHERE RECU_ID = 39 AND PERF_ID = 1;
COMMIT;


--Queiroz
ALTER TABLE MODRED.PEDIDO_WORKUP_AUD 
ADD (USUA_ID NUMBER );
COMMIT;
--fim


--Queiroz criando recurso para acessar cabeçalho de doador

INSERT INTO MODRED.RECURSO (RECU_ID, RECU_TX_SIGLA, RECU_TX_DESCRICAO) VALUES  (44, 'VISUALIZAR_IDENTIFICACAO_COMPLETA_DOADOR', 'Permite ver o cabeçalho do doador.');
insert into MODRED.permissao (recu_id, perf_id, perm_in_com_restricao) values (44, 9, 0);
insert into MODRED.permissao (recu_id, perf_id, perm_in_com_restricao) values (44, 1, 0);
insert into MODRED.permissao (recu_id, perf_id, perm_in_com_restricao) values (44, 7, 0);
insert into MODRED.permissao (recu_id, perf_id, perm_in_com_restricao) values (44, 8, 0);
insert into MODRED.permissao (recu_id, perf_id, perm_in_com_restricao) values (44, 6, 0);
DELETE FROM MODRED.PERMISSAO WHERE PERF_ID = 9 AND RECU_ID = 8;
commit;

--fim queiroz

ALTER TABLE MODRED.DISPONIBILIDADE_CENTRO_COLETA  
MODIFY (DICC_TX_DESCRICAO VARCHAR2(255 BYTE) );
commit;

--Alteração na tabela de motivo de cancelamento de workup
ALTER TABLE MODRED.MOTIVO_CANCELAMENTO_WORKUP 
ADD (MOCW_IN_SELECIONAVEL NUMBER(1) DEFAULT 1 NOT NULL);

COMMENT ON COLUMN MODRED.MOTIVO_CANCELAMENTO_WORKUP.MOCW_IN_SELECIONAVEL IS 'Indica se o registro poderá ser selecionado na aplicação.';

update MODRED.MOTIVO_CANCELAMENTO_WORKUP set MOCW_IN_SELECIONAVEL=0 where MOCW_ID=4;
commit;
--FIM
