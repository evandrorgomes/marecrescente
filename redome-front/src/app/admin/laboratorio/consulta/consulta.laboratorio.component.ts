import { Component, ComponentFactory, ComponentFactoryResolver, ComponentRef, OnDestroy, OnInit, ViewChild, ViewContainerRef } from '@angular/core';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { FiltroConsulta } from 'app/admin/crud/consulta/filtro/filtro.consulta';
import { Laboratorio } from 'app/shared/dominio/laboratorio';
import { UrlParametro } from 'app/shared/url.parametro';
import { ArrayUtil } from 'app/shared/util/array.util';
import { ColunaDto } from '../../../shared/dto/coluna.dto';
import { ComponenteRecurso } from '../../../shared/enums/componente.recurso';
import { TiposColuna } from '../../../shared/enums/tipos.coluna';
import { PermissaoRotaComponente } from '../../../shared/permissao.rota.componente';
import { LaboratorioService } from '../../../shared/service/laboratorio.service';
import { CrudConsultaComponent } from '../../crud/consulta/crud.consulta.component';

/**
 * Classe que representa o componente de consulta de médicos
 * @author ergomes
 */
@Component({
    selector: 'laboratorio-consulta',
    templateUrl: './consulta.laboratorio.component.html'
})
export class ConsultaLaboratorioComponent implements PermissaoRotaComponente, OnInit, OnDestroy {

    @ViewChild('consulta', { read: ViewContainerRef }) container: ViewContainerRef;

    private componentRef: ComponentRef<CrudConsultaComponent>;

    @ViewChild(FiltroConsulta)
    private filtroConsulta: FiltroConsulta;

    public filtrarPorNome: string;
    public filtrarPorUf: string;

    /**
     * @param FormBuilder
     * @param PacienteService
     * @param Router
     * @param TranslateService
     * @author ergomes
     */
    constructor(private laboratorioService: LaboratorioService, private router: Router,
        private translate: TranslateService, private resolver: ComponentFactoryResolver ) {

    }

    /**
     *
     * @memberOf ConsultaComponent
     */
    ngOnInit(): void {
        this.translate.get('manterLaboratorio.consulta').subscribe(res => {
             this.container.clear();
             const factory: ComponentFactory<CrudConsultaComponent> = this.resolver.resolveComponentFactory(CrudConsultaComponent);
             this.componentRef = this.container.createComponent(factory);
             this.componentRef.instance.colunas = [
                 new ColunaDto('id', TiposColuna.NUMBER, true, false),
                 new ColunaDto('nome', TiposColuna.STRING),
                 new ColunaDto('endereco.municipio.uf.sigla', TiposColuna.STRING)
             ];
             this.componentRef.instance.crudData = res;
             this.componentRef.instance.metodoListar = this.listar;
             this.componentRef.instance.metodoClicarLinha = this.clicarLinha;
             this.componentRef.instance.esconderBotaoNovo();
             this.componentRef.instance.filtroConsulta = this.filtroConsulta;
        });


    }

    ngOnDestroy() {
        this.componentRef.destroy();
    }

    private listar = (pagina, quantidadeRegistros: number): Promise<any> => {
        return this.laboratorioService.listarLaboratoriosCT(null, pagina, quantidadeRegistros);
    }

    private clicarLinha = (value: any): void => {
        let laboratorio: Laboratorio = <Laboratorio> value;
        this.router.navigateByUrl("/admin/laboratorios/" + laboratorio.id);
    }

    nomeComponente(): string {
        return ComponenteRecurso.Componente[ComponenteRecurso.Componente.LaboratorioConsultaComponent];
    }

     /**
   * @description Chama o lista de medico utilizando algum dos filtros.
   * @author ergomes
   */
  public listarUtilizandoFiltros(): void{
    let filtros: UrlParametro[] = [];

    if(this.filtrarPorNome){
      filtros.push(new UrlParametro("nome", this.filtrarPorNome));
    }

    if(this.filtrarPorUf){
      filtros.push(new UrlParametro("uf", this.filtrarPorUf));
    }

    this.componentRef.instance.metodoListar = (pagina, quantidadeRegistros: number): Promise<any> => {
      return this.laboratorioService.listarLaboratoriosCT(ArrayUtil.isNotEmpty(filtros) ? filtros : null,
          pagina, quantidadeRegistros);
    };
    this.componentRef.instance.ngOnInit();
  }
}
