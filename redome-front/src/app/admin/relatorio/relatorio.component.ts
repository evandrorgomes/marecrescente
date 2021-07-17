import { ModalAlterarConstanteRelatorioComponent } from './modal/modal.alterar.constante.relatorio.component';
import { BaseForm } from '../../shared/base.form';
import { Component, ViewEncapsulation, ViewChild } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { RelatorioService } from './relatorio.service';
import { Relatorio } from './relatorio';
import { OnInit } from '@angular/core/src/metadata/lifecycle_hooks';
import { RelatorioDTO } from './relatorio.dto';
import { MessageBox } from '../../shared/modal/message.box';
import { ModalEvent } from '../../shared/eventos/modal.event';
import { TranslateService } from '@ngx-translate/core';
import { CKEditorComponent } from 'ng2-ckeditor';

/**
 * Classe gera um relatorio utilizando a API ng2-ckeditor
 * referencia: https://www.npmjs.com/package/ng2-ckeditor
 * botões do ckeditor customizaveis:
 * https://ckeditor.com/latest/samples/toolbarconfigurator/index.html#basic
 */
@Component({
    selector: "relatorio",
    templateUrl: './relatorio.component.html'
})
export class RelatorioComponent extends BaseForm<Relatorio> implements OnInit {

    @ViewChild(CKEditorComponent)
    ckEditor: CKEditorComponent;


    public formRelatorio: FormGroup;
    public ckeditorContent: string = '';
    public codigo: string = '';
    public relatorios: Relatorio[] = [];
    public parametros: string[] = [];
    public constantes: string[] = [];

    constructor(private fb: FormBuilder, private relatorioService: RelatorioService
        , private messageBox: MessageBox, translate: TranslateService) {
        super();
        this.translate = translate;
    }

    public confirmar(): void {
        this.messageBox.confirmation('Deseja sobrescrever o relatorio existente?').yesOption = this.incluir;
    }

    //https://ckeditor.com/latest/samples/toolbarconfigurator/index.html#basic
    ngAfterViewChecked() {
        let editor = this.ckEditor.instance;

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

    buildForm() {

        this.formRelatorio = this.fb.group({
            'codigo': [null, Validators.required]
        });

        return this.formRelatorio;

    }
    /**
     * @returns void
     */
    public incluir(): void {
        let relatorioDTO: RelatorioDTO = new RelatorioDTO();
        relatorioDTO.html = this.ckeditorContent;
        relatorioDTO.codigo = this.formRelatorio.get('codigo').value;
        if (this.validateForm()) {
            this.relatorioService.incluir(relatorioDTO).then(res => {
                this.listar();
            });
        }
    }

    public limpar() {
        this.formRelatorio.get('codigo').setValue(null);
        this.ckeditorContent = '';
    }

    /**
     * Listar todos os relatorios.
     * @returns void
     */
    public listar(): void {
        this.relatorios = [];
        this.relatorioService.listar().then(res => {
            console.log(res)
            if (res) {
                res.forEach(element => {
                    let relatorio: Relatorio = new Relatorio();
                    relatorio.id = element.id;
                    relatorio.codigo = element.codigo;
                    relatorio.html = element.html
                    relatorio.tipo = element.tipo
                    this.relatorios.push(relatorio);
                });
            }
        })
    }
    /**
     * Prepara para edição.
     * @param  {Relatorio} relatorio
     * @returns void
     */
    public editar(relatorio: Relatorio): void {
        this.ckeditorContent = relatorio.html;
        this.formRelatorio.get('codigo').setValue(relatorio.codigo);
    }
    /**
     * Baixar arquivo.
     * @param  {Relatorio} relatorio
     * @returns void
     */
    public baixarArquivo(relatorio: Relatorio): void {
        this.relatorioService.downloadRelatorio(relatorio.codigo);
    }
    ngOnInit(): void {
        this.listar();
        this.buildForm();
        this.translate.get("admin").subscribe(res => {
            this._formLabels = res;
            this.criarMensagemValidacaoPorFormGroup(this.formRelatorio);
            this.setMensagensErro(this.formRelatorio);
        });
        this.relatorioService.listarParametrosRelatorios().then(res => {
            this.parametros = res;
        });

        this.relatorioService.listarConstantes().then(res => {
            this.constantes = res;
        });
    }

    public form(): FormGroup {
        return this.formRelatorio
    }
    public preencherFormulario(entidade: any): void {
        throw new Error("Method not implemented.");
    }
    nomeComponente(): string {
        return "RelatorioComponent"
    }

    public copiarTexto(texto:string){
        // We need to create a dummy textarea with the text to be copied in the DOM
        var textArea = document.createElement("textarea");

        // Hide the textarea from actually showing
        textArea.style.position = 'fixed';
        textArea.style.top = '-999px';
        textArea.style.left = '-999px';
        textArea.style.width = '2em';
        textArea.style.height = '2em';
        textArea.style.padding = '0';
        textArea.style.border = 'none';
        textArea.style.outline = 'none';
        textArea.style.boxShadow = 'none';
        textArea.style.background = 'transparent';

        // Set the texarea's content to our value defined in our [text-copy] attribute
        textArea.value = texto;
        document.body.appendChild(textArea);

        // This will select the textarea
        textArea.select();

        try {
            // Most modern browsers support execCommand('copy'|'cut'|'paste'), if it doesn't it should throw an error
            var successful = document.execCommand('copy');
            var msg = successful ? 'successful' : 'unsuccessful';
            // Let the user know the text has been copied, e.g toast, alert etc.
            console.log(msg);
        } catch (err) {
            // Tell the user copying is not supported and give alternative, e.g alert window with the text to copy
            console.log('unable to copy');
        }

        // Finally we remove the textarea from the DOM
        document.body.removeChild(textArea);
    }

    abrirModalAlterarConstante(event: any) {
        let data: any = {
            codigo: event.target.value,
            fechar: () => {

            }

        };

        this.messageBox.dynamic(data, ModalAlterarConstanteRelatorioComponent)
            .withTarget(this)
            .withCloseOption((target?: any) => {                
            })
            .show();

        
    }

};