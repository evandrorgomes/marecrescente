import { RessalvaComponent } from './ressalva.component';
import { DoadorService } from '../../../doador.service';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ExportTranslateModule } from '../../../../shared/export.translate.module';
import { ModalModule } from 'ngx-bootstrap/modal/modal.module';
import { MensagemModalModule } from '../../../../shared/modal/mensagem.modal.module';
import { MensagemModule } from '../../../../shared/mensagem/mensagem.module';
import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { RessalvaService } from './ressalva.service';


@NgModule({
    imports: [CommonModule, FormsModule, ExportTranslateModule, ModalModule, ReactiveFormsModule,
      MensagemModule, MensagemModalModule],
    declarations: [RessalvaComponent],
    providers: [RessalvaService],
    exports: [RessalvaComponent]
  })
  export class RessalvaModule { }