
insert into formulario values(SQ_FORM_ID.Nextval, 3, 1,'
[{
	"titulo": "Página 1",
	"secoes": [{
		"titulo": "ANAMNESE",
		"perguntas":
[{
	"marcador": "",
	"id": "RW_TOMAMEDICACAO",
	"descricao": "Toma alguma medicação regularmente?",
	"tipo": "RADIOBUTTON",
	"opcoes": [{
		"descricao": "Sim",
		"valor": "S"
	},
	{
		"descricao": "Não",
		"valor": "N"
	}],
    "resposta": "<RW_TOMAMEDICACAO>",
	"dependentes": [{ 
		"idPergunta": "RW_QUALMEDICACAO",
		"valor": "S"
	}]
},
{
	"marcador": "",
	"id": "RW_QUALMEDICACAO", "descricao": "Quais medicamentos?","tipo": "TEXTAREA","possuiDependencia": "true",
	"tamanho": 4000, "resposta": "<RW_QUALMEDICACAO>"
},
{
	"marcador": "",
	"id": "RW_ProblemaSaude",
	"descricao": "Tem algum problema de saúde?",
	"tipo": "RADIOBUTTON",
	"opcoes": [{
		"descricao": "Sim",
		"valor": "S"
	},
	{
		"descricao": "Não",
		"valor": "N"
	}],
    "resposta": "<RW_ProblemaSaude>",
	"dependentes": [{
		"idPergunta": "RW_QualProblemaSaude",
		"valor": "S"
	}]
},');

var seq number; 
exec select SQ_FORM_ID.currval into :seq from dual;
    
    
UPDATE MODRED.FORMULARIO SET  FORM_TX_FORMATO = FORM_TX_FORMATO ||   
    '{
	"marcador": "",
	"id": "RW_QualProblemaSaude",
	"descricao": "Quais problemas?",
	"tipo": "TEXTAREA",
	"possuiDependencia": "true",
	"tamanho": 4000,
	"resposta": "<RW_QualProblemaSaude>"
},
{
	"marcador": "", 
	"id": "RW_Acidente", 
	"descricao": "Já sofreu algum acidente?", 
	"tipo": "RADIOBUTTON", 
	"opcoes": [{
		"descricao": "Sim",
		"valor": "S"
	},
	{
		"descricao": "Não",
		"valor": "N"
	}],
    "resposta": "<RW_Acidente>",
	"dependentes": [{
		"idPergunta": "RW_QualAcidente",
		"valor": "S"
	}]
},
{
	"marcador": "",
	"id": "RW_QualAcidente",
	"descricao": "Quais acidentes?",
	"tipo": "TEXTAREA",
	"possuiDependencia": "true",
	"tamanho": 4000,
	"resposta": "<RW_QualAcidente>"
},'
WHERE FORM_ID = :seq;

UPDATE MODRED.FORMULARIO SET  FORM_TX_FORMATO = FORM_TX_FORMATO ||   
    '{
	"marcador": "",
	"id": "RW_Cirurgia",
	"descricao": "Já fez alguma cirurgia?",
	"tipo": "RADIOBUTTON",
	"opcoes": [{
		"descricao": "Sim",
		"valor": "S"
	},
	{
		"descricao": "Não",
		"valor": "N"
	}],
    "resposta": "<RW_Cirurgia>",
	"dependentes": [{
		"idPergunta": "RW_QualCirurgia",
		"valor": "S"
	},
	{
		"idPergunta": "RW_ComplicacaoCirurgia",
		"valor": "S"
	}]
},
{
	"marcador": "",
	"id": "RW_QualCirurgia",
	"descricao": "Quais cirurgias?",
	"tipo": "TEXTAREA",
	"possuiDependencia": "true",
	"tamanho": 4000,
	"resposta": "<RW_QualCirurgia>"
},
{
	"marcador": "",
	"id": "RW_ComplicacaoCirurgia",
	"descricao": "Complicações cirúrgicas?",
	"tipo": "TEXTAREA",
	"possuiDependencia": "true",
	"tamanho": 4000,
	"resposta": "<RW_ComplicacaoCirurgia>"
},
{
	"marcador": "",
	"id": "RW_Transfusao",
	"descricao": "Transfusões?",
	"tipo": "RADIOBUTTON",
	"opcoes": [{
		"descricao": "Sim",
		"valor": "S"
	},'
WHERE FORM_ID = :seq;

UPDATE MODRED.FORMULARIO SET  FORM_TX_FORMATO = FORM_TX_FORMATO ||   
    '{
		"descricao": "Não",
		"valor": "N"
	}],
    "resposta": "<RW_Transfusao>",
	"dependentes": [{
		"idPergunta": "RW_NumeroTransfusao",
		"valor": "S"
	},
	{
		"idPergunta": "RW_ComponenteTransfusao",
		"valor": "S"
	},
		{
		"idPergunta": "RW_AnoTransfusao",
		"valor": "S"
	}]
},
{
	"marcador": "",
	"id": "RW_NumeroTransfusao",
	"descricao": "Número",
	"tipo": "NUMERIC",
	"possuiDependencia": "true",
	"tamanho": 4,
	"resposta": "<RW_NumeroTransfusao>"
},
{
	"marcador": "",
	"id": "RW_ComponenteTransfusao",
	"descricao": "Componente",
	"tipo": "TEXT",
	"possuiDependencia": "true",
	"tamanho": 100,
	"resposta": "<RW_ComponenteTransfusao>"
},
{
	"marcador": "",
	"id": "RW_AnoTransfusao",
	"descricao": "Ano da transfusão",
	"tipo": "NUMERIC",
	"possuiDependencia": "true",
	"tamanho": 4,
	"resposta": "<RW_AnoTransfusao"
},
{
	"marcador": "",
	"id": "RW_Gravidez",
	"descricao": "Gravidez?",
	"tipo": "RADIOBUTTON",
	"opcoes": [{
		"descricao": "Sim",
		"valor": "S"
	},
	{
		"descricao": "Não",
		"valor": "N"
	},
	{
		"descricao": "Não Aplicável",
		"valor": "A"
	}],
    "resposta": "<RW_Gravidez>",
	"dependentes": [{
		"idPergunta": "RW_NumeroGravidez",
		"valor": "S"
	},'
WHERE FORM_ID = :seq;
        
UPDATE MODRED.FORMULARIO SET  FORM_TX_FORMATO = FORM_TX_FORMATO ||           
    '{
		"idPergunta": "RW_AbortoGravidez",
		"valor": "S"
	}]
},
{
	"marcador": "",
	"id": "RW_NumeroGravidez",
	"descricao": "Número",
	"tipo": "NUMERIC",
	"possuiDependencia": "true",
	"tamanho": 4,
	"resposta": "<RW_NumeroGravidez>"
},
{
	"marcador": "",
	"id": "RW_AbortoGravidez",
	"descricao": "Número de abortos espontânios",
	"tipo": "NUMERIC",
	"possuiDependencia": "true",
	"tamanho": 4,
	"resposta": "<RW_AbortoGravidez>"
},
{
	"marcador": "5",
	"id": "RW_Tatuagem",
	"descricao": "Já fez alguma tatuagem?",
	"tipo": "RADIOBUTTON",
	"opcoes": [{
		"descricao": "Sim",
		"valor": "S"
	},
	{
		"descricao": "Não",
		"valor": "N"
	}],
    "resposta": "<1.TatuagensWorkup>",
	"dependentes": [{
		"idPergunta": "QuandoTatuagensWorkup",
		"valor": "S"
	}]
},'
WHERE FORM_ID = :seq;

UPDATE MODRED.FORMULARIO SET  FORM_TX_FORMATO = FORM_TX_FORMATO ||
'  {
	"marcador": "5.1",
	"id": "QuandoTatuagensWorkup",
	"descricao": "Quando?",
	"tipo": "TEXT",
	"possuiDependencia": "true",
	"tamanho": 20,
	"resposta": "<1.QuandoTatuagensWorkup>"
},
{
	"marcador": "6",
	"id": "ZicaDengueWorkup",
	"descricao": "Já teve Zica, Dengue ou Chikungunya?",
	"tipo": "RADIOBUTTON",
	"opcoes": [{
		"descricao": "Sim",
		"valor": "S"
	},
	{
		"descricao": "Não",
		"valor": "N"
	}],
    "resposta": "<1.ZicaDengueWorkup>",
	"dependentes": [{
		"idPergunta": "QuandoZicaDengueWorkup",
		"valor": "S"
	}]
},
{
	"marcador": "6.1",
	"id": "QuandoZicaDengueWorkup",
	"descricao": "Quando?",
	"tipo": "TEXT",
	"possuiDependencia": "true",
	"tamanho": 20,
	"resposta": "<1.QuandoZicaDengueWorkup>"
},
{
	"marcador": "7",
	"id": "VacinaFebreWorkup",
	"descricao": "Tomou a vacina para febre amarela?",
	"tipo": "RADIOBUTTON",
	"opcoes": [{
		"descricao": "Sim",
		"valor": "S"
	},'
WHERE FORM_ID = :seq;

UPDATE MODRED.FORMULARIO SET  FORM_TX_FORMATO = FORM_TX_FORMATO || 
    '{
		"descricao": "Não",
		"valor": "N"
	}],
    "resposta": "<1.VacinaFebreWorkup>",
	"dependentes": [{
		"idPergunta": "QuandoVacinaFebreWorkup",
		"valor": "S"
	}]
},
{
	"marcador": "7.1",
	"id": "QuandoVacinaFebreWorkup",
	"descricao": "Quando?",
	"tipo": "TEXT",
	"possuiDependencia": "true",
	"tamanho": 20,
	"resposta": "<1.QuandoVacinaFebreWorkup>"
},
{
	"marcador": "8",
	"id": "EngravidouWorkup",
	"descricao": "Já engravidou?",
	"tipo": "RADIOBUTTON",
	"opcoes": [{
		"descricao": "Sim",
		"valor": "S"
	},
	{
		"descricao": "Não",
		"valor": "N"
	}],
    "resposta": "<1.EngravidouWorkup>",
	"dependentes": [{
		"idPergunta": "QuandoEngravidouWorkup",
		"valor": "S"
	}]
},
{
	"marcador": "8.1",
	"id": "QuandoEngravidouWorkup",
	"descricao": "Quando?",
	"tipo": "TEXT",
	"possuiDependencia": "true",
	"tamanho": 20,
	"resposta": "<1.QuandoEngravidouWorkup>"
},
{
	"marcador": "9",
	"id": "EngravidarWorkup",
	"descricao": "Pretende engravidar em breve?",
	"tipo": "RADIOBUTTON",
	"opcoes": [{
		"descricao": "Sim",
		"valor": "S"
	},'
WHERE FORM_ID = :seq;

UPDATE MODRED.FORMULARIO SET  FORM_TX_FORMATO = FORM_TX_FORMATO || 
    '{
		"descricao": "Não",
		"valor": "N"
	}]
},
{
	"marcador": "10",
	"id": "DisponivelViagemWorkup",
	"descricao": "Tem disponibilidade para viajar?",
	"tipo": "RADIOBUTTON",
	"opcoes": [{
		"descricao": "Sim",
		"valor": "S"
	},
	{
		"descricao": "Não",
		"valor": "N"
	}]
},
{
	"marcador": "11",
	"id": "ObservacoesWorkup", "descricao": "Observações", "tipo": "TEXTAREA", "tamanho": 4000
}]
}]
}]'
WHERE FORM_ID = :seq;

COMMIT;