import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TextMaskModule } from 'angular2-text-mask/src/angular2TextMask';
import { HeaderDoadorModule } from 'app/doador/consulta/header/header.doador.module';
import { DestinoColetaService } from 'app/doador/consulta/recebimentocoleta/destino.coleta.service';
import { HeaderPacienteModule } from 'app/paciente/consulta/identificacao/header.paciente.module';
import { SelectCentrosModule } from 'app/shared/component/selectcentros/select.centros.module';
import { DiretivasModule } from 'app/shared/diretivas/diretivas.module';
import { ExportTranslateModule } from 'app/shared/export.translate.module';
import { MensagemModule } from 'app/shared/mensagem/mensagem.module';
import { MensagemModalModule } from 'app/shared/modal/mensagem.modal.module';
import { CurrencyMaskModule } from 'ng2-currency-mask';
import { ModalModule, PaginationModule } from 'ngx-bootstrap';
import { RecebimentoColetaCadastroInternacionalComponent } from './recebimento.coleta.cadastro.internacional.component';
import { InputModule } from 'app/shared/component/inputcomponent/input.module';
import { RecebimentoColetaService } from 'app/shared/service/recebimento.coleta.service';


@NgModule({
    imports: [CommonModule, FormsModule, ExportTranslateModule, ModalModule, ReactiveFormsModule,
      MensagemModule, MensagemModalModule, PaginationModule, DiretivasModule
      , TextMaskModule, HeaderPacienteModule,TextMaskModule, CurrencyMaskModule, SelectCentrosModule, InputModule],
    declarations: [RecebimentoColetaCadastroInternacionalComponent],
    providers: [RecebimentoColetaService],
    exports: [RecebimentoColetaCadastroInternacionalComponent]
  })
  export class RecebimentoColetaModule { }
