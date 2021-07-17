import { Observable } from 'rxjs';
import { EventEmitterService } from '../event.emitter.service';
import { Component, Input, ViewChild, EventEmitter, Output } from '@angular/core';
import { ModalDirective, BsModalRef, BsModalService } from 'ngx-bootstrap';

/**
 * Classe Component para padronizar o modal
 * @author Fillipe Queiroz
 */
@Component({
    moduleId: module.id,
    selector: 'modal',
    templateUrl: './mensagem.modal.component.html'
})
export class MensagemModalComponente {

    @Output()
    public fecharModal: EventEmitter<String> = new EventEmitter<String>();

    @Input()
    public mensagem: string;

    @Input()
    exibirBotaoFechar: boolean = false;

    @ViewChild('modal')
    private modal: ModalDirective;

    fecharModalComponente() {
        this.modal.hide();
        if (this.fecharModal.observers.length > 0) {
            this.fecharModal.emit();
        }
    }

    public abrirModal() {
        this.modal.show();
    }

}