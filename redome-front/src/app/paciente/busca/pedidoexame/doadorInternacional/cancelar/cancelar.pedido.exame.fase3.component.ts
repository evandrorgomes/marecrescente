import { Component, ViewChild } from "@angular/core";
import { OnInit } from '@angular/core/src/metadata/lifecycle_hooks';
import { TranslateService } from "@ngx-translate/core";
import { pedidoExameService } from '../../../../../export.mock.spec';
import { PedidoExameService } from '../../../../../laboratorio/pedido.exame.service';
import { ErroMensagem } from '../../../../../shared/erromensagem';
import { IModalComponent } from '../../../../../shared/modal/factory/i.modal.component';
import { IModalContent } from '../../../../../shared/modal/factory/i.modal.content';
import { MessageBox } from '../../../../../shared/modal/message.box';
import { Observable } from 'rxjs';
import { InativacaoComponent } from "../../../../../doador/inativacao/inativacao.component";
import { Modal } from "../../../../../shared/modal/factory/modal.factory";
import { Recursos } from "../../../../../shared/enums/recursos";
import { TiposTarefa } from "../../../../../shared/enums/tipos.tarefa";
import { SolicitacaoService } from "../../../../../doador/solicitacao/solicitacao.service";
import { ErroUtil } from "../../../../../shared/util/erro.util";
import { DateMoment } from "../../../../../shared/util/date/date.moment";
import { DateTypeFormats } from "../../../../../shared/util/date/date.type.formats";


/**
 * Tela de cancelamento do pedido de exame fase 3.
 * @author Pizão
 */
@Component({
    selector: 'cancelar-pedido-exame-fase3-doador',
    templateUrl: './cancelar.pedido.exame.fase3.component.html'
})
export class CancelarPedidoExameFase3Component implements IModalContent, OnInit {

    target: IModalComponent;
    close: (target: IModalComponent) => void;
    data: any;
    public justificativa: string;
    public dataCancelamento: string;
    public mensagemErroJustificativa: string;
    public mensagemErroDataCancelamento: string;
    private _mensagemObrigatorio: string;
    public TIPO_EXAME_FASE_2: number = 2;
    public TIPO_EXAME_CT: number = 3;
    public dateMask: Array<string | RegExp>

    @ViewChild("inativacaoDoador")
    private inativacaoDoador: InativacaoComponent;


    constructor(private translate: TranslateService, private messageBox: MessageBox,
        private pedidoExameService: PedidoExameService, private solicitacaoService: SolicitacaoService) { 
            this.dateMask = [/[0-9]/,/[0-9]/, '\/', /[0-9]/,/[0-9]/, '\/', /[0-9]/,/[0-9]/,/[0-9]/,/[0-9]/]
        }

    public ngOnInit() {
        this.inativacaoDoador.recurso = 
            this.isExameCtInternacional() ? Recursos.CANCELAR_FASE_3_INTERNACIONAL : Recursos.CANCELAR_PEDIDO_IDM;

        this.translate.get("modalcancelarpedidoexamefase3.justificativa").subscribe(label => {
            this.translate.get("mensagem.erro.obrigatorio", { "campo": label }).subscribe(res => {
                this._mensagemObrigatorio = res;
            });
        });

        if(this.isExameIdmOuCtInternacional()){
            this.translate.get("modalcancelarpedidoexamefase3.datacancelamento").subscribe(label => {
                this.translate.get("mensagem.erro.obrigatorio", { "campo": label }).subscribe(res => {
                    this._mensagemObrigatorio = res;
                });
            }); 
        }

        this.inativacaoDoador.obrigatorio("motivoStatusDoador", false);
    }

    ngAfterViewInit() {}

    private validarForm(): boolean {
        this.mensagemErroJustificativa = null;

        let valid: boolean = false
        if (!this.isExameIdmOuCtInternacional()) {
            valid = this.justificativa != null && this.justificativa != "";
            if (!valid) {
                this.mensagemErroJustificativa = this._mensagemObrigatorio;
            }
        } else {
            valid = this.dataCancelamento != null && this.dataCancelamento != "";
            if (!valid) {
                this.mensagemErroDataCancelamento = this._mensagemObrigatorio;
            }
        }

        let doadorInvalidado: boolean = this.inativacaoDoador.validateForm();

        return valid && doadorInvalidado;
    }

