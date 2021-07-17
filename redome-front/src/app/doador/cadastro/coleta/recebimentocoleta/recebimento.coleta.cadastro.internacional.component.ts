import { Component, OnInit, ViewChild } from '@angular/core';
import { FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { PedidoColeta } from 'app/doador/consulta/coleta/pedido.coleta';
import { PedidoColetaService } from 'app/doador/consulta/coleta/pedido.coleta.service';
import { BuildForm } from 'app/shared/buildform/build.form';
import { NumberControl } from 'app/shared/buildform/controls/number.control';
import { StringControl } from 'app/shared/buildform/controls/string.control';
import { DestinoColeta } from 'app/shared/dominio/destino.coleta';
import { RecebimentoColeta } from 'app/shared/dominio/recebimento.coleta';
import { FontesCelulas } from 'app/shared/enums/fontes.celulas';
import { TiposDestinoColeta } from 'app/shared/enums/tipos.destino.coleta';
import { TiposPrescricao } from 'app/shared/enums/tipos.prescricao';
import { ErroMensagem } from 'app/shared/erromensagem';
import { MessageBox } from 'app/shared/modal/message.box';
import { RecebimentoColetaService } from 'app/shared/service/recebimento.coleta.service';
import { DataUtil } from 'app/shared/util/data.util';
import { DateMoment } from 'app/shared/util/date/date.moment';
import { DateTypeFormats } from 'app/shared/util/date/date.type.formats';
import { ErroUtil } from 'app/shared/util/erro.util';
import { HeaderPacienteComponent } from '../../../../paciente/consulta/identificacao/header.paciente.component';
import { FonteCelula } from '../../../../shared/dominio/fonte.celula';


@Component({
    selector: "recebimento-coleta-cadastro-internacional",
    templateUrl: './recebimento.coleta.cadastro.internacional.component.html'
})
export class RecebimentoColetaCadastroInternacionalComponent implements OnInit {
    private pedidoColetaId: number;

    @ViewChild('headerPaciente')
    private headerPaciente: HeaderPacienteComponent;

    private _recebimentoForm: BuildForm<any>;
    private _idPedidoColeta: number;
    private _pedidoColeta: PedidoColeta;
    private _identificacaoDoador: string;
    private _idTipoPrescricao: Number;

    private _opcoesMedulaSangue:Number[] = [0,1];
    private _labelsMedulaSangue:String[] = [];

    private _opcoesInfusaoCongelamentoDesprezado:Number[] = [0,1,2];
    private _labelsInfusaoCongelamentoDesprezado:String[] = [];

    private _opcoesSimNao:String[] = ["false","true"];
    private _labelsSimNao:String[] = [];

    public maskData: Array<string | RegExp>
    private customErros: any = {maiorDataColeta: ""}

    private _exibirMotivoProdutoColetadoIncorreto: boolean = false;
    private _exibirMotivoIdentificacaoDoadorIncorreta: boolean = false;
    private _exibirMotivoProdudoAcondicionadoIncorreto: boolean = false;
    private _exibirDescrevaProdudoEventoAdverso: boolean = false;
    private _exibirDataInfusao: boolean = false;
    private _exibirDataPrevista: boolean = false;
    private _exibirDataDescarte: boolean = false;
    private _exibirOpcaoMedulaESangue: boolean = false;

    constructor(
        private router: Router,
        private translate: TranslateService,
        private activatedRouter: ActivatedRoute,
        private messageBox: MessageBox,
        private recebimentoColetaService: RecebimentoColetaService,
        private pedidoColetaService: PedidoColetaService) {

        this.buildForm();

        this.maskData = [/[0-9]/, /[0-9]/, '\/', /[0-9]/, /[0-9]/, '\/', /[0-9]/, /[0-9]/, /[0-9]/, /[0-9]/]
    }

    public form(): FormGroup {
        return this._recebimentoForm.form;
    }

    public preencherFormulario(entidade: RecebimentoColeta): void {
        throw new Error("Method not implemented.");
    }

    nomeComponente(): string {
      return "RecebimentoColetaCadastroInternacionalComponent";
    }

    ngOnInit(): void {

      this.translate.get("mensagem.erro").subscribe(res => {
        this.customErros["dataRecebimentoMaiorDataColeta"] = res.dataRecebimentoMaiorDataColeta;
        this.customErros["dataInfusaoMaiorDataRecebimento"] = res.dataInfusaoMaiorDataRecebimento;
        this.customErros["dataCongelamentoMaiorDataRecebimento"] = res.dataCongelamentoMaiorDataRecebimento;
        this.customErros["dataDescarteMaiorDataRecebimento"] = res.dataDescarteMaiorDataRecebimento;
      });

      this.translate.get(["textosGenericos.sim", "textosGenericos.nao"]).subscribe(res => {
        this._labelsSimNao[0] = res['textosGenericos.nao'];
        this._labelsSimNao[1] = res['textosGenericos.sim'];
      });

      this.translate.get(["textosGenericos.medula", "textosGenericos.sangueperiferico"]).subscribe(res => {
        this._labelsMedulaSangue[0] = res['textosGenericos.medula'];
        this._labelsMedulaSangue[1] = res['textosGenericos.sangueperiferico'];
      });

      this.translate.get(["workup.recebimentoColeta.infusao", "workup.recebimentoColeta.congelamento", "workup.recebimentoColeta.desprezado"]).subscribe(res => {
        this._labelsInfusaoCongelamentoDesprezado[0] = res['workup.recebimentoColeta.infusao'];
        this._labelsInfusaoCongelamentoDesprezado[1] = res['workup.recebimentoColeta.congelamento'];
        this._labelsInfusaoCongelamentoDesprezado[2] = res['workup.recebimentoColeta.desprezado'];
      });

      this.carregarRecebimento();
    }

    /**
    * Constrói os BuildForm dos campos obrigatorios
    */
    buildForm() {
      this._recebimentoForm = new BuildForm()
          .add(new NumberControl('fonteCelula', 0, [Validators.required]))
          .add(new NumberControl('numeroDeBolsas', [Validators.required]))
          .add(new NumberControl('totalTotalTcn', [Validators.required]))
          .add(new NumberControl('volume', [Validators.required]))

          .add(new StringControl('produtoColetadoDeAcordo', [Validators.required]))
          .add(new StringControl('motivoProdutoColetadoIncorreto', [Validators.required]))

          .add(new StringControl('identificacaoDoadorConfere', [Validators.required]))
          .add(new StringControl('motivoIdentificacaoDoadorIncorreta', [Validators.required]))

          .add(new StringControl('produdoAcondicionadoCorreto', [Validators.required]))
          .add(new StringControl('motivoProdudoAcondicionadoIncorreto', [Validators.required]))

          .add(new StringControl('produdoEventoAdverso', [Validators.required]))
          .add(new StringControl('descrevaProdudoEventoAdverso', [Validators.required]))

          .add(new StringControl('comentarioAdicional'))

          .add(new StringControl('dataRecebimento', [Validators.required]))

          .add(new NumberControl('destino', [Validators.required]))

          .add(new StringControl('dataInfusao', [Validators.required]))

          .add(new StringControl('dataDescarte', [Validators.required]))
          .add(new StringControl('dataPrevistaInfusao', [Validators.required]))

          .add(new StringControl('justificativaCongelamento', [Validators.required]))
          .add(new StringControl('justificativaDescarte', [Validators.required]))
    }

    carregarRecebimento(){

      this.activatedRouter.queryParamMap.subscribe(queryParam => {
        if (queryParam.keys.length != 0) {
          this._idPedidoColeta = Number(queryParam.get('idPedidoColeta'));
          this._identificacaoDoador = queryParam.get('identificacaoDoador');
          this._idTipoPrescricao = Number(queryParam.get('idTipoPrescricao'));
          let rmr = Number(queryParam.get('rmr'));

            this.pedidoColetaService.obterPedidoColeta(this._idPedidoColeta).then(res => {
              this._pedidoColeta = new PedidoColeta().jsonToEntity(res);
            },
            (error: ErroMensagem) => {
              ErroUtil.exibirMensagemErro(error, this.messageBox);
            });

            if(TiposPrescricao.MEDULA == this._idTipoPrescricao){
              this._exibirOpcaoMedulaESangue = true;
            }

            Promise.resolve(this.headerPaciente).then(() => {
              this.headerPaciente.popularCabecalhoIdentificacaoPorPaciente(rmr);
            });
        }
      });
    }

    /**
     * salva o recebimento da coleta.
     */
    salvarRecebimentoColeta(){

      if(this.validateForm()){

        let recebimentoColeta: RecebimentoColeta = new RecebimentoColeta();
        let objForm = this._recebimentoForm.value;

        recebimentoColeta.pedidoColeta = new PedidoColeta(this._idPedidoColeta);
        recebimentoColeta.recebeuColeta = true;

        if(this._exibirOpcaoMedulaESangue){
          recebimentoColeta.fonteCelula = new FonteCelula(objForm.fonteCelula);
        }else{
          recebimentoColeta.fonteCelula = new FonteCelula(FontesCelulas.MEDULA_OSSEA.id);
        }
        recebimentoColeta.numeroDeBolsas = objForm.numeroDeBolsas;
        recebimentoColeta.totalTotalTcn = objForm.totalTotalTcn;
        recebimentoColeta.volume = objForm.volume;

        recebimentoColeta.produtoColetadoDeAcordo = objForm.produtoColetadoDeAcordo;
        if(objForm.produtoColetadoDeAcordo == "false"){
          recebimentoColeta.motivoProdutoColetadoIncorreto = objForm.motivoProdutoColetadoIncorreto;
        }

        recebimentoColeta.identificacaoDoadorConfere = objForm.identificacaoDoadorConfere;
        if(objForm.identificacaoDoadorConfere == "false"){
          recebimentoColeta.motivoIdentificacaoDoadorIncorreta = objForm.motivoIdentificacaoDoadorIncorreta;
        }

        recebimentoColeta.produdoAcondicionadoCorreto = objForm.produdoAcondicionadoCorreto;
        if(objForm.produdoAcondicionadoCorreto == "false"){
          recebimentoColeta.motivoProdudoAcondicionadoIncorreto = objForm.motivoProdudoAcondicionadoIncorreto;
        }

        recebimentoColeta.produdoEventoAdverso = objForm.produdoEventoAdverso;
        if(objForm.produdoEventoAdverso == "true"){
          recebimentoColeta.descrevaProdudoEventoAdverso = objForm.descrevaProdudoEventoAdverso;
        }

        recebimentoColeta.comentarioAdicional = objForm.comentarioAdicional;
        recebimentoColeta.dataRecebimento = DataUtil.toDate(objForm.dataRecebimento, DateTypeFormats.DATE_ONLY);

        if(objForm.destino == 0){
          recebimentoColeta.destinoColeta = new DestinoColeta(TiposDestinoColeta.INFUSAO);
          recebimentoColeta.dataInfusao = DataUtil.toDate(objForm.dataInfusao, DateTypeFormats.DATE_ONLY);
        }else if(objForm.destino == 1){
          recebimentoColeta.destinoColeta = new DestinoColeta(TiposDestinoColeta.CONGELAMENTO.valueOf());
          recebimentoColeta.dataPrevistaInfusao = DataUtil.toDate(objForm.dataPrevistaInfusao, DateTypeFormats.DATE_ONLY);
          recebimentoColeta.justificativaCongelamento = objForm.justificativaCongelamento;
        }else if(objForm.destino == 2){
          recebimentoColeta.destinoColeta = new DestinoColeta(TiposDestinoColeta.DESCARTE.valueOf());
          recebimentoColeta.dataDescarte = DataUtil.toDate(objForm.dataDescarte, DateTypeFormats.DATE_ONLY);
          recebimentoColeta.justificativaDescarte = objForm.justificativaDescarte;
        }

        this.recebimentoColetaService.salvarRecebimentoColeta(recebimentoColeta).then(res => {
          this.messageBox.alert(res)
            .withTarget(this)
            .withCloseOption((target?: any) => {
              this.voltar();
            })
            .show();
        },
        (error: ErroMensagem) => {
          ErroUtil.exibirMensagemErro(error, this.messageBox);
        });
      }
    }

    validateForm():boolean{

      let objForm = this._recebimentoForm.value;
      let dateMoment: DateMoment = DateMoment.getInstance();

      if(!this._exibirOpcaoMedulaESangue){
        this._recebimentoForm.getChildAsBuildFormControl("fonteCelula").makeOptional();
      }

      if(objForm.produtoColetadoDeAcordo == "false"){
        this._recebimentoForm.getChildAsBuildFormControl("motivoProdutoColetadoIncorreto").makeOptional();
      }

      if(objForm.identificacaoDoadorConfere  == "false"){
        this._recebimentoForm.getChildAsBuildFormControl("motivoIdentificacaoDoadorIncorreta").makeOptional();
      }

      if(objForm.produdoAcondicionadoCorreto  == "false"){
        this._recebimentoForm.getChildAsBuildFormControl("motivoProdudoAcondicionadoIncorreto").makeOptional();
      }

      if(objForm.produdoEventoAdverso  == "true"){
        this._recebimentoForm.getChildAsBuildFormControl("descrevaProdudoEventoAdverso").makeOptional();
      }

      if(objForm.destino == 0){
        this._recebimentoForm.getChildAsBuildFormControl("dataPrevistaInfusao").makeOptional();
        this._recebimentoForm.getChildAsBuildFormControl("justificativaCongelamento").makeOptional();
        this._recebimentoForm.getChildAsBuildFormControl("dataDescarte").makeOptional();
        this._recebimentoForm.getChildAsBuildFormControl("justificativaDescarte").makeOptional();
      }else if(objForm.destino == 1){
        this._recebimentoForm.getChildAsBuildFormControl("dataInfusao").makeOptional();
        this._recebimentoForm.getChildAsBuildFormControl("dataDescarte").makeOptional();
        this._recebimentoForm.getChildAsBuildFormControl("justificativaDescarte").makeOptional();
      }else if(objForm.destino == 2){
        this._recebimentoForm.getChildAsBuildFormControl("dataInfusao").makeOptional();
        this._recebimentoForm.getChildAsBuildFormControl("dataPrevistaInfusao").makeOptional();
        this._recebimentoForm.getChildAsBuildFormControl("justificativaCongelamento").makeOptional();
      }

      let formularioValido: boolean = this._recebimentoForm.valid;

      let dataColeta = this._pedidoColeta.dataColeta;
      let dataRecebimento = DataUtil.toDate(objForm.dataRecebimento, DateTypeFormats.DATE_ONLY);

      let dataRecebimentoValida = dateMoment.isDateTimeSameOrBefore(dataRecebimento, dataColeta);
      if (!dataRecebimentoValida) {
        this._recebimentoForm.getChildAsBuildFormControl("dataRecebimento").invalidate(this.customErros["dataRecebimentoMaiorDataColeta"]);
        return false;
      }

      let dataInfusao = DataUtil.toDate(objForm.dataInfusao, DateTypeFormats.DATE_ONLY);
      let dataInfusaoValida = dateMoment.isDateTimeSameOrBefore(dataInfusao, dataRecebimento);
      if (!dataInfusaoValida) {
        this._recebimentoForm.getChildAsBuildFormControl("dataInfusao").invalidate(this.customErros["dataInfusaoMaiorDataRecebimento"]);
        return false;
      }

      let dataCongelamento = DataUtil.toDate(objForm.dataCongelamento, DateTypeFormats.DATE_ONLY);
      let dataCongelamentoValida = dateMoment.isDateTimeSameOrBefore(dataCongelamento, dataRecebimento);
      if (!dataCongelamentoValida) {
        this._recebimentoForm.getChildAsBuildFormControl("dataCongelamento").invalidate(this.customErros["dataCongelamentoMaiorDataRecebimento"]);
        return false;
      }

      let dataDescarte = DataUtil.toDate(objForm.dataDescarte, DateTypeFormats.DATE_ONLY);
      let dataDescarteValida = dateMoment.isDateTimeSameOrBefore(dataDescarte, dataRecebimento);
      if (!dataDescarteValida) {
        this._recebimentoForm.getChildAsBuildFormControl("dataDescarte").invalidate(this.customErros["dataDescarteMaiorDataRecebimento"]);
        return false;
      }

      return formularioValido;
    }

    voltar(){
        this.router.navigateByUrl('/doadores/workup/centrocoleta/consulta');
    }

    isExibirMotivoProdutoColetadoIncorreto(value: string){
      return value == "false" ? this._exibirMotivoProdutoColetadoIncorreto = true : this._exibirMotivoProdutoColetadoIncorreto = false;
    }

    isExibirMotivoIdentificacaoDoadorIncorreta(value: string){
      return value == "false" ? this._exibirMotivoIdentificacaoDoadorIncorreta = true : this._exibirMotivoIdentificacaoDoadorIncorreta = false;
    }

    isExibirMotivoProdudoAcondicionadoIncorreto(value: string){
      return value == "false" ? this._exibirMotivoProdudoAcondicionadoIncorreto = true : this._exibirMotivoProdudoAcondicionadoIncorreto = false;
    }

    isExibirDescrevaProdudoEventoAdverso(value: string){
      return value == "true" ? this._exibirDescrevaProdudoEventoAdverso = true : this._exibirDescrevaProdudoEventoAdverso = false;
    }

    isExibirDadosDestino(value: number){
      if(value == 0){
        this._exibirDataInfusao = true;
        this._exibirDataPrevista = false;
        this._exibirDataDescarte = false;
      }else if(value == 1){
        this._exibirDataInfusao = false;
        this._exibirDataPrevista = true;
        this._exibirDataDescarte = false;
      }else if(value == 2){
        this._exibirDataInfusao = false;
        this._exibirDataPrevista = false;
        this._exibirDataDescarte = true;
      }
    }

    limparProdutoColetado(){
      this._recebimentoForm.getChildAsBuildFormControl("numeroDeBolsas").value = "";
      this._recebimentoForm.getChildAsBuildFormControl("totalTotalTcn").value = "";
      this._recebimentoForm.getChildAsBuildFormControl("volume").value = "";
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
     * Getter opcoesInfusaoCongelamentoDesprezado
     * @return {Number[] }
     */
	public get opcoesInfusaoCongelamentoDesprezado(): Number[]  {
		return this._opcoesInfusaoCongelamentoDesprezado;
	}

    /**
     * Setter opcoesInfusaoCongelamentoDesprezado
     * @param {Number[] } value
     */
	public set opcoesInfusaoCongelamentoDesprezado(value: Number[] ) {
		this._opcoesInfusaoCongelamentoDesprezado = value;
	}


    /**
     * Getter labelsInfusaoCongelamentoDesprezado
     * @return {String[] }
     */
	public get labelsInfusaoCongelamentoDesprezado(): String[]  {
		return this._labelsInfusaoCongelamentoDesprezado;
	}

    /**
     * Setter labelsInfusaoCongelamentoDesprezado
     * @param {String[] } value
     */
	public set labelsInfusaoCongelamentoDesprezado(value: String[] ) {
		this._labelsInfusaoCongelamentoDesprezado = value;
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
     * Getter identificacaoDoador
     * @return {string}
     */
	public get identificacaoDoador(): string {
		return this._identificacaoDoador;
	}

    /**
     * Setter identificacaoDoador
     * @param {string} value
     */
	public set identificacaoDoador(value: string) {
		this._identificacaoDoador = value;
	}

    /**
     * Getter exibirDescrevaProdudoEventoAdverso
     * @return {boolean }
     */
	public get exibirDescrevaProdudoEventoAdverso(): boolean  {
		return this._exibirDescrevaProdudoEventoAdverso;
	}

    /**
     * Setter exibirDescrevaProdudoEventoAdverso
     * @param {boolean } value
     */
	public set exibirDescrevaProdudoEventoAdverso(value: boolean ) {
		this._exibirDescrevaProdudoEventoAdverso = value;
	}



    /**
     * Getter exibirMotivoProdutoColetadoIncorreto
     * @return {boolean }
     */
	public get exibirMotivoProdutoColetadoIncorreto(): boolean  {
		return this._exibirMotivoProdutoColetadoIncorreto;
	}

    /**
     * Setter exibirMotivoProdutoColetadoIncorreto
     * @param {boolean } value
     */
	public set exibirMotivoProdutoColetadoIncorreto(value: boolean ) {
		this._exibirMotivoProdutoColetadoIncorreto = value;
	}


    /**
     * Getter exibirMotivoProdudoAcondicionadoIncorreto
     * @return {boolean }
     */
	public get exibirMotivoProdudoAcondicionadoIncorreto(): boolean  {
		return this._exibirMotivoProdudoAcondicionadoIncorreto;
	}

    /**
     * Setter exibirMotivoProdudoAcondicionadoIncorreto
     * @param {boolean } value
     */
	public set exibirMotivoProdudoAcondicionadoIncorreto(value: boolean ) {
		this._exibirMotivoProdudoAcondicionadoIncorreto = value;
	}


    /**
     * Getter exibirDataInfusao
     * @return {boolean }
     */
	public get exibirDataInfusao(): boolean  {
		return this._exibirDataInfusao;
	}

    /**
     * Setter exibirDataInfusao
     * @param {boolean } value
     */
	public set exibirDataInfusao(value: boolean ) {
		this._exibirDataInfusao = value;
	}


    /**
     * Getter exibirMotivoIdentificacaoDoadorIncorreta
     * @return {boolean }
     */
	public get exibirMotivoIdentificacaoDoadorIncorreta(): boolean  {
		return this._exibirMotivoIdentificacaoDoadorIncorreta;
	}

    /**
     * Setter exibirMotivoIdentificacaoDoadorIncorreta
     * @param {boolean } value
     */
	public set exibirMotivoIdentificacaoDoadorIncorreta(value: boolean ) {
		this._exibirMotivoIdentificacaoDoadorIncorreta = value;
	}


    /**
     * Getter exibirDataPrevista
     * @return {boolean }
     */
	public get exibirDataPrevista(): boolean  {
		return this._exibirDataPrevista;
	}

    /**
     * Setter exibirDataPrevista
     * @param {boolean } value
     */
	public set exibirDataPrevista(value: boolean ) {
		this._exibirDataPrevista = value;
	}


    /**
     * Getter exibirDataDescarte
     * @return {boolean }
     */
	public get exibirDataDescarte(): boolean  {
		return this._exibirDataDescarte;
	}

    /**
     * Setter exibirDataDescarte
     * @param {boolean } value
     */
	public set exibirDataDescarte(value: boolean ) {
		this._exibirDataDescarte = value;
	}


    /**
     * Getter exibirOpcaoMedulaESangue
     * @return {boolean }
     */
	public get exibirOpcaoMedulaESangue(): boolean  {
		return this._exibirOpcaoMedulaESangue;
	}

    /**
     * Setter exibirOpcaoMedulaESangue
     * @param {boolean } value
     */
	public set exibirOpcaoMedulaESangue(value: boolean ) {
		this._exibirOpcaoMedulaESangue = value;
	}


    // public destinoInfusao() {
    //     this.resetFieldRequired(this.formularioRecebimento,'justificativa');
    //     this.formularioRecebimento.get("data").setValidators(Validators.compose([Validators.required,ValidateDataMenorQueHoje]));
    //     this.formularioRecebimento.get("data").updateValueAndValidity();
    //     this.clearMensagensErro(this.formularioRecebimento, null, "data");
    //     this.translate.get("workup.recebimentoColeta.dataInfusao").subscribe(res => {
    //         this._labelData = res;
    //     });
    //     this.setInfusao();
    //     this._justificativa = false;
    // }

    // public destinoCongelamento() {
    //     this.setFieldRequired(this.formularioRecebimento,'justificativa');
    //     this.formularioRecebimento.get("data").setValidators(Validators.compose([Validators.required,ValidateDataMaiorOuIgualHoje]));
    //     this.formularioRecebimento.get("data").updateValueAndValidity();
    //     this.clearMensagensErro(this.formularioRecebimento, null, "data");
    //     this.translate.get("workup.recebimentoColeta.dataPrevista").subscribe(res => {
    //         this._labelData = res;
    //     });
    //     this.setCongelamento();
    //     this._justificativa = true;
    // }

    // public destinoDescarte() {
    //     this.setFieldRequired(this.formularioRecebimento,'justificativa');
    //     this.formularioRecebimento.get("data").setValidators(Validators.compose([Validators.required,ValidateDataMenorQueHoje]));
    //     this.formularioRecebimento.get("data").updateValueAndValidity();
    //     this.clearMensagensErro(this.formularioRecebimento, null, "data");
    //     this.translate.get("workup.recebimentoColeta.dataDescarte").subscribe(res => {
    //         this._labelData = res;
    //     });
    //     this.setDescarte();
    //     this._justificativa = true;
    // }


    // public get fonteCelulas(): FonteCelula {
    //     return this._fonteCelulas;
    // }

    // public set fonteCelulas(value: FonteCelula) {
    //     this._fonteCelulas = value;
    // }


    // /**
    //  * Getter recebimentoColeta
    //  * @return {RecebimentoColeta}
    //  */
    // public get recebimentoColeta(): RecebimentoColeta {
    //     return this._recebimentoColeta;
    // }

    // /**
    //  * Setter recebimentoColeta
    //  * @param {RecebimentoColeta} value
    //  */
    // public set recebimentoColeta(value: RecebimentoColeta) {
    //     this._recebimentoColeta = value;
    // }


    // /**
    //  * Getter dmr
    //  * @return {number}
    //  */
    // public get dmr(): number {
    //     return this._dmr;
    // }

    // /**
    //  * Setter dmr
    //  * @param {number} value
    //  */
    // public set dmr(value: number) {
    //     this._dmr = value;
    // }


    // /**
    //  * Getter labelData
    //  * @return {string}
    //  */
    // public get labelData(): string {
    //     return this._labelData;
    // }

    // /**
    //  * Setter labelData
    //  * @param {string} value
    //  */
    // public set labelData(value: string) {
    //     this._labelData = value;
    // }

    // /**
    //  * Getter justificativa
    //  * @return {boolean}
    //  */
    // public get justificativa(): boolean {
    //     return this._justificativa;
    // }

    // /**
    //  * Setter justificativa
    //  * @param {boolean} value
    //  */
    // public set justificativa(value: boolean) {
    //     this._justificativa = value;
    // }

    // /*
    //  Método que habilita ou desabilita os campos da tela.
    //  * Se recebeu habilita todos os campos.
    // */
    // public radioButtonRecebeuColeta() {
    //     this.desabilitarCampos = false;
    // }

    // /*
    //  * Método que habilita ou desabilita os campos da tela.
    //  * Se não recebeu desabilita todos os campos.
    // */
    // public radioButtonNaoRecebeuColeta() {
    //     this.desabilitarCampos = true;
    // }

    /**
     * salva o recebimento da coleta.
     */
    // public salvarRecebimentoColeta(){
    //     if(this.validateForm()){
    //         if(this._infusao){
    //             this.recebimentoColeta.dataInfusao = PacienteUtil.obterDataSemMascara(this.formularioRecebimento.get('data').value);
    //         }

    //         if(this._congelamento){
    //             this.recebimentoColeta.dataPrevistaInfusao = PacienteUtil.obterDataSemMascara(this.formularioRecebimento.get('data').value);
    //             this.recebimentoColeta.justificativaCongelamento = this.formularioRecebimento.get('justificativa').value;
    //         }

    //         if(this._descarte){
    //             this.recebimentoColeta.dataDescarte = PacienteUtil.obterDataSemMascara(this.formularioRecebimento.get('data').value);
    //             this.recebimentoColeta.justificativaDescarte = this.formularioRecebimento.get('justificativa').value;
    //         }
    //         this.recebimentoColeta.fonteCelula = new FonteCelula();
    //         this.recebimentoColeta.fonteCelula.id = 1;
    //         if(this.recebimentoColeta.fonteCelula.id = 1){
    //             this.recebimentoColeta.totalTotalTcn =  this.formularioRecebimento.get('total').value;
    //         }
    //         else if(this.recebimentoColeta.fonteCelula.id = 2){
    //             this.recebimentoColeta.totalTotalCd34 =  this.formularioRecebimento.get('total').value;
    //         }
    //         this.recebimentoColeta.destinoColeta = new DestinoColeta();
    //         this.recebimentoColeta.destinoColeta.id = this.formularioRecebimento.get('destino').value;
    //         this.recebimentoColeta.recebeuColeta = this.formularioRecebimento.get('recebeu').value == 1?true:false;
    //         this.recebimentoColeta.pedidoColeta = new PedidoColeta();
    //         this.recebimentoColeta.pedidoColeta.id = this.pedidoColetaId;
    //         this.recebimentoColetaService.atualizarRecebimentoColeta(this.recebimentoColeta).then(res => {
    //             this.modalMensagem.mensagem = res.mensagem;
    //             this.modalMensagem.abrirModal();
    //         },
    //         (error: ErroMensagem) => {
    //             this.exibirMensagemErro(error);
    //         });
    //     }
    // }

  //   public voltar() {
  //       this.tarefaService.removerAtribuicaoTarefa(this.tarefaId).then(res => {
  //           this.router.navigateByUrl('/doadores/workup/recebimentocoleta');
  //       },
  //       (error: ErroMensagem) => {
  //           this.exibirMensagemErro(error);
  //       });
  //   }

  //   public voltarSemCancelarTarefa(){
  //       this.router.navigateByUrl('/doadores/workup/recebimentocoleta');
  //   }

  //   public validateForm():boolean{
  //       this.clearMensagensErro(this.formularioRecebimento);
  //       let valid: boolean = this.validateFields(this.formularioRecebimento);
  //       this.setMensagensErro(this.formularioRecebimento);
  //       return valid;
  //   }

  //   private setInfusao(){
  //       this._infusao = true;
  //       this._congelamento = false;
  //       this._descarte = false;
  //   }

  //   private setCongelamento(){
  //       this._infusao = false;
  //       this._congelamento = true;
  //       this._descarte = false;
  //   }

  //   private setDescarte(){
  //       this._infusao = false;
  //       this._congelamento = false;
  //       this._descarte = true;
  //   }


  //   /**
  //    * Getter tarefaId
  //    * @return {number}
  //    */
	// public get tarefaId(): number {
	// 	return this._tarefaId;
	// }

  //   /**
  //    * Setter tarefaId
  //    * @param {number} value
  //    */
	// public set tarefaId(value: number) {
	// 	this._tarefaId = value;
	// }

  //   /**
  //    * Getter idRegistro
  //    * @return {string}
  //    */
	// public get idRegistro(): string {
	// 	return this._idRegistro;
	// }

  //   /**
  //    * Setter idRegistro
  //    * @param {string} value
  //    */
	// public set idRegistro(value: string) {
	// 	this._idRegistro = value;
	// }

  //   public get ehFonteCelulaOpcaoMedula(): boolean {
  //       if(this.recebimentoColeta && this.recebimentoColeta.fonteCelula){
  //           return this.recebimentoColeta.fonteCelula.id === FontesCelulas.MEDULA_OSSEA.id;
  //       }
  //       return false;
  //   }

  //   public get ehFonteCelulaOpcaoSanguePeriferico(): boolean {
  //       if(this.recebimentoColeta && this.recebimentoColeta.fonteCelula){
  //           return this.recebimentoColeta.fonteCelula.id === FontesCelulas.SANGUE_PERIFERICO.id;
  //       }
  //       return false;
  //   }

};
