import { ConvertUtil } from 'app/shared/util/convert.util';
import { BaseEntidade } from '../../../../shared/base.entidade';
import { ResultadoWorkup } from "./resultado.workup";

/**
 * Classe de referencia aos arquivos vinculados ao laudo de resultado
 * de workup.
 * @author Filipe Paes
 */
export class ArquivoResultadoWorkup extends BaseEntidade {
    private _id: Number;
    private _excluido: boolean;
    private _caminho: String;
    private _descricao: String;
    private _resultadoWorkup: ResultadoWorkup;

    public get id(): Number {
        return this._id;
    }

    public set id(value: Number) {
        this._id = value;
    }

    public get excluido(): boolean {
        return this._excluido;
    }

    public set excluido(value: boolean) {
        this._excluido = value;
    }

    public get caminho(): String {
        return this._caminho;
    }

    public set caminho(value: String) {
        this._caminho = value;
    }

    public get descricao(): String {
        return this._descricao;
    }

    public set descricao(value: String) {
        this._descricao = value;
    }

    public get resultadoWorkup(): ResultadoWorkup {
        return this._resultadoWorkup;
    }

    public set resultadoWorkup(value: ResultadoWorkup) {
        this._resultadoWorkup = value;
    }

    public jsonToEntity(res:any): ArquivoResultadoWorkup{

      if(res.resultadoWorkup){
          this._resultadoWorkup = new ResultadoWorkup().jsonToEntity(res.resultadoWorkup);
      }

      this._id = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
      this._excluido = ConvertUtil.parseJsonParaAtributos(res.excluido, new Boolean());
      this._caminho = ConvertUtil.parseJsonParaAtributos(res.caminho, new String());
      this._descricao = ConvertUtil.parseJsonParaAtributos(res.descricao, new String());

		return this;
	}
}
