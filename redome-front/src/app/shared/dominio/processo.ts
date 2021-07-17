import { BaseEntidade } from '../base.entidade';
import { Paciente } from '../../paciente/paciente';
import { ConvertUtil } from '../util/convert.util';

/*
*Classe que representa um Processo
*/
export class Processo extends BaseEntidade {

	private _id: number;
	private _paciente: Paciente;

	constructor(id?: number) {
		super()
		this._id = id;
	}	
	
	/**
	 * @returns number
	 */
	public get id(): number {
		return this._id;
	}
	/**
	 * @param  {number} value
	 */
	public set id(value: number) {
		this._id = value;
	}

    /**
     * Paciente associado ao processo. Foi trazido para front-end
	 * quando for necess�rio exibir informa��o referente a ele.
     * @return {Paciente}
     */
	public get paciente(): Paciente {
		return this._paciente;
	}

	public set paciente(value: Paciente) {
		this._paciente = value;
	}

	public jsonToEntity(res:any):Processo{
		if (res.paciente) {
			this.paciente = new Paciente().jsonToEntity(res.paciente);
		}
		this.id = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
		return this;
	}
    
}