import {FormGroup, Validators} from '@angular/forms';
import {BuildForm} from 'app/shared/buildform/build.form';
import {Component, OnInit} from "@angular/core";
import {IModalContent} from "app/shared/modal/factory/i.modal.content";
import {IModalComponent} from "app/shared/modal/factory/i.modal.component";
import {DominioService} from "app/shared/dominio/dominio.service";
import {TranslateService} from '@ngx-translate/core';
import {MessageBox} from 'app/shared/modal/message.box';
import {StringControl} from "../../buildform/controls/string.control";

/**
 * Conteudo do modal dinámico para cancelamento de pedido de exame fase 3.
 * @author Bruno Sousa
 */
@Component({
    selector: 'modal-justificativa',
    templateUrl: './modal.justificativa.component.html'
})
export class ModalJustificativaComponent implements IModalContent, OnInit {

    target: IModalComponent;
    close: (target: IModalComponent) => void;
    data: any;

    private _form: BuildForm<any>;


    constructor(private translate: TranslateService, private dominioService: DominioService,
        private messageBox: MessageBox) {

        this._form = new BuildForm<any>()
            .add(new StringControl('justificativa', [Validators.required]));
    }

    ngOnInit() {

    }

    public get form(): FormGroup {
        return this._form.form;
    }

    /**
     * Método para reprovar uma avaliação
     * 
     * @memberof AvaliacaoComponent
     */
    confirmar(){       
        if (this._form.valid) {
            this.target.hide();
            this.data.confirmar(this._form.value.justificativa);
        }
    }

}