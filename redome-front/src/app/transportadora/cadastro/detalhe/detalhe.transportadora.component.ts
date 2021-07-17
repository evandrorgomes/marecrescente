import { Component, OnInit, ViewChild } from "@angular/core";
import { FormControl, FormGroup, ValidationErrors, Validators } from "@angular/forms";
import { ActivatedRoute, Router } from "@angular/router";
import { TranslateService } from "@ngx-translate/core";
import { EmailContatoComponent } from "app/shared/component/email/email.contato.component";
import { ContatoTelefoneComponent } from "app/shared/component/telefone/contato.telefone.component";
import { ContatoTelefonicoTransportadora } from "app/shared/model/contato.telefonico.transportadora";
import { EmailContatoTransportadora } from "app/shared/model/email.contato.transportadora";
import { EnderecoContatoTransportadora } from "app/shared/model/endereco.contato.transportadora";
import { Transportadora } from "app/shared/model/transportadora";
import { ContatoTelefonicoService } from "app/shared/service/contato.telefonico.service";
import { EmailContatoService } from "app/shared/service/email.contato.service";
import { TransportadoraService } from "app/shared/service/transportadora.service";
import { UsuarioService } from '../../../admin/usuario/usuario.service';
import { BaseForm } from "../../../shared/base.form";
import { BuildForm } from "../../../shared/buildform/build.form";
import { ArrayControl } from '../../../shared/buildform/controls/array.control';
import { StringControl } from "../../../shared/buildform/controls/string.control";
import { ContatoTelefonico } from '../../../shared/classes/contato.telefonico';
import { Usuario } from '../../../shared/dominio/usuario';
import { ErroMensagem } from '../../../shared/erromensagem';
import { HistoricoNavegacao } from '../../../shared/historico.navegacao';
import { Modal, ModalConfirmation } from '../../../shared/modal/factory/modal.factory';
import { MessageBox } from "../../../shared/modal/message.box";
import { ErroUtil } from '../../../shared/util/erro.util';
import { RouterUtil } from '../../../shared/util/router.util';
import { EnderecoContatoComponent } from "app/shared/component/endereco/endereco.contato.component";


/**
 * Classe de controle da tela de cadastro de transportadora.
 *
 * @author Filipe Paes
 * @export
 * @class DetalheTransportadoraComponent
 * @extends {BaseForm<Transportadora>}
 */
@Component({
    selector: 'detalhe-transportadora',
    templateUrl: './detalhe.transportadora.component.html'
})
export class DetalheTransportadoraComponent extends BaseForm<Transportadora> implements OnInit {

    public transportadora: Transportadora;
    public dadosSomenteLeitura:Boolean = false;
    public formulario: BuildForm<Transportadora>;
    public _id:number;
    public _modoInclusaoHabilitado: boolean = true;

    public _exibirLinkEditarUsuarios:boolean = false;
    public _mostraFormularioUsuarios:string = 'hide';
    public _mostraUsuarios:string = '';
    public _exibirBotoesEditarUsuarios:boolean = false;
    private _usuariosDisponiveis: Usuario[] = [];
    private _usuariosSelecionados: Usuario[] = [];
    private _buildFormDisponiveis:BuildForm<any>;
    private _buildFormSelecionados:BuildForm<any>;

    public _mostraEndereco:string = '';
    public _exibirLinkEditarEndereco:boolean = true;
    public _mostraFormularioEndereco:string = 'hide';
    public _exibirBotoesEditarEndereco:boolean = true;
    public _mostraFormularioTelefone:string = '';

    public _mostraTelefone:string = 'hide';
    public _exibirLinkInserirTelefone:boolean = false;
    public _exibirBotaoNovoTelefone:boolean = false;
    public _validarTelefonePrincipal: string = 'false';
    public _exibirBotoesInserirTelefone = true;
    public _mostraLinkInserirTelefone = false;

    public _validarEmailPrincipal:string = 'false';
    public _mostraEmail:string = 'hide';
    public _mostraFormularioEmail:string = '';
    public _mostraLinkInserirEmail:boolean = false;
    public _exibirBotoesEditarEmail:boolean = false;



    @ViewChild("endereco")
    private enderecoComponent: EnderecoContatoComponent;

