import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TranslateModule } from '@ngx-translate/core';
import { PaginationModule } from 'ngx-bootstrap/pagination';
import { InputModule } from '../../../shared/component/inputcomponent/input.module';
import { PedidoContatoSmsComponent } from './pedido.contato.sms.component';
import { PedidoContatoSmsService } from 'app/shared/service/pedido.contato.sms.service';

@NgModule({
    imports: [ CommonModule, FormsModule, ReactiveFormsModule, TranslateModule, InputModule, PaginationModule ],
    declarations: [ PedidoContatoSmsComponent ],
    providers: [PedidoContatoSmsService],
    exports: [ PedidoContatoSmsComponent ]
})
export class PedidoContatoSmsModule { }