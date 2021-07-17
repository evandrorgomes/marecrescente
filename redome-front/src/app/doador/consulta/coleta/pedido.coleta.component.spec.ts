import { Observable } from 'rxjs';
import { PerfilUsuario } from '../../../shared/enums/perfil.usuario';
import { MockTarefaService } from '../../../shared/mock/mock.tarefa.service';
import { ExportTranslateModule } from '../../../shared/export.translate.module';
import { TarefaService } from '../../../shared/tarefa.service';
import { DoadorModule } from '../../doador.module';
import { PedidoColetaComponent } from './pedido.coleta.component';
import { MockAutenticacaoService } from '../../../shared/mock/mock.autenticacao.service';
import { AutenticacaoService } from '../../../shared/autenticacao/autenticacao.service';
import { MockPendenciaService } from '../../../shared/mock/mock.pendencia.service';
import { Router } from '@angular/router';
import { FormBuilder } from '@angular/forms';
import { MockPacienteService } from '../../../shared/mock/mock.paciente.service';
import { FakeLoader } from '../../../shared/fake.loader';
import { TranslateModule, TranslateLoader, TranslateService } from '@ngx-translate/core';
import { Renderer } from '@angular/core';
import { AppModule } from '../../../app.module';
import { AppComponent } from '../../../app.component';
import { ComponentFixture, async, TestBed, inject } from '@angular/core/testing';
import { TarefaPedidoColeta } from './tarefa.pedido.coleta';
import { tarefaService } from '../../../export.mock.spec';
import { AtributoOrdenacaoDTO } from '../../../shared/util/atributo.ordenacao.dto';

describe('PedidoColetaComponent', () => {
    let fixtureApp: ComponentFixture<AppComponent>;
    let app: AppComponent;
    let fixture: ComponentFixture<PedidoColetaComponent>;
    let component: PedidoColetaComponent

    beforeEach(() => { 
        
        tarefaService.listarTarefasPaginadas = function(tipotarefa: number, perfil: PerfilUsuario, 
            idUsuarioLogado:number, pagina: number, quantidadeRegistro: number, 
            buscaPorUsuarioLogado?:boolean, atributoOrdenacaoDTO?: AtributoOrdenacaoDTO,
            statusTarefa?:number, parceiro?:number, ordenacao?:number,
            parceiroPorUsuarioLogado?: boolean): Promise<any> {

            let tarefas: TarefaPedidoColeta[] = []

            return Observable.of(tarefas).toPromise();
        };

        fixture = TestBed.createComponent(PedidoColetaComponent);
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
    it('deveria criar o PedidoColetaComponent', () => {
        expect(component).toBeTruthy();
    });

    
});