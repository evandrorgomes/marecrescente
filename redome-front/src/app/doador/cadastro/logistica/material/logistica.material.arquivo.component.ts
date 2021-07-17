import { Component, OnInit, ViewChild } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { HeaderDoadorComponent } from 'app/doador/consulta/header/header.doador.component';
import { DetalheLogisticaMaterialDTO } from "app/shared/dto/detalhe.logistica.material.dto";
import { TiposDoador } from "app/shared/enums/tipos.doador";
import { Modal } from "app/shared/modal/factory/modal.factory";
import { MessageBox } from 'app/shared/modal/message.box';
import { Transportadora } from 'app/shared/model/transportadora';
import { TransportadoraService } from 'app/shared/service/transportadora.service';
import { PedidoTransporteService } from "app/transportadora/pedido.transporte.service";
import { FileItem, FileLikeObject, FileUploader, FileUploaderOptions } from 'ng2-file-upload';
import { Arquivo } from '../../../../paciente/cadastro/exame/arquivo';
import { BaseForm } from "../../../../shared/base.form";
import { Configuracao } from '../../../../shared/dominio/configuracao';
import { DominioService } from '../../../../shared/dominio/dominio.service';
import { ErroMensagem } from '../../../../shared/erromensagem';
import { HistoricoNavegacao } from '../../../../shared/historico.navegacao';
import { MensagemModalComponente } from '../../../../shared/modal/mensagem.modal.component';
import { LogisticaMaterialService } from '../../../../shared/service/logistica.material.service';
import { UploadArquivoComponent } from '../../../../shared/upload/upload.arquivo.component';
import { DateMoment } from '../../../../shared/util/date/date.moment';
import { DateTypeFormats } from '../../../../shared/util/date/date.type.formats';
import { StringUtil } from '../../../../shared/util/string.util';
import { PedidoTransporte } from '../../../../transportadora/tarefas/pedido.transporte';
import { AlteracaoLogisticaMaterialNacionalComponent } from '../alteracao_agendamento/alteracao.logistica.material.nacional.component';


/**
 * Componente de detalhe da logística de material coletado.
 * Ponto onde é informada a data de retirada do material e a transportadora responsável.
 */
@Component({
    selector: 'logistica-material-arquivo',
    templateUrl: './logistica.material.arquivo.component.html'
})

export class LogisticaMaterialArquivoComponent extends BaseForm<DetalheLogisticaMaterialDTO> implements OnInit {

    @ViewChild('modalMsg')
    private modalMsg: MensagemModalComponente;

    @ViewChild('headerDoador')
    private headerDoador: HeaderDoadorComponent;

    @ViewChild('modalMsgSucesso')
    private modalSucesso: MensagemModalComponente;

    @ViewChild("uploadComponent")
    private uploadComponent: UploadArquivoComponent;

    @ViewChild('modalMsgErro')
    private modalErro: MensagemModalComponente;

    // Chaves que envolvem o upload do arquivo
    private CONFIG_UPLOAD_CHAVES: String[] = ["extensaoArquivoPedidoTransporte", "quantidadeMaximaArquivosPedidoTransporte","tamanhoArquivoPedidoTransporteEmByte"];

    public logisticaMaterialComponent: LogisticaMaterialArquivoComponent = this;
    public configuracaoUpload: Configuracao[];
    private _arquivo: Arquivo = null;
    public uploader: FileUploader;
    public logisticaMaterialGroup: FormGroup;
    public hora: Array<string | RegExp>;

    public detalhe: DetalheLogisticaMaterialDTO;
    public listaTransportadoras: Transportadora[];
    public modoEdicao: boolean = false;

    private customErros: any = {
        tipoArquivoPrescricao: "",
        quantidadeArquivoPrescricao: "",
        tamanhoArquivoPrescricao: "",
        inserirPrescricao: "",
        nenhumaJustificativaSelecionada: "",
        quantidadeTotalInvalida: ""
    }

    constructor(
        private fb: FormBuilder,
        private router: Router, translate: TranslateService,
        private activatedRouter: ActivatedRoute,
        private messageBox: MessageBox,
        private pedidoTransporteService: PedidoTransporteService,
        private logisticaMaterialService: LogisticaMaterialService,
        private dominioService:DominioService,
        private transportadoraService:TransportadoraService) {
        super();

        this.translate = translate;

        this.hora = [/[0-2]/, /\d/, ':', /[0-5]/, /\d/];
        this.uploader = new FileUploader({});
    }

