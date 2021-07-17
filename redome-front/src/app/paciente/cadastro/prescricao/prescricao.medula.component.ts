import {FontesCelulas} from '../../../shared/enums/fontes.celulas';
import {ValidateData, ValidateDataMaiorOuIgualHoje} from '../../../validators/data.validator';
import {Component, OnInit, ViewChild} from "@angular/core";
import {BaseForm} from "../../../shared/base.form";
import {SolicitacaoDTO} from "../../busca/analise/solicitacao.dto";
import {Configuracao} from "../../../shared/dominio/configuracao";
import {FileItem, FileLikeObject, FileUploader, FileUploaderOptions} from "ng2-file-upload";
import {FormArray, FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Arquivo} from "../exame/arquivo";
import {DominioService} from "../../../shared/dominio/dominio.service";
import {TranslateService} from "@ngx-translate/core";
import {StringUtil} from "../../../shared/util/string.util";
import {ErroMensagem} from "../../../shared/erromensagem";
import {DateMoment} from '../../../shared/util/date/date.moment';
import {PacienteUtil} from '../../../shared/paciente.util';
import {HeaderPacienteComponent} from '../../consulta/identificacao/header.paciente.component';
import {ActivatedRoute, Router} from '@angular/router';
import {FonteCelula} from '../../../shared/dominio/fonte.celula';
import {HistoricoNavegacao} from '../../../shared/historico.navegacao';
import {PacienteService} from '../../paciente.service';
import {MessageBox} from '../../../shared/modal/message.box';
import {PrescricaoService} from '../../../doador/solicitacao/prescricao.service';
import {DoadorService} from '../../../doador/doador.service';
import {ErroUtil} from "../../../shared/util/erro.util";
import {TipoAmostraService} from 'app/doador/solicitacao/tipoamostra.service';
import {TipoAmostra} from '../../../shared/classes/tipo.amostra';
import {TipoAmostraPrescricao} from '../../../shared/classes/tipo.amostra.prescricao';
import {Evolucao} from '../evolucao/evolucao';
import {RouterUtil} from "../../../shared/util/router.util";
import {CriarPrescricaoMedulaDTO} from "../../../shared/dto/criar.prescricao.medula.dto";
import {Doador} from "../../../doador/doador";
import {TiposDoador} from "../../../shared/enums/tipos.doador";
import {DoadorNacional} from "../../../doador/doador.nacional";
import { DoadorInternacional } from "app/doador/doador.internacional";

/**
 * Component responsavel exames
 * @author Filipe Paes
 * @class PrescricaoComponent
 * @extends {BaseForm}
 * @implements {OnDestroy}
 */
@Component({
    selector: 'prescricao',
    moduleId: module.id,
    templateUrl: './prescricao.medula.component.html',
    //styleUrls: ['../paciente.css']
})
export class PrescricaoMedulaComponent extends BaseForm<SolicitacaoDTO> implements OnInit {

    // Constantes da classe
    private static PRAZO_DIAS_MAXIMO_PARA_COLETA_MEDULA_SEM_JUSTIFICATIVA: number = 30;

    @ViewChild('headerPaciente')
    private headerPaciente: HeaderPacienteComponent;

    // Chaves que envolvem o upload do arquivo
    private CONFIG_UPLOAD_CHAVES: String[] = ["extensaoArquivoPrescricao",
        "quantidadeMaximaArquivosPrescricao",
        "tamanhoArquivoPrescricaoEmByte"];

    /** Lista de itens carregadas na tela **/
    // Lista de configurações referente ao upload de arquivo.
    public configuracaoUpload: Configuracao[];
    // Lista de fontes de células exibidas.
    private _fontesCelulas: FonteCelula[] = [];

    // Referência para upload dos arquivos da prescrição.
    public uploader: FileUploader;
    // Referência para upload do arquivo de justificativa, se for necessário.
    public uploaderJustificativa: FileUploader;

    public formGroup: FormGroup;

    // Mensagens de erro customizadas para o front.
    private customErros: any = {
        tipoArquivoPrescricao: "",
        quantidadeArquivoPrescricao: "",
        tamanhoArquivoPrescricao: "",
        inserirPrescricao: "",
        nenhumaJustificativaSelecionada: "",
        quantidadeTotalInvalida: ""
    }

    private _arquivos: Arquivo[] = [];
    private _arquivoJustificativa: Arquivo = null;
    public data: Array<string | RegExp>;

