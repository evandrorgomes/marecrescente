
INSERT INTO MODRED.FORMULARIO VALUES (3, 2, 1, '

[
    { "marcador": "1", "id": "Profissao", "descricao": "Profissão ?", "tipo": "TEXT",      
      "tamanho": 255,
      "resposta": "<2.Profissao>"
    },    
    { "marcador": "2", "id": "Escolaridade", "descricao": "Escolaridade ?", "tipo": "SELECT",
      "resposta": "<2.Escolaridade>",
      "opcoes": [ { "descricao": "1º GRAU - COMPLETO", "valor": "A" },
                  { "descricao": "1º GRAU - INCOMPLETO", "valor": "B" }, 
                  { "descricao": "1º GRAU - EM ANDAMENTO", "valor": "C" }, 
                  { "descricao": "2º GRAU - COMPLETO", "valor": "D" },
                  { "descricao": "2º GRAU - INCOMPLETO", "valor": "E" }, 
                  { "descricao": "2º GRAU - EM ANDAMENTO", "valor": "F" }, 
                  { "descricao": "3º GRAU - COMPLETO", "valor": "G" },
                  { "descricao": "3º GRAU - INCOMPLETO", "valor": "H" }, 
                  { "descricao": "3º GRAU - EM ANDAMENTO", "valor": "I" }, 
                  { "descricao": "PÓS GRADUAÇÃO - ESPECIALIZAÇÃO", "valor": "K" }, 
                  { "descricao": "PÓS GRADUAÇÃO - MESTRADO", "valor": "J" }, 
                  { "descricao": "PÓS GRADUAÇÃO - DOUTORADO", "valor": "L" } ]
    },
    { "marcador": "3", "id": "MotivoCadastramento", "descricao": "O que motivou o cadastramento no REDOME?", "tipo": "CHECKBUTTON",
      "resposta": "<2.MotivoCadastramento>",
      "opcoes": [ { "descricao": "Já sou doador de orgãos e tecidos", "valor": "DOADOR" },
                  { "descricao": "Doença na família ou de amigo", "valor": "DOENCA" }, 
                  { "descricao": "Curiosidade", "valor": "CURIOSIDADE" }, 
                  { "descricao": "Solidariedade", "valor": "SOLIDARIEDADE" }, 
                  { "descricao": "Isenção em concurso público", "valor": "ISENCAO" }, 
                  { "descricao": "Outros", "valor": "OUTROS" } ]
    },
    { "marcador": "4", "id": "ConhecimentoPrograma", "descricao": "Como tomou conhecimento do programa de doação de medula óssea?", "tipo": "CHECKBUTTON",
      "resposta": "<2.ConhecimentoPrograma>",
      "opcoes": [ { "descricao": "TV", "valor": "TV" },
                  { "descricao": "Rádio", "valor": "RADIO" },
                  { "descricao": "Internet", "valor": "INTERNET" },
                  { "descricao": "Rede Social", "valor": "REDESOCIAL" },
                  { "descricao": "Hemocentro", "valor": "HEMOCENTRO" },
                  { "descricao": "Campanha", "valor": "CAMPANHA" }, 
                  { "descricao": "Amigos e familiares", "valor": "AMIGOS" }, 
                  { "descricao": "Outros", "valor": "OUTROS" } ]
    },');
    
    
UPDATE MODRED.FORMULARIO SET  FORM_TX_FORMATO = FORM_TX_FORMATO ||   
    '{ "marcador": "5", "id": "Confidencialidade", "descricao": "Está ciente da confidencialidade no processo de doação de CTH?", "tipo": "RADIOBUTTON",
      "resposta": "<2.Confidencialidade>",
      "opcoes": [ { "descricao": "Sim", "valor": "S" }, { "descricao": "Não", "valor": "N" } ]
    },
    { "marcador": "6", "id": "Anonimato", "descricao": "Qual é a sua opinião sobre o anonimato entre você e a pessoa para quem fará a doação ?", "tipo": "RADIOBUTTON",
      "resposta": "<2.Anonimato>",
      "opcoes": [ { "descricao": "Concordo", "valor": "CONCORDO" },
                  { "descricao": "Não Concordo", "valor": "NAOCONCORDO" }, 
                  { "descricao": "Indiferente", "valor": "INDIFERENTE" } ]
    },
    { "marcador": "7", "id": "DoadorSangue", "descricao": "Você já realizou doação de sangue ?", "tipo": "RADIOBUTTON",
      "valorDefault" : "<1.DoadorSangue>", 
      "resposta": "<2.DoadorSangue>",
      "opcoes": [ { "descricao": "Sim", "valor": "S" }, { "descricao": "Não", "valor": "N" } ],
      "dependentes": [ { "idPergunta": "UltimaDoacao", "valor": "S" } ]
    },
    { "marcador": "7.1", "id": "UltimaDoacao", "descricao": "Última doação de sangue?", "tipo": "RADIOBUTTON",
      "resposta": "<2.UltimaDoacao>",
      "possuiDependencia": "true",
      "opcoes": [ { "descricao": "< 3 meses ", "valor": "A" }, { "descricao": "3 a 6 meses", "valor": "B" },
                  { "descricao": "6m a 1 ano", "valor": "C" }, { "descricao": "> 1 ano", "valor": "D" }]
    },
            
    { "marcador": "8", "id": "Medicamento", "descricao": "Você está em tratamento médico ou faz uso de algum medicamento ?", "tipo": "RADIOBUTTON",
      "valorDefault" : "<1.Medicamento>", 
      "resposta": "<2.Medicamento>",
      "opcoes": [ { "descricao": "Sim", "valor": "S" }, { "descricao": "Não", "valor": "N" } ],
      "dependentes": [ { "idPergunta": "QuaisMedicamento", "valor": "S" } ]
    },
    { "marcador": "8.1", "id": "QuaisMedicamento", "descricao": "se sim, qual ?", "tipo": "TEXTAREA",
      "valorDefault" : "<1.QuaisMedicamento>",
	  "resposta": "<2.QuaisMedicamento>", 
      "possuiDependencia": "true",
      "tamanho": 4000
    },'
WHERE FORM_ID = 2;

UPDATE MODRED.FORMULARIO SET  FORM_TX_FORMATO = FORM_TX_FORMATO ||   
    '{ "marcador": "9", "id": "Cancer", "descricao": "Já teve algum tipo de câncer?", "tipo": "RADIOBUTTON",
      "valorDefault" : "<1.Cancer>", 
      "resposta": "<2.Cancer>",
      "opcoes": [ { "descricao": "Sim", "valor": "S" }, { "descricao": "Não", "valor": "N" } ],
      "dependentes": [ { "idPergunta": "QuaisCancer", "valor": "S" }, { "idPergunta": "AnoCancer", "valor": "S" },
                       { "idPergunta": "TratamentoCancer", "valor": "S" }]
    },
    { "marcador": "9.1", "id": "QuaisCancer", "descricao": "se sim, qual ?", "tipo": "TEXTAREA",
      "resposta": "<2.QuaisCancer>",      
      "possuiDependencia": "true",
      "tamanho": 4000
    },
    { "marcador": "9.2", "id": "AnoCancer", "descricao": "Ano :", "tipo": "NUMERIC",
      "resposta": "<2.AnoCancer>",      
      "possuiDependencia": "true",
      "formatoNumerico": {"prefixo": "", "permitirNegativo" : "false", "separadorMilhares": "", "casasDecimais": "0" },
      "tamanho": 4
    },
    { "marcador": "9.3", "id": "TratamentoCancer", "descricao": "Tratamento :", "tipo": "TEXTAREA",
      "resposta": "<2.TratamentoCancer>",      
      "possuiDependencia": "true",
      "tamanho": 4000
    },    
    { "marcador": "10", "id": "Hepatite", "descricao": "Já teve algum tipo de hepatite?", "tipo": "RADIOBUTTON",
      "resposta": "<2.Hepatite>",
      "opcoes": [ { "descricao": "Sim", "valor": "S" }, { "descricao": "Não", "valor": "N" } ],
      "dependentes": [ { "idPergunta": "QuaisHepatite", "valor": "S" } ]
    },
    { "marcador": "10.1", "id": "QuaisHepatite", "descricao": "Informe quais tipos, indicando o ano de ocorrência de cada um:", "tipo": "TEXTAREA",      
      "resposta": "<2.QuaisHepatite>", 
      "possuiDependencia": "true",
      "tamanho": 4000
    },    
    { "marcador": "11", "id": "Hiv", "descricao": "HIV ?", "tipo": "RADIOBUTTON",      
      "resposta": "<2.Hiv>", 
      "opcoes": [ { "descricao": "Sim", "valor": "S" }, { "descricao": "Não", "valor": "N" } ]
    },
    { "marcador": "12", "id": "Htlv", "descricao": "HTLV ?", "tipo": "RADIOBUTTON",
      "resposta": "<2.Htlv>",
      "opcoes": [ { "descricao": "Sim", "valor": "S" }, { "descricao": "Não", "valor": "N" } ]
    },        
    { "marcador": "13", "id": "Sifilis", "descricao": "Sífilis", "tipo": "RADIOBUTTON",      
      "resposta": "<2.Sifilis>", 
      "opcoes": [ { "descricao": "Sim", "valor": "S" }, { "descricao": "Não", "valor": "N" } ],
      "dependentes": [ { "idPergunta": "TerminoTratamentoSifilis", "valor": "S" } ]
    },
    { "marcador": "13.1", "id": "TerminoTratamentoSifilis", "descricao": "Término tratamento ?", "tipo": "TEXTAREA",
      "resposta": "<2.TerminoTratamentoSifilis>",      
      "possuiDependencia": "true",
      "tamanho": 4000
    },
    '
