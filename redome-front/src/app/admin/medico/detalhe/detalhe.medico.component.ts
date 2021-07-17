import { AutenticacaoService } from './../../../shared/autenticacao/autenticacao.service';
import { CentroTransplanteService } from './../../centrotransplante/centrotransplante.service';
import { centroTransplanteService, autenticacaoService } from './../../../export.mock.spec';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ContatoTelefonico } from 'app/shared/classes/contato.telefonico';
import { ContatoTelefonicoMedico } from 'app/shared/classes/contato.telefonico.medico';
import { EmailContatoMedico } from 'app/shared/classes/email.contato.medico';
import { EnderecoContatoMedico } from 'app/shared/classes/endereco.contato.medico';
import { EmailContatoComponent } from 'app/shared/component/email/email.contato.component';
import { EnderecoContatoComponent } from 'app/shared/component/endereco/endereco.contato.component';
import { ContatoTelefoneComponent } from 'app/shared/component/telefone/contato.telefone.component';
import { Medico } from 'app/shared/dominio/medico';
import { ErroMensagem } from 'app/shared/erromensagem';
import { MessageBox } from 'app/shared/modal/message.box';
import { ContatoTelefonicoService } from 'app/shared/service/contato.telefonico.service';
import { MedicoService } from 'app/shared/service/medico.service';
import { ErroUtil } from 'app/shared/util/erro.util';
import { BuildForm } from '../../../shared/buildform/build.form';
import { StringControl } from '../../../shared/buildform/controls/string.control';
import { ComponenteRecurso } from "../../../shared/enums/componente.recurso";
import { PermissaoRotaComponente } from '../../../shared/permissao.rota.componente';
import { EmailContatoService } from '../../../shared/service/email.contato.service';
import { FormatterUtil } from '../../../shared/util/formatter.util';
import { RouterUtil } from '../../../shared/util/router.util';
import { EnderecoContatoService } from './../../../shared/service/endereco.contato.service';
import { CentroTransplante } from 'app/shared/dominio/centro.transplante';
import { ArrayControl } from 'app/shared/buildform/controls/array.control';
import { FuncaoCentroTransplante } from 'app/shared/enums/funcao.centro.transplante';
import { EnderecoContatoMedicoService } from 'app/shared/service/endereco.contato.medico.service';
/**
 * Classe que representa o componente de detalhe do paciente
 * @author Bruno Sousa
 */
@Component({
    selector: 'datelhe-medico',
    templateUrl: './detalhe.medico.component.html'
})
export class DetalheMedicoComponent implements PermissaoRotaComponente, OnInit {

    private _id: number;

    private _medico: Medico;

    public _mostraDadosBasicos: string = '';
    public _mostraFormularioDadosBasicos: string = 'hide';
    public _mostraLinkEditarDadosBasicos: boolean = true;

    public _mostraEndereco: string = '';
    public _mostraFormularioEndereco: string = 'hide';
    public _mostraLinkEditarEndereco: boolean = true;

    public _mostraTelefone: string = '';
    public _mostraFormularioTelefone: string = 'hide';
    public _validarTelefonePrincipal: string = 'false';
    public _mostraLinkInserirTelefone: boolean = true;

    public _mostraEmail: string = '';
    public _mostraFormularioEmail: string = 'hide';
    public _validarEmailPrincipal: string = 'false';
    public _mostraLinkInserirEmail: boolean = true;

    public _mostraCentrosReferencia = '';
    public _mostraFormularioCentrosReferencia: string = 'hide';
    public _mostraLinkEditarCentrosReferencia: boolean = true;

    private _buildForm: BuildForm<Medico>;

    @ViewChild("endereco")
    private enderecoComponent: EnderecoContatoComponent;

    @ViewChild('contatoTelefoneComponent')
    public contatoTelefoneComponent: ContatoTelefoneComponent;

    @ViewChild('emailContatoComponent')
    public emailContatoComponent: EmailContatoComponent;

    private _buildFormDisponiveis: BuildForm<any>;
    private _buildFormSelecionados: BuildForm<any>;
    private _centrosAvaliadores: CentroTransplante[];
    private _centrosDiponives: CentroTransplante[];
    private _centrosSelecionados: CentroTransplante[];
    public _temPerfilMedico: boolean;


