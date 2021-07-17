import { Solicitacao } from '../doador/solicitacao/solicitacao';
import { StatusPedidoExame } from './status.pedido.exame';
import { BaseEntidade } from '../shared/base.entidade';
import { Laboratorio } from '../shared/dominio/laboratorio';
import { TipoExame } from './tipo.exame';
import { Exame } from '../paciente/cadastro/exame/exame.';
import { Locus } from '../paciente/cadastro/exame/locus';
import { ConvertUtil } from 'app/shared/util/convert.util';
import { ExamePaciente } from 'app/paciente/cadastro/exame/exame.paciente';
import { ExameDoadorNacional } from 'app/doador/exame.doador.nacional';
import { ExameDoadorInternacional } from 'app/doador/exame.doador.internacional';

export class PedidoExame extends BaseEntidade {

    private _id: number;
    private _dataCriacao: Date;
    private _solicitacao: Solicitacao;
    private _dataColetaAmostra: Date;
    private _dataRecebimentoAmostra: Date;
    private _laboratorio: Laboratorio;
    private _exame: Exame;
    private _tipoExame: TipoExame;
    private _statusPedidoExame: StatusPedidoExame;
    private _justificativa: string;
    private _locusSolicitados:Locus[];

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
     * Getter solicitacao
     * @return {Solicitacao}
     */
    public get solicitacao(): Solicitacao {
        return this._solicitacao;
    }

    /**
     * Setter solicitacao
     * @param {Solicitacao} value
     */
    public set solicitacao(value: Solicitacao) {
        this._solicitacao = value;
    }
    
    /**
     * Getter dataColetaAmostra
     * @return {Date}
     */
    public get dataColetaAmostra(): Date {
        return this._dataColetaAmostra;
    }

    /**
     * Setter dataColetaAmostra
     * @param {Date} value
     */
    public set dataColetaAmostra(value: Date) {
        this._dataColetaAmostra = value;
    }



    /**
     * Getter dataRecebimentoAmostra
     * @return {Date}
     */
    public get dataRecebimentoAmostra(): Date {
        return this._dataRecebimentoAmostra;
    }

    /**
     * Setter dataRecebimentoAmostra
     * @param {Date} value
     */
    public set dataRecebimentoAmostra(value: Date) {
        this._dataRecebimentoAmostra = value;
    }


    /**
     * Getter laboratorio
     * @return {Laboratorio}
     */
    public get laboratorio(): Laboratorio {
        return this._laboratorio;
    }

    /**
     * Setter laboratorio
     * @param {Laboratorio} value
     */
    public set laboratorio(value: Laboratorio) {
        this._laboratorio = value;
    }
    
    /**
     * Getter exame
     * @return {Exame}
     */
	public get exame(): Exame {
		return this._exame;
	}

    /**
     * Setter exame
     * @param {Exame} value
     */
	public set exame(value: Exame) {
		this._exame = value;
	}

    /**
     * Getter tipoExame
     * @return {TipoExame}
     */
	public get tipoExame(): TipoExame {
		return this._tipoExame;
	}

    /**
     * Setter tipoExame
     * @param {TipoExame} value
     */
	public set tipoExame(value: TipoExame) {
		this._tipoExame = value;
	}
    
/**
     * Getter statusPedidoExame
     * @return {StatusPedidoExame}
     */
    public get statusPedidoExame(): StatusPedidoExame {
        return this._statusPedidoExame;
    }

    /**
     * Setter statusPedidoExame
     * @param {StatusPedidoExame} value
     */
    public set statusPedidoExame(value: StatusPedidoExame) {
        this._statusPedidoExame = value;
    }

    /**
     * Getter justificativa
     * @return {string}
     */
	public get justificativa(): string {
		return this._justificativa;
	}

    /**
     * Setter justificativa
     * @param {string} value
     */
	public set justificativa(value: string) {
		this._justificativa = value;
    }
    
    /**
     * Getter locusSolicitados
     * @return {Locus[]}
     */
	public get locusSolicitados(): Locus[] {
		return this._locusSolicitados;
	}

    /**
     * Setter locusSolicitados
     * @param {Locus[]} value
     */
	public set locusSolicitados(value: Locus[]) {
		this._locusSolicitados = value;
	}


    public jsonToEntity(res:any): PedidoExame {

        if (res.solicitacao) {
            this.solicitacao = new Solicitacao().jsonToEntity(res.solicitacao);
        }

        if (res.laboratorio) {
            this.laboratorio = new Laboratorio().jsonToEntity(res.laboratorio);
        }

        if(res.exame && res.exame['@type']){
            if (res.exame['@type'] == "examePaciente") {
                this.exame = new ExamePaciente().jsonToEntity(res.exame);
            }
            else if (res.exame['@type'] == "exameDoadorNacional") {
                this.exame = new ExameDoadorNacional().jsonToEntity(res.exame);
            }
            else if (res.exame['@type'] == "exameDoadorInternacional") {
                this.exame = new ExameDoadorInternacional().jsonToEntity(res.exame);
            }
            else {
                new Error("Method not implemented.");
            }
        }

        if (res.tipoExame) {
            this.tipoExame = new TipoExame().jsonToEntity(res.tipoExame);
        }

        if (res.statusPedidoExame) {
            this.statusPedidoExame = new StatusPedidoExame(null).jsonToEntity(res.statusPedidoExame);
        }

        if (res.locusSolicitados) {
            this.locusSolicitados = [];
            res.locusSolicitados.forEach(locus => {
                this.locusSolicitados.push(new Locus().jsonToEntity(locus));
            });
        }

        this.id  = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
        this.dataCriacao = ConvertUtil.parseJsonParaAtributos(res.dataCriacao, new Date());
        this.dataColetaAmostra = ConvertUtil.parseJsonParaAtributos(res.dataColetaAmostra, new Date());
        this.dataRecebimentoAmostra = ConvertUtil.parseJsonParaAtributos(res.dataRecebimentoAmostra, new Date());
        this.justificativa = ConvertUtil.parseJsonParaAtributos(res.justificativa, new String());

		return this;
	}
}