import { DateTypeFormats } from './../../shared/util/date/date.type.formats';
import { DateMoment } from './../../shared/util/date/date.moment';
import { InativacaoComponent } from './../inativacao/inativacao.component';
import { IModalContent } from "../../shared/modal/factory/i.modal.content";
import { OnInit, Component, ViewChild } from "@angular/core";
import { IModalComponent } from "../../shared/modal/factory/i.modal.component";
import { DoadorService } from '../doador.service';
import { MessageBox } from '../../shared/modal/message.box';
import { ErroMensagem } from '../../shared/erromensagem';
import { ErroUtil } from '../../shared/util/erro.util';
import { StatusDoador } from '../../shared/dominio/status.doador';

/**
 * Modal para inativação de doador internacional.
 * @author Filipe Paes
 */
@Component({
    selector: 'modal-inativar-doador-internacional',
    templateUrl: './inativar.doador.internacional.modal.component.html'
})
export class ModalInativarDoadorInternacionalComponent implements IModalContent, OnInit {
    
    target: IModalComponent;
    close: (target: IModalComponent) => void;
    data: any;

    @ViewChild("inativacaoDoador")
    public inativacaoDoador:InativacaoComponent;

    constructor(private doadorService:DoadorService,
        private messageBox: MessageBox){

    }
    
    ngOnInit(): void {
       this.inativacaoDoador.doador = this.data.doador;
    }

    inativarDoador(){
        if (this.inativacaoDoador.validateForm()) {
            this.target.hide();          
            this.inativacaoDoador.doador.motivoStatusDoador = this.inativacaoDoador.inativarForm.get('motivoStatusDoador').value;
            this.inativacaoDoador.doador.dataRetornoInatividade = this.inativacaoDoador.inativarForm.get('tempoAfastamento').value ? DateMoment.getInstance().parse(this.inativacaoDoador.inativarForm.get('tempoAfastamento').value, DateTypeFormats.DATE_ONLY): null;
            this.inativacaoDoador.doador.statusDoador.id = this.inativacaoDoador.doador.dataRetornoInatividade? StatusDoador.INATIVO_TEMPORARIO: StatusDoador.INATIVO_PERMANENTE;
            this.doadorService.atualizarStatusDoador(this.inativacaoDoador.doador).then(res => {
                this.messageBox.alert(res.mensagem)
                    .withCloseOption((target: any) => {
                        this.data.fecharModalSucesso();
                    })
                    .show();                
            }, (error: ErroMensagem) => {
                ErroUtil.exibirMensagemErro(error, this.messageBox);                
            });
            
        }
    }
}