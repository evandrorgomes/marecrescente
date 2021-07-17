import { PacienteUtil } from '../../shared/paciente.util';
import { Modal } from '../../shared/modal/factory/modal.factory';
import { IModalComponent } from '../../shared/modal/factory/i.modal.component';
import { DataUtil } from '../../shared/util/data.util';
import { EventEmitterService } from '../../shared/event.emitter.service';
import { EventEmitter } from '@angular/core';
import { DateTypeFormats } from '../../shared/util/date/date.type.formats';
import { ErroMensagem } from '../../shared/erromensagem';
import { ModalEvent } from '../../shared/eventos/modal.event';
import { IModalContent } from '../../shared/modal/factory/i.modal.content';
import { ValidateDataMenorQueHoje } from '../../validators/data.validator';
import { TarefaService } from '../../shared/tarefa.service';
import { TranslateService } from '@ngx-translate/core';
import { Router, ActivatedRoute } from '@angular/router';
import { PedidoExameService } from '../pedido.exame.service';
import { FormGroup } from '@angular/forms/src/model';
import { OnInit, Component, Output } from '@angular/core';
import { PedidoExame } from '../pedido.exame';
import { BaseForm } from '../../shared/base.form';
import { Validators, FormBuilder } from '@angular/forms';
import { DateMoment } from '../../shared/util/date/date.moment';
import { MessageBox } from '../../shared/modal/message.box';

@Component({ 
    selector: 'laboratorio-exame-receber-modal', 
    templateUrl: './laboratorio.exame.receber.modal.html'
})

export class LaboratorioExameReceberModal extends BaseForm<PedidoExame> implements OnInit, IModalContent {
    target: IModalComponent;
    close: (target: IModalComponent) => void;
    public MODAL_RECEBIMENTO:string = "MODAL_RECEBIMENTO";
    
    public data:any;

    public dataMask: (string | RegExp)[];
    
    private mensagensErro: any;
    
    public receberPedidoForm:FormGroup;

    constructor(private fb: FormBuilder,
        private pedidoExameService: PedidoExameService,
        private router: Router, private activatedRouter: ActivatedRoute,
        translate: TranslateService,
        private tarefaService:TarefaService,
        private messageBox:MessageBox) {
        super();
        this.translate = translate;
        this.buildForm();
        this.translate.get("laboratorioReceberColeta.recebimento").subscribe(res => {
            this._formLabels = res;
            this.mensagensErro = res;
            this.criarMensagemValidacaoPorFormGroup(this.form());
            this.setMensagensErro(this.form());
        });
    }


    public form(): FormGroup {
        return this.receberPedidoForm;
    }
    public preencherFormulario(entidade: PedidoExame): void {
        throw new Error("Method not implemented.");
    }
    nomeComponente(): string {
        return "LaboratorioExameReceberComponent";
    }
    ngOnInit(): void {
        this.dataMask = [/[0-3]/,/[0-9]/, '\/', /[0-1]/,/[0-9]/, '\/', /[1-2]/,/[0-9]/,/[0-9]/,/[0-9]/];
    }

    buildForm(){
        this.receberPedidoForm =  this.fb.group({
            'dataColeta': [null, Validators.compose([Validators.required,ValidateDataMenorQueHoje])],
            'dataRecebimento': [null, Validators.compose([Validators.required,ValidateDataMenorQueHoje])]
        });
    }

    /**
     * Envia o recebimento para o backend.
     */
    salvarRecebimento(){
        if(this.validarGeral()){ 
            let pedido:PedidoExame = this.data;
            pedido.dataColetaAmostra = DataUtil.toDate(this.receberPedidoForm.get("dataColeta").value, DateTypeFormats.DATE_TIME);
            pedido.dataRecebimentoAmostra =  DataUtil.toDate(this.receberPedidoForm.get("dataRecebimento").value, DateTypeFormats.DATE_TIME);
            this.fecharModal();
            this.pedidoExameService.receberPedidoExame(pedido.id, pedido).then(res=>{
                let alert: Modal = this.messageBox.alert(res.mensagem);
                alert.target = this.target.target;
                alert.closeOption = alert.target.resultadoModal;
                alert.show();
            },
            (error: ErroMensagem) => {
                this.messageBox.alert(error.mensagem + "").show();
            });
        }
    }

    validarGeral():boolean{
        this.clearMensagensErro(this.receberPedidoForm);
        let dataColetaMenorDataExame = true;
        let dataColeta: Date = PacienteUtil.obterDataSemMascara(this.form().get("dataColeta").value);
        let dataExame: Date = PacienteUtil.obterDataSemMascara(this.form().get("dataRecebimento").value);
        
        dataColetaMenorDataExame = this.verificarSeDatasInicioFimSaoValidas(
            dataColeta, dataExame, "dataRecebimento", this._formLabels['dataColetaMenorQueRecebimento']);
        return this.validateForm() && dataColetaMenorDataExame;
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
            
            if(dateMoment.isDateAfter(dataInicio, dataFinal)){
                this.forceError(nomeCampoReferenciaValidacao, mensagemErro);
                return false;
            }
            return true;
        }
        return false;
    }

    fecharModal(){
        this.close(this.target);
    }
}