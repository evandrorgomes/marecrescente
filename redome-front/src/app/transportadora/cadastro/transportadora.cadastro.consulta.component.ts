import { Component, ComponentFactory, ComponentFactoryResolver, ComponentRef, OnInit, ViewChild, ViewContainerRef } from "@angular/core";
import { Router } from "@angular/router";
import { TranslateService } from '@ngx-translate/core';
import { Transportadora } from "app/shared/model/transportadora";
import { TransportadoraService } from "app/shared/service/transportadora.service";
import { CrudConsultaComponent } from '../../admin/crud/consulta/crud.consulta.component';
import { FiltroConsulta } from '../../admin/crud/consulta/filtro/filtro.consulta';
import { ColunaDto } from '../../shared/dto/coluna.dto';
import { TiposColuna } from '../../shared/enums/tipos.coluna';
import { HistoricoNavegacao } from '../../shared/historico.navegacao';
import { PermissaoRotaComponente } from "../../shared/permissao.rota.componente";
import { UrlParametro } from '../../shared/url.parametro';
import { ArrayUtil } from '../../shared/util/array.util';


/**
 * Componente para consulta de transportadoras do redome.
 * @author Filipe Paes
 */
@Component({
    selector: "transportadora-cadastro-consulta",
    templateUrl: './transportadora.cadastro.consulta.component.html',
    styleUrls: ['./transportadora.cadastro.consulta.component.css']
})
export class TransportadoraCadastroConsultaComponent implements PermissaoRotaComponente, OnInit {


    @ViewChild('consulta', { read: ViewContainerRef }) container: ViewContainerRef;
    private componentRef: ComponentRef<CrudConsultaComponent>;

    @ViewChild(FiltroConsulta)
    private filtroConsulta: FiltroConsulta;

    public filtrarPorNome: string;

    constructor(private router: Router,
        private translate: TranslateService,
        private resolver: ComponentFactoryResolver,
        private transportadoraService: TransportadoraService) {
    }


    public voltarSemDesatribuir(target: any) {
        target.router.navigateByUrl(HistoricoNavegacao.urlRetorno())
    }
    ngOnInit(): void {
        this.translate.get('manterTransportadora').subscribe(res => {
            //this.container.clear();
            const factory: ComponentFactory<CrudConsultaComponent> = this.resolver.resolveComponentFactory(CrudConsultaComponent);
            this.componentRef = this.container.createComponent(factory);
            this.componentRef.instance.colunas = [
                new ColunaDto('id', TiposColuna.NUMBER, true, false),
                new ColunaDto('nome', TiposColuna.STRING),
            ];
            this.componentRef.instance.crudData = res;
            this.componentRef.instance.metodoListar = (pagina, quantidadeRegistros: number): Promise<any> => {
                return this.transportadoraService.buscarTransportadoras(pagina, quantidadeRegistros);
            };
            this.componentRef.instance.metodoClicarLinha = (value: any): void => {
              let transportadora: Transportadora = value;
              this.router.navigateByUrl("/admin/transportadoras/" + transportadora.id);
            };
            this.componentRef.instance.mostrarBotaoNovo();
            this.componentRef.instance.metodoClicarNovo = (): void => {
              this.router.navigateByUrl("/admin/transportadoras/nova");
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

    this.componentRef.instance.metodoListar = (pagina, quantidadeRegistros: number): Promise<any> => {
          return this.transportadoraService.buscarTransportadoras(pagina, quantidadeRegistros, ArrayUtil.isNotEmpty(filtros) ? filtros : null);
    };
    this.componentRef.instance.ngOnInit();
  }

    ngOnDestroy() {
        this.componentRef.destroy();
    }

    nomeComponente(): string {
        return "TransportadoraCadastroConsultaComponent";
    }



};
