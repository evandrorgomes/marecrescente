import { BaseEntidade } from '../base.entidade';
import { TiposDoador } from '../enums/tipos.doador';
import { GenotipoDTO } from 'app/paciente/genotipo.dto';
import { Exame } from 'app/paciente/cadastro/exame/exame.';
import { PedidoExame } from 'app/laboratorio/pedido.exame';
import { forEach } from '@angular/router/src/utils/collection';
import { ExameDoadorInternacional } from 'app/doador/exame.doador.internacional';
import { ExameDoadorNacional } from 'app/doador/exame.doador.nacional';
import { ExamePaciente } from 'app/paciente/cadastro/exame/exame.paciente';
import { ConvertUtil } from 'app/shared/util/convert.util';
import { ExameDoador } from 'app/shared/classes/exame.doador';

export class GenotipoDivergenteDTO extends BaseEntidade {

    private _tipoDoador: TiposDoador;
	private _genotiposDto: GenotipoDTO[];
	private _exames: ExameDoador[];
	private _pedidos: PedidoExame[];
    private _existeSolicitacaoFase3EmAberto: boolean;
    private _textoEmailDivergencia: string;

    /**
     * Getter tipoDoador
     * @return {TiposDoador}
     */
	public get tipoDoador(): TiposDoador {
		return this._tipoDoador;
	}

    /**
     * Setter tipoDoador
     * @param {TiposDoador} value
     */
	public set tipoDoador(value: TiposDoador) {
		this._tipoDoador = value;
	}

    /**
     * Getter genotiposDto
     * @return {GenotipoDTO[]}
     */
	public get genotiposDto(): GenotipoDTO[] {
		return this._genotiposDto;
	}

    /**
     * Setter genotiposDto
     * @param {GenotipoDTO[]} value
     */
	public set genotiposDto(value: GenotipoDTO[]) {
		this._genotiposDto = value;
	}

    /**
     * Getter exames
     * @return {Exame[]}
     */
	public get exames(): ExameDoador[] {
		return this._exames;
	}

    /**
     * Setter exames
     * @param {Exame[]} value
     */
	public set exames(value: ExameDoador[]) {
		this._exames = value;
	}

    /**
     * Getter pedidos
     * @return {PedidoExame[]}
     */
	public get pedidos(): PedidoExame[] {
		return this._pedidos;
	}

    /**
     * Setter pedidos
     * @param {PedidoExame[]} value
     */
	public set pedidos(value: PedidoExame[]) {
		this._pedidos = value;
	}

    /**
     * Getter existeSolicitacaoFase3EmAberto
     * @return {boolean}
     */
	public get existeSolicitacaoFase3EmAberto(): boolean {
		return this._existeSolicitacaoFase3EmAberto;
	}

    /**
     * Setter existeSolicitacaoFase3EmAberto
     * @param {boolean} value
     */
	public set existeSolicitacaoFase3EmAberto(value: boolean) {
		this._existeSolicitacaoFase3EmAberto = value;
	}

    /**
     * Getter textoEmailDivergencia
     * @return {string}
     */
	public get textoEmailDivergencia(): string {
		return this._textoEmailDivergencia;
	}

    /**
     * Setter textoEmailDivergencia
     * @param {string} value
     */
	public set textoEmailDivergencia(value: string) {
		this._textoEmailDivergencia = value;
	}

    public jsonToEntity(res: any): GenotipoDivergenteDTO {

        if (res.tipoDoador) {
			this.tipoDoador = TiposDoador.valueOf(res.tipoDoador);
        }

        if (res.genotiposDto) {
            this.genotiposDto = [];
            res.genotiposDto.forEach(genotipoDTO => {
                this.genotiposDto.push(new GenotipoDTO().jsonToEntity(genotipoDTO));
            });
        }

        if(res.exames){
            this.exames = [];
            res.exames.forEach(exame => {
                if (exame['@type']) {
                    if (exame['@type'] === 'exameDoadorNacional') {
                        this.exames.push(new ExameDoadorNacional().jsonToEntity(exame));
                    }
                    else if (exame['@type'] === 'exameDoadorInternacional') {
                        this.exames.push(new ExameDoadorInternacional().jsonToEntity(exame));
                    }
                    else {
                        new Error('Method not implemented.');
                    }
                }
            });
        }

        if (res.pedidos) {
            this.pedidos = [];
            res.pedidos.forEach(pedido => {
                this.pedidos.push(new PedidoExame().jsonToEntity(pedido));
            });
        }

        this.existeSolicitacaoFase3EmAberto = ConvertUtil.parseJsonParaAtributos(res.existeSolicitacaoFase3EmAberto, new Boolean());
        this.textoEmailDivergencia = ConvertUtil.parseJsonParaAtributos(res.textoEmailDivergencia, new String());

        return this;
    }


}