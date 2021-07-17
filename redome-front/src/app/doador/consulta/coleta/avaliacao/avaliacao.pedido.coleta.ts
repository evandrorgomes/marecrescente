import { UsuarioLogado } from '../../../../shared/dominio/usuario.logado';
import { Solicitacao } from '../../../solicitacao/solicitacao';
import { BaseEntidade } from '../../../../shared/base.entidade';


/**
 * Classe de representação de Avaliação de Pedido de Coleta.
 * @author Filipe Paes
 */
export class AvaliacaoPedidoColeta extends BaseEntidade {
    public jsonToEntity(res: any) {
        throw new Error("Method not implemented.");
    }

    private _id: Number;

    private _dataAvaliacao: Date;

    private _dataCriacao: Date;

    private _pedidoProssegue: Boolean;

    private _justificativa: String;

    private _solicitacao: Solicitacao;

    private _usuario: UsuarioLogado;


    public get id(): Number {
        return this._id;
    }

    public set id(value: Number) {
        this._id = value;
    }


    public get dataAvaliacao(): Date {
        return this._dataAvaliacao;
    }

    public set dataAvaliacao(value: Date) {
        this._dataAvaliacao = value;
    }


    public get dataCriacao(): Date {
        return this._dataCriacao;
    }

    public set dataCriacao(value: Date) {
        this._dataCriacao = value;
    }


    public get pedidoProssegue(): Boolean {
        return this._pedidoProssegue;
    }

    public set pedidoProssegue(value: Boolean) {
        this._pedidoProssegue = value;
    }


    public get justificativa(): String {
        return this._justificativa;
    }

    public set justificativa(value: String) {
        this._justificativa = value;
    }


    public get solicitacao(): Solicitacao {
        return this._solicitacao;
    }

    public set solicitacao(value: Solicitacao) {
        this._solicitacao = value;
    }


    public get usuario(): UsuarioLogado {
        return this._usuario;
    }

    public set usuario(value: UsuarioLogado) {
        this._usuario = value;
    }

}