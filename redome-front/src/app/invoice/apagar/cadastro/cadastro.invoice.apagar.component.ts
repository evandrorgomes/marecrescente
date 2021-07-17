import {Component, OnDestroy, OnInit, ViewChild} from "@angular/core";
import {PermissaoRotaComponente} from "../../../shared/permissao.rota.componente";
import {ActivatedRoute, Router} from "@angular/router";
import {TranslateService} from "@ngx-translate/core";
import {MessageBox} from "../../../shared/modal/message.box";
import {ComponenteRecurso} from "../../../shared/enums/componente.recurso";
import {InvoiceService} from "../../../shared/service/invoice.service";
import {Invoice} from "../../../shared/classes/invoice";
import {InvoiceApagarEventService} from "./invoice.apagar.event.service";
import {CampoMensagem} from "../../../shared/campo.mensagem";
import {ErroMensagem} from "../../../shared/erromensagem";
import {ErroUtil} from "../../../shared/util/erro.util";
import {DataUtil} from "../../../shared/util/data.util";
import {DateMoment} from "../../../shared/util/date/date.moment";
import {DateTypeFormats} from "../../../shared/util/date/date.type.formats";
import {StatusInvoice} from "../../../shared/dominio/status.invoice";
import {RouterUtil} from "../../../shared/util/router.util";
import { ItensInvoiceApagarComponent } from "./itensinvoice/itens.invoice.apagar.component";

@Component({
   selector: 'cadastro-invoice-apagar',
   templateUrl: './cadastro.invoice.apagar.component.html'
})
export class CadastroInvoiceApagarComponent implements PermissaoRotaComponente, OnInit, OnDestroy {

   private ETAPA_DADOS_INVOICE: number = 1;
   private ETAPA_ITEM_INVOICE: number = 2;

   private _etapaAtual: number = 1;

   private invoice: Invoice;

   private STATUS_INVOICE_PAGA:number = 0;
   private STATUS_INVOICE_CANCELADA:number = 2;

   @ViewChild("itemInvoice")
   public itensInvoice: ItensInvoiceApagarComponent;



   constructor(private router: Router, private translate: TranslateService, protected activatedRouter: ActivatedRoute,
               private messageBox: MessageBox, private invoiceService: InvoiceService) {

   }

   ngOnInit(): void {

      RouterUtil.recuperarParametroDaActivatedRoute(this.activatedRouter, 'id').then(res => {
         if (res) {
            this.invoiceService.obterPorId(res).then(res => {
               this.invoice = new Invoice().jsonToEntity(res);
               InvoiceApagarEventService.popularFormularioEtapa1({numero: this.invoice.numero,
               dataFaturamento: DataUtil.dataDateToString(this.invoice.dataFaturamento),
               dataVencimento: DataUtil.dataDateToString(this.invoice.dataVencimento)})
               this.itensInvoice._invoice = this.invoice;
               this.itensInvoice.listarItensConciliados();
            })
         }
      });

   }

   ngOnDestroy(): void {
   }

   nomeComponente(): string {
      return ComponenteRecurso.Componente[ComponenteRecurso.Componente.CadastroInvoiceApagarComponent];
   }

   public voltarConsulta() {
      this.router.navigateByUrl('/invoices/apagar');
   }


   finalizarInvoice(){
      let status: StatusInvoice = new StatusInvoice();
      status.id = this.STATUS_INVOICE_PAGA;
      this.invoice.status = status;
      this.invoiceService.atualizar(this.invoice).then(res =>{
         this.translate.get('invoice.apagar.cadastro.mensagem.sucesso').subscribe(res => {
            this.messageBox.alert(res)
            .withCloseOption((target?: any) => {  
               this.voltarConsulta();
            })
            .show();
         });
      });
   }

   cancelarInvoice(){
      let status: StatusInvoice = new StatusInvoice();
      status.id = this.STATUS_INVOICE_CANCELADA;
      this.invoice.status = status;
      this.invoiceService.atualizar(this.invoice).then(res =>{
         this.translate.get('invoice.apagar.cadastro.mensagem.sucesso').subscribe(res => {
            this.messageBox.alert(res)
            .withCloseOption((target?: any) => {  
               this.voltarConsulta();
            })
            .show();
         });
      });
   }

   public getEstiloDoLinkDaMigalhaDePao(etapaAtual): string {
      if (this._etapaAtual > etapaAtual) {
         return "ativo";
      } else if (this._etapaAtual == etapaAtual) {
         return "ativo current";
      } else {
         return "";
      }

   }

   public habilitarEtapa(etapaParaHabilitar: number): void {
      if (etapaParaHabilitar < this._etapaAtual) {
         this._etapaAtual = etapaParaHabilitar;
      }
   }

   /**
    * Mtodo para habilitar a etapa anterior
    *
    * @memberof CadastroComponent
    */
   public habilitarEtapaAnterior(): void {
      if (this._etapaAtual == this.ETAPA_DADOS_INVOICE) {
         return;
      }
      this._etapaAtual--;
   }

   exibirAnterior(): boolean {
      return this._etapaAtual > this.ETAPA_DADOS_INVOICE;
   }

   /**
    * Avança para a prxima etapa, somente se o formulrio atual
    * tiver preenchido corretamente.
    *
    * @memberof CadastroComponent
    */
   public habilitarProximaEtapa(): void {
      if (this._etapaAtual == this.ETAPA_ITEM_INVOICE) {
         return;
      } else {
         this.salvarInvoice().then(res => {
            if (res) {
               this._etapaAtual++;
            }
         });
      }
   }

   public exibirProximo(): boolean {
      return this._etapaAtual < this.ETAPA_ITEM_INVOICE;
   }

   public esconderEtapa(etapaParaTestar): boolean {
      return this._etapaAtual != etapaParaTestar;
   }

   private salvarInvoice(): Promise<Boolean> {
      if (InvoiceApagarEventService.validarFormularioEtapa1()) {
         const dados = InvoiceApagarEventService.obterDadosFormularioEtapa1();
         if (!this.invoice) {
            this.invoice = new Invoice();
            this.invoice.status = new StatusInvoice(StatusInvoice.ABERTA);
         }
         this.invoice.numero = dados.numero;
         this.invoice.dataFaturamento = DataUtil.toDate(dados.dataFaturamento, DateTypeFormats.DATE_ONLY);
         this.invoice.dataVencimento = DataUtil.toDate(dados.dataVencimento, DateTypeFormats.DATE_ONLY);
         if (!this.invoice.id) {
            return this.invoiceService.salvar(this.invoice).then(res => {
               this.invoice = new Invoice().jsonToEntity(res);
               return Promise.resolve(true);
            }, (error: ErroMensagem) => {
               ErroUtil.exibirMensagemErro(error, this.messageBox);
               return Promise.resolve(false);
            });
         }
         return this.invoiceService.atualizar(this.invoice).then(res => {
            this.invoice = new Invoice().jsonToEntity(res);
            return Promise.resolve(true);
         }, (error: ErroMensagem) => {
            ErroUtil.exibirMensagemErro(error, this.messageBox);
            return Promise.resolve(false);
         });

      }
      return Promise.resolve(false);
   }


}

