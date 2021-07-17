import { ArquivoVoucherLogisticaService } from '../../../../shared/service/arquivo.voucher.logistica.service';
import { ArquivoVoucherLogistica } from '../../../../shared/model/arquivo.voucher.logistica';
import { ErroMensagem } from '../../../../shared/erromensagem';
import { Input } from '@angular/core/src/metadata/directives';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { DominioService } from '../../../../shared/dominio/dominio.service';
import { TranslateService } from '@ngx-translate/core';
import { Configuracao } from '../../../../shared/dominio/configuracao';
import { FileUploader, FileUploaderOptions, FileItem, FileLikeObject } from 'ng2-file-upload';
import { Component, OnInit, Output, EventEmitter, ViewChild, ElementRef } from '@angular/core';
import { BaseForm } from '../../../../shared/base.form';
import { TiposVoucher } from '../../../../shared/enums/tipos.voucher';
import { WorkupService } from 'app/doador/consulta/workup/workup.service';
import { PedidoLogistica } from 'app/shared/model/pedido.logistica';
import { LogisticaService } from 'app/shared/service/logistica.service';

/**
 * Classe que representa o componente para arquvios de voucher para o pedido de logistica.
 * @author Bruno Sousa
 */
@Component({
    selector: "voucher",
    templateUrl: './arquivo.voucher.logistica.component.html'
})
export class ArquivoVoucherLogisticaComponent extends BaseForm<Object> implements OnInit {

    @Output()
    public eventoCancelar: EventEmitter<String> = new EventEmitter<String>();

    @Output()
    public eventoEnviar: EventEmitter<String> = new EventEmitter<String>();

    @ViewChild("fileInput")
    private fileInput: ElementRef;

    mostraDados: string = '';
    mostraFormulario: string = 'hide';

    public uploader: FileUploader;

    // Chaves que envolvem o upload do arquivo de voucher
    private CONFIG_UPLOAD_CHAVES: String[] = ["extensaoArquivoPedidoLogistica", "tamanhoArquivoPedidoLogisticaEmByte"];
    // Lista de configurações referente ao upload de arquivo.
    public configuracaoUpload: Configuracao[];

    private customErros: any = {
        tipoArquivo: "",
        tamanhoArquivo: "",
        inserirArquivo: ""
    }

    voucherForm: FormGroup;

    private _fileItem: FileItem;

    private _lista: ArquivoVoucherLogistica[] = [];

    private _listaDownload: ArquivoVoucherLogistica[] = [];


    constructor(translate: TranslateService,  private dominioService: DominioService,
        private fb: FormBuilder,  private workupService: WorkupService,
        private arquivoVoucherLogisticaService: ArquivoVoucherLogisticaService,
        private logisticaService: LogisticaService) {

        super();

        var arquivoVoucherLogisticaComponent: ArquivoVoucherLogisticaComponent = this;

        this.translate = translate;

        this.formErrors['arquivo'] = "";

        this.uploader = new FileUploader({});

        // Carrega a configuração necessária para o upload do arquivo.
        this.dominioService.listarConfiguracoes(this.CONFIG_UPLOAD_CHAVES)
            .then(result => {
                this.configuracaoUpload = result;
                let options: FileUploaderOptions = this.uploader.options;
                this.configuracaoUpload.forEach(configuracao => {
                    if (configuracao.chave == this.CONFIG_UPLOAD_CHAVES[0]) {
                        options.allowedMimeType = configuracao.valor.split(',');
                    }
                    else if (configuracao.chave == this.CONFIG_UPLOAD_CHAVES[1]) {
                        options.maxFileSize = Number.parseInt(configuracao.valor);
                    }
                });
                this.translate.get("mensagem.erro.tamanhoArquivo", {maxFileSize: this.formatBytes(this.configuracaoUpload.find(configuracao => configuracao.chave == this.CONFIG_UPLOAD_CHAVES[1]).valor )}).subscribe(res => {
                    this.customErros['tamanhoArquivo'] = res;
                });
                this.uploader.setOptions(options);

            }, (error: ErroMensagem) => {
                // TODO: Durante a implementação do formulário, tratar mensagem de erro!
            }
        );

        this.uploader.onAfterAddingFile = function (fileItem: FileItem): any {

            arquivoVoucherLogisticaComponent.clearMensagensErro(arquivoVoucherLogisticaComponent.voucherForm);
            delete arquivoVoucherLogisticaComponent.formErrors['arquivo'];
            arquivoVoucherLogisticaComponent._fileItem = fileItem;
            arquivoVoucherLogisticaComponent.voucherForm.get("filename").setValue(fileItem.file.name);
        };

        this.uploader.onWhenAddingFileFailed = function (item: FileLikeObject, filter: any, options: any): any {

            arquivoVoucherLogisticaComponent.clearMensagensErro(arquivoVoucherLogisticaComponent.voucherForm);
            arquivoVoucherLogisticaComponent._fileItem = null;
            arquivoVoucherLogisticaComponent.voucherForm.get("filename").setValue("");
            arquivoVoucherLogisticaComponent.formErrors["filename"] = "";
            arquivoVoucherLogisticaComponent.markAsInvalid(arquivoVoucherLogisticaComponent.form().get("filename"));
            switch (filter.name) {
                case 'queueLimit':
                    //exameComponent.formErrors['arquivo'] = exameComponent.customErros['quantidadeArquivoLaudo'];
                    break;
                case 'fileSize':
                    let maxFileSize: number = 0;
                    arquivoVoucherLogisticaComponent.configuracaoUpload.forEach(configuracao => {
                        if (configuracao.chave == arquivoVoucherLogisticaComponent.CONFIG_UPLOAD_CHAVES[1]) {
                            maxFileSize = Number.parseInt(configuracao.valor);
                        }
                    });
                    arquivoVoucherLogisticaComponent.formErrors['arquivo'] = arquivoVoucherLogisticaComponent.customErros["tamanhoArquivo"];
                    break;
                case 'mimeType':
                    arquivoVoucherLogisticaComponent.formErrors['arquivo'] = arquivoVoucherLogisticaComponent.customErros["tipoArquivo"];
                    break;
                default:
                    arquivoVoucherLogisticaComponent.formErrors['arquivo'] = arquivoVoucherLogisticaComponent.customErros["inserirArquivo"];
                    break;
            }
        };
        this.buildForm(null, null);
    }

