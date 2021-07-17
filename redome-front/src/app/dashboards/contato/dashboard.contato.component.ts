import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { BsDatepickerConfig } from 'ngx-bootstrap/datepicker';
import { DashboardContatoService } from '../../shared/service/dashboard.contato.service';
import { DateMoment } from '../../shared/util/date/date.moment';
import { ContatoVo } from '../../shared/vo/contato.vo';
import { MessageBox } from '../../shared/modal/message.box';
import { ErroMensagem } from '../../shared/erromensagem';
import { ErroUtil } from '../../shared/util/erro.util';
import { DetalheContatoVo } from 'app/shared/vo/detalhe.contato.vo';
import { detachEmbeddedView } from '@angular/core/src/view';
import { Paginacao } from 'app/shared/paginacao';

@Component({
    selector: 'dashboard-contato',
    moduleId: module.id,
    templateUrl: 'dashboard.contato.component.html',
    styleUrls: ['./dashboard.contato.component.css'],
    //encapsulation: ViewEncapsulation.Emulated
})
export class DashboardContatoComponent implements OnInit, OnDestroy {

    @ViewChild('rangePicker') rangePicker;

    public bsConfig: Partial<BsDatepickerConfig>;

    public _range: Date[];
    public _maxDate: Date = new Date();

    private titulos: string[];

    public _quadroAtual: number = 0;

    private subscribers: any[] = [];

    public _contatoVo: ContatoVo;

    public _paginacao: Paginacao;
    public _qtdRegistro: number = 10;
    private paginaAtual: number = 1;

    constructor(private translate: TranslateService, private messageBox: MessageBox,
         private dashboardContatoService: DashboardContatoService) {
        
        this._paginacao = new Paginacao([], 0, this._qtdRegistro);
        this._paginacao.number = 1;

        this.bsConfig = Object.assign({}, {containerClass: 'theme-default', locale: 'ptBr', showWeekNumbers: false});
        this._range = [new Date(), new Date() ];
        this._range[0].setDate(this._range[0].getDate() - 15);
     }

    ngOnInit() { 

        this.subscribers[0] = this.rangePicker.bsValueChange.subscribe(range => {
            if (range) {
                this.obterTotais(range);
            }
            
        });
        
        this.subscribers[1] = this.translate.get('dashboard.contato').subscribe(res => {
            this.titulos = [];
            this.titulos[0] = res['enriquecimento'].fase2;
            this.titulos[1] = res['fase2'];
            this.titulos[2] = res['enriquecimento'].fase3;
            this.titulos[3] = res['fase3'];
        })

    }

    ngOnDestroy() {
        this.subscribers[0].unsubscribe();
        this.subscribers[1].unsubscribe();
    }

    _dataInicial(): string {
        return DateMoment.getInstance().formatWithPattern(this._range[0], 'LL');
    }

    _dataFinal(): string {
        return DateMoment.getInstance().formatWithPattern(this._range[1], 'LL');
    }

    onDateRangePickerShow() {
        const moment = DateMoment.getInstance();
        let prevMonth = moment.adicionarMeses(new Date(), -1);
        if (moment.isDateBefore(this._range[0], prevMonth)) {
            prevMonth = this._range[0];
        }

        this.rangePicker._datepicker.instance.monthSelectHandler({ date: prevMonth });
    }

    obterTitulo(): string {
        return this.titulos ? this.titulos[this._quadroAtual]: '';
    }

    listarPedidosPorQuadro(quadro: number): void {
        this._quadroAtual = quadro;
        this.montarListaDetlahe();
    }

    obterTotais(range: Date[]) {
        this.dashboardContatoService.obterTotaisContato(range[0], range[1]).then(res => {
            this._contatoVo = new ContatoVo().jsonToEntity(res);
            this.montarListaDetlahe();
        },
        (error: ErroMensagem) => {
            ErroUtil.exibirMensagemErro(error, this.messageBox);            
        });
    }

    private montarListaDetlahe(): void {
        this._paginacao.content = [];
        let tipoPedido = this._quadroAtual == 0 || this._quadroAtual == 2 ? 'E': 'C';
        let fase = this._quadroAtual < 2 ? 1: 2;

        let listaAux: DetalheContatoVo[] = this._contatoVo.detalhes.filter(
            detalhe => detalhe.fase == fase && detalhe.tipoPedido == tipoPedido
        );

        let primeiroRegistro: number = (this.paginaAtual - 1) * this._qtdRegistro;
        let ultimoRegistro: number = this._qtdRegistro * this.paginaAtual;
        if (ultimoRegistro > listaAux.length) {
            this._paginacao.content = listaAux.slice(primeiroRegistro)
        }
        else {
            this._paginacao.content = listaAux.slice(primeiroRegistro, ultimoRegistro);
        }
        this._paginacao.totalElements = listaAux.length;
        this._paginacao.quantidadeRegistro = this._qtdRegistro;

    }

    /**
     * Método acionado quando é alterado a quantidade de registros por página
     * 
     * @param {*} event 
     * @param {*} modal 
     * 
     * @memberOf ConsultaComponent
     */
    selecinaQuantidadePorPagina(event: any, modal: any) {
        this.paginaAtual = 1;
        this.montarListaDetlahe();
    }
    /**
     * Método acionado quando muda a página
     * 
     * @param {*} event 
     * @param {*} modal 
     * 
     * @memberOf ConsultaComponent
     */
    mudarPagina(event: any) {
        this.paginaAtual = event.page;
        this.montarListaDetlahe();
    }

    public recarregarDados() {
        this.obterTotais(this._range);
    }


}