WHERE FORM_ID = 2;

UPDATE MODRED.FORMULARIO SET  FORM_TX_FORMATO = FORM_TX_FORMATO ||   
    '{ "marcador": "14", "id": "Tuberculose", "descricao": "Tuberculose", "tipo": "RADIOBUTTON",
      "resposta": "<2.Tuberculose>", 
      "opcoes": [ { "descricao": "Sim", "valor": "S" }, { "descricao": "Não", "valor": "N" } ],
      "dependentes": [ { "idPergunta": "TerminoTratamentoTuberculose", "valor": "S" } ]
    },
    { "marcador": "14.1", "id": "TerminoTratamentoTuberculose", "descricao": "Término tratamento ?", "tipo": "TEXTAREA",
      "resposta": "<2.TerminoTratamentoTuberculose>",      
      "possuiDependencia": "true",
      "tamanho": 4000
    },
    { "marcador": "15", "id": "DoencasChagas", "descricao": "Doença de Chagas ?", "tipo": "RADIOBUTTON",
      "resposta": "<2.DoencasChagas>", 
      "opcoes": [ { "descricao": "Sim", "valor": "S" }, { "descricao": "Não", "valor": "N" } ]
    },        
    { "marcador": "16", "id": "Malaria", "descricao": "Malária", "tipo": "RADIOBUTTON",
      "resposta": "<2.Malaria>", 
      "opcoes": [ { "descricao": "Sim", "valor": "S" }, { "descricao": "Não", "valor": "N" } ],
      "dependentes": [ { "idPergunta": "TerminoTratamentoMalaria", "valor": "S" } ]
    },
    { "marcador": "16.1", "id": "TerminoTratamentoMalaria", "descricao": "Término tratamento ?", "tipo": "TEXTAREA",
      "resposta": "<2.TerminoTratamentoMalaria>",      
      "possuiDependencia": "true",
      "tamanho": 4000
    },    
    { "marcador": "17", "id": "AutoImune", "descricao": "Possui alguma doença auto-imune ?", "tipo": "RADIOBUTTON",
      "resposta": "<2.AutoImune>", 
      "opcoes": [ { "descricao": "Sim", "valor": "S" }, { "descricao": "Não", "valor": "N" } ],
      "dependentes": [ { "idPergunta": "QuaisAutoImune", "valor": "S" } ]
    },
    { "marcador": "17.1", "id": "QuaisAutoImune", "descricao": "", "tipo": "CHECKBUTTON",
      "resposta": "<2.QuaisAutoImune>",      
      "opcoes": [ { "descricao": "Lupus eritematoso sistêmico", "valor": "LES" }, { "descricao": "Artrite reumatoide", "valor": "AR" }, { "descricao": "Outra(s)", "valor": "OUTRAS" } ],
      "possuiDependencia": "true",
	  "dependentes": [ { "idPergunta": "OutrasAutoImune", "valor": "OUTRAS" } ]
    },
    { "marcador": "17.2", "id": "OutrasAutoImune", "descricao": "Especificar se for outras :", "tipo": "TEXTAREA",
      "resposta": "<2.OutrasAutoImune>",
      "possuiDependencia": "true",
      "tamanho": 4000
    },    
    { "marcador": "18", "id": "Alergia", "descricao": "Possui alguma doença alérgica ?", "tipo": "RADIOBUTTON",
      "resposta": "<2.Alergia>", 
      "opcoes": [ { "descricao": "Sim", "valor": "S" }, { "descricao": "Não", "valor": "N" } ],
      "dependentes": [ { "idPergunta": "QuaisAlergia", "valor": "S" } ]
    },
    { "marcador": "18.1", "id": "QuaisAlergia", "descricao": "", "tipo": "CHECKBUTTON",
      "resposta": "<2.QuaisAlergia>",      
      "opcoes": [ { "descricao": "Rinite Alérgica", "valor": "RA" }, { "descricao": "Asma Bônquica", "valor": "AB" }, { "descricao": "Dermatite Atópica", "valor": "DA" }, { "descricao": "Outra(s)", "valor": "OUTRAS" } ],
      "possuiDependencia": "true",
	  "dependentes": [ { "idPergunta": "OutrasAlergia", "valor": "OUTRAS" } ]
    },    
    { "marcador": "18.2", "id": "OutrasAlergia", "descricao": "Especificar se for outras :", "tipo": "TEXTAREA",
      "resposta": "<2.OutrasAlergia>",
      "possuiDependencia": "true",
      "tamanho": 4000
    },
    { "marcador": "19", "id": "Cirurgia", "descricao": "Realizou cirurgia ?", "tipo": "RADIOBUTTON",
      "resposta": "<2.Cirurgia>", 
      "opcoes": [ { "descricao": "Sim", "valor": "S" }, { "descricao": "Não", "valor": "N" } ],
      "dependentes": [ { "idPergunta": "DataCirurgia", "valor": "S" }, { "idPergunta": "QualCirurgia", "valor": "S" } ]
    },
    { "marcador": "19.1", "id": "DataCirurgia", "descricao": "Data :", "tipo": "DATE",
      "resposta": "<2.DataCirurgia>",
      "possuiDependencia": "true",
      "tamanho": 10
    },
    '
