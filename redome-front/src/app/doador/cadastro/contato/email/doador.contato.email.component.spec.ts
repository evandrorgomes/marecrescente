import { TranslateService } from '@ngx-translate/core';
import { FormBuilder } from '@angular/forms';
import { MockAutenticacaoService } from '../../../../shared/mock/mock.autenticacao.service';
import { AutenticacaoService } from '../../../../shared/autenticacao/autenticacao.service';
import { MockDominioService } from '../../../../shared/mock/mock.dominio.service';
import { DominioService } from '../../../../shared/dominio/dominio.service';
import { ExportTranslateModule } from '../../../../shared/export.translate.module';
import { DoadorContatoModule } from '../doador.contato.module';
import { AppModule } from '../../../../app.module';
import { AppComponent } from '../../../../app.component';
import { ComponentFixture, async, TestBed, inject } from '@angular/core/testing';
import { DoadorEmailContatoComponent } from './doador.contato.email.component';

describe('DoadorContatoEmailComponent', () => {
    let fixtureApp: ComponentFixture<AppComponent>;
    let app: AppComponent;
    let fixture: ComponentFixture<DoadorEmailContatoComponent>;
    let component: DoadorEmailContatoComponent;

    beforeEach(() => {
        fixture = TestBed.createComponent(DoadorEmailContatoComponent);
        component = fixture.componentInstance;        
        component.ngOnInit();
    });

    afterEach(() => {
        fixture = undefined;
        component = undefined;
    });

    it('deveria criar o EmailContatoComponent', () => {
        expect(component).toBeTruthy();
    }); 

});