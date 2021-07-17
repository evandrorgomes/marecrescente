import { Component, ViewChild, ElementRef } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { FileItem, FileLikeObject, FileUploader, FileUploaderOptions } from 'ng2-file-upload';
import { WorkupService } from '../../doador/consulta/workup/workup.service';
import { BaseForm } from '../base.form';
import { Configuracao } from '../dominio/configuracao';
import { DominioService } from '../dominio/dominio.service';
import { ErroMensagem } from '../erromensagem';
import { MessageBox } from '../modal/message.box';
import { ErroUtil } from '../util/erro.util';

/**
 * Classe que separa o comportamento de upload do arquivo.
 * @author Pizão.
 */
@Component({
    selector: "upload-arquivo-component",
    templateUrl: './upload.arquivo.component.html'
})
export class UploadArquivoComponent extends BaseForm<Object> {

    public uploader: FileUploader;

    // Chaves contendo as extensões na configuração de cada opção.
    public extensoes: string;
    private extensoesValor: string[];

    // Chaves contendo as extensões na configuração de cada opção.
    public tamanhoLimite: string;
    private _quantidadeMaximaArquivos: string;

    // Executa método de envio após selecionamento do arquivo
    public enviarArquivoAposSelecionamento:Boolean = false;

    public enviarArquivo: (data: FormData) => void;
    private tamanhoLimiteValor: number;

    // Lista de configurações referente ao upload de arquivo.
    public configuracaoUpload: Configuracao[];
    private tipoArquivoIncorreto: string;
    private quantidadeArquivos:number;
    private tamanhoArquivoExcedido: string;
    private quantidadeArquivoExcedido: string;
    private erroAoInserirArquivo: string;
    private nenhumArquivoSelecionado: string;

    // Formulário que possui o campo de texto que exibe o nome do arquivo selecionado.
    public formGroup: FormGroup = new FormGroup({});

    @ViewChild("fileInput")
    private fileInput: ElementRef;

    public  mensagemClass: string = "form-group col-sm-12 col-xs-12";

    /**
     * Guarda os arquivos selecionados para upload.
     */
    private _arquivos: Map<string, FileItem> = new Map<string, FileItem>();

    // Indica que no mínimo um arquivo deve ser selecionado.
    private _uploadObrigatorio: boolean = true;



    constructor(translate: TranslateService,  private dominioService: DominioService,
        private fb: FormBuilder,  private workupService: WorkupService, private messageBox: MessageBox) {

        super();

        this.mensagemErro = null;

        this.formGroup = this.fb.group({
            'filename': null,
        });

        this.translate = translate;
        this.uploader = new FileUploader({});

        this.translate.get("mensagem.erro").subscribe(res => {
            this.tipoArquivoIncorreto = res.tipoArquivo;
            this.erroAoInserirArquivo = res.inserirArquivo;
            this.nenhumArquivoSelecionado = res.nenhumArquivoSelecionado;
        });



        this.uploader.onAfterAddingFile = (fileItem) => {
            let temItem: boolean = this.uploader.queue.some(fileItemExistente => fileItemExistente.file.name == fileItem.file.name);
            if(this.enviarArquivo){
                let data: FormData = new FormData();
                data.append("file", fileItem._file, fileItem.file.name);
                this.enviarArquivo(data);
            }

            if (temItem) {
                let files:FileItem[] = this.uploader.queue.filter(fileItemExistente => fileItemExistente.file.name == fileItem.file.name);
                if (files.length > 1) {
                    let index = this.uploader.queue.indexOf(files[0]);
                    this.uploader.queue.splice(index, 1);
                    return;
                }
            }
            this.mensagemErro = null;
            if (!this._quantidadeMaximaArquivos || this.quantidadeArquivos == 1) {
                this._arquivos.clear();
            } else if (this._arquivos.size >= this.quantidadeArquivos) {
                this.translate.get("mensagem.erro.quantidadeArquivoLaudo").subscribe(res => {
                    this.quantidadeArquivoExcedido = res;
                    this.mensagemErro = res;
                    this._arquivos.delete(fileItem.file.name);
                });
            }
            this._arquivos.set(fileItem.file.name, fileItem);
            this.setPropertyValue("filename", fileItem.file.name);
        };

        this.uploader.onWhenAddingFileFailed = (file: FileLikeObject, filter: any, options: any) => {
            // if(this._arquivos.get(file.name)){

            //     this._arquivos.delete(file.name);
            // }
            this.setPropertyValue("filename", null);
            this.formErrors["filename"] = "";
            this.markAsInvalid(this.form().get("filename"));

            switch (filter.name) {
                case 'queueLimit':
                    this.mensagemErro = this.quantidadeArquivoExcedido;
                    break;
                case 'fileSize':
                    this.mensagemErro = this.tamanhoArquivoExcedido;
                    break;
                case 'mimeType':
                    this.mensagemErro = this.tipoArquivoIncorreto;
                    break;
                default:
                    this.mensagemErro = this.erroAoInserirArquivo;
                    break;
            }
        };
    }

