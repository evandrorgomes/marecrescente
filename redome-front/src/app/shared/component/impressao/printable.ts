import { Component, ViewChild, ElementRef, ContentChild } from "@angular/core";

@Component({
    selector: 'printable',
    templateUrl: './printable.html'
})
export class Printable {

    /**
     * Realiza um "print" com o conteúdo do objeto informado.
     * 
     * @param component componente que deverá ser o conteúdo do print.
     */
    public print(): void{
        window.print();
    }
}