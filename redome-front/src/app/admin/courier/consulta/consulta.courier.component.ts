import { PermissaoRotaComponente } from "../../../shared/permissao.rota.componente";
import { OnInit, Component, ViewContainerRef, ComponentRef, ViewChild, ComponentFactoryResolver, ComponentFactory } from "@angular/core";
import { CrudConsultaComponent } from "../../crud/consulta/crud.consulta.component";
import { FiltroConsulta } from "../../crud/consulta/filtro/filtro.consulta";
import { Router } from "@angular/router";
import { TranslateService } from "@ngx-translate/core";
import { CourierService } from "../../../transportadora/courier.service";
import { HistoricoNavegacao } from "../../../shared/historico.navegacao";
import { TiposColuna } from "../../../shared/enums/tipos.coluna";
import { ColunaDto } from "../../../shared/dto/coluna.dto";
import { Courier } from "../../../transportadora/courier";
import { UrlParametro } from "../../../shared/url.parametro";
import { ArrayUtil } from "../../../shared/util/array.util";


/**
 * Componente para consulta de courier do redome.
 * @author Filipe Paes
 */
@Component({
    selector: "consulta-courier",
    templateUrl: './consulta.courier.component.html'
})
export class CourierConsultaComponent implements PermissaoRotaComponente, OnInit {


    @ViewChild('consulta', { read: ViewContainerRef }) container: ViewContainerRef;
    private componentRef: ComponentRef<CrudConsultaComponent>;

    @ViewChild(FiltroConsulta)
    private filtroConsulta: FiltroConsulta;

    public filtrarPorNome: string;
    public filtrarPorCpf: string;

    constructor(private router: Router,
        private translate: TranslateService,
        private resolver: ComponentFactoryResolver,
        private courierService: CourierService) {
    }


    public voltarSemDesatribuir(target: any) {
        target.router.navigateByUrl(HistoricoNavegacao.urlRetorno())
    }

    
    ngOnInit(): void {
        this.translate.get('manterCourier').subscribe(res => {
            //this.container.clear();
            const factory: ComponentFactory<CrudConsultaComponent> = this.resolver.resolveComponentFactory(CrudConsultaComponent);
            this.componentRef = this.container.createComponent(factory);
            this.componentRef.instance.colunas = [
                new ColunaDto('id', TiposColuna.NUMBER, true, false),
                new ColunaDto('nome', TiposColuna.STRING),
                new ColunaDto('cpf', TiposColuna.STRING),
                new ColunaDto('rg', TiposColuna.STRING),
            ];
            this.componentRef.instance.crudData = res;
            this.componentRef.instance.metodoListar = (pagina, quantidadeRegistros: number): Promise<any> => {
                return this.courierService.buscarCouriers(pagina, quantidadeRegistros);
            };
            this.componentRef.instance.metodoClicarLinha = (value: any): void => {
              let courier: Courier = value;
              this.router.navigateByUrl("/admin/couriers/" + courier.id);
            };
            this.componentRef.instance.mostrarBotaoNovo();
            this.componentRef.instance.metodoClicarNovo = (): void => {
              this.router.navigateByUrl("/admin/couriers/novo");
            };

            this.componentRef.instance.filtroConsulta = this.filtroConsulta;

        });

    }

    /**
   * @description Chama o lista de usuários utilizando algum dos filtros.
   * @author Pizão
   */
  public listarUtilizandoFiltros(): void{
    let filtros: UrlParametro[] = [];

    if(this.filtrarPorNome){
      filtros.push(new UrlParametro("nome", this.filtrarPorNome));
    }

    if(this.filtrarPorCpf){
        filtros.push(new UrlParametro("cpf", this.filtrarPorCpf));
      }

    this.componentRef.instance.metodoListar = (pagina, quantidadeRegistros: number): Promise<any> => {
          return this.courierService.buscarCouriers(pagina, quantidadeRegistros, ArrayUtil.isNotEmpty(filtros) ? filtros : null);
    };
    this.componentRef.instance.ngOnInit();
  }

    ngOnDestroy() {
        this.componentRef.destroy();
    }

    nomeComponente(): string {
        return "CourierConsultaComponent";
    }



};