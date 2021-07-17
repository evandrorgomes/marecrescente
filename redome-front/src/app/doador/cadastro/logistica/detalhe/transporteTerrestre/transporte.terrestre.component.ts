import { BaseForm } from '../../../../../shared/base.form';
import { TranslateService } from '@ngx-translate/core';
import { Component, OnInit, Input, ViewEncapsulation } from '@angular/core';
import { FormBuilder, FormGroup, FormControl, Validators, FormArray } from "@angular/forms";
import { DominioService } from '../../../../../shared/dominio/dominio.service';
import { DateMoment } from '../../../../../shared/util/date/date.moment';
import { DateTypeFormats } from '../../../../../shared/util/date/date.type.formats';
import { StringUtil } from '../../../../../shared/util/string.util';
import { TransporteTerrestre } from 'app/shared/model/transporte.terrestre';

/**
 * Componente visual que organiza o CRUD de transporte terrestre para a logística do doador.
 * @author Pizão
 */
@Component({
    selector: "transporte-terrestre-component",
    moduleId: module.id,
    templateUrl: "./transporte.terrestre.component.html"
})
export class TransporteTerrestreComponent extends BaseForm<TransporteTerrestre> implements OnInit {

    public transporteTerrestreForm: FormGroup;
    public data: Array<string | RegExp>;
    public mensagens;

    @Input()
    public listaTransportesTerrestres: TransporteTerrestre[];


    constructor(private fb: FormBuilder,
        private serviceDominio: DominioService,
        translate: TranslateService) {
        super();
        this.translate = translate;
        this.buildForm();
        this.data = [/[0-9]/,/[0-9]/, '\/', /[0-9]/,/[0-9]/, '\/', /[0-9]/,/[0-9]/,/[0-9]/,/[0-9]/, ' ', /[0-2]/, /[0-9]/,':',/[0-5]/,/[0-9]/]

        this.translate.get('workup.logistica.labels').subscribe(res => {
            this._formLabels = res;
            this.criarMensagemValidacaoPorFormGroup(this.transporteTerrestreForm);
            this.setMensagensErro(this.transporteTerrestreForm);
        });

        this.translate.get('workup.logistica.mensagens').subscribe(res => {
            this.mensagens = res;
        });
    }

    ngOnInit() {}

    /**
     * Método principal de construção do formulário
     * @author Rafael Pizão
     */
    buildForm() {
        this.transporteTerrestreForm = this.fb.group({
            'origem': [null, Validators.required],
            'destino': [null, Validators.required],
            'dataRealizacao': [null, Validators.required],
            'objetoTransportado': [null, Validators.required]
        });
    }

    /**
     * Verifica se o formulário é válido.
     * @author Rafael Pizão
     */
    public validateForm(): boolean {
        return super.validateForm();
    }

    public form(): FormGroup{
        return this.transporteTerrestreForm;
    }

    public preencherFormulario(transp: TransporteTerrestre): void {
        if(transp){
            let dateMoment: DateMoment = DateMoment.getInstance();

            this.setPropertyValue("origem", transp.origem);
            this.setPropertyValue("destino", transp.destino);
            this.setPropertyValue("dataRealizacao",
                dateMoment.format(transp.dataRealizacao, DateTypeFormats.DATE_TIME));
            this.setPropertyValue("objetoTransportado", transp.objetoTransportado);
        }
    }

    public recuperarObjetoForm(): TransporteTerrestre{
        let dateMoment: DateMoment = DateMoment.getInstance();

        let transp: TransporteTerrestre = new TransporteTerrestre();
        transp.dataRealizacao =
            dateMoment.parse(this.form().get("dataRealizacao").value, DateTypeFormats.DATE_TIME);
        transp.origem = this.form().get("origem").value;
        transp.destino = this.form().get("destino").value;
        transp.objetoTransportado = this.form().get("objetoTransportado").value;
        return transp;
    }

    nomeComponente(): string {
        return "TransporteTerrestreComponent";
    }

    public setMensagemErroPorCampo(nomeCampo: string) {
        super.setMensagemErroPorCampo(nomeCampo);

        let dataRealizacaoOriginal: string = this.form().get("dataRealizacao").value;

        if(!StringUtil.isNullOrEmpty(dataRealizacaoOriginal)){
            let dateMoment: DateMoment = DateMoment.getInstance();
            let dataRealizacao: Date =
                dateMoment.parse(dataRealizacaoOriginal, DateTypeFormats.DATE_TIME);

            this.formErrors["dataRealizacao"] = "";
            if(dateMoment.isInvalid(dataRealizacao)){
                this.markAsInvalid(this.form().get("dataRealizacao"));
                this.formErrors["dataRealizacao"] = this.mensagens.dataInvalida;
            }
        }
    }


};
