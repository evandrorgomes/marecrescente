import { BaseEntidade } from '../../shared/base.entidade';

/**
 * @author Fillipe Queiroz
 */
export class RelatorioDTO extends BaseEntidade{
    public jsonToEntity(res: any) {
        throw new Error("Method not implemented.");
    }

	private _codigo:string;
	private _html:string;

    /**
     * Getter codigo
     * @return {string}
     */
	public get codigo(): string {
		return this._codigo;
	}

    /**
     * Setter codigo
     * @param {string} value
     */
	public set codigo(value: string) {
		this._codigo = value;
	}

    /**
     * Getter html
     * @return {string}
     */
	public get html(): string {
		return this._html;
	}

    /**
     * Setter html
     * @param {string} value
     */
	public set html(value: string) {
		this._html = value;
	}
	
}
