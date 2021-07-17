import { TarefaService } from './../../shared/tarefa.service';
import { DateTypeFormats } from './../../shared/util/date/date.type.formats';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { ExamePaciente } from '../cadastro/exame/exame.paciente';
import { Laboratorio } from '../../shared/dominio/laboratorio';
import { HlaComponent } from '../../shared/hla/hla.component';
import { LaboratorioService } from '../../shared/service/laboratorio.service';
import { DateMoment } from '../../shared/util/date/date.moment';
import { BaseForm } from '../../shared/base.form';
import { DataUtil } from '../../shared/util/data.util';
import { CampoMensagem } from '../../shared/campo.mensagem';
import { DominioService } from '../../shared/dominio/dominio.service';
import { MotivoDescarte } from '../../shared/dominio/motivo.descarte';
import { ErroMensagem } from '../../shared/erromensagem';
import { PacienteUtil } from '../../shared/paciente.util';
import { ArquivoExame } from '../cadastro/exame/arquivo.exame';
import { ArquivoExameService } from '../cadastro/exame/arquivo.exame.service';
import { ExameService } from '../cadastro/exame/exame.service';
import { Metodologia } from '../cadastro/exame/metodologia';
import { HeaderPacienteComponent } from '../consulta/identificacao/header.paciente.component';
import { RouterUtil } from '../../shared/util/router.util';

/**
 * Component responsavel pela conferencia de exame
 * @author Bruno Souza
 * @export
 * @class ConferenciaDetalheComponent
 * @extends {BaseForm}
 * @implements {OnDestroy}
 */
@Component({
    selector: 'avaliacao',
    moduleId: module.id,
    templateUrl: './conferencia.detalhe.component.html',
    styleUrls: ['./conferencia.component.css']
})
export class ConferenciaDetalheComponent extends BaseForm<Object> implements OnInit {
    sucessoAceiteExame: any;
    sucessoDescarteExame: any;
    mensagemSucesso: any;
    _idExameInserido: any;

    segundaMensagem: string = "";

    private _exame: ExamePaciente;
    private rmrPaciente: number;
    public exameForm: FormGroup;
    public maskData: Array<string | RegExp>;

    private customErros: any = {
        tipoArquivoLaudo: "",
        quantidadeArquivoLaudo: "",
        tamanhoArquivoLaudo: "",
        inserirLaudo: "",
        nenhumLaudoSelecionado: "",
        metodologia: ""
    }

    private METODOLOGIA_IND: number = 5;

    private _motivosDescarte: MotivoDescarte[];
    motivoId: number = 1;

    private descarteIrProximo: boolean;

    private idExame: number;

    private idTarefa:number;

    @ViewChild("modalMsg")
    private modalMsg;

    @ViewChild("modalMsgErro")
    private modalMsgErro;

    @ViewChild(HeaderPacienteComponent)
    private headerPaciente: HeaderPacienteComponent;

    @ViewChild("modalDescartarExame")
    private modalDescartarExame;

    @ViewChild("modalErroDescarte")
    private modalErroDescarte;

    public laboratorios: Laboratorio[] = [];

    @ViewChild("hlaComponent")
    private hlaComponent: HlaComponent;


    public preencherFormulario(exame: ExamePaciente): Promise<any> {
        let promessaLaboratorio= Promise.resolve(this.adicionarLaboratorioNoForm(exame.laboratorio));
        let promesaMetodologias = Promise.resolve(this.adicionarMetodologiasNoForm(exame.metodologias));

        this.hlaComponent.setValue(exame.locusExames);

        this.exameForm.get("laboratorioParticular").setValue(exame.laboratorioParticular);
        this.clickLaboratorioParticular({target: {checked: exame.laboratorioParticular}});
        this.exameForm.get("dataColetaAmostra").setValue(exame.dataColetaAmostraFormatada);
        this.exameForm.get("dataExame").setValue(exame.dataExameFormatada);
        return Promise.all([promesaMetodologias]);
    }

    constructor(private fb: FormBuilder,private activatedRouter: ActivatedRoute,translate: TranslateService, private exameService:ExameService,
            private router:Router, private arquivoExameService:ArquivoExameService, private dominioService:DominioService,
            private laboratorioService: LaboratorioService, private tarefaService:TarefaService){
        super();
        this.maskData = [/[0-9]/, /[0-9]/, '\/', /[0-9]/, /[0-9]/, '\/', /[0-9]/, /[0-9]/, /[0-9]/, /[0-9]/]
        this.translate = translate;
        this.buildForm();
    }

