import {Component, OnDestroy, OnInit, ViewChild} from "@angular/core";
import {BuildForm} from "../../../../shared/buildform/build.form";
import {NumberControl} from "../../../../shared/buildform/controls/number.control";
import {FormGroup, Validators} from "@angular/forms";
import {StringControl} from "../../../../shared/buildform/controls/string.control";
import {ValidateData} from "../../../../validators/data.validator";
import {UploadArquivoComponent} from "../../../../shared/upload/upload.arquivo.component";
import {Paginacao} from "../../../../shared/paginacao";
import {PacienteUtil} from "../../../../shared/paciente.util";
import {ItemInvoice} from "../../../../shared/classes/item.invoice";
import {TipoServico} from "../../../../shared/dominio/tipo.servico";
import { InvoiceService } from "app/shared/service/invoice.service";
import { UrlParametro } from "app/shared/url.parametro";
import { MessageBox } from "app/shared/modal/message.box";
import { ItemConcilicacaoModalComponent } from "./modalconciliacao/itemconciliacao.modal.component";
import { Invoice } from "app/shared/classes/invoice";
import { ErroUtil } from "app/shared/util/erro.util";
import { ErroMensagem } from "app/shared/erromensagem";
import { ModalItensNaoConciliadosComponent } from "./modalitensnaolocalizados/modal.itensnaoconciliados.component";

@Component({
   selector: 'itens-invoice-apagar',
   templateUrl: './itens.invoice.apagar.component.html'
})
export class ItensInvoiceApagarComponent implements OnInit, OnDestroy {

   _labelsTipoConsulta: string[] = ['Pesquisar Paciente', 'Importar Arquivo'];
   _valuesTipoConsulta: string[] = ['0', '1'];
   _invoice:Invoice;

   private formItensInvoice: BuildForm<any>;

   @ViewChild("uploadComponent")
   private uploadComponent: UploadArquivoComponent;

   quantidadeRegistro: number = 10;
   paginacao: Paginacao;

   quantidadeRegistroConciliado: number = 10;
   paginacaoConciliado: Paginacao;


   constructor(private invoiceService:InvoiceService
      , private messageBox: MessageBox) {
      this.formItensInvoice = new BuildForm<any>()
         .add(new StringControl('tipoConsulta', '0'))
         .add(new NumberControl('rmr', null))
         .add(new StringControl('nome', null));

      this.paginacao = new Paginacao('', 1, this.quantidadeRegistro);
      this.paginacao.quantidadeRegistro = 10;
      this.paginacao.number = 1;

      this.paginacaoConciliado =  new Paginacao('', 1, this.quantidadeRegistro);
      this.paginacaoConciliado.quantidadeRegistro = 10;
      this.paginacaoConciliado.number = 1;
   }

   ngOnInit(): void {

      this.uploadComponent.extensoes = 'extensaoArquivoFinanceiro';
      this.uploadComponent.tamanhoLimite = 'tamanhoArquivoFinanceiroEmByte';
      this.uploadComponent.quantidadeMaximaArquivos = 'quantidadeArquivoFinanceiro';
      this.uploadComponent.mensagemClass = "form-group form-group-min-height-0 col-sm-12 col-xs-12";
   }

   ngOnDestroy(): void {
   }

   form(): FormGroup {
      return <FormGroup>this.formItensInvoice.form;
   }

   formataData(data: string): string {
      return PacienteUtil.converterStringEmData(data).toLocaleDateString();
   }

   mudarPaginaPaginacao(event: any) {
      this.paginacao.slicePage(event.page);
   }

   mudarPaginaPaginacaoConciliado(event: any) {
      this.paginacaoConciliado.slicePage(event.page);
   }

   selecinaQuantidadePorPagina(event: any) {
      this.paginacao.quantidadeRegistro = this.quantidadeRegistro;
   }

   selecinaQuantidadePorPaginaConciliado(event: any){
      this.paginacaoConciliado.quantidadeRegistro = this.quantidadeRegistroConciliado;
   }

   formatarNumero(valor: number): string {
      return new Intl.NumberFormat('pt-BR', {style: 'decimal', maximumFractionDigits: 2, minimumFractionDigits: 2}).format(valor);
   }

   pesquisar(){
      let filtros: UrlParametro[] = [];

      if (this.formItensInvoice.get('rmr').value) {
         filtros.push(new UrlParametro("rmr", this.formItensInvoice.get('rmr').value));
      }
      if (this.formItensInvoice.get('nome').value) {
         filtros.push(new UrlParametro("nome", this.formItensInvoice.get('nome').value));
      }

      this.invoiceService.listarItens(filtros, this.paginacao.number - 1, this.paginacao.quantidadeRegistro).then(res=>{
         this.paginacao.content = res.content;
      });
   }

   conciliarItem(item){
      let data: any = {
         idInvoice: this._invoice.id,
         idPagamento:item.idPagamento,
         idTipoServico: item.tipoServico.id,
         idDoadorInternacional: item.idDoadorInternacional,
         rmr:item.rmr,
         listarConciliados: ()=>{
            this.listarItensConciliados();
         },
         pesquisar: ()=>{
            this.pesquisar();
         }
     };

     this.messageBox.dynamic(data, ItemConcilicacaoModalComponent)
         .withTarget(this)
         .withCloseOption((target?: any) => {                
         })
         .show();
   }

   listarItensConciliados(){
      this.invoiceService.listarItensConciliados(this._invoice.id
         , this.paginacaoConciliado.number - 1
         , this.quantidadeRegistroConciliado).then(res=>{
            this.paginacaoConciliado.content = res.content;
      });
   }

   excluirItem(id:number){
      this.invoiceService.excluirItem(id).then(res=>{
         this.listarItensConciliados();
         this.pesquisar();
      });
   }

   importar(){
      if(this.uploadComponent.validateForm()){
         this.invoiceService.enviarArquivo(this._invoice.id, this.uploadComponent.arquivos).then(res=>{
             if(res.itensNaoLocalizados && res.itensNaoLocalizados.length > 0){
               let data: any = {
                  mainList:res.itensNaoLocalizados
              };
         
              this.messageBox.dynamic(data, ModalItensNaoConciliadosComponent)
                  .withTarget(this)
                  .withCloseOption((target?: any) => {  
                     this.uploadComponent.clear();              
                     this.formItensInvoice.form.controls["tipoConsulta"].setValue("0");
                  })
                  .show();
             }
             this.paginacaoConciliado.mainList = res.itensLocalizados;
         }, (error: ErroMensagem) => {
            ErroUtil.exibirMensagemErro(error, this.messageBox);
        });
      }
   }




}
