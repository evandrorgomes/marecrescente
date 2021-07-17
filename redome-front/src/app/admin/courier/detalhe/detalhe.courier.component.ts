import { EmailContatoService } from './../../../shared/service/email.contato.service';
import { EmailContatoCourier } from './../../../transportadora/email.contato.courier';
import { ContatoTelefonicoCourier } from './../../../transportadora/contato.telefonico.courier';
import { ContatoTelefoneComponent } from 'app/shared/component/telefone/contato.telefone.component';
import { Courier } from './../../../transportadora/courier';
import { Component, OnInit, ViewChild } from "@angular/core";
import { BaseForm } from "../../../shared/base.form";
import { FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { CourierService } from '../../../transportadora/courier.service';
import { TranslateService } from '@ngx-translate/core';
import { BuildForm } from '../../../shared/buildform/build.form';
import { StringControl } from '../../../shared/buildform/controls/string.control';
import { PermissaoRotaComponente } from '../../../shared/permissao.rota.componente';
import { EnderecoContatoComponent } from '../../../shared/component/endereco/endereco.contato.component';
import { MascaraUtil } from '../../../shared/util/mascara.util';
import { MessageBox } from '../../../shared/modal/message.box';
import { HistoricoNavegacao } from '../../../shared/historico.navegacao';
import { ErroMensagem } from '../../../shared/erromensagem';
import { ErroUtil } from '../../../shared/util/erro.util';
import { FormatterUtil } from '../../../shared/util/formatter.util';
import { RouterUtil } from '../../../shared/util/router.util';
import { ContatoTelefonicoService } from '../../../shared/service/contato.telefonico.service';
import { ContatoTelefonico } from '../../../shared/classes/contato.telefonico';
import { ModalConfirmation, Modal } from '../../../shared/modal/factory/modal.factory';
import { EmailContatoComponent } from '../../../shared/component/email/email.contato.component';

/**
 * @description Detalhe .
 *
 * @author Filipe Paes
 * @export
 * @class DetalheCourierComponent
 * @extends {BaseForm<Courier>}
 */
@Component({
    selector: 'detalhe-courier',
    templateUrl: './detalhe.courier.component.html'
})
export class DetalheCourierComponent  implements PermissaoRotaComponente, OnInit {

    private _buildForm: BuildForm<Courier>;
    public _mostraTelefone: string = '';
    public _mostraFormularioTelefone: string = 'hide';
    public _mostraFormularioDadosBasicos: string = '';
    public _mostraDadosBasicos: string = 'hide';
    public _validarTelefonePrincipal: string = 'false';
    public _exibirLinkInserirTelefone: boolean = false;
    public _exibirBotoesInserirTelefone: boolean = true;
    public _esconderBotaoAdicionarTelefone:string = "true";
    public _exibirLinkEditarDadosBasicos:boolean = false;
    public _exibirBotoesEditarDadosBasicos:boolean = false;
    public _mostraFormularioEmail:string = 'hide';
    public _mostraEmail:string = '';
    public _validarEmailPrincipal:string = "true";
    public _mostraLinkInserirEmail:boolean = true;
    public _esconderBotaoAdicionarEmail:boolean = true;
    public _exibirBotoesEditarEmail:boolean = true;
    public _courier:Courier;
    private _id:number;

    @ViewChild("contatoTelefoneComponent")
    private contatoComponent: ContatoTelefoneComponent;

    @ViewChild("emailContatoComponent")
    private emailComponent: EmailContatoComponent;


    constructor(private router: Router, private activatedRouter: ActivatedRoute,
        private courierService: CourierService, private messageBox: MessageBox,
        private translate: TranslateService,
        private contatoTelefonicoService: ContatoTelefonicoService,
        private emailContatoService:EmailContatoService) {

        this._buildForm = new BuildForm<Courier>()
            .add(new StringControl("nome", [ Validators.required ] ))
            .add(new StringControl("CPF", [ Validators.required ] ))
            .add(new StringControl("RG", [ Validators.required ] ));

    }

    ngOnInit(): void {
        RouterUtil.recuperarParametroDaActivatedRoute(this.activatedRouter, "idCourier").then(res => {
            if (res == "novo") {
                this.modoDeInclusao();
            }
            else {
                this._id = <number>res;
                this.modoDeAlteracao();
                this.obterCourier();
            }
        });
    }

    private obterCourier() {
        this.courierService.obterCourier(this._id).then(courier => {
            this._courier = new Courier().jsonToEntity(courier);
        });
    }

    public excluirEmail(email: EmailContatoCourier) {
        this.emailContatoService.excluirEmailContatoPorId(email.id).then(res => {
            this.messageBox.alert(res)
                .withTarget(this)
                .withCloseOption((target?: any) => {
                    this.obterCourier();
                })
                .show();
        },
        (error: ErroMensagem) => {
            ErroUtil.exibirMensagemErro(error, this.messageBox);
        });
    }
    public salvarContatoEmail(): void {
        if (this.emailComponent.validateForm()) {
            let emails: EmailContatoCourier[] = [];
            this.emailComponent.listar().forEach(contato => {
                let email: EmailContatoCourier = new EmailContatoCourier().jsonToEntity(contato);
                email.courier = this._courier;
                emails.push(email);
            });
            this.courierService.inserirEmail(this._id, emails[0]).then(res => {
                this.messageBox.alert(res.mensagem)
                    .withTarget(this)
                    .withCloseOption((target?: any) => {
                        this.obterCourier();
                        this.cancelarEdicaoEmail();
                    })
                    .show();
            }, (error: ErroMensagem) => {
                ErroUtil.exibirMensagemErro(error, this.messageBox);
            });
        }
    }

    public cancelarEdicaoEmail() {
        this._mostraFormularioEmail = 'hide';
        this._mostraEmail = '';
        this._mostraLinkInserirEmail = true;
    }
    private mostrarFormularioEmail() {
        this._mostraEmail = 'hide';
        this._mostraFormularioEmail = '';
        this._mostraLinkInserirEmail = false;
    }
    public incluirEmail() {
        if (!this._courier.emails || this._courier.emails.length == 0){
            this._validarEmailPrincipal = "true";
        }

        this.emailComponent.buildForm();
        this.mostrarFormularioEmail();
    }
    public form(): FormGroup {
        return <FormGroup>this._buildForm.form;
    }
    public preencherFormulario(entidade: Courier): void {
        throw new Error("Method not implemented.");
    }
    nomeComponente(): string {
        return 'DetalheCourierComponent';
    }

    private modoDeInclusao() {
        this._mostraTelefone = 'hide';
        this._mostraFormularioTelefone = '';
        this._mostraDadosBasicos = 'hide';
        this._mostraFormularioDadosBasicos='';
        this._exibirBotoesInserirTelefone = false;
        this._esconderBotaoAdicionarTelefone = "false";
        this._mostraFormularioEmail = '';
        this._mostraEmail = 'hide';
        this._mostraLinkInserirEmail = false;
        this._esconderBotaoAdicionarEmail = false;
        this._exibirBotoesEditarEmail = false;
    }
    private modoDeAlteracao(){
        this._mostraTelefone = '';
        this._mostraDadosBasicos = '';
        this._mostraFormularioTelefone = 'hide';
        this._mostraFormularioDadosBasicos='hide';
        this._esconderBotaoAdicionarTelefone = "true";
        this._exibirLinkEditarDadosBasicos = true;
        this._exibirLinkInserirTelefone = true;
        this._mostraFormularioEmail = 'hide';
        this._mostraEmail = '';
        this._mostraLinkInserirEmail = true;
        this._esconderBotaoAdicionarEmail = true;
        this._exibirBotoesEditarEmail = true;
    }

    public editarDadosBasicos(){
        let value: any = {
            'nome': this._courier.nome,
            'CPF': FormatterUtil.aplicarMascaraCpf(this._courier.cpf),
            'RG': this._courier.rg
        }
        this._buildForm.form.reset();
        this._buildForm.value = value;
        this.modoEdicaoDadosPessoais();
    }

    private modoEdicaoDadosPessoais() {
        this._mostraDadosBasicos = 'hide';
        this._exibirLinkEditarDadosBasicos = false;
        this._mostraFormularioDadosBasicos = '';
        this._exibirBotoesEditarDadosBasicos = true;
    }

    public cancelarEdicaoDadosBasicos() {
        this._mostraDadosBasicos = '';
        this._exibirLinkEditarDadosBasicos = true;
        this._mostraFormularioDadosBasicos = 'hide';
        this._exibirBotoesEditarDadosBasicos = false;
    }

    public incluirTelefone() {
        this._mostraTelefone = 'hide';
        this._mostraFormularioTelefone = '';
        this._exibirBotoesInserirTelefone = true;
        this._esconderBotaoAdicionarTelefone = "true";
        this._exibirLinkInserirTelefone = false;
    }

    public cancelarEdicaoTelefone() {
        this._mostraFormularioTelefone = 'hide';
        this._mostraTelefone = '';
        this._exibirBotoesInserirTelefone = true
        this._exibirLinkInserirTelefone = true;;
    }

    public get maskCpf(): Array<string | RegExp> {
        return MascaraUtil.cpf;
    }

    public validarNovoCourier():boolean{
        let validarContato: boolean = this.contatoComponent.validateForm();
        let validarForm:boolean = this._buildForm.valid;
        let validarEmail:boolean = this.emailComponent.validateForm();
        return validarContato && validarForm && validarEmail;
    }

    public salvarNovo(){
        if(this.validarNovoCourier()){
            let courier: Courier = this.carregarCourierDoFormulario();
            courier.contatosTelefonicos = [];
            courier.emails = [];
            this.contatoComponent.listarTelefonesContato().forEach(contato => {
                let contatoTelefonico: ContatoTelefonicoCourier = new ContatoTelefonicoCourier().jsonToEntity(contato);
                courier.contatosTelefonicos.push(contatoTelefonico);
            });
            this.emailComponent.listar().forEach(e=>{
                courier.emails.push(<EmailContatoCourier>e);
            });
            this.courierService.inserirCourier(courier).then(res => {
                this.messageBox.alert(res.mensagem)
                    .withTarget(this)
                    .withCloseOption((target?: any) => {
                        this.router.navigateByUrl("/admin/couriers").then(res => {
                            HistoricoNavegacao.removerUltimoAcesso();
                            this.ngOnInit();
                        });
                    })
                    .show();
            }, (error: ErroMensagem) => {
                ErroUtil.exibirMensagemErro(error, this.messageBox);
            });
        }
    }

    private carregarCourierDoFormulario() {
        let courier: Courier = new Courier();
        courier.nome = this._buildForm.get("nome").value;
        courier.cpf = FormatterUtil.obterCPFSemMascara(this._buildForm.get("CPF").value);
        courier.rg = this._buildForm.get("RG").value;
        return courier;
    }

    public salvarDadosBasicos(){
        if(this._buildForm.valid){
            let courier: Courier = this.carregarCourierDoFormulario();
            this.courierService.atualizarCourier(this._courier.id, courier).then(res => {
                this.messageBox.alert(res.mensagem)
                    .withTarget(this)
                    .withCloseOption((target?: any) => {
                        this.obterCourier();
                        this.cancelarEdicaoDadosBasicos();
                    })
                    .show();
            }, (error: ErroMensagem) => {
                ErroUtil.exibirMensagemErro(error, this.messageBox);
            });
        }
    }


    public inativarCourier(){
            this.translate.get("manterCourier.mensagem.confirmarInativacao").subscribe(mensagem =>{
                let modalConfirmacao: ModalConfirmation = this.messageBox.confirmation(mensagem);
                modalConfirmacao.yesOption = () => {
                    this.courierService.inativar(this._courier.id).then(res => {
                        let modal: Modal = this.messageBox.alert(res.mensagem);
                        modal.closeOption = () => {
                            this.router.navigateByUrl("/admin/couriers").then(res => {
                                HistoricoNavegacao.removerUltimoAcesso();
                                this.ngOnInit();
                            });
                        };
                        modal.show();

                    }, (error: ErroMensagem) => {
                        this.messageBox.alert(ErroUtil.extrairMensagensErro(error)).show();
                    });
                };
                modalConfirmacao.show();
            });
    }

    public excluirTelefone(telefone: ContatoTelefonico) {
        this.contatoTelefonicoService.excluirContatoTelefonicoPorId(telefone.id).then(res => {
            this.messageBox.alert(res)
                .withTarget(this)
                .withCloseOption((target?: any) => {
                    this.obterCourier();
                })
                .show();
        },
        (error: ErroMensagem) => {
            ErroUtil.exibirMensagemErro(error, this.messageBox);
        });
    }

    public salvarTelefone(telefone: ContatoTelefonico) {
        if (this.contatoComponent.validateForm()) {
            let contatosTelefonico: ContatoTelefonicoCourier[] = [];
            this.contatoComponent.listarTelefonesContato().forEach(contato => {
                let contatoTelefonico: ContatoTelefonicoCourier = new ContatoTelefonicoCourier().jsonToEntity(contato);
                contatosTelefonico.push(contatoTelefonico);
            });
            this.courierService.salvarContatoCourier(this._courier.id, contatosTelefonico[0]).then(res => {
                this.messageBox.alert(res.mensagem)
                    .withTarget(this)
                    .withCloseOption((target?: any) => {
                        this.obterCourier();
                        this.cancelarEdicaoTelefone();
                    })
                    .show();
            }, (error: ErroMensagem) => {
                ErroUtil.exibirMensagemErro(error, this.messageBox);
            });
        }
    }


}
