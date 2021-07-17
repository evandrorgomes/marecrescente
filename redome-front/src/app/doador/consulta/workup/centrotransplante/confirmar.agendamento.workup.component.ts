import { Component, OnInit, ViewChild } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { BaseForm } from '../../../../shared/base.form';
import { DominioService } from '../../../../shared/dominio/dominio.service';
import { TiposDoador } from '../../../../shared/enums/tipos.doador';
import { ErroMensagem } from '../../../../shared/erromensagem';
import { PacienteUtil } from '../../../../shared/paciente.util';
import { DateMoment } from '../../../../shared/util/date/date.moment';
import { ValidateData, ValidateDataMaiorOuIgualHoje } from '../../../../validators/data.validator';
import { Disponibilidade } from '../disponibilidade';
import { WorkupService } from "../workup.service";
import { MessageBox } from "../../../../shared/modal/message.box";
import { ModalConfirmation, Modal } from "../../../../shared/modal/factory/modal.factory";
import { HeaderPacienteComponent } from "../../../../paciente/consulta/identificacao/header.paciente.component";
import { RouterUtil } from "../../../../shared/util/router.util";
import { PedidoColetaService } from "../../coleta/pedido.coleta.service";
import { ErroUtil } from "../../../../shared/util/erro.util";

@Component({
    moduleId: module.id,
    selector: "confirmar-agendamento-workup",
    templateUrl: "./confirmar.agendamento.workup.component.html"
})
export class ConfirmarAgendamentoWorkupComponent extends BaseForm<Disponibilidade> implements OnInit {

    @ViewChild(HeaderPacienteComponent)
    private headerPaciente: HeaderPacienteComponent;

    @ViewChild('termo')
    public termoModal;

    private properties: any;

    // ID do pedido e RMR associados a tarefa selecionada na lista de pendências.
    public pedidoId: number;
    public pedidoColetaId: number;
    public rmr: number;

    public disponibilidade: Disponibilidade;

    public disponibilidadeCtForm: FormGroup;

    public data: Array<string | RegExp>;

    // Termo de responsabilidade exibido, quando CT solicita ser o
    // responsável pela coleta.
    public termoDisponibilidade: string;
    private _isNacional: boolean;
    private _isMedula: boolean;

    constructor(protected messageBox: MessageBox,
        protected activatedRouter?: ActivatedRoute,
        protected fb?: FormBuilder,
        protected router?: Router,
        private workupService?: WorkupService, translate?: TranslateService,
        private pedidoColetaService?:PedidoColetaService,
         private dominioService?: DominioService) {

        super();

        this.translate = translate;

        this.data = [/[0-9]/, /[0-9]/, '\/', /[0-9]/, /[0-9]/, '\/', /[0-9]/, /[0-9]/, /[0-9]/, /[0-9]/]

        RouterUtil.recuperarParametrosDaActivatedRoute(this.activatedRouter, ["rmr", "pedidoId", "pedidoColetaId"]).then(res => {
            this.rmr = res['rmr'];
            this.pedidoId = res['pedidoId'];
            this.pedidoColetaId = res['pedidoColetaId'];
            if (this.pedidoColetaId) {
                this._isMedula = false;
                this.pedidoId = null;
            } else {

                this._isMedula = true;
            }

            if (this._isMedula) {
                this.disponibilidadeCtForm = this.fb.group({
                    'desejaRealizarColeta': null
                });
            } else {
                this.disponibilidadeCtForm = this.fb.group({
                    'desejaRealizarColeta': null
                });
            }

        })

    }


    ngOnInit(): void {
        this.disponibilidade = new Disponibilidade();

        if (this._isMedula) {
            this.obterDisponibilidade();
        } else {
            this.obterDisponibilidadeCordao();
        }

        this.translate.get('workup.pendencia').subscribe(res => {
            this._formLabels = res;
            this.properties = res.mensagens;
            this.criarMensagemValidacaoPorFormGroup(this.disponibilidadeCtForm);
            this.setMensagensErro(this.disponibilidadeCtForm);
        });

        Promise.resolve(this.headerPaciente).then(() => {
            this.headerPaciente.popularCabecalhoIdentificacaoPorPaciente(this.rmr);
        });
    }
    

