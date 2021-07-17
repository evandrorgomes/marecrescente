import { ConvertUtil } from 'app/shared/util/convert.util';
import { TiposColuna } from './../enums/tipos.coluna';
import { BaseEntidade } from "../base.entidade";


export class ColunaDto {

    private _nome: string;
    private _mostrar: boolean = true;
    private _tipoColuna: TiposColuna;
    private _chave: boolean = false;
    private _casasDecimais: number = 0;
    private _beforeShow: (value: any) => any ;

    constructor(nome: string, tipoColuna: TiposColuna, chave?: boolean, mostrar?: boolean, beforeShow?: (value: any) => any, casasDecimais?: number) {
        this.nome = nome;
        this.tipoColuna = tipoColuna;
        if (chave != null) {
            this.chave = chave;
        }
        if (mostrar != null) {
            this.mostrar = mostrar;
        }
        if (beforeShow != null) {
            this._beforeShow = beforeShow;
        }
        if (casasDecimais != null) {
          this._casasDecimais = casasDecimais;
        }
    }

    /**
     * Getter nome
     * @return {string}
     */
	public get nome(): string {
		return this._nome;
	}

    /**
     * Setter nome
     * @param {string} value
     */
	public set nome(value: string) {
		this._nome = value;
	}

    /**
     * Getter mostrar
     * @return {boolean}
     */
	public get mostrar(): boolean {
		return this._mostrar;
	}

    /**
     * Setter mostrar
     * @param {boolean} value
     */
	public set mostrar(value: boolean) {
		this._mostrar = value;
    }

    /**
     * Getter tipoColuna
     * @return {TiposColuna}
     */
	public get tipoColuna(): TiposColuna {
		return this._tipoColuna;
	}

    /**
     * Setter tipoColuna
     * @param {TiposColuna} value
     */
	public set tipoColuna(value: TiposColuna) {
		this._tipoColuna = value;
    }

    /**
     * Getter chave
     * @return {boolean}
     */
	public get chave(): boolean {
		return this._chave;
	}

    /**
     * Setter chave
     * @param {boolean} value
     */
	public set chave(value: boolean) {
		this._chave = value;
    }

    public set beforeShow(value: (value: any) => any) {
        this._beforeShow = value;
    }

    public get beforeShow(): (value: any) => any {
        return this._beforeShow;
    }

  get casasDecimais(): number {
    return this._casasDecimais;
  }

  set casasDecimais(value: number) {
    this._casasDecimais = value;
  }
}
