import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { QuestionarioModule } from "app/shared/questionario/questionario.module";
import { AccordionModule, ModalModule } from "ngx-bootstrap";
import { HeaderPacienteModule } from "../../../../paciente/consulta/identificacao/header.paciente.module";
import { DetalhePrescricaoModule } from "../../../../shared/component/detalheprescricao/detalhe.prescricao.module";
import { InputModule } from "../../../../shared/component/inputcomponent/input.module";
import { DialogoModule } from "../../../../shared/dialogo/dialogo.module";
import { ExportTranslateModule } from "../../../../shared/export.translate.module";
import { MensagemModule } from "../../../../shared/mensagem/mensagem.module";
import { MensagemModalModule } from "../../../../shared/modal/mensagem.modal.module";
import { TarefaService } from "../../../../shared/tarefa.service";
import { ResultadoWorkupService } from "../../../consulta/workup/resultado/resultadoworkup.service";
import { AvaliacaoPedidoColetaService } from "../../../../shared/service/avalaiacao.pedido.coleta.service";
import { AvaliacaoResultadoWorkupService } from "./avaliacao.resultado.workup.service";
import { AvaliarResultadoWorkupInternacionalComponent } from "./avaliar.resultado.workup.internacional.component";
import { AvaliarResultadoWorkupNacionalComponent } from "./avaliar.resultado.workup.nacional.component";
import { ModalDescricaoExameAdicionalComponent } from "./modal/modal.descricao.exame.adicional.component";
import { ModalJustificativaComponent } from "../../../../shared/component/modaljustificativa/modal.justificativa.component";
import {VisualizarPrescricaoModule} from "../../../../shared/component/visualizar/prescricao/visualizar.prescricao.module";
import {VisualizarResultadoWorkupModule} from "../../../../shared/component/visualizar/resultadoworkup/visualizar.resultado.workup.module";
import {ModalJustificativaModule} from "../../../../shared/component/modaljustificativa/modal.justificativa.module";



@NgModule({
    imports: [CommonModule, FormsModule, ExportTranslateModule, ModalModule, ReactiveFormsModule, AccordionModule,
      MensagemModule, MensagemModalModule, HeaderPacienteModule, DialogoModule, InputModule,
      VisualizarPrescricaoModule, VisualizarResultadoWorkupModule, ModalJustificativaModule],
    declarations: [AvaliarResultadoWorkupInternacionalComponent, AvaliarResultadoWorkupNacionalComponent,
                  ModalDescricaoExameAdicionalComponent],
    entryComponents: [ModalDescricaoExameAdicionalComponent],
    providers: [ResultadoWorkupService, AvaliacaoResultadoWorkupService, AvaliacaoPedidoColetaService, TarefaService],
    exports: [AvaliarResultadoWorkupInternacionalComponent, AvaliarResultadoWorkupNacionalComponent]
  })
  export class AvaliarResultadoWorkupModule { }
