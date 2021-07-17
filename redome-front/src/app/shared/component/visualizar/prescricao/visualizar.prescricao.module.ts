import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {ExportTranslateModule} from "../../../export.translate.module";
import {DetalhePrescricaoModule} from "../../detalheprescricao/detalhe.prescricao.module";
import {VisualizarPrescricaoComponent} from "./visualizar.prescricao.component";
import {VisualizarPrescricaoDataEventService} from "./visualizar.prescricao.data.event.service";
import {AccordionModule} from "ngx-bootstrap";

@NgModule({
   imports: [CommonModule, ExportTranslateModule, AccordionModule, DetalhePrescricaoModule],
   declarations: [VisualizarPrescricaoComponent],
   entryComponents: [],
   providers: [VisualizarPrescricaoDataEventService],
   exports: [VisualizarPrescricaoComponent]
})
export class VisualizarPrescricaoModule { }