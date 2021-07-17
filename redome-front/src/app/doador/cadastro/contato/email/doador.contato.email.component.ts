import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { AutenticacaoService } from '../../../../shared/autenticacao/autenticacao.service';
import { BaseForm } from '../../../../shared/base.form';
import { EmailContato } from '../../../../shared/classes/email.contato';
import { EmailContatoComponent } from '../../../../shared/component/email/email.contato.component';
import { ErroMensagem } from "../../../../shared/erromensagem";
import { EmailContatoService } from '../../../../shared/service/email.contato.service';
import { DoadorService } from '../../../doador.service';
import { EmailContatoDoador } from './../../../email.contato.doador';

/**
 * Classe que representa o componente de telefone de contato
 * @author Bruno Sousa
 */
@Component({
    selector: "doador-contato-email",
    moduleId: module.id,
    templateUrl: "./doador.contato.email.component.html",
    //styleUrls: ['../../paciente.css']
})
export class DoadorEmailContatoComponent extends BaseForm<EmailContatoDoador[]> implements OnInit {
        
    @ViewChild("emailContatoComponent")
    private emailContatoComponent: EmailContatoComponent;

    @ViewChild("modalErro")
    private modalErro;

    private _idDoador: number;
    private _contatosEmail: EmailContatoDoador[];
    private _esconderLinkIncluirEmail: string = 'false';

    mostraDados: string = '';
    mostraFormulario: string = 'hide';
    deveExibirRemoverEmail:boolean = false;
    deveExibirEmailContatoExcluido:boolean = false;

    @Input()
    set exibirRemoverEmail (value: string) {
        if (!value) {
            this.deveExibirRemoverEmail = true;
        } else {
            this.deveExibirRemoverEmail = value == 'true' ? true : false;
        }
    }

    @Input()
    set exibirEmailContatoExcluido (value: string) {
        if (!value) {
            this.deveExibirEmailContatoExcluido = true;
        } else {
            this.deveExibirEmailContatoExcluido = value == 'true' ? true : false;
        }
    }

    /**
     * Construtor 
     * @param serviceDominio - serviço de dominio para buscar os dominios
     * @author Bruno Sousa
     */
    constructor(private fb: FormBuilder, private doadorService: DoadorService, 
        private autenticacaoService: AutenticacaoService, translate: TranslateService,
        private emailContatoService:EmailContatoService) {
        super();
        this.translate = translate;        
    }

    ngOnInit() {
        this.buildForm();
    }

    /**
     * Método principal de construção do formulário
     * @author Bruno Sousa
     */
    buildForm() {
        this.emailContatoComponent.buildForm();
    }
    
    /**
     * Retorna a lista com os telefones.
     * @author Bruno Sousa
     */
    listarEmailsContato(): EmailContatoDoador[] {
        let retorno: EmailContatoDoador[] = [];
        this.emailContatoComponent.listar().forEach(emailContato => {
            let emailContatoDoador: EmailContatoDoador = new EmailContatoDoador();

            if(!emailContato.excluido){
                emailContatoDoador.id = emailContato.id;
                emailContatoDoador.email = emailContato.email;
                emailContatoDoador.principal = emailContato.principal;
                emailContatoDoador.excluido = emailContato.excluido;
                retorno.push(emailContatoDoador);
            }
        })

        return retorno;
    }

    public preencherDados(idDoador: number, contatos:EmailContatoDoador[]): void {
        this._idDoador = idDoador;
        if(!this.deveExibirEmailContatoExcluido){
            this._contatosEmail = contatos != null ? contatos.filter(email => {
                return email.excluido == false;
            }) : [];
        }else{
            this._contatosEmail = contatos;
        }
    }

    // Override
    public preencherFormulario(contatos: EmailContatoDoador[]): void {}

    nomeComponente(): string {
        return null;
    }

	public get contatosEmail(): EmailContatoDoador[] {
		return this._contatosEmail
    }
    
    public incluir() {
        this.emailContatoComponent.buildForm();
        this.mostraDados = 'hide';
        this.mostraFormulario = '';
    }

    cancelarEdicao() {
        this.mostraFormulario = 'hide';
        this.mostraDados = '';        
    }

    public form(): FormGroup {
        return this.emailContatoComponent.form();
    }

    salvarEmail() {
        if (this.emailContatoComponent.validateForm()) {
            let emailContatoDoador: EmailContatoDoador = this.listarEmailsContato()[0];

            if(this._contatosEmail === undefined){
                this._contatosEmail = [];
            }

            this.doadorService.adicionarEmail(this._idDoador, emailContatoDoador).then(res => {
                emailContatoDoador.id = res.idObjeto;
                emailContatoDoador.excluido = false;
                this._contatosEmail.push(emailContatoDoador);
                this.cancelarEdicao();
            }).catch(error => {
                this.exibirMensagemErro(error);
            });
        }
    }
    
    deveEsconderLinkIncluirEmail(): boolean {
        return this._esconderLinkIncluirEmail == "true" || !this.temPermissaoParaIncluirEmail();
    }

    temPermissaoParaIncluirEmail(): boolean {
        return this.autenticacaoService.temRecurso('ADICIONAR_EMAIL_DOADOR');
    }

    @Input()
    public set esconderLinkIncluirEmail(value: string ) {
        if (!value) {
            this._esconderLinkIncluirEmail = "true";
        }
        else {
            this._esconderLinkIncluirEmail = value;
        }
	}

    private excluirEmail(email: EmailContato) {
        this.emailContatoService.excluirEmailContatoPorId(email.id).then(
            res => {
                email.excluido = true;
                this._contatosEmail = this._contatosEmail.filter(email => {
                    return email.excluido == false;
                })
            },
            (error: ErroMensagem) => {
                this.exibirMensagemErro(error);
            });;
    }

    private exibirMensagemErro(error: ErroMensagem) {
        error.listaCampoMensagem.forEach(obj => {
            this.modalErro.mensagem = obj.mensagem;
        })
        this.modalErro.abrirModal();
    } 

    contatoExcuido(email: EmailContato){
        if(email.excluido){
            return 'contato-excluido-custom';
        }
        return 'texto-bloco';
    }

}