    private _rmr: number;
    private _idDoador: number;
    private _peso: number;
    private _idMatch: number;
    private _idTipoDoador: number;
    private _avisoDataColetaPrazoExpirado: string = "";
    private _doador: Doador;

    tiposAmostra: TipoAmostra[] = [];
    private ID_TIPO_AMOSTRA_OUTROS: number = 6;
    evolucao: Evolucao;


    ngOnInit() {
        this.translate.get("prescricaoComponent").subscribe(res => {
            this._formLabels = res;
            this.criarMensagensErro(this.formGroup);
        });

        this.tipoAmostraService.listarTiposDeAmostra().then(res=>{
            res.forEach((tipoAmostra)=>{
                this.tiposAmostra.push(new TipoAmostra().jsonToEntity(tipoAmostra));
                this.tipoAmostraFormArray.push(this.tipoAmostraGroup(tipoAmostra));
            });

        });

        this.dominioService.listarFonteCelula([FontesCelulas.MEDULA_OSSEA, FontesCelulas.SANGUE_PERIFERICO])
            .then(res => {
                res.forEach(item => {
                    let fonteCelula: FonteCelula = new FonteCelula();
                    fonteCelula.id = item.id;
                    fonteCelula.descricao = item.descricao;
                    fonteCelula.sigla = item.sigla;
                    this._fontesCelulas.push(fonteCelula);
                });
            }, (error: ErroMensagem) => {
                let mensagemErro: string = ErroUtil.extrairMensagensErro(error);
                this.messageBox.alert(mensagemErro)
                   .withTarget(this)
                   .withCloseOption(() => {
                       this.router.navigateByUrl(HistoricoNavegacao.urlRetorno());
                   })
                   .show();
            });

        // Carrega a configuração necessária para o upload do arquivo.
        this.dominioService.listarConfiguracoes(this.CONFIG_UPLOAD_CHAVES)
            .then(result => {
                this.configuracaoUpload = result;
                let options: FileUploaderOptions = this.uploader.options;
                let optionsJustificativa: FileUploaderOptions = this.uploaderJustificativa.options;
                this.configuracaoUpload.forEach(configuracao => {
                    if (configuracao.chave == this.CONFIG_UPLOAD_CHAVES[0]) {
                        options.allowedMimeType = configuracao.valor.split(',');
                        optionsJustificativa.allowedMimeType = configuracao.valor.split(',');
                    }
                    else if (configuracao.chave == this.CONFIG_UPLOAD_CHAVES[1]) {
                        options.queueLimit = Number.parseInt(configuracao.valor);
                        optionsJustificativa.queueLimit = 1;
                    }
                    else if (configuracao.chave == this.CONFIG_UPLOAD_CHAVES[2]) {
                        options.maxFileSize = Number.parseInt(configuracao.valor);
                        optionsJustificativa.maxFileSize = Number.parseInt(configuracao.valor);
                    }
                });
                this.translate.get("mensagem.erro.tamanhoArquivoLaudo", {maxFileSize: StringUtil.formatBytes(this.configuracaoUpload.find(configuracao => configuracao.chave == this.CONFIG_UPLOAD_CHAVES[2]).valor )}).subscribe(res => {
                    this.customErros['tamanhoArquivoLaudo'] = res;
                });
                this.uploader.setOptions(options);
                this.uploaderJustificativa.setOptions(optionsJustificativa);

            }, (error: ErroMensagem) => {
                let mensagemErro: string = ErroUtil.extrairMensagensErro(error);
                this.messageBox.alert(mensagemErro)
                   .withTarget(this)
                   .withCloseOption(() => {
                       this.router.navigateByUrl(HistoricoNavegacao.urlRetorno());
                   })
                   .show();
            });

        this.uploader.onAfterAddingFile = (fileItem: FileItem): any => {
            this.clearMensagensErro(this.formGroup);
            delete this.formErrors['arquivo'];

            let arquivo:Arquivo=new Arquivo();
            arquivo.fileItem=fileItem;
            arquivo.nome=fileItem.file.name;
            arquivo.nomeSemTimestamp=fileItem.file.name;
            this._arquivos.push(arquivo);
        };

        this.uploader.onWhenAddingFileFailed = (item: FileLikeObject, filter: any, options: any): any => {
            this.clearMensagensErro(this.formGroup);
            switch (filter.name) {
                case 'queueLimit':
                    this.formErrors['arquivo'] = this.customErros['quantidadeArquivoPrescricao'];
                    break;
                case 'fileSize':
                    let maxFileSize: number = 0;
                    this.configuracaoUpload.forEach(configuracao => {
                        if (configuracao.chave == this.CONFIG_UPLOAD_CHAVES[2]) {
                            maxFileSize = Number.parseInt(configuracao.valor);
                        }
                    });
                    this.formErrors['arquivo'] = this.customErros["tamanhoArquivoPrescricao"];
                    break;
                case 'mimeType':
                this.formErrors['arquivo'] = this.customErros["tipoArquivoPrescricao"];
                    break;
                default:
                    this.formErrors['arquivo'] = this.customErros["inserirPrescricao"];
                    break;
            }
        };

        this.uploaderJustificativa.onAfterAddingFile = (fileItem: FileItem): any => {

            this.clearMensagensErro(this.formGroup);
            delete this.formErrors['arquivoJustificativa'];

            let arquivo:Arquivo=new Arquivo();
            arquivo.fileItem=fileItem;
            arquivo.nome=fileItem.file.name;
            arquivo.nomeSemTimestamp=fileItem.file.name;
            this._arquivoJustificativa = arquivo;
        };

        this.uploaderJustificativa.onWhenAddingFileFailed = (item: FileLikeObject, filter: any, options: any): any => {
            this.clearMensagensErro(this.formGroup);
            switch (filter.name) {
                case 'queueLimit':
                    this.formErrors['arquivoJustificativa'] = this.customErros['quantidadeArquivoPrescricao'];
                    break;
                case 'fileSize':
                    let maxFileSize: number = 0;
                    this.configuracaoUpload.forEach(configuracao => {
                        if (configuracao.chave == this.CONFIG_UPLOAD_CHAVES[2]) {
                            maxFileSize = Number.parseInt(configuracao.valor);
                        }
                    });
                    this.formErrors['arquivoJustificativa'] = this.customErros["tamanhoArquivoPrescricao"];
                    // 'O arquivo possui ' + exameComponent.formatBytes(item.size) + ' que ultrapassa o tamanho máximo permitido de ' + exameComponent.formatBytes(maxFileSize);
                    break;
                case 'mimeType':
                    this.formErrors['arquivoJustificativa'] = this.customErros["tipoArquivoPrescricao"];
                    break;
                default:
                    this.formErrors['arquivoJustificativa'] = this.customErros["inserirPrescricao"];
                    break;
            }
        };

        this.translate.get("mensagem.erro").subscribe(res => {
            this.customErros["tipoArquivoPrescricao"] = res.tipoArquivoLaudo;
            this.customErros['quantidadeArquivoPrescricao'] = res.quantidadeArquivoPrescricao;

            this.customErros['inserirPrescricao'] = res.inserirLaudo;
            this.customErros['nenhumaPrescricaoSelecionada'] = res.nenhumaPrescricaoSelecionada;
            this.customErros['nenhumaJustificativaSelecionada'] = res.nenhumaJustificativaSelecionada
            this.customErros['quantidadeTotalInvalida'] = res.quantidadeTotalInvalida;
        });

    }

