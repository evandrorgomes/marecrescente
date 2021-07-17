import { CommonModule } from '@angular/common';
import { NgModule } from "@angular/core";
import { AutoCollapsedMenu } from 'app/shared/component/menu/auto.collapsed.menu';
import { BsDropdownModule } from 'ngx-bootstrap';
import { MenuItemDirective } from './menu.item.directive';
import { MenuItem } from './menu.item';

@NgModule({
  imports: [CommonModule, BsDropdownModule.forRoot()],
  declarations: [AutoCollapsedMenu, MenuItemDirective, MenuItem],
  providers: [],
  exports: [AutoCollapsedMenu, MenuItemDirective, MenuItem]
})
export class AutoCollapsedMenuModule { 

}