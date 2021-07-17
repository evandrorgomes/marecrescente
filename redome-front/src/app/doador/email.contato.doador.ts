import { DoadorNacional } from './doador.nacional';
import { EmailContato } from '../shared/classes/email.contato';

/**
 * Classe Bean utilizada para definir os campos de contato de email do DoadorNacional
 * 
 * @export
 * @class EmailContatoDoador
 */
export class EmailContatoDoador extends EmailContato {
    
    private _doador: DoadorNacional;


	public get doador(): DoadorNacional {
		return this._doador;
	}

	public set doador(value: DoadorNacional) {
		this._doador = value;
	}

	public jsonToEntity(res: any): EmailContatoDoador {
		if (res.doador) {
			this.doador = new DoadorNacional().jsonToEntity(res.doador);
		}
		super.jsonToEntity(res);
		return this;		
	}
    

}