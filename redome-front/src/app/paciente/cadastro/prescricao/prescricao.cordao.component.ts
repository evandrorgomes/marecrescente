import { Component, OnInit, ViewChild } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from "@ngx-translate/core";
import { TipoAmostraService } from 'app/doador/solicitacao/tipoamostra.service';
import { EnderecoContatoCentroTransplante } from 'app/shared/model/endereco.contato.centro.transplante';
import { FileItem, FileLikeObject, FileUploader, FileUploaderOptions } from "ng2-file-upload";
import { CentroTransplanteService } from "../../../admin/centrotransplante/centrotransplante.service";
import { CordaoInternacional } from '../../../doador/cordao.internacional';
import { CordaoNacional } from "../../../doador/cordao.nacional";
import { DoadorService } from '../../../doador/doador.service';
import { ICordao } from "../../../doador/ICordao";
import { PrescricaoService } from '../../../doador/solicitacao/prescricao.service';
import { BaseForm } from "../../../shared/base.form";
import { EnderecoContatoComponent } from "../../../shared/component/endereco/endereco.contato.component";
import { Configuracao } from "../../../shared/dominio/configuracao";
import { DominioService } from "../../../shared/dominio/dominio.service";
import { CriarPrescricaoCordaoDTO } from "../../../shared/dto/criar.prescricao.cordao.dto";
import { TiposDoador } from "../../../shared/enums/tipos.doador";
import { ErroMensagem } from "../../../shared/erromensagem";
import { HistoricoNavegacao } from '../../../shared/historico.navegacao';
import { Modal } from '../../../shared/modal/factory/modal.factory';
import { MessageBox } from '../../../shared/modal/message.box';
import { PacienteUtil } from '../../../shared/paciente.util';
import { ArrayUtil } from '../../../shared/util/array.util';
import { DateMoment } from '../../../shared/util/date/date.moment';
import { ErroUtil } from "../../../shared/util/erro.util";
import { NumberUtil } from '../../../shared/util/number.util';
import { RouterUtil } from "../../../shared/util/router.util";
import { StringUtil } from "../../../shared/util/string.util";
import { ValidateData, ValidateDataMaiorOuIgualHoje } from '../../../validators/data.validator';
import { SolicitacaoDTO } from "../../busca/analise/solicitacao.dto";
import { HeaderPacienteComponent } from '../../consulta/identificacao/header.paciente.component';
import { PacienteService } from '../../paciente.service';
import { Evolucao } from '../evolucao/evolucao';
import { Arquivo } from "../exame/arquivo";

/**
 * Component responsavel exames
 * @author Filipe Paes
 * @class PrescricaoComponent
 * @extends {BaseForm}
 * @implements {OnDestroy}
 */
@Component({
    selector: 'prescricao-cordao',
    moduleId: module.id,
    templateUrl: './prescricao.cordao.component.html',
})
export class PrescricaoCordaoComponent extends BaseForm<SolicitacaoDTO> implements OnInit {

    // Constantes da classe
    private static PRAZO_DIAS_MAXIMO_PARA_COLETA_CORDAO_SEM_JUSTIFICATIVA: number = 20;

    @ViewChild('headerPaciente')
    private headerPaciente: HeaderPacienteComponent;

    @ViewChild('enderecoEntrega')
    private enderecoContatoComponent: EnderecoContatoComponent;

    // Chaves que envolvem o upload do arquivo
    private CONFIG_UPLOAD_CHAVES: String[] = ["extensaoArquivoPrescricao",
        "quantidadeMaximaArquivosPrescricao",
        "tamanhoArquivoPrescricaoEmByte"];

    // Lista de configurações referente ao upload de arquivo.
    public configuracaoUpload: Configuracao[];

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
    private _cordao: ICordao;
    private _idTipoDoador: number;
    private _idMatch: number;
    private _peso: number;

    private _avisoDataColetaPrazoExpirado: string = "";

    public enderecoEntrega: EnderecoContatoCentroTransplante;

    evolucao: Evolucao;

