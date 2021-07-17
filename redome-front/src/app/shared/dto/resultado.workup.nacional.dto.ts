import { BaseEntidade } from "../base.entidade";
import { ConvertUtil } from "../util/convert.util";

/**
 * Classe para armazenamento de resultados de workup.
 * @author ergomes
 */
export class ResultadoWorkupNacionalDTO extends BaseEntidade {

    private _id: number;
    private _coletaInviavel: boolean;
    private _motivoInviabilidade: string;
    private _idPedidoWorkup: number;
    private _idFonteCelula: number;
    private _dataColeta: Date;
    private _dataGCSF: Date;
    private _adeguadoAferese: boolean;
    private _acessoVenosoCentral: string;
    private _sangueAntologoColetado: boolean;
    private _motivoSangueAntologoNaoColetado: string;

    public get coletaInviavel(): boolean {
        return this._coletaInviavel;
    }

    public set coletaInviavel(value: boolean) {
        this._coletaInviavel = value;
    }


    /**
     * Getter motivoInviabilidade
     * @return {string}
     */
	public get motivoInviabilidade(): string {
		return this._motivoInviabilidade;
	}

    /**
     * Setter motivoInviabilidade
     * @param {string} value
     */
	public set motivoInviabilidade(value: string) {
		this._motivoInviabilidade = value;
	}
    /**
     * Getter id
     * @return {number}
     */
	public get id(): number {
		return this._id;
	}

    /**
     * Getter idPedidoWorkup
     * @return {number}
     */
	public get idPedidoWorkup(): number {
		return this._idPedidoWorkup;
	}

    /**
     * Setter id
     * @param {number} value
     */
	public set id(value: number) {
		this._id = value;
	}

    /**
     * Setter idPedidoWorkup
     * @param {number} value
     */
	public set idPedidoWorkup(value: number) {
		this._idPedidoWorkup = value;
	}


   get idFonteCelula(): number {
      return this._idFonteCelula;
   }

   set idFonteCelula(value: number) {
      this._idFonteCelula = value;
   }

   get dataColeta(): Date {
      return this._dataColeta;
   }

   set dataColeta(value: Date) {
      this._dataColeta = value;
   }

   get dataGCSF(): Date {
      return this._dataGCSF;
   }

   set dataGCSF(value: Date) {
      this._dataGCSF = value;
   }

   get adeguadoAferese(): boolean {
      return this._adeguadoAferese;
   }

   set adeguadoAferese(value: boolean) {
      this._adeguadoAferese = value;
   }

   get acessoVenosoCentral(): string {
      return this._acessoVenosoCentral;
   }

   set acessoVenosoCentral(value: string) {
      this._acessoVenosoCentral = value;
   }

   get sangueAntologoColetado(): boolean {
      return this._sangueAntologoColetado;
   }

   set sangueAntologoColetado(value: boolean) {
      this._sangueAntologoColetado = value;
   }

   get motivoSangueAntologoNaoColetado(): string {
      return this._motivoSangueAntologoNaoColetado;
   }

   set motivoSangueAntologoNaoColetado(value: string) {
      this._motivoSangueAntologoNaoColetado = value;
   }

   public jsonToEntity(res:any) :ResultadoWorkupNacionalDTO {

      this._id = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
      this._coletaInviavel = ConvertUtil.parseJsonParaAtributos(res.coletaInviavel, new Boolean());
      this._motivoInviabilidade = ConvertUtil.parseJsonParaAtributos(res.motivoInviabilidade, new String());
      this._idPedidoWorkup = ConvertUtil.parseJsonParaAtributos(res.idPedidoWorkup, new Number());

      return this;
	}

}
