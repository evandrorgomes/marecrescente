import { Component, OnInit } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { MessageBox } from 'app/shared/modal/message.box';
import { PedidoContatoSmsService } from 'app/shared/service/pedido.contato.sms.service';
import { SmsVo } from '../../../shared/vo/sms.vo';
import { ErroMensagem } from '../../../shared/erromensagem';
import { ErroUtil } from '../../../shared/util/erro.util';
import { Paginacao } from 'app/shared/paginacao';
import { FormGroup, FormBuilder } from '@angular/forms';
import { DateMoment } from 'app/shared/util/date/date.moment';
import { DateTypeFormats } from 'app/shared/util/date/date.type.formats';
import { StatusSms, IStatusSms } from 'app/shared/enums/status.sms';

@Component({
    selector: 'consulta-sms',
    templateUrl: 'pedido.contato.sms.component.html'
})
export class PedidoContatoSmsComponent implements OnInit {

    public _form: FormGroup;

    public _paginacao: Paginacao;
    public _qtdRegistros: number = 10;

    public _maskData: Array<string | RegExp>;

    public _listaStatus: any[];

    private mensagemNenhumRegistro: string;

    constructor(private fb: FormBuilder, private translate: TranslateService, private messageBox: MessageBox, 
        private pedidoContatoSmsService: PedidoContatoSmsService) {

        this._maskData = [/[0-9]/, /[0-9]/, '\/', /[0-9]/, /[0-9]/, '\/', /[0-9]/, /[0-9]/, /[0-9]/, /[0-9]/]

        this._paginacao = new Paginacao('', 0, this._qtdRegistros);
        this._paginacao.number = 1;

        this._form = this.fb.group({
            'dmr': [null, null],
            'dataInicial': [null, null],
            'dataFinal': [null, null],
            'status': [null, null]
        });

        this._listaStatus = this.listarStatus();

    }

    ngOnInit() {
        this.translate.get("mesnagem.nenhumregistro").subscribe(res => {
            this.mensagemNenhumRegistro = res;
        });
        
    }

    nomeComponente(): string {
        return "PedidoContatoSmsComponent";
    }

    private listar(pagina: number) {
        let dmr: number = this._form.get("dmr").value;
        let dataInicial: Date;
        if (this._form.get("dataInicial").value) {
            dataInicial = DateMoment.getInstance().parse(this._form.get("dataInicial").value, DateTypeFormats.DATE_ONLY) ;
        }
        let dataFinal: Date;
        if (this._form.get("dataFinal").value) {
            dataFinal = DateMoment.getInstance().parse(this._form.get("dataFinal").value, DateTypeFormats.DATE_ONLY) ;
        }
        let status: IStatusSms;
        if (this._form.get("status").value) {
            status =  StatusSms.parser(Number.parseInt(this._form.get("status").value));
        }

        this.pedidoContatoSmsService.listarSmsEnviados(dmr, dataInicial, dataFinal, status, pagina - 1, this._qtdRegistros).then(res => {
            let listaSmsVo: SmsVo[] = [];
            if (res && res.content) {
                res.content.forEach(sms => {
                    listaSmsVo.push(new SmsVo().jsonToEntity(sms));
                })
            }
            this._paginacao.content = listaSmsVo;
            this._paginacao.totalElements = res.totalElements;
            this._paginacao.quantidadeRegistro = this._qtdRegistros;
            if (listaSmsVo.length == 0) {
                this.messageBox.alert(this.mensagemNenhumRegistro).show();
            }
        },
        (error: ErroMensagem) => {
            ErroUtil.exibirMensagemErro(error, this.messageBox);
        })
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
        this.listar(1);
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
        this.listar(event.page);
    }

    public consultar() {
        this.listar(1);
    }

    public listarStatus(): any[] {
        let lista: any[] = [];
        for(let value in StatusSms) {             
            if (value !== 'parser') {
                lista.push(StatusSms.parser(value));
            }
        }

        return lista;
    }

}