    /**
     * Caso o checkbox seja selecionado, torna ou não os campos obrigatórios.
     * 
     * @param obrigatorio TRUE para tornar obrigatório.
     */
    public tornarInativarObrigatorio(obrigatorio: boolean): void {
        this.inativacaoDoador.obrigatorio("motivoStatusDoador", obrigatorio);
    }

    public salvar() {
        if (this.validarForm()) {
            this.target.hide();

            if (this.isExameComplementar()) {
                this.solicitacaoService.cancelarFase2Internacional(
                    this.data.idSolicitacao, this.justificativa,
                    this.inativacaoDoador.motivoCancelamento, this.inativacaoDoador.tempoAfastamento).then(res => {
                        let alert: Modal = this.messageBox.alert(res.mensagem);
                        alert.target = this.target.target;
                        alert.closeOption = alert.target.atualizaMatch;
                        alert.show();

                    }, (error: ErroMensagem) => {
                        ErroUtil.exibirMensagemErro(error, this.messageBox);
                    });

            }
            else if (this.isExameCtInternacional()) {
                this.solicitacaoService.cancelarFase3Internacional(this.data.idSolicitacao, 
                        DateMoment.getInstance().parse(this.dataCancelamento, DateTypeFormats.DATE_ONLY)).then(res => {
                    let alert: Modal = this.messageBox.alert(res.mensagem);
                    alert.target = this.target.target;
                    alert.closeOption = alert.target.atualizaMatch;
                    alert.show();
                }, (error: ErroMensagem) => {
                    ErroUtil.exibirMensagemErro(error, this.messageBox);
                });
            }
            else if (this.isExameIdmInternacional()) {
                this.solicitacaoService.cancelarPedidoIdm(this.data.idSolicitacao, 
                    DateMoment.getInstance().parse(this.dataCancelamento, DateTypeFormats.DATE_ONLY)).then(res => {
                    let alert: Modal = this.messageBox.alert(res.mensagem);
                    alert.target = this.target.target;
                    alert.closeOption = alert.target.atualizaMatch;
                    alert.show();
                }, (error: ErroMensagem) => {
                    ErroUtil.exibirMensagemErro(error, this.messageBox);
                });
            }
            
        }
    }

    /**
     * Retorna TRUE quando o exame a ser cancelado é de fase 2 (complementar).
     * @return TRUE, caso tarefa do tipo CADASTRAR_RESULTADO_EXAME_INTERNACIONAL.
     */
    isExameComplementar(){
        return this.data.idTipoTarefa == TiposTarefa.CADASTRAR_RESULTADO_EXAME_INTERNACIONAL.id;
    }

    /**
     * Retorna TRUE quando o exame a ser cancelado é de CT.
     * @return TRUE, caso tarefa do tipo CADASTRAR_RESULTADO_EXAME_CT_INTERNACIONAL.
     */
    isExameCtInternacional(): boolean{ 
        return this.data.idTipoTarefa == TiposTarefa.CADASTRAR_RESULTADO_EXAME_CT_INTERNACIONAL.id ;
    }

    /**
     * Retorna TRUE quando o exame a ser cancelado é de IDM.
     * @return TRUE, caso tarefa do tipo CADASTRAR_RESULTADO_EXAME_IDM_INTERNACIONAL.
     */
    isExameIdmInternacional(){
        return this.data.idTipoTarefa == TiposTarefa.CADASTRAR_RESULTADO_EXAME_IDM_INTERNACIONAL.id;
    }

    /**
     * Retorna TRUE quando o exame é de CT ou IDM.
     * @return TRUE, caso tarefa do tipo CADASTRAR_RESULTADO_EXAME_CT_INTERNACIONAL ou 
     * CADASTRAR_RESULTADO_EXAME_IDM_INTERNACIONAL.
     */
    isExameIdmOuCtInternacional(){
        return  this.isExameCtInternacional() || this.isExameIdmInternacional();
    }


}