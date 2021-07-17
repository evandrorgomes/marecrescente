import { Component, OnInit } from "@angular/core";
import { FormGroup, Validators } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { BuildForm } from 'app/shared/buildform/build.form';
import { DominioService } from "app/shared/dominio/dominio.service";
import { IModalComponent } from "app/shared/modal/factory/i.modal.component";
import { IModalContent } from "app/shared/modal/factory/i.modal.content";
import { MessageBox } from 'app/shared/modal/message.box';
import { StringControl } from "../../../../../shared/buildform/controls/string.control";

/**
 * Conteudo do modal descrição dos exames adicionais.
 * @author ergomes
 */
@Component({
    selector: 'modal-descricao-exame-adicional',
    templateUrl: './modal.descricao.exame.adicional.component.html'
})
export class ModalDescricaoExameAdicionalComponent implements IModalContent, OnInit {

    target: IModalComponent;
    close: (target: IModalComponent) => void;
    data: any;

    private _form: BuildForm<any>;


    constructor(private translate: TranslateService, private dominioService: DominioService,
        private messageBox: MessageBox) {

        this._form = new BuildForm<any>()
            .add(new StringControl('descricao', [Validators.required]));
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
            this.data.confirmar(this._form.value.descricao);
        }
    }

}
