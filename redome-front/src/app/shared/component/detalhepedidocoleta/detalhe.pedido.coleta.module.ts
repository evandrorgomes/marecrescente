import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { ExportTranslateModule } from "../../export.translate.module";
import { DetalhePedidoColetaComponent } from "./detalhe.pedido.coleta.component";

@NgModule({
    imports: [CommonModule, ExportTranslateModule],
    declarations: [DetalhePedidoColetaComponent],
    providers: [],
    exports: [DetalhePedidoColetaComponent]
  })
  export class DetalhePedidoColetaModule { }