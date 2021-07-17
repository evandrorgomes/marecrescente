import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { environment } from 'environments/environment';
import { Job } from "node-schedule";
import { BaseEntidade } from '../../shared/base.entidade';
import { BaseForm } from '../../shared/base.form';
import { CampoMensagem } from '../../shared/campo.mensagem';
import { EntidadeUtil } from '../../shared/classes/entidade.util';
import { IntervaloTempo } from '../../shared/classes/schedule/intervalo.tempo';
import { CentroTransplante } from '../../shared/dominio/centro.transplante';
import { CondicaoPaciente } from '../../shared/dominio/condicaoPaciente';
import { Configuracao } from '../../shared/dominio/configuracao';
import { DominioService } from '../../shared/dominio/dominio.service';
import { EstagioDoenca } from '../../shared/dominio/estagio.doenca';
import { Etnia } from '../../shared/dominio/etnia';
import { Motivo } from '../../shared/dominio/motivo';
import { Pais } from '../../shared/dominio/pais';
import { Raca } from '../../shared/dominio/raca';
import { TipoTransplante } from '../../shared/dominio/tipoTransplante';
import { UF } from '../../shared/dominio/uf';
import { ComponenteRecurso } from '../../shared/enums/componente.recurso';
import { ErroMensagem } from '../../shared/erromensagem';
import { EventEmitterService } from '../../shared/event.emitter.service';
import { MessageBox } from '../../shared/modal/message.box';
import { BuscaPreliminar } from '../../shared/model/busca.preliminar';
import { PacienteUtil } from '../../shared/paciente.util';
import { PermissaoRotaComponente } from '../../shared/permissao.rota.componente';
import { BuscaPreliminarService } from '../../shared/service/busca.preliminar.service';
import { FormatterUtil } from '../../shared/util/formatter.util';
import { PacienteConstantes } from '../paciente.constantes';
import { PacienteService } from '../paciente.service';
import { RascunhoService } from '../rascunho.service';
import { Responsavel } from '../responsavel';
import { Paciente } from './../paciente';
import { AvaliacaoComponent } from './avaliacao/avaliacao.component';
import { ContatoPacienteComponent } from './contato/contato.paciente.component';
import { EnderecoContatoPaciente } from './contato/endereco/endereco.contato.paciente';
import { DadosPessoaisComponent } from './dadospessoais/dadospessoais.component';
import { Diagnostico } from './diagnostico/diagnostico';
import { DiagnosticoComponent } from './diagnostico/diagnostico.component';
import { Evolucao } from './evolucao/evolucao';
import { EvolucaoComponent } from './evolucao/evolucao.component';
import { ExameComponent } from './exame/exame.component';
import { Locus } from './exame/locus';
import { IdentificacaoComponent } from './identificacao/identificacao.component';

/**
 * Classe que representa o componente de cadastro do paciente
 * @author Bruno Sousa
 */
@Component({
    selector: 'cadastro',
    templateUrl: './cadastro.component.html'
})
export class CadastroComponent implements PermissaoRotaComponente, OnInit, OnDestroy {

    private _CONFIG_DRAFT_CHAVES: String[] = ["tempoSalvarRascunhoEmSegundos"];

    pacienteForm: FormGroup;
    private contatoEndereco: EnderecoContatoPaciente;
    mensagem: string = "";
    private _listaCampoMensagem: CampoMensagem[] = [];

    private _tempoSalvarDraft: number;

    private etapaAtual;

    mensagemSucesso: string;

    @ViewChild(IdentificacaoComponent)
    private identificacaoComponent: IdentificacaoComponent;

    @ViewChild(ContatoPacienteComponent)
    private contatoPacienteComponent: ContatoPacienteComponent;

    @ViewChild(DiagnosticoComponent)
    private diagnosticoComponent: DiagnosticoComponent;

    @ViewChild(EvolucaoComponent)
    private evolucaoComponent: EvolucaoComponent;

    @ViewChild(ExameComponent)
    private exameComponent: ExameComponent;

    @ViewChild(AvaliacaoComponent)
    private motivoComponent: AvaliacaoComponent;

    @ViewChild(DadosPessoaisComponent)
    private dadosPessoaisComponent: DadosPessoaisComponent;

    private _exibeBotaoPopularPaciente:boolean = false;

    @ViewChild('erroModal')
    private erroModal;

    @ViewChild('confirmacaoModal')
    private confirmacaoModal;


    mensagemErroModal: string;

    // Lista que guarda todos os forms e suas posições.
    // As posições indicam as etapas no qual o form se apresenta.
    private etapasForms: Map<Number, BaseForm<BaseEntidade>>;

