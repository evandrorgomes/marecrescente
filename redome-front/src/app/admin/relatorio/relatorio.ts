
/**
 * @author Fillipe Queiroz
 */
export class Relatorio {

	private _id: number;
	private _codigo:string;
	private _html:string;
	private _tipo:number;



    /**
     * Getter id
     * @return {number}
     */
	public get id(): number {
		return this._id;
	}

    /**
     * Setter id
     * @param {number} value
     */
	public set id(value: number) {
		this._id = value;
	}

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

    /**
     * Getter tipo
     * @return {number}
     */
	public get tipo(): number {
		return this._tipo;
	}

    /**
     * Setter tipo
     * @param {number} value
     */
	public set tipo(value: number) {
		this._tipo = value;
	}
	
}
