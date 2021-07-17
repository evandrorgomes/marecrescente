import { Component, OnInit, ViewChild } from "@angular/core";
import { FormBuilder } from "@angular/forms";
import { TranslateService } from "@ngx-translate/core";
import { IModalComponent } from "app/shared/modal/factory/i.modal.component";
import { UploadArquivoComponent } from "app/shared/upload/upload.arquivo.component";
import { MessageBox } from "app/shared/modal/message.box";
import { IModalContent } from "app/shared/modal/factory/i.modal.content";
import { PrescricaoService } from "app/doador/solicitacao/prescricao.service";
import { ErroMensagem } from "app/shared/erromensagem";
import { ErroUtil } from "app/shared/util/erro.util";
import { FileItem } from "ng2-file-upload";



/**
 * Componente de modal para cancelar pedido de workup.
 * @author Bruno Sousa
 */
@Component({ 
    selector: 'modal-upload-autorizacao-paciente',
    templateUrl: './modal.upload.autorizacaopaciente.component.html'                    
})
export class ModalUploadAutorizacaoPacienteComponent implements IModalContent, OnInit {
    
    target: IModalComponent;
    close: (target?: IModalComponent) => void;
    data: any;

    @ViewChild("uploadComponent")
    private uploadComponent: UploadArquivoComponent;

    constructor(private translate: TranslateService, private fb: FormBuilder, 
        private messageBox: MessageBox, private prescricaoService: PrescricaoService) {
    }

    public ngOnInit() {
        this.uploadComponent.extensoes = "extensaoArquivoAutorizacaoPaciente";
        this.uploadComponent.tamanhoLimite = "tamanhoArquivoAutorizacaoPacienteEmByte";
    }
    
    public fechar() {
        this.close(this.target);
    }

    /**
     * Cancela o pedido de workup
     * @returns void
     */
    public cancelarPedidoWorkup(): void {
        if (this.uploadComponent.validateForm()) {

            let arquivo: FileItem = null;
            this.uploadComponent.arquivos.forEach((item: FileItem, key: string) => {
                arquivo = item;
            });

            this.target.hide();

            this.prescricaoService.uploadArquivoAutorizacaoPaciente(this.data.idPrescricao,
                 arquivo).then(res => {
                this.messageBox.alert(res)
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


}