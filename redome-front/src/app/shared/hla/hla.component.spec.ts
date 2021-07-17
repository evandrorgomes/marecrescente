import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { FormArray, FormControl, AbstractControl } from "@angular/forms";
import { FileItem, FileUploader } from 'ng2-file-upload';
import { Observable } from "rxjs";
import { HlaComponent } from './hla.component';
import { dominioService } from '../../export.mock.spec';
import { LocusVO } from '../../paciente/cadastro/exame/locusvo';
import { LocusExame } from '../../paciente/cadastro/exame/locusexame';

/**
 * Classe para teste da Classe de HlaComponent
 * @author Pizão
 */
describe('HlaComponent', () => {
    let fixture: ComponentFixture<HlaComponent>;
    let component: HlaComponent;

    beforeEach((done) => (async () => {
        fixture = TestBed.createComponent(HlaComponent);
        component = fixture.debugElement.componentInstance;
        await component.ngOnInit();
    })().then(done).catch(done.fail));

    afterEach(() => {
        fixture = undefined;
        component = undefined;
    });

    /**
   * Verifica se o componente de HLA foi instanciado com sucesso
   * @author Fillipe Queiroz
   */
    it('deveria criar o HlaComponent', () => {
        expect(component).toBeTruthy();
    });

    it('deve exibir mensagem de obrigatoriedade caso o locus A não seja preenchido', async(() => {
        component.setValue([new LocusExame('A')]);

        let control: AbstractControl = component.controls[0];
        component.validateForm();
        expect(control.get('locus').value).toEqual('A');
        expect(component.retornarMensagemErro()).toEqual('O HLA é obrigatório');

    }));

    it('não deve exibir mensagem de obrigatoriedade caso o locus A seja preenchido', async(() => {
        component.setValue([new LocusExame('A')]);

        let control: AbstractControl = component.controls[0];
        control.get('alelo1').setValue("01:01");
        control.get('alelo2').setValue("01:02");

        component.validateForm();
        expect(control.get('locus').value).toEqual('A');
        expect(component.retornarMensagemErro()).toEqual(null);
    }));


    it('deve exibir mensagem de obrigatoriedade caso o locus B não seja preenchido', async(() => {
        component.setValue([new LocusExame('A'), new LocusExame('B')]);

        let controlA: AbstractControl = component.controls[0];
        controlA.get('alelo1').setValue("01:01");
        controlA.get('alelo2').setValue("01:02");

        let controlB: AbstractControl = component.controls[1];

        component.validateForm();
        expect(controlB.get('locus').value).toEqual('B');
        expect(component.retornarMensagemErro()).toEqual('O HLA é obrigatório');
    }));

    it('não deve exibir mensagem de obrigatoriedade caso o locus B seja preenchido', async(() => {
        component.setValue([new LocusExame('A'), new LocusExame('B')]);

        let controlA: AbstractControl = component.controls[0];
        controlA.get('alelo1').setValue("01:01");
        controlA.get('alelo2').setValue("01:02");

        let controlB: AbstractControl = component.controls[1];
        controlB.get('alelo1').setValue("01:01");
        controlB.get('alelo2').setValue("01:02");
        
        component.validateForm();
        expect(controlB.get('locus').value).toEqual('B');
        expect(component.retornarMensagemErro()).toEqual(null);
    }));

    it('deve exibir mensagem de obrigatoriedade caso o locus DRB1 não seja preenchido', async(() => {
        component.setValue([new LocusExame('A'), new LocusExame('B'), new LocusExame('DRB1')]);
        
        let controlA: AbstractControl = component.controls[0];
        controlA.get('alelo1').setValue("01:01");
        controlA.get('alelo2').setValue("01:02");

        let controlB: AbstractControl = component.controls[1];
        controlB.get('alelo1').setValue("01:01");
        controlB.get('alelo2').setValue("01:02");

        let controlDRB1: AbstractControl = component.controls[2];

        component.validateForm();
        expect(controlDRB1.get('locus').value).toEqual('DRB1');
        expect(component.retornarMensagemErro()).toEqual('O HLA é obrigatório');
    }));

    it('não deve exibir mensagem de obrigatoriedade caso o locus DRB1 seja preenchido', async(() => {
        component.setValue([new LocusExame('A'), new LocusExame('B'), new LocusExame('DRB1')]);

        let controlA: AbstractControl = component.controls[0];
        controlA.get('alelo1').setValue("01:01");
        controlA.get('alelo2').setValue("01:02");

        let controlB: AbstractControl = component.controls[1];
        controlB.get('alelo1').setValue("01:01");
        controlB.get('alelo2').setValue("01:02");

        let controlDRB1: AbstractControl = component.controls[2];
        controlDRB1.get('alelo1').setValue("01:01");
        controlDRB1.get('alelo2').setValue("01:02");

        component.validateForm();
        expect(controlDRB1.get('locus').value).toEqual('DRB1');
        expect(component.retornarMensagemErro()).toEqual(null);
    }));


    it('deve exibir mensagem de obrigatoriedade caso o locus A no alelo 1 não seja preenchido', async(() => {
        component.setValue([new LocusExame('A')]);

        let controlA: AbstractControl = component.controls[0];
        controlA.get('alelo1').setValue("01:01");

        component.validateForm();
        expect(controlA.get('locus').value).toEqual('A');
        expect(component.retornarMensagemErro()).toEqual('O HLA é obrigatório');
    }));

    it('deve exibir mensagem de obrigatoriedade caso o locus A no alelo 2 não seja preenchido', async(() => {
        component.setValue([new LocusExame('A')]);

        let controlA: AbstractControl = component.controls[0];
        controlA.get('alelo2').setValue("01:01");

        component.validateForm();
        expect(controlA.get('locus').value).toEqual('A');
        expect(component.retornarMensagemErro()).toEqual('O HLA é obrigatório');
    }));

    it('não deve exibir mensagem de obrigatoriedade caso o locus A nos alelos 1 e 2 sejam preenchidos', async(() => {
        component.setValue([new LocusExame('A')]);

        let controlA: AbstractControl = component.controls[0];
        controlA.get('alelo1').setValue("01:01");
        controlA.get('alelo2').setValue("01:02");

        component.validateForm();
        expect(controlA.get('locus').value).toEqual('A');
        expect(component.retornarMensagemErro()).toEqual(null);
    }));

    it('deve exibir mensagem de obrigatoriedade caso o locus B  no alelo 1 não seja preenchido', async(() => {
        component.setValue([new LocusExame('A'), new LocusExame('B')]);

        let controlA: AbstractControl = component.controls[0];
        controlA.get('alelo1').setValue("01:01");
        controlA.get('alelo2').setValue("01:02");

        let controlB: AbstractControl = component.controls[1];
        controlB.get('alelo2').setValue("01:02");

        component.validateForm();
        expect(controlB.get('locus').value).toEqual('B');
        expect(component.retornarMensagemErro()).toEqual('O HLA é obrigatório');
    }));

    it('deve exibir mensagem de obrigatoriedade caso o locus B  no alelo 2 não seja preenchido', async(() => {
        component.setValue([new LocusExame('A'), new LocusExame('B')]);

        let controlA: AbstractControl = component.controls[0];
        controlA.get('alelo1').setValue("01:01");
        controlA.get('alelo2').setValue("01:02");

        let controlB: AbstractControl = component.controls[1];
        controlB.get('alelo1').setValue("01:01");

        component.validateForm();
        expect(controlB.get('locus').value).toEqual('B');
        expect(component.retornarMensagemErro()).toEqual('O HLA é obrigatório');
    }));

    it('não deve exibir mensagem de obrigatoriedade caso o locus B nos alelo 1 e 2 sejam preenchidos', async(() => {
        component.setValue([new LocusExame('A'), new LocusExame('B')]);

        let controlA: AbstractControl = component.controls[0];
        controlA.get('alelo1').setValue("01:01");
        controlA.get('alelo2').setValue("01:02");

        let controlB: AbstractControl = component.controls[1];
        controlB.get('alelo1').setValue("01:01");
        controlB.get('alelo2').setValue("01:02");

        component.validateForm();
        expect(controlB.get('locus').value).toEqual('B');
        expect(component.retornarMensagemErro()).toEqual(null);
    }));

    it('deve exibir mensagem de obrigatoriedade caso o locus DRB1 no alelo 1 não seja preenchido', async(() => {
        component.setValue([new LocusExame('A'), new LocusExame('B'), new LocusExame('DRB1')]);

        let controlA: AbstractControl = component.controls[0];
        controlA.get('alelo1').setValue("01:01");
        controlA.get('alelo2').setValue("01:02");

        let controlB: AbstractControl = component.controls[1];
        controlB.get('alelo1').setValue("01:01");
        controlB.get('alelo2').setValue("01:02");

        let controlDRB1: AbstractControl = component.controls[2];
        controlDRB1.get('alelo2').setValue("01:01");

        component.validateForm();
        expect(controlDRB1.get('locus').value).toEqual('DRB1');
        expect(component.retornarMensagemErro()).toEqual('O HLA é obrigatório');
    }));

    it('deve exibir mensagem de obrigatoriedade caso o locus DRB1 no alelo 2 não seja preenchido', async(() => {
        component.setValue([new LocusExame('A'), new LocusExame('B'), new LocusExame('DRB1')]);

        let controlA: AbstractControl = component.controls[0];
        controlA.get('alelo1').setValue("01:01");
        controlA.get('alelo2').setValue("01:02");

        let controlB: AbstractControl = component.controls[1];
        controlB.get('alelo1').setValue("01:01");
        controlB.get('alelo2').setValue("01:02");

        let controlDRB1: AbstractControl = component.controls[2];
        controlDRB1.get('alelo1').setValue("01:01");

        component.validateForm();
        expect(controlDRB1.get('locus').value).toEqual('DRB1');
        expect(component.retornarMensagemErro()).toEqual('O HLA é obrigatório');
    }));

    it('não deve exibir mensagem de obrigatoriedade caso o locus DRB1 nos alelo 1 e 2 sejam preenchidos', async(() => {
        component.setValue([new LocusExame('A'), new LocusExame('B'), new LocusExame('DRB1')]);

        let controlA: AbstractControl = component.controls[0];
        controlA.get('alelo1').setValue("01:01");
        controlA.get('alelo2').setValue("01:02");

        let controlB: AbstractControl = component.controls[1];
        controlB.get('alelo1').setValue("01:01");
        controlB.get('alelo2').setValue("01:02");

        let controlDRB1: AbstractControl = component.controls[2];
        controlDRB1.get('alelo1').setValue("01:01");
        controlDRB1.get('alelo2').setValue("01:02");

        component.validateForm();
        expect(controlDRB1.get('locus').value).toEqual('DRB1');
        expect(component.retornarMensagemErro()).toEqual(null);
    }));

});