import { Component, OnInit } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { TranslateService } from "@ngx-translate/core";
import { BaseForm } from '../../../shared/base.form';
import { IModalComponent } from "../../../shared/modal/factory/i.modal.component";
import { IModalContent } from "../../../shared/modal/factory/i.modal.content";
import { MessageBox } from "../../../shared/modal/message.box";
import { DominioService } from "../../../shared/dominio/dominio.service";
import { CentroTransplante } from "../../../shared/dominio/centro.transplante";
import { ErroMensagem } from "../../../shared/erromensagem";
import { ErroUtil } from "../../../shared/util/erro.util";
import { TiposDoador } from "../../../shared/enums/tipos.doador";
import { PacienteUtil } from "../../../shared/paciente.util";
import { DateMoment } from "../../../shared/util/date/date.moment";
import { AgendamentoWorkupDTO } from "../../consulta/workup/agendamento.workup.dto";
import { WorkupService } from "../../consulta/workup/workup.service";
import { FonteCelula } from "../../../shared/dominio/fonte.celula";
import { DataUtil } from "../../../shared/util/data.util";
import { DateTypeFormats } from "../../../shared/util/date/date.type.formats";

/**
 * Componente de modal para visualização do historico de disponibilidades do centro de coleta.
 * @author Bruno Sousa
 */
@Component({
    selector: 'modal-agendar-pedido-workup',
    templateUrl: './modal.agendar.pedido.workup.component.html'
})
export class ModalAgendarPedidoWorkupComponent extends BaseForm<Object> implements IModalContent, OnInit {
    
    target: IModalComponent;
    close: (target: IModalComponent) => void;
    data: any;

    private _centrosColetores: CentroTransplante[] = [];
    public regexpData: Array<string | RegExp>
    private _form: FormGroup;

    constructor(translate: TranslateService, private fb: FormBuilder, 
            private dominioService: DominioService, private messageBox: MessageBox,
            private workupService: WorkupService) {
             super();
        this.translate = translate;
        this.regexpData = [/[0-9]/, /[0-9]/, '\/', /[0-9]/, /[0-9]/, '\/', /[0-9]/, /[0-9]/, /[0-9]/, /[0-9]/];        
    }

    public ngOnInit() {
        this.buildForm();

        this.dominioService.listarCentroColeta().then(res => {
            this._centrosColetores = res;
        }, (error: ErroMensagem) => {
            ErroUtil.exibirMensagemErro(error, this.messageBox);
        });

        this.translate.get("workup.pedido").subscribe(res => {
            this._formLabels = res;
            this.criarMensagemValidacaoPorFormGroup(this._form);
            this.setMensagensErro(this._form);
        });
    }

    public preencherFormulario(entidade: Object): void {
        throw new Error("Method not implemented.");
    }

    public form(): FormGroup {
        return this._form;
    }

    /**
     * * Método que constrói o formulário
     * @returns void
     */
    buildForm(): void {
        if(!this.data || this.isDoadorNacional()){
            this._form = this.fb.group({
                'centroColeta': [null, Validators.required],
                'dataInicioWorkup': [null, Validators.required],
                'dataFinalWorkup': [null, null],
                'dataPrevistaDisponibilidadeDoador': [null, Validators.required],
                'dataPrevistaLiberacaoDoador': [null, Validators.required],
                'necessitaLogistica': null,
                'fonteCelula': [null, null],
                'dataLimiteResult': [null, Validators.required],
                'dataFinalColeta': [null, Validators.required]
            });
        }
        else if(this.isDoadorInternacional()){
            this._form = this.fb.group({
                'dataFinalColeta': [null, Validators.required],
                'dataFinalWorkup': [null, Validators.required],
                'dataLimiteResult': [null, Validators.required],
                'fonteCelula': [null, null]
            });
        }
        else {
            throw new Error("Doador, neste momento, só poderia ser de medula (nacional ou internacional)");
        }
        if (this.listarFonteCelulasPrescricao().length == 1) {
            this._form.get("fonteCelula").setValue(this.listarFonteCelulasPrescricao()[0].id);
            this._form.get("fonteCelula").disable({onlySelf: true, emitEvent: true});
        }
        else {
            this._form.get("fonteCelula").enable();
            this._form.get("fonteCelula").setValue(null);
        }


    }

      /**
     * Indica se o doador associado ao pedido de workup é nacional
     * (somente medula, por se tratar de workup).
     * @return TRUE se for nacional.
     */
    public isDoadorNacional(): boolean{
        return this.data && this.data.pedidoWorkup && this.data.pedidoWorkup.solicitacao.doador.tipoDoador == TiposDoador.NACIONAL;
    }

