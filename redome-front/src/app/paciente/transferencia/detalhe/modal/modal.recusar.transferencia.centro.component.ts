import { ErroUtil } from 'app/shared/util/erro.util';
import { PedidoTransferenciaCentroService } from 'app/shared/service/pedido.transferencia.centro.service';
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


/**
 * Conteudo do modal dinámico para cancelamento de pedido de transferência de centro avaliador.
 * @author Bruno Sousa
 */
@Component({
    selector: 'modal-recusar-transferencia-centro',
    templateUrl: './modal.recusar.transferencia.centro.component.html'
})
export class ModalRecusarTransferenciaCentroComponent implements IModalContent, OnInit {

    target: IModalComponent;
    close: (target: IModalComponent) => void;
    data: any;
    public justificativa: string;
    public mensagemErroJustificativa: string;

    private _mensagemObrigatorio: string;

    constructor(private translate: TranslateService, private messageBox: MessageBox,
        private pedidoTransferenciaCentroService: PedidoTransferenciaCentroService) {

    }

    public ngOnInit() {
        this.translate.get("transferencia.centro.modal.recusar.justificativa" ).subscribe(label => {
            this.translate.get("mensagem.erro.obrigatorio", {"campo": label}).subscribe(res => {
                this._mensagemObrigatorio = res;
            });    
        });
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
            this.pedidoTransferenciaCentroService.recusarPedidoTransferencia(this.data.idPedidoTransferenciaCentro, this.justificativa).then(res => {
                this.messageBox.alert(res.mensagem).show();
                this.data.fechar();
            }, (error: ErroMensagem) => {
                ErroUtil.exibirMensagemErro(error, this.messageBox);
            });
        }
    }

}