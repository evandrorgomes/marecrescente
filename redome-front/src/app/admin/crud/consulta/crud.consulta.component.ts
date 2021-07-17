import {AfterViewInit, Component, OnInit, TemplateRef} from '@angular/core';
import {FiltroConsulta} from 'app/admin/crud/consulta/filtro/filtro.consulta';
import {Paginacao} from 'app/shared/paginacao';
import {ConvertUtil} from 'app/shared/util/convert.util';
import {ColunaDto} from './../../../shared/dto/coluna.dto';
import {TipoColuna, TiposColuna} from "../../../shared/enums/tipos.coluna";

/**
 * Classe que representa o componente crud de consulta
 * @author Bruno Sousa
 */
@Component({
   selector: 'crud-consulta',
   templateUrl: './crud.consulta.component.html',
})
export class CrudConsultaComponent implements OnInit, AfterViewInit {

   paginacao: Paginacao;

   quantidadeRegistro: number = 10;

   private _exibirBotaoNovo: boolean = true;
   private _crudData: any;
   private _colunas: ColunaDto[];
   public metodoListar: (pagina, quantidadeRegistro: number) => Promise<any>;
   public metodoClicarLinha: (value: any) => void;
   public metodoClicarNovo: () => void;


   public filtros: TemplateRef<any>;

   /**
    * @param FormBuilder
    * @param PacienteService
    * @param Router
    * @param TranslateService
    * @author Bruno Sousa
    */
   constructor() {
      this.paginacao = new Paginacao('', 0, this.quantidadeRegistro);
      this.paginacao.number = 1;
   }

   ngAfterViewInit() {
   }

   /**
    *
    * @memberOf ConsultaComponent
    */
   ngOnInit(): void {
      this.listar(1);
   }

   /**
    *  Método que envia os dados para a consulta, quando é retornado apenas um registro
    * é verificado se o paciente é do médico logado, caso seja é redirecionado automaticamente
    * para a página de detalhe
    *
    * @private
    * @param {number} pagina
    * @param {*} modal
    *
    * @memberOf ConsultaComponent
    */
   public listar(pagina: number) {

      if (this.metodoListar) {
         this.metodoListar(pagina - 1, this.quantidadeRegistro).then(res => {
            this.paginacao.content = res.content;
            this.paginacao.totalElements = res.totalElements;
            this.paginacao.quantidadeRegistro = this.quantidadeRegistro;
            this.paginacao.number = pagina;
         })
      }

   }

   /**
    * Método acionado quando muda a página
    *
    * @param {*} event
    * @param {*} modal
    *
    * @memberOf ConsultaComponent
    */
   mudarPagina(event: any) {
      this.listar(event.page);
   }

   /**
    * Método acionado quando é alterado a quantidade de registros por página
    *
    * @param {*} event
    * @param {*} modal
    *
    * @memberOf ConsultaComponent
    */
   selecinaQuantidadePorPagina(event: any, modal: any) {
      this.listar(1);
   }

   public titulo(): string {
      if (this._crudData)
         return this._crudData['titulo'];
      return '';
   }

   public getColumnTitulo(value: string): string {
      if (this._crudData && this._crudData["colunas"]) {
         return this._crudData["colunas"][value];
      }
      return '';
   }

   /**
    * Getter colunas
    * @return {ColunaDto[]}
    */
   public get colunas(): ColunaDto[] {
      return this._colunas;
   }

   /**
    * Setter colunas
    * @param {ColunaDto[]} value
    */
   public set colunas(value: ColunaDto[]) {
      this._colunas = value;
   }

   /**
    * Setter crudData
    * @param {any} value
    */
   public set crudData(value: any) {
      this._crudData = value;
   }

   public getConteudo(conteudo: any, coluna: ColunaDto): any {
      let value = ConvertUtil.parseJsonParaAtributos(this.getNiveis(conteudo, coluna), coluna.tipoColuna);
      if (coluna.beforeShow) {
         return coluna.beforeShow(value);
      } else {
         if (value && coluna.tipoColuna == TiposColuna.NUMBER && coluna.casasDecimais != 0) {
            return new Intl.NumberFormat('pt-BR', {style: 'decimal', maximumFractionDigits: coluna.casasDecimais, minimumFractionDigits: coluna.casasDecimais}).format(value);
         }
      }
      return value;
      //return ConvertUtil.parseJsonParaAtributos(this.getNiveis(conteudo, coluna), coluna.tipoColuna);
   }

   public getNiveis(conteudo: any, coluna: ColunaDto): any {
      let niveis = coluna.nome.split('.');

      let valorRetornado = conteudo;
      niveis.forEach(c => {
         valorRetornado = valorRetornado[c];
      });
      return valorRetornado;

   }

   public clicarLinha(value: any): void {
      if (this.metodoClicarLinha) {
         this.metodoClicarLinha(value);
      }
   }

   public clicarNovo(): void {
      if (this.metodoClicarNovo) {
         this.metodoClicarNovo();
      }
   }

   /**
    * Getter exibirBotaoNovo
    * @return {boolean }
    */
   public get exibirBotaoNovo(): boolean {
      return this._exibirBotaoNovo;
   }

   public esconderBotaoNovo() {
      this._exibirBotaoNovo = false;
   }

   public mostrarBotaoNovo() {
      this._exibirBotaoNovo = true;
   }

   public set filtroConsulta(filtro: FiltroConsulta) {
      this.filtros = filtro.template;
   }
}
