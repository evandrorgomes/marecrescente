import { CommonModule } from '@angular/common';
import { NgModule } from "@angular/core";
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ChecklistService } from 'app/shared/service/cheklist.service';
import { ModalModule } from 'ngx-bootstrap';
import { DiretivasModule } from '../../../../shared/diretivas/diretivas.module';
import { ExportTranslateModule } from '../../../../shared/export.translate.module';
import { MensagemModule } from '../../../../shared/mensagem/mensagem.module';
import { MensagemModalModule } from '../../../../shared/modal/mensagem.modal.module';
import { ChecklistComponent } from './checklist.component';

/**
 * Módulo para importação de Checklist
 */
@NgModule({
    imports:[CommonModule, FormsModule, ExportTranslateModule, ModalModule, ReactiveFormsModule,
        MensagemModule, MensagemModalModule, DiretivasModule]
    , declarations: [ChecklistComponent]
    , providers:[ChecklistService]
    ,exports:[ChecklistComponent]
})
export class ChecklistModule{}
