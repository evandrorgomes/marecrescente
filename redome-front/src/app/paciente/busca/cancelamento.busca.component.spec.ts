import { Observable } from 'rxjs';
import { activatedRoute } from '../../export.mock.spec';
import { MotivoCancelamentoBusca } from './motivo.cancelamento.busca';
import { MockBuscaService } from '../../shared/mock/mock.busca.service';
import { BuscaService } from './busca.service';
import { MockDominioService } from '../../shared/mock/mock.dominio.service';
import { DominioService } from '../../shared/dominio/dominio.service';
import { ExportTranslateModule } from '../../shared/export.translate.module';
import { AppModule } from '../../app.module';
import { AppComponent } from '../../app.component';
import { CancelamentoBuscaComponent } from './cancelamento.busca.component';
import { Paciente } from '../paciente';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { FormBuilder } from '@angular/forms';
import { ComponentFixture, async, TestBed, inject } from '@angular/core/testing';
import { buscaService } from '../../export.mock.spec';

describe('CancelamentoBuscaComponent', () => {
    let fixture: ComponentFixture<CancelamentoBuscaComponent>;
    let component: CancelamentoBuscaComponent

    beforeEach(() => {
        fixture = TestBed.createComponent(CancelamentoBuscaComponent);
        component = fixture.debugElement.componentInstance;

        activatedRoute.testParams = {'idPaciente': 1};

    });

    afterEach(() => {
        fixture = undefined;
        component = undefined;
    });

    /**
     * Verifica se o componente de evalocao foi instanciado com sucesso
     * @author Fillipe Queiroz
     */
    it('deveria criar o CancelamentoBuscaComponent', () => {
        expect(component).toBeTruthy();
    });

    it("deve retornar mensagem obrigatoriedade se evento do cancelamento vazio", (done) => (async () => {
        buscaService.listarMotivosCancelamentoBusca = function (): Promise<any> {
            let motivos:MotivoCancelamentoBusca[] = [];
            let novoMotivo =new  MotivoCancelamentoBusca()
            novoMotivo.descricao="teste"
            novoMotivo.descricaoObrigatorio = 1;
            novoMotivo.id = 1;
            motivos.push(novoMotivo)
            //return Promise.resolve(motivos);

            let res = [
                {"id": "1", "descricao": "teste", "descricaoObrigatorio": "1"}
            ];

            return Observable.of(res).toPromise();

        }

        await component.ngOnInit();
        component.validateForm();
        expect('O campo Motivo é obrigatório').toEqual(component.formErrors['motivo']);

    })().then(done).catch(done.fail));

    it("não deve retornar mensagem de obrigatoriedade se evento do cancelamento estiver preenchido", (done) => (async () => {
        buscaService.listarMotivosCancelamentoBusca = function (): Promise<any> {
            let motivos:MotivoCancelamentoBusca[] = [];
            let novoMotivo =new  MotivoCancelamentoBusca()
            novoMotivo.descricao="teste"
            novoMotivo.descricaoObrigatorio = 1;
            novoMotivo.id = 1;
            motivos.push(novoMotivo)
            //return Promise.of(motivos).;

            let res = [
                {"id": "1", "descricao": "teste", "descricaoObrigatorio": "1"}
            ];

            return Observable.of(res).toPromise();

        }

        await component.ngOnInit();
        component.cancelamentoBuscaForm.get("motivo").setValue(1);
        component.validateForm();
        expect('').toEqual(component.formErrors['motivo']);

    })().then(done).catch(done.fail));

    it(" deve retornar mensagem de obrigatoriedade se evento do cancelamento obrigar ter nome", (done) => (async () => {
        buscaService.listarMotivosCancelamentoBusca = function (): Promise<any> {
            let motivos:MotivoCancelamentoBusca[] = [];
            let novoMotivo =new  MotivoCancelamentoBusca()
            novoMotivo.descricao="teste"
            novoMotivo.descricaoObrigatorio =1;
            novoMotivo.id = 1;
            motivos.push(novoMotivo)
            //return Promise.resolve(motivos);
            let res = [
                {"id": "1", "descricao": "teste", "descricaoObrigatorio": "1"}
            ];

            return Observable.of(res).toPromise();
        }

        await component.ngOnInit();

        component.cancelamentoBuscaForm.get("motivo").setValue(1);
        component.alterarValidacaoDoCampoDescricao(1);
        component.validateForm();
        expect('O campo Especifique é obrigatório').toEqual(component.formErrors['especifique']);

    })().then(done).catch(done.fail));

    it("nao deve retornar mensagem de obrigatoriedade se evento do cancelamento nao obrigar ter nome", (done) => (async () => {
        buscaService.listarMotivosCancelamentoBusca = function (): Promise<any> {
            let motivos:MotivoCancelamentoBusca[] = [];
            let novoMotivo =new  MotivoCancelamentoBusca()
            novoMotivo.descricao="teste"
            novoMotivo.descricaoObrigatorio =0;
            novoMotivo.id = 1;
            motivos.push(novoMotivo)
            //return Promise.resolve(motivos);
            let res = [
                {"id": "1", "descricao": "teste", "descricaoObrigatorio": "0"}
            ];

            return Observable.of(res).toPromise();
        }

        await  component.ngOnInit();

        component.cancelamentoBuscaForm.get("motivo").setValue(1);
        component.alterarValidacaoDoCampoDescricao(1);
        component.validateForm();
        expect('').toEqual(component.formErrors['especifique']);

    })().then(done).catch(done.fail));

});
