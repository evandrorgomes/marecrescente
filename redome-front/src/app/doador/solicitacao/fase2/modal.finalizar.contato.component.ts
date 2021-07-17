import { Component, OnInit } from '@angular/core';
import { Validators } from '@angular/forms';
import { FormGroup } from '@angular/forms/src/model';
import { TranslateService } from '@ngx-translate/core';
import { SolicitacaoService } from 'app/doador/solicitacao/solicitacao.service';
import { BuildForm } from 'app/shared/buildform/build.form';
import { BooleanControl } from 'app/shared/buildform/controls/boolean.control';
import { NumberControl } from 'app/shared/buildform/controls/number.control';
import { AcaoPedidoContato } from 'app/shared/enums/acao.pedido.contato';
import { IModalComponent } from 'app/shared/modal/factory/i.modal.component';
import { IModalContent } from 'app/shared/modal/factory/i.modal.content';
import { MessageBox } from 'app/shared/modal/message.box';
import { PedidoContatoFinalizadoVo } from 'app/shared/vo/pedido.contato.finalizado.vo';
import { ErroUtil } from '../../../shared/util/erro.util';
import { MotivoStatusDoador } from '../../inativacao/motivo.status.doador';


/**
 * Modal para registro de atendimento telefonico realizado junto ao doador a cerca
 * de
 */
@Component({
    selector: 'modal-finalizar-contato',
    templateUrl: './modal.finalizar.contato.component.html'
})
export class ModalFinalizarContatoComponent  implements IModalContent, OnInit {
    target: IModalComponent;
    data: any;
    close: (target: IModalComponent) => void; 
    

    public _NAO_PROSSEGUIR: AcaoPedidoContato = AcaoPedidoContato.NAO_PROSSEGUIR;

    public _listasSimNao: any[] = [];
    public _listaAcoes: AcaoPedidoContato[] = [];
    public _motivosStatusDoador: MotivoStatusDoador[] = [];
    
    
    private finalizarContatoForm: BuildForm<any>;
    
    public _dataMask: Array<string | RegExp>;
    public _horaMask: Array<string | RegExp>;

    constructor(private translate: TranslateService
        , private messageBox: MessageBox
        , private solicitacaoService: SolicitacaoService){

        this._dataMask = [/[0-9]/, /[0-9]/, '\/', /[0-9]/, /[0-9]/, '\/', /[0-9]/, /[0-9]/, /[0-9]/, /[0-9]/];
        this._horaMask = [/[0-2]/, /\d/, ':', /[0-5]/, /\d/];
        
        this.finalizarContatoForm = new BuildForm<any>()            
            .add(new BooleanControl('contactado', [Validators.required]))
            .add(new BooleanControl('contactadoTerceiro'))
            .add(new NumberControl('acao'))
            .add(new NumberControl('motivo'))
            .add(new NumberControl('tempo'));

        this._listaAcoes.push(AcaoPedidoContato.PROSSEGUIR);
        this._listaAcoes.push(AcaoPedidoContato.NAO_PROSSEGUIR);
        this._listaAcoes.push(AcaoPedidoContato.ANALISE_MEDICA);

    }

    ngOnInit(): void {

        this.translate.get("textosGenericos").subscribe(res => {
            let sim: any = {
                descricao: res["sim"],
                valor: true
            }
            this._listasSimNao.push(sim);
            
            let nao: any = {
                descricao: res["nao"],
                valor: false
            }
            this._listasSimNao.push(nao);
        });

        this.solicitacaoService.listarMotivosStatusDoador(this.data.recurso).then(res => {
            if (res) {
                res.forEach( motivo => {
                    this._motivosStatusDoador.push(new MotivoStatusDoador().jsonToEntity(motivo));
                });
            } 
        }, 
        error => {
            ErroUtil.exibirMensagemErro(error, this.messageBox);
        });

    }

    public fechar(target: any) {
        target.close(target.target);
    }

    public confirmar() {
        if (this.finalizarContatoForm.valid) {
            let pedidoContatoFinalizadoVo: PedidoContatoFinalizadoVo = new PedidoContatoFinalizadoVo();
            pedidoContatoFinalizadoVo.contactado = this.finalizarContatoForm.get("contactado").value;
            if (this.finalizarContatoForm.get("contactadoTerceiro").value != null || this.finalizarContatoForm.get("contactadoTerceiro").value != undefined) {
                pedidoContatoFinalizadoVo.contactadoPorTerceiro = this.finalizarContatoForm.get("contactadoTerceiro").value;
            }
            if (this.finalizarContatoForm.get("acao").value != null && this.finalizarContatoForm.get("acao").value != undefined) {
                pedidoContatoFinalizadoVo.acao = AcaoPedidoContato.valueOf(this.finalizarContatoForm.get("acao").value)
            }
            if (this.finalizarContatoForm.get("motivo").value != null || this.finalizarContatoForm.get("motivo").value != undefined) {
                pedidoContatoFinalizadoVo.idMotivoStatusDoador = this.finalizarContatoForm.get("motivo").value;
            }
            if (this.finalizarContatoForm.get("tempo").value != null || this.finalizarContatoForm.get("tempo").value != undefined) {
                pedidoContatoFinalizadoVo.tempoInativacaoTemporaria = this.finalizarContatoForm.get("tempo").value;
            }

            this.target.hide();
            
            this.data.finalizar(pedidoContatoFinalizadoVo);
        }
    }
 
    public form(): FormGroup {
        return this.finalizarContatoForm.form;
    }

    public deveExibirCampoTempo(): boolean {
        if (this.finalizarContatoForm.get('motivo').value != null && this.finalizarContatoForm.get('motivo').value != undefined ) {
            let index = this._motivosStatusDoador.findIndex(motivo => motivo.id == this.finalizarContatoForm.get('motivo').value);
            if (this._motivosStatusDoador[index].statusDoador.tempoObrigatorio) {
                this.finalizarContatoForm.get('tempo').makeRequired();
                return true;
            }
            else {
                this.finalizarContatoForm.get('tempo').makeOptional();
            }
        }
        return false;
    }

    public tornarObrigatorio(value: string) {

        this.finalizarContatoForm.get('contactadoTerceiro').value = null;
        this.finalizarContatoForm.get('acao').value = null;
        this.finalizarContatoForm.get('motivo').value = null;
        this.finalizarContatoForm.get('tempo').value = null;

        if (value == 'true') {
            this.finalizarContatoForm.get('contactadoTerceiro').makeRequired();
            this.finalizarContatoForm.get('acao').makeRequired();
        }
        else {
            this.finalizarContatoForm.get('contactadoTerceiro').makeOptional();
            this.finalizarContatoForm.get('acao').makeOptional();
            this.finalizarContatoForm.get('motivo').makeOptional();
            this.finalizarContatoForm.get('tempo').makeOptional();
        }
    }

    public tornarMotivoObrigatorio(value: string) {
        if (value == AcaoPedidoContato.NAO_PROSSEGUIR.id+'') {
            this.finalizarContatoForm.get('motivo').makeRequired();
        }
        else {
            this.finalizarContatoForm.get('motivo').makeOptional();
        }
    }



}