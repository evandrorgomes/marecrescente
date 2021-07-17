import { Component, ContentChildren, QueryList, TemplateRef, Input } from '@angular/core';
import { ArrayUtil } from 'app/shared/util/array.util';
import { MenuItemDirective } from './menu.item.directive';
import { MenuItem } from './menu.item';
import { DEFAULT_ENCODING } from 'crypto';

@Component({
    selector: 'auto-collapsed-menu',
    templateUrl: './auto.collapsed.menu.html'
})
export class AutoCollapsedMenu {
    private static readonly DEFAULT_NORMAL_LENGHT : number = 4;
    //private static readonly DEFAULT_SPACE : number = 100;

    @ContentChildren(MenuItem)
    private menuItemns: QueryList<MenuItem>;


    private _normalItemns: TemplateRef<any>[];
    private _collapsedItemns: TemplateRef<any>[];

    constructor() {
        this._normalItemns = [];
        this._collapsedItemns = [];
    }

    @Input()
    public maxShowing: number = AutoCollapsedMenu.DEFAULT_NORMAL_LENGHT;

    public get normalItemns(): TemplateRef<any>[]{
        return this._normalItemns; 
    }

    public get collapsedItemns(): TemplateRef<any>[]{
        return this._collapsedItemns; 
    }

    public get hasCollapsedItemns(): boolean{
        return ArrayUtil.isNotEmpty(this.collapsedItemns);
    }

    ngAfterContentInit() {
        this.toOrganizeItemns(this.maxShowing);
    }

    /* @HostListener('resize', ['$event'])
    onResize(event) {
      this.toOrganizeItemns(Math.trunc(event.target.innerWidth / AutoCollapsedMenu.DEFAULT_SPACE));
    } */

    private toOrganizeItemns(normalLenght: number): void{
        if (ArrayUtil.isNotEmpty(this.menuItemns.toArray())) {
            this.menuItemns.toArray().forEach(item => {
                if(this.normalItemns.length < normalLenght){
                    this.normalItemns.push(item.menuItem.templateRef);
                }
                else {
                    this.collapsedItemns.push(item.menuItem.templateRef);
                }
            });
        }
    }
}