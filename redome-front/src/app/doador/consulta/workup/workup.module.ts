import {CommonModule} from "@angular/common";
import {NgModule} from "@angular/core";
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {TextMaskModule} from 'angular2-text-mask/src/angular2TextMask';
import {ConfirmarAgendamentoWorkupComponent} from './centrotransplante/confirmar.agendamento.workup.component';
import {DoadorPendenciaWorkupComponent} from './centrotransplante/doador.pendencia.workup.component';
import {ConsultaWorkupComponent} from './consulta.workup.component';
import {WorkupService} from './workup.service';
import {HeaderPacienteModule} from '../../../paciente/consulta/identificacao/header.paciente.module';
import {AutenticacaoService} from "../../../shared/autenticacao/autenticacao.service";
import {EmailContatoModule} from '../../../shared/component/email/email.contato.module';
import {ModalModule} from 'ngx-bootstrap';
import {PaginationModule} from 'ngx-bootstrap/pagination';
import {DiretivasModule} from '../../../shared/diretivas/diretivas.module';
import {EnderecoContatoModule} from '../../../shared/component/endereco/endereco.contato.module';
import {ContatoTelefoneModule} from '../../../shared/component/telefone/contato.telefone.module';
import {ExportTranslateModule} from '../../../shared/export.translate.module';
import {MensagemModule} from '../../../shared/mensagem/mensagem.module';
import {MensagemModalModule} from '../../../shared/modal/mensagem.modal.module';
import {PedidoColetaService} from "../coleta/pedido.coleta.service";
import {SelectCentrosModule} from "../../../shared/component/selectcentros/select.centros.module";
import { ConsultaWorkupCentroColetaComponent } from "./centrocoleta/consulta.workup.centro.coleta.component";


@NgModule({
    imports: [CommonModule, FormsModule, ExportTranslateModule, ModalModule, ReactiveFormsModule,
      MensagemModule, MensagemModalModule, EnderecoContatoModule, ContatoTelefoneModule, EmailContatoModule,
      PaginationModule, DiretivasModule, HeaderPacienteModule, TextMaskModule, SelectCentrosModule],
    declarations: [ConsultaWorkupComponent, DoadorPendenciaWorkupComponent,
                  ConfirmarAgendamentoWorkupComponent, ConsultaWorkupCentroColetaComponent],
    providers: [AutenticacaoService, WorkupService, PedidoColetaService],
    exports: [ConsultaWorkupComponent, DoadorPendenciaWorkupComponent,
              ConfirmarAgendamentoWorkupComponent, ConsultaWorkupCentroColetaComponent]
  })
  export class WorkupModule { }