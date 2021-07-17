import { Component, OnInit } from "@angular/core";
import { FormBuilder, FormGroup } from "@angular/forms";
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { DetalheLogisticaMaterialDTO } from "app/shared/dto/detalhe.logistica.material.dto";
import { DetalheLogisticaColetaInternacionalDTO } from "app/shared/dto/detalhe.logitica.coleta.internacional.dto";
import { LogisticaService } from "app/shared/service/logistica.service";
import { TransportadoraService } from "app/shared/service/transportadora.service";
import { BaseForm } from "../../../../../shared/base.form";
import { TiposDoador } from '../../../../../shared/enums/tipos.doador';
import { ErroMensagem } from '../../../../../shared/erromensagem';
import { HistoricoNavegacao } from '../../../../../shared/historico.navegacao';
import { IModalComponent } from "../../../../../shared/modal/factory/i.modal.component";
import { IModalContent } from "../../../../../shared/modal/factory/i.modal.content";
import { Modal } from '../../../../../shared/modal/factory/modal.factory';
import { MessageBox } from '../../../../../shared/modal/message.box';
import { LogisticaMaterialService } from '../../../../../shared/service/logistica.material.service';
import { TarefaService } from "../../../../../shared/tarefa.service";
import { DataUtil } from '../../../../../shared/util/data.util';
import { DateTypeFormats } from '../../../../../shared/util/date/date.type.formats';
import { DoadorService } from '../../../../doador.service';


/**
 * Componente de detalhe da logística de material internacional coletado do doador.
 * Neste ponto são informados a data de retirada do material e a transportadora responsável.
 */
@Component({
    selector: 'modal-logistica-material-internacional',
    templateUrl: './modal.logistica.material.internacional.component.html'
})

export class ModalLogisticaMaterialInternacionalComponent extends BaseForm<DetalheLogisticaMaterialDTO> implements IModalContent, OnInit{

    public detalhe: DetalheLogisticaColetaInternacionalDTO;
    public hora: Array<string | RegExp>;
    private pedidoLogisticaId: number;

    public logisticaMaterialGroup: FormGroup;

    target: IModalComponent;
    close: (target: IModalComponent) => void;

    public datas: Array<string | RegExp>;
    data: any;

    constructor(private fb: FormBuilder, private router: Router, translate: TranslateService,
        private activatedRouter: ActivatedRoute, private doadorService: DoadorService,
        private builder: FormBuilder, private transportadoraService: TransportadoraService,
        private logisticaMaterialService: LogisticaMaterialService,
        private logisticaService: LogisticaService,
        private tarefaService: TarefaService,
        private messageBox: MessageBox) {
        super();
        this.translate = translate;
        this.datas = [/[0-9]/,/[0-9]/, '\/', /[0-9]/,/[0-9]/, '\/', /[0-9]/,/[0-9]/,/[0-9]/,/[0-9]/]

        this.logisticaMaterialGroup = builder.group({
            'retiradaIdDoador': [null],
            'retiradaHawb': [null],
            'nomeCourier': [null],
            'passaporteCourier': [null],
            'dataEmbarque': [null],
            'dataChegada': [null],
            'retiradaLocal': [null]
        });

        this.criarMensagensErro(this.logisticaMaterialGroup);
        this.setMensagensErro(this.logisticaMaterialGroup);
    }

    ngOnInit() {
        this.pedidoLogisticaId = this.data;
        this.logisticaMaterialService.obterLogisticaDoadorInternacionalColeta(this.pedidoLogisticaId).then(t => {
            this.detalhe = t;
            this.form().get("retiradaIdDoador").setValue(this.detalhe.retiradaIdDoador);
            this.form().get("retiradaHawb").setValue(this.detalhe.retiradaHawb);
            this.form().get("nomeCourier").setValue(this.detalhe.nomeCourier);
            this.form().get("passaporteCourier").setValue(this.detalhe.passaporteCourier);
            this.form().get("dataEmbarque").setValue(DataUtil.toDateFormat(this.detalhe.dataEmbarque, DateTypeFormats.DATE_ONLY));
            this.form().get("dataChegada").setValue(DataUtil.toDateFormat(this.detalhe.dataChegada, DateTypeFormats.DATE_ONLY));
            this.form().get("retiradaLocal").setValue(this.detalhe.retiradaLocal);
        },
        (error:ErroMensagem)=> {
            this.exibirMensagemDeAutorizacao(error);
        });
    }

    nomeComponente(): string {
        return "ModalLogisticaMaterialInternacionalComponent";
    }

    private exibirMensagemDeAutorizacao(error:ErroMensagem){
        var mensagem: string;
        error.listaCampoMensagem.forEach(obj => {
            mensagem = obj.mensagem;

        })
        var modal:Modal = this.messageBox.alert(mensagem);
        modal.closeOption = ()=>{
            this.router.navigateByUrl(HistoricoNavegacao.urlRetorno());
        }
        modal.show();
    }

    private setarCampos(){
        if(!this.validateForm()){
            return;
        }
        this.detalhe.retiradaIdDoador = this.getField(this.form(), "retiradaIdDoador").value;
        this.detalhe.retiradaHawb = this.getField(this.form(), "retiradaHawb").value;
        this.detalhe.nomeCourier = this.getField(this.form(), "nomeCourier").value;
        this.detalhe.passaporteCourier = this.getField(this.form(), "passaporteCourier").value;
        this.detalhe.dataEmbarque = DataUtil.dataStringToDate(this.getField(this.form(), "dataEmbarque").value);
        this.detalhe.dataChegada = DataUtil.dataStringToDate(this.getField(this.form(), "dataChegada").value);
        this.detalhe.retiradaLocal = this.getField(this.form(), "retiradaLocal").value;
    }

    public salvarLogistica(): void{
        this.setarCampos();
        this.logisticaService.salvarPedidoLogisticaMaterialColetaInternacional(this.pedidoLogisticaId, this.detalhe).then(msg => {
          let modal: Modal = this.messageBox.alert(msg);
          this.target.hide();
          modal.show();
        },
        (error:ErroMensagem)=> {
            this.messageBox.alert(error.mensagem.valueOf()).show();
        });
    }

    public form(): FormGroup {
        return this.logisticaMaterialGroup;
    }

    public preencherFormulario(entidade: DetalheLogisticaMaterialDTO): void {
        throw new Error("Method not implemented.");
    }

    public formatarData(data: Date): string{
        return DataUtil.toDateFormat(data, DateTypeFormats.DATE_ONLY);
    }

    public isCordao(): boolean{
        return this.detalhe &&
            (this.detalhe.idTipoDoador == TiposDoador.CORDAO_NACIONAL ||
                this.detalhe.idTipoDoador == TiposDoador.CORDAO_INTERNACIONAL);
	}

	public isMedula(): boolean{
        return this.detalhe &&
            (this.detalhe.idTipoDoador == TiposDoador.NACIONAL ||
                this.detalhe.idTipoDoador == TiposDoador.INTERNACIONAL);
    }

    public obterDocumento(relatorio: string) {
        this.logisticaMaterialService.baixarDocumentos(this.pedidoLogisticaId, relatorio);
    }

}
