import { BuscaService } from '../../busca/busca.service';
import { RetornarPaginaAnterior } from '../../../shared/diretivas/retornar.pagina.anterior';
import { AvaliacaoMedicoComponent } from './avaliacao.medico.component';
import { AutenticacaoService } from '../../../shared/autenticacao/autenticacao.service';
import { PendenciaService } from '../pendencia.service';
import { FormsModule } from '@angular/forms';
import { DiretivasModule } from '../../../shared/diretivas/diretivas.module';
import { AvaliacaoService } from '../avaliacao.service';
import { MensagemModule } from '../../../shared/mensagem/mensagem.module';
import { PacienteService } from '../../paciente.service';
import { DominioService } from '../../../shared/dominio/dominio.service';
import { MensagemModalModule } from '../../../shared/modal/mensagem.modal.module';
import { RouterModule } from '@angular/router';
import { HeaderPacienteModule } from '../../consulta/identificacao/header.paciente.module';
import { ModalModule, AccordionModule, PaginationModule } from 'ngx-bootstrap';
import { ExportTranslateModule } from '../../../shared/export.translate.module';
import { ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { NgModule } from "@angular/core";
import { PendenciaDialogoModule } from '../pendenciadialogo/pendencia.dialogo.module';


@NgModule({
    imports: [CommonModule, ReactiveFormsModule, FormsModule, ExportTranslateModule, ModalModule, AccordionModule,
              HeaderPacienteModule, RouterModule, MensagemModalModule, MensagemModule, PaginationModule, DiretivasModule, 
              PendenciaDialogoModule],
    declarations: [AvaliacaoMedicoComponent],
    providers: [DominioService, PacienteService, AvaliacaoService, PendenciaService, BuscaService],
    exports: [AvaliacaoMedicoComponent]
  })
  export class AvaliacaoMedicoModule { }