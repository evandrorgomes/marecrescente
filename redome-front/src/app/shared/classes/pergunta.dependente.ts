import { ConvertUtil } from 'app/shared/util/convert.util';
import { BaseEntidade } from '../base.entidade';
import { Pergunta } from './pergunta';

/**
 * Classe que representa as perguntas dependentes de outra pergunta.
 * 
 * @export
 * @class PerguntaDependente
 */
export class PerguntaDependente extends BaseEntidade {

    private _idPergunta: string;
	private _valor: string;
	private _tipoComparacao: string;
	private _pergunta: Pergunta;
	private _comValidacao: boolean;
    private _comErro: boolean;

	constructor(idPergunta?: string, valor?: string, tipoComparacao?: string) {
		super();
        this._idPergunta = idPergunta;
		this._valor = valor;
		this._tipoComparacao = tipoComparacao;
	}

	/**
	 * Identificador da pergunta que é dependenta.
	 * 
	 * @type {string}
	 * @memberOf PerguntaDependente
	 */
	public get idPergunta(): string {
		return this._idPergunta;
	}

	/**
	 * Identificador da pergunta que é dependenta.
	 * 
	 * @memberOf PerguntaDependente
	 */
	public set idPergunta(value: string) {
		this._idPergunta = value;
	}

	/**
	 * Valor ao qual será feita a comparação com a resposta da pergunta ao qual ela é dependente
	 * 
	 * @type {string}
	 * @memberOf PerguntaDependente
	 */
	public get valor(): string {
		return this._valor;
	}

	/**
	 * Valor ao qual será feita a comparação com a resposta da pergunta ao qual ela é dependente
	 * 
	 * @memberOf PerguntaDependente
	 */
	public set valor(value: string) {
		this._valor = value;
	}

	/**
	 * Método transiente que contem a pergunta referente à propriedade idPergunta.
	 * Esta propriedade não será enviada para o backend.
	 * Uso interno do compoente de Questionário
	 * 
	 * @type {Pergunta}
	 * @memberOf PerguntaDependente
	 */
	public get pergunta(): Pergunta {
		return this._pergunta;
	}

	/**
	 * Método transiente que contem a pergunta referente à propriedade idPergunta.
	 * Esta propriedade não será enviada para o backend.
	 * Uso interno do compoente de Questionário
	 * 
	 * @memberOf PerguntaDependente
	 */
	public set pergunta(value: Pergunta) {
		this._pergunta = value;
	}

    /**
     * Getter tipoComparacao
     * @return {number}
     */
	public get tipoComparacao(): string {
		return this._tipoComparacao;
	}

    /**
     * Setter tipoComparacao
     * @param {number} value
     */
	public set tipoComparacao(value: string) {
		this._tipoComparacao = value;
	}
 

    /**
     * Getter comValidacao
     * @return {boolean}
     */
	public get comValidacao(): boolean {
		return this._comValidacao;
	}

    /**
     * Setter comValidacao
     * @param {boolean} value
     */
	public set comValidacao(value: boolean) {
		this._comValidacao = value;
	}


    /**
     * Getter comErro
     * @return {boolean}
     */
	public get comErro(): boolean {
		return this._comErro;
	}

    /**
     * Setter comErro
     * @param {boolean} value
     */
	public set comErro(value: boolean) {
		this._comErro = value;
	}


	toJson(): string {
		let json: any = {};
		json['idPergunta'] = this._idPergunta;
		json['valor'] = this._valor;
		json['tipoComparacao'] = this._tipoComparacao;
		
		return json;
	}
 
	public jsonToEntity(res: any): PerguntaDependente {
		
		if (res.pergunta) {
			this.pergunta = new Pergunta().jsonToEntity(res.pergunta);
		}

		this.idPergunta = ConvertUtil.parseJsonParaAtributos(res.idPergunta, new String());
		this.valor 		= ConvertUtil.parseJsonParaAtributos(res.valor, new String());
		this.tipoComparacao	= ConvertUtil.parseJsonParaAtributos(res.tipoComparacao, new String());
		this.comValidacao		= ConvertUtil.parseJsonParaAtributos(res.comValidacao, new Boolean());
		this.comErro			= ConvertUtil.parseJsonParaAtributos(res.comErro, new Boolean());
				
		return this;
    }

}