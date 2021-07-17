import { ExportTranslateModule } from '../../export.translate.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { NgModule } from "@angular/core";
import { ComentarioMatchComponent } from './comentario.match.component';
import { MensagemModule } from '../../mensagem/mensagem.module';


@NgModule({
    imports: [CommonModule, FormsModule, ExportTranslateModule, ReactiveFormsModule, MensagemModule],
    declarations: [ComentarioMatchComponent],
    providers: [],
    exports: [ComentarioMatchComponent]
  })
  export class ComentarioMatchModule { }