    ngAfterViewInit(): void {
        // Carrega a configuração necessária para o upload do arquivo.
        let configuracoes: string[] = this.obterItensChaveConfiguracao();

        this.dominioService.listarConfiguracoes(configuracoes)
            .then(result => {
                this.configuracaoUpload = <Configuracao[]> result;
                let options: FileUploaderOptions = this.uploader.options;

                let configuracaoExtensoes: string =
                    this.obterConfiguracao(this.configuracaoUpload, this.extensoes);
                this.extensoesValor = configuracaoExtensoes.split(',');
               // options.allowedMimeType = this.extensoesValor;

                let configuracaoTamanhoLimite: string =
                    this.obterConfiguracao(this.configuracaoUpload, this.tamanhoLimite);
                if(configuracaoTamanhoLimite){
                this.tamanhoLimiteValor = Number.parseInt(configuracaoTamanhoLimite);
                options.maxFileSize = this.tamanhoLimiteValor;
                }

                let configuracaoQtdArquivos: string =
                    this.obterConfiguracao(this.configuracaoUpload, this._quantidadeMaximaArquivos);
                if(configuracaoQtdArquivos){
                    this.quantidadeArquivos = Number.parseInt(configuracaoQtdArquivos);
                    options.queueLimit = this.quantidadeArquivos;
                }

                let excedeuLimiteTamanhoMensagem: string = this.formatarExibicaoTamanho(this.tamanhoLimiteValor);
                this.translate.get( "mensagem.erro.tamanhoArquivo", {maxFileSize: excedeuLimiteTamanhoMensagem}).subscribe(res => {
                    this.tamanhoArquivoExcedido = res;
                });

                this.translate.get("mensagem.erro.quantidadeArquivoLaudo").subscribe(res => {
                    this.quantidadeArquivoExcedido = res;
                });

                this.uploader.setOptions(options);

            }, (error: ErroMensagem) => {
                this.messageBox.alert(ErroUtil.extrairMensagensErro(error)).show();
            }
        );
    }

    public removerArquivo(arquivo:any){
        this._arquivos.delete(arquivo);
        this.fileInput.nativeElement.value = '';
        this.setPropertyValue("filename", null);
        this.formErrors["filename"] = "";
        this.mensagemErro = "";

        let fileItem = this.uploader.queue.filter(fileItem=> fileItem.file.name == arquivo)[0];
        let index = this.uploader.queue.indexOf(fileItem);
        this.uploader.queue.splice(index,1);
    }


    /**
     * Obtém a configuração de mesma chave dentre as configurações informadas.
     *
     * @param configuracoes
     * @param chave
     */
    private obterConfiguracao(configuracoes: Configuracao[], chave: string): string{
        let configuracaoEncontrada: Configuracao;
        this.configuracaoUpload.forEach(configuracao => {
            if(configuracao.chave == chave){
                configuracaoEncontrada = configuracao;
            }
        });
        if(configuracaoEncontrada == null){
            return null;
        }
        return configuracaoEncontrada.valor;
    }

    /**
     * Obtém a lista de chaves das configurações
     * a ser carregadas do back-end.
     * @return lista de chaves que deverão ser buscadas.
     */
    private obterItensChaveConfiguracao(): string[]{
        let configuracoes: string[] = [];
        configuracoes.push(this.extensoes);

        if(this.tamanhoLimite){
            configuracoes.push(this.tamanhoLimite);
        }

        if(this.quantidadeMaximaArquivos){
            configuracoes.push(this.quantidadeMaximaArquivos);
        }

        return configuracoes;
    }

    /**
     * Formata a exibição do tamanho para ser utilizado na mensagem
     * de erro quando o arquivo excedo o tamanho limite.
     *
     * @param bytes
     * @param decimals
     */
    private formatarExibicaoTamanho(bytes: number, decimals?): string {
        if (bytes == 0) return '0 Bytes';
        const k = 1024,
            dm = decimals || 2,
            sizes = ['Bytes', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'],
            i = Math.floor(Math.log(bytes) / Math.log(k));
        return parseFloat((bytes / Math.pow(k, i)).toFixed(dm)) + ' ' + sizes[i];
    }

    public form(): FormGroup {
        return this.formGroup;
    }

    public preencherFormulario(entidade: Object): void {
        throw new Error("Method not implemented.");
    }

    nomeComponente(): string {
        throw new Error("Method not implemented.");
    }

    public get arquivos(): Map<string, FileItem> {
        return this._arquivos;
    }

    public set arquivos(arquivos: Map<string, FileItem>){
        this._arquivos = arquivos;
    }

    /**
     * Retorna o boolean indicando se é obrigatória a seleção.
     * @return {boolean}
     */
	public get uploadObrigatorio(): boolean {
		return this._uploadObrigatorio;
    }

    public set uploadObrigatorio(value: boolean) {
		this._uploadObrigatorio = value;
    }

    public validateForm(): boolean{
        if(!this._uploadObrigatorio || (this._uploadObrigatorio && this._arquivos.size > 0)){
            this.mensagemErro = null;
            return true;
        }
        this.mensagemErro = this.nenhumArquivoSelecionado;
        return false;
    }

    public set mensagemErro(mensagem: string){
        this.formErrors['arquivo'] = mensagem;
    }

    getKeys(map){
        return Array.from(map.keys());
    }

    /**
     * Getter quantidadeMaximaArquivos
     * @return {string}
     */
	public get quantidadeMaximaArquivos(): string {
		return this._quantidadeMaximaArquivos;
	}

    /**
     * Setter quantidadeMaximaArquivos
     * @param {string} value
     */
	public set quantidadeMaximaArquivos(value: string) {
		this._quantidadeMaximaArquivos = value;
    }

    public clear() {
        this.clearForm();
        this.arquivos.clear();
        this.uploader.clearQueue();
    }

}
