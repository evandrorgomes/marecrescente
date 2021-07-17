import {Component, OnInit} from "@angular/core";
import {IModalComponent} from "app/shared/modal/factory/i.modal.component";
import {IModalContent} from "app/shared/modal/factory/i.modal.content";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {BuildForm} from "../../../../shared/buildform/build.form";
import {StringControl} from "../../../../shared/buildform/controls/string.control";


/**
 * Conteudo do modal dinámico para cancelamento de pedido de exame fase 3.
 * @author Bruno Sousa
 */
@Component({
    selector: 'justificativa-avaliacao-modal',
    templateUrl: './justificativa.avaliacao.modal.html'
})
export class JustificativaAvaliacaoModal implements IModalContent, OnInit {

    target: IModalComponent;
    close: (target: IModalComponent) => void;
    data: any;

    private justificativaForm: BuildForm<any>;

    constructor(private fb: FormBuilder) {

        this.justificativaForm = new BuildForm()
           .add(new StringControl("justificativa", [Validators.required]));

    }

    public ngOnInit() {}

    private validarForm(): boolean {
        return this.justificativaForm.valid;
    }

    form(): FormGroup {
        return this.justificativaForm.form;
    }

    /**
     * @description Reprova a avaliação de solicitação de nova busca.
     * Neste caso, somente a avaliação é finalizada e nada muda no "ciclo de vida"
     * do paciente e/ou busca.
     * @author Pizão
     */
    public confirmar(): void{
        if (this.validarForm()) {
            this.target.hide();
            this.data.confirmar(this.justificativaForm.get("justificativa").value);
        }
    }

}
