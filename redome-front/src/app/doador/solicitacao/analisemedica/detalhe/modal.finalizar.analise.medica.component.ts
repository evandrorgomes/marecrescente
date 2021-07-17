import { Component, OnInit } from '@angular/core';
import { Validators } from '@angular/forms';
import { FormGroup } from '@angular/forms/src/model';
import { TranslateService } from '@ngx-translate/core';
import { SolicitacaoService } from 'app/doador/solicitacao/solicitacao.service';
import { BuildForm } from 'app/shared/buildform/build.form';
import { NumberControl } from 'app/shared/buildform/controls/number.control';
import { AcaoPedidoContato } from 'app/shared/enums/acao.pedido.contato';
import { IModalComponent } from 'app/shared/modal/factory/i.modal.component';
import { IModalContent } from 'app/shared/modal/factory/i.modal.content';
import { MessageBox } from 'app/shared/modal/message.box';
import { PedidoContatoFinalizadoVo } from 'app/shared/vo/pedido.contato.finalizado.vo';
import { MotivoStatusDoador } from 'app/doador/inativacao/motivo.status.doador';
import { ErroUtil } from 'app/shared/util/erro.util';
import { AnaliseMedicaFinalizadaVo } from 'app/shared/vo/analise.medica.finalizada.vo';


/**
 * Modal para registro de atendimento telefonico realizado junto ao doador a cerca
 * de
 */
@Component({
    selector: 'modal-finalizar-analise-medica',
    templateUrl: './modal.finalizar.analise.medica.component.html'
})
export class ModalFinalizarAnaliseMedicaComponent  implements IModalContent, OnInit {
    target: IModalComponent;
    data: any;
    close: (target: IModalComponent) => void; 
    

    public _NAO_PROSSEGUIR: AcaoPedidoContato = AcaoPedidoContato.NAO_PROSSEGUIR;

    public _listaAcoes: AcaoPedidoContato[] = [];
    public _motivosStatusDoador: MotivoStatusDoador[] = [];
    
    
    private finalizarAnaliseMedicaForm: BuildForm<any>;
    
    public _dataMask: Array<string | RegExp>;
    public _horaMask: Array<string | RegExp>;

    constructor(private translate: TranslateService
        , private messageBox: MessageBox
        , private solicitacaoService: SolicitacaoService){

        this._dataMask = [/[0-9]/, /[0-9]/, '\/', /[0-9]/, /[0-9]/, '\/', /[0-9]/, /[0-9]/, /[0-9]/, /[0-9]/];
        this._horaMask = [/[0-2]/, /\d/, ':', /[0-5]/, /\d/];
        
        this.finalizarAnaliseMedicaForm = new BuildForm<any>()            
            .add(new NumberControl('acao', [Validators.required]))
            .add(new NumberControl('motivo'))
            .add(new NumberControl('tempo'));

        this._listaAcoes.push(AcaoPedidoContato.PROSSEGUIR);
        this._listaAcoes.push(AcaoPedidoContato.NAO_PROSSEGUIR);

    }

    ngOnInit(): void {

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
        if (this.finalizarAnaliseMedicaForm.valid) {
            let analiseMedicaFinalizadaVo: AnaliseMedicaFinalizadaVo = new AnaliseMedicaFinalizadaVo();            
            if (this.finalizarAnaliseMedicaForm.get("acao").value != null && this.finalizarAnaliseMedicaForm.get("acao").value != undefined) {
                analiseMedicaFinalizadaVo.acao = AcaoPedidoContato.valueOf(this.finalizarAnaliseMedicaForm.get("acao").value)
            }
            if (this.finalizarAnaliseMedicaForm.get("motivo").value != null || this.finalizarAnaliseMedicaForm.get("motivo").value != undefined) {
                analiseMedicaFinalizadaVo.idMotivoStatusDoador = this.finalizarAnaliseMedicaForm.get("motivo").value;
            }
            if (this.finalizarAnaliseMedicaForm.get("tempo").value != null || this.finalizarAnaliseMedicaForm.get("tempo").value != undefined) {
                analiseMedicaFinalizadaVo.tempoInativacaoTemporaria = this.finalizarAnaliseMedicaForm.get("tempo").value;
            }

            this.target.hide();
            
            this.data.finalizar(analiseMedicaFinalizadaVo);
        }
    }
 
    public form(): FormGroup {
        return <FormGroup> this.finalizarAnaliseMedicaForm.form;
    }

    public deveExibirCampoTempo(): boolean {
        if (this.finalizarAnaliseMedicaForm.get('motivo').value != null && this.finalizarAnaliseMedicaForm.get('motivo').value != undefined ) {
            let index = this._motivosStatusDoador.findIndex(motivo => motivo.id == this.finalizarAnaliseMedicaForm.get('motivo').value);
            if (this._motivosStatusDoador[index].statusDoador.tempoObrigatorio) {
                this.finalizarAnaliseMedicaForm.get('tempo').makeRequired();
                return true;
            }
            else {
                this.finalizarAnaliseMedicaForm.get('tempo').makeOptional();
            }
        }
        return false;
    }

    public tornarMotivoObrigatorio(value: string) {
        if (value == AcaoPedidoContato.NAO_PROSSEGUIR.id+'') {
            this.finalizarAnaliseMedicaForm.get('motivo').makeRequired();
        }
        else {
            this.finalizarAnaliseMedicaForm.get('motivo').makeOptional();
        }
    }



}