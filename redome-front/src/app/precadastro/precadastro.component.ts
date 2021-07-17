import { Router } from '@angular/router';
import { PreCadastroMedicoEmail } from 'app/shared/classes/precadastro.medico.email';
import { PreCadastroMedicoTelefone } from 'app/shared/classes/precadastro.medico.telefone';
import { PreCadastroMedicoEndereco } from './../shared/classes/precadastro.medico.endereco';
import { PreCadastroMedico } from './../shared/classes/precadastro.medico';
import { Observable } from 'rxjs/Observable';
import { TranslateService } from '@ngx-translate/core';
import { StringControl } from './../shared/buildform/controls/string.control';
import { Component, OnInit, ViewChild } from "@angular/core";
import { BuildForm } from '../shared/buildform/build.form';
import { Validators, FormGroup } from '@angular/forms';
import { NumberControl } from 'app/shared/buildform/controls/number.control';
import { DominioService } from 'app/shared/dominio/dominio.service';
import { promise } from 'protractor';
import { EnderecoContatoComponent } from 'app/shared/component/endereco/endereco.contato.component';
import { UploadArquivoComponent } from 'app/shared/upload/upload.arquivo.component';
import { ContatoTelefoneComponent } from 'app/shared/component/telefone/contato.telefone.component';
import { EmailContatoComponent } from '../shared/component/email/email.contato.component';
import { CentroTransplante } from 'app/shared/dominio/centro.transplante';
import { ArrayControl } from 'app/shared/buildform/controls/array.control';
import { CentroTransplanteService } from 'app/admin/centrotransplante/centrotransplante.service';
import { ErroMensagem } from 'app/shared/erromensagem';
import { ErroUtil } from 'app/shared/util/erro.util';
import { MessageBox } from 'app/shared/modal/message.box';
import { FuncaoCentroTransplante } from 'app/shared/enums/funcao.centro.transplante';
import { MedicoService } from 'app/shared/service/medico.service';
import { FileItem } from 'ng2-file-upload';
import { Modal } from 'app/shared/modal/factory/modal.factory';
import { CampoMensagem } from '../shared/campo.mensagem';

@Component({
    moduleId: module.id,
    templateUrl: './precadastro.component.html',
    styleUrls: ['./precadastro.css']
})
export class PreCadastroComponent implements OnInit {

    private ETAPA_IDENTIFICACAO: number = 1;
    private ETAPA_CONTATO: number = 2;
    private ETAPA_CENTROS_REFERENCIA: number = 3;

    @ViewChild("endereco")
    private _enderecoComponent: EnderecoContatoComponent;

    @ViewChild("contatoTelefoneComponent")
    private _contatoTelefoneComponente: ContatoTelefoneComponent;

    @ViewChild("emailContatoComponente")
    private _emailContatoComponente: EmailContatoComponent;

    @ViewChild("uploadComponent")
    private uploadComponent: UploadArquivoComponent;

    private _buildForm: BuildForm<any>;
    private _etapaAtual: number;

    private _buildFormDisponiveis: BuildForm<any>;
    private _buildFormSelecionados: BuildForm<any>;
    private _centrosAvaliadores: CentroTransplante[];
    private _centrosDiponives: CentroTransplante[];
    private _centrosSelecionados: CentroTransplante[];

    constructor(private centroTransplanteService: CentroTransplanteService,
        private messageBox: MessageBox, private router: Router,
        private medicoService: MedicoService) {

        this._etapaAtual = this.ETAPA_IDENTIFICACAO;
        this._buildForm = new BuildForm<any>()
            .add(new StringControl("crm", [Validators.required]))
            .add(new StringControl("nome", [Validators.required]))
            .add(new StringControl("login", [Validators.required]));

        this._buildFormDisponiveis = new BuildForm<any>()
            .add(new ArrayControl("selecionados"));

        this._buildFormSelecionados = new BuildForm<any>()
            .add(new ArrayControl("selecionados"))
            .add(new StringControl("recaptcha", [Validators.required]));
    }

    ngOnInit() {
        this.uploadComponent.extensoes = "extensaoArquivoPreCadastro";
        this.uploadComponent.tamanhoLimite = "tamanhoArquivoPreCadastroEmByte";
        this.uploadComponent.quantidadeMaximaArquivos = "quantidadeArquivosPreCadastro"
        this.uploadComponent.uploadObrigatorio = true;

        if (this._enderecoComponent) {
            this._enderecoComponent.desabilitarCampoPais();
        }
        this._centrosDiponives = [];
        this._centrosSelecionados = [];
        this.centroTransplanteService.listarCentroTransplantes(null, 0, 9999999, FuncaoCentroTransplante.AVALIADOR).then(res => {
            this._centrosAvaliadores = [];
            if (res && res.content) {
                res.content.forEach(centro => {
                    this._centrosAvaliadores.push(new CentroTransplante().jsonToEntity(centro));
                });
            }
            this._centrosDiponives = this._centrosAvaliadores;
        }, (error: ErroMensagem) => {
            ErroUtil.exibirMensagemErro(error, this.messageBox);
        });
    }

