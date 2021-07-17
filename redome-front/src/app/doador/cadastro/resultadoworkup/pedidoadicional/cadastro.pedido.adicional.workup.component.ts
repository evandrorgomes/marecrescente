import { Component, OnInit, ViewChild } from "@angular/core";
import { FormArray, FormBuilder, FormGroup, Validators } from "@angular/forms";
import { ActivatedRoute, Router } from "@angular/router";
import { TranslateService } from "@ngx-translate/core";
import { HeaderDoadorComponent } from "app/doador/consulta/header/header.doador.component";
import { Arquivo } from "app/paciente/cadastro/exame/arquivo";
import { BaseForm } from "app/shared/base.form";
import { Configuracao } from "app/shared/dominio/configuracao";
import { DominioService } from "app/shared/dominio/dominio.service";
import { ErroMensagem } from "app/shared/erromensagem";
import { MessageBox } from "app/shared/modal/message.box";
import { PermissaoRotaComponente } from "app/shared/permissao.rota.componente";
import { PedidoWorkupService } from "app/shared/service/pedido.workup.service";
import { UploadArquivoComponent } from "app/shared/upload/upload.arquivo.component";
import { ErroUtil } from "app/shared/util/erro.util";
import { FileItem, FileLikeObject, FileUploader, FileUploaderOptions } from "ng2-file-upload";
import { ArquivoPedidoAdicionalWorkup } from "../resultado/arquivo.pedido.adicional.workup";
import { PedidoAdicionalWorkup } from "../resultado/pedido.adicional.workup";

/**
 * Classe que representa o componente para catrastrar o formulário de workup.
 */
@Component({
	selector: 'cadastro-pedido-adicional-workup',
	templateUrl: './cadastro.pedido.adicional.workup.component.html'
})

export class CadastroPedidoAdicionalWorkupComponent extends BaseForm<Object> implements OnInit, PermissaoRotaComponente {

  @ViewChild("uploadComponent")
  private uploadComponent: UploadArquivoComponent;

  @ViewChild('headerDoador')
  private headerDoador: HeaderDoadorComponent;

  private CONFIG_UPLOAD_CHAVES: String[] = ["extensaoArquivoResultadoWorkup"
  , "tamanhoArquivoResultadoWorkupEmByte"
  , "quantidadeMaximaArquivosResultadoWorkup"];

  private customErros: any = {
    tipoArquivoLaudo: "",
    quantidadeArquivoLaudo: "",
    tamanhoArquivoLaudo: "",
    inserirLaudo: "",
    nenhumLaudoSelecionado: "",
  }

  public configuracaoUpload: Configuracao[];
  public uploader: FileUploader;
  public cadastrarForm: FormGroup;

  private _pedidoAdicional: PedidoAdicionalWorkup;
  private _idPedidoAdicional: number;

  constructor(
    translate: TranslateService,
    protected fb: FormBuilder,
    protected router: Router,
    protected activatedRouter: ActivatedRoute,
    protected messageBox: MessageBox,
    protected dominioService: DominioService,
    protected pedidoWorkupService: PedidoWorkupService) {
    super();

    this.translate = translate;
    this.buildForm();
  }

  ngOnInit(): void {

    this.inicializarComponenteUpload();

    this.translate.get("mensagem.erro").subscribe(res => {
      this.customErros["tipoArquivoLaudo"] = res.tipoArquivoLaudo;
      this.customErros['quantidadeArquivoLaudo'] = res.quantidadeArquivoLaudo;
      this.customErros['inserirLaudo'] = res.inserirLaudo;
      this.customErros['nenhumLaudoSelecionado'] = res.nenhumLaudoSelecionado;
    });

    this.activatedRouter.queryParamMap.subscribe(queryParam => {
      if (queryParam.keys.length != 0) {
          this._idPedidoAdicional = Number(queryParam.get('idPedidoAdicional'));
          let idDoador = Number(queryParam.get('idDoador'));

          this.pedidoWorkupService.obterPedidoAdicionalWorkup(this._idPedidoAdicional).then(res => {
            this._pedidoAdicional = res;
            this.atualizarListaDeArquivos(res.arquivosPedidoAdicionalWorkup);
          }, (error: ErroMensagem) => {
            ErroUtil.exibirMensagemErro(error, this.messageBox);
          });

          Promise.resolve(this.headerDoador).then(() => {
            this.headerDoador.clearCache();
            this.headerDoador.popularCabecalhoIdentificacaoPorDoador(idDoador);
         });
      }
    });
  }

