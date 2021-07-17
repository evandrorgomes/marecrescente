import { CampoMensagem } from '../../../../shared/campo.mensagem';
import { flushMicrotasks, tick } from '@angular/core/testing';
import { MensagemModalComponente } from '../../../../shared/modal/mensagem.modal.component';
import {DoadorService} from '../../../doador.service';
import { MockDoadorService } from '../../../../shared/mock/mock.doador.service';
import { MockActivatedRoute } from '../../../../shared/mock/mock.activated.route';
import { MockPedidoColetaService } from '../../../../shared/mock/mock.pedido.coleta.service';
import { PedidoColetaService } from '../../../consulta/coleta/pedido.coleta.service';
import { MockAutenticacaoService } from '../../../../shared/mock/mock.autenticacao.service';
import {TarefaService} from '../../../../shared/tarefa.service';
import { ExportTranslateModule } from '../../../../shared/export.translate.module';
import { DoadorModule } from '../../../doador.module';
import { AppModule } from '../../../../app.module';
import { MockTarefaService } from '../../../../shared/mock/mock.tarefa.service';
import { AppComponent } from '../../../../app.component';
import { Observable } from 'rxjs';
import { AutenticacaoService } from '../../../../shared/autenticacao/autenticacao.service';
import { Router,  ActivatedRoute} from '@angular/router';
import { FormBuilder } from '@angular/forms';
import { TranslateModule, TranslateLoader, TranslateService } from '@ngx-translate/core';
import { Renderer } from '@angular/core';
import { ComponentFixture, async, TestBed, inject, fakeAsync } from '@angular/core/testing';
import { TarefaPedidoColeta } from '../../../consulta/coleta/tarefa.pedido.coleta';
import { PedidoColeta } from '../../../consulta/coleta/pedido.coleta';
import { setTimeout } from 'core-js/library/web/timers';
import { RessalvaComponent } from './ressalva.component';

describe('RessalvaComponent', () => {
    let fixture: ComponentFixture<RessalvaComponent>;
    let component: RessalvaComponent

    jasmine.DEFAULT_TIMEOUT_INTERVAL = 100000;

    beforeEach((done) => (async () => {        
        fixture = TestBed.createComponent(RessalvaComponent);
        component = fixture.componentInstance;
        await component.ngOnInit();
    })().then(done).catch(done.fail));

    afterEach(() => {
        fixture = undefined;
        component = undefined;
    })

    /**
     * Verifica se o componente foi instanciado com sucesso
     * @author Bruno Sousa
     */
    it('[SUCESSO] deveria criar o component RessalvaComponent', () => {        
        expect(component).toBeDefined();
    });

    it('[FALHA] deve validar todos os campos do formulário', async(() => {
        component.validateForm();
        expect(component.formErrors['ressalva']).toEqual("O campo Ressalva é obrigatório")
    }));

    it('[SUCESSO] deve retornar OK se ressalva foi inserida', async(() => {
        component.ressalvaForm.get('ressalva').setValue('A');
        component.validateForm();
        expect(component.formErrors['ressalva']).toEqual("")
    }));

    
});