    @ViewChild("contatoTelefoneComponent")
    private contatoTelefoneComponent: ContatoTelefoneComponent;


    @ViewChild("emailContatoComponent")
    private emailComponent: EmailContatoComponent;



    constructor(protected translate: TranslateService,
                protected activatedRouter: ActivatedRoute,
                protected router: Router,
                protected transportadoraService: TransportadoraService,
                protected messageBox: MessageBox,
                protected usuarioService:UsuarioService,
                protected emailContatoService: EmailContatoService,
                protected contatoTelefonicoService: ContatoTelefonicoService) {
        super();
        this.translate = translate;

        this.formulario = new BuildForm<Transportadora>();
        this.formulario.add(
            new StringControl("nome", [ Validators.required ])
        );

        this._buildFormDisponiveis = new BuildForm<any>()
        .add(new ArrayControl("selecionados"));

        this._buildFormSelecionados = new BuildForm<any>();
        this._buildFormSelecionados.add(new ArrayControl("selecionados", [this.ValidateListMin, this.ValidateMaxItens] ));
    }

    ngOnInit(): void {
        RouterUtil.recuperarParametroDaActivatedRoute(this.activatedRouter, "idTransportadora").then(res => {
            if (res == "nova") {
                this.modoInclusao();
            }
            else {
                this.modoEdicao();
                this._id = <number>res;
                this.obterTransportadora();
            }
        });

        this.usuarioService.listarUsuarioTransportadora().then(res=>{
            if (res) {
                res.forEach(usu => {
                    this._usuariosDisponiveis.push(new Usuario().jsonToEntity(usu));
                });
            }
         }, (error: ErroMensagem) => {
            this.messageBox.alert(ErroUtil.extrairMensagensErro(error)).show();
        });
    }
    private modoEdicao() {
        this._modoInclusaoHabilitado = false;
        this._exibirLinkEditarUsuarios = true;
        this._mostraUsuarios = '';
        this._mostraEmail ='';
        this._mostraFormularioEmail = 'hide';
        this._mostraLinkInserirEmail = true;
        this._exibirBotoesEditarEmail = true;
        this._mostraFormularioTelefone='hide';
        this._mostraTelefone = '';
        this._exibirLinkInserirTelefone = true;
        this._exibirBotaoNovoTelefone = true;
    }

    private modoInclusao() {
        this._modoInclusaoHabilitado = true;
        this._mostraFormularioUsuarios = '';
        this._mostraUsuarios = 'hide';
        this._mostraEndereco = 'hide';
        this._exibirLinkEditarEndereco = false;
        this._mostraFormularioEndereco = '';
        this._exibirBotoesEditarEndereco = false;
        this._mostraEmail ='hide';
        this._mostraFormularioEmail = '';
        this._mostraLinkInserirEmail = false;
        this._exibirBotoesEditarEmail = false;
        this._exibirBotoesInserirTelefone = false;
    }

    public excluirEmail(email: EmailContatoTransportadora) {
        this.emailContatoService.excluirEmailContatoPorId(email.id).then(res => {
            this.messageBox.alert(res)
                .withTarget(this)
                .withCloseOption((target?: any) => {
                    this.obterTransportadora();
                })
                .show();
        },
        (error: ErroMensagem) => {
            ErroUtil.exibirMensagemErro(error, this.messageBox);
        });
    }

    public editarEndereco(): void {
        this.enderecoComponent.clearForm();
        if (this.transportadora.endereco) {
            let endereco: EnderecoContatoTransportadora = this.transportadora.endereco;
            if (endereco) {
                this.enderecoComponent.preencherFormulario(endereco);
            }
            else {
                this.enderecoComponent.setValoresPadroes();
                this.enderecoComponent.configEndNacionalForm();
            }
        }
        else {
            this.enderecoComponent.setValoresPadroes();
            this.enderecoComponent.configEndNacionalForm();
        }

        this.mostrarFormularioEndereco();
    }

    private mostrarFormularioEndereco() {
        this._mostraEndereco = 'hide';
        this._mostraFormularioEndereco = '';
        this._exibirLinkEditarEndereco = false;
        this._exibirBotoesEditarEndereco = true;
    }
    public cancelarEdicaoEndereco(): void {
        this._mostraFormularioEndereco = 'hide';
        this._mostraEndereco = '';
        this._exibirLinkEditarEndereco = true;
    }

