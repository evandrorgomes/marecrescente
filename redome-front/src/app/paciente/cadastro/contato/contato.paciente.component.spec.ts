import { FakeLoader } from '../../../shared/fake.loader';
import { AppComponent } from '../../../app.component';
import { AppModule } from '../../../app.module';
import { CommonModule } from '@angular/common';
import { Injector } from '@angular/core/src/di';
import { TranslateService } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { HelperTest } from '../../../shared/helper.test';
import { CepCorreioService } from '../../../shared/cep.correio.service';
import { ContatoPacienteComponent } from './contato.paciente.component';
import { PacienteConstantes } from '../../paciente.constantes';
import { Observable } from 'rxjs';
import { DominioService } from '../../../shared/dominio/dominio.service';
import { MockBackend } from '@angular/http/testing';
import { Pais } from '../../../shared/dominio/pais';
import { HttpClient } from '../../../shared/httpclient.service';
import { HttpModule, XHRBackend, Response, ResponseOptions, Http, ConnectionBackend, RequestOptions, BaseRequestOptions } from '@angular/http';
import { CadastroComponent } from '../cadastro.component';
import { CadastroModule } from '../cadastro.module';
import { TestBed, async, inject, getTestBed, ComponentFixture } from '@angular/core/testing';
import { FormsModule, FormBuilder, FormGroup, ReactiveFormsModule } from "@angular/forms";
import { MockConnection } from "@angular/http/testing";

describe('ContatoPacienteComponent', () => {
  let fixture: ComponentFixture<ContatoPacienteComponent>;
  let component: ContatoPacienteComponent

  beforeEach(() => {
    fixture = TestBed.createComponent(ContatoPacienteComponent);
    component = fixture.debugElement.componentInstance;
    component.ngOnInit();
  });

  afterEach(() => {
    fixture = undefined;
    component = undefined;
  });

  it('deveria criar o ContatoPacienteComponent', () => {
    expect(component).toBeTruthy();
  });


});