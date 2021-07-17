import { BaseEntidade } from '../base.entidade';
import { MatchDTO } from '../component/listamatch/match.dto';
import { Match } from '../../paciente/busca/match';

export class ComentarioMatch extends BaseEntidade {

    private _id: number;
    private _comentario: string;
    private _match: Match;


	public get id(): number {
		return this._id;
	}

	public set id(value: number) {
		this._id = value;
	}

	public get comentario(): string {
		return this._comentario;
	}

	public set comentario(value: string) {
		this._comentario = value;
	}

	public get match(): Match {
		return this._match;
	}

	public set match(value: Match) {
		this._match = value;
	}

	public jsonToEntity(res: any): any {
        throw new Error("Method not implemented.");
    }
}