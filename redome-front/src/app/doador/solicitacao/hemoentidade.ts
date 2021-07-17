import { BaseEntidade } from "../../shared/base.entidade";

/**
 * Classe Bean utilizada para definir os campos de HemoEntidade.
 * Entidade representa as unidades de hemocentro ou hemonúcleos
 * que podem ser selecionados.
 *
 * @author Pizão.
 */
export class HemoEntidade extends BaseEntidade {

  private _id: number;
  private _nome: string;


  public get id(): number {
    return this._id;
  }

  public set id(value: number) {
    this._id = value;
  }

	public get nome(): string {
		return this._nome;
	}

	public set nome(value: string) {
		this._nome = value;
  }
  
  public jsonToEntity(res: any): HemoEntidade {
    throw new Error("Method not implemented.");
  }

}