    ngOnInit() {

        this.uploadComponent.extensoes = "extensaoArquivoPedidoTransporte";
        this.uploadComponent.tamanhoLimite = "tamanhoArquivoPedidoTransporteEmByte";
        this.uploadComponent.quantidadeMaximaArquivos = "quantidadeMaximaArquivosPedidoTransporte"

        this.buildForm();
        this.translate.get("mensagem.erro").subscribe(res => {
            this.customErros['quantidadeArquivoPedidoTransporte'] = res.quantidadeArquivoPedidoTransporte;
            this.customErros["nenhumArquivoPedidoSelecionado"] = res.nenhumArquivoPedidoSelecionado;
        });

        this.translate.get("logisticaMaterial").subscribe(res => {
            this._formLabels = res;
            this.criarMensagemValidacaoPorFormGroup(this.logisticaMaterialGroup);
            this.setMensagensErro(this.logisticaMaterialGroup);
        });

        this.translate.get("prescricaoComponent").subscribe(res => {
          this._formLabels = res;
          this.criarMensagensErro(this.logisticaMaterialGroup);
        });

        this.activatedRouter.queryParams.subscribe(params=>{
            this.modoEdicao = Boolean(JSON.parse(params["edicao"]));
        });

        this.activatedRouter.params.subscribe(params => {
            let pedidoLogisticaId = params['pedidoId'];

              this.logisticaMaterialService.obterLogisticaMaterialAereo(pedidoLogisticaId).then(p =>{
                  this.detalhe = new DetalheLogisticaMaterialDTO().jsonToEntity(p);
                  this.headerDoador.popularCabecalhoIdentificacaoPorDoador(this.detalhe.idDoador);
                  this.logisticaMaterialGroup.get("transportadora").setValue(this.detalhe.materialAereo.idTransportadora);
                  let horaPrevistaRetirada: Date =
                      DateMoment.getInstance().parse(p.horaPrevistaRetirada, DateTypeFormats.TIME_ONLY);
                  this.detalhe.horaPrevistaRetirada = horaPrevistaRetirada;
              },
              (error:ErroMensagem)=> {
                  this.exibirMensagemErro(error);
              });
        });

        this.carregarConfiguracaoUploadArquivo();


        this.transportadoraService.listarTransportadoras().then(t =>{
            this.listaTransportadoras = t;
        },
        (error:ErroMensagem)=> {
            this.exibirMensagemErro(error);
        });

    }

    public confirmarLogistica(): void{
      if(this.validarGeral()){
          let pedidoTransporte: PedidoTransporte = new PedidoTransporte();
          pedidoTransporte.id = this.detalhe.materialAereo.idPedidoTransporte;
          this.pedidoTransporteService.atualizarInformacoesTransporteAereo(pedidoTransporte.id, this.uploadComponent.arquivos).then(res => {
            let modal: Modal = this.messageBox.alert(res)
            modal.closeOption = () => {
              this.router.navigateByUrl('/doadores/workup/logistica');
            };
            modal.show();
         }, (erro: ErroMensagem) => {
              this.exibirMensagemErro(erro);
          })
      }
    }