    /**
     * Executado ao inicializar o componente
     *
     * @memberof ConferenciaDetalheComponent
     */
    ngOnInit() {
        RouterUtil.recuperarParametrosDaActivatedRoute(this.activatedRouter, ['idExame', 'idTarefa']).then(res => {
            this.idExame = Number(res['idExame']);
            this.idTarefa =  Number(res['idTarefa']);
            this.carregarExame(this.idExame);
        });

        this.translate.get("mensagem.sucesso.aceiteexame").subscribe(res => {
            this.sucessoAceiteExame = res;
        });

        this.translate.get("mensagem.sucesso.descarteexame").subscribe(res => {
            this.sucessoDescarteExame = res;
        });

        this.translate.get("conferenciaExame.segundaMensagem").subscribe(res => {
            this.segundaMensagem = res;
        });

        this.exameService.listarMotivosDescarte().then( response => {
            this._motivosDescarte = response;
        }, error => {});

        //como o onInit é sempre chamado ao descartar um exame
        //garanto que a combo sempre será iniciada com o primeiro elemento
        this.motivoId = 1;
    }

    /**
     * Metodo que constrói o formulário
     *
     * @memberof ConferenciaComponent
     */
    buildForm() {
        this.exameForm = this.fb.group({
            'laboratorio': [null, Validators.required],
            'laboratorioParticular': [null, Validators.required],
            'dataColetaAmostra': [null, Validators.required],
            'dataExame': [null, Validators.required],
            'listMetodologias': new FormArray([])
        });
    }

    private adicionarLaboratorioNoForm(laboratorio: Laboratorio): Promise<any> {
        return this.laboratorioService.listarLaboratorios().then(res => {
            this.laboratorios = [];
            if (res.content) {
                res.content.forEach(item => {
                    this.laboratorios.push(new Laboratorio().jsonToEntity(item));
                });
            }

            if (laboratorio && laboratorio.id) {
                this.form().get("laboratorio").setValue(laboratorio.id);
            }

        }, (error: ErroMensagem) => {
            this.alterarMensagem.emit(error.mensagem);
        });
    }

    /**
     * Adicionar as metodologias ja inseridas no cadastro do paciente pelo medico
     * para avaliacao do pessoal do redome
     *
     * @private
     * @memberof ConferenciaComponent
     */
    private adicionarMetodologiasNoForm(metodologiasSelecionadas: Metodologia[]):Promise<any> {
        //let promiseMetodologia = this.dominioService.listarMetodologias();
        return this.dominioService.listarMetodologias().then(res => {
            let todasMetodologias: Metodologia[] = res;
            let metodologiaFormArray: FormArray = new FormArray([]);
            todasMetodologias.forEach((metodologia: Metodologia) => {
                if(metodologia.id != this.METODOLOGIA_IND){

                    let marcarMetodologia: boolean = false;

                    if(metodologiasSelecionadas){
                        marcarMetodologia = metodologiasSelecionadas.some(function(metodologiaSelecionada){ return metodologiaSelecionada.sigla == metodologia.sigla })
                    }
                    metodologiaFormArray.push(this.fb.group({
                        'id': [metodologia.id, null],
                        'sigla': [metodologia.sigla, null],
                        'descricao': [metodologia.descricao, null],
                        'checked': [marcarMetodologia, null]
                    }));
                }
            });
            this.exameForm.setControl('listMetodologias', metodologiaFormArray);

        }, (error: ErroMensagem) => {
            this.alterarMensagem.emit(error.mensagem);
        });
    }

    public descartarExame(proximo: boolean) {
        this.descarteIrProximo = proximo;

        this.modalDescartarExame.hide();
        this.exameService.descartarExame(this._exame.id, this.motivoId).then(response => {
                this.mensagemSucesso = this.sucessoDescarteExame;
                this.modalMsg.abrirModal();
            }, (error: ErroMensagem) => {
                let mensagem;
                error.listaCampoMensagem.forEach(obj => {
                    mensagem = obj.mensagem;
                });
                this.modalErroDescarte.mensagem = mensagem;
                this.modalErroDescarte.abrirModal();
            });
    }

    public abrirModalDescartarExame() {
        this.modalDescartarExame.show();
    }

    public fecharModalDescartarExame(modal: any) {
        modal.hide();
    }

    public fecharModalErroDescarte() {
        if (this.descarteIrProximo) {
          //  this.carregarExame();
        }
        else {
            this.irParaHome();
        }
    }

    /**
     * Recupera a listad e metodologia do formulario
     *
     * @readonly
     * @type {FormArray}
     * @memberof ConferenciaComponent
     */
    get listMetodologiasForm(): FormArray {
        return this.exameForm.get('listMetodologias') as FormArray;
    }

    /**
     * Método para voltar para home
     *
     * @memberof ConferenciaComponent
     */
    public irParaHome() {
        this.router.navigateByUrl('/home');
    }

    /**
     * Retorna o formulario atual para validações de campos
     *
     * @returns {FormGroup}
     * @memberof ConferenciaComponent
     */
    public form(): FormGroup {
        return this.exameForm;
    }

