import { BaseEntidade } from "../base.entidade";
import { ArquivoVoucherLogistica } from "../model/arquivo.voucher.logistica";
import { TransporteTerrestre } from "../model/transporte.terrestre";

/**
 * Informação dos detalhes da logística necessária a determinado pedido de logística.
 */
export class LogisticaDoadorWorkupDTO extends BaseEntidade {

	private _transporteTerrestre: TransporteTerrestre[];
	private _aereos: ArquivoVoucherLogistica[];
	private _hospedagens: ArquivoVoucherLogistica[];
	private _observacao: string;

    /**
     * Getter transporteTerrestre
     * @return {TransporteTerrestre[]}
     */
	public get transporteTerrestre(): TransporteTerrestre[] {
		return this._transporteTerrestre;
	}

    /**
     * Getter aereos
     * @return {ArquivoVoucherLogistica[]}
     */
	public get aereos(): ArquivoVoucherLogistica[] {
		return this._aereos;
	}

    /**
     * Getter hospedagens
     * @return {ArquivoVoucherLogistica[]}
     */
	public get hospedagens(): ArquivoVoucherLogistica[] {
		return this._hospedagens;
	}

    /**
     * Getter observacao
     * @return {string}
     */
	public get observacao(): string {
		return this._observacao;
	}

    /**
     * Setter transporteTerrestre
     * @param {TransporteTerrestre[]} value
     */
	public set transporteTerrestre(value: TransporteTerrestre[]) {
		this._transporteTerrestre = value;
	}

    /**
     * Setter aereos
     * @param {ArquivoVoucherLogistica[]} value
     */
	public set aereos(value: ArquivoVoucherLogistica[]) {
		this._aereos = value;
	}

    /**
     * Setter hospedagens
     * @param {ArquivoVoucherLogistica[]} value
     */
	public set hospedagens(value: ArquivoVoucherLogistica[]) {
		this._hospedagens = value;
	}

    /**
     * Setter observacao
     * @param {string} value
     */
	public set observacao(value: string) {
		this._observacao = value;
	}


	public jsonToEntity(res: any) {
		throw new Error("Method not implemented.");
	}

}
