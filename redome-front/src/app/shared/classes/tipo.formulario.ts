import { ConvertUtil } from 'app/shared/util/convert.util';
import { BaseEntidade } from '../base.entidade';

export class TipoFormulario extends BaseEntidade {

    private _id: number;
    private _desricao: string;

	constructor(id?: number, descricao?: string) {
        super();
        this._id = id;
        this._desricao = descricao;
	}

	public get id(): number {
		return this._id;
	}

	public set id(value: number) {
		this._id = value;
	}
    

	public get desricao(): string {
		return this._desricao;
	}

	public set desricao(value: string) {
		this._desricao = value;
	}
 
	public jsonToEntity(res: any): TipoFormulario {
		
		this.id 		= ConvertUtil.parseJsonParaAtributos(res.id, new Number());
        this.desricao 	= ConvertUtil.parseJsonParaAtributos(res.descricao, new String());

        return this;
    }

}