import { EmailContato } from "app/shared/classes/email.contato";
import { Laboratorio } from 'app/shared/dominio/laboratorio';

/**
 * Classe Bean utilizada para definir os campos de contato de email do Laboratório
 * 
 * @export
 * @class EmailContatoLaboratorio
 */
export class EmailContatoLaboratorio extends EmailContato {
    
    private _laboratorio: Laboratorio;

    /**
     * Getter laboratorio
     * @return {Laboratorio}
     */
	public get laboratorio(): Laboratorio {
		return this._laboratorio;
	}

    /**
     * Setter laboratorio
     * @param {Laboratorio} value
     */
	public set laboratorio(value: Laboratorio) {
		this._laboratorio = value;
	}
    

	public jsonToEntity(res: any): EmailContatoLaboratorio {
        if (res.laboratorio) {
            this.laboratorio = new Laboratorio().jsonToEntity(res.laboratorio);
        }
        super.jsonToEntity(res);

        return this;
	}
    

}