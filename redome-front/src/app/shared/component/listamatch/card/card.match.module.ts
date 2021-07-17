import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { PrazoModule } from "app/paciente/busca/analise/diretivas/prazo.module";
import { ExportTranslateModule } from "app/shared/export.translate.module";
import { TooltipModule } from "ngx-bootstrap";
import { BsDropdownModule } from "ngx-bootstrap/dropdown/bs-dropdown.module";
import { RessalvaDoadorModule } from "../../ressalvadoador/ressalva.doador.module";
import { CardChecklistModule } from '../checklist/card.checklist.module';
import { MatchDataEventService } from "../match.data.event.service";
import { CardMatchComponent } from "./card.match.component";


@NgModule({
    imports: [CommonModule, ExportTranslateModule,  
              RessalvaDoadorModule, TooltipModule, BsDropdownModule, PrazoModule, CardChecklistModule],
    declarations: [CardMatchComponent],
    providers: [MatchDataEventService],
    exports: [CardMatchComponent]
  })
  export class CardMatchModule { }