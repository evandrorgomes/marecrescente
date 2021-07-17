import { Component, OnInit, ViewChild } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { SolicitacaoService } from '../../../../doador/solicitacao/solicitacao.service';
import { PedidoExame } from '../../../../laboratorio/pedido.exame';
import { PedidoExameService } from '../../../../laboratorio/pedido.exame.service';
import { BaseForm } from '../../../../shared/base.form';
import { CampoMensagem } from '../../../../shared/campo.mensagem';
import { DominioService } from '../../../../shared/dominio/dominio.service';
import { ErroMensagem } from '../../../../shared/erromensagem';
import { LocusComponent } from '../../../../shared/hla/locus/locus.component';
import { IModalComponent } from '../../../../shared/modal/factory/i.modal.component';
import { IModalContent } from '../../../../shared/modal/factory/i.modal.content';
import { Modal } from '../../../../shared/modal/factory/modal.factory';
import { MessageBox } from '../../../../shared/modal/message.box';
import { ErroUtil } from '../../../../shared/util/erro.util';
import { Locus } from '../../../cadastro/exame/locus';

@Component({
    selector: 'pedido-exame-modal',
    templateUrl: './pedido.exame.modal.html'
})

export class PedidoExameModal extends BaseForm<PedidoExame> implements OnInit, IModalContent {
    data: any;
    target: IModalComponent;
    close: (target: IModalComponent) => void;

    public static ACAO_MODAL_PEDIDO_EXAME_CRIAR = "criar";
    public static ACAO_MODAL_PEDIDO_EXAME_EDITAR = "editar";
    private _listLocus: Locus[];

    @ViewChild("locusComponent")
    private locusComponent: LocusComponent;
    private _pedidoExame:PedidoExame;

    constructor(private messageBox: MessageBox, private dominioService: DominioService,
        private pedidoExameService:PedidoExameService, private solicitacaoService: SolicitacaoService) {
        super();
    }

    public form(): FormGroup {
        return null;
    }
    public preencherFormulario(entidade: PedidoExame): void {
        throw new Error("Method not implemented.");
    }
    nomeComponente(): string {
        return "PedidoExameModal";
    }

    ngOnInit(): void {
        this.target.target['match'] = this.data['match'];
        if(this.data.acao == PedidoExameModal.ACAO_MODAL_PEDIDO_EXAME_EDITAR){
            this.pedidoExameService.obterPedidoExamePorId(this.data.idPedido).then(res =>{
                this.pedidoExame =res;
                let locusObrigatorios:string[] = [];
                if(this.pedidoExame.locusSolicitados){
                    this.pedidoExame.locusSolicitados.forEach(l=>{
                        locusObrigatorios.push(l.codigo);
                    })
                }
                this.locusComponent.locusObrigatorios = locusObrigatorios;
                this.locusComponent.required = false;

             },
            (error: ErroMensagem) => {
                ErroUtil.exibirMensagemErro(error,this.messageBox);
            })
        }
    }

    buildForm() {
    }

    salvar() {
        if(this.locusComponent.validateForm()){
                            this.target.hide();

            if(this.data.acao == PedidoExameModal.ACAO_MODAL_PEDIDO_EXAME_EDITAR){
                let pedidoExame: PedidoExame = new PedidoExame();
                let locusSelecionados: Locus[] = this.locusComponent.values;
                pedidoExame.locusSolicitados = locusSelecionados;
                pedidoExame.id = this.pedidoExame.id;


                this.pedidoExameService.atualizarPedidoExameInternacional(this.pedidoExame.id, pedidoExame).then(res=>{
                    let alert: Modal = this.messageBox.alert(res.mensagem);
                    alert.target = this.target.target;
                    alert.closeOption = this.data.atualizaMatch;
                    alert.show();
                },
                (error: ErroMensagem) => {
                    ErroUtil.exibirMensagemErro(error, this.messageBox);
                });
            }else{

                let locusSelecionados: Locus[] = this.locusComponent.values;
                this.solicitacaoService.solicitarFase2Internacional(this.data.idMatch, locusSelecionados)
                    .then((retorno: CampoMensagem) => {
                        let alert: Modal = this.messageBox.alert(retorno.mensagem.toString());
                        alert.target = this.target.target;
                        alert.closeOption = this.data.closeOption;
                        alert.show();
                    }, (error: ErroMensagem) =>{
                        ErroUtil.exibirMensagemErro(error, this.messageBox);
                    });
            }

        }
    }

    /**
     * Getter listLocus
     * @return {Locus[]}
     */
	public get listLocus(): Locus[] {
		return this._listLocus;
	}

    /**
     * Setter listLocus
     * @param {Locus[]} value
     */
	public set listLocus(value: Locus[]) {
		this._listLocus = value;
    }


    /**
     * Getter pedidoExame
     * @return {PedidoExame}
     */
	public get pedidoExame(): PedidoExame {
		return this._pedidoExame;
	}

    /**
     * Setter pedidoExame
     * @param {PedidoExame} value
     */
	public set pedidoExame(value: PedidoExame) {
		this._pedidoExame = value;
    }


}
