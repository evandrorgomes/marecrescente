import { UsuarioLogado } from "../../../shared/dominio/usuario.logado";
import { BaseEntidade } from "../../../shared/base.entidade";
import { StatusPedidoTransporte } from "../status.pedido.transporte";
import { EnderecoContato } from "../../../shared/classes/endereco.contato";


export class ConfirmacaoTransporteDTO extends BaseEntidade {
    public jsonToEntity(res: any) {
        throw new Error("Method not implemented.");
    }

    private _dadosVoo: string;
    private _idCourier: number;
    private _voo: boolean;

    /**
     * Getter dadosVoo
     * @return {string}
     */
	public get dadosVoo(): string {
		return this._dadosVoo;
	}

    /**
     * Setter dadosVoo
     * @param {string} value
     */
	public set dadosVoo(value: string) {
		this._dadosVoo = value;
	}


    /**
     * Getter idCourier
     * @return {number}
     */
	public get idCourier(): number {
		return this._idCourier;
	}

    /**
     * Setter idCourier
     * @param {number} value
     */
	public set idCourier(value: number) {
		this._idCourier = value;
	}


    /**
     * Getter voo
     * @return {boolean}
     */
	public get voo(): boolean {
		return this._voo;
	}

    /**
     * Setter voo
     * @param {boolean} value
     */
	public set voo(value: boolean) {
		this._voo = value;
	}

}