    /**
     * @param FormBuilder
     * @param PacienteService
     * @param Router
     * @param TranslateService
     * @author Bruno Sousa
     */
    constructor(private router: Router, private activatedRouter: ActivatedRoute,
        private medicoService: MedicoService, private messageBox: MessageBox,
        private centroTransplanteService: CentroTransplanteService,
        private contatoTelefonicoService: ContatoTelefonicoService,
        private emailContatoService: EmailContatoService,
        private autenticacaoService: AutenticacaoService,
        private enderecoContatoMedicoService: EnderecoContatoMedicoService) {

        this._buildForm = new BuildForm<Medico>()
            .add(new StringControl("crm", [ Validators.required ] ))
            .add(new StringControl("nome", [ Validators.required ] ));

        this._buildFormDisponiveis = new BuildForm<any>()
            .add(new ArrayControl("selecionados"));

        this._buildFormSelecionados = new BuildForm<any>()
            .add(new ArrayControl("selecionados"));

        this._temPerfilMedico = this.autenticacaoService.temPerfilMedico();
    }

    /**
     *
     * @memberOf DetalheComponent
     */
    ngOnInit(): void {

        if (this.enderecoComponent) {
            this.enderecoComponent.desabilitarCampoPais();
        }

        this._centrosDiponives = [];
        this._centrosSelecionados = [];
        this.centroTransplanteService.listarCentroTransplantes(null, 0, 99999, FuncaoCentroTransplante.AVALIADOR).then(res => {
            this._centrosAvaliadores = [];
            if (res && res.content) {
                res.content.forEach(centro => {
                    this._centrosAvaliadores.push(new CentroTransplante().jsonToEntity(centro));
                });
            }
        }, (error: ErroMensagem) => {
            ErroUtil.exibirMensagemErro(error, this.messageBox);
        });

        RouterUtil.recuperarParametroDaActivatedRoute(this.activatedRouter, "idMedico").then(res => {
            this._id = <number>res;
            this.obterMedico();
        });

    }

    private obterMedico() {
        this.medicoService.obterMedicoPorId(this._id).then(medico => {
            console.log(medico);
            this._medico = new Medico().jsonToEntity(medico);
        }, (error: ErroMensagem) => {
            ErroUtil.exibirMensagemErro(error, this.messageBox);
        });
    }

    nomeComponente(): string {
        return ComponenteRecurso.Componente[ComponenteRecurso.Componente.DetalheMedicoComponent];
    }

    public get medico(): Medico {
        return this._medico
    }

    private mostrarFormularioDadosBasicos() {
        this._mostraDadosBasicos = 'hide';
        this._mostraFormularioDadosBasicos = '';
        this._mostraLinkEditarDadosBasicos = false;
    }

    public editarDadosBasicos(): void {

        this.mostrarFormularioDadosBasicos();

        let value: any = {
            'nome': this._medico.nome,
            'crm': this._medico.crm
        }

        this._buildForm.form.reset();
        this._buildForm.value = value;

    }

    public cancelarEdicaoDadosBasicos(): void {
        this._mostraLinkEditarDadosBasicos = true;
        this._mostraFormularioDadosBasicos = 'hide';
        this._mostraDadosBasicos = '';
    }

    public salvarEdicaoDadosBasicos(): void {
        if (this._buildForm.valid) {
            let medico: Medico = new Medico().jsonToEntity(this._buildForm.value);
            this.medicoService.atualizarDadosIdentificacao(this._id, medico).then(res => {
                this.messageBox.alert(res.mensagem)
                    .withTarget(this)
                    .withCloseOption((target?: any) => {
                        this.obterMedico();
                        this.cancelarEdicaoDadosBasicos();
                    })
                    .show();
            }, (error: ErroMensagem) => {
                ErroUtil.exibirMensagemErro(error, this.messageBox);
            });


        }
    }

    public form(): FormGroup {
        return <FormGroup>this._buildForm.form;
    }

    private mostrarFormularioEndereco() {
        this._mostraEndereco = 'hide';
        this._mostraFormularioEndereco = '';
        this._mostraLinkEditarEndereco = false;
    }

    public editarEndereco(): void {
        this.enderecoComponent.clearForm();

        if (this._medico.endereco) {
            this.enderecoComponent.preencherFormulario(this._medico.endereco);
        }
        else {
            this.enderecoComponent.setValoresPadroes();
        }
        this.enderecoComponent.configEndNacionalForm();
        this.mostrarFormularioEndereco();
    }

    public cancelarEdicaoEndereco(): void {
        this._mostraLinkEditarEndereco = true;
        this._mostraFormularioEndereco = 'hide';
        this._mostraEndereco = '';
    }

