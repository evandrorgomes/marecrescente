import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { HlaComponent } from './hla.component';
import { ModalModule } from 'ngx-bootstrap';
import { ExportTranslateModule } from '../export.translate.module';
import { MensagemModule } from '../mensagem/mensagem.module';
import { LocusModule } from './locus/locus.module';
import { HlaViewerComponent } from './hla.viewer.component';

@NgModule({
  imports: [CommonModule, ReactiveFormsModule, ExportTranslateModule, ModalModule, 
            RouterModule, MensagemModule, LocusModule],
  declarations: [HlaComponent, HlaViewerComponent],
  providers: [],
  exports: [HlaComponent, HlaViewerComponent]
})
export class HlaModule { }

  