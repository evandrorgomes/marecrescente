import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TextMaskModule } from 'angular2-text-mask/src/angular2TextMask';
import { ModalModule } from 'ngx-bootstrap';
import { CurrencyMaskModule } from 'ng2-currency-mask';
import { PedidoExameInternacionalComponent } from '../../../paciente/busca/pedidoexame/doadorInternacional/pedido.exame.internacional.component';
import { ExportTranslateModule } from '../../export.translate.module';
import { MensagemModule } from '../../mensagem/mensagem.module';
import { MensagemModalModule } from '../../modal/mensagem.modal.module';
import { HlaModule } from '../hla.module';
import { LocusComponent } from './locus.component';
import { DominioService } from '../../dominio/dominio.service';
import { ExameService } from '../../../paciente/cadastro/exame/exame.service';

@NgModule({
  imports: [CommonModule,FormsModule, ExportTranslateModule, ModalModule, ReactiveFormsModule,
    MensagemModule],
  declarations: [LocusComponent],
  providers: [DominioService, ExameService],
  exports: [LocusComponent]
})
export class LocusModule { }