    // Guarda a última imagem de paciente salvo como draft.
    private ultimoDraftSalvo:Paciente;

    // Texto, para as labels, internacionalizadas.
    existeRascunhoDisponivelLabel:string;
    naoLabel:string;
    simLabel:string;

    // Job disparado para salvar drafts
    private rascunhoJob: Job;


    /**
     * @param FormBuilder cria o formBuilder para enviar para os filhos que populam os GroupForms
     * @author Fillipe Queiroz
     */
    constructor(private _fb: FormBuilder,
        private dominioService: DominioService,
        private servicePaciente: PacienteService,
        private router: Router,
        private rascunhoService: RascunhoService,
        private translate: TranslateService,
        private activatedRouter: ActivatedRoute,
        private buscaPreliminarService: BuscaPreliminarService,
        private messageBox: MessageBox) {

        this.etapaAtual = PacienteConstantes.ETAPA_IDENTIFICACAO;
        this._exibeBotaoPopularPaciente = !environment.production;


        this.translate.get("botao").subscribe(res => {
            this.naoLabel = res["nao"];
            this.simLabel = res["sim"];
        });

        this.translate.get("draft").subscribe(res => {
            this.existeRascunhoDisponivelLabel = res["existe.rascunho.disponivel"];
        });
    }

    /**
     * Método que é executado ao iniciar para preencher o GroupForm com os dados basicos e de contato
     * @author Fillipe Queiroz
     */
    ngOnInit(): void {

         this.pacienteForm = this._fb.group({
                identificacaoGroup: this.identificacaoComponent.identificacaoForm,
                dadosContatoGroup: this.contatoPacienteComponent.contatoForm,
                diagnosticoGroup: this.diagnosticoComponent.diagnosticoForm,
                evolucaoGroup: this.evolucaoComponent.evolucaoForm,
                exameGroup: this.exameComponent.exameForm,
                motivoGroup: this.motivoComponent.motivoForm,
                dadosPessoaisGroup: this.dadosPessoaisComponent.dadosPessoaisForm
        });

        this.criarRelacaoEtapaForm();

        this.activatedRouter.params.subscribe(params => {
            let idBuscaPreliminar: number = params['idBuscaPreliminar'];

            if(idBuscaPreliminar){
                // Recupera a busca preliminar para preenchimento do cadastro.
                this.recuperarBuscaPreliminar(idBuscaPreliminar);
            }
            else {
                // ou recupera o rascunho associado ao usuário logado.
                this.recuperarDraftParaUsuarioLogado();
            }
        });
        //20/03/2019
        //foi desabilitada a gravação do draft a cada 10 segundos
        //this.configurarSalvamentoDraft();

        EventEmitterService.get('eventSalvarDraft').subscribe(():void => {
            this.forcarSalvarDraft();
        });
    }

    /**
     * Recupera o último draft salvo para o usuário logado.
     * Caso exista, deve perguntar ao usuário se o mesmo
     * deseja continuar o cadastro. Caso não, ele deverá ser descartado.
     */
    private recuperarDraftParaUsuarioLogado():void{
        this.rascunhoService.recuperar()
            .then(result => {
                if(!EntidadeUtil.isEmpty(result)){
                    this.ultimoDraftSalvo = result;
                    if (result.raca) {
                        this.ultimoDraftSalvo.raca = new Raca(result.raca.id, result.raca.nome);
                    }
                    else {
                        this.ultimoDraftSalvo.raca = new Raca(null);
                    }
                    if (result.etnia) {
                        this.ultimoDraftSalvo.etnia = new Etnia(result.etnia.id, result.etnia.nome);
                    }
                    else {
                        this.ultimoDraftSalvo.etnia = new Etnia(null);
                    }
                    if (result.pais) {
                        this.ultimoDraftSalvo.pais = new Pais(result.pais.id, result.pais.nome);
                    }
                    else {
                        this.ultimoDraftSalvo.pais = new Pais(null);
                    }
                    if (result.naturalidade) {
                        this.ultimoDraftSalvo.naturalidade = new UF(result.naturalidade.sigla, result.naturalidade.nome);
                    }
                    else {
                        this.ultimoDraftSalvo.naturalidade = new UF(null);
                    }
                    if(result.locusMismatch){
                        result.locusMismatch.forEach(loc => {
                            let locus: Locus = new Locus().jsonToEntity(loc);
                            this.ultimoDraftSalvo.locusMismatch.push(locus);
                        });
                    }
                    else{
                        this.ultimoDraftSalvo.locusMismatch = null;
                    }
                    this.confirmacaoModal.show();
                }else{
                    this.verificarDraftAtual();
                }

            }, (erro: ErroMensagem) => {
                if (erro.listaCampoMensagem.length != 0) {
                    erro.listaCampoMensagem.forEach((campoMensagem: CampoMensagem) => {
                        this.mensagemErroModal += campoMensagem.mensagem;
                    });
                }
                else {
                    this.mensagemErroModal = erro.mensagem.toString();
                }
                this.erroModal.show();
            }
        );
    }

