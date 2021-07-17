import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { TranslateService } from '@ngx-translate/core';
import { TypeaheadMatch } from "ngx-bootstrap";
import { Observable } from 'rxjs';
import { BaseForm } from '../../../shared/base.form';
import { CampoMensagem } from '../../../shared/campo.mensagem';
import { Cid } from '../../../shared/dominio/cid';
import { DominioService } from "../../../shared/dominio/dominio.service";
import { EventEmitterService } from '../../../shared/event.emitter.service';
import { MessageBox } from '../../../shared/modal/message.box';
import { PacienteUtil } from '../../../shared/paciente.util';
import { DataUtil } from '../../../shared/util/data.util';
import { DateTypeFormats } from '../../../shared/util/date/date.type.formats';
import { localizado } from "../../../validators/localizar.validator";
import { PacienteConstantes } from '../../paciente.constantes';
import { Diagnostico } from './diagnostico';

/**
 * Classe que representa o componente de diagnóstico
 *
 * @export
 * @class DiagnosticoComponent
 * @extends {BaseForm}
 */
@Component({
    selector: "diagnostico-paciente",
    templateUrl: "./diagnostico.component.html",
    //styleUrls: ['../paciente.css']
})
export class DiagnosticoComponent extends BaseForm<Diagnostico> implements OnInit {

    diagnosticoForm: FormGroup;

    cids: Cid[] = [];

    private diagnostico: Diagnostico;

    cidNaoEncontrada: string;


    public dataSource: Observable<any>;

    public typeaheadNoResults: boolean;

    public data: Array<string | RegExp>

    public isContempladoPortaria: string;

    private _ultimoCidConsultado:string;

    public idadePaciente:number;

    // Guarda a referência da última cid selecionada.
    // Workaround para o componente de autopreenchimento.
    public ultimaCidSelecionada:Cid;

    private _mensagemCid: string;

    /**
     * Construtor
     * @param serviceDominio - serviço de dominio para buscar os dominios
     */
    constructor(private fb: FormBuilder, private serviceDominio:DominioService
        , translate: TranslateService, private messageBox: MessageBox){
        super();
        this.translate = translate;
        this.buildForm();

        this.buscarDescricaoCidInformada();

        this.data = [/[0-9]/,/[0-9]/, '\/', /[0-9]/,/[0-9]/, '\/', /[0-9]/,/[0-9]/,/[0-9]/,/[0-9]/]
    }

    ngOnInit() {
        EventEmitterService.get('onChangeCid');
        this.translate.get("pacienteForm.diagnosticoGroup").subscribe(res => {
            this._formLabels = res;
            this.criarMensagemValidacaoPorFormGroup(this.diagnosticoForm);
            this.setMensagensErro(this.diagnosticoForm);
            this.criarValidacoesCustomizadas();
        });

        this.translate.get("pacienteForm.diagnosticoGroup.mensagemCid").subscribe(res=> {
            this._mensagemCid = res;
        });

    }

    /**
     * Buscar CID a partir da descrição informada.
     *
     */
    private buscarDescricaoCidInformada():void{
        this.dataSource = Observable.create((observer: any) => {

            if(this.diagnosticoForm.get('cid').value != this._ultimoCidConsultado){
                this.serviceDominio.getCids(this.diagnosticoForm.get('cid').value).then(res => {
                    let lista: Cid[] = [];
                    res.forEach(i => {
                        let cid:Cid = new Cid(i.id, i.codigo, i.descricao,i.transplante, i.idadeMinima, i.idadeMaxima);
                        lista.push(cid);
                    });

                    observer.next(lista);
                });
            }

        });
    }

    /**
     * Reseta o formulário limpando mensagens de error e valores dos campos
     *
     * @author Fillipe Queiroz
     * @memberof DiagnosticoComponent
     */
    resetaFormulario(){
        this.clearMensagensErro(this.diagnosticoForm)
        this.diagnosticoForm.reset();
        this.setValoresPadroes();
        this.cids = [];
    }

    /**
     * Seta os valores default do formulario
     *
     * @author Fillipe Queiroz
     * @memberof DiagnosticoComponent
     */
    setValoresPadroes(){
        this.diagnosticoForm.get('portaria').setValue(false);
    }
    /**
     * Método principal de construção do formulário
     * @author Bruno Sousa
     */
    buildForm()  {

        // Formulário com as validações padrão para o diagnóstico.
        this.diagnosticoForm = this.fb.group({
            dataDiagnostico: [null, Validators.required],
            cid: [null, Validators.required],
            cidSelecionada: null,
            portaria: { value: false, disabled: true },
            idadeMinima: { value: false, disabled: true },
            idadeMaxima: { value: false, disabled: true },
        });

        //Chamada aos métodos default para que a tela funcione com validações
        // this.criarFormsErro(this.diagnosticoForm);
        //return this.diagnosticoForm;
    }

