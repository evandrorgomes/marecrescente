import { CentroTransplante } from 'app/shared/dominio/centro.transplante';
import { BaseEntidade } from "../base.entidade";
import { UsuarioLogado } from "./usuario.logado";
import { ConvertUtil } from '../util/convert.util';

/**
 * Classe associativa de centro transplante e usuario.
 *  
 * @author Filipe Paes
 */
export class CentroTransplanteUsuario extends BaseEntidade {
    private _id:number;
    private _usuario:UsuarioLogado;
    private _centroTransplante:CentroTransplante;
    private _responsavel:boolean;



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
     * Getter usuario
     * @return {UsuarioLogado}
     */
	public get usuario(): UsuarioLogado {
		return this._usuario;
	}

    /**
     * Setter usuario
     * @param {UsuarioLogado} value
     */
	public set usuario(value: UsuarioLogado) {
		this._usuario = value;
	}

    /**
     * Getter centroTransplante
     * @return {CentroTransplante}
     */
	public get centroTransplante(): CentroTransplante {
		return this._centroTransplante;
	}

    /**
     * Setter centroTransplante
     * @param {CentroTransplante} value
     */
	public set centroTransplante(value: CentroTransplante) {
		this._centroTransplante = value;
	}

    /**
     * Getter responsavel
     * @return {boolean}
     */
	public get responsavel(): boolean {
		return this._responsavel;
	}

    /**
     * Setter responsavel
     * @param {boolean} value
     */
	public set responsavel(value: boolean) {
		this._responsavel = value;
	}

    public jsonToEntity(res: any):this{
        this._id =  ConvertUtil.parseJsonParaAtributos(res.id, new Number());
        this._usuario =   new UsuarioLogado();
        this._usuario.id = ConvertUtil.parseJsonParaAtributos(res.usuario.id, new Number());
        this._usuario.nome = ConvertUtil.parseJsonParaAtributos(res.usuario.nome, new String());
        if(res.centroTransplante){
            this._centroTransplante = new CentroTransplante();
            this._centroTransplante.id = ConvertUtil.parseJsonParaAtributos(res.centroTransplante.id, new Number());
            this._centroTransplante.nome = ConvertUtil.parseJsonParaAtributos(res.centroTransplante.nome, new Number());
            this._responsavel =  ConvertUtil.parseJsonParaAtributos(res.responsavel, new Boolean());
        }
        return this;
    }

}