    public cancelarEdicaoTelefone() {
        this._mostraFormularioTelefone = 'hide';
        this._mostraTelefone = '';
        this._exibirLinkInserirTelefone = true;
    }

    private mostrarFormularioEmail() {
        this._mostraEmail = 'hide';
        this._mostraFormularioEmail = '';
        this._mostraLinkInserirEmail = false;
    }
    public incluirEmail() {
        if (!this.transportadora.emails || this.transportadora.emails.length == 0){
            this._validarEmailPrincipal = "true";
        }

        this.emailComponent.buildForm();
        this.mostrarFormularioEmail();
    }

    private mostrarFormularioTelefone() {
        this._mostraTelefone = 'hide';
        this._mostraFormularioTelefone = '';
        this._mostraLinkInserirTelefone = false;
        this._exibirBotoesInserirTelefone = true;
        this._exibirBotaoNovoTelefone = false;
    }
    public incluirTelefone() {
        if (!this.transportadora.telefones || this.transportadora.telefones.length == 0){
            this._validarTelefonePrincipal = "true";
        }

        this.contatoTelefoneComponent.buildForm();
        this.mostrarFormularioTelefone();
    }

    public excluirTelefone(telefone: ContatoTelefonico) {
        this.contatoTelefonicoService.excluirContatoTelefonicoPorId(telefone.id).then(res => {
            this.messageBox.alert(res)
                .withTarget(this)
                .withCloseOption((target?: any) => {
                    this.obterTransportadora();
                })
                .show();
        },
        (error: ErroMensagem) => {
            ErroUtil.exibirMensagemErro(error, this.messageBox);
        });
    }

    private obterTransportadora() {
        this.transportadoraService.obterTransportadora(this._id).then(res => {
            this.transportadora = new Transportadora().jsonToEntity(res);
            this.formulario.get("nome").value = this.transportadora.nome;
            this._modoInclusaoHabilitado = false;
            this.dadosSomenteLeitura = true;
        });
    }

    public salvar(): void {
        if(this.validarNovaTransportadora()){
            let transportadoraAlterada: Transportadora = this.formulario.value;
            transportadoraAlterada.usuarios = this._usuariosSelecionados;
            transportadoraAlterada.endereco = <EnderecoContatoTransportadora>this.enderecoComponent.obterEndereco();
            transportadoraAlterada.telefones = [];
            transportadoraAlterada.emails = [];
            this.contatoTelefoneComponent.listarTelefonesContato().forEach(t=>{
                transportadoraAlterada.telefones.push(<ContatoTelefonicoTransportadora>t);
            });
            this.emailComponent.listar().forEach(e=>{
                transportadoraAlterada.emails.push(<EmailContatoTransportadora>e);
            });

            this.transportadoraService.salvarTransportadora(transportadoraAlterada).then(res=>{
                let modal: Modal = this.messageBox.alert(res.mensagem);
                modal.closeOption = () => {
                    this.router.navigateByUrl(HistoricoNavegacao.urlRetorno());
                };
                modal.show();
            }, (error: ErroMensagem) => {
                this.messageBox.alert(ErroUtil.extrairMensagensErro(error)).show();
            });

        }
    }

    public validarNovaTransportadora():boolean{
        let validacaoDadosBasicos:boolean = this.formulario.valid;
        let validacaoUsuarios:boolean =  this._buildFormSelecionados.valid;
        let validacaoEndereco:boolean = this.enderecoComponent.validateForm();
        let validacaoTelefone:boolean = this.contatoTelefoneComponent.validateForm();
        let validacaoEmail:boolean = this.emailComponent.validateForm();
        return validacaoDadosBasicos && validacaoUsuarios && validacaoEndereco && validacaoTelefone && validacaoEmail;
    }


