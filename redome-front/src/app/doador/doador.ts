import { forEach } from '@angular/router/src/utils/collection';
import { MotivoStatusDoador } from './inativacao/motivo.status.doador';
import { RessalvaDoador } from "./ressalva.doador";
import { Solicitacao } from './solicitacao/solicitacao';
import { Paciente } from '../paciente/paciente';
import { BaseEntidade } from '../shared/base.entidade';
import { Registro } from '../shared/dominio/registro';
import { UsuarioLogado } from '../shared/dominio/usuario.logado';
import { StatusDoador } from '../shared/dominio/status.doador';
import { ConvertUtil } from '../shared/util/convert.util';
import { TiposDoador } from '../shared/enums/tipos.doador';

export abstract class Doador extends BaseEntidade {

	private _id: number;
	private _dataCadastro: Date;
	private _sexo: string;
	private _abo: string;
	private _dataNascimento: Date;
	private _dataAtualizacao: Date;
	private _statusDoador: StatusDoador;
	private _dataRetornoInatividade: Date;
	private _motivoStatusDoador: MotivoStatusDoador;
	private _peso: number;
	private _ressalvas: RessalvaDoador[];
	private _registroOrigem: Registro;
	private _usuario: UsuarioLogado;
	private _ativo: Boolean;
	private _grid:string;
	//private _paciente:Paciente;
	private _idTipoDoador:number;
	private _tipoDoador: TiposDoador;

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
     * Indica se o doador está ativo ou não para o match.
     * @return TRUE se doador ativo, FALSE caso contrário.
     */
	public get ativo(): Boolean {
		return this._ativo;
	}

	public set ativo(value: Boolean) {
		this._ativo = value;
	}

	public get dataNascimento(): Date {
		return this._dataNascimento;
	}

	public set dataNascimento(value: Date) {
		this._dataNascimento = value;
	}

	public get dataCadastro(): Date {
		return this._dataCadastro;
	}

	public set dataCadastro(value: Date) {
		this._dataCadastro = value;
	}


	public get sexo(): string {
		return this._sexo;
	}

	public set sexo(value: string) {
		this._sexo = value;
	}

	public get abo(): string {
		return this._abo;
	}

	public set abo(value: string) {
		this._abo = value;
	}

	public get dataAtualizacao(): Date {
		return this._dataAtualizacao;
	}

	public set dataAtualizacao(value: Date) {
		this._dataAtualizacao = value;
	}

	public get usuario(): UsuarioLogado {
		return this._usuario;
	}

	public set usuario(value: UsuarioLogado) {
		this._usuario = value;
	}

	public get statusDoador(): StatusDoador {
		return this._statusDoador;
	}

	public set statusDoador(value: StatusDoador) {
		this._statusDoador = value;
	}

	public get dataRetornoInatividade(): Date {
		return this._dataRetornoInatividade;
	}

	public set dataRetornoInatividade(value: Date) {
		this._dataRetornoInatividade = value;
	}

	public get motivoStatusDoador(): MotivoStatusDoador {
		return this._motivoStatusDoador;
	}

	public set motivoStatusDoador(value: MotivoStatusDoador) {
		this._motivoStatusDoador = value;
	}

	public get peso(): number {
		return this._peso;
	}

	public set peso(value: number) {
		this._peso = value;
	}

	public get ressalvas(): RessalvaDoador[] {
		return this._ressalvas;
	}

	public set ressalvas(value: RessalvaDoador[]) {
		this._ressalvas = value;
	}
	/**
	 * @returns Solicitacao
	 */
	/* public get solicitacao(): Solicitacao {
		return this._solicitacao;
	} */
	/**
	 * @param  {Solicitacao} value
	 */
	/* public set solicitacao(value: Solicitacao) {
		this._solicitacao = value;
	} */

    /**
     * Getter registroOrigem
     * @return {Registro}
     */
	public get registroOrigem(): Registro {
		return this._registroOrigem;
	}

    /**
     * Setter registroOrigem
     * @param {Registro} value
     */
	public set registroOrigem(value: Registro) {
		this._registroOrigem = value;
	}

    /**
     * Getter grid
     * @return {string}
     */
	public get grid(): string {
		return this._grid;
	}

    /**
     * Setter grid
     * @param {string} value
     */
	public set grid(value: string) {
		this._grid = value;
	}

    /**
     * Getter paciente
     * @return {Paciente}
     */
/* 	public get paciente(): Paciente {
		return this._paciente;
	} */

    /**
     * Setter paciente
     * @param {Paciente} value
     */
	/* public set paciente(value: Paciente) {
		this._paciente = value;
	} */

    /**
     * Getter idTipoDoador
     * @return {number}
     */
	public get idTipoDoador(): number {
		return this._idTipoDoador;
	}

    /**
     * Setter idTipoDoador
     * @param {number} value
     */
	public set idTipoDoador(value: number) {
		this._idTipoDoador = value;
	}

    /**
     * Getter TipoDoador
     * @return {TiposDoador}
     */
	public get tipoDoador(): TiposDoador {
		return this._tipoDoador;
	}

    /**
     * Setter TipoDoador
     * @param {TiposDoador} value
     */
	public set tipoDoador(value: TiposDoador) {
		this._tipoDoador = value;
	}

	public abstract get identificacao(): string;


	public jsonToEntity(res: any): this {

		if (res.tipoDoador) {
			this.tipoDoador = TiposDoador.valueOf(res.tipoDoador);
		}

		if (res.statusDoador) {
			this.statusDoador = new StatusDoador().jsonToEntity(res.statusDoador);
		}

		if (res.motivoStatusDoador) {
			this.motivoStatusDoador = new MotivoStatusDoador().jsonToEntity(res.motivoStatusDoador);
		}

		if (res.ressalvas) {
			this.ressalvas = [];
			res.ressalvas.forEach(ressalva => {
				this.ressalvas.push(new RessalvaDoador().jsonToEntity(ressalva));
			});
		}

		if (res.registroOrigem) {
			this.registroOrigem = new Registro().jsonToEntity(res.registroOrigem);
		}

		if (res.usuario) {
			this.usuario = new UsuarioLogado().jsonToEntity(res.usuario);
		}

		this.id = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
		this.dataCadastro = ConvertUtil.parseJsonParaAtributos(res.dataCadastro, new Date());
		this.sexo = ConvertUtil.parseJsonParaAtributos(res.sexo, new String());
		this.abo = ConvertUtil.parseJsonParaAtributos(res.abo, new String());
		this.dataNascimento = ConvertUtil.parseJsonParaAtributos(res.dataNascimento, new Date());
		this.dataAtualizacao = ConvertUtil.parseJsonParaAtributos(res.dataAtualizacao, new Date());
		this.dataRetornoInatividade = ConvertUtil.parseJsonParaAtributos(res.dataRetornoInatividade, new Date());
		this.peso = ConvertUtil.parseJsonParaAtributos(res.peso, new Number());
		this.ativo = ConvertUtil.parseJsonParaAtributos(res.ativo, new Boolean());
		this.grid = ConvertUtil.parseJsonParaAtributos(res.grid, new String());
		this.idTipoDoador = ConvertUtil.parseJsonParaAtributos(res.idTipoDoador, new Number());

		return this;
	}

}
