import {BaseEntidade} from "../base.entidade";
import {ConvertUtil} from "../util/convert.util";
import {TiposDoador} from "../enums/tipos.doador";

export class AvaliacoesPrescricaoDto extends BaseEntidade {

   private _idTarefa: number;
   private _idStatusTarefa: number;
   private _idAvaliacaoPrescricao: number;
   private _rmr: number;
   private _nome: string;
   private _identificacaoDoador: string;
   private _tipoDoador: TiposDoador;
   private _dataColeta: Date;

   get idTarefa(): number {
      return this._idTarefa;
   }

   get idStatusTarefa(): number {
      return this._idStatusTarefa;
   }

   get idAvaliacaoPrescricao(): number {
      return this._idAvaliacaoPrescricao;
   }

   get rmr(): number {
      return this._rmr;
   }

   get nome(): string {
      return this._nome;
   }

   get identificacaoDoador(): string {
      return this._identificacaoDoador;
   }

   get tipoDoador(): TiposDoador {
      return this._tipoDoador;
   }

   get dataColeta(): Date {
      return this._dataColeta;
   }

   jsonToEntity(res: any): AvaliacoesPrescricaoDto {

      this._idTarefa                = ConvertUtil.parseJsonParaAtributos(res.idTarefa, new Number());
      this._idStatusTarefa          = ConvertUtil.parseJsonParaAtributos(res.idStatusTarefa, new Number());
      this._idAvaliacaoPrescricao   = ConvertUtil.parseJsonParaAtributos(res.idAvaliacaoPrescricao, new Number());
      this._rmr                     = ConvertUtil.parseJsonParaAtributos(res.rmr, new Number());
      this._nome                    = ConvertUtil.parseJsonParaAtributos(res.nome, new String());
      this._identificacaoDoador     = ConvertUtil.parseJsonParaAtributos(res.identificacaoDoador, new String());;
      this._tipoDoador              = TiposDoador.valueOf(res.tipoDoador);
      this._dataColeta              = ConvertUtil.parseJsonParaAtributos(res.dataColeta, new Date());

      return this;
   }
}
