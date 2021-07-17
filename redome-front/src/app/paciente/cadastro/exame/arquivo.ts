import { FileItem } from 'ng2-file-upload';

/**
 * Classe com o arquivo FileItem e o nome do arquivo enviado no storage
 * @author Fillipe QUeiroz
 * @export
 * @class Arquivo
 */
export class Arquivo {

  private _id:Number;
  private _fileItem: FileItem;
  private _nome: String;
  private _descricao: String;
  private _nomeSemTimestamp: String;

  /**
   * Recupera valores de id.
   *
   * @type {Number}
   * @memberof Arquivo
   */
  public get id(): Number {
    return this._id;
  }

  /**
   * Seta valores em id.
   *
   * @param {Number}
   * @memberof Arquivo
   */
  public set id(value: Number) {
    this._id = value;
  }
	/**
   *
   *
   * @type {FileItem}
   * @memberof Arquivo
   */
  public get fileItem(): FileItem {
    return this._fileItem;
  }

	/**
   *
   *
   * @memberof Arquivo
   */
  public set fileItem(value: FileItem) {
    this._fileItem = value;
  }

	/**
   *
   *
   * @type {String}
   * @memberof Arquivo
   */
  public get nome(): String {
    return this._nome;
  }

	/**
   *
   *
   * @memberof Arquivo
   */
  public set nome(value: String) {
    this._nome = value;
  }

  /**
   * Recupera nome do arquivo sem o timestamp concatenado
   *
   * @type {String}
   * @memberof Arquivo
   */
  public get nomeSemTimestamp(): String {
    return this._nomeSemTimestamp;
  }

	/**
   * Seta nome do arquivo sem o timestamp concatenado
   *
   * @memberof Arquivo
   */
  public set nomeSemTimestamp(value: String) {
    this._nomeSemTimestamp = value;
  }
  /**
   * Recupera nome do arquivo
   *
   * @type {String}
   * @memberof Arquivo
   */
  public get descricao(): String {
    return this._descricao;
  }
  /**
 * Seta nome do arquivo
 *
 * @memberof Arquivo
 */
  public set descricao(value: String) {
    this._descricao = value;
  }

}