    /**
     * Recupera a busca preliminar e pré-preenche o cadastro de paciente
     * com os dados informados.
     *
     * @param idBuscaPreliminar ID da busca.
     */
    private recuperarBuscaPreliminar(idBuscaPreliminar: number): void{
        // Se o paciente está sendo cadastrado a partir
        this.buscaPreliminarService.obterBuscarPreliminar(idBuscaPreliminar)
            .then(resultado => {
                let buscaPreliminar: BuscaPreliminar = new BuscaPreliminar().jsonToEntity(resultado);
                let paciente: Paciente = new Paciente();
                paciente.nome = buscaPreliminar.nomePaciente;
                paciente.dataNascimento = buscaPreliminar.dataNascimento;
                paciente.abo = buscaPreliminar.abo;

                let evolucao: Evolucao = new Evolucao();
                evolucao.peso = buscaPreliminar.peso;
                paciente.evolucoes = [evolucao];

                this.identificacaoComponent.preencherFormulario(paciente);
                this.dadosPessoaisComponent.preencherFormulario(paciente);
                this.evolucaoComponent.ngOnInit();
                this.evolucaoComponent.preencherFormulario(paciente.evolucoes[0]);

                this.verificarDraftAtual();

            }, (erro: ErroMensagem) => {
                this.translate.get("mensagem.erro.requisicao").subscribe(res => {
                    this.messageBox.alert(res).show();
                });
            });
    }

    ngOnDestroy(): void {
        // TODO: Parar o shedule, quando a tela encerrar.
        if(this.rascunhoJob){
            this.rascunhoJob.cancel();
        }
    }

    /**
     * Cria a relação entre as etasas e os forms.
     */
    private criarRelacaoEtapaForm(): void{
        this.etapasForms = new Map<Number, BaseForm<Paciente>>();
        this.etapasForms.set(PacienteConstantes.ETAPA_IDENTIFICACAO, this.identificacaoComponent);
        this.etapasForms.set(PacienteConstantes.ETAPA_DADOS_PESSOAIS, this.dadosPessoaisComponent);
        this.etapasForms.set(PacienteConstantes.ETAPA_CONTATO, this.contatoPacienteComponent);
        this.etapasForms.set(PacienteConstantes.ETAPA_DIAGNOSTICO, this.diagnosticoComponent);
        this.etapasForms.set(PacienteConstantes.ETAPA_EVOLUCAO, this.evolucaoComponent);
        this.etapasForms.set(PacienteConstantes.ETAPA_EXAME, this.exameComponent);
        this.etapasForms.set(PacienteConstantes.ETAPA_AVALIACAO, this.motivoComponent);
    }

    /**
     * Método que envia os dados preenchidos para gravação
     * @author Fillipe Queiroz
     */
    onSubmit(pacienteForm: FormGroup, sucessoModal: any) {
        this.mensagem = '';
        this._listaCampoMensagem = [];
        var isValid = true;

        if (!this.validarUltimaEtapa()) {
            return false;
        }

        this.marcarCamposInvalidosParaTouched(this.pacienteForm);

        //FIXME retornar o valor da variável.
        //Remover o condicional e retornar o valor da variável sem inversão
        if (!isValid) {
            return false;
        }

        let paciente = this.popularPaciente(pacienteForm);

        this.servicePaciente.incluir(paciente).then(
            res => {

                this.mensagemSucesso = res['mensagem'];
                sucessoModal.show();
            },
            (error: ErroMensagem) => {
                this.identificacaoComponent.formErrors['numeroTel'] = '';
                this.contatoPacienteComponent.formErrors['numeroCep'] = '';
                this.mensagem = '';
                /* let formErrors = this.identificacaoComponent.formErrors;
                for (var field in formErrors) {
                    this.identificacaoComponent.formErrors[field] = '';
                } */

                let etapasInvalidas:number[] =
                    this.marcarCamposInvalidosRetornadosBackend(error.listaCampoMensagem);

                this.habilitarEtapa(etapasInvalidas[0]);
            });
    }

