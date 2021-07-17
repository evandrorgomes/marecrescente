import { NgModule } from "@angular/core";
import { ModalModule } from 'ngx-bootstrap';
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { ExportTranslateModule } from "../../../shared/export.translate.module";
import { MensagemModule } from "../../../shared/mensagem/mensagem.module";
import { TextMaskModule } from "angular2-text-mask/src/angular2TextMask";
import { MensagemModalModule } from "../../../shared/modal/mensagem.modal.module";
import { CommonModule } from "@angular/common";
import { ConfirmarCentroTransplantePopup } from "./confirmar.centro.transplante.popup";
import { CentroTransplanteFormComponent } from "./form/centro.transplante.form.component";
import { HeaderPacienteModule } from "../../consulta/identificacao/header.paciente.module";
import { CentroTransplanteFormModule } from "./form/centro.transplante.form.module";
import { HistoricoRecusasCentroTransplanteModal } from "./historicoRecusas/historico.recusas.ct.modal";

@NgModule({
  imports: [CommonModule, ModalModule, FormsModule, ExportTranslateModule,
    MensagemModule, ReactiveFormsModule, TextMaskModule, MensagemModalModule, 
    HeaderPacienteModule, CentroTransplanteFormModule],
  declarations: [ConfirmarCentroTransplantePopup, HistoricoRecusasCentroTransplanteModal ],
  providers: [],
  entryComponents: [HistoricoRecusasCentroTransplanteModal],
  exports: [ConfirmarCentroTransplantePopup, HistoricoRecusasCentroTransplanteModal ]
})
export class ConfirmarCentroTransplanteModule {}
