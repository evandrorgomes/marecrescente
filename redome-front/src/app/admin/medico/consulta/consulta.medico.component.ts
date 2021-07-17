import { Medico } from './../../../shared/dominio/medico';
import { OnDestroy } from '@angular/core';
import { TiposColuna } from './../../../shared/enums/tipos.coluna';
import { CrudConsultaComponent } from './../../crud/consulta/crud.consulta.component';
import { ComponenteRecurso } from '../../../shared/enums/componente.recurso';
import { PermissaoRotaComponente } from '../../../shared/permissao.rota.componente';
import { TranslateService } from '@ngx-translate/core';
import { Router } from '@angular/router';
import { Component, OnInit, AfterViewInit, ViewChild, ViewEncapsulation, ViewContainerRef, ComponentFactoryResolver, ComponentFactory, ComponentRef } from '@angular/core';
import { ColunaDto } from '../../../shared/dto/coluna.dto';
import { MedicoService } from 'app/shared/service/medico.service';
import { UrlParametro } from 'app/shared/url.parametro';
import { ArrayUtil } from 'app/shared/util/array.util';
import { FiltroConsulta } from 'app/admin/crud/consulta/filtro/filtro.consulta';

/**
 * Classe que representa o componente de consulta de médicos
 * @author Bruno Sousa
 */
@Component({
    selector: 'consultamedico',
    templateUrl: './consulta.medico.component.html'
})
export class ConsultaMedicoComponent implements PermissaoRotaComponente, OnInit, OnDestroy {
    
    @ViewChild('consulta', { read: ViewContainerRef }) container: ViewContainerRef;

    @ViewChild(FiltroConsulta)
    private filtroConsulta: FiltroConsulta;

    private componentRef: ComponentRef<CrudConsultaComponent>;

    public filtrarPorCrm: string;
    public filtrarPorNome: string;
 
    /**
     * @param FormBuilder
     * @param PacienteService
     * @param Router
     * @param TranslateService
     * @author Bruno Sousa
     */
    constructor(private medicoService: MedicoService, private router: Router,
        private translate: TranslateService, private resolver: ComponentFactoryResolver ) {

    }

    /**
     * 
     * @memberOf ConsultaComponent
     */
    ngOnInit(): void {
        this.translate.get('medico.consulta').subscribe(res => {
             this.container.clear();
             const factory: ComponentFactory<CrudConsultaComponent> = this.resolver.resolveComponentFactory(CrudConsultaComponent);
             this.componentRef = this.container.createComponent(factory);
             this.componentRef.instance.colunas = [
                 new ColunaDto('id', TiposColuna.NUMBER, true, false),
                 new ColunaDto('crm', TiposColuna.STRING),
                 new ColunaDto('nome', TiposColuna.STRING)                 
             ];
             this.componentRef.instance.crudData = res;
             this.componentRef.instance.metodoListar = this.listar;
             this.componentRef.instance.metodoClicarLinha = this.clicarLinha;
             this.componentRef.instance.esconderBotaoNovo();
        });
        this.componentRef.instance.filtroConsulta = this.filtroConsulta;
    }

    ngOnDestroy() {
        this.componentRef.destroy();
    }

    private listar = (pagina, quantidadeRegistros: number): Promise<any> => {
        return this.medicoService.listarMedicos(null, pagina, quantidadeRegistros);
    }

    private clicarLinha = (value: any): void => {
        let medico: Medico = <Medico> value;
        this.router.navigateByUrl("/admin/medicos/" + medico.id);
    }
    
    nomeComponente(): string {
        return ComponenteRecurso.Componente[ComponenteRecurso.Componente.ConsultaMedicoComponent];
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

    if(this.filtrarPorCrm){
      filtros.push(new UrlParametro("crm", this.filtrarPorCrm));
    }

    this.componentRef.instance.metodoListar = (pagina, quantidadeRegistros: number): Promise<any> => {
      return this.medicoService.listarMedicos(ArrayUtil.isNotEmpty(filtros) ? filtros : null,
          pagina, quantidadeRegistros);
    };
    this.componentRef.instance.ngOnInit();
  }
}