import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { CentroTransplanteService } from 'app/admin/centrotransplante/centrotransplante.service';
import { BuildForm } from 'app/shared/buildform/build.form';
import { NumberControl } from 'app/shared/buildform/controls/number.control';
import { StringControl } from 'app/shared/buildform/controls/string.control';
import { EnderecoContatoComponent } from 'app/shared/component/endereco/endereco.contato.component';
import { ErroMensagem } from 'app/shared/erromensagem';
import { HistoricoNavegacao } from 'app/shared/historico.navegacao';
import { DataUtil } from 'app/shared/util/data.util';
import { DateMoment } from 'app/shared/util/date/date.moment';
import { DateTypeFormats } from 'app/shared/util/date/date.type.formats';
import { ErroUtil } from 'app/shared/util/erro.util';
import { ValidateDataMaiorOuIgualHoje, ValidateHoraValida } from 'app/validators/data.validator';
import { MessageBox } from '../../../../shared/modal/message.box';
import { PedidoColeta } from '../../../consulta/coleta/pedido.coleta';
import { PedidoColetaService } from '../../../consulta/coleta/pedido.coleta.service';
import { HeaderDoadorComponent } from '../../../consulta/header/header.doador.component';
import { EnderecoContatoCentroTransplante } from 'app/shared/model/endereco.contato.centro.transplante';
import { PrescricaoService } from 'app/doador/solicitacao/prescricao.service';
import { VisualizarPrescricaoDataEventService } from 'app/shared/component/visualizar/prescricao/visualizar.prescricao.data.event.service';
import { PrescricaoDTO } from 'app/shared/dto/prescricao.dto';
import { PrescricaoEvolucaoDTO } from 'app/shared/dto/prescricao.evolucao.dto';

/**
 * Classe que representa o componente para agendar pedido de coleta.
 * @author ergomes
 */
@Component({
    selector: "agendar-pedido-coleta",
    templateUrl: './agendar.pedido.coleta.component.html'
})
export class AgendarPedidoColetaComponent implements OnInit {

    @ViewChild('headerDoador')
    private headerDoador: HeaderDoadorComponent;

    @ViewChild(EnderecoContatoComponent)
    private enderecoGcsfComponent: EnderecoContatoComponent;

    @ViewChild(EnderecoContatoComponent)
    private enderecoInternacaoComponent: EnderecoContatoComponent;

    private _pedidoColeta: PedidoColeta;
    private _enderecos: EnderecoContatoCentroTransplante[] = [];

    private _idPedidoColeta: number;
    private _estaEmJejum: boolean = false;
    private _ehMedula: boolean = true;

    private _rmr: number;
    private _prescricaoEvolucao: PrescricaoEvolucaoDTO;

    private _agendamentoForm: BuildForm<any>;


    private _opcoesMedulaSangue:Number[] = [0,1];
    private _labelsMedulaSangue:String[] = [];
    private _opcoesSimNao:String[] = ["false","true"];
    private _labelsSimNao:String[] = [];

    public maskData: Array<string | RegExp>
    public maskHora: Array<string | RegExp>

    private customErros: any = {maiorDataColeta: ""}

    constructor(
        private translate: TranslateService,
        private router: Router,
        private messageBox: MessageBox,
        private activatedRouter: ActivatedRoute,
        private pedidoColetaService: PedidoColetaService,
        private centroTransplanteService: CentroTransplanteService,
        protected prescricaoService: PrescricaoService,
        private visualizarPrescricaoDataEventService: VisualizarPrescricaoDataEventService) {

        this.buildForm();

        this.maskData = [/[0-9]/, /[0-9]/, '\/', /[0-9]/, /[0-9]/, '\/', /[0-9]/, /[0-9]/, /[0-9]/, /[0-9]/]
        this.maskHora = [/[0-2]/, /\d/, ':', /[0-5]/, /\d/]
    }

