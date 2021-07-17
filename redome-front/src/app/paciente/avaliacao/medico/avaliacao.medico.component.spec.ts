import { MockAutenticacaoService } from '../../../shared/mock/mock.autenticacao.service';
import { AutenticacaoService } from '../../../shared/autenticacao/autenticacao.service';
import { MockAvaliacaoService } from '../../../shared/mock/mock.avaliacao.service';
import { MockPendenciaService } from '../../../shared/mock/mock.pendencia.service';
import { Router, ActivatedRoute } from '@angular/router';
import {PendenciaService} from '../pendencia.service';
import { AvaliacaoService } from '../avaliacao.service';
import { FormBuilder } from '@angular/forms';
import { PacienteService } from '../../paciente.service';
import { MockPacienteService } from '../../../shared/mock/mock.paciente.service';
import { FakeLoader } from '../../../shared/fake.loader';
import { TranslateModule, TranslateLoader, TranslateService } from '@ngx-translate/core';
import { Renderer } from '@angular/core';
import { AppModule } from '../../../app.module';
import { AvaliacaoMedicoComponent } from './avaliacao.medico.component';
import { AppComponent } from '../../../app.component';
import { ComponentFixture, async, TestBed, inject } from '@angular/core/testing';

describe('AvaliacaoMedicoComponent', () => {
    let fixture: ComponentFixture<AvaliacaoMedicoComponent>;
    let component: AvaliacaoMedicoComponent
    
    beforeEach(() => {
        fixture = TestBed.createComponent(AvaliacaoMedicoComponent);
        component = fixture.debugElement.componentInstance;
        component.ngOnInit();
    });

    afterEach(() => {
        fixture = undefined;
        component = undefined;
    });

    /**
     * Verifica se o componente foi instanciado com sucesso
     * @author Bruno Sousa
     */
    it('deveria criar o AvaliacaoMedicoComponent', () => {
        expect(component).toBeTruthy();
    });
    
    
});