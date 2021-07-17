import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { FileItem, FileLikeObject, FileUploader, FileUploaderOptions } from 'ng2-file-upload';
import { Subscription } from "rxjs";
import { Arquivo } from '../../../../paciente/cadastro/exame/arquivo';
import { BaseForm } from '../../../../shared/base.form';
import { GroupRadioComponent } from "../../../../shared/component/inputcomponent/group.radio.component";
import { Configuracao } from '../../../../shared/dominio/configuracao';
import { DominioService } from '../../../../shared/dominio/dominio.service';
import { ErroMensagem } from '../../../../shared/erromensagem';
import { HistoricoNavegacao } from '../../../../shared/historico.navegacao';
import { MessageBox } from '../../../../shared/modal/message.box';
import { ErroUtil } from '../../../../shared/util/erro.util';
import { RouterUtil } from '../../../../shared/util/router.util';
import { HeaderDoadorComponent } from '../../../consulta/header/header.doador.component';
import { ResultadoWorkupService } from '../../../consulta/workup/resultado/resultadoworkup.service';
import { ArquivoResultadoWorkup } from './arquivo.resultado.workup';
import { ResultadoWorkup } from './resultado.workup';


/**
 * Classe de component com métodos de funcionalidades de tela da parte de resultado de exame de workup.
 * @author Filipe Paes
 */
@Component({
    selector: "resultado-workup-internacional",
    moduleId: module.id,
    templateUrl: "./cadastro.resultado.workup.internacional.component.html"
})
export class CadastroResultadoWorkupInternacionalComponent extends BaseForm<Object> implements OnInit, OnDestroy {

    @ViewChild('headerDoador')
    private headerDoador: HeaderDoadorComponent;

    @ViewChild('grColetaViavel')
    private grColetaViavel: GroupRadioComponent;

    @ViewChild('grDoadorIndisponivel')
    private grDoadorIndisponivel: GroupRadioComponent;

    public cadastrarForm: FormGroup;
    //private arquivos: Arquivo[] = [];
    private CONFIG_UPLOAD_CHAVES: String[] = ["extensaoArquivoResultadoWorkup"
        , "tamanhoArquivoResultadoWorkupEmByte"
        , "quantidadeMaximaArquivosResultadoWorkup"];
    public configuracaoUpload: Configuracao[];
    public uploader: FileUploader;

    private customErros: any = {
        tipoArquivoLaudo: "",
        quantidadeArquivoLaudo: "",
        tamanhoArquivoLaudo: "",
        inserirLaudo: "",
        nenhumLaudoSelecionado: "",
    }
    private _idPedidoWorkup: number;
    private _caletaInviavelChange: Subscription;

    public opcoesSimNao:String[] = ["false","true"];
    public labelsSimNao:String[] =[];

    constructor(private fb: FormBuilder
        , translate: TranslateService
        , private resultadoWorkupService: ResultadoWorkupService
        , private dominioService: DominioService
        , private messageBox: MessageBox
        , private activatedRouter: ActivatedRoute
        , private router: Router) {
        super();

        this.translate = translate;
        this.buildForm();

    }

    ngOnInit(): void {
        this.inicializarComponenteUpload();

        this.translate.get(["textosGenericos.sim", "textosGenericos.nao"]).subscribe(res => {
            this.labelsSimNao[0] = res['textosGenericos.nao'];
            this.labelsSimNao[1] = res['textosGenericos.sim'];
        });

        this.translate.get("workup.resultadoworkup").subscribe(res => {
            this._formLabels = res;
            this.criarMensagemValidacaoPorFormGroup(this.cadastrarForm);
            this.setMensagensErro(this.cadastrarForm);
        });

        this.translate.get("mensagem.erro").subscribe(res => {
            this.customErros["tipoArquivoLaudo"] = res.tipoArquivoLaudo;
            this.customErros['quantidadeArquivoLaudo'] = res.quantidadeArquivoLaudo;
            this.customErros['inserirLaudo'] = res.inserirLaudo;
            this.customErros['nenhumLaudoSelecionado'] = res.nenhumLaudoSelecionado;
        });

        RouterUtil.recuperarParametrosDaActivatedRoute(this.activatedRouter, ["idPedidoWorkup", "idDoador"]).then(res => {
            this._idPedidoWorkup = <number>res['idPedidoWorkup'];
            this.headerDoador.popularCabecalhoIdentificacaoNoCache(<number>res['idDoador']);
        });

    }