    /**
     * Inicializa a classe com os dados buscando no serviço REST
     *
     * @memberOf AgendarPedidoColetaComponent
     */
    ngOnInit(): void {

      this.translate.get(["textosGenericos.sim", "textosGenericos.nao"]).subscribe(res => {
        this._labelsSimNao[0] = res['textosGenericos.nao'];
        this._labelsSimNao[1] = res['textosGenericos.sim'];
      });

      this.translate.get(["textosGenericos.medula", "textosGenericos.sangueperiferico"]).subscribe(res => {
        this._labelsMedulaSangue[0] = res['textosGenericos.medula'];
        this._labelsMedulaSangue[1] = res['textosGenericos.sangueperiferico'];
      });

      this.translate.get("mensagem.erro").subscribe(res => {
        this.customErros["maiorDataColeta"] = res.maiorDataColeta;
      });

      this.activatedRouter.queryParamMap.subscribe(queryParam => {
        if (queryParam.keys.length != 0) {
          this._idPedidoColeta = Number(queryParam.get('idPedidoColeta'));
          let idDoador = Number(queryParam.get('idDoador'));
          let idPrescricao = Number(queryParam.get('idPrescricao'));

          this.popularPrescricao(idPrescricao);

          this.pedidoColetaService.obterPedidoColeta(this._idPedidoColeta).then(res => {
              this._pedidoColeta = new PedidoColeta().jsonToEntity(res);

              this.carregarEnderecosColeta();

              Promise.resolve(this.headerDoador).then(() => {
                this.headerDoador.clearCache();
                this.headerDoador.popularCabecalhoIdentificacaoPorDoador(idDoador);
              });
          },
          (error: ErroMensagem) => {
            ErroUtil.exibirMensagemErro(error, this.messageBox);
          });
        }
      });
    }

    /**
     * * Método que constrói o formulário
     * @returns void
     */
    buildForm() {

      this._agendamentoForm = new BuildForm()
        .add(new NumberControl('tipoProduto', 0, [Validators.required]))
        .add(new StringControl('horaColeta', [ValidateHoraValida, Validators.required]))
        .add(new StringControl('dataInicioGcsf', [ValidateDataMaiorOuIgualHoje, Validators.required]))
        .add(new StringControl('horaInicioGcsf', [ValidateHoraValida, Validators.required]))
        .add(new StringControl('horaInternacao', [ValidateHoraValida, Validators.required]))
        .add(new StringControl('gcsfSetorAndar', [Validators.required]))
        .add(new StringControl('gcsfProcurarPor', [Validators.required]))
        .add(new StringControl('internacaoSetorAndar', [Validators.required]))
        .add(new StringControl('internacaoProcurarPor', [Validators.required]))
        .add(new StringControl('estaEmJejum', "false", [Validators.required]))
        .add(new NumberControl('quantasHorasEmJejum', [Validators.required]))
        .add(new StringControl('estaTotalmenteEmJejum', "false", [Validators.required]))
        .add(new StringControl('informacoesAdicionais'));
    }

    public form(): FormGroup {
      return this._agendamentoForm.form;
    }
    public preencherFormulario(entidade: PedidoColeta): void {
      throw new Error("Method not implemented.");
    }
    nomeComponente(): string {
      return "AgendarPedidoColetaComponent";
    }

    isDoadorEmJejum(value: string): void {
      value == 'true' ? this._estaEmJejum = true: this._estaEmJejum = false;
    }

    isMedula(value: number): void {
      value == 0 ? this._ehMedula = true: this._ehMedula = false;
      this.carregarEnderecosColeta();
    }

