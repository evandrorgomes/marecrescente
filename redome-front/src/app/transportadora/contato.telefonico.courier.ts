import { Courier } from './courier';
import { ContatoTelefonico } from '../shared/classes/contato.telefonico';


/**
 * Classe Bean utilizada para definir os campos de contato do Courier
 * 
 * @export
 * @class ContatoTelefonicoCourier
 */
export class ContatoTelefonicoCourier extends ContatoTelefonico {

	private _courier: Courier;

    /**
     * Getter courier
     * @return {Courier}
     */
	public get courier(): Courier {
		return this._courier;
	}

    /**
     * Setter courier
     * @param {Courier} value
     */
	public set courier(value: Courier) {
		this._courier = value;
	}


	public jsonToEntity(res: any): ContatoTelefonicoCourier {

		super.jsonToEntity(res);

		if (res.courier) {
			this._courier = new Courier().jsonToEntity(res.courier);
		}

		return this;
	}


}