    popularPacienteParaTestar(){
        this.pacienteForm.get("identificacaoGroup.dataNascimento").setValue("31/03/1987");


        this.pacienteForm.get("identificacaoGroup.cpf").setValue("525.185.449-84");
        this.identificacaoComponent.realizarValidacoesDinamicasPorData();
        this.identificacaoComponent.realizarValidacoesDinamicasPorCpfECns();

        this.pacienteForm.get("identificacaoGroup.nome").setValue("Teste");
        this.pacienteForm.get("identificacaoGroup.nomeMae").setValue("Teste Nome Mãe");
        this.pacienteForm.get("dadosPessoaisGroup.sexo").setValue("F");
        this.pacienteForm.get("dadosPessoaisGroup.abo").setValue("B-");
        this.pacienteForm.get("dadosPessoaisGroup.raca.id").setValue("1");
        this.pacienteForm.get("dadosPessoaisGroup.etnia.id").setValue("1");
        this.pacienteForm.get("dadosPessoaisGroup.pais.id").setValue("1");
        this.pacienteForm.get("dadosPessoaisGroup.uf.sigla").setValue("RJ");
        this.pacienteForm.get("dadosContatoGroup.endereco.cep").setValue("21060-610");


        //this.pacienteForm.get("dadosContatoGroup.endereco.tipoLogradouro").setValue("RUA");
        //this.pacienteForm.get("dadosContatoGroup.endereco.nomeLogradouro").setValue("JOANA FONTOURA");
        //this.pacienteForm.get("dadosContatoGroup.endereco.bairro").setValue("BONSUCESSO");
        //this.pacienteForm.get("dadosContatoGroup.endereco.numero").setValue("76");
        //this.pacienteForm.get("dadosContatoGroup.endereco.municipio").setValue("RIO DE JANEIRO");
        //this.pacienteForm.get("dadosContatoGroup.endereco.uf").setValue("RJ");

        this.pacienteForm.get("dadosContatoGroup.telefone.listaTelefone.0.codArea").setValue("21");
        this.pacienteForm.get("dadosContatoGroup.telefone.listaTelefone.0.numero").setValue("21574600");
        this.pacienteForm.get("dadosContatoGroup.telefone.listaTelefone.0.nome").setValue("teste");
        this.pacienteForm.get("dadosContatoGroup.telefone.listaTelefone.0.principal").setValue(true);

        this.pacienteForm.get("diagnosticoGroup.dataDiagnostico").setValue("20/02/2000");
        this.pacienteForm.get("evolucaoGroup.peso").setValue(new Number("80").valueOf());
        this.pacienteForm.get("evolucaoGroup.altura").setValue(new Number("1.80").valueOf());
        this.pacienteForm.get("evolucaoGroup.resultadoCMV").setValue("true");
        this.pacienteForm.get("evolucaoGroup.tipoTransplanteAnterior").setValue("1");
        this.pacienteForm.get("evolucaoGroup.tratamentoAnterior").setValue("tratamento anterior");
        this.pacienteForm.get("evolucaoGroup.tratamentoAtual").setValue("tratamento atual");
        this.pacienteForm.get("evolucaoGroup.condicaoAtual").setValue("condição atual");
        this.pacienteForm.get("evolucaoGroup.evolucao").setValue("1");
        // this.pacienteForm.get("diagnosticoGroup.cidSelecionada").setValue("1");
        this.exameComponent.exameForm.get("laboratorio").setValue("1");
        this.exameComponent.exameForm.get("laboratorioParticular").setValue(false);
        this.exameComponent.exameForm.get("dataColetaAmostra").setValue("20/02/2000");
        this.exameComponent.exameForm.get("dataExame").setValue("20/02/2000");

        this.exameComponent.listMetodologiasForm.get('0.checked').setValue(true);

        this.exameComponent.exameForm.get("listLocus.0.alelo1").setValue("02:AVJP");
        this.exameComponent.exameForm.get("listLocus.0.alelo2").setValue("02:AVJP");
        this.exameComponent.exameForm.get("listLocus.1.alelo1").setValue("49:AZRJ");
        this.exameComponent.exameForm.get("listLocus.1.alelo2").setValue("49:AZRJ");
        this.exameComponent.exameForm.get("listLocus.2.alelo1").setValue("14:BKJZ");
        this.exameComponent.exameForm.get("listLocus.2.alelo2").setValue("14:BKJZ");
    }

