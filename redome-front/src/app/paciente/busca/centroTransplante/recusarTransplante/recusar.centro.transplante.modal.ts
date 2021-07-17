import { Component, OnInit } from "@angular/core";
import { BaseForm } from "../../../../shared/base.form";
import { FormGroup, FormBuilder, Validators } from "@angular/forms";
import { TranslateService } from "@ngx-translate/core";
import { EventEmitterService } from "../../../../shared/event.emitter.service";
import { ModalEvent } from "../../../../shared/eventos/modal.event";
import { IModalContent } from "../../../../shared/modal/factory/i.modal.content";
import { BsModalRef } from "ngx-bootstrap";
import { PacienteService } from "../../../paciente.service";
import { IModalComponent } from "../../../../shared/modal/factory/i.modal.component";
import { ErroMensagem } from "../../../../shared/erromensagem";
import { MessageBox } from "../../../../shared/modal/message.box";
import { CampoMensagem } from "../../../../shared/campo.mensagem";

/**
 * Componente onde é informado a justificativa para recusa do centro de transplante
 * para o paciente.
 * Este componente é parte a ser acrescida em um modal.
 *
 * @author Pizão
 */
@Component({
    selector: "recusar-centro-transplante-modal",
    templateUrl: './recusar.centro.transplante.modal.html'
})
export class RecusarCentroTransplanteModal extends BaseForm<any> implements OnInit, IModalContent {

    target: IModalComponent;
    close: (target: IModalComponent) => void;
    public data: any;
    public recusarCentroGroup: FormGroup;
    private mensagensErro: any;

    constructor(translate: TranslateService, formBuilder: FormBuilder,
                private pacienteService: PacienteService,
                private messageBox: MessageBox){
        super();
        this.translate = translate;

        this.recusarCentroGroup = formBuilder.group({
            'justificativa': [null, Validators.required]
        });

        this.translate.get("recusarCentroTransplante").subscribe(res => {
            this._formLabels = res;
            this.mensagensErro = res;
            this.criarMensagemValidacaoPorFormGroup(this.form());
            this.setMensagensErro(this.form());
        });
    }

    public form(): FormGroup {
        return this.recusarCentroGroup;
    }

    public confirmar(): void{
        if(this.validateForm()){
            this.close(this.target);
            let justificativaRecusa: string = this.form().get("justificativa").value;
            this.pacienteService.recusarCT(this.data.rmr, justificativaRecusa)
            .then(res => {
                this.data.atualizarHeader();
               },
                (error: ErroMensagem) => {
                    this.close(this.target);
                    this.messageBox.alert(error.listaCampoMensagem[0].mensagem).show();
            });
        }
    }

    ngOnInit(): void {
    }

    public preencherFormulario(entidade: any): void {
        throw new Error("Method not implemented.");
    }

    nomeComponente(): string {
        throw new Error("Method not implemented.");
    }

    public exibirMensagemErrorPorCampo(fieldName: string): string {
        if(super.exibirMensagemErrorPorCampo(fieldName)){
            return this.mensagensErro.justificativaObrigatoria;
        }
        return null;
    }
}
