import { MockAutenticacaoService } from '../../../shared/mock/mock.autenticacao.service';
import { AutenticacaoService } from '../../../shared/autenticacao/autenticacao.service';
import { FakeLoader } from '../../../shared/fake.loader';
import { AppModule } from '../../../app.module';
import { AppComponent } from '../../../app.component';
import { TranslateService } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { AvaliacaoComponent } from './avaliacao.component';
import { HelperTest } from '../../../shared/helper.test';
import { DominioService } from '../../../shared/dominio/dominio.service';
import { MockBackend } from '@angular/http/testing';
import { HttpClient } from '../../../shared/httpclient.service';
import { HttpModule, XHRBackend, ResponseOptions, Http } from '@angular/http';
import { CadastroComponent } from '../cadastro.component';
import { CadastroModule } from '../cadastro.module';
import { TestBed, async, inject, ComponentFixture } from '@angular/core/testing';
import { FormsModule, FormBuilder, FormGroup } from "@angular/forms";
import { MotivoCadastro } from "../../../shared/enums/motivo.cadastro";
import { Observable } from 'rxjs';

describe('AvaliacaoComponent', () => {
    let fixture: ComponentFixture<AvaliacaoComponent>;
    let component: AvaliacaoComponent

    beforeEach((done) => (async () => { 
        fixture = TestBed.createComponent(AvaliacaoComponent);
        component = fixture.debugElement.componentInstance;
        await component.ngOnInit();
    })().then(done).catch(done.fail));

    afterEach(() => {
        fixture = undefined;
        component = undefined;
    });

    /**
     * Verifica se o componente de motivo foi instanciado com sucesso
     * @author Fillipe Queiroz
     */
    it('deveria criar o AvaliacaoComponent', () => {
        expect(component).toBeTruthy();
    });

    it('deve exibir mensagem de obrigatoriedade para o centro avaliador', async(() => {
        component.motivoForm.controls["centroAvaliador"].setValue(null);
        component.validateForm();
        expect(component.formErrors['centroAvaliador']).toEqual('O campo Centro Avaliador é obrigatório');
    }));
    it('deve exibir mensagem de obrigatoriedade para o aceite mismatch', async(() => {
        component.motivoForm.controls["aceiteMismatch"].setValue(null);
        component.validateForm();
        expect(component.formErrors['aceiteMismatch']).toEqual('O campo Aceita Mismatch de Busca é obrigatório');
    }));
});