    /**
     * Método responsável por preencher a classe de paciente
     *
     * @private
     * @param {FormGroup} pacienteForm
     * @returns {Paciente}
     *
     * @memberOf CadastroComponent
     */
    private popularPaciente(pacienteForm: FormGroup): Paciente {

        let paciente: Paciente = new Paciente();

        paciente.cpf = PacienteUtil.obterCPFSemMascara(pacienteForm.get("identificacaoGroup.cpf").value) || null;

        paciente.cns = pacienteForm.get("identificacaoGroup.cns").value || null;

        let _dataNascimento = pacienteForm.get("identificacaoGroup.dataNascimento").value;
        paciente.dataNascimento = PacienteUtil.obterDataSemMascara( _dataNascimento ) || null;

        paciente.nome = pacienteForm.get("identificacaoGroup.nome").value || null;
        paciente.nomeMae = pacienteForm.get("identificacaoGroup.nomeMae").value || null;
        paciente.maeDesconhecida = pacienteForm.get('identificacaoGroup.maeDesconhecida').value || false;

        let cpfResponsavel: string = PacienteUtil
            .obterCPFSemMascara(pacienteForm
                .get("identificacaoGroup.responsavel.cpf").value) || null;

        let nomeResponsavel: string = pacienteForm.get("identificacaoGroup.responsavel.nome").value || null;
        let parentesco: string = pacienteForm.get("identificacaoGroup.responsavel.parentesco").value || null;
        if (cpfResponsavel || nomeResponsavel || parentesco) {
            paciente.responsavel = new Responsavel(cpfResponsavel, nomeResponsavel, parentesco);
        }
        else {
            paciente.responsavel = null;
        }

        paciente.sexo = pacienteForm.get("dadosPessoaisGroup.sexo").value || null;
        paciente.abo = pacienteForm.get("dadosPessoaisGroup.abo").value || null;
        let racaId: number = pacienteForm.get("dadosPessoaisGroup.raca.id").value || null;
        if (racaId) {
            let raca: Raca = this.dadosPessoaisComponent.obterRacas().find(obj => obj.id == racaId);
            paciente.raca = new Raca(racaId, raca.nome);
        }
        else {
            paciente.raca = null;
        }

        let etniaId: number = pacienteForm.get("dadosPessoaisGroup.etnia.id").value || null;
        if (etniaId) {
            if (paciente.raca && PacienteConstantes.RACA_INDIGENA_COM_ETNIAS == paciente.raca.nome) {
                paciente.etnia = new Etnia(etniaId);
            }
            else
                paciente.etnia = null;
        }
        else {
            paciente.etnia = null;
        }

        let paisId: number = pacienteForm.get("dadosPessoaisGroup.pais.id").value || null;
        if (paisId) {
            let pais: Pais = this.dadosPessoaisComponent.obterPaises().find(obj => obj.id == paisId);
            paciente.pais = new Pais(paisId, pais.nome);
        }

        let sigla: string = pacienteForm.get("dadosPessoaisGroup.uf.sigla").value || null;
        if (sigla && paciente.pais && paciente.pais.nome == PacienteConstantes.PAIS_BRASIL) {
            paciente.naturalidade = new UF(sigla);
        }
        else {
            paciente.naturalidade = null;
        }

        paciente.email = this.contatoPacienteComponent.obterEmail();

        paciente.enderecosContato = [this.contatoPacienteComponent.obterEndereco()];

        paciente.contatosTelefonicos = this.contatoPacienteComponent.listarTelefonesContato();

        paciente.diagnostico = new Diagnostico(null);

        let _dataDiagnostico = pacienteForm.get("diagnosticoGroup.dataDiagnostico").value;
        paciente.diagnostico.dataDiagnostico = PacienteUtil.obterDataSemMascara(_dataDiagnostico) || null;

        let cidId = pacienteForm.get("diagnosticoGroup.cidSelecionada").value || null;

        if (cidId) {
            paciente.diagnostico.cid =
                this.diagnosticoComponent.ultimaCidSelecionada;
        } else {
            paciente.diagnostico.cid = null;
        }

        /* ####### EVOLUÇÃO ######### */

        let evolucoes: Evolucao[] = [];
        let evolucao: Evolucao = new Evolucao();

        evolucao.altura = FormatterUtil.obterAlturaSemMascara( pacienteForm.get("evolucaoGroup.altura").value ) || null;
        let idMotivo = pacienteForm.get("evolucaoGroup.motivo").value || null;
        if (idMotivo) {
            evolucao.motivo = new Motivo(idMotivo);
        }
        else {
            evolucao.motivo = null;
        }
        evolucao.peso = PacienteUtil.arredondar( pacienteForm.get("evolucaoGroup.peso").value, 1 ) || null;
        evolucao.cmv = pacienteForm.get("evolucaoGroup.resultadoCMV").value || null;
        evolucao.tratamentoAnterior =  pacienteForm.get("evolucaoGroup.tratamentoAnterior").value || null;
        evolucao.tratamentoAtual = pacienteForm.get("evolucaoGroup.tratamentoAtual").value || null;
        evolucao.condicaoAtual = pacienteForm.get("evolucaoGroup.condicaoAtual").value || null;
        evolucao.dataUltimoTransplante = PacienteUtil.obterDataSemMascara(pacienteForm.get("evolucaoGroup.dataUltimoTransplante").value) || null;
        evolucao.exameAnticorpo = Boolean(pacienteForm.get("evolucaoGroup.exameAnticorpo").value == '0' ? 0 : 1);
        if(evolucao.exameAnticorpo){
          evolucao.dataExameAnticorpo = PacienteUtil.obterDataSemMascara(pacienteForm.get('evolucaoGroup.dataExameAnticorpo').value) || null ;
          evolucao.resultadoExameAnticorpo = pacienteForm.get('evolucaoGroup.resultadoExameAnticorpo').value;
        }

        let checkTransplanteAnterior = pacienteForm.get("evolucaoGroup.checkTransplanteAnterior") as FormArray;
        if (checkTransplanteAnterior && checkTransplanteAnterior.controls) {
            checkTransplanteAnterior.controls.forEach(c =>{
                if(c.get('check').value != null && c.get('check').value == true){
                  let tipoTransplante = new TipoTransplante();
                  tipoTransplante.id = c.get('id').value;
                  evolucao.tiposTransplante.push(tipoTransplante);
                }
            });
        }

        let estagioDoenca = pacienteForm.get("evolucaoGroup.estagioDoenca");
        let idEstagio;

        if(estagioDoenca) {
            idEstagio = estagioDoenca.value || null
        }

        if (idEstagio) {
            evolucao.estagioDoenca = new EstagioDoenca(idEstagio);
        }
        else {
            evolucao.estagioDoenca = null
        }

        let idCondicaoPaciente = pacienteForm.get("evolucaoGroup.evolucao").value;
        if (idCondicaoPaciente) {
            let condicaoPaciente: CondicaoPaciente = new CondicaoPaciente(idCondicaoPaciente);
            evolucao.condicaoPaciente = condicaoPaciente;
        }
        else {
            evolucao.condicaoPaciente = null;
        }
        if(this.evolucaoComponent.arquivosEvolucao.length > 0){
            evolucao.arquivosEvolucao = this.evolucaoComponent.arquivosEvolucao;
        }

        if (!evolucao.altura && !evolucao.motivo && !evolucao.peso && !evolucao.cmv
          && !evolucao.tratamentoAnterior && !evolucao.tratamentoAtual && !evolucao.condicaoAtual
          && !evolucao.estagioDoenca) {
          evolucao = null;
        }

        evolucoes.push(evolucao);
        paciente.evolucoes = evolucoes;

        /* ######## MOTIVO ########## */

        paciente.aceiteMismatch = pacienteForm.get('motivoGroup.aceiteMismatch').value;
        let idCentroAvaliador = pacienteForm.get('motivoGroup.centroAvaliador').value;
        if (idCentroAvaliador) {
            paciente.centroAvaliador = new CentroTransplante();
            paciente.centroAvaliador.id = pacienteForm.get('motivoGroup.centroAvaliador').value;
        }
        else {
            paciente.centroAvaliador = null;
        }

        paciente.exames.push(this.exameComponent.exame);

        paciente.locusMismatch = this.motivoComponent.obterListaLocusMismatch();
        return paciente;
    }