    salvarPreCadastro() {
        if (this._buildFormSelecionados.valid) {
            let preCadastro: PreCadastroMedico = new PreCadastroMedico().jsonToEntity(this._buildForm.value);
            preCadastro.endereco = new PreCadastroMedicoEndereco().jsonToEntity(this._enderecoComponent.obterEndereco());

            preCadastro.telefones = [];
            this._contatoTelefoneComponente.listarTelefonesContato().forEach(telefone => {
                preCadastro.telefones.push(new PreCadastroMedicoTelefone().jsonToEntity(telefone));
            });

            preCadastro.emails = [];
            this._emailContatoComponente.listar().forEach(email => {
                preCadastro.emails.push(new PreCadastroMedicoEmail().jsonToEntity(email));
            });

            preCadastro.centrosReferencia = this._centrosSelecionados;

            let arquivoCrm: FileItem = this.uploadComponent.arquivos.entries().next().value[1];
            this.medicoService.salvarPreCadastro(preCadastro, arquivoCrm)
                .then(result => {
                    let modal: Modal = this.messageBox.alert(JSON.parse(result).mensagem);
                    modal.closeOption = () => {
                        this.abrirTelaLogin();
                    }
                    modal.show();

                }, (error: ErroMensagem) => {
                    error.listaCampoMensagem.forEach(mensagem => {
                        this.redirecionarAba(mensagem);
                    });
                });
        }
    }

    private redirecionarAba(mensagem: CampoMensagem): void{
        switch (mensagem.campo) {
            case "crm":
            case "login":
                this.habilitarEtapa(this.ETAPA_IDENTIFICACAO);
                this._buildForm.invalidate([mensagem]);
                break;

            default:
                break;
        }
    }

    form(): FormGroup {
        return this._buildForm.form;
    }

    /**
     * Habilita botão de salvar se for a última etapa
     *
     * @returns {boolean}
     * @memberof PreCadastroComponent
     */
    public habilitarSalvarSeEhUltimaEtapa(): boolean{
        return this.ETAPA_CENTROS_REFERENCIA != this._etapaAtual;
    }

    /**
     * Método para pegar estido do link do breadCrumb
     *
     * @param {any} etapaAtual
     * @returns {string}
     * @memberof PreCadastroComponent
     */
    public getEstiloDoLinkDaMigalhaDePao(etapaAtual): string {
        if (this._etapaAtual > etapaAtual)  {
            return  "ativo";
        }
        else if (this._etapaAtual == etapaAtual)  {
            return  "ativo current";
        }
        else {
            return "";
        }
    }

    /**
     * Método que habilita etapas anteriores para ser clicada
     *
     * @param {number} etapaParaHabilitar
     * @memberof PreCadastroComponent
     */
    public habilitarEtapa(etapaParaHabilitar: number): void {
        if (etapaParaHabilitar < this._etapaAtual) {
            this._etapaAtual = etapaParaHabilitar;
        }
    }

    /**
     * Método para habilitar a etapa anterior
     *
     * @memberof PreCadastroComponent
     */
    public habilitarEtapaAnterior():void{
        this._etapaAtual--;
    }

    /**
     * Avança para a próxima etapa, somente se o formulário atual
     * tiver preenchido corretamente.
     *
     * @memberof PreCadastroComponent
     */
    public habilitarProximaEtapa():void{
        if (this._etapaAtual == this.ETAPA_IDENTIFICACAO) {
            //let arquivoValido: boolean = this.uploadComponent.validateForm();
            this.validarPrimeiraEtapa().then(res => {
                if (res) {
                    this._etapaAtual++;
                }
            })

        }
        else if (this._etapaAtual == this.ETAPA_CONTATO) {
            let enderecoValido: boolean = this._enderecoComponent.validateForm();
            let telefonesValidos: boolean = this._contatoTelefoneComponente.validateForm();
            let emailsValidos: boolean = this._emailContatoComponente.validateForm();

            if (enderecoValido && telefonesValidos && emailsValidos) {
                this._etapaAtual++;
            }
        }
        else if (this._etapaAtual == this.ETAPA_CENTROS_REFERENCIA) {

        }

    }

    /**
     * Método para esconder botão anterior se etapa for 1
     *
     * @returns {boolean}
     * @memberof PreCadastroComponent
     */
    public escondeSeEtapa1():boolean{
        return this._etapaAtual == this.ETAPA_IDENTIFICACAO;
    }

    /**
     * Método que esconde botão proximo se for a última etapa
     *
     * @returns {boolean}
     * @memberof PreCadastroComponent
     */
    public escondeSeUltimaEtapa():boolean{
        return this._etapaAtual == this.ETAPA_CENTROS_REFERENCIA;
    }

    /**
     * Método para esconder as etapas que não são as atuais
     *
     * @param {any} etapaParaTestar
     * @returns {boolean}
     * @memberof PreCadastroComponent
     */
    public esconderEtapa(etapaParaTestar: number): boolean {
        return this._etapaAtual != etapaParaTestar;
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

    abrirTelaLogin() {
        this.router.navigateByUrl("/login");
    }

    private validarPrimeiraEtapa(): Promise<Boolean> {
        let valid = this._buildForm.valid;
        let arquivoValido: boolean = this.uploadComponent.validateForm();
        if (valid && arquivoValido) {
            return new Promise((resolve, reject) => {
                this.medicoService.validarCrmELogin(this._buildForm.value.crm, this._buildForm.value.login)
                    .then(res => {
                        resolve(true);
                    },
                    (error: ErroMensagem) => {
                        resolve(false);
                        if (error.mensagem) {
                            ErroUtil.exibirMensagemErro(error, this.messageBox);
                        }
                        else if (error.listaCampoMensagem && error.listaCampoMensagem.length != 0) {
                            this._buildForm.invalidate(error.listaCampoMensagem);
                        }
                    }
                );
            })
        }

        return Promise.resolve(false);

    }

}
