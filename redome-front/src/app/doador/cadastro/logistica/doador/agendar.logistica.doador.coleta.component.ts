import { Component, OnInit, ViewChild } from '@angular/core';
import { FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { CentroTransplanteService } from 'app/admin/centrotransplante/centrotransplante.service';
import { HeaderDoadorComponent } from 'app/doador/consulta/header/header.doador.component';
import { Doador } from 'app/doador/doador';
import { DoadorNacional } from 'app/doador/doador.nacional';
import { BuildForm } from 'app/shared/buildform/build.form';
import { StringControl } from 'app/shared/buildform/controls/string.control';
import { CentroTransplante } from 'app/shared/dominio/centro.transplante';
import { LogisticaDoadorColetaDTO } from 'app/shared/dto/logistica.doador.coleta.dto';
import { Modal } from 'app/shared/modal/factory/modal.factory';
import { MessageBox } from 'app/shared/modal/message.box';
import { PedidoLogistica } from 'app/shared/model/pedido.logistica';
import { TransporteTerrestre } from 'app/shared/model/transporte.terrestre';
import { LogisticaService } from 'app/shared/service/logistica.service';
import { ErroUtil } from 'app/shared/util/erro.util';
import { RouterUtil } from 'app/shared/util/router.util';
import { Pais } from '../../../../shared/dominio/pais';
import { TiposVoucher } from '../../../../shared/enums/tipos.voucher';
import { ErroMensagem } from '../../../../shared/erromensagem';
import { HistoricoNavegacao } from '../../../../shared/historico.navegacao';
import { DataUtil } from '../../../../shared/util/data.util';
import { DateTypeFormats } from '../../../../shared/util/date/date.type.formats';
import { DoadorService } from '../../../doador.service';
import { ArquivoVoucherLogisticaComponent } from '../detalhe/arquivo.voucher.logistica.component';
import { TransporteTerrestreComponent } from '../detalhe/transporteTerrestre/transporte.terrestre.component';

/**
 * Classe que representa o componente para detalhar as informações de logística de doador.
 * @author ergomes
 */
@Component({
    selector: "agendar-logistica-doador-coleta",
    templateUrl: './agendar.logistica.doador.coleta.component.html'
})
export class AgendarLogisticaDoadorColetaComponent implements OnInit {

    private _idPedidoLogistica: number;
    private _idDoador: number;
    private _dataColeta: Date;
    private _observacao: string;
    private _prosseguir: boolean = true;

    private _dadosDoador: DoadorNacional;
    private _centroTransplante: CentroTransplante;

    private _agendamentoForm: BuildForm<any>;

    /**
     * Detalhes da logística do doador.
     * Deve ser utilizado para preencher a tela de detalhe do pedido.
     */
    public dto: LogisticaDoadorColetaDTO;

    @ViewChild('headerDoador')
    private headerDoador: HeaderDoadorComponent;

    @ViewChild('transporteTerrestreComponent')
    private transporteTerrestreComponent: TransporteTerrestreComponent;

    @ViewChild("voucherTransporteAereo")
    private voucherTransporteAereo: ArquivoVoucherLogisticaComponent;

    @ViewChild("voucherHospedagem")
    private voucherHospedagem: ArquivoVoucherLogisticaComponent;

    @ViewChild("modal")
    public modal;

    private _mostraDados: string = '';
    private _mostraFormulario: string = 'hide';
    private _mostrarIncluirTransporteAereo: string= "";
    private _mostrarIncluirHospedagem: string= "";
    private _mostraDadosObservacao: string = '';
    private _mostraFormularioObservacao: string = 'hide';
    private _mostrarEditarObservacao: string= "";

    private _mostraProsseguir: string = "";
    private _mostraJustificativa: string = "hide";

    private _opcoesSimNao:String[] = ["false","true"];
    private _labelsSimNao:String[] = [];

    constructor(private router: Router,
        public translate: TranslateService,
        private activatedRouter: ActivatedRoute,
        private messageBox: MessageBox,
        private logisticaService: LogisticaService,
        private doadorService: DoadorService,
        private centroTransplanteService: CentroTransplanteService) {

      this.buildForm();
    }

    /**
     * Nome do componente atual
     */
    nomeComponente(): string {
        return "AgendarLogisticaDoadorColetaComponent";
    }

    /**
     * Inicializa a classe com os dados buscando no serviço REST
     *
     */
    ngOnInit(): void {

      this.translate.get(["textosGenericos.sim", "textosGenericos.nao"]).subscribe(res => {
        this._labelsSimNao[0] = res['textosGenericos.nao'];
        this._labelsSimNao[1] = res['textosGenericos.sim'];
      });

      RouterUtil.recuperarParametrosDaActivatedRoute(this.activatedRouter, ["pedidoId", "idDoador"]).then(res => {
          this._idPedidoLogistica = <number>res['pedidoId'];
          this._idDoador = <number>res['idDoador'];

          this.obterDetalhePedidoLogistica();
          this.obterDetalhesDoador();
      });
    }

    /**
     * * Método que constrói o formulário
     * @returns void
     */
    buildForm() {

      this._agendamentoForm = new BuildForm()
        .add(new StringControl('prosseguirComPedidoLogistica', 'true', [Validators.required]))
        .add(new StringControl('justificativa', [Validators.required]));
    }

    public form(): FormGroup {
      return this._agendamentoForm.form;
    }
    public preencherFormulario(entidade: LogisticaDoadorColetaDTO): void {
      throw new Error("Method not implemented.");
    }

    /**
     * Salva os dados atualizados da logística e encerra a tarefa.
     */
    public salvar(): void{
      this.validarListaUpload();

      this.dto.prosseguirComPedidoLogistica = this._prosseguir;
      this.logisticaService.salvarLogisticaDoadorColeta(this._idPedidoLogistica, this.dto).then(retorno => {
        let modal: Modal = this.messageBox.alert(retorno);
        modal.show();
        },
        (error: ErroMensagem) => {
          ErroUtil.exibirMensagemErro(error, this.messageBox);
        });
    }

    public finalizar():void{
      this.validarListaUpload();

      let objForm = this._agendamentoForm.value;

      if(!this._prosseguir){
        this.dto.transporteTerrestre.forEach(transporte => transporte.excluido = true);
        this.dto.hospedagens.forEach(voucher => voucher.excluido = true);
        this.dto.aereos.forEach(voucher => voucher.excluido = true);
        this.dto.justificativa = objForm.justificativa;
        this.dto.observacao = "";
      }

      this.dto.prosseguirComPedidoLogistica = this._prosseguir;
      this.logisticaService.encerrarLogisticaDoadorColeta(this._idPedidoLogistica, this.dto).then(retorno => {
          this.modal.mensagem = retorno;
          this.modal.abrirModal();
      });
    }

    obterDetalhePedidoLogistica(): void{

        this.logisticaService.obterPedidoLogisticaDoadorColetaCustomizado(this._idPedidoLogistica).then(res => {
            this.dto = new LogisticaDoadorColetaDTO;
            Object.assign(this.dto, res);

            this._dataColeta = this.dto.dataColeta;
            this.voucherTransporteAereo.listaDownload = this.dto.aereos;
            this.voucherHospedagem.listaDownload = this.dto.hospedagens;
            this.transporteTerrestreComponent.listaTransportesTerrestres = this.dto.transporteTerrestre;

            this.centroTransplanteService.obterCentroTransplante(this.dto.idCentroColeta).then(res => {
                this._centroTransplante = new CentroTransplante().jsonToEntity(res);
            },
            (error:ErroMensagem) => {
                this.exibirMensagemErro(error);
            });
        },
        (error:ErroMensagem) => {
            this.exibirMensagemErro(error);
        });
    }

    obterDetalhesDoador(){
      this.doadorService.obterDetalhesDoador(this._idDoador).then(res => {
          this._dadosDoador = new DoadorNacional().jsonToEntity(res);
      },
      (error:ErroMensagem) => {
          this.exibirMensagemErro(error);
      });

      this.headerDoador.popularCabecalhoIdentificacaoNoCache(this._idDoador);
    }

    private exibirMensagemErro(error: ErroMensagem) {
        error.listaCampoMensagem.forEach(obj => {
            this.modal.mensagem = obj.mensagem;
        })
        this.modal.abrirModal();
    }

    public isBrasil(pais: Pais): boolean{
        return pais && pais.id == 1;
    }

    public cancelarInclusaoTransporte() {
        this._mostraFormulario = 'hide';
        this._mostraDados = '';
        this.transporteTerrestreComponent.clearForm();
    }

    incluirTransporteAereo() {
        this.voucherTransporteAereo.incluirVoucher(this._idPedidoLogistica, TiposVoucher.TRANSPORTE_AEREO);
        this._mostrarIncluirTransporteAereo = "hide";
    }

    public adicionarTransporte(): void{
        if(this.transporteTerrestreComponent.validateForm()){
            if(this.dto.transporteTerrestre == null){
                this.dto.transporteTerrestre = [];
            }

            let transporte: TransporteTerrestre =
                this.transporteTerrestreComponent.recuperarObjetoForm();
            transporte.pedidoLogistica = new PedidoLogistica();
            transporte.pedidoLogistica.id = this._idPedidoLogistica;

            this.dto.transporteTerrestre.push( transporte );

            this.cancelarInclusaoTransporte();
        }
    }

    editar() {
        this._mostraDados = 'hide';
        this._mostraFormulario = '';
    }

    public toDateTimeString(date: Date): string{
        return DataUtil.toDateFormat(date, DateTypeFormats.DATE_TIME);
    }
    private adicionarRetorno(transporte:TransporteTerrestre){
        this.editar();
        let transporteForm = this.transporteTerrestreComponent.form();
        transporteForm.get("origem").setValue(transporte.destino);
        transporteForm.get("destino").setValue(transporte.origem);
    }

    public toDateString(date: Date): string{
        return DataUtil.toDateFormat(date, DateTypeFormats.DATE_ONLY);
    }

    private validarListaUpload() {
        if (this.voucherTransporteAereo.lista.length != 0) {
            this.dto.aereos = this.voucherTransporteAereo.lista;
            console.log(this.dto.aereos);
        }
        if (this.voucherHospedagem.lista.length != 0) {
            this.dto.hospedagens = this.voucherHospedagem.lista;
        }
    }

    cancelarEdicaoTransporteAereo() {
        this._mostrarIncluirTransporteAereo = "";
    }

    enviarEdicaoTransporteAereo() {
        this._mostrarIncluirTransporteAereo = "";
    }

    cancelarEdicaoHospedagem() {
        this._mostrarIncluirHospedagem = "";
    }

    enviarEdicaoHospedagem() {
        this._mostrarIncluirHospedagem = "";
    }

    incluirHospedagem() {
        this.voucherHospedagem.incluirVoucher(this._idPedidoLogistica, TiposVoucher.HOSPEDAGEM);
        this._mostrarIncluirHospedagem = "hide";
    }

    edtiarObservacao() {
        this._observacao = this.dto.observacao;
        this._mostraDadosObservacao = "hide";
        this._mostraFormularioObservacao = "";
    }

    cancelarEdicaoObservacao() {
        this._mostraFormularioObservacao = "hide";
        this._mostraDadosObservacao = "";
        this._observacao = "";
    }

    salvarObservacao() {
        this.dto.observacao = this._observacao;
        this.cancelarEdicaoObservacao();
    }

    fecharModal() {
        this.router.navigateByUrl(HistoricoNavegacao.urlRetorno());
    }

    public abrirModal(){
        this.modal.show();
    }

    public voltarParaListagem(): void{
        this.router.navigateByUrl(HistoricoNavegacao.urlRetorno());
    }

    public excluirTransporteTerrestre(transporteIndex: number): void{
        if(this.dto.transporteTerrestre.length == 1){
            this.dto.transporteTerrestre = [];
        }
        else{
            this.dto.transporteTerrestre.splice(transporteIndex, 1);
        }
    }

    isProsseguirComPedidoLogistica(value: string): void {
      if(value == 'true') {
         this._mostraProsseguir = "";
         this._mostraJustificativa = "hide";
         this._prosseguir = true;
      }else{
        this._mostraProsseguir = "hide";
        this._mostraJustificativa = "";
        this._prosseguir = false;
      }
    }


	public get mostraDados(): string  {
		return this._mostraDados;
	}

	public get mostraFormulario(): string  {
		return this._mostraFormulario;
	}

	public get mostrarIncluirTransporteAereo(): string {
		return this._mostrarIncluirTransporteAereo;
	}

	public get mostrarIncluirHospedagem(): string {
		return this._mostrarIncluirHospedagem;
	}

	public get mostraDadosObservacao(): string  {
		return this._mostraDadosObservacao;
	}

	public get mostraFormularioObservacao(): string  {
		return this._mostraFormularioObservacao;
    }

	public get mostrarEditarObservacao(): string {
		return this._mostrarEditarObservacao;
	}

	public get observacao(): string {
		return this._observacao;
	}

	public set observacao(value: string) {
		this._observacao = value;
	}

    /**
     * Getter centroTransplante
     * @return {CentroTransplante}
     */
	public get centroTransplante(): CentroTransplante {
		return this._centroTransplante;
	}

    /**
     * Setter centroTransplante
     * @param {CentroTransplante} value
     */
	public set centroTransplante(value: CentroTransplante) {
		this._centroTransplante = value;
	}


    /**
     * Getter dadosDoador
     * @return {Doador}
     */
	public get dadosDoador(): DoadorNacional {
		return this._dadosDoador;
	}

    /**
     * Setter dadosDoador
     * @param {Doador} value
     */
	public set dadosDoador(value: DoadorNacional) {
		this._dadosDoador = value;
	}

    /**
     * Getter dataColeta
     * @return {Date}
     */
	public get dataColeta(): Date {
		return this._dataColeta;
	}

    /**
     * Setter dataColeta
     * @param {Date} value
     */
	public set dataColeta(value: Date) {
		this._dataColeta = value;
	}


    /**
     * Getter prosseguir
     * @return {boolean }
     */
	public get prosseguir(): boolean  {
		return this._prosseguir;
	}

    /**
     * Setter prosseguir
     * @param {boolean } value
     */
	public set prosseguir(value: boolean ) {
		this._prosseguir = value;
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


    /**
     * Getter mostraProsseguir
     * @return {string }
     */
	public get mostraProsseguir(): string  {
		return this._mostraProsseguir;
	}

    /**
     * Setter mostraProsseguir
     * @param {string } value
     */
	public set mostraProsseguir(value: string ) {
		this._mostraProsseguir = value;
	}

    /**
     * Getter mostraJustificativa
     * @return {string }
     */
	public get mostraJustificativa(): string  {
		return this._mostraJustificativa;
	}

    /**
     * Setter mostraJustificativa
     * @param {string } value
     */
	public set mostraJustificativa(value: string ) {
		this._mostraJustificativa = value;
	}

};
