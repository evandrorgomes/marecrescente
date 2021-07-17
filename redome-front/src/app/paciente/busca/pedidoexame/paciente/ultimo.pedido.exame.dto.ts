import { PedidoExame } from "../../../../laboratorio/pedido.exame";
import { Laboratorio } from "../../../../shared/dominio/laboratorio";

export class UltimoPedidoExameDTO {

    private _rmr: number;
    private _municipioEnderecoPaciente: string;
    private _ufEnderecoPaciente: string;
    private _laboratorioDePrefencia: Laboratorio;
    private _pedidoExame: PedidoExame;

    constructor() {

    }

    /**
     * Getter rmr
     * @return {number}
     */
	public get rmr(): number {
		return this._rmr;
	}

    /**
     * Setter rmr
     * @param {number} value
     */
	public set rmr(value: number) {
		this._rmr = value;
	}

    /**
     * Getter municipioEnderecoPaciente
     * @return {string}
     */
	public get municipioEnderecoPaciente(): string {
		return this._municipioEnderecoPaciente;
	}

    /**
     * Setter municipioEnderecoPaciente
     * @param {string} value
     */
	public set municipioEnderecoPaciente(value: string) {
		this._municipioEnderecoPaciente = value;
	}

    /**
     * Getter ufEnderecoPaciente
     * @return {string}
     */
	public get ufEnderecoPaciente(): string {
		return this._ufEnderecoPaciente;
	}

    /**
     * Setter ufEnderecoPaciente
     * @param {string} value
     */
	public set ufEnderecoPaciente(value: string) {
		this._ufEnderecoPaciente = value;
	}

    /**
     * Getter pedidoExame
     * @return {PedidoExame}
     */
	public get pedidoExame(): PedidoExame {
		return this._pedidoExame;
	}

    /**
     * Setter pedidoExame
     * @param {PedidoExame} value
     */
	public set pedidoExame(value: PedidoExame) {
		this._pedidoExame = value;
    }

    /**
     * Getter laboratorioDePrefencia
     * @return {Laboratorio}
     */
	public get laboratorioDePrefencia(): Laboratorio {
		return this._laboratorioDePrefencia;
	}

    /**
     * Setter laboratorioDePrefencia
     * @param {Laboratorio} value
     */
	public set laboratorioDePrefencia(value: Laboratorio) {
		this._laboratorioDePrefencia = value;
	}
    

}