import { ArquivoPedidoAdicionalWorkup } from "app/doador/cadastro/resultadoworkup/resultado/arquivo.pedido.adicional.workup";
import { ConvertUtil } from "app/shared/util/convert.util";
import { BaseEntidade } from "../base.entidade";

export class RetornoInclusaoExameAdicionalDTO extends BaseEntidade {

	private _mensagem: string;
  private _arquivosExamesAdicionais: ArquivoPedidoAdicionalWorkup[];

  /**
   * Getter mensagem
   * @return {string}
   */
	public get mensagem(): string {
		return this._mensagem;
	}

  /**
   * Getter arquivosExamesAdicionais
   * @return {ArquivoPedidoAdicionalWorkup[]}
   */
	public get arquivosExamesAdicionais(): ArquivoPedidoAdicionalWorkup[] {
		return this._arquivosExamesAdicionais;
	}

  /**
   * Setter mensagem
   * @param {string} value
   */
	public set mensagem(value: string) {
		this._mensagem = value;
	}

  /**
   * Setter arquivosExamesAdicionais
   * @param {ArquivoPedidoAdicionalWorkup[]} value
   */
	public set arquivosExamesAdicionais(value: ArquivoPedidoAdicionalWorkup[]) {
		this._arquivosExamesAdicionais = value;
	}

  public jsonToEntity(res: any): RetornoInclusaoExameAdicionalDTO {

    if (res.arquivosExamesAdicionais) {
      this._arquivosExamesAdicionais = [];
      res.arquivosExamesAdicionais.forEach(arquivo => {
          this._arquivosExamesAdicionais.push(new ArquivoPedidoAdicionalWorkup().jsonToEntity(arquivo));
      });
    }

    this.mensagem  = ConvertUtil.parseJsonParaAtributos(res.mensagem, new String());

      return this;
  }

}
