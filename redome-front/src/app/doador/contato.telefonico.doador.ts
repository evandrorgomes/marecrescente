import { ContatoTelefonico } from '../shared/classes/contato.telefonico';
import { DoadorNacional } from './doador.nacional';
import { ConvertUtil } from 'app/shared/util/convert.util';


/**
 * Classe Bean utilizada para definir os campos de contato do DoadorNacional
 * 
 * @export
 * @class ContatoTelefonicoDoador
 */
export class ContatoTelefonicoDoador extends ContatoTelefonico {

	private _doador: DoadorNacional;
 	private _atendidoPeloDoador: boolean;	
	private _naoAtendido: boolean;

	public get doador(): DoadorNacional {
		return this._doador;
	}

	public set doador(value: DoadorNacional) {
		this._doador = value;
	}

 	public get atendidoPeloDoador(): boolean {
		return this._atendidoPeloDoador;
	}

	public set atendidoPeloDoador(value: boolean) {
		this._atendidoPeloDoador = value;
	}

	public get naoAtendido(): boolean {
		return this._naoAtendido;
	}

	public set naoAtendido(value: boolean) {
		this._naoAtendido = value;
	}

	public jsonToEntity(res: any): ContatoTelefonicoDoador {
		if (res.doador) {
			this.doador = new DoadorNacional().jsonToEntity(res.doador);
		}

		this.atendidoPeloDoador = ConvertUtil.parseJsonParaAtributos(res.atendidoPeloDoador, new Boolean());
		this.naoAtendido = ConvertUtil.parseJsonParaAtributos(res.naoAtendido, new Boolean());
		super.jsonToEntity(res);

		return this;
    }
	
	
}