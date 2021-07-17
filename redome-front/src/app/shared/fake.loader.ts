import { Http } from '@angular/http';
import { Observable } from 'rxjs';
import { TranslateLoader } from '@ngx-translate/core';

/**
 * Classe utilizada nos test para pegar a internacionalização
 * 
 * @author Bruno Sousa
 * @export
 * @class FakeLoader
 * @implements {TranslateLoader}
 */
export class FakeLoader implements TranslateLoader {
    
    /**
	 * Variével criada como cópia do arquivo pt.json
	 * 
	 * @private
	 * @type {*}
	 * @memberOf FakeLoader
	 */
	private translations: any = {
	"formLogin": {
		"usuario": "Nome de usuário",
		"senha": "Senha"
	},
	"mensagem": {
		"erro": {
			"obrigatorio": "O campo {{ campo }} é obrigatório",
			"numeroDigitos": "O campo de {{campo}} deve ter ao menos {{digitos}} dígitos",
			"menorDataAtual": "{{campo}} deve ser menor que data atual.",
			"tipoArquivoLaudo": "Tipo de arquivo não suportado.",
			"quantidadeArquivoLaudo": "Limite de laudos por exame atingido.",
			"tamanhoArquivoLaudo": "O arquivo ultrapassou o tamanho máximo permitido de {{maxFileSize}}",
			"inserirLaudo": "Erro tentar incluir o arquivo",
			"nenhumLaudoSelecionado": "É necessário selecionar um arquivo de laudo.",
			"metodologia": "Selecione ao menos uma metodologia"
		}
	},
	"botao": {
		"entrar": "Entrar",
		"adicionar": "Adicionar",
		"adicionarMetodologia": "Adicionar Metodologia"
	},
	"pacienteForm": {
		"identificacaoGroup": {
			"dataNascimento": "Data de Nascimento",
            "cpf": "CPF",
            "cns":"CNS",
			"responsavel": {
            	"cpf": "CPF do responsável",
            	"nome": "Nome do responsável",
            	"parentesco": "Parentesco"
			},
            "nome": "Nome",
            "nomeMae": "Nome da Mãe",
			"maeDesconhecida": "Mãe Desconhecida?",
		},
		"dadosPessoaisGroup":{
            "sexo": "Sexo",
            "abo": "ABO",
			"etnia": {
            	"id": "Etnia"
			},
			"pais": {
            	"id": "Nacionalidade"
			},
			"raca": {
            	"id": "Raça"
			},
			"uf": {
            	"sigla": "Naturalidade"
			}
		},
		"dadosContatoGroup": {
			"principal": "Principal?",
            "nome": "Nome para contato",
            "tipo": "Tipo",
            "codInter":"Código Internacional",
            "codArea":"Código de Área",
            "numero":"Número",
            "complemento":"Complemento",
			"pais": {
            	"id":"País em que reside"
			},
            "cep":"CEP",
            "tipoLogradouro":"Tipo de Logradouro",
            "nomeLogradouro":"Nome do Logradouro",
            "bairro":"Bairro",
            "municipio":"Município",
            "uf":"UF",
            "enderecoEstrangeiro":"End. Estrangeiro",
            "email":"E-mail"
		},
		"diagnosticoGroup": {
			"dataDiagnostico": "Data do Diagnóstico",
            "cid": "CID"
		},
		"evolucaoGroup": {
			"motivo": "Motivo",
            "peso": "Peso",
            "altura": "Altura",
            "tratamentoAnterior": "Tratamento Anterior",
            "tratamentoAtual": "Tratamento Atual",
            "condicaoAtual": "Condição Atual",
            "estagioDoenca": "Estágio da doença",
            "evolucao": "Condição do paciente",
            "existenciaTransplante": "Existência de transplante prévio",
            "resultadoCMV": "Resultado CMV"
		},
		"exameGroup": {
			"metodologia": "Metodologia",
            "dataExame": "Data de Exame",
            "alelo1": "Alelo 1",
            "alelo2": "Alelo 2",
            "laudo": "Laudo"
		},
		"motivoGroup": {
			"motivoCadastro": "Motivo do cadastro",
            "centroAvaliador": "Centro Avaliador",
			"centroTransplantador": "Centro Transplantador",
			"aceiteMismatch": "Aceita mismatch de busca"
		}
	}
};
    
    /**
	 * Método herdado 
	 * 
	 * @param {string} lang 
	 * @returns {Observable<any>} 
	 * 
	 * @memberOf FakeLoader
	 */
	getTranslation(lang: string): Observable<any> {
        return Observable.of(this.translations); 
    }
}