    public salvarEdicaoEndereco(): void {
        if (this.enderecoComponent.validateForm()) {
            let endereco: EnderecoContatoTransportadora =
                new EnderecoContatoTransportadora().jsonToEntity(this.enderecoComponent.obterEndereco());
                endereco.id = this.transportadora.endereco && this.transportadora.endereco.id?this.transportadora.endereco.id:null;
                this.transportadoraService.inserirEndereco(this.transportadora.id, endereco).then(res => {
                    this.messageBox.alert(res.mensagem)
                        .withTarget(this)
                        .withCloseOption((target?: any) => {
                            this.obterTransportadora();
                            this.cancelarEdicaoEndereco();
                        })
                        .show();
            }, (error: ErroMensagem) => {
                ErroUtil.exibirMensagemErro(error, this.messageBox);
            });
        }
    }

    public salvarContatoTelefonico(): void {
        if (this.contatoTelefoneComponent.validateForm()) {
            let contatosTelefonico: ContatoTelefonicoTransportadora[] = [];
            this.contatoTelefoneComponent.listarTelefonesContato().forEach(contato => {
                let contatoTelefonico: ContatoTelefonicoTransportadora = new ContatoTelefonicoTransportadora().jsonToEntity(contato);
                contatoTelefonico.transportadora = this.transportadora;
                contatosTelefonico.push(contatoTelefonico);
            });
            this.transportadoraService.inserirTelefone(this.transportadora.id, contatosTelefonico[0]).then(res => {
                this.messageBox.alert(res.mensagem)
                    .withTarget(this)
                    .withCloseOption((target?: any) => {
                        this.obterTransportadora();
                        this.cancelarEdicaoTelefone();
                    })
                    .show();
            }, (error: ErroMensagem) => {
                ErroUtil.exibirMensagemErro(error, this.messageBox);
            });
        }
    }
    public salvarContatoEmail(): void {
        if (this.emailComponent.validateForm()) {
            let emails: EmailContatoTransportadora[] = [];
            this.emailComponent.listar().forEach(contato => {
                let email: EmailContatoTransportadora = new EmailContatoTransportadora().jsonToEntity(contato);
                email.transportadora = this.transportadora;
                emails.push(email);
            });
            this.transportadoraService.inserirEmail(this._id, emails[0]).then(res => {
                this.messageBox.alert(res.mensagem)
                    .withTarget(this)
                    .withCloseOption((target?: any) => {
                        this.obterTransportadora();
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


    public salvarEdicaoUsuarios(){
        if (this._buildFormSelecionados.valid) {
            this.transportadoraService.atualizarUsuarios(this._id, this.usuariosSelecionados).then(res => {
                this.messageBox.alert(res.mensagem)
                    .withTarget(this)
                    .withCloseOption((target?: any) => {
                        this.obterTransportadora();
                        this.cancelarEdicaoUsuarios();
                    })
                    .show();
            }, (error: ErroMensagem) => {
                ErroUtil.exibirMensagemErro(error, this.messageBox);
            });
        }
    }
    public cancelarEdicaoUsuarios(): void {
        this._exibirLinkEditarUsuarios = true;
        this._mostraFormularioUsuarios = 'hide';
        this._mostraUsuarios = '';
        this._exibirBotoesEditarUsuarios = false;
    }
    public editarUsuarios(){
        this._exibirLinkEditarUsuarios = false;
        this._mostraFormularioUsuarios = '';
        this._mostraUsuarios = 'hide';
        this._exibirBotoesEditarUsuarios = true;

        if (this.transportadora.usuarios &&this.transportadora.usuarios.length > 0) {
            let listaUsuarios: any[] = [];
            this.transportadora.usuarios.forEach( usuario =>{
                listaUsuarios.push(usuario.id);
            })
            this._usuariosSelecionados = this.transportadora.usuarios;
        }
    }


    inativar(): void{
        this.translate.get("manterTransportadora.mensagem.confirmarInativacao").subscribe(mensagem =>{
            let modalConfirmacao: ModalConfirmation = this.messageBox.confirmation(mensagem);
            modalConfirmacao.yesOption = () => {
                this.transportadoraService.inativar(this.transportadora.id).then(res => {
                    let modal: Modal = this.messageBox.alert(res.mensagem);
                    modal.closeOption = () => {
                        this.router.navigateByUrl(HistoricoNavegacao.urlRetorno());
                    };
                    modal.show();

                }, (error: ErroMensagem) => {
                    this.messageBox.alert(ErroUtil.extrairMensagensErro(error)).show();
                });
            };
            modalConfirmacao.show();
        });
    }

    ValidateListMin(control: FormControl): ValidationErrors {
        let value: any[] = control.value;
        if (!value && control.dirty && !control.pristine) {
            if(!value || value.length < 1){
                return { "required" : true }
            }
        }
        return null;
    }


    ValidateMaxItens(control: FormControl): ValidationErrors {
        let value: any[] = control.value;
        if (value && control.dirty && !control.pristine) {
            if(value && value.length > 3){
                return { "invalidMaxItens" : true }
            }
        }
        return null;
    }

    public adicionarUsuarios(usuariosLista? : any[]) {
        if ((this._buildFormDisponiveis.value && this._buildFormDisponiveis.value.selecionados &&
            this._buildFormDisponiveis.value.selecionados.length != 0)||usuariosLista) {
            let selecionados = usuariosLista? usuariosLista: this._buildFormDisponiveis.value.selecionados;
            for (let idx = 0; idx < selecionados.length; idx++) {
                let indice:number = 0;
                let usuario: Usuario = this._usuariosDisponiveis
                    .find((usuario, index) => {
                        indice = index;
                        return usuario.id == selecionados[idx];
                    });
                if (usuario) {
                    this._usuariosDisponiveis.splice(indice, 1);
                    this._usuariosSelecionados.push(usuario);
                }
            }
            this._buildFormSelecionados.getChildAsBuildFormControl("selecionados").value =
                this._usuariosSelecionados.map(medico => medico.id);
        }
    }

    public removerUsuarios() {
        if (this._buildFormSelecionados.value && this._buildFormSelecionados.value.selecionados &&
            this._buildFormSelecionados.value.selecionados.length != 0) {
            let selecionados = this._buildFormSelecionados.value.selecionados;
            for (let idx = 0; idx < selecionados.length; idx++) {
                let indice:number = 0;
                let usuario: Usuario = this._usuariosSelecionados
                    .find((usuario, index) => {
                        indice = index;
                        return usuario.id == selecionados[idx];
                    });
                if (usuario) {
                    this._usuariosSelecionados.splice(indice, 1);
                    this._usuariosDisponiveis.push(usuario);
                }
            }
            this._buildFormSelecionados.form.reset();
            if (this._usuariosSelecionados.length != 0) {
                this._buildFormSelecionados.getChildAsBuildFormControl("selecionados").value =
                    this._usuariosSelecionados.map(medico => medico.id);
            }
        }
    }


    nomeComponente(): string {
        return "DetalheTransportadoraComponent";
    }

    public form(): FormGroup {
        return this.formulario.form;
    }

    public cancelarEdicaoDadosBasicos(): void{
        this.dadosSomenteLeitura = true;
    }

    public formUsuariosDisponiveis(): FormGroup {
        return this._buildFormDisponiveis.form;
    }

    public formUsuariosSelecionados(): FormGroup {
        return this._buildFormSelecionados.form;
    }


    public preencherFormulario(entidade: Transportadora): void {
        throw new Error("Method not implemented.");
    }

    protected habilitarModoInclusao(): void{
        this._modoInclusaoHabilitado = true;
    }

    public get modoInclusaoHabilitado(): boolean{
        return this._modoInclusaoHabilitado;
    }

    public cancelarEdicao(): void{
        this.dadosSomenteLeitura = true;
    }

    /**
     * Getter usuariosDisponiveis
     * @return {Usuario[] }
     */
	public get usuariosDisponiveis(): Usuario[]  {
		return this._usuariosDisponiveis;
	}

    /**
     * Setter usuariosDisponiveis
     * @param {Usuario[] } value
     */
	public set usuariosDisponiveis(value: Usuario[] ) {
		this._usuariosDisponiveis = value;
    }


    /**
     * Getter usuariosSelecionados
     * @return {Usuario[] }
     */
	public get usuariosSelecionados(): Usuario[]  {
		return this._usuariosSelecionados;
	}

    /**
     * Setter usuariosSelecionados
     * @param {Usuario[] } value
     */
	public set usuariosSelecionados(value: Usuario[] ) {
		this._usuariosSelecionados = value;
	}


}
