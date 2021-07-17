import { BaseEntidade } from '../../../shared/base.entidade';
import { TipoAmostraPrescricao } from 'app/shared/classes/tipo.amostra.prescricao';

/**
 * Dto para criar uma solicitação para o doador.
 *
 * @author Bruno Sousa
 * @export
 * @class SolicitacaoDTO
 * @extends {BaseEntidade}
 */
export class SolicitacaoDTO extends BaseEntidade {

    private _rmr: number;
	private _idDoador: number;
	private _tipo: number;
	private _dataColeta1: Date;
	private _dataColeta2: Date;
	private _dataLimiteWorkup1: Date;
	private _dataLimiteWorkup2: Date;
	private _fonteCelulaOpcao1: number;
	private _quantidadeTotalOpcao1: number;
	private _quantidadePorKgOpcao1: number;
	private _fonteCelulaOpcao2: number;
	private _quantidadeTotalOpcao2: number;
	private _quantidadePorKgOpcao2: number;
	private _tiposAmostraPrescricao:TipoAmostraPrescricao[] = [];

    constructor(rmr?: number, idDoador?: number, tipo?:number) {
        super();
        this._rmr = rmr;
        this._idDoador = idDoador;
        this._tipo = tipo;
    }


	public get rmr(): number {
		return this._rmr;
	}

	public set rmr(value: number) {
		this._rmr = value;
	}

	public get idDoador(): number {
		return this._idDoador;
	}

	public set idDoador(value: number) {
		this._idDoador = value;
	}

	public get tipo(): number {
		return this._tipo;
	}

	public set tipo(value: number) {
		this._tipo = value;
	}

	public get dataColeta1(): Date {
		return this._dataColeta1;
	}

	public set dataColeta1(value: Date) {
		this._dataColeta1 = value;
	}

	public get dataColeta2(): Date {
		return this._dataColeta2;
	}

	public set dataColeta2(value: Date) {
		this._dataColeta2 = value;
	}

	public get dataLimiteWorkup1(): Date {
		return this._dataLimiteWorkup1;
	}

	public set dataLimiteWorkup1(value: Date) {
		this._dataLimiteWorkup1 = value;
	}

	public get dataLimiteWorkup2(): Date {
		return this._dataLimiteWorkup2;
	}

	public set dataLimiteWorkup2(value: Date) {
		this._dataLimiteWorkup2 = value;
	}

	public get fonteCelulaOpcao1(): number {
		return this._fonteCelulaOpcao1;
	}

	public set fonteCelulaOpcao1(value: number) {
		this._fonteCelulaOpcao1 = value;
	}

	public get quantidadeTotalOpcao1(): number {
		return this._quantidadeTotalOpcao1;
	}

	public set quantidadeTotalOpcao1(value: number) {
		this._quantidadeTotalOpcao1 = value;
	}

	public get quantidadePorKgOpcao1(): number {
		return this._quantidadePorKgOpcao1;
	}

	public set quantidadePorKgOpcao1(value: number) {
		this._quantidadePorKgOpcao1 = value;
	}

	public get fonteCelulaOpcao2(): number {
		return this._fonteCelulaOpcao2;
	}

	public set fonteCelulaOpcao2(value: number) {
		this._fonteCelulaOpcao2 = value;
	}

	public get quantidadeTotalOpcao2(): number {
		return this._quantidadeTotalOpcao2;
	}

	public set quantidadeTotalOpcao2(value: number) {
		this._quantidadeTotalOpcao2 = value;
	}

	public get quantidadePorKgOpcao2(): number {
		return this._quantidadePorKgOpcao2;
	}

	public set quantidadePorKgOpcao2(value: number) {
		this._quantidadePorKgOpcao2 = value;
	}

	public get tiposAmostraPrescricao(): TipoAmostraPrescricao[]  {
		return this._tiposAmostraPrescricao;
	}

	public set tiposAmostraPrescricao(value: TipoAmostraPrescricao[] ) {
		this._tiposAmostraPrescricao = value;
	}
	public jsonToEntity(res:any):this{

		return this;
	}

}
