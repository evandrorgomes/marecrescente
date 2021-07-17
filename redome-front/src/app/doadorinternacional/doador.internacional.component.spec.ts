import { CampoMensagem } from '../shared/campo.mensagem';
import { flushMicrotasks, tick } from '@angular/core/testing';
import { Observable } from 'rxjs';
import { AutenticacaoService } from '../shared/autenticacao/autenticacao.service';
import { Router,  ActivatedRoute} from '@angular/router';
import { FormBuilder } from '@angular/forms';
import { TranslateModule, TranslateLoader, TranslateService } from '@ngx-translate/core';
import { Renderer } from '@angular/core';
import { ComponentFixture, async, TestBed, inject, fakeAsync } from '@angular/core/testing';
import { TarefaPedidoColeta } from '../doador/consulta/coleta/tarefa.pedido.coleta';
import { PedidoColeta } from '../doador/consulta/coleta/pedido.coleta';
import { setTimeout } from 'core-js/library/web/timers';
import { DoadorInternacionalComponent } from './doador.internacional.component';
import { activatedRoute } from '../export.mock.spec';

describe('DoadorInternacionalComponent', () => {
    let fixture: ComponentFixture<DoadorInternacionalComponent>;
    let component: DoadorInternacionalComponent

    jasmine.DEFAULT_TIMEOUT_INTERVAL = 100000;

    const createComponent = () => {        
        fixture = TestBed.createComponent(DoadorInternacionalComponent);
        component = fixture.componentInstance;         
    };
            
    beforeEach(() => { 
        // activatedRoute.testQueryParams = {'tarefaId': 1};
        // activatedRoute.testParams = {'pedidoId': 3};


        createComponent(); 
    });

    afterEach(() => {
        fixture = undefined;
        component = undefined;
    });
});