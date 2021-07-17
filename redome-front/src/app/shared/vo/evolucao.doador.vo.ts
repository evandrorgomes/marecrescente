import { BaseEntidade } from 'app/shared/base.entidade';


export class EvolucaoDoadorVo extends BaseEntidade {

    private _idDoador: number;
    private _descricao: string;

    /**
     * Getter idDoador
     * @return {number}
     */
	public get idDoador(): number {
		return this._idDoador;
	}

    /**
     * Setter idDoador
     * @param {number} value
     */
	public set idDoador(value: number) {
		this._idDoador = value;
	}

    /**
     * Getter nome
     * @return {string}
     */
	public get descricao(): string {
		return this._descricao;
	}

    /**
     * Setter nome
     * @param {string} value
     */
	public set descricao(value: string) {
		this._descricao = value;
    }

    public jsonToEntity(res: any) {
        throw new Error("Method not implemented.");
    }


}
