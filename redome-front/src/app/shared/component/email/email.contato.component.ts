import { REQUIRED,INVALID_PATTERN } from 'app/validators/data.validator';
import { EmailContato } from '../../classes/email.contato';
import { BaseForm } from '../../base.form';
import { TranslateService } from '@ngx-translate/core';
import { FormGroup, FormBuilder, FormArray, Validators, EmailValidator } from '@angular/forms';
import { Component, OnInit, Input } from '@angular/core';

/**
 * Classe que representa o componente de telefone de contato
 * @author Bruno Sousa
 */
@Component({
    selector: "contato-email",
    moduleId: module.id,
    templateUrl: "./email.contato.component.html",
    //styleUrls: ['../../paciente.css']
})
export class EmailContatoComponent extends BaseForm<EmailContato[]> implements OnInit {
    
    private _esconderCampoPrincipal: boolean = false;
    
    private _validarPrincipal: boolean =  true;

    @Input() esconderBotaoAdicionarEmail: boolean = true;

    emailForm: FormGroup;

    private _emailsContato: string;
    private _emailPrincipal: string;
    private _emailInvalido: string;

    
    
    /**
     * Construtor 
     * @param serviceDominio - serviço de dominio para buscar os dominios
     * @author Bruno Sousa
     */
    constructor(private fb: FormBuilder, translate: TranslateService) {
        super();
        this.translate = translate;
        this.buildForm();
    }

    ngOnInit() {
        this.translate.get("emailComponent").subscribe(res => {
            this._formLabels = res;            
            //Chamada aos métodos default para que a tela funcione com validações
            this.criarMensagemValidacaoPorFormGroup(this.emailForm);
            this.setMensagensErro(this.emailForm);
        });

        this.translate.get("mensagem.erro.camposObrigatorios").subscribe(res => {
            this._emailsContato = res;
        });

        this.translate.get("mensagem.erro.camposInvalidos").subscribe(res => {
            this._emailInvalido = res;
        });

        this.translate.get("mensagem.erro.campoPrincipal", {campo: 'email'}).subscribe(res => {
            this._emailPrincipal = res;
        });
    }

    /**
     * Método principal de construção do formulário
     * @author Bruno Sousa
     */
    buildForm() {
        this.criarListasEmail(1);
    }

     /**
     * Cria as linhas para a lista de telefones, considerando a quantidade solicitada
     * no parametro.
     * 
     * @param qtdLinhas quantidade de linhas de telefone a serem criadas. 
     */
    private criarListasEmail(qtdLinhas:number):void{
        let emailFormArray: FormArray = new FormArray([]);

        for(let i=0; i < qtdLinhas; i++){
            emailFormArray.setControl(i, this.criarEmailGroup());
        }

        this.emailForm = this.fb.group({
            listaEmail: emailFormArray
        });
    }

    get listaEmailForm(): FormArray { 
        return this.emailForm.get('listaEmail') as FormArray; 
    }

    adicionarEmail() {
        this.listaEmailForm.push(this.criarEmailGroup());
        this.criarMensagemValidacaoPorFormGroup(this.emailForm);
        this.setMensagensErro(this.emailForm);
    }

    private criarEmailGroup(): FormGroup {
        return this.fb.group({
            principal: [false, null],
            email: [null, Validators.compose([
                Validators.required,
                Validators.pattern('^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+.[a-zA-Z0-9-.]+$')
            ])]
        });
    }

    /**
     * Retorna a lista com os telefones.
     * @author Bruno Sousa
     */
    listar(): EmailContato[] {
        let retorno: EmailContato[] = [];
        this.listaEmailForm.controls.forEach((telGroup: FormGroup) => {
            let emailContato: EmailContato = new EmailContato();            
            emailContato.email = telGroup.get("email").value;   
            emailContato.principal = telGroup.get("principal").value;
            retorno.push(emailContato);
        });
        return retorno;
    }

    public form(): FormGroup{
        return this.emailForm;
    }

    // Override
    public preencherFormulario(contatos: EmailContato[]): void {
        
        if(contatos){
            this.criarListasEmail(contatos.length);

            let index:number = 0;
            contatos.forEach(contato => {
                this.setPropertyValue("listaEmail." + index + ".email", contato.email);
                this.setPropertyValue("listaEmail." + index + ".principal", contato.principal);
                index++;
            });
        }
    }

    /**
     * Verifica se o formulário é válido.
     * @author Rafael Pizão
     */
    public validateForm(): boolean {
        super.validateForm();
        this.resetMsgErrors();
        return this.verificarValidacao();
    }

    /**
     * verifica as obrigatoriedades de cada campo de emails
     * @author Bruno Sousa
     * @return boolean se foi validado com sucesso ou não
     */
    private verificarValidacao():boolean{
        let valid: boolean = this.emailForm.valid;
        if(!valid){
            this.listaEmailForm.controls.forEach((emailGroup: FormGroup) => {
                for (const key in emailGroup.get('email').errors) {
                    if(key == REQUIRED){
                        this.formErrors["emailsContato"] = this._emailsContato;
                    }
                    else if(key == INVALID_PATTERN){
                        this.formErrors["emailsContato"] = this._emailInvalido;
                    }
                }
            });
            return valid;
        }
        
        if (this._validarPrincipal) {            
            let validPrincipal: boolean = false;
            if (this.listaEmailForm.length > 1) { 
                this.listaEmailForm.controls.forEach((emailGroup: FormGroup) => {
                    if (emailGroup.get('principal').value) {
                        validPrincipal = true;
                    }
                });
            }
            else {
                this.listaEmailForm.get('0.principal').setValue(true);
                validPrincipal = true;
            }
            if (!validPrincipal) {
                this.formErrors['emailPrincipal'] = this._emailPrincipal;
            }
            valid = validPrincipal;

            return valid;
        }
        return true;
        
    }

    nomeComponente(): string {
        return null;
    }

    deveEsconderCampoPrincipal(): boolean {  

        return !this._esconderCampoPrincipal;
    }

    /**
     * reseta as mensagens de erro de telefones
     * @author Rafael Pizão
     */
    resetMsgErrors(){
        this.formErrors['emailsContato'] = '';
        this.formErrors['emailPrincipal'] = '';
    }

    /**
     * Método para remover um item dos telefones
     * @param item - referente a um telefone
     * @author Rafael Pizão
     */
    removerContato(i) {
        this.listaEmailForm.removeAt(i);
    }

    marcarComoPrincipal(i) {
        let lista: FormArray = this.listaEmailForm;

        if (lista.get(i + ".principal").value == true) {
            for (let p = 0; p <= lista.length - 1; p++) {
                if (p==i) {
                    continue;
                }
                lista.get(p + ".principal").setValue(false);
            }
        }
    }

    @Input()
    public set esconderCampoPrincipal(value: string ) {
        if (!value) {
            this._esconderCampoPrincipal = true;    
        }
        else {
            this._esconderCampoPrincipal = value == "true" ? true : false;
        }        
    }

    @Input()
    public set validarPrincipal(value: string) {
        if (!value) {
            this._validarPrincipal = true;    
        }
        else {
            this._validarPrincipal = value == "true" ? true : false;
        }
	}
}