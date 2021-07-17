import { Validators } from '@angular/forms';
import { ErroMensagem } from './../../../../shared/erromensagem';
import { StringControl } from './../../../../shared/buildform/controls/string.control';
import { BuildForm } from 'app/shared/buildform/build.form';
import { Component, AfterViewInit, OnInit } from "@angular/core";
import { IModalContent } from "app/shared/modal/factory/i.modal.content";
import { IModalComponent } from "app/shared/modal/factory/i.modal.component";
import { DominioService } from "app/shared/dominio/dominio.service";
import { FormGroup } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { MessageBox } from 'app/shared/modal/message.box';
import { ErroUtil } from 'app/shared/util/erro.util';
import { NumberControl } from '../../../../shared/buildform/controls/number.control';

/**
 * Conteudo do modal dinámico para cancelamento de pedido de exame fase 3.
 * @author Bruno Sousa
 */
@Component({
    selector: 'modal-aprovar-reprovar-avaliacao',
    templateUrl: './modal.aprovar.reprovar.avaliacao.component.html'
})
export class ModalAprovarReprovarAvaliacaoComponent implements IModalContent, OnInit {

    target: IModalComponent;
    close: (target: IModalComponent) => void;
    data: any;

    private _form: BuildForm<any>;
    public _labelObservacao: string;
    public _temposParaTransplante: number[] = [];

    constructor(private translate: TranslateService, private dominioService: DominioService,
        private messageBox: MessageBox) {

        this._form = new BuildForm<any>()
            .add(new StringControl('observacao'))
            .add(new NumberControl('tempoParaTransplante'));

        this.dominioService.obterConfiguracao('temposParaTransplanteEmDias').then(value => {
            let tempos = value.valor.split(",");
            tempos.forEach(tempo => {
                this._temposParaTransplante.push(new Number(tempo).valueOf());
            })
        },(error: ErroMensagem) => {
            ErroUtil.exibirMensagemErro(error, this.messageBox);
        });
    }

    ngOnInit() {        
        this.translate.get(['avaliacaoPaciente.modalAprovacaoAvaliacao.observacao', 'placeholder.opcional'] ).subscribe(res => {
            this._labelObservacao = res['avaliacaoPaciente.modalAprovacaoAvaliacao.observacao'];
            if (this.data.aprovarAvaliacao) {
                this._labelObservacao += " " + res['placeholder.opcional'];
            }
            this._labelObservacao += ":";
        });
        
        if (this.data.aprovarAvaliacao) {
            this._form.getControlAsNumberControl('tempoParaTransplante').makeRequired();
        }
        else {
            this._form.getControlAsStringControl('observacao').makeRequired();
        }

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
            this.data.confirmar(this._form.value);
        }
    }

    public getLabelobservacao(): string {
        return this._labelObservacao;
    }


}