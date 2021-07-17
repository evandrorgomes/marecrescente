import { constructor } from 'events';
import { forEach } from '@angular/router/src/utils/collection';
import { Doador } from './doador';
import { Registro } from '../shared/dominio/registro';
import { ExameCordaoInternacional } from './exame.cordao.internacional';
import { ConvertUtil } from '../shared/util/convert.util';
import { TiposDoador } from 'app/shared/enums/tipos.doador';
import {ICordao} from "./ICordao";

export class CordaoInternacional extends Doador implements ICordao {

	private _idRegistro: string;
    private _registroPagamento: Registro;
    private _exames: ExameCordaoInternacional[];
	private _cadastradoEmdis: Boolean;
	private _quantidadeTotalTCN:number;
    private _quantidadeTotalCD34:number;
    private _volume:number;

    constructor() {
        super();
        this.tipoDoador = TiposDoador.CORDAO_INTERNACIONAL;
    }

    /**
     * Getter idRegistro
     * @return {string}
     */
	public get idRegistro(): string {
		return this._idRegistro;
	}

    /**
     * Setter idRegistro
     * @param {string} value
     */
	public set idRegistro(value: string) {
		this._idRegistro = value;
	}

    /**
     * Getter registroPagamento
     * @return {Registro}
     */
	public get registroPagamento(): Registro {
		return this._registroPagamento;
	}

    /**
     * Setter registroPagamento
     * @param {Registro} value
     */
	public set registroPagamento(value: Registro) {
		this._registroPagamento = value;
	}

    /**
     * Getter exames
     * @return {ExameCordaoInternacional[]}
     */
	public get exames(): ExameCordaoInternacional[] {
		return this._exames;
	}

    /**
     * Setter exames
     * @param {ExameCordaoInternacional[]} value
     */
	public set exames(value: ExameCordaoInternacional[]) {
		this._exames = value;
	}

    /**
     * Getter cadastradoEmdis
     * @return {Boolean}
     */
	public get cadastradoEmdis(): Boolean {
		return this._cadastradoEmdis;
	}

    /**
     * Setter cadastradoEmdis
     * @param {Boolean} value
     */
	public set cadastradoEmdis(value: Boolean) {
		this._cadastradoEmdis = value;
	}

    /**
     * Getter quantidadeTotalTCN
     * @return {number}
     */
	public get quantidadeTotalTCN(): number {
		return this._quantidadeTotalTCN;
	}

    /**
     * Setter quantidadeTotalTCN
     * @param {number} value
     */
	public set quantidadeTotalTCN(value: number) {
		this._quantidadeTotalTCN = value;
	}

    /**
     * Getter quantidadeTotalCD34
     * @return {number}
     */
	public get quantidadeTotalCD34(): number {
		return this._quantidadeTotalCD34;
	}

    /**
     * Setter quantidadeTotalCD34
     * @param {number} value
     */
	public set quantidadeTotalCD34(value: number) {
		this._quantidadeTotalCD34 = value;
	}

    /**
     * Getter volume
     * @return {number}
     */
	public get volume(): number {
		return this._volume;
	}

    /**
     * Setter volume
     * @param {number} value
     */
	public set volume(value: number) {
		this._volume = value;
	}

	get identificacao(): string {
		return this._idRegistro;
	}

	public get bancoOrigem(): string {
		if (this.registroOrigem && this.registroOrigem.nome) {
			return this.registroOrigem.nome;
		}
		return null;
	}

	public jsonToEntity(res: any): this {

        if (res.registroPagamento) {
            this.registroPagamento = new Registro().jsonToEntity(res.registroPagamento);
        }

        if (res.exames) {
            this.exames = [];
            res.exames.forEach(exame => {
                this.exames.push(new ExameCordaoInternacional().jsonToEntity(exame));
            })
        }

		this.idRegistro = ConvertUtil.parseJsonParaAtributos(res.idRegistro, new String());
	    this.cadastradoEmdis = ConvertUtil.parseJsonParaAtributos(res.cadastradoEmdis, new Boolean());
	    this.quantidadeTotalTCN = ConvertUtil.parseJsonParaAtributos(res.quantidadeTotalTCN, new Number());
	    this.quantidadeTotalCD34 = ConvertUtil.parseJsonParaAtributos(res.quantidadeTotalCD34, new Number());
        this.volume = ConvertUtil.parseJsonParaAtributos(res.volume, new Number());
 
        super.jsonToEntity(res);

        return this;
	}
}
