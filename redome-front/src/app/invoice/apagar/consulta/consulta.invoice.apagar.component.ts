import {
   Component,
   ComponentFactory,
   ComponentFactoryResolver,
   ComponentRef, OnDestroy,
   OnInit,
   ViewChild,
   ViewContainerRef
} from '@angular/core';
import {PermissaoRotaComponente} from "../../../shared/permissao.rota.componente";
import {CrudConsultaComponent} from "../../../admin/crud/consulta/crud.consulta.component";
import {CentroTransplanteService} from "../../../admin/centrotransplante/centrotransplante.service";
import {Router} from "@angular/router";
import {TranslateService} from "@ngx-translate/core";
import {InvoiceService} from "../../../shared/service/invoice.service";
import {ColunaDto} from "../../../shared/dto/coluna.dto";
import {TiposColuna} from "../../../shared/enums/tipos.coluna";
import {FiltroConsulta} from "../../../admin/crud/consulta/filtro/filtro.consulta";
import {UrlParametro} from "../../../shared/url.parametro";
import {ArrayUtil} from "../../../shared/util/array.util";
import {StringUtil} from "../../../shared/util/string.util";
import {DataUtil} from "../../../shared/util/data.util";
import {DateMoment} from "../../../shared/util/date/date.moment";
import {ComponenteRecurso} from "../../../shared/enums/componente.recurso";

@Component({
   selector: 'consulta-invoice-apagar',
   templateUrl: './consulta.invoice.apagar.component.html'
})
export class ConsultaInvoiceApagarComponent implements PermissaoRotaComponente, OnInit, OnDestroy {

   @ViewChild('consulta', {read: ViewContainerRef}) container: ViewContainerRef;

   @ViewChild(FiltroConsulta)
   private filtroConsulta: FiltroConsulta;

   public _maskData: Array<string | RegExp> = [/[0-9]/, /[0-9]/, '\/', /[0-9]/, /[0-9]/, '\/', /[0-9]/, /[0-9]/, /[0-9]/, /[0-9]/];

   private componentRef: ComponentRef<CrudConsultaComponent>;

   public filtrarPorNumero: string;
   public filtrarDataInicio: string;
   public filtrarDataFinal: string;

   constructor(private invoiceService: InvoiceService, private router: Router,
               private translate: TranslateService, private resolver: ComponentFactoryResolver) {

   }

   ngOnInit() {
      this.translate.get('invoice.apagar.consulta').subscribe(res => {
         this.container.clear();
         const factory: ComponentFactory<CrudConsultaComponent> = this.resolver.resolveComponentFactory(CrudConsultaComponent);
         this.componentRef = this.container.createComponent(factory);

         this.componentRef.instance.colunas = [new ColunaDto('id', TiposColuna.NUMBER, true, false),
            new ColunaDto('numero', TiposColuna.STRING),
            new ColunaDto('dataVencimento', TiposColuna.DATE, false, true, (value: any): any => {
               let dateFormat: DateMoment = DateMoment.getInstance();

               if (value) {
                  return dateFormat.format(value);
               }
               return "";
            }),
            new ColunaDto('dataFaturamento', TiposColuna.DATE, false, true, (value: any): any => {
               let dateFormat: DateMoment = DateMoment.getInstance();

               if (value) {
                  return dateFormat.format(value);
               }
               return "";
            }),
            new ColunaDto('total', TiposColuna.NUMBER, false, true, null, 2),
            new ColunaDto('status.nome', TiposColuna.STRING)
         ];
         this.componentRef.instance.crudData = res;
         this.componentRef.instance.metodoListar = this.listar;
         this.componentRef.instance.metodoClicarLinha = (value: any) => {
            this.router.navigateByUrl(`/invoices/apagar/${value.id}`);
         }
         this.componentRef.instance.metodoClicarNovo = (): void => {
            this.router.navigateByUrl("/invoices/apagar/novo");
         }

         this.componentRef.instance.filtroConsulta = this.filtroConsulta;
      });
   }

   private listar = (pagina, quantidadeRegistros: number): Promise<any> => {
      return this.invoiceService.listar(null, pagina, quantidadeRegistros);
   }

   ngOnDestroy(): void {
      this.componentRef.destroy();
   }

   nomeComponente(): string {
      return ComponenteRecurso.Componente[ComponenteRecurso.Componente.ConsultaInvoiceApagarComponent];
   }

   public listarUtilizandoFiltros(): void {
      let filtros: UrlParametro[] = [];

      if (this.filtrarPorNumero) {
         filtros.push(new UrlParametro("numero", this.filtrarPorNumero));
      }

      if (this.filtrarDataInicio) {
         let dataAux: string = this.filtrarDataInicio.replace('/', '').replace('/', '');
         filtros.push(new UrlParametro("dataFaturamentoInicial", dataAux));
      }

      if (this.filtrarDataFinal) {
         let dataAux: string = this.filtrarDataFinal.replace("/", "").replace('/', '');
         filtros.push(new UrlParametro("dataFaturamentoFinal", dataAux));
      }

      this.componentRef.instance.metodoListar = (pagina, quantidadeRegistros: number): Promise<any> => {
         return this.invoiceService.listar(ArrayUtil.isNotEmpty(filtros) ? filtros : null,
            pagina, quantidadeRegistros);
      };
      this.componentRef.instance.listar(1);

   }

}
