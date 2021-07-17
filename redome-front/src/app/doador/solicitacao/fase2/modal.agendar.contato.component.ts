import { Component, OnInit } from '@angular/core';
import { Validators } from '@angular/forms';
import { FormGroup } from '@angular/forms/src/model';
import { TranslateService } from '@ngx-translate/core';
import { BuildForm } from 'app/shared/buildform/build.form';
import { NumberControl } from 'app/shared/buildform/controls/number.control';
import { StringControl } from 'app/shared/buildform/controls/string.control';
import { IModalComponent } from 'app/shared/modal/factory/i.modal.component';
import { IModalContent } from 'app/shared/modal/factory/i.modal.content';
import { MessageBox } from 'app/shared/modal/message.box';
import { DateTypeFormats } from 'app/shared/util/date/date.type.formats';
import { UrlParametro } from '../../../shared/url.parametro';
import { DateMoment } from '../../../shared/util/date/date.moment';
import { ErroUtil } from '../../../shared/util/erro.util';
import { TentativaContatoDoadorService } from './tentativa.contato.doador.service';
import { BooleanControl } from 'app/shared/buildform/controls/boolean.control';


/**
 * Modal para registro de atendimento telefonico realizado junto ao doador a cerca
 * de
 */
@Component({
    selector: 'modal-agendar-contato',
    templateUrl: './modal.agendar.contato.component.html'
})
export class ModalAgendarContatoComponent  implements IModalContent, OnInit {
    target: IModalComponent;
    data: any;
    close: (target: IModalComponent) => void;
    
    private agendarContatoForm: BuildForm<any>;
    
    public _dataMask: Array<string | RegExp>;
    public _horaMask: Array<string | RegExp>;

    public opcoesAtribuicao:String[] = ["S","N"];
    public labelAtribuicao:String[] = [];

    constructor(private translate: TranslateService
        , private messageBox: MessageBox
        , private tentativaContatoDoadorService: TentativaContatoDoadorService){

        this._dataMask = [/[0-9]/, /[0-9]/, '\/', /[0-9]/, /[0-9]/, '\/', /[0-9]/, /[0-9]/, /[0-9]/, /[0-9]/];
        this._horaMask = [/[0-2]/, /\d/, ':', /[0-5]/, /\d/];
        
        this.agendarContatoForm = new BuildForm<any>()            
            .add(new NumberControl('telefone', [Validators.required]))
            .add(new StringControl('data', [Validators.required]))
            .add(new StringControl('horaInicio', [Validators.required]))
            .add(new StringControl('atribuirParaMeuUsuario', [Validators.required]))
            .add(new StringControl('horaFim'));
    }

    ngOnInit(): void {
        this.translate.get("modalAgendarContato.opcoesAtribuir.sim").subscribe(res => {
            this.labelAtribuicao[0] = res;
        });
        this.translate.get("modalAgendarContato.opcoesAtribuir.nao").subscribe(res => {
            this.labelAtribuicao[1] = res;
        });
    }

    public fechar(target: any) {
        target.close(target.target);
    }

    public confirmar() {
        if (this.agendarContatoForm.valid) {
            let dateMoment: DateMoment = DateMoment.getInstance();
            let parametros: UrlParametro[] = [];

            parametros.push(new UrlParametro("idContatoTelefone", this.agendarContatoForm.get("telefone").value));
            parametros.push(new UrlParametro("atribuirUsuario", this.agendarContatoForm.get("atribuirParaMeuUsuario").value));
            
            let dataAgendamento: Date = dateMoment.parse(this.agendarContatoForm.get("data").value, DateTypeFormats.DATE_ONLY);

            parametros.push(new UrlParametro("dataAgendamento", dateMoment.formatWithPattern(dataAgendamento, 'DD/MM/YY')));
            parametros.push(new UrlParametro("horaInicio", this.agendarContatoForm.get("horaInicio").value));
            if (this.agendarContatoForm.get("horaFim").value) {
                parametros.push(new UrlParametro("horaFim", this.agendarContatoForm.get("horaFim").value));
            }

            this.tentativaContatoDoadorService.finalizarTentativaContatoCriarProximaTentativa(this.data.idTentativaContato, parametros).then(res => {
                this.target.hide();
                this.messageBox.alert(res.mensagem).withCloseOption(() => {
                    this.data.perguntarProximoDoador();
                }).show();
            }, 
            error => {
                ErroUtil.exibirMensagemErro(error, this.messageBox);
            });            
        }
    }
 
    public form(): FormGroup {
        return this.agendarContatoForm.form;
    }
}