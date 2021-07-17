import { BaseEntidade } from "../shared/base.entidade";
import { Paciente } from './paciente';
import { ConvertUtil } from "app/shared/util/convert.util";
import { UsuarioLogado } from "../shared/dominio/usuario.logado";
import { CategoriaNotificacao } from "app/shared/classes/categoria.notificacao";


/**
 * Classe que representa uma notificação, associada ao paciente.
 * @author Pizão
 */
export class Notificacao extends BaseEntidade {

	private _id: number;
	private _descricao: string;
	private _categoria: CategoriaNotificacao;
	private _rmr: number;
	private _lido: boolean;
	private _dataCriacao: Date;
	private _dataLeitura: Date;	
	private _parceiro: number;
	private _usuario: UsuarioLogado;
	private _aging: string;


	public get id(): number {
		return this._id;
	}

	public set id(value: number) {
		this._id = value;
	}

    /**
     * Getter notificado
     * @return {boolean}
     */
	public get lido(): boolean {
		return this._lido;
	}

    /**
     * Setter notificado
     * @param {boolean} value
     */
	public set lido(value: boolean) {
		this._lido = value;
	}

	public get descricao(): string {
		return this._descricao;
	}

	public set descricao(value: string) {
		this._descricao = value;
	}

	public get rmr(): number {
		return this._rmr;
	}

	public set rmr(value: number) {
		this._rmr = value;
	}

    /**
     * Getter categoria
     * @return {CategoriaNotificacao}
     */
	public get categoria(): CategoriaNotificacao {
		return this._categoria;
	}

    /**
     * Setter categoria
     * @param {CategoriaNotificacao} value
     */
	public set categoria(value: CategoriaNotificacao) {
		this._categoria = value;
	}

    /**
     * Getter dataCriacao
     * @return {Date}
     */
	public get dataCriacao(): Date {
		return this._dataCriacao;
	}

    /**
     * Setter dataCriacao
     * @param {Date} value
     */
	public set dataCriacao(value: Date) {
		this._dataCriacao = value;
	}

    /**
     * Getter dataLeitura
     * @return {Date}
     */
	public get dataLeitura(): Date {
		return this._dataLeitura;
	}

    /**
     * Setter dataLeitura
     * @param {Date} value
     */
	public set dataLeitura(value: Date) {
		this._dataLeitura = value;
	}

    /**
     * Getter parceiro
     * @return {number}
     */
	public get parceiro(): number {
		return this._parceiro;
	}

    /**
     * Setter parceiro
     * @param {number} value
     */
	public set parceiro(value: number) {
		this._parceiro = value;
	}

    /**
     * Getter usuario
     * @return {UsuarioLogado}
     */
	public get usuario(): UsuarioLogado {
		return this._usuario;
	}

    /**
     * Setter usuario
     * @param {UsuarioLogado} value
     */
	public set usuario(value: UsuarioLogado) {
		this._usuario = value;
	}

    /**
     * Getter aging
     * @return {string}
     */
	public get aging(): string {
		return this._aging;
	}

    /**
     * Setter aging
     * @param {string} value
     */
	public set aging(value: string) {
		this._aging = value;
	}



	public jsonToEntity(res: any): Notificacao {

		if (res.categoria) {
			this.categoria = new CategoriaNotificacao().jsonToEntity(res.categoria);
		}

		if (res.usuario) {
			this.usuario = new UsuarioLogado().jsonToEntity(res.usuario);
		}

		this.id = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
		this.descricao = ConvertUtil.parseJsonParaAtributos(res.descricao, new String());
		this.rmr = ConvertUtil.parseJsonParaAtributos(res.rmr, new Number());
		this.lido = ConvertUtil.parseJsonParaAtributos(res.lido, new Boolean());
		this.dataCriacao = ConvertUtil.parseJsonParaAtributos(res.dataCriacao, new Date());
		this.dataLeitura = ConvertUtil.parseJsonParaAtributos(res.dataLeitura, new Date());
		this.parceiro = ConvertUtil.parseJsonParaAtributos(res.parceiro, new Number());
		this.aging = ConvertUtil.parseJsonParaAtributos(res.aging, new String());

		return this;
    }
}

