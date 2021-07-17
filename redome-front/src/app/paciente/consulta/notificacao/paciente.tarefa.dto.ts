import { BaseEntidade } from '../../../shared/base.entidade';

export class PacienteTarefaDto extends BaseEntidade {
    private _rmr: number;
    private _nome: string;
    private _totalTarefas: number;
    private _agingAvaliacao: string;
    private _agingTarefa: string;
    private _dataCriacaoPendencia: Date;
    private _dataCriacaoAvaliacao: Date;
    private _descricao:String;
    private _idTarefa:number;
    private _statusTarefa:string;
    
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
    public get totalTarefas(): number {
        return this._totalTarefas;
    }
    public set totalTarefas(value: number) {
        this._totalTarefas = value;
    }
    public get dataCriacaoPendencia(): Date {
        return this._dataCriacaoPendencia;
    }
    public set dataCriacaoPendencia(value: Date) {
        this._dataCriacaoPendencia = value;
    }
    public get dataCriacaoAvaliacao(): Date {
        return this._dataCriacaoAvaliacao;
    }
    public set dataCriacaoAvaliacao(value: Date) {
        this._dataCriacaoAvaliacao = value;
    }
    public get agingAvaliacao(): string {
        return this._agingAvaliacao;
    }
    public set agingAvaliacao(value: string) {
        this._agingAvaliacao = value;
    }
    public get agingTarefa(): string {
        return this._agingTarefa;
    }
    public set agingTarefa(value: string) {
        this._agingTarefa = value;
    }

	/**
     * 
     * 
     * @type {String}
     * @memberof PacienteTarefaDto
     */
    public get descricao(): String {
		return this._descricao;
	}

	/**
     * 
     * 
     * @memberof PacienteTarefaDto
     */
    public set descricao(value: String) {
		this._descricao = value;
	}


	/**
     * Recupera o Id da tarefa.
     * 
     * @type {number}
     * @memberof PacienteTarefaDto
     */
    public get idTarefa(): number {
		return this._idTarefa;
	}

	/**
     * Seta o id da tarefa.
     * 
     * @memberof PacienteTarefaDto
     */
    public set idTarefa(value: number) {
		this._idTarefa = value;
	}


	/**
     * Retorna o status da tarefa em string
     * 
     * @type {string}
     * @memberof PacienteTarefaDto
     */
    public get statusTarefa(): string {
		return this._statusTarefa;
	}

	/**
     * Seta o status da tarefa em string
     * 
     * @memberof PacienteTarefaDto
     */
    public set statusTarefa(value: string) {
		this._statusTarefa = value;
	}
 
    public jsonToEntity(res: any) {
        throw new Error("Method not implemented.");
    }
    
}