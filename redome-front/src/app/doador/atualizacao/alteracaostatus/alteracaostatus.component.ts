import { TranslateService } from '@ngx-translate/core';
import { DoadorService } from '../../doador.service';
import { BaseForm } from '../../../shared/base.form';
import { Component, OnInit, ViewChild, Input } from "@angular/core";
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MotivoStatusDoador } from '../../inativacao/motivo.status.doador';
import { MensagemModalComponente } from '../../../shared/modal/mensagem.modal.component';
import { SolicitacaoService } from '../../solicitacao/solicitacao.service';
import { PacienteUtil } from '../../../shared/paciente.util';
import { ComponenteRecurso } from '../../../shared/enums/componente.recurso';
import { StatusDoador } from '../../../shared/dominio/status.doador';
import { ErroMensagem } from '../../../shared/erromensagem';
import { EventEmitterService } from '../../../shared/event.emitter.service';
import { DadosStatus } from './dados.status';
import { DataUtil } from '../../../shared/util/data.util';
import { DateMoment } from '../../../shared/util/date/date.moment';
import { StatusSolicitacao } from '../../../shared/enums/status.solicitacao';
import { PedidoContato } from '../../solicitacao/fase2/pedido.contato';
import { StatusPedidosWorkup } from '../../../shared/enums/status.pedidos.workup';
import { Solicitacao } from '../../solicitacao/solicitacao';
import { Doador } from '../../doador';


/**
 * Classe Component para inativação do doador.
 * 
 * @author Pizão.
 */
@Component({
    selector: 'alteracaostatus-modal',
    templateUrl: './alteracaostatus.component.html'
})
export class AlteracaoStatusComponent extends BaseForm<Doador> implements OnInit {

    public static FINALIZAR_EVENTO_ALTERACAO_STATUS: string = 'finalizadoAlteracaoStatus';

    public alteracaoStatusForm: FormGroup;

    public motivosStatusDoador: MotivoStatusDoador[];

    exibirTempoAfastamento: boolean = false;

    private _doador: Doador;
    private _solicitacao: Solicitacao;
    public exibeMotivoInativacao: boolean = false;

    @Input()
    public recurso: string;

    @ViewChild('modal')
    private modal;

    @ViewChild('modalMsg')
    private modalMsg: MensagemModalComponente;

    public dadosStatus: DadosStatus;


    private textosInternacionalizados: any;


    public data: Array<string | RegExp>


    constructor(private fb: FormBuilder, private doadorService: DoadorService,
        private solicitacaoService: SolicitacaoService, protected translate: TranslateService) {

        super();

        this.data = [/[0-9]/, /[0-9]/, '\/', /[0-9]/, /[0-9]/, '\/', /[0-9]/, /[0-9]/, /[0-9]/, /[0-9]/]

        this.translate.get('inativarDoador').subscribe(res => {
            this.textosInternacionalizados = res;
        });

        this.alteracaoStatusForm = this.fb.group({
            'status': [null, Validators.required],
            'motivoStatusDoador': [null, null],
            'tempoAfastamento': null
        });

        this.criarMensagemValidacaoPorFormGroup(this.alteracaoStatusForm);
    }

    ngOnInit(): void {
        // FIXME: Este serviço deveria mesmo receber o ID do Recurso?
        this.solicitacaoService.listarMotivosStatusDoador(this.recurso)
            .then(res => {
                this.motivosStatusDoador = res;
            });

        if (this.dadosStatus) {
            console.log(this.alteracaoStatusForm.get("status").value);
        }
    }

    cancelar() {
        this.modal.hide();
    }

    public abrirModal() {
        this.alteracaoStatusForm.get("status").setValue(null)
        this.alteracaoStatusForm.get("motivoStatusDoador").setValue(null)
        this.alteracaoStatusForm.get("tempoAfastamento").setValue(null)
        this.exibirTempoAfastamento = false;
        this.exibeMotivoInativacao = false;
        this.resetFieldRequiredSemForm("motivoStatusDoador")
        this.resetFieldRequiredSemForm('tempoAfastamento');
        if (this.doador.statusDoador.id == StatusDoador.INATIVO_TEMPORARIO
        || this.doador.statusDoador.id == StatusDoador.INATIVO_PERMANENTE) {
            this.alteracaoStatusForm.get("status").setValue(3)
            this.alteracaoStatusForm.get("motivoStatusDoador").setValue(this.doador.motivoStatusDoador.id);
            this.exibeMotivoInativacao = true;
            
            this.setFieldRequiredSemForm("motivoStatusDoador")
            if(this.doador.statusDoador.tempoObrigatorio){
                this.exibirTempoAfastamento = true;
            }
            if (this.doador.dataRetornoInatividade) {
                let dateFormat: DateMoment = DateMoment.getInstance();
                let dadataRetornoInatividadeta:any = this.doador.dataRetornoInatividade;
                
                let dataRetornoInatividade: string = dateFormat.format(dateFormat.parse(dadataRetornoInatividadeta));
                this.alteracaoStatusForm.get("tempoAfastamento").setValue(dataRetornoInatividade);
                this.setFieldRequiredSemForm('tempoAfastamento');
            }
        } else {
            this.alteracaoStatusForm.get("status").setValue(1)
            this.resetFieldRequiredSemForm("motivoStatusDoador")
            this.exibeMotivoInativacao = false;
            this.exibirTempoAfastamento = false;
            this.resetFieldRequiredSemForm('tempoAfastamento');
        }

        this.modal.show();
    }

