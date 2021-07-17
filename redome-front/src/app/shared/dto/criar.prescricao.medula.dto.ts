import {TipoAmostraPrescricao} from "../classes/tipo.amostra.prescricao";
import {BaseEntidade} from "../base.entidade";

export class CriarPrescricaoMedulaDTO extends BaseEntidade {

   private _rmr: number;
   private _idMatch: number;
   private _idTipoDoador: number;
   private _dataColeta1: Date;
   private _dataColeta2: Date;
   private _dataLimiteWorkup1: Date;
   private _dataLimiteWorkup2: Date;
   private _fonteCelulaOpcao1: number;
   private _quantidadeTotalOpcao1: number;
   private _quantidadePorKgOpcao1: number;
   private _fonteCelulaOpcao2: number;
   private _quantidadeTotalOpcao2: number;
   private _quantidadePorKgOpcao2: number;
   private _tiposAmostraPrescricao: TipoAmostraPrescricao[] = [];
   private _fazerColeta: boolean;

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

   get dataColeta1(): Date {
      return this._dataColeta1;
   }

   set dataColeta1(value: Date) {
      this._dataColeta1 = value;
   }

   get dataColeta2(): Date {
      return this._dataColeta2;
   }

   set dataColeta2(value: Date) {
      this._dataColeta2 = value;
   }

   get dataLimiteWorkup1(): Date {
      return this._dataLimiteWorkup1;
   }

   set dataLimiteWorkup1(value: Date) {
      this._dataLimiteWorkup1 = value;
   }

   get dataLimiteWorkup2(): Date {
      return this._dataLimiteWorkup2;
   }

   set dataLimiteWorkup2(value: Date) {
      this._dataLimiteWorkup2 = value;
   }

   get fonteCelulaOpcao1(): number {
      return this._fonteCelulaOpcao1;
   }

   set fonteCelulaOpcao1(value: number) {
      this._fonteCelulaOpcao1 = value;
   }

   get quantidadeTotalOpcao1(): number {
      return this._quantidadeTotalOpcao1;
   }

   set quantidadeTotalOpcao1(value: number) {
      this._quantidadeTotalOpcao1 = value;
   }

   get quantidadePorKgOpcao1(): number {
      return this._quantidadePorKgOpcao1;
   }

   set quantidadePorKgOpcao1(value: number) {
      this._quantidadePorKgOpcao1 = value;
   }

   get fonteCelulaOpcao2(): number {
      return this._fonteCelulaOpcao2;
   }

   set fonteCelulaOpcao2(value: number) {
      this._fonteCelulaOpcao2 = value;
   }

   get quantidadeTotalOpcao2(): number {
      return this._quantidadeTotalOpcao2;
   }

   set quantidadeTotalOpcao2(value: number) {
      this._quantidadeTotalOpcao2 = value;
   }

   get quantidadePorKgOpcao2(): number {
      return this._quantidadePorKgOpcao2;
   }

   set quantidadePorKgOpcao2(value: number) {
      this._quantidadePorKgOpcao2 = value;
   }

   get tiposAmostraPrescricao(): TipoAmostraPrescricao[] {
      return this._tiposAmostraPrescricao;
   }

   set tiposAmostraPrescricao(value: TipoAmostraPrescricao[]) {
      this._tiposAmostraPrescricao = value;
   }

   get fazerColeta(): boolean {
      return this._fazerColeta;
   }

   set fazerColeta(value: boolean) {
      this._fazerColeta = value;
   }

   jsonToEntity(res: any): any {
      return this;
   }

}
