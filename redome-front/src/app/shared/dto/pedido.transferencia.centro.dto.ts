import { PedidoTransferenciaCentro } from './../classes/pedido.transferencia.centro';
import { BaseEntidade } from 'app/shared/base.entidade';
import { Evolucao } from 'app/paciente/cadastro/evolucao/evolucao';

export class PedidoTransferenciaCentroDTO {

    private _pedidoTransferenciaCentro: PedidoTransferenciaCentro;
    private _ultimaEvolucao: Evolucao;

    /**
     * Getter pedidoTransferenciaCentro
     * @return {PedidoTransferenciaCentro}
     */
	public get pedidoTransferenciaCentro(): PedidoTransferenciaCentro {
		return this._pedidoTransferenciaCentro;
	}

    /**
     * Setter pedidoTransferenciaCentro
     * @param {PedidoTransferenciaCentro} value
     */
	public set pedidoTransferenciaCentro(value: PedidoTransferenciaCentro) {
		this._pedidoTransferenciaCentro = value;
	}

    /**
     * Getter ultimaEvolucao
     * @return {Evolucao}
     */
	public get ultimaEvolucao(): Evolucao {
		return this._ultimaEvolucao;
	}

    /**
     * Setter ultimaEvolucao
     * @param {Evolucao} value
     */
	public set ultimaEvolucao(value: Evolucao) {
		this._ultimaEvolucao = value;
	}

    public jsonToEntity(res: any): PedidoTransferenciaCentroDTO  {

        if (res.pedidoTransferenciaCentro) {
            this.pedidoTransferenciaCentro = new PedidoTransferenciaCentro().jsonToEntity(res.pedidoTransferenciaCentro);
        }

        if (res.ultimaEvolucao) {
            this.ultimaEvolucao = new Evolucao().jsonToEntity(res.ultimaEvolucao);
        }

        return this;
    }

}