    carregarEnderecosColeta(){

      this.centroTransplanteService.obterCentroTransplante(this._pedidoColeta.centroColeta).then(res => {
        this._enderecos = res.enderecos;

        if(this._ehMedula){
            this.enderecoInternacaoComponent.clearForm();
            let enderecoInternacao: EnderecoContatoCentroTransplante = this._enderecos.filter(endereco => endereco.internacao)[0];
            if (enderecoInternacao) {
              this.enderecoInternacaoComponent.preencherFormulario(enderecoInternacao);
          }
          else {
              this.enderecoInternacaoComponent.setValoresPadroes();
              this.enderecoInternacaoComponent.configEndNacionalForm();
          }
        }else{
          this.enderecoGcsfComponent.clearForm();
          let enderecoGcsf: EnderecoContatoCentroTransplante = this._enderecos.filter(endereco => endereco.gcsf)[0];
          if (enderecoGcsf) {
            this.enderecoGcsfComponent.preencherFormulario(enderecoGcsf);
          }
          else {
              this.enderecoGcsfComponent.setValoresPadroes();
              this.enderecoGcsfComponent.configEndNacionalForm();
          }
        }
      },
      (error: ErroMensagem) => {
        ErroUtil.exibirMensagemErro(error, this.messageBox);
      });
    }

    voltarConsulta(){
      this.router.navigateByUrl(HistoricoNavegacao.urlRetorno())
     }

    salvarColeta(){
      if(this.validateForm()){

        let pedidoColeta: PedidoColeta = new PedidoColeta();
        let objForm = this._agendamentoForm.value;

        pedidoColeta.tipoProdudo = objForm.tipoProduto;
        if(this._ehMedula){
          pedidoColeta.dataInternacao = DataUtil.toDate(this._pedidoColeta.dataInternacaoFormatada + objForm.horaInternacao, DateTypeFormats.DATE_TIME);
          pedidoColeta.internacaoSetorAndar = objForm.internacaoSetorAndar;
          pedidoColeta.internacaoProcurarPor = objForm.internacaoProcurarPor;
        }else{
          pedidoColeta.dataInicioGcsf = DataUtil.toDate(objForm.dataInicioGcsf + objForm.horaInicioGcsf, DateTypeFormats.DATE_TIME);
          pedidoColeta.gcsfSetorAndar = objForm.gcsfSetorAndar;
          pedidoColeta.gcsfProcurarPor = objForm.gcsfProcurarPor;
        }
        pedidoColeta.dataColeta = DataUtil.toDate(this._pedidoColeta.dataColetaFormatada + objForm.horaColeta, DateTypeFormats.DATE_TIME);
        pedidoColeta.estaEmJejum = objForm.estaEmJejum == 'true'? true:false;
        pedidoColeta.quantasHorasEmJejum = objForm.quantasHorasEmJejum;
        pedidoColeta.estaTotalmenteEmJejum = objForm.estaTotalmenteEmJejum == 'true'? true:false;
        pedidoColeta.informacoesAdicionais = objForm.informacoesAdicionais;

        this.pedidoColetaService.agendarColeta(this._idPedidoColeta, pedidoColeta).then(res => {
          this.messageBox.alert(res)
            .withTarget(this)
            .withCloseOption((target?: any) => {
              this.router.navigateByUrl(HistoricoNavegacao.urlRetorno());
            })
            .show();
        },
        (error: ErroMensagem) => {
          ErroUtil.exibirMensagemErro(error, this.messageBox);
        });
      }
    }

    validateForm():boolean{

      let formularioValido: boolean = this._agendamentoForm.valid;
      let objForm = this._agendamentoForm.value;
      let dateMoment: DateMoment = DateMoment.getInstance();

      let estaEmJejum = objForm.estaEmJejum == 'true'? true:false;
      if(!estaEmJejum){
          this._agendamentoForm.getChildAsBuildFormControl("quantasHorasEmJejum").makeOptional();
          this._agendamentoForm.getChildAsBuildFormControl("estaTotalmenteEmJejum").makeOptional();
      }

      let dataColeta = DataUtil.toDate(this._pedidoColeta.dataColetaFormatada, DateTypeFormats.DATE_ONLY);
      if(this._ehMedula){
        this._agendamentoForm.getChildAsBuildFormControl("dataInicioGcsf").makeOptional();
        this._agendamentoForm.getChildAsBuildFormControl("horaInicioGcsf").makeOptional();
        this._agendamentoForm.getChildAsBuildFormControl("gcsfSetorAndar").makeOptional();
        this._agendamentoForm.getChildAsBuildFormControl("gcsfProcurarPor").makeOptional();
      }else{
        this._agendamentoForm.getChildAsBuildFormControl("horaInternacao").makeOptional();
        this._agendamentoForm.getChildAsBuildFormControl("internacaoSetorAndar").makeOptional();
        this._agendamentoForm.getChildAsBuildFormControl("internacaoProcurarPor").makeOptional();

        let dataInicioGcsf = DataUtil.toDate(objForm.dataInicioGcsf, DateTypeFormats.DATE_ONLY);
        let dataInicioGcsfValida = dateMoment.isDateTimeSameOrBefore(dataInicioGcsf, dataColeta);
        if (!dataInicioGcsfValida) {
          this._agendamentoForm.getChildAsBuildFormControl("dataInicioGcsf").invalidate(this.customErros["maiorDataColeta"]);
          return false;
        }
      }

      return formularioValido;
    }

