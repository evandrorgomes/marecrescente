import { Cid } from '../../../shared/dominio/cid';
import { BaseEntidade } from "../../../shared/base.entidade";
import { ConvertUtil } from 'app/shared/util/convert.util';
import { Paciente } from 'app/paciente/paciente';


/**
 * Classe Bean utilizada para definir os campos de Diagnóstico do Paciente
 * 
 * @author Bruno Sousa
 * @export
 * @class Diagnostico
 */
export class Diagnostico extends BaseEntidade {

	private _id: number;

	private _dataDiagnostico: Date;

	private _cid: Cid;

	private _paciente: Paciente;

	constructor(id?: number, dataDiagnostico?: Date, cid?: Cid) {
		super();
		this._id = id;
		this._dataDiagnostico = dataDiagnostico;
		this._cid = cid;
	}

	public get id(): number {
		return this._id;
	}

	public set id(value: number) {
		this._id = value;
	}

	public get dataDiagnostico(): Date {
		return this._dataDiagnostico;
	}

	public set dataDiagnostico(value: Date) {
		this._dataDiagnostico = value;
	}

	public get cid(): Cid {
		return this._cid;
	}

	public set cid(value: Cid) {
		this._cid = value;
	}

    /**
     * Getter paciente
     * @return {Paciente}
     */
	public get paciente(): Paciente {
		return this._paciente;
	}

    /**
     * Setter paciente
     * @param {Paciente} value
     */
	public set paciente(value: Paciente) {
		this._paciente = value;
	}

	public jsonToEntity(res:any): Diagnostico{

		if (res.paciente) {
			this.paciente = new Paciente().jsonToEntity(res.paciente);
		}

		if (res.cid) {
			this.cid = new Cid().jsonToEntity(res.cid);
		}
		
		this.id = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
		this.dataDiagnostico = ConvertUtil.parseJsonParaAtributos(res.dataDiagnostico, new Date());

		return this;
	}
}