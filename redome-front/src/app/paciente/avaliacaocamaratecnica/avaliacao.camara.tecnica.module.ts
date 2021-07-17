import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ExportTranslateModule } from '../../shared/export.translate.module';
import { ModalModule, AccordionModule, PaginationModule } from 'ngx-bootstrap';
import { HeaderPacienteModule } from '../consulta/identificacao/header.paciente.module';
import { RouterModule } from '@angular/router';
import { MensagemModalModule } from '../../shared/modal/mensagem.modal.module';
import { MensagemModule } from '../../shared/mensagem/mensagem.module';
import { DiretivasModule } from '../../shared/diretivas/diretivas.module';
import { PendenciaDialogoModule } from '../avaliacao/pendenciadialogo/pendencia.dialogo.module';
import { DialogoModule } from '../../shared/dialogo/dialogo.module';
import { AvaliacaoCamaraTecnicaComponent } from './avaliacao.camara.tecnica.component';
import { NgModule } from '@angular/core';
import { AvalicacaoCamaraTecnicaService } from './avaliacao.camara.tecnica.service';
import { DetalheAvaliacaoCamaraTecnicaComponent } from './cadastro/detalhe.avaliacao.camara.tecnica.component';
import { ModalDetalheAvaliacaoCamaraTecnicaComponent } from './cadastro/modal/modal.detalhe.avaliacao.camara.tecnica.component';
import { UploadArquivoModule } from '../../shared/upload/upload.arquivo.module';

@NgModule({
    imports: [CommonModule, ReactiveFormsModule, FormsModule, ExportTranslateModule, ModalModule, AccordionModule,
              HeaderPacienteModule, RouterModule, MensagemModalModule, MensagemModule, PaginationModule, DiretivasModule, 
              PendenciaDialogoModule, DialogoModule, UploadArquivoModule],
    declarations: [AvaliacaoCamaraTecnicaComponent, DetalheAvaliacaoCamaraTecnicaComponent, ModalDetalheAvaliacaoCamaraTecnicaComponent],
    entryComponents: [ModalDetalheAvaliacaoCamaraTecnicaComponent],
    providers: [AvalicacaoCamaraTecnicaService, ModalDetalheAvaliacaoCamaraTecnicaComponent],
    exports: [AvaliacaoCamaraTecnicaComponent,DetalheAvaliacaoCamaraTecnicaComponent, ModalDetalheAvaliacaoCamaraTecnicaComponent]
  })
  export class AvaliacaoCamaraTecnicaModule { 
      
  }