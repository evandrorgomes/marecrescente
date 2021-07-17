import { ConvertUtil } from 'app/shared/util/convert.util';
import { BaseEntidade } from '../base.entidade';
import { Pagina } from './pagina';
import { TipoFormulario } from './tipo.formulario';

export class Formulario extends BaseEntidade {

	private _id: number;
	private _tipoFormulario: TipoFormulario;
	private _paginas: Pagina[];
	private _comValidacao: boolean;
	private _comErro: boolean;

	public get id(): number {
		return this._id;
	}

	public set id(value: number) {
		this._id = value;
	}

	public get tipoFormulario(): TipoFormulario {
		return this._tipoFormulario;
	}

	public set tipoFormulario(value: TipoFormulario) {
		this._tipoFormulario = value;
	}

    /**
     * Getter comValidacao
     * @return {boolean}
     */
	public get comValidacao(): boolean {
		return this._comValidacao;
	}

    /**
     * Setter comValidacao
     * @param {boolean} value
     */
	public set comValidacao(value: boolean) {
		this._comValidacao = value;
	}


    /**
     * Getter paginas
     * @return {Pagina[]}
     */
	public get paginas(): Pagina[] {
		return this._paginas;
	}

    /**
     * Setter paginas
     * @param {Pagina[]} value
     */
	public set paginas(value: Pagina[]) {
		this._paginas = value;
	}

    /**
     * Getter comErro
     * @return {boolean}
     */
	public get comErro(): boolean {
		return this._comErro;
	}

    /**
     * Setter comErro
     * @param {boolean} value
     */
	public set comErro(value: boolean) {
		this._comErro = value;
	}


	public jsonToEntity(res:any): Formulario {

		if (res.tipoFormulario) {
			this.tipoFormulario = new TipoFormulario().jsonToEntity(res.tipoFormulario);
		}

		if (res.paginas) {
			this.paginas = [];
			res.paginas.forEach(pagina => {
				this.paginas.push(new Pagina().jsonToEntity(pagina));
			});
		}

		this.id = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
		this.comValidacao = ConvertUtil.parseJsonParaAtributos(res.comValidacao, new Boolean());
		this.comErro = ConvertUtil.parseJsonParaAtributos(res.comErro, new Boolean());
		
		return this;
	}
    
}