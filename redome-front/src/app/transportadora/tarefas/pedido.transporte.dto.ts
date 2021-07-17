import { TiposTarefa } from '../../shared/enums/tipos.tarefa';
import { UsuarioLogado } from "../../shared/dominio/usuario.logado";
import { BaseEntidade } from "../../shared/base.entidade";
import { StatusPedidoTransporte } from "./status.pedido.transporte";
import { EnderecoContato } from "../../shared/classes/endereco.contato";
import { Courier } from '../courier';
import { ConvertUtil } from '../../shared/util/convert.util';


export class PedidoTransporteDTO extends BaseEntidade {

    private _idPedidoTransporte: number;
    private _idTarefa: number;
    private _horaPrevistaRetirada: Date;
    private _rmr: number;
    private _identificacao: string;
    private _nomeLocalRetirada: string;
    private _nomeCentroTransplante: string;
    private _status: StatusPedidoTransporte;
    private _tipoTarefa: TiposTarefa;
    private _enderecoCT:EnderecoContato;
    private _enderecoCC:EnderecoContato;
    private _enderecoBancoCordao:string;
    private _dataRetirada: Date;
    private _dataEntrega: Date;
    private _courier:Courier;
    private _dadosVoo:string;
    private _caminho:string;
    private _nomeFonteCelula: string;
    private _arquivos: string[];



    /**
     * Getter horaPrevistaRetirada
     * @return {Date}
     */
    public get horaPrevistaRetirada(): Date {
        return this._horaPrevistaRetirada;
    }

    /**
     * Setter horaPrevistaRetirada
     * @param {Date} value
     */
    public set horaPrevistaRetirada(value: Date) {
        this._horaPrevistaRetirada = value;
    }

    public get nomeLocalRetirada(): string {
        return this._nomeLocalRetirada;
    }

    public set nomeLocalRetirada(value: string) {
        this._nomeLocalRetirada = value;
    }

    /**
     * Getter nomeCentroTransplante
     * @return {string}
     */
    public get nomeCentroTransplante(): string {
        return this._nomeCentroTransplante;
    }

    /**
     * Setter nomeCentroTransplante
     * @param {string} value
     */
    public set nomeCentroTransplante(value: string) {
        this._nomeCentroTransplante = value;
    }

    /**
     * Getter rmr
     * @return {number}
     */
    public get rmr(): number {
        return this._rmr;
    }

    /**
     * Setter rmr
     * @param {number} value
     */
    public set rmr(value: number) {
        this._rmr = value;
    }

    /**
     * Getter dmr
     * @return {number}
     */
    public get identificacao(): string {
        return this._identificacao;
    }

    /**
     * Setter dmr
     * @param {number} value
     */
    public set identificacao(value: string) {
        this._identificacao = value;
    }

    /**
     * Getter status
     * @return {StatusPedidoTransporte}
     */
    public get status(): StatusPedidoTransporte {
        return this._status;
    }

    /**
     * Setter status
     * @param {StatusPedidoTransporte} value
     */
    public set status(value: StatusPedidoTransporte) {
        this._status = value;
    }


    /**
     * Getter idPedidoTransporte
     * @return {number}
     */
    public get idPedidoTransporte(): number {
        return this._idPedidoTransporte;
    }

    /**
     * Setter idPedidoTransporte
     * @param {number} value
     */
    public set idPedidoTransporte(value: number) {
        this._idPedidoTransporte = value;
    }

    /**
     * Getter idTarefa
     * @return {number}
     */
    public get idTarefa(): number {
        return this._idTarefa;
    }

    /**
     * Setter idTarefa
     * @param {number} value
     */
    public set idTarefa(value: number) {
        this._idTarefa = value;
    }

    /**
     * Getter tipoTarefa
     * @return {TiposTarefa}
     */
	public get tipoTarefa(): TiposTarefa {
		return this._tipoTarefa;
	}

    /**
     * Setter tipoTarefa
     * @param {TiposTarefa} value
     */
	public set tipoTarefa(value: TiposTarefa) {
		this._tipoTarefa = value;
	}


    /**
     * Getter enderecoCT
     * @return {EnderecoContato}
     */
	public get enderecoCT(): EnderecoContato {
		return this._enderecoCT;
	}

    /**
     * Setter enderecoCT
     * @param {EnderecoContato} value
     */
	public set enderecoCT(value: EnderecoContato) {
		this._enderecoCT = value;
	}

    /**
     * Getter dataRetirada
     * @return {Date}
     */
	public get dataRetirada(): Date {
		return this._dataRetirada;
	}

    /**
     * Setter dataRetirada
     * @param {Date} value
     */
	public set dataRetirada(value: Date) {
		this._dataRetirada = value;
	}


    /**
     * Getter courier
     * @return {Courier}
     */
	public get courier(): Courier {
		return this._courier;
	}

    /**
     * Setter courier
     * @param {Courier} value
     */
	public set courier(value: Courier) {
		this._courier = value;
	}


    /**
     * Getter dadosVoo
     * @return {string}
     */
	public get dadosVoo(): string {
		return this._dadosVoo;
	}

    /**
     * Setter dadosVoo
     * @param {string} value
     */
	public set dadosVoo(value: string) {
		this._dadosVoo = value;
	}

    /**
     * Getter caminho
     * @return {string}
     */
	public get caminho(): string {
		return this._caminho;
	}

    /**
     * Setter caminho
     * @param {string} value
     */
	public set caminho(value: string) {
		this._caminho = value;
    }

    /**
     * Getter enderecoCC
     * @return {EnderecoContato}
     */
	public get enderecoCC(): EnderecoContato {
		return this._enderecoCC;
	}

    /**
     * Setter enderecoCC
     * @param {EnderecoContato} value
     */
	public set enderecoCC(value: EnderecoContato) {
		this._enderecoCC = value;
	}

    /**
     * Getter enderecoBancoCordao
     * @return {string}
     */
	public get enderecoBancoCordao(): string {
		return this._enderecoBancoCordao;
	}

    /**
     * Setter enderecoBancoCordao
     * @param {string} value
     */
	public set enderecoBancoCordao(value: string) {
		this._enderecoBancoCordao = value;
    }

    /**
     * Getter nomeFonteCelula
     * @return {string}
     */
	public get nomeFonteCelula(): string {
		return this._nomeFonteCelula;
	}

    /**
     * Setter nomeFonteCelula
     * @param {string} value
     */
	public set nomeFonteCelula(value: string) {
		this._nomeFonteCelula = value;
    }

    /**
     * Getter arquivos
     * @return {string[]}
     */
	public get arquivos(): string[] {
		return this._arquivos;
	}

    /**
     * Setter arquivos
     * @param {string[]} value
     */
	public set arquivos(value: string[]) {
		this._arquivos = value;
	}

    /**
     * Getter dataEntrega
     * @return {Date}
     */
	public get dataEntrega(): Date {
		return this._dataEntrega;
	}

    /**
     * Setter dataEntrega
     * @param {Date} value
     */
	public set dataEntrega(value: Date) {
		this._dataEntrega = value;
	}


    public jsonToEntity(res: any):PedidoTransporteDTO {
        if(res.courier){
            this._courier = new Courier().jsonToEntity(res.courier);
        }
        if(res.status){
            this._status = new StatusPedidoTransporte().jsonToEntity(res.status);
        }

        return this;
    }

}