    /**
     * Indica se o doador associado ao pedido de workup é medula internacional
     * @return TRUE se for internacional.
     */
    public isDoadorInternacional(): boolean{
        
        return this.data && this.data.pedidoWorkup && this.data.pedidoWorkup.solicitacao.doador.tipoDoador == TiposDoador.INTERNACIONAL;
    }


    public validateForm():boolean{
        let valido: boolean = super.validateForm();
        
        let dataFinalWorkup: Date = 
            PacienteUtil.obterDataSemMascara(this.form().get("dataFinalWorkup").value);

        let dataLimiteResult: Date =
            PacienteUtil.obterDataSemMascara(this.form().get("dataLimiteResult").value);

        let dataFinalColeta: Date =
            PacienteUtil.obterDataSemMascara(this.form().get("dataFinalColeta").value);

        let datasLimiteWorkupValidas: boolean = true;
        if(dataFinalWorkup){
            datasLimiteWorkupValidas = 
            this.verificarSeDatasInicioFimSaoValidas(
                dataFinalWorkup, dataLimiteResult, "dataFinalWorkup", this._formLabels['dataFinalMenorDataLimiteWorkup']);
        }

        let datasLimiteColetaValidas: boolean = true;
        if(dataFinalColeta){
            datasLimiteColetaValidas =
            this.verificarSeDatasInicioFimSaoValidas(
                dataLimiteResult, dataFinalColeta, "dataLimiteResult", this._formLabels['dataLimiteWorkupMaiorDataColeta']);
        }

        if (this.isDoadorNacional()) {

            let dataInicioWorkup: Date = 
                PacienteUtil.obterDataSemMascara(this.form().get("dataInicioWorkup").value);

            let dataPrevistaDisponibilidadeDoador: Date = 
                PacienteUtil.obterDataSemMascara(this.form().get("dataPrevistaDisponibilidadeDoador").value);

            let dataPrevistaLiberacaoDoador: Date = 
                PacienteUtil.obterDataSemMascara(this.form().get("dataPrevistaLiberacaoDoador").value);

            let datasColetaValidas: boolean = true;

            if (dataPrevistaDisponibilidadeDoador && dataPrevistaLiberacaoDoador) {
                datasColetaValidas =
                    this.verificarSeDatasInicioFimSaoValidas(
                        dataPrevistaDisponibilidadeDoador, dataPrevistaLiberacaoDoador, "dataPrevistaLiberacaoDoador", this._formLabels['dataPrevistaDisponibilidadeDoadorMaiorDataPrevistaLiberacaoDoador']);
            }

            let datasPrevistaDisponibilidadeDoadorValida: boolean = true;
            if (dataPrevistaDisponibilidadeDoador && dataFinalColeta) {
                datasPrevistaDisponibilidadeDoadorValida =
                    this.verificarSeDatasInicioFimSaoValidas(
                        dataPrevistaDisponibilidadeDoador, dataFinalColeta, "dataPrevistaDisponibilidadeDoador", this._formLabels['dataPrevistaDisponibilidadeDoadorMaiorDataRealizacaoColeta']);
            }

            let datasWorkupValidas: boolean = true;
            if(dataFinalWorkup){
                datasWorkupValidas = 
                this.verificarSeDatasInicioFimSaoValidas(
                    dataInicioWorkup, dataFinalWorkup, "dataInicioWorkup", this._formLabels['dataInicioMaiorDataFinalWorkup']);
            }
            
            return valido && datasColetaValidas && datasPrevistaDisponibilidadeDoadorValida && 
                datasWorkupValidas && datasLimiteWorkupValidas && datasLimiteColetaValidas;
        }
        else {
            return valido && datasLimiteWorkupValidas;
        }
    }

