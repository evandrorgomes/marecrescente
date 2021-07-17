import { ValidateData, ValidateDataMaiorOuIgualHoje, ValidateDataMenorQueHoje } from '../../../../validators/data.validator';
import { TarefaService } from '../../../../shared/tarefa.service';
import { DateTypeFormats } from '../../../../shared/util/date/date.type.formats';
import { PedidoColeta } from '../../coleta/pedido.coleta';
import { AutenticacaoService } from '../../../../shared/autenticacao/autenticacao.service';
import { FonteCelula } from '../../../../shared/dominio/fonte.celula';
import { ActivatedRoute } from '@angular/router';
import { ErroMensagem } from '../../../../shared/erromensagem';
import { DestinoColetaService } from '../destino.coleta.service';
import { RecebimentoColetaService } from '../recebimento.coleta.service';
import { ArquivoVoucherLogisticaService } from '../../../../shared/service/arquivo.voucher.logistica.service';
import { WorkupService } from '../../workup/workup.service';
import { DominioService } from '../../../../shared/dominio/dominio.service';
import { TranslateService } from '@ngx-translate/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MensagemModalComponente } from '../../../../shared/modal/mensagem.modal.component';
import { HeaderPacienteComponent } from '../../../../paciente/consulta/identificacao/header.paciente.component';
import { BaseForm } from '../../../../shared/base.form';
import { Component, OnInit, ViewChild } from '@angular/core';
import { constructor } from 'events';
import { DataUtil } from '../../../../shared/util/data.util';
import { PacienteUtil } from '../../../../shared/paciente.util';
import { Router } from '@angular/router';
import { HistoricoNavegacao } from '../../../../shared/historico.navegacao';
import { FontesCelulas } from 'app/shared/enums/fontes.celulas';
import { RecebimentoColeta } from 'app/shared/dominio/recebimento.coleta';
import { DestinoColeta } from 'app/shared/dominio/destino.coleta';


@Component({
    selector: "recebimento-coleta-cadastro",
    templateUrl: './recebimento.coleta.cadastro.component.html'
})
export class RecebimentoColetaCadastroComponent extends BaseForm<RecebimentoColeta> implements OnInit {
    private pedidoColetaId: number;

    @ViewChild('headerPaciente')
    private headerPaciente: HeaderPacienteComponent;

    @ViewChild('modalMsg')
    private modalMensagem: MensagemModalComponente;

    @ViewChild("modalErro")
    public modalErro;

    private _fonteCelulas: FonteCelula;

    private _formularioRecebimento: FormGroup;

    private _destinosColeta: DestinoColeta[];

    private _idRecebimentoColeta: Number;

    private _recebimentoColeta: RecebimentoColeta;

    private _dmr: number;

    private _idRegistro: string;

    private _labelData: string;

    private _justificativa: boolean;
    protected translate: TranslateService;
    public desabilitarCampos:boolean = false;

    private _tarefaId:number;
    private _infusao:boolean;
    private _descarte:boolean;
    private _congelamento:boolean;
    public mask: Array<string | RegExp>
    public data: Array<string | RegExp>

    constructor(translate: TranslateService, private tarefaService:TarefaService, private dominioService: DominioService,
        private recebimentoColetaService: RecebimentoColetaService, private activatedRouter: ActivatedRoute,
        private fb: FormBuilder, private destinoColetaService: DestinoColetaService,
        private router: Router, private autenticacao: AutenticacaoService) {
        super();
        this.translate = translate;

        this.mask = [
            /[0-9]/,/[0-9]/,/[0-9]/,
             '.', /[0-9]/,/[0-9]/,/[0-9]/,
              '.', /[0-9]/,/[0-9]/,/[0-9]/,
               '-', /[0-9]/,/[0-9]/
            ];

        this.data = [/[0-9]/,/[0-9]/, '\/', /[0-9]/,/[0-9]/, '\/', /[0-9]/,/[0-9]/,/[0-9]/,/[0-9]/];
    }

    public form(): FormGroup {
        return this.formularioRecebimento;
    }
    public preencherFormulario(entidade: RecebimentoColeta): void {
        throw new Error("Method not implemented.");
    }

