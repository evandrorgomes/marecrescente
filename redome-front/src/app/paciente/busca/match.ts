import { BaseEntidade } from '../../shared/base.entidade';
import { ConvertUtil } from '../../shared/util/convert.util';
import { Doador } from '../../doador/doador';
import { Busca } from 'app/paciente/busca/busca';

/**
 * Classe que representa um match de um doador com um paciente.
 * 
 * @author Bruno Sousa
 * @export
 * @class Match
 * @extends {BaseEntidade}
 */
export class Match extends BaseEntidade {
    
	private _id: number;
	private _doador:Doador;
	private _busca: Busca;

	public get id(): number {
		return this._id;
	}

	public set id(value: number) {
		this._id = value;
	}

    /**
     * Getter doador
     * @return {Doador}
     */
	public get doador(): Doador {
		return this._doador;
	}

    /**
     * Setter doador
     * @param {Doador} value
     */
	public set doador(value: Doador) {
		this._doador = value;
	}

	public jsonToEntity(res: any): this {

		if (res.doador) {
			this.doador = ConvertUtil.parseJsonDoadorParaDoador(res.doador);
		}
		if (res.busca) {
			this._busca = new Busca().jsonToEntity(res.busca);
		}

		this.id = ConvertUtil.parseJsonParaAtributos(res.id, new Number());

		return this;
		   
	}
	
	public get busca(): Busca {
		return this._busca;
	}

    public set busca(value: Busca) {
		this._busca = value;
	}

    
}