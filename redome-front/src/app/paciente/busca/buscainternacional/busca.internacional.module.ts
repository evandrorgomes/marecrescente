import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { HeaderPacienteModule } from "app/paciente/consulta/identificacao/header.paciente.module";
import { DialogoModule } from "app/shared/dialogo/dialogo.module";
import { ExportTranslateModule } from "app/shared/export.translate.module";
import { LocusModule } from "app/shared/hla/locus/locus.module";
import { MensagemModule } from "app/shared/mensagem/mensagem.module";
import { ModalModule, TooltipModule } from "ngx-bootstrap";
import { UploadArquivoModule } from '../../../shared/upload/upload.arquivo.module';
import { BuscaInternacionalComponent } from "./busca.internacional.component";
import { ArquivoRelatorioInternacionalService } from "app/shared/service/arquivo.relatorio.internacional.service";


@NgModule({
    imports: [CommonModule, FormsModule, ExportTranslateModule, ModalModule, MensagemModule, 
              DialogoModule, TooltipModule, LocusModule, HeaderPacienteModule, UploadArquivoModule],
  
    entryComponents: [],
  
    declarations: [BuscaInternacionalComponent],
    
    providers: [ArquivoRelatorioInternacionalService],
    
    exports: [BuscaInternacionalComponent]
  })
  export class BuscaInternacionalModule { }