    /**
     * Obtém a disponibilidade a partir do pedido (workup ou coleta).
     */
    public obterDisponibilidade(): void {
        this.workupService.obterDisponibilidade(this.pedidoId)
            .then(res => {
                Object.assign(this.disponibilidade, res);
                this._isNacional = <number>res.pedidoWorkup.solicitacao.match.doador.idTipoDoador == TiposDoador.NACIONAL;

            },
                (error: ErroMensagem) => {
                    ErroUtil.exibirMensagemErro(error, this.messageBox);
                });
    }
    /**
     * Obtém a disponibilidade a partir do pedido (workup ou coleta).
     */
    public obterDisponibilidadeCordao(): void {
        this.pedidoColetaService.obterDisponibilidade(this.pedidoColetaId)
            .then(res => {
                // Object.assign(this.disponibilidade, res);
                this.disponibilidade = new Disponibilidade().jsonToEntity(res);
            },
                (error: ErroMensagem) => {
                    ErroUtil.exibirMensagemErro(error, this.messageBox);
                });
    }

    /**
     * Retorna o nome do componente
     * @returns string
     */
    nomeComponente(): string {
        return "ConfirmarAgendamentoWorkupComponent";
    }

    public form(): FormGroup {
        return this.disponibilidadeCtForm;
    }

    public preencherFormulario(entidade: Disponibilidade): void { }


    public cancelarPrescricao(): void {
        if(this._isMedula && this._isNacional){
            let modal: ModalConfirmation = this.messageBox.confirmation(this.properties.confirmar_cancelamento_prescricao);
            modal.yesOption = () => {
                this.confirmarCancelamento();
            }
            modal.show();
        }
    }

    /**
     * Cancela pedido de workup ou coleta, ambos considerados parte da prescrição.
     */
    protected confirmarCancelamento(): void {
        this.workupService.cancelarWorkupPeloCT(this.pedidoId).then(res => {
            this.redirecionarListaPedidosWorkup(res.mensagem);
        },
            (error: ErroMensagem) => {
                ErroUtil.exibirMensagemErro(error, this.messageBox);
            });
    }

    private redirecionarListaPedidosWorkup(mensagem: string): void {
        let modal: Modal = this.messageBox.alert(mensagem)
        modal.closeOption = () => {
            this.router.navigateByUrl('/doadores/workup/pendencias');
        }
        modal.show();
    }

    public sugerirNovasDatas(): void {
        let centroTransplanteQuerColetar: boolean =
            this.disponibilidadeCtForm.get("desejaRealizarColeta").value;

        if (centroTransplanteQuerColetar) {
            this.termoModal.show();
        }
        else {
            this.incluirDisponibilidade();
        }
    }

    protected incluirDisponibilidade(): void {
        this.disponibilidade.centroTransplanteQuerColetar = this.disponibilidadeCtForm.get("desejaRealizarColeta").value;
        this.incluirDisponibilidadeCT();
    }

    /**
     * Salva a nova disponibilidade informada pelo CT.
     * Com isso, o pedido de workup ou coleta é devolvido ao analista de workup
     * para ser avaliado se as datas atendem as necessidades do centro de coleta e do doador.
     * 
     * @return o retorno da chamada ao back-end.
     */
    protected incluirDisponibilidadeCT(): void {
        this.workupService.incluirDisponibilidadeCT(this.pedidoId, this.disponibilidade)
            .then(res => {
                this.redirecionarListaPedidosWorkup(res.mensagem);
            },
                (error: ErroMensagem) => {
                    ErroUtil.exibirMensagemErro(error, this.messageBox);
                });
    }

    public concordarTermo(): void {
        this.incluirDisponibilidade();
        this.fecharTermo();
    }

    public fecharTermo(): void {
        this.termoModal.hide();
    }

    /**
     * Valida se o campos data inicio e data fim estão preenchidos corretamente, ou seja,
     * data início maior que final.
     * Com preenchimento inválido, marca o campo com erro e a mensagem informados.
     */
    private verificarSeDatasInicioFimSaoValidas(dataInicio: Date, dataFinal: Date,
        nomeCampoReferenciaValidacao: string, mensagemErro: string): boolean {
        if (dataInicio && dataFinal) {
            let dateMoment: DateMoment = DateMoment.getInstance();

            if (dateMoment.isDateAfter(dataInicio, dataFinal)) {
                this.forceError(nomeCampoReferenciaValidacao, mensagemErro);
                return false;
            }
        }
        return true;
    }

    verificaDesejoRealizarColeta(value: boolean) {
        this.form().get("desejaRealizarColeta").setValue(value);
    }

    /**
     * Getter isNacional
     * @return {boolean}
     */
    public get isNacional(): boolean {
        return this._isNacional;
    }

    /**
     * Setter isNacional
     * @param {boolean} value
     */
    public set isNacional(value: boolean) {
        this._isNacional = value;
    }


    /**
     * Getter isMedula
     * @return {boolean}
     */
	public get isMedula(): boolean {
		return this._isMedula;
	}

    /**
     * Setter isMedula
     * @param {boolean} value
     */
	public set isMedula(value: boolean) {
		this._isMedula = value;
	}


}