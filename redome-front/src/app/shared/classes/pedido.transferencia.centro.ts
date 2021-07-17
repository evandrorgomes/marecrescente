import { BaseEntidade } from 'app/shared/base.entidade';
import { TiposTransferenciaCentro } from 'app/shared/enums/tipos.transferencia.centro';
import { Paciente } from 'app/paciente/paciente';
import { CentroTransplante } from 'app/shared/dominio/centro.transplante';
import { UsuarioLogado } from '../dominio/usuario.logado';
import { ConvertUtil } from 'app/shared/util/convert.util';

/**
 * Classe que representa o Pedido de transferencia de centro.
 *
 * @author Bruno Sousa
 * @export
 * @class PedidoTransferenciaCentro
 * @extends {BaseEntidade}
 */
export class PedidoTransferenciaCentro extends BaseEntidade {

	private _id: number;
	private _dataCriacao: Date;
	private _dataAtualizacao: Date;
	private _tipoTransferenciaCentro: TiposTransferenciaCentro;
	private _aprovado: boolean;	
	private _paciente: Paciente;	
	private _centroAvaliadorOrigem: CentroTransplante;
	private _centroAvaliadorDestino: CentroTransplante;
    private _usuario: UsuarioLogado;

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
     * Getter dataCriacao
     * @return {Date}
     */
	public get dataCriacao(): Date {
		return this._dataCriacao;
	}

    /**
     * Setter dataCriacao
     * @param {Date} value
     */
	public set dataCriacao(value: Date) {
		this._dataCriacao = value;
	}

    /**
     * Getter dataAtualizacao
     * @return {Date}
     */
	public get dataAtualizacao(): Date {
		return this._dataAtualizacao;
	}

    /**
     * Setter dataAtualizacao
     * @param {Date} value
     */
	public set dataAtualizacao(value: Date) {
		this._dataAtualizacao = value;
	}

    /**
     * Getter tipoTransferenciaCentro
     * @return {TiposTransferenciaCentro}
     */
	public get tipoTransferenciaCentro(): TiposTransferenciaCentro {
		return this._tipoTransferenciaCentro;
	}

    /**
     * Setter tipoTransferenciaCentro
     * @param {TiposTransferenciaCentro} value
     */
	public set tipoTransferenciaCentro(value: TiposTransferenciaCentro) {
		this._tipoTransferenciaCentro = value;
	}

    /**
     * Getter aprovado
     * @return {boolean}
     */
	public get aprovado(): boolean {
		return this._aprovado;
	}

    /**
     * Setter aprovado
     * @param {boolean} value
     */
	public set aprovado(value: boolean) {
		this._aprovado = value;
	}

    /**
     * Getter paciente
     * @return {Paciente}
     */
	public get paciente(): Paciente {
		return this._paciente;
	}

    /**
     * Setter paciente
     * @param {Paciente} value
     */
	public set paciente(value: Paciente) {
		this._paciente = value;
	}

    /**
     * Getter centroAvaliadorOrigem
     * @return {CentroTransplante}
     */
	public get centroAvaliadorOrigem(): CentroTransplante {
		return this._centroAvaliadorOrigem;
	}

    /**
     * Setter centroAvaliadorOrigem
     * @param {CentroTransplante} value
     */
	public set centroAvaliadorOrigem(value: CentroTransplante) {
		this._centroAvaliadorOrigem = value;
	}

    /**
     * Getter centroAvaliadorDestino
     * @return {CentroTransplante}
     */
	public get centroAvaliadorDestino(): CentroTransplante {
		return this._centroAvaliadorDestino;
	}

    /**
     * Setter centroAvaliadorDestino
     * @param {CentroTransplante} value
     */
	public set centroAvaliadorDestino(value: CentroTransplante) {
		this._centroAvaliadorDestino = value;
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

    public jsonToEntity(res: any): PedidoTransferenciaCentro  {
        
        if (res.tipoTransferenciaCentro != null) { 
            this.tipoTransferenciaCentro = TiposTransferenciaCentro[typeof res.tipoTransferenciaCentro];
        }

        if (res.paciente) {
            this.paciente = new Paciente().jsonToEntity(res.paciente);	
        }

        if (res.centroAvaliadorOrigem) {
            this.centroAvaliadorOrigem = new CentroTransplante().jsonToEntity(res.centroAvaliadorOrigem);
        }

        if (res.centroAvaliadorDestino) {
            this.centroAvaliadorDestino = new CentroTransplante().jsonToEntity(res.centroAvaliadorDestino);
        }

        if (res.usuario) {
            this.usuario = new UsuarioLogado().jsonToEntity(res.usuario);
        }

        this.id = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
        this.dataCriacao = ConvertUtil.parseJsonParaAtributos(res.dataCriacao, new Date());
        this.dataAtualizacao = ConvertUtil.parseJsonParaAtributos(res.dataAtualizacao, new Date());
        this.aprovado = ConvertUtil.parseJsonParaAtributos(res.aprovado, new Boolean());
           
        return this;
    }

}