import { ComponentFixture, TestBed } from '@angular/core/testing';
import { AppComponent } from '../../app.component';
import { CadastroDoadorInternacionalComponent } from './cadastro.doador.internacional.component';
import { dominioService, activatedRoute } from '../../export.mock.spec';
import { Observable } from 'rxjs';

describe('CadastroDoadorInternacionalComponent', () => {
    let fixtureApp: ComponentFixture<AppComponent>;
    let app: AppComponent;
    let fixture: ComponentFixture<CadastroDoadorInternacionalComponent>;
    let component: CadastroDoadorInternacionalComponent;

    const createComponent = () => {
        fixture = TestBed.createComponent(CadastroDoadorInternacionalComponent);
        component = fixture.componentInstance;
        // component.ngOnInit();
    };

    beforeEach((done) => (async () => {
        activatedRoute.testParams = {'rmr': 1};
        activatedRoute.testQueryParams = {'rmr': 1};

        dominioService.listarRegistrosInternacionais = function(): Promise<any> {
            let _RES_REGISTROS = [
                {
                    id: '1',
                    nome: 'nome'
                },
                {
                    id: '2',
                    nome: 'nome2'
                }

            ];

            return Observable.of(_RES_REGISTROS).toPromise();
        }

        createComponent();
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
    it('deveria criar o CadastroDoadorInternacionalComponent', () => {
        expect(component).toBeTruthy();
    });

    it('[SUCESSO] DEVERIA CHAMAR O CADASTRO DE CORDÃO', () => {
        component.isCordao = true;
        component.doadorForm.get('quantidadeTotalTCN').setValue("2321");
        component.doadorForm.get('quantidadeTotalCD34').setValue("2321");
        component.doadorForm.get('volume').setValue("2321");
        component.doadorForm.get('id').setValue("2321");
        component.doadorForm.get('abo').setValue("A+");
        component.doadorForm.get('sexo').setValue("M");
        component.doadorForm.get('origem').setValue("1");
        component.doadorForm.get('pagamento').setValue("1");

        component.validateForm();
        component.salvar();
        expect(component.formErrors['quantidadeTotalTCN']).toEqual('');
        expect(component.formErrors['quantidadeTotalCD34']).toEqual('');
        expect(component.formErrors['volume']).toEqual('');
        expect(component.formErrors['id']).toEqual('');
        expect(component.formErrors['abo']).toEqual('');
        expect(component.formErrors['sexo']).toEqual('');
        expect(component.formErrors['origem']).toEqual('');
        expect(component.formErrors['pagamento']).toEqual('');



    });

    it('[FALHA] DEVERIA EXIBIR MENSAGEM DE OBRIGATORIEDADE DE CD34', () => {
        component.isCordao = true;
        component.doadorForm.get('quantidadeTotalTCN').setValue("2321");
        // component.doadorForm.get('quantidadeTotalCD34').setValue("");
        component.doadorForm.get('volume').setValue("2321");
        component.doadorForm.get('id').setValue("2321");
        component.doadorForm.get('abo').setValue("A+");
        component.doadorForm.get('sexo').setValue("M");
        component.doadorForm.get('origem').setValue("1");
        component.doadorForm.get('pagamento').setValue("1");

        // component.validateForm();
        component.salvar();
        console.log(component.formErrors)
        expect(component.formErrors['quantidadeTotalCD34']).toEqual('O campo CD34 é obrigatório');

    });

    it('[FALHA] DEVERIA EXIBIR MENSAGEM DE OBRIGATORIEDADE DE TCN', () => {
        component.isCordao = true;
        // component.doadorForm.get('quantidadeTotalTCN').setValue("2321");
        component.doadorForm.get('quantidadeTotalCD34').setValue("1322");
        component.doadorForm.get('volume').setValue("2321");
        component.doadorForm.get('id').setValue("2321");
        component.doadorForm.get('abo').setValue("A+");
        component.doadorForm.get('sexo').setValue("M");
        component.doadorForm.get('origem').setValue("1");
        component.doadorForm.get('pagamento').setValue("1");

        // component.validateForm();
        component.salvar();
        console.log(component.formErrors)
        expect(component.formErrors['quantidadeTotalTCN']).toEqual('O campo TCN é obrigatório');

    });

    it('[FALHA] DEVERIA EXIBIR MENSAGEM DE OBRIGATORIEDADE DE VOLUME', () => {
        component.isCordao = true;
        component.doadorForm.get('quantidadeTotalTCN').setValue("2321");
        component.doadorForm.get('quantidadeTotalCD34').setValue("1322");
        // component.doadorForm.get('volume').setValue("2321");
        component.doadorForm.get('id').setValue("2321");
        component.doadorForm.get('abo').setValue("A+");
        component.doadorForm.get('sexo').setValue("M");
        component.doadorForm.get('origem').setValue("1");
        component.doadorForm.get('pagamento').setValue("1");

        // component.validateForm();
        component.salvar();
        console.log(component.formErrors)
        expect(component.formErrors['volume']).toEqual('O campo Volume é obrigatório');

    });

    it('[FALHA] DEVERIA EXIBIR MENSAGEM DE OBRIGATORIEDADE DE ID', () => {
        component.isCordao = true;
        component.doadorForm.get('quantidadeTotalTCN').setValue("2321");
        component.doadorForm.get('quantidadeTotalCD34').setValue("1322");
        component.doadorForm.get('volume').setValue("2321");
        // component.doadorForm.get('id').setValue("2321");
        component.doadorForm.get('abo').setValue("A+");
        component.doadorForm.get('sexo').setValue("M");
        component.doadorForm.get('origem').setValue("1");
        component.doadorForm.get('pagamento').setValue("1");

        // component.validateForm();
        component.salvar();
        console.log(component.formErrors)
        expect(component.formErrors['id']).toEqual('O campo ID é obrigatório');

    });

    it('[FALHA] DEVERIA EXIBIR MENSAGEM DE OBRIGATORIEDADE DE SEXO', () => {
        component.isCordao = true;
        component.doadorForm.get('quantidadeTotalTCN').setValue("2321");
        component.doadorForm.get('quantidadeTotalCD34').setValue("1322");
        component.doadorForm.get('volume').setValue("2321");
        component.doadorForm.get('id').setValue("2321");
        component.doadorForm.get('abo').setValue("A+");
        // component.doadorForm.get('sexo').setValue("M");
        component.doadorForm.get('origem').setValue("1");
        component.doadorForm.get('pagamento').setValue("1");

        // component.validateForm();
        component.salvar();
        console.log(component.formErrors)
        expect(component.formErrors['sexo']).toEqual('O campo Sexo é obrigatório');

    });

    it('[FALHA] DEVERIA EXIBIR MENSAGEM DE OBRIGATORIEDADE DE ORIGEM', () => {
        component.isCordao = true;
        component.doadorForm.get('quantidadeTotalTCN').setValue("2321");
        component.doadorForm.get('quantidadeTotalCD34').setValue("1322");
        component.doadorForm.get('volume').setValue("2321");
        component.doadorForm.get('id').setValue("2321");
        component.doadorForm.get('abo').setValue("A+");
        component.doadorForm.get('sexo').setValue("M");
        // component.doadorForm.get('origem').setValue("1");
        component.doadorForm.get('pagamento').setValue("1");

        // component.validateForm();
        component.salvar();
        console.log(component.formErrors)
        expect('O campo Registro de Origem é obrigatório').toEqual(component.formErrors['origem']);

    });


    // it('[SUCESSO] Centro obrigatório', async(() => {
    //     component.resultadoForm.get("centro").setValue(1);
    //     component.validateForm();
    //     expect(component.formErrors['centro']).toEqual('');
    // }));


});
