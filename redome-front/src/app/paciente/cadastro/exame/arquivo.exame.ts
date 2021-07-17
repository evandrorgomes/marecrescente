import { ExamePaciente } from "./exame.paciente";
import { BaseEntidade } from "../../../shared/base.entidade";
import { Exame } from "./exame.";

/**
 * Classe com informações sobre os arquivos subidos no exame (laudos).
 * @author Rafael Pizão
 * @export
 * @class ArquivoExame
 * @extends {BaseEntidade}
 */
export class ArquivoExame extends BaseEntidade {

  /**
   * ID do Arquivo Exame
   * 
   * @private
   * @type {Number}@memberof ArquivoExame
   */
  private _id: Number;

  /**
   * Caminho do arquivo carregado no sistema
   * 
   * @private
   * @type {string}@memberof ArquivoExame
   */
  private _caminhoArquivo: String;

  /**
   * Exame ao qual estão vinculados os laudos (arquivos carregados) 
   * @private
   * @type {Exame}@memberof ArquivoExame
   */
  private _exame: Exame;

  private _nomeSemTimestamp:string;


  public get id(): Number {
    return this._id;
  }
	public set id(id: Number) {
    this._id = id;
  }

  public get caminhoArquivo(): String {
    return this._caminhoArquivo;
  }
	public set caminhoArquivo(value: String) {
    this._caminhoArquivo = value;
  }

  public get exame(): Exame {
    return this._exame;
  }
	public set exame(value: Exame) {
    this._exame = value;
  }

	/**
   * Recupera o Nome do arquivo a ser apresentado ao usuario
   * 
   * @type {string}
   * @memberof ArquivoExame
   */
  public get nomeSemTimestamp(): string {
		return this._nomeSemTimestamp;
	}

	/**
   * Seta o Nome do arquivo a ser apresentado ao usuario
   * 
   * @memberof ArquivoExame
   */
  public set nomeSemTimestamp(value: string) {
		this._nomeSemTimestamp = value;
  }
  
  public jsonToEntity(res: any) {
    throw new Error("Method not implemented.");
}

}