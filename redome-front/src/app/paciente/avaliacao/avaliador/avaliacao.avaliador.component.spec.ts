import { activatedRoute } from '../../../export.mock.spec';
import { MockAutenticacaoService } from '../../../shared/mock/mock.autenticacao.service';
import { AutenticacaoService } from '../../../shared/autenticacao/autenticacao.service';
import { MockPendenciaService } from '../../../shared/mock/mock.pendencia.service';
import { Router } from '@angular/router';
import {PendenciaService} from '../pendencia.service';
import { AvaliacaoService } from '../avaliacao.service';
import { PacienteModule } from '../../paciente.module';
import { FormBuilder } from '@angular/forms';
import { PacienteService } from '../../paciente.service';
import { MockPacienteService } from '../../../shared/mock/mock.paciente.service';
import { FakeLoader } from '../../../shared/fake.loader';
import { TranslateModule, TranslateLoader, TranslateService } from '@ngx-translate/core';
import { Renderer } from '@angular/core';
import { AvaliacaoAvaliadorModule } from './avaliacao.avaliador.module';
import { AppModule } from '../../../app.module';
import { AvaliacaoAvaliadorComponent } from './avaliacao.avaliador.component';
import { AppComponent } from '../../../app.component';
import { ComponentFixture, async, TestBed, inject } from '@angular/core/testing';

describe('AvaliacaoAvaliadorComponent', () => {
    let fixture: ComponentFixture<AvaliacaoAvaliadorComponent>;
    let component: AvaliacaoAvaliadorComponent

    beforeEach(() => {
        fixture = TestBed.createComponent(AvaliacaoAvaliadorComponent);
        component = fixture.debugElement.componentInstance;
        //idPaciente
        activatedRoute.testQueryParams = {'rmr': 1};

        //component.ngOnInit();
    });

    afterEach(() => {
        fixture = undefined;
        component = undefined;
    });

    /**
     * Verifica se o componente foi instanciado com sucesso
     * @author Bruno Sousa
     */
    it('deveria criar o AvaliacaoComponent', () => {
        expect(component).toBeTruthy();
    });
    
});