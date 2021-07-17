import { PedidoContato } from "../fase2/pedido.contato";
import { BaseEntidade } from "app/shared/base.entidade";
import { Usuario } from "app/shared/dominio/usuario";
import { MotivoStatusDoador } from "app/doador/inativacao/motivo.status.doador";
import { ConvertUtil } from "app/shared/util/convert.util";

export class AnaliseMedica extends BaseEntidade{
    
    private _id:number;
	private _pedidoContato : PedidoContato;
	private _motivoStatusDoador : MotivoStatusDoador;
    private _dataCriacao : Date;
    private _dataAnalise : Date;
    
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
     * Getter motivoStatusDoador
     * @return {MotivoStatusDoador}
     */
	public get motivoStatusDoador(): MotivoStatusDoador {
		return this._motivoStatusDoador;
	}

    /**
     * Getter dataCriacao
     * @return {Date}
     */
	public get dataCriacao(): Date {
		return this._dataCriacao;
	}

    /**
     * Getter dataAnalise
     * @return {Date}
     */
	public get dataAnalise(): Date {
		return this._dataAnalise;
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
     * Setter motivoStatusDoador
     * @param {MotivoStatusDoador} value
     */
	public set motivoStatusDoador(value: MotivoStatusDoador) {
		this._motivoStatusDoador = value;
	}

    /**
     * Setter dataCriacao
     * @param {Date} value
     */
	public set dataCriacao(value: Date) {
		this._dataCriacao = value;
	}

    /**
     * Setter dataAnalise
     * @param {Date} value
     */
	public set dataAnalise(value: Date) {
		this._dataAnalise = value;
	}


    public jsonToEntity(res: any): this{
        if(res.pedidoContato){
            this.pedidoContato =  new PedidoContato().jsonToEntity(res.pedidoContato);
        }
        if(res.motivoStatusDoador){
            this.motivoStatusDoador = new MotivoStatusDoador().jsonToEntity(res.motivoStatusDoador);
        }
        this.dataAnalise = ConvertUtil.parseJsonParaAtributos(res.dataAnalise, new Date());
        this.dataCriacao = ConvertUtil.parseJsonParaAtributos(res.dataCriacao, new Date());
        this.id = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
        return this;
    }

}