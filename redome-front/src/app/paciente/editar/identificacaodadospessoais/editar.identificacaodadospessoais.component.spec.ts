import * as moment from 'moment';
import { DataUtil } from '../../../shared/util/data.util';
import { Raca } from '../../../shared/dominio/raca';
import { UF } from '../../../shared/dominio/uf';
import { Pais } from '../../../shared/dominio/pais';
import { MockActivatedRoute } from '../../../shared/mock/mock.activated.route';
import { Paciente } from '../../paciente';
import { ActivatedRoute, Router } from '@angular/router';
import { MockAutenticacaoService } from '../../../shared/mock/mock.autenticacao.service';
import { AutenticacaoService } from '../../../shared/autenticacao/autenticacao.service';
import { TranslateService } from '@ngx-translate/core';
import { FormBuilder } from '@angular/forms';
import { MockPacienteService } from '../../../shared/mock/mock.paciente.service';
import { DominioService } from '../../../shared/dominio/dominio.service';
import { MockDominioService } from '../../../shared/mock/mock.dominio.service';
import { PacienteService } from '../../paciente.service';
import { ExportTranslateModule } from '../../../shared/export.translate.module';
import { EditarModule } from '../editar.module';
import { AppModule } from '../../../app.module';
import { EditarIdentificacaoDadosPessoaisComponent } from './editar.identificacaodadospessoais.component';
import { AppComponent } from '../../../app.component';
import { ComponentFixture, async, TestBed, inject, fakeAsync, flushMicrotasks, tick } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { activatedRoute, pacienteService } from '../../../export.mock.spec';

describe('EditarIdentificacaoDadosPessoaisComponent', () => {
    let fixture: ComponentFixture<EditarIdentificacaoDadosPessoaisComponent>;
    let component: EditarIdentificacaoDadosPessoaisComponent

    jasmine.DEFAULT_TIMEOUT_INTERVAL = 100000;
    
    beforeEach((done) => (async () => {
        activatedRoute.testParams = {'idPaciente': 1};

        pacienteService.obterIdentificaoEDadosPessoais = function(rmr: number): Promise<string> {
            let paciente: Paciente = new Paciente();
            paciente.rmr = 1;
            paciente.dataNascimento = new Date("10/10/1980");
            paciente.dataNascimento.toJSON = function(){ 
                return moment(this).format("YYYY-MM-DD");
            }
            paciente.cpf = '62455588327';
            paciente.cns = '';
            paciente.nome = 'Nome do paciente';
            paciente.nomeMae = '';
            paciente.sexo = 'M';
            paciente.pais = new Pais(1, "BRASIL");
            paciente.naturalidade = new UF('RJ');
            paciente.abo = 'O-';
            paciente.raca = new Raca(1);        

            return Observable.of(paciente.toJSON()).toPromise();
        }
        
        pacienteService.verificarDuplicidade = function (paciente: Paciente) {            
            return Observable.of('').toPromise();
        }

        pacienteService.salvarIdentificacaoEDadosPessoais = function (paciente: Paciente) {
            let retorno = {mensagem: 'Paciente (RMR ' + paciente.rmr + ') atualizado com sucesso.'};
            return Observable.of(retorno).toPromise();
        }

        fixture = TestBed.createComponent(EditarIdentificacaoDadosPessoaisComponent);
        component = fixture.debugElement.componentInstance;
        await component.ngOnInit();
        fixture.detectChanges();

    })().then(done).catch(done.fail));

    afterEach(() => {
        fixture = undefined;
        component = undefined;
    });

    /**
     * Verifica se o componente de evalocao foi instanciado com sucesso
     * @author Fillipe Queiroz
     */
    it('deveria criar o EvolucaoComponent', () => {
        expect(component).toBeTruthy();
    });

    it("deve retornar mensagem 'Paciente (RMR 1) atualizado com sucesso.' Quando atualizar o paciente", (done) => (async () => {
        
        await component.onSubmit(null, null);
        expect(component.mensagemSucesso).toEqual('Paciente (RMR 1) atualizado com sucesso.');
        

    })().then(done).catch(done.fail));

});