  public form(): FormGroup {
    return this.cadastrarForm;
  }

  public arquivosFormArray(): FormArray {
    return <FormArray>this.cadastrarForm.get('arquivos');
  }

  public preencherFormulario(entidade: PedidoAdicionalWorkup): void {
    throw new Error("Method not implemented.");
  }

  nomeComponente(): string {
    return "CadastroPedidoAdicionalWorkupComponent";
  }

  buildForm() {
    this.cadastrarForm = this.fb.group({
        'upload': null
    });
    this.cadastrarForm.addControl('arquivos', new FormArray([]));
  }

  atualizarListaDeArquivos(exames: ArquivoPedidoAdicionalWorkup[]){
    if(exames){
      this.buildForm();
      let exameAdicionalComponent: CadastroPedidoAdicionalWorkupComponent = this;
      exames.forEach(exame => {
        let arquivo: Arquivo = new Arquivo();
        arquivo.id = exame.id;
        arquivo.nome = exame.caminho;
        arquivo.descricao = exame.descricao;
        if (arquivo.nome) {
          let primeiroIndex = arquivo.nome.indexOf("_");
          let index: number = exameAdicionalComponent.qualquerIndex(arquivo.nome, "_", 4);
          arquivo.nomeSemTimestamp = arquivo.nome.substring(index + 1, arquivo.nome.length);
        }
        let itens: FormArray = exameAdicionalComponent.cadastrarForm.get('arquivos') as FormArray;
        itens.push(exameAdicionalComponent.criarItemArquivo(arquivo.id, arquivo.nomeSemTimestamp, arquivo.nome, arquivo.descricao));
      }, (error: ErroMensagem) => {
        ErroUtil.exibirMensagemErro(error, this.messageBox);
      });
    }
  }

  salvar() {
    if (this.validarFormCadastro()) {

      let arquivosExAdicionais: ArquivoPedidoAdicionalWorkup[] = [];

      let arquivosForm = this.cadastrarForm.get('arquivos') as FormArray;
      arquivosForm.controls.forEach(c => {
          let arquivoExAdicionalObj: ArquivoPedidoAdicionalWorkup = new ArquivoPedidoAdicionalWorkup();
          arquivoExAdicionalObj.id = c.get("idArquivo").value;
          arquivoExAdicionalObj.caminho = c.get("nomeArquivoCompleto").value;
          arquivoExAdicionalObj.descricao = c.get("comentario").value;
          arquivoExAdicionalObj.pedidoAdicional = this._idPedidoAdicional;
          arquivosExAdicionais.push(arquivoExAdicionalObj);
      });

      this.pedidoWorkupService.salvarArquivosExamesAdicionais(this._idPedidoAdicional, arquivosExAdicionais).then(res => {
          this.atualizarListaDeArquivos(res.arquivosExamesAdicionais);
          this.messageBox.alert(res.mensagem).show();
      }, (error: ErroMensagem) => {
          ErroUtil.exibirMensagemErro(error, this.messageBox)
      });
    }
  }