    /**
     * Altera a mensagem na tela de cadastro, é chamada pelo EventEmitter dos componentes filhos
     * @author Fillipe Queiroz
     */
    alterarMensagem(event) {
        this.mensagem = event;
    }

    /**
     * Método para alterar a lista de erros chamada por componentes filhos
     * @author Fillipe Queiroz
     * @param {any} event
     *
     * @memberof CadastroComponent
     */
    alterarMensagemErro(event) {
        let mensagem: CampoMensagem = new CampoMensagem();
        mensagem.campo = 'erro';
        mensagem.mensagem = event;
        this._listaCampoMensagem.push(mensagem);
    }

    /**
     * Altera a mensagem
     * @author Rafael Pizão
     * @param pacienteJaExiste
     */
    public verificarSeExisteDuplicidade(pacienteJaExiste: String) {
        if (pacienteJaExiste == "true") {
            this.mensagem = 'Paciente já cadastrado no sistema.';
        } else {
            this.mensagem = '';
        }
    }

    /**
     * Método para returnar o pacienteForm
     * @returns FormGroup - grupo do formulário de paciente
     * @author Fillipe Queiroz
     *
     */
    public getPacienteForm(): FormGroup {
        return this.pacienteForm;
    }

    /**
     * Método para limpar a listagem de erro no topo da tela
     */
    public limparMensagemErro() {
        this._listaCampoMensagem = [];
    }

