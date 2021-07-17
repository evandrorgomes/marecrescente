import {BaseEntidade} from "../base.entidade";

export class CriarPrescricaoCordaoDTO extends BaseEntidade {

   private _rmr: number;
   private _idMatch: number;
   private _idTipoDoador: number;
   private _dataInfusao: Date;
   private _dataReceber1: Date;
   private _dataReceber2: Date;
   private _dataReceber3: Date;
   private _armazenaCordao: boolean;
   private _nomeContatoParaReceber: string;
   private _nomeContatoUrgente: string;
   private _codigoAreaUrgente: string;
   private _telefoneUrgente: number;

   get rmr(): number {
      return this._rmr;
   }

   set rmr(value: number) {
      this._rmr = value;
   }

   get idMatch(): number {
      return this._idMatch;
   }

   set idMatch(value: number) {
      this._idMatch = value;
   }

   get idTipoDoador(): number {
      return this._idTipoDoador;
   }

   set idTipoDoador(value: number) {
      this._idTipoDoador = value;
   }

   get dataInfusao(): Date {
      return this._dataInfusao;
   }

   set dataInfusao(value: Date) {
      this._dataInfusao = value;
   }

   get dataReceber1(): Date {
      return this._dataReceber1;
   }

   set dataReceber1(value: Date) {
      this._dataReceber1 = value;
   }

   get dataReceber2(): Date {
      return this._dataReceber2;
   }

   set dataReceber2(value: Date) {
      this._dataReceber2 = value;
   }

   get dataReceber3(): Date {
      return this._dataReceber3;
   }

   set dataReceber3(value: Date) {
      this._dataReceber3 = value;
   }

   get armazenaCordao(): boolean {
      return this._armazenaCordao;
   }

   set armazenaCordao(value: boolean) {
      this._armazenaCordao = value;
   }

   get nomeContatoParaReceber(): string {
      return this._nomeContatoParaReceber;
   }

   set nomeContatoParaReceber(value: string) {
      this._nomeContatoParaReceber = value;
   }

   get nomeContatoUrgente(): string {
      return this._nomeContatoUrgente;
   }

   set nomeContatoUrgente(value: string) {
      this._nomeContatoUrgente = value;
   }

   get codigoAreaUrgente(): string {
      return this._codigoAreaUrgente;
   }

   set codigoAreaUrgente(value: string) {
      this._codigoAreaUrgente = value;
   }

   get telefoneUrgente(): number {
      return this._telefoneUrgente;
   }

   set telefoneUrgente(value: number) {
      this._telefoneUrgente = value;
   }

   jsonToEntity(res: any): any {
      return this;
   }

}
