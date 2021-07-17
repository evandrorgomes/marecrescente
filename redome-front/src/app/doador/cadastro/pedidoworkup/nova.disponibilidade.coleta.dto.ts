import { PedidoWorkup } from '../../consulta/workup/pedido.workup';
import { Tarefa } from '../../../paciente/consulta/pendencia/tarefa';
import { Raca } from '../../../shared/dominio/raca';
import { Etnia } from '../../../shared/dominio/etnia';
import { UF } from '../../../shared/dominio/uf';
import { RessalvaDoador } from "../../ressalva.doador";
import { MotivoStatusDoador } from '../../inativacao/motivo.status.doador';
import { Prescricao } from '../../consulta/workup/prescricao';
import { BaseEntidade } from '../../../shared/base.entidade';

export class NovaDisponibilidadeColetaDTO extends BaseEntidade {
	public jsonToEntity(res: any) {
		throw new Error("Method not implemented.");
	}

	private _idCentroColeta: number;
	private _data: string;
	private _idDisponibilidadeCentroColeta: number;

	/**
	 * Identificador do centro de coleta.
	 * @returns number
	 */
	public get idCentroColeta(): number {
		return this._idCentroColeta;
	}
	/**
	 * Identificador do centro de coleta.
	 * @param  {number} value
	 */
	public set idCentroColeta(value: number) {
		this._idCentroColeta = value;
	}
	/**
	 * Data escrita pelo analista apenas para o proprio controle.
	 * @returns string
	 */
	public get data(): string {
		return this._data;
	}
	/**
	 * * Data escrita pelo analista apenas para o proprio controle.
	 * @param  {string} value
	 */
	public set data(value: string) {
		this._data = value;
	}
	/**
	 * Identificador da disponibilidade do centro de coleta.
	 * @returns number
	 */
	public get idDisponibilidadeCentroColeta(): number {
		return this._idDisponibilidadeCentroColeta;
	}
	/**
	 * Identificador da disponibilidade do centro de coleta.
	 * @param  {number} value
	 */
	public set idDisponibilidadeCentroColeta(value: number) {
		this._idDisponibilidadeCentroColeta = value;
	}


}