    ngOnDestroy() {
        this._caletaInviavelChange.unsubscribe();

        super.ngOnDestroy();
    }

    inicializarComponenteUpload(): void {
        let resultadoWorkupComponent: CadastroResultadoWorkupInternacionalComponent = this;
        this.formErrors['tipoArquivoLaudo'] = "";
        this.formErrors['quantidadeArquivoLaudo'] = "";
        this.formErrors['tamanhoArquivoLaudo'] = "";
        this.formErrors['inserirLaudo'] = "";
        this.formErrors['nenhumLaudoSelecionado'] = "";
        this.formErrors['metodologia'] = "";

        this.uploader = new FileUploader({});

        // Carrega a configuração necessária para o upload do arquivo.
        this.dominioService.listarConfiguracoes(this.CONFIG_UPLOAD_CHAVES)
            .then(result => {
                this.configuracaoUpload = result;
                let options: FileUploaderOptions = this.uploader.options;
                this.configuracaoUpload.forEach(configuracao => {
                    if (configuracao.chave == this.CONFIG_UPLOAD_CHAVES[0]) {
                        //options.allowedMimeType = configuracao.valor.split(',');
                    }
                    else if (configuracao.chave == this.CONFIG_UPLOAD_CHAVES[2]) {
                        options.queueLimit = Number.parseInt(configuracao.valor);
                    }
                    else if (configuracao.chave == this.CONFIG_UPLOAD_CHAVES[1]) {
                        options.maxFileSize = Number.parseInt(configuracao.valor);
                    }
                });
                this.translate.get("mensagem.erro.tamanhoArquivoLaudo", {
                    maxFileSize: this.formatBytes(this.configuracaoUpload.find(configuracao => configuracao.chave == this.CONFIG_UPLOAD_CHAVES[1]).valor)
                }).subscribe(res => {
                    this.customErros['tamanhoArquivoLaudo'] = res;
                });
                this.uploader.setOptions(options);

            }, (error: ErroMensagem) => {
                // TODO: Durante a implementação do formulário, tratar mensagem de erro!
            });


        this.uploader.onAfterAddingFile = function (fileItem: FileItem): any {

            fileItem.remove();
            if (resultadoWorkupComponent.uploader.queue.filter(f => f._file.name == fileItem._file.name).length == 0) {
                resultadoWorkupComponent.uploader.queue.push(fileItem);
            }
            else {
                resultadoWorkupComponent.form().controls["upload"].reset(null);
                resultadoWorkupComponent.form().controls["upload"].updateValueAndValidity();
                return
            }

            delete resultadoWorkupComponent.formErrors['arquivo'];
            let data: FormData = new FormData();

            data.append("file", fileItem._file, fileItem.file.name);

            data.append("idPedidoWorkup", new Blob([resultadoWorkupComponent._idPedidoWorkup], {
                 type: 'application/json'
            }), '');

            resultadoWorkupComponent
                .resultadoWorkupService
                .salvarArquivo(data).then(res => {
                    let arquivo: Arquivo = new Arquivo();
                    arquivo.fileItem = fileItem;
                    arquivo.nome = res.stringRetorno;
                    if (arquivo.nome) {
                        let primeiroIndex = arquivo.nome.indexOf("_");
                        let index: number = resultadoWorkupComponent.qualquerIndex(arquivo.nome, "_", 2);
                        arquivo.nomeSemTimestamp = arquivo.nome.substring(index + 1, arquivo.nome.length);
                    }
                    let itens: FormArray = resultadoWorkupComponent.cadastrarForm.get('arquivos') as FormArray;
                    itens.push(resultadoWorkupComponent.criarItemArquivo(arquivo.nomeSemTimestamp, arquivo.nome));
                    resultadoWorkupComponent.criarMensagemValidacaoPorFormGroup(resultadoWorkupComponent.cadastrarForm);
                    resultadoWorkupComponent.form().controls["upload"].reset(null);
                    resultadoWorkupComponent.form().controls["upload"].updateValueAndValidity();

                }, (error: ErroMensagem) => {
                    resultadoWorkupComponent.alterarMensagem.emit(error.mensagem);
                });
        };

        this.uploader.onWhenAddingFileFailed = function (item: FileLikeObject, filter: any, options: any): any {
            resultadoWorkupComponent.clearMensagensErro(resultadoWorkupComponent.cadastrarForm);
            switch (filter.name) {
                case 'queueLimit':
                    resultadoWorkupComponent.formErrors['arquivo'] = resultadoWorkupComponent.customErros['quantidadeArquivoLaudo'];
                    break;
                case 'fileSize':
                    let maxFileSize: number = 0;
                    resultadoWorkupComponent.configuracaoUpload.forEach(configuracao => {
                        if (configuracao.chave == resultadoWorkupComponent.CONFIG_UPLOAD_CHAVES[1]) {
                            maxFileSize = Number.parseInt(configuracao.valor);
                        }
                    });
                    resultadoWorkupComponent.formErrors['arquivo'] = resultadoWorkupComponent.customErros["tamanhoArquivoLaudo"];
                    break;
                case 'mimeType':
                    resultadoWorkupComponent.formErrors['arquivo'] = resultadoWorkupComponent.customErros["tipoArquivoLaudo"];
                    break;
                default:
                    resultadoWorkupComponent.formErrors['arquivo'] = resultadoWorkupComponent.customErros["inserirLaudo"];
                    break;
            }
        };

    }

