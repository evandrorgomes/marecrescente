import { ErroUtil } from 'app/shared/util/erro.util';
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
import { MedicoService } from "app/shared/service/medico.service";


/**
 * Conteudo do modal dinámico para cancelamento de pedido de exame fase 3.
 * @author Bruno Sousa
 */
@Component({
    selector: 'modal-reprovar-precadastro-medico',
    templateUrl: './modal.reprovar.precadastro.medico.component.html'
})
export class ModalReprovarPreCadastroMedicoComponent implements IModalContent, OnInit {

    target: IModalComponent;
    close: (target: IModalComponent) => void;
    data: any;
    public justificativa: string;
    public mensagemErroJustificativa: string;

    private _mensagemObrigatorio: string;

    constructor(private translate: TranslateService, private messageBox: MessageBox,
        private medicoService: MedicoService) {

    }

    public ngOnInit() {
        this.translate.get("modalreprovarprecadastromedico.justificativa" ).subscribe(label => {
            this.translate.get("mensagem.erro.obrigatorio", {"campo": label}).subscribe(res => {
                this._mensagemObrigatorio = res;
            });    
        });
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
            this.medicoService.reprovarPreCadastro(this.data.idPreCadastroMedico, this.justificativa).then(res => {
                this.messageBox.alert(res.mensagem)
                    .withCloseOption((target: any) => {
                        this.data.fecharModalSucesso();
                    })
                    .show();                
            }, (error: ErroMensagem) => {
                ErroUtil.exibirMensagemErro(error, this.messageBox);                
            });
            
        }
    }

}