    /**
     * Método para esconder as etapas que não são as atuais
     *
     * @param {any} etapaParaTestar
     * @returns {boolean}
     * @memberof CadastroComponent
     */
    public esconderEtapa(etapaParaTestar): boolean {
        return this.etapaAtual != etapaParaTestar;
    }

    /**
     * Habilita botão de salvar se for a última etapa
     *
     * @returns {boolean}
     * @memberof CadastroComponent
     */
    public habilitarSalvarSeEhUltimaEtapa(): boolean{
        return PacienteConstantes.ETAPA_AVALIACAO != this.etapaAtual;
    }

    /**
     * Método para pegar estido do link do breadCrumb
     *
     * @param {any} etapaAtual
     * @returns {string}
     * @memberof CadastroComponent
     */
    public getEstiloDoLinkDaMigalhaDePao(etapaAtual): string {
        if (this.etapaAtual > etapaAtual)  {
            return  "ativo";
        }
        else if (this.etapaAtual == etapaAtual)  {
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
     * @memberof CadastroComponent
     */
    public habilitarEtapa(etapaParaHabilitar: number): void {
        if (etapaParaHabilitar < this.etapaAtual) {
            this.etapaAtual = etapaParaHabilitar;
        }
    }

    /**
     * Avança para a próxima etapa, somente se o formulário atual
     * tiver preenchido corretamente.
     *
     * @memberof CadastroComponent
     */
    public habilitarProximaEtapa():void{
        let form:BaseForm<Paciente> = this.etapasForms.get(this.etapaAtual);
        if(form.validateForm()){
            this.etapaAtual++;
            this.verificarDraftAtual();
        }
    }

    private validarUltimaEtapa(): boolean {
        let form:BaseForm<Paciente> = this.etapasForms.get(this.etapaAtual);
        return form.validateForm();
    }

    /**
     * Método para habilitar a etapa anterior
     *
     * @memberof CadastroComponent
     */
    public habilitarEtapaAnterior():void{
        this.etapaAtual--;
        this.verificarDraftAtual();
    }

    /**
     * Método para esconder botão anterior se etapa for 1
     *
     * @returns {boolean}
     * @memberof CadastroComponent
     */
    public escondeSeEtapa1():boolean{
        return this.etapaAtual == PacienteConstantes.ETAPA_IDENTIFICACAO;
    }

    /**
     * Método que esconde botão proximo se for a última etapa
     *
     * @returns {boolean}
     * @memberof CadastroComponent
     */
    public escondeSeUltimaEtapa():boolean{
        return this.etapaAtual == PacienteConstantes.ETAPA_AVALIACAO;
    }

    public fecharSucessoModal(sucessoModal: any) {
        sucessoModal.hide();

        this.router.navigateByUrl("/home");
    }

    private marcarCamposInvalidosParaTouched(form: FormGroup) {
        for (const field in form.controls) {
            let control = form.get(field);
            if (control && control instanceof FormControl) {
                if (control.invalid && control.untouched) {
                    control.markAsTouched();
                }
            } else if (control && control instanceof FormGroup) {
                this.marcarCamposInvalidosParaTouched(control)
            } else if (control && control instanceof FormArray) {
                control.controls.forEach(element => {
                    if(element instanceof FormGroup){
                        this.marcarCamposInvalidosParaTouched(element);
                    }
                });

            }
        }
    }

    /**
     * Marca os campos de todas as etapas com erro, identificadas de acordo
     * com a mensagem retornada do backend
     *
     * @param mensagens
     */
    private marcarCamposInvalidosRetornadosBackend(mensagens:CampoMensagem[]): number[]{
        let etapasInvalidas:number[] = [];

        mensagens.forEach(mensagemErro => {
            this.etapasForms.forEach((component:BaseForm<Paciente>, etapa:number) => {
                let campo:string = mensagemErro.campo.indexOf(".") != -1?mensagemErro.campo.split(".")[0]:mensagemErro.campo;
                let form:FormGroup = component.form();
                if(component.existsField(campo)){
                    const control = component.form().get(campo);
                    if (control && control instanceof FormGroup) {
                       form = control;
                       let pai: string = campo;
                       campo = mensagemErro.campo.split(".")[1];
                       component.formErrors[pai][campo] = mensagemErro.mensagem;
                    }
                    else {
                       component.formErrors[mensagemErro.campo] = mensagemErro.mensagem;
                    }

                    component.markAsInvalid(component.getField(form, campo));
                    etapasInvalidas.push(etapa);
                }
            });
        });

        return etapasInvalidas;
    }

    fecharErroModal() {
        this.erroModal.hide();
    }

        /**
     * Agenda o job para salvar drafts do paciente a cada N segundos,
     * conforme configurado na aplicação.
     *
     * @param valorEmSegundos o tempo, em segundos, para o timeout do job.
     */
    public agendarDraft(valorEmSegundos:number):void{
        if (window.location.href.indexOf(":49157") == -1) {
            this.rascunhoJob =
                this.servicePaciente.agendarJob(new IntervaloTempo(valorEmSegundos), ():void => {
                    this.verificarDraftAtual();
                });
        }
    }

    private verificarDraftAtual(): void{
        let pacienteAtual = this.popularPaciente(this.pacienteForm);
        if(!EntidadeUtil.compareEquals(this.ultimoDraftSalvo, pacienteAtual) && this.identificacaoComponent.verificarPreenchimento()){
            this.salvarDraft(pacienteAtual);
        }
    }

    /**
     * Preenche o paciente com os valores da tela e envia um draft do mesmo
     * para salvar no back-end.
     *
     */
    public salvarDraft(pacienteAtual:Paciente):void{
        this.ultimoDraftSalvo = pacienteAtual;
        this.rascunhoService.salvar(pacienteAtual);
    }

    /**
     * Força o salvamento do draft com a imagem atual retirada do formulário.
     *
     */
    private forcarSalvarDraft():void{
        let pacienteAtual = this.popularPaciente(this.pacienteForm);
        this.salvarDraft(pacienteAtual);
    }

    /**
     * Fecha o modal que foi informado no parametro.
     *
     * @param modal
     */
    public fecharModal(modal:any):void{
        modal.hide();

    }

    /**
     * Método chamado quando o usuário aceita continuar
     * a inclusão do paciente salvo no draft.
     *
     */
    public aceitarDraft(){
        this.preencherFormularioDaTela(this.ultimoDraftSalvo);
        this.verificarDraftAtual();
    }

    /**
     * Método chamado quando o usuário recusar continuar
     * a inclusão do paciente salvo no draft.
     *
     */
    public recusarDraft(){
        this.ultimoDraftSalvo = null;
        this.rascunhoService.excluir();
        this.verificarDraftAtual();
    }

    /**
     * Configura o salvamento do draft de acordo com o parametro de tempo
     * estabelecido no banco.
     */
    private configurarSalvamentoDraft():void{
        // Carrega a configuração necessária para o upload do arquivo.
        this.dominioService.listarConfiguracoes(this._CONFIG_DRAFT_CHAVES)
            .then(configuracoes => {
                let configuracao:Configuracao = configuracoes[0];
                this._tempoSalvarDraft = Number(configuracao.valor);
                // Agendar schedule para o tempo especificado.
                this.agendarDraft(this._tempoSalvarDraft);
            }, (erro: ErroMensagem) => {
                if (erro.listaCampoMensagem.length != 0) {
                    erro.listaCampoMensagem.forEach((campoMensagem: CampoMensagem) => {
                        this.mensagemErroModal += campoMensagem.mensagem;
                    });
                }
                else {
                    this.mensagemErroModal = erro.mensagem.toString();
                }
                this.erroModal.show();
            }
        );
    }

    /**
     * Preenche o formulário com o rascunho salvo anteriormente.
     *
     * @param paciente paciente salvo no rascunho
     */
    private preencherFormularioDaTela(paciente:Paciente):void{
        this.identificacaoComponent.preencherFormulario(paciente);
        this.identificacaoComponent.calcularIdadeEmit();

        this.dadosPessoaisComponent.preencherFormulario(paciente);
        this.contatoPacienteComponent.preencherFormulario(paciente);
        this.diagnosticoComponent.preencherFormulario(paciente.diagnostico);
        if(paciente.evolucoes != null && paciente.evolucoes.length > 0){
            this.evolucaoComponent.ngOnInit();
            this.evolucaoComponent.preencherFormulario(paciente.evolucoes[0]);
        }
        if(paciente.exames != null && paciente.exames.length > 0){
            this.exameComponent.preencherFormulario(paciente.exames[0]);
        }
        this.motivoComponent.preencherFormulario(paciente);
    }

    nomeComponente(): string {
        return ComponenteRecurso.Componente[ComponenteRecurso.Componente.CadastroComponent];
    }

    public get exibeBotaoPopularPaciente(): boolean{
        return this._exibeBotaoPopularPaciente;
    }

	public get listaCampoMensagem(): CampoMensagem[]  {
		return this._listaCampoMensagem;
    }

    enviarIdadeParaDiagnostico(event){
        this.diagnosticoComponent.idadePaciente = event;
    }

}
