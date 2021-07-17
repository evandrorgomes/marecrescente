export class StatusDoadorDTO{
    private _id:number;
    private _descricao:number;


    /**
     * Getter id
     * @return {number}
     */
	public get id(): number {
		return this._id;
	}

    /**
     * Getter nome
     * @return {number}
     */
	public get descricao(): number {
		return this._descricao;
	}

    /**
     * Setter id
     * @param {number} value
     */
	public set id(value: number) {
		this._id = value;
	}

    /**
     * Setter nome
     * @param {number} value
     */
	public set descricao(value: number) {
		this._descricao = value;
	}

}
