import { Component, OnInit } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { TranslateService } from "@ngx-translate/core";
import { BaseForm } from "../../../shared/base.form";
import { TiposTarefa } from "../../../shared/enums/tipos.tarefa";
import { ErroMensagem } from "../../../shared/erromensagem";
import { IModalComponent } from "../../../shared/modal/factory/i.modal.component";
import { IModalContent } from "../../../shared/modal/factory/i.modal.content";
import { MessageBox } from "../../../shared/modal/message.box";
import { DateTypeFormats } from "../../../shared/util/date/date.type.formats";
import { ErroUtil } from '../../../shared/util/erro.util';
import { ValidateDataHora } from "../../../validators/data.validator";
import { PedidoTransporteService } from "../../pedido.transporte.service";
import { DateMoment } from './../../../shared/util/date/date.moment';

@Component({
    selector: 'modal-retirada-entrega-transporte-material-internacional',
    templateUrl: './modal.retirada.entrega.transporte.material.inter.component.html',
    providers: []
})
export class ModalRetiradaEntregaTransporteMaterialInternacionalComponent extends BaseForm<Object> implements IModalContent, OnInit {

    target: IModalComponent;
    close: (target: IModalComponent) => void;
    data: any;

    maskData: Array<string | RegExp>;
    private _retiradaEntregaForm: FormGroup;

    constructor(translate: TranslateService, private fb: FormBuilder, private messageBox: MessageBox,
        private pedidoTransporteService: PedidoTransporteService) {
        super();
        this.translate = translate;
        this.maskData = [/[0-9]/,/[0-9]/, '\/', /[0-9]/,/[0-9]/, '\/', /[0-9]/,/[0-9]/,/[0-9]/,/[0-9]/, ' ', /[0-2]/, /[0-9]/,':',/[0-5]/,/[0-9]/];
        this.buildForm();
    }

    ngOnInit(): void {
        this.translate.get("transporteMaterial.retiradaentrega.label").subscribe(res => {
            this._formLabels = res;            
            this.criarMensagensErro(this.form());
            this.setMensagensErro(this.form());
        });

        if (this.data._pedidoTransporteDTOSelecionado.tipoTarefa.id == TiposTarefa.RETIRADA_MATERIAL_INTERNACIONAL.id) {
            this._retiradaEntregaForm.get("dataRetirada").setValidators(Validators.compose([Validators.required, ValidateDataHora]));            
            this._retiradaEntregaForm.get("dataEntrega").setValidators(null);            
        }
        else {
            this._retiradaEntregaForm.get("dataRetirada").setValidators(null);
            this._retiradaEntregaForm.get("dataEntrega").setValidators(Validators.compose([Validators.required, ValidateDataHora]));
        }
        this._retiradaEntregaForm.reset();
        this.clearForm();

    }

        /**
     * Método principal de construção do formulário
     * @author Bruno Sousa
     */
    buildForm() {
        this._retiradaEntregaForm = this.fb.group({
            'dataRetirada': [null, Validators.compose([Validators.required, ValidateDataHora])],
            'dataEntrega': [null, Validators.compose([Validators.required, ValidateDataHora])]
        });
    }

    public form(): FormGroup {
        return this._retiradaEntregaForm;
    }
    
    public preencherFormulario(entidade: Object): void {
        throw new Error("Method not implemented.");
    }

    validateForm(): boolean {
        let valid: boolean  = super.validateForm();
        if(!valid){
            return false;
        }

        let dataValida: boolean = true;
        if (this.data._pedidoTransporteDTOSelecionado.tipoTarefa.id === TiposTarefa.ENTREGA_MATERIAL_INTERNACIONAL.id) {
            let dateMoment: DateMoment = DateMoment.getInstance();
            let dataEntrega = dateMoment.parse(this.form().get("dataEntrega").value, DateTypeFormats.DATE_TIME);
            let dataRetirada = new Date(this.data._pedidoTransporteDTOSelecionado.dataRetirada);
            dataValida = dateMoment.isDateTimeSameOrBefore(dataRetirada, dataEntrega);
            if (!dataValida) {
                this.forceError("dataEntrega", this._formLabels['dataEntregaMenorDataRetirada']);
            }
        }
        return dataValida;
    }

    confirmar() {
        if (this.validateForm()) {
            this.target.hide();
            let dateMoment: DateMoment = DateMoment.getInstance();
            if (this.data._pedidoTransporteDTOSelecionado.tipoTarefa == TiposTarefa.RETIRADA_MATERIAL_INTERNACIONAL) {
                let dataRetirada: Date = dateMoment.parse(this.form().get("dataRetirada").value, DateTypeFormats.DATE_TIME);
                dataRetirada.toJSON = dateMoment.getToJson();

               this.pedidoTransporteService.confirmarRetiradaTransporteMaterial(
                    this.data._pedidoTransporteDTOSelecionado.idPedidoTransporte, dataRetirada).then(res => {
                        this.messageBox.alert(res.mensagem)
                            .withTarget(this.target.target)
                            .withCloseOption(this.data.fechar)
                            .show();
                })
                .catch((error: ErroMensagem) => {
                    ErroUtil.exibirMensagemErro(error, this.messageBox);
                });
            }
            else if (this.data._pedidoTransporteDTOSelecionado.tipoTarefa == TiposTarefa.ENTREGA_MATERIAL_INTERNACIONAL) {
                let dataEntrega: Date = dateMoment.parse(this.form().get("dataEntrega").value, DateTypeFormats.DATE_TIME);
                dataEntrega.toJSON = dateMoment.getToJson();

                console.log(dataEntrega);
                this.pedidoTransporteService.confirmarEntregaTransporteMaterial(
                    this.data._pedidoTransporteDTOSelecionado.idPedidoTransporte, dataEntrega).then(res=> {
                    this.messageBox.alert(res.mensagem)
                        .withTarget(this.target.target)
                        .withCloseOption(this.data.fechar)
                        .show();
                })
                .catch((error: ErroMensagem) => {
                    ErroUtil.exibirMensagemErro(error, this.messageBox);
                });
            }
        }
    }

    exibirDataRetirada(): boolean {
        return this.data._pedidoTransporteDTOSelecionado ? 
                this.data._pedidoTransporteDTOSelecionado.tipoTarefa == TiposTarefa.RETIRADA_MATERIAL_INTERNACIONAL :
                false;
    }

    exibirDataEntrega(): boolean {
        return this.data._pedidoTransporteDTOSelecionado ? 
                this.data._pedidoTransporteDTOSelecionado.tipoTarefa == TiposTarefa.ENTREGA_MATERIAL_INTERNACIONAL :
                false;
    }

    public exibirDataPrevistaRetirada(): string{
        if(!this.data || !this.data._pedidoTransporteDTOSelecionado){
            return "";
        }
        let dataPrevista: string = this.data._pedidoTransporteDTOSelecionado.dataRetirada;
        return dataPrevista;
    }

    nomeComponente(): string {
        throw new Error("Method not implemented.");
    }


}
