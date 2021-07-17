import { FakeLoader } from '../../../shared/fake.loader';
import { AppModule } from '../../../app.module';
import { AppComponent } from '../../../app.component';
import { TranslateService } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { DiagnosticoComponent } from './diagnostico.component';
import { HelperTest } from '../../../shared/helper.test';
import { DominioService } from '../../../shared/dominio/dominio.service';
import { MockBackend } from '@angular/http/testing';
import { HttpClient } from '../../../shared/httpclient.service';
import { HttpModule, XHRBackend, ResponseOptions, Http } from '@angular/http';
import { CadastroComponent } from '../cadastro.component';
import { CadastroModule } from '../cadastro.module';
import { TestBed, async, inject, ComponentFixture } from '@angular/core/testing';
import { FormsModule, FormBuilder, FormGroup } from "@angular/forms";

describe('diagnosticoComponent', () => {
  let fixture: ComponentFixture<DiagnosticoComponent>;
  let component: DiagnosticoComponent

  beforeEach(() => {
    fixture = TestBed.createComponent(DiagnosticoComponent);
    component = fixture.debugElement.componentInstance;
    component.ngOnInit();
  });

  afterEach(() => {
    fixture = undefined;
    component = undefined;
  });

  /**
     * Verifica se o componente de diagnostico foi instanciado com sucesso
     * @author Bruno Sousa
     */
  it('deveria criar o DiagnosticoComponent', () => {
    expect(component).toBeTruthy();
  });

  it('deve exibir a mensagem \'O campo Data do Diagnóstico é obrigatório\' caso o data seja vazia', async(() => {

    component.diagnosticoForm.controls["dataDiagnostico"].setValue('');
    component.validateForm();
    expect( 'O campo Data do Diagnóstico é obrigatório').toEqual(component.formErrors['dataDiagnostico']);
  }));

  it('não deve exibir mensagem de erro caso a data seja preenchida', async(() => {

    component.diagnosticoForm.controls["dataDiagnostico"].setValue('2017-06-08');

    component.validateForm();
    expect('').toEqual(component.formErrors['dataDiagnostico']);
  }));

  it('deve exibir a mensagem \'O campo CID é obrigatório\' caso seja vazio', async(() => {

    component.diagnosticoForm.controls["cid"].setValue('')

    component.validateForm();
    expect('O campo CID é obrigatório').toEqual(component.formErrors['cid']);
  }));

  it('não deve exibir mensagem de erro caso o cid seja preenchido', async(() => {

    component.diagnosticoForm.controls["cid"].setValue(1);

    component.validateForm();
    expect('').toEqual(component.formErrors['cid']);
    
  }));


});