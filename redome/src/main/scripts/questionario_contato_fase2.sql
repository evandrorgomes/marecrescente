INSERT INTO MODRED.FORMULARIO VALUES (1, 1, 1, '
[{
	"marcador": "1",
	"id": "LembraREDOME",
	"descricao": "Você lembra de ter se cadastrado como doador do REDOME?",
	"tipo": "RADIOBUTTON",
	"opcoes": [{
		"descricao": "Sim",
		"valor": "S"
	},
	{
		"descricao": "Não",
		"valor": "N"
	}],
    "resposta": "<1.LembraREDOME>"
},
{
	"marcador": "2",
	"id": "Doncas",
	"descricao": "Você apresenta ou apresentou alguma destas doenças?",
	"tipo": "CHECKBUTTON",
	"opcoes": [{
		"descricao": "Nenhum",
		"valor": "NENHUM",
		"invalidarOutras":true
	},
	{
		"descricao": "Câncer",
		"valor": "CANCER"
	},
	{
		"descricao": "Hepatite",
		"valor": "HEPATITE"
	},
	{
		"descricao": "Tuberculose",
		"valor": "TUBERCULOSE"
	},
	{
		"descricao": "HIV",
		"valor": "HIV"
	}],
	"resposta": "<1.Doncas>",
	"dependentes": [{
		"idPergunta": "QuaisDoencas",
		"valor": "CANCER, HEPATITE, TUBERCULOSE"
	}]
},
{
	"marcador": "2.1",
	"id": "QuaisDoencas",
	"descricao": "Especificar ano e tratamento:",
	"tipo": "TEXTAREA",
	"possuiDependencia": "true",
	"tamanho": 4000,
	"resposta": "<1.QuaisDoencas>"
},
{
	"marcador": "3",
	"id": "Gravida",
	"descricao": "Para as mulheres - Você está grávida ?",
	"tipo": "RADIOBUTTON",
                           "resposta": "<1.Gravida>",
	"opcoes": [{
		"descricao": "Sim",
		"valor": "S"
	},
	{
		"descricao": "Não",
		"valor": "N"
	}],
	"dependentes": [{
		"idPergunta": "DataProvavelParto",
		"valor": "S"
	}],
	"condicao": {
		"atributo": "sexo",
		"valor": "F",
		"tipoComparacao": "IGUAL"
	}
},
{
	"marcador": "3.1",
	"id": "DataProvavelParto",
	"descricao": "Data provável do parto:",
	"tipo": "TEXT",
	"possuiDependencia": "true",
	"tamanho": 10,
                           "resposta": "<1.DataProvavelParto>"
},
{
	"marcador": "4",
	"id": "Medicamento",
	"descricao": "Você usa alguma medicação regularmente?",
	"tipo": "RADIOBUTTON",
                           "resposta": "<1.Medicamento>",
	"opcoes": [{
		"descricao": "Sim",
		"valor": "S"
	},
	{
		"descricao": "Não",
		"valor": "N"
	}],
	"dependentes": [{
		"idPergunta": "QuaisMedicamento",
		"valor": "S"
	}]
},
{
	"marcador": "4.1",
	"id": "QuaisMedicamento",
	"descricao": "se sim, qual ?",
	"tipo": "TEXTAREA",
	"possuiDependencia": "true",
	"tamanho": 4000,
                            "resposta": "<1.QuaisMedicamento>"
},
{
	"marcador": "5",
	"id": "FormaContato",
	"descricao": "Qual melhor forma de receber informações e comunicados do REDOME ?",
	"tipo": "CHECKBUTTON",
                           "resposta": "<1.FormaContato>",
	"opcoes": [{
		"descricao": "SMS",
		"valor": "SMS"
	},
	{
		"descricao": "Telegrama",
		"valor": "TELEGRAMA"
	},
	{
		"descricao": "E-Mail",
		"valor": "EMAIL"
	},
	{
		"descricao": "Telefone",
		"valor": "TELEFONE"
	},
	{
		"descricao": "WhatsApp",
		"valor": "WHATSAPP"
	},
	{
		"descricao": "Outras",
		"valor": "OUTRAS"
	}],
	"dependentes": [{
		"idPergunta": "OutrosContato",
		"valor": "OUTRAS"
	}]
},
{
	"marcador": "5.1",
	"id": "OutrosContato",
	"descricao": "Especificar se for Outras:",
	"tipo": "TEXTAREA",
	"possuiDependencia": "true",
	"tamanho": 4000,
                           "resposta": "<1.OutrosContato>"
},
{
	"marcador": "6",
	"id": "PeriodoContato",
	"descricao": "Qual o melhor horário para que o REDOME entre em contato com você novamente em caso de necessidade ?",
	"tipo": "CHECKBUTTON",
     "resposta": "<1.PeriodoContato>",
	"opcoes": [{
		"descricao": "Manhã",
		"valor": "M"
	},
	{
		"descricao": "Tarde",
		"valor": "T"
	},
	{
		"descricao": "Noite",
		"valor": "N"
	},
	{
		"descricao": "Todos",
		"valor": "TO"
	}],
	"dependentes": [{
		"idPergunta": "EspecificarHorario",
		"valor": "TO"
	}]
},
{
	"marcador": "6.1",
	"id": "EspecificarHorario",
	"descricao": "Especificar horário:",
	"tipo": "TEXTAREA",
	"possuiDependencia": "true",
	"tamanho": 4000,
    "resposta": "<1.EspecificarHorario>"
}]');

COMMIT;
