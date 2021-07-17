import { MotivoCancelamentoBusca } from './motivo.cancelamento.busca';
import { BaseEntidade } from '../../shared/base.entidade';
import { StatusBusca } from './status.busca';

/**
 * Classe Bean utilizada para definir os campos de busca do Paciente
 *
 * @author Fillipe Queiroz
 * @export
 * @class CancelamentoBusca
 */
export class CancelamentoBusca extends BaseEntidade {

  private _id: number;
  private _dataEvento: Date;
  private _especifique: string;
  private _motivoCancelamentoBusca: MotivoCancelamentoBusca;

  /**
   * Identificador de cancelamento de busca
   * @return id
   */
  public get id(): number {
    return this._id;
  }

  /**
   * Identificador de cancelamento de busca
   */
  public set id(value: number) {
    this._id = value;
  }

  /**
   * Data de evento de cancelamento de busca
   * @return dataEvento
   */
  public get dataEvento(): Date {
    return this._dataEvento;
  }

  /**
   * Data de evento de cancelamento de busca
   */
  public set dataEvento(value: Date) {
    this._dataEvento = value;
  }

  /**
   * Especifique de cancelamento de busca
   * @return observacao
   */
  public get especifique(): string {
    return this._especifique;
  }

  /**
   * Especifique de cancelamento de busca
   */
  public set especifique(value: string) {
    this._especifique = value;
  }

  /**
   * motivo de cancelamento de busca
   * @return motivo de cancelamento de busca
   */
  public get motivoCancelamentoBusca(): MotivoCancelamentoBusca {
    return this._motivoCancelamentoBusca;
  }

  /**
   * motivo de cancelamento de busca
   */
  public set motivoCancelamentoBusca(value: MotivoCancelamentoBusca) {
    this._motivoCancelamentoBusca = value;
  }

  public jsonToEntity(res:any):this{
		
		return this;
	}
}