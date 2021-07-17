import { Component, Input, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { TiposAmostra } from 'app/shared/enums/tipos.amostra';
import { FileItem, FileLikeObject, FileUploader, FileUploaderOptions } from 'ng2-file-upload';
import { PedidoExame } from '../../../laboratorio/pedido.exame';
import { PedidoExameService } from '../../../laboratorio/pedido.exame.service';
import { AutenticacaoService } from '../../../shared/autenticacao/autenticacao.service';
import { BaseForm } from '../../../shared/base.form';
import { Configuracao } from '../../../shared/dominio/configuracao';
import { DominioService } from '../../../shared/dominio/dominio.service';
import { Laboratorio } from '../../../shared/dominio/laboratorio';
import { TiposExame } from '../../../shared/enums/tipos.exame';
import { ErroMensagem } from '../../../shared/erromensagem';
import { EventEmitterService } from '../../../shared/event.emitter.service';
import { HlaComponent } from '../../../shared/hla/hla.component';
import { PacienteUtil } from '../../../shared/paciente.util';
import { LaboratorioService } from '../../../shared/service/laboratorio.service';
import { ArrayUtil } from '../../../shared/util/array.util';
import { DataUtil } from '../../../shared/util/data.util';
import { DateMoment } from '../../../shared/util/date/date.moment';
import { DateTypeFormats } from '../../../shared/util/date/date.type.formats';
import { StringUtil } from '../../../shared/util/string.util';
import { PacienteConstantes } from '../../paciente.constantes';
import { PacienteService } from '../../paciente.service';
import { Arquivo } from './arquivo';
import { ArquivoExame } from './arquivo.exame';
import { ArquivoExameService } from './arquivo.exame.service';
import { ExamePaciente } from './exame.paciente';
import { ExameService } from './exame.service';
import { Locus } from './locus';
import { LocusExame } from './locusexame';
import { LocusVO } from './locusvo';
import { Metodologia } from './metodologia';

/**
 * Component responsavel exames
 * @author Filipe Paes
 * @export
 * @class ExameComponent
 * @extends {BaseForm}
 * @implements {OnDestroy}
 */
@Component({
    selector: 'exame',
    moduleId: module.id,
    templateUrl: './exame.component.html',
    //styleUrls: ['../paciente.css']
})
export class ExameComponent extends BaseForm<ExamePaciente> implements OnInit, OnDestroy {

    // Chaves que envolvem o upload do arquivo
    // TODO: Definir as chaves que envolvem o upload.
    private CONFIG_UPLOAD_CHAVES: String[] = ["extensaoArquivoLaudo", "qtdArquivosLaudo", "tamanhoArquivoLaudoEmByte"];

    // Lista de configurações referente ao upload de arquivo.
    public configuracaoUpload: Configuracao[];
    private readonly LOCUS_A: string = "A";
    private readonly LOCUS_B: string = "B";
    private readonly LOCUS_DRB1: string = "DRB1";
    private readonly LOCUS_OBRIGATORIOS: string[] = [this.LOCUS_A, this.LOCUS_B, this.LOCUS_DRB1];
    public locusObrigatoriosCT: string[] = [];
    public uploader: FileUploader;
    public aleloFocus:any;
    private caminhoArquivosLaudoEnviados:string;

    private METODOLOGIA_IND: number = 5;

    // Se TRUE, indica que a tela de exame foi acessada pelo cadastro do paciente
    // devendo ser carregado os 3 locus básicos. Se FALSE, estão sendo acessado
    // pela de tela de novo exame, que não abre nenhum locus sugerido.
    public iniciarPorTelaCadastro:boolean = true;

    public tipoExameCtSwab: boolean = false;

    @Input("idpedidoexame")
    public idPedidoExame:number;
    public pedidoExame:PedidoExame;
    listLocusEscolhidos:LocusVO[];
    //metodologias: Metodologia[] = [];
    exameForm: FormGroup;
    private _locus: Locus[] = [];
    private customErros: any = {
        tipoArquivoLaudo: "",
        quantidadeArquivoLaudo: "",
        tamanhoArquivoLaudo: "",
        inserirLaudo: "",
        nenhumLaudoSelecionado: "",
        metodologia: ""
    }

    private _arquivos: Arquivo[] = [];

    public data: Array<string | RegExp>;

    public laboratorios: Laboratorio[] = [];

    // Componente de HLA associado a tela de exame
    @ViewChild("hlaComponent")
    private hlaComponent: HlaComponent;
    // Representação do HLA no formulário
    public hlaFormArray: FormArray = new FormArray([]);


    constructor( private autenticacaoService: AutenticacaoService,
                private fb: FormBuilder, private dominioService: DominioService,
                private exameService: ExameService, translate: TranslateService,
                private pacienteService:PacienteService, private arquivoExameService: ArquivoExameService,
                private laboratorioService: LaboratorioService,  private pedidoExameService:PedidoExameService) {
        super();
        this.translate = translate;

        var exameComponent: ExameComponent = this;

        this.formErrors['tipoArquivoLaudo'] = "";
        this.formErrors['quantidadeArquivoLaudo'] = "";
        this.formErrors['tamanhoArquivoLaudo'] = "";
        this.formErrors['inserirLaudo'] = "";
        this.formErrors['nenhumLaudoSelecionado'] = "";
        this.formErrors['metodologia'] = "";

        this.uploader = new FileUploader({});

        // Carrega a configuração necessária para o upload do arquivo.
        this.dominioService.listarConfiguracoes(this.CONFIG_UPLOAD_CHAVES)
            .then(result => {
                this.configuracaoUpload = result;
                let options: FileUploaderOptions = this.uploader.options;
                this.configuracaoUpload.forEach(configuracao => {
                    if (configuracao.chave == this.CONFIG_UPLOAD_CHAVES[0]) {
                        options.allowedMimeType = configuracao.valor.split(',');
                    }
                    else if (configuracao.chave == this.CONFIG_UPLOAD_CHAVES[1]) {
                        options.queueLimit = Number.parseInt(configuracao.valor);
                    }
                    else if (configuracao.chave == this.CONFIG_UPLOAD_CHAVES[2]) {
                        options.maxFileSize = Number.parseInt(configuracao.valor);
                    }
                });
                this.translate.get("mensagem.erro.tamanhoArquivoLaudo", {maxFileSize: StringUtil.formatBytes(this.configuracaoUpload.find(configuracao => configuracao.chave == this.CONFIG_UPLOAD_CHAVES[2]).valor )}).subscribe(res => {
                    this.customErros['tamanhoArquivoLaudo'] = res;
                });
                this.uploader.setOptions(options);

            }, (error: ErroMensagem) => {
                // TODO: Durante a implementação do formulário, tratar mensagem de erro!
            });


        this.uploader.onAfterAddingFile = function (fileItem: FileItem): any {

            exameComponent.clearMensagensErro(exameComponent.exameForm);
            delete exameComponent.formErrors['arquivo'];
            let data: FormData = new FormData();

            data.append("file", fileItem._file, fileItem.file.name);
            if(exameComponent.iniciarPorTelaCadastro){
                //FIXME colocar o ID real do usuario logado
                exameComponent.arquivoExameService.salvarArquivo(data).then(res =>
                {
                    let arquivo:Arquivo=new Arquivo();
                    arquivo.fileItem=fileItem;
                    arquivo.nome=<String>res;
                    if (arquivo.nome) {
                        let index = arquivo.nome.indexOf("_");
                        arquivo.nomeSemTimestamp = arquivo.nome.substring(index + 1, arquivo.nome.length);
                    }
                    exameComponent._arquivos.push(arquivo);
					EventEmitterService.get("eventSalvarDraft").emit();
                }, (error: ErroMensagem) => {
                    exameComponent.alterarMensagem.emit(error.mensagem);
                });
            }
            else {
                let arquivo:Arquivo=new Arquivo();
                arquivo.fileItem=fileItem;
                arquivo.nome=fileItem.file.name;
                arquivo.nomeSemTimestamp=fileItem.file.name;
                exameComponent._arquivos.push(arquivo);
            }
        };

        this.uploader.onWhenAddingFileFailed = function (item: FileLikeObject, filter: any, options: any): any {
            exameComponent.clearMensagensErro(exameComponent.exameForm);
            switch (filter.name) {
                case 'queueLimit':
                    exameComponent.formErrors['arquivo'] = exameComponent.customErros['quantidadeArquivoLaudo'];
                    break;
                case 'fileSize':
                    let maxFileSize: number = 0;
                    exameComponent.configuracaoUpload.forEach(configuracao => {
                        if (configuracao.chave == exameComponent.CONFIG_UPLOAD_CHAVES[2]) {
                            maxFileSize = Number.parseInt(configuracao.valor);
                        }
                    });
                    exameComponent.formErrors['arquivo'] = exameComponent.customErros["tamanhoArquivoLaudo"];
                    // 'O arquivo possui ' + exameComponent.formatBytes(item.size) + ' que ultrapassa o tamanho máximo permitido de ' + exameComponent.formatBytes(maxFileSize);
                    break;
                case 'mimeType':
                    exameComponent.formErrors['arquivo'] = exameComponent.customErros["tipoArquivoLaudo"];
                    break;
                default:
                    exameComponent.formErrors['arquivo'] = exameComponent.customErros["inserirLaudo"];
                    break;
            }
        };
        this.data = [/[0-9]/,/[0-9]/, '\/', /[0-9]/,/[0-9]/, '\/', /[0-9]/,/[0-9]/,/[0-9]/,/[0-9]/]

        this.buildForm();
    }

    ngOnInit() {

        this.translate.get("pacienteForm.exameGroup").subscribe(res => {
            this._formLabels = res;

            if(this.temPermissaoParaCadastrarTipoAmostra()){
                let tipoAmostraFormArray: FormArray = new FormArray([]);
                this.listTiposAmostraExame().forEach(amostra => {
                    tipoAmostraFormArray.push(this.fb.group(amostra));
                });
                this.exameForm.setControl('listTiposAmostra', tipoAmostraFormArray);
            }

            this.criarMensagensErro(this.exameForm);
        });
        this.translate.get("mensagem.erro").subscribe(res => {
            this.customErros["tipoArquivoLaudo"] = res.tipoArquivoLaudo;
            this.customErros['quantidadeArquivoLaudo'] = res.quantidadeArquivoLaudo;

            this.customErros['inserirLaudo'] = res.inserirLaudo;
            this.customErros['nenhumLaudoSelecionado'] = res.nenhumLaudoSelecionado;
            if(this.temPermissaoParaCadastrarTipoAmostra()){
                this.customErros["tipoAmostra"] = res.tipoAmostra;
            }
            this.customErros["metodologia"] = res.metodologia;

        });

        if(this.idPedidoExame){
            this.pedidoExameService.carregarPedidoExame(this.idPedidoExame).then(r =>{
                this.pedidoExame = r;
                this.pedidoExame.tipoExame.locus.forEach(l=>{
                    this.locusObrigatoriosCT.push(l.codigo);
                    this.adicionarLocusIniciaisCTNoForm();
                    this.removerElementosForm();
                });
                if(this.pedidoExame.tipoExame.id === TiposExame.TESTE_CONFIRMATORIO_SWAB
                    || this.pedidoExame.tipoExame.id === TiposExame.TESTE_CONFIRMATORIO_SOMENTE_SWAB){
                    this.tipoExameCtSwab = true;
                }
            }).catch(error => {
                this.alterarMensagem.emit(error.mensagem);
            });

        }

        this.laboratorioService.listarLaboratorios().then(res => {
            if (res.content) {
                res.content.forEach(item => {
                    let lab: Laboratorio = new Laboratorio().jsonToEntity(item);
                    this.laboratorios.push(lab);
                });
            }
        }, (error: ErroMensagem) => {
            this.alterarMensagem.emit(error.mensagem);
        });

        this.adicionarMetodologiasNoForm();

        this.carregarTodosLocus();
    }

    ngAfterViewInit(){}

    private adicionarMetodologiasNoForm(){
        this.dominioService.listarMetodologias().then(res => {
            let metodologias: Metodologia[] = res;
            let metodologiaFormArray: FormArray = new FormArray([]);
            metodologias.forEach((metodologia: Metodologia) => {
                if(metodologia.id != this.METODOLOGIA_IND){
                    metodologiaFormArray.push(this.fb.group({
                        'id': [metodologia.id, null ],
                        'sigla': [metodologia.sigla, null],
                        'descricao': [metodologia.descricao, null],
                        'checked': [null, null]
                    }));
                }
            });
            this.exameForm.setControl('listMetodologias', metodologiaFormArray);
            this.criarMensagemValidacaoPorFormGroup(this.exameForm);

        }, (error: ErroMensagem) => {
            this.alterarMensagem.emit(error.mensagem);
        });
    }

    private carregarTodosLocus(){
        this.dominioService.listarLocus().then(res => {
            this.locus = res;

            if(this.iniciarPorTelaCadastro){
                this.adicionarLocusIniciaisNoForm();
            }

        }, (error: ErroMensagem) => {
            this.alterarMensagem.emit(error.mensagem);
        });
    }

    /**
     * Inicialmente somente os locus A, B, DRB1 eram pré selecionados e exigidos,
     * agora todos os locus devem ser listados.
     */
    private adicionarLocusIniciaisNoForm(){
        this.hlaComponent.locusObrigatorios =
            [LocusVO.LOCI_A, LocusVO.LOCI_B, LocusVO.LOCI_DRB1];
        this.hlaComponent.setValue(
            this.locus.map(loci => {return new LocusExame(loci.codigo)})
        );
        this.criarMensagemValidacaoPorFormGroup(this.exameForm);
    }



    /**
     * Adiciona a obrigatoriedade do locus inicial de CT.
     *
     * @private
     * @memberof ExameComponent
     */
    private adicionarLocusIniciaisCTNoForm(){
        this.dominioService.listarLocus().then(res => {
            this.locus = res;
            let locusExamesObrigatorios: LocusExame[] = [];
            this.locus.forEach(locus => {
                this.locusObrigatoriosCT.forEach(locusCT =>{
                    if (locus.codigo == locusCT){
                        locusExamesObrigatorios.push(new LocusExame(locusCT));
                    }
                });
            });

            this.hlaComponent.locusObrigatorios =
                ArrayUtil.distinct(
                    locusExamesObrigatorios.map(locusExame => {
                        return locusExame.id.locus.codigo;
                    })
                );
            this.hlaComponent.setValue(locusExamesObrigatorios);
            this.criarMensagemValidacaoPorFormGroup(this.exameForm);
            this.exameForm.get('dataColetaAmostra').setValue(DataUtil.toDateFormat(this.pedidoExame.dataColetaAmostra, DateTypeFormats.DATE_ONLY));
        }, (error: ErroMensagem) => {
            this.alterarMensagem.emit(error.mensagem);
        });
    }

    /**
     * Constrói os FormControl dos campos obrigatorios
     * @param FormBuilder para preencher os campos com suas respectivas validações
     * @author Filipe Paes
     */
    buildForm() {
        this.exameForm = this.fb.group({
            'upload': null,
            'listMetodologias': new FormArray([]),
            'listTiposAmostra': new FormArray([]),
            'laboratorio':[null, Validators.required],
            'laboratorioParticular':[false, Validators.required],
            'dataColetaAmostra':[null, Validators.required],
            'dataExame':[null, Validators.required],
        });
    }

    removerElementosForm(){
        this.exameForm.removeControl('laboratorio');
        this.exameForm.removeControl('laboratorioParticular');
        // this.exameForm.removeControl('dataColetaAmostra');
    }

    get locus(): Locus[]{
        return this._locus;
    }

    set locus(locus:Locus[]){
        this._locus = locus;
    }

    /**
     * Método para popular o objeto de exame e devolver para o
     * componente de cadastro este item pronto para ser enviado
     * @author Filipe Paes
     * @readonly
     * @type {Exame}@memberof ExameComponent
     */
    get exame(): ExamePaciente {
        let exame: ExamePaciente = new ExamePaciente();
        if(!this.idPedidoExame){
            if (this.exameForm.get("laboratorio").value && this.exameForm.get("laboratorio").value != "") {
                exame.laboratorio = new Laboratorio(new Number(this.exameForm.get("laboratorio").value).valueOf());
            }

            exame.laboratorioParticular = this.exameForm.get("laboratorioParticular").value;

            let _dataColeta = this.exameForm.get("dataColetaAmostra").value;
            exame.dataColetaAmostra = PacienteUtil.obterDataSemMascara( _dataColeta );
        }

        let _dataExame = this.exameForm.get("dataExame").value;
        exame.dataExame = PacienteUtil.obterDataSemMascara( _dataExame );

        let qtd: number = 0;
        this.listTiposAmostraForm.controls.forEach(tipoAmostraForm => {
            if(tipoAmostraForm.get('checked').value){
                exame.tipoAmostra = tipoAmostraForm.get('id').value;
                qtd++;
            }
        });
        if(qtd > 1){
            exame.tipoAmostra = 2;
        }

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

        this._arquivos.forEach(arquivo => {
            let arq: ArquivoExame = new ArquivoExame();
            arq.caminhoArquivo = arquivo.nome;
            exame.arquivosExame.push(arq);
        })
        return exame;
    }


    ngOnDestroy(): void {
        //throw new Error("Method not implemented.");
    }

    clearMensagensErro(form: FormGroup, nomeGroupPai?: string,fieldName?:string, index?: number) {
        super.clearMensagensErro(form, nomeGroupPai,fieldName, index);
        this.formErrors['tipoArquivoLaudo'] = "";
        this.formErrors['quantidadeArquivoLaudo'] = "";
        this.formErrors['tamanhoArquivoLaudo'] = "";
        this.formErrors['inserirLaudo'] = "";
        this.formErrors['nenhumLaudoSelecionado'] = "";
    }

    resetaFormulario() {
        this.clearMensagensErro(this.exameForm);
        this.exameForm.reset();
        this._arquivos = [];
    }

    public form(): FormGroup{
        return this.exameForm;
    }

    get listMetodologiasForm(): FormArray {
        return this.exameForm.get('listMetodologias') as FormArray;
    }

    get listTiposAmostraForm(): FormArray {
        return this.exameForm.get('listTiposAmostra') as FormArray;
    }

    /**
     * Método para validar todo o formulario
     *
     * @returns {boolean}
     * @memberof ExameComponent
     */
    public validateForm():boolean{
        this.clearMensagensErro(this.exameForm);
        let valid: boolean = super.validateForm();
        if (this._arquivos == null || this._arquivos.length == 0) {
//            this.formErrors['nenhumLaudoSelecionado'] = this.customErros["nenhumLaudoSelecionado"];
            //valid = false;
//            valid = true;
        }
        let dataColetaMenorDataExame = true;
        let dataColeta: Date = PacienteUtil.obterDataSemMascara(this.form().get("dataColetaAmostra").value);
        let dataExame: Date = PacienteUtil.obterDataSemMascara(this.form().get("dataExame").value);

        dataColetaMenorDataExame = this.verificarSeDatasInicioFimSaoValidas(
            dataColeta, dataExame, "dataColetaAmostra", this._formLabels['dataColetaAmostraMaiorDataExame']);

        let metodologiaValid: boolean = this.validateMetodologias();

        let validaFinal: boolean = true;//this.validarArquivosExame();
        let hlaValido: boolean = this.hlaComponent.validateForm();

        if(this.temPermissaoParaCadastrarTipoAmostra()){
            let tipoAmostraValid: boolean = this.validateTiposAmostra();
            return valid && validaFinal && metodologiaValid && tipoAmostraValid && dataColetaMenorDataExame && hlaValido;
        }
        return valid && validaFinal && metodologiaValid && dataColetaMenorDataExame && hlaValido;
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
     * @memberof ExameComponent
     */
    private validateMetodologias(): boolean {
        let valid: boolean = false;
        this.listMetodologiasForm.controls.forEach(control => {
            if (control.get('checked').value)
                valid = true;
        });

        if (!valid) {
            this.formErrors['metodologia'] = this.customErros["metodologia"];
        }
        else {
            this.formErrors['metodologia'] = '';
        }

        return valid;
    }

    /**
     * Método para verificar se ao menos um tipo de amostra foi preenchido
     *
     * @private
     * @returns {boolean} true se ao menos um tipo de amostra foi seleionado
     * @memberof ExameComponent
     */
    private validateTiposAmostra(): boolean {
        let valid: boolean = false;
        this.listTiposAmostraForm.controls.forEach(control => {
            if (control.get('checked').value)
                valid = true;
        });

        if (!valid) {
            this.formErrors['tipoAmostra'] = this.customErros["tipoAmostra"];
        }
        else {
            this.formErrors['tipoAmostra'] = '';
        }

        return valid;
    }

    /**
     * Valida se foi incluído, no mínimo, um arquivo de laudo.
     *
     * @author Filipe Paes
     * @return TRUE se foi validado com sucesso
     */
    private validarArquivosExame():boolean{
        delete this.formErrors['exameGeral'];
        delete this.formErrors['arquivo'];

        this.formErrors["exameGeral"] = "";
        if(this._arquivos.length < 1 ){
            this.translate.get("mensagem.erro.aoMenosUmArquivo").subscribe(res => {
                this.formErrors["arquivo"] = res;
            });
        }

        return (this._arquivos.length > 0);
    }

    // Override
    public preencherFormulario(exame:ExamePaciente): void {
        if(exame){
            if (exame.laboratorio && exame.laboratorio.id) {
                this.setPropertyValue("laboratorio", exame.laboratorio.id);
            }
            this.setPropertyValue("laboratorioParticular", exame.laboratorioParticular);
            if (exame.laboratorioParticular) {
                this.clickLaboratorioParticular({target: {checked: true}});
            }

            this.setPropertyValue("dataColetaAmostra",
                DataUtil.toDateFormat(exame.dataColetaAmostra, DateTypeFormats.DATE_ONLY));
            this.setPropertyValue("dataExame",
                DataUtil.toDateFormat(exame.dataExame, DateTypeFormats.DATE_ONLY));
            this.selecionarMetodologias(exame.metodologias);

            if(ArrayUtil.isNotEmpty(exame.locusExames)){
                this.hlaComponent.setValue(exame.locusExames);
            }

            this.selecionarArquivosLaudo(exame.arquivosExame);
        }
    }

    /**
     * Remove o arquivo que foi feito no upload
     *
     * @param {string} nomeArquivo
     * @memberof ExameComponent
     */
    public removerArquivo(nomeArquivo: string) {
        if (this.iniciarPorTelaCadastro) {
            this.arquivoExameService.removerArquivo(PacienteConstantes.USUARIO_LOGADO_FAKE, nomeArquivo).then(res => {
                if (res.ok) {
                    this.removerArquivoPorNome(nomeArquivo);
                }
            });
        } else {//Foi iniciado pela tela de novo exame
            this.removerArquivoPorNome(nomeArquivo);
        }
    }

    private removerArquivoPorNome(nomeArquivo: string) {

        for(let i=0; i< this.uploader.queue.length; i++) {
            let arquivo:FileItem = this.uploader.queue[i];
            if (this.iniciarPorTelaCadastro) {
                let index = nomeArquivo.indexOf("_");
                if (index > 0) {
                    let nomeSemTimestamp: string = nomeArquivo.substring(index + 1, nomeArquivo.length);
                    if (arquivo._file.name == nomeSemTimestamp) {
                        this.uploader.queue.splice(i, 1);
                    }
                }
            } else{
                if (arquivo._file.name == nomeArquivo) {
                    this.uploader.queue.splice(i, 1);
                }
            }
        }

        for (let x = 0; x < this._arquivos.length; x++) {
            if (this._arquivos[x].nome == nomeArquivo) {
                this._arquivos.splice(x, 1);
            }
        }
    }

    /**
     * Seleciona o checkbox, segundo os valores informados como parametros.
     * Os demais são considerados não checados.
     *
     * @param metodologiasSelecionadas
     */
    private selecionarMetodologias(metodologiasSelecionadas:Metodologia[]):void{
        if(metodologiasSelecionadas != null && metodologiasSelecionadas.length > 0){
            this.selecionarMetodologiasInformadasPorParametro(metodologiasSelecionadas);
        }
    }

    /**
     * Remove todas as seleções de todas as metodologias informadas
     *
     */
    private removerTodasSelecaoMetodologias():void{
        let metodologias: FormArray = <FormArray> this.form().get("listMetodologias");
        metodologias.controls.forEach(metodologiaFormGroup => {
            metodologiaFormGroup.get('checked').setValue(false);
        });
    }

    /**
     * Seleciona somente as metodologias informadas no parametro.
     *
     */
    private selecionarMetodologiasInformadasPorParametro(selecionadas:Metodologia[]):void{
        this.removerTodasSelecaoMetodologias();

        let metodologias: FormArray = <FormArray> this.form().get("listMetodologias");

        metodologias.controls.forEach(metodologiaFormGroup => {
            selecionadas.forEach(metodologia => {
                if(metodologia.id === metodologiaFormGroup.get('id').value){
                    metodologiaFormGroup.get('checked').setValue(true);
                }
            });
        });
    }

    /**
     * Preenche a lista de arquivos da lista do uploads na tela.
     *
     * @param arquivos
     */
    private selecionarArquivosLaudo(arquivosExame:ArquivoExame[]):void{
        this._arquivos = [];
        if(arquivosExame != null && arquivosExame.length > 0){
            arquivosExame.forEach(arquivoExame => {
                let arquivo:Arquivo = new Arquivo();
                arquivo.nome = arquivoExame.caminhoArquivo;

                let index = arquivoExame.caminhoArquivo.indexOf("_");
                if (index > 0) {
                    arquivo.nomeSemTimestamp = arquivoExame.caminhoArquivo.substring(index + 1, arquivoExame.caminhoArquivo.length);
                }

                this._arquivos.push(arquivo);
            });
        }
    }

    nomeComponente(): string {
        return null;
    }

    public get arquivos(): Arquivo[]  {
		return this._arquivos;
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


     /**
     * @description Lista todos os tipos de amostra
     * @author ergomes
     * @public
     */
    public listTiposAmostraExame(): any[] {
        const sangue = {
            id: [TiposAmostra.SANGUE,null],
            descricao: [this._formLabels['sangue'],null],
            checked: [null,null],
        }
        const swab = {
            id: [TiposAmostra.SWAB,null],
            descricao: [this._formLabels['swab'],null],
            checked: [null,null],
        }

        return [sangue, swab];
    }

    public temPermissaoParaCadastrarTipoAmostra(): boolean {
        return this.autenticacaoService.temRecurso('CADASTRAR_TIPO_AMOSTRA_EXAME');
    }
}
