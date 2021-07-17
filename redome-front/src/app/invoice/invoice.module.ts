import {CommonModule} from '@angular/common';
import {NgModule} from '@angular/core';
import {InvoiceComponent} from "./invoice.component";
import {InvoiceService} from "../shared/service/invoice.service";
import {InvoiceApagarModule} from "./apagar/invoice.apagar.module";
import {RouterModule} from "@angular/router";
import {InvoiceApagarEventService} from "./apagar/cadastro/invoice.apagar.event.service";


@NgModule({
  imports: [CommonModule, RouterModule, InvoiceApagarModule],
  declarations: [InvoiceComponent],
  entryComponents: [],
  providers: [InvoiceService, InvoiceApagarEventService],
  exports: [InvoiceComponent]
})
export class InvoiceModule { }

