import { ContatoTelefonico } from "app/shared/classes/contato.telefonico";
import { Medico } from "app/shared/dominio/medico";

/**
 * Classe Bean utilizada para definir os campos de contato do médico.
 * 
 * @export
 * @class ContatoTelefonicoMedico
 */
export class ContatoTelefonicoMedico extends ContatoTelefonico {
    
    private _medico: Medico;

    /**
     * Getter medico
     * @return {Medico}
     */
	public get medico(): Medico {
		return this._medico;
	}

    /**
     * Setter medico
     * @param {Medico} value
     */
	public set medico(value: Medico) {
		this._medico = value;
	}


	public jsonToEntity(res: any): ContatoTelefonicoMedico {
        
        if (res.medico) {
            this.medico = new Medico().jsonToEntity(res);
        }
        super.jsonToEntity(res);

        return this;
    }
}