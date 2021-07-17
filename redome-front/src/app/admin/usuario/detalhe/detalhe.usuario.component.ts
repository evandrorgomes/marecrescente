import { BaseForm } from "../../../shared/base.form";
import { UsuarioLogado } from "../../../shared/dominio/usuario.logado";
import { Component, ViewChild, OnInit, ElementRef } from "@angular/core";
import { FormGroup, Validators, NgSelectOption } from "@angular/forms";
import { BuildForm } from "../../../shared/buildform/build.form";
import { TranslateService } from "@ngx-translate/core";
import { ArrayUtil } from "../../../shared/util/array.util";
import { StringControl } from "../../../shared/buildform/controls/string.control";
import { Perfil } from "../../../shared/dominio/perfil";
import { SelectComponent } from "../../../shared/component/inputcomponent/select.component";
import { RouterUtil } from "app/shared/util/router.util";
import { ActivatedRoute, Router } from "@angular/router";
import { UsuarioService } from "../usuario.service";
import { ErroMensagem } from "app/shared/erromensagem";
import { ErroUtil } from "app/shared/util/erro.util";
import { MessageBox } from "../../../shared/modal/message.box";
import { Usuario } from "app/shared/dominio/usuario";
import { PerfilService } from "../../perfil/perfil.service";
import { SistemaService } from "../../../shared/service/sistema.service";
import { Sistema } from "app/shared/dominio/sistema";
import { Modal, ModalConfirmation } from "app/shared/modal/factory/modal.factory";
import { HistoricoNavegacao } from "../../../shared/historico.navegacao";
import { BancoSangueCordao } from "app/shared/dominio/banco.sangue.cordao";
import { BscupService } from "app/shared/service/bscup.service";
import {Perfis} from "../../../shared/enums/perfis";

/**
 * @description Detalhe do usuário ao selecionar um item na tela de
 * listagem de usuários cadastrados.
 *
 * @author Pizão
 * @export
 * @class DetalheUsuarioComponent
 * @extends {BaseForm<UsuarioLogado>}
 */
@Component({
    selector: 'detalhe-usuario',
    templateUrl: './detalhe.usuario.component.html'
})
export class DetalheUsuarioComponent extends BaseForm<UsuarioLogado> implements OnInit {

    @ViewChild("perfisDisponiveis")
    private perfisDisponiveisComponent: SelectComponent;

    @ViewChild("perfisSelecionados")
    private perfisSelecionadosComponent: SelectComponent;

    private mensagemRemoverPerfilMedico: string = '';


    public usuario: Usuario;
    protected listaTodosPerfis: Perfil[] = [];
    public listaPerfisDisponiveis: Perfil[] = [];
    public listaPerfisSelecionados: Perfil[] = [];
    public mensagemPerfisNaoSelecionados: string = null;

    public listaSistemas: Sistema[] = [];
    public idSistemaSelecionado: Number;

    public listaBscups: BancoSangueCordao[] = [];
    public idBscupSelecionado: Number;
    public exibirSelecaoBscups: boolean;
    public mensagemBscupNaoSelecionado: string = null;

    public formulario: BuildForm<Usuario>;
    private _dadosPessoaisSomenteLeitura: boolean = true;
    private _permissoesSomenteLeitura: boolean = true;

    /**
     * Indica quando o modo de inclusão está habilitado (no menu principal, o salvar global
     * é habilitado) deve ser exibido.
     * Habilitado somente na tela NovoUsuarioComponent.
     */
    private _modoInclusaoHabilitado: boolean = false;


    constructor(protected translate: TranslateService,
                protected activatedRouter: ActivatedRoute,
                protected router: Router,
                protected usuarioService: UsuarioService,
                protected messageBox: MessageBox,
                protected perfilService: PerfilService,
                protected sistemaService: SistemaService,
                protected bscupService: BscupService) {
        super();
        this.translate = translate;

        this.formulario = new BuildForm<Usuario>();
        this.formulario.add(
            new StringControl("nome", [ Validators.required ]),
            new StringControl("username", [ Validators.required ]),
            new StringControl("email", [ Validators.required ])
        );

        this.translate.get('manterUsuario.mensagem.removerperfilmedico').subscribe(res => {
            this.mensagemRemoverPerfilMedico = res;
        });

    }

    ngOnInit(): void {
        this.listarPerfis();
        this.listarSistemas();
        this.listarBscups();
    }

