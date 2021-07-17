import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { RouterModule } from '@angular/router';
import { RelatorioComponent } from './relatorio.component';
import { RelatorioService } from './relatorio.service';
import { ExportTranslateModule } from '../../shared/export.translate.module';
import { TransporteMaterialModule } from '../../transportadora/tarefas/transporte.material.module';
import { CKEditorModule } from 'ng2-ckeditor';
import { DiretivasModule } from '../../shared/diretivas/diretivas.module';
import { MensagemModule } from '../../shared/mensagem/mensagem.module';
import { ModalAlterarConstanteRelatorioComponent } from 'app/admin/relatorio/modal/modal.alterar.constante.relatorio.component';

@NgModule({
  imports: [CommonModule, RouterModule, FormsModule, ExportTranslateModule, TransporteMaterialModule, 
     FormsModule, CKEditorModule, ReactiveFormsModule, MensagemModule, DiretivasModule],
  declarations: [RelatorioComponent, ModalAlterarConstanteRelatorioComponent],
  entryComponents: [ModalAlterarConstanteRelatorioComponent],
  providers: [RelatorioService],
  exports: [RelatorioComponent]
})
export class RelatorioModule { }