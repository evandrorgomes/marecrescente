import { BaseEntidade } from "../shared/base.entidade";
import { ConvertUtil } from "app/shared/util/convert.util";

/**
 * Classe que representa um responsável
 * @author Fillipe Queiroz - Diogo Paraíso
 */
export class Responsavel extends BaseEntidade {

	private _id: number;
	private _cpf: string;
	private _nome: string;
	private _parentesco: string;

	constructor(id: number, cpf?: string, nome?: string, parentesco?: string);
	constructor(cpf: string, nome?: string, parentesco?: string);
	constructor(idOuCpf: number|string, cpfOuNome?: string,  nomeOuParentesco?: string, parentesco?: string) {
		super();
		if (typeof idOuCpf === "number") {
			this._id = idOuCpf;
			this._cpf = cpfOuNome;
			this._nome = nomeOuParentesco;
			this._parentesco = parentesco;
		}
		else {
			this._cpf = idOuCpf;
			this._nome = cpfOuNome;
			this._parentesco = nomeOuParentesco;
		}
		
		

	}

	public get cpf(): string {
		return this._cpf;
	}

	public set cpf(value: string) {
		this._cpf = value;
	}

	public get nome(): string {
		return this._nome;
	}

	public set nome(value: string) {
		this._nome = value;
	}

	public get parentesco(): string {
		return this._parentesco;
	}

	public set parentesco(value: string) {
		this._parentesco = value;
	}

	public get id(): number {
		return this._id;
	}

	public set id(value: number) {
		this._id = value;
	}

	public jsonToEntity(res:any):this{
		this.nome =  ConvertUtil.parseJsonParaAtributos(res.nome, new String());
		this.id =  ConvertUtil.parseJsonParaAtributos(res.id, new Number());
		this.parentesco =  ConvertUtil.parseJsonParaAtributos(res.parentesco, new String());
		this.cpf =  ConvertUtil.parseJsonParaAtributos(res.cpf, new String());
		return this;
	}

}