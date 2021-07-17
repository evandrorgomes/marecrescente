import { UsuarioLogado } from '../../../shared/dominio/usuario.logado';
import { Busca } from '../busca';
import { BaseEntidade } from '../../../shared/base.entidade';
/**
 * Classe Bean utilizada para definir os campos de diálogo de busca do Paciente
 *
 * @author Filipe Paes
 * @export
 * @class Busca
 */
export class DialogoBusca extends BaseEntidade {
    
    private _busca:Busca;
    private _usuario:UsuarioLogado;
    private _mensagem:String;
    private _dataHoraMensagem:Date;

    /**
     * Getter busca
     * @return {Busca}
     */
	public get busca(): Busca {
		return this._busca;
	}

    /**
     * Setter busca
     * @param {Busca} value
     */
	public set busca(value: Busca) {
		this._busca = value;
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
     * Getter mensagem
     * @return {String}
     */
	public get mensagem(): String {
		return this._mensagem;
	}

    /**
     * Setter mensagem
     * @param {String} value
     */
	public set mensagem(value: String) {
		this._mensagem = value;
	}

    /**
     * Getter dataHoraMensagem
     * @return {Date}
     */
	public get dataHoraMensagem(): Date {
		return this._dataHoraMensagem;
	}

    /**
     * Setter dataHoraMensagem
     * @param {Date} value
     */
	public set dataHoraMensagem(value: Date) {
		this._dataHoraMensagem = value;
	}
 
    public jsonToEntity(res: any) {
        throw new Error("Method not implemented.");
    }
}