    ngOnInit(): void {
        this.carregarRecebimento();
        this.buildForm();
        this.translate.get("workup.recebimentoColeta").subscribe(res => {
            this._formLabels = res;
            super.criarMensagemValidacaoPorFormGroup(this._formularioRecebimento);
            this.setMensagensErro(this._formularioRecebimento);
        });
    }

    carregarRecebimento(){
        this.activatedRouter.queryParams.subscribe(params => {
            if (params && params["tarefaId"]) {
                this._tarefaId = Number(params["tarefaId"]);
            }
            else {
                this.translate.get("mensagem.erro.requisicao").subscribe(res => {
                    this.modalMensagem.mensagem = res;
                    this.modalMensagem.abrirModal();
                });
            }
        })

        this.activatedRouter.params.subscribe(params => {
            this.recebimentoColetaService.obterRecebimentoPorId(Number(params['recebimentoId']).valueOf())
                .then(res => {
                    this._recebimentoColeta = new RecebimentoColeta();
                    this._recebimentoColeta = Object.assign(this.recebimentoColeta, res);
                    this.dmr = res.pedidoColeta.pedidoWorkup.solicitacao.doador.dmr;
                    this.idRegistro = res.pedidoColeta.pedidoWorkup.solicitacao.doador.idRegistro;
                    this.pedidoColetaId = res.pedidoColeta.id;

                    Promise.resolve(this.headerPaciente).then(() => {
                        this.headerPaciente.popularCabecalhoIdentificacaoPorPaciente(res.pedidoColeta.pedidoWorkup.solicitacao.paciente.rmr);
                    });
                    if(this.recebimentoColeta.recebeuColeta){
                        let total:number  = this.recebimentoColeta.totalTotalCd34? this.recebimentoColeta.totalTotalCd34: this.recebimentoColeta.totalTotalTcn;
                        this.formularioRecebimento.get("recebeu").setValue("1");
                        this.formularioRecebimento.get("recebeu").disable();
                        this.formularioRecebimento.get("total").setValue(total)
                        this.formularioRecebimento.get("destino").setValue(this.recebimentoColeta.destinoColeta.id + "");
                        if(this.recebimentoColeta.destinoColeta.id == 1){
                            this.formularioRecebimento.get("data").setValue(DataUtil.toDateFormat(this.recebimentoColeta.dataInfusao, DateTypeFormats.DATE_ONLY));
                            this.destinoInfusao();
                        }else if(this.recebimentoColeta.destinoColeta.id == 2){
                            this.formularioRecebimento.get("data").setValue(DataUtil.toDateFormat(this.recebimentoColeta.dataPrevistaInfusao, DateTypeFormats.DATE_ONLY));
                            this.formularioRecebimento.get("justificativa").setValue(this.recebimentoColeta.justificativaCongelamento);
                            this.destinoCongelamento();
                        }else if(this.recebimentoColeta.destinoColeta.id == 3){
                            this.formularioRecebimento.get("data").setValue(DataUtil.toDateFormat(this.recebimentoColeta.dataDescarte, DateTypeFormats.DATE_ONLY));
                            this.formularioRecebimento.get("justificativa").setValue(this.recebimentoColeta.justificativaDescarte);
                            this.destinoDescarte();
                        }
                        this.translate.get("workup.recebimentoColeta").subscribe(res => {
                            this._formLabels = res;
                            super.criarMensagemValidacaoPorFormGroup(this._formularioRecebimento);
                            this.setMensagensErro(this._formularioRecebimento);
                        });
                    }

                },
                (error: ErroMensagem) => {
                    this.exibirMensagemErro(error);
                });
        });


    }


    private exibirMensagemErro(error: ErroMensagem) {
        error.listaCampoMensagem.forEach(obj => {
            this.modalErro.mensagem = obj.mensagem;
        })
        this.modalErro.abrirModal();
    }


    /**
    * Constrói os FormControl dos campos obrigatorios
    * @param FormBuilder para preencher os campos com suas respectivas validações
    * @author Filipe Paes
    */
    buildForm() {
        this._formularioRecebimento = this.fb.group({
            'recebeu': [null, Validators.required],
            'total': [null, Validators.required],
            'quantidade': [null],
            'destino': [null, Validators.required],
            'data': [null],
            'justificativa': [null]
        });
    }

