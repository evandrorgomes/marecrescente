import { BaseEntidade } from 'app/shared/base.entidade';
import { PreCadastroMedico } from './precadastro.medico';
import { ConvertUtil } from 'app/shared/util/convert.util';

export class PreCadastroMedicoEmail extends BaseEntidade {
    
    private _id: number;
	private _email: string;
    private _principal: boolean;
    private _preCadastroMedico: PreCadastroMedico;
    
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

    /**
     * Getter preCadastro
     * @return {PreCadastro}
     */
	public get preCadastroMedico(): PreCadastroMedico {
		return this._preCadastroMedico;
	}

    /**
     * Setter preCadastro
     * @param {PreCadastro} value
     */
	public set preCadastroMedico(value: PreCadastroMedico) {
		this._preCadastroMedico = value;
	}
    

    public jsonToEntity(res: any): PreCadastroMedicoEmail {

        if (res.preCadastroMedico) {
            this.preCadastroMedico = new PreCadastroMedico().jsonToEntity(res.preCadastro);
        }

        this.id = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
        this.email = ConvertUtil.parseJsonParaAtributos(res.email, new String());
        this.principal = ConvertUtil.parseJsonParaAtributos(res.principal, new Boolean());

        return this;
    }

}