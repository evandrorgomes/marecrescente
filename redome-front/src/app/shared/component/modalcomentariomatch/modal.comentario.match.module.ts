import { ComentarioMatchService } from '../../service/comentrio.match.service';
import { MensagemModalModule } from '../../modal/mensagem.modal.module';
import { MensagemModule } from '../../mensagem/mensagem.module';
import { ModalModule } from 'ngx-bootstrap';
import { ExportTranslateModule } from '../../export.translate.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { NgModule } from "@angular/core";
import { TextMaskModule } from 'angular2-text-mask/src/angular2TextMask';
import { ModalComentarioMatchComponent } from './modal.comentario.match.component';
import { MatchService } from '../../../doador/solicitacao/match.service';
import { ComentarioMatchModule } from '../comentariomatch/comentario.match.module';
import { MockMatchService } from '../../mock/mock.match.service';
import { MockComentarioMatchService } from '../../mock/mock.comentario.match.service';

@NgModule({
    imports: [CommonModule, FormsModule, ExportTranslateModule, ModalModule, ReactiveFormsModule, ComentarioMatchModule,
          MensagemModalModule],
    declarations: [ModalComentarioMatchComponent],
    providers: [MatchService, ComentarioMatchService],
    exports: [ModalComentarioMatchComponent]
  })
  export class ModalComentarioMatchModule { }