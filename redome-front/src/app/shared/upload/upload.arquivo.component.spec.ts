import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { FileItem, FileUploader } from 'ng2-file-upload';
import { Observable } from "rxjs";
import { UploadArquivoComponent } from './upload.arquivo.component';
import { dominioService } from '../../export.mock.spec';

/**
 * Classe para teste da Classe de UploadArquivoComponent.
 * @author Pizão
 */
describe('UploadArquivoComponent', () => {
    let fixture: ComponentFixture<UploadArquivoComponent>;
    let component: UploadArquivoComponent;

    beforeEach((done) => (async () => {
        dominioService.listarConfiguracoes = function (lista: String[]): Promise<any> {
            let _RES_CONFIGURACAO = [
                {
                    chave: 'extensaoArquivoLaudo',
                    valor: 'image/pdf'
                },
                {
                    chave: 'tamanhoArquivoLaudoEmByte',
                    valor: '5242880'
                },
                {
                    chave: 'quantidadeMaximaArquivosPedidoTransporte',
                    valor: '2'
                }
            ];

            return Observable.of(_RES_CONFIGURACAO).toPromise();
        }

        

        fixture = TestBed.createComponent(UploadArquivoComponent);
        component = fixture.debugElement.componentInstance;
        component.extensoes = "extensaoArquivoLaudo";
        component.tamanhoLimite = "tamanhoArquivoLaudoEmByte";
        component.quantidadeMaximaArquivos = "quantidadeMaximaArquivosPedidoTransporte";
        await component.ngAfterViewInit();

    })().then(done).catch(done.fail));

    afterEach(() => {
        fixture = undefined;
        component = undefined;
    });

    it('deveria instanciar o UploadArquivoComponent', () => {
        expect(component).toBeTruthy();
    });

    it( 'não deve exibir mensagem de obrigatoriedade caso a seleção para upload seja obrigatória e, ' + 
        'ao menos, um arquivo seja selecionado', async(() => {

        let file = new Blob(['']);
        file['name'] = "teste.jpg";

        let uploader: FileUploader = new FileUploader({});
        component.uploader.queue = [new FileItem(component.uploader, <File>file, {})];
        component.uploader.onAfterAddingFile(new FileItem(uploader, <File>file, {}));
        component.validateForm();
        expect(component.formErrors["arquivo"]).toEqual(null);
        expect(component.arquivos.size).toEqual(1);
        expect(component.arquivos.get("teste.jpg").file.name).toEqual(file["name"]);
    }));

});