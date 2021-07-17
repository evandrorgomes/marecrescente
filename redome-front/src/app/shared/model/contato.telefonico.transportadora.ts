import { ContatoTelefonico } from 'app/shared/classes/contato.telefonico';
import { Transportadora } from './transportadora';

export class ContatoTelefonicoTransportadora extends ContatoTelefonico {

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


    public jsonToEntity(res: any):  ContatoTelefonicoTransportadora{

		if (res.transportadora) {
			this._transportadora = new Transportadora().jsonToEntity(res.doador);
		}
		super.jsonToEntity(res);

        return this;
    }

}