      popularPrescricao(idPrescricao : number){

      this.visualizarPrescricaoDataEventService.dataEvent.carregarPrescricao(idPrescricao);

      this.prescricaoService.obterPrescricaoComEvolucaoPorIdPrecricao(idPrescricao)
         .then(res => {

            this._prescricaoEvolucao = new PrescricaoEvolucaoDTO().jsonToEntity(res);
            let prescricao: PrescricaoDTO = this._prescricaoEvolucao.prescricao;

            this._rmr = prescricao.rmr;

         });
    }
    /**
     * Getter pedidoColeta
     * @return {PedidoColeta}
     */
    public get pedidoColeta(): PedidoColeta {
      return this._pedidoColeta;
    }

    /**
     * Setter pedidoColeta
     * @param {PedidoColeta} value
     */
    public set pedidoColeta(value: PedidoColeta) {
      this._pedidoColeta = value;
    }

    /**
     * Getter estaEmJejum
     * @return {boolean }
     */
    public get estaEmJejum(): boolean  {
      return this._estaEmJejum;
    }

    /**
     * Setter estaEmJejum
     * @param {boolean } value
     */
    public set estaEmJejum(value: boolean ) {
      this._estaEmJejum = value;
    }


    /**
     * Getter ehMedula
     * @return {boolean }
     */
    public get ehMedula(): boolean  {
      return this._ehMedula;
    }

    /**
     * Setter ehMedula
     * @param {boolean } value
     */
    public set ehMedula(value: boolean ) {
      this._ehMedula = value;
    }


    /**
     * Getter opcoesMedulaSangue
     * @return {Number[] }
     */
    public get opcoesMedulaSangue(): Number[]  {
      return this._opcoesMedulaSangue;
    }

    /**
     * Setter opcoesMedulaSangue
     * @param {Number[] } value
     */
    public set opcoesMedulaSangue(value: Number[] ) {
      this._opcoesMedulaSangue = value;
    }

    /**
     * Getter labelsMedulaSangue
     * @return {String[] }
     */
    public get labelsMedulaSangue(): String[]  {
      return this._labelsMedulaSangue;
    }

    /**
     * Setter labelsMedulaSangue
     * @param {String[] } value
     */
    public set labelsMedulaSangue(value: String[] ) {
      this._labelsMedulaSangue = value;
    }


    /**
     * Getter opcoesSimNao
     * @return {String[] }
     */
    public get opcoesSimNao(): String[]  {
      return this._opcoesSimNao;
    }

    /**
     * Setter opcoesSimNao
     * @param {String[] } value
     */
    public set opcoesSimNao(value: String[] ) {
      this._opcoesSimNao = value;
    }

    /**
     * Getter labelsSimNao
     * @return {String[] }
     */
    public get labelsSimNao(): String[]  {
      return this._labelsSimNao;
    }

    /**
     * Setter labelsSimNao
     * @param {String[] } value
     */
    public set labelsSimNao(value: String[] ) {
      this._labelsSimNao = value;
    }

};