    public destinoInfusao() {
        this.resetFieldRequired(this.formularioRecebimento,'justificativa');
        this.formularioRecebimento.get("data").setValidators(Validators.compose([Validators.required,ValidateDataMenorQueHoje]));
        this.formularioRecebimento.get("data").updateValueAndValidity();
        this.clearMensagensErro(this.formularioRecebimento, null, "data");
        this.translate.get("workup.recebimentoColeta.dataInfusao").subscribe(res => {
            this._labelData = res;
        });
        this.setInfusao();
        this._justificativa = false;
    }

    public destinoCongelamento() {
        this.setFieldRequired(this.formularioRecebimento,'justificativa');
        this.formularioRecebimento.get("data").setValidators(Validators.compose([Validators.required,ValidateDataMaiorOuIgualHoje]));
        this.formularioRecebimento.get("data").updateValueAndValidity();
        this.clearMensagensErro(this.formularioRecebimento, null, "data");
        this.translate.get("workup.recebimentoColeta.dataPrevista").subscribe(res => {
            this._labelData = res;
        });
        this.setCongelamento();
        this._justificativa = true;
    }

    public destinoDescarte() {
        this.setFieldRequired(this.formularioRecebimento,'justificativa');
        this.formularioRecebimento.get("data").setValidators(Validators.compose([Validators.required,ValidateDataMenorQueHoje]));
        this.formularioRecebimento.get("data").updateValueAndValidity();
        this.clearMensagensErro(this.formularioRecebimento, null, "data");
        this.translate.get("workup.recebimentoColeta.dataDescarte").subscribe(res => {
            this._labelData = res;
        });
        this.setDescarte();
        this._justificativa = true;
    }

    /**
     * Retorna o nome do componente
     * @returns string
     */
    nomeComponente(): string {
        return "RecebimentoColetaCadastroComponent";
    }


    public get formularioRecebimento(): FormGroup {
        return this._formularioRecebimento;
    }

    public set formularioRecebimento(value: FormGroup) {
        this._formularioRecebimento = value;
    }

    public get fonteCelulas(): FonteCelula {
        return this._fonteCelulas;
    }

    public set fonteCelulas(value: FonteCelula) {
        this._fonteCelulas = value;
    }


    /**
     * Getter recebimentoColeta
     * @return {RecebimentoColeta}
     */
    public get recebimentoColeta(): RecebimentoColeta {
        return this._recebimentoColeta;
    }

    /**
     * Setter recebimentoColeta
     * @param {RecebimentoColeta} value
     */
    public set recebimentoColeta(value: RecebimentoColeta) {
        this._recebimentoColeta = value;
    }


    /**
     * Getter dmr
     * @return {number}
     */
    public get dmr(): number {
        return this._dmr;
    }

    /**
     * Setter dmr
     * @param {number} value
     */
    public set dmr(value: number) {
        this._dmr = value;
    }


    /**
     * Getter labelData
     * @return {string}
     */
    public get labelData(): string {
        return this._labelData;
    }

    /**
     * Setter labelData
     * @param {string} value
     */
    public set labelData(value: string) {
        this._labelData = value;
    }

    /**
     * Getter justificativa
     * @return {boolean}
     */
    public get justificativa(): boolean {
        return this._justificativa;
    }

    /**
     * Setter justificativa
     * @param {boolean} value
     */
    public set justificativa(value: boolean) {
        this._justificativa = value;
    }

    /*
     Método que habilita ou desabilita os campos da tela.
     * Se recebeu habilita todos os campos.
    */
    public radioButtonRecebeuColeta() {
        this.desabilitarCampos = false;
    }

    /*
     * Método que habilita ou desabilita os campos da tela.
     * Se não recebeu desabilita todos os campos.
    */
    public radioButtonNaoRecebeuColeta() {
        this.desabilitarCampos = true;
    }

