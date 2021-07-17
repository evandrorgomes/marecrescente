import { Doador } from './doador';
import { Registro } from '../shared/dominio/registro';
import { ExameCordaoInternacional } from './exame.cordao.internacional';
import { BancoSangueCordao } from '../shared/dominio/banco.sangue.cordao';
import { ConvertUtil } from '../shared/util/convert.util';
import { TiposDoador } from 'app/shared/enums/tipos.doador';
import {ICordao} from "./ICordao";

export class CordaoNacional extends Doador implements ICordao {

	private _idBancoSangueCordao: string;
    private _bancoSangueCordao: BancoSangueCordao;
	private _quantidadeTotalTCN:number;
	private _quantidadeTotalCD34:number;
    private _volume:number;

    constructor() {
        super();
        this.tipoDoador = TiposDoador.CORDAO_NACIONAL;
    }

    /**
     * Getter idBancoSangueCordao
     * @return {string}
     */
	public get idBancoSangueCordao(): string {
		return this._idBancoSangueCordao;
	}

    /**
     * Setter idBancoSangueCordao
     * @param {string} value
     */
	public set idBancoSangueCordao(value: string) {
		this._idBancoSangueCordao = value;
	}

    /**
     * Getter bancoSangueCordao
     * @return {Registro}
     */
	public get bancoSangueCordao(): BancoSangueCordao {
		return this._bancoSangueCordao;
	}

    /**
     * Setter bancoSangueCordao
     * @param {Registro} value
     */
	public set bancoSangueCordao(value: BancoSangueCordao) {
		this._bancoSangueCordao = value;
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

	get identificacao(): string {
		return this.idBancoSangueCordao;
	}

	/**
     * Setter volume
     * @param {number} value
     */
	public set volume(value: number) {
		this._volume = value;
    }

   public get bancoOrigem(): string {
		if (this._bancoSangueCordao && this._bancoSangueCordao.nome) {
			return this._bancoSangueCordao.nome;
		}
		return null;
	}

    public jsonToEntity(res: any): this {

        if (res.bancoSangueCordao) {
            this.bancoSangueCordao = new BancoSangueCordao().jsonToEntity(res.bancoSangueCordao);
        }

        this.idBancoSangueCordao = ConvertUtil.parseJsonParaAtributos(res.idBancoSangueCordao, new String());
        this.quantidadeTotalTCN = ConvertUtil.parseJsonParaAtributos(res.quantidadeTotalTCN, new Number());
        this.quantidadeTotalCD34 = ConvertUtil.parseJsonParaAtributos(res.quantidadeTotalCD34, new Number());
        this.volume = ConvertUtil.parseJsonParaAtributos(res.volume, new Number());
        super.jsonToEntity(res);

        return this;
    }


}
