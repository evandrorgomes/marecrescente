import { ConvertUtil } from 'app/shared/util/convert.util';
import { BaseEntidade } from '../../../../shared/base.entidade';

/**
 * Classe de referencia aos arquivos vinculados ao pedido adicional de workup.
 *
 * @author ergomes
 */
export class ArquivoPedidoAdicionalWorkup extends BaseEntidade {
    private _id: Number;
    private _caminho: String;
    private _descricao: String;
    private _pedidoAdicional: number;

    public get id(): Number {
        return this._id;
    }

    public set id(value: Number) {
        this._id = value;
    }

    public get caminho(): String {
        return this._caminho;
    }

    public set caminho(value: String) {
        this._caminho = value;
    }

    /**
     * Getter descricao
     * @return {String}
     */
    public get descricao(): String {
      return this._descricao;
    }

    /**
     * Setter descricao
     * @param {String} value
     */
    public set descricao(value: String) {
      this._descricao = value;
    }


    /**
     * Getter pedidoAdicional
     * @return {number}
     */
    public get pedidoAdicional(): number {
      return this._pedidoAdicional;
    }

    /**
     * Setter pedidoAdicional
     * @param {number} value
     */
    public set pedidoAdicional(value: number) {
      this._pedidoAdicional = value;
    }

    public jsonToEntity(res:any): ArquivoPedidoAdicionalWorkup{

      this._id = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
      this._caminho = ConvertUtil.parseJsonParaAtributos(res.caminho, new String());
      this._descricao = ConvertUtil.parseJsonParaAtributos(res.descricao, new String());

		return this;
	}
}
