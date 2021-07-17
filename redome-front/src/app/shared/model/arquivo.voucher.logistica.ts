import { BaseEntidade } from '../base.entidade';
import { PedidoLogistica } from './pedido.logistica';

export class ArquivoVoucherLogistica extends BaseEntidade {
	public jsonToEntity(res: any) {
		throw new Error("Method not implemented.");
	}

    private _id: number;
	private _comentario: string;
	private _caminho: string;
    private _excluido: boolean;
    // 1 - Hospedagem, 2 - Aéreo
	private _tipo: number;

	private _pedidoLogistica: PedidoLogistica;

	public get id(): number {
		return this._id;
	}

	public set id(value: number) {
		this._id = value;
	}

	public get comentario(): string {
		return this._comentario;
	}

	public set comentario(value: string) {
		this._comentario = value;
	}

	public get caminho(): string {
		return this._caminho;
	}

	public set caminho(value: string) {
		this._caminho = value;
	}

	public get excluido(): boolean {
		return this._excluido;
	}

	public set excluido(value: boolean) {
		this._excluido = value;
	}

	public get tipo(): number {
		return this._tipo;
	}

	public set tipo(value: number) {
		this._tipo = value;
    }

    public get nomeSemTimestamp(): string {
        if (this._caminho) {
            let index = this._caminho.lastIndexOf("_");
            return this._caminho.substring(index + 1, this._caminho.length);
        }
		return "";
	}

	public get pedidoLogistica(): PedidoLogistica {
		return this._pedidoLogistica;
	}

	public set pedidoLogistica(value: PedidoLogistica) {
		this._pedidoLogistica = value;
	}



}
