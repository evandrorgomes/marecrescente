import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { PrazoModule } from "app/paciente/busca/analise/diretivas/prazo.module";
import { TooltipModule } from "ngx-bootstrap";
import { BsDropdownModule } from "ngx-bootstrap/dropdown/bs-dropdown.module";
import { ExportTranslateModule } from "../../export.translate.module";
import { ModalComentarioMatchModule } from "../modalcomentariomatch/modal.comentario.match.module";
import { RessalvaDoadorModule } from "../ressalvadoador/ressalva.doador.module";
import { CardMatchModule } from "./card/card.match.module";
import { ListaMatchComponent } from "./lista.match.component";
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DiretivasModule } from "app/shared/diretivas/diretivas.module";

@NgModule({
    imports: [CommonModule, FormsModule, ReactiveFormsModule, ExportTranslateModule, ModalComentarioMatchModule, 
              RessalvaDoadorModule, TooltipModule, BsDropdownModule, PrazoModule, 
              CardMatchModule, DiretivasModule],
    declarations: [ListaMatchComponent],
    providers: [],
    exports: [ListaMatchComponent]
  })
  export class ListaMatchModule { }