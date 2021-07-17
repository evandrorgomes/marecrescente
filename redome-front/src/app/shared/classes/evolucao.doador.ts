import { BaseEntidade } from '../base.entidade';
import { Doador } from 'app/doador/doador';
import { Usuario } from 'app/shared/dominio/usuario';
import { ConvertUtil } from 'app/shared/util/convert.util';
import { DateMoment } from '../util/date/date.moment';
import { DateTypeFormats } from '../util/date/date.type.formats';
import { DataUtil } from 'app/shared/util/data.util';

export class EvolucaoDoador extends BaseEntidade {

	private _id: number;
	private _dataCriacao: Date;
	private _doador: Doador;
	private _usuarioResponsavel: Usuario;
    private _descricao: string;

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
     * Getter doador
     * @return {Doador}
     */
	public get doador(): Doador {
		return this._doador;
	}

    /**
     * Setter doador
     * @param {Doador} value
     */
	public set doador(value: Doador) {
		this._doador = value;
	}

    /**
     * Getter usuarioResponsavel
     * @return {Usuario}
     */
	public get usuarioResponsavel(): Usuario {
		return this._usuarioResponsavel;
	}

    /**
     * Setter usuarioResponsavel
     * @param {Usuario} value
     */
	public set usuarioResponsavel(value: Usuario) {
		this._usuarioResponsavel = value;
	}

    /**
     * Getter nome
     * @return {string}
     */
	public get descricao(): string {
		return this._descricao;
	}

    /**
     * Setter nome
     * @param {string} value
     */
	public set descricao(value: string) {
		this._descricao = value;
	}

    public jsonToEntity(res: any): EvolucaoDoador {

        if (res.doador) {
            this.doador = ConvertUtil.parseJsonDoadorParaDoador(res.doador);
        }

        if (res.usuarioResponsavel) {
            this.usuarioResponsavel = new Usuario().jsonToEntity(res.usuarioResponsavel);
        }

        this.id = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
        this.dataCriacao = ConvertUtil.parseJsonParaAtributos(res.dataCriacao, new Date());
        this.descricao = ConvertUtil.parseJsonParaAtributos(res.descricao, new String());

        return this;
    }

    public dataCriacaoEUsuarioFormatado(): string {
        return DataUtil.toDateFormat(this.dataCriacao, DateTypeFormats.DATE_TIME) + ' - '
            + (this.usuarioResponsavel ? this.usuarioResponsavel.nome : '');
    }




}