    verificarCheckHabilitacao(formTipoAmostra: FormGroup): string{
        return (!formTipoAmostra.get('check').value == true)? 'disabled':'';
    }

    constructor(private fb: FormBuilder, private dominioService: DominioService,
                private prescricaoService: PrescricaoService, translate: TranslateService,
                private pacienteService: PacienteService, private messageBox: MessageBox,
                private activatedRouter: ActivatedRoute, private router: Router,
                private doadorService: DoadorService, private tipoAmostraService: TipoAmostraService) {
        super();
        this.translate = translate;

        this.recuperarIdPacientePelaUrl();
        this.recuperarParametrosPrescricaoPelaUrl();

        this.uploader = new FileUploader({});
        this.uploaderJustificativa = new FileUploader({});

        this.data = [/[0-9]/,/[0-9]/, '\/', /[0-9]/,/[0-9]/, '\/', /[0-9]/,/[0-9]/,/[0-9]/,/[0-9]/]
    }

    limparCampos(event, formTipoAmostra:FormGroup){
        formTipoAmostra.get('ml').setValue('');

        if(event.target.checked){
            formTipoAmostra.get('ml').enable();
            formTipoAmostra.get('descricaoOutrosExames').enable();
        }else{
            formTipoAmostra.get('ml').disable();
            formTipoAmostra.get('descricaoOutrosExames').disable();
        }

        formTipoAmostra.get('ml').updateValueAndValidity();
        formTipoAmostra.get('ml').markAsPristine();
        formTipoAmostra.get('ml').markAsUntouched();
        formTipoAmostra.get('descricaoOutrosExames').setValue(null);
        formTipoAmostra.get('descricaoOutrosExames').updateValueAndValidity();
    }