    /**
     * Nome do componente atual
     *
     * @returns {string}
     * @memberof ConferenciaDetalheComponent
     */
    nomeComponente(): string {
        return "ConferenciaDetalheComponent";
    }

    /**
     * Faz a chamada para o download do arquivo no servidor REST.
     *
     * @param arquivoExame
     */
    public baixarArquivo(arquivoExame: ArquivoExame) {
        this.arquivoExameService.baixarArquivoExame(
            arquivoExame.id,
            PacienteUtil.retornaNomeArquivoExameSelecionado(arquivoExame.caminhoArquivo));
    }

    /**
     * Faz a chamada para visualização do arquivo.
     *
     * @param arquivoExame
     */
    public visualizarArquivo(arquivoExame: ArquivoExame) {
        this.arquivoExameService.visualizarArquivoExame(arquivoExame.id);
    }

    /**
     * Faz a chamada para o download do arquivo zipado.
     *
     * @param arquivoExame
     */
    public baixarArquivoZipado(arquivoExame: ArquivoExame) {
        this.arquivoExameService.baixarArquivoExameZipado(arquivoExame.id,
            PacienteUtil.retornaNomeArquivoExameSelecionado(arquivoExame.caminhoArquivo));
    }

    /**
     * Faz a chamada para o download do arquivo zipado.
     *
     * @param arquivoExame
     */
    public baixarTodosArquivosZipados(exame: ExamePaciente) {
        this.exameService.baixarArquivosExamesZipados(exame.id, "laudo_exame");
    }

    /**
     * Metodo executado ao submeter o formulario de novo exame
     *
     * @param {*} sucessoModal
     * @memberof ConferenciaComponent
     */
    public conferirExame()
    {
        let exameOk: boolean = this.validateForm();
        if(exameOk){
            let exame: ExamePaciente = this._exame;
            this.exameService.aceitarExame(this._exame.id, this.obterExamePopulado()).then(res => {
                this.mensagemSucesso = this.sucessoAceiteExame;
                this.modalMsg.abrirModal();
                //sucessoModal.abrirModal();
            },
            (error: ErroMensagem) => {
                if (error.statusCode == 409) {
                        this.mensagemSucesso = error.listaCampoMensagem[0].mensagem;
                        this.modalMsg.abrirModal();
                }
                else {
                   this.marcarCamposInvalidosRetornadosBackend(error.listaCampoMensagem);
                }
            });
        }

    }
     /**
     * Marca os campos de todas as etapas com erro, identificadas de acordo
     * com a mensagem retornada do backend
     *
     * @param mensagens
     */
    private marcarCamposInvalidosRetornadosBackend(mensagens:CampoMensagem[]){
        mensagens.forEach(mensagemErro => {
            if(this.existsField(mensagemErro.campo)){
                this.markAsInvalid(this.getField(this.form(), mensagemErro.campo));
                this.formErrors[mensagemErro.campo] = mensagemErro.mensagem;
            }
            else {
                if (mensagemErro.campo == 'exameGeral') {
                    this.formErrors[mensagemErro.campo] = mensagemErro.mensagem;
                }
            }
        });
    }

 /**
     * Método para popular o objeto de exame e devolver para o
     * componente de cadastro este item pronto para ser enviado
     * @author Filipe Paes
     * @readonly
     * @type {Exame}@memberof ConferenciaComponent
     */
    obterExamePopulado(): ExamePaciente {
        let exame: ExamePaciente = new ExamePaciente();
        exame.id = this._exame.id;

        if (this.exameForm.get("laboratorio").value && this.exameForm.get("laboratorio").value != "") {
            exame.laboratorio = new Laboratorio(new Number(this.exameForm.get("laboratorio").value).valueOf());
        }

        exame.laboratorioParticular = this.exameForm.get("laboratorioParticular").value;

        let _dataColetaAmostra = this.exameForm.get("dataColetaAmostra").value;
        exame.dataColetaAmostra = DataUtil.toDate(_dataColetaAmostra);

        let _dataExame = this.exameForm.get("dataExame").value;
        exame.dataExame = DataUtil.toDate(_dataExame);
        //PacienteUtil.obterDataComSeparador(_dataExame);

        this.listMetodologiasForm.controls.forEach(metodologiaForm => {
            if (metodologiaForm.get('checked').value) {
                let metodologia: Metodologia = new Metodologia();
                metodologia.id = metodologiaForm.get('id').value;
                metodologia.sigla = metodologiaForm.get('sigla').value;
                metodologia.descricao = metodologiaForm.get('descricao').value;
                exame.metodologias.push(metodologia);
            }
        });

        exame.locusExames = this.hlaComponent.getValue();

        return exame;
    }

