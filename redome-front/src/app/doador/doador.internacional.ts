import { constructor } from 'events';
import { forEach } from '@angular/router/src/utils/collection';
import { ExameDoadorInternacional } from './exame.doador.internacional';
import { Doador } from './doador';
import { Registro } from '../shared/dominio/registro';
import { ConvertUtil } from '../shared/util/convert.util';
import { TiposDoador } from 'app/shared/enums/tipos.doador';

export class DoadorInternacional extends Doador {

	private _idRegistro: string;
	private _idade: number;
    private _registroPagamento: Registro;
    private _exames: ExameDoadorInternacional[];
    private _cadastradoEmdis: Boolean;
    private _rmrAssociado:string;

    constructor() {
        super();
        this.tipoDoador = TiposDoador.INTERNACIONAL;
    }

    /**
     * Getter idRegistro
     * @return {string}
     */
	public get idRegistro(): string {
		return this._idRegistro;
	}

    /**
     * Setter idRegistro
     * @param {string} value
     */
	public set idRegistro(value: string) {
		this._idRegistro = value;
	}

    /**
     * Getter idade
     * @return {number}
     */
	public get idade(): number {
		return this._idade;
	}

    /**
     * Setter idade
     * @param {number} value
     */
	public set idade(value: number) {
		this._idade = value;
	}

    /**
     * Getter registroPagamento
     * @return {Registro}
     */
	public get registroPagamento(): Registro {
		return this._registroPagamento;
	}

    /**
     * Setter registroPagamento
     * @param {Registro} value
     */
	public set registroPagamento(value: Registro) {
		this._registroPagamento = value;
	}

    /**
     * Getter exames
     * @return {ExameDoadorInternacional[]}
     */
	public get exames(): ExameDoadorInternacional[] {
		return this._exames;
	}

    /**
     * Setter exames
     * @param {ExameDoadorInternacional[]} value
     */
	public set exames(value: ExameDoadorInternacional[]) {
		this._exames = value;
    }

    /**
     * Indica se o cadastro foi realizado no EMDIS.
     * @return {Boolean}
     */
	public get cadastradoEmdis(): Boolean {
		return this._cadastradoEmdis;
	}

	public set cadastradoEmdis(value: Boolean) {
		this._cadastradoEmdis = value;
    }

    /**
     * Getter rmrAssociado
     * @return {string}
     */
	public get rmrAssociado(): string {
		return this._rmrAssociado;
	}

    /**
     * Setter rmrAssociado
     * @param {string} value
     */
	public set rmrAssociado(value: string) {
		this._rmrAssociado = value;
	}

   get identificacao(): string {
      return this._idRegistro;
   }


   public jsonToEntity(res: any): this {

        if (res.registroPagamento) {
            this.registroPagamento = new Registro().jsonToEntity(res.registroPagamento);
        }

        if (this.exames) {
            this.exames = [];
            res.exames.forEach(exame => {
                this.exames.push(new ExameDoadorInternacional().jsonToEntity(exame));
            });
        }

        this.idRegistro = ConvertUtil.parseJsonParaAtributos(res.idRegistro, new String());
        this.idade = ConvertUtil.parseJsonParaAtributos(res.idade, new Number());
        this.cadastradoEmdis = ConvertUtil.parseJsonParaAtributos(res.cadastradoEmdis, new Boolean());
        super.jsonToEntity(res);

        return this;
    }

}
