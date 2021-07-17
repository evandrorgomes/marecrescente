import { EmailContato } from "app/shared/classes/email.contato";
import { CentroTransplante } from 'app/shared/dominio/centro.transplante';

/**
 * Classe Bean utilizada para definir os campos de contato de email do centro de transplante
 * 
 * @export
 * @class EmailContatoCentroTransplante
 */
export class EmailContatoCentroTransplante extends EmailContato {
    
    private _centroTransplante: CentroTransplante;

    /**
     * Getter centroTransplante
     * @return {CentroTransplante}
     */
	public get centroTransplante(): CentroTransplante {
		return this._centroTransplante;
	}

    /**
     * Setter centroTransplante
     * @param {CentroTransplante} value
     */
	public set centroTransplante(value: CentroTransplante) {
		this._centroTransplante = value;
	}
    
	public jsonToEntity(res: any): EmailContatoCentroTransplante {
        if (res.centroTransplante) {
            this.centroTransplante = new CentroTransplante().jsonToEntity(res.centroTransplante);
        }
        super.jsonToEntity(res);

        return this;
	}
    

}