import { BaseEntidade } from '../base.entidade';
import { ConvertUtil } from 'app/shared/util/convert.util';
/**
 * Classe que representa as opções para os tipos de Pergunta (RADDIOBUTTON, CHECKBUTTON e SELECT)
 * 
 * @export
 * @class OpcaoPergunta
 */
export class OpcaoPergunta extends BaseEntidade {

    private _descricao: string;
	private _valor: string;
	private _invalidarOutras:boolean;


	constructor(descricao?: string, valor?: string, invalidar?:boolean) {
		super()
        this._descricao = descricao;
		this._valor = valor;
		this._invalidarOutras = invalidar;
	}

	/**
	 * Descrição da opção.
	 * 
	 * @type {string}
	 * @memberOf OpcaoPergunta
	 */
	public get descricao(): string {
		return this._descricao;
	}

	/**
	 * Descrição da opção.
	 * 
	 * @memberOf OpcaoPergunta
	 */
	public set descricao(value: string) {
		this._descricao = value;
	}
    
	/**
	 * Valor da opção que será  usuada como resposta para a pergunta
	 * 
	 * @type {string}
	 * @memberOf OpcaoPergunta
	 */
	public get valor(): string {
		return this._valor;
	}

	/**
	 * Valor da opção que será  usuada como resposta para a pergunta
	 * 
	 * @memberOf OpcaoPergunta
	 */
	public set valor(value: string) {
		this._valor = value;
	}

	/**
     * Getter invalidarOutras
     * @return {boolean}
     */
	public get invalidarOutras(): boolean {
		return this._invalidarOutras;
	}

    /**
     * Setter invalidarOutras
     * @param {boolean} value
     */
	public set invalidarOutras(value: boolean) {
		this._invalidarOutras = value;
	}

	public jsonToEntity(res: any): OpcaoPergunta {
		
		this.descricao 			= ConvertUtil.parseJsonParaAtributos(res.descricao, new String());
		this.valor 				= ConvertUtil.parseJsonParaAtributos(res.valor, new String());
		this.invalidarOutras  	= ConvertUtil.parseJsonParaAtributos(res.invalidarOutras, new Boolean());

		return this;
	}

}