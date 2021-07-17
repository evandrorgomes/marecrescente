import { AcaoPedidoContato } from 'app/shared/enums/acao.pedido.contato';
import { Formulario } from '../classes/formulario';
import { BaseEntidade } from '../base.entidade';
import { IAnaliseMedicaFinalizadaVo } from './interface.analise.medica.finalizada.vo';

export class AnaliseMedicaFinalizadaVo implements IAnaliseMedicaFinalizadaVo {

	private _acao: AcaoPedidoContato;
	private _idMotivoStatusDoador: number;
	private _tempoInativacaoTemporaria: number;
	private _formulario: Formulario;
    
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
    
    toJSON() {
        return {            
            acao: this.acao ? this.acao.id: undefined,
            idMotivoStatusDoador: this.idMotivoStatusDoador,
	        tempoInativacaoTemporaria: this.tempoInativacaoTemporaria,
	        formulario: this.formulario
        };
    }   

}