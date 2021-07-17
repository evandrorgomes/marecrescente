import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {ExportTranslateModule} from "../../../export.translate.module";
import {QuestionarioModule} from "../../../questionario/questionario.module";
import {VisualizarResultadoWorkupComponent} from "./visualizar.resultado.workup.component";
import {VisualizarResultadoWorkupDataEventService} from "./visualizar.resultado.workup.data.event.service";
import {ResultadoWorkupService} from "../../../../doador/consulta/workup/resultado/resultadoworkup.service";

@NgModule({
   imports: [CommonModule, ExportTranslateModule, QuestionarioModule],
   declarations: [VisualizarResultadoWorkupComponent],
   entryComponents: [],
   providers: [VisualizarResultadoWorkupDataEventService, ResultadoWorkupService],
   exports: [VisualizarResultadoWorkupComponent]
})
export class VisualizarResultadoWorkupModule { }