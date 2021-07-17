import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TextMaskModule } from 'angular2-text-mask/src/angular2TextMask';
import { ModalModule } from 'ngx-bootstrap';
import { PaginationModule } from 'ngx-bootstrap/pagination';
import { DiretivasModule } from '../../../shared/diretivas/diretivas.module';
import { ExportTranslateModule } from '../../../shared/export.translate.module';
import { MensagemModule } from '../../../shared/mensagem/mensagem.module';
import { MensagemModalModule } from '../../../shared/modal/mensagem.modal.module';
import { ContatoPassivoAtualizarModule } from '../../atualizacao/contatopassivo/contatopassivo.atualizar.module';
import { DoadorService } from '../../doador.service';
import { ConsultaContatoDoadorComponent } from './consulta.contato.doador.component';


@NgModule({
    imports: [CommonModule, FormsModule, ExportTranslateModule, ModalModule, ReactiveFormsModule,
      MensagemModule, MensagemModalModule, PaginationModule, DiretivasModule, ContatoPassivoAtualizarModule, TextMaskModule,
    DiretivasModule],
    declarations: [ConsultaContatoDoadorComponent],
    providers: [DoadorService],
    exports: [ConsultaContatoDoadorComponent]
  })
  export class ConsultaContatoDoadorModule { }