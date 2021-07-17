import { TarefaBase } from '../../../shared/dominio/tarefa.base';
import { AvaliacaoResultadoWorkup } from '../../cadastro/resultadoworkup/resultado/avaliacao.resultado.workup';

export class TarefaAvaliacaoResultadoWorkup extends TarefaBase {

	private _avaliacaoResultadoWorkup: AvaliacaoResultadoWorkup;


	public get avaliacaoResultadoWorkup(): AvaliacaoResultadoWorkup {
		return this._avaliacaoResultadoWorkup;
	}

	public set avaliacaoResultadoWorkup(value: AvaliacaoResultadoWorkup) {
		this._avaliacaoResultadoWorkup = value;
	}


}