    verificaSeTipoAmostraEOutros(formTipoAmostra:FormGroup){
        return formTipoAmostra.get("check").value && formTipoAmostra.get("idTipoAmostra").value == 6;
    }
    tipoAmostraGroup(tipoAmostra:TipoAmostra): FormGroup {
        return this.fb.group({
            check:[null, null],
            id: [null, null],
            ml: [{value:null, disabled:true}, Validators.required],
            descricao: [tipoAmostra.descricao, null],
            idTipoAmostra: [tipoAmostra.id],
            descricaoOutrosExames: [null, null]
        });
    }


    public get tipoAmostraFormArray(): FormArray{
        return this.formGroup.get('tiposAmostra') as FormArray;
    }

    /**
     * Recupera da URL do RMR do paciente.
     */
    private recuperarIdPacientePelaUrl(): void{
        RouterUtil.recuperarParametroDaActivatedRoute(this.activatedRouter, 'idPaciente').then(rmr => {
            this._rmr = rmr;
            Promise.resolve(this.headerPaciente).then(() => {
                this.headerPaciente.popularCabecalhoIdentificacaoPorPaciente(this._rmr);
            });
            this.pacienteService.verificarUltimaEvolucaoAtualizada(this._rmr).then(res => {
                if (!res) {
                    this.translate.get("mensagem.avisoNovaEvolucao").subscribe(res => {
                        this.messageBox.alert(res).
                            withCloseOption( () => this.router.navigateByUrl(HistoricoNavegacao.urlRetorno()))
                           .show();
                    });
                }
                else {
                    this.evolucao = new Evolucao().jsonToEntity(res);
                }
            });
        });
    }

    /**
     * Recupera os dados relativos a prescrição passados como parâmetro.
     * São eles: ID do doador, Peso do Paciente (utilizado para conferência
     * do cálculo de volume/Kg utilizado para prescrição com medula) e se é uma
     * prescrição de cordão ou não.
     */
    private recuperarParametrosPrescricaoPelaUrl(): void {
        RouterUtil.recuperarParametrosDaActivatedRoute(this.activatedRouter, ['idDoador', 'peso', 'tipoDoador', 'idMatch']).then(params => {
            if (!params["idDoador"] || !params["peso"] || !params['tipoDoador'] || !params['idMatch']) {
                this.translate.get("mensagem.erro.requisicao").subscribe(res => {
                    this.messageBox.alert(res)
                       .withTarget(this)
                       .withCloseOption(() => {
                           this.router.navigateByUrl(HistoricoNavegacao.urlRetorno());
                       })
                       .show();
                });
                return;
            }
            this._idDoador = Number(params["idDoador"]);
            this._idTipoDoador = Number(params["tipoDoador"]);
            this._idMatch = Number(params["idMatch"]);
            this._peso = Number(params["peso"]) / 10;

            this.doadorService.obterDetalheDoadorParaPrescricao(this._idDoador).then(result => {
                if (this._idTipoDoador == TiposDoador.NACIONAL) {
                    this._doador = new DoadorNacional().jsonToEntity(result);
                }
                else {
                    this._doador = new DoadorInternacional().jsonToEntity(result);
                }
            }, (error: ErroMensagem) => {
                ErroUtil.exibirMensagemErro(error, this.messageBox);
            });

            this.buildFormMedula();
            this.translate.get("mensagem.avisoDataPrazoExpirado",
               {"campo": "data da coleta", "prazo": PrescricaoMedulaComponent.PRAZO_DIAS_MAXIMO_PARA_COLETA_MEDULA_SEM_JUSTIFICATIVA}).subscribe(res => {
                this._avisoDataColetaPrazoExpirado = res;
            });

        });
    }

