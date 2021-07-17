import { BaseEntidade } from "../base.entidade";
import { UsuarioLogado } from "../dominio/usuario.logado";


/**
 * Classe que representa um comentário do dialogo
 * @author Fillipe Queiroz
 */
export class Comentario extends BaseEntidade {

	private _texto: string;
	private _username: string;
	private _nomeUsuario:string;
	private _dataFormatadaDialogo: string;
	private _rota:string;
	private _estiloIcone:string;

	public get texto(): string {
		return this._texto;
	}

	public set texto(value: string) {
		this._texto = value;
	}

	public get dataFormatadaDialogo(): string {
		return this._dataFormatadaDialogo;
	}

	public set dataFormatadaDialogo(value: string) {
		this._dataFormatadaDialogo = value;
	}

	public get username(): string {
		return this._username;
	}

	public set username(value: string) {
		this._username = value;
	}
	public get nomeUsuario(): string {
		return this._nomeUsuario;
	}
	public set nomeUsuario(value: string) {
		this._nomeUsuario = value;
	}

	public get rota(): string {
		return this._rota;
	}

	public set rota(value: string) {
		this._rota = value;
	}

	public get estiloIcone(): string {
		return this._estiloIcone;
	}

	public set estiloIcone(value: string) {
		this._estiloIcone = value;
	}

	public jsonToEntity(res: any) {
        throw new Error("Method not implemented.");
    }
}

