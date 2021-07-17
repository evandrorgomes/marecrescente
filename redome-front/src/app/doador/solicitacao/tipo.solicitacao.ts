import {BaseEntidade} from '../../shared/base.entidade';
import { ConvertUtil } from 'app/shared/util/convert.util';

export class TipoSolicitacao extends BaseEntidade {
    private _id: Number;
    private _descricao: string;


	public get id(): Number {
		return this._id;
	}

	public set id(value: Number) {
		this._id = value;
	}
	
	public get descricao(): string {
		return this._descricao;
	}

	public set descricao(value: string) {
		this._descricao = value;
	}
	
	public jsonToEntity(res: any): TipoSolicitacao {
		this.id = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
		this.descricao = ConvertUtil.parseJsonParaAtributos(res.descricao, new String());
		return this;
    }
}