    /**
     * Constrói os FormControl para os campos referentes a prescrição de medula.
     * @author Bruno Sousa
     */
    buildFormMedula() {
        this.formGroup = this.fb.group({
            'dataLimiteWorkup1': [null, Validators.compose([Validators.required, ValidateData, ValidateDataMaiorOuIgualHoje])],
            'dataLimiteWorkup2': [null, Validators.compose([Validators.required, ValidateData, ValidateDataMaiorOuIgualHoje])],
            'dataColeta1': [null, Validators.compose([Validators.required, ValidateData, ValidateDataMaiorOuIgualHoje])],
            'dataColeta2': [null, Validators.compose([Validators.required, ValidateData, ValidateDataMaiorOuIgualHoje])],
            'fonteCelulaOpcao1': [null, Validators.required],
            'quantidadeTotalOpcao1': [null, null],
            'quantidadePorKgOpcao1': [null, null],
            'fonteCelulaOpcao2': [null, null],
            'quantidadeTotalOpcao2': [null, null],
            'quantidadePorKgOpcao2': [null, null],
            'fazerColeta': [false, null],
            'tiposAmostra':  new FormArray([])
        });
    }

    /**
     * faz a validação dos campos de data.
     *
     * @author Bruno Sousa
     * @private
     * @returns {boolean}
     * @memberof PrescricaoComponent
     */
    private validarDatas():boolean{
        let dataLimiteWorkup1: Date =
            PacienteUtil.obterDataSemMascara(this.form().get("dataLimiteWorkup1").value);

        let dataLimiteWorkup2: Date =
            PacienteUtil.obterDataSemMascara(this.form().get("dataLimiteWorkup2").value);

        let dataColeta1: Date =
            PacienteUtil.obterDataSemMascara(this.form().get("dataColeta1").value);

        let dataColeta2: Date =
            PacienteUtil.obterDataSemMascara(this.form().get("dataColeta2").value);

        let dataLimiteWorkupEDataColeta1Valida: boolean = this.verificarSeDatasInicioFimSaoValidas(
            dataLimiteWorkup1, dataColeta1, "dataLimiteWorkup1", this._formLabels['dataLimiteWorkupEDataColeta']);

        let dataLimiteWorkupEDataColeta2Valida: boolean = this.verificarSeDatasInicioFimSaoValidas(
                dataLimiteWorkup2, dataColeta2, "dataLimiteWorkup2", this._formLabels['dataLimiteWorkupEDataColeta']);

        let todasAsDatasIguais: boolean = this.verificarSeDatasInicioFimSaoIguais(dataLimiteWorkup1, dataColeta1, dataLimiteWorkup2, dataColeta2,
            'datasIguais', this._formLabels['peloMenosUmaDataDeveSerDiferente']);


        return dataLimiteWorkupEDataColeta1Valida && dataLimiteWorkupEDataColeta2Valida && !todasAsDatasIguais;
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

    private verificarSeDatasInicioFimSaoIguais(dataInicio: Date, dataFinal: Date,
        dataInicio2: Date, dataFinal2: Date,  nomeCampoReferenciaValidacao: string,
        mensagemErro: string): boolean {

        if (dataInicio && dataInicio2 && dataFinal && dataFinal2) {
            let dateMoment: DateMoment = DateMoment.getInstance();
            let datasInicioIguais: boolean = dateMoment.isSame(dataInicio, dataInicio2);
            let datasFinaisIguais: boolean = dateMoment.isSame(dataFinal, dataFinal2);
            if (datasInicioIguais && datasFinaisIguais) {
                this.formErrors[nomeCampoReferenciaValidacao] = mensagemErro;
                return true;
            }
        }
        return false;

    }

    /**
     * @inheritDoc
     */
    clearMensagensErro(form: FormGroup, nomeGroupPai?: string,fieldName?:string, index?: number) {
        super.clearMensagensErro(form, nomeGroupPai,fieldName, index);
        this.formErrors['arquivo'] = "";
        this.formErrors['arquivoJustificativa'] = "";
        this.formErrors['datasIguais'] = "";
    }

    private resetaFormulario() {
        this.clearMensagensErro(this.formGroup);
        this.formGroup.reset();
        this._arquivos = [];
    }

    /**
     * @inheritDoc
     */
    public validateForm():boolean{
        let valid: boolean = super.validateForm();

        let datasValidas: boolean = false;
        let arquivoJustificativaValido: boolean = false;
        let valoresOpcao1Validos: boolean = false;
        let valoresOpcao2Validos: boolean = false;

        if (valid) {
            datasValidas = this.validarDatas();
            if (datasValidas) {
                arquivoJustificativaValido = this.validarArquivoJustificativa();
            }
            valoresOpcao1Validos = this.validarQuantidadesDigitadas("fonteCelulaOpcao1",
                "quantidadePorKgOpcao1", "quantidadeTotalOpcao1");
            valoresOpcao2Validos = this.validarQuantidadesDigitadas("fonteCelulaOpcao2",
                "quantidadePorKgOpcao2", "quantidadeTotalOpcao2");

        }

        this.tipoAmostraFormArray.controls.forEach(tipoAmostraForm => {
            if(tipoAmostraForm.get("check").value){
                if(!tipoAmostraForm.get('ml').value){
                    this.markAsInvalid(tipoAmostraForm.get('ml'));
                }
                if(tipoAmostraForm.get('idTipoAmostra').value == this.ID_TIPO_AMOSTRA_OUTROS){
                    if(!tipoAmostraForm.get('descricaoOutrosExames').value){
                        this.markAsInvalid(tipoAmostraForm.get('descricaoOutrosExames'));
                    }
                }
            }
        })

        return valid && datasValidas && arquivoJustificativaValido && valoresOpcao1Validos && valoresOpcao2Validos;

    }

    /**
     * Valida se foi selecionado  arquivo de justificativa.
     * @author Bruno Sousa
     * @return boolean se foi validado com sucesso
     */
    private validarArquivoJustificativa():boolean{
        delete this.formErrors['arquivoJustificativa'];
        let valid: boolean = true;
        if (this.dataColetaComPrazoLimiteExpirado("dataColeta1") || this.dataColetaComPrazoLimiteExpirado("dataColeta2")) {
            if (this._arquivoJustificativa == null) {
                this.formErrors["arquivoJustificativa"] = this.customErros['nenhumaJustificativaSelecionada'];
                valid = false;
            }
        }

        return valid;
    }

    /**
     * Remove o arquivo da lista
     *
     * @author Bruno Sousa
     * @private
     * @param {string} nomeArquivo
     * @memberof PrescricaoComponent
     */
    public removerArquivo(nomeArquivo: string) {

        for(let i=0; i< this.uploader.queue.length; i++) {
            let arquivo:FileItem = this.uploader.queue[i];

            if (arquivo._file.name == nomeArquivo) {
                this.uploader.queue.splice(i, 1);
            }
        }

        for (let x = 0; x < this._arquivos.length; x++) {
            if (this._arquivos[x].nome == nomeArquivo) {
                this._arquivos.splice(x, 1);
            }
        }
    }

    /**
     * @inheritDoc
     */
    public form(): FormGroup {
        return this.formGroup;
    }

    /**
     * @inheritDoc
     */
    public preencherFormulario(entidade: SolicitacaoDTO): void {
        throw new Error("Method not implemented.");
    }

    /**
     * @inheritDoc
     */
    nomeComponente(): string {
        return "PrescricaoMedulaComponent"
    }

    /**
     * Método que faz a chamada ao back para salvar a prescrição de medula.
     *
     * @author Bruno Sousa
     * @memberof PrescricaoComponent
     */
    public salvarPrescricaoMedula() {
        if (this.validateForm()) {
            let dto: CriarPrescricaoMedulaDTO = new CriarPrescricaoMedulaDTO();
            dto.rmr = this._rmr;
            dto.idMatch = this._idMatch;
            dto.idTipoDoador = this._idTipoDoador;
            dto.dataLimiteWorkup1 = PacienteUtil.obterDataSemMascara(this.form().get("dataLimiteWorkup1").value);
            dto.dataLimiteWorkup2 = PacienteUtil.obterDataSemMascara(this.form().get("dataLimiteWorkup2").value);
            dto.dataColeta1 = PacienteUtil.obterDataSemMascara(this.form().get("dataColeta1").value);
            dto.dataColeta2 = PacienteUtil.obterDataSemMascara(this.form().get("dataColeta2").value);
            dto.fonteCelulaOpcao1 = new Number(this.form().get("fonteCelulaOpcao1").value).valueOf();
            dto.quantidadeTotalOpcao1 = this.form().get("quantidadeTotalOpcao1").value;
            dto.quantidadePorKgOpcao1 = this.form().get("quantidadePorKgOpcao1").value;
            if(this.form().get("fonteCelulaOpcao2").value || Number(this.form().get("fonteCelulaOpcao2").value) != 0){
                dto.fonteCelulaOpcao2 = new Number(this.form().get("fonteCelulaOpcao2").value).valueOf();
                dto.quantidadeTotalOpcao2 = this.form().get("quantidadeTotalOpcao2").value;
                dto.quantidadePorKgOpcao2 = this.form().get("quantidadePorKgOpcao2").value;
            }

            this.tipoAmostraFormArray.controls.forEach(t => {
                if(t.get('check').value){
                    let tipoAmostraPres: TipoAmostraPrescricao = new TipoAmostraPrescricao();
                    tipoAmostraPres.tipoAmostra = new TipoAmostra();
                    tipoAmostraPres.tipoAmostra.id = t.get('idTipoAmostra').value;
                    tipoAmostraPres.ml = t.get('ml').value;
                    tipoAmostraPres.descricaoOutrosExames = t.get('descricaoOutrosExames').value;
                    dto.tiposAmostraPrescricao.push(tipoAmostraPres);
                }
            });

            dto.fazerColeta = this.form().get("fazerColeta").value;

            this.prescricaoService.salvarPrescricaoMedula(
                    dto, this._arquivoJustificativa ? this.uploaderJustificativa.queue[0] : null, this.uploader.queue).then(res => {

                this.messageBox.alert(res)
                   .withTarget(this)
                   .withCloseOption(() => {
                       this.router.navigateByUrl(HistoricoNavegacao.urlRetorno());
                   })
                   .show();

            }, (erro: ErroMensagem) => {
                ErroUtil.exibirMensagemErro(erro, this.messageBox);
            })
        }
    }

    /**
     * Get do campo _fontesCelulas
     *
     * @readonly
     * @type {FonteCelula[]}
     * @memberof PrescricaoComponent
     */
    public get listaFonteCelula(): FonteCelula[] {
        return this._fontesCelulas
    }

    /**
     * Método que é chamado no onchange do campo do formulário fonteCelula
     *
     * @author Bruno Sousa
     * @param {*} evento
     * @memberof PrescricaoComponent
     */
    public mudouFonteCelulaOpcao1(evento: any) {
        this.form().get("quantidadeTotalOpcao1").setValue(null);
        this.form().get("quantidadePorKgOpcao1").setValue(null);
        if (this.fonteCelulaPreenchida("fonteCelulaOpcao1")) {
            this.setFieldRequired(this.form(), "quantidadeTotalOpcao1");
            this.setFieldRequired(this.form(), "quantidadePorKgOpcao1");
        }
        else {
            this.resetFieldRequired(this.form(), "quantidadeTotalOpcao1");
            this.resetFieldRequired(this.form(), "quantidadePorKgOpcao1");
        }
    }

    /**
     * Retorna se o campo de quantidade pode ser exibido.
     *
     * @author Bruno Sousa
     * @returns {boolean}
     * @memberof PrescricaoComponent
     */
    public fonteCelulaPreenchida(campo: string): boolean {
        return this.existsField(campo) &&
           this.form().get(campo).value != null && this.form().get(campo).value != "";
    }

    public get ehFonteCelulaOpcao1Medula(): boolean {
        return this.form().get("fonteCelulaOpcao1").value == new String(FontesCelulas.MEDULA_OSSEA.id).toString();
    }

    public get ehFonteCelulaOpcao1SanguePeriferico(): boolean {
        return this.form().get("fonteCelulaOpcao1").value == new String(FontesCelulas.SANGUE_PERIFERICO.id).toString();
    }

    /**
     * Valida o valores digitados de quantidade para a opção 1.
     *
     * @author Bruno Sousa
     * @memberof PrescricaoComponent
     */
    private validarQuantidadesDigitadas(campoFonteCelula: string,  campoPorKg: string, campoTotal: string): boolean {
        if (this.fonteCelulaPreenchida(campoFonteCelula)) {
            let valorPorKg: number = this.form().get(campoPorKg).value
            /* if (valor.indexOf(",") > 0 ) {
                valor = valor.replace(",", ".");
            }
            let valorPorKg: number = new Number(valor).valueOf(); */
            let total = valorPorKg * this._peso;

            let valorTotal: number = this.form().get(campoTotal).value;
            /* if (valor.indexOf(",") > 0 ) {
                valor = valor.replace(",", ".");
            }
            let valorTotal: number = new Number(valor).valueOf(); */

            let resultado: boolean =  total == valorTotal;
            if (!resultado) {
                this.formErrors[campoTotal] = this.customErros['quantidadeTotalInvalida'];
            }

            return resultado;
        }
        return true;
    }

    public get arquivos(): Arquivo[] {
        return this._arquivos;
    }

    /**
     * Valida se os dias entre a data de coleta e a data de hoje é menor que 30 dias, para medula,
     * e 10 dias para cordão.
     * Caso seja menor é exibida uma mensagem de alerta
     *
     * @author Bruno Sousa
     * @param {string} dataColeta
     */
    private dataColetaComPrazoLimiteExpirado(dataColeta: string): boolean {
        let diferenca: number = this.diferencaEmDiasDaDataAtual(dataColeta);
        let prazo: number = PrescricaoMedulaComponent.PRAZO_DIAS_MAXIMO_PARA_COLETA_MEDULA_SEM_JUSTIFICATIVA;

        return diferenca && diferenca <= prazo;
    }

    /**
     * Exibe a mensagem de erro, de acordo com a tipo de doador informador (medula ou cordão).
     *
     * @param dataColeta data da coleta a ser considerada no cálculo de exibição da mensagem.
     */
    public exibeMensagemAlertaPrazoExpirado(dataColeta: string) {
        if (this.dataColetaComPrazoLimiteExpirado(dataColeta)) {
            this.messageBox.alert(this._avisoDataColetaPrazoExpirado).show();
        }
    }

    /**
     * Calcula a diferença em dias de um campo do fomulário com a data atual.
     *
     * @author Bruno Sousa
     * @private
     * @param {string} dataColeta
     * @returns {number}
     * @memberof PrescricaoComponent
     */
    private diferencaEmDiasDaDataAtual(dataColeta: string): number {
        if (this.form().get(dataColeta).valid) {
            let dataColeta1: Date =
                PacienteUtil.obterDataSemMascara(this.form().get(dataColeta).value);

            if (dataColeta1 && !DateMoment.getInstance().isInvalid(dataColeta1)) {
                return  DateMoment.getInstance().diffDays(dataColeta1, new Date());
            }
            return null;
        }
        return null;
    }

    /**
     * Get do campo _fontesCelulas removendo a fonte da primeira opção
     *
     * @readonly
     * @type {FonteCelula[]}
     * @memberof PrescricaoComponent
     */
    public get listaFonteCelulaOpcao2(): FonteCelula[] {
        if (this.fonteCelulaPreenchida("fonteCelulaOpcao1")) {
            return this._fontesCelulas.filter(fonteCelula => {
               return new String(fonteCelula.id).toString() != this.form().get("fonteCelulaOpcao1").value;
            })
        }
        return this._fontesCelulas;
    }

    /**
     * Método que é chamado no onchange do campo do formulário fonteCelula opcao 2
     *
     * @author Bruno Sousa
     * @param {*} evento
     * @memberof PrescricaoComponent
     */
    public mudouFonteCelulaOpcao2(evento: any) {
        this.form().get("quantidadeTotalOpcao2").setValue(null);
        this.form().get("quantidadePorKgOpcao2").setValue(null);
        if (this.fonteCelulaPreenchida("fonteCelulaOpcao2")) {
            this.setFieldRequired(this.form(), "quantidadeTotalOpcao2");
            this.setFieldRequired(this.form(), "quantidadePorKgOpcao2");
        }
        else {
            this.resetFieldRequired(this.form(), "quantidadeTotalOpcao2");
            this.resetFieldRequired(this.form(), "quantidadePorKgOpcao2");
        }
    }

    public get ehFonteCelulaOpcao2Medula(): boolean {
        return this.form().get("fonteCelulaOpcao2").value == new String(FontesCelulas.MEDULA_OSSEA.id).toString();
    }

    public get ehFonteCelulaOpcao2SanguePeriferico(): boolean {
        return this.form().get("fonteCelulaOpcao2").value == new String(FontesCelulas.SANGUE_PERIFERICO.id).toString();
    }

    public get possuiArquivoJustificativa(): boolean {
        return this._arquivoJustificativa != null;
    }

    public get nomeArquivoJustificativa(): string {
        if (this.possuiArquivoJustificativa) {
            return this._arquivoJustificativa.nome.toString();
        }
        return "";
    }

    public removerArquivoJustificativa() {
        this.uploaderJustificativa.clearQueue();
        this._arquivoJustificativa = null;
    }

    public get mostrarInputArquivoJustificativa(): boolean {
        return this.dataColetaComPrazoLimiteExpirado("dataColeta1") || this.dataColetaComPrazoLimiteExpirado("dataColeta2");
    }

    public get doador(): Doador {
        return this._doador;
    }

    public obterPesoFormatado(): string {
        if (this._doador && this._doador.peso) {
            return new String(PacienteUtil.arredondar(new String(this._doador.peso).valueOf(), 1)).valueOf();
        }
        return "0,0"
    }


}