    confirmar(): void {
        if (this.validateForm()) {
            const motivoStatusId: number = this.alteracaoStatusForm.get("motivoStatusDoador").value;
            const motivoStatus: MotivoStatusDoador = this.encontrarMotivoStatus(motivoStatusId);
            this.doador.motivoStatusDoador = motivoStatus;
            this.doador.statusDoador.id = this.alteracaoStatusForm.get("status").value;

            this.doador.dataRetornoInatividade =
                PacienteUtil.obterDataSemMascara(this.alteracaoStatusForm.get("tempoAfastamento").value) || null;
            this.modal.hide();
            this.doadorService.atualizarStatusDoador(this.doador).then(res => { 
                this.cancelar();
                //this.doador = res.object.doador;
                //this.solicitacao = res.object.solicitacao;

                /* this.translate.get('solicitacao').subscribe(res => {
                    this.preencherDescricaoStatus(res);
                }); */
                this.modalMsg.mensagem = res.mensagem;
                this.modalMsg.abrirModal();

                EventEmitterService.get(AlteracaoStatusComponent.FINALIZAR_EVENTO_ALTERACAO_STATUS).emit(res.object);
            },
                (error: ErroMensagem) => {
                    this.exibirMensagemErro(error);
                });
        }
    }
    /**
     * Altera a descrição do status do pedido de contato
     * @param  {any} res
     */
    /* public preencherDescricaoStatus(res: any) {
        if (this.solicitacao) {
            
        }

        if (this.solicitacao.pedidoWorkup && this.solicitacao.pedidoWorkup.statusPedidoWorkup) {
            let statusPedidoWorkup = this.solicitacao.pedidoWorkup.statusPedidoWorkup.id == StatusPedidosWorkup.CANCELADO || 
                this.solicitacao.pedidoWorkup.statusPedidoWorkup.id == StatusPedidosWorkup.REALIZADO 
            let valorStatus:string= statusPedidoWorkup ? res['aberta'] : res['fechada']
            this.solicitacao.pedidoContato.dataCriacao = this.solicitacao.pedidoWorkup.dataCriacao
            this.solicitacao.pedidoContato.descricaoStatus = valorStatus            
        } else {

            if (this.solicitacao.pedidoContato.aberto) {
                this.solicitacao.pedidoContato.descricaoStatus = res['aberta']
            } else {
                this.solicitacao.pedidoContato.descricaoStatus = res['fechada']
            }
        }
    } */

    private exibirMensagemErro(error: ErroMensagem) {
        error.listaCampoMensagem.forEach(obj => {
            this.modalMsg.mensagem = obj.mensagem;
        })
        this.modalMsg.abrirModal();
    }

    public form(): FormGroup {
        return this.alteracaoStatusForm;
    }

    public preencherFormulario(entidade: Doador): void {
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

        if (motivoId) {
            const motivoStatus: MotivoStatusDoador = this.encontrarMotivoStatus(motivoId);

            this.exibirTempoAfastamento = motivoStatus.statusDoador.tempoObrigatorio;
            this.exibirTempoAfastamento ?
                this.setFieldRequired(this.form(), 'tempoAfastamento') :
                this.resetFieldRequired(this.form(), 'tempoAfastamento');
            if(!this.exibirTempoAfastamento){
                this.form().get("tempoAfastamento").setValue(null)
            }
        }
    }

    private encontrarMotivoStatus(motivoId: number): MotivoStatusDoador {
        const motivoStatus: MotivoStatusDoador =
            this.motivosStatusDoador.find(motivoStatus => {
                return motivoStatus.id == motivoId;
            });
        return motivoStatus;
    }

	/**
	 * @returns Doador
	 */
    public get doador(): Doador {
        return this._doador;
    }
	/**
	 * @param  {Doador} value
	 */
    public set doador(value: Doador) {
        this._doador = value;
    }

    public exibirMotivosInativacaoSeStatusAtivo(status: number) {
        console.log(status)
        if (StatusDoador.ATIVO == status) {
            this.resetFieldRequiredSemForm("motivoStatusDoador")
            this.exibeMotivoInativacao = false;
            this.exibirTempoAfastamento = false;
            this.form().get("tempoAfastamento").setValue(null)
            this.form().get("motivoStatusDoador").setValue(null)
            this.resetFieldRequired(this.form(), 'tempoAfastamento');
        } else {
            this.setFieldRequiredSemForm("motivoStatusDoador")
            this.exibeMotivoInativacao = true;
        }

    }

    /**
     * Getter solicitacao
     * @return {Solicitacao}
     */
	public get solicitacao(): Solicitacao {
		return this._solicitacao;
	}

    /**
     * Setter solicitacao
     * @param {Solicitacao} value
     */
	public set solicitacao(value: Solicitacao) {
		this._solicitacao = value;
	}
    
}
