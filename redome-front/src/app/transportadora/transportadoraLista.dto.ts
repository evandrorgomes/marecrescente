import { BaseEntidade } from "app/shared/base.entidade";
export class TransportadoraListaDTO extends BaseEntidade {

    private _id: number;
    private _nome: string;

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
     * Getter nome
     * @return {string}
     */
	public get nome(): string {
		return this._nome;
	}

    /**
     * Setter nome
     * @param {string} value
     */
	public set nome(value: string) {
		this._nome = value;
	}

	public jsonToEntity(res: any) {
		throw new Error("Method not implemented.");
	}

}