import { AvaliacaoCamaraTecnica } from './../avaliacao.camara.tecnica';
import { BaseEntidade } from "../../../shared/base.entidade";
import { Evolucao } from '../../cadastro/evolucao/evolucao';

export class AvaliacaoCamaraTecnicaDTO extends BaseEntidade {

    private _avaliacaoCamaraTecnica: AvaliacaoCamaraTecnica;
    private _ultimaEvolucao: Evolucao;


    /**
     * Getter avaliacaoCamaraTecnica
     * @return {AvaliacaoCamaraTecnica}
     */
	public get avaliacaoCamaraTecnica(): AvaliacaoCamaraTecnica {
		return this._avaliacaoCamaraTecnica;
	}

    /**
     * Setter avaliacaoCamaraTecnica
     * @param {AvaliacaoCamaraTecnica} value
     */
	public set avaliacaoCamaraTecnica(value: AvaliacaoCamaraTecnica) {
		this._avaliacaoCamaraTecnica = value;
	}


    /**
     * Getter ultimaEvolucao
     * @return {Evolucao}
     */
	public get ultimaEvolucao(): Evolucao {
		return this._ultimaEvolucao;
	}

    /**
     * Setter ultimaEvolucao
     * @param {Evolucao} value
     */
	public set ultimaEvolucao(value: Evolucao) {
		this._ultimaEvolucao = value;
	}


    public jsonToEntity(res: any) :this{
        this._avaliacaoCamaraTecnica = new AvaliacaoCamaraTecnica().jsonToEntity(res.avaliacaoCamaraTecnica);
        this._ultimaEvolucao = new Evolucao().jsonToEntity(res.ultimaEvolucao);
        return this;
    }
}