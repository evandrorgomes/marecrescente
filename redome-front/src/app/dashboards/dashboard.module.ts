import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { DashboardContatoComponent } from './contato/dashboard.contato.component';
import { ExportTranslateModule } from 'app/shared/export.translate.module';
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';
import { FormsModule } from '@angular/forms';
import { DashboardContatoService } from '../shared/service/dashboard.contato.service';
import { PaginationModule } from 'ngx-bootstrap/pagination';


@NgModule({
    imports: [ CommonModule, FormsModule, ExportTranslateModule, BsDatepickerModule, PaginationModule ],
    declarations: [ DashboardContatoComponent ],
    providers: [DashboardContatoService],
    exports:    [ DashboardContatoComponent ]
})
export class DashboardModule { }