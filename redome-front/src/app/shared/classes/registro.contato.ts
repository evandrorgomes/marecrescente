import { BaseEntidade } from '../base.entidade';
import { PedidoContato } from '../../doador/solicitacao/fase2/pedido.contato';
import { Usuario } from '../dominio/usuario';
import { RegistroTipoOcorrencia } from './registro.tipo.ocorencia';
import { ConvertUtil } from '../util/convert.util';
import { ContatoTelefonico } from './contato.telefonico';
export class RegistroContato extends BaseEntidade{
    
    private _id:number;
    private _pedidoContato:PedidoContato;
    private _momentoLigacao:Date;
    private _usuario:Usuario;
    private _registroTipoOcorrencia:RegistroTipoOcorrencia;
    private _observacao:string;
    private _contatoTelefonico: ContatoTelefonico;


    /**
     * Getter id
     * @return {number}
     */
	public get id(): number {
		return this._id;
	}

    /**
     * Getter pedidoContato
     * @return {PedidoContato}
     */
	public get pedidoContato(): PedidoContato {
		return this._pedidoContato;
	}

    /**
     * Getter momentoLigacao
     * @return {Date}
     */
	public get momentoLigacao(): Date {
		return this._momentoLigacao;
	}

    /**
     * Getter usuario
     * @return {Usuario}
     */
	public get usuario(): Usuario {
		return this._usuario;
	}

    /**
     * Getter registroTipoOcorrencia
     * @return {RegistroTipoOcorrencia}
     */
	public get registroTipoOcorrencia(): RegistroTipoOcorrencia {
		return this._registroTipoOcorrencia;
	}

    /**
     * Getter observacao
     * @return {string}
     */
	public get observacao(): string {
		return this._observacao;
	}

    /**
     * Setter id
     * @param {number} value
     */
	public set id(value: number) {
		this._id = value;
	}

    /**
     * Setter pedidoContato
     * @param {PedidoContato} value
     */
	public set pedidoContato(value: PedidoContato) {
		this._pedidoContato = value;
	}

    /**
     * Setter momentoLigacao
     * @param {Date} value
     */
	public set momentoLigacao(value: Date) {
		this._momentoLigacao = value;
	}

    /**
     * Setter usuario
     * @param {Usuario} value
     */
	public set usuario(value: Usuario) {
		this._usuario = value;
	}

    /**
     * Setter registroTipoOcorrencia
     * @param {RegistroTipoOcorrencia} value
     */
	public set registroTipoOcorrencia(value: RegistroTipoOcorrencia) {
		this._registroTipoOcorrencia = value;
	}

    /**
     * Setter observacao
     * @param {string} value
     */
	public set observacao(value: string) {
		this._observacao = value;
	}
    /**
     * Getter contatoTelefonico
     * @return {ContatoTelefonico}
     */
	public get contatoTelefonico(): ContatoTelefonico {
		return this._contatoTelefonico;
	}

    /**
     * Setter contatoTelefonico
     * @param {ContatoTelefonico} value
     */
	public set contatoTelefonico(value: ContatoTelefonico) {
		this._contatoTelefonico = value;
	}


    public jsonToEntity(res: any) :this {
        this.id = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
        this.observacao = ConvertUtil.parseJsonParaAtributos(res.observacao, new String());

        if(res.usuario){
            this.usuario = new Usuario().jsonToEntity(res.usuario)
        }
        if(res.pedidoContato){
            this.pedidoContato = new PedidoContato().jsonToEntity(res.pedidoContato);
        }
        if(res.registroTipoOcorrencia){
            this.registroTipoOcorrencia = new RegistroTipoOcorrencia().jsonToEntity(res.registroTipoOcorrencia);
        }
        if(res.contatoTelefonico){
            this.contatoTelefonico = new ContatoTelefonico().jsonToEntity(res.contatoTelefonico);
        }
        this.momentoLigacao = ConvertUtil.parseJsonParaAtributos(res.momentoLigacao, new Date());
        return this;
    }

}