import { FileItem } from 'ng2-file-upload';
import { StatusAvaliacaoCamaraTecnica } from './../../status.avaliacao.camara.tecnica';
import { FormBuilder, Validators } from '@angular/forms';
import { ErroUtil } from 'app/shared/util/erro.util';
import { PedidoTransferenciaCentroService } from 'app/shared/service/pedido.transferencia.centro.service';
import { Component, ViewChild } from "@angular/core";
import { OnInit } from '@angular/core/src/metadata/lifecycle_hooks';
import { TranslateService } from "@ngx-translate/core";
import { pedidoExameService } from '../../../../export.mock.spec';
import { PedidoExameService } from '../../../../laboratorio/pedido.exame.service';
import { ErroMensagem } from '../../../../shared/erromensagem';
import { IModalComponent } from '../../../../shared/modal/factory/i.modal.component';
import { IModalContent } from '../../../../shared/modal/factory/i.modal.content';
import { MessageBox } from '../../../../shared/modal/message.box';
import { Observable } from 'rxjs';
import { BaseForm } from '../../../../shared/base.form';
import { AvaliacaoCamaraTecnica } from '../../avaliacao.camara.tecnica';
import { FormGroup } from '@angular/forms';
import { AvalicacaoCamaraTecnicaService } from '../../avaliacao.camara.tecnica.service';
import { UploadArquivoComponent } from '../../../../shared/upload/upload.arquivo.component';


/**
 * Conteudo do modal dinámico detalhe de avaliação de câmara técnica.
 * @author Filipe Paes
 */
@Component({
    selector: 'modal-detalhe-avaliacao-camara-tecnica',
    templateUrl: './modal.detalhe.avaliacao.camara.tecnica.component.html'
})
export class ModalDetalheAvaliacaoCamaraTecnicaComponent  extends BaseForm<AvaliacaoCamaraTecnica>  implements IModalContent, OnInit {
    target: IModalComponent;
    close: (target: IModalComponent) => void;
    data: any;
    public tipoFormulario:any;
    private _formAvaliacao:FormGroup;
    private _statusLista:StatusAvaliacaoCamaraTecnica[] = [];

    @ViewChild("uploadComponent")
    private uploadComponent: UploadArquivoComponent;

    private  STATUS_AGUARDANDO_AVALIACAO: number = 1;
    private  STATUS_APROVADO: number = 2;
    private  STATUS_REPROVADO: number = 3;
    public acao:string = "";

    constructor(translate: TranslateService, private messageBox: MessageBox,private fb: FormBuilder,
        private avaliacaoCamaraTecnicaService:AvalicacaoCamaraTecnicaService) {
            super();
        
        this.translate = translate;
        this.buildForm();
    }

    ngOnInit(): void {
        this.uploadComponent.quantidadeMaximaArquivos = "quantidadeMaximaArquivosCamaraTecnica";
        this.uploadComponent.extensoes = "extensaoArquivoPdfPadrao";
        this.uploadComponent.tamanhoLimite = "tamanhoArquivoPdfPadraoEmByte";    

       this.tipoFormulario = this.data.tipoForm;

        if( this.tipoFormulario ==  "status"){
            this.avaliacaoCamaraTecnicaService.obterListaStatus().then(res=>{
                res.forEach(s => {
                    let status : StatusAvaliacaoCamaraTecnica = new StatusAvaliacaoCamaraTecnica().jsonToEntity(s);
                    if(status.id != this.STATUS_AGUARDANDO_AVALIACAO &&
                        status.id != this.STATUS_APROVADO &&
                        status.id != this.STATUS_REPROVADO){
                            this._statusLista.push(status);
                        }
                });
            },
            (error: ErroMensagem) => {
                this.exibirMensagemErro(error);
            });
            this.setFieldRequired(this.form(), "status");
        }
        
        if(this.data.acao == "aprovar"){
            this.translate.get("avaliacaocamaratecnica.detalhe.acao.aprovacao").subscribe(res => {
                this.acao = res;
            });
        }
        else if(this.data.acao == "reprovar"){
            this.translate.get("avaliacaocamaratecnica.detalhe.acao.reprovacao").subscribe(res => {
                this.acao = res;
            });
        }
        else if(this.data.acao == "status"){
            this.translate.get("avaliacaocamaratecnica.detalhe.acao.status").subscribe(res => {
                this.acao = res;
            }); 
        }

        this.translate.get("avaliacaocamaratecnica.detalhe.form").subscribe(res => {
            this._formLabels = res;
            this.criarMensagensErro(this._formAvaliacao);
        });
    }

    private exibirMensagemErro(erro: ErroMensagem): void{
        if (erro.listaCampoMensagem != null && erro.listaCampoMensagem.length != 0) {
            this.messageBox.alert(erro.listaCampoMensagem[0].mensagem)
            .withTarget(this)
            .show();
        }
    }

    public fechar(target: any) {    
        target.close(target.target);
    }
    public confirmar() {
        if(this.validateForm()){
            let avaliacao: AvaliacaoCamaraTecnica = new AvaliacaoCamaraTecnica();
            avaliacao.id = this.data.idAvaliacao;
            avaliacao.status = new StatusAvaliacaoCamaraTecnica();
            if( this.tipoFormulario  ==  "status"){
                avaliacao.status.id = this._formAvaliacao.get("status").value;
                this.target.hide();         
                this.avaliacaoCamaraTecnicaService.salvarStatus(avaliacao).then(res=>{
                    this.messageBox.alert(res).show();
                    this.data.fechar();
                },
                (error: ErroMensagem) => {
                    this.exibirMensagemErro(error);
                });
            }
            else{
                if(this.data.acao == "aprovar"){
                    avaliacao.status.id = this.STATUS_APROVADO;
                    avaliacao.justificativa = this._formAvaliacao.get("justificativa").value;
                    this.target.hide();         
                    this.avaliacaoCamaraTecnicaService.aprovarAvaliacao(avaliacao,this.uploadComponent.arquivos).then(res=>{
                        this.messageBox.alert(res).show();
                        this.data.fechar();
                    },
                    (error: ErroMensagem) => {
                        this.exibirMensagemErro(error);
                    });
                }
                else{
                    avaliacao.status.id = this.STATUS_REPROVADO;
                    avaliacao.justificativa = this._formAvaliacao.get("justificativa").value;
                    this.target.hide();         
                    this.avaliacaoCamaraTecnicaService.reprovarAvaliacao(avaliacao, this.uploadComponent.arquivos).then(res=>{
                        this.messageBox.alert(res).show();
                        this.data.fechar();
                    },
                    (error: ErroMensagem) => {
                        this.exibirMensagemErro(error);
                    });
                }
            }
        }
    }


    public form():FormGroup {
        return this._formAvaliacao;
    }
    public preencherFormulario(entidade: AvaliacaoCamaraTecnica): void {
        throw new Error("Method not implemented.");
    }

     /**
     * * Método que constrói o formulário
     * @returns void
     */
    buildForm(): void {
        this._formAvaliacao = this.fb.group({
            'status': [null],
            'justificativa': [null]
        });
    }


    /**
     * Getter statusLista
     * @return {StatusAvaliacaoCamaraTecnica[]}
     */
	public get statusLista(): StatusAvaliacaoCamaraTecnica[] {
		return this._statusLista;
    }

    exibirUpload():boolean{
        return this.tipoFormulario  !=  "upload";
    }
    exibirStatus():boolean{
        return this.tipoFormulario  !=  "status";
    }
    
    nomeComponente(): string {
        throw new Error("Method not implemented.");
    }

}