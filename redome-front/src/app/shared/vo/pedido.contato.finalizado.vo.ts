import { AcaoPedidoContato } from 'app/shared/enums/acao.pedido.contato';
import { Formulario } from '../classes/formulario';
import { BaseEntidade } from '../base.entidade';
import { IPedidoContatoFinalizadoVo } from './interface.pedido.contato.finalizado.vo';

export class PedidoContatoFinalizadoVo implements IPedidoContatoFinalizadoVo {

    private _contactado: boolean; 
	private _contactadoPorTerceiro: boolean; 
	private _acao: AcaoPedidoContato;
	private _idMotivoStatusDoador: number;
	private _tempoInativacaoTemporaria: number;
	private _formulario: Formulario;
    private _hemocentro: number;
    

    /**
     * Getter contactado
     * @return {boolean}
     */
	public get contactado(): boolean {
		return this._contactado;
	}

    /**
     * Setter contactado
     * @param {boolean} value
     */
	public set contactado(value: boolean) {
		this._contactado = value;
	}

    /**
     * Getter contactadoPorTerceiro
     * @return {boolean}
     */
	public get contactadoPorTerceiro(): boolean {
		return this._contactadoPorTerceiro;
	}

    /**
     * Setter contactadoPorTerceiro
     * @param {boolean} value
     */
	public set contactadoPorTerceiro(value: boolean) {
		this._contactadoPorTerceiro = value;
    }

    /**
     * Getter idMotivoStatusDoador
     * @return {number}
     */
	public get idMotivoStatusDoador(): number {
		return this._idMotivoStatusDoador;
	}

    /**
     * Setter idMotivoStatusDoador
     * @param {number} value
     */
	public set idMotivoStatusDoador(value: number) {
		this._idMotivoStatusDoador = value;
	}

    /**
     * Getter tempoInativacaoTemporaria
     * @return {number}
     */
	public get tempoInativacaoTemporaria(): number {
		return this._tempoInativacaoTemporaria;
	}

    /**
     * Setter tempoInativacaoTemporaria
     * @param {number} value
     */
	public set tempoInativacaoTemporaria(value: number) {
		this._tempoInativacaoTemporaria = value;
	}

    /**
     * Getter formulario
     * @return {Formulario}
     */
	public get formulario(): Formulario {
		return this._formulario;
	}

    /**
     * Setter formulario
     * @param {Formulario} value
     */
	public set formulario(value: Formulario) {
		this._formulario = value;
	}

    /**
     * Getter hemocentro
     * @return {number}
     */
	public get hemocentro(): number {
		return this._hemocentro;
	}

    /**
     * Setter hemocentro
     * @param {number} value
     */
	public set hemocentro(value: number) {
		this._hemocentro = value;
    }

    /**
     * Getter acao
     * @return {AcaoPedidoContato}
     */
	public get acao(): AcaoPedidoContato {
		return this._acao;
	}

    /**
     * Setter acao
     * @param {AcaoPedidoContato} value
     */
	public set acao(value: AcaoPedidoContato) {
		this._acao = value;
    }
    
    toJSON(): any {
        return {
            contactado: this.contactado,
            contactadoPorTerceiro: this.contactadoPorTerceiro,
            acao: this.acao ? this.acao.id: undefined,
            idMotivoStatusDoador: this.idMotivoStatusDoador,
	        tempoInativacaoTemporaria: this.tempoInativacaoTemporaria,
	        formulario: this.formulario,
            hemocentro: this.hemocentro

        };
    }   

}