    public carregarConfiguracaoUploadArquivo(): void{

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
                        options.queueLimit = Number.parseInt(configuracao.valor);
                    }
                    else if (configuracao.chave == this.CONFIG_UPLOAD_CHAVES[2]) {
                        options.maxFileSize = Number.parseInt(configuracao.valor);
                    }
                });
                this.translate.get("mensagem.erro.tamanhoArquivoPedidoTransporte", {maxFileSize: StringUtil.formatBytes(this.configuracaoUpload.find(configuracao => configuracao.chave == this.CONFIG_UPLOAD_CHAVES[2]).valor )}).subscribe(res => {
                    this.customErros['tamanhoArquivoPedidoTransporte'] = res;
                });

                this.translate.get("mensagem.erro.tipoArquivoPedidoTransporte", {formato: this.configuracaoUpload.find(configuracao => configuracao.chave == this.CONFIG_UPLOAD_CHAVES[0]).valor.split("/")[1]}).subscribe(res => {
                    this.customErros['tipoArquivoPedidoTransporte'] = res;
                });
                this.uploader.setOptions(options);

            }, (error: ErroMensagem) => {
                this.modalErro.mensagem = error.mensagem.toString();
                this.modalErro.abrirModal();
            });

        this.uploader.onAfterAddingFile = function (fileItem: FileItem): any {

            this.logisticaMaterialComponent.clearMensagensErro(this.logisticaMaterialComponent.logisticaMaterialGroup);
            delete this.logisticaMaterialComponent.formErrors['arquivo'];

            let arquivo:Arquivo=new Arquivo();
            arquivo.fileItem=fileItem;
            arquivo.nome=fileItem.file.name;
            arquivo.nomeSemTimestamp=fileItem.file.name;
            this.logisticaMaterialComponent._arquivo = arquivo;
        };

        this.uploader.onWhenAddingFileFailed = function (item: FileLikeObject, filter: any, options: any): any {
            this.logisticaMaterialComponent.clearMensagensErro(this.logisticaMaterialComponent.logisticaMaterialGroup);
            switch (filter.name) {
                case 'queueLimit':
                this.logisticaMaterialComponent.formErrors['arquivo'] = this.logisticaMaterialComponent.customErros['quantidadeArquivoPedidoTransporte'];
                    break;
                case 'fileSize':
                    let maxFileSize: number = 0;
                    this.logisticaMaterialComponent.configuracaoUpload.forEach(configuracao => {
                        if (configuracao.chave == this.logisticaMaterialComponent.CONFIG_UPLOAD_CHAVES[2]) {
                            maxFileSize = Number.parseInt(configuracao.valor);
                        }
                    });
                    this.logisticaMaterialComponent.formErrors['arquivo'] = this.logisticaMaterialComponent.customErros["tamanhoArquivoPedidoTransporte"];
                    // 'O arquivo possui ' + exameComponent.formatBytes(item.size) + ' que ultrapassa o tamanho máximo permitido de ' + exameComponent.formatBytes(maxFileSize);
                    break;
                case 'mimeType':
                    this.logisticaMaterialComponent.formErrors['arquivo'] = this.logisticaMaterialComponent.customErros["tipoArquivoPedidoTransporte"];
                    break;
                default:
                    this.logisticaMaterialComponent.formErrors['arquivo'] = this.logisticaMaterialComponent.customErros["nenhumArquivoPedidoSelecionado"];
                    break;
            }
        };


    }

    public obterHoraFormatada(hora: Date): string{
        if(!hora){
            return "";
        }
        return DateMoment.getInstance().format(hora, DateTypeFormats.TIME_ONLY);
    }

    buildForm() {
        this.logisticaMaterialGroup = this.fb.group({
            'transportadora': [null, Validators.required],
            'horaRetirada': [null, Validators.required]
        });
    }

    nomeComponente(): string {
        return "LogisticaMaterialArquivoComponent";
    }

    public isCordao(): boolean {
      return this.detalhe && this.detalhe.idTipoDoador == TiposDoador.CORDAO_NACIONAL;
    }

    public isMedula(): boolean {
     return this.detalhe && this.detalhe.idTipoDoador == TiposDoador.NACIONAL
    }

    private exibirMensagemErro(error: ErroMensagem) {
        error.listaCampoMensagem.forEach(obj => {
            this.modalMsg.mensagem = obj.mensagem;
        })
        this.modalMsg.abrirModal();
    }

   private validarGeral():boolean{
        // delete this.formErrors['arquivo'];
        // let valid: boolean = this.validateForm();
        // if (this._arquivo == null) {
        //     this.formErrors["arquivo"] = this.customErros['nenhumArquivoPedidoSelecionado'];
        //     valid = false;
        // }

        return this.uploadComponent.validateForm();
    }

    public form(): FormGroup {
        return this.logisticaMaterialGroup;
    }

    public preencherFormulario(entidade: DetalheLogisticaMaterialDTO): void {
        throw new Error("Method not implemented.");
    }
    public removerArquivo() {
        this.uploader.clearQueue();
        this._arquivo = null;
    }

    public get nomeArquivo(): string {
        if (this.possuiArquivo) {
            return this._arquivo.nome.toString();
        }
        return "";
    }

    public get possuiArquivo(): boolean {
        return this._arquivo != null;
    }

    public fecharModalSucesso() {
        this.router.navigateByUrl(HistoricoNavegacao.urlRetorno());
    }

    public abrirPopUpAlteracao():void{
        let data: any = {
            idPedidoTransporte: this.detalhe.materialAereo.idPedidoTransporte,
            rmr: this.detalhe.rmr,
            horaPrevistaRetirada: this.detalhe.materialAereo.horaPrevistaRetirada
        }
        this.messageBox.dynamic(data, AlteracaoLogisticaMaterialNacionalComponent)
        .show();
    }
}