  finalizar() {
    if (this.validarFormCadastro()) {

      let arquivosExAdicionais: ArquivoPedidoAdicionalWorkup[] = [];

      let arquivosForm = this.cadastrarForm.get('arquivos') as FormArray;
      arquivosForm.controls.forEach(c => {
          let arquivoExAdicionalObj: ArquivoPedidoAdicionalWorkup = new ArquivoPedidoAdicionalWorkup();
          arquivoExAdicionalObj.id = c.get("idArquivo").value;
          arquivoExAdicionalObj.caminho = c.get("nomeArquivoCompleto").value;
          arquivoExAdicionalObj.descricao = c.get("comentario").value;
          arquivoExAdicionalObj.pedidoAdicional = this._idPedidoAdicional;
          arquivosExAdicionais.push(arquivoExAdicionalObj);
      });
      this._pedidoAdicional.arquivosPedidoAdicionalWorkup = arquivosExAdicionais;

      this.pedidoWorkupService.finalizarPedidoAdicionalWorkup(this._pedidoAdicional).then(res => {
          this.messageBox.alert(res).show();
          this.voltar();

      }, (error: ErroMensagem) => {
          ErroUtil.exibirMensagemErro(error, this.messageBox)
      });
    }
  }

  inicializarComponenteUpload(): void {
    let exameAdicionalComponent: CadastroPedidoAdicionalWorkupComponent = this;
    this.uploader = new FileUploader({});
    this.carregarConfiguracaoUploadArquivo();
    this.salvarArquivoStorage(exameAdicionalComponent);
    this.tratamentoErroArquivo(exameAdicionalComponent);
  }

