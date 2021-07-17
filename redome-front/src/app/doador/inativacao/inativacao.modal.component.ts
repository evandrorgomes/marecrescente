import { Component, EventEmitter, Input, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { DoadorNacional } from '../doador.nacional';
import { DoadorService } from '../doador.service';
import { MotivoStatusDoador } from './motivo.status.doador';
import { ComponenteRecurso } from '../../shared/enums/componente.recurso';
import { ErroMensagem } from '../../shared/erromensagem';
import { EventEmitterService } from '../../shared/event.emitter.service';
import { MensagemModalComponente } from '../../shared/modal/mensagem.modal.component';
import { PacienteUtil } from '../../shared/paciente.util';
import { MessageBox } from '../../shared/modal/message.box';
import { ErroUtil } from '../../shared/util/erro.util';
import { InativacaoComponent } from './inativacao.component';

/**
 * Classe Component para inativação do doador.
 * 
 * @author Pizão.
 */
@Component({
    selector: 'inativacao-modal',
    templateUrl: './inativacao.modal.component.html'
})
export class InativacaoModalComponent implements OnInit, OnDestroy {

    public static FINALIZAR_EVENTO: string = 'finalizadoListener';


    private _motivosStatusDoador: MotivoStatusDoador[];

    exibirTempoAfastamento: boolean = false;

    @Input()
    public doador: DoadorNacional;

    @Input()
    public recurso: string;

    @ViewChild('modal')
    private modal;

    @ViewChild('modalMsg')
    private modalSucesso: MensagemModalComponente;

    @ViewChild('modalError')
    private modalError: MensagemModalComponente;

    @ViewChild('inativarDoador')
    private inativarDoador: InativacaoComponent;

    private textosInternacionalizados: any;

    private finalizarEvento: EventEmitter<any>;

    public data: Array<string | RegExp>


    constructor(private fb: FormBuilder, private doadorService: DoadorService,
        protected translate: TranslateService,
        private messageBox: MessageBox) {

    }

    ngOnInit(): void {
        this.inativarDoador.doador = this.doador;
        this.inativarDoador.recurso = this.recurso;
    }

    cancelar() {
        this.modal.hide();
    }

    public abrirModal() {
        this.modal.show();
    }

    confirmar(): void {
        if (this.inativarDoador.validateForm()) {
            const motivoStatusId: number = this.inativarDoador.inativarForm.get("motivoStatusDoador").value;
            const motivoStatus: MotivoStatusDoador = this.encontrarMotivoStatus(motivoStatusId);
            this.doador.motivoStatusDoador = motivoStatus;

            this.doador.dataRetornoInatividade =
            PacienteUtil.obterDataSemMascara(this.inativarDoador.inativarForm.get("tempoAfastamento").value) || null;
            this.modal.hide();
            this.doadorService.inativar(this.doador).then(res => {
                this.cancelar();
                Object.assign(this.doador, res);

                this.translate.get("inativarDoador").subscribe(res => {
                    this.modalSucesso.mensagem = res['mensagemDoadorInativadoSucesso']
                    this.modalSucesso.abrirModal();
                    EventEmitterService.get(InativacaoModalComponent.FINALIZAR_EVENTO).emit(this.doador);
                });
               
            },
            (error: ErroMensagem) => {
                ErroUtil.exibirMensagemErro(error, this.messageBox)
            });
        }
    }


    public preencherFormulario(entidade: DoadorNacional): void {
        throw new Error("Method not implemented.");
    }

    nomeComponente(): string {
        return ComponenteRecurso.Componente[
            ComponenteRecurso.Componente.InativacaoModalComponent];
    }

    private exibirMensagemErro(error: ErroMensagem) {
        error.listaCampoMensagem.forEach(obj => {
            this.modalError.mensagem = obj.mensagem;
        })
        this.modalError.abrirModal();
    }


    public ngOnDestroy(): void {
        if (this.finalizarEvento != null) {
            this.finalizarEvento.unsubscribe();
        }
    }

    private encontrarMotivoStatus(motivoId: number): MotivoStatusDoador {
        const motivoStatus: MotivoStatusDoador =
            this.inativarDoador.motivosStatusDoador.find(motivoStatus => {
                return motivoStatus.id == motivoId;
            });
        return motivoStatus;
    }

    public get motivosStatusDoador(): MotivoStatusDoador[] {
        return this._motivosStatusDoador;
    }

}
