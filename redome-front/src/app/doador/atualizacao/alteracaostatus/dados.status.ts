import { BaseEntidade } from '../../../shared/base.entidade';

export class DadosStatus extends BaseEntidade {

    private _status:number;
    private _motivoStatusDoador:number;
    private _tempoAfastamento:Date;

	/**
	 * @returns number
	 */
	public get status(): number {
		return this._status;
	}
	/**
	 * @param  {number} value
	 */
	public set status(value: number) {
		this._status = value;
	}
	/**
	 * @returns number
	 */
	public get motivoStatusDoador(): number {
		return this._motivoStatusDoador;
	}
	/**
	 * @param  {number} value
	 */
	public set motivoStatusDoador(value: number) {
		this._motivoStatusDoador = value;
	}
	/**
	 * @returns Date
	 */
	public get tempoAfastamento(): Date {
		return this._tempoAfastamento;
	}
	/**
	 * @param  {Date} value
	 */
	public set tempoAfastamento(value: Date) {
		this._tempoAfastamento = value;
	}
	public jsonToEntity(res: any) {
        throw new Error("Method not implemented.");
    }
    
}