    /**
     * Cria validação customizada da cid
     * o tipo deve ser o mesmo tipo retornador pela classe validators
     * @author Bruno Sousa
     */
    criarValidacoesCustomizadas(){
        this.translate.get("mensagem.erro.nenhumalocalizada", {campo: this.getLabel('cid') }).subscribe(res=> {
            var campoMensagem: CampoMensagem = new CampoMensagem();
            campoMensagem.campo = 'cid';
            campoMensagem.tipo = PacienteConstantes.VALIDATOR_LOCALIZADO;
            campoMensagem.mensagem = res;
            this.validationMessages.push(campoMensagem);
        });
    }


    /**
     * Método que é executando quando a CID é selecionada
     *
     * @param {TypeaheadMatch} e
     * @memberof DiagnosticoComponent
     */
    public typeaheadOnSelect(e: TypeaheadMatch): void {

        let cid:Cid = (<Cid>e.item);
        this.clearMensagensErro(this.diagnosticoForm, null, 'cid');
        this.diagnosticoForm.get("cidSelecionada").setValue(cid.id);
        this.diagnosticoForm.get("cid").setValue(cid.descricaoFormatada);
        this.diagnosticoForm.get("cid").setValidators([Validators.required]);
        this.diagnosticoForm.get("cid").updateValueAndValidity();

        EventEmitterService.get('onChangeCid').emit( cid.id );

        this.indicarSeContempladoEmPortaria(cid);

        this._ultimoCidConsultado = cid.descricaoFormatada;
        this.ultimaCidSelecionada = cid;

        this.clearMensagensErro(this.diagnosticoForm , null, "cid");
        this.setMensagensErro(this.diagnosticoForm, null, "cid");
    }

    /**
     * Indica se a cid informada é contemplada em portaria e muda o style de acordo
     * com a informação.
     *
     * @param cid
     */
    private indicarSeContempladoEmPortaria(cid:Cid):void{
        if(cid.transplante) {
            this.isContempladoPortaria = 'azul';
        } else {
            this.isContempladoPortaria = 'amarelo';
        }
    }


    setMensagemErroPorCampoELimpaContempladoPortaria() {
        this.clearMensagensErro(this.diagnosticoForm , null, 'cid');
        this.setMensagensErro(this.diagnosticoForm, null, 'cid');
        if(!this.diagnosticoForm.get("cid").value){
            this.isContempladoPortaria = '';
        }

    }

    /**
     * Método para restartar componente de autocomplet de cid
     *
     * @param {boolean} e
     * @memberof DiagnosticoComponent
     */
    public changeTypeaheadNoResults(e: boolean): void {
        this.typeaheadNoResults = e;
        if (e && this.diagnosticoForm.get("cid").valid) {
            this.diagnosticoForm.get("cidSelecionada").setValue('');
            this.diagnosticoForm.get("cid").setValidators([Validators.required, localizado(e)]);
            this.diagnosticoForm.get("cid").updateValueAndValidity();
            this.setMensagensErro(this.diagnosticoForm, null, 'cidSelecionada');
            this.isContempladoPortaria = '';
        }
    }

    public form(): FormGroup{
        return this.diagnosticoForm;
    }

    // Override
    public preencherFormulario(diagnostico:Diagnostico): void {
        if(diagnostico){
            this.setPropertyValue("dataDiagnostico",
                DataUtil.toDateFormat(diagnostico.dataDiagnostico, DateTypeFormats.DATE_ONLY));
            if(diagnostico.cid){
                EventEmitterService.get('onChangeCid').emit( diagnostico.cid.id);
                this.ultimaCidSelecionada =
                    new Cid(diagnostico.cid.id, diagnostico.cid.codigo,
                            diagnostico.cid.descricao, diagnostico.cid.transplante, diagnostico.cid.idadeMinima, diagnostico.cid.idadeMaxima);
                this.setPropertyValue("cid", this.ultimaCidSelecionada.descricaoFormatada);
                this.setPropertyValue("cidSelecionada", this.ultimaCidSelecionada.id);
                this.setMensagemErroPorCampoELimpaContempladoPortaria();
                this.indicarSeContempladoEmPortaria(this.ultimaCidSelecionada);
            }
        }
    }

    /**
     * Valida se a CID é ou não contemplada dentro do
     * range de idade.
     */
    public validateForm():boolean{
        if (PacienteUtil.deveExibirMensagemCid(this.idadePaciente, this.ultimaCidSelecionada)) {
            this.messageBox.alert(this._mensagemCid).show();
        }

        return super.validateForm();
    }

    nomeComponente(): string {
        return null;
    }

}
