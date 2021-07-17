import { BaseEntidade } from "../base.entidade";
import { Comentario } from "./comentario";


/**
 * Classe que Constroi a lista de Comentarios
 * @author Fillipe Queiroz
 */
export abstract class DialogoBuilder{

	protected _comentarios:Comentario[];
	public abstract buildComentarios(list:any):void;


	public get comentarios(): Comentario[] {
		return this._comentarios;
	}

}