  carregarConfiguracaoUploadArquivo(){
    // Carrega a configuração necessária para o upload do arquivo.
    this.dominioService.listarConfiguracoes(this.CONFIG_UPLOAD_CHAVES).then(result => {
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
  }

  salvarArquivoStorage(exameAdicionalComponent: CadastroPedidoAdicionalWorkupComponent){

    this.uploader.onAfterAddingFile = function (fileItem: FileItem): any {

      fileItem.remove();
      if (exameAdicionalComponent.uploader.queue.filter(f => f._file.name == fileItem._file.name).length == 0) {
          exameAdicionalComponent.uploader.queue.push(fileItem);
      }
      else {
          exameAdicionalComponent.form().controls["upload"].reset(null);
          exameAdicionalComponent.form().controls["upload"].updateValueAndValidity();
          return
      }

      delete exameAdicionalComponent.formErrors['arquivo'];
      let data: FormData = new FormData();

      data.append("file", fileItem._file, fileItem.file.name);

      data.append("idPedidoAdicional", new Blob([exameAdicionalComponent._idPedidoAdicional], {
           type: 'application/json'
      }), '');

      exameAdicionalComponent.pedidoWorkupService.salvarArquivoStorage(data).then(res => {
        let arquivo: Arquivo = new Arquivo();
        arquivo.fileItem = fileItem;
        arquivo.nome = res.stringRetorno;
        if (arquivo.nome) {
            let primeiroIndex = arquivo.nome.indexOf("_");
            let index: number = exameAdicionalComponent.qualquerIndex(arquivo.nome, "_", 4);
            arquivo.nomeSemTimestamp = arquivo.nome.substring(index + 1, arquivo.nome.length);
        }
        let itens: FormArray = exameAdicionalComponent.cadastrarForm.get('arquivos') as FormArray;
        itens.push(exameAdicionalComponent.criarItemArquivo(arquivo.id, arquivo.nomeSemTimestamp, arquivo.nome));
        exameAdicionalComponent.criarMensagemValidacaoPorFormGroup(exameAdicionalComponent.cadastrarForm);
        exameAdicionalComponent.form().controls["upload"].reset(null);
        exameAdicionalComponent.form().controls["upload"].updateValueAndValidity();
        }, (error: ErroMensagem) => {
            exameAdicionalComponent.alterarMensagem.emit(error.mensagem);
        });
    };
  }

  tratamentoErroArquivo(exameAdicionalComponent: CadastroPedidoAdicionalWorkupComponent){

    this.formErrors['tipoArquivoLaudo'] = "";
    this.formErrors['quantidadeArquivoLaudo'] = "";
    this.formErrors['tamanhoArquivoLaudo'] = "";
    this.formErrors['inserirLaudo'] = "";
    this.formErrors['nenhumLaudoSelecionado'] = "";
    this.formErrors['metodologia'] = "";

    this.uploader.onWhenAddingFileFailed = function (item: FileLikeObject, filter: any, options: any): any {
      exameAdicionalComponent.clearMensagensErro(exameAdicionalComponent.cadastrarForm);
      switch (filter.name) {
          case 'queueLimit':
              exameAdicionalComponent.formErrors['arquivo'] = exameAdicionalComponent.customErros['quantidadeArquivoLaudo'];
              break;
          case 'fileSize':
              let maxFileSize: number = 0;
              exameAdicionalComponent.configuracaoUpload.forEach(configuracao => {
                  if (configuracao.chave == exameAdicionalComponent.CONFIG_UPLOAD_CHAVES[1]) {
                      maxFileSize = Number.parseInt(configuracao.valor);
                  }
              });
              exameAdicionalComponent.formErrors['arquivo'] = exameAdicionalComponent.customErros["tamanhoArquivoLaudo"];
              break;
          case 'mimeType':
              exameAdicionalComponent.formErrors['arquivo'] = exameAdicionalComponent.customErros["tipoArquivoLaudo"];
              break;
          default:
              exameAdicionalComponent.formErrors['arquivo'] = exameAdicionalComponent.customErros["inserirLaudo"];
              break;
      }
    };

  }

  /**
  * Remove o arquivo que foi feito no upload
  *
  * @param {string} nomeArquivo
  * @memberof ExameComponent
  */
  public removerArquivo(idArquivoAdicional: number,caminho: string) {
  this.pedidoWorkupService.removerArquivoStorage(idArquivoAdicional, caminho)
      .then(res => {
          this.removerArquivoPorNome(res.stringRetorno);
      }, (error: ErroMensagem) => {
          this.alterarMensagem.emit(error.mensagem);
      });
  }

  private removerArquivoPorNome(nomeArquivo: string) {
      for (let i = 0; i < this.uploader.queue.length; i++) {
          let arquivo: FileItem = this.uploader.queue[i];
          let index = this.qualquerIndex(nomeArquivo, "_", 4);
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

  private formatBytes(bytes, decimals?) {
    if (bytes == 0) return '0 Bytes';
    const k = 1024,
        dm = decimals || 2,
        sizes = ['Bytes', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'],
        i = Math.floor(Math.log(bytes) / Math.log(k));
    return parseFloat((bytes / Math.pow(k, i)).toFixed(dm)) + ' ' + sizes[i];
  }

  qualquerIndex(valor: String, strProcura: string, indice: number): number {
    var L = valor.length, i = -1;
    while (indice-- && i++ < L) {
        i = valor.indexOf(strProcura, i);
        if (i < 0) break;
    }
    return i;
  }

  criarItemArquivo(idArquivo: Number, nomeArquivo: String = "", nomeArquivoCompleto: String = "", comentario: String = ""): FormGroup {
    return this.fb.group({
        'idArquivo': [idArquivo, null],
        'nomeArquivo': [nomeArquivo, null],
        'nomeArquivoCompleto': [nomeArquivoCompleto, Validators.required],
        'comentario': [comentario]
    });
  }

  validarFormCadastro(): boolean {
    this.clearMensagensErro(this.cadastrarForm);

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

  voltar(){
    this.router.navigateByUrl("/doadores/workup/consulta");
  }

  /**
   * Getter pedidoAdicional
   * @return {PedidoAdicionalWorkup}
   */
	public get pedidoAdicional(): PedidoAdicionalWorkup {
		return this._pedidoAdicional;
	}

  /**
   * Setter pedidoAdicional
   * @param {PedidoAdicionalWorkup} value
   */
	public set pedidoAdicional(value: PedidoAdicionalWorkup) {
		this._pedidoAdicional = value;
	}

}
