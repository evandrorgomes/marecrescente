import { Observable } from 'rxjs';
import { Component, Input, ViewChild, EventEmitter, Output, OnInit, ElementRef } from '@angular/core';
import { BsModalRef } from 'ngx-bootstrap/modal';
import { EventEmitterService } from '../../event.emitter.service';
import { ModalEvent } from '../../eventos/modal.event';
import { IModalComponent } from './i.modal.component';
import { EventDispatcher } from '../../eventos/event.dispatcher';


/**
 * Classe que define o visual do modal de alerta para todas as chamadas do sistema.
 * 
 * @author Pizão
 */
@Component({
    moduleId: module.id,
    selector: 'modal-content',
    templateUrl: './alerta.modal.component.html'
})
export class AlertaModalComponente extends IModalComponent implements OnInit {
    target: any;
    closeOption: (target?: any)=>void;
    private jaFechou: boolean = false;

    public exibirBotaoOk: boolean = false;

    constructor(public bsModalRef: BsModalRef) {
        super();
    }

    ngOnInit() {
    }

    fecharModalComponente() {
        if (!this.jaFechou) {
            this.bsModalRef.hide();
        }
        if(this.closeOption){
            this.closeOption(this.target);
        }
    }

    public hasData(): boolean {
        return this.data != null;
    }

    public hide(): void {
        this.jaFechou = true;
        this.bsModalRef.hide();
    }

}