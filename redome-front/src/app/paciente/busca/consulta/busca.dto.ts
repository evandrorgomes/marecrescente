import { BaseEntidade } from '../../../shared/base.entidade';


/**
 * Classe que carrega os dados para tela onde são listados os pacientes
 * com busca ativa.
 * 
 * @export
 * @class BuscaDTO
 * @extends {BaseEntidade}
 * @author Filipe Paes
 */
export class BuscaDTO extends BaseEntidade {
    private _idBusca: number;
    private _rmr: number;
    private _nome: string;
    private _idade: number;
    private _score: number;
    private _dataAvaliacao: Date;
    private _nomeCentro: string;
    private _nomeCid: string;
    private _codigoCid: string;
    private _totalRegistros: number;
    private _prazoExpirar: Date;
    private _prazoExpirarDiasUteis: number;
    private _dataUltimaAnalise: Date;
    private _nomeCentroAvaliador: string;
    private _dataUltimaEvolucao: Date;

    public get idBusca(): number {
        return this._idBusca;
    }

    public set idBusca(value: number) {
        this._idBusca = value;
    }

    public get rmr(): number {
        return this._rmr;
    }

    public set rmr(value: number) {
        this._rmr = value;
    }

    public get nome(): string {
        return this._nome;
    }

    public set nome(value: string) {
        this._nome = value;
    }

    public get idade(): number {
        return this._idade;
    }

    public set idade(value: number) {
        this._idade = value;
    }

    public get score(): number {
        return this._score;
    }

    public set score(value: number) {
        this._score = value;
    }

    public get dataAvaliacao(): Date {
        return this._dataAvaliacao;
    }

    public set dataAvaliacao(value: Date) {
        this._dataAvaliacao = value;
    }

    public get nomeCentro(): string {
        return this._nomeCentro;
    }

    public set nomeCentro(value: string) {
        this._nomeCentro = value;
    }

    public get nomeCid(): string {
        return this._nomeCid;
    }

    public set nomeCid(value: string) {
        this._nomeCid = value;
    }

    public get codigoCid(): string {
        return this._codigoCid;
    }

    public set codigoCid(value: string) {
        this._codigoCid = value;
    }

    public jsonToEntity(res: any) {
		throw new Error("Method not implemented.");
    }

    public get totalRegistros(): number {
		return this._totalRegistros;
	}

    public set totalRegistros(value: number) {
		this._totalRegistros = value;
	}

	public get prazoExpirar(): Date {
		return this._prazoExpirar;
	}

	public set prazoExpirar(value: Date) {
		this._prazoExpirar = value;
    }
    
	public get prazoExpirarDiasUteis(): number {
		return this._prazoExpirarDiasUteis;
	}

	public set prazoExpirarDiasUteis(value: number) {
		this._prazoExpirarDiasUteis = value;
	}

    public get nomeCentroAvaliador(): string {
		return this._nomeCentroAvaliador;
	}

	public set nomeCentroAvaliador(value: string) {
		this._nomeCentroAvaliador = value;
	}

    /**
     * Getter dataUltimaAnalise
     * @return {Date}
     */
	public get dataUltimaAnalise(): Date {
		return this._dataUltimaAnalise;
	}

    /**
     * Setter dataUltimaAnalise
     * @param {Date} value
     */
	public set dataUltimaAnalise(value: Date) {
		this._dataUltimaAnalise = value;
	}

    /**
     * Getter dataUltimaEvolucao
     * @return {Date}
     */
	public get dataUltimaEvolucao(): Date {
		return this._dataUltimaEvolucao;
	}

    /**
     * Setter dataUltimaEvolucao
     * @param {Date} value
     */
	public set dataUltimaEvolucao(value: Date) {
		this._dataUltimaEvolucao = value;
	}
}