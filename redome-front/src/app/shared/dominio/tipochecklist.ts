import { BaseEntidade } from "../base.entidade";
import { ConvertUtil } from "../util/convert.util";
import { CategoriaChecklist } from "./categoria.checklist";


/**
 * Classe de agrupamento de checklist para conclusão de tarefas.
 * @author Filipe Paes
 */
export class TipoChecklist extends BaseEntidade {
    private _id: number;
    private _nome: String;
    private _categorias: CategoriaChecklist[];

    /**
     * Getter id
     * @return {number}
     */
    public get id(): number {
        return this._id;
    }

    /**
     * Setter id
     * @param {number} value
     */
    public set id(value: number) {
        this._id = value;
    }


    /**
     * Getter nome
     * @return {String}
     */
    public get nome(): String {
        return this._nome;
    }

    /**
     * Setter nome
     * @param {String} value
     */
    public set nome(value: String) {
        this._nome = value;
    }



    /**
     * Getter categorias
     * @return {CategoriaChecklist[]}
     */
    public get categorias(): CategoriaChecklist[] {
        return this._categorias;
    }

    /**
     * Setter categorias
     * @param {CategoriaChecklist[]} value
     */
    public set categorias(value: CategoriaChecklist[]) {
        this._categorias = value;
    }


    public jsonToEntity(res: any) {
        this._id = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
        this._nome = ConvertUtil.parseJsonParaAtributos(res.nome, new String());
        if(res.categorias){
            this.categorias = [];
            res.categorias.forEach(categoria => {
                this.categorias.push(new  CategoriaChecklist().jsonToEntity(categoria));
            });
        }
    }
}
