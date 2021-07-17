import { BaseEntidade } from 'app/shared/base.entidade';
import { ConvertUtil } from 'app/shared/util/convert.util';


/**
 * Classe que representa o status do paciente
 * @author ergomes
 */
export class StatusPaciente extends BaseEntidade {

	private _id: number;
	private _descricao: string;
	private _ordem: number;

    /**
     * Getter id
     * @return {number}
     */
	public get id(): number {
		return this._id;
	}

    /**
     * Getter nome
     * @return {string}
     */
	public get descricao(): string {
		return this._descricao;
	}

    /**
     * Getter ordem
     * @return {number}
     */
	public get ordem(): number {
		return this._ordem;
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
     * @param {string} value
     */
	public set descricao(value: string) {
		this._descricao = value;
	}

    /**
     * Setter ordem
     * @param {number} value
     */
	public set ordem(value: number) {
		this._ordem = value;
	}

	public jsonToEntity(res:any):this{

		this.id  = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
		this.descricao = ConvertUtil.parseJsonParaAtributos(res.descricao, new String());
		this.ordem = ConvertUtil.parseJsonParaAtributos(res.ordem, new Number());

        return this;
	}


}

