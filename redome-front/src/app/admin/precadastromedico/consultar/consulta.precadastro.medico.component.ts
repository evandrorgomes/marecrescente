import { PreCadastroMedico } from './../../../shared/classes/precadastro.medico';
import { Component, ComponentFactory, ComponentFactoryResolver, ComponentRef, OnDestroy, OnInit, ViewChild, ViewContainerRef } from '@angular/core';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { MedicoService } from 'app/shared/service/medico.service';
import { ColunaDto } from '../../../shared/dto/coluna.dto';
import { ComponenteRecurso } from '../../../shared/enums/componente.recurso';
import { PermissaoRotaComponente } from '../../../shared/permissao.rota.componente';
import { StatusPreCadastro } from './../../../shared/enums/status.precadastro';
import { TiposColuna } from './../../../shared/enums/tipos.coluna';
import { CrudConsultaComponent } from './../../crud/consulta/crud.consulta.component';
import { DateMoment } from 'app/shared/util/date/date.moment';

/**
 * Classe que representa o componente de consulta do centro de transplante
 * @author Bruno Sousa
 */
@Component({
    selector: 'consultaprecadastromedico',
    templateUrl: './consulta.precadastro.medico.component.html'
})
export class ConsultaPreCadastroMedicoComponent implements PermissaoRotaComponente, OnInit, OnDestroy {
    
    @ViewChild('consulta', { read: ViewContainerRef }) container: ViewContainerRef;

    private componentRef: ComponentRef<CrudConsultaComponent>;

    private labelDias: any;

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

        this.translate.get('textosGenericos.dias').subscribe(res => {
            this.labelDias = res;
        });
        this.translate.get('precadastromedico.consulta').subscribe(res => {
             this.container.clear();
             const factory: ComponentFactory<CrudConsultaComponent> = this.resolver.resolveComponentFactory(CrudConsultaComponent);
             this.componentRef = this.container.createComponent(factory);
             this.componentRef.instance.colunas = [new ColunaDto('id', TiposColuna.NUMBER, true, false),
                new ColunaDto('nome', TiposColuna.STRING),
                new ColunaDto('dataSolicitacao', TiposColuna.DATE, false, true, (value: any): any => {
                    let dateFormat: DateMoment = DateMoment.getInstance();

                    if (value) {
                        let dataLimiteFormatada: string = dateFormat.format(value);
                        let diferencaDias: number = dateFormat.diffDays(value, new Date());        
                        return diferencaDias + " " + this.labelDias + " (" + dataLimiteFormatada + ")";
                    }
                    return "";
                 })
             ];
             this.componentRef.instance.esconderBotaoNovo();
             this.componentRef.instance.crudData = res;
             this.componentRef.instance.metodoListar = this.listar;             
             this.componentRef.instance.metodoClicarLinha = this.clicarLinha;
             

        });

    }

    ngOnDestroy() {
        this.componentRef.destroy();
    }

    private listar = (pagina, quantidadeRegistros: number): Promise<any> => {
        return this.medicoService.listarPreCadastroPorStatus(StatusPreCadastro.AGUARDANDO_APROVACAO, pagina, quantidadeRegistros);
    }

    private clicarLinha = (value: any): void => {
        let preCadastroMedico: PreCadastroMedico = <PreCadastroMedico> value;
        this.router.navigateByUrl("/admin/precadastromedico/" + preCadastroMedico.id);
    }
    
    nomeComponente(): string {
        return ComponenteRecurso.Componente[ComponenteRecurso.Componente.ConsultaPreCadastroMedicoComponent];
    }

}