import { NgModule } from "@angular/core";
import { CardChecklistDirective } from "./card.checklist.directive";
import { TooltipModule, BsDropdownModule } from 'ngx-bootstrap';
import { ReactiveFormsModule, FormsModule } from "@angular/forms";
import { CommonModule } from "@angular/common";
import { PrazoModule } from "app/paciente/busca/analise/diretivas/prazo.module";
import { BuscaChecklistService } from "app/shared/service/busca.checklist.service";
import { MatchDataEventService } from 'app/shared/component/listamatch/match.data.event.service';

@NgModule({
  imports: [ReactiveFormsModule, CommonModule, FormsModule, BsDropdownModule, TooltipModule, PrazoModule],
  declarations: [CardChecklistDirective],
  providers: [BuscaChecklistService, MatchDataEventService],
  exports: [CardChecklistDirective]
})
export class CardChecklistModule { 

}