WHERE FORM_ID = 2;
        
UPDATE MODRED.FORMULARIO SET  FORM_TX_FORMATO = FORM_TX_FORMATO ||           
    '{ "marcador": "19.2", "id": "QualCirurgia", "descricao": "Qual ?", "tipo": "TEXTAREA",
      "resposta": "<2.QualCirurgia>",
      "possuiDependencia": "true",
      "tamanho": 4000
    },
    { "marcador": "20", "id": "Tatuagem", "descricao": "Realizou tatuagem/maquiagem definitiva ou piercing nos últimos 12 meses ?", "tipo": "RADIOBUTTON",
      "resposta": "<2.Tatuagem>", 
      "opcoes": [ { "descricao": "Sim", "valor": "S" }, { "descricao": "Não", "valor": "N" } ],
      "dependentes": [ { "idPergunta": "DataTatuagem", "valor": "S" }, { "idPergunta": "ObservacaoTatuagem", "valor": "S" } ]
    },
    { "marcador": "20.1", "id": "DataTatuagem", "descricao": "Data :", "tipo": "DATE",
      "resposta": "<2.DataTatuagem>",
      "possuiDependencia": "true",
      "tamanho": 10
    },
    { "marcador": "20.2", "id": "ObservacaoTatuagem", "descricao": "Observação :", "tipo": "TEXTAREA",
      "resposta": "<2.ObservacaoTatuagem>",
      "possuiDependencia": "true",
      "tamanho": 4000
    },
    { "marcador": "21", "id": "Transfusao", "descricao": "Recebeu transfusão de sangue ou plaqueta ?", "tipo": "RADIOBUTTON",
      "resposta": "<2.Transfusao>", 
      "opcoes": [ { "descricao": "Sim", "valor": "S" }, { "descricao": "Não", "valor": "N" } ],
      "dependentes": [ { "idPergunta": "DataTransfusao", "valor": "S" }, { "idPergunta": "ObservacaoTransfusao", "valor": "S" } ]
    },
    { "marcador": "21.1", "id": "DataTransfusao", "descricao": "Data :", "tipo": "DATE",
      "resposta": "<2.DataTransfusao>",
      "possuiDependencia": "true",
      "tamanho": 11
    },
    { "marcador": "21.2", "id": "ObservacaoTransfusao", "descricao": "Informe quais transfusões e o respectivo ano e motivo:", "tipo": "TEXTAREA",
      "resposta": "<2.ObservacaoTransfusao>", 
      "possuiDependencia": "true",
      "tamanho": 4000
    },
    { "marcador": "22", "id": "Vacinas", "descricao": "Recebeu vacina nos últimos 12 meses ?", "tipo": "RADIOBUTTON",
      "resposta": "<2.Vacinas>",
      "opcoes": [ { "descricao": "Sim", "valor": "S" }, { "descricao": "Não", "valor": "N" } ],
      "dependentes": [ { "idPergunta": "QuaisVacinas", "valor": "S" } ]
    },
    { "marcador": "22.1", "id": "QuaisVacinas", "descricao": "Quais:", "tipo": "TEXTAREA",
      "resposta": "<2.QuaisVacinas>",
      "possuiDependencia": "true",
      "tamanho": 4000
    },
	'
