import { Component, OnInit } from "@angular/core";
import { IModalContent } from "../../../shared/modal/factory/i.modal.content";
import { IModalComponent } from "../../../shared/modal/factory/i.modal.component";
import { TranslateService } from "@ngx-translate/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { MessageBox } from "../../../shared/modal/message.box";
import { BaseForm } from "../../../shared/base.form";
import { WorkupService } from "../../consulta/workup/workup.service";
import { MotivoCancelamentoWorkup } from "../../consulta/workup/motivo.cancelamento.workup";
import { ErroMensagem } from "../../../shared/erromensagem";
import { ErroUtil } from "../../../shared/util/erro.util";

/**
 * Componente de modal para cancelar pedido de workup.
 * @author Bruno Sousa
 */
@Component({
    selector: 'modal-cancelar-pedidoworkup',
    templateUrl: './modal.cancelar.pedido.workup.component.html'
})
export class ModalCancelarPedidoWorkupComponent extends BaseForm<Object> implements IModalContent, OnInit {
    
    target: IModalComponent;
    close: (target: IModalComponent) => void;
    data: any;

    private _form: FormGroup;
    private _motivosCancelamento: MotivoCancelamentoWorkup[];

    constructor(translate: TranslateService, private fb: FormBuilder, 
        private messageBox: MessageBox, private workupService: WorkupService) {
            super();
            this.translate = translate;
    }

    public ngOnInit() {
        this.buildForm();
        this.translate.get("workup.pedido").subscribe(res => {
            this._formLabels = res;
            this.criarMensagemValidacaoPorFormGroup(this._form);
            this.setMensagensErro(this._form);
        });

        this.workupService.listarMotivosCancelamento().then(res => {
            this._motivosCancelamento = res;
        }, (error: ErroMensagem) => {
            ErroUtil.exibirMensagemErro(error, this.messageBox);
        })
    }
    
    public fechar() {
        this.target.hide();
    }

    public preencherFormulario(entidade: Object): void {
        throw new Error("Method not implemented.");
    }

    /**
     * Metodo obrigatorio para retornar o formulario atual
     * @returns FormGroup
     */
    public form(): FormGroup {
        return this._form;
    }

    /**
     * * Método que constrói o formulário
     * @returns void
     */
    buildForm(): void {
        this._form = this.fb.group({
            'motivoCancelamento': [null, Validators.required]
        });
    }

    /**
     * Cancela o pedido de workup
     * @returns void
     */
    public cancelarPedidoWorkup(): void {
        if (this.validateForm()) {

            this.target.hide();
            this.workupService.cancelarPedidoWorkup(this.data.pedidoWorkupId, this.form().get("motivoCancelamento").value).then(res => {
                this.messageBox.alert(res.mensagem)
                    .withTarget(this)
                    .withCloseOption((target: any) => {
                        this.data.fechar();
                    })
                    .show();
            }, (error: ErroMensagem) => {
                ErroUtil.exibirMensagemErro(error, this.messageBox);                
            })
        }
    }

	/**
	 * @returns MotivoCancelamentoWorkup
	 */
    public get motivosCancelamento(): MotivoCancelamentoWorkup[] {
        return this._motivosCancelamento;
    }

    nomeComponente(): string {
        throw new Error("Method not implemented.");
    }

}