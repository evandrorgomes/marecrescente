import { Component, OnInit } from "@angular/core";
import { ConfirmarAgendamentoWorkupComponent } from "../../workup/centrotransplante/confirmar.agendamento.workup.component";
import { ErroMensagem } from "../../../../shared/erromensagem";
import { TiposDoador } from "../../../../shared/enums/tipos.doador";
import { Modal } from "../../../../shared/modal/factory/modal.factory";
import { MessageBox } from "../../../../shared/modal/message.box";
import { PedidoColetaService } from "../pedido.coleta.service";
import { ErroUtil } from "../../../../shared/util/erro.util";
import { ActivatedRoute, Router } from '@angular/router';
import { FormGroup, Validators, FormBuilder } from "@angular/forms";
import { ValidateData, ValidateDataMaiorOuIgualHoje } from "../../../../validators/data.validator";
import { Disponibilidade } from "../../workup/disponibilidade";
import { PacienteUtil } from "../../../../shared/paciente.util";
import { HistoricoNavegacao } from "../../../../shared/historico.navegacao";


/**
 * @description Representa o componente de tela onde o médico transplantador (associado ao CT responsável pela coleta)
 * avalia e aceita ou não as datas de disponibilidade sugeridas pelo analista de workup, quando as datas sugeridas na prescrição
 * não são viáveis.
 * 
 * @author Pizão
 * @export
 * @class ConfirmarAgendamentoColetaComponent
 * @extends {ConfirmarAgendamentoWorkupComponent}
 */
@Component({
    moduleId: module.id,
    selector: "confirmar-agendamento-coleta",
    templateUrl: "../../workup/centrotransplante/confirmar.agendamento.workup.component.html"
})

export class ConfirmarAgendamentoColetaComponent extends ConfirmarAgendamentoWorkupComponent implements OnInit {

    public disponibilidadeCtForm: FormGroup;
    
    constructor(protected messageBox: MessageBox, private coletaService: PedidoColetaService,
        protected activatedRoute: ActivatedRoute, protected fb?: FormBuilder,
        protected router?: Router){
        super(messageBox, activatedRoute, fb, router);
    }

    ngOnInit(){
        this.obterDisponibilidade();
    }

    /**
     * Obtém a disponibilidade a partir do pedido (workup ou coleta).
     */
    public obterDisponibilidade(): void{
        this.coletaService.obterDisponibilidade(this.pedidoColetaId)
            .then(res => {
                this.disponibilidade = new Disponibilidade().jsonToEntity(res);
                // this.isNacional =  
                //     res.pedidoWorkup.solicitacao.match.doador.idTipoDoador == TiposDoador.NACIONAL;

            },
            (error: ErroMensagem)=> {
                ErroUtil.exibirMensagemErro(error, this.messageBox);
            });
    }

    public sugerirNovasDatas(): void {
        if (this.validateForm()) {
            let centroTransplanteQuerColetar: boolean =
                this.disponibilidadeCtForm.get("desejaRealizarColeta").value;

            if (centroTransplanteQuerColetar) {
                this.termoModal.show();
            }
            else {
                this.incluirDisponibilidade();
            }
        }
    }

    protected incluirDisponibilidade(): void {
        this.disponibilidade.centroTransplanteQuerColetar = this.disponibilidadeCtForm.get("desejaRealizarColeta").value;
        this.incluirDisponibilidadeCT();
    }

    protected incluirDisponibilidadeCT(): void{
        this.coletaService.incluirDisponibilidadeCT(this.pedidoColetaId, this.disponibilidade)
            .then(res => {
                this.redirecionarListaPedidosColeta(res.mensagem);
            },
            (error: ErroMensagem)=> {
                ErroUtil.exibirMensagemErro(error, this.messageBox);
            });
    }

    private redirecionarListaPedidosColeta(mensagem: string): void{
        let modal: Modal = this.messageBox.alert(mensagem)
        modal.closeOption = () => {
            this.router.navigateByUrl(HistoricoNavegacao.urlRetorno());
        }
        modal.show();
    }

    protected confirmarCancelamento(): void{
        this.coletaService.cancelarColetaPeloCT(this.pedidoId).then(res => {
            this.redirecionarListaPedidosColeta(res.mensagem);
        },
        (error: ErroMensagem)=> {
            ErroUtil.exibirMensagemErro(error, this.messageBox);
        });
    }

    /**
     * Retorna o nome do componente
     * @returns string
     */
    nomeComponente(): string {
        return "ConfirmarAgendamentoColetaComponent";
    }

    public validateForm(): boolean {
        this.clearMensagensErro(this.form());
        let valid: boolean = this.validateFields(this.form());
        this.setMensagensErro(this.form());
        return valid;
    }

}