import { Component, ElementRef, Input, ViewEncapsulation } from '@angular/core';
import { TranslateService } from '@ngx-translate/core/src/translate.service';

/**
 * Componente para exibição da mensagem de obrigatoriedade dos campos.
 * 
 * @export
 * @class RequiredMensagemComponent
 * @author Pizão
 */
@Component({
    selector: 'required-message',
    template: "<small>" +
                    "<span class='glyphicon glyphicon-exclamation-sign'></span>" + 
                    "{{mensagem}}" + 
              "</small>",
    styleUrls: ['./mensagem.component.css'],
    encapsulation: ViewEncapsulation.None
})
export class RequiredMensagemComponent {

    public mensagem: string;

    constructor(private translate: TranslateService) {
        this.translate.get('mensagem.obrigatorio').subscribe(msg =>{
            this.mensagem = msg;
        });
    }

}