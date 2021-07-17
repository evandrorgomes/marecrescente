import {BaseEntidade} from '../../shared/base.entidade';
import { ConvertUtil } from 'app/shared/util/convert.util';

/**
 * Classe Bean utilizada para definir os campos de evolução do Paciente
 *
 * @author Fillipe Queiroz 
 * @export
 * @class StatusBusca
 */
export class StatusBusca extends BaseEntidade {

  private _id: number;
  private _descricao: string;
  private _buscaAtiva: number;

  /**
   * id do status
   */
	public get id(): number {
		return this._id;
	}

  /**
   * Id do status
   */
	public set id(value: number) {
		this._id = value;
	}

  /**
   * Descricao do status
   */
	public get descricao(): string {
		return this._descricao;
	}

  /**
   * Descricao do status
   */
	public set descricao(value: string) {
		this._descricao = value;
	}


  /**
   * Retorna se busca está em atividade
   * @return id se busca esta ativa ou inativa
   */
	public get buscaAtiva(): number {
		return this._buscaAtiva;
	}

  /**
   * Informa se busca está em atividade
   */
	public set buscaAtiva(value: number) {
		this._buscaAtiva = value;
  }
  
  public jsonToEntity(res: any): StatusBusca {

    this.id = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
    this.descricao = ConvertUtil.parseJsonParaAtributos(res.descricao, new String());
    this.buscaAtiva = ConvertUtil.parseJsonParaAtributos(res.buscaAtiva, new Number());

    return this;
  }

}