    ngOnInit() {

        this.translate.get("workup.logistica.voucherForm").subscribe(res => {
            this._formLabels = res;
            this.criarMensagensErro(this.voucherForm);
        });
        this.translate.get("mensagem.erro").subscribe(res => {
            this.customErros["tipoArquivo"] = res.tipoArquivo;
            this.customErros['inserirArquivo'] = res.inserirArquivo;
        });

    }

     /**
     * Constrói os FormControl dos campos obrigatorios
     * @param FormBuilder para preencher os campos com suas respectivas validações
     * @author Bruno Sousa
     */
    buildForm(pedidoLogisticaId: number, tipo: TiposVoucher) {
        this.voucherForm = this.fb.group({
            'pedidoLogisticaId': [pedidoLogisticaId, null],
            'tipo': [tipo, null],
            'filename': [null, Validators.required],
            'comentario': null
        });
    }

    public incluirVoucher(pedidoLogisticaId: number, tipo: TiposVoucher) {
        this._fileItem = null;
        this.buildForm(pedidoLogisticaId, tipo);
        this.uploader.clearQueue();
        this.fileInput.nativeElement.value = '';
        this.mostraDados = "hide";
        this.mostraFormulario = "";

    }

    cancelarEdicao() {
        this.mostraDados = "";
        this.mostraFormulario = "hide";
        if(this.eventoCancelar.observers.length > 0){
            this.eventoCancelar.emit();
        }
    }

    salvar() {
        let valid: boolean = this.validateForm();
        if (valid) {
            let data: FormData = new FormData();
            data.append("file", this._fileItem._file, this._fileItem.file.name);
            this.logisticaService.incluirArquivoVoucherLogistica(data, this.form().get('pedidoLogisticaId').value)
                .then(res => {
                    let arquivoVoucherLogistica: ArquivoVoucherLogistica = new ArquivoVoucherLogistica();
                    arquivoVoucherLogistica.caminho = res;
                    arquivoVoucherLogistica.comentario = this.form().get('comentario').value;
                    arquivoVoucherLogistica.excluido = false;
                    arquivoVoucherLogistica.tipo = this.form().get('tipo').value;
                    arquivoVoucherLogistica.pedidoLogistica = new PedidoLogistica();
                    arquivoVoucherLogistica.pedidoLogistica.id = this.form().get('pedidoLogisticaId').value;

                    this._lista.push(arquivoVoucherLogistica);

                    this.mostraDados = "";
                    this.mostraFormulario = "hide";

                    if(this.eventoEnviar.observers.length > 0){
                        this.eventoEnviar.emit();
                    }

                })
                .catch(error => {
                    console.log(error);
                });
        }

    }

    private excluir(arquivoVoucherLogistica: ArquivoVoucherLogistica) {
        let index = arquivoVoucherLogistica.caminho.lastIndexOf("/");
        let nomeArquivo: string =  arquivoVoucherLogistica.caminho.substring(index + 1, arquivoVoucherLogistica.caminho.length);

        this.logisticaService.excluirArquivoVoucherLogistica(nomeArquivo, arquivoVoucherLogistica.pedidoLogistica.id)
            .then(res => {
                this._lista = this._lista.filter(voucher => voucher.caminho != arquivoVoucherLogistica.caminho );
            })
            .catch(error => {

            });


    }

    private formatBytes(bytes, decimals?) {
        if (bytes == 0) return '0 Bytes';
        const k = 1024,
            dm = decimals || 2,
            sizes = ['Bytes', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'],
            i = Math.floor(Math.log(bytes) / Math.log(k));
        return parseFloat((bytes / Math.pow(k, i)).toFixed(dm)) + ' ' + sizes[i];
    }

    public form(): FormGroup {
        return this.voucherForm;
    }

    public preencherFormulario(entidade: Object): void {
        throw new Error("Method not implemented.");
    }

    nomeComponente(): string {
        throw new Error("Method not implemented.");
    }

    public get lista(): ArquivoVoucherLogistica[] {
        return this._lista;
    }

	public set listaDownload(value: ArquivoVoucherLogistica[] ) {
		this._listaDownload = value;
    }

    public get listaDownload(): ArquivoVoucherLogistica[] {
        return this._listaDownload;
    }

    public removerTimestamp(caminho: string): string {
        let index = caminho.lastIndexOf("_");
        return caminho.substring(index + 1, caminho.length);
	}

    public baixarArquivo(arquivoPrescricao: ArquivoVoucherLogistica) {
        this.arquivoVoucherLogisticaService.baixarArquivoVoucher(arquivoPrescricao.id, this.removerTimestamp(arquivoPrescricao.caminho));
    }

}
