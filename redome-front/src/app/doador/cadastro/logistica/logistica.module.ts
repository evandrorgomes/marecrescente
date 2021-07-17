import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TextMaskModule } from 'angular2-text-mask/src/angular2TextMask';
import { CentroTransplanteService } from "app/admin/centrotransplante/centrotransplante.service";
import { HeaderDoadorModule } from "app/doador/consulta/header/header.doador.module";
import { WorkupService } from "app/doador/consulta/workup/workup.service";
import { HeaderPacienteModule } from 'app/paciente/consulta/identificacao/header.paciente.module';
import { InputModule } from 'app/shared/component/inputcomponent/input.module';
import { LogisticaService } from "app/shared/service/logistica.service";
import { TransportadoraService } from "app/shared/service/transportadora.service";
import { BsDropdownModule, ModalModule } from 'ngx-bootstrap';
import { PaginationModule } from 'ngx-bootstrap/pagination';
import { AutenticacaoService } from "../../../shared/autenticacao/autenticacao.service";
import { EmailContatoModule } from '../../../shared/component/email/email.contato.module';
import { EnderecoContatoModule } from '../../../shared/component/endereco/endereco.contato.module';
import { ContatoTelefoneModule } from '../../../shared/component/telefone/contato.telefone.module';
import { DiretivasModule } from '../../../shared/diretivas/diretivas.module';
import { ExportFileUploaderDirectiveModule } from '../../../shared/export.file.uploader.directive.module';
import { ExportTranslateModule } from '../../../shared/export.translate.module';
import { MensagemModule } from '../../../shared/mensagem/mensagem.module';
import { MensagemModalModule } from '../../../shared/modal/mensagem.modal.module';
import { ArquivoVoucherLogisticaService } from '../../../shared/service/arquivo.voucher.logistica.service';
import { LogisticaMaterialService } from '../../../shared/service/logistica.material.service';
import { UploadArquivoModule } from "../../../shared/upload/upload.arquivo.module";
import { DoadorContatoModule } from '../contato/doador.contato.module';
import { AlteracaoLogisticaMaterialNacionalComponent } from './alteracao_agendamento/alteracao.logistica.material.nacional.component';
import { ChecklistModule } from './checklist/checklist.module';
import { ArquivoVoucherLogisticaComponent } from './detalhe/arquivo.voucher.logistica.component';
import { DetalharLogisticaComponent } from "./detalhe/detalhar.logistica.component";
import { TransporteTerrestreComponent } from './detalhe/transporteTerrestre/transporte.terrestre.component';
import { AgendarLogisticaDoadorColetaComponent } from "./doador/agendar.logistica.doador.coleta.component";
import { LogisticaMaterialInternacionalChecklistComponent } from './material/internacional/logistica.material.internacional.checklist.component';
import { ModalLogisticaMaterialInternacionalComponent } from './material/internacional/modal.logistica.material.internacional.component';
import { LogisticaMaterialArquivoComponent } from './material/logistica.material.arquivo.component';
import { LogisticaMaterialNacionalComponent } from './material/nacional/logistica.material.nacional.component';


@NgModule({
    imports: [CommonModule, FormsModule, ExportTranslateModule, ModalModule, ReactiveFormsModule,
      MensagemModule, MensagemModalModule,
      PaginationModule, DiretivasModule, HeaderPacienteModule, HeaderDoadorModule, TextMaskModule,
      ExportFileUploaderDirectiveModule, UploadArquivoModule, ChecklistModule, BsDropdownModule, InputModule,
      EnderecoContatoModule, ContatoTelefoneModule, EmailContatoModule, DoadorContatoModule],
    entryComponents: [ModalLogisticaMaterialInternacionalComponent,AlteracaoLogisticaMaterialNacionalComponent],
    declarations: [DetalharLogisticaComponent, TransporteTerrestreComponent, ArquivoVoucherLogisticaComponent, ModalLogisticaMaterialInternacionalComponent, LogisticaMaterialArquivoComponent, LogisticaMaterialInternacionalChecklistComponent
      , LogisticaMaterialNacionalComponent, ModalLogisticaMaterialInternacionalComponent, AlteracaoLogisticaMaterialNacionalComponent,
      AgendarLogisticaDoadorColetaComponent],
    providers: [AutenticacaoService, WorkupService, LogisticaService,
              ArquivoVoucherLogisticaService, LogisticaMaterialService, TransportadoraService, CentroTransplanteService],
    exports: [DetalharLogisticaComponent, TransporteTerrestreComponent, ModalLogisticaMaterialInternacionalComponent, LogisticaMaterialInternacionalChecklistComponent, LogisticaMaterialNacionalComponent, ModalLogisticaMaterialInternacionalComponent]
  })
  export class LogisticaModule { }