    /**
         * Método para validar todo o formulario
         *
         * @returns {boolean}
         * @memberof ConferenciaComponent
         */
    public validateForm(): boolean {
        this.criarMensagemValidacaoPorFormGroup(this.exameForm);
        let valid: boolean = super.validateForm();
        let hlaValido: boolean = this.hlaComponent.validateForm();

        let dataColeta: Date = PacienteUtil.obterDataSemMascara(this.form().get("dataColetaAmostra").value);
        let dataExame: Date = PacienteUtil.obterDataSemMascara(this.form().get("dataExame").value);

        let dataColetaMenorDataExame = this.verificarSeDatasInicioFimSaoValidas(
            dataColeta, dataExame, "dataColetaAmostra", this._formLabels['dataColetaAmostraMaiorDataExame']);

        let metodologiaValid: boolean = this.validateMetodologias();

        return valid && metodologiaValid && dataColetaMenorDataExame && hlaValido;
    }

    /**
     * Valida se o campos data inicio e data fim estão preenchidos corretamente, ou seja,
     * data início maior que final.
     * Com preenchimento inválido, marca o campo com erro e a mensagem informados.
     */
    private verificarSeDatasInicioFimSaoValidas(dataInicio: Date, dataFinal: Date,
        nomeCampoReferenciaValidacao: string, mensagemErro: string): boolean{
        if(dataInicio && dataFinal){
            let dateMoment: DateMoment = DateMoment.getInstance();

            if(dateMoment.isDateAfter(dataInicio, dataFinal)){
                this.forceError(nomeCampoReferenciaValidacao, mensagemErro);
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * Método para verificar se ao menos uma metodologia foi preenchida
     *
     * @private
     * @returns {boolean} true se ao menos uma metodologia foi seleionada
     * @memberof ConferenciaComponent
     */
    private validateMetodologias(): boolean {
        let valid: boolean = false;
        this.listMetodologiasForm.controls.forEach(control => {
            if (control.get('checked').value)
                valid = true;
        });

        if (!valid) {
            this.translate.get("mensagem.erro.metodologia").subscribe(res => {
                this.formErrors["metodologia"] = res;
            });
        }else {
            this.formErrors['metodologia'] = '';
        }
        return valid;
    }

    carregarExame(idExame:number) {

        this.translate.get("pacienteForm.exameGroup").subscribe(res => {
            this._formLabels = res;
            console.log(res);
            this.exameService.obterExame(idExame).then(res => {
                console.log("CarregaExame: " +  idExame);
                this._exame = res;
                this._exame.dataColetaAmostraFormatada = DataUtil.toDateFormat( DataUtil.toDate(res.dataColetaAmostra, DateTypeFormats.DATE_ONLY), DateTypeFormats.DATE_ONLY);
                this._exame.dataExameFormatada = DataUtil.toDateFormat( DataUtil.toDate(res.dataExame, DateTypeFormats.DATE_ONLY), DateTypeFormats.DATE_ONLY);
                this.rmrPaciente = res.paciente.rmr;
                setTimeout(() => {
                    Promise.resolve(this.headerPaciente).then(() => {
                        this.headerPaciente.popularCabecalhoIdentificacaoPorPaciente(this.rmrPaciente);
                    });
                }, 1);

                // this._exame.arquivosExame.forEach((arquivo:ArquivoExame)=>{
                //     if (arquivo.caminhoArquivo) {
                //         let index = arquivo.caminhoArquivo.indexOf("_");
                //         arquivo.nomeSemTimestamp = arquivo.caminhoArquivo.substring(index + 1, arquivo.caminhoArquivo.length);
                //     }
                // });

                this.preencherFormulario(this._exame).then(res=>{
                    this.criarMensagemValidacaoPorFormGroup(this.exameForm);

                });


            }, (error: ErroMensagem) => {
                error.listaCampoMensagem.forEach(obj => {
                    this.modalMsgErro.mensagem = obj.mensagem;
                });
                this.modalMsgErro.abrirModal();
            });


        });

    }

    fecharModalConferencia(modal:any){
        modal.fecharModalComponente();
    }

	public get exame(): ExamePaciente {
		return this._exame;
    }

	public get motivosDescarte(): MotivoDescarte[] {
		return this._motivosDescarte;
    }

    voltar(){
        this.tarefaService.removerAtribuicaoTarefa(this.idTarefa);
        this.router.navigateByUrl("/conferencia");
    }

    clickLaboratorioParticular(evento) {
        if (evento.target.checked) {
            this.form().get('laboratorio').setValue(null);
            this.resetFieldRequired(this.form(), 'laboratorio');
            this.form().get('laboratorio').disable();
        }
        else {
            this.setFieldRequired(this.form(), 'laboratorio');
            this.form().get('laboratorio').enable();
        }
    }



}
