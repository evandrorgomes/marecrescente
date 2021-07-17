import { Evolucao } from "./evolucao";
import { BaseEntidade } from "../../../shared/base.entidade";


/**
 * Classe de referencia aos arquivos que pertencem a Evolução.
 * @author Filipe Paes
 */
export class ArquivoEvolucao extends BaseEntidade {

    /**
     * ID do Arquivo Exame
     *
     * @private
     * @type {Number}@memberof ArquivoEvolucao
     */
    private _id: Number;

    /**
     * Caminho do arquivo carregado no sistema
     *
     * @private
     * @type {string}@memberof ArquivoEvolucao
     */
    private _caminhoArquivo: String;

    /**
     * Exame ao qual estão vinculados os laudos (arquivos carregados)
     * @private
     * @type {Exame}@memberof ArquivoEvolucao
     */
    private _evolucao: Evolucao;

    private _nomeSemTimestamp:string;


    /**
     * Getter id
     * @return {Number}
     */
	public get id(): Number {
		return this._id;
	}

    /**
     * Setter id
     * @param {Number} value
     */
	public set id(value: Number) {
		this._id = value;
	}



    /**
     * Getter evolucao
     * @return {Evolucao}
     */
	public get evolucao(): Evolucao {
		return this._evolucao;
	}

    /**
     * Setter evolucao
     * @param {Evolucao} value
     */
	public set evolucao(value: Evolucao) {
		this._evolucao = value;
	}



    /**
     * Getter caminhoArquivo
     * @return {String}
     */
	public get caminhoArquivo(): String {
		return this._caminhoArquivo;
	}

    /**
     * Setter caminhoArquivo
     * @param {String} value
     */
	public set caminhoArquivo(value: String) {
		this._caminhoArquivo = value;
	}



    /**
     * Getter nomeSemTimestamp
     * @return {string}
     */
	public get nomeSemTimestamp(): string {
		return this._nomeSemTimestamp;
	}

    /**
     * Setter nomeSemTimestamp
     * @param {string} value
     */
	public set nomeSemTimestamp(value: string) {
		this._nomeSemTimestamp = value;
	}


    public jsonToEntity(res: any): ArquivoEvolucao {

        this._id = res.id;
        this._caminhoArquivo = res.caminhoArquivo;
        return this;
    }


}
