
export class DistribuicoesWorkupDTO {

   private _idTarefaDistribuicaoWorkup: number;

   private _statusTarefaDistribuicaoWorkup: number;

   private _idDistribuicaoWorkup: number;

   private _rmr: number;

   private _nomeCentroTransplante: string;

   private _medicoResponsavelPrescricao: string;

   private _tipoPrescricao: string;

   private _identificacaoDoador: string;

   private _dataPrescricao: string;

   private _dataAvaliacaoPrescricao: string;


   get idTarefaDistribuicaoWorkup(): number {
      return this._idTarefaDistribuicaoWorkup;
   }

   set idTarefaDistribuicaoWorkup(value: number) {
      this._idTarefaDistribuicaoWorkup = value;
   }

   get statusTarefaDistribuicaoWorkup(): number {
      return this._statusTarefaDistribuicaoWorkup;
   }

   set statusTarefaDistribuicaoWorkup(value: number) {
      this._statusTarefaDistribuicaoWorkup = value;
   }

   get idDistribuicaoWorkup(): number {
      return this._idDistribuicaoWorkup;
   }

   set idDistribuicaoWorkup(value: number) {
      this._idDistribuicaoWorkup = value;
   }

   get rmr(): number {
      return this._rmr;
   }

   set rmr(value: number) {
      this._rmr = value;
   }

   get nomeCentroTransplante(): string {
      return this._nomeCentroTransplante;
   }

   set nomeCentroTransplante(value: string) {
      this._nomeCentroTransplante = value;
   }

   get medicoResponsavelPrescricao(): string {
      return this._medicoResponsavelPrescricao;
   }

   set medicoResponsavelPrescricao(value: string) {
      this._medicoResponsavelPrescricao = value;
   }

   get tipoPrescricao(): string {
      return this._tipoPrescricao;
   }

   set tipoPrescricao(value: string) {
      this._tipoPrescricao = value;
   }

   get identificacaoDoador(): string {
      return this._identificacaoDoador;
   }

   set identificacaoDoador(value: string) {
      this._identificacaoDoador = value;
   }

   get dataPrescricao(): string {
      return this._dataPrescricao;
   }

   set dataPrescricao(value: string) {
      this._dataPrescricao = value;
   }

   get dataAvaliacaoPrescricao(): string {
      return this._dataAvaliacaoPrescricao;
   }

   set dataAvaliacaoPrescricao(value: string) {
      this._dataAvaliacaoPrescricao = value;
   }
}