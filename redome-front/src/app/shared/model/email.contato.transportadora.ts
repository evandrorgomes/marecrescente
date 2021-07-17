import { EmailContato } from '../classes/email.contato';
import { Transportadora } from './transportadora';
export class EmailContatoTransportadora extends EmailContato {

    private _transportadora: Transportadora;


    /**
     * Getter transportadora
     * @return {Transportadora}
     */
	public get transportadora(): Transportadora {
		return this._transportadora;
	}

    /**
     * Setter transportadora
     * @param {Transportadora} value
     */
	public set transportadora(value: Transportadora) {
		this._transportadora = value;
	}


    public jsonToEntity(res: any): EmailContatoTransportadora {
		if (res.transportadora) {
			this._transportadora = new Transportadora().jsonToEntity(res.doador);
		}
		super.jsonToEntity(res);

        return this;
    }

}
