import { BaseEntidade } from "app/shared/base.entidade";
import { Paciente } from "../../paciente/paciente";
import { StatusAvaliacaoNovaBusca } from "../enums/status.avaliacao.nova.busca";
import { UsuarioLogado } from "../dominio/usuario.logado";

/**
 * Avaliação da solicitação para início de novo processo de busca
 * para o paciente que já possuia cadastro no Redome.
 * 
 * @author Pizão
 */
export class AvaliacaoNovaBusca extends BaseEntidade {
    
    private _id: number;
    private _paciente: Paciente;
	private _dataCriacao: Date;
    private _status: StatusAvaliacaoNovaBusca;
    private _avaliador: UsuarioLogado;
    private _justificativa: string;


	public get id(): number {
		return this._id;
	}

	public set id(value: number) {
		this._id = value;
    }
    
	public get paciente(): Paciente {
		return this._paciente;
    }
    
	public set paciente(value: Paciente) {
		this._paciente = value;
	}

	public get dataCriacao(): Date {
		return this._dataCriacao;
	}

	public set dataCriacao(value: Date) {
		this._dataCriacao = value;
	}

	public get status(): StatusAvaliacaoNovaBusca {
		return this._status;
	}

	public set status(value: StatusAvaliacaoNovaBusca) {
		this._status = value;
	}

	public get avaliador(): UsuarioLogado {
		return this._avaliador;
	}

	public set avaliador(value: UsuarioLogado) {
		this._avaliador = value;
	}

	public get justificativa(): string {
		return this._justificativa;
	}

	public set justificativa(value: string) {
		this._justificativa = value;
	}
    

    public jsonToEntity(res: any) {
        throw new Error("Method not implemented.");
    }

}