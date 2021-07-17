import { UsuarioLogado } from '../../../shared/dominio/usuario.logado';
import { Medico } from '../../../shared/dominio/medico';
import { Paciente } from '../../paciente';
import { Avaliacao } from '../avaliacao';
import { ExportTranslateModule } from '../../../shared/export.translate.module';
import {MockAutenticacaoService} from '../../../shared/mock/mock.autenticacao.service';
import { AutenticacaoService } from '../../../shared/autenticacao/autenticacao.service';
import { FakeLoader } from '../../../shared/fake.loader';
import { AppModule } from '../../../app.module';
import { PendenciaDialogoComponent } from './pendencia.dialogo.component';
import { AppComponent } from '../../../app.component';
import { Router } from '@angular/router';
import { FormBuilder } from '@angular/forms';
import { TranslateModule, TranslateLoader, TranslateService } from '@ngx-translate/core';
import { Renderer } from '@angular/core';
import { ComponentFixture, async, TestBed, inject, fakeAsync, flushMicrotasks } from '@angular/core/testing';

describe('DialogoComponent', () => {
    let fixtureApp: ComponentFixture<AppComponent>;
    let app: AppComponent;
    let fixture: ComponentFixture<PendenciaDialogoComponent>;
    let component: PendenciaDialogoComponent

    beforeEach((done) => (async () => {        
        fixture = TestBed.createComponent(PendenciaDialogoComponent);
        component = fixture.debugElement.componentInstance;        
        await component.ngOnInit();
    })().then(done).catch(done.fail));

    afterEach(() => {
        fixture = undefined;
        component = undefined;
    });

    /**
     * Verifica se o componente foi instanciado com sucesso
     * @author Fillipe Queiroz
     */
    it('deveria criar o DialogoComponent', () => {
        expect(component).toBeTruthy();
    });

    it('comentário deveria ser obrigatório ', async(() => {
        component.validateForm();
        expect('O campo Comentário é obrigatório').toEqual(component.formErrors['comentario']);
    }));

    it('comentário preenchido corretamente não deveria retornar mensagem de erro', async(() =>{
		component.dialogoForm.controls["comentario"].setValue("Comentário válido inserido.");            
		component.validateForm();
        expect('').toEqual(component.formErrors['comentario']);
    }));

});