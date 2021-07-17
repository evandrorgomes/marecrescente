import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { ReactiveFormsModule, FormsModule } from "@angular/forms";
import { PaginationModule } from "ngx-bootstrap";
import { PacienteService } from "../../paciente/paciente.service";
import { DiretivasModule } from "../../shared/diretivas/diretivas.module";
import { ExportTranslateModule } from "../../shared/export.translate.module";
import { MensagemModule } from "../../shared/mensagem/mensagem.module";
import { MensagemModalModule } from "../../shared/modal/mensagem.modal.module";
import { HeaderPacienteModule } from './../../paciente/consulta/identificacao/header.paciente.module';
import { LogEvolucaoComponent } from "./log.evolucao.component";

@NgModule({
  imports: [CommonModule, MensagemModule, MensagemModalModule, FormsModule,
    DiretivasModule, ReactiveFormsModule, ExportTranslateModule, HeaderPacienteModule, 
    PaginationModule],
  declarations: [LogEvolucaoComponent],
  providers: [PacienteService],
  exports: [LogEvolucaoComponent]
})
export class LogEvolucaoModule { }