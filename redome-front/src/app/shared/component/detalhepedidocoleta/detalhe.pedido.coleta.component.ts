import { DataUtil } from '../../util/data.util';
import { Component, Input } from "@angular/core";
import { Disponibilidade } from '../../../doador/consulta/workup/disponibilidade';

/**
 * Classe que representa o componente para agendar pedido de coleta.
 * @author Bruno Sousa
 */
@Component({
    selector: "detalhe-pedido-coleta",
    templateUrl: './detalhe.pedido.coleta.component.html'
})
export class DetalhePedidoColetaComponent {

    private _cordao: boolean;
    private _centroColeta: string;
    private _dataColeta: Date;
    private _fonteCelula: string;
    private _dataPrevistaDisponibilidadeDoador: Date;
    private _dataPrevistaLiberacaoDoador: Date;

    private _dataPrevistaColeta1: Date;
    private _dataPrevistaColeta2: Date;
    private _ultimaDisponibilidade: Disponibilidade;

    private _naoExibirDadosDoadorInternacional: boolean;

	public get centroColeta(): string {
		return this._centroColeta;
	}

    @Input()
	public set centrocoleta(value: string) {
		this._centroColeta = value;
    }
    
	public get dataColeta(): Date {
		return this._dataColeta;
	}

    @Input()
	public set datacoleta(value: string) {
        if (value) {
            try {
                this._dataColeta = DataUtil.toDate(value);
            } catch (error) {
                this._dataColeta = undefined;
            }    
        }
        else {
            this._dataColeta = undefined;
        }
		
    }
    
	public get fonteCelula(): string {
		return this._fonteCelula;
	}

    @Input()
	public set fontecelula(value: string) {
		this._fonteCelula = value;
    }
    

	public get dataPrevistaDisponibilidadeDoador(): Date {
		return this._dataPrevistaDisponibilidadeDoador;
	}

    @Input()
	public set dataprevistadisponibilidadedoador(value: string) {
        if (value) {
            try {
                this._dataPrevistaDisponibilidadeDoador = DataUtil.toDate(value);
            } catch (error) {
                this._dataPrevistaDisponibilidadeDoador = undefined;
            }    
        }
        else {
            this._dataPrevistaDisponibilidadeDoador = undefined;
        }		
	}
    

	public get dataPrevistaLiberacaoDoador(): Date {
		return this._dataPrevistaLiberacaoDoador;
	}

    @Input()
	public set dataprevistaliberacaodoador(value: string) {
        if (value) {
            try {
                this._dataPrevistaLiberacaoDoador = DataUtil.toDate(value);
            } catch (error) {
                this._dataPrevistaLiberacaoDoador = undefined;
            }    
        }
        else {
            this._dataPrevistaLiberacaoDoador = undefined;
        }
	}
    

    /**
     * Getter cordao
     * @return {boolean}
     */
	public ehCordao(): boolean {
		return this._cordao;
	}

    @Input()
	public set cordao(value: string|boolean) {
        if (value) {            
            if (value == 'true' || value) {
                this._cordao = true;
            }
            else 
                this._cordao = false;
        }
        else {
            this._cordao = false;
        }
	}


    /**
     * Getter dataPrevistaColeta1
     * @return {Date}
     */
	public get dataPrevistaColeta1(): Date {
		return this._dataPrevistaColeta1;
	}
    
    @Input()
	public set dataprevistacoleta1(value: string) {
        if (value) {
            try {
                this._dataPrevistaColeta1 = DataUtil.toDate(value);
            } catch (error) {
                this._dataPrevistaColeta1 = undefined;
            }    
        }
        else {
            this._dataPrevistaColeta1 = undefined;
        }
    }

    /**
     * Getter dataPrevistaColeta2
     * @return {Date}
     */
	public get dataPrevistaColeta2(): Date {
		return this._dataPrevistaColeta2;
	}

    @Input()
	public set dataprevistacoleta2(value: string) {
        if (value) {
            try {
                this._dataPrevistaColeta2 = DataUtil.toDate(value);
            } catch (error) {
                this._dataPrevistaColeta2 = undefined;
            }    
        }
        else {
            this._dataPrevistaColeta2 = undefined;
        }
    }

    /**
     * Getter ultimaDisponibilidade
     * @return {Disponibilidade}
     */
	public get ultimaDisponibilidade(): Disponibilidade {
		return this._ultimaDisponibilidade;
    }
    
    @Input()
	public set ultimadisponibilidade(value: Disponibilidade) {
        if (value) {
            try {
                this._ultimaDisponibilidade = value;
            } catch (error) {
                this._ultimaDisponibilidade = undefined;
            }    
        }
        else {
            this._ultimaDisponibilidade = undefined;
        }
    }

    /**
     * Getter naoExibirDadosDoadorInternacional
     * @return {boolean}
     */
	public naoExibirDoadorInternacional(): boolean {
		return this._naoExibirDadosDoadorInternacional;
	}

    /**
     * Setter naoExibirDadosDoadorInternacional
     * @param {boolean} value
     */
    @Input()
    public set naoExibirDadosDoadorInternacional(value: string|boolean) {
        if (value) {
            if (value === 'true' || value) {
                this._naoExibirDadosDoadorInternacional = true;
            }
            else 
                this._naoExibirDadosDoadorInternacional = false;
        }
        else {
            this._naoExibirDadosDoadorInternacional = false;
        }
    }

}