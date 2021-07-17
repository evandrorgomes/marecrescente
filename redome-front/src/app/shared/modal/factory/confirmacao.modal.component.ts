import { Observable } from 'rxjs';
import { Component, Input, ViewChild, EventEmitter, Output } from '@angular/core';
import { BsModalRef } from 'ngx-bootstrap';
import { EventEmitterService } from '../../event.emitter.service';
import { ModalEvent } from '../../eventos/modal.event';
import { IModalComponent } from './i.modal.component';
import { EventDispatcher } from '../../eventos/event.dispatcher';

/**
 * Classe visual do componente de modal de confirmação.
 * 
 * @author Pizão
 */
@Component({
    moduleId: module.id,
    selector: 'confirmacao-modal',
    templateUrl: './confirmacao.modal.component.html'
})
export class ConfirmacaoModalComponente extends IModalComponent {
    target: any;
    public exibirBotaoOk: boolean = false;
    public yesOption: (target?: any) => void;
    public noOption: (target?: any) => void;
    closeOption: (target?: any) => void;    
    private jaFechou: boolean = false;


    constructor(protected bsModalRef: BsModalRef) {
        super();
    }

    public fecharModalComponente() {
        if (!this.jaFechou) {
            this.bsModalRef.hide();
        }
        if (this.closeOption) {
            this.closeOption(this.target);
        }
    }

    public selecionarSim(): void {
        if (!this.jaFechou) {
            this.bsModalRef.hide();
        }
        if (this.yesOption) {
            this.yesOption(this.target);
        }
    }

    public selecionarNao(): void {
        if (!this.jaFechou) {
            this.bsModalRef.hide();
        }
        if (this.noOption) {
            this.noOption(this.target);
        }
    }

    public hide(): void {
        this.jaFechou = true;
        this.bsModalRef.hide();
    }
}