    /**
     * @description Lista os perfis disponíveis e pré seleciona, de acordo
     * com os perfis que o usuário já possui.
     * @author Pizão
     * @protected
     */
    protected listarPerfis(): void{
        RouterUtil.recuperarParametroDaActivatedRoute(
            this.activatedRouter, "idUsuario").then(idUsuario => {
            this.usuarioService.obterUsuario(idUsuario).then(res => {
                this.usuario = new Usuario().jsonToEntity(res);

                this.perfilService.listarPerfis().then(perfis => {
                    this.listaPerfisDisponiveis = [];
                    this.listaPerfisSelecionados = [];

                    this.usuario.perfis.forEach(perfil => {
                        if (Perfis.MEDICO === perfil.id) {
                            this.listaPerfisSelecionados.push(perfil);
                        }
                    })

                    perfis.forEach(jsonPerfil => {
                        let perfil: Perfil = new Perfil().jsonToEntity(jsonPerfil);

                        if(ArrayUtil.contains(this.usuario.perfis, perfil, "id")){
                            this.listaPerfisSelecionados.push(perfil);
                        }
                        else {
                            this.listaPerfisDisponiveis.push(perfil);
                        }
                    });

                    this.listaTodosPerfis = ArrayUtil.clone(this.listaPerfisDisponiveis);
                    ArrayUtil.sort(this.listaPerfisDisponiveis, "descricao");

                    if(this.usuario.bancoSangue){
                        this.idBscupSelecionado = this.usuario.bancoSangue.id;
                    }

                }, (error: ErroMensagem) => {
                    this.messageBox.alert(ErroUtil.extrairMensagensErro(error));
                });

            }, (error: ErroMensagem) => {
                this.messageBox.alert(ErroUtil.extrairMensagensErro(error));
            });
        });
    }

    /**
     * @description Carrega a combo de sistemas, utilizada como filtro
     * para os perfis disponíveis para seleção.
     * @author Pizão
     * @protected
     */
    protected listarSistemas():void{
        this.sistemaService.listarSistemas().then(sistemas => {
            this.listaSistemas = [];

            sistemas.forEach(jsonSistema => {
                let sistema: Sistema = new Sistema().jsonToEntity(jsonSistema);
                this.listaSistemas.push(sistema);
            });

        }, (error: ErroMensagem) => {
            this.messageBox.alert(ErroUtil.extrairMensagensErro(error));
        });
    }

    /**
     * @description Lista todos os bancos de sangue de cordão disponíveis.
     * @author Pizão
     * @private
     */
    private listarBscups(): void{
        this.bscupService.listarBscups().then(bscups => {
            this.listaBscups = [];

            bscups.forEach(jsonBscup => {
                let bancoSangue: BancoSangueCordao = new BancoSangueCordao().jsonToEntity(jsonBscup);
                this.listaBscups.push(bancoSangue);
            });

        }, (error: ErroMensagem) => {
            this.messageBox.alert(ErroUtil.extrairMensagensErro(error));
        });
    }

    public form(): FormGroup {
        return this.formulario.form;
    }

    public preencherFormulario(entidade: UsuarioLogado): void {
        throw new Error("Method not implemented.");
    }

    /**
     * @description Realiza a mudança de visualização de
     * leitura para edição no bloco de dados pessoais.
     */
    public set dadosPessoaisSomenteLeitura(value: boolean) {
        this._dadosPessoaisSomenteLeitura = value;

        if(!this._dadosPessoaisSomenteLeitura){
            this.formulario.value = this.usuario;
        }
    }

    public get dadosPessoaisSomenteLeitura(): boolean {
        return this._dadosPessoaisSomenteLeitura;
    }

    /**
     * @description Realiza a mudança de visualização de
     * leitura para edição no bloco de perfis.
     */
    public set permissoesSomenteLeitura(value: boolean) {
        this._permissoesSomenteLeitura = value;
    }

    public get permissoesSomenteLeitura(): boolean {
        return this._permissoesSomenteLeitura;
    }

    /**
     * @description Lista os perfis formatados para serem exibidos
     * alinhados na mesma string.
     * @author Pizão
     * @returns {string}
     */
    public listarPerfisFormatados(): string {
        if(this.usuario && ArrayUtil.isNotEmpty(this.usuario.perfis)){
            let perfis: String[] =
                this.usuario.perfis.map(perfil => {
                    return perfil.descricao;
                });
            return ArrayUtil.join(perfis, "<br>");
        }
        return "";
    }

    /**
     * @description Adiciona um novo perfil ao usuário já previamente
     * cadastrado.
     * @author Pizão
     */
    public adicionarPerfil(): void{
        let selecionado: Perfil =
            ArrayUtil.removeByProperty(this.listaPerfisDisponiveis, this.perfisDisponiveisComponent.value, "id");
        this.listaPerfisSelecionados.push( selecionado );
    }