    constructor(private fb: FormBuilder, private dominioService: DominioService,
        private prescricaoService: PrescricaoService, translate: TranslateService,
        private pacienteService: PacienteService, private messageBox: MessageBox,
        private activatedRouter: ActivatedRoute, private router: Router,
        private doadorService: DoadorService, private tipoAmostraService: TipoAmostraService,
        private centroTransplanteServce: CentroTransplanteService) {
        super();
        this.translate = translate;

        this.recuperarIdPacientePelaUrl();
        this.recuperarParametrosPrescricaoPelaUrl();

        this.uploader = new FileUploader({});
        this.uploaderJustificativa = new FileUploader({});

        this.data = [/[0-9]/,/[0-9]/, '\/', /[0-9]/,/[0-9]/, '\/', /[0-9]/,/[0-9]/,/[0-9]/,/[0-9]/]
    }

    ngOnInit() {
        this.translate.get("prescricaoCordaoComponent").subscribe(res => {
            this._formLabels = res;
            this.criarMensagensErro(this.formGroup);
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
                this.messageBox.alert(error.mensagem.toString())
                   .withTarget(this)
                   .withCloseOption(() => this.router.navigateByUrl(HistoricoNavegacao.urlRetorno()))
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
                        this.messageBox.alert(res)
                           .withCloseOption(() => this.router.navigateByUrl(HistoricoNavegacao.urlRetorno()))
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
    private recuperarParametrosPrescricaoPelaUrl(): void{
        RouterUtil.recuperarParametrosDaActivatedRoute(this.activatedRouter, ['idDoador', 'peso', 'tipoDoador', 'idMatch', 'idCentroTransplante']).then(params => {
            if (!params["idDoador"] || !params["peso"] || !params['tipoDoador'] || !params['idMatch'] || !params['idCentroTransplante']) {
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
            let idCentroTransplante = Number(params['idCentroTransplante']);

            this.buildFormCordao();
            this.translate.get("mensagem.avisoDataPrazoExpirado",
               {"campo": "data para receber o cordão",  "prazo": PrescricaoCordaoComponent.PRAZO_DIAS_MAXIMO_PARA_COLETA_CORDAO_SEM_JUSTIFICATIVA}).subscribe(res => {
                this._avisoDataColetaPrazoExpirado = res;
            });

            this.doadorService.obterDetalheDoadorParaPrescricao(this._idDoador).then(result => {
               if (this._idTipoDoador == TiposDoador.CORDAO_NACIONAL) {
                  this._cordao = new CordaoNacional().jsonToEntity(result);
               }
               else {
                  this._cordao = new CordaoInternacional().jsonToEntity(result);
               }
               this.calcularCelulasCordaoPorKg(this._cordao);
            }, (error: ErroMensagem) => {
               ErroUtil.exibirMensagemErro(error, this.messageBox);
            });

            this.centroTransplanteServce.obterEnderecoEntrega(idCentroTransplante).then(res => {
                this.enderecoEntrega = new EnderecoContatoCentroTransplante().jsonToEntity(res);
                this.enderecoContatoComponent.preencherFormulario(this.enderecoEntrega);
            }, (error: ErroMensagem) => {
                ErroUtil.exibirMensagemErro(error, this.messageBox);
            });

        });

    }

    buildFormCordao() {
        this.formGroup = this.fb.group({
            'dataReceber1': [null, Validators.compose([Validators.required, ValidateData, ValidateDataMaiorOuIgualHoje])],
            'dataReceber2': [null, Validators.compose([ValidateData, ValidateDataMaiorOuIgualHoje])],
            'dataReceber3': [null, Validators.compose([ValidateData, ValidateDataMaiorOuIgualHoje])],
            'dataInfusao': [null, Validators.compose([Validators.required, ValidateData, ValidateDataMaiorOuIgualHoje])],
            'armazenaCordao': [null, Validators.required],
            'nomeContatoParaReceber': [null, Validators.required],
            'nomeContatoUrgente': [null, null],
            'codigoAreaUrgente': [null, null],
            'telefoneUrgente': [null, null],
            'quantidadeTotalOpcao1': [null, null],
            'quantidadePorKgOpcao1': [null, null],
            'quantidadeTotalOpcao2': [null, null],
            'quantidadePorKgOpcao2': [null, null]
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
        let dataInfusao: Date =
            PacienteUtil.obterDataSemMascara(this.form().get("dataInfusao").value);

        let dataReceber1: Date =
            PacienteUtil.obterDataSemMascara(this.form().get("dataReceber1").value);

        let dataReceber2: Date =
           PacienteUtil.obterDataSemMascara(this.form().get("dataReceber2").value);

        let dataReceber3: Date =
           PacienteUtil.obterDataSemMascara(this.form().get("dataReceber3").value);


        let dataInfusaoEDataReceber1Valida: boolean = this.verificarSeDatasInicioFimSaoValidas(
            dataInfusao, dataReceber1, "dataInfusao", this._formLabels['dataInfusaoEDataReceber']);

        let dataInfusaoEDataReceber2Valida: boolean = this.verificarSeDatasInicioFimSaoValidas(
           dataInfusao, dataReceber2, "dataInfusao", this._formLabels['dataInfusaoEDataReceber']);

        let dataInfusaoEDataReceber3Valida: boolean = this.verificarSeDatasInicioFimSaoValidas(
           dataInfusao, dataReceber3, "dataInfusao", this._formLabels['dataInfusaoEDataReceber']);


        let todasAsDatasIguais: boolean = this.verificarSeDatasSaoIguais(dataReceber1, dataReceber2, dataReceber3,
            'datasIguais', this._formLabels['peloMenosUmaDataDeveSerDiferente']);


        return dataInfusaoEDataReceber1Valida && dataInfusaoEDataReceber2Valida && dataInfusaoEDataReceber3Valida && !todasAsDatasIguais;
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

            if(dateMoment.isDateBefore(dataInicio, dataFinal)){
                this.forceError(nomeCampoReferenciaValidacao, mensagemErro);
                return false;
            }
            return true;
        }
        return false;
    }

    private verificarSeDatasSaoIguais(data1: Date, data2: Date,
        data3: Date,  nomeCampoReferenciaValidacao: string,  mensagemErro: string): boolean {

        if (data1) {
            let dateMoment: DateMoment = DateMoment.getInstance();

            let data1IgualData2: boolean = !data2 ? false : dateMoment.isSame(data1, data2);
            let data1IgualData3: boolean = !data3 ? false : dateMoment.isSame(data1, data3);
            let data2IgualData3: boolean = !data2 || !data3 ? false : dateMoment.isSame(data2, data3);

            if (data1IgualData2 && data1IgualData3 && data2IgualData3) {
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

        let datasValidas: boolean = this.validarDatas();
        let arquivoJustificativaValido: boolean = this.validarArquivoJustificativa();

        return valid && datasValidas && arquivoJustificativaValido;

    }

    /**
     * Valida se foi selecionado  arquivo de justificativa.
     * @author Bruno Sousa
     * @return boolean se foi validado com sucesso
     */
    private validarArquivoJustificativa():boolean{
        delete this.formErrors['arquivoJustificativa'];
        let valid: boolean = true;
        if (this.dataComPrazoLimiteExpirado("dataReceber1") || this.dataComPrazoLimiteExpirado("dataReceber2")
           || this.dataComPrazoLimiteExpirado("dataReceber3")) {
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
        return "PrescricaoCordaoComponent"
    }

    /**
     * Método que faz a chamada ao back para salvar a prescrição de cordão.
     *
     * @author Bruno Sousa
     * @memberof PrescricaoComponent
     */
    public salvarPrescricaoCordao() {
        if (this.validateForm()) {
            let dto: CriarPrescricaoCordaoDTO = new CriarPrescricaoCordaoDTO();
            dto.rmr = this._rmr;
            dto.idTipoDoador = this._idTipoDoador;
            dto.idMatch= this._idMatch;
            dto.dataReceber1 = PacienteUtil.obterDataSemMascara(this.form().get("dataReceber1").value);
            dto.dataReceber2 = PacienteUtil.obterDataSemMascara(this.form().get("dataReceber2").value);
            dto.dataReceber3 = PacienteUtil.obterDataSemMascara(this.form().get("dataReceber3").value);
            dto.dataInfusao =  PacienteUtil.obterDataSemMascara(this.form().get("dataInfusao").value);
            dto.nomeContatoParaReceber = this.form().get("nomeContatoParaReceber").value;
            dto.nomeContatoUrgente = this.form().get("nomeContatoUrgente").value;
            dto.codigoAreaUrgente = this.form().get("codigoAreaUrgente").value;
            dto.telefoneUrgente = this.form().get("telefoneUrgente").value;
            dto.armazenaCordao = this.form().get("armazenaCordao").value;

            this.prescricaoService.salvarPrescricaoCordao(
                    dto, this._arquivoJustificativa ? this.uploaderJustificativa.queue[0] : null, this.uploader.queue).then(res => {

                this.messageBox.alert(res)
                   .withCloseOption(() => this.router.navigateByUrl(HistoricoNavegacao.urlRetorno()))
                   .show();

            }, (erro: ErroMensagem) => {
                this.exibirMensagemErro(erro);
            })
        }

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
    private dataComPrazoLimiteExpirado(formField: string): boolean {
        let diferenca: number = this.diferencaEmDiasDaDataAtual(formField);
        let prazo: number = PrescricaoCordaoComponent.PRAZO_DIAS_MAXIMO_PARA_COLETA_CORDAO_SEM_JUSTIFICATIVA;
        return diferenca && diferenca <= prazo;
    }

    /**
     * Exibe a mensagem de erro, de acordo com a tipo de doador informador (medula ou cordão).
     *
     * @param data data da coleta a ser considerada no cálculo de exibição da mensagem.
     */
    public exibeMensagemAlertaPrazoExpirado(formField: string) {
        if (this.dataComPrazoLimiteExpirado(formField)) {
            let modalAlerta: Modal = this.messageBox.alert(this._avisoDataColetaPrazoExpirado);
            modalAlerta.show();
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
    private diferencaEmDiasDaDataAtual(formField: string): number {
        if (this.form().get(formField) && this.form().get(formField).valid) {
            let dataColeta1: Date =
                PacienteUtil.obterDataSemMascara(this.form().get(formField).value);

            if (dataColeta1 && !DateMoment.getInstance().isInvalid(dataColeta1)) {
                return  DateMoment.getInstance().diffDays(dataColeta1, new Date());
            }
            return null;
        }
        return null;
    }

    private exibirMensagemErro(error: ErroMensagem) {
        let erros: string[] = [];
        let index: number = 0;
        error.listaCampoMensagem.forEach(obj => {
            erros[index++] = obj.mensagem;
        })
        let modalAlerta: Modal = this.messageBox.alert(ArrayUtil.join(erros, ", "));
        modalAlerta.show();
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
        return this.dataComPrazoLimiteExpirado("dataReceber1") || this.dataComPrazoLimiteExpirado("dataReceber2")
           || this.dataComPrazoLimiteExpirado("dataReceber3");
    }

    private calcularCelulasCordaoPorKg(cordao: ICordao): void{
        this.setPropertyValue("quantidadeTotalOpcao1", cordao.quantidadeTotalTCN);
        this.setPropertyValue("quantidadeTotalOpcao2", cordao.quantidadeTotalCD34);
        this.setPropertyValue("quantidadePorKgOpcao1", NumberUtil.round(cordao.quantidadeTotalTCN / this._peso, 2));
        this.setPropertyValue("quantidadePorKgOpcao2", NumberUtil.round(cordao.quantidadeTotalCD34 / this._peso, 2));
    }

    public obterPesoFormatado(): string {
        if (this._cordao && this._cordao.peso) {
            return new String(PacienteUtil.arredondar(new String(this._cordao.peso).valueOf(), 1)).valueOf();
        }
        return "0,0"
    }

    public get cordao(): ICordao {
        return this._cordao;
    }

}
