import { UsuarioLogado } from '../../../../shared/dominio/usuario.logado';
import { BaseEntidade } from '../../../../shared/base.entidade';
import { ResultadoWorkup } from './resultado.workup';
import { ConvertUtil } from 'app/shared/util/convert.util';


/**
 * Classe para armazenamento de resultados de Avaliação de resultado de workup.
 * @author Fillipe QUeiroz
 */
export class AvaliacaoResultadoWorkup extends BaseEntidade {
    
    private _id: number;
    private _resultadoWorkup: ResultadoWorkup;
    private _dataAtualizacao: Date;
    private _dataCriacao: Date;
    private _usuarioResponsavel: UsuarioLogado;
    private _solicitaColeta: boolean;
    private _justificativa: string;


	public get id(): number {
		return this._id;
	}

	public set id(value: number) {
		this._id = value;
	}

    public get resultadoWorkup(): ResultadoWorkup {
        return this._resultadoWorkup;
    }

    public set resultadoWorkup(value: ResultadoWorkup) {
        this._resultadoWorkup = value;
    }

    public get dataAtualizacao(): Date {
        return this._dataAtualizacao;
    }

    public set dataAtualizacao(value: Date) {
        this._dataAtualizacao = value;
    }

    public get dataCriacao(): Date {
        return this._dataCriacao;
    }

    public set dataCriacao(value: Date) {
        this._dataCriacao = value;
    }

    public get usuarioResponsavel(): UsuarioLogado {
        return this._usuarioResponsavel;
    }

    public set usuarioResponsavel(value: UsuarioLogado) {
        this._usuarioResponsavel = value;
    }

    public get solicitaColeta(): boolean {
        return this._solicitaColeta;
    }

    public set solicitaColeta(value: boolean) {
        this._solicitaColeta = value;
    }

    public get justificativa(): string {
        return this._justificativa;
    }

    public set justificativa(value: string) {
        this._justificativa = value;
    }

    public jsonToEntity(res: any): AvaliacaoResultadoWorkup {
        
        if (res.resultadoWorkup) {
            this.resultadoWorkup = new ResultadoWorkup().jsonToEntity(res.resultadoWorkup);
        }

        if (res.usuarioResponsavel) {
            this.usuarioResponsavel = new UsuarioLogado().jsonToEntity(res.usuarioResponsavel);
        }

        this.id                 = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
        this.dataAtualizacao    = ConvertUtil.parseJsonParaAtributos(res.dataAtualizacao, new Date());
        this.dataCriacao        = ConvertUtil.parseJsonParaAtributos(res.dataCriacao, new Date());
        this.solicitaColeta     = ConvertUtil.parseJsonParaAtributos(res.solicitaColeta, new Boolean());
        this.justificativa      = ConvertUtil.parseJsonParaAtributos(res.justificativa, new String());

        return this;
    }

}