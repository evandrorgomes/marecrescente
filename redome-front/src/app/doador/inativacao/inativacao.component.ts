import { DoadorInternacional } from './../doador.internacional';
import {TranslateService} from '@ngx-translate/core';
import { BaseForm } from '../../shared/base.form';
import { SolicitacaoService } from '../solicitacao/solicitacao.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Observable } from 'rxjs';
import { Component, EventEmitter, Input, OnInit, Output, ViewChild, OnDestroy } from '@angular/core';
import { DoadorService } from '../doador.service';
import { MotivoStatusDoador } from './motivo.status.doador';
import { DoadorNacional } from '../doador.nacional';
import { ComponenteRecurso } from '../../shared/enums/componente.recurso';
import { MensagemModalComponente } from '../../shared/modal/mensagem.modal.component';
import { EventEmitterService } from '../../shared/event.emitter.service';
import { PacienteUtil } from '../../shared/paciente.util';
import { ErroMensagem } from '../../shared/erromensagem';
import { InativacaoModalComponent } from './inativacao.modal.component';

/**
 * Classe Component para inativação do doador.
 * 
 * @author Pizão.
 */
@Component({
    selector: 'inativacao-doador',
    templateUrl: './inativacao.component.html'
})
export class InativacaoComponent extends BaseForm<DoadorNacional> implements OnInit, OnDestroy{
    
    public static FINALIZAR_EVENTO: string = 'finalizadoListener';

    public inativarForm: FormGroup;
    
    private _motivosStatusDoador: MotivoStatusDoador[];

    exibirTempoAfastamento: boolean = false;

    @Input()
    public doador: DoadorNacional;

    @Input()
    public recurso: string;

    @ViewChild('modal')
    private modal;


    // Evento disparado quando finaliza a atualização do doador.
    private inativadoEventListener: EventEmitter<DoadorNacional>;

    private textosInternacionalizados: any;

    private finalizarEvento: EventEmitter<any>;
    
    public data: Array<string | RegExp>


    constructor(private fb: FormBuilder, private doadorService: DoadorService, 
        private solicitacaoService: SolicitacaoService, protected translate: TranslateService){
        
        super();

        this.data = [/[0-9]/,/[0-9]/, '\/', /[0-9]/,/[0-9]/, '\/', /[0-9]/,/[0-9]/,/[0-9]/,/[0-9]/]

        this.translate.get('inativarDoador').subscribe(res => {
            this.textosInternacionalizados = res;
        });

        this.inativarForm = this.fb.group({
            'motivoStatusDoador' : [null, Validators.required],
            'tempoAfastamento': null
        });

        this.criarMensagemValidacaoPorFormGroup(this.inativarForm);
    }

    ngOnInit(): void {
        // FIXME: Este serviço deveria mesmo receber o ID do Recurso?
        this.solicitacaoService.listarMotivosStatusDoador(this.recurso)
                .then(res => this._motivosStatusDoador = res);
    }


    public form(): FormGroup {
        return this.inativarForm;
    }
    
    public preencherFormulario(entidade: DoadorNacional): void {
        throw new Error("Method not implemented.");
    }

    nomeComponente(): string {
        return ComponenteRecurso.Componente[
            ComponenteRecurso.Componente.InativacaoModalComponent];
    }


        /**
     * Metodo que ao selecionar um motivo verifica se deve colocar 
     * o campo observacao como obrigatorio.
     * @param motivo 
     */
    public alterarValidacaoDoCampoDescricao(motivoId: number): void {
        this.exibirTempoAfastamento = false;

        if(motivoId){
            const motivoStatus: MotivoStatusDoador = this.encontrarMotivoStatus(motivoId);

            this.exibirTempoAfastamento = motivoStatus.statusDoador.tempoObrigatorio;
            this.exibirTempoAfastamento ?
                this.setFieldRequired(this.form(), 'tempoAfastamento') :
                    this.resetFieldRequired(this.form(), 'tempoAfastamento');
        }
    }

    public ngOnDestroy(): void {
        if(this.finalizarEvento != null){
            this.finalizarEvento.unsubscribe();
        }
    }

    private encontrarMotivoStatus(motivoId: number): MotivoStatusDoador{
        const motivoStatus: MotivoStatusDoador =
                this._motivosStatusDoador.find(motivoStatus => {
            return motivoStatus.id == motivoId;
        });
        return motivoStatus;
    }

	public get motivosStatusDoador(): MotivoStatusDoador[] {
		return this._motivosStatusDoador;
	}
    
    public get motivoCancelamento(): MotivoStatusDoador {
        const motivoStatusId: number = this.inativarForm.get("motivoStatusDoador").value;
        const motivoStatus: MotivoStatusDoador = this.encontrarMotivoStatus(motivoStatusId);
        return motivoStatus;
    }

    public get tempoAfastamento(): Date {
        return PacienteUtil.obterDataSemMascara(
            this.inativarForm.get("tempoAfastamento").value) || null;
    }

    obrigatorio(campo: string, value: boolean) {
        if(value){
            this.setFieldRequired(this.form(), campo);
        }
        else {
            this.resetFieldRequired(this.form(), campo);
        }
    }

}