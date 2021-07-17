import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { HeaderPacienteModule } from "app/paciente/consulta/identificacao/header.paciente.module";
import { ExportTranslateModule } from "../../export.translate.module";
import { EnderecoContatoModule } from "../endereco/endereco.contato.module";
import { DetalhePrescricaoCordaoComponent } from "./detalhe.prescricao.cordao.component";
import { DetalhePrescricaoDataEventService } from "./detalhe.prescricao.data.event.service";
import { DetalhePrecricaoMedulaComponent } from "./detalhe.prescricao.medula.component";
import { ReactiveFormsModule, FormsModule } from "@angular/forms";


@NgModule({
   imports: [ReactiveFormsModule, FormsModule, HeaderPacienteModule, CommonModule, ExportTranslateModule, EnderecoContatoModule],
   declarations: [DetalhePrecricaoMedulaComponent, DetalhePrescricaoCordaoComponent],
   providers: [DetalhePrescricaoDataEventService],
   exports: [DetalhePrecricaoMedulaComponent, DetalhePrescricaoCordaoComponent]
})
export class DetalhePrescricaoModule { }