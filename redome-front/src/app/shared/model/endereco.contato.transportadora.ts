import { EnderecoContato } from '../classes/endereco.contato';
import { Transportadora } from './transportadora';

export class EnderecoContatoTransportadora extends EnderecoContato {

    private _transportadora: Transportadora;

	public jsonToEntity(res: any): EnderecoContatoTransportadora {
		if (res.transportadora) {
			this._transportadora = new Transportadora().jsonToEntity(res.doador);
		}
		super.jsonToEntity(res);

        return this;
    }

}
