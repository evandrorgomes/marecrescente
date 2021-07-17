import { Doador } from './doador';
import { EnderecoContato } from '../shared/classes/endereco.contato';
import { DoadorNacional } from './doador.nacional';

/**
 * Classe Bean utilizada para definir os campos de contato do Doador
 * 
 * @export
 * @class ContatoEndereco
 */
export class EnderecoContatoDoador extends EnderecoContato {
    
    private _doador: DoadorNacional;
    

	public get doador(): DoadorNacional {
		return this._doador;
	}

	public set doador(value: DoadorNacional) {
		this._doador = value;
	}

	public jsonToEntity(res: any): EnderecoContatoDoador {
		if (res.doador) {
			this.doador = new DoadorNacional().jsonToEntity(res.doador);
		}
		super.jsonToEntity(res);

        return this;
    }

}