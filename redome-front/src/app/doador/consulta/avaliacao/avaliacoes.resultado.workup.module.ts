import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ModalModule } from 'ngx-bootstrap';
import { PaginationModule } from 'ngx-bootstrap/pagination';
import { SelectCentrosModule } from '../../../shared/component/selectcentros/select.centros.module';
import { ExportTranslateModule } from '../../../shared/export.translate.module';
import { MensagemModule } from '../../../shared/mensagem/mensagem.module';
import { MensagemModalModule } from '../../../shared/modal/mensagem.modal.module';
import { TarefaService } from '../../../shared/tarefa.service';
import { AvaliacoesResultadoWorkupComponent } from './avaliacoes.resultado.workup.component';


@NgModule({
    imports: [CommonModule, FormsModule, ExportTranslateModule, ModalModule, ReactiveFormsModule,
      MensagemModule, MensagemModalModule, PaginationModule, SelectCentrosModule],
    declarations: [AvaliacoesResultadoWorkupComponent],
    providers: [TarefaService],
    exports: [AvaliacoesResultadoWorkupComponent]
  })
  export class AvaliacoesResultadoWorkupModule { }