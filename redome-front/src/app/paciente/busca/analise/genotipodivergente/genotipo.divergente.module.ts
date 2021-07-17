import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { ExportTranslateModule } from "app/shared/export.translate.module";
import { ModalModule, TooltipModule } from "ngx-bootstrap";
import { MensagemModule } from "app/shared/mensagem/mensagem.module";
import { DialogoModule } from "app/shared/dialogo/dialogo.module";
import { LocusModule } from "app/shared/hla/locus/locus.module";
import { GenotipoDivergenteComponent } from "./genotipo.divergente.component";
import { HeaderDoadorModule } from '../../../../doador/consulta/header/header.doador.module';
import { LocusExameService } from '../../../../shared/service/locus.exame.service';
import { InputModule } from "app/shared/component/inputcomponent/input.module";
import { ModalEditarTextoEmailLaboratorioComponent } from "./modal/modal.editar.texto.email.laboratorio.component";
import { CKEditorModule } from "ng2-ckeditor";


@NgModule({
    imports: [CommonModule, FormsModule, ExportTranslateModule, ModalModule, MensagemModule, 
              DialogoModule, TooltipModule, LocusModule, HeaderDoadorModule, InputModule,
              CKEditorModule, ReactiveFormsModule],
  
    entryComponents: [ModalEditarTextoEmailLaboratorioComponent],
  
    declarations: [GenotipoDivergenteComponent, ModalEditarTextoEmailLaboratorioComponent],
    
    providers: [LocusExameService],
    
    exports: [GenotipoDivergenteComponent]
  })
  export class GenotipoDivergenteModule { }