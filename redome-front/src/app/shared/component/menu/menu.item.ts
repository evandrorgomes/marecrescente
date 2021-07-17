import { Component, Input, ViewChild } from '@angular/core';
import { MenuItemDirective } from 'app/shared/component/menu/menu.item.directive';

@Component({
    selector: 'menu-item',
    templateUrl: './menu.item.html'
})
export class MenuItem {

    @ViewChild(MenuItemDirective)
    public menuItem: MenuItemDirective;

}