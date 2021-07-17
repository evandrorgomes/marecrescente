import { CommonModule } from '@angular/common';
import { NgModule } from "@angular/core";
import { AutoCollapsedMenuModule } from '../component/menu/auto.collapsed.menu.module';
import { DisableControlDirective } from './disable.control.directive';
import { Filtro } from './filtro.directive';
import { PermissaoDirective } from './permissao.directive';
import { RetornarPaginaAnterior } from './retornar.pagina.anterior';
import { Printable } from 'app/shared/component/impressao/printable';
import { Alert } from 'app/shared/component/alert/alert';

@NgModule({
  imports: [CommonModule, AutoCollapsedMenuModule],
  declarations: [Filtro, RetornarPaginaAnterior, 
                DisableControlDirective, PermissaoDirective, 
                Printable, Alert],
  providers: [],
  exports: [Filtro, RetornarPaginaAnterior, 
            DisableControlDirective, PermissaoDirective, 
            AutoCollapsedMenuModule, Printable,
            Alert]
})
export class DiretivasModule { 

}