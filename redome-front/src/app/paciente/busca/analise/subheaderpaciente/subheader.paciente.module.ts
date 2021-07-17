import { SubHeaderPacienteComponent } from "./subheader.paciente.component";
import { BrowserModule } from "@angular/platform-browser";
import { CommonModule } from "@angular/common";
import { ExportTranslateModule } from "../../../../shared/export.translate.module";
import { NgModule } from "@angular/core";

@NgModule({
    imports: [BrowserModule, CommonModule, ExportTranslateModule], 
    declarations: [SubHeaderPacienteComponent],
    providers: [],
    exports: [SubHeaderPacienteComponent]
  })
  export class SubHeaderPacienteModule {}