    public salvarEdicaoEndereco(): void {
        if (this.enderecoComponent.validateForm()) {
            let id: number;
            if (this._medico.endereco) {
                id = this._medico.endereco.id
            }
            let endereco: EnderecoContatoMedico =
                new EnderecoContatoMedico().jsonToEntity(this.enderecoComponent.obterEndereco());
            endereco.medico = this._medico;
            endereco.principal = true;
            endereco.id = id;
            this.enderecoContatoMedicoService.atualizarEnderecoContato(id, endereco).then(res => {
                this.messageBox.alert(res.mensagem)
                    .withTarget(this)
                    .withCloseOption((target?: any) => {
                        this.obterMedico();
                        this.cancelarEdicaoEndereco();
                    })
                    .show();
            }, (error: ErroMensagem) => {
                ErroUtil.exibirMensagemErro(error, this.messageBox);
            });
        }
    }

    private mostrarFormularioTelefone() {
        this._mostraTelefone = 'hide';
        this._mostraFormularioTelefone = '';
        this._mostraLinkInserirTelefone = false;
    }

    public incluirTelefone() {
        if (!this._medico.contatosTelefonicos || this._medico.contatosTelefonicos.length == 0){
            this._validarTelefonePrincipal = "true";
        }

        this.contatoTelefoneComponent.buildForm();
        this.mostrarFormularioTelefone();
    }

    public salvarContatoTelefonico(): void {
        if (this.contatoTelefoneComponent.validateForm()) {
            let contatosTelefonico: ContatoTelefonicoMedico[] = [];
            this.contatoTelefoneComponent.listarTelefonesContato().forEach(contato => {
                let contatoTelefonico: ContatoTelefonicoMedico = new ContatoTelefonicoMedico().jsonToEntity(contato);
                contatoTelefonico.medico = this._medico;
                contatosTelefonico.push(contatoTelefonico);
            });
            this.medicoService.adicionarTelefone(this._id, contatosTelefonico[0]).then(res => {
                this.messageBox.alert(res.mensagem)
                    .withTarget(this)
                    .withCloseOption((target?: any) => {
                        this.obterMedico();
                        this.cancelarEdicaoTelefone();
                    })
                    .show();
            }, (error: ErroMensagem) => {
                ErroUtil.exibirMensagemErro(error, this.messageBox);
            });
        }
    }

    public excluirTelefone(telefone: ContatoTelefonico) {
        this.contatoTelefonicoService.excluirContatoTelefonicoPorId(telefone.id).then(res => {
            this.messageBox.alert(res)
                .withTarget(this)
                .withCloseOption((target?: any) => {
                    this.obterMedico();
                })
                .show();
        },
        (error: ErroMensagem) => {
            ErroUtil.exibirMensagemErro(error, this.messageBox);
        });
    }

    public cancelarEdicaoTelefone() {
        this._mostraFormularioTelefone = 'hide';
        this._mostraTelefone = '';
        this._mostraLinkInserirTelefone = true;
    }

    private mostrarFormularioEmail() {
        this._mostraEmail = 'hide';
        this._mostraFormularioEmail = '';
        this._mostraLinkInserirEmail = false;
    }

    public incluirEmail() {
        if (!this._medico.emails || this._medico.emails.length == 0){
            this._validarEmailPrincipal = "true";
        }

        this.emailContatoComponent.buildForm();
        this.mostrarFormularioEmail();
    }

    public salvarContatoEmail(): void {
        if (this.emailContatoComponent.validateForm()) {
            let emails: EmailContatoMedico[] = [];
            this.emailContatoComponent.listar().forEach(contato => {
                let email: EmailContatoMedico = new EmailContatoMedico().jsonToEntity(contato);
                email.medico = this._medico;
                emails.push(email);
            });
            this.medicoService.adicionarEmail(this._id, emails[0]).then(res => {
                this.messageBox.alert(res.mensagem)
                    .withTarget(this)
                    .withCloseOption((target?: any) => {
                        this.obterMedico();
                        this.cancelarEdicaoEmail();
                    })
                    .show();
            }, (error: ErroMensagem) => {
                ErroUtil.exibirMensagemErro(error, this.messageBox);
            });
        }
    }

    public excluirEmail(email: EmailContatoMedico) {
        this.emailContatoService.excluirEmailContatoPorId(email.id).then(res => {
            this.messageBox.alert(res)
                .withTarget(this)
                .withCloseOption((target?: any) => {
                    this.obterMedico();
                })
                .show();
        },
        (error: ErroMensagem) => {
            ErroUtil.exibirMensagemErro(error, this.messageBox);
        });
    }

    public cancelarEdicaoEmail() {
        this._mostraFormularioEmail = 'hide';
        this._mostraEmail = '';
        this._mostraLinkInserirEmail = true;
    }

    public obterNomeArquivo(value: string): string {
        if (value) {
            return FormatterUtil.obterNomeArquivoSelecionado(value).toString();
        }
        return "";
    }

    public formCentrosDisponiveis(): FormGroup {
        return this._buildFormDisponiveis.form;
    }

