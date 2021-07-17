import { BaseEntidade } from '../../../shared/base.entidade';
import { MatchDTO } from "../../../shared/component/listamatch/match.dto";

/**
 * Classe que representa uma classificacao abo.
 * 
 * @author Fillipe QUeiroz
 * @export
 * @class ClassificacaoABO
 */
export class ClassificacaoABOPK extends BaseEntidade {
    public jsonToEntity(res: any) {
        throw new Error("Method not implemented.");
    }

    private _abo: string;
    private _aboRelacionado: string;


    /**
     * Getter abo
     * @return {string}
     */
	public get abo(): string {
		return this._abo;
	}

    /**
     * Setter abo
     * @param {string} value
     */
	public set abo(value: string) {
		this._abo = value;
	}

    /**
     * Getter aboRelacionado
     * @return {string}
     */
	public get aboRelacionado(): string {
		return this._aboRelacionado;
	}

    /**
     * Setter aboRelacionado
     * @param {string} value
     */
	public set aboRelacionado(value: string) {
		this._aboRelacionado = value;
	}
	
}