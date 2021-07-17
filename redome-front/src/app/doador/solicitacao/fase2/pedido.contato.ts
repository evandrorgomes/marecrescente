import { ConvertUtil } from 'app/shared/util/convert.util';
import { BaseEntidade } from "../../../shared/base.entidade";
import { HemoEntidade } from '../hemoentidade';
import { Solicitacao } from "../solicitacao";
import { TentativaContatoDoador } from './tentativa.contato.doador';

export class PedidoContato extends BaseEntidade {
	private _id: number;
	private _dataCriacao: Date;
	private _aberto: Boolean;
	private _solicitacao: Solicitacao;
	private _hemoEntidade: HemoEntidade;
	private _tentativasContato: TentativaContatoDoador[];
	private _descricaoStatus: string;
	/**
	 * @returns Number
	 */
	public get id(): number {
		return this._id;
	}
	/**
	 * @param  {Number} value
	 */
	public set id(value: number) {
		this._id = value;
	}
	/**
	 * @returns Date
	 */
	public get dataCriacao(): Date {
		return this._dataCriacao;
	}
	/**
	 * @param  {Date} value
	 */
	public set dataCriacao(value: Date) {
		this._dataCriacao = value;
	}
	/**
	 * @returns Boolean
	 */
	public get aberto(): Boolean {
		return this._aberto;
	}
	/**
	 * @param  {Boolean} value
	 */
	public set aberto(value: Boolean) {
		this._aberto = value;
	}
	/**
	 * @returns Solicitacao
	 */
	public get solicitacao(): Solicitacao {
		return this._solicitacao;
	}
	/**
	 * @param  {Solicitacao} value
	 */
	public set solicitacao(value: Solicitacao) {
		this._solicitacao = value;
	}
	/**
	 * @returns HemoEntidade
	 */
	public get hemoEntidade(): HemoEntidade {
		return this._hemoEntidade;
	}
	/**
	 * @param  {HemoEntidade} value
	 */
	public set hemoEntidade(value: HemoEntidade) {
		this._hemoEntidade = value;
	}
	/**
	 * @returns TentativaContatoDoador
	 */
	public get tentativasContato(): TentativaContatoDoador[] {
		return this._tentativasContato;
	}
	/**
	 * @param  {TentativaContatoDoador[]} value
	 */
	public set tentativasContato(value: TentativaContatoDoador[]) {
		this._tentativasContato = value;
	}
	/**
	 * @returns string
	 */
	public get descricaoStatus(): string {
		return this._descricaoStatus;
	}
	/**
	 * @param  {string} value
	 */
	public set descricaoStatus(value: string) {
		this._descricaoStatus = value;
	}

	public jsonToEntity(res:any): PedidoContato {

		if (res.solicitacao) {
			this.solicitacao = new Solicitacao().jsonToEntity(res.solicitacao);
		}

		if (res.hemoEntidade) {
			this.hemoEntidade = new HemoEntidade().jsonToEntity(res.hemoEntidade);
		}

		if (res.tentativasContato) {
			this.tentativasContato = [];
			res.tentativasContato.forEach(tentativa => {
				this.tentativasContato.push(new TentativaContatoDoador().jsonToEntity(tentativa));
			})
			
		}


		
		this.id = 				ConvertUtil.parseJsonParaAtributos(res.id, new Number());
		this.dataCriacao =		ConvertUtil.parseJsonParaAtributos(res.dataCriacao, new Date());
		this.aberto = 			ConvertUtil.parseJsonParaAtributos(res.aberto, new Boolean());
		
		
		
		this.descricaoStatus = ConvertUtil.parseJsonParaAtributos(res.descricaoStatus, new String());

		return this;		
	}
}