    public formCentrosSelecionados(): FormGroup {
        return this._buildFormSelecionados.form;
    }

    public get listaCentrosDisponiveis(): CentroTransplante[] {
        return this._centrosDiponives;
    }

    public get listaCentrosSelecionados(): CentroTransplante[] {
        return this._centrosSelecionados;
    }

    public adicionarCentros() {
        if (this._buildFormDisponiveis.value && this._buildFormDisponiveis.value.selecionados &&
            this._buildFormDisponiveis.value.selecionados.length != 0) {
            let selecionados = this._buildFormDisponiveis.value.selecionados;
            for (let idx = 0; idx <= selecionados.length - 1; idx++) {
                let centro: CentroTransplante = this._centrosAvaliadores
                    .find((centro, index) => centro.id == selecionados[idx]);
                if (centro) {
                    this._centrosSelecionados.push(centro);
                }
            }
            if (this._centrosSelecionados.length != 0) {
                this._buildFormDisponiveis.form.reset();
                this._centrosDiponives = this._centrosAvaliadores.filter(centro => {
                    let achou = this._centrosSelecionados.some(usuarioCentro => usuarioCentro.id == centro.id);
                    return !achou;
                });
            }
        }
    }

    public removerCentros() {
        if (this._buildFormSelecionados.value && this._buildFormSelecionados.value.selecionados &&
            this._buildFormSelecionados.value.selecionados.length != 0) {
            let selecionados = this._buildFormSelecionados.value.selecionados;
            let centrosTransplantesParaTarefas = this._centrosSelecionados;
            for (let idx = 0; idx <= selecionados.length - 1; idx++) {
                let index = centrosTransplantesParaTarefas.findIndex(
                    centro => centro.id == selecionados[idx]);
                if (index != undefined) {
                    centrosTransplantesParaTarefas = centrosTransplantesParaTarefas
                        .filter((centro, idx2) => idx2 != index);
                }
            }

            this._centrosSelecionados = centrosTransplantesParaTarefas;
            this._buildFormSelecionados.form.reset();

            this._buildFormDisponiveis.form.reset();
            this._centrosDiponives = this._centrosAvaliadores.filter(centro => {
                let achou = this._centrosSelecionados.some(usuarioCentro => usuarioCentro.id == centro.id);
                return !achou;
            });

        }
    }

    public habiltaBotaoAdicionar(): boolean {
        return !(this._centrosDiponives && this._centrosDiponives.length != 0
                && this._buildFormDisponiveis.value.selecionados && this._buildFormDisponiveis.value.selecionados.length != 0);
    }

    public habiltaBotaoRemover(): boolean {
        return !(this._buildFormSelecionados.value.selecionados
            && this._buildFormSelecionados.value.selecionados.length != 0);
    }

    private mostrarFormularioCentrosReferencia() {
        this._mostraCentrosReferencia = 'hide';
        this._mostraFormularioCentrosReferencia = '';
        this._mostraLinkEditarCentrosReferencia = false;
    }

    public editarCentroReferencia(): void {

        this.mostrarFormularioCentrosReferencia();

        this._centrosDiponives = [];
        this._buildFormDisponiveis.form.reset();
        if (this._medico.centrosReferencia) {
            this._centrosDiponives = this._centrosAvaliadores.filter(centro => {
                let achou = this._medico.centrosReferencia.some(centroReferencia => centroReferencia.id == centro.id);
                return !achou;
            });
            this._centrosSelecionados = this._medico.centrosReferencia;
        }
        else {
            this._centrosDiponives = this._centrosAvaliadores.copyWithin(0,0);
            this._centrosSelecionados = [];
        }

        this._buildFormSelecionados.form.reset();
    }

    public cancelarEdicaoCentroReferencia(): void {
        this._mostraFormularioCentrosReferencia = 'hide';
        this._mostraCentrosReferencia = '';
        this._mostraLinkEditarCentrosReferencia = true;
    }

    public salvarContatoCentroReferencia(): void {
        if (this._buildFormSelecionados.valid)  {
            this.medicoService.atualizarCentrosReferencia(this._id, this._centrosSelecionados).then(res => {
                this.messageBox.alert(res.mensagem)
                    .withTarget(this)
                    .withCloseOption((target?: any) => {
                        this.obterMedico();
                        this.cancelarEdicaoCentroReferencia();
                    })
                    .show();
            }, (error: ErroMensagem) => {
                ErroUtil.exibirMensagemErro(error, this.messageBox);
            });
        }
    }

    baixarArquivo() {
        this.medicoService.baixarMedicoArquivoCRM(this._medico.id, this.obterNomeArquivo(this._medico.arquivoCrm));
    }

}