    private formatBytes(bytes, decimals?) {
        if (bytes == 0) return '0 Bytes';
        const k = 1024,
            dm = decimals || 2,
            sizes = ['Bytes', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'],
            i = Math.floor(Math.log(bytes) / Math.log(k));
        return parseFloat((bytes / Math.pow(k, i)).toFixed(dm)) + ' ' + sizes[i];
    }


    /**
      * Remove o arquivo que foi feito no upload
      *
      * @param {string} nomeArquivo
      * @memberof ExameComponent
      */
    public removerArquivo(caminho: string) {
        this.resultadoWorkupService.removerArquivoResultadoWorkup(this._idPedidoWorkup, caminho)
            .then(res => {
                this.removerArquivoPorNome(res.stringRetorno);
            }, (error: ErroMensagem) => {
                this.alterarMensagem.emit(error.mensagem);
            });
    }

    private removerArquivoPorNome(nomeArquivo: string) {
        for (let i = 0; i < this.uploader.queue.length; i++) {
            let arquivo: FileItem = this.uploader.queue[i];
            let index = this.qualquerIndex(nomeArquivo, "_", 2);
            if (index > 0) {
                let nomeSemTimestamp: string = nomeArquivo.substring(index + 1, nomeArquivo.length);
                if (arquivo._file.name == nomeSemTimestamp) {
                    this.uploader.queue.splice(i, 1);
                    let itens: FormArray = this.cadastrarForm.get('arquivos') as FormArray;
                    itens.removeAt(i);
                }
            }
        }

    }

    buildForm() {
        this.cadastrarForm = this.fb.group({
            'coletaViavel': [null, Validators.required],
            'doadorIndisponivel': [null],
            'upload': null
        });

        this.cadastrarForm.addControl('arquivos', new FormArray([]));

        this._caletaInviavelChange =  this.cadastrarForm.controls['coletaViavel'].valueChanges.subscribe(res => {
            if (res === 'false') {
                this.setFieldRequired(this.cadastrarForm,'doadorIndisponivel');
            }
            else if (res === 'true') {
                this.resetFieldRequired(this.cadastrarForm,'doadorIndisponivel');
                this.cadastrarForm.controls['doadorIndisponivel'].setValue(null);
                this.cadastrarForm.controls['doadorIndisponivel'].updateValueAndValidity();
            }
        });

    }

    criarItemArquivo(nomeArquivo: String = "", nomeArquivoCompleto: String = ""): FormGroup {
        return this.fb.group({
            'nomeArquivo': [nomeArquivo, null],
            'nomeArquivoCompleto': [nomeArquivoCompleto, Validators.required],
            'comentario': null
        });
    }

    public form(): FormGroup {
        return this.cadastrarForm;
    }

    public arquivosFormArray(): FormArray {
        return <FormArray>this.cadastrarForm.get('arquivos');
    }

    public preencherFormulario(entidade: ResultadoWorkup): void {
        throw new Error("Method not implemented.");
    }

    nomeComponente(): string {
        return "CadastroResultadoWorkupInternacionalComponent";
    }

    salvarResultado() {
        if (this.validarFormCadastro()) {

            let resultadoWorkup: ResultadoWorkup = new ResultadoWorkup();

            resultadoWorkup.coletaInviavel = !JSON.parse(this.cadastrarForm.get('coletaViavel').value);
            resultadoWorkup.doadorIndisponivel = false;
            if (resultadoWorkup.coletaInviavel) {
                resultadoWorkup.doadorIndisponivel = JSON.parse(this.cadastrarForm.get('doadorIndisponivel').value);
            }

            let arquivosResultado: ArquivoResultadoWorkup[] = [];

            let arquivosForm = this.cadastrarForm.get('arquivos') as FormArray;
            arquivosForm.controls.forEach(c => {
                let arquivoResultadoObj: ArquivoResultadoWorkup = new ArquivoResultadoWorkup();
                arquivoResultadoObj.caminho = c.get("nomeArquivoCompleto").value;
                arquivoResultadoObj.descricao = c.get("comentario").value;
                arquivosResultado.push(arquivoResultadoObj);
            });

            resultadoWorkup.arquivosResultadoWorkup = arquivosResultado;

            this.resultadoWorkupService.salvarResultado(this._idPedidoWorkup, resultadoWorkup).then(res => {
                this.messageBox.alert(res)
                    .withCloseOption(() => { this.router.navigateByUrl(HistoricoNavegacao.urlRetorno()) })
                    .show();
            }, (error: ErroMensagem) => {
                ErroUtil.exibirMensagemErro(error, this.messageBox)
            });
        }
    }

    maskAsTouchedAndDirty() {
        this.cadastrarForm.controls['coletaViavel'].markAsTouched();
        this.cadastrarForm.controls['coletaViavel'].markAsDirty();
        this.cadastrarForm.controls['coletaViavel'].updateValueAndValidity();

        this.cadastrarForm.controls['doadorIndisponivel'].markAsTouched();
        this.cadastrarForm.controls['doadorIndisponivel'].markAsDirty();
        this.cadastrarForm.controls['doadorIndisponivel'].updateValueAndValidity();

    }


    validarFormCadastro(): boolean {
        this.clearMensagensErro(this.cadastrarForm);
        this.maskAsTouchedAndDirty();
        //this.grColetaViavel.checkErro();
        //this.grDoadorIndisponivel.checkErro();

        let valid: boolean = this.validateFields(this.cadastrarForm);
        let arquivosForm = this.cadastrarForm.get('arquivos') as FormArray;
        if (arquivosForm.controls.length < 1) {
            this.translate.get("workup.resultadoworkup.arquivoObrigatorio").subscribe(res => {
                this.formErrors["arquivo"] = res;
                valid = false;
            });
        }
        this.setMensagensErro(this.cadastrarForm);
        return valid;
    }

    voltarConsulta() {
        this.cadastrarForm.reset();
        this.router.navigateByUrl(HistoricoNavegacao.urlRetorno());
    }

    qualquerIndex(valor: String, strProcura: string, indice: number): number {
        var L = valor.length, i = -1;
        while (indice-- && i++ < L) {
            i = valor.indexOf(strProcura, i);
            if (i < 0) break;
        }
        return i;
    }

    coletaInviavel(): boolean {
        return this.cadastrarForm.controls['coletaViavel'].value !== null && this.cadastrarForm.controls['coletaViavel'].value === 'false' ? true : false;
    }


}
