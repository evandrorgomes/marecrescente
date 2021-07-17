import { MensagemModule } from '../../../shared/mensagem/mensagem.module';
import { AssociaPendenciaComponent } from './associa.pendencia.component';
import { ExportTranslateModule } from '../../../shared/export.translate.module';
import { ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { NgModule } from "@angular/core";

@NgModule({
    imports: [CommonModule, ReactiveFormsModule, ExportTranslateModule, MensagemModule],
    declarations: [AssociaPendenciaComponent],
    providers: [],
    exports: [AssociaPendenciaComponent]
  })
  export class AssociaPendenciaModule { }