WHERE FORM_ID = 2;

UPDATE MODRED.FORMULARIO SET  FORM_TX_FORMATO = FORM_TX_FORMATO ||

'  { "marcador": "23", "id": "NumeroGestacoesAbortamentos", "descricao": "Para mulheres, Nº de gestações, incluindo abortamentos :", "tipo": "NUMERIC",
      "resposta": "<2.NumeroGestacoesAbortamentos>",
      "formatoNumerico": {"prefixo": "", "permitirNegativo" : "false", "separadorMilhares": "", "casasDecimais": "0" },
      "tamanho": 2,
      "dependentes": [ { "idPergunta": "DataUltimoPartoAbortamento", "valor": "0","tipoComparacao":"MAIOR" }],
	  "condicao": {
		"atributo": "sexo",
		"valor": "F",
		"tipoComparacao": "IGUAL"
	  }
	},
    { "marcador": "24", "id": "DataUltimoPartoAbortamento", "descricao": "Para mulheres, data do último parto ou abortamento:", "tipo": "DATE",
      "resposta": "<2.DataUltimoPartoAbortamento>",
      "possuiDependencia": "true",
      "tamanho": 10,
	  "condicao": {
		"atributo": "sexo",
		"valor": "F",
		"tipoComparacao": "IGUAL"
	  }
	},
    { "marcador": "25", "id": "Amamentando", "descricao": "Para mulheres, você está amamentando ?", "tipo": "RADIOBUTTON",
      "resposta": "<2.Amamentando>", 
      "opcoes": [ { "descricao": "Sim", "valor": "S" }, { "descricao": "Não", "valor": "N" } ],
      "possuiDependencia": "false",
	  "condicao": {
		"atributo": "sexo",
		"valor": "F",
		"tipoComparacao": "IGUAL"
	  }
	},
	{ "marcador": "26", "id": "DisponibilidadeViagem", "descricao": "Disponibilidade para viajar no ato da doação caso não possa ser em seu estado ?", "tipo": "RADIOBUTTON",
      "resposta": "<2.DisponibilidadeViagem>", 
      "opcoes": [ { "descricao": "Sim", "valor": "S" }, { "descricao": "Não", "valor": "N" } ],
      "dependentes": [ { "idPergunta": "JustificativaViagem", "valor": "N" } ]
    },
    { "marcador": "26.1", "id": "JustificativaViagem", "descricao": "Comentário :", "tipo": "TEXTAREA",
      "resposta": "<2.JustificativaViagem>",      
      "possuiDependencia": "true",
      "tamanho": 4000
    },
