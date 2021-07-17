import { CurrencyMaskModule } from 'ng2-currency-mask';
import { DestinoColetaService } from './destino.coleta.service';
import { HeaderPacienteComponent } from '../../../paciente/consulta/identificacao/header.paciente.component';
import { RecebimentoColetaCadastroComponent } from './cadastro/recebimento.coleta.cadastro.component';
import { RecebimentoColetaComponent } from './recebimento.coleta.component';
import { TextMaskModule } from 'angular2-text-mask/src/angular2TextMask';
import { HeaderDoadorModule } from '../header/header.doador.module';
import { DiretivasModule } from '../../../shared/diretivas/diretivas.module';
import { MensagemModule } from '../../../shared/mensagem/mensagem.module';
import { MensagemModalModule } from '../../../shared/modal/mensagem.modal.module';
import { AutenticacaoService } from '../../../shared/autenticacao/autenticacao.service';
import { ModalModule, PaginationModule } from 'ngx-bootstrap';
import { ExportTranslateModule } from '../../../shared/export.translate.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { HeaderPacienteModule } from '../../../paciente/consulta/identificacao/header.paciente.module';
import { SelectCentrosModule } from 'app/shared/component/selectcentros/select.centros.module';


@NgModule({
    imports: [CommonModule, FormsModule, ExportTranslateModule, ModalModule, ReactiveFormsModule,
      MensagemModule, MensagemModalModule, PaginationModule, DiretivasModule, HeaderDoadorModule
      , TextMaskModule, HeaderPacienteModule,TextMaskModule, CurrencyMaskModule, SelectCentrosModule],
    declarations: [RecebimentoColetaComponent, RecebimentoColetaCadastroComponent],
    providers: [DestinoColetaService],
    exports: [RecebimentoColetaComponent, RecebimentoColetaCadastroComponent]
  })
  export class RecebimentoColetaModule { }