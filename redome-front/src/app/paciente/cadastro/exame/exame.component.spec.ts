import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { FileItem, FileUploader } from 'ng2-file-upload';
import { Observable } from "rxjs";
import { dominioService, laboratorioService } from '../../../export.mock.spec';
import { ExameComponent } from './exame.component';

/**
 * Classe para teste da Classe de ExameComponent
 * @author Filipe Paes
 */
describe('ExameComponent', () => {
    let fixture: ComponentFixture<ExameComponent>;
    let component: ExameComponent

    beforeEach((done) => (async () => { 
        dominioService.listarConfiguracoes = function (lista: String[]): Promise<any> {
            let _RES_CONFIGURACAO = [
                {
                    chave: 'extensaoArquivoLaudo',
                    valor: 'image/png,image/jpeg,image/bmp,image/tiff,image/jpg'
                },
                {
                    chave: 'tamanhoArquivoLaudoEmByte',
                    valor: '5242880'
                },
                {
                    chave: 'qtdArquivosLaudo',
                    valor: '5'
                }
            ];
            return Observable.of(this._RES_CONFIGURACAO).toPromise();
        }

        laboratorioService.listarLaboratorios = function(): Promise<any> {
            let retorno = {
                content: [{
                  id: "1",
                  nome: "Nome do laboratório"
                }]
            }

            return Observable.of(retorno).toPromise();
        }

        fixture = TestBed.createComponent(ExameComponent);
        component = fixture.debugElement.componentInstance;

        await component.ngOnInit();

    })().then(done).catch(done.fail));

    afterEach(() => {
        fixture = undefined;
        component = undefined;
    });

    /**
   * Verifica se o componente de exame foi instanciado com sucesso
   * @author Fillipe Queiroz
   */
    it('deveria criar o ExameComponent', () => {
        expect(component).toBeTruthy();
    });

    it('deve exibir mensagem de obrigatoriedade caso o laboratorio esteja vazio e laboratorio particular é falso', async(() => {
        component.exameForm.controls["laboratorio"].setValue(null);
        component.exameForm.controls["laboratorioParticular"].setValue(false);
        component.validateForm();
        expect(component.formErrors['laboratorio']).toEqual('O campo Laboratório é obrigatório');
    }));

    it('Nao deve exibir mensagem de obrigatoriedade caso o laboratorio esteja vazio e laboratorio particular é true', async(() => {
        component.exameForm.controls["laboratorio"].setValue(null);
        component.exameForm.controls["laboratorioParticular"].setValue(true);
        component.clickLaboratorioParticular({target: {checked: true}});
        component.validateForm();
        expect(component.formErrors['laboratorio']).toEqual('');
    }));

    it('deve exibir mensagem de obrigatoriedade caso a data da coleta esteja vazia', async(() => {
        component.exameForm.controls["dataColetaAmostra"].setValue(null);
        component.validateForm();
        expect(component.formErrors['dataColetaAmostra']).toEqual('O campo Data da Coleta é obrigatório');
    }));

    it('não deve exibir mensagem de obrigatoriedade caso a data da coleta esteja preenchida', async(() => {
        component.exameForm.controls["dataColetaAmostra"].setValue("05/05/2017");
        component.validateForm();
        expect(component.formErrors['dataColetaAmostra']).toEqual('');
    }));

    it('deve exibir mensagem de obrigatoriedade caso a data exame esteja vazia', async(() => {
        component.exameForm.controls["dataExame"].setValue(null);
        component.validateForm();
        expect(component.formErrors['dataExame']).toEqual('O campo Data de Exame é obrigatório');
    }));

    it('não deve exibir mensagem de obrigatoriedade caso a data exame esteja preenchida', async(() => {
        component.exameForm.controls["dataExame"].setValue("05/05/2017");
        component.validateForm();
        expect(component.formErrors['dataExame']).toEqual('');
    }));

    it('deve exibir mensagem caso a data coleta seja maior que a data do exame', async(() => {
        component.exameForm.controls["dataColetaAmostra"].setValue("10/05/2018");
        component.exameForm.controls["dataExame"].setValue("01/05/2018");
        component.validateForm();
        expect(component.formErrors['dataColetaAmostra']).toEqual('Data da Coleta deve ser menor que a Data de Exame');
    }));

    it('deve exibir mensagem de obrigatoriedade caso a metodologia não seja selecionada', async(() => {
        
        component.validateForm();
        expect(component.formErrors["metodologia"]).toEqual('Selecione ao menos uma metodologia');
    }));

    it('não deve exibir mensagem de obrigatoriedade caso a metodologia seja selecionada', async(() => {
            component.listMetodologiasForm.controls[0].get('checked').setValue(true);
            component.validateForm();
            expect(component.formErrors["metodologia"]).toEqual('');
    }));

    it('deve exibir mensagem de obrigatoriedade caso o laudo não seja selecionado', async(() => {
        component.validateForm();
        expect(component.formErrors["nenhumLaudoSelecionado"]).toEqual('É necessário selecionar um arquivo de laudo.');
    }));

    /**
    *   Utilizado Blob como file por conta do PhantomJS que não aceita o construtor
    *       new File([], "teste.jpg", {})
    *
    *   Linha Substituida: component.uploader.queue = [new FileItem(component.uploader, new File([], "teste.jpg", {}), {})];
    *   Por: component.uploader.queue = [new FileItem(component.uploader, <File>file, {})];
    */
    it('não deve exibir mensagem de obrigatoriedade caso a laudo seja selecionada', async(() => {

        let file = new Blob(['']);
        file['name'] = "teste.jpg";

        let uploader: FileUploader = new FileUploader({});
        component.iniciarPorTelaCadastro = false;
        component.uploader.queue = [new FileItem(component.uploader, <File>file, {})];
        component.uploader.onAfterAddingFile(new FileItem(uploader, <File>file, {}));
        component.validateForm();
        expect(component.formErrors["nenhumLaudoSelecionado"]).toEqual('');
    }));
});