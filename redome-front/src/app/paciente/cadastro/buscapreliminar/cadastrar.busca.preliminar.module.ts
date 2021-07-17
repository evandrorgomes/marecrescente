import { HlaModule } from './../../../shared/hla/hla.module';
import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { ReactiveFormsModule } from "@angular/forms";
import { TextMaskModule } from "angular2-text-mask/src/angular2TextMask";
import { ExportTranslateModule } from "../../../shared/export.translate.module";
import { TooltipModule, ModalModule, BsDropdownModule } from "ngx-bootstrap";
import { CurrencyMaskModule } from "ng2-currency-mask";
import { MensagemModule } from "../../../shared/mensagem/mensagem.module";
import { DiretivasModule } from "../../../shared/diretivas/diretivas.module";
import { MensagemModalModule } from "../../../shared/modal/mensagem.modal.module";
import { CadastrarBuscaPreliminarComponent } from "./cadastrar.busca.preliminar.component";
import { BuscaPreliminarService } from 'app/shared/service/busca.preliminar.service';

@NgModule({
  imports: [CommonModule, ReactiveFormsModule,  
            TextMaskModule, ExportTranslateModule, TooltipModule, ModalModule, 
            CurrencyMaskModule, BsDropdownModule, MensagemModule, 
            DiretivasModule, MensagemModalModule, HlaModule],
  declarations: [CadastrarBuscaPreliminarComponent],
  providers: [BuscaPreliminarService],
  exports: [CadastrarBuscaPreliminarComponent]
})
export class CadastrarBuscaPreliminarModule { }

  
