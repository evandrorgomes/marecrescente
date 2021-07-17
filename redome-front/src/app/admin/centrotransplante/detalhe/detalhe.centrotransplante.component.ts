import { HistoricoNavegacao } from 'app/shared/historico.navegacao';
import { Component, OnInit, ViewChild, ɵConsole } from '@angular/core';
import { FormArray, FormGroup, ValidationErrors, Validators, FormControl } from '@angular/forms';
import { ActivatedRoute, Router, NavigationExtras } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { ContatoTelefonico } from 'app/shared/classes/contato.telefonico';
import { EnderecoContato } from 'app/shared/classes/endereco.contato';
import { EnderecoContatoComponent } from 'app/shared/component/endereco/endereco.contato.component';
import { ContatoTelefoneComponent } from 'app/shared/component/telefone/contato.telefone.component';
import { ErroMensagem } from 'app/shared/erromensagem';
import { MessageBox } from 'app/shared/modal/message.box';
import { ContatoTelefonicoService } from 'app/shared/service/contato.telefonico.service';
import { ErroUtil } from 'app/shared/util/erro.util';
import { AutenticacaoService } from '../../../shared/autenticacao/autenticacao.service';
import { BuildForm } from '../../../shared/buildform/build.form';
import { StringControl } from '../../../shared/buildform/controls/string.control';
import { CentroTransplante } from '../../../shared/dominio/centro.transplante';
import { Laboratorio } from '../../../shared/dominio/laboratorio';
import { ComponenteRecurso } from "../../../shared/enums/componente.recurso";
import { PermissaoRotaComponente } from '../../../shared/permissao.rota.componente';
import { EnderecoContatoCentroTransplanteService } from '../../../shared/service/endereco.contato.centro.transplante.service';
import { FormatterUtil } from '../../../shared/util/formatter.util';
import { RouterUtil } from '../../../shared/util/router.util';
import { CentroTransplanteService } from '../centrotransplante.service';
import { BooleanControl } from './../../../shared/buildform/controls/boolean.control';
import { BuildFormArray } from './../../../shared/buildform/controls/build.form.array';
import { BuildFormGroup } from './../../../shared/buildform/controls/build.form.group';
import { NumberControl } from './../../../shared/buildform/controls/number.control';
import { PositionType } from './../../../shared/component/inputcomponent/group.checkbox.component';
import { FuncaoTransplante } from './../../../shared/dominio/funca.transplante';
import { EnderecoContatoService } from './../../../shared/service/endereco.contato.service';
import { LaboratorioService } from './../../../shared/service/laboratorio.service';
import { MascaraUtil } from './../../../shared/util/mascara.util';
import { Medico } from '../../../shared/dominio/medico';
import { MedicoService } from '../../../shared/service/medico.service';
import { ArrayControl } from '../../../shared/buildform/controls/array.control';
import { CentroTransplanteUsuario } from '../../../shared/dominio/centro.transplante.usuario';
import { UsuarioLogado } from '../../../shared/dominio/usuario.logado';
import { EmailContatoComponent } from 'app/shared/component/email/email.contato.component';
import { EmailContatoService } from 'app/shared/service/email.contato.service';
import { EmailContatoCentroTransplante } from 'app/shared/classes/email.contato.centrotransplante';
import { EnderecoContatoCentroTransplante } from 'app/shared/model/endereco.contato.centro.transplante';
import { ContatoTelefonicoCentroTransplante } from 'app/shared/model/contato.telefonico.centro.transplante';

/**
 * Classe que representa o componente de detalhe do paciente
 * @author Bruno Sousa
 */
@Component({
    selector: 'datelhe-centrotransplante',
    templateUrl: './detalhe.centrotransplante.component.html'
})
export class DetalheCentroTransplanteComponent implements PermissaoRotaComponente, OnInit {

    private _id: number;

    private _centroTransplante: CentroTransplante;

    public _exibirLinkEditarDadosBasicos: boolean = true;
    public _exibirBotoesEditarDadosBasicos: boolean = true;
    public _mostraDadosBasicos: string = '';
    public _mostraFormularioDadosBasicos: string = 'hide';

    public _exibirLinkEditarLaboratorio: boolean = true;
    public _exibirBotoesEditarLaboratorio: boolean = true;
    public _mostraLaborarioPreferencial: string = '';
    public _mostraFormularioLaborarioPreferencial: string = 'hide';

    public _exibirLinkEditarMedicos: boolean = true;
    public _exibirBotoesEditarMedicos: boolean = true;
    public _mostraMedicos: string = '';
    public _mostraFormularioMedicos: string = 'hide';

    public _exibirLinkEditarEndereco: boolean = true;
    public _exibirBotoesEditarEndereco: boolean = true;
    public _mostraEndereco: string = '';
    public _mostraFormularioEndereco: string = 'hide';

    public _exibirLinkInserirEditarEnderecoRetirada: boolean = true;
    public _exibirBotoesInserirEditarEnderecoRetirada: boolean = true;
    public _mostraEnderecoRetirada: string = '';
    public _mostraFormularioEnderecoRetirada: string = 'hide';

    public _exibirLinkInserirEditarEnderecoEntrega: boolean = true;
    public _exibirBotoesInserirEditarEnderecoEntrega: boolean = true;
    public _mostraEnderecoEntrega: string = '';
    public _mostraFormularioEnderecoEntrega: string = 'hide';

    public _exibirLinkInserirEditarEnderecoWorkup: boolean = true;
    public _exibirBotoesInserirEditarEnderecoWorkup: boolean = true;
    public _mostraEnderecoWorkup: string = '';
    public _mostraFormularioEnderecoWorkup: string = 'hide';

    public _exibirLinkInserirEditarEnderecoGcsf: boolean = true;
    public _exibirBotoesInserirEditarEnderecoGcsf: boolean = true;
    public _mostraEnderecoGcsf: string = '';
    public _mostraFormularioEnderecoGcsf: string = 'hide';

    public _exibirLinkInserirEditarEnderecoInternacao: boolean = true;
    public _exibirBotoesInserirEditarEnderecoInternacao: boolean = true;
    public _mostraEnderecoInternacao: string = '';
    public _mostraFormularioEnderecoInternacao: string = 'hide';

    public _exibirLinkInserirTelefone: boolean = true;
    public _exibirBotoesInserirTelefone: boolean = true;
    public _mostraTelefone: string = '';
    public _mostraFormularioTelefone: string = 'hide';

    public _naoPossuiEnderecoRetirada: boolean = false;
    public _naoPossuiEnderecoEntrega: boolean = false;
    public _naoPossuiEnderecoWorkup: boolean = false;
    public _naoPossuiEnderecoGcsf: boolean = false;
    public _naoPossuiEnderecoInternacao: boolean = false;

    public _esconderBotaoAdicionarTelefone: string = "true";

    private _buildForm: BuildForm<CentroTransplante>;
    private _buildFormLaboratorio: BuildForm<Laboratorio>;
    private _buildFormDisponiveis:BuildForm<any>;
    private _buildFormSelecionados:BuildForm<any>;

    private _laboratorios: Laboratorio[];
    private _funcoes: FuncaoTransplante[];

    private _medicosDisponiveis: Medico[] = [];
    private _medicosListaTemporaria: Medico[] = [];
    private _medicosSelecionados: Medico[] = [];

    private _mensagemErroNumeroMaximo: "";

    public _exibirLinkInserirEmail: boolean = true;
    public _exibirBotoesInserirEmail: boolean = true;
    public _mostraEmail: string = '';
    public _validarEmailPrincipal: string = 'false';
    public _mostraFormularioEmail: string = 'hide';