'
WHERE FORM_ID = 2;

UPDATE MODRED.FORMULARIO SET  FORM_TX_FORMATO = FORM_TX_FORMATO || 
    '{ "marcador": "27", "id": "Prosseguir", "descricao": "Deseja seguir com o processo de doação ?", "tipo": "RADIOBUTTON",
      "resposta": "<2.Prosseguir>",
      "opcoes": [ { "descricao": "Sim", "valor": "S" }, { "descricao": "Não", "valor": "N" } ]
    },
    { "marcador": "28", "id": "RestricaoReligiao", "descricao": "Sua religião restringe uma futura doação de medula óssea ?", "tipo": "RADIOBUTTON",
      "resposta": "<2.RestricaoReligiao>",       
      "opcoes": [ { "descricao": "Sim", "valor": "S" }, { "descricao": "Não", "valor": "N" } ]
    },
    { "marcador": "29", "id": "FormaContato", "descricao": "Qual a melhor forma de receber informações e comunicados do REDOME ?", "tipo": "CHECKBUTTON",
      "valorDefault" : "<1.FormaContato>",
      "resposta": "<2.FormaContato>", 
      "opcoes": [ { "descricao": "SMS", "valor": "SMS" }, { "descricao": "Telegrama", "valor": "TELEGRAMA" }, { "descricao": "E-Mail", "valor": "EMAIL" }, { "descricao": "Telefone", "valor": "TELEFONE" }, { "descricao": "WhatsApp", "valor": "WHATSAPP" }, { "descricao": "Outras", "valor": "OUTRAS" } ],
      "dependentes": [ { "idPergunta": "OutrosContato", "valor": "OUTROS" } ]
    },
    { "marcador": "29.1", "id": "OutrasContato", "descricao": "Especificar se for outras :", "tipo": "TEXTAREA",
      "valorDefault" : "<1.OutrosContato>",
      "resposta": "<2.OutrasContato>", 
      "possuiDependencia": "true",
      "tamanho": 4000
    },
    { "marcador": "30", "id": "PeriodoContato", "descricao": "Qual a melhor horário para que o REDOME entre em  contato com você novamente em caso de necessidade ?", "tipo": "CHECKBUTTON",
      "valorDefault" : "<1.PeriodoContato>",
      "resposta": "<2.PeriodoContato>", 
      "opcoes": [ { "descricao": "Manhã", "valor": "M" }, { "descricao": "Tarde", "valor": "T" }, { "descricao": "Noite", "valor": "N" } ],
      "dependentes": [ { "idPergunta": "EspecificarHorario", "valor": "M,T,N" } ]
    },    
    { "marcador": "31.1", "id": "EspecificarHorario", "descricao": "Especificar horário :", "tipo": "TEXTAREA",
      "resposta": "<2.EspecificarHorario>",      
      "possuiDependencia": "true",
      "tamanho": 4000
    },
    { "marcador": "32", "id": "PeriodoIndisponibilidade", "descricao": "Período de indisponibilidade programado nos próximos 6 meses ?", "tipo": "RADIOBUTTON",
      "resposta": "<2.PeriodoIndisponibilidade>",      
      "opcoes": [ { "descricao": "Sim", "valor": "S" }, { "descricao": "Não", "valor": "N" } ],
      "dependentes": [ { "idPergunta": "DataInicio", "valor": "S" }, { "idPergunta": "DataFim", "valor": "S" }, { "idPergunta": "EspecificarDisponibilidade", "valor": "S" } ]
    },
    { "marcador": "32.1", "id": "DataInicio", "descricao": "Data Início :", "tipo": "DATE",
      "resposta": "<2.DataInicio>",      
      "possuiDependencia": "true",
      "tamanho": 10
    },
    { "marcador": "32.2",  "id": "DataFim", "descricao": "Data Fim :", "tipo": "DATE",
      "resposta": "<2.DataFim>",      
      "possuiDependencia": "true",
      "tamanho": 10
    },
    { "marcador": "33.3", "id": "EspecificarDisponibilidade", "descricao": "Especifique :", "tipo": "TEXTAREA",
      "resposta": "<2.EspecificarDisponibilidade>",      
      "possuiDependencia": "true",
      "tamanho": 4000
    },
    { "marcador": "34", "id": "Transplante", "descricao": "Realizou algum transplante ?", "tipo": "RADIOBUTTON",
      "resposta": "<2.Transplante>",      
      "opcoes": [ { "descricao": "Sim", "valor": "S" }, { "descricao": "Não", "valor": "N" } ],
      "dependentes": [ { "idPergunta": "DataTransplante", "valor": "S" }, { "idPergunta": "QualTrasnplante", "valor": "S" } ]
    },
    '
WHERE FORM_ID = 2;

UPDATE MODRED.FORMULARIO SET  FORM_TX_FORMATO = FORM_TX_FORMATO || 
    '{ "marcador": "34.1", "id": "DataTransplante", "descricao": "Data :", "tipo": "DATE",
      "resposta": "<2.DataTransplante>",      
      "possuiDependencia": "true",
      "tamanho": 10
    },
    { "marcador": "34.2", "id": "QualTrasnplante", "descricao": "Qual tipo ?", "tipo": "TEXTAREA",
      "resposta": "<2.QualTrasnplante>",      
      "possuiDependencia": "true",
      "tamanho": 4000
    }

  ]
    '
WHERE FORM_ID = 2;

COMMIT;