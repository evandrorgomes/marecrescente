import { Observable } from 'rxjs';
import { EventEmitterService } from '../event.emitter.service';
import { Component, Input, ViewChild, EventEmitter, Output } from '@angular/core';

/**
 * Classe Component para padronizar o modal
 * @author Fillipe Queiroz
 */
@Component({
    moduleId: module.id,
    selector:'pergunta-modal',
    templateUrl: './pergunta.modal.component.html'
})
export class PerguntaModalComponente {
    
    @Output()
    public fecharSucesso: EventEmitter<String> = new EventEmitter<String>();

    @Output()
    public fecharModal: EventEmitter<String> = new EventEmitter<String>();

    @Input()
    mensagem: string;

    @Input()
    segundaMensagem: string;

    @Input()
    id: string = "pergunta";

    @ViewChild('modal')
    private modal;

    fecharModalComponente(confirmado: boolean){
        this.modal.hide();
        if(confirmado && this.fecharSucesso.observers.length > 0){
            this.fecharSucesso.emit();
        }
        else if (this.fecharModal.observers.length > 0) {
            this.fecharModal.emit();
        }
    }

    public abrirModal(){
        this.modal.show();
    }
    
}