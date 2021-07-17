import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { CentroTransplanteService } from 'app/admin/centrotransplante/centrotransplante.service';
import { HeaderDoadorComponent } from 'app/doador/consulta/header/header.doador.component';
import { Doador } from 'app/doador/doador';
import { DoadorNacional } from 'app/doador/doador.nacional';
import { CentroTransplante } from 'app/shared/dominio/centro.transplante';
import { LogisticaDoadorWorkupDTO } from 'app/shared/dto/logistica.doador.workup.dto';
import { PedidoLogistica } from 'app/shared/model/pedido.logistica';
import { TransporteTerrestre } from 'app/shared/model/transporte.terrestre';
import { LogisticaService } from 'app/shared/service/logistica.service';
import { PedidoWorkupService } from 'app/shared/service/pedido.workup.service';
import { RouterUtil } from 'app/shared/util/router.util';
import { Pais } from '../../../../shared/dominio/pais';
import { TiposVoucher } from '../../../../shared/enums/tipos.voucher';
import { ErroMensagem } from '../../../../shared/erromensagem';
import { HistoricoNavegacao } from '../../../../shared/historico.navegacao';
import { DataUtil } from '../../../../shared/util/data.util';
import { DateTypeFormats } from '../../../../shared/util/date/date.type.formats';
import { DoadorService } from '../../../doador.service';
import { ArquivoVoucherLogisticaComponent } from './arquivo.voucher.logistica.component';
import { TransporteTerrestreComponent } from './transporteTerrestre/transporte.terrestre.component';

/**
 * Classe que representa o componente para detalhar as informações de logística de doador.
 * @author ergomes
 */
@Component({
    selector: "detalhar-logistica",
    templateUrl: './detalhar.logistica.component.html'
})
export class DetalharLogisticaComponent implements OnInit {

    private _idPedidoLogistica: number;
    private _idDoador: number;
    private _dataExameMedico1: Date;
    private _dataExameMedico2: Date;
    private _observacao: string;

    private _dadosDoador: DoadorNacional;
    private _centroTransplante: CentroTransplante;

    /**
     * Detalhes da logística do doador.
     * Deve ser utilizado para preencher a tela de detalhe do pedido.
     */
    public dto: LogisticaDoadorWorkupDTO;

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

    // Guarda os textos internacionalizados.
    public textos: any;

    private _mostraDados: string = '';
    private _mostraFormulario: string = 'hide';
    private _mostrarIncluirTransporteAereo: string= "";
    private _mostrarIncluirHospedagem: string= "";
    private _mostraDadosObservacao: string = '';
    private _mostraFormularioObservacao: string = 'hide';
    private _mostrarEditarObservacao: string= "";


    constructor(private router: Router,
        public translate: TranslateService,
        private activatedRouter: ActivatedRoute,
        private logisticaService: LogisticaService,
        private doadorService: DoadorService,
        private pedidoWorkupService: PedidoWorkupService,
        private centroTransplanteService: CentroTransplanteService) {

        this.translate.get('workup.detalhe').subscribe(res => {
            this.textos = res;
        });
    }

    /**
     * Nome do componente atual
     */
    nomeComponente(): string {
        return "DetalharLogisticaComponent";
    }

    /**
     * Inicializa a classe com os dados buscando no serviço REST
     *
     */
    ngOnInit(): void {

       RouterUtil.recuperarParametrosDaActivatedRoute(this.activatedRouter, ["idPedidoLogistica", "idDoador"]).then(res => {
            this._idPedidoLogistica = <number>res['idPedidoLogistica'];
            this._idDoador = <number>res['idDoador'];

            this.obterDetalhePedidoLogistica();
            this.obterDetalhesDoador();
        });
    }

    obterDetalhePedidoLogistica(): void{

        this.logisticaService.obterPedidoLogisticaCustomizado(this._idPedidoLogistica).then(res => {
            let idPedidoWorkup = res.idPedidoWorkup;
            this.dto = new LogisticaDoadorWorkupDTO;
            Object.assign(this.dto, res);

            this.voucherTransporteAereo.listaDownload = this.dto.aereos;
            this.voucherHospedagem.listaDownload = this.dto.hospedagens;
            this.transporteTerrestreComponent.listaTransportesTerrestres = this.dto.transporteTerrestre;

            this.obterPlanoWorkupNacional(idPedidoWorkup);
        },
        (error:ErroMensagem) => {
            this.exibirMensagemErro(error);
        });
    }

    obterPlanoWorkupNacional(idPedidoWorkup: number){

        this.pedidoWorkupService.obterPlanoWorkupNacional(idPedidoWorkup).then(res =>{
            this._dataExameMedico1 = res.dataExameMedico1;
            this._dataExameMedico2 = res.dataExameMedico2;
            let idCentroTransplante = res.idCentroTransplante;

            this.centroTransplanteService.obterCentroTransplante(idCentroTransplante).then(res => {
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

    /**
     * Salva os dados atualizados da logística e encerra a tarefa.
     */
    public salvar(): void{
        this.validarListaUpload();

        this.logisticaService.salvarLogistica(this._idPedidoLogistica, this.dto).then(retorno => {
            this.modal.mensagem = retorno;
            this.modal.abrirModal();
        });
    }

    public finalizar():void{
        this.validarListaUpload();

        this.logisticaService.encerrarLogisticaDoadorWorkup(this._idPedidoLogistica, this.dto).then(retorno => {
            this.modal.mensagem = retorno;
            this.modal.abrirModal();
        });
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
     * Getter dataExameMedico1
     * @return {Date}
     */
	public get dataExameMedico1(): Date {
		return this._dataExameMedico1;
	}

    /**
     * Setter dataExameMedico1
     * @param {Date} value
     */
	public set dataExameMedico1(value: Date) {
		this._dataExameMedico1 = value;
	}

    /**
     * Getter dataExameMedico2
     * @return {Date}
     */
	public get dataExameMedico2(): Date {
		return this._dataExameMedico2;
	}

    /**
     * Setter dataExameMedico2
     * @param {Date} value
     */
	public set dataExameMedico2(value: Date) {
		this._dataExameMedico2 = value;
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

};
