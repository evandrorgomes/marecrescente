import {BaseEntidade} from '../../shared/base.entidade';
import { StatusBusca } from './status.busca';

/**
 * Classe Bean utilizada para definir os campos de Motivo de Cancelamento de Busca do Paciente
 *
 * @author Fillipe Queiroz
 * @export
 * @class MotivoCancelamentoBusca
 */
export class MotivoCancelamentoBusca extends BaseEntidade {

  private _id: number;
  private _descricao: string;
  private _descricaoObrigatorio:number;

  /**
   * Identificador de motivo de cancelamento
   * @return id
   */
	public get id(): number {
		return this._id;
	}

  /**
   * Identificador de motivo de cancelamento
   */
	public set id(value: number) {
		this._id = value;
	}

  /**
   * Descricao do motivo de cancelamento
   * @return descricao
   */
	public get descricao(): string {
		return this._descricao;
	}

  /**
   * Descricao do motivo de cancelamento
   */
	public set descricao(value: string) {
		this._descricao = value;
	}

  /**
   * Diz se o campo nome deve ser validado como obrigatório.
   */
	public get descricaoObrigatorio(): number {
		return this._descricaoObrigatorio;
	}

  /**
   * Diz se o campo nome deve ser validado como obrigatório.
   */
	public set descricaoObrigatorio(value: number) {
		this._descricaoObrigatorio = value;
	}

  public jsonToEntity(res:any):this{

		return this;
	}

}
