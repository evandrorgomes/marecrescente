import { Component, OnInit } from '@angular/core';
import { Validators } from '@angular/forms';
import { FormGroup } from '@angular/forms/src/model';
import { TranslateService } from '@ngx-translate/core';
import { BuildForm } from 'app/shared/buildform/build.form';
import { NumberControl } from 'app/shared/buildform/controls/number.control';
import { StringControl } from 'app/shared/buildform/controls/string.control';
import { ContatoTelefonico } from 'app/shared/classes/contato.telefonico';
import { RegistroContato } from 'app/shared/classes/registro.contato';
import { RegistroTipoOcorrencia } from 'app/shared/classes/registro.tipo.ocorencia';
import { IModalComponent } from 'app/shared/modal/factory/i.modal.component';
import { IModalContent } from 'app/shared/modal/factory/i.modal.content';
import { MessageBox } from 'app/shared/modal/message.box';
import { RegistroContatoService } from 'app/shared/service/registro.contato.service';
import { RegistroTipoOcorrenciaService } from 'app/shared/service/registro.tipo.ocorrencia.service';
import { ErroUtil } from '../../../shared/util/erro.util';
import { PedidoContato } from './pedido.contato';


/**
 * Modal para registro de atendimento telefonico realizado junto ao doador a cerca
 * de
 */
@Component({
    selector: 'modal-registro-atendimento',
    templateUrl: './modal.registro.atendimento.component.html'
})
export class ModalRegistroContatoComponent  implements IModalContent, OnInit {
    target: IModalComponent;
    data: any;
    close: (target: IModalComponent) => void;
    private registroContatoForm: BuildForm<any>;
    public labelsTipoOcorrencia:string[] = [];
    public valuesTipoOcorrencia:number[] = [];

    constructor(private translate: TranslateService
        , private messageBox: MessageBox
        , private registroContatoService:RegistroContatoService
        , private tiposOcorrenciaService:RegistroTipoOcorrenciaService){
            this.registroContatoForm = new BuildForm<any>()
            .add(new StringControl('nomeContato', [Validators.required]))
            .add(new StringControl('observacao', [Validators.required]))
            .add(new NumberControl('tipoocorrencia', [Validators.required]));
    }

    ngOnInit(): void {
        this.tiposOcorrenciaService.listar().then(lista => {
            lista.forEach((t: RegistroTipoOcorrencia) => {
                this.labelsTipoOcorrencia.push(t.nome);
                this.valuesTipoOcorrencia.push(t.id);
            });
        });

        this.registroContatoForm.getControlAsStringControl('nomeContato').value = this.data.nomeContato;
    }
    public fechar(target: any) {
        target.close(target.target);
    }

    public confirmar() {
        if (this.registroContatoForm.valid) {
            const registro = new RegistroContato();
            registro.pedidoContato = new PedidoContato();
            registro.pedidoContato.id = this.data.idPedido;
            registro.contatoTelefonico = new ContatoTelefonico();
            registro.contatoTelefonico.nome = this.data.nomeContato;
            registro.contatoTelefonico.id = this.data.idContato;
            registro.registroTipoOcorrencia = new RegistroTipoOcorrencia();
            registro.contatoTelefonico.nome = this.form().get('nomeContato').value;
            registro.registroTipoOcorrencia.id = this.form().get('tipoocorrencia').value;
            registro.observacao = this.form().get('observacao').value;
            this.registroContatoService.salvar(registro).then(res =>{
                this.target.hide();
                if(this.data.excluirDoador){
                    this.data.excluirDoador();
                }else{
                    this.data.atualizarContato(registro.contatoTelefonico.id, registro.contatoTelefonico.nome);
                }
                this.close(this.target);
                this.messageBox.alert(res).show();
            }, error => {
                ErroUtil.exibirMensagemErro(error, this.messageBox);
            });
        }
    }
 
    public form(): FormGroup {
        return this.registroContatoForm.form;
    }
}