    /**
     * Valida se o campos data inicio e data fim estão preenchidos corretamente, ou seja,
     * data início maior que final.
     * Com preenchimento inválido, marca o campo com erro e a mensagem informados.
     */
    private verificarSeDatasInicioFimSaoValidas(dataInicio: Date, dataFinal: Date, 
        nomeCampoReferenciaValidacao: string, mensagemErro: string): boolean{
        if(dataInicio && dataFinal){
            let dateMoment: DateMoment = DateMoment.getInstance();

            if(dateMoment.isDateBefore(dataFinal, dataInicio)){
                this.forceError(nomeCampoReferenciaValidacao, mensagemErro);
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * Método para agendar um pedido de workup
     * @returns void
     */
    public agendarPedidoWorkup(): void {
        this.form().markAsTouched();
        if (this.validateForm()) {
            let agendamentoWorkupDTO: AgendamentoWorkupDTO = new AgendamentoWorkupDTO();
            if(this.isDoadorNacional()){
                agendamentoWorkupDTO.dataPrevistaDisponibilidadeDoador = 
                    PacienteUtil.obterDataSemMascara(this.form().get("dataPrevistaDisponibilidadeDoador").value);
                agendamentoWorkupDTO.dataPrevistaLiberacaoDoador =
                    PacienteUtil.obterDataSemMascara(this.form().get("dataPrevistaLiberacaoDoador").value);    

                agendamentoWorkupDTO.dataInicioResultado = 
                    PacienteUtil.obterDataSemMascara(this.form().get("dataInicioWorkup").value);

                agendamentoWorkupDTO.necessitaLogistica = false;
                agendamentoWorkupDTO.idCentroColeta = this.form().get("centroColeta").value;
                agendamentoWorkupDTO.necessitaLogistica = this.form().get("necessitaLogistica").value;
            }
            agendamentoWorkupDTO.idFonteCelula = this.form().get("fonteCelula").value;
            agendamentoWorkupDTO.dataColeta = 
                PacienteUtil.obterDataSemMascara(this.form().get("dataFinalColeta").value);
            agendamentoWorkupDTO.dataLimiteWorkup = 
                PacienteUtil.obterDataSemMascara(this.form().get("dataLimiteResult").value);
            agendamentoWorkupDTO.dataFinalResultado = 
                PacienteUtil.obterDataSemMascara(this.form().get("dataFinalWorkup").value);

            this.target.hide();
            
            this.workupService.agendar(this.data.pedidoWorkup.id, agendamentoWorkupDTO).then(res => {
                this.messageBox.alert(res.mensagem)
                    .withTarget(this)
                    .withCloseOption((target: any) => {
                        this.data.fechar();
                    })
                    .show();                
            },
            (error: ErroMensagem) => {
                ErroUtil.exibirMensagemErro(error, this.messageBox);                
            });
        }
    }

    private listarFonteCelulasPrescricao(): FonteCelula[] {
        let lista: FonteCelula[] = [];
        if (this.data.pedidoWorkup && this.data.pedidoWorkup.solicitacao.prescricao.fonteCelulaOpcao1 &&
                this.data.pedidoWorkup.solicitacao.prescricao.fonteCelulaOpcao2) {
            
            if (this.data.pedidoWorkup.solicitacao.prescricao.avaliacaoPrescricao && 
                    this.data.pedidoWorkup.solicitacao.prescricao.avaliacaoPrescricao.fonteCelula) {

                if (this.data.pedidoWorkup.solicitacao.prescricao.fonteCelulaOpcao1.id == this.data.pedidoWorkup.solicitacao.prescricao.avaliacaoPrescricao.fonteCelula.id) {
                    lista.push(this.data.pedidoWorkup.solicitacao.prescricao.fonteCelulaOpcao2);
                }
                else {
                    lista.push(this.data.pedidoWorkup.solicitacao.prescricao.fonteCelulaOpcao1);
                }
            }
            else {
                lista.push(this.data.pedidoWorkup.solicitacao.prescricao.fonteCelulaOpcao1);
                lista.push(this.data.pedidoWorkup.solicitacao.prescricao.fonteCelulaOpcao2);
            }
        }
        else if (this.data.pedidoWorkup) {
            lista.push(this.data.pedidoWorkup.solicitacao.prescricao.fonteCelulaOpcao1);
        }

        return lista;
    }

    public toDateString(date: Date): string{
        if(!date){
            return null;
        }
        return DataUtil.toDateFormat(date, DateTypeFormats.DATE_ONLY);
    }

    public get fontesCelulas(): FonteCelula[] {
        return this.listarFonteCelulasPrescricao();
    }

    public fechar() {
        this.target.hide();
    }

    public isFonteCelulaDisabled(): string {
        return this.form().get('fonteCelula').disabled ? "disabled" : undefined;
    }

    public get centrosColetores(): CentroTransplante[] {
        return this._centrosColetores;
    }

    sugerirDataFim(){
        if (this.form().get("dataInicioWorkup").valid && this.form().get("dataInicioWorkup").value != '' && this.form().get("dataInicioWorkup").value != null ) {
            if (this.form().get("dataFinalWorkup").value == '' || this.form().get("dataFinalWorkup").value == null) {
                this.form().get("dataFinalWorkup").setValue(this.form().get("dataInicioWorkup").value);
            }
        }
    }

    nomeComponente(): string {
        throw new Error("Method not implemented.");
    }
    

}