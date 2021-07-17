import { ConvertUtil } from 'app/shared/util/convert.util';
import { BaseEntidade } from '../base.entidade';
import { UsuarioLogado } from '../dominio/usuario.logado';
import { PedidoWorkup } from 'app/doador/consulta/workup/pedido.workup';
import { TipoPedidoLogistica } from '../dominio/tipo.pedido.logistica';
import { StatusPedidoLogistica } from '../dominio/status.pedido.logistica';
import { PedidoColeta } from 'app/doador/consulta/coleta/pedido.coleta';
import { PedidoTransporte } from 'app/transportadora/tarefas/pedido.transporte';

export class PedidoLogistica extends BaseEntidade {


	private _id: number;
	private _dataCriacao: Date;
	private _dataAtualizacao: Date;
	private _observacao: string;
	private _usuarioResponsavel: UsuarioLogado;
	private _pedidoWorkup: PedidoWorkup;
	private _tipo: TipoPedidoLogistica;
	private _status: StatusPedidoLogistica;
	private _pedidoColeta: PedidoColeta;
	private _pedidoTransporte: PedidoTransporte;


	public get id(): number {
		return this._id;
	}

	public set id(value: number) {
		this._id = value;
	}

	public get dataCriacao(): Date {
		return this._dataCriacao;
	}

	public set dataCriacao(value: Date) {
		this._dataCriacao = value;
	}

	public get dataAtualizacao(): Date {
		return this._dataAtualizacao;
	}

	public set dataAtualizacao(value: Date) {
		this._dataAtualizacao = value;
	}

	public get observacao(): string {
		return this._observacao;
	}

	public set observacao(value: string) {
		this._observacao = value;
	}

	public get usuarioResponsavel(): UsuarioLogado {
		return this._usuarioResponsavel;
	}

	public set usuarioResponsavel(value: UsuarioLogado) {
		this._usuarioResponsavel = value;
	}

	public get tipo(): TipoPedidoLogistica {
		return this._tipo;
	}

	public set tipo(value: TipoPedidoLogistica) {
		this._tipo = value;
	}

	public get status(): StatusPedidoLogistica {
		return this._status;
	}

	public set status(value: StatusPedidoLogistica) {
		this._status = value;
	}

	public get pedidoColeta(): PedidoColeta {
		return this._pedidoColeta;
	}

	public set pedidoColeta(value: PedidoColeta) {
		this._pedidoColeta = value;
	}

	public get pedidoTransporte(): PedidoTransporte {
		return this._pedidoTransporte;
	}

	public set pedidoTransporte(value: PedidoTransporte) {
		this._pedidoTransporte = value;
	}

    /**
     * Getter pedidoWorkup
     * @return {PedidoWorkup}
     */
	public get pedidoWorkup(): PedidoWorkup {
		return this._pedidoWorkup;
	}

    /**
     * Setter pedidoWorkup
     * @param {PedidoWorkup} value
     */
	public set pedidoWorkup(value: PedidoWorkup) {
		this._pedidoWorkup = value;
	}


	public jsonToEntity(res: any):this {

    if(res.pedidoWorkup){
      this._pedidoWorkup = new PedidoWorkup().jsonToEntity(res.pedidoWorkup);
    }

    if(res.pedidoColeta){
  		this._pedidoColeta = new PedidoColeta().jsonToEntity(res.pedidoColeta);
    }

    if(res.pedidoTransporte){
      this._pedidoTransporte = new PedidoTransporte().jsonToEntity(res.pedidoTransporte);
    }

    if(res.status){
      this._status = new StatusPedidoLogistica().jsonToEntity(res.status);
    }

    if(res.tipo){
      this._tipo = new TipoPedidoLogistica().jsonToEntity(res.tipo);
    }

    if(res.usuarioResposnsavel){
      this._usuarioResponsavel = new UsuarioLogado().jsonToEntity(res.usuarioResposnsavel);
    }

		this.id = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
		this.dataAtualizacao = ConvertUtil.parseJsonParaAtributos(res.dataAtualizacao, new Date());
		this.dataCriacao = ConvertUtil.parseJsonParaAtributos(res.dataCriacao, new Date());
		this.observacao = ConvertUtil.parseJsonParaAtributos(res.observacao, new String());

    return this;
	}

}
