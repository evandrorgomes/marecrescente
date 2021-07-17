import { Component, ComponentFactory, ComponentFactoryResolver, ComponentRef, OnDestroy, OnInit, ViewChild, ViewContainerRef, Input } from '@angular/core';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { UrlParametro } from 'app/shared/url.parametro';
import { ArrayUtil } from 'app/shared/util/array.util';
import { CentroTransplante } from '../../../shared/dominio/centro.transplante';
import { ColunaDto } from '../../../shared/dto/coluna.dto';
import { ComponenteRecurso } from '../../../shared/enums/componente.recurso';
import { PermissaoRotaComponente } from '../../../shared/permissao.rota.componente';
import { FormatterUtil } from '../../../shared/util/formatter.util';
import { CentroTransplanteService } from '../centrotransplante.service';
import { TiposColuna } from './../../../shared/enums/tipos.coluna';
import { CrudConsultaComponent } from './../../crud/consulta/crud.consulta.component';
import { FiltroConsulta } from 'app/admin/crud/consulta/filtro/filtro.consulta';

/**
 * Classe que representa o componente de consulta do centro de transplante
 * @author Bruno Sousa
 */
@Component({
    selector: 'consultacentrotransplante',
    templateUrl: './consulta.centrotransplante.component.html'
})
export class ConsultaCentroTransplanteComponent implements PermissaoRotaComponente, OnInit, OnDestroy {
    
    @ViewChild('consulta', { read: ViewContainerRef }) container: ViewContainerRef;

    private componentRef: ComponentRef<CrudConsultaComponent>;
    
    @Input() mascara: Array<string | RegExp>;

    @ViewChild(FiltroConsulta)
    private filtroConsulta: FiltroConsulta;

    public filtrarPorNome: string;
    public filtrarPorCnpj: string;
    public filtrarPorCnes: string;

    /**
     * @param FormBuilder
     * @param PacienteService
     * @param Router
     * @param TranslateService
     * @author Bruno Sousa
     */
    constructor(private centroTransplanteService: CentroTransplanteService, private router: Router,
        private translate: TranslateService, private resolver: ComponentFactoryResolver ) {

    }

    /**
     * 
     * @memberOf ConsultaComponent
     */
    ngOnInit(): void {
        this.translate.get('centrotransplante.consulta').subscribe(res => {
             this.container.clear();
             const factory: ComponentFactory<CrudConsultaComponent> = this.resolver.resolveComponentFactory(CrudConsultaComponent);
             this.componentRef = this.container.createComponent(factory);
             this.componentRef.instance.colunas = [new ColunaDto('id', TiposColuna.NUMBER, true, false),
                 new ColunaDto('nome', TiposColuna.STRING),
                 new ColunaDto('cnpj', TiposColuna.STRING),
                 new ColunaDto('cnes', TiposColuna.STRING)
             ];
             this.componentRef.instance.crudData = res;
             this.componentRef.instance.metodoListar = this.listar;
             this.componentRef.instance.metodoClicarLinha = this.clicarLinha;
             this.componentRef.instance.metodoClicarNovo = (): void => {
                 this.router.navigateByUrl("/admin/centrostransplante/novo");
             }

             this.componentRef.instance.filtroConsulta = this.filtroConsulta;
        });

    }

    ngOnDestroy() {
        this.componentRef.destroy();
    }

    private listar = (pagina, quantidadeRegistros: number): Promise<any> => {
        return this.centroTransplanteService.listarCentroTransplantes(null, pagina, quantidadeRegistros);
    }

    private clicarLinha = (value: any): void => {
        let centroTransplante: CentroTransplante = <CentroTransplante> value;
        this.router.navigateByUrl("/admin/centrostransplante/" + centroTransplante.id);
    }
    
    nomeComponente(): string {
        return ComponenteRecurso.Componente[ComponenteRecurso.Componente.ConsultaCentroTransplanteComponent];
    }

    /**
     * Formata o cnpj do centro de transplante
     * 
     * @param {string} cnpj 
     * @returns {string} 
     * @memberof ConsultaComponent
     */
    formataCNPJ(cnpj: string): string {
        return FormatterUtil.aplicarMascaraCNPJ(cnpj);
    }

     /**
   * @description Chama o lista de usuários utilizando algum dos filtros.
   * @author ergomes
   */
  public listarUtilizandoFiltros(): void{
    let filtros: UrlParametro[] = [];

    if(this.filtrarPorNome){
      filtros.push(new UrlParametro("nome", this.filtrarPorNome));
    }

    if(this.filtrarPorCnpj){
      filtros.push(new UrlParametro("cnpj", this.filtrarPorCnpj));
    }

    if(this.filtrarPorCnes){
      filtros.push(new UrlParametro("cnes", this.filtrarPorCnes));
    }

    this.componentRef.instance.metodoListar = (pagina, quantidadeRegistros: number): Promise<any> => {
      return this.centroTransplanteService.listarCentroTransplantes(ArrayUtil.isNotEmpty(filtros) ? filtros : null,
          pagina, quantidadeRegistros);
    };
    this.componentRef.instance.ngOnInit();
  }

}