    public _esconderBotaoAdicionarEmail: boolean = true;

    public consultaMedico: string;



    @ViewChild("endereco")
    private enderecoComponent: EnderecoContatoComponent;

    @ViewChild("enderecoRetirada")
    private enderecoRetiradaComponent: EnderecoContatoComponent;

    @ViewChild("enderecoEntrega")
    private enderecoEntregaComponent: EnderecoContatoComponent;

    @ViewChild("enderecoWorkup")
    private enderecoWorkupComponent: EnderecoContatoComponent;

    @ViewChild("enderecoGcsf")
    private enderecoGcsfComponent: EnderecoContatoComponent;

    @ViewChild("enderecoInternacao")
    private enderecoInternacaoComponent: EnderecoContatoComponent;

    @ViewChild('contatoTelefoneComponent')
    public contatoTelefoneComponent: ContatoTelefoneComponent;

    @ViewChild('emailContatoComponent')
    public emailContatoComponent: EmailContatoComponent;



    /**
     * @param FormBuilder
     * @param PacienteService
     * @param Router
     * @param TranslateService
     * @author Bruno Sousa
     */
    constructor(private router: Router, private activatedRouter: ActivatedRoute,
        private centroTransplanteService: CentroTransplanteService,
        private laboratioService: LaboratorioService, private messageBox: MessageBox,
        private enderecoContatoCentroTransplanteService: EnderecoContatoCentroTransplanteService,
        private enderecoContatoService: EnderecoContatoService,
        private contatoTelefonicoService: ContatoTelefonicoService,
        private medidoService: MedicoService, private translate: TranslateService,
        private emailContatoService: EmailContatoService ) {

        this._buildFormLaboratorio = new BuildForm<Laboratorio>()
            .add(new NumberControl("id"));

        this._buildFormDisponiveis = new BuildForm<any>()
            .add(new ArrayControl("selecionados"));

        this._buildFormSelecionados = new BuildForm<any>()
            .add(new ArrayControl("selecionados", [this.ValidateListMin, this.ValidateMaxItens] ));

        this._buildForm = new BuildForm<CentroTransplante>()
            .add(new StringControl("nome", [ Validators.required ] ))
            .add(new StringControl("cnpj", [ Validators.required ] ))
            .add(new StringControl("cnes", [ Validators.required ] ))
            .add(new BuildFormArray<any>("itens", this.ValidateSelected ));
    }

