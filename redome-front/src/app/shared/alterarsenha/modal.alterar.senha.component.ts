import { Component } from "@angular/core";
import { OnInit } from '@angular/core/src/metadata/lifecycle_hooks';
import { TranslateService } from "@ngx-translate/core";
import { IModalContent } from 'app/shared/modal/factory/i.modal.content';
import { IModalComponent } from 'app/shared/modal/factory/i.modal.component';
import { MessageBox } from 'app/shared/modal/message.box';
import { BuildForm } from '../buildform/build.form';
import { StringControl } from '../buildform/controls/string.control';
import { Validators, FormGroup } from '@angular/forms';
import { matchOtherValidator } from "app/validators/match.validator";
import { AutenticacaoService } from '../autenticacao/autenticacao.service';
import { UsuarioService } from "app/admin/usuario/usuario.service";
import { ErroMensagem } from "../erromensagem";
import { ErroUtil } from '../util/erro.util';


/**
 * Conteudo do modal dinámico para cancelamento de pedido de exame fase 3.
 * @author Bruno Sousa
 */
@Component({
    selector: 'modal-alterar-senha',
    templateUrl: './modal.alterar.senha.component.html'
})
export class ModalAlterarSenhaComponent implements IModalContent, OnInit {

    target: IModalComponent;
    close: (target: IModalComponent) => void;
    data: any;

    private senhaForm: BuildForm<any>;

    constructor(private translate: TranslateService, private messageBox: MessageBox,
        private usuarioService: UsuarioService) {
        this.senhaForm = new BuildForm<any>()
            .add(new StringControl('senhaAtual', [Validators.required]))
            .add(new StringControl('novaSenha', [Validators.required, Validators.minLength(8)]))
            .add(new StringControl('confirmarSenha', [Validators.required, matchOtherValidator('novaSenha')]));
    }

    public ngOnInit() {
        
    }


    public fechar(target: any) {    
        target.close(target.target);
    }


    public confirmar() {
        if (this.senhaForm.valid) {
            this.usuarioService.alterarSenha(this.senhaForm.value.senhaAtual, this.senhaForm.value.novaSenha).then(res => {
                this.target.hide();
                this.messageBox.alert(res.mensagem).show();
            }, (error: ErroMensagem) => {
                ErroUtil.exibirMensagemErro(error, this.messageBox);
            } )
        }
    }

    public form(): FormGroup {
        return this.senhaForm.form;
    }

}