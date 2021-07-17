import { BaseEntidade } from '../../../shared/base.entidade';
import { MatchDTO } from "../../../shared/component/listamatch/match.dto";
import { ClassificacaoABOPK } from './classificacao.abo.pk';

/**
 * Classe que representa uma classificacao abo.
 * 
 * @author Fillipe QUeiroz
 * @export
 * @class ClassificacaoABO
 */
export class ClassificacaoABO extends BaseEntidade {

    private _prioridade: number;
    private _id: ClassificacaoABOPK;

    /**
     * Getter prioridade
     * @return {number}
     */
    public get prioridade(): number {
        return this._prioridade;
    }

    /**
     * Setter prioridade
     * @param {number} value
     */
    public set prioridade(value: number) {
        this._prioridade = value;
    }

    /**
     * Getter id
     * @return {ClassificacaoABOPK}
     */
    public get id(): ClassificacaoABOPK {
        return this._id;
    }

    /**
     * Setter id
     * @param {ClassificacaoABOPK} value
     */
    public set id(value: ClassificacaoABOPK) {
        this._id = value;
    }

    public jsonToEntity(res: any) {
        throw new Error("Method not implemented.");
    }

}