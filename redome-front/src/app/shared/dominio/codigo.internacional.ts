import { BaseEntidade } from "../base.entidade";

/**
 * Classe utilizada para representar a vo codigo internacional
 *  
 * @author Bruno Sousa
 */
export class CodigoInternacional extends BaseEntidade {

    /**
	 * Código internacional do país
	 * 
	 * @private
	 * @type {string}
	 * @memberOf CodigoInternacional
	 */
	private _codigo: string;


    /**
	 * Nome do país
	 * 
	 * @private
	 * @type {string}
	 * @memberOf CodigoInternacional
	 */
	private _texto: string;

    /**
	 * Texto formatado: texto + '  +' + codigo 
	 * 
	 * @private
	 * @type {string}
	 * @memberOf CodigoInternacional
	 */
	private _apresentacao: string;
		
	public get codigo(): string {
		return this._codigo;
	}

	public set codigo(value: string) {
		this._codigo = value;
	}

	public get texto(): string {
		return this._texto;
	}
    
	public set texto(value: string) {
		this._texto = value;
	}

    public get apresentacao(): string {
		return this._apresentacao;
	}
    
	public set apresentacao(apresentacao: string) {
		this._apresentacao = apresentacao;
	}

	public jsonToEntity(res: any) {
        throw new Error("Method not implemented.");
    }

}