import { BaseEntidade } from "../base.entidade";

/**
 * Classe utilizada para definir as configurações utilizadas no sistema, através de chave/valor,
 * indicando as validações, por exemplo, nos tamanhos de arquivo para upload, extensões possíveis, etc.
 * @author Pizão
 */
export class Configuracao extends BaseEntidade {

  /**
   * Chave de identificação da configuração.
   * @private
   * @type {number}@memberof Metodologia
   */
	private _chave: string;
	
    /**
   * Valor refere a configuração a ser reutilizado no sistema.
   * @private
   * @type {number}@memberof Metodologia
   */
    private _valor: string;

	constructor(chave: string, valor: string) {
		super();
		this._chave = chave;
		this._valor = valor;
	}

	public get chave(): string {
		return this._chave;
	}
	public set chave(value: string) {
		this._chave = value;
	}

	public get valor(): string {
		return this._valor;
	}
	public set valor(value: string) {
		this._valor = value;
	}

	public jsonToEntity(res: any) :this{
        return this;
    }
}