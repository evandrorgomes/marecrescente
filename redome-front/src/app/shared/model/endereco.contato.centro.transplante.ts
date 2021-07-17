import { ConvertUtil } from 'app/shared/util/convert.util';
import { CentroTransplante } from '../dominio/centro.transplante';
import { EnderecoContato } from '../classes/endereco.contato';

/**
 * Classe Bean utilizada para definir os campos de endereço do centro de transplante.
 *
 * @export
 * @class ContatoEndereco
 */
export class EnderecoContatoCentroTransplante extends EnderecoContato {

    private _centroTranplante: CentroTransplante;
    private _retirada: boolean;
    private _entrega: boolean;
    private _workup: boolean;
    private _gcsf: boolean;
    private _internacao: boolean;

    /**
     * Getter centroTranplante
     * @return {CentroTransplante}
     */
	public get centroTranplante(): CentroTransplante {
		return this._centroTranplante;
	}

    /**
     * Setter centroTranplante
     * @param {CentroTransplante} value
     */
	public set centroTranplante(value: CentroTransplante) {
		this._centroTranplante = value;
	}

    /**
     * Getter retirada
     * @return {boolean}
     */
	public get retirada(): boolean {
		return this._retirada;
	}

    /**
     * Setter retirada
     * @param {boolean} value
     */
	public set retirada(value: boolean) {
		this._retirada = value;
	}

    /**
     * Getter entrega
     * @return {boolean}
     */
	public get entrega(): boolean {
		return this._entrega;
	}

    /**
     * Setter entrega
     * @param {boolean} value
     */
	public set entrega(value: boolean) {
		this._entrega = value;
	}


    /**
     * Getter workup
     * @return {boolean}
     */
	public get workup(): boolean {
		return this._workup;
	}

    /**
     * Setter workup
     * @param {boolean} value
     */
	public set workup(value: boolean) {
		this._workup = value;
	}

    /**
     * Getter gcsf
     * @return {boolean}
     */
	public get gcsf(): boolean {
		return this._gcsf;
	}

    /**
     * Setter gcsf
     * @param {boolean} value
     */
	public set gcsf(value: boolean) {
		this._gcsf = value;
	}

  /**
     * Getter internacao
     * @return {boolean}
     */
	public get internacao(): boolean {
		return this._internacao;
	}

    /**
     * Setter internacao
     * @param {boolean} value
     */
	public set internacao(value: boolean) {
		this._internacao = value;
	}


    public jsonToEntity(res: any): EnderecoContatoCentroTransplante {
		  if (!res) {
  		  	 return this;
		  }
        if (res.centroTransplante) {
            this.centroTranplante = new CentroTransplante().jsonToEntity(res.centroTransplante);
        }
        this.retirada = ConvertUtil.parseJsonParaAtributos(res.retirada, new Boolean());
        this.entrega = ConvertUtil.parseJsonParaAtributos(res.entrega, new Boolean());
        this.workup = ConvertUtil.parseJsonParaAtributos(res.workup, new Boolean());
        this.gcsf = ConvertUtil.parseJsonParaAtributos(res.gcsf, new Boolean());
        this.internacao = ConvertUtil.parseJsonParaAtributos(res.internacao, new Boolean());

        super.jsonToEntity(res);
        return this;
    }
}
