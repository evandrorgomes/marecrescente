import {UF} from './uf';
import {ConvertUtil} from 'app/shared/util/convert.util';
import {EnderecoContato} from '../classes/endereco.contato';
import {constructor} from 'events';
import {BaseEntidade} from '../base.entidade';
import {InstrucaoColeta} from './instrucao.coleta';
import {EmailContatoLaboratorio} from '../classes/email.contato.laboratorio';
import {Usuario} from './usuario';
import {Pais} from './pais';

export class Laboratorio extends BaseEntidade {

   private _id: number;
   private _nome: string;
   private _quantidadeExamesCT: number;
   private _instrucoesColeta: InstrucaoColeta[];
   private _endereco: EnderecoContato;
   private _quantidadeAtual: number;
   private _emails: EmailContatoLaboratorio[];
   private _usuarios: Usuario[] = [];

   constructor(id?: number, nome?: string) {
      super();
      this._id = id;
      this._nome = nome;
   }

   /**
    * Getter id
    * @return {number}
    */
   public get id(): number {
      return this._id;
   }

   /**
    * Setter id
    * @param {number} value
    */
   public set id(value: number) {
      this._id = value;
   }

   /**
    * Getter nome
    * @return {string}
    */
   public get nome(): string {
      return this._nome;
   }

   /**
    * Setter nome
    * @param {string} value
    */
   public set nome(value: string) {
      this._nome = value;
   }

   /**
    * Getter quantidadeExamesCT
    * @return {number}
    */
   public get quantidadeExamesCT(): number {
      return this._quantidadeExamesCT;
   }

   /**
    * Setter quantidadeExamesCT
    * @param {number} value
    */
   public set quantidadeExamesCT(value: number) {
      this._quantidadeExamesCT = value;
   }

   /**
    * Getter instrucoesColeta
    * @return {InstrucaoColeta[]}
    */
   public get instrucoesColeta(): InstrucaoColeta[] {
      return this._instrucoesColeta;
   }

   /**
    * Setter instrucoesColeta
    * @param {InstrucaoColeta[]} value
    */
   public set instrucoesColeta(value: InstrucaoColeta[]) {
      this._instrucoesColeta = value;
   }

   public get endereco(): EnderecoContato {
      return this._endereco;
   }

   public set endereco(value: EnderecoContato) {
      this._endereco = value;
   }

   /**
    * Getter quantidadeAtual
    * @return {number}
    */
   public get quantidadeAtual(): number {
      return this._quantidadeAtual;
   }

   /**
    * Setter quantidadeAtual
    * @param {number} value
    */
   public set quantidadeAtual(value: number) {
      this._quantidadeAtual = value;
   }

   public get descricaoUfNome(): string {
      return this.endereco && this.endereco.municipio && this.endereco.municipio.uf ? (this.endereco.municipio.uf.sigla + ' | ' + this.nome) : this.nome;
   }

   public get nomeFormatadoComMunicipioComCapacidadeESaldoAtual(): string {
      if(this.endereco && this.endereco.municipio && this.endereco.municipio.uf) {
         return this.endereco.municipio.descricao + " - " + this.endereco.municipio.uf.sigla + " - " + this.obterNomeFormatadoComCapacidadeESaldoAtual();
      }
      return this.obterNomeFormatadoComCapacidadeESaldoAtual();
   }

   private obterNomeFormatadoComCapacidadeESaldoAtual(): string {
      return this.nome + ' ' +
         (this.quantidadeAtual ? this.quantidadeAtual: '0')  + '/' +  (this.quantidadeExamesCT ? this.quantidadeExamesCT : '0');
   }


   /**
    * Getter emails
    * @return {EmailContatoLaboratorio[]}
    */
   public get emails(): EmailContatoLaboratorio[] {
      return this._emails;
   }

   /**
    * Setter emails
    * @param {EmailContatoLaboratorio[]} value
    */
   public set emails(value: EmailContatoLaboratorio[]) {
      this._emails = value;
   }

   /**
    * Getter usuarios
    * @return {Usuario[]}
    */
   public get usuarios(): Usuario[] {
      return this._usuarios;
   }
descricaoLab
   /**
    * Setter usuarios
    * @param {Usuario[]} value
    */
   public set usuarios(value: Usuario[]) {
      this._usuarios = value;
   }

   public jsonToEntity(res: any): Laboratorio {

      //let laboratorio: Laboratorio = new Laboratorio();

      this.id = res.id;
      this.nome = res.nome;

      if (res.usuarios) {
         res.usuarios.forEach(u => {
            this.usuarios.push(new Usuario().jsonToEntity(u));
         });
      }

      if (res.endereco) {
         this.endereco = new EnderecoContato().jsonToEntity(res.endereco);
         /*laboratorio.endereco.tipoLogradouro = res.endereco.tipoLogradouro;
         laboratorio.endereco.nomeLogradouro = res.endereco.nomeLogradouro;
         laboratorio.endereco.numero = res.endereco.numero;
         laboratorio.endereco.complemento = res.endereco.complemento;
         laboratorio.endereco.bairro = res.endereco.bairro;
         laboratorio.endereco.municipio = res.endereco.municipio;
         laboratorio.endereco.cep = res.endereco.cep;
         if (laboratorio.endereco.pais) {
             laboratorio.endereco.pais = new Pais();
             laboratorio.endereco.pais.nome = res.endereco.pais.nome;
         }*/
      }
      if (res.instrucoesColeta) {
         this.instrucoesColeta = [];
         res.instrucoesColeta.forEach(instrucaoColeta => {
            this.instrucoesColeta.push(new InstrucaoColeta().jsonToEntity(instrucaoColeta));
         });
      }

      if (res.emails) {
         this.emails = [];
         res.emails.forEach(email => {
            this.emails.push(new EmailContatoLaboratorio().jsonToEntity(email));
         });
      }

      if (res.quantidadeExamesCT) {
         this.quantidadeExamesCT = new Number(res.quantidadeExamesCT).valueOf();
      }

      if (res.quantidadeAtual) {
         this.quantidadeAtual = new Number(res.quantidadeAtual).valueOf();
      }

      return this;
   }

}
