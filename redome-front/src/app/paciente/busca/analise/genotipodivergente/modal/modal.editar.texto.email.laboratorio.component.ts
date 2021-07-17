import { AfterViewChecked, Component, OnInit, ViewChild } from '@angular/core';
import { FormGroup, Validators } from '@angular/forms';
import { TranslateService } from "@ngx-translate/core";
import { BuildForm } from 'app/shared/buildform/build.form';
import { StringControl } from 'app/shared/buildform/controls/string.control';
import { IModalComponent } from "app/shared/modal/factory/i.modal.component";
import { IModalContent } from "app/shared/modal/factory/i.modal.content";
import { MessageBox } from "app/shared/modal/message.box";
import { CKEditorComponent } from "ng2-ckeditor";
import { LaboratorioService } from 'app/shared/service/laboratorio.service';
import { ErroMensagem } from 'app/shared/erromensagem';
import { ErroUtil } from 'app/shared/util/erro.util';

/**
 * Conteudo do modal dinámico para editar o texto padrão do email que é 
 * enviado para os laboratórios com exame divergente.
 * 
 * @author Bruno Sousa
 */
@Component({ 
    selector: 'modal-editar-texto-email-laboratorio',
    templateUrl: './modal.editar.texto.email.laboratorio.component.html'
})
export class ModalEditarTextoEmailLaboratorioComponent implements IModalContent, OnInit, AfterViewChecked {
    
    target: IModalComponent;
    close: (target: IModalComponent) => void;
    data: any;

    public textoEmail: string = '';

    @ViewChild('ckEditor')
    private ckEditor: CKEditorComponent;

    private _buildForm: BuildForm<any>;

    constructor(private messageBox: MessageBox, private laboratorioService: LaboratorioService) {

        this._buildForm = new BuildForm<any>()
            .add(new StringControl("destinatarios", [ Validators.required ] ));

    }
    

    ngOnInit(): void {

        this.textoEmail = this.data['textoEmailPadrao'];
        if (this.data.exameSelecionado && this.data.exameSelecionado.laboratorio && this.data.exameSelecionado.laboratorio.emails ) {
            const destinatarios = this.data.exameSelecionado.laboratorio.emails.map(email => email.email).join(";");
            this._buildForm.getControlAsStringControl('destinatarios').value = destinatarios;
        }

    }

    ngAfterViewChecked(): void {
        const editor = this.ckEditor.instance;
        editor.config.height = 100;

        editor.config.toolbarGroups = [
            { name: 'document', groups: ['mode'] },
            { name: 'clipboard', groups: ['undo'] },
            { name: 'editing', groups: ['find', 'selection', 'spellchecker', 'editing'] },
            { name: 'basicstyles', groups: ['basicstyles', 'cleanup'] },
            { name: 'paragraph', groups: ['list', 'ident', 'blocks', 'align', 'bidi'] },
            { name: 'links', groups: ['links'] },
            { name: 'styles', groups: ['styles'] },
            { name: 'colors', groups: ['colors'] },
            { name: 'tools', groups: ['tools'] },
            { name: 'about', groups: ['about'] },
            { name: 'insert'},
            { name: 'forms'}
            // { name: 'styles', items: [ 'Styles', 'Format', 'Font', 'FontSize' ] },
            // { name: 'colors', items: [ 'TextColor', 'BGColor' ] },
            // { name: 'tools', items: [ 'Maximize', 'ShowBlocks' ] },
            // { name: 'about', items: [ 'About' ] }


        ]

        //  editor.config.removeButtons = 'Source,Save,Templates,Find,Replace,Scayt,SelectAll,Form,Radio';        
    }

    public enviarEmail() {
        if (this._buildForm.valid) {
            const destinatarios: string = this._buildForm.value.destinatarios;                        
            this.target.hide();
            this.laboratorioService.enviarEmailExameDivergente(this.data.exameSelecionado.laboratorio.id, this.data.idMatch, destinatarios, this.textoEmail).then(res => {                
                this.messageBox.alert(res).show();
            },
            (error: ErroMensagem) => {
                ErroUtil.exibirMensagemErro(error, this.messageBox);
            });
        }
    }

    public form(): FormGroup {
        return <FormGroup>this._buildForm.form;
    }


}