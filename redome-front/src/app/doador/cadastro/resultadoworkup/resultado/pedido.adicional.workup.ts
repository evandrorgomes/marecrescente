import { ConvertUtil } from 'app/shared/util/convert.util';
import { BaseEntidade } from '../../../../shared/base.entidade';
import { ArquivoPedidoAdicionalWorkup } from './arquivo.pedido.adicional.workup';

export class PedidoAdicionalWorkup extends BaseEntidade {

  private _id: number;
	private _descricao: string;
  private _arquivosPedidoAdicionalWorkup: ArquivoPedidoAdicionalWorkup[] = [];
  private _pedidoWorkup: number;

    /**
     * Getter id
     * @return {number}
     */
	public get id(): number {
		return this._id;
	}

    /**
     * Setter id
     * @param {number} value
     */
	public set id(value: number) {
		this._id = value;
	}


    /**
     * Getter descricao
     * @return {string}
     */
	public get descricao(): string {
		return this._descricao;
	}

    /**
     * Getter arquivosPedidoAdicionalWorkup
     * @return {ArquivoPedidoAdicionalWorkup[] }
     */
	public get arquivosPedidoAdicionalWorkup(): ArquivoPedidoAdicionalWorkup[]  {
		return this._arquivosPedidoAdicionalWorkup;
	}

    /**
     * Getter pedidoWorkup
     * @return {number}
     */
	public get pedidoWorkup(): number {
		return this._pedidoWorkup;
	}

    /**
     * Setter descricao
     * @param {string} value
     */
	public set descricao(value: string) {
		this._descricao = value;
	}

    /**
     * Setter arquivosPedidoAdicionalWorkup
     * @param {ArquivoPedidoAdicionalWorkup[] } value
     */
	public set arquivosPedidoAdicionalWorkup(value: ArquivoPedidoAdicionalWorkup[] ) {
		this._arquivosPedidoAdicionalWorkup = value;
	}

    /**
     * Setter pedidoWorkup
     * @param {number} value
     */
	public set pedidoWorkup(value: number) {
		this._pedidoWorkup = value;
	}


	public jsonToEntity(res: any): PedidoAdicionalWorkup {

    this._id = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
    this._descricao = ConvertUtil.parseJsonParaAtributos(res.descricao, new String());

    return this;
  }

}