    /**
     * salva o recebimento da coleta.
     */
    public salvarRecebimentoColeta(){
        if(this.validateForm()){
            if(this._infusao){
                this.recebimentoColeta.dataInfusao = PacienteUtil.obterDataSemMascara(this.formularioRecebimento.get('data').value);
            }

            if(this._congelamento){
                this.recebimentoColeta.dataPrevistaInfusao = PacienteUtil.obterDataSemMascara(this.formularioRecebimento.get('data').value);
                this.recebimentoColeta.justificativaCongelamento = this.formularioRecebimento.get('justificativa').value;
            }

            if(this._descarte){
                this.recebimentoColeta.dataDescarte = PacienteUtil.obterDataSemMascara(this.formularioRecebimento.get('data').value);
                this.recebimentoColeta.justificativaDescarte = this.formularioRecebimento.get('justificativa').value;
            }
            this.recebimentoColeta.fonteCelula = new FonteCelula();
            this.recebimentoColeta.fonteCelula.id = 1;
            if(this.recebimentoColeta.fonteCelula.id = 1){
                this.recebimentoColeta.totalTotalTcn =  this.formularioRecebimento.get('total').value;
            }
            else if(this.recebimentoColeta.fonteCelula.id = 2){
                this.recebimentoColeta.totalTotalCd34 =  this.formularioRecebimento.get('total').value;
            }
            this.recebimentoColeta.destinoColeta = new DestinoColeta();
            this.recebimentoColeta.destinoColeta.id = this.formularioRecebimento.get('destino').value;
            this.recebimentoColeta.recebeuColeta = this.formularioRecebimento.get('recebeu').value == 1?true:false;
            this.recebimentoColeta.pedidoColeta = new PedidoColeta();
            this.recebimentoColeta.pedidoColeta.id = this.pedidoColetaId;
            this.recebimentoColetaService.atualizarRecebimentoColeta(this.recebimentoColeta).then(res => {
                this.modalMensagem.mensagem = res.mensagem;
                this.modalMensagem.abrirModal();
            },
            (error: ErroMensagem) => {
                this.exibirMensagemErro(error);
            });
        }
    }

    public voltar() {
        this.tarefaService.removerAtribuicaoTarefa(this.tarefaId).then(res => {
            this.router.navigateByUrl('/doadores/workup/recebimentocoleta');
        },
        (error: ErroMensagem) => {
            this.exibirMensagemErro(error);
        });
    }

    public voltarSemCancelarTarefa(){
        this.router.navigateByUrl('/doadores/workup/recebimentocoleta');
    }

    public validateForm():boolean{
        this.clearMensagensErro(this.formularioRecebimento);
        let valid: boolean = this.validateFields(this.formularioRecebimento);
        this.setMensagensErro(this.formularioRecebimento);
        return valid;
    }

    private setInfusao(){
        this._infusao = true;
        this._congelamento = false;
        this._descarte = false;
    }

    private setCongelamento(){
        this._infusao = false;
        this._congelamento = true;
        this._descarte = false;
    }

    private setDescarte(){
        this._infusao = false;
        this._congelamento = false;
        this._descarte = true;
    }


    /**
     * Getter tarefaId
     * @return {number}
     */
	public get tarefaId(): number {
		return this._tarefaId;
	}

    /**
     * Setter tarefaId
     * @param {number} value
     */
	public set tarefaId(value: number) {
		this._tarefaId = value;
	}

    /**
     * Getter idRegistro
     * @return {string}
     */
	public get idRegistro(): string {
		return this._idRegistro;
	}

    /**
     * Setter idRegistro
     * @param {string} value
     */
	public set idRegistro(value: string) {
		this._idRegistro = value;
	}

    public get ehFonteCelulaOpcaoMedula(): boolean {
        if(this.recebimentoColeta && this.recebimentoColeta.fonteCelula){
            return this.recebimentoColeta.fonteCelula.id === FontesCelulas.MEDULA_OSSEA.id;
        }
        return false;
    }

    public get ehFonteCelulaOpcaoSanguePeriferico(): boolean {
        if(this.recebimentoColeta && this.recebimentoColeta.fonteCelula){
            return this.recebimentoColeta.fonteCelula.id === FontesCelulas.SANGUE_PERIFERICO.id;
        }
        return false;
    }

};
