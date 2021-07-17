import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { FormArray, FormControl, AbstractControl } from "@angular/forms";
import { FileItem, FileUploader } from 'ng2-file-upload';
import { Observable } from "rxjs";
import { LocusComponent } from './locus.component';
import { Locus } from '../../../paciente/cadastro/exame/locus';

/**
 * Classe para teste da Classe de HlaComponent
 * @author Pizão
 */
describe('LocusComponent', () => {
    let fixture: ComponentFixture<LocusComponent>;
    let component: LocusComponent;

    beforeEach((done) => (async () => {
        fixture = TestBed.createComponent(LocusComponent);
        component = fixture.debugElement.componentInstance;
        await component.ngOnInit();
    })().then(done).catch(done.fail));

    afterEach(() => {
        fixture = undefined;
        component = undefined;
    });

    it('deveria criar o LocusComponent', () => {
        expect(component).toBeTruthy();
    });

    it('[FALHA] deve exibir mensagem de obrigatoriedade caso seja obrigatório e nenhum lócus tenha sido informado', async(() => {
        component.required = true;
        component.validateForm();
        expect(component.retornarMensagemErro()).toEqual('Ao menos um lócus deve ser informado');

    }));

    it('[SUCESSO] NÃO deve exibir mensagem de obrigatoriedade caso NÃO seja obrigatório e ao menos um lócus é informado como obrigatório', async(() => {
        component.required = false;
        component.locusObrigatorio = "A";
        component.validateForm();
        expect(component.retornarMensagemErro()).toEqual(null);

    }));

    it('[SUCESSO] não deve exibir mensagem de obrigatoriedade caso o locus A seja preenchido', async(() => {
        component.required = false;
        component.validateForm();
        expect(component.retornarMensagemErro()).toEqual(null);
    }));

});