    ValidateSelected(control: FormArray): ValidationErrors {
        let value: any[] = control.value;
        if (value && control.dirty && !control.pristine) {
            let valid: boolean = false;
            value.forEach(item => {
                if (item['selected']) {
                    valid = true;
                }
            });

            if(!valid) {
                return { "required"  : true }
            }
        }
        return null;
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



    /**
     *
     * @memberOf DetalheComponent
     */
    ngOnInit(): void {

        this.aplicarValoresDefault();

        this.laboratioService.listarLaboratorios().then(res => {
            this._laboratorios = [];
            if (res.content) {
                res.content.forEach(laboratorio => {
                    this._laboratorios.push(new Laboratorio().jsonToEntity(laboratorio));
                })
            }
        })

        this.centroTransplanteService.listarFuncoes().then(res => {
            this._funcoes = [];
            if (res) {
                res.forEach(funcao => {
                    this._funcoes.push(new FuncaoTransplante().jsonToEntity(funcao));
                })
            }
            if (this._funcoes.length != 0) {
                this._funcoes.forEach(funcao => {
                    this._buildForm.getChildAsBuildFormArray("itens").add(
                        new BuildFormGroup<any>(funcao.id+"")
                            .add(new BooleanControl("selected", false ))
                    );
                });
            }
        });

        this.medidoService.listarMedicos(null, null, null).then(res => {
            if (res && res.content) {
                res.content.forEach(medico => {
                    let medicoObjeto:Medico = new Medico();
                    medicoObjeto.id = medico.usuario.id;
                    medicoObjeto.nome = medico.nome;
                    this._medicosDisponiveis.push(medicoObjeto);
                    this._medicosListaTemporaria = this._medicosDisponiveis;
                })
            }
        });

        if (this.enderecoComponent) {
            this.enderecoComponent.desabilitarCampoPais();
        }

        if (this.enderecoRetiradaComponent) {
            this.enderecoRetiradaComponent.desabilitarCampoPais();
        }

        if (this.enderecoEntregaComponent) {
            this.enderecoEntregaComponent.desabilitarCampoPais();
        }

        if (this.enderecoWorkupComponent) {
          this.enderecoWorkupComponent.desabilitarCampoPais();
        }

        if (this.enderecoGcsfComponent) {
          this.enderecoGcsfComponent.desabilitarCampoPais();
        }

        if (this.enderecoInternacaoComponent) {
          this.enderecoInternacaoComponent.desabilitarCampoPais();
        }

        RouterUtil.recuperarParametroDaActivatedRoute(this.activatedRouter, "idCentroTransplante").then(res => {
            if (res == "novo") {
                this.prepararTelaNovoCentroTransplante();
            }
            else {
                this._id = <number>res;
                this.obterCentro();
            }
        })
    }

    pesquisarMedicos(event: any){
        let valor = event.target.value;
        let medicosConsultados = this._medicosListaTemporaria.filter(o =>{
            return o.nome.indexOf(new String(valor).toUpperCase())> -1;
        } );
        this.medicosDisponiveis = medicosConsultados;
    }

    private obterCentro() {
        this.centroTransplanteService.obterCentroTransplante(this._id).then(centro => {
            this._centroTransplante = new CentroTransplante().jsonToEntity(centro);
        }, (error: ErroMensagem) => {
            ErroUtil.exibirMensagemErro(error, this.messageBox);
        });
    }

    private aplicarValoresDefault() {

        this.cancelarEdicaoDadosBasicos();
        this.cancelarEdicaoLaboratorioPreferencial();
        this.cancelarEdicaoEndereco();
        this.cancelarEdicaoEnderecoRetirada();
        this.cancelarEdicaoEnderecoEntrega();
        this.cancelarEdicaoEnderecoWorkup();
        this.cancelarEdicaoEnderecoGcsf();
        this.cancelarEdicaoEnderecoInternacao();
        this.cancelarEdicaoTelefone();
        this.cancelarEdicaoMedicos();
        this.cancelarEdicaoEmail();
    }

    private prepararTelaNovoCentroTransplante() {
        this._centroTransplante = new CentroTransplante();

        this.mostrarFormularioDadosBasicos();
        this.mostrarFormularioLaboratorioPreferencial();
        this.mostrarFormularioEndereco();
        this.mostrarFormularioEnderecoRetirada();
        this.mostrarFormularioEnderecoEntrega();
        this.mostrarFormularioEnderecoWorkup();
        this.mostrarFormularioEnderecoGcsf();
        this.mostrarFormularioEnderecoInternacao();
        this.mostrarFormularioTelefone();
        this.mostrarFormularioMedicos();
        this.mostrarFormularioEmail();


        this._exibirBotoesEditarDadosBasicos = false;
        this._exibirBotoesEditarLaboratorio = false;
        this._exibirBotoesEditarEndereco = false;
        this._exibirBotoesInserirEditarEnderecoRetirada = false;
        this._exibirBotoesInserirEditarEnderecoEntrega = false;
        this._exibirBotoesInserirEditarEnderecoWorkup = false;
        this._exibirBotoesInserirEditarEnderecoGcsf = false;
        this._exibirBotoesInserirEditarEnderecoInternacao = false;
        this._exibirBotoesInserirTelefone = false;
        this._exibirBotoesEditarMedicos = false;
        this._exibirBotoesInserirEmail = false;

        this._esconderBotaoAdicionarTelefone = "false";
        this._esconderBotaoAdicionarEmail = false;

        this._validarEmailPrincipal = 'true';
    }

    nomeComponente(): string {
        return ComponenteRecurso.Componente[ComponenteRecurso.Componente.DetalheCentroTransplanteComponent];
    }

    public get centroTransplante(): CentroTransplante {
        return this._centroTransplante
    }

    private mostrarFormularioDadosBasicos() {
        this._mostraDadosBasicos = 'hide';
        this._mostraFormularioDadosBasicos = '';
        this._exibirLinkEditarDadosBasicos = false;
        this._exibirBotoesEditarDadosBasicos = true;
    }

    public editarDadosBasicos(): void {

        this.mostrarFormularioDadosBasicos();

        let value: any = {
            'nome': this._centroTransplante.nome,
            'cnpj': FormatterUtil.aplicarMascaraCNPJ(this._centroTransplante.cnpj),
            'cnes': this._centroTransplante.cnes,
            'itens': {
            }
        }

        this._funcoes.forEach(funcao => {
            value['itens'][funcao.id+""] = {
                'selected': this.centroTransplante.funcoes.some(funcaoCentro => funcaoCentro.id == funcao.id)
            }
        });
        this._buildForm.form.reset();
        this._buildForm.value = value;

    }

    public cancelarEdicaoDadosBasicos(): void {
        this._mostraFormularioDadosBasicos = 'hide';
        this._mostraDadosBasicos = '';
        this._exibirLinkEditarDadosBasicos = true;
    }

    public salvarEdicaoDadosBasicos(): void {
        if (this._buildForm.valid) {
            let centro: CentroTransplante = new CentroTransplante().jsonToEntity(this._buildForm.value);
            centro.cnpj = FormatterUtil.obterCNPJSemMascara(centro.cnpj);
            centro.funcoes = [];
            let itens: any = this._buildForm.value['itens'];
            this._funcoes.forEach( (funcao, index) => {
                if (itens[funcao.id].selected) {
                    centro.funcoes.push(funcao);
                }
            });
            this.centroTransplanteService.atualizarDadosBasicos(this._id, centro).then(res => {
                this.messageBox.alert(res.mensagem)
                    .withTarget(this)
                    .withCloseOption((target?: any) => {
                        this.obterCentro();
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

    public get maskCnpj(): Array<string | RegExp> {
        return MascaraUtil.cnpj;
    }

    public formLaboratorio(): FormGroup {
        return <FormGroup>this._buildFormLaboratorio.form
    }

    /**
     * Getter laboratorios
     * @return {Laboratorio[]}
     */
	public get laboratorios(): Laboratorio[] {
		return this._laboratorios;
    }

    private mostrarFormularioLaboratorioPreferencial() {
        this._mostraLaborarioPreferencial = 'hide';
        this._mostraFormularioLaborarioPreferencial = '';
        this._exibirLinkEditarLaboratorio = false;
        this._exibirBotoesEditarLaboratorio = true;
    }

    private mostrarFormularioMedicos() {
        this._mostraMedicos = 'hide';
        this._mostraFormularioMedicos = '';
        this._exibirLinkEditarMedicos = false;
        this._exibirBotoesEditarMedicos = true;
        this._buildFormSelecionados.form.reset();
    }


    public editarLaboratorioPreferencial(): void {
        this.mostrarFormularioLaboratorioPreferencial();

        this._buildFormLaboratorio.form.reset();
        if (this._centroTransplante.laboratorioPreferencia && this._centroTransplante.laboratorioPreferencia.id) {
            this._buildFormLaboratorio.value = this._centroTransplante.laboratorioPreferencia;
        }
    }
    public editarMedicos(): void {
        this.mostrarFormularioMedicos();

        if (this._centroTransplante.centroTransplanteUsuarios && this._centroTransplante.centroTransplanteUsuarios.length > 0) {
            let listaMedicos: any[] = [];
            this._centroTransplante.centroTransplanteUsuarios.forEach( centroUsuario =>{
                listaMedicos.push(centroUsuario.usuario.id);
            })
            this.adicionarMedicos(listaMedicos);
        }
    }

    public cancelarEdicaoLaboratorioPreferencial(): void {
        this._mostraFormularioLaborarioPreferencial = 'hide';
        this._mostraLaborarioPreferencial = '';
        this._exibirLinkEditarLaboratorio = true;
    }

    public cancelarEdicaoMedicos(): void {
        this._mostraFormularioMedicos = 'hide';
        this._mostraMedicos = '';
        this._exibirLinkEditarMedicos = true;
    }


    public salvarEdicaoLaboratorioPreferencial(): void {
        if (this._buildFormLaboratorio.valid) {
            let laboratorio: Laboratorio = new Laboratorio().jsonToEntity(this._buildFormLaboratorio.value);
            if (laboratorio.id) {
                this.centroTransplanteService.atualizarLaboratorioPreferencia(this._id, laboratorio).then(res => {
                    this.messageBox.alert(res.mensagem)
                    .withTarget(this)
                    .withCloseOption((target?: any) => {
                        this.obterCentro();
                        this.cancelarEdicaoLaboratorioPreferencial();
                    })
                    .show();
                }, (error: ErroMensagem) => {
                    ErroUtil.exibirMensagemErro(error, this.messageBox);
                });
            }
            else {
                this.centroTransplanteService.removerLaboratorioPreferencia(this._id).then(res => {
                    this.messageBox.alert(res.mensagem)
                        .withTarget(this)
                        .withCloseOption((target?: any) => {
                            this.obterCentro();
                            this.cancelarEdicaoLaboratorioPreferencial();
                        })
                        .show();
                }, (error: ErroMensagem) => {
                    ErroUtil.exibirMensagemErro(error, this.messageBox);
                });
            }
        }
    }


    public getVertical(): PositionType {
        return PositionType.VERTICAL;
    }

    private mostrarFormularioEndereco() {
        this._mostraEndereco = 'hide';
        this._mostraFormularioEndereco = '';
        this._exibirLinkEditarEndereco = false;
        this._exibirBotoesEditarEndereco = true;
    }

    public editarEndereco(): void {
        this.enderecoComponent.clearForm();
        if (this._centroTransplante.enderecos) {
            let endereco: EnderecoContatoCentroTransplante = this._centroTransplante.enderecos.find(endereco => endereco.principal);
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

    public cancelarEdicaoEndereco(): void {
        this._mostraFormularioEndereco = 'hide';
        this._mostraEndereco = '';
        this._exibirLinkEditarEndereco = true;
    }

    public salvarEdicaoEndereco(): void {
        if (this.enderecoComponent.validateForm()) {
           let id: number;
            if (this._centroTransplante.enderecos) {
                id = this._centroTransplante.enderecos
                    .filter(endereco => endereco.principal)
                    .map(endereco => endereco.id)
                    .find(id => id !== null);
            }
            let endereco: EnderecoContatoCentroTransplante =
                new EnderecoContatoCentroTransplante().jsonToEntity(this.enderecoComponent.obterEndereco());
            endereco.centroTranplante = this._centroTransplante;
            endereco.principal = true;
            if (id) {
                endereco.id = id;
                this.enderecoContatoCentroTransplanteService.atualizarEnderecoContato(id, endereco).then(res => {
                    this.messageBox.alert(res.mensagem)
                        .withTarget(this)
                        .withCloseOption((target?: any) => {
                            this.obterCentro();
                            this.cancelarEdicaoEndereco();
                        })
                        .show();
                }, (error: ErroMensagem) => {
                    ErroUtil.exibirMensagemErro(error, this.messageBox);
                });
            }
            else {
                this.centroTransplanteService.adicionarEnderecoContato(this._id, endereco).then(res => {
                    this.messageBox.alert(res.mensagem)
                        .withTarget(this)
                        .withCloseOption((target?: any) => {
                            this.obterCentro();
                            this.cancelarEdicaoEndereco();
                        })
                        .show();
                }, (error: ErroMensagem) => {
                    ErroUtil.exibirMensagemErro(error, this.messageBox);
                });
            }

        }
    }

    public salvarEdicaoMedicos(){
        if (this._buildFormSelecionados.valid) {
            let centroTransplanteUsuarioList: CentroTransplanteUsuario[] =[];
            this._medicosSelecionados.forEach(medico =>{
                let centroUsuario: CentroTransplanteUsuario = new CentroTransplanteUsuario();
                centroUsuario.usuario = new UsuarioLogado();
                centroUsuario.usuario.id = medico.id;
                centroTransplanteUsuarioList.push(centroUsuario);
            });
            this.centroTransplanteService.atualizarMedicos(this._id, centroTransplanteUsuarioList).then(res => {
                this.messageBox.alert(res.mensagem)
                    .withTarget(this)
                    .withCloseOption((target?: any) => {
                        this.obterCentro();
                        this.cancelarEdicaoMedicos();
                    })
                    .show();
            }, (error: ErroMensagem) => {
                ErroUtil.exibirMensagemErro(error, this.messageBox);
            });
        }
    }

    private mostrarFormularioEnderecoRetirada() {
        this._mostraEnderecoRetirada = 'hide';
        this._mostraFormularioEnderecoRetirada = '';
        this._exibirLinkInserirEditarEnderecoRetirada = false;
        this._exibirBotoesInserirEditarEnderecoRetirada = true;
    }

    public editarEnderecoRetirada(): void {
        this.enderecoRetiradaComponent.clearForm();
        if (this._centroTransplante.enderecos) {
            let endereco: EnderecoContatoCentroTransplante = this._centroTransplante.enderecos.find(endereco => endereco.retirada);
            if (endereco) {
                this.enderecoRetiradaComponent.preencherFormulario(endereco);
            }
            else {
                this.enderecoRetiradaComponent.setValoresPadroes();
                this.enderecoRetiradaComponent.configEndNacionalForm();
            }
        }
        else {
            this.enderecoRetiradaComponent.setValoresPadroes();
            this.enderecoRetiradaComponent.configEndNacionalForm();
        }

        this.mostrarFormularioEnderecoRetirada();
    }

    public cancelarEdicaoEnderecoRetirada(): void {
        this._mostraFormularioEnderecoRetirada = 'hide';
        this._mostraEnderecoRetirada = '';
        this._exibirLinkInserirEditarEnderecoRetirada = true;
    }

    public salvarEdicaoEnderecoRetirada(): void {
        if (this.enderecoRetiradaComponent.validateForm()) {
           let id: number;
            if (this._centroTransplante.enderecos) {
                id = this._centroTransplante.enderecos
                    .filter(endereco => endereco.retirada)
                    .map(endereco => endereco.id)
                    .find(id => id !== null);
            }
            let endereco: EnderecoContatoCentroTransplante =
                new EnderecoContatoCentroTransplante().jsonToEntity(this.enderecoRetiradaComponent.obterEndereco());
            endereco.centroTranplante = this._centroTransplante;
            endereco.retirada = true;
            if (id) {
                endereco.id = id;
                this.enderecoContatoCentroTransplanteService.atualizarEnderecoContato(id, endereco).then(res => {
                    this.messageBox.alert(res.mensagem)
                        .withTarget(this)
                        .withCloseOption((target?: any) => {
                            this.obterCentro();
                            this.cancelarEdicaoEnderecoRetirada();
                        })
                        .show();
                }, (error: ErroMensagem) => {
                    ErroUtil.exibirMensagemErro(error, this.messageBox);
                });
            }
            else {
                this.centroTransplanteService.adicionarEnderecoContato(this._id, endereco).then(res => {
                    this.messageBox.alert(res.mensagem)
                        .withTarget(this)
                        .withCloseOption((target?: any) => {
                            this.obterCentro();
                            this.cancelarEdicaoEnderecoRetirada();
                        })
                        .show();
                }, (error: ErroMensagem) => {
                    ErroUtil.exibirMensagemErro(error, this.messageBox);
                });
            }
        }
    }

    public excluirEnderecoContatoRetirada(endereco: EnderecoContatoCentroTransplante): void {
        this.enderecoContatoService.excluirEnderecoContatoPorId(endereco.id).then(res => {
            this.messageBox.alert(res)
                .withTarget(this)
                .withCloseOption((target?: any) => {
                    this.obterCentro();
                })
                .show();
        }, (error: ErroMensagem) => {
            ErroUtil.exibirMensagemErro(error, this.messageBox);
        });
    }

    public copiarEnderecoParaEnderecoRetirada(): void {
        this.enderecoRetiradaComponent.clearForm();
        if (this._id) {
            if (this._centroTransplante.enderecos) {
                let endereco: EnderecoContatoCentroTransplante = new EnderecoContatoCentroTransplante()
                    .jsonToEntity(this._centroTransplante.enderecos.find(endereco => endereco.principal));
                if (endereco) {
                    endereco.principal = false;
                    this.enderecoRetiradaComponent.preencherFormulario(endereco);
                }
                else {
                    this.enderecoRetiradaComponent.setValoresPadroes();
                    this.enderecoRetiradaComponent.configEndNacionalForm();
                }
            }
        }
        else {
            let endereco: EnderecoContato = this.enderecoComponent.obterEndereco();
            endereco.principal = false;
            this.enderecoRetiradaComponent.preencherFormulario(endereco);
        }
    }

    private mostrarFormularioEnderecoEntrega() {
        this._mostraEnderecoEntrega = 'hide';
        this._mostraFormularioEnderecoEntrega = '';
        this._exibirLinkInserirEditarEnderecoEntrega = false;
        this._exibirBotoesInserirEditarEnderecoRetirada = true;
    }

    public editarEnderecoEntrega(): void {
        this.enderecoEntregaComponent.clearForm();
        if (this._centroTransplante.enderecos) {
            let endereco: EnderecoContatoCentroTransplante = this._centroTransplante.enderecos.find(endereco => endereco.entrega);
            if (endereco) {
                this.enderecoEntregaComponent.preencherFormulario(endereco);
            }
            else {
                this.enderecoEntregaComponent.setValoresPadroes();
                this.enderecoEntregaComponent.configEndNacionalForm();
            }
        }
        else {
            this.enderecoEntregaComponent.setValoresPadroes();
            this.enderecoEntregaComponent.configEndNacionalForm();
        }
        this.mostrarFormularioEnderecoEntrega();
    }

    public cancelarEdicaoEnderecoEntrega(): void {
        this._mostraFormularioEnderecoEntrega = 'hide';
        this._mostraEnderecoEntrega = '';
        this._exibirLinkInserirEditarEnderecoEntrega = true;
    }

    public salvarEdicaoEnderecoEntrega(): void {
        if (this.enderecoEntregaComponent.validateForm()) {
           let id: number;
            if (this._centroTransplante.enderecos) {
                id = this._centroTransplante.enderecos
                    .filter(endereco => endereco.entrega)
                    .map(endereco => endereco.id)
                    .find(id => id !== null);
            }
            let endereco: EnderecoContatoCentroTransplante =
                new EnderecoContatoCentroTransplante().jsonToEntity(this.enderecoEntregaComponent.obterEndereco());
            endereco.centroTranplante = this._centroTransplante;
            endereco.entrega = true;
            if (id) {
                endereco.id = id;
                this.enderecoContatoCentroTransplanteService.atualizarEnderecoContato(id, endereco).then(res => {
                    this.messageBox.alert(res.mensagem)
                        .withTarget(this)
                        .withCloseOption((target?: any) => {
                            this.obterCentro();
                            this.cancelarEdicaoEnderecoEntrega();
                        })
                        .show();
                }, (error: ErroMensagem) => {
                    ErroUtil.exibirMensagemErro(error, this.messageBox);
                });
            }
            else {
                this.centroTransplanteService.adicionarEnderecoContato(this._id, endereco).then(res => {
                    this.messageBox.alert(res.mensagem)
                        .withTarget(this)
                        .withCloseOption((target?: any) => {
                            this.obterCentro();
                            this.cancelarEdicaoEnderecoEntrega();
                        })
                        .show();
                }, (error: ErroMensagem) => {
                    ErroUtil.exibirMensagemErro(error, this.messageBox);
                });
            }
        }
    }

    public excluirEnderecoContatoEntrega(endereco: EnderecoContatoCentroTransplante): void {
        this.enderecoContatoService.excluirEnderecoContatoPorId(endereco.id).then(res => {
            this.messageBox.alert(res)
                .withTarget(this)
                .withCloseOption((target?: any) => {
                    this.obterCentro();
                })
                .show();
        }, (error: ErroMensagem) => {
            ErroUtil.exibirMensagemErro(error, this.messageBox);
        });
    }

    public copiarEnderecoParaEnderecoEntrega(): void {
        this.enderecoEntregaComponent.clearForm();
        if (this._id) {
            if (this._centroTransplante.enderecos) {
                let endereco: EnderecoContatoCentroTransplante = new EnderecoContatoCentroTransplante()
                    .jsonToEntity(this._centroTransplante.enderecos.find(endereco => endereco.principal));
                if (endereco) {
                    endereco.principal = false;
                    this.enderecoEntregaComponent.preencherFormulario(endereco);
                }
                else {
                    this.enderecoEntregaComponent.setValoresPadroes();
                    this.enderecoEntregaComponent.configEndNacionalForm();
                }
            }
        }
        else {
            let endereco: EnderecoContato = this.enderecoComponent.obterEndereco();
            endereco.principal = false;
            this.enderecoEntregaComponent.preencherFormulario(endereco);
        }
    }

    //########## ENDEREÇO WORKUP #############

    private mostrarFormularioEnderecoWorkup() {
        this._mostraEnderecoWorkup = 'hide';
        this._mostraFormularioEnderecoWorkup = '';
        this._exibirLinkInserirEditarEnderecoWorkup = false;
        this._exibirBotoesInserirEditarEnderecoWorkup = true;
    }

    public editarEnderecoWorkup(): void {
        this.enderecoWorkupComponent.clearForm();
        if (this._centroTransplante.enderecos) {
            let endereco: EnderecoContatoCentroTransplante = this._centroTransplante.enderecos.find(endereco => endereco.workup);
            if (endereco) {
                this.enderecoWorkupComponent.preencherFormulario(endereco);
            }
            else {
                this.enderecoWorkupComponent.setValoresPadroes();
                this.enderecoWorkupComponent.configEndNacionalForm();
            }
        }
        else {
            this.enderecoWorkupComponent.setValoresPadroes();
            this.enderecoWorkupComponent.configEndNacionalForm();
        }
        this.mostrarFormularioEnderecoWorkup();
    }

    public cancelarEdicaoEnderecoWorkup(): void {
        this._mostraFormularioEnderecoWorkup = 'hide';
        this._mostraEnderecoWorkup = '';
        this._exibirLinkInserirEditarEnderecoWorkup = true;
    }

    public salvarEdicaoEnderecoWorkup(): void {
        if (this.enderecoWorkupComponent.validateForm()) {
           let id: number;
            if (this._centroTransplante.enderecos) {
                id = this._centroTransplante.enderecos
                    .filter(endereco => endereco.workup)
                    .map(endereco => endereco.id)
                    .find(id => id !== null);
            }
            let endereco: EnderecoContatoCentroTransplante =
                new EnderecoContatoCentroTransplante().jsonToEntity(this.enderecoWorkupComponent.obterEndereco());
            endereco.centroTranplante = this._centroTransplante;
            endereco.workup = true;
            if (id) {
                endereco.id = id;
                this.enderecoContatoCentroTransplanteService.atualizarEnderecoContato(id, endereco).then(res => {
                    this.messageBox.alert(res.mensagem)
                        .withTarget(this)
                        .withCloseOption((target?: any) => {
                            this.obterCentro();
                            this.cancelarEdicaoEnderecoWorkup();
                        })
                        .show();
                }, (error: ErroMensagem) => {
                    ErroUtil.exibirMensagemErro(error, this.messageBox);
                });
            }
            else {
                this.centroTransplanteService.adicionarEnderecoContato(this._id, endereco).then(res => {
                    this.messageBox.alert(res.mensagem)
                        .withTarget(this)
                        .withCloseOption((target?: any) => {
                            this.obterCentro();
                            this.cancelarEdicaoEnderecoWorkup();
                        })
                        .show();
                }, (error: ErroMensagem) => {
                    ErroUtil.exibirMensagemErro(error, this.messageBox);
                });
            }
        }
    }

    public excluirEnderecoContatoWorkup(endereco: EnderecoContatoCentroTransplante): void {
        this.enderecoContatoService.excluirEnderecoContatoPorId(endereco.id).then(res => {
            this.messageBox.alert(res)
                .withTarget(this)
                .withCloseOption((target?: any) => {
                    this.obterCentro();
                })
                .show();
        }, (error: ErroMensagem) => {
            ErroUtil.exibirMensagemErro(error, this.messageBox);
        });
    }

    public copiarEnderecoParaEnderecoWorkup(): void {
        this.enderecoWorkupComponent.clearForm();
        if (this._id) {
            if (this._centroTransplante.enderecos) {
                let endereco: EnderecoContatoCentroTransplante = new EnderecoContatoCentroTransplante()
                    .jsonToEntity(this._centroTransplante.enderecos.find(endereco => endereco.principal));
                if (endereco) {
                    endereco.principal = false;
                    this.enderecoWorkupComponent.preencherFormulario(endereco);
                }
                else {
                    this.enderecoWorkupComponent.setValoresPadroes();
                    this.enderecoWorkupComponent.configEndNacionalForm();
                }
            }
        }
        else {
            let endereco: EnderecoContato = this.enderecoComponent.obterEndereco();
            endereco.principal = false;
            this.enderecoWorkupComponent.preencherFormulario(endereco);
        }
    }

    // #################################################################

    //########## ENDEREÇO G-CSF #############

    private mostrarFormularioEnderecoGcsf() {
      this._mostraEnderecoGcsf = 'hide';
      this._mostraFormularioEnderecoGcsf = '';
      this._exibirLinkInserirEditarEnderecoGcsf = false;
      this._exibirBotoesInserirEditarEnderecoGcsf = true;
  }

  public editarEnderecoGcsf(): void {
      this.enderecoGcsfComponent.clearForm();
      if (this._centroTransplante.enderecos) {
          let endereco: EnderecoContatoCentroTransplante = this._centroTransplante.enderecos.find(endereco => endereco.gcsf);
          if (endereco) {
              this.enderecoGcsfComponent.preencherFormulario(endereco);
          }
          else {
              this.enderecoGcsfComponent.setValoresPadroes();
              this.enderecoGcsfComponent.configEndNacionalForm();
          }
      }
      else {
          this.enderecoGcsfComponent.setValoresPadroes();
          this.enderecoGcsfComponent.configEndNacionalForm();
      }
      this.mostrarFormularioEnderecoGcsf();
  }

  public cancelarEdicaoEnderecoGcsf(): void {
      this._mostraFormularioEnderecoGcsf = 'hide';
      this._mostraEnderecoGcsf = '';
      this._exibirLinkInserirEditarEnderecoGcsf = true;
  }

  public salvarEdicaoEnderecoGcsf(): void {
      if (this.enderecoGcsfComponent.validateForm()) {
         let id: number;
          if (this._centroTransplante.enderecos) {
              id = this._centroTransplante.enderecos
                  .filter(endereco => endereco.gcsf)
                  .map(endereco => endereco.id)
                  .find(id => id !== null);
          }
          let endereco: EnderecoContatoCentroTransplante =
              new EnderecoContatoCentroTransplante().jsonToEntity(this.enderecoGcsfComponent.obterEndereco());
          endereco.centroTranplante = this._centroTransplante;
          endereco.gcsf = true;
          if (id) {
              endereco.id = id;
              this.enderecoContatoCentroTransplanteService.atualizarEnderecoContato(id, endereco).then(res => {
                  this.messageBox.alert(res.mensagem)
                      .withTarget(this)
                      .withCloseOption((target?: any) => {
                          this.obterCentro();
                          this.cancelarEdicaoEnderecoGcsf();
                      })
                      .show();
              }, (error: ErroMensagem) => {
                  ErroUtil.exibirMensagemErro(error, this.messageBox);
              });
          }
          else {
              this.centroTransplanteService.adicionarEnderecoContato(this._id, endereco).then(res => {
                  this.messageBox.alert(res.mensagem)
                      .withTarget(this)
                      .withCloseOption((target?: any) => {
                          this.obterCentro();
                          this.cancelarEdicaoEnderecoGcsf();
                      })
                      .show();
              }, (error: ErroMensagem) => {
                  ErroUtil.exibirMensagemErro(error, this.messageBox);
              });
          }
      }
  }

  public excluirEnderecoContatoGcsf(endereco: EnderecoContatoCentroTransplante): void {
      this.enderecoContatoService.excluirEnderecoContatoPorId(endereco.id).then(res => {
          this.messageBox.alert(res)
              .withTarget(this)
              .withCloseOption((target?: any) => {
                  this.obterCentro();
              })
              .show();
      }, (error: ErroMensagem) => {
          ErroUtil.exibirMensagemErro(error, this.messageBox);
      });
  }

  public copiarEnderecoParaEnderecoGcsf(): void {
      this.enderecoGcsfComponent.clearForm();
      if (this._id) {
          if (this._centroTransplante.enderecos) {
              let endereco: EnderecoContatoCentroTransplante = new EnderecoContatoCentroTransplante()
                  .jsonToEntity(this._centroTransplante.enderecos.find(endereco => endereco.principal));
              if (endereco) {
                  endereco.principal = false;
                  this.enderecoGcsfComponent.preencherFormulario(endereco);
              }
              else {
                  this.enderecoGcsfComponent.setValoresPadroes();
                  this.enderecoGcsfComponent.configEndNacionalForm();
              }
          }
      }
      else {
          let endereco: EnderecoContato = this.enderecoComponent.obterEndereco();
          endereco.principal = false;
          this.enderecoGcsfComponent.preencherFormulario(endereco);
      }
  }

  // #################################################################

    //########## ENDEREÇO G-CSF #############

    private mostrarFormularioEnderecoInternacao() {
      this._mostraEnderecoInternacao = 'hide';
      this._mostraFormularioEnderecoInternacao = '';
      this._exibirLinkInserirEditarEnderecoInternacao = false;
      this._exibirBotoesInserirEditarEnderecoInternacao = true;
  }

  public editarEnderecoInternacao(): void {
      this.enderecoInternacaoComponent.clearForm();
      if (this._centroTransplante.enderecos) {
          let endereco: EnderecoContatoCentroTransplante = this._centroTransplante.enderecos.find(endereco => endereco.internacao);
          if (endereco) {
              this.enderecoInternacaoComponent.preencherFormulario(endereco);
          }
          else {
              this.enderecoInternacaoComponent.setValoresPadroes();
              this.enderecoInternacaoComponent.configEndNacionalForm();
          }
      }
      else {
          this.enderecoInternacaoComponent.setValoresPadroes();
          this.enderecoInternacaoComponent.configEndNacionalForm();
      }
      this.mostrarFormularioEnderecoInternacao();
  }

  public cancelarEdicaoEnderecoInternacao(): void {
      this._mostraFormularioEnderecoInternacao = 'hide';
      this._mostraEnderecoInternacao = '';
      this._exibirLinkInserirEditarEnderecoInternacao = true;
  }

  public salvarEdicaoEnderecoInternacao(): void {
      if (this.enderecoInternacaoComponent.validateForm()) {
         let id: number;
          if (this._centroTransplante.enderecos) {
              id = this._centroTransplante.enderecos
                  .filter(endereco => endereco.internacao)
                  .map(endereco => endereco.id)
                  .find(id => id !== null);
          }
          let endereco: EnderecoContatoCentroTransplante =
              new EnderecoContatoCentroTransplante().jsonToEntity(this.enderecoInternacaoComponent.obterEndereco());
          endereco.centroTranplante = this._centroTransplante;
          endereco.internacao = true;
          if (id) {
              endereco.id = id;
              this.enderecoContatoCentroTransplanteService.atualizarEnderecoContato(id, endereco).then(res => {
                  this.messageBox.alert(res.mensagem)
                      .withTarget(this)
                      .withCloseOption((target?: any) => {
                          this.obterCentro();
                          this.cancelarEdicaoEnderecoInternacao();
                      })
                      .show();
              }, (error: ErroMensagem) => {
                  ErroUtil.exibirMensagemErro(error, this.messageBox);
              });
          }
          else {
              this.centroTransplanteService.adicionarEnderecoContato(this._id, endereco).then(res => {
                  this.messageBox.alert(res.mensagem)
                      .withTarget(this)
                      .withCloseOption((target?: any) => {
                          this.obterCentro();
                          this.cancelarEdicaoEnderecoInternacao();
                      })
                      .show();
              }, (error: ErroMensagem) => {
                  ErroUtil.exibirMensagemErro(error, this.messageBox);
              });
          }
      }
  }

  public excluirEnderecoContatoInternacao(endereco: EnderecoContatoCentroTransplante): void {
      this.enderecoContatoService.excluirEnderecoContatoPorId(endereco.id).then(res => {
          this.messageBox.alert(res)
              .withTarget(this)
              .withCloseOption((target?: any) => {
                  this.obterCentro();
              })
              .show();
      }, (error: ErroMensagem) => {
          ErroUtil.exibirMensagemErro(error, this.messageBox);
      });
  }

  public copiarEnderecoParaEnderecoInternacao(): void {
      this.enderecoInternacaoComponent.clearForm();
      if (this._id) {
          if (this._centroTransplante.enderecos) {
              let endereco: EnderecoContatoCentroTransplante = new EnderecoContatoCentroTransplante()
                  .jsonToEntity(this._centroTransplante.enderecos.find(endereco => endereco.principal));
              if (endereco) {
                  endereco.principal = false;
                  this.enderecoInternacaoComponent.preencherFormulario(endereco);
              }
              else {
                  this.enderecoInternacaoComponent.setValoresPadroes();
                  this.enderecoInternacaoComponent.configEndNacionalForm();
              }
          }
      }
      else {
          let endereco: EnderecoContato = this.enderecoComponent.obterEndereco();
          endereco.principal = false;
          this.enderecoInternacaoComponent.preencherFormulario(endereco);
      }
  }

  // #################################################################


    private mostrarFormularioTelefone() {
        this._mostraTelefone = 'hide';
        this._mostraFormularioTelefone = '';
        this._exibirLinkInserirTelefone = false;
        this._exibirBotoesInserirTelefone = true;
        this._esconderBotaoAdicionarTelefone = "true";
    }

    public incluirTelefone() {
        this.contatoTelefoneComponent.buildForm();
        this.mostrarFormularioTelefone();
    }

    public salvarContatoTelefonico(): void {
        if (this.contatoTelefoneComponent.validateForm()) {
            let contatosTelefonico: ContatoTelefonicoCentroTransplante[] = [];
            this.contatoTelefoneComponent.listarTelefonesContato().forEach(contato => {
                let contatoTelefonico: ContatoTelefonicoCentroTransplante = new ContatoTelefonicoCentroTransplante().jsonToEntity(contato);
                contatoTelefonico.centroTransplante = this._centroTransplante;
                contatosTelefonico.push(contatoTelefonico);
            });
            this.centroTransplanteService.adicionarContatoTelefonico(this._id, contatosTelefonico[0]).then(res => {
                this.messageBox.alert(res.mensagem)
                    .withTarget(this)
                    .withCloseOption((target?: any) => {
                        this.obterCentro();
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
                    this.obterCentro();
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
        this._exibirLinkInserirTelefone = true;
    }

    public formatarCnpj(cnpj: string): string {
        return FormatterUtil.aplicarMascaraCNPJ(cnpj);
    }

    public listaDescricaoFuncao(): string[] {
        if (this._funcoes) {
            return this._funcoes.map(funcao => funcao.descricao);
        }
        return [];
    }

    public deveExibirEditarEnderecoRetirada(): boolean {
        if (this._centroTransplante && this._centroTransplante.enderecos) {
            return this._centroTransplante.enderecos.some(endereco => endereco.retirada);
        }
        return false;
    }

    public deveExibirEditarEnderecoEntrega(): boolean {
        if (this._centroTransplante && this._centroTransplante.enderecos) {
            return this._centroTransplante.enderecos.some(endereco => endereco.entrega);
        }
        return false;
    }

    public deveExibirEditarEnderecoWorkup(): boolean {
        if (this._centroTransplante && this._centroTransplante.enderecos) {
            return this._centroTransplante.enderecos.some(endereco => endereco.workup);
        }
        return false;
    }

    public deveExibirEditarEnderecoGcsf(): boolean {
      if (this._centroTransplante && this._centroTransplante.enderecos) {
          return this._centroTransplante.enderecos.some(endereco => endereco.gcsf);
      }
      return false;
    }
    public deveExibirEditarEnderecoInternacao(): boolean {
      if (this._centroTransplante && this._centroTransplante.enderecos) {
          return this._centroTransplante.enderecos.some(endereco => endereco.internacao);
      }
      return false;
    }

    private validarNovoCentroTransplante(): boolean {
        let valid: boolean = this._buildForm.valid && this._buildFormLaboratorio.valid;
        let validEndereco: boolean = this.enderecoComponent.validateForm();
        let validEnderecoRetirada: boolean = false;

        if (this._naoPossuiEnderecoRetirada) {
            validEnderecoRetirada = true;
        }
        else {
            validEnderecoRetirada = this.enderecoRetiradaComponent.validateForm();
        }

        let validEnderecoEntrega: boolean = false;
        if (this._naoPossuiEnderecoEntrega) {
            validEnderecoEntrega = true;
        }
        else {
            validEnderecoEntrega = this.enderecoEntregaComponent.validateForm();
        }

        let validEnderecoWorkup: boolean = false;
        if (this._naoPossuiEnderecoWorkup) {
            validEnderecoWorkup = true;
        }
        else {
            validEnderecoWorkup = this.enderecoWorkupComponent.validateForm();
        }

        let validEnderecoGcsf: boolean = false;
        if (this._naoPossuiEnderecoGcsf) {
            validEnderecoGcsf = true;
        }
        else {
            validEnderecoGcsf = this.enderecoGcsfComponent.validateForm();
        }

        let validEnderecoInternacao: boolean = false;
        if (this._naoPossuiEnderecoInternacao) {
            validEnderecoInternacao = true;
        }
        else {
            validEnderecoInternacao = this.enderecoInternacaoComponent.validateForm();
        }

        let validTelefone: boolean = this.contatoTelefoneComponent.validateForm();

        let validEmail : boolean = this.emailContatoComponent.validateForm();

        let validMedicoResponsavel: boolean = this._buildFormSelecionados.valid;

        return valid && validEndereco && validEnderecoRetirada && validEnderecoEntrega && validEnderecoWorkup
              && validEnderecoGcsf && validEnderecoInternacao && validTelefone && validMedicoResponsavel && validEmail;
    }

    public salvarNovoCentroTransplante() {
        if (this.validarNovoCentroTransplante()) {
            let centro: CentroTransplante = new CentroTransplante().jsonToEntity(this._buildForm.value);
            centro.cnpj = FormatterUtil.obterCNPJSemMascara(centro.cnpj);
            centro.funcoes = [];
            let itens: any = this._buildForm.value['itens'];
            this._funcoes.forEach( (funcao, index) => {
                if (itens[funcao.id].selected) {
                    centro.funcoes.push(funcao);
                }
            });
            centro.laboratorioPreferencia = new Laboratorio().jsonToEntity(this._buildFormLaboratorio.value);
            let endereco: EnderecoContatoCentroTransplante =
                new EnderecoContatoCentroTransplante().jsonToEntity(this.enderecoComponent.obterEndereco());
            endereco.centroTranplante = this._centroTransplante;
            endereco.principal = true;
            centro.enderecos = [];
            centro.enderecos.push(endereco);

            if (!this._naoPossuiEnderecoRetirada) {
                let enderecoRetirada: EnderecoContatoCentroTransplante =
                    new EnderecoContatoCentroTransplante().jsonToEntity(this.enderecoRetiradaComponent.obterEndereco());
                enderecoRetirada.centroTranplante = this._centroTransplante;
                enderecoRetirada.principal = false;
                enderecoRetirada.retirada = true;
                centro.enderecos.push(enderecoRetirada);
            }

            if (!this._naoPossuiEnderecoEntrega) {
                let enderecoEntrega: EnderecoContatoCentroTransplante =
                    new EnderecoContatoCentroTransplante().jsonToEntity(this.enderecoEntregaComponent.obterEndereco());
                enderecoEntrega.centroTranplante = this._centroTransplante;
                enderecoEntrega.principal = false;
                enderecoEntrega.entrega = true;
                centro.enderecos.push(enderecoEntrega);
            }

            if (!this._naoPossuiEnderecoWorkup) {
                let enderecoWorkup: EnderecoContatoCentroTransplante =
                    new EnderecoContatoCentroTransplante().jsonToEntity(this.enderecoWorkupComponent.obterEndereco());
                enderecoWorkup.centroTranplante = this._centroTransplante;
                enderecoWorkup.principal = false;
                enderecoWorkup.entrega = true;
                centro.enderecos.push(enderecoWorkup);
            }

            if (!this._naoPossuiEnderecoGcsf) {
              let enderecoGcsf: EnderecoContatoCentroTransplante =
                  new EnderecoContatoCentroTransplante().jsonToEntity(this.enderecoGcsfComponent.obterEndereco());
              enderecoGcsf.centroTranplante = this._centroTransplante;
              enderecoGcsf.principal = false;
              enderecoGcsf.entrega = true;
              centro.enderecos.push(enderecoGcsf);
            }

            if (!this._naoPossuiEnderecoInternacao) {
              let enderecoInternacao: EnderecoContatoCentroTransplante =
                  new EnderecoContatoCentroTransplante().jsonToEntity(this.enderecoInternacaoComponent.obterEndereco());
              enderecoInternacao.centroTranplante = this._centroTransplante;
              enderecoInternacao.principal = false;
              enderecoInternacao.entrega = true;
              centro.enderecos.push(enderecoInternacao);
            }

            centro.contatosTelefonicos = [];
            this.contatoTelefoneComponent.listarTelefonesContato().forEach(contato => {
                let contatoTelefonico: ContatoTelefonicoCentroTransplante = new ContatoTelefonicoCentroTransplante().jsonToEntity(contato);
                contatoTelefonico.centroTransplante = this._centroTransplante;
                centro.contatosTelefonicos.push(contatoTelefonico);
            });

            let centroTransplanteUsuarioList: CentroTransplanteUsuario[] =[];
            this._medicosSelecionados.forEach(medico =>{
                let centroUsuario: CentroTransplanteUsuario = new CentroTransplanteUsuario();
                centroUsuario.usuario = new UsuarioLogado();
                centroUsuario.usuario.id = medico.id;
                centroTransplanteUsuarioList.push(centroUsuario);
            });
            centro.centroTransplanteUsuarios = centroTransplanteUsuarioList;

            centro.emails = [];
            this.emailContatoComponent.listar().forEach(email => {
                let emailContato: EmailContatoCentroTransplante = new EmailContatoCentroTransplante().jsonToEntity(email);
                emailContato.centroTransplante = this._centroTransplante;
                centro.emails.push(emailContato);
            });

            this.centroTransplanteService.salvarCentroTransplante(centro).then(res => {
                this.messageBox.alert(res.mensagem)
                    .withTarget(this)
                    .withCloseOption((target?: any) => {
                        this.router.navigateByUrl("/admin/centrostransplante/" + res.idObjeto).then(res => {
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

    public naoPossuiEnderecoRetirada(value: boolean) {
        this._naoPossuiEnderecoRetirada = value;
        if (value) {
            this.enderecoRetiradaComponent.clearForm();
        }
    }

    public naoPossuiEnderecoEntrega(value: boolean) {
        this._naoPossuiEnderecoEntrega = value;
        if (value) {
            this.enderecoEntregaComponent.clearForm();
        }
    }

    public naoPossuiEnderecoWorkup(value: boolean) {
        this._naoPossuiEnderecoWorkup = value;
        if (value) {
            this.enderecoWorkupComponent.clearForm();
        }
    }

    public naoPossuiEnderecoGcsf(value: boolean) {
      this._naoPossuiEnderecoGcsf = value;
      if (value) {
          this.enderecoGcsfComponent.clearForm();
      }
    }

    public naoPossuiEnderecoInternacao(value: boolean) {
      this._naoPossuiEnderecoInternacao = value;
      if (value) {
          this.enderecoInternacaoComponent.clearForm();
      }
    }

    public adicionarMedicos(medicosLista? : any[]) {
        if ((this._buildFormDisponiveis.value && this._buildFormDisponiveis.value.selecionados &&
            this._buildFormDisponiveis.value.selecionados.length != 0)||medicosLista) {
            let selecionados = medicosLista? medicosLista: this._buildFormDisponiveis.value.selecionados;
            for (let idx = 0; idx < selecionados.length; idx++) {
                let indice:number = 0;
                let medico: Medico = this._medicosDisponiveis
                    .find((medico, index) => {
                        indice = index;
                        return medico.id == selecionados[idx];
                    });
                if (medico) {
                    this._medicosDisponiveis.splice(indice, 1);
                    this._medicosSelecionados.push(medico);
                }
            }
            this._buildFormSelecionados.getChildAsBuildFormControl("selecionados").value =
                this._medicosSelecionados.map(medico => medico.id);
            this.medicosDisponiveis = this._medicosListaTemporaria;
            this.consultaMedico = "";
        }
    }

    public removerMedicos() {
        if (this._buildFormSelecionados.value && this._buildFormSelecionados.value.selecionados &&
            this._buildFormSelecionados.value.selecionados.length != 0) {
            let selecionados = this._buildFormSelecionados.value.selecionados;
            for (let idx = 0; idx < selecionados.length; idx++) {
                let indice:number = 0;
                let medico: Medico = this._medicosSelecionados
                    .find((medico, index) => {
                        indice = index;
                        return medico.id == selecionados[idx];
                    });
                if (medico) {
                    this._medicosSelecionados.splice(indice, 1);
                    this._medicosDisponiveis.push(medico);
                }
            }
            this._buildFormSelecionados.form.reset();
            if (this._medicosSelecionados.length != 0) {
                this._buildFormSelecionados.getChildAsBuildFormControl("selecionados").value =
                    this._medicosSelecionados.map(medico => medico.id);
            }
        }
    }



    /**
     * Getter medicosDisponiveis
     * @return {Medico[]}
     */
	public get medicosDisponiveis(): Medico[] {
		return this._medicosDisponiveis;
	}

    /**
     * Setter medicosDisponiveis
     * @param {Medico[]} value
     */
	public set medicosDisponiveis(value: Medico[]) {
		this._medicosDisponiveis = value;
    }

    /**
     * Getter medicosSelecionados
     * @return {Medico[]}
     */
	public get medicosSelecionados(): Medico[] {
		return this._medicosSelecionados;
	}

    /**
     * Setter medicosSelecionados
     * @param {Medico[]} value
     */
	public set medicosSelecionados(value: Medico[]) {
		this._medicosSelecionados = value;
    }

    public formMedicosDisponiveis(): FormGroup {
        return this._buildFormDisponiveis.form;
    }

    public formMedicosSelecionados(): FormGroup {
        return this._buildFormSelecionados.form;
    }

    private mostrarFormularioEmail() {
        this._mostraEmail = 'hide';
        this._mostraFormularioEmail = '';
        this._exibirLinkInserirEmail = false;
        this._exibirBotoesInserirEmail = true;
        this._esconderBotaoAdicionarEmail = true;
    }

    public incluirEmail() {
        this.emailContatoComponent.buildForm();
        this.mostrarFormularioEmail();
    }

    public salvarEmail(): void {
        if (this.emailContatoComponent.validateForm()) {
            let emailsContato: EmailContatoCentroTransplante[] = [];
            this.emailContatoComponent.listar().forEach(email => {
                let emailContato: EmailContatoCentroTransplante = new EmailContatoCentroTransplante().jsonToEntity(email);
                emailContato.centroTransplante = this._centroTransplante;
                emailsContato.push(emailContato);
            });
            this.centroTransplanteService.adicionarEmailContato(this._id, emailsContato[0]).then(res => {
                this.messageBox.alert(res.mensagem)
                    .withTarget(this)
                    .withCloseOption((target?: any) => {
                        this.obterCentro();
                        this.cancelarEdicaoEmail();
                    })
                    .show();
            }, (error: ErroMensagem) => {
                ErroUtil.exibirMensagemErro(error, this.messageBox);
            });
        }
    }

    public excluirEmail(email: EmailContatoCentroTransplante) {
        this.emailContatoService.excluirEmailContatoPorId(email.id).then(res => {
            this.messageBox.alert(res)
                .withTarget(this)
                .withCloseOption((target?: any) => {
                    this.obterCentro();
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
        this._exibirLinkInserirEmail = true;
        this._validarEmailPrincipal = 'false';
    }

}
