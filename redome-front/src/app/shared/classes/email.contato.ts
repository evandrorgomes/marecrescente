import { BaseEntidade } from '../base.entidade';
import { ConvertUtil } from 'app/shared/util/convert.util';

/**
 * Classe Bean utilizada para definir os campos de email
 * 
 * @export
 * @class EmailContato
 */
export class EmailContato extends BaseEntidade {

    private _id: number;
	private _email: string;
	private _principal: boolean;
	private _excluido: boolean;
	
	public get id(): number {
		return this._id;
	}

	public set id(value: number) {
		this._id = value;
	}

	public get email(): string {
		return this._email;
	}

	public set email(value: string) {
		this._email = value;
	}

	public get principal(): boolean {
		return this._principal;
	}

	public set principal(value: boolean) {
		this._principal = value;
	}

	public get excluido(): boolean {
		return this._excluido;
	}

	public set excluido(value: boolean) {
		this._excluido = value;
	}

	public jsonToEntity(res: any): EmailContato {
		
		this.id = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
		this.email = ConvertUtil.parseJsonParaAtributos(res.email, new String());
		this.principal = ConvertUtil.parseJsonParaAtributos(res.principal, new Boolean());
		this.excluido = ConvertUtil.parseJsonParaAtributos(res.excluido, new Boolean());

		return this;
    }
}