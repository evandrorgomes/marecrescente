import { exameService, laboratorioService,activatedRoute } from '../../export.mock.spec';
import { HeaderPacienteComponent } from '../consulta/identificacao/header.paciente.component';
import { PacienteModule } from '../paciente.module';
import { MockPacienteService } from '../../shared/mock/mock.paciente.service';
import {PacienteService} from '../paciente.service';
import { HeaderPacienteModule } from '../consulta/identificacao/header.paciente.module';
import { ArquivoExameService } from '../cadastro/exame/arquivo.exame.service';
import {FakeLoader} from '../../shared/fake.loader';
import { Router } from '@angular/router';
import { ExportTranslateModule } from '../../shared/export.translate.module';
import { MockAutenticacaoService } from '../../shared/mock/mock.autenticacao.service';
import { AutenticacaoService } from '../../shared/autenticacao/autenticacao.service';
import { ConferenciaModule } from './conferencia.module';
import { MockExameService } from '../../shared/mock/mock.exame.service';
import { ExameService } from '../cadastro/exame/exame.service';
import { MockDominioService } from '../../shared/mock/mock.dominio.service';
import { DominioService } from '../../shared/dominio/dominio.service';
import { AppModule } from '../../app.module';
import { ConferenciaComponent } from './conferencia.component';
import { AppComponent } from '../../app.component';
import {TranslateModule, TranslateLoader,  TranslateService} from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { FileUploader, FileItem } from 'ng2-file-upload';
import { TestBed, async, inject, ComponentFixture, fakeAsync, tick } from '@angular/core/testing';
import { HttpModule, XHRBackend, ResponseOptions, Http } from '@angular/http';
import { FormsModule, FormBuilder, FormGroup, FormArray, FormControl } from "@angular/forms";
import { Observable } from "rxjs";
import { discardPeriodicTasks } from "@angular/core/testing";
import { flushMicrotasks } from "@angular/core/testing";
import { ConferenciaDetalheComponent } from './conferencia.detalhe.component';
import { ExamePaciente } from '../cadastro/exame/exame.paciente';
import { Paciente } from '../paciente';

/**
 * Classe para teste da Classe de ExameComponent
 * @author Filipe Paes
 */
describe('ConferenciaDetalheComponent', () => { 
    let fixture: ComponentFixture<ConferenciaDetalheComponent>;
    let component: ConferenciaDetalheComponent;

    const createComponent = () => {        
        fixture = TestBed.createComponent(ConferenciaDetalheComponent);
        component = fixture.componentInstance;
        component.ngOnInit();
    };
    
    beforeEach((done) => (async () => {
        activatedRoute.testQueryParams = {'idTarefa': 1};
        activatedRoute.testParams = {"idExame":1}
        
        laboratorioService.listarLaboratorios = function(): Promise<any> {
            let retorno = {
                content: [{
                  id: "1",
                  nome: "Nome do laboratório"
                }]
            }

            return Observable.of(retorno).toPromise();
        }
        createComponent();
    })().then(done).catch(done.fail));

    afterEach(() => {
        fixture = undefined;
        component = undefined;
    });

    /**
   * Verifica se o componente de exame foi instanciado com sucesso
   * @author Fillipe Queiroz
   */
    it('deveria criar o ExameComponent', () => {
        expect(component).toBeTruthy();
    });

    it('deve exibir mensagem de obrigatoriedade caso o laboratorio esteja vazio e laboratorio particular é falso', async(() => {
        fixture.detectChanges();
        fixture.whenStable().then(() => {
            fixture.detectChanges();     
            component.exameForm.controls["laboratorio"].setValue(null);
            component.exameForm.controls["laboratorioParticular"].setValue(false);
            component.validateForm();
            expect(component.formErrors['laboratorio']).toEqual('O campo Laboratório é obrigatório');
        });
    }));

    it('Nao deve exibir mensagem de obrigatoriedade caso o laboratorio esteja vazio e laboratorio particular é true', async(() => {
        fixture.detectChanges();
        fixture.whenStable().then(() => {
            fixture.detectChanges();  
            component.exameForm.controls["laboratorio"].setValue(null);
            component.exameForm.controls["laboratorioParticular"].setValue(true);
            component.clickLaboratorioParticular({target: {checked: true}});
            component.validateForm();
            expect(component.formErrors['laboratorio']).toEqual('');
        });
    }));

    it('deve exibir mensagem de obrigatoriedade caso a data coleta esteja vazia', async(() => {
        fixture.detectChanges();
        fixture.whenStable().then(() => {
            fixture.detectChanges();  
            component.exameForm.get("dataColetaAmostra").setValue(null);
            component.validateForm();
            expect(component.formErrors['dataColetaAmostra']).toEqual('O campo Data da Coleta é obrigatório');
        });
    
    }));

    it('não deve exibir mensagem de obrigatoriedade caso a data da coleta esteja preenchida', async(() => {
        fixture.detectChanges();
        fixture.whenStable().then(() => {
            fixture.detectChanges();  
            component.exameForm.controls["dataColetaAmostra"].setValue("05/05/2017");
            component.validateForm();
            expect(component.formErrors['dataColetaAmostra']).toEqual('');
        });
    }));

    it('deve exibir mensagem de obrigatoriedade caso a data exame esteja vazia', async(() => {
        fixture.detectChanges();
        fixture.whenStable().then(() => {
            fixture.detectChanges();  
            component.exameForm.get("dataExame").setValue(null);
            component.validateForm();
            expect(component.formErrors['dataExame']).toEqual('O campo Data de Exame é obrigatório');
        });
        
    }));

    it('não deve exibir mensagem de obrigatoriedade caso a data exame esteja preenchida', async(() => {
        fixture.detectChanges();
        fixture.whenStable().then(() => {
            fixture.detectChanges();  
            component.exameForm.controls["dataExame"].setValue("2017-05-05");
            component.validateForm();
            expect(component.formErrors['dataExame']).toEqual('');
        });
    }));

    it('deve exibir mensagem caso a data coleta seja maior que a data do exame', async(() => {
        fixture.detectChanges();
        fixture.whenStable().then(() => {
            fixture.detectChanges();  
            component.exameForm.controls["dataColetaAmostra"].setValue("10/05/2018");
            component.exameForm.controls["dataExame"].setValue("01/05/2018");
            component.validateForm();
            expect(component.formErrors['dataColetaAmostra']).toEqual('Data da Coleta deve ser menor que a Data de Exame');
        });
    }));

    it('não deve exibir mensagem de obrigatoriedade caso a metodologia seja selecionada', async(() => {
        fixture.detectChanges();
        fixture.whenStable().then(() => {
            fixture.detectChanges();  
            component.listMetodologiasForm.controls[0].get('checked').setValue(true);
            component.validateForm();
            expect(component.formErrors["metodologia"]).toEqual('');
        });
    })); 


});