import { ElementRef, Component, Input } from "@angular/core";

/**
 * @description Componente responsável por exibir um simples alerta na tela,
 * para alguma situação específica e que precise dar ciência ao usuário 
 * que a acessou.
 *  
 * @author Pizão
 * @export
 * @class Alert
 */
@Component({
    selector: 'alert',
    templateUrl: './alert.html'
})

export class Alert {

    @Input()
    public show: boolean = true;

}