    /**
     * @description Remove o perfil definido para o usuário.
     * @author Pizão
     */
    public removerPerfil(): void{
        if (this.perfisSelecionadosComponent.value && this.perfisSelecionadosComponent.value.length !== 0) {
            let podeExcluir = true;
            this.perfisSelecionadosComponent.value.forEach(id => {
                if (Number.parseInt(id).valueOf() === Perfis.MEDICO ) {
                    podeExcluir = false;
                }
            });
            if (podeExcluir) {
                this.perfisSelecionadosComponent.value.forEach(id => {
                    if (Number.parseInt(id).valueOf() !== Perfis.MEDICO) {
                        let removido: Perfil =
                           ArrayUtil.removeByProperty(this.listaPerfisSelecionados, id, "id");
                        this.listaPerfisDisponiveis.push(removido);
                    }
                });

                ArrayUtil.sort(this.listaPerfisDisponiveis, "descricao");
            }
            else {
                this.messageBox.alert(this.mensagemRemoverPerfilMedico).show();
            }

        }
    }

    /**
     * @description Finaliza a edição dos dados básicos
     * sem salvar as alterações.
     * @author Pizão
     */
    public cancelarEdicaoDadosBasicos(): void{
        this.dadosPessoaisSomenteLeitura = true;
    }

    /**
     * @description Confirma as alterações realizadas
     * enviando-as ao back-end parar serem salvas.
     * Os blocos são independentes na edição, somente na
     * inclusão que o salvar é geral para todos os itens.
     * @author Pizão
     */
    public salvarEdicaoDadosBasicos(): void {
        if(this.formulario.valid){
            this.dadosPessoaisSomenteLeitura = true;
            let usuarioAlterado: Usuario = this.formulario.value;

            this.usuario.nome = usuarioAlterado.nome;
            this.usuario.email = usuarioAlterado.email;
            this.usuario.username = usuarioAlterado.username;

            this.usuarioService.alterarDadosAcesso(
                this.usuario.id, this.usuario.username, this.usuario.email, this.usuario.nome)
                    .then(res => {
                        let modal: Modal = this.messageBox.alert(res.mensagem);
                        modal.closeOption = () => {
                            this.router.navigateByUrl(HistoricoNavegacao.urlRetorno());
                        };
                        modal.show();

                    }, (error: ErroMensagem) => {
                        this.messageBox.alert(ErroUtil.extrairMensagensErro(error));
                    });
        }
    }

    /**
     * @description Finaliza a edição das alterações
     * dos perfis do usuário selecionado.
     * @author Pizão
     */
    public cancelarEdicaoPerfis(): void{
        this.permissoesSomenteLeitura = true;
    }

    /**
     * @description Valida o preenchimento dos perfis para o usuário.
     * @author Pizão
     * @private
     * @returns {boolean}
     */
    private validarSelecaoPerfis(): boolean{
        this.mensagemPerfisNaoSelecionados = null;

        if(ArrayUtil.isEmpty(this.listaPerfisSelecionados)){
            this.translate.get("manterUsuario.mensagem.selecionePerfil").subscribe(mensagem =>{
                this.mensagemPerfisNaoSelecionados = mensagem;
            });
            return false;
        }
        return true;
    }

    /**
     * @description Valida o preenchimento do BSCUP, caso o perfil
     * exija o preenchimento.
     * @author Pizão
     * @private
     * @returns {boolean}
     */
    private validarSelecaoBscups(): boolean{
        this.mensagemBscupNaoSelecionado = null;

        if((this.exibirSelecaoBscups || this.existeBscupSelecionado()) && !this.idBscupSelecionado){
            this.translate.get("manterUsuario.mensagem.selecioneBscup").subscribe(mensagem =>{
                this.mensagemBscupNaoSelecionado = mensagem;
            });
            return false;
        }
        return true;
    }

    /**
     * @description Confirma as alterações realizadas
     * enviando-as ao back-end parar serem salvas.
     * Os blocos são independentes na edição, somente na
     * inclusão que o salvar é geral para todos os itens.
     * @author Pizão
     */
    public salvarEdicaoPerfis(): void {
        let perfisSelecionadosValido: boolean = this.validarSelecaoPerfis();
        let bscupSelecionadoValido: boolean = this.validarSelecaoBscups();

        if( perfisSelecionadosValido && bscupSelecionadoValido ){
            this.mensagemPerfisNaoSelecionados = null;
            this.permissoesSomenteLeitura = true;
            this.usuario.perfis = ArrayUtil.clone(this.listaPerfisSelecionados);
            this.usuario.bancoSangue = null;

            if(this.existeBscupSelecionado()){
                this.usuario.bancoSangue =
                    ArrayUtil.filterOne(this.listaBscups, "id", this.idBscupSelecionado);
            }

            this.usuarioService.alterarPerfis(this.usuario.id, this.usuario.perfis, this.usuario.bancoSangue)
                    .then(res => {
                        let modal: Modal = this.messageBox.alert(res.mensagem);
                        modal.closeOption = () => {
                            this.router.navigateByUrl(HistoricoNavegacao.urlRetorno());
                        };
                        modal.show();

                    }, (error: ErroMensagem) => {
                        this.messageBox.alert(ErroUtil.extrairMensagensErro(error));
                    });
        }
    }

    /**
     * @description Ao selecionar o sistema e preencher os perfis associados a ele.
     * @author Pizão
     * @param {Sistema} sistema
     */
    public selecionarPerfisAssociados(idSistema: number): void{
        let sistemaSelecionado: Sistema = ArrayUtil.filterOne(this.listaSistemas, "id", idSistema);
        this.exibirSelecaoBscups = Sistema.BSCUP == idSistema;
        this.listaPerfisDisponiveis = this.listaTodosPerfis;

        if(ArrayUtil.isNotEmpty(this.listaPerfisDisponiveis)){
            if(sistemaSelecionado){
                this.listaPerfisDisponiveis =
                    this.listaPerfisDisponiveis.filter(perfil => {
                        let pertenceAoSistema: boolean = (perfil.sistema.id == sistemaSelecionado.id);
                        let perfilNaoSelecionado: boolean = !ArrayUtil.contains(this.listaPerfisSelecionados, perfil);
                        return pertenceAoSistema && perfilNaoSelecionado;
                    });
            }
        }
    }

    /**
     * @description Verifica se existe algum perfil relacionado ao banco
     * de sangue selecionado. Se houver, a seleção do banco de sangue
     * deve manter-se visível.
     * @author Pizão
     * @returns {boolean}
     */
    public existeBscupSelecionado(): boolean {
        return this.listaPerfisSelecionados.find(perfil => {
            return perfil.sistema.id == Sistema.BSCUP;
        }) != null;
    }

    /**
     * @description Ao selecionar o perfil na lista, exibir qual o sistema
     * que ele está associado.
     * @author Pizão
     * @param {Perfil} perfil
     */
    public selecionarSistema(perfilId: number): void{
        let perfilSelecionado: Perfil = ArrayUtil.filterOne(this.listaPerfisDisponiveis, "id", perfilId);
        this.idSistemaSelecionado = perfilSelecionado.sistema.id;
    }

    /**
     * @description Habilita o botão de salvar global.
     * Somente é possível habilitá-lo uma única vez, o que é realizado
     * somente na tela NovoUsuarioComponent.
     *
     * @author Pizão
     * @protected
     */
    protected habilitarModoInclusao(): void{
        this._modoInclusaoHabilitado = true;
        this._dadosPessoaisSomenteLeitura = false;
        this._permissoesSomenteLeitura = false;
    }

    public get modoInclusaoHabilitado(): boolean{
        return this._modoInclusaoHabilitado;
    }

    /**
     * @description Indica se o usuário que foi selecionado está
     * associado a um perfil e um sistema que está disponível para
     * cadastro da equipe do Redome.
     *
     * @readonly
     * @type {boolean}
     */
    public get disponivelParaCadastroRedome(): boolean{
        if(this.usuario){
            let disponivel: boolean = false;

            this.usuario.perfis.forEach(perfil => {
                if(perfil.sistema.disponivelRedome){
                    disponivel = true;
                }
            });

            return disponivel;
        }
        return true;
    }

    /**
     * @description Salva todo o usuário preenchido. Funcionalidade
     * chamada pelo salvar global, para quando está sendo incluído um
     * novo usuário.
     * @author Pizão
     */
    public salvarGlobal(): void{
        let formularioValido: boolean = this.formulario.valid;
        let perfisSelecionadosValidos: boolean = this.validarSelecaoPerfis();
        let bscupSelecionadoValido: boolean = this.validarSelecaoBscups();

        if(formularioValido && perfisSelecionadosValidos && bscupSelecionadoValido){
            let novoUsuario: Usuario = new Usuario();
            let usuarioAlterado: Usuario = this.formulario.value;

            novoUsuario.nome = usuarioAlterado.nome;
            novoUsuario.email = usuarioAlterado.email;
            novoUsuario.username = usuarioAlterado.username;
            novoUsuario.perfis = ArrayUtil.clone(this.listaPerfisSelecionados);
            if(this.exibirSelecaoBscups){
                novoUsuario.bancoSangue =
                    ArrayUtil.filterOne(this.listaBscups, "id", this.idBscupSelecionado);
            }

            this.usuarioService.salvar(novoUsuario).then(res => {
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

    nomeComponente(): string {
        return "DetalheUsuarioComponent";
    }

    inativar(): void{
        this.translate.get("manterUsuario.mensagem.confirmarInativacao").subscribe(mensagem =>{
            let modalConfirmacao: ModalConfirmation = this.messageBox.confirmation(mensagem);
            modalConfirmacao.yesOption = () => {
                this.usuarioService.inativar(this.usuario.id).then(res => {
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
}
