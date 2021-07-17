import { Component, OnInit } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { TranslateService } from "@ngx-translate/core";
import { RelatorioService } from "app/admin/relatorio/relatorio.service";
import { BaseForm } from "../../../shared/base.form";
import { ErroMensagem } from "../../../shared/erromensagem";
import { IModalComponent } from "../../../shared/modal/factory/i.modal.component";
import { IModalContent } from "../../../shared/modal/factory/i.modal.content";
import { MessageBox } from "../../../shared/modal/message.box";
import { ErroUtil } from "../../../shared/util/erro.util";

/**
 * Componente de modal para cancelar pedido de workup.
 * @author Bruno Sousa
 */
@Component({
    selector: 'modal-alterar-constante-relatorio',
    templateUrl: './modal.alterar.constante.relatorio.component.html'
})
export class ModalAlterarConstanteRelatorioComponent extends BaseForm<Object> implements IModalContent, OnInit {
    
    target: IModalComponent;
    close: (target: IModalComponent) => void;
    data: any;

    private _form: FormGroup;

    constructor(translate: TranslateService, private fb: FormBuilder, 
        private messageBox: MessageBox, private relatorioService: RelatorioService) {
            super();
            this.translate = translate;
    }

    public ngOnInit() {
        this.buildForm();
        this.translate.get("admin.modal").subscribe(res => {
            this._formLabels = res;
            this.criarMensagemValidacaoPorFormGroup(this._form);
            this.setMensagensErro(this._form);
        });

        this.relatorioService.obterValorConstante(this.data.codigo).then(res => {
            this.setPropertyValue("valor", res);
        }, (error: ErroMensagem) => {
            ErroUtil.exibirMensagemErro(error, this.messageBox);
        });
            
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
            'valor': [null, Validators.required]
        });
    }

    /**
     * Cancela o pedido de workup
     * @returns void
     */
    public salvarValorConstante(): void {
        if (this.validateForm()) {

            this.target.hide();
            this.relatorioService.atualizarValorConstante(this.data.codigo, this.form().get("valor").value).then(res => {
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

    nomeComponente(): string {
        throw new Error("Method not implemented.");
    }

}