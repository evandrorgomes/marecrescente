import { AtributoOrdenacao } from "./atributo.ordenacao";
import { BaseEntidade } from "../base.entidade";

/**
 * Classe utilizada para ordenacao de tarefa.
 * @author Fillipe Queiroz
 */
export class AtributoOrdenacaoDTO extends BaseEntidade{
    
	private _atributos: AtributoOrdenacao[];
	
	constructor(){
		super();
		this.atributos = [];
	}

	public get atributos(): AtributoOrdenacao[] {
		return this._atributos;
	}

	public set atributos(value: AtributoOrdenacao[]) {
		this._atributos = value;
	}

	public jsonToEntity(res: any) {
        throw new Error("Method not implemented.");
    }
    

}