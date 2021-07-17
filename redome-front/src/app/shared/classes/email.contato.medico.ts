import { EmailContato } from "app/shared/classes/email.contato";
import { Medico } from "app/shared/dominio/medico";

/**
 * Classe Bean utilizada para definir os campos de contato de email do Médico
 * 
 * @export
 * @class EmailContatoMedico
 */
export class EmailContatoMedico extends EmailContato {
    
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

	public jsonToEntity(res: any): EmailContatoMedico {
        if (res.medico) {
            this.medico = new Medico().jsonToEntity(res.medico);
        }
        super.jsonToEntity(res);

        return this;
	}
    

}