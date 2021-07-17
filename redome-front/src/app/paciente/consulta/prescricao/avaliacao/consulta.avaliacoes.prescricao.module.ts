import {TarefaService} from '../../../../shared/tarefa.service';
import {RouterModule} from '@angular/router';
import {PaginationModule} from 'ngx-bootstrap/pagination';
import {BusyModule} from 'angular2-busy/build';
import {DiretivasModule} from '../../../../shared/diretivas/diretivas.module';
import {AutenticacaoModule} from '../../../../shared/autenticacao/autenticacao.module';
import {MensagemModalModule} from '../../../../shared/modal/mensagem.modal.module';
import {MensagemModule} from '../../../../shared/mensagem/mensagem.module';
import {ModalModule} from 'ngx-bootstrap/modal';
import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { FormsModule } from "@angular/forms";
import { ExportTranslateModule } from "../../../../shared/export.translate.module";
import { ConsultaAvaliacoesPrescricaoComponent } from './consulta.avaliacoes.prescricao.component';
import { AutenticacaoService } from '../../../../shared/autenticacao/autenticacao.service';


@NgModule({
  imports: [CommonModule,FormsModule, ExportTranslateModule, ModalModule, MensagemModule, 
    MensagemModalModule, BusyModule, PaginationModule, RouterModule,
  AutenticacaoModule, DiretivasModule, PaginationModule],
  declarations: [ConsultaAvaliacoesPrescricaoComponent],
  providers: [TarefaService, AutenticacaoService],
  exports: [ConsultaAvaliacoesPrescricaoComponent]
})
export class ConsultaAvaliacoesPrescricaoModule { }
