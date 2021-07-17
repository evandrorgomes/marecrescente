import { Component, Input, Directive, ElementRef, ViewEncapsulation } from '@angular/core';
import { FormControl, AbstractControl } from '@angular/forms';

/**
 * Classe generica para exibição de mensagens de erro
 * 
 * @export
 * @class MensagemComponent
 * @author Fillipe Queiroz
 */
@Component({
    selector: 'msg',
    template: "<small *ngIf='mensagem'>" +
    "<span class='glyphicon glyphicon-exclamation-sign'></span>{{mensagem}}</small>",
    styleUrls: ['./mensagem.component.css'],
    encapsulation: ViewEncapsulation.None

})
export class MensagemComponent {

    @Input()
    public mensagem: string;

    constructor() { }

}