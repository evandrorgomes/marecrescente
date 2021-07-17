import { ConvertUtil } from 'app/shared/util/convert.util';
import { BaseEntidade } from '../base.entidade';
import { Pergunta } from './pergunta';

export class Secao extends BaseEntidade {

	private _titulo: string;
	private _perguntas: Pergunta[];


    /**
     * Getter titulo
     * @return {string}
     */
	public get titulo(): string {
		return this._titulo;
	}

    /**
     * Setter titulo
     * @param {string} value
     */
	public set titulo(value: string) {
		this._titulo = value;
	}


    /**
     * Getter perguntas
     * @return {Pergunta[]}
     */
	public get perguntas(): Pergunta[] {
		return this._perguntas;
	}

    /**
     * Setter perguntas
     * @param {Pergunta[]} value
     */
	public set perguntas(value: Pergunta[]) {
		this._perguntas = value;
	}


	public jsonToEntity(res:any): Secao {

		if (res.perguntas) {
			this.perguntas = [];
			res.perguntas.forEach(pergunta => {
				this.perguntas.push(new Pergunta().jsonToEntity(pergunta));
			});
		}

		this.titulo = ConvertUtil.parseJsonParaAtributos(res.titulo, new String());
		
		return this;
	}
    
}