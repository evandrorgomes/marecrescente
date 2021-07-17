import { Paciente } from '../paciente';
import { BaseEntidade } from '../../shared/base.entidade';
import { StatusBusca } from './status.busca';
import { UsuarioLogado } from '../../shared/dominio/usuario.logado';
import { CentroTransplante } from '../../shared/dominio/centro.transplante';
import { ConvertUtil } from 'app/shared/util/convert.util';

/**
 * Classe Bean utilizada para definir os campos de busca do Paciente
 *
 * @author Fillipe Queiroz
 * @export
 * @class Busca
 */
export class Busca extends BaseEntidade {

  private _id: number;
  private _status: StatusBusca;
  private _usuario: UsuarioLogado;
  private _paciente: Paciente;
  private _centroTransplante: CentroTransplante;

  /**
   * Id da busca
   */
  public get id(): number {
    return this._id;
  }

  /**
   * Id da busca
   */
  public set id(value: number) {
    this._id = value;
  }

  /**
   * Status da busca
   */
  public get status(): StatusBusca {
    return this._status;
  }

  /**
   * Status da busca
   */
  public set status(value: StatusBusca) {
    this._status = value;
  }
  /**
   * Usuario responsavel pela busca
   */
  public get usuario(): UsuarioLogado {
    return this._usuario;
  }
  /**
   * Usuario responsavel pela busca
   */ 
  public set usuario(value: UsuarioLogado) {
    this._usuario = value;
  }

  /**
   * Paciente a ser acompanhado
   */
  public get paciente(): Paciente {
    return this._paciente;
  }
  /**
   * Paciente a ser acompanhado
   */
  public set paciente(value: Paciente) {
    this._paciente = value;
  }


  public jsonToEntity(res: any): Busca {

    if (res.status) {
      this.status = new StatusBusca().jsonToEntity(res.status);
    }

    if (res.usuario) {
      this.usuario = new UsuarioLogado().jsonToEntity(res.usuario);
    }

    if (res.paciente) {
      this.paciente = new Paciente().jsonToEntity(res.paciente);
    }

    if (res.centroTransplante) {
      this.centroTransplante = new CentroTransplante().jsonToEntity(res.centroTransplante);
    }

    this.id = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
    
    return this
  }

  public get centroTransplante(): CentroTransplante {
		return this._centroTransplante;
  }
  
	public set centroTransplante(value: CentroTransplante) {
		this._centroTransplante = value;
	}

}