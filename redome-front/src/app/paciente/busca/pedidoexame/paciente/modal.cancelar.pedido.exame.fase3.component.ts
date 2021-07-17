import { Component } from "@angular/core";
import { OnInit } from '@angular/core/src/metadata/lifecycle_hooks';
import { TranslateService } from "@ngx-translate/core";
import { pedidoExameService } from '../../../../export.mock.spec';
import { PedidoExameService } from '../../../../laboratorio/pedido.exame.service';
import { ErroMensagem } from '../../../../shared/erromensagem';
import { IModalComponent } from '../../../../shared/modal/factory/i.modal.component';
import { IModalContent } from '../../../../shared/modal/factory/i.modal.content';
import { MessageBox } from '../../../../shared/modal/message.box';
import { Observable } from 'rxjs';
import { SolicitacaoService } from "app/doador/solicitacao/solicitacao.service";


/**
 * Conteudo do modal dinámico para cancelamento de pedido de exame fase 3.
 * @author Bruno Sousa
 */
@Component({
    selector: 'modal-cancelar-pedido-exame-fase3-paciente',
    templateUrl: './modal.cancelar.pedido.exame.fase3.component.html'
})
export class ModalCancelarPedidoExameFase3Component implements IModalContent, OnInit {

    target: IModalComponent;
    close: (target: IModalComponent) => void;
    data: any;
    public justificativa: string;
    public mensagemErroJustificativa: string;

    private _mensagemObrigatorio: string;

    constructor(private translate: TranslateService, private messageBox: MessageBox,
        private solicitacaoService: SolicitacaoService) {

    }

    public ngOnInit() {
        this.translate.get("modalcancelarpedidoexamefase3.justificativa" ).subscribe(label => {
            this.translate.get("mensagem.erro.obrigatorio", {"campo": label}).subscribe(res => {
                this._mensagemObrigatorio = res;
            });    
        });
    }

    private exibirMensagemErro(erro: ErroMensagem) {
        let modalErro = this.messageBox.alert("erro").showButtonOk(false);
        modalErro.target = this;
        modalErro.closeOption = this.fechar;
        modalErro.show();
    }

    public fechar(target: any) {    
        target.close(target.target);
    }

    private validarForm(): boolean {
        this.mensagemErroJustificativa = null;
        let valid: boolean  = this.justificativa != null && this.justificativa != "";
        if (!valid) {
            this.mensagemErroJustificativa = this._mensagemObrigatorio;
        } 
        return valid;
    }

    public confirmar() {
        if (this.validarForm()) {
            this.target.hide();            
            this.solicitacaoService.cancelarFase3Paciente(this.data.idSolicitacao, this.justificativa).then(res => {
                this.messageBox.alert(res.mensagem).show();
                this.close(this.target);
            }, (error: ErroMensagem) => {
                this.messageBox.alert(error